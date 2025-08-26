package com.samsung.android.knox.net.vpn.serviceprovider;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.knox.keystore.CertificateInfo;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface IKnoxVpnService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService";

    public class Default implements IKnoxVpnService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
        public int createConnection(String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
        public List<String> getAllConnections() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
        public CertificateInfo getCACertificate(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
        public String getConnection(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
        public String getErrorString(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
        public int getState(String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
        public CertificateInfo getUserCertificate(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
        public int getVpnModeOfOperation(String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
        public int removeConnection(String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
        public boolean setAutoRetryOnConnectionError(String str, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
        public boolean setCACertificate(String str, byte[] bArr) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
        public boolean setServerCertValidationUserAcceptanceCriteria(String str, boolean z, List list, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
        public boolean setUserCertificate(String str, byte[] bArr, String str2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
        public int setVpnModeOfOperation(String str, int i) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
        public int startConnection(String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
        public int stopConnection(String str) throws RemoteException {
            return 0;
        }
    }

    int createConnection(String str) throws RemoteException;

    List<String> getAllConnections() throws RemoteException;

    CertificateInfo getCACertificate(String str) throws RemoteException;

    String getConnection(String str) throws RemoteException;

    String getErrorString(String str) throws RemoteException;

    int getState(String str) throws RemoteException;

    CertificateInfo getUserCertificate(String str) throws RemoteException;

    int getVpnModeOfOperation(String str) throws RemoteException;

    int removeConnection(String str) throws RemoteException;

    boolean setAutoRetryOnConnectionError(String str, boolean z) throws RemoteException;

    boolean setCACertificate(String str, byte[] bArr) throws RemoteException;

    boolean setServerCertValidationUserAcceptanceCriteria(String str, boolean z, List list, int i) throws RemoteException;

    boolean setUserCertificate(String str, byte[] bArr, String str2) throws RemoteException;

    int setVpnModeOfOperation(String str, int i) throws RemoteException;

    int startConnection(String str) throws RemoteException;

    int stopConnection(String str) throws RemoteException;

    public abstract class Stub extends Binder implements IKnoxVpnService {
        public static final int TRANSACTION_createConnection = 1;
        public static final int TRANSACTION_getAllConnections = 3;
        public static final int TRANSACTION_getCACertificate = 8;
        public static final int TRANSACTION_getConnection = 4;
        public static final int TRANSACTION_getErrorString = 12;
        public static final int TRANSACTION_getState = 11;
        public static final int TRANSACTION_getUserCertificate = 7;
        public static final int TRANSACTION_getVpnModeOfOperation = 14;
        public static final int TRANSACTION_removeConnection = 2;
        public static final int TRANSACTION_setAutoRetryOnConnectionError = 16;
        public static final int TRANSACTION_setCACertificate = 6;
        public static final int TRANSACTION_setServerCertValidationUserAcceptanceCriteria = 15;
        public static final int TRANSACTION_setUserCertificate = 5;
        public static final int TRANSACTION_setVpnModeOfOperation = 13;
        public static final int TRANSACTION_startConnection = 9;
        public static final int TRANSACTION_stopConnection = 10;

        class Proxy implements IKnoxVpnService {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
            public int createConnection(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
            public List<String> getAllConnections() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnService.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
            public CertificateInfo getCACertificate(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CertificateInfo) parcelObtain2.readTypedObject(CertificateInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
            public String getConnection(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
            public String getErrorString(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IKnoxVpnService.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
            public int getState(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
            public CertificateInfo getUserCertificate(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CertificateInfo) parcelObtain2.readTypedObject(CertificateInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
            public int getVpnModeOfOperation(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
            public int removeConnection(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
            public boolean setAutoRetryOnConnectionError(String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
            public boolean setCACertificate(String str, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
            public boolean setServerCertValidationUserAcceptanceCriteria(String str, boolean z, List list, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeList(list);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
            public boolean setUserCertificate(String str, byte[] bArr, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
            public int setVpnModeOfOperation(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
            public int startConnection(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
            public int stopConnection(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IKnoxVpnService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IKnoxVpnService.DESCRIPTOR);
        }

        public static IKnoxVpnService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IKnoxVpnService.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IKnoxVpnService)) ? new Proxy(iBinder) : (IKnoxVpnService) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IKnoxVpnService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IKnoxVpnService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iCreateConnection = createConnection(string);
                    parcel2.writeNoException();
                    parcel2.writeInt(iCreateConnection);
                    return true;
                case 2:
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iRemoveConnection = removeConnection(string2);
                    parcel2.writeNoException();
                    parcel2.writeInt(iRemoveConnection);
                    return true;
                case 3:
                    List<String> allConnections = getAllConnections();
                    parcel2.writeNoException();
                    parcel2.writeStringList(allConnections);
                    return true;
                case 4:
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String connection = getConnection(string3);
                    parcel2.writeNoException();
                    parcel2.writeString(connection);
                    return true;
                case 5:
                    String string4 = parcel.readString();
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean userCertificate = setUserCertificate(string4, bArrCreateByteArray, string5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(userCertificate);
                    return true;
                case 6:
                    String string6 = parcel.readString();
                    byte[] bArrCreateByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    boolean cACertificate = setCACertificate(string6, bArrCreateByteArray2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(cACertificate);
                    return true;
                case 7:
                    String string7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    CertificateInfo userCertificate2 = getUserCertificate(string7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(userCertificate2, 1);
                    return true;
                case 8:
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    CertificateInfo cACertificate2 = getCACertificate(string8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(cACertificate2, 1);
                    return true;
                case 9:
                    String string9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iStartConnection = startConnection(string9);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStartConnection);
                    return true;
                case 10:
                    String string10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iStopConnection = stopConnection(string10);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStopConnection);
                    return true;
                case 11:
                    String string11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int state = getState(string11);
                    parcel2.writeNoException();
                    parcel2.writeInt(state);
                    return true;
                case 12:
                    String string12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String errorString = getErrorString(string12);
                    parcel2.writeNoException();
                    parcel2.writeString(errorString);
                    return true;
                case 13:
                    String string13 = parcel.readString();
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int vpnModeOfOperation = setVpnModeOfOperation(string13, i3);
                    parcel2.writeNoException();
                    parcel2.writeInt(vpnModeOfOperation);
                    return true;
                case 14:
                    String string14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int vpnModeOfOperation2 = getVpnModeOfOperation(string14);
                    parcel2.writeNoException();
                    parcel2.writeInt(vpnModeOfOperation2);
                    return true;
                case 15:
                    String string15 = parcel.readString();
                    boolean z = parcel.readBoolean();
                    ArrayList arrayList = parcel.readArrayList(getClass().getClassLoader());
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean serverCertValidationUserAcceptanceCriteria = setServerCertValidationUserAcceptanceCriteria(string15, z, arrayList, i4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(serverCertValidationUserAcceptanceCriteria);
                    return true;
                case 16:
                    String string16 = parcel.readString();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean autoRetryOnConnectionError = setAutoRetryOnConnectionError(string16, z2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(autoRetryOnConnectionError);
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
}
