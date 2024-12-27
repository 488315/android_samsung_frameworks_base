package com.android.systemui.qp;

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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SubscreenWifiController implements SubscreenQSControllerContract$Presenter {
    public static Context mContext;
    public static SubscreenWifiController sInstance;
    public TileReceiver mWifiReceiver;
    public boolean mWifiState;
    public SubscreenQSControllerContract$View mWifiView;
    public TileReceiver mWifiViewReceiver;
    public final WifiManager mWifiAdapter = (WifiManager) mContext.getSystemService(ImsProfile.PDN_WIFI);
    public final NetworkController mNetworkController = (NetworkController) Dependency.sDependency.getDependencyInner(NetworkController.class);
    public final WifiSignalCallback mSignalCallback = new WifiSignalCallback(this, 0);

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class WifiSignalCallback implements SignalCallback {
        public /* synthetic */ WifiSignalCallback(SubscreenWifiController subscreenWifiController, int i) {
            this();
        }

        @Override // com.android.systemui.statusbar.connectivity.SignalCallback
        public final void setWifiIndicators(WifiIndicators wifiIndicators) {
            StringBuilder sb = new StringBuilder("setWifiIndicators enabled=");
            boolean z = wifiIndicators.enabled;
            ActionBarContextView$$ExternalSyntheticOutline0.m(sb, z, "SubscreenWifiController");
            SubscreenWifiController subscreenWifiController = SubscreenWifiController.this;
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

    @Override // com.android.systemui.qp.SubscreenQSControllerContract$Presenter
    public final void registerReceiver(boolean z) {
        IntentFilter m = AppCompatDelegateImpl$$ExternalSyntheticOutline0.m("WIFI_STATE_CHANGE");
        if (z) {
            this.mWifiViewReceiver = new TileReceiver();
        } else {
            this.mWifiReceiver = new TileReceiver();
        }
        Log.d("SubscreenWifiController", "SWC registerWifiReceiver mWifiViewReceiver: " + this.mWifiViewReceiver + "mWifiReceiver: " + this.mWifiReceiver);
        ((BroadcastDispatcher) Dependency.sDependency.getDependencyInner(BroadcastDispatcher.class)).registerReceiver(z ? this.mWifiViewReceiver : this.mWifiReceiver, m, null, UserHandle.ALL, 2, "com.samsung.systemui.permission.WIFI_STATE_CHANGE");
        ((NetworkControllerImpl) this.mNetworkController).addCallback(this.mSignalCallback);
    }

    @Override // com.android.systemui.qp.SubscreenQSControllerContract$Presenter
    public final void unRegisterReceiver(boolean z) {
        if (z && this.mWifiViewReceiver != null) {
            Log.d("SubscreenWifiController", "SWC unRegisterReceiver mWifiViewReceiver: " + this.mWifiViewReceiver);
            ((BroadcastDispatcher) Dependency.sDependency.getDependencyInner(BroadcastDispatcher.class)).unregisterReceiver(this.mWifiViewReceiver);
            this.mWifiViewReceiver = null;
        } else if (this.mWifiReceiver != null) {
            Log.d("SubscreenWifiController", "SWC unRegisterReceiver mWifiReceiver: " + this.mWifiReceiver);
            ((BroadcastDispatcher) Dependency.sDependency.getDependencyInner(BroadcastDispatcher.class)).unregisterReceiver(this.mWifiReceiver);
            this.mWifiReceiver = null;
        }
        WifiSignalCallback wifiSignalCallback = this.mSignalCallback;
        if (wifiSignalCallback != null) {
            ((NetworkControllerImpl) this.mNetworkController).removeCallback(wifiSignalCallback);
        }
    }
}
