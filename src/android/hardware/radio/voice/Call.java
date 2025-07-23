package android.hardware.radio.voice;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class Call implements Parcelable {
    public static final Parcelable.Creator<Call> CREATOR = new Parcelable.Creator<Call>() { // from class: android.hardware.radio.voice.Call.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Call createFromParcel(Parcel parcel) {
            Call call = new Call();
            call.readFromParcel(parcel);
            return call;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Call[] newArray(int i) {
            return new Call[i];
        }
    };
    public static final int PRESENTATION_ALLOWED = 0;
    public static final int PRESENTATION_PAYPHONE = 3;
    public static final int PRESENTATION_RESTRICTED = 1;
    public static final int PRESENTATION_UNKNOWN = 2;
    public static final int STATE_ACTIVE = 0;
    public static final int STATE_ALERTING = 3;
    public static final int STATE_DIALING = 2;
    public static final int STATE_HOLDING = 1;
    public static final int STATE_INCOMING = 4;
    public static final int STATE_WAITING = 5;
    public String forwardedNumber;
    public String name;
    public String number;
    public UusInfo[] uusInfo;
    public int state = 0;
    public int index = 0;
    public int toa = 0;
    public boolean isMpty = false;
    public boolean isMT = false;
    public byte als = 0;
    public boolean isVoice = false;

    @Deprecated
    public boolean isVoicePrivacy = false;
    public int numberPresentation = 0;
    public int namePresentation = 0;
    public int audioQuality = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.state);
        parcel.writeInt(this.index);
        parcel.writeInt(this.toa);
        parcel.writeBoolean(this.isMpty);
        parcel.writeBoolean(this.isMT);
        parcel.writeByte(this.als);
        parcel.writeBoolean(this.isVoice);
        parcel.writeBoolean(this.isVoicePrivacy);
        parcel.writeString(this.number);
        parcel.writeInt(this.numberPresentation);
        parcel.writeString(this.name);
        parcel.writeInt(this.namePresentation);
        parcel.writeTypedArray(this.uusInfo, i);
        parcel.writeInt(this.audioQuality);
        parcel.writeString(this.forwardedNumber);
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
                this.state = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.index = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.toa = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.isMpty = parcel.readBoolean();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.isMT = parcel.readBoolean();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.als = parcel.readByte();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.isVoice = parcel.readBoolean();
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            this.isVoicePrivacy = parcel.readBoolean();
                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                this.number = parcel.readString();
                                                if (parcel.dataPosition() - dataPosition < readInt) {
                                                    this.numberPresentation = parcel.readInt();
                                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                                        this.name = parcel.readString();
                                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                                            this.namePresentation = parcel.readInt();
                                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                                this.uusInfo = (UusInfo[]) parcel.createTypedArray(UusInfo.CREATOR);
                                                                if (parcel.dataPosition() - dataPosition < readInt) {
                                                                    this.audioQuality = parcel.readInt();
                                                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                                                        this.forwardedNumber = parcel.readString();
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

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "{", "}");
        stringJoiner.add("state: " + this.state);
        stringJoiner.add("index: " + this.index);
        stringJoiner.add("toa: " + this.toa);
        stringJoiner.add("isMpty: " + this.isMpty);
        stringJoiner.add("isMT: " + this.isMT);
        stringJoiner.add("als: " + ((int) this.als));
        stringJoiner.add("isVoice: " + this.isVoice);
        stringJoiner.add("isVoicePrivacy: " + this.isVoicePrivacy);
        stringJoiner.add("number: " + Objects.toString(this.number));
        stringJoiner.add("numberPresentation: " + this.numberPresentation);
        stringJoiner.add("name: " + Objects.toString(this.name));
        stringJoiner.add("namePresentation: " + this.namePresentation);
        stringJoiner.add("uusInfo: " + Arrays.toString(this.uusInfo));
        stringJoiner.add("audioQuality: " + AudioQuality$$.toString(this.audioQuality));
        stringJoiner.add("forwardedNumber: " + Objects.toString(this.forwardedNumber));
        return "Call" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.uusInfo);
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
