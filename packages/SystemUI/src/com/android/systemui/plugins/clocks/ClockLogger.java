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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ClockLogger extends Logger {
    private float loggedAlpha;
    private final View view;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;
    private static final LogcatOnlyMessageBuffer DEFAULT_MESSAGE_BUFFER = new LogcatOnlyMessageBuffer(LogLevel.INFO);
    private static final LogcatOnlyMessageBuffer DEBUG_MESSAGE_BUFFER = new LogcatOnlyMessageBuffer(LogLevel.DEBUG);
    private static final ClockLogger INIT_LOGGER = new ClockLogger(null, new LogcatOnlyMessageBuffer(LogLevel.ERROR), "CLOCK_INIT");

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("animateFidget(", VPointF.m2764toStringimpl(VPointF.Companion.m2769fromLongAsyRdg(logMessage.getLong1())), ")");
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
        return "onLayout(" + logMessage.getBool1() + ", " + VRect.m2802toStringimpl(VRect.Companion.m2806fromLongqYjogQA(logMessage.getLong1())) + ")";
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
        LogMessage obtain = getBuffer().obtain(getTag(), LogLevel.DEBUG, clockLogger$$ExternalSyntheticLambda0, null);
        obtain.setBool1(z);
        obtain.setBool2(z2);
        getBuffer().commit(obtain);
    }

    public final void animateFidget(float f, float f2) {
        ClockLogger$$ExternalSyntheticLambda0 clockLogger$$ExternalSyntheticLambda0 = new ClockLogger$$ExternalSyntheticLambda0(8);
        LogMessage obtain = getBuffer().obtain(getTag(), LogLevel.DEBUG, clockLogger$$ExternalSyntheticLambda0, null);
        obtain.setLong1(VPointF.m2762toLongimpl(VPointF.m2731constructorimpl(f, f2)));
        getBuffer().commit(obtain);
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
        LogMessage obtain = getBuffer().obtain(getTag(), LogLevel.DEBUG, clockLogger$$ExternalSyntheticLambda0, null);
        obtain.setBool1(z);
        obtain.setLong1(VRect.m2800toLongimpl(VRect.m2785constructorimpl(i, i2, i3, i4)));
        getBuffer().commit(obtain);
    }

    public final void onMeasure(int i, int i2) {
        ClockLogger$$ExternalSyntheticLambda0 clockLogger$$ExternalSyntheticLambda0 = new ClockLogger$$ExternalSyntheticLambda0(2);
        LogMessage obtain = getBuffer().obtain(getTag(), LogLevel.DEBUG, clockLogger$$ExternalSyntheticLambda0, null);
        obtain.setInt1(i);
        obtain.setInt2(i2);
        getBuffer().commit(obtain);
    }

    public final void onViewAdded(View view) {
        ClockLogger$$ExternalSyntheticLambda0 clockLogger$$ExternalSyntheticLambda0 = new ClockLogger$$ExternalSyntheticLambda0(3);
        LogMessage obtain = getBuffer().obtain(getTag(), LogLevel.DEBUG, clockLogger$$ExternalSyntheticLambda0, null);
        String simpleName = Reflection.getOrCreateKotlinClass(view.getClass()).getSimpleName();
        simpleName.getClass();
        obtain.setStr1(simpleName);
        obtain.setInt1(view.getId());
        getBuffer().commit(obtain);
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
            LogMessage obtain = getBuffer().obtain(getTag(), LogLevel.DEBUG, new ClockLogger$$ExternalSyntheticLambda0(7), null);
            obtain.setDouble1(f);
            getBuffer().commit(obtain);
        }
    }

    public final void setVisibility(int i) {
        View view = this.view;
        if (view == null || i != view.getVisibility()) {
            ClockLogger$$ExternalSyntheticLambda0 clockLogger$$ExternalSyntheticLambda0 = new ClockLogger$$ExternalSyntheticLambda0(6);
            LogMessage obtain = getBuffer().obtain(getTag(), LogLevel.DEBUG, clockLogger$$ExternalSyntheticLambda0, null);
            obtain.setInt1(i);
            getBuffer().commit(obtain);
        }
    }

    public final void updateAxes(String str, String str2, boolean z) {
        ClockLogger$$ExternalSyntheticLambda0 clockLogger$$ExternalSyntheticLambda0 = new ClockLogger$$ExternalSyntheticLambda0(5);
        LogMessage obtain = getBuffer().obtain(getTag(), LogLevel.INFO, clockLogger$$ExternalSyntheticLambda0, null);
        obtain.setStr1(str);
        obtain.setStr2(str2);
        obtain.setBool1(z);
        getBuffer().commit(obtain);
    }

    public final void onDraw(String str) {
        ClockLogger$$ExternalSyntheticLambda0 clockLogger$$ExternalSyntheticLambda0 = new ClockLogger$$ExternalSyntheticLambda0(9);
        LogMessage obtain = getBuffer().obtain(getTag(), LogLevel.DEBUG, clockLogger$$ExternalSyntheticLambda0, null);
        if (str == null) {
            str = "";
        }
        obtain.setStr1(str);
        getBuffer().commit(obtain);
    }

    public final void onDraw(String str, String str2) {
        ClockLogger$$ExternalSyntheticLambda0 clockLogger$$ExternalSyntheticLambda0 = new ClockLogger$$ExternalSyntheticLambda0(1);
        LogMessage obtain = getBuffer().obtain(getTag(), LogLevel.DEBUG, clockLogger$$ExternalSyntheticLambda0, null);
        obtain.setStr1(str);
        obtain.setStr2(str2);
        getBuffer().commit(obtain);
    }
}
