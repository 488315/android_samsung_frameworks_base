package com.android.server.remoteappmode;

import android.content.Context;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.ArrayMap;
import com.samsung.android.remoteappmode.ISecureAppChangedListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes3.dex */
public class SecureAppNotifier {
    public Context mContext;
    public final Map mSecureAppChangedListeners = new ArrayMap();

    public SecureAppNotifier(Context context) {
        this.mContext = context;
    }

    public boolean registerSecureAppChangedListener(ISecureAppChangedListener iSecureAppChangedListener, String str) {
        synchronized (this.mSecureAppChangedListeners) {
            IBinder asBinder = iSecureAppChangedListener.asBinder();
            try {
                if (str.length() > 100) {
                    str = str.substring(0, 100);
                }
                SecureAppChangedListenerInfo secureAppChangedListenerInfo = new SecureAppChangedListenerInfo(iSecureAppChangedListener, str, Binder.getCallingPid(), Binder.getCallingUid());
                asBinder.linkToDeath(secureAppChangedListenerInfo, 0);
                this.mSecureAppChangedListeners.put(asBinder, secureAppChangedListenerInfo);
            } catch (RemoteException unused) {
                return false;
            }
        }
        return true;
    }

    public boolean unregisterSecureAppChangedListener(ISecureAppChangedListener iSecureAppChangedListener) {
        synchronized (this.mSecureAppChangedListeners) {
            SecureAppChangedListenerInfo secureAppChangedListenerInfo = (SecureAppChangedListenerInfo) this.mSecureAppChangedListeners.remove(iSecureAppChangedListener.asBinder());
            if (secureAppChangedListenerInfo == null) {
                return false;
            }
            secureAppChangedListenerInfo.listener.asBinder().unlinkToDeath(secureAppChangedListenerInfo, 0);
            return true;
        }
    }

    public void notifySecuredAppLaunched(int i, String str) {
        ArrayList arrayList;
        synchronized (this.mSecureAppChangedListeners) {
            arrayList = new ArrayList(this.mSecureAppChangedListeners.values());
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            try {
                ((SecureAppChangedListenerInfo) it.next()).listener.onSecuredAppLaunched(i, str);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void notifySecuredAppRemoved(int i, String str) {
        ArrayList arrayList;
        synchronized (this.mSecureAppChangedListeners) {
            arrayList = new ArrayList(this.mSecureAppChangedListeners.values());
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            try {
                ((SecureAppChangedListenerInfo) it.next()).listener.onSecuredAppRemoved(i, str);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void releaseAllListeners() {
        synchronized (this.mSecureAppChangedListeners) {
            for (IBinder iBinder : this.mSecureAppChangedListeners.keySet()) {
                try {
                    SecureAppChangedListenerInfo secureAppChangedListenerInfo = (SecureAppChangedListenerInfo) this.mSecureAppChangedListeners.get(iBinder);
                    Objects.requireNonNull(secureAppChangedListenerInfo);
                    iBinder.unlinkToDeath(secureAppChangedListenerInfo, 0);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
            this.mSecureAppChangedListeners.clear();
        }
    }

    public class SecureAppChangedListenerInfo extends ListenerInfo {
        public final ISecureAppChangedListener listener;

        public SecureAppChangedListenerInfo(ISecureAppChangedListener iSecureAppChangedListener, String str, int i, int i2) {
            super(str, i, i2);
            this.listener = iSecureAppChangedListener;
        }

        @Override // com.android.server.remoteappmode.ListenerInfo, android.os.IBinder.DeathRecipient
        public void binderDied() {
            super.binderDied();
            synchronized (SecureAppNotifier.this.mSecureAppChangedListeners) {
                SecureAppNotifier.this.mSecureAppChangedListeners.remove(this.listener.asBinder());
            }
            this.listener.asBinder().unlinkToDeath(this, 0);
        }
    }
}
