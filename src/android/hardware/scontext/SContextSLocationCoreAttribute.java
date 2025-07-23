package android.hardware.scontext;

import android.os.Bundle;
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

    public SContextSLocationCoreAttribute(int i, int i2) {
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
        this.mMode = i;
        this.mAction = i2;
        setAttribute();
    }

    public SContextSLocationCoreAttribute(int i, int i2, int i3, double d, double d2, int i4, int i5, int i6) {
        this.mStatus = 0;
        this.mMin_Ditance = 0;
        this.mMin_Time = 0;
        this.mAccuracy = 0;
        this.mTimeStamp = 0L;
        this.mMode = i;
        this.mAction = i2;
        this.mFenceId = i3;
        this.mRadius = i4;
        this.mTotalGpsCnt = i5;
        this.mSuccessGpsCnt = i6;
        this.mLatitude = d;
        this.mLongitude = d2;
        setAttribute();
    }

    public SContextSLocationCoreAttribute(int i, int i2, int i3) {
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
        this.mMode = i;
        this.mAction = i2;
        this.mFenceId = i3;
        setAttribute();
    }

    public SContextSLocationCoreAttribute(int i, int i2, int i3, int i4, int i5) {
        this.mTotalGpsCnt = 0;
        this.mSuccessGpsCnt = 0;
        this.mMin_Ditance = 0;
        this.mMin_Time = 0;
        this.mAccuracy = 0;
        this.mTimeStamp = 0L;
        this.mLongitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.mLatitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.mMode = i;
        this.mAction = i2;
        this.mFenceId = i3;
        this.mRadius = i4;
        this.mStatus = i5;
        setAttribute();
    }

    public SContextSLocationCoreAttribute(int i, int i2, int i3, int i4) {
        this.mFenceId = 0;
        this.mRadius = 0;
        this.mStatus = 0;
        this.mTotalGpsCnt = 0;
        this.mSuccessGpsCnt = 0;
        this.mAccuracy = 0;
        this.mTimeStamp = 0L;
        this.mLongitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.mLatitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.mMode = i;
        this.mAction = i2;
        this.mMin_Ditance = i3;
        this.mMin_Time = i4;
        setAttribute();
    }

    public SContextSLocationCoreAttribute(int i, int i2, double d, double d2, int i3, long j) {
        this.mFenceId = 0;
        this.mRadius = 0;
        this.mStatus = 0;
        this.mTotalGpsCnt = 0;
        this.mSuccessGpsCnt = 0;
        this.mMin_Ditance = 0;
        this.mMin_Time = 0;
        this.mMode = i;
        this.mAction = i2;
        this.mLatitude = d;
        this.mLongitude = d2;
        this.mAccuracy = i3;
        this.mTimeStamp = j;
        setAttribute();
    }

    @Override // android.hardware.scontext.SContextAttribute, com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        int i;
        int i2 = this.mMode;
        if (i2 < -1 || i2 > 1) {
            Log.d(TAG, "Mode value is wrong!!");
            return false;
        }
        if (i2 == 0) {
            int i3 = this.mAction;
            if (i3 < -1 || i3 > 10) {
                Log.d(TAG, "Action value is wrong!!");
                return false;
            }
        } else if (i2 == 1 && ((i = this.mAction) < -1 || i > 14)) {
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
        double d = this.mLongitude;
        if (d < -180.0d || d > 180.0d) {
            Log.d(TAG, "Longitude is wrong");
            return false;
        }
        double d2 = this.mLatitude;
        if (d2 >= -90.0d && d2 <= 90.0d) {
            return true;
        }
        Log.d(TAG, "Latitidue is wrong");
        return false;
    }

    private void setAttribute() {
        Bundle bundle = new Bundle();
        int i = this.mMode;
        if (i == 0) {
            int i2 = this.mAction;
            if (i2 == 1) {
                double[] dArr = {this.mLatitude, this.mLongitude};
                bundle.putIntArray("IntType", new int[]{this.mFenceId, this.mRadius, this.mTotalGpsCnt, this.mSuccessGpsCnt});
                bundle.putDoubleArray("DoubleType", dArr);
            } else if (i2 == 2) {
                bundle.putIntArray("IntType", new int[]{this.mFenceId});
            } else if (i2 == 7) {
                bundle.putIntArray("IntType", new int[]{this.mFenceId, this.mRadius, this.mStatus});
            } else if (i2 == 9) {
                bundle.putIntArray("IntType", new int[]{this.mMin_Ditance, this.mMin_Time});
            } else {
                Log.d(TAG, "This Type is default attribute type");
            }
        } else if (i == 1) {
            if (this.mAction == 8) {
                double[] dArr2 = {this.mLatitude, this.mLongitude};
                int[] iArr = {this.mAccuracy};
                long[] jArr = {this.mTimeStamp};
                bundle.putDoubleArray("DoubleType", dArr2);
                bundle.putIntArray("IntType", iArr);
                bundle.putLongArray("LongType", jArr);
            } else {
                Log.d(TAG, "This Type is default attribute type");
            }
        }
        bundle.putInt("Mode", this.mMode);
        bundle.putInt("Action", this.mAction);
        Log.d(TAG, "setAttribute() mode : " + bundle.getInt("Mode") + " action : " + bundle.getInt("Action"));
        super.setAttribute(47, bundle);
    }
}
