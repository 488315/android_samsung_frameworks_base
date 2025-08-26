package com.android.internal.telephony;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.satellite.SemSatelliteServiceState;
import android.telephony.satellite.SemSatelliteSignalStrength;
import com.android.internal.telephony.ITiantongSatelliteChangeListener;

/* loaded from: classes4.dex */
public interface ISemTelephonyRegistry extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telephony.ISemTelephonyRegistry";

    public static class Default implements ISemTelephonyRegistry {
        @Override // com.android.internal.telephony.ISemTelephonyRegistry
        public void addTiantongSatelliteChangeListener(ITiantongSatelliteChangeListener iTiantongSatelliteChangeListener, String str, String str2) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.telephony.ISemTelephonyRegistry
        public void notifySemSatelliteServiceStateChanged(int i, int i2, SemSatelliteServiceState semSatelliteServiceState) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISemTelephonyRegistry
        public void notifySemSatelliteSignalStrengthChanged(int i, int i2, SemSatelliteSignalStrength semSatelliteSignalStrength) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ISemTelephonyRegistry
        public void removeTiantongSatelliteChangeListener(ITiantongSatelliteChangeListener iTiantongSatelliteChangeListener, String str) throws RemoteException {
        }
    }

    void addTiantongSatelliteChangeListener(ITiantongSatelliteChangeListener iTiantongSatelliteChangeListener, String str, String str2) throws RemoteException;

    void notifySemSatelliteServiceStateChanged(int i, int i2, SemSatelliteServiceState semSatelliteServiceState) throws RemoteException;

    void notifySemSatelliteSignalStrengthChanged(int i, int i2, SemSatelliteSignalStrength semSatelliteSignalStrength) throws RemoteException;

    void removeTiantongSatelliteChangeListener(ITiantongSatelliteChangeListener iTiantongSatelliteChangeListener, String str) throws RemoteException;

    public static abstract class Stub extends Binder implements ISemTelephonyRegistry {
        static final int TRANSACTION_addTiantongSatelliteChangeListener = 1;
        static final int TRANSACTION_notifySemSatelliteServiceStateChanged = 3;
        static final int TRANSACTION_notifySemSatelliteSignalStrengthChanged = 4;
        static final int TRANSACTION_removeTiantongSatelliteChangeListener = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, ISemTelephonyRegistry.DESCRIPTOR);
        }

        public static ISemTelephonyRegistry asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISemTelephonyRegistry.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISemTelephonyRegistry)) {
                return (ISemTelephonyRegistry) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "addTiantongSatelliteChangeListener";
            }
            if (i == 2) {
                return "removeTiantongSatelliteChangeListener";
            }
            if (i == 3) {
                return "notifySemSatelliteServiceStateChanged";
            }
            if (i != 4) {
                return null;
            }
            return "notifySemSatelliteSignalStrengthChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISemTelephonyRegistry.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISemTelephonyRegistry.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ITiantongSatelliteChangeListener iTiantongSatelliteChangeListenerAsInterface = ITiantongSatelliteChangeListener.Stub.asInterface(parcel.readStrongBinder());
                String string = parcel.readString();
                String string2 = parcel.readString();
                parcel.enforceNoDataAvail();
                addTiantongSatelliteChangeListener(iTiantongSatelliteChangeListenerAsInterface, string, string2);
                parcel2.writeNoException();
            } else if (i == 2) {
                ITiantongSatelliteChangeListener iTiantongSatelliteChangeListenerAsInterface2 = ITiantongSatelliteChangeListener.Stub.asInterface(parcel.readStrongBinder());
                String string3 = parcel.readString();
                parcel.enforceNoDataAvail();
                removeTiantongSatelliteChangeListener(iTiantongSatelliteChangeListenerAsInterface2, string3);
                parcel2.writeNoException();
            } else if (i == 3) {
                int i3 = parcel.readInt();
                int i4 = parcel.readInt();
                SemSatelliteServiceState semSatelliteServiceState = (SemSatelliteServiceState) parcel.readTypedObject(SemSatelliteServiceState.CREATOR);
                parcel.enforceNoDataAvail();
                notifySemSatelliteServiceStateChanged(i3, i4, semSatelliteServiceState);
                parcel2.writeNoException();
            } else if (i == 4) {
                int i5 = parcel.readInt();
                int i6 = parcel.readInt();
                SemSatelliteSignalStrength semSatelliteSignalStrength = (SemSatelliteSignalStrength) parcel.readTypedObject(SemSatelliteSignalStrength.CREATOR);
                parcel.enforceNoDataAvail();
                notifySemSatelliteSignalStrengthChanged(i5, i6, semSatelliteSignalStrength);
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISemTelephonyRegistry {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemTelephonyRegistry.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.ISemTelephonyRegistry
            public void addTiantongSatelliteChangeListener(ITiantongSatelliteChangeListener iTiantongSatelliteChangeListener, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephonyRegistry.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iTiantongSatelliteChangeListener);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephonyRegistry
            public void removeTiantongSatelliteChangeListener(ITiantongSatelliteChangeListener iTiantongSatelliteChangeListener, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephonyRegistry.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iTiantongSatelliteChangeListener);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephonyRegistry
            public void notifySemSatelliteServiceStateChanged(int i, int i2, SemSatelliteServiceState semSatelliteServiceState) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephonyRegistry.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(semSatelliteServiceState, 0);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ISemTelephonyRegistry
            public void notifySemSatelliteSignalStrengthChanged(int i, int i2, SemSatelliteSignalStrength semSatelliteSignalStrength) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemTelephonyRegistry.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(semSatelliteSignalStrength, 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
