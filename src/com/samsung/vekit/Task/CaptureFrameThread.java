package com.samsung.vekit.Task;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Handler;
import com.samsung.vekit.Common.Object.CaptureInfo;
import com.samsung.vekit.Common.VEContext;
import com.samsung.vekit.Item.ImageItem;
import com.samsung.vekit.Item.Item;
import com.samsung.vekit.Listener.CaptureFrameTaskListener;
import com.samsung.vekit.Task.CaptureFrameTask;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/* loaded from: classes6.dex */
public class CaptureFrameThread extends Thread {
    VEContext context;
    Handler handler;
    private boolean isRunning = false;
    private BlockingDeque<CaptureFrameTask> queue = new LinkedBlockingDeque();

    public CaptureFrameThread(VEContext vEContext, Handler handler) {
        this.context = vEContext;
        this.handler = handler;
    }

    public void startThread() {
        start();
        this.isRunning = true;
    }

    public void stopThread() {
        this.queue.clear();
        this.isRunning = false;
        interrupt();
    }

    public void addRequest(ImageItem imageItem, int i, int i2, CaptureFrameTaskListener captureFrameTaskListener) {
        CaptureFrameTask captureFrameTask = new CaptureFrameTask(imageItem, i, i2, captureFrameTaskListener);
        synchronized (this.queue) {
            try {
                this.queue.put(captureFrameTask);
                this.queue.notify();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void addRequest(int i, int i2, CaptureFrameTaskListener captureFrameTaskListener) {
        CaptureFrameTask captureFrameTask = new CaptureFrameTask(i, i2, captureFrameTaskListener);
        synchronized (this.queue) {
            try {
                this.queue.put(captureFrameTask);
                this.queue.notify();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void addRequest(Item item, int i, int i2, CaptureFrameTask.CaptureType captureType, CaptureFrameTaskListener captureFrameTaskListener) {
        CaptureFrameTask captureFrameTask = new CaptureFrameTask(item, i, i2, captureType, captureFrameTaskListener);
        synchronized (this.queue) {
            try {
                this.queue.put(captureFrameTask);
                this.queue.notify();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void addRequest(int i, int i2, int i3, int i4, CaptureFrameTask.CaptureType captureType, CaptureFrameTaskListener captureFrameTaskListener) {
        CaptureFrameTask captureFrameTask = new CaptureFrameTask(i, i2, i3, i4, captureType, captureFrameTaskListener);
        synchronized (this.queue) {
            try {
                this.queue.put(captureFrameTask);
                this.queue.notify();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        CaptureFrameTask captureFrameTaskPoll;
        while (this.isRunning) {
            synchronized (this.queue) {
                while (this.queue.isEmpty()) {
                    try {
                        this.queue.wait();
                    } catch (InterruptedException unused) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
                captureFrameTaskPoll = this.queue.poll();
            }
            if (captureFrameTaskPoll != null) {
                if (captureFrameTaskPoll.getCaptureType() == CaptureFrameTask.CaptureType.ORIGINAL_FRAME) {
                    this.handler.sendMessage(this.handler.obtainMessage(-1, new CaptureInfo(captureFrameTaskPoll.getOutputWidth(), captureFrameTaskPoll.getOutputHeight(), captureFrameTaskPoll.getListener(), this.context.getNativeInterface().captureLatestFrame(captureFrameTaskPoll.getOutputWidth(), captureFrameTaskPoll.getOutputHeight()))));
                } else if (captureFrameTaskPoll.getCaptureType() == CaptureFrameTask.CaptureType.RENDERED_FRAME) {
                    this.handler.sendMessage(this.handler.obtainMessage(captureFrameTaskPoll.getItem().getId(), new CaptureInfo(captureFrameTaskPoll.getOutputWidth(), captureFrameTaskPoll.getOutputHeight(), captureFrameTaskPoll.getListener(), this.context.getNativeInterface().captureAnimatedFrame(captureFrameTaskPoll.getItem(), captureFrameTaskPoll.getOutputWidth(), captureFrameTaskPoll.getOutputHeight()))));
                } else {
                    Bitmap bitmapCaptureSuperHDRFrame = this.context.getNativeInterface().captureSuperHDRFrame(captureFrameTaskPoll.getItem(), captureFrameTaskPoll.getOutputWidth(), captureFrameTaskPoll.getOutputHeight(), captureFrameTaskPoll.getOutputCenterX(), captureFrameTaskPoll.getOutputCenterY());
                    int id = captureFrameTaskPoll.getItem() != null ? captureFrameTaskPoll.getItem().getId() : -1;
                    if (bitmapCaptureSuperHDRFrame != null && !bitmapCaptureSuperHDRFrame.isRecycled()) {
                        Matrix matrix = new Matrix();
                        matrix.postScale(1.0f, -1.0f, bitmapCaptureSuperHDRFrame.getWidth() / 2.0f, bitmapCaptureSuperHDRFrame.getHeight() / 2.0f);
                        bitmapCaptureSuperHDRFrame = Bitmap.createBitmap(bitmapCaptureSuperHDRFrame, 0, 0, bitmapCaptureSuperHDRFrame.getWidth(), bitmapCaptureSuperHDRFrame.getHeight(), matrix, true);
                    }
                    this.handler.sendMessage(this.handler.obtainMessage(id, new CaptureInfo(captureFrameTaskPoll.getOutputWidth(), captureFrameTaskPoll.getOutputHeight(), captureFrameTaskPoll.getListener(), bitmapCaptureSuperHDRFrame)));
                }
            }
        }
    }
}
