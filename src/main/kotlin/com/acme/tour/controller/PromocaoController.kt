package com.acme.tour.controller

import com.acme.tour.exception.PromocaoNotFoundException
import com.acme.tour.model.ErrorMessage
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
    fun getAllPromocoes(@RequestParam(required = false, defaultValue = "0") start: Int,
                        @RequestParam(required = false, defaultValue = "3") size: Int)
    : ResponseEntity<List<Promocao>> {
        return ResponseEntity(this.promocaoService.getAll(start, size), HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun getPromocaoById(@PathVariable id: Long): ResponseEntity<Any> {
        var promocao = this.promocaoService.getById(id)

        return if(promocao != null)
            ResponseEntity(promocao, HttpStatus.OK)
        else
            ResponseEntity(ErrorMessage("Promocao não localizada", "Promoção ${id} não localizada."), HttpStatus.NOT_FOUND)
    }

    @PostMapping
    fun createPromocao(@RequestBody promocao: Promocao): ResponseEntity<Map<String, String>> {
        this.promocaoService.create(promocao)
        val map = mapOf("message" to "OK")
        return ResponseEntity(map, HttpStatus.CREATED)
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

    @GetMapping("/count")
    fun count(): ResponseEntity<Map<String, Long>> =
        ResponseEntity.ok().body(mapOf("count" to this.promocaoService.count()))

    @GetMapping("/ordenados")
    fun ordenados() = this.promocaoService.getAllSortedByLocal()

    @GetMapping("/menor-que-9000")
    fun getAllMenores() = this.promocaoService.getAllByPrecoMenorQue9000()
}