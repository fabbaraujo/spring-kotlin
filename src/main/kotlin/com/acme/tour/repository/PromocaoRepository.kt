package com.acme.tour.repository

import com.acme.tour.model.Promocao
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
interface PromocaoRepository : PagingAndSortingRepository<Promocao, Long>{

    @Query(value="select p from Promocao p where p.preco <= :preco")
    //@Query(value="select * from promocao p where p.preco <= 9000", nativeQuery = true)
    fun findByPrecoMenorQue(@Param("preco") preco: Double): List<Promocao>

    @Query(value = "select p from Promocao p where p.local in :names")
    fun findByLocalInList(@Param("names") names: List<String>): List<Promocao>

    @Query(value = "update Promocao p set p.preco = :valor where p.local = :local")
    @Transactional
    @Modifying
    fun updateByLocal(@Param("preco") preco: Double, @Param("local") local: String)
}