package android.media;

import java.util.Set;

public interface AudioMetadataReadMap {
    <T> boolean containsKey(AudioMetadata.Key<T> key);

    AudioMetadataMap dup();

    <T> T get(AudioMetadata.Key<T> key);

    Set<AudioMetadata.Key<?>> keySet();

    int size();
}
