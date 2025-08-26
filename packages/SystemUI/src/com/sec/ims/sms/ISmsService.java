package com.sec.ims.sms;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.sec.ims.sms.ISmsServiceEventListener;

/* loaded from: classes4.dex */
public interface ISmsService extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.sms.ISmsService";

    void deRegisterForSMSStateChange(int i, ISmsServiceEventListener iSmsServiceEventListener) throws RemoteException;

    boolean getSmsFallback(int i) throws RemoteException;

    void registerForSMSStateChange(int i, ISmsServiceEventListener iSmsServiceEventListener) throws RemoteException;

    void sendDeliverReport(int i, byte[] bArr) throws RemoteException;

    void sendSMSOverIMS(int i, byte[] bArr, String str, String str2, int i2) throws RemoteException;

    void sendSMSResponse(boolean z, int i) throws RemoteException;

    public abstract class Stub extends Binder implements ISmsService {
        static final int TRANSACTION_deRegisterForSMSStateChange = 2;
        static final int TRANSACTION_getSmsFallback = 3;
        static final int TRANSACTION_registerForSMSStateChange = 1;
        static final int TRANSACTION_sendDeliverReport = 6;
        static final int TRANSACTION_sendSMSOverIMS = 4;
        static final int TRANSACTION_sendSMSResponse = 5;

        class Proxy implements ISmsService {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.sec.ims.sms.ISmsService
            public void deRegisterForSMSStateChange(int i, ISmsServiceEventListener iSmsServiceEventListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISmsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iSmsServiceEventListener);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return ISmsService.DESCRIPTOR;
            }

            @Override // com.sec.ims.sms.ISmsService
            public boolean getSmsFallback(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISmsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.sms.ISmsService
            public void registerForSMSStateChange(int i, ISmsServiceEventListener iSmsServiceEventListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISmsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iSmsServiceEventListener);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.sms.ISmsService
            public void sendDeliverReport(int i, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISmsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.sms.ISmsService
            public void sendSMSOverIMS(int i, byte[] bArr, String str, String str2, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISmsService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.ims.sms.ISmsService
            public void sendSMSResponse(boolean z, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISmsService.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, ISmsService.DESCRIPTOR);
        }

        public static ISmsService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISmsService.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof ISmsService)) ? new Proxy(iBinder) : (ISmsService) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISmsService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISmsService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int i3 = parcel.readInt();
                    ISmsServiceEventListener iSmsServiceEventListenerAsInterface = ISmsServiceEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerForSMSStateChange(i3, iSmsServiceEventListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int i4 = parcel.readInt();
                    ISmsServiceEventListener iSmsServiceEventListenerAsInterface2 = ISmsServiceEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    deRegisterForSMSStateChange(i4, iSmsServiceEventListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean smsFallback = getSmsFallback(i5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(smsFallback);
                    return true;
                case 4:
                    int i6 = parcel.readInt();
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    String string = parcel.readString();
                    String string2 = parcel.readString();
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendSMSOverIMS(i6, bArrCreateByteArray, string, string2, i7);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    boolean z = parcel.readBoolean();
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    sendSMSResponse(z, i8);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int i9 = parcel.readInt();
                    byte[] bArrCreateByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    sendDeliverReport(i9, bArrCreateByteArray2);
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

    public class Default implements ISmsService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.sms.ISmsService
        public boolean getSmsFallback(int i) throws RemoteException {
            return false;
        }

        @Override // com.sec.ims.sms.ISmsService
        public void deRegisterForSMSStateChange(int i, ISmsServiceEventListener iSmsServiceEventListener) throws RemoteException {
        }

        @Override // com.sec.ims.sms.ISmsService
        public void registerForSMSStateChange(int i, ISmsServiceEventListener iSmsServiceEventListener) throws RemoteException {
        }

        @Override // com.sec.ims.sms.ISmsService
        public void sendDeliverReport(int i, byte[] bArr) throws RemoteException {
        }

        @Override // com.sec.ims.sms.ISmsService
        public void sendSMSResponse(boolean z, int i) throws RemoteException {
        }

        @Override // com.sec.ims.sms.ISmsService
        public void sendSMSOverIMS(int i, byte[] bArr, String str, String str2, int i2) throws RemoteException {
        }
    }
}
