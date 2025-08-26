package android.hardware.cas;

import android.hardware.common.NativeHandle;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public final class DestinationBuffer implements Parcelable {
    public static final Parcelable.Creator<DestinationBuffer> CREATOR = new Parcelable.Creator<DestinationBuffer>() { // from class: android.hardware.cas.DestinationBuffer.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DestinationBuffer createFromParcel(Parcel parcel) {
            return new DestinationBuffer(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DestinationBuffer[] newArray(int i) {
            return new DestinationBuffer[i];
        }
    };
    public static final int nonsecureMemory = 0;
    public static final int secureMemory = 1;
    private int _tag;
    private Object _value;

    public @interface Tag {
        public static final int nonsecureMemory = 0;
        public static final int secureMemory = 1;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    public DestinationBuffer() {
        this._tag = 0;
        this._value = null;
    }

    private DestinationBuffer(Parcel parcel) {
        readFromParcel(parcel);
    }

    private DestinationBuffer(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }

    public int getTag() {
        return this._tag;
    }

    public static DestinationBuffer nonsecureMemory(SharedBuffer sharedBuffer) {
        return new DestinationBuffer(0, sharedBuffer);
    }

    public SharedBuffer getNonsecureMemory() {
        _assertTag(0);
        return (SharedBuffer) this._value;
    }

    public void setNonsecureMemory(SharedBuffer sharedBuffer) {
        _set(0, sharedBuffer);
    }

    public static DestinationBuffer secureMemory(NativeHandle nativeHandle) {
        return new DestinationBuffer(1, nativeHandle);
    }

    public NativeHandle getSecureMemory() {
        _assertTag(1);
        return (NativeHandle) this._value;
    }

    public void setSecureMemory(NativeHandle nativeHandle) {
        _set(1, nativeHandle);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        int i2 = this._tag;
        if (i2 == 0) {
            parcel.writeTypedObject(getNonsecureMemory(), i);
        } else {
            if (i2 != 1) {
                return;
            }
            parcel.writeTypedObject(getSecureMemory(), i);
        }
    }

    public void readFromParcel(Parcel parcel) {
        int i = parcel.readInt();
        if (i == 0) {
            _set(i, (SharedBuffer) parcel.readTypedObject(SharedBuffer.CREATOR));
        } else if (i == 1) {
            _set(i, (NativeHandle) parcel.readTypedObject(NativeHandle.CREATOR));
        } else {
            throw new IllegalArgumentException("union: unknown tag: " + i);
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        int tag = getTag();
        if (tag == 0) {
            return describeContents(getNonsecureMemory());
        }
        if (tag != 1) {
            return 0;
        }
        return describeContents(getSecureMemory());
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
            return "nonsecureMemory";
        }
        if (i == 1) {
            return "secureMemory";
        }
        throw new IllegalStateException("unknown field: " + i);
    }

    private void _set(int i, Object obj) {
        this._tag = i;
        this._value = obj;
    }
}
