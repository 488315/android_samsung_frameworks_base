package com.android.systemui.statusbar.connectivity;

import android.content.Context;
import com.android.settingslib.AccessibilityContentDescriptions;
import com.android.settingslib.SignalIcon$IconGroup;

public final class EthernetSignalController extends SignalController {
    public EthernetSignalController(Context context, CallbackHandler callbackHandler, NetworkControllerImpl networkControllerImpl) {
        super("EthernetSignalController", context, 3, callbackHandler, networkControllerImpl);
        ConnectivityState connectivityState = this.mCurrentState;
        ConnectivityState connectivityState2 = this.mLastState;
        int[][] iArr = EthernetIcons.ETHERNET_ICONS;
        int[] iArr2 = AccessibilityContentDescriptions.ETHERNET_CONNECTION_VALUES;
        SignalIcon$IconGroup signalIcon$IconGroup = new SignalIcon$IconGroup("Ethernet Icons", iArr, null, iArr2, 0, 0, 0, 0, iArr2[0], null);
        connectivityState2.iconGroup = signalIcon$IconGroup;
        connectivityState.iconGroup = signalIcon$IconGroup;
    }

    @Override // com.android.systemui.statusbar.connectivity.SignalController
    public final ConnectivityState cleanState() {
        return new ConnectivityState();
    }

    @Override // com.android.systemui.statusbar.connectivity.SignalController
    public final int getContentDescription() {
        ConnectivityState connectivityState = this.mCurrentState;
        return connectivityState.connected ? connectivityState.iconGroup.contentDesc[1] : connectivityState.iconGroup.discContentDesc;
    }

    @Override // com.android.systemui.statusbar.connectivity.SignalController
    public final void notifyListeners(SignalCallback signalCallback) {
        signalCallback.setEthernetIndicators(new IconState(this.mCurrentState.connected, getCurrentIconId(), getTextIfExists(getContentDescription()).toString()));
    }
}
