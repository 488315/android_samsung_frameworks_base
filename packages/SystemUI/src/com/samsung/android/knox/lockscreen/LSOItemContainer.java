package com.samsung.android.knox.lockscreen;

import android.os.Parcel;
import android.os.ParcelFormatException;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class LSOItemContainer extends LSOItemData {
    public static final int LSO_FIELD_BG = 256;
    public static final int LSO_FIELD_ORIENTATION = 128;
    public String bgImagePath;
    public List<LSOItemData> childObj;
    public ORIENTATION orientation;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public enum ORIENTATION {
        VERTICAL,
        HORIZONTAL
    }

    public LSOItemContainer() {
        super((byte) 4);
        this.orientation = ORIENTATION.VERTICAL;
        this.childObj = new ArrayList();
    }

    public boolean addItem(LSOItemData lSOItemData) {
        return this.childObj.add(lSOItemData);
    }

    public String getBGImagePath() {
        return this.bgImagePath;
    }

    public LSOItemData getItem(int i) {
        return this.childObj.get(i);
    }

    public int getNumItems() {
        return this.childObj.size();
    }

    public ORIENTATION getOrientation() {
        return this.orientation;
    }

    @Override // com.samsung.android.knox.lockscreen.LSOItemData
    public void readFromParcel(Parcel parcel) {
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

    public void setBGImage(String str) {
        this.bgImagePath = str;
        updateFieldFlag(256);
    }

    public void setOrientation(ORIENTATION orientation) {
        this.orientation = orientation;
        updateFieldFlag(128);
    }

    @Override // com.samsung.android.knox.lockscreen.LSOItemData
    public String toString() {
        return toString(toString("ContainerView " + super.toString(), 256, "BG_IMAGE:" + this.bgImagePath), 128, "Orientation:".concat(this.orientation == ORIENTATION.VERTICAL ? "VERTICAL" : "HORIZONTAL"));
    }

    @Override // com.samsung.android.knox.lockscreen.LSOItemData, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
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
