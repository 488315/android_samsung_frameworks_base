package android.companion.virtualcamera;

import android.os.BadParcelableException;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.server.companion.virtual.camera.VirtualCameraConversionUtil$1;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class VirtualCameraConfiguration implements Parcelable {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1();
    public int lensFacing;
    public int sensorOrientation = 0;
    public SupportedStreamConfiguration[] supportedStreamConfigs;
    public IVirtualCameraCallback virtualCameraCallback;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: android.companion.virtualcamera.VirtualCameraConfiguration$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            IVirtualCameraCallback iVirtualCameraCallback;
            VirtualCameraConfiguration virtualCameraConfiguration =
                    new VirtualCameraConfiguration();
            int dataPosition = parcel.dataPosition();
            int readInt = parcel.readInt();
            try {
                if (readInt < 4) {
                    throw new BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - dataPosition < readInt) {
                    virtualCameraConfiguration.supportedStreamConfigs =
                            (SupportedStreamConfiguration[])
                                    parcel.createTypedArray(SupportedStreamConfiguration.CREATOR);
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        IBinder readStrongBinder = parcel.readStrongBinder();
                        int i = VirtualCameraConversionUtil$1.$r8$clinit;
                        if (readStrongBinder == null) {
                            iVirtualCameraCallback = null;
                        } else {
                            IInterface queryLocalInterface =
                                    readStrongBinder.queryLocalInterface(
                                            "android.companion.virtualcamera.IVirtualCameraCallback");
                            if (queryLocalInterface == null
                                    || !(queryLocalInterface instanceof IVirtualCameraCallback)) {
                                IVirtualCameraCallback$Stub$Proxy
                                        iVirtualCameraCallback$Stub$Proxy =
                                                new IVirtualCameraCallback$Stub$Proxy();
                                iVirtualCameraCallback$Stub$Proxy.mRemote = readStrongBinder;
                                iVirtualCameraCallback = iVirtualCameraCallback$Stub$Proxy;
                            } else {
                                iVirtualCameraCallback =
                                        (IVirtualCameraCallback) queryLocalInterface;
                            }
                        }
                        virtualCameraConfiguration.virtualCameraCallback = iVirtualCameraCallback;
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            virtualCameraConfiguration.sensorOrientation = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                virtualCameraConfiguration.lensFacing = parcel.readInt();
                                if (dataPosition > Integer.MAX_VALUE - readInt) {
                                    throw new BadParcelableException(
                                            "Overflow in the size of parcelable");
                                }
                            } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                throw new BadParcelableException(
                                        "Overflow in the size of parcelable");
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
                return virtualCameraConfiguration;
            } catch (Throwable th) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                throw th;
            }
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new VirtualCameraConfiguration[i];
        }
    }

    public static int describeContents(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (!(obj instanceof Object[])) {
            if (obj instanceof Parcelable) {
                return ((Parcelable) obj).describeContents();
            }
            return 0;
        }
        int i = 0;
        for (Object obj2 : (Object[]) obj) {
            i |= describeContents(obj2);
        }
        return i;
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return describeContents(this.supportedStreamConfigs);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int dataPosition = parcel.dataPosition();
        parcel.writeInt(0);
        parcel.writeTypedArray(this.supportedStreamConfigs, i);
        parcel.writeStrongInterface(this.virtualCameraCallback);
        parcel.writeInt(this.sensorOrientation);
        int m =
                SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(
                        parcel, this.lensFacing, dataPosition);
        SupportedStreamConfiguration$$ExternalSyntheticOutline0.m(m, dataPosition, parcel, m);
    }
}
