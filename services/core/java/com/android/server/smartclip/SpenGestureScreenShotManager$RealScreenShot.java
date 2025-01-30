package com.android.server.smartclip;

import android.graphics.Bitmap;
import android.graphics.Rect;
import com.android.server.LocalServices;
import com.android.server.wm.WindowManagerInternal;

/* compiled from: SpenGestureManagerService.java */
/* loaded from: classes3.dex */
public class SpenGestureScreenShotManager$RealScreenShot
    implements SpenGestureScreenShotManager$ScreenShot {
  public final Bitmap bitmap;
  public WindowManagerInternal mWmInternal;

  public SpenGestureScreenShotManager$RealScreenShot(
      int i, int i2, boolean z, Rect rect, int i3, int i4, boolean z2) {
    WindowManagerInternal windowManagerInternal =
        (WindowManagerInternal) LocalServices.getService(WindowManagerInternal.class);
    this.mWmInternal = windowManagerInternal;
    this.bitmap =
        windowManagerInternal.takeScreenshotToTargetWindowInternal(i, i2, z, rect, i3, i4, z2);
  }

  @Override // com.android.server.smartclip.SpenGestureScreenShotManager$ScreenShot
  public Bitmap getScreenShot() {
    return this.bitmap;
  }
}
