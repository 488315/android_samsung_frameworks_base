package android.hardware.radio.data;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class RouteSelectionDescriptor implements Parcelable {
    public static final Parcelable.Creator<RouteSelectionDescriptor> CREATOR = new Parcelable.Creator<RouteSelectionDescriptor>() { // from class: android.hardware.radio.data.RouteSelectionDescriptor.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RouteSelectionDescriptor createFromParcel(Parcel parcel) {
            RouteSelectionDescriptor routeSelectionDescriptor = new RouteSelectionDescriptor();
            routeSelectionDescriptor.readFromParcel(parcel);
            return routeSelectionDescriptor;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RouteSelectionDescriptor[] newArray(int i) {
            return new RouteSelectionDescriptor[i];
        }
    };
    public static final byte SSC_MODE_1 = 1;
    public static final byte SSC_MODE_2 = 2;
    public static final byte SSC_MODE_3 = 3;
    public static final byte SSC_MODE_UNKNOWN = -1;
    public String[] dnn;
    public SliceInfo[] sliceInfo;
    public byte precedence = 0;
    public int sessionType = 0;
    public byte sscMode = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeByte(this.precedence);
        parcel.writeInt(this.sessionType);
        parcel.writeByte(this.sscMode);
        parcel.writeTypedArray(this.sliceInfo, i);
        parcel.writeStringArray(this.dnn);
        int iDataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(iDataPosition);
        parcel.writeInt(iDataPosition2 - iDataPosition);
        parcel.setDataPosition(iDataPosition2);
    }

    public final void readFromParcel(Parcel parcel) {
        int iDataPosition = parcel.dataPosition();
        int i = parcel.readInt();
        try {
            if (i < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - iDataPosition < i) {
                this.precedence = parcel.readByte();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.sessionType = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.sscMode = parcel.readByte();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.sliceInfo = (SliceInfo[]) parcel.createTypedArray(SliceInfo.CREATOR);
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.dnn = parcel.createStringArray();
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
                } else if (iDataPosition > Integer.MAX_VALUE - i) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
            } else if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
        } catch (Throwable th) {
            if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
            throw th;
        }
    }

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        stringJoiner.add("precedence: " + ((int) this.precedence));
        stringJoiner.add("sessionType: " + PdpProtocolType$$.toString(this.sessionType));
        stringJoiner.add("sscMode: " + ((int) this.sscMode));
        stringJoiner.add("sliceInfo: " + Arrays.toString(this.sliceInfo));
        stringJoiner.add("dnn: " + Arrays.toString(this.dnn));
        return "RouteSelectionDescriptor" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.sliceInfo);
    }

    private int describeContents(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof Object[]) {
            int iDescribeContents = 0;
            for (Object obj2 : (Object[]) obj) {
                iDescribeContents |= describeContents(obj2);
            }
            return iDescribeContents;
        }
        if (obj instanceof Parcelable) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
