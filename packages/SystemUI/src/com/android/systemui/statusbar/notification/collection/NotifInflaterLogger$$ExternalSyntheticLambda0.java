package com.android.systemui.statusbar.notification.collection;

import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class NotifInflaterLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("exception inflating views for ", logMessage.getStr1(), ": ", logMessage.getStr2());
            case 1:
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("inflating views for ", logMessage.getStr1(), ": ", logMessage.getStr2());
            case 2:
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("rebinding views for ", logMessage.getStr1(), ": ", logMessage.getStr2());
            case 3:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("aborting inflation for ", logMessage.getStr1());
            case 4:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("aborted task to abort inflation for ", logMessage.getStr1());
            case 5:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("rebound views for ", logMessage.getStr1());
            default:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("inflated views for ", logMessage.getStr1());
        }
    }
}
