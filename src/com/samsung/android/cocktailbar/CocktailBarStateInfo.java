package com.samsung.android.cocktailbar;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public class CocktailBarStateInfo implements Parcelable, Cloneable {
    public static final Parcelable.Creator<CocktailBarStateInfo> CREATOR = new Parcelable.Creator<CocktailBarStateInfo>() { // from class: com.samsung.android.cocktailbar.CocktailBarStateInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CocktailBarStateInfo createFromParcel(Parcel parcel) {
            return new CocktailBarStateInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CocktailBarStateInfo[] newArray(int i) {
            return new CocktailBarStateInfo[i];
        }
    };
    public static final int FLAG_CHANGE_ACTIVATE = 64;
    public static final int FLAG_CHANGE_LOCK_STATE = 8;
    public static final int FLAG_CHANGE_MODE = 16;
    public static final int FLAG_CHANGE_POSITION = 4;
    public static final int FLAG_CHANGE_SHOW_TIMEOUT = 32;
    public static final int FLAG_CHANGE_VISIBILITY = 1;
    public static final int FLAG_CHANGE_WINDOW_TYPE = 128;
    public static final int LOCK_STATE_HIDE = 2;
    public static final int LOCK_STATE_NONE = 0;
    public static final int LOCK_STATE_RESTRICT = 4;
    public static final int LOCK_STATE_SHOW = 1;

    @Deprecated
    public static final int MODE_IMMERSIVE = 2;

    @Deprecated
    public static final int MODE_MULTITASKING = 1;

    @Deprecated
    public static final int MODE_UNKNOWN = 0;
    public static final int POSITION_BOTTOM = 4;
    public static final int POSITION_LEFT = 1;
    public static final int POSITION_RIGHT = 2;
    public static final int POSITION_TOP = 3;
    public static final int POSITION_UNKNOWN = 0;
    public static final int STATE_INVISIBLE = 2;
    public static final int STATE_VISIBLE = 1;
    public static final int WINDOW_TYPE_FULLSCREEN = 2;
    public static final int WINDOW_TYPE_MINIMIZE = 1;
    public static final int WINDOW_TYPE_UNKNOWN = 0;
    public boolean activate;
    public int changeFlag;
    public int lockState;
    public int mode;
    public int position;
    public int showTimeout;
    public int visibility;
    public int windowType;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CocktailBarStateInfo(int i) {
        this.position = 0;
        this.lockState = 0;
        this.mode = 0;
        this.showTimeout = -1;
        this.activate = true;
        this.windowType = 0;
        this.changeFlag = 0;
        this.visibility = i;
    }

    public CocktailBarStateInfo(CocktailBarStateInfo cocktailBarStateInfo) {
        this.position = 0;
        this.lockState = 0;
        this.mode = 0;
        this.showTimeout = -1;
        this.activate = true;
        this.windowType = 0;
        this.changeFlag = 0;
        this.visibility = cocktailBarStateInfo.visibility;
        this.position = cocktailBarStateInfo.position;
        this.lockState = cocktailBarStateInfo.lockState;
        this.showTimeout = cocktailBarStateInfo.showTimeout;
        this.activate = cocktailBarStateInfo.activate;
        this.windowType = cocktailBarStateInfo.windowType;
    }

    public CocktailBarStateInfo(Parcel parcel) {
        this.position = 0;
        this.lockState = 0;
        this.mode = 0;
        this.showTimeout = -1;
        this.activate = true;
        this.windowType = 0;
        this.changeFlag = 0;
        this.visibility = parcel.readInt();
        this.position = parcel.readInt();
        this.lockState = parcel.readInt();
        this.mode = parcel.readInt();
        this.showTimeout = parcel.readInt();
        this.activate = parcel.readInt() == 1;
        this.windowType = parcel.readInt();
        this.changeFlag = parcel.readInt();
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public CocktailBarStateInfo m9024clone() {
        Parcel obtain = Parcel.obtain();
        writeToParcel(obtain, 0);
        obtain.setDataPosition(0);
        CocktailBarStateInfo cocktailBarStateInfo = new CocktailBarStateInfo(obtain);
        obtain.recycle();
        return cocktailBarStateInfo;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.visibility);
        parcel.writeInt(this.position);
        parcel.writeInt(this.lockState);
        parcel.writeInt(this.mode);
        parcel.writeInt(this.showTimeout);
        parcel.writeInt(this.activate ? 1 : 0);
        parcel.writeInt(this.windowType);
        parcel.writeInt(this.changeFlag);
    }
}
