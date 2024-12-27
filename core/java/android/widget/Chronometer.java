package android.widget;

import android.animation.ObjectAnimator;
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
import android.widget.RemoteViewsAdapter;
import com.android.internal.R;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Locale;

@RemoteViews.RemoteView
/* loaded from: classes4.dex */
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
    private final Runnable mTickRunnable;
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
        public void readProperties(Chronometer node, PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readBoolean(this.mCountDownId, node.isCountDown());
            propertyReader.readObject(this.mFormatId, node.getFormat());
        }
    }

    public Chronometer(Context context) {
        this(context, null, 0);
    }

    public Chronometer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Chronometer(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public Chronometer(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
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
                int tickTime;
                if (Chronometer.this.mRequestedTickTime != 1000) {
                    tickTime = Chronometer.this.mRequestedTickTime;
                } else if (Chronometer.this.mPlaySpeed != 1.0f) {
                    tickTime = (int) (1000.0f / Chronometer.this.mPlaySpeed);
                } else {
                    tickTime = 1000;
                }
                if (Chronometer.this.mRunning) {
                    Chronometer.this.updateText(SystemClock.elapsedRealtime());
                    Chronometer.this.dispatchChronometerTick();
                    if (tickTime != 1000) {
                        Chronometer.this.postDelayed(Chronometer.this.mTickRunnable, tickTime);
                    } else {
                        Chronometer.this.postTickOnNextSecond();
                    }
                }
            }
        };
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Chronometer, defStyleAttr, defStyleRes);
        saveAttributeDataForStyleable(context, R.styleable.Chronometer, attrs, a, defStyleAttr, defStyleRes);
        setFormat(a.getString(0));
        setCountDown(a.getBoolean(1, false));
        a.recycle();
        init();
    }

    private void init() {
        this.mBase = SystemClock.elapsedRealtime();
        updateText(this.mBase);
    }

    @RemotableViewMethod
    public void setCountDown(boolean countDown) {
        this.mCountDown = countDown;
        updateText(SystemClock.elapsedRealtime());
    }

    public boolean isCountDown() {
        return this.mCountDown;
    }

    public boolean isTheFinalCountDown() {
        try {
            getContext().startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://youtu.be/9jK-NcRmVcw")).addCategory(Intent.CATEGORY_BROWSABLE).addFlags(528384));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @RemotableViewMethod
    public void setBase(long base) {
        if (this.mMode == 1 || this.mMode == 4) {
            this.mBaseTimerSeconds = base - SystemClock.elapsedRealtime();
            if (this.mBaseTimerSeconds > this.mFirstTimerSeconds) {
                this.mBaseTimerSeconds = this.mFirstTimerSeconds;
            }
            if ((this.mFirstTimerSeconds == 0 && this.mOriginalBase == 0) || this.mFirstTimerSeconds == this.mBaseTimerSeconds || this.mFirstTimerSeconds == 0) {
                setProgressInternal(this.mMaxProgress, false);
            } else {
                float scale = this.mBaseTimerSeconds / this.mFirstTimerSeconds;
                int i = this.mProgress;
                this.mProgress = (int) (10000.0f * scale);
                setProgressInternal(this.mProgress, false);
            }
        }
        this.mBase = base;
        dispatchChronometerTick();
        updateText(SystemClock.elapsedRealtime());
    }

    public long getBase() {
        return this.mBase;
    }

    @RemotableViewMethod
    public void setFormat(String format) {
        this.mFormat = format;
        if (format != null && this.mFormatBuilder == null) {
            this.mFormatBuilder = new StringBuilder(format.length() * 2);
        }
    }

    public String getFormat() {
        return this.mFormat;
    }

    public void setOnChronometerTickListener(OnChronometerTickListener listener) {
        this.mOnChronometerTickListener = listener;
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
    public void setStarted(boolean started) {
        this.mProgressAnimationDuration = Math.max((int) this.mBaseTimerSeconds, 0);
        if (started) {
            if (this.mMode == 1 || this.mMode == 4) {
                if ((this.mFirstTimerSeconds == 0 && this.mOriginalBase == 0) || this.mFirstTimerSeconds == this.mBaseTimerSeconds || this.mFirstTimerSeconds == 0) {
                    setProgressInternal(this.mMaxProgress, false);
                } else {
                    float scale = this.mBaseTimerSeconds / this.mFirstTimerSeconds;
                    int i = this.mProgress;
                    this.mProgress = (int) (10000.0f * scale);
                    setProgressInternal(this.mProgress, false);
                }
            }
            setProgressInternal(0, true);
        }
        this.mStarted = started;
        updateRunning();
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mVisible = false;
        updateRunning();
        if (this.mRefreshProgressRunnable != null) {
            removeCallbacks(this.mRefreshProgressRunnable);
            this.mRefreshIsPosted = false;
        }
        super.onDetachedFromWindow();
        this.mAttached = false;
    }

    @Override // android.view.View
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        this.mVisible = visibility == 0;
        updateRunning();
    }

    @Override // android.widget.TextView, android.view.View
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        updateRunning();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:28:0x009d A[Catch: all -> 0x013e, TryCatch #1 {, blocks: (B:4:0x0005, B:6:0x000b, B:7:0x0013, B:9:0x001b, B:10:0x0020, B:12:0x0029, B:15:0x002e, B:18:0x0038, B:20:0x0040, B:21:0x006e, B:23:0x0073, B:25:0x007b, B:26:0x0098, B:28:0x009d, B:30:0x00a6, B:32:0x00b9, B:34:0x00c2, B:38:0x00d4, B:40:0x00d8, B:41:0x00ae, B:42:0x00f4, B:44:0x00f8, B:47:0x00fe, B:48:0x0107, B:50:0x010b, B:55:0x0110, B:57:0x0116, B:62:0x0125, B:64:0x0128, B:69:0x0134, B:71:0x0139, B:73:0x0104, B:76:0x005e, B:77:0x000f), top: B:3:0x0005, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x010b A[Catch: all -> 0x013e, TryCatch #1 {, blocks: (B:4:0x0005, B:6:0x000b, B:7:0x0013, B:9:0x001b, B:10:0x0020, B:12:0x0029, B:15:0x002e, B:18:0x0038, B:20:0x0040, B:21:0x006e, B:23:0x0073, B:25:0x007b, B:26:0x0098, B:28:0x009d, B:30:0x00a6, B:32:0x00b9, B:34:0x00c2, B:38:0x00d4, B:40:0x00d8, B:41:0x00ae, B:42:0x00f4, B:44:0x00f8, B:47:0x00fe, B:48:0x0107, B:50:0x010b, B:55:0x0110, B:57:0x0116, B:62:0x0125, B:64:0x0128, B:69:0x0134, B:71:0x0139, B:73:0x0104, B:76:0x005e, B:77:0x000f), top: B:3:0x0005, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0116 A[Catch: all -> 0x013e, TryCatch #1 {, blocks: (B:4:0x0005, B:6:0x000b, B:7:0x0013, B:9:0x001b, B:10:0x0020, B:12:0x0029, B:15:0x002e, B:18:0x0038, B:20:0x0040, B:21:0x006e, B:23:0x0073, B:25:0x007b, B:26:0x0098, B:28:0x009d, B:30:0x00a6, B:32:0x00b9, B:34:0x00c2, B:38:0x00d4, B:40:0x00d8, B:41:0x00ae, B:42:0x00f4, B:44:0x00f8, B:47:0x00fe, B:48:0x0107, B:50:0x010b, B:55:0x0110, B:57:0x0116, B:62:0x0125, B:64:0x0128, B:69:0x0134, B:71:0x0139, B:73:0x0104, B:76:0x005e, B:77:0x000f), top: B:3:0x0005, inners: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x012c  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0139 A[Catch: all -> 0x013e, TRY_LEAVE, TryCatch #1 {, blocks: (B:4:0x0005, B:6:0x000b, B:7:0x0013, B:9:0x001b, B:10:0x0020, B:12:0x0029, B:15:0x002e, B:18:0x0038, B:20:0x0040, B:21:0x006e, B:23:0x0073, B:25:0x007b, B:26:0x0098, B:28:0x009d, B:30:0x00a6, B:32:0x00b9, B:34:0x00c2, B:38:0x00d4, B:40:0x00d8, B:41:0x00ae, B:42:0x00f4, B:44:0x00f8, B:47:0x00fe, B:48:0x0107, B:50:0x010b, B:55:0x0110, B:57:0x0116, B:62:0x0125, B:64:0x0128, B:69:0x0134, B:71:0x0139, B:73:0x0104, B:76:0x005e, B:77:0x000f), top: B:3:0x0005, inners: #0 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized void updateText(long r19) {
        /*
            Method dump skipped, instructions count: 321
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.Chronometer.updateText(long):void");
    }

    private ChronometerProgressDrawable getChronometerProgressDrawable() {
        if (this.mProgressDrawable instanceof LayerDrawable) {
            Drawable drawable = ((LayerDrawable) this.mProgressDrawable).findDrawableByLayerId(16908301);
            if (drawable instanceof ChronometerProgressDrawable) {
                return (ChronometerProgressDrawable) drawable;
            }
            if (drawable instanceof ClipDrawable) {
                return (ChronometerProgressDrawable) ((ClipDrawable) drawable).getDrawable();
            }
            return null;
        }
        return null;
    }

    private ChronometerProgressDrawable getChronometerBackgroundDrawable() {
        if (this.mProgressDrawable instanceof LayerDrawable) {
            Drawable drawable = ((LayerDrawable) this.mProgressDrawable).findDrawableByLayerId(16908288);
            if (drawable instanceof ChronometerProgressDrawable) {
                return (ChronometerProgressDrawable) drawable;
            }
            if (drawable instanceof ClipDrawable) {
                return (ChronometerProgressDrawable) ((ClipDrawable) drawable).getDrawable();
            }
            return null;
        }
        return null;
    }

    private void updateRunning() {
        boolean running;
        int tickTime;
        if (this.mMode == 0) {
            running = this.mVisible && this.mStarted && isShown();
        } else {
            boolean running2 = this.mVisible;
            running = running2 && this.mStarted && isShownForSemMode();
        }
        if (this.mRequestedTickTime != 1000) {
            tickTime = this.mRequestedTickTime;
        } else if (this.mPlaySpeed != 1.0f) {
            tickTime = (int) (1000.0f / this.mPlaySpeed);
        } else {
            tickTime = 1000;
        }
        if (running != this.mRunning) {
            if (running) {
                if (this.mMode == 2) {
                    post(this.mTickRunnable);
                } else {
                    updateText(SystemClock.elapsedRealtime());
                    dispatchChronometerTick();
                    if (tickTime != 1000) {
                        postDelayed(this.mTickRunnable, tickTime);
                    } else {
                        postTickOnNextSecond();
                    }
                }
            } else {
                removeCallbacks(this.mTickRunnable);
            }
            this.mRunning = running;
        }
    }

    private boolean isShownForSemMode() {
        View current = this;
        while (current.getVisibility() == 0) {
            Object parent = current.getParent();
            if (parent == null) {
                return current instanceof RemoteViewsAdapter.RemoteViewsFrameLayout;
            }
            if (!(parent instanceof View)) {
                return true;
            }
            current = (View) parent;
            if (current == null) {
                return false;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postTickOnNextSecond() {
        long delayMillis;
        long nowMillis = this.mNow;
        if (this.mCountDown) {
            delayMillis = (this.mBase - nowMillis) % 1000;
            if (delayMillis <= 0) {
                delayMillis += 1000;
            }
        } else {
            long delayMillis2 = this.mBase;
            delayMillis = 1000 - (Math.abs(nowMillis - delayMillis2) % 1000);
        }
        postDelayed(this.mTickRunnable, delayMillis + 1);
    }

    void dispatchChronometerTick() {
        if (this.mOnChronometerTickListener != null) {
            this.mOnChronometerTickListener.onChronometerTick(this);
        }
    }

    private static String formatDuration(long ms) {
        int duration = (int) (ms / 1000);
        if (duration < 0) {
            duration = -duration;
        }
        int h = 0;
        int m = 0;
        if (duration >= 3600) {
            h = duration / 3600;
            duration -= h * 3600;
        }
        if (duration >= 60) {
            m = duration / 60;
            duration -= m * 60;
        }
        int s = duration;
        ArrayList<Measure> measures = new ArrayList<>();
        if (h > 0) {
            measures.add(new Measure(Integer.valueOf(h), MeasureUnit.HOUR));
        }
        if (m > 0) {
            measures.add(new Measure(Integer.valueOf(m), MeasureUnit.MINUTE));
        }
        measures.add(new Measure(Integer.valueOf(s), MeasureUnit.SECOND));
        return MeasureFormat.getInstance(Locale.getDefault(), MeasureFormat.FormatWidth.WIDE).formatMeasures((Measure[]) measures.toArray(new Measure[measures.size()]));
    }

    @Override // android.view.View
    public CharSequence getContentDescription() {
        return formatDuration(this.mNow - this.mBase);
    }

    @Override // android.widget.TextView, android.view.View
    public CharSequence getAccessibilityClassName() {
        return Chronometer.class.getName();
    }

    @Override // android.widget.TextView, android.view.View
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mMode != 0 && this.mMode != 2) {
            drawTrack(canvas);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    void drawTrack(Canvas canvas) {
        Drawable drawable = this.mCurrentDrawable;
        if (drawable != 0) {
            int saveCount = canvas.save();
            canvas.translate(this.mPaddingLeft, this.mPaddingTop);
            drawable.draw(canvas);
            canvas.restoreToCount(saveCount);
            if (this.mShouldStartAnimationDrawable && (drawable instanceof Animatable)) {
                ((Animatable) drawable).start();
                this.mShouldStartAnimationDrawable = false;
            }
        }
    }

    private synchronized void setProgressInternal(int progress, boolean animate) {
        int progress2 = MathUtils.constrain(progress, this.mMinProgress, this.mMaxProgress);
        this.mProgress = progress2;
        if (this.mProgressDrawable instanceof LayerDrawable) {
            Drawable drawable = ((LayerDrawable) this.mProgressDrawable).findDrawableByLayerId(16908301);
            if (drawable instanceof ClipDrawable) {
                drawable = ((ClipDrawable) drawable).getDrawable();
            }
            if (drawable instanceof ChronometerProgressDrawable) {
                ((ChronometerProgressDrawable) drawable).setProgress(progress2, animate);
            }
        }
        refreshProgress(16908301, this.mProgress, animate);
    }

    private synchronized void refreshProgress(int id, int progress, boolean animate) {
        if (this.mRefreshProgressRunnable == null) {
            this.mRefreshProgressRunnable = new RefreshProgressRunnable();
        }
        RefreshData rd = RefreshData.obtain(id, progress, animate);
        this.mRefreshData.add(rd);
        if (this.mAttached && !this.mRefreshIsPosted) {
            post(this.mRefreshProgressRunnable);
            this.mRefreshIsPosted = true;
        }
    }

    private void initializeHorizontalProgressMode() {
        HorizontalProgressDrawable primaryProgress = new HorizontalProgressDrawable(false, colorToColorStateList(this.mProgressColor), colorToColorStateList(this.mProgressWarningColor));
        HorizontalProgressDrawable background = new HorizontalProgressDrawable(true, colorToColorStateList(this.mProgressBackgroundColor), null);
        drawables[0].setLevel(10000);
        Drawable[] drawables = {new ClipDrawable(background, 21, 1), new ClipDrawable(primaryProgress, 21, 1)};
        drawables[1].setLevel(0);
        LayerDrawable layer = new LayerDrawable(drawables);
        layer.setPaddingMode(1);
        layer.setId(0, 16908288);
        layer.setId(1, 16908301);
        setProgressDrawable(layer);
        this.mProgress = 0;
        this.mMinWidth = -1;
        this.mMaxWidth = -1;
        this.mMinHeight = 34;
        this.mMaxHeight = -1;
        this.mRoundStrokeWidth = this.mMinHeight;
    }

    private void initializeRoundCicleMode() {
        CirCleProgressDrawable primaryProgress = new CirCleProgressDrawable(false, colorToColorStateList(this.mProgressColor), colorToColorStateList(this.mProgressWarningColor));
        CirCleProgressDrawable background = new CirCleProgressDrawable(true, colorToColorStateList(this.mProgressBackgroundColor), null);
        Drawable[] drawables = {background, primaryProgress};
        LayerDrawable layer = new LayerDrawable(drawables);
        layer.setPaddingMode(1);
        layer.setId(0, 16908288);
        layer.setId(1, 16908301);
        setProgressDrawable(layer);
        this.mProgress = 0;
        this.mMinWidth = 180;
        this.mMaxWidth = 180;
        this.mMinHeight = 180;
        this.mMaxHeight = 180;
    }

    private ColorStateList colorToColorStateList(int color) {
        int[][] EMPTY = {new int[0]};
        return new ColorStateList(EMPTY, new int[]{color});
    }

    private void setProgressDrawable(Drawable d) {
        if (this.mProgressDrawable != d) {
            if (this.mProgressDrawable != null) {
                this.mProgressDrawable.setCallback(null);
                unscheduleDrawable(this.mProgressDrawable);
            }
            this.mProgressDrawable = d;
            d.setCallback(this);
            d.setLayoutDirection(getLayoutDirection());
            if (d.isStateful()) {
                d.setState(getDrawableState());
            }
            int drawableHeight = d.getMinimumHeight();
            if (this.mMaxHeight < drawableHeight) {
                this.mMaxHeight = drawableHeight;
                requestLayout();
            }
            swapCurrentDrawable(d);
            postInvalidate();
            updateDrawableBounds(getWidth(), getHeight());
            updateDrawableState();
            doRefreshProgress(16908301, this.mProgress, false, false);
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

    private void updateDrawableBounds(int w, int h) {
        int w2 = w - (this.mPaddingRight + this.mPaddingLeft);
        int h2 = h - (this.mPaddingTop + this.mPaddingBottom);
        if (this.mProgressDrawable != null) {
            this.mProgressDrawable.setBounds(0, 0, w2, h2);
        }
    }

    private void updateDrawableState() {
        int[] state = getDrawableState();
        boolean changed = false;
        Drawable progressDrawable = this.mProgressDrawable;
        if (progressDrawable != null && progressDrawable.isStateful()) {
            changed = false | progressDrawable.setState(state);
        }
        if (changed) {
            invalidate();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void doRefreshProgress(int id, int progress, boolean callBackToApp, boolean animate) {
        int range = this.mMaxProgress - this.mMinProgress;
        float scale = range > 0 ? (progress - this.mMinProgress) / range : 0.0f;
        Drawable d = this.mCurrentDrawable;
        if (d != null) {
            int level = (int) (10000.0f * scale);
            if (d instanceof LayerDrawable) {
                Drawable progressDrawable = ((LayerDrawable) d).findDrawableByLayerId(id);
                if (progressDrawable != null && canResolveLayoutDirection()) {
                    progressDrawable.setLayoutDirection(getLayoutDirection());
                }
                (progressDrawable != null ? progressDrawable : d).setLevel(level);
            } else {
                d.setLevel(level);
            }
        } else {
            invalidate();
        }
        if (callBackToApp) {
            onProgressRefresh(scale, progress);
        }
    }

    void onProgressRefresh(float scale, int progress) {
        if (getStateDescription() == null) {
            AccessibilityEvent event = AccessibilityEvent.obtain();
            event.setEventType(2048);
            event.setContentChangeTypes(64);
            sendAccessibilityEventUnchecked(event);
        }
    }

    @Override // android.widget.TextView, android.view.View, android.view.accessibility.AccessibilityEventSource
    public void sendAccessibilityEventUnchecked(AccessibilityEvent event) {
        if (event.getEventType() == 2048 && event.getContentChangeTypes() == 2) {
            return;
        }
        super.sendAccessibilityEventUnchecked(event);
    }

    @Override // android.widget.TextView, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mRefreshData != null) {
            synchronized (this) {
                int count = this.mRefreshData.size();
                for (int i = 0; i < count; i++) {
                    RefreshData rd = this.mRefreshData.get(i);
                    doRefreshProgress(rd.id, rd.progress, true, rd.animate);
                    rd.recycle();
                }
                this.mRefreshData.clear();
            }
        }
        this.mAttached = true;
    }

    @Override // android.widget.TextView, android.view.View
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (this.mMode == 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } else {
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
            setMeasuredDimension(measuredWidth, measuredHeight);
            updateDrawableBounds(getMeasuredWidth(), getMeasuredHeight());
        }
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

        public static RefreshData obtain(int id, int progress, boolean animate) {
            RefreshData rd = sPool.acquire();
            if (rd == null) {
                rd = new RefreshData();
            }
            rd.id = id;
            rd.progress = progress;
            rd.animate = animate;
            return rd;
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
                int count = Chronometer.this.mRefreshData.size();
                for (int i = 0; i < count; i++) {
                    RefreshData rd = (RefreshData) Chronometer.this.mRefreshData.get(i);
                    Chronometer.this.doRefreshProgress(rd.id, rd.progress, true, rd.animate);
                    rd.recycle();
                }
                Chronometer.this.mRefreshData.clear();
                Chronometer.this.mRefreshIsPosted = false;
            }
        }
    }

    private abstract class ChronometerProgressDrawable extends Drawable {
        ObjectAnimator mAnimator;
        int mColor;
        ColorStateList mColorStateList;
        boolean mIsBackground;
        boolean mIsWarningMode;
        public int mProgress;
        ColorStateList mWarningColorStateList;
        final Paint mPaint = new Paint();
        int mAlpha = 255;
        final ProgressState mState = new ProgressState();
        private final IntProperty<ChronometerProgressDrawable> VISUAL_CIRCLE_PROGRESS = new IntProperty<ChronometerProgressDrawable>("visual_progress") { // from class: android.widget.Chronometer.ChronometerProgressDrawable.1
            @Override // android.util.IntProperty
            public void setValue(ChronometerProgressDrawable object, int value) {
                int newValue = value;
                if (Chronometer.this.getVisibility() == 8) {
                    ChronometerProgressDrawable.this.mAnimator.cancel();
                    newValue = Chronometer.this.mMaxProgress;
                }
                object.mProgress = newValue;
                Drawable drawable = ((LayerDrawable) Chronometer.this.mProgressDrawable).findDrawableByLayerId(16908301);
                if (drawable instanceof ClipDrawable) {
                    drawable.setLevel(newValue);
                }
                ChronometerProgressDrawable.this.invalidateSelf();
            }

            @Override // android.util.Property
            public Integer get(ChronometerProgressDrawable object) {
                return Integer.valueOf(object.mProgress);
            }
        };

        public ChronometerProgressDrawable(boolean isBackground, ColorStateList color, ColorStateList warningColor) {
            this.mIsBackground = isBackground;
            this.mPaint.setStyle(Paint.Style.STROKE);
            this.mPaint.setStrokeCap(Paint.Cap.ROUND);
            this.mPaint.setAntiAlias(true);
            this.mColorStateList = color;
            this.mWarningColorStateList = warningColor;
            this.mColor = color.getDefaultColor();
            this.mPaint.setColor(this.mColor);
            this.mProgress = 0;
        }

        public void updateColor(ColorStateList color, ColorStateList warningColor) {
            this.mColorStateList = color;
            this.mWarningColorStateList = warningColor;
            this.mColor = color.getDefaultColor();
            this.mPaint.setColor(this.mColor);
        }

        void cancelAnimator() {
            if (this.mAnimator != null) {
                this.mAnimator.cancel();
            }
        }

        void setWarningMode(boolean enable) {
            if (this.mWarningColorStateList != null) {
                this.mIsWarningMode = enable;
                if (enable) {
                    setTintList(this.mWarningColorStateList);
                } else {
                    setTintList(this.mColorStateList);
                }
            }
        }

        void setWarningColor(ColorStateList warningColor) {
            this.mWarningColorStateList = warningColor;
        }

        int modulateAlpha(int paintAlpha, int alpha) {
            int scale = (alpha >>> 7) + alpha;
            return (paintAlpha * scale) >>> 8;
        }

        @Override // android.graphics.drawable.Drawable
        public boolean isStateful() {
            return true;
        }

        void setProgress(int progress, boolean animate) {
            if (animate) {
                this.mAnimator = ObjectAnimator.ofInt(this, this.VISUAL_CIRCLE_PROGRESS, progress);
                this.mAnimator.overrideDurationScale(1.0f);
                this.mAnimator.setAutoCancel(true);
                this.mAnimator.setDuration(Chronometer.this.mProgressAnimationDuration);
                this.mAnimator.setInterpolator(Chronometer.PROGRESS_ANIM_INTERPOLATOR);
                this.mAnimator.start();
                return;
            }
            if (this.mAnimator != null && this.mAnimator.isRunning()) {
                this.mAnimator.setFloatValues(this.mProgress);
                this.mAnimator.end();
                this.mAnimator.cancel();
                Drawable drawable = ((LayerDrawable) Chronometer.this.mProgressDrawable).findDrawableByLayerId(16908301);
                if (drawable instanceof ClipDrawable) {
                    drawable.setLevel(progress);
                }
                invalidateSelf();
            } else {
                this.mProgress = progress;
            }
            Chronometer.this.invalidate();
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
                this.mColor = tint.getDefaultColor();
                this.mPaint.setColor(this.mColor);
                invalidateSelf();
            }
        }

        @Override // android.graphics.drawable.Drawable
        protected boolean onStateChange(int[] stateSet) {
            int color;
            boolean changed = super.onStateChange(stateSet);
            if (this.mIsWarningMode) {
                color = this.mWarningColorStateList.getColorForState(stateSet, this.mColor);
            } else {
                color = this.mColorStateList.getColorForState(stateSet, this.mColor);
            }
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
                return ChronometerProgressDrawable.this;
            }

            @Override // android.graphics.drawable.Drawable.ConstantState
            public int getChangingConfigurations() {
                return 0;
            }
        }
    }

    private class CirCleProgressDrawable extends ChronometerProgressDrawable {
        private RectF mArcRect;

        public CirCleProgressDrawable(boolean isBackground, ColorStateList color, ColorStateList warningColor) {
            super(isBackground, color, warningColor);
            this.mArcRect = new RectF();
        }

        @Override // android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            this.mPaint.setStrokeWidth(Chronometer.this.mRoundStrokeWidth);
            int prevAlpha = this.mPaint.getAlpha();
            this.mPaint.setAlpha(modulateAlpha(prevAlpha, this.mAlpha));
            this.mArcRect.set((Chronometer.this.mRoundStrokeWidth / 2.0f) + Chronometer.this.mCirclePadding, (Chronometer.this.mRoundStrokeWidth / 2.0f) + Chronometer.this.mCirclePadding, (Chronometer.this.getWidth() - (Chronometer.this.mRoundStrokeWidth / 2.0f)) - Chronometer.this.mCirclePadding, (Chronometer.this.getWidth() - (Chronometer.this.mRoundStrokeWidth / 2.0f)) - Chronometer.this.mCirclePadding);
            int range = Chronometer.this.mMaxProgress - Chronometer.this.mMinProgress;
            float scale = range > 0 ? (this.mProgress - Chronometer.this.mMinProgress) / range : 0.0f;
            canvas.save();
            if (this.mIsBackground) {
                canvas.drawArc(this.mArcRect, 270.0f, 360.0f, false, this.mPaint);
            } else {
                canvas.drawArc(this.mArcRect, 270.0f, scale * 360.0f, false, this.mPaint);
            }
            canvas.restore();
            this.mPaint.setAlpha(prevAlpha);
        }
    }

    private class HorizontalProgressDrawable extends ChronometerProgressDrawable {
        public HorizontalProgressDrawable(boolean isBackground, ColorStateList color, ColorStateList warningColor) {
            super(isBackground, color, warningColor);
        }

        @Override // android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            this.mPaint.setStrokeWidth(Chronometer.this.mRoundStrokeWidth);
            int prevAlpha = this.mPaint.getAlpha();
            this.mPaint.setAlpha(modulateAlpha(prevAlpha, this.mAlpha));
            canvas.save();
            float yOffSet = Chronometer.this.mRoundStrokeWidth / 2.0f;
            canvas.drawLine(yOffSet, yOffSet, Chronometer.this.getWidth() - yOffSet, yOffSet, this.mPaint);
            canvas.restore();
            this.mPaint.setAlpha(prevAlpha);
        }
    }

    @Override // android.widget.TextView, android.view.View, android.graphics.drawable.Drawable.Callback
    public void invalidateDrawable(Drawable dr) {
        if (verifyDrawable(dr)) {
            Rect dirty = dr.getBounds();
            int scrollX = getScrollX() + getPaddingLeft();
            int scrollY = getScrollY() + getPaddingTop();
            invalidate(dirty.left + scrollX, dirty.top + scrollY, dirty.right + scrollX, dirty.bottom + scrollY);
            return;
        }
        super.invalidateDrawable(dr);
    }

    @Override // android.widget.TextView, android.view.View
    protected boolean verifyDrawable(Drawable who) {
        return who == this.mProgressDrawable || super.verifyDrawable(who);
    }

    @RemotableViewMethod
    public void hidden_semSetMode(int mode) {
        if (this.mMode != mode) {
            this.mMode = mode;
            switch (this.mMode) {
                case 1:
                    initializeRoundCicleMode();
                    initCirCleStrokeWidth();
                    break;
                case 4:
                    initializeHorizontalProgressMode();
                    break;
            }
            invalidate();
        }
    }

    @RemotableViewMethod
    public void hidden_semSetOriginalBase(long originalBase) {
        this.mOriginalBase = originalBase;
        long seconds = originalBase - SystemClock.elapsedRealtime();
        this.mFirstTimerSeconds = seconds;
    }

    @RemotableViewMethod
    public void hidden_semSetMaxProgressValue(long maxValue) {
        this.mFirstTimerSeconds = maxValue;
    }

    @RemotableViewMethod
    public void hidden_semSetProgressBackgroundColor(int color) {
        this.mProgressBackgroundColor = color;
        ChronometerProgressDrawable drawable = getChronometerBackgroundDrawable();
        if (drawable != null) {
            drawable.updateColor(colorToColorStateList(color), null);
        }
    }

    @RemotableViewMethod
    public void hidden_semSetProgressColor(int color) {
        this.mProgressColor = color;
        ChronometerProgressDrawable drawable = getChronometerProgressDrawable();
        if (drawable != null) {
            drawable.updateColor(colorToColorStateList(this.mProgressColor), colorToColorStateList(this.mProgressWarningColor));
        }
    }

    @RemotableViewMethod
    public void hidden_semSetProgressWidth(int size) {
        this.mRoundStrokeWidth = size;
    }

    @RemotableViewMethod
    public void hidden_semSetWarningColor(int warningColor) {
        this.mProgressWarningColor = warningColor;
        ChronometerProgressDrawable drawable = getChronometerProgressDrawable();
        if (drawable != null) {
            drawable.setWarningColor(colorToColorStateList(warningColor));
        }
    }

    @RemotableViewMethod
    public void hidden_semSetWarningTime(long warningTime) {
        this.mWaringTime = warningTime;
    }

    @RemotableViewMethod
    public void hidden_semInvokeChronometer(String name) {
        if (name.equals("stop")) {
            stop();
        }
    }

    @RemotableViewMethod
    public void hidden_semSetFixedHourFormat(boolean enabled) {
        this.mIsFixedHourFormat = enabled;
    }

    @RemotableViewMethod
    public void hidden_semSetTimeSpeed(float speed) {
        if (this.mPlaySpeed != speed && speed > 0.0f) {
            this.mPlaySpeed = speed;
        }
    }

    @RemotableViewMethod
    public void hidden_semSetForceTickTime(int updateTickTime) {
        this.mRequestedTickTime = updateTickTime;
    }

    @RemotableViewMethod
    public void hidden_semSetMilliSecondCount(int millisSecondCount) {
        this.mMilliSecondCount = Math.min(Math.max(millisSecondCount, 0), 3);
    }

    @RemotableViewMethod
    public void hidden_semSetShowingSeconds(boolean isShowingSeconds) {
        this.mIsShowingSeconds = isShowingSeconds;
    }
}
