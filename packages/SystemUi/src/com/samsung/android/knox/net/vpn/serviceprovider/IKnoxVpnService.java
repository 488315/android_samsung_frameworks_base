package com.samsung.android.knox.net.vpn.serviceprovider;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.samsung.android.knox.keystore.CertificateInfo;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface IKnoxVpnService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService";

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements IKnoxVpnService {
        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
        public final int createConnection(String str) {
            return 0;
        }

        @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
        public final List<String> getAllConnections() {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
        public final CertificateInfo getCACertificate(String str) {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
        public final String getConnection(String str) {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
        public final String getErrorString(String str) {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
        public final int getState(String str) {
            return 0;
        }

        @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
        public final CertificateInfo getUserCertificate(String str) {
            return null;
        }

        @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
        public final int getVpnModeOfOperation(String str) {
            return 0;
        }

        @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
        public final int removeConnection(String str) {
            return 0;
        }

        @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
        public final boolean setAutoRetryOnConnectionError(String str, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
        public final boolean setCACertificate(String str, byte[] bArr) {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
        public final boolean setServerCertValidationUserAcceptanceCriteria(String str, boolean z, List list, int i) {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
        public final boolean setUserCertificate(String str, byte[] bArr, String str2) {
            return false;
        }

        @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
        public final int setVpnModeOfOperation(String str, int i) {
            return 0;
        }

        @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
        public final int startConnection(String str) {
            return 0;
        }

        @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
        public final int stopConnection(String str) {
            return 0;
        }
    }

    int createConnection(String str);

    List<String> getAllConnections();

    CertificateInfo getCACertificate(String str);

    String getConnection(String str);

    String getErrorString(String str);

    int getState(String str);

    CertificateInfo getUserCertificate(String str);

    int getVpnModeOfOperation(String str);

    int removeConnection(String str);

    boolean setAutoRetryOnConnectionError(String str, boolean z);

    boolean setCACertificate(String str, byte[] bArr);

    boolean setServerCertValidationUserAcceptanceCriteria(String str, boolean z, List list, int i);

    boolean setUserCertificate(String str, byte[] bArr, String str2);

    int setVpnModeOfOperation(String str, int i);

    int startConnection(String str);

    int stopConnection(String str);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IKnoxVpnService {
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

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IKnoxVpnService {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
            public final int createConnection(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
            public final List<String> getAllConnections() {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnService.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
            public final CertificateInfo getCACertificate(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CertificateInfo) obtain2.readTypedObject(CertificateInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
            public final String getConnection(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
            public final String getErrorString(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final String getInterfaceDescriptor() {
                return IKnoxVpnService.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
            public final int getState(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
            public final CertificateInfo getUserCertificate(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CertificateInfo) obtain2.readTypedObject(CertificateInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
            public final int getVpnModeOfOperation(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
            public final int removeConnection(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
            public final boolean setAutoRetryOnConnectionError(String str, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
            public final boolean setCACertificate(String str, byte[] bArr) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
            public final boolean setServerCertValidationUserAcceptanceCriteria(String str, boolean z, List list, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeList(list);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
            public final boolean setUserCertificate(String str, byte[] bArr, String str2) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeByteArray(bArr);
                    obtain.writeString(str2);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
            public final int setVpnModeOfOperation(String str, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
            public final int startConnection(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.vpn.serviceprovider.IKnoxVpnService
            public final int stopConnection(String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IKnoxVpnService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IKnoxVpnService.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IKnoxVpnService)) ? new Proxy(iBinder) : (IKnoxVpnService) queryLocalInterface;
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IKnoxVpnService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IKnoxVpnService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int createConnection = createConnection(readString);
                    parcel2.writeNoException();
                    parcel2.writeInt(createConnection);
                    return true;
                case 2:
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int removeConnection = removeConnection(readString2);
                    parcel2.writeNoException();
                    parcel2.writeInt(removeConnection);
                    return true;
                case 3:
                    List<String> allConnections = getAllConnections();
                    parcel2.writeNoException();
                    parcel2.writeStringList(allConnections);
                    return true;
                case 4:
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String connection = getConnection(readString3);
                    parcel2.writeNoException();
                    parcel2.writeString(connection);
                    return true;
                case 5:
                    String readString4 = parcel.readString();
                    byte[] createByteArray = parcel.createByteArray();
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean userCertificate = setUserCertificate(readString4, createByteArray, readString5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(userCertificate);
                    return true;
                case 6:
                    String readString6 = parcel.readString();
                    byte[] createByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    boolean cACertificate = setCACertificate(readString6, createByteArray2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(cACertificate);
                    return true;
                case 7:
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    CertificateInfo userCertificate2 = getUserCertificate(readString7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(userCertificate2, 1);
                    return true;
                case 8:
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    CertificateInfo cACertificate2 = getCACertificate(readString8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(cACertificate2, 1);
                    return true;
                case 9:
                    String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int startConnection = startConnection(readString9);
                    parcel2.writeNoException();
                    parcel2.writeInt(startConnection);
                    return true;
                case 10:
                    String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int stopConnection = stopConnection(readString10);
                    parcel2.writeNoException();
                    parcel2.writeInt(stopConnection);
                    return true;
                case 11:
                    String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int state = getState(readString11);
                    parcel2.writeNoException();
                    parcel2.writeInt(state);
                    return true;
                case 12:
                    String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String errorString = getErrorString(readString12);
                    parcel2.writeNoException();
                    parcel2.writeString(errorString);
                    return true;
                case 13:
                    String readString13 = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int vpnModeOfOperation = setVpnModeOfOperation(readString13, readInt);
                    parcel2.writeNoException();
                    parcel2.writeInt(vpnModeOfOperation);
                    return true;
                case 14:
                    String readString14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int vpnModeOfOperation2 = getVpnModeOfOperation(readString14);
                    parcel2.writeNoException();
                    parcel2.writeInt(vpnModeOfOperation2);
                    return true;
                case 15:
                    String readString15 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    ArrayList readArrayList = parcel.readArrayList(getClass().getClassLoader());
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean serverCertValidationUserAcceptanceCriteria = setServerCertValidationUserAcceptanceCriteria(readString15, readBoolean, readArrayList, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(serverCertValidationUserAcceptanceCriteria);
                    return true;
                case 16:
                    String readString16 = parcel.readString();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean autoRetryOnConnectionError = setAutoRetryOnConnectionError(readString16, readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(autoRetryOnConnectionError);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}
