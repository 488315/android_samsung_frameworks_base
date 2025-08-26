package com.android.systemui.statusbar.pipeline.wifi.data.repository.prod;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class WifiRepositoryImpl$$ExternalSyntheticLambda3 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ WifiRepositoryImpl$$ExternalSyntheticLambda3(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                WifiRepositoryImpl.Companion companion = WifiRepositoryImpl.Companion;
                return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "ReceivedInetCondition: ");
            case 1:
                WifiRepositoryImpl.Companion companion2 = WifiRepositoryImpl.Companion;
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("onWifiInfo: ", logMessage.getStr1());
            case 2:
                WifiRepositoryImpl.Companion companion3 = WifiRepositoryImpl.Companion;
                return KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("hideDuringSwitching: ", logMessage.getBool1());
            case 3:
                WifiRepositoryImpl.Companion companion4 = WifiRepositoryImpl.Companion;
                return KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("testReported: ", logMessage.getBool1());
            case 4:
                WifiRepositoryImpl.Companion companion5 = WifiRepositoryImpl.Companion;
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("onWifiEntriesChanged. ConnectedEntry=", logMessage.getStr1());
            case 5:
                WifiRepositoryImpl.Companion companion6 = WifiRepositoryImpl.Companion;
                return "onWifiStateChanged. State=" + (logMessage.getInt1() == -1 ? null : Integer.valueOf(logMessage.getInt1()));
            case 6:
                WifiRepositoryImpl.Companion companion7 = WifiRepositoryImpl.Companion;
                return "onScanResultsAvailable";
            default:
                WifiRepositoryImpl.Companion companion8 = WifiRepositoryImpl.Companion;
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("onActivityChanged: ", logMessage.getStr1());
        }
    }
}
