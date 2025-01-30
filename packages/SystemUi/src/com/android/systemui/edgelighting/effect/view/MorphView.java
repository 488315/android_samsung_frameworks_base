package com.android.systemui.edgelighting.effect.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Slog;
import android.view.DisplayCutout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.keyguard.SecLockIconView$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.edgelighting.effect.container.EdgeLightingDialog;
import com.android.systemui.edgelighting.effect.container.NotificationEffect;
import com.android.systemui.edgelighting.effect.interfaces.IEdgeLightingWindowCallback;
import com.android.systemui.edgelighting.effect.utils.OneSpring;
import com.android.systemui.edgelighting.effect.utils.SineIn60;
import com.android.systemui.edgelighting.effect.utils.SineInOut80;
import com.android.systemui.edgelighting.effect.utils.Utils;
import com.android.systemui.edgelighting.effect.utils.VerificationCodeUtils;
import com.samsung.android.content.clipboard.SemClipboardManager;
import com.samsung.android.content.clipboard.data.SemTextClipData;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class MorphView extends AbsToastView {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final TextView mCodeText;
    public final ImageView mExpandButton;
    public final LinearLayout mIconRootLayout;
    public boolean mIsGrayScaled;
    public boolean mIsShowAppIcon;
    public boolean mIsSupportAppIcon;
    public final TextView mMainText;
    public final ImageView mNotiIcon;
    public final LinearLayout mNotiIconBg;
    public NotificationEffect.C13151 mPopupListener;
    public boolean mShouldShowButton;
    public final ImageView mSmallIcon;
    public final TextView mSubText;
    public ValueAnimator mTextAnimator;
    public final LinearLayout mTextRootLayout;
    public final LinearLayout mToastRootLayout;
    public final Rect mTouchRect;

    public MorphView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.morph_view, (ViewGroup) this, true);
        this.mToastRootLayout = (LinearLayout) findViewById(R.id.toast_root);
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
                    Log.i("MorphView", " Do not copy when hiding animation");
                    return;
                }
                if (morphView.mCodeText.getText() == null) {
                    int i2 = MorphView.$r8$clinit;
                    Log.i("MorphView", " code text is null. So can not copy : " + VerificationCodeUtils.getVerifyCode());
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
                if ((cls != null) && semClipboardManager.isEnabled()) {
                    SemTextClipData semTextClipData = new SemTextClipData();
                    semTextClipData.setText(charSequence);
                    semClipboardManager.addClip(context2, semTextClipData, (SemClipboardManager.OnAddClipResultListener) null);
                } else {
                    ((ClipboardManager) context2.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("label", charSequence));
                }
                Slog.i(str, "doCopyCode : copiedCode = " + charSequence);
                MorphView morphView2 = MorphView.this;
                NotificationEffect.C13151 c13151 = morphView2.mPopupListener;
                if (c13151 != null) {
                    morphView2.mCodeText.getText().toString();
                    EdgeLightingDialog.C13144 c13144 = NotificationEffect.this.mEdgeListener;
                    if (c13144 == null || (iEdgeLightingWindowCallback = EdgeLightingDialog.this.mWindowCallback) == null) {
                        return;
                    }
                    iEdgeLightingWindowCallback.doActionNotification();
                }
            }
        });
        this.mTouchRect = new Rect();
    }

    public final void changeNotiText(final float f, long j, long j2) {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.mTextRootLayout, "alpha", f);
        this.mTextAnimator = ofFloat;
        ofFloat.setStartDelay(j);
        this.mTextAnimator.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.edgelighting.effect.view.MorphView.3
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                if (f == 0.0f) {
                    MorphView.this.mTextRootLayout.setVisibility(8);
                }
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                if (f == 1.0f) {
                    MorphView.this.mTextRootLayout.setVisibility(0);
                    MorphView.this.mTextRootLayout.setAlpha(0.0f);
                }
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationRepeat(Animator animator) {
            }
        });
        this.mTextAnimator.setDuration(j2);
        this.mTextAnimator.setInterpolator(new LinearInterpolator());
        this.mTextAnimator.start();
    }

    public final void changeNotificationWidth(final int i, final int i2, long j, long j2) {
        this.mTranslationXAnimatorSet = new AnimatorSet();
        final boolean z = i > i2;
        float[] fArr = new float[2];
        fArr[0] = z ? 1.0f : 1.5f;
        fArr[1] = z ? 1.5f : 1.0f;
        ValueAnimator ofFloat = ValueAnimator.ofFloat(fArr);
        ofFloat.setDuration(j2);
        ofFloat.setInterpolator(new OneSpring());
        if (!z) {
            this.mIconRootLayout.setScaleX(1.5f);
            this.mIconRootLayout.setScaleY(1.5f);
        }
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.edgelighting.effect.view.MorphView.6
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                MorphView.this.mIconRootLayout.setScaleX(floatValue);
                MorphView.this.mIconRootLayout.setScaleY(floatValue);
            }
        });
        float dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.lighting_popup_round);
        float dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.lighting_popup_round_scaled);
        float[] fArr2 = new float[2];
        fArr2[0] = z ? dimensionPixelSize : dimensionPixelSize2;
        if (z) {
            dimensionPixelSize = dimensionPixelSize2;
        }
        fArr2[1] = dimensionPixelSize;
        ValueAnimator ofFloat2 = ValueAnimator.ofFloat(fArr2);
        ofFloat2.setDuration(j2);
        ofFloat2.setInterpolator(new OneSpring());
        ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.edgelighting.effect.view.MorphView.7
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                float[] fArr3 = {floatValue, floatValue, floatValue, floatValue, floatValue, floatValue, floatValue, floatValue};
                ShapeDrawable shapeDrawable = new ShapeDrawable(new RoundRectShape(fArr3, null, fArr3));
                shapeDrawable.getPaint().setColor(MorphView.this.getResources().getColor(R.color.noti_toast_bg_color));
                MorphView.this.mToastRootLayout.setBackground(shapeDrawable);
            }
        });
        ValueAnimator ofFloat3 = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat3.setDuration(j2);
        ofFloat3.setInterpolator(new OneSpring());
        ofFloat3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.edgelighting.effect.view.MorphView.8
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                float f = ((i2 - r0) * floatValue) + i;
                if (z) {
                    floatValue = 1.0f - floatValue;
                }
                float dimensionPixelOffset = floatValue * MorphView.this.getResources().getDimensionPixelOffset(R.dimen.toast_app_icon_additional_margin);
                MorphView morphView = MorphView.this;
                if (morphView.mIsShowAppIcon) {
                    ((LinearLayout.LayoutParams) morphView.mIconRootLayout.getLayoutParams()).setMarginStart((int) dimensionPixelOffset);
                }
                MorphView.this.mToastRootLayout.setLayoutParams(new RelativeLayout.LayoutParams((int) f, MorphView.this.mMinWidth));
            }
        });
        float dimensionPixelSize3 = getResources().getDimensionPixelSize(R.dimen.toast_root_elevation);
        float[] fArr3 = new float[2];
        fArr3[0] = z ? dimensionPixelSize3 : 0.0f;
        if (z) {
            dimensionPixelSize3 = 0.0f;
        }
        fArr3[1] = dimensionPixelSize3;
        ValueAnimator ofFloat4 = ValueAnimator.ofFloat(fArr3);
        ofFloat4.setDuration(j2);
        ofFloat4.setInterpolator(new OneSpring());
        ofFloat4.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.edgelighting.effect.view.MorphView.9
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                MorphView.this.mToastRootLayout.setElevation(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        this.mTranslationXAnimatorSet.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.edgelighting.effect.view.MorphView.10
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                NotificationEffect.C13151 c13151;
                if (z || (c13151 = MorphView.this.mPopupListener) == null) {
                    return;
                }
                NotificationEffect.this.finishToastPopupAnimation();
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                NotificationEffect.C13151 c13151;
                if (z || (c13151 = MorphView.this.mPopupListener) == null) {
                    return;
                }
                NotificationEffect.this.startToastPopupAnimation();
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationRepeat(Animator animator) {
            }
        });
        this.mTranslationXAnimatorSet.playTogether(ofFloat3, ofFloat, ofFloat4);
        this.mTranslationXAnimatorSet.setStartDelay(j);
        this.mTranslationXAnimatorSet.start();
    }

    public final void disappear() {
        if (this.mIsHiding) {
            Log.i("MorphView", "Morph animation is running. So ignore hide action.");
            return;
        }
        this.mIsHiding = true;
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, "alpha", 0.0f);
        ofFloat.setInterpolator(new LinearInterpolator());
        ofFloat.setDuration(200L);
        ofFloat.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.edgelighting.effect.view.MorphView.4
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

    public final Rect getRootRect() {
        int[] iArr = new int[2];
        this.mToastRootLayout.getLocationInWindow(iArr);
        int i = iArr[0];
        int i2 = iArr[1];
        return new Rect(i, i2, this.mToastRootLayout.getWidth() + i, this.mToastRootLayout.getHeight() + i2);
    }

    public final void hide() {
        if (this.mIsHiding) {
            Log.i("MorphView", "Morph animation is running. So ignore hide action.");
            return;
        }
        this.mIsHiding = true;
        if (!isEmptyTickerText()) {
            NotificationEffect.C13151 c13151 = this.mPopupListener;
            if (c13151 != null) {
                NotificationEffect.this.dismissToastPopup();
            }
            changeNotiText(0.0f, 0L, 200L);
            changeNotificationWidth(this.mMaxWidth, this.mMinWidth, 150L, 250L);
        }
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, "translationY", -350.0f);
        ofFloat.setInterpolator(new SineIn60());
        ofFloat.setDuration(350L);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this, "alpha", 0.0f);
        ofFloat2.setInterpolator(new LinearInterpolator());
        ofFloat2.setDuration(200L);
        AnimatorSet animatorSet = new AnimatorSet();
        this.mTranslationYAnimatorSet = animatorSet;
        animatorSet.setStartDelay(400L);
        this.mTranslationYAnimatorSet.playTogether(ofFloat, ofFloat2);
        this.mTranslationYAnimatorSet.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.edgelighting.effect.view.MorphView.5
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
        this.mTranslationYAnimatorSet.start();
    }

    public final void initialize() {
        int i;
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.toast_height);
        int dimensionPixelOffset = getResources().getDimensionPixelOffset(R.dimen.morph_side_margin);
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
            i = this.mMainText.getMeasuredWidth() + 0;
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
        if (this.mIsShowAppIcon) {
            dimensionPixelSize2 += getResources().getDimensionPixelOffset(R.dimen.toast_app_icon_additional_margin);
        }
        int dimensionPixelOffset2 = (Utils.isLargeCoverFlipFolded() && ((float) ((WindowManager) getContext().getSystemService("window")).getDefaultDisplay().getRotation()) == 1.0f) ? getContext().getResources().getDimensionPixelOffset(R.dimen.cut_off_height) : 0;
        setPadding(0, 0, dimensionPixelOffset2, 0);
        int min = Math.min((this.mScreenWidth - (dimensionPixelOffset * 2)) - dimensionPixelOffset2, dimensionPixelSize2);
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
        setTranslationY(-250.0f);
        this.mTextRootLayout.setAlpha(0.0f);
        setAlpha(0.0f);
        if (this.mTranslationYAnimatorSet != null && (this.isAnimating.booleanValue() || this.mTranslationYAnimatorSet.isRunning())) {
            this.mTranslationYAnimatorSet.removeAllListeners();
            this.mTranslationYAnimatorSet.cancel();
        }
        ValueAnimator valueAnimator = this.mTextAnimator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.mTextAnimator.removeAllListeners();
            this.mTextAnimator.cancel();
        }
        AnimatorSet animatorSet = this.mTranslationXAnimatorSet;
        if (animatorSet != null && animatorSet.isRunning()) {
            this.mTranslationXAnimatorSet.removeAllListeners();
            this.mTranslationXAnimatorSet.cancel();
        }
        this.isAnimating = Boolean.FALSE;
    }

    public final void show() {
        if (this.isAnimating.booleanValue()) {
            return;
        }
        this.isAnimating = Boolean.TRUE;
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, "translationY", 10.0f);
        ofFloat.setDuration(350L);
        ofFloat.setInterpolator(new SineInOut80());
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this, "translationY", 0.0f);
        ofFloat2.setStartDelay(350L);
        ofFloat2.setDuration(100L);
        SecLockIconView$$ExternalSyntheticOutline0.m82m(0.1f, 0.0f, 0.67f, 1.0f, ofFloat2);
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(this, "alpha", 1.0f);
        ofFloat3.setStartDelay(150L);
        ofFloat3.setDuration(200L);
        ofFloat3.setInterpolator(new LinearInterpolator());
        AnimatorSet animatorSet = new AnimatorSet();
        this.mTranslationYAnimatorSet = animatorSet;
        animatorSet.setStartDelay(0L);
        this.mTranslationYAnimatorSet.playTogether(ofFloat, ofFloat2, ofFloat3);
        this.mTranslationYAnimatorSet.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.edgelighting.effect.view.MorphView.2
            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                int i = MorphView.this.mMinWidth;
                MorphView.this.mToastRootLayout.setLayoutParams(new RelativeLayout.LayoutParams(i, i));
                if (!MorphView.this.isEmptyTickerText()) {
                    MorphView.this.mTextRootLayout.setVisibility(8);
                    MorphView morphView = MorphView.this;
                    morphView.changeNotificationWidth(morphView.mMinWidth, morphView.mMaxWidth, 450L, 300L);
                    MorphView.this.changeNotiText(1.0f, 600L, 250L);
                }
                MorphView.this.mToastRootLayout.setElevation(0.0f);
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationRepeat(Animator animator) {
            }
        });
        this.mTranslationYAnimatorSet.setDuration(400L);
        this.mTranslationYAnimatorSet.start();
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
