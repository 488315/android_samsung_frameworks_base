package com.samsung.android.hardware.context;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class SemContextLocationCore extends SemContextEventContext {
  public static final int ACTION_CURRENT_LOCATION_ACTIVITY_RECOGNITION_START = 11;
  public static final int ACTION_CURRENT_LOCATION_ACTIVITY_RECOGNITION_STOP = 12;
  public static final int ACTION_CURRENT_LOCATION_DISTANCE_CALLBACK = 4;
  public static final int ACTION_CURRENT_LOCATION_INJECT_PASSIVE_LOCATION = 8;
  public static final int ACTION_CURRENT_LOCATION_REQUEST_DISTANCE = 13;
  public static final int ACTION_CURRENT_LOCATION_RESET_DISTANCE = 14;
  public static final int ACTION_DUMPSTATE = 6;
  public static final int ACTION_EXTRA_DATA_ARRAY_CALLBACK = 10;
  public static final int ACTION_FLP_BATCHING_CALLBACK = 7;
  public static final int ACTION_FLP_BATCHING_CLEANUP = 22;
  public static final int ACTION_FLP_BATCHING_FLUSH = 20;
  public static final int ACTION_FLP_BATCHING_INJECT_LOCATION = 21;
  public static final int ACTION_FLP_BATCHING_REQUEST_LOCATION = 19;
  public static final int ACTION_FLP_BATCHING_START = 16;
  public static final int ACTION_FLP_BATCHING_STOP = 18;
  public static final int ACTION_FLP_BATCHING_UPDATE = 17;
  public static final int ACTION_GEOFENCE_ACTIVITY_RECOGNITION_START = 9;
  public static final int ACTION_GEOFENCE_ACTIVITY_RECOGNITION_STOP = 10;
  public static final int ACTION_GEOFENCE_ACTIVITY_RECOGNITION_TRACKING_CALLBACK = 3;
  public static final int ACTION_GEOFENCE_ADD = 1;
  public static final int ACTION_GEOFENCE_ERROR_CALLBACK = 5;
  public static final int ACTION_GEOFENCE_ERROR_CODE_GENERIC = -100;
  public static final int ACTION_GEOFENCE_ERROR_CODE_SUCCESS = 0;
  public static final int ACTION_GEOFENCE_GPS_PAUSE = 3;
  public static final int ACTION_GEOFENCE_GPS_RESUME = 4;
  public static final int ACTION_GEOFENCE_NLP_PAUSE = 5;
  public static final int ACTION_GEOFENCE_NLP_RESUME = 6;
  public static final int ACTION_GEOFENCE_REMOVE = 2;
  public static final int ACTION_GEOFENCE_STATUS_ENTER = 0;
  public static final int ACTION_GEOFENCE_STATUS_EXIT = 1;
  public static final int ACTION_GEOFENCE_STATUS_REMOVE = 15;
  public static final int ACTION_GEOFENCE_TRANSITION_CALLBACK = 1;
  public static final int ACTION_GEOFENCE_UPDATE = 7;
  public static final int ACTION_GEOFENCE_UPDATE_CALLBACK = 2;
  public static final int ACTION_GEOFENCE_VERSION = 1;
  public static final int ACTION_GEOFENCE_VERSION_CALLBACK = 0;
  public static final int ACTION_SEND_EXTRA_CMD = 23;
  public static final int ACTION_UNKNOWN = -1;
  public static final Parcelable.Creator<SemContextLocationCore> CREATOR =
      new Parcelable.Creator<
          SemContextLocationCore>() { // from class:
                                      // com.samsung.android.hardware.context.SemContextLocationCore.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextLocationCore createFromParcel(Parcel in) {
          return new SemContextLocationCore(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextLocationCore[] newArray(int size) {
          return new SemContextLocationCore[size];
        }
      };
  public static final int MODE_CURRENT_LOCATION = 1;
  public static final int MODE_DUMPSTATE = 2;
  public static final int MODE_EXTRA_DATA = 3;
  public static final int MODE_FLP_BATCHING = 3;
  public static final int MODE_GEOFENCE = 0;
  public static final int MODE_UNKNOWN = -1;
  private Bundle mContext;

  SemContextLocationCore() {
    this.mContext = new Bundle();
  }

  SemContextLocationCore(Parcel src) {
    readFromParcel(src);
  }

  public int getMode() {
    return this.mContext.getInt("Mode");
  }

  public int getAction() {
    return this.mContext.getInt("Action");
  }

  public int getFenceId() {
    return this.mContext.getInt("GeoFenceId");
  }

  public int getStatus() {
    return this.mContext.getInt("GeoFenceStatus");
  }

  public long getTimeStamp() {
    return this.mContext.getLong("Timestamp");
  }

  public double getLatitude() {
    return this.mContext.getDouble("Latitude");
  }

  public double getLongitude() {
    return this.mContext.getDouble("Longitude");
  }

  public int getAccuracy() {
    return this.mContext.getInt("Accuracy");
  }

  public int getTotalGpsCount() {
    return this.mContext.getInt("TotalGpsCount");
  }

  public int getSuccessGpsCount() {
    return this.mContext.getInt("SuccessGpsCount");
  }

  public int getErrorCallbackType() {
    return this.mContext.getInt("FunctionType");
  }

  public int getErrorCode() {
    return this.mContext.getInt("ErrorCode");
  }

  public float getDistance() {
    return this.mContext.getFloat("Distance");
  }

  public int getDataSize() {
    return this.mContext.getInt("DataCount");
  }

  public int[] getTypeArray() {
    return this.mContext.getIntArray("EventTypeArray");
  }

  public int[] getStatusArray() {
    return this.mContext.getIntArray("EventStatusArray");
  }

  public long[] getTimeStampArray() {
    return this.mContext.getLongArray("TimeStampArray");
  }

  public int[] getDataArray() {
    return this.mContext.getIntArray("DataArray");
  }

  public double[] getLatitudeArray() {
    return this.mContext.getDoubleArray("LatitudeArray");
  }

  public double[] getLongitudeArray() {
    return this.mContext.getDoubleArray("LongitudeArray");
  }

  public int[] getAltitudeArray() {
    return this.mContext.getIntArray("AltitudeArray");
  }

  public int[] getSpeedArray() {
    return this.mContext.getIntArray("SpeedArray");
  }

  public int[] getBearingArray() {
    return this.mContext.getIntArray("BearingArray");
  }

  public int[] getAccuracyArray() {
    return this.mContext.getIntArray("AccuracyArray");
  }

  public int getDataSequence() {
    return this.mContext.getInt("DataSequence");
  }

  public int getTotalSequence() {
    return this.mContext.getInt("TotalSequence");
  }

  public int[] getExtraDataArray() {
    return this.mContext.getIntArray("ExtraDataArray");
  }

  @Override // com.samsung.android.hardware.context.SemContextEventContext
  public void setValues(Bundle context) {
    this.mContext = context;
  }

  @Override // com.samsung.android.hardware.context.SemContextEventContext,
            // android.os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeBundle(this.mContext);
  }

  private void readFromParcel(Parcel src) {
    this.mContext = src.readBundle(getClass().getClassLoader());
  }
}
