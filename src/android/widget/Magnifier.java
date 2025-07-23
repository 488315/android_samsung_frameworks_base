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
import android.widget.Magnifier;
import com.android.internal.R;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* loaded from: classes5.dex */
public final class Magnifier {
    private static final float FISHEYE_RAMP_WIDTH = 12.0f;
    private static final int NONEXISTENT_PREVIOUS_CONFIG_VALUE = -1;
    public static final int SOURCE_BOUND_MAX_IN_SURFACE = 0;
    public static final int SOURCE_BOUND_MAX_VISIBLE = 1;
    private static final String TAG = "Magnifier";
    private static final HandlerThread sPixelCopyHandlerThread;
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
    private boolean mIsDarkMode;
    private boolean mIsFishEyeStyle;
    private int mLeftContentBound;
    private int mLeftCutWidth;
    private final Object mLock;
    private int mMagnifierBackgroundColorDark;
    private int mMagnifierBackgroundColorLight;
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
    public @interface SourceBound {
    }

    static {
        HandlerThread handlerThread = new HandlerThread("magnifier pixel copy result handler");
        sPixelCopyHandlerThread = handlerThread;
        handlerThread.start();
    }

    @Deprecated
    public Magnifier(View view) {
        this(createBuilderWithOldMagnifierDefaults(view));
    }

    static Builder createBuilderWithOldMagnifierDefaults(View view) {
        Builder builder = new Builder(view);
        Context context = view.getContext();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(null, R.styleable.Magnifier, R.attr.magnifierStyle, 0);
        builder.mWidth = obtainStyledAttributes.getDimensionPixelSize(5, 0);
        builder.mHeight = obtainStyledAttributes.getDimensionPixelSize(2, 0);
        builder.mElevation = obtainStyledAttributes.getDimension(1, 0.0f);
        builder.mCornerRadius = getDeviceDefaultDialogCornerRadius(context);
        builder.mZoom = obtainStyledAttributes.getFloat(6, 0.0f);
        builder.mHorizontalDefaultSourceToMagnifierOffset = obtainStyledAttributes.getDimensionPixelSize(3, 0);
        builder.mVerticalDefaultSourceToMagnifierOffset = obtainStyledAttributes.getDimensionPixelSize(4, 0);
        builder.mOverlay = new ColorDrawable(obtainStyledAttributes.getColor(0, 0));
        obtainStyledAttributes.recycle();
        builder.mClippingEnabled = true;
        builder.mLeftContentBound = 1;
        builder.mTopContentBound = 0;
        builder.mRightContentBound = 1;
        builder.mBottomContentBound = 0;
        return builder;
    }

    private static float getDeviceDefaultDialogCornerRadius(Context context) {
        TypedArray obtainStyledAttributes = new ContextThemeWrapper(context, 16974120).obtainStyledAttributes(new int[]{16844145});
        float dimension = obtainStyledAttributes.getDimension(0, 0.0f);
        obtainStyledAttributes.recycle();
        return dimension;
    }

    private Magnifier(Builder builder) {
        this.mWindowCoords = new Point();
        this.mClampedCenterZoomCoords = new Point();
        this.mPrevStartCoordsInSurface = new Point(-1, -1);
        this.mPrevShowSourceCoords = new PointF(-1.0f, -1.0f);
        this.mPrevShowWindowCoords = new PointF(-1.0f, -1.0f);
        this.mPixelCopyRequestRect = new Rect();
        this.mLock = new Object();
        this.mLeftCutWidth = 0;
        this.mRightCutWidth = 0;
        View view = builder.mView;
        this.mView = view;
        int i = builder.mWidth;
        this.mWindowWidth = i;
        this.mWindowHeight = builder.mHeight;
        this.mZoom = builder.mZoom;
        this.mIsFishEyeStyle = builder.mIsFishEyeStyle;
        if (builder.mSourceWidth > 0 && builder.mSourceHeight > 0) {
            this.mSourceWidth = builder.mSourceWidth;
            this.mSourceHeight = builder.mSourceHeight;
        } else {
            this.mSourceWidth = Math.round(i / this.mZoom);
            this.mSourceHeight = Math.round(this.mWindowHeight / this.mZoom);
        }
        this.mWindowElevation = builder.mElevation;
        this.mWindowCornerRadius = builder.mCornerRadius;
        this.mOverlay = builder.mOverlay;
        this.mDefaultHorizontalSourceToMagnifierOffset = builder.mHorizontalDefaultSourceToMagnifierOffset;
        this.mDefaultVerticalSourceToMagnifierOffset = builder.mVerticalDefaultSourceToMagnifierOffset;
        this.mClippingEnabled = builder.mClippingEnabled;
        this.mLeftContentBound = builder.mLeftContentBound;
        this.mTopContentBound = builder.mTopContentBound;
        this.mRightContentBound = builder.mRightContentBound;
        this.mBottomContentBound = builder.mBottomContentBound;
        this.mViewCoordinatesInSurface = new int[2];
        this.mRamp = (int) TypedValue.applyDimension(1, FISHEYE_RAMP_WIDTH, view.getContext().getResources().getDisplayMetrics());
        this.mIsDarkMode = builder.mIsDarkMode;
        this.mMagnifierBackgroundColorDark = builder.mMagnifierBackgroundColorDark;
        this.mMagnifierBackgroundColorLight = builder.mMagnifierBackgroundColorLight;
    }

    public void show(float f, float f2) {
        show(f, f2, this.mDefaultHorizontalSourceToMagnifierOffset + f, this.mDefaultVerticalSourceToMagnifierOffset + f2);
    }

    void setDrawCursor(boolean z, Drawable drawable) {
        this.mDrawCursorEnabled = z;
        this.mCursorDrawable = drawable;
    }

    public void show(float f, float f2, float f3, float f4) {
        float f5;
        float f6;
        int i;
        obtainSurfaces();
        obtainContentCoordinates(f, f2);
        int i2 = this.mClampedCenterZoomCoords.x - (this.mSourceWidth / 2);
        int i3 = this.mClampedCenterZoomCoords.y - (this.mSourceHeight / 2);
        boolean z = true;
        if (this.mIsFishEyeStyle) {
            f5 = this.mClampedCenterZoomCoords.x - this.mViewCoordinatesInSurface[0];
            f6 = this.mClampedCenterZoomCoords.y - this.mViewCoordinatesInSurface[1];
            int i4 = this.mSourceWidth;
            int i5 = this.mRamp;
            float f7 = this.mZoom;
            float f8 = (i4 - ((i4 - (i5 * 2)) / f7)) / 2.0f;
            float f9 = f - (i4 / 2.0f);
            float f10 = i5 + f9;
            float f11 = 0.0f;
            if (0.0f > f10) {
                f11 = f - ((f - 0.0f) / f7);
            } else if (0.0f > f9) {
                f11 = (f9 + f8) - (((f10 - 0.0f) * f8) / i5);
            }
            int min = Math.min((int) f11, this.mView.getWidth());
            float f12 = (this.mSourceWidth / 2.0f) + f;
            float f13 = f12 - this.mRamp;
            float width = this.mView.getWidth();
            if (width < f13) {
                width = ((width - f) / this.mZoom) + f;
            } else if (width < f12) {
                width = (((width - f13) * f8) / this.mRamp) + (f12 - f8);
            }
            int max = Math.max(min, (int) width);
            int max2 = Math.max(min + this.mViewCoordinatesInSurface[0], 0);
            int min2 = Math.min(max + this.mViewCoordinatesInSurface[0], this.mContentCopySurface.mWidth);
            this.mLeftCutWidth = Math.max(0, max2 - i2);
            this.mRightCutWidth = Math.max(0, (this.mSourceWidth + i2) - min2);
            i2 = Math.max(i2, max2);
        } else {
            f5 = f3;
            f6 = f4;
        }
        obtainWindowCoordinates(f5, f6);
        if (f != this.mPrevShowSourceCoords.x || f2 != this.mPrevShowSourceCoords.y || this.mDirtyState) {
            if (this.mWindow == null) {
                synchronized (this.mLock) {
                    Context context = this.mView.getContext();
                    Display display = this.mView.getDisplay();
                    SurfaceControl surfaceControl = this.mParentSurface.mSurfaceControl;
                    int i6 = this.mWindowWidth;
                    int i7 = this.mWindowHeight;
                    float f14 = this.mZoom;
                    int i8 = this.mRamp;
                    float f15 = this.mWindowElevation;
                    float f16 = this.mWindowCornerRadius;
                    Drawable drawable = this.mOverlay;
                    if (drawable != null) {
                        i = i8;
                    } else {
                        i = i8;
                        drawable = new ColorDrawable(0);
                    }
                    this.mWindow = new InternalPopupWindow(context, display, surfaceControl, i6, i7, f14, i, f15, f16, drawable, Handler.getMain(), this.mLock, this.mCallback, this.mIsFishEyeStyle);
                }
                z = true;
            }
            performPixelCopy(i2, i3, z);
        } else if (f5 != this.mPrevShowWindowCoords.x || f6 != this.mPrevShowWindowCoords.y) {
            final Point currentClampedWindowCoordinates = getCurrentClampedWindowCoordinates();
            final InternalPopupWindow internalPopupWindow = this.mWindow;
            sPixelCopyHandlerThread.getThreadHandler().post(new Runnable() { // from class: android.widget.Magnifier$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    Magnifier.this.lambda$show$0(internalPopupWindow, currentClampedWindowCoordinates);
                }
            });
        }
        this.mPrevShowSourceCoords.x = f;
        this.mPrevShowSourceCoords.y = f2;
        this.mPrevShowWindowCoords.x = f5;
        this.mPrevShowWindowCoords.y = f6;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$show$0(InternalPopupWindow internalPopupWindow, Point point) {
        synchronized (this.mLock) {
            InternalPopupWindow internalPopupWindow2 = this.mWindow;
            if (internalPopupWindow2 != internalPopupWindow) {
                return;
            }
            internalPopupWindow2.setContentPositionForNextDraw(point.x, point.y);
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
                performPixelCopy(this.mPrevStartCoordsInSurface.x, this.mPrevStartCoordsInSurface.y, false);
            } else {
                show(this.mPrevShowSourceCoords.x, this.mPrevShowSourceCoords.y, this.mPrevShowWindowCoords.x, this.mPrevShowWindowCoords.y);
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

    public void setZoom(float f) {
        Preconditions.checkArgumentPositive(f, "Zoom should be positive");
        this.mZoom = f;
        this.mSourceWidth = this.mIsFishEyeStyle ? this.mWindowWidth : Math.round(this.mWindowWidth / f);
        this.mSourceHeight = Math.round(this.mWindowHeight / this.mZoom);
        this.mDirtyState = true;
    }

    void updateSourceFactors(int i, float f) {
        this.mZoom = f;
        this.mSourceHeight = i;
        int i2 = (int) (i * f);
        this.mWindowHeight = i2;
        InternalPopupWindow internalPopupWindow = this.mWindow;
        if (internalPopupWindow != null) {
            internalPopupWindow.updateContentFactors(i2, f);
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
        Point currentClampedWindowCoordinates = getCurrentClampedWindowCoordinates();
        currentClampedWindowCoordinates.offset(-this.mParentSurface.mInsets.left, -this.mParentSurface.mInsets.top);
        return new Point(currentClampedWindowCoordinates);
    }

    public Point getSourcePosition() {
        if (this.mWindow == null) {
            return null;
        }
        Point point = new Point(this.mPixelCopyRequestRect.left, this.mPixelCopyRequestRect.top);
        point.offset(-this.mContentCopySurface.mInsets.left, -this.mContentCopySurface.mInsets.top);
        return new Point(point);
    }

    private void obtainSurfaces() {
        ViewRootImpl viewRootImpl;
        Surface surface;
        SurfaceInfo surfaceInfo = SurfaceInfo.NULL;
        if (this.mView.getViewRootImpl() != null && (surface = (viewRootImpl = this.mView.getViewRootImpl()).mSurface) != null && surface.isValid()) {
            Rect rect = viewRootImpl.mWindowAttributes.surfaceInsets;
            surfaceInfo = new SurfaceInfo(viewRootImpl.getSurfaceControl(), surface, viewRootImpl.getWidth() + rect.left + rect.right, viewRootImpl.getHeight() + rect.top + rect.bottom, rect, true);
        }
        SurfaceInfo surfaceInfo2 = SurfaceInfo.NULL;
        View view = this.mView;
        if (view instanceof SurfaceView) {
            SurfaceControl surfaceControl = ((SurfaceView) view).getSurfaceControl();
            SurfaceHolder holder = ((SurfaceView) this.mView).getHolder();
            Surface surface2 = holder.getSurface();
            if (surfaceControl != null && surfaceControl.isValid()) {
                Rect surfaceFrame = holder.getSurfaceFrame();
                surfaceInfo2 = new SurfaceInfo(surfaceControl, surface2, surfaceFrame.right, surfaceFrame.bottom, new Rect(), false);
            }
        }
        this.mParentSurface = surfaceInfo != SurfaceInfo.NULL ? surfaceInfo : surfaceInfo2;
        if (this.mView instanceof SurfaceView) {
            surfaceInfo = surfaceInfo2;
        }
        this.mContentCopySurface = surfaceInfo;
    }

    private void obtainContentCoordinates(float f, float f2) {
        int round;
        int round2;
        int max;
        int[] iArr = this.mViewCoordinatesInSurface;
        int i = iArr[0];
        int i2 = iArr[1];
        this.mView.getLocationInSurface(iArr);
        int[] iArr2 = this.mViewCoordinatesInSurface;
        int i3 = iArr2[0];
        if (i3 != i || iArr2[1] != i2) {
            this.mDirtyState = true;
        }
        if (this.mView instanceof SurfaceView) {
            round = Math.round(f);
            round2 = Math.round(f2);
        } else {
            round = Math.round(f + i3);
            round2 = Math.round(f2 + this.mViewCoordinatesInSurface[1]);
        }
        Rect[] rectArr = new Rect[2];
        rectArr[0] = new Rect(0, 0, this.mContentCopySurface.mWidth, this.mContentCopySurface.mHeight);
        Rect rect = new Rect();
        this.mView.getGlobalVisibleRect(rect);
        if (this.mView.getViewRootImpl() != null) {
            Rect rect2 = this.mView.getViewRootImpl().mWindowAttributes.surfaceInsets;
            rect.offset(rect2.left, rect2.top);
        }
        if (this.mView instanceof SurfaceView) {
            int[] iArr3 = this.mViewCoordinatesInSurface;
            rect.offset(-iArr3[0], -iArr3[1]);
        }
        rectArr[1] = rect;
        int i4 = Integer.MIN_VALUE;
        int i5 = Integer.MIN_VALUE;
        for (int i6 = this.mLeftContentBound; i6 >= 0; i6--) {
            i5 = Math.max(i5, rectArr[i6].left);
        }
        for (int i7 = this.mTopContentBound; i7 >= 0; i7--) {
            i4 = Math.max(i4, rectArr[i7].top);
        }
        int i8 = Integer.MAX_VALUE;
        int i9 = Integer.MAX_VALUE;
        for (int i10 = this.mRightContentBound; i10 >= 0; i10--) {
            i9 = Math.min(i9, rectArr[i10].right);
        }
        for (int i11 = this.mBottomContentBound; i11 >= 0; i11--) {
            i8 = Math.min(i8, rectArr[i11].bottom);
        }
        int min = Math.min(i5, this.mContentCopySurface.mWidth - this.mSourceWidth);
        int min2 = Math.min(i4, this.mContentCopySurface.mHeight - this.mSourceHeight);
        if (min < 0 || min2 < 0) {
            Log.e(TAG, "Magnifier's content is copied from a surface smaller thanthe content requested size. The magnifier will be dismissed.");
        }
        int max2 = Math.max(i9, this.mSourceWidth + min);
        int max3 = Math.max(i8, this.mSourceHeight + min2);
        Point point = this.mClampedCenterZoomCoords;
        if (this.mIsFishEyeStyle) {
            max = Math.max(min, Math.min(round, max2));
        } else {
            int i12 = this.mSourceWidth;
            max = Math.max(min + (i12 / 2), Math.min(round, max2 - (i12 / 2)));
        }
        point.x = max;
        Point point2 = this.mClampedCenterZoomCoords;
        int i13 = this.mSourceHeight;
        point2.y = Math.max(min2 + (i13 / 2), Math.min(round2, max3 - (i13 / 2)));
    }

    private void obtainWindowCoordinates(float f, float f2) {
        int round;
        int round2;
        if (this.mView instanceof SurfaceView) {
            round = Math.round(f);
            round2 = Math.round(f2);
        } else {
            round = Math.round(f + this.mViewCoordinatesInSurface[0]);
            round2 = Math.round(f2 + this.mViewCoordinatesInSurface[1]);
        }
        this.mWindowCoords.x = round - (this.mWindowWidth / 2);
        this.mWindowCoords.y = round2 - (this.mWindowHeight / 2);
        if (this.mParentSurface != this.mContentCopySurface) {
            this.mWindowCoords.x += this.mViewCoordinatesInSurface[0];
            this.mWindowCoords.y += this.mViewCoordinatesInSurface[1];
        }
    }

    private void maybeDrawCursor(Canvas canvas) {
        if (this.mDrawCursorEnabled) {
            Drawable drawable = this.mCursorDrawable;
            if (drawable != null) {
                int i = this.mSourceWidth;
                drawable.setBounds(i / 2, 0, (i / 2) + drawable.getIntrinsicWidth(), this.mSourceHeight);
                this.mCursorDrawable.draw(canvas);
            } else {
                Paint paint = new Paint();
                paint.setColor(-16777216);
                canvas.drawRect(new Rect((r3 / 2) - 1, 0, (this.mSourceWidth / 2) + 1, this.mSourceHeight), paint);
            }
        }
    }

    private void performPixelCopy(int i, int i2, final boolean z) {
        if (this.mContentCopySurface.mSurface == null || !this.mContentCopySurface.mSurface.isValid()) {
            onPixelCopyFailed();
            return;
        }
        final Point currentClampedWindowCoordinates = getCurrentClampedWindowCoordinates();
        this.mPixelCopyRequestRect.set(i, i2, ((this.mSourceWidth + i) - this.mLeftCutWidth) - this.mRightCutWidth, this.mSourceHeight + i2);
        this.mPrevStartCoordsInSurface.x = i;
        this.mPrevStartCoordsInSurface.y = i2;
        this.mDirtyState = false;
        final InternalPopupWindow internalPopupWindow = this.mWindow;
        if (this.mPixelCopyRequestRect.width() == 0) {
            this.mWindow.updateContent(Bitmap.createBitmap(this.mSourceWidth, this.mSourceHeight, Bitmap.Config.ALPHA_8));
        } else {
            final Bitmap createBitmap = Bitmap.createBitmap((this.mSourceWidth - this.mLeftCutWidth) - this.mRightCutWidth, this.mSourceHeight, Bitmap.Config.ARGB_8888);
            PixelCopy.request(this.mContentCopySurface.mSurface, this.mPixelCopyRequestRect, createBitmap, new PixelCopy.OnPixelCopyFinishedListener() { // from class: android.widget.Magnifier$$ExternalSyntheticLambda0
                @Override // android.view.PixelCopy.OnPixelCopyFinishedListener
                public final void onPixelCopyFinished(int i3) {
                    Magnifier.this.lambda$performPixelCopy$1(internalPopupWindow, z, currentClampedWindowCoordinates, createBitmap, i3);
                }
            }, sPixelCopyHandlerThread.getThreadHandler());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$performPixelCopy$1(InternalPopupWindow internalPopupWindow, boolean z, Point point, Bitmap bitmap, int i) {
        if (i != 0) {
            onPixelCopyFailed();
            return;
        }
        synchronized (this.mLock) {
            InternalPopupWindow internalPopupWindow2 = this.mWindow;
            if (internalPopupWindow2 != internalPopupWindow) {
                return;
            }
            if (z) {
                internalPopupWindow2.setContentPositionForNextDraw(point.x, point.y);
            }
            int width = bitmap.getWidth();
            int i2 = this.mSourceWidth;
            if (width < i2) {
                Bitmap createBitmap = Bitmap.createBitmap(i2, bitmap.getHeight(), bitmap.getConfig());
                Canvas canvas = new Canvas(createBitmap);
                canvas.drawBitmap(bitmap, (Rect) null, new Rect(this.mLeftCutWidth, 0, this.mSourceWidth - this.mRightCutWidth, bitmap.getHeight()), (Paint) null);
                maybeDrawCursor(canvas);
                this.mWindow.updateContent(createBitmap);
            } else {
                Bitmap createBitmap2 = Bitmap.createBitmap((i2 - this.mLeftCutWidth) - this.mRightCutWidth, this.mSourceHeight, Bitmap.Config.ARGB_8888);
                Bitmap createBitmap3 = Bitmap.createBitmap((this.mSourceWidth - this.mLeftCutWidth) - this.mRightCutWidth, this.mSourceHeight, Bitmap.Config.ARGB_8888);
                createBitmap3.eraseColor(this.mIsDarkMode ? this.mMagnifierBackgroundColorDark : this.mMagnifierBackgroundColorLight);
                Canvas canvas2 = new Canvas(createBitmap2);
                Rect rect = new Rect(this.mLeftCutWidth, 0, this.mSourceWidth - this.mRightCutWidth, bitmap.getHeight());
                canvas2.drawBitmap(createBitmap3, (Rect) null, rect, (Paint) null);
                canvas2.drawBitmap(bitmap, (Rect) null, rect, (Paint) null);
                maybeDrawCursor(canvas2);
                this.mWindow.updateContent(createBitmap2);
            }
        }
    }

    private void onPixelCopyFailed() {
        Log.e(TAG, "Magnifier failed to copy content from the view Surface. It will be dismissed.");
        Handler.getMain().postAtFrontOfQueue(new Runnable() { // from class: android.widget.Magnifier$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                Magnifier.this.lambda$onPixelCopyFailed$2();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onPixelCopyFailed$2() {
        dismiss();
        Callback callback = this.mCallback;
        if (callback != null) {
            callback.onOperationComplete();
        }
    }

    private Point getCurrentClampedWindowCoordinates() {
        Rect rect;
        if (!this.mClippingEnabled) {
            return new Point(this.mWindowCoords);
        }
        if (this.mParentSurface.mIsMainWindowSurface) {
            Insets systemWindowInsets = this.mView.getRootWindowInsets().getSystemWindowInsets();
            rect = new Rect(systemWindowInsets.left + this.mParentSurface.mInsets.left, systemWindowInsets.top + this.mParentSurface.mInsets.top, (this.mParentSurface.mWidth - systemWindowInsets.right) - this.mParentSurface.mInsets.right, (this.mParentSurface.mHeight - systemWindowInsets.bottom) - this.mParentSurface.mInsets.bottom);
        } else {
            rect = new Rect(0, 0, this.mParentSurface.mWidth, this.mParentSurface.mHeight);
        }
        return new Point(Math.max(rect.left, Math.min(rect.right - this.mWindowWidth, this.mWindowCoords.x)), Math.max(rect.top, Math.min(rect.bottom - this.mWindowHeight, this.mWindowCoords.y)));
    }

    private static class SurfaceInfo {
        public static final SurfaceInfo NULL = new SurfaceInfo(null, null, 0, 0, null, false);
        private int mHeight;
        private Rect mInsets;
        private boolean mIsMainWindowSurface;
        private Surface mSurface;
        private SurfaceControl mSurfaceControl;
        private int mWidth;

        SurfaceInfo(SurfaceControl surfaceControl, Surface surface, int i, int i2, Rect rect, boolean z) {
            this.mSurfaceControl = surfaceControl;
            this.mSurface = surface;
            this.mWidth = i;
            this.mHeight = i2;
            this.mInsets = rect;
            this.mIsMainWindowSurface = z;
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

        InternalPopupWindow(Context context, Display display, SurfaceControl surfaceControl, int i, int i2, float f, int i3, float f2, float f3, Drawable drawable, Handler handler, Object obj, Callback callback, boolean z) {
            this.mDisplay = display;
            this.mOverlay = drawable;
            this.mLock = obj;
            this.mCallback = callback;
            this.mContentWidth = i;
            this.mContentHeight = i2;
            this.mZoom = f;
            this.mRamp = i3;
            int i4 = (int) (1.05f * f2);
            this.mOffsetX = i4;
            this.mOffsetY = i4;
            SurfaceSession surfaceSession = new SurfaceSession();
            this.mSurfaceSession = surfaceSession;
            SurfaceControl build = new SurfaceControl.Builder(surfaceSession).setName("magnifier surface").setFlags(4).setContainerLayer().setParent(surfaceControl).setCallsite("InternalPopupWindow").build();
            this.mSurfaceControl = build;
            SurfaceControl build2 = new SurfaceControl.Builder(surfaceSession).setName("magnifier surface bbq wrapper").setHidden(false).setBLASTLayer().setParent(build).setCallsite("InternalPopupWindow").build();
            this.mBbqSurfaceControl = build2;
            BLASTBufferQueue bLASTBufferQueue = new BLASTBufferQueue("magnifier surface", true);
            this.mBBQ = bLASTBufferQueue;
            bLASTBufferQueue.update(build2, (i4 * 2) + i, (i4 * 2) + i2, -3);
            Surface createSurface = bLASTBufferQueue.createSurface();
            this.mSurface = createSurface;
            ThreadedRenderer.SimpleRenderer simpleRenderer = new ThreadedRenderer.SimpleRenderer(context, "magnifier renderer", createSurface);
            this.mRenderer = simpleRenderer;
            RenderNode createRenderNodeForBitmap = createRenderNodeForBitmap("magnifier content", f2, f3);
            this.mBitmapRenderNode = createRenderNodeForBitmap;
            RenderNode createRenderNodeForOverlay = createRenderNodeForOverlay("magnifier overlay", f3);
            this.mOverlayRenderNode = createRenderNodeForOverlay;
            setupOverlay();
            RecordingCanvas beginRecording = simpleRenderer.getRootNode().beginRecording(i, i2);
            try {
                beginRecording.enableZ();
                beginRecording.drawRenderNode(createRenderNodeForBitmap);
                beginRecording.disableZ();
                beginRecording.drawRenderNode(createRenderNodeForOverlay);
                beginRecording.disableZ();
                simpleRenderer.getRootNode().endRecording();
                if (this.mCallback != null) {
                    this.mCurrentContent = Bitmap.createBitmap(i, this.mContentHeight, Bitmap.Config.ARGB_8888);
                    updateCurrentContentForTesting();
                }
                this.mHandler = handler;
                this.mMagnifierUpdater = new Runnable() { // from class: android.widget.Magnifier$InternalPopupWindow$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        Magnifier.InternalPopupWindow.this.doDraw();
                    }
                };
                this.mFrameDrawScheduled = false;
                this.mIsFishEyeStyle = z;
                if (z) {
                    createMeshMatrixForFishEyeEffect();
                }
            } catch (Throwable th) {
                this.mRenderer.getRootNode().endRecording();
                throw th;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updateContentFactors(int i, float f) {
            int i2;
            int i3 = this.mContentHeight;
            if (i3 == i && this.mZoom == f) {
                return;
            }
            if (i3 < i) {
                this.mBBQ.update(this.mBbqSurfaceControl, this.mContentWidth, i, -3);
                this.mRenderer.setSurface(this.mSurface);
                Outline outline = new Outline();
                i2 = i;
                outline.setRoundRect(0, 0, this.mContentWidth, i2, 0.0f);
                outline.setAlpha(1.0f);
                RenderNode renderNode = this.mBitmapRenderNode;
                int i4 = this.mOffsetX;
                int i5 = this.mOffsetY;
                renderNode.setLeftTopRightBottom(i4, i5, this.mContentWidth + i4, i5 + i2);
                this.mBitmapRenderNode.setOutline(outline);
                RenderNode renderNode2 = this.mOverlayRenderNode;
                int i6 = this.mOffsetX;
                int i7 = this.mOffsetY;
                renderNode2.setLeftTopRightBottom(i6, i7, this.mContentWidth + i6, i7 + i2);
                this.mOverlayRenderNode.setOutline(outline);
                RecordingCanvas beginRecording = this.mRenderer.getRootNode().beginRecording(this.mContentWidth, i2);
                try {
                    beginRecording.enableZ();
                    beginRecording.drawRenderNode(this.mBitmapRenderNode);
                    beginRecording.disableZ();
                    beginRecording.drawRenderNode(this.mOverlayRenderNode);
                    beginRecording.disableZ();
                } finally {
                    this.mRenderer.getRootNode().endRecording();
                }
            } else {
                i2 = i;
            }
            this.mContentHeight = i2;
            this.mZoom = f;
            fillMeshMatrix();
        }

        private void createMeshMatrixForFishEyeEffect() {
            this.mMeshWidth = 1;
            this.mMeshHeight = 6;
            this.mMeshLeft = new float[(1 + 1) * 2 * (6 + 1)];
            this.mMeshRight = new float[(1 + 1) * 2 * (6 + 1)];
            fillMeshMatrix();
        }

        private void fillMeshMatrix() {
            this.mMeshWidth = 1;
            this.mMeshHeight = 6;
            float f = this.mContentWidth;
            float f2 = this.mContentHeight;
            float f3 = f2 / this.mZoom;
            float f4 = f2 - f3;
            int i = 0;
            while (true) {
                int i2 = this.mMeshWidth;
                int i3 = this.mMeshHeight;
                if (i >= (i2 + 1) * 2 * (i3 + 1)) {
                    return;
                }
                float[] fArr = this.mMeshLeft;
                float f5 = (i % ((i2 + 1) * 2)) / 2;
                int i4 = this.mRamp;
                fArr[i] = (i4 * f5) / i2;
                float[] fArr2 = this.mMeshRight;
                fArr2[i] = (f - i4) + ((r7 * i4) / i2);
                float f6 = f5 * f4;
                float f7 = (f6 / i2) + f3;
                int i5 = i + 1;
                float f8 = (i / 2) / (i2 + 1);
                fArr[i5] = ((f2 - f7) / 2.0f) + ((f7 * f8) / i3);
                float f9 = f2 - (f6 / i2);
                fArr2[i5] = ((f2 - f9) / 2.0f) + ((f9 * f8) / i3);
                i += 2;
            }
        }

        private RenderNode createRenderNodeForBitmap(String str, float f, float f2) {
            RenderNode create = RenderNode.create(str, null);
            int i = this.mOffsetX;
            int i2 = this.mOffsetY;
            create.setLeftTopRightBottom(i, i2, this.mContentWidth + i, this.mContentHeight + i2);
            create.setElevation(f);
            Outline outline = new Outline();
            outline.setRoundRect(0, 0, this.mContentWidth, this.mContentHeight, f2);
            outline.setAlpha(1.0f);
            create.setOutline(outline);
            create.setClipToOutline(true);
            try {
                create.beginRecording(this.mContentWidth, this.mContentHeight).drawColor(Color.GREEN);
                return create;
            } finally {
                create.endRecording();
            }
        }

        private RenderNode createRenderNodeForOverlay(String str, float f) {
            RenderNode create = RenderNode.create(str, null);
            int i = this.mOffsetX;
            int i2 = this.mOffsetY;
            create.setLeftTopRightBottom(i, i2, this.mContentWidth + i, this.mContentHeight + i2);
            Outline outline = new Outline();
            outline.setRoundRect(0, 0, this.mContentWidth, this.mContentHeight, f);
            outline.setAlpha(1.0f);
            create.setOutline(outline);
            create.setClipToOutline(true);
            return create;
        }

        private void setupOverlay() {
            drawOverlay();
            this.mOverlay.setCallback(new Drawable.Callback() { // from class: android.widget.Magnifier.InternalPopupWindow.1
                @Override // android.graphics.drawable.Drawable.Callback
                public void invalidateDrawable(Drawable drawable) {
                    InternalPopupWindow.this.drawOverlay();
                    if (InternalPopupWindow.this.mCallback != null) {
                        InternalPopupWindow.this.updateCurrentContentForTesting();
                    }
                }

                @Override // android.graphics.drawable.Drawable.Callback
                public void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
                    Handler.getMain().postAtTime(runnable, drawable, j);
                }

                @Override // android.graphics.drawable.Drawable.Callback
                public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
                    Handler.getMain().removeCallbacks(runnable, drawable);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void drawOverlay() {
            RecordingCanvas beginRecording = this.mOverlayRenderNode.beginRecording(this.mContentWidth, this.mContentHeight);
            try {
                this.mOverlay.setBounds(0, 0, this.mContentWidth, this.mContentHeight);
                this.mOverlay.draw(beginRecording);
            } finally {
                this.mOverlayRenderNode.endRecording();
            }
        }

        public void setContentPositionForNextDraw(int i, int i2) {
            this.mWindowPositionX = i - this.mOffsetX;
            this.mWindowPositionY = i2 - this.mOffsetY;
            this.mPendingWindowPositionUpdate = true;
            requestUpdate();
        }

        public void updateContent(Bitmap bitmap) {
            Bitmap bitmap2 = this.mBitmap;
            if (bitmap2 != null) {
                bitmap2.recycle();
            }
            this.mBitmap = bitmap;
            requestUpdate();
        }

        private void requestUpdate() {
            if (this.mFrameDrawScheduled) {
                return;
            }
            Message obtain = Message.obtain(this.mHandler, this.mMagnifierUpdater);
            obtain.setAsynchronous(true);
            obtain.sendToTarget();
            this.mFrameDrawScheduled = true;
        }

        public void destroy() {
            this.mRenderer.destroy();
            this.mSurface.destroy();
            this.mBBQ.destroy();
            new SurfaceControl.Transaction().remove(this.mSurfaceControl).remove(this.mBbqSurfaceControl).apply();
            this.mSurfaceSession.kill();
            this.mHandler.removeCallbacks(this.mMagnifierUpdater);
            Bitmap bitmap = this.mBitmap;
            if (bitmap != null) {
                bitmap.recycle();
            }
            this.mOverlay.setCallback(null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* JADX WARN: Removed duplicated region for block: B:24:0x00ca  */
        /* JADX WARN: Removed duplicated region for block: B:26:? A[RETURN, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void doDraw() {
            /*
                Method dump skipped, instructions count: 223
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: android.widget.Magnifier.InternalPopupWindow.doDraw():void");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$doDraw$0(boolean z, int i, int i2, boolean z2, long j) {
            if (this.mSurface.isValid()) {
                if (z) {
                    this.mTransaction.setPosition(this.mSurfaceControl, i, i2);
                }
                if (z2) {
                    this.mTransaction.setLayer(this.mSurfaceControl, 5).show(this.mSurfaceControl);
                }
                this.mBBQ.mergeWithNextTransaction(this.mTransaction, j);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updateCurrentContentForTesting() {
            Canvas canvas = new Canvas(this.mCurrentContent);
            Rect rect = new Rect(0, 0, this.mContentWidth, this.mContentHeight);
            Bitmap bitmap = this.mBitmap;
            if (bitmap != null && !bitmap.isRecycled()) {
                canvas.drawBitmap(this.mBitmap, new Rect(0, 0, this.mBitmap.getWidth(), this.mBitmap.getHeight()), rect, (Paint) null);
            }
            this.mOverlay.setBounds(rect);
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
        private boolean mIsDarkMode;
        private boolean mIsFishEyeStyle;
        private int mLeftContentBound;
        private int mMagnifierBackgroundColorDark;
        private int mMagnifierBackgroundColorLight;
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
            this.mHorizontalDefaultSourceToMagnifierOffset = resources.getDimensionPixelSize(R.dimen.default_magnifier_horizontal_offset);
            this.mVerticalDefaultSourceToMagnifierOffset = resources.getDimensionPixelSize(R.dimen.default_magnifier_vertical_offset);
            this.mOverlay = new ColorDrawable(resources.getColor(R.color.default_magnifier_color_overlay, null));
            this.mClippingEnabled = true;
            this.mLeftContentBound = 1;
            this.mTopContentBound = 1;
            this.mRightContentBound = 1;
            this.mBottomContentBound = 1;
            this.mIsFishEyeStyle = false;
            this.mIsDarkMode = (resources.getConfiguration().uiMode & 48) == 32;
            this.mMagnifierBackgroundColorDark = resources.getColor(R.color.sem_magnifier_background_color_dark, null);
            this.mMagnifierBackgroundColorLight = resources.getColor(R.color.sem_magnifier_background_color_light, null);
        }

        public Builder setSize(int i, int i2) {
            Preconditions.checkArgumentPositive(i, "Width should be positive");
            Preconditions.checkArgumentPositive(i2, "Height should be positive");
            this.mWidth = i;
            this.mHeight = i2;
            return this;
        }

        public Builder setInitialZoom(float f) {
            Preconditions.checkArgumentPositive(f, "Zoom should be positive");
            this.mZoom = f;
            return this;
        }

        public Builder setElevation(float f) {
            Preconditions.checkArgumentNonNegative(f, "Elevation should be non-negative");
            this.mElevation = f;
            return this;
        }

        public Builder setCornerRadius(float f) {
            Preconditions.checkArgumentNonNegative(f, "Corner radius should be non-negative");
            this.mCornerRadius = f;
            return this;
        }

        public Builder setOverlay(Drawable drawable) {
            this.mOverlay = drawable;
            return this;
        }

        public Builder setDefaultSourceToMagnifierOffset(int i, int i2) {
            this.mHorizontalDefaultSourceToMagnifierOffset = i;
            this.mVerticalDefaultSourceToMagnifierOffset = i2;
            return this;
        }

        public Builder setClippingEnabled(boolean z) {
            this.mClippingEnabled = z;
            return this;
        }

        public Builder setSourceBounds(int i, int i2, int i3, int i4) {
            this.mLeftContentBound = i;
            this.mTopContentBound = i2;
            this.mRightContentBound = i3;
            this.mBottomContentBound = i4;
            return this;
        }

        Builder setSourceSize(int i, int i2) {
            this.mSourceWidth = i;
            this.mSourceHeight = i2;
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
        InternalPopupWindow internalPopupWindow = this.mWindow;
        if (internalPopupWindow != null) {
            internalPopupWindow.mCallback = callback;
        }
    }

    public Bitmap getContent() {
        Bitmap bitmap;
        InternalPopupWindow internalPopupWindow = this.mWindow;
        if (internalPopupWindow == null) {
            return null;
        }
        synchronized (internalPopupWindow.mLock) {
            bitmap = this.mWindow.mCurrentContent;
        }
        return bitmap;
    }

    public Bitmap getOriginalContent() {
        Bitmap createBitmap;
        InternalPopupWindow internalPopupWindow = this.mWindow;
        if (internalPopupWindow == null) {
            return null;
        }
        synchronized (internalPopupWindow.mLock) {
            createBitmap = Bitmap.createBitmap(this.mWindow.mBitmap);
        }
        return createBitmap;
    }

    public static PointF getMagnifierDefaultSize() {
        Resources system = Resources.getSystem();
        float f = system.getDisplayMetrics().density;
        PointF pointF = new PointF();
        pointF.x = system.getDimension(R.dimen.default_magnifier_width) / f;
        pointF.y = system.getDimension(R.dimen.default_magnifier_height) / f;
        return pointF;
    }
}
