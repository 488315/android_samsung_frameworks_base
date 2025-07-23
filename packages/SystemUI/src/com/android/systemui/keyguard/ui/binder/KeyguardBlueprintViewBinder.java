package com.android.systemui.keyguard.ui.binder;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.datastore.preferences.core.MutablePreferences$$ExternalSyntheticOutline0;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.customization.R$id;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardBlueprintViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardClockViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardSmartspaceViewModel;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.log.core.Logger;
import com.android.systemui.plugins.clocks.ClockController;
import com.android.systemui.plugins.clocks.ClockLogger;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyguardBlueprintViewBinder {
    public static final KeyguardBlueprintViewBinder INSTANCE = new KeyguardBlueprintViewBinder();
    public static final String TAG = "KeyguardBlueprintViewBinder";

    private KeyguardBlueprintViewBinder() {
    }

    public static final void access$logConstraintSet(KeyguardBlueprintViewBinder keyguardBlueprintViewBinder, Logger logger, ConstraintSet constraintSet, KeyguardClockViewModel keyguardClockViewModel) {
        keyguardBlueprintViewBinder.getClass();
        ClockController clockController = (ClockController) keyguardClockViewModel.currentClock.$$delegate_0.getValue();
        if (clockController == null) {
            return;
        }
        final int i = 0;
        Function1 function1 = new Function1() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBlueprintViewBinder$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                switch (i) {
                    case 0:
                        KeyguardBlueprintViewBinder keyguardBlueprintViewBinder2 = KeyguardBlueprintViewBinder.INSTANCE;
                        String visText = ClockLogger.Companion.getVisText(logMessage.getInt1());
                        String str1 = logMessage.getStr1();
                        String str2 = logMessage.getStr2();
                        StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("applyCsToSmallClock: vis=", visText, "; alpha=", str1, "; scale=");
                        m.append(str2);
                        return m.toString();
                    case 1:
                        KeyguardBlueprintViewBinder keyguardBlueprintViewBinder3 = KeyguardBlueprintViewBinder.INSTANCE;
                        String visText2 = ClockLogger.Companion.getVisText(logMessage.getInt1());
                        String str12 = logMessage.getStr1();
                        return MutablePreferences$$ExternalSyntheticOutline0.m(SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("applyCsToLargeClock: vis=", visText2, "; alpha=", str12, "; scale="), logMessage.getStr2(), "; pivotX=", logMessage.getStr3());
                    default:
                        KeyguardBlueprintViewBinder keyguardBlueprintViewBinder4 = KeyguardBlueprintViewBinder.INSTANCE;
                        return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("applyCsToSmartspaceDate: vis=", ClockLogger.Companion.getVisText(logMessage.getInt1()), "; alpha=", logMessage.getStr1());
                }
            }
        };
        LogLevel logLevel = LogLevel.INFO;
        LogMessage obtain = logger.getBuffer().obtain(logger.getTag(), logLevel, function1, null);
        int i2 = R$id.lockscreen_clock_view;
        obtain.setInt1(constraintSet.getVisibility(i2));
        obtain.setStr1(String.valueOf(constraintSet.getConstraint(i2).propertySet.alpha));
        obtain.setStr2(String.valueOf(constraintSet.getConstraint(i2).transform.scaleX));
        logger.getBuffer().commit(obtain);
        final int i3 = 1;
        LogMessage obtain2 = logger.getBuffer().obtain(logger.getTag(), logLevel, new Function1() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBlueprintViewBinder$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                switch (i3) {
                    case 0:
                        KeyguardBlueprintViewBinder keyguardBlueprintViewBinder2 = KeyguardBlueprintViewBinder.INSTANCE;
                        String visText = ClockLogger.Companion.getVisText(logMessage.getInt1());
                        String str1 = logMessage.getStr1();
                        String str2 = logMessage.getStr2();
                        StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("applyCsToSmallClock: vis=", visText, "; alpha=", str1, "; scale=");
                        m.append(str2);
                        return m.toString();
                    case 1:
                        KeyguardBlueprintViewBinder keyguardBlueprintViewBinder3 = KeyguardBlueprintViewBinder.INSTANCE;
                        String visText2 = ClockLogger.Companion.getVisText(logMessage.getInt1());
                        String str12 = logMessage.getStr1();
                        return MutablePreferences$$ExternalSyntheticOutline0.m(SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("applyCsToLargeClock: vis=", visText2, "; alpha=", str12, "; scale="), logMessage.getStr2(), "; pivotX=", logMessage.getStr3());
                    default:
                        KeyguardBlueprintViewBinder keyguardBlueprintViewBinder4 = KeyguardBlueprintViewBinder.INSTANCE;
                        return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("applyCsToSmartspaceDate: vis=", ClockLogger.Companion.getVisText(logMessage.getInt1()), "; alpha=", logMessage.getStr1());
                }
            }
        }, null);
        int id = clockController.getLargeClock().getLayout().getViews().get(0).getId();
        obtain2.setInt1(constraintSet.getVisibility(id));
        obtain2.setStr1(String.valueOf(constraintSet.getConstraint(id).propertySet.alpha));
        obtain2.setStr2(String.valueOf(constraintSet.getConstraint(id).transform.scaleX));
        obtain2.setStr3(String.valueOf(constraintSet.getConstraint(id).transform.transformPivotX));
        logger.getBuffer().commit(obtain2);
        final int i4 = 2;
        LogMessage obtain3 = logger.getBuffer().obtain(logger.getTag(), logLevel, new Function1() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBlueprintViewBinder$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                LogMessage logMessage = (LogMessage) obj;
                switch (i4) {
                    case 0:
                        KeyguardBlueprintViewBinder keyguardBlueprintViewBinder2 = KeyguardBlueprintViewBinder.INSTANCE;
                        String visText = ClockLogger.Companion.getVisText(logMessage.getInt1());
                        String str1 = logMessage.getStr1();
                        String str2 = logMessage.getStr2();
                        StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("applyCsToSmallClock: vis=", visText, "; alpha=", str1, "; scale=");
                        m.append(str2);
                        return m.toString();
                    case 1:
                        KeyguardBlueprintViewBinder keyguardBlueprintViewBinder3 = KeyguardBlueprintViewBinder.INSTANCE;
                        String visText2 = ClockLogger.Companion.getVisText(logMessage.getInt1());
                        String str12 = logMessage.getStr1();
                        return MutablePreferences$$ExternalSyntheticOutline0.m(SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("applyCsToLargeClock: vis=", visText2, "; alpha=", str12, "; scale="), logMessage.getStr2(), "; pivotX=", logMessage.getStr3());
                    default:
                        KeyguardBlueprintViewBinder keyguardBlueprintViewBinder4 = KeyguardBlueprintViewBinder.INSTANCE;
                        return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("applyCsToSmartspaceDate: vis=", ClockLogger.Companion.getVisText(logMessage.getInt1()), "; alpha=", logMessage.getStr1());
                }
            }
        }, null);
        obtain3.setInt1(constraintSet.getVisibility(R.id.date_smartspace_view));
        obtain3.setStr1(String.valueOf(constraintSet.getConstraint(R.id.date_smartspace_view).propertySet.alpha));
        logger.getBuffer().commit(obtain3);
    }

    public static final void bind(ConstraintLayout constraintLayout, KeyguardBlueprintViewModel keyguardBlueprintViewModel, KeyguardClockViewModel keyguardClockViewModel, KeyguardSmartspaceViewModel keyguardSmartspaceViewModel, LogBuffer logBuffer) {
        RepeatWhenAttachedKt.repeatWhenAttached(constraintLayout, EmptyCoroutineContext.INSTANCE, new KeyguardBlueprintViewBinder$bind$1(keyguardBlueprintViewModel, constraintLayout, keyguardClockViewModel, keyguardSmartspaceViewModel, logBuffer, new Logger(logBuffer, TAG), null));
    }
}
