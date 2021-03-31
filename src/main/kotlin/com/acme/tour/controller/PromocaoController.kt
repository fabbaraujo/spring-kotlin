package com.acme.tour.controller

import com.acme.tour.model.Promocao
import com.acme.tour.service.PromocaoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.concurrent.ConcurrentHashMap

@RestController
@RequestMapping("promocoes")
class PromocaoController {

    @Autowired
    lateinit var promocaoService: PromocaoService

    @GetMapping
    fun getAllPromocoes(@RequestParam(required = false, defaultValue = "") localFilter: String): ResponseEntity<List<Promocao>> {
        return ResponseEntity(this.promocaoService.searchByLocal(localFilter), HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun getPromocaoById(@PathVariable id: Long): ResponseEntity<Promocao?> {
        var promocao = this.promocaoService.getById(id)
        var status = if(promocao == null) HttpStatus.NOT_FOUND else HttpStatus.OK

        return ResponseEntity(promocao, status)
    }

    @PostMapping
    fun createPromocao(@RequestBody promocao: Promocao): ResponseEntity<Unit> {
        this.promocaoService.create(promocao)
        return ResponseEntity(Unit, HttpStatus.CREATED)
    }

    @DeleteMapping("/{id}")
    fun deletePromocao(@PathVariable id: Long): ResponseEntity<Unit> {
        var status = HttpStatus.NOT_FOUND

        if(this.promocaoService.getById(id) != null) {
            status = HttpStatus.ACCEPTED
            this.promocaoService.delete(id)
        }

        return ResponseEntity(Unit, status)
    }

    @PutMapping("/{id}")
    fun updatePromocao(@PathVariable id: Long, @RequestBody promocao: Promocao): ResponseEntity<Unit> {
        var status = HttpStatus.NOT_FOUND

        if(this.promocaoService.getById(id) != null) {
            status = HttpStatus.ACCEPTED
            this.promocaoService.update(id, promocao)
        }

        return ResponseEntity(Unit, status)
    }
}