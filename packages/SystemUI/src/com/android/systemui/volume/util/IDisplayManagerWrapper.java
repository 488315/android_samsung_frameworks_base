package com.android.systemui.volume.util;

import android.content.Context;
import android.hardware.display.IDisplayManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.provider.Settings;
import android.util.Log;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.hardware.display.IRefreshRateToken;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class IDisplayManagerWrapper {
    public final Context context;
    public IRefreshRateToken iRefreshRateMinLimitToken;
    public IDisplayManager iDisplayManager = IDisplayManager.Stub.asInterface(ServiceManager.getService("display"));
    public final IBinder refreshRateToken = new Binder();
    public final Object lock = new Object();
    public final IDisplayManagerWrapper$refreshRateLimitOnRunnable$1 refreshRateLimitOnRunnable = new Runnable() { // from class: com.android.systemui.volume.util.IDisplayManagerWrapper$refreshRateLimitOnRunnable$1
        @Override // java.lang.Runnable
        public final void run() {
            IRefreshRateToken acquireRefreshRateMinLimitToken;
            IDisplayManagerWrapper iDisplayManagerWrapper = IDisplayManagerWrapper.this;
            synchronized (iDisplayManagerWrapper.lock) {
                try {
                    int intForUser = Settings.Secure.getIntForUser(iDisplayManagerWrapper.context.getContentResolver(), SettingsHelper.INDEX_REFRESH_RATE_MODE, 1, -2);
                    if ((intForUser == 1 || intForUser == 2) && iDisplayManagerWrapper.iRefreshRateMinLimitToken == null) {
                        IDisplayManager iDisplayManager = iDisplayManagerWrapper.iDisplayManager;
                        if (iDisplayManager == null) {
                            iDisplayManager = IDisplayManager.Stub.asInterface(ServiceManager.getService("display"));
                        }
                        iDisplayManagerWrapper.iDisplayManager = iDisplayManager;
                        if (iDisplayManager != null) {
                            try {
                                acquireRefreshRateMinLimitToken = iDisplayManager.acquireRefreshRateMinLimitToken(iDisplayManagerWrapper.refreshRateToken, 60, "VolumePanelView");
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        } else {
                            acquireRefreshRateMinLimitToken = null;
                        }
                        iDisplayManagerWrapper.iRefreshRateMinLimitToken = acquireRefreshRateMinLimitToken;
                        Log.d("VolumePanelView", "enableRefreshRateMinLimit");
                        if (iDisplayManagerWrapper.iRefreshRateMinLimitToken == null) {
                            Log.w("VolumePanelView", "enableRefreshRateMinLimit failed");
                        }
                    }
                    Unit unit = Unit.INSTANCE;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    };
    public final IDisplayManagerWrapper$refreshRateLimitOffRunnable$1 refreshRateLimitOffRunnable = new Runnable() { // from class: com.android.systemui.volume.util.IDisplayManagerWrapper$refreshRateLimitOffRunnable$1
        @Override // java.lang.Runnable
        public final void run() {
            IDisplayManagerWrapper iDisplayManagerWrapper = IDisplayManagerWrapper.this;
            synchronized (iDisplayManagerWrapper.lock) {
                IRefreshRateToken iRefreshRateToken = iDisplayManagerWrapper.iRefreshRateMinLimitToken;
                if (iRefreshRateToken != null) {
                    try {
                        iRefreshRateToken.release();
                        Log.d("VolumePanelView", "disableRefreshRateMinLimit");
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    iDisplayManagerWrapper.iRefreshRateMinLimitToken = null;
                    Unit unit = Unit.INSTANCE;
                }
            }
        }
    };

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r1v6, types: [com.android.systemui.volume.util.IDisplayManagerWrapper$refreshRateLimitOnRunnable$1] */
    /* JADX WARN: Type inference failed for: r1v7, types: [com.android.systemui.volume.util.IDisplayManagerWrapper$refreshRateLimitOffRunnable$1] */
    public IDisplayManagerWrapper(Context context) {
        this.context = context;
    }
}
