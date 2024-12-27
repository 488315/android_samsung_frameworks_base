package com.samsung.android.edge;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Binder;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

/* loaded from: classes6.dex */
public final class SemEdgeLightingInfo implements Parcelable {
    public static final Parcelable.Creator<SemEdgeLightingInfo> CREATOR =
            new Parcelable.Creator<
                    SemEdgeLightingInfo>() { // from class:
                                             // com.samsung.android.edge.SemEdgeLightingInfo.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemEdgeLightingInfo createFromParcel(Parcel source) {
                    return new SemEdgeLightingInfo(source);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemEdgeLightingInfo[] newArray(int size) {
                    return new SemEdgeLightingInfo[size];
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

    public SemEdgeLightingInfo() {
        this.mUserId = 0;
        this.DEFAULT_LIGHTING_COLOR = -8081686;
        this.mType = 1;
        this.mEffectColors = new int[] {this.DEFAULT_LIGHTING_COLOR};
        this.mRepeatCount = 0;
    }

    public SemEdgeLightingInfo(int type, int[] color) {
        this.mUserId = 0;
        this.DEFAULT_LIGHTING_COLOR = -8081686;
        enforceEdgeLightingType(type);
        this.mType = type;
        if (color == null || color.length == 0) {
            this.mEffectColors = new int[] {this.DEFAULT_LIGHTING_COLOR};
        } else {
            this.mEffectColors = color;
        }
        this.mRepeatCount = 0;
    }

    private SemEdgeLightingInfo(Parcel source) {
        this.mUserId = 0;
        this.DEFAULT_LIGHTING_COLOR = -8081686;
        this.mType = source.readInt();
        int effectColorSize = source.readInt();
        this.mEffectColors = new int[effectColorSize];
        source.readIntArray(this.mEffectColors);
        this.mRepeatCount = source.readInt();
        this.mExtra = source.readBundle();
        this.mUserId = source.readInt();
    }

    private void enforceEdgeLightingType(int type) {
        if (type >= 2000 && Binder.getCallingUid() != 1000) {
            throw new SecurityException(
                    "only SYSTEM can use the type("
                            + type
                            + NavigationBarInflaterView.KEY_CODE_END);
        }
    }

    public int getType() {
        return this.mType;
    }

    public int[] getEffectColors() {
        return this.mEffectColors;
    }

    public void setEffectColors(int[] colors) {
        if (colors == null || colors.length < 1) {
            throw new IllegalArgumentException("color set should be more than 1");
        }
        this.mEffectColors = colors;
    }

    public int getRepeatCount() {
        return this.mRepeatCount;
    }

    public void setRepeatCount(int count) {
        if (count < 0) {
            this.mRepeatCount = -1;
        } else {
            this.mRepeatCount = count;
        }
    }

    public void setExtra(Bundle extra) {
        this.mExtra = extra;
    }

    public Bundle getExtra() {
        return this.mExtra;
    }

    public void setUserId(int userId) {
        this.mUserId = userId;
    }

    public int getUserId() {
        return this.mUserId;
    }

    public String toString() {
        return "SemEdgeLighitngInfo{type = "
                + this.mType
                + ", colors= "
                + Arrays.toString(this.mEffectColors)
                + ", repeat= "
                + this.mRepeatCount
                + ", userId = "
                + this.mUserId
                + "}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mType);
        dest.writeInt(this.mEffectColors.length);
        dest.writeIntArray(this.mEffectColors);
        dest.writeInt(this.mRepeatCount);
        dest.writeBundle(this.mExtra);
        dest.writeInt(this.mUserId);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
