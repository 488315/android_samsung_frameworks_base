package com.samsung.vekit.Listener;

import android.graphics.Bitmap;

public interface CaptureFrameTaskListener {
    void onCaptureFrameReceived(int i, Bitmap bitmap);
}
