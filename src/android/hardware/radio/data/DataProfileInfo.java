package android.hardware.radio.data;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public class DataProfileInfo implements Parcelable {
    public static final Parcelable.Creator<DataProfileInfo> CREATOR = new Parcelable.Creator<DataProfileInfo>() { // from class: android.hardware.radio.data.DataProfileInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DataProfileInfo createFromParcel(Parcel parcel) {
            DataProfileInfo dataProfileInfo = new DataProfileInfo();
            dataProfileInfo.readFromParcel(parcel);
            return dataProfileInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DataProfileInfo[] newArray(int i) {
            return new DataProfileInfo[i];
        }
    };
    public static final int ID_CBS = 4;
    public static final int ID_DEFAULT = 0;
    public static final int ID_FOTA = 3;
    public static final int ID_IMS = 2;
    public static final int ID_INVALID = -1;
    public static final int ID_OEM_BASE = 1000;
    public static final int ID_TETHERED = 1;
    public static final int INFRASTRUCTURE_CELLULAR = 1;
    public static final int INFRASTRUCTURE_SATELLITE = 2;
    public static final int INFRASTRUCTURE_UNKNOWN = 0;
    public static final int TYPE_3GPP = 1;
    public static final int TYPE_3GPP2 = 2;
    public static final int TYPE_COMMON = 0;
    public String apn;
    public String password;
    public TrafficDescriptor trafficDescriptor;
    public String user;
    public int profileId = 0;
    public int protocol = 0;
    public int roamingProtocol = 0;
    public int authType = 0;
    public int type = 0;
    public int maxConnsTime = 0;
    public int maxConns = 0;
    public int waitTime = 0;
    public boolean enabled = false;
    public int supportedApnTypesBitmap = 0;
    public int bearerBitmap = 0;
    public int mtuV4 = 0;
    public int mtuV6 = 0;
    public boolean preferred = false;
    public boolean persistent = false;
    public boolean alwaysOn = false;
    public int infrastructureBitmap = 0;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.profileId);
        parcel.writeString(this.apn);
        parcel.writeInt(this.protocol);
        parcel.writeInt(this.roamingProtocol);
        parcel.writeInt(this.authType);
        parcel.writeString(this.user);
        parcel.writeString(this.password);
        parcel.writeInt(this.type);
        parcel.writeInt(this.maxConnsTime);
        parcel.writeInt(this.maxConns);
        parcel.writeInt(this.waitTime);
        parcel.writeBoolean(this.enabled);
        parcel.writeInt(this.supportedApnTypesBitmap);
        parcel.writeInt(this.bearerBitmap);
        parcel.writeInt(this.mtuV4);
        parcel.writeInt(this.mtuV6);
        parcel.writeBoolean(this.preferred);
        parcel.writeBoolean(this.persistent);
        parcel.writeBoolean(this.alwaysOn);
        parcel.writeTypedObject(this.trafficDescriptor, i);
        parcel.writeInt(this.infrastructureBitmap);
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
                this.profileId = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.apn = parcel.readString();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.protocol = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.roamingProtocol = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.authType = parcel.readInt();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.user = parcel.readString();
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.password = parcel.readString();
                                        if (parcel.dataPosition() - iDataPosition < i) {
                                            this.type = parcel.readInt();
                                            if (parcel.dataPosition() - iDataPosition < i) {
                                                this.maxConnsTime = parcel.readInt();
                                                if (parcel.dataPosition() - iDataPosition < i) {
                                                    this.maxConns = parcel.readInt();
                                                    if (parcel.dataPosition() - iDataPosition < i) {
                                                        this.waitTime = parcel.readInt();
                                                        if (parcel.dataPosition() - iDataPosition < i) {
                                                            this.enabled = parcel.readBoolean();
                                                            if (parcel.dataPosition() - iDataPosition < i) {
                                                                this.supportedApnTypesBitmap = parcel.readInt();
                                                                if (parcel.dataPosition() - iDataPosition < i) {
                                                                    this.bearerBitmap = parcel.readInt();
                                                                    if (parcel.dataPosition() - iDataPosition < i) {
                                                                        this.mtuV4 = parcel.readInt();
                                                                        if (parcel.dataPosition() - iDataPosition < i) {
                                                                            this.mtuV6 = parcel.readInt();
                                                                            if (parcel.dataPosition() - iDataPosition < i) {
                                                                                this.preferred = parcel.readBoolean();
                                                                                if (parcel.dataPosition() - iDataPosition < i) {
                                                                                    this.persistent = parcel.readBoolean();
                                                                                    if (parcel.dataPosition() - iDataPosition < i) {
                                                                                        this.alwaysOn = parcel.readBoolean();
                                                                                        if (parcel.dataPosition() - iDataPosition < i) {
                                                                                            this.trafficDescriptor = (TrafficDescriptor) parcel.readTypedObject(TrafficDescriptor.CREATOR);
                                                                                            if (parcel.dataPosition() - iDataPosition < i) {
                                                                                                this.infrastructureBitmap = parcel.readInt();
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
        stringJoiner.add("profileId: " + this.profileId);
        stringJoiner.add("apn: " + Objects.toString(this.apn));
        stringJoiner.add("protocol: " + PdpProtocolType$$.toString(this.protocol));
        stringJoiner.add("roamingProtocol: " + PdpProtocolType$$.toString(this.roamingProtocol));
        stringJoiner.add("authType: " + ApnAuthType$$.toString(this.authType));
        stringJoiner.add("user: " + Objects.toString(this.user));
        stringJoiner.add("password: " + Objects.toString(this.password));
        stringJoiner.add("type: " + this.type);
        stringJoiner.add("maxConnsTime: " + this.maxConnsTime);
        stringJoiner.add("maxConns: " + this.maxConns);
        stringJoiner.add("waitTime: " + this.waitTime);
        stringJoiner.add("enabled: " + this.enabled);
        stringJoiner.add("supportedApnTypesBitmap: " + this.supportedApnTypesBitmap);
        stringJoiner.add("bearerBitmap: " + this.bearerBitmap);
        stringJoiner.add("mtuV4: " + this.mtuV4);
        stringJoiner.add("mtuV6: " + this.mtuV6);
        stringJoiner.add("preferred: " + this.preferred);
        stringJoiner.add("persistent: " + this.persistent);
        stringJoiner.add("alwaysOn: " + this.alwaysOn);
        stringJoiner.add("trafficDescriptor: " + Objects.toString(this.trafficDescriptor));
        stringJoiner.add("infrastructureBitmap: " + this.infrastructureBitmap);
        return "DataProfileInfo" + stringJoiner.toString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.trafficDescriptor);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
