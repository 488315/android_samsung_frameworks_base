package com.sec.ims.options;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.sec.ims.options.ICapabilityServiceEventListener;
import com.sec.ims.util.ImsUri;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface ICapabilityService extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.options.ICapabilityService";

    void addFakeCapabilityInfo(List<ImsUri> list, boolean z, int i);

    void deRegisterService(List<String> list);

    Capabilities[] getAllCapabilities(int i);

    Capabilities getCapabilities(ImsUri imsUri, int i, int i2);

    Capabilities[] getCapabilitiesByContactId(String str, int i, int i2);

    Capabilities getCapabilitiesById(int i, int i2);

    Capabilities getCapabilitiesByNumber(String str, int i, int i2);

    Capabilities getCapabilitiesWithDelay(String str, int i, int i2);

    Capabilities getCapabilitiesWithFeature(String str, int i, int i2);

    Capabilities[] getCapabilitiesWithFeatureByUriList(List<ImsUri> list, int i, int i2, int i3);

    Capabilities getOwnCapabilities(int i);

    boolean isOwnInfoPublished();

    String registerListener(ICapabilityServiceEventListener iCapabilityServiceEventListener, int i);

    void registerListenerWithToken(ICapabilityServiceEventListener iCapabilityServiceEventListener, String str, int i);

    void registerService(String str, String str2);

    void setUserActivity(boolean z, int i);

    void unregisterListener(String str, int i);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements ICapabilityService {
        static final int TRANSACTION_addFakeCapabilityInfo = 13;
        static final int TRANSACTION_deRegisterService = 17;
        static final int TRANSACTION_getAllCapabilities = 9;
        static final int TRANSACTION_getCapabilities = 2;
        static final int TRANSACTION_getCapabilitiesByContactId = 8;
        static final int TRANSACTION_getCapabilitiesById = 3;
        static final int TRANSACTION_getCapabilitiesByNumber = 4;
        static final int TRANSACTION_getCapabilitiesWithDelay = 5;
        static final int TRANSACTION_getCapabilitiesWithFeature = 6;
        static final int TRANSACTION_getCapabilitiesWithFeatureByUriList = 7;
        static final int TRANSACTION_getOwnCapabilities = 1;
        static final int TRANSACTION_isOwnInfoPublished = 15;
        static final int TRANSACTION_registerListener = 10;
        static final int TRANSACTION_registerListenerWithToken = 11;
        static final int TRANSACTION_registerService = 16;
        static final int TRANSACTION_setUserActivity = 14;
        static final int TRANSACTION_unregisterListener = 12;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements ICapabilityService {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.sec.ims.options.ICapabilityService
            public void addFakeCapabilityInfo(List<ImsUri> list, boolean z, int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICapabilityService.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.sec.ims.options.ICapabilityService
            public void deRegisterService(List<String> list) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICapabilityService.DESCRIPTOR);
                    obtain.writeStringList(list);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.options.ICapabilityService
            public Capabilities[] getAllCapabilities(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICapabilityService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Capabilities[]) obtain2.createTypedArray(Capabilities.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.options.ICapabilityService
            public Capabilities getCapabilities(ImsUri imsUri, int i, int i2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICapabilityService.DESCRIPTOR);
                    obtain.writeTypedObject(imsUri, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Capabilities) obtain2.readTypedObject(Capabilities.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.options.ICapabilityService
            public Capabilities[] getCapabilitiesByContactId(String str, int i, int i2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICapabilityService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Capabilities[]) obtain2.createTypedArray(Capabilities.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.options.ICapabilityService
            public Capabilities getCapabilitiesById(int i, int i2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICapabilityService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Capabilities) obtain2.readTypedObject(Capabilities.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.options.ICapabilityService
            public Capabilities getCapabilitiesByNumber(String str, int i, int i2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICapabilityService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Capabilities) obtain2.readTypedObject(Capabilities.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.options.ICapabilityService
            public Capabilities getCapabilitiesWithDelay(String str, int i, int i2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICapabilityService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Capabilities) obtain2.readTypedObject(Capabilities.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.options.ICapabilityService
            public Capabilities getCapabilitiesWithFeature(String str, int i, int i2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICapabilityService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Capabilities) obtain2.readTypedObject(Capabilities.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.options.ICapabilityService
            public Capabilities[] getCapabilitiesWithFeatureByUriList(List<ImsUri> list, int i, int i2, int i3) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICapabilityService.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Capabilities[]) obtain2.createTypedArray(Capabilities.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return ICapabilityService.DESCRIPTOR;
            }

            @Override // com.sec.ims.options.ICapabilityService
            public Capabilities getOwnCapabilities(int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICapabilityService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Capabilities) obtain2.readTypedObject(Capabilities.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.options.ICapabilityService
            public boolean isOwnInfoPublished() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICapabilityService.DESCRIPTOR);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.options.ICapabilityService
            public String registerListener(ICapabilityServiceEventListener iCapabilityServiceEventListener, int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICapabilityService.DESCRIPTOR);
                    obtain.writeStrongInterface(iCapabilityServiceEventListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.options.ICapabilityService
            public void registerListenerWithToken(ICapabilityServiceEventListener iCapabilityServiceEventListener, String str, int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICapabilityService.DESCRIPTOR);
                    obtain.writeStrongInterface(iCapabilityServiceEventListener);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.options.ICapabilityService
            public void registerService(String str, String str2) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICapabilityService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.options.ICapabilityService
            public void setUserActivity(boolean z, int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICapabilityService.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.options.ICapabilityService
            public void unregisterListener(String str, int i) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICapabilityService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, ICapabilityService.DESCRIPTOR);
        }

        public static ICapabilityService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ICapabilityService.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof ICapabilityService)) ? new Proxy(iBinder) : (ICapabilityService) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ICapabilityService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ICapabilityService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Capabilities ownCapabilities = getOwnCapabilities(readInt);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(ownCapabilities, 1);
                    return true;
                case 2:
                    ImsUri imsUri = (ImsUri) parcel.readTypedObject(ImsUri.CREATOR);
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Capabilities capabilities = getCapabilities(imsUri, readInt2, readInt3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(capabilities, 1);
                    return true;
                case 3:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Capabilities capabilitiesById = getCapabilitiesById(readInt4, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(capabilitiesById, 1);
                    return true;
                case 4:
                    String readString = parcel.readString();
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Capabilities capabilitiesByNumber = getCapabilitiesByNumber(readString, readInt6, readInt7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(capabilitiesByNumber, 1);
                    return true;
                case 5:
                    String readString2 = parcel.readString();
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Capabilities capabilitiesWithDelay = getCapabilitiesWithDelay(readString2, readInt8, readInt9);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(capabilitiesWithDelay, 1);
                    return true;
                case 6:
                    String readString3 = parcel.readString();
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Capabilities capabilitiesWithFeature = getCapabilitiesWithFeature(readString3, readInt10, readInt11);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(capabilitiesWithFeature, 1);
                    return true;
                case 7:
                    ArrayList createTypedArrayList = parcel.createTypedArrayList(ImsUri.CREATOR);
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Capabilities[] capabilitiesWithFeatureByUriList = getCapabilitiesWithFeatureByUriList(createTypedArrayList, readInt12, readInt13, readInt14);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(capabilitiesWithFeatureByUriList, 1);
                    return true;
                case 8:
                    String readString4 = parcel.readString();
                    int readInt15 = parcel.readInt();
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Capabilities[] capabilitiesByContactId = getCapabilitiesByContactId(readString4, readInt15, readInt16);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(capabilitiesByContactId, 1);
                    return true;
                case 9:
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Capabilities[] allCapabilities = getAllCapabilities(readInt17);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(allCapabilities, 1);
                    return true;
                case 10:
                    ICapabilityServiceEventListener asInterface = ICapabilityServiceEventListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String registerListener = registerListener(asInterface, readInt18);
                    parcel2.writeNoException();
                    parcel2.writeString(registerListener);
                    return true;
                case 11:
                    ICapabilityServiceEventListener asInterface2 = ICapabilityServiceEventListener.Stub.asInterface(parcel.readStrongBinder());
                    String readString5 = parcel.readString();
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerListenerWithToken(asInterface2, readString5, readInt19);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    String readString6 = parcel.readString();
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterListener(readString6, readInt20);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    ArrayList createTypedArrayList2 = parcel.createTypedArrayList(ImsUri.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addFakeCapabilityInfo(createTypedArrayList2, readBoolean, readInt21);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    boolean readBoolean2 = parcel.readBoolean();
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setUserActivity(readBoolean2, readInt22);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    boolean isOwnInfoPublished = isOwnInfoPublished();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isOwnInfoPublished);
                    return true;
                case 16:
                    String readString7 = parcel.readString();
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    registerService(readString7, readString8);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    deRegisterService(createStringArrayList);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements ICapabilityService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.options.ICapabilityService
        public Capabilities[] getAllCapabilities(int i) {
            return null;
        }

        @Override // com.sec.ims.options.ICapabilityService
        public Capabilities getCapabilities(ImsUri imsUri, int i, int i2) {
            return null;
        }

        @Override // com.sec.ims.options.ICapabilityService
        public Capabilities[] getCapabilitiesByContactId(String str, int i, int i2) {
            return null;
        }

        @Override // com.sec.ims.options.ICapabilityService
        public Capabilities getCapabilitiesById(int i, int i2) {
            return null;
        }

        @Override // com.sec.ims.options.ICapabilityService
        public Capabilities getCapabilitiesByNumber(String str, int i, int i2) {
            return null;
        }

        @Override // com.sec.ims.options.ICapabilityService
        public Capabilities getCapabilitiesWithDelay(String str, int i, int i2) {
            return null;
        }

        @Override // com.sec.ims.options.ICapabilityService
        public Capabilities getCapabilitiesWithFeature(String str, int i, int i2) {
            return null;
        }

        @Override // com.sec.ims.options.ICapabilityService
        public Capabilities[] getCapabilitiesWithFeatureByUriList(List<ImsUri> list, int i, int i2, int i3) {
            return null;
        }

        @Override // com.sec.ims.options.ICapabilityService
        public Capabilities getOwnCapabilities(int i) {
            return null;
        }

        @Override // com.sec.ims.options.ICapabilityService
        public boolean isOwnInfoPublished() {
            return false;
        }

        @Override // com.sec.ims.options.ICapabilityService
        public String registerListener(ICapabilityServiceEventListener iCapabilityServiceEventListener, int i) {
            return null;
        }

        @Override // com.sec.ims.options.ICapabilityService
        public void deRegisterService(List<String> list) {
        }

        @Override // com.sec.ims.options.ICapabilityService
        public void registerService(String str, String str2) {
        }

        @Override // com.sec.ims.options.ICapabilityService
        public void setUserActivity(boolean z, int i) {
        }

        @Override // com.sec.ims.options.ICapabilityService
        public void unregisterListener(String str, int i) {
        }

        @Override // com.sec.ims.options.ICapabilityService
        public void addFakeCapabilityInfo(List<ImsUri> list, boolean z, int i) {
        }

        @Override // com.sec.ims.options.ICapabilityService
        public void registerListenerWithToken(ICapabilityServiceEventListener iCapabilityServiceEventListener, String str, int i) {
        }
    }
}
