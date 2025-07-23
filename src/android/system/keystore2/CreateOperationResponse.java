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
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeStrongInterface(this.iOperation);
        parcel.writeTypedObject(this.operationChallenge, i);
        parcel.writeTypedObject(this.parameters, i);
        parcel.writeByteArray(this.upgradedBlob);
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
                this.iOperation = IKeystoreOperation.Stub.asInterface(parcel.readStrongBinder());
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.operationChallenge = (OperationChallenge) parcel.readTypedObject(OperationChallenge.CREATOR);
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.parameters = (KeyParameters) parcel.readTypedObject(KeyParameters.CREATOR);
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.upgradedBlob = parcel.createByteArray();
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
        return describeContents(this.parameters) | describeContents(this.operationChallenge);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
