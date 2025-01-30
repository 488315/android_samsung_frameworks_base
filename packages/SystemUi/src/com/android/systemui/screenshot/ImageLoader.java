package com.android.systemui.screenshot;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import java.io.File;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ImageLoader {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Result {
        public Bitmap bitmap;
        public File fileName;

        public final String toString() {
            return "Result{uri=null, fileName=" + this.fileName + ", bitmap=" + this.bitmap + '}';
        }
    }

    public ImageLoader(ContentResolver contentResolver) {
    }
}
