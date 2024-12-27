package com.android.systemui.statusbar.pipeline.shared.data.repository;

import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import androidx.core.app.NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecSecurityContainerController$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryImpl$imsRegState$1$mImsRegStateChangedCallback$1;
import com.android.systemui.statusbar.pipeline.shared.data.model.ImsRegState;
import com.sec.ims.IImsRegistrationListener;
import com.sec.ims.ImsRegistration;
import com.sec.ims.ImsRegistrationError;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
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
            NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m(this.$slotId, "onDeregistered[", "]: TYPE_MOBILE_EMERGENCY", "ImsRegStateUtil");
            return;
        }
        if (imsRegistration.hasService("mmtel")) {
            ImsRegState imsRegState = this.this$0.imsRegState;
            imsRegState.voWifiRegState = false;
            imsRegState.voLTERegState = false;
            imsRegState.ePDGRegState = false;
            NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m(this.$slotId, "onDeregistered[", "]: voice profiles are disconnected", "ImsRegStateUtil");
        } else {
            if (imsRegistration.getNetworkType() == 11) {
                this.this$0.imsRegState.voWifiRegState = false;
                NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m(this.$slotId, "onDeregistered[", "]: VoWifi is disconnected", "ImsRegStateUtil");
            }
            NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m(this.$slotId, "onDeregistered[", "]: is not MMTEL_VOICE", "ImsRegStateUtil");
        }
        this.this$0.imsRegStates.put(Integer.valueOf(this.$slotId), this.this$0.imsRegState);
        ImsRegStateUtil imsRegStateUtil = this.this$0;
        imsRegStateUtil._ePDGConnected.updateState(null, Boolean.valueOf(imsRegStateUtil.ePDGConnected()));
        Iterable iterable = (Iterable) ((ArrayList) this.this$0.imsRegStateChangedCallbacks).get(this.$slotId);
        ImsRegStateUtil imsRegStateUtil2 = this.this$0;
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            ((MobileConnectionRepositoryImpl$imsRegState$1$mImsRegStateChangedCallback$1) it.next()).onImsRegStateChanged(ImsRegState.copy$default(imsRegStateUtil2.imsRegState));
        }
    }

    @Override // com.sec.ims.IImsRegistrationListener
    public final void onRegistered(ImsRegistration imsRegistration) {
        if (imsRegistration.getNetworkType() == 15) {
            NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m(this.$slotId, "onRegistered[", "]: TYPE_MOBILE_EMERGENCY", "ImsRegStateUtil");
            return;
        }
        if (!imsRegistration.hasService("mmtel")) {
            if (!imsRegistration.getEpdgStatus() && imsRegistration.getNetworkType() == 11) {
                this.this$0.imsRegState.voWifiRegState = false;
                NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m(this.$slotId, "onRegistered[", "]: VoWifi is disconnected", "ImsRegStateUtil");
            }
            NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m(this.$slotId, "onRegistered[", "]: is not MMTEL_VOICE", "ImsRegStateUtil");
        } else if (imsRegistration.getEpdgStatus() || imsRegistration.getNetworkType() == 1) {
            this.this$0.imsRegState.voWifiRegState = true;
            NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m(this.$slotId, "onRegistered[", "]: VoWifi is connected", "ImsRegStateUtil");
        } else {
            if (imsRegistration.getNetworkType() == 11) {
                this.this$0.imsRegState.voLTERegState = true;
            }
            this.this$0.imsRegState.voWifiRegState = false;
            SuggestionsAdapter$$ExternalSyntheticOutline0.m(this.$slotId, imsRegistration.getNetworkType(), "onRegistered[", "]: MMTEL_VOICE. network type=", "ImsRegStateUtil");
        }
        if (this.this$0.carrierInfraMediator.isEnabled(CarrierInfraMediator.Conditions.USE_WIFI_CALLING_ICON, this.$slotId, new Object[0])) {
            this.this$0.imsRegState.ePDGRegState = imsRegistration.getEpdgStatus();
            KeyguardSecSecurityContainerController$$ExternalSyntheticOutline0.m("onRegistered[", this.$slotId, "]: ePDGStatus=", this.this$0.imsRegState.ePDGRegState, "ImsRegStateUtil");
        }
        this.this$0.imsRegStates.put(Integer.valueOf(this.$slotId), this.this$0.imsRegState);
        ImsRegStateUtil imsRegStateUtil = this.this$0;
        imsRegStateUtil._ePDGConnected.updateState(null, Boolean.valueOf(imsRegStateUtil.ePDGConnected()));
        Iterable iterable = (Iterable) ((ArrayList) this.this$0.imsRegStateChangedCallbacks).get(this.$slotId);
        ImsRegStateUtil imsRegStateUtil2 = this.this$0;
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            ((MobileConnectionRepositoryImpl$imsRegState$1$mImsRegStateChangedCallback$1) it.next()).onImsRegStateChanged(ImsRegState.copy$default(imsRegStateUtil2.imsRegState));
        }
    }
}
