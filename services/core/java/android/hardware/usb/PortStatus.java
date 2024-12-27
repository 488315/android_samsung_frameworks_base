package android.hardware.usb;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class PortStatus implements Parcelable {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1();
    public boolean canChangeDataRole;
    public boolean canChangeMode;
    public boolean canChangePowerRole;
    public int[] complianceWarnings;
    public byte contaminantDetectionStatus;
    public byte contaminantProtectionStatus;
    public byte currentDataRole;
    public byte currentMode;
    public byte currentPowerRole;
    public int plugOrientation;
    public String portName;
    public byte powerBrickStatus;
    public boolean powerTransferLimited;
    public AltModeData[] supportedAltModes;
    public byte[] supportedContaminantProtectionModes;
    public byte[] supportedModes;
    public boolean supportsComplianceWarnings;
    public boolean supportsEnableContaminantPresenceDetection;
    public boolean supportsEnableContaminantPresenceProtection;
    public byte[] usbDataStatus;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: android.hardware.usb.PortStatus$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            PortStatus portStatus = new PortStatus();
            portStatus.currentDataRole = (byte) 0;
            portStatus.currentPowerRole = (byte) 0;
            portStatus.currentMode = (byte) 0;
            portStatus.canChangeMode = false;
            portStatus.canChangeDataRole = false;
            portStatus.canChangePowerRole = false;
            portStatus.supportsEnableContaminantPresenceProtection = false;
            portStatus.contaminantProtectionStatus = (byte) 0;
            portStatus.supportsEnableContaminantPresenceDetection = false;
            portStatus.contaminantDetectionStatus = (byte) 0;
            portStatus.powerTransferLimited = false;
            portStatus.supportsComplianceWarnings = false;
            portStatus.complianceWarnings = new int[0];
            portStatus.plugOrientation = 0;
            portStatus.supportedAltModes = new AltModeData[0];
            int dataPosition = parcel.dataPosition();
            int readInt = parcel.readInt();
            try {
                if (readInt < 4) {
                    throw new BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - dataPosition < readInt) {
                    portStatus.portName = parcel.readString();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        portStatus.currentDataRole = parcel.readByte();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            portStatus.currentPowerRole = parcel.readByte();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                portStatus.currentMode = parcel.readByte();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    portStatus.canChangeMode = parcel.readBoolean();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        portStatus.canChangeDataRole = parcel.readBoolean();
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            portStatus.canChangePowerRole = parcel.readBoolean();
                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                portStatus.supportedModes =
                                                        parcel.createByteArray();
                                                if (parcel.dataPosition() - dataPosition
                                                        < readInt) {
                                                    portStatus.supportedContaminantProtectionModes =
                                                            parcel.createByteArray();
                                                    if (parcel.dataPosition() - dataPosition
                                                            < readInt) {
                                                        portStatus
                                                                        .supportsEnableContaminantPresenceProtection =
                                                                parcel.readBoolean();
                                                        if (parcel.dataPosition() - dataPosition
                                                                < readInt) {
                                                            portStatus.contaminantProtectionStatus =
                                                                    parcel.readByte();
                                                            if (parcel.dataPosition() - dataPosition
                                                                    < readInt) {
                                                                portStatus
                                                                                .supportsEnableContaminantPresenceDetection =
                                                                        parcel.readBoolean();
                                                                if (parcel.dataPosition()
                                                                                - dataPosition
                                                                        < readInt) {
                                                                    portStatus
                                                                                    .contaminantDetectionStatus =
                                                                            parcel.readByte();
                                                                    if (parcel.dataPosition()
                                                                                    - dataPosition
                                                                            < readInt) {
                                                                        portStatus.usbDataStatus =
                                                                                parcel
                                                                                        .createByteArray();
                                                                        if (parcel.dataPosition()
                                                                                        - dataPosition
                                                                                < readInt) {
                                                                            portStatus
                                                                                            .powerTransferLimited =
                                                                                    parcel
                                                                                            .readBoolean();
                                                                            if (parcel
                                                                                                    .dataPosition()
                                                                                            - dataPosition
                                                                                    < readInt) {
                                                                                portStatus
                                                                                                .powerBrickStatus =
                                                                                        parcel
                                                                                                .readByte();
                                                                                if (parcel
                                                                                                        .dataPosition()
                                                                                                - dataPosition
                                                                                        < readInt) {
                                                                                    portStatus
                                                                                                    .supportsComplianceWarnings =
                                                                                            parcel
                                                                                                    .readBoolean();
                                                                                    if (parcel
                                                                                                            .dataPosition()
                                                                                                    - dataPosition
                                                                                            < readInt) {
                                                                                        portStatus
                                                                                                        .complianceWarnings =
                                                                                                parcel
                                                                                                        .createIntArray();
                                                                                        if (parcel
                                                                                                                .dataPosition()
                                                                                                        - dataPosition
                                                                                                < readInt) {
                                                                                            portStatus
                                                                                                            .plugOrientation =
                                                                                                    parcel
                                                                                                            .readInt();
                                                                                            if (parcel
                                                                                                                    .dataPosition()
                                                                                                            - dataPosition
                                                                                                    < readInt) {
                                                                                                portStatus
                                                                                                                .supportedAltModes =
                                                                                                        (AltModeData
                                                                                                                        [])
                                                                                                                parcel
                                                                                                                        .createTypedArray(
                                                                                                                                AltModeData
                                                                                                                                        .CREATOR);
                                                                                                if (dataPosition
                                                                                                        > Integer
                                                                                                                        .MAX_VALUE
                                                                                                                - readInt) {
                                                                                                    throw new BadParcelableException(
                                                                                                            "Overflow"
                                                                                                                + " in the"
                                                                                                                + " size"
                                                                                                                + " of parcelable");
                                                                                                }
                                                                                            } else if (dataPosition
                                                                                                    > Integer
                                                                                                                    .MAX_VALUE
                                                                                                            - readInt) {
                                                                                                throw new BadParcelableException(
                                                                                                        "Overflow"
                                                                                                            + " in the"
                                                                                                            + " size"
                                                                                                            + " of parcelable");
                                                                                            }
                                                                                        } else if (dataPosition
                                                                                                > Integer
                                                                                                                .MAX_VALUE
                                                                                                        - readInt) {
                                                                                            throw new BadParcelableException(
                                                                                                    "Overflow"
                                                                                                        + " in the"
                                                                                                        + " size"
                                                                                                        + " of parcelable");
                                                                                        }
                                                                                    } else if (dataPosition
                                                                                            > Integer
                                                                                                            .MAX_VALUE
                                                                                                    - readInt) {
                                                                                        throw new BadParcelableException(
                                                                                                "Overflow"
                                                                                                    + " in the"
                                                                                                    + " size"
                                                                                                    + " of parcelable");
                                                                                    }
                                                                                } else if (dataPosition
                                                                                        > Integer
                                                                                                        .MAX_VALUE
                                                                                                - readInt) {
                                                                                    throw new BadParcelableException(
                                                                                            "Overflow"
                                                                                                + " in the"
                                                                                                + " size"
                                                                                                + " of parcelable");
                                                                                }
                                                                            } else if (dataPosition
                                                                                    > Integer
                                                                                                    .MAX_VALUE
                                                                                            - readInt) {
                                                                                throw new BadParcelableException(
                                                                                        "Overflow"
                                                                                            + " in the"
                                                                                            + " size"
                                                                                            + " of parcelable");
                                                                            }
                                                                        } else if (dataPosition
                                                                                > Integer.MAX_VALUE
                                                                                        - readInt) {
                                                                            throw new BadParcelableException(
                                                                                    "Overflow in"
                                                                                        + " the size"
                                                                                        + " of parcelable");
                                                                        }
                                                                    } else if (dataPosition
                                                                            > Integer.MAX_VALUE
                                                                                    - readInt) {
                                                                        throw new BadParcelableException(
                                                                                "Overflow in the"
                                                                                    + " size of"
                                                                                    + " parcelable");
                                                                    }
                                                                } else if (dataPosition
                                                                        > Integer.MAX_VALUE
                                                                                - readInt) {
                                                                    throw new BadParcelableException(
                                                                            "Overflow in the size"
                                                                                + " of parcelable");
                                                                }
                                                            } else if (dataPosition
                                                                    > Integer.MAX_VALUE - readInt) {
                                                                throw new BadParcelableException(
                                                                        "Overflow in the size of"
                                                                            + " parcelable");
                                                            }
                                                        } else if (dataPosition
                                                                > Integer.MAX_VALUE - readInt) {
                                                            throw new BadParcelableException(
                                                                    "Overflow in the size of"
                                                                        + " parcelable");
                                                        }
                                                    } else if (dataPosition
                                                            > Integer.MAX_VALUE - readInt) {
                                                        throw new BadParcelableException(
                                                                "Overflow in the size of"
                                                                    + " parcelable");
                                                    }
                                                } else if (dataPosition
                                                        > Integer.MAX_VALUE - readInt) {
                                                    throw new BadParcelableException(
                                                            "Overflow in the size of parcelable");
                                                }
                                            } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                                throw new BadParcelableException(
                                                        "Overflow in the size of parcelable");
                                            }
                                        } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                            throw new BadParcelableException(
                                                    "Overflow in the size of parcelable");
                                        }
                                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                        throw new BadParcelableException(
                                                "Overflow in the size of parcelable");
                                    }
                                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                    throw new BadParcelableException(
                                            "Overflow in the size of parcelable");
                                }
                            } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                throw new BadParcelableException(
                                        "Overflow in the size of parcelable");
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
                return portStatus;
            } catch (Throwable th) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                throw th;
            }
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new PortStatus[i];
        }
    }

    public static int describeContents(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (!(obj instanceof Object[])) {
            if (obj instanceof Parcelable) {
                return ((Parcelable) obj).describeContents();
            }
            return 0;
        }
        int i = 0;
        for (Object obj2 : (Object[]) obj) {
            i |= describeContents(obj2);
        }
        return i;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return describeContents(this.supportedAltModes);
    }

    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeString(this.portName);
        parcel.writeByte(this.currentDataRole);
        parcel.writeByte(this.currentPowerRole);
        parcel.writeByte(this.currentMode);
        parcel.writeBoolean(this.canChangeMode);
        parcel.writeBoolean(this.canChangeDataRole);
        parcel.writeBoolean(this.canChangePowerRole);
        parcel.writeByteArray(this.supportedModes);
        parcel.writeByteArray(this.supportedContaminantProtectionModes);
        parcel.writeBoolean(this.supportsEnableContaminantPresenceProtection);
        parcel.writeByte(this.contaminantProtectionStatus);
        parcel.writeBoolean(this.supportsEnableContaminantPresenceDetection);
        parcel.writeByte(this.contaminantDetectionStatus);
        parcel.writeByteArray(this.usbDataStatus);
        parcel.writeBoolean(this.powerTransferLimited);
        parcel.writeByte(this.powerBrickStatus);
        parcel.writeBoolean(this.supportsComplianceWarnings);
        parcel.writeIntArray(this.complianceWarnings);
        parcel.writeInt(this.plugOrientation);
        parcel.writeTypedArray(this.supportedAltModes, i);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2 - dataPosition);
        parcel.setDataPosition(dataPosition2);
    }
}
