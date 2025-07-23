package androidx.room;

import android.os.RemoteException;
import android.util.Log;
import androidx.room.IMultiInstanceInvalidationService;
import java.util.LinkedHashMap;
import kotlin.Unit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class MultiInstanceInvalidationService$binder$1 extends IMultiInstanceInvalidationService.Stub {
    public final /* synthetic */ MultiInstanceInvalidationService this$0;

    public MultiInstanceInvalidationService$binder$1(MultiInstanceInvalidationService multiInstanceInvalidationService) {
        this.this$0 = multiInstanceInvalidationService;
    }

    @Override // androidx.room.IMultiInstanceInvalidationService
    public final void broadcastInvalidation(String[] strArr, int i) {
        MultiInstanceInvalidationService multiInstanceInvalidationService = this.this$0;
        synchronized (multiInstanceInvalidationService.callbackList) {
            String str = (String) ((LinkedHashMap) multiInstanceInvalidationService.clientNames).get(Integer.valueOf(i));
            if (str == null) {
                Log.w("ROOM", "Remote invalidation client ID not registered");
                return;
            }
            int beginBroadcast = multiInstanceInvalidationService.callbackList.beginBroadcast();
            for (int i2 = 0; i2 < beginBroadcast; i2++) {
                try {
                    Integer num = (Integer) multiInstanceInvalidationService.callbackList.getBroadcastCookie(i2);
                    int intValue = num.intValue();
                    String str2 = (String) ((LinkedHashMap) multiInstanceInvalidationService.clientNames).get(num);
                    if (i != intValue && str.equals(str2)) {
                        try {
                            ((IMultiInstanceInvalidationCallback) multiInstanceInvalidationService.callbackList.getBroadcastItem(i2)).onInvalidation(strArr);
                            Unit unit = Unit.INSTANCE;
                        } catch (RemoteException e) {
                            Log.w("ROOM", "Error invoking a remote callback", e);
                        }
                    }
                } catch (Throwable th) {
                    multiInstanceInvalidationService.callbackList.finishBroadcast();
                    throw th;
                }
            }
            multiInstanceInvalidationService.callbackList.finishBroadcast();
            Unit unit2 = Unit.INSTANCE;
        }
    }

    @Override // androidx.room.IMultiInstanceInvalidationService
    public final int registerCallback(IMultiInstanceInvalidationCallback iMultiInstanceInvalidationCallback, String str) {
        int i = 0;
        if (str == null) {
            return 0;
        }
        MultiInstanceInvalidationService multiInstanceInvalidationService = this.this$0;
        synchronized (multiInstanceInvalidationService.callbackList) {
            try {
                int i2 = multiInstanceInvalidationService.maxClientId + 1;
                multiInstanceInvalidationService.maxClientId = i2;
                if (multiInstanceInvalidationService.callbackList.register(iMultiInstanceInvalidationCallback, Integer.valueOf(i2))) {
                    multiInstanceInvalidationService.clientNames.put(Integer.valueOf(i2), str);
                    i = i2;
                } else {
                    multiInstanceInvalidationService.maxClientId--;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return i;
    }

    @Override // androidx.room.IMultiInstanceInvalidationService
    public final void unregisterCallback(IMultiInstanceInvalidationCallback iMultiInstanceInvalidationCallback, int i) {
        MultiInstanceInvalidationService multiInstanceInvalidationService = this.this$0;
        synchronized (multiInstanceInvalidationService.callbackList) {
            multiInstanceInvalidationService.callbackList.unregister(iMultiInstanceInvalidationCallback);
        }
    }
}
