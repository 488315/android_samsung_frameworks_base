package com.sec.ims.openapi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.sec.ims.IDialogEventListener;
import com.sec.ims.IImsRegistrationListener;
import com.sec.ims.openapi.ISipDialogListener;
import com.sec.ims.util.ImsUri;
import com.sec.ims.volte2.IImsCallEventListener;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface IOpenApiService extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.openapi.IOpenApiService";

    void registerDialogEventListener(IDialogEventListener iDialogEventListener);

    void registerImsCallEventListener(IImsCallEventListener iImsCallEventListener);

    void registerImsRegistrationListener(IImsRegistrationListener iImsRegistrationListener);

    void registerIncomingSipMessageListener(ISipDialogListener iSipDialogListener);

    boolean sendSip(ImsUri imsUri, String str, ISipDialogListener iSipDialogListener);

    void setFeatureTags(String[] strArr);

    void setupMediaPath(String[] strArr);

    void unregisterDialogEventListener(IDialogEventListener iDialogEventListener);

    void unregisterImsCallEventListener(IImsCallEventListener iImsCallEventListener);

    void unregisterImsRegistrationListener(IImsRegistrationListener iImsRegistrationListener);

    void unregisterIncomingSipMessageListener(ISipDialogListener iSipDialogListener);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IOpenApiService {
        static final int TRANSACTION_registerDialogEventListener = 6;
        static final int TRANSACTION_registerImsCallEventListener = 4;
        static final int TRANSACTION_registerImsRegistrationListener = 8;
        static final int TRANSACTION_registerIncomingSipMessageListener = 2;
        static final int TRANSACTION_sendSip = 10;
        static final int TRANSACTION_setFeatureTags = 1;
        static final int TRANSACTION_setupMediaPath = 11;
        static final int TRANSACTION_unregisterDialogEventListener = 7;
        static final int TRANSACTION_unregisterImsCallEventListener = 5;
        static final int TRANSACTION_unregisterImsRegistrationListener = 9;
        static final int TRANSACTION_unregisterIncomingSipMessageListener = 3;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IOpenApiService {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IOpenApiService.DESCRIPTOR;
            }

            @Override // com.sec.ims.openapi.IOpenApiService
            public void registerDialogEventListener(IDialogEventListener iDialogEventListener) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IOpenApiService.DESCRIPTOR);
                    obtain.writeStrongInterface(iDialogEventListener);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.openapi.IOpenApiService
            public void registerImsCallEventListener(IImsCallEventListener iImsCallEventListener) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IOpenApiService.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallEventListener);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.openapi.IOpenApiService
            public void registerImsRegistrationListener(IImsRegistrationListener iImsRegistrationListener) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IOpenApiService.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsRegistrationListener);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.openapi.IOpenApiService
            public void registerIncomingSipMessageListener(ISipDialogListener iSipDialogListener) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IOpenApiService.DESCRIPTOR);
                    obtain.writeStrongInterface(iSipDialogListener);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.openapi.IOpenApiService
            public boolean sendSip(ImsUri imsUri, String str, ISipDialogListener iSipDialogListener) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IOpenApiService.DESCRIPTOR);
                    obtain.writeTypedObject(imsUri, 0);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iSipDialogListener);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.openapi.IOpenApiService
            public void setFeatureTags(String[] strArr) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IOpenApiService.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.openapi.IOpenApiService
            public void setupMediaPath(String[] strArr) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IOpenApiService.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.openapi.IOpenApiService
            public void unregisterDialogEventListener(IDialogEventListener iDialogEventListener) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IOpenApiService.DESCRIPTOR);
                    obtain.writeStrongInterface(iDialogEventListener);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.openapi.IOpenApiService
            public void unregisterImsCallEventListener(IImsCallEventListener iImsCallEventListener) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IOpenApiService.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCallEventListener);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.openapi.IOpenApiService
            public void unregisterImsRegistrationListener(IImsRegistrationListener iImsRegistrationListener) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IOpenApiService.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsRegistrationListener);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.ims.openapi.IOpenApiService
            public void unregisterIncomingSipMessageListener(ISipDialogListener iSipDialogListener) {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IOpenApiService.DESCRIPTOR);
                    obtain.writeStrongInterface(iSipDialogListener);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IOpenApiService.DESCRIPTOR);
        }

        public static IOpenApiService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IOpenApiService.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IOpenApiService)) ? new Proxy(iBinder) : (IOpenApiService) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IOpenApiService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IOpenApiService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String[] createStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    setFeatureTags(createStringArray);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    ISipDialogListener asInterface = ISipDialogListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerIncomingSipMessageListener(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    ISipDialogListener asInterface2 = ISipDialogListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterIncomingSipMessageListener(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    IImsCallEventListener asInterface3 = IImsCallEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerImsCallEventListener(asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    IImsCallEventListener asInterface4 = IImsCallEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterImsCallEventListener(asInterface4);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    IDialogEventListener asInterface5 = IDialogEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerDialogEventListener(asInterface5);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    IDialogEventListener asInterface6 = IDialogEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterDialogEventListener(asInterface6);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    IImsRegistrationListener asInterface7 = IImsRegistrationListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerImsRegistrationListener(asInterface7);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    IImsRegistrationListener asInterface8 = IImsRegistrationListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterImsRegistrationListener(asInterface8);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    ImsUri imsUri = (ImsUri) parcel.readTypedObject(ImsUri.CREATOR);
                    String readString = parcel.readString();
                    ISipDialogListener asInterface9 = ISipDialogListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean sendSip = sendSip(imsUri, readString, asInterface9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sendSip);
                    return true;
                case 11:
                    String[] createStringArray2 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    setupMediaPath(createStringArray2);
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
    public static class Default implements IOpenApiService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.openapi.IOpenApiService
        public boolean sendSip(ImsUri imsUri, String str, ISipDialogListener iSipDialogListener) {
            return false;
        }

        @Override // com.sec.ims.openapi.IOpenApiService
        public void registerDialogEventListener(IDialogEventListener iDialogEventListener) {
        }

        @Override // com.sec.ims.openapi.IOpenApiService
        public void registerImsCallEventListener(IImsCallEventListener iImsCallEventListener) {
        }

        @Override // com.sec.ims.openapi.IOpenApiService
        public void registerImsRegistrationListener(IImsRegistrationListener iImsRegistrationListener) {
        }

        @Override // com.sec.ims.openapi.IOpenApiService
        public void registerIncomingSipMessageListener(ISipDialogListener iSipDialogListener) {
        }

        @Override // com.sec.ims.openapi.IOpenApiService
        public void setFeatureTags(String[] strArr) {
        }

        @Override // com.sec.ims.openapi.IOpenApiService
        public void setupMediaPath(String[] strArr) {
        }

        @Override // com.sec.ims.openapi.IOpenApiService
        public void unregisterDialogEventListener(IDialogEventListener iDialogEventListener) {
        }

        @Override // com.sec.ims.openapi.IOpenApiService
        public void unregisterImsCallEventListener(IImsCallEventListener iImsCallEventListener) {
        }

        @Override // com.sec.ims.openapi.IOpenApiService
        public void unregisterImsRegistrationListener(IImsRegistrationListener iImsRegistrationListener) {
        }

        @Override // com.sec.ims.openapi.IOpenApiService
        public void unregisterIncomingSipMessageListener(ISipDialogListener iSipDialogListener) {
        }
    }
}
