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
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.id, i);
        parcel.writeInt(this.type);
        parcel.writeString(this.name);
        parcel.writeInt(this.version);
        parcel.writeString(this.tag);
        parcel.writeStringArray(this.requiredPermissions);
        parcel.writeTypedArray(this.services, i);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2 - dataPosition);
        parcel.setDataPosition(dataPosition2);
    }

    public final void readFromParcel(Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        try {
            if (readInt < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - dataPosition < readInt) {
                this.id = (EndpointId) parcel.readTypedObject(EndpointId.CREATOR);
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.type = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.name = parcel.readString();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.version = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.tag = parcel.readString();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.requiredPermissions = parcel.createStringArray();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.services = (Service[]) parcel.createTypedArray(Service.CREATOR);
                                        if (dataPosition > Integer.MAX_VALUE - readInt) {
                                            throw new BadParcelableException("Overflow in the size of parcelable");
                                        }
                                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                        throw new BadParcelableException("Overflow in the size of parcelable");
                                    }
                                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                    throw new BadParcelableException("Overflow in the size of parcelable");
                                }
                            } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
            } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
        } catch (Throwable th) {
            if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
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
            int i = 0;
            for (Object obj2 : (Object[]) obj) {
                i |= describeContents(obj2);
            }
            return i;
        }
        if (obj instanceof Parcelable) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
