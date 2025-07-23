package com.samsung.android.cocktailbar;

import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.RemoteViews;

/* loaded from: classes6.dex */
public class CocktailInfo implements Parcelable {
    public static final Parcelable.Creator<CocktailInfo> CREATOR = new Parcelable.Creator<CocktailInfo>() { // from class: com.samsung.android.cocktailbar.CocktailInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CocktailInfo createFromParcel(Parcel parcel) {
            CocktailInfo cocktailInfo = new CocktailInfo();
            cocktailInfo.readFromParcel(parcel);
            return cocktailInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CocktailInfo[] newArray(int i) {
            return new CocktailInfo[i];
        }
    };
    private int mUserId = 0;
    private int mOrientation = 1;
    private int mDisplayPolicy = 1;
    private int mCategory = 1;
    private RemoteViews mContentView = null;
    private RemoteViews mHelpView = null;
    private Bundle mContentInfo = null;
    private ComponentName mClassInfo = null;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static class Builder {
        private Context mContext;
        private int mOrientation = 1;
        private int mDisplayPolicy = 1;
        private int mCategory = 1;
        private RemoteViews mContentView = null;
        private RemoteViews mHelpView = null;
        private Bundle mContentInfo = null;
        private ComponentName mClassInfo = null;

        public Builder(Context context) {
            this.mContext = context;
        }

        public Builder setOrientation(int i) {
            this.mOrientation = i;
            return this;
        }

        public Builder setDiplayPolicy(int i) {
            this.mDisplayPolicy = i;
            return this;
        }

        public Builder setCategory(int i) {
            this.mCategory = i;
            return this;
        }

        public Builder setContentView(RemoteViews remoteViews) {
            this.mContentView = remoteViews;
            return this;
        }

        public Builder setContentInfo(Bundle bundle) {
            this.mContentInfo = bundle;
            return this;
        }

        public Builder setHelpView(RemoteViews remoteViews) {
            this.mHelpView = remoteViews;
            return this;
        }

        public Builder setClassloader(ComponentName componentName) {
            this.mClassInfo = componentName;
            return this;
        }

        public CocktailInfo build() {
            CocktailInfo cocktailInfo = new CocktailInfo();
            cocktailInfo.mOrientation = this.mOrientation;
            cocktailInfo.mDisplayPolicy = this.mDisplayPolicy;
            cocktailInfo.mCategory = this.mCategory;
            cocktailInfo.mContentView = this.mContentView;
            cocktailInfo.mHelpView = this.mHelpView;
            cocktailInfo.mUserId = this.mContext.getUserId();
            cocktailInfo.mContentInfo = this.mContentInfo;
            cocktailInfo.mClassInfo = this.mClassInfo;
            return cocktailInfo;
        }
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public int getDisplayPolicy() {
        return this.mDisplayPolicy;
    }

    public int getCategory() {
        return this.mCategory;
    }

    public void setCategory(int i) {
        this.mCategory = i;
    }

    public RemoteViews getContentView() {
        return this.mContentView;
    }

    public RemoteViews getHelpView() {
        return this.mHelpView;
    }

    public Bundle getContentInfo() {
        return this.mContentInfo;
    }

    public ComponentName getClassInfo() {
        return this.mClassInfo;
    }

    public void updateContentView(RemoteViews remoteViews, boolean z) {
        RemoteViews remoteViews2;
        if (z && (remoteViews2 = this.mContentView) != null) {
            remoteViews2.mergeRemoteViews(remoteViews);
        } else {
            this.mContentView = remoteViews;
        }
    }

    public void updateHelpView(RemoteViews remoteViews, boolean z) {
        RemoteViews remoteViews2;
        if (z && (remoteViews2 = this.mHelpView) != null) {
            remoteViews2.mergeRemoteViews(remoteViews);
        } else {
            this.mHelpView = remoteViews;
        }
    }

    public void mergeInfo(CocktailInfo cocktailInfo) {
        this.mOrientation = cocktailInfo.mOrientation;
        this.mCategory = cocktailInfo.mCategory;
        this.mDisplayPolicy = cocktailInfo.mDisplayPolicy;
        Bundle bundle = cocktailInfo.mContentInfo;
        if (bundle != null) {
            this.mContentInfo = bundle;
            this.mContentView = null;
        }
        RemoteViews remoteViews = cocktailInfo.mContentView;
        if (remoteViews != null) {
            this.mContentView = remoteViews;
            this.mContentInfo = null;
        }
        RemoteViews remoteViews2 = cocktailInfo.mHelpView;
        if (remoteViews2 != null) {
            this.mHelpView = remoteViews2;
        }
        ComponentName componentName = cocktailInfo.mClassInfo;
        if (componentName != null) {
            this.mClassInfo = componentName;
        }
    }

    public String dump() {
        String str = "U:" + this.mUserId + " ORI:" + this.mOrientation + " DP:" + this.mDisplayPolicy + " CAT:" + this.mCategory;
        if (this.mContentView != null) {
            str = str + " has RemoteViews";
        }
        if (this.mContentInfo != null) {
            str = str + " has ContentInfo";
        }
        if (this.mClassInfo != null) {
            str = str + " ClassInfo : " + this.mClassInfo.flattenToShortString();
        }
        if (this.mHelpView == null) {
            return str;
        }
        return str + " has HelpView";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.mContentView, i);
        parcel.writeParcelable(this.mHelpView, i);
        parcel.writeInt(this.mUserId);
        parcel.writeInt(this.mOrientation);
        parcel.writeInt(this.mDisplayPolicy);
        parcel.writeInt(this.mCategory);
        parcel.writeBundle(this.mContentInfo);
        if (this.mClassInfo != null) {
            parcel.writeInt(1);
            this.mClassInfo.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
    }

    public void readFromParcel(Parcel parcel) {
        this.mContentView = (RemoteViews) parcel.readParcelable(RemoteViews.class.getClassLoader());
        this.mHelpView = (RemoteViews) parcel.readParcelable(RemoteViews.class.getClassLoader());
        this.mUserId = parcel.readInt();
        this.mOrientation = parcel.readInt();
        this.mDisplayPolicy = parcel.readInt();
        this.mCategory = parcel.readInt();
        this.mContentInfo = parcel.readBundle();
        this.mClassInfo = parcel.readInt() != 0 ? new ComponentName(parcel) : null;
    }
}
