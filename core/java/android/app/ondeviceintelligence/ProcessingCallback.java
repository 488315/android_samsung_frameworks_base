package android.app.ondeviceintelligence;

import android.annotation.SystemApi;
import android.os.Bundle;

import java.util.function.Consumer;

@SystemApi
public interface ProcessingCallback {
    void onError(OnDeviceIntelligenceException onDeviceIntelligenceException);

    void onResult(Bundle bundle);

    default void onDataAugmentRequest(Bundle processedContent, Consumer<Bundle> contentConsumer) {
        contentConsumer.accept(Bundle.EMPTY);
    }
}
