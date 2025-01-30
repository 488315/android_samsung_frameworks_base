package com.android.internal.graphics.drawable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.HardwareRenderer;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RenderNode;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.p009os.Debug;
import android.p009os.Handler;
import android.p009os.Looper;
import android.util.ArraySet;
import android.util.Log;
import android.util.LongSparseArray;
import android.view.SemBlurInfo;
import android.view.View;
import android.view.ViewRootImpl;
import android.view.ViewTreeObserver;
import com.android.internal.C4337R;

/* loaded from: classes4.dex */
public final class BackgroundBlurDrawable extends Drawable {
  private static final boolean DEBUG;
  private static final String TAG;
  private final Aggregator mAggregator;
  private float mAlpha;
  private int mBlurRadius;
  private SemBlurInfo.ColorCurve mColorCurve;
  private float mCornerRadiusBL;
  private float mCornerRadiusBR;
  private float mCornerRadiusTL;
  private float mCornerRadiusTR;
  private final Handler mHandler;
  private boolean mIsTargetView;
  private final Paint mPaint;
  public final RenderNode.PositionUpdateListener mPositionUpdateListener;
  private final Rect mRect;
  private final Path mRectPath;
  private final RenderNode mRenderNode;
  private float mScaleX;
  private float mScaleY;
  private boolean mShowDebug;
  private final float[] mTmpRadii;
  private boolean mVisible;

  /* JADX WARN: Code restructure failed: missing block: B:4:0x0017, code lost:

     if (android.p009os.SystemProperties.getInt("viewroot.debug.blur", 0) != 0) goto L6;
  */
  static {
    String simpleName = BackgroundBlurDrawable.class.getSimpleName();
    TAG = simpleName;
    boolean z = Log.isLoggable(simpleName, 3);
    DEBUG = z;
  }

  /* renamed from: com.android.internal.graphics.drawable.BackgroundBlurDrawable$1 */
  class C44541 implements RenderNode.PositionUpdateListener {
    C44541() {}

    @Override // android.graphics.RenderNode.PositionUpdateListener
    public void positionChanged(
        final long frameNumber, final int left, final int top, final int right, final int bottom) {
      final boolean showDebug = BackgroundBlurDrawable.this.mShowDebug;
      BackgroundBlurDrawable.this.mRect.set(left, top, right, bottom);
      if (BackgroundBlurDrawable.DEBUG || showDebug) {
        Log.m98i(
            BackgroundBlurDrawable.TAG,
            "positionChanged fn="
                + frameNumber
                + " drawable="
                + BackgroundBlurDrawable.this
                + ", left : "
                + left
                + ", top : "
                + top
                + ", right : "
                + right
                + ", bottom : "
                + bottom);
        Drawable.Callback callback = BackgroundBlurDrawable.this.getCallback();
        if (callback instanceof View) {
          View attachedView = (View) callback;
          ViewRootImpl viewRoot = attachedView.getViewRootImpl();
          Log.m98i(
              BackgroundBlurDrawable.TAG,
              "positionChanged attached View="
                  + attachedView
                  + ", viewRoot="
                  + ((Object) (viewRoot != null ? viewRoot.getTag() : viewRoot)));
        } else {
          Log.m98i(BackgroundBlurDrawable.TAG, "positionChanged attached callback=" + callback);
        }
      }
      BackgroundBlurDrawable.this.mAggregator.onRenderNodePositionChanged(
          frameNumber,
          new Runnable() { // from class:
                           // com.android.internal.graphics.drawable.BackgroundBlurDrawable$1$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
              BackgroundBlurDrawable.C44541.this.lambda$positionChanged$1(
                  showDebug, frameNumber, left, top, right, bottom);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$positionChanged$1(
        final boolean showDebug, final long frameNumber, int left, int top, int right, int bottom) {
      if (BackgroundBlurDrawable.DEBUG || showDebug) {
        Log.m98i(
            BackgroundBlurDrawable.TAG,
            "positionChanged$run fn="
                + frameNumber
                + " dr=BackgroundBlurDrawable@"
                + BackgroundBlurDrawable.this.hashCode()
                + " rect="
                + BackgroundBlurDrawable.this.mRect);
      }
      BackgroundBlurDrawable.this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.internal.graphics.drawable.BackgroundBlurDrawable$1$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              BackgroundBlurDrawable.C44541.this.lambda$positionChanged$0(showDebug, frameNumber);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$positionChanged$0(boolean showDebug, long frameNumber) {
      if (BackgroundBlurDrawable.DEBUG || showDebug) {
        Log.m98i(
            BackgroundBlurDrawable.TAG,
            "positionChanged$run#2 fn="
                + frameNumber
                + " dr=BackgroundBlurDrawable@"
                + BackgroundBlurDrawable.this.hashCode()
                + " rect="
                + BackgroundBlurDrawable.this.mRect);
      }
      BackgroundBlurDrawable.this.invalidateSelf();
      BackgroundBlurDrawable.this.mAggregator.onBlurDrawableUpdated(BackgroundBlurDrawable.this);
    }

    @Override // android.graphics.RenderNode.PositionUpdateListener
    public void positionLost(final long frameNumber) {
      final boolean showDebug = BackgroundBlurDrawable.this.mShowDebug;
      BackgroundBlurDrawable.this.mRect.setEmpty();
      if (BackgroundBlurDrawable.DEBUG || showDebug) {
        Log.m98i(
            BackgroundBlurDrawable.TAG,
            "positionLost fn="
                + frameNumber
                + " dr=BackgroundBlurDrawable@"
                + BackgroundBlurDrawable.this.hashCode()
                + " rect="
                + BackgroundBlurDrawable.this.mRect);
      }
      BackgroundBlurDrawable.this.mAggregator.onRenderNodePositionChanged(
          frameNumber,
          new Runnable() { // from class:
                           // com.android.internal.graphics.drawable.BackgroundBlurDrawable$1$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
              BackgroundBlurDrawable.C44541.this.lambda$positionLost$3(showDebug, frameNumber);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$positionLost$3(
        final boolean showDebug, final long frameNumber) {
      if (BackgroundBlurDrawable.DEBUG || showDebug) {
        Log.m98i(
            BackgroundBlurDrawable.TAG,
            "positionLost$run fn="
                + frameNumber
                + " dr=BackgroundBlurDrawable@"
                + BackgroundBlurDrawable.this.hashCode()
                + " rect="
                + BackgroundBlurDrawable.this.mRect);
      }
      BackgroundBlurDrawable.this.mHandler.post(
          new Runnable() { // from class:
                           // com.android.internal.graphics.drawable.BackgroundBlurDrawable$1$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
              BackgroundBlurDrawable.C44541.this.lambda$positionLost$2(showDebug, frameNumber);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$positionLost$2(boolean showDebug, long frameNumber) {
      if (BackgroundBlurDrawable.DEBUG || showDebug) {
        Log.m98i(
            BackgroundBlurDrawable.TAG,
            "positionLost$run#2 fn="
                + frameNumber
                + " dr=BackgroundBlurDrawable@"
                + BackgroundBlurDrawable.this.hashCode()
                + " rect="
                + BackgroundBlurDrawable.this.mRect);
      }
      BackgroundBlurDrawable.this.invalidateSelf();
      BackgroundBlurDrawable.this.mAggregator.onBlurDrawableUpdated(BackgroundBlurDrawable.this);
    }
  }

  public boolean isShowDebug() {
    return this.mShowDebug;
  }

  private boolean showDebugByTargetView() {
    Drawable.Callback callback = getCallback();
    if (!(callback instanceof View) || !((View) callback).isBlurDebug()) {
      return false;
    }
    return true;
  }

  private BackgroundBlurDrawable(Aggregator aggregator, boolean showDebug) {
    this.mColorCurve = null;
    this.mShowDebug = false;
    this.mIsTargetView = false;
    Paint paint = new Paint();
    this.mPaint = paint;
    this.mRectPath = new Path();
    this.mTmpRadii = new float[8];
    this.mVisible = true;
    this.mAlpha = 1.0f;
    this.mScaleX = 1.0f;
    this.mScaleY = 1.0f;
    this.mRect = new Rect();
    C44541 c44541 = new C44541();
    this.mPositionUpdateListener = c44541;
    this.mAggregator = aggregator;
    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
    paint.setColor(0);
    paint.setAntiAlias(true);
    RenderNode renderNode = new RenderNode("BackgroundBlurDrawable");
    this.mRenderNode = renderNode;
    this.mShowDebug = showDebug;
    this.mIsTargetView = showDebug;
    renderNode.addPositionUpdateListener(c44541);
    this.mHandler = new Handler(Looper.myLooper());
  }

  private BackgroundBlurDrawable(Aggregator aggregator) {
    this.mColorCurve = null;
    this.mShowDebug = false;
    this.mIsTargetView = false;
    Paint paint = new Paint();
    this.mPaint = paint;
    this.mRectPath = new Path();
    this.mTmpRadii = new float[8];
    this.mVisible = true;
    this.mAlpha = 1.0f;
    this.mScaleX = 1.0f;
    this.mScaleY = 1.0f;
    this.mRect = new Rect();
    C44541 c44541 = new C44541();
    this.mPositionUpdateListener = c44541;
    this.mAggregator = aggregator;
    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
    paint.setColor(0);
    paint.setAntiAlias(true);
    RenderNode renderNode = new RenderNode("BackgroundBlurDrawable");
    this.mRenderNode = renderNode;
    renderNode.addPositionUpdateListener(c44541);
    this.mHandler = new Handler(Looper.myLooper());
  }

  @Override // android.graphics.drawable.Drawable
  public void draw(Canvas canvas) {
    if (!isVisible() || getAlpha() == 0) {
      if (DEBUG) {
        Log.m98i(
            TAG,
            "draw: BackgroundBlurDrawable@"
                + hashCode()
                + " bounds="
                + getBounds()
                + ", mRectPath.isEmpty()="
                + this.mRectPath.isEmpty()
                + " isVisible()="
                + isVisible()
                + " getAlpha()="
                + getAlpha()
                + " mAlpha="
                + this.mAlpha
                + " return");
        return;
      }
      return;
    }
    if (this.mRectPath.isEmpty()) {
      updatePath();
    }
    if (canvas.isHardwareAccelerated()) {
      canvas.drawPath(this.mRectPath, this.mPaint);
      if (this.mShowDebug) {
        this.mRenderNode.setBlurRadius(this.mBlurRadius);
        if (this.mBlurRadius <= 100) {
          Log.m98i(
              TAG,
              "draw: BackgroundBlurDrawable@"
                  + hashCode()
                  + " bounds="
                  + getBounds()
                  + ", drawRenderNode w="
                  + this.mRenderNode.getWidth()
                  + " h="
                  + this.mRenderNode.getHeight());
        }
      }
      canvas.drawRenderNode(this.mRenderNode);
      return;
    }
    Log.m98i(TAG, "BackgroundBlur is not supported on S/W canvas!!!!");
  }

  public void setScaleX(float scaleX) {
    Log.m94d("BackgroundBlurDrawable", "setScaleX, scaleX : " + scaleX);
    this.mScaleX = scaleX;
    this.mAggregator.onBlurDrawableUpdated(this);
  }

  public void setScaleY(float scaleY) {
    Log.m94d("BackgroundBlurDrawable", "setScaleY, scaleY : " + scaleY);
    this.mScaleY = scaleY;
    this.mAggregator.onBlurDrawableUpdated(this);
  }

  public void setColor(int color) {
    this.mPaint.setColor(color);
  }

  @Override // android.graphics.drawable.Drawable
  public boolean setVisible(boolean visible, boolean restart) {
    boolean changed = super.setVisible(visible, restart);
    if (this.mShowDebug) {
      Log.m98i(
          TAG,
          "setVisible: visible="
              + visible
              + " BackgroundBlurDrawable@"
              + hashCode()
              + " Callers="
              + Debug.getCallers(10));
    }
    if (changed) {
      this.mVisible = visible;
      if (this.mIsTargetView && visible && getAlpha() != 0 && this.mRectPath.isEmpty()) {
        Log.m98i(TAG, "setVisible: mRectPath is empty, need to call updatePath");
        updatePath();
      }
      this.mAggregator.onBlurDrawableUpdated(this);
    }
    return changed;
  }

  @Override // android.graphics.drawable.Drawable
  public void setAlpha(int alpha) {
    if (this.mAlpha != alpha / 255.0f) {
      this.mAlpha = alpha / 255.0f;
      if (this.mIsTargetView && getAlpha() != 0 && this.mVisible && this.mRectPath.isEmpty()) {
        if (this.mShowDebug) {
          Log.m98i(
              TAG,
              "setAlpha: mRectPath is empty, need to call updatePath mAlpha="
                  + this.mAlpha
                  + " BackgroundBlurDrawable@"
                  + hashCode()
                  + " Callers="
                  + Debug.getCallers(10));
        }
        updatePath();
      }
      invalidateSelf();
      this.mAggregator.onBlurDrawableUpdated(this);
    }
  }

  public void setBlurRadius(int blurRadius) {
    if (this.mBlurRadius != blurRadius) {
      this.mBlurRadius = blurRadius;
      invalidateSelf();
      this.mAggregator.onBlurDrawableUpdated(this);
    }
  }

  public void setBlurColorCurve(SemBlurInfo.ColorCurve colorCurve) {
    SemBlurInfo.ColorCurve colorCurve2 = this.mColorCurve;
    if (colorCurve2 == null || !colorCurve2.equals(colorCurve)) {
      this.mColorCurve = colorCurve;
      invalidateSelf();
      this.mAggregator.onBlurDrawableUpdated(this);
    }
  }

  public void setCornerRadius(float cornerRadius) {
    setCornerRadius(cornerRadius, cornerRadius, cornerRadius, cornerRadius);
  }

  public void setCornerRadius(
      float cornerRadiusTL, float cornerRadiusTR, float cornerRadiusBL, float cornerRadiusBR) {
    if (this.mCornerRadiusTL != cornerRadiusTL
        || this.mCornerRadiusTR != cornerRadiusTR
        || this.mCornerRadiusBL != cornerRadiusBL
        || this.mCornerRadiusBR != cornerRadiusBR) {
      this.mCornerRadiusTL = cornerRadiusTL;
      this.mCornerRadiusTR = cornerRadiusTR;
      this.mCornerRadiusBL = cornerRadiusBL;
      this.mCornerRadiusBR = cornerRadiusBR;
      updatePath();
      invalidateSelf();
      this.mAggregator.onBlurDrawableUpdated(this);
    }
  }

  @Override // android.graphics.drawable.Drawable
  public void setBounds(int left, int top, int right, int bottom) {
    super.setBounds(left, top, right, bottom);
    if (this.mShowDebug) {
      Log.m98i(
          TAG,
          "setBounds: BackgroundBlurDrawable@"
              + hashCode()
              + " setPosition("
              + left
              + ", "
              + top
              + ", "
              + right
              + ", "
              + bottom
              + NavigationBarInflaterView.KEY_CODE_END
              + " Callers="
              + Debug.getCallers(10));
    }
    this.mRenderNode.setPosition(left, top, right, bottom);
    updatePath();
  }

  private void updatePath() {
    float[] fArr = this.mTmpRadii;
    float f = this.mCornerRadiusTL;
    fArr[1] = f;
    fArr[0] = f;
    float f2 = this.mCornerRadiusTR;
    fArr[3] = f2;
    fArr[2] = f2;
    float f3 = this.mCornerRadiusBL;
    fArr[5] = f3;
    fArr[4] = f3;
    float f4 = this.mCornerRadiusBR;
    fArr[7] = f4;
    fArr[6] = f4;
    this.mRectPath.reset();
    if (getAlpha() == 0 || !isVisible()) {
      return;
    }
    Rect bounds = getBounds();
    this.mRectPath.addRoundRect(
        bounds.left, bounds.top, bounds.right, bounds.bottom, this.mTmpRadii, Path.Direction.CW);
  }

  @Override // android.graphics.drawable.Drawable
  public void setColorFilter(ColorFilter colorFilter) {
    throw new IllegalArgumentException("not implemented");
  }

  @Override // android.graphics.drawable.Drawable
  public int getOpacity() {
    return -3;
  }

  public String toString() {
    return "BackgroundBlurDrawable{@"
        + hashCode()
        + " blurRadius="
        + this.mBlurRadius
        + ", corners={"
        + this.mCornerRadiusTL
        + ","
        + this.mCornerRadiusTR
        + ","
        + this.mCornerRadiusBL
        + ","
        + this.mCornerRadiusBR
        + "}, alpha="
        + this.mAlpha
        + ", visible="
        + this.mVisible
        + ", rect="
        + this.mRect
        + (this.mColorCurve != null ? ", blurColorCurve=" + this.mColorCurve : "")
        + "}";
  }

  public static final class Aggregator {
    private boolean mHasUiUpdates;
    private ViewTreeObserver.OnPreDrawListener mOnPreDrawListener;
    private ViewRootImpl mViewRoot;
    private final Object mRtLock = new Object();
    private final ArraySet<BackgroundBlurDrawable> mDrawables = new ArraySet<>();
    private final LongSparseArray<ArraySet<Runnable>> mFrameRtUpdates = new LongSparseArray<>();
    private long mLastFrameNumber = 0;
    private BlurRegion[] mLastFrameBlurRegions = null;
    private BlurRegion[] mTmpBlurRegionsForFrame = new BlurRegion[0];

    public Aggregator(ViewRootImpl viewRoot) {
      setViewRoot(viewRoot);
    }

    public void setViewRoot(ViewRootImpl viewRoot) {
      this.mViewRoot = viewRoot;
    }

    public BackgroundBlurDrawable createBackgroundBlurDrawable(Context context) {
      BackgroundBlurDrawable drawable = new BackgroundBlurDrawable(this);
      drawable.setBlurRadius(
          context
              .getResources()
              .getDimensionPixelSize(C4337R.dimen.default_background_blur_radius));
      return drawable;
    }

    public BackgroundBlurDrawable createBackgroundBlurDrawable(Context context, boolean showDebug) {
      BackgroundBlurDrawable drawable = new BackgroundBlurDrawable(this, showDebug);
      drawable.setBlurRadius(
          context
              .getResources()
              .getDimensionPixelSize(C4337R.dimen.default_background_blur_radius));
      return drawable;
    }

    void onBlurDrawableUpdated(BackgroundBlurDrawable drawable) {
      ViewRootImpl viewRootImpl;
      boolean showDebug = false;
      boolean shouldBeDrawn =
          drawable.mAlpha != 0.0f && drawable.mBlurRadius > 0 && drawable.mVisible;
      boolean isDrawn = this.mDrawables.contains(drawable);
      if (drawable.isShowDebug() && ((shouldBeDrawn && !isDrawn) || (!shouldBeDrawn && isDrawn))) {
        showDebug = true;
      }
      if (BackgroundBlurDrawable.DEBUG || showDebug) {
        Log.m98i(
            BackgroundBlurDrawable.TAG,
            "onBlurDrawableUpdated BackgroundBlurDrawable@"
                + drawable.hashCode()
                + " rect="
                + drawable.mRect
                + " bounds="
                + drawable.getBounds()
                + ", renderNode w="
                + drawable.mRenderNode.getWidth()
                + " h="
                + drawable.mRenderNode.getHeight()
                + ", shouldBeDrawn="
                + shouldBeDrawn
                + ", isDrawn="
                + shouldBeDrawn
                + ", size="
                + this.mDrawables.size()
                + ", visible="
                + drawable.mVisible
                + ", Callers="
                + Debug.getCallers(5));
      }
      if (shouldBeDrawn) {
        this.mHasUiUpdates = true;
        if (!isDrawn) {
          this.mDrawables.add(drawable);
          if (showDebug) {
            Log.m98i(
                BackgroundBlurDrawable.TAG, "Add BackgroundBlurDrawable@" + drawable.hashCode());
          } else if (BackgroundBlurDrawable.DEBUG) {
            Log.m94d(BackgroundBlurDrawable.TAG, "Add " + drawable);
          }
        } else if (showDebug) {
          Log.m98i(
              BackgroundBlurDrawable.TAG, "Update BackgroundBlurDrawable@" + drawable.hashCode());
        } else if (BackgroundBlurDrawable.DEBUG) {
          Log.m94d(BackgroundBlurDrawable.TAG, "Update " + drawable);
        }
      } else if (!shouldBeDrawn && isDrawn) {
        this.mHasUiUpdates = true;
        this.mDrawables.remove(drawable);
        if (showDebug) {
          Log.m98i(
              BackgroundBlurDrawable.TAG, "Remove BackgroundBlurDrawable@" + drawable.hashCode());
        } else if (BackgroundBlurDrawable.DEBUG) {
          Log.m94d(BackgroundBlurDrawable.TAG, "Remove " + drawable);
        }
      }
      if (this.mOnPreDrawListener == null
          && (viewRootImpl = this.mViewRoot) != null
          && viewRootImpl.getView() != null
          && hasRegions()) {
        if (showDebug) {
          Log.m98i(BackgroundBlurDrawable.TAG, "registerPreDrawListener");
        }
        registerPreDrawListener(showDebug);
      }
    }

    private void registerPreDrawListener(final boolean showDebug) {
      this.mOnPreDrawListener =
          new ViewTreeObserver
              .OnPreDrawListener() { // from class:
                                     // com.android.internal.graphics.drawable.BackgroundBlurDrawable$Aggregator$$ExternalSyntheticLambda1
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public final boolean onPreDraw() {
              boolean lambda$registerPreDrawListener$1;
              lambda$registerPreDrawListener$1 =
                  BackgroundBlurDrawable.Aggregator.this.lambda$registerPreDrawListener$1(
                      showDebug);
              return lambda$registerPreDrawListener$1;
            }
          };
      this.mViewRoot.getView().getViewTreeObserver().addOnPreDrawListener(this.mOnPreDrawListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$registerPreDrawListener$1(boolean debug) {
      final boolean hasUiUpdates = hasUpdates();
      if (hasUiUpdates || hasRegions()) {
        final BlurRegion[] blurRegionsForNextFrame = getBlurRegionsCopyForRT();
        this.mViewRoot.registerRtFrameCallback(
            new HardwareRenderer
                .FrameDrawingCallback() { // from class:
                                          // com.android.internal.graphics.drawable.BackgroundBlurDrawable$Aggregator$$ExternalSyntheticLambda0
              @Override // android.graphics.HardwareRenderer.FrameDrawingCallback
              public final void onFrameDraw(long j) {
                BackgroundBlurDrawable.Aggregator.this.lambda$registerPreDrawListener$0(
                    blurRegionsForNextFrame, hasUiUpdates, j);
              }
            });
      }
      if (!hasRegions() && this.mViewRoot.getView() != null) {
        this.mViewRoot
            .getView()
            .getViewTreeObserver()
            .removeOnPreDrawListener(this.mOnPreDrawListener);
        this.mOnPreDrawListener = null;
        if (debug) {
          Log.m98i(BackgroundBlurDrawable.TAG, "removeOnPreDrawListener");
          return true;
        }
        return true;
      }
      return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerPreDrawListener$0(
        BlurRegion[] blurRegionsForNextFrame, boolean hasUiUpdates, long frame) {
      synchronized (this.mRtLock) {
        this.mLastFrameNumber = frame;
        this.mLastFrameBlurRegions = blurRegionsForNextFrame;
        handleDispatchBlurTransactionLocked(frame, blurRegionsForNextFrame, hasUiUpdates);
      }
    }

    void onRenderNodePositionChanged(long frameNumber, Runnable update) {
      synchronized (this.mRtLock) {
        ArraySet<Runnable> frameRtUpdates = this.mFrameRtUpdates.get(frameNumber);
        if (frameRtUpdates == null) {
          frameRtUpdates = new ArraySet<>();
          this.mFrameRtUpdates.put(frameNumber, frameRtUpdates);
        }
        frameRtUpdates.add(update);
        if (this.mLastFrameNumber == frameNumber) {
          handleDispatchBlurTransactionLocked(frameNumber, this.mLastFrameBlurRegions, true);
        }
      }
    }

    public boolean hasUpdates() {
      if (BackgroundBlurDrawable.DEBUG) {
        Log.m94d(BackgroundBlurDrawable.TAG, "hasUpdates " + this.mHasUiUpdates);
      }
      return this.mHasUiUpdates;
    }

    public boolean hasRegions() {
      if (BackgroundBlurDrawable.DEBUG) {
        Log.m94d(BackgroundBlurDrawable.TAG, "hasRegions " + this.mDrawables.size());
      }
      return this.mDrawables.size() > 0;
    }

    public BlurRegion[] getBlurRegionsCopyForRT() {
      if (this.mHasUiUpdates) {
        this.mTmpBlurRegionsForFrame = new BlurRegion[this.mDrawables.size()];
        for (int i = 0; i < this.mDrawables.size(); i++) {
          this.mTmpBlurRegionsForFrame[i] = new BlurRegion(this.mDrawables.valueAt(i));
        }
        this.mHasUiUpdates = false;
      }
      return this.mTmpBlurRegionsForFrame;
    }

    public float[][] getBlurRegionsForFrameLocked(
        long frameNumber, BlurRegion[] blurRegionsForFrame, boolean forceUpdate) {
      if (!forceUpdate
          && (this.mFrameRtUpdates.size() == 0 || this.mFrameRtUpdates.keyAt(0) > frameNumber)) {
        return null;
      }
      while (this.mFrameRtUpdates.size() != 0 && this.mFrameRtUpdates.keyAt(0) <= frameNumber) {
        ArraySet<Runnable> frameUpdates = this.mFrameRtUpdates.valueAt(0);
        this.mFrameRtUpdates.removeAt(0);
        for (int i = 0; i < frameUpdates.size(); i++) {
          frameUpdates.valueAt(i).run();
        }
      }
      if (BackgroundBlurDrawable.DEBUG) {
        Log.m94d(
            BackgroundBlurDrawable.TAG,
            "Dispatching " + blurRegionsForFrame.length + " blur regions:");
      }
      float[][] blurRegionsArray = new float[blurRegionsForFrame.length][];
      for (int i2 = 0; i2 < blurRegionsArray.length; i2++) {
        blurRegionsArray[i2] = blurRegionsForFrame[i2].toFloatArray();
        if (BackgroundBlurDrawable.DEBUG) {
          Log.m94d(BackgroundBlurDrawable.TAG, blurRegionsForFrame[i2].toString());
        }
      }
      return blurRegionsArray;
    }

    private void handleDispatchBlurTransactionLocked(
        long frameNumber, BlurRegion[] blurRegions, boolean forceUpdate) {
      ViewRootImpl viewRootImpl;
      float[][] blurRegionsArray =
          getBlurRegionsForFrameLocked(frameNumber, blurRegions, forceUpdate);
      if (blurRegionsArray != null && (viewRootImpl = this.mViewRoot) != null) {
        viewRootImpl.dispatchBlurRegions(blurRegionsArray, frameNumber);
      }
    }
  }

  public static final class BlurRegion {
    private static final int COLOR_CURVE_ITEM_SIZE = 16;
    private static final int DEFAULT_ITEM_SIZE = 10;
    public final float alpha;
    public final int blurRadius;
    public final SemBlurInfo.ColorCurve colorCurve;
    public final float cornerRadiusBL;
    public final float cornerRadiusBR;
    public final float cornerRadiusTL;
    public final float cornerRadiusTR;
    private String hashId = null;
    public final Rect rect;
    private boolean showDebug;

    BlurRegion(BackgroundBlurDrawable drawable) {
      this.showDebug = false;
      this.alpha = drawable.mAlpha;
      this.blurRadius = drawable.mBlurRadius;
      float scaleValue = drawable.mScaleX == drawable.mScaleY ? drawable.mScaleX : 1.0f;
      this.cornerRadiusTL = drawable.mCornerRadiusTL * scaleValue;
      this.cornerRadiusTR = drawable.mCornerRadiusTR * scaleValue;
      this.cornerRadiusBL = drawable.mCornerRadiusBL * scaleValue;
      this.cornerRadiusBR = drawable.mCornerRadiusBR * scaleValue;
      this.rect = drawable.mRect;
      this.colorCurve = drawable.mColorCurve;
      boolean isShowDebug = drawable.isShowDebug();
      this.showDebug = isShowDebug;
      if (isShowDebug) {
        Log.m98i(BackgroundBlurDrawable.TAG, "BlurRegion@" + hashCode() + " drawable=" + drawable);
      }
    }

    float[] toFloatArray() {
      SemBlurInfo.ColorCurve colorCurve;
      int size = this.colorCurve == null ? 10 : 16;
      float[] floatArray = new float[size];
      floatArray[0] = this.blurRadius;
      floatArray[1] = this.alpha;
      floatArray[2] = this.rect.left;
      floatArray[3] = this.rect.top;
      floatArray[4] = this.rect.right;
      floatArray[5] = this.rect.bottom;
      floatArray[6] = this.cornerRadiusTL;
      floatArray[7] = this.cornerRadiusTR;
      floatArray[8] = this.cornerRadiusBL;
      floatArray[9] = this.cornerRadiusBR;
      if (size == 16 && (colorCurve = this.colorCurve) != null) {
        floatArray[10] = colorCurve.mMinX;
        floatArray[11] = this.colorCurve.mMinY;
        floatArray[12] = this.colorCurve.mMaxX;
        floatArray[13] = this.colorCurve.mMaxY;
        floatArray[14] = this.colorCurve.mCurveBias;
        floatArray[15] = this.colorCurve.mSaturation;
      }
      if (this.showDebug) {
        Log.m98i(
            BackgroundBlurDrawable.TAG,
            "toFloatArray: BlurRegion@" + hashCode() + " rect=" + this.rect);
      }
      return floatArray;
    }

    public String toString() {
      return "BlurRegion{@"
          + hashCode()
          + " blurRadius="
          + this.blurRadius
          + ", corners={"
          + this.cornerRadiusTL
          + ","
          + this.cornerRadiusTR
          + ","
          + this.cornerRadiusBL
          + ","
          + this.cornerRadiusBR
          + "}, alpha="
          + this.alpha
          + ", rect="
          + this.rect
          + (this.colorCurve != null ? ", blurColorCurve=" + this.colorCurve : "")
          + "}";
    }
  }
}
