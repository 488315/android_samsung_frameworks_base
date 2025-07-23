package android.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.VectorDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.media.quality.ParameterCapability;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.FloatProperty;
import android.util.IntProperty;
import android.util.MathUtils;
import android.util.Pools;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.RemotableViewMethod;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewHierarchyEncoder;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.Flags;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.RemoteViews;
import com.android.internal.R;
import java.lang.ref.WeakReference;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

@RemoteViews.RemoteView
/* loaded from: classes5.dex */
public class ProgressBar extends View {
    private static boolean DEBUG = false;
    private static final int MAX_LEVEL = 10000;
    public static final int MODE_STANDARD = 0;
    private static final int PROGRESS_ANIM_DURATION = 80;
    private static final DecelerateInterpolator PROGRESS_ANIM_INTERPOLATOR = new DecelerateInterpolator();
    public static final int SEM_MODE_CIRCLE = 6;
    public static final int SEM_MODE_DUAL_COLOR = 2;
    public static final int SEM_MODE_EXPAND = 5;

    @Deprecated
    public static final int SEM_MODE_SPLIT = 4;
    public static final int SEM_MODE_VERTICAL = 3;

    @Deprecated
    public static final int SEM_MODE_WARNING = 1;
    private static final String SEM_PROGRESS_PATH_NAME_BACKGROUND = "android:background";
    public static final String SEM_PROGRESS_PATH_NAME_MEASURES = "android:measures";
    public static final String SEM_PROGRESS_PATH_NAME_PRIMARY = "android:progress";
    private static final String TAG = "ProgressBar";
    private final FloatProperty<ProgressBar> VISUAL_PROGRESS;
    private boolean mAggregatedIsVisible;
    private AlphaAnimation mAnimation;
    private boolean mAttached;
    private int mBehavior;
    private Locale mCachedLocale;
    private CircleAnimationCallback mCircleAnimationCallback;
    private Drawable mCurrentDrawable;
    protected int mCurrentMode;
    protected float mDensity;
    private int mDuration;
    private boolean mHasAnimation;
    private boolean mInDrawing;
    private boolean mIndeterminate;
    private Drawable mIndeterminateDrawable;
    private Drawable mIndeterminateHorizontalLarge;
    private Drawable mIndeterminateHorizontalMedium;
    private Drawable mIndeterminateHorizontalSmall;
    private Drawable mIndeterminateHorizontalXlarge;
    private Drawable mIndeterminateHorizontalXsmall;
    private Interpolator mInterpolator;
    public boolean mIsDeviceDefaultDark;
    private ObjectAnimator mLastProgressAnimator;
    private int mMax;
    int mMaxHeight;
    private boolean mMaxInitialized;
    int mMaxWidth;
    private int mMin;
    int mMinHeight;
    private boolean mMinInitialized;
    int mMinWidth;
    boolean mMirrorForRtl;
    private boolean mNoInvalidate;
    private boolean mOnlyIndeterminate;
    private NumberFormat mPercentFormat;
    private int mProgress;
    private Drawable mProgressDrawable;
    private ProgressTintInfo mProgressTintInfo;
    private final ArrayList<RefreshData> mRefreshData;
    private boolean mRefreshIsPosted;
    private RefreshProgressRunnable mRefreshProgressRunnable;
    private int mRoundStrokeWidth;
    int mSampleWidth;
    private int mSecondaryProgress;
    public int mSemMin;
    public boolean mSemMinEnabled;
    private boolean mShouldStartAnimationDrawable;
    private Transformation mTransformation;
    private long mUiThreadId;
    private boolean mUseHorizontalProgress;
    private float mVisualProgress;

    void onVisualProgressChanged(int i, float f) {
    }

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<ProgressBar> {
        private int mIndeterminateDrawableId;
        private int mIndeterminateId;
        private int mIndeterminateTintBlendModeId;
        private int mIndeterminateTintId;
        private int mIndeterminateTintModeId;
        private int mInterpolatorId;
        private int mMaxId;
        private int mMinId;
        private int mMirrorForRtlId;
        private int mProgressBackgroundTintBlendModeId;
        private int mProgressBackgroundTintId;
        private int mProgressBackgroundTintModeId;
        private int mProgressDrawableId;
        private int mProgressId;
        private int mProgressTintBlendModeId;
        private int mProgressTintId;
        private int mProgressTintModeId;
        private boolean mPropertiesMapped = false;
        private int mSecondaryProgressId;
        private int mSecondaryProgressTintBlendModeId;
        private int mSecondaryProgressTintId;
        private int mSecondaryProgressTintModeId;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(PropertyMapper propertyMapper) {
            this.mIndeterminateId = propertyMapper.mapBoolean("indeterminate", 16843065);
            this.mIndeterminateDrawableId = propertyMapper.mapObject("indeterminateDrawable", 16843067);
            this.mIndeterminateTintId = propertyMapper.mapObject("indeterminateTint", 16843881);
            this.mIndeterminateTintBlendModeId = propertyMapper.mapObject("indeterminateTintBlendMode", 23);
            this.mIndeterminateTintModeId = propertyMapper.mapObject("indeterminateTintMode", 16843882);
            this.mInterpolatorId = propertyMapper.mapObject("interpolator", 16843073);
            this.mMaxId = propertyMapper.mapInt("max", 16843062);
            this.mMinId = propertyMapper.mapInt(ParameterCapability.CAPABILITY_MIN, 16844089);
            this.mMirrorForRtlId = propertyMapper.mapBoolean("mirrorForRtl", 16843726);
            this.mProgressId = propertyMapper.mapInt("progress", 16843063);
            this.mProgressBackgroundTintId = propertyMapper.mapObject("progressBackgroundTint", 16843877);
            this.mProgressBackgroundTintBlendModeId = propertyMapper.mapObject("progressBackgroundTintBlendMode", 19);
            this.mProgressBackgroundTintModeId = propertyMapper.mapObject("progressBackgroundTintMode", 16843878);
            this.mProgressDrawableId = propertyMapper.mapObject("progressDrawable", 16843068);
            this.mProgressTintId = propertyMapper.mapObject("progressTint", 16843875);
            this.mProgressTintBlendModeId = propertyMapper.mapObject("progressTintBlendMode", 17);
            this.mProgressTintModeId = propertyMapper.mapObject("progressTintMode", 16843876);
            this.mSecondaryProgressId = propertyMapper.mapInt("secondaryProgress", 16843064);
            this.mSecondaryProgressTintId = propertyMapper.mapObject("secondaryProgressTint", 16843879);
            this.mSecondaryProgressTintBlendModeId = propertyMapper.mapObject("secondaryProgressTintBlendMode", 21);
            this.mSecondaryProgressTintModeId = propertyMapper.mapObject("secondaryProgressTintMode", 16843880);
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(ProgressBar progressBar, PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readBoolean(this.mIndeterminateId, progressBar.isIndeterminate());
            propertyReader.readObject(this.mIndeterminateDrawableId, progressBar.getIndeterminateDrawable());
            propertyReader.readObject(this.mIndeterminateTintId, progressBar.getIndeterminateTintList());
            propertyReader.readObject(this.mIndeterminateTintBlendModeId, progressBar.getIndeterminateTintBlendMode());
            propertyReader.readObject(this.mIndeterminateTintModeId, progressBar.getIndeterminateTintMode());
            propertyReader.readObject(this.mInterpolatorId, progressBar.getInterpolator());
            propertyReader.readInt(this.mMaxId, progressBar.getMax());
            propertyReader.readInt(this.mMinId, progressBar.getMin());
            propertyReader.readBoolean(this.mMirrorForRtlId, progressBar.getMirrorForRtl());
            propertyReader.readInt(this.mProgressId, progressBar.getProgress());
            propertyReader.readObject(this.mProgressBackgroundTintId, progressBar.getProgressBackgroundTintList());
            propertyReader.readObject(this.mProgressBackgroundTintBlendModeId, progressBar.getProgressBackgroundTintBlendMode());
            propertyReader.readObject(this.mProgressBackgroundTintModeId, progressBar.getProgressBackgroundTintMode());
            propertyReader.readObject(this.mProgressDrawableId, progressBar.getProgressDrawable());
            propertyReader.readObject(this.mProgressTintId, progressBar.getProgressTintList());
            propertyReader.readObject(this.mProgressTintBlendModeId, progressBar.getProgressTintBlendMode());
            propertyReader.readObject(this.mProgressTintModeId, progressBar.getProgressTintMode());
            propertyReader.readInt(this.mSecondaryProgressId, progressBar.getSecondaryProgress());
            propertyReader.readObject(this.mSecondaryProgressTintId, progressBar.getSecondaryProgressTintList());
            propertyReader.readObject(this.mSecondaryProgressTintBlendModeId, progressBar.getSecondaryProgressTintBlendMode());
            propertyReader.readObject(this.mSecondaryProgressTintModeId, progressBar.getSecondaryProgressTintMode());
        }
    }

    public ProgressBar(Context context) {
        this(context, null);
    }

    public ProgressBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842871);
    }

    public ProgressBar(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public ProgressBar(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mSampleWidth = 0;
        this.mMirrorForRtl = false;
        this.mRefreshData = new ArrayList<>();
        this.mCurrentMode = 0;
        this.mUseHorizontalProgress = false;
        this.VISUAL_PROGRESS = new FloatProperty<ProgressBar>(this, "visual_progress") { // from class: android.widget.ProgressBar.2
            @Override // android.util.FloatProperty
            public void setValue(ProgressBar progressBar, float f) {
                progressBar.setVisualProgress(16908301, f);
                progressBar.mVisualProgress = f;
            }

            @Override // android.util.Property
            public Float get(ProgressBar progressBar) {
                return Float.valueOf(progressBar.mVisualProgress);
            }
        };
        this.mUiThreadId = Thread.currentThread().getId();
        initProgressBar();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ProgressBar, i, i2);
        saveAttributeDataForStyleable(context, R.styleable.ProgressBar, attributeSet, obtainStyledAttributes, i, i2);
        this.mNoInvalidate = true;
        Drawable drawable = obtainStyledAttributes.getDrawable(8);
        if (drawable != null) {
            Drawable mutate = drawable.mutate();
            if (needsTileify(mutate)) {
                setProgressDrawableTiled(mutate);
            } else {
                setProgressDrawable(mutate);
            }
        }
        this.mDuration = obtainStyledAttributes.getInt(9, this.mDuration);
        this.mMinWidth = obtainStyledAttributes.getDimensionPixelSize(11, this.mMinWidth);
        this.mMaxWidth = obtainStyledAttributes.getDimensionPixelSize(0, this.mMaxWidth);
        this.mMinHeight = obtainStyledAttributes.getDimensionPixelSize(12, this.mMinHeight);
        this.mMaxHeight = obtainStyledAttributes.getDimensionPixelSize(1, this.mMaxHeight);
        this.mBehavior = obtainStyledAttributes.getInt(10, this.mBehavior);
        int resourceId = obtainStyledAttributes.getResourceId(13, 17432587);
        if (resourceId > 0) {
            setInterpolator(context, resourceId);
        }
        setMin(obtainStyledAttributes.getInt(26, this.mMin));
        setMax(obtainStyledAttributes.getInt(2, this.mMax));
        setProgress(obtainStyledAttributes.getInt(3, this.mProgress));
        setSecondaryProgress(obtainStyledAttributes.getInt(4, this.mSecondaryProgress));
        Drawable drawable2 = obtainStyledAttributes.getDrawable(7);
        if (drawable2 != null) {
            if (needsTileify(drawable2)) {
                setIndeterminateDrawableTiled(drawable2);
            } else {
                setIndeterminateDrawable(drawable2);
            }
        }
        boolean z = obtainStyledAttributes.getBoolean(6, this.mOnlyIndeterminate);
        this.mOnlyIndeterminate = z;
        this.mNoInvalidate = false;
        setIndeterminate(z || obtainStyledAttributes.getBoolean(5, this.mIndeterminate));
        this.mMirrorForRtl = obtainStyledAttributes.getBoolean(15, this.mMirrorForRtl);
        if (obtainStyledAttributes.hasValue(17)) {
            if (this.mProgressTintInfo == null) {
                this.mProgressTintInfo = new ProgressTintInfo();
            }
            this.mProgressTintInfo.mProgressBlendMode = Drawable.parseBlendMode(obtainStyledAttributes.getInt(17, -1), null);
            this.mProgressTintInfo.mHasProgressTintMode = true;
        }
        if (obtainStyledAttributes.hasValue(16)) {
            if (this.mProgressTintInfo == null) {
                this.mProgressTintInfo = new ProgressTintInfo();
            }
            this.mProgressTintInfo.mProgressTintList = obtainStyledAttributes.getColorStateList(16);
            this.mProgressTintInfo.mHasProgressTint = true;
        }
        if (obtainStyledAttributes.hasValue(19)) {
            if (this.mProgressTintInfo == null) {
                this.mProgressTintInfo = new ProgressTintInfo();
            }
            this.mProgressTintInfo.mProgressBackgroundBlendMode = Drawable.parseBlendMode(obtainStyledAttributes.getInt(19, -1), null);
            this.mProgressTintInfo.mHasProgressBackgroundTintMode = true;
        }
        if (obtainStyledAttributes.hasValue(18)) {
            if (this.mProgressTintInfo == null) {
                this.mProgressTintInfo = new ProgressTintInfo();
            }
            this.mProgressTintInfo.mProgressBackgroundTintList = obtainStyledAttributes.getColorStateList(18);
            this.mProgressTintInfo.mHasProgressBackgroundTint = true;
        }
        if (obtainStyledAttributes.hasValue(21)) {
            if (this.mProgressTintInfo == null) {
                this.mProgressTintInfo = new ProgressTintInfo();
            }
            this.mProgressTintInfo.mSecondaryProgressBlendMode = Drawable.parseBlendMode(obtainStyledAttributes.getInt(21, -1), null);
            this.mProgressTintInfo.mHasSecondaryProgressTintMode = true;
        }
        if (obtainStyledAttributes.hasValue(20)) {
            if (this.mProgressTintInfo == null) {
                this.mProgressTintInfo = new ProgressTintInfo();
            }
            this.mProgressTintInfo.mSecondaryProgressTintList = obtainStyledAttributes.getColorStateList(20);
            this.mProgressTintInfo.mHasSecondaryProgressTint = true;
        }
        if (obtainStyledAttributes.hasValue(23)) {
            if (this.mProgressTintInfo == null) {
                this.mProgressTintInfo = new ProgressTintInfo();
            }
            this.mProgressTintInfo.mIndeterminateBlendMode = Drawable.parseBlendMode(obtainStyledAttributes.getInt(23, -1), null);
            this.mProgressTintInfo.mHasIndeterminateTintMode = true;
        }
        if (obtainStyledAttributes.hasValue(22)) {
            if (this.mProgressTintInfo == null) {
                this.mProgressTintInfo = new ProgressTintInfo();
            }
            this.mProgressTintInfo.mIndeterminateTintList = obtainStyledAttributes.getColorStateList(22);
            this.mProgressTintInfo.mHasIndeterminateTint = true;
        }
        obtainStyledAttributes.recycle();
        applyProgressTints();
        applyIndeterminateTint();
        if (getImportantForAccessibility() == 0) {
            setImportantForAccessibility(1);
        }
        this.mDensity = context.getResources().getDisplayMetrics().density;
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.parentIsDeviceDefaultDark, typedValue, true);
        this.mIsDeviceDefaultDark = typedValue.data != 0;
        TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, R.styleable.SemProgressBar, i, i2);
        this.mUseHorizontalProgress = obtainStyledAttributes2.getBoolean(6, this.mUseHorizontalProgress);
        obtainStyledAttributes2.recycle();
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, 16974123);
        this.mIndeterminateHorizontalXsmall = getResources().getDrawable(R.drawable.sem_progress_bar_indeterminate_xsmall_transition, contextThemeWrapper.getTheme());
        this.mIndeterminateHorizontalSmall = getResources().getDrawable(R.drawable.sem_progress_bar_indeterminate_small_transition, contextThemeWrapper.getTheme());
        this.mIndeterminateHorizontalMedium = getResources().getDrawable(R.drawable.sem_progress_bar_indeterminate_medium_transition, contextThemeWrapper.getTheme());
        this.mIndeterminateHorizontalLarge = getResources().getDrawable(R.drawable.sem_progress_bar_indeterminate_large_transition, contextThemeWrapper.getTheme());
        this.mIndeterminateHorizontalXlarge = getResources().getDrawable(R.drawable.sem_progress_bar_indeterminate_xlarge_transition, contextThemeWrapper.getTheme());
        this.mCircleAnimationCallback = new CircleAnimationCallback(this);
    }

    public void setMinWidth(int i) {
        this.mMinWidth = i;
        requestLayout();
    }

    public int getMinWidth() {
        return this.mMinWidth;
    }

    public void setMaxWidth(int i) {
        this.mMaxWidth = i;
        requestLayout();
    }

    public int getMaxWidth() {
        return this.mMaxWidth;
    }

    public void setMinHeight(int i) {
        this.mMinHeight = i;
        requestLayout();
    }

    public int getMinHeight() {
        return this.mMinHeight;
    }

    public void setMaxHeight(int i) {
        this.mMaxHeight = i;
        requestLayout();
    }

    public int getMaxHeight() {
        return this.mMaxHeight;
    }

    private static boolean needsTileify(Drawable drawable) {
        if (drawable instanceof LayerDrawable) {
            LayerDrawable layerDrawable = (LayerDrawable) drawable;
            int numberOfLayers = layerDrawable.getNumberOfLayers();
            for (int i = 0; i < numberOfLayers; i++) {
                if (needsTileify(layerDrawable.getDrawable(i))) {
                    return true;
                }
            }
            return false;
        }
        if (!(drawable instanceof StateListDrawable)) {
            return drawable instanceof BitmapDrawable;
        }
        StateListDrawable stateListDrawable = (StateListDrawable) drawable;
        int stateCount = stateListDrawable.getStateCount();
        for (int i2 = 0; i2 < stateCount; i2++) {
            if (needsTileify(stateListDrawable.getStateDrawable(i2))) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r8v0, types: [android.graphics.drawable.Drawable] */
    /* JADX WARN: Type inference failed for: r8v1, types: [android.graphics.drawable.Drawable] */
    /* JADX WARN: Type inference failed for: r8v4, types: [android.graphics.drawable.BitmapDrawable, android.graphics.drawable.Drawable] */
    private Drawable tileify(Drawable drawable, boolean z) {
        int i = 0;
        if (drawable instanceof LayerDrawable) {
            LayerDrawable layerDrawable = (LayerDrawable) drawable;
            int numberOfLayers = layerDrawable.getNumberOfLayers();
            Drawable[] drawableArr = new Drawable[numberOfLayers];
            for (int i2 = 0; i2 < numberOfLayers; i2++) {
                int id = layerDrawable.getId(i2);
                drawableArr[i2] = tileify(layerDrawable.getDrawable(i2), id == 16908301 || id == 16908303);
            }
            LayerDrawable layerDrawable2 = new LayerDrawable(drawableArr);
            while (i < numberOfLayers) {
                layerDrawable2.setId(i, layerDrawable.getId(i));
                layerDrawable2.setLayerGravity(i, layerDrawable.getLayerGravity(i));
                layerDrawable2.setLayerWidth(i, layerDrawable.getLayerWidth(i));
                layerDrawable2.setLayerHeight(i, layerDrawable.getLayerHeight(i));
                layerDrawable2.setLayerInsetLeft(i, layerDrawable.getLayerInsetLeft(i));
                layerDrawable2.setLayerInsetRight(i, layerDrawable.getLayerInsetRight(i));
                layerDrawable2.setLayerInsetTop(i, layerDrawable.getLayerInsetTop(i));
                layerDrawable2.setLayerInsetBottom(i, layerDrawable.getLayerInsetBottom(i));
                layerDrawable2.setLayerInsetStart(i, layerDrawable.getLayerInsetStart(i));
                layerDrawable2.setLayerInsetEnd(i, layerDrawable.getLayerInsetEnd(i));
                i++;
            }
            return layerDrawable2;
        }
        if (drawable instanceof StateListDrawable) {
            StateListDrawable stateListDrawable = (StateListDrawable) drawable;
            StateListDrawable stateListDrawable2 = new StateListDrawable();
            int stateCount = stateListDrawable.getStateCount();
            while (i < stateCount) {
                stateListDrawable2.addState(stateListDrawable.getStateSet(i), tileify(stateListDrawable.getStateDrawable(i), z));
                i++;
            }
            return stateListDrawable2;
        }
        if (drawable instanceof BitmapDrawable) {
            drawable = (BitmapDrawable) drawable.getConstantState().newDrawable(getResources());
            drawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
            if (this.mSampleWidth <= 0) {
                this.mSampleWidth = drawable.getIntrinsicWidth();
            }
            if (z) {
                return new ClipDrawable((Drawable) drawable, 3, 1);
            }
        }
        return drawable;
    }

    Shape getDrawableShape() {
        return new RoundRectShape(new float[]{5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f}, null, null);
    }

    private Drawable tileifyIndeterminate(Drawable drawable) {
        if (!(drawable instanceof AnimationDrawable)) {
            return drawable;
        }
        AnimationDrawable animationDrawable = (AnimationDrawable) drawable;
        int numberOfFrames = animationDrawable.getNumberOfFrames();
        AnimationDrawable animationDrawable2 = new AnimationDrawable();
        animationDrawable2.setOneShot(animationDrawable.isOneShot());
        for (int i = 0; i < numberOfFrames; i++) {
            Drawable tileify = tileify(animationDrawable.getFrame(i), true);
            tileify.setLevel(10000);
            animationDrawable2.addFrame(tileify, animationDrawable.getDuration(i));
        }
        animationDrawable2.setLevel(10000);
        return animationDrawable2;
    }

    private void initProgressBar() {
        this.mMin = 0;
        this.mMax = 100;
        this.mProgress = 0;
        this.mSecondaryProgress = 0;
        this.mIndeterminate = false;
        this.mOnlyIndeterminate = false;
        this.mDuration = 4000;
        this.mBehavior = 1;
        this.mMinWidth = 24;
        this.mMaxWidth = 48;
        this.mMinHeight = 24;
        this.mMaxHeight = 48;
    }

    @ViewDebug.ExportedProperty(category = "progress")
    public synchronized boolean isIndeterminate() {
        return this.mIndeterminate;
    }

    @RemotableViewMethod
    public synchronized void setIndeterminate(boolean z) {
        if ((!this.mOnlyIndeterminate || !this.mIndeterminate) && z != this.mIndeterminate) {
            this.mIndeterminate = z;
            if (z) {
                swapCurrentDrawable(this.mIndeterminateDrawable);
                startAnimation();
            } else {
                swapCurrentDrawable(this.mProgressDrawable);
                stopAnimation();
            }
            notifyViewAccessibilityStateChangedIfNeeded(0);
        }
    }

    private void swapCurrentDrawable(Drawable drawable) {
        Drawable drawable2 = this.mCurrentDrawable;
        this.mCurrentDrawable = drawable;
        if (drawable2 != drawable) {
            if (drawable2 != null) {
                drawable2.setVisible(false, false);
            }
            Drawable drawable3 = this.mCurrentDrawable;
            if (drawable3 != null) {
                drawable3.setVisible(getWindowVisibility() == 0 && isShown(), false);
            }
        }
    }

    public Drawable getIndeterminateDrawable() {
        return this.mIndeterminateDrawable;
    }

    public void setIndeterminateDrawable(Drawable drawable) {
        Drawable drawable2 = this.mIndeterminateDrawable;
        if (drawable2 != drawable) {
            if (drawable2 != null) {
                if (this.mUseHorizontalProgress) {
                    stopAnimation();
                }
                this.mIndeterminateDrawable.setCallback(null);
                unscheduleDrawable(this.mIndeterminateDrawable);
            }
            this.mIndeterminateDrawable = drawable;
            if (drawable != null) {
                drawable.setCallback(this);
                drawable.setLayoutDirection(getLayoutDirection());
                if (drawable.isStateful()) {
                    drawable.setState(getDrawableState());
                }
                applyIndeterminateTint();
            }
            if (this.mIndeterminate) {
                if (this.mUseHorizontalProgress) {
                    startAnimation();
                }
                swapCurrentDrawable(drawable);
                postInvalidate();
            }
        }
    }

    @RemotableViewMethod
    public void setIndeterminateTintList(ColorStateList colorStateList) {
        if (this.mProgressTintInfo == null) {
            this.mProgressTintInfo = new ProgressTintInfo();
        }
        this.mProgressTintInfo.mIndeterminateTintList = colorStateList;
        this.mProgressTintInfo.mHasIndeterminateTint = true;
        applyIndeterminateTint();
    }

    public ColorStateList getIndeterminateTintList() {
        ProgressTintInfo progressTintInfo = this.mProgressTintInfo;
        if (progressTintInfo != null) {
            return progressTintInfo.mIndeterminateTintList;
        }
        return null;
    }

    public void setIndeterminateTintMode(PorterDuff.Mode mode) {
        setIndeterminateTintBlendMode(mode != null ? BlendMode.fromValue(mode.nativeInt) : null);
    }

    @RemotableViewMethod
    public void setIndeterminateTintBlendMode(BlendMode blendMode) {
        if (this.mProgressTintInfo == null) {
            this.mProgressTintInfo = new ProgressTintInfo();
        }
        this.mProgressTintInfo.mIndeterminateBlendMode = blendMode;
        this.mProgressTintInfo.mHasIndeterminateTintMode = true;
        applyIndeterminateTint();
    }

    public PorterDuff.Mode getIndeterminateTintMode() {
        BlendMode indeterminateTintBlendMode = getIndeterminateTintBlendMode();
        if (indeterminateTintBlendMode != null) {
            return BlendMode.blendModeToPorterDuffMode(indeterminateTintBlendMode);
        }
        return null;
    }

    public BlendMode getIndeterminateTintBlendMode() {
        ProgressTintInfo progressTintInfo = this.mProgressTintInfo;
        if (progressTintInfo != null) {
            return progressTintInfo.mIndeterminateBlendMode;
        }
        return null;
    }

    private void applyIndeterminateTint() {
        ProgressTintInfo progressTintInfo;
        if (this.mIndeterminateDrawable == null || (progressTintInfo = this.mProgressTintInfo) == null) {
            return;
        }
        if (progressTintInfo.mHasIndeterminateTint || progressTintInfo.mHasIndeterminateTintMode) {
            this.mIndeterminateDrawable = this.mIndeterminateDrawable.mutate();
            if (progressTintInfo.mHasIndeterminateTint) {
                this.mIndeterminateDrawable.setTintList(progressTintInfo.mIndeterminateTintList);
            }
            if (progressTintInfo.mHasIndeterminateTintMode) {
                this.mIndeterminateDrawable.setTintBlendMode(progressTintInfo.mIndeterminateBlendMode);
            }
            if (this.mIndeterminateDrawable.isStateful()) {
                this.mIndeterminateDrawable.setState(getDrawableState());
            }
        }
    }

    public void setIndeterminateDrawableTiled(Drawable drawable) {
        if (drawable != null) {
            drawable = tileifyIndeterminate(drawable);
        }
        setIndeterminateDrawable(drawable);
    }

    public Drawable getProgressDrawable() {
        return this.mProgressDrawable;
    }

    public void setProgressDrawable(Drawable drawable) {
        Drawable drawable2 = this.mProgressDrawable;
        if (drawable2 != drawable) {
            if (drawable2 != null) {
                drawable2.setCallback(null);
                unscheduleDrawable(this.mProgressDrawable);
            }
            this.mProgressDrawable = drawable;
            if (drawable != null) {
                drawable.setCallback(this);
                drawable.setLayoutDirection(getLayoutDirection());
                if (drawable.isStateful()) {
                    drawable.setState(getDrawableState());
                }
                if (this.mCurrentMode == 3) {
                    int minimumWidth = drawable.getMinimumWidth();
                    if (this.mMaxWidth < minimumWidth) {
                        this.mMaxWidth = minimumWidth;
                        requestLayout();
                    }
                } else {
                    int minimumHeight = drawable.getMinimumHeight();
                    if (this.mMaxHeight < minimumHeight) {
                        this.mMaxHeight = minimumHeight;
                        requestLayout();
                    }
                }
                applyProgressTints();
            }
            if (!this.mIndeterminate) {
                swapCurrentDrawable(drawable);
                postInvalidate();
            }
            updateDrawableBounds(getWidth(), getHeight());
            updateDrawableState();
            doRefreshProgress(16908301, this.mProgress, false, false, false);
            doRefreshProgress(16908303, this.mSecondaryProgress, false, false, false);
        }
    }

    public boolean getMirrorForRtl() {
        return this.mMirrorForRtl;
    }

    private void applyProgressTints() {
        if (this.mProgressDrawable == null || this.mProgressTintInfo == null) {
            return;
        }
        applyPrimaryProgressTint();
        applyProgressBackgroundTint();
        applySecondaryProgressTint();
    }

    private void applyPrimaryProgressTint() {
        if (this.mProgressTintInfo.mHasProgressTint || this.mProgressTintInfo.mHasProgressTintMode) {
            Drawable drawable = this.mProgressDrawable;
            if (drawable instanceof VectorDrawable) {
                this.mProgressDrawable = drawable.mutate();
                if (this.mProgressTintInfo.mHasProgressTint) {
                    ((VectorDrawable) this.mProgressDrawable).setPathStrokeColor(SEM_PROGRESS_PATH_NAME_PRIMARY, this.mProgressTintInfo.mProgressTintList.getDefaultColor());
                    return;
                }
                return;
            }
            Drawable tintTarget = getTintTarget(16908301, true);
            if (tintTarget != null) {
                if (this.mProgressTintInfo.mHasProgressTint) {
                    tintTarget.setTintList(this.mProgressTintInfo.mProgressTintList);
                }
                if (this.mProgressTintInfo.mHasProgressTintMode) {
                    tintTarget.setTintBlendMode(this.mProgressTintInfo.mProgressBlendMode);
                }
                if (tintTarget.isStateful()) {
                    tintTarget.setState(getDrawableState());
                }
            }
        }
    }

    private void applyProgressBackgroundTint() {
        if (this.mProgressTintInfo.mHasProgressBackgroundTint || this.mProgressTintInfo.mHasProgressBackgroundTintMode) {
            Drawable drawable = this.mProgressDrawable;
            if (drawable instanceof VectorDrawable) {
                this.mProgressDrawable = drawable.mutate();
                if (this.mProgressTintInfo.mHasProgressBackgroundTint) {
                    ((VectorDrawable) this.mProgressDrawable).setPathStrokeColor(SEM_PROGRESS_PATH_NAME_BACKGROUND, this.mProgressTintInfo.mProgressBackgroundTintList.getDefaultColor());
                    return;
                }
                return;
            }
            Drawable tintTarget = getTintTarget(16908288, false);
            if (tintTarget != null) {
                if (this.mProgressTintInfo.mHasProgressBackgroundTint) {
                    tintTarget.setTintList(this.mProgressTintInfo.mProgressBackgroundTintList);
                }
                if (this.mProgressTintInfo.mHasProgressBackgroundTintMode) {
                    tintTarget.setTintBlendMode(this.mProgressTintInfo.mProgressBackgroundBlendMode);
                }
                if (tintTarget.isStateful()) {
                    tintTarget.setState(getDrawableState());
                }
            }
        }
    }

    private void applySecondaryProgressTint() {
        Drawable tintTarget;
        if ((this.mProgressTintInfo.mHasSecondaryProgressTint || this.mProgressTintInfo.mHasSecondaryProgressTintMode) && (tintTarget = getTintTarget(16908303, false)) != null) {
            if (this.mProgressTintInfo.mHasSecondaryProgressTint) {
                tintTarget.setTintList(this.mProgressTintInfo.mSecondaryProgressTintList);
            }
            if (this.mProgressTintInfo.mHasSecondaryProgressTintMode) {
                tintTarget.setTintBlendMode(this.mProgressTintInfo.mSecondaryProgressBlendMode);
            }
            if (tintTarget.isStateful()) {
                tintTarget.setState(getDrawableState());
            }
        }
    }

    @RemotableViewMethod
    public void setProgressTintList(ColorStateList colorStateList) {
        if (this.mProgressTintInfo == null) {
            this.mProgressTintInfo = new ProgressTintInfo();
        }
        this.mProgressTintInfo.mProgressTintList = colorStateList;
        this.mProgressTintInfo.mHasProgressTint = true;
        if (this.mProgressDrawable != null) {
            applyPrimaryProgressTint();
        }
    }

    public ColorStateList getProgressTintList() {
        ProgressTintInfo progressTintInfo = this.mProgressTintInfo;
        if (progressTintInfo != null) {
            return progressTintInfo.mProgressTintList;
        }
        return null;
    }

    protected ColorStateList semGetProgressTintList() {
        ProgressTintInfo progressTintInfo = this.mProgressTintInfo;
        if (progressTintInfo != null) {
            return progressTintInfo.mProgressTintList;
        }
        return null;
    }

    public void setProgressTintMode(PorterDuff.Mode mode) {
        setProgressTintBlendMode(mode != null ? BlendMode.fromValue(mode.nativeInt) : null);
    }

    @RemotableViewMethod
    public void setProgressTintBlendMode(BlendMode blendMode) {
        if (this.mProgressTintInfo == null) {
            this.mProgressTintInfo = new ProgressTintInfo();
        }
        this.mProgressTintInfo.mProgressBlendMode = blendMode;
        this.mProgressTintInfo.mHasProgressTintMode = true;
        if (this.mProgressDrawable != null) {
            applyPrimaryProgressTint();
        }
    }

    public PorterDuff.Mode getProgressTintMode() {
        BlendMode progressTintBlendMode = getProgressTintBlendMode();
        if (progressTintBlendMode != null) {
            return BlendMode.blendModeToPorterDuffMode(progressTintBlendMode);
        }
        return null;
    }

    public BlendMode getProgressTintBlendMode() {
        ProgressTintInfo progressTintInfo = this.mProgressTintInfo;
        if (progressTintInfo != null) {
            return progressTintInfo.mProgressBlendMode;
        }
        return null;
    }

    @RemotableViewMethod
    public void setProgressBackgroundTintList(ColorStateList colorStateList) {
        if (this.mProgressTintInfo == null) {
            this.mProgressTintInfo = new ProgressTintInfo();
        }
        this.mProgressTintInfo.mProgressBackgroundTintList = colorStateList;
        this.mProgressTintInfo.mHasProgressBackgroundTint = true;
        if (this.mProgressDrawable != null) {
            applyProgressBackgroundTint();
        }
    }

    public ColorStateList getProgressBackgroundTintList() {
        ProgressTintInfo progressTintInfo = this.mProgressTintInfo;
        if (progressTintInfo != null) {
            return progressTintInfo.mProgressBackgroundTintList;
        }
        return null;
    }

    protected ColorStateList semGetProgressBackgroundTintList() {
        ProgressTintInfo progressTintInfo = this.mProgressTintInfo;
        if (progressTintInfo != null) {
            return progressTintInfo.mProgressBackgroundTintList;
        }
        return null;
    }

    public void setProgressBackgroundTintMode(PorterDuff.Mode mode) {
        setProgressBackgroundTintBlendMode(mode != null ? BlendMode.fromValue(mode.nativeInt) : null);
    }

    @RemotableViewMethod
    public void setProgressBackgroundTintBlendMode(BlendMode blendMode) {
        if (this.mProgressTintInfo == null) {
            this.mProgressTintInfo = new ProgressTintInfo();
        }
        this.mProgressTintInfo.mProgressBackgroundBlendMode = blendMode;
        this.mProgressTintInfo.mHasProgressBackgroundTintMode = true;
        if (this.mProgressDrawable != null) {
            applyProgressBackgroundTint();
        }
    }

    public PorterDuff.Mode getProgressBackgroundTintMode() {
        BlendMode progressBackgroundTintBlendMode = getProgressBackgroundTintBlendMode();
        if (progressBackgroundTintBlendMode != null) {
            return BlendMode.blendModeToPorterDuffMode(progressBackgroundTintBlendMode);
        }
        return null;
    }

    public BlendMode getProgressBackgroundTintBlendMode() {
        ProgressTintInfo progressTintInfo = this.mProgressTintInfo;
        if (progressTintInfo != null) {
            return progressTintInfo.mProgressBackgroundBlendMode;
        }
        return null;
    }

    @RemotableViewMethod
    public void setSecondaryProgressTintList(ColorStateList colorStateList) {
        if (this.mProgressTintInfo == null) {
            this.mProgressTintInfo = new ProgressTintInfo();
        }
        this.mProgressTintInfo.mSecondaryProgressTintList = colorStateList;
        this.mProgressTintInfo.mHasSecondaryProgressTint = true;
        if (this.mProgressDrawable != null) {
            applySecondaryProgressTint();
        }
    }

    public ColorStateList getSecondaryProgressTintList() {
        ProgressTintInfo progressTintInfo = this.mProgressTintInfo;
        if (progressTintInfo != null) {
            return progressTintInfo.mSecondaryProgressTintList;
        }
        return null;
    }

    public void setSecondaryProgressTintMode(PorterDuff.Mode mode) {
        setSecondaryProgressTintBlendMode(mode != null ? BlendMode.fromValue(mode.nativeInt) : null);
    }

    @RemotableViewMethod
    public void setSecondaryProgressTintBlendMode(BlendMode blendMode) {
        if (this.mProgressTintInfo == null) {
            this.mProgressTintInfo = new ProgressTintInfo();
        }
        this.mProgressTintInfo.mSecondaryProgressBlendMode = blendMode;
        this.mProgressTintInfo.mHasSecondaryProgressTintMode = true;
        if (this.mProgressDrawable != null) {
            applySecondaryProgressTint();
        }
    }

    public PorterDuff.Mode getSecondaryProgressTintMode() {
        BlendMode secondaryProgressTintBlendMode = getSecondaryProgressTintBlendMode();
        if (secondaryProgressTintBlendMode != null) {
            return BlendMode.blendModeToPorterDuffMode(secondaryProgressTintBlendMode);
        }
        return null;
    }

    public BlendMode getSecondaryProgressTintBlendMode() {
        ProgressTintInfo progressTintInfo = this.mProgressTintInfo;
        if (progressTintInfo != null) {
            return progressTintInfo.mSecondaryProgressBlendMode;
        }
        return null;
    }

    private Drawable getTintTarget(int i, boolean z) {
        Drawable drawable = this.mProgressDrawable;
        if (drawable != null) {
            this.mProgressDrawable = drawable.mutate();
            r1 = drawable instanceof LayerDrawable ? ((LayerDrawable) drawable).findDrawableByLayerId(i) : null;
            if (z && r1 == null) {
                return drawable;
            }
        }
        return r1;
    }

    public void setProgressDrawableTiled(Drawable drawable) {
        if (drawable != null) {
            drawable = tileify(drawable, false);
        }
        setProgressDrawable(drawable);
    }

    public Drawable getCurrentDrawable() {
        return this.mCurrentDrawable;
    }

    @Override // android.view.View
    protected boolean verifyDrawable(Drawable drawable) {
        return drawable == this.mProgressDrawable || drawable == this.mIndeterminateDrawable || super.verifyDrawable(drawable);
    }

    @Override // android.view.View
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable drawable = this.mProgressDrawable;
        if (drawable != null) {
            drawable.jumpToCurrentState();
        }
        Drawable drawable2 = this.mIndeterminateDrawable;
        if (drawable2 != null) {
            drawable2.jumpToCurrentState();
        }
    }

    @Override // android.view.View
    public void onResolveDrawables(int i) {
        Drawable drawable = this.mCurrentDrawable;
        if (drawable != null) {
            drawable.setLayoutDirection(i);
        }
        Drawable drawable2 = this.mIndeterminateDrawable;
        if (drawable2 != null) {
            drawable2.setLayoutDirection(i);
        }
        Drawable drawable3 = this.mProgressDrawable;
        if (drawable3 != null) {
            drawable3.setLayoutDirection(i);
        }
    }

    @Override // android.view.View
    public void postInvalidate() {
        if (this.mNoInvalidate) {
            return;
        }
        super.postInvalidate();
    }

    private class RefreshProgressRunnable implements Runnable {
        private RefreshProgressRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            synchronized (ProgressBar.this) {
                int size = ProgressBar.this.mRefreshData.size();
                for (int i = 0; i < size; i++) {
                    RefreshData refreshData = (RefreshData) ProgressBar.this.mRefreshData.get(i);
                    ProgressBar.this.doRefreshProgress(refreshData.id, refreshData.progress, refreshData.fromUser, true, refreshData.animate);
                    refreshData.recycle();
                }
                ProgressBar.this.mRefreshData.clear();
                ProgressBar.this.mRefreshIsPosted = false;
            }
        }
    }

    private static class RefreshData {
        private static final int POOL_MAX = 24;
        private static final Pools.SynchronizedPool<RefreshData> sPool = new Pools.SynchronizedPool<>(24);
        public boolean animate;
        public boolean fromUser;
        public int id;
        public int progress;

        private RefreshData() {
        }

        public static RefreshData obtain(int i, int i2, boolean z, boolean z2) {
            RefreshData acquire = sPool.acquire();
            if (acquire == null) {
                acquire = new RefreshData();
            }
            acquire.id = i;
            acquire.progress = i2;
            acquire.fromUser = z;
            acquire.animate = z2;
            return acquire;
        }

        public void recycle() {
            sPool.release(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void doRefreshProgress(int i, int i2, boolean z, boolean z2, boolean z3) {
        ObjectAnimator objectAnimator;
        Drawable drawable;
        int i3 = this.mMax - this.mMin;
        float f = i3 > 0 ? (i2 - r1) / i3 : 0.0f;
        boolean z4 = i == 16908301;
        int i4 = (int) (10000.0f * f);
        Drawable drawable2 = this.mCurrentDrawable;
        if (drawable2 != null) {
            if (drawable2 instanceof LayerDrawable) {
                Drawable findDrawableByLayerId = ((LayerDrawable) drawable2).findDrawableByLayerId(i);
                if (findDrawableByLayerId != null && canResolveLayoutDirection()) {
                    findDrawableByLayerId.setLayoutDirection(getLayoutDirection());
                }
                if (findDrawableByLayerId != null) {
                    drawable2 = findDrawableByLayerId;
                }
                drawable2.setLevel(i4);
            } else if (drawable2 instanceof StateListDrawable) {
                int stateCount = ((StateListDrawable) drawable2).getStateCount();
                for (int i5 = 0; i5 < stateCount; i5++) {
                    Drawable stateDrawable = ((StateListDrawable) drawable2).getStateDrawable(i5);
                    if (stateDrawable instanceof LayerDrawable) {
                        drawable = ((LayerDrawable) stateDrawable).findDrawableByLayerId(i);
                        if (drawable != null && canResolveLayoutDirection()) {
                            drawable.setLayoutDirection(getLayoutDirection());
                        }
                    } else {
                        drawable = null;
                    }
                    if (drawable == null) {
                        drawable = drawable2;
                    }
                    drawable.setLevel(i4);
                }
            } else {
                drawable2.setLevel(i4);
            }
        } else {
            invalidate();
        }
        if (z4 && z3) {
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, this.VISUAL_PROGRESS, f);
            ofFloat.setAutoCancel(true);
            ofFloat.setDuration(80L);
            ofFloat.setInterpolator(PROGRESS_ANIM_INTERPOLATOR);
            ofFloat.addListener(new AnimatorListenerAdapter() { // from class: android.widget.ProgressBar.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    ProgressBar.this.mLastProgressAnimator = null;
                }
            });
            ofFloat.start();
            this.mLastProgressAnimator = ofFloat;
        } else {
            if (z4 && (objectAnimator = this.mLastProgressAnimator) != null) {
                objectAnimator.cancel();
                this.mLastProgressAnimator = null;
            }
            setVisualProgress(i, f);
        }
        if (z4 && z2) {
            onProgressRefresh(f, z, i2);
        }
    }

    private float getPercent(int i) {
        float max = getMax();
        float min = getMin();
        float f = i;
        float f2 = max - min;
        if (f2 <= 0.0f) {
            return 0.0f;
        }
        return Math.max(0.0f, Math.min(1.0f, (f - min) / f2));
    }

    private CharSequence formatStateDescription(int i) {
        Locale locale = this.mContext.getResources().getConfiguration().getLocales().get(0);
        if (!locale.equals(this.mCachedLocale)) {
            this.mCachedLocale = locale;
            this.mPercentFormat = NumberFormat.getPercentInstance(locale);
        }
        return this.mPercentFormat.format(getPercent(i));
    }

    @Override // android.view.View
    @RemotableViewMethod
    public void setStateDescription(CharSequence charSequence) {
        super.setStateDescription(charSequence);
    }

    void onProgressRefresh(float f, boolean z, int i) {
        if (AccessibilityManager.getInstance(this.mContext).isEnabled() && getStateDescription() == null && !isIndeterminate()) {
            AccessibilityEvent obtain = AccessibilityEvent.obtain();
            obtain.setEventType(2048);
            obtain.setContentChangeTypes(64);
            sendAccessibilityEventUnchecked(obtain);
        }
        int i2 = this.mSecondaryProgress;
        if (i2 <= this.mProgress || z) {
            return;
        }
        refreshProgress(16908303, i2, false, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setVisualProgress(int i, float f) {
        this.mVisualProgress = f;
        Drawable drawable = this.mCurrentDrawable;
        if ((drawable instanceof LayerDrawable) && (drawable = ((LayerDrawable) drawable).findDrawableByLayerId(i)) == null) {
            drawable = this.mCurrentDrawable;
        }
        if (drawable != null) {
            drawable.setLevel((int) (10000.0f * f));
        } else {
            invalidate();
        }
        onVisualProgressChanged(i, f);
    }

    private synchronized void refreshProgress(int i, int i2, boolean z, boolean z2) {
        ProgressBar progressBar;
        try {
        } catch (Throwable th) {
            th = th;
        }
        try {
            if (this.mUiThreadId == Thread.currentThread().getId()) {
                progressBar = this;
                progressBar.doRefreshProgress(i, i2, z, true, z2);
            } else {
                progressBar = this;
                if (progressBar.mRefreshProgressRunnable == null) {
                    progressBar.mRefreshProgressRunnable = new RefreshProgressRunnable();
                }
                progressBar.mRefreshData.add(RefreshData.obtain(i, i2, z, z2));
                if (progressBar.mAttached && !progressBar.mRefreshIsPosted) {
                    progressBar.post(progressBar.mRefreshProgressRunnable);
                    progressBar.mRefreshIsPosted = true;
                }
            }
            return;
        } catch (Throwable th2) {
            th = th2;
            throw th;
        }
        throw th;
    }

    @RemotableViewMethod
    public synchronized void setProgress(int i) {
        setProgressInternal(i, false, false);
    }

    public void setProgress(int i, boolean z) {
        setProgressInternal(i, false, z);
    }

    @RemotableViewMethod
    synchronized boolean setProgressInternal(int i, boolean z, boolean z2) {
        Drawable findDrawableByLayerId;
        if (this.mIndeterminate) {
            return false;
        }
        int constrain = MathUtils.constrain(i, this.mSemMinEnabled ? this.mSemMin : this.mMin, this.mMax);
        if (constrain == this.mProgress) {
            return false;
        }
        this.mProgress = constrain;
        if (this.mCurrentMode == 6 && (getProgressDrawable() instanceof LayerDrawable) && (findDrawableByLayerId = ((LayerDrawable) getProgressDrawable()).findDrawableByLayerId(16908301)) != null && (findDrawableByLayerId instanceof CirCleProgressDrawable)) {
            ((CirCleProgressDrawable) findDrawableByLayerId).setProgress(constrain, z2);
        }
        refreshProgress(16908301, this.mProgress, z, z2);
        return true;
    }

    @RemotableViewMethod
    public synchronized void setSecondaryProgress(int i) {
        if (this.mIndeterminate) {
            return;
        }
        int i2 = this.mMin;
        if (i < i2) {
            i = i2;
        }
        int i3 = this.mMax;
        if (i > i3) {
            i = i3;
        }
        if (i != this.mSecondaryProgress) {
            this.mSecondaryProgress = i;
            refreshProgress(16908303, i, false, false);
        }
    }

    @ViewDebug.ExportedProperty(category = "progress")
    public synchronized int getProgress() {
        return this.mIndeterminate ? 0 : this.mProgress;
    }

    @ViewDebug.ExportedProperty(category = "progress")
    public synchronized int getSecondaryProgress() {
        return this.mIndeterminate ? 0 : this.mSecondaryProgress;
    }

    @ViewDebug.ExportedProperty(category = "progress")
    public synchronized int getMin() {
        return this.mMin;
    }

    @ViewDebug.ExportedProperty(category = "progress")
    public synchronized int getMax() {
        return this.mMax;
    }

    @RemotableViewMethod
    public synchronized void setMin(int i) {
        int i2;
        boolean z = this.mMaxInitialized;
        if (z && i > (i2 = this.mMax)) {
            i = i2;
        }
        this.mMinInitialized = true;
        if (z && i != this.mMin) {
            this.mMin = i;
            postInvalidate();
            if (this.mProgress < i) {
                this.mProgress = i;
            }
            refreshProgress(16908301, this.mProgress, false, false);
        } else {
            this.mMin = i;
        }
    }

    @RemotableViewMethod
    public synchronized void setMax(int i) {
        int i2;
        boolean z = this.mMinInitialized;
        if (z && i < (i2 = this.mMin)) {
            i = i2;
        }
        this.mMaxInitialized = true;
        if (z && i != this.mMax) {
            this.mMax = i;
            postInvalidate();
            if (this.mProgress > i) {
                this.mProgress = i;
            }
            refreshProgress(16908301, this.mProgress, false, false);
        } else {
            this.mMax = i;
        }
    }

    public final synchronized void incrementProgressBy(int i) {
        setProgress(this.mProgress + i);
    }

    public final synchronized void incrementSecondaryProgressBy(int i) {
        setSecondaryProgress(this.mSecondaryProgress + i);
    }

    void startAnimation() {
        if (getVisibility() == 0 && getWindowVisibility() == 0) {
            Drawable drawable = this.mIndeterminateDrawable;
            if (drawable instanceof Animatable) {
                this.mShouldStartAnimationDrawable = true;
                this.mHasAnimation = false;
                if (drawable instanceof AnimatedVectorDrawable) {
                    ((AnimatedVectorDrawable) drawable).registerAnimationCallback(this.mCircleAnimationCallback);
                }
            } else {
                this.mHasAnimation = true;
                if (this.mInterpolator == null) {
                    this.mInterpolator = new LinearInterpolator();
                }
                Transformation transformation = this.mTransformation;
                if (transformation == null) {
                    this.mTransformation = new Transformation();
                } else {
                    transformation.clear();
                }
                AlphaAnimation alphaAnimation = this.mAnimation;
                if (alphaAnimation == null) {
                    this.mAnimation = new AlphaAnimation(0.0f, 1.0f);
                } else {
                    alphaAnimation.reset();
                }
                this.mAnimation.setRepeatMode(this.mBehavior);
                this.mAnimation.setRepeatCount(-1);
                this.mAnimation.setDuration(this.mDuration);
                this.mAnimation.setInterpolator(this.mInterpolator);
                this.mAnimation.setStartTime(-1L);
            }
            postInvalidate();
        }
    }

    void stopAnimation() {
        this.mHasAnimation = false;
        Object obj = this.mIndeterminateDrawable;
        if (obj instanceof Animatable) {
            ((Animatable) obj).stop();
            Drawable drawable = this.mIndeterminateDrawable;
            if (drawable instanceof AnimatedVectorDrawable) {
                ((AnimatedVectorDrawable) drawable).unregisterAnimationCallback(this.mCircleAnimationCallback);
            }
            this.mShouldStartAnimationDrawable = false;
        }
        postInvalidate();
    }

    public void setInterpolator(Context context, int i) {
        setInterpolator(AnimationUtils.loadInterpolator(context, i));
    }

    public void setInterpolator(Interpolator interpolator) {
        this.mInterpolator = interpolator;
    }

    public Interpolator getInterpolator() {
        return this.mInterpolator;
    }

    @Override // android.view.View
    public void onVisibilityAggregated(boolean z) {
        super.onVisibilityAggregated(z);
        if (z != this.mAggregatedIsVisible) {
            this.mAggregatedIsVisible = z;
            if (this.mIndeterminate) {
                if (z) {
                    startAnimation();
                } else {
                    stopAnimation();
                }
            }
            Drawable drawable = this.mCurrentDrawable;
            if (drawable != null) {
                drawable.setVisible(z, false);
            }
        }
    }

    @Override // android.view.View, android.graphics.drawable.Drawable.Callback
    public void invalidateDrawable(Drawable drawable) {
        if (this.mInDrawing) {
            return;
        }
        if (verifyDrawable(drawable)) {
            Rect bounds = drawable.getBounds();
            int i = this.mScrollX + this.mPaddingLeft;
            int i2 = this.mScrollY + this.mPaddingTop;
            invalidate(bounds.left + i, bounds.top + i2, bounds.right + i, bounds.bottom + i2);
            return;
        }
        super.invalidateDrawable(drawable);
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        updateDrawableBounds(i, i2);
    }

    protected void updateDrawableBounds(int i, int i2) {
        int i3;
        int i4;
        int i5;
        int i6 = i - (this.mPaddingRight + this.mPaddingLeft);
        int i7 = i2 - (this.mPaddingTop + this.mPaddingBottom);
        Drawable drawable = this.mIndeterminateDrawable;
        if (drawable != null) {
            if (this.mOnlyIndeterminate && !(drawable instanceof AnimationDrawable)) {
                float intrinsicWidth = drawable.getIntrinsicWidth() / this.mIndeterminateDrawable.getIntrinsicHeight();
                float f = i6;
                float f2 = i7;
                float f3 = f / f2;
                if (intrinsicWidth != f3) {
                    if (f3 > intrinsicWidth) {
                        int i8 = (int) (f2 * intrinsicWidth);
                        int i9 = (i6 - i8) / 2;
                        i5 = i9;
                        i3 = i8 + i9;
                        i4 = 0;
                    } else {
                        int i10 = (int) (f * (1.0f / intrinsicWidth));
                        int i11 = (i7 - i10) / 2;
                        int i12 = i10 + i11;
                        i4 = i11;
                        i7 = i12;
                        i3 = i6;
                        i5 = 0;
                    }
                    if (this.mMirrorForRtl || !isLayoutRtl()) {
                        i6 = i3;
                    } else {
                        int i13 = i6 - i3;
                        i6 -= i5;
                        i5 = i13;
                    }
                    this.mIndeterminateDrawable.setBounds(i5, i4, i6, i7);
                }
            }
            i3 = i6;
            i4 = 0;
            i5 = 0;
            if (this.mMirrorForRtl) {
            }
            i6 = i3;
            this.mIndeterminateDrawable.setBounds(i5, i4, i6, i7);
        }
        Drawable drawable2 = this.mProgressDrawable;
        if (drawable2 != null) {
            drawable2.setBounds(0, 0, i6, i7);
        }
    }

    @Override // android.view.View
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawTrack(canvas);
    }

    /* JADX WARN: Multi-variable type inference failed */
    void drawTrack(Canvas canvas) {
        Drawable drawable = this.mCurrentDrawable;
        if (drawable != 0) {
            int save = canvas.save();
            if (this.mCurrentMode != 3 && this.mMirrorForRtl && isLayoutRtl()) {
                canvas.translate(getWidth() - this.mPaddingRight, this.mPaddingTop);
                canvas.scale(-1.0f, 1.0f);
            } else {
                canvas.translate(this.mPaddingLeft, this.mPaddingTop);
            }
            long drawingTime = getDrawingTime();
            if (this.mHasAnimation) {
                this.mAnimation.getTransformation(drawingTime, this.mTransformation);
                float alpha = this.mTransformation.getAlpha();
                try {
                    this.mInDrawing = true;
                    drawable.setLevel((int) (alpha * 10000.0f));
                    this.mInDrawing = false;
                    postInvalidateOnAnimation();
                } catch (Throwable th) {
                    this.mInDrawing = false;
                    throw th;
                }
            }
            drawable.draw(canvas);
            canvas.restoreToCount(save);
            if (this.mShouldStartAnimationDrawable && (drawable instanceof Animatable)) {
                ((Animatable) drawable).start();
                this.mShouldStartAnimationDrawable = false;
            }
        }
    }

    @Override // android.view.View
    protected synchronized void onMeasure(int i, int i2) {
        int i3;
        int i4;
        Drawable drawable = this.mCurrentDrawable;
        if (drawable != null) {
            i4 = Math.max(this.mMinWidth, Math.min(this.mMaxWidth, drawable.getIntrinsicWidth()));
            i3 = Math.max(this.mMinHeight, Math.min(this.mMaxHeight, drawable.getIntrinsicHeight()));
        } else {
            i3 = 0;
            i4 = 0;
        }
        updateDrawableState();
        int i5 = i4 + this.mPaddingLeft + this.mPaddingRight;
        int i6 = i3 + this.mPaddingTop + this.mPaddingBottom;
        int resolveSizeAndState = resolveSizeAndState(i5, i, 0);
        int resolveSizeAndState2 = resolveSizeAndState(i6, i2, 0);
        initCirCleStrokeWidth((resolveSizeAndState - this.mPaddingLeft) - this.mPaddingRight);
        if (this.mUseHorizontalProgress && this.mIndeterminate) {
            semSetIndeterminateProgressDrawable((resolveSizeAndState - this.mPaddingLeft) - this.mPaddingRight);
        }
        setMeasuredDimension(resolveSizeAndState, resolveSizeAndState2);
    }

    @Override // android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        updateDrawableState();
    }

    private void updateDrawableState() {
        int[] drawableState = getDrawableState();
        Drawable drawable = this.mProgressDrawable;
        boolean state = (drawable == null || !drawable.isStateful()) ? false : drawable.setState(drawableState);
        Drawable drawable2 = this.mIndeterminateDrawable;
        if (drawable2 != null && drawable2.isStateful()) {
            state |= drawable2.setState(drawableState);
        }
        if (state) {
            invalidate();
        }
    }

    @Override // android.view.View
    public void drawableHotspotChanged(float f, float f2) {
        super.drawableHotspotChanged(f, f2);
        Drawable drawable = this.mProgressDrawable;
        if (drawable != null) {
            drawable.setHotspot(f, f2);
        }
        Drawable drawable2 = this.mIndeterminateDrawable;
        if (drawable2 != null) {
            drawable2.setHotspot(f, f2);
        }
    }

    static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: android.widget.ProgressBar.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        int progress;
        int secondaryProgress;

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            this.progress = parcel.readInt();
            this.secondaryProgress = parcel.readInt();
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.progress);
            parcel.writeInt(this.secondaryProgress);
        }
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.progress = this.mProgress;
        savedState.secondaryProgress = this.mSecondaryProgress;
        return savedState;
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        setProgress(savedState.progress);
        setSecondaryProgress(savedState.secondaryProgress);
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
        ProgressBar progressBar;
        super.onAttachedToWindow();
        if (this.mIndeterminate) {
            startAnimation();
        }
        if (this.mRefreshData != null) {
            synchronized (this) {
                try {
                    try {
                        int size = this.mRefreshData.size();
                        int i = 0;
                        while (i < size) {
                            RefreshData refreshData = this.mRefreshData.get(i);
                            ProgressBar progressBar2 = this;
                            progressBar2.doRefreshProgress(refreshData.id, refreshData.progress, refreshData.fromUser, true, refreshData.animate);
                            refreshData.recycle();
                            i++;
                            this = progressBar2;
                        }
                        progressBar = this;
                        progressBar.mRefreshData.clear();
                    } catch (Throwable th) {
                        th = th;
                        ProgressBar progressBar3 = this;
                        Throwable th2 = th;
                        throw th2;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    Throwable th22 = th;
                    throw th22;
                }
            }
        } else {
            progressBar = this;
        }
        progressBar.mAttached = true;
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        if (this.mIndeterminate) {
            stopAnimation();
        } else {
            this.mCircleAnimationCallback = null;
        }
        RefreshProgressRunnable refreshProgressRunnable = this.mRefreshProgressRunnable;
        if (refreshProgressRunnable != null) {
            removeCallbacks(refreshProgressRunnable);
            this.mRefreshIsPosted = false;
        }
        super.onDetachedFromWindow();
        this.mAttached = false;
    }

    @Override // android.view.View
    public CharSequence getAccessibilityClassName() {
        return ProgressBar.class.getName();
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEventInternal(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEventInternal(accessibilityEvent);
        accessibilityEvent.setItemCount(this.mMax - this.mMin);
        accessibilityEvent.setCurrentItemIndex(this.mProgress);
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
        AccessibilityNodeInfo.RangeInfo rangeInfo;
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        if (isIndeterminate()) {
            rangeInfo = Flags.indeterminateRangeInfo() ? AccessibilityNodeInfo.RangeInfo.INDETERMINATE : null;
        } else {
            rangeInfo = new AccessibilityNodeInfo.RangeInfo(0, getMin(), getMax(), getProgress());
        }
        accessibilityNodeInfo.setRangeInfo(rangeInfo);
        if (getStateDescription() == null) {
            if (isIndeterminate()) {
                accessibilityNodeInfo.setStateDescription(getResources().getString(R.string.in_progress));
            } else {
                accessibilityNodeInfo.setStateDescription(formatStateDescription(this.mProgress));
            }
        }
    }

    @Override // android.view.View
    protected void encodeProperties(ViewHierarchyEncoder viewHierarchyEncoder) {
        super.encodeProperties(viewHierarchyEncoder);
        viewHierarchyEncoder.addProperty("progress:max", getMax());
        viewHierarchyEncoder.addProperty("progress:progress", getProgress());
        viewHierarchyEncoder.addProperty("progress:secondaryProgress", getSecondaryProgress());
        viewHierarchyEncoder.addProperty("progress:indeterminate", isIndeterminate());
    }

    public boolean isAnimating() {
        return isIndeterminate() && getWindowVisibility() == 0 && isShown();
    }

    private static class ProgressTintInfo {
        boolean mHasIndeterminateTint;
        boolean mHasIndeterminateTintMode;
        boolean mHasProgressBackgroundTint;
        boolean mHasProgressBackgroundTintMode;
        boolean mHasProgressTint;
        boolean mHasProgressTintMode;
        boolean mHasSecondaryProgressTint;
        boolean mHasSecondaryProgressTintMode;
        BlendMode mIndeterminateBlendMode;
        ColorStateList mIndeterminateTintList;
        BlendMode mProgressBackgroundBlendMode;
        ColorStateList mProgressBackgroundTintList;
        BlendMode mProgressBlendMode;
        ColorStateList mProgressTintList;
        BlendMode mSecondaryProgressBlendMode;
        ColorStateList mSecondaryProgressTintList;

        private ProgressTintInfo() {
        }
    }

    public void semSetMin(int i) {
        this.mMin = 0;
        this.mSemMin = i;
        this.mSemMinEnabled = true;
    }

    @Deprecated
    public void semSetMode(int i) {
        this.mCurrentMode = i;
        if (i == 3) {
            Drawable drawable = this.mContext.getDrawable(R.drawable.tw_scrubber_progress_vertical_material);
            if (drawable != null) {
                setProgressDrawableTiled(drawable);
                return;
            }
            return;
        }
        if (i != 4) {
            if (i != 6) {
                return;
            }
            initializeRoundCicleMode();
        } else {
            Drawable drawable2 = this.mContext.getDrawable(R.drawable.tw_split_seekbar_background_progress_material);
            if (drawable2 != null) {
                setProgressDrawableTiled(drawable2);
            }
        }
    }

    protected void onSlidingRefresh(int i) {
        Drawable drawable = this.mCurrentDrawable;
        if (drawable != null) {
            Drawable findDrawableByLayerId = drawable instanceof LayerDrawable ? ((LayerDrawable) drawable).findDrawableByLayerId(16908301) : null;
            if (findDrawableByLayerId != null) {
                findDrawableByLayerId.setLevel(i);
            }
        }
    }

    private void initCirCleStrokeWidth(int i) {
        if (this.mContext.getResources().getDimensionPixelSize(R.dimen.sem_progress_bar_size_small) == i) {
            this.mRoundStrokeWidth = this.mContext.getResources().getDimensionPixelSize(R.dimen.sem_progress_circle_size_small_width);
            return;
        }
        if (this.mContext.getResources().getDimensionPixelSize(R.dimen.sem_progress_bar_size_small_title) == i) {
            this.mRoundStrokeWidth = this.mContext.getResources().getDimensionPixelSize(R.dimen.sem_progress_circle_size_small_title_width);
        } else if (this.mContext.getResources().getDimensionPixelSize(R.dimen.sem_progress_bar_size_large) == i) {
            this.mRoundStrokeWidth = this.mContext.getResources().getDimensionPixelSize(R.dimen.sem_progress_circle_size_large_width);
        } else {
            this.mRoundStrokeWidth = this.mContext.getResources().getDimensionPixelSize(R.dimen.sem_progress_circle_size_normal_width);
        }
    }

    private void semSetIndeterminateProgressDrawable(int i) {
        if (getResources().getDimensionPixelSize(R.dimen.sem_progress_bar_indeterminate_xsmall) >= i) {
            setIndeterminateDrawable(this.mIndeterminateHorizontalXsmall);
            return;
        }
        if (getResources().getDimensionPixelSize(R.dimen.sem_progress_bar_indeterminate_small) >= i) {
            setIndeterminateDrawable(this.mIndeterminateHorizontalSmall);
            return;
        }
        if (getResources().getDimensionPixelSize(R.dimen.sem_progress_bar_indeterminate_medium) >= i) {
            setIndeterminateDrawable(this.mIndeterminateHorizontalMedium);
        } else if (getResources().getDimensionPixelSize(R.dimen.sem_progress_bar_indeterminate_large) >= i) {
            setIndeterminateDrawable(this.mIndeterminateHorizontalLarge);
        } else {
            setIndeterminateDrawable(this.mIndeterminateHorizontalXlarge);
        }
    }

    private static class CircleAnimationCallback extends Animatable2.AnimationCallback {
        final Handler mHandler = new Handler(Looper.getMainLooper());
        private WeakReference<ProgressBar> mProgressBar;

        public CircleAnimationCallback(ProgressBar progressBar) {
            this.mProgressBar = new WeakReference<>(progressBar);
        }

        @Override // android.graphics.drawable.Animatable2.AnimationCallback
        public void onAnimationEnd(Drawable drawable) {
            this.mHandler.post(new Runnable() { // from class: android.widget.ProgressBar.CircleAnimationCallback.1
                @Override // java.lang.Runnable
                public void run() {
                    ProgressBar progressBar = (ProgressBar) CircleAnimationCallback.this.mProgressBar.get();
                    if (progressBar == null) {
                        return;
                    }
                    ((AnimatedVectorDrawable) progressBar.mIndeterminateDrawable).start();
                }
            });
        }
    }

    private void initializeRoundCicleMode() {
        this.mOnlyIndeterminate = false;
        setIndeterminate(false);
        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{new CirCleProgressDrawable(true, colorToColorStateList(this.mContext.getResources().getColor(R.color.tw_progress_color_control_bg_light))), new CirCleProgressDrawable(false, colorToColorStateList(this.mContext.getResources().getColor(R.color.tw_progress_color_control_activated_light)))});
        layerDrawable.setPaddingMode(1);
        layerDrawable.setId(0, 16908288);
        layerDrawable.setId(1, 16908301);
        setProgressDrawable(layerDrawable);
    }

    private ColorStateList colorToColorStateList(int i) {
        return new ColorStateList(new int[][]{new int[0]}, new int[]{i});
    }

    private class CirCleProgressDrawable extends Drawable {
        private final IntProperty<CirCleProgressDrawable> VISUAL_CIRCLE_PROGRESS;
        int mAlpha;
        private RectF mArcRect;
        int mColor;
        ColorStateList mColorStateList;
        private boolean mIsBackground;
        private final Paint mPaint;
        public int mProgress;
        private final ProgressState mState;

        private int modulateAlpha(int i, int i2) {
            return (i * (i2 + (i2 >>> 7))) >>> 8;
        }

        @Override // android.graphics.drawable.Drawable
        public boolean isStateful() {
            return true;
        }

        public CirCleProgressDrawable(boolean z, ColorStateList colorStateList) {
            Paint paint = new Paint();
            this.mPaint = paint;
            this.mAlpha = 255;
            this.mArcRect = new RectF();
            this.mState = new ProgressState();
            this.VISUAL_CIRCLE_PROGRESS = new IntProperty<CirCleProgressDrawable>("visual_progress") { // from class: android.widget.ProgressBar.CirCleProgressDrawable.1
                @Override // android.util.IntProperty
                public void setValue(CirCleProgressDrawable cirCleProgressDrawable, int i) {
                    cirCleProgressDrawable.mProgress = i;
                    CirCleProgressDrawable.this.invalidateSelf();
                }

                @Override // android.util.Property
                public Integer get(CirCleProgressDrawable cirCleProgressDrawable) {
                    return Integer.valueOf(cirCleProgressDrawable.mProgress);
                }
            };
            this.mIsBackground = z;
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeCap(Paint.Cap.ROUND);
            this.mColorStateList = colorStateList;
            int defaultColor = colorStateList.getDefaultColor();
            this.mColor = defaultColor;
            paint.setColor(defaultColor);
            this.mProgress = 0;
        }

        @Override // android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            this.mPaint.setStrokeWidth(ProgressBar.this.mRoundStrokeWidth);
            int alpha = this.mPaint.getAlpha();
            this.mPaint.setAlpha(modulateAlpha(alpha, this.mAlpha));
            this.mArcRect.set(ProgressBar.this.mRoundStrokeWidth / 2.0f, ProgressBar.this.mRoundStrokeWidth / 2.0f, ProgressBar.this.getWidth() - (ProgressBar.this.mRoundStrokeWidth / 2.0f), ProgressBar.this.getWidth() - (ProgressBar.this.mRoundStrokeWidth / 2.0f));
            int i = ProgressBar.this.mMax - ProgressBar.this.mMin;
            float f = i > 0 ? (this.mProgress - ProgressBar.this.mMin) / i : 0.0f;
            canvas.save();
            if (this.mIsBackground) {
                canvas.drawArc(this.mArcRect, 270.0f, 360.0f, false, this.mPaint);
            } else {
                canvas.drawArc(this.mArcRect, 270.0f, f * 360.0f, false, this.mPaint);
            }
            canvas.restore();
            this.mPaint.setAlpha(alpha);
        }

        public void setProgress(int i, boolean z) {
            if (z) {
                ObjectAnimator ofInt = ObjectAnimator.ofInt(this, this.VISUAL_CIRCLE_PROGRESS, i);
                ofInt.setAutoCancel(true);
                ofInt.setDuration(80L);
                ofInt.setInterpolator(ProgressBar.PROGRESS_ANIM_INTERPOLATOR);
                ofInt.start();
                return;
            }
            this.mProgress = i;
            ProgressBar.this.invalidate();
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
                int defaultColor = colorStateList.getDefaultColor();
                this.mColor = defaultColor;
                this.mPaint.setColor(defaultColor);
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
            return onStateChange;
        }

        @Override // android.graphics.drawable.Drawable
        public Drawable.ConstantState getConstantState() {
            return this.mState;
        }

        private class ProgressState extends Drawable.ConstantState {
            @Override // android.graphics.drawable.Drawable.ConstantState
            public int getChangingConfigurations() {
                return 0;
            }

            private ProgressState() {
            }

            @Override // android.graphics.drawable.Drawable.ConstantState
            public Drawable newDrawable() {
                return CirCleProgressDrawable.this;
            }
        }
    }

    @RemotableViewMethod
    public void hidden_semSetInterpolator(int i) {
        if (this.mContext != null) {
            setInterpolator(this.mContext, i);
        }
    }
}
