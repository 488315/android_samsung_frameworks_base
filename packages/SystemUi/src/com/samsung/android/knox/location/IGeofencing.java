package com.samsung.android.knox.location;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.samsung.android.knox.ContextInfo;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface IGeofencing extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.location.IGeofencing";

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements IGeofencing {
        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.location.IGeofencing
        public final int createGeofence(ContextInfo contextInfo, Geofence geofence) {
            return 0;
        }

        @Override // com.samsung.android.knox.location.IGeofencing
        public final boolean destroyGeofence(ContextInfo contextInfo, int i) {
            return false;
        }

        @Override // com.samsung.android.knox.location.IGeofencing
        public final List<Geofence> getGeofences(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.location.IGeofencing
        public final float getMinDistanceParameter(ContextInfo contextInfo) {
            return 0.0f;
        }

        @Override // com.samsung.android.knox.location.IGeofencing
        public final long getMinTimeParameter(ContextInfo contextInfo) {
            return 0L;
        }

        @Override // com.samsung.android.knox.location.IGeofencing
        public final List isDeviceInsideGeofence(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.location.IGeofencing
        public final boolean isGeofencingEnabled(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.location.IGeofencing
        public final boolean setMinDistanceParameter(ContextInfo contextInfo, float f) {
            return false;
        }

        @Override // com.samsung.android.knox.location.IGeofencing
        public final boolean setMinTimeParameter(ContextInfo contextInfo, long j) {
            return false;
        }

        @Override // com.samsung.android.knox.location.IGeofencing
        public final boolean startGeofencing(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.location.IGeofencing
        public final boolean stopGeofencing(ContextInfo contextInfo) {
            return false;
        }
    }

    int createGeofence(ContextInfo contextInfo, Geofence geofence);

    boolean destroyGeofence(ContextInfo contextInfo, int i);

    List<Geofence> getGeofences(ContextInfo contextInfo);

    float getMinDistanceParameter(ContextInfo contextInfo);

    long getMinTimeParameter(ContextInfo contextInfo);

    List isDeviceInsideGeofence(ContextInfo contextInfo);

    boolean isGeofencingEnabled(ContextInfo contextInfo);

    boolean setMinDistanceParameter(ContextInfo contextInfo, float f);

    boolean setMinTimeParameter(ContextInfo contextInfo, long j);

    boolean startGeofencing(ContextInfo contextInfo);

    boolean stopGeofencing(ContextInfo contextInfo);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IGeofencing {
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

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IGeofencing {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.location.IGeofencing
            public final int createGeofence(ContextInfo contextInfo, Geofence geofence) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IGeofencing.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(geofence, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.location.IGeofencing
            public final boolean destroyGeofence(ContextInfo contextInfo, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IGeofencing.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.location.IGeofencing
            public final List<Geofence> getGeofences(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IGeofencing.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(Geofence.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final String getInterfaceDescriptor() {
                return IGeofencing.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.location.IGeofencing
            public final float getMinDistanceParameter(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IGeofencing.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.location.IGeofencing
            public final long getMinTimeParameter(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IGeofencing.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.location.IGeofencing
            public final List isDeviceInsideGeofence(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IGeofencing.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readArrayList(Proxy.class.getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.location.IGeofencing
            public final boolean isGeofencingEnabled(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IGeofencing.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.location.IGeofencing
            public final boolean setMinDistanceParameter(ContextInfo contextInfo, float f) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IGeofencing.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeFloat(f);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.location.IGeofencing
            public final boolean setMinTimeParameter(ContextInfo contextInfo, long j) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IGeofencing.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeLong(j);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.location.IGeofencing
            public final boolean startGeofencing(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IGeofencing.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.location.IGeofencing
            public final boolean stopGeofencing(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IGeofencing.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IGeofencing.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IGeofencing)) ? new Proxy(iBinder) : (IGeofencing) queryLocalInterface;
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

        public final int getMaxTransactionId() {
            return 10;
        }

        public final String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
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
                    int createGeofence = createGeofence(contextInfo, geofence);
                    parcel2.writeNoException();
                    parcel2.writeInt(createGeofence);
                    return true;
                case 2:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean destroyGeofence = destroyGeofence(contextInfo2, readInt);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(destroyGeofence);
                    return true;
                case 3:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean startGeofencing = startGeofencing(contextInfo3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(startGeofencing);
                    return true;
                case 4:
                    ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean stopGeofencing = stopGeofencing(contextInfo4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(stopGeofencing);
                    return true;
                case 5:
                    ContextInfo contextInfo5 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isGeofencingEnabled = isGeofencingEnabled(contextInfo5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isGeofencingEnabled);
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
                    List isDeviceInsideGeofence = isDeviceInsideGeofence(contextInfo7);
                    parcel2.writeNoException();
                    parcel2.writeList(isDeviceInsideGeofence);
                    return true;
                case 8:
                    ContextInfo contextInfo8 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    boolean minTimeParameter = setMinTimeParameter(contextInfo8, readLong);
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
                    float readFloat = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    boolean minDistanceParameter = setMinDistanceParameter(contextInfo10, readFloat);
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
        public final IBinder asBinder() {
            return this;
        }
    }
}
