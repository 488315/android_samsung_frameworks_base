package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import android.util.Log;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.pipeline.shared.data.model.ImsRegState;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryImpl$imsRegState$1$mImsRegStateChangedCallback$1 */
/* loaded from: classes2.dex */
public final class C3289x498d3842 {
    public final /* synthetic */ ProducerScope $$this$conflatedCallbackFlow;
    public final /* synthetic */ MobileConnectionRepositoryImpl this$0;

    public C3289x498d3842(MobileConnectionRepositoryImpl mobileConnectionRepositoryImpl, ProducerScope producerScope) {
        this.this$0 = mobileConnectionRepositoryImpl;
        this.$$this$conflatedCallbackFlow = producerScope;
    }

    public final void onImsRegStateChanged(ImsRegState imsRegState) {
        int i = this.this$0.slotId;
        boolean z = imsRegState.voWifiRegState;
        boolean z2 = imsRegState.voLTERegState;
        boolean z3 = imsRegState.ePDGRegState;
        StringBuilder m76m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m76m("onImsRegStateChanged[", i, "] (", z, " / ");
        m76m.append(z2);
        m76m.append(" / ");
        m76m.append(z3);
        m76m.append(")");
        Log.d("MobileConnectionRepositoryImpl", m76m.toString());
        ((ChannelCoroutine) this.$$this$conflatedCallbackFlow).mo2872trySendJP2dKIU(imsRegState);
    }
}
