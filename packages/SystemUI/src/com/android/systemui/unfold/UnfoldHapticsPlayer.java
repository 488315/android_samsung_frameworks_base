package com.android.systemui.unfold;

import android.os.SystemProperties;
import android.os.VibrationAttributes;
import android.os.VibrationEffect;
import android.os.Vibrator;
import com.android.systemui.unfold.UnfoldTransitionProgressProvider;
import com.android.systemui.unfold.config.ResourceUnfoldTransitionConfig;
import com.android.systemui.unfold.config.UnfoldTransitionConfig;
import com.android.systemui.unfold.updates.FoldProvider;
import com.sec.ims.configuration.DATA;
import java.util.concurrent.Executor;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.text.StringsKt__StringNumberConversionsJVMKt;
import kotlin.text.StringsKt__StringNumberConversionsKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class UnfoldHapticsPlayer implements UnfoldTransitionProgressProvider.TransitionProgressListener {
    public final Lazy effect$delegate;
    public boolean isFirstAnimationAfterUnfold;
    public float lastTransitionProgress;
    public final Executor mainExecutor;
    public final VibrationAttributes touchVibrationAttributes = VibrationAttributes.createForUsage(50);
    public final Vibrator vibrator;

    public UnfoldHapticsPlayer(UnfoldTransitionProgressProvider unfoldTransitionProgressProvider, FoldProvider foldProvider, UnfoldTransitionConfig unfoldTransitionConfig, Executor executor, Vibrator vibrator) {
        this.mainExecutor = executor;
        this.vibrator = vibrator;
        if (vibrator != null && ((Boolean) ((ResourceUnfoldTransitionConfig) unfoldTransitionConfig).isHapticsEnabled$delegate.getValue()).booleanValue()) {
            unfoldTransitionProgressProvider.addCallback(this);
            foldProvider.registerCallback(new FoldProvider.FoldCallback() { // from class: com.android.systemui.unfold.UnfoldHapticsPlayer.1
                @Override // com.android.systemui.unfold.updates.FoldProvider.FoldCallback
                public final void onFoldUpdated(boolean z) {
                    if (z) {
                        UnfoldHapticsPlayer.this.isFirstAnimationAfterUnfold = true;
                    }
                }
            }, executor);
        }
        this.lastTransitionProgress = 1.0f;
        this.effect$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.unfold.UnfoldHapticsPlayer$effect$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                VibrationEffect.Composition addPrimitive = VibrationEffect.startComposition().addPrimitive(7, 0.0f, 0);
                UnfoldHapticsPlayer.this.getClass();
                Integer intOrNull = StringsKt__StringNumberConversionsKt.toIntOrNull(10, SystemProperties.get("persist.unfold.primitives_count", DATA.DM_FIELD_INDEX.SIP_TD_TIMER));
                int intValue = intOrNull != null ? intOrNull.intValue() : 18;
                UnfoldHapticsPlayer unfoldHapticsPlayer = UnfoldHapticsPlayer.this;
                for (int i = 0; i < intValue; i++) {
                    unfoldHapticsPlayer.getClass();
                    Float floatOrNull = StringsKt__StringNumberConversionsJVMKt.toFloatOrNull(SystemProperties.get("persist.unfold.haptics_scale", "0.5"));
                    addPrimitive.addPrimitive(8, floatOrNull != null ? floatOrNull.floatValue() : 0.5f, 0);
                }
                UnfoldHapticsPlayer.this.getClass();
                Float floatOrNull2 = StringsKt__StringNumberConversionsJVMKt.toFloatOrNull(SystemProperties.get("persist.unfold.haptics_scale_end_tick", "1.0"));
                return addPrimitive.addPrimitive(7, floatOrNull2 != null ? floatOrNull2.floatValue() : 1.0f).compose();
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
