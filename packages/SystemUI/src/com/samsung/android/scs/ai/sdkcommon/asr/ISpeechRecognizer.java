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

/* loaded from: classes4.dex */
public interface ISpeechRecognizer extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.scs.ai.sdkcommon.asr.ISpeechRecognizer";

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

    public abstract class Stub extends Binder implements ISpeechRecognizer {
        static final int TRANSACTION_cancel = 3;
        static final int TRANSACTION_prepare = 1;
        static final int TRANSACTION_release = 4;
        static final int TRANSACTION_write = 2;

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
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpeechRecognizer.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return ISpeechRecognizer.DESCRIPTOR;
            }

            @Override // com.samsung.android.scs.ai.sdkcommon.asr.ISpeechRecognizer
            public boolean prepare(Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpeechRecognizer.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, bundle, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.scs.ai.sdkcommon.asr.ISpeechRecognizer
            public boolean release() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpeechRecognizer.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.scs.ai.sdkcommon.asr.ISpeechRecognizer
            public boolean write(ParcelFileDescriptor parcelFileDescriptor, IRecognitionListener iRecognitionListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISpeechRecognizer.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, parcelFileDescriptor, 0);
                    parcelObtain.writeStrongInterface(iRecognitionListener);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISpeechRecognizer.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof ISpeechRecognizer)) ? new Proxy(iBinder) : (ISpeechRecognizer) iInterfaceQueryLocalInterface;
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
                boolean zPrepare = prepare((Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR));
                parcel2.writeNoException();
                parcel2.writeInt(zPrepare ? 1 : 0);
            } else if (i == 2) {
                boolean zWrite = write((ParcelFileDescriptor) _Parcel.readTypedObject(parcel, ParcelFileDescriptor.CREATOR), IRecognitionListener.Stub.asInterface(parcel.readStrongBinder()));
                parcel2.writeNoException();
                parcel2.writeInt(zWrite ? 1 : 0);
            } else if (i == 3) {
                cancel();
                parcel2.writeNoException();
            } else {
                if (i != 4) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                boolean zRelease = release();
                parcel2.writeNoException();
                parcel2.writeInt(zRelease ? 1 : 0);
            }
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }
}
