package com.android.systemui.statusbar.pipeline.mobile.data.repository;

import android.telephony.CellSignalStrength;
import com.android.systemui.log.table.TableLogBuffer;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.StateFlow;

public interface MobileConnectionRepository {
    public static final Companion Companion = Companion.$$INSTANCE;

    public final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final int DEFAULT_NUM_LEVELS = CellSignalStrength.getNumSignalStrengthLevels();

        private Companion() {
        }
    }

    StateFlow getAllowNetworkSliceIndicator();

    StateFlow getCarrierId();

    StateFlow getCarrierName();

    StateFlow getCarrierNetworkChangeActive();

    StateFlow getCdmaLevel();

    StateFlow getCdmaRoaming();

    StateFlow getDataActivityDirection();

    StateFlow getDataConnectionState();

    StateFlow getDataEnabled();

    StateFlow getHasPrioritizedNetworkCapabilities();

    StateFlow getImsRegState();

    StateFlow getInflateSignalStrength();

    StateFlow getMobileDataEnabledChanged();

    StateFlow getMobileServiceState();

    StateFlow getNetworkName();

    StateFlow getNumberOfLevels();

    StateFlow getOnTheCall();

    StateFlow getOperatorAlphaShort();

    StateFlow getPrimaryLevel();

    StateFlow getResolvedNetworkType();

    StateFlow getSatelliteLevel();

    StateFlow getSemOMCChangedEvent();

    StateFlow getSemSatelliteEnabled();

    StateFlow getSemSatelliteServiceState();

    StateFlow getSemSatelliteSignalStrength();

    StateFlow getSim1On();

    StateFlow getSimCardInfo();

    int getSlotId();

    int getSubId();

    StateFlow getSwRoaming();

    TableLogBuffer getTableLogBuffer();

    StateFlow isAllowedDuringAirplaneMode();

    StateFlow isEmergencyOnly();

    StateFlow isGsm();

    Object isInEcmMode(Continuation continuation);

    StateFlow isInService();

    StateFlow isNonTerrestrial();

    StateFlow isRoaming();
}
