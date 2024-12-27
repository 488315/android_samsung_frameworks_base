package com.android.systemui.qp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.UserHandle;
import android.util.Log;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.util.SettingsHelper;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SubscreenAirplaneController implements SubscreenQSControllerContract$Presenter {
    public static Context mContext;
    public static SubscreenAirplaneController sInstance;
    public TileReceiver mAirplaneReceiver;
    public SubscreenQSControllerContract$View mAirplaneView;
    public TileReceiver mAirplaneViewReceiver;
    public final ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext.getSystemService("connectivity");

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class TileReceiver extends BroadcastReceiver {
        public TileReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            Log.d("SubscreenAirplaneController", "SAC onReceive action: " + intent.getAction());
            if ("AIRPLANE_MODE_CHANGE".equals(intent.getAction())) {
                SubscreenAirplaneController.this.getClass();
                boolean z = !((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isAirplaneModeOn();
                SubscreenAirplaneController.this.mConnectivityManager.setAirplaneMode(z);
                SubscreenAirplaneController.this.mAirplaneView.updateView(z);
            }
        }
    }

    private SubscreenAirplaneController() {
    }

    public static SubscreenAirplaneController getInstance(Context context) {
        if (sInstance == null) {
            mContext = context;
            sInstance = new SubscreenAirplaneController();
        }
        return sInstance;
    }

    @Override // com.android.systemui.qp.SubscreenQSControllerContract$Presenter
    public final void registerReceiver(boolean z) {
        IntentFilter m = AppCompatDelegateImpl$$ExternalSyntheticOutline0.m("AIRPLANE_MODE_CHANGE");
        if (z) {
            this.mAirplaneViewReceiver = new TileReceiver();
        } else {
            this.mAirplaneReceiver = new TileReceiver();
        }
        Log.d("SubscreenAirplaneController", "SAC registerAirplaneReceiver mAirplaneViewReceiver: " + this.mAirplaneViewReceiver + "mAirplaneReceiver: " + this.mAirplaneReceiver);
        ((BroadcastDispatcher) Dependency.sDependency.getDependencyInner(BroadcastDispatcher.class)).registerReceiver(z ? this.mAirplaneViewReceiver : this.mAirplaneReceiver, m, null, UserHandle.ALL, 2, "com.samsung.systemui.permission.AIRPLANE_STATE_CHANGE");
    }

    @Override // com.android.systemui.qp.SubscreenQSControllerContract$Presenter
    public final void unRegisterReceiver(boolean z) {
        if (z && this.mAirplaneViewReceiver != null) {
            Log.d("SubscreenAirplaneController", "SAC unRegisterReceiver mAirplaneViewReceiver: " + this.mAirplaneViewReceiver);
            ((BroadcastDispatcher) Dependency.sDependency.getDependencyInner(BroadcastDispatcher.class)).unregisterReceiver(this.mAirplaneViewReceiver);
            this.mAirplaneViewReceiver = null;
            return;
        }
        if (this.mAirplaneReceiver != null) {
            Log.d("SubscreenAirplaneController", "SAC unRegisterReceiver mAirplaneReceiver: " + this.mAirplaneReceiver);
            ((BroadcastDispatcher) Dependency.sDependency.getDependencyInner(BroadcastDispatcher.class)).unregisterReceiver(this.mAirplaneReceiver);
            this.mAirplaneReceiver = null;
        }
    }
}
