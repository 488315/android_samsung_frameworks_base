package com.android.systemui.plugins.clocks;

import android.view.View;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.log.core.LogcatOnlyMessageBuffer;
import com.android.systemui.log.core.Logger;
import com.android.systemui.log.core.MessageBuffer;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;
import kotlin.text.StringsKt__StringsJVMKt;

/* loaded from: classes2.dex */
public final class ClockLogger extends Logger {
    private float loggedAlpha;
    private final View view;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;
    private static final LogcatOnlyMessageBuffer DEFAULT_MESSAGE_BUFFER = new LogcatOnlyMessageBuffer(LogLevel.INFO);
    private static final LogcatOnlyMessageBuffer DEBUG_MESSAGE_BUFFER = new LogcatOnlyMessageBuffer(LogLevel.DEBUG);
    private static final ClockLogger INIT_LOGGER = new ClockLogger(null, new LogcatOnlyMessageBuffer(LogLevel.ERROR), "CLOCK_INIT");

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final String escapeTime(String str) {
            if (str != null) {
                return StringsKt__StringsJVMKt.replace$default(str, "\n", "\\n");
            }
            return null;
        }

        public final LogcatOnlyMessageBuffer getDEBUG_MESSAGE_BUFFER() {
            return ClockLogger.DEBUG_MESSAGE_BUFFER;
        }

        public final LogcatOnlyMessageBuffer getDEFAULT_MESSAGE_BUFFER() {
            return ClockLogger.DEFAULT_MESSAGE_BUFFER;
        }

        public final ClockLogger getINIT_LOGGER() {
            return ClockLogger.INIT_LOGGER;
        }

        public final String getSpecText(int i) {
            int size = View.MeasureSpec.getSize(i);
            int mode = View.MeasureSpec.getMode(i);
            return "(" + size + ", " + (mode != Integer.MIN_VALUE ? mode != 0 ? mode != 1073741824 ? String.valueOf(mode) : "EXACTLY" : "UNSPECIFIED" : "AT MOST") + ")";
        }

        public final String getVisText(int i) {
            return i != 0 ? i != 4 ? i != 8 ? String.valueOf(i) : "GONE" : "INVISIBLE" : "VISIBLE";
        }

        private Companion() {
        }
    }

    public ClockLogger(View view, MessageBuffer messageBuffer, String str) {
        super(messageBuffer, str);
        this.view = view;
        this.loggedAlpha = 1000.0f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String animateDoze$lambda$16(LogMessage logMessage) {
        return "animateDoze(isDozing=" + logMessage.getBool1() + ", isAnimated=" + logMessage.getBool2() + ")";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String animateFidget$lambda$18(LogMessage logMessage) {
        return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("animateFidget(", VPointF.m2780toStringimpl(VPointF.Companion.m2785fromLongAsyRdg(logMessage.getLong1())), ")");
    }

    public static final String escapeTime(String str) {
        return Companion.escapeTime(str);
    }

    public static final String getSpecText(int i) {
        return Companion.getSpecText(i);
    }

    public static final String getVisText(int i) {
        return Companion.getVisText(i);
    }

    private final boolean isDrawn() {
        View view = this.view;
        return ((view != null ? view.mPrivateFlags : 0) & 32) > 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String onDraw$lambda$4(LogMessage logMessage) {
        return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("onDraw(", Companion.escapeTime(logMessage.getStr1()), ")");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String onDraw$lambda$6(LogMessage logMessage) {
        Companion companion = Companion;
        return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("onDraw(ls = ", companion.escapeTime(logMessage.getStr1()), ", aod = ", companion.escapeTime(logMessage.getStr2()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String onLayout$lambda$2(LogMessage logMessage) {
        return "onLayout(" + logMessage.getBool1() + ", " + VRect.m2818toStringimpl(VRect.Companion.m2822fromLongqYjogQA(logMessage.getLong1())) + ")";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String onMeasure$lambda$0(LogMessage logMessage) {
        Companion companion = Companion;
        return MotionLayout$$ExternalSyntheticOutline0.m("onMeasure(", companion.getSpecText(logMessage.getInt1()), ", ", companion.getSpecText(logMessage.getInt2()), ")");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String onViewAdded$lambda$14(LogMessage logMessage) {
        return ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "onViewAdded(", logMessage.getStr1(), " @", ")");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String setAlpha$lambda$10(LogMessage logMessage) {
        return "setAlpha(" + logMessage.getDouble1() + ")";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String setVisibility$lambda$8(LogMessage logMessage) {
        return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("setVisibility(", Companion.getVisText(logMessage.getInt1()), ")");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String updateAxes$lambda$12(LogMessage logMessage) {
        String str1 = logMessage.getStr1();
        String str2 = logMessage.getStr2();
        return MoveResult$$ExternalSyntheticOutline0.m(SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("updateAxes(LS = ", str1, ", AOD = ", str2, ", isAnimated="), logMessage.getBool1(), ")");
    }

    public final void animateCharge() {
        Logger.d$default(this, "animateCharge()", null, 2, null);
    }

    public final void animateDoze(boolean z, boolean z2) {
        ClockLogger$$ExternalSyntheticLambda0 clockLogger$$ExternalSyntheticLambda0 = new ClockLogger$$ExternalSyntheticLambda0(0);
        LogMessage logMessageObtain = getBuffer().obtain(getTag(), LogLevel.DEBUG, clockLogger$$ExternalSyntheticLambda0, null);
        logMessageObtain.setBool1(z);
        logMessageObtain.setBool2(z2);
        getBuffer().commit(logMessageObtain);
    }

    public final void animateFidget(float f, float f2) {
        ClockLogger$$ExternalSyntheticLambda0 clockLogger$$ExternalSyntheticLambda0 = new ClockLogger$$ExternalSyntheticLambda0(8);
        LogMessage logMessageObtain = getBuffer().obtain(getTag(), LogLevel.DEBUG, clockLogger$$ExternalSyntheticLambda0, null);
        logMessageObtain.setLong1(VPointF.m2778toLongimpl(VPointF.m2747constructorimpl(f, f2)));
        getBuffer().commit(logMessageObtain);
    }

    public final void invalidate() {
        View view;
        if (isDrawn() && (view = this.view) != null && view.getVisibility() == 0) {
            Logger.d$default(this, "invalidate()", null, 2, null);
        }
    }

    public final void onDraw() {
        Logger.d$default(this, "onDraw()", null, 2, null);
    }

    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        ClockLogger$$ExternalSyntheticLambda0 clockLogger$$ExternalSyntheticLambda0 = new ClockLogger$$ExternalSyntheticLambda0(4);
        LogMessage logMessageObtain = getBuffer().obtain(getTag(), LogLevel.DEBUG, clockLogger$$ExternalSyntheticLambda0, null);
        logMessageObtain.setBool1(z);
        logMessageObtain.setLong1(VRect.m2816toLongimpl(VRect.m2801constructorimpl(i, i2, i3, i4)));
        getBuffer().commit(logMessageObtain);
    }

    public final void onMeasure(int i, int i2) {
        ClockLogger$$ExternalSyntheticLambda0 clockLogger$$ExternalSyntheticLambda0 = new ClockLogger$$ExternalSyntheticLambda0(2);
        LogMessage logMessageObtain = getBuffer().obtain(getTag(), LogLevel.DEBUG, clockLogger$$ExternalSyntheticLambda0, null);
        logMessageObtain.setInt1(i);
        logMessageObtain.setInt2(i2);
        getBuffer().commit(logMessageObtain);
    }

    public final void onViewAdded(View view) {
        ClockLogger$$ExternalSyntheticLambda0 clockLogger$$ExternalSyntheticLambda0 = new ClockLogger$$ExternalSyntheticLambda0(3);
        LogMessage logMessageObtain = getBuffer().obtain(getTag(), LogLevel.DEBUG, clockLogger$$ExternalSyntheticLambda0, null);
        String simpleName = Reflection.getOrCreateKotlinClass(view.getClass()).getSimpleName();
        simpleName.getClass();
        logMessageObtain.setStr1(simpleName);
        logMessageObtain.setInt1(view.getId());
        getBuffer().commit(logMessageObtain);
    }

    public final void refreshTime() {
        Logger.d$default(this, "refreshTime()", null, 2, null);
    }

    public final void requestLayout() {
        View view = this.view;
        if (view == null || view.isLayoutRequested()) {
            return;
        }
        Logger.d$default(this, "requestLayout()", null, 2, null);
    }

    public final void setAlpha(float f) {
        if (Math.abs(this.loggedAlpha - f) >= ((f <= 0.0f || f >= 1.0f) ? 0.001f : 0.5f)) {
            this.loggedAlpha = f;
            LogMessage logMessageObtain = getBuffer().obtain(getTag(), LogLevel.DEBUG, new ClockLogger$$ExternalSyntheticLambda0(7), null);
            logMessageObtain.setDouble1(f);
            getBuffer().commit(logMessageObtain);
        }
    }

    public final void setVisibility(int i) {
        View view = this.view;
        if (view == null || i != view.getVisibility()) {
            ClockLogger$$ExternalSyntheticLambda0 clockLogger$$ExternalSyntheticLambda0 = new ClockLogger$$ExternalSyntheticLambda0(6);
            LogMessage logMessageObtain = getBuffer().obtain(getTag(), LogLevel.DEBUG, clockLogger$$ExternalSyntheticLambda0, null);
            logMessageObtain.setInt1(i);
            getBuffer().commit(logMessageObtain);
        }
    }

    public final void updateAxes(String str, String str2, boolean z) {
        ClockLogger$$ExternalSyntheticLambda0 clockLogger$$ExternalSyntheticLambda0 = new ClockLogger$$ExternalSyntheticLambda0(5);
        LogMessage logMessageObtain = getBuffer().obtain(getTag(), LogLevel.INFO, clockLogger$$ExternalSyntheticLambda0, null);
        logMessageObtain.setStr1(str);
        logMessageObtain.setStr2(str2);
        logMessageObtain.setBool1(z);
        getBuffer().commit(logMessageObtain);
    }

    public final void onDraw(String str) {
        ClockLogger$$ExternalSyntheticLambda0 clockLogger$$ExternalSyntheticLambda0 = new ClockLogger$$ExternalSyntheticLambda0(9);
        LogMessage logMessageObtain = getBuffer().obtain(getTag(), LogLevel.DEBUG, clockLogger$$ExternalSyntheticLambda0, null);
        if (str == null) {
            str = "";
        }
        logMessageObtain.setStr1(str);
        getBuffer().commit(logMessageObtain);
    }

    public final void onDraw(String str, String str2) {
        ClockLogger$$ExternalSyntheticLambda0 clockLogger$$ExternalSyntheticLambda0 = new ClockLogger$$ExternalSyntheticLambda0(1);
        LogMessage logMessageObtain = getBuffer().obtain(getTag(), LogLevel.DEBUG, clockLogger$$ExternalSyntheticLambda0, null);
        logMessageObtain.setStr1(str);
        logMessageObtain.setStr2(str2);
        getBuffer().commit(logMessageObtain);
    }
}
