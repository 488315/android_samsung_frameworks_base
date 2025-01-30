package com.android.server.location.gnss.sec;

import android.location.Location;
import android.util.Log;

/* loaded from: classes2.dex */
public class VelocitySmoothingFilter {
  public boolean isDriving;
  public int mDriveCount;
  public Location mPrevLocation;

  public VelocitySmoothingFilter() {
    init();
  }

  public void init() {
    this.isDriving = false;
    this.mDriveCount = 0;
    this.mPrevLocation = null;
  }

  public Location getFilteredLocation(Location location) {
    if (this.mPrevLocation == null) {
      this.mPrevLocation = location;
      return location;
    }
    if (location.getSpeed() < 5.555f) {
      Log.d("VSFilter", "Current speed=" + location.getSpeed() + ", just return");
      init();
      return location;
    }
    if (location.getTime() - this.mPrevLocation.getTime() > 2000) {
      Log.d(
          "VSFilter",
          "Current time="
              + location.getTime()
              + ", Previous time="
              + this.mPrevLocation.getTime()
              + ", just return");
      init();
      return location;
    }
    if (!this.isDriving) {
      Log.d("VSFilter", "Not driving yet, cnt=" + this.mDriveCount);
      int i = this.mDriveCount + 1;
      this.mDriveCount = i;
      if (i >= 5) {
        this.isDriving = true;
      }
    } else {
      float speed = this.mPrevLocation.getSpeed() - location.getSpeed();
      if (Math.abs(speed) >= 4.1666f) {
        float speed2 = location.getSpeed() + (speed / 2.0f);
        Log.d("VSFilter", "Driving. original speed=" + location.getSpeed() + ", set to=" + speed2);
        location.setSpeed(speed2);
      }
    }
    this.mPrevLocation = location;
    return location;
  }
}
