package com.samsung.android.knox.container;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;

/* loaded from: classes4.dex */
public class LightweightConfigurationType extends KnoxConfigurationType {
    public static final Parcelable.Creator<LightweightConfigurationType> CREATOR = new Parcelable.Creator<LightweightConfigurationType>() { // from class: com.samsung.android.knox.container.LightweightConfigurationType.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LightweightConfigurationType createFromParcel(Parcel parcel) {
            return new LightweightConfigurationType(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LightweightConfigurationType[] newArray(int i) {
            Log.d(LightweightConfigurationType.TAG, "LightweightConfigurationType[] array to be created");
            return new LightweightConfigurationType[i];
        }
    };
    public static final String TAG = "LightweightConfigurationType";
    public String mFolderDisabledChangeLayout;
    public String mFolderHeaderIcon;
    public String mFolderHeaderTitle;

    public LightweightConfigurationType() {
        this.mFolderHeaderTitle = null;
        this.mFolderHeaderIcon = null;
        this.mFolderDisabledChangeLayout = null;
    }

    @Override // com.samsung.android.knox.container.KnoxConfigurationType, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.samsung.android.knox.container.KnoxConfigurationType
    public void dumpState() {
        Log.d(TAG, "Lightweight config dump START:");
        Log.d(TAG, "mFolderHeaderIcon : " + this.mFolderHeaderIcon);
        Log.d(TAG, "mFolderHeaderTitle : " + this.mFolderHeaderTitle);
        ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("mFolderDisabledChangeLayout : "), this.mFolderDisabledChangeLayout, TAG);
        super.dumpState();
        Log.d(TAG, "Lightweight config dump END.");
    }

    public String getFolderDisabledChangeLayout() {
        return this.mFolderDisabledChangeLayout;
    }

    public String getFolderHeaderIcon() {
        return this.mFolderHeaderIcon;
    }

    public String getFolderHeaderTitle() {
        return this.mFolderHeaderTitle;
    }

    public void setFolderDisabledChangeLayout(String str) {
        this.mFolderDisabledChangeLayout = str;
    }

    public void setFolderHeaderIcon(String str) {
        this.mFolderHeaderIcon = str;
    }

    public void setFolderHeaderTitle(String str) {
        this.mFolderHeaderTitle = str;
    }

    @Override // com.samsung.android.knox.container.KnoxConfigurationType, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        String str = this.mFolderHeaderIcon;
        if (str != null) {
            parcel.writeString(str);
        } else {
            parcel.writeString("");
        }
        String str2 = this.mFolderHeaderTitle;
        if (str2 != null) {
            parcel.writeString(str2);
        } else {
            parcel.writeString("");
        }
        String str3 = this.mFolderDisabledChangeLayout;
        if (str3 != null) {
            parcel.writeString(str3);
        } else {
            parcel.writeString("");
        }
    }

    @Override // com.samsung.android.knox.container.KnoxConfigurationType
    public LightweightConfigurationType clone(String str) {
        if (str == null || str.isEmpty()) {
            Log.d(TAG, "clone(): name is either null or empty, hence returning null");
            return null;
        }
        LightweightConfigurationType lightweightConfigurationType = new LightweightConfigurationType();
        cloneConfiguration(lightweightConfigurationType, str);
        lightweightConfigurationType.setFolderHeaderIcon(this.mFolderHeaderIcon);
        lightweightConfigurationType.setFolderHeaderTitle(this.mFolderHeaderTitle);
        lightweightConfigurationType.setFolderDisabledChangeLayout(this.mFolderDisabledChangeLayout);
        return lightweightConfigurationType;
    }

    public LightweightConfigurationType(Parcel parcel) {
        super(parcel);
        String str = null;
        this.mFolderHeaderTitle = null;
        this.mFolderHeaderIcon = null;
        this.mFolderDisabledChangeLayout = null;
        String string = parcel.readString();
        this.mFolderHeaderIcon = (string == null || string.isEmpty()) ? null : string;
        String string2 = parcel.readString();
        this.mFolderHeaderTitle = (string2 == null || string2.isEmpty()) ? null : string2;
        String string3 = parcel.readString();
        if (string3 != null && !string3.isEmpty()) {
            str = string3;
        }
        this.mFolderDisabledChangeLayout = str;
    }
}
