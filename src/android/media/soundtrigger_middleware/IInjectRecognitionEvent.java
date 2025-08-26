package android.media.soundtrigger_middleware;

import android.media.soundtrigger.PhraseRecognitionExtra;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IInjectRecognitionEvent extends IInterface {
    public static final String DESCRIPTOR = "android.media.soundtrigger_middleware.IInjectRecognitionEvent";

    public static class Default implements IInjectRecognitionEvent {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.soundtrigger_middleware.IInjectRecognitionEvent
        public void triggerAbortRecognition() throws RemoteException {
        }

        @Override // android.media.soundtrigger_middleware.IInjectRecognitionEvent
        public void triggerRecognitionEvent(byte[] bArr, PhraseRecognitionExtra[] phraseRecognitionExtraArr) throws RemoteException {
        }
    }

    void triggerAbortRecognition() throws RemoteException;

    void triggerRecognitionEvent(byte[] bArr, PhraseRecognitionExtra[] phraseRecognitionExtraArr) throws RemoteException;

    public static abstract class Stub extends Binder implements IInjectRecognitionEvent {
        static final int TRANSACTION_triggerAbortRecognition = 2;
        static final int TRANSACTION_triggerRecognitionEvent = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IInjectRecognitionEvent.DESCRIPTOR);
        }

        public static IInjectRecognitionEvent asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IInjectRecognitionEvent.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IInjectRecognitionEvent)) {
                return (IInjectRecognitionEvent) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IInjectRecognitionEvent.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IInjectRecognitionEvent.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                byte[] bArrCreateByteArray = parcel.createByteArray();
                PhraseRecognitionExtra[] phraseRecognitionExtraArr = (PhraseRecognitionExtra[]) parcel.createTypedArray(PhraseRecognitionExtra.CREATOR);
                parcel.enforceNoDataAvail();
                triggerRecognitionEvent(bArrCreateByteArray, phraseRecognitionExtraArr);
            } else if (i == 2) {
                triggerAbortRecognition();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IInjectRecognitionEvent {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IInjectRecognitionEvent.DESCRIPTOR;
            }

            @Override // android.media.soundtrigger_middleware.IInjectRecognitionEvent
            public void triggerRecognitionEvent(byte[] bArr, PhraseRecognitionExtra[] phraseRecognitionExtraArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInjectRecognitionEvent.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeTypedArray(phraseRecognitionExtraArr, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.media.soundtrigger_middleware.IInjectRecognitionEvent
            public void triggerAbortRecognition() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IInjectRecognitionEvent.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
