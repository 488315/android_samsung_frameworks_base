package com.android.systemui.media.mediaoutput.wrapper;

import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;
import androidx.compose.ui.hapticfeedback.HapticFeedback;
import java.lang.reflect.Field;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SecPlatformHapticFeedback implements HapticFeedback {
    public final HapticFeedback hapticFeedback;
    public final Lazy view$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.mediaoutput.wrapper.SecPlatformHapticFeedback$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            SecPlatformHapticFeedback secPlatformHapticFeedback = SecPlatformHapticFeedback.this;
            Field declaredField = secPlatformHapticFeedback.hapticFeedback.getClass().getDeclaredField("view");
            declaredField.setAccessible(true);
            Object obj = declaredField.get(secPlatformHapticFeedback.hapticFeedback);
            if (obj == null || !(obj instanceof View)) {
                return null;
            }
            return (View) obj;
        }
    });

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[SecHapticFeedbackType.values().length];
            try {
                iArr[SecHapticFeedbackType.Switch.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[SecHapticFeedbackType.Seekbar.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new Companion(null);
    }

    public SecPlatformHapticFeedback(HapticFeedback hapticFeedback) {
        this.hapticFeedback = hapticFeedback;
        Log.d("SecPlatformHapticFeedback", "init()");
    }

    public final void performHapticFeedback(SecHapticFeedbackType secHapticFeedbackType) {
        int i;
        int i2 = WhenMappings.$EnumSwitchMapping$0[secHapticFeedbackType.ordinal()];
        if (i2 == 1) {
            i = 27;
        } else {
            if (i2 != 2) {
                throw new NoWhenBranchMatchedException();
            }
            i = 41;
        }
        View view = (View) this.view$delegate.getValue();
        if (view != null) {
            view.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(i));
        }
    }

    @Override // androidx.compose.ui.hapticfeedback.HapticFeedback
    /* renamed from: performHapticFeedback-CdsT49E */
    public final void mo570performHapticFeedbackCdsT49E(int i) {
        this.hapticFeedback.mo570performHapticFeedbackCdsT49E(i);
    }
}
