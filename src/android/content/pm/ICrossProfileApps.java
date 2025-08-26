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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ICrossProfileApps)) {
                return (ICrossProfileApps) iInterfaceQueryLocalInterface;
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
                    IApplicationThread iApplicationThreadAsInterface = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    String string = parcel.readString();
                    String string2 = parcel.readString();
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int i3 = parcel.readInt();
                    boolean z = parcel.readBoolean();
                    IBinder strongBinder = parcel.readStrongBinder();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    startActivityAsUser(iApplicationThreadAsInterface, string, string2, componentName, i3, z, strongBinder, bundle);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    IApplicationThread iApplicationThreadAsInterface2 = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
                    String string3 = parcel.readString();
                    String string4 = parcel.readString();
                    Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    int i4 = parcel.readInt();
                    IBinder strongBinder2 = parcel.readStrongBinder();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    startActivityAsUserByIntent(iApplicationThreadAsInterface2, string3, string4, intent, i4, strongBinder2, bundle2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<UserHandle> targetUserProfiles = getTargetUserProfiles(string5);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(targetUserProfiles, 1);
                    return true;
                case 4:
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zCanInteractAcrossProfiles = canInteractAcrossProfiles(string6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCanInteractAcrossProfiles);
                    return true;
                case 5:
                    String string7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zCanRequestInteractAcrossProfiles = canRequestInteractAcrossProfiles(string7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCanRequestInteractAcrossProfiles);
                    return true;
                case 6:
                    int i5 = parcel.readInt();
                    String string8 = parcel.readString();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setInteractAcrossProfilesAppOp(i5, string8, i6);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int i7 = parcel.readInt();
                    String string9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zCanConfigureInteractAcrossProfiles = canConfigureInteractAcrossProfiles(i7, string9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCanConfigureInteractAcrossProfiles);
                    return true;
                case 8:
                    int i8 = parcel.readInt();
                    String string10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zCanUserAttemptToConfigureInteractAcrossProfiles = canUserAttemptToConfigureInteractAcrossProfiles(i8, string10);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCanUserAttemptToConfigureInteractAcrossProfiles);
                    return true;
                case 9:
                    int i9 = parcel.readInt();
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    resetInteractAcrossProfilesAppOps(i9, arrayListCreateStringArrayList);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    clearInteractAcrossProfilesAppOps(i10);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iApplicationThread);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.ICrossProfileApps
            public void startActivityAsUserByIntent(IApplicationThread iApplicationThread, String str, String str2, Intent intent, int i, IBinder iBinder, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iApplicationThread);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(intent, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.ICrossProfileApps
            public List<UserHandle> getTargetUserProfiles(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(UserHandle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.ICrossProfileApps
            public boolean canInteractAcrossProfiles(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.ICrossProfileApps
            public boolean canRequestInteractAcrossProfiles(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.ICrossProfileApps
            public void setInteractAcrossProfilesAppOp(int i, String str, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.ICrossProfileApps
            public boolean canConfigureInteractAcrossProfiles(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.ICrossProfileApps
            public boolean canUserAttemptToConfigureInteractAcrossProfiles(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.ICrossProfileApps
            public void resetInteractAcrossProfilesAppOps(int i, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.content.pm.ICrossProfileApps
            public void clearInteractAcrossProfilesAppOps(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
