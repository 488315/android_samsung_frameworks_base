package com.samsung.android.hardware.context;

import android.hardware.scontext.SContextConstants;
import android.p009os.Bundle;
import android.p009os.Parcel;
import android.p009os.Parcelable;
import android.util.Log;

/* loaded from: classes5.dex */
public class SemContextPedometerAttribute extends SemContextAttribute {
    public static final Parcelable.Creator<SemContextPedometerAttribute> CREATOR = new Parcelable.Creator<SemContextPedometerAttribute>() { // from class: com.samsung.android.hardware.context.SemContextPedometerAttribute.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextPedometerAttribute createFromParcel(Parcel in) {
            return new SemContextPedometerAttribute(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemContextPedometerAttribute[] newArray(int size) {
            return new SemContextPedometerAttribute[size];
        }
    };
    private static final int MODE_EXERCISE = 1;
    private static final int MODE_USER_INFO = 0;
    private static final String TAG = "SemContextPedometerAttribute";
    private int mExerciseMode;
    private int mGender;
    private double mHeight;
    private int mMode;
    private double mWeight;

    SemContextPedometerAttribute() {
        this.mGender = 1;
        this.mHeight = 170.0d;
        this.mWeight = 60.0d;
        this.mExerciseMode = -1;
        this.mMode = -1;
        this.mMode = 0;
        setAttribute();
    }

    SemContextPedometerAttribute(Parcel src) {
        super(src);
        this.mGender = 1;
        this.mHeight = 170.0d;
        this.mWeight = 60.0d;
        this.mExerciseMode = -1;
        this.mMode = -1;
    }

    public SemContextPedometerAttribute(int gender, double height, double weight) {
        this.mGender = 1;
        this.mHeight = 170.0d;
        this.mWeight = 60.0d;
        this.mExerciseMode = -1;
        this.mMode = -1;
        this.mMode = 0;
        this.mGender = gender;
        this.mHeight = height;
        this.mWeight = weight;
        setAttribute();
    }

    public SemContextPedometerAttribute(int exerciseMode) {
        this.mGender = 1;
        this.mHeight = 170.0d;
        this.mWeight = 60.0d;
        this.mExerciseMode = -1;
        this.mMode = -1;
        this.mMode = 1;
        this.mExerciseMode = exerciseMode;
        setAttribute();
    }

    @Override // com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        int i = this.mGender;
        if (i < 1 || i > 2) {
            Log.m96e(TAG, "The gender is wrong.");
            return false;
        }
        if (this.mHeight <= SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
            Log.m96e(TAG, "The height is wrong.");
            return false;
        }
        if (this.mWeight <= SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
            Log.m96e(TAG, "The weight is wrong.");
            return false;
        }
        if (this.mExerciseMode >= -1) {
            return true;
        }
        Log.m96e(TAG, "The exercise mode is wrong.");
        return false;
    }

    private void setAttribute() {
        Bundle attribute = new Bundle();
        attribute.putInt("mode", this.mMode);
        if (this.mMode == 0) {
            attribute.putInt("gender", this.mGender);
            attribute.putDouble("height", this.mHeight);
            attribute.putDouble("weight", this.mWeight);
        } else {
            attribute.putInt("exercise_mode", this.mExerciseMode);
        }
        super.setAttribute(2, attribute);
    }
}
