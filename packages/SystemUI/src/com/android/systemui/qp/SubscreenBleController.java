package com.android.systemui.qp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;
import android.util.Log;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.statusbar.policy.BluetoothController;

public final class SubscreenBleController implements SubscreenQSControllerContract$Presenter {
    public static Context mContext;
    public static SubscreenBleController sInstance;
    public TileReceiver mBleReceiver;
    public SubscreenQSControllerContract$View mBleView;
    public TileReceiver mBleViewReceiver;
    public final AnonymousClass1 mCallback = new BluetoothController.Callback() { // from class: com.android.systemui.qp.SubscreenBleController.1
        @Override // com.android.systemui.statusbar.policy.BluetoothController.Callback
        public final void onBluetoothStateChange(boolean z) {
            SubscreenBleController subscreenBleController = SubscreenBleController.this;
            int bluetoothState = subscreenBleController.mBluetoothController.getBluetoothState();
            Log.d("SubscreenBleController", "onBluetoothStateChange = " + z + " state: " + bluetoothState);
            SubscreenQSControllerContract$View subscreenQSControllerContract$View = subscreenBleController.mBleView;
            if (subscreenQSControllerContract$View != null) {
                if (bluetoothState == 12 || bluetoothState == 10) {
                    subscreenQSControllerContract$View.updateView(z);
                }
            }
        }

        @Override // com.android.systemui.statusbar.policy.BluetoothController.Callback
        public final void onBluetoothDevicesChanged() {
        }
    };
    public final BluetoothController mBluetoothController = (BluetoothController) Dependency.sDependency.getDependencyInner(BluetoothController.class);

    public final class TileReceiver extends BroadcastReceiver {
        public TileReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (intent == null) {
                return;
            }
            Log.d("SubscreenBleController", "SBC onReceive action: " + intent.getAction());
            if ("BLUETOOTH_STATE_CHANGE".equals(intent.getAction())) {
                boolean z = !SubscreenBleController.this.isEnabled();
                SubscreenBleController.this.mBluetoothController.setBluetoothEnabled(z);
                SubscreenBleController.this.mBleView.updateView(z);
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.qp.SubscreenBleController$1] */
    private SubscreenBleController() {
    }

    public static SubscreenBleController getInstance(Context context) {
        if (sInstance == null) {
            mContext = context;
            sInstance = new SubscreenBleController();
        }
        return sInstance;
    }

    public final boolean isEnabled() {
        boolean isBluetoothEnabled = this.mBluetoothController.isBluetoothEnabled();
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("SBC isEnabled enabled: ", "SubscreenBleController", isBluetoothEnabled);
        return isBluetoothEnabled;
    }

    @Override // com.android.systemui.qp.SubscreenQSControllerContract$Presenter
    public final void registerReceiver(boolean z) {
        IntentFilter m = AppCompatDelegateImpl$$ExternalSyntheticOutline0.m("BLUETOOTH_STATE_CHANGE");
        if (z) {
            this.mBleViewReceiver = new TileReceiver();
        } else {
            this.mBleReceiver = new TileReceiver();
        }
        Log.d("SubscreenBleController", "SBC registerBleReceiver mBleViewReceiver: " + this.mBleViewReceiver + "mBleReceiver: " + this.mBleReceiver);
        ((BroadcastDispatcher) Dependency.sDependency.getDependencyInner(BroadcastDispatcher.class)).registerReceiver(z ? this.mBleViewReceiver : this.mBleReceiver, m, null, UserHandle.ALL, 2, "com.samsung.systemui.permission.BLUETOOTH_STATE_CHANGE");
        this.mBluetoothController.addCallback(this.mCallback);
    }

    @Override // com.android.systemui.qp.SubscreenQSControllerContract$Presenter
    public final void unRegisterReceiver(boolean z) {
        if (z && this.mBleViewReceiver != null) {
            Log.d("SubscreenBleController", "SBC unRegisterReceiver mBleViewReceiver: " + this.mBleViewReceiver);
            ((BroadcastDispatcher) Dependency.sDependency.getDependencyInner(BroadcastDispatcher.class)).unregisterReceiver(this.mBleViewReceiver);
            this.mBleViewReceiver = null;
        } else if (this.mBleReceiver != null) {
            Log.d("SubscreenBleController", "SBC unRegisterReceiver mBleReceiver: " + this.mBleReceiver);
            ((BroadcastDispatcher) Dependency.sDependency.getDependencyInner(BroadcastDispatcher.class)).unregisterReceiver(this.mBleReceiver);
            this.mBleReceiver = null;
        }
        AnonymousClass1 anonymousClass1 = this.mCallback;
        if (anonymousClass1 != null) {
            this.mBluetoothController.removeCallback(anonymousClass1);
        }
    }
}
