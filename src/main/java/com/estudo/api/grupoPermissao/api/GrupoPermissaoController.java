package com.estudo.api.grupoPermissao.api;

import com.estudo.api.commons.ApiCollectionResponse;
import com.estudo.api.grupoPermissao.domain.GrupoPermissao;
import com.estudo.api.grupoPermissao.domain.GrupoPermissaoService;
import com.estudo.api.grupoPermissao.domain.GrupoUsuarioPermissaoJDBCRepository;
import com.estudo.api.grupoPermissao.domain.GrupoUsuarioPermissaoLstDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/grupoPermissao")
public class GrupoPermissaoController {
    @Autowired
    private GrupoPermissaoService service;

    @Autowired
    private GrupoUsuarioPermissaoJDBCRepository jDBCRepository;


    @GetMapping
    @ApiOperation("Recupera lista de Grupo Permissao")
    public ResponseEntity<ApiCollectionResponse> findAll(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "pageSize", defaultValue = "20") int pageSize, @RequestParam(value = "filter", defaultValue = "") String filter) {
        Page<GrupoPermissao> tipoPage = service.findAll(page, pageSize,filter);
        return ResponseEntity.ok()
                .body(ApiCollectionResponse.builder()
                        .items(tipoPage.getContent()).hasNext(tipoPage.hasNext()).build()
                );
    }


    @RequestMapping(value = "/{idGrupo}", method = RequestMethod.GET)
    @ApiOperation("Recupera lista de Grupo Permissao pelo grupo informado")
    public ResponseEntity<ApiCollectionResponse> findByListaGrupoPermissaoPorGrupo(@PathVariable String idGrupo) {
        List<GrupoUsuarioPermissaoLstDto> byGrupo = service.findByListaGrupoPermissaoPorGrupo(idGrupo);
        return ResponseEntity.ok().body(ApiCollectionResponse.builder()
                .items(byGrupo).hasNext(false).build());
    }


    @RequestMapping(value = "agrupador/{agrupador}", method = RequestMethod.GET)
    @ApiOperation("Recupera o Grupo Permissao pelo Agrupador")
    public ResponseEntity<GrupoPermissao> findByAgrupador(@PathVariable String agrupador) {
        GrupoPermissao obj = service.findByAgrupador(agrupador);
        return ResponseEntity.ok().body(obj);
    }


    @PostMapping
    @ApiOperation("Insere um Grupo Permissao")
    public ResponseEntity<GrupoPermissao> insert(@RequestBody GrupoPermissao grupoPermissao) {
        GrupoPermissao novoGrupoPermissao = service.insert(grupoPermissao);
        URI createdResponse = ServletUriComponentsBuilder.fromCurrentRequest().path("/").path(novoGrupoPermissao.getId())
                .build().toUri();
        return ResponseEntity.created(createdResponse).body(grupoPermissao);
    }


    @PutMapping(path = "/{id}")
    @ApiOperation("Atualiza um Grupo de Permissao")
    public ResponseEntity<GrupoPermissao> update(@PathVariable String id, @RequestBody GrupoPermissao grupoPermissao) {
        GrupoPermissao novoGrupoPermissao = service.update(grupoPermissao);
        URI createdResponse = ServletUriComponentsBuilder.fromCurrentRequest().path("/").path(novoGrupoPermissao.getId())
                .build().toUri();
        return ResponseEntity.created(createdResponse).body(grupoPermissao);
    }


    @ApiOperation("Remove GrupoPermissao pelo id")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<GrupoPermissao> removeById(@PathVariable String id) {
        return ResponseEntity.ok().body(service.delete(id));
    }
}
