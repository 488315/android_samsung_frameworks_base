package androidx.room;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteCallbackList;
import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes.dex */
public final class MultiInstanceInvalidationService extends Service {
    public int maxClientId;
    public final Map clientNames = new LinkedHashMap();
    public final MultiInstanceInvalidationService$callbackList$1 callbackList = new RemoteCallbackList() { // from class: androidx.room.MultiInstanceInvalidationService$callbackList$1
        @Override // android.os.RemoteCallbackList
        public final void onCallbackDied(IInterface iInterface, Object obj) {
            this.this$0.clientNames.remove((Integer) obj);
        }
    };
    public final MultiInstanceInvalidationService$binder$1 binder = new MultiInstanceInvalidationService$binder$1(this);

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return this.binder;
    }
}
