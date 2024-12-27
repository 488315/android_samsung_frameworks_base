package com.android.server.wm;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import com.android.server.am.ActivityManagerService$$ExternalSyntheticOutline0;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class WmScreenshotFileController {
    public int mFileNumber;
    public int mFocusedTaskNumber;
    public int mRotationLayerNumber;
    public WindowManagerService mService;
    public int mTargetWindowNumber;

    public final void saveBitmapToScreenshotFile(String str, Bitmap bitmap, PrintWriter printWriter, int i) {
        String m;
        int i2;
        File file = new File(Environment.getDataSystemCeDirectory(this.mService.mCurrentUserId), "screenshot");
        if (!file.exists() && !file.mkdir()) {
            if (printWriter == null) {
                Log.e("WindowManager", "Failed create directory");
                return;
            }
            printWriter.println("Failed create directory");
            printWriter.println("dir:" + file);
            return;
        }
        int i3 = 0;
        if ("target_window".equals(str)) {
            i2 = this.mTargetWindowNumber;
            if (i2 < 100) {
                this.mTargetWindowNumber = i2 + 1;
                i3 = i2;
                m = str + "_" + i3 + "_d" + i + ".jpg";
            } else {
                this.mTargetWindowNumber = 0;
                m = str + "_" + i3 + "_d" + i + ".jpg";
            }
        } else if ("focused_task".equals(str)) {
            i2 = this.mFocusedTaskNumber;
            if (i2 < 100) {
                this.mFocusedTaskNumber = i2 + 1;
                i3 = i2;
                m = str + "_" + i3 + "_d" + i + ".jpg";
            } else {
                this.mFocusedTaskNumber = 0;
                m = str + "_" + i3 + "_d" + i + ".jpg";
            }
        } else if ("rotation".equals(str)) {
            i2 = this.mRotationLayerNumber;
            if (i2 < 100) {
                this.mRotationLayerNumber = i2 + 1;
                i3 = i2;
                m = str + "_" + i3 + "_d" + i + ".jpg";
            } else {
                this.mRotationLayerNumber = 0;
                m = str + "_" + i3 + "_d" + i + ".jpg";
            }
        } else {
            StringBuilder sb = new StringBuilder();
            int i4 = this.mFileNumber;
            this.mFileNumber = i4 + 1;
            m = ActivityManagerService$$ExternalSyntheticOutline0.m(i4, i, "_d", ".jpg", sb);
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(file, m));
            try {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                if (printWriter != null) {
                    printWriter.println("Save fileName:" + m);
                } else {
                    Log.d("WindowManager", "Save fileName:" + m);
                }
                fileOutputStream.close();
            } finally {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
