package com.android.internal.telephony;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.satellite.SemSatelliteServiceState;
import android.telephony.satellite.SemSatelliteSignalStrength;

/* loaded from: classes4.dex */
public interface ITiantongSatelliteChangeListener extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telephony.ITiantongSatelliteChangeListener";

    public static class Default implements ITiantongSatelliteChangeListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.telephony.ITiantongSatelliteChangeListener
        public void onSemSatelliteServiceStateChanged(int i, int i2, SemSatelliteServiceState semSatelliteServiceState) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ITiantongSatelliteChangeListener
        public void onSemSatelliteSignalStrengthChanged(int i, int i2, SemSatelliteSignalStrength semSatelliteSignalStrength) throws RemoteException {
        }
    }

    void onSemSatelliteServiceStateChanged(int i, int i2, SemSatelliteServiceState semSatelliteServiceState) throws RemoteException;

    void onSemSatelliteSignalStrengthChanged(int i, int i2, SemSatelliteSignalStrength semSatelliteSignalStrength) throws RemoteException;

    public static abstract class Stub extends Binder implements ITiantongSatelliteChangeListener {
        static final int TRANSACTION_onSemSatelliteServiceStateChanged = 1;
        static final int TRANSACTION_onSemSatelliteSignalStrengthChanged = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, ITiantongSatelliteChangeListener.DESCRIPTOR);
        }

        public static ITiantongSatelliteChangeListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ITiantongSatelliteChangeListener.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ITiantongSatelliteChangeListener)) {
                return (ITiantongSatelliteChangeListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onSemSatelliteServiceStateChanged";
            }
            if (i != 2) {
                return null;
            }
            return "onSemSatelliteSignalStrengthChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ITiantongSatelliteChangeListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ITiantongSatelliteChangeListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int i3 = parcel.readInt();
                int i4 = parcel.readInt();
                SemSatelliteServiceState semSatelliteServiceState = (SemSatelliteServiceState) parcel.readTypedObject(SemSatelliteServiceState.CREATOR);
                parcel.enforceNoDataAvail();
                onSemSatelliteServiceStateChanged(i3, i4, semSatelliteServiceState);
            } else if (i == 2) {
                int i5 = parcel.readInt();
                int i6 = parcel.readInt();
                SemSatelliteSignalStrength semSatelliteSignalStrength = (SemSatelliteSignalStrength) parcel.readTypedObject(SemSatelliteSignalStrength.CREATOR);
                parcel.enforceNoDataAvail();
                onSemSatelliteSignalStrengthChanged(i5, i6, semSatelliteSignalStrength);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ITiantongSatelliteChangeListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITiantongSatelliteChangeListener.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.ITiantongSatelliteChangeListener
            public void onSemSatelliteServiceStateChanged(int i, int i2, SemSatelliteServiceState semSatelliteServiceState) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITiantongSatelliteChangeListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(semSatelliteServiceState, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ITiantongSatelliteChangeListener
            public void onSemSatelliteSignalStrengthChanged(int i, int i2, SemSatelliteSignalStrength semSatelliteSignalStrength) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITiantongSatelliteChangeListener.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(semSatelliteSignalStrength, 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
