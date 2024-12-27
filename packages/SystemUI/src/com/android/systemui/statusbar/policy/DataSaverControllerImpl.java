package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.net.NetworkPolicyManager;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import com.android.systemui.statusbar.policy.DataSaverController;
import java.util.ArrayList;

public final class DataSaverControllerImpl implements DataSaverController {
    public final NetworkPolicyManager mPolicyManager;
    public final Handler mHandler = new Handler(Looper.getMainLooper());
    public final ArrayList mListeners = new ArrayList();
    public int mIsDataSavingOn = -1;
    public final AnonymousClass1 mPolicyListener = new AnonymousClass1();

    /* renamed from: com.android.systemui.statusbar.policy.DataSaverControllerImpl$1, reason: invalid class name */
    public final class AnonymousClass1 extends NetworkPolicyManager.Listener {
        public AnonymousClass1() {
        }

        public final void onRestrictBackgroundChanged(final boolean z) {
            DataSaverControllerImpl.this.mHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.policy.DataSaverControllerImpl.1.1
                @Override // java.lang.Runnable
                public final void run() {
                    ArrayList arrayList;
                    DataSaverControllerImpl dataSaverControllerImpl = DataSaverControllerImpl.this;
                    boolean z2 = z;
                    dataSaverControllerImpl.mIsDataSavingOn = z2 ? 1 : 0;
                    synchronized (dataSaverControllerImpl.mListeners) {
                        arrayList = new ArrayList(dataSaverControllerImpl.mListeners);
                    }
                    for (int i = 0; i < arrayList.size(); i++) {
                        ((DataSaverController.Listener) arrayList.get(i)).onDataSaverChanged(z2);
                    }
                }
            });
        }
    }

    public DataSaverControllerImpl(Context context) {
        this.mPolicyManager = NetworkPolicyManager.from(context);
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Object obj) {
        DataSaverController.Listener listener = (DataSaverController.Listener) obj;
        synchronized (this.mListeners) {
            try {
                this.mListeners.add(listener);
                if (this.mListeners.size() == 1) {
                    this.mPolicyManager.registerListener(this.mPolicyListener);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        listener.onDataSaverChanged(isDataSaverEnabled$1());
    }

    public final boolean isDataSaverEnabled$1() {
        if (this.mIsDataSavingOn < 0) {
            this.mIsDataSavingOn = this.mPolicyManager.getRestrictBackground() ? 1 : 0;
        }
        return this.mIsDataSavingOn > 0;
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        DataSaverController.Listener listener = (DataSaverController.Listener) obj;
        synchronized (this.mListeners) {
            try {
                this.mListeners.remove(listener);
                if (this.mListeners.size() == 0) {
                    this.mPolicyManager.unregisterListener(this.mPolicyListener);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setDataSaverEnabled(boolean z) {
        this.mPolicyManager.setRestrictBackground(z);
        try {
            this.mPolicyListener.onRestrictBackgroundChanged(z);
        } catch (RemoteException unused) {
        }
    }
}
