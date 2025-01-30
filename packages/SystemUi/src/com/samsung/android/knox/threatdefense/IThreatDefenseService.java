package com.samsung.android.knox.threatdefense;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.samsung.android.knox.ContextInfo;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface IThreatDefenseService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.threatdefense.IThreatDefenseService";

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements IThreatDefenseService {
        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.threatdefense.IThreatDefenseService
        public final int[] getProcessId(ContextInfo contextInfo, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.threatdefense.IThreatDefenseService
        public final boolean hasPackageRules(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.threatdefense.IThreatDefenseService
        public final String procReader(ContextInfo contextInfo, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.threatdefense.IThreatDefenseService
        public final String processProcReader(ContextInfo contextInfo, String str, int i) {
            return null;
        }

        @Override // com.samsung.android.knox.threatdefense.IThreatDefenseService
        public final int setPackageRules(ContextInfo contextInfo, String str) {
            return 0;
        }
    }

    int[] getProcessId(ContextInfo contextInfo, String str);

    boolean hasPackageRules(ContextInfo contextInfo);

    String procReader(ContextInfo contextInfo, String str);

    String processProcReader(ContextInfo contextInfo, String str, int i);

    int setPackageRules(ContextInfo contextInfo, String str);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IThreatDefenseService {
        public static final int TRANSACTION_getProcessId = 2;
        public static final int TRANSACTION_hasPackageRules = 5;
        public static final int TRANSACTION_procReader = 1;
        public static final int TRANSACTION_processProcReader = 3;
        public static final int TRANSACTION_setPackageRules = 4;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IThreatDefenseService {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            public final String getInterfaceDescriptor() {
                return IThreatDefenseService.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.threatdefense.IThreatDefenseService
            public final int[] getProcessId(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IThreatDefenseService.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.threatdefense.IThreatDefenseService
            public final boolean hasPackageRules(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IThreatDefenseService.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.threatdefense.IThreatDefenseService
            public final String procReader(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IThreatDefenseService.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.threatdefense.IThreatDefenseService
            public final String processProcReader(ContextInfo contextInfo, String str, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IThreatDefenseService.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.threatdefense.IThreatDefenseService
            public final int setPackageRules(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IThreatDefenseService.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IThreatDefenseService.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IThreatDefenseService)) ? new Proxy(iBinder) : (IThreatDefenseService) queryLocalInterface;
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

        public final int getMaxTransactionId() {
            return 4;
        }

        public final String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IThreatDefenseService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IThreatDefenseService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ContextInfo contextInfo = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                String procReader = procReader(contextInfo, readString);
                parcel2.writeNoException();
                parcel2.writeString(procReader);
            } else if (i == 2) {
                ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                String readString2 = parcel.readString();
                parcel.enforceNoDataAvail();
                int[] processId = getProcessId(contextInfo2, readString2);
                parcel2.writeNoException();
                parcel2.writeIntArray(processId);
            } else if (i == 3) {
                ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                String readString3 = parcel.readString();
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                String processProcReader = processProcReader(contextInfo3, readString3, readInt);
                parcel2.writeNoException();
                parcel2.writeString(processProcReader);
            } else if (i == 4) {
                ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                String readString4 = parcel.readString();
                parcel.enforceNoDataAvail();
                int packageRules = setPackageRules(contextInfo4, readString4);
                parcel2.writeNoException();
                parcel2.writeInt(packageRules);
            } else {
                if (i != 5) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                ContextInfo contextInfo5 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                parcel.enforceNoDataAvail();
                boolean hasPackageRules = hasPackageRules(contextInfo5);
                parcel2.writeNoException();
                parcel2.writeBoolean(hasPackageRules);
            }
            return true;
        }

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }
    }
}
