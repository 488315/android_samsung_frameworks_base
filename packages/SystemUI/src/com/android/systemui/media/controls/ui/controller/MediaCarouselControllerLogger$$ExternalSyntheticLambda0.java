package com.android.systemui.media.controls.ui.controller;

import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class MediaCarouselControllerLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                int int1 = logMessage.getInt1();
                boolean bool1 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                StringBuilder m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("media host visibility changed location=", int1, ", visible:", bool1, ", was:");
                m.append(bool2);
                return m.toString();
            case 1:
                return "hiding carousel";
            case 2:
                return "showing carousel";
            default:
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Potential memory leak: Removing control panel for ", logMessage.getStr1(), " from map without calling #onDestroy");
        }
    }
}
