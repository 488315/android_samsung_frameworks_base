package com.samsung.android.photoremaster;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import java.util.List;

/* loaded from: classes5.dex */
public interface IDirector {

    public interface ProgressUpdateListener {
        void onUpdateMetadata(String str);

        void onUpdateProgress(double d, int i, int i2);
    }

    void deinit();

    Bitmap getBitmapParam(int i);

    String getFocusRoi(String str, String str2);

    int getIntParam(int i);

    long getLongParam(int i);

    String getStringParam(int i);

    void init(Context context);

    boolean processImage(int i, List<Integer> list);

    void setBitmapParam(int i, Bitmap bitmap);

    void setLongParam(int i, long j);

    void setProgressUpdateListener(ProgressUpdateListener progressUpdateListener);

    void setStringParam(int i, String str);

    void setUriParam(int i, Uri uri);

    void stop();

    boolean tryInit(Context context);
}
