package com.android.systemui.media.controls.ui.controller;

import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class MediaCarouselControllerLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                int int1 = logMessage.getInt1();
                boolean bool1 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                StringBuilder sbM = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("media host visibility changed location=", int1, ", visible:", bool1, ", was:");
                sbM.append(bool2);
                return sbM.toString();
            case 1:
                return "hiding carousel";
            case 2:
                return "showing carousel";
            default:
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Potential memory leak: Removing control panel for ", logMessage.getStr1(), " from map without calling #onDestroy");
        }
    }
}
