package com.android.server.broadcastradio.aidl;

import android.hardware.broadcastradio.IBroadcastRadio;
import android.os.IBinder;
import android.os.IServiceCallback;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.ArrayMap;
import android.util.Log;
import android.util.SparseArray;
import com.android.server.utils.Slogf;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class BroadcastRadioServiceImpl {
    public static final boolean DEBUG = Log.isLoggable("BcRadioAidlSrv", 3);
    public final Object mLock = new Object();
    public final Map mServiceNameToModuleIdMap = new ArrayMap();
    public final SparseArray mModules = new SparseArray();
    public final AnonymousClass1 mServiceListener = new IServiceCallback.Stub() { // from class: com.android.server.broadcastradio.aidl.BroadcastRadioServiceImpl.1
        public final void onRegistration(String str, IBinder iBinder) {
            boolean z;
            Slogf.i("BcRadioAidlSrv", "onRegistration for %s", str);
            synchronized (BroadcastRadioServiceImpl.this.mLock) {
                try {
                    Integer num = (Integer) ((ArrayMap) BroadcastRadioServiceImpl.this.mServiceNameToModuleIdMap).get(str);
                    if (num == null) {
                        num = Integer.valueOf(BroadcastRadioServiceImpl.this.mNextModuleId);
                        z = true;
                    } else {
                        z = false;
                    }
                    RadioModule tryLoadingModule = RadioModule.tryLoadingModule(num.intValue(), iBinder, str);
                    if (tryLoadingModule == null) {
                        Slogf.w("BcRadioAidlSrv", "No module %s with id %d (HAL AIDL)", str, num);
                        return;
                    }
                    if (BroadcastRadioServiceImpl.DEBUG) {
                        Slogf.d("BcRadioAidlSrv", "Loaded broadcast radio module %s with id %d (HAL AIDL)", str, num);
                    }
                    RadioModule radioModule = (RadioModule) BroadcastRadioServiceImpl.this.mModules.get(num.intValue());
                    BroadcastRadioServiceImpl.this.mModules.put(num.intValue(), tryLoadingModule);
                    if (radioModule != null) {
                        radioModule.closeSessions();
                    }
                    if (z) {
                        ((ArrayMap) BroadcastRadioServiceImpl.this.mServiceNameToModuleIdMap).put(str, num);
                        BroadcastRadioServiceImpl.this.mNextModuleId++;
                    }
                    try {
                        ((IBroadcastRadio.Stub.Proxy) tryLoadingModule.mService).mRemote.linkToDeath(BroadcastRadioServiceImpl.this.new BroadcastRadioDeathRecipient(num.intValue()), num.intValue());
                    } catch (RemoteException unused) {
                        Slogf.w("BcRadioAidlSrv", "Service has already died, so remove its entry from mModules.");
                        BroadcastRadioServiceImpl.this.mModules.remove(num.intValue());
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    };
    public int mNextModuleId = 0;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BroadcastRadioDeathRecipient implements IBinder.DeathRecipient {
        public final int mModuleId;

        public BroadcastRadioDeathRecipient(int i) {
            this.mModuleId = i;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            Slogf.i("BcRadioAidlSrv", "ServiceDied for module id %d", Integer.valueOf(this.mModuleId));
            synchronized (BroadcastRadioServiceImpl.this.mLock) {
                try {
                    RadioModule radioModule = (RadioModule) BroadcastRadioServiceImpl.this.mModules.removeReturnOld(this.mModuleId);
                    if (radioModule != null) {
                        radioModule.closeSessions();
                    }
                    Iterator it = ((ArrayMap) BroadcastRadioServiceImpl.this.mServiceNameToModuleIdMap).entrySet().iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        Map.Entry entry = (Map.Entry) it.next();
                        if (((Integer) entry.getValue()).intValue() == this.mModuleId) {
                            Slogf.w("BcRadioAidlSrv", "Service %s died, removed RadioModule with ID %d", entry.getKey(), Integer.valueOf(this.mModuleId));
                            break;
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.server.broadcastradio.aidl.BroadcastRadioServiceImpl$1] */
    public BroadcastRadioServiceImpl(ArrayList arrayList) {
        if (DEBUG) {
            Slogf.d("BcRadioAidlSrv", "Initializing BroadcastRadioServiceImpl %s", IBroadcastRadio.DESCRIPTOR);
        }
        for (int i = 0; i < arrayList.size(); i++) {
            try {
                ServiceManager.registerForNotifications((String) arrayList.get(i), this.mServiceListener);
            } catch (RemoteException e) {
                Slogf.e("BcRadioAidlSrv", e, "failed to register for service notifications for service %s", arrayList.get(i));
            }
        }
    }
}
