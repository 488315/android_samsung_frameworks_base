package com.android.systemui.screenshot.scroll;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import java.io.File;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class ImageLoader {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Result {
        public Bitmap mBitmap;
        public File mFilename;

        public final String toString() {
            return "Result{uri=null, fileName=" + this.mFilename + ", bitmap=" + this.mBitmap + '}';
        }
    }

    public ImageLoader(ContentResolver contentResolver) {
    }
}
