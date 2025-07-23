package com.android.systemui.qs;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.window.OnBackInvokedCallback;
import com.airbnb.lottie.LottieAnimationView;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import com.android.systemui.util.SettingsHelper;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.template.FooterButton;
import com.sec.android.secsetupwizardlib.SuwBaseActivity;
import java.util.Arrays;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class QSOnboardingActivity extends SuwBaseActivity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public LinearLayout firstPage;
    public LottieAnimationView firstPageAnimationView;
    public ScrollView fullScreenScrollView;
    public LinearLayout secondPage;
    public TextView secondPageDescription;
    public LottieAnimationView separateAnimationView;
    public FrameLayout separateStroke;
    public ValueAnimator strokeAnimator;
    public LottieAnimationView togetherAnimationView;
    public FrameLayout togetherStroke;
    public final Lazy settingsHelper$delegate = LazyKt__LazyJVMKt.lazy(new QSOnboardingActivity$$ExternalSyntheticLambda0());
    public final String ON_SECOND_PAGE = "on_second_page";
    public boolean isSplit = true;
    public final QSOnboardingActivity$onBackInvokedCallback$1 onBackInvokedCallback = new OnBackInvokedCallback() { // from class: com.android.systemui.qs.QSOnboardingActivity$onBackInvokedCallback$1
        @Override // android.window.OnBackInvokedCallback
        public final void onBackInvoked() {
            QSOnboardingActivity.this.onBackPressed();
        }
    };

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public static final void access$updatePanelSplit(QSOnboardingActivity qSOnboardingActivity, boolean z) {
        if (qSOnboardingActivity.isSplit == z) {
            return;
        }
        qSOnboardingActivity.isSplit = z;
        ((SettingsHelper) qSOnboardingActivity.settingsHelper$delegate.getValue()).setPanelSplit(z);
        ValueAnimator valueAnimator = qSOnboardingActivity.strokeAnimator;
        if (valueAnimator == null) {
            valueAnimator = null;
        }
        valueAnimator.start();
        qSOnboardingActivity.playLottieAnimation();
        TextView textView = qSOnboardingActivity.secondPageDescription;
        (textView != null ? textView : null).setText(qSOnboardingActivity.isSplit ? R.string.qs_edit_separate_description : R.string.qs_edit_together_description);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public final void onBackPressed() {
        LinearLayout linearLayout = this.firstPage;
        if (linearLayout == null) {
            linearLayout = null;
        }
        if (linearLayout.getVisibility() == 0) {
            setResult(0);
            finish();
            return;
        }
        LinearLayout linearLayout2 = this.secondPage;
        if (linearLayout2 == null) {
            linearLayout2 = null;
        }
        LinearLayout linearLayout3 = this.firstPage;
        if (linearLayout3 == null) {
            linearLayout3 = null;
        }
        transitionToPage(linearLayout2, linearLayout3);
        LottieAnimationView lottieAnimationView = this.firstPageAnimationView;
        (lottieAnimationView != null ? lottieAnimationView : null).playAnimation();
    }

    /* JADX WARN: Type inference failed for: r4v3, types: [android.view.View$OnClickListener, com.android.systemui.qs.QSOnboardingActivity$onCreate$1] */
    @Override // com.sec.android.secsetupwizardlib.SuwBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        FooterBarMixin footerBarMixin;
        LinearLayout linearLayout;
        View findViewById;
        SuwBaseActivity suwBaseActivity = this.mContext;
        super.onCreate(bundle);
        this.isSplit = ((SettingsHelper) this.settingsHelper$delegate.getValue()).isPanelSplit();
        setHeaderTitle(R.string.qs_onboarding_title);
        final boolean z = false;
        this.mIsNeedScrollView = false;
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.sswl_scroll_view);
        if (!this.mIsNeedScrollView) {
            viewGroup = (ViewGroup) findViewById(R.id.sswl_layout_content);
            View findViewById2 = this.mRootLayout.findViewById(R.id.sud_landscape_content_area);
            if (findViewById2 != null && (findViewById = this.mRootLayout.findViewById(R.id.sud_layout_content)) != null) {
                int paddingTop = findViewById.getPaddingTop();
                findViewById.setPadding(findViewById.getPaddingStart(), 0, findViewById.getPaddingEnd(), findViewById.getPaddingBottom());
                findViewById2.setPadding(findViewById2.getPaddingStart(), paddingTop, findViewById2.getPaddingEnd(), findViewById2.getPaddingBottom());
            }
        }
        if (viewGroup != null) {
            viewGroup.removeAllViews();
        }
        LayoutInflater.from(this).inflate(R.layout.qs_onboarding_page, viewGroup);
        setHeaderIcon(getResources().getDrawable(R.drawable.ic_suw_quickpanel_separated));
        int i = isScrollBottomReached() ? R.string.next_description : R.string.qs_onboarding_more;
        ?? r4 = new View.OnClickListener() { // from class: com.android.systemui.qs.QSOnboardingActivity$onCreate$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                QSOnboardingActivity qSOnboardingActivity = QSOnboardingActivity.this;
                int i2 = QSOnboardingActivity.$r8$clinit;
                if (qSOnboardingActivity.isScrollBottomReached()) {
                    qSOnboardingActivity.setResult(-1);
                    qSOnboardingActivity.finish();
                } else {
                    ScrollView scrollView = qSOnboardingActivity.mRootLayout.getScrollView();
                    if (scrollView != null) {
                        scrollView.pageScroll(130);
                    }
                }
            }
        };
        FooterButton footerButton = this.mPrimaryButton;
        if (footerButton == null) {
            FooterButton.Builder builder = new FooterButton.Builder(suwBaseActivity);
            builder.textResourceName = FooterButton.getTextResourceName(builder.context, i);
            String string = builder.context.getString(i);
            builder.onClickListener = r4;
            this.mPrimaryButton = new FooterButton(string, r4, builder.textResourceName);
        } else {
            footerButton.visibility = 0;
            FooterBarMixin.AnonymousClass1 anonymousClass1 = footerButton.buttonListener;
            if (anonymousClass1 != null && (linearLayout = (footerBarMixin = FooterBarMixin.this).buttonContainer) != null) {
                Button button = (Button) linearLayout.findViewById(anonymousClass1.val$id);
                if (button == null) {
                    FooterBarMixin.LOG.atDebug("onVisibilityChanged: button is null, skiped.");
                } else if (button.getVisibility() == 0) {
                    FooterBarMixin.LOG.atDebug("onVisibilityChanged: button visibility is not changed, skiped.");
                } else {
                    button.setVisibility(0);
                    footerBarMixin.autoSetButtonBarVisibility();
                    if (PartnerConfigHelper.isGlifExpressiveEnabled(footerBarMixin.context)) {
                        footerBarMixin.repopulateButtons();
                    }
                }
            }
            this.mPrimaryButton.setText(suwBaseActivity, i);
            this.mPrimaryButton.onClickListener = r4;
        }
        ((FooterBarMixin) this.mRootLayout.getMixin(FooterBarMixin.class)).setPrimaryButton(this.mPrimaryButton);
        this.firstPage = (LinearLayout) requireViewById(R.id.first_page);
        this.secondPage = (LinearLayout) requireViewById(R.id.second_page);
        LinearLayout linearLayout2 = this.firstPage;
        if (linearLayout2 == null) {
            linearLayout2 = null;
        }
        linearLayout2.setVisibility(0);
        LinearLayout linearLayout3 = this.secondPage;
        if (linearLayout3 == null) {
            linearLayout3 = null;
        }
        linearLayout3.setVisibility(8);
        this.separateStroke = (FrameLayout) requireViewById(R.id.separate_stroke_background);
        this.togetherStroke = (FrameLayout) requireViewById(R.id.together_stroke_background);
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.strokeAnimator = ofFloat;
        if (ofFloat == null) {
            ofFloat = null;
        }
        ofFloat.setDuration(500L);
        ValueAnimator valueAnimator = this.strokeAnimator;
        if (valueAnimator == null) {
            valueAnimator = null;
        }
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.qs.QSOnboardingActivity$initStrokeAnimator$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                float floatValue = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                QSOnboardingActivity qSOnboardingActivity = QSOnboardingActivity.this;
                if (qSOnboardingActivity.isSplit) {
                    FrameLayout frameLayout = qSOnboardingActivity.separateStroke;
                    if (frameLayout == null) {
                        frameLayout = null;
                    }
                    frameLayout.setAlpha(floatValue);
                    FrameLayout frameLayout2 = QSOnboardingActivity.this.togetherStroke;
                    (frameLayout2 != null ? frameLayout2 : null).setAlpha(1.0f - floatValue);
                    return;
                }
                FrameLayout frameLayout3 = qSOnboardingActivity.separateStroke;
                if (frameLayout3 == null) {
                    frameLayout3 = null;
                }
                frameLayout3.setAlpha(1.0f - floatValue);
                FrameLayout frameLayout4 = QSOnboardingActivity.this.togetherStroke;
                (frameLayout4 != null ? frameLayout4 : null).setAlpha(floatValue);
            }
        });
        if (this.isSplit) {
            FrameLayout frameLayout = this.separateStroke;
            if (frameLayout == null) {
                frameLayout = null;
            }
            frameLayout.setAlpha(1.0f);
            FrameLayout frameLayout2 = this.togetherStroke;
            if (frameLayout2 == null) {
                frameLayout2 = null;
            }
            frameLayout2.setAlpha(0.0f);
        } else {
            FrameLayout frameLayout3 = this.separateStroke;
            if (frameLayout3 == null) {
                frameLayout3 = null;
            }
            frameLayout3.setAlpha(0.0f);
            FrameLayout frameLayout4 = this.togetherStroke;
            if (frameLayout4 == null) {
                frameLayout4 = null;
            }
            frameLayout4.setAlpha(1.0f);
        }
        this.separateAnimationView = (LottieAnimationView) requireViewById(R.id.separate_animation_view);
        this.togetherAnimationView = (LottieAnimationView) requireViewById(R.id.together_animation_view);
        LottieAnimationView lottieAnimationView = (LottieAnimationView) requireViewById(R.id.first_page_animation_view);
        final boolean z2 = true;
        lottieAnimationView.setUseCompositionFrameRate(true);
        this.firstPageAnimationView = lottieAnimationView;
        lottieAnimationView.playAnimation();
        this.secondPageDescription = (TextView) requireViewById(R.id.second_page_description);
        ((TextView) requireViewById(R.id.separate_button_textview)).setSelected(true);
        ((TextView) requireViewById(R.id.together_button_textview)).setSelected(true);
        TextView textView = this.secondPageDescription;
        if (textView == null) {
            textView = null;
        }
        textView.setText(this.isSplit ? R.string.qs_edit_separate_description : R.string.qs_edit_together_description);
        String string2 = getResources().getString(R.string.qs_onboarding_together_description);
        String substringBefore$default = StringsKt__StringsKt.substringBefore$default(StringsKt__StringsKt.substringAfter$default(string2, "%1$s"), "%2$s");
        ClickableSpan clickableSpan = new ClickableSpan() { // from class: com.android.systemui.qs.QSOnboardingActivity$formatClickableText$clickableSpan$1
            @Override // android.text.style.ClickableSpan
            public final void onClick(View view) {
                QSOnboardingActivity qSOnboardingActivity = QSOnboardingActivity.this;
                LinearLayout linearLayout4 = qSOnboardingActivity.firstPage;
                if (linearLayout4 == null) {
                    linearLayout4 = null;
                }
                LinearLayout linearLayout5 = qSOnboardingActivity.secondPage;
                if (linearLayout5 == null) {
                    linearLayout5 = null;
                }
                qSOnboardingActivity.transitionToPage(linearLayout4, linearLayout5);
                LottieAnimationView lottieAnimationView2 = qSOnboardingActivity.firstPageAnimationView;
                (lottieAnimationView2 != null ? lottieAnimationView2 : null).pauseAnimation();
                qSOnboardingActivity.playLottieAnimation();
            }
        };
        int i2 = StringCompanionObject.$r8$clinit;
        SpannableString spannableString = new SpannableString(String.format(string2, Arrays.copyOf(new Object[]{"", ""}, 2)));
        int indexOf$default = StringsKt__StringsKt.indexOf$default(spannableString, substringBefore$default, 0, false, 6);
        int length = substringBefore$default.length() + indexOf$default;
        spannableString.setSpan(new StyleSpan(1), indexOf$default, length, 33);
        spannableString.setSpan(clickableSpan, indexOf$default, length, 33);
        TextView textView2 = (TextView) requireViewById(R.id.next_page_link_text);
        textView2.setText(spannableString);
        textView2.setMovementMethod(LinkMovementMethod.getInstance());
        final ScrollView scrollView = (ScrollView) requireViewById(R.id.sud_scroll_view);
        this.fullScreenScrollView = scrollView;
        if (scrollView == null) {
            scrollView = null;
        }
        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() { // from class: com.android.systemui.qs.QSOnboardingActivity$initScrollView$1$1
            @Override // android.view.View.OnScrollChangeListener
            public final void onScrollChange(View view, int i3, int i4, int i5, int i6) {
                QSOnboardingActivity qSOnboardingActivity = QSOnboardingActivity.this;
                int i7 = QSOnboardingActivity.$r8$clinit;
                int i8 = qSOnboardingActivity.isScrollBottomReached() ? R.string.next_description : R.string.qs_onboarding_more;
                FooterButton footerButton2 = qSOnboardingActivity.mPrimaryButton;
                if (footerButton2 != null) {
                    footerButton2.setText(qSOnboardingActivity.mContext, i8);
                }
            }
        });
        scrollView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.qs.QSOnboardingActivity$initScrollView$1$2
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
                float measuredWidth = scrollView.getMeasuredWidth() * 0.9f;
                View requireViewById = scrollView.requireViewById(R.id.qs_onboarding_page);
                QSOnboardingActivity qSOnboardingActivity = this;
                FrameLayout frameLayout5 = (FrameLayout) requireViewById;
                int i11 = QSOnboardingActivity.$r8$clinit;
                qSOnboardingActivity.getClass();
                SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor = (SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class);
                int dimensionPixelSize = (secQsUiDisplayModeInteractor.isTablet() || secQsUiDisplayModeInteractor.isFoldWide()) ? frameLayout5.getResources().getDimensionPixelSize(R.dimen.qs_onboarding_large_screen_page_width) : frameLayout5.getResources().getDimensionPixelSize(R.dimen.qs_onboarding_page_width);
                int i12 = (int) measuredWidth;
                if (frameLayout5.getLayoutParams().width != Math.min(dimensionPixelSize, i12)) {
                    frameLayout5.getLayoutParams().width = Math.min(dimensionPixelSize, i12);
                    frameLayout5.requestLayout();
                }
                QSOnboardingActivity qSOnboardingActivity2 = this;
                int i13 = qSOnboardingActivity2.isScrollBottomReached() ? R.string.next_description : R.string.qs_onboarding_more;
                FooterButton footerButton2 = qSOnboardingActivity2.mPrimaryButton;
                if (footerButton2 != null) {
                    footerButton2.setText(qSOnboardingActivity2.mContext, i13);
                }
            }
        });
        FrameLayout frameLayout5 = (FrameLayout) requireViewById(R.id.separate_button);
        frameLayout5.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.QSOnboardingActivity$initClickListeners$1$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                QSOnboardingActivity.access$updatePanelSplit(QSOnboardingActivity.this, z2);
            }
        });
        frameLayout5.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.systemui.qs.QSOnboardingActivity$initClickListeners$1$2
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                QSOnboardingActivity.access$updatePanelSplit(QSOnboardingActivity.this, z2);
                return true;
            }
        });
        FrameLayout frameLayout6 = (FrameLayout) requireViewById(R.id.together_button);
        frameLayout6.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.QSOnboardingActivity$initClickListeners$1$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                QSOnboardingActivity.access$updatePanelSplit(QSOnboardingActivity.this, z);
            }
        });
        frameLayout6.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.systemui.qs.QSOnboardingActivity$initClickListeners$1$2
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                QSOnboardingActivity.access$updatePanelSplit(QSOnboardingActivity.this, z);
                return true;
            }
        });
        FrameLayout frameLayout7 = (FrameLayout) requireViewById(R.id.sud_layout_content);
        SecQsUiDisplayModeInteractor secQsUiDisplayModeInteractor = (SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class);
        frameLayout7.setPadding(0, (secQsUiDisplayModeInteractor.isTablet() || secQsUiDisplayModeInteractor.isFoldWide()) ? frameLayout7.getPaddingTop() : 0, 0, frameLayout7.getPaddingBottom());
        ViewGroup.LayoutParams layoutParams = frameLayout7.getLayoutParams();
        ViewGroup.MarginLayoutParams marginLayoutParams = layoutParams instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams : null;
        if (marginLayoutParams != null) {
            marginLayoutParams.leftMargin = 0;
            marginLayoutParams.rightMargin = 0;
        }
        if (bundle != null ? bundle.getBoolean(this.ON_SECOND_PAGE) : false) {
            LinearLayout linearLayout4 = this.firstPage;
            if (linearLayout4 == null) {
                linearLayout4 = null;
            }
            linearLayout4.setVisibility(8);
            LinearLayout linearLayout5 = this.secondPage;
            (linearLayout5 != null ? linearLayout5 : null).setVisibility(0);
        }
    }

    @Override // com.sec.android.secsetupwizardlib.SuwBaseActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        LinearLayout linearLayout = this.secondPage;
        if (linearLayout == null) {
            linearLayout = null;
        }
        bundle.putBoolean(this.ON_SECOND_PAGE, linearLayout.getVisibility() == 0);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onStart() {
        super.onStart();
        getOnBackInvokedDispatcher().registerOnBackInvokedCallback(0, this.onBackInvokedCallback);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onStop() {
        super.onStop();
        getOnBackInvokedDispatcher().unregisterOnBackInvokedCallback(this.onBackInvokedCallback);
    }

    public final void playLottieAnimation() {
        if (this.isSplit) {
            LottieAnimationView lottieAnimationView = this.separateAnimationView;
            if (lottieAnimationView == null) {
                lottieAnimationView = null;
            }
            lottieAnimationView.setUseCompositionFrameRate(true);
            LottieAnimationView lottieAnimationView2 = this.separateAnimationView;
            if (lottieAnimationView2 == null) {
                lottieAnimationView2 = null;
            }
            lottieAnimationView2.playAnimation();
            LottieAnimationView lottieAnimationView3 = this.togetherAnimationView;
            if (lottieAnimationView3 == null) {
                lottieAnimationView3 = null;
            }
            lottieAnimationView3.setProgress(0.0f);
            LottieAnimationView lottieAnimationView4 = this.togetherAnimationView;
            (lottieAnimationView4 != null ? lottieAnimationView4 : null).pauseAnimation();
            return;
        }
        LottieAnimationView lottieAnimationView5 = this.togetherAnimationView;
        if (lottieAnimationView5 == null) {
            lottieAnimationView5 = null;
        }
        lottieAnimationView5.setUseCompositionFrameRate(true);
        LottieAnimationView lottieAnimationView6 = this.togetherAnimationView;
        if (lottieAnimationView6 == null) {
            lottieAnimationView6 = null;
        }
        lottieAnimationView6.playAnimation();
        LottieAnimationView lottieAnimationView7 = this.separateAnimationView;
        if (lottieAnimationView7 == null) {
            lottieAnimationView7 = null;
        }
        lottieAnimationView7.setProgress(0.0f);
        LottieAnimationView lottieAnimationView8 = this.separateAnimationView;
        (lottieAnimationView8 != null ? lottieAnimationView8 : null).pauseAnimation();
    }

    public final void transitionToPage(final View view, final View view2) {
        ScrollView scrollView = this.fullScreenScrollView;
        if (scrollView == null) {
            scrollView = null;
        }
        scrollView.animate().alpha(0.0f).setDuration(150L).withEndAction(new Runnable() { // from class: com.android.systemui.qs.QSOnboardingActivity$transitionToPage$1
            @Override // java.lang.Runnable
            public final void run() {
                view.setVisibility(8);
                ScrollView scrollView2 = this.fullScreenScrollView;
                if (scrollView2 == null) {
                    scrollView2 = null;
                }
                scrollView2.scrollTo(0, 0);
                View view3 = view2;
                LinearLayout linearLayout = this.secondPage;
                if (linearLayout == null) {
                    linearLayout = null;
                }
                if (Intrinsics.areEqual(view3, linearLayout)) {
                    this.setHeaderTitle(R.string.qs_edit_view_type);
                } else {
                    this.setHeaderTitle(R.string.qs_onboarding_title);
                }
                view2.setVisibility(0);
                ScrollView scrollView3 = this.fullScreenScrollView;
                ViewPropertyAnimator duration = (scrollView3 != null ? scrollView3 : null).animate().alpha(1.0f).setDuration(150L);
                final QSOnboardingActivity qSOnboardingActivity = this;
                duration.withEndAction(new Runnable() { // from class: com.android.systemui.qs.QSOnboardingActivity$transitionToPage$1.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        QSOnboardingActivity qSOnboardingActivity2 = QSOnboardingActivity.this;
                        int i = QSOnboardingActivity.$r8$clinit;
                        qSOnboardingActivity2.playLottieAnimation();
                    }
                }).start();
            }
        }).start();
    }
}
