package com.android.systemui.statusbar.pipeline.shared.data.repository;

import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import androidx.core.app.AbstractC0147x487e7be7;
import com.android.keyguard.AbstractC0731x5bb8a836;
import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.C3289x498d3842;
import com.android.systemui.statusbar.pipeline.shared.data.model.ImsRegState;
import com.sec.ims.IImsRegistrationListener;
import com.sec.ims.ImsRegistration;
import com.sec.ims.ImsRegistrationError;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ImsRegStateUtil$getRegistrationListener$1 extends IImsRegistrationListener.Stub {
    public final /* synthetic */ int $slotId;
    public final /* synthetic */ ImsRegStateUtil this$0;

    public ImsRegStateUtil$getRegistrationListener$1(int i, ImsRegStateUtil imsRegStateUtil) {
        this.$slotId = i;
        this.this$0 = imsRegStateUtil;
    }

    @Override // com.sec.ims.IImsRegistrationListener
    public final void onDeregistered(ImsRegistration imsRegistration, ImsRegistrationError imsRegistrationError) {
        if (imsRegistration.getNetworkType() == 15) {
            AbstractC0147x487e7be7.m26m("onDeregistered[", this.$slotId, "]: TYPE_MOBILE_EMERGENCY", "ImsRegStateUtil");
            return;
        }
        if (imsRegistration.hasService("mmtel")) {
            ImsRegState imsRegState = this.this$0.imsRegState;
            imsRegState.voWifiRegState = false;
            imsRegState.voLTERegState = false;
            imsRegState.ePDGRegState = false;
            AbstractC0147x487e7be7.m26m("onDeregistered[", this.$slotId, "]: voice profiles are disconnected", "ImsRegStateUtil");
        } else {
            if (imsRegistration.getNetworkType() == 11) {
                this.this$0.imsRegState.voWifiRegState = false;
                AbstractC0147x487e7be7.m26m("onDeregistered[", this.$slotId, "]: VoWifi is disconnected", "ImsRegStateUtil");
            }
            AbstractC0147x487e7be7.m26m("onDeregistered[", this.$slotId, "]: is not MMTEL_VOICE", "ImsRegStateUtil");
        }
        this.this$0.imsRegStates.put(Integer.valueOf(this.$slotId), this.this$0.imsRegState);
        ImsRegStateUtil imsRegStateUtil = this.this$0;
        imsRegStateUtil._ePDGConnected.setValue(Boolean.valueOf(imsRegStateUtil.ePDGConnected()));
        Iterable<C3289x498d3842> iterable = (Iterable) ((ArrayList) this.this$0.imsRegStateChangedCallbacks).get(this.$slotId);
        ImsRegStateUtil imsRegStateUtil2 = this.this$0;
        for (C3289x498d3842 c3289x498d3842 : iterable) {
            ImsRegState imsRegState2 = imsRegStateUtil2.imsRegState;
            c3289x498d3842.onImsRegStateChanged(new ImsRegState(imsRegState2.voWifiRegState, imsRegState2.voLTERegState, imsRegState2.ePDGRegState));
        }
    }

    @Override // com.sec.ims.IImsRegistrationListener
    public final void onRegistered(ImsRegistration imsRegistration) {
        if (imsRegistration.getNetworkType() == 15) {
            AbstractC0147x487e7be7.m26m("onRegistered[", this.$slotId, "]: TYPE_MOBILE_EMERGENCY", "ImsRegStateUtil");
            return;
        }
        if (!imsRegistration.hasService("mmtel")) {
            if (!imsRegistration.getEpdgStatus() && imsRegistration.getNetworkType() == 11) {
                this.this$0.imsRegState.voWifiRegState = false;
                AbstractC0147x487e7be7.m26m("onRegistered[", this.$slotId, "]: VoWifi is disconnected", "ImsRegStateUtil");
            }
            AbstractC0147x487e7be7.m26m("onRegistered[", this.$slotId, "]: is not MMTEL_VOICE", "ImsRegStateUtil");
        } else if (imsRegistration.getEpdgStatus() || imsRegistration.getNetworkType() == 1) {
            this.this$0.imsRegState.voWifiRegState = true;
            AbstractC0147x487e7be7.m26m("onRegistered[", this.$slotId, "]: VoWifi is connected", "ImsRegStateUtil");
        } else {
            if (imsRegistration.getNetworkType() == 11) {
                this.this$0.imsRegState.voLTERegState = true;
            }
            this.this$0.imsRegState.voWifiRegState = false;
            SuggestionsAdapter$$ExternalSyntheticOutline0.m12m("onRegistered[", this.$slotId, "]: MMTEL_VOICE. network type=", imsRegistration.getNetworkType(), "ImsRegStateUtil");
        }
        if (this.this$0.carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.USE_WIFI_CALLING_ICON, this.$slotId, new Object[0])) {
            this.this$0.imsRegState.ePDGRegState = imsRegistration.getEpdgStatus();
            AbstractC0731x5bb8a836.m72m("onRegistered[", this.$slotId, "]: ePDGStatus=", this.this$0.imsRegState.ePDGRegState, "ImsRegStateUtil");
        }
        this.this$0.imsRegStates.put(Integer.valueOf(this.$slotId), this.this$0.imsRegState);
        ImsRegStateUtil imsRegStateUtil = this.this$0;
        imsRegStateUtil._ePDGConnected.setValue(Boolean.valueOf(imsRegStateUtil.ePDGConnected()));
        Iterable<C3289x498d3842> iterable = (Iterable) ((ArrayList) this.this$0.imsRegStateChangedCallbacks).get(this.$slotId);
        ImsRegStateUtil imsRegStateUtil2 = this.this$0;
        for (C3289x498d3842 c3289x498d3842 : iterable) {
            ImsRegState imsRegState = imsRegStateUtil2.imsRegState;
            c3289x498d3842.onImsRegStateChanged(new ImsRegState(imsRegState.voWifiRegState, imsRegState.voLTERegState, imsRegState.ePDGRegState));
        }
    }
}
