package android.hardware.biometrics.common;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class OperationContext implements Parcelable {
    public static final Parcelable.Creator<OperationContext> CREATOR = new Parcelable.Creator<OperationContext>() { // from class: android.hardware.biometrics.common.OperationContext.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public OperationContext createFromParcel(Parcel parcel) {
            OperationContext operationContext = new OperationContext();
            operationContext.readFromParcel(parcel);
            return operationContext;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public OperationContext[] newArray(int i) {
            return new OperationContext[i];
        }
    };
    public AuthenticateReason authenticateReason;
    public OperationState operationState;
    public int id = 0;
    public byte reason = 0;

    @Deprecated
    public boolean isAod = false;
    public boolean isCrypto = false;
    public int wakeReason = 0;
    public int displayState = 0;
    public int foldState = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.id);
        parcel.writeByte(this.reason);
        parcel.writeBoolean(this.isAod);
        parcel.writeBoolean(this.isCrypto);
        parcel.writeInt(this.wakeReason);
        parcel.writeInt(this.displayState);
        parcel.writeTypedObject(this.authenticateReason, i);
        parcel.writeInt(this.foldState);
        parcel.writeTypedObject(this.operationState, i);
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
                this.id = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.reason = parcel.readByte();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.isAod = parcel.readBoolean();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.isCrypto = parcel.readBoolean();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.wakeReason = parcel.readInt();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.displayState = parcel.readInt();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.authenticateReason = (AuthenticateReason) parcel.readTypedObject(AuthenticateReason.CREATOR);
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            this.foldState = parcel.readInt();
                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                this.operationState = (OperationState) parcel.readTypedObject(OperationState.CREATOR);
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
        return describeContents(this.operationState) | describeContents(this.authenticateReason);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
