package com.samsung.android.globalactions.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import com.samsung.android.view.SemWindowManager;

/* loaded from: classes6.dex */
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
        Display defaultDisplay = this.mWindowManager.getDefaultDisplay();
        int displayId = defaultDisplay.getDisplayId();
        defaultDisplay.getRealMetrics(displayMetrics);
        Bitmap bitmapScreenshot = this.mSemWindowManager.screenshot(displayId, WindowManager.LayoutParams.TYPE_GLOBAL_ACTION, false, new Rect(), Math.abs(displayMetrics.widthPixels) / 5, Math.abs(displayMetrics.heightPixels) / 5, false, 0, true);
        if (bitmapScreenshot == null) {
            this.mLogWrapper.v(TAG, "bitmap is null !!!!");
        } else {
            this.mCapture = bitmapScreenshot;
        }
    }

    private static Bitmap rotateBitmap(Bitmap bitmap, float f) {
        Matrix matrix = new Matrix();
        matrix.postRotate(f);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
}
