package com.android.systemui.statusbar.pipeline.mobile.data;

import android.content.Intent;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.TelephonyDisplayInfo;
import android.telephony.satellite.NtnSignalStrength;
import android.telephony.satellite.SemSatelliteRegistrationStateResult;
import android.telephony.satellite.SemSatelliteServiceState;
import android.telephony.satellite.SemSatelliteSignalStrength;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.lazy.LazyListMeasuredItem$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.HeightInLinesModifierKt$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.BiometricMessageDeferralLogger$logUpdateMessage$2$$ExternalSyntheticOutline0;
import com.android.settingslib.SignalIcon$MobileIconGroup;
import com.android.settingslib.Utils;
import com.android.settingslib.mobile.MobileMappings;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.pipeline.shared.data.model.ImsRegState;
import java.util.Map;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class MobileInputLogger {
    public final LogBuffer buffer;

    public MobileInputLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public final void logActionCarrierConfigChanged() {
        LogLevel logLevel = LogLevel.INFO;
        MobileInputLogger$logActionCarrierConfigChanged$2 mobileInputLogger$logActionCarrierConfigChanged$2 = new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger$logActionCarrierConfigChanged$2
            @Override // kotlin.jvm.functions.Function1
            public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return "Intent received: ACTION_CARRIER_CONFIG_CHANGED";
            }
        };
        LogBuffer logBuffer = this.buffer;
        logBuffer.commit(logBuffer.obtain("MobileInputLog", logLevel, mobileInputLogger$logActionCarrierConfigChanged$2, null));
    }

    public final void logCarrierConfigChanged(int i) {
        LogLevel logLevel = LogLevel.INFO;
        MobileInputLogger$logCarrierConfigChanged$2 mobileInputLogger$logCarrierConfigChanged$2 = new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger$logCarrierConfigChanged$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(((LogMessage) obj).getInt1(), "onCarrierConfigChanged: subId=");
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MobileInputLog", logLevel, mobileInputLogger$logCarrierConfigChanged$2, null);
        ((LogMessageImpl) obtain).int1 = i;
        logBuffer.commit(obtain);
    }

    public final void logDefaultDataSubRatConfig(MobileMappings.Config config) {
        LogLevel logLevel = LogLevel.INFO;
        MobileInputLogger$logDefaultDataSubRatConfig$2 mobileInputLogger$logDefaultDataSubRatConfig$2 = new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger$logDefaultDataSubRatConfig$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("defaultDataSubRatConfig: ", ((LogMessage) obj).getStr1());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MobileInputLog", logLevel, mobileInputLogger$logDefaultDataSubRatConfig$2, null);
        ((LogMessageImpl) obtain).str1 = config.toString();
        logBuffer.commit(obtain);
    }

    public final void logDefaultMobileIconGroup(SignalIcon$MobileIconGroup signalIcon$MobileIconGroup) {
        LogLevel logLevel = LogLevel.INFO;
        MobileInputLogger$logDefaultMobileIconGroup$2 mobileInputLogger$logDefaultMobileIconGroup$2 = new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger$logDefaultMobileIconGroup$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("defaultMobileIconGroup: ", ((LogMessage) obj).getStr1());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MobileInputLog", logLevel, mobileInputLogger$logDefaultMobileIconGroup$2, null);
        ((LogMessageImpl) obtain).str1 = signalIcon$MobileIconGroup.name;
        logBuffer.commit(obtain);
    }

    public final void logDefaultMobileIconMapping(int i, Map map) {
        LogLevel logLevel = LogLevel.INFO;
        MobileInputLogger$logDefaultMobileIconMapping$2 mobileInputLogger$logDefaultMobileIconMapping$2 = new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger$logDefaultMobileIconMapping$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return BiometricMessageDeferralLogger$logUpdateMessage$2$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "defaultMobileIconMapping(", "): ", logMessage.getStr1());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MobileInputLog", logLevel, mobileInputLogger$logDefaultMobileIconMapping$2, null);
        ((LogMessageImpl) obtain).int1 = i;
        ((LogMessageImpl) obtain).str1 = map.toString();
        logBuffer.commit(obtain);
    }

    public final void logImsRegState(int i, ImsRegState imsRegState) {
        LogLevel logLevel = LogLevel.INFO;
        MobileInputLogger$logImsRegState$2 mobileInputLogger$logImsRegState$2 = new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger$logImsRegState$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                int int1 = logMessage.getInt1();
                boolean bool1 = logMessage.getBool1();
                return KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("onImsRegStateChanged: slotId=", int1, " voWifi=", bool1, " voLTE="), logMessage.getBool2(), " ePDG=", logMessage.getBool3());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MobileInputLog", logLevel, mobileInputLogger$logImsRegState$2, null);
        ((LogMessageImpl) obtain).int1 = i;
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.bool1 = imsRegState.voWifiRegState;
        logMessageImpl.bool2 = imsRegState.voLTERegState;
        logMessageImpl.bool3 = imsRegState.ePDGRegState;
        logBuffer.commit(obtain);
    }

    public final void logMobileIconMappingTable(int i, Map map) {
        LogLevel logLevel = LogLevel.INFO;
        MobileInputLogger$logMobileIconMappingTable$2 mobileInputLogger$logMobileIconMappingTable$2 = new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger$logMobileIconMappingTable$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return BiometricMessageDeferralLogger$logUpdateMessage$2$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "mobileIconMappingTable(", "): ", logMessage.getStr1());
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MobileInputLog", logLevel, mobileInputLogger$logMobileIconMappingTable$2, null);
        ((LogMessageImpl) obtain).int1 = i;
        ((LogMessageImpl) obtain).str1 = map.toString();
        logBuffer.commit(obtain);
    }

    public final void logNtnSignalStrengthChanged(NtnSignalStrength ntnSignalStrength) {
        LogLevel logLevel = LogLevel.INFO;
        MobileInputLogger$logNtnSignalStrengthChanged$2 mobileInputLogger$logNtnSignalStrengthChanged$2 = new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger$logNtnSignalStrengthChanged$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(((LogMessage) obj).getInt1(), "onCarrierRoamingNtnSignalStrengthChanged: level=");
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MobileInputLog", logLevel, mobileInputLogger$logNtnSignalStrengthChanged$2, null);
        ((LogMessageImpl) obtain).int1 = ntnSignalStrength.getLevel();
        logBuffer.commit(obtain);
    }

    public final void logOnCallStateChanged(int i, int i2) {
        LogLevel logLevel = LogLevel.INFO;
        MobileInputLogger$logOnCallStateChanged$2 mobileInputLogger$logOnCallStateChanged$2 = new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger$logOnCallStateChanged$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return HeightInLinesModifierKt$$ExternalSyntheticOutline0.m(logMessage.getInt1(), logMessage.getInt2(), "onCallStateChanged: subId=", " callState=");
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MobileInputLog", logLevel, mobileInputLogger$logOnCallStateChanged$2, null);
        ((LogMessageImpl) obtain).int1 = i2;
        ((LogMessageImpl) obtain).int2 = i;
        logBuffer.commit(obtain);
    }

    public final void logOnCarrierNetworkChange(int i, boolean z) {
        LogLevel logLevel = LogLevel.INFO;
        MobileInputLogger$logOnCarrierNetworkChange$2 mobileInputLogger$logOnCarrierNetworkChange$2 = new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger$logOnCarrierNetworkChange$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return "onCarrierNetworkChange: subId=" + logMessage.getInt1() + " active=" + logMessage.getBool1();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MobileInputLog", logLevel, mobileInputLogger$logOnCarrierNetworkChange$2, null);
        ((LogMessageImpl) obtain).int1 = i;
        ((LogMessageImpl) obtain).bool1 = z;
        logBuffer.commit(obtain);
    }

    public final void logOnCarrierRoamingNtnModeChanged(int i, boolean z) {
        LogLevel logLevel = LogLevel.INFO;
        MobileInputLogger$logOnCarrierRoamingNtnModeChanged$2 mobileInputLogger$logOnCarrierRoamingNtnModeChanged$2 = new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger$logOnCarrierRoamingNtnModeChanged$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return "onCarrierRoamingNtnModeChanged: subId=" + logMessage.getInt1() + " active=" + logMessage.getBool1();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MobileInputLog", logLevel, mobileInputLogger$logOnCarrierRoamingNtnModeChanged$2, null);
        ((LogMessageImpl) obtain).int1 = i;
        ((LogMessageImpl) obtain).bool1 = z;
        logBuffer.commit(obtain);
    }

    public final void logOnDataActivity(int i, int i2) {
        LogLevel logLevel = LogLevel.INFO;
        MobileInputLogger$logOnDataActivity$2 mobileInputLogger$logOnDataActivity$2 = new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger$logOnDataActivity$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return HeightInLinesModifierKt$$ExternalSyntheticOutline0.m(logMessage.getInt1(), logMessage.getInt2(), "onDataActivity: subId=", " direction=");
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MobileInputLog", logLevel, mobileInputLogger$logOnDataActivity$2, null);
        ((LogMessageImpl) obtain).int1 = i2;
        ((LogMessageImpl) obtain).int2 = i;
        logBuffer.commit(obtain);
    }

    public final void logOnDataConnectionStateChanged(int i, int i2, int i3) {
        LogLevel logLevel = LogLevel.INFO;
        MobileInputLogger$logOnDataConnectionStateChanged$2 mobileInputLogger$logOnDataConnectionStateChanged$2 = new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger$logOnDataConnectionStateChanged$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                int int1 = logMessage.getInt1();
                int int2 = logMessage.getInt2();
                String str1 = logMessage.getStr1();
                StringBuilder m = RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(int1, int2, "onDataConnectionStateChanged: subId=", " dataState=", " networkType=");
                m.append(str1);
                return m.toString();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MobileInputLog", logLevel, mobileInputLogger$logOnDataConnectionStateChanged$2, null);
        ((LogMessageImpl) obtain).int1 = i3;
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.int2 = i;
        logMessageImpl.str1 = String.valueOf(i2);
        logBuffer.commit(obtain);
    }

    public final void logOnDataEnabledChanged(int i, boolean z) {
        LogLevel logLevel = LogLevel.INFO;
        MobileInputLogger$logOnDataEnabledChanged$2 mobileInputLogger$logOnDataEnabledChanged$2 = new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger$logOnDataEnabledChanged$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return "onDataEnabledChanged: subId=" + logMessage.getInt1() + " enabled=" + logMessage.getBool1();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MobileInputLog", logLevel, mobileInputLogger$logOnDataEnabledChanged$2, null);
        ((LogMessageImpl) obtain).int1 = i;
        ((LogMessageImpl) obtain).bool1 = z;
        logBuffer.commit(obtain);
    }

    public final void logOnDisplayInfoChanged(TelephonyDisplayInfo telephonyDisplayInfo, int i) {
        LogLevel logLevel = LogLevel.INFO;
        MobileInputLogger$logOnDisplayInfoChanged$2 mobileInputLogger$logOnDisplayInfoChanged$2 = new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger$logOnDisplayInfoChanged$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                int int1 = logMessage.getInt1();
                String str1 = logMessage.getStr1();
                boolean bool1 = logMessage.getBool1();
                StringBuilder m = KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0.m(int1, "onDisplayInfoChanged: subId=", " displayInfo=", str1, " isRoaming=");
                m.append(bool1);
                return m.toString();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MobileInputLog", logLevel, mobileInputLogger$logOnDisplayInfoChanged$2, null);
        ((LogMessageImpl) obtain).int1 = i;
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = telephonyDisplayInfo.toString();
        logMessageImpl.bool1 = telephonyDisplayInfo.isRoaming();
        logBuffer.commit(obtain);
    }

    public final void logOnSemSatelliteServiceStateChanged(SemSatelliteServiceState semSatelliteServiceState, int i) {
        LogLevel logLevel = LogLevel.INFO;
        MobileInputLogger$logOnSemSatelliteServiceStateChanged$2 mobileInputLogger$logOnSemSatelliteServiceStateChanged$2 = new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger$logOnSemSatelliteServiceStateChanged$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                int int1 = logMessage.getInt1();
                String str1 = logMessage.getStr1();
                String str2 = logMessage.getStr2();
                StringBuilder m = KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0.m(int1, "OnSemSatelliteServiceStateChanged: subId=", " radioState=", str1, " regiState=");
                m.append(str2);
                return m.toString();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MobileInputLog", logLevel, mobileInputLogger$logOnSemSatelliteServiceStateChanged$2, null);
        ((LogMessageImpl) obtain).int1 = i;
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = SemSatelliteServiceState.radioStateToString(semSatelliteServiceState.getRadioState());
        SemSatelliteRegistrationStateResult registrationState = semSatelliteServiceState.getRegistrationState();
        logMessageImpl.str2 = registrationState != null ? SemSatelliteRegistrationStateResult.regStateToString(registrationState.getRegState()) : null;
        logBuffer.commit(obtain);
    }

    public final void logOnSemSatelliteSignalStrengthChanged(SemSatelliteSignalStrength semSatelliteSignalStrength, int i) {
        LogLevel logLevel = LogLevel.INFO;
        MobileInputLogger$logOnSemSatelliteSignalStrengthChanged$2 mobileInputLogger$logOnSemSatelliteSignalStrengthChanged$2 = new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger$logOnSemSatelliteSignalStrengthChanged$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return HeightInLinesModifierKt$$ExternalSyntheticOutline0.m(logMessage.getInt2(), logMessage.getInt1(), "OnSemSatelliteSignalStrengthChanged:  subId=", " level=");
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MobileInputLog", logLevel, mobileInputLogger$logOnSemSatelliteSignalStrengthChanged$2, null);
        ((LogMessageImpl) obtain).int1 = semSatelliteSignalStrength.getLevel();
        ((LogMessageImpl) obtain).int2 = i;
        logBuffer.commit(obtain);
    }

    public final void logOnServiceStateChanged(int i, ServiceState serviceState) {
        LogLevel logLevel = LogLevel.INFO;
        MobileInputLogger$logOnServiceStateChanged$2 mobileInputLogger$logOnServiceStateChanged$2 = new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger$logOnServiceStateChanged$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                int int1 = logMessage.getInt1();
                boolean bool1 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                boolean bool3 = logMessage.getBool3();
                boolean bool4 = logMessage.getBool4();
                String str1 = logMessage.getStr1();
                int int2 = logMessage.getInt2();
                StringBuilder m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("onServiceStateChanged: subId=", int1, " connected=", bool1, " emergencyOnly=");
                KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(m, bool2, " roaming=", bool3, " isNTN=");
                m.append(bool4);
                m.append(" operator=");
                m.append(str1);
                m.append(" voiceNetworkType=");
                m.append(int2);
                return m.toString();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MobileInputLog", logLevel, mobileInputLogger$logOnServiceStateChanged$2, null);
        ((LogMessageImpl) obtain).int1 = i;
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.bool1 = Utils.isInService(serviceState);
        logMessageImpl.bool2 = serviceState.isEmergencyOnly();
        logMessageImpl.bool3 = serviceState.getRoaming();
        logMessageImpl.bool4 = serviceState.isUsingNonTerrestrialNetwork();
        logMessageImpl.str1 = serviceState.getOperatorAlphaShort();
        logMessageImpl.int2 = serviceState.getVoiceNetworkType();
        logBuffer.commit(obtain);
    }

    public final void logOnSignalStrengthsChanged(SignalStrength signalStrength, int i) {
        LogLevel logLevel = LogLevel.INFO;
        MobileInputLogger$logOnSignalStrengthsChanged$2 mobileInputLogger$logOnSignalStrengthsChanged$2 = new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger$logOnSignalStrengthsChanged$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                int int1 = logMessage.getInt1();
                String str1 = logMessage.getStr1();
                int int2 = logMessage.getInt2();
                StringBuilder m = KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0.m(int1, "onSignalStrengthsChanged: subId=", " strengths=", str1, " primaryLevel=");
                m.append(int2);
                return m.toString();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MobileInputLog", logLevel, mobileInputLogger$logOnSignalStrengthsChanged$2, null);
        ((LogMessageImpl) obtain).int1 = i;
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = signalStrength.toString();
        logMessageImpl.int2 = signalStrength.getVendorLevel();
        logBuffer.commit(obtain);
    }

    public final void logOnSubscriptionsChanged() {
        LogLevel logLevel = LogLevel.INFO;
        MobileInputLogger$logOnSubscriptionsChanged$2 mobileInputLogger$logOnSubscriptionsChanged$2 = new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger$logOnSubscriptionsChanged$2
            @Override // kotlin.jvm.functions.Function1
            public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return "onSubscriptionsChanged";
            }
        };
        LogBuffer logBuffer = this.buffer;
        logBuffer.commit(logBuffer.obtain("MobileInputLog", logLevel, mobileInputLogger$logOnSubscriptionsChanged$2, null));
    }

    public final void logPrioritizedNetworkAvailable(int i) {
        LogLevel logLevel = LogLevel.INFO;
        MobileInputLogger$logPrioritizedNetworkAvailable$2 mobileInputLogger$logPrioritizedNetworkAvailable$2 = new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger$logPrioritizedNetworkAvailable$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return LazyListMeasuredItem$$ExternalSyntheticOutline0.m(((LogMessage) obj).getInt1(), "Found prioritized network (nedId=", ")");
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MobileInputLog", logLevel, mobileInputLogger$logPrioritizedNetworkAvailable$2, null);
        ((LogMessageImpl) obtain).int1 = i;
        logBuffer.commit(obtain);
    }

    public final void logPrioritizedNetworkLost(int i) {
        LogLevel logLevel = LogLevel.INFO;
        MobileInputLogger$logPrioritizedNetworkLost$2 mobileInputLogger$logPrioritizedNetworkLost$2 = new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger$logPrioritizedNetworkLost$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return LazyListMeasuredItem$$ExternalSyntheticOutline0.m(((LogMessage) obj).getInt1(), "Lost prioritized network (nedId=", ")");
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MobileInputLog", logLevel, mobileInputLogger$logPrioritizedNetworkLost$2, null);
        ((LogMessageImpl) obtain).int1 = i;
        logBuffer.commit(obtain);
    }

    public final void logSatelliteEnabled(int i, boolean z) {
        LogLevel logLevel = LogLevel.INFO;
        MobileInputLogger$logSatelliteEnabled$2 mobileInputLogger$logSatelliteEnabled$2 = new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger$logSatelliteEnabled$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return "satelliteEnabled: " + logMessage.getBool1() + ", ril.tiantong.phone.id: " + logMessage.getInt1();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MobileInputLog", logLevel, mobileInputLogger$logSatelliteEnabled$2, null);
        ((LogMessageImpl) obtain).bool1 = z;
        ((LogMessageImpl) obtain).int1 = i;
        logBuffer.commit(obtain);
    }

    public final void logSemCarrierChanged() {
        LogLevel logLevel = LogLevel.INFO;
        MobileInputLogger$logSemCarrierChanged$2 mobileInputLogger$logSemCarrierChanged$2 = new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger$logSemCarrierChanged$2
            @Override // kotlin.jvm.functions.Function1
            public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return "Intent received: com.samsung.carrier.action.CARRIER_CHANGED";
            }
        };
        LogBuffer logBuffer = this.buffer;
        logBuffer.commit(logBuffer.obtain("MobileInputLog", logLevel, mobileInputLogger$logSemCarrierChanged$2, null));
    }

    public final void logSemOMCChanged() {
        LogLevel logLevel = LogLevel.INFO;
        MobileInputLogger$logSemOMCChanged$2 mobileInputLogger$logSemOMCChanged$2 = new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger$logSemOMCChanged$2
            @Override // kotlin.jvm.functions.Function1
            public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                return "Intent received: com.samsung.intent.action.OMC_CHANGED";
            }
        };
        LogBuffer logBuffer = this.buffer;
        logBuffer.commit(logBuffer.obtain("MobileInputLog", logLevel, mobileInputLogger$logSemOMCChanged$2, null));
    }

    public final void logServiceProvidersUpdatedBroadcast(Intent intent) {
        boolean booleanExtra = intent.getBooleanExtra("android.telephony.extra.SHOW_SPN", false);
        String stringExtra = intent.getStringExtra("android.telephony.extra.DATA_SPN");
        boolean booleanExtra2 = intent.getBooleanExtra("android.telephony.extra.SHOW_PLMN", false);
        String stringExtra2 = intent.getStringExtra("android.telephony.extra.PLMN");
        LogLevel logLevel = LogLevel.INFO;
        MobileInputLogger$logServiceProvidersUpdatedBroadcast$2 mobileInputLogger$logServiceProvidersUpdatedBroadcast$2 = new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger$logServiceProvidersUpdatedBroadcast$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return "Intent: ACTION_SERVICE_PROVIDERS_UPDATED. showSpn=" + logMessage.getBool1() + " spn=" + logMessage.getStr1() + " showPlmn=" + logMessage.getBool2() + " plmn=" + logMessage.getStr2();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MobileInputLog", logLevel, mobileInputLogger$logServiceProvidersUpdatedBroadcast$2, null);
        ((LogMessageImpl) obtain).bool1 = booleanExtra;
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.str1 = stringExtra;
        logMessageImpl.bool2 = booleanExtra2;
        logMessageImpl.str2 = stringExtra2;
        logBuffer.commit(obtain);
    }

    public final void logSimSettingChanged(int i, boolean z) {
        LogLevel logLevel = LogLevel.INFO;
        MobileInputLogger$logSimSettingChanged$2 mobileInputLogger$logSimSettingChanged$2 = new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger$logSimSettingChanged$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return "simSettingChanged: slot=" + logMessage.getInt1() + " simOn=" + logMessage.getBool1();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MobileInputLog", logLevel, mobileInputLogger$logSimSettingChanged$2, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
        logMessageImpl.int1 = i;
        logMessageImpl.bool1 = z;
        logBuffer.commit(obtain);
    }

    public final void logSlotId(int i, int i2) {
        LogLevel logLevel = LogLevel.INFO;
        MobileInputLogger$logSlotId$2 mobileInputLogger$logSlotId$2 = new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger$logSlotId$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return HeightInLinesModifierKt$$ExternalSyntheticOutline0.m(logMessage.getInt1(), logMessage.getInt2(), "onSlotId: slotId=", " subId=");
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MobileInputLog", logLevel, mobileInputLogger$logSlotId$2, null);
        ((LogMessageImpl) obtain).int1 = i;
        ((LogMessageImpl) obtain).int2 = i2;
        logBuffer.commit(obtain);
    }

    public final void logTopLevelServiceStateBroadcastEmergencyOnly(int i, ServiceState serviceState) {
        LogLevel logLevel = LogLevel.INFO;
        MobileInputLogger$logTopLevelServiceStateBroadcastEmergencyOnly$2 mobileInputLogger$logTopLevelServiceStateBroadcastEmergencyOnly$2 = new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger$logTopLevelServiceStateBroadcastEmergencyOnly$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                return "ACTION_SERVICE_STATE for subId=" + logMessage.getInt1() + ". ServiceState.isEmergencyOnly=" + logMessage.getBool1();
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MobileInputLog", logLevel, mobileInputLogger$logTopLevelServiceStateBroadcastEmergencyOnly$2, null);
        ((LogMessageImpl) obtain).int1 = i;
        ((LogMessageImpl) obtain).bool1 = serviceState.isEmergencyOnly();
        logBuffer.commit(obtain);
    }

    public final void logTopLevelServiceStateBroadcastMissingExtras(int i) {
        LogLevel logLevel = LogLevel.INFO;
        MobileInputLogger$logTopLevelServiceStateBroadcastMissingExtras$2 mobileInputLogger$logTopLevelServiceStateBroadcastMissingExtras$2 = new Function1() { // from class: com.android.systemui.statusbar.pipeline.mobile.data.MobileInputLogger$logTopLevelServiceStateBroadcastMissingExtras$2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return LazyListMeasuredItem$$ExternalSyntheticOutline0.m(((LogMessage) obj).getInt1(), "ACTION_SERVICE_STATE for subId=", ". Intent is missing extras. Ignoring");
            }
        };
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("MobileInputLog", logLevel, mobileInputLogger$logTopLevelServiceStateBroadcastMissingExtras$2, null);
        ((LogMessageImpl) obtain).int1 = i;
        logBuffer.commit(obtain);
    }
}
