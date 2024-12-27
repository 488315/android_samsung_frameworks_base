package com.android.server.display;

import android.R;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.display.DisplayManager;
import android.util.Slog;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.Surface;
import android.view.TextureView;
import android.view.ThreadedRenderer;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.android.internal.util.DumpUtils;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.ServiceKeeper$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.am.KillPolicyManager$$ExternalSyntheticOutline0;
import com.android.server.chimera.AggressivePolicyHandler$$ExternalSyntheticOutline0;
import com.android.server.display.OverlayDisplayAdapter.OverlayDisplayHandle.AnonymousClass1;
import com.android.server.display.utils.DebugUtils;

import java.io.PrintWriter;

public final class OverlayDisplayWindow implements DumpUtils.Dump {
    public static final boolean DEBUG = DebugUtils.isDebuggable("OverlayDisplayWindow");
    public final Context mContext;
    public final Display mDefaultDisplay;
    public final DisplayInfo mDefaultDisplayInfo;
    public int mDensityDpi;
    public final AnonymousClass1 mDisplayListener;
    public final DisplayManager mDisplayManager;
    public final GestureDetector mGestureDetector;
    public int mHeight;
    public final OverlayDisplayAdapter.OverlayDisplayHandle mListener;
    public float mLiveScale;
    public float mLiveTranslationX;
    public float mLiveTranslationY;
    public final String mName;
    public final AnonymousClass4 mOnGestureListener;
    public final AnonymousClass5 mOnScaleGestureListener;
    public final AnonymousClass3 mOnTouchListener;
    public final ScaleGestureDetector mScaleGestureDetector;
    public final boolean mSecure;
    public final AnonymousClass2 mSurfaceTextureListener;
    public final TextureView mTextureView;
    public String mTitle;
    public int mWidth;
    public final View mWindowContent;
    public final WindowManager mWindowManager;
    public final WindowManager.LayoutParams mWindowParams;
    public float mWindowScale;
    public boolean mWindowVisible;
    public int mWindowX;
    public int mWindowY;

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.server.display.OverlayDisplayWindow$1] */
    public OverlayDisplayWindow(
            Context context,
            String str,
            int i,
            int i2,
            int i3,
            int i4,
            boolean z,
            OverlayDisplayAdapter.OverlayDisplayHandle overlayDisplayHandle) {
        DisplayInfo displayInfo = new DisplayInfo();
        this.mDefaultDisplayInfo = displayInfo;
        this.mLiveScale = 1.0f;
        this.mDisplayListener =
                new DisplayManager
                        .DisplayListener() { // from class:
                                             // com.android.server.display.OverlayDisplayWindow.1
                    @Override // android.hardware.display.DisplayManager.DisplayListener
                    public final void onDisplayAdded(int i5) {}

                    @Override // android.hardware.display.DisplayManager.DisplayListener
                    public final void onDisplayChanged(int i5) {
                        if (i5 == OverlayDisplayWindow.this.mDefaultDisplay.getDisplayId()) {
                            if (!OverlayDisplayWindow.this.updateDefaultDisplayInfo()) {
                                OverlayDisplayWindow.this.dismiss();
                                return;
                            }
                            OverlayDisplayWindow.this.relayout();
                            OverlayDisplayWindow overlayDisplayWindow = OverlayDisplayWindow.this;
                            OverlayDisplayAdapter.OverlayDisplayHandle overlayDisplayHandle2 =
                                    overlayDisplayWindow.mListener;
                            int i6 = overlayDisplayWindow.mDefaultDisplayInfo.state;
                            synchronized (OverlayDisplayAdapter.this.mSyncRoot) {
                                try {
                                    OverlayDisplayAdapter.OverlayDisplayHandle.AnonymousClass1
                                            anonymousClass1 = overlayDisplayHandle2.mDevice;
                                    if (anonymousClass1 != null) {
                                        anonymousClass1.mState = i6;
                                        anonymousClass1.mInfo = null;
                                        OverlayDisplayAdapter.this.sendDisplayDeviceEventLocked(
                                                anonymousClass1, 2);
                                    }
                                } finally {
                                }
                            }
                        }
                    }

                    @Override // android.hardware.display.DisplayManager.DisplayListener
                    public final void onDisplayRemoved(int i5) {
                        if (i5 == OverlayDisplayWindow.this.mDefaultDisplay.getDisplayId()) {
                            OverlayDisplayWindow.this.dismiss();
                        }
                    }
                };
        TextureView.SurfaceTextureListener surfaceTextureListener =
                new TextureView
                        .SurfaceTextureListener() { // from class:
                                                    // com.android.server.display.OverlayDisplayWindow.2
                    @Override // android.view.TextureView.SurfaceTextureListener
                    public final void onSurfaceTextureAvailable(
                            SurfaceTexture surfaceTexture, int i5, int i6) {
                        OverlayDisplayWindow overlayDisplayWindow = OverlayDisplayWindow.this;
                        OverlayDisplayAdapter.OverlayDisplayHandle overlayDisplayHandle2 =
                                overlayDisplayWindow.mListener;
                        float refreshRate =
                                overlayDisplayWindow.mDefaultDisplayInfo.getRefreshRate();
                        DisplayInfo displayInfo2 = OverlayDisplayWindow.this.mDefaultDisplayInfo;
                        long j = displayInfo2.presentationDeadlineNanos;
                        int i7 = displayInfo2.state;
                        synchronized (OverlayDisplayAdapter.this.mSyncRoot) {
                            OverlayDisplayAdapter.OverlayDisplayHandle.AnonymousClass1
                                    anonymousClass1 =
                                            overlayDisplayHandle2
                                            .new AnonymousClass1(
                                                    DisplayControl.createVirtualDisplay(
                                                            overlayDisplayHandle2.mName,
                                                            overlayDisplayHandle2.mFlags.mSecure),
                                                    overlayDisplayHandle2.mName,
                                                    overlayDisplayHandle2.mModes,
                                                    overlayDisplayHandle2.mActiveMode,
                                                    refreshRate,
                                                    j,
                                                    overlayDisplayHandle2.mFlags,
                                                    i7,
                                                    surfaceTexture,
                                                    overlayDisplayHandle2.mNumber);
                            overlayDisplayHandle2.mDevice = anonymousClass1;
                            OverlayDisplayAdapter.this.sendDisplayDeviceEventLocked(
                                    anonymousClass1, 1);
                        }
                    }

                    @Override // android.view.TextureView.SurfaceTextureListener
                    public final boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
                        OverlayDisplayAdapter.OverlayDisplayHandle overlayDisplayHandle2 =
                                OverlayDisplayWindow.this.mListener;
                        synchronized (OverlayDisplayAdapter.this.mSyncRoot) {
                            try {
                                OverlayDisplayAdapter.OverlayDisplayHandle.AnonymousClass1
                                        anonymousClass1 = overlayDisplayHandle2.mDevice;
                                if (anonymousClass1 != null) {
                                    anonymousClass1.mSurfaceTexture = null;
                                    Surface surface = anonymousClass1.mSurface;
                                    if (surface != null) {
                                        surface.release();
                                        anonymousClass1.mSurface = null;
                                    }
                                    DisplayControl.destroyVirtualDisplay(
                                            anonymousClass1.mDisplayToken);
                                    OverlayDisplayAdapter.this.sendDisplayDeviceEventLocked(
                                            overlayDisplayHandle2.mDevice, 3);
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        return true;
                    }

                    @Override // android.view.TextureView.SurfaceTextureListener
                    public final void onSurfaceTextureSizeChanged(
                            SurfaceTexture surfaceTexture, int i5, int i6) {}

                    @Override // android.view.TextureView.SurfaceTextureListener
                    public final void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {}
                };
        View.OnTouchListener onTouchListener =
                new View
                        .OnTouchListener() { // from class:
                                             // com.android.server.display.OverlayDisplayWindow.3
                    @Override // android.view.View.OnTouchListener
                    public final boolean onTouch(View view, MotionEvent motionEvent) {
                        float x = motionEvent.getX();
                        float y = motionEvent.getY();
                        motionEvent.setLocation(motionEvent.getRawX(), motionEvent.getRawY());
                        OverlayDisplayWindow.this.mGestureDetector.onTouchEvent(motionEvent);
                        OverlayDisplayWindow.this.mScaleGestureDetector.onTouchEvent(motionEvent);
                        int actionMasked = motionEvent.getActionMasked();
                        if (actionMasked == 1 || actionMasked == 3) {
                            OverlayDisplayWindow overlayDisplayWindow = OverlayDisplayWindow.this;
                            WindowManager.LayoutParams layoutParams =
                                    overlayDisplayWindow.mWindowParams;
                            overlayDisplayWindow.mWindowX = layoutParams.x;
                            overlayDisplayWindow.mWindowY = layoutParams.y;
                            overlayDisplayWindow.mWindowScale =
                                    overlayDisplayWindow.mTextureView.getScaleX();
                            overlayDisplayWindow.mLiveTranslationX =
                                    FullScreenMagnificationGestureHandler.MAX_SCALE;
                            overlayDisplayWindow.mLiveTranslationY =
                                    FullScreenMagnificationGestureHandler.MAX_SCALE;
                            overlayDisplayWindow.mLiveScale = 1.0f;
                        }
                        motionEvent.setLocation(x, y);
                        return true;
                    }
                };
        GestureDetector.SimpleOnGestureListener simpleOnGestureListener =
                new GestureDetector
                        .SimpleOnGestureListener() { // from class:
                                                     // com.android.server.display.OverlayDisplayWindow.4
                    @Override // android.view.GestureDetector.SimpleOnGestureListener,
                              // android.view.GestureDetector.OnGestureListener
                    public final boolean onScroll(
                            MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                        OverlayDisplayWindow overlayDisplayWindow = OverlayDisplayWindow.this;
                        overlayDisplayWindow.mLiveTranslationX -= f;
                        overlayDisplayWindow.mLiveTranslationY -= f2;
                        overlayDisplayWindow.relayout();
                        return true;
                    }
                };
        ScaleGestureDetector.SimpleOnScaleGestureListener simpleOnScaleGestureListener =
                new ScaleGestureDetector
                        .SimpleOnScaleGestureListener() { // from class:
                                                          // com.android.server.display.OverlayDisplayWindow.5
                    @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener,
                              // android.view.ScaleGestureDetector.OnScaleGestureListener
                    public final boolean onScale(ScaleGestureDetector scaleGestureDetector) {
                        OverlayDisplayWindow overlayDisplayWindow = OverlayDisplayWindow.this;
                        overlayDisplayWindow.mLiveScale =
                                scaleGestureDetector.getScaleFactor()
                                        * overlayDisplayWindow.mLiveScale;
                        OverlayDisplayWindow.this.relayout();
                        return true;
                    }
                };
        ThreadedRenderer.disableVsync();
        this.mContext = context;
        this.mName = str;
        this.mSecure = z;
        this.mListener = overlayDisplayHandle;
        this.mDisplayManager = (DisplayManager) context.getSystemService("display");
        this.mWindowManager = (WindowManager) context.getSystemService("window");
        this.mDefaultDisplay = context.getDisplay();
        updateDefaultDisplayInfo();
        resize(i, i2, i3, false);
        View inflate =
                LayoutInflater.from(context)
                        .inflate(R.layout.preference_list_content_material, (ViewGroup) null);
        this.mWindowContent = inflate;
        inflate.setOnTouchListener(onTouchListener);
        TextureView textureView =
                (TextureView) this.mWindowContent.findViewById(R.id.remote_views_override_id);
        this.mTextureView = textureView;
        textureView.setPivotX(FullScreenMagnificationGestureHandler.MAX_SCALE);
        this.mTextureView.setPivotY(FullScreenMagnificationGestureHandler.MAX_SCALE);
        this.mTextureView.getLayoutParams().width = this.mWidth;
        this.mTextureView.getLayoutParams().height = this.mHeight;
        this.mTextureView.setOpaque(false);
        this.mTextureView.setSurfaceTextureListener(surfaceTextureListener);
        ((TextView) this.mWindowContent.findViewById(R.id.remote_views_stable_id))
                .setText(this.mTitle);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(2026);
        this.mWindowParams = layoutParams;
        int i5 = layoutParams.flags;
        layoutParams.flags = 16778024 | i5;
        if (z) {
            layoutParams.flags = i5 | 16786216;
        }
        layoutParams.privateFlags |= 2;
        layoutParams.alpha = 0.8f;
        layoutParams.gravity = 51;
        layoutParams.setTitle(this.mTitle);
        this.mGestureDetector = new GestureDetector(context, simpleOnGestureListener);
        this.mScaleGestureDetector =
                new ScaleGestureDetector(context, simpleOnScaleGestureListener);
        this.mWindowX = (i4 & 3) == 3 ? 0 : displayInfo.logicalWidth;
        this.mWindowY = (i4 & 48) != 48 ? displayInfo.logicalHeight : 0;
        this.mWindowScale = 0.5f;
    }

    public final void dismiss() {
        if (this.mWindowVisible) {
            this.mDisplayManager.unregisterDisplayListener(this.mDisplayListener);
            this.mWindowManager.removeView(this.mWindowContent);
            this.mWindowVisible = false;
        }
    }

    public final void dump(PrintWriter printWriter, String str) {
        StringBuilder m =
                KillPolicyManager$$ExternalSyntheticOutline0.m(
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                                new StringBuilder("mWindowVisible="),
                                                this.mWindowVisible,
                                                printWriter,
                                                "mWindowX="),
                                        this.mWindowX,
                                        printWriter,
                                        "mWindowY="),
                                this.mWindowY,
                                printWriter,
                                "mWindowScale="),
                        this.mWindowScale,
                        printWriter,
                        "mWindowParams=");
        m.append(this.mWindowParams);
        printWriter.println(m.toString());
        if (this.mTextureView != null) {
            printWriter.println("mTextureView.getScaleX()=" + this.mTextureView.getScaleX());
            printWriter.println("mTextureView.getScaleY()=" + this.mTextureView.getScaleY());
        }
        AggressivePolicyHandler$$ExternalSyntheticOutline0.m(
                KillPolicyManager$$ExternalSyntheticOutline0.m(
                        KillPolicyManager$$ExternalSyntheticOutline0.m(
                                new StringBuilder("mLiveTranslationX="),
                                this.mLiveTranslationX,
                                printWriter,
                                "mLiveTranslationY="),
                        this.mLiveTranslationY,
                        printWriter,
                        "mLiveScale="),
                this.mLiveScale,
                printWriter);
    }

    public final void relayout() {
        if (this.mWindowVisible) {
            updateWindowParams();
            this.mWindowManager.updateViewLayout(this.mWindowContent, this.mWindowParams);
        }
    }

    public final void resize(int i, int i2, int i3, boolean z) {
        this.mWidth = i;
        this.mHeight = i2;
        this.mDensityDpi = i3;
        this.mTitle =
                this.mContext
                        .getResources()
                        .getString(
                                R.string.image_wallpaper_component,
                                this.mName,
                                Integer.valueOf(this.mWidth),
                                Integer.valueOf(this.mHeight),
                                Integer.valueOf(this.mDensityDpi));
        if (this.mSecure) {
            this.mTitle += this.mContext.getResources().getString(R.string.imTypeWork);
        }
        if (z) {
            relayout();
        }
    }

    public final boolean updateDefaultDisplayInfo() {
        if (this.mDefaultDisplay.getDisplayInfo(this.mDefaultDisplayInfo)) {
            return true;
        }
        Slog.w(
                "OverlayDisplayWindow",
                "Cannot show overlay display because there is no default display upon which to show"
                    + " it.");
        return false;
    }

    public final void updateWindowParams() {
        float max =
                Math.max(
                        0.3f,
                        Math.min(
                                1.0f,
                                Math.min(
                                        Math.min(
                                                this.mWindowScale * this.mLiveScale,
                                                this.mDefaultDisplayInfo.logicalWidth
                                                        / this.mWidth),
                                        this.mDefaultDisplayInfo.logicalHeight / this.mHeight)));
        float f = ((max / this.mWindowScale) - 1.0f) * 0.5f;
        int i = (int) (this.mWidth * max);
        int i2 = (int) (this.mHeight * max);
        int i3 = (int) ((this.mWindowX + this.mLiveTranslationX) - (i * f));
        int i4 = (int) ((this.mWindowY + this.mLiveTranslationY) - (i2 * f));
        int max2 = Math.max(0, Math.min(i3, this.mDefaultDisplayInfo.logicalWidth - i));
        int max3 = Math.max(0, Math.min(i4, this.mDefaultDisplayInfo.logicalHeight - i2));
        if (DEBUG) {
            StringBuilder sb = new StringBuilder("updateWindowParams: scale=");
            sb.append(max);
            sb.append(", offsetScale=");
            sb.append(f);
            sb.append(", x=");
            ServiceKeeper$$ExternalSyntheticOutline0.m(max2, max3, ", y=", ", width=", sb);
            sb.append(i);
            sb.append(", height=");
            sb.append(i2);
            Slog.d("OverlayDisplayWindow", sb.toString());
        }
        this.mTextureView.setScaleX(max);
        this.mTextureView.setScaleY(max);
        WindowManager.LayoutParams layoutParams = this.mWindowParams;
        layoutParams.x = max2;
        layoutParams.y = max3;
        layoutParams.width = i;
        layoutParams.height = i2;
    }
}
