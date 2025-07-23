package com.samsung.android.scs.ai.sdkcommon.asr;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;
import com.samsung.android.scs.ai.sdkcommon.asr.IRecognitionListener;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface ISpeechRecognizer extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.scs.ai.sdkcommon.asr.ISpeechRecognizer";

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class _Parcel {
        /* JADX INFO: Access modifiers changed from: private */
        public static <T> T readTypedObject(Parcel parcel, Parcelable.Creator<T> creator) {
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

    void cancel() throws RemoteException;

    boolean prepare(Bundle bundle) throws RemoteException;

    boolean release() throws RemoteException;

    boolean write(ParcelFileDescriptor parcelFileDescriptor, IRecognitionListener iRecognitionListener) throws RemoteException;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Default implements ISpeechRecognizer {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.scs.ai.sdkcommon.asr.ISpeechRecognizer
        public boolean prepare(Bundle bundle) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.scs.ai.sdkcommon.asr.ISpeechRecognizer
        public boolean release() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.scs.ai.sdkcommon.asr.ISpeechRecognizer
        public boolean write(ParcelFileDescriptor parcelFileDescriptor, IRecognitionListener iRecognitionListener) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.scs.ai.sdkcommon.asr.ISpeechRecognizer
        public void cancel() throws RemoteException {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Stub extends Binder implements ISpeechRecognizer {
        static final int TRANSACTION_cancel = 3;
        static final int TRANSACTION_prepare = 1;
        static final int TRANSACTION_release = 4;
        static final int TRANSACTION_write = 2;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        class Proxy implements ISpeechRecognizer {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.scs.ai.sdkcommon.asr.ISpeechRecognizer
            public void cancel() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpeechRecognizer.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return ISpeechRecognizer.DESCRIPTOR;
            }

            @Override // com.samsung.android.scs.ai.sdkcommon.asr.ISpeechRecognizer
            public boolean prepare(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpeechRecognizer.DESCRIPTOR);
                    _Parcel.writeTypedObject(obtain, bundle, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.scs.ai.sdkcommon.asr.ISpeechRecognizer
            public boolean release() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpeechRecognizer.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.scs.ai.sdkcommon.asr.ISpeechRecognizer
            public boolean write(ParcelFileDescriptor parcelFileDescriptor, IRecognitionListener iRecognitionListener) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpeechRecognizer.DESCRIPTOR);
                    _Parcel.writeTypedObject(obtain, parcelFileDescriptor, 0);
                    obtain.writeStrongInterface(iRecognitionListener);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt() != 0;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, ISpeechRecognizer.DESCRIPTOR);
        }

        public static ISpeechRecognizer asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISpeechRecognizer.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof ISpeechRecognizer)) ? new Proxy(iBinder) : (ISpeechRecognizer) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISpeechRecognizer.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISpeechRecognizer.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                boolean prepare = prepare((Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR));
                parcel2.writeNoException();
                parcel2.writeInt(prepare ? 1 : 0);
            } else if (i == 2) {
                boolean write = write((ParcelFileDescriptor) _Parcel.readTypedObject(parcel, ParcelFileDescriptor.CREATOR), IRecognitionListener.Stub.asInterface(parcel.readStrongBinder()));
                parcel2.writeNoException();
                parcel2.writeInt(write ? 1 : 0);
            } else if (i == 3) {
                cancel();
                parcel2.writeNoException();
            } else {
                if (i != 4) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                boolean release = release();
                parcel2.writeNoException();
                parcel2.writeInt(release ? 1 : 0);
            }
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }
}
