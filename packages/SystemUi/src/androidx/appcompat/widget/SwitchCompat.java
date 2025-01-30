package androidx.appcompat.widget;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.text.InputFilter;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.util.Property;
import android.view.ActionMode;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.Animation;
import android.view.animation.PathInterpolator;
import android.view.animation.Transformation;
import android.widget.CompoundButton;
import androidx.appcompat.R$styleable;
import androidx.appcompat.text.AllCapsTransformationMethod;
import androidx.core.view.ViewCompat;
import androidx.core.widget.TextViewCompat;
import androidx.emoji2.text.EmojiCompat;
import androidx.reflect.view.SeslHapticFeedbackConstantsReflector;
import androidx.reflect.view.SeslViewReflector;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class SwitchCompat extends CompoundButton {
    public static final int[] CHECKED_STATE_SET;
    public CharSequence mAccessibilityTextOff;
    public CharSequence mAccessibilityTextOn;
    public AppCompatEmojiTextHelper mAppCompatEmojiTextHelper;
    public EmojiCompatInitCallback mEmojiCompatInitCallback;
    public boolean mHasThumbTint;
    public final boolean mHasThumbTintMode;
    public boolean mHasTrackTint;
    public final boolean mHasTrackTintMode;
    public final PathInterpolator mInterpolator;
    public Layout mOffLayout;
    public Layout mOnLayout;
    public ThumbAnimation mPositionAnimator;
    public final boolean mShowText;
    public final boolean mSplitTrack;
    public int mSwitchBottom;
    public int mSwitchHeight;
    public int mSwitchLeft;
    public final int mSwitchPadding;
    public int mSwitchRight;
    public int mSwitchTop;
    public AllCapsTransformationMethod mSwitchTransformationMethod;
    public int mSwitchWidth;
    public final Rect mTempRect;
    public ColorStateList mTextColors;
    public CharSequence mTextOff;
    public CharSequence mTextOffTransformed;
    public CharSequence mTextOn;
    public CharSequence mTextOnTransformed;
    public final TextPaint mTextPaint;
    public Drawable mThumbDrawable;
    public float mThumbPosition;
    public final int mThumbTextPadding;
    public ColorStateList mThumbTintList;
    public final PorterDuff.Mode mThumbTintMode;
    public int mThumbWidth;
    public int mTouchMode;
    public final int mTouchSlop;
    public float mTouchX;
    public float mTouchY;
    public Drawable mTrackDrawable;
    public int mTrackMargin;
    public Drawable mTrackOffDrawable;
    public Drawable mTrackOnDrawable;
    public ColorStateList mTrackTintList;
    public final PorterDuff.Mode mTrackTintMode;
    public final VelocityTracker mVelocityTracker;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class EmojiCompatInitCallback extends EmojiCompat.InitCallback {
        public final Reference mOuterWeakRef;

        public EmojiCompatInitCallback(SwitchCompat switchCompat) {
            this.mOuterWeakRef = new WeakReference(switchCompat);
        }

        @Override // androidx.emoji2.text.EmojiCompat.InitCallback
        public final void onFailed() {
            SwitchCompat switchCompat = (SwitchCompat) this.mOuterWeakRef.get();
            if (switchCompat != null) {
                switchCompat.setTextOnInternal(switchCompat.mTextOn);
                switchCompat.setTextOffInternal(switchCompat.mTextOff);
                switchCompat.requestLayout();
            }
        }

        @Override // androidx.emoji2.text.EmojiCompat.InitCallback
        public final void onInitialized() {
            SwitchCompat switchCompat = (SwitchCompat) this.mOuterWeakRef.get();
            if (switchCompat != null) {
                switchCompat.setTextOnInternal(switchCompat.mTextOn);
                switchCompat.setTextOffInternal(switchCompat.mTextOff);
                switchCompat.requestLayout();
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ThumbAnimation extends Animation {
        public final float mDiff;
        public final float mStartPosition;

        public ThumbAnimation(float f, float f2) {
            this.mStartPosition = f;
            this.mDiff = f2 - f;
        }

        @Override // android.view.animation.Animation
        public final void applyTransformation(float f, Transformation transformation) {
            SwitchCompat.this.setThumbPosition((this.mDiff * f) + this.mStartPosition);
        }
    }

    static {
        new Property(Float.class, "thumbPos") { // from class: androidx.appcompat.widget.SwitchCompat.1
            @Override // android.util.Property
            public final Object get(Object obj) {
                return Float.valueOf(((SwitchCompat) obj).mThumbPosition);
            }

            @Override // android.util.Property
            public final void set(Object obj, Object obj2) {
                ((SwitchCompat) obj).setThumbPosition(((Float) obj2).floatValue());
            }
        };
        CHECKED_STATE_SET = new int[]{R.attr.state_checked};
    }

    public SwitchCompat(Context context) {
        this(context, null);
    }

    public final void applyThumbTint() {
        Drawable drawable = this.mThumbDrawable;
        if (drawable != null) {
            if (this.mHasThumbTint || this.mHasThumbTintMode) {
                Drawable mutate = drawable.mutate();
                this.mThumbDrawable = mutate;
                if (this.mHasThumbTint) {
                    mutate.setTintList(this.mThumbTintList);
                }
                if (this.mHasThumbTintMode) {
                    this.mThumbDrawable.setTintMode(this.mThumbTintMode);
                }
                if (this.mThumbDrawable.isStateful()) {
                    this.mThumbDrawable.setState(getDrawableState());
                }
            }
        }
    }

    public final void applyTrackTint() {
        Drawable drawable = this.mTrackDrawable;
        if (drawable != null) {
            if (this.mHasTrackTint || this.mHasTrackTintMode) {
                Drawable mutate = drawable.mutate();
                this.mTrackDrawable = mutate;
                if (this.mHasTrackTint) {
                    mutate.setTintList(this.mTrackTintList);
                }
                if (this.mHasTrackTintMode) {
                    this.mTrackDrawable.setTintMode(this.mTrackTintMode);
                }
                if (this.mTrackDrawable.isStateful()) {
                    this.mTrackDrawable.setState(getDrawableState());
                }
            }
        }
    }

    public final boolean canHapticFeedback(boolean z) {
        return z != isChecked() && hasWindowFocus() && SeslViewReflector.isVisibleToUser(this) && !isTemporarilyDetached();
    }

    @Override // android.view.View
    public final void draw(Canvas canvas) {
        int i;
        int i2;
        Rect rect = this.mTempRect;
        int i3 = this.mSwitchLeft;
        int i4 = this.mSwitchTop;
        int i5 = this.mSwitchRight;
        int i6 = this.mSwitchBottom;
        int thumbScrollRange = ((int) (((ViewUtils.isLayoutRtl(this) ? 1.0f - this.mThumbPosition : this.mThumbPosition) * getThumbScrollRange()) + 0.5f)) + i3;
        Drawable drawable = this.mThumbDrawable;
        Rect opticalBounds = drawable != null ? DrawableUtils.getOpticalBounds(drawable) : DrawableUtils.INSETS_NONE;
        Drawable drawable2 = this.mTrackDrawable;
        if (drawable2 != null) {
            drawable2.getPadding(rect);
            int i7 = rect.left;
            thumbScrollRange += i7;
            int i8 = this.mTrackMargin / 2;
            int i9 = i3 + i8;
            int i10 = i5 - i8;
            if (opticalBounds != null) {
                int i11 = opticalBounds.left;
                if (i11 > i7) {
                    i9 += i11 - i7;
                }
                int i12 = opticalBounds.top;
                int i13 = rect.top;
                i = i12 > i13 ? (i12 - i13) + i4 : i4;
                int i14 = opticalBounds.right;
                int i15 = rect.right;
                if (i14 > i15) {
                    i10 -= i14 - i15;
                }
                int i16 = opticalBounds.bottom;
                int i17 = rect.bottom;
                if (i16 > i17) {
                    i2 = i6 - (i16 - i17);
                    this.mTrackDrawable.setBounds(i9, i, i10, i2);
                }
            } else {
                i = i4;
            }
            i2 = i6;
            this.mTrackDrawable.setBounds(i9, i, i10, i2);
        }
        Drawable drawable3 = this.mThumbDrawable;
        if (drawable3 != null) {
            drawable3.getPadding(rect);
            int i18 = thumbScrollRange - rect.left;
            int i19 = thumbScrollRange + this.mThumbWidth + rect.right;
            this.mThumbDrawable.setBounds(i18, i4, i19, i6);
            Drawable background = getBackground();
            if (background != null) {
                background.setHotspotBounds(i18, i4, i19, i6);
            }
        }
        super.draw(canvas);
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    public final void drawableHotspotChanged(float f, float f2) {
        super.drawableHotspotChanged(f, f2);
        Drawable drawable = this.mThumbDrawable;
        if (drawable != null) {
            drawable.setHotspot(f, f2);
        }
        Drawable drawable2 = this.mTrackDrawable;
        if (drawable2 != null) {
            drawable2.setHotspot(f, f2);
        }
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    public final void drawableStateChanged() {
        super.drawableStateChanged();
        int[] drawableState = getDrawableState();
        Drawable drawable = this.mThumbDrawable;
        boolean z = false;
        if (drawable != null && drawable.isStateful()) {
            z = false | drawable.setState(drawableState);
        }
        Drawable drawable2 = this.mTrackDrawable;
        if (drawable2 != null && drawable2.isStateful()) {
            z |= drawable2.setState(drawableState);
        }
        if (z) {
            invalidate();
        }
    }

    @Override // android.widget.CompoundButton, android.widget.TextView
    public final int getCompoundPaddingLeft() {
        if (!ViewUtils.isLayoutRtl(this)) {
            return super.getCompoundPaddingLeft();
        }
        int compoundPaddingLeft = super.getCompoundPaddingLeft() + this.mSwitchWidth + this.mTrackMargin;
        return !TextUtils.isEmpty(getText()) ? compoundPaddingLeft + this.mSwitchPadding : compoundPaddingLeft;
    }

    @Override // android.widget.CompoundButton, android.widget.TextView
    public final int getCompoundPaddingRight() {
        if (ViewUtils.isLayoutRtl(this)) {
            return super.getCompoundPaddingRight();
        }
        int compoundPaddingRight = super.getCompoundPaddingRight() + this.mSwitchWidth + this.mTrackMargin;
        return !TextUtils.isEmpty(getText()) ? compoundPaddingRight + this.mSwitchPadding : compoundPaddingRight;
    }

    @Override // android.widget.TextView
    public final ActionMode.Callback getCustomSelectionActionModeCallback() {
        return TextViewCompat.unwrapCustomSelectionActionModeCallback(super.getCustomSelectionActionModeCallback());
    }

    public final AppCompatEmojiTextHelper getEmojiTextViewHelper() {
        if (this.mAppCompatEmojiTextHelper == null) {
            this.mAppCompatEmojiTextHelper = new AppCompatEmojiTextHelper(this);
        }
        return this.mAppCompatEmojiTextHelper;
    }

    public final int getThumbScrollRange() {
        Drawable drawable = this.mTrackDrawable;
        if (drawable == null) {
            return 0;
        }
        Rect rect = this.mTempRect;
        drawable.getPadding(rect);
        Drawable drawable2 = this.mThumbDrawable;
        Rect opticalBounds = drawable2 != null ? DrawableUtils.getOpticalBounds(drawable2) : DrawableUtils.INSETS_NONE;
        return (((((this.mSwitchWidth + this.mTrackMargin) - this.mThumbWidth) - rect.left) - rect.right) - opticalBounds.left) - opticalBounds.right;
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    public final void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable drawable = this.mThumbDrawable;
        if (drawable != null) {
            drawable.jumpToCurrentState();
        }
        Drawable drawable2 = this.mTrackDrawable;
        if (drawable2 != null) {
            drawable2.jumpToCurrentState();
        }
        if (this.mPositionAnimator != null) {
            clearAnimation();
            this.mPositionAnimator = null;
        }
        setThumbPosition(isChecked() ? 1.0f : 0.0f);
    }

    public final Layout makeLayout(CharSequence charSequence) {
        return new StaticLayout(charSequence, this.mTextPaint, charSequence != null ? (int) Math.ceil(Layout.getDesiredWidth(charSequence, r2)) : 0, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
    }

    @Override // android.widget.TextView, android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mSwitchWidth = getResources().getDimensionPixelSize(com.android.systemui.R.dimen.sesl_switch_width);
        this.mAccessibilityTextOn = getResources().getString(com.android.systemui.R.string.sesl_switch_on);
        this.mAccessibilityTextOff = getResources().getString(com.android.systemui.R.string.sesl_switch_off);
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    public int[] onCreateDrawableState(int i) {
        int[] onCreateDrawableState = super.onCreateDrawableState(i + 1);
        if (isChecked()) {
            CompoundButton.mergeDrawableStates(onCreateDrawableState, CHECKED_STATE_SET);
        }
        return onCreateDrawableState;
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    public final void onDraw(Canvas canvas) {
        int width;
        super.onDraw(canvas);
        Rect rect = this.mTempRect;
        Drawable drawable = this.mTrackDrawable;
        if (drawable != null) {
            drawable.getPadding(rect);
        } else {
            rect.setEmpty();
        }
        int i = this.mSwitchTop;
        int i2 = this.mSwitchBottom;
        int i3 = i + rect.top;
        int i4 = i2 - rect.bottom;
        Drawable drawable2 = this.mThumbDrawable;
        if (drawable != null) {
            if (!this.mSplitTrack || drawable2 == null) {
                Drawable drawable3 = isChecked() ? this.mTrackOffDrawable : this.mTrackOnDrawable;
                drawable3.setBounds(drawable.getBounds());
                int i5 = (int) (this.mThumbPosition * 255.0f);
                if (i5 > 255) {
                    i5 = 255;
                } else if (i5 < 0) {
                    i5 = 0;
                }
                int i6 = 255 - i5;
                if (isChecked()) {
                    drawable.setAlpha(i5);
                    drawable3.setAlpha(i6);
                } else {
                    drawable.setAlpha(i6);
                    drawable3.setAlpha(i5);
                }
                drawable.draw(canvas);
                drawable3.draw(canvas);
            } else {
                Rect opticalBounds = DrawableUtils.getOpticalBounds(drawable2);
                drawable2.copyBounds(rect);
                rect.left += opticalBounds.left;
                rect.right -= opticalBounds.right;
                int save = canvas.save();
                canvas.clipRect(rect, Region.Op.DIFFERENCE);
                drawable.draw(canvas);
                canvas.restoreToCount(save);
            }
        }
        int save2 = canvas.save();
        if (drawable2 != null) {
            drawable2.draw(canvas);
        }
        Layout layout = (this.mThumbPosition > 0.5f ? 1 : (this.mThumbPosition == 0.5f ? 0 : -1)) > 0 ? this.mOnLayout : this.mOffLayout;
        if (layout != null) {
            int[] drawableState = getDrawableState();
            ColorStateList colorStateList = this.mTextColors;
            if (colorStateList != null) {
                this.mTextPaint.setColor(colorStateList.getColorForState(drawableState, 0));
            }
            this.mTextPaint.drawableState = drawableState;
            if (drawable2 != null) {
                Rect bounds = drawable2.getBounds();
                width = bounds.left + bounds.right;
            } else {
                width = getWidth();
            }
            canvas.translate((width / 2) - (layout.getWidth() / 2), ((i3 + i4) / 2) - (layout.getHeight() / 2));
            layout.draw(canvas);
        }
        canvas.restoreToCount(save2);
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName("android.widget.Switch");
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName("android.widget.Switch");
    }

    @Override // android.widget.TextView, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int width;
        int i6;
        int i7;
        int i8;
        super.onLayout(z, i, i2, i3, i4);
        int i9 = 0;
        if (this.mThumbDrawable != null) {
            Rect rect = this.mTempRect;
            Drawable drawable = this.mTrackDrawable;
            if (drawable != null) {
                drawable.getPadding(rect);
            } else {
                rect.setEmpty();
            }
            Rect opticalBounds = DrawableUtils.getOpticalBounds(this.mThumbDrawable);
            i5 = Math.max(0, opticalBounds.left - rect.left);
            i9 = Math.max(0, opticalBounds.right - rect.right);
        } else {
            i5 = 0;
        }
        if (ViewUtils.isLayoutRtl(this)) {
            i6 = getPaddingLeft() + i5;
            width = (((this.mSwitchWidth + i6) + this.mTrackMargin) - i5) - i9;
        } else {
            width = (getWidth() - getPaddingRight()) - i9;
            i6 = ((width - this.mSwitchWidth) - this.mTrackMargin) + i5 + i9;
        }
        int gravity = getGravity() & 112;
        if (gravity == 16) {
            int height = ((getHeight() + getPaddingTop()) - getPaddingBottom()) / 2;
            int i10 = this.mSwitchHeight;
            int i11 = height - (i10 / 2);
            i7 = i10 + i11;
            i8 = i11;
        } else if (gravity != 80) {
            i8 = getPaddingTop();
            i7 = this.mSwitchHeight + i8;
        } else {
            i7 = getHeight() - getPaddingBottom();
            i8 = i7 - this.mSwitchHeight;
        }
        this.mSwitchLeft = i6;
        this.mSwitchTop = i8;
        this.mSwitchBottom = i7;
        this.mSwitchRight = width;
    }

    @Override // android.widget.TextView, android.view.View
    public final void onMeasure(int i, int i2) {
        int i3;
        int i4;
        int i5;
        if (this.mShowText) {
            if (this.mOnLayout == null) {
                this.mOnLayout = makeLayout(this.mTextOnTransformed);
            }
            if (this.mOffLayout == null) {
                this.mOffLayout = makeLayout(this.mTextOffTransformed);
            }
        }
        Rect rect = this.mTempRect;
        Drawable drawable = this.mThumbDrawable;
        if (drawable != null) {
            drawable.getPadding(rect);
            i3 = (this.mThumbDrawable.getIntrinsicWidth() - rect.left) - rect.right;
            i4 = this.mThumbDrawable.getIntrinsicHeight();
        } else {
            i3 = 0;
            i4 = 0;
        }
        this.mThumbWidth = Math.max(this.mShowText ? (this.mThumbTextPadding * 2) + Math.max(this.mOnLayout.getWidth(), this.mOffLayout.getWidth()) : 0, i3);
        Drawable drawable2 = this.mTrackDrawable;
        if (drawable2 != null) {
            drawable2.getPadding(rect);
            i5 = this.mTrackDrawable.getIntrinsicHeight();
        } else {
            rect.setEmpty();
            i5 = 0;
        }
        int i6 = rect.left;
        int i7 = rect.right;
        Drawable drawable3 = this.mThumbDrawable;
        if (drawable3 != null) {
            Rect opticalBounds = DrawableUtils.getOpticalBounds(drawable3);
            Math.max(i6, opticalBounds.left);
            Math.max(i7, opticalBounds.right);
        }
        int max = Math.max(i5, i4);
        this.mSwitchHeight = max;
        this.mTrackMargin = ((float) this.mThumbWidth) / ((float) this.mSwitchWidth) > 0.5714286f ? (int) Math.ceil(r1 - (r4 * 0.5714286f)) : 0;
        super.onMeasure(i, i2);
        if (getMeasuredHeight() < max) {
            setMeasuredDimension(getMeasuredWidthAndState(), max);
        }
    }

    @Override // android.view.View
    public final void onPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onPopulateAccessibilityEvent(accessibilityEvent);
        CharSequence charSequence = isChecked() ? this.mAccessibilityTextOn : this.mAccessibilityTextOff;
        if (charSequence != null) {
            accessibilityEvent.getText().add(charSequence);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:57:0x00da, code lost:
    
        if (r8 > 0.5f) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x00ec, code lost:
    
        r0 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:6:0x0018, code lost:
    
        if (r0 != 3) goto L97;
     */
    @Override // android.widget.TextView, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z;
        this.mVelocityTracker.addMovement(motionEvent);
        int actionMasked = motionEvent.getActionMasked();
        float f = 1.0f;
        boolean z2 = false;
        if (actionMasked != 0) {
            if (actionMasked != 1) {
                if (actionMasked == 2) {
                    int i = this.mTouchMode;
                    if (i == 1) {
                        float x = motionEvent.getX();
                        float y = motionEvent.getY();
                        if (Math.abs(x - this.mTouchX) > this.mTouchSlop || Math.abs(y - this.mTouchY) > this.mTouchSlop) {
                            this.mTouchMode = 2;
                            getParent().requestDisallowInterceptTouchEvent(true);
                            this.mTouchX = x;
                            this.mTouchY = y;
                            return true;
                        }
                    } else if (i == 2) {
                        float x2 = motionEvent.getX();
                        int thumbScrollRange = getThumbScrollRange();
                        float f2 = x2 - this.mTouchX;
                        float f3 = thumbScrollRange != 0 ? f2 / thumbScrollRange : f2 > 0.0f ? 1.0f : -1.0f;
                        if (ViewUtils.isLayoutRtl(this)) {
                            f3 = -f3;
                        }
                        float f4 = this.mThumbPosition;
                        float f5 = f3 + f4;
                        if (f5 < 0.0f) {
                            f = 0.0f;
                        } else if (f5 <= 1.0f) {
                            f = f5;
                        }
                        if (f != f4) {
                            this.mTouchX = x2;
                            setThumbPosition(f);
                        }
                        return true;
                    }
                }
            }
            if (this.mTouchMode == 2) {
                this.mTouchMode = 0;
                boolean z3 = motionEvent.getAction() == 1 && isEnabled();
                boolean isChecked = isChecked();
                if (z3) {
                    this.mVelocityTracker.computeCurrentVelocity(1000);
                    float xVelocity = this.mVelocityTracker.getXVelocity();
                    if (Math.abs(xVelocity) <= 2000.0f && Math.abs(xVelocity) <= 500.0f) {
                        float f6 = this.mThumbPosition;
                        if (f6 == 0.0f || f6 == 1.0f) {
                        }
                    }
                    z = !ViewUtils.isLayoutRtl(this) ? false : false;
                } else {
                    z = isChecked;
                }
                if (z != isChecked) {
                    playSoundEffect(0);
                }
                setChecked(z);
                MotionEvent obtain = MotionEvent.obtain(motionEvent);
                obtain.setAction(3);
                super.onTouchEvent(obtain);
                obtain.recycle();
                super.onTouchEvent(motionEvent);
                return true;
            }
            this.mTouchMode = 0;
            this.mVelocityTracker.clear();
        } else {
            float x3 = motionEvent.getX();
            float y2 = motionEvent.getY();
            if (isEnabled()) {
                if (this.mThumbDrawable != null) {
                    int thumbScrollRange2 = (int) (((ViewUtils.isLayoutRtl(this) ? 1.0f - this.mThumbPosition : this.mThumbPosition) * getThumbScrollRange()) + 0.5f);
                    this.mThumbDrawable.getPadding(this.mTempRect);
                    int i2 = this.mSwitchTop;
                    int i3 = this.mTouchSlop;
                    int i4 = i2 - i3;
                    int i5 = (this.mSwitchLeft + thumbScrollRange2) - i3;
                    int i6 = this.mThumbWidth + i5;
                    Rect rect = this.mTempRect;
                    int i7 = i6 + rect.left + rect.right + i3;
                    int i8 = this.mSwitchBottom + i3;
                    if (x3 > i5 && x3 < i7 && y2 > i4 && y2 < i8) {
                        z2 = true;
                    }
                }
                if (z2) {
                    this.mTouchMode = 1;
                    this.mTouchX = x3;
                    this.mTouchY = y2;
                }
            }
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override // android.widget.TextView
    public final void setAllCaps(boolean z) {
        super.setAllCaps(z);
        getEmojiTextViewHelper().setAllCaps(z);
    }

    @Override // android.widget.CompoundButton, android.widget.Checkable
    public void setChecked(boolean z) {
        if (canHapticFeedback(z)) {
            performHapticFeedback(SeslHapticFeedbackConstantsReflector.semGetVibrationIndex(27));
        }
        super.setChecked(z);
        final boolean isChecked = isChecked();
        if (isChecked) {
            setOnStateDescriptionOnRAndAbove();
        } else {
            setOffStateDescriptionOnRAndAbove();
        }
        if (getWindowToken() != null) {
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            if (ViewCompat.Api19Impl.isLaidOut(this)) {
                ThumbAnimation thumbAnimation = this.mPositionAnimator;
                if (thumbAnimation != null && thumbAnimation != null) {
                    clearAnimation();
                    this.mPositionAnimator = null;
                }
                ThumbAnimation thumbAnimation2 = new ThumbAnimation(this.mThumbPosition, isChecked ? 1.0f : 0.0f);
                this.mPositionAnimator = thumbAnimation2;
                thumbAnimation2.setDuration(150L);
                this.mPositionAnimator.setDuration(300L);
                this.mPositionAnimator.setInterpolator(this.mInterpolator);
                this.mPositionAnimator.setAnimationListener(new Animation.AnimationListener() { // from class: androidx.appcompat.widget.SwitchCompat.2
                    @Override // android.view.animation.Animation.AnimationListener
                    public final void onAnimationEnd(Animation animation) {
                        SwitchCompat switchCompat = SwitchCompat.this;
                        if (switchCompat.mPositionAnimator == animation) {
                            switchCompat.setThumbPosition(isChecked ? 1.0f : 0.0f);
                            SwitchCompat.this.mPositionAnimator = null;
                        }
                    }

                    @Override // android.view.animation.Animation.AnimationListener
                    public final void onAnimationRepeat(Animation animation) {
                    }

                    @Override // android.view.animation.Animation.AnimationListener
                    public final void onAnimationStart(Animation animation) {
                    }
                });
                startAnimation(this.mPositionAnimator);
                return;
            }
        }
        if (this.mPositionAnimator != null) {
            clearAnimation();
            this.mPositionAnimator = null;
        }
        setThumbPosition(isChecked ? 1.0f : 0.0f);
    }

    public final void setCheckedWithoutAnimation(boolean z) {
        super.setChecked(z);
        boolean isChecked = isChecked();
        if (isChecked) {
            setOnStateDescriptionOnRAndAbove();
        } else {
            setOffStateDescriptionOnRAndAbove();
        }
        if (this.mPositionAnimator != null) {
            clearAnimation();
            this.mPositionAnimator = null;
        }
        setThumbPosition(isChecked ? 1.0f : 0.0f);
    }

    @Override // android.widget.TextView
    public final void setCustomSelectionActionModeCallback(ActionMode.Callback callback) {
        super.setCustomSelectionActionModeCallback(callback);
    }

    @Override // android.widget.TextView
    public final void setFilters(InputFilter[] inputFilterArr) {
        super.setFilters(getEmojiTextViewHelper().getFilters(inputFilterArr));
    }

    public final void setOffStateDescriptionOnRAndAbove() {
        Object obj = this.mAccessibilityTextOff;
        if (obj == null) {
            obj = getResources().getString(com.android.systemui.R.string.abc_capital_off);
        }
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        new ViewCompat.C01573(com.android.systemui.R.id.tag_state_description, CharSequence.class, 64, 30).set(this, obj);
    }

    public final void setOnStateDescriptionOnRAndAbove() {
        Object obj = this.mAccessibilityTextOn;
        if (obj == null) {
            obj = getResources().getString(com.android.systemui.R.string.abc_capital_on);
        }
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        new ViewCompat.C01573(com.android.systemui.R.id.tag_state_description, CharSequence.class, 64, 30).set(this, obj);
    }

    public final void setSwitchTypeface(Typeface typeface) {
        if ((this.mTextPaint.getTypeface() == null || this.mTextPaint.getTypeface().equals(typeface)) && (this.mTextPaint.getTypeface() != null || typeface == null)) {
            return;
        }
        this.mTextPaint.setTypeface(typeface);
        requestLayout();
        invalidate();
    }

    public final void setTextOffInternal(CharSequence charSequence) {
        this.mTextOff = charSequence;
        AppCompatEmojiTextHelper emojiTextViewHelper = getEmojiTextViewHelper();
        TransformationMethod wrapTransformationMethod = emojiTextViewHelper.mEmojiTextViewHelper.mHelper.wrapTransformationMethod(this.mSwitchTransformationMethod);
        if (wrapTransformationMethod != null) {
            charSequence = wrapTransformationMethod.getTransformation(charSequence, this);
        }
        this.mTextOffTransformed = charSequence;
        this.mOffLayout = null;
        if (this.mShowText) {
            setupEmojiCompatLoadCallback();
        }
    }

    public final void setTextOnInternal(CharSequence charSequence) {
        this.mTextOn = charSequence;
        AppCompatEmojiTextHelper emojiTextViewHelper = getEmojiTextViewHelper();
        TransformationMethod wrapTransformationMethod = emojiTextViewHelper.mEmojiTextViewHelper.mHelper.wrapTransformationMethod(this.mSwitchTransformationMethod);
        if (wrapTransformationMethod != null) {
            charSequence = wrapTransformationMethod.getTransformation(charSequence, this);
        }
        this.mTextOnTransformed = charSequence;
        this.mOnLayout = null;
        if (this.mShowText) {
            setupEmojiCompatLoadCallback();
        }
    }

    public final void setThumbPosition(float f) {
        this.mThumbPosition = f;
        invalidate();
    }

    public final void setupEmojiCompatLoadCallback() {
        if (this.mEmojiCompatInitCallback == null && this.mAppCompatEmojiTextHelper.mEmojiTextViewHelper.mHelper.isEnabled()) {
            if (EmojiCompat.sInstance != null) {
                EmojiCompat emojiCompat = EmojiCompat.get();
                int loadState = emojiCompat.getLoadState();
                if (loadState == 3 || loadState == 0) {
                    EmojiCompatInitCallback emojiCompatInitCallback = new EmojiCompatInitCallback(this);
                    this.mEmojiCompatInitCallback = emojiCompatInitCallback;
                    emojiCompat.registerInitCallback(emojiCompatInitCallback);
                }
            }
        }
    }

    @Override // android.widget.CompoundButton, android.widget.Checkable
    public final void toggle() {
        setChecked(!isChecked());
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    public final boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.mThumbDrawable || drawable == this.mTrackDrawable;
    }

    public SwitchCompat(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, com.android.systemui.R.attr.switchStyle);
    }

    public SwitchCompat(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Typeface typeface;
        Typeface create;
        this.mThumbTintList = null;
        this.mThumbTintMode = null;
        this.mHasThumbTint = false;
        this.mHasThumbTintMode = false;
        this.mTrackTintList = null;
        this.mTrackTintMode = null;
        this.mHasTrackTint = false;
        this.mHasTrackTintMode = false;
        this.mVelocityTracker = VelocityTracker.obtain();
        this.mTempRect = new Rect();
        this.mTrackMargin = 0;
        ThemeUtils.checkAppCompatTheme(getContext(), this);
        TextPaint textPaint = new TextPaint(1);
        this.mTextPaint = textPaint;
        textPaint.density = getResources().getDisplayMetrics().density;
        int i2 = Settings.System.getString(context.getContentResolver(), "current_sec_active_themepackage") != null ? com.android.systemui.R.attr.themeSwitchStyle : i;
        int[] iArr = R$styleable.SwitchCompat;
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, iArr, i2, 0);
        TypedArray typedArray = obtainStyledAttributes.mWrapped;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api29Impl.saveAttributeDataForStyleable(this, context, iArr, attributeSet, typedArray, i2, 0);
        Drawable drawable = obtainStyledAttributes.getDrawable(2);
        this.mThumbDrawable = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
        }
        Drawable drawable2 = obtainStyledAttributes.getDrawable(12);
        this.mTrackDrawable = drawable2;
        if (drawable2 != null) {
            drawable2.setCallback(this);
            Drawable.ConstantState constantState = this.mTrackDrawable.getConstantState();
            if (constantState != null) {
                this.mTrackOnDrawable = constantState.newDrawable();
                this.mTrackOffDrawable = constantState.newDrawable();
            } else {
                Drawable drawable3 = this.mTrackDrawable;
                this.mTrackOnDrawable = drawable3;
                this.mTrackOffDrawable = drawable3;
            }
            this.mTrackOnDrawable.setState(new int[]{R.attr.state_enabled, R.attr.state_checked});
            this.mTrackOffDrawable.setState(new int[]{R.attr.state_enabled, -16842912});
        }
        setTextOnInternal(obtainStyledAttributes.getText(0));
        setTextOffInternal(obtainStyledAttributes.getText(1));
        this.mShowText = obtainStyledAttributes.getBoolean(3, true);
        this.mThumbTextPadding = obtainStyledAttributes.getDimensionPixelSize(9, 0);
        obtainStyledAttributes.getDimensionPixelSize(5, 0);
        this.mSwitchPadding = obtainStyledAttributes.getDimensionPixelSize(7, 0);
        this.mSplitTrack = obtainStyledAttributes.getBoolean(4, false);
        ColorStateList colorStateList = obtainStyledAttributes.getColorStateList(10);
        if (colorStateList != null) {
            this.mThumbTintList = colorStateList;
            this.mHasThumbTint = true;
        }
        PorterDuff.Mode parseTintMode = DrawableUtils.parseTintMode(obtainStyledAttributes.getInt(11, -1), null);
        if (parseTintMode != null) {
            this.mThumbTintMode = parseTintMode;
            this.mHasThumbTintMode = true;
        }
        if (this.mHasThumbTint || this.mHasThumbTintMode) {
            applyThumbTint();
        }
        ColorStateList colorStateList2 = obtainStyledAttributes.getColorStateList(13);
        if (colorStateList2 != null) {
            this.mTrackTintList = colorStateList2;
            this.mHasTrackTint = true;
        }
        PorterDuff.Mode parseTintMode2 = DrawableUtils.parseTintMode(obtainStyledAttributes.getInt(14, -1), null);
        if (parseTintMode2 != null) {
            this.mTrackTintMode = parseTintMode2;
            this.mHasTrackTintMode = true;
        }
        if (this.mHasTrackTint || this.mHasTrackTintMode) {
            applyTrackTint();
        }
        int resourceId = obtainStyledAttributes.getResourceId(8, 0);
        if (resourceId != 0) {
            TintTypedArray obtainStyledAttributes2 = TintTypedArray.obtainStyledAttributes(context, resourceId, R$styleable.TextAppearance);
            ColorStateList colorStateList3 = obtainStyledAttributes2.getColorStateList(3);
            if (colorStateList3 != null) {
                this.mTextColors = colorStateList3;
            } else {
                this.mTextColors = getTextColors();
            }
            int dimensionPixelSize = obtainStyledAttributes2.getDimensionPixelSize(0, 0);
            if (dimensionPixelSize != 0) {
                float f = dimensionPixelSize;
                if (f != textPaint.getTextSize()) {
                    textPaint.setTextSize(f);
                    requestLayout();
                }
            }
            int i3 = obtainStyledAttributes2.getInt(1, -1);
            int i4 = obtainStyledAttributes2.getInt(2, -1);
            if (i3 == 1) {
                typeface = Typeface.SANS_SERIF;
            } else if (i3 != 2) {
                typeface = i3 != 3 ? null : Typeface.MONOSPACE;
            } else {
                typeface = Typeface.SERIF;
            }
            if (i4 > 0) {
                if (typeface == null) {
                    create = Typeface.defaultFromStyle(i4);
                } else {
                    create = Typeface.create(typeface, i4);
                }
                setSwitchTypeface(create);
                int i5 = (~(create != null ? create.getStyle() : 0)) & i4;
                textPaint.setFakeBoldText((i5 & 1) != 0);
                textPaint.setTextSkewX((2 & i5) != 0 ? -0.25f : 0.0f);
            } else {
                textPaint.setFakeBoldText(false);
                textPaint.setTextSkewX(0.0f);
                setSwitchTypeface(typeface);
            }
            if (obtainStyledAttributes2.getBoolean(14, false)) {
                this.mSwitchTransformationMethod = new AllCapsTransformationMethod(getContext());
            } else {
                this.mSwitchTransformationMethod = null;
            }
            setTextOnInternal(this.mTextOn);
            setTextOffInternal(this.mTextOff);
            obtainStyledAttributes2.recycle();
        }
        new AppCompatTextHelper(this).loadFromAttributes(attributeSet, i);
        obtainStyledAttributes.recycle();
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        viewConfiguration.getScaledMinimumFlingVelocity();
        getEmojiTextViewHelper().loadFromAttributes(attributeSet, i);
        this.mSwitchWidth = getResources().getDimensionPixelSize(com.android.systemui.R.dimen.sesl_switch_width);
        this.mAccessibilityTextOn = getResources().getString(com.android.systemui.R.string.sesl_switch_on);
        this.mAccessibilityTextOff = getResources().getString(com.android.systemui.R.string.sesl_switch_off);
        this.mInterpolator = new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f);
        refreshDrawableState();
        setChecked(isChecked());
    }
}
