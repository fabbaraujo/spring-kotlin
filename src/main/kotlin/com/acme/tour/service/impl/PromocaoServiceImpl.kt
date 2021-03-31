package com.acme.tour.service.impl

import com.acme.tour.model.Promocao
import com.acme.tour.service.PromocaoService
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

@Component
class PromocaoServiceImpl: PromocaoService {
    companion object { //static do java
        val initialPromocoes = arrayOf(
            Promocao(1, "Viagem para Cancun", "Cancun", true, 7, 4999.99),
            Promocao(2, "Viagem para Nova Zelândia", "Nova Zelândia", false, 12, 12000.00),
            Promocao(3, "Viagem para Tailândia", "Tailândia", false, 17, 15000.00),
            Promocao(4, "Viagem para Gramado", "Gramado", true, 5, 3500.35)
        )
    }

    var promocoes = ConcurrentHashMap<Long, Promocao>(initialPromocoes.associateBy(Promocao::id))

    override fun create(promocao: Promocao) {
        promocoes[promocao.id] = promocao
    }

    override fun getById(id: Long): Promocao? {
        return promocoes[id]
    }

    override fun delete(id: Long) {
        promocoes.remove(id)
    }

    override fun update(id: Long, promocao: Promocao) {
        delete(id)
        create(promocao)
    }

    override fun searchByLocal(local: String): List<Promocao> {
        return promocoes.filter {
            it.value.local.contains(local, true)
        }.map ( Map.Entry<Long, Promocao>::value ).toList()
    }
}