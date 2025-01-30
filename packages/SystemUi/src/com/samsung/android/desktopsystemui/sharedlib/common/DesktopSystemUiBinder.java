package com.samsung.android.desktopsystemui.sharedlib.common;

import android.app.IWallpaperManager;
import android.app.IWallpaperManagerCallback;
import android.app.SemWallpaperColors;
import android.app.WallpaperColors;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.Log;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.desktopsystemui.sharedlib.common.DesktopSystemUiBinder;
import com.samsung.android.desktopsystemui.sharedlib.common.IDesktopBar;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class DesktopSystemUiBinder {
    private static final String SYSTEMUI_DESKTOP_PACKAGE = "com.sec.android.dexsystemui";
    private static final String SYSTEMUI_DESKTOP_SERVICE = "com.sec.android.dexsystemui.services.desktopbar.DesktopBarService";
    private static final String TAG = "DesktopSystemUIBinder";
    private Context mContext;
    private IDesktopBar mDesktopBar;
    private IDesktopBarCallback mIDesktopCallback;
    private final IWallpaperManager mWallpaperService;
    private ArrayList<Callback> mCallbacks = new ArrayList<>();
    private final ServiceConnection mServiceConnection = new ServiceConnectionC45541();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class KeyguardWallpaperController extends IWallpaperManagerCallback.Stub {
        private KeyguardWallpaperController() {
        }

        public void onSemWallpaperChanged(int i, int i2, Bundle bundle) {
            SuggestionsAdapter$$ExternalSyntheticOutline0.m12m("onSemWallpaperChanged type=", i, " which=", i2, DesktopSystemUiBinder.TAG);
            if ((i2 & 8) == 0 || (i2 & 2) == 0) {
                Log.i(DesktopSystemUiBinder.TAG, "onSemWallpaperChanged - not for dex keyguard wallpaper");
            } else if (DesktopSystemUiBinder.this.mDesktopBar != null) {
                try {
                    DesktopSystemUiBinder.this.mDesktopBar.onKeyguardWallpaperChanged();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        public /* synthetic */ KeyguardWallpaperController(DesktopSystemUiBinder desktopSystemUiBinder, ServiceConnectionC45541 serviceConnectionC45541) {
            this();
        }

        public void onSemMultipackApplied(int i) {
        }

        public void onWallpaperChanged() {
        }

        public void onSemWallpaperColorsAnalysisRequested(int i, int i2) {
        }

        public void onSemBackupStatusChanged(int i, int i2, int i3) {
        }

        public void onSemWallpaperColorsChanged(SemWallpaperColors semWallpaperColors, int i, int i2) {
        }

        public void onWallpaperColorsChanged(WallpaperColors wallpaperColors, int i, int i2) {
        }
    }

    public DesktopSystemUiBinder(Context context) {
        this.mContext = context;
        IWallpaperManager asInterface = IWallpaperManager.Stub.asInterface(ServiceManager.getService("wallpaper"));
        this.mWallpaperService = asInterface;
        if (asInterface != null) {
            try {
                asInterface.setLockWallpaperCallback(new KeyguardWallpaperController(this, null));
            } catch (RemoteException unused) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isDesktopMode() {
        int enabled = ((SemDesktopModeManager) this.mContext.getSystemService(SemDesktopModeManager.class)).getDesktopModeState().getEnabled();
        return enabled == 3 || enabled == 4;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void start() {
        start(this.mIDesktopCallback);
    }

    public boolean isDesktopBarConnected() {
        return this.mDesktopBar != null;
    }

    public void notifyPrivacyItemsChanged(boolean z) {
        if (this.mDesktopBar != null) {
            try {
                Log.i(TAG, "notifyPrivacyItemsChanged - visible = " + z);
                this.mDesktopBar.notifyPrivacyItemsChanged(z);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void onDismiss() {
        if (this.mDesktopBar != null) {
            try {
                Log.i(TAG, "onDismiss() keyguard");
                this.mDesktopBar.onDismiss();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void onKeyguardWallpaperChanged() {
        if (this.mDesktopBar != null) {
            try {
                Log.i(TAG, "onKeyguardWallpaperChanged()");
                this.mDesktopBar.onKeyguardWallpaperChanged();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void onLockout(boolean z, Bundle bundle) {
        if (this.mDesktopBar != null) {
            try {
                Log.i(TAG, "onLockout() lockout=" + z);
                this.mDesktopBar.setLockout(z, bundle);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void onShow(int i) {
        if (this.mDesktopBar != null) {
            try {
                Log.i(TAG, "onShow() keyguard.");
                this.mDesktopBar.onShow(i);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void onUpdate(int i, Bundle bundle) {
        if (this.mDesktopBar != null) {
            try {
                Log.i(TAG, "onUpdate() type=" + i);
                this.mDesktopBar.onUpdate(i, bundle);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void registerCallback(Callback callback) {
        this.mCallbacks.add(callback);
    }

    public void setAirplaneMode(boolean z, int i) {
        IDesktopBar iDesktopBar = this.mDesktopBar;
        if (iDesktopBar != null) {
            try {
                iDesktopBar.setAirplaneMode(z, i);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void setBtTetherIcon(boolean z, int i) {
        IDesktopBar iDesktopBar = this.mDesktopBar;
        if (iDesktopBar != null) {
            try {
                iDesktopBar.setBtTetherIcon(z, i);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void setConnectedDeviceListForGroup(Bundle bundle) {
        if (this.mDesktopBar != null) {
            try {
                Log.i(TAG, "setConnectedDeviceListForGroup");
                this.mDesktopBar.setConnectedDeviceListForGroup(bundle);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void setMPTCPIcon(boolean z, int i, int i2, int i3) {
        IDesktopBar iDesktopBar = this.mDesktopBar;
        if (iDesktopBar != null) {
            try {
                iDesktopBar.setMPTCPIcon(z, i, i2, i3);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void setMobileIcon(Bundle bundle) {
        IDesktopBar iDesktopBar = this.mDesktopBar;
        if (iDesktopBar != null) {
            try {
                iDesktopBar.setMobileIcon(bundle);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void setOccluded(boolean z) {
        if (this.mDesktopBar != null) {
            try {
                Log.i(TAG, "setOccluded() occluded=" + z);
                this.mDesktopBar.setOccluded(z);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void setSubs() {
        if (this.mDesktopBar != null) {
            try {
                Log.i(TAG, "setSubs");
                this.mDesktopBar.setSubs();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void setWifiIcon(boolean z, int i, int i2) {
        IDesktopBar iDesktopBar = this.mDesktopBar;
        if (iDesktopBar != null) {
            try {
                iDesktopBar.setWifiIcon(z, i, i2);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        Log.i(TAG, "stop");
        if (this.mDesktopBar != null) {
            try {
                Log.i(TAG, "mDesktopBar is stopped");
                this.mDesktopBar.stop();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        this.mDesktopBar = null;
        ServiceConnection serviceConnection = this.mServiceConnection;
        if (serviceConnection != null) {
            try {
                this.mContext.unbindService(serviceConnection);
            } catch (IllegalArgumentException e2) {
                Log.w(TAG, "SystemUIDesktop unbindService Exception: " + e2);
                e2.printStackTrace();
            }
        }
    }

    public void unregisterCallback(Callback callback) {
        this.mCallbacks.remove(callback);
    }

    public void start(IDesktopBarCallback iDesktopBarCallback) {
        Log.i(TAG, NetworkAnalyticsConstants.DataPoints.OPEN_TIME);
        this.mContext.bindServiceAsUser(new Intent().setComponent(new ComponentName(SYSTEMUI_DESKTOP_PACKAGE, SYSTEMUI_DESKTOP_SERVICE)), this.mServiceConnection, 33554433, UserHandle.SYSTEM);
        this.mIDesktopCallback = iDesktopBarCallback;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.samsung.android.desktopsystemui.sharedlib.common.DesktopSystemUiBinder$1 */
    public class ServiceConnectionC45541 implements ServiceConnection {
        public ServiceConnectionC45541() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onServiceConnected$0() {
            Log.w(DesktopSystemUiBinder.TAG, "SystemUIDesktop is died");
            DesktopSystemUiBinder.this.stop();
            if (DesktopSystemUiBinder.this.isDesktopMode()) {
                Log.i(DesktopSystemUiBinder.TAG, "DeathRecipient-Reconnect");
                DesktopSystemUiBinder.this.start();
            }
        }

        @Override // android.content.ServiceConnection
        public void onBindingDied(ComponentName componentName) {
            Log.e(DesktopSystemUiBinder.TAG, "onBindingDied name=" + componentName);
            if (DesktopSystemUiBinder.this.mDesktopBar != null) {
                DesktopSystemUiBinder.this.stop();
            } else if (DesktopSystemUiBinder.this.isDesktopMode()) {
                Log.e(DesktopSystemUiBinder.TAG, "onBindingDied-Reconnect");
                DesktopSystemUiBinder.this.start();
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            if (iBinder != null) {
                DesktopSystemUiBinder.this.mDesktopBar = IDesktopBar.Stub.asInterface(iBinder);
                if (DesktopSystemUiBinder.this.mDesktopBar != null) {
                    try {
                        DesktopSystemUiBinder.this.mDesktopBar.asBinder().linkToDeath(new IBinder.DeathRecipient() { // from class: com.samsung.android.desktopsystemui.sharedlib.common.DesktopSystemUiBinder$1$$ExternalSyntheticLambda0
                            @Override // android.os.IBinder.DeathRecipient
                            public final void binderDied() {
                                DesktopSystemUiBinder.ServiceConnectionC45541.this.lambda$onServiceConnected$0();
                            }
                        }, 0);
                        DesktopSystemUiBinder.this.mDesktopBar.setDesktopBarCallback(DesktopSystemUiBinder.this.mIDesktopCallback);
                        try {
                            Log.i(DesktopSystemUiBinder.TAG, "Start mDesktopBar");
                            DesktopSystemUiBinder.this.mDesktopBar.start();
                        } catch (RemoteException e) {
                            Log.w(DesktopSystemUiBinder.TAG, "SystemUIDesktop mDesktopBar.start RemoteException " + e.toString());
                            e.printStackTrace();
                        }
                    } catch (RemoteException e2) {
                        Log.w(DesktopSystemUiBinder.TAG, "SystemUIDesktop linkToDeath RemoteException " + e2.toString());
                        e2.printStackTrace();
                    }
                }
                Iterator it = DesktopSystemUiBinder.this.mCallbacks.iterator();
                while (it.hasNext()) {
                    ((Callback) it.next()).onServiceConnected();
                }
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i(DesktopSystemUiBinder.TAG, "onServiceDisconnected");
            DesktopSystemUiBinder.this.stop();
            Iterator it = DesktopSystemUiBinder.this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((Callback) it.next()).onServiceDisconnected();
            }
        }

        @Override // android.content.ServiceConnection
        public void onNullBinding(ComponentName componentName) {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Callback {
        default void onServiceConnected() {
        }

        default void onServiceDisconnected() {
        }
    }
}
