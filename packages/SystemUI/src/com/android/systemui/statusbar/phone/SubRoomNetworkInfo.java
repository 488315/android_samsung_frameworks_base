package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import com.android.systemui.plugins.subscreen.SubRoom;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class SubRoomNetworkInfo implements SubRoom {
    public boolean isAirplane;
    public byte[] modeIcon;
    public int noServiceType;
    public SubRoom.StateChangeListener stateChangeListener;

    @Override // com.android.systemui.plugins.subscreen.SubRoom
    public final View getView(Context context) {
        return null;
    }

    @Override // com.android.systemui.plugins.subscreen.SubRoom
    public final void removeListener() {
        this.stateChangeListener = null;
    }

    @Override // com.android.systemui.plugins.subscreen.SubRoom
    public final Bundle request(String str, Bundle bundle) {
        if (!SubRoom.STATE_NETWORK_INFO.equals(str)) {
            return bundle;
        }
        Bundle bundle2 = new Bundle();
        bundle2.putBoolean(SubRoom.EXTRA_KEY_AIRPLANE_MODE, this.isAirplane);
        bundle2.putInt(SubRoom.EXTRA_KEY_NO_SIGNAL, this.noServiceType);
        bundle2.putByteArray(SubRoom.EXTRA_KEY_ROUTINE_MODE, this.modeIcon);
        return bundle2;
    }

    @Override // com.android.systemui.plugins.subscreen.SubRoom
    public final void setListener(SubRoom.StateChangeListener stateChangeListener) {
        this.stateChangeListener = stateChangeListener;
    }

    @Override // com.android.systemui.plugins.subscreen.SubRoom
    public final void onCloseFinished() {
    }

    @Override // com.android.systemui.plugins.subscreen.SubRoom
    public final void onCloseStarted() {
    }

    @Override // com.android.systemui.plugins.subscreen.SubRoom
    public final void onOpenFinished() {
    }

    @Override // com.android.systemui.plugins.subscreen.SubRoom
    public final void onOpenStarted() {
    }
}
