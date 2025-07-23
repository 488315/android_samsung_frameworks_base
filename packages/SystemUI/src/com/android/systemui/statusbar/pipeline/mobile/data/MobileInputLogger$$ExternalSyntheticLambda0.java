package com.android.systemui.statusbar.pipeline.mobile.data;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import androidx.compose.runtime.external.kotlinx.collections.immutable.internal.ListImplementation$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.BiometricMessageDeferralLogger$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class MobileInputLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                return BiometricMessageDeferralLogger$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "defaultMobileIconMapping(", "): ", logMessage.getStr1());
            case 1:
                return "onDataEnabledChanged: subId=" + logMessage.getInt1() + " enabled=" + logMessage.getBool1();
            case 2:
                return "Intent: ACTION_SERVICE_PROVIDERS_UPDATED. showSpn=" + logMessage.getBool1() + " spn=" + logMessage.getStr1() + " dataSpn=" + logMessage.getStr2() + " showPlmn=" + logMessage.getBool2() + " plmn=" + logMessage.getStr3();
            case 3:
                int int1 = logMessage.getInt1();
                String str1 = logMessage.getStr1();
                boolean bool1 = logMessage.getBool1();
                StringBuilder m = KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0.m(int1, "onDisplayInfoChanged: subId=", " displayInfo=", str1, " isRoaming=");
                m.append(bool1);
                return m.toString();
            case 4:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("defaultMobileIconGroup: ", logMessage.getStr1());
            case 5:
                return ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "Found prioritized network (nedId=", ")");
            case 6:
                return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "onCarrierConfigChanged: subId=");
            case 7:
                return ListImplementation$$ExternalSyntheticOutline0.m(logMessage.getInt1(), logMessage.getInt2(), "onDataActivity: subId=", " direction=");
            case 8:
                return "Intent received: com.samsung.carrier.action.CARRIER_CHANGED";
            case 9:
                return "onSubscriptionsChanged";
            case 10:
                return KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("onImsRegStateChanged: slotId=", logMessage.getInt1(), " voWifi=", logMessage.getBool1(), " voLTE="), logMessage.getBool2(), " ePDG=", logMessage.getBool3());
            case 11:
                return ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "Lost prioritized network (nedId=", ")");
            case 12:
                return BiometricMessageDeferralLogger$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "mobileIconMappingTable(", "): ", logMessage.getStr1());
            case 13:
                return "simSettingChanged: slot=" + logMessage.getInt1() + " simOn=" + logMessage.getBool1();
            case 14:
                return ListImplementation$$ExternalSyntheticOutline0.m(logMessage.getInt1(), logMessage.getInt2(), "onSlotId: slotId=", " subId=");
            case 15:
                return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "CarrierIdChanged: carrierId= ");
            case 16:
                return "satelliteEnabled: " + logMessage.getBool1() + ", ril.tiantong.phone.id: " + logMessage.getInt1();
            case 17:
                return ListImplementation$$ExternalSyntheticOutline0.m(logMessage.getInt2(), logMessage.getInt1(), "OnSemSatelliteSignalStrengthChanged:  subId=", " level=");
            case 18:
                return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "onCarrierRoamingNtnSignalStrengthChanged: level=");
            case 19:
                int int12 = logMessage.getInt1();
                String str12 = logMessage.getStr1();
                String str2 = logMessage.getStr2();
                StringBuilder m2 = KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0.m(int12, "OnSemSatelliteServiceStateChanged: subId=", " radioState=", str12, " regiState=");
                m2.append(str2);
                return m2.toString();
            case 20:
                return ListImplementation$$ExternalSyntheticOutline0.m(logMessage.getInt1(), logMessage.getInt2(), "onCallStateChanged: subId=", " callState=");
            case 21:
                int int13 = logMessage.getInt1();
                boolean bool12 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                boolean bool3 = logMessage.getBool3();
                boolean bool4 = logMessage.getBool4();
                String str13 = logMessage.getStr1();
                int int2 = logMessage.getInt2();
                StringBuilder m3 = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("onServiceStateChanged: subId=", int13, " connected=", bool12, " emergencyOnly=");
                KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(m3, bool2, " roaming=", bool3, " isNTN=");
                m3.append(bool4);
                m3.append(" operator=");
                m3.append(str13);
                m3.append(" voiceNetworkType=");
                m3.append(int2);
                return m3.toString();
            case 22:
                return "onCarrierNetworkChange: subId=" + logMessage.getInt1() + " active=" + logMessage.getBool1();
            case 23:
                return "onCarrierRoamingNtnModeChanged: subId=" + logMessage.getInt1() + " active=" + logMessage.getBool1();
            case 24:
                return "Intent received: ACTION_CARRIER_CONFIG_CHANGED";
            case 25:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("defaultDataSubRatConfig: ", logMessage.getStr1());
            case 26:
                int int14 = logMessage.getInt1();
                int int22 = logMessage.getInt2();
                String str14 = logMessage.getStr1();
                StringBuilder m4 = MutableObjectList$$ExternalSyntheticOutline0.m(int14, int22, "onDataConnectionStateChanged: subId=", " dataState=", " networkType=");
                m4.append(str14);
                return m4.toString();
            case 27:
                int int15 = logMessage.getInt1();
                String str15 = logMessage.getStr1();
                int int23 = logMessage.getInt2();
                StringBuilder m5 = KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0.m(int15, "onSignalStrengthsChanged: subId=", " strengths=", str15, " primaryLevel=");
                m5.append(int23);
                return m5.toString();
            default:
                return "Intent received: com.samsung.intent.action.OMC_CHANGED";
        }
    }
}
