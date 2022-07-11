package com.estudo.api.cliente.api;

import com.estudo.api.cliente.domain.Cliente;
import com.estudo.api.cliente.domain.ClienteService;
import com.estudo.api.commons.ApiCollectionResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @GetMapping
    @ApiOperation("Recupera lista de Clientes")
    public ResponseEntity<ApiCollectionResponse> findAll(@RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "pageSize", defaultValue = "20") int pageSize, @RequestParam(value = "filter", defaultValue = "") String filter) {
        Page<Cliente> allPage = service.findAll(page, pageSize, filter);
        return ResponseEntity.ok()
                .body(ApiCollectionResponse.builder()
                        .items(allPage.getContent()).hasNext(allPage.hasNext()).build()
                );
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation("Recupera uma Cliente pelo ID")
    public ResponseEntity<Cliente> findById(@PathVariable String id) {
        Cliente obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }


    @RequestMapping(value = "cnpjCpf/{cnpjCpf}", method = RequestMethod.GET)
    @ApiOperation("Recupera uma Cliente pelo CNPJ ou CPF")
    public ResponseEntity<Cliente> findByCnpjCPF(@PathVariable String cnpjCpf) {
        Cliente obj = service.findByCnpjCPF(cnpjCpf);
        return ResponseEntity.ok().body(obj);
    }


    @PostMapping
    @ApiOperation("Insere uma Cliente")
    public ResponseEntity<Cliente> insert(@RequestBody Cliente cliente) {
        Cliente novoCliente = service.insert(cliente);
        URI createdResponse = ServletUriComponentsBuilder.fromCurrentRequest().path("/").path(novoCliente.getId())
                .build().toUri();
        return ResponseEntity.created(createdResponse).body(novoCliente);
    }


    @PutMapping(path = "/{id}")
    @ApiOperation("Atualiza um Cliente")
    public ResponseEntity<Cliente> update(@PathVariable String id, @RequestBody Cliente cliente) {
        Cliente novoCliente = service.update(cliente);
        URI createdResponse = ServletUriComponentsBuilder.fromCurrentRequest().path("/").path(novoCliente.getId())
                .build().toUri();
        return ResponseEntity.created(createdResponse).body(novoCliente);
    }
}
