package com.sec.ims.options;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.sec.ims.options.ICapabilityServiceEventListener;
import com.sec.ims.util.ImsUri;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface ICapabilityService extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.options.ICapabilityService";

    void addFakeCapabilityInfo(List<ImsUri> list, boolean z, int i) throws RemoteException;

    void deRegisterService(List<String> list) throws RemoteException;

    Capabilities[] getAllCapabilities(int i) throws RemoteException;

    Capabilities getCapabilities(ImsUri imsUri, int i, int i2) throws RemoteException;

    Capabilities[] getCapabilitiesByContactId(String str, int i, int i2) throws RemoteException;

    Capabilities getCapabilitiesById(int i, int i2) throws RemoteException;

    Capabilities getCapabilitiesByNumber(String str, int i, int i2) throws RemoteException;

    Capabilities getCapabilitiesWithDelay(String str, int i, int i2) throws RemoteException;

    Capabilities getCapabilitiesWithFeature(String str, int i, int i2) throws RemoteException;

    Capabilities[] getCapabilitiesWithFeatureByUriList(List<ImsUri> list, int i, int i2, int i3) throws RemoteException;

    Capabilities getOwnCapabilities(int i) throws RemoteException;

    boolean isOwnInfoPublished() throws RemoteException;

    String registerListener(ICapabilityServiceEventListener iCapabilityServiceEventListener, int i) throws RemoteException;

    void registerListenerWithToken(ICapabilityServiceEventListener iCapabilityServiceEventListener, String str, int i) throws RemoteException;

    void registerService(String str, String str2) throws RemoteException;

    void setUserActivity(boolean z, int i) throws RemoteException;

    void unregisterListener(String str, int i) throws RemoteException;

    public abstract class Stub extends Binder implements ICapabilityService {
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

        class Proxy implements ICapabilityService {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.sec.ims.options.ICapabilityService
            public void addFakeCapabilityInfo(List<ImsUri> list, boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICapabilityService.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.sec.ims.options.ICapabilityService
            public void deRegisterService(List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICapabilityService.DESCRIPTOR);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.options.ICapabilityService
            public Capabilities[] getAllCapabilities(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICapabilityService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Capabilities[]) parcelObtain2.createTypedArray(Capabilities.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.options.ICapabilityService
            public Capabilities getCapabilities(ImsUri imsUri, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICapabilityService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(imsUri, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Capabilities) parcelObtain2.readTypedObject(Capabilities.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.options.ICapabilityService
            public Capabilities[] getCapabilitiesByContactId(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICapabilityService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Capabilities[]) parcelObtain2.createTypedArray(Capabilities.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.options.ICapabilityService
            public Capabilities getCapabilitiesById(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICapabilityService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Capabilities) parcelObtain2.readTypedObject(Capabilities.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.options.ICapabilityService
            public Capabilities getCapabilitiesByNumber(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICapabilityService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Capabilities) parcelObtain2.readTypedObject(Capabilities.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.options.ICapabilityService
            public Capabilities getCapabilitiesWithDelay(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICapabilityService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Capabilities) parcelObtain2.readTypedObject(Capabilities.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.options.ICapabilityService
            public Capabilities getCapabilitiesWithFeature(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICapabilityService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Capabilities) parcelObtain2.readTypedObject(Capabilities.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.options.ICapabilityService
            public Capabilities[] getCapabilitiesWithFeatureByUriList(List<ImsUri> list, int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICapabilityService.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Capabilities[]) parcelObtain2.createTypedArray(Capabilities.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return ICapabilityService.DESCRIPTOR;
            }

            @Override // com.sec.ims.options.ICapabilityService
            public Capabilities getOwnCapabilities(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICapabilityService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Capabilities) parcelObtain2.readTypedObject(Capabilities.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.options.ICapabilityService
            public boolean isOwnInfoPublished() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICapabilityService.DESCRIPTOR);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.options.ICapabilityService
            public String registerListener(ICapabilityServiceEventListener iCapabilityServiceEventListener, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICapabilityService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iCapabilityServiceEventListener);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.options.ICapabilityService
            public void registerListenerWithToken(ICapabilityServiceEventListener iCapabilityServiceEventListener, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICapabilityService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iCapabilityServiceEventListener);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.options.ICapabilityService
            public void registerService(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICapabilityService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.options.ICapabilityService
            public void setUserActivity(boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICapabilityService.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.options.ICapabilityService
            public void unregisterListener(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICapabilityService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ICapabilityService.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof ICapabilityService)) ? new Proxy(iBinder) : (ICapabilityService) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ICapabilityService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ICapabilityService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Capabilities ownCapabilities = getOwnCapabilities(i3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(ownCapabilities, 1);
                    return true;
                case 2:
                    ImsUri imsUri = (ImsUri) parcel.readTypedObject(ImsUri.CREATOR);
                    int i4 = parcel.readInt();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Capabilities capabilities = getCapabilities(imsUri, i4, i5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(capabilities, 1);
                    return true;
                case 3:
                    int i6 = parcel.readInt();
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Capabilities capabilitiesById = getCapabilitiesById(i6, i7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(capabilitiesById, 1);
                    return true;
                case 4:
                    String string = parcel.readString();
                    int i8 = parcel.readInt();
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Capabilities capabilitiesByNumber = getCapabilitiesByNumber(string, i8, i9);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(capabilitiesByNumber, 1);
                    return true;
                case 5:
                    String string2 = parcel.readString();
                    int i10 = parcel.readInt();
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Capabilities capabilitiesWithDelay = getCapabilitiesWithDelay(string2, i10, i11);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(capabilitiesWithDelay, 1);
                    return true;
                case 6:
                    String string3 = parcel.readString();
                    int i12 = parcel.readInt();
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Capabilities capabilitiesWithFeature = getCapabilitiesWithFeature(string3, i12, i13);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(capabilitiesWithFeature, 1);
                    return true;
                case 7:
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(ImsUri.CREATOR);
                    int i14 = parcel.readInt();
                    int i15 = parcel.readInt();
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Capabilities[] capabilitiesWithFeatureByUriList = getCapabilitiesWithFeatureByUriList(arrayListCreateTypedArrayList, i14, i15, i16);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(capabilitiesWithFeatureByUriList, 1);
                    return true;
                case 8:
                    String string4 = parcel.readString();
                    int i17 = parcel.readInt();
                    int i18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Capabilities[] capabilitiesByContactId = getCapabilitiesByContactId(string4, i17, i18);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(capabilitiesByContactId, 1);
                    return true;
                case 9:
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    Capabilities[] allCapabilities = getAllCapabilities(i19);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(allCapabilities, 1);
                    return true;
                case 10:
                    ICapabilityServiceEventListener iCapabilityServiceEventListenerAsInterface = ICapabilityServiceEventListener.Stub.asInterface(parcel.readStrongBinder());
                    int i20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String strRegisterListener = registerListener(iCapabilityServiceEventListenerAsInterface, i20);
                    parcel2.writeNoException();
                    parcel2.writeString(strRegisterListener);
                    return true;
                case 11:
                    ICapabilityServiceEventListener iCapabilityServiceEventListenerAsInterface2 = ICapabilityServiceEventListener.Stub.asInterface(parcel.readStrongBinder());
                    String string5 = parcel.readString();
                    int i21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerListenerWithToken(iCapabilityServiceEventListenerAsInterface2, string5, i21);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    String string6 = parcel.readString();
                    int i22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterListener(string6, i22);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    ArrayList arrayListCreateTypedArrayList2 = parcel.createTypedArrayList(ImsUri.CREATOR);
                    boolean z = parcel.readBoolean();
                    int i23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    addFakeCapabilityInfo(arrayListCreateTypedArrayList2, z, i23);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    boolean z2 = parcel.readBoolean();
                    int i24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setUserActivity(z2, i24);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    boolean zIsOwnInfoPublished = isOwnInfoPublished();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsOwnInfoPublished);
                    return true;
                case 16:
                    String string7 = parcel.readString();
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    registerService(string7, string8);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    deRegisterService(arrayListCreateStringArrayList);
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

    public class Default implements ICapabilityService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.options.ICapabilityService
        public Capabilities[] getAllCapabilities(int i) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.options.ICapabilityService
        public Capabilities getCapabilities(ImsUri imsUri, int i, int i2) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.options.ICapabilityService
        public Capabilities[] getCapabilitiesByContactId(String str, int i, int i2) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.options.ICapabilityService
        public Capabilities getCapabilitiesById(int i, int i2) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.options.ICapabilityService
        public Capabilities getCapabilitiesByNumber(String str, int i, int i2) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.options.ICapabilityService
        public Capabilities getCapabilitiesWithDelay(String str, int i, int i2) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.options.ICapabilityService
        public Capabilities getCapabilitiesWithFeature(String str, int i, int i2) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.options.ICapabilityService
        public Capabilities[] getCapabilitiesWithFeatureByUriList(List<ImsUri> list, int i, int i2, int i3) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.options.ICapabilityService
        public Capabilities getOwnCapabilities(int i) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.options.ICapabilityService
        public boolean isOwnInfoPublished() throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.options.ICapabilityService
        public String registerListener(ICapabilityServiceEventListener iCapabilityServiceEventListener, int i) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.options.ICapabilityService
        public void deRegisterService(List<String> list) throws RemoteException {
        }

        @Override // com.sec.ims.options.ICapabilityService
        public void registerService(String str, String str2) throws RemoteException {
        }

        @Override // com.sec.ims.options.ICapabilityService
        public void setUserActivity(boolean z, int i) throws RemoteException {
        }

        @Override // com.sec.ims.options.ICapabilityService
        public void unregisterListener(String str, int i) throws RemoteException {
        }

        @Override // com.sec.ims.options.ICapabilityService
        public void addFakeCapabilityInfo(List<ImsUri> list, boolean z, int i) throws RemoteException {
        }

        @Override // com.sec.ims.options.ICapabilityService
        public void registerListenerWithToken(ICapabilityServiceEventListener iCapabilityServiceEventListener, String str, int i) throws RemoteException {
        }
    }
}
