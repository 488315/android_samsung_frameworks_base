package androidx.room;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteCallbackList;
import java.util.HashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class MultiInstanceInvalidationService extends Service {
    public int mMaxClientId = 0;
    public final HashMap mClientNames = new HashMap();
    public final RemoteCallbackListC04881 mCallbackList = new RemoteCallbackList() { // from class: androidx.room.MultiInstanceInvalidationService.1
        @Override // android.os.RemoteCallbackList
        public final void onCallbackDied(IInterface iInterface, Object obj) {
            MultiInstanceInvalidationService.this.mClientNames.remove(Integer.valueOf(((Integer) obj).intValue()));
        }
    };
    public final BinderC04892 mBinder = new BinderC04892();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: androidx.room.MultiInstanceInvalidationService$2 */
    public final class BinderC04892 extends IMultiInstanceInvalidationService$Stub {
        public BinderC04892() {
        }
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return this.mBinder;
    }
}
