package android.hardware.radio.messaging;

import android.hardware.radio.RadioTechnologyFamily$$;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class ImsSmsMessage implements Parcelable {
    public static final Parcelable.Creator<ImsSmsMessage> CREATOR = new Parcelable.Creator<ImsSmsMessage>() { // from class: android.hardware.radio.messaging.ImsSmsMessage.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImsSmsMessage createFromParcel(Parcel parcel) {
            ImsSmsMessage imsSmsMessage = new ImsSmsMessage();
            imsSmsMessage.readFromParcel(parcel);
            return imsSmsMessage;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImsSmsMessage[] newArray(int i) {
            return new ImsSmsMessage[i];
        }
    };

    @Deprecated
    public CdmaSmsMessage[] cdmaMessage;
    public GsmSmsMessage[] gsmMessage;
    public int tech = 0;
    public boolean retry = false;
    public int messageRef = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.tech);
        parcel.writeBoolean(this.retry);
        parcel.writeInt(this.messageRef);
        parcel.writeTypedArray(this.cdmaMessage, i);
        parcel.writeTypedArray(this.gsmMessage, i);
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
                this.tech = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.retry = parcel.readBoolean();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.messageRef = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.cdmaMessage = (CdmaSmsMessage[]) parcel.createTypedArray(CdmaSmsMessage.CREATOR);
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.gsmMessage = (GsmSmsMessage[]) parcel.createTypedArray(GsmSmsMessage.CREATOR);
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
            parcel.setDataPosition(dataPosition + readInt);
        } catch (Throwable th) {
            if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
            throw th;
        }
    }

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        stringJoiner.add("tech: " + RadioTechnologyFamily$$.toString(this.tech));
        stringJoiner.add("retry: " + this.retry);
        stringJoiner.add("messageRef: " + this.messageRef);
        stringJoiner.add("cdmaMessage: " + Arrays.toString(this.cdmaMessage));
        stringJoiner.add("gsmMessage: " + Arrays.toString(this.gsmMessage));
        return "ImsSmsMessage" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.gsmMessage) | describeContents(this.cdmaMessage);
    }

    private int describeContents(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof Object[]) {
            int i = 0;
            for (Object obj2 : (Object[]) obj) {
                i |= describeContents(obj2);
            }
            return i;
        }
        if (obj instanceof Parcelable) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
