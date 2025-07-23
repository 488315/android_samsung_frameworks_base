package com.samsung.android.knox.net.vpn.serviceprovider.tethering;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.knox.net.vpn.serviceprovider.tethering.IAuthenticationStatus;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface IKnoxVpnTetherAuthInterface extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.net.vpn.serviceprovider.tethering.IKnoxVpnTetherAuthInterface";

    int getAuthenticationStatus() throws RemoteException;

    int setCACertificate(byte[] bArr, String str) throws RemoteException;

    boolean setCaAlias(String str) throws RemoteException;

    boolean setCaptivePortalAlias(String str) throws RemoteException;

    int setCaptivePortalCertificate(byte[] bArr, String str) throws RemoteException;

    void setClientAuthDetails(Bundle bundle) throws RemoteException;

    void setHtmlResponsePage(String str) throws RemoteException;

    void setHtmlSignInPage(String str) throws RemoteException;

    boolean setServerAlias(String str) throws RemoteException;

    int setServerCertificate(byte[] bArr, String str) throws RemoteException;

    Bundle startAuthenticationProcess(IAuthenticationStatus iAuthenticationStatus) throws RemoteException;

    int stopAuthenticationProcess() throws RemoteException;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Stub extends Binder implements IKnoxVpnTetherAuthInterface {
        public static final int TRANSACTION_getAuthenticationStatus = 1;
        public static final int TRANSACTION_setCACertificate = 2;
        public static final int TRANSACTION_setCaAlias = 5;
        public static final int TRANSACTION_setCaptivePortalAlias = 7;
        public static final int TRANSACTION_setCaptivePortalCertificate = 4;
        public static final int TRANSACTION_setClientAuthDetails = 8;
        public static final int TRANSACTION_setHtmlResponsePage = 10;
        public static final int TRANSACTION_setHtmlSignInPage = 9;
        public static final int TRANSACTION_setServerAlias = 6;
        public static final int TRANSACTION_setServerCertificate = 3;
        public static final int TRANSACTION_startAuthenticationProcess = 11;
        public static final int TRANSACTION_stopAuthenticationProcess = 12;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        class Proxy implements IKnoxVpnTetherAuthInterface {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.net.vpn.serviceprovider.tethering.IKnoxVpnTetherAuthInterface
            public int getAuthenticationStatus() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnTetherAuthInterface.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IKnoxVpnTetherAuthInterface.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.net.vpn.serviceprovider.tethering.IKnoxVpnTetherAuthInterface
            public int setCACertificate(byte[] bArr, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnTetherAuthInterface.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.serviceprovider.tethering.IKnoxVpnTetherAuthInterface
            public boolean setCaAlias(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnTetherAuthInterface.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.serviceprovider.tethering.IKnoxVpnTetherAuthInterface
            public boolean setCaptivePortalAlias(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnTetherAuthInterface.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.serviceprovider.tethering.IKnoxVpnTetherAuthInterface
            public int setCaptivePortalCertificate(byte[] bArr, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnTetherAuthInterface.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.serviceprovider.tethering.IKnoxVpnTetherAuthInterface
            public void setClientAuthDetails(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnTetherAuthInterface.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.serviceprovider.tethering.IKnoxVpnTetherAuthInterface
            public void setHtmlResponsePage(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnTetherAuthInterface.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.serviceprovider.tethering.IKnoxVpnTetherAuthInterface
            public void setHtmlSignInPage(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnTetherAuthInterface.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.serviceprovider.tethering.IKnoxVpnTetherAuthInterface
            public boolean setServerAlias(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnTetherAuthInterface.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.serviceprovider.tethering.IKnoxVpnTetherAuthInterface
            public int setServerCertificate(byte[] bArr, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnTetherAuthInterface.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.serviceprovider.tethering.IKnoxVpnTetherAuthInterface
            public Bundle startAuthenticationProcess(IAuthenticationStatus iAuthenticationStatus) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnTetherAuthInterface.DESCRIPTOR);
                    obtain.writeStrongInterface(iAuthenticationStatus);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.serviceprovider.tethering.IKnoxVpnTetherAuthInterface
            public int stopAuthenticationProcess() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnTetherAuthInterface.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IKnoxVpnTetherAuthInterface.DESCRIPTOR);
        }

        public static IKnoxVpnTetherAuthInterface asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IKnoxVpnTetherAuthInterface.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IKnoxVpnTetherAuthInterface)) ? new Proxy(iBinder) : (IKnoxVpnTetherAuthInterface) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IKnoxVpnTetherAuthInterface.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IKnoxVpnTetherAuthInterface.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int authenticationStatus = getAuthenticationStatus();
                    parcel2.writeNoException();
                    parcel2.writeInt(authenticationStatus);
                    return true;
                case 2:
                    byte[] createByteArray = parcel.createByteArray();
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int cACertificate = setCACertificate(createByteArray, readString);
                    parcel2.writeNoException();
                    parcel2.writeInt(cACertificate);
                    return true;
                case 3:
                    byte[] createByteArray2 = parcel.createByteArray();
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int serverCertificate = setServerCertificate(createByteArray2, readString2);
                    parcel2.writeNoException();
                    parcel2.writeInt(serverCertificate);
                    return true;
                case 4:
                    byte[] createByteArray3 = parcel.createByteArray();
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int captivePortalCertificate = setCaptivePortalCertificate(createByteArray3, readString3);
                    parcel2.writeNoException();
                    parcel2.writeInt(captivePortalCertificate);
                    return true;
                case 5:
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean caAlias = setCaAlias(readString4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(caAlias);
                    return true;
                case 6:
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean serverAlias = setServerAlias(readString5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(serverAlias);
                    return true;
                case 7:
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean captivePortalAlias = setCaptivePortalAlias(readString6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(captivePortalAlias);
                    return true;
                case 8:
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    setClientAuthDetails(bundle);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setHtmlSignInPage(readString7);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setHtmlResponsePage(readString8);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    IAuthenticationStatus asInterface = IAuthenticationStatus.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    Bundle startAuthenticationProcess = startAuthenticationProcess(asInterface);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(startAuthenticationProcess, 1);
                    return true;
                case 12:
                    int stopAuthenticationProcess = stopAuthenticationProcess();
                    parcel2.writeNoException();
                    parcel2.writeInt(stopAuthenticationProcess);
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Default implements IKnoxVpnTetherAuthInterface {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.serviceprovider.tethering.IKnoxVpnTetherAuthInterface
        public int getAuthenticationStatus() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.net.vpn.serviceprovider.tethering.IKnoxVpnTetherAuthInterface
        public int setCACertificate(byte[] bArr, String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.net.vpn.serviceprovider.tethering.IKnoxVpnTetherAuthInterface
        public boolean setCaAlias(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.serviceprovider.tethering.IKnoxVpnTetherAuthInterface
        public boolean setCaptivePortalAlias(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.serviceprovider.tethering.IKnoxVpnTetherAuthInterface
        public int setCaptivePortalCertificate(byte[] bArr, String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.net.vpn.serviceprovider.tethering.IKnoxVpnTetherAuthInterface
        public boolean setServerAlias(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.serviceprovider.tethering.IKnoxVpnTetherAuthInterface
        public int setServerCertificate(byte[] bArr, String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.net.vpn.serviceprovider.tethering.IKnoxVpnTetherAuthInterface
        public Bundle startAuthenticationProcess(IAuthenticationStatus iAuthenticationStatus) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.serviceprovider.tethering.IKnoxVpnTetherAuthInterface
        public int stopAuthenticationProcess() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.net.vpn.serviceprovider.tethering.IKnoxVpnTetherAuthInterface
        public void setClientAuthDetails(Bundle bundle) throws RemoteException {
        }

        @Override // com.samsung.android.knox.net.vpn.serviceprovider.tethering.IKnoxVpnTetherAuthInterface
        public void setHtmlResponsePage(String str) throws RemoteException {
        }

        @Override // com.samsung.android.knox.net.vpn.serviceprovider.tethering.IKnoxVpnTetherAuthInterface
        public void setHtmlSignInPage(String str) throws RemoteException {
        }
    }
}
