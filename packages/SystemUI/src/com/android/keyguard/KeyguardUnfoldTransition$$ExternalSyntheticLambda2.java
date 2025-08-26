package com.android.keyguard;

import com.android.systemui.R;
import com.android.systemui.customization.R$id;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shared.animation.UnfoldConstantTranslateAnimator;
import com.android.systemui.unfold.UnfoldTransitionProgressProvider;
import java.util.Set;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.SetsKt___SetsKt;
import kotlin.jvm.functions.Function0;

/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardUnfoldTransition$$ExternalSyntheticLambda2 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardUnfoldTransition f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ KeyguardUnfoldTransition$$ExternalSyntheticLambda2(KeyguardUnfoldTransition keyguardUnfoldTransition, UnfoldTransitionProgressProvider unfoldTransitionProgressProvider, int i) {
        this.$r8$classId = i;
        this.f$0 = keyguardUnfoldTransition;
        this.f$1 = unfoldTransitionProgressProvider;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                KeyguardUnfoldTransition$$ExternalSyntheticLambda4 keyguardUnfoldTransition$$ExternalSyntheticLambda4 = new KeyguardUnfoldTransition$$ExternalSyntheticLambda4();
                UnfoldConstantTranslateAnimator.Direction direction = UnfoldConstantTranslateAnimator.Direction.START;
                KeyguardUnfoldTransition keyguardUnfoldTransition = this.f$0;
                KeyguardUnfoldTransition$$ExternalSyntheticLambda1 keyguardUnfoldTransition$$ExternalSyntheticLambda1 = keyguardUnfoldTransition.filterKeyguard;
                Set set = ArraysKt___ArraysKt.toSet(new UnfoldConstantTranslateAnimator.ViewIdToTranslate[]{new UnfoldConstantTranslateAnimator.ViewIdToTranslate(R.id.date_smartspace_view, direction, keyguardUnfoldTransition$$ExternalSyntheticLambda1, keyguardUnfoldTransition$$ExternalSyntheticLambda4), new UnfoldConstantTranslateAnimator.ViewIdToTranslate(R.id.bc_smartspace_view, direction, keyguardUnfoldTransition$$ExternalSyntheticLambda1, keyguardUnfoldTransition$$ExternalSyntheticLambda4), new UnfoldConstantTranslateAnimator.ViewIdToTranslate(R.id.weather_smartspace_view, direction, keyguardUnfoldTransition$$ExternalSyntheticLambda1, keyguardUnfoldTransition$$ExternalSyntheticLambda4)});
                int i = R$id.lockscreen_clock_view_large;
                KeyguardUnfoldTransition$$ExternalSyntheticLambda2 keyguardUnfoldTransition$$ExternalSyntheticLambda2 = keyguardUnfoldTransition.filterKeyguardAndSplitShadeOnly;
                return new UnfoldConstantTranslateAnimator(SetsKt___SetsKt.plus(ArraysKt___ArraysKt.toSet(new UnfoldConstantTranslateAnimator.ViewIdToTranslate[]{new UnfoldConstantTranslateAnimator.ViewIdToTranslate(i, direction, keyguardUnfoldTransition$$ExternalSyntheticLambda2, null, 8, null), new UnfoldConstantTranslateAnimator.ViewIdToTranslate(R$id.lockscreen_clock_view, direction, keyguardUnfoldTransition$$ExternalSyntheticLambda1, null, 8, null), new UnfoldConstantTranslateAnimator.ViewIdToTranslate(R.id.notification_stack_scroller, UnfoldConstantTranslateAnimator.Direction.END, keyguardUnfoldTransition$$ExternalSyntheticLambda2, null, 8, null)}), (Iterable) set), (UnfoldTransitionProgressProvider) this.f$1);
            case 1:
                UnfoldConstantTranslateAnimator.Direction direction2 = UnfoldConstantTranslateAnimator.Direction.START;
                KeyguardUnfoldTransition$$ExternalSyntheticLambda1 keyguardUnfoldTransition$$ExternalSyntheticLambda12 = this.f$0.filterKeyguard;
                return new UnfoldConstantTranslateAnimator(ArraysKt___ArraysKt.toSet(new UnfoldConstantTranslateAnimator.ViewIdToTranslate[]{new UnfoldConstantTranslateAnimator.ViewIdToTranslate(R.id.start_button, direction2, keyguardUnfoldTransition$$ExternalSyntheticLambda12, null, 8, null), new UnfoldConstantTranslateAnimator.ViewIdToTranslate(R.id.end_button, UnfoldConstantTranslateAnimator.Direction.END, keyguardUnfoldTransition$$ExternalSyntheticLambda12, null, 8, null)}), (UnfoldTransitionProgressProvider) this.f$1);
            default:
                return Boolean.valueOf(((StatusBarStateController) this.f$1).getState() == 1 && !this.f$0.statusViewCentered);
        }
    }

    public /* synthetic */ KeyguardUnfoldTransition$$ExternalSyntheticLambda2(StatusBarStateController statusBarStateController, KeyguardUnfoldTransition keyguardUnfoldTransition) {
        this.$r8$classId = 2;
        this.f$1 = statusBarStateController;
        this.f$0 = keyguardUnfoldTransition;
    }
}
