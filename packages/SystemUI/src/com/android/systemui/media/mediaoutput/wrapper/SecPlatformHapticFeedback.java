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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SecPlatformHapticFeedback implements HapticFeedback {
    public final HapticFeedback hapticFeedback;
    public final Lazy view$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.media.mediaoutput.wrapper.SecPlatformHapticFeedback$view$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            Field declaredField = SecPlatformHapticFeedback.this.hapticFeedback.getClass().getDeclaredField("view");
            declaredField.setAccessible(true);
            Object obj = declaredField.get(SecPlatformHapticFeedback.this.hapticFeedback);
            if (obj == null || !(obj instanceof View)) {
                return null;
            }
            return (View) obj;
        }
    });

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
    public final void mo492performHapticFeedbackCdsT49E(int i) {
        this.hapticFeedback.mo492performHapticFeedbackCdsT49E(i);
    }
}
