package android.window;

import android.graphics.PointF;
import android.graphics.RectF;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes5.dex */
public class WindowAnimationState implements Parcelable {
    public static final Parcelable.Creator<WindowAnimationState> CREATOR = new Parcelable.Creator<WindowAnimationState>() { // from class: android.window.WindowAnimationState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WindowAnimationState createFromParcel(Parcel parcel) {
            WindowAnimationState windowAnimationState = new WindowAnimationState();
            windowAnimationState.readFromParcel(parcel);
            return windowAnimationState;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WindowAnimationState[] newArray(int i) {
            return new WindowAnimationState[i];
        }
    };
    public RectF bounds;
    public PointF velocityPxPerMs;
    public long timestamp = 0;
    public float scale = 0.0f;
    public float topLeftRadius = 0.0f;
    public float topRightRadius = 0.0f;
    public float bottomRightRadius = 0.0f;
    public float bottomLeftRadius = 0.0f;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeLong(this.timestamp);
        parcel.writeTypedObject(this.bounds, i);
        parcel.writeFloat(this.scale);
        parcel.writeFloat(this.topLeftRadius);
        parcel.writeFloat(this.topRightRadius);
        parcel.writeFloat(this.bottomRightRadius);
        parcel.writeFloat(this.bottomLeftRadius);
        parcel.writeTypedObject(this.velocityPxPerMs, i);
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
                this.timestamp = parcel.readLong();
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.bounds = (RectF) parcel.readTypedObject(RectF.CREATOR);
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.scale = parcel.readFloat();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.topLeftRadius = parcel.readFloat();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.topRightRadius = parcel.readFloat();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.bottomRightRadius = parcel.readFloat();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.bottomLeftRadius = parcel.readFloat();
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            this.velocityPxPerMs = (PointF) parcel.readTypedObject(PointF.CREATOR);
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
        return describeContents(this.velocityPxPerMs) | describeContents(this.bounds);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
