package com.sec.ims.presence;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.sec.ims.util.ImsUri;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface IPresenceService extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.presence.IPresenceService";

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements IPresenceService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.presence.IPresenceService
        public PresenceInfo getOwnPresenceInfo() {
            return null;
        }

        @Override // com.sec.ims.presence.IPresenceService
        public PresenceInfo getPresenceInfo(ImsUri imsUri) {
            return null;
        }

        @Override // com.sec.ims.presence.IPresenceService
        public PresenceInfo getPresenceInfoByContactId(String str) {
            return null;
        }
    }

    PresenceInfo getOwnPresenceInfo();

    PresenceInfo getPresenceInfo(ImsUri imsUri);

    PresenceInfo getPresenceInfoByContactId(String str);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IPresenceService {
        static final int TRANSACTION_getOwnPresenceInfo = 1;
        static final int TRANSACTION_getPresenceInfo = 2;
        static final int TRANSACTION_getPresenceInfoByContactId = 3;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IPresenceService {
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
            public PresenceInfo getOwnPresenceInfo() {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPresenceService.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (PresenceInfo) obtain2.readTypedObject(PresenceInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.presence.IPresenceService
            public PresenceInfo getPresenceInfo(ImsUri imsUri) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPresenceService.DESCRIPTOR);
                    obtain.writeTypedObject(imsUri, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (PresenceInfo) obtain2.readTypedObject(PresenceInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.presence.IPresenceService
            public PresenceInfo getPresenceInfoByContactId(String str) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IPresenceService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (PresenceInfo) obtain2.readTypedObject(PresenceInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IPresenceService.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IPresenceService)) ? new Proxy(iBinder) : (IPresenceService) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
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
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                PresenceInfo presenceInfoByContactId = getPresenceInfoByContactId(readString);
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
