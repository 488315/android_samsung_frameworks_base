package com.android.systemui.aibrief.ui;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.android.systemui.FontSizeUtils;
import com.android.systemui.R;
import com.android.systemui.aibrief.data.NowBarData;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class BriefNowBarView extends BriefNowBarBaseView {
    public static final int $stable = 8;
    private final Lazy icon$delegate;
    private final Lazy iconContainer$delegate;
    private final Lazy mainText$delegate;
    private final Lazy starIcon$delegate;
    private final Lazy subText$delegate;

    public BriefNowBarView(Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
    }

    private final ImageView getIcon() {
        return (ImageView) this.icon$delegate.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final FrameLayout getIconContainer() {
        return (FrameLayout) this.iconContainer$delegate.getValue();
    }

    private final TextView getMainText() {
        return (TextView) this.mainText$delegate.getValue();
    }

    private final LottieAnimationView getStarIcon() {
        return (LottieAnimationView) this.starIcon$delegate.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final TextView getSubText() {
        return (TextView) this.subText$delegate.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final FrameLayout iconContainer_delegate$lambda$3(BriefNowBarView briefNowBarView) {
        return (FrameLayout) briefNowBarView.requireViewById(R.id.ai_brief_now_bar_icon_container);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ImageView icon_delegate$lambda$2(BriefNowBarView briefNowBarView) {
        return (ImageView) briefNowBarView.requireViewById(R.id.ai_brief_now_bar_image);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final TextView mainText_delegate$lambda$0(BriefNowBarView briefNowBarView) {
        return (TextView) briefNowBarView.requireViewById(R.id.ai_brief_main_text);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final LottieAnimationView starIcon_delegate$lambda$4(BriefNowBarView briefNowBarView) {
        return (LottieAnimationView) briefNowBarView.requireViewById(R.id.ai_brief_now_bar_four_star_image);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final TextView subText_delegate$lambda$1(BriefNowBarView briefNowBarView) {
        return (TextView) briefNowBarView.requireViewById(R.id.ai_brief_sub_text);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void translationAnimation() {
        final TextView mainText = getMainText();
        final ValueAnimator ofFloat = ValueAnimator.ofFloat(mainText.getTranslationY(), 0.0f);
        ofFloat.setDuration(150L);
        ofFloat.setInterpolator(BriefNowBarBaseView.Companion.getTRANSLATION_INTERPOLATOR());
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.aibrief.ui.BriefNowBarView$translationAnimation$1$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                mainText.setTranslationY(((Float) ofFloat.getAnimatedValue()).floatValue());
            }
        });
        ofFloat.start();
    }

    @Override // com.android.systemui.aibrief.ui.BriefNowBarBaseView
    public void initAnimatedViews() {
        if (getSubText().getText().length() > 0) {
            getSubText().setAlpha(0.0f);
            getMainText().setTranslationY(getSubText().getHeight() / 2);
        } else {
            getMainText().setTranslationY(0.0f);
        }
        getIconContainer().setAlpha(0.0f);
        getIconContainer().setVisibility(0);
    }

    @Override // com.android.systemui.aibrief.ui.BriefNowBarBaseView
    public void resetViews() {
        getMainText().setTranslationY(0.0f);
        getIconContainer().setAlpha(1.0f);
        getIconContainer().setVisibility(0);
        getStarIcon().setAlpha(0.0f);
    }

    @Override // com.android.systemui.aibrief.ui.BriefNowBarBaseView
    public void startFourStarAnimation() {
        LottieAnimationView starIcon = getStarIcon();
        starIcon.playAnimation();
        showAnimation(starIcon);
        starIcon.addAnimatorListener(new BriefNowBarView$startFourStarAnimation$1$1(this, starIcon));
    }

    @Override // com.android.systemui.aibrief.ui.BriefNowBarBaseView
    public void updateNowBarData(NowBarData nowBarData, GradientDrawable gradientDrawable) {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ai_brief_now_bar_container);
        if (linearLayout != null) {
            if (gradientDrawable != null) {
                linearLayout.setBackground(gradientDrawable);
            }
            TextView mainText = getMainText();
            mainText.setText(nowBarData.getTitle());
            mainText.setMaxLines(nowBarData.getSubTitle() != null ? 1 : 2);
            TextView subText = getSubText();
            if (nowBarData.getSubTitle() != null) {
                subText.setText(nowBarData.getSubTitle());
            } else {
                subText.setVisibility(8);
            }
            ImageView icon = getIcon();
            byte[] icon2 = nowBarData.getIcon();
            if (icon2 != null) {
                icon.setImageBitmap(toBitmap(icon2));
            } else {
                icon.setVisibility(8);
            }
            getStarIcon().setAlpha(0.0f);
        }
    }

    @Override // com.android.systemui.aibrief.ui.BriefNowBarBaseView
    public void updateNowBarResources() {
        ViewGroup.LayoutParams layoutParams;
        ViewGroup.LayoutParams layoutParams2;
        FontSizeUtils.updateFontSize(getMainText(), R.dimen.ai_brief_main_text_size);
        FontSizeUtils.updateFontSize(getSubText(), R.dimen.ai_brief_sub_text_size);
        int dimensionPixelSize = getContext().getResources().getDimensionPixelSize(R.dimen.ai_brief_now_bar_image_size);
        LottieAnimationView lottieAnimationView = (LottieAnimationView) findViewById(R.id.ai_brief_now_bar_four_star_image);
        if (lottieAnimationView != null && (layoutParams2 = lottieAnimationView.getLayoutParams()) != null) {
            layoutParams2.height = dimensionPixelSize;
            layoutParams2.width = dimensionPixelSize;
        }
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.ai_brief_now_bar_icon_container);
        if (frameLayout != null && (layoutParams = frameLayout.getLayoutParams()) != null) {
            layoutParams.width = dimensionPixelSize;
            layoutParams.height = dimensionPixelSize;
        }
        FrameLayout frameLayout2 = (FrameLayout) findViewById(R.id.ai_brief_now_bar_icon_parent_container);
        if (frameLayout2 != null) {
            ((ViewGroup.MarginLayoutParams) frameLayout2.getLayoutParams()).setMarginStart(frameLayout2.getContext().getResources().getDimensionPixelSize(R.dimen.ai_brief_now_bar_image_container_start_margin));
        }
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ai_brief_text_container);
        if (linearLayout != null) {
            ((ViewGroup.MarginLayoutParams) linearLayout.getLayoutParams()).setMarginStart(linearLayout.getContext().getResources().getDimensionPixelSize(R.dimen.ai_brief_now_bar_text_container_start_margin));
            linearLayout.setPadding(0, 0, linearLayout.getContext().getResources().getDimensionPixelSize(R.dimen.ai_brief_now_bar_text_container_end_padding), 0);
        }
    }

    @Override // com.android.systemui.aibrief.ui.BriefNowBarBaseView
    public void updateViewAlpha(boolean z) {
        getMainText().setAlpha(z ? 1.0f : 0.7f);
        getSubText().setAlpha(z ? 1.0f : 0.7f);
        getIcon().setAlpha(z ? 1.0f : 0.7f);
    }

    public /* synthetic */ BriefNowBarView(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    public BriefNowBarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        final int i = 0;
        this.mainText$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.aibrief.ui.BriefNowBarView$$ExternalSyntheticLambda0
            public final /* synthetic */ BriefNowBarView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                TextView mainText_delegate$lambda$0;
                TextView subText_delegate$lambda$1;
                ImageView icon_delegate$lambda$2;
                FrameLayout iconContainer_delegate$lambda$3;
                LottieAnimationView starIcon_delegate$lambda$4;
                int i2 = i;
                BriefNowBarView briefNowBarView = this.f$0;
                switch (i2) {
                    case 0:
                        mainText_delegate$lambda$0 = BriefNowBarView.mainText_delegate$lambda$0(briefNowBarView);
                        return mainText_delegate$lambda$0;
                    case 1:
                        subText_delegate$lambda$1 = BriefNowBarView.subText_delegate$lambda$1(briefNowBarView);
                        return subText_delegate$lambda$1;
                    case 2:
                        icon_delegate$lambda$2 = BriefNowBarView.icon_delegate$lambda$2(briefNowBarView);
                        return icon_delegate$lambda$2;
                    case 3:
                        iconContainer_delegate$lambda$3 = BriefNowBarView.iconContainer_delegate$lambda$3(briefNowBarView);
                        return iconContainer_delegate$lambda$3;
                    default:
                        starIcon_delegate$lambda$4 = BriefNowBarView.starIcon_delegate$lambda$4(briefNowBarView);
                        return starIcon_delegate$lambda$4;
                }
            }
        });
        final int i2 = 1;
        this.subText$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.aibrief.ui.BriefNowBarView$$ExternalSyntheticLambda0
            public final /* synthetic */ BriefNowBarView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                TextView mainText_delegate$lambda$0;
                TextView subText_delegate$lambda$1;
                ImageView icon_delegate$lambda$2;
                FrameLayout iconContainer_delegate$lambda$3;
                LottieAnimationView starIcon_delegate$lambda$4;
                int i22 = i2;
                BriefNowBarView briefNowBarView = this.f$0;
                switch (i22) {
                    case 0:
                        mainText_delegate$lambda$0 = BriefNowBarView.mainText_delegate$lambda$0(briefNowBarView);
                        return mainText_delegate$lambda$0;
                    case 1:
                        subText_delegate$lambda$1 = BriefNowBarView.subText_delegate$lambda$1(briefNowBarView);
                        return subText_delegate$lambda$1;
                    case 2:
                        icon_delegate$lambda$2 = BriefNowBarView.icon_delegate$lambda$2(briefNowBarView);
                        return icon_delegate$lambda$2;
                    case 3:
                        iconContainer_delegate$lambda$3 = BriefNowBarView.iconContainer_delegate$lambda$3(briefNowBarView);
                        return iconContainer_delegate$lambda$3;
                    default:
                        starIcon_delegate$lambda$4 = BriefNowBarView.starIcon_delegate$lambda$4(briefNowBarView);
                        return starIcon_delegate$lambda$4;
                }
            }
        });
        final int i3 = 2;
        this.icon$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.aibrief.ui.BriefNowBarView$$ExternalSyntheticLambda0
            public final /* synthetic */ BriefNowBarView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                TextView mainText_delegate$lambda$0;
                TextView subText_delegate$lambda$1;
                ImageView icon_delegate$lambda$2;
                FrameLayout iconContainer_delegate$lambda$3;
                LottieAnimationView starIcon_delegate$lambda$4;
                int i22 = i3;
                BriefNowBarView briefNowBarView = this.f$0;
                switch (i22) {
                    case 0:
                        mainText_delegate$lambda$0 = BriefNowBarView.mainText_delegate$lambda$0(briefNowBarView);
                        return mainText_delegate$lambda$0;
                    case 1:
                        subText_delegate$lambda$1 = BriefNowBarView.subText_delegate$lambda$1(briefNowBarView);
                        return subText_delegate$lambda$1;
                    case 2:
                        icon_delegate$lambda$2 = BriefNowBarView.icon_delegate$lambda$2(briefNowBarView);
                        return icon_delegate$lambda$2;
                    case 3:
                        iconContainer_delegate$lambda$3 = BriefNowBarView.iconContainer_delegate$lambda$3(briefNowBarView);
                        return iconContainer_delegate$lambda$3;
                    default:
                        starIcon_delegate$lambda$4 = BriefNowBarView.starIcon_delegate$lambda$4(briefNowBarView);
                        return starIcon_delegate$lambda$4;
                }
            }
        });
        final int i4 = 3;
        this.iconContainer$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.aibrief.ui.BriefNowBarView$$ExternalSyntheticLambda0
            public final /* synthetic */ BriefNowBarView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                TextView mainText_delegate$lambda$0;
                TextView subText_delegate$lambda$1;
                ImageView icon_delegate$lambda$2;
                FrameLayout iconContainer_delegate$lambda$3;
                LottieAnimationView starIcon_delegate$lambda$4;
                int i22 = i4;
                BriefNowBarView briefNowBarView = this.f$0;
                switch (i22) {
                    case 0:
                        mainText_delegate$lambda$0 = BriefNowBarView.mainText_delegate$lambda$0(briefNowBarView);
                        return mainText_delegate$lambda$0;
                    case 1:
                        subText_delegate$lambda$1 = BriefNowBarView.subText_delegate$lambda$1(briefNowBarView);
                        return subText_delegate$lambda$1;
                    case 2:
                        icon_delegate$lambda$2 = BriefNowBarView.icon_delegate$lambda$2(briefNowBarView);
                        return icon_delegate$lambda$2;
                    case 3:
                        iconContainer_delegate$lambda$3 = BriefNowBarView.iconContainer_delegate$lambda$3(briefNowBarView);
                        return iconContainer_delegate$lambda$3;
                    default:
                        starIcon_delegate$lambda$4 = BriefNowBarView.starIcon_delegate$lambda$4(briefNowBarView);
                        return starIcon_delegate$lambda$4;
                }
            }
        });
        final int i5 = 4;
        this.starIcon$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.aibrief.ui.BriefNowBarView$$ExternalSyntheticLambda0
            public final /* synthetic */ BriefNowBarView f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                TextView mainText_delegate$lambda$0;
                TextView subText_delegate$lambda$1;
                ImageView icon_delegate$lambda$2;
                FrameLayout iconContainer_delegate$lambda$3;
                LottieAnimationView starIcon_delegate$lambda$4;
                int i22 = i5;
                BriefNowBarView briefNowBarView = this.f$0;
                switch (i22) {
                    case 0:
                        mainText_delegate$lambda$0 = BriefNowBarView.mainText_delegate$lambda$0(briefNowBarView);
                        return mainText_delegate$lambda$0;
                    case 1:
                        subText_delegate$lambda$1 = BriefNowBarView.subText_delegate$lambda$1(briefNowBarView);
                        return subText_delegate$lambda$1;
                    case 2:
                        icon_delegate$lambda$2 = BriefNowBarView.icon_delegate$lambda$2(briefNowBarView);
                        return icon_delegate$lambda$2;
                    case 3:
                        iconContainer_delegate$lambda$3 = BriefNowBarView.iconContainer_delegate$lambda$3(briefNowBarView);
                        return iconContainer_delegate$lambda$3;
                    default:
                        starIcon_delegate$lambda$4 = BriefNowBarView.starIcon_delegate$lambda$4(briefNowBarView);
                        return starIcon_delegate$lambda$4;
                }
            }
        });
    }
}
