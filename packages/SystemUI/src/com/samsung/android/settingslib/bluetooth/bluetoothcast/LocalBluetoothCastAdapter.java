package com.samsung.android.settingslib.bluetooth.bluetoothcast;

import android.app.AlarmManager;
import android.content.Context;
import android.os.SystemClock;
import android.util.Log;
import com.android.systemui.statusbar.policy.SBluetoothControllerImpl$$ExternalSyntheticLambda0;
import com.samsung.android.bluetooth.SemBluetoothCastAdapter;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class LocalBluetoothCastAdapter {
    public static LocalBluetoothCastAdapter sInstance;
    public final String TAG;
    public final AlarmManager mAlarmManager;
    public final AnonymousClass2 mBluetoothCastListener;
    public final ArrayList mCallbacks;
    public SemBluetoothCastAdapter mCastAdapter;
    public LocalBluetoothCastProfileManager mCastProfileManager;
    public final AnonymousClass1 mDiscoveryAlarmListener;

    /* JADX WARN: Type inference failed for: r1v0, types: [com.samsung.android.settingslib.bluetooth.bluetoothcast.LocalBluetoothCastAdapter$1] */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.samsung.android.bluetooth.SemBluetoothCastAdapter$BluetoothCastAdapterListener, com.samsung.android.settingslib.bluetooth.bluetoothcast.LocalBluetoothCastAdapter$2] */
    public LocalBluetoothCastAdapter(Context context) {
        String simpleName = getClass().getSimpleName();
        this.TAG = simpleName;
        this.mDiscoveryAlarmListener = new AlarmManager.OnAlarmListener() { // from class: com.samsung.android.settingslib.bluetooth.bluetoothcast.LocalBluetoothCastAdapter.1
            @Override // android.app.AlarmManager.OnAlarmListener
            public final void onAlarm() {
                Log.d(LocalBluetoothCastAdapter.this.TAG, "Discovery timed out");
                LocalBluetoothCastAdapter localBluetoothCastAdapter = LocalBluetoothCastAdapter.this;
                SemBluetoothCastAdapter semBluetoothCastAdapter = localBluetoothCastAdapter.mCastAdapter;
                String str = localBluetoothCastAdapter.TAG;
                if (semBluetoothCastAdapter == null) {
                    Log.d(str, "Cannot suspendDiscovery");
                    return;
                }
                semBluetoothCastAdapter.suspendDiscovery();
                Log.d(str, "suspendDiscovery");
                localBluetoothCastAdapter.mAlarmManager.cancel(localBluetoothCastAdapter.mDiscoveryAlarmListener);
            }
        };
        ?? r1 = new SemBluetoothCastAdapter.BluetoothCastAdapterListener() { // from class: com.samsung.android.settingslib.bluetooth.bluetoothcast.LocalBluetoothCastAdapter.2
            public final void onServiceConnected(SemBluetoothCastAdapter semBluetoothCastAdapter) {
                Log.d(LocalBluetoothCastAdapter.this.TAG, "SemBluetoothCastAdapter Connected");
                LocalBluetoothCastAdapter localBluetoothCastAdapter = LocalBluetoothCastAdapter.this;
                localBluetoothCastAdapter.mCastAdapter = semBluetoothCastAdapter;
                if (localBluetoothCastAdapter.mCastProfileManager == null) {
                    Log.d(localBluetoothCastAdapter.TAG, "Cannot set BluetoothCastStateOn");
                    return;
                }
                ArrayList arrayList = localBluetoothCastAdapter.mCallbacks;
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    ((SBluetoothControllerImpl$$ExternalSyntheticLambda0) obj).f$0.mHandler.obtainMessage(7, Boolean.TRUE).sendToTarget();
                }
            }

            public final void onServiceDisconnected() {
                LocalBluetoothCastAdapter localBluetoothCastAdapter = LocalBluetoothCastAdapter.this;
                if (localBluetoothCastAdapter.mCastAdapter != null) {
                    localBluetoothCastAdapter.mCastAdapter = null;
                }
                ArrayList arrayList = localBluetoothCastAdapter.mCallbacks;
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    ((SBluetoothControllerImpl$$ExternalSyntheticLambda0) obj).f$0.mHandler.obtainMessage(7, Boolean.FALSE).sendToTarget();
                }
            }
        };
        this.mBluetoothCastListener = r1;
        Log.d(simpleName, "LocalBluetoothCastAdapter");
        this.mCallbacks = new ArrayList();
        this.mAlarmManager = (AlarmManager) context.getSystemService("alarm");
        SemBluetoothCastAdapter.getProxy(context, (SemBluetoothCastAdapter.BluetoothCastAdapterListener) r1);
    }

    public final void cancelDiscovery() {
        SemBluetoothCastAdapter semBluetoothCastAdapter = this.mCastAdapter;
        String str = this.TAG;
        if (semBluetoothCastAdapter == null) {
            Log.d(str, "Cannot cancelDiscovery");
        } else {
            Log.d(str, "cancelDiscovery");
            this.mCastAdapter.cancelDiscovery();
        }
    }

    public final void finalize() {
        super.finalize();
        this.mCastAdapter.closeProxy();
    }

    public final void startDiscovery() {
        SemBluetoothCastAdapter semBluetoothCastAdapter = this.mCastAdapter;
        String str = this.TAG;
        if (semBluetoothCastAdapter == null) {
            Log.d(str, "Cannot startDiscovery");
            return;
        }
        semBluetoothCastAdapter.startDiscovery();
        Log.d(str, "startDiscovery");
        this.mAlarmManager.setExact(2, SystemClock.elapsedRealtime() + 12000, "Discovery", this.mDiscoveryAlarmListener, null);
    }
}
