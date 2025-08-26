package android.hardware.contexthub;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class ContextHubInfo implements Parcelable {
    public static final Parcelable.Creator<ContextHubInfo> CREATOR = new Parcelable.Creator<ContextHubInfo>() { // from class: android.hardware.contexthub.ContextHubInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ContextHubInfo createFromParcel(Parcel parcel) {
            ContextHubInfo contextHubInfo = new ContextHubInfo();
            contextHubInfo.readFromParcel(parcel);
            return contextHubInfo;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ContextHubInfo[] newArray(int i) {
            return new ContextHubInfo[i];
        }
    };
    public String name;
    public String[] supportedPermissions;
    public String toolchain;

    /* renamed from: vendor, reason: collision with root package name */
    public String f1vendor;
    public int id = 0;
    public float peakMips = 0.0f;
    public int maxSupportedMessageLengthBytes = 0;
    public long chrePlatformId = 0;
    public byte chreApiMajorVersion = 0;
    public byte chreApiMinorVersion = 0;
    public char chrePatchVersion = 0;
    public boolean supportsReliableMessages = false;

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
        parcel.writeString(this.name);
        parcel.writeString(this.f1vendor);
        parcel.writeString(this.toolchain);
        parcel.writeInt(this.id);
        parcel.writeFloat(this.peakMips);
        parcel.writeInt(this.maxSupportedMessageLengthBytes);
        parcel.writeLong(this.chrePlatformId);
        parcel.writeByte(this.chreApiMajorVersion);
        parcel.writeByte(this.chreApiMinorVersion);
        parcel.writeInt(this.chrePatchVersion);
        parcel.writeStringArray(this.supportedPermissions);
        parcel.writeBoolean(this.supportsReliableMessages);
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
                this.name = parcel.readString();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.f1vendor = parcel.readString();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.toolchain = parcel.readString();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.id = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.peakMips = parcel.readFloat();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.maxSupportedMessageLengthBytes = parcel.readInt();
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.chrePlatformId = parcel.readLong();
                                        if (parcel.dataPosition() - iDataPosition < i) {
                                            this.chreApiMajorVersion = parcel.readByte();
                                            if (parcel.dataPosition() - iDataPosition < i) {
                                                this.chreApiMinorVersion = parcel.readByte();
                                                if (parcel.dataPosition() - iDataPosition < i) {
                                                    this.chrePatchVersion = (char) parcel.readInt();
                                                    if (parcel.dataPosition() - iDataPosition < i) {
                                                        this.supportedPermissions = parcel.createStringArray();
                                                        if (parcel.dataPosition() - iDataPosition < i) {
                                                            this.supportsReliableMessages = parcel.readBoolean();
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
