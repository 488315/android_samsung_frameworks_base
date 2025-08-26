package com.android.systemui.statusbar.pipeline.mobile.data.repository;

import android.telephony.CellSignalStrength;
import com.android.systemui.kairos.State;
import com.android.systemui.log.table.TableLogBuffer;

/* loaded from: classes3.dex */
public interface MobileConnectionRepositoryKairos {
    public static final Companion Companion = Companion.$$INSTANCE;

    public final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final int DEFAULT_NUM_LEVELS = CellSignalStrength.getNumSignalStrengthLevels();

        private Companion() {
        }
    }

    State getAllowNetworkSliceIndicator();

    State getCarrierId();

    State getCarrierName();

    State getCarrierNetworkChangeActive();

    State getCdmaLevel();

    State getCdmaRoaming();

    State getDataActivityDirection();

    State getDataConnectionState();

    State getDataEnabled();

    State getHasPrioritizedNetworkCapabilities();

    State getInflateSignalStrength();

    State getNetworkName();

    State getNumberOfLevels();

    State getOperatorAlphaShort();

    State getPrimaryLevel();

    State getResolvedNetworkType();

    State getSatelliteLevel();

    int getSubId();

    TableLogBuffer getTableLogBuffer();

    State isAllowedDuringAirplaneMode();

    State isEmergencyOnly();

    State isGsm();

    State isInEcmMode();

    State isInService();

    State isNonTerrestrial();

    State isRoaming();
}
