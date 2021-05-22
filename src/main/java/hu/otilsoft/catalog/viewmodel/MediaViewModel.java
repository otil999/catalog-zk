package hu.otilsoft.catalog.viewmodel;

import hu.otilsoft.catalog.Media;
import hu.otilsoft.catalog.repository.MediaRepository;

import java.util.List;

public class MediaViewModel extends EntityManagerViewModel {
    private final MediaRepository mediaRepository;

    public MediaViewModel() {
        mediaRepository = new MediaRepository(entityManager);
    }

    public List<Media> getMedia() {
        return mediaRepository.findAll();
    }
}
