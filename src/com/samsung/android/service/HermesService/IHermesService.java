package com.samsung.android.service.HermesService;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IHermesService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.service.HermesService.IHermesService";

    public static class Default implements IHermesService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.service.HermesService.IHermesService
        public int getFailureCount(int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.HermesService.IHermesService
        public byte[] hermesCosPatchTest(byte[] bArr) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.service.HermesService.IHermesService
        public byte[] hermesCosUnitTest(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.service.HermesService.IHermesService
        public byte[] hermesGetAppletVersion() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.service.HermesService.IHermesService
        public byte[] hermesGetSeId() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.service.HermesService.IHermesService
        public byte[] hermesGetSecureHWInfo() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.service.HermesService.IHermesService
        public int hermesProvisioning() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.HermesService.IHermesService
        public int hermesSecureHwPowerOff() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.HermesService.IHermesService
        public int hermesSecureHwPowerOn() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.HermesService.IHermesService
        public byte[] hermesSelftest() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.service.HermesService.IHermesService
        public byte[] hermesSelftest2(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.service.HermesService.IHermesService
        public byte[] hermesSendApdu(byte[] bArr) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.service.HermesService.IHermesService
        public int hermesTerminateService() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.service.HermesService.IHermesService
        public byte[] hermesUpdateApplet() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.service.HermesService.IHermesService
        public byte[] hermesUpdateCryptoFW() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.service.HermesService.IHermesService
        public int hermesVerifyProvisioning() throws RemoteException {
            return 0;
        }
    }

    int getFailureCount(int i) throws RemoteException;

    byte[] hermesCosPatchTest(byte[] bArr) throws RemoteException;

    byte[] hermesCosUnitTest(String str) throws RemoteException;

    byte[] hermesGetAppletVersion() throws RemoteException;

    byte[] hermesGetSeId() throws RemoteException;

    byte[] hermesGetSecureHWInfo() throws RemoteException;

    int hermesProvisioning() throws RemoteException;

    int hermesSecureHwPowerOff() throws RemoteException;

    int hermesSecureHwPowerOn() throws RemoteException;

    byte[] hermesSelftest() throws RemoteException;

    byte[] hermesSelftest2(String str) throws RemoteException;

    byte[] hermesSendApdu(byte[] bArr) throws RemoteException;

    int hermesTerminateService() throws RemoteException;

    byte[] hermesUpdateApplet() throws RemoteException;

    byte[] hermesUpdateCryptoFW() throws RemoteException;

    int hermesVerifyProvisioning() throws RemoteException;

    public static abstract class Stub extends Binder implements IHermesService {
        static final int TRANSACTION_getFailureCount = 15;
        static final int TRANSACTION_hermesCosPatchTest = 12;
        static final int TRANSACTION_hermesCosUnitTest = 14;
        static final int TRANSACTION_hermesGetAppletVersion = 16;
        static final int TRANSACTION_hermesGetSeId = 13;
        static final int TRANSACTION_hermesGetSecureHWInfo = 5;
        static final int TRANSACTION_hermesProvisioning = 3;
        static final int TRANSACTION_hermesSecureHwPowerOff = 10;
        static final int TRANSACTION_hermesSecureHwPowerOn = 9;
        static final int TRANSACTION_hermesSelftest = 1;
        static final int TRANSACTION_hermesSelftest2 = 2;
        static final int TRANSACTION_hermesSendApdu = 11;
        static final int TRANSACTION_hermesTerminateService = 6;
        static final int TRANSACTION_hermesUpdateApplet = 8;
        static final int TRANSACTION_hermesUpdateCryptoFW = 7;
        static final int TRANSACTION_hermesVerifyProvisioning = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 15;
        }

        public Stub() {
            attachInterface(this, IHermesService.DESCRIPTOR);
        }

        public static IHermesService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IHermesService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IHermesService)) {
                return (IHermesService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "hermesSelftest";
                case 2:
                    return "hermesSelftest2";
                case 3:
                    return "hermesProvisioning";
                case 4:
                    return "hermesVerifyProvisioning";
                case 5:
                    return "hermesGetSecureHWInfo";
                case 6:
                    return "hermesTerminateService";
                case 7:
                    return "hermesUpdateCryptoFW";
                case 8:
                    return "hermesUpdateApplet";
                case 9:
                    return "hermesSecureHwPowerOn";
                case 10:
                    return "hermesSecureHwPowerOff";
                case 11:
                    return "hermesSendApdu";
                case 12:
                    return "hermesCosPatchTest";
                case 13:
                    return "hermesGetSeId";
                case 14:
                    return "hermesCosUnitTest";
                case 15:
                    return "getFailureCount";
                case 16:
                    return "hermesGetAppletVersion";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IHermesService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IHermesService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    byte[] bArrHermesSelftest = hermesSelftest();
                    parcel2.writeNoException();
                    parcel2.writeByteArray(bArrHermesSelftest);
                    return true;
                case 2:
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    byte[] bArrHermesSelftest2 = hermesSelftest2(string);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(bArrHermesSelftest2);
                    return true;
                case 3:
                    int iHermesProvisioning = hermesProvisioning();
                    parcel2.writeNoException();
                    parcel2.writeInt(iHermesProvisioning);
                    return true;
                case 4:
                    int iHermesVerifyProvisioning = hermesVerifyProvisioning();
                    parcel2.writeNoException();
                    parcel2.writeInt(iHermesVerifyProvisioning);
                    return true;
                case 5:
                    byte[] bArrHermesGetSecureHWInfo = hermesGetSecureHWInfo();
                    parcel2.writeNoException();
                    parcel2.writeByteArray(bArrHermesGetSecureHWInfo);
                    return true;
                case 6:
                    int iHermesTerminateService = hermesTerminateService();
                    parcel2.writeNoException();
                    parcel2.writeInt(iHermesTerminateService);
                    return true;
                case 7:
                    byte[] bArrHermesUpdateCryptoFW = hermesUpdateCryptoFW();
                    parcel2.writeNoException();
                    parcel2.writeByteArray(bArrHermesUpdateCryptoFW);
                    return true;
                case 8:
                    byte[] bArrHermesUpdateApplet = hermesUpdateApplet();
                    parcel2.writeNoException();
                    parcel2.writeByteArray(bArrHermesUpdateApplet);
                    return true;
                case 9:
                    int iHermesSecureHwPowerOn = hermesSecureHwPowerOn();
                    parcel2.writeNoException();
                    parcel2.writeInt(iHermesSecureHwPowerOn);
                    return true;
                case 10:
                    int iHermesSecureHwPowerOff = hermesSecureHwPowerOff();
                    parcel2.writeNoException();
                    parcel2.writeInt(iHermesSecureHwPowerOff);
                    return true;
                case 11:
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    byte[] bArrHermesSendApdu = hermesSendApdu(bArrCreateByteArray);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(bArrHermesSendApdu);
                    return true;
                case 12:
                    byte[] bArrCreateByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    byte[] bArrHermesCosPatchTest = hermesCosPatchTest(bArrCreateByteArray2);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(bArrHermesCosPatchTest);
                    return true;
                case 13:
                    byte[] bArrHermesGetSeId = hermesGetSeId();
                    parcel2.writeNoException();
                    parcel2.writeByteArray(bArrHermesGetSeId);
                    return true;
                case 14:
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    byte[] bArrHermesCosUnitTest = hermesCosUnitTest(string2);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(bArrHermesCosUnitTest);
                    return true;
                case 15:
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int failureCount = getFailureCount(i3);
                    parcel2.writeNoException();
                    parcel2.writeInt(failureCount);
                    return true;
                case 16:
                    byte[] bArrHermesGetAppletVersion = hermesGetAppletVersion();
                    parcel2.writeNoException();
                    parcel2.writeByteArray(bArrHermesGetAppletVersion);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IHermesService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IHermesService.DESCRIPTOR;
            }

            @Override // com.samsung.android.service.HermesService.IHermesService
            public byte[] hermesSelftest() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IHermesService.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.HermesService.IHermesService
            public byte[] hermesSelftest2(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IHermesService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.HermesService.IHermesService
            public int hermesProvisioning() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IHermesService.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.HermesService.IHermesService
            public int hermesVerifyProvisioning() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IHermesService.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.HermesService.IHermesService
            public byte[] hermesGetSecureHWInfo() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IHermesService.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.HermesService.IHermesService
            public int hermesTerminateService() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IHermesService.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.HermesService.IHermesService
            public byte[] hermesUpdateCryptoFW() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IHermesService.DESCRIPTOR);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.HermesService.IHermesService
            public byte[] hermesUpdateApplet() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IHermesService.DESCRIPTOR);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.HermesService.IHermesService
            public int hermesSecureHwPowerOn() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IHermesService.DESCRIPTOR);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.HermesService.IHermesService
            public int hermesSecureHwPowerOff() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IHermesService.DESCRIPTOR);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.HermesService.IHermesService
            public byte[] hermesSendApdu(byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IHermesService.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.HermesService.IHermesService
            public byte[] hermesCosPatchTest(byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IHermesService.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.HermesService.IHermesService
            public byte[] hermesGetSeId() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IHermesService.DESCRIPTOR);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.HermesService.IHermesService
            public byte[] hermesCosUnitTest(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IHermesService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.HermesService.IHermesService
            public int getFailureCount(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IHermesService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.service.HermesService.IHermesService
            public byte[] hermesGetAppletVersion() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IHermesService.DESCRIPTOR);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
