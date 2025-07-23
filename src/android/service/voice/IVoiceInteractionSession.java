package android.service.voice;

import android.app.assist.AssistContent;
import android.app.assist.AssistStructure;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.ThreadedRenderer;
import com.android.internal.app.IVoiceInteractionSessionShowCallback;

/* loaded from: classes3.dex */
public interface IVoiceInteractionSession extends IInterface {

    public static class Default implements IVoiceInteractionSession {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.voice.IVoiceInteractionSession
        public void closeSystemDialogs() throws RemoteException {
        }

        @Override // android.service.voice.IVoiceInteractionSession
        public void destroy() throws RemoteException {
        }

        @Override // android.service.voice.IVoiceInteractionSession
        public void handleAssist(int i, IBinder iBinder, Bundle bundle, AssistStructure assistStructure, AssistContent assistContent, int i2, int i3) throws RemoteException {
        }

        @Override // android.service.voice.IVoiceInteractionSession
        public void handleScreenshot(Bitmap bitmap) throws RemoteException {
        }

        @Override // android.service.voice.IVoiceInteractionSession
        public void hide() throws RemoteException {
        }

        @Override // android.service.voice.IVoiceInteractionSession
        public void notifyVisibleActivityInfoChanged(VisibleActivityInfo visibleActivityInfo, int i) throws RemoteException {
        }

        @Override // android.service.voice.IVoiceInteractionSession
        public void onLockscreenShown() throws RemoteException {
        }

        @Override // android.service.voice.IVoiceInteractionSession
        public void show(Bundle bundle, int i, IVoiceInteractionSessionShowCallback iVoiceInteractionSessionShowCallback) throws RemoteException {
        }

        @Override // android.service.voice.IVoiceInteractionSession
        public void taskFinished(Intent intent, int i) throws RemoteException {
        }

        @Override // android.service.voice.IVoiceInteractionSession
        public void taskStarted(Intent intent, int i) throws RemoteException {
        }
    }

    void closeSystemDialogs() throws RemoteException;

    void destroy() throws RemoteException;

    void handleAssist(int i, IBinder iBinder, Bundle bundle, AssistStructure assistStructure, AssistContent assistContent, int i2, int i3) throws RemoteException;

    void handleScreenshot(Bitmap bitmap) throws RemoteException;

    void hide() throws RemoteException;

    void notifyVisibleActivityInfoChanged(VisibleActivityInfo visibleActivityInfo, int i) throws RemoteException;

    void onLockscreenShown() throws RemoteException;

    void show(Bundle bundle, int i, IVoiceInteractionSessionShowCallback iVoiceInteractionSessionShowCallback) throws RemoteException;

    void taskFinished(Intent intent, int i) throws RemoteException;

    void taskStarted(Intent intent, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IVoiceInteractionSession {
        public static final String DESCRIPTOR = "android.service.voice.IVoiceInteractionSession";
        static final int TRANSACTION_closeSystemDialogs = 7;
        static final int TRANSACTION_destroy = 9;
        static final int TRANSACTION_handleAssist = 3;
        static final int TRANSACTION_handleScreenshot = 4;
        static final int TRANSACTION_hide = 2;
        static final int TRANSACTION_notifyVisibleActivityInfoChanged = 10;
        static final int TRANSACTION_onLockscreenShown = 8;
        static final int TRANSACTION_show = 1;
        static final int TRANSACTION_taskFinished = 6;
        static final int TRANSACTION_taskStarted = 5;

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

        public static IVoiceInteractionSession asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IVoiceInteractionSession)) {
                return (IVoiceInteractionSession) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return ThreadedRenderer.OVERDRAW_PROPERTY_SHOW;
                case 2:
                    return "hide";
                case 3:
                    return "handleAssist";
                case 4:
                    return "handleScreenshot";
                case 5:
                    return "taskStarted";
                case 6:
                    return "taskFinished";
                case 7:
                    return "closeSystemDialogs";
                case 8:
                    return "onLockscreenShown";
                case 9:
                    return "destroy";
                case 10:
                    return "notifyVisibleActivityInfoChanged";
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
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int readInt = parcel.readInt();
                    IVoiceInteractionSessionShowCallback asInterface = IVoiceInteractionSessionShowCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    show(bundle, readInt, asInterface);
                    return true;
                case 2:
                    hide();
                    return true;
                case 3:
                    int readInt2 = parcel.readInt();
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    AssistStructure assistStructure = (AssistStructure) parcel.readTypedObject(AssistStructure.CREATOR);
                    AssistContent assistContent = (AssistContent) parcel.readTypedObject(AssistContent.CREATOR);
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    handleAssist(readInt2, readStrongBinder, bundle2, assistStructure, assistContent, readInt3, readInt4);
                    return true;
                case 4:
                    Bitmap bitmap = (Bitmap) parcel.readTypedObject(Bitmap.CREATOR);
                    parcel.enforceNoDataAvail();
                    handleScreenshot(bitmap);
                    return true;
                case 5:
                    Intent intent = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    taskStarted(intent, readInt5);
                    return true;
                case 6:
                    Intent intent2 = (Intent) parcel.readTypedObject(Intent.CREATOR);
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    taskFinished(intent2, readInt6);
                    return true;
                case 7:
                    closeSystemDialogs();
                    return true;
                case 8:
                    onLockscreenShown();
                    return true;
                case 9:
                    destroy();
                    return true;
                case 10:
                    VisibleActivityInfo visibleActivityInfo = (VisibleActivityInfo) parcel.readTypedObject(VisibleActivityInfo.CREATOR);
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyVisibleActivityInfoChanged(visibleActivityInfo, readInt7);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IVoiceInteractionSession {
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

            @Override // android.service.voice.IVoiceInteractionSession
            public void show(Bundle bundle, int i, IVoiceInteractionSessionShowCallback iVoiceInteractionSessionShowCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iVoiceInteractionSessionShowCallback);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.IVoiceInteractionSession
            public void hide() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.IVoiceInteractionSession
            public void handleAssist(int i, IBinder iBinder, Bundle bundle, AssistStructure assistStructure, AssistContent assistContent, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeTypedObject(assistStructure, 0);
                    obtain.writeTypedObject(assistContent, 0);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.IVoiceInteractionSession
            public void handleScreenshot(Bitmap bitmap) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(bitmap, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.IVoiceInteractionSession
            public void taskStarted(Intent intent, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.IVoiceInteractionSession
            public void taskFinished(Intent intent, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(intent, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.IVoiceInteractionSession
            public void closeSystemDialogs() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.IVoiceInteractionSession
            public void onLockscreenShown() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.IVoiceInteractionSession
            public void destroy() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.voice.IVoiceInteractionSession
            public void notifyVisibleActivityInfoChanged(VisibleActivityInfo visibleActivityInfo, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(visibleActivityInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
