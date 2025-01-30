package android.media.soundtrigger_middleware;

import android.media.soundtrigger.PhraseRecognitionExtra;
import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes2.dex */
public interface IInjectRecognitionEvent extends IInterface {
    public static final String DESCRIPTOR = "android$media$soundtrigger_middleware$IInjectRecognitionEvent".replace('$', '.');

    void triggerAbortRecognition() throws RemoteException;

    void triggerRecognitionEvent(byte[] bArr, PhraseRecognitionExtra[] phraseRecognitionExtraArr) throws RemoteException;

    public static class Default implements IInjectRecognitionEvent {
        @Override // android.media.soundtrigger_middleware.IInjectRecognitionEvent
        public void triggerRecognitionEvent(byte[] data, PhraseRecognitionExtra[] phraseExtras) throws RemoteException {
        }

        @Override // android.media.soundtrigger_middleware.IInjectRecognitionEvent
        public void triggerAbortRecognition() throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IInjectRecognitionEvent {
        static final int TRANSACTION_triggerAbortRecognition = 2;
        static final int TRANSACTION_triggerRecognitionEvent = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IInjectRecognitionEvent asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IInjectRecognitionEvent)) {
                return (IInjectRecognitionEvent) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.p009os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            String descriptor = DESCRIPTOR;
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(descriptor);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(descriptor);
                    return true;
                default:
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
        }

        private static class Proxy implements IInjectRecognitionEvent {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override // android.media.soundtrigger_middleware.IInjectRecognitionEvent
            public void triggerRecognitionEvent(byte[] data, PhraseRecognitionExtra[] phraseExtras) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
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
                    _data.writeInterfaceToken(DESCRIPTOR);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }
    }
}
