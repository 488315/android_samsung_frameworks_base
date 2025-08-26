package com.android.keyguard.logging;

import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardTransitionAnimationLogger$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        switch (this.$r8$classId) {
            case 0:
                return logMessage.getStr1() + " transitionStep=" + logMessage.getStr2() + ", animationValue=" + logMessage.getStr3();
            default:
                return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("[", logMessage.getStr1(), "] starts at: ", logMessage.getStr2());
        }
    }
}
