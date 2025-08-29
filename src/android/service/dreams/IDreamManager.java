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
        public void semStartDozingOneWay(IBinder iBinder, int i, int i2, float f, int i3, boolean z, int i4, boolean z2) throws RemoteException {
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

    void semStartDozingOneWay(IBinder iBinder, int i, int i2, float f, int i3, boolean z, int i4, boolean z2) throws RemoteException;

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
        static final int TRANSACTION_semStartDozingOneWay = 24;
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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IDreamManager)) {
                return (IDreamManager) iInterfaceQueryLocalInterface;
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
                    return "semStartDozingOneWay";
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
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ComponentName defaultDreamComponentForUser = getDefaultDreamComponentForUser(i3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(defaultDreamComponentForUser, 1);
                    return true;
                case 6:
                    int i4 = parcel.readInt();
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    testDream(i4, componentName);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    boolean zIsDreaming = isDreaming();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsDreaming);
                    return true;
                case 8:
                    boolean zIsDreamingOrInPreview = isDreamingOrInPreview();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsDreamingOrInPreview);
                    return true;
                case 9:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zCanStartDreaming = canStartDreaming(z);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCanStartDreaming);
                    return true;
                case 10:
                    IBinder strongBinder = parcel.readStrongBinder();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    finishSelf(strongBinder, z2);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    IBinder strongBinder2 = parcel.readStrongBinder();
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    float f = parcel.readFloat();
                    int i7 = parcel.readInt();
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    startDozing(strongBinder2, i5, i6, f, i7, z3);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    IBinder strongBinder3 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    stopDozing(strongBinder3);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    forceAmbientDisplayEnabled(z4);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ComponentName[] dreamComponentsForUser = getDreamComponentsForUser(i8);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(dreamComponentsForUser, 1);
                    return true;
                case 15:
                    int i9 = parcel.readInt();
                    ComponentName[] componentNameArr2 = (ComponentName[]) parcel.createTypedArray(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    setDreamComponentsForUser(i9, componentNameArr2);
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
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDreamIsObscured(z5);
                    return true;
                case 20:
                    boolean z6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setDevicePostured(z6);
                    return true;
                case 21:
                    IBinder strongBinder4 = parcel.readStrongBinder();
                    int i10 = parcel.readInt();
                    int i11 = parcel.readInt();
                    float f2 = parcel.readFloat();
                    int i12 = parcel.readInt();
                    boolean z7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    startDozingOneway(strongBinder4, i10, i11, f2, i12, z7);
                    return true;
                case 22:
                    IBinder strongBinder5 = parcel.readStrongBinder();
                    boolean z8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    finishSelfOneway(strongBinder5, z8);
                    return true;
                case 23:
                    boolean z9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setScreensaverEnabled(z9);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    IBinder strongBinder6 = parcel.readStrongBinder();
                    int i13 = parcel.readInt();
                    int i14 = parcel.readInt();
                    float f3 = parcel.readFloat();
                    int i15 = parcel.readInt();
                    boolean z10 = parcel.readBoolean();
                    int i16 = parcel.readInt();
                    boolean z11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    semStartDozingOneWay(strongBinder6, i13, i14, f3, i15, z10, i16, z11);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public void awaken() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public void setDreamComponents(ComponentName[] componentNameArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedArray(componentNameArr, 0);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public ComponentName[] getDreamComponents() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ComponentName[]) parcelObtain2.createTypedArray(ComponentName.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public ComponentName getDefaultDreamComponentForUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ComponentName) parcelObtain2.readTypedObject(ComponentName.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public void testDream(int i, ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public boolean isDreaming() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public boolean isDreamingOrInPreview() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public boolean canStartDreaming(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public void finishSelf(IBinder iBinder, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public void startDozing(IBinder iBinder, int i, int i2, float f, int i3, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeFloat(f);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public void stopDozing(IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public void forceAmbientDisplayEnabled(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public ComponentName[] getDreamComponentsForUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ComponentName[]) parcelObtain2.createTypedArray(ComponentName.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public void setDreamComponentsForUser(int i, ComponentName[] componentNameArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedArray(componentNameArr, 0);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public void setSystemDreamComponent(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public void registerDreamOverlayService(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public void startDreamActivity(Intent intent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(intent, 0);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public void setDreamIsObscured(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(19, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public void setDevicePostured(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(20, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public void startDozingOneway(IBinder iBinder, int i, int i2, float f, int i3, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeFloat(f);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(21, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public void finishSelfOneway(IBinder iBinder, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(22, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public void setScreensaverEnabled(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.dreams.IDreamManager
            public void semStartDozingOneWay(IBinder iBinder, int i, int i2, float f, int i3, boolean z, int i4, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeFloat(f);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i4);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(24, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
