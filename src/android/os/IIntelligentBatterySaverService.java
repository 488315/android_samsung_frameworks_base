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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IIntelligentBatterySaverService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IIntelligentBatterySaverService)) {
                return (IIntelligentBatterySaverService) queryLocalInterface;
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
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSqdUiControlEnabled(readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    boolean isSqdUiControlEnabled = isSqdUiControlEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSqdUiControlEnabled);
                    return true;
                case 3:
                    boolean isSqdSupport = isSqdSupport();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSqdSupport);
                    return true;
                case 4:
                    int readInt = parcel.readInt();
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean addSqdBlockList = addSqdBlockList(readInt, readString);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addSqdBlockList);
                    return true;
                case 5:
                    String readString2 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean addScreenQuickDimApp = addScreenQuickDimApp(readString2, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addScreenQuickDimApp);
                    return true;
                case 6:
                    int readInt3 = parcel.readInt();
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean removeSqdBlockList = removeSqdBlockList(readInt3, readString3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeSqdBlockList);
                    return true;
                case 7:
                    String readString4 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean removeScreenQuickDimApp = removeScreenQuickDimApp(readString4, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeScreenQuickDimApp);
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
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSarrUiControlEnable(readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSleepModeEnabled(readBoolean3);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    long readLong = parcel.readLong();
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    setSleepTime(readLong, readLong2);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setRubinEvent(readString5);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    boolean isEnableSerive = isEnableSerive();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isEnableSerive);
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
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    List dexoptPackages = dexoptPackages(createStringArrayList);
                    parcel2.writeNoException();
                    parcel2.writeList(dexoptPackages);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIntelligentBatterySaverService.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IIntelligentBatterySaverService
            public boolean isSqdUiControlEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIntelligentBatterySaverService.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IIntelligentBatterySaverService
            public boolean isSqdSupport() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIntelligentBatterySaverService.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IIntelligentBatterySaverService
            public boolean addSqdBlockList(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIntelligentBatterySaverService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IIntelligentBatterySaverService
            public boolean addScreenQuickDimApp(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIntelligentBatterySaverService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IIntelligentBatterySaverService
            public boolean removeSqdBlockList(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIntelligentBatterySaverService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IIntelligentBatterySaverService
            public boolean removeScreenQuickDimApp(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIntelligentBatterySaverService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IIntelligentBatterySaverService
            public Map getSqdBlockList() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIntelligentBatterySaverService.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readHashMap(getClass().getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IIntelligentBatterySaverService
            public Map getScreenQuickDimApps() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIntelligentBatterySaverService.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readHashMap(getClass().getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IIntelligentBatterySaverService
            public long[] getGain() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIntelligentBatterySaverService.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createLongArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IIntelligentBatterySaverService
            public void setSarrUiControlEnable(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIntelligentBatterySaverService.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IIntelligentBatterySaverService
            public void setSleepModeEnabled(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIntelligentBatterySaverService.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IIntelligentBatterySaverService
            public void setSleepTime(long j, long j2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIntelligentBatterySaverService.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IIntelligentBatterySaverService
            public void setRubinEvent(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIntelligentBatterySaverService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IIntelligentBatterySaverService
            public boolean isEnableSerive() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIntelligentBatterySaverService.DESCRIPTOR);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IIntelligentBatterySaverService
            public Bundle getOperationHistory() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIntelligentBatterySaverService.DESCRIPTOR);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IIntelligentBatterySaverService
            public Bundle getSleepTime() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIntelligentBatterySaverService.DESCRIPTOR);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IIntelligentBatterySaverService
            public List dexoptPackages(List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIntelligentBatterySaverService.DESCRIPTOR);
                    obtain.writeStringList(list);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readArrayList(getClass().getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
