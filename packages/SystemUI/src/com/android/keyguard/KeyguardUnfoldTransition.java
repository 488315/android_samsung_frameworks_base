package com.android.keyguard;

import android.content.Context;
import android.view.View;
import com.android.systemui.Flags;
import com.android.systemui.R;
import com.android.systemui.keyguard.ui.view.KeyguardRootView;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.NotificationShadeWindowView;
import com.android.systemui.shared.animation.UnfoldConstantTranslateAnimator;
import com.android.systemui.unfold.UnfoldTransitionProgressProvider;
import java.util.Collections;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.collections.SetsKt___SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class KeyguardUnfoldTransition {
    public final Context context;
    public final Function0 filterKeyguard;
    public final Function0 filterKeyguardAndSplitShadeOnly;
    public final KeyguardRootView keyguardRootView;
    public final NotificationShadeWindowView shadeWindowView;
    public final Lazy shortcutButtonsAnimator$delegate;
    public boolean statusViewCentered;
    public final Lazy translateAnimator$delegate;

    public KeyguardUnfoldTransition(Context context, KeyguardRootView keyguardRootView, NotificationShadeWindowView notificationShadeWindowView, final StatusBarStateController statusBarStateController, final UnfoldTransitionProgressProvider unfoldTransitionProgressProvider) {
        this.context = context;
        this.keyguardRootView = keyguardRootView;
        this.shadeWindowView = notificationShadeWindowView;
        this.filterKeyguardAndSplitShadeOnly = new Function0() { // from class: com.android.keyguard.KeyguardUnfoldTransition$filterKeyguardAndSplitShadeOnly$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Boolean.valueOf(StatusBarStateController.this.getState() == 1 && !this.statusViewCentered);
            }
        };
        this.filterKeyguard = new Function0() { // from class: com.android.keyguard.KeyguardUnfoldTransition$filterKeyguard$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Boolean.valueOf(StatusBarStateController.this.getState() == 1);
            }
        };
        this.translateAnimator$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.keyguard.KeyguardUnfoldTransition$translateAnimator$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Flags.migrateClocksToBlueprint();
                UnfoldConstantTranslateAnimator.Direction direction = UnfoldConstantTranslateAnimator.Direction.START;
                return new UnfoldConstantTranslateAnimator(SetsKt___SetsKt.plus(SetsKt__SetsKt.setOf(new UnfoldConstantTranslateAnimator.ViewIdToTranslate(R.id.lockscreen_clock_view_large, direction, KeyguardUnfoldTransition.this.filterKeyguardAndSplitShadeOnly, null, 8, null), new UnfoldConstantTranslateAnimator.ViewIdToTranslate(R.id.lockscreen_clock_view, direction, KeyguardUnfoldTransition.this.filterKeyguard, null, 8, null), new UnfoldConstantTranslateAnimator.ViewIdToTranslate(R.id.notification_stack_scroller, UnfoldConstantTranslateAnimator.Direction.END, KeyguardUnfoldTransition.this.filterKeyguardAndSplitShadeOnly, null, 8, null)), (Iterable) Collections.singleton(new UnfoldConstantTranslateAnimator.ViewIdToTranslate(R.id.keyguard_status_area, direction, KeyguardUnfoldTransition.this.filterKeyguard, new Function2() { // from class: com.android.keyguard.KeyguardUnfoldTransition$translateAnimator$2$smartSpaceViews$1
                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj, Object obj2) {
                        View view = (View) obj;
                        float floatValue = ((Number) obj2).floatValue();
                        KeyguardStatusAreaView keyguardStatusAreaView = view instanceof KeyguardStatusAreaView ? (KeyguardStatusAreaView) view : null;
                        if (keyguardStatusAreaView != null) {
                            keyguardStatusAreaView.translateXFromUnfold = floatValue;
                            keyguardStatusAreaView.setTranslationX(keyguardStatusAreaView.translateXFromAod + keyguardStatusAreaView.translateXFromClockDesign + floatValue);
                        }
                        return Unit.INSTANCE;
                    }
                }))), unfoldTransitionProgressProvider);
            }
        });
        this.shortcutButtonsAnimator$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.keyguard.KeyguardUnfoldTransition$shortcutButtonsAnimator$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return new UnfoldConstantTranslateAnimator(SetsKt__SetsKt.setOf(new UnfoldConstantTranslateAnimator.ViewIdToTranslate(R.id.start_button, UnfoldConstantTranslateAnimator.Direction.START, KeyguardUnfoldTransition.this.filterKeyguard, null, 8, null), new UnfoldConstantTranslateAnimator.ViewIdToTranslate(R.id.end_button, UnfoldConstantTranslateAnimator.Direction.END, KeyguardUnfoldTransition.this.filterKeyguard, null, 8, null)), unfoldTransitionProgressProvider);
            }
        });
    }
}
