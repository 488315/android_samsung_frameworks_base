package com.android.wm.shell.desktopmode;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class DisplayDeskState implements Parcelable {
    public static final Parcelable.Creator<DisplayDeskState> CREATOR = new Parcelable.Creator() { // from class: com.android.wm.shell.desktopmode.DisplayDeskState.1
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            DisplayDeskState displayDeskState = new DisplayDeskState();
            int iDataPosition = parcel.dataPosition();
            int i = parcel.readInt();
            try {
                if (i < 4) {
                    throw new BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - iDataPosition < i) {
                    displayDeskState.displayId = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        displayDeskState.activeDeskId = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            displayDeskState.deskIds = parcel.createIntArray();
                            if (iDataPosition > Integer.MAX_VALUE - i) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (iDataPosition > Integer.MAX_VALUE - i) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (iDataPosition > Integer.MAX_VALUE - i) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (iDataPosition > Integer.MAX_VALUE - i) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(iDataPosition + i);
                return displayDeskState;
            } catch (Throwable th) {
                if (iDataPosition > Integer.MAX_VALUE - i) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(iDataPosition + i);
                throw th;
            }
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new DisplayDeskState[i];
        }
    };
    public int[] deskIds;
    public int displayId = 0;
    public int activeDeskId = 0;

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.displayId);
        parcel.writeInt(this.activeDeskId);
        parcel.writeIntArray(this.deskIds);
        int iDataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(iDataPosition);
        parcel.writeInt(iDataPosition2 - iDataPosition);
        parcel.setDataPosition(iDataPosition2);
    }
}
