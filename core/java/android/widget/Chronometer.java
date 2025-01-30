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
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.util.IntProperty;
import android.util.Log;
import android.util.MathUtils;
import android.util.Pools;
import android.view.RemotableViewMethod;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.LinearInterpolator;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import com.android.internal.C4337R;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.IllegalFormatException;
import java.util.Locale;
import java.util.TimeZone;

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
  private boolean mLogged;
  private int mMaxHeight;
  private int mMaxProgress;
  private int mMaxWidth;
  private int mMinHeight;
  private int mMinProgress;
  private int mMinWidth;
  private int mMode;
  private long mNow;
  private OnChronometerTickListener mOnChronometerTickListener;
  private long mOriginalBase;
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

  public final class InspectionCompanion
      implements android.view.inspector.InspectionCompanion<Chronometer> {
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
    this.mFirstTimerSeconds = 0L;
    this.mOriginalBase = 0L;
    this.mFormatterArgs = new Object[1];
    this.mRecycle = new StringBuilder(8);
    this.mTickRunnable =
        new Runnable() { // from class: android.widget.Chronometer.1
          @Override // java.lang.Runnable
          public void run() {
            if (Chronometer.this.mRunning) {
              Chronometer.this.updateText(SystemClock.elapsedRealtime());
              Chronometer.this.dispatchChronometerTick();
              Chronometer chronometer = Chronometer.this;
              chronometer.postDelayed(chronometer.mTickRunnable, 1000L);
            }
          }
        };
    TypedArray a =
        context.obtainStyledAttributes(
            attrs, C4337R.styleable.Chronometer, defStyleAttr, defStyleRes);
    saveAttributeDataForStyleable(
        context, C4337R.styleable.Chronometer, attrs, a, defStyleAttr, defStyleRes);
    setFormat(a.getString(0));
    setCountDown(a.getBoolean(1, false));
    a.recycle();
    init();
  }

  private void init() {
    long elapsedRealtime = SystemClock.elapsedRealtime();
    this.mBase = elapsedRealtime;
    updateText(elapsedRealtime);
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
      getContext()
          .startActivity(
              new Intent("android.intent.action.VIEW", Uri.parse("https://youtu.be/9jK-NcRmVcw"))
                  .addCategory(Intent.CATEGORY_BROWSABLE)
                  .addFlags(528384));
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  @RemotableViewMethod
  public void setBase(long base) {
    int i = this.mMode;
    if (i == 1 || i == 4) {
      long elapsedRealtime = base - SystemClock.elapsedRealtime();
      this.mBaseTimerSeconds = elapsedRealtime;
      long j = this.mFirstTimerSeconds;
      if (elapsedRealtime > j) {
        this.mBaseTimerSeconds = j;
      }
      if (this.mOriginalBase != 0) {
        long j2 = this.mBaseTimerSeconds;
        if (j != j2 && j != 0) {
          float scale = j2 / j;
          int i2 = this.mProgress;
          int i3 = (int) (10000.0f * scale);
          this.mProgress = i3;
          setProgressInternal(i3, false);
        }
      }
      setProgressInternal(this.mMaxProgress, false);
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
      int i = this.mMode;
      if (i == 1 || i == 4) {
        if (this.mOriginalBase != 0) {
          long j = this.mFirstTimerSeconds;
          long j2 = this.mBaseTimerSeconds;
          if (j != j2 && j != 0) {
            float scale = j2 / j;
            int i2 = this.mProgress;
            int i3 = (int) (10000.0f * scale);
            this.mProgress = i3;
            setProgressInternal(i3, false);
          }
        }
        setProgressInternal(this.mMaxProgress, false);
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
    RefreshProgressRunnable refreshProgressRunnable = this.mRefreshProgressRunnable;
    if (refreshProgressRunnable != null) {
      removeCallbacks(refreshProgressRunnable);
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
  /* JADX WARN: Removed duplicated region for block: B:48:0x00e3 A[Catch: all -> 0x0105, TryCatch #0 {, blocks: (B:4:0x0005, B:6:0x000b, B:7:0x0013, B:10:0x0026, B:14:0x0037, B:16:0x0058, B:17:0x006a, B:19:0x0070, B:21:0x0079, B:23:0x008c, B:25:0x0095, B:29:0x00a5, B:31:0x00a9, B:32:0x0081, B:33:0x00c5, B:38:0x00cd, B:39:0x00d6, B:46:0x00dd, B:48:0x00e3, B:51:0x00ec, B:53:0x00ef, B:58:0x00fb, B:60:0x0100, B:62:0x00d3, B:65:0x000f), top: B:3:0x0005, inners: #1 }] */
  /* JADX WARN: Removed duplicated region for block: B:60:0x0100 A[Catch: all -> 0x0105, TRY_LEAVE, TryCatch #0 {, blocks: (B:4:0x0005, B:6:0x000b, B:7:0x0013, B:10:0x0026, B:14:0x0037, B:16:0x0058, B:17:0x006a, B:19:0x0070, B:21:0x0079, B:23:0x008c, B:25:0x0095, B:29:0x00a5, B:31:0x00a9, B:32:0x0081, B:33:0x00c5, B:38:0x00cd, B:39:0x00d6, B:46:0x00dd, B:48:0x00e3, B:51:0x00ec, B:53:0x00ef, B:58:0x00fb, B:60:0x0100, B:62:0x00d3, B:65:0x000f), top: B:3:0x0005, inners: #1 }] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public synchronized void updateText(long now) {
    long seconds;
    boolean negative;
    String text;
    int i;
    ChronometerProgressDrawable drawable;
    this.mNow = now;
    long seconds2 = (this.mCountDown ? this.mBase - now : now - this.mBase) / 1000;
    if (seconds2 >= 0) {
      seconds = seconds2;
      negative = false;
    } else {
      seconds = -seconds2;
      negative = true;
    }
    String text2 = DateUtils.formatElapsedTime(this.mRecycle, seconds);
    if (this.mIsFixedHourFormat && seconds < 86400) {
      SimpleDateFormat formatWithHour = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
      formatWithHour.setTimeZone(TimeZone.getTimeZone("GMT"));
      text2 = formatWithHour.format(Long.valueOf(seconds * 1000));
    }
    if (!negative) {
      text = text2;
    } else {
      text = getResources().getString(C4337R.string.negative_duration, text2);
    }
    boolean z = true;
    if (this.mFormat != null) {
      Locale loc = Locale.getDefault();
      if (this.mFormatter == null || !loc.equals(this.mFormatterLocale)) {
        this.mFormatterLocale = loc;
        this.mFormatter = new Formatter(this.mFormatBuilder, loc);
      }
      this.mFormatBuilder.setLength(0);
      Object[] objArr = this.mFormatterArgs;
      objArr[0] = text;
      try {
        this.mFormatter.format(this.mFormat, objArr);
        text = this.mFormatBuilder.toString();
      } catch (IllegalFormatException e) {
        if (!this.mLogged) {
          Log.m102w(TAG, "Illegal format string: " + this.mFormat);
          this.mLogged = true;
        }
      }
    }
    int i2 = this.mMode;
    if (i2 != 0 && i2 != 2) {
      setText("");
      i = this.mMode;
      if (i != 1 || i == 4) {
        drawable = getChronometerProgressDrawable();
        if (drawable != null) {
          if (seconds >= this.mWaringTime / 1000) {
            z = false;
          }
          drawable.setWarningMode(z);
        }
        if (this.mCountDown && (seconds == 0 || negative)) {
          this.mRunning = false;
          if (drawable != null) {
            drawable.cancelAnimator();
          }
        }
      }
    }
    setText(text);
    i = this.mMode;
    if (i != 1) {}
    drawable = getChronometerProgressDrawable();
    if (drawable != null) {}
    if (this.mCountDown) {
      this.mRunning = false;
      if (drawable != null) {}
    }
  }

  private ChronometerProgressDrawable getChronometerProgressDrawable() {
    Drawable drawable = this.mProgressDrawable;
    if (drawable instanceof LayerDrawable) {
      Drawable drawable2 = ((LayerDrawable) drawable).findDrawableByLayerId(16908301);
      if (drawable2 instanceof ChronometerProgressDrawable) {
        return (ChronometerProgressDrawable) drawable2;
      }
      if (drawable2 instanceof ClipDrawable) {
        return (ChronometerProgressDrawable) ((ClipDrawable) drawable2).getDrawable();
      }
      return null;
    }
    return null;
  }

  private ChronometerProgressDrawable getChronometerBackgroundDrawable() {
    Drawable drawable = this.mProgressDrawable;
    if (drawable instanceof LayerDrawable) {
      Drawable drawable2 = ((LayerDrawable) drawable).findDrawableByLayerId(16908288);
      if (drawable2 instanceof ChronometerProgressDrawable) {
        return (ChronometerProgressDrawable) drawable2;
      }
      if (drawable2 instanceof ClipDrawable) {
        return (ChronometerProgressDrawable) ((ClipDrawable) drawable2).getDrawable();
      }
      return null;
    }
    return null;
  }

  private void updateRunning() {
    boolean running;
    if (this.mMode == 0) {
      running = this.mVisible && this.mStarted && isShown();
    } else {
      boolean running2 = this.mVisible;
      running = running2 && this.mStarted && isShownForSemMode();
    }
    if (running != this.mRunning) {
      if (running) {
        if (this.mMode == 2) {
          post(this.mTickRunnable);
        } else {
          updateText(SystemClock.elapsedRealtime());
          dispatchChronometerTick();
          postDelayed(this.mTickRunnable, 1000L);
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

  void dispatchChronometerTick() {
    OnChronometerTickListener onChronometerTickListener = this.mOnChronometerTickListener;
    if (onChronometerTickListener != null) {
      onChronometerTickListener.onChronometerTick(this);
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
    return MeasureFormat.getInstance(Locale.getDefault(), MeasureFormat.FormatWidth.WIDE)
        .formatMeasures((Measure[]) measures.toArray(new Measure[measures.size()]));
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
    int i = this.mMode;
    if (i != 0 && i != 2) {
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
    Drawable drawable = this.mProgressDrawable;
    if (drawable instanceof LayerDrawable) {
      Drawable drawable2 = ((LayerDrawable) drawable).findDrawableByLayerId(16908301);
      if (drawable2 instanceof ClipDrawable) {
        drawable2 = ((ClipDrawable) drawable2).getDrawable();
      }
      if (drawable2 instanceof ChronometerProgressDrawable) {
        ((ChronometerProgressDrawable) drawable2).setProgress(progress2, animate);
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
    HorizontalProgressDrawable primaryProgress =
        new HorizontalProgressDrawable(
            false,
            colorToColorStateList(this.mProgressColor),
            colorToColorStateList(this.mProgressWarningColor));
    HorizontalProgressDrawable background =
        new HorizontalProgressDrawable(
            true, colorToColorStateList(this.mProgressBackgroundColor), null);
    drawables[0].setLevel(10000);
    Drawable[] drawables = {
      new ClipDrawable(background, 21, 1), new ClipDrawable(primaryProgress, 21, 1)
    };
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
    this.mRoundStrokeWidth = 34;
  }

  private void initializeRoundCicleMode() {
    CirCleProgressDrawable primaryProgress =
        new CirCleProgressDrawable(
            false,
            colorToColorStateList(this.mProgressColor),
            colorToColorStateList(this.mProgressWarningColor));
    CirCleProgressDrawable background =
        new CirCleProgressDrawable(
            true, colorToColorStateList(this.mProgressBackgroundColor), null);
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
    return new ColorStateList(EMPTY, new int[] {color});
  }

  private void setProgressDrawable(Drawable d) {
    Drawable drawable = this.mProgressDrawable;
    if (drawable != d) {
      if (drawable != null) {
        drawable.setCallback(null);
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
    if (oldDrawable != newDrawable) {
      if (oldDrawable != null) {
        oldDrawable.setVisible(false, false);
      }
      Drawable drawable = this.mCurrentDrawable;
      if (drawable != null) {
        drawable.setVisible(getWindowVisibility() == 0 && isShown(), false);
      }
    }
  }

  private void updateDrawableBounds(int w, int h) {
    int w2 = w - (this.mPaddingRight + this.mPaddingLeft);
    int h2 = h - (this.mPaddingTop + this.mPaddingBottom);
    Drawable drawable = this.mProgressDrawable;
    if (drawable != null) {
      drawable.setBounds(0, 0, w2, h2);
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
  public synchronized void doRefreshProgress(
      int id, int progress, boolean callBackToApp, boolean animate) {
    int range = this.mMaxProgress - this.mMinProgress;
    float scale = range > 0 ? (progress - r1) / range : 0.0f;
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

  @Override // android.widget.TextView, android.view.View
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    if (this.mRefreshData != null) {
      synchronized (this) {
        int count = this.mRefreshData.size();
        for (int i = 0; i < count; i++) {
          RefreshData rd = this.mRefreshData.get(i);
          doRefreshProgress(rd.f600id, rd.progress, true, rd.animate);
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
    private static final Pools.SynchronizedPool<RefreshData> sPool =
        new Pools.SynchronizedPool<>(24);
    public boolean animate;

    /* renamed from: id */
    public int f600id;
    public int progress;

    private RefreshData() {}

    public static RefreshData obtain(int id, int progress, boolean animate) {
      RefreshData rd = sPool.acquire();
      if (rd == null) {
        rd = new RefreshData();
      }
      rd.f600id = id;
      rd.progress = progress;
      rd.animate = animate;
      return rd;
    }

    public void recycle() {
      sPool.release(this);
    }
  }

  private class RefreshProgressRunnable implements Runnable {
    private RefreshProgressRunnable() {}

    @Override // java.lang.Runnable
    public void run() {
      synchronized (Chronometer.this) {
        int count = Chronometer.this.mRefreshData.size();
        for (int i = 0; i < count; i++) {
          RefreshData rd = (RefreshData) Chronometer.this.mRefreshData.get(i);
          Chronometer.this.doRefreshProgress(rd.f600id, rd.progress, true, rd.animate);
          rd.recycle();
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

    public ChronometerProgressDrawable(
        boolean isBackground, ColorStateList color, ColorStateList warningColor) {
      Paint paint = new Paint();
      this.mPaint = paint;
      this.mAlpha = 255;
      this.mState = new ProgressState();
      this.VISUAL_CIRCLE_PROGRESS =
          new IntProperty<ChronometerProgressDrawable>(
              "visual_progress") { // from class:
                                   // android.widget.Chronometer.ChronometerProgressDrawable.1
            @Override // android.util.IntProperty
            public void setValue(ChronometerProgressDrawable object, int value) {
              int newValue = value;
              if (Chronometer.this.getVisibility() == 8) {
                ChronometerProgressDrawable.this.mAnimator.cancel();
                newValue = Chronometer.this.mMaxProgress;
              }
              object.mProgress = newValue;
              Drawable drawable =
                  ((LayerDrawable) Chronometer.this.mProgressDrawable)
                      .findDrawableByLayerId(16908301);
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
      this.mIsBackground = isBackground;
      paint.setStyle(Paint.Style.STROKE);
      paint.setStrokeCap(Paint.Cap.ROUND);
      paint.setAntiAlias(true);
      this.mColorStateList = color;
      this.mWarningColorStateList = warningColor;
      int defaultColor = color.getDefaultColor();
      this.mColor = defaultColor;
      paint.setColor(defaultColor);
      this.mProgress = 0;
    }

    public void updateColor(ColorStateList color, ColorStateList warningColor) {
      this.mColorStateList = color;
      this.mWarningColorStateList = warningColor;
      int defaultColor = color.getDefaultColor();
      this.mColor = defaultColor;
      this.mPaint.setColor(defaultColor);
    }

    void cancelAnimator() {
      ObjectAnimator objectAnimator = this.mAnimator;
      if (objectAnimator != null) {
        objectAnimator.cancel();
      }
    }

    void setWarningMode(boolean enable) {
      ColorStateList colorStateList = this.mWarningColorStateList;
      if (colorStateList != null) {
        this.mIsWarningMode = enable;
        if (enable) {
          setTintList(colorStateList);
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
        ObjectAnimator ofInt = ObjectAnimator.ofInt(this, this.VISUAL_CIRCLE_PROGRESS, progress);
        this.mAnimator = ofInt;
        ofInt.setAutoCancel(true);
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
        Drawable drawable =
            ((LayerDrawable) Chronometer.this.mProgressDrawable).findDrawableByLayerId(16908301);
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
        int defaultColor = tint.getDefaultColor();
        this.mColor = defaultColor;
        this.mPaint.setColor(defaultColor);
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
        this.mPaint.setColor(color);
        invalidateSelf();
      }
      return changed;
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable.ConstantState getConstantState() {
      return this.mState;
    }

    private class ProgressState extends Drawable.ConstantState {
      private ProgressState() {}

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

    public CirCleProgressDrawable(
        boolean isBackground, ColorStateList color, ColorStateList warningColor) {
      super(isBackground, color, warningColor);
      this.mArcRect = new RectF();
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
      this.mPaint.setStrokeWidth(Chronometer.this.mRoundStrokeWidth);
      int prevAlpha = this.mPaint.getAlpha();
      this.mPaint.setAlpha(modulateAlpha(prevAlpha, this.mAlpha));
      this.mArcRect.set(
          (Chronometer.this.mRoundStrokeWidth / 2.0f) + Chronometer.this.mCirclePadding,
          (Chronometer.this.mRoundStrokeWidth / 2.0f) + Chronometer.this.mCirclePadding,
          (Chronometer.this.getWidth() - (Chronometer.this.mRoundStrokeWidth / 2.0f))
              - Chronometer.this.mCirclePadding,
          (Chronometer.this.getWidth() - (Chronometer.this.mRoundStrokeWidth / 2.0f))
              - Chronometer.this.mCirclePadding);
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
    public HorizontalProgressDrawable(
        boolean isBackground, ColorStateList color, ColorStateList warningColor) {
      super(isBackground, color, warningColor);
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
      this.mPaint.setStrokeWidth(Chronometer.this.mRoundStrokeWidth);
      int prevAlpha = this.mPaint.getAlpha();
      this.mPaint.setAlpha(modulateAlpha(prevAlpha, this.mAlpha));
      canvas.save();
      float yOffSet = Chronometer.this.mRoundStrokeWidth / 2.0f;
      canvas.drawLine(
          yOffSet, yOffSet, Chronometer.this.getWidth() - yOffSet, yOffSet, this.mPaint);
      canvas.restore();
      this.mPaint.setAlpha(prevAlpha);
    }
  }

  @Override // android.widget.TextView, android.view.View,
            // android.graphics.drawable.Drawable.Callback
  public void invalidateDrawable(Drawable dr) {
    if (verifyDrawable(dr)) {
      Rect dirty = dr.getBounds();
      int scrollX = getScrollX() + getPaddingLeft();
      int scrollY = getScrollY() + getPaddingTop();
      invalidate(
          dirty.left + scrollX, dirty.top + scrollY, dirty.right + scrollX, dirty.bottom + scrollY);
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
      switch (mode) {
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
      drawable.updateColor(
          colorToColorStateList(this.mProgressColor),
          colorToColorStateList(this.mProgressWarningColor));
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
}
