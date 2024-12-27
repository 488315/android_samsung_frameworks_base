package android.app.appfunctions;

import android.app.appsearch.GenericDocument;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.MathUtils;

import java.util.Objects;

/* loaded from: classes.dex */
public final class GenericDocumentWrapper implements Parcelable {
    public static final Parcelable.Creator<GenericDocumentWrapper> CREATOR =
            new Parcelable.Creator<GenericDocumentWrapper>() { // from class:
                // android.app.appfunctions.GenericDocumentWrapper.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public GenericDocumentWrapper createFromParcel(Parcel in) {
                    int length = in.readInt();
                    int offset = in.dataPosition();
                    in.setDataPosition(MathUtils.addOrThrow(offset, length));
                    Parcel p = Parcel.obtain();
                    p.appendFrom(in, offset, length);
                    p.setDataPosition(0);
                    return new GenericDocumentWrapper(p);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public GenericDocumentWrapper[] newArray(int size) {
                    return new GenericDocumentWrapper[size];
                }
            };
    private GenericDocument mGenericDocument;
    private final Object mLock;
    private Parcel mParcel;

    public GenericDocumentWrapper(GenericDocument genericDocument) {
        this.mLock = new Object();
        this.mGenericDocument = (GenericDocument) Objects.requireNonNull(genericDocument);
        this.mParcel = null;
    }

    public GenericDocumentWrapper(Parcel parcel) {
        this.mLock = new Object();
        this.mGenericDocument = null;
        this.mParcel = (Parcel) Objects.requireNonNull(parcel);
    }

    public GenericDocument getValue() {
        GenericDocument genericDocument;
        unparcel();
        synchronized (this.mLock) {
            genericDocument = (GenericDocument) Objects.requireNonNull(this.mGenericDocument);
        }
        return genericDocument;
    }

    private void unparcel() {
        synchronized (this.mLock) {
            if (this.mGenericDocument != null) {
                return;
            }
            byte[] dataBlob =
                    (byte[])
                            Objects.requireNonNull(
                                    ((Parcel) Objects.requireNonNull(this.mParcel)).readBlob());
            Parcel unmarshallParcel = Parcel.obtain();
            try {
                unmarshallParcel.unmarshall(dataBlob, 0, dataBlob.length);
                unmarshallParcel.setDataPosition(0);
                this.mGenericDocument = GenericDocument.createFromParcel(unmarshallParcel);
                this.mParcel.recycle();
                this.mParcel = null;
            } finally {
                unmarshallParcel.recycle();
            }
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        synchronized (this.mLock) {
            if (this.mGenericDocument != null) {
                int lengthPos = dest.dataPosition();
                dest.writeInt(-1);
                Parcel tempParcel = Parcel.obtain();
                try {
                    this.mGenericDocument.writeToParcel(tempParcel, flags);
                    byte[] bytes = tempParcel.marshall();
                    tempParcel.recycle();
                    int startPos = dest.dataPosition();
                    dest.writeBlob(bytes);
                    int endPos = dest.dataPosition();
                    dest.setDataPosition(lengthPos);
                    dest.writeInt(endPos - startPos);
                    dest.setDataPosition(endPos);
                } catch (Throwable th) {
                    tempParcel.recycle();
                    throw th;
                }
            } else {
                Parcel originalParcel = (Parcel) Objects.requireNonNull(this.mParcel);
                dest.writeInt(originalParcel.dataSize());
                dest.appendFrom(originalParcel, 0, originalParcel.dataSize());
            }
        }
    }
}
