package com.android.systemui.biometrics.ui.binder;

import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.android.systemui.Flags;
import com.android.systemui.R;
import com.android.systemui.biometrics.AuthContainerView;
import com.android.systemui.biometrics.ui.binder.Spaghetti;
import com.android.systemui.biometrics.ui.viewmodel.PromptViewModel;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.statusbar.VibratorHelper;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class BiometricViewBinder {
    static {
        new BiometricViewBinder();
    }

    private BiometricViewBinder() {
    }

    public static final Spaghetti bind(View view, PromptViewModel promptViewModel, AuthContainerView.AnonymousClass2 anonymousClass2, View view2, Spaghetti.Callback callback, CoroutineScope coroutineScope, VibratorHelper vibratorHelper) {
        Flags.constraintBp();
        Object systemService = view.getContext().getSystemService((Class<Object>) AccessibilityManager.class);
        Intrinsics.checkNotNull(systemService);
        AccessibilityManager accessibilityManager = (AccessibilityManager) systemService;
        int color = view.getResources().getColor(R.color.biometric_dialog_error, view.getContext().getTheme());
        int color2 = view.getResources().getColor(R.color.biometric_dialog_gray, view.getContext().getTheme());
        ImageView imageView = (ImageView) view.requireViewById(R.id.logo);
        TextView textView = (TextView) view.requireViewById(R.id.logo_description);
        TextView textView2 = (TextView) view.requireViewById(R.id.title);
        TextView textView3 = (TextView) view.requireViewById(R.id.subtitle);
        TextView textView4 = (TextView) view.requireViewById(R.id.description);
        LinearLayout linearLayout = (LinearLayout) view.requireViewById(R.id.customized_view_container);
        Flags.constraintBp();
        View requireViewById = view.requireViewById(R.id.panel);
        Intrinsics.checkNotNull(requireViewById);
        textView2.setSelected((accessibilityManager.isEnabled() && accessibilityManager.isTouchExplorationEnabled()) ? false : true);
        textView3.setSelected((accessibilityManager.isEnabled() && accessibilityManager.isTouchExplorationEnabled()) ? false : true);
        textView4.setMovementMethod(new ScrollingMovementMethod());
        LottieAnimationView lottieAnimationView = (LottieAnimationView) view.requireViewById(R.id.biometric_icon_overlay);
        LottieAnimationView lottieAnimationView2 = (LottieAnimationView) view.requireViewById(R.id.biometric_icon);
        Flags.constraintBp();
        TextView textView5 = (TextView) view.requireViewById(R.id.indicator);
        Button button = (Button) view.requireViewById(R.id.button_negative);
        Button button2 = (Button) view.requireViewById(R.id.button_cancel);
        Button button3 = (Button) view.requireViewById(R.id.button_use_credential);
        Button button4 = (Button) view.requireViewById(R.id.button_confirm);
        Button button5 = (Button) view.requireViewById(R.id.button_try_again);
        Spaghetti spaghetti = new Spaghetti(view, promptViewModel, view.getContext().getApplicationContext(), coroutineScope);
        RepeatWhenAttachedKt.repeatWhenAttached(view, EmptyCoroutineContext.INSTANCE, new BiometricViewBinder$bind$1(promptViewModel, view, imageView, textView, textView2, textView3, textView4, linearLayout, callback, button, button2, button3, button4, button5, spaghetti, new Ref$BooleanRef(), textView5, null, anonymousClass2, lottieAnimationView2, lottieAnimationView, null, requireViewById, view2, accessibilityManager, color, color2, vibratorHelper, null));
        return spaghetti;
    }
}
