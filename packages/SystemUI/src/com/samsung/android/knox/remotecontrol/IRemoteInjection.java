package com.samsung.android.knox.remotecontrol;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.KeyEvent;
import android.view.MotionEvent;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.remotecontrol.IRemoteScreenWatcherCallback;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface IRemoteInjection extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.remotecontrol.IRemoteInjection";

    boolean addRemoteScreenWatcherCallback(IRemoteScreenWatcherCallback iRemoteScreenWatcherCallback) throws RemoteException;

    boolean allowRemoteControl(ContextInfo contextInfo, boolean z, boolean z2) throws RemoteException;

    boolean injectKeyEvent(KeyEvent keyEvent, boolean z) throws RemoteException;

    boolean injectPointerEvent(MotionEvent motionEvent, boolean z) throws RemoteException;

    boolean injectTrackballEvent(MotionEvent motionEvent, boolean z) throws RemoteException;

    boolean isRemoteControlAllowed(ContextInfo contextInfo) throws RemoteException;

    boolean isRemoteControlDisabled(int i) throws RemoteException;

    void updateRemoteScreenDimensionsAndCallerUid(int i, int i2, int i3) throws RemoteException;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Stub extends Binder implements IRemoteInjection {
        public static final int TRANSACTION_addRemoteScreenWatcherCallback = 8;
        public static final int TRANSACTION_allowRemoteControl = 4;
        public static final int TRANSACTION_injectKeyEvent = 1;
        public static final int TRANSACTION_injectPointerEvent = 2;
        public static final int TRANSACTION_injectTrackballEvent = 3;
        public static final int TRANSACTION_isRemoteControlAllowed = 5;
        public static final int TRANSACTION_isRemoteControlDisabled = 7;
        public static final int TRANSACTION_updateRemoteScreenDimensionsAndCallerUid = 6;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        class Proxy implements IRemoteInjection {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.remotecontrol.IRemoteInjection
            public boolean addRemoteScreenWatcherCallback(IRemoteScreenWatcherCallback iRemoteScreenWatcherCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRemoteInjection.DESCRIPTOR);
                    obtain.writeStrongInterface(iRemoteScreenWatcherCallback);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.remotecontrol.IRemoteInjection
            public boolean allowRemoteControl(ContextInfo contextInfo, boolean z, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRemoteInjection.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRemoteInjection.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.remotecontrol.IRemoteInjection
            public boolean injectKeyEvent(KeyEvent keyEvent, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRemoteInjection.DESCRIPTOR);
                    obtain.writeTypedObject(keyEvent, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.remotecontrol.IRemoteInjection
            public boolean injectPointerEvent(MotionEvent motionEvent, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRemoteInjection.DESCRIPTOR);
                    obtain.writeTypedObject(motionEvent, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.remotecontrol.IRemoteInjection
            public boolean injectTrackballEvent(MotionEvent motionEvent, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRemoteInjection.DESCRIPTOR);
                    obtain.writeTypedObject(motionEvent, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.remotecontrol.IRemoteInjection
            public boolean isRemoteControlAllowed(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRemoteInjection.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.remotecontrol.IRemoteInjection
            public boolean isRemoteControlDisabled(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRemoteInjection.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.remotecontrol.IRemoteInjection
            public void updateRemoteScreenDimensionsAndCallerUid(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRemoteInjection.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IRemoteInjection.DESCRIPTOR);
        }

        public static IRemoteInjection asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IRemoteInjection.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IRemoteInjection)) ? new Proxy(iBinder) : (IRemoteInjection) queryLocalInterface;
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "injectKeyEvent";
                case 2:
                    return "injectPointerEvent";
                case 3:
                    return "injectTrackballEvent";
                case 4:
                    return "allowRemoteControl";
                case 5:
                    return "isRemoteControlAllowed";
                case 6:
                    return "updateRemoteScreenDimensionsAndCallerUid";
                case 7:
                    return "isRemoteControlDisabled";
                case 8:
                    return "addRemoteScreenWatcherCallback";
                default:
                    return null;
            }
        }

        public int getMaxTransactionId() {
            return 7;
        }

        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IRemoteInjection.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IRemoteInjection.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    KeyEvent keyEvent = (KeyEvent) parcel.readTypedObject(KeyEvent.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean injectKeyEvent = injectKeyEvent(keyEvent, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(injectKeyEvent);
                    return true;
                case 2:
                    MotionEvent motionEvent = (MotionEvent) parcel.readTypedObject(MotionEvent.CREATOR);
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean injectPointerEvent = injectPointerEvent(motionEvent, readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(injectPointerEvent);
                    return true;
                case 3:
                    MotionEvent motionEvent2 = (MotionEvent) parcel.readTypedObject(MotionEvent.CREATOR);
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean injectTrackballEvent = injectTrackballEvent(motionEvent2, readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(injectTrackballEvent);
                    return true;
                case 4:
                    ContextInfo contextInfo = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean4 = parcel.readBoolean();
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowRemoteControl = allowRemoteControl(contextInfo, readBoolean4, readBoolean5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowRemoteControl);
                    return true;
                case 5:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isRemoteControlAllowed = isRemoteControlAllowed(contextInfo2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isRemoteControlAllowed);
                    return true;
                case 6:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateRemoteScreenDimensionsAndCallerUid(readInt, readInt2, readInt3);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isRemoteControlDisabled = isRemoteControlDisabled(readInt4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isRemoteControlDisabled);
                    return true;
                case 8:
                    IRemoteScreenWatcherCallback asInterface = IRemoteScreenWatcherCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean addRemoteScreenWatcherCallback = addRemoteScreenWatcherCallback(asInterface);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addRemoteScreenWatcherCallback);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Default implements IRemoteInjection {
        @Override // com.samsung.android.knox.remotecontrol.IRemoteInjection
        public boolean addRemoteScreenWatcherCallback(IRemoteScreenWatcherCallback iRemoteScreenWatcherCallback) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.remotecontrol.IRemoteInjection
        public boolean allowRemoteControl(ContextInfo contextInfo, boolean z, boolean z2) throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.remotecontrol.IRemoteInjection
        public boolean injectKeyEvent(KeyEvent keyEvent, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.remotecontrol.IRemoteInjection
        public boolean injectPointerEvent(MotionEvent motionEvent, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.remotecontrol.IRemoteInjection
        public boolean injectTrackballEvent(MotionEvent motionEvent, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.remotecontrol.IRemoteInjection
        public boolean isRemoteControlAllowed(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.remotecontrol.IRemoteInjection
        public boolean isRemoteControlDisabled(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.remotecontrol.IRemoteInjection
        public void updateRemoteScreenDimensionsAndCallerUid(int i, int i2, int i3) throws RemoteException {
        }
    }
}
