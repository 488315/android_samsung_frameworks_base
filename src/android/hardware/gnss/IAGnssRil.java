package android.hardware.gnss;

import android.hardware.gnss.IAGnssRilCallback;
import android.os.BadParcelableException;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IAGnssRil extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$gnss$IAGnssRil".replace('$', '.');
    public static final String HASH = "fc957f1d3d261d065ff5e5415f2d21caa79c310f";
    public static final int NETWORK_CAPABILITY_NOT_METERED = 1;
    public static final int NETWORK_CAPABILITY_NOT_ROAMING = 2;
    public static final int VERSION = 2;

    public @interface AGnssRefLocationType {
        public static final int GSM_CELLID = 1;
        public static final int LTE_CELLID = 4;
        public static final int NR_CELLID = 8;
        public static final int UMTS_CELLID = 2;
    }

    public @interface SetIdType {
        public static final int IMSI = 1;
        public static final int MSISDM = 2;
        public static final int NONE = 0;
    }

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void setCallback(IAGnssRilCallback iAGnssRilCallback) throws RemoteException;

    void setRefLocation(AGnssRefLocation aGnssRefLocation) throws RemoteException;

    void setSetId(int i, String str) throws RemoteException;

    void updateNetworkState(NetworkAttributes networkAttributes) throws RemoteException;

    public static class Default implements IAGnssRil {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.gnss.IAGnssRil
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.gnss.IAGnssRil
        public void setCallback(IAGnssRilCallback iAGnssRilCallback) throws RemoteException {
        }

        @Override // android.hardware.gnss.IAGnssRil
        public void setRefLocation(AGnssRefLocation aGnssRefLocation) throws RemoteException {
        }

        @Override // android.hardware.gnss.IAGnssRil
        public void setSetId(int i, String str) throws RemoteException {
        }

        @Override // android.hardware.gnss.IAGnssRil
        public void updateNetworkState(NetworkAttributes networkAttributes) throws RemoteException {
        }

        @Override // android.hardware.gnss.IAGnssRil
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IAGnssRil {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_setCallback = 1;
        static final int TRANSACTION_setRefLocation = 2;
        static final int TRANSACTION_setSetId = 3;
        static final int TRANSACTION_updateNetworkState = 4;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 16777214;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IAGnssRil asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IAGnssRil)) {
                return (IAGnssRil) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "setCallback";
            }
            if (i == 2) {
                return "setRefLocation";
            }
            if (i == 3) {
                return "setSetId";
            }
            if (i == 4) {
                return "updateNetworkState";
            }
            switch (i) {
                case 16777214:
                    return "getInterfaceHash";
                case 16777215:
                    return "getInterfaceVersion";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            String str = DESCRIPTOR;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(str);
            }
            if (i == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            if (i == 16777215) {
                parcel2.writeNoException();
                parcel2.writeInt(getInterfaceVersion());
                return true;
            }
            if (i == 16777214) {
                parcel2.writeNoException();
                parcel2.writeString(getInterfaceHash());
                return true;
            }
            if (i == 1) {
                IAGnssRilCallback asInterface = IAGnssRilCallback.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                setCallback(asInterface);
                parcel2.writeNoException();
            } else if (i == 2) {
                AGnssRefLocation aGnssRefLocation = (AGnssRefLocation) parcel.readTypedObject(AGnssRefLocation.CREATOR);
                parcel.enforceNoDataAvail();
                setRefLocation(aGnssRefLocation);
                parcel2.writeNoException();
            } else if (i == 3) {
                int readInt = parcel.readInt();
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                setSetId(readInt, readString);
                parcel2.writeNoException();
            } else if (i == 4) {
                NetworkAttributes networkAttributes = (NetworkAttributes) parcel.readTypedObject(NetworkAttributes.CREATOR);
                parcel.enforceNoDataAvail();
                updateNetworkState(networkAttributes);
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IAGnssRil {
            private IBinder mRemote;
            private int mCachedVersion = -1;
            private String mCachedHash = "-1";

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override // android.hardware.gnss.IAGnssRil
            public void setCallback(IAGnssRilCallback iAGnssRilCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeStrongInterface(iAGnssRilCallback);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new RemoteException("Method setCallback is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IAGnssRil
            public void setRefLocation(AGnssRefLocation aGnssRefLocation) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(aGnssRefLocation, 0);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new RemoteException("Method setRefLocation is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IAGnssRil
            public void setSetId(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new RemoteException("Method setSetId is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IAGnssRil
            public void updateNetworkState(NetworkAttributes networkAttributes) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(networkAttributes, 0);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new RemoteException("Method updateNetworkState is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IAGnssRil
            public int getInterfaceVersion() throws RemoteException {
                if (this.mCachedVersion == -1) {
                    Parcel obtain = Parcel.obtain(asBinder());
                    Parcel obtain2 = Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777215, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedVersion = obtain2.readInt();
                    } finally {
                        obtain2.recycle();
                        obtain.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.hardware.gnss.IAGnssRil
            public synchronized String getInterfaceHash() throws RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    Parcel obtain = Parcel.obtain(asBinder());
                    Parcel obtain2 = Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777214, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedHash = obtain2.readString();
                        obtain2.recycle();
                        obtain.recycle();
                    } catch (Throwable th) {
                        obtain2.recycle();
                        obtain.recycle();
                        throw th;
                    }
                }
                return this.mCachedHash;
            }
        }
    }

    public static class AGnssRefLocationCellID implements Parcelable {
        public static final Parcelable.Creator<AGnssRefLocationCellID> CREATOR = new Parcelable.Creator<AGnssRefLocationCellID>() { // from class: android.hardware.gnss.IAGnssRil.AGnssRefLocationCellID.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AGnssRefLocationCellID createFromParcel(Parcel parcel) {
                AGnssRefLocationCellID aGnssRefLocationCellID = new AGnssRefLocationCellID();
                aGnssRefLocationCellID.readFromParcel(parcel);
                return aGnssRefLocationCellID;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AGnssRefLocationCellID[] newArray(int i) {
                return new AGnssRefLocationCellID[i];
            }
        };
        public int type;
        public int mcc = 0;
        public int mnc = 0;
        public int lac = 0;
        public long cid = 0;
        public int tac = 0;
        public int pcid = 0;
        public int arfcn = 0;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public final int getStability() {
            return 1;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            int dataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeInt(this.type);
            parcel.writeInt(this.mcc);
            parcel.writeInt(this.mnc);
            parcel.writeInt(this.lac);
            parcel.writeLong(this.cid);
            parcel.writeInt(this.tac);
            parcel.writeInt(this.pcid);
            parcel.writeInt(this.arfcn);
            int dataPosition2 = parcel.dataPosition();
            parcel.setDataPosition(dataPosition);
            parcel.writeInt(dataPosition2 - dataPosition);
            parcel.setDataPosition(dataPosition2);
        }

        public final void readFromParcel(Parcel parcel) {
            int dataPosition = parcel.dataPosition();
            int readInt = parcel.readInt();
            try {
                if (readInt < 4) {
                    throw new BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.type = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.mcc = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.mnc = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.lac = parcel.readInt();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.cid = parcel.readLong();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.tac = parcel.readInt();
                                        if (parcel.dataPosition() - dataPosition < readInt) {
                                            this.pcid = parcel.readInt();
                                            if (parcel.dataPosition() - dataPosition < readInt) {
                                                this.arfcn = parcel.readInt();
                                                if (dataPosition > Integer.MAX_VALUE - readInt) {
                                                    throw new BadParcelableException("Overflow in the size of parcelable");
                                                }
                                            } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                                throw new BadParcelableException("Overflow in the size of parcelable");
                                            }
                                        } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                            throw new BadParcelableException("Overflow in the size of parcelable");
                                        }
                                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                        throw new BadParcelableException("Overflow in the size of parcelable");
                                    }
                                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                    throw new BadParcelableException("Overflow in the size of parcelable");
                                }
                            } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } catch (Throwable th) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                throw th;
            }
        }
    }

    public static class AGnssRefLocation implements Parcelable {
        public static final Parcelable.Creator<AGnssRefLocation> CREATOR = new Parcelable.Creator<AGnssRefLocation>() { // from class: android.hardware.gnss.IAGnssRil.AGnssRefLocation.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AGnssRefLocation createFromParcel(Parcel parcel) {
                AGnssRefLocation aGnssRefLocation = new AGnssRefLocation();
                aGnssRefLocation.readFromParcel(parcel);
                return aGnssRefLocation;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AGnssRefLocation[] newArray(int i) {
                return new AGnssRefLocation[i];
            }
        };
        public AGnssRefLocationCellID cellID;
        public int type;

        @Override // android.os.Parcelable
        public final int getStability() {
            return 1;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            int dataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeInt(this.type);
            parcel.writeTypedObject(this.cellID, i);
            int dataPosition2 = parcel.dataPosition();
            parcel.setDataPosition(dataPosition);
            parcel.writeInt(dataPosition2 - dataPosition);
            parcel.setDataPosition(dataPosition2);
        }

        public final void readFromParcel(Parcel parcel) {
            int dataPosition = parcel.dataPosition();
            int readInt = parcel.readInt();
            try {
                if (readInt < 4) {
                    throw new BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.type = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.cellID = (AGnssRefLocationCellID) parcel.readTypedObject(AGnssRefLocationCellID.CREATOR);
                        if (dataPosition > Integer.MAX_VALUE - readInt) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } catch (Throwable th) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                throw th;
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return describeContents(this.cellID);
        }

        private int describeContents(Object obj) {
            if (obj != null && (obj instanceof Parcelable)) {
                return ((Parcelable) obj).describeContents();
            }
            return 0;
        }
    }

    public static class NetworkAttributes implements Parcelable {
        public static final Parcelable.Creator<NetworkAttributes> CREATOR = new Parcelable.Creator<NetworkAttributes>() { // from class: android.hardware.gnss.IAGnssRil.NetworkAttributes.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public NetworkAttributes createFromParcel(Parcel parcel) {
                NetworkAttributes networkAttributes = new NetworkAttributes();
                networkAttributes.readFromParcel(parcel);
                return networkAttributes;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public NetworkAttributes[] newArray(int i) {
                return new NetworkAttributes[i];
            }
        };
        public String apn;
        public long networkHandle = 0;
        public boolean isConnected = false;
        public int capabilities = 0;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public final int getStability() {
            return 1;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            int dataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeLong(this.networkHandle);
            parcel.writeBoolean(this.isConnected);
            parcel.writeInt(this.capabilities);
            parcel.writeString(this.apn);
            int dataPosition2 = parcel.dataPosition();
            parcel.setDataPosition(dataPosition);
            parcel.writeInt(dataPosition2 - dataPosition);
            parcel.setDataPosition(dataPosition2);
        }

        public final void readFromParcel(Parcel parcel) {
            int dataPosition = parcel.dataPosition();
            int readInt = parcel.readInt();
            try {
                if (readInt < 4) {
                    throw new BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - dataPosition < readInt) {
                    this.networkHandle = parcel.readLong();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.isConnected = parcel.readBoolean();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.capabilities = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.apn = parcel.readString();
                                if (dataPosition > Integer.MAX_VALUE - readInt) {
                                    throw new BadParcelableException("Overflow in the size of parcelable");
                                }
                            } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
            } catch (Throwable th) {
                if (dataPosition > Integer.MAX_VALUE - readInt) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(dataPosition + readInt);
                throw th;
            }
        }
    }
}
