package android.widget;

import android.animation.Animator;
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

/* loaded from: classes4.dex */
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

    public final class InspectionCompanion
            implements android.view.inspector.InspectionCompanion<AbsSeekBar> {
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
        public void readProperties(AbsSeekBar node, PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readObject(this.mThumbTintId, node.getThumbTintList());
            propertyReader.readObject(this.mThumbTintModeId, node.getThumbTintMode());
            propertyReader.readObject(this.mTickMarkTintId, node.getTickMarkTintList());
            propertyReader.readObject(
                    this.mTickMarkTintBlendModeId, node.getTickMarkTintBlendMode());
            propertyReader.readObject(this.mTickMarkTintModeId, node.getTickMarkTintMode());
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
        this.mUserGestureExclusionRects = Collections.emptyList();
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

    public AbsSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
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
        this.mUserGestureExclusionRects = Collections.emptyList();
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

    public AbsSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public AbsSeekBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
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
        this.mUserGestureExclusionRects = Collections.emptyList();
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
        TypedArray a =
                context.obtainStyledAttributes(
                        attrs, R.styleable.SeekBar, defStyleAttr, defStyleRes);
        saveAttributeDataForStyleable(
                context, R.styleable.SeekBar, attrs, a, defStyleAttr, defStyleRes);
        Drawable thumb = a.getDrawable(0);
        setThumb(thumb);
        if (a.hasValue(4)) {
            this.mThumbBlendMode = Drawable.parseBlendMode(a.getInt(4, -1), this.mThumbBlendMode);
            this.mHasThumbBlendMode = true;
        }
        if (a.hasValue(3)) {
            this.mThumbTintList = a.getColorStateList(3);
            this.mHasThumbTint = true;
        }
        Drawable tickMark = a.getDrawable(5);
        setTickMark(tickMark);
        if (a.hasValue(7)) {
            this.mTickMarkBlendMode =
                    Drawable.parseBlendMode(a.getInt(7, -1), this.mTickMarkBlendMode);
            this.mHasTickMarkBlendMode = true;
        }
        if (a.hasValue(6)) {
            this.mTickMarkTintList = a.getColorStateList(6);
            this.mHasTickMarkTint = true;
        }
        this.mSplitTrack = a.getBoolean(2, false);
        int thumbOffset = a.getDimensionPixelOffset(1, getThumbOffset());
        setThumbOffset(thumbOffset);
        boolean useDisabledAlpha = a.getBoolean(8, true);
        a.recycle();
        if (useDisabledAlpha) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Theme, 0, 0);
            this.mDisabledAlpha = ta.getFloat(3, 0.4f);
            ta.recycle();
        } else {
            this.mDisabledAlpha = 1.0f;
        }
        applyThumbTint();
        applyTickMarkTint();
        this.mScaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.mThumbExclusionMaxSize =
                getResources().getDimensionPixelSize(R.dimen.seekbar_thumb_exclusion_max_size);
        Resources res = context.getResources();
        this.mTrackMinWidth = res.getDimensionPixelSize(R.dimen.sem_seekbar_track_height);
        this.mTrackMaxWidth = res.getDimensionPixelSize(R.dimen.sem_seekbar_track_height_expand);
        this.mModeExpandTrackMinWidth =
                res.getDimensionPixelSize(R.dimen.sem_seekbar_mode_expand_track_height);
        this.mModeExpandTrackMaxWidth =
                res.getDimensionPixelSize(R.dimen.sem_seekbar_mode_expand_track_height_expand);
        this.mThumbRadius = res.getDimensionPixelSize(R.dimen.sem_seekbar_thumb_radius);
        this.mModeExpandThumbRadius =
                res.getDimensionPixelSize(R.dimen.sem_seekbar_mode_expand_thumb_radius);
        if (this.mIsDeviceDefaultDark) {
            i = R.color.tw_seekbar_color_control_normal_dark;
        } else {
            i = R.color.tw_seekbar_color_control_normal_light;
        }
        this.mDefaultNormalProgressColor = colorToColorStateList(res.getColor(i, null));
        this.mDefaultSecondaryProgressColor =
                colorToColorStateList(
                        res.getColor(R.color.tw_seekbar_color_control_secondary, null));
        this.mDefaultActivatedProgressColor = semGetProgressTintList();
        if (this.mDefaultActivatedProgressColor == null) {
            if (this.mIsDeviceDefaultDark) {
                i7 = R.color.tw_seekbar_color_control_activated_dark;
            } else {
                i7 = R.color.tw_seekbar_color_control_activated_light;
            }
            this.mDefaultActivatedProgressColor = colorToColorStateList(res.getColor(i7, null));
        }
        if (this.mIsDeviceDefaultDark) {
            i2 = R.color.tw_seekbar_color_overlap_normal_dark;
        } else {
            i2 = R.color.tw_seekbar_color_overlap_normal_light;
        }
        this.mOverlapNormalProgressColor = colorToColorStateList(res.getColor(i2, null));
        boolean z = this.mIsDeviceDefaultDark;
        int i8 = R.color.tw_seekbar_color_overlap_activated_dark;
        if (z) {
            i3 = 17171699;
        } else {
            i3 = 17171700;
        }
        this.mOverlapActivatedProgressColor = colorToColorStateList(res.getColor(i3, null));
        int[][] states = {new int[] {16842910}, new int[] {-16842910}};
        int color = res.getColor(this.mIsDeviceDefaultDark ? i8 : 17171700);
        if (this.mIsDeviceDefaultDark) {
            i4 = R.color.tw_seekbar_disable_color_activated_dark;
        } else {
            i4 = 17171704;
        }
        int[] colors = {color, res.getColor(i4)};
        this.mOverlapActivatedThumbColor = new ColorStateList(states, colors);
        this.mDefaultActivatedThumbColor = this.mThumbTintList;
        if (this.mDefaultActivatedThumbColor == null) {
            if (this.mIsDeviceDefaultDark) {
                i5 = R.color.tw_thumb_color_control_activated_dark;
            } else {
                i5 = R.color.tw_thumb_color_control_activated_light;
            }
            int color2 = res.getColor(i5);
            if (this.mIsDeviceDefaultDark) {
                i6 = R.color.tw_seekbar_disable_color_activated_dark;
            } else {
                i6 = 17171704;
            }
            int[] colors2 = {color2, res.getColor(i6)};
            this.mDefaultActivatedThumbColor = new ColorStateList(states, colors2);
        }
        this.mAllowedSeekBarAnimation = res.getBoolean(R.bool.tw_seekbar_sliding_animation);
        if (this.mAllowedSeekBarAnimation) {
            initMuteAnimation();
        }
    }

    public void setThumb(Drawable thumb) {
        boolean needUpdate;
        if (this.mThumb != null && thumb != this.mThumb) {
            this.mThumb.setCallback(null);
            needUpdate = true;
        } else {
            needUpdate = false;
        }
        if (thumb != null) {
            thumb.setCallback(this);
            if (canResolveLayoutDirection()) {
                thumb.setLayoutDirection(getLayoutDirection());
            }
            if (this.mCurrentMode == 3) {
                this.mThumbOffset = thumb.getIntrinsicHeight() / 2;
            } else {
                this.mThumbOffset = thumb.getIntrinsicWidth() / 2;
            }
            if (needUpdate
                    && (thumb.getIntrinsicWidth() != this.mThumb.getIntrinsicWidth()
                            || thumb.getIntrinsicHeight() != this.mThumb.getIntrinsicHeight())) {
                requestLayout();
            }
        }
        this.mThumb = thumb;
        applyThumbTint();
        invalidate();
        if (needUpdate) {
            updateThumbAndTrackPos(getWidth(), getHeight());
            if (thumb != null && thumb.isStateful()) {
                int[] state = getDrawableState();
                thumb.setState(state);
            }
        }
    }

    public Drawable getThumb() {
        return this.mThumb;
    }

    public void setThumbTintList(ColorStateList tint) {
        this.mThumbTintList = tint;
        this.mHasThumbTint = true;
        applyThumbTint();
        this.mDefaultActivatedThumbColor = tint;
    }

    public ColorStateList getThumbTintList() {
        return this.mThumbTintList;
    }

    public void setThumbTintMode(PorterDuff.Mode tintMode) {
        setThumbTintBlendMode(tintMode != null ? BlendMode.fromValue(tintMode.nativeInt) : null);
    }

    public void setThumbTintBlendMode(BlendMode blendMode) {
        this.mThumbBlendMode = blendMode;
        this.mHasThumbBlendMode = true;
        applyThumbTint();
    }

    public PorterDuff.Mode getThumbTintMode() {
        if (this.mThumbBlendMode != null) {
            return BlendMode.blendModeToPorterDuffMode(this.mThumbBlendMode);
        }
        return null;
    }

    public BlendMode getThumbTintBlendMode() {
        return this.mThumbBlendMode;
    }

    private void applyThumbTint() {
        if (this.mThumb != null) {
            if (this.mHasThumbTint || this.mHasThumbBlendMode) {
                this.mThumb = this.mThumb.mutate();
                if (this.mHasThumbTint) {
                    this.mThumb.setTintList(this.mThumbTintList);
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

    public void setThumbOffset(int thumbOffset) {
        this.mThumbOffset = thumbOffset;
        invalidate();
    }

    public void setSplitTrack(boolean splitTrack) {
        this.mSplitTrack = splitTrack;
        invalidate();
    }

    public boolean getSplitTrack() {
        return this.mSplitTrack;
    }

    public void setTickMark(Drawable tickMark) {
        if (this.mTickMark != null) {
            this.mTickMark.setCallback(null);
        }
        this.mTickMark = tickMark;
        if (tickMark != null) {
            tickMark.setCallback(this);
            tickMark.setLayoutDirection(getLayoutDirection());
            if (tickMark.isStateful()) {
                tickMark.setState(getDrawableState());
            }
            applyTickMarkTint();
        }
        invalidate();
    }

    public Drawable getTickMark() {
        return this.mTickMark;
    }

    public void setTickMarkTintList(ColorStateList tint) {
        this.mTickMarkTintList = tint;
        this.mHasTickMarkTint = true;
        applyTickMarkTint();
    }

    public ColorStateList getTickMarkTintList() {
        return this.mTickMarkTintList;
    }

    public void setTickMarkTintMode(PorterDuff.Mode tintMode) {
        setTickMarkTintBlendMode(tintMode != null ? BlendMode.fromValue(tintMode.nativeInt) : null);
    }

    public void setTickMarkTintBlendMode(BlendMode blendMode) {
        this.mTickMarkBlendMode = blendMode;
        this.mHasTickMarkBlendMode = true;
        applyTickMarkTint();
    }

    public PorterDuff.Mode getTickMarkTintMode() {
        if (this.mTickMarkBlendMode != null) {
            return BlendMode.blendModeToPorterDuffMode(this.mTickMarkBlendMode);
        }
        return null;
    }

    public BlendMode getTickMarkTintBlendMode() {
        return this.mTickMarkBlendMode;
    }

    private void applyTickMarkTint() {
        if (this.mTickMark != null) {
            if (this.mHasTickMarkTint || this.mHasTickMarkBlendMode) {
                this.mTickMark = this.mTickMark.mutate();
                if (this.mHasTickMarkTint) {
                    this.mTickMark.setTintList(this.mTickMarkTintList);
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

    public void setKeyProgressIncrement(int increment) {
        this.mKeyProgressIncrement = increment < 0 ? -increment : increment;
    }

    public int getKeyProgressIncrement() {
        return this.mKeyProgressIncrement;
    }

    @Override // android.widget.ProgressBar
    public synchronized void setMin(int min) {
        super.setMin(min);
        int range = getMax() - getMin();
        if (this.mKeyProgressIncrement == 0 || range / this.mKeyProgressIncrement > 20) {
            setKeyProgressIncrement(Math.max(1, Math.round(range / 20.0f)));
        }
    }

    @Override // android.widget.ProgressBar
    public synchronized void setMax(int max) {
        super.setMax(max);
        int range = getMax() - getMin();
        if (this.mKeyProgressIncrement == 0 || range / this.mKeyProgressIncrement > 20) {
            setKeyProgressIncrement(Math.max(1, Math.round(range / 20.0f)));
        }
    }

    @Override // android.widget.ProgressBar, android.view.View
    protected boolean verifyDrawable(Drawable who) {
        return who == this.mThumb || who == this.mTickMark || super.verifyDrawable(who);
    }

    @Override // android.widget.ProgressBar, android.view.View
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        if (this.mThumb != null) {
            this.mThumb.jumpToCurrentState();
        }
        if (this.mTickMark != null) {
            this.mTickMark.jumpToCurrentState();
        }
    }

    @Override // android.widget.ProgressBar, android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable progressDrawable = getProgressDrawable();
        if (progressDrawable != null && this.mDisabledAlpha < 1.0f) {
            int alpha = isEnabled() ? 255 : (int) (this.mDisabledAlpha * 255.0f);
            progressDrawable.setAlpha(alpha);
            if (this.mOverlapBackground != null) {
                this.mOverlapBackground.setAlpha(alpha);
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
        if (this.mSetDualColorMode
                && progressDrawable != null
                && progressDrawable.isStateful()
                && this.mOverlapBackground != null) {
            this.mOverlapBackground.setState(getDrawableState());
        }
        Drawable thumb = this.mThumb;
        if (thumb != null && thumb.isStateful() && thumb.setState(getDrawableState())) {
            invalidateDrawable(thumb);
        }
        Drawable tickMark = this.mTickMark;
        if (tickMark != null && tickMark.isStateful() && tickMark.setState(getDrawableState())) {
            invalidateDrawable(tickMark);
        }
    }

    @Override // android.widget.ProgressBar, android.view.View
    public void drawableHotspotChanged(float x, float y) {
        super.drawableHotspotChanged(x, y);
        if (this.mThumb != null) {
            this.mThumb.setHotspot(x, y);
        }
    }

    @Override // android.widget.ProgressBar
    void onVisualProgressChanged(int id, float scale) {
        Drawable thumb;
        super.onVisualProgressChanged(id, scale);
        if (id == 16908301 && (thumb = this.mThumb) != null) {
            setThumbPos(getWidth(), thumb, scale, Integer.MIN_VALUE);
            invalidate();
        }
    }

    @Override // android.widget.ProgressBar, android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        updateThumbAndTrackPos(w, h);
    }

    private void updateThumbAndTrackPos(int w, int h) {
        int trackOffset;
        int offsetHeight;
        if (this.mCurrentMode == 3) {
            updateThumbAndTrackPosInVertical(w, h);
            return;
        }
        int paddedHeight = (h - this.mPaddingTop) - this.mPaddingBottom;
        Drawable track = getCurrentDrawable();
        Drawable thumb = this.mThumb;
        int trackHeight = Math.min(this.mMaxHeight, paddedHeight);
        int thumbHeight = thumb == null ? 0 : thumb.getIntrinsicHeight();
        if (thumbHeight > trackHeight) {
            offsetHeight = (paddedHeight - thumbHeight) / 2;
            trackOffset = ((thumbHeight - trackHeight) / 2) + offsetHeight;
        } else {
            int thumbOffset = paddedHeight - trackHeight;
            int offsetHeight2 = thumbOffset / 2;
            trackOffset = offsetHeight2;
            offsetHeight = ((trackHeight - thumbHeight) / 2) + offsetHeight2;
        }
        if (track != null) {
            int trackWidth = (w - this.mPaddingRight) - this.mPaddingLeft;
            track.setBounds(0, trackOffset, trackWidth, trackOffset + trackHeight);
        }
        if (thumb != null) {
            setThumbPos(w, thumb, getScale(), offsetHeight);
        }
        updateSplitProgress();
    }

    private float getScale() {
        int min = getMin();
        int max = getMax();
        int range = max - min;
        if (range > 0) {
            return (getProgress() - min) / range;
        }
        return 0.0f;
    }

    private void setThumbPos(int w, Drawable thumb, float scale, int offset) {
        int top;
        int bottom;
        if (this.mCurrentMode == 3) {
            setThumbPosInVertical(getHeight(), thumb, scale, offset);
            return;
        }
        int available = (w - this.mPaddingLeft) - this.mPaddingRight;
        int thumbWidth = thumb.getIntrinsicWidth();
        int thumbHeight = thumb.getIntrinsicHeight();
        int available2 = (available - thumbWidth) + (this.mThumbOffset * 2);
        int thumbPos = (int) ((available2 * scale) + 0.5f);
        if (offset == Integer.MIN_VALUE) {
            Rect oldBounds = thumb.getBounds();
            top = oldBounds.top;
            bottom = oldBounds.bottom;
        } else {
            top = offset;
            bottom = offset + thumbHeight;
        }
        int left = (this.mMirrorForRtl && isLayoutRtl()) ? available2 - thumbPos : thumbPos;
        int right = left + thumbWidth;
        Drawable background = getBackground();
        if (background != null) {
            int offsetX = this.mPaddingLeft - this.mThumbOffset;
            int offsetY = this.mPaddingTop;
            int available3 = bottom + offsetY;
            background.setHotspotBounds(left + offsetX, top + offsetY, right + offsetX, available3);
        }
        thumb.setBounds(left, top, right, bottom);
        this.mThumbPosX = (this.mPaddingLeft + left) - (this.mPaddingLeft - (thumbWidth / 2));
        updateSplitProgress();
        updateGestureExclusionRects();
    }

    @Override // android.view.View
    public void setSystemGestureExclusionRects(List<Rect> rects) {
        Preconditions.checkNotNull(rects, "rects must not be null");
        this.mUserGestureExclusionRects = rects;
        updateGestureExclusionRects();
    }

    private void updateGestureExclusionRects() {
        Drawable thumb = this.mThumb;
        if (thumb == null) {
            super.setSystemGestureExclusionRects(this.mUserGestureExclusionRects);
            return;
        }
        this.mGestureExclusionRects.clear();
        thumb.copyBounds(this.mThumbRect);
        this.mThumbRect.offset(this.mPaddingLeft - this.mThumbOffset, this.mPaddingTop);
        growRectTo(this.mThumbRect, Math.min(getHeight(), this.mThumbExclusionMaxSize));
        this.mGestureExclusionRects.add(this.mThumbRect);
        this.mGestureExclusionRects.addAll(this.mUserGestureExclusionRects);
        super.setSystemGestureExclusionRects(this.mGestureExclusionRects);
    }

    public void growRectTo(Rect r, int minimumSize) {
        int dy = minimumSize - r.height();
        if (dy > 0) {
            r.top -= (dy + 1) / 2;
            r.bottom += dy / 2;
        }
        int dx = minimumSize - r.width();
        if (dx > 0) {
            r.left -= (dx + 1) / 2;
            r.right += dx / 2;
        }
    }

    @Override // android.widget.ProgressBar, android.view.View
    public void onResolveDrawables(int layoutDirection) {
        super.onResolveDrawables(layoutDirection);
        if (this.mThumb != null) {
            this.mThumb.setLayoutDirection(layoutDirection);
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
        Drawable thumbDrawable = this.mThumb;
        if (thumbDrawable != null && this.mSplitTrack) {
            Insets insets = thumbDrawable.getOpticalInsets();
            Rect tempRect = this.mTempRect;
            thumbDrawable.copyBounds(tempRect);
            tempRect.offset(this.mPaddingLeft - this.mThumbOffset, this.mPaddingTop);
            tempRect.left += insets.left;
            tempRect.right -= insets.right;
            int saveCount = canvas.save();
            canvas.clipRect(tempRect, Region.Op.DIFFERENCE);
            super.drawTrack(canvas);
            drawTickMarks(canvas);
            canvas.restoreToCount(saveCount);
        } else {
            super.drawTrack(canvas);
            drawTickMarks(canvas);
        }
        if (!checkInvalidatedDualColorMode()) {
            canvas.save();
            if (this.mMirrorForRtl && isLayoutRtl()) {
                canvas.translate(getWidth() - this.mPaddingRight, this.mPaddingTop);
                canvas.scale(-1.0f, 1.0f);
            } else {
                canvas.translate(this.mPaddingLeft, this.mPaddingTop);
            }
            Rect base = this.mOverlapBackground.getBounds();
            Rect tempRect2 = this.mTempRect;
            this.mOverlapBackground.copyBounds(tempRect2);
            int curProgress = Math.max(getProgress(), this.mOverlapPoint);
            int maxProgress = getMax();
            if (this.mCurrentMode == 3) {
                tempRect2.bottom =
                        (int) (base.bottom - (base.height() * (curProgress / maxProgress)));
            } else {
                tempRect2.left = (int) (base.left + (base.width() * (curProgress / maxProgress)));
            }
            canvas.clipRect(tempRect2);
            if (this.mDefaultNormalProgressColor.getDefaultColor()
                    != this.mOverlapNormalProgressColor.getDefaultColor()) {
                this.mOverlapBackground.draw(canvas);
            }
            canvas.restore();
        }
    }

    protected void drawTickMarks(Canvas canvas) {
        if (this.mTickMark != null) {
            int count = getMax() - getMin();
            if (count > 1) {
                int w = this.mTickMark.getIntrinsicWidth();
                int h = this.mTickMark.getIntrinsicHeight();
                int halfW = w >= 0 ? w / 2 : 1;
                int halfH = h >= 0 ? h / 2 : 1;
                this.mTickMark.setBounds(-halfW, -halfH, halfW, halfH);
                float spacing = ((getWidth() - this.mPaddingLeft) - this.mPaddingRight) / count;
                int saveCount = canvas.save();
                canvas.translate(this.mPaddingLeft, getHeight() / 2.0f);
                for (int i = 0; i <= count; i++) {
                    this.mTickMark.draw(canvas);
                    canvas.translate(spacing, 0.0f);
                }
                canvas.restoreToCount(saveCount);
            }
        }
    }

    void drawThumb(Canvas canvas) {
        if (this.mThumb != null) {
            int saveCount = canvas.save();
            if (this.mCurrentMode == 3) {
                canvas.translate(this.mPaddingLeft, this.mPaddingTop - this.mThumbOffset);
            } else {
                canvas.translate(this.mPaddingLeft - this.mThumbOffset, this.mPaddingTop);
            }
            this.mThumb.draw(canvas);
            canvas.restoreToCount(saveCount);
        }
    }

    @Override // android.widget.ProgressBar, android.view.View
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Drawable d = getCurrentDrawable();
        int dw = 0;
        int dh = 0;
        if (d != null) {
            if (this.mCurrentMode == 3) {
                int thumbWidth = this.mThumb == null ? 0 : this.mThumb.getIntrinsicHeight();
                int dw2 =
                        Math.max(this.mMinWidth, Math.min(this.mMaxWidth, d.getIntrinsicHeight()));
                dh = Math.max(this.mMinHeight, Math.min(this.mMaxHeight, d.getIntrinsicWidth()));
                dw = Math.max(thumbWidth, dw2);
            } else {
                int thumbHeight = this.mThumb == null ? 0 : this.mThumb.getIntrinsicHeight();
                dw = Math.max(this.mMinWidth, Math.min(this.mMaxWidth, d.getIntrinsicWidth()));
                int dh2 =
                        Math.max(
                                this.mMinHeight, Math.min(this.mMaxHeight, d.getIntrinsicHeight()));
                dh = Math.max(thumbHeight, dh2);
            }
        }
        int thumbHeight2 = this.mPaddingLeft;
        setMeasuredDimension(
                resolveSizeAndState(dw + thumbHeight2 + this.mPaddingRight, widthMeasureSpec, 0),
                resolveSizeAndState(
                        dh + this.mPaddingTop + this.mPaddingBottom, heightMeasureSpec, 0));
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00ee, code lost:

       return true;
    */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r7) {
        /*
            Method dump skipped, instructions count: 252
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " android.widget.AbsSeekBar.onTouchEvent(android.view.MotionEvent):boolean");
    }

    private void startDrag(MotionEvent event) {
        setPressed(true);
        if (this.mThumb != null) {
            invalidate(this.mThumb.getBounds());
        }
        onStartTrackingTouch();
        trackTouchEvent(event);
        attemptClaimDrag();
    }

    private void setHotspot(float x, float y) {
        Drawable bg = getBackground();
        if (bg != null) {
            bg.setHotspot(x, y);
        }
    }

    private void trackTouchEvent(MotionEvent event) {
        float scale;
        if (this.mCurrentMode == 3) {
            trackTouchEventInVertical(event);
            return;
        }
        int x = Math.round(event.getX());
        int y = Math.round(event.getY());
        int width = getWidth();
        int availableWidth = (width - this.mPaddingLeft) - this.mPaddingRight;
        float progress = 0.0f;
        if (isLayoutRtl() && this.mMirrorForRtl) {
            if (x > width - this.mPaddingRight) {
                scale = 0.0f;
            } else if (x < this.mPaddingLeft) {
                scale = 1.0f;
            } else {
                scale =
                        (((availableWidth - x) + this.mPaddingLeft) / availableWidth)
                                + this.mTouchThumbOffset;
                progress = this.mTouchProgressOffset;
            }
        } else if (x < this.mPaddingLeft) {
            scale = 0.0f;
        } else if (x > width - this.mPaddingRight) {
            scale = 1.0f;
        } else {
            scale = ((x - this.mPaddingLeft) / availableWidth) + this.mTouchThumbOffset;
            progress = this.mTouchProgressOffset;
        }
        int range = getMax() - getMin();
        float basicWidth = 1.0f / range;
        if (scale > 0.0f && scale < 1.0f) {
            float remainder = scale % basicWidth;
            if (remainder > basicWidth / 2.0f) {
                scale += basicWidth - remainder;
            }
        }
        setHotspot(x, y);
        setProgressInternal(Math.round(progress + (range * scale) + getMin()), true, false);
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

    void onKeyChange() {}

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0028  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x001b  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0019  */
    @Override // android.view.View, android.view.KeyEvent.Callback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onKeyDown(int r5, android.view.KeyEvent r6) {
        /*
            r4 = this;
            boolean r0 = r4.isEnabled()
            if (r0 == 0) goto L4a
            int r0 = r4.mKeyProgressIncrement
            int r1 = r4.mCurrentMode
            r2 = 3
            r3 = 1
            if (r1 != r2) goto L2c
            switch(r5) {
                case 19: goto L13;
                case 20: goto L12;
                case 69: goto L12;
                case 70: goto L13;
                case 81: goto L13;
                default: goto L11;
            }
        L11:
            goto L4a
        L12:
            int r0 = -r0
        L13:
            boolean r1 = r4.isLayoutRtl()
            if (r1 == 0) goto L1b
            int r1 = -r0
            goto L1c
        L1b:
            r1 = r0
        L1c:
            r0 = r1
            int r1 = r4.getProgress()
            int r1 = r1 + r0
            boolean r1 = r4.setProgressInternal(r1, r3, r3)
            if (r1 == 0) goto L4a
            r4.onKeyChange()
            return r3
        L2c:
            switch(r5) {
                case 21: goto L30;
                case 22: goto L31;
                case 69: goto L30;
                case 70: goto L31;
                case 81: goto L31;
                default: goto L2f;
            }
        L2f:
            goto L4a
        L30:
            int r0 = -r0
        L31:
            boolean r1 = r4.isLayoutRtl()
            if (r1 == 0) goto L39
            int r1 = -r0
            goto L3a
        L39:
            r1 = r0
        L3a:
            r0 = r1
            int r1 = r4.getProgress()
            int r1 = r1 + r0
            boolean r1 = r4.setProgressInternal(r1, r3, r3)
            if (r1 == 0) goto L4a
            r4.onKeyChange()
            return r3
        L4a:
            boolean r0 = super.onKeyDown(r5, r6)
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: android.widget.AbsSeekBar.onKeyDown(int,"
                    + " android.view.KeyEvent):boolean");
    }

    @Override // android.widget.ProgressBar, android.view.View
    public CharSequence getAccessibilityClassName() {
        return AbsSeekBar.class.getName();
    }

    @Override // android.widget.ProgressBar, android.view.View
    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfoInternal(info);
        if (isEnabled()) {
            int progress = getProgress();
            if (progress > getMin()) {
                info.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_BACKWARD);
            }
            if (progress < getMax()) {
                info.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_FORWARD);
            }
        }
    }

    @Override // android.view.View
    public boolean performAccessibilityActionInternal(int action, Bundle arguments) {
        if (super.performAccessibilityActionInternal(action, arguments)) {
            return true;
        }
        if (!isEnabled()) {
            return false;
        }
        switch (action) {
            case 4096:
            case 8192:
                if (!canUserSetProgress()) {
                    return false;
                }
                int range = getMax() - getMin();
                int increment = Math.max(1, Math.round(range / 20.0f));
                if (action == 8192) {
                    increment = -increment;
                }
                if (!setProgressInternal(getProgress() + increment, true, true)) {
                    return false;
                }
                onKeyChange();
                return true;
            case 16908349:
                if (!canUserSetProgress()
                        || arguments == null
                        || !arguments.containsKey(
                                AccessibilityNodeInfo.ACTION_ARGUMENT_PROGRESS_VALUE)) {
                    return false;
                }
                float value =
                        arguments.getFloat(AccessibilityNodeInfo.ACTION_ARGUMENT_PROGRESS_VALUE);
                return setProgressInternal((int) value, true, true);
            default:
                return false;
        }
    }

    boolean canUserSetProgress() {
        return !isIndeterminate() && isEnabled();
    }

    @Override // android.view.View
    public void onRtlPropertiesChanged(int layoutDirection) {
        super.onRtlPropertiesChanged(layoutDirection);
        Drawable thumb = this.mThumb;
        if (thumb != null) {
            setThumbPos(getWidth(), thumb, getScale(), Integer.MIN_VALUE);
            invalidate();
        }
    }

    @Deprecated
    public void semSetThumbTintColor(int color) {
        ColorStateList mOverlapColor = colorToColorStateList(color);
        if (!mOverlapColor.equals(this.mDefaultActivatedThumbColor)) {
            this.mDefaultActivatedThumbColor = mOverlapColor;
        }
    }

    @Override // android.widget.ProgressBar
    void onProgressRefresh(float scale, boolean fromUser, int progress) {
        int targetLevel = (int) (10000.0f * scale);
        boolean isMuteAnimationNeeded =
                (!this.mUseMuteAnimation || this.mIsFirstSetProgress || this.mIsDraggingForSliding)
                        ? false
                        : true;
        if (isMuteAnimationNeeded && this.mCurrentProgressLevel != 0 && targetLevel == 0) {
            startMuteAnimation();
            return;
        }
        cancelMuteAnimation();
        this.mIsFirstSetProgress = false;
        this.mCurrentProgressLevel = targetLevel;
        super.onProgressRefresh(scale, fromUser, progress);
        Drawable thumb = this.mThumb;
        if (thumb != null) {
            setThumbPos(getWidth(), thumb, scale, Integer.MIN_VALUE);
            invalidate();
        }
    }

    private void updateThumbAndTrackPosInVertical(int w, int h) {
        int trackOffset;
        int offsetWidth;
        int paddedWidth = (w - this.mPaddingLeft) - this.mPaddingRight;
        Drawable track = getCurrentDrawable();
        Drawable thumb = this.mThumb;
        int trackWidth = Math.min(this.mMaxWidth, paddedWidth);
        int thumbWidth = thumb == null ? 0 : thumb.getIntrinsicWidth();
        if (thumbWidth > trackWidth) {
            offsetWidth = (paddedWidth - thumbWidth) / 2;
            trackOffset = ((thumbWidth - trackWidth) / 2) + offsetWidth;
        } else {
            int thumbOffset = paddedWidth - trackWidth;
            int offsetWidth2 = thumbOffset / 2;
            trackOffset = offsetWidth2;
            offsetWidth = ((trackWidth - thumbWidth) / 2) + offsetWidth2;
        }
        if (track != null) {
            int trackHeight = (h - this.mPaddingBottom) - this.mPaddingTop;
            track.setBounds(trackOffset, 0, paddedWidth - trackOffset, trackHeight);
        }
        if (thumb != null) {
            setThumbPosInVertical(h, thumb, getScale(), offsetWidth);
        }
    }

    private void setThumbPosInVertical(int h, Drawable thumb, float scale, int offset) {
        int left;
        int right;
        int available = (h - this.mPaddingTop) - this.mPaddingBottom;
        int thumbWidth = thumb.getIntrinsicHeight();
        int thumbHeight = thumb.getIntrinsicHeight();
        int available2 = (available - thumbHeight) + (this.mThumbOffset * 2);
        int thumbPos = (int) ((available2 * scale) + 0.5f);
        if (offset == Integer.MIN_VALUE) {
            Rect oldBounds = thumb.getBounds();
            left = oldBounds.left;
            right = oldBounds.right;
        } else {
            left = offset;
            right = offset + thumbWidth;
        }
        int top = available2 - thumbPos;
        int bottom = top + thumbHeight;
        Drawable background = getBackground();
        if (background != null) {
            int offsetX = this.mPaddingLeft;
            int offsetY = this.mPaddingTop - this.mThumbOffset;
            background.setHotspotBounds(
                    left + offsetX, top + offsetY, right + offsetX, bottom + offsetY);
        }
        thumb.setBounds(left, top, right, bottom);
        this.mThumbPosX = (thumbWidth / 2) + top + this.mPaddingLeft;
    }

    private void updateSplitProgress() {
        if (this.mCurrentMode != 4) {
            return;
        }
        Drawable d = this.mSplitProgress;
        Rect base = getCurrentDrawable().getBounds();
        if (d != null) {
            if (this.mMirrorForRtl && isLayoutRtl()) {
                d.setBounds(
                        this.mThumbPosX, base.top, getWidth() - this.mPaddingRight, base.bottom);
            } else {
                d.setBounds(this.mPaddingLeft, base.top, this.mThumbPosX, base.bottom);
            }
        }
        int w = getWidth();
        int h = getHeight();
        if (this.mDivider != null) {
            this.mDivider.setBounds(
                    (int) ((w / 2.0f) - ((this.mDensity * 4.0f) / 2.0f)),
                    (int) ((h / 2.0f) - ((this.mDensity * 22.0f) / 2.0f)),
                    (int) ((w / 2.0f) + ((this.mDensity * 4.0f) / 2.0f)),
                    (int) ((h / 2.0f) + ((this.mDensity * 22.0f) / 2.0f)));
        }
    }

    private void trackTouchEventInVertical(MotionEvent event) {
        float scale;
        int height = getHeight();
        int availableHeight = (height - this.mPaddingTop) - this.mPaddingBottom;
        int x = Math.round(event.getX());
        int y = height - Math.round(event.getY());
        float progress = 0.0f;
        if (y < this.mPaddingBottom) {
            scale = 0.0f;
        } else if (y > height - this.mPaddingTop) {
            scale = 1.0f;
        } else {
            scale = (y - this.mPaddingBottom) / availableHeight;
            progress = this.mTouchProgressOffset;
        }
        int range = getMax() - getMin();
        float basicWidth = 1.0f / range;
        if (scale > 0.0f && scale < 1.0f) {
            float remainder = scale % basicWidth;
            if (remainder > basicWidth / 2.0f) {
                scale += basicWidth - remainder;
            }
        }
        setHotspot(x, y);
        setProgressInternal(Math.round(progress + (range * scale) + getMin()), true, false);
    }

    @Override // android.widget.ProgressBar
    public boolean setProgressInternal(int progress, boolean fromUser, boolean animate) {
        boolean superRet = super.setProgressInternal(progress, fromUser, animate);
        updateWarningMode(progress);
        updateDualColorMode();
        return superRet;
    }

    void onStartTrackingHover(int hoverLevel, int posX, int posY) {}

    void onStopTrackingHover() {}

    void onHoverChanged(int hoverLevel, int posX, int posY) {}

    private void trackHoverEvent(int posX) {
        float scale;
        int width = getWidth();
        int available = (width - this.mPaddingLeft) - this.mPaddingRight;
        float hoverLevel = 0.0f;
        if (posX < this.mPaddingLeft) {
            scale = 0.0f;
        } else if (posX > width - this.mPaddingRight) {
            scale = 1.0f;
        } else {
            scale = (posX - this.mPaddingLeft) / available;
            hoverLevel = this.mTouchProgressOffset;
        }
        int max = getMax();
        this.mHoveringLevel = (int) (hoverLevel + (max * scale));
    }

    @Override // android.view.View
    public void semSetHoverPopupType(int type) {
        if (!isHoveringUIEnabled()) {
            return;
        }
        if (type == 3) {
            semGetHoverPopup(true).setGravity(12849);
            int contentHeight = getMeasuredHeight();
            semGetHoverPopup(true).setOffset(0, contentHeight / 2);
            semGetHoverPopup(true).setHoverDetectTime(200);
        }
        super.semSetHoverPopupType(type);
    }

    @Override // android.view.View
    public boolean onHoverEvent(MotionEvent event) {
        int toolType = event.getToolType(0);
        boolean isPossibleToolType = toolType == 2 || toolType == 1 || toolType == 3;
        if (isPossibleToolType && isHoveringUIEnabled()) {
            int action = event.getAction();
            int x = (int) event.getX();
            int y = (int) event.getY();
            switch (action) {
                case 7:
                    trackHoverEvent(x);
                    onHoverChanged(this.mHoveringLevel, x, y);
                    if (this.mHoverPopupType == 3) {
                        semGetHoverPopup(true).update();
                        break;
                    }
                    break;
                case 9:
                    trackHoverEvent(x);
                    onStartTrackingHover(this.mHoveringLevel, x, y);
                    break;
                case 10:
                    onStopTrackingHover();
                    break;
            }
        }
        return super.onHoverEvent(event);
    }

    @Override // android.widget.ProgressBar
    public void setProgressDrawable(Drawable d) {
        super.setProgressDrawable(d);
    }

    public Rect semGetThumbBounds() {
        if (this.mThumb != null) {
            return this.mThumb.getBounds();
        }
        return null;
    }

    public int getThumbHeight() {
        return this.mThumb.getIntrinsicHeight();
    }

    @Override // android.widget.ProgressBar
    public void semSetMode(int mode) {
        if (this.mCurrentMode == mode) {
            return;
        }
        super.semSetMode(mode);
        switch (mode) {
            case 0:
                setProgressTintList(this.mDefaultActivatedProgressColor);
                setThumbTintList(this.mDefaultActivatedThumbColor);
                break;
            case 1:
                updateWarningMode(getProgress());
                break;
            case 3:
                Drawable thumb =
                        this.mContext.getDrawable(
                                R.drawable.tw_scrubber_control_vertical_material_anim);
                setThumb(thumb);
                break;
            case 4:
                this.mSplitProgress =
                        this.mContext.getDrawable(
                                R.drawable.tw_split_seekbar_primary_progress_material);
                this.mDivider =
                        this.mContext.getDrawable(
                                R.drawable.tw_split_seekbar_vertical_bar_material);
                updateSplitProgress();
                break;
            case 5:
                initializeExpandMode();
                break;
        }
        drawableStateChanged();
        invalidate();
    }

    private void initializeExpandMode() {
        SliderDrawable background =
                new SliderDrawable(
                        this,
                        this.mModeExpandTrackMinWidth,
                        this.mModeExpandTrackMaxWidth,
                        this.mDefaultNormalProgressColor);
        SliderDrawable secondaryProgress =
                new SliderDrawable(
                        this,
                        this.mModeExpandTrackMinWidth,
                        this.mModeExpandTrackMaxWidth,
                        this.mDefaultSecondaryProgressColor);
        SliderDrawable primaryProgress =
                new SliderDrawable(
                        this,
                        this.mModeExpandTrackMinWidth,
                        this.mModeExpandTrackMaxWidth,
                        this.mDefaultActivatedProgressColor);
        Drawable thumbDrawable =
                new ThumbDrawable(
                        this.mModeExpandThumbRadius, this.mDefaultActivatedThumbColor, false);
        Drawable[] drawables = {
            background,
            new ClipDrawable(secondaryProgress, 19, 1),
            new ClipDrawable(primaryProgress, 19, 1)
        };
        LayerDrawable layer = new LayerDrawable(drawables);
        layer.setPaddingMode(1);
        layer.setId(0, 16908288);
        layer.setId(1, 16908303);
        layer.setId(2, 16908301);
        setProgressDrawable(layer);
        setThumb(thumbDrawable);
        setBackgroundResource(R.drawable.sem_seekbar_background_borderless);
        if (getMaxHeight() > this.mModeExpandTrackMaxWidth) {
            setMaxHeight(this.mModeExpandTrackMaxWidth);
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

    public void setOverlapBackgroundForDualColor(int color) {
        ColorStateList mOverlapColor = colorToColorStateList(color);
        if (!mOverlapColor.equals(this.mOverlapNormalProgressColor)) {
            this.mOverlapNormalProgressColor = mOverlapColor;
        }
        this.mOverlapActivatedProgressColor = this.mOverlapNormalProgressColor;
        this.mLargeFont = true;
    }

    public void semSetOverlapPointForDualColor(int value) {
        if (value >= getMax()) {
            return;
        }
        this.mSetDualColorMode = true;
        this.mOverlapPoint = value;
        if (value == -1) {
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
        Rect base = getCurrentDrawable().getBounds();
        this.mOverlapBackground.setBounds(base);
    }

    public void setDualModeOverlapColor(int bgColor, int fgColor) {
        ColorStateList mOverlapBackgroundColor = colorToColorStateList(bgColor);
        ColorStateList mOverlapForegroundColor = colorToColorStateList(fgColor);
        if (!mOverlapBackgroundColor.equals(this.mOverlapNormalProgressColor)) {
            this.mOverlapNormalProgressColor = mOverlapBackgroundColor;
        }
        if (!mOverlapForegroundColor.equals(this.mOverlapActivatedProgressColor)) {
            this.mOverlapActivatedProgressColor = mOverlapForegroundColor;
        }
        updateDualColorMode();
        invalidate();
    }

    private boolean checkInvalidatedDualColorMode() {
        return this.mOverlapPoint == -1 || this.mOverlapBackground == null;
    }

    private void initDualOverlapDrawable() {
        if (this.mCurrentMode == 5) {
            this.mOverlapBackground =
                    new SliderDrawable(
                            this,
                            this.mModeExpandTrackMinWidth,
                            this.mModeExpandTrackMaxWidth,
                            this.mOverlapNormalProgressColor);
        } else if (getProgressDrawable() != null
                && getProgressDrawable().getConstantState() != null) {
            this.mOverlapBackground =
                    getProgressDrawable().getConstantState().newDrawable().mutate();
        }
    }

    @Override // android.widget.ProgressBar
    protected void updateDrawableBounds(int w, int h) {
        super.updateDrawableBounds(w, h);
        updateThumbAndTrackPos(w, h);
        updateBoundsForDualColor();
    }

    private ColorStateList colorToColorStateList(int color) {
        int[][] EMPTY = {new int[0]};
        return new ColorStateList(EMPTY, new int[] {color});
    }

    private void updateWarningMode(int progress) {
        if (this.mCurrentMode == 1) {
            boolean isMax = progress == getMax();
            if (isMax) {
                setProgressOverlapTintList(this.mOverlapActivatedProgressColor);
                setThumbOverlapTintList(this.mOverlapActivatedThumbColor);
            } else {
                setProgressTintList(this.mDefaultActivatedProgressColor);
                setThumbTintList(this.mDefaultActivatedThumbColor);
            }
        }
    }

    private void initMuteAnimation() {
        this.mMuteAnimationSet = new AnimatorSet();
        List<Animator> list = new ArrayList<>();
        int duration = 500 / 8;
        int distance = 400;
        for (int i = 0; i < 8; i++) {
            boolean isGoingDirection = i % 2 == 0;
            ValueAnimator progressZeroAnimation =
                    isGoingDirection
                            ? ValueAnimator.ofInt(0, distance)
                            : ValueAnimator.ofInt(distance, 0);
            progressZeroAnimation.setDuration(duration);
            progressZeroAnimation.setInterpolator(new LinearInterpolator());
            progressZeroAnimation.addUpdateListener(
                    new ValueAnimator
                            .AnimatorUpdateListener() { // from class: android.widget.AbsSeekBar.1
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public void onAnimationUpdate(ValueAnimator animation) {
                            AbsSeekBar.this.mCurrentProgressLevel =
                                    ((Integer) animation.getAnimatedValue()).intValue();
                            AbsSeekBar.this.onSlidingRefresh(AbsSeekBar.this.mCurrentProgressLevel);
                        }
                    });
            list.add(progressZeroAnimation);
            if (isGoingDirection) {
                distance = (int) (distance * 0.6d);
            }
        }
        this.mMuteAnimationSet.playSequentially(list);
    }

    private void cancelMuteAnimation() {
        if (this.mMuteAnimationSet != null && this.mMuteAnimationSet.isRunning()) {
            this.mMuteAnimationSet.cancel();
        }
    }

    private void startMuteAnimation() {
        cancelMuteAnimation();
        if (this.mMuteAnimationSet != null) {
            this.mMuteAnimationSet.start();
        }
    }

    public void setMuteAnimation(boolean use) {
        if (this.mAllowedSeekBarAnimation) {
            this.mUseMuteAnimation = use;
        }
    }

    @Override // android.widget.ProgressBar
    protected void onSlidingRefresh(int level) {
        super.onSlidingRefresh(level);
        float scale = level / 10000.0f;
        Drawable thumb = this.mThumb;
        if (thumb != null) {
            setThumbPos(getWidth(), thumb, scale, Integer.MIN_VALUE);
            invalidate();
        }
    }

    public void setDefaultColorForVolumePanel(boolean isClearCoverOpened) {
        int i;
        int i2;
        int i3;
        int i4;
        if (isClearCoverOpened) {
            this.mDefaultNormalProgressColor = colorToColorStateList(Color.parseColor("#ffe3e0e0"));
            this.mDefaultActivatedProgressColor =
                    colorToColorStateList(Color.parseColor("#ff56c0e5"));
            this.mDefaultActivatedThumbColor = colorToColorStateList(Color.parseColor("#ff56c0e5"));
            this.mOverlapNormalProgressColor = colorToColorStateList(Color.parseColor("#fff7cdbd"));
            this.mOverlapActivatedProgressColor =
                    colorToColorStateList(Color.parseColor("#fff1662f"));
            this.mOverlapActivatedThumbColor = colorToColorStateList(Color.parseColor("#fff1662f"));
            return;
        }
        Resources res = this.mContext.getResources();
        if (this.mIsDeviceDefaultDark) {
            i = R.color.tw_seekbar_color_control_normal_dark;
        } else {
            i = R.color.tw_seekbar_color_control_normal_light;
        }
        this.mDefaultNormalProgressColor = colorToColorStateList(i);
        if (this.mIsDeviceDefaultDark) {
            i2 = R.color.tw_seekbar_color_control_activated_dark;
        } else {
            i2 = R.color.tw_seekbar_color_control_activated_light;
        }
        this.mDefaultActivatedProgressColor = colorToColorStateList(res.getColor(i2, null));
        if (this.mIsDeviceDefaultDark) {
            i3 = R.color.tw_thumb_color_control_activated_dark;
        } else {
            i3 = R.color.tw_thumb_color_control_activated_light;
        }
        this.mDefaultActivatedThumbColor = colorToColorStateList(res.getColor(i3, null));
        this.mOverlapNormalProgressColor =
                colorToColorStateList(
                        res.getColor(R.color.tw_seekbar_color_overlap_normal_light, null));
        boolean z = this.mIsDeviceDefaultDark;
        int i5 = R.color.tw_seekbar_color_overlap_activated_light;
        if (z) {
            i4 = 17171700;
        } else {
            i4 = 17171699;
        }
        this.mOverlapActivatedProgressColor = colorToColorStateList(res.getColor(i4, null));
        if (!this.mIsDeviceDefaultDark) {
            i5 = 17171699;
        }
        this.mOverlapActivatedThumbColor = colorToColorStateList(res.getColor(i5, null));
    }

    private void setThumbOverlapTintList(ColorStateList tint) {
        this.mThumbTintList = tint;
        this.mHasThumbTint = true;
        applyThumbTint();
    }

    @Override // android.widget.ProgressBar
    public void setProgressTintList(ColorStateList tint) {
        super.setProgressTintList(tint);
        this.mDefaultActivatedProgressColor = tint;
    }

    private void setProgressOverlapTintList(ColorStateList tint) {
        super.setProgressTintList(tint);
    }

    public void setTouchDisabled(boolean disabled) {
        this.mIsTouchDisabled = disabled;
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

        public SliderDrawable(
                AbsSeekBar absSeekBar, float minWidth, float maxWidth, ColorStateList color) {
            this(minWidth, maxWidth, color, false);
        }

        public SliderDrawable(
                float minWidth, float maxWidth, ColorStateList color, boolean isVertical) {
            this.SINE_IN_OUT_80 = new PathInterpolator(0.33f, 0.0f, 0.2f, 1.0f);
            this.mPaint = new Paint();
            this.mIsStateChanged = false;
            this.mAlpha = 255;
            this.mState = new SliderState();
            this.mPaint.setStyle(Paint.Style.STROKE);
            this.mPaint.setStrokeCap(Paint.Cap.ROUND);
            this.mColorStateList = color;
            this.mColor = color.getDefaultColor();
            this.mPaint.setColor(this.mColor);
            this.mPaint.setStrokeWidth(minWidth);
            this.mSliderMinWidth = minWidth;
            this.mSliderMaxWidth = maxWidth;
            this.mRadius = minWidth / 2.0f;
            this.mIsVertical = isVertical;
            initAnimator();
        }

        private void initAnimator() {
            float tempTrackMinWidth = this.mSliderMinWidth;
            float tempTrackMaxWidth = this.mSliderMaxWidth;
            this.mPressedAnimator = ValueAnimator.ofFloat(tempTrackMinWidth, tempTrackMaxWidth);
            this.mPressedAnimator.setDuration(250L);
            this.mPressedAnimator.setInterpolator(this.SINE_IN_OUT_80);
            this.mPressedAnimator.addUpdateListener(
                    new ValueAnimator
                            .AnimatorUpdateListener() { // from class:
                                                        // android.widget.AbsSeekBar.SliderDrawable.1
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            float value = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                            SliderDrawable.this.invalidateTrack(value);
                        }
                    });
            this.mReleasedAnimator = ValueAnimator.ofFloat(tempTrackMaxWidth, tempTrackMinWidth);
            this.mReleasedAnimator.setDuration(250L);
            this.mReleasedAnimator.setInterpolator(this.SINE_IN_OUT_80);
            this.mReleasedAnimator.addUpdateListener(
                    new ValueAnimator
                            .AnimatorUpdateListener() { // from class:
                                                        // android.widget.AbsSeekBar.SliderDrawable.2
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            float value = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                            SliderDrawable.this.invalidateTrack(value);
                        }
                    });
        }

        @Override // android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            int prevAlpha = this.mPaint.getAlpha();
            this.mPaint.setAlpha(modulateAlpha(prevAlpha, this.mAlpha));
            canvas.save();
            if (!this.mIsVertical) {
                float width =
                        ((AbsSeekBar.this.getWidth() - AbsSeekBar.this.mPaddingLeft)
                                        - AbsSeekBar.this.mPaddingRight)
                                - this.mRadius;
                canvas.drawLine(
                        this.mRadius,
                        AbsSeekBar.this.getHeight() / 2.0f,
                        width,
                        AbsSeekBar.this.getHeight() / 2.0f,
                        this.mPaint);
            } else {
                float height =
                        ((AbsSeekBar.this.getHeight() - AbsSeekBar.this.getPaddingTop())
                                        - AbsSeekBar.this.getPaddingBottom())
                                - this.mRadius;
                canvas.drawLine(
                        AbsSeekBar.this.getWidth() / 2.0f,
                        height,
                        AbsSeekBar.this.getWidth() / 2.0f,
                        this.mRadius,
                        this.mPaint);
            }
            canvas.restore();
            this.mPaint.setAlpha(prevAlpha);
        }

        private int modulateAlpha(int paintAlpha, int alpha) {
            int scale = (alpha >>> 7) + alpha;
            return (paintAlpha * scale) >>> 8;
        }

        @Override // android.graphics.drawable.Drawable
        public boolean isStateful() {
            return true;
        }

        @Override // android.graphics.drawable.Drawable
        public void setAlpha(int alpha) {
            this.mAlpha = alpha;
            invalidateSelf();
        }

        @Override // android.graphics.drawable.Drawable
        public void setColorFilter(ColorFilter colorFilter) {
            this.mPaint.setColorFilter(colorFilter);
            invalidateSelf();
        }

        @Override // android.graphics.drawable.Drawable
        public int getOpacity() {
            Paint p = this.mPaint;
            if (p.getXfermode() == null) {
                int alpha = p.getAlpha();
                if (alpha == 0) {
                    return -2;
                }
                if (alpha == 255) {
                    return -1;
                }
                return -3;
            }
            return -3;
        }

        @Override // android.graphics.drawable.Drawable
        public void setTintList(ColorStateList tint) {
            super.setTintList(tint);
            if (tint != null) {
                this.mColorStateList = tint;
                this.mColor =
                        this.mColorStateList.getColorForState(
                                AbsSeekBar.this.getDrawableState(), this.mColor);
                this.mPaint.setColor(this.mColor);
                invalidateSelf();
            }
        }

        @Override // android.graphics.drawable.Drawable
        protected boolean onStateChange(int[] stateSet) {
            boolean changed = super.onStateChange(stateSet);
            int color = this.mColorStateList.getColorForState(stateSet, this.mColor);
            if (this.mColor != color) {
                this.mColor = color;
                this.mPaint.setColor(this.mColor);
                invalidateSelf();
            }
            boolean enabled = false;
            boolean pressed = false;
            boolean z = false;
            for (int state : stateSet) {
                if (state == 16842910) {
                    enabled = true;
                } else if (state == 16842919) {
                    pressed = true;
                }
            }
            if (enabled && pressed) {
                z = true;
            }
            startSliderAnimation(z);
            return changed;
        }

        @Override // android.graphics.drawable.Drawable
        public int getIntrinsicWidth() {
            return (int) this.mSliderMaxWidth;
        }

        @Override // android.graphics.drawable.Drawable
        public int getIntrinsicHeight() {
            return (int) this.mSliderMaxWidth;
        }

        public void setStrokeWidth(float height) {
            this.mPaint.setStrokeWidth(height);
            this.mRadius = height / 2.0f;
        }

        private void startSliderAnimation(boolean isChanged) {
            if (this.mIsStateChanged != isChanged) {
                if (isChanged) {
                    startPressedAnimation();
                } else {
                    startReleasedAnimation();
                }
                this.mIsStateChanged = isChanged;
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

        void invalidateTrack(float value) {
            setStrokeWidth(value);
            invalidateSelf();
        }

        @Override // android.graphics.drawable.Drawable
        public Drawable.ConstantState getConstantState() {
            return this.mState;
        }

        private class SliderState extends Drawable.ConstantState {
            private SliderState() {}

            @Override // android.graphics.drawable.Drawable.ConstantState
            public Drawable newDrawable() {
                return SliderDrawable.this;
            }

            @Override // android.graphics.drawable.Drawable.ConstantState
            public int getChangingConfigurations() {
                return 0;
            }
        }
    }

    private class ThumbDrawable extends Drawable {
        private static final int PRESSED_DURATION = 100;
        private static final int RELEASED_DURATION = 300;
        int mColor;
        private ColorStateList mColorStateList;
        private boolean mIsVertical;
        private final int mRadius;
        private int mRadiusForAni;
        private ValueAnimator mThumbPressed;
        private ValueAnimator mThumbReleased;
        private final Interpolator SINE_IN_OUT_90 = new PathInterpolator(0.33f, 0.0f, 0.1f, 1.0f);
        private final Paint mPaint = new Paint(1);
        private final Paint mPaintFill = new Paint(1);
        private boolean mIsStateChanged = false;
        private int mAlpha = 255;

        public ThumbDrawable(int radius, ColorStateList color, boolean isVertical) {
            this.mIsVertical = false;
            this.mRadiusForAni = radius;
            this.mRadius = radius;
            this.mColorStateList = color;
            this.mColor = color.getDefaultColor();
            this.mPaint.setStyle(Paint.Style.STROKE);
            this.mPaintFill.setStyle(Paint.Style.FILL);
            this.mPaint.setColor(this.mColor);
            this.mPaint.setStrokeWidth(
                    AbsSeekBar.this
                            .mContext
                            .getResources()
                            .getDimension(R.dimen.sem_seekbar_thumb_stroke));
            this.mPaintFill.setColor(
                    AbsSeekBar.this
                            .mContext
                            .getResources()
                            .getColor(R.color.tw_thumb_control_fill_color_activated));
            this.mIsVertical = isVertical;
            initAnimation();
        }

        void initAnimation() {
            this.mThumbPressed = ValueAnimator.ofFloat(this.mRadius, 0.0f);
            this.mThumbPressed.setDuration(100L);
            this.mThumbPressed.setInterpolator(new LinearInterpolator());
            this.mThumbPressed.addUpdateListener(
                    new ValueAnimator
                            .AnimatorUpdateListener() { // from class:
                                                        // android.widget.AbsSeekBar.ThumbDrawable.1
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float value = ((Float) animation.getAnimatedValue()).floatValue();
                            ThumbDrawable.this.setRadius((int) value);
                            ThumbDrawable.this.invalidateSelf();
                        }
                    });
            this.mThumbReleased = ValueAnimator.ofFloat(0.0f, this.mRadius);
            this.mThumbReleased.setDuration(300L);
            this.mThumbReleased.setInterpolator(this.SINE_IN_OUT_90);
            this.mThumbReleased.addUpdateListener(
                    new ValueAnimator
                            .AnimatorUpdateListener() { // from class:
                                                        // android.widget.AbsSeekBar.ThumbDrawable.2
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float value = ((Float) animation.getAnimatedValue()).floatValue();
                            ThumbDrawable.this.setRadius((int) value);
                            ThumbDrawable.this.invalidateSelf();
                        }
                    });
        }

        @Override // android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            int prevAlpha = this.mPaint.getAlpha();
            this.mPaint.setAlpha(modulateAlpha(prevAlpha, this.mAlpha));
            this.mPaintFill.setAlpha(modulateAlpha(prevAlpha, this.mAlpha));
            canvas.save();
            if (!this.mIsVertical) {
                canvas.drawCircle(
                        AbsSeekBar.this.mThumbPosX,
                        AbsSeekBar.this.getHeight() / 2.0f,
                        this.mRadiusForAni,
                        this.mPaintFill);
                canvas.drawCircle(
                        AbsSeekBar.this.mThumbPosX,
                        AbsSeekBar.this.getHeight() / 2.0f,
                        this.mRadiusForAni,
                        this.mPaint);
            } else {
                canvas.drawCircle(
                        AbsSeekBar.this.getWidth() / 2.0f,
                        AbsSeekBar.this.mThumbPosX,
                        this.mRadiusForAni,
                        this.mPaintFill);
                canvas.drawCircle(
                        AbsSeekBar.this.getWidth() / 2.0f,
                        AbsSeekBar.this.mThumbPosX,
                        this.mRadiusForAni,
                        this.mPaint);
            }
            canvas.restore();
            this.mPaint.setAlpha(prevAlpha);
            this.mPaintFill.setAlpha(prevAlpha);
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
        public boolean isStateful() {
            return true;
        }

        @Override // android.graphics.drawable.Drawable
        public void setTintList(ColorStateList tint) {
            super.setTintList(tint);
            if (tint != null) {
                this.mColorStateList = tint;
                this.mColor =
                        this.mColorStateList.getColorForState(
                                AbsSeekBar.this.getDrawableState(), this.mColor);
                this.mPaint.setColor(this.mColor);
                invalidateSelf();
            }
        }

        @Override // android.graphics.drawable.Drawable
        protected boolean onStateChange(int[] stateSet) {
            boolean changed = super.onStateChange(stateSet);
            int color = this.mColorStateList.getColorForState(stateSet, this.mColor);
            if (this.mColor != color) {
                this.mColor = color;
                this.mPaint.setColor(this.mColor);
                invalidateSelf();
            }
            boolean enabled = false;
            boolean pressed = false;
            boolean z = false;
            for (int state : stateSet) {
                if (state == 16842910) {
                    enabled = true;
                } else if (state == 16842919) {
                    pressed = true;
                }
            }
            if (enabled && pressed) {
                z = true;
            }
            startThumbAnimation(z);
            return changed;
        }

        private void startThumbAnimation(boolean isChanged) {
            if (this.mIsStateChanged != isChanged) {
                if (isChanged) {
                    startPressedAnimation();
                } else {
                    startReleasedAnimation();
                }
                this.mIsStateChanged = isChanged;
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
        public void setRadius(int radius) {
            this.mRadiusForAni = radius;
        }

        @Override // android.graphics.drawable.Drawable
        public void setAlpha(int alpha) {
            this.mAlpha = alpha;
            invalidateSelf();
        }

        private int modulateAlpha(int paintAlpha, int alpha) {
            int scale = (alpha >>> 7) + alpha;
            return (paintAlpha * scale) >>> 8;
        }

        @Override // android.graphics.drawable.Drawable
        public void setColorFilter(ColorFilter colorFilter) {
            this.mPaint.setColorFilter(colorFilter);
            invalidateSelf();
        }

        @Override // android.graphics.drawable.Drawable
        public int getOpacity() {
            Paint p = this.mPaint;
            if (p.getXfermode() == null) {
                int alpha = p.getAlpha();
                if (alpha == 0) {
                    return -2;
                }
                if (alpha == 255) {
                    return -1;
                }
                return -3;
            }
            return -3;
        }
    }
}
