package android.hardware.radio.data;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class SetupDataCallResult implements Parcelable {
    public static final Parcelable.Creator<SetupDataCallResult> CREATOR = new Parcelable.Creator<SetupDataCallResult>() { // from class: android.hardware.radio.data.SetupDataCallResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SetupDataCallResult createFromParcel(Parcel parcel) {
            SetupDataCallResult setupDataCallResult = new SetupDataCallResult();
            setupDataCallResult.readFromParcel(parcel);
            return setupDataCallResult;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SetupDataCallResult[] newArray(int i) {
            return new SetupDataCallResult[i];
        }
    };
    public static final int DATA_CONNECTION_STATUS_ACTIVE = 2;
    public static final int DATA_CONNECTION_STATUS_DORMANT = 1;
    public static final int DATA_CONNECTION_STATUS_INACTIVE = 0;
    public static final byte HANDOVER_FAILURE_MODE_DO_FALLBACK = 1;
    public static final byte HANDOVER_FAILURE_MODE_LEGACY = 0;
    public static final byte HANDOVER_FAILURE_MODE_NO_FALLBACK_RETRY_HANDOVER = 2;
    public static final byte HANDOVER_FAILURE_MODE_NO_FALLBACK_RETRY_SETUP_NORMAL = 3;
    public LinkAddress[] addresses;
    public Qos defaultQos;
    public String[] dnses;
    public String[] gateways;
    public String ifname;
    public String[] pcscf;
    public QosSession[] qosSessions;
    public SliceInfo sliceInfo;
    public TrafficDescriptor[] trafficDescriptors;
    public int cause = 0;
    public long suggestedRetryTime = 0;
    public int cid = 0;
    public int active = 0;
    public int type = 0;
    public int mtuV4 = 0;
    public int mtuV6 = 0;
    public byte handoverFailureMode = 0;
    public int pduSessionId = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.cause);
        parcel.writeLong(this.suggestedRetryTime);
        parcel.writeInt(this.cid);
        parcel.writeInt(this.active);
        parcel.writeInt(this.type);
        parcel.writeString(this.ifname);
        parcel.writeTypedArray(this.addresses, i);
        parcel.writeStringArray(this.dnses);
        parcel.writeStringArray(this.gateways);
        parcel.writeStringArray(this.pcscf);
        parcel.writeInt(this.mtuV4);
        parcel.writeInt(this.mtuV6);
        parcel.writeTypedObject(this.defaultQos, i);
        parcel.writeTypedArray(this.qosSessions, i);
        parcel.writeByte(this.handoverFailureMode);
        parcel.writeInt(this.pduSessionId);
        parcel.writeTypedObject(this.sliceInfo, i);
        parcel.writeTypedArray(this.trafficDescriptors, i);
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
                this.cause = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.suggestedRetryTime = parcel.readLong();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.cid = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.active = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.type = parcel.readInt();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.ifname = parcel.readString();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.addresses = (LinkAddress[]) parcel.createTypedArray(LinkAddress.CREATOR);
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            this.dnses = parcel.createStringArray();
                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                this.gateways = parcel.createStringArray();
                                                if (parcel.dataPosition() - dataPosition < readInt) {
                                                    this.pcscf = parcel.createStringArray();
                                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                                        this.mtuV4 = parcel.readInt();
                                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                                            this.mtuV6 = parcel.readInt();
                                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                                this.defaultQos = (Qos) parcel.readTypedObject(Qos.CREATOR);
                                                                if (parcel.dataPosition() - dataPosition < readInt) {
                                                                    this.qosSessions = (QosSession[]) parcel.createTypedArray(QosSession.CREATOR);
                                                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                                                        this.handoverFailureMode = parcel.readByte();
                                                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                                                            this.pduSessionId = parcel.readInt();
                                                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                                                this.sliceInfo = (SliceInfo) parcel.readTypedObject(SliceInfo.CREATOR);
                                                                                if (parcel.dataPosition() - dataPosition < readInt) {
                                                                                    this.trafficDescriptors = (TrafficDescriptor[]) parcel.createTypedArray(TrafficDescriptor.CREATOR);
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
        stringJoiner.add("cause: " + DataCallFailCause$$.toString(this.cause));
        stringJoiner.add("suggestedRetryTime: " + this.suggestedRetryTime);
        stringJoiner.add("cid: " + this.cid);
        stringJoiner.add("active: " + this.active);
        stringJoiner.add("type: " + PdpProtocolType$$.toString(this.type));
        stringJoiner.add("ifname: " + Objects.toString(this.ifname));
        stringJoiner.add("addresses: " + Arrays.toString(this.addresses));
        stringJoiner.add("dnses: " + Arrays.toString(this.dnses));
        stringJoiner.add("gateways: " + Arrays.toString(this.gateways));
        stringJoiner.add("pcscf: " + Arrays.toString(this.pcscf));
        stringJoiner.add("mtuV4: " + this.mtuV4);
        stringJoiner.add("mtuV6: " + this.mtuV6);
        stringJoiner.add("defaultQos: " + Objects.toString(this.defaultQos));
        stringJoiner.add("qosSessions: " + Arrays.toString(this.qosSessions));
        stringJoiner.add("handoverFailureMode: " + ((int) this.handoverFailureMode));
        stringJoiner.add("pduSessionId: " + this.pduSessionId);
        stringJoiner.add("sliceInfo: " + Objects.toString(this.sliceInfo));
        stringJoiner.add("trafficDescriptors: " + Arrays.toString(this.trafficDescriptors));
        return "SetupDataCallResult" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.trafficDescriptors) | describeContents(this.addresses) | describeContents(this.defaultQos) | describeContents(this.qosSessions) | describeContents(this.sliceInfo);
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
