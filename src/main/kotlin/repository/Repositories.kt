package hu.otilsoft.catalog.repository

import hu.otilsoft.catalog.Game
import hu.otilsoft.catalog.Media
import hu.otilsoft.catalog.Movie
import hu.otilsoft.catalog.Series
import hu.otilsoft.catalog.Storage
import hu.otilsoft.catalog.StorageType
import java.util.Random
import javax.persistence.EntityManager


class StorageTypeRepository(entityManager: EntityManager) : AbstractRepository<StorageType>(entityManager) {
    override fun findAll(): List<StorageType> =
        entityManager
            .createQuery("select st from storage_type st order by st.name", StorageType::class.java)
            .resultList
}


class SeriesRepository(entityManager: EntityManager) : AbstractRepository<Series>(entityManager) {
    override fun findAll(): List<Series> =
        entityManager
            .createQuery("select s from Series s", Series::class.java)
            .resultList
            .sortedWith { series1, series2 -> collator.compare(series1.name, series2.name) }
}


class MediaRepository(entityManager: EntityManager) : AbstractRepository<Media>(entityManager) {
    override fun findAll(): List<Media> =
        entityManager
            .createQuery("select m from Media m order by m.name", Media::class.java)
            .resultList
}


class StorageRepository(entityManager: EntityManager) : AbstractRepository<Storage>(entityManager) {
    override fun findAll(): List<Storage> =
        entityManager
            .createQuery("select s from Storage s order by s.name", Storage::class.java)
            .resultList
}


class MovieRepository(entityManager: EntityManager) : AbstractRepository<Movie>(entityManager) {
    override fun findAll(): List<Movie> =
        entityManager
            .createQuery("select m from Movie m", Movie::class.java)
            .resultList
            .sortedWith { movie1, movie2 -> collator.compare(movie1.name, movie2.name) }

    //    public Movie find(long id) {
    //        return entityManager
    //                .createQuery("select m from Movie m where m.id=:id", Movie.class)
    //                .setParameter("id", id)
    //                .getSingleResult();
    //    }

    fun randomLeastSeen(): List<Movie> {
        val maxCounter = entityManager.createQuery("select min(m.counter) from Movie m").singleResult as Int
        val list = entityManager
            .createQuery("select m from Movie m where m.counter=:counter", Movie::class.java)
            .setParameter("counter", maxCounter)
            .resultList
        val idx = Random().nextInt(list.size)
        return listOf(list[idx])
    }
}


class GameRepository(entityManager: EntityManager) : AbstractRepository<Game>(entityManager) {
    override fun findAll(): List<Game> =
        entityManager
            .createQuery("select g from Game g", Game::class.java)
            .resultList

    fun randomLeastSeen(): List<Game> {
        val maxCounter = entityManager.createQuery("select min(g.counter) from Game g").singleResult as Int
        val list: List<Game> = entityManager
            .createQuery("select g from Game g where g.counter = :counter", Game::class.java)
            .setParameter("counter", maxCounter)
            .resultList
        val idx = Random().nextInt(list.size)
        return listOf(list[idx])
    }
}
