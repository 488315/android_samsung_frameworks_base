package com.samsung.android.knox.custom;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.io.ByteArrayOutputStream;

/* loaded from: classes4.dex */
public class ShortcutItem implements Parcelable {
    public static final Parcelable.Creator<ShortcutItem> CREATOR = new Parcelable.Creator<ShortcutItem>() { // from class: com.samsung.android.knox.custom.ShortcutItem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ShortcutItem createFromParcel(Parcel parcel) {
            return new ShortcutItem(parcel, 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ShortcutItem[] newArray(int i) {
            return new ShortcutItem[i];
        }
    };
    public final String TAG;
    public int mCellX;
    public final String mCellX_KEY;
    public int mCellY;
    public final String mCellY_KEY;
    public int mColour;
    public final String mColour_KEY;
    public int mFolderPosition;
    public final String mFolderPosition_KEY;
    public BitmapDrawable mIcon;
    public final String mIcon_KEY;
    public Intent mIntent;
    public final String mIntent_KEY;
    public int mMoreItems;
    public final String mMoreItems_KEY;
    public String mName;
    public final String mName_KEY;
    public String mParent;
    public final String mParent_KEY;
    public int mShortcutType;
    public final String mShortcutType_KEY;

    public /* synthetic */ ShortcutItem(Parcel parcel, int i) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int getCellX() {
        return this.mCellX;
    }

    public int getCellY() {
        return this.mCellY;
    }

    public int getColour() {
        return this.mColour;
    }

    public int getFolderPosition() {
        return this.mFolderPosition;
    }

    public BitmapDrawable getIcon() {
        return this.mIcon;
    }

    public byte[] getIconArray() {
        BitmapDrawable bitmapDrawable = this.mIcon;
        if (bitmapDrawable != null) {
            Bitmap bitmap = bitmapDrawable.getBitmap();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            if (bitmap != null) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, byteArrayOutputStream);
                return byteArrayOutputStream.toByteArray();
            }
        }
        return null;
    }

    public Intent getIntent() {
        return this.mIntent;
    }

    public int getMoreItems() {
        return this.mMoreItems;
    }

    public String getName() {
        return this.mName;
    }

    public String getParent() {
        return this.mParent;
    }

    public int getShortcutType() {
        return this.mShortcutType;
    }

    public String toString() {
        return "descr:" + describeContents() + " shortcutType:" + this.mShortcutType + " name:" + this.mName + " parent:" + this.mParent + " intent:" + this.mIntent;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("intent", this.mIntent);
        parcel.writeBundle(bundle);
        parcel.writeInt(this.mShortcutType);
        parcel.writeString(this.mName);
        parcel.writeString(this.mParent);
        parcel.writeInt(this.mFolderPosition);
        parcel.writeInt(this.mCellX);
        parcel.writeInt(this.mCellY);
        parcel.writeInt(this.mColour);
        parcel.writeInt(this.mMoreItems);
        byte[] iconArray = getIconArray();
        if (iconArray == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(iconArray.length);
            parcel.writeByteArray(iconArray);
        }
    }

    public ShortcutItem(int i, String str, Intent intent, String str2, int i2, int i3, int i4, int i5, BitmapDrawable bitmapDrawable, int i6) {
        this.TAG = "ShortcutItem";
        this.mShortcutType_KEY = "SHORTCUT";
        this.mName_KEY = "NAME";
        this.mFolderPosition_KEY = "FOLDER_POS";
        this.mIntent_KEY = "INTENT";
        this.mParent_KEY = "PARENT";
        this.mCellX_KEY = "CELLX";
        this.mCellY_KEY = "CELLY";
        this.mColour_KEY = "COLOUR";
        this.mIcon_KEY = "ICON";
        this.mMoreItems_KEY = "MORE";
        this.mShortcutType = i;
        this.mName = str;
        this.mIntent = intent;
        this.mParent = str2;
        this.mFolderPosition = i2;
        this.mCellX = i3;
        this.mCellY = i4;
        this.mColour = i5;
        this.mIcon = bitmapDrawable;
        this.mMoreItems = i6;
    }

    public ShortcutItem(int i, String str, Intent intent, String str2, int i2, int i3, int i4) {
        this.TAG = "ShortcutItem";
        this.mShortcutType_KEY = "SHORTCUT";
        this.mName_KEY = "NAME";
        this.mFolderPosition = 0;
        this.mFolderPosition_KEY = "FOLDER_POS";
        this.mIntent_KEY = "INTENT";
        this.mParent_KEY = "PARENT";
        this.mCellX_KEY = "CELLX";
        this.mCellY_KEY = "CELLY";
        this.mColour = 0;
        this.mColour_KEY = "COLOUR";
        this.mIcon = null;
        this.mIcon_KEY = "ICON";
        this.mMoreItems_KEY = "MORE";
        this.mShortcutType = i;
        this.mName = str;
        this.mIntent = intent;
        this.mParent = str2;
        this.mCellX = i2;
        this.mCellY = i3;
        this.mMoreItems = i4;
    }

    public ShortcutItem(int i, String str, Intent intent, String str2, int i2, int i3) {
        this.TAG = "ShortcutItem";
        this.mShortcutType_KEY = "SHORTCUT";
        this.mName_KEY = "NAME";
        this.mFolderPosition_KEY = "FOLDER_POS";
        this.mIntent_KEY = "INTENT";
        this.mParent_KEY = "PARENT";
        this.mCellX = 0;
        this.mCellX_KEY = "CELLX";
        this.mCellY = 0;
        this.mCellY_KEY = "CELLY";
        this.mColour = 0;
        this.mColour_KEY = "COLOUR";
        this.mIcon = null;
        this.mIcon_KEY = "ICON";
        this.mMoreItems_KEY = "MORE";
        this.mShortcutType = i;
        this.mName = str;
        this.mIntent = intent;
        this.mParent = str2;
        this.mFolderPosition = i2;
        this.mMoreItems = i3;
    }

    public ShortcutItem(int i, String str, Intent intent, String str2, int i2, int i3, BitmapDrawable bitmapDrawable, int i4) {
        this.TAG = "ShortcutItem";
        this.mShortcutType_KEY = "SHORTCUT";
        this.mName_KEY = "NAME";
        this.mFolderPosition = 0;
        this.mFolderPosition_KEY = "FOLDER_POS";
        this.mIntent_KEY = "INTENT";
        this.mParent_KEY = "PARENT";
        this.mCellX_KEY = "CELLX";
        this.mCellY_KEY = "CELLY";
        this.mColour = 0;
        this.mColour_KEY = "COLOUR";
        this.mIcon_KEY = "ICON";
        this.mMoreItems_KEY = "MORE";
        this.mShortcutType = i;
        this.mName = str;
        this.mIntent = intent;
        this.mParent = str2;
        this.mCellX = i2;
        this.mCellY = i3;
        this.mIcon = bitmapDrawable;
        this.mMoreItems = i4;
    }

    public ShortcutItem(int i, String str, Intent intent, String str2, int i2, BitmapDrawable bitmapDrawable, int i3) {
        this.TAG = "ShortcutItem";
        this.mShortcutType_KEY = "SHORTCUT";
        this.mName_KEY = "NAME";
        this.mFolderPosition_KEY = "FOLDER_POS";
        this.mIntent_KEY = "INTENT";
        this.mParent_KEY = "PARENT";
        this.mCellX = 0;
        this.mCellX_KEY = "CELLX";
        this.mCellY = 0;
        this.mCellY_KEY = "CELLY";
        this.mColour = 0;
        this.mColour_KEY = "COLOUR";
        this.mIcon_KEY = "ICON";
        this.mMoreItems_KEY = "MORE";
        this.mShortcutType = i;
        this.mName = str;
        this.mIntent = intent;
        this.mParent = str2;
        this.mFolderPosition = i2;
        this.mIcon = bitmapDrawable;
        this.mMoreItems = i3;
    }

    public ShortcutItem(int i, String str, String str2, int i2, int i3, int i4, int i5) {
        this.TAG = "ShortcutItem";
        this.mShortcutType_KEY = "SHORTCUT";
        this.mName_KEY = "NAME";
        this.mFolderPosition = 0;
        this.mFolderPosition_KEY = "FOLDER_POS";
        this.mIntent_KEY = "INTENT";
        this.mParent_KEY = "PARENT";
        this.mCellX_KEY = "CELLX";
        this.mCellY_KEY = "CELLY";
        this.mColour_KEY = "COLOUR";
        this.mIcon = null;
        this.mIcon_KEY = "ICON";
        this.mMoreItems_KEY = "MORE";
        this.mShortcutType = i;
        this.mName = str;
        this.mParent = str2;
        this.mCellX = i2;
        this.mCellY = i3;
        this.mColour = i4;
        this.mMoreItems = i5;
    }

    private ShortcutItem(Parcel parcel) {
        this.TAG = "ShortcutItem";
        this.mShortcutType = 1;
        this.mShortcutType_KEY = "SHORTCUT";
        this.mName_KEY = "NAME";
        this.mFolderPosition = 0;
        this.mFolderPosition_KEY = "FOLDER_POS";
        this.mIntent_KEY = "INTENT";
        this.mParent_KEY = "PARENT";
        this.mCellX = 0;
        this.mCellX_KEY = "CELLX";
        this.mCellY = 0;
        this.mCellY_KEY = "CELLY";
        this.mColour = 0;
        this.mColour_KEY = "COLOUR";
        this.mIcon = null;
        this.mIcon_KEY = "ICON";
        this.mMoreItems = 1;
        this.mMoreItems_KEY = "MORE";
        this.mIntent = (Intent) parcel.readBundle().getParcelable("intent");
        this.mShortcutType = parcel.readInt();
        this.mName = parcel.readString();
        this.mParent = parcel.readString();
        this.mFolderPosition = parcel.readInt();
        this.mCellX = parcel.readInt();
        this.mCellY = parcel.readInt();
        this.mColour = parcel.readInt();
        this.mMoreItems = parcel.readInt();
        int i = parcel.readInt();
        if (i > 0) {
            byte[] bArr = new byte[i];
            parcel.readByteArray(bArr);
            this.mIcon = new BitmapDrawable(BitmapFactory.decodeByteArray(bArr, 0, i));
        }
    }
}
