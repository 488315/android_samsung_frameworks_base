package com.android.systemui.media.controls.pipeline;

import android.content.Context;
import com.android.settingslib.bluetooth.LocalBluetoothManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class LocalMediaManagerFactory {
    public final Context context;
    public final LocalBluetoothManager localBluetoothManager;

    public LocalMediaManagerFactory(Context context, LocalBluetoothManager localBluetoothManager) {
        this.context = context;
        this.localBluetoothManager = localBluetoothManager;
    }
}
