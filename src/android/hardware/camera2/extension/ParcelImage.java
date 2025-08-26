package android.hardware.camera2.extension;

import android.graphics.Rect;
import android.hardware.HardwareBuffer;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class ParcelImage implements Parcelable {
    public static final Parcelable.Creator<ParcelImage> CREATOR = new Parcelable.Creator<ParcelImage>() { // from class: android.hardware.camera2.extension.ParcelImage.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ParcelImage createFromParcel(Parcel parcel) {
            ParcelImage parcelImage = new ParcelImage();
            parcelImage.readFromParcel(parcel);
            return parcelImage;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ParcelImage[] newArray(int i) {
            return new ParcelImage[i];
        }
    };
    public HardwareBuffer buffer;
    public Rect crop;
    public ParcelFileDescriptor fence;
    public int format = 0;
    public int width = 0;
    public int height = 0;
    public int transform = 0;
    public int scalingMode = 0;
    public long timestamp = 0;
    public int planeCount = 0;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iDataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeInt(this.format);
        parcel.writeInt(this.width);
        parcel.writeInt(this.height);
        parcel.writeInt(this.transform);
        parcel.writeInt(this.scalingMode);
        parcel.writeLong(this.timestamp);
        parcel.writeInt(this.planeCount);
        parcel.writeTypedObject(this.crop, i);
        parcel.writeTypedObject(this.buffer, i);
        parcel.writeTypedObject(this.fence, i);
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
                this.format = parcel.readInt();
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.width = parcel.readInt();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.height = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.transform = parcel.readInt();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.scalingMode = parcel.readInt();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.timestamp = parcel.readLong();
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.planeCount = parcel.readInt();
                                        if (parcel.dataPosition() - iDataPosition < i) {
                                            this.crop = (Rect) parcel.readTypedObject(Rect.CREATOR);
                                            if (parcel.dataPosition() - iDataPosition < i) {
                                                this.buffer = (HardwareBuffer) parcel.readTypedObject(HardwareBuffer.CREATOR);
                                                if (parcel.dataPosition() - iDataPosition < i) {
                                                    this.fence = (ParcelFileDescriptor) parcel.readTypedObject(ParcelFileDescriptor.CREATOR);
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
        return describeContents(this.fence) | describeContents(this.crop) | describeContents(this.buffer);
    }

    private int describeContents(Object obj) {
        if (obj != null && (obj instanceof Parcelable)) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
