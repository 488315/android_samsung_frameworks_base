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
        CaptureFrameTask poll;
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
                poll = this.queue.poll();
            }
            if (poll != null) {
                if (poll.getCaptureType() == CaptureFrameTask.CaptureType.ORIGINAL_FRAME) {
                    this.handler.sendMessage(this.handler.obtainMessage(-1, new CaptureInfo(poll.getOutputWidth(), poll.getOutputHeight(), poll.getListener(), this.context.getNativeInterface().captureLatestFrame(poll.getOutputWidth(), poll.getOutputHeight()))));
                } else if (poll.getCaptureType() == CaptureFrameTask.CaptureType.RENDERED_FRAME) {
                    this.handler.sendMessage(this.handler.obtainMessage(poll.getItem().getId(), new CaptureInfo(poll.getOutputWidth(), poll.getOutputHeight(), poll.getListener(), this.context.getNativeInterface().captureAnimatedFrame(poll.getItem(), poll.getOutputWidth(), poll.getOutputHeight()))));
                } else {
                    Bitmap captureSuperHDRFrame = this.context.getNativeInterface().captureSuperHDRFrame(poll.getItem(), poll.getOutputWidth(), poll.getOutputHeight(), poll.getOutputCenterX(), poll.getOutputCenterY());
                    int id = poll.getItem() != null ? poll.getItem().getId() : -1;
                    if (captureSuperHDRFrame != null && !captureSuperHDRFrame.isRecycled()) {
                        Matrix matrix = new Matrix();
                        matrix.postScale(1.0f, -1.0f, captureSuperHDRFrame.getWidth() / 2.0f, captureSuperHDRFrame.getHeight() / 2.0f);
                        captureSuperHDRFrame = Bitmap.createBitmap(captureSuperHDRFrame, 0, 0, captureSuperHDRFrame.getWidth(), captureSuperHDRFrame.getHeight(), matrix, true);
                    }
                    this.handler.sendMessage(this.handler.obtainMessage(id, new CaptureInfo(poll.getOutputWidth(), poll.getOutputHeight(), poll.getListener(), captureSuperHDRFrame)));
                }
            }
        }
    }
}
