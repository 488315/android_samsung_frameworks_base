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

    public abstract class Stub extends Binder implements IRemoteInjection {
        public static final int TRANSACTION_addRemoteScreenWatcherCallback = 8;
        public static final int TRANSACTION_allowRemoteControl = 4;
        public static final int TRANSACTION_injectKeyEvent = 1;
        public static final int TRANSACTION_injectPointerEvent = 2;
        public static final int TRANSACTION_injectTrackballEvent = 3;
        public static final int TRANSACTION_isRemoteControlAllowed = 5;
        public static final int TRANSACTION_isRemoteControlDisabled = 7;
        public static final int TRANSACTION_updateRemoteScreenDimensionsAndCallerUid = 6;

        class Proxy implements IRemoteInjection {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.remotecontrol.IRemoteInjection
            public boolean addRemoteScreenWatcherCallback(IRemoteScreenWatcherCallback iRemoteScreenWatcherCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteInjection.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iRemoteScreenWatcherCallback);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.remotecontrol.IRemoteInjection
            public boolean allowRemoteControl(ContextInfo contextInfo, boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteInjection.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteInjection.DESCRIPTOR);
                    parcelObtain.writeTypedObject(keyEvent, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.remotecontrol.IRemoteInjection
            public boolean injectPointerEvent(MotionEvent motionEvent, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteInjection.DESCRIPTOR);
                    parcelObtain.writeTypedObject(motionEvent, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.remotecontrol.IRemoteInjection
            public boolean injectTrackballEvent(MotionEvent motionEvent, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteInjection.DESCRIPTOR);
                    parcelObtain.writeTypedObject(motionEvent, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.remotecontrol.IRemoteInjection
            public boolean isRemoteControlAllowed(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteInjection.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.remotecontrol.IRemoteInjection
            public boolean isRemoteControlDisabled(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteInjection.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.remotecontrol.IRemoteInjection
            public void updateRemoteScreenDimensionsAndCallerUid(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemoteInjection.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IRemoteInjection.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IRemoteInjection)) ? new Proxy(iBinder) : (IRemoteInjection) iInterfaceQueryLocalInterface;
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
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zInjectKeyEvent = injectKeyEvent(keyEvent, z);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zInjectKeyEvent);
                    return true;
                case 2:
                    MotionEvent motionEvent = (MotionEvent) parcel.readTypedObject(MotionEvent.CREATOR);
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zInjectPointerEvent = injectPointerEvent(motionEvent, z2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zInjectPointerEvent);
                    return true;
                case 3:
                    MotionEvent motionEvent2 = (MotionEvent) parcel.readTypedObject(MotionEvent.CREATOR);
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zInjectTrackballEvent = injectTrackballEvent(motionEvent2, z3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zInjectTrackballEvent);
                    return true;
                case 4:
                    ContextInfo contextInfo = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z4 = parcel.readBoolean();
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zAllowRemoteControl = allowRemoteControl(contextInfo, z4, z5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAllowRemoteControl);
                    return true;
                case 5:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsRemoteControlAllowed = isRemoteControlAllowed(contextInfo2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsRemoteControlAllowed);
                    return true;
                case 6:
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateRemoteScreenDimensionsAndCallerUid(i3, i4, i5);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsRemoteControlDisabled = isRemoteControlDisabled(i6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsRemoteControlDisabled);
                    return true;
                case 8:
                    IRemoteScreenWatcherCallback iRemoteScreenWatcherCallbackAsInterface = IRemoteScreenWatcherCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zAddRemoteScreenWatcherCallback = addRemoteScreenWatcherCallback(iRemoteScreenWatcherCallbackAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddRemoteScreenWatcherCallback);
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
