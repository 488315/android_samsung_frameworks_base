package com.android.systemui.screenshot.scroll;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import java.io.File;

public final class ImageLoader {

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
