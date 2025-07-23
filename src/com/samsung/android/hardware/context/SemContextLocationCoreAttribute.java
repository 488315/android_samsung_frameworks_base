package com.samsung.android.hardware.context;

import android.hardware.scontext.SContextConstants;
import android.location.Location;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/* loaded from: classes6.dex */
public class SemContextLocationCoreAttribute extends SemContextAttribute {
    public static final Parcelable.Creator<SemContextLocationCoreAttribute> CREATOR = new Parcelable.Creator<SemContextLocationCoreAttribute>() { // from class: com.samsung.android.hardware.context.SemContextLocationCoreAttribute.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextLocationCoreAttribute createFromParcel(Parcel parcel) {
            return new SemContextLocationCoreAttribute(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextLocationCoreAttribute[] newArray(int i) {
            return new SemContextLocationCoreAttribute[i];
        }
    };
    private static final String TAG = "SemContextLocationCoreAttribute";
    private int mAccuracy;
    private int mAction;
    private int mBatchingSize;
    private int mFenceId;
    private FusedBatchOption mFusedBatchOption;
    private double mLatitude;
    private Location mLocation;
    private double mLongitude;
    private int mMinDistance;
    private int mMinTime;
    private int mMode;
    private int mRadius;
    private int[] mRawData;
    private int mRequestId;
    private int mStatus;
    private int mSuccessGpsCnt;
    private long mTimeStamp;
    private int mTotalGpsCnt;

    SemContextLocationCoreAttribute() {
        this.mMode = -1;
        this.mAction = -1;
        this.mFenceId = 0;
        this.mRadius = 0;
        this.mStatus = 0;
        this.mTotalGpsCnt = 0;
        this.mSuccessGpsCnt = 0;
        this.mMinDistance = 0;
        this.mMinTime = 0;
        this.mAccuracy = 0;
        this.mTimeStamp = 0L;
        this.mLongitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.mLatitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.mRequestId = 0;
        this.mBatchingSize = 0;
        this.mRawData = null;
        this.mFusedBatchOption = null;
        this.mLocation = null;
        setAttribute();
    }

    private SemContextLocationCoreAttribute(Parcel parcel) {
        super(parcel);
        this.mMode = -1;
        this.mAction = -1;
        this.mFenceId = 0;
        this.mRadius = 0;
        this.mStatus = 0;
        this.mTotalGpsCnt = 0;
        this.mSuccessGpsCnt = 0;
        this.mMinDistance = 0;
        this.mMinTime = 0;
        this.mAccuracy = 0;
        this.mTimeStamp = 0L;
        this.mLongitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.mLatitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.mRequestId = 0;
        this.mBatchingSize = 0;
        this.mRawData = null;
        this.mFusedBatchOption = null;
        this.mLocation = null;
    }

    public SemContextLocationCoreAttribute(int i, int i2) {
        this.mFenceId = 0;
        this.mRadius = 0;
        this.mStatus = 0;
        this.mTotalGpsCnt = 0;
        this.mSuccessGpsCnt = 0;
        this.mMinDistance = 0;
        this.mMinTime = 0;
        this.mAccuracy = 0;
        this.mTimeStamp = 0L;
        this.mLongitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.mLatitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.mRequestId = 0;
        this.mBatchingSize = 0;
        this.mRawData = null;
        this.mFusedBatchOption = null;
        this.mLocation = null;
        this.mMode = i;
        this.mAction = i2;
        setAttribute();
    }

    public SemContextLocationCoreAttribute(int i, int i2, int i3, double d, double d2, int i4, int i5, int i6) {
        this.mStatus = 0;
        this.mMinDistance = 0;
        this.mMinTime = 0;
        this.mAccuracy = 0;
        this.mTimeStamp = 0L;
        this.mRequestId = 0;
        this.mBatchingSize = 0;
        this.mRawData = null;
        this.mFusedBatchOption = null;
        this.mLocation = null;
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

    public SemContextLocationCoreAttribute(int i, int i2, int i3) {
        this.mFenceId = 0;
        this.mRadius = 0;
        this.mStatus = 0;
        this.mTotalGpsCnt = 0;
        this.mSuccessGpsCnt = 0;
        this.mMinDistance = 0;
        this.mMinTime = 0;
        this.mAccuracy = 0;
        this.mTimeStamp = 0L;
        this.mLongitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.mLatitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.mRequestId = 0;
        this.mBatchingSize = 0;
        this.mRawData = null;
        this.mFusedBatchOption = null;
        this.mLocation = null;
        this.mMode = i;
        this.mAction = i2;
        if (i == 0) {
            this.mFenceId = i3;
        } else if (i == 3) {
            if (i2 == 18) {
                this.mRequestId = i3;
            } else if (i2 == 19) {
                this.mBatchingSize = i3;
            }
        }
        setAttribute();
    }

    public SemContextLocationCoreAttribute(int i, int i2, int i3, int i4, int i5) {
        this.mTotalGpsCnt = 0;
        this.mSuccessGpsCnt = 0;
        this.mMinDistance = 0;
        this.mMinTime = 0;
        this.mAccuracy = 0;
        this.mTimeStamp = 0L;
        this.mLongitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.mLatitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.mRequestId = 0;
        this.mBatchingSize = 0;
        this.mRawData = null;
        this.mFusedBatchOption = null;
        this.mLocation = null;
        this.mMode = i;
        this.mAction = i2;
        this.mFenceId = i3;
        this.mRadius = i4;
        this.mStatus = i5;
        setAttribute();
    }

    public SemContextLocationCoreAttribute(int i, int i2, int i3, int i4) {
        this.mFenceId = 0;
        this.mRadius = 0;
        this.mStatus = 0;
        this.mTotalGpsCnt = 0;
        this.mSuccessGpsCnt = 0;
        this.mAccuracy = 0;
        this.mTimeStamp = 0L;
        this.mLongitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.mLatitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.mRequestId = 0;
        this.mBatchingSize = 0;
        this.mRawData = null;
        this.mFusedBatchOption = null;
        this.mLocation = null;
        this.mMode = i;
        this.mAction = i2;
        this.mMinDistance = i3;
        this.mMinTime = i4;
        setAttribute();
    }

    public SemContextLocationCoreAttribute(int i, int i2, double d, double d2, int i3, long j) {
        this.mFenceId = 0;
        this.mRadius = 0;
        this.mStatus = 0;
        this.mTotalGpsCnt = 0;
        this.mSuccessGpsCnt = 0;
        this.mMinDistance = 0;
        this.mMinTime = 0;
        this.mRequestId = 0;
        this.mBatchingSize = 0;
        this.mRawData = null;
        this.mFusedBatchOption = null;
        this.mLocation = null;
        this.mMode = i;
        this.mAction = i2;
        this.mLatitude = d;
        this.mLongitude = d2;
        this.mAccuracy = i3;
        this.mTimeStamp = j;
        setAttribute();
    }

    public SemContextLocationCoreAttribute(int i, int i2, int i3, long j, int i4, int i5, double d, float f) {
        this.mFenceId = 0;
        this.mRadius = 0;
        this.mStatus = 0;
        this.mTotalGpsCnt = 0;
        this.mSuccessGpsCnt = 0;
        this.mMinDistance = 0;
        this.mMinTime = 0;
        this.mAccuracy = 0;
        this.mTimeStamp = 0L;
        this.mLongitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.mLatitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.mBatchingSize = 0;
        this.mRawData = null;
        this.mFusedBatchOption = null;
        this.mLocation = null;
        this.mMode = i;
        this.mAction = i2;
        this.mRequestId = i3;
        this.mFusedBatchOption = new FusedBatchOption(j, i4, i5, d, f);
        setAttribute();
    }

    public SemContextLocationCoreAttribute(int i, int i2, Location location) {
        this.mFenceId = 0;
        this.mRadius = 0;
        this.mStatus = 0;
        this.mTotalGpsCnt = 0;
        this.mSuccessGpsCnt = 0;
        this.mMinDistance = 0;
        this.mMinTime = 0;
        this.mAccuracy = 0;
        this.mTimeStamp = 0L;
        this.mLongitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.mLatitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.mRequestId = 0;
        this.mBatchingSize = 0;
        this.mRawData = null;
        this.mFusedBatchOption = null;
        this.mMode = i;
        this.mAction = i2;
        this.mLocation = location;
        setAttribute();
    }

    public SemContextLocationCoreAttribute(int i, int i2, int[] iArr) {
        this.mFenceId = 0;
        this.mRadius = 0;
        this.mStatus = 0;
        this.mTotalGpsCnt = 0;
        this.mSuccessGpsCnt = 0;
        this.mMinDistance = 0;
        this.mMinTime = 0;
        this.mAccuracy = 0;
        this.mTimeStamp = 0L;
        this.mLongitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.mLatitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.mRequestId = 0;
        this.mBatchingSize = 0;
        this.mRawData = null;
        this.mFusedBatchOption = null;
        this.mLocation = null;
        this.mMode = i;
        this.mAction = i2;
        int[] iArr2 = new int[iArr.length];
        this.mRawData = iArr2;
        System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
        setAttribute();
    }

    @Override // com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        int i = this.mMode;
        if (i < -1 || i > 3) {
            Log.d(TAG, "Mode value is wrong!!");
            return false;
        }
        if (i == 0) {
            int i2 = this.mAction;
            if ((i2 < -1 || i2 > 10) && i2 != 23) {
                Log.d(TAG, "Action value is wrong!!");
                return false;
            }
        } else if (i == 1) {
            int i3 = this.mAction;
            if (i3 < -1 || i3 > 14) {
                Log.d(TAG, "Action value is wrong!!");
                return false;
            }
        } else if (i == 3) {
            int i4 = this.mAction;
            if (i4 < 16 || i4 > 22) {
                Log.d(TAG, "Action value is wrong!!");
                return false;
            }
            if ((i4 == 16 || i4 == 17) && !this.mFusedBatchOption.isValid()) {
                Log.d(TAG, "FusedBatchOption is wrong");
                return false;
            }
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
        if (this.mMinDistance < 0) {
            Log.d(TAG, "Minimum distance is wrong");
            return false;
        }
        if (this.mMinTime < 0) {
            Log.d(TAG, "Minimum time is wrong");
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
        if (d2 < -90.0d || d2 > 90.0d) {
            Log.d(TAG, "Latitude is wrong");
            return false;
        }
        if (this.mRequestId < 0) {
            Log.d(TAG, "RequestId is wrong");
            return false;
        }
        if (this.mBatchingSize >= 0) {
            return true;
        }
        Log.d(TAG, "BatchingSize is wrong");
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
                bundle.putIntArray("IntType", new int[]{this.mMinDistance, this.mMinTime});
            } else if (i2 == 23) {
                int[] iArr = this.mRawData;
                int[] iArr2 = new int[iArr.length];
                System.arraycopy(iArr, 0, iArr2, 0, iArr.length);
                bundle.putIntArray("IntType", iArr2);
            } else {
                Log.d(TAG, "This Type is default attribute type");
            }
        } else if (i != 1) {
            if (i == 3) {
                int i3 = this.mAction;
                if (i3 == 16 || i3 == 17) {
                    int[] iArr3 = {this.mRequestId, this.mFusedBatchOption.user_info, this.mFusedBatchOption.flags};
                    long[] jArr = {this.mFusedBatchOption.period};
                    double[] dArr2 = {this.mFusedBatchOption.max_power};
                    float[] fArr = {this.mFusedBatchOption.distance_thrs};
                    bundle.putIntArray("IntType", iArr3);
                    bundle.putLongArray("LongType", jArr);
                    bundle.putDoubleArray("DoubleType", dArr2);
                    bundle.putFloatArray("FloatType", fArr);
                } else if (i3 == 18) {
                    bundle.putIntArray("IntType", new int[]{this.mRequestId});
                } else if (i3 == 19) {
                    bundle.putIntArray("IntType", new int[]{this.mBatchingSize});
                } else if (i3 == 21) {
                    String provider = this.mLocation.getProvider();
                    long[] jArr2 = {this.mLocation.getTime()};
                    double[] dArr3 = {this.mLocation.getLatitude(), this.mLocation.getLongitude(), this.mLocation.getAltitude()};
                    float[] fArr2 = {this.mLocation.getSpeed(), this.mLocation.getBearing(), this.mLocation.getAccuracy()};
                    bundle.putString("StringType", provider);
                    bundle.putLongArray("IntType", jArr2);
                    bundle.putDoubleArray("DoubleType", dArr3);
                    bundle.putFloatArray("FloatType", fArr2);
                } else {
                    Log.d(TAG, "This Type is default attribute type");
                }
            }
        } else if (this.mAction == 8) {
            double[] dArr4 = {this.mLatitude, this.mLongitude};
            int[] iArr4 = {this.mAccuracy};
            long[] jArr3 = {this.mTimeStamp};
            bundle.putDoubleArray("DoubleType", dArr4);
            bundle.putIntArray("IntType", iArr4);
            bundle.putLongArray("LongType", jArr3);
        } else {
            Log.d(TAG, "This Type is default attribute type");
        }
        bundle.putInt("Mode", this.mMode);
        bundle.putInt("Action", this.mAction);
        Log.d(TAG, "setAttribute() mode : " + bundle.getInt("Mode") + " action : " + bundle.getInt("Action"));
        super.setAttribute(47, bundle);
    }

    private static class FusedBatchOption {
        final float distance_thrs;
        final int flags;
        final double max_power;
        final long period;
        final int user_info;

        FusedBatchOption(long j, int i, int i2, double d, float f) {
            this.period = j;
            this.user_info = i;
            this.flags = i2;
            this.max_power = d;
            this.distance_thrs = f;
        }

        boolean isValid() {
            if (this.period < 0) {
                Log.d(SemContextLocationCoreAttribute.TAG, "FusedBatchOption.period is wrong.");
                return false;
            }
            if (this.user_info < 0) {
                Log.d(SemContextLocationCoreAttribute.TAG, "FusedBatchOption.user_info is wrong.");
                return false;
            }
            if (this.flags < 0) {
                Log.d(SemContextLocationCoreAttribute.TAG, "FusedBatchOption.flags is wrong.");
                return false;
            }
            if (this.max_power < SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
                Log.d(SemContextLocationCoreAttribute.TAG, "FusedBatchOption.max_power is wrong.");
                return false;
            }
            if (this.distance_thrs >= 0.0f) {
                return true;
            }
            Log.d(SemContextLocationCoreAttribute.TAG, "FusedBatchOption.distance_thrs is wrong.");
            return false;
        }
    }
}
