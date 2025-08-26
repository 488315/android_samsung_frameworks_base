package com.sec.ims.mdmi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.sec.ims.mdmi.IMdmiEventListener;

/* loaded from: classes4.dex */
public interface IMdmiService extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.mdmi.IMdmiService";

    void registerMdmiEventListener(IMdmiEventListener iMdmiEventListener) throws RemoteException;

    public abstract class Stub extends Binder implements IMdmiService {
        static final int TRANSACTION_registerMdmiEventListener = 1;

        class Proxy implements IMdmiService {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IMdmiService.DESCRIPTOR;
            }

            @Override // com.sec.ims.mdmi.IMdmiService
            public void registerMdmiEventListener(IMdmiEventListener iMdmiEventListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMdmiService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iMdmiEventListener);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IMdmiService.DESCRIPTOR);
        }

        public static IMdmiService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IMdmiService.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IMdmiService)) ? new Proxy(iBinder) : (IMdmiService) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IMdmiService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IMdmiService.DESCRIPTOR);
                return true;
            }
            if (i != 1) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            IMdmiEventListener iMdmiEventListenerAsInterface = IMdmiEventListener.Stub.asInterface(parcel.readStrongBinder());
            parcel.enforceNoDataAvail();
            registerMdmiEventListener(iMdmiEventListenerAsInterface);
            parcel2.writeNoException();
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

    public class Default implements IMdmiService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.mdmi.IMdmiService
        public void registerMdmiEventListener(IMdmiEventListener iMdmiEventListener) throws RemoteException {
        }
    }
}
