package android.graphics.drawable;

import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.CanvasProperty;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleAnimationSession;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import com.android.internal.R;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class RippleDrawable extends LayerDrawable {
    private static final int BACKGROUND_OPACITY_DURATION = 80;
    private static final int DEFAULT_EFFECT_COLOR = -1912602625;
    private static final boolean FORCE_PATTERNED_STYLE = false;
    private static final LinearInterpolator LINEAR_INTERPOLATOR = new LinearInterpolator();
    private static final int MASK_CONTENT = 1;
    private static final int MASK_EXPLICIT = 2;
    private static final int MASK_NONE = 0;
    private static final int MASK_UNKNOWN = -1;
    private static final int MAX_RIPPLES = 10;
    public static final int RADIUS_AUTO = -1;
    public static final int STYLE_PATTERNED = 1;
    public static final int STYLE_SOLID = 0;
    private static final String TAG = "RippleDrawable";
    private boolean mAddRipple;
    private RippleBackground mBackground;
    private ValueAnimator mBackgroundAnimation;
    private float mBackgroundOpacity;
    private int mDensity;
    private final Rect mDirtyBounds;
    private final Rect mDrawingBounds;
    private boolean mExitingAnimation;
    private RippleForeground[] mExitingRipples;
    private int mExitingRipplesCount;
    private PorterDuffColorFilter mFocusColorFilter;
    private boolean mForceSoftware;
    private boolean mHasPending;
    private boolean mHasValidMask;
    private final Rect mHotspotBounds;
    private Drawable mMask;
    private Bitmap mMaskBuffer;
    private Canvas mMaskCanvas;
    private PorterDuffColorFilter mMaskColorFilter;
    private Matrix mMaskMatrix;
    private BitmapShader mMaskShader;
    private boolean mOverrideBounds;
    private float mPendingX;
    private float mPendingY;
    private RippleForeground mRipple;
    private boolean mRippleActive;
    private Paint mRipplePaint;
    private boolean mRunBackgroundAnimation;
    private ArrayList<RippleAnimationSession> mRunningAnimations;
    private boolean mSpenHovered;
    private RippleState mState;
    private float mTargetBackgroundOpacity;
    private final Rect mTempRect;

    @Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.LOCAL_VARIABLE, ElementType.FIELD})
    @Retention(RetentionPolicy.SOURCE)
    public @interface RippleStyle {
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public boolean hasFocusStateSpecified() {
        return true;
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public boolean isStateful() {
        return true;
    }

    RippleDrawable() {
        this(new RippleState(null, null, null), null);
    }

    public RippleDrawable(ColorStateList colorStateList, Drawable drawable, Drawable drawable2) {
        this(new RippleState(null, null, null), null);
        if (colorStateList == null) {
            throw new IllegalArgumentException("RippleDrawable requires a non-null color");
        }
        if (drawable != null) {
            addLayer(drawable, null, 0, 0, 0, 0, 0);
        }
        if (drawable2 != null) {
            addLayer(drawable2, null, 16908334, 0, 0, 0, 0);
        }
        setColor(colorStateList);
        ensurePadding();
        refreshPadding();
        updateLocalState();
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public void jumpToCurrentState() {
        super.jumpToCurrentState();
        RippleForeground rippleForeground = this.mRipple;
        if (rippleForeground != null) {
            rippleForeground.end();
        }
        RippleBackground rippleBackground = this.mBackground;
        if (rippleBackground != null) {
            rippleBackground.jumpToFinal();
        }
        cancelExitingRipples();
        endPatternedAnimations();
    }

    private void endPatternedAnimations() {
        for (int i = 0; i < this.mRunningAnimations.size(); i++) {
            this.mRunningAnimations.get(i).end();
        }
        this.mRunningAnimations.clear();
    }

    private void cancelExitingRipples() {
        int i = this.mExitingRipplesCount;
        RippleForeground[] rippleForegroundArr = this.mExitingRipples;
        for (int i2 = 0; i2 < i; i2++) {
            rippleForegroundArr[i2].end();
        }
        if (rippleForegroundArr != null) {
            Arrays.fill(rippleForegroundArr, 0, i, (Object) null);
        }
        this.mExitingRipplesCount = 0;
        invalidateSelf(false);
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    protected boolean onStateChange(int[] iArr) {
        boolean zOnStateChange = super.onStateChange(iArr);
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        boolean z4 = false;
        boolean z5 = false;
        boolean z6 = false;
        for (int i : iArr) {
            if (i == 16842910) {
                z2 = true;
            } else if (i == 16842908) {
                z4 = true;
            } else if (i == 16842919) {
                z3 = true;
            } else if (i == 16843623) {
                z5 = true;
            } else if (i == 16842909) {
                z6 = true;
            } else if (i == 17957251) {
                this.mSpenHovered = true;
            }
        }
        if (z2 && z3) {
            z = true;
        }
        setRippleActive(z);
        setBackgroundActive(z5, z4, z3, z6);
        return zOnStateChange;
    }

    private void setRippleActive(boolean z) {
        if (this.mRippleActive != z) {
            this.mRippleActive = z;
            if (this.mState.mRippleStyle == 0) {
                if (z) {
                    tryRippleEnter();
                    return;
                } else {
                    tryRippleExit();
                    return;
                }
            }
            if (z) {
                startPatternedAnimation();
            } else {
                exitPatternedAnimation();
            }
        }
    }

    public void setBackgroundActive(boolean z, boolean z2, boolean z3, boolean z4) {
        if (this.mSpenHovered) {
            this.mSpenHovered = false;
            return;
        }
        if (this.mState.mRippleStyle != 0) {
            if (!z2 && !z) {
                exitPatternedBackgroundAnimation();
                return;
            } else {
                if (z3) {
                    return;
                }
                enterPatternedBackgroundAnimation(z2, z, z4);
                return;
            }
        }
        this.mTargetBackgroundOpacity = z4 ? 0.6f : 0.2f;
        if (Looper.myLooper() == null) {
            Log.w(TAG, "Thread doesn't have a looper. Skipping animation.");
            return;
        }
        if (this.mBackground == null && (z || z2)) {
            RippleBackground rippleBackground = new RippleBackground(this, this.mHotspotBounds, isBounded());
            this.mBackground = rippleBackground;
            rippleBackground.setup(this.mState.mMaxRadius, this.mDensity);
        }
        RippleBackground rippleBackground2 = this.mBackground;
        if (rippleBackground2 != null) {
            rippleBackground2.setState(z2, z, z3);
        }
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        if (!this.mOverrideBounds) {
            this.mHotspotBounds.set(rect);
            onHotspotBoundsChanged();
        }
        int i = this.mExitingRipplesCount;
        RippleForeground[] rippleForegroundArr = this.mExitingRipples;
        for (int i2 = 0; i2 < i; i2++) {
            rippleForegroundArr[i2].onBoundsChange();
        }
        RippleBackground rippleBackground = this.mBackground;
        if (rippleBackground != null) {
            rippleBackground.onBoundsChange();
        }
        RippleForeground rippleForeground = this.mRipple;
        if (rippleForeground != null) {
            rippleForeground.onBoundsChange();
        }
        invalidateSelf();
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public boolean setVisible(boolean z, boolean z2) {
        boolean visible = super.setVisible(z, z2);
        if (!z) {
            clearHotspots();
            return visible;
        }
        if (visible) {
            if (this.mRippleActive) {
                if (this.mState.mRippleStyle == 0) {
                    tryRippleEnter();
                } else {
                    invalidateSelf();
                }
            }
            jumpToCurrentState();
        }
        return visible;
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public boolean isProjected() {
        if (isBounded()) {
            return false;
        }
        int i = this.mState.mMaxRadius;
        Rect bounds = getBounds();
        Rect rect = this.mHotspotBounds;
        if (i == -1 || i > rect.width() / 2 || i > rect.height() / 2) {
            return true;
        }
        return (bounds.equals(rect) || bounds.contains(rect)) ? false : true;
    }

    private boolean isBounded() {
        return getNumberOfLayers() > 0;
    }

    public void setColor(ColorStateList colorStateList) {
        if (colorStateList == null) {
            throw new IllegalArgumentException("color cannot be null");
        }
        this.mState.mColor = colorStateList;
        invalidateSelf(false);
    }

    public void setEffectColor(ColorStateList colorStateList) {
        if (colorStateList == null) {
            throw new IllegalArgumentException("color cannot be null");
        }
        this.mState.mEffectColor = colorStateList;
        invalidateSelf(false);
    }

    public ColorStateList getEffectColor() {
        return this.mState.mEffectColor;
    }

    public void setRadius(int i) {
        this.mState.mMaxRadius = i;
        invalidateSelf(false);
    }

    public int getRadius() {
        return this.mState.mMaxRadius;
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        TypedArray typedArrayObtainAttributes = obtainAttributes(resources, theme, attributeSet, R.styleable.RippleDrawable);
        setPaddingMode(1);
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        updateStateFromTypedArray(typedArrayObtainAttributes);
        verifyRequiredAttributes(typedArrayObtainAttributes);
        typedArrayObtainAttributes.recycle();
        updateLocalState();
    }

    @Override // android.graphics.drawable.LayerDrawable
    public boolean setDrawableByLayerId(int i, Drawable drawable) {
        if (!super.setDrawableByLayerId(i, drawable)) {
            return false;
        }
        if (i != 16908334) {
            return true;
        }
        this.mMask = drawable;
        this.mHasValidMask = false;
        return true;
    }

    @Override // android.graphics.drawable.LayerDrawable
    public void setPaddingMode(int i) {
        super.setPaddingMode(i);
    }

    private void updateStateFromTypedArray(TypedArray typedArray) throws XmlPullParserException {
        RippleState rippleState = this.mState;
        rippleState.mChangingConfigurations |= typedArray.getChangingConfigurations();
        rippleState.mTouchThemeAttrs = typedArray.extractThemeAttrs();
        ColorStateList colorStateList = typedArray.getColorStateList(0);
        if (colorStateList != null) {
            this.mState.mColor = colorStateList;
        }
        ColorStateList colorStateList2 = typedArray.getColorStateList(2);
        if (colorStateList2 != null) {
            this.mState.mEffectColor = colorStateList2;
        }
        RippleState rippleState2 = this.mState;
        rippleState2.mMaxRadius = typedArray.getDimensionPixelSize(1, rippleState2.mMaxRadius);
    }

    private void verifyRequiredAttributes(TypedArray typedArray) throws XmlPullParserException {
        if (this.mState.mColor == null) {
            if (this.mState.mTouchThemeAttrs == null || this.mState.mTouchThemeAttrs[0] == 0) {
                throw new XmlPullParserException(typedArray.getPositionDescription() + ": <ripple> requires a valid color attribute");
            }
        }
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public void applyTheme(Resources.Theme theme) {
        super.applyTheme(theme);
        RippleState rippleState = this.mState;
        if (rippleState == null) {
            return;
        }
        if (rippleState.mTouchThemeAttrs != null) {
            TypedArray typedArrayResolveAttributes = theme.resolveAttributes(rippleState.mTouchThemeAttrs, R.styleable.RippleDrawable);
            try {
                try {
                    updateStateFromTypedArray(typedArrayResolveAttributes);
                    verifyRequiredAttributes(typedArrayResolveAttributes);
                } catch (XmlPullParserException e) {
                    rethrowAsRuntimeException(e);
                }
            } finally {
                typedArrayResolveAttributes.recycle();
            }
        }
        if (rippleState.mColor != null && rippleState.mColor.canApplyTheme()) {
            rippleState.mColor = rippleState.mColor.obtainForTheme(theme);
        }
        updateLocalState();
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public boolean canApplyTheme() {
        RippleState rippleState = this.mState;
        return (rippleState != null && rippleState.canApplyTheme()) || super.canApplyTheme();
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public void setHotspot(float f, float f2) {
        this.mPendingX = f;
        this.mPendingY = f2;
        RippleForeground rippleForeground = this.mRipple;
        if (rippleForeground == null || this.mBackground == null) {
            this.mHasPending = true;
        }
        if (rippleForeground != null) {
            rippleForeground.move(f, f2);
        }
    }

    private void tryRippleEnter() {
        RippleDrawable rippleDrawable;
        float fExactCenterX;
        float fExactCenterY;
        if (this.mExitingRipplesCount >= 10) {
            return;
        }
        if (this.mRipple == null) {
            if (this.mHasPending) {
                this.mHasPending = false;
                fExactCenterX = this.mPendingX;
                fExactCenterY = this.mPendingY;
            } else {
                fExactCenterX = this.mHotspotBounds.exactCenterX();
                fExactCenterY = this.mHotspotBounds.exactCenterY();
            }
            rippleDrawable = this;
            rippleDrawable.mRipple = new RippleForeground(rippleDrawable, this.mHotspotBounds, fExactCenterX, fExactCenterY, this.mForceSoftware);
        } else {
            rippleDrawable = this;
        }
        rippleDrawable.mRipple.setup(rippleDrawable.mState.mMaxRadius, rippleDrawable.mDensity);
        rippleDrawable.mRipple.enter();
    }

    private void tryRippleExit() {
        RippleForeground rippleForeground = this.mRipple;
        if (rippleForeground != null) {
            if (this.mExitingRipples == null) {
                this.mExitingRipples = new RippleForeground[10];
            }
            RippleForeground[] rippleForegroundArr = this.mExitingRipples;
            int i = this.mExitingRipplesCount;
            this.mExitingRipplesCount = i + 1;
            rippleForegroundArr[i] = rippleForeground;
            rippleForeground.exit();
            this.mRipple = null;
        }
    }

    private void clearHotspots() {
        RippleForeground rippleForeground = this.mRipple;
        if (rippleForeground != null) {
            rippleForeground.end();
            this.mRipple = null;
            this.mRippleActive = false;
        }
        RippleBackground rippleBackground = this.mBackground;
        if (rippleBackground != null) {
            rippleBackground.setState(false, false, false);
        }
        cancelExitingRipples();
        endPatternedAnimations();
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public void setHotspotBounds(int i, int i2, int i3, int i4) {
        this.mOverrideBounds = true;
        this.mHotspotBounds.set(i, i2, i3, i4);
        onHotspotBoundsChanged();
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public void getHotspotBounds(Rect rect) {
        rect.set(this.mHotspotBounds);
    }

    private void onHotspotBoundsChanged() {
        int i = this.mExitingRipplesCount;
        RippleForeground[] rippleForegroundArr = this.mExitingRipples;
        for (int i2 = 0; i2 < i; i2++) {
            rippleForegroundArr[i2].onHotspotBoundsChanged();
        }
        RippleForeground rippleForeground = this.mRipple;
        if (rippleForeground != null) {
            rippleForeground.onHotspotBoundsChanged();
        }
        RippleBackground rippleBackground = this.mBackground;
        if (rippleBackground != null) {
            rippleBackground.onHotspotBoundsChanged();
        }
        float computedRadius = getComputedRadius();
        for (int i3 = 0; i3 < this.mRunningAnimations.size(); i3++) {
            RippleAnimationSession rippleAnimationSession = this.mRunningAnimations.get(i3);
            rippleAnimationSession.setRadius(computedRadius);
            rippleAnimationSession.getProperties().getShader().setResolution(this.mHotspotBounds.width(), this.mHotspotBounds.height());
            float fCenterX = this.mHotspotBounds.centerX();
            float fCenterY = this.mHotspotBounds.centerY();
            rippleAnimationSession.getProperties().getShader().setOrigin(fCenterX, fCenterY);
            rippleAnimationSession.getProperties().setOrigin(Float.valueOf(fCenterX), Float.valueOf(fCenterY));
            if (!rippleAnimationSession.isForceSoftware()) {
                rippleAnimationSession.getCanvasProperties().setOrigin(CanvasProperty.createFloat(fCenterX), CanvasProperty.createFloat(fCenterY));
            }
        }
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public void getOutline(Outline outline) {
        LayerDrawable.LayerState layerState = this.mLayerState;
        LayerDrawable.ChildDrawable[] childDrawableArr = layerState.mChildren;
        int i = layerState.mNumChildren;
        for (int i2 = 0; i2 < i; i2++) {
            if (childDrawableArr[i2].mId != 16908334) {
                childDrawableArr[i2].mDrawable.getOutline(outline);
                if (!outline.isEmpty()) {
                    return;
                }
            }
        }
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        if (this.mState.mRippleStyle == 0) {
            drawSolid(canvas);
        } else {
            drawPatterned(canvas);
        }
    }

    private void drawSolid(Canvas canvas) {
        pruneRipples();
        Rect dirtyBounds = getDirtyBounds();
        int iSave = canvas.save(2);
        if (isBounded()) {
            canvas.clipRect(dirtyBounds);
        }
        drawContent(canvas);
        drawBackgroundAndRipples(canvas);
        canvas.restoreToCount(iSave);
    }

    private void exitPatternedBackgroundAnimation() {
        this.mTargetBackgroundOpacity = 0.0f;
        ValueAnimator valueAnimator = this.mBackgroundAnimation;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        this.mRunBackgroundAnimation = true;
        invalidateSelf(false);
    }

    private void startPatternedAnimation() {
        this.mAddRipple = true;
        invalidateSelf(false);
    }

    private void exitPatternedAnimation() {
        this.mExitingAnimation = true;
        invalidateSelf(false);
    }

    public float getTargetBackgroundOpacity() {
        return this.mTargetBackgroundOpacity;
    }

    private void enterPatternedBackgroundAnimation(boolean z, boolean z2, boolean z3) {
        this.mBackgroundOpacity = 0.0f;
        if (z) {
            this.mTargetBackgroundOpacity = z3 ? 0.6f : 0.2f;
        } else {
            this.mTargetBackgroundOpacity = z2 ? 0.2f : 0.0f;
        }
        ValueAnimator valueAnimator = this.mBackgroundAnimation;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        this.mRunBackgroundAnimation = true;
        invalidateSelf(false);
    }

    private void startBackgroundAnimation() {
        this.mRunBackgroundAnimation = false;
        if (Looper.myLooper() == null) {
            Log.w(TAG, "Thread doesn't have a looper. Skipping animation.");
            return;
        }
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.mBackgroundOpacity, this.mTargetBackgroundOpacity);
        this.mBackgroundAnimation = valueAnimatorOfFloat;
        valueAnimatorOfFloat.setInterpolator(LINEAR_INTERPOLATOR);
        this.mBackgroundAnimation.setDuration(80L);
        this.mBackgroundAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: android.graphics.drawable.RippleDrawable$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f$0.lambda$startBackgroundAnimation$0(valueAnimator);
            }
        });
        this.mBackgroundAnimation.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startBackgroundAnimation$0(ValueAnimator valueAnimator) {
        this.mBackgroundOpacity = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        invalidateSelf(false);
    }

    private void drawPatterned(Canvas canvas) {
        float fExactCenterX;
        float fExactCenterY;
        Rect rect = this.mHotspotBounds;
        int iSave = canvas.save(2);
        boolean z = this.mForceSoftware;
        if (isBounded()) {
            canvas.clipRect(getDirtyBounds());
        }
        boolean z2 = this.mAddRipple;
        float fCenterX = rect.centerX();
        float fCenterY = rect.centerY();
        boolean z3 = this.mExitingAnimation;
        int i = 0;
        this.mExitingAnimation = false;
        this.mAddRipple = false;
        if (this.mRunningAnimations.size() > 0 && !z2) {
            updateRipplePaint();
        }
        drawContent(canvas);
        drawPatternedBackground(canvas, fCenterX, fCenterY);
        if (z2 && this.mRunningAnimations.size() <= 10) {
            if (this.mHasPending) {
                fExactCenterX = this.mPendingX;
                fExactCenterY = this.mPendingY;
                this.mHasPending = false;
            } else {
                fExactCenterX = rect.exactCenterX();
                fExactCenterY = rect.exactCenterY();
            }
            float fHeight = rect.height();
            this.mRunningAnimations.add(new RippleAnimationSession(createAnimationProperties(fExactCenterX, fExactCenterY, fCenterX, fCenterY, rect.width(), fHeight), z).setOnAnimationUpdated(new Runnable() { // from class: android.graphics.drawable.RippleDrawable$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$drawPatterned$1();
                }
            }).setOnSessionEnd(new Consumer() { // from class: android.graphics.drawable.RippleDrawable$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.lambda$drawPatterned$2((RippleAnimationSession) obj);
                }
            }).setForceSoftwareAnimation(z).enter(canvas));
        }
        if (z3) {
            for (int i2 = 0; i2 < this.mRunningAnimations.size(); i2++) {
                this.mRunningAnimations.get(i2).exit(canvas);
            }
        }
        while (true) {
            if (i >= this.mRunningAnimations.size()) {
                break;
            }
            RippleAnimationSession rippleAnimationSession = this.mRunningAnimations.get(i);
            if (!canvas.isHardwareAccelerated()) {
                Log.e(TAG, "The RippleDrawable.STYLE_PATTERNED animation is not supported for a non-hardware accelerated Canvas. Skipping animation.");
                break;
            }
            if (!z) {
                RippleAnimationSession.AnimationProperties<CanvasProperty<Float>, CanvasProperty<Paint>> canvasProperties = rippleAnimationSession.getCanvasProperties();
                ((RecordingCanvas) canvas).drawRipple(canvasProperties.getX(), canvasProperties.getY(), canvasProperties.getMaxRadius(), canvasProperties.getPaint(), canvasProperties.getProgress(), canvasProperties.getNoisePhase(), canvasProperties.getColor(), canvasProperties.getShader());
            } else {
                RippleAnimationSession.AnimationProperties<Float, Paint> properties = rippleAnimationSession.getProperties();
                canvas.drawCircle(properties.getX().floatValue(), properties.getY().floatValue(), properties.getMaxRadius().floatValue(), properties.getPaint());
            }
            i++;
        }
        canvas.restoreToCount(iSave);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$drawPatterned$1() {
        invalidateSelf(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$drawPatterned$2(RippleAnimationSession rippleAnimationSession) {
        this.mRunningAnimations.remove(rippleAnimationSession);
    }

    private void drawPatternedBackground(Canvas canvas, float f, float f2) {
        if (this.mRunBackgroundAnimation) {
            startBackgroundAnimation();
        }
        if (this.mBackgroundOpacity == 0.0f) {
            return;
        }
        Paint paintUpdateRipplePaint = updateRipplePaint();
        float f3 = this.mBackgroundOpacity;
        int alpha = paintUpdateRipplePaint.getAlpha();
        int iMin = Math.min((int) ((alpha * f3) + 0.5f), 255);
        if (iMin > 0) {
            ColorFilter colorFilter = paintUpdateRipplePaint.getColorFilter();
            paintUpdateRipplePaint.setColorFilter(this.mFocusColorFilter);
            paintUpdateRipplePaint.setAlpha(iMin);
            canvas.drawCircle(f, f2, getComputedRadius(), paintUpdateRipplePaint);
            paintUpdateRipplePaint.setAlpha(alpha);
            paintUpdateRipplePaint.setColorFilter(colorFilter);
        }
    }

    private float computeRadius() {
        float fWidth = this.mHotspotBounds.width() / 2.0f;
        float fHeight = this.mHotspotBounds.height() / 2.0f;
        return (float) Math.sqrt((fWidth * fWidth) + (fHeight * fHeight));
    }

    private int getComputedRadius() {
        return this.mState.mMaxRadius >= 0 ? this.mState.mMaxRadius : (int) computeRadius();
    }

    private RippleAnimationSession.AnimationProperties<Float, Paint> createAnimationProperties(float f, float f2, float f3, float f4, float f5, float f6) {
        Paint paint = new Paint(updateRipplePaint());
        float computedRadius = getComputedRadius();
        RippleShader rippleShader = new RippleShader();
        PorterDuffColorFilter porterDuffColorFilter = this.mMaskColorFilter;
        int iClampAlpha = clampAlpha(porterDuffColorFilter == null ? this.mState.mColor.getColorForState(getState(), -16777216) : porterDuffColorFilter.getColor());
        int colorForState = this.mState.mEffectColor.getColorForState(getState(), Color.MAGENTA);
        float fCurrentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
        rippleShader.setColor(iClampAlpha, colorForState);
        rippleShader.setOrigin(f3, f4);
        rippleShader.setTouch(f, f2);
        rippleShader.setResolution(f5, f6);
        rippleShader.setNoisePhase(fCurrentAnimationTimeMillis);
        rippleShader.setRadius(computedRadius);
        rippleShader.setProgress(0.0f);
        RippleAnimationSession.AnimationProperties<Float, Paint> animationProperties = new RippleAnimationSession.AnimationProperties<>(Float.valueOf(f3), Float.valueOf(f4), Float.valueOf(computedRadius), Float.valueOf(fCurrentAnimationTimeMillis), paint, Float.valueOf(0.0f), iClampAlpha, rippleShader);
        BitmapShader bitmapShader = this.mMaskShader;
        if (bitmapShader == null) {
            rippleShader.setShader(null);
        } else {
            rippleShader.setShader(bitmapShader);
        }
        paint.setShader(rippleShader);
        paint.setColorFilter(null);
        paint.setColor(-16777216);
        return animationProperties;
    }

    private int clampAlpha(int i) {
        return Color.alpha(i) > 128 ? (16777215 & i) | Integer.MIN_VALUE : i;
    }

    @Override // android.graphics.drawable.Drawable
    public void invalidateSelf() {
        invalidateSelf(true);
    }

    void invalidateSelf(boolean z) {
        super.invalidateSelf();
        if (z) {
            this.mHasValidMask = false;
        }
    }

    private void pruneRipples() {
        RippleForeground[] rippleForegroundArr = this.mExitingRipples;
        int i = this.mExitingRipplesCount;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            if (!rippleForegroundArr[i3].hasFinishedExit()) {
                rippleForegroundArr[i2] = rippleForegroundArr[i3];
                i2++;
            }
        }
        for (int i4 = i2; i4 < i; i4++) {
            rippleForegroundArr[i4] = null;
        }
        this.mExitingRipplesCount = i2;
    }

    private void updateMaskShaderIfNeeded() {
        int maskType;
        if (this.mHasValidMask || (maskType = getMaskType()) == -1) {
            return;
        }
        this.mHasValidMask = true;
        Rect bounds = getBounds();
        if (maskType == 0 || bounds.isEmpty()) {
            Bitmap bitmap = this.mMaskBuffer;
            if (bitmap != null) {
                bitmap.recycle();
                this.mMaskBuffer = null;
                this.mMaskShader = null;
                this.mMaskCanvas = null;
            }
            this.mMaskMatrix = null;
            this.mMaskColorFilter = null;
            return;
        }
        Bitmap bitmap2 = this.mMaskBuffer;
        if (bitmap2 == null || bitmap2.getWidth() != bounds.width() || this.mMaskBuffer.getHeight() != bounds.height()) {
            Bitmap bitmap3 = this.mMaskBuffer;
            if (bitmap3 != null) {
                bitmap3.recycle();
            }
            this.mMaskBuffer = Bitmap.createBitmap(bounds.width(), bounds.height(), Bitmap.Config.ALPHA_8);
            this.mMaskShader = new BitmapShader(this.mMaskBuffer, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            this.mMaskCanvas = new Canvas(this.mMaskBuffer);
        } else {
            this.mMaskBuffer.eraseColor(0);
        }
        Matrix matrix = this.mMaskMatrix;
        if (matrix == null) {
            this.mMaskMatrix = new Matrix();
        } else {
            matrix.reset();
        }
        if (this.mMaskColorFilter == null) {
            this.mMaskColorFilter = new PorterDuffColorFilter(0, PorterDuff.Mode.SRC_IN);
            this.mFocusColorFilter = new PorterDuffColorFilter(0, PorterDuff.Mode.SRC_IN);
        }
        int iSave = this.mMaskCanvas.save();
        this.mMaskCanvas.translate(-bounds.left, -bounds.top);
        if (maskType == 2) {
            drawMask(this.mMaskCanvas);
        } else if (maskType == 1) {
            drawContent(this.mMaskCanvas);
        }
        this.mMaskCanvas.restoreToCount(iSave);
    }

    private int getMaskType() {
        RippleBackground rippleBackground;
        if (this.mRipple == null && this.mExitingRipplesCount <= 0 && (((rippleBackground = this.mBackground) == null || !rippleBackground.isVisible()) && this.mState.mRippleStyle == 0)) {
            return -1;
        }
        Drawable drawable = this.mMask;
        if (drawable != null) {
            return drawable.getOpacity() == -1 ? 0 : 2;
        }
        LayerDrawable.ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        int i = this.mLayerState.mNumChildren;
        for (int i2 = 0; i2 < i; i2++) {
            if (childDrawableArr[i2].mDrawable.getOpacity() != -1) {
                return 1;
            }
        }
        return 0;
    }

    private void drawContent(Canvas canvas) {
        LayerDrawable.ChildDrawable[] childDrawableArr = this.mLayerState.mChildren;
        int i = this.mLayerState.mNumChildren;
        for (int i2 = 0; i2 < i; i2++) {
            if (childDrawableArr[i2].mId != 16908334) {
                childDrawableArr[i2].mDrawable.draw(canvas);
            }
        }
    }

    private void drawBackgroundAndRipples(Canvas canvas) {
        RippleForeground rippleForeground = this.mRipple;
        RippleBackground rippleBackground = this.mBackground;
        int i = this.mExitingRipplesCount;
        if (rippleForeground != null || i > 0 || (rippleBackground != null && rippleBackground.isVisible())) {
            float fExactCenterX = this.mHotspotBounds.exactCenterX();
            float fExactCenterY = this.mHotspotBounds.exactCenterY();
            canvas.translate(fExactCenterX, fExactCenterY);
            Paint paintUpdateRipplePaint = updateRipplePaint();
            if (rippleBackground != null && rippleBackground.isVisible()) {
                rippleBackground.draw(canvas, paintUpdateRipplePaint);
            }
            if (i > 0) {
                RippleForeground[] rippleForegroundArr = this.mExitingRipples;
                for (int i2 = 0; i2 < i; i2++) {
                    rippleForegroundArr[i2].draw(canvas, paintUpdateRipplePaint);
                }
            }
            if (rippleForeground != null) {
                rippleForeground.draw(canvas, paintUpdateRipplePaint);
            }
            canvas.translate(-fExactCenterX, -fExactCenterY);
        }
    }

    private void drawMask(Canvas canvas) {
        this.mMask.draw(canvas);
    }

    Paint updateRipplePaint() {
        if (this.mRipplePaint == null) {
            Paint paint = new Paint();
            this.mRipplePaint = paint;
            paint.setAntiAlias(true);
            this.mRipplePaint.setStyle(Paint.Style.FILL);
        }
        float fExactCenterX = this.mHotspotBounds.exactCenterX();
        float fExactCenterY = this.mHotspotBounds.exactCenterY();
        updateMaskShaderIfNeeded();
        if (this.mMaskShader != null) {
            Rect bounds = getBounds();
            if (this.mState.mRippleStyle == 1) {
                this.mMaskMatrix.setTranslate(bounds.left, bounds.top);
            } else {
                this.mMaskMatrix.setTranslate(bounds.left - fExactCenterX, bounds.top - fExactCenterY);
            }
            this.mMaskShader.setLocalMatrix(this.mMaskMatrix);
            if (this.mState.mRippleStyle == 1) {
                for (int i = 0; i < this.mRunningAnimations.size(); i++) {
                    this.mRunningAnimations.get(i).getProperties().getShader().setShader(this.mMaskShader);
                }
            }
        }
        int iClampAlpha = clampAlpha(this.mState.mColor.getColorForState(getState(), -16777216));
        Paint paint2 = this.mRipplePaint;
        if (this.mMaskColorFilter != null) {
            int i2 = this.mState.mRippleStyle == 1 ? iClampAlpha : iClampAlpha | (-16777216);
            if (this.mMaskColorFilter.getColor() != i2) {
                this.mMaskColorFilter = new PorterDuffColorFilter(i2, this.mMaskColorFilter.getMode());
                this.mFocusColorFilter = new PorterDuffColorFilter(iClampAlpha | (-16777216), this.mFocusColorFilter.getMode());
            }
            paint2.setColor(iClampAlpha & (-16777216));
            paint2.setColorFilter(this.mMaskColorFilter);
            paint2.setShader(this.mMaskShader);
            return paint2;
        }
        paint2.setColor(iClampAlpha);
        paint2.setColorFilter(null);
        paint2.setShader(null);
        return paint2;
    }

    @Override // android.graphics.drawable.Drawable
    public Rect getDirtyBounds() {
        if (!isBounded()) {
            Rect rect = this.mDrawingBounds;
            Rect rect2 = this.mDirtyBounds;
            rect2.set(rect);
            rect.setEmpty();
            int iExactCenterX = (int) this.mHotspotBounds.exactCenterX();
            int iExactCenterY = (int) this.mHotspotBounds.exactCenterY();
            Rect rect3 = this.mTempRect;
            RippleForeground[] rippleForegroundArr = this.mExitingRipples;
            int i = this.mExitingRipplesCount;
            for (int i2 = 0; i2 < i; i2++) {
                rippleForegroundArr[i2].getBounds(rect3);
                rect3.offset(iExactCenterX, iExactCenterY);
                rect.union(rect3);
            }
            RippleBackground rippleBackground = this.mBackground;
            if (rippleBackground != null) {
                rippleBackground.getBounds(rect3);
                rect3.offset(iExactCenterX, iExactCenterY);
                rect.union(rect3);
            }
            rect2.union(rect);
            rect2.union(super.getDirtyBounds());
            return rect2;
        }
        return getBounds();
    }

    public void setForceSoftware(boolean z) {
        this.mForceSoftware = z;
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public Drawable.ConstantState getConstantState() {
        return this.mState;
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public Drawable mutate() {
        super.mutate();
        this.mState = (RippleState) this.mLayerState;
        this.mMask = findDrawableByLayerId(16908334);
        return this;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // android.graphics.drawable.LayerDrawable
    public RippleState createConstantState(LayerDrawable.LayerState layerState, Resources resources) {
        return new RippleState(layerState, this, resources);
    }

    static class RippleState extends LayerDrawable.LayerState {
        ColorStateList mColor;
        ColorStateList mEffectColor;
        int mMaxRadius;
        int mRippleStyle;
        int[] mTouchThemeAttrs;

        public RippleState(LayerDrawable.LayerState layerState, RippleDrawable rippleDrawable, Resources resources) {
            super(layerState, rippleDrawable, resources);
            this.mColor = ColorStateList.valueOf(Color.MAGENTA);
            this.mEffectColor = ColorStateList.valueOf(RippleDrawable.DEFAULT_EFFECT_COLOR);
            this.mMaxRadius = -1;
            this.mRippleStyle = 0;
            if (layerState == null || !(layerState instanceof RippleState)) {
                return;
            }
            RippleState rippleState = (RippleState) layerState;
            this.mTouchThemeAttrs = rippleState.mTouchThemeAttrs;
            this.mColor = rippleState.mColor;
            this.mMaxRadius = rippleState.mMaxRadius;
            this.mRippleStyle = rippleState.mRippleStyle;
            this.mEffectColor = rippleState.mEffectColor;
            if (rippleState.mDensity != this.mDensity) {
                applyDensityScaling(layerState.mDensity, this.mDensity);
            }
        }

        @Override // android.graphics.drawable.LayerDrawable.LayerState
        protected void onDensityChanged(int i, int i2) {
            super.onDensityChanged(i, i2);
            applyDensityScaling(i, i2);
        }

        private void applyDensityScaling(int i, int i2) {
            int i3 = this.mMaxRadius;
            if (i3 != -1) {
                this.mMaxRadius = Drawable.scaleFromDensity(i3, i, i2, true);
            }
        }

        @Override // android.graphics.drawable.LayerDrawable.LayerState, android.graphics.drawable.Drawable.ConstantState
        public boolean canApplyTheme() {
            if (this.mTouchThemeAttrs != null) {
                return true;
            }
            ColorStateList colorStateList = this.mColor;
            return (colorStateList != null && colorStateList.canApplyTheme()) || super.canApplyTheme();
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // android.graphics.drawable.LayerDrawable.LayerState, android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable() {
            return new RippleDrawable(this, (Resources) null);
        }

        @Override // android.graphics.drawable.LayerDrawable.LayerState, android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources) {
            return new RippleDrawable(this, resources);
        }

        @Override // android.graphics.drawable.LayerDrawable.LayerState, android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            int changingConfigurations = super.getChangingConfigurations();
            ColorStateList colorStateList = this.mColor;
            return (colorStateList != null ? colorStateList.getChangingConfigurations() : 0) | changingConfigurations;
        }
    }

    private RippleDrawable(RippleState rippleState, Resources resources) {
        this.mTempRect = new Rect();
        this.mHotspotBounds = new Rect();
        this.mDrawingBounds = new Rect();
        this.mDirtyBounds = new Rect();
        this.mExitingRipplesCount = 0;
        this.mAddRipple = false;
        this.mRunningAnimations = new ArrayList<>();
        this.mSpenHovered = false;
        RippleState rippleState2 = new RippleState(rippleState, this, resources);
        this.mState = rippleState2;
        this.mLayerState = rippleState2;
        this.mDensity = Drawable.resolveDensity(resources, this.mState.mDensity);
        if (this.mState.mNumChildren > 0) {
            ensurePadding();
            refreshPadding();
        }
        updateLocalState();
    }

    private void updateLocalState() {
        this.mMask = findDrawableByLayerId(16908334);
    }
}
