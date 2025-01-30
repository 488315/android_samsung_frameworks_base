package com.android.server.wm;


/* loaded from: classes3.dex */
public class SplashScreenStartingData extends StartingData {
  public final int mTheme;

  @Override // com.android.server.wm.StartingData
  public boolean needRevealAnimation() {
    return true;
  }

  public SplashScreenStartingData(WindowManagerService windowManagerService, int i, int i2) {
    super(windowManagerService, i2);
    this.mTheme = i;
  }

  @Override // com.android.server.wm.StartingData
  public StartingSurfaceController.StartingSurface createStartingSurface(
      ActivityRecord activityRecord) {
    return this.mService.mStartingSurfaceController.createSplashScreenStartingSurface(
        activityRecord, this.mTheme);
  }
}
