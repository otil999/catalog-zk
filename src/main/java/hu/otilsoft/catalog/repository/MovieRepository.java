package hu.otilsoft.catalog.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.text.Collator;
import java.util.*;

import hu.otilsoft.catalog.model.AbstractEntity;
import hu.otilsoft.catalog.model.Movie;

public class MovieRepository extends AbstractRepository<Movie> {
    public MovieRepository(EntityManager entityManager) {
        super(entityManager);
    }

    public List<Movie> findAll() {
        List<Movie> movies = entityManager.createQuery("select m from Movie m order by m.name", Movie.class).getResultList();
        movies.sort((o1, o2) -> COLLATOR.compare(o1.getName(), o2.getName()));
        return movies;
    }

//    public Movie find(long id) {
//        return entityManager
//                .createQuery("select m from Movie m where m.id=:id", Movie.class)
//                .setParameter("id", id)
//                .getSingleResult();
//    }

    public List<Movie> randomLeastSeen() {
        int maxCounter = (Integer) entityManager.createQuery("select min(m.counter) from Movie m").getSingleResult();
        List<Movie> list = entityManager
                .createQuery("select m from Movie m where m.counter=:counter", Movie.class)
                .setParameter("counter", maxCounter)
                .getResultList();
        int idx = new Random().nextInt(list.size());

        List<Movie> chosenMovie = new ArrayList<>(1);
        chosenMovie.add(list.get(idx));
        return chosenMovie;
    }
}
