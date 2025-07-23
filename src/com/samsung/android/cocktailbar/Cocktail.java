package com.samsung.android.cocktailbar;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.RemoteViews;

/* loaded from: classes6.dex */
public class Cocktail implements Parcelable {
    public static final Parcelable.Creator<Cocktail> CREATOR = new Parcelable.Creator<Cocktail>() { // from class: com.samsung.android.cocktailbar.Cocktail.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Cocktail createFromParcel(Parcel parcel) {
            Cocktail cocktail = new Cocktail();
            cocktail.readFromParcel(parcel);
            return cocktail;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Cocktail[] newArray(int i) {
            return new Cocktail[i];
        }
    };
    public static final int STATE_DISABLE = 2;
    public static final int STATE_ENABLE = 1;
    public static final int STATE_NONE = 0;
    private PendingIntent mBroadcast;
    private int mCocktailId;
    private CocktailInfo mCocktailInfo;
    private boolean mEnable;
    private boolean mIsPackageSuspended;
    private boolean mIsPackageUpdated;
    private CocktailProviderInfo mProviderInfo;
    private int mState;
    private int mUid;
    private int mVersion;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Cocktail() {
        this.mUid = 0;
        this.mCocktailInfo = new CocktailInfo();
        this.mVersion = 1;
        this.mState = 0;
        this.mEnable = true;
        this.mIsPackageUpdated = false;
        this.mIsPackageSuspended = false;
    }

    public Cocktail(int i) {
        this.mUid = 0;
        this.mCocktailInfo = new CocktailInfo();
        this.mVersion = 1;
        this.mState = 0;
        this.mEnable = true;
        this.mIsPackageUpdated = false;
        this.mIsPackageSuspended = false;
        this.mCocktailId = i;
    }

    public Cocktail(int i, CocktailInfo cocktailInfo) {
        this(i);
        this.mCocktailInfo = cocktailInfo;
    }

    public void setProviderInfo(CocktailProviderInfo cocktailProviderInfo) {
        this.mProviderInfo = cocktailProviderInfo;
    }

    public void setUid(int i) {
        this.mUid = i;
    }

    public void setVersion(int i) {
        this.mVersion = i;
    }

    public int getCocktailId() {
        return this.mCocktailId;
    }

    public CocktailInfo getCocktailInfo() {
        return this.mCocktailInfo;
    }

    public int getUid() {
        return this.mUid;
    }

    public int getVersion() {
        return this.mVersion;
    }

    public CocktailProviderInfo getProviderInfo() {
        return this.mProviderInfo;
    }

    public PendingIntent getBroadcast() {
        return this.mBroadcast;
    }

    public void setBroadcast(PendingIntent pendingIntent) {
        this.mBroadcast = pendingIntent;
    }

    public ComponentName getProvider() {
        CocktailProviderInfo cocktailProviderInfo = this.mProviderInfo;
        if (cocktailProviderInfo != null) {
            return cocktailProviderInfo.provider;
        }
        return null;
    }

    public int getState() {
        return this.mState;
    }

    public void setState(int i) {
        this.mState = i;
    }

    public void setPackageUpdated(boolean z) {
        this.mIsPackageUpdated = z;
    }

    public boolean isPackageUpdated() {
        return this.mIsPackageUpdated;
    }

    public void setPackageSuspended(boolean z) {
        this.mIsPackageSuspended = z;
    }

    public boolean isPackageSuspended() {
        return this.mIsPackageSuspended;
    }

    @Deprecated
    public void addCocktailInfo(CocktailInfo cocktailInfo) {
        this.mCocktailInfo = cocktailInfo;
    }

    public void updateCocktailInfo(CocktailInfo cocktailInfo) {
        CocktailInfo cocktailInfo2 = this.mCocktailInfo;
        if (cocktailInfo2 == null || cocktailInfo == null) {
            this.mCocktailInfo = cocktailInfo;
        } else {
            cocktailInfo2.mergeInfo(cocktailInfo);
        }
    }

    public void updateCocktailContentView(RemoteViews remoteViews, boolean z) {
        CocktailInfo cocktailInfo = this.mCocktailInfo;
        if (cocktailInfo != null) {
            cocktailInfo.updateContentView(remoteViews, z);
        }
    }

    public void updateCocktailHelpView(RemoteViews remoteViews, boolean z) {
        CocktailInfo cocktailInfo = this.mCocktailInfo;
        if (cocktailInfo != null) {
            cocktailInfo.updateHelpView(remoteViews, z);
        }
    }

    public String getUpdateIntentName() {
        return getUpdateIntentName(this.mVersion);
    }

    public static String getUpdateIntentName(int i) {
        if (i == 2) {
            return CocktailBarManager.ACTION_COCKTAIL_UPDATE_V2;
        }
        return CocktailBarManager.ACTION_COCKTAIL_UPDATE;
    }

    public String dump() {
        String str = "[CocktailId:" + this.mCocktailId + " uid:" + this.mUid + " version:" + this.mVersion + " state:" + this.mState;
        if (this.mBroadcast != null) {
            str = str + " has broadcast";
        }
        CocktailInfo cocktailInfo = this.mCocktailInfo;
        if (cocktailInfo != null) {
            str = str + " " + cocktailInfo.dump();
        }
        return str + " ]";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mCocktailId);
        parcel.writeInt(this.mUid);
        parcel.writeInt(this.mVersion);
        parcel.writeInt(this.mState);
        parcel.writeParcelable(this.mBroadcast, i);
        parcel.writeParcelable(this.mProviderInfo, i);
        parcel.writeParcelable(this.mCocktailInfo, i);
        if (this.mIsPackageUpdated) {
            parcel.writeByte((byte) 1);
        } else {
            parcel.writeByte((byte) 0);
        }
        if (this.mIsPackageSuspended) {
            parcel.writeByte((byte) 1);
        } else {
            parcel.writeByte((byte) 0);
        }
    }

    public void readFromParcel(Parcel parcel) {
        this.mCocktailId = parcel.readInt();
        this.mUid = parcel.readInt();
        this.mVersion = parcel.readInt();
        this.mState = parcel.readInt();
        this.mBroadcast = (PendingIntent) parcel.readParcelable(PendingIntent.class.getClassLoader());
        this.mProviderInfo = (CocktailProviderInfo) parcel.readParcelable(ComponentName.class.getClassLoader());
        this.mCocktailInfo = (CocktailInfo) parcel.readParcelable(CocktailInfo.class.getClassLoader());
        this.mIsPackageUpdated = parcel.readByte() == 1;
        this.mIsPackageSuspended = parcel.readByte() == 1;
    }
}
