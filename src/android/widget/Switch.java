package android.widget;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.Insets;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.provider.Settings;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.AllCapsTransformationMethod;
import android.text.method.TransformationMethod2;
import android.util.AttributeSet;
import android.util.FloatProperty;
import android.util.MathUtils;
import android.util.TypedValue;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.RemotableViewMethod;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewStructure;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.PathInterpolator;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.RemoteViews;
import com.android.internal.R;

@RemoteViews.RemoteView
/* loaded from: classes5.dex */
public class Switch extends CompoundButton {
    private static final int MAX_LOOP_COUNT = 100;
    private static final int MONOSPACE = 3;
    private static final int SANS = 1;
    private static final int SEM_THUMB_ANIMATION_DURATION = 300;
    private static final int SERIF = 2;
    private static final int THUMB_ANIMATION_DURATION = 250;
    private static final float THUMB_TRACK_WIDTH_RATIO = 0.5714286f;
    private static final int TOUCH_MODE_DOWN = 1;
    private static final int TOUCH_MODE_DRAGGING = 2;
    private static final int TOUCH_MODE_IDLE = 0;
    private boolean mHasThumbTint;
    private boolean mHasThumbTintMode;
    private boolean mHasTrackTint;
    private boolean mHasTrackTintMode;
    private boolean mIsMetaDataInActivity;
    private boolean mIsSamsungBasicInteraction;
    private boolean mIsSupportSemSwitchVI;
    private boolean mIsThemeChanged;
    private int mMinFlingVelocity;
    private Layout mOffLayout;
    private Layout mOnLayout;
    private ObjectAnimator mPositionAnimator;
    private boolean mShowText;
    private boolean mSplitTrack;
    private int mSwitchBottom;
    private int mSwitchHeight;
    private int mSwitchLeft;
    private int mSwitchMinWidth;
    private int mSwitchPadding;
    private int mSwitchRight;
    private int mSwitchTop;
    private TransformationMethod2 mSwitchTransformationMethod;
    private int mSwitchWidth;
    private final Rect mTempRect;
    private ColorStateList mTextColors;
    private CharSequence mTextOff;
    private CharSequence mTextOn;
    private TextPaint mTextPaint;
    private BlendMode mThumbBlendMode;
    private Drawable mThumbDrawable;
    private float mThumbPosition;
    private int mThumbTextPadding;
    private ColorStateList mThumbTintList;
    private int mThumbWidth;
    private int mTouchMode;
    private int mTouchSlop;
    private float mTouchX;
    private float mTouchY;
    private BlendMode mTrackBlendMode;
    private Drawable mTrackDrawable;
    private int mTrackMargin;
    private Drawable mTrackOffDrawable;
    private Drawable mTrackOnDrawable;
    private ColorStateList mTrackTintList;
    private boolean mUseFallbackLineSpacing;
    private VelocityTracker mVelocityTracker;
    private static final int[] CHECKED_STATE_SET = {16842912};
    private static final FloatProperty<Switch> THUMB_POS = new FloatProperty<Switch>("thumbPos") { // from class: android.widget.Switch.1
        @Override // android.util.Property
        public Float get(Switch r1) {
            return Float.valueOf(r1.mThumbPosition);
        }

        @Override // android.util.FloatProperty
        public void setValue(Switch r1, float f) {
            r1.setThumbPosition(f);
        }
    };

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<Switch> {
        private boolean mPropertiesMapped = false;
        private int mShowTextId;
        private int mSplitTrackId;
        private int mSwitchMinWidthId;
        private int mSwitchPaddingId;
        private int mTextOffId;
        private int mTextOnId;
        private int mThumbId;
        private int mThumbTextPaddingId;
        private int mThumbTintBlendModeId;
        private int mThumbTintId;
        private int mThumbTintModeId;
        private int mTrackId;
        private int mTrackTintBlendModeId;
        private int mTrackTintId;
        private int mTrackTintModeId;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(PropertyMapper propertyMapper) {
            this.mShowTextId = propertyMapper.mapBoolean("showText", 16843949);
            this.mSplitTrackId = propertyMapper.mapBoolean("splitTrack", 16843852);
            this.mSwitchMinWidthId = propertyMapper.mapInt("switchMinWidth", 16843632);
            this.mSwitchPaddingId = propertyMapper.mapInt("switchPadding", 16843633);
            this.mTextOffId = propertyMapper.mapObject("textOff", 16843045);
            this.mTextOnId = propertyMapper.mapObject("textOn", 16843044);
            this.mThumbId = propertyMapper.mapObject("thumb", 16843074);
            this.mThumbTextPaddingId = propertyMapper.mapInt("thumbTextPadding", 16843634);
            this.mThumbTintId = propertyMapper.mapObject("thumbTint", 16843889);
            this.mThumbTintBlendModeId = propertyMapper.mapObject("thumbTintBlendMode", 10);
            this.mThumbTintModeId = propertyMapper.mapObject("thumbTintMode", 16843890);
            this.mTrackId = propertyMapper.mapObject("track", 16843631);
            this.mTrackTintId = propertyMapper.mapObject("trackTint", 16843993);
            this.mTrackTintBlendModeId = propertyMapper.mapObject("trackTintBlendMode", 13);
            this.mTrackTintModeId = propertyMapper.mapObject("trackTintMode", 16843994);
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(Switch r3, PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readBoolean(this.mShowTextId, r3.getShowText());
            propertyReader.readBoolean(this.mSplitTrackId, r3.getSplitTrack());
            propertyReader.readInt(this.mSwitchMinWidthId, r3.getSwitchMinWidth());
            propertyReader.readInt(this.mSwitchPaddingId, r3.getSwitchPadding());
            propertyReader.readObject(this.mTextOffId, r3.getTextOff());
            propertyReader.readObject(this.mTextOnId, r3.getTextOn());
            propertyReader.readObject(this.mThumbId, r3.getThumbDrawable());
            propertyReader.readInt(this.mThumbTextPaddingId, r3.getThumbTextPadding());
            propertyReader.readObject(this.mThumbTintId, r3.getThumbTintList());
            propertyReader.readObject(this.mThumbTintBlendModeId, r3.getThumbTintBlendMode());
            propertyReader.readObject(this.mThumbTintModeId, r3.getThumbTintMode());
            propertyReader.readObject(this.mTrackId, r3.getTrackDrawable());
            propertyReader.readObject(this.mTrackTintId, r3.getTrackTintList());
            propertyReader.readObject(this.mTrackTintBlendModeId, r3.getTrackTintBlendMode());
            propertyReader.readObject(this.mTrackTintModeId, r3.getTrackTintMode());
        }
    }

    public Switch(Context context) {
        this(context, null);
    }

    public Switch(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16843839);
    }

    public Switch(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public Switch(Context context, AttributeSet attributeSet, int i, int i2) throws Resources.NotFoundException {
        ActivityInfo activityInfo;
        super(context, attributeSet, i, i2);
        this.mThumbTintList = null;
        this.mThumbBlendMode = null;
        this.mHasThumbTint = false;
        this.mHasThumbTintMode = false;
        this.mTrackTintList = null;
        this.mTrackBlendMode = null;
        this.mHasTrackTint = false;
        this.mHasTrackTintMode = false;
        this.mVelocityTracker = VelocityTracker.obtain();
        this.mIsSupportSemSwitchVI = false;
        this.mTrackMargin = 0;
        this.mIsSamsungBasicInteraction = View.sIsSamsungBasicInteraction;
        this.mIsMetaDataInActivity = false;
        this.mTempRect = new Rect();
        this.mTextPaint = new TextPaint(1);
        Resources resources = getResources();
        this.mTextPaint.density = resources.getDisplayMetrics().density;
        this.mTextPaint.setCompatibilityScaling(resources.getCompatibilityInfo().applicationScale);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.Switch, i, i2);
        saveAttributeDataForStyleable(context, R.styleable.Switch, attributeSet, typedArrayObtainStyledAttributes, i, i2);
        Activity activityContext = getActivityContext(context);
        if (activityContext != null && (activityInfo = activityContext.getActivityInfo()) != null && activityInfo.metaData != null) {
            String string = activityInfo.metaData.getString("SamsungBasicInteraction");
            this.mIsMetaDataInActivity = "SEP10".equals(string) || "SEP11".equals(string);
        }
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.parentIsDeviceDefault, typedValue, false);
        if (typedValue.data != 0) {
            this.mIsThemeChanged = Settings.System.getString(context.getContentResolver(), "current_sec_active_themepackage") != null;
        }
        TypedValue typedValue2 = new TypedValue();
        context.getTheme().resolveAttribute(16844176, typedValue2, true);
        if ((this.mIsSamsungBasicInteraction || this.mIsMetaDataInActivity) && typedArrayObtainStyledAttributes.getResourceId(2, 0) == 17304667) {
            if (this.mIsThemeChanged) {
                this.mThumbDrawable = typedValue2.data != 0 ? resources.getDrawable(R.drawable.sem_switch_thumb_material_anim_for_theme, context.getTheme()) : resources.getDrawable(R.drawable.sem_switch_thumb_material_dark_anim_for_theme, context.getTheme());
            } else {
                this.mThumbDrawable = typedValue2.data != 0 ? resources.getDrawable(R.drawable.sem_switch_thumb_material_anim, context.getTheme()) : resources.getDrawable(R.drawable.sem_switch_thumb_material_dark_anim, context.getTheme());
            }
        } else {
            this.mThumbDrawable = typedArrayObtainStyledAttributes.getDrawable(2);
        }
        Drawable drawable = this.mThumbDrawable;
        if (drawable != null) {
            drawable.setCallback(this);
        }
        if ((this.mIsSamsungBasicInteraction || this.mIsMetaDataInActivity) && typedArrayObtainStyledAttributes.getResourceId(4, 0) == 17304673) {
            this.mTrackDrawable = typedValue2.data != 0 ? resources.getDrawable(R.drawable.sem_switch_track_material, context.getTheme()) : resources.getDrawable(R.drawable.sem_switch_track_material_dark, context.getTheme());
        } else {
            this.mTrackDrawable = typedArrayObtainStyledAttributes.getDrawable(4);
        }
        Drawable drawable2 = this.mTrackDrawable;
        if (drawable2 != null) {
            drawable2.setCallback(this);
        }
        if ((this.mIsSamsungBasicInteraction || this.mIsMetaDataInActivity) && !this.mIsThemeChanged) {
            this.mTrackOnDrawable = resources.getDrawable(R.drawable.sem_switch_track_mtrl_alpha_on, context.getTheme());
            Drawable drawable3 = resources.getDrawable(R.drawable.sem_switch_track_mtrl_alpha_off, context.getTheme());
            this.mTrackOffDrawable = drawable3;
            if (this.mTrackOnDrawable != null && drawable3 != null) {
                this.mIsSupportSemSwitchVI = true;
            }
        }
        this.mTextOn = typedArrayObtainStyledAttributes.getText(0);
        this.mTextOff = typedArrayObtainStyledAttributes.getText(1);
        this.mShowText = typedArrayObtainStyledAttributes.getBoolean(11, true);
        this.mThumbTextPadding = typedArrayObtainStyledAttributes.getDimensionPixelSize(7, 0);
        this.mSwitchMinWidth = typedArrayObtainStyledAttributes.getDimensionPixelSize(5, 0);
        this.mSwitchPadding = typedArrayObtainStyledAttributes.getDimensionPixelSize(6, 0);
        this.mSplitTrack = typedArrayObtainStyledAttributes.getBoolean(8, false);
        this.mUseFallbackLineSpacing = context.getApplicationInfo().targetSdkVersion >= 28;
        ColorStateList colorStateList = typedArrayObtainStyledAttributes.getColorStateList(9);
        if (colorStateList != null) {
            this.mThumbTintList = colorStateList;
            this.mHasThumbTint = true;
        }
        BlendMode blendMode = Drawable.parseBlendMode(typedArrayObtainStyledAttributes.getInt(10, -1), null);
        if (this.mThumbBlendMode != blendMode) {
            this.mThumbBlendMode = blendMode;
            this.mHasThumbTintMode = true;
        }
        if (this.mHasThumbTint || this.mHasThumbTintMode) {
            applyThumbTint();
        }
        ColorStateList colorStateList2 = typedArrayObtainStyledAttributes.getColorStateList(12);
        if (colorStateList2 != null) {
            this.mTrackTintList = colorStateList2;
            this.mHasTrackTint = true;
        }
        BlendMode blendMode2 = Drawable.parseBlendMode(typedArrayObtainStyledAttributes.getInt(13, -1), null);
        if (this.mTrackBlendMode != blendMode2) {
            this.mTrackBlendMode = blendMode2;
            this.mHasTrackTintMode = true;
        }
        if (this.mHasTrackTint || this.mHasTrackTintMode) {
            applyTrackTint();
        }
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(3, 0);
        if (resourceId != 0) {
            setSwitchTextAppearance(context, resourceId);
        }
        typedArrayObtainStyledAttributes.recycle();
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mMinFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        refreshDrawableState();
        setDefaultStateDescription();
        setChecked(isChecked());
    }

    public void setSwitchTextAppearance(Context context, int i) throws Resources.NotFoundException {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(i, R.styleable.TextAppearance);
        ColorStateList colorStateList = typedArrayObtainStyledAttributes.getColorStateList(3);
        if (colorStateList != null) {
            this.mTextColors = colorStateList;
        } else {
            this.mTextColors = getTextColors();
        }
        int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(0, 0);
        if (dimensionPixelSize != 0) {
            float f = dimensionPixelSize;
            if (f != this.mTextPaint.getTextSize()) {
                this.mTextPaint.setTextSize(f);
                requestLayout();
            }
        }
        setSwitchTypefaceByIndex(typedArrayObtainStyledAttributes.getInt(1, -1), typedArrayObtainStyledAttributes.getInt(2, -1));
        if (typedArrayObtainStyledAttributes.getBoolean(11, false)) {
            AllCapsTransformationMethod allCapsTransformationMethod = new AllCapsTransformationMethod(getContext());
            this.mSwitchTransformationMethod = allCapsTransformationMethod;
            allCapsTransformationMethod.setLengthChangesAllowed(true);
        } else {
            this.mSwitchTransformationMethod = null;
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    private void setSwitchTypefaceByIndex(int i, int i2) {
        Typeface typeface;
        if (i == 1) {
            typeface = Typeface.SANS_SERIF;
        } else if (i == 2) {
            typeface = Typeface.SERIF;
        } else {
            typeface = i != 3 ? null : Typeface.MONOSPACE;
        }
        setSwitchTypeface(typeface, i2);
    }

    public void setSwitchTypeface(Typeface typeface, int i) {
        Typeface typefaceCreate;
        if (i > 0) {
            if (typeface == null) {
                typefaceCreate = Typeface.defaultFromStyle(i);
            } else {
                typefaceCreate = Typeface.create(typeface, i);
            }
            setSwitchTypeface(typefaceCreate);
            int i2 = (~(typefaceCreate != null ? typefaceCreate.getStyle() : 0)) & i;
            this.mTextPaint.setFakeBoldText((i2 & 1) != 0);
            this.mTextPaint.setTextSkewX((i2 & 2) != 0 ? -0.25f : 0.0f);
            return;
        }
        this.mTextPaint.setFakeBoldText(false);
        this.mTextPaint.setTextSkewX(0.0f);
        setSwitchTypeface(typeface);
    }

    public void setSwitchTypeface(Typeface typeface) {
        if (this.mTextPaint.getTypeface() != typeface) {
            this.mTextPaint.setTypeface(typeface);
            requestLayout();
            invalidate();
        }
    }

    @RemotableViewMethod
    public void setSwitchPadding(int i) {
        this.mSwitchPadding = i;
        requestLayout();
    }

    public int getSwitchPadding() {
        return this.mSwitchPadding;
    }

    @RemotableViewMethod
    public void setSwitchMinWidth(int i) {
        this.mSwitchMinWidth = i;
        requestLayout();
    }

    public int getSwitchMinWidth() {
        return this.mSwitchMinWidth;
    }

    @RemotableViewMethod
    public void setThumbTextPadding(int i) {
        this.mThumbTextPadding = i;
        requestLayout();
    }

    public int getThumbTextPadding() {
        return this.mThumbTextPadding;
    }

    /* renamed from: setTrackDrawable, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public void lambda$setTrackResourceAsync$0(Drawable drawable) {
        Drawable drawable2 = this.mTrackDrawable;
        if (drawable2 != null) {
            drawable2.setCallback(null);
        }
        this.mTrackDrawable = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
        }
        requestLayout();
    }

    @RemotableViewMethod(asyncImpl = "setTrackResourceAsync")
    public void setTrackResource(int i) {
        lambda$setTrackResourceAsync$0(getContext().getDrawable(i));
    }

    public Runnable setTrackResourceAsync(int i) {
        final Drawable drawable = i == 0 ? null : getContext().getDrawable(i);
        return new Runnable() { // from class: android.widget.Switch$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$setTrackResourceAsync$0(drawable);
            }
        };
    }

    public Drawable getTrackDrawable() {
        return this.mTrackDrawable;
    }

    @RemotableViewMethod(asyncImpl = "setTrackIconAsync")
    public void setTrackIcon(Icon icon) {
        lambda$setTrackResourceAsync$0(icon == null ? null : icon.loadDrawable(getContext()));
    }

    public Runnable setTrackIconAsync(Icon icon) {
        final Drawable drawableLoadDrawable = icon == null ? null : icon.loadDrawable(getContext());
        return new Runnable() { // from class: android.widget.Switch$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$setTrackIconAsync$1(drawableLoadDrawable);
            }
        };
    }

    @RemotableViewMethod
    public void setTrackTintList(ColorStateList colorStateList) {
        this.mTrackTintList = colorStateList;
        this.mHasTrackTint = true;
        applyTrackTint();
    }

    public ColorStateList getTrackTintList() {
        return this.mTrackTintList;
    }

    public void setTrackTintMode(PorterDuff.Mode mode) {
        setTrackTintBlendMode(mode != null ? BlendMode.fromValue(mode.nativeInt) : null);
    }

    @RemotableViewMethod
    public void setTrackTintBlendMode(BlendMode blendMode) {
        this.mTrackBlendMode = blendMode;
        this.mHasTrackTintMode = true;
        applyTrackTint();
    }

    public PorterDuff.Mode getTrackTintMode() {
        BlendMode trackTintBlendMode = getTrackTintBlendMode();
        if (trackTintBlendMode != null) {
            return BlendMode.blendModeToPorterDuffMode(trackTintBlendMode);
        }
        return null;
    }

    public BlendMode getTrackTintBlendMode() {
        return this.mTrackBlendMode;
    }

    private void applyTrackTint() {
        Drawable drawable = this.mTrackDrawable;
        if (drawable != null) {
            if (this.mHasTrackTint || this.mHasTrackTintMode) {
                Drawable drawableMutate = drawable.mutate();
                this.mTrackDrawable = drawableMutate;
                if (this.mHasTrackTint) {
                    drawableMutate.setTintList(this.mTrackTintList);
                }
                if (this.mHasTrackTintMode) {
                    this.mTrackDrawable.setTintBlendMode(this.mTrackBlendMode);
                }
                if (this.mTrackDrawable.isStateful()) {
                    this.mTrackDrawable.setState(getDrawableState());
                }
            }
        }
    }

    /* renamed from: setThumbDrawable, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public void lambda$setThumbResourceAsync$2(Drawable drawable) {
        Drawable drawable2 = this.mThumbDrawable;
        if (drawable2 != null) {
            drawable2.setCallback(null);
        }
        this.mThumbDrawable = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
        }
        requestLayout();
    }

    @RemotableViewMethod(asyncImpl = "setThumbResourceAsync")
    public void setThumbResource(int i) {
        lambda$setThumbResourceAsync$2(getContext().getDrawable(i));
    }

    public Runnable setThumbResourceAsync(int i) {
        final Drawable drawable = i == 0 ? null : getContext().getDrawable(i);
        return new Runnable() { // from class: android.widget.Switch$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$setThumbResourceAsync$2(drawable);
            }
        };
    }

    public Drawable getThumbDrawable() {
        return this.mThumbDrawable;
    }

    @RemotableViewMethod(asyncImpl = "setThumbIconAsync")
    public void setThumbIcon(Icon icon) {
        lambda$setThumbResourceAsync$2(icon == null ? null : icon.loadDrawable(getContext()));
    }

    public Runnable setThumbIconAsync(Icon icon) {
        final Drawable drawableLoadDrawable = icon == null ? null : icon.loadDrawable(getContext());
        return new Runnable() { // from class: android.widget.Switch$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$setThumbIconAsync$3(drawableLoadDrawable);
            }
        };
    }

    @RemotableViewMethod
    public void setThumbTintList(ColorStateList colorStateList) {
        this.mThumbTintList = colorStateList;
        this.mHasThumbTint = true;
        applyThumbTint();
    }

    public ColorStateList getThumbTintList() {
        return this.mThumbTintList;
    }

    public void setThumbTintMode(PorterDuff.Mode mode) {
        setThumbTintBlendMode(mode != null ? BlendMode.fromValue(mode.nativeInt) : null);
    }

    @RemotableViewMethod
    public void setThumbTintBlendMode(BlendMode blendMode) {
        this.mThumbBlendMode = blendMode;
        this.mHasThumbTintMode = true;
        applyThumbTint();
    }

    public PorterDuff.Mode getThumbTintMode() {
        BlendMode thumbTintBlendMode = getThumbTintBlendMode();
        if (thumbTintBlendMode != null) {
            return BlendMode.blendModeToPorterDuffMode(thumbTintBlendMode);
        }
        return null;
    }

    public BlendMode getThumbTintBlendMode() {
        return this.mThumbBlendMode;
    }

    private void applyThumbTint() {
        Drawable drawable = this.mThumbDrawable;
        if (drawable != null) {
            if (this.mHasThumbTint || this.mHasThumbTintMode) {
                Drawable drawableMutate = drawable.mutate();
                this.mThumbDrawable = drawableMutate;
                if (this.mHasThumbTint) {
                    drawableMutate.setTintList(this.mThumbTintList);
                }
                if (this.mHasThumbTintMode) {
                    this.mThumbDrawable.setTintBlendMode(this.mThumbBlendMode);
                }
                if (this.mThumbDrawable.isStateful()) {
                    this.mThumbDrawable.setState(getDrawableState());
                }
            }
        }
    }

    @RemotableViewMethod
    public void setSplitTrack(boolean z) {
        this.mSplitTrack = z;
        invalidate();
    }

    public boolean getSplitTrack() {
        return this.mSplitTrack;
    }

    public CharSequence getTextOn() {
        return this.mTextOn;
    }

    @RemotableViewMethod
    public void setTextOn(CharSequence charSequence) {
        this.mTextOn = charSequence;
        requestLayout();
        setDefaultStateDescription();
    }

    public CharSequence getTextOff() {
        return this.mTextOff;
    }

    @RemotableViewMethod
    public void setTextOff(CharSequence charSequence) {
        this.mTextOff = charSequence;
        requestLayout();
        setDefaultStateDescription();
    }

    @RemotableViewMethod
    public void setShowText(boolean z) {
        if (this.mShowText != z) {
            this.mShowText = z;
            requestLayout();
        }
    }

    public boolean getShowText() {
        return this.mShowText;
    }

    @Override // android.widget.TextView, android.view.View
    public void onMeasure(int i, int i2) {
        int intrinsicWidth;
        int intrinsicHeight;
        int intrinsicHeight2;
        int iMax;
        if (this.mShowText) {
            if (this.mOnLayout == null) {
                this.mOnLayout = makeLayout(this.mTextOn);
            }
            if (this.mOffLayout == null) {
                this.mOffLayout = makeLayout(this.mTextOff);
            }
        }
        Rect rect = this.mTempRect;
        Drawable drawable = this.mThumbDrawable;
        if (drawable != null) {
            drawable.getPadding(rect);
            intrinsicWidth = (this.mThumbDrawable.getIntrinsicWidth() - rect.left) - rect.right;
            intrinsicHeight = this.mThumbDrawable.getIntrinsicHeight();
        } else {
            intrinsicWidth = 0;
            intrinsicHeight = 0;
        }
        this.mThumbWidth = Math.max(this.mShowText ? Math.max(this.mOnLayout.getWidth(), this.mOffLayout.getWidth()) + (this.mThumbTextPadding * 2) : 0, intrinsicWidth);
        Drawable drawable2 = this.mTrackDrawable;
        if (drawable2 != null) {
            drawable2.getPadding(rect);
            intrinsicHeight2 = this.mTrackDrawable.getIntrinsicHeight();
        } else {
            rect.setEmpty();
            intrinsicHeight2 = 0;
        }
        int iMax2 = rect.left;
        int iMax3 = rect.right;
        Drawable drawable3 = this.mThumbDrawable;
        if (drawable3 != null) {
            Insets opticalInsets = drawable3.getOpticalInsets();
            iMax2 = Math.max(iMax2, opticalInsets.left);
            iMax3 = Math.max(iMax3, opticalInsets.right);
        }
        if (this.mIsSamsungBasicInteraction || this.mIsMetaDataInActivity) {
            iMax = Math.max(this.mSwitchMinWidth, getResources().getDimensionPixelSize(R.dimen.tw_switch_width));
        } else {
            iMax = Math.max(this.mSwitchMinWidth, (this.mThumbWidth * 2) + iMax2 + iMax3);
        }
        int iMax4 = Math.max(intrinsicHeight2, intrinsicHeight);
        this.mSwitchWidth = iMax;
        this.mSwitchHeight = iMax4;
        if (this.mIsThemeChanged) {
            this.mTrackMargin = ((float) this.mThumbWidth) / ((float) iMax) > THUMB_TRACK_WIDTH_RATIO ? (int) Math.ceil(r3 - (iMax * THUMB_TRACK_WIDTH_RATIO)) : 0;
        }
        super.onMeasure(i, i2);
        if (getMeasuredHeight() < iMax4) {
            setMeasuredDimension(getMeasuredWidthAndState(), iMax4);
        }
    }

    @Override // android.widget.TextView, android.view.View
    public void onPopulateAccessibilityEventInternal(AccessibilityEvent accessibilityEvent) throws Resources.NotFoundException {
        super.onPopulateAccessibilityEventInternal(accessibilityEvent);
        String string = getResources().getString(R.string.sem_switch_on);
        String string2 = getResources().getString(R.string.sem_switch_off);
        if (!isChecked()) {
            string = string2;
        }
        if (string != null) {
            accessibilityEvent.getText().add(string);
        }
    }

    private Layout makeLayout(CharSequence charSequence) {
        TransformationMethod2 transformationMethod2 = this.mSwitchTransformationMethod;
        if (transformationMethod2 != null) {
            charSequence = transformationMethod2.getTransformation(charSequence, this);
        }
        return StaticLayout.Builder.obtain(charSequence, 0, charSequence.length(), this.mTextPaint, (int) Math.ceil(Layout.getDesiredWidth(charSequence, 0, charSequence.length(), this.mTextPaint, getTextDirectionHeuristic()))).setUseLineSpacingFromFallbacks(this.mUseFallbackLineSpacing).build();
    }

    private boolean hitThumb(float f, float f2) {
        if (this.mThumbDrawable == null) {
            return false;
        }
        int thumbOffset = getThumbOffset();
        this.mThumbDrawable.getPadding(this.mTempRect);
        int i = this.mSwitchTop;
        int i2 = this.mTouchSlop;
        int i3 = i - i2;
        int i4 = (this.mSwitchLeft + thumbOffset) - i2;
        int i5 = this.mThumbWidth + i4 + this.mTempRect.left + this.mTempRect.right;
        int i6 = this.mTouchSlop;
        return f > ((float) i4) && f < ((float) (i5 + i6)) && f2 > ((float) i3) && f2 < ((float) (this.mSwitchBottom + i6));
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x0089  */
    @Override // android.widget.TextView, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.mVelocityTracker.addMovement(motionEvent);
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            if (isEnabled() && hitThumb(x, y)) {
                this.mTouchMode = 1;
                this.mTouchX = x;
                this.mTouchY = y;
            }
        } else if (actionMasked == 1) {
            if (this.mTouchMode == 2) {
                stopDrag(motionEvent);
                super.onTouchEvent(motionEvent);
                return true;
            }
            this.mTouchMode = 0;
            this.mVelocityTracker.clear();
        } else if (actionMasked == 2) {
            int i = this.mTouchMode;
            if (i == 1) {
                float x2 = motionEvent.getX();
                float y2 = motionEvent.getY();
                if (Math.abs(x2 - this.mTouchX) > this.mTouchSlop || Math.abs(y2 - this.mTouchY) > this.mTouchSlop) {
                    this.mTouchMode = 2;
                    getParent().requestDisallowInterceptTouchEvent(true);
                    this.mTouchX = x2;
                    this.mTouchY = y2;
                    return true;
                }
            } else if (i == 2) {
                float x3 = motionEvent.getX();
                int thumbScrollRange = getThumbScrollRange();
                float f = x3 - this.mTouchX;
                float f2 = thumbScrollRange != 0 ? f / thumbScrollRange : f > 0.0f ? 1.0f : -1.0f;
                if (isLayoutRtl()) {
                    f2 = -f2;
                }
                float fConstrain = MathUtils.constrain(this.mThumbPosition + f2, 0.0f, 1.0f);
                if (fConstrain != this.mThumbPosition) {
                    this.mTouchX = x3;
                    setThumbPosition(fConstrain);
                }
                return true;
            }
        } else if (actionMasked == 3) {
        }
        return super.onTouchEvent(motionEvent);
    }

    private void cancelSuperTouch(MotionEvent motionEvent) {
        MotionEvent motionEventObtain = MotionEvent.obtain(motionEvent);
        motionEventObtain.setAction(3);
        super.onTouchEvent(motionEventObtain);
        motionEventObtain.recycle();
    }

    private void stopDrag(MotionEvent motionEvent) {
        this.mTouchMode = 0;
        boolean targetCheckedState = true;
        boolean z = motionEvent.getAction() == 1 && isEnabled();
        boolean zIsChecked = isChecked();
        if (z) {
            this.mVelocityTracker.computeCurrentVelocity(1000);
            float xVelocity = this.mVelocityTracker.getXVelocity();
            if (Math.abs(xVelocity) > this.mMinFlingVelocity) {
                if (!isLayoutRtl() ? xVelocity <= 0.0f : xVelocity >= 0.0f) {
                    targetCheckedState = false;
                }
            } else {
                targetCheckedState = getTargetCheckedState();
            }
        } else {
            targetCheckedState = zIsChecked;
        }
        if (targetCheckedState != zIsChecked) {
            playSoundEffect(0);
        }
        setChecked(targetCheckedState);
        cancelSuperTouch(motionEvent);
    }

    private void animateThumbToCheckedState(boolean z) {
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this, THUMB_POS, z ? 1.0f : 0.0f);
        this.mPositionAnimator = objectAnimatorOfFloat;
        if (this.mIsSupportSemSwitchVI) {
            objectAnimatorOfFloat.setInterpolator(new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f));
            this.mPositionAnimator.setDuration(300L);
        } else {
            objectAnimatorOfFloat.setDuration(250L);
        }
        this.mPositionAnimator.setAutoCancel(true);
        this.mPositionAnimator.start();
    }

    private void cancelPositionAnimator() {
        ObjectAnimator objectAnimator = this.mPositionAnimator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
    }

    private boolean getTargetCheckedState() {
        return this.mThumbPosition > 0.5f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setThumbPosition(float f) {
        this.mThumbPosition = f;
        invalidate();
    }

    @Override // android.widget.CompoundButton, android.widget.Checkable
    public void toggle() {
        setChecked(!isChecked());
    }

    @Override // android.widget.CompoundButton
    protected CharSequence getButtonStateDescription() {
        if (isChecked()) {
            CharSequence charSequence = this.mTextOn;
            return charSequence == null ? getResources().getString(R.string.sem_switch_on) : charSequence;
        }
        CharSequence charSequence2 = this.mTextOff;
        return charSequence2 == null ? getResources().getString(R.string.sem_switch_off) : charSequence2;
    }

    @Override // android.widget.CompoundButton, android.widget.Checkable
    @RemotableViewMethod(asyncImpl = "setCheckedAsync")
    public void setChecked(boolean z) {
        if (z != isChecked() && hasWindowFocus() && isVisibleToUser() && !isTemporarilyDetached()) {
            performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(27));
        }
        super.setChecked(z);
        boolean zIsChecked = isChecked();
        if (isAttachedToWindow() && isLaidOut()) {
            animateThumbToCheckedState(zIsChecked);
        } else {
            cancelPositionAnimator();
            setThumbPosition(zIsChecked ? 1.0f : 0.0f);
        }
    }

    @Override // android.widget.TextView, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int iMax;
        int width;
        int paddingLeft;
        int i5;
        int paddingTop;
        int height;
        super.onLayout(z, i, i2, i3, i4);
        int iMax2 = 0;
        if (this.mThumbDrawable != null) {
            Rect rect = this.mTempRect;
            Drawable drawable = this.mTrackDrawable;
            if (drawable != null) {
                drawable.getPadding(rect);
            } else {
                rect.setEmpty();
            }
            Insets opticalInsets = this.mThumbDrawable.getOpticalInsets();
            iMax = Math.max(0, opticalInsets.left - rect.left);
            iMax2 = Math.max(0, opticalInsets.right - rect.right);
        } else {
            iMax = 0;
        }
        if (isLayoutRtl()) {
            paddingLeft = getPaddingLeft() + iMax;
            width = (((this.mSwitchWidth + paddingLeft) + this.mTrackMargin) - iMax) - iMax2;
        } else {
            width = (getWidth() - getPaddingRight()) - iMax2;
            paddingLeft = ((width - this.mSwitchWidth) - this.mTrackMargin) + iMax + iMax2;
        }
        int gravity = getGravity() & 112;
        if (gravity == 16) {
            int paddingTop2 = ((getPaddingTop() + getHeight()) - getPaddingBottom()) / 2;
            i5 = this.mSwitchHeight;
            paddingTop = paddingTop2 - (i5 / 2);
        } else if (gravity != 80) {
            paddingTop = getPaddingTop();
            i5 = this.mSwitchHeight;
        } else {
            height = getHeight() - getPaddingBottom();
            paddingTop = height - this.mSwitchHeight;
            this.mSwitchLeft = paddingLeft;
            this.mSwitchTop = paddingTop;
            this.mSwitchBottom = height;
            this.mSwitchRight = width;
        }
        height = i5 + paddingTop;
        this.mSwitchLeft = paddingLeft;
        this.mSwitchTop = paddingTop;
        this.mSwitchBottom = height;
        this.mSwitchRight = width;
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        Insets opticalInsets;
        int i;
        int i2;
        Rect rect = this.mTempRect;
        int i3 = this.mSwitchLeft;
        int i4 = this.mSwitchTop;
        int i5 = this.mSwitchRight;
        int i6 = this.mSwitchBottom;
        int thumbOffset = getThumbOffset() + i3;
        Drawable drawable = this.mThumbDrawable;
        if (drawable != null) {
            opticalInsets = drawable.getOpticalInsets();
        } else {
            opticalInsets = Insets.NONE;
        }
        Drawable drawable2 = this.mTrackDrawable;
        if (drawable2 != null) {
            drawable2.getPadding(rect);
            thumbOffset += rect.left;
            int i7 = this.mTrackMargin;
            int i8 = i3 + (i7 / 2);
            int i9 = i5 - (i7 / 2);
            if (opticalInsets != Insets.NONE) {
                if (opticalInsets.left > rect.left) {
                    i8 += opticalInsets.left - rect.left;
                }
                i = opticalInsets.top > rect.top ? (opticalInsets.top - rect.top) + i4 : i4;
                if (opticalInsets.right > rect.right) {
                    i9 -= opticalInsets.right - rect.right;
                }
                if (opticalInsets.bottom > rect.bottom) {
                    i2 = i6 - (opticalInsets.bottom - rect.bottom);
                }
                this.mTrackDrawable.setBounds(i8, i, i9, i2);
            } else {
                i = i4;
            }
            i2 = i6;
            this.mTrackDrawable.setBounds(i8, i, i9, i2);
        }
        Drawable drawable3 = this.mThumbDrawable;
        if (drawable3 != null) {
            drawable3.getPadding(rect);
            int i10 = thumbOffset - rect.left;
            int i11 = this.mThumbWidth + thumbOffset + rect.right;
            if (this.mIsSamsungBasicInteraction || this.mIsMetaDataInActivity) {
                i11 = thumbOffset + this.mThumbWidth;
            } else {
                thumbOffset = i10;
            }
            this.mThumbDrawable.setBounds(thumbOffset, i4, i11, i6);
            Drawable background = getBackground();
            if (background != null) {
                background.setHotspotBounds(thumbOffset, i4, i11, i6);
            }
        }
        super.draw(canvas);
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    protected void onDraw(Canvas canvas) {
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
            if (this.mSplitTrack && drawable2 != null) {
                Insets opticalInsets = drawable2.getOpticalInsets();
                drawable2.copyBounds(rect);
                rect.left += opticalInsets.left;
                rect.right -= opticalInsets.right;
                int iSave = canvas.save();
                canvas.clipRect(rect, Region.Op.DIFFERENCE);
                drawable.draw(canvas);
                canvas.restoreToCount(iSave);
            } else if (this.mIsSupportSemSwitchVI) {
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
                drawable.draw(canvas);
            }
        }
        int iSave2 = canvas.save();
        if (drawable2 != null) {
            drawable2.draw(canvas);
        }
        Layout layout = getTargetCheckedState() ? this.mOnLayout : this.mOffLayout;
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
        canvas.restoreToCount(iSave2);
    }

    @Override // android.widget.CompoundButton, android.widget.TextView
    public int getCompoundPaddingLeft() {
        if (!isLayoutRtl()) {
            return super.getCompoundPaddingLeft();
        }
        int compoundPaddingLeft = super.getCompoundPaddingLeft() + this.mSwitchWidth + this.mTrackMargin;
        return !TextUtils.isEmpty(getText()) ? compoundPaddingLeft + this.mSwitchPadding : compoundPaddingLeft;
    }

    @Override // android.widget.CompoundButton, android.widget.TextView
    public int getCompoundPaddingRight() {
        if (isLayoutRtl()) {
            return super.getCompoundPaddingRight();
        }
        int compoundPaddingRight = super.getCompoundPaddingRight() + this.mSwitchWidth + this.mTrackMargin;
        return !TextUtils.isEmpty(getText()) ? compoundPaddingRight + this.mSwitchPadding : compoundPaddingRight;
    }

    private int getThumbOffset() {
        float f;
        if (isLayoutRtl()) {
            f = 1.0f - this.mThumbPosition;
        } else {
            f = this.mThumbPosition;
        }
        return (int) ((f * getThumbScrollRange()) + 0.5f);
    }

    private int getThumbScrollRange() {
        Insets opticalInsets;
        Drawable drawable = this.mTrackDrawable;
        if (drawable == null) {
            return 0;
        }
        Rect rect = this.mTempRect;
        drawable.getPadding(rect);
        Drawable drawable2 = this.mThumbDrawable;
        if (drawable2 != null) {
            opticalInsets = drawable2.getOpticalInsets();
        } else {
            opticalInsets = Insets.NONE;
        }
        return (((((this.mSwitchWidth + this.mTrackMargin) - this.mThumbWidth) - rect.left) - rect.right) - opticalInsets.left) - opticalInsets.right;
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    protected int[] onCreateDrawableState(int i) {
        int[] iArrOnCreateDrawableState = super.onCreateDrawableState(i + 1);
        if (isChecked()) {
            mergeDrawableStates(iArrOnCreateDrawableState, CHECKED_STATE_SET);
        }
        return iArrOnCreateDrawableState;
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        int[] drawableState = getDrawableState();
        Drawable drawable = this.mThumbDrawable;
        boolean state = (drawable == null || !drawable.isStateful()) ? false : drawable.setState(drawableState);
        Drawable drawable2 = this.mTrackDrawable;
        if (drawable2 != null && drawable2.isStateful()) {
            state |= drawable2.setState(drawableState);
        }
        if (state) {
            invalidate();
        }
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    public void drawableHotspotChanged(float f, float f2) {
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
    protected boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.mThumbDrawable || drawable == this.mTrackDrawable;
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable drawable = this.mThumbDrawable;
        if (drawable != null) {
            drawable.jumpToCurrentState();
        }
        Drawable drawable2 = this.mTrackDrawable;
        if (drawable2 != null) {
            drawable2.jumpToCurrentState();
        }
        ObjectAnimator objectAnimator = this.mPositionAnimator;
        if (objectAnimator == null || !objectAnimator.isStarted()) {
            return;
        }
        this.mPositionAnimator.end();
        this.mPositionAnimator = null;
    }

    @Override // android.widget.CompoundButton, android.widget.Button, android.widget.TextView, android.view.View
    public CharSequence getAccessibilityClassName() {
        return Switch.class.getName();
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    protected void onProvideStructure(ViewStructure viewStructure, int i, int i2) {
        super.onProvideStructure(viewStructure, i, i2);
        CharSequence charSequence = isChecked() ? this.mTextOn : this.mTextOff;
        if (TextUtils.isEmpty(charSequence)) {
            return;
        }
        CharSequence text = viewStructure.getText();
        if (TextUtils.isEmpty(text)) {
            viewStructure.setText(charSequence);
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(text);
        sb.append(' ');
        sb.append(charSequence);
        viewStructure.setText(sb);
    }

    public void semSetSamsungBasicInteraction() {
        this.mIsSamsungBasicInteraction = true;
    }

    private Activity getActivityContext(Context context) {
        Activity activity = null;
        for (int i = 0; activity == null && context != null && i < 100; i++) {
            if (context instanceof Activity) {
                activity = (Activity) context;
            } else {
                context = context instanceof ContextWrapper ? ((ContextWrapper) context).getBaseContext() : null;
            }
        }
        return activity;
    }
}
