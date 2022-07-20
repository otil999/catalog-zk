package hu.otilsoft.catalog.repository

import hu.otilsoft.catalog.model.Game
import hu.otilsoft.catalog.model.Media
import hu.otilsoft.catalog.model.Movie
import hu.otilsoft.catalog.model.Series
import hu.otilsoft.catalog.model.Storage
import hu.otilsoft.catalog.model.StorageType
import java.util.Random
import javax.persistence.EntityManager


class StorageTypeRepository(entityManager: EntityManager) : AbstractRepository<StorageType>(entityManager) {
    override fun findAll(): List<StorageType> =
        entityManager
            .createQuery("select st from storage_type st order by st.name", StorageType::class.java)
            .resultList
}


class  SeriesRepository(entityManager: EntityManager) : AbstractRepository<Series>(entityManager) {
    override fun findAll(): List<Series> =
        entityManager
            .createQuery("select s from Series s", Series::class.java)
            .resultList
            .sortedWith { series1, series2 -> collator.compare(series1.name, series2.name) }

    fun findByName(name: String): List<Series> =
        entityManager
            .createQuery("select s from Series s where lower(s.name) like :keyword", Series::class.java)
            .setParameter("keyword", "${name.toLowerCase()}%")
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

    fun findByName(name: String): List<Movie> =
        entityManager
            .createQuery("select m from Movie m where lower(m.name) like :keyword", Movie::class.java)
            .setParameter("keyword", "%${name.toLowerCase()}%")
            .resultList
            .sortedWith { movie1, movie2 -> collator.compare(movie1.name, movie2.name) }

    fun findBySeries(series: Series): List<Movie> =
        entityManager
            .createQuery("select m from Movie m where m.series=:series order by m.seriesNum", Movie::class.java)
            .setParameter("series", series)
            .resultList

    fun findByStorage(storage: Storage): List<Movie> =
        entityManager
            .createQuery("select m from Movie m where m.storage=:storage order by m.name", Movie::class.java)
            .setParameter("storage", storage)
            .resultList

    fun randomLeastSeen(): List<Movie> {
        val maxCounter = entityManager.createQuery("select min(m.counter) from Movie m").singleResult as Int
        val list = entityManager
            .createQuery("select m from Movie m where m.counter=:counter", Movie::class.java)
            .setParameter("counter", maxCounter)
            .resultList
        val idx = Random().nextInt(list.size)
        return listOf(list[idx])
    }

    fun recentlyViewed(): List<Movie> =
        entityManager
            .createQuery("select m from Movie m order by updated desc", Movie::class.java)
            .resultList
            .subList(0, 20)

    fun save(entity: Movie, affectedSeries: Series?) {
        super.save(entity)
        affectedSeries?.let { entityManager.refresh(it) }
        entity.series?.let { entityManager.refresh(it) }
    }
}


class GameRepository(entityManager: EntityManager) : AbstractRepository<Game>(entityManager) {
    override fun findAll(): List<Game> =
        entityManager
            .createQuery("select g from Game g", Game::class.java)
            .resultList

    fun findByName(name: String): List<Game> =
        entityManager
            .createQuery("select g from Game g where lower(g.name) like :keyword", Game::class.java)
            .setParameter("keyword", "${name.toLowerCase()}%")
            .resultList
            .sortedWith { game1, game2 -> collator.compare(game1.name, game2.name) }

    fun findBySeries(series: Series): List<Game> =
        entityManager
            .createQuery("select g from Game g where g.series=:series order by g.seriesNum", Game::class.java)
            .setParameter("series", series)
            .resultList

    fun randomLeastPlayed(): List<Game> {
        val maxCounter = entityManager.createQuery("select min(g.counter) from Game g").singleResult as Int
        val list: List<Game> = entityManager
            .createQuery("select g from Game g where g.counter = :counter", Game::class.java)
            .setParameter("counter", maxCounter)
            .resultList
        val idx = Random().nextInt(list.size)
        return listOf(list[idx])
    }

    fun randomPlayed(): List<Game> {
        val list: List<Game> = entityManager
            .createQuery("select g from Game g where g.counter >= 1", Game::class.java)
            .resultList
        val idx = Random().nextInt(list.size)
        return listOf(list[idx])
    }
}
