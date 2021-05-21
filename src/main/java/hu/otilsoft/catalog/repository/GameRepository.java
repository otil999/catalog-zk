package hu.otilsoft.catalog.repository;

import hu.otilsoft.catalog.model.Game;
import hu.otilsoft.catalog.model.Movie;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameRepository extends AbstractRepository<Game> {
    public GameRepository(EntityManager entityManager) {
        super(entityManager);
    }

    public List<Game> findAll() {
        return entityManager.createQuery("select g from Game g order by g.name", Game.class).getResultList();
    }

    public List<Game> randomLeastSeen() {
        int maxCounter = (Integer) entityManager.createQuery("select min(g.counter) from Game g").getSingleResult();
        List<Game> list = entityManager
                .createQuery("select g from Game g where g.counter=:counter", Game.class)
                .setParameter("counter", maxCounter)
                .getResultList();
        int idx = new Random().nextInt(list.size());

        List<Game> chosenGame = new ArrayList<>(1);
        chosenGame.add(list.get(idx));
        return chosenGame;
    }
}
