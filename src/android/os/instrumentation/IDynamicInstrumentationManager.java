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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IDynamicInstrumentationManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IDynamicInstrumentationManager)) {
                return (IDynamicInstrumentationManager) queryLocalInterface;
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
                IOffsetCallback asInterface = IOffsetCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                getExecutableMethodFileOffsets(targetProcess, methodDescriptor, asInterface);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDynamicInstrumentationManager.DESCRIPTOR);
                    obtain.writeTypedObject(targetProcess, 0);
                    obtain.writeTypedObject(methodDescriptor, 0);
                    obtain.writeStrongInterface(iOffsetCallback);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
