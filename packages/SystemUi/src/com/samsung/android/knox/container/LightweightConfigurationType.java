package com.samsung.android.knox.container;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class LightweightConfigurationType extends KnoxConfigurationType {
    public static final Parcelable.Creator<LightweightConfigurationType> CREATOR = new Parcelable.Creator<LightweightConfigurationType>() { // from class: com.samsung.android.knox.container.LightweightConfigurationType.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final LightweightConfigurationType createFromParcel(Parcel parcel) {
            return new LightweightConfigurationType(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public final LightweightConfigurationType createFromParcel(Parcel parcel) {
            return new LightweightConfigurationType(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final LightweightConfigurationType[] newArray(int i) {
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
    public final int describeContents() {
        return 0;
    }

    @Override // com.samsung.android.knox.container.KnoxConfigurationType
    public final void dumpState() {
        Log.d(TAG, "Lightweight config dump START:");
        Log.d(TAG, "mFolderHeaderIcon : " + this.mFolderHeaderIcon);
        Log.d(TAG, "mFolderHeaderTitle : " + this.mFolderHeaderTitle);
        Log.d(TAG, "mFolderDisabledChangeLayout : " + this.mFolderDisabledChangeLayout);
        Log.d(TAG, "Lightweight config dump END.");
    }

    public final String getFolderDisabledChangeLayout() {
        return this.mFolderDisabledChangeLayout;
    }

    public final String getFolderHeaderIcon() {
        return this.mFolderHeaderIcon;
    }

    public final String getFolderHeaderTitle() {
        return this.mFolderHeaderTitle;
    }

    public final void setFolderDisabledChangeLayout(String str) {
        this.mFolderDisabledChangeLayout = str;
    }

    public final void setFolderHeaderIcon(String str) {
        this.mFolderHeaderIcon = str;
    }

    public final void setFolderHeaderTitle(String str) {
        this.mFolderHeaderTitle = str;
    }

    @Override // com.samsung.android.knox.container.KnoxConfigurationType, android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
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
    public final LightweightConfigurationType clone(String str) {
        if (str == null || str.isEmpty()) {
            Log.d(TAG, "clone(): name is either null or empty, hence returning null");
            return null;
        }
        LightweightConfigurationType lightweightConfigurationType = new LightweightConfigurationType();
        cloneConfiguration(lightweightConfigurationType, str);
        lightweightConfigurationType.mFolderHeaderIcon = this.mFolderHeaderIcon;
        lightweightConfigurationType.mFolderHeaderTitle = this.mFolderHeaderTitle;
        lightweightConfigurationType.mFolderDisabledChangeLayout = this.mFolderDisabledChangeLayout;
        return lightweightConfigurationType;
    }

    public LightweightConfigurationType(Parcel parcel) {
        super(parcel);
        String str = null;
        this.mFolderHeaderTitle = null;
        this.mFolderHeaderIcon = null;
        this.mFolderDisabledChangeLayout = null;
        String readString = parcel.readString();
        this.mFolderHeaderIcon = (readString == null || readString.isEmpty()) ? null : readString;
        String readString2 = parcel.readString();
        this.mFolderHeaderTitle = (readString2 == null || readString2.isEmpty()) ? null : readString2;
        String readString3 = parcel.readString();
        if (readString3 != null && !readString3.isEmpty()) {
            str = readString3;
        }
        this.mFolderDisabledChangeLayout = str;
    }
}
