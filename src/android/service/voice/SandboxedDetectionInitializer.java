package android.service.voice;

import android.annotation.SystemApi;
import android.os.Bundle;
import android.os.IRemoteCallback;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.os.SharedMemory;
import java.util.function.IntConsumer;

@SystemApi
/* loaded from: classes3.dex */
public interface SandboxedDetectionInitializer {
    public static final int INITIALIZATION_STATUS_SUCCESS = 0;
    public static final int INITIALIZATION_STATUS_UNKNOWN = 100;
    public static final String KEY_INITIALIZATION_STATUS = "initialization_status";
    public static final int MAXIMUM_NUMBER_OF_INITIALIZATION_STATUS_CUSTOM_ERROR = 2;

    static int getMaxCustomInitializationStatus() {
        return 2;
    }

    void onUpdateState(PersistableBundle persistableBundle, SharedMemory sharedMemory, long j, IntConsumer intConsumer);

    static IntConsumer createInitializationStatusConsumer(final IRemoteCallback iRemoteCallback) {
        if (iRemoteCallback != null) {
            return new IntConsumer() { // from class: android.service.voice.SandboxedDetectionInitializer$$ExternalSyntheticLambda0
                @Override // java.util.function.IntConsumer
                public final void accept(int i) {
                    SandboxedDetectionInitializer.lambda$createInitializationStatusConsumer$0(IRemoteCallback.this, i);
                }
            };
        }
        return null;
    }

    static /* synthetic */ void lambda$createInitializationStatusConsumer$0(IRemoteCallback iRemoteCallback, int i) {
        if (i > getMaxCustomInitializationStatus()) {
            throw new IllegalArgumentException("The initialization status is invalid for " + i);
        }
        try {
            Bundle bundle = new Bundle();
            bundle.putInt("initialization_status", i);
            iRemoteCallback.sendResult(bundle);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
