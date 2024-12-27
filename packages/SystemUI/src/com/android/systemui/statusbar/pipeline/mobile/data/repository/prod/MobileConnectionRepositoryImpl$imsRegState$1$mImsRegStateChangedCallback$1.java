package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import android.util.Log;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.pipeline.shared.data.model.ImsRegState;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class MobileConnectionRepositoryImpl$imsRegState$1$mImsRegStateChangedCallback$1 {
    public final /* synthetic */ ProducerScope $$this$conflatedCallbackFlow;
    public final /* synthetic */ MobileConnectionRepositoryImpl this$0;

    public MobileConnectionRepositoryImpl$imsRegState$1$mImsRegStateChangedCallback$1(MobileConnectionRepositoryImpl mobileConnectionRepositoryImpl, ProducerScope producerScope) {
        this.this$0 = mobileConnectionRepositoryImpl;
        this.$$this$conflatedCallbackFlow = producerScope;
    }

    public final void onImsRegStateChanged(ImsRegState imsRegState) {
        int i = this.this$0.slotId;
        boolean z = imsRegState.voWifiRegState;
        boolean z2 = imsRegState.voLTERegState;
        boolean z3 = imsRegState.ePDGRegState;
        StringBuilder m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("onImsRegStateChanged[", i, "] (", z, " / ");
        m.append(z2);
        m.append(" / ");
        m.append(z3);
        m.append(")");
        Log.d("MobileConnectionRepositoryImpl", m.toString());
        ((ChannelCoroutine) this.$$this$conflatedCallbackFlow).mo2552trySendJP2dKIU(imsRegState);
    }
}
