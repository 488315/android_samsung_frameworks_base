package android.content.pm;

import android.app.IApplicationThread;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.UserHandle;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public interface ICrossProfileApps extends IInterface {

    public static class Default implements ICrossProfileApps {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.content.pm.ICrossProfileApps
        public boolean canConfigureInteractAcrossProfiles(int i, String str) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.ICrossProfileApps
        public boolean canInteractAcrossProfiles(String str) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.ICrossProfileApps
        public boolean canRequestInteractAcrossProfiles(String str) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.ICrossProfileApps
        public boolean canUserAttemptToConfigureInteractAcrossProfiles(int i, String str) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.ICrossProfileApps
        public void clearInteractAcrossProfilesAppOps(int i) throws RemoteException {
        }

        @Override // android.content.pm.ICrossProfileApps
        public List<UserHandle> getTargetUserProfiles(String str) throws RemoteException {
            return null;
        }

        @Override // android.content.pm.ICrossProfileApps
        public void resetInteractAcrossProfilesAppOps(int i, List<String> list) throws RemoteException {
        }

        @Override // android.content.pm.ICrossProfileApps
        public void setInteractAcrossProfilesAppOp(int i, String str, int i2) throws RemoteException {
        }

        @Override // android.content.pm.ICrossProfileApps
        public void startActivityAsUser(IApplicationThread iApplicationThread, String str, String str2, ComponentName componentName, int i, boolean z, IBinder iBinder, Bundle bundle) throws RemoteException {
        }

        @Override // android.content.pm.ICrossProfileApps
        public void startActivityAsUserByIntent(IApplicationThread iApplicationThread, String str, String str2, Intent intent, int i, IBinder iBinder, Bundle bundle) throws RemoteException {
        }
    }

    boolean canConfigureInteractAcrossProfiles(int i, String str) throws RemoteException;

    boolean canInteractAcrossProfiles(String str) throws RemoteException;

    boolean canRequestInteractAcrossProfiles(String str) throws RemoteException;

    boolean canUserAttemptToConfigureInteractAcrossProfiles(int i, String str) throws RemoteException;

    void clearInteractAcrossProfilesAppOps(int i) throws RemoteException;

    List<UserHandle> getTargetUserProfiles(String str) throws RemoteException;

    void resetInteractAcrossProfilesAppOps(int i, List<String> list) throws RemoteException;

    void setInteractAcrossProfilesAppOp(int i, String str, int i2) throws RemoteException;

    void startActivityAsUser(IApplicationThread iApplicationThread, String str, String str2, ComponentName componentName, int i, boolean z, IBinder iBinder, Bundle bundle) throws RemoteException;

    void startActivityAsUserByIntent(IApplicationThread iApplicationThread, String str, String str2, Intent intent, int i, IBinder iBinder, Bundle bundle) throws RemoteException;

    public static abstract class Stub extends Binder implements ICrossProfileApps {
        public static final String DESCRIPTOR = "android.content.pm.ICrossProfileApps";
        static final int TRANSACTION_canConfigureInteractAcrossProfiles = 7;
        static final int TRANSACTION_canInteractAcrossProfiles = 4;
        static final int TRANSACTION_canRequestInteractAcrossProfiles = 5;
        static final int TRANSACTION_canUserAttemptToConfigureInteractAcrossProfiles = 8;
        static final int TRANSACTION_clearInteractAcrossProfilesAppOps = 10;
        static final int TRANSACTION_getTargetUserProfiles = 3;
        static final int TRANSACTION_resetInteractAcrossProfilesAppOps = 9;
        static final int TRANSACTION_setInteractAcrossProfilesAppOp = 6;
        static final int TRANSACTION_startActivityAsUser = 1;
        static final int TRANSACTION_startActivityAsUserByIntent = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 9;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ICrossProfileApps asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ICrossProfileApps)) {
                return (ICrossProfileApps) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "startActivityAsUser";
                case 2:
                    return "startActivityAsUserByIntent";
                case 3:
                    return "getTargetUserProfiles";
                case 4:
                    return "canInteractAcrossProfiles";
                case 5:
                    return "canRequestInteractAcrossProfiles";
                case 6:
                    return "setInteractAcrossProfilesAppOp";
                case 7:
                    return "canConfigureInteractAcrossProfiles";
                case 8:
                    return "canUserAttemptToConfigureInteractAcrossProfiles";
                case 9:
                    return "resetInteractAcrossProfilesAppOps";
                case 10:
                    return "clearInteractAcrossProfilesAppOps";
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    IApplicationThread asInterface = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    String readString = parcel.readString();
                    String readString2 = parcel.readString();
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    startActivityAsUser(asInterface, readString, readString2, componentName, readInt, readBoolean, readStrongBinder, bundle);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    IApplicationThread asInterface2 = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    String readString3 = parcel.readString();
                    String readString4 = parcel.readString();
                    Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    int readInt2 = parcel.readInt();
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    startActivityAsUserByIntent(asInterface2, readString3, readString4, intent, readInt2, readStrongBinder2, bundle2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<UserHandle> targetUserProfiles = getTargetUserProfiles(readString5);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(targetUserProfiles, 1);
                    return true;
                case 4:
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean canInteractAcrossProfiles = canInteractAcrossProfiles(readString6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canInteractAcrossProfiles);
                    return true;
                case 5:
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean canRequestInteractAcrossProfiles = canRequestInteractAcrossProfiles(readString7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canRequestInteractAcrossProfiles);
                    return true;
                case 6:
                    int readInt3 = parcel.readInt();
                    String readString8 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setInteractAcrossProfilesAppOp(readInt3, readString8, readInt4);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int readInt5 = parcel.readInt();
                    String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean canConfigureInteractAcrossProfiles = canConfigureInteractAcrossProfiles(readInt5, readString9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canConfigureInteractAcrossProfiles);
                    return true;
                case 8:
                    int readInt6 = parcel.readInt();
                    String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean canUserAttemptToConfigureInteractAcrossProfiles = canUserAttemptToConfigureInteractAcrossProfiles(readInt6, readString10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canUserAttemptToConfigureInteractAcrossProfiles);
                    return true;
                case 9:
                    int readInt7 = parcel.readInt();
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    resetInteractAcrossProfilesAppOps(readInt7, createStringArrayList);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearInteractAcrossProfilesAppOps(readInt8);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ICrossProfileApps {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.content.pm.ICrossProfileApps
            public void startActivityAsUser(IApplicationThread iApplicationThread, String str, String str2, ComponentName componentName, int i, boolean z, IBinder iBinder, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ICrossProfileApps
            public void startActivityAsUserByIntent(IApplicationThread iApplicationThread, String str, String str2, Intent intent, int i, IBinder iBinder, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iApplicationThread);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ICrossProfileApps
            public List<UserHandle> getTargetUserProfiles(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(UserHandle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ICrossProfileApps
            public boolean canInteractAcrossProfiles(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ICrossProfileApps
            public boolean canRequestInteractAcrossProfiles(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ICrossProfileApps
            public void setInteractAcrossProfilesAppOp(int i, String str, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeInt(i2);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ICrossProfileApps
            public boolean canConfigureInteractAcrossProfiles(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ICrossProfileApps
            public boolean canUserAttemptToConfigureInteractAcrossProfiles(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ICrossProfileApps
            public void resetInteractAcrossProfilesAppOps(int i, List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStringList(list);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.ICrossProfileApps
            public void clearInteractAcrossProfilesAppOps(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
