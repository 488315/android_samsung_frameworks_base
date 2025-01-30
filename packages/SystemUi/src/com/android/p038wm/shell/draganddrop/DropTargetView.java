package com.android.p038wm.shell.draganddrop;

import android.animation.AnimatorSet;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SemBlurInfo;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.systemui.R;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.view.SemWindowManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class DropTargetView extends FrameLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
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
    public View mView;

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
        return (isLandScapeWithNotMultiSplit() && this.mIsFreeform) ? CoreRune.MW_SUPPORT_DRAG_AND_DROP_PATIAL_BLUR ? R.drawable.drag_area_blur_background_round_freeform_land : R.drawable.drag_area_background_round_freeform_land : CoreRune.MW_SUPPORT_DRAG_AND_DROP_PATIAL_BLUR ? this.mIsFreeform ? R.drawable.drag_area_blur_background_round_freeform : R.drawable.drag_area_blur_background_round : this.mIsFreeform ? R.drawable.drag_area_background_round_freeform : R.drawable.drag_area_background;
    }

    public final boolean isLandScapeWithNotMultiSplit() {
        boolean z = getResources().getConfiguration().orientation == 2;
        return CoreRune.MW_DND_MULTI_SPLIT_DROP_TARGET ? MultiWindowUtils.isInSubDisplay(((FrameLayout) this).mContext) && z : z;
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
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
        this.mText.setTextSize(0, DragAndDropUtil.calculateFontSizeWithScale(getResources().getDimension(R.dimen.dnd_drop_target_text_size), this.mCurrentFontScale));
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mView = findViewById(R.id.dnd_drop_target_view);
        this.mText = (TextView) findViewById(R.id.dnd_drop_target_text);
        this.mPartialBlurView = (ImageView) findViewById(R.id.patial_blur_view);
        this.mCurrentFontScale = this.mText.getResources().getConfiguration().fontScale;
        this.mCurrentDensityDpi = this.mText.getResources().getConfiguration().densityDpi;
        this.mIsNightModeOn = (getResources().getConfiguration().uiMode & 32) != 0;
        this.mOrientation = getResources().getConfiguration().orientation;
        this.mText.setTextSize(0, DragAndDropUtil.calculateFontSizeWithScale(getResources().getDimension(R.dimen.dnd_drop_target_text_size), this.mCurrentFontScale));
    }

    public final void setBlurEffect(int i) {
        SemBlurInfo semBlurInfo;
        if (CoreRune.MW_SUPPORT_DRAG_AND_DROP_REAL_TIME_BLUR) {
            semBlurInfo = new SemBlurInfo.Builder(0).setRadius(i).setBackgroundCornerRadius(getResources().getDimension(R.dimen.dnd_drop_freeform_corner_radius_size)).build();
        } else {
            if (CoreRune.MW_SUPPORT_DRAG_AND_DROP_CAPTURED_BLUR) {
                int backgroundResourceId = getBackgroundResourceId();
                if (this.mCapture == null) {
                    Rect rect = new Rect();
                    DragAndDropOptions dragAndDropOptions = this.mDropOptions;
                    if (dragAndDropOptions != null) {
                        rect.set(dragAndDropOptions.mBounds);
                    }
                    if (this.mIsFreeform) {
                        rect.inset((rect.width() - this.mFreeformWidth) / 2, (rect.height() - this.mFreeformHeight) / 2);
                    }
                    Bitmap screenshot = SemWindowManager.getInstance().screenshot(0, 2000, false, rect, rect.width(), rect.height(), false, ((FrameLayout) this).mContext.getDisplay().getRotation());
                    this.mCapture = screenshot != null ? screenshot : null;
                }
                semBlurInfo = new SemBlurInfo.Builder(1).setRadius(i).setBitmap(this.mCapture).build();
                this.mPartialBlurView.setBackgroundResource(backgroundResourceId);
                this.mPartialBlurView.setClipToOutline(true);
            } else {
                semBlurInfo = null;
            }
        }
        this.mPartialBlurView.semSetBlurInfo(semBlurInfo);
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
        int i;
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
        if (CoreRune.MW_SUPPORT_DRAG_AND_DROP_PATIAL_BLUR) {
            showBlurEffect();
            this.mCapture = null;
        }
        this.mView.setBackground(getContext().getDrawable(getBackgroundResourceId()));
        DragAndDropOptions dragAndDropOptions = this.mDropOptions;
        int i2 = R.string.drop_here_to_open;
        if (dragAndDropOptions != null) {
            if (dragAndDropOptions.mIsFreeform) {
                i = R.string.drop_here_for_popup_view;
            } else if (dragAndDropOptions.mIsFullscreen) {
                i = dragAndDropOptions.mIsResizable ? R.string.drop_now_to_open_in_fullscreen_view : R.string.dnd_applist_non_resizable_fullscreen;
            }
            i2 = i;
        }
        this.mText.setText(i2);
        setX(this.mBounds.left);
        setY(this.mBounds.top);
        ViewGroup.LayoutParams layoutParams3 = getLayoutParams();
        layoutParams3.width = this.mBounds.width();
        layoutParams3.height = this.mBounds.height();
        setLayoutParams(layoutParams3);
    }
}
