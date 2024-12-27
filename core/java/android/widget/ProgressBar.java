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
/* loaded from: classes4.dex */
public class ProgressBar extends View {
    private static final int MAX_LEVEL = 10000;
    public static final int MODE_STANDARD = 0;
    private static final int PROGRESS_ANIM_DURATION = 80;
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
    private static boolean DEBUG = false;
    private static final DecelerateInterpolator PROGRESS_ANIM_INTERPOLATOR = new DecelerateInterpolator();

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
            this.mMinId = propertyMapper.mapInt("min", 16844089);
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
        public void readProperties(ProgressBar node, PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readBoolean(this.mIndeterminateId, node.isIndeterminate());
            propertyReader.readObject(this.mIndeterminateDrawableId, node.getIndeterminateDrawable());
            propertyReader.readObject(this.mIndeterminateTintId, node.getIndeterminateTintList());
            propertyReader.readObject(this.mIndeterminateTintBlendModeId, node.getIndeterminateTintBlendMode());
            propertyReader.readObject(this.mIndeterminateTintModeId, node.getIndeterminateTintMode());
            propertyReader.readObject(this.mInterpolatorId, node.getInterpolator());
            propertyReader.readInt(this.mMaxId, node.getMax());
            propertyReader.readInt(this.mMinId, node.getMin());
            propertyReader.readBoolean(this.mMirrorForRtlId, node.getMirrorForRtl());
            propertyReader.readInt(this.mProgressId, node.getProgress());
            propertyReader.readObject(this.mProgressBackgroundTintId, node.getProgressBackgroundTintList());
            propertyReader.readObject(this.mProgressBackgroundTintBlendModeId, node.getProgressBackgroundTintBlendMode());
            propertyReader.readObject(this.mProgressBackgroundTintModeId, node.getProgressBackgroundTintMode());
            propertyReader.readObject(this.mProgressDrawableId, node.getProgressDrawable());
            propertyReader.readObject(this.mProgressTintId, node.getProgressTintList());
            propertyReader.readObject(this.mProgressTintBlendModeId, node.getProgressTintBlendMode());
            propertyReader.readObject(this.mProgressTintModeId, node.getProgressTintMode());
            propertyReader.readInt(this.mSecondaryProgressId, node.getSecondaryProgress());
            propertyReader.readObject(this.mSecondaryProgressTintId, node.getSecondaryProgressTintList());
            propertyReader.readObject(this.mSecondaryProgressTintBlendModeId, node.getSecondaryProgressTintBlendMode());
            propertyReader.readObject(this.mSecondaryProgressTintModeId, node.getSecondaryProgressTintMode());
        }
    }

    public ProgressBar(Context context) {
        this(context, null);
    }

    public ProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 16842871);
    }

    public ProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ProgressBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        boolean z;
        this.mSampleWidth = 0;
        this.mMirrorForRtl = false;
        this.mRefreshData = new ArrayList<>();
        this.mCurrentMode = 0;
        this.mUseHorizontalProgress = false;
        this.VISUAL_PROGRESS = new FloatProperty<ProgressBar>("visual_progress") { // from class: android.widget.ProgressBar.2
            @Override // android.util.FloatProperty
            public void setValue(ProgressBar object, float value) {
                object.setVisualProgress(16908301, value);
                object.mVisualProgress = value;
            }

            @Override // android.util.Property
            public Float get(ProgressBar object) {
                return Float.valueOf(object.mVisualProgress);
            }
        };
        this.mUiThreadId = Thread.currentThread().getId();
        initProgressBar();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ProgressBar, defStyleAttr, defStyleRes);
        saveAttributeDataForStyleable(context, R.styleable.ProgressBar, attrs, a, defStyleAttr, defStyleRes);
        this.mNoInvalidate = true;
        Drawable progressDrawable = a.getDrawable(8);
        if (progressDrawable != null) {
            Drawable progressDrawable2 = progressDrawable.mutate();
            if (needsTileify(progressDrawable2)) {
                setProgressDrawableTiled(progressDrawable2);
            } else {
                setProgressDrawable(progressDrawable2);
            }
        }
        this.mDuration = a.getInt(9, this.mDuration);
        this.mMinWidth = a.getDimensionPixelSize(11, this.mMinWidth);
        this.mMaxWidth = a.getDimensionPixelSize(0, this.mMaxWidth);
        this.mMinHeight = a.getDimensionPixelSize(12, this.mMinHeight);
        this.mMaxHeight = a.getDimensionPixelSize(1, this.mMaxHeight);
        this.mBehavior = a.getInt(10, this.mBehavior);
        int resID = a.getResourceId(13, 17432587);
        if (resID > 0) {
            setInterpolator(context, resID);
        }
        setMin(a.getInt(26, this.mMin));
        setMax(a.getInt(2, this.mMax));
        setProgress(a.getInt(3, this.mProgress));
        setSecondaryProgress(a.getInt(4, this.mSecondaryProgress));
        Drawable indeterminateDrawable = a.getDrawable(7);
        if (indeterminateDrawable != null) {
            if (needsTileify(indeterminateDrawable)) {
                setIndeterminateDrawableTiled(indeterminateDrawable);
            } else {
                setIndeterminateDrawable(indeterminateDrawable);
            }
        }
        this.mOnlyIndeterminate = a.getBoolean(6, this.mOnlyIndeterminate);
        this.mNoInvalidate = false;
        if (!this.mOnlyIndeterminate && !a.getBoolean(5, this.mIndeterminate)) {
            z = false;
        } else {
            z = true;
        }
        setIndeterminate(z);
        this.mMirrorForRtl = a.getBoolean(15, this.mMirrorForRtl);
        if (a.hasValue(17)) {
            if (this.mProgressTintInfo == null) {
                this.mProgressTintInfo = new ProgressTintInfo();
            }
            this.mProgressTintInfo.mProgressBlendMode = Drawable.parseBlendMode(a.getInt(17, -1), null);
            this.mProgressTintInfo.mHasProgressTintMode = true;
        }
        if (a.hasValue(16)) {
            if (this.mProgressTintInfo == null) {
                this.mProgressTintInfo = new ProgressTintInfo();
            }
            this.mProgressTintInfo.mProgressTintList = a.getColorStateList(16);
            this.mProgressTintInfo.mHasProgressTint = true;
        }
        if (a.hasValue(19)) {
            if (this.mProgressTintInfo == null) {
                this.mProgressTintInfo = new ProgressTintInfo();
            }
            this.mProgressTintInfo.mProgressBackgroundBlendMode = Drawable.parseBlendMode(a.getInt(19, -1), null);
            this.mProgressTintInfo.mHasProgressBackgroundTintMode = true;
        }
        if (a.hasValue(18)) {
            if (this.mProgressTintInfo == null) {
                this.mProgressTintInfo = new ProgressTintInfo();
            }
            this.mProgressTintInfo.mProgressBackgroundTintList = a.getColorStateList(18);
            this.mProgressTintInfo.mHasProgressBackgroundTint = true;
        }
        if (a.hasValue(21)) {
            if (this.mProgressTintInfo == null) {
                this.mProgressTintInfo = new ProgressTintInfo();
            }
            this.mProgressTintInfo.mSecondaryProgressBlendMode = Drawable.parseBlendMode(a.getInt(21, -1), null);
            this.mProgressTintInfo.mHasSecondaryProgressTintMode = true;
        }
        if (a.hasValue(20)) {
            if (this.mProgressTintInfo == null) {
                this.mProgressTintInfo = new ProgressTintInfo();
            }
            this.mProgressTintInfo.mSecondaryProgressTintList = a.getColorStateList(20);
            this.mProgressTintInfo.mHasSecondaryProgressTint = true;
        }
        if (a.hasValue(23)) {
            if (this.mProgressTintInfo == null) {
                this.mProgressTintInfo = new ProgressTintInfo();
            }
            this.mProgressTintInfo.mIndeterminateBlendMode = Drawable.parseBlendMode(a.getInt(23, -1), null);
            this.mProgressTintInfo.mHasIndeterminateTintMode = true;
        }
        if (a.hasValue(22)) {
            if (this.mProgressTintInfo == null) {
                this.mProgressTintInfo = new ProgressTintInfo();
            }
            this.mProgressTintInfo.mIndeterminateTintList = a.getColorStateList(22);
            this.mProgressTintInfo.mHasIndeterminateTint = true;
        }
        a.recycle();
        applyProgressTints();
        applyIndeterminateTint();
        if (getImportantForAccessibility() == 0) {
            setImportantForAccessibility(1);
        }
        this.mDensity = context.getResources().getDisplayMetrics().density;
        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.parentIsDeviceDefaultDark, outValue, true);
        this.mIsDeviceDefaultDark = outValue.data != 0;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SemProgressBar, defStyleAttr, defStyleRes);
        this.mUseHorizontalProgress = ta.getBoolean(6, this.mUseHorizontalProgress);
        ta.recycle();
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, 16974123);
        this.mIndeterminateHorizontalXsmall = getResources().getDrawable(R.drawable.sem_progress_bar_indeterminate_xsmall_transition, contextThemeWrapper.getTheme());
        this.mIndeterminateHorizontalSmall = getResources().getDrawable(R.drawable.sem_progress_bar_indeterminate_small_transition, contextThemeWrapper.getTheme());
        this.mIndeterminateHorizontalMedium = getResources().getDrawable(R.drawable.sem_progress_bar_indeterminate_medium_transition, contextThemeWrapper.getTheme());
        this.mIndeterminateHorizontalLarge = getResources().getDrawable(R.drawable.sem_progress_bar_indeterminate_large_transition, contextThemeWrapper.getTheme());
        this.mIndeterminateHorizontalXlarge = getResources().getDrawable(R.drawable.sem_progress_bar_indeterminate_xlarge_transition, contextThemeWrapper.getTheme());
        this.mCircleAnimationCallback = new CircleAnimationCallback(this);
    }

    public void setMinWidth(int minWidth) {
        this.mMinWidth = minWidth;
        requestLayout();
    }

    public int getMinWidth() {
        return this.mMinWidth;
    }

    public void setMaxWidth(int maxWidth) {
        this.mMaxWidth = maxWidth;
        requestLayout();
    }

    public int getMaxWidth() {
        return this.mMaxWidth;
    }

    public void setMinHeight(int minHeight) {
        this.mMinHeight = minHeight;
        requestLayout();
    }

    public int getMinHeight() {
        return this.mMinHeight;
    }

    public void setMaxHeight(int maxHeight) {
        this.mMaxHeight = maxHeight;
        requestLayout();
    }

    public int getMaxHeight() {
        return this.mMaxHeight;
    }

    private static boolean needsTileify(Drawable dr) {
        if (dr instanceof LayerDrawable) {
            LayerDrawable orig = (LayerDrawable) dr;
            int N = orig.getNumberOfLayers();
            for (int i = 0; i < N; i++) {
                if (needsTileify(orig.getDrawable(i))) {
                    return true;
                }
            }
            return false;
        }
        if (!(dr instanceof StateListDrawable)) {
            return dr instanceof BitmapDrawable;
        }
        StateListDrawable in = (StateListDrawable) dr;
        int N2 = in.getStateCount();
        for (int i2 = 0; i2 < N2; i2++) {
            if (needsTileify(in.getStateDrawable(i2))) {
                return true;
            }
        }
        return false;
    }

    private Drawable tileify(Drawable drawable, boolean clip) {
        if (drawable instanceof LayerDrawable) {
            LayerDrawable orig = (LayerDrawable) drawable;
            int N = orig.getNumberOfLayers();
            Drawable[] outDrawables = new Drawable[N];
            for (int i = 0; i < N; i++) {
                int id = orig.getId(i);
                outDrawables[i] = tileify(orig.getDrawable(i), id == 16908301 || id == 16908303);
            }
            LayerDrawable clone = new LayerDrawable(outDrawables);
            for (int i2 = 0; i2 < N; i2++) {
                clone.setId(i2, orig.getId(i2));
                clone.setLayerGravity(i2, orig.getLayerGravity(i2));
                clone.setLayerWidth(i2, orig.getLayerWidth(i2));
                clone.setLayerHeight(i2, orig.getLayerHeight(i2));
                clone.setLayerInsetLeft(i2, orig.getLayerInsetLeft(i2));
                clone.setLayerInsetRight(i2, orig.getLayerInsetRight(i2));
                clone.setLayerInsetTop(i2, orig.getLayerInsetTop(i2));
                clone.setLayerInsetBottom(i2, orig.getLayerInsetBottom(i2));
                clone.setLayerInsetStart(i2, orig.getLayerInsetStart(i2));
                clone.setLayerInsetEnd(i2, orig.getLayerInsetEnd(i2));
            }
            return clone;
        }
        if (drawable instanceof StateListDrawable) {
            StateListDrawable in = (StateListDrawable) drawable;
            StateListDrawable out = new StateListDrawable();
            int N2 = in.getStateCount();
            for (int i3 = 0; i3 < N2; i3++) {
                out.addState(in.getStateSet(i3), tileify(in.getStateDrawable(i3), clip));
            }
            return out;
        }
        if (drawable instanceof BitmapDrawable) {
            Drawable.ConstantState cs = drawable.getConstantState();
            BitmapDrawable clone2 = (BitmapDrawable) cs.newDrawable(getResources());
            clone2.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
            if (this.mSampleWidth <= 0) {
                this.mSampleWidth = clone2.getIntrinsicWidth();
            }
            if (clip) {
                return new ClipDrawable(clone2, 3, 1);
            }
            return clone2;
        }
        return drawable;
    }

    Shape getDrawableShape() {
        float[] roundedCorners = {5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f};
        return new RoundRectShape(roundedCorners, null, null);
    }

    private Drawable tileifyIndeterminate(Drawable drawable) {
        if (drawable instanceof AnimationDrawable) {
            AnimationDrawable background = (AnimationDrawable) drawable;
            int N = background.getNumberOfFrames();
            AnimationDrawable newBg = new AnimationDrawable();
            newBg.setOneShot(background.isOneShot());
            for (int i = 0; i < N; i++) {
                Drawable frame = tileify(background.getFrame(i), true);
                frame.setLevel(10000);
                newBg.addFrame(frame, background.getDuration(i));
            }
            newBg.setLevel(10000);
            return newBg;
        }
        return drawable;
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
    public synchronized void setIndeterminate(boolean indeterminate) {
        if ((!this.mOnlyIndeterminate || !this.mIndeterminate) && indeterminate != this.mIndeterminate) {
            this.mIndeterminate = indeterminate;
            if (indeterminate) {
                swapCurrentDrawable(this.mIndeterminateDrawable);
                startAnimation();
            } else {
                swapCurrentDrawable(this.mProgressDrawable);
                stopAnimation();
            }
            notifyViewAccessibilityStateChangedIfNeeded(0);
        }
    }

    private void swapCurrentDrawable(Drawable newDrawable) {
        Drawable oldDrawable = this.mCurrentDrawable;
        this.mCurrentDrawable = newDrawable;
        if (oldDrawable != this.mCurrentDrawable) {
            if (oldDrawable != null) {
                oldDrawable.setVisible(false, false);
            }
            if (this.mCurrentDrawable != null) {
                this.mCurrentDrawable.setVisible(getWindowVisibility() == 0 && isShown(), false);
            }
        }
    }

    public Drawable getIndeterminateDrawable() {
        return this.mIndeterminateDrawable;
    }

    public void setIndeterminateDrawable(Drawable d) {
        if (this.mIndeterminateDrawable != d) {
            if (this.mIndeterminateDrawable != null) {
                if (this.mUseHorizontalProgress) {
                    stopAnimation();
                }
                this.mIndeterminateDrawable.setCallback(null);
                unscheduleDrawable(this.mIndeterminateDrawable);
            }
            this.mIndeterminateDrawable = d;
            if (d != null) {
                d.setCallback(this);
                d.setLayoutDirection(getLayoutDirection());
                if (d.isStateful()) {
                    d.setState(getDrawableState());
                }
                applyIndeterminateTint();
            }
            if (this.mIndeterminate) {
                if (this.mUseHorizontalProgress) {
                    startAnimation();
                }
                swapCurrentDrawable(d);
                postInvalidate();
            }
        }
    }

    @RemotableViewMethod
    public void setIndeterminateTintList(ColorStateList tint) {
        if (this.mProgressTintInfo == null) {
            this.mProgressTintInfo = new ProgressTintInfo();
        }
        this.mProgressTintInfo.mIndeterminateTintList = tint;
        this.mProgressTintInfo.mHasIndeterminateTint = true;
        applyIndeterminateTint();
    }

    public ColorStateList getIndeterminateTintList() {
        if (this.mProgressTintInfo != null) {
            return this.mProgressTintInfo.mIndeterminateTintList;
        }
        return null;
    }

    public void setIndeterminateTintMode(PorterDuff.Mode tintMode) {
        setIndeterminateTintBlendMode(tintMode != null ? BlendMode.fromValue(tintMode.nativeInt) : null);
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
        BlendMode mode = getIndeterminateTintBlendMode();
        if (mode != null) {
            return BlendMode.blendModeToPorterDuffMode(mode);
        }
        return null;
    }

    public BlendMode getIndeterminateTintBlendMode() {
        if (this.mProgressTintInfo != null) {
            return this.mProgressTintInfo.mIndeterminateBlendMode;
        }
        return null;
    }

    private void applyIndeterminateTint() {
        if (this.mIndeterminateDrawable != null && this.mProgressTintInfo != null) {
            ProgressTintInfo tintInfo = this.mProgressTintInfo;
            if (tintInfo.mHasIndeterminateTint || tintInfo.mHasIndeterminateTintMode) {
                this.mIndeterminateDrawable = this.mIndeterminateDrawable.mutate();
                if (tintInfo.mHasIndeterminateTint) {
                    this.mIndeterminateDrawable.setTintList(tintInfo.mIndeterminateTintList);
                }
                if (tintInfo.mHasIndeterminateTintMode) {
                    this.mIndeterminateDrawable.setTintBlendMode(tintInfo.mIndeterminateBlendMode);
                }
                if (this.mIndeterminateDrawable.isStateful()) {
                    this.mIndeterminateDrawable.setState(getDrawableState());
                }
            }
        }
    }

    public void setIndeterminateDrawableTiled(Drawable d) {
        if (d != null) {
            d = tileifyIndeterminate(d);
        }
        setIndeterminateDrawable(d);
    }

    public Drawable getProgressDrawable() {
        return this.mProgressDrawable;
    }

    public void setProgressDrawable(Drawable d) {
        if (this.mProgressDrawable != d) {
            if (this.mProgressDrawable != null) {
                this.mProgressDrawable.setCallback(null);
                unscheduleDrawable(this.mProgressDrawable);
            }
            this.mProgressDrawable = d;
            if (d != null) {
                d.setCallback(this);
                d.setLayoutDirection(getLayoutDirection());
                if (d.isStateful()) {
                    d.setState(getDrawableState());
                }
                if (this.mCurrentMode == 3) {
                    int drawableWidth = d.getMinimumWidth();
                    if (this.mMaxWidth < drawableWidth) {
                        this.mMaxWidth = drawableWidth;
                        requestLayout();
                    }
                } else {
                    int drawableHeight = d.getMinimumHeight();
                    if (this.mMaxHeight < drawableHeight) {
                        this.mMaxHeight = drawableHeight;
                        requestLayout();
                    }
                }
                applyProgressTints();
            }
            if (!this.mIndeterminate) {
                swapCurrentDrawable(d);
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
        if (this.mProgressDrawable != null && this.mProgressTintInfo != null) {
            applyPrimaryProgressTint();
            applyProgressBackgroundTint();
            applySecondaryProgressTint();
        }
    }

    private void applyPrimaryProgressTint() {
        if (this.mProgressTintInfo.mHasProgressTint || this.mProgressTintInfo.mHasProgressTintMode) {
            if (this.mProgressDrawable instanceof VectorDrawable) {
                this.mProgressDrawable = this.mProgressDrawable.mutate();
                if (this.mProgressTintInfo.mHasProgressTint) {
                    ((VectorDrawable) this.mProgressDrawable).setPathStrokeColor(SEM_PROGRESS_PATH_NAME_PRIMARY, this.mProgressTintInfo.mProgressTintList.getDefaultColor());
                    return;
                }
                return;
            }
            Drawable target = getTintTarget(16908301, true);
            if (target != null) {
                if (this.mProgressTintInfo.mHasProgressTint) {
                    target.setTintList(this.mProgressTintInfo.mProgressTintList);
                }
                if (this.mProgressTintInfo.mHasProgressTintMode) {
                    target.setTintBlendMode(this.mProgressTintInfo.mProgressBlendMode);
                }
                if (target.isStateful()) {
                    target.setState(getDrawableState());
                }
            }
        }
    }

    private void applyProgressBackgroundTint() {
        if (this.mProgressTintInfo.mHasProgressBackgroundTint || this.mProgressTintInfo.mHasProgressBackgroundTintMode) {
            if (this.mProgressDrawable instanceof VectorDrawable) {
                this.mProgressDrawable = this.mProgressDrawable.mutate();
                if (this.mProgressTintInfo.mHasProgressBackgroundTint) {
                    ((VectorDrawable) this.mProgressDrawable).setPathStrokeColor(SEM_PROGRESS_PATH_NAME_BACKGROUND, this.mProgressTintInfo.mProgressBackgroundTintList.getDefaultColor());
                    return;
                }
                return;
            }
            Drawable target = getTintTarget(16908288, false);
            if (target != null) {
                if (this.mProgressTintInfo.mHasProgressBackgroundTint) {
                    target.setTintList(this.mProgressTintInfo.mProgressBackgroundTintList);
                }
                if (this.mProgressTintInfo.mHasProgressBackgroundTintMode) {
                    target.setTintBlendMode(this.mProgressTintInfo.mProgressBackgroundBlendMode);
                }
                if (target.isStateful()) {
                    target.setState(getDrawableState());
                }
            }
        }
    }

    private void applySecondaryProgressTint() {
        Drawable target;
        if ((this.mProgressTintInfo.mHasSecondaryProgressTint || this.mProgressTintInfo.mHasSecondaryProgressTintMode) && (target = getTintTarget(16908303, false)) != null) {
            if (this.mProgressTintInfo.mHasSecondaryProgressTint) {
                target.setTintList(this.mProgressTintInfo.mSecondaryProgressTintList);
            }
            if (this.mProgressTintInfo.mHasSecondaryProgressTintMode) {
                target.setTintBlendMode(this.mProgressTintInfo.mSecondaryProgressBlendMode);
            }
            if (target.isStateful()) {
                target.setState(getDrawableState());
            }
        }
    }

    @RemotableViewMethod
    public void setProgressTintList(ColorStateList tint) {
        if (this.mProgressTintInfo == null) {
            this.mProgressTintInfo = new ProgressTintInfo();
        }
        this.mProgressTintInfo.mProgressTintList = tint;
        this.mProgressTintInfo.mHasProgressTint = true;
        if (this.mProgressDrawable != null) {
            applyPrimaryProgressTint();
        }
    }

    public ColorStateList getProgressTintList() {
        if (this.mProgressTintInfo != null) {
            return this.mProgressTintInfo.mProgressTintList;
        }
        return null;
    }

    protected ColorStateList semGetProgressTintList() {
        if (this.mProgressTintInfo != null) {
            return this.mProgressTintInfo.mProgressTintList;
        }
        return null;
    }

    public void setProgressTintMode(PorterDuff.Mode tintMode) {
        setProgressTintBlendMode(tintMode != null ? BlendMode.fromValue(tintMode.nativeInt) : null);
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
        BlendMode mode = getProgressTintBlendMode();
        if (mode != null) {
            return BlendMode.blendModeToPorterDuffMode(mode);
        }
        return null;
    }

    public BlendMode getProgressTintBlendMode() {
        if (this.mProgressTintInfo != null) {
            return this.mProgressTintInfo.mProgressBlendMode;
        }
        return null;
    }

    @RemotableViewMethod
    public void setProgressBackgroundTintList(ColorStateList tint) {
        if (this.mProgressTintInfo == null) {
            this.mProgressTintInfo = new ProgressTintInfo();
        }
        this.mProgressTintInfo.mProgressBackgroundTintList = tint;
        this.mProgressTintInfo.mHasProgressBackgroundTint = true;
        if (this.mProgressDrawable != null) {
            applyProgressBackgroundTint();
        }
    }

    public ColorStateList getProgressBackgroundTintList() {
        if (this.mProgressTintInfo != null) {
            return this.mProgressTintInfo.mProgressBackgroundTintList;
        }
        return null;
    }

    protected ColorStateList semGetProgressBackgroundTintList() {
        if (this.mProgressTintInfo != null) {
            return this.mProgressTintInfo.mProgressBackgroundTintList;
        }
        return null;
    }

    public void setProgressBackgroundTintMode(PorterDuff.Mode tintMode) {
        setProgressBackgroundTintBlendMode(tintMode != null ? BlendMode.fromValue(tintMode.nativeInt) : null);
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
        BlendMode mode = getProgressBackgroundTintBlendMode();
        if (mode != null) {
            return BlendMode.blendModeToPorterDuffMode(mode);
        }
        return null;
    }

    public BlendMode getProgressBackgroundTintBlendMode() {
        if (this.mProgressTintInfo != null) {
            return this.mProgressTintInfo.mProgressBackgroundBlendMode;
        }
        return null;
    }

    @RemotableViewMethod
    public void setSecondaryProgressTintList(ColorStateList tint) {
        if (this.mProgressTintInfo == null) {
            this.mProgressTintInfo = new ProgressTintInfo();
        }
        this.mProgressTintInfo.mSecondaryProgressTintList = tint;
        this.mProgressTintInfo.mHasSecondaryProgressTint = true;
        if (this.mProgressDrawable != null) {
            applySecondaryProgressTint();
        }
    }

    public ColorStateList getSecondaryProgressTintList() {
        if (this.mProgressTintInfo != null) {
            return this.mProgressTintInfo.mSecondaryProgressTintList;
        }
        return null;
    }

    public void setSecondaryProgressTintMode(PorterDuff.Mode tintMode) {
        setSecondaryProgressTintBlendMode(tintMode != null ? BlendMode.fromValue(tintMode.nativeInt) : null);
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
        BlendMode mode = getSecondaryProgressTintBlendMode();
        if (mode != null) {
            return BlendMode.blendModeToPorterDuffMode(mode);
        }
        return null;
    }

    public BlendMode getSecondaryProgressTintBlendMode() {
        if (this.mProgressTintInfo != null) {
            return this.mProgressTintInfo.mSecondaryProgressBlendMode;
        }
        return null;
    }

    private Drawable getTintTarget(int layerId, boolean shouldFallback) {
        Drawable layer = null;
        Drawable d = this.mProgressDrawable;
        if (d == null) {
            return null;
        }
        this.mProgressDrawable = d.mutate();
        if (d instanceof LayerDrawable) {
            layer = ((LayerDrawable) d).findDrawableByLayerId(layerId);
        }
        if (shouldFallback && layer == null) {
            return d;
        }
        return layer;
    }

    public void setProgressDrawableTiled(Drawable d) {
        if (d != null) {
            d = tileify(d, false);
        }
        setProgressDrawable(d);
    }

    public Drawable getCurrentDrawable() {
        return this.mCurrentDrawable;
    }

    @Override // android.view.View
    protected boolean verifyDrawable(Drawable who) {
        return who == this.mProgressDrawable || who == this.mIndeterminateDrawable || super.verifyDrawable(who);
    }

    @Override // android.view.View
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        if (this.mProgressDrawable != null) {
            this.mProgressDrawable.jumpToCurrentState();
        }
        if (this.mIndeterminateDrawable != null) {
            this.mIndeterminateDrawable.jumpToCurrentState();
        }
    }

    @Override // android.view.View
    public void onResolveDrawables(int layoutDirection) {
        Drawable d = this.mCurrentDrawable;
        if (d != null) {
            d.setLayoutDirection(layoutDirection);
        }
        if (this.mIndeterminateDrawable != null) {
            this.mIndeterminateDrawable.setLayoutDirection(layoutDirection);
        }
        if (this.mProgressDrawable != null) {
            this.mProgressDrawable.setLayoutDirection(layoutDirection);
        }
    }

    @Override // android.view.View
    public void postInvalidate() {
        if (!this.mNoInvalidate) {
            super.postInvalidate();
        }
    }

    private class RefreshProgressRunnable implements Runnable {
        private RefreshProgressRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            synchronized (ProgressBar.this) {
                int count = ProgressBar.this.mRefreshData.size();
                for (int i = 0; i < count; i++) {
                    RefreshData rd = (RefreshData) ProgressBar.this.mRefreshData.get(i);
                    ProgressBar.this.doRefreshProgress(rd.id, rd.progress, rd.fromUser, true, rd.animate);
                    rd.recycle();
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

        public static RefreshData obtain(int id, int progress, boolean fromUser, boolean animate) {
            RefreshData rd = sPool.acquire();
            if (rd == null) {
                rd = new RefreshData();
            }
            rd.id = id;
            rd.progress = progress;
            rd.fromUser = fromUser;
            rd.animate = animate;
            return rd;
        }

        public void recycle() {
            sPool.release(this);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void doRefreshProgress(int id, int progress, boolean fromUser, boolean callBackToApp, boolean animate) {
        int range = this.mMax - this.mMin;
        float scale = range > 0 ? (progress - this.mMin) / range : 0.0f;
        boolean isPrimary = id == 16908301;
        int level = (int) (10000.0f * scale);
        Drawable d = this.mCurrentDrawable;
        if (d != null) {
            if (d instanceof LayerDrawable) {
                Drawable progressDrawable = ((LayerDrawable) d).findDrawableByLayerId(id);
                if (progressDrawable != null && canResolveLayoutDirection()) {
                    progressDrawable.setLayoutDirection(getLayoutDirection());
                }
                (progressDrawable != null ? progressDrawable : d).setLevel(level);
            } else if (d instanceof StateListDrawable) {
                int numStates = ((StateListDrawable) d).getStateCount();
                for (int i = 0; i < numStates; i++) {
                    Drawable mStateDrawable = ((StateListDrawable) d).getStateDrawable(i);
                    Drawable progressDrawable2 = null;
                    if ((mStateDrawable instanceof LayerDrawable) && (progressDrawable2 = ((LayerDrawable) mStateDrawable).findDrawableByLayerId(id)) != null && canResolveLayoutDirection()) {
                        progressDrawable2.setLayoutDirection(getLayoutDirection());
                    }
                    (progressDrawable2 != null ? progressDrawable2 : d).setLevel(level);
                }
            } else {
                d.setLevel(level);
            }
        } else {
            invalidate();
        }
        if (isPrimary && animate) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(this, this.VISUAL_PROGRESS, scale);
            animator.setAutoCancel(true);
            animator.setDuration(80L);
            animator.setInterpolator(PROGRESS_ANIM_INTERPOLATOR);
            animator.addListener(new AnimatorListenerAdapter() { // from class: android.widget.ProgressBar.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animation) {
                    ProgressBar.this.mLastProgressAnimator = null;
                }
            });
            animator.start();
            this.mLastProgressAnimator = animator;
        } else {
            if (isPrimary && this.mLastProgressAnimator != null) {
                this.mLastProgressAnimator.cancel();
                this.mLastProgressAnimator = null;
            }
            setVisualProgress(id, scale);
        }
        if (isPrimary && callBackToApp) {
            onProgressRefresh(scale, fromUser, progress);
        }
    }

    private float getPercent(int progress) {
        float maxProgress = getMax();
        float minProgress = getMin();
        float currentProgress = progress;
        float diffProgress = maxProgress - minProgress;
        if (diffProgress <= 0.0f) {
            return 0.0f;
        }
        float percent = (currentProgress - minProgress) / diffProgress;
        return Math.max(0.0f, Math.min(1.0f, percent));
    }

    private CharSequence formatStateDescription(int progress) {
        Locale curLocale = this.mContext.getResources().getConfiguration().getLocales().get(0);
        if (!curLocale.equals(this.mCachedLocale)) {
            this.mCachedLocale = curLocale;
            this.mPercentFormat = NumberFormat.getPercentInstance(curLocale);
        }
        return this.mPercentFormat.format(getPercent(progress));
    }

    @Override // android.view.View
    @RemotableViewMethod
    public void setStateDescription(CharSequence stateDescription) {
        super.setStateDescription(stateDescription);
    }

    void onProgressRefresh(float scale, boolean fromUser, int progress) {
        if (AccessibilityManager.getInstance(this.mContext).isEnabled() && getStateDescription() == null && !isIndeterminate()) {
            AccessibilityEvent event = AccessibilityEvent.obtain();
            event.setEventType(2048);
            event.setContentChangeTypes(64);
            sendAccessibilityEventUnchecked(event);
        }
        if (this.mSecondaryProgress > this.mProgress && !fromUser) {
            refreshProgress(16908303, this.mSecondaryProgress, false, false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setVisualProgress(int id, float progress) {
        this.mVisualProgress = progress;
        Drawable d = this.mCurrentDrawable;
        if ((d instanceof LayerDrawable) && (d = ((LayerDrawable) d).findDrawableByLayerId(id)) == null) {
            d = this.mCurrentDrawable;
        }
        if (d != null) {
            int level = (int) (10000.0f * progress);
            d.setLevel(level);
        } else {
            invalidate();
        }
        onVisualProgressChanged(id, progress);
    }

    void onVisualProgressChanged(int id, float progress) {
    }

    private synchronized void refreshProgress(int id, int progress, boolean fromUser, boolean animate) {
        if (this.mUiThreadId == Thread.currentThread().getId()) {
            doRefreshProgress(id, progress, fromUser, true, animate);
        } else {
            if (this.mRefreshProgressRunnable == null) {
                this.mRefreshProgressRunnable = new RefreshProgressRunnable();
            }
            RefreshData rd = RefreshData.obtain(id, progress, fromUser, animate);
            this.mRefreshData.add(rd);
            if (this.mAttached && !this.mRefreshIsPosted) {
                post(this.mRefreshProgressRunnable);
                this.mRefreshIsPosted = true;
            }
        }
    }

    @RemotableViewMethod
    public synchronized void setProgress(int progress) {
        setProgressInternal(progress, false, false);
    }

    public void setProgress(int progress, boolean animate) {
        setProgressInternal(progress, false, animate);
    }

    @RemotableViewMethod
    synchronized boolean setProgressInternal(int progress, boolean fromUser, boolean animate) {
        Drawable drawable;
        if (this.mIndeterminate) {
            return false;
        }
        int progress2 = MathUtils.constrain(progress, this.mSemMinEnabled ? this.mSemMin : this.mMin, this.mMax);
        if (progress2 == this.mProgress) {
            return false;
        }
        this.mProgress = progress2;
        if (this.mCurrentMode == 6 && (getProgressDrawable() instanceof LayerDrawable) && (drawable = ((LayerDrawable) getProgressDrawable()).findDrawableByLayerId(16908301)) != null && (drawable instanceof CirCleProgressDrawable)) {
            ((CirCleProgressDrawable) drawable).setProgress(progress2, animate);
        }
        refreshProgress(16908301, this.mProgress, fromUser, animate);
        return true;
    }

    @RemotableViewMethod
    public synchronized void setSecondaryProgress(int secondaryProgress) {
        if (this.mIndeterminate) {
            return;
        }
        if (secondaryProgress < this.mMin) {
            secondaryProgress = this.mMin;
        }
        if (secondaryProgress > this.mMax) {
            secondaryProgress = this.mMax;
        }
        if (secondaryProgress != this.mSecondaryProgress) {
            this.mSecondaryProgress = secondaryProgress;
            refreshProgress(16908303, this.mSecondaryProgress, false, false);
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
    public synchronized void setMin(int min) {
        if (this.mMaxInitialized && min > this.mMax) {
            min = this.mMax;
        }
        this.mMinInitialized = true;
        if (this.mMaxInitialized && min != this.mMin) {
            this.mMin = min;
            postInvalidate();
            if (this.mProgress < min) {
                this.mProgress = min;
            }
            refreshProgress(16908301, this.mProgress, false, false);
        } else {
            this.mMin = min;
        }
    }

    @RemotableViewMethod
    public synchronized void setMax(int max) {
        if (this.mMinInitialized && max < this.mMin) {
            max = this.mMin;
        }
        this.mMaxInitialized = true;
        if (this.mMinInitialized && max != this.mMax) {
            this.mMax = max;
            postInvalidate();
            if (this.mProgress > max) {
                this.mProgress = max;
            }
            refreshProgress(16908301, this.mProgress, false, false);
        } else {
            this.mMax = max;
        }
    }

    public final synchronized void incrementProgressBy(int diff) {
        setProgress(this.mProgress + diff);
    }

    public final synchronized void incrementSecondaryProgressBy(int diff) {
        setSecondaryProgress(this.mSecondaryProgress + diff);
    }

    void startAnimation() {
        if (getVisibility() != 0 || getWindowVisibility() != 0) {
            return;
        }
        if (this.mIndeterminateDrawable instanceof Animatable) {
            this.mShouldStartAnimationDrawable = true;
            this.mHasAnimation = false;
            if (this.mIndeterminateDrawable instanceof AnimatedVectorDrawable) {
                ((AnimatedVectorDrawable) this.mIndeterminateDrawable).registerAnimationCallback(this.mCircleAnimationCallback);
            }
        } else {
            this.mHasAnimation = true;
            if (this.mInterpolator == null) {
                this.mInterpolator = new LinearInterpolator();
            }
            if (this.mTransformation == null) {
                this.mTransformation = new Transformation();
            } else {
                this.mTransformation.clear();
            }
            if (this.mAnimation == null) {
                this.mAnimation = new AlphaAnimation(0.0f, 1.0f);
            } else {
                this.mAnimation.reset();
            }
            this.mAnimation.setRepeatMode(this.mBehavior);
            this.mAnimation.setRepeatCount(-1);
            this.mAnimation.setDuration(this.mDuration);
            this.mAnimation.setInterpolator(this.mInterpolator);
            this.mAnimation.setStartTime(-1L);
        }
        postInvalidate();
    }

    void stopAnimation() {
        this.mHasAnimation = false;
        if (this.mIndeterminateDrawable instanceof Animatable) {
            ((Animatable) this.mIndeterminateDrawable).stop();
            if (this.mIndeterminateDrawable instanceof AnimatedVectorDrawable) {
                ((AnimatedVectorDrawable) this.mIndeterminateDrawable).unregisterAnimationCallback(this.mCircleAnimationCallback);
            }
            this.mShouldStartAnimationDrawable = false;
        }
        postInvalidate();
    }

    public void setInterpolator(Context context, int resID) {
        setInterpolator(AnimationUtils.loadInterpolator(context, resID));
    }

    public void setInterpolator(Interpolator interpolator) {
        this.mInterpolator = interpolator;
    }

    public Interpolator getInterpolator() {
        return this.mInterpolator;
    }

    @Override // android.view.View
    public void onVisibilityAggregated(boolean isVisible) {
        super.onVisibilityAggregated(isVisible);
        if (isVisible != this.mAggregatedIsVisible) {
            this.mAggregatedIsVisible = isVisible;
            if (this.mIndeterminate) {
                if (isVisible) {
                    startAnimation();
                } else {
                    stopAnimation();
                }
            }
            if (this.mCurrentDrawable != null) {
                this.mCurrentDrawable.setVisible(isVisible, false);
            }
        }
    }

    @Override // android.view.View, android.graphics.drawable.Drawable.Callback
    public void invalidateDrawable(Drawable dr) {
        if (!this.mInDrawing) {
            if (verifyDrawable(dr)) {
                Rect dirty = dr.getBounds();
                int scrollX = this.mScrollX + this.mPaddingLeft;
                int scrollY = this.mScrollY + this.mPaddingTop;
                invalidate(dirty.left + scrollX, dirty.top + scrollY, dirty.right + scrollX, dirty.bottom + scrollY);
                return;
            }
            super.invalidateDrawable(dr);
        }
    }

    @Override // android.view.View
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        updateDrawableBounds(w, h);
    }

    protected void updateDrawableBounds(int w, int h) {
        int w2 = w - (this.mPaddingRight + this.mPaddingLeft);
        int h2 = h - (this.mPaddingTop + this.mPaddingBottom);
        int right = w2;
        int bottom = h2;
        int top = 0;
        int left = 0;
        if (this.mIndeterminateDrawable != null) {
            if (this.mOnlyIndeterminate && !(this.mIndeterminateDrawable instanceof AnimationDrawable)) {
                int intrinsicWidth = this.mIndeterminateDrawable.getIntrinsicWidth();
                int intrinsicHeight = this.mIndeterminateDrawable.getIntrinsicHeight();
                float intrinsicAspect = intrinsicWidth / intrinsicHeight;
                float boundAspect = w2 / h2;
                if (intrinsicAspect != boundAspect) {
                    if (boundAspect > intrinsicAspect) {
                        int width = (int) (h2 * intrinsicAspect);
                        left = (w2 - width) / 2;
                        right = left + width;
                    } else {
                        int height = (int) (w2 * (1.0f / intrinsicAspect));
                        int top2 = (h2 - height) / 2;
                        bottom = top2 + height;
                        top = top2;
                    }
                }
            }
            if (this.mMirrorForRtl && isLayoutRtl()) {
                int tempLeft = left;
                left = w2 - right;
                right = w2 - tempLeft;
            }
            this.mIndeterminateDrawable.setBounds(left, top, right, bottom);
        }
        if (this.mProgressDrawable != null) {
            this.mProgressDrawable.setBounds(0, 0, right, bottom);
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
            int saveCount = canvas.save();
            if (this.mCurrentMode != 3 && this.mMirrorForRtl && isLayoutRtl()) {
                canvas.translate(getWidth() - this.mPaddingRight, this.mPaddingTop);
                canvas.scale(-1.0f, 1.0f);
            } else {
                canvas.translate(this.mPaddingLeft, this.mPaddingTop);
            }
            long time = getDrawingTime();
            if (this.mHasAnimation) {
                this.mAnimation.getTransformation(time, this.mTransformation);
                float scale = this.mTransformation.getAlpha();
                try {
                    this.mInDrawing = true;
                    drawable.setLevel((int) (10000.0f * scale));
                    this.mInDrawing = false;
                    postInvalidateOnAnimation();
                } catch (Throwable th) {
                    this.mInDrawing = false;
                    throw th;
                }
            }
            drawable.draw(canvas);
            canvas.restoreToCount(saveCount);
            if (this.mShouldStartAnimationDrawable && (drawable instanceof Animatable)) {
                ((Animatable) drawable).start();
                this.mShouldStartAnimationDrawable = false;
            }
        }
    }

    @Override // android.view.View
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int dw = 0;
        int dh = 0;
        Drawable d = this.mCurrentDrawable;
        if (d != null) {
            dw = Math.max(this.mMinWidth, Math.min(this.mMaxWidth, d.getIntrinsicWidth()));
            dh = Math.max(this.mMinHeight, Math.min(this.mMaxHeight, d.getIntrinsicHeight()));
        }
        updateDrawableState();
        int dw2 = dw + this.mPaddingLeft + this.mPaddingRight;
        int dh2 = dh + this.mPaddingTop + this.mPaddingBottom;
        int measuredWidth = resolveSizeAndState(dw2, widthMeasureSpec, 0);
        int measuredHeight = resolveSizeAndState(dh2, heightMeasureSpec, 0);
        initCirCleStrokeWidth((measuredWidth - this.mPaddingLeft) - this.mPaddingRight);
        if (this.mUseHorizontalProgress && this.mIndeterminate) {
            semSetIndeterminateProgressDrawable((measuredWidth - this.mPaddingLeft) - this.mPaddingRight);
        }
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    @Override // android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        updateDrawableState();
    }

    private void updateDrawableState() {
        int[] state = getDrawableState();
        boolean changed = false;
        Drawable progressDrawable = this.mProgressDrawable;
        if (progressDrawable != null && progressDrawable.isStateful()) {
            changed = false | progressDrawable.setState(state);
        }
        Drawable indeterminateDrawable = this.mIndeterminateDrawable;
        if (indeterminateDrawable != null && indeterminateDrawable.isStateful()) {
            changed |= indeterminateDrawable.setState(state);
        }
        if (changed) {
            invalidate();
        }
    }

    @Override // android.view.View
    public void drawableHotspotChanged(float x, float y) {
        super.drawableHotspotChanged(x, y);
        if (this.mProgressDrawable != null) {
            this.mProgressDrawable.setHotspot(x, y);
        }
        if (this.mIndeterminateDrawable != null) {
            this.mIndeterminateDrawable.setHotspot(x, y);
        }
    }

    static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: android.widget.ProgressBar.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        int progress;
        int secondaryProgress;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.progress = in.readInt();
            this.secondaryProgress = in.readInt();
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(this.progress);
            out.writeInt(this.secondaryProgress);
        }
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.progress = this.mProgress;
        ss.secondaryProgress = this.mSecondaryProgress;
        return ss;
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        setProgress(ss.progress);
        setSecondaryProgress(ss.secondaryProgress);
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mIndeterminate) {
            startAnimation();
        }
        if (this.mRefreshData != null) {
            synchronized (this) {
                int count = this.mRefreshData.size();
                for (int i = 0; i < count; i++) {
                    RefreshData rd = this.mRefreshData.get(i);
                    doRefreshProgress(rd.id, rd.progress, rd.fromUser, true, rd.animate);
                    rd.recycle();
                }
                this.mRefreshData.clear();
            }
        }
        this.mAttached = true;
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        if (this.mIndeterminate) {
            stopAnimation();
        } else {
            this.mCircleAnimationCallback = null;
        }
        if (this.mRefreshProgressRunnable != null) {
            removeCallbacks(this.mRefreshProgressRunnable);
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
    public void onInitializeAccessibilityEventInternal(AccessibilityEvent event) {
        super.onInitializeAccessibilityEventInternal(event);
        event.setItemCount(this.mMax - this.mMin);
        event.setCurrentItemIndex(this.mProgress);
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfoInternal(info);
        if (!isIndeterminate()) {
            AccessibilityNodeInfo.RangeInfo rangeInfo = AccessibilityNodeInfo.RangeInfo.obtain(0, getMin(), getMax(), getProgress());
            info.setRangeInfo(rangeInfo);
        }
        if (getStateDescription() == null) {
            if (isIndeterminate()) {
                info.setStateDescription(getResources().getString(R.string.in_progress));
            } else {
                info.setStateDescription(formatStateDescription(this.mProgress));
            }
        }
    }

    @Override // android.view.View
    protected void encodeProperties(ViewHierarchyEncoder stream) {
        super.encodeProperties(stream);
        stream.addProperty("progress:max", getMax());
        stream.addProperty("progress:progress", getProgress());
        stream.addProperty("progress:secondaryProgress", getSecondaryProgress());
        stream.addProperty("progress:indeterminate", isIndeterminate());
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

    public void semSetMin(int min) {
        this.mMin = 0;
        this.mSemMin = min;
        this.mSemMinEnabled = true;
    }

    @Deprecated
    public void semSetMode(int mode) {
        this.mCurrentMode = mode;
        switch (mode) {
            case 3:
                Drawable progressDrawable = this.mContext.getDrawable(R.drawable.tw_scrubber_progress_vertical_material);
                if (progressDrawable != null) {
                    setProgressDrawableTiled(progressDrawable);
                    break;
                }
                break;
            case 4:
                Drawable progressDrawable2 = this.mContext.getDrawable(R.drawable.tw_split_seekbar_background_progress_material);
                if (progressDrawable2 != null) {
                    setProgressDrawableTiled(progressDrawable2);
                    break;
                }
                break;
            case 6:
                initializeRoundCicleMode();
                break;
        }
    }

    protected void onSlidingRefresh(int level) {
        Drawable d = this.mCurrentDrawable;
        if (this.mCurrentDrawable != null) {
            Drawable progressDrawable = null;
            if (d instanceof LayerDrawable) {
                progressDrawable = ((LayerDrawable) d).findDrawableByLayerId(16908301);
            }
            if (progressDrawable != null) {
                progressDrawable.setLevel(level);
            }
        }
    }

    private void initCirCleStrokeWidth(int width) {
        if (this.mContext.getResources().getDimensionPixelSize(R.dimen.sem_progress_bar_size_small) == width) {
            this.mRoundStrokeWidth = this.mContext.getResources().getDimensionPixelSize(R.dimen.sem_progress_circle_size_small_width);
            return;
        }
        if (this.mContext.getResources().getDimensionPixelSize(R.dimen.sem_progress_bar_size_small_title) == width) {
            this.mRoundStrokeWidth = this.mContext.getResources().getDimensionPixelSize(R.dimen.sem_progress_circle_size_small_title_width);
        } else if (this.mContext.getResources().getDimensionPixelSize(R.dimen.sem_progress_bar_size_large) == width) {
            this.mRoundStrokeWidth = this.mContext.getResources().getDimensionPixelSize(R.dimen.sem_progress_circle_size_large_width);
        } else {
            this.mRoundStrokeWidth = this.mContext.getResources().getDimensionPixelSize(R.dimen.sem_progress_circle_size_normal_width);
        }
    }

    private void semSetIndeterminateProgressDrawable(int width) {
        if (getResources().getDimensionPixelSize(R.dimen.sem_progress_bar_indeterminate_xsmall) >= width) {
            setIndeterminateDrawable(this.mIndeterminateHorizontalXsmall);
            return;
        }
        if (getResources().getDimensionPixelSize(R.dimen.sem_progress_bar_indeterminate_small) >= width) {
            setIndeterminateDrawable(this.mIndeterminateHorizontalSmall);
            return;
        }
        if (getResources().getDimensionPixelSize(R.dimen.sem_progress_bar_indeterminate_medium) >= width) {
            setIndeterminateDrawable(this.mIndeterminateHorizontalMedium);
        } else if (getResources().getDimensionPixelSize(R.dimen.sem_progress_bar_indeterminate_large) >= width) {
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
        CirCleProgressDrawable primaryProgress = new CirCleProgressDrawable(false, colorToColorStateList(this.mContext.getResources().getColor(R.color.tw_progress_color_control_activated_light)));
        CirCleProgressDrawable background = new CirCleProgressDrawable(true, colorToColorStateList(this.mContext.getResources().getColor(R.color.tw_progress_color_control_bg_light)));
        Drawable[] drawables = {background, primaryProgress};
        LayerDrawable layer = new LayerDrawable(drawables);
        layer.setPaddingMode(1);
        layer.setId(0, 16908288);
        layer.setId(1, 16908301);
        setProgressDrawable(layer);
    }

    private ColorStateList colorToColorStateList(int color) {
        int[][] EMPTY = {new int[0]};
        return new ColorStateList(EMPTY, new int[]{color});
    }

    private class CirCleProgressDrawable extends Drawable {
        int mColor;
        ColorStateList mColorStateList;
        private boolean mIsBackground;
        public int mProgress;
        private final Paint mPaint = new Paint();
        int mAlpha = 255;
        private RectF mArcRect = new RectF();
        private final ProgressState mState = new ProgressState();
        private final IntProperty<CirCleProgressDrawable> VISUAL_CIRCLE_PROGRESS = new IntProperty<CirCleProgressDrawable>("visual_progress") { // from class: android.widget.ProgressBar.CirCleProgressDrawable.1
            @Override // android.util.IntProperty
            public void setValue(CirCleProgressDrawable object, int value) {
                object.mProgress = value;
                CirCleProgressDrawable.this.invalidateSelf();
            }

            @Override // android.util.Property
            public Integer get(CirCleProgressDrawable object) {
                return Integer.valueOf(object.mProgress);
            }
        };

        public CirCleProgressDrawable(boolean isBackground, ColorStateList color) {
            this.mIsBackground = isBackground;
            this.mPaint.setStyle(Paint.Style.STROKE);
            this.mPaint.setStrokeCap(Paint.Cap.ROUND);
            this.mColorStateList = color;
            this.mColor = color.getDefaultColor();
            this.mPaint.setColor(this.mColor);
            this.mProgress = 0;
        }

        @Override // android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            this.mPaint.setStrokeWidth(ProgressBar.this.mRoundStrokeWidth);
            int prevAlpha = this.mPaint.getAlpha();
            this.mPaint.setAlpha(modulateAlpha(prevAlpha, this.mAlpha));
            this.mArcRect.set(ProgressBar.this.mRoundStrokeWidth / 2.0f, ProgressBar.this.mRoundStrokeWidth / 2.0f, ProgressBar.this.getWidth() - (ProgressBar.this.mRoundStrokeWidth / 2.0f), ProgressBar.this.getWidth() - (ProgressBar.this.mRoundStrokeWidth / 2.0f));
            int range = ProgressBar.this.mMax - ProgressBar.this.mMin;
            float scale = range > 0 ? (this.mProgress - ProgressBar.this.mMin) / range : 0.0f;
            canvas.save();
            if (this.mIsBackground) {
                canvas.drawArc(this.mArcRect, 270.0f, 360.0f, false, this.mPaint);
            } else {
                canvas.drawArc(this.mArcRect, 270.0f, scale * 360.0f, false, this.mPaint);
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

        public void setProgress(int progress, boolean animate) {
            if (animate) {
                ObjectAnimator animator = ObjectAnimator.ofInt(this, this.VISUAL_CIRCLE_PROGRESS, progress);
                animator.setAutoCancel(true);
                animator.setDuration(80L);
                animator.setInterpolator(ProgressBar.PROGRESS_ANIM_INTERPOLATOR);
                animator.start();
                return;
            }
            this.mProgress = progress;
            ProgressBar.this.invalidate();
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
                this.mColor = this.mColorStateList.getDefaultColor();
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
            return changed;
        }

        @Override // android.graphics.drawable.Drawable
        public Drawable.ConstantState getConstantState() {
            return this.mState;
        }

        private class ProgressState extends Drawable.ConstantState {
            private ProgressState() {
            }

            @Override // android.graphics.drawable.Drawable.ConstantState
            public Drawable newDrawable() {
                return CirCleProgressDrawable.this;
            }

            @Override // android.graphics.drawable.Drawable.ConstantState
            public int getChangingConfigurations() {
                return 0;
            }
        }
    }

    @RemotableViewMethod
    public void hidden_semSetInterpolator(int resId) {
        if (this.mContext != null) {
            setInterpolator(this.mContext, resId);
        }
    }
}
