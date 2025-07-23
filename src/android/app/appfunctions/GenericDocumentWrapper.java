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
            int readInt = parcel.readInt();
            int dataPosition = parcel.dataPosition();
            parcel.setDataPosition(MathUtils.addOrThrow(dataPosition, readInt));
            Parcel obtain = Parcel.obtain();
            obtain.appendFrom(parcel, dataPosition, readInt);
            obtain.setDataPosition(0);
            return new GenericDocumentWrapper(obtain);
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
            Parcel obtain = Parcel.obtain();
            try {
                obtain.unmarshall(bArr, 0, bArr.length);
                obtain.setDataPosition(0);
                this.mGenericDocument = GenericDocument.createFromParcel(obtain);
                this.mParcel.recycle();
                this.mParcel = null;
            } finally {
                obtain.recycle();
            }
        }
    }

    int getDataSize() {
        synchronized (this.mLock) {
            Integer num = this.mDataSize;
            if (num != null) {
                return num.intValue();
            }
            Parcel obtain = Parcel.obtain();
            writeToParcel(obtain, 0);
            this.mDataSize = Integer.valueOf(obtain.dataSize());
            obtain.recycle();
            return this.mDataSize.intValue();
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        synchronized (this.mLock) {
            if (this.mGenericDocument != null) {
                int dataPosition = parcel.dataPosition();
                parcel.writeInt(-1);
                Parcel obtain = Parcel.obtain();
                try {
                    this.mGenericDocument.writeToParcel(obtain, i);
                    byte[] marshall = obtain.marshall();
                    obtain.recycle();
                    int dataPosition2 = parcel.dataPosition();
                    parcel.writeBlob(marshall);
                    int dataPosition3 = parcel.dataPosition();
                    parcel.setDataPosition(dataPosition);
                    parcel.writeInt(dataPosition3 - dataPosition2);
                    parcel.setDataPosition(dataPosition3);
                } catch (Throwable th) {
                    obtain.recycle();
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
