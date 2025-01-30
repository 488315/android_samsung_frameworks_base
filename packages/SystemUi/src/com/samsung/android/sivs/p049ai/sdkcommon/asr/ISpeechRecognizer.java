package com.samsung.android.sivs.p049ai.sdkcommon.asr;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import com.samsung.android.sivs.p049ai.sdkcommon.asr.IRecognitionListener;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface ISpeechRecognizer extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.sivs.ai.sdkcommon.asr.ISpeechRecognizer";

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class _Parcel {
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

    void cancel();

    boolean prepare(Bundle bundle);

    boolean release();

    boolean write(ParcelFileDescriptor parcelFileDescriptor, IRecognitionListener iRecognitionListener);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements ISpeechRecognizer {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.sivs.p049ai.sdkcommon.asr.ISpeechRecognizer
        public boolean prepare(Bundle bundle) {
            return false;
        }

        @Override // com.samsung.android.sivs.p049ai.sdkcommon.asr.ISpeechRecognizer
        public boolean release() {
            return false;
        }

        @Override // com.samsung.android.sivs.p049ai.sdkcommon.asr.ISpeechRecognizer
        public boolean write(ParcelFileDescriptor parcelFileDescriptor, IRecognitionListener iRecognitionListener) {
            return false;
        }

        @Override // com.samsung.android.sivs.p049ai.sdkcommon.asr.ISpeechRecognizer
        public void cancel() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements ISpeechRecognizer {
        static final int TRANSACTION_cancel = 3;
        static final int TRANSACTION_prepare = 1;
        static final int TRANSACTION_release = 4;
        static final int TRANSACTION_write = 2;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements ISpeechRecognizer {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.sivs.p049ai.sdkcommon.asr.ISpeechRecognizer
            public void cancel() {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISpeechRecognizer.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return ISpeechRecognizer.DESCRIPTOR;
            }

            @Override // com.samsung.android.sivs.p049ai.sdkcommon.asr.ISpeechRecognizer
            public boolean prepare(Bundle bundle) {
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

            @Override // com.samsung.android.sivs.p049ai.sdkcommon.asr.ISpeechRecognizer
            public boolean release() {
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

            @Override // com.samsung.android.sivs.p049ai.sdkcommon.asr.ISpeechRecognizer
            public boolean write(ParcelFileDescriptor parcelFileDescriptor, IRecognitionListener iRecognitionListener) {
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
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
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
