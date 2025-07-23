package com.samsung.android.scs.ai.sdkcommon.asr;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface IRecognitionListener extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.scs.ai.sdkcommon.asr.IRecognitionListener";

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class _Parcel {
        /* renamed from: -$$Nest$smreadTypedObject, reason: not valid java name */
        public static /* bridge */ /* synthetic */ Object m3292$$Nest$smreadTypedObject(Parcel parcel) {
            return readTypedObject(parcel, Bundle.CREATOR);
        }

        private static <T> T readTypedObject(Parcel parcel, Parcelable.Creator<T> creator) {
            if (parcel.readInt() != 0) {
                return creator.createFromParcel(parcel);
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static <T extends Parcelable> void writeTypedObject(Parcel parcel, T t, int i) {
            if (t == null) {
                parcel.writeInt(0);
            } else {
                parcel.writeInt(1);
                t.writeToParcel(parcel, i);
            }
        }
    }

    void onError(Bundle bundle) throws RemoteException;

    void onPartialResults(Bundle bundle) throws RemoteException;

    void onResults(Bundle bundle) throws RemoteException;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Stub extends Binder implements IRecognitionListener {
        static final int TRANSACTION_onError = 1;
        static final int TRANSACTION_onPartialResults = 3;
        static final int TRANSACTION_onResults = 2;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        class Proxy implements IRecognitionListener {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRecognitionListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.scs.ai.sdkcommon.asr.IRecognitionListener
            public void onError(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRecognitionListener.DESCRIPTOR);
                    _Parcel.writeTypedObject(obtain, bundle, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.scs.ai.sdkcommon.asr.IRecognitionListener
            public void onPartialResults(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRecognitionListener.DESCRIPTOR);
                    _Parcel.writeTypedObject(obtain, bundle, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.scs.ai.sdkcommon.asr.IRecognitionListener
            public void onResults(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IRecognitionListener.DESCRIPTOR);
                    _Parcel.writeTypedObject(obtain, bundle, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IRecognitionListener.DESCRIPTOR);
        }

        public static IRecognitionListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IRecognitionListener.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IRecognitionListener)) ? new Proxy(iBinder) : (IRecognitionListener) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IRecognitionListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IRecognitionListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                Parcelable.Creator creator = Bundle.CREATOR;
                onError((Bundle) _Parcel.m3292$$Nest$smreadTypedObject(parcel));
                parcel2.writeNoException();
                return true;
            }
            if (i == 2) {
                Parcelable.Creator creator2 = Bundle.CREATOR;
                onResults((Bundle) _Parcel.m3292$$Nest$smreadTypedObject(parcel));
                parcel2.writeNoException();
                return true;
            }
            if (i != 3) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            Parcelable.Creator creator3 = Bundle.CREATOR;
            onPartialResults((Bundle) _Parcel.m3292$$Nest$smreadTypedObject(parcel));
            parcel2.writeNoException();
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Default implements IRecognitionListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.scs.ai.sdkcommon.asr.IRecognitionListener
        public void onError(Bundle bundle) throws RemoteException {
        }

        @Override // com.samsung.android.scs.ai.sdkcommon.asr.IRecognitionListener
        public void onPartialResults(Bundle bundle) throws RemoteException {
        }

        @Override // com.samsung.android.scs.ai.sdkcommon.asr.IRecognitionListener
        public void onResults(Bundle bundle) throws RemoteException {
        }
    }
}
