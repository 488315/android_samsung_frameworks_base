package com.android.systemui.media;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.util.Log;
import androidx.core.app.NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecSimPinViewController$$ExternalSyntheticOutline0;
import kotlin.Pair;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class MediaBluetoothHelper {
    public BluetoothA2dp a2dp;
    public final Context context;
    public Pair lastBudsDrawable;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public MediaBluetoothHelper(Context context) {
        this.context = context;
        BluetoothProfile.ServiceListener serviceListener = new BluetoothProfile.ServiceListener() { // from class: com.android.systemui.media.MediaBluetoothHelper$serviceListener$1
            @Override // android.bluetooth.BluetoothProfile.ServiceListener
            public final void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
                Log.d("MediaBluetoothHelper", "onServiceConnected");
                if (i != 2) {
                    NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m(i, "onServiceConnected: ", " is not supported", "MediaBluetoothHelper");
                } else {
                    MediaBluetoothHelper.this.a2dp = (BluetoothA2dp) bluetoothProfile;
                }
            }

            @Override // android.bluetooth.BluetoothProfile.ServiceListener
            public final void onServiceDisconnected(int i) {
                Log.d("MediaBluetoothHelper", "onServiceDisconnected");
                if (i == 2) {
                    MediaBluetoothHelper.this.a2dp = null;
                } else {
                    NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m(i, "onServiceDisconnected: ", " is not supported", "MediaBluetoothHelper");
                }
            }
        };
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter != null) {
            defaultAdapter.getProfileProxy(context, serviceListener, 2);
            Log.d("MediaBluetoothHelper", "getProfileProxy");
        }
    }

    public static short semIconIndex(BluetoothDevice bluetoothDevice) {
        byte[] semGetManufacturerDeviceIconIndex = bluetoothDevice.semGetManufacturerDeviceIconIndex();
        if (semGetManufacturerDeviceIconIndex == null) {
            return (short) -1;
        }
        try {
            return (short) (semGetManufacturerDeviceIconIndex[1] | (semGetManufacturerDeviceIconIndex[0] << 8));
        } catch (Exception e) {
            KeyguardSecSimPinViewController$$ExternalSyntheticOutline0.m("Fail to get iconIndex ", e, "MediaBluetoothHelper");
            return (short) -1;
        }
    }
}
