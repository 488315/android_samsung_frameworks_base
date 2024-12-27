package android.media.soundtrigger_middleware;

import android.media.soundtrigger.PhraseRecognitionExtra;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IInjectRecognitionEvent extends IInterface {
    public static final String DESCRIPTOR = "android.media.soundtrigger_middleware.IInjectRecognitionEvent";

    void triggerAbortRecognition() throws RemoteException;

    void triggerRecognitionEvent(byte[] bArr, PhraseRecognitionExtra[] phraseRecognitionExtraArr) throws RemoteException;

    public static class Default implements IInjectRecognitionEvent {
        @Override // android.media.soundtrigger_middleware.IInjectRecognitionEvent
        public void triggerRecognitionEvent(byte[] data, PhraseRecognitionExtra[] phraseExtras) throws RemoteException {
        }

        @Override // android.media.soundtrigger_middleware.IInjectRecognitionEvent
        public void triggerAbortRecognition() throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IInjectRecognitionEvent {
        static final int TRANSACTION_triggerAbortRecognition = 2;
        static final int TRANSACTION_triggerRecognitionEvent = 1;

        public Stub() {
            attachInterface(this, IInjectRecognitionEvent.DESCRIPTOR);
        }

        public static IInjectRecognitionEvent asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IInjectRecognitionEvent.DESCRIPTOR);
            if (iin != null && (iin instanceof IInjectRecognitionEvent)) {
                return (IInjectRecognitionEvent) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IInjectRecognitionEvent.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IInjectRecognitionEvent.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    byte[] _arg0 = data.createByteArray();
                    PhraseRecognitionExtra[] _arg1 = (PhraseRecognitionExtra[]) data.createTypedArray(PhraseRecognitionExtra.CREATOR);
                    data.enforceNoDataAvail();
                    triggerRecognitionEvent(_arg0, _arg1);
                    return true;
                case 2:
                    triggerAbortRecognition();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IInjectRecognitionEvent {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IInjectRecognitionEvent.DESCRIPTOR;
            }

            @Override // android.media.soundtrigger_middleware.IInjectRecognitionEvent
            public void triggerRecognitionEvent(byte[] data, PhraseRecognitionExtra[] phraseExtras) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IInjectRecognitionEvent.DESCRIPTOR);
                    _data.writeByteArray(data);
                    _data.writeTypedArray(phraseExtras, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.IInjectRecognitionEvent
            public void triggerAbortRecognition() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IInjectRecognitionEvent.DESCRIPTOR);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }
    }
}
