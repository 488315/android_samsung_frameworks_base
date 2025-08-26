package com.sec.ims.presence;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.sec.ims.util.ImsUri;

/* loaded from: classes4.dex */
public interface IPresenceService extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.presence.IPresenceService";

    public class Default implements IPresenceService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.presence.IPresenceService
        public PresenceInfo getOwnPresenceInfo() throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.presence.IPresenceService
        public PresenceInfo getPresenceInfo(ImsUri imsUri) throws RemoteException {
            return null;
        }

        @Override // com.sec.ims.presence.IPresenceService
        public PresenceInfo getPresenceInfoByContactId(String str) throws RemoteException {
            return null;
        }
    }

    PresenceInfo getOwnPresenceInfo() throws RemoteException;

    PresenceInfo getPresenceInfo(ImsUri imsUri) throws RemoteException;

    PresenceInfo getPresenceInfoByContactId(String str) throws RemoteException;

    public abstract class Stub extends Binder implements IPresenceService {
        static final int TRANSACTION_getOwnPresenceInfo = 1;
        static final int TRANSACTION_getPresenceInfo = 2;
        static final int TRANSACTION_getPresenceInfoByContactId = 3;

        class Proxy implements IPresenceService {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IPresenceService.DESCRIPTOR;
            }

            @Override // com.sec.ims.presence.IPresenceService
            public PresenceInfo getOwnPresenceInfo() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPresenceService.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (PresenceInfo) parcelObtain2.readTypedObject(PresenceInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.presence.IPresenceService
            public PresenceInfo getPresenceInfo(ImsUri imsUri) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPresenceService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(imsUri, 0);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (PresenceInfo) parcelObtain2.readTypedObject(PresenceInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.presence.IPresenceService
            public PresenceInfo getPresenceInfoByContactId(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IPresenceService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (PresenceInfo) parcelObtain2.readTypedObject(PresenceInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IPresenceService.DESCRIPTOR);
        }

        public static IPresenceService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IPresenceService.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IPresenceService)) ? new Proxy(iBinder) : (IPresenceService) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IPresenceService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IPresenceService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                PresenceInfo ownPresenceInfo = getOwnPresenceInfo();
                parcel2.writeNoException();
                parcel2.writeTypedObject(ownPresenceInfo, 1);
            } else if (i == 2) {
                ImsUri imsUri = (ImsUri) parcel.readTypedObject(ImsUri.CREATOR);
                parcel.enforceNoDataAvail();
                PresenceInfo presenceInfo = getPresenceInfo(imsUri);
                parcel2.writeNoException();
                parcel2.writeTypedObject(presenceInfo, 1);
            } else {
                if (i != 3) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                String string = parcel.readString();
                parcel.enforceNoDataAvail();
                PresenceInfo presenceInfoByContactId = getPresenceInfoByContactId(string);
                parcel2.writeNoException();
                parcel2.writeTypedObject(presenceInfoByContactId, 1);
            }
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }
}
