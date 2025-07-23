package com.android.systemui.media.taptotransfer.sender;

import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class MediaTttSenderLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                int i = MediaTttSenderLogger.$r8$clinit;
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("State removal: id=", logMessage.getStr1(), " reason=", logMessage.getStr2());
            case 1:
                int i2 = MediaTttSenderLogger.$r8$clinit;
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Current sender states: ", logMessage.getStr1());
            case 2:
                int i3 = MediaTttSenderLogger.$r8$clinit;
                return MotionLayout$$ExternalSyntheticOutline0.m("Cannot display state=", logMessage.getStr2(), " after state=", logMessage.getStr1(), "; invalid transition");
            default:
                int i4 = MediaTttSenderLogger.$r8$clinit;
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("Chip removal requested due to ", logMessage.getStr1(), "; however, removal was ignored because ", logMessage.getStr2());
        }
    }
}
