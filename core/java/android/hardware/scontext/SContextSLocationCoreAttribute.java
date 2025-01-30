package android.hardware.scontext;

import android.p009os.Bundle;
import android.util.Log;

@Deprecated
/* loaded from: classes2.dex */
public class SContextSLocationCoreAttribute extends SContextAttribute {
  private static final String TAG = "SContextSLocationCoreAttribute";
  private int mAccuracy;
  private int mAction;
  private int mFenceId;
  private double mLatitude;
  private double mLongitude;
  private int mMin_Ditance;
  private int mMin_Time;
  private int mMode;
  private int mRadius;
  private int mStatus;
  private int mSuccessGpsCnt;
  private long mTimeStamp;
  private int mTotalGpsCnt;

  SContextSLocationCoreAttribute() {
    this.mMode = -1;
    this.mAction = -1;
    this.mFenceId = 0;
    this.mRadius = 0;
    this.mStatus = 0;
    this.mTotalGpsCnt = 0;
    this.mSuccessGpsCnt = 0;
    this.mMin_Ditance = 0;
    this.mMin_Time = 0;
    this.mAccuracy = 0;
    this.mTimeStamp = 0L;
    this.mLongitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    this.mLatitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    setAttribute();
  }

  public SContextSLocationCoreAttribute(int mode, int action) {
    this.mMode = -1;
    this.mAction = -1;
    this.mFenceId = 0;
    this.mRadius = 0;
    this.mStatus = 0;
    this.mTotalGpsCnt = 0;
    this.mSuccessGpsCnt = 0;
    this.mMin_Ditance = 0;
    this.mMin_Time = 0;
    this.mAccuracy = 0;
    this.mTimeStamp = 0L;
    this.mLongitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    this.mLatitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    this.mMode = mode;
    this.mAction = action;
    setAttribute();
  }

  public SContextSLocationCoreAttribute(
      int mode,
      int action,
      int fence_id,
      double latitude,
      double longitude,
      int radius,
      int total_gps_cnt,
      int success_gps_cnt) {
    this.mMode = -1;
    this.mAction = -1;
    this.mFenceId = 0;
    this.mRadius = 0;
    this.mStatus = 0;
    this.mTotalGpsCnt = 0;
    this.mSuccessGpsCnt = 0;
    this.mMin_Ditance = 0;
    this.mMin_Time = 0;
    this.mAccuracy = 0;
    this.mTimeStamp = 0L;
    this.mLongitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    this.mLatitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    this.mMode = mode;
    this.mAction = action;
    this.mFenceId = fence_id;
    this.mRadius = radius;
    this.mTotalGpsCnt = total_gps_cnt;
    this.mSuccessGpsCnt = success_gps_cnt;
    this.mLatitude = latitude;
    this.mLongitude = longitude;
    setAttribute();
  }

  public SContextSLocationCoreAttribute(int mode, int action, int fence_id) {
    this.mMode = -1;
    this.mAction = -1;
    this.mFenceId = 0;
    this.mRadius = 0;
    this.mStatus = 0;
    this.mTotalGpsCnt = 0;
    this.mSuccessGpsCnt = 0;
    this.mMin_Ditance = 0;
    this.mMin_Time = 0;
    this.mAccuracy = 0;
    this.mTimeStamp = 0L;
    this.mLongitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    this.mLatitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    this.mMode = mode;
    this.mAction = action;
    this.mFenceId = fence_id;
    setAttribute();
  }

  public SContextSLocationCoreAttribute(
      int mode, int action, int fence_id, int radius, int status) {
    this.mMode = -1;
    this.mAction = -1;
    this.mFenceId = 0;
    this.mRadius = 0;
    this.mStatus = 0;
    this.mTotalGpsCnt = 0;
    this.mSuccessGpsCnt = 0;
    this.mMin_Ditance = 0;
    this.mMin_Time = 0;
    this.mAccuracy = 0;
    this.mTimeStamp = 0L;
    this.mLongitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    this.mLatitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    this.mMode = mode;
    this.mAction = action;
    this.mFenceId = fence_id;
    this.mRadius = radius;
    this.mStatus = status;
    setAttribute();
  }

  public SContextSLocationCoreAttribute(int mode, int action, int min_distance, int min_time) {
    this.mMode = -1;
    this.mAction = -1;
    this.mFenceId = 0;
    this.mRadius = 0;
    this.mStatus = 0;
    this.mTotalGpsCnt = 0;
    this.mSuccessGpsCnt = 0;
    this.mMin_Ditance = 0;
    this.mMin_Time = 0;
    this.mAccuracy = 0;
    this.mTimeStamp = 0L;
    this.mLongitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    this.mLatitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    this.mMode = mode;
    this.mAction = action;
    this.mMin_Ditance = min_distance;
    this.mMin_Time = min_time;
    setAttribute();
  }

  public SContextSLocationCoreAttribute(
      int mode, int action, double latitude, double longitude, int accuracy, long timestamp) {
    this.mMode = -1;
    this.mAction = -1;
    this.mFenceId = 0;
    this.mRadius = 0;
    this.mStatus = 0;
    this.mTotalGpsCnt = 0;
    this.mSuccessGpsCnt = 0;
    this.mMin_Ditance = 0;
    this.mMin_Time = 0;
    this.mAccuracy = 0;
    this.mTimeStamp = 0L;
    this.mLongitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    this.mLatitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    this.mMode = mode;
    this.mAction = action;
    this.mLatitude = latitude;
    this.mLongitude = longitude;
    this.mAccuracy = accuracy;
    this.mTimeStamp = timestamp;
    setAttribute();
  }

  @Override // android.hardware.scontext.SContextAttribute,
            // com.samsung.android.hardware.context.SemContextAttribute
  public boolean checkAttribute() {
    int i;
    int i2 = this.mMode;
    if (i2 < -1 || i2 > 1) {
      Log.m94d(TAG, "Mode value is wrong!!");
      return false;
    }
    if (i2 == 0) {
      int i3 = this.mAction;
      if (i3 < -1 || i3 > 10) {
        Log.m94d(TAG, "Action value is wrong!!");
        return false;
      }
    } else if (i2 == 1 && ((i = this.mAction) < -1 || i > 14)) {
      Log.m94d(TAG, "Action value is wrong!!");
      return false;
    }
    if (this.mFenceId < 0) {
      Log.m94d(TAG, "FenceID is wrong!!");
      return false;
    }
    if (this.mRadius < 0) {
      Log.m94d(TAG, "Radius is wrong!!");
      return false;
    }
    if (this.mStatus < 0) {
      Log.m94d(TAG, "Status is wrong!1");
      return false;
    }
    if (this.mTotalGpsCnt < 0) {
      Log.m94d(TAG, "TotalGpsCount is wrong!!");
      return false;
    }
    if (this.mSuccessGpsCnt < 0) {
      Log.m94d(TAG, "Success gps count is wrong");
      return false;
    }
    if (this.mMin_Ditance < 0) {
      Log.m94d(TAG, "Minimun distnace is wrong");
      return false;
    }
    if (this.mMin_Time < 0) {
      Log.m94d(TAG, "Minimun time is wrong");
      return false;
    }
    if (this.mAccuracy < 0) {
      Log.m94d(TAG, "Accuracy is wrong");
      return false;
    }
    if (this.mTimeStamp < 0) {
      Log.m94d(TAG, "Timestamp is wrong");
      return false;
    }
    double d = this.mLongitude;
    if (d < -180.0d || d > 180.0d) {
      Log.m94d(TAG, "Longitude is wrong");
      return false;
    }
    double d2 = this.mLatitude;
    if (d2 >= -90.0d && d2 <= 90.0d) {
      return true;
    }
    Log.m94d(TAG, "Latitidue is wrong");
    return false;
  }

  private void setAttribute() {
    Bundle attribute = new Bundle();
    switch (this.mMode) {
      case 0:
        int i = this.mAction;
        if (i == 1) {
          double[] doubleType = {this.mLatitude, this.mLongitude};
          int[] intType = {this.mFenceId, this.mRadius, this.mTotalGpsCnt, this.mSuccessGpsCnt};
          attribute.putIntArray("IntType", intType);
          attribute.putDoubleArray("DoubleType", doubleType);
          break;
        } else if (i == 2) {
          int[] intType2 = {this.mFenceId};
          attribute.putIntArray("IntType", intType2);
          break;
        } else if (i == 7) {
          int[] intType3 = {this.mFenceId, this.mRadius, this.mStatus};
          attribute.putIntArray("IntType", intType3);
          break;
        } else if (i == 9) {
          int[] intType4 = {this.mMin_Ditance, this.mMin_Time};
          attribute.putIntArray("IntType", intType4);
          break;
        } else {
          Log.m94d(TAG, "This Type is default attribute type");
          break;
        }
      case 1:
        if (this.mAction == 8) {
          double[] doubleType2 = {this.mLatitude, this.mLongitude};
          int[] intType5 = {this.mAccuracy};
          long[] longType = {this.mTimeStamp};
          attribute.putDoubleArray("DoubleType", doubleType2);
          attribute.putIntArray("IntType", intType5);
          attribute.putLongArray("LongType", longType);
          break;
        } else {
          Log.m94d(TAG, "This Type is default attribute type");
          break;
        }
    }
    attribute.putInt("Mode", this.mMode);
    attribute.putInt("Action", this.mAction);
    Log.m94d(
        TAG,
        "setAttribute() mode : "
            + attribute.getInt("Mode")
            + " action : "
            + attribute.getInt("Action"));
    super.setAttribute(47, attribute);
  }
}
