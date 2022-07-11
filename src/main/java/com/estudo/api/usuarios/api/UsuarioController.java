package com.estudo.api.usuarios.api;

import com.estudo.api.commons.ApiCollectionResponse;
import com.estudo.api.usuarios.domain.Usuario;
import com.estudo.api.usuarios.domain.UsuarioService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

//import com.estudo.api.credencial.api.UsuarioUpdateSenhaDTO;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @Autowired
    private UsuarioDTOAssenbler assenbler;

    //@PreAuthorize("hasAnyRole('USUARIO_LISTAR_TODOS')")
    @GetMapping
    @ApiOperation("Recupera lista de Usuários")
    public ResponseEntity<ApiCollectionResponse> findAll(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "pageSize", defaultValue = "20") int pageSize, @RequestParam(value = "filter", defaultValue = "") String filter) {
        Page<Usuario> tipoPage = service.findAll(page, pageSize, filter, null);
        return ResponseEntity.ok()
                .body(ApiCollectionResponse.builder()
                        .items(tipoPage.getContent()).hasNext(tipoPage.hasNext()).build()
                );
    }

    //PreAuthorize("hasAnyRole('USUARIO_LISTAR_ID')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation("Recupera um Usuários pelo ID")
    public ResponseEntity<Usuario> find(@PathVariable String id) {
        Usuario obj = service.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    //@PreAuthorize("hasAnyRole('USUARIO_LISTAR_EMAIL')")
    @ApiOperation("Recupera um Usuários pelo seu E-Mail")
    @RequestMapping(value = "email/{email}", method = RequestMethod.GET)
    public ResponseEntity<Usuario> findByEmail(@PathVariable String email) {
        Usuario obj = service.findByEmail(email);
        return ResponseEntity.ok().body(obj);
    }

    //@PreAuthorize("hasAnyRole('USUARIO_LISTAR_CODIGO')")
    @ApiOperation("Recupera um Usuários pelo Código")
    @RequestMapping(value = "codigo/{codigo}", method = RequestMethod.GET)
    public ResponseEntity<Usuario> findByEmail(@PathVariable Integer codigo) {
        Usuario obj = service.findByCodigo(codigo);
        return ResponseEntity.ok().body(obj);
    }


    //@PreAuthorize("hasAnyRole('USUARIO_LISTAR_MYUSER')")
    @ApiOperation("Recupera um Usuários pelo Código")
    @RequestMapping(value = "MyUser/", method = RequestMethod.GET)
    public ResponseEntity<Usuario> findByMyUser() {
        Usuario obj = service.findByMyUser();
        return ResponseEntity.ok().body(obj);
    }

    //@PreAuthorize("hasAnyRole('USUARIO_INSERT')")
    @PostMapping
    @ApiOperation("Insere um Usuário")
    public ResponseEntity<Usuario> insert(@RequestBody Usuario usuario) {
        Usuario novaUsuario = service.insert(usuario);
        URI createdResponse = ServletUriComponentsBuilder.fromCurrentRequest().path("/").path(novaUsuario.getId())
                .build().toUri();
        return ResponseEntity.created(createdResponse).body(novaUsuario);
    }

    //@PreAuthorize("hasAnyRole('USUARIO_UPDATE')")
    @PutMapping(path = "/{id}")
    @ApiOperation("Atualiza um Usuário")
    public ResponseEntity<Usuario> update(@PathVariable String id, @RequestBody Usuario usuario) {
        Usuario novaUsuario = service.update(usuario);
        URI createdResponse = ServletUriComponentsBuilder.fromCurrentRequest().path("/").path(id)
                .build().toUri();
        return ResponseEntity.created(createdResponse).body(novaUsuario);
    }



    //@PreAuthorize("hasAnyRole('USUARIO_DELETE')")
    @ApiOperation("Remove Usuário pelo id")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Usuario> removeById(@PathVariable String id) {
        return ResponseEntity.ok().body(service.delete(id));
    }

//    @PreAuthorize("hasAnyRole('USUARIO_LISTAR_LOOKUP')")
//    @GetMapping(value = "/lookup/all")
//    @ApiOperation("Recupera lista de Usuarios para Lookup")
//    public ResponseEntity<ApiCollectionResponse> findAlllookup(@RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "pageSize", defaultValue = "20") int pageSize, @RequestParam(value = "filter", defaultValue = "") String filter, @RequestParam(value = "grupoId", defaultValue = "") String grupoId) {
//        Page<Usuario> allPage = service.findAll(page, pageSize, filter, "true",grupoId);
//        return ResponseEntity.ok()
//                .body(ApiCollectionResponse.builder()
//                        .items(assenbler.fromEntity(allPage.getContent())).hasNext(allPage.hasNext()).build()
//                );
//    }

}
