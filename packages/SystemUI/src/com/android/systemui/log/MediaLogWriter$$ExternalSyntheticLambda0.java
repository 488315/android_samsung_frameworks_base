package com.android.systemui.log;

import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class MediaLogWriter$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                return " Media player updated [" + logMessage.getStr1() + "] isPlaying[" + logMessage.getBool1() + "]";
            case 1:
                return " Media player added [" + logMessage.getStr1() + "] isPlaying[" + logMessage.getBool1() + "]";
            case 2:
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m(" Media player removed [", logMessage.getStr1(), "]");
            case 3:
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m(" Media player removed error [", logMessage.getStr1(), "]");
            case 4:
                return MotionLayout$$ExternalSyntheticOutline0.m(" Media data removed[", logMessage.getStr1(), "] \n callStack[", logMessage.getStr2(), "]");
            case 5:
                return ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(logMessage.getInt1(), " Media config changed data size is [", "]");
            case 6:
                return ContentInViewNode$Request$$ExternalSyntheticOutline0.m(" Media remove clicked [", logMessage.getStr1(), "]");
            default:
                String str1 = logMessage.getStr1();
                return TransitionKt$$ExternalSyntheticOutline0.m(ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m888m(logMessage.getInt1(), " Media action clicked [", str1, "][", "]["), logMessage.getStr2(), "]");
        }
    }
}
