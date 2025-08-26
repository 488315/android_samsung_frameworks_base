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
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeLong(this.nanoappId);
        parcel.writeInt(this.hostEndPoint);
        parcel.writeInt(this.messageType);
        parcel.writeByteArray(this.messageBody);
        parcel.writeStringArray(this.permissions);
        parcel.writeBoolean(this.isReliable);
        parcel.writeInt(this.messageSequenceNumber);
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
                this.nanoappId = parcel.readLong();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.hostEndPoint = (char) parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.messageType = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.messageBody = parcel.createByteArray();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.permissions = parcel.createStringArray();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.isReliable = parcel.readBoolean();
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.messageSequenceNumber = parcel.readInt();
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
            parcel.setDataPosition(iDataPosition + i);
        } catch (Throwable th) {
            if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
            throw th;
        }
    }
}
