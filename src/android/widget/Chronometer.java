package android.widget;

import android.animation.ObjectAnimator;
import android.appwidget.AppWidgetHostView;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.icu.text.MeasureFormat;
import android.icu.util.Measure;
import android.icu.util.MeasureUnit;
import android.net.Uri;
import android.os.SystemClock;
import android.provider.Telephony;
import android.util.AttributeSet;
import android.util.IntProperty;
import android.util.MathUtils;
import android.util.Pools;
import android.view.RemotableViewMethod;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.LinearInterpolator;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.RemoteViews;
import com.android.internal.R;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Locale;

@RemoteViews.RemoteView
/* loaded from: classes5.dex */
public class Chronometer extends TextView {
    private static final int HOUR_IN_SEC = 3600;
    private static final int MAX_LEVEL = 10000;
    private static final int MIN_IN_SEC = 60;
    private static final LinearInterpolator PROGRESS_ANIM_INTERPOLATOR = new LinearInterpolator();
    private static final int SEM_MODE_CIRCLE_PROGRESS = 1;
    private static final int SEM_MODE_DEFAULT = 0;
    private static final int SEM_MODE_DEFAULT_KEEP_UPDATE = 2;
    private static final int SEM_MODE_HORIZONTAL_PROGRESS = 4;
    private static final String TAG = "Chronometer";
    private boolean mAttached;
    private long mBase;
    private long mBaseTimerSeconds;
    private int mCirclePadding;
    private boolean mCountDown;
    private Drawable mCurrentDrawable;
    private long mFirstTimerSeconds;
    private String mFormat;
    private StringBuilder mFormatBuilder;
    private Formatter mFormatter;
    private Object[] mFormatterArgs;
    private Locale mFormatterLocale;
    private boolean mIsFixedHourFormat;
    private boolean mIsShowingSeconds;
    private boolean mLogged;
    private int mMaxHeight;
    private int mMaxProgress;
    private int mMaxWidth;
    private int mMilliSecondCount;
    private int mMinHeight;
    private int mMinProgress;
    private int mMinWidth;
    private int mMode;
    private long mNow;
    private OnChronometerTickListener mOnChronometerTickListener;
    private long mOriginalBase;
    private float mPlaySpeed;
    private int mProgress;
    private int mProgressAnimationDuration;
    private int mProgressBackgroundColor;
    private int mProgressColor;
    private Drawable mProgressDrawable;
    private int mProgressWarningColor;
    private StringBuilder mRecycle;
    private final ArrayList<RefreshData> mRefreshData;
    private boolean mRefreshIsPosted;
    private RefreshProgressRunnable mRefreshProgressRunnable;
    private int mRequestedTickTime;
    private int mRoundStrokeWidth;
    private boolean mRunning;
    private boolean mShouldStartAnimationDrawable;
    private boolean mStarted;
    private long mStoppedTime;
    private final Runnable mTickRunnable;
    private boolean mUseStoppedTimeText;
    private boolean mVisible;
    private long mWaringTime;

    public interface OnChronometerTickListener {
        void onChronometerTick(Chronometer chronometer);
    }

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<Chronometer> {
        private int mCountDownId;
        private int mFormatId;
        private boolean mPropertiesMapped = false;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(PropertyMapper propertyMapper) {
            this.mCountDownId = propertyMapper.mapBoolean("countDown", 16844059);
            this.mFormatId = propertyMapper.mapObject(Telephony.CellBroadcasts.MESSAGE_FORMAT, 16843013);
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(Chronometer chronometer, PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readBoolean(this.mCountDownId, chronometer.isCountDown());
            propertyReader.readObject(this.mFormatId, chronometer.getFormat());
        }
    }

    public Chronometer(Context context) {
        this(context, null, 0);
    }

    public Chronometer(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public Chronometer(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public Chronometer(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mProgressAnimationDuration = 1000;
        this.mRefreshData = new ArrayList<>();
        this.mMode = 0;
        this.mMaxProgress = 10000;
        this.mMinProgress = 0;
        this.mProgressBackgroundColor = -7566196;
        this.mProgressColor = -8026114;
        this.mProgressWarningColor = -561304;
        this.mWaringTime = 6000L;
        this.mIsFixedHourFormat = false;
        this.mIsShowingSeconds = true;
        this.mPlaySpeed = 1.0f;
        this.mFirstTimerSeconds = 0L;
        this.mOriginalBase = 0L;
        this.mRequestedTickTime = 1000;
        this.mMilliSecondCount = 0;
        this.mFormatterArgs = new Object[1];
        this.mRecycle = new StringBuilder(8);
        this.mTickRunnable = new Runnable() { // from class: android.widget.Chronometer.1
            @Override // java.lang.Runnable
            public void run() {
                int i3;
                if (Chronometer.this.mRequestedTickTime != 1000) {
                    i3 = Chronometer.this.mRequestedTickTime;
                } else {
                    i3 = Chronometer.this.mPlaySpeed != 1.0f ? (int) (1000.0f / Chronometer.this.mPlaySpeed) : 1000;
                }
                if (Chronometer.this.mRunning) {
                    Chronometer.this.updateText(SystemClock.elapsedRealtime());
                    Chronometer.this.dispatchChronometerTick();
                    if (i3 == 1000) {
                        Chronometer.this.postTickOnNextSecond();
                    } else {
                        Chronometer chronometer = Chronometer.this;
                        chronometer.postDelayed(chronometer.mTickRunnable, i3);
                    }
                }
            }
        };
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.Chronometer, i, i2);
        saveAttributeDataForStyleable(context, R.styleable.Chronometer, attributeSet, obtainStyledAttributes, i, i2);
        setFormat(obtainStyledAttributes.getString(0));
        setCountDown(obtainStyledAttributes.getBoolean(1, false));
        obtainStyledAttributes.recycle();
        init();
    }

    private void init() {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        this.mBase = elapsedRealtime;
        updateText(elapsedRealtime);
    }

    @RemotableViewMethod
    public void setCountDown(boolean z) {
        this.mCountDown = z;
        updateText(SystemClock.elapsedRealtime());
    }

    public boolean isCountDown() {
        return this.mCountDown;
    }

    public boolean isTheFinalCountDown() {
        try {
            getContext().startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://youtu.be/9jK-NcRmVcw")).addCategory(Intent.CATEGORY_BROWSABLE).addFlags(528384));
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    @RemotableViewMethod
    public void setBase(long j) {
        int i = this.mMode;
        if (i == 1 || i == 4) {
            long elapsedRealtime = j - SystemClock.elapsedRealtime();
            this.mBaseTimerSeconds = elapsedRealtime;
            long j2 = this.mFirstTimerSeconds;
            if (elapsedRealtime > j2) {
                this.mBaseTimerSeconds = j2;
            }
            if (j2 != 0 || this.mOriginalBase != 0) {
                long j3 = this.mBaseTimerSeconds;
                if (j2 != j3 && j2 != 0) {
                    int i2 = (int) ((j3 / j2) * 10000.0f);
                    this.mProgress = i2;
                    setProgressInternal(i2, false);
                }
            }
            setProgressInternal(this.mMaxProgress, false);
        }
        this.mBase = j;
        dispatchChronometerTick();
        updateText(SystemClock.elapsedRealtime());
    }

    public long getBase() {
        return this.mBase;
    }

    @RemotableViewMethod
    public void setFormat(String str) {
        this.mFormat = str;
        if (str == null || this.mFormatBuilder != null) {
            return;
        }
        this.mFormatBuilder = new StringBuilder(str.length() * 2);
    }

    public String getFormat() {
        return this.mFormat;
    }

    public void setOnChronometerTickListener(OnChronometerTickListener onChronometerTickListener) {
        this.mOnChronometerTickListener = onChronometerTickListener;
    }

    public OnChronometerTickListener getOnChronometerTickListener() {
        return this.mOnChronometerTickListener;
    }

    public void start() {
        this.mProgressAnimationDuration = (int) this.mBaseTimerSeconds;
        setProgressInternal(0, true);
        this.mStarted = true;
        updateRunning();
    }

    public void stop() {
        this.mStarted = false;
        if (this.mMode == 4) {
            setProgressInternal(this.mMaxProgress, false);
        }
        updateRunning();
    }

    @RemotableViewMethod
    public void setStarted(boolean z) {
        this.mProgressAnimationDuration = Math.max((int) this.mBaseTimerSeconds, 0);
        if (z) {
            int i = this.mMode;
            if (i == 1 || i == 4) {
                long j = this.mFirstTimerSeconds;
                if (j != 0 || this.mOriginalBase != 0) {
                    long j2 = this.mBaseTimerSeconds;
                    if (j != j2 && j != 0) {
                        int i2 = (int) ((j2 / j) * 10000.0f);
                        this.mProgress = i2;
                        setProgressInternal(i2, false);
                    }
                }
                setProgressInternal(this.mMaxProgress, false);
            }
            setProgressInternal(0, true);
        }
        this.mStarted = z;
        updateRunning();
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mVisible = false;
        updateRunning();
        RefreshProgressRunnable refreshProgressRunnable = this.mRefreshProgressRunnable;
        if (refreshProgressRunnable != null) {
            removeCallbacks(refreshProgressRunnable);
            this.mRefreshIsPosted = false;
        }
        super.onDetachedFromWindow();
        this.mAttached = false;
    }

    @Override // android.view.View
    protected void onWindowVisibilityChanged(int i) {
        super.onWindowVisibilityChanged(i);
        this.mVisible = i == 0;
        updateRunning();
    }

    @Override // android.widget.TextView, android.view.View
    protected void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        updateRunning();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0082 A[Catch: all -> 0x010e, TryCatch #1 {, blocks: (B:4:0x0003, B:6:0x0009, B:7:0x0018, B:9:0x0020, B:10:0x0023, B:12:0x002d, B:15:0x0032, B:18:0x003b, B:20:0x0043, B:21:0x005c, B:23:0x0060, B:25:0x0068, B:26:0x007e, B:28:0x0082, B:30:0x008a, B:32:0x009d, B:34:0x00a6, B:36:0x00b4, B:38:0x00b8, B:39:0x0092, B:40:0x00cd, B:45:0x00d5, B:46:0x00de, B:53:0x00e5, B:55:0x00eb, B:60:0x00f8, B:62:0x00fb, B:67:0x0105, B:69:0x0109, B:71:0x00db, B:73:0x0053, B:74:0x000c, B:76:0x0010, B:77:0x0015), top: B:3:0x0003, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00e2  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00eb A[Catch: all -> 0x010e, TryCatch #1 {, blocks: (B:4:0x0003, B:6:0x0009, B:7:0x0018, B:9:0x0020, B:10:0x0023, B:12:0x002d, B:15:0x0032, B:18:0x003b, B:20:0x0043, B:21:0x005c, B:23:0x0060, B:25:0x0068, B:26:0x007e, B:28:0x0082, B:30:0x008a, B:32:0x009d, B:34:0x00a6, B:36:0x00b4, B:38:0x00b8, B:39:0x0092, B:40:0x00cd, B:45:0x00d5, B:46:0x00de, B:53:0x00e5, B:55:0x00eb, B:60:0x00f8, B:62:0x00fb, B:67:0x0105, B:69:0x0109, B:71:0x00db, B:73:0x0053, B:74:0x000c, B:76:0x0010, B:77:0x0015), top: B:3:0x0003, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x00ff  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0109 A[Catch: all -> 0x010e, TRY_LEAVE, TryCatch #1 {, blocks: (B:4:0x0003, B:6:0x0009, B:7:0x0018, B:9:0x0020, B:10:0x0023, B:12:0x002d, B:15:0x0032, B:18:0x003b, B:20:0x0043, B:21:0x005c, B:23:0x0060, B:25:0x0068, B:26:0x007e, B:28:0x0082, B:30:0x008a, B:32:0x009d, B:34:0x00a6, B:36:0x00b4, B:38:0x00b8, B:39:0x0092, B:40:0x00cd, B:45:0x00d5, B:46:0x00de, B:53:0x00e5, B:55:0x00eb, B:60:0x00f8, B:62:0x00fb, B:67:0x0105, B:69:0x0109, B:71:0x00db, B:73:0x0053, B:74:0x000c, B:76:0x0010, B:77:0x0015), top: B:3:0x0003, inners: #0 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized void updateText(long r13) {
        /*
            Method dump skipped, instructions count: 273
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.Chronometer.updateText(long):void");
    }

    private ChronometerProgressDrawable getChronometerProgressDrawable() {
        Drawable drawable = this.mProgressDrawable;
        if (!(drawable instanceof LayerDrawable)) {
            return null;
        }
        Drawable findDrawableByLayerId = ((LayerDrawable) drawable).findDrawableByLayerId(16908301);
        if (findDrawableByLayerId instanceof ChronometerProgressDrawable) {
            return (ChronometerProgressDrawable) findDrawableByLayerId;
        }
        if (findDrawableByLayerId instanceof ClipDrawable) {
            return (ChronometerProgressDrawable) ((ClipDrawable) findDrawableByLayerId).getDrawable();
        }
        return null;
    }

    private ChronometerProgressDrawable getChronometerBackgroundDrawable() {
        Drawable drawable = this.mProgressDrawable;
        if (!(drawable instanceof LayerDrawable)) {
            return null;
        }
        Drawable findDrawableByLayerId = ((LayerDrawable) drawable).findDrawableByLayerId(16908288);
        if (findDrawableByLayerId instanceof ChronometerProgressDrawable) {
            return (ChronometerProgressDrawable) findDrawableByLayerId;
        }
        if (findDrawableByLayerId instanceof ClipDrawable) {
            return (ChronometerProgressDrawable) ((ClipDrawable) findDrawableByLayerId).getDrawable();
        }
        return null;
    }

    private void updateRunning() {
        boolean z = true;
        if (this.mMode != 0 ? !this.mVisible || !this.mStarted || !isShownForSemMode() : !this.mVisible || !this.mStarted || !isShown()) {
            z = false;
        }
        int i = this.mRequestedTickTime;
        if (i == 1000) {
            float f = this.mPlaySpeed;
            i = f != 1.0f ? (int) (1000.0f / f) : 1000;
        }
        if (z != this.mRunning) {
            if (z) {
                updateText(SystemClock.elapsedRealtime());
                dispatchChronometerTick();
                if (i == 1000) {
                    postTickOnNextSecond();
                } else {
                    postDelayed(this.mTickRunnable, i);
                }
            } else {
                removeCallbacks(this.mTickRunnable);
            }
            this.mRunning = z;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0, types: [android.widget.Chronometer] */
    /* JADX WARN: Type inference failed for: r3v1, types: [android.view.View] */
    /* JADX WARN: Type inference failed for: r3v5, types: [android.view.View] */
    private boolean isShownForSemMode() {
        while (this.getVisibility() == 0) {
            Object parent = this.getParent();
            if (parent == null) {
                return this instanceof AppWidgetHostView;
            }
            if (!(parent instanceof View)) {
                return true;
            }
            this = (View) parent;
            if (this == 0) {
                return false;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postTickOnNextSecond() {
        long abs;
        long j = this.mNow;
        if (this.mCountDown) {
            abs = (this.mBase - j) % 1000;
            if (abs <= 0) {
                abs += 1000;
            }
        } else {
            abs = 1000 - (Math.abs(j - this.mBase) % 1000);
        }
        postDelayed(this.mTickRunnable, abs + 1);
    }

    void dispatchChronometerTick() {
        OnChronometerTickListener onChronometerTickListener = this.mOnChronometerTickListener;
        if (onChronometerTickListener != null) {
            onChronometerTickListener.onChronometerTick(this);
        }
    }

    private static String formatDuration(long j) {
        int i;
        int i2 = (int) (j / 1000);
        if (i2 < 0) {
            i2 = -i2;
        }
        int i3 = 0;
        if (i2 >= 3600) {
            i = i2 / 3600;
            i2 -= i * 3600;
        } else {
            i = 0;
        }
        if (i2 >= 60) {
            i3 = i2 / 60;
            i2 -= i3 * 60;
        }
        ArrayList arrayList = new ArrayList();
        if (i > 0) {
            arrayList.add(new Measure(Integer.valueOf(i), MeasureUnit.HOUR));
        }
        if (i3 > 0) {
            arrayList.add(new Measure(Integer.valueOf(i3), MeasureUnit.MINUTE));
        }
        arrayList.add(new Measure(Integer.valueOf(i2), MeasureUnit.SECOND));
        return MeasureFormat.getInstance(Locale.getDefault(), MeasureFormat.FormatWidth.WIDE).formatMeasures((Measure[]) arrayList.toArray(new Measure[arrayList.size()]));
    }

    @Override // android.view.View
    public CharSequence getContentDescription() {
        if (this.mUseStoppedTimeText) {
            return formatDuration(this.mStoppedTime);
        }
        return formatDuration(this.mNow - this.mBase);
    }

    @Override // android.widget.TextView, android.view.View
    public CharSequence getAccessibilityClassName() {
        return Chronometer.class.getName();
    }

    @Override // android.widget.TextView, android.view.View
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int i = this.mMode;
        if (i != 0 && i != 2) {
            drawTrack(canvas);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    void drawTrack(Canvas canvas) {
        Drawable drawable = this.mCurrentDrawable;
        if (drawable != 0) {
            int save = canvas.save();
            canvas.translate(this.mPaddingLeft, this.mPaddingTop);
            drawable.draw(canvas);
            canvas.restoreToCount(save);
            if (this.mShouldStartAnimationDrawable && (drawable instanceof Animatable)) {
                ((Animatable) drawable).start();
                this.mShouldStartAnimationDrawable = false;
            }
        }
    }

    private synchronized void setProgressInternal(int i, boolean z) {
        int constrain = MathUtils.constrain(i, this.mMinProgress, this.mMaxProgress);
        this.mProgress = constrain;
        Drawable drawable = this.mProgressDrawable;
        if (drawable instanceof LayerDrawable) {
            Drawable findDrawableByLayerId = ((LayerDrawable) drawable).findDrawableByLayerId(16908301);
            if (findDrawableByLayerId instanceof ClipDrawable) {
                findDrawableByLayerId = ((ClipDrawable) findDrawableByLayerId).getDrawable();
            }
            if (findDrawableByLayerId instanceof ChronometerProgressDrawable) {
                ((ChronometerProgressDrawable) findDrawableByLayerId).setProgress(constrain, z);
            }
        }
        refreshProgress(16908301, this.mProgress, z);
    }

    private synchronized void refreshProgress(int i, int i2, boolean z) {
        if (this.mRefreshProgressRunnable == null) {
            this.mRefreshProgressRunnable = new RefreshProgressRunnable();
        }
        this.mRefreshData.add(RefreshData.obtain(i, i2, z));
        if (this.mAttached && !this.mRefreshIsPosted) {
            post(this.mRefreshProgressRunnable);
            this.mRefreshIsPosted = true;
        }
    }

    private void initializeHorizontalProgressMode() {
        HorizontalProgressDrawable horizontalProgressDrawable = new HorizontalProgressDrawable(false, colorToColorStateList(this.mProgressColor), colorToColorStateList(this.mProgressWarningColor));
        HorizontalProgressDrawable horizontalProgressDrawable2 = new HorizontalProgressDrawable(true, colorToColorStateList(this.mProgressBackgroundColor), null);
        r2[0].setLevel(10000);
        Drawable[] drawableArr = {new ClipDrawable(horizontalProgressDrawable2, 21, 1), new ClipDrawable(horizontalProgressDrawable, 21, 1)};
        drawableArr[1].setLevel(0);
        LayerDrawable layerDrawable = new LayerDrawable(drawableArr);
        layerDrawable.setPaddingMode(1);
        layerDrawable.setId(0, 16908288);
        layerDrawable.setId(1, 16908301);
        setProgressDrawable(layerDrawable);
        this.mProgress = 0;
        this.mMinWidth = -1;
        this.mMaxWidth = -1;
        this.mMinHeight = 34;
        this.mMaxHeight = -1;
        this.mRoundStrokeWidth = 34;
    }

    private void initializeRoundCicleMode() {
        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{new CirCleProgressDrawable(true, colorToColorStateList(this.mProgressBackgroundColor), null), new CirCleProgressDrawable(false, colorToColorStateList(this.mProgressColor), colorToColorStateList(this.mProgressWarningColor))});
        layerDrawable.setPaddingMode(1);
        layerDrawable.setId(0, 16908288);
        layerDrawable.setId(1, 16908301);
        setProgressDrawable(layerDrawable);
        this.mProgress = 0;
        this.mMinWidth = 180;
        this.mMaxWidth = 180;
        this.mMinHeight = 180;
        this.mMaxHeight = 180;
    }

    private ColorStateList colorToColorStateList(int i) {
        return new ColorStateList(new int[][]{new int[0]}, new int[]{i});
    }

    private void setProgressDrawable(Drawable drawable) {
        Drawable drawable2 = this.mProgressDrawable;
        if (drawable2 != drawable) {
            if (drawable2 != null) {
                drawable2.setCallback(null);
                unscheduleDrawable(this.mProgressDrawable);
            }
            this.mProgressDrawable = drawable;
            drawable.setCallback(this);
            drawable.setLayoutDirection(getLayoutDirection());
            if (drawable.isStateful()) {
                drawable.setState(getDrawableState());
            }
            int minimumHeight = drawable.getMinimumHeight();
            if (this.mMaxHeight < minimumHeight) {
                this.mMaxHeight = minimumHeight;
                requestLayout();
            }
            swapCurrentDrawable(drawable);
            postInvalidate();
            updateDrawableBounds(getWidth(), getHeight());
            updateDrawableState();
            doRefreshProgress(16908301, this.mProgress, false, false);
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

    private void updateDrawableBounds(int i, int i2) {
        int i3 = i - (this.mPaddingRight + this.mPaddingLeft);
        int i4 = i2 - (this.mPaddingTop + this.mPaddingBottom);
        Drawable drawable = this.mProgressDrawable;
        if (drawable != null) {
            drawable.setBounds(0, 0, i3, i4);
        }
    }

    private void updateDrawableState() {
        int[] drawableState = getDrawableState();
        Drawable drawable = this.mProgressDrawable;
        if ((drawable == null || !drawable.isStateful()) ? false : drawable.setState(drawableState)) {
            invalidate();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void doRefreshProgress(int i, int i2, boolean z, boolean z2) {
        int i3 = this.mMaxProgress - this.mMinProgress;
        float f = i3 > 0 ? (i2 - r0) / i3 : 0.0f;
        Drawable drawable = this.mCurrentDrawable;
        if (drawable != null) {
            int i4 = (int) (10000.0f * f);
            if (drawable instanceof LayerDrawable) {
                Drawable findDrawableByLayerId = ((LayerDrawable) drawable).findDrawableByLayerId(i);
                if (findDrawableByLayerId != null && canResolveLayoutDirection()) {
                    findDrawableByLayerId.setLayoutDirection(getLayoutDirection());
                }
                if (findDrawableByLayerId != null) {
                    drawable = findDrawableByLayerId;
                }
                drawable.setLevel(i4);
            } else {
                drawable.setLevel(i4);
            }
        } else {
            invalidate();
        }
        if (z) {
            onProgressRefresh(f, i2);
        }
    }

    void onProgressRefresh(float f, int i) {
        if (getStateDescription() == null) {
            AccessibilityEvent obtain = AccessibilityEvent.obtain();
            obtain.setEventType(2048);
            obtain.setContentChangeTypes(64);
            sendAccessibilityEventUnchecked(obtain);
        }
    }

    @Override // android.widget.TextView, android.view.View, android.view.accessibility.AccessibilityEventSource
    public void sendAccessibilityEventUnchecked(AccessibilityEvent accessibilityEvent) {
        if (accessibilityEvent.getEventType() == 2048 && accessibilityEvent.getContentChangeTypes() == 2) {
            return;
        }
        super.sendAccessibilityEventUnchecked(accessibilityEvent);
    }

    @Override // android.widget.TextView, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mRefreshData != null) {
            synchronized (this) {
                int size = this.mRefreshData.size();
                for (int i = 0; i < size; i++) {
                    RefreshData refreshData = this.mRefreshData.get(i);
                    doRefreshProgress(refreshData.id, refreshData.progress, true, refreshData.animate);
                    refreshData.recycle();
                }
                this.mRefreshData.clear();
            }
        }
        this.mAttached = true;
    }

    @Override // android.widget.TextView, android.view.View
    protected synchronized void onMeasure(int i, int i2) {
        int i3;
        int i4;
        int i5 = this.mMode;
        if (i5 != 0 && i5 != 2) {
            Drawable drawable = this.mCurrentDrawable;
            if (drawable != null) {
                i4 = Math.max(this.mMinWidth, Math.min(this.mMaxWidth, drawable.getIntrinsicWidth()));
                i3 = Math.max(this.mMinHeight, Math.min(this.mMaxHeight, drawable.getIntrinsicHeight()));
            } else {
                i3 = 0;
                i4 = 0;
            }
            updateDrawableState();
            setMeasuredDimension(resolveSizeAndState(i4 + this.mPaddingLeft + this.mPaddingRight, i, 0), resolveSizeAndState(i3 + this.mPaddingTop + this.mPaddingBottom, i2, 0));
            updateDrawableBounds(getMeasuredWidth(), getMeasuredHeight());
        }
        super.onMeasure(i, i2);
    }

    private void initCirCleStrokeWidth() {
        this.mRoundStrokeWidth = 32;
        this.mCirclePadding = 0;
    }

    private static class RefreshData {
        private static final int POOL_MAX = 24;
        private static final Pools.SynchronizedPool<RefreshData> sPool = new Pools.SynchronizedPool<>(24);
        public boolean animate;
        public int id;
        public int progress;

        private RefreshData() {
        }

        public static RefreshData obtain(int i, int i2, boolean z) {
            RefreshData acquire = sPool.acquire();
            if (acquire == null) {
                acquire = new RefreshData();
            }
            acquire.id = i;
            acquire.progress = i2;
            acquire.animate = z;
            return acquire;
        }

        public void recycle() {
            sPool.release(this);
        }
    }

    private class RefreshProgressRunnable implements Runnable {
        private RefreshProgressRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            synchronized (Chronometer.this) {
                int size = Chronometer.this.mRefreshData.size();
                for (int i = 0; i < size; i++) {
                    RefreshData refreshData = (RefreshData) Chronometer.this.mRefreshData.get(i);
                    Chronometer.this.doRefreshProgress(refreshData.id, refreshData.progress, true, refreshData.animate);
                    refreshData.recycle();
                }
                Chronometer.this.mRefreshData.clear();
                Chronometer.this.mRefreshIsPosted = false;
            }
        }
    }

    private abstract class ChronometerProgressDrawable extends Drawable {
        private final IntProperty<ChronometerProgressDrawable> VISUAL_CIRCLE_PROGRESS;
        int mAlpha;
        ObjectAnimator mAnimator;
        int mColor;
        ColorStateList mColorStateList;
        boolean mIsBackground;
        boolean mIsWarningMode;
        final Paint mPaint;
        public int mProgress;
        final ProgressState mState;
        ColorStateList mWarningColorStateList;

        @Override // android.graphics.drawable.Drawable
        public boolean isStateful() {
            return true;
        }

        int modulateAlpha(int i, int i2) {
            return (i * (i2 + (i2 >>> 7))) >>> 8;
        }

        public ChronometerProgressDrawable(boolean z, ColorStateList colorStateList, ColorStateList colorStateList2) {
            Paint paint = new Paint();
            this.mPaint = paint;
            this.mAlpha = 255;
            this.mState = new ProgressState();
            this.VISUAL_CIRCLE_PROGRESS = new IntProperty<ChronometerProgressDrawable>("visual_progress") { // from class: android.widget.Chronometer.ChronometerProgressDrawable.1
                @Override // android.util.IntProperty
                public void setValue(ChronometerProgressDrawable chronometerProgressDrawable, int i) {
                    if (Chronometer.this.getVisibility() == 8) {
                        ChronometerProgressDrawable.this.mAnimator.cancel();
                        i = Chronometer.this.mMaxProgress;
                    }
                    chronometerProgressDrawable.mProgress = i;
                    Drawable findDrawableByLayerId = ((LayerDrawable) Chronometer.this.mProgressDrawable).findDrawableByLayerId(16908301);
                    if (findDrawableByLayerId instanceof ClipDrawable) {
                        findDrawableByLayerId.setLevel(i);
                    }
                    ChronometerProgressDrawable.this.invalidateSelf();
                }

                @Override // android.util.Property
                public Integer get(ChronometerProgressDrawable chronometerProgressDrawable) {
                    return Integer.valueOf(chronometerProgressDrawable.mProgress);
                }
            };
            this.mIsBackground = z;
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setAntiAlias(true);
            this.mColorStateList = colorStateList;
            this.mWarningColorStateList = colorStateList2;
            int defaultColor = colorStateList.getDefaultColor();
            this.mColor = defaultColor;
            paint.setColor(defaultColor);
            this.mProgress = 0;
        }

        public void updateColor(ColorStateList colorStateList, ColorStateList colorStateList2) {
            this.mColorStateList = colorStateList;
            this.mWarningColorStateList = colorStateList2;
            int defaultColor = colorStateList.getDefaultColor();
            this.mColor = defaultColor;
            this.mPaint.setColor(defaultColor);
        }

        void cancelAnimator() {
            ObjectAnimator objectAnimator = this.mAnimator;
            if (objectAnimator != null) {
                objectAnimator.cancel();
            }
        }

        void setWarningMode(boolean z) {
            ColorStateList colorStateList = this.mWarningColorStateList;
            if (colorStateList != null) {
                this.mIsWarningMode = z;
                if (z) {
                    setTintList(colorStateList);
                } else {
                    setTintList(this.mColorStateList);
                }
            }
        }

        void setWarningColor(ColorStateList colorStateList) {
            this.mWarningColorStateList = colorStateList;
        }

        void setProgress(int i, boolean z) {
            if (z) {
                ObjectAnimator ofInt = ObjectAnimator.ofInt(this, this.VISUAL_CIRCLE_PROGRESS, i);
                this.mAnimator = ofInt;
                ofInt.overrideDurationScale(1.0f);
                this.mAnimator.setAutoCancel(true);
                this.mAnimator.setDuration(Chronometer.this.mProgressAnimationDuration);
                this.mAnimator.setInterpolator(Chronometer.PROGRESS_ANIM_INTERPOLATOR);
                this.mAnimator.start();
                return;
            }
            ObjectAnimator objectAnimator = this.mAnimator;
            if (objectAnimator != null && objectAnimator.isRunning()) {
                this.mAnimator.setFloatValues(this.mProgress);
                this.mAnimator.end();
                this.mAnimator.cancel();
                Drawable findDrawableByLayerId = ((LayerDrawable) Chronometer.this.mProgressDrawable).findDrawableByLayerId(16908301);
                if (findDrawableByLayerId instanceof ClipDrawable) {
                    findDrawableByLayerId.setLevel(i);
                }
                invalidateSelf();
            } else {
                this.mProgress = i;
            }
            Chronometer.this.invalidate();
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
                int defaultColor = colorStateList.getDefaultColor();
                this.mColor = defaultColor;
                this.mPaint.setColor(defaultColor);
                invalidateSelf();
            }
        }

        @Override // android.graphics.drawable.Drawable
        protected boolean onStateChange(int[] iArr) {
            int colorForState;
            boolean onStateChange = super.onStateChange(iArr);
            if (this.mIsWarningMode) {
                colorForState = this.mWarningColorStateList.getColorForState(iArr, this.mColor);
            } else {
                colorForState = this.mColorStateList.getColorForState(iArr, this.mColor);
            }
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
                return ChronometerProgressDrawable.this;
            }
        }
    }

    private class CirCleProgressDrawable extends ChronometerProgressDrawable {
        private RectF mArcRect;

        public CirCleProgressDrawable(boolean z, ColorStateList colorStateList, ColorStateList colorStateList2) {
            super(z, colorStateList, colorStateList2);
            this.mArcRect = new RectF();
        }

        @Override // android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            this.mPaint.setStrokeWidth(Chronometer.this.mRoundStrokeWidth);
            int alpha = this.mPaint.getAlpha();
            this.mPaint.setAlpha(modulateAlpha(alpha, this.mAlpha));
            this.mArcRect.set((Chronometer.this.mRoundStrokeWidth / 2.0f) + Chronometer.this.mCirclePadding, (Chronometer.this.mRoundStrokeWidth / 2.0f) + Chronometer.this.mCirclePadding, (Chronometer.this.getWidth() - (Chronometer.this.mRoundStrokeWidth / 2.0f)) - Chronometer.this.mCirclePadding, (Chronometer.this.getWidth() - (Chronometer.this.mRoundStrokeWidth / 2.0f)) - Chronometer.this.mCirclePadding);
            int i = Chronometer.this.mMaxProgress - Chronometer.this.mMinProgress;
            float f = i > 0 ? (this.mProgress - Chronometer.this.mMinProgress) / i : 0.0f;
            canvas.save();
            if (this.mIsBackground) {
                canvas.drawArc(this.mArcRect, 270.0f, 360.0f, false, this.mPaint);
            } else {
                canvas.drawArc(this.mArcRect, 270.0f, f * 360.0f, false, this.mPaint);
            }
            canvas.restore();
            this.mPaint.setAlpha(alpha);
        }
    }

    private class HorizontalProgressDrawable extends ChronometerProgressDrawable {
        public HorizontalProgressDrawable(boolean z, ColorStateList colorStateList, ColorStateList colorStateList2) {
            super(z, colorStateList, colorStateList2);
        }

        @Override // android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            this.mPaint.setStrokeWidth(Chronometer.this.mRoundStrokeWidth);
            int alpha = this.mPaint.getAlpha();
            this.mPaint.setAlpha(modulateAlpha(alpha, this.mAlpha));
            canvas.save();
            float f = Chronometer.this.mRoundStrokeWidth / 2.0f;
            canvas.drawLine(f, f, Chronometer.this.getWidth() - f, f, this.mPaint);
            canvas.restore();
            this.mPaint.setAlpha(alpha);
        }
    }

    @Override // android.widget.TextView, android.view.View, android.graphics.drawable.Drawable.Callback
    public void invalidateDrawable(Drawable drawable) {
        if (verifyDrawable(drawable)) {
            Rect bounds = drawable.getBounds();
            int scrollX = getScrollX() + getPaddingLeft();
            int scrollY = getScrollY() + getPaddingTop();
            invalidate(bounds.left + scrollX, bounds.top + scrollY, bounds.right + scrollX, bounds.bottom + scrollY);
            return;
        }
        super.invalidateDrawable(drawable);
    }

    @Override // android.widget.TextView, android.view.View
    protected boolean verifyDrawable(Drawable drawable) {
        return drawable == this.mProgressDrawable || super.verifyDrawable(drawable);
    }

    @RemotableViewMethod
    public void hidden_semSetMode(int i) {
        if (this.mMode != i) {
            this.mMode = i;
            if (i == 1) {
                initializeRoundCicleMode();
                initCirCleStrokeWidth();
            } else if (i == 4) {
                initializeHorizontalProgressMode();
            }
            invalidate();
        }
    }

    @RemotableViewMethod
    public void hidden_semSetOriginalBase(long j) {
        this.mOriginalBase = j;
        this.mFirstTimerSeconds = j - SystemClock.elapsedRealtime();
    }

    @RemotableViewMethod
    public void hidden_semSetMaxProgressValue(long j) {
        this.mFirstTimerSeconds = j;
    }

    @RemotableViewMethod
    public void hidden_semSetProgressBackgroundColor(int i) {
        this.mProgressBackgroundColor = i;
        ChronometerProgressDrawable chronometerBackgroundDrawable = getChronometerBackgroundDrawable();
        if (chronometerBackgroundDrawable != null) {
            chronometerBackgroundDrawable.updateColor(colorToColorStateList(i), null);
        }
    }

    @RemotableViewMethod
    public void hidden_semSetProgressColor(int i) {
        this.mProgressColor = i;
        ChronometerProgressDrawable chronometerProgressDrawable = getChronometerProgressDrawable();
        if (chronometerProgressDrawable != null) {
            chronometerProgressDrawable.updateColor(colorToColorStateList(this.mProgressColor), colorToColorStateList(this.mProgressWarningColor));
        }
    }

    @RemotableViewMethod
    public void hidden_semSetProgressWidth(int i) {
        this.mRoundStrokeWidth = i;
    }

    @RemotableViewMethod
    public void hidden_semSetWarningColor(int i) {
        this.mProgressWarningColor = i;
        ChronometerProgressDrawable chronometerProgressDrawable = getChronometerProgressDrawable();
        if (chronometerProgressDrawable != null) {
            chronometerProgressDrawable.setWarningColor(colorToColorStateList(i));
        }
    }

    @RemotableViewMethod
    public void hidden_semSetWarningTime(long j) {
        this.mWaringTime = j;
    }

    @RemotableViewMethod
    public void hidden_semInvokeChronometer(String str) {
        if (str.equals("stop")) {
            stop();
        }
    }

    @RemotableViewMethod
    public void hidden_semSetFixedHourFormat(boolean z) {
        this.mIsFixedHourFormat = z;
    }

    @RemotableViewMethod
    public void hidden_semSetTimeSpeed(float f) {
        if (this.mPlaySpeed == f || f <= 0.0f) {
            return;
        }
        this.mPlaySpeed = f;
    }

    @RemotableViewMethod
    public void hidden_semSetForceTickTime(int i) {
        this.mRequestedTickTime = i;
    }

    @RemotableViewMethod
    public void hidden_semSetMilliSecondCount(int i) {
        this.mMilliSecondCount = Math.min(Math.max(i, 0), 3);
    }

    @RemotableViewMethod
    public void hidden_semSetShowingSeconds(boolean z) {
        this.mIsShowingSeconds = z;
    }

    @RemotableViewMethod
    public void hidden_semSetStoppedTime(long j) {
        this.mStoppedTime = j;
    }

    @RemotableViewMethod
    public void hidden_semSetUseStoppedTimeText(boolean z) {
        this.mUseStoppedTimeText = z;
        if (!z) {
            stop();
        }
        updateText(this.mBase);
    }
}
