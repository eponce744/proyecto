package ar.edu.iua.business;

import ar.edu.iua.business.exception.*;
import ar.edu.iua.model.Orden;
import ar.edu.iua.model.DTO.OrdenSurtidorDTO;
import ar.edu.iua.model.DTO.PesajeDTO;

import java.util.List;

public interface IOrdenBusiness {

    public Orden load(Long id) throws BusinessException, NotFoundException;

    public List<Orden> list() throws BusinessException;

    public Orden save(Orden orden) throws BusinessException;

    public void delete(Long id) throws BusinessException, NotFoundException;

    public Orden updateSurtidor(OrdenSurtidorDTO ordenSurtidorDTO) throws BusinessException, NotFoundException, BadRequestException; 

    public Orden updatePesajeInicial(PesajeDTO pesajeDTO) throws BusinessException, NotFoundException,BadRequestException;

    public Orden cerrarOrdenPorCodigoExterno(OrdenSurtidorDTO ordenDTO) throws BusinessException, NotFoundException,BadRequestException;

    public Orden updatePesajeFinal(PesajeDTO pesajeDTO) throws BusinessException, NotFoundException,BadRequestException;
}
