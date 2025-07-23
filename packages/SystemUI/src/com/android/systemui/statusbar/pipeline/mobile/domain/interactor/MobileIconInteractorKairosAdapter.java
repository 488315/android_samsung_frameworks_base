package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import com.android.systemui.log.table.TableLogBuffer;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class MobileIconInteractorKairosAdapter implements MobileIconInteractor {
    public final Flow activity;
    public final StateFlow alwaysShowDataRatIcon;
    public final StateFlow carrierName;
    public final StateFlow carrierNetworkChangeActive;
    public final Flow disabledActivityIcon;
    public final Flow disabledDataIcon;
    public final Flow imsRegState;
    public final StateFlow isAllowedDuringAirplaneMode;
    public final StateFlow isDataConnected;
    public final StateFlow isDataEnabled;
    public final StateFlow isEmergencyOnly;
    public final Flow isForceHidden;
    public final StateFlow isInService;
    public final StateFlow isNonTerrestrial;
    public final StateFlow isRoaming;
    public final Flow isSim1On;
    public final Flow isSimOn;
    public final StateFlow isSingleCarrier;
    public final Flow isVoWifiConnected;
    public final Flow mobileIsDefault;
    public final Flow mobileServiceState;
    public final StateFlow networkName;
    public final StateFlow networkTypeIconGroup;
    public final Flow otherSlotInCallState;
    public final Flow roamingId;
    public final StateFlow showSliceAttribution;
    public final StateFlow signalLevelIcon;
    public final int slotId;
    public final TableLogBuffer tableLogBuffer;
    public final Flow voiceNoServiceIcon;

    public MobileIconInteractorKairosAdapter(TableLogBuffer tableLogBuffer, Flow flow, Flow flow2, StateFlow stateFlow, StateFlow stateFlow2, StateFlow stateFlow3, StateFlow stateFlow4, StateFlow stateFlow5, StateFlow stateFlow6, StateFlow stateFlow7, StateFlow stateFlow8, StateFlow stateFlow9, StateFlow stateFlow10, StateFlow stateFlow11, StateFlow stateFlow12, StateFlow stateFlow13, Flow flow3, StateFlow stateFlow14, StateFlow stateFlow15, Flow flow4, Flow flow5, Flow flow6, Flow flow7, Flow flow8, Flow flow9, Flow flow10, Flow flow11, Flow flow12, Flow flow13, int i) {
        this.tableLogBuffer = tableLogBuffer;
        this.activity = flow;
        this.mobileIsDefault = flow2;
        this.isDataConnected = stateFlow;
        this.isInService = stateFlow2;
        this.isEmergencyOnly = stateFlow3;
        this.isDataEnabled = stateFlow4;
        this.alwaysShowDataRatIcon = stateFlow5;
        this.signalLevelIcon = stateFlow6;
        this.networkTypeIconGroup = stateFlow7;
        this.showSliceAttribution = stateFlow8;
        this.isNonTerrestrial = stateFlow9;
        this.networkName = stateFlow10;
        this.carrierName = stateFlow11;
        this.isSingleCarrier = stateFlow12;
        this.isRoaming = stateFlow13;
        this.isForceHidden = flow3;
        this.isAllowedDuringAirplaneMode = stateFlow14;
        this.carrierNetworkChangeActive = stateFlow15;
        this.roamingId = flow4;
        this.isSimOn = flow5;
        this.isSim1On = flow6;
        this.mobileServiceState = flow7;
        this.disabledDataIcon = flow8;
        this.disabledActivityIcon = flow9;
        this.otherSlotInCallState = flow10;
        this.voiceNoServiceIcon = flow11;
        this.imsRegState = flow12;
        this.isVoWifiConnected = flow13;
        this.slotId = i;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor
    public final Flow getActivity() {
        return this.activity;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor
    public final Flow getAlwaysShowDataRatIcon() {
        return this.alwaysShowDataRatIcon;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor
    public final Flow getCarrierName() {
        return this.carrierName;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor
    public final Flow getCarrierNetworkChangeActive() {
        return this.carrierNetworkChangeActive;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor
    public final Flow getDisabledActivityIcon() {
        return this.disabledActivityIcon;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor
    public final Flow getDisabledDataIcon() {
        return this.disabledDataIcon;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor
    public final Flow getImsRegState() {
        return this.imsRegState;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor
    public final Flow getMobileIsDefault() {
        return this.mobileIsDefault;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor
    public final Flow getMobileServiceState() {
        return this.mobileServiceState;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor
    public final Flow getNetworkName() {
        return this.networkName;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor
    public final Flow getNetworkTypeIconGroup() {
        return this.networkTypeIconGroup;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor
    public final Flow getOtherSlotInCallState() {
        return this.otherSlotInCallState;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor
    public final Flow getRoamingId() {
        return this.roamingId;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor
    public final Flow getShowSliceAttribution() {
        return this.showSliceAttribution;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor
    public final Flow getSignalLevelIcon() {
        return this.signalLevelIcon;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor
    public final int getSlotId() {
        return this.slotId;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor
    public final TableLogBuffer getTableLogBuffer() {
        return this.tableLogBuffer;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor
    public final Flow getVoiceNoServiceIcon() {
        return this.voiceNoServiceIcon;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor
    public final Flow isAllowedDuringAirplaneMode() {
        return this.isAllowedDuringAirplaneMode;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor
    public final Flow isDataConnected() {
        return this.isDataConnected;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor
    public final Flow isDataEnabled() {
        return this.isDataEnabled;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor
    public final Flow isEmergencyOnly() {
        return this.isEmergencyOnly;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor
    public final Flow isForceHidden() {
        return this.isForceHidden;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor
    public final Flow isInService() {
        return this.isInService;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor
    public final Flow isNonTerrestrial() {
        return this.isNonTerrestrial;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor
    public final Flow isRoaming() {
        return this.isRoaming;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor
    public final Flow isSim1On() {
        return this.isSim1On;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor
    public final Flow isSimOn() {
        return this.isSimOn;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor
    public final Flow isSingleCarrier() {
        return this.isSingleCarrier;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconInteractor
    public final Flow isVoWifiConnected() {
        return this.isVoWifiConnected;
    }
}
