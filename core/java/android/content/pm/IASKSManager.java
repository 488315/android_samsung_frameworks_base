package android.content.pm;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

public interface IASKSManager extends IInterface {
    public static final String DESCRIPTOR = "android.content.pm.IASKSManager";

    boolean applyScpmPolicyFromService(String str) throws RemoteException;

    String[] checkASKSTarget(int i) throws RemoteException;

    void checkDeletableListForASKS() throws RemoteException;

    boolean checkFollowingLegitimateWay(String str, int i) throws RemoteException;

    String checkIfSuspiciousValue(String str, String str2, boolean z, Map<String, String> map)
            throws RemoteException;

    int checkRestrictedPermission(String str, String str2) throws RemoteException;

    int checkSecurityEnabled() throws RemoteException;

    int checkUnknownSourcePackage(
            String str,
            String[] strArr,
            String[] strArr2,
            String str2,
            Signature[] signatureArr,
            String str3,
            String str4,
            String str5,
            int i,
            String str6,
            String str7,
            int i2,
            String str8,
            String str9)
            throws RemoteException;

    void clearASKSruleForRemovedPackage(String str) throws RemoteException;

    List<String> getIMEIList() throws RemoteException;

    String getPolicyVersion(String str) throws RemoteException;

    byte[] getSEInfo(String str) throws RemoteException;

    String getUNvalueForASKS() throws RemoteException;

    List<String> getUnknownAppList() throws RemoteException;

    boolean isSuspiciousMsgTarget(String str) throws RemoteException;

    boolean isTrustedStore(String str, int i) throws RemoteException;

    boolean isUnknownApps(String str, Signature[] signatureArr) throws RemoteException;

    void postASKSsetup(String str, String str2, int i) throws RemoteException;

    String readASKSFiles(String str, String str2) throws RemoteException;

    void setASKSPolicyVersion(String str) throws RemoteException;

    void setTrustTimebyStatusChanged() throws RemoteException;

    void systemReady() throws RemoteException;

    int verifyASKStokenForPackage(
            String str,
            String str2,
            long j,
            Signature[] signatureArr,
            String str3,
            String str4,
            boolean z)
            throws RemoteException;

    public static class Default implements IASKSManager {
        @Override // android.content.pm.IASKSManager
        public void systemReady() throws RemoteException {}

        @Override // android.content.pm.IASKSManager
        public int verifyASKStokenForPackage(
                String packageName,
                String baseCodePath,
                long versionCode,
                Signature[] signatures,
                String installerPackageName,
                String initiatingPackageName,
                boolean isSystemApp)
                throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IASKSManager
        public void postASKSsetup(String packageName, String baseCodePath, int userId)
                throws RemoteException {}

        @Override // android.content.pm.IASKSManager
        public void clearASKSruleForRemovedPackage(String packageName) throws RemoteException {}

        @Override // android.content.pm.IASKSManager
        public int checkRestrictedPermission(String packageName, String permission)
                throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IASKSManager
        public byte[] getSEInfo(String packageName) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IASKSManager
        public List<String> getIMEIList() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IASKSManager
        public boolean checkFollowingLegitimateWay(String pkgName, int flags)
                throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IASKSManager
        public void checkDeletableListForASKS() throws RemoteException {}

        @Override // android.content.pm.IASKSManager
        public void setTrustTimebyStatusChanged() throws RemoteException {}

        @Override // android.content.pm.IASKSManager
        public String getUNvalueForASKS() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IASKSManager
        public String[] checkASKSTarget(int type) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IASKSManager
        public int checkUnknownSourcePackage(
                String packageName,
                String[] permList,
                String[] servicePermList,
                String baseCodePath,
                Signature[] signatures,
                String initiatingPackageName,
                String originatingPackageName,
                String originalInstallerPackageName,
                int sdkVersion,
                String referralUrl,
                String downloadUrl,
                int userId,
                String safeInstallToken,
                String safeInstallCert)
                throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IASKSManager
        public boolean isUnknownApps(String packageName, Signature[] signatures)
                throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IASKSManager
        public List<String> getUnknownAppList() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IASKSManager
        public String checkIfSuspiciousValue(
                String targetContentsValue,
                String fileName,
                boolean checkContains,
                Map<String, String> results)
                throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IASKSManager
        public String getPolicyVersion(String fileName) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IASKSManager
        public boolean isSuspiciousMsgTarget(String modelName) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IASKSManager
        public boolean applyScpmPolicyFromService(String serviceType) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IASKSManager
        public String readASKSFiles(String dir, String file) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IASKSManager
        public boolean isTrustedStore(String packageName, int userId) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IASKSManager
        public int checkSecurityEnabled() throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IASKSManager
        public void setASKSPolicyVersion(String newVersion) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IASKSManager {
        static final int TRANSACTION_applyScpmPolicyFromService = 19;
        static final int TRANSACTION_checkASKSTarget = 12;
        static final int TRANSACTION_checkDeletableListForASKS = 9;
        static final int TRANSACTION_checkFollowingLegitimateWay = 8;
        static final int TRANSACTION_checkIfSuspiciousValue = 16;
        static final int TRANSACTION_checkRestrictedPermission = 5;
        static final int TRANSACTION_checkSecurityEnabled = 22;
        static final int TRANSACTION_checkUnknownSourcePackage = 13;
        static final int TRANSACTION_clearASKSruleForRemovedPackage = 4;
        static final int TRANSACTION_getIMEIList = 7;
        static final int TRANSACTION_getPolicyVersion = 17;
        static final int TRANSACTION_getSEInfo = 6;
        static final int TRANSACTION_getUNvalueForASKS = 11;
        static final int TRANSACTION_getUnknownAppList = 15;
        static final int TRANSACTION_isSuspiciousMsgTarget = 18;
        static final int TRANSACTION_isTrustedStore = 21;
        static final int TRANSACTION_isUnknownApps = 14;
        static final int TRANSACTION_postASKSsetup = 3;
        static final int TRANSACTION_readASKSFiles = 20;
        static final int TRANSACTION_setASKSPolicyVersion = 23;
        static final int TRANSACTION_setTrustTimebyStatusChanged = 10;
        static final int TRANSACTION_systemReady = 1;
        static final int TRANSACTION_verifyASKStokenForPackage = 2;

        public Stub() {
            attachInterface(this, IASKSManager.DESCRIPTOR);
        }

        public static IASKSManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IASKSManager.DESCRIPTOR);
            if (iin != null && (iin instanceof IASKSManager)) {
                return (IASKSManager) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "systemReady";
                case 2:
                    return "verifyASKStokenForPackage";
                case 3:
                    return "postASKSsetup";
                case 4:
                    return "clearASKSruleForRemovedPackage";
                case 5:
                    return "checkRestrictedPermission";
                case 6:
                    return "getSEInfo";
                case 7:
                    return "getIMEIList";
                case 8:
                    return "checkFollowingLegitimateWay";
                case 9:
                    return "checkDeletableListForASKS";
                case 10:
                    return "setTrustTimebyStatusChanged";
                case 11:
                    return "getUNvalueForASKS";
                case 12:
                    return "checkASKSTarget";
                case 13:
                    return "checkUnknownSourcePackage";
                case 14:
                    return "isUnknownApps";
                case 15:
                    return "getUnknownAppList";
                case 16:
                    return "checkIfSuspiciousValue";
                case 17:
                    return "getPolicyVersion";
                case 18:
                    return "isSuspiciousMsgTarget";
                case 19:
                    return "applyScpmPolicyFromService";
                case 20:
                    return "readASKSFiles";
                case 21:
                    return "isTrustedStore";
                case 22:
                    return "checkSecurityEnabled";
                case 23:
                    return "setASKSPolicyVersion";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, final Parcel data, final Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IASKSManager.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IASKSManager.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    systemReady();
                    reply.writeNoException();
                    return true;
                case 2:
                    String _arg0 = data.readString();
                    String _arg1 = data.readString();
                    long _arg2 = data.readLong();
                    Signature[] _arg3 = (Signature[]) data.createTypedArray(Signature.CREATOR);
                    String _arg4 = data.readString();
                    String _arg5 = data.readString();
                    boolean _arg6 = data.readBoolean();
                    data.enforceNoDataAvail();
                    int _result =
                            verifyASKStokenForPackage(
                                    _arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6);
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                case 3:
                    String _arg02 = data.readString();
                    String _arg12 = data.readString();
                    int _arg22 = data.readInt();
                    data.enforceNoDataAvail();
                    postASKSsetup(_arg02, _arg12, _arg22);
                    reply.writeNoException();
                    return true;
                case 4:
                    String _arg03 = data.readString();
                    data.enforceNoDataAvail();
                    clearASKSruleForRemovedPackage(_arg03);
                    reply.writeNoException();
                    return true;
                case 5:
                    String _arg04 = data.readString();
                    String _arg13 = data.readString();
                    data.enforceNoDataAvail();
                    int _result2 = checkRestrictedPermission(_arg04, _arg13);
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    return true;
                case 6:
                    String _arg05 = data.readString();
                    data.enforceNoDataAvail();
                    byte[] _result3 = getSEInfo(_arg05);
                    reply.writeNoException();
                    reply.writeByteArray(_result3);
                    return true;
                case 7:
                    List<String> _result4 = getIMEIList();
                    reply.writeNoException();
                    reply.writeStringList(_result4);
                    return true;
                case 8:
                    String _arg06 = data.readString();
                    int _arg14 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result5 = checkFollowingLegitimateWay(_arg06, _arg14);
                    reply.writeNoException();
                    reply.writeBoolean(_result5);
                    return true;
                case 9:
                    checkDeletableListForASKS();
                    reply.writeNoException();
                    return true;
                case 10:
                    setTrustTimebyStatusChanged();
                    reply.writeNoException();
                    return true;
                case 11:
                    String _result6 = getUNvalueForASKS();
                    reply.writeNoException();
                    reply.writeString(_result6);
                    return true;
                case 12:
                    int _arg07 = data.readInt();
                    data.enforceNoDataAvail();
                    String[] _result7 = checkASKSTarget(_arg07);
                    reply.writeNoException();
                    reply.writeStringArray(_result7);
                    return true;
                case 13:
                    String _arg08 = data.readString();
                    String[] _arg15 = data.createStringArray();
                    String[] _arg23 = data.createStringArray();
                    String _arg32 = data.readString();
                    Signature[] _arg42 = (Signature[]) data.createTypedArray(Signature.CREATOR);
                    String _arg52 = data.readString();
                    String _arg62 = data.readString();
                    String _arg7 = data.readString();
                    int _arg8 = data.readInt();
                    String _arg9 = data.readString();
                    String _arg10 = data.readString();
                    int _arg11 = data.readInt();
                    String _arg122 = data.readString();
                    String _arg132 = data.readString();
                    data.enforceNoDataAvail();
                    int _result8 =
                            checkUnknownSourcePackage(
                                    _arg08, _arg15, _arg23, _arg32, _arg42, _arg52, _arg62, _arg7,
                                    _arg8, _arg9, _arg10, _arg11, _arg122, _arg132);
                    reply.writeNoException();
                    reply.writeInt(_result8);
                    return true;
                case 14:
                    String _arg09 = data.readString();
                    Signature[] _arg16 = (Signature[]) data.createTypedArray(Signature.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result9 = isUnknownApps(_arg09, _arg16);
                    reply.writeNoException();
                    reply.writeBoolean(_result9);
                    return true;
                case 15:
                    List<String> _result10 = getUnknownAppList();
                    reply.writeNoException();
                    reply.writeStringList(_result10);
                    return true;
                case 16:
                    String _arg010 = data.readString();
                    String _arg17 = data.readString();
                    boolean _arg24 = data.readBoolean();
                    int N = data.readInt();
                    final Map<String, String> _arg33 = N < 0 ? null : new HashMap<>();
                    IntStream.range(0, N)
                            .forEach(
                                    new IntConsumer() { // from class:
                                        // android.content.pm.IASKSManager$Stub$$ExternalSyntheticLambda0
                                        @Override // java.util.function.IntConsumer
                                        public final void accept(int i) {
                                            IASKSManager.Stub.lambda$onTransact$0(
                                                    Parcel.this, _arg33, i);
                                        }
                                    });
                    data.enforceNoDataAvail();
                    String _result11 = checkIfSuspiciousValue(_arg010, _arg17, _arg24, _arg33);
                    reply.writeNoException();
                    reply.writeString(_result11);
                    if (_arg33 == null) {
                        reply.writeInt(-1);
                        return true;
                    }
                    reply.writeInt(_arg33.size());
                    _arg33.forEach(
                            new BiConsumer() { // from class:
                                // android.content.pm.IASKSManager$Stub$$ExternalSyntheticLambda1
                                @Override // java.util.function.BiConsumer
                                public final void accept(Object obj, Object obj2) {
                                    IASKSManager.Stub.lambda$onTransact$1(
                                            Parcel.this, (String) obj, (String) obj2);
                                }
                            });
                    return true;
                case 17:
                    String _arg011 = data.readString();
                    data.enforceNoDataAvail();
                    String _result12 = getPolicyVersion(_arg011);
                    reply.writeNoException();
                    reply.writeString(_result12);
                    return true;
                case 18:
                    String _arg012 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result13 = isSuspiciousMsgTarget(_arg012);
                    reply.writeNoException();
                    reply.writeBoolean(_result13);
                    return true;
                case 19:
                    String _arg013 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result14 = applyScpmPolicyFromService(_arg013);
                    reply.writeNoException();
                    reply.writeBoolean(_result14);
                    return true;
                case 20:
                    String _arg014 = data.readString();
                    String _arg18 = data.readString();
                    data.enforceNoDataAvail();
                    String _result15 = readASKSFiles(_arg014, _arg18);
                    reply.writeNoException();
                    reply.writeString(_result15);
                    return true;
                case 21:
                    String _arg015 = data.readString();
                    int _arg19 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result16 = isTrustedStore(_arg015, _arg19);
                    reply.writeNoException();
                    reply.writeBoolean(_result16);
                    return true;
                case 22:
                    int _result17 = checkSecurityEnabled();
                    reply.writeNoException();
                    reply.writeInt(_result17);
                    return true;
                case 23:
                    String _arg016 = data.readString();
                    data.enforceNoDataAvail();
                    setASKSPolicyVersion(_arg016);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        static /* synthetic */ void lambda$onTransact$0(Parcel data, Map _arg3, int i) {
            String k = data.readString();
            String v = data.readString();
            _arg3.put(k, v);
        }

        static /* synthetic */ void lambda$onTransact$1(Parcel reply, String k, String v) {
            reply.writeString(k);
            reply.writeString(v);
        }

        /* JADX INFO: Access modifiers changed from: private */
        static class Proxy implements IASKSManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IASKSManager.DESCRIPTOR;
            }

            @Override // android.content.pm.IASKSManager
            public void systemReady() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public int verifyASKStokenForPackage(
                    String packageName,
                    String baseCodePath,
                    long versionCode,
                    Signature[] signatures,
                    String installerPackageName,
                    String initiatingPackageName,
                    boolean isSystemApp)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(baseCodePath);
                    _data.writeLong(versionCode);
                    _data.writeTypedArray(signatures, 0);
                    _data.writeString(installerPackageName);
                    _data.writeString(initiatingPackageName);
                    _data.writeBoolean(isSystemApp);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public void postASKSsetup(String packageName, String baseCodePath, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(baseCodePath);
                    _data.writeInt(userId);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public void clearASKSruleForRemovedPackage(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public int checkRestrictedPermission(String packageName, String permission)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeString(permission);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public byte[] getSEInfo(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public List<String> getIMEIList() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public boolean checkFollowingLegitimateWay(String pkgName, int flags)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    _data.writeString(pkgName);
                    _data.writeInt(flags);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public void checkDeletableListForASKS() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public void setTrustTimebyStatusChanged() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public String getUNvalueForASKS() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public String[] checkASKSTarget(int type) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    _data.writeInt(type);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public int checkUnknownSourcePackage(
                    String packageName,
                    String[] permList,
                    String[] servicePermList,
                    String baseCodePath,
                    Signature[] signatures,
                    String initiatingPackageName,
                    String originatingPackageName,
                    String originalInstallerPackageName,
                    int sdkVersion,
                    String referralUrl,
                    String downloadUrl,
                    int userId,
                    String safeInstallToken,
                    String safeInstallCert)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeStringArray(permList);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    _data.writeStringArray(servicePermList);
                } catch (Throwable th2) {
                    th = th2;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeString(baseCodePath);
                } catch (Throwable th3) {
                    th = th3;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeTypedArray(signatures, 0);
                } catch (Throwable th4) {
                    th = th4;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
                try {
                    _data.writeString(initiatingPackageName);
                    try {
                        _data.writeString(originatingPackageName);
                    } catch (Throwable th5) {
                        th = th5;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeString(originalInstallerPackageName);
                    } catch (Throwable th6) {
                        th = th6;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(sdkVersion);
                    } catch (Throwable th7) {
                        th = th7;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeString(referralUrl);
                    } catch (Throwable th8) {
                        th = th8;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeString(downloadUrl);
                    } catch (Throwable th9) {
                        th = th9;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeInt(userId);
                    } catch (Throwable th10) {
                        th = th10;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                    try {
                        _data.writeString(safeInstallToken);
                        _data.writeString(safeInstallCert);
                        this.mRemote.transact(13, _data, _reply, 0);
                        _reply.readException();
                        int _result = _reply.readInt();
                        _reply.recycle();
                        _data.recycle();
                        return _result;
                    } catch (Throwable th11) {
                        th = th11;
                        _reply.recycle();
                        _data.recycle();
                        throw th;
                    }
                } catch (Throwable th12) {
                    th = th12;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                }
            }

            @Override // android.content.pm.IASKSManager
            public boolean isUnknownApps(String packageName, Signature[] signatures)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeTypedArray(signatures, 0);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public List<String> getUnknownAppList() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public String checkIfSuspiciousValue(
                    String targetContentsValue,
                    String fileName,
                    boolean checkContains,
                    final Map<String, String> results)
                    throws RemoteException {
                final Parcel _data = Parcel.obtain(asBinder());
                final Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    _data.writeString(targetContentsValue);
                    _data.writeString(fileName);
                    _data.writeBoolean(checkContains);
                    if (results == null) {
                        _data.writeInt(-1);
                    } else {
                        _data.writeInt(results.size());
                        results.forEach(
                                new BiConsumer() { // from class:
                                    // android.content.pm.IASKSManager$Stub$Proxy$$ExternalSyntheticLambda0
                                    @Override // java.util.function.BiConsumer
                                    public final void accept(Object obj, Object obj2) {
                                        IASKSManager.Stub.Proxy.lambda$checkIfSuspiciousValue$0(
                                                Parcel.this, (String) obj, (String) obj2);
                                    }
                                });
                    }
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    if (results != null) {
                        results.clear();
                    }
                    IntStream.range(0, _reply.readInt())
                            .forEach(
                                    new IntConsumer() { // from class:
                                        // android.content.pm.IASKSManager$Stub$Proxy$$ExternalSyntheticLambda1
                                        @Override // java.util.function.IntConsumer
                                        public final void accept(int i) {
                                            IASKSManager.Stub.Proxy.lambda$checkIfSuspiciousValue$1(
                                                    Parcel.this, results, i);
                                        }
                                    });
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            static /* synthetic */ void lambda$checkIfSuspiciousValue$0(
                    Parcel _data, String k, String v) {
                _data.writeString(k);
                _data.writeString(v);
            }

            static /* synthetic */ void lambda$checkIfSuspiciousValue$1(
                    Parcel _reply, Map results, int i) {
                String k = _reply.readString();
                String v = _reply.readString();
                results.put(k, v);
            }

            @Override // android.content.pm.IASKSManager
            public String getPolicyVersion(String fileName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    _data.writeString(fileName);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public boolean isSuspiciousMsgTarget(String modelName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    _data.writeString(modelName);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public boolean applyScpmPolicyFromService(String serviceType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    _data.writeString(serviceType);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public String readASKSFiles(String dir, String file) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    _data.writeString(dir);
                    _data.writeString(file);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public boolean isTrustedStore(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public int checkSecurityEnabled() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public void setASKSPolicyVersion(String newVersion) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    _data.writeString(newVersion);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 22;
        }
    }
}
