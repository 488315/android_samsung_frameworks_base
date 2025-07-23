package com.android.internal.statusbar;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArrayMap;
import com.android.internal.view.AppearanceRegion;

/* loaded from: classes4.dex */
public final class RegisterStatusBarResult implements Parcelable {
    public static final Parcelable.Creator<RegisterStatusBarResult> CREATOR = new Parcelable.Creator<RegisterStatusBarResult>() { // from class: com.android.internal.statusbar.RegisterStatusBarResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RegisterStatusBarResult createFromParcel(Parcel parcel) {
            return new RegisterStatusBarResult(parcel.createTypedArrayMap(StatusBarIcon.CREATOR), parcel.readInt(), parcel.readInt(), (AppearanceRegion[]) parcel.readParcelableArray(null, AppearanceRegion.class), parcel.readInt(), parcel.readInt(), parcel.readBoolean(), parcel.readInt(), parcel.readBoolean(), parcel.readInt(), parcel.readInt(), parcel.readString(), parcel.readInt(), (LetterboxDetails[]) parcel.readParcelableArray(null, LetterboxDetails.class));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RegisterStatusBarResult[] newArray(int i) {
            return new RegisterStatusBarResult[i];
        }
    };
    public final int mAppearance;
    public final AppearanceRegion[] mAppearanceRegions;
    public final int mBehavior;
    public final int mDisabledFlags1;
    public final int mDisabledFlags2;
    public final ArrayMap<String, StatusBarIcon> mIcons;
    public final int mImeBackDisposition;
    public final int mImeWindowVis;
    public final LetterboxDetails[] mLetterboxDetails;
    public final boolean mNavbarColorManagedByIme;
    public final String mPackageName;
    public final int mRequestedVisibleTypes;
    public final boolean mShowImeSwitcher;
    public final int mTransientBarTypes;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public RegisterStatusBarResult(ArrayMap<String, StatusBarIcon> arrayMap, int i, int i2, AppearanceRegion[] appearanceRegionArr, int i3, int i4, boolean z, int i5, boolean z2, int i6, int i7, String str, int i8, LetterboxDetails[] letterboxDetailsArr) {
        this.mIcons = new ArrayMap<>(arrayMap);
        this.mDisabledFlags1 = i;
        this.mAppearance = i2;
        this.mAppearanceRegions = appearanceRegionArr;
        this.mImeWindowVis = i3;
        this.mImeBackDisposition = i4;
        this.mShowImeSwitcher = z;
        this.mDisabledFlags2 = i5;
        this.mNavbarColorManagedByIme = z2;
        this.mBehavior = i6;
        this.mRequestedVisibleTypes = i7;
        this.mPackageName = str;
        this.mTransientBarTypes = i8;
        this.mLetterboxDetails = letterboxDetailsArr;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedArrayMap(this.mIcons, i);
        parcel.writeInt(this.mDisabledFlags1);
        parcel.writeInt(this.mAppearance);
        parcel.writeParcelableArray(this.mAppearanceRegions, 0);
        parcel.writeInt(this.mImeWindowVis);
        parcel.writeInt(this.mImeBackDisposition);
        parcel.writeBoolean(this.mShowImeSwitcher);
        parcel.writeInt(this.mDisabledFlags2);
        parcel.writeBoolean(this.mNavbarColorManagedByIme);
        parcel.writeInt(this.mBehavior);
        parcel.writeInt(this.mRequestedVisibleTypes);
        parcel.writeString(this.mPackageName);
        parcel.writeInt(this.mTransientBarTypes);
        parcel.writeParcelableArray(this.mLetterboxDetails, i);
    }
}
