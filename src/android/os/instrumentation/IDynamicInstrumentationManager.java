package android.os.instrumentation;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.instrumentation.IOffsetCallback;

/* loaded from: classes3.dex */
public interface IDynamicInstrumentationManager extends IInterface {
    public static final String DESCRIPTOR = "android.os.instrumentation.IDynamicInstrumentationManager";

    public static class Default implements IDynamicInstrumentationManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.instrumentation.IDynamicInstrumentationManager
        public void getExecutableMethodFileOffsets(TargetProcess targetProcess, MethodDescriptor methodDescriptor, IOffsetCallback iOffsetCallback) throws RemoteException {
        }
    }

    void getExecutableMethodFileOffsets(TargetProcess targetProcess, MethodDescriptor methodDescriptor, IOffsetCallback iOffsetCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IDynamicInstrumentationManager {
        static final int TRANSACTION_getExecutableMethodFileOffsets = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IDynamicInstrumentationManager.DESCRIPTOR);
        }

        public static IDynamicInstrumentationManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IDynamicInstrumentationManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IDynamicInstrumentationManager)) {
                return (IDynamicInstrumentationManager) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IDynamicInstrumentationManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDynamicInstrumentationManager.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                TargetProcess targetProcess = (TargetProcess) parcel.readTypedObject(TargetProcess.CREATOR);
                MethodDescriptor methodDescriptor = (MethodDescriptor) parcel.readTypedObject(MethodDescriptor.CREATOR);
                IOffsetCallback iOffsetCallbackAsInterface = IOffsetCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                getExecutableMethodFileOffsets(targetProcess, methodDescriptor, iOffsetCallbackAsInterface);
                parcel2.writeNoException();
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IDynamicInstrumentationManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDynamicInstrumentationManager.DESCRIPTOR;
            }

            @Override // android.os.instrumentation.IDynamicInstrumentationManager
            public void getExecutableMethodFileOffsets(TargetProcess targetProcess, MethodDescriptor methodDescriptor, IOffsetCallback iOffsetCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDynamicInstrumentationManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(targetProcess, 0);
                    parcelObtain.writeTypedObject(methodDescriptor, 0);
                    parcelObtain.writeStrongInterface(iOffsetCallback);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
