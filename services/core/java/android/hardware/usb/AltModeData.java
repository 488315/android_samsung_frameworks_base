package android.hardware.usb;

import android.companion.virtualcamera.SupportedStreamConfiguration$$ExternalSyntheticOutline0;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class AltModeData implements Parcelable {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1(0);
    public int _tag;
    public Object _value;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: android.hardware.usb.AltModeData$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        public final /* synthetic */ int $r8$classId;

        public /* synthetic */ AnonymousClass1(int i) {
            this.$r8$classId = i;
        }

        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            switch (this.$r8$classId) {
                case 0:
                    AltModeData altModeData = new AltModeData();
                    int readInt = parcel.readInt();
                    if (readInt != 0) {
                        throw new IllegalArgumentException(
                                VibrationParam$1$$ExternalSyntheticOutline0.m(
                                        readInt, "union: unknown tag: "));
                    }
                    DisplayPortAltModeData displayPortAltModeData =
                            (DisplayPortAltModeData)
                                    parcel.readTypedObject(DisplayPortAltModeData.CREATOR);
                    altModeData._tag = readInt;
                    altModeData._value = displayPortAltModeData;
                    return altModeData;
                default:
                    DisplayPortAltModeData displayPortAltModeData2 = new DisplayPortAltModeData();
                    displayPortAltModeData2.partnerSinkStatus = 0;
                    displayPortAltModeData2.cableStatus = 0;
                    displayPortAltModeData2.pinAssignment = 0;
                    displayPortAltModeData2.hpd = false;
                    displayPortAltModeData2.linkTrainingStatus = 0;
                    int dataPosition = parcel.dataPosition();
                    int readInt2 = parcel.readInt();
                    try {
                        if (readInt2 < 4) {
                            throw new BadParcelableException("Parcelable too small");
                        }
                        if (parcel.dataPosition() - dataPosition < readInt2) {
                            displayPortAltModeData2.partnerSinkStatus = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt2) {
                                displayPortAltModeData2.cableStatus = parcel.readInt();
                                if (parcel.dataPosition() - dataPosition < readInt2) {
                                    displayPortAltModeData2.pinAssignment = parcel.readInt();
                                    if (parcel.dataPosition() - dataPosition < readInt2) {
                                        displayPortAltModeData2.hpd = parcel.readBoolean();
                                        if (parcel.dataPosition() - dataPosition < readInt2) {
                                            displayPortAltModeData2.linkTrainingStatus =
                                                    parcel.readInt();
                                            if (dataPosition > Integer.MAX_VALUE - readInt2) {
                                                throw new BadParcelableException(
                                                        "Overflow in the size of parcelable");
                                            }
                                        } else if (dataPosition > Integer.MAX_VALUE - readInt2) {
                                            throw new BadParcelableException(
                                                    "Overflow in the size of parcelable");
                                        }
                                    } else if (dataPosition > Integer.MAX_VALUE - readInt2) {
                                        throw new BadParcelableException(
                                                "Overflow in the size of parcelable");
                                    }
                                } else if (dataPosition > Integer.MAX_VALUE - readInt2) {
                                    throw new BadParcelableException(
                                            "Overflow in the size of parcelable");
                                }
                            } else if (dataPosition > Integer.MAX_VALUE - readInt2) {
                                throw new BadParcelableException(
                                        "Overflow in the size of parcelable");
                            }
                        } else if (dataPosition > Integer.MAX_VALUE - readInt2) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                        parcel.setDataPosition(dataPosition + readInt2);
                        return displayPortAltModeData2;
                    } catch (Throwable th) {
                        if (dataPosition > Integer.MAX_VALUE - readInt2) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                        parcel.setDataPosition(dataPosition + readInt2);
                        throw th;
                    }
            }
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            switch (this.$r8$classId) {
                case 0:
                    return new AltModeData[i];
                default:
                    return new DisplayPortAltModeData[i];
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DisplayPortAltModeData implements Parcelable {
        public static final Parcelable.Creator CREATOR = new AnonymousClass1(1);
        public int cableStatus;
        public boolean hpd;
        public int linkTrainingStatus;
        public int partnerSinkStatus;
        public int pinAssignment;

        @Override // android.os.Parcelable
        public final int describeContents() {
            return 0;
        }

        public final int getStability() {
            return 1;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            int dataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeInt(this.partnerSinkStatus);
            parcel.writeInt(this.cableStatus);
            parcel.writeInt(this.pinAssignment);
            parcel.writeBoolean(this.hpd);
            int m =
                    SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(
                            parcel, this.linkTrainingStatus, dataPosition);
            SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(m, dataPosition, parcel, m);
        }
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        if (this._tag != 0) {
            return 0;
        }
        getDisplayPortAltModeData();
        return 0;
    }

    public final DisplayPortAltModeData getDisplayPortAltModeData() {
        if (this._tag == 0) {
            return (DisplayPortAltModeData) this._value;
        }
        int i = this._tag;
        if (i == 0) {
            throw new IllegalStateException(
                    "bad access: displayPortAltModeData, displayPortAltModeData is available.");
        }
        throw new IllegalStateException(
                VibrationParam$1$$ExternalSyntheticOutline0.m(i, "unknown field: "));
    }

    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        if (this._tag != 0) {
            return;
        }
        parcel.writeTypedObject(getDisplayPortAltModeData(), i);
    }
}
