package com.android.systemui.shade;

import android.content.Context;
import android.view.ViewGroup;
import com.android.systemui.R;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shared.animation.UnfoldConstantTranslateAnimator;
import com.android.systemui.unfold.util.NaturalRotationUnfoldProgressProvider;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotificationPanelUnfoldAnimationController {
    public final Context context;
    public final Function0 filterShade;
    public final Lazy translateAnimator$delegate;
    public final Lazy translateAnimatorStatusBar$delegate;

    public NotificationPanelUnfoldAnimationController(Context context, final StatusBarStateController statusBarStateController, final NaturalRotationUnfoldProgressProvider naturalRotationUnfoldProgressProvider) {
        this.context = context;
        this.filterShade = new Function0() { // from class: com.android.systemui.shade.NotificationPanelUnfoldAnimationController$filterShade$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Boolean.valueOf(StatusBarStateController.this.getState() == 0 || StatusBarStateController.this.getState() == 2);
            }
        };
        this.translateAnimator$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.NotificationPanelUnfoldAnimationController$translateAnimator$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return new UnfoldConstantTranslateAnimator(SetsKt__SetsKt.setOf(new UnfoldConstantTranslateAnimator.ViewIdToTranslate(R.id.quick_settings_panel, UnfoldConstantTranslateAnimator.Direction.START, NotificationPanelUnfoldAnimationController.this.filterShade, null, 8, null), new UnfoldConstantTranslateAnimator.ViewIdToTranslate(R.id.notification_stack_scroller, UnfoldConstantTranslateAnimator.Direction.END, NotificationPanelUnfoldAnimationController.this.filterShade, null, 8, null)), naturalRotationUnfoldProgressProvider);
            }
        });
        this.translateAnimatorStatusBar$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.shade.NotificationPanelUnfoldAnimationController$translateAnimatorStatusBar$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                UnfoldConstantTranslateAnimator.Direction direction = UnfoldConstantTranslateAnimator.Direction.END;
                UnfoldConstantTranslateAnimator.ViewIdToTranslate viewIdToTranslate = new UnfoldConstantTranslateAnimator.ViewIdToTranslate(R.id.statusIcons, direction, NotificationPanelUnfoldAnimationController.this.filterShade, null, 8, null);
                UnfoldConstantTranslateAnimator.ViewIdToTranslate viewIdToTranslate2 = new UnfoldConstantTranslateAnimator.ViewIdToTranslate(R.id.privacy_container, direction, NotificationPanelUnfoldAnimationController.this.filterShade, null, 8, null);
                UnfoldConstantTranslateAnimator.ViewIdToTranslate viewIdToTranslate3 = new UnfoldConstantTranslateAnimator.ViewIdToTranslate(R.id.batteryRemainingIcon, direction, NotificationPanelUnfoldAnimationController.this.filterShade, null, 8, null);
                UnfoldConstantTranslateAnimator.ViewIdToTranslate viewIdToTranslate4 = new UnfoldConstantTranslateAnimator.ViewIdToTranslate(R.id.carrier_group, direction, NotificationPanelUnfoldAnimationController.this.filterShade, null, 8, null);
                UnfoldConstantTranslateAnimator.Direction direction2 = UnfoldConstantTranslateAnimator.Direction.START;
                return new UnfoldConstantTranslateAnimator(SetsKt__SetsKt.setOf(viewIdToTranslate, viewIdToTranslate2, viewIdToTranslate3, viewIdToTranslate4, new UnfoldConstantTranslateAnimator.ViewIdToTranslate(R.id.clock, direction2, NotificationPanelUnfoldAnimationController.this.filterShade, null, 8, null), new UnfoldConstantTranslateAnimator.ViewIdToTranslate(R.id.date, direction2, NotificationPanelUnfoldAnimationController.this.filterShade, null, 8, null)), naturalRotationUnfoldProgressProvider);
            }
        });
    }

    public final void setup(ViewGroup viewGroup) {
        float dimensionPixelSize = this.context.getResources().getDimensionPixelSize(R.dimen.notification_side_paddings);
        UnfoldConstantTranslateAnimator unfoldConstantTranslateAnimator = (UnfoldConstantTranslateAnimator) this.translateAnimator$delegate.getValue();
        unfoldConstantTranslateAnimator.rootView = viewGroup;
        unfoldConstantTranslateAnimator.translationMax = dimensionPixelSize;
        unfoldConstantTranslateAnimator.progressProvider.addCallback(unfoldConstantTranslateAnimator);
        ViewGroup viewGroup2 = (ViewGroup) viewGroup.findViewById(R.id.split_shade_status_bar);
        if (viewGroup2 != null) {
            UnfoldConstantTranslateAnimator unfoldConstantTranslateAnimator2 = (UnfoldConstantTranslateAnimator) this.translateAnimatorStatusBar$delegate.getValue();
            unfoldConstantTranslateAnimator2.rootView = viewGroup2;
            unfoldConstantTranslateAnimator2.translationMax = dimensionPixelSize;
            unfoldConstantTranslateAnimator2.progressProvider.addCallback(unfoldConstantTranslateAnimator2);
        }
    }
}
