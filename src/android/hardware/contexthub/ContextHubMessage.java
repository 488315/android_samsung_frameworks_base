package android.hardware.contexthub;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class ContextHubMessage implements Parcelable {
    public static final Parcelable.Creator<ContextHubMessage> CREATOR = new Parcelable.Creator<ContextHubMessage>() { // from class: android.hardware.contexthub.ContextHubMessage.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ContextHubMessage createFromParcel(Parcel parcel) {
            ContextHubMessage contextHubMessage = new ContextHubMessage();
            contextHubMessage.readFromParcel(parcel);
            return contextHubMessage;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ContextHubMessage[] newArray(int i) {
            return new ContextHubMessage[i];
        }
    };
    public byte[] messageBody;
    public String[] permissions;
    public long nanoappId = 0;
    public char hostEndPoint = 0;
    public int messageType = 0;
    public boolean isReliable = false;
    public int messageSequenceNumber = 0;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeLong(this.nanoappId);
        parcel.writeInt(this.hostEndPoint);
        parcel.writeInt(this.messageType);
        parcel.writeByteArray(this.messageBody);
        parcel.writeStringArray(this.permissions);
        parcel.writeBoolean(this.isReliable);
        parcel.writeInt(this.messageSequenceNumber);
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
                this.nanoappId = parcel.readLong();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.hostEndPoint = (char) parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.messageType = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.messageBody = parcel.createByteArray();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.permissions = parcel.createStringArray();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.isReliable = parcel.readBoolean();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.messageSequenceNumber = parcel.readInt();
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
            parcel.setDataPosition(dataPosition + readInt);
        } catch (Throwable th) {
            if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
            throw th;
        }
    }
}
