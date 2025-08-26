package android.hardware.biometrics.fingerprint;

import android.hardware.biometrics.common.CommonProps;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class SensorProps implements Parcelable {
    public static final Parcelable.Creator<SensorProps> CREATOR = new Parcelable.Creator<SensorProps>() { // from class: android.hardware.biometrics.fingerprint.SensorProps.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SensorProps createFromParcel(Parcel parcel) {
            SensorProps sensorProps = new SensorProps();
            sensorProps.readFromParcel(parcel);
            return sensorProps;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SensorProps[] newArray(int i) {
            return new SensorProps[i];
        }
    };
    public CommonProps commonProps;
    public SensorLocation[] sensorLocations;
    public TouchDetectionParameters touchDetectionParameters;
    public byte sensorType = 0;
    public boolean supportsNavigationGestures = false;
    public boolean supportsDetectInteraction = false;
    public boolean halHandlesDisplayTouches = false;
    public boolean halControlsIllumination = false;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.commonProps, i);
        parcel.writeByte(this.sensorType);
        parcel.writeTypedArray(this.sensorLocations, i);
        parcel.writeBoolean(this.supportsNavigationGestures);
        parcel.writeBoolean(this.supportsDetectInteraction);
        parcel.writeBoolean(this.halHandlesDisplayTouches);
        parcel.writeBoolean(this.halControlsIllumination);
        parcel.writeTypedObject(this.touchDetectionParameters, i);
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
                this.commonProps = (CommonProps) parcel.readTypedObject(CommonProps.CREATOR);
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.sensorType = parcel.readByte();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.sensorLocations = (SensorLocation[]) parcel.createTypedArray(SensorLocation.CREATOR);
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.supportsNavigationGestures = parcel.readBoolean();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.supportsDetectInteraction = parcel.readBoolean();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.halHandlesDisplayTouches = parcel.readBoolean();
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.halControlsIllumination = parcel.readBoolean();
                                        if (parcel.dataPosition() - iDataPosition < i) {
                                            this.touchDetectionParameters = (TouchDetectionParameters) parcel.readTypedObject(TouchDetectionParameters.CREATOR);
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
            parcel.setDataPosition(iDataPosition + i);
        } catch (Throwable th) {
            if (iDataPosition > Integer.MAX_VALUE - i) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(iDataPosition + i);
            throw th;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return describeContents(this.touchDetectionParameters) | describeContents(this.commonProps) | describeContents(this.sensorLocations);
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
