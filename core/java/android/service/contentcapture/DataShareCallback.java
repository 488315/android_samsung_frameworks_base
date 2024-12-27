package android.service.contentcapture;

import android.annotation.SystemApi;

import java.util.concurrent.Executor;

@SystemApi
public interface DataShareCallback {
    void onAccept(Executor executor, DataShareReadAdapter dataShareReadAdapter);

    void onReject();
}
