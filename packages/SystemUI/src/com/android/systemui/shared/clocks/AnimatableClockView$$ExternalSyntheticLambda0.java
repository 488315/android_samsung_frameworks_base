package com.android.systemui.shared.clocks;

import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.plugins.clocks.ClockLogger;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class AnimatableClockView$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                String str = AnimatableClockView.TAG;
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("refreshFormat(", ClockLogger.Companion.escapeTime(logMessage.getStr1()), ")");
            case 1:
                String str2 = AnimatableClockView.TAG;
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("onTextChanged(", ClockLogger.Companion.escapeTime(logMessage.getStr1()), ")");
            case 2:
                String str3 = AnimatableClockView.TAG;
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("refreshTime: new formattedText=", ClockLogger.Companion.escapeTime(logMessage.getStr1()));
            case 3:
                String str4 = AnimatableClockView.TAG;
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("refreshTime: done setting new time text to: ", ClockLogger.Companion.escapeTime(logMessage.getStr1()));
            default:
                String str5 = AnimatableClockView.TAG;
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("onTimeZoneChanged(", logMessage.getStr1(), ")");
        }
    }
}
