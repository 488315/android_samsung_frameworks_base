package android.content.pm;

import android.content.pm.IASKSManager;
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

/* loaded from: classes.dex */
public interface IASKSManager extends IInterface {
    public static final String DESCRIPTOR = "android.content.pm.IASKSManager";

    public static class Default implements IASKSManager {
        @Override // android.content.pm.IASKSManager
        public boolean applyScpmPolicyFromService(String str) throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.content.pm.IASKSManager
        public String[] checkASKSTarget(int i) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IASKSManager
        public void checkDeletableListForASKS() throws RemoteException {
        }

        @Override // android.content.pm.IASKSManager
        public boolean checkFollowingLegitimateWay(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IASKSManager
        public String checkIfSuspiciousValue(String str, String str2, boolean z, Map<String, String> map) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IASKSManager
        public int checkRestrictedPermission(String str, String str2) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IASKSManager
        public int checkSecurityEnabled() throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IASKSManager
        public int checkUnknownSourcePackage(String str, String[] strArr, String[] strArr2, String str2, Signature[] signatureArr, String str3, String str4, String str5, int i, String str6, String str7, int i2, AsksParcel asksParcel) throws RemoteException {
            return 0;
        }

        @Override // android.content.pm.IASKSManager
        public void clearASKSruleForRemovedPackage(String str) throws RemoteException {
        }

        @Override // android.content.pm.IASKSManager
        public List<String> getIMEIList() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IASKSManager
        public String getPolicyVersion(String str) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IASKSManager
        public byte[] getSEInfo(String str) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IASKSManager
        public String getUNvalueForASKS() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IASKSManager
        public List<String> getUnknownAppList() throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IASKSManager
        public boolean isSuspiciousMsgTarget(String str) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IASKSManager
        public boolean isTrustedStore(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IASKSManager
        public boolean isUnknownApps(String str, Signature[] signatureArr) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.IASKSManager
        public void postASKSsetup(String str, String str2, int i) throws RemoteException {
        }

        @Override // android.content.pm.IASKSManager
        public String readASKSFiles(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.IASKSManager
        public void setASKSPolicyVersion(String str) throws RemoteException {
        }

        @Override // android.content.pm.IASKSManager
        public void setTrustTimebyStatusChanged() throws RemoteException {
        }

        @Override // android.content.pm.IASKSManager
        public void systemReady() throws RemoteException {
        }

        @Override // android.content.pm.IASKSManager
        public int verifyASKStokenForPackage(String str, String str2, long j, Signature[] signatureArr, String str3, String str4, boolean z) throws RemoteException {
            return 0;
        }
    }

    boolean applyScpmPolicyFromService(String str) throws RemoteException;

    String[] checkASKSTarget(int i) throws RemoteException;

    void checkDeletableListForASKS() throws RemoteException;

    boolean checkFollowingLegitimateWay(String str, int i) throws RemoteException;

    String checkIfSuspiciousValue(String str, String str2, boolean z, Map<String, String> map) throws RemoteException;

    int checkRestrictedPermission(String str, String str2) throws RemoteException;

    int checkSecurityEnabled() throws RemoteException;

    int checkUnknownSourcePackage(String str, String[] strArr, String[] strArr2, String str2, Signature[] signatureArr, String str3, String str4, String str5, int i, String str6, String str7, int i2, AsksParcel asksParcel) throws RemoteException;

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

    int verifyASKStokenForPackage(String str, String str2, long j, Signature[] signatureArr, String str3, String str4, boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements IASKSManager {
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

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 22;
        }

        public Stub() {
            attachInterface(this, IASKSManager.DESCRIPTOR);
        }

        public static IASKSManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IASKSManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IASKSManager)) {
                return (IASKSManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
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
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, final Parcel parcel, final Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IASKSManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IASKSManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    systemReady();
                    parcel2.writeNoException();
                    return true;
                case 2:
                    String readString = parcel.readString();
                    String readString2 = parcel.readString();
                    long readLong = parcel.readLong();
                    Signature[] signatureArr = (Signature[]) parcel.createTypedArray(Signature.CREATOR);
                    String readString3 = parcel.readString();
                    String readString4 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int verifyASKStokenForPackage = verifyASKStokenForPackage(readString, readString2, readLong, signatureArr, readString3, readString4, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeInt(verifyASKStokenForPackage);
                    return true;
                case 3:
                    String readString5 = parcel.readString();
                    String readString6 = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    postASKSsetup(readString5, readString6, readInt);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    clearASKSruleForRemovedPackage(readString7);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    String readString8 = parcel.readString();
                    String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int checkRestrictedPermission = checkRestrictedPermission(readString8, readString9);
                    parcel2.writeNoException();
                    parcel2.writeInt(checkRestrictedPermission);
                    return true;
                case 6:
                    String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    byte[] sEInfo = getSEInfo(readString10);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(sEInfo);
                    return true;
                case 7:
                    List<String> iMEIList = getIMEIList();
                    parcel2.writeNoException();
                    parcel2.writeStringList(iMEIList);
                    return true;
                case 8:
                    String readString11 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean checkFollowingLegitimateWay = checkFollowingLegitimateWay(readString11, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(checkFollowingLegitimateWay);
                    return true;
                case 9:
                    checkDeletableListForASKS();
                    parcel2.writeNoException();
                    return true;
                case 10:
                    setTrustTimebyStatusChanged();
                    parcel2.writeNoException();
                    return true;
                case 11:
                    String uNvalueForASKS = getUNvalueForASKS();
                    parcel2.writeNoException();
                    parcel2.writeString(uNvalueForASKS);
                    return true;
                case 12:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String[] checkASKSTarget = checkASKSTarget(readInt3);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(checkASKSTarget);
                    return true;
                case 13:
                    String readString12 = parcel.readString();
                    String[] createStringArray = parcel.createStringArray();
                    String[] createStringArray2 = parcel.createStringArray();
                    String readString13 = parcel.readString();
                    Signature[] signatureArr2 = (Signature[]) parcel.createTypedArray(Signature.CREATOR);
                    String readString14 = parcel.readString();
                    String readString15 = parcel.readString();
                    String readString16 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    String readString17 = parcel.readString();
                    String readString18 = parcel.readString();
                    int readInt5 = parcel.readInt();
                    AsksParcel asksParcel = (AsksParcel) parcel.readTypedObject(AsksParcel.CREATOR);
                    parcel.enforceNoDataAvail();
                    int checkUnknownSourcePackage = checkUnknownSourcePackage(readString12, createStringArray, createStringArray2, readString13, signatureArr2, readString14, readString15, readString16, readInt4, readString17, readString18, readInt5, asksParcel);
                    parcel2.writeNoException();
                    parcel2.writeInt(checkUnknownSourcePackage);
                    return true;
                case 14:
                    String readString19 = parcel.readString();
                    Signature[] signatureArr3 = (Signature[]) parcel.createTypedArray(Signature.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isUnknownApps = isUnknownApps(readString19, signatureArr3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUnknownApps);
                    break;
                case 15:
                    List<String> unknownAppList = getUnknownAppList();
                    parcel2.writeNoException();
                    parcel2.writeStringList(unknownAppList);
                    break;
                case 16:
                    String readString20 = parcel.readString();
                    String readString21 = parcel.readString();
                    boolean readBoolean2 = parcel.readBoolean();
                    int readInt6 = parcel.readInt();
                    final HashMap hashMap = readInt6 < 0 ? null : new HashMap();
                    IntStream.range(0, readInt6).forEach(new IntConsumer() { // from class: android.content.pm.IASKSManager$Stub$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i3) {
                            hashMap.put(r0.readString(), Parcel.this.readString());
                        }
                    });
                    parcel.enforceNoDataAvail();
                    String checkIfSuspiciousValue = checkIfSuspiciousValue(readString20, readString21, readBoolean2, hashMap);
                    parcel2.writeNoException();
                    parcel2.writeString(checkIfSuspiciousValue);
                    if (hashMap == null) {
                        parcel2.writeInt(-1);
                        break;
                    } else {
                        parcel2.writeInt(hashMap.size());
                        hashMap.forEach(new BiConsumer() { // from class: android.content.pm.IASKSManager$Stub$$ExternalSyntheticLambda1
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                IASKSManager.Stub.lambda$onTransact$1(Parcel.this, (String) obj, (String) obj2);
                            }
                        });
                        break;
                    }
                case 17:
                    String readString22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String policyVersion = getPolicyVersion(readString22);
                    parcel2.writeNoException();
                    parcel2.writeString(policyVersion);
                    break;
                case 18:
                    String readString23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isSuspiciousMsgTarget = isSuspiciousMsgTarget(readString23);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSuspiciousMsgTarget);
                    break;
                case 19:
                    String readString24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean applyScpmPolicyFromService = applyScpmPolicyFromService(readString24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(applyScpmPolicyFromService);
                    break;
                case 20:
                    String readString25 = parcel.readString();
                    String readString26 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String readASKSFiles = readASKSFiles(readString25, readString26);
                    parcel2.writeNoException();
                    parcel2.writeString(readASKSFiles);
                    break;
                case 21:
                    String readString27 = parcel.readString();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isTrustedStore = isTrustedStore(readString27, readInt7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isTrustedStore);
                    break;
                case 22:
                    int checkSecurityEnabled = checkSecurityEnabled();
                    parcel2.writeNoException();
                    parcel2.writeInt(checkSecurityEnabled);
                    break;
                case 23:
                    String readString28 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setASKSPolicyVersion(readString28);
                    parcel2.writeNoException();
                    break;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        static /* synthetic */ void lambda$onTransact$1(Parcel parcel, String str, String str2) {
            parcel.writeString(str);
            parcel.writeString(str2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        static class Proxy implements IASKSManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public int verifyASKStokenForPackage(String str, String str2, long j, Signature[] signatureArr, String str3, String str4, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeLong(j);
                    obtain.writeTypedArray(signatureArr, 0);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public void postASKSsetup(String str, String str2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public void clearASKSruleForRemovedPackage(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public int checkRestrictedPermission(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public byte[] getSEInfo(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public List<String> getIMEIList() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public boolean checkFollowingLegitimateWay(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public void checkDeletableListForASKS() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public void setTrustTimebyStatusChanged() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public String getUNvalueForASKS() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public String[] checkASKSTarget(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public int checkUnknownSourcePackage(String str, String[] strArr, String[] strArr2, String str2, Signature[] signatureArr, String str3, String str4, String str5, int i, String str6, String str7, int i2, AsksParcel asksParcel) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStringArray(strArr);
                    obtain.writeStringArray(strArr2);
                    obtain.writeString(str2);
                    obtain.writeTypedArray(signatureArr, 0);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeString(str5);
                    obtain.writeInt(i);
                    obtain.writeString(str6);
                    obtain.writeString(str7);
                    obtain.writeInt(i2);
                    obtain.writeTypedObject(asksParcel, 0);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public boolean isUnknownApps(String str, Signature[] signatureArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedArray(signatureArr, 0);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public List<String> getUnknownAppList() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public String checkIfSuspiciousValue(String str, String str2, boolean z, final Map<String, String> map) throws RemoteException {
                final Parcel obtain = Parcel.obtain(asBinder());
                final Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    if (map == null) {
                        obtain.writeInt(-1);
                    } else {
                        obtain.writeInt(map.size());
                        map.forEach(new BiConsumer() { // from class: android.content.pm.IASKSManager$Stub$Proxy$$ExternalSyntheticLambda0
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                IASKSManager.Stub.Proxy.lambda$checkIfSuspiciousValue$0(Parcel.this, (String) obj, (String) obj2);
                            }
                        });
                    }
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    String readString = obtain2.readString();
                    if (map != null) {
                        map.clear();
                    }
                    IntStream.range(0, obtain2.readInt()).forEach(new IntConsumer() { // from class: android.content.pm.IASKSManager$Stub$Proxy$$ExternalSyntheticLambda1
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i) {
                            map.put(r0.readString(), Parcel.this.readString());
                        }
                    });
                    return readString;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            static /* synthetic */ void lambda$checkIfSuspiciousValue$0(Parcel parcel, String str, String str2) {
                parcel.writeString(str);
                parcel.writeString(str2);
            }

            @Override // android.content.pm.IASKSManager
            public String getPolicyVersion(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public boolean isSuspiciousMsgTarget(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public boolean applyScpmPolicyFromService(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public String readASKSFiles(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public boolean isTrustedStore(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public int checkSecurityEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public void setASKSPolicyVersion(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
