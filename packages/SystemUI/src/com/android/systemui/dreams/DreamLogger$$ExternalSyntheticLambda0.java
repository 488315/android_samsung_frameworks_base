package com.android.systemui.dreams;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class DreamLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ DreamLogger$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                return KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("Dream overlay status bar visible: ", logMessage.getBool1());
            case 1:
                return KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("Dream overlay enabled: ", logMessage.getBool1());
            case 2:
                return (logMessage.getBool1() ? "Showing" : "Hiding") + " dream status bar item: " + logMessage.getInt1();
            case 3:
                return KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("Dream overlay active: ", logMessage.getBool1());
            case 4:
                return KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("Low light mode active: ", logMessage.getBool1());
            case 5:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Add dream complication: ", logMessage.getStr1());
            case 6:
                return KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("Dream overlay should show complications: ", logMessage.getBool1());
            case 7:
                return KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("Dream overlay has Assistant attention: ", logMessage.getBool1());
            default:
                return MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "Available complication types: ");
        }
    }
}
