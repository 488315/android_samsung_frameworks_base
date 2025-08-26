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
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeLong(this.producerId);
        parcel.writeInt(this.uid);
        parcel.writeDouble(this.fps);
        parcel.writeTypedObject(this.currentlyLatchedFrame, i);
        parcel.writeBoolean(this.cpuDeadlineMissed);
        parcel.writeBoolean(this.gpuDeadlineMissed);
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
                this.producerId = parcel.readLong();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.uid = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.fps = parcel.readDouble();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.currentlyLatchedFrame = (LatchedFrameData) parcel.readTypedObject(LatchedFrameData.CREATOR);
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.cpuDeadlineMissed = parcel.readBoolean();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.gpuDeadlineMissed = parcel.readBoolean();
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
        return describeContents(this.currentlyLatchedFrame);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
