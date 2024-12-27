package com.android.systemui.statusbar.connectivity;

import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import java.util.Locale;
import kotlin.jvm.functions.Function1;

public final /* synthetic */ class NetworkControllerImpl$$ExternalSyntheticLambda8 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        Locale locale = Locale.US;
        return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Received broadcast with action \"", ((LogMessage) obj).getStr1(), "\"");
    }
}
