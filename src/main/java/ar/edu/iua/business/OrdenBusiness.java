package ar.edu.iua.business;

import ar.edu.iua.business.exception.*;
import ar.edu.iua.model.*;
import ar.edu.iua.model.DTO.OrdenSurtidorDTO;
import ar.edu.iua.model.DTO.PesajeDTO;
import ar.edu.iua.model.persistence.OrdenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrdenBusiness implements IOrdenBusiness {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrdenRepository ordenDAO;
    @Autowired
    private OrdenDetalleBusiness ordenDetalleBusiness;
    @Autowired
    private ConciliacionBusiness conciliacionBusiness;

    @Override
    public Orden load(Long id) throws BusinessException, NotFoundException {
        Optional<Orden> op;
        try {
            op = ordenDAO.findById(id);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
        if (!op.isPresent())
            throw new NotFoundException("No se encuentra el orden id:" + id);
        return op.get();
    }

    @Override
    public List<Orden> list() throws BusinessException {
        try {
            return ordenDAO.findAll();
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public Orden save(Orden orden) throws BusinessException {
        try {
            orden.setEstado(1);
            orden.setCaudal(0);
            orden.setDensidad(0);
            Date fechaGen = java.util.Calendar.getInstance().getTime();
            orden.setFechaGeneracionOrden(fechaGen);
            orden.setFechaUltimoAlmacenamiento(null);
            orden.setMasaAcum(0);
            orden.setTemperatura(0);
            orden.setPsw("");
            orden.setPesajeInicial(0);
            orden.setFechaPesaje(null);
            return ordenDAO.save(orden);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public void delete(Long id) throws BusinessException, NotFoundException {
        try {
            ordenDAO.deleteById(id);
        } catch (EmptyResultDataAccessException e1) {
            throw new NotFoundException("No se encuentra el orden id:" + id);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    @Override
    public Orden updateSurtidor(OrdenSurtidorDTO ordenSurtidorDTO) throws BusinessException,
            NotFoundException,BadRequestException{
        Orden orden = null;
        try {
            orden = ordenDAO.findByCodigoExterno(ordenSurtidorDTO.getIdOrden());
            
            if (!orden.getPsw().equals(ordenSurtidorDTO.getPsw())) {
                throw new BadRequestException("Password Inválido");
            }
            if (orden.getEstado() == 3) {
                throw new BadRequestException("Orden cerrada.");
            } else if (orden.getEstado() != 2) {
                throw new BadRequestException("La orden no se encuentra en estado 2.");
            }
            double capacidad = 0;
            for (Cisterna c : orden.getCamion().getCisternaList()) {
                capacidad += c.getCapacidad();
            }
            
            if (ordenSurtidorDTO.getMasaAcum() > capacidad || ordenSurtidorDTO.getMasaAcum() > orden.getPreset()) {
                throw new BadRequestException("No se puede cargar mas combustible, se excede la capacidad del camion");
            }
            DateFormat inputDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date dateSurtidor = inputDF.parse(ordenSurtidorDTO.getFecha());
            double caudal = (ordenSurtidorDTO.getMasaAcum() - orden.getMasaAcum());
            OrdenDetalle ordenDetalle = new OrdenDetalle(ordenSurtidorDTO.getMasaAcum(), ordenSurtidorDTO.getDensidad(), ordenSurtidorDTO.getTemp(), caudal, orden.getId(), dateSurtidor);
            if (caudal > 0 && orden.getMasaAcum() < ordenSurtidorDTO.getMasaAcum() && ordenSurtidorDTO.getMasaAcum() > 0) {
                if (orden.getFechaUltimoAlmacenamiento() != null) {
                   if ((dateSurtidor.getTime() - orden.getFechaUltimoAlmacenamiento().getTime()) >= 10000) {
                	   ordenDetalleBusiness.save(ordenDetalle);
                       ordenDAO.updateOrdenSurtidorConFecha(orden.getId(), caudal, ordenSurtidorDTO.getDensidad(), ordenSurtidorDTO.getTemp(), ordenSurtidorDTO.getMasaAcum(), dateSurtidor);
                       orden = load(orden.getId());
                   } else {
                       ordenDAO.updateOrdenSurtidor(orden.getId(), caudal, ordenSurtidorDTO.getDensidad(), ordenSurtidorDTO.getTemp(), ordenSurtidorDTO.getMasaAcum());
                       orden = load(orden.getId());
                   }    
                } else {
                    ordenDetalleBusiness.save(ordenDetalle);
                    ordenDAO.updateOrdenSurtidorConFecha(orden.getId(), caudal, ordenSurtidorDTO.getDensidad(), ordenSurtidorDTO.getTemp(), ordenSurtidorDTO.getMasaAcum(), dateSurtidor);
                    orden = load(orden.getId());
                }
            }
        }catch (BadRequestException e) {
            log.error(e.getMessage(), e);
            throw new BadRequestException(e);
        }catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(e);
        }
        if (orden == null) {

            throw new NotFoundException("No se encontro ningun producto cn el filtro especificado.");
        }
        return orden;
    }

  

    @Override
    public Orden updatePesajeInicial(PesajeDTO pesajeDTO) throws BusinessException, NotFoundException, BadRequestException {
        Orden orden = null;
        try {
            orden = ordenDAO.findByCodigoExterno(pesajeDTO.getIdOrden());
            if (orden.getEstado() != 1) {
                throw new BadRequestException("La orden no se encuentra en estado 1.");
            }
            Date dateSurtidor = java.util.Calendar.getInstance().getTime();
            String password = randomPassword(5); 
            ordenDAO.updatePesajeInicial(pesajeDTO.getIdOrden(), pesajeDTO.getPeso(), dateSurtidor, 2, password);
            orden = load(orden.getId());
        }catch (BadRequestException e) {
            log.error(e.getMessage(), e);
            throw new BadRequestException(e);
        }  
        catch (BusinessException e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(e);
        } 
        if (orden == null) {
            throw new NotFoundException("No se encontro ningun producto con el filtro.");
        }
        return orden;
    }

    public static String randomPassword(int len) {
        final String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            int randomIndex = random.nextInt(caracteres.length());
            sb.append(caracteres.charAt(randomIndex));
        }
        return sb.toString();
    }
    
    @Override
    public Orden cerrarOrdenPorCodigoExterno(OrdenSurtidorDTO ordenDTO) throws BusinessException, NotFoundException,BadRequestException {
        Orden orden = null;
        try {
            orden = ordenDAO.findByCodigoExterno(ordenDTO.getIdOrden());
            if (orden.getEstado() >= 3) {
                throw new BadRequestException("La orden ya fue cerrada.");
            } else if (orden.getEstado() == 2) {
                ordenDAO.cerrarOrdenPorCodigoExterno(ordenDTO.getIdOrden());
                orden = load(orden.getId());
            } else {
                throw new BadRequestException("La orden debe estar en estado 2.");
            }
        }catch (BadRequestException e) {
            log.error(e.getMessage(), e);
            throw new BadRequestException(e);
        } 
        catch (BusinessException e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(e);
        }
        if (orden == null) {
            throw new NotFoundException("No se encontro ningun producto cn el filtro especificado.");
        }
        return orden;
    }

    @Override
    public Orden updatePesajeFinal(PesajeDTO pesajeDTO) throws BusinessException, NotFoundException,BadRequestException {
        Orden orden = null;
        Conciliacion conciliacion = null;
        try {
            orden = ordenDAO.findByCodigoExterno(pesajeDTO.getIdOrden());
            if (orden.getEstado() < 3) {
                throw new BadRequestException("La orden no se ha cerrado aún.");
            } else if (orden.getEstado() == 3) {
                Date dateSurtidor = java.util.Calendar.getInstance().getTime();
                ordenDAO.updatePesajeFinal(pesajeDTO.getIdOrden(), pesajeDTO.getPeso(), dateSurtidor); 
                conciliacion = calcularConciliacion(orden.getId());
                conciliacion.setCodigoExterno(pesajeDTO.getIdOrden()); 
                conciliacion = conciliacionBusiness.save(conciliacion);
                ordenDAO.updateConciliacion(orden.getId(), conciliacion.getId());
                orden = load(orden.getId());
            }
            conciliacion = orden.getConciliacion();
        }catch (BadRequestException e) {
            log.error(e.getMessage(), e);
            throw new BadRequestException(e);
        }  
        catch (BusinessException e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(e);
        } 
        if (orden == null || conciliacion == null) {
            throw new NotFoundException("No se encontro ninguna orden con el filtro.");
        }
        return orden;
    }

    public Conciliacion calcularConciliacion(long idOrden) throws BusinessException, NotFoundException{
        Conciliacion conciliacion = new Conciliacion();
        try {
            Orden orden = load(idOrden);
            List<OrdenDetalle> lista = ordenDetalleBusiness.getAllOrdenDetalleByIdOrden(idOrden);
            conciliacion.setPesajeInicial(orden.getPesajeInicial());
            conciliacion.setPesajeFinal(orden.getPesajeFinal());
            conciliacion.setProductoCargado(orden.getMasaAcum());
            double netoPorBalanza = orden.getPesajeFinal() - orden.getPesajeInicial();
            conciliacion.setNetoBalanza(netoPorBalanza);
            double diferencia = netoPorBalanza - orden.getMasaAcum();
            conciliacion.setDiferenciaBalanzaCaudal(diferencia);
            double temp = 0;
            double densidad = 0;
            double caudal = 0;
            for(OrdenDetalle ordenAux : lista){
                temp += ordenAux.getTemp();
                densidad += ordenAux.getDensidad();
                caudal += ordenAux.getCaudal();
            }
            double promTemp = temp/lista.size();
            double promDensidad = densidad/lista.size();
            double promCaudal = caudal/lista.size();
            conciliacion.setTemp(promTemp);
            conciliacion.setDensidad(promDensidad);
            conciliacion.setCaudal(promCaudal);
        } catch (BusinessException e) {
            log.error(e.getMessage(), e);
            throw new BusinessException(e);
        } catch (NotFoundException e) {
            log.error(e.getMessage(), e);
            throw new NotFoundException(e);
        }

        return conciliacion;
    }
}
