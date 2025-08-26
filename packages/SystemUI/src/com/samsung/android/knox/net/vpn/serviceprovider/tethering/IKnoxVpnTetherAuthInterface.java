package com.samsung.android.knox.net.vpn.serviceprovider.tethering;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.knox.net.vpn.serviceprovider.tethering.IAuthenticationStatus;

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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnTetherAuthInterface.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IKnoxVpnTetherAuthInterface.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.net.vpn.serviceprovider.tethering.IKnoxVpnTetherAuthInterface
            public int setCACertificate(byte[] bArr, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnTetherAuthInterface.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.serviceprovider.tethering.IKnoxVpnTetherAuthInterface
            public boolean setCaAlias(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnTetherAuthInterface.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.serviceprovider.tethering.IKnoxVpnTetherAuthInterface
            public boolean setCaptivePortalAlias(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnTetherAuthInterface.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.serviceprovider.tethering.IKnoxVpnTetherAuthInterface
            public int setCaptivePortalCertificate(byte[] bArr, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnTetherAuthInterface.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.serviceprovider.tethering.IKnoxVpnTetherAuthInterface
            public void setClientAuthDetails(Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnTetherAuthInterface.DESCRIPTOR);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.serviceprovider.tethering.IKnoxVpnTetherAuthInterface
            public void setHtmlResponsePage(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnTetherAuthInterface.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.serviceprovider.tethering.IKnoxVpnTetherAuthInterface
            public void setHtmlSignInPage(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnTetherAuthInterface.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.serviceprovider.tethering.IKnoxVpnTetherAuthInterface
            public boolean setServerAlias(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnTetherAuthInterface.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.serviceprovider.tethering.IKnoxVpnTetherAuthInterface
            public int setServerCertificate(byte[] bArr, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnTetherAuthInterface.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.serviceprovider.tethering.IKnoxVpnTetherAuthInterface
            public Bundle startAuthenticationProcess(IAuthenticationStatus iAuthenticationStatus) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnTetherAuthInterface.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAuthenticationStatus);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.serviceprovider.tethering.IKnoxVpnTetherAuthInterface
            public int stopAuthenticationProcess() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnTetherAuthInterface.DESCRIPTOR);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IKnoxVpnTetherAuthInterface.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IKnoxVpnTetherAuthInterface)) ? new Proxy(iBinder) : (IKnoxVpnTetherAuthInterface) iInterfaceQueryLocalInterface;
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
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int cACertificate = setCACertificate(bArrCreateByteArray, string);
                    parcel2.writeNoException();
                    parcel2.writeInt(cACertificate);
                    return true;
                case 3:
                    byte[] bArrCreateByteArray2 = parcel.createByteArray();
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int serverCertificate = setServerCertificate(bArrCreateByteArray2, string2);
                    parcel2.writeNoException();
                    parcel2.writeInt(serverCertificate);
                    return true;
                case 4:
                    byte[] bArrCreateByteArray3 = parcel.createByteArray();
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int captivePortalCertificate = setCaptivePortalCertificate(bArrCreateByteArray3, string3);
                    parcel2.writeNoException();
                    parcel2.writeInt(captivePortalCertificate);
                    return true;
                case 5:
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean caAlias = setCaAlias(string4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(caAlias);
                    return true;
                case 6:
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean serverAlias = setServerAlias(string5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(serverAlias);
                    return true;
                case 7:
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean captivePortalAlias = setCaptivePortalAlias(string6);
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
                    String string7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setHtmlSignInPage(string7);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setHtmlResponsePage(string8);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    IAuthenticationStatus iAuthenticationStatusAsInterface = IAuthenticationStatus.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    Bundle bundleStartAuthenticationProcess = startAuthenticationProcess(iAuthenticationStatusAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(bundleStartAuthenticationProcess, 1);
                    return true;
                case 12:
                    int iStopAuthenticationProcess = stopAuthenticationProcess();
                    parcel2.writeNoException();
                    parcel2.writeInt(iStopAuthenticationProcess);
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
