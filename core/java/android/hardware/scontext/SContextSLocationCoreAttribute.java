package android.hardware.scontext;

import android.os.Bundle;
import android.util.Log;

@Deprecated
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
        if (this.mMode < -1 || this.mMode > 1) {
            Log.d(TAG, "Mode value is wrong!!");
            return false;
        }
        if (this.mMode == 0) {
            if (this.mAction < -1 || this.mAction > 10) {
                Log.d(TAG, "Action value is wrong!!");
                return false;
            }
        } else if (this.mMode == 1 && (this.mAction < -1 || this.mAction > 14)) {
            Log.d(TAG, "Action value is wrong!!");
            return false;
        }
        if (this.mFenceId < 0) {
            Log.d(TAG, "FenceID is wrong!!");
            return false;
        }
        if (this.mRadius < 0) {
            Log.d(TAG, "Radius is wrong!!");
            return false;
        }
        if (this.mStatus < 0) {
            Log.d(TAG, "Status is wrong!1");
            return false;
        }
        if (this.mTotalGpsCnt < 0) {
            Log.d(TAG, "TotalGpsCount is wrong!!");
            return false;
        }
        if (this.mSuccessGpsCnt < 0) {
            Log.d(TAG, "Success gps count is wrong");
            return false;
        }
        if (this.mMin_Ditance < 0) {
            Log.d(TAG, "Minimun distnace is wrong");
            return false;
        }
        if (this.mMin_Time < 0) {
            Log.d(TAG, "Minimun time is wrong");
            return false;
        }
        if (this.mAccuracy < 0) {
            Log.d(TAG, "Accuracy is wrong");
            return false;
        }
        if (this.mTimeStamp < 0) {
            Log.d(TAG, "Timestamp is wrong");
            return false;
        }
        if (this.mLongitude < -180.0d || this.mLongitude > 180.0d) {
            Log.d(TAG, "Longitude is wrong");
            return false;
        }
        if (this.mLatitude >= -90.0d && this.mLatitude <= 90.0d) {
            return true;
        }
        Log.d(TAG, "Latitidue is wrong");
        return false;
    }

    private void setAttribute() {
        Bundle attribute = new Bundle();
        switch (this.mMode) {
            case 0:
                if (this.mAction == 1) {
                    double[] doubleType = {this.mLatitude, this.mLongitude};
                    int[] intType = {
                        this.mFenceId, this.mRadius, this.mTotalGpsCnt, this.mSuccessGpsCnt
                    };
                    attribute.putIntArray("IntType", intType);
                    attribute.putDoubleArray("DoubleType", doubleType);
                    break;
                } else if (this.mAction == 2) {
                    int[] intType2 = {this.mFenceId};
                    attribute.putIntArray("IntType", intType2);
                    break;
                } else if (this.mAction == 7) {
                    int[] intType3 = {this.mFenceId, this.mRadius, this.mStatus};
                    attribute.putIntArray("IntType", intType3);
                    break;
                } else if (this.mAction == 9) {
                    int[] intType4 = {this.mMin_Ditance, this.mMin_Time};
                    attribute.putIntArray("IntType", intType4);
                    break;
                } else {
                    Log.d(TAG, "This Type is default attribute type");
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
                    Log.d(TAG, "This Type is default attribute type");
                    break;
                }
        }
        attribute.putInt("Mode", this.mMode);
        attribute.putInt("Action", this.mAction);
        Log.d(
                TAG,
                "setAttribute() mode : "
                        + attribute.getInt("Mode")
                        + " action : "
                        + attribute.getInt("Action"));
        super.setAttribute(47, attribute);
    }
}
