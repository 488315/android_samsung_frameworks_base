package com.samsung.android.oneconnect.mediaoutput;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.oneconnect.mediaoutput.deviceoperations.IDeviceStatusChangeCallback;
import com.samsung.android.oneconnect.mediaoutput.entity.Device;
import com.samsung.android.oneconnect.mediaoutput.entity.MediaOutputDevice;
import com.samsung.android.oneconnect.mediaoutput.entity.MediaOutputDeviceV2;
import com.samsung.android.oneconnect.mediaoutput.mediadeviceoperations.IMediaContentChangeCallback;
import com.samsung.android.oneconnect.mediaoutput.mediadeviceoperations.IMediaDeviceControlCallback;
import com.samsung.android.oneconnect.mediaoutput.mediadeviceoperations.IMediaOutputSelectedCallback;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface IMediaOutputService extends IInterface {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class _Parcel {
        /* renamed from: -$$Nest$smwriteTypedList, reason: not valid java name */
        public static void m3291$$Nest$smwriteTypedList(Parcel parcel, List list) {
            if (list == null) {
                parcel.writeInt(-1);
                return;
            }
            int size = list.size();
            parcel.writeInt(size);
            for (int i = 0; i < size; i++) {
                writeTypedObject(parcel, (Parcelable) list.get(i));
            }
        }

        public static void writeTypedObject(Parcel parcel, Parcelable parcelable) {
            if (parcelable == null) {
                parcel.writeInt(0);
            } else {
                parcel.writeInt(1);
                parcelable.writeToParcel(parcel, 1);
            }
        }
    }

    void addContentChangeCallback(IMediaContentChangeCallback iMediaContentChangeCallback);

    void addDeviceStatusChangeCallback(IDeviceStatusChangeCallback iDeviceStatusChangeCallback);

    void addMediaDeviceControlCallback();

    void addMediaOutputSelectedCallback(IMediaOutputSelectedCallback iMediaOutputSelectedCallback);

    MediaOutputDevice getCurrentMediaOutput(String str);

    MediaOutputDeviceV2 getCurrentMediaOutputV2(String str);

    List getDevices();

    String getMediaContent(String str);

    List getMediaOutputDevices(String str);

    List getMediaOutputDevicesV2(String str);

    boolean isMuted();

    boolean isNearby();

    boolean isPpAgreed();

    boolean isSupported(String str);

    void launchRemoteControlPlugIn(String str);

    void removeContentChangeCallback(IMediaContentChangeCallback iMediaContentChangeCallback);

    void removeDeviceStatusChangeCallback(IDeviceStatusChangeCallback iDeviceStatusChangeCallback);

    void removeMediaDeviceControlCallback();

    void removeMediaOutputSelectedCallback(IMediaOutputSelectedCallback iMediaOutputSelectedCallback);

    void selectMediaOutput(String str, String str2);

    void setMute(String str, boolean z);

    void setPlayback(String str);

    void startCloudSync();

    void stopCloudSync();

    void updateVolume(int i, String str);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Stub extends Binder implements IMediaOutputService {
        public static final /* synthetic */ int $r8$clinit = 0;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public class Proxy implements IMediaOutputService {
            public final IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.oneconnect.mediaoutput.IMediaOutputService
            public final void addContentChangeCallback(IMediaContentChangeCallback iMediaContentChangeCallback) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.samsung.android.oneconnect.mediaoutput.IMediaOutputService");
                    obtain.writeStrongInterface(iMediaContentChangeCallback);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.oneconnect.mediaoutput.IMediaOutputService
            public final void addDeviceStatusChangeCallback(IDeviceStatusChangeCallback iDeviceStatusChangeCallback) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.samsung.android.oneconnect.mediaoutput.IMediaOutputService");
                    obtain.writeStrongInterface(iDeviceStatusChangeCallback);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.oneconnect.mediaoutput.IMediaOutputService
            public final void addMediaOutputSelectedCallback(IMediaOutputSelectedCallback iMediaOutputSelectedCallback) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.samsung.android.oneconnect.mediaoutput.IMediaOutputService");
                    obtain.writeStrongInterface(iMediaOutputSelectedCallback);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.oneconnect.mediaoutput.IMediaOutputService
            public final MediaOutputDevice getCurrentMediaOutput(String str) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.samsung.android.oneconnect.mediaoutput.IMediaOutputService");
                    obtain.writeString(str);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? MediaOutputDevice.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.oneconnect.mediaoutput.IMediaOutputService
            public final MediaOutputDeviceV2 getCurrentMediaOutputV2(String str) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.samsung.android.oneconnect.mediaoutput.IMediaOutputService");
                    obtain.writeString(str);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0 ? MediaOutputDeviceV2.CREATOR.createFromParcel(obtain2) : null;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.oneconnect.mediaoutput.IMediaOutputService
            public final List getDevices() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.samsung.android.oneconnect.mediaoutput.IMediaOutputService");
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(Device.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.oneconnect.mediaoutput.IMediaOutputService
            public final String getMediaContent(String str) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.samsung.android.oneconnect.mediaoutput.IMediaOutputService");
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.oneconnect.mediaoutput.IMediaOutputService
            public final List getMediaOutputDevices(String str) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.samsung.android.oneconnect.mediaoutput.IMediaOutputService");
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(MediaOutputDevice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.oneconnect.mediaoutput.IMediaOutputService
            public final List getMediaOutputDevicesV2(String str) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.samsung.android.oneconnect.mediaoutput.IMediaOutputService");
                    obtain.writeString(str);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(MediaOutputDeviceV2.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.oneconnect.mediaoutput.IMediaOutputService
            public final boolean isSupported(String str) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.samsung.android.oneconnect.mediaoutput.IMediaOutputService");
                    obtain.writeString(str);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.oneconnect.mediaoutput.IMediaOutputService
            public final void launchRemoteControlPlugIn(String str) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.samsung.android.oneconnect.mediaoutput.IMediaOutputService");
                    obtain.writeString(str);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.oneconnect.mediaoutput.IMediaOutputService
            public final void removeContentChangeCallback(IMediaContentChangeCallback iMediaContentChangeCallback) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.samsung.android.oneconnect.mediaoutput.IMediaOutputService");
                    obtain.writeStrongInterface(iMediaContentChangeCallback);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.oneconnect.mediaoutput.IMediaOutputService
            public final void removeDeviceStatusChangeCallback(IDeviceStatusChangeCallback iDeviceStatusChangeCallback) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.samsung.android.oneconnect.mediaoutput.IMediaOutputService");
                    obtain.writeStrongInterface(iDeviceStatusChangeCallback);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.oneconnect.mediaoutput.IMediaOutputService
            public final void removeMediaOutputSelectedCallback(IMediaOutputSelectedCallback iMediaOutputSelectedCallback) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.samsung.android.oneconnect.mediaoutput.IMediaOutputService");
                    obtain.writeStrongInterface(iMediaOutputSelectedCallback);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.oneconnect.mediaoutput.IMediaOutputService
            public final void selectMediaOutput(String str, String str2) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.samsung.android.oneconnect.mediaoutput.IMediaOutputService");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.oneconnect.mediaoutput.IMediaOutputService
            public final void setMute(String str, boolean z) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.samsung.android.oneconnect.mediaoutput.IMediaOutputService");
                    obtain.writeString(str);
                    obtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.oneconnect.mediaoutput.IMediaOutputService
            public final void setPlayback(String str) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.samsung.android.oneconnect.mediaoutput.IMediaOutputService");
                    obtain.writeString(str);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.oneconnect.mediaoutput.IMediaOutputService
            public final void startCloudSync() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.samsung.android.oneconnect.mediaoutput.IMediaOutputService");
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.oneconnect.mediaoutput.IMediaOutputService
            public final void stopCloudSync() {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.samsung.android.oneconnect.mediaoutput.IMediaOutputService");
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.oneconnect.mediaoutput.IMediaOutputService
            public final void updateVolume(int i, String str) {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.samsung.android.oneconnect.mediaoutput.IMediaOutputService");
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, "com.samsung.android.oneconnect.mediaoutput.IMediaOutputService");
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.samsung.android.oneconnect.mediaoutput.IMediaOutputService");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.samsung.android.oneconnect.mediaoutput.IMediaOutputService");
                return true;
            }
            IDeviceStatusChangeCallback iDeviceStatusChangeCallback = null;
            IMediaOutputSelectedCallback iMediaOutputSelectedCallback = null;
            IMediaOutputSelectedCallback iMediaOutputSelectedCallback2 = null;
            IMediaContentChangeCallback iMediaContentChangeCallback = null;
            IMediaContentChangeCallback iMediaContentChangeCallback2 = null;
            IDeviceStatusChangeCallback iDeviceStatusChangeCallback2 = null;
            switch (i) {
                case 1:
                    List devices = getDevices();
                    parcel2.writeNoException();
                    _Parcel.m3291$$Nest$smwriteTypedList(parcel2, devices);
                    return true;
                case 2:
                    List mediaOutputDevices = getMediaOutputDevices(parcel.readString());
                    parcel2.writeNoException();
                    _Parcel.m3291$$Nest$smwriteTypedList(parcel2, mediaOutputDevices);
                    return true;
                case 3:
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    if (readStrongBinder != null) {
                        IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.samsung.android.oneconnect.mediaoutput.deviceoperations.IDeviceStatusChangeCallback");
                        iDeviceStatusChangeCallback = (queryLocalInterface == null || !(queryLocalInterface instanceof IDeviceStatusChangeCallback)) ? new IDeviceStatusChangeCallback.Stub.Proxy(readStrongBinder) : (IDeviceStatusChangeCallback) queryLocalInterface;
                    }
                    addDeviceStatusChangeCallback(iDeviceStatusChangeCallback);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    if (readStrongBinder2 != null) {
                        IInterface queryLocalInterface2 = readStrongBinder2.queryLocalInterface("com.samsung.android.oneconnect.mediaoutput.deviceoperations.IDeviceStatusChangeCallback");
                        iDeviceStatusChangeCallback2 = (queryLocalInterface2 == null || !(queryLocalInterface2 instanceof IDeviceStatusChangeCallback)) ? new IDeviceStatusChangeCallback.Stub.Proxy(readStrongBinder2) : (IDeviceStatusChangeCallback) queryLocalInterface2;
                    }
                    removeDeviceStatusChangeCallback(iDeviceStatusChangeCallback2);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    parcel.readString();
                    boolean isNearby = isNearby();
                    parcel2.writeNoException();
                    parcel2.writeInt(isNearby ? 1 : 0);
                    return true;
                case 6:
                    String mediaContent = getMediaContent(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeString(mediaContent);
                    return true;
                case 7:
                    IBinder readStrongBinder3 = parcel.readStrongBinder();
                    if (readStrongBinder3 != null) {
                        IInterface queryLocalInterface3 = readStrongBinder3.queryLocalInterface("com.samsung.android.oneconnect.mediaoutput.mediadeviceoperations.IMediaContentChangeCallback");
                        iMediaContentChangeCallback2 = (queryLocalInterface3 == null || !(queryLocalInterface3 instanceof IMediaContentChangeCallback)) ? new IMediaContentChangeCallback.Stub.Proxy(readStrongBinder3) : (IMediaContentChangeCallback) queryLocalInterface3;
                    }
                    addContentChangeCallback(iMediaContentChangeCallback2);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    IBinder readStrongBinder4 = parcel.readStrongBinder();
                    if (readStrongBinder4 != null) {
                        IInterface queryLocalInterface4 = readStrongBinder4.queryLocalInterface("com.samsung.android.oneconnect.mediaoutput.mediadeviceoperations.IMediaContentChangeCallback");
                        iMediaContentChangeCallback = (queryLocalInterface4 == null || !(queryLocalInterface4 instanceof IMediaContentChangeCallback)) ? new IMediaContentChangeCallback.Stub.Proxy(readStrongBinder4) : (IMediaContentChangeCallback) queryLocalInterface4;
                    }
                    removeContentChangeCallback(iMediaContentChangeCallback);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    MediaOutputDevice currentMediaOutput = getCurrentMediaOutput(parcel.readString());
                    parcel2.writeNoException();
                    _Parcel.writeTypedObject(parcel2, currentMediaOutput);
                    return true;
                case 10:
                    selectMediaOutput(parcel.readString(), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 11:
                    IBinder readStrongBinder5 = parcel.readStrongBinder();
                    if (readStrongBinder5 != null) {
                        IInterface queryLocalInterface5 = readStrongBinder5.queryLocalInterface("com.samsung.android.oneconnect.mediaoutput.mediadeviceoperations.IMediaOutputSelectedCallback");
                        iMediaOutputSelectedCallback2 = (queryLocalInterface5 == null || !(queryLocalInterface5 instanceof IMediaOutputSelectedCallback)) ? new IMediaOutputSelectedCallback.Stub.Proxy(readStrongBinder5) : (IMediaOutputSelectedCallback) queryLocalInterface5;
                    }
                    addMediaOutputSelectedCallback(iMediaOutputSelectedCallback2);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    IBinder readStrongBinder6 = parcel.readStrongBinder();
                    if (readStrongBinder6 != null) {
                        IInterface queryLocalInterface6 = readStrongBinder6.queryLocalInterface("com.samsung.android.oneconnect.mediaoutput.mediadeviceoperations.IMediaOutputSelectedCallback");
                        iMediaOutputSelectedCallback = (queryLocalInterface6 == null || !(queryLocalInterface6 instanceof IMediaOutputSelectedCallback)) ? new IMediaOutputSelectedCallback.Stub.Proxy(readStrongBinder6) : (IMediaOutputSelectedCallback) queryLocalInterface6;
                    }
                    removeMediaOutputSelectedCallback(iMediaOutputSelectedCallback);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    updateVolume(parcel.readInt(), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 14:
                    setMute(parcel.readString(), parcel.readInt() != 0);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    parcel.readString();
                    boolean isMuted = isMuted();
                    parcel2.writeNoException();
                    parcel2.writeInt(isMuted ? 1 : 0);
                    return true;
                case 16:
                    IBinder readStrongBinder7 = parcel.readStrongBinder();
                    if (readStrongBinder7 != null) {
                        IInterface queryLocalInterface7 = readStrongBinder7.queryLocalInterface("com.samsung.android.oneconnect.mediaoutput.mediadeviceoperations.IMediaDeviceControlCallback");
                        if (queryLocalInterface7 == null || !(queryLocalInterface7 instanceof IMediaDeviceControlCallback)) {
                            new IMediaDeviceControlCallback.Stub.Proxy(readStrongBinder7);
                        }
                    }
                    addMediaDeviceControlCallback();
                    parcel2.writeNoException();
                    return true;
                case 17:
                    IBinder readStrongBinder8 = parcel.readStrongBinder();
                    if (readStrongBinder8 != null) {
                        IInterface queryLocalInterface8 = readStrongBinder8.queryLocalInterface("com.samsung.android.oneconnect.mediaoutput.mediadeviceoperations.IMediaDeviceControlCallback");
                        if (queryLocalInterface8 == null || !(queryLocalInterface8 instanceof IMediaDeviceControlCallback)) {
                            new IMediaDeviceControlCallback.Stub.Proxy(readStrongBinder8);
                        }
                    }
                    removeMediaDeviceControlCallback();
                    parcel2.writeNoException();
                    return true;
                case 18:
                    launchRemoteControlPlugIn(parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 19:
                    boolean isPpAgreed = isPpAgreed();
                    parcel2.writeNoException();
                    parcel2.writeInt(isPpAgreed ? 1 : 0);
                    return true;
                case 20:
                    setPlayback(parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 21:
                    List mediaOutputDevicesV2 = getMediaOutputDevicesV2(parcel.readString());
                    parcel2.writeNoException();
                    _Parcel.m3291$$Nest$smwriteTypedList(parcel2, mediaOutputDevicesV2);
                    return true;
                case 22:
                    MediaOutputDeviceV2 currentMediaOutputV2 = getCurrentMediaOutputV2(parcel.readString());
                    parcel2.writeNoException();
                    _Parcel.writeTypedObject(parcel2, currentMediaOutputV2);
                    return true;
                case 23:
                    boolean isSupported = isSupported(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(isSupported ? 1 : 0);
                    return true;
                case 24:
                    startCloudSync();
                    parcel2.writeNoException();
                    return true;
                case 25:
                    stopCloudSync();
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}
