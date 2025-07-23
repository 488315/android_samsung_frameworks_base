package android.hardware.biometrics.fingerprint;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;
import java.util.Objects;

/* loaded from: classes2.dex */
public class PointerContext implements Parcelable {
    public static final Parcelable.Creator<PointerContext> CREATOR = new Parcelable.Creator<PointerContext>() { // from class: android.hardware.biometrics.fingerprint.PointerContext.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PointerContext createFromParcel(Parcel parcel) {
            PointerContext pointerContext = new PointerContext();
            pointerContext.readFromParcel(parcel);
            return pointerContext;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PointerContext[] newArray(int i) {
            return new PointerContext[i];
        }
    };
    public int pointerId = -1;
    public float x = 0.0f;
    public float y = 0.0f;
    public float minor = 0.0f;
    public float major = 0.0f;
    public float orientation = 0.0f;
    public boolean isAod = false;
    public long time = 0;
    public long gestureStart = 0;

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
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.pointerId);
        parcel.writeFloat(this.x);
        parcel.writeFloat(this.y);
        parcel.writeFloat(this.minor);
        parcel.writeFloat(this.major);
        parcel.writeFloat(this.orientation);
        parcel.writeBoolean(this.isAod);
        parcel.writeLong(this.time);
        parcel.writeLong(this.gestureStart);
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
                this.pointerId = parcel.readInt();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.x = parcel.readFloat();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.y = parcel.readFloat();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.minor = parcel.readFloat();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.major = parcel.readFloat();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.orientation = parcel.readFloat();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.isAod = parcel.readBoolean();
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            this.time = parcel.readLong();
                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                this.gestureStart = parcel.readLong();
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
            parcel.setDataPosition(dataPosition + readInt);
        } catch (Throwable th) {
            if (dataPosition > Integer.MAX_VALUE - readInt) {
                throw new BadParcelableException("Overflow in the size of parcelable");
            }
            parcel.setDataPosition(dataPosition + readInt);
            throw th;
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof PointerContext)) {
            return false;
        }
        PointerContext pointerContext = (PointerContext) obj;
        return Objects.deepEquals(Integer.valueOf(this.pointerId), Integer.valueOf(pointerContext.pointerId)) && Objects.deepEquals(Float.valueOf(this.x), Float.valueOf(pointerContext.x)) && Objects.deepEquals(Float.valueOf(this.y), Float.valueOf(pointerContext.y)) && Objects.deepEquals(Float.valueOf(this.minor), Float.valueOf(pointerContext.minor)) && Objects.deepEquals(Float.valueOf(this.major), Float.valueOf(pointerContext.major)) && Objects.deepEquals(Float.valueOf(this.orientation), Float.valueOf(pointerContext.orientation)) && Objects.deepEquals(Boolean.valueOf(this.isAod), Boolean.valueOf(pointerContext.isAod)) && Objects.deepEquals(Long.valueOf(this.time), Long.valueOf(pointerContext.time)) && Objects.deepEquals(Long.valueOf(this.gestureStart), Long.valueOf(pointerContext.gestureStart));
    }

    public int hashCode() {
        return Arrays.deepHashCode(Arrays.asList(Integer.valueOf(this.pointerId), Float.valueOf(this.x), Float.valueOf(this.y), Float.valueOf(this.minor), Float.valueOf(this.major), Float.valueOf(this.orientation), Boolean.valueOf(this.isAod), Long.valueOf(this.time), Long.valueOf(this.gestureStart)).toArray());
    }
}
