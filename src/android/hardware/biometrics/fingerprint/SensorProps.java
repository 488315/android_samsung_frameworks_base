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
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.commonProps, i);
        parcel.writeByte(this.sensorType);
        parcel.writeTypedArray(this.sensorLocations, i);
        parcel.writeBoolean(this.supportsNavigationGestures);
        parcel.writeBoolean(this.supportsDetectInteraction);
        parcel.writeBoolean(this.halHandlesDisplayTouches);
        parcel.writeBoolean(this.halControlsIllumination);
        parcel.writeTypedObject(this.touchDetectionParameters, i);
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
                this.commonProps = (CommonProps) parcel.readTypedObject(CommonProps.CREATOR);
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.sensorType = parcel.readByte();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.sensorLocations = (SensorLocation[]) parcel.createTypedArray(SensorLocation.CREATOR);
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.supportsNavigationGestures = parcel.readBoolean();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.supportsDetectInteraction = parcel.readBoolean();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.halHandlesDisplayTouches = parcel.readBoolean();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.halControlsIllumination = parcel.readBoolean();
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            this.touchDetectionParameters = (TouchDetectionParameters) parcel.readTypedObject(TouchDetectionParameters.CREATOR);
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
            parcel.setDataPosition(dataPosition + readInt);
        } catch (Throwable th) {
            if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
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
