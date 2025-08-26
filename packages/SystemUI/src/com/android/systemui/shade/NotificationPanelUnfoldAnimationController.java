package com.android.systemui.shade;

import android.content.Context;
import com.android.systemui.R;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shared.animation.UnfoldConstantTranslateAnimator;
import com.android.systemui.unfold.util.NaturalRotationUnfoldProgressProvider;
import java.util.Collections;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class NotificationPanelUnfoldAnimationController {
    public final Context context;
    public final NotificationPanelUnfoldAnimationController$$ExternalSyntheticLambda0 filterShade;
    public final Lazy translateAnimator$delegate;

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.shade.NotificationPanelUnfoldAnimationController$$ExternalSyntheticLambda0] */
    public NotificationPanelUnfoldAnimationController(Context context, final StatusBarStateController statusBarStateController, final NaturalRotationUnfoldProgressProvider naturalRotationUnfoldProgressProvider) {
        this.context = context;
        this.filterShade = new Function0() { // from class: com.android.systemui.shade.NotificationPanelUnfoldAnimationController$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                StatusBarStateController statusBarStateController2 = statusBarStateController;
                return Boolean.valueOf(statusBarStateController2.getState() == 0 || statusBarStateController2.getState() == 2);
            }
        };
        final int i = 0;
        this.translateAnimator$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.shade.NotificationPanelUnfoldAnimationController$$ExternalSyntheticLambda1
            public final /* synthetic */ NotificationPanelUnfoldAnimationController f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        return new UnfoldConstantTranslateAnimator(Collections.singleton(new UnfoldConstantTranslateAnimator.ViewIdToTranslate(R.id.quick_settings_panel, UnfoldConstantTranslateAnimator.Direction.START, this.f$0.filterShade, null, 8, null)), naturalRotationUnfoldProgressProvider);
                    default:
                        UnfoldConstantTranslateAnimator.Direction direction = UnfoldConstantTranslateAnimator.Direction.END;
                        NotificationPanelUnfoldAnimationController$$ExternalSyntheticLambda0 notificationPanelUnfoldAnimationController$$ExternalSyntheticLambda0 = this.f$0.filterShade;
                        UnfoldConstantTranslateAnimator.ViewIdToTranslate viewIdToTranslate = new UnfoldConstantTranslateAnimator.ViewIdToTranslate(R.id.shade_header_system_icons, direction, notificationPanelUnfoldAnimationController$$ExternalSyntheticLambda0, null, 8, null);
                        Function2 function2 = null;
                        int i2 = 8;
                        UnfoldConstantTranslateAnimator.ViewIdToTranslate viewIdToTranslate2 = new UnfoldConstantTranslateAnimator.ViewIdToTranslate(R.id.privacy_container, direction, notificationPanelUnfoldAnimationController$$ExternalSyntheticLambda0, function2, i2, null);
                        UnfoldConstantTranslateAnimator.ViewIdToTranslate viewIdToTranslate3 = new UnfoldConstantTranslateAnimator.ViewIdToTranslate(R.id.carrier_group, direction, notificationPanelUnfoldAnimationController$$ExternalSyntheticLambda0, function2, i2, null);
                        UnfoldConstantTranslateAnimator.Direction direction2 = UnfoldConstantTranslateAnimator.Direction.START;
                        Function2 function22 = null;
                        int i3 = 8;
                        DefaultConstructorMarker defaultConstructorMarker = null;
                        return new UnfoldConstantTranslateAnimator(ArraysKt___ArraysKt.toSet(new UnfoldConstantTranslateAnimator.ViewIdToTranslate[]{viewIdToTranslate, viewIdToTranslate2, viewIdToTranslate3, new UnfoldConstantTranslateAnimator.ViewIdToTranslate(R.id.clock, direction2, notificationPanelUnfoldAnimationController$$ExternalSyntheticLambda0, function22, i3, defaultConstructorMarker), new UnfoldConstantTranslateAnimator.ViewIdToTranslate(R.id.date, direction2, notificationPanelUnfoldAnimationController$$ExternalSyntheticLambda0, function22, i3, defaultConstructorMarker)}), naturalRotationUnfoldProgressProvider);
                }
            }
        });
        final int i2 = 1;
        LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.shade.NotificationPanelUnfoldAnimationController$$ExternalSyntheticLambda1
            public final /* synthetic */ NotificationPanelUnfoldAnimationController f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        return new UnfoldConstantTranslateAnimator(Collections.singleton(new UnfoldConstantTranslateAnimator.ViewIdToTranslate(R.id.quick_settings_panel, UnfoldConstantTranslateAnimator.Direction.START, this.f$0.filterShade, null, 8, null)), naturalRotationUnfoldProgressProvider);
                    default:
                        UnfoldConstantTranslateAnimator.Direction direction = UnfoldConstantTranslateAnimator.Direction.END;
                        NotificationPanelUnfoldAnimationController$$ExternalSyntheticLambda0 notificationPanelUnfoldAnimationController$$ExternalSyntheticLambda0 = this.f$0.filterShade;
                        UnfoldConstantTranslateAnimator.ViewIdToTranslate viewIdToTranslate = new UnfoldConstantTranslateAnimator.ViewIdToTranslate(R.id.shade_header_system_icons, direction, notificationPanelUnfoldAnimationController$$ExternalSyntheticLambda0, null, 8, null);
                        Function2 function2 = null;
                        int i22 = 8;
                        UnfoldConstantTranslateAnimator.ViewIdToTranslate viewIdToTranslate2 = new UnfoldConstantTranslateAnimator.ViewIdToTranslate(R.id.privacy_container, direction, notificationPanelUnfoldAnimationController$$ExternalSyntheticLambda0, function2, i22, null);
                        UnfoldConstantTranslateAnimator.ViewIdToTranslate viewIdToTranslate3 = new UnfoldConstantTranslateAnimator.ViewIdToTranslate(R.id.carrier_group, direction, notificationPanelUnfoldAnimationController$$ExternalSyntheticLambda0, function2, i22, null);
                        UnfoldConstantTranslateAnimator.Direction direction2 = UnfoldConstantTranslateAnimator.Direction.START;
                        Function2 function22 = null;
                        int i3 = 8;
                        DefaultConstructorMarker defaultConstructorMarker = null;
                        return new UnfoldConstantTranslateAnimator(ArraysKt___ArraysKt.toSet(new UnfoldConstantTranslateAnimator.ViewIdToTranslate[]{viewIdToTranslate, viewIdToTranslate2, viewIdToTranslate3, new UnfoldConstantTranslateAnimator.ViewIdToTranslate(R.id.clock, direction2, notificationPanelUnfoldAnimationController$$ExternalSyntheticLambda0, function22, i3, defaultConstructorMarker), new UnfoldConstantTranslateAnimator.ViewIdToTranslate(R.id.date, direction2, notificationPanelUnfoldAnimationController$$ExternalSyntheticLambda0, function22, i3, defaultConstructorMarker)}), naturalRotationUnfoldProgressProvider);
                }
            }
        });
    }
}
