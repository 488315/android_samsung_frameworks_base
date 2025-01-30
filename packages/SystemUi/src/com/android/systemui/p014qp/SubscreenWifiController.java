package com.android.systemui.p014qp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.UserHandle;
import android.util.Log;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.statusbar.connectivity.NetworkController;
import com.android.systemui.statusbar.connectivity.NetworkControllerImpl;
import com.android.systemui.statusbar.connectivity.SignalCallback;
import com.android.systemui.statusbar.connectivity.WifiIndicators;
import com.sec.ims.settings.ImsProfile;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SubscreenWifiController implements SubscreenQSControllerContract$Presenter {
    public static Context mContext;
    public static SubscreenWifiController sInstance;
    public TileReceiver mWifiReceiver;
    public boolean mWifiState;
    public SubscreenQSControllerContract$View mWifiView;
    public TileReceiver mWifiViewReceiver;
    public final WifiManager mWifiAdapter = (WifiManager) mContext.getSystemService(ImsProfile.PDN_WIFI);
    public final NetworkController mNetworkController = (NetworkController) Dependency.get(NetworkController.class);
    public final WifiSignalCallback mSignalCallback = new WifiSignalCallback(this, 0);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class TileReceiver extends BroadcastReceiver {
        public TileReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (intent == null) {
                return;
            }
            Log.d("SubscreenWifiController", "SWC onReceive action: " + intent.getAction());
            if (intent.getAction().equals("WIFI_STATE_CHANGE")) {
                boolean z = !intent.getBooleanExtra("wifi_state", false);
                SubscreenWifiController.this.mWifiAdapter.setWifiEnabled(z);
                SubscreenWifiController.this.mWifiView.updateView(z);
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class WifiSignalCallback implements SignalCallback {
        public /* synthetic */ WifiSignalCallback(SubscreenWifiController subscreenWifiController, int i) {
            this();
        }

        @Override // com.android.systemui.statusbar.connectivity.SignalCallback
        public final void setWifiIndicators(WifiIndicators wifiIndicators) {
            ActionBarContextView$$ExternalSyntheticOutline0.m9m(new StringBuilder("setWifiIndicators enabled="), wifiIndicators.enabled, "SubscreenWifiController");
            SubscreenWifiController subscreenWifiController = SubscreenWifiController.this;
            boolean z = wifiIndicators.enabled;
            subscreenWifiController.mWifiState = z;
            SubscreenQSControllerContract$View subscreenQSControllerContract$View = subscreenWifiController.mWifiView;
            if (subscreenQSControllerContract$View != null) {
                subscreenQSControllerContract$View.updateView(z);
            }
        }

        private WifiSignalCallback() {
        }
    }

    private SubscreenWifiController() {
    }

    public static SubscreenWifiController getInstance(Context context) {
        if (sInstance == null) {
            mContext = context;
            sInstance = new SubscreenWifiController();
        }
        return sInstance;
    }

    @Override // com.android.systemui.p014qp.SubscreenQSControllerContract$Presenter
    public final void registerReceiver(boolean z) {
        IntentFilter m5m = AppCompatDelegateImpl$$ExternalSyntheticOutline0.m5m("WIFI_STATE_CHANGE");
        if (z) {
            this.mWifiViewReceiver = new TileReceiver();
        } else {
            this.mWifiReceiver = new TileReceiver();
        }
        Log.d("SubscreenWifiController", "SWC registerWifiReceiver mWifiViewReceiver: " + this.mWifiViewReceiver + "mWifiReceiver: " + this.mWifiReceiver);
        ((BroadcastDispatcher) Dependency.get(BroadcastDispatcher.class)).registerReceiver(z ? this.mWifiViewReceiver : this.mWifiReceiver, m5m, null, UserHandle.ALL, 2, "com.samsung.systemui.permission.WIFI_STATE_CHANGE");
        ((NetworkControllerImpl) this.mNetworkController).addCallback(this.mSignalCallback);
    }

    public final void setListener(SubscreenQSControllerContract$View subscreenQSControllerContract$View) {
        this.mWifiView = subscreenQSControllerContract$View;
        this.mWifiViewReceiver = new TileReceiver();
    }

    @Override // com.android.systemui.p014qp.SubscreenQSControllerContract$Presenter
    public final void unRegisterReceiver(boolean z) {
        if (z && this.mWifiViewReceiver != null) {
            Log.d("SubscreenWifiController", "SWC unRegisterReceiver mWifiViewReceiver: " + this.mWifiViewReceiver);
            ((BroadcastDispatcher) Dependency.get(BroadcastDispatcher.class)).unregisterReceiver(this.mWifiViewReceiver);
            this.mWifiViewReceiver = null;
        } else if (this.mWifiReceiver != null) {
            Log.d("SubscreenWifiController", "SWC unRegisterReceiver mWifiReceiver: " + this.mWifiReceiver);
            ((BroadcastDispatcher) Dependency.get(BroadcastDispatcher.class)).unregisterReceiver(this.mWifiReceiver);
            this.mWifiReceiver = null;
        }
        WifiSignalCallback wifiSignalCallback = this.mSignalCallback;
        if (wifiSignalCallback != null) {
            ((NetworkControllerImpl) this.mNetworkController).removeCallback(wifiSignalCallback);
        }
    }
}
