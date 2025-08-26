package android.system.keystore2;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import android.system.keystore2.IKeystoreOperation;

/* loaded from: classes3.dex */
public class CreateOperationResponse implements Parcelable {
    public static final Parcelable.Creator<CreateOperationResponse> CREATOR = new Parcelable.Creator<CreateOperationResponse>() { // from class: android.system.keystore2.CreateOperationResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CreateOperationResponse createFromParcel(Parcel parcel) {
            CreateOperationResponse createOperationResponse = new CreateOperationResponse();
            createOperationResponse.readFromParcel(parcel);
            return createOperationResponse;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CreateOperationResponse[] newArray(int i) {
            return new CreateOperationResponse[i];
        }
    };
    public IKeystoreOperation iOperation;
    public OperationChallenge operationChallenge;
    public KeyParameters parameters;
    public byte[] upgradedBlob;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeStrongInterface(this.iOperation);
        parcel.writeTypedObject(this.operationChallenge, i);
        parcel.writeTypedObject(this.parameters, i);
        parcel.writeByteArray(this.upgradedBlob);
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
                this.iOperation = IKeystoreOperation.Stub.asInterface(parcel.readStrongBinder());
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.operationChallenge = (OperationChallenge) parcel.readTypedObject(OperationChallenge.CREATOR);
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.parameters = (KeyParameters) parcel.readTypedObject(KeyParameters.CREATOR);
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.upgradedBlob = parcel.createByteArray();
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
        return describeContents(this.parameters) | describeContents(this.operationChallenge);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
