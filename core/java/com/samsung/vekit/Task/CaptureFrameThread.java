package com.samsung.vekit.Task;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Handler;
import android.os.Message;

import com.samsung.vekit.Common.Object.CaptureInfo;
import com.samsung.vekit.Common.VEContext;
import com.samsung.vekit.Item.ImageItem;
import com.samsung.vekit.Item.Item;
import com.samsung.vekit.Listener.CaptureFrameTaskListener;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/* loaded from: classes6.dex */
public class CaptureFrameThread extends Thread {
    VEContext context;
    Handler handler;
    private boolean isRunning = false;
    private BlockingDeque<CaptureFrameTask> queue = new LinkedBlockingDeque();

    public CaptureFrameThread(VEContext context, Handler handler) {
        this.context = context;
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

    public void addRequest(
            ImageItem item, int width, int height, CaptureFrameTaskListener listener) {
        CaptureFrameTask task = new CaptureFrameTask(item, width, height, listener);
        synchronized (this.queue) {
            try {
                this.queue.put(task);
                this.queue.notify();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void addRequest(int outputWidth, int outputHeight, CaptureFrameTaskListener listener) {
        CaptureFrameTask task = new CaptureFrameTask(outputWidth, outputHeight, listener);
        synchronized (this.queue) {
            try {
                this.queue.put(task);
                this.queue.notify();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void addRequest(
            Item item,
            int outputWidth,
            int outputHeight,
            CaptureFrameTask.CaptureType captureType,
            CaptureFrameTaskListener listener) {
        CaptureFrameTask task =
                new CaptureFrameTask(item, outputWidth, outputHeight, captureType, listener);
        synchronized (this.queue) {
            try {
                this.queue.put(task);
                this.queue.notify();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void addRequest(
            int outputWidth,
            int outputHeight,
            int outputCenterX,
            int outputCenterY,
            CaptureFrameTask.CaptureType captureType,
            CaptureFrameTaskListener listener) {
        CaptureFrameTask task =
                new CaptureFrameTask(
                        outputWidth,
                        outputHeight,
                        outputCenterX,
                        outputCenterY,
                        captureType,
                        listener);
        synchronized (this.queue) {
            try {
                this.queue.put(task);
                this.queue.notify();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        CaptureFrameTask request;
        while (this.isRunning) {
            synchronized (this.queue) {
                while (this.queue.isEmpty()) {
                    try {
                        this.queue.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
                request = this.queue.poll();
            }
            if (request != null) {
                if (request.getCaptureType() == CaptureFrameTask.CaptureType.ORIGINAL_FRAME) {
                    Bitmap bitmap =
                            this.context
                                    .getNativeInterface()
                                    .captureLatestFrame(
                                            request.getOutputWidth(), request.getOutputHeight());
                    Message message =
                            this.handler.obtainMessage(
                                    -1,
                                    new CaptureInfo(
                                            request.getOutputWidth(),
                                            request.getOutputHeight(),
                                            request.getListener(),
                                            bitmap));
                    this.handler.sendMessage(message);
                } else if (request.getCaptureType()
                        == CaptureFrameTask.CaptureType.RENDERED_FRAME) {
                    Bitmap bitmap2 =
                            this.context
                                    .getNativeInterface()
                                    .captureAnimatedFrame(
                                            request.getItem(),
                                            request.getOutputWidth(),
                                            request.getOutputHeight());
                    Message message2 =
                            this.handler.obtainMessage(
                                    request.getItem().getId(),
                                    new CaptureInfo(
                                            request.getOutputWidth(),
                                            request.getOutputHeight(),
                                            request.getListener(),
                                            bitmap2));
                    this.handler.sendMessage(message2);
                } else {
                    Bitmap bitmap3 =
                            this.context
                                    .getNativeInterface()
                                    .captureSuperHDRFrame(
                                            request.getItem(),
                                            request.getOutputWidth(),
                                            request.getOutputHeight(),
                                            request.getOutputCenterX(),
                                            request.getOutputCenterY());
                    int id = -1;
                    if (request.getItem() != null) {
                        id = request.getItem().getId();
                    }
                    if (bitmap3 != null && !bitmap3.isRecycled()) {
                        Matrix matrix = new Matrix();
                        matrix.postScale(
                                1.0f, -1.0f, bitmap3.getWidth() / 2.0f, bitmap3.getHeight() / 2.0f);
                        bitmap3 =
                                Bitmap.createBitmap(
                                        bitmap3,
                                        0,
                                        0,
                                        bitmap3.getWidth(),
                                        bitmap3.getHeight(),
                                        matrix,
                                        true);
                    }
                    Message message3 =
                            this.handler.obtainMessage(
                                    id,
                                    new CaptureInfo(
                                            request.getOutputWidth(),
                                            request.getOutputHeight(),
                                            request.getListener(),
                                            bitmap3));
                    this.handler.sendMessage(message3);
                }
            }
        }
    }
}
