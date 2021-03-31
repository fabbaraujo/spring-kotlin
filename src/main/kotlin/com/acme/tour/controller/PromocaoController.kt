package com.acme.tour.controller

import com.acme.tour.model.Promocao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.concurrent.ConcurrentHashMap

@RestController
@RequestMapping("promocoes")
class PromocaoController {

    @Autowired
    lateinit var promocoes: ConcurrentHashMap<Long, Promocao>

    @GetMapping("/sayhello")
    fun sayHello(): String {
        return "Hello World!!"
    }

    @GetMapping
    fun getAllPromocoes(@RequestParam(required = false, defaultValue = "") localFilter: String) =
        promocoes.filter {
            it.value.local.contains(localFilter, true)
        }.map ( Map.Entry<Long, Promocao>::value ).toList()


    @GetMapping("/{id}")
    fun getPromocaoById(@PathVariable id: Long) = promocoes[id]

    @PostMapping
    fun createPromocao(@RequestBody promocao: Promocao) {
        promocoes[promocao.id] = promocao
    }

    @DeleteMapping("/{id}")
    fun deletePromocao(@PathVariable id: Long) {
        promocoes.remove(id)
    }

    @PutMapping("/{id}")
    fun updatePromocao(@PathVariable id: Long, @RequestBody promocao: Promocao) {
        promocoes.remove(id)
        promocoes[id] = promocao
    }
}