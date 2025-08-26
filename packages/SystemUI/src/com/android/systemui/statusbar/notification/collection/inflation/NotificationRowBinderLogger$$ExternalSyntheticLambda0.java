package com.android.systemui.statusbar.notification.collection.inflation;

import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class NotificationRowBinderLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("not releasing views for ", logMessage.getStr1(), ": row doesn't exist");
            case 1:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("releasing views for ", logMessage.getStr1());
            case 2:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("inflating row for ", logMessage.getStr1());
            case 3:
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("updating row for ", logMessage.getStr1(), ": ", logMessage.getStr2());
            case 4:
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("creating row for ", logMessage.getStr1(), ": ", logMessage.getStr2());
            case 5:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("inflated row for ", logMessage.getStr1());
            case 6:
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("requesting rebind for ", logMessage.getStr1(), ": ", logMessage.getStr2());
            default:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("rebind complete for ", logMessage.getStr1());
        }
    }
}
