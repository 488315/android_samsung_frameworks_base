package com.android.settingslib.deviceinfo;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import com.android.settingslib.core.lifecycle.Lifecycle;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class AbstractBluetoothAddressPreferenceController extends AbstractConnectivityPreferenceController {
    public static final String[] CONNECTIVITY_INTENTS = {"android.bluetooth.adapter.action.STATE_CHANGED"};
    static final String KEY_BT_ADDRESS = "bt_address";

    public AbstractBluetoothAddressPreferenceController(Context context, Lifecycle lifecycle) {
        super(context, lifecycle);
    }

    @Override // com.android.settingslib.deviceinfo.AbstractConnectivityPreferenceController
    public final String[] getConnectivityIntents() {
        return CONNECTIVITY_INTENTS;
    }

    @Override // com.android.settingslib.deviceinfo.AbstractConnectivityPreferenceController
    public final void updateConnectivity() {
        BluetoothAdapter.getDefaultAdapter();
    }
}
