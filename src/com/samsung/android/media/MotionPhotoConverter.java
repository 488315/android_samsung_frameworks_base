package com.samsung.android.media;

import com.samsung.android.media.SemExtendedFormat;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/* loaded from: classes6.dex */
public class MotionPhotoConverter {
    private static final String TAG = "MotionPhotoConverter";
    private static MotionPhotoConverter sInstance;

    private MotionPhotoConverter() {
    }

    public static synchronized MotionPhotoConverter getInstance() {
        MotionPhotoConverter motionPhotoConverter;
        synchronized (MotionPhotoConverter.class) {
            if (sInstance == null) {
                sInstance = new MotionPhotoConverter();
            }
            motionPhotoConverter = sInstance;
        }
        return motionPhotoConverter;
    }

    public synchronized void convertToMp4(String str, String str2) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(str2);
            try {
                fileOutputStream.write(SemExtendedFormat.getData(new File(str), SemExtendedFormat.KeyName.MOTION_PHOTO_DATA));
                fileOutputStream.close();
            } catch (Throwable th) {
                try {
                    fileOutputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
