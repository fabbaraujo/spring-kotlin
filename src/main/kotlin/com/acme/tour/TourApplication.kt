package com.acme.tour

import com.acme.tour.model.Promocao
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import java.util.concurrent.ConcurrentHashMap

@SpringBootApplication
class TourApplication {
	companion object {
		val initialPromocoes = arrayOf(
			Promocao(1, "Viagem para Cancun", "Cancun", true, 7, 4999.99),
			Promocao(2, "Viagem para Nova Zelândia", "Nova Zelândia", false, 12, 12000.00),
			Promocao(3, "Viagem para Tailândia", "Tailândia", false, 17, 15000.00),
			Promocao(4, "Viagem para Gramado", "Gramado", true, 5, 3500.35)
		)
	}

	@Bean
	fun promocoes() = ConcurrentHashMap<Long, Promocao>(initialPromocoes.associateBy(Promocao::id))
}

fun main(args: Array<String>) {
	runApplication<TourApplication>(*args)
}
