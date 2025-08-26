package android.hardware.radio.sim;

import android.hardware.radio.config.MultipleEnabledProfilesMode$$;
import android.hardware.radio.config.SlotPortMapping;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class CardStatus implements Parcelable {
    public static final Parcelable.Creator<CardStatus> CREATOR = new Parcelable.Creator<CardStatus>() { // from class: android.hardware.radio.sim.CardStatus.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CardStatus createFromParcel(Parcel parcel) {
            CardStatus cardStatus = new CardStatus();
            cardStatus.readFromParcel(parcel);
            return cardStatus;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CardStatus[] newArray(int i) {
            return new CardStatus[i];
        }
    };
    public static final int STATE_ABSENT = 0;
    public static final int STATE_ERROR = 2;
    public static final int STATE_PRESENT = 1;
    public static final int STATE_RESTRICTED = 3;
    public AppStatus[] applications;
    public String atr;
    public String eid;
    public String iccid;
    public SlotPortMapping slotMap;
    public int cardState = 0;
    public int universalPinState = 0;
    public int gsmUmtsSubscriptionAppIndex = 0;

    @Deprecated
    public int cdmaSubscriptionAppIndex = 0;
    public int imsSubscriptionAppIndex = 0;
    public int supportedMepMode = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.cardState);
        parcel.writeInt(this.universalPinState);
        parcel.writeInt(this.gsmUmtsSubscriptionAppIndex);
        parcel.writeInt(this.cdmaSubscriptionAppIndex);
        parcel.writeInt(this.imsSubscriptionAppIndex);
        parcel.writeTypedArray(this.applications, i);
        parcel.writeString(this.atr);
        parcel.writeString(this.iccid);
        parcel.writeString(this.eid);
        parcel.writeTypedObject(this.slotMap, i);
        parcel.writeInt(this.supportedMepMode);
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
                this.cardState = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.universalPinState = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.gsmUmtsSubscriptionAppIndex = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.cdmaSubscriptionAppIndex = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.imsSubscriptionAppIndex = parcel.readInt();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.applications = (AppStatus[]) parcel.createTypedArray(AppStatus.CREATOR);
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.atr = parcel.readString();
                                        if (parcel.dataPosition() - iDataPosition < i) {
                                            this.iccid = parcel.readString();
                                            if (parcel.dataPosition() - iDataPosition < i) {
                                                this.eid = parcel.readString();
                                                if (parcel.dataPosition() - iDataPosition < i) {
                                                    this.slotMap = (SlotPortMapping) parcel.readTypedObject(SlotPortMapping.CREATOR);
                                                    if (parcel.dataPosition() - iDataPosition < i) {
                                                        this.supportedMepMode = parcel.readInt();
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
        stringJoiner.add("cardState: " + this.cardState);
        stringJoiner.add("universalPinState: " + PinState$$.toString(this.universalPinState));
        stringJoiner.add("gsmUmtsSubscriptionAppIndex: " + this.gsmUmtsSubscriptionAppIndex);
        stringJoiner.add("cdmaSubscriptionAppIndex: " + this.cdmaSubscriptionAppIndex);
        stringJoiner.add("imsSubscriptionAppIndex: " + this.imsSubscriptionAppIndex);
        stringJoiner.add("applications: " + Arrays.toString(this.applications));
        stringJoiner.add("atr: " + Objects.toString(this.atr));
        stringJoiner.add("iccid: " + Objects.toString(this.iccid));
        stringJoiner.add("eid: " + Objects.toString(this.eid));
        stringJoiner.add("slotMap: " + Objects.toString(this.slotMap));
        stringJoiner.add("supportedMepMode: " + MultipleEnabledProfilesMode$$.toString(this.supportedMepMode));
        return "CardStatus" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.slotMap) | describeContents(this.applications);
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
