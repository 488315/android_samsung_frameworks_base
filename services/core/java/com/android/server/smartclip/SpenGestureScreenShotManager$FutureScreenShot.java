package com.android.server.smartclip;

import android.graphics.Bitmap;
import android.util.Log;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/* compiled from: SpenGestureManagerService.java */
/* loaded from: classes3.dex */
public class SpenGestureScreenShotManager$FutureScreenShot extends FutureTask
    implements SpenGestureScreenShotManager$ScreenShot {
  public static String TAG = "FutureScreenShot";

  public SpenGestureScreenShotManager$FutureScreenShot(Callable callable) {
    super(callable);
  }

  @Override // com.android.server.smartclip.SpenGestureScreenShotManager$ScreenShot
  public Bitmap getScreenShot() {
    try {
      return ((SpenGestureScreenShotManager$RealScreenShot) get()).getScreenShot();
    } catch (InterruptedException e) {
      Log.e(TAG, e.getMessage());
      return null;
    } catch (ExecutionException e2) {
      Log.e(TAG, e2.getMessage());
      return null;
    }
  }
}
