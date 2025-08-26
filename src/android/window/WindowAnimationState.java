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
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeLong(this.timestamp);
        parcel.writeTypedObject(this.bounds, i);
        parcel.writeFloat(this.scale);
        parcel.writeFloat(this.topLeftRadius);
        parcel.writeFloat(this.topRightRadius);
        parcel.writeFloat(this.bottomRightRadius);
        parcel.writeFloat(this.bottomLeftRadius);
        parcel.writeTypedObject(this.velocityPxPerMs, i);
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
                this.timestamp = parcel.readLong();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.bounds = (RectF) parcel.readTypedObject(RectF.CREATOR);
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.scale = parcel.readFloat();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.topLeftRadius = parcel.readFloat();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.topRightRadius = parcel.readFloat();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.bottomRightRadius = parcel.readFloat();
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.bottomLeftRadius = parcel.readFloat();
                                        if (parcel.dataPosition() - iDataPosition < i) {
                                            this.velocityPxPerMs = (PointF) parcel.readTypedObject(PointF.CREATOR);
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
        return describeContents(this.velocityPxPerMs) | describeContents(this.bounds);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
