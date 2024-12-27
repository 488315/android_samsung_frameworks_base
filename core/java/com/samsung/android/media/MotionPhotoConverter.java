package com.samsung.android.media;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MotionPhotoConverter {
    private static final String TAG = "MotionPhotoConverter";
    private static MotionPhotoConverter sInstance;

    private MotionPhotoConverter() {}

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

    public synchronized void convertToMp4(String srcPath, String targetPath) {
        try {
            FileOutputStream outputStream = new FileOutputStream(targetPath);
            try {
                byte[] buf =
                        SemExtendedFormat.getData(
                                new File(srcPath), SemExtendedFormat.KeyName.MOTION_PHOTO_DATA);
                outputStream.write(buf);
                outputStream.close();
            } catch (Throwable th) {
                try {
                    outputStream.close();
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
