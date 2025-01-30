package com.samsung.android.settingslib.bluetooth.bluetoothcast;

import android.app.AlarmManager;
import android.content.Context;
import android.util.Log;
import com.android.systemui.statusbar.policy.SBluetoothControllerImpl$$ExternalSyntheticLambda0;
import com.samsung.android.bluetooth.SemBluetoothCastAdapter;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class LocalBluetoothCastAdapter {
    public static LocalBluetoothCastAdapter sInstance;
    public final AlarmManager mAlarmManager;
    public final C47702 mBluetoothCastListener;
    public final ArrayList mCallbacks;
    public SemBluetoothCastAdapter mCastAdapter;
    public LocalBluetoothCastProfileManager mCastProfileManager;
    public final String TAG = "LocalBluetoothCastAdapter";
    public final C47691 mDiscoveryAlarmListener = new AlarmManager.OnAlarmListener() { // from class: com.samsung.android.settingslib.bluetooth.bluetoothcast.LocalBluetoothCastAdapter.1
        @Override // android.app.AlarmManager.OnAlarmListener
        public final void onAlarm() {
            Log.d(LocalBluetoothCastAdapter.this.TAG, "Discovery timed out");
            LocalBluetoothCastAdapter.this.suspendDiscovery();
        }
    };

    /* JADX WARN: Type inference failed for: r1v0, types: [com.samsung.android.settingslib.bluetooth.bluetoothcast.LocalBluetoothCastAdapter$1] */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.samsung.android.bluetooth.SemBluetoothCastAdapter$BluetoothCastAdapterListener, com.samsung.android.settingslib.bluetooth.bluetoothcast.LocalBluetoothCastAdapter$2] */
    public LocalBluetoothCastAdapter(Context context) {
        ?? r1 = new SemBluetoothCastAdapter.BluetoothCastAdapterListener() { // from class: com.samsung.android.settingslib.bluetooth.bluetoothcast.LocalBluetoothCastAdapter.2
            public final void onServiceConnected(SemBluetoothCastAdapter semBluetoothCastAdapter) {
                Log.d(LocalBluetoothCastAdapter.this.TAG, "SemBluetoothCastAdapter Connected");
                LocalBluetoothCastAdapter localBluetoothCastAdapter = LocalBluetoothCastAdapter.this;
                localBluetoothCastAdapter.mCastAdapter = semBluetoothCastAdapter;
                if (localBluetoothCastAdapter.mCastProfileManager == null) {
                    Log.d(localBluetoothCastAdapter.TAG, "Cannot set BluetoothCastStateOn");
                    return;
                }
                Iterator it = localBluetoothCastAdapter.mCallbacks.iterator();
                while (it.hasNext()) {
                    SBluetoothControllerImpl$$ExternalSyntheticLambda0 sBluetoothControllerImpl$$ExternalSyntheticLambda0 = (SBluetoothControllerImpl$$ExternalSyntheticLambda0) it.next();
                    sBluetoothControllerImpl$$ExternalSyntheticLambda0.f$0.mHandler.obtainMessage(7, Boolean.TRUE).sendToTarget();
                }
            }

            public final void onServiceDisconnected() {
                LocalBluetoothCastAdapter localBluetoothCastAdapter = LocalBluetoothCastAdapter.this;
                if (localBluetoothCastAdapter.mCastAdapter != null) {
                    localBluetoothCastAdapter.mCastAdapter = null;
                }
                Iterator it = localBluetoothCastAdapter.mCallbacks.iterator();
                while (it.hasNext()) {
                    SBluetoothControllerImpl$$ExternalSyntheticLambda0 sBluetoothControllerImpl$$ExternalSyntheticLambda0 = (SBluetoothControllerImpl$$ExternalSyntheticLambda0) it.next();
                    sBluetoothControllerImpl$$ExternalSyntheticLambda0.f$0.mHandler.obtainMessage(7, Boolean.FALSE).sendToTarget();
                }
            }
        };
        this.mBluetoothCastListener = r1;
        Log.d("LocalBluetoothCastAdapter", "LocalBluetoothCastAdapter");
        this.mCallbacks = new ArrayList();
        this.mAlarmManager = (AlarmManager) context.getSystemService("alarm");
        SemBluetoothCastAdapter.getProxy(context, (SemBluetoothCastAdapter.BluetoothCastAdapterListener) r1);
    }

    public final void finalize() {
        super.finalize();
        this.mCastAdapter.closeProxy();
    }

    public final void suspendDiscovery() {
        SemBluetoothCastAdapter semBluetoothCastAdapter = this.mCastAdapter;
        String str = this.TAG;
        if (semBluetoothCastAdapter == null) {
            Log.d(str, "Cannot suspendDiscovery");
            return;
        }
        Log.d(str, "suspendDiscovery");
        this.mAlarmManager.cancel(this.mDiscoveryAlarmListener);
        this.mCastAdapter.suspendDiscovery();
    }
}
