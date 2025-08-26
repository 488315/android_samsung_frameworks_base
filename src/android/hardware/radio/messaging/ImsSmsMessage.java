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
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.tech);
        parcel.writeBoolean(this.retry);
        parcel.writeInt(this.messageRef);
        parcel.writeTypedArray(this.cdmaMessage, i);
        parcel.writeTypedArray(this.gsmMessage, i);
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
                this.tech = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.retry = parcel.readBoolean();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.messageRef = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.cdmaMessage = (CdmaSmsMessage[]) parcel.createTypedArray(CdmaSmsMessage.CREATOR);
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.gsmMessage = (GsmSmsMessage[]) parcel.createTypedArray(GsmSmsMessage.CREATOR);
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
            parcel.setDataPosition(iDataPosition + i);
        } catch (Throwable th) {
            if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
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
            int iDescribeContents = 0;
            for (Object obj2 : (Object[]) obj) {
                iDescribeContents |= describeContents(obj2);
            }
            return iDescribeContents;
        }
        if (obj instanceof Parcelable) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
