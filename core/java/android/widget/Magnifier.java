package android.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.BLASTBufferQueue;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Insets;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.RenderNode;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.PixelCopy;
import android.view.Surface;
import android.view.SurfaceControl;
import android.view.SurfaceHolder;
import android.view.SurfaceSession;
import android.view.SurfaceView;
import android.view.ThreadedRenderer;
import android.view.View;
import android.view.ViewRootImpl;

import com.android.internal.R;
import com.android.internal.util.Preconditions;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

public final class Magnifier {
    private static final float FISHEYE_RAMP_WIDTH = 12.0f;
    private static final int NONEXISTENT_PREVIOUS_CONFIG_VALUE = -1;
    public static final int SOURCE_BOUND_MAX_IN_SURFACE = 0;
    public static final int SOURCE_BOUND_MAX_VISIBLE = 1;
    private static final String TAG = "Magnifier";
    private static final HandlerThread sPixelCopyHandlerThread =
            new HandlerThread("magnifier pixel copy result handler");
    private int mBottomContentBound;
    private Callback mCallback;
    private final Point mClampedCenterZoomCoords;
    private final boolean mClippingEnabled;
    private SurfaceInfo mContentCopySurface;
    private Drawable mCursorDrawable;
    private final int mDefaultHorizontalSourceToMagnifierOffset;
    private final int mDefaultVerticalSourceToMagnifierOffset;
    private boolean mDirtyState;
    private boolean mDrawCursorEnabled;
    private boolean mIsFishEyeStyle;
    private int mLeftContentBound;
    private int mLeftCutWidth;
    private final Object mLock;
    private final Drawable mOverlay;
    private SurfaceInfo mParentSurface;
    private final Rect mPixelCopyRequestRect;
    private final PointF mPrevShowSourceCoords;
    private final PointF mPrevShowWindowCoords;
    private final Point mPrevStartCoordsInSurface;
    private final int mRamp;
    private int mRightContentBound;
    private int mRightCutWidth;
    private int mSourceHeight;
    private int mSourceWidth;
    private int mTopContentBound;
    private final View mView;
    private final int[] mViewCoordinatesInSurface;
    private InternalPopupWindow mWindow;
    private final Point mWindowCoords;
    private final float mWindowCornerRadius;
    private final float mWindowElevation;
    private int mWindowHeight;
    private final int mWindowWidth;
    private float mZoom;

    public interface Callback {
        void onOperationComplete();
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SourceBound {}

    static {
        sPixelCopyHandlerThread.start();
    }

    @Deprecated
    public Magnifier(View view) {
        this(createBuilderWithOldMagnifierDefaults(view));
    }

    static Builder createBuilderWithOldMagnifierDefaults(View view) {
        Builder params = new Builder(view);
        Context context = view.getContext();
        TypedArray a =
                context.obtainStyledAttributes(
                        null, R.styleable.Magnifier, R.attr.magnifierStyle, 0);
        params.mWidth = a.getDimensionPixelSize(5, 0);
        params.mHeight = a.getDimensionPixelSize(2, 0);
        params.mElevation = a.getDimension(1, 0.0f);
        params.mCornerRadius = getDeviceDefaultDialogCornerRadius(context);
        params.mZoom = a.getFloat(6, 0.0f);
        params.mHorizontalDefaultSourceToMagnifierOffset = a.getDimensionPixelSize(3, 0);
        params.mVerticalDefaultSourceToMagnifierOffset = a.getDimensionPixelSize(4, 0);
        params.mOverlay = new ColorDrawable(a.getColor(0, 0));
        a.recycle();
        params.mClippingEnabled = true;
        params.mLeftContentBound = 1;
        params.mTopContentBound = 0;
        params.mRightContentBound = 1;
        params.mBottomContentBound = 0;
        return params;
    }

    private static float getDeviceDefaultDialogCornerRadius(Context context) {
        Context deviceDefaultContext = new ContextThemeWrapper(context, 16974120);
        TypedArray ta = deviceDefaultContext.obtainStyledAttributes(new int[] {16844145});
        float dialogCornerRadius = ta.getDimension(0, 0.0f);
        ta.recycle();
        return dialogCornerRadius;
    }

    private Magnifier(Builder params) {
        this.mWindowCoords = new Point();
        this.mClampedCenterZoomCoords = new Point();
        this.mPrevStartCoordsInSurface = new Point(-1, -1);
        this.mPrevShowSourceCoords = new PointF(-1.0f, -1.0f);
        this.mPrevShowWindowCoords = new PointF(-1.0f, -1.0f);
        this.mPixelCopyRequestRect = new Rect();
        this.mLock = new Object();
        this.mLeftCutWidth = 0;
        this.mRightCutWidth = 0;
        this.mView = params.mView;
        this.mWindowWidth = params.mWidth;
        this.mWindowHeight = params.mHeight;
        this.mZoom = params.mZoom;
        this.mIsFishEyeStyle = params.mIsFishEyeStyle;
        if (params.mSourceWidth > 0 && params.mSourceHeight > 0) {
            this.mSourceWidth = params.mSourceWidth;
            this.mSourceHeight = params.mSourceHeight;
        } else {
            this.mSourceWidth = Math.round(this.mWindowWidth / this.mZoom);
            this.mSourceHeight = Math.round(this.mWindowHeight / this.mZoom);
        }
        this.mWindowElevation = params.mElevation;
        this.mWindowCornerRadius = params.mCornerRadius;
        this.mOverlay = params.mOverlay;
        this.mDefaultHorizontalSourceToMagnifierOffset =
                params.mHorizontalDefaultSourceToMagnifierOffset;
        this.mDefaultVerticalSourceToMagnifierOffset =
                params.mVerticalDefaultSourceToMagnifierOffset;
        this.mClippingEnabled = params.mClippingEnabled;
        this.mLeftContentBound = params.mLeftContentBound;
        this.mTopContentBound = params.mTopContentBound;
        this.mRightContentBound = params.mRightContentBound;
        this.mBottomContentBound = params.mBottomContentBound;
        this.mViewCoordinatesInSurface = new int[2];
        this.mRamp =
                (int)
                        TypedValue.applyDimension(
                                1,
                                FISHEYE_RAMP_WIDTH,
                                this.mView.getContext().getResources().getDisplayMetrics());
    }

    public void show(float sourceCenterX, float sourceCenterY) {
        show(
                sourceCenterX,
                sourceCenterY,
                this.mDefaultHorizontalSourceToMagnifierOffset + sourceCenterX,
                this.mDefaultVerticalSourceToMagnifierOffset + sourceCenterY);
    }

    void setDrawCursor(boolean enabled, Drawable cursorDrawable) {
        this.mDrawCursorEnabled = enabled;
        this.mCursorDrawable = cursorDrawable;
    }

    public void show(
            float sourceCenterX,
            float sourceCenterY,
            float magnifierCenterX,
            float magnifierCenterY) {
        float magnifierCenterX2;
        float magnifierCenterY2;
        int startX;
        float magnifierCenterX3;
        float magnifierCenterY3;
        Drawable drawable;
        float magnifierCenterX4;
        obtainSurfaces();
        obtainContentCoordinates(sourceCenterX, sourceCenterY);
        int startX2 = this.mClampedCenterZoomCoords.x - (this.mSourceWidth / 2);
        int startY = this.mClampedCenterZoomCoords.y - (this.mSourceHeight / 2);
        if (this.mIsFishEyeStyle) {
            float magnifierCenterX5 =
                    this.mClampedCenterZoomCoords.x - this.mViewCoordinatesInSurface[0];
            float magnifierCenterY4 =
                    this.mClampedCenterZoomCoords.y - this.mViewCoordinatesInSurface[1];
            float rampPre =
                    (this.mSourceWidth - ((this.mSourceWidth - (this.mRamp * 2)) / this.mZoom))
                            / 2.0f;
            float x0 = sourceCenterX - (this.mSourceWidth / 2.0f);
            float rampX0 = this.mRamp + x0;
            float leftEdge = 0.0f;
            if (0.0f > rampX0) {
                leftEdge = sourceCenterX - ((sourceCenterX - 0.0f) / this.mZoom);
            } else if (0.0f > x0) {
                leftEdge = (x0 + rampPre) - (((rampX0 - 0.0f) * rampPre) / this.mRamp);
            }
            int leftBound = Math.min((int) leftEdge, this.mView.getWidth());
            float x1 = sourceCenterX + (this.mSourceWidth / 2.0f);
            float rampX1 = x1 - this.mRamp;
            float rightEdge = this.mView.getWidth();
            if (rightEdge < rampX1) {
                rightEdge = sourceCenterX + ((rightEdge - sourceCenterX) / this.mZoom);
                magnifierCenterX4 = magnifierCenterX5;
            } else if (rightEdge < x1) {
                magnifierCenterX4 = magnifierCenterX5;
                rightEdge = (x1 - rampPre) + (((rightEdge - rampX1) * rampPre) / this.mRamp);
            } else {
                magnifierCenterX4 = magnifierCenterX5;
            }
            int rightBound = Math.max(leftBound, (int) rightEdge);
            int leftBound2 = Math.max(this.mViewCoordinatesInSurface[0] + leftBound, 0);
            int rightBound2 =
                    Math.min(
                            this.mViewCoordinatesInSurface[0] + rightBound,
                            this.mContentCopySurface.mWidth);
            this.mLeftCutWidth = Math.max(0, leftBound2 - startX2);
            this.mRightCutWidth = Math.max(0, (this.mSourceWidth + startX2) - rightBound2);
            magnifierCenterX2 = magnifierCenterX4;
            startX = Math.max(startX2, leftBound2);
            magnifierCenterY2 = magnifierCenterY4;
        } else {
            magnifierCenterX2 = magnifierCenterX;
            magnifierCenterY2 = magnifierCenterY;
            startX = startX2;
        }
        obtainWindowCoordinates(magnifierCenterX2, magnifierCenterY2);
        if (sourceCenterX != this.mPrevShowSourceCoords.x
                || sourceCenterY != this.mPrevShowSourceCoords.y
                || this.mDirtyState) {
            if (this.mWindow != null) {
                magnifierCenterX3 = magnifierCenterX2;
                magnifierCenterY3 = magnifierCenterY2;
            } else {
                synchronized (this.mLock) {
                    try {
                        try {
                            Context context = this.mView.getContext();
                            Display display = this.mView.getDisplay();
                            SurfaceControl surfaceControl = this.mParentSurface.mSurfaceControl;
                            int i = this.mWindowWidth;
                            int i2 = this.mWindowHeight;
                            float f = this.mZoom;
                            int i3 = this.mRamp;
                            float f2 = this.mWindowElevation;
                            float f3 = this.mWindowCornerRadius;
                            if (this.mOverlay != null) {
                                try {
                                    magnifierCenterY3 = magnifierCenterY2;
                                    drawable = this.mOverlay;
                                } catch (Throwable th) {
                                    th = th;
                                    throw th;
                                }
                            } else {
                                magnifierCenterY3 = magnifierCenterY2;
                                try {
                                    drawable = new ColorDrawable(0);
                                } catch (Throwable th2) {
                                    th = th2;
                                    throw th;
                                }
                            }
                            magnifierCenterX3 = magnifierCenterX2;
                            this.mWindow =
                                    new InternalPopupWindow(
                                            context,
                                            display,
                                            surfaceControl,
                                            i,
                                            i2,
                                            f,
                                            i3,
                                            f2,
                                            f3,
                                            drawable,
                                            Handler.getMain(),
                                            this.mLock,
                                            this.mCallback,
                                            this.mIsFishEyeStyle);
                        } catch (Throwable th3) {
                            th = th3;
                        }
                    } catch (Throwable th4) {
                        th = th4;
                    }
                }
            }
            performPixelCopy(startX, startY, true);
        } else if (magnifierCenterX2 == this.mPrevShowWindowCoords.x
                && magnifierCenterY2 == this.mPrevShowWindowCoords.y) {
            magnifierCenterX3 = magnifierCenterX2;
            magnifierCenterY3 = magnifierCenterY2;
        } else {
            final Point windowCoords = getCurrentClampedWindowCoordinates();
            final InternalPopupWindow currentWindowInstance = this.mWindow;
            sPixelCopyHandlerThread
                    .getThreadHandler()
                    .post(
                            new Runnable() { // from class:
                                             // android.widget.Magnifier$$ExternalSyntheticLambda2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    Magnifier.this.lambda$show$0(
                                            currentWindowInstance, windowCoords);
                                }
                            });
            magnifierCenterX3 = magnifierCenterX2;
            magnifierCenterY3 = magnifierCenterY2;
        }
        this.mPrevShowSourceCoords.x = sourceCenterX;
        this.mPrevShowSourceCoords.y = sourceCenterY;
        this.mPrevShowWindowCoords.x = magnifierCenterX3;
        this.mPrevShowWindowCoords.y = magnifierCenterY3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$show$0(
            InternalPopupWindow currentWindowInstance, Point windowCoords) {
        synchronized (this.mLock) {
            if (this.mWindow != currentWindowInstance) {
                return;
            }
            this.mWindow.setContentPositionForNextDraw(windowCoords.x, windowCoords.y);
        }
    }

    public void dismiss() {
        if (this.mWindow != null) {
            synchronized (this.mLock) {
                this.mWindow.destroy();
                this.mWindow = null;
            }
            this.mPrevShowSourceCoords.x = -1.0f;
            this.mPrevShowSourceCoords.y = -1.0f;
            this.mPrevShowWindowCoords.x = -1.0f;
            this.mPrevShowWindowCoords.y = -1.0f;
            this.mPrevStartCoordsInSurface.x = -1;
            this.mPrevStartCoordsInSurface.y = -1;
        }
    }

    public void update() {
        if (this.mWindow != null) {
            obtainSurfaces();
            if (!this.mDirtyState) {
                performPixelCopy(
                        this.mPrevStartCoordsInSurface.x, this.mPrevStartCoordsInSurface.y, false);
            } else {
                show(
                        this.mPrevShowSourceCoords.x,
                        this.mPrevShowSourceCoords.y,
                        this.mPrevShowWindowCoords.x,
                        this.mPrevShowWindowCoords.y);
            }
        }
    }

    public int getWidth() {
        return this.mWindowWidth;
    }

    public int getHeight() {
        return this.mWindowHeight;
    }

    public int getSourceWidth() {
        return this.mSourceWidth;
    }

    public int getSourceHeight() {
        return this.mSourceHeight;
    }

    public void setZoom(float zoom) {
        Preconditions.checkArgumentPositive(zoom, "Zoom should be positive");
        this.mZoom = zoom;
        this.mSourceWidth =
                this.mIsFishEyeStyle
                        ? this.mWindowWidth
                        : Math.round(this.mWindowWidth / this.mZoom);
        this.mSourceHeight = Math.round(this.mWindowHeight / this.mZoom);
        this.mDirtyState = true;
    }

    void updateSourceFactors(int sourceHeight, float zoom) {
        this.mZoom = zoom;
        this.mSourceHeight = sourceHeight;
        this.mWindowHeight = (int) (sourceHeight * zoom);
        if (this.mWindow != null) {
            this.mWindow.updateContentFactors(this.mWindowHeight, zoom);
        }
    }

    public float getZoom() {
        return this.mZoom;
    }

    public float getElevation() {
        return this.mWindowElevation;
    }

    public float getCornerRadius() {
        return this.mWindowCornerRadius;
    }

    public int getDefaultHorizontalSourceToMagnifierOffset() {
        return this.mDefaultHorizontalSourceToMagnifierOffset;
    }

    public int getDefaultVerticalSourceToMagnifierOffset() {
        return this.mDefaultVerticalSourceToMagnifierOffset;
    }

    public Drawable getOverlay() {
        return this.mOverlay;
    }

    public boolean isClippingEnabled() {
        return this.mClippingEnabled;
    }

    public Point getPosition() {
        if (this.mWindow == null) {
            return null;
        }
        Point position = getCurrentClampedWindowCoordinates();
        position.offset(-this.mParentSurface.mInsets.left, -this.mParentSurface.mInsets.top);
        return new Point(position);
    }

    public Point getSourcePosition() {
        if (this.mWindow == null) {
            return null;
        }
        Point position = new Point(this.mPixelCopyRequestRect.left, this.mPixelCopyRequestRect.top);
        position.offset(
                -this.mContentCopySurface.mInsets.left, -this.mContentCopySurface.mInsets.top);
        return new Point(position);
    }

    private void obtainSurfaces() {
        ViewRootImpl viewRootImpl;
        Surface mainWindowSurface;
        SurfaceInfo validMainWindowSurface = SurfaceInfo.NULL;
        if (this.mView.getViewRootImpl() != null
                && (mainWindowSurface = (viewRootImpl = this.mView.getViewRootImpl()).mSurface)
                        != null
                && mainWindowSurface.isValid()) {
            Rect surfaceInsets = viewRootImpl.mWindowAttributes.surfaceInsets;
            int surfaceWidth = viewRootImpl.getWidth() + surfaceInsets.left + surfaceInsets.right;
            int surfaceHeight = viewRootImpl.getHeight() + surfaceInsets.top + surfaceInsets.bottom;
            validMainWindowSurface =
                    new SurfaceInfo(
                            viewRootImpl.getSurfaceControl(),
                            mainWindowSurface,
                            surfaceWidth,
                            surfaceHeight,
                            surfaceInsets,
                            true);
        }
        SurfaceInfo validSurfaceViewSurface = SurfaceInfo.NULL;
        if (this.mView instanceof SurfaceView) {
            SurfaceControl sc = ((SurfaceView) this.mView).getSurfaceControl();
            SurfaceHolder surfaceHolder = ((SurfaceView) this.mView).getHolder();
            Surface surfaceViewSurface = surfaceHolder.getSurface();
            if (sc != null && sc.isValid()) {
                Rect surfaceFrame = surfaceHolder.getSurfaceFrame();
                validSurfaceViewSurface =
                        new SurfaceInfo(
                                sc,
                                surfaceViewSurface,
                                surfaceFrame.right,
                                surfaceFrame.bottom,
                                new Rect(),
                                false);
            }
        }
        this.mParentSurface =
                validMainWindowSurface != SurfaceInfo.NULL
                        ? validMainWindowSurface
                        : validSurfaceViewSurface;
        this.mContentCopySurface =
                this.mView instanceof SurfaceView
                        ? validSurfaceViewSurface
                        : validMainWindowSurface;
    }

    private void obtainContentCoordinates(float xPosInView, float yPosInView) {
        int zoomCenterX;
        int zoomCenterY;
        int max;
        int prevViewXInSurface = this.mViewCoordinatesInSurface[0];
        int prevViewYInSurface = this.mViewCoordinatesInSurface[1];
        this.mView.getLocationInSurface(this.mViewCoordinatesInSurface);
        if (this.mViewCoordinatesInSurface[0] != prevViewXInSurface
                || this.mViewCoordinatesInSurface[1] != prevViewYInSurface) {
            this.mDirtyState = true;
        }
        if (this.mView instanceof SurfaceView) {
            zoomCenterX = Math.round(xPosInView);
            zoomCenterY = Math.round(yPosInView);
        } else {
            zoomCenterX = Math.round(xPosInView + this.mViewCoordinatesInSurface[0]);
            zoomCenterY = Math.round(yPosInView + this.mViewCoordinatesInSurface[1]);
        }
        Rect[] bounds = new Rect[2];
        Rect surfaceBounds =
                new Rect(0, 0, this.mContentCopySurface.mWidth, this.mContentCopySurface.mHeight);
        bounds[0] = surfaceBounds;
        Rect viewVisibleRegion = new Rect();
        this.mView.getGlobalVisibleRect(viewVisibleRegion);
        if (this.mView.getViewRootImpl() != null) {
            Rect surfaceInsets = this.mView.getViewRootImpl().mWindowAttributes.surfaceInsets;
            viewVisibleRegion.offset(surfaceInsets.left, surfaceInsets.top);
        }
        if (this.mView instanceof SurfaceView) {
            viewVisibleRegion.offset(
                    -this.mViewCoordinatesInSurface[0], -this.mViewCoordinatesInSurface[1]);
        }
        bounds[1] = viewVisibleRegion;
        int resolvedLeft = Integer.MIN_VALUE;
        for (int i = this.mLeftContentBound; i >= 0; i--) {
            resolvedLeft = Math.max(resolvedLeft, bounds[i].left);
        }
        int resolvedTop = Integer.MIN_VALUE;
        for (int i2 = this.mTopContentBound; i2 >= 0; i2--) {
            resolvedTop = Math.max(resolvedTop, bounds[i2].top);
        }
        int resolvedRight = Integer.MAX_VALUE;
        for (int i3 = this.mRightContentBound; i3 >= 0; i3--) {
            resolvedRight = Math.min(resolvedRight, bounds[i3].right);
        }
        int resolvedBottom = Integer.MAX_VALUE;
        for (int i4 = this.mBottomContentBound; i4 >= 0; i4--) {
            resolvedBottom = Math.min(resolvedBottom, bounds[i4].bottom);
        }
        int resolvedLeft2 =
                Math.min(resolvedLeft, this.mContentCopySurface.mWidth - this.mSourceWidth);
        int resolvedTop2 =
                Math.min(resolvedTop, this.mContentCopySurface.mHeight - this.mSourceHeight);
        if (resolvedLeft2 < 0 || resolvedTop2 < 0) {
            Log.e(
                    TAG,
                    "Magnifier's content is copied from a surface smaller thanthe content requested"
                        + " size. The magnifier will be dismissed.");
        }
        int resolvedRight2 = Math.max(resolvedRight, this.mSourceWidth + resolvedLeft2);
        int resolvedBottom2 = Math.max(resolvedBottom, this.mSourceHeight + resolvedTop2);
        Point point = this.mClampedCenterZoomCoords;
        if (!this.mIsFishEyeStyle) {
            max =
                    Math.max(
                            (this.mSourceWidth / 2) + resolvedLeft2,
                            Math.min(zoomCenterX, resolvedRight2 - (this.mSourceWidth / 2)));
        } else {
            max = Math.max(resolvedLeft2, Math.min(zoomCenterX, resolvedRight2));
        }
        point.x = max;
        this.mClampedCenterZoomCoords.y =
                Math.max(
                        (this.mSourceHeight / 2) + resolvedTop2,
                        Math.min(zoomCenterY, resolvedBottom2 - (this.mSourceHeight / 2)));
    }

    private void obtainWindowCoordinates(float xWindowPos, float yWindowPos) {
        int windowCenterX;
        int windowCenterY;
        if (this.mView instanceof SurfaceView) {
            windowCenterX = Math.round(xWindowPos);
            windowCenterY = Math.round(yWindowPos);
        } else {
            windowCenterX = Math.round(this.mViewCoordinatesInSurface[0] + xWindowPos);
            windowCenterY = Math.round(this.mViewCoordinatesInSurface[1] + yWindowPos);
        }
        this.mWindowCoords.x = windowCenterX - (this.mWindowWidth / 2);
        this.mWindowCoords.y = windowCenterY - (this.mWindowHeight / 2);
        if (this.mParentSurface != this.mContentCopySurface) {
            this.mWindowCoords.x += this.mViewCoordinatesInSurface[0];
            this.mWindowCoords.y += this.mViewCoordinatesInSurface[1];
        }
    }

    private void maybeDrawCursor(Canvas canvas) {
        if (this.mDrawCursorEnabled) {
            if (this.mCursorDrawable != null) {
                this.mCursorDrawable.setBounds(
                        this.mSourceWidth / 2,
                        0,
                        (this.mSourceWidth / 2) + this.mCursorDrawable.getIntrinsicWidth(),
                        this.mSourceHeight);
                this.mCursorDrawable.draw(canvas);
            } else {
                Paint paint = new Paint();
                paint.setColor(-16777216);
                canvas.drawRect(
                        new Rect(
                                (this.mSourceWidth / 2) - 1,
                                0,
                                (this.mSourceWidth / 2) + 1,
                                this.mSourceHeight),
                        paint);
            }
        }
    }

    private void performPixelCopy(
            int startXInSurface, int startYInSurface, final boolean updateWindowPosition) {
        if (this.mContentCopySurface.mSurface == null
                || !this.mContentCopySurface.mSurface.isValid()) {
            onPixelCopyFailed();
            return;
        }
        final Point windowCoords = getCurrentClampedWindowCoordinates();
        this.mPixelCopyRequestRect.set(
                startXInSurface,
                startYInSurface,
                ((this.mSourceWidth + startXInSurface) - this.mLeftCutWidth) - this.mRightCutWidth,
                this.mSourceHeight + startYInSurface);
        this.mPrevStartCoordsInSurface.x = startXInSurface;
        this.mPrevStartCoordsInSurface.y = startYInSurface;
        this.mDirtyState = false;
        final InternalPopupWindow currentWindowInstance = this.mWindow;
        if (this.mPixelCopyRequestRect.width() == 0) {
            this.mWindow.updateContent(
                    Bitmap.createBitmap(
                            this.mSourceWidth, this.mSourceHeight, Bitmap.Config.ALPHA_8));
        } else {
            final Bitmap bitmap =
                    Bitmap.createBitmap(
                            (this.mSourceWidth - this.mLeftCutWidth) - this.mRightCutWidth,
                            this.mSourceHeight,
                            Bitmap.Config.ARGB_8888);
            PixelCopy.request(
                    this.mContentCopySurface.mSurface,
                    this.mPixelCopyRequestRect,
                    bitmap,
                    new PixelCopy
                            .OnPixelCopyFinishedListener() { // from class:
                                                             // android.widget.Magnifier$$ExternalSyntheticLambda0
                        @Override // android.view.PixelCopy.OnPixelCopyFinishedListener
                        public final void onPixelCopyFinished(int i) {
                            Magnifier.this.lambda$performPixelCopy$1(
                                    currentWindowInstance,
                                    updateWindowPosition,
                                    windowCoords,
                                    bitmap,
                                    i);
                        }
                    },
                    sPixelCopyHandlerThread.getThreadHandler());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$performPixelCopy$1(
            InternalPopupWindow currentWindowInstance,
            boolean updateWindowPosition,
            Point windowCoords,
            Bitmap bitmap,
            int result) {
        if (result != 0) {
            onPixelCopyFailed();
            return;
        }
        synchronized (this.mLock) {
            if (this.mWindow != currentWindowInstance) {
                return;
            }
            if (updateWindowPosition) {
                this.mWindow.setContentPositionForNextDraw(windowCoords.x, windowCoords.y);
            }
            if (bitmap.getWidth() < this.mSourceWidth) {
                Bitmap newBitmap =
                        Bitmap.createBitmap(
                                this.mSourceWidth, bitmap.getHeight(), bitmap.getConfig());
                Canvas can = new Canvas(newBitmap);
                Rect dstRect =
                        new Rect(
                                this.mLeftCutWidth,
                                0,
                                this.mSourceWidth - this.mRightCutWidth,
                                bitmap.getHeight());
                can.drawBitmap(bitmap, (Rect) null, dstRect, (Paint) null);
                maybeDrawCursor(can);
                this.mWindow.updateContent(newBitmap);
            } else {
                maybeDrawCursor(new Canvas(bitmap));
                this.mWindow.updateContent(bitmap);
            }
        }
    }

    private void onPixelCopyFailed() {
        Log.e(TAG, "Magnifier failed to copy content from the view Surface. It will be dismissed.");
        Handler.getMain()
                .postAtFrontOfQueue(
                        new Runnable() { // from class:
                                         // android.widget.Magnifier$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                Magnifier.this.lambda$onPixelCopyFailed$2();
                            }
                        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onPixelCopyFailed$2() {
        dismiss();
        if (this.mCallback != null) {
            this.mCallback.onOperationComplete();
        }
    }

    private Point getCurrentClampedWindowCoordinates() {
        Rect windowBounds;
        if (!this.mClippingEnabled) {
            return new Point(this.mWindowCoords);
        }
        if (this.mParentSurface.mIsMainWindowSurface) {
            Insets systemInsets = this.mView.getRootWindowInsets().getSystemWindowInsets();
            windowBounds =
                    new Rect(
                            systemInsets.left + this.mParentSurface.mInsets.left,
                            systemInsets.top + this.mParentSurface.mInsets.top,
                            (this.mParentSurface.mWidth - systemInsets.right)
                                    - this.mParentSurface.mInsets.right,
                            (this.mParentSurface.mHeight - systemInsets.bottom)
                                    - this.mParentSurface.mInsets.bottom);
        } else {
            windowBounds = new Rect(0, 0, this.mParentSurface.mWidth, this.mParentSurface.mHeight);
        }
        int windowCoordsX =
                Math.max(
                        windowBounds.left,
                        Math.min(windowBounds.right - this.mWindowWidth, this.mWindowCoords.x));
        int windowCoordsY =
                Math.max(
                        windowBounds.top,
                        Math.min(windowBounds.bottom - this.mWindowHeight, this.mWindowCoords.y));
        return new Point(windowCoordsX, windowCoordsY);
    }

    private static class SurfaceInfo {
        public static final SurfaceInfo NULL = new SurfaceInfo(null, null, 0, 0, null, false);
        private int mHeight;
        private Rect mInsets;
        private boolean mIsMainWindowSurface;
        private Surface mSurface;
        private SurfaceControl mSurfaceControl;
        private int mWidth;

        SurfaceInfo(
                SurfaceControl surfaceControl,
                Surface surface,
                int width,
                int height,
                Rect insets,
                boolean isMainWindowSurface) {
            this.mSurfaceControl = surfaceControl;
            this.mSurface = surface;
            this.mWidth = width;
            this.mHeight = height;
            this.mInsets = insets;
            this.mIsMainWindowSurface = isMainWindowSurface;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class InternalPopupWindow {
        private static final int SURFACE_Z = 5;
        private final BLASTBufferQueue mBBQ;
        private final SurfaceControl mBbqSurfaceControl;
        private Bitmap mBitmap;
        private final RenderNode mBitmapRenderNode;
        private Callback mCallback;
        private int mContentHeight;
        private final int mContentWidth;
        private Bitmap mCurrentContent;
        private final Display mDisplay;
        private boolean mFrameDrawScheduled;
        private final Handler mHandler;
        private boolean mIsFishEyeStyle;
        private final Object mLock;
        private final Runnable mMagnifierUpdater;
        private int mMeshHeight;
        private float[] mMeshLeft;
        private float[] mMeshRight;
        private int mMeshWidth;
        private final int mOffsetX;
        private final int mOffsetY;
        private final Drawable mOverlay;
        private final RenderNode mOverlayRenderNode;
        private boolean mPendingWindowPositionUpdate;
        private final int mRamp;
        private final ThreadedRenderer.SimpleRenderer mRenderer;
        private final Surface mSurface;
        private final SurfaceControl mSurfaceControl;
        private final SurfaceSession mSurfaceSession;
        private int mWindowPositionX;
        private int mWindowPositionY;
        private float mZoom;
        private final SurfaceControl.Transaction mTransaction = new SurfaceControl.Transaction();
        private boolean mFirstDraw = true;

        InternalPopupWindow(
                Context context,
                Display display,
                SurfaceControl parentSurfaceControl,
                int width,
                int height,
                float zoom,
                int ramp,
                float elevation,
                float cornerRadius,
                Drawable overlay,
                Handler handler,
                Object lock,
                Callback callback,
                boolean isFishEyeStyle) {
            this.mDisplay = display;
            this.mOverlay = overlay;
            this.mLock = lock;
            this.mCallback = callback;
            this.mContentWidth = width;
            this.mContentHeight = height;
            this.mZoom = zoom;
            this.mRamp = ramp;
            this.mOffsetX = (int) (elevation * 1.05f);
            this.mOffsetY = (int) (1.05f * elevation);
            int surfaceWidth = (this.mOffsetX * 2) + this.mContentWidth;
            int surfaceHeight = this.mContentHeight + (this.mOffsetY * 2);
            this.mSurfaceSession = new SurfaceSession();
            this.mSurfaceControl =
                    new SurfaceControl.Builder(this.mSurfaceSession)
                            .setName("magnifier surface")
                            .setFlags(4)
                            .setContainerLayer()
                            .setParent(parentSurfaceControl)
                            .setCallsite("InternalPopupWindow")
                            .build();
            this.mBbqSurfaceControl =
                    new SurfaceControl.Builder(this.mSurfaceSession)
                            .setName("magnifier surface bbq wrapper")
                            .setHidden(false)
                            .setBLASTLayer()
                            .setParent(this.mSurfaceControl)
                            .setCallsite("InternalPopupWindow")
                            .build();
            this.mBBQ =
                    new BLASTBufferQueue(
                            "magnifier surface",
                            this.mBbqSurfaceControl,
                            surfaceWidth,
                            surfaceHeight,
                            -3);
            this.mSurface = this.mBBQ.createSurface();
            this.mRenderer =
                    new ThreadedRenderer.SimpleRenderer(
                            context, "magnifier renderer", this.mSurface);
            this.mBitmapRenderNode =
                    createRenderNodeForBitmap("magnifier content", elevation, cornerRadius);
            this.mOverlayRenderNode = createRenderNodeForOverlay("magnifier overlay", cornerRadius);
            setupOverlay();
            RecordingCanvas canvas = this.mRenderer.getRootNode().beginRecording(width, height);
            try {
                canvas.enableZ();
                canvas.drawRenderNode(this.mBitmapRenderNode);
                canvas.disableZ();
                canvas.drawRenderNode(this.mOverlayRenderNode);
                canvas.disableZ();
                this.mRenderer.getRootNode().endRecording();
                if (this.mCallback != null) {
                    this.mCurrentContent =
                            Bitmap.createBitmap(
                                    this.mContentWidth,
                                    this.mContentHeight,
                                    Bitmap.Config.ARGB_8888);
                    updateCurrentContentForTesting();
                }
                this.mHandler = handler;
                this.mMagnifierUpdater =
                        new Runnable() { // from class:
                                         // android.widget.Magnifier$InternalPopupWindow$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                Magnifier.InternalPopupWindow.this.doDraw();
                            }
                        };
                this.mFrameDrawScheduled = false;
                this.mIsFishEyeStyle = isFishEyeStyle;
                if (this.mIsFishEyeStyle) {
                    createMeshMatrixForFishEyeEffect();
                }
            } catch (Throwable th) {
                this.mRenderer.getRootNode().endRecording();
                throw th;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updateContentFactors(int contentHeight, float zoom) {
            if (this.mContentHeight == contentHeight && this.mZoom == zoom) {
                return;
            }
            if (this.mContentHeight < contentHeight) {
                this.mBBQ.update(this.mBbqSurfaceControl, this.mContentWidth, contentHeight, -3);
                this.mRenderer.setSurface(this.mSurface);
                Outline outline = new Outline();
                outline.setRoundRect(0, 0, this.mContentWidth, contentHeight, 0.0f);
                outline.setAlpha(1.0f);
                this.mBitmapRenderNode.setLeftTopRightBottom(
                        this.mOffsetX,
                        this.mOffsetY,
                        this.mOffsetX + this.mContentWidth,
                        this.mOffsetY + contentHeight);
                this.mBitmapRenderNode.setOutline(outline);
                this.mOverlayRenderNode.setLeftTopRightBottom(
                        this.mOffsetX,
                        this.mOffsetY,
                        this.mOffsetX + this.mContentWidth,
                        this.mOffsetY + contentHeight);
                this.mOverlayRenderNode.setOutline(outline);
                RecordingCanvas canvas =
                        this.mRenderer
                                .getRootNode()
                                .beginRecording(this.mContentWidth, contentHeight);
                try {
                    canvas.enableZ();
                    canvas.drawRenderNode(this.mBitmapRenderNode);
                    canvas.disableZ();
                    canvas.drawRenderNode(this.mOverlayRenderNode);
                    canvas.disableZ();
                } finally {
                    this.mRenderer.getRootNode().endRecording();
                }
            }
            this.mContentHeight = contentHeight;
            this.mZoom = zoom;
            fillMeshMatrix();
        }

        private void createMeshMatrixForFishEyeEffect() {
            this.mMeshWidth = 1;
            this.mMeshHeight = 6;
            this.mMeshLeft = new float[(this.mMeshWidth + 1) * 2 * (this.mMeshHeight + 1)];
            this.mMeshRight = new float[(this.mMeshWidth + 1) * 2 * (this.mMeshHeight + 1)];
            fillMeshMatrix();
        }

        private void fillMeshMatrix() {
            this.mMeshWidth = 1;
            this.mMeshHeight = 6;
            float w = this.mContentWidth;
            float h = this.mContentHeight;
            float h0 = h / this.mZoom;
            float dh = h - h0;
            int i = 0;
            for (int i2 = 1; i < (this.mMeshWidth + i2) * 2 * (this.mMeshHeight + i2); i2 = 1) {
                int colIndex = (i % ((this.mMeshWidth + i2) * 2)) / 2;
                this.mMeshLeft[i] = (colIndex * this.mRamp) / this.mMeshWidth;
                this.mMeshRight[i] = (w - this.mRamp) + ((this.mRamp * colIndex) / this.mMeshWidth);
                int rowIndex = (i / 2) / (this.mMeshWidth + i2);
                float hl = ((colIndex * dh) / this.mMeshWidth) + h0;
                float yl = (h - hl) / 2.0f;
                this.mMeshLeft[i + 1] = ((rowIndex * hl) / this.mMeshHeight) + yl;
                float hr = h - ((colIndex * dh) / this.mMeshWidth);
                float yr = (h - hr) / 2.0f;
                this.mMeshRight[i + 1] = ((rowIndex * hr) / this.mMeshHeight) + yr;
                i += 2;
            }
        }

        private RenderNode createRenderNodeForBitmap(
                String name, float elevation, float cornerRadius) {
            RenderNode bitmapRenderNode = RenderNode.create(name, null);
            bitmapRenderNode.setLeftTopRightBottom(
                    this.mOffsetX,
                    this.mOffsetY,
                    this.mOffsetX + this.mContentWidth,
                    this.mOffsetY + this.mContentHeight);
            bitmapRenderNode.setElevation(elevation);
            Outline outline = new Outline();
            outline.setRoundRect(0, 0, this.mContentWidth, this.mContentHeight, cornerRadius);
            outline.setAlpha(1.0f);
            bitmapRenderNode.setOutline(outline);
            bitmapRenderNode.setClipToOutline(true);
            RecordingCanvas canvas =
                    bitmapRenderNode.beginRecording(this.mContentWidth, this.mContentHeight);
            try {
                canvas.drawColor(Color.GREEN);
                return bitmapRenderNode;
            } finally {
                bitmapRenderNode.endRecording();
            }
        }

        private RenderNode createRenderNodeForOverlay(String name, float cornerRadius) {
            RenderNode overlayRenderNode = RenderNode.create(name, null);
            overlayRenderNode.setLeftTopRightBottom(
                    this.mOffsetX,
                    this.mOffsetY,
                    this.mOffsetX + this.mContentWidth,
                    this.mOffsetY + this.mContentHeight);
            Outline outline = new Outline();
            outline.setRoundRect(0, 0, this.mContentWidth, this.mContentHeight, cornerRadius);
            outline.setAlpha(1.0f);
            overlayRenderNode.setOutline(outline);
            overlayRenderNode.setClipToOutline(true);
            return overlayRenderNode;
        }

        private void setupOverlay() {
            drawOverlay();
            this.mOverlay.setCallback(
                    new Drawable
                            .Callback() { // from class:
                                          // android.widget.Magnifier.InternalPopupWindow.1
                        @Override // android.graphics.drawable.Drawable.Callback
                        public void invalidateDrawable(Drawable who) {
                            InternalPopupWindow.this.drawOverlay();
                            if (InternalPopupWindow.this.mCallback != null) {
                                InternalPopupWindow.this.updateCurrentContentForTesting();
                            }
                        }

                        @Override // android.graphics.drawable.Drawable.Callback
                        public void scheduleDrawable(Drawable who, Runnable what, long when) {
                            Handler.getMain().postAtTime(what, who, when);
                        }

                        @Override // android.graphics.drawable.Drawable.Callback
                        public void unscheduleDrawable(Drawable who, Runnable what) {
                            Handler.getMain().removeCallbacks(what, who);
                        }
                    });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void drawOverlay() {
            RecordingCanvas canvas =
                    this.mOverlayRenderNode.beginRecording(this.mContentWidth, this.mContentHeight);
            try {
                this.mOverlay.setBounds(0, 0, this.mContentWidth, this.mContentHeight);
                this.mOverlay.draw(canvas);
            } finally {
                this.mOverlayRenderNode.endRecording();
            }
        }

        public void setContentPositionForNextDraw(int contentX, int contentY) {
            this.mWindowPositionX = contentX - this.mOffsetX;
            this.mWindowPositionY = contentY - this.mOffsetY;
            this.mPendingWindowPositionUpdate = true;
            requestUpdate();
        }

        public void updateContent(Bitmap bitmap) {
            if (this.mBitmap != null) {
                this.mBitmap.recycle();
            }
            this.mBitmap = bitmap;
            requestUpdate();
        }

        private void requestUpdate() {
            if (this.mFrameDrawScheduled) {
                return;
            }
            Message request = Message.obtain(this.mHandler, this.mMagnifierUpdater);
            request.setAsynchronous(true);
            request.sendToTarget();
            this.mFrameDrawScheduled = true;
        }

        public void destroy() {
            this.mRenderer.destroy();
            this.mSurface.destroy();
            this.mBBQ.destroy();
            new SurfaceControl.Transaction()
                    .remove(this.mSurfaceControl)
                    .remove(this.mBbqSurfaceControl)
                    .apply();
            this.mSurfaceSession.kill();
            this.mHandler.removeCallbacks(this.mMagnifierUpdater);
            if (this.mBitmap != null) {
                this.mBitmap.recycle();
            }
            this.mOverlay.setCallback(null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Removed duplicated region for block: B:26:0x00f4  */
        /* JADX WARN: Removed duplicated region for block: B:28:? A[RETURN, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void doDraw() {
            /*
                Method dump skipped, instructions count: 265
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " android.widget.Magnifier.InternalPopupWindow.doDraw():void");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$doDraw$0(
                boolean updateWindowPosition,
                int pendingX,
                int pendingY,
                boolean firstDraw,
                long frame) {
            if (!this.mSurface.isValid()) {
                return;
            }
            if (updateWindowPosition) {
                this.mTransaction.setPosition(this.mSurfaceControl, pendingX, pendingY);
            }
            if (firstDraw) {
                this.mTransaction.setLayer(this.mSurfaceControl, 5).show(this.mSurfaceControl);
            }
            this.mBBQ.mergeWithNextTransaction(this.mTransaction, frame);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updateCurrentContentForTesting() {
            Canvas canvas = new Canvas(this.mCurrentContent);
            Rect bounds = new Rect(0, 0, this.mContentWidth, this.mContentHeight);
            if (this.mBitmap != null && !this.mBitmap.isRecycled()) {
                Rect originalBounds =
                        new Rect(0, 0, this.mBitmap.getWidth(), this.mBitmap.getHeight());
                canvas.drawBitmap(this.mBitmap, originalBounds, bounds, (Paint) null);
            }
            this.mOverlay.setBounds(bounds);
            this.mOverlay.draw(canvas);
        }
    }

    public static final class Builder {
        private int mBottomContentBound;
        private boolean mClippingEnabled;
        private float mCornerRadius;
        private float mElevation;
        private int mHeight;
        private int mHorizontalDefaultSourceToMagnifierOffset;
        private boolean mIsFishEyeStyle;
        private int mLeftContentBound;
        private Drawable mOverlay;
        private int mRightContentBound;
        private int mSourceHeight;
        private int mSourceWidth;
        private int mTopContentBound;
        private int mVerticalDefaultSourceToMagnifierOffset;
        private View mView;
        private int mWidth;
        private float mZoom;

        public Builder(View view) {
            this.mView = (View) Objects.requireNonNull(view);
            applyDefaults();
        }

        private void applyDefaults() {
            Resources resources = this.mView.getContext().getResources();
            this.mWidth = resources.getDimensionPixelSize(R.dimen.default_magnifier_width);
            this.mHeight = resources.getDimensionPixelSize(R.dimen.default_magnifier_height);
            this.mElevation = resources.getDimension(R.dimen.default_magnifier_elevation);
            this.mCornerRadius = resources.getDimension(R.dimen.default_magnifier_corner_radius);
            this.mZoom = resources.getFloat(R.dimen.default_magnifier_zoom);
            this.mHorizontalDefaultSourceToMagnifierOffset =
                    resources.getDimensionPixelSize(R.dimen.default_magnifier_horizontal_offset);
            this.mVerticalDefaultSourceToMagnifierOffset =
                    resources.getDimensionPixelSize(R.dimen.default_magnifier_vertical_offset);
            this.mOverlay =
                    new ColorDrawable(
                            resources.getColor(R.color.default_magnifier_color_overlay, null));
            this.mClippingEnabled = true;
            this.mLeftContentBound = 1;
            this.mTopContentBound = 1;
            this.mRightContentBound = 1;
            this.mBottomContentBound = 1;
            this.mIsFishEyeStyle = false;
        }

        public Builder setSize(int width, int height) {
            Preconditions.checkArgumentPositive(width, "Width should be positive");
            Preconditions.checkArgumentPositive(height, "Height should be positive");
            this.mWidth = width;
            this.mHeight = height;
            return this;
        }

        public Builder setInitialZoom(float zoom) {
            Preconditions.checkArgumentPositive(zoom, "Zoom should be positive");
            this.mZoom = zoom;
            return this;
        }

        public Builder setElevation(float elevation) {
            Preconditions.checkArgumentNonNegative(elevation, "Elevation should be non-negative");
            this.mElevation = elevation;
            return this;
        }

        public Builder setCornerRadius(float cornerRadius) {
            Preconditions.checkArgumentNonNegative(
                    cornerRadius, "Corner radius should be non-negative");
            this.mCornerRadius = cornerRadius;
            return this;
        }

        public Builder setOverlay(Drawable overlay) {
            this.mOverlay = overlay;
            return this;
        }

        public Builder setDefaultSourceToMagnifierOffset(int horizontalOffset, int verticalOffset) {
            this.mHorizontalDefaultSourceToMagnifierOffset = horizontalOffset;
            this.mVerticalDefaultSourceToMagnifierOffset = verticalOffset;
            return this;
        }

        public Builder setClippingEnabled(boolean clip) {
            this.mClippingEnabled = clip;
            return this;
        }

        public Builder setSourceBounds(int left, int top, int right, int bottom) {
            this.mLeftContentBound = left;
            this.mTopContentBound = top;
            this.mRightContentBound = right;
            this.mBottomContentBound = bottom;
            return this;
        }

        Builder setSourceSize(int width, int height) {
            this.mSourceWidth = width;
            this.mSourceHeight = height;
            return this;
        }

        Builder setFishEyeStyle() {
            this.mIsFishEyeStyle = true;
            return this;
        }

        public Magnifier build() {
            return new Magnifier(this);
        }
    }

    public void setOnOperationCompleteCallback(Callback callback) {
        this.mCallback = callback;
        if (this.mWindow != null) {
            this.mWindow.mCallback = callback;
        }
    }

    public Bitmap getContent() {
        Bitmap bitmap;
        if (this.mWindow == null) {
            return null;
        }
        synchronized (this.mWindow.mLock) {
            bitmap = this.mWindow.mCurrentContent;
        }
        return bitmap;
    }

    public Bitmap getOriginalContent() {
        Bitmap createBitmap;
        if (this.mWindow == null) {
            return null;
        }
        synchronized (this.mWindow.mLock) {
            createBitmap = Bitmap.createBitmap(this.mWindow.mBitmap);
        }
        return createBitmap;
    }

    public static PointF getMagnifierDefaultSize() {
        Resources resources = Resources.getSystem();
        float density = resources.getDisplayMetrics().density;
        PointF size = new PointF();
        size.x = resources.getDimension(R.dimen.default_magnifier_width) / density;
        size.y = resources.getDimension(R.dimen.default_magnifier_height) / density;
        return size;
    }
}
