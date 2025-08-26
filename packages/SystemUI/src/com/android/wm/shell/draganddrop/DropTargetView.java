package com.android.wm.shell.draganddrop;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Debug;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SemBlurInfo;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.naturalswitching.NonDragTarget;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.util.InterpolatorUtils;
import com.samsung.android.view.SemWindowManager;

/* loaded from: classes3.dex */
public class DropTargetView extends FrameLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public ShellExecutor mBackgroundExecutor;
    public boolean mBlurredBitmapCreated;
    public final Rect mBounds;
    public Bitmap mCapture;
    public int mCurrentDensityDpi;
    public float mCurrentFontScale;
    public DragAndDropOptions mDropOptions;
    public int mFreeformHeight;
    public int mFreeformPatialBlurViewHeight;
    public int mFreeformPatialBlurViewWidth;
    public int mFreeformWidth;
    public AnimatorSet mHideAnimatorSet;
    public boolean mIsFreeform;
    public boolean mIsNightModeOn;
    public int mOrientation;
    public ImageView mPartialBlurView;
    public AnimatorSet mShowAnimatorSet;
    public TextView mText;
    public Handler mUiHandler;
    public View mView;

    public static /* synthetic */ void $r8$lambda$tplp4yJwUthw95eKri7lNnjwHIU(final DropTargetView dropTargetView, final long j) {
        Bitmap bitmap = dropTargetView.mCapture;
        if (bitmap == null) {
            Log.w("DropTargetView", "createBlurredBitmapIfNeeded: bitmap is null");
        } else {
            final Bitmap blurredBitmap = NonDragTarget.getBlurredBitmap(((FrameLayout) dropTargetView).mContext, bitmap);
            dropTargetView.mUiHandler.post(new Runnable(blurredBitmap, j) { // from class: com.android.wm.shell.draganddrop.DropTargetView$$ExternalSyntheticLambda1
                public final /* synthetic */ Bitmap f$1;

                @Override // java.lang.Runnable
                public final void run() {
                    DropTargetView dropTargetView2 = this.f$0;
                    Bitmap bitmap2 = this.f$1;
                    ImageView imageView = dropTargetView2.mPartialBlurView;
                    if (imageView == null) {
                        Log.w("DropTargetView", "createBlurredBitmapIfNeeded: view is null");
                    } else {
                        imageView.setImageBitmap(bitmap2);
                    }
                }
            });
        }
    }

    public DropTargetView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Point point = new Point();
        this.mIsFreeform = false;
        this.mBounds = new Rect();
        this.mCapture = null;
        this.mShowAnimatorSet = null;
        this.mHideAnimatorSet = null;
        context.getDisplay().getRealSize(point);
    }

    public final int getBackgroundResourceId() {
        return (isLandScapeWithNotMultiSplit() && this.mIsFreeform) ? CoreRune.MW_SUPPORT_DRAG_AND_DROP_PARTIAL_BLUR ? R.drawable.drag_area_blur_background_round_freeform_land : R.drawable.drag_area_background_round_freeform_land : CoreRune.MW_SUPPORT_DRAG_AND_DROP_PARTIAL_BLUR ? this.mIsFreeform ? R.drawable.drag_area_blur_background_round_freeform : R.drawable.drag_area_blur_background_round : this.mIsFreeform ? R.drawable.drag_area_background_round_freeform : R.drawable.drag_area_background;
    }

    public final void hide() {
        if (this.mHideAnimatorSet == null) {
            this.mHideAnimatorSet = new AnimatorSet();
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this, "alpha", 1.0f, 0.0f);
            objectAnimatorOfFloat.setDuration(300L);
            PathInterpolator pathInterpolator = InterpolatorUtils.SINE_OUT_60;
            objectAnimatorOfFloat.setInterpolator(pathInterpolator);
            ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(this.mText, "alpha", 1.0f, 0.0f);
            objectAnimatorOfFloat2.setDuration(300L);
            objectAnimatorOfFloat2.setInterpolator(pathInterpolator);
            ValueAnimator valueAnimatorOfInt = ObjectAnimator.ofInt(CoreRune.MW_SUPPORT_DRAG_AND_DROP_REAL_TIME_BLUR ? 125 : 80, 0);
            valueAnimatorOfInt.setDuration(200L);
            valueAnimatorOfInt.setInterpolator(pathInterpolator);
            ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(this.mText, "scaleX", 1.0f, 0.8f);
            objectAnimatorOfFloat3.setDuration(150L);
            PathInterpolator pathInterpolator2 = InterpolatorUtils.ONE_EASING;
            objectAnimatorOfFloat3.setInterpolator(pathInterpolator2);
            ObjectAnimator objectAnimatorOfFloat4 = ObjectAnimator.ofFloat(this.mText, "scaleY", 1.0f, 0.8f);
            objectAnimatorOfFloat4.setDuration(150L);
            objectAnimatorOfFloat4.setInterpolator(pathInterpolator2);
            valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.draganddrop.DropTargetView.3
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    DropTargetView dropTargetView = DropTargetView.this;
                    int iIntValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                    int i = DropTargetView.$r8$clinit;
                    dropTargetView.setBlurEffect(iIntValue);
                }
            });
            this.mHideAnimatorSet.playTogether(objectAnimatorOfFloat, objectAnimatorOfFloat2, objectAnimatorOfFloat3, objectAnimatorOfFloat4, valueAnimatorOfInt);
        }
        AnimatorSet animatorSet = this.mShowAnimatorSet;
        if (animatorSet != null && animatorSet.isRunning()) {
            this.mShowAnimatorSet.cancel();
        }
        if (this.mHideAnimatorSet.isRunning()) {
            this.mHideAnimatorSet.cancel();
        }
        this.mHideAnimatorSet.start();
        this.mBounds.setEmpty();
    }

    public final boolean isLandScapeWithNotMultiSplit() {
        boolean z = getResources().getConfiguration().orientation == 2;
        return CoreRune.MW_DND_MULTI_SPLIT_DROP_TARGET ? MultiWindowUtils.isInSubDisplay(((FrameLayout) this).mContext) && z : z;
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) throws Resources.NotFoundException {
        super.onConfigurationChanged(configuration);
        int i = configuration.orientation;
        if (i != this.mOrientation) {
            this.mOrientation = i;
            updateBounds();
        }
        boolean z = (configuration.uiMode & 32) != 0;
        if (this.mIsNightModeOn != z) {
            this.mIsNightModeOn = z;
            int backgroundResourceId = getBackgroundResourceId();
            this.mView.setBackgroundResource(0);
            this.mView.setBackgroundResource(backgroundResourceId);
            this.mText.setTextColor(((FrameLayout) this).mContext.getColor(R.color.drop_target_text_color));
            if (CoreRune.MW_SUPPORT_DRAG_AND_DROP_CAPTURED_BLUR) {
                this.mPartialBlurView.setBackgroundResource(0);
                this.mPartialBlurView.setBackgroundResource(backgroundResourceId);
            }
        }
        float f = this.mCurrentFontScale;
        float f2 = configuration.fontScale;
        if (f == f2 && this.mCurrentDensityDpi == configuration.densityDpi) {
            return;
        }
        this.mCurrentFontScale = f2;
        this.mCurrentDensityDpi = configuration.densityDpi;
        float dimension = getResources().getDimension(R.dimen.dnd_drop_target_text_size);
        this.mText.setTextSize(0, (float) (this.mCurrentFontScale > 1.3f ? Math.floor(Math.ceil(dimension / r0) * 1.2999999523162842d) : Math.ceil(dimension)));
    }

    @Override // android.view.View
    public final void onFinishInflate() throws Resources.NotFoundException {
        super.onFinishInflate();
        this.mView = findViewById(R.id.dnd_drop_target_view);
        this.mText = (TextView) findViewById(R.id.dnd_drop_target_text);
        this.mPartialBlurView = (ImageView) findViewById(R.id.patial_blur_view);
        this.mCurrentFontScale = this.mText.getResources().getConfiguration().fontScale;
        this.mCurrentDensityDpi = this.mText.getResources().getConfiguration().densityDpi;
        this.mIsNightModeOn = (getResources().getConfiguration().uiMode & 32) != 0;
        this.mOrientation = getResources().getConfiguration().orientation;
        float dimension = getResources().getDimension(R.dimen.dnd_drop_target_text_size);
        this.mText.setTextSize(0, (float) (this.mCurrentFontScale > 1.3f ? Math.floor(Math.ceil(dimension / r2) * 1.2999999523162842d) : Math.ceil(dimension)));
    }

    public final void setBlurEffect(int i) {
        SemBlurInfo semBlurInfoBuild;
        if (CoreRune.MW_SUPPORT_DRAG_AND_DROP_REAL_TIME_BLUR) {
            semBlurInfoBuild = new SemBlurInfo.Builder(0).setRadius(i).setBackgroundCornerRadius(getResources().getDimension(R.dimen.dnd_drop_freeform_corner_radius_size)).build();
        } else if (CoreRune.MW_SUPPORT_DRAG_AND_DROP_CAPTURED_BLUR) {
            int backgroundResourceId = getBackgroundResourceId();
            if (this.mCapture == null) {
                Rect rect = new Rect();
                DragAndDropOptions dragAndDropOptions = this.mDropOptions;
                if (dragAndDropOptions != null && dragAndDropOptions.mBounds.isValid()) {
                    rect.set(this.mDropOptions.mBounds);
                } else if (this.mBounds.isValid()) {
                    rect.set(this.mBounds);
                }
                if (this.mIsFreeform) {
                    rect.inset((rect.width() - this.mFreeformWidth) / 2, (rect.height() - this.mFreeformHeight) / 2);
                }
                Bitmap bitmapScreenshot = SemWindowManager.getInstance().screenshot(0, 2000, false, rect, rect.width(), rect.height(), false, ((FrameLayout) this).mContext.getDisplay().getRotation());
                if (bitmapScreenshot == null) {
                    Log.w("DropTargetView", "setBlurEffect: failed, capture failed, " + Debug.getCallers(7));
                    this.mPartialBlurView.semSetBlurInfo(null);
                    return;
                }
                this.mCapture = bitmapScreenshot;
            }
            if (this.mBackgroundExecutor != null && this.mUiHandler != null) {
                if (this.mBlurredBitmapCreated) {
                    return;
                }
                final long jCurrentTimeMillis = System.currentTimeMillis();
                this.mBlurredBitmapCreated = true;
                this.mBackgroundExecutor.execute(new Runnable() { // from class: com.android.wm.shell.draganddrop.DropTargetView$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        DropTargetView.$r8$lambda$tplp4yJwUthw95eKri7lNnjwHIU(this.f$0, jCurrentTimeMillis);
                    }
                });
                return;
            }
            semBlurInfoBuild = new SemBlurInfo.Builder(1).setRadius(i).setBitmap(this.mCapture).build();
            this.mPartialBlurView.setBackgroundResource(backgroundResourceId);
            this.mPartialBlurView.setClipToOutline(true);
        } else {
            semBlurInfoBuild = null;
        }
        this.mPartialBlurView.semSetBlurInfo(semBlurInfoBuild);
    }

    public final void showBlurEffect() {
        if (!this.mIsFreeform) {
            setForeground(null);
        } else if (isLandScapeWithNotMultiSplit()) {
            setForeground(getResources().getDrawable(R.drawable.drag_area_shadow_background_round_freeform_land));
        } else {
            setForeground(getResources().getDrawable(R.drawable.drag_area_shadow_background_round_freeform));
        }
        setBlurEffect(CoreRune.MW_SUPPORT_DRAG_AND_DROP_REAL_TIME_BLUR ? 125 : 80);
    }

    public final void updateBounds() {
        if (isLandScapeWithNotMultiSplit()) {
            this.mFreeformWidth = (int) getResources().getDimension(R.dimen.dnd_drop_freeform_height);
            this.mFreeformHeight = (int) getResources().getDimension(R.dimen.dnd_drop_freeform_width);
            this.mFreeformPatialBlurViewWidth = (int) getResources().getDimension(R.dimen.dnd_drop_freeform_dim_height);
            this.mFreeformPatialBlurViewHeight = (int) getResources().getDimension(R.dimen.dnd_drop_freeform_dim_width);
        } else {
            this.mFreeformWidth = (int) getResources().getDimension(R.dimen.dnd_drop_freeform_width);
            this.mFreeformHeight = (int) getResources().getDimension(R.dimen.dnd_drop_freeform_height);
            this.mFreeformPatialBlurViewWidth = (int) getResources().getDimension(R.dimen.dnd_drop_freeform_dim_width);
            this.mFreeformPatialBlurViewHeight = (int) getResources().getDimension(R.dimen.dnd_drop_freeform_dim_height);
        }
        View view = this.mView;
        ImageView imageView = this.mPartialBlurView;
        boolean z = this.mIsFreeform;
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        ViewGroup.LayoutParams layoutParams2 = imageView.getLayoutParams();
        if (layoutParams != null) {
            if (z) {
                layoutParams.width = this.mFreeformWidth;
                layoutParams.height = this.mFreeformHeight;
            } else {
                layoutParams.width = -1;
                layoutParams.height = -1;
            }
            view.setLayoutParams(layoutParams);
        }
        if (layoutParams2 != null) {
            if (z) {
                layoutParams2.width = this.mFreeformPatialBlurViewWidth;
                layoutParams2.height = this.mFreeformPatialBlurViewHeight;
            } else {
                layoutParams2.width = -1;
                layoutParams2.height = -1;
            }
            imageView.setLayoutParams(layoutParams2);
        }
        if (CoreRune.MW_SUPPORT_DRAG_AND_DROP_PARTIAL_BLUR) {
            showBlurEffect();
            this.mCapture = null;
        }
        this.mView.setBackground(getContext().getDrawable(getBackgroundResourceId()));
        DragAndDropOptions dragAndDropOptions = this.mDropOptions;
        int i = R.string.drop_here_to_open;
        if (dragAndDropOptions != null) {
            if (dragAndDropOptions.mIsFreeform) {
                i = R.string.drop_here_for_popup_view;
            } else if (dragAndDropOptions.mIsFullscreen) {
                i = dragAndDropOptions.mIsResizable ? R.string.drop_now_to_open_in_fullscreen_view : R.string.dnd_applist_non_resizable_fullscreen;
            }
        }
        this.mText.setText(i);
        setX(this.mBounds.left);
        setY(this.mBounds.top);
        ViewGroup.LayoutParams layoutParams3 = getLayoutParams();
        layoutParams3.width = this.mBounds.width();
        layoutParams3.height = this.mBounds.height();
        setLayoutParams(layoutParams3);
    }
}
