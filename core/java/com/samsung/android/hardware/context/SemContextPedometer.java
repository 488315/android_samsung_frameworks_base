package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class SemContextPedometer extends SemContextEventContext {
  public static final Parcelable.Creator<SemContextPedometer> CREATOR =
      new Parcelable.Creator<
          SemContextPedometer>() { // from class:
                                   // com.samsung.android.hardware.context.SemContextPedometer.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextPedometer createFromParcel(Parcel in) {
          return new SemContextPedometer(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextPedometer[] newArray(int size) {
          return new SemContextPedometer[size];
        }
      };
  public static final int EXERCISE_MODE_BICYCLE = 2;
  public static final int EXERCISE_MODE_NONE = -1;
  public static final int EXERCISE_MODE_NON_BICYCLE = 3;
  public static final int EXERCISE_MODE_RUN = 1;
  public static final int EXERCISE_MODE_WALK = 0;
  public static final int GENDER_MAN = 1;
  public static final int GENDER_WOMAN = 2;
  public static final int HISTORY_MODE = 2;
  public static final int LOGGING_MODE = 1;
  public static final int NORMAL_MODE = 0;
  public static final int PARAMETERS_UNKNOWN = 0;
  public static final int STEP_STATUS_MARK = 1;
  public static final int STEP_STATUS_RUN = 4;
  public static final int STEP_STATUS_RUN_DOWN = 9;
  public static final int STEP_STATUS_RUN_UP = 8;
  public static final int STEP_STATUS_RUSH = 5;
  public static final int STEP_STATUS_STOP = 0;
  public static final int STEP_STATUS_STROLL = 2;
  public static final int STEP_STATUS_UNKNOWN = -1;
  public static final int STEP_STATUS_WALK = 3;
  public static final int STEP_STATUS_WALK_DOWN = 7;
  public static final int STEP_STATUS_WALK_UP = 6;
  private Bundle mContext;
  private int mMode;

  SemContextPedometer() {
    this.mContext = new Bundle();
    this.mMode = 0;
  }

  SemContextPedometer(Parcel src) {
    readFromParcel(src);
  }

  public int getMode() {
    return this.mMode;
  }

  public int getStepStatus() {
    return this.mContext.getInt("StepStatus");
  }

  public double getSpeed() {
    return this.mContext.getDouble("Speed");
  }

  public double getCalorie() {
    return this.mContext.getDouble("CumulativeCalorie");
  }

  public double getDistance() {
    return this.mContext.getDouble("CumulativeDistance");
  }

  public long getTotalStepCount() {
    return this.mContext.getLong("CumulativeTotalStepCount");
  }

  public long getWalkStepCount() {
    return this.mContext.getLong("CumulativeWalkFlatStepCount");
  }

  public long getWalkUpStepCount() {
    return this.mContext.getLong("CumulativeWalkUpStepCount");
  }

  public long getWalkDownStepCount() {
    return this.mContext.getLong("CumulativeWalkDownStepCount");
  }

  public long getRunStepCount() {
    return this.mContext.getLong("CumulativeRunFlatStepCount");
  }

  public long getRunUpStepCount() {
    return this.mContext.getLong("CumulativeRunUpStepCount");
  }

  public long getRunDownStepCount() {
    return this.mContext.getLong("CumulativeRunDownStepCount");
  }

  @Deprecated
  public long getUpDownStepCount() {
    return this.mContext.getLong("UpDownStepCount");
  }

  public double getCalorieDiff() {
    return this.mContext.getDouble("CalorieDiff");
  }

  public double getDistanceDiff() {
    return this.mContext.getDouble("DistanceDiff");
  }

  public long getTotalStepCountDiff() {
    return this.mContext.getLong("TotalStepCountDiff");
  }

  public long getWalkStepCountDiff() {
    return this.mContext.getLong("WalkStepCountDiff");
  }

  public long getWalkUpStepCountDiff() {
    return this.mContext.getLong("WalkUpStepCountDiff");
  }

  public long getWalkDownStepCountDiff() {
    return this.mContext.getLong("WalkDownStepCountDiff");
  }

  public long getRunStepCountDiff() {
    return this.mContext.getLong("RunStepCountDiff");
  }

  public long getRunUpStepCountDiff() {
    return this.mContext.getLong("RunUpStepCountDiff");
  }

  public long getRunDownStepCountDiff() {
    return this.mContext.getLong("RunDownStepCountDiff");
  }

  @Deprecated
  public long getUpDownStepCountDiff() {
    return this.mContext.getLong("UpDownStepCountDiff");
  }

  public double getWalkingFrequency() {
    return this.mContext.getDouble("WalkingFrequency");
  }

  @Deprecated
  public double getCumulativeDistance() {
    return this.mContext.getDouble("CumulativeDistance");
  }

  @Deprecated
  public double getCumulativeCalorie() {
    return this.mContext.getDouble("CumulativeCalorie");
  }

  @Deprecated
  public long getCumulativeTotalStepCount() {
    return this.mContext.getLong("CumulativeTotalStepCount");
  }

  @Deprecated
  public long getCumulativeWalkStepCount() {
    return this.mContext.getLong("CumulativeWalkFlatStepCount");
  }

  @Deprecated
  public long getCumulativeWalkUpStepCount() {
    return this.mContext.getLong("CumulativeWalkUpStepCount");
  }

  @Deprecated
  public long getCumulativeWalkDownStepCount() {
    return this.mContext.getLong("CumulativeWalkDownStepCount");
  }

  @Deprecated
  public long getCumulativeRunStepCount() {
    return this.mContext.getLong("CumulativeRunFlatStepCount");
  }

  @Deprecated
  public long getCumulativeRunUpStepCount() {
    return this.mContext.getLong("CumulativeRunUpStepCount");
  }

  @Deprecated
  public long getCumulativeRunDownStepCount() {
    return this.mContext.getLong("CumulativeRunDownStepCount");
  }

  public double[] getSpeedArray() {
    int i = this.mMode;
    if (i != 1 && i != 2) {
      return null;
    }
    double[] res = this.mContext.getDoubleArray("SpeedArray");
    return res;
  }

  public double[] getCalorieDiffArray() {
    int i = this.mMode;
    if (i != 1 && i != 2) {
      return null;
    }
    double[] res = this.mContext.getDoubleArray("CalorieDiffArray");
    return res;
  }

  public double[] getDistanceDiffArray() {
    int i = this.mMode;
    if (i != 1 && i != 2) {
      return null;
    }
    double[] res = this.mContext.getDoubleArray("DistanceDiffArray");
    return res;
  }

  public long[] getTotalStepCountDiffArray() {
    int i = this.mMode;
    if (i != 1 && i != 2) {
      return null;
    }
    long[] res = this.mContext.getLongArray("TotalStepCountDiffArray");
    return res;
  }

  public long[] getWalkStepCountDiffArray() {
    int i = this.mMode;
    if (i != 1 && i != 2) {
      return null;
    }
    long[] res = this.mContext.getLongArray("WalkStepCountDiffArray");
    return res;
  }

  public long[] getWalkUpStepCountDiffArray() {
    int i = this.mMode;
    if (i != 1 && i != 2) {
      return null;
    }
    long[] res = this.mContext.getLongArray("WalkUpStepCountDiffArray");
    return res;
  }

  public long[] getWalkDownStepCountDiffArray() {
    int i = this.mMode;
    if (i != 1 && i != 2) {
      return null;
    }
    long[] res = this.mContext.getLongArray("WalkDownStepCountDiffArray");
    return res;
  }

  public long[] getRunStepCountDiffArray() {
    int i = this.mMode;
    if (i != 1 && i != 2) {
      return null;
    }
    long[] res = this.mContext.getLongArray("RunStepCountDiffArray");
    return res;
  }

  public long[] getRunUpStepCountDiffArray() {
    int i = this.mMode;
    if (i != 1 && i != 2) {
      return null;
    }
    long[] res = this.mContext.getLongArray("RunUpStepCountDiffArray");
    return res;
  }

  public long[] getRunDownStepCountDiffArray() {
    int i = this.mMode;
    if (i != 1 && i != 2) {
      return null;
    }
    long[] res = this.mContext.getLongArray("RunDownStepCountDiffArray");
    return res;
  }

  public long[] getTimeStampArray() {
    int i = this.mMode;
    if (i != 1 && i != 2) {
      return null;
    }
    long[] res = this.mContext.getLongArray("TimeStampArray");
    return res;
  }

  public int getLoggingCount() {
    int i = this.mMode;
    if (i != 1 && i != 2) {
      return 0;
    }
    int res = this.mContext.getInt("LoggingCount");
    return res;
  }

  @Override // com.samsung.android.hardware.context.SemContextEventContext
  public void setValues(Bundle context) {
    this.mContext = context;
    this.mMode = context.getInt("Mode");
  }

  @Override // com.samsung.android.hardware.context.SemContextEventContext,
            // android.os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeBundle(this.mContext);
    dest.writeInt(this.mMode);
  }

  private void readFromParcel(Parcel src) {
    this.mContext = src.readBundle(getClass().getClassLoader());
    this.mMode = src.readInt();
  }
}
