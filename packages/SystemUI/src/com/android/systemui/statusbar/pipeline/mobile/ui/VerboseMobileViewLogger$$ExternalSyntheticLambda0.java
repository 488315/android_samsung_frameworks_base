package com.android.systemui.statusbar.pipeline.mobile.ui;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class VerboseMobileViewLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                int int1 = logMessage.getInt1();
                String str1 = logMessage.getStr1();
                int int2 = logMessage.getInt2();
                boolean bool1 = logMessage.getBool1();
                StringBuilder m = KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0.m(int1, "Binder[subId=", ", viewId=", str1, "] received new signal icon: level=");
                m.append(int2);
                m.append(" showExclamation=");
                m.append(bool1);
                return m.toString();
            case 1:
                int int12 = logMessage.getInt1();
                String str12 = logMessage.getStr1();
                boolean bool12 = logMessage.getBool1();
                StringBuilder m2 = KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0.m(int12, "Binder[subId=", ", viewId=", str12, "] received visibility: ");
                m2.append(bool12);
                return m2.toString();
            default:
                int int13 = logMessage.getInt1();
                String str13 = logMessage.getStr1();
                String m3 = logMessage.getBool1() ? MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(logMessage.getInt2(), "resId=") : "null";
                StringBuilder m4 = KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1$$ExternalSyntheticOutline0.m(int13, "Binder[subId=", ", viewId=", str13, "] received new network type icon: ");
                m4.append(m3);
                return m4.toString();
        }
    }
}
