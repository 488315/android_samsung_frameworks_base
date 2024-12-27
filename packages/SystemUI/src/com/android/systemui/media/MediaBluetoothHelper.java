package com.android.systemui.media;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.util.Log;
import androidx.core.app.NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class MediaBluetoothHelper {
    public BluetoothA2dp a2dp;

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

    public MediaBluetoothHelper(Context context) {
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
}
