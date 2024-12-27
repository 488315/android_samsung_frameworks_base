package android.media;

import android.annotation.SystemApi;

import java.util.List;
import java.util.function.Consumer;

@SystemApi
public interface NearbyMediaDevicesProvider {
    void registerNearbyDevicesCallback(Consumer<List<NearbyDevice>> consumer);

    void unregisterNearbyDevicesCallback(Consumer<List<NearbyDevice>> consumer);
}
