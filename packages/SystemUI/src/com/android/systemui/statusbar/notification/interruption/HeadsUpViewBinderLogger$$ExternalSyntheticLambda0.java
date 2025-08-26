package com.android.systemui.statusbar.notification.interruption;

import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class HeadsUpViewBinderLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("aborted potential ongoing heads up entry binding ", logMessage.getStr1(), " ");
            case 1:
                return "start binding heads up entry " + logMessage.getStr1() + ". isPinnedByUser=" + logMessage.getBool1() + " ";
            case 2:
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("heads up entry bind stage params null on unbind ", logMessage.getStr1(), " ");
            case 3:
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("start unbinding heads up entry ", logMessage.getStr1(), " ");
            case 4:
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("heads up entry bound successfully ", logMessage.getStr1(), " ");
            default:
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("heads up entry unbound successfully ", logMessage.getStr1(), " ");
        }
    }
}
