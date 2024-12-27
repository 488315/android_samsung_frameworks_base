package vendor.samsung.frameworks.codecsolution;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class SehVideoRecordingParameter implements Parcelable {
    public static final Parcelable.Creator<SehVideoRecordingParameter> CREATOR = new Parcelable.Creator<SehVideoRecordingParameter>() { // from class: vendor.samsung.frameworks.codecsolution.SehVideoRecordingParameter.1
        @Override // android.os.Parcelable.Creator
        public SehVideoRecordingParameter createFromParcel(Parcel parcel) {
            SehVideoRecordingParameter sehVideoRecordingParameter = new SehVideoRecordingParameter();
            sehVideoRecordingParameter.readFromParcel(parcel);
            return sehVideoRecordingParameter;
        }

        @Override // android.os.Parcelable.Creator
        public SehVideoRecordingParameter[] newArray(int i) {
            return new SehVideoRecordingParameter[i];
        }
    };
    public int author;
    public int width = 0;
    public int height = 0;
    public int bitrate = 0;
    public int framerate = 0;
    public int intraFrameInterval = 0;
    public int codec = 0;
    public int mode = 0;
    public int componentType = 0;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final int getStability() {
        return 1;
    }

    public final void readFromParcel(Parcel parcel) {
        int dataPosition = parcel.dataPosition();
        int readInt = parcel.readInt();
        try {
            if (readInt < 4) {
                throw new BadParcelableException("Parcelable too small");
            }
            if (parcel.dataPosition() - dataPosition < readInt) {
                this.width = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.height = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.bitrate = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.framerate = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.intraFrameInterval = parcel.readInt();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.codec = parcel.readInt();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.mode = parcel.readInt();
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            this.componentType = parcel.readInt();
                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                this.author = parcel.readInt();
                                                if (dataPosition > Integer.MAX_VALUE - readInt) {
                                                    throw new BadParcelableException("Overflow in the size of parcelable");
                                                }
                                                parcel.setDataPosition(dataPosition + readInt);
                                                return;
                                            }
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
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.width);
        parcel.writeInt(this.height);
        parcel.writeInt(this.bitrate);
        parcel.writeInt(this.framerate);
        parcel.writeInt(this.intraFrameInterval);
        parcel.writeInt(this.codec);
        parcel.writeInt(this.mode);
        parcel.writeInt(this.componentType);
        parcel.writeInt(this.author);
        int dataPosition2 = parcel.dataPosition();
        parcel.setDataPosition(dataPosition);
        parcel.writeInt(dataPosition2 - dataPosition);
        parcel.setDataPosition(dataPosition2);
    }
}
