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
        int iDataPosition = parcel.dataPosition();
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
                this.id = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.reason = parcel.readByte();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.isAod = parcel.readBoolean();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.isCrypto = parcel.readBoolean();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.wakeReason = parcel.readInt();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.displayState = parcel.readInt();
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.authenticateReason = (AuthenticateReason) parcel.readTypedObject(AuthenticateReason.CREATOR);
                                        if (parcel.dataPosition() - iDataPosition < i) {
                                            this.foldState = parcel.readInt();
                                            if (parcel.dataPosition() - iDataPosition < i) {
                                                this.operationState = (OperationState) parcel.readTypedObject(OperationState.CREATOR);
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
        return describeContents(this.operationState) | describeContents(this.authenticateReason);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
