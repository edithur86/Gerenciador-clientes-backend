package com.estudo.api.grupo.api;

import com.estudo.api.commons.ApiCollectionResponse;
import com.estudo.api.grupo.domain.Grupo;
import com.estudo.api.grupo.domain.GrupoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/grupo")
public class GrupoController {

    @Autowired
    private GrupoService service;


    @GetMapping
    @ApiOperation("Recupera lista Grupo de Usuário")
    public ResponseEntity<ApiCollectionResponse> findAll(@RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "pageSize", defaultValue = "20") int pageSize, @RequestParam(value = "filter", defaultValue = "") String filter) {
        Page<Grupo> allPage = service.findAll(page, pageSize, filter, null);
        return ResponseEntity.ok()
                .body(ApiCollectionResponse.builder()
                        .items(allPage.getContent()).hasNext(allPage.hasNext()).build()
                );
    }


    @GetMapping(value = "/ordem")
    @ApiOperation("Recupera a lista de grupos de usuarios ordenado pela ordem")
    public ResponseEntity<ApiCollectionResponse> findAllOrderByOrdem(@RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "pageSize", defaultValue = "20") int pageSize) {
        Page<Grupo> allPage = service.findAllOrderByOrdem(page, pageSize, "true");
        return ResponseEntity.ok()
                .body(ApiCollectionResponse.builder()
                        .items(allPage.getContent()).hasNext(allPage.hasNext()).build()
                );
    }


    @PutMapping(value = "ordenar/{idGrupo}/ordemDestino/{ordemDestino}")
    @ApiOperation("Fazendo a ordenação Subir/Descer do Grupo de Usuário")
    public ResponseEntity<Grupo> ordenar(@PathVariable String idGrupo, @PathVariable Integer ordemDestino) {
        return ResponseEntity.ok().body(service.ordenar(idGrupo, ordemDestino));
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation("Recupera um Grupo de usuário pelo ID")
    public ResponseEntity<Grupo> findById(@PathVariable String id) {
        Grupo obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }


    @RequestMapping(value = "codigo/{codigo}", method = RequestMethod.GET)
    @ApiOperation("Recupera Grupo de Usuário pelo Código")
    public ResponseEntity<Grupo> findByCodigo(@PathVariable Integer codigo) {
        Grupo obj = service.findByCodigo(codigo);
        return ResponseEntity.ok().body(obj);
    }


    @PostMapping
    @ApiOperation("Insere Grupo de Usuário")
    public ResponseEntity<Grupo> insert(@RequestBody Grupo grupo) {
        Grupo newGrupo = service.insert(grupo);
        URI createdResponse = ServletUriComponentsBuilder.fromCurrentRequest().path("/").path(newGrupo.getId())
                .build().toUri();
        return ResponseEntity.created(createdResponse).body(grupo);
    }


    @PutMapping(path = "/{id}")
    @ApiOperation("Atualiza Grupo de usuário")
    public ResponseEntity<Grupo> update(@PathVariable String id, @RequestBody Grupo grupo) {
        Grupo newGrupo = service.update(grupo);
        URI createdResponse = ServletUriComponentsBuilder.fromCurrentRequest().path("/").path(newGrupo.getId())
                .build().toUri();
        return ResponseEntity.created(createdResponse).body(grupo);
    }


    @ApiOperation("Remove Grupo pelo id")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Grupo> removeById(@PathVariable String id) {
        return ResponseEntity.ok().body(service.delete(id));
    }


    @GetMapping(value = "/lookup/all")
    @ApiOperation("Recupera lista de Grupo de Usuário para Lookup")
    public ResponseEntity<ApiCollectionResponse> findAlllookup(@RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "pageSize", defaultValue = "20") int pageSize, @RequestParam(value = "filter", defaultValue = "") String filter) {
        Page<Grupo> allPage = service.findAll(page, pageSize, filter, "true");
        return ResponseEntity.ok()
                .body(ApiCollectionResponse.builder()
                        .items(allPage.getContent()).hasNext(allPage.hasNext()).build()
                );
    }
}
