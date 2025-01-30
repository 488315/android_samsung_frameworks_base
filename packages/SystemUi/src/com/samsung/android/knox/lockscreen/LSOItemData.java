package com.samsung.android.knox.lockscreen;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.util.Log;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class LSOItemData implements Parcelable {
    public static final Parcelable.Creator<LSOItemData> CREATOR = new Parcelable.Creator<LSOItemData>() { // from class: com.samsung.android.knox.lockscreen.LSOItemData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final LSOItemData[] newArray(int i) {
            return null;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final LSOItemData createFromParcel(Parcel parcel) {
            try {
                int dataPosition = parcel.dataPosition();
                byte readByte = parcel.readByte();
                parcel.setDataPosition(dataPosition);
                return LSOItemCreator.createItem(readByte, parcel);
            } catch (Exception e) {
                EmergencyButton$$ExternalSyntheticOutline0.m58m("Exception in createFromParcel: ", e, LSOItemData.TAG);
                return null;
            }
        }

        @Override // android.os.Parcelable.Creator
        public final LSOItemData[] newArray(int i) {
            return null;
        }
    };
    public static final float DEFAULT_FLOAT_VALUE = 0.0f;
    public static final int DEFAULT_INT_VALUE = -1;
    public static final int DEFAULT_WH_VALUE = -99;
    public static final int LSO_FIELD_ATTRIBUTES = 64;
    public static final int LSO_FIELD_BG_COLOR = 16;
    public static final int LSO_FIELD_GRAVITY = 32;
    public static final int LSO_FIELD_ITEMID = 1;
    public static final int LSO_FIELD_LAST = 128;
    public static final int LSO_FIELD_PARAM_HEIGHT = 4;
    public static final int LSO_FIELD_PARAM_WEIGHT = 8;
    public static final int LSO_FIELD_PARAM_WIDTH = 2;
    public static final String TAG = "LSO_LSOItemData";
    public LSOAttributeSet attrs;
    public int bg_color;
    public int gravity;
    public int height;
    public String itemId;
    public int modifiedFields;
    public ParcelFileDescriptor pfd;
    public byte type;
    public float weight;
    public int width;

    public LSOItemData(byte b) {
        this.modifiedFields = 0;
        this.type = b;
        this.attrs = new LSOAttributeSet();
        this.width = -99;
        this.height = -99;
        this.weight = 0.0f;
        this.bg_color = -1;
        this.gravity = -1;
    }

    public final void closeFileDescriptor() {
        ParcelFileDescriptor parcelFileDescriptor = this.pfd;
        if (parcelFileDescriptor != null && parcelFileDescriptor != null) {
            try {
                parcelFileDescriptor.close();
                this.pfd = null;
            } catch (IOException unused) {
            }
        }
        if (this instanceof LSOItemContainer) {
            LSOItemContainer lSOItemContainer = (LSOItemContainer) this;
            int numItems = lSOItemContainer.getNumItems();
            for (int i = 0; i < numItems; i++) {
                lSOItemContainer.getItem(i).closeFileDescriptor();
            }
        }
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final LSOAttributeSet getAttrs() {
        return new LSOAttributeSet(this.attrs);
    }

    public final int getBgColor() {
        return this.bg_color;
    }

    public final ParcelFileDescriptor getFileDescriptor() {
        return this.pfd;
    }

    public final int getGravity() {
        return this.gravity;
    }

    public final int getHeight() {
        return this.height;
    }

    public final String getId() {
        return this.itemId;
    }

    public final byte getType() {
        return this.type;
    }

    public final int[] getUpdatedFields() {
        int i = this.modifiedFields;
        if (i == 0) {
            return null;
        }
        int i2 = 0;
        while (i > 0) {
            i2++;
            i &= i - 1;
        }
        int[] iArr = new int[i2];
        int i3 = this.modifiedFields;
        int i4 = 0;
        for (int i5 = 0; i5 <= 31 && i3 > 0 && i4 < i2; i5++) {
            int i6 = 1 << i5;
            if ((i3 & i6) == i6) {
                iArr[i4] = i6;
                i3 &= ~i6;
                i4++;
            }
        }
        return iArr;
    }

    public final float getWeight() {
        return this.weight;
    }

    public final int getWidth() {
        return this.width;
    }

    public final boolean isFieldUpdated(int i) {
        return (this.modifiedFields & i) == i;
    }

    public final void openFileDescriptor() {
        if (this instanceof LSOItemContainer) {
            LSOItemContainer lSOItemContainer = (LSOItemContainer) this;
            openFileDescriptorInternal(lSOItemContainer.bgImagePath);
            int numItems = lSOItemContainer.getNumItems();
            for (int i = 0; i < numItems; i++) {
                lSOItemContainer.getItem(i).openFileDescriptor();
            }
            return;
        }
        if (this instanceof LSOItemImage) {
            openFileDescriptorInternal(((LSOItemImage) this).imagePath);
            return;
        }
        LSOAttributeSet attrs = getAttrs();
        if (attrs.containsKey(LSOAttrConst.ATTR_IMAGE_SRC)) {
            openFileDescriptorInternal(attrs.getAsString(LSOAttrConst.ATTR_IMAGE_SRC));
        }
    }

    public final void openFileDescriptorInternal(String str) {
        if (str != null) {
            try {
                this.pfd = ParcelFileDescriptor.open(new File(str), QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
            } catch (FileNotFoundException unused) {
                Log.w(TAG, "openFileDescriptorInternal() file not found - ".concat(str));
            } catch (Exception e) {
                Log.e(TAG, "openFileDescriptorInternal() error occurs - ".concat(str), e);
            }
        }
    }

    public final boolean readBoolFromParcel(Parcel parcel, int i) {
        return isFieldUpdated(i) && 1 == parcel.readByte();
    }

    public final byte readByteFromParcel(Parcel parcel, int i) {
        if (isFieldUpdated(i)) {
            return parcel.readByte();
        }
        return (byte) 0;
    }

    public final float readFloatFromParcel(Parcel parcel, int i) {
        if (isFieldUpdated(i)) {
            return parcel.readFloat();
        }
        return 0.0f;
    }

    public void readFromParcel(Parcel parcel) {
        this.type = parcel.readByte();
        this.modifiedFields = parcel.readInt();
        this.itemId = readStringFromParcel(parcel, 1);
        this.width = readIntFromParcel(parcel, 2, -99);
        this.height = readIntFromParcel(parcel, 4, -99);
        this.weight = readFloatFromParcel(parcel, 8, 0.0f);
        this.bg_color = readIntFromParcel(parcel, 16, -1);
        this.gravity = readIntFromParcel(parcel, 32, -1);
        if (isFieldUpdated(64)) {
            this.attrs = LSOAttributeSet.createFromParcel(parcel);
        }
        if (parcel.readInt() != 0) {
            this.pfd = (ParcelFileDescriptor) ParcelFileDescriptor.CREATOR.createFromParcel(parcel);
        } else {
            this.pfd = null;
        }
    }

    public final int readIntFromParcel(Parcel parcel, int i, int i2) {
        return isFieldUpdated(i) ? parcel.readInt() : i2;
    }

    public final String readStringFromParcel(Parcel parcel, int i) {
        if (isFieldUpdated(i)) {
            return parcel.readString();
        }
        return null;
    }

    public final void removeFieldFlag(int i) {
        this.modifiedFields = (~i) & this.modifiedFields;
    }

    public final void resetUpdatedFields() {
        this.modifiedFields = 0;
    }

    public final void setAttribute(String str, String str2) {
        this.attrs.put(str, str2);
        updateFieldFlag(64);
    }

    public final void setAttrs(LSOAttributeSet lSOAttributeSet) {
        if (lSOAttributeSet == null) {
            return;
        }
        this.attrs.clear();
        this.attrs.putAll(lSOAttributeSet);
        updateFieldFlag(64);
    }

    public final void setBgColor(int i) {
        this.bg_color = i;
        updateFieldFlag(16);
    }

    public final void setDimension(int i, int i2) {
        setWidth(i);
        setHeight(i2);
    }

    public final void setGravity(int i) {
        this.gravity = i;
        updateFieldFlag(32);
    }

    public final void setHeight(int i) {
        this.height = i;
        updateFieldFlag(4);
    }

    public final void setId(String str) {
        this.itemId = str;
        updateFieldFlag(1);
    }

    public final void setWeight(float f) {
        this.weight = f;
        updateFieldFlag(8);
    }

    public final void setWidth(int i) {
        this.width = i;
        updateFieldFlag(2);
    }

    public String toString() {
        return toString(toString(toString(toString(toString(toString(toString("", 1, "ItemId:" + this.itemId), 2, "Width:" + this.width), 4, "Height:" + this.height), 8, "Weight:" + this.weight), 16, "BG_Color:" + this.bg_color), 32, "Gravity:" + this.gravity), 64, "Attributes:" + this.attrs.toString());
    }

    public final void updateFieldFlag(int i) {
        this.modifiedFields = i | this.modifiedFields;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte(this.type);
        parcel.writeInt(this.modifiedFields);
        writeToParcel(parcel, 1, this.itemId);
        writeToParcel(parcel, 2, this.width);
        writeToParcel(parcel, 4, this.height);
        writeToParcel(parcel, 8, this.weight);
        writeToParcel(parcel, 16, this.bg_color);
        writeToParcel(parcel, 32, this.gravity);
        if (isFieldUpdated(64)) {
            this.attrs.writeToParcel(parcel, i);
        }
        if (this.pfd == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            this.pfd.writeToParcel(parcel, i);
        }
    }

    public final float readFloatFromParcel(Parcel parcel, int i, float f) {
        return isFieldUpdated(i) ? parcel.readFloat() : f;
    }

    public final void setAttribute(String str, Byte b) {
        this.attrs.put(str, b);
        updateFieldFlag(64);
    }

    public final void setDimension(int i, int i2, float f) {
        setWidth(i);
        setHeight(i2);
        setWeight(f);
    }

    public final void setAttribute(String str, Integer num) {
        this.attrs.put(str, num);
        updateFieldFlag(64);
    }

    public final void setAttribute(String str, Long l) {
        this.attrs.put(str, l);
        updateFieldFlag(64);
    }

    public final void setAttribute(String str, Float f) {
        this.attrs.put(str, f);
        updateFieldFlag(64);
    }

    public LSOItemData(byte b, Parcel parcel) {
        this.modifiedFields = 0;
        this.type = b;
        this.attrs = new LSOAttributeSet();
        readFromParcel(parcel);
    }

    public final String toString(String str, int i, String str2) {
        return isFieldUpdated(i) ? AbstractResolvableFuture$$ExternalSyntheticOutline0.m15m(str, str2, " ") : str;
    }

    public final void setAttribute(String str, Double d) {
        this.attrs.put(str, d);
        updateFieldFlag(64);
    }

    public final void setAttribute(String str, Boolean bool) {
        this.attrs.put(str, bool);
        updateFieldFlag(64);
    }

    public final void setAttribute(String str, byte[] bArr) {
        this.attrs.put(str, bArr);
        updateFieldFlag(64);
    }

    public final void writeToParcel(Parcel parcel, int i, boolean z) {
        if (isFieldUpdated(i)) {
            parcel.writeByte(z ? (byte) 1 : (byte) 0);
        }
    }

    public final void writeToParcel(Parcel parcel, int i, byte b) {
        if (isFieldUpdated(i)) {
            parcel.writeByte(b);
        }
    }

    public final void writeToParcel(Parcel parcel, int i, int i2) {
        if (isFieldUpdated(i)) {
            parcel.writeInt(i2);
        }
    }

    public final void writeToParcel(Parcel parcel, int i, float f) {
        if (isFieldUpdated(i)) {
            parcel.writeFloat(f);
        }
    }

    public final void writeToParcel(Parcel parcel, int i, String str) {
        if (isFieldUpdated(i)) {
            parcel.writeString(str);
        }
    }
}
