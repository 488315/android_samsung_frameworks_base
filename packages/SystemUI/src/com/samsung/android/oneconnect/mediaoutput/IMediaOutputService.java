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

/* loaded from: classes4.dex */
public interface IMediaOutputService extends IInterface {

    public class _Parcel {
        /* renamed from: -$$Nest$smwriteTypedList, reason: not valid java name */
        public static void m3308$$Nest$smwriteTypedList(Parcel parcel, List list) {
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

    public abstract class Stub extends Binder implements IMediaOutputService {
        public static final /* synthetic */ int $r8$clinit = 0;

        public class Proxy implements IMediaOutputService {
            public final IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.oneconnect.mediaoutput.IMediaOutputService
            public final void addContentChangeCallback(IMediaContentChangeCallback iMediaContentChangeCallback) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.samsung.android.oneconnect.mediaoutput.IMediaOutputService");
                    parcelObtain.writeStrongInterface(iMediaContentChangeCallback);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.oneconnect.mediaoutput.IMediaOutputService
            public final void addDeviceStatusChangeCallback(IDeviceStatusChangeCallback iDeviceStatusChangeCallback) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.samsung.android.oneconnect.mediaoutput.IMediaOutputService");
                    parcelObtain.writeStrongInterface(iDeviceStatusChangeCallback);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.oneconnect.mediaoutput.IMediaOutputService
            public final void addMediaOutputSelectedCallback(IMediaOutputSelectedCallback iMediaOutputSelectedCallback) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.samsung.android.oneconnect.mediaoutput.IMediaOutputService");
                    parcelObtain.writeStrongInterface(iMediaOutputSelectedCallback);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.oneconnect.mediaoutput.IMediaOutputService
            public final MediaOutputDevice getCurrentMediaOutput(String str) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.samsung.android.oneconnect.mediaoutput.IMediaOutputService");
                    parcelObtain.writeString(str);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? MediaOutputDevice.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.oneconnect.mediaoutput.IMediaOutputService
            public final MediaOutputDeviceV2 getCurrentMediaOutputV2(String str) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.samsung.android.oneconnect.mediaoutput.IMediaOutputService");
                    parcelObtain.writeString(str);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? MediaOutputDeviceV2.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.oneconnect.mediaoutput.IMediaOutputService
            public final List getDevices() {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.samsung.android.oneconnect.mediaoutput.IMediaOutputService");
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(Device.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.oneconnect.mediaoutput.IMediaOutputService
            public final String getMediaContent(String str) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.samsung.android.oneconnect.mediaoutput.IMediaOutputService");
                    parcelObtain.writeString(str);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.oneconnect.mediaoutput.IMediaOutputService
            public final List getMediaOutputDevices(String str) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.samsung.android.oneconnect.mediaoutput.IMediaOutputService");
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(MediaOutputDevice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.oneconnect.mediaoutput.IMediaOutputService
            public final List getMediaOutputDevicesV2(String str) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.samsung.android.oneconnect.mediaoutput.IMediaOutputService");
                    parcelObtain.writeString(str);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(MediaOutputDeviceV2.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.oneconnect.mediaoutput.IMediaOutputService
            public final boolean isSupported(String str) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.samsung.android.oneconnect.mediaoutput.IMediaOutputService");
                    parcelObtain.writeString(str);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.oneconnect.mediaoutput.IMediaOutputService
            public final void launchRemoteControlPlugIn(String str) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.samsung.android.oneconnect.mediaoutput.IMediaOutputService");
                    parcelObtain.writeString(str);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.oneconnect.mediaoutput.IMediaOutputService
            public final void removeContentChangeCallback(IMediaContentChangeCallback iMediaContentChangeCallback) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.samsung.android.oneconnect.mediaoutput.IMediaOutputService");
                    parcelObtain.writeStrongInterface(iMediaContentChangeCallback);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.oneconnect.mediaoutput.IMediaOutputService
            public final void removeDeviceStatusChangeCallback(IDeviceStatusChangeCallback iDeviceStatusChangeCallback) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.samsung.android.oneconnect.mediaoutput.IMediaOutputService");
                    parcelObtain.writeStrongInterface(iDeviceStatusChangeCallback);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.oneconnect.mediaoutput.IMediaOutputService
            public final void removeMediaOutputSelectedCallback(IMediaOutputSelectedCallback iMediaOutputSelectedCallback) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.samsung.android.oneconnect.mediaoutput.IMediaOutputService");
                    parcelObtain.writeStrongInterface(iMediaOutputSelectedCallback);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.oneconnect.mediaoutput.IMediaOutputService
            public final void selectMediaOutput(String str, String str2) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.samsung.android.oneconnect.mediaoutput.IMediaOutputService");
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.oneconnect.mediaoutput.IMediaOutputService
            public final void setMute(String str, boolean z) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.samsung.android.oneconnect.mediaoutput.IMediaOutputService");
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.oneconnect.mediaoutput.IMediaOutputService
            public final void setPlayback(String str) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.samsung.android.oneconnect.mediaoutput.IMediaOutputService");
                    parcelObtain.writeString(str);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.oneconnect.mediaoutput.IMediaOutputService
            public final void startCloudSync() {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.samsung.android.oneconnect.mediaoutput.IMediaOutputService");
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.oneconnect.mediaoutput.IMediaOutputService
            public final void stopCloudSync() {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.samsung.android.oneconnect.mediaoutput.IMediaOutputService");
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.oneconnect.mediaoutput.IMediaOutputService
            public final void updateVolume(int i, String str) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.samsung.android.oneconnect.mediaoutput.IMediaOutputService");
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
            IDeviceStatusChangeCallback proxy = null;
            IMediaOutputSelectedCallback proxy2 = null;
            IMediaOutputSelectedCallback proxy3 = null;
            IMediaContentChangeCallback proxy4 = null;
            IMediaContentChangeCallback proxy5 = null;
            IDeviceStatusChangeCallback proxy6 = null;
            switch (i) {
                case 1:
                    List devices = getDevices();
                    parcel2.writeNoException();
                    _Parcel.m3308$$Nest$smwriteTypedList(parcel2, devices);
                    return true;
                case 2:
                    List mediaOutputDevices = getMediaOutputDevices(parcel.readString());
                    parcel2.writeNoException();
                    _Parcel.m3308$$Nest$smwriteTypedList(parcel2, mediaOutputDevices);
                    return true;
                case 3:
                    IBinder strongBinder = parcel.readStrongBinder();
                    if (strongBinder != null) {
                        IInterface iInterfaceQueryLocalInterface = strongBinder.queryLocalInterface("com.samsung.android.oneconnect.mediaoutput.deviceoperations.IDeviceStatusChangeCallback");
                        proxy = (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IDeviceStatusChangeCallback)) ? new IDeviceStatusChangeCallback.Stub.Proxy(strongBinder) : (IDeviceStatusChangeCallback) iInterfaceQueryLocalInterface;
                    }
                    addDeviceStatusChangeCallback(proxy);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    IBinder strongBinder2 = parcel.readStrongBinder();
                    if (strongBinder2 != null) {
                        IInterface iInterfaceQueryLocalInterface2 = strongBinder2.queryLocalInterface("com.samsung.android.oneconnect.mediaoutput.deviceoperations.IDeviceStatusChangeCallback");
                        proxy6 = (iInterfaceQueryLocalInterface2 == null || !(iInterfaceQueryLocalInterface2 instanceof IDeviceStatusChangeCallback)) ? new IDeviceStatusChangeCallback.Stub.Proxy(strongBinder2) : (IDeviceStatusChangeCallback) iInterfaceQueryLocalInterface2;
                    }
                    removeDeviceStatusChangeCallback(proxy6);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    parcel.readString();
                    boolean zIsNearby = isNearby();
                    parcel2.writeNoException();
                    parcel2.writeInt(zIsNearby ? 1 : 0);
                    return true;
                case 6:
                    String mediaContent = getMediaContent(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeString(mediaContent);
                    return true;
                case 7:
                    IBinder strongBinder3 = parcel.readStrongBinder();
                    if (strongBinder3 != null) {
                        IInterface iInterfaceQueryLocalInterface3 = strongBinder3.queryLocalInterface("com.samsung.android.oneconnect.mediaoutput.mediadeviceoperations.IMediaContentChangeCallback");
                        proxy5 = (iInterfaceQueryLocalInterface3 == null || !(iInterfaceQueryLocalInterface3 instanceof IMediaContentChangeCallback)) ? new IMediaContentChangeCallback.Stub.Proxy(strongBinder3) : (IMediaContentChangeCallback) iInterfaceQueryLocalInterface3;
                    }
                    addContentChangeCallback(proxy5);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    IBinder strongBinder4 = parcel.readStrongBinder();
                    if (strongBinder4 != null) {
                        IInterface iInterfaceQueryLocalInterface4 = strongBinder4.queryLocalInterface("com.samsung.android.oneconnect.mediaoutput.mediadeviceoperations.IMediaContentChangeCallback");
                        proxy4 = (iInterfaceQueryLocalInterface4 == null || !(iInterfaceQueryLocalInterface4 instanceof IMediaContentChangeCallback)) ? new IMediaContentChangeCallback.Stub.Proxy(strongBinder4) : (IMediaContentChangeCallback) iInterfaceQueryLocalInterface4;
                    }
                    removeContentChangeCallback(proxy4);
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
                    IBinder strongBinder5 = parcel.readStrongBinder();
                    if (strongBinder5 != null) {
                        IInterface iInterfaceQueryLocalInterface5 = strongBinder5.queryLocalInterface("com.samsung.android.oneconnect.mediaoutput.mediadeviceoperations.IMediaOutputSelectedCallback");
                        proxy3 = (iInterfaceQueryLocalInterface5 == null || !(iInterfaceQueryLocalInterface5 instanceof IMediaOutputSelectedCallback)) ? new IMediaOutputSelectedCallback.Stub.Proxy(strongBinder5) : (IMediaOutputSelectedCallback) iInterfaceQueryLocalInterface5;
                    }
                    addMediaOutputSelectedCallback(proxy3);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    IBinder strongBinder6 = parcel.readStrongBinder();
                    if (strongBinder6 != null) {
                        IInterface iInterfaceQueryLocalInterface6 = strongBinder6.queryLocalInterface("com.samsung.android.oneconnect.mediaoutput.mediadeviceoperations.IMediaOutputSelectedCallback");
                        proxy2 = (iInterfaceQueryLocalInterface6 == null || !(iInterfaceQueryLocalInterface6 instanceof IMediaOutputSelectedCallback)) ? new IMediaOutputSelectedCallback.Stub.Proxy(strongBinder6) : (IMediaOutputSelectedCallback) iInterfaceQueryLocalInterface6;
                    }
                    removeMediaOutputSelectedCallback(proxy2);
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
                    boolean zIsMuted = isMuted();
                    parcel2.writeNoException();
                    parcel2.writeInt(zIsMuted ? 1 : 0);
                    return true;
                case 16:
                    IBinder strongBinder7 = parcel.readStrongBinder();
                    if (strongBinder7 != null) {
                        IInterface iInterfaceQueryLocalInterface7 = strongBinder7.queryLocalInterface("com.samsung.android.oneconnect.mediaoutput.mediadeviceoperations.IMediaDeviceControlCallback");
                        if (iInterfaceQueryLocalInterface7 == null || !(iInterfaceQueryLocalInterface7 instanceof IMediaDeviceControlCallback)) {
                            new IMediaDeviceControlCallback.Stub.Proxy(strongBinder7);
                        }
                    }
                    addMediaDeviceControlCallback();
                    parcel2.writeNoException();
                    return true;
                case 17:
                    IBinder strongBinder8 = parcel.readStrongBinder();
                    if (strongBinder8 != null) {
                        IInterface iInterfaceQueryLocalInterface8 = strongBinder8.queryLocalInterface("com.samsung.android.oneconnect.mediaoutput.mediadeviceoperations.IMediaDeviceControlCallback");
                        if (iInterfaceQueryLocalInterface8 == null || !(iInterfaceQueryLocalInterface8 instanceof IMediaDeviceControlCallback)) {
                            new IMediaDeviceControlCallback.Stub.Proxy(strongBinder8);
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
                    boolean zIsPpAgreed = isPpAgreed();
                    parcel2.writeNoException();
                    parcel2.writeInt(zIsPpAgreed ? 1 : 0);
                    return true;
                case 20:
                    setPlayback(parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 21:
                    List mediaOutputDevicesV2 = getMediaOutputDevicesV2(parcel.readString());
                    parcel2.writeNoException();
                    _Parcel.m3308$$Nest$smwriteTypedList(parcel2, mediaOutputDevicesV2);
                    return true;
                case 22:
                    MediaOutputDeviceV2 currentMediaOutputV2 = getCurrentMediaOutputV2(parcel.readString());
                    parcel2.writeNoException();
                    _Parcel.writeTypedObject(parcel2, currentMediaOutputV2);
                    return true;
                case 23:
                    boolean zIsSupported = isSupported(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(zIsSupported ? 1 : 0);
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
