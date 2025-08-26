package android.os;

import android.os.Parcelable;

/* loaded from: classes3.dex */
public final class Service implements Parcelable {
    public static final Parcelable.Creator<Service> CREATOR = new Parcelable.Creator<Service>() { // from class: android.os.Service.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Service createFromParcel(Parcel parcel) {
            return new Service(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Service[] newArray(int i) {
            return new Service[i];
        }
    };
    public static final int accessor = 1;
    public static final int serviceWithMetadata = 0;
    private int _tag;
    private Object _value;

    public @interface Tag {
        public static final int accessor = 1;
        public static final int serviceWithMetadata = 0;
    }

    public Service() {
        this._tag = 0;
        this._value = null;
    }

    private Service(Parcel parcel) {
        readFromParcel(parcel);
    }

    private Service(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static Service serviceWithMetadata(ServiceWithMetadata serviceWithMetadata2) {
        return new Service(0, serviceWithMetadata2);
    }

    public ServiceWithMetadata getServiceWithMetadata() {
        _assertTag(0);
        return (ServiceWithMetadata) this._value;
    }

    public void setServiceWithMetadata(ServiceWithMetadata serviceWithMetadata2) {
        _set(0, serviceWithMetadata2);
    }

    public static Service accessor(IBinder iBinder) {
        return new Service(1, iBinder);
    }

    public IBinder getAccessor() {
        _assertTag(1);
        return (IBinder) this._value;
    }

    public void setAccessor(IBinder iBinder) {
        _set(1, iBinder);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        int i2 = this._tag;
        if (i2 == 0) {
            parcel.writeTypedObject(getServiceWithMetadata(), i);
        } else {
            if (i2 != 1) {
                return;
            }
            parcel.writeStrongBinder(getAccessor());
        }
    }

    public void readFromParcel(Parcel parcel) {
        int i = parcel.readInt();
        if (i == 0) {
            _set(i, (ServiceWithMetadata) parcel.readTypedObject(ServiceWithMetadata.CREATOR));
        } else if (i == 1) {
            _set(i, parcel.readStrongBinder());
        } else {
            throw new IllegalArgumentException("union: unknown tag: " + i);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        if (getTag() != 0) {
            return 0;
        }
        return describeContents(getServiceWithMetadata());
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }

    private void _assertTag(int i) {
        if (getTag() == i) {
            return;
        }
        throw new IllegalStateException("bad access: " + _tagString(i) + ", " + _tagString(getTag()) + " is available.");
    }

    private String _tagString(int i) {
        if (i == 0) {
            return "serviceWithMetadata";
        }
        if (i == 1) {
            return "accessor";
        }
        throw new IllegalStateException("unknown field: " + i);
    }

    private void _set(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
