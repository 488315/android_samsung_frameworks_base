package com.sec.ims.mdmi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface IMdmiEventListener extends IInterface {
    public static final String DESCRIPTOR = "com.sec.ims.mdmi.IMdmiEventListener";

    void onE911StatsUpdated(long j, long j2, long j3, long j4, long j5, double d, double d2, double d3) throws RemoteException;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Stub extends Binder implements IMdmiEventListener {
        static final int TRANSACTION_onE911StatsUpdated = 1;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMdmiEventListener.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    obtain.writeLong(j3);
                    obtain.writeLong(j4);
                    obtain.writeLong(j5);
                    obtain.writeDouble(d);
                    obtain.writeDouble(d2);
                    obtain.writeDouble(d3);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IMdmiEventListener.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IMdmiEventListener)) ? new Proxy(iBinder) : (IMdmiEventListener) queryLocalInterface;
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
            long readLong = parcel3.readLong();
            long readLong2 = parcel3.readLong();
            long readLong3 = parcel3.readLong();
            long readLong4 = parcel3.readLong();
            long readLong5 = parcel3.readLong();
            double readDouble = parcel3.readDouble();
            double readDouble2 = parcel3.readDouble();
            double readDouble3 = parcel3.readDouble();
            parcel3.enforceNoDataAvail();
            onE911StatsUpdated(readLong, readLong2, readLong3, readLong4, readLong5, readDouble, readDouble2, readDouble3);
            parcel2.writeNoException();
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
