package android.hardware.power;

import android.hardware.scontext.SContextConstants;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class FrameProducer implements Parcelable {
    public static final Parcelable.Creator<FrameProducer> CREATOR = new Parcelable.Creator<FrameProducer>() { // from class: android.hardware.power.FrameProducer.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrameProducer createFromParcel(Parcel parcel) {
            FrameProducer frameProducer = new FrameProducer();
            frameProducer.readFromParcel(parcel);
            return frameProducer;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrameProducer[] newArray(int i) {
            return new FrameProducer[i];
        }
    };
    public LatchedFrameData currentlyLatchedFrame;
    public long producerId = 0;
    public int uid = 0;
    public double fps = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    public boolean cpuDeadlineMissed = false;
    public boolean gpuDeadlineMissed = false;

    @Override // android.os.Parcelable
    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeLong(this.producerId);
        parcel.writeInt(this.uid);
        parcel.writeDouble(this.fps);
        parcel.writeTypedObject(this.currentlyLatchedFrame, i);
        parcel.writeBoolean(this.cpuDeadlineMissed);
        parcel.writeBoolean(this.gpuDeadlineMissed);
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
                this.producerId = parcel.readLong();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.uid = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.fps = parcel.readDouble();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.currentlyLatchedFrame = (LatchedFrameData) parcel.readTypedObject(LatchedFrameData.CREATOR);
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.cpuDeadlineMissed = parcel.readBoolean();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.gpuDeadlineMissed = parcel.readBoolean();
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
        return describeContents(this.currentlyLatchedFrame);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
