package com.android.systemui.media;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.util.Log;
import androidx.core.app.AbstractC0147x487e7be7;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MediaBluetoothHelper {
    public BluetoothA2dp a2dp;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
                    AbstractC0147x487e7be7.m26m("onServiceConnected: ", i, " is not supported", "MediaBluetoothHelper");
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
                    AbstractC0147x487e7be7.m26m("onServiceDisconnected: ", i, " is not supported", "MediaBluetoothHelper");
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
