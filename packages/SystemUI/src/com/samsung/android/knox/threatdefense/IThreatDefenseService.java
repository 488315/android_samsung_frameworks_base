package com.samsung.android.knox.threatdefense;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.knox.ContextInfo;

/* loaded from: classes4.dex */
public interface IThreatDefenseService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.threatdefense.IThreatDefenseService";

    public class Default implements IThreatDefenseService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.threatdefense.IThreatDefenseService
        public int[] getProcessId(ContextInfo contextInfo, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.threatdefense.IThreatDefenseService
        public boolean hasPackageRules(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.threatdefense.IThreatDefenseService
        public String procReader(ContextInfo contextInfo, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.threatdefense.IThreatDefenseService
        public String processProcReader(ContextInfo contextInfo, String str, int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.threatdefense.IThreatDefenseService
        public int setPackageRules(ContextInfo contextInfo, String str) throws RemoteException {
            return 0;
        }
    }

    int[] getProcessId(ContextInfo contextInfo, String str) throws RemoteException;

    boolean hasPackageRules(ContextInfo contextInfo) throws RemoteException;

    String procReader(ContextInfo contextInfo, String str) throws RemoteException;

    String processProcReader(ContextInfo contextInfo, String str, int i) throws RemoteException;

    int setPackageRules(ContextInfo contextInfo, String str) throws RemoteException;

    public abstract class Stub extends Binder implements IThreatDefenseService {
        public static final int TRANSACTION_getProcessId = 2;
        public static final int TRANSACTION_hasPackageRules = 5;
        public static final int TRANSACTION_procReader = 1;
        public static final int TRANSACTION_processProcReader = 3;
        public static final int TRANSACTION_setPackageRules = 4;

        class Proxy implements IThreatDefenseService {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IThreatDefenseService.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.threatdefense.IThreatDefenseService
            public int[] getProcessId(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IThreatDefenseService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.threatdefense.IThreatDefenseService
            public boolean hasPackageRules(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IThreatDefenseService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.threatdefense.IThreatDefenseService
            public String procReader(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IThreatDefenseService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.threatdefense.IThreatDefenseService
            public String processProcReader(ContextInfo contextInfo, String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IThreatDefenseService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.threatdefense.IThreatDefenseService
            public int setPackageRules(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IThreatDefenseService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IThreatDefenseService.DESCRIPTOR);
        }

        public static IThreatDefenseService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IThreatDefenseService.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IThreatDefenseService)) ? new Proxy(iBinder) : (IThreatDefenseService) iInterfaceQueryLocalInterface;
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "procReader";
            }
            if (i == 2) {
                return "getProcessId";
            }
            if (i == 3) {
                return "processProcReader";
            }
            if (i == 4) {
                return "setPackageRules";
            }
            if (i != 5) {
                return null;
            }
            return "hasPackageRules";
        }

        public int getMaxTransactionId() {
            return 4;
        }

        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IThreatDefenseService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IThreatDefenseService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ContextInfo contextInfo = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                String string = parcel.readString();
                parcel.enforceNoDataAvail();
                String strProcReader = procReader(contextInfo, string);
                parcel2.writeNoException();
                parcel2.writeString(strProcReader);
            } else if (i == 2) {
                ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                String string2 = parcel.readString();
                parcel.enforceNoDataAvail();
                int[] processId = getProcessId(contextInfo2, string2);
                parcel2.writeNoException();
                parcel2.writeIntArray(processId);
            } else if (i == 3) {
                ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                String string3 = parcel.readString();
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                String strProcessProcReader = processProcReader(contextInfo3, string3, i3);
                parcel2.writeNoException();
                parcel2.writeString(strProcessProcReader);
            } else if (i == 4) {
                ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                String string4 = parcel.readString();
                parcel.enforceNoDataAvail();
                int packageRules = setPackageRules(contextInfo4, string4);
                parcel2.writeNoException();
                parcel2.writeInt(packageRules);
            } else {
                if (i != 5) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                ContextInfo contextInfo5 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                parcel.enforceNoDataAvail();
                boolean zHasPackageRules = hasPackageRules(contextInfo5);
                parcel2.writeNoException();
                parcel2.writeBoolean(zHasPackageRules);
            }
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }
}
