package com.android.systemui.statusbar.pipeline.mobile.domain.interactor;

import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository;
import com.android.systemui.statusbar.pipeline.mobile.util.MobileMappingsProxy;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class FakeMobileConnectionRepository implements MobileConnectionRepository {
    public final StateFlow carrierId;
    public final StateFlow carrierNetworkChangeActive;
    public final StateFlow cdmaLevel;
    public final StateFlow cdmaRoaming;
    public final MobileConnectionRepository connectionRepository;
    public final StateFlow dataActivityDirection;
    public final StateFlow dataConnectionState;
    public final StateFlow dataEnabled;
    public final StateFlow imsRegState;
    public final StateFlow isEmergencyOnly;
    public final StateFlow isGsm;
    public final StateFlow isInService;
    public final StateFlow isNonTerrestrial;
    public final StateFlow isRoaming;
    public final StateFlow mobileDataEnabledChanged;
    public final StateFlow mobileServiceState;
    public final StateFlow networkName;
    public final StateFlow numberOfLevels;
    public final StateFlow onTheCall;
    public final StateFlow operatorAlphaShort;
    public final StateFlow primaryLevel;
    public final StateFlow resolvedNetworkType;
    public final StateFlow sim1On;
    public final StateFlow simCardInfo;
    public final int slotId;
    public final int subId;
    public final StateFlow swRoaming;
    public final TableLogBuffer tableLogBuffer;

    public FakeMobileConnectionRepository(MobileConnectionRepository mobileConnectionRepository, MobileMappingsProxy mobileMappingsProxy) {
        this.connectionRepository = mobileConnectionRepository;
        this.subId = mobileConnectionRepository.getSubId();
        this.slotId = mobileConnectionRepository.getSlotId();
        this.carrierId = mobileConnectionRepository.getCarrierId();
        this.tableLogBuffer = mobileConnectionRepository.getTableLogBuffer();
        this.isEmergencyOnly = mobileConnectionRepository.isEmergencyOnly();
        this.isRoaming = mobileConnectionRepository.isRoaming();
        this.operatorAlphaShort = mobileConnectionRepository.getOperatorAlphaShort();
        this.isInService = mobileConnectionRepository.isInService();
        this.isNonTerrestrial = mobileConnectionRepository.isNonTerrestrial();
        this.isGsm = mobileConnectionRepository.isGsm();
        this.cdmaLevel = mobileConnectionRepository.getCdmaLevel();
        this.primaryLevel = mobileConnectionRepository.getPrimaryLevel();
        this.dataConnectionState = mobileConnectionRepository.getDataConnectionState();
        this.dataActivityDirection = mobileConnectionRepository.getDataActivityDirection();
        this.carrierNetworkChangeActive = mobileConnectionRepository.getCarrierNetworkChangeActive();
        this.resolvedNetworkType = mobileConnectionRepository.getResolvedNetworkType();
        this.numberOfLevels = mobileConnectionRepository.getNumberOfLevels();
        this.dataEnabled = mobileConnectionRepository.getDataEnabled();
        this.cdmaRoaming = mobileConnectionRepository.getCdmaRoaming();
        this.swRoaming = mobileConnectionRepository.getSwRoaming();
        this.networkName = mobileConnectionRepository.getNetworkName();
        this.simCardInfo = mobileConnectionRepository.getSimCardInfo();
        this.sim1On = mobileConnectionRepository.getSim1On();
        this.onTheCall = mobileConnectionRepository.getOnTheCall();
        this.mobileServiceState = mobileConnectionRepository.getMobileServiceState();
        this.imsRegState = mobileConnectionRepository.getImsRegState();
        this.mobileDataEnabledChanged = mobileConnectionRepository.getMobileDataEnabledChanged();
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow getCarrierId() {
        return this.carrierId;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow getCarrierNetworkChangeActive() {
        return this.carrierNetworkChangeActive;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow getCdmaLevel() {
        return this.cdmaLevel;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow getCdmaRoaming() {
        return this.cdmaRoaming;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow getDataActivityDirection() {
        return this.dataActivityDirection;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow getDataConnectionState() {
        return this.dataConnectionState;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow getDataEnabled() {
        return this.dataEnabled;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow getImsRegState() {
        return this.imsRegState;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow getMobileDataEnabledChanged() {
        return this.mobileDataEnabledChanged;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow getMobileServiceState() {
        return this.mobileServiceState;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow getNetworkName() {
        return this.networkName;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow getNumberOfLevels() {
        return this.numberOfLevels;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow getOnTheCall() {
        return this.onTheCall;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow getOperatorAlphaShort() {
        return this.operatorAlphaShort;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow getPrimaryLevel() {
        return this.primaryLevel;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow getResolvedNetworkType() {
        return this.resolvedNetworkType;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow getSim1On() {
        return this.sim1On;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow getSimCardInfo() {
        return this.simCardInfo;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final int getSlotId() {
        return this.slotId;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final int getSubId() {
        return this.subId;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow getSwRoaming() {
        return this.swRoaming;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final TableLogBuffer getTableLogBuffer() {
        return this.tableLogBuffer;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow isEmergencyOnly() {
        return this.isEmergencyOnly;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow isGsm() {
        return this.isGsm;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow isInService() {
        return this.isInService;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow isNonTerrestrial() {
        return this.isNonTerrestrial;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow isRoaming() {
        return this.isRoaming;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final void setSim1On(boolean z) {
        this.connectionRepository.setSim1On(z);
    }
}
