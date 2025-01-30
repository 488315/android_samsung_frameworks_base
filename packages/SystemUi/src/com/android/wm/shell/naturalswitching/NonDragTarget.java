package com.android.wm.shell.naturalswitching;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.RectEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.draganddrop.DropTargetView;
import com.android.systemui.R;
import com.samsung.android.graphics.SemGfxImageFilter;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.multiwindow.SurfaceFreezerSnapshot;
import com.samsung.android.util.InterpolatorUtils;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class NonDragTarget extends FrameLayout {
    public boolean mAnimatingExit;
    public ValueAnimator mAnimator;
    public final Rect mBaseBounds;
    public ValueAnimator mBlurAnimator;
    public ImageView mBlurView;
    public final Rect mCurrentOutlineInsets;
    public final PointF mDownScale;
    public DropTargetView mDropTargetView;
    public final Rect mEndBounds;
    public boolean mHasProtectedContent;
    public final Rect mInitialOutlineInsets;
    public boolean mInsetsInitialized;
    public NonDragTargetView mNonDragTargetView;
    public int mNsWindowingMode;
    public final Rect mOriginBounds;
    public ValueAnimator mOutlineInsetsAnimator;
    public final C40141 mOutlineProvider;
    public final ArrayList mPolygon;
    public int mStagePosition;
    public final Rect mTargetOutlineInsets;
    public int mTaskId;
    public final Rect mTmpInsetsRect;
    public final Rect mTmpRect;
    public ValueAnimator mTransitAnimator;
    public ImageView mView;
    public static final RectEvaluator RECT_EVALUATOR = new RectEvaluator(new Rect());
    public static final float[] BLUR_PRESET = {250.0f, 0.0f, 8.0f, 29.4f, 255.0f, 0.0f, 229.5f};
    public static final float[] DARK_BLUR_PRESET = {250.0f, 0.0f, 8.0f, 29.4f, 255.0f, 0.0f, 216.8f};

    /* renamed from: -$$Nest$mupdateImageMatrix, reason: not valid java name */
    public static void m2751$$Nest$mupdateImageMatrix(NonDragTarget nonDragTarget, ImageView imageView) {
        nonDragTarget.getClass();
        Matrix imageMatrix = imageView.getImageMatrix();
        int i = nonDragTarget.mCurrentOutlineInsets.top - nonDragTarget.mInitialOutlineInsets.top;
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) nonDragTarget.mView.getLayoutParams();
        Rect rect = nonDragTarget.mTmpRect;
        int i2 = marginLayoutParams.leftMargin;
        int i3 = marginLayoutParams.topMargin;
        rect.set(i2, i3, marginLayoutParams.width + i2, marginLayoutParams.height + i3);
        if (!nonDragTarget.mTmpRect.isEmpty() && !nonDragTarget.mOriginBounds.isEmpty()) {
            imageMatrix.setScale(nonDragTarget.mTmpRect.width() / nonDragTarget.mOriginBounds.width(), nonDragTarget.mTmpRect.height() / nonDragTarget.mOriginBounds.height());
        }
        imageMatrix.postTranslate(0.0f, i);
        imageView.setImageMatrix(imageMatrix);
        imageView.invalidate();
    }

    /* JADX WARN: Type inference failed for: r2v7, types: [com.android.wm.shell.naturalswitching.NonDragTarget$1] */
    public NonDragTarget(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mStagePosition = 0;
        this.mTmpRect = new Rect();
        this.mEndBounds = new Rect();
        this.mOriginBounds = new Rect();
        this.mBaseBounds = new Rect();
        this.mPolygon = new ArrayList();
        this.mDownScale = new PointF(1.0f, 1.0f);
        this.mInsetsInitialized = false;
        this.mHasProtectedContent = false;
        this.mInitialOutlineInsets = new Rect();
        this.mCurrentOutlineInsets = new Rect();
        this.mTargetOutlineInsets = new Rect();
        this.mTmpInsetsRect = new Rect();
        this.mDropTargetView = null;
        this.mOutlineProvider = new ViewOutlineProvider() { // from class: com.android.wm.shell.naturalswitching.NonDragTarget.1
            @Override // android.view.ViewOutlineProvider
            public final void getOutline(View view, Outline outline) {
                Rect rect = NonDragTarget.this.mCurrentOutlineInsets;
                outline.setRoundRect(rect.left, rect.top, view.getWidth() - NonDragTarget.this.mCurrentOutlineInsets.right, view.getHeight() - NonDragTarget.this.mCurrentOutlineInsets.bottom, r7.mNonDragTargetView.mCornerRadius);
            }
        };
    }

    public final void animate(Rect rect) {
        ValueAnimator valueAnimator = this.mAnimator;
        if (valueAnimator != null) {
            valueAnimator.end();
        }
        ValueAnimator valueAnimator2 = this.mBlurAnimator;
        if (valueAnimator2 != null) {
            valueAnimator2.end();
        }
        final Rect rect2 = new Rect();
        getCurrentLayoutBounds(rect2);
        this.mEndBounds.set(rect);
        if (rect2.equals(rect)) {
            return;
        }
        final ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mView.getLayoutParams();
        final ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) this.mBlurView.getLayoutParams();
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.mAnimator = ofFloat;
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.naturalswitching.NonDragTarget.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator3) {
                Rect evaluate = NonDragTarget.RECT_EVALUATOR.evaluate(((Float) valueAnimator3.getAnimatedValue()).floatValue(), rect2, NonDragTarget.this.mEndBounds);
                ViewGroup.MarginLayoutParams marginLayoutParams3 = marginLayoutParams2;
                ViewGroup.MarginLayoutParams marginLayoutParams4 = marginLayoutParams;
                int width = evaluate.width();
                marginLayoutParams4.width = width;
                marginLayoutParams3.width = width;
                ViewGroup.MarginLayoutParams marginLayoutParams5 = marginLayoutParams2;
                ViewGroup.MarginLayoutParams marginLayoutParams6 = marginLayoutParams;
                int height = evaluate.height();
                marginLayoutParams6.height = height;
                marginLayoutParams5.height = height;
                ViewGroup.MarginLayoutParams marginLayoutParams7 = marginLayoutParams2;
                ViewGroup.MarginLayoutParams marginLayoutParams8 = marginLayoutParams;
                int i = evaluate.left;
                marginLayoutParams8.leftMargin = i;
                marginLayoutParams7.leftMargin = i;
                int i2 = evaluate.top;
                marginLayoutParams8.topMargin = i2;
                marginLayoutParams7.topMargin = i2;
                NonDragTarget.this.mView.setLayoutParams(marginLayoutParams8);
                NonDragTarget.this.mBlurView.setLayoutParams(marginLayoutParams2);
                NonDragTarget nonDragTarget = NonDragTarget.this;
                NonDragTarget.m2751$$Nest$mupdateImageMatrix(nonDragTarget, nonDragTarget.mView);
                NonDragTarget nonDragTarget2 = NonDragTarget.this;
                NonDragTarget.m2751$$Nest$mupdateImageMatrix(nonDragTarget2, nonDragTarget2.mBlurView);
            }
        });
        this.mAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.naturalswitching.NonDragTarget.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                NonDragTarget.this.mAnimator = null;
            }
        });
        this.mAnimator.setInterpolator(InterpolatorUtils.SINE_OUT_60);
        this.mAnimator.setDuration(350L);
        this.mAnimator.start();
        if (!this.mHasProtectedContent) {
            boolean z = true;
            boolean z2 = this.mBlurView.getAlpha() > 0.0f;
            if (this.mEndBounds.width() == this.mOriginBounds.width() && this.mEndBounds.height() == this.mOriginBounds.height()) {
                z = false;
            }
            if (z2 != z) {
                ValueAnimator ofFloat2 = z ? ValueAnimator.ofFloat(0.0f, 1.0f) : ValueAnimator.ofFloat(1.0f, 0.0f);
                this.mBlurAnimator = ofFloat2;
                ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.naturalswitching.NonDragTarget.4
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator3) {
                        NonDragTarget.this.mBlurView.setAlpha(((Float) valueAnimator3.getAnimatedValue()).floatValue());
                    }
                });
                this.mBlurAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.naturalswitching.NonDragTarget.5
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        NonDragTarget.this.mBlurAnimator = null;
                    }
                });
                this.mBlurAnimator.setInterpolator(new LinearInterpolator());
                this.mBlurAnimator.setDuration(100L);
                if (!z) {
                    this.mBlurAnimator.setStartDelay(250L);
                }
                this.mBlurAnimator.start();
            }
        }
        startOutlineInsetsAnimationIfNeeded();
    }

    public final void getCurrentLayoutBounds(Rect rect) {
        rect.set(this.mView.getLeft(), this.mView.getTop(), this.mView.getRight(), this.mView.getBottom());
    }

    public final void init(NonDragTargetView nonDragTargetView, int i, int i2, Rect rect, int i3) {
        this.mNonDragTargetView = nonDragTargetView;
        this.mTaskId = i;
        this.mNsWindowingMode = i2;
        this.mBaseBounds.set(rect);
        this.mOriginBounds.set(rect);
        if (!this.mOriginBounds.isEmpty()) {
            int width = this.mOriginBounds.width();
            int height = this.mOriginBounds.height();
            PointF pointF = this.mDownScale;
            int i4 = this.mNonDragTargetView.mScaleDeltaSize;
            pointF.x = (width - i4) / width;
            pointF.y = (height - i4) / height;
        }
        this.mStagePosition = i3;
    }

    public final void initForTaskOnly(NonDragTargetView nonDragTargetView, Rect rect, int i) {
        this.mNonDragTargetView = nonDragTargetView;
        this.mNsWindowingMode = i;
        int width = rect.width();
        int height = rect.height();
        int dimensionPixelSize = ((FrameLayout) this).mContext.getResources().getDimensionPixelSize(R.dimen.ns_drop_freeform_width);
        int dimensionPixelSize2 = ((FrameLayout) this).mContext.getResources().getDimensionPixelSize(R.dimen.ns_drop_freeform_height);
        int i2 = (width - dimensionPixelSize) / 2;
        int i3 = (height - dimensionPixelSize2) / 2;
        Rect rect2 = new Rect();
        rect2.set(i2, i3, dimensionPixelSize + i2, dimensionPixelSize2 + i3);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mView.getLayoutParams();
        marginLayoutParams.width = rect2.width();
        marginLayoutParams.height = rect2.height();
        marginLayoutParams.leftMargin = rect2.left;
        marginLayoutParams.topMargin = rect2.top;
        setDropTargetView();
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mView = (ImageView) findViewById(R.id.non_target);
        this.mBlurView = (ImageView) findViewById(R.id.non_target_blur);
    }

    public final void setDropTargetView() {
        DropTargetView dropTargetView = (DropTargetView) LayoutInflater.from(((FrameLayout) this).mContext).inflate(R.layout.dnd_drop_target_view, (ViewGroup) null);
        this.mDropTargetView = dropTargetView;
        Rect rect = this.mBaseBounds;
        dropTargetView.mText.setText(R.string.drop_here_to_open);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-2, -2);
        dropTargetView.setX(rect.left);
        dropTargetView.setY(rect.top);
        dropTargetView.setLayoutParams(layoutParams);
        ViewGroup.LayoutParams layoutParams2 = dropTargetView.mView.getLayoutParams();
        layoutParams2.width = rect.width();
        layoutParams2.height = rect.height();
        dropTargetView.mView.setBackgroundResource(dropTargetView.getBackgroundResourceId());
        ViewGroup.LayoutParams layoutParams3 = dropTargetView.mPartialBlurView.getLayoutParams();
        layoutParams3.width = rect.width();
        layoutParams3.height = rect.height();
        dropTargetView.showBlurEffect();
        this.mDropTargetView.setClipToOutline(true);
        this.mDropTargetView.setOutlineProvider(this.mOutlineProvider);
        addView(this.mDropTargetView);
    }

    public final void setThumbnail() {
        this.mView.setClipToOutline(true);
        this.mView.setOutlineProvider(this.mOutlineProvider);
        this.mBlurView.setClipToOutline(true);
        this.mBlurView.setOutlineProvider(this.mOutlineProvider);
        this.mView.setVisibility(0);
        this.mBlurView.setVisibility(0);
        SurfaceFreezerSnapshot surfaceFreezerSnapshot = MultiWindowManager.getInstance().getSurfaceFreezerSnapshot(this.mTaskId);
        if (surfaceFreezerSnapshot != null) {
            this.mHasProtectedContent = surfaceFreezerSnapshot.hasProtectedContent();
        }
        if (this.mHasProtectedContent) {
            this.mView.setBackgroundColor(getResources().getColor(R.color.protected_content_bg_color));
            this.mView.setImageDrawable(getResources().getDrawable(R.drawable.mw_splitview_ic_previewlock_mtrl));
            this.mView.getDrawable().setAlpha(76);
            return;
        }
        if (surfaceFreezerSnapshot == null || surfaceFreezerSnapshot.getSnapshotBitmap() == null) {
            return;
        }
        Bitmap snapshotBitmap = surfaceFreezerSnapshot.getSnapshotBitmap();
        Context context = ((FrameLayout) this).mContext;
        SemGfxImageFilter semGfxImageFilter = new SemGfxImageFilter();
        float[] fArr = (context.getResources().getConfiguration().uiMode & 32) != 0 ? DARK_BLUR_PRESET : BLUR_PRESET;
        semGfxImageFilter.setBlurRadius(fArr[0]);
        semGfxImageFilter.setProportionalSaturation(fArr[1]);
        semGfxImageFilter.setCurveLevel(fArr[2]);
        semGfxImageFilter.setCurveMinX(fArr[3]);
        semGfxImageFilter.setCurveMaxX(fArr[4]);
        semGfxImageFilter.setCurveMinY(fArr[5]);
        semGfxImageFilter.setCurveMaxY(fArr[6]);
        Bitmap applyToBitmap = semGfxImageFilter.applyToBitmap(snapshotBitmap);
        this.mView.setImageBitmap(snapshotBitmap);
        ImageView imageView = this.mBlurView;
        if (applyToBitmap != null) {
            snapshotBitmap = applyToBitmap;
        }
        imageView.setImageBitmap(snapshotBitmap);
    }

    public final void startOutlineInsetsAnimationIfNeeded() {
        int i;
        boolean z = true;
        final boolean z2 = !this.mInsetsInitialized;
        this.mTmpInsetsRect.setEmpty();
        DisplayLayout displayLayout = this.mNonDragTargetView.mTaskVisibility.mDisplayLayout;
        boolean z3 = !(displayLayout.mWidth > displayLayout.mHeight);
        Rect rect = this.mEndBounds.isEmpty() ? this.mOriginBounds : this.mEndBounds;
        Rect rect2 = this.mNonDragTargetView.mStableRect;
        int i2 = rect.left;
        int i3 = rect2.left;
        if (i2 < i3) {
            this.mTmpInsetsRect.left = i3 - i2;
        }
        int i4 = rect.top;
        int i5 = rect2.top;
        if (i4 < i5 && z3) {
            this.mTmpInsetsRect.top = i5 - i4;
        }
        int i6 = rect.right;
        int i7 = rect2.right;
        if (i6 > i7) {
            this.mTmpInsetsRect.right = i6 - i7;
        }
        int i8 = rect.bottom;
        int i9 = rect2.bottom;
        if (i8 > i9) {
            Rect rect3 = this.mTmpInsetsRect;
            int i10 = i8 - i9;
            rect3.bottom = i10;
            if (z3 && (i = this.mInitialOutlineInsets.top) > 0) {
                rect3.bottom = i10 - i;
            }
        }
        if (!this.mInsetsInitialized) {
            this.mInsetsInitialized = true;
            this.mInitialOutlineInsets.set(this.mTmpInsetsRect);
        }
        if (this.mTargetOutlineInsets.equals(this.mTmpInsetsRect)) {
            z = false;
        } else {
            this.mTargetOutlineInsets.set(this.mTmpInsetsRect);
        }
        if (z) {
            ValueAnimator valueAnimator = this.mOutlineInsetsAnimator;
            if (valueAnimator != null) {
                valueAnimator.end();
            }
            final Rect rect4 = new Rect(this.mCurrentOutlineInsets);
            final Rect rect5 = new Rect(this.mTargetOutlineInsets);
            ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            this.mOutlineInsetsAnimator = ofFloat;
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.naturalswitching.NonDragTarget.6
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    NonDragTarget.this.mCurrentOutlineInsets.set(NonDragTarget.RECT_EVALUATOR.evaluate(((Float) valueAnimator2.getAnimatedValue()).floatValue(), rect4, rect5));
                    NonDragTarget.this.mView.invalidateOutline();
                    NonDragTarget.this.mBlurView.invalidateOutline();
                    if (z2) {
                        return;
                    }
                    NonDragTarget nonDragTarget = NonDragTarget.this;
                    NonDragTarget.m2751$$Nest$mupdateImageMatrix(nonDragTarget, nonDragTarget.mView);
                    NonDragTarget nonDragTarget2 = NonDragTarget.this;
                    NonDragTarget.m2751$$Nest$mupdateImageMatrix(nonDragTarget2, nonDragTarget2.mBlurView);
                }
            });
            this.mOutlineInsetsAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.naturalswitching.NonDragTarget.7
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    NonDragTarget.this.mOutlineInsetsAnimator = null;
                }
            });
            this.mOutlineInsetsAnimator.setInterpolator(InterpolatorUtils.SINE_OUT_60);
            this.mOutlineInsetsAnimator.setDuration(150L);
            this.mOutlineInsetsAnimator.start();
        }
    }

    @Override // android.view.View
    public final String toString() {
        StringBuilder sb = new StringBuilder("NonDragTarget{Mode=");
        sb.append(this.mNsWindowingMode);
        sb.append(", mBaseBounds=");
        sb.append(this.mBaseBounds);
        sb.append(", hasDropTarget=");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m6m(sb, this.mDropTargetView != null, "}");
    }

    public final void init(NonDragTargetView nonDragTargetView, Rect rect, ArrayList arrayList, int i) {
        init(nonDragTargetView, 0, 13, rect, i);
        this.mPolygon.addAll(arrayList);
    }
}
