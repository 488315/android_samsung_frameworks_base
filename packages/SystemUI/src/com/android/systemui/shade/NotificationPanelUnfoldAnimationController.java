package com.android.systemui.shade;

import android.content.Context;
import com.android.systemui.R;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shared.animation.UnfoldConstantTranslateAnimator;
import com.android.systemui.unfold.util.NaturalRotationUnfoldProgressProvider;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
                UnfoldConstantTranslateAnimator.ViewIdToTranslate viewIdToTranslate = new UnfoldConstantTranslateAnimator.ViewIdToTranslate(R.id.shade_header_system_icons, direction, NotificationPanelUnfoldAnimationController.this.filterShade, null, 8, null);
                UnfoldConstantTranslateAnimator.ViewIdToTranslate viewIdToTranslate2 = new UnfoldConstantTranslateAnimator.ViewIdToTranslate(R.id.privacy_container, direction, NotificationPanelUnfoldAnimationController.this.filterShade, null, 8, null);
                UnfoldConstantTranslateAnimator.ViewIdToTranslate viewIdToTranslate3 = new UnfoldConstantTranslateAnimator.ViewIdToTranslate(R.id.carrier_group, direction, NotificationPanelUnfoldAnimationController.this.filterShade, null, 8, null);
                UnfoldConstantTranslateAnimator.Direction direction2 = UnfoldConstantTranslateAnimator.Direction.START;
                return new UnfoldConstantTranslateAnimator(SetsKt__SetsKt.setOf(viewIdToTranslate, viewIdToTranslate2, viewIdToTranslate3, new UnfoldConstantTranslateAnimator.ViewIdToTranslate(R.id.clock, direction2, NotificationPanelUnfoldAnimationController.this.filterShade, null, 8, null), new UnfoldConstantTranslateAnimator.ViewIdToTranslate(R.id.date, direction2, NotificationPanelUnfoldAnimationController.this.filterShade, null, 8, null)), naturalRotationUnfoldProgressProvider);
            }
        });
    }
}
