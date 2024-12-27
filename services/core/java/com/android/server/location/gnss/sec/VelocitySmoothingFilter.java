package com.android.server.location.gnss.sec;

import android.location.Location;
import android.util.Log;

import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class VelocitySmoothingFilter {
    public boolean isDriving;
    public int mDriveCount;
    public Location mPrevLocation;

    public final Location getFilteredLocation(Location location) {
        if (this.mPrevLocation == null) {
            this.mPrevLocation = location;
            return location;
        }
        if (location.getSpeed() < 5.555f) {
            Log.d("VSFilter", "Current speed=" + location.getSpeed() + ", just return");
            this.isDriving = false;
            this.mDriveCount = 0;
            this.mPrevLocation = null;
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
            this.isDriving = false;
            this.mDriveCount = 0;
            this.mPrevLocation = null;
            return location;
        }
        if (this.isDriving) {
            float speed = this.mPrevLocation.getSpeed() - location.getSpeed();
            if (Math.abs(speed) >= 4.1666f) {
                float speed2 = (speed / 2.0f) + location.getSpeed();
                Log.d(
                        "VSFilter",
                        "Driving. original speed=" + location.getSpeed() + ", set to=" + speed2);
                location.setSpeed(speed2);
            }
        } else {
            GestureWakeup$$ExternalSyntheticOutline0.m(
                    new StringBuilder("Not driving yet, cnt="), this.mDriveCount, "VSFilter");
            int i = this.mDriveCount + 1;
            this.mDriveCount = i;
            if (i >= 5) {
                this.isDriving = true;
            }
        }
        this.mPrevLocation = location;
        return location;
    }
}
