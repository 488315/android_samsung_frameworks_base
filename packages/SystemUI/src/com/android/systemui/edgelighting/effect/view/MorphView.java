package com.android.systemui.edgelighting.effect.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Slog;
import android.view.DisplayCutout;
import android.view.LayoutInflater;
import android.view.SemBlurInfo;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.internal.dynamicanimation.animation.DynamicAnimation;
import com.android.internal.dynamicanimation.animation.SpringAnimation;
import com.android.internal.dynamicanimation.animation.SpringForce;
import com.android.systemui.R;
import com.android.systemui.edgelighting.effect.container.EdgeLightingDialog;
import com.android.systemui.edgelighting.effect.container.NotificationEffect;
import com.android.systemui.edgelighting.effect.interfaces.IEdgeLightingWindowCallback;
import com.android.systemui.edgelighting.effect.utils.Utils;
import com.android.systemui.edgelighting.effect.utils.VerificationCodeUtils;
import com.samsung.android.content.clipboard.SemClipboardManager;
import com.samsung.android.content.clipboard.data.SemTextClipData;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class MorphView extends AbsToastView {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final TextView mCodeText;
    public final ImageView mExpandButton;
    public final LinearLayout mIconRootLayout;
    public boolean mIsGrayScaled;
    public boolean mIsSmallIcon;
    public boolean mIsUsingAppIcon;
    public final TextView mMainText;
    public final ImageView mNotiIcon;
    public final LinearLayout mNotiIconBg;
    public NotificationEffect.AnonymousClass1 mPopupListener;
    public boolean mShouldShowButton;
    public final ImageView mSmallIcon;
    public final TextView mSubText;
    public final LinearLayout mTextRootLayout;
    public final LinearLayout mToastBlurLayout;
    public final LinearLayout mToastRootLayout;
    public final Rect mTouchRect;

    public MorphView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.morph_view, (ViewGroup) this, true);
        this.mToastRootLayout = (LinearLayout) findViewById(R.id.toast_root);
        this.mToastBlurLayout = (LinearLayout) findViewById(R.id.toast_blur_root);
        this.mIconRootLayout = (LinearLayout) findViewById(R.id.toast_icon_root);
        this.mNotiIconBg = (LinearLayout) findViewById(R.id.noti_icon_bg);
        this.mNotiIcon = (ImageView) findViewById(R.id.toast_app_icon);
        this.mSmallIcon = (ImageView) findViewById(R.id.toast_small_icon);
        this.mTextRootLayout = (LinearLayout) findViewById(R.id.toast_text_layout);
        this.mMainText = (TextView) findViewById(R.id.toast_title_text);
        this.mSubText = (TextView) findViewById(R.id.toast_sub_text);
        this.mExpandButton = (ImageView) findViewById(R.id.expand_button);
        TextView textView = (TextView) findViewById(R.id.verification_code);
        this.mCodeText = textView;
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.edgelighting.effect.view.MorphView.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Class<?> cls;
                IEdgeLightingWindowCallback iEdgeLightingWindowCallback;
                MorphView morphView = MorphView.this;
                if (morphView.mIsHiding) {
                    int i = MorphView.$r8$clinit;
                    Slog.i("MorphView", " Do not copy when hiding animation");
                    return;
                }
                if (morphView.mCodeText.getText() == null) {
                    int i2 = MorphView.$r8$clinit;
                    Slog.i("MorphView", " code text is null. So can not copy : " + VerificationCodeUtils.getVerifyCode());
                    return;
                }
                Context context2 = MorphView.this.getContext();
                String charSequence = MorphView.this.mCodeText.getText().toString();
                String str = Utils.TAG;
                SemClipboardManager semClipboardManager = (SemClipboardManager) context2.getSystemService("semclipboard");
                try {
                    cls = Class.forName("com.samsung.android.emergencymode.SemEmergencyManager");
                } catch (ClassNotFoundException unused) {
                    Slog.i("com.samsung.android.emergencymode.SemEmergencyManager not found", str);
                    cls = null;
                }
                if (cls == null || !semClipboardManager.isEnabled()) {
                    ((ClipboardManager) context2.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("label", charSequence));
                } else {
                    SemTextClipData semTextClipData = new SemTextClipData();
                    semTextClipData.setText(charSequence);
                    semClipboardManager.addClip(context2, semTextClipData, (SemClipboardManager.OnAddClipResultListener) null);
                }
                Slog.i(str, "doCopyCode : copiedCode = " + charSequence);
                MorphView morphView2 = MorphView.this;
                NotificationEffect.AnonymousClass1 anonymousClass1 = morphView2.mPopupListener;
                if (anonymousClass1 != null) {
                    morphView2.mCodeText.getText().toString();
                    EdgeLightingDialog.AnonymousClass4 anonymousClass4 = NotificationEffect.this.mEdgeListener;
                    if (anonymousClass4 == null || (iEdgeLightingWindowCallback = EdgeLightingDialog.this.mWindowCallback) == null) {
                        return;
                    }
                    iEdgeLightingWindowCallback.doActionNotification();
                }
            }
        });
        this.mTouchRect = new Rect();
    }

    public final void disappear() {
        if (this.mIsHiding) {
            Slog.i("MorphView", "Morph animation is running. So ignore hide action.");
            return;
        }
        this.mIsHiding = true;
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, "alpha", 0.0f);
        ofFloat.setInterpolator(new LinearInterpolator());
        ofFloat.setDuration(200L);
        ofFloat.setStartDelay(0L);
        ofFloat.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.edgelighting.effect.view.MorphView.3
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                MorphView.this.mIsHiding = false;
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                MorphView.this.reset();
                MorphView.this.mIsHiding = false;
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
            }
        });
        ofFloat.start();
    }

    public final void hide() {
        NotificationEffect.AnonymousClass1 anonymousClass1;
        if (this.mIsHiding) {
            Slog.i("MorphView", "Morph animation is running. So ignore hide action.");
            return;
        }
        this.mIsHiding = true;
        if (!isEmptyTickerText() && (anonymousClass1 = this.mPopupListener) != null) {
            NotificationEffect.this.dismissToastPopup();
        }
        SpringAnimation springAnimation = new SpringAnimation(this, SpringAnimation.TRANSLATION_Y, -400.0f);
        springAnimation.addEndListener(new DynamicAnimation.OnAnimationEndListener() { // from class: com.android.systemui.edgelighting.effect.view.MorphView.4
            public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z, float f, float f2) {
                MorphView.this.reset();
                MorphView.this.mIsHiding = false;
            }
        });
        SpringForce springForce = new SpringForce(-400.0f);
        springForce.setStiffness(75.0f);
        springForce.setDampingRatio(0.808f);
        springAnimation.setSpring(springForce);
        postDelayed(new MorphView$$ExternalSyntheticLambda0(this, springAnimation, 0), 0L);
    }

    public final void initialize() {
        int i;
        int dimensionPixelOffset;
        double d;
        double d2;
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.toast_height);
        if (Utils.isLargeCoverFlipFolded()) {
            this.mMainText.setTextSize(1, 13.0f);
            this.mSubText.setTextSize(1, 13.0f);
            this.mCodeText.setTextSize(1, 13.0f);
        } else {
            this.mMainText.setTextSize(13.0f);
            this.mSubText.setTextSize(13.0f);
            this.mCodeText.setTextSize(13.0f);
        }
        if (this.mMainText.getText() == null || this.mMainText.getText().length() <= 0) {
            i = 0;
        } else {
            this.mMainText.measure(0, 0);
            i = this.mMainText.getMeasuredWidth();
        }
        if (this.mSubText.getText() != null && this.mSubText.getText().length() > 0) {
            this.mSubText.measure(0, 0);
            i += this.mSubText.getMeasuredWidth();
        }
        if (this.mCodeText.getVisibility() == 0 && VerificationCodeUtils.getVerifyCode() != null) {
            this.mCodeText.measure(0, 0);
            i += this.mCodeText.getMeasuredWidth();
        }
        if (this.mExpandButton.getVisibility() == 0) {
            this.mExpandButton.measure(0, 0);
            i += this.mExpandButton.getMeasuredWidth();
        }
        int dimensionPixelSize2 = i > 0 ? getResources().getDimensionPixelSize(R.dimen.toast_text_layout_end_padding) + getResources().getDimensionPixelSize(R.dimen.toast_text_layout_start_padding) + i + dimensionPixelSize : dimensionPixelSize;
        if (this.mIsUsingAppIcon) {
            int dimensionPixelOffset2 = getResources().getDimensionPixelOffset(R.dimen.toast_app_icon_additional_margin);
            dimensionPixelSize2 += dimensionPixelOffset2;
            ((LinearLayout.LayoutParams) this.mIconRootLayout.getLayoutParams()).setMarginStart(dimensionPixelOffset2);
        }
        int dimensionPixelOffset3 = (Utils.isLargeCoverFlipFolded() && ((float) ((WindowManager) getContext().getSystemService("window")).getDefaultDisplay().getRotation()) == 1.0f) ? getContext().getResources().getDimensionPixelOffset(R.dimen.cut_off_height) : 0;
        ((LinearLayout.LayoutParams) this.mIconRootLayout.getLayoutParams()).leftMargin = 0;
        setPadding(0, 0, dimensionPixelOffset3, 0);
        if (Utils.isLargeCoverFlipFolded()) {
            dimensionPixelOffset = (this.mScreenWidth - (getResources().getDimensionPixelOffset(R.dimen.morph_side_margin_cover) * 2)) - dimensionPixelOffset3;
        } else {
            if (getResources().getConfiguration().orientation == 2) {
                d = this.mScreenWidth;
                d2 = 0.6d;
            } else if (this.mScreenWidth > getResources().getDimensionPixelOffset(R.dimen.settings_edge_lighting_style_large_screen_point)) {
                d = this.mScreenWidth;
                d2 = 0.7d;
            } else {
                dimensionPixelOffset = this.mScreenWidth - (getResources().getDimensionPixelOffset(R.dimen.morph_side_margin) * 2);
            }
            dimensionPixelOffset = (int) (d * d2);
        }
        int min = Math.min(dimensionPixelOffset, dimensionPixelSize2);
        this.mMinWidth = dimensionPixelSize;
        this.mMaxWidth = min;
        updateMargin(getRootWindowInsets());
        int dimensionPixelSize3 = getResources().getDimensionPixelSize(R.dimen.toast_elevation_margin);
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.width = this.mScreenWidth;
        layoutParams.height = (dimensionPixelSize3 * 2) + dimensionPixelSize;
        setLayoutParams(layoutParams);
        ViewGroup.LayoutParams layoutParams2 = this.mToastRootLayout.getLayoutParams();
        layoutParams2.width = min;
        layoutParams2.height = dimensionPixelSize;
        this.mToastRootLayout.setLayoutParams(layoutParams2);
        this.mTextRootLayout.setPaddingRelative(getResources().getDimensionPixelSize(R.dimen.toast_text_layout_start_padding), 0, getResources().getDimensionPixelSize(R.dimen.toast_text_layout_end_padding), 0);
        LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) this.mTextRootLayout.getLayoutParams();
        layoutParams3.width = min - this.mMinWidth;
        this.mTextRootLayout.setLayoutParams(layoutParams3);
        this.mToastBlurLayout.semSetBlurInfo(new SemBlurInfo.Builder(0).setColorCurvePreset((getResources().getConfiguration().uiMode & 48) == 32 ? 122 : 113).setBackgroundCornerRadius(getResources().getDimensionPixelOffset(R.dimen.lighting_popup_round_scaled)).setBackgroundColor(getContext().getColor(R.color.blend_blur_color)).build());
        this.mToastRootLayout.setElevation(getResources().getDimensionPixelSize(R.dimen.toast_root_elevation));
        reset();
    }

    public final boolean isEmptyTickerText() {
        if (this.mMainText.getText() == null || this.mMainText.getText().length() <= 0) {
            return this.mSubText.getText() == null || this.mSubText.getText().length() <= 0;
        }
        return false;
    }

    @Override // android.view.View
    public final WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        updateMargin(windowInsets);
        return windowInsets.replaceSystemWindowInsets(windowInsets.getSystemWindowInsetLeft(), windowInsets.getSystemWindowInsetTop(), windowInsets.getSystemWindowInsetRight(), windowInsets.getSystemWindowInsetBottom());
    }

    public final void reset() {
        setTranslationY(-400.0f);
        setAlpha(0.0f);
        this.isAnimating = Boolean.FALSE;
    }

    @Override // android.view.View
    public final void setAlpha(float f) {
        super.setAlpha(f);
        LinearLayout linearLayout = this.mToastBlurLayout;
        if (linearLayout == null || linearLayout.getBackground() == null) {
            return;
        }
        this.mToastBlurLayout.getBackground().setAlpha((int) (f * 255.0f));
    }

    public final void show() {
        if (this.isAnimating.booleanValue()) {
            return;
        }
        this.isAnimating = Boolean.TRUE;
        SpringAnimation springAnimation = new SpringAnimation(this, SpringAnimation.TRANSLATION_Y, 0.0f);
        springAnimation.addEndListener(new DynamicAnimation.OnAnimationEndListener() { // from class: com.android.systemui.edgelighting.effect.view.MorphView.2
            public final void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z, float f, float f2) {
                NotificationEffect.AnonymousClass1 anonymousClass1 = MorphView.this.mPopupListener;
                if (anonymousClass1 != null) {
                    NotificationEffect.this.finishToastPopupAnimation();
                }
            }
        });
        SpringForce springForce = new SpringForce(0.0f);
        springForce.setStiffness(75.0f);
        springForce.setDampingRatio(0.808f);
        springAnimation.setSpring(springForce);
        postDelayed(new MorphView$$ExternalSyntheticLambda0(this, springAnimation, 1), 0L);
    }

    public final void showExpandButton(boolean z) {
        if (z != this.mShouldShowButton) {
            this.mExpandButton.setVisibility(z ? 0 : 8);
            this.mShouldShowButton = z;
        }
    }

    public final void updateMargin(WindowInsets windowInsets) {
        Resources resources;
        int i;
        DisplayCutout displayCutout;
        if (Utils.isLargeCoverFlipFolded()) {
            resources = getResources();
            i = R.dimen.subscreen_toast_top_margin;
        } else {
            resources = getResources();
            i = R.dimen.toast_top_margin;
        }
        int dimensionPixelOffset = resources.getDimensionPixelOffset(i);
        if (windowInsets != null && (displayCutout = windowInsets.getDisplayCutout()) != null) {
            int safeInsetTop = displayCutout.getSafeInsetTop();
            if (Utils.isLargeCoverFlipFolded()) {
                dimensionPixelOffset += safeInsetTop;
            } else if (dimensionPixelOffset <= safeInsetTop) {
                dimensionPixelOffset = safeInsetTop;
            }
        }
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
        if (marginLayoutParams != null) {
            marginLayoutParams.topMargin = dimensionPixelOffset;
        }
        Rect rect = this.mTouchRect;
        rect.top = dimensionPixelOffset;
        rect.bottom = dimensionPixelOffset + this.mMinWidth;
        int i2 = this.mScreenWidth;
        int i3 = (i2 - this.mMaxWidth) / 2;
        rect.left = i3;
        rect.right = i2 - i3;
        if (rect == null) {
            return;
        }
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.toast_root_elevation);
        rect.left -= dimensionPixelSize;
        rect.top -= dimensionPixelSize;
        rect.right += dimensionPixelSize;
        rect.bottom += dimensionPixelSize;
    }
}
