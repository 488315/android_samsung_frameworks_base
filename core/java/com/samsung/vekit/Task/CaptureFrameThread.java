package com.samsung.vekit.Task;

import android.graphics.Bitmap;
import android.p009os.Handler;
import android.p009os.Message;
import com.samsung.vekit.Common.Object.CaptureInfo;
import com.samsung.vekit.Common.VEContext;
import com.samsung.vekit.Item.ImageItem;
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

  public void addRequest(ImageItem item, int width, int height, CaptureFrameTaskListener listener) {
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
                  .captureLatestFrame(request.getOutputWidth(), request.getOutputHeight());
          Message message =
              this.handler.obtainMessage(
                  -1,
                  new CaptureInfo(
                      request.getOutputWidth(),
                      request.getOutputHeight(),
                      request.getListener(),
                      bitmap));
          this.handler.sendMessage(message);
        } else {
          Bitmap bitmap2 =
              this.context
                  .getNativeInterface()
                  .captureAnimatedFrame(
                      request.getItem(), request.getOutputWidth(), request.getOutputHeight());
          Message message2 =
              this.handler.obtainMessage(
                  request.getItem().getId(),
                  new CaptureInfo(
                      request.getOutputWidth(),
                      request.getOutputHeight(),
                      request.getListener(),
                      bitmap2));
          this.handler.sendMessage(message2);
        }
      }
    }
  }
}
