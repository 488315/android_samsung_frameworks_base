package android.hardware;

import android.hardware.ISensorPrivacyListener;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public interface ISensorPrivacyManager extends IInterface {
    public static final String DESCRIPTOR = "android.hardware.ISensorPrivacyManager";

    public static class Default implements ISensorPrivacyManager {
        @Override // android.hardware.ISensorPrivacyManager
        public void addSensorPrivacyListener(ISensorPrivacyListener iSensorPrivacyListener) throws RemoteException {
        }

        @Override // android.hardware.ISensorPrivacyManager
        public void addToggleSensorPrivacyListener(ISensorPrivacyListener iSensorPrivacyListener) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.ISensorPrivacyManager
        public List<String> getCameraPrivacyAllowlist() throws RemoteException {
            return null;
        }

        @Override // android.hardware.ISensorPrivacyManager
        public int getToggleSensorPrivacyState(int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.ISensorPrivacyManager
        public boolean isCameraPrivacyEnabled(String str) throws RemoteException {
            return false;
        }

        @Override // android.hardware.ISensorPrivacyManager
        public boolean isCombinedToggleSensorPrivacyEnabled(int i) throws RemoteException {
            return false;
        }

        @Override // android.hardware.ISensorPrivacyManager
        public boolean isSensorPrivacyEnabled() throws RemoteException {
            return false;
        }

        @Override // android.hardware.ISensorPrivacyManager
        public boolean isToggleSensorPrivacyEnabled(int i, int i2) throws RemoteException {
            return false;
        }

        @Override // android.hardware.ISensorPrivacyManager
        public void removeSensorPrivacyListener(ISensorPrivacyListener iSensorPrivacyListener) throws RemoteException {
        }

        @Override // android.hardware.ISensorPrivacyManager
        public void removeToggleSensorPrivacyListener(ISensorPrivacyListener iSensorPrivacyListener) throws RemoteException {
        }

        @Override // android.hardware.ISensorPrivacyManager
        public boolean requiresAuthentication() throws RemoteException {
            return false;
        }

        @Override // android.hardware.ISensorPrivacyManager
        public void setCameraPrivacyAllowlist(List<String> list) throws RemoteException {
        }

        @Override // android.hardware.ISensorPrivacyManager
        public void setSensorPrivacy(boolean z) throws RemoteException {
        }

        @Override // android.hardware.ISensorPrivacyManager
        public void setToggleSensorPrivacy(int i, int i2, int i3, boolean z) throws RemoteException {
        }

        @Override // android.hardware.ISensorPrivacyManager
        public void setToggleSensorPrivacyForProfileGroup(int i, int i2, int i3, boolean z) throws RemoteException {
        }

        @Override // android.hardware.ISensorPrivacyManager
        public void setToggleSensorPrivacyForProfileGroupWithConfirmPopup(int i, int i2, int i3, boolean z, int i4) throws RemoteException {
        }

        @Override // android.hardware.ISensorPrivacyManager
        public void setToggleSensorPrivacyState(int i, int i2, int i3, int i4) throws RemoteException {
        }

        @Override // android.hardware.ISensorPrivacyManager
        public void setToggleSensorPrivacyStateForProfileGroup(int i, int i2, int i3, int i4) throws RemoteException {
        }

        @Override // android.hardware.ISensorPrivacyManager
        public void showSensorUseDialog(int i) throws RemoteException {
        }

        @Override // android.hardware.ISensorPrivacyManager
        public boolean supportsSensorToggle(int i, int i2) throws RemoteException {
            return false;
        }

        @Override // android.hardware.ISensorPrivacyManager
        public void suppressToggleSensorPrivacyReminders(int i, int i2, IBinder iBinder, boolean z) throws RemoteException {
        }
    }

    void addSensorPrivacyListener(ISensorPrivacyListener iSensorPrivacyListener) throws RemoteException;

    void addToggleSensorPrivacyListener(ISensorPrivacyListener iSensorPrivacyListener) throws RemoteException;

    List<String> getCameraPrivacyAllowlist() throws RemoteException;

    int getToggleSensorPrivacyState(int i, int i2) throws RemoteException;

    boolean isCameraPrivacyEnabled(String str) throws RemoteException;

    boolean isCombinedToggleSensorPrivacyEnabled(int i) throws RemoteException;

    boolean isSensorPrivacyEnabled() throws RemoteException;

    boolean isToggleSensorPrivacyEnabled(int i, int i2) throws RemoteException;

    void removeSensorPrivacyListener(ISensorPrivacyListener iSensorPrivacyListener) throws RemoteException;

    void removeToggleSensorPrivacyListener(ISensorPrivacyListener iSensorPrivacyListener) throws RemoteException;

    boolean requiresAuthentication() throws RemoteException;

    void setCameraPrivacyAllowlist(List<String> list) throws RemoteException;

    void setSensorPrivacy(boolean z) throws RemoteException;

    void setToggleSensorPrivacy(int i, int i2, int i3, boolean z) throws RemoteException;

    void setToggleSensorPrivacyForProfileGroup(int i, int i2, int i3, boolean z) throws RemoteException;

    void setToggleSensorPrivacyForProfileGroupWithConfirmPopup(int i, int i2, int i3, boolean z, int i4) throws RemoteException;

    void setToggleSensorPrivacyState(int i, int i2, int i3, int i4) throws RemoteException;

    void setToggleSensorPrivacyStateForProfileGroup(int i, int i2, int i3, int i4) throws RemoteException;

    void showSensorUseDialog(int i) throws RemoteException;

    boolean supportsSensorToggle(int i, int i2) throws RemoteException;

    void suppressToggleSensorPrivacyReminders(int i, int i2, IBinder iBinder, boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements ISensorPrivacyManager {
        static final int TRANSACTION_addSensorPrivacyListener = 2;
        static final int TRANSACTION_addToggleSensorPrivacyListener = 3;
        static final int TRANSACTION_getCameraPrivacyAllowlist = 13;
        static final int TRANSACTION_getToggleSensorPrivacyState = 14;
        static final int TRANSACTION_isCameraPrivacyEnabled = 17;
        static final int TRANSACTION_isCombinedToggleSensorPrivacyEnabled = 7;
        static final int TRANSACTION_isSensorPrivacyEnabled = 6;
        static final int TRANSACTION_isToggleSensorPrivacyEnabled = 8;
        static final int TRANSACTION_removeSensorPrivacyListener = 4;
        static final int TRANSACTION_removeToggleSensorPrivacyListener = 5;
        static final int TRANSACTION_requiresAuthentication = 20;
        static final int TRANSACTION_setCameraPrivacyAllowlist = 18;
        static final int TRANSACTION_setSensorPrivacy = 9;
        static final int TRANSACTION_setToggleSensorPrivacy = 10;
        static final int TRANSACTION_setToggleSensorPrivacyForProfileGroup = 11;
        static final int TRANSACTION_setToggleSensorPrivacyForProfileGroupWithConfirmPopup = 12;
        static final int TRANSACTION_setToggleSensorPrivacyState = 15;
        static final int TRANSACTION_setToggleSensorPrivacyStateForProfileGroup = 16;
        static final int TRANSACTION_showSensorUseDialog = 21;
        static final int TRANSACTION_supportsSensorToggle = 1;
        static final int TRANSACTION_suppressToggleSensorPrivacyReminders = 19;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 20;
        }

        public Stub() {
            attachInterface(this, ISensorPrivacyManager.DESCRIPTOR);
        }

        public static ISensorPrivacyManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISensorPrivacyManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISensorPrivacyManager)) {
                return (ISensorPrivacyManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "supportsSensorToggle";
                case 2:
                    return "addSensorPrivacyListener";
                case 3:
                    return "addToggleSensorPrivacyListener";
                case 4:
                    return "removeSensorPrivacyListener";
                case 5:
                    return "removeToggleSensorPrivacyListener";
                case 6:
                    return "isSensorPrivacyEnabled";
                case 7:
                    return "isCombinedToggleSensorPrivacyEnabled";
                case 8:
                    return "isToggleSensorPrivacyEnabled";
                case 9:
                    return "setSensorPrivacy";
                case 10:
                    return "setToggleSensorPrivacy";
                case 11:
                    return "setToggleSensorPrivacyForProfileGroup";
                case 12:
                    return "setToggleSensorPrivacyForProfileGroupWithConfirmPopup";
                case 13:
                    return "getCameraPrivacyAllowlist";
                case 14:
                    return "getToggleSensorPrivacyState";
                case 15:
                    return "setToggleSensorPrivacyState";
                case 16:
                    return "setToggleSensorPrivacyStateForProfileGroup";
                case 17:
                    return "isCameraPrivacyEnabled";
                case 18:
                    return "setCameraPrivacyAllowlist";
                case 19:
                    return "suppressToggleSensorPrivacyReminders";
                case 20:
                    return "requiresAuthentication";
                case 21:
                    return "showSensorUseDialog";
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
                parcel.enforceInterface(ISensorPrivacyManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISensorPrivacyManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean supportsSensorToggle = supportsSensorToggle(readInt, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(supportsSensorToggle);
                    return true;
                case 2:
                    ISensorPrivacyListener asInterface = ISensorPrivacyListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addSensorPrivacyListener(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    ISensorPrivacyListener asInterface2 = ISensorPrivacyListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addToggleSensorPrivacyListener(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    ISensorPrivacyListener asInterface3 = ISensorPrivacyListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeSensorPrivacyListener(asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    ISensorPrivacyListener asInterface4 = ISensorPrivacyListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeToggleSensorPrivacyListener(asInterface4);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    boolean isSensorPrivacyEnabled = isSensorPrivacyEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSensorPrivacyEnabled);
                    return true;
                case 7:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isCombinedToggleSensorPrivacyEnabled = isCombinedToggleSensorPrivacyEnabled(readInt3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCombinedToggleSensorPrivacyEnabled);
                    return true;
                case 8:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isToggleSensorPrivacyEnabled = isToggleSensorPrivacyEnabled(readInt4, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isToggleSensorPrivacyEnabled);
                    return true;
                case 9:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSensorPrivacy(readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setToggleSensorPrivacy(readInt6, readInt7, readInt8, readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setToggleSensorPrivacyForProfileGroup(readInt9, readInt10, readInt11, readBoolean3);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    boolean readBoolean4 = parcel.readBoolean();
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setToggleSensorPrivacyForProfileGroupWithConfirmPopup(readInt12, readInt13, readInt14, readBoolean4, readInt15);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    List<String> cameraPrivacyAllowlist = getCameraPrivacyAllowlist();
                    parcel2.writeNoException();
                    parcel2.writeStringList(cameraPrivacyAllowlist);
                    return true;
                case 14:
                    int readInt16 = parcel.readInt();
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int toggleSensorPrivacyState = getToggleSensorPrivacyState(readInt16, readInt17);
                    parcel2.writeNoException();
                    parcel2.writeInt(toggleSensorPrivacyState);
                    return true;
                case 15:
                    int readInt18 = parcel.readInt();
                    int readInt19 = parcel.readInt();
                    int readInt20 = parcel.readInt();
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setToggleSensorPrivacyState(readInt18, readInt19, readInt20, readInt21);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    int readInt22 = parcel.readInt();
                    int readInt23 = parcel.readInt();
                    int readInt24 = parcel.readInt();
                    int readInt25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setToggleSensorPrivacyStateForProfileGroup(readInt22, readInt23, readInt24, readInt25);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isCameraPrivacyEnabled = isCameraPrivacyEnabled(readString);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCameraPrivacyEnabled);
                    return true;
                case 18:
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    setCameraPrivacyAllowlist(createStringArrayList);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    int readInt26 = parcel.readInt();
                    int readInt27 = parcel.readInt();
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    suppressToggleSensorPrivacyReminders(readInt26, readInt27, readStrongBinder, readBoolean5);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    boolean requiresAuthentication = requiresAuthentication();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requiresAuthentication);
                    return true;
                case 21:
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    showSensorUseDialog(readInt28);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISensorPrivacyManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISensorPrivacyManager.DESCRIPTOR;
            }

            @Override // android.hardware.ISensorPrivacyManager
            public boolean supportsSensorToggle(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public void addSensorPrivacyListener(ISensorPrivacyListener iSensorPrivacyListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iSensorPrivacyListener);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public void addToggleSensorPrivacyListener(ISensorPrivacyListener iSensorPrivacyListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iSensorPrivacyListener);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public void removeSensorPrivacyListener(ISensorPrivacyListener iSensorPrivacyListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iSensorPrivacyListener);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public void removeToggleSensorPrivacyListener(ISensorPrivacyListener iSensorPrivacyListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iSensorPrivacyListener);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public boolean isSensorPrivacyEnabled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public boolean isCombinedToggleSensorPrivacyEnabled(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public boolean isToggleSensorPrivacyEnabled(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public void setSensorPrivacy(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public void setToggleSensorPrivacy(int i, int i2, int i3, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public void setToggleSensorPrivacyForProfileGroup(int i, int i2, int i3, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public void setToggleSensorPrivacyForProfileGroupWithConfirmPopup(int i, int i2, int i3, boolean z, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i4);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public List<String> getCameraPrivacyAllowlist() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public int getToggleSensorPrivacyState(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public void setToggleSensorPrivacyState(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public void setToggleSensorPrivacyStateForProfileGroup(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public boolean isCameraPrivacyEnabled(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public void setCameraPrivacyAllowlist(List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    obtain.writeStringList(list);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public void suppressToggleSensorPrivacyReminders(int i, int i2, IBinder iBinder, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public boolean requiresAuthentication() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public void showSensorUseDialog(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
