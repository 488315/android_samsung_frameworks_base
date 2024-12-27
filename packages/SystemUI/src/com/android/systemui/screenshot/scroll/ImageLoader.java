package com.android.systemui.screenshot.scroll;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import java.io.File;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ImageLoader {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Result {
        public Bitmap mBitmap;
        public File mFilename;

        public final String toString() {
            return "Result{uri=null, fileName=" + this.mFilename + ", bitmap=" + this.mBitmap + '}';
        }
    }

    public ImageLoader(ContentResolver contentResolver) {
    }
}
