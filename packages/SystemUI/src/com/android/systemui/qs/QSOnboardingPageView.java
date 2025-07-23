package com.android.systemui.qs;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class QSOnboardingPageView extends FrameLayout {
    public QSOnboardingPageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor = (SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class);
        if (secQsUiDisplayModeInteractor.isTablet() || secQsUiDisplayModeInteractor.isFoldWide()) {
            FrameLayout frameLayout = (FrameLayout) requireViewById(R.id.button_parent);
            frameLayout.getLayoutParams().width = frameLayout.getResources().getDimensionPixelSize(R.dimen.qs_onboarding_large_screen_button_parent_width);
            updateButton(R.id.separate_button);
            updateButton(R.id.together_button);
            updateButton(R.id.together_stroke_background);
            updateButton(R.id.separate_stroke_background);
            ((ViewGroup.MarginLayoutParams) ((LinearLayout) requireViewById(R.id.first_page)).getLayoutParams()).topMargin = getResources().getDimensionPixelSize(R.dimen.qs_onboarding_large_screen_first_page_top_margin);
            ((ViewGroup.MarginLayoutParams) ((LinearLayout) requireViewById(R.id.second_page)).getLayoutParams()).topMargin = getResources().getDimensionPixelSize(R.dimen.qs_onboarding_large_screen_second_page_top_margin);
            updateTextSize(R.id.first_page_description);
            updateTextSize(R.id.next_page_link_text);
            updateTextSize(R.id.second_page_description);
            updateButtonText(R.id.separate_button_textview);
            updateButtonText(R.id.together_button_textview);
            LottieAnimationView lottieAnimationView = (LottieAnimationView) requireViewById(R.id.separate_animation_view);
            lottieAnimationView.getLayoutParams().width = lottieAnimationView.getResources().getDimensionPixelSize(R.dimen.qs_onboarding_large_screen_animation_view_width);
            lottieAnimationView.getLayoutParams().height = lottieAnimationView.getResources().getDimensionPixelSize(R.dimen.qs_onboarding_large_screen_animation_view_height);
            LottieAnimationView lottieAnimationView2 = (LottieAnimationView) requireViewById(R.id.together_animation_view);
            lottieAnimationView2.getLayoutParams().width = lottieAnimationView2.getResources().getDimensionPixelSize(R.dimen.qs_onboarding_large_screen_animation_view_width);
            lottieAnimationView2.getLayoutParams().height = lottieAnimationView2.getResources().getDimensionPixelSize(R.dimen.qs_onboarding_large_screen_animation_view_height);
            LottieAnimationView lottieAnimationView3 = (LottieAnimationView) requireViewById(R.id.first_page_animation_view);
            lottieAnimationView3.getLayoutParams().width = lottieAnimationView3.getResources().getDimensionPixelSize(R.dimen.qs_onboarding_large_screen_first_page_animation_view_width);
            lottieAnimationView3.getLayoutParams().height = lottieAnimationView3.getResources().getDimensionPixelSize(R.dimen.qs_onboarding_large_screen_first_page_animation_view_height);
        }
    }

    public final void updateButton(int i) {
        FrameLayout frameLayout = (FrameLayout) requireViewById(i);
        frameLayout.getLayoutParams().width = frameLayout.getResources().getDimensionPixelSize(R.dimen.qs_onboarding_large_screen_button_width);
        frameLayout.getLayoutParams().height = frameLayout.getResources().getDimensionPixelSize(R.dimen.qs_onboarding_large_screen_button_height);
    }

    public final void updateButtonText(int i) {
        TextView textView = (TextView) requireViewById(i);
        textView.setTextSize(0, textView.getResources().getDimension(R.dimen.qs_onboarding_large_screen_button_text_size));
        ((ViewGroup.MarginLayoutParams) textView.getLayoutParams()).topMargin = textView.getResources().getDimensionPixelSize(R.dimen.qs_onboarding_large_screen_button_text_top_margin);
    }

    public final void updateTextSize(int i) {
        TextView textView = (TextView) requireViewById(i);
        textView.setTextSize(0, textView.getResources().getDimension(R.dimen.qs_onboarding_large_screen_text_size));
        textView.setLineHeight(0, textView.getResources().getDimension(R.dimen.qs_onboarding_large_screen_text_line_height));
    }
}
