package com.android.systemui.communal.domain.interactor;

import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class CommunalInteractor$isCommunalShowing$3$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                return "Communal is ".concat(logMessage.getBool1() ? "showing" : "gone");
            default:
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Communal is ", logMessage.getBool1() ? "" : "un", "available");
        }
    }
}
