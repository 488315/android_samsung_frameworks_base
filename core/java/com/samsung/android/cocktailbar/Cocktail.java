package com.samsung.android.cocktailbar;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.RemoteViews;

/* loaded from: classes5.dex */
public class Cocktail implements Parcelable {
  public static final Parcelable.Creator<Cocktail> CREATOR =
      new Parcelable.Creator<Cocktail>() { // from class: com.samsung.android.cocktailbar.Cocktail.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Cocktail createFromParcel(Parcel in) {
          Cocktail data = new Cocktail();
          data.readFromParcel(in);
          return data;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Cocktail[] newArray(int size) {
          return new Cocktail[size];
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

  public Cocktail() {
    this.mUid = 0;
    this.mCocktailInfo = new CocktailInfo();
    this.mVersion = 1;
    this.mState = 0;
    this.mEnable = true;
    this.mIsPackageUpdated = false;
    this.mIsPackageSuspended = false;
  }

  public Cocktail(int cocktailId) {
    this.mUid = 0;
    this.mCocktailInfo = new CocktailInfo();
    this.mVersion = 1;
    this.mState = 0;
    this.mEnable = true;
    this.mIsPackageUpdated = false;
    this.mIsPackageSuspended = false;
    this.mCocktailId = cocktailId;
  }

  public Cocktail(int cocktailId, CocktailInfo cocktailInfo) {
    this(cocktailId);
    this.mCocktailInfo = cocktailInfo;
  }

  public void setProviderInfo(CocktailProviderInfo providerInfo) {
    this.mProviderInfo = providerInfo;
  }

  public void setUid(int uid) {
    this.mUid = uid;
  }

  public void setVersion(int version) {
    this.mVersion = version;
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

  public void setBroadcast(PendingIntent broadcast) {
    this.mBroadcast = broadcast;
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

  public void setState(int state) {
    this.mState = state;
  }

  public void setPackageUpdated(boolean isUpdated) {
    this.mIsPackageUpdated = isUpdated;
  }

  public boolean isPackageUpdated() {
    return this.mIsPackageUpdated;
  }

  public void setPackageSuspended(boolean isSuspended) {
    this.mIsPackageSuspended = isSuspended;
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

  public void updateCocktailContentView(RemoteViews contentView, boolean isPartialUpdate) {
    CocktailInfo cocktailInfo = this.mCocktailInfo;
    if (cocktailInfo != null) {
      cocktailInfo.updateContentView(contentView, isPartialUpdate);
    }
  }

  public void updateCocktailHelpView(RemoteViews helpView, boolean isPartialUpdate) {
    CocktailInfo cocktailInfo = this.mCocktailInfo;
    if (cocktailInfo != null) {
      cocktailInfo.updateHelpView(helpView, isPartialUpdate);
    }
  }

  public String getUpdateIntentName() {
    return getUpdateIntentName(this.mVersion);
  }

  public static String getUpdateIntentName(int version) {
    switch (version) {
    }
    return CocktailBarManager.ACTION_COCKTAIL_UPDATE;
  }

  public String dump() {
    String dumpResult =
        "[CocktailId:"
            + this.mCocktailId
            + " uid:"
            + this.mUid
            + " version:"
            + this.mVersion
            + " state:"
            + this.mState;
    if (this.mBroadcast != null) {
      dumpResult = dumpResult + " has broadcast";
    }
    CocktailInfo cocktailInfo = this.mCocktailInfo;
    if (cocktailInfo != null) {
      String cocktailInfoDump = cocktailInfo.dump();
      dumpResult = dumpResult + " " + cocktailInfoDump;
    }
    return dumpResult + " ]";
  }

  @Override // android.os.Parcelable
  public void writeToParcel(Parcel out, int flags) {
    out.writeInt(this.mCocktailId);
    out.writeInt(this.mUid);
    out.writeInt(this.mVersion);
    out.writeInt(this.mState);
    out.writeParcelable(this.mBroadcast, flags);
    out.writeParcelable(this.mProviderInfo, flags);
    out.writeParcelable(this.mCocktailInfo, flags);
    if (this.mIsPackageUpdated) {
      out.writeByte((byte) 1);
    } else {
      out.writeByte((byte) 0);
    }
    if (this.mIsPackageSuspended) {
      out.writeByte((byte) 1);
    } else {
      out.writeByte((byte) 0);
    }
  }

  public void readFromParcel(Parcel in) {
    this.mCocktailId = in.readInt();
    this.mUid = in.readInt();
    this.mVersion = in.readInt();
    this.mState = in.readInt();
    this.mBroadcast = (PendingIntent) in.readParcelable(PendingIntent.class.getClassLoader());
    this.mProviderInfo =
        (CocktailProviderInfo) in.readParcelable(ComponentName.class.getClassLoader());
    this.mCocktailInfo = (CocktailInfo) in.readParcelable(CocktailInfo.class.getClassLoader());
    this.mIsPackageUpdated = in.readByte() == 1;
    this.mIsPackageSuspended = in.readByte() == 1;
  }

  @Override // android.os.Parcelable
  public int describeContents() {
    return 0;
  }
}
