package android.hardware.camera2.extension;

import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.Surface;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class CameraOutputConfig implements Parcelable {
    public static final Parcelable.Creator<CameraOutputConfig> CREATOR = new Parcelable.Creator<CameraOutputConfig>() { // from class: android.hardware.camera2.extension.CameraOutputConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CameraOutputConfig createFromParcel(Parcel parcel) {
            CameraOutputConfig cameraOutputConfig = new CameraOutputConfig();
            cameraOutputConfig.readFromParcel(parcel);
            return cameraOutputConfig;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CameraOutputConfig[] newArray(int i) {
            return new CameraOutputConfig[i];
        }
    };
    public static final int TYPE_IMAGEREADER = 1;
    public static final int TYPE_MULTIRES_IMAGEREADER = 2;
    public static final int TYPE_SURFACE = 0;
    public OutputConfigId outputId;
    public String physicalCameraId;
    public List<CameraOutputConfig> sharedSurfaceConfigs;
    public Size size;
    public Surface surface;
    public int imageFormat = 0;
    public int capacity = 0;
    public long usage = 0;
    public long dynamicRangeProfile = 0;
    public int type = 0;
    public int surfaceGroupId = 0;
    public boolean isMultiResolutionOutput = false;

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedObject(this.size, i);
        parcel.writeTypedObject(this.surface, i);
        parcel.writeInt(this.imageFormat);
        parcel.writeInt(this.capacity);
        parcel.writeLong(this.usage);
        parcel.writeLong(this.dynamicRangeProfile);
        parcel.writeInt(this.type);
        parcel.writeTypedObject(this.outputId, i);
        parcel.writeInt(this.surfaceGroupId);
        parcel.writeString(this.physicalCameraId);
        parcel.writeTypedList(this.sharedSurfaceConfigs, i);
        parcel.writeBoolean(this.isMultiResolutionOutput);
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
                this.size = (Size) parcel.readTypedObject(Size.CREATOR);
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.surface = (Surface) parcel.readTypedObject(Surface.CREATOR);
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.imageFormat = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.capacity = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.usage = parcel.readLong();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.dynamicRangeProfile = parcel.readLong();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.type = parcel.readInt();
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            this.outputId = (OutputConfigId) parcel.readTypedObject(OutputConfigId.CREATOR);
                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                this.surfaceGroupId = parcel.readInt();
                                                if (parcel.dataPosition() - dataPosition < readInt) {
                                                    this.physicalCameraId = parcel.readString();
                                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                                        this.sharedSurfaceConfigs = parcel.createTypedArrayList(CREATOR);
                                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                                            this.isMultiResolutionOutput = parcel.readBoolean();
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
        return describeContents(this.sharedSurfaceConfigs) | describeContents(this.size) | describeContents(this.surface) | describeContents(this.outputId);
    }

    private int describeContents(Object obj) {
        int i = 0;
        if (obj == null) {
            return 0;
        }
        if (obj instanceof Collection) {
            Iterator it = ((Collection) obj).iterator();
            while (it.hasNext()) {
                i |= describeContents(it.next());
            }
            return i;
        }
        if (obj instanceof Parcelable) {
            return ((Parcelable) obj).describeContents();
        }
        return 0;
    }
}
