package com.android.server.enterprise.auditlog;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public class LogWritter {
  public CircularBuffer mCircularBuffer;
  public LooperThread mLooperThread;
  public IObserver mObserver;

  public LogWritter(int i, Context context, String str) {
    this.mCircularBuffer = new CircularBuffer(i, context, str);
    LooperThread looperThread = new LooperThread();
    this.mLooperThread = looperThread;
    looperThread.start();
  }

  public void swapFiles(String str) {
    Message message = new Message();
    Bundle bundle = new Bundle();
    bundle.putString("swap", str);
    message.setData(bundle);
    this.mLooperThread.mHandler.sendMessage(message);
  }

  public void write(String str) {
    LooperThread looperThread = this.mLooperThread;
    if (looperThread == null || looperThread.mHandler == null) {
      return;
    }
    Message message = new Message();
    Bundle bundle = new Bundle();
    bundle.putString("log", str);
    message.setData(bundle);
    this.mLooperThread.mHandler.sendMessage(message);
  }

  public void shutdown() {
    this.mLooperThread.removeCallbacks();
    this.mCircularBuffer.closeFile();
  }

  public void setCriticalLogSize(int i) {
    this.mCircularBuffer.setCriticalLogSize(i);
  }

  public int getCriticalLogSize() {
    return this.mCircularBuffer.getCriticalLogSize();
  }

  public void setMaximumLogSize(int i) {
    this.mCircularBuffer.setMaximumLogSize(i);
  }

  public int getMaximumLogSize() {
    return this.mCircularBuffer.getMaximumLogSize();
  }

  public int getCurrentLogFileSize() {
    return this.mCircularBuffer.getCurrentLogFileSize();
  }

  public void deleteAllFiles() {
    this.mCircularBuffer.deleteAllFiles();
  }

  public Object getDumpFilesList() {
    return this.mCircularBuffer.getDumpFilesList();
  }

  public void setObserver(IObserver iObserver) {
    this.mObserver = iObserver;
  }

  public void setIsDumping(boolean z, boolean z2) {
    this.mCircularBuffer.setIsDumping(z, z2);
  }

  public void setBootCompleted(boolean z) {
    this.mCircularBuffer.setBootCompleted(z);
  }

  public void setTypeOfDump(boolean z) {
    this.mCircularBuffer.setTypeOfDump(z);
  }

  public void setBufferLogSize(long j) {
    this.mCircularBuffer.setBufferLogSize(j);
  }

  public long getBufferLogSize() {
    return this.mCircularBuffer.getBufferLogSize();
  }

  public void createBubbleDirectory() {
    this.mCircularBuffer.createBubbleDir();
  }

  public void createBubbleFile() {
    this.mCircularBuffer.resizeBubbleFile(getBufferLogSize());
  }

  public void setLastTimestamp() {
    this.mCircularBuffer.setLastTimestamp((ArrayList) getDumpFilesList());
  }

  public class LooperThread extends Thread {
    public SaveLogHandler mHandler;

    public LooperThread() {}

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
      Looper.prepare();
      this.mHandler = LogWritter.this.new SaveLogHandler();
      Looper.loop();
    }

    public void removeCallbacks() {
      this.mHandler.removeCallbacks(this);
    }
  }

  public class SaveLogHandler extends Handler {
    public Bundle data;

    public SaveLogHandler() {}

    @Override // android.os.Handler
    public void handleMessage(Message message) {
      Bundle data = message.getData();
      this.data = data;
      if (data.getString("swap") != null) {
        LogWritter.this.mCircularBuffer.write(null);
        LogWritter.this.mObserver.notifyReadyToDump(true);
      } else if (this.data.getString("log") != null) {
        LogWritter.this.mCircularBuffer.write(this.data.getString("log"));
      } else {
        LogWritter.this.mCircularBuffer.closeFile();
      }
    }
  }
}
