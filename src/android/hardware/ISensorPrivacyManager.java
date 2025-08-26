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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISensorPrivacyManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISensorPrivacyManager)) {
                return (ISensorPrivacyManager) iInterfaceQueryLocalInterface;
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
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zSupportsSensorToggle = supportsSensorToggle(i3, i4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSupportsSensorToggle);
                    return true;
                case 2:
                    ISensorPrivacyListener iSensorPrivacyListenerAsInterface = ISensorPrivacyListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addSensorPrivacyListener(iSensorPrivacyListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    ISensorPrivacyListener iSensorPrivacyListenerAsInterface2 = ISensorPrivacyListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addToggleSensorPrivacyListener(iSensorPrivacyListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    ISensorPrivacyListener iSensorPrivacyListenerAsInterface3 = ISensorPrivacyListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeSensorPrivacyListener(iSensorPrivacyListenerAsInterface3);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    ISensorPrivacyListener iSensorPrivacyListenerAsInterface4 = ISensorPrivacyListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeToggleSensorPrivacyListener(iSensorPrivacyListenerAsInterface4);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    boolean zIsSensorPrivacyEnabled = isSensorPrivacyEnabled();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSensorPrivacyEnabled);
                    return true;
                case 7:
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsCombinedToggleSensorPrivacyEnabled = isCombinedToggleSensorPrivacyEnabled(i5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCombinedToggleSensorPrivacyEnabled);
                    return true;
                case 8:
                    int i6 = parcel.readInt();
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsToggleSensorPrivacyEnabled = isToggleSensorPrivacyEnabled(i6, i7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsToggleSensorPrivacyEnabled);
                    return true;
                case 9:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setSensorPrivacy(z);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int i8 = parcel.readInt();
                    int i9 = parcel.readInt();
                    int i10 = parcel.readInt();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setToggleSensorPrivacy(i8, i9, i10, z2);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int i11 = parcel.readInt();
                    int i12 = parcel.readInt();
                    int i13 = parcel.readInt();
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setToggleSensorPrivacyForProfileGroup(i11, i12, i13, z3);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    int i14 = parcel.readInt();
                    int i15 = parcel.readInt();
                    int i16 = parcel.readInt();
                    boolean z4 = parcel.readBoolean();
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setToggleSensorPrivacyForProfileGroupWithConfirmPopup(i14, i15, i16, z4, i17);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    List<String> cameraPrivacyAllowlist = getCameraPrivacyAllowlist();
                    parcel2.writeNoException();
                    parcel2.writeStringList(cameraPrivacyAllowlist);
                    return true;
                case 14:
                    int i18 = parcel.readInt();
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int toggleSensorPrivacyState = getToggleSensorPrivacyState(i18, i19);
                    parcel2.writeNoException();
                    parcel2.writeInt(toggleSensorPrivacyState);
                    return true;
                case 15:
                    int i20 = parcel.readInt();
                    int i21 = parcel.readInt();
                    int i22 = parcel.readInt();
                    int i23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setToggleSensorPrivacyState(i20, i21, i22, i23);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    int i24 = parcel.readInt();
                    int i25 = parcel.readInt();
                    int i26 = parcel.readInt();
                    int i27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setToggleSensorPrivacyStateForProfileGroup(i24, i25, i26, i27);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsCameraPrivacyEnabled = isCameraPrivacyEnabled(string);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCameraPrivacyEnabled);
                    return true;
                case 18:
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    setCameraPrivacyAllowlist(arrayListCreateStringArrayList);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    int i28 = parcel.readInt();
                    int i29 = parcel.readInt();
                    IBinder strongBinder = parcel.readStrongBinder();
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    suppressToggleSensorPrivacyReminders(i28, i29, strongBinder, z5);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    boolean zRequiresAuthentication = requiresAuthentication();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRequiresAuthentication);
                    return true;
                case 21:
                    int i30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    showSensorUseDialog(i30);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public void addSensorPrivacyListener(ISensorPrivacyListener iSensorPrivacyListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSensorPrivacyListener);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public void addToggleSensorPrivacyListener(ISensorPrivacyListener iSensorPrivacyListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSensorPrivacyListener);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public void removeSensorPrivacyListener(ISensorPrivacyListener iSensorPrivacyListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSensorPrivacyListener);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public void removeToggleSensorPrivacyListener(ISensorPrivacyListener iSensorPrivacyListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSensorPrivacyListener);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public boolean isSensorPrivacyEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public boolean isCombinedToggleSensorPrivacyEnabled(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public boolean isToggleSensorPrivacyEnabled(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public void setSensorPrivacy(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public void setToggleSensorPrivacy(int i, int i2, int i3, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public void setToggleSensorPrivacyForProfileGroup(int i, int i2, int i3, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public void setToggleSensorPrivacyForProfileGroupWithConfirmPopup(int i, int i2, int i3, boolean z, int i4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i4);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public List<String> getCameraPrivacyAllowlist() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public int getToggleSensorPrivacyState(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public void setToggleSensorPrivacyState(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public void setToggleSensorPrivacyStateForProfileGroup(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public boolean isCameraPrivacyEnabled(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public void setCameraPrivacyAllowlist(List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public void suppressToggleSensorPrivacyReminders(int i, int i2, IBinder iBinder, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public boolean requiresAuthentication() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.ISensorPrivacyManager
            public void showSensorUseDialog(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISensorPrivacyManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
