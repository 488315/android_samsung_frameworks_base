package com.sec.ims.mdmi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IMdmiEventListener extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.mdmi.IMdmiEventListener";

    void onE911StatsUpdated(long j, long j2, long j3, long j4, long j5, double d, double d2, double d3) throws RemoteException;

    public abstract class Stub extends Binder implements IMdmiEventListener {
        static final int TRANSACTION_onE911StatsUpdated = 1;

        class Proxy implements IMdmiEventListener {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IMdmiEventListener.DESCRIPTOR;
            }

            @Override // com.sec.ims.mdmi.IMdmiEventListener
            public void onE911StatsUpdated(long j, long j2, long j3, long j4, long j5, double d, double d2, double d3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMdmiEventListener.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeLong(j2);
                    parcelObtain.writeLong(j3);
                    parcelObtain.writeLong(j4);
                    parcelObtain.writeLong(j5);
                    parcelObtain.writeDouble(d);
                    parcelObtain.writeDouble(d2);
                    parcelObtain.writeDouble(d3);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IMdmiEventListener.DESCRIPTOR);
        }

        public static IMdmiEventListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IMdmiEventListener.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IMdmiEventListener)) ? new Proxy(iBinder) : (IMdmiEventListener) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            Parcel parcel3;
            if (i < 1 || i > 16777215) {
                parcel3 = parcel;
            } else {
                parcel3 = parcel;
                parcel3.enforceInterface(IMdmiEventListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IMdmiEventListener.DESCRIPTOR);
                return true;
            }
            if (i != 1) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            long j = parcel3.readLong();
            long j2 = parcel3.readLong();
            long j3 = parcel3.readLong();
            long j4 = parcel3.readLong();
            long j5 = parcel3.readLong();
            double d = parcel3.readDouble();
            double d2 = parcel3.readDouble();
            double d3 = parcel3.readDouble();
            parcel3.enforceNoDataAvail();
            onE911StatsUpdated(j, j2, j3, j4, j5, d, d2, d3);
            parcel2.writeNoException();
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

    public class Default implements IMdmiEventListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.ims.mdmi.IMdmiEventListener
        public void onE911StatsUpdated(long j, long j2, long j3, long j4, long j5, double d, double d2, double d3) throws RemoteException {
        }
    }
}
