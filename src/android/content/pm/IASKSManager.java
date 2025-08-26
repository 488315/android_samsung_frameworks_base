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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IASKSManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IASKSManager)) {
                return (IASKSManager) iInterfaceQueryLocalInterface;
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
                    String string = parcel.readString();
                    String string2 = parcel.readString();
                    long j = parcel.readLong();
                    Signature[] signatureArr = (Signature[]) parcel.createTypedArray(Signature.CREATOR);
                    String string3 = parcel.readString();
                    String string4 = parcel.readString();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int iVerifyASKStokenForPackage = verifyASKStokenForPackage(string, string2, j, signatureArr, string3, string4, z);
                    parcel2.writeNoException();
                    parcel2.writeInt(iVerifyASKStokenForPackage);
                    return true;
                case 3:
                    String string5 = parcel.readString();
                    String string6 = parcel.readString();
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    postASKSsetup(string5, string6, i3);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    String string7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    clearASKSruleForRemovedPackage(string7);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    String string8 = parcel.readString();
                    String string9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iCheckRestrictedPermission = checkRestrictedPermission(string8, string9);
                    parcel2.writeNoException();
                    parcel2.writeInt(iCheckRestrictedPermission);
                    return true;
                case 6:
                    String string10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    byte[] sEInfo = getSEInfo(string10);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(sEInfo);
                    return true;
                case 7:
                    List<String> iMEIList = getIMEIList();
                    parcel2.writeNoException();
                    parcel2.writeStringList(iMEIList);
                    return true;
                case 8:
                    String string11 = parcel.readString();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zCheckFollowingLegitimateWay = checkFollowingLegitimateWay(string11, i4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCheckFollowingLegitimateWay);
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
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String[] strArrCheckASKSTarget = checkASKSTarget(i5);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(strArrCheckASKSTarget);
                    return true;
                case 13:
                    String string12 = parcel.readString();
                    String[] strArrCreateStringArray = parcel.createStringArray();
                    String[] strArrCreateStringArray2 = parcel.createStringArray();
                    String string13 = parcel.readString();
                    Signature[] signatureArr2 = (Signature[]) parcel.createTypedArray(Signature.CREATOR);
                    String string14 = parcel.readString();
                    String string15 = parcel.readString();
                    String string16 = parcel.readString();
                    int i6 = parcel.readInt();
                    String string17 = parcel.readString();
                    String string18 = parcel.readString();
                    int i7 = parcel.readInt();
                    AsksParcel asksParcel = (AsksParcel) parcel.readTypedObject(AsksParcel.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iCheckUnknownSourcePackage = checkUnknownSourcePackage(string12, strArrCreateStringArray, strArrCreateStringArray2, string13, signatureArr2, string14, string15, string16, i6, string17, string18, i7, asksParcel);
                    parcel2.writeNoException();
                    parcel2.writeInt(iCheckUnknownSourcePackage);
                    return true;
                case 14:
                    String string19 = parcel.readString();
                    Signature[] signatureArr3 = (Signature[]) parcel.createTypedArray(Signature.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsUnknownApps = isUnknownApps(string19, signatureArr3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsUnknownApps);
                    break;
                case 15:
                    List<String> unknownAppList = getUnknownAppList();
                    parcel2.writeNoException();
                    parcel2.writeStringList(unknownAppList);
                    break;
                case 16:
                    String string20 = parcel.readString();
                    String string21 = parcel.readString();
                    boolean z2 = parcel.readBoolean();
                    int i8 = parcel.readInt();
                    final HashMap map = i8 < 0 ? null : new HashMap();
                    IntStream.range(0, i8).forEach(new IntConsumer() { // from class: android.content.pm.IASKSManager$Stub$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i9) {
                            Parcel parcel3 = parcel;
                            map.put(parcel3.readString(), parcel3.readString());
                        }
                    });
                    parcel.enforceNoDataAvail();
                    String strCheckIfSuspiciousValue = checkIfSuspiciousValue(string20, string21, z2, map);
                    parcel2.writeNoException();
                    parcel2.writeString(strCheckIfSuspiciousValue);
                    if (map == null) {
                        parcel2.writeInt(-1);
                        break;
                    } else {
                        parcel2.writeInt(map.size());
                        map.forEach(new BiConsumer() { // from class: android.content.pm.IASKSManager$Stub$$ExternalSyntheticLambda1
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                IASKSManager.Stub.lambda$onTransact$1(parcel2, (String) obj, (String) obj2);
                            }
                        });
                        break;
                    }
                case 17:
                    String string22 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String policyVersion = getPolicyVersion(string22);
                    parcel2.writeNoException();
                    parcel2.writeString(policyVersion);
                    break;
                case 18:
                    String string23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsSuspiciousMsgTarget = isSuspiciousMsgTarget(string23);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSuspiciousMsgTarget);
                    break;
                case 19:
                    String string24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zApplyScpmPolicyFromService = applyScpmPolicyFromService(string24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zApplyScpmPolicyFromService);
                    break;
                case 20:
                    String string25 = parcel.readString();
                    String string26 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String aSKSFiles = readASKSFiles(string25, string26);
                    parcel2.writeNoException();
                    parcel2.writeString(aSKSFiles);
                    break;
                case 21:
                    String string27 = parcel.readString();
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsTrustedStore = isTrustedStore(string27, i9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsTrustedStore);
                    break;
                case 22:
                    int iCheckSecurityEnabled = checkSecurityEnabled();
                    parcel2.writeNoException();
                    parcel2.writeInt(iCheckSecurityEnabled);
                    break;
                case 23:
                    String string28 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setASKSPolicyVersion(string28);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public int verifyASKStokenForPackage(String str, String str2, long j, Signature[] signatureArr, String str3, String str4, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeTypedArray(signatureArr, 0);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public void postASKSsetup(String str, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public void clearASKSruleForRemovedPackage(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public int checkRestrictedPermission(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public byte[] getSEInfo(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public List<String> getIMEIList() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public boolean checkFollowingLegitimateWay(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public void checkDeletableListForASKS() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public void setTrustTimebyStatusChanged() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public String getUNvalueForASKS() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public String[] checkASKSTarget(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public int checkUnknownSourcePackage(String str, String[] strArr, String[] strArr2, String str2, Signature[] signatureArr, String str3, String str4, String str5, int i, String str6, String str7, int i2, AsksParcel asksParcel) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeStringArray(strArr2);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedArray(signatureArr, 0);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeString(str5);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str6);
                    parcelObtain.writeString(str7);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(asksParcel, 0);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public boolean isUnknownApps(String str, Signature[] signatureArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedArray(signatureArr, 0);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public List<String> getUnknownAppList() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public String checkIfSuspiciousValue(String str, String str2, boolean z, final Map<String, String> map) throws RemoteException {
                final Parcel parcelObtain = Parcel.obtain(asBinder());
                final Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeBoolean(z);
                    if (map == null) {
                        parcelObtain.writeInt(-1);
                    } else {
                        parcelObtain.writeInt(map.size());
                        map.forEach(new BiConsumer() { // from class: android.content.pm.IASKSManager$Stub$Proxy$$ExternalSyntheticLambda0
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                IASKSManager.Stub.Proxy.lambda$checkIfSuspiciousValue$0(parcelObtain, (String) obj, (String) obj2);
                            }
                        });
                    }
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    String string = parcelObtain2.readString();
                    if (map != null) {
                        map.clear();
                    }
                    IntStream.range(0, parcelObtain2.readInt()).forEach(new IntConsumer() { // from class: android.content.pm.IASKSManager$Stub$Proxy$$ExternalSyntheticLambda1
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i) {
                            Parcel parcel = parcelObtain2;
                            map.put(parcel.readString(), parcel.readString());
                        }
                    });
                    return string;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            static /* synthetic */ void lambda$checkIfSuspiciousValue$0(Parcel parcel, String str, String str2) {
                parcel.writeString(str);
                parcel.writeString(str2);
            }

            @Override // android.content.pm.IASKSManager
            public String getPolicyVersion(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public boolean isSuspiciousMsgTarget(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public boolean applyScpmPolicyFromService(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public String readASKSFiles(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public boolean isTrustedStore(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public int checkSecurityEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.IASKSManager
            public void setASKSPolicyVersion(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IASKSManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
