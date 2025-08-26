package android.hardware.biometrics.face;

import android.hardware.biometrics.common.CommonProps;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class SensorProps implements Parcelable {
    public static final Parcelable.Creator<SensorProps> CREATOR = new Parcelable.Creator<SensorProps>() { // from class: android.hardware.biometrics.face.SensorProps.1
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
    public byte sensorType = 0;
    public boolean halControlsPreview = false;
    public int previewDisplayId = 0;
    public int enrollPreviewWidth = 0;
    public int enrollPreviewHeight = 0;
    public float enrollTranslationX = 0.0f;
    public float enrollTranslationY = 0.0f;
    public float enrollPreviewScale = 0.0f;
    public boolean supportsDetectInteraction = false;

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
        parcel.writeBoolean(this.halControlsPreview);
        parcel.writeInt(this.previewDisplayId);
        parcel.writeInt(this.enrollPreviewWidth);
        parcel.writeInt(this.enrollPreviewHeight);
        parcel.writeFloat(this.enrollTranslationX);
        parcel.writeFloat(this.enrollTranslationY);
        parcel.writeFloat(this.enrollPreviewScale);
        parcel.writeBoolean(this.supportsDetectInteraction);
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
                        this.halControlsPreview = parcel.readBoolean();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.previewDisplayId = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.enrollPreviewWidth = parcel.readInt();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.enrollPreviewHeight = parcel.readInt();
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.enrollTranslationX = parcel.readFloat();
                                        if (parcel.dataPosition() - iDataPosition < i) {
                                            this.enrollTranslationY = parcel.readFloat();
                                            if (parcel.dataPosition() - iDataPosition < i) {
                                                this.enrollPreviewScale = parcel.readFloat();
                                                if (parcel.dataPosition() - iDataPosition < i) {
                                                    this.supportsDetectInteraction = parcel.readBoolean();
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
        return describeContents(this.commonProps);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
