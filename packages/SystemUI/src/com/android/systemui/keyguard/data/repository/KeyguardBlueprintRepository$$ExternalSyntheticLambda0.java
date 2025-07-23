package com.android.systemui.keyguard.data.repository;

import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class KeyguardBlueprintRepository$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Could not find blueprint with id: ", logMessage.getStr1(), ". Perhaps it was not added to KeyguardBlueprintModule?");
            case 1:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Skipping low priority transition: ", logMessage.getStr1());
            default:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("refreshBlueprint: Failed to emit blueprint refresh: ", logMessage.getStr1());
        }
    }
}
