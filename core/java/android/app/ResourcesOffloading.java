package android.app;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.util.Slog;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes.dex */
public final class ResourcesOffloading {
  private static final String TAG = "ResourcesOffloading";
  private Context appContext;
  private Handler mHandler = null;
  private Runnable mScheduleEndAppLaunchMarker = null;

  ResourcesOffloading(Context appContext) {
    this.appContext = appContext;
  }

  public void resourcesOffloading(String processName) {
    if (isProcessValid(processName)) {
      this.appContext.getResources();
      Resources.setApplicationContext(this.appContext);
      this.appContext.getResources();
      Resources.setIfAppLaunching(true);
      Handler handler = this.mHandler;
      if (handler == null) {
        this.mHandler = new Handler(Looper.getMainLooper());
      } else {
        Runnable runnable = this.mScheduleEndAppLaunchMarker;
        if (runnable != null) {
          handler.removeCallbacks(runnable);
        }
      }
      Runnable runnable2 = new Runnable() { // from class: android.app.ResourcesOffloading.1
            @Override // java.lang.Runnable
            public void run() {
              ResourcesOffloading.this.appContext.getResources();
              Resources.setIfAppLaunching(false);
            }
          };
      this.mScheduleEndAppLaunchMarker = runnable2;
      if (!this.mHandler.postDelayed(runnable2, 2000L)) {
        return;
      }
      Thread thread = new Thread(new ResourcesOffloadingRunnable());
      thread.start();
    }
  }

  private boolean isProcessValid(String processName) {
    if (processName == null || processName.contains(":")) {
      return false;
    }
    return true;
  }

  private class ResourcesOffloadingRunnable implements Runnable {
    private ResourcesOffloadingRunnable() {}

    @Override // java.lang.Runnable
    public void run() {
      File file = ResourcesOffloading.this.appContext.getFileStreamPath("rList");
      if (file != null && file.exists()) {
        try {
          FileInputStream fis = new FileInputStream(file);
          try {
            ObjectInputStream ois = new ObjectInputStream(fis);
            try {
              ArrayList<String> rList = (ArrayList) ois.readObject();
              if (rList != null) {
                Iterator<String> it = rList.iterator();
                while (it.hasNext()) {
                  String rID = it.next();
                  int resourceId = Integer.parseInt(rID);
                  ResourcesOffloading.this.appContext.getResources().offloadDrawable(resourceId, 0);
                }
              }
              ois.close();
              fis.close();
            } finally {
            }
          } finally {
          }
        } catch (Exception e) {
          Slog.m122w(ResourcesOffloading.TAG, "An exception occurred : ", e);
        }
      }
    }
  }
}
