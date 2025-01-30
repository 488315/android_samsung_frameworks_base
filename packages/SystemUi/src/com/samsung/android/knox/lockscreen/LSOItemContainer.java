package com.samsung.android.knox.lockscreen;

import android.os.Parcel;
import android.os.ParcelFormatException;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class LSOItemContainer extends LSOItemData {
    public static final int LSO_FIELD_BG = 256;
    public static final int LSO_FIELD_ORIENTATION = 128;
    public String bgImagePath;
    public List<LSOItemData> childObj;
    public ORIENTATION orientation;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum ORIENTATION {
        VERTICAL,
        HORIZONTAL
    }

    public LSOItemContainer() {
        super((byte) 4);
        this.orientation = ORIENTATION.VERTICAL;
        this.childObj = new ArrayList();
    }

    public final boolean addItem(LSOItemData lSOItemData) {
        return this.childObj.add(lSOItemData);
    }

    public final String getBGImagePath() {
        return this.bgImagePath;
    }

    public final LSOItemData getItem(int i) {
        return this.childObj.get(i);
    }

    public final int getNumItems() {
        return this.childObj.size();
    }

    public final ORIENTATION getOrientation() {
        return this.orientation;
    }

    @Override // com.samsung.android.knox.lockscreen.LSOItemData
    public final void readFromParcel(Parcel parcel) {
        super.readFromParcel(parcel);
        this.orientation = readByteFromParcel(parcel, 128) == 0 ? ORIENTATION.VERTICAL : ORIENTATION.HORIZONTAL;
        this.bgImagePath = readStringFromParcel(parcel, 256);
        int readInt = parcel.readInt();
        for (int i = 0; i < readInt; i++) {
            LSOItemData createFromParcel = LSOItemData.CREATOR.createFromParcel(parcel);
            if (createFromParcel == null) {
                throw new ParcelFormatException("Parcel format exception");
            }
            this.childObj.add(createFromParcel);
        }
    }

    public final void setBGImage(String str) {
        this.bgImagePath = str;
        updateFieldFlag(256);
    }

    public final void setOrientation(ORIENTATION orientation) {
        this.orientation = orientation;
        updateFieldFlag(128);
    }

    @Override // com.samsung.android.knox.lockscreen.LSOItemData
    public final String toString() {
        return toString(toString("ContainerView " + super.toString(), 256, "BG_IMAGE:" + this.bgImagePath), 128, "Orientation:".concat(this.orientation == ORIENTATION.VERTICAL ? "VERTICAL" : "HORIZONTAL"));
    }

    @Override // com.samsung.android.knox.lockscreen.LSOItemData, android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        writeToParcel(parcel, 128, (byte) (this.orientation == ORIENTATION.VERTICAL ? 0 : 1));
        writeToParcel(parcel, 256, this.bgImagePath);
        parcel.writeInt(this.childObj.size());
        for (int i2 = 0; i2 < this.childObj.size(); i2++) {
            this.childObj.get(i2).writeToParcel(parcel, i);
        }
    }

    public LSOItemContainer(Parcel parcel) {
        super((byte) 4);
        this.childObj = new ArrayList();
        readFromParcel(parcel);
    }
}
