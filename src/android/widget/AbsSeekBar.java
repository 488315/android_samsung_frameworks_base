package android.widget;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Insets;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import com.android.internal.R;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes5.dex */
public abstract class AbsSeekBar extends ProgressBar {
    private static final int MUTE_VIB_DISTANCE_LVL = 400;
    private static final int MUTE_VIB_DURATION = 500;
    private static final int MUTE_VIB_TOTAL = 4;
    private static final int NO_ALPHA = 255;
    private boolean mAllowedSeekBarAnimation;
    private int mCurrentProgressLevel;
    private ColorStateList mDefaultActivatedProgressColor;
    private ColorStateList mDefaultActivatedThumbColor;
    private ColorStateList mDefaultNormalProgressColor;
    private ColorStateList mDefaultSecondaryProgressColor;
    private float mDisabledAlpha;
    private Drawable mDivider;
    private final List<Rect> mGestureExclusionRects;
    private boolean mHasThumbBlendMode;
    private boolean mHasThumbTint;
    private boolean mHasTickMarkBlendMode;
    private boolean mHasTickMarkTint;
    private int mHoveringLevel;
    private boolean mIsDragging;
    private boolean mIsDraggingForSliding;
    private boolean mIsFirstSetProgress;
    private boolean mIsTouchDisabled;
    boolean mIsUserSeekable;
    private int mKeyProgressIncrement;
    private boolean mLargeFont;
    private int mModeExpandThumbRadius;
    private int mModeExpandTrackMaxWidth;
    private int mModeExpandTrackMinWidth;
    private AnimatorSet mMuteAnimationSet;
    private ColorStateList mOverlapActivatedProgressColor;
    private ColorStateList mOverlapActivatedThumbColor;
    private Drawable mOverlapBackground;
    private ColorStateList mOverlapNormalProgressColor;
    private int mOverlapPoint;
    private int mScaledTouchSlop;
    private boolean mSetDualColorMode;
    private Drawable mSplitProgress;
    private boolean mSplitTrack;
    private final Rect mTempRect;
    private Drawable mThumb;
    private BlendMode mThumbBlendMode;
    private int mThumbExclusionMaxSize;
    private int mThumbOffset;
    private int mThumbPosX;
    private int mThumbRadius;
    private final Rect mThumbRect;
    private ColorStateList mThumbTintList;
    private Drawable mTickMark;
    private BlendMode mTickMarkBlendMode;
    private ColorStateList mTickMarkTintList;
    private float mTouchDownX;
    private float mTouchDownY;
    float mTouchProgressOffset;
    private float mTouchThumbOffset;
    private int mTrackMaxWidth;
    private int mTrackMinWidth;
    private boolean mUseMuteAnimation;
    private List<Rect> mUserGestureExclusionRects;

    void onHoverChanged(int i, int i2, int i3) {
    }

    void onKeyChange() {
    }

    void onStartTrackingHover(int i, int i2, int i3) {
    }

    void onStopTrackingHover() {
    }

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<AbsSeekBar> {
        private boolean mPropertiesMapped = false;
        private int mThumbTintId;
        private int mThumbTintModeId;
        private int mTickMarkTintBlendModeId;
        private int mTickMarkTintId;
        private int mTickMarkTintModeId;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(PropertyMapper propertyMapper) {
            this.mThumbTintId = propertyMapper.mapObject("thumbTint", 16843889);
            this.mThumbTintModeId = propertyMapper.mapObject("thumbTintMode", 16843890);
            this.mTickMarkTintId = propertyMapper.mapObject("tickMarkTint", 16844043);
            this.mTickMarkTintBlendModeId = propertyMapper.mapObject("tickMarkTintBlendMode", 7);
            this.mTickMarkTintModeId = propertyMapper.mapObject("tickMarkTintMode", 16844044);
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(AbsSeekBar absSeekBar, PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readObject(this.mThumbTintId, absSeekBar.getThumbTintList());
            propertyReader.readObject(this.mThumbTintModeId, absSeekBar.getThumbTintMode());
            propertyReader.readObject(this.mTickMarkTintId, absSeekBar.getTickMarkTintList());
            propertyReader.readObject(this.mTickMarkTintBlendModeId, absSeekBar.getTickMarkTintBlendMode());
            propertyReader.readObject(this.mTickMarkTintModeId, absSeekBar.getTickMarkTintMode());
        }
    }

    public AbsSeekBar(Context context) {
        super(context);
        this.mTempRect = new Rect();
        this.mThumbTintList = null;
        this.mThumbBlendMode = null;
        this.mHasThumbTint = false;
        this.mHasThumbBlendMode = false;
        this.mTickMarkTintList = null;
        this.mTickMarkBlendMode = null;
        this.mHasTickMarkTint = false;
        this.mHasTickMarkBlendMode = false;
        this.mIsUserSeekable = true;
        this.mKeyProgressIncrement = 1;
        this.mTouchThumbOffset = 0.0f;
        this.mUserGestureExclusionRects = Collections.EMPTY_LIST;
        this.mGestureExclusionRects = new ArrayList();
        this.mThumbRect = new Rect();
        this.mHoveringLevel = 0;
        this.mOverlapPoint = -1;
        this.mAllowedSeekBarAnimation = false;
        this.mUseMuteAnimation = false;
        this.mIsFirstSetProgress = false;
        this.mIsDraggingForSliding = false;
        this.mLargeFont = false;
        this.mIsTouchDisabled = false;
        this.mSetDualColorMode = false;
    }

    public AbsSeekBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTempRect = new Rect();
        this.mThumbTintList = null;
        this.mThumbBlendMode = null;
        this.mHasThumbTint = false;
        this.mHasThumbBlendMode = false;
        this.mTickMarkTintList = null;
        this.mTickMarkBlendMode = null;
        this.mHasTickMarkTint = false;
        this.mHasTickMarkBlendMode = false;
        this.mIsUserSeekable = true;
        this.mKeyProgressIncrement = 1;
        this.mTouchThumbOffset = 0.0f;
        this.mUserGestureExclusionRects = Collections.EMPTY_LIST;
        this.mGestureExclusionRects = new ArrayList();
        this.mThumbRect = new Rect();
        this.mHoveringLevel = 0;
        this.mOverlapPoint = -1;
        this.mAllowedSeekBarAnimation = false;
        this.mUseMuteAnimation = false;
        this.mIsFirstSetProgress = false;
        this.mIsDraggingForSliding = false;
        this.mLargeFont = false;
        this.mIsTouchDisabled = false;
        this.mSetDualColorMode = false;
    }

    public AbsSeekBar(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public AbsSeekBar(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mTempRect = new Rect();
        this.mThumbTintList = null;
        this.mThumbBlendMode = null;
        this.mHasThumbTint = false;
        this.mHasThumbBlendMode = false;
        this.mTickMarkTintList = null;
        this.mTickMarkBlendMode = null;
        this.mHasTickMarkTint = false;
        this.mHasTickMarkBlendMode = false;
        this.mIsUserSeekable = true;
        this.mKeyProgressIncrement = 1;
        this.mTouchThumbOffset = 0.0f;
        this.mUserGestureExclusionRects = Collections.EMPTY_LIST;
        this.mGestureExclusionRects = new ArrayList();
        this.mThumbRect = new Rect();
        this.mHoveringLevel = 0;
        this.mOverlapPoint = -1;
        this.mAllowedSeekBarAnimation = false;
        this.mUseMuteAnimation = false;
        this.mIsFirstSetProgress = false;
        this.mIsDraggingForSliding = false;
        this.mLargeFont = false;
        this.mIsTouchDisabled = false;
        this.mSetDualColorMode = false;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.SeekBar, i, i2);
        saveAttributeDataForStyleable(context, R.styleable.SeekBar, attributeSet, obtainStyledAttributes, i, i2);
        setThumb(obtainStyledAttributes.getDrawable(0));
        if (obtainStyledAttributes.hasValue(4)) {
            this.mThumbBlendMode = Drawable.parseBlendMode(obtainStyledAttributes.getInt(4, -1), this.mThumbBlendMode);
            this.mHasThumbBlendMode = true;
        }
        if (obtainStyledAttributes.hasValue(3)) {
            this.mThumbTintList = obtainStyledAttributes.getColorStateList(3);
            this.mHasThumbTint = true;
        }
        setTickMark(obtainStyledAttributes.getDrawable(5));
        if (obtainStyledAttributes.hasValue(7)) {
            this.mTickMarkBlendMode = Drawable.parseBlendMode(obtainStyledAttributes.getInt(7, -1), this.mTickMarkBlendMode);
            this.mHasTickMarkBlendMode = true;
        }
        if (obtainStyledAttributes.hasValue(6)) {
            this.mTickMarkTintList = obtainStyledAttributes.getColorStateList(6);
            this.mHasTickMarkTint = true;
        }
        this.mSplitTrack = obtainStyledAttributes.getBoolean(2, false);
        setThumbOffset(obtainStyledAttributes.getDimensionPixelOffset(1, getThumbOffset()));
        boolean z = obtainStyledAttributes.getBoolean(8, true);
        obtainStyledAttributes.recycle();
        if (z) {
            TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, R.styleable.Theme, 0, 0);
            this.mDisabledAlpha = obtainStyledAttributes2.getFloat(3, 0.4f);
            obtainStyledAttributes2.recycle();
        } else {
            this.mDisabledAlpha = 1.0f;
        }
        applyThumbTint();
        applyTickMarkTint();
        this.mScaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.mThumbExclusionMaxSize = getResources().getDimensionPixelSize(R.dimen.seekbar_thumb_exclusion_max_size);
        Resources resources = context.getResources();
        this.mTrackMinWidth = resources.getDimensionPixelSize(R.dimen.sem_seekbar_track_height);
        this.mTrackMaxWidth = resources.getDimensionPixelSize(R.dimen.sem_seekbar_track_height_expand);
        this.mModeExpandTrackMinWidth = resources.getDimensionPixelSize(R.dimen.sem_seekbar_mode_expand_track_height);
        this.mModeExpandTrackMaxWidth = resources.getDimensionPixelSize(R.dimen.sem_seekbar_mode_expand_track_height_expand);
        this.mThumbRadius = resources.getDimensionPixelSize(R.dimen.sem_seekbar_thumb_radius);
        this.mModeExpandThumbRadius = resources.getDimensionPixelSize(R.dimen.sem_seekbar_mode_expand_thumb_radius);
        this.mDefaultNormalProgressColor = colorToColorStateList(resources.getColor(this.mIsDeviceDefaultDark ? R.color.tw_seekbar_color_control_normal_dark : R.color.tw_seekbar_color_control_normal_light, null));
        this.mDefaultSecondaryProgressColor = colorToColorStateList(resources.getColor(R.color.tw_seekbar_color_control_secondary, null));
        ColorStateList semGetProgressTintList = semGetProgressTintList();
        this.mDefaultActivatedProgressColor = semGetProgressTintList;
        if (semGetProgressTintList == null) {
            this.mDefaultActivatedProgressColor = colorToColorStateList(resources.getColor(this.mIsDeviceDefaultDark ? R.color.tw_seekbar_color_control_activated_dark : R.color.tw_seekbar_color_control_activated_light, null));
        }
        this.mOverlapNormalProgressColor = colorToColorStateList(resources.getColor(this.mIsDeviceDefaultDark ? R.color.tw_seekbar_color_overlap_normal_dark : R.color.tw_seekbar_color_overlap_normal_light, null));
        boolean z2 = this.mIsDeviceDefaultDark;
        int i3 = R.color.tw_seekbar_color_overlap_activated_dark;
        this.mOverlapActivatedProgressColor = colorToColorStateList(resources.getColor(z2 ? 17171793 : 17171794, null));
        int[][] iArr = {new int[]{16842910}, new int[]{-16842910}};
        int color = resources.getColor(this.mIsDeviceDefaultDark ? i3 : 17171794);
        boolean z3 = this.mIsDeviceDefaultDark;
        int i4 = R.color.tw_seekbar_disable_color_activated_dark;
        this.mOverlapActivatedThumbColor = new ColorStateList(iArr, new int[]{color, resources.getColor(z3 ? 17171797 : 17171798)});
        ColorStateList colorStateList = this.mThumbTintList;
        this.mDefaultActivatedThumbColor = colorStateList;
        if (colorStateList == null) {
            this.mDefaultActivatedThumbColor = new ColorStateList(iArr, new int[]{resources.getColor(this.mIsDeviceDefaultDark ? R.color.tw_thumb_color_control_activated_dark : R.color.tw_thumb_color_control_activated_light), resources.getColor(this.mIsDeviceDefaultDark ? i4 : 17171798)});
        }
        boolean z4 = resources.getBoolean(R.bool.tw_seekbar_sliding_animation);
        this.mAllowedSeekBarAnimation = z4;
        if (z4) {
            initMuteAnimation();
        }
    }

    public void setThumb(Drawable drawable) {
        boolean z;
        Drawable drawable2 = this.mThumb;
        if (drawable2 == null || drawable == drawable2) {
            z = false;
        } else {
            drawable2.setCallback(null);
            z = true;
        }
        if (drawable != null) {
            drawable.setCallback(this);
            if (canResolveLayoutDirection()) {
                drawable.setLayoutDirection(getLayoutDirection());
            }
            if (this.mCurrentMode == 3) {
                this.mThumbOffset = drawable.getIntrinsicHeight() / 2;
            } else {
                this.mThumbOffset = drawable.getIntrinsicWidth() / 2;
            }
            if (z && (drawable.getIntrinsicWidth() != this.mThumb.getIntrinsicWidth() || drawable.getIntrinsicHeight() != this.mThumb.getIntrinsicHeight())) {
                requestLayout();
            }
        }
        this.mThumb = drawable;
        applyThumbTint();
        invalidate();
        if (z) {
            updateThumbAndTrackPos(getWidth(), getHeight());
            if (drawable == null || !drawable.isStateful()) {
                return;
            }
            drawable.setState(getDrawableState());
        }
    }

    public Drawable getThumb() {
        return this.mThumb;
    }

    public void setThumbTintList(ColorStateList colorStateList) {
        this.mThumbTintList = colorStateList;
        this.mHasThumbTint = true;
        applyThumbTint();
        this.mDefaultActivatedThumbColor = colorStateList;
    }

    public ColorStateList getThumbTintList() {
        return this.mThumbTintList;
    }

    public void setThumbTintMode(PorterDuff.Mode mode) {
        setThumbTintBlendMode(mode != null ? BlendMode.fromValue(mode.nativeInt) : null);
    }

    public void setThumbTintBlendMode(BlendMode blendMode) {
        this.mThumbBlendMode = blendMode;
        this.mHasThumbBlendMode = true;
        applyThumbTint();
    }

    public PorterDuff.Mode getThumbTintMode() {
        BlendMode blendMode = this.mThumbBlendMode;
        if (blendMode != null) {
            return BlendMode.blendModeToPorterDuffMode(blendMode);
        }
        return null;
    }

    public BlendMode getThumbTintBlendMode() {
        return this.mThumbBlendMode;
    }

    private void applyThumbTint() {
        Drawable drawable = this.mThumb;
        if (drawable != null) {
            if (this.mHasThumbTint || this.mHasThumbBlendMode) {
                Drawable mutate = drawable.mutate();
                this.mThumb = mutate;
                if (this.mHasThumbTint) {
                    mutate.setTintList(this.mThumbTintList);
                }
                if (this.mHasThumbBlendMode) {
                    this.mThumb.setTintBlendMode(this.mThumbBlendMode);
                }
                if (this.mThumb.isStateful()) {
                    this.mThumb.setState(getDrawableState());
                }
            }
        }
    }

    public int getThumbOffset() {
        return this.mThumbOffset;
    }

    public void setThumbOffset(int i) {
        this.mThumbOffset = i;
        invalidate();
    }

    public void setSplitTrack(boolean z) {
        this.mSplitTrack = z;
        invalidate();
    }

    public boolean getSplitTrack() {
        return this.mSplitTrack;
    }

    public void setTickMark(Drawable drawable) {
        Drawable drawable2 = this.mTickMark;
        if (drawable2 != null) {
            drawable2.setCallback(null);
        }
        this.mTickMark = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
            drawable.setLayoutDirection(getLayoutDirection());
            if (drawable.isStateful()) {
                drawable.setState(getDrawableState());
            }
            applyTickMarkTint();
        }
        invalidate();
    }

    public Drawable getTickMark() {
        return this.mTickMark;
    }

    public void setTickMarkTintList(ColorStateList colorStateList) {
        this.mTickMarkTintList = colorStateList;
        this.mHasTickMarkTint = true;
        applyTickMarkTint();
    }

    public ColorStateList getTickMarkTintList() {
        return this.mTickMarkTintList;
    }

    public void setTickMarkTintMode(PorterDuff.Mode mode) {
        setTickMarkTintBlendMode(mode != null ? BlendMode.fromValue(mode.nativeInt) : null);
    }

    public void setTickMarkTintBlendMode(BlendMode blendMode) {
        this.mTickMarkBlendMode = blendMode;
        this.mHasTickMarkBlendMode = true;
        applyTickMarkTint();
    }

    public PorterDuff.Mode getTickMarkTintMode() {
        BlendMode blendMode = this.mTickMarkBlendMode;
        if (blendMode != null) {
            return BlendMode.blendModeToPorterDuffMode(blendMode);
        }
        return null;
    }

    public BlendMode getTickMarkTintBlendMode() {
        return this.mTickMarkBlendMode;
    }

    private void applyTickMarkTint() {
        Drawable drawable = this.mTickMark;
        if (drawable != null) {
            if (this.mHasTickMarkTint || this.mHasTickMarkBlendMode) {
                Drawable mutate = drawable.mutate();
                this.mTickMark = mutate;
                if (this.mHasTickMarkTint) {
                    mutate.setTintList(this.mTickMarkTintList);
                }
                if (this.mHasTickMarkBlendMode) {
                    this.mTickMark.setTintBlendMode(this.mTickMarkBlendMode);
                }
                if (this.mTickMark.isStateful()) {
                    this.mTickMark.setState(getDrawableState());
                }
            }
        }
    }

    public void setKeyProgressIncrement(int i) {
        if (i < 0) {
            i = -i;
        }
        this.mKeyProgressIncrement = i;
    }

    public int getKeyProgressIncrement() {
        return this.mKeyProgressIncrement;
    }

    @Override // android.widget.ProgressBar
    public synchronized void setMin(int i) {
        super.setMin(i);
        int max = getMax() - getMin();
        int i2 = this.mKeyProgressIncrement;
        if (i2 == 0 || max / i2 > 20) {
            setKeyProgressIncrement(Math.max(1, Math.round(max / 20.0f)));
        }
    }

    @Override // android.widget.ProgressBar
    public synchronized void setMax(int i) {
        super.setMax(i);
        int max = getMax() - getMin();
        int i2 = this.mKeyProgressIncrement;
        if (i2 == 0 || max / i2 > 20) {
            setKeyProgressIncrement(Math.max(1, Math.round(max / 20.0f)));
        }
    }

    @Override // android.widget.ProgressBar, android.view.View
    protected boolean verifyDrawable(Drawable drawable) {
        return drawable == this.mThumb || drawable == this.mTickMark || super.verifyDrawable(drawable);
    }

    @Override // android.widget.ProgressBar, android.view.View
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable drawable = this.mThumb;
        if (drawable != null) {
            drawable.jumpToCurrentState();
        }
        Drawable drawable2 = this.mTickMark;
        if (drawable2 != null) {
            drawable2.jumpToCurrentState();
        }
    }

    @Override // android.widget.ProgressBar, android.view.View
    protected void drawableStateChanged() {
        Drawable drawable;
        super.drawableStateChanged();
        Drawable progressDrawable = getProgressDrawable();
        if (progressDrawable != null && this.mDisabledAlpha < 1.0f) {
            int i = isEnabled() ? 255 : (int) (this.mDisabledAlpha * 255.0f);
            progressDrawable.setAlpha(i);
            Drawable drawable2 = this.mOverlapBackground;
            if (drawable2 != null) {
                drawable2.setAlpha(i);
            }
        }
        if (this.mThumb != null && this.mHasThumbTint) {
            if (!isEnabled()) {
                this.mThumb.setTintList(null);
            } else {
                this.mThumb.setTintList(this.mDefaultActivatedThumbColor);
                updateDualColorMode();
            }
        }
        if (this.mSetDualColorMode && progressDrawable != null && progressDrawable.isStateful() && (drawable = this.mOverlapBackground) != null) {
            drawable.setState(getDrawableState());
        }
        Drawable drawable3 = this.mThumb;
        if (drawable3 != null && drawable3.isStateful() && drawable3.setState(getDrawableState())) {
            invalidateDrawable(drawable3);
        }
        Drawable drawable4 = this.mTickMark;
        if (drawable4 != null && drawable4.isStateful() && drawable4.setState(getDrawableState())) {
            invalidateDrawable(drawable4);
        }
    }

    @Override // android.widget.ProgressBar, android.view.View
    public void drawableHotspotChanged(float f, float f2) {
        super.drawableHotspotChanged(f, f2);
        Drawable drawable = this.mThumb;
        if (drawable != null) {
            drawable.setHotspot(f, f2);
        }
    }

    @Override // android.widget.ProgressBar
    void onVisualProgressChanged(int i, float f) {
        Drawable drawable;
        super.onVisualProgressChanged(i, f);
        if (i != 16908301 || (drawable = this.mThumb) == null) {
            return;
        }
        setThumbPos(getWidth(), drawable, f, Integer.MIN_VALUE);
        invalidate();
    }

    @Override // android.widget.ProgressBar, android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        updateThumbAndTrackPos(i, i2);
    }

    private void updateThumbAndTrackPos(int i, int i2) {
        int i3;
        int i4;
        if (this.mCurrentMode == 3) {
            updateThumbAndTrackPosInVertical(i, i2);
            return;
        }
        int i5 = (i2 - this.mPaddingTop) - this.mPaddingBottom;
        Drawable currentDrawable = getCurrentDrawable();
        Drawable drawable = this.mThumb;
        int min = Math.min(this.mMaxHeight, i5);
        int intrinsicHeight = drawable == null ? 0 : drawable.getIntrinsicHeight();
        if (intrinsicHeight > min) {
            i4 = (i5 - intrinsicHeight) / 2;
            i3 = ((intrinsicHeight - min) / 2) + i4;
        } else {
            int i6 = (i5 - min) / 2;
            int i7 = ((min - intrinsicHeight) / 2) + i6;
            i3 = i6;
            i4 = i7;
        }
        if (currentDrawable != null) {
            currentDrawable.setBounds(0, i3, (i - this.mPaddingRight) - this.mPaddingLeft, min + i3);
        }
        if (drawable != null) {
            setThumbPos(i, drawable, getScale(), i4);
        }
        updateSplitProgress();
    }

    private float getScale() {
        int max = getMax() - getMin();
        if (max > 0) {
            return (getProgress() - r0) / max;
        }
        return 0.0f;
    }

    private void setThumbPos(int i, Drawable drawable, float f, int i2) {
        int i3;
        if (this.mCurrentMode == 3) {
            setThumbPosInVertical(getHeight(), drawable, f, i2);
            return;
        }
        int i4 = (i - this.mPaddingLeft) - this.mPaddingRight;
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        int i5 = (i4 - intrinsicWidth) + (this.mThumbOffset * 2);
        int i6 = (int) ((f * i5) + 0.5f);
        if (i2 == Integer.MIN_VALUE) {
            Rect bounds = drawable.getBounds();
            int i7 = bounds.top;
            i3 = bounds.bottom;
            i2 = i7;
        } else {
            i3 = intrinsicHeight + i2;
        }
        if (this.mMirrorForRtl && isLayoutRtl()) {
            i6 = i5 - i6;
        }
        int i8 = i6 + intrinsicWidth;
        Drawable background = getBackground();
        if (background != null) {
            int i9 = this.mPaddingLeft - this.mThumbOffset;
            int i10 = this.mPaddingTop;
            background.setHotspotBounds(i6 + i9, i2 + i10, i9 + i8, i10 + i3);
        }
        drawable.setBounds(i6, i2, i8, i3);
        this.mThumbPosX = (i6 + this.mPaddingLeft) - (this.mPaddingLeft - (intrinsicWidth / 2));
        updateSplitProgress();
        updateGestureExclusionRects();
    }

    @Override // android.view.View
    public void setSystemGestureExclusionRects(List<Rect> list) {
        Preconditions.checkNotNull(list, "rects must not be null");
        this.mUserGestureExclusionRects = list;
        updateGestureExclusionRects();
    }

    private void updateGestureExclusionRects() {
        Drawable drawable = this.mThumb;
        if (drawable == null) {
            super.setSystemGestureExclusionRects(this.mUserGestureExclusionRects);
            return;
        }
        if (isFreeform()) {
            super.setSystemGestureExclusionRects(this.mUserGestureExclusionRects);
            return;
        }
        this.mGestureExclusionRects.clear();
        drawable.copyBounds(this.mThumbRect);
        this.mThumbRect.offset(this.mPaddingLeft - this.mThumbOffset, this.mPaddingTop);
        growRectTo(this.mThumbRect, Math.min(getHeight(), this.mThumbExclusionMaxSize));
        this.mGestureExclusionRects.add(this.mThumbRect);
        this.mGestureExclusionRects.addAll(this.mUserGestureExclusionRects);
        super.setSystemGestureExclusionRects(this.mGestureExclusionRects);
    }

    private boolean isFreeform() {
        return getContext().getResources().getConfiguration().windowConfiguration.getWindowingMode() == 5;
    }

    public void growRectTo(Rect rect, int i) {
        int height = i - rect.height();
        if (height > 0) {
            rect.top -= (height + 1) / 2;
            rect.bottom += height / 2;
        }
        int width = i - rect.width();
        if (width > 0) {
            rect.left -= (width + 1) / 2;
            rect.right += width / 2;
        }
    }

    @Override // android.widget.ProgressBar, android.view.View
    public void onResolveDrawables(int i) {
        super.onResolveDrawables(i);
        Drawable drawable = this.mThumb;
        if (drawable != null) {
            drawable.setLayoutDirection(i);
        }
    }

    @Override // android.widget.ProgressBar, android.view.View
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mCurrentMode == 4) {
            this.mSplitProgress.draw(canvas);
            this.mDivider.draw(canvas);
        }
        if (!this.mIsTouchDisabled) {
            drawThumb(canvas);
        }
    }

    @Override // android.widget.ProgressBar
    void drawTrack(Canvas canvas) {
        Drawable drawable = this.mThumb;
        if (drawable != null && this.mSplitTrack) {
            Insets opticalInsets = drawable.getOpticalInsets();
            Rect rect = this.mTempRect;
            drawable.copyBounds(rect);
            rect.offset(this.mPaddingLeft - this.mThumbOffset, this.mPaddingTop);
            rect.left += opticalInsets.left;
            rect.right -= opticalInsets.right;
            int save = canvas.save();
            canvas.clipRect(rect, Region.Op.DIFFERENCE);
            super.drawTrack(canvas);
            drawTickMarks(canvas);
            canvas.restoreToCount(save);
        } else {
            super.drawTrack(canvas);
            drawTickMarks(canvas);
        }
        if (checkInvalidatedDualColorMode()) {
            return;
        }
        canvas.save();
        if (this.mMirrorForRtl && isLayoutRtl()) {
            canvas.translate(getWidth() - this.mPaddingRight, this.mPaddingTop);
            canvas.scale(-1.0f, 1.0f);
        } else {
            canvas.translate(this.mPaddingLeft, this.mPaddingTop);
        }
        Rect bounds = this.mOverlapBackground.getBounds();
        Rect rect2 = this.mTempRect;
        this.mOverlapBackground.copyBounds(rect2);
        int max = Math.max(getProgress(), this.mOverlapPoint);
        int max2 = getMax();
        if (this.mCurrentMode == 3) {
            rect2.bottom = (int) (bounds.bottom - (bounds.height() * (max / max2)));
        } else {
            rect2.left = (int) (bounds.left + (bounds.width() * (max / max2)));
        }
        canvas.clipRect(rect2);
        if (this.mDefaultNormalProgressColor.getDefaultColor() != this.mOverlapNormalProgressColor.getDefaultColor()) {
            this.mOverlapBackground.draw(canvas);
        }
        canvas.restore();
    }

    protected void drawTickMarks(Canvas canvas) {
        if (this.mTickMark != null) {
            int max = getMax() - getMin();
            if (max > 1) {
                int intrinsicWidth = this.mTickMark.getIntrinsicWidth();
                int intrinsicHeight = this.mTickMark.getIntrinsicHeight();
                int i = intrinsicWidth >= 0 ? intrinsicWidth / 2 : 1;
                int i2 = intrinsicHeight >= 0 ? intrinsicHeight / 2 : 1;
                this.mTickMark.setBounds(-i, -i2, i, i2);
                float width = ((getWidth() - this.mPaddingLeft) - this.mPaddingRight) / max;
                int save = canvas.save();
                canvas.translate(this.mPaddingLeft, getHeight() / 2.0f);
                for (int i3 = 0; i3 <= max; i3++) {
                    this.mTickMark.draw(canvas);
                    canvas.translate(width, 0.0f);
                }
                canvas.restoreToCount(save);
            }
        }
    }

    void drawThumb(Canvas canvas) {
        if (this.mThumb != null) {
            int save = canvas.save();
            if (this.mCurrentMode == 3) {
                canvas.translate(this.mPaddingLeft, this.mPaddingTop - this.mThumbOffset);
            } else {
                canvas.translate(this.mPaddingLeft - this.mThumbOffset, this.mPaddingTop);
            }
            this.mThumb.draw(canvas);
            canvas.restoreToCount(save);
        }
    }

    @Override // android.widget.ProgressBar, android.view.View
    protected synchronized void onMeasure(int i, int i2) {
        int i3;
        int i4;
        Drawable currentDrawable = getCurrentDrawable();
        if (currentDrawable == null) {
            i3 = 0;
            i4 = 0;
        } else if (this.mCurrentMode == 3) {
            Drawable drawable = this.mThumb;
            int intrinsicHeight = drawable == null ? 0 : drawable.getIntrinsicHeight();
            int max = Math.max(this.mMinWidth, Math.min(this.mMaxWidth, currentDrawable.getIntrinsicHeight()));
            i3 = Math.max(this.mMinHeight, Math.min(this.mMaxHeight, currentDrawable.getIntrinsicWidth()));
            i4 = Math.max(intrinsicHeight, max);
        } else {
            Drawable drawable2 = this.mThumb;
            int intrinsicHeight2 = drawable2 == null ? 0 : drawable2.getIntrinsicHeight();
            int max2 = Math.max(this.mMinWidth, Math.min(this.mMaxWidth, currentDrawable.getIntrinsicWidth()));
            i3 = Math.max(intrinsicHeight2, Math.max(this.mMinHeight, Math.min(this.mMaxHeight, currentDrawable.getIntrinsicHeight())));
            i4 = max2;
        }
        setMeasuredDimension(resolveSizeAndState(i4 + this.mPaddingLeft + this.mPaddingRight, i, 0), resolveSizeAndState(i3 + this.mPaddingTop + this.mPaddingBottom, i2, 0));
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.mIsUserSeekable || this.mIsTouchDisabled || !isEnabled()) {
            return false;
        }
        int action = motionEvent.getAction();
        if (action == 0) {
            if (this.mThumb != null) {
                float width = (getWidth() - this.mPaddingLeft) - this.mPaddingRight;
                float progress = ((getProgress() - getMin()) / (getMax() - getMin())) - ((motionEvent.getX() - this.mPaddingLeft) / width);
                this.mTouchThumbOffset = progress;
                if (Math.abs(progress * width) > getThumbOffset()) {
                    this.mTouchThumbOffset = 0.0f;
                }
            }
            this.mIsDraggingForSliding = false;
            if (this.mCurrentMode == 5 || isInScrollingContainer()) {
                this.mTouchDownX = motionEvent.getX();
                this.mTouchDownY = motionEvent.getY();
            } else {
                startDrag(motionEvent);
            }
        } else if (action == 1) {
            if (this.mIsDraggingForSliding) {
                this.mIsDraggingForSliding = false;
            }
            if (this.mIsDragging) {
                trackTouchEvent(motionEvent);
                onStopTrackingTouch();
                setPressed(false);
            } else {
                onStartTrackingTouch();
                trackTouchEvent(motionEvent);
                onStopTrackingTouch();
            }
            invalidate();
        } else if (action == 2) {
            this.mIsDraggingForSliding = true;
            if (this.mIsDragging) {
                trackTouchEvent(motionEvent);
            } else {
                float x = motionEvent.getX();
                float y = motionEvent.getY();
                if ((this.mCurrentMode != 3 && Math.abs(x - this.mTouchDownX) > this.mScaledTouchSlop) || (this.mCurrentMode == 3 && Math.abs(y - this.mTouchDownY) > this.mScaledTouchSlop)) {
                    startDrag(motionEvent);
                }
            }
        } else if (action == 3) {
            this.mIsDraggingForSliding = false;
            if (this.mIsDragging) {
                onStopTrackingTouch();
                setPressed(false);
            }
            invalidate();
        }
        return true;
    }

    private void startDrag(MotionEvent motionEvent) {
        setPressed(true);
        Drawable drawable = this.mThumb;
        if (drawable != null) {
            invalidate(drawable.getBounds());
        }
        onStartTrackingTouch();
        trackTouchEvent(motionEvent);
        attemptClaimDrag();
    }

    private void setHotspot(float f, float f2) {
        Drawable background = getBackground();
        if (background != null) {
            background.setHotspot(f, f2);
        }
    }

    private void trackTouchEvent(MotionEvent motionEvent) {
        float f;
        float f2;
        if (this.mCurrentMode == 3) {
            trackTouchEventInVertical(motionEvent);
            return;
        }
        int round = Math.round(motionEvent.getX());
        int round2 = Math.round(motionEvent.getY());
        int width = getWidth();
        int i = (width - this.mPaddingLeft) - this.mPaddingRight;
        if (isLayoutRtl() && this.mMirrorForRtl) {
            if (round <= width - this.mPaddingRight) {
                if (round >= this.mPaddingLeft) {
                    f = (((i - round) + this.mPaddingLeft) / i) + this.mTouchThumbOffset;
                    f2 = this.mTouchProgressOffset;
                }
                f = 1.0f;
                f2 = 0.0f;
            }
            f = 0.0f;
            f2 = 0.0f;
        } else {
            if (round >= this.mPaddingLeft) {
                if (round <= width - this.mPaddingRight) {
                    f = ((round - this.mPaddingLeft) / i) + this.mTouchThumbOffset;
                    f2 = this.mTouchProgressOffset;
                }
                f = 1.0f;
                f2 = 0.0f;
            }
            f = 0.0f;
            f2 = 0.0f;
        }
        float max = getMax() - getMin();
        float f3 = 1.0f / max;
        if (f > 0.0f && f < 1.0f) {
            float f4 = f % f3;
            if (f4 > f3 / 2.0f) {
                f += f3 - f4;
            }
        }
        setHotspot(round, round2);
        setProgressInternal(Math.round(f2 + (f * max) + getMin()), true, false);
    }

    private void attemptClaimDrag() {
        if (this.mParent != null) {
            this.mParent.requestDisallowInterceptTouchEvent(true);
        }
    }

    void onStartTrackingTouch() {
        this.mIsDragging = true;
    }

    void onStopTrackingTouch() {
        this.mIsDragging = false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0020, code lost:
    
        if (r8 != 81) goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0046, code lost:
    
        if (r8 != 81) goto L38;
     */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x005c  */
    @Override // android.view.View, android.view.KeyEvent.Callback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onKeyDown(int r8, android.view.KeyEvent r9) {
        /*
            r7 = this;
            boolean r0 = r7.isEnabled()
            if (r0 == 0) goto L60
            int r0 = r7.mKeyProgressIncrement
            int r1 = r7.mCurrentMode
            r2 = 3
            r3 = 81
            r4 = 70
            r5 = 69
            r6 = 1
            if (r1 != r2) goto L3a
            r1 = 19
            if (r8 == r1) goto L24
            r1 = 20
            if (r8 == r1) goto L23
            if (r8 == r5) goto L23
            if (r8 == r4) goto L24
            if (r8 == r3) goto L24
            goto L60
        L23:
            int r0 = -r0
        L24:
            boolean r1 = r7.isLayoutRtl()
            if (r1 == 0) goto L2b
            int r0 = -r0
        L2b:
            int r1 = r7.getProgress()
            int r1 = r1 + r0
            boolean r0 = r7.setProgressInternal(r1, r6, r6)
            if (r0 == 0) goto L60
            r7.onKeyChange()
            return r6
        L3a:
            r1 = 21
            if (r8 == r1) goto L49
            r1 = 22
            if (r8 == r1) goto L4a
            if (r8 == r5) goto L49
            if (r8 == r4) goto L4a
            if (r8 == r3) goto L4a
            goto L60
        L49:
            int r0 = -r0
        L4a:
            boolean r1 = r7.isLayoutRtl()
            if (r1 == 0) goto L51
            int r0 = -r0
        L51:
            int r1 = r7.getProgress()
            int r1 = r1 + r0
            boolean r0 = r7.setProgressInternal(r1, r6, r6)
            if (r0 == 0) goto L60
            r7.onKeyChange()
            return r6
        L60:
            boolean r7 = super.onKeyDown(r8, r9)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.AbsSeekBar.onKeyDown(int, android.view.KeyEvent):boolean");
    }

    @Override // android.widget.ProgressBar, android.view.View
    public CharSequence getAccessibilityClassName() {
        return AbsSeekBar.class.getName();
    }

    @Override // android.widget.ProgressBar, android.view.View
    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        if (isEnabled()) {
            int progress = getProgress();
            if (progress > getMin()) {
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_BACKWARD);
            }
            if (progress < getMax()) {
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_FORWARD);
            }
        }
    }

    @Override // android.view.View
    public boolean performAccessibilityActionInternal(int i, Bundle bundle) {
        if (super.performAccessibilityActionInternal(i, bundle)) {
            return true;
        }
        if (!isEnabled()) {
            return false;
        }
        if (i != 4096 && i != 8192) {
            if (i == 16908349 && canUserSetProgress() && bundle != null && bundle.containsKey(AccessibilityNodeInfo.ACTION_ARGUMENT_PROGRESS_VALUE)) {
                return setProgressInternal((int) bundle.getFloat(AccessibilityNodeInfo.ACTION_ARGUMENT_PROGRESS_VALUE), true, true);
            }
            return false;
        }
        if (!canUserSetProgress()) {
            return false;
        }
        int max = Math.max(1, Math.round((getMax() - getMin()) / 20.0f));
        if (i == 8192) {
            max = -max;
        }
        if (!setProgressInternal(getProgress() + max, true, true)) {
            return false;
        }
        onKeyChange();
        return true;
    }

    boolean canUserSetProgress() {
        return !isIndeterminate() && isEnabled();
    }

    @Override // android.view.View
    public void onRtlPropertiesChanged(int i) {
        super.onRtlPropertiesChanged(i);
        Drawable drawable = this.mThumb;
        if (drawable != null) {
            setThumbPos(getWidth(), drawable, getScale(), Integer.MIN_VALUE);
            invalidate();
        }
    }

    @Deprecated
    public void semSetThumbTintColor(int i) {
        ColorStateList colorToColorStateList = colorToColorStateList(i);
        if (colorToColorStateList.equals(this.mDefaultActivatedThumbColor)) {
            return;
        }
        this.mDefaultActivatedThumbColor = colorToColorStateList;
    }

    @Override // android.widget.ProgressBar
    void onProgressRefresh(float f, boolean z, int i) {
        int i2 = (int) (10000.0f * f);
        if (this.mUseMuteAnimation && !this.mIsFirstSetProgress && !this.mIsDraggingForSliding && this.mCurrentProgressLevel != 0 && i2 == 0) {
            startMuteAnimation();
            return;
        }
        cancelMuteAnimation();
        this.mIsFirstSetProgress = false;
        this.mCurrentProgressLevel = i2;
        super.onProgressRefresh(f, z, i);
        Drawable drawable = this.mThumb;
        if (drawable != null) {
            setThumbPos(getWidth(), drawable, f, Integer.MIN_VALUE);
            invalidate();
        }
    }

    private void updateThumbAndTrackPosInVertical(int i, int i2) {
        int i3;
        int i4;
        int i5 = (i - this.mPaddingLeft) - this.mPaddingRight;
        Drawable currentDrawable = getCurrentDrawable();
        Drawable drawable = this.mThumb;
        int min = Math.min(this.mMaxWidth, i5);
        int intrinsicWidth = drawable == null ? 0 : drawable.getIntrinsicWidth();
        if (intrinsicWidth > min) {
            i4 = (i5 - intrinsicWidth) / 2;
            i3 = ((intrinsicWidth - min) / 2) + i4;
        } else {
            int i6 = (i5 - min) / 2;
            int i7 = ((min - intrinsicWidth) / 2) + i6;
            i3 = i6;
            i4 = i7;
        }
        if (currentDrawable != null) {
            currentDrawable.setBounds(i3, 0, i5 - i3, (i2 - this.mPaddingBottom) - this.mPaddingTop);
        }
        if (drawable != null) {
            setThumbPosInVertical(i2, drawable, getScale(), i4);
        }
    }

    private void setThumbPosInVertical(int i, Drawable drawable, float f, int i2) {
        int i3;
        int i4 = (i - this.mPaddingTop) - this.mPaddingBottom;
        int intrinsicHeight = drawable.getIntrinsicHeight();
        int intrinsicHeight2 = drawable.getIntrinsicHeight();
        int i5 = (i4 - intrinsicHeight2) + (this.mThumbOffset * 2);
        int i6 = (int) ((f * i5) + 0.5f);
        if (i2 == Integer.MIN_VALUE) {
            Rect bounds = drawable.getBounds();
            int i7 = bounds.left;
            i3 = bounds.right;
            i2 = i7;
        } else {
            i3 = i2 + intrinsicHeight;
        }
        int i8 = i5 - i6;
        int i9 = intrinsicHeight2 + i8;
        Drawable background = getBackground();
        if (background != null) {
            int i10 = this.mPaddingLeft;
            int i11 = this.mPaddingTop - this.mThumbOffset;
            background.setHotspotBounds(i2 + i10, i8 + i11, i10 + i3, i11 + i9);
        }
        drawable.setBounds(i2, i8, i3, i9);
        this.mThumbPosX = i8 + (intrinsicHeight / 2) + this.mPaddingLeft;
    }

    private void updateSplitProgress() {
        if (this.mCurrentMode != 4) {
            return;
        }
        Drawable drawable = this.mSplitProgress;
        Rect bounds = getCurrentDrawable().getBounds();
        if (drawable != null) {
            if (this.mMirrorForRtl && isLayoutRtl()) {
                drawable.setBounds(this.mThumbPosX, bounds.top, getWidth() - this.mPaddingRight, bounds.bottom);
            } else {
                drawable.setBounds(this.mPaddingLeft, bounds.top, this.mThumbPosX, bounds.bottom);
            }
        }
        int width = getWidth();
        int height = getHeight();
        Drawable drawable2 = this.mDivider;
        if (drawable2 != null) {
            float f = width / 2.0f;
            float f2 = height / 2.0f;
            drawable2.setBounds((int) (f - ((this.mDensity * 4.0f) / 2.0f)), (int) (f2 - ((this.mDensity * 22.0f) / 2.0f)), (int) (f + ((this.mDensity * 4.0f) / 2.0f)), (int) (f2 + ((this.mDensity * 22.0f) / 2.0f)));
        }
    }

    private void trackTouchEventInVertical(MotionEvent motionEvent) {
        float f;
        float f2;
        int height = getHeight();
        int i = (height - this.mPaddingTop) - this.mPaddingBottom;
        int round = Math.round(motionEvent.getX());
        int round2 = height - Math.round(motionEvent.getY());
        if (round2 < this.mPaddingBottom) {
            f = 0.0f;
            f2 = 0.0f;
        } else if (round2 > height - this.mPaddingTop) {
            f = 1.0f;
            f2 = 0.0f;
        } else {
            f = (round2 - this.mPaddingBottom) / i;
            f2 = this.mTouchProgressOffset;
        }
        float max = getMax() - getMin();
        float f3 = 1.0f / max;
        if (f > 0.0f && f < 1.0f) {
            float f4 = f % f3;
            if (f4 > f3 / 2.0f) {
                f += f3 - f4;
            }
        }
        setHotspot(round, round2);
        setProgressInternal(Math.round(f2 + (f * max) + getMin()), true, false);
    }

    @Override // android.widget.ProgressBar
    public boolean setProgressInternal(int i, boolean z, boolean z2) {
        boolean progressInternal = super.setProgressInternal(i, z, z2);
        updateWarningMode(i);
        updateDualColorMode();
        return progressInternal;
    }

    private void trackHoverEvent(int i) {
        float f;
        int width = getWidth();
        int i2 = (width - this.mPaddingLeft) - this.mPaddingRight;
        float f2 = 0.0f;
        if (i < this.mPaddingLeft) {
            f = 0.0f;
        } else if (i > width - this.mPaddingRight) {
            f = 1.0f;
        } else {
            f2 = this.mTouchProgressOffset;
            f = (i - this.mPaddingLeft) / i2;
        }
        this.mHoveringLevel = (int) (f2 + (f * getMax()));
    }

    @Override // android.view.View
    public void semSetHoverPopupType(int i) {
        if (isHoveringUIEnabled()) {
            if (i == 3) {
                semGetHoverPopup(true).setGravity(12849);
                semGetHoverPopup(true).setOffset(0, getMeasuredHeight() / 2);
                semGetHoverPopup(true).setHoverDetectTime(200);
            }
            super.semSetHoverPopupType(i);
        }
    }

    @Override // android.view.View
    public boolean onHoverEvent(MotionEvent motionEvent) {
        int toolType = motionEvent.getToolType(0);
        if ((toolType == 2 || toolType == 1 || toolType == 3) && isHoveringUIEnabled()) {
            int action = motionEvent.getAction();
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();
            if (action == 7) {
                trackHoverEvent(x);
                onHoverChanged(this.mHoveringLevel, x, y);
                if (this.mHoverPopupType == 3) {
                    semGetHoverPopup(true).update();
                }
            } else if (action == 9) {
                trackHoverEvent(x);
                onStartTrackingHover(this.mHoveringLevel, x, y);
            } else if (action == 10) {
                onStopTrackingHover();
            }
        }
        return super.onHoverEvent(motionEvent);
    }

    @Override // android.widget.ProgressBar
    public void setProgressDrawable(Drawable drawable) {
        super.setProgressDrawable(drawable);
    }

    public Rect semGetThumbBounds() {
        Drawable drawable = this.mThumb;
        if (drawable != null) {
            return drawable.getBounds();
        }
        return null;
    }

    public int getThumbHeight() {
        return this.mThumb.getIntrinsicHeight();
    }

    @Override // android.widget.ProgressBar
    public void semSetMode(int i) {
        if (this.mCurrentMode == i) {
            return;
        }
        super.semSetMode(i);
        if (i == 0) {
            setProgressTintList(this.mDefaultActivatedProgressColor);
            setThumbTintList(this.mDefaultActivatedThumbColor);
        } else if (i == 1) {
            updateWarningMode(getProgress());
        } else if (i == 3) {
            setThumb(this.mContext.getDrawable(R.drawable.tw_scrubber_control_vertical_material_anim));
        } else if (i == 4) {
            this.mSplitProgress = this.mContext.getDrawable(R.drawable.tw_split_seekbar_primary_progress_material);
            this.mDivider = this.mContext.getDrawable(R.drawable.tw_split_seekbar_vertical_bar_material);
            updateSplitProgress();
        } else if (i == 5) {
            initializeExpandMode();
        }
        drawableStateChanged();
        invalidate();
    }

    private void initializeExpandMode() {
        SliderDrawable sliderDrawable = new SliderDrawable(this, this.mModeExpandTrackMinWidth, this.mModeExpandTrackMaxWidth, this.mDefaultNormalProgressColor);
        SliderDrawable sliderDrawable2 = new SliderDrawable(this, this.mModeExpandTrackMinWidth, this.mModeExpandTrackMaxWidth, this.mDefaultSecondaryProgressColor);
        SliderDrawable sliderDrawable3 = new SliderDrawable(this, this.mModeExpandTrackMinWidth, this.mModeExpandTrackMaxWidth, this.mDefaultActivatedProgressColor);
        Drawable thumbDrawable = new ThumbDrawable(this.mModeExpandThumbRadius, this.mDefaultActivatedThumbColor, false);
        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{sliderDrawable, new ClipDrawable(sliderDrawable2, 19, 1), new ClipDrawable(sliderDrawable3, 19, 1)});
        layerDrawable.setPaddingMode(1);
        layerDrawable.setId(0, 16908288);
        layerDrawable.setId(1, 16908303);
        layerDrawable.setId(2, 16908301);
        setProgressDrawable(layerDrawable);
        setThumb(thumbDrawable);
        setBackgroundResource(R.drawable.sem_seekbar_background_borderless);
        int maxHeight = getMaxHeight();
        int i = this.mModeExpandTrackMaxWidth;
        if (maxHeight > i) {
            setMaxHeight(i);
        }
    }

    public void setSplitProgressDrawable(Drawable drawable) {
        if (this.mSplitProgress == null) {
            return;
        }
        this.mSplitProgress = drawable;
    }

    public void setDividerDrawable(Drawable drawable) {
        if (this.mDivider == null) {
            return;
        }
        this.mDivider = drawable;
    }

    public void setOverlapBackgroundForDualColor(int i) {
        ColorStateList colorToColorStateList = colorToColorStateList(i);
        if (!colorToColorStateList.equals(this.mOverlapNormalProgressColor)) {
            this.mOverlapNormalProgressColor = colorToColorStateList;
        }
        this.mOverlapActivatedProgressColor = this.mOverlapNormalProgressColor;
        this.mLargeFont = true;
    }

    public void semSetOverlapPointForDualColor(int i) {
        if (i >= getMax()) {
            return;
        }
        this.mSetDualColorMode = true;
        this.mOverlapPoint = i;
        if (i == -1) {
            setProgressTintList(this.mDefaultActivatedProgressColor);
            setThumbTintList(this.mDefaultActivatedThumbColor);
        } else {
            if (this.mOverlapBackground == null) {
                initDualOverlapDrawable();
            }
            updateDualColorMode();
        }
        invalidate();
    }

    private void updateDualColorMode() {
        if (checkInvalidatedDualColorMode()) {
            return;
        }
        this.mOverlapBackground.setTintList(this.mOverlapNormalProgressColor);
        if (!this.mLargeFont) {
            if (getProgress() > this.mOverlapPoint) {
                setProgressOverlapTintList(this.mOverlapActivatedProgressColor);
                if (isEnabled()) {
                    setThumbOverlapTintList(this.mOverlapActivatedThumbColor);
                }
            } else {
                setProgressTintList(this.mDefaultActivatedProgressColor);
                if (isEnabled()) {
                    setThumbTintList(this.mDefaultActivatedThumbColor);
                }
            }
        }
        updateBoundsForDualColor();
    }

    private void updateBoundsForDualColor() {
        if (getCurrentDrawable() == null || checkInvalidatedDualColorMode()) {
            return;
        }
        this.mOverlapBackground.setBounds(getCurrentDrawable().getBounds());
    }

    public void setDualModeOverlapColor(int i, int i2) {
        ColorStateList colorToColorStateList = colorToColorStateList(i);
        ColorStateList colorToColorStateList2 = colorToColorStateList(i2);
        if (!colorToColorStateList.equals(this.mOverlapNormalProgressColor)) {
            this.mOverlapNormalProgressColor = colorToColorStateList;
        }
        if (!colorToColorStateList2.equals(this.mOverlapActivatedProgressColor)) {
            this.mOverlapActivatedProgressColor = colorToColorStateList2;
        }
        updateDualColorMode();
        invalidate();
    }

    private boolean checkInvalidatedDualColorMode() {
        return this.mOverlapPoint == -1 || this.mOverlapBackground == null;
    }

    private void initDualOverlapDrawable() {
        if (this.mCurrentMode == 5) {
            this.mOverlapBackground = new SliderDrawable(this, this.mModeExpandTrackMinWidth, this.mModeExpandTrackMaxWidth, this.mOverlapNormalProgressColor);
        } else {
            if (getProgressDrawable() == null || getProgressDrawable().getConstantState() == null) {
                return;
            }
            this.mOverlapBackground = getProgressDrawable().getConstantState().newDrawable().mutate();
        }
    }

    @Override // android.widget.ProgressBar
    protected void updateDrawableBounds(int i, int i2) {
        super.updateDrawableBounds(i, i2);
        updateThumbAndTrackPos(i, i2);
        updateBoundsForDualColor();
    }

    private ColorStateList colorToColorStateList(int i) {
        return new ColorStateList(new int[][]{new int[0]}, new int[]{i});
    }

    private void updateWarningMode(int i) {
        if (this.mCurrentMode == 1) {
            if (i == getMax()) {
                setProgressOverlapTintList(this.mOverlapActivatedProgressColor);
                setThumbOverlapTintList(this.mOverlapActivatedThumbColor);
            } else {
                setProgressTintList(this.mDefaultActivatedProgressColor);
                setThumbTintList(this.mDefaultActivatedThumbColor);
            }
        }
    }

    private void initMuteAnimation() {
        ValueAnimator ofInt;
        this.mMuteAnimationSet = new AnimatorSet();
        ArrayList arrayList = new ArrayList();
        int i = 400;
        for (int i2 = 0; i2 < 8; i2++) {
            boolean z = i2 % 2 == 0;
            if (z) {
                ofInt = ValueAnimator.ofInt(0, i);
            } else {
                ofInt = ValueAnimator.ofInt(i, 0);
            }
            ofInt.setDuration(62);
            ofInt.setInterpolator(new LinearInterpolator());
            ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: android.widget.AbsSeekBar.1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    AbsSeekBar.this.mCurrentProgressLevel = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                    AbsSeekBar absSeekBar = AbsSeekBar.this;
                    absSeekBar.onSlidingRefresh(absSeekBar.mCurrentProgressLevel);
                }
            });
            arrayList.add(ofInt);
            if (z) {
                i = (int) (i * 0.6d);
            }
        }
        this.mMuteAnimationSet.playSequentially(arrayList);
    }

    private void cancelMuteAnimation() {
        AnimatorSet animatorSet = this.mMuteAnimationSet;
        if (animatorSet == null || !animatorSet.isRunning()) {
            return;
        }
        this.mMuteAnimationSet.cancel();
    }

    private void startMuteAnimation() {
        cancelMuteAnimation();
        AnimatorSet animatorSet = this.mMuteAnimationSet;
        if (animatorSet != null) {
            animatorSet.start();
        }
    }

    public void setMuteAnimation(boolean z) {
        if (this.mAllowedSeekBarAnimation) {
            this.mUseMuteAnimation = z;
        }
    }

    @Override // android.widget.ProgressBar
    protected void onSlidingRefresh(int i) {
        super.onSlidingRefresh(i);
        float f = i / 10000.0f;
        Drawable drawable = this.mThumb;
        if (drawable != null) {
            setThumbPos(getWidth(), drawable, f, Integer.MIN_VALUE);
            invalidate();
        }
    }

    public void setDefaultColorForVolumePanel(boolean z) {
        if (z) {
            this.mDefaultNormalProgressColor = colorToColorStateList(Color.parseColor("#ffe3e0e0"));
            this.mDefaultActivatedProgressColor = colorToColorStateList(Color.parseColor("#ff56c0e5"));
            this.mDefaultActivatedThumbColor = colorToColorStateList(Color.parseColor("#ff56c0e5"));
            this.mOverlapNormalProgressColor = colorToColorStateList(Color.parseColor("#fff7cdbd"));
            this.mOverlapActivatedProgressColor = colorToColorStateList(Color.parseColor("#fff1662f"));
            this.mOverlapActivatedThumbColor = colorToColorStateList(Color.parseColor("#fff1662f"));
            return;
        }
        Resources resources = this.mContext.getResources();
        this.mDefaultNormalProgressColor = colorToColorStateList(this.mIsDeviceDefaultDark ? R.color.tw_seekbar_color_control_normal_dark : R.color.tw_seekbar_color_control_normal_light);
        this.mDefaultActivatedProgressColor = colorToColorStateList(resources.getColor(this.mIsDeviceDefaultDark ? R.color.tw_seekbar_color_control_activated_dark : R.color.tw_seekbar_color_control_activated_light, null));
        this.mDefaultActivatedThumbColor = colorToColorStateList(resources.getColor(this.mIsDeviceDefaultDark ? R.color.tw_thumb_color_control_activated_dark : R.color.tw_thumb_color_control_activated_light, null));
        this.mOverlapNormalProgressColor = colorToColorStateList(resources.getColor(R.color.tw_seekbar_color_overlap_normal_light, null));
        boolean z2 = this.mIsDeviceDefaultDark;
        int i = R.color.tw_seekbar_color_overlap_activated_light;
        this.mOverlapActivatedProgressColor = colorToColorStateList(resources.getColor(z2 ? 17171794 : 17171793, null));
        if (!this.mIsDeviceDefaultDark) {
            i = 17171793;
        }
        this.mOverlapActivatedThumbColor = colorToColorStateList(resources.getColor(i, null));
    }

    private void setThumbOverlapTintList(ColorStateList colorStateList) {
        this.mThumbTintList = colorStateList;
        this.mHasThumbTint = true;
        applyThumbTint();
    }

    @Override // android.widget.ProgressBar
    public void setProgressTintList(ColorStateList colorStateList) {
        super.setProgressTintList(colorStateList);
        this.mDefaultActivatedProgressColor = colorStateList;
    }

    private void setProgressOverlapTintList(ColorStateList colorStateList) {
        super.setProgressTintList(colorStateList);
    }

    public void setTouchDisabled(boolean z) {
        this.mIsTouchDisabled = z;
    }

    private class SliderDrawable extends Drawable {
        private static final int ANIMATION_DURATION = 250;
        private final Interpolator SINE_IN_OUT_80;
        int mAlpha;
        int mColor;
        ColorStateList mColorStateList;
        private boolean mIsStateChanged;
        private boolean mIsVertical;
        private final Paint mPaint;
        ValueAnimator mPressedAnimator;
        private float mRadius;
        ValueAnimator mReleasedAnimator;
        private final float mSliderMaxWidth;
        private final float mSliderMinWidth;
        private final SliderState mState;

        private int modulateAlpha(int i, int i2) {
            return (i * (i2 + (i2 >>> 7))) >>> 8;
        }

        @Override // android.graphics.drawable.Drawable
        public boolean isStateful() {
            return true;
        }

        public SliderDrawable(AbsSeekBar absSeekBar, float f, float f2, ColorStateList colorStateList) {
            this(f, f2, colorStateList, false);
        }

        public SliderDrawable(float f, float f2, ColorStateList colorStateList, boolean z) {
            this.SINE_IN_OUT_80 = new PathInterpolator(0.33f, 0.0f, 0.2f, 1.0f);
            Paint paint = new Paint();
            this.mPaint = paint;
            this.mIsStateChanged = false;
            this.mAlpha = 255;
            this.mState = new SliderState();
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeCap(Paint.Cap.ROUND);
            this.mColorStateList = colorStateList;
            int defaultColor = colorStateList.getDefaultColor();
            this.mColor = defaultColor;
            paint.setColor(defaultColor);
            paint.setStrokeWidth(f);
            this.mSliderMinWidth = f;
            this.mSliderMaxWidth = f2;
            this.mRadius = f / 2.0f;
            this.mIsVertical = z;
            initAnimator();
        }

        private void initAnimator() {
            float f = this.mSliderMinWidth;
            float f2 = this.mSliderMaxWidth;
            ValueAnimator ofFloat = ValueAnimator.ofFloat(f, f2);
            this.mPressedAnimator = ofFloat;
            ofFloat.setDuration(250L);
            this.mPressedAnimator.setInterpolator(this.SINE_IN_OUT_80);
            this.mPressedAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: android.widget.AbsSeekBar.SliderDrawable.1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    SliderDrawable.this.invalidateTrack(((Float) valueAnimator.getAnimatedValue()).floatValue());
                }
            });
            ValueAnimator ofFloat2 = ValueAnimator.ofFloat(f2, f);
            this.mReleasedAnimator = ofFloat2;
            ofFloat2.setDuration(250L);
            this.mReleasedAnimator.setInterpolator(this.SINE_IN_OUT_80);
            this.mReleasedAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: android.widget.AbsSeekBar.SliderDrawable.2
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    SliderDrawable.this.invalidateTrack(((Float) valueAnimator.getAnimatedValue()).floatValue());
                }
            });
        }

        @Override // android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            int alpha = this.mPaint.getAlpha();
            this.mPaint.setAlpha(modulateAlpha(alpha, this.mAlpha));
            canvas.save();
            if (!this.mIsVertical) {
                float width = (AbsSeekBar.this.getWidth() - AbsSeekBar.this.mPaddingLeft) - AbsSeekBar.this.mPaddingRight;
                float f = this.mRadius;
                canvas.drawLine(f, AbsSeekBar.this.getHeight() / 2.0f, width - f, AbsSeekBar.this.getHeight() / 2.0f, this.mPaint);
            } else {
                canvas.drawLine(AbsSeekBar.this.getWidth() / 2.0f, ((AbsSeekBar.this.getHeight() - AbsSeekBar.this.getPaddingTop()) - AbsSeekBar.this.getPaddingBottom()) - this.mRadius, AbsSeekBar.this.getWidth() / 2.0f, this.mRadius, this.mPaint);
            }
            canvas.restore();
            this.mPaint.setAlpha(alpha);
        }

        @Override // android.graphics.drawable.Drawable
        public void setAlpha(int i) {
            this.mAlpha = i;
            invalidateSelf();
        }

        @Override // android.graphics.drawable.Drawable
        public void setColorFilter(ColorFilter colorFilter) {
            this.mPaint.setColorFilter(colorFilter);
            invalidateSelf();
        }

        @Override // android.graphics.drawable.Drawable
        public int getOpacity() {
            Paint paint = this.mPaint;
            if (paint.getXfermode() != null) {
                return -3;
            }
            int alpha = paint.getAlpha();
            if (alpha == 0) {
                return -2;
            }
            return alpha == 255 ? -1 : -3;
        }

        @Override // android.graphics.drawable.Drawable
        public void setTintList(ColorStateList colorStateList) {
            super.setTintList(colorStateList);
            if (colorStateList != null) {
                this.mColorStateList = colorStateList;
                int colorForState = colorStateList.getColorForState(AbsSeekBar.this.getDrawableState(), this.mColor);
                this.mColor = colorForState;
                this.mPaint.setColor(colorForState);
                invalidateSelf();
            }
        }

        @Override // android.graphics.drawable.Drawable
        protected boolean onStateChange(int[] iArr) {
            boolean onStateChange = super.onStateChange(iArr);
            int colorForState = this.mColorStateList.getColorForState(iArr, this.mColor);
            if (this.mColor != colorForState) {
                this.mColor = colorForState;
                this.mPaint.setColor(colorForState);
                invalidateSelf();
            }
            boolean z = false;
            boolean z2 = false;
            boolean z3 = false;
            for (int i : iArr) {
                if (i == 16842910) {
                    z2 = true;
                } else if (i == 16842919) {
                    z3 = true;
                }
            }
            if (z2 && z3) {
                z = true;
            }
            startSliderAnimation(z);
            return onStateChange;
        }

        @Override // android.graphics.drawable.Drawable
        public int getIntrinsicWidth() {
            return (int) this.mSliderMaxWidth;
        }

        @Override // android.graphics.drawable.Drawable
        public int getIntrinsicHeight() {
            return (int) this.mSliderMaxWidth;
        }

        public void setStrokeWidth(float f) {
            this.mPaint.setStrokeWidth(f);
            this.mRadius = f / 2.0f;
        }

        private void startSliderAnimation(boolean z) {
            if (this.mIsStateChanged != z) {
                if (z) {
                    startPressedAnimation();
                } else {
                    startReleasedAnimation();
                }
                this.mIsStateChanged = z;
            }
        }

        private void startPressedAnimation() {
            if (this.mPressedAnimator.isRunning()) {
                return;
            }
            if (this.mReleasedAnimator.isRunning()) {
                this.mReleasedAnimator.cancel();
            }
            this.mPressedAnimator.setFloatValues(this.mSliderMinWidth, this.mSliderMaxWidth);
            this.mPressedAnimator.start();
        }

        private void startReleasedAnimation() {
            if (this.mReleasedAnimator.isRunning()) {
                return;
            }
            if (this.mPressedAnimator.isRunning()) {
                this.mPressedAnimator.cancel();
            }
            this.mReleasedAnimator.setFloatValues(this.mSliderMaxWidth, this.mSliderMinWidth);
            this.mReleasedAnimator.start();
        }

        void invalidateTrack(float f) {
            setStrokeWidth(f);
            invalidateSelf();
        }

        @Override // android.graphics.drawable.Drawable
        public Drawable.ConstantState getConstantState() {
            return this.mState;
        }

        private class SliderState extends Drawable.ConstantState {
            @Override // android.graphics.drawable.Drawable.ConstantState
            public int getChangingConfigurations() {
                return 0;
            }

            private SliderState() {
            }

            @Override // android.graphics.drawable.Drawable.ConstantState
            public Drawable newDrawable() {
                return SliderDrawable.this;
            }
        }
    }

    private class ThumbDrawable extends Drawable {
        private static final int PRESSED_DURATION = 100;
        private static final int RELEASED_DURATION = 300;
        private final Interpolator SINE_IN_OUT_90 = new PathInterpolator(0.33f, 0.0f, 0.1f, 1.0f);
        private int mAlpha;
        int mColor;
        private ColorStateList mColorStateList;
        private boolean mIsStateChanged;
        private boolean mIsVertical;
        private final Paint mPaint;
        private final Paint mPaintFill;
        private final int mRadius;
        private int mRadiusForAni;
        private ValueAnimator mThumbPressed;
        private ValueAnimator mThumbReleased;

        private int modulateAlpha(int i, int i2) {
            return (i * (i2 + (i2 >>> 7))) >>> 8;
        }

        @Override // android.graphics.drawable.Drawable
        public boolean isStateful() {
            return true;
        }

        public ThumbDrawable(int i, ColorStateList colorStateList, boolean z) {
            Paint paint = new Paint(1);
            this.mPaint = paint;
            Paint paint2 = new Paint(1);
            this.mPaintFill = paint2;
            this.mIsStateChanged = false;
            this.mAlpha = 255;
            this.mIsVertical = false;
            this.mRadiusForAni = i;
            this.mRadius = i;
            this.mColorStateList = colorStateList;
            this.mColor = colorStateList.getDefaultColor();
            paint.setStyle(Paint.Style.STROKE);
            paint2.setStyle(Paint.Style.FILL);
            paint.setColor(this.mColor);
            paint.setStrokeWidth(AbsSeekBar.this.mContext.getResources().getDimension(R.dimen.sem_seekbar_thumb_stroke));
            paint2.setColor(AbsSeekBar.this.mContext.getResources().getColor(R.color.tw_thumb_control_fill_color_activated));
            this.mIsVertical = z;
            initAnimation();
        }

        void initAnimation() {
            ValueAnimator ofFloat = ValueAnimator.ofFloat(this.mRadius, 0.0f);
            this.mThumbPressed = ofFloat;
            ofFloat.setDuration(100L);
            this.mThumbPressed.setInterpolator(new LinearInterpolator());
            this.mThumbPressed.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: android.widget.AbsSeekBar.ThumbDrawable.1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    ThumbDrawable.this.setRadius((int) ((Float) valueAnimator.getAnimatedValue()).floatValue());
                    ThumbDrawable.this.invalidateSelf();
                }
            });
            ValueAnimator ofFloat2 = ValueAnimator.ofFloat(0.0f, this.mRadius);
            this.mThumbReleased = ofFloat2;
            ofFloat2.setDuration(300L);
            this.mThumbReleased.setInterpolator(this.SINE_IN_OUT_90);
            this.mThumbReleased.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: android.widget.AbsSeekBar.ThumbDrawable.2
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    ThumbDrawable.this.setRadius((int) ((Float) valueAnimator.getAnimatedValue()).floatValue());
                    ThumbDrawable.this.invalidateSelf();
                }
            });
        }

        @Override // android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            int alpha = this.mPaint.getAlpha();
            this.mPaint.setAlpha(modulateAlpha(alpha, this.mAlpha));
            this.mPaintFill.setAlpha(modulateAlpha(alpha, this.mAlpha));
            canvas.save();
            if (!this.mIsVertical) {
                canvas.drawCircle(AbsSeekBar.this.mThumbPosX, AbsSeekBar.this.getHeight() / 2.0f, this.mRadiusForAni, this.mPaintFill);
                canvas.drawCircle(AbsSeekBar.this.mThumbPosX, AbsSeekBar.this.getHeight() / 2.0f, this.mRadiusForAni, this.mPaint);
            } else {
                canvas.drawCircle(AbsSeekBar.this.getWidth() / 2.0f, AbsSeekBar.this.mThumbPosX, this.mRadiusForAni, this.mPaintFill);
                canvas.drawCircle(AbsSeekBar.this.getWidth() / 2.0f, AbsSeekBar.this.mThumbPosX, this.mRadiusForAni, this.mPaint);
            }
            canvas.restore();
            this.mPaint.setAlpha(alpha);
            this.mPaintFill.setAlpha(alpha);
        }

        @Override // android.graphics.drawable.Drawable
        public int getIntrinsicWidth() {
            return this.mRadius * 2;
        }

        @Override // android.graphics.drawable.Drawable
        public int getIntrinsicHeight() {
            return this.mRadius * 2;
        }

        @Override // android.graphics.drawable.Drawable
        public void setTintList(ColorStateList colorStateList) {
            super.setTintList(colorStateList);
            if (colorStateList != null) {
                this.mColorStateList = colorStateList;
                int colorForState = colorStateList.getColorForState(AbsSeekBar.this.getDrawableState(), this.mColor);
                this.mColor = colorForState;
                this.mPaint.setColor(colorForState);
                invalidateSelf();
            }
        }

        @Override // android.graphics.drawable.Drawable
        protected boolean onStateChange(int[] iArr) {
            boolean onStateChange = super.onStateChange(iArr);
            int colorForState = this.mColorStateList.getColorForState(iArr, this.mColor);
            if (this.mColor != colorForState) {
                this.mColor = colorForState;
                this.mPaint.setColor(colorForState);
                invalidateSelf();
            }
            boolean z = false;
            boolean z2 = false;
            boolean z3 = false;
            for (int i : iArr) {
                if (i == 16842910) {
                    z2 = true;
                } else if (i == 16842919) {
                    z3 = true;
                }
            }
            if (z2 && z3) {
                z = true;
            }
            startThumbAnimation(z);
            return onStateChange;
        }

        private void startThumbAnimation(boolean z) {
            if (this.mIsStateChanged != z) {
                if (z) {
                    startPressedAnimation();
                } else {
                    startReleasedAnimation();
                }
                this.mIsStateChanged = z;
            }
        }

        private void startPressedAnimation() {
            if (this.mThumbPressed.isRunning()) {
                return;
            }
            if (this.mThumbReleased.isRunning()) {
                this.mThumbReleased.cancel();
            }
            this.mThumbPressed.start();
        }

        private void startReleasedAnimation() {
            if (this.mThumbReleased.isRunning()) {
                return;
            }
            if (this.mThumbPressed.isRunning()) {
                this.mThumbPressed.cancel();
            }
            this.mThumbReleased.start();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setRadius(int i) {
            this.mRadiusForAni = i;
        }

        @Override // android.graphics.drawable.Drawable
        public void setAlpha(int i) {
            this.mAlpha = i;
            invalidateSelf();
        }

        @Override // android.graphics.drawable.Drawable
        public void setColorFilter(ColorFilter colorFilter) {
            this.mPaint.setColorFilter(colorFilter);
            invalidateSelf();
        }

        @Override // android.graphics.drawable.Drawable
        public int getOpacity() {
            Paint paint = this.mPaint;
            if (paint.getXfermode() != null) {
                return -3;
            }
            int alpha = paint.getAlpha();
            if (alpha == 0) {
                return -2;
            }
            return alpha == 255 ? -1 : -3;
        }
    }
}
