package com.android.systemui.brightness.ui.compose;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import com.android.systemui.R;
import com.android.systemui.brightness.ui.viewmodel.BrightnessSliderViewModel;
import com.android.systemui.settings.brightness.ui.BrightnessWarningToast;
import com.android.systemui.toast.SystemUIToast;
import com.android.systemui.utils.PolicyRestriction;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public final /* synthetic */ class BrightnessSliderKt$$ExternalSyntheticLambda3 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ BrightnessSliderKt$$ExternalSyntheticLambda3(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        View view;
        switch (this.$r8$classId) {
            case 0:
                PolicyRestriction policyRestriction = (PolicyRestriction) this.f$0;
                if (policyRestriction instanceof PolicyRestriction.Restricted) {
                    ((Function1) this.f$1).mo781invoke(policyRestriction);
                }
                break;
            default:
                Context context = (Context) this.f$1;
                final BrightnessWarningToast brightnessWarningToast = ((BrightnessSliderViewModel) this.f$0).brightnessWarningToast;
                View view2 = brightnessWarningToast.toastView;
                if ((view2 == null || !view2.isAttachedToWindow()) && ((view = brightnessWarningToast.toastView) == null || !view.isAttachedToWindow())) {
                    Resources resources = context.getResources();
                    final SystemUIToast systemUIToastCreateToast = brightnessWarningToast.toastFactory.createToast(context, context, resources.getString(R.string.quick_settings_brightness_unable_adjust_msg), context.getPackageName(), context.getUserId(), resources.getConfiguration().orientation);
                    brightnessWarningToast.toastView = systemUIToastCreateToast.mToastView;
                    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                    layoutParams.height = -2;
                    layoutParams.width = -2;
                    layoutParams.format = -3;
                    layoutParams.setTitle("Brightness warning toast");
                    layoutParams.type = 2017;
                    layoutParams.flags = 152;
                    layoutParams.y = systemUIToastCreateToast.getYOffset().intValue();
                    int absoluteGravity = Gravity.getAbsoluteGravity(systemUIToastCreateToast.getGravity().intValue(), resources.getConfiguration().getLayoutDirection());
                    layoutParams.gravity = absoluteGravity;
                    if ((absoluteGravity & 7) == 7) {
                        layoutParams.horizontalWeight = 1.0f;
                    }
                    if ((absoluteGravity & 112) == 112) {
                        layoutParams.verticalWeight = 1.0f;
                    }
                    brightnessWarningToast.windowManager.addView(brightnessWarningToast.toastView, layoutParams);
                    Animator animator = systemUIToastCreateToast.mInAnimator;
                    if (animator != null) {
                        animator.start();
                    }
                    View view3 = brightnessWarningToast.toastView;
                    if (view3 != null) {
                        view3.postDelayed(new Runnable() { // from class: com.android.systemui.settings.brightness.ui.BrightnessWarningToast$show$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                Animator animator2 = systemUIToastCreateToast.mOutAnimator;
                                if (animator2 != null) {
                                    animator2.start();
                                    final BrightnessWarningToast brightnessWarningToast2 = brightnessWarningToast;
                                    animator2.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.settings.brightness.ui.BrightnessWarningToast$show$1.1
                                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                        public final void onAnimationEnd(Animator animator3) {
                                            View view4 = brightnessWarningToast2.toastView;
                                            if (view4 != null && view4.isAttachedToWindow()) {
                                                BrightnessWarningToast brightnessWarningToast3 = brightnessWarningToast2;
                                                brightnessWarningToast3.windowManager.removeViewImmediate(brightnessWarningToast3.toastView);
                                            }
                                            brightnessWarningToast2.toastView = null;
                                        }
                                    });
                                }
                            }
                        }, 3000L);
                    }
                }
                break;
        }
        return Unit.INSTANCE;
    }
}
