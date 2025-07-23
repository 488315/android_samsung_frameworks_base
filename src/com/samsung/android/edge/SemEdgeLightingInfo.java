package com.samsung.android.edge;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Binder;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;

/* loaded from: classes6.dex */
public final class SemEdgeLightingInfo implements Parcelable {
    public static final Parcelable.Creator<SemEdgeLightingInfo> CREATOR = new Parcelable.Creator<SemEdgeLightingInfo>() { // from class: com.samsung.android.edge.SemEdgeLightingInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemEdgeLightingInfo createFromParcel(Parcel parcel) {
            return new SemEdgeLightingInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemEdgeLightingInfo[] newArray(int i) {
            return new SemEdgeLightingInfo[i];
        }
    };
    public static final int REPEAT_INFINITE = -1;
    public static final int TYPE_APPLICATION = 1;
    private static final int TYPE_INTERNAL = 2000;
    public static final int TYPE_NOTIFICATION = 2001;
    private int DEFAULT_LIGHTING_COLOR;
    private int[] mEffectColors;
    private Bundle mExtra;
    private int mRepeatCount;
    private final int mType;
    private int mUserId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SemEdgeLightingInfo() {
        this.mUserId = 0;
        this.DEFAULT_LIGHTING_COLOR = -8081686;
        this.mType = 1;
        this.mEffectColors = new int[]{-8081686};
        this.mRepeatCount = 0;
    }

    public SemEdgeLightingInfo(int i, int[] iArr) {
        this.mUserId = 0;
        this.DEFAULT_LIGHTING_COLOR = -8081686;
        enforceEdgeLightingType(i);
        this.mType = i;
        if (iArr == null || iArr.length == 0) {
            this.mEffectColors = new int[]{this.DEFAULT_LIGHTING_COLOR};
        } else {
            this.mEffectColors = iArr;
        }
        this.mRepeatCount = 0;
    }

    private SemEdgeLightingInfo(Parcel parcel) {
        this.mUserId = 0;
        this.DEFAULT_LIGHTING_COLOR = -8081686;
        this.mType = parcel.readInt();
        int[] iArr = new int[parcel.readInt()];
        this.mEffectColors = iArr;
        parcel.readIntArray(iArr);
        this.mRepeatCount = parcel.readInt();
        this.mExtra = parcel.readBundle();
        this.mUserId = parcel.readInt();
    }

    private void enforceEdgeLightingType(int i) {
        if (i < 2000 || Binder.getCallingUid() == 1000) {
            return;
        }
        throw new SecurityException("only SYSTEM can use the type(" + i + NavigationBarInflaterView.KEY_CODE_END);
    }

    public int getType() {
        return this.mType;
    }

    public int[] getEffectColors() {
        return this.mEffectColors;
    }

    public void setEffectColors(int[] iArr) {
        if (iArr == null || iArr.length < 1) {
            throw new IllegalArgumentException("color set should be more than 1");
        }
        this.mEffectColors = iArr;
    }

    public int getRepeatCount() {
        return this.mRepeatCount;
    }

    public void setRepeatCount(int i) {
        if (i < 0) {
            this.mRepeatCount = -1;
        } else {
            this.mRepeatCount = i;
        }
    }

    public void setExtra(Bundle bundle) {
        this.mExtra = bundle;
    }

    public Bundle getExtra() {
        return this.mExtra;
    }

    public void setUserId(int i) {
        this.mUserId = i;
    }

    public int getUserId() {
        return this.mUserId;
    }

    public String toString() {
        return "SemEdgeLighitngInfo{type = " + this.mType + ", colors= " + Arrays.toString(this.mEffectColors) + ", repeat= " + this.mRepeatCount + ", userId = " + this.mUserId + "}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mType);
        parcel.writeInt(this.mEffectColors.length);
        parcel.writeIntArray(this.mEffectColors);
        parcel.writeInt(this.mRepeatCount);
        parcel.writeBundle(this.mExtra);
        parcel.writeInt(this.mUserId);
    }
}
