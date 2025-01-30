package com.android.systemui.unfold;

import android.os.SystemProperties;
import android.os.VibrationAttributes;
import android.os.VibrationEffect;
import android.os.Vibrator;
import com.android.systemui.unfold.UnfoldTransitionProgressProvider;
import com.android.systemui.unfold.updates.FoldProvider;
import com.samsung.android.nexus.video.VideoPlayer;
import com.sec.ims.configuration.DATA;
import java.util.concurrent.Executor;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt__CharJVMKt;
import kotlin.text.StringsKt__StringNumberConversionsJVMKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class UnfoldHapticsPlayer implements UnfoldTransitionProgressProvider.TransitionProgressListener {
    public final Lazy effect$delegate;
    public boolean isFirstAnimationAfterUnfold;
    public float lastTransitionProgress;
    public final Executor mainExecutor;
    public final VibrationAttributes touchVibrationAttributes = VibrationAttributes.createForUsage(50);
    public final Vibrator vibrator;

    public UnfoldHapticsPlayer(UnfoldTransitionProgressProvider unfoldTransitionProgressProvider, FoldProvider foldProvider, Executor executor, Vibrator vibrator) {
        this.mainExecutor = executor;
        this.vibrator = vibrator;
        if (vibrator != null) {
            unfoldTransitionProgressProvider.addCallback(this);
        }
        foldProvider.registerCallback(new FoldProvider.FoldCallback() { // from class: com.android.systemui.unfold.UnfoldHapticsPlayer.1
            @Override // com.android.systemui.unfold.updates.FoldProvider.FoldCallback
            public final void onFoldUpdated(boolean z) {
                if (z) {
                    UnfoldHapticsPlayer.this.isFirstAnimationAfterUnfold = true;
                }
            }
        }, executor);
        this.lastTransitionProgress = 1.0f;
        this.effect$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.unfold.UnfoldHapticsPlayer$effect$2
            {
                super(0);
            }

            /* JADX WARN: Removed duplicated region for block: B:29:0x007d  */
            /* JADX WARN: Removed duplicated region for block: B:32:0x0089  */
            /* JADX WARN: Removed duplicated region for block: B:42:0x00bf  */
            /* JADX WARN: Removed duplicated region for block: B:46:0x00c4  */
            /* JADX WARN: Removed duplicated region for block: B:47:0x0082  */
            @Override // kotlin.jvm.functions.Function0
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object invoke() {
                Integer num;
                int intValue;
                int i;
                boolean z;
                int i2;
                int i3;
                VibrationEffect.Composition addPrimitive = VibrationEffect.startComposition().addPrimitive(7, 0.0f, 0);
                UnfoldHapticsPlayer.this.getClass();
                String str = SystemProperties.get("persist.unfold.primitives_count", DATA.DM_FIELD_INDEX.SIP_TD_TIMER);
                CharsKt__CharJVMKt.checkRadix(10);
                int length = str.length();
                if (length != 0) {
                    char charAt = str.charAt(0);
                    int i4 = -2147483647;
                    if (Intrinsics.compare(charAt, 48) < 0) {
                        i2 = 1;
                        if (length != 1) {
                            if (charAt == '-') {
                                i4 = VideoPlayer.MEDIA_ERROR_SYSTEM;
                                z = true;
                            } else if (charAt == '+') {
                                z = false;
                            }
                        }
                    } else {
                        z = false;
                        i2 = 0;
                    }
                    int i5 = 0;
                    int i6 = -59652323;
                    while (i2 < length) {
                        int digit = Character.digit((int) str.charAt(i2), 10);
                        if (digit >= 0 && ((i5 >= i6 || (i6 == -59652323 && i5 >= (i6 = i4 / 10))) && (i3 = i5 * 10) >= i4 + digit)) {
                            i5 = i3 - digit;
                            i2++;
                        }
                    }
                    num = z ? Integer.valueOf(i5) : Integer.valueOf(-i5);
                    intValue = num == null ? num.intValue() : 18;
                    UnfoldHapticsPlayer unfoldHapticsPlayer = UnfoldHapticsPlayer.this;
                    for (i = 0; i < intValue; i++) {
                        unfoldHapticsPlayer.getClass();
                        Float floatOrNull = StringsKt__StringNumberConversionsJVMKt.toFloatOrNull(SystemProperties.get("persist.unfold.haptics_scale", "0.1"));
                        addPrimitive.addPrimitive(8, floatOrNull != null ? floatOrNull.floatValue() : 0.1f, 0);
                    }
                    UnfoldHapticsPlayer.this.getClass();
                    Float floatOrNull2 = StringsKt__StringNumberConversionsJVMKt.toFloatOrNull(SystemProperties.get("persist.unfold.haptics_scale_end_tick", "0.6"));
                    return addPrimitive.addPrimitive(7, floatOrNull2 == null ? floatOrNull2.floatValue() : 0.6f).compose();
                }
                num = null;
                if (num == null) {
                }
                UnfoldHapticsPlayer unfoldHapticsPlayer2 = UnfoldHapticsPlayer.this;
                while (i < intValue) {
                }
                UnfoldHapticsPlayer.this.getClass();
                Float floatOrNull22 = StringsKt__StringNumberConversionsJVMKt.toFloatOrNull(SystemProperties.get("persist.unfold.haptics_scale_end_tick", "0.6"));
                return addPrimitive.addPrimitive(7, floatOrNull22 == null ? floatOrNull22.floatValue() : 0.6f).compose();
            }
        });
    }

    @Override // com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
    public final void onTransitionFinished() {
        this.lastTransitionProgress = 1.0f;
    }

    @Override // com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
    public final void onTransitionFinishing() {
        Vibrator vibrator;
        if (this.isFirstAnimationAfterUnfold) {
            this.isFirstAnimationAfterUnfold = false;
            if (this.lastTransitionProgress >= 0.9f || (vibrator = this.vibrator) == null) {
                return;
            }
            vibrator.vibrate((VibrationEffect) this.effect$delegate.getValue(), this.touchVibrationAttributes);
        }
    }

    @Override // com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
    public final void onTransitionProgress(float f) {
        this.lastTransitionProgress = f;
    }

    @Override // com.android.systemui.unfold.UnfoldTransitionProgressProvider.TransitionProgressListener
    public final void onTransitionStarted() {
        this.lastTransitionProgress = 0.0f;
    }
}
