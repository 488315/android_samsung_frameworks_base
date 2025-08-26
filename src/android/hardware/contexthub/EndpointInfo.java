package android.hardware.contexthub;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class EndpointInfo implements Parcelable {
    public static final Parcelable.Creator<EndpointInfo> CREATOR = new Parcelable.Creator<EndpointInfo>() { // from class: android.hardware.contexthub.EndpointInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EndpointInfo createFromParcel(Parcel parcel) {
            EndpointInfo endpointInfo = new EndpointInfo();
            endpointInfo.readFromParcel(parcel);
            return endpointInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EndpointInfo[] newArray(int i) {
            return new EndpointInfo[i];
        }
    };
    public EndpointId id;
    public String name;
    public String[] requiredPermissions;
    public Service[] services;
    public String tag;
    public int type;
    public int version = 0;

    public @interface EndpointType {
        public static final int APP = 2;
        public static final int FRAMEWORK = 1;
        public static final int GENERIC = 5;
        public static final int NANOAPP = 4;
        public static final int NATIVE = 3;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.id, i);
        parcel.writeInt(this.type);
        parcel.writeString(this.name);
        parcel.writeInt(this.version);
        parcel.writeString(this.tag);
        parcel.writeStringArray(this.requiredPermissions);
        parcel.writeTypedArray(this.services, i);
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
                this.id = (EndpointId) parcel.readTypedObject(EndpointId.CREATOR);
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.type = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.name = parcel.readString();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.version = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.tag = parcel.readString();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.requiredPermissions = parcel.createStringArray();
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.services = (Service[]) parcel.createTypedArray(Service.CREATOR);
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

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.services) | describeContents(this.id);
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
