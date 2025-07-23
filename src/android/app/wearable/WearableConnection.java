package android.app.wearable;

import android.annotation.SystemApi;
import android.os.ParcelFileDescriptor;
import android.os.PersistableBundle;

@SystemApi
/* loaded from: classes.dex */
public interface WearableConnection {
    ParcelFileDescriptor getConnection();

    PersistableBundle getMetadata();

    void onConnectionAccepted();

    void onError(int i);
}
