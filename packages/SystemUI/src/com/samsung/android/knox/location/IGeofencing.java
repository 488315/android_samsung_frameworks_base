package com.samsung.android.knox.location;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.knox.ContextInfo;
import java.util.List;

/* loaded from: classes4.dex */
public interface IGeofencing extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.location.IGeofencing";

    public class Default implements IGeofencing {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.location.IGeofencing
        public int createGeofence(ContextInfo contextInfo, Geofence geofence) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.location.IGeofencing
        public boolean destroyGeofence(ContextInfo contextInfo, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.location.IGeofencing
        public List<Geofence> getGeofences(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.location.IGeofencing
        public float getMinDistanceParameter(ContextInfo contextInfo) throws RemoteException {
            return 0.0f;
        }

        @Override // com.samsung.android.knox.location.IGeofencing
        public long getMinTimeParameter(ContextInfo contextInfo) throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.knox.location.IGeofencing
        public List isDeviceInsideGeofence(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.location.IGeofencing
        public boolean isGeofencingEnabled(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.location.IGeofencing
        public boolean setMinDistanceParameter(ContextInfo contextInfo, float f) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.location.IGeofencing
        public boolean setMinTimeParameter(ContextInfo contextInfo, long j) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.location.IGeofencing
        public boolean startGeofencing(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.location.IGeofencing
        public boolean stopGeofencing(ContextInfo contextInfo) throws RemoteException {
            return false;
        }
    }

    int createGeofence(ContextInfo contextInfo, Geofence geofence) throws RemoteException;

    boolean destroyGeofence(ContextInfo contextInfo, int i) throws RemoteException;

    List<Geofence> getGeofences(ContextInfo contextInfo) throws RemoteException;

    float getMinDistanceParameter(ContextInfo contextInfo) throws RemoteException;

    long getMinTimeParameter(ContextInfo contextInfo) throws RemoteException;

    List isDeviceInsideGeofence(ContextInfo contextInfo) throws RemoteException;

    boolean isGeofencingEnabled(ContextInfo contextInfo) throws RemoteException;

    boolean setMinDistanceParameter(ContextInfo contextInfo, float f) throws RemoteException;

    boolean setMinTimeParameter(ContextInfo contextInfo, long j) throws RemoteException;

    boolean startGeofencing(ContextInfo contextInfo) throws RemoteException;

    boolean stopGeofencing(ContextInfo contextInfo) throws RemoteException;

    public abstract class Stub extends Binder implements IGeofencing {
        public static final int TRANSACTION_createGeofence = 1;
        public static final int TRANSACTION_destroyGeofence = 2;
        public static final int TRANSACTION_getGeofences = 6;
        public static final int TRANSACTION_getMinDistanceParameter = 11;
        public static final int TRANSACTION_getMinTimeParameter = 9;
        public static final int TRANSACTION_isDeviceInsideGeofence = 7;
        public static final int TRANSACTION_isGeofencingEnabled = 5;
        public static final int TRANSACTION_setMinDistanceParameter = 10;
        public static final int TRANSACTION_setMinTimeParameter = 8;
        public static final int TRANSACTION_startGeofencing = 3;
        public static final int TRANSACTION_stopGeofencing = 4;

        class Proxy implements IGeofencing {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.location.IGeofencing
            public int createGeofence(ContextInfo contextInfo, Geofence geofence) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGeofencing.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeTypedObject(geofence, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.location.IGeofencing
            public boolean destroyGeofence(ContextInfo contextInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGeofencing.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.location.IGeofencing
            public List<Geofence> getGeofences(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGeofencing.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(Geofence.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IGeofencing.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.location.IGeofencing
            public float getMinDistanceParameter(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGeofencing.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readFloat();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.location.IGeofencing
            public long getMinTimeParameter(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGeofencing.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.location.IGeofencing
            public List isDeviceInsideGeofence(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGeofencing.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readArrayList(getClass().getClassLoader());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.location.IGeofencing
            public boolean isGeofencingEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGeofencing.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.location.IGeofencing
            public boolean setMinDistanceParameter(ContextInfo contextInfo, float f) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGeofencing.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeFloat(f);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.location.IGeofencing
            public boolean setMinTimeParameter(ContextInfo contextInfo, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGeofencing.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.location.IGeofencing
            public boolean startGeofencing(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGeofencing.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.location.IGeofencing
            public boolean stopGeofencing(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGeofencing.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IGeofencing.DESCRIPTOR);
        }

        public static IGeofencing asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IGeofencing.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IGeofencing)) ? new Proxy(iBinder) : (IGeofencing) iInterfaceQueryLocalInterface;
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "createGeofence";
                case 2:
                    return "destroyGeofence";
                case 3:
                    return "startGeofencing";
                case 4:
                    return "stopGeofencing";
                case 5:
                    return "isGeofencingEnabled";
                case 6:
                    return "getGeofences";
                case 7:
                    return "isDeviceInsideGeofence";
                case 8:
                    return "setMinTimeParameter";
                case 9:
                    return "getMinTimeParameter";
                case 10:
                    return "setMinDistanceParameter";
                case 11:
                    return "getMinDistanceParameter";
                default:
                    return null;
            }
        }

        public int getMaxTransactionId() {
            return 10;
        }

        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IGeofencing.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IGeofencing.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ContextInfo contextInfo = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    Geofence geofence = (Geofence) parcel.readTypedObject(Geofence.CREATOR);
                    parcel.enforceNoDataAvail();
                    int iCreateGeofence = createGeofence(contextInfo, geofence);
                    parcel2.writeNoException();
                    parcel2.writeInt(iCreateGeofence);
                    return true;
                case 2:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zDestroyGeofence = destroyGeofence(contextInfo2, i3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zDestroyGeofence);
                    return true;
                case 3:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zStartGeofencing = startGeofencing(contextInfo3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zStartGeofencing);
                    return true;
                case 4:
                    ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zStopGeofencing = stopGeofencing(contextInfo4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zStopGeofencing);
                    return true;
                case 5:
                    ContextInfo contextInfo5 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsGeofencingEnabled = isGeofencingEnabled(contextInfo5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsGeofencingEnabled);
                    return true;
                case 6:
                    ContextInfo contextInfo6 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<Geofence> geofences = getGeofences(contextInfo6);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(geofences, 1);
                    return true;
                case 7:
                    ContextInfo contextInfo7 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List listIsDeviceInsideGeofence = isDeviceInsideGeofence(contextInfo7);
                    parcel2.writeNoException();
                    parcel2.writeList(listIsDeviceInsideGeofence);
                    return true;
                case 8:
                    ContextInfo contextInfo8 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long j = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean minTimeParameter = setMinTimeParameter(contextInfo8, j);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(minTimeParameter);
                    return true;
                case 9:
                    ContextInfo contextInfo9 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    long minTimeParameter2 = getMinTimeParameter(contextInfo9);
                    parcel2.writeNoException();
                    parcel2.writeLong(minTimeParameter2);
                    return true;
                case 10:
                    ContextInfo contextInfo10 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    float f = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    boolean minDistanceParameter = setMinDistanceParameter(contextInfo10, f);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(minDistanceParameter);
                    return true;
                case 11:
                    ContextInfo contextInfo11 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    float minDistanceParameter2 = getMinDistanceParameter(contextInfo11);
                    parcel2.writeNoException();
                    parcel2.writeFloat(minDistanceParameter2);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }
}
