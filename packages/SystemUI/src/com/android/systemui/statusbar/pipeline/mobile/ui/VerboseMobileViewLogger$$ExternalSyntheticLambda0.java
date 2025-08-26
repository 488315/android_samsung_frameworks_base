package com.android.systemui.statusbar.pipeline.mobile.ui;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class VerboseMobileViewLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                int int1 = logMessage.getInt1();
                String str1 = logMessage.getStr1();
                int int2 = logMessage.getInt2();
                boolean bool1 = logMessage.getBool1();
                StringBuilder sbM = KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0.m(int1, "Binder[subId=", ", viewId=", str1, "] received new signal icon: level=");
                sbM.append(int2);
                sbM.append(" showExclamation=");
                sbM.append(bool1);
                return sbM.toString();
            case 1:
                int int12 = logMessage.getInt1();
                String str12 = logMessage.getStr1();
                boolean bool12 = logMessage.getBool1();
                StringBuilder sbM2 = KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0.m(int12, "Binder[subId=", ", viewId=", str12, "] received visibility: ");
                sbM2.append(bool12);
                return sbM2.toString();
            default:
                int int13 = logMessage.getInt1();
                String str13 = logMessage.getStr1();
                String strM = logMessage.getBool1() ? MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(logMessage.getInt2(), "resId=") : "null";
                StringBuilder sbM3 = KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0.m(int13, "Binder[subId=", ", viewId=", str13, "] received new network type icon: ");
                sbM3.append(strM);
                return sbM3.toString();
        }
    }
}
