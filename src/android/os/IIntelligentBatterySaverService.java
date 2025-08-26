package android.os;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public interface IIntelligentBatterySaverService extends IInterface {
    public static final String DESCRIPTOR = "android.os.IIntelligentBatterySaverService";

    public static class Default implements IIntelligentBatterySaverService {
        @Override // android.os.IIntelligentBatterySaverService
        public boolean addScreenQuickDimApp(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.os.IIntelligentBatterySaverService
        public boolean addSqdBlockList(int i, String str) throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.IIntelligentBatterySaverService
        public List dexoptPackages(List<String> list) throws RemoteException {
            return null;
        }

        @Override // android.os.IIntelligentBatterySaverService
        public long[] getGain() throws RemoteException {
            return null;
        }

        @Override // android.os.IIntelligentBatterySaverService
        public Bundle getOperationHistory() throws RemoteException {
            return null;
        }

        @Override // android.os.IIntelligentBatterySaverService
        public Map getScreenQuickDimApps() throws RemoteException {
            return null;
        }

        @Override // android.os.IIntelligentBatterySaverService
        public Bundle getSleepTime() throws RemoteException {
            return null;
        }

        @Override // android.os.IIntelligentBatterySaverService
        public Map getSqdBlockList() throws RemoteException {
            return null;
        }

        @Override // android.os.IIntelligentBatterySaverService
        public boolean isEnableSerive() throws RemoteException {
            return false;
        }

        @Override // android.os.IIntelligentBatterySaverService
        public boolean isSqdSupport() throws RemoteException {
            return false;
        }

        @Override // android.os.IIntelligentBatterySaverService
        public boolean isSqdUiControlEnabled() throws RemoteException {
            return false;
        }

        @Override // android.os.IIntelligentBatterySaverService
        public boolean removeScreenQuickDimApp(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.os.IIntelligentBatterySaverService
        public boolean removeSqdBlockList(int i, String str) throws RemoteException {
            return false;
        }

        @Override // android.os.IIntelligentBatterySaverService
        public void setRubinEvent(String str) throws RemoteException {
        }

        @Override // android.os.IIntelligentBatterySaverService
        public void setSarrUiControlEnable(boolean z) throws RemoteException {
        }

        @Override // android.os.IIntelligentBatterySaverService
        public void setSleepModeEnabled(boolean z) throws RemoteException {
        }

        @Override // android.os.IIntelligentBatterySaverService
        public void setSleepTime(long j, long j2) throws RemoteException {
        }

        @Override // android.os.IIntelligentBatterySaverService
        public void setSqdUiControlEnabled(boolean z) throws RemoteException {
        }
    }

    boolean addScreenQuickDimApp(String str, int i) throws RemoteException;

    boolean addSqdBlockList(int i, String str) throws RemoteException;

    List dexoptPackages(List<String> list) throws RemoteException;

    long[] getGain() throws RemoteException;

    Bundle getOperationHistory() throws RemoteException;

    Map getScreenQuickDimApps() throws RemoteException;

    Bundle getSleepTime() throws RemoteException;

    Map getSqdBlockList() throws RemoteException;

    boolean isEnableSerive() throws RemoteException;

    boolean isSqdSupport() throws RemoteException;

    boolean isSqdUiControlEnabled() throws RemoteException;

    boolean removeScreenQuickDimApp(String str, int i) throws RemoteException;

    boolean removeSqdBlockList(int i, String str) throws RemoteException;

    void setRubinEvent(String str) throws RemoteException;

    void setSarrUiControlEnable(boolean z) throws RemoteException;

    void setSleepModeEnabled(boolean z) throws RemoteException;

    void setSleepTime(long j, long j2) throws RemoteException;

    void setSqdUiControlEnabled(boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements IIntelligentBatterySaverService {
        static final int TRANSACTION_addScreenQuickDimApp = 5;
        static final int TRANSACTION_addSqdBlockList = 4;
        static final int TRANSACTION_dexoptPackages = 18;
        static final int TRANSACTION_getGain = 10;
        static final int TRANSACTION_getOperationHistory = 16;
        static final int TRANSACTION_getScreenQuickDimApps = 9;
        static final int TRANSACTION_getSleepTime = 17;
        static final int TRANSACTION_getSqdBlockList = 8;
        static final int TRANSACTION_isEnableSerive = 15;
        static final int TRANSACTION_isSqdSupport = 3;
        static final int TRANSACTION_isSqdUiControlEnabled = 2;
        static final int TRANSACTION_removeScreenQuickDimApp = 7;
        static final int TRANSACTION_removeSqdBlockList = 6;
        static final int TRANSACTION_setRubinEvent = 14;
        static final int TRANSACTION_setSarrUiControlEnable = 11;
        static final int TRANSACTION_setSleepModeEnabled = 12;
        static final int TRANSACTION_setSleepTime = 13;
        static final int TRANSACTION_setSqdUiControlEnabled = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 17;
        }

        public Stub() {
            attachInterface(this, IIntelligentBatterySaverService.DESCRIPTOR);
        }

        public static IIntelligentBatterySaverService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IIntelligentBatterySaverService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IIntelligentBatterySaverService)) {
                return (IIntelligentBatterySaverService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setSqdUiControlEnabled";
                case 2:
                    return "isSqdUiControlEnabled";
                case 3:
                    return "isSqdSupport";
                case 4:
                    return "addSqdBlockList";
                case 5:
                    return "addScreenQuickDimApp";
                case 6:
                    return "removeSqdBlockList";
                case 7:
                    return "removeScreenQuickDimApp";
                case 8:
                    return "getSqdBlockList";
                case 9:
                    return "getScreenQuickDimApps";
                case 10:
                    return "getGain";
                case 11:
                    return "setSarrUiControlEnable";
                case 12:
                    return "setSleepModeEnabled";
                case 13:
                    return "setSleepTime";
                case 14:
                    return "setRubinEvent";
                case 15:
                    return "isEnableSerive";
                case 16:
                    return "getOperationHistory";
                case 17:
                    return "getSleepTime";
                case 18:
                    return "dexoptPackages";
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
                parcel.enforceInterface(IIntelligentBatterySaverService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IIntelligentBatterySaverService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSqdUiControlEnabled(z);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    boolean zIsSqdUiControlEnabled = isSqdUiControlEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSqdUiControlEnabled);
                    return true;
                case 3:
                    boolean zIsSqdSupport = isSqdSupport();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSqdSupport);
                    return true;
                case 4:
                    int i3 = parcel.readInt();
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zAddSqdBlockList = addSqdBlockList(i3, string);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddSqdBlockList);
                    return true;
                case 5:
                    String string2 = parcel.readString();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zAddScreenQuickDimApp = addScreenQuickDimApp(string2, i4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddScreenQuickDimApp);
                    return true;
                case 6:
                    int i5 = parcel.readInt();
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zRemoveSqdBlockList = removeSqdBlockList(i5, string3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveSqdBlockList);
                    return true;
                case 7:
                    String string4 = parcel.readString();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zRemoveScreenQuickDimApp = removeScreenQuickDimApp(string4, i6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveScreenQuickDimApp);
                    return true;
                case 8:
                    Map sqdBlockList = getSqdBlockList();
                    parcel2.writeNoException();
                    parcel2.writeMap(sqdBlockList);
                    return true;
                case 9:
                    Map screenQuickDimApps = getScreenQuickDimApps();
                    parcel2.writeNoException();
                    parcel2.writeMap(screenQuickDimApps);
                    return true;
                case 10:
                    long[] gain = getGain();
                    parcel2.writeNoException();
                    parcel2.writeLongArray(gain);
                    return true;
                case 11:
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSarrUiControlEnable(z2);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSleepModeEnabled(z3);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    long j = parcel.readLong();
                    long j2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setSleepTime(j, j2);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setRubinEvent(string5);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    boolean zIsEnableSerive = isEnableSerive();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsEnableSerive);
                    return true;
                case 16:
                    Bundle operationHistory = getOperationHistory();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(operationHistory, 1);
                    return true;
                case 17:
                    Bundle sleepTime = getSleepTime();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(sleepTime, 1);
                    return true;
                case 18:
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    List listDexoptPackages = dexoptPackages(arrayListCreateStringArrayList);
                    parcel2.writeNoException();
                    parcel2.writeList(listDexoptPackages);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IIntelligentBatterySaverService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IIntelligentBatterySaverService.DESCRIPTOR;
            }

            @Override // android.os.IIntelligentBatterySaverService
            public void setSqdUiControlEnabled(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIntelligentBatterySaverService.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IIntelligentBatterySaverService
            public boolean isSqdUiControlEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIntelligentBatterySaverService.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IIntelligentBatterySaverService
            public boolean isSqdSupport() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIntelligentBatterySaverService.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IIntelligentBatterySaverService
            public boolean addSqdBlockList(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIntelligentBatterySaverService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IIntelligentBatterySaverService
            public boolean addScreenQuickDimApp(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIntelligentBatterySaverService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IIntelligentBatterySaverService
            public boolean removeSqdBlockList(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIntelligentBatterySaverService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IIntelligentBatterySaverService
            public boolean removeScreenQuickDimApp(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIntelligentBatterySaverService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IIntelligentBatterySaverService
            public Map getSqdBlockList() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIntelligentBatterySaverService.DESCRIPTOR);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readHashMap(getClass().getClassLoader());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IIntelligentBatterySaverService
            public Map getScreenQuickDimApps() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIntelligentBatterySaverService.DESCRIPTOR);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readHashMap(getClass().getClassLoader());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IIntelligentBatterySaverService
            public long[] getGain() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIntelligentBatterySaverService.DESCRIPTOR);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createLongArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IIntelligentBatterySaverService
            public void setSarrUiControlEnable(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIntelligentBatterySaverService.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IIntelligentBatterySaverService
            public void setSleepModeEnabled(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIntelligentBatterySaverService.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IIntelligentBatterySaverService
            public void setSleepTime(long j, long j2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIntelligentBatterySaverService.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeLong(j2);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IIntelligentBatterySaverService
            public void setRubinEvent(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIntelligentBatterySaverService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IIntelligentBatterySaverService
            public boolean isEnableSerive() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIntelligentBatterySaverService.DESCRIPTOR);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IIntelligentBatterySaverService
            public Bundle getOperationHistory() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIntelligentBatterySaverService.DESCRIPTOR);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IIntelligentBatterySaverService
            public Bundle getSleepTime() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIntelligentBatterySaverService.DESCRIPTOR);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IIntelligentBatterySaverService
            public List dexoptPackages(List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIntelligentBatterySaverService.DESCRIPTOR);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readArrayList(getClass().getClassLoader());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
