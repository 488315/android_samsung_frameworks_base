package com.android.systemui.clipboardoverlay;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Insets;
import android.graphics.Rect;
import android.graphics.drawable.Icon;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.MathUtils;
import android.view.DisplayCutout;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.screenshot.DraggableConstraintLayout;
import com.android.systemui.screenshot.ui.binder.ActionButtonViewBinder;
import com.android.systemui.screenshot.ui.viewmodel.ActionButtonAppearance;
import com.android.systemui.screenshot.ui.viewmodel.ActionButtonViewModel;
import java.util.ArrayList;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class ClipboardOverlayView extends DraggableConstraintLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AccessibilityManager mAccessibilityManager;
    public final ActionButtonViewBinder mActionButtonViewBinder;
    public final ArrayList mActionChips;
    public LinearLayout mActionContainer;
    public View mActionContainerBackground;
    public ClipboardOverlayCallbacks mClipboardCallbacks;
    public View mClipboardPreview;
    public View mDismissButton;
    public final DisplayMetrics mDisplayMetrics;
    public TextView mHiddenPreview;
    public ImageView mImagePreview;
    public View mIndicationContainer;
    public LinearLayout mMinimizedPreview;
    public View mPreviewBorder;
    public View mRemoteCopyChip;
    public View mShareChip;
    public TextView mTextPreview;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface ClipboardOverlayCallbacks extends DraggableConstraintLayout.SwipeDismissCallbacks {
        void onDismissButtonTapped();

        void onMinimizedViewTapped();

        void onPreviewTapped();

        void onRemoteCopyButtonTapped();

        void onShareButtonTapped();
    }

    public ClipboardOverlayView(Context context) {
        this(context, null);
    }

    public final Animator getExitAnimation() {
        LinearInterpolator linearInterpolator = new LinearInterpolator();
        PathInterpolator pathInterpolator = new PathInterpolator(0.3f, 0.0f, 1.0f, 1.0f);
        AnimatorSet animatorSet = new AnimatorSet();
        final int i = 2;
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat.setInterpolator(linearInterpolator);
        ofFloat.setDuration(100L);
        final int i2 = 0;
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(this) { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayView$$ExternalSyntheticLambda1
            public final /* synthetic */ ClipboardOverlayView f$0;

            {
                this.f$0 = this;
            }

            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                int i3 = i2;
                ClipboardOverlayView clipboardOverlayView = this.f$0;
                switch (i3) {
                    case 0:
                        int i4 = ClipboardOverlayView.$r8$clinit;
                        clipboardOverlayView.getClass();
                        clipboardOverlayView.setAlpha(1.0f - valueAnimator.getAnimatedFraction());
                        break;
                    case 1:
                        int i5 = ClipboardOverlayView.$r8$clinit;
                        clipboardOverlayView.getClass();
                        float lerp = MathUtils.lerp(1.0f, 0.9f, valueAnimator.getAnimatedFraction());
                        clipboardOverlayView.mMinimizedPreview.setScaleX(lerp);
                        clipboardOverlayView.mMinimizedPreview.setScaleY(lerp);
                        clipboardOverlayView.mClipboardPreview.setScaleX(lerp);
                        clipboardOverlayView.mClipboardPreview.setScaleY(lerp);
                        clipboardOverlayView.mPreviewBorder.setScaleX(lerp);
                        clipboardOverlayView.mPreviewBorder.setScaleY(lerp);
                        float x = clipboardOverlayView.mClipboardPreview.getX() + (clipboardOverlayView.mClipboardPreview.getWidth() / 2.0f);
                        View view = clipboardOverlayView.mActionContainerBackground;
                        view.setPivotX(x - view.getX());
                        LinearLayout linearLayout = clipboardOverlayView.mActionContainer;
                        linearLayout.setPivotX(x - ((View) linearLayout.getParent()).getX());
                        float lerp2 = MathUtils.lerp(1.0f, 0.8f, valueAnimator.getAnimatedFraction());
                        float lerp3 = MathUtils.lerp(1.0f, 0.9f, valueAnimator.getAnimatedFraction());
                        clipboardOverlayView.mActionContainer.setScaleX(lerp2);
                        clipboardOverlayView.mActionContainer.setScaleY(lerp3);
                        clipboardOverlayView.mActionContainerBackground.setScaleX(lerp2);
                        clipboardOverlayView.mActionContainerBackground.setScaleY(lerp3);
                        break;
                    default:
                        int i6 = ClipboardOverlayView.$r8$clinit;
                        clipboardOverlayView.getClass();
                        float animatedFraction = 1.0f - valueAnimator.getAnimatedFraction();
                        clipboardOverlayView.mMinimizedPreview.setAlpha(animatedFraction);
                        clipboardOverlayView.mClipboardPreview.setAlpha(animatedFraction);
                        clipboardOverlayView.mPreviewBorder.setAlpha(animatedFraction);
                        clipboardOverlayView.mDismissButton.setAlpha(animatedFraction);
                        clipboardOverlayView.mActionContainer.setAlpha(animatedFraction);
                        break;
                }
            }
        });
        ValueAnimator ofFloat2 = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat2.setInterpolator(pathInterpolator);
        ofFloat2.setDuration(250L);
        final int i3 = 1;
        ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(this) { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayView$$ExternalSyntheticLambda1
            public final /* synthetic */ ClipboardOverlayView f$0;

            {
                this.f$0 = this;
            }

            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                int i32 = i3;
                ClipboardOverlayView clipboardOverlayView = this.f$0;
                switch (i32) {
                    case 0:
                        int i4 = ClipboardOverlayView.$r8$clinit;
                        clipboardOverlayView.getClass();
                        clipboardOverlayView.setAlpha(1.0f - valueAnimator.getAnimatedFraction());
                        break;
                    case 1:
                        int i5 = ClipboardOverlayView.$r8$clinit;
                        clipboardOverlayView.getClass();
                        float lerp = MathUtils.lerp(1.0f, 0.9f, valueAnimator.getAnimatedFraction());
                        clipboardOverlayView.mMinimizedPreview.setScaleX(lerp);
                        clipboardOverlayView.mMinimizedPreview.setScaleY(lerp);
                        clipboardOverlayView.mClipboardPreview.setScaleX(lerp);
                        clipboardOverlayView.mClipboardPreview.setScaleY(lerp);
                        clipboardOverlayView.mPreviewBorder.setScaleX(lerp);
                        clipboardOverlayView.mPreviewBorder.setScaleY(lerp);
                        float x = clipboardOverlayView.mClipboardPreview.getX() + (clipboardOverlayView.mClipboardPreview.getWidth() / 2.0f);
                        View view = clipboardOverlayView.mActionContainerBackground;
                        view.setPivotX(x - view.getX());
                        LinearLayout linearLayout = clipboardOverlayView.mActionContainer;
                        linearLayout.setPivotX(x - ((View) linearLayout.getParent()).getX());
                        float lerp2 = MathUtils.lerp(1.0f, 0.8f, valueAnimator.getAnimatedFraction());
                        float lerp3 = MathUtils.lerp(1.0f, 0.9f, valueAnimator.getAnimatedFraction());
                        clipboardOverlayView.mActionContainer.setScaleX(lerp2);
                        clipboardOverlayView.mActionContainer.setScaleY(lerp3);
                        clipboardOverlayView.mActionContainerBackground.setScaleX(lerp2);
                        clipboardOverlayView.mActionContainerBackground.setScaleY(lerp3);
                        break;
                    default:
                        int i6 = ClipboardOverlayView.$r8$clinit;
                        clipboardOverlayView.getClass();
                        float animatedFraction = 1.0f - valueAnimator.getAnimatedFraction();
                        clipboardOverlayView.mMinimizedPreview.setAlpha(animatedFraction);
                        clipboardOverlayView.mClipboardPreview.setAlpha(animatedFraction);
                        clipboardOverlayView.mPreviewBorder.setAlpha(animatedFraction);
                        clipboardOverlayView.mDismissButton.setAlpha(animatedFraction);
                        clipboardOverlayView.mActionContainer.setAlpha(animatedFraction);
                        break;
                }
            }
        });
        ValueAnimator ofFloat3 = ValueAnimator.ofFloat(0.0f, 1.0f);
        ofFloat3.setInterpolator(linearInterpolator);
        ofFloat3.setDuration(166L);
        ofFloat3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(this) { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayView$$ExternalSyntheticLambda1
            public final /* synthetic */ ClipboardOverlayView f$0;

            {
                this.f$0 = this;
            }

            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                int i32 = i;
                ClipboardOverlayView clipboardOverlayView = this.f$0;
                switch (i32) {
                    case 0:
                        int i4 = ClipboardOverlayView.$r8$clinit;
                        clipboardOverlayView.getClass();
                        clipboardOverlayView.setAlpha(1.0f - valueAnimator.getAnimatedFraction());
                        break;
                    case 1:
                        int i5 = ClipboardOverlayView.$r8$clinit;
                        clipboardOverlayView.getClass();
                        float lerp = MathUtils.lerp(1.0f, 0.9f, valueAnimator.getAnimatedFraction());
                        clipboardOverlayView.mMinimizedPreview.setScaleX(lerp);
                        clipboardOverlayView.mMinimizedPreview.setScaleY(lerp);
                        clipboardOverlayView.mClipboardPreview.setScaleX(lerp);
                        clipboardOverlayView.mClipboardPreview.setScaleY(lerp);
                        clipboardOverlayView.mPreviewBorder.setScaleX(lerp);
                        clipboardOverlayView.mPreviewBorder.setScaleY(lerp);
                        float x = clipboardOverlayView.mClipboardPreview.getX() + (clipboardOverlayView.mClipboardPreview.getWidth() / 2.0f);
                        View view = clipboardOverlayView.mActionContainerBackground;
                        view.setPivotX(x - view.getX());
                        LinearLayout linearLayout = clipboardOverlayView.mActionContainer;
                        linearLayout.setPivotX(x - ((View) linearLayout.getParent()).getX());
                        float lerp2 = MathUtils.lerp(1.0f, 0.8f, valueAnimator.getAnimatedFraction());
                        float lerp3 = MathUtils.lerp(1.0f, 0.9f, valueAnimator.getAnimatedFraction());
                        clipboardOverlayView.mActionContainer.setScaleX(lerp2);
                        clipboardOverlayView.mActionContainer.setScaleY(lerp3);
                        clipboardOverlayView.mActionContainerBackground.setScaleX(lerp2);
                        clipboardOverlayView.mActionContainerBackground.setScaleY(lerp3);
                        break;
                    default:
                        int i6 = ClipboardOverlayView.$r8$clinit;
                        clipboardOverlayView.getClass();
                        float animatedFraction = 1.0f - valueAnimator.getAnimatedFraction();
                        clipboardOverlayView.mMinimizedPreview.setAlpha(animatedFraction);
                        clipboardOverlayView.mClipboardPreview.setAlpha(animatedFraction);
                        clipboardOverlayView.mPreviewBorder.setAlpha(animatedFraction);
                        clipboardOverlayView.mDismissButton.setAlpha(animatedFraction);
                        clipboardOverlayView.mActionContainer.setAlpha(animatedFraction);
                        break;
                }
            }
        });
        animatorSet.play(ofFloat3).with(ofFloat2);
        animatorSet.play(ofFloat).after(150L).after(ofFloat3);
        return animatorSet;
    }

    @Override // com.android.systemui.screenshot.DraggableConstraintLayout, android.view.View
    public final void onFinishInflate() {
        this.mActionContainerBackground = requireViewById(R.id.actions_container_background);
        this.mActionContainer = (LinearLayout) requireViewById(R.id.actions);
        this.mClipboardPreview = requireViewById(R.id.clipboard_preview);
        this.mPreviewBorder = requireViewById(R.id.preview_border);
        this.mImagePreview = (ImageView) requireViewById(R.id.image_preview);
        this.mTextPreview = (TextView) requireViewById(R.id.text_preview);
        this.mHiddenPreview = (TextView) requireViewById(R.id.hidden_preview);
        this.mMinimizedPreview = (LinearLayout) requireViewById(R.id.minimized_preview);
        this.mShareChip = requireViewById(R.id.share_chip);
        this.mRemoteCopyChip = requireViewById(R.id.remote_copy_chip);
        this.mDismissButton = requireViewById(R.id.dismiss_button);
        View requireViewById = requireViewById(R.id.indication_container);
        this.mIndicationContainer = requireViewById;
        ActionButtonViewBinder actionButtonViewBinder = this.mActionButtonViewBinder;
        View view = this.mRemoteCopyChip;
        ActionButtonViewModel.Companion companion = ActionButtonViewModel.Companion;
        ActionButtonAppearance actionButtonAppearance = new ActionButtonAppearance(Icon.createWithResource(((ViewGroup) this).mContext, R.drawable.ic_baseline_devices_24).loadDrawable(((ViewGroup) this).mContext), null, ((ViewGroup) this).mContext.getString(R.string.clipboard_send_nearby_description), true);
        Function0 function0 = new Function0() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayView.1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ClipboardOverlayCallbacks clipboardOverlayCallbacks = ClipboardOverlayView.this.mClipboardCallbacks;
                if (clipboardOverlayCallbacks == null) {
                    return null;
                }
                clipboardOverlayCallbacks.onRemoteCopyButtonTapped();
                return null;
            }
        };
        companion.getClass();
        ActionButtonViewModel withNextId = ActionButtonViewModel.Companion.withNextId(actionButtonAppearance, function0);
        actionButtonViewBinder.getClass();
        ActionButtonViewBinder.bind(view, withNextId);
        ActionButtonViewBinder actionButtonViewBinder2 = this.mActionButtonViewBinder;
        View view2 = this.mShareChip;
        ActionButtonViewModel withNextId2 = ActionButtonViewModel.Companion.withNextId(new ActionButtonAppearance(Icon.createWithResource(((ViewGroup) this).mContext, R.drawable.ic_screenshot_share).loadDrawable(((ViewGroup) this).mContext), null, ((ViewGroup) this).mContext.getString(17043144), true), new Function0() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayView.2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ClipboardOverlayCallbacks clipboardOverlayCallbacks = ClipboardOverlayView.this.mClipboardCallbacks;
                if (clipboardOverlayCallbacks == null) {
                    return null;
                }
                clipboardOverlayCallbacks.onShareButtonTapped();
                return null;
            }
        });
        actionButtonViewBinder2.getClass();
        ActionButtonViewBinder.bind(view2, withNextId2);
        this.mTextPreview.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: com.android.systemui.clipboardoverlay.ClipboardOverlayView$$ExternalSyntheticLambda0
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public final boolean onPreDraw() {
                ClipboardOverlayView clipboardOverlayView = ClipboardOverlayView.this;
                int height = clipboardOverlayView.mTextPreview.getHeight() - (clipboardOverlayView.mTextPreview.getPaddingBottom() + clipboardOverlayView.mTextPreview.getPaddingTop());
                TextView textView = clipboardOverlayView.mTextPreview;
                textView.setMaxLines(Math.max(height / textView.getLineHeight(), 1));
                return true;
            }
        });
        super.onFinishInflate();
    }

    public final void setInsets(WindowInsets windowInsets, int i) {
        Rect rect;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) getLayoutParams();
        if (layoutParams == null) {
            return;
        }
        DisplayCutout displayCutout = windowInsets.getDisplayCutout();
        Insets insets = windowInsets.getInsets(WindowInsets.Type.navigationBars());
        Insets insets2 = windowInsets.getInsets(WindowInsets.Type.ime());
        if (displayCutout == null) {
            rect = new Rect(0, 0, 0, Math.max(insets2.bottom, insets.bottom));
        } else {
            Insets waterfallInsets = displayCutout.getWaterfallInsets();
            rect = i == 1 ? new Rect(waterfallInsets.left, Math.max(displayCutout.getSafeInsetTop(), waterfallInsets.top), waterfallInsets.right, Math.max(insets2.bottom, Math.max(displayCutout.getSafeInsetBottom(), Math.max(insets.bottom, waterfallInsets.bottom)))) : new Rect(waterfallInsets.left, waterfallInsets.top, waterfallInsets.right, Math.max(insets2.bottom, Math.max(insets.bottom, waterfallInsets.bottom)));
        }
        layoutParams.setMargins(rect.left, rect.top, rect.right, rect.bottom);
        setLayoutParams(layoutParams);
        requestLayout();
    }

    public final void setMinimized(boolean z) {
        if (!z) {
            this.mMinimizedPreview.setVisibility(8);
            this.mClipboardPreview.setVisibility(0);
            this.mPreviewBorder.setVisibility(0);
            this.mActionContainer.setVisibility(0);
            return;
        }
        this.mMinimizedPreview.setVisibility(0);
        this.mClipboardPreview.setVisibility(8);
        this.mPreviewBorder.setVisibility(8);
        this.mActionContainer.setVisibility(8);
        this.mActionContainerBackground.setVisibility(8);
    }

    public ClipboardOverlayView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ClipboardOverlayView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mActionChips = new ArrayList();
        this.mActionButtonViewBinder = new ActionButtonViewBinder();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.mDisplayMetrics = displayMetrics;
        ((ViewGroup) this).mContext.getDisplay().getRealMetrics(displayMetrics);
        this.mAccessibilityManager = AccessibilityManager.getInstance(((ViewGroup) this).mContext);
    }
}
