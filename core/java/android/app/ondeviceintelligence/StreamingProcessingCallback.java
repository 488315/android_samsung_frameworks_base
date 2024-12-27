package android.app.ondeviceintelligence;

import android.annotation.SystemApi;
import android.os.Bundle;

@SystemApi
/* loaded from: classes.dex */
public interface StreamingProcessingCallback extends ProcessingCallback {
    void onPartialResult(Bundle bundle);
}
