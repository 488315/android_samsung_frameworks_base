package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkRequest;
import android.telephony.CarrierConfigManager;
import android.telephony.TelephonyManager;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.flags.FeatureFlagsClassic;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator;
import com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger;
import com.android.systemui.statusbar.pipeline.mobile.data.model.NetworkNameModel;
import com.android.systemui.statusbar.pipeline.mobile.data.model.SystemUiCarrierConfig;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.CarrierConfigRepository;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository;
import com.android.systemui.statusbar.pipeline.mobile.util.MobileMappingsProxy;
import com.android.systemui.statusbar.pipeline.mobile.util.SimCardInfoUtil;
import com.android.systemui.statusbar.pipeline.shared.data.repository.ImsRegStateUtil;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SettingsHelper;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlow;

public final class MobileConnectionRepositoryImpl implements MobileConnectionRepository {
    public final ReadonlyStateFlow allowNetworkSliceIndicator;
    public final CoroutineDispatcher bgDispatcher;
    public final ReadonlyStateFlow callbackEvents;
    public final CarrierConfigManager carrierConfigManager;
    public final ReadonlyStateFlow carrierId;
    public final CarrierInfraMediator carrierInfraMediator;
    public final ReadonlyStateFlow carrierName;
    public final ReadonlyStateFlow carrierNetworkChangeActive;
    public final ReadonlyStateFlow cdmaLevel;
    public final ReadonlyStateFlow cdmaRoaming;
    public final Context context;
    public final ReadonlyStateFlow dataActivityDirection;
    public final ReadonlyStateFlow dataConnectionState;
    public final ReadonlyStateFlow dataEnabled;
    public final ReadonlyStateFlow dataRegistrationState;
    public final ReadonlyStateFlow dataRoamingType;
    public final ReadonlyStateFlow hasPrioritizedNetworkCapabilities;
    public final ReadonlyStateFlow imsRegState;
    public final ImsRegStateUtil imsRegStateUtil;
    public final ReadonlyStateFlow inflateSignalStrength;
    public final ReadonlyStateFlow isAllowedDuringAirplaneMode;
    public final ReadonlyStateFlow isEmergencyOnly;
    public final ReadonlyStateFlow isGsm;
    public final ReadonlyStateFlow isInService;
    public final ReadonlyStateFlow isNonTerrestrial;
    public final ReadonlyStateFlow isRoaming;
    public final ReadonlyStateFlow mobileDataEnabledChanged;
    public final MobileMappingsProxy mobileMappingsProxy;
    public final ReadonlyStateFlow mobileServiceState;
    public final ReadonlyStateFlow msimSubmode;
    public final ReadonlyStateFlow networkName;
    public final NetworkRequest networkSliceRequest;
    public final ReadonlyStateFlow numberOfLevels;
    public final ReadonlyStateFlow onTheCall;
    public final ReadonlyStateFlow operatorAlphaShort;
    public final ReadonlyStateFlow optionalRadioTech;
    public final ReadonlyStateFlow primaryLevel;
    public final ReadonlyStateFlow resolvedNetworkType;
    public final ReadonlyStateFlow satelliteLevel;
    public final ReadonlyStateFlow semOMCChangedEvent;
    public final ReadonlyStateFlow semSatelliteEnabled;
    public final ReadonlyStateFlow semSatelliteServiceState;
    public final ReadonlyStateFlow semSatelliteSignalStrength;
    private final SettingsHelper settingsHelper;
    public final ReadonlyStateFlow sim1On;
    public final ReadonlyStateFlow simCardInfo;
    public final SimCardInfoUtil simCardInfoUtil;
    public final ReadonlyStateFlow simSettingsChanged;
    public final int slotId;
    public final int subId;
    public final ReadonlyStateFlow swRoaming;
    public final TableLogBuffer tableLogBuffer;
    public final ReadonlyStateFlow telephonyDisplayInfo;
    public final TelephonyManager telephonyManager;
    public final MobileConnectionRepositoryImpl$special$$inlined$map$17 telephonyPollingEvent;
    public final ReadonlyStateFlow voiceCallAvailable;
    public final ReadonlyStateFlow voiceNetworkType;

    public final class Factory {
        public final CoroutineDispatcher bgDispatcher;
        public final BroadcastDispatcher broadcastDispatcher;
        public final CarrierConfigManager carrierConfigManager;
        public final CarrierConfigRepository carrierConfigRepository;
        public final CarrierInfraMediator carrierInfraMediator;
        public final ConnectivityManager connectivityManager;
        public final Context context;
        public final FeatureFlagsClassic flags;
        public final ImsRegStateUtil imsRegStateUtil;
        public final MobileInputLogger logger;
        public final MobileMappingsProxy mobileMappingsProxy;
        public final CoroutineScope scope;
        private final SettingsHelper settingsHelper;
        public final SimCardInfoUtil simCardInfoUtil;
        public final TelephonyManager telephonyManager;

        public Factory(Context context, BroadcastDispatcher broadcastDispatcher, ConnectivityManager connectivityManager, TelephonyManager telephonyManager, MobileInputLogger mobileInputLogger, CarrierConfigRepository carrierConfigRepository, MobileMappingsProxy mobileMappingsProxy, FeatureFlagsClassic featureFlagsClassic, CoroutineDispatcher coroutineDispatcher, CoroutineScope coroutineScope, CarrierInfraMediator carrierInfraMediator, SimCardInfoUtil simCardInfoUtil, SettingsHelper settingsHelper, ImsRegStateUtil imsRegStateUtil, CarrierConfigManager carrierConfigManager) {
            this.context = context;
            this.broadcastDispatcher = broadcastDispatcher;
            this.connectivityManager = connectivityManager;
            this.telephonyManager = telephonyManager;
            this.logger = mobileInputLogger;
            this.carrierConfigRepository = carrierConfigRepository;
            this.mobileMappingsProxy = mobileMappingsProxy;
            this.flags = featureFlagsClassic;
            this.bgDispatcher = coroutineDispatcher;
            this.scope = coroutineScope;
            this.carrierInfraMediator = carrierInfraMediator;
            this.simCardInfoUtil = simCardInfoUtil;
            this.settingsHelper = settingsHelper;
            this.imsRegStateUtil = imsRegStateUtil;
            this.carrierConfigManager = carrierConfigManager;
        }

        public final MobileConnectionRepositoryImpl build(int i, TableLogBuffer tableLogBuffer, Flow flow, NetworkNameModel networkNameModel, String str) {
            Context context = this.context;
            ConnectivityManager connectivityManager = this.connectivityManager;
            TelephonyManager createForSubscriptionId = this.telephonyManager.createForSubscriptionId(i);
            SystemUiCarrierConfig orCreateConfigForSubId = this.carrierConfigRepository.getOrCreateConfigForSubId(i);
            SettingsHelper settingsHelper = this.settingsHelper;
            CarrierConfigManager carrierConfigManager = this.carrierConfigManager;
            return new MobileConnectionRepositoryImpl(i, context, flow, networkNameModel, str, connectivityManager, createForSubscriptionId, orCreateConfigForSubId, this.broadcastDispatcher, this.mobileMappingsProxy, this.bgDispatcher, this.logger, tableLogBuffer, this.flags, this.scope, this.carrierInfraMediator, this.simCardInfoUtil, settingsHelper, this.imsRegStateUtil, carrierConfigManager);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x047f  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0564  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0482  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public MobileConnectionRepositoryImpl(int r35, android.content.Context r36, final kotlinx.coroutines.flow.Flow r37, final com.android.systemui.statusbar.pipeline.mobile.data.model.NetworkNameModel r38, java.lang.String r39, android.net.ConnectivityManager r40, android.telephony.TelephonyManager r41, final com.android.systemui.statusbar.pipeline.mobile.data.model.SystemUiCarrierConfig r42, com.android.systemui.broadcast.BroadcastDispatcher r43, com.android.systemui.statusbar.pipeline.mobile.util.MobileMappingsProxy r44, kotlinx.coroutines.CoroutineDispatcher r45, com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger r46, com.android.systemui.log.table.TableLogBuffer r47, com.android.systemui.flags.FeatureFlagsClassic r48, kotlinx.coroutines.CoroutineScope r49, com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator r50, com.android.systemui.statusbar.pipeline.mobile.util.SimCardInfoUtil r51, com.android.systemui.util.SettingsHelper r52, com.android.systemui.statusbar.pipeline.shared.data.repository.ImsRegStateUtil r53, android.telephony.CarrierConfigManager r54) {
        /*
            Method dump skipped, instructions count: 1426
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryImpl.<init>(int, android.content.Context, kotlinx.coroutines.flow.Flow, com.android.systemui.statusbar.pipeline.mobile.data.model.NetworkNameModel, java.lang.String, android.net.ConnectivityManager, android.telephony.TelephonyManager, com.android.systemui.statusbar.pipeline.mobile.data.model.SystemUiCarrierConfig, com.android.systemui.broadcast.BroadcastDispatcher, com.android.systemui.statusbar.pipeline.mobile.util.MobileMappingsProxy, kotlinx.coroutines.CoroutineDispatcher, com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger, com.android.systemui.log.table.TableLogBuffer, com.android.systemui.flags.FeatureFlagsClassic, kotlinx.coroutines.CoroutineScope, com.android.systemui.statusbar.pipeline.carrier.CarrierInfraMediator, com.android.systemui.statusbar.pipeline.mobile.util.SimCardInfoUtil, com.android.systemui.util.SettingsHelper, com.android.systemui.statusbar.pipeline.shared.data.repository.ImsRegStateUtil, android.telephony.CarrierConfigManager):void");
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow getAllowNetworkSliceIndicator() {
        return this.allowNetworkSliceIndicator;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow getCarrierId() {
        return this.carrierId;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow getCarrierName() {
        return this.carrierName;
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
    public final StateFlow getHasPrioritizedNetworkCapabilities() {
        return this.hasPrioritizedNetworkCapabilities;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow getImsRegState() {
        return this.imsRegState;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow getInflateSignalStrength() {
        return this.inflateSignalStrength;
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
    public final StateFlow getSatelliteLevel() {
        return this.satelliteLevel;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow getSemOMCChangedEvent() {
        return this.semOMCChangedEvent;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow getSemSatelliteEnabled() {
        return this.semSatelliteEnabled;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow getSemSatelliteServiceState() {
        return this.semSatelliteServiceState;
    }

    @Override // com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionRepository
    public final StateFlow getSemSatelliteSignalStrength() {
        return this.semSatelliteSignalStrength;
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
    public final StateFlow isAllowedDuringAirplaneMode() {
        return this.isAllowedDuringAirplaneMode;
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
    public final Object isInEcmMode(Continuation continuation) {
        return BuildersKt.withContext(this.bgDispatcher, new MobileConnectionRepositoryImpl$isInEcmMode$2(this, null), continuation);
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

    public final boolean isSwRoaming() {
        String mSimSystemProperty = DeviceState.getMSimSystemProperty("persist.sys.softsim.status", this.slotId, "default");
        return mSimSystemProperty.equals("activating") || mSimSystemProperty.equals("activated");
    }
}
