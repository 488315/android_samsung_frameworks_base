package android.os;

import android.os.Parcelable;

/* loaded from: classes3.dex */
public final class ParcelableException extends RuntimeException implements Parcelable {
    public static final Parcelable.Creator<ParcelableException> CREATOR = new Parcelable.Creator<ParcelableException>() { // from class: android.os.ParcelableException.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ParcelableException createFromParcel(Parcel parcel) {
            return new ParcelableException(ParcelableException.readFromParcel(parcel));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ParcelableException[] newArray(int i) {
            return new ParcelableException[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ParcelableException(Throwable th) {
        super(th);
    }

    public <T extends Throwable> void maybeRethrow(Class<T> cls) throws Throwable {
        if (cls.isAssignableFrom(getCause().getClass())) {
            throw getCause();
        }
    }

    public static Throwable readFromParcel(Parcel parcel) throws ClassNotFoundException {
        String string = parcel.readString();
        String string2 = parcel.readString();
        try {
            Class<?> cls = Class.forName(string, true, Parcelable.class.getClassLoader());
            if (Throwable.class.isAssignableFrom(cls)) {
                return (Throwable) cls.getConstructor(String.class).newInstance(string2);
            }
        } catch (ReflectiveOperationException unused) {
        }
        return new RuntimeException(string + ": " + string2);
    }

    public static void writeToParcel(Parcel parcel, Throwable th) {
        parcel.writeString(th.getClass().getName());
        parcel.writeString(th.getMessage());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        writeToParcel(parcel, getCause());
    }
}
