package android.app.appfunctions;

import android.app.appsearch.GenericDocument;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.MathUtils;
import java.util.Objects;

/* loaded from: classes.dex */
public final class GenericDocumentWrapper implements Parcelable {
    public static final Parcelable.Creator<GenericDocumentWrapper> CREATOR = new Parcelable.Creator<GenericDocumentWrapper>() { // from class: android.app.appfunctions.GenericDocumentWrapper.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GenericDocumentWrapper createFromParcel(Parcel parcel) {
            int i = parcel.readInt();
            int iDataPosition = parcel.dataPosition();
            parcel.setDataPosition(MathUtils.addOrThrow(iDataPosition, i));
            Parcel parcelObtain = Parcel.obtain();
            parcelObtain.appendFrom(parcel, iDataPosition, i);
            parcelObtain.setDataPosition(0);
            return new GenericDocumentWrapper(parcelObtain);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GenericDocumentWrapper[] newArray(int i) {
            return new GenericDocumentWrapper[i];
        }
    };
    private Integer mDataSize;
    private GenericDocument mGenericDocument;
    private final Object mLock;
    private Parcel mParcel;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public GenericDocumentWrapper(GenericDocument genericDocument) {
        this.mLock = new Object();
        this.mGenericDocument = (GenericDocument) Objects.requireNonNull(genericDocument);
        this.mParcel = null;
        this.mDataSize = null;
    }

    public GenericDocumentWrapper(Parcel parcel) {
        this.mLock = new Object();
        this.mGenericDocument = null;
        Parcel parcel2 = (Parcel) Objects.requireNonNull(parcel);
        this.mParcel = parcel2;
        this.mDataSize = Integer.valueOf(parcel2.dataSize());
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
            byte[] bArr = (byte[]) Objects.requireNonNull(((Parcel) Objects.requireNonNull(this.mParcel)).readBlob());
            Parcel parcelObtain = Parcel.obtain();
            try {
                parcelObtain.unmarshall(bArr, 0, bArr.length);
                parcelObtain.setDataPosition(0);
                this.mGenericDocument = GenericDocument.createFromParcel(parcelObtain);
                this.mParcel.recycle();
                this.mParcel = null;
            } finally {
                parcelObtain.recycle();
            }
        }
    }

    int getDataSize() {
        synchronized (this.mLock) {
            Integer num = this.mDataSize;
            if (num != null) {
                return num.intValue();
            }
            Parcel parcelObtain = Parcel.obtain();
            writeToParcel(parcelObtain, 0);
            this.mDataSize = Integer.valueOf(parcelObtain.dataSize());
            parcelObtain.recycle();
            return this.mDataSize.intValue();
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        synchronized (this.mLock) {
            if (this.mGenericDocument != null) {
                int iDataPosition = parcel.dataPosition();
                parcel.writeInt(-1);
                Parcel parcelObtain = Parcel.obtain();
                try {
                    this.mGenericDocument.writeToParcel(parcelObtain, i);
                    byte[] bArrMarshall = parcelObtain.marshall();
                    parcelObtain.recycle();
                    int iDataPosition2 = parcel.dataPosition();
                    parcel.writeBlob(bArrMarshall);
                    int iDataPosition3 = parcel.dataPosition();
                    parcel.setDataPosition(iDataPosition);
                    parcel.writeInt(iDataPosition3 - iDataPosition2);
                    parcel.setDataPosition(iDataPosition3);
                } catch (Throwable th) {
                    parcelObtain.recycle();
                    throw th;
                }
            } else {
                Parcel parcel2 = (Parcel) Objects.requireNonNull(this.mParcel);
                parcel.writeInt(parcel2.dataSize());
                parcel.appendFrom(parcel2, 0, parcel2.dataSize());
            }
        }
    }
}
