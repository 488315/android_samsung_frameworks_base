package com.samsung.android.globalactions.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import com.samsung.android.view.SemWindowManager;

/* loaded from: classes5.dex */
public class ScreenCaptureUtil {
    private static final String TAG = "ScreenCaptureUtil";
    private Bitmap mCapture;
    private Context mContext;
    private LogWrapper mLogWrapper;
    private SemWindowManager mSemWindowManager = SemWindowManager.getInstance();
    private WindowManager mWindowManager;

    public ScreenCaptureUtil(Context context, LogWrapper logWrapper) {
        this.mContext = context;
        this.mLogWrapper = logWrapper;
        this.mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }

    public Bitmap takeScreenShot() {
        if (this.mCapture == null) {
            captureScreen();
        }
        return this.mCapture;
    }

    public void clearScreenShot() {
        this.mCapture = null;
    }

    private void captureScreen() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        Display display = this.mWindowManager.getDefaultDisplay();
        int displayId = display.getDisplayId();
        display.getRealMetrics(displayMetrics);
        int lcdWidth = Math.abs(displayMetrics.widthPixels);
        int lcdHeight = Math.abs(displayMetrics.heightPixels);
        Bitmap bitmap = this.mSemWindowManager.screenshot(displayId, WindowManager.LayoutParams.TYPE_GLOBAL_ACTION, false, new Rect(), lcdWidth / 5, lcdHeight / 5, false, 0, true);
        if (bitmap == null) {
            this.mLogWrapper.m266v(TAG, "bitmap is null !!!!");
        } else {
            this.mCapture = bitmap;
        }
    }

    private static Bitmap rotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }
}
