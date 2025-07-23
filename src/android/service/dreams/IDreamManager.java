package android.service.dreams;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IDreamManager extends IInterface {

    public static class Default implements IDreamManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.dreams.IDreamManager
        public void awaken() throws RemoteException {
        }

        @Override // android.service.dreams.IDreamManager
        public boolean canStartDreaming(boolean z) throws RemoteException {
            return false;
        }

        @Override // android.service.dreams.IDreamManager
        public void dream() throws RemoteException {
        }

        @Override // android.service.dreams.IDreamManager
        public void finishSelf(IBinder iBinder, boolean z) throws RemoteException {
        }

        @Override // android.service.dreams.IDreamManager
        public void finishSelfOneway(IBinder iBinder, boolean z) throws RemoteException {
        }

        @Override // android.service.dreams.IDreamManager
        public void forceAmbientDisplayEnabled(boolean z) throws RemoteException {
        }

        @Override // android.service.dreams.IDreamManager
        public ComponentName getDefaultDreamComponentForUser(int i) throws RemoteException {
            return null;
        }

        @Override // android.service.dreams.IDreamManager
        public ComponentName[] getDreamComponents() throws RemoteException {
            return null;
        }

        @Override // android.service.dreams.IDreamManager
        public ComponentName[] getDreamComponentsForUser(int i) throws RemoteException {
            return null;
        }

        @Override // android.service.dreams.IDreamManager
        public boolean isDreaming() throws RemoteException {
            return false;
        }

        @Override // android.service.dreams.IDreamManager
        public boolean isDreamingOrInPreview() throws RemoteException {
            return false;
        }

        @Override // android.service.dreams.IDreamManager
        public void registerDreamOverlayService(ComponentName componentName) throws RemoteException {
        }

        @Override // android.service.dreams.IDreamManager
        public void semStartDozing(IBinder iBinder, int i, int i2, int i3, int i4, boolean z) throws RemoteException {
        }

        @Override // android.service.dreams.IDreamManager
        public void setDevicePostured(boolean z) throws RemoteException {
        }

        @Override // android.service.dreams.IDreamManager
        public void setDreamComponents(ComponentName[] componentNameArr) throws RemoteException {
        }

        @Override // android.service.dreams.IDreamManager
        public void setDreamComponentsForUser(int i, ComponentName[] componentNameArr) throws RemoteException {
        }

        @Override // android.service.dreams.IDreamManager
        public void setDreamIsObscured(boolean z) throws RemoteException {
        }

        @Override // android.service.dreams.IDreamManager
        public void setScreensaverEnabled(boolean z) throws RemoteException {
        }

        @Override // android.service.dreams.IDreamManager
        public void setSystemDreamComponent(ComponentName componentName) throws RemoteException {
        }

        @Override // android.service.dreams.IDreamManager
        public void startDozing(IBinder iBinder, int i, int i2, float f, int i3, boolean z) throws RemoteException {
        }

        @Override // android.service.dreams.IDreamManager
        public void startDozingOneway(IBinder iBinder, int i, int i2, float f, int i3, boolean z) throws RemoteException {
        }

        @Override // android.service.dreams.IDreamManager
        public void startDreamActivity(Intent intent) throws RemoteException {
        }

        @Override // android.service.dreams.IDreamManager
        public void stopDozing(IBinder iBinder) throws RemoteException {
        }

        @Override // android.service.dreams.IDreamManager
        public void testDream(int i, ComponentName componentName) throws RemoteException {
        }
    }

    void awaken() throws RemoteException;

    boolean canStartDreaming(boolean z) throws RemoteException;

    void dream() throws RemoteException;

    @Deprecated
    void finishSelf(IBinder iBinder, boolean z) throws RemoteException;

    void finishSelfOneway(IBinder iBinder, boolean z) throws RemoteException;

    void forceAmbientDisplayEnabled(boolean z) throws RemoteException;

    ComponentName getDefaultDreamComponentForUser(int i) throws RemoteException;

    ComponentName[] getDreamComponents() throws RemoteException;

    ComponentName[] getDreamComponentsForUser(int i) throws RemoteException;

    boolean isDreaming() throws RemoteException;

    boolean isDreamingOrInPreview() throws RemoteException;

    void registerDreamOverlayService(ComponentName componentName) throws RemoteException;

    void semStartDozing(IBinder iBinder, int i, int i2, int i3, int i4, boolean z) throws RemoteException;

    void setDevicePostured(boolean z) throws RemoteException;

    void setDreamComponents(ComponentName[] componentNameArr) throws RemoteException;

    void setDreamComponentsForUser(int i, ComponentName[] componentNameArr) throws RemoteException;

    void setDreamIsObscured(boolean z) throws RemoteException;

    void setScreensaverEnabled(boolean z) throws RemoteException;

    void setSystemDreamComponent(ComponentName componentName) throws RemoteException;

    @Deprecated
    void startDozing(IBinder iBinder, int i, int i2, float f, int i3, boolean z) throws RemoteException;

    void startDozingOneway(IBinder iBinder, int i, int i2, float f, int i3, boolean z) throws RemoteException;

    void startDreamActivity(Intent intent) throws RemoteException;

    void stopDozing(IBinder iBinder) throws RemoteException;

    void testDream(int i, ComponentName componentName) throws RemoteException;

    public static abstract class Stub extends Binder implements IDreamManager {
        public static final String DESCRIPTOR = "android.service.dreams.IDreamManager";
        static final int TRANSACTION_awaken = 2;
        static final int TRANSACTION_canStartDreaming = 9;
        static final int TRANSACTION_dream = 1;
        static final int TRANSACTION_finishSelf = 10;
        static final int TRANSACTION_finishSelfOneway = 22;
        static final int TRANSACTION_forceAmbientDisplayEnabled = 13;
        static final int TRANSACTION_getDefaultDreamComponentForUser = 5;
        static final int TRANSACTION_getDreamComponents = 4;
        static final int TRANSACTION_getDreamComponentsForUser = 14;
        static final int TRANSACTION_isDreaming = 7;
        static final int TRANSACTION_isDreamingOrInPreview = 8;
        static final int TRANSACTION_registerDreamOverlayService = 17;
        static final int TRANSACTION_semStartDozing = 24;
        static final int TRANSACTION_setDevicePostured = 20;
        static final int TRANSACTION_setDreamComponents = 3;
        static final int TRANSACTION_setDreamComponentsForUser = 15;
        static final int TRANSACTION_setDreamIsObscured = 19;
        static final int TRANSACTION_setScreensaverEnabled = 23;
        static final int TRANSACTION_setSystemDreamComponent = 16;
        static final int TRANSACTION_startDozing = 11;
        static final int TRANSACTION_startDozingOneway = 21;
        static final int TRANSACTION_startDreamActivity = 18;
        static final int TRANSACTION_stopDozing = 12;
        static final int TRANSACTION_testDream = 6;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 23;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IDreamManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IDreamManager)) {
                return (IDreamManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "dream";
                case 2:
                    return "awaken";
                case 3:
                    return "setDreamComponents";
                case 4:
                    return "getDreamComponents";
                case 5:
                    return "getDefaultDreamComponentForUser";
                case 6:
                    return "testDream";
                case 7:
                    return "isDreaming";
                case 8:
                    return "isDreamingOrInPreview";
                case 9:
                    return "canStartDreaming";
                case 10:
                    return "finishSelf";
                case 11:
                    return "startDozing";
                case 12:
                    return "stopDozing";
                case 13:
                    return "forceAmbientDisplayEnabled";
                case 14:
                    return "getDreamComponentsForUser";
                case 15:
                    return "setDreamComponentsForUser";
                case 16:
                    return "setSystemDreamComponent";
                case 17:
                    return "registerDreamOverlayService";
                case 18:
                    return "startDreamActivity";
                case 19:
                    return "setDreamIsObscured";
                case 20:
                    return "setDevicePostured";
                case 21:
                    return "startDozingOneway";
                case 22:
                    return "finishSelfOneway";
                case 23:
                    return "setScreensaverEnabled";
                case 24:
                    return "semStartDozing";
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
                    dream();
                    parcel2.writeNoException();
                    return true;
                case 2:
                    awaken();
                    parcel2.writeNoException();
                    return true;
                case 3:
                    ComponentName[] componentNameArr = (ComponentName[]) parcel.createTypedArray(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDreamComponents(componentNameArr);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    ComponentName[] dreamComponents = getDreamComponents();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(dreamComponents, 1);
                    return true;
                case 5:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ComponentName defaultDreamComponentForUser = getDefaultDreamComponentForUser(readInt);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(defaultDreamComponentForUser, 1);
                    return true;
                case 6:
                    int readInt2 = parcel.readInt();
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    testDream(readInt2, componentName);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    boolean isDreaming = isDreaming();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDreaming);
                    return true;
                case 8:
                    boolean isDreamingOrInPreview = isDreamingOrInPreview();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDreamingOrInPreview);
                    return true;
                case 9:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean canStartDreaming = canStartDreaming(readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canStartDreaming);
                    return true;
                case 10:
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    finishSelf(readStrongBinder, readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    float readFloat = parcel.readFloat();
                    int readInt5 = parcel.readInt();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    startDozing(readStrongBinder2, readInt3, readInt4, readFloat, readInt5, readBoolean3);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    IBinder readStrongBinder3 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    stopDozing(readStrongBinder3);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    forceAmbientDisplayEnabled(readBoolean4);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ComponentName[] dreamComponentsForUser = getDreamComponentsForUser(readInt6);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(dreamComponentsForUser, 1);
                    return true;
                case 15:
                    int readInt7 = parcel.readInt();
                    ComponentName[] componentNameArr2 = (ComponentName[]) parcel.createTypedArray(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDreamComponentsForUser(readInt7, componentNameArr2);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    ComponentName componentName2 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    setSystemDreamComponent(componentName2);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    ComponentName componentName3 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    registerDreamOverlayService(componentName3);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    parcel.enforceNoDataAvail();
                    startDreamActivity(intent);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDreamIsObscured(readBoolean5);
                    return true;
                case 20:
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDevicePostured(readBoolean6);
                    return true;
                case 21:
                    IBinder readStrongBinder4 = parcel.readStrongBinder();
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    float readFloat2 = parcel.readFloat();
                    int readInt10 = parcel.readInt();
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    startDozingOneway(readStrongBinder4, readInt8, readInt9, readFloat2, readInt10, readBoolean7);
                    return true;
                case 22:
                    IBinder readStrongBinder5 = parcel.readStrongBinder();
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    finishSelfOneway(readStrongBinder5, readBoolean8);
                    return true;
                case 23:
                    boolean readBoolean9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setScreensaverEnabled(readBoolean9);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    IBinder readStrongBinder6 = parcel.readStrongBinder();
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    boolean readBoolean10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    semStartDozing(readStrongBinder6, readInt11, readInt12, readInt13, readInt14, readBoolean10);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IDreamManager {
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

            @Override // android.service.dreams.IDreamManager
            public void dream() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public void awaken() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public void setDreamComponents(ComponentName[] componentNameArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedArray(componentNameArr, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public ComponentName[] getDreamComponents() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ComponentName[]) obtain2.createTypedArray(ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public ComponentName getDefaultDreamComponentForUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ComponentName) obtain2.readTypedObject(ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public void testDream(int i, ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public boolean isDreaming() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public boolean isDreamingOrInPreview() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public boolean canStartDreaming(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public void finishSelf(IBinder iBinder, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public void startDozing(IBinder iBinder, int i, int i2, float f, int i3, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeFloat(f);
                    obtain.writeInt(i3);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public void stopDozing(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public void forceAmbientDisplayEnabled(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public ComponentName[] getDreamComponentsForUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ComponentName[]) obtain2.createTypedArray(ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public void setDreamComponentsForUser(int i, ComponentName[] componentNameArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedArray(componentNameArr, 0);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public void setSystemDreamComponent(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public void registerDreamOverlayService(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public void startDreamActivity(Intent intent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public void setDreamIsObscured(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(19, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public void setDevicePostured(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(20, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public void startDozingOneway(IBinder iBinder, int i, int i2, float f, int i3, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeFloat(f);
                    obtain.writeInt(i3);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(21, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public void finishSelfOneway(IBinder iBinder, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(22, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public void setScreensaverEnabled(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public void semStartDozing(IBinder iBinder, int i, int i2, int i3, int i4, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
