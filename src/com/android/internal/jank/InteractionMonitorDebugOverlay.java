package com.android.internal.jank;

import android.app.Application;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Handler;
import android.os.Trace;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import java.util.ArrayList;

/* loaded from: classes5.dex */
class InteractionMonitorDebugOverlay {
    private static final long HIDE_OVERLAY_DELAY = 2000;
    private static final int REASON_STILL_RUNNING = -1000;
    private static final String TAG = "InteractionMonitorDebug";
    private final Application mCurrentApplication;
    private final DebugOverlayView mDebugOverlayView;
    private final Handler mUiThread;
    private final WindowManager mWindowManager;
    private final ArrayList<TrackerState> mRunningCujs = new ArrayList<>();
    private final Runnable mHideOverlayRunnable = new Runnable() { // from class: com.android.internal.jank.InteractionMonitorDebugOverlay.1
        @Override // java.lang.Runnable
        public void run() {
            InteractionMonitorDebugOverlay.this.mRunningCujs.clear();
            InteractionMonitorDebugOverlay.this.mDebugOverlayView.setVisibility(4);
        }
    };

    InteractionMonitorDebugOverlay(Application application, Handler handler, int i, double d) {
        this.mCurrentApplication = application;
        this.mUiThread = handler;
        WindowManager windowManager = (WindowManager) application.createDisplayContext(((DisplayManager) application.getSystemService(DisplayManager.class)).getDisplay(0)).createWindowContext(2006, null).getSystemService(WindowManager.class);
        this.mWindowManager = windowManager;
        Rect bounds = windowManager.getCurrentWindowMetrics().getBounds();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(2006, 8388920, -3);
        layoutParams.privateFlags |= 80;
        layoutParams.layoutInDisplayCutoutMode = 3;
        layoutParams.setFitInsetsTypes(0);
        layoutParams.privateFlags |= 16777216;
        layoutParams.width = bounds.width();
        layoutParams.height = bounds.height();
        layoutParams.gravity = 17;
        layoutParams.setTitle("InteractionMonitorDebugOverlay");
        if (!handler.getLooper().isCurrentThread()) {
            Log.e(TAG, "InteractionMonitorDebugOverlay must be constructed on InteractionJankMonitor's worker thread");
        }
        DebugOverlayView debugOverlayView = new DebugOverlayView(application, i, d);
        this.mDebugOverlayView = debugOverlayView;
        windowManager.addView(debugOverlayView, layoutParams);
    }

    void onTrackerAdded(final int i, final int i2) {
        this.mUiThread.removeCallbacks(this.mHideOverlayRunnable);
        this.mUiThread.post(new Runnable() { // from class: com.android.internal.jank.InteractionMonitorDebugOverlay$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onTrackerAdded$0(i, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onTrackerAdded$0(int i, int i2) {
        Log.i(TAG, Cuj.getNameOfCuj(i) + " started (cookie=" + i2 + NavigationBarInflaterView.KEY_CODE_END);
        this.mRunningCujs.add(new TrackerState(i, i2));
        this.mDebugOverlayView.setVisibility(0);
        this.mDebugOverlayView.invalidate();
    }

    void onTrackerRemoved(final int i, final int i2, final int i3) {
        this.mUiThread.post(new Runnable() { // from class: com.android.internal.jank.InteractionMonitorDebugOverlay$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$onTrackerRemoved$1(i, i3, i2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onTrackerRemoved$1(int i, int i2, int i3) {
        TrackerState trackerState = null;
        boolean z = true;
        for (int i4 = 0; i4 < this.mRunningCujs.size(); i4++) {
            TrackerState trackerState2 = this.mRunningCujs.get(i4);
            if (trackerState2.mCuj == i && trackerState2.mCookie == i2) {
                trackerState = trackerState2;
            } else {
                z = z && trackerState2.mState != -1000;
            }
        }
        if (trackerState != null) {
            trackerState.mState = i3;
        }
        String nameOfCuj = Cuj.getNameOfCuj(i);
        StringBuilder sb = new StringBuilder();
        sb.append(nameOfCuj);
        sb.append(i3 == 0 ? " ended" : " cancelled");
        sb.append(" (cookie=");
        sb.append(i2);
        sb.append(NavigationBarInflaterView.KEY_CODE_END);
        Log.i(TAG, sb.toString());
        if (z) {
            Log.i(TAG, "All CUJs ended");
            this.mUiThread.postDelayed(this.mHideOverlayRunnable, HIDE_OVERLAY_DELAY);
        }
        this.mDebugOverlayView.invalidate();
    }

    void dispose() {
        this.mUiThread.post(new Runnable() { // from class: com.android.internal.jank.InteractionMonitorDebugOverlay$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$dispose$2();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$dispose$2() {
        this.mWindowManager.removeView(this.mDebugOverlayView);
    }

    private static class TrackerState {
        final int mCookie;
        final int mCuj;
        int mState;

        private TrackerState(int i, int i2) {
            this.mCuj = i;
            this.mCookie = i2;
            this.mState = -1000;
        }
    }

    private class DebugOverlayView extends View {
        private static final String TRACK_NAME = "InteractionJankMonitor";
        private final int mBgColor;
        final int mCujFontSize;
        final float mCujNameTextHeight;
        final float mCujStatusWidth;
        private final Paint.FontMetrics mDebugFontMetrics;
        private final Paint mDebugPaint;
        private final float mDensity;
        final int mPackageNameFontSize;
        private final String mPackageNameText;
        final float mPackageNameTextHeight;
        final float mPackageNameWidth;
        final int mPadding;
        private final double mYOffset;

        private DebugOverlayView(Context context, int i, double d) {
            super(context);
            setVisibility(4);
            this.mBgColor = i;
            this.mYOffset = d;
            this.mDensity = getContext().getResources().getDisplayMetrics().density;
            Paint paint = new Paint();
            this.mDebugPaint = paint;
            paint.setAntiAlias(false);
            this.mDebugFontMetrics = new Paint.FontMetrics();
            String str = "package:" + InteractionMonitorDebugOverlay.this.mCurrentApplication.getPackageName();
            this.mPackageNameText = str;
            this.mPadding = dipToPx(5);
            int iDipToPx = dipToPx(12);
            this.mPackageNameFontSize = iDipToPx;
            int iDipToPx2 = dipToPx(18);
            this.mCujFontSize = iDipToPx2;
            float textHeight = getTextHeight(iDipToPx2);
            this.mCujNameTextHeight = textHeight;
            this.mCujStatusWidth = textHeight * 1.2f;
            this.mPackageNameTextHeight = getTextHeight(iDipToPx);
            this.mPackageNameWidth = getWidthOfText(str, iDipToPx);
        }

        private int dipToPx(int i) {
            return (int) ((this.mDensity * i) + 0.5f);
        }

        private float getTextHeight(int i) {
            this.mDebugPaint.setTextSize(i);
            this.mDebugPaint.getFontMetrics(this.mDebugFontMetrics);
            return this.mDebugFontMetrics.descent - this.mDebugFontMetrics.ascent;
        }

        private float getWidthOfText(String str, int i) {
            this.mDebugPaint.setTextSize(i);
            return this.mDebugPaint.measureText(str);
        }

        private float getWidthOfLongestCujName(int i) {
            this.mDebugPaint.setTextSize(i);
            float f = 0.0f;
            for (int i2 = 0; i2 < InteractionMonitorDebugOverlay.this.mRunningCujs.size(); i2++) {
                float fMeasureText = this.mDebugPaint.measureText(Cuj.getNameOfCuj(((TrackerState) InteractionMonitorDebugOverlay.this.mRunningCujs.get(i2)).mCuj));
                if (fMeasureText > f) {
                    f = fMeasureText;
                }
            }
            return f;
        }

        @Override // android.view.View
        protected void onDraw(Canvas canvas) {
            String str;
            super.onDraw(canvas);
            Trace.asyncTraceForTrackBegin(4096L, TRACK_NAME, "DEBUG_OVERLAY_DRAW", 0);
            int height = getHeight();
            int width = getWidth();
            int i = (int) (height * this.mYOffset);
            float fMax = Math.max(this.mPackageNameWidth, getWidthOfLongestCujName(this.mCujFontSize)) + this.mCujStatusWidth;
            canvas.translate((int) ((width - fMax) / 2.0f), i);
            this.mDebugPaint.setColor(this.mBgColor);
            int i2 = this.mPadding;
            canvas.drawRect((-i2) * 2, -i2, (i2 * 2) + fMax, (i2 * 2) + this.mPackageNameTextHeight + (this.mCujNameTextHeight * InteractionMonitorDebugOverlay.this.mRunningCujs.size()), this.mDebugPaint);
            this.mDebugPaint.setTextSize(this.mPackageNameFontSize);
            this.mDebugPaint.setColor(-16777216);
            this.mDebugPaint.setStrikeThruText(false);
            canvas.translate(0.0f, this.mPackageNameTextHeight);
            canvas.drawText(this.mPackageNameText, 0.0f, 0.0f, this.mDebugPaint);
            this.mDebugPaint.setTextSize(this.mCujFontSize);
            for (int i3 = 0; i3 < InteractionMonitorDebugOverlay.this.mRunningCujs.size(); i3++) {
                TrackerState trackerState = (TrackerState) InteractionMonitorDebugOverlay.this.mRunningCujs.get(i3);
                int i4 = trackerState.mState;
                if (i4 == -1000) {
                    this.mDebugPaint.setColor(-16777216);
                    this.mDebugPaint.setStrikeThruText(false);
                    str = "☐";
                } else if (i4 == 0) {
                    this.mDebugPaint.setColor(Color.GRAY);
                    this.mDebugPaint.setStrikeThruText(false);
                    str = "✅";
                } else {
                    this.mDebugPaint.setColor(-65536);
                    this.mDebugPaint.setStrikeThruText(true);
                    str = "❌";
                }
                String nameOfCuj = Cuj.getNameOfCuj(trackerState.mCuj);
                canvas.translate(0.0f, this.mCujNameTextHeight);
                canvas.drawText(str, 0.0f, 0.0f, this.mDebugPaint);
                canvas.drawText(nameOfCuj, this.mCujStatusWidth, 0.0f, this.mDebugPaint);
            }
            Trace.asyncTraceForTrackEnd(4096L, TRACK_NAME, 0);
        }
    }
}
