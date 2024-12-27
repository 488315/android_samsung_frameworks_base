package android.app.ondeviceintelligence;

import android.annotation.SystemApi;
import android.os.Bundle;

@SystemApi
public interface StreamingProcessingCallback extends ProcessingCallback {
    void onPartialResult(Bundle bundle);
}
