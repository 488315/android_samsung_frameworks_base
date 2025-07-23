package android.hardware.gnss;

import android.os.BadParcelableException;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IGnssNavigationMessageCallback extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$gnss$IGnssNavigationMessageCallback".replace('$', '.');
    public static final String HASH = "fc957f1d3d261d065ff5e5415f2d21caa79c310f";
    public static final int VERSION = 2;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void gnssNavigationMessageCb(GnssNavigationMessage gnssNavigationMessage) throws RemoteException;

    public static class Default implements IGnssNavigationMessageCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.gnss.IGnssNavigationMessageCallback
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.gnss.IGnssNavigationMessageCallback
        public void gnssNavigationMessageCb(GnssNavigationMessage gnssNavigationMessage) throws RemoteException {
        }

        @Override // android.hardware.gnss.IGnssNavigationMessageCallback
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IGnssNavigationMessageCallback {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_gnssNavigationMessageCb = 1;

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

        public static IGnssNavigationMessageCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IGnssNavigationMessageCallback)) {
                return (IGnssNavigationMessageCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "gnssNavigationMessageCb";
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
                GnssNavigationMessage gnssNavigationMessage = (GnssNavigationMessage) parcel.readTypedObject(GnssNavigationMessage.CREATOR);
                parcel.enforceNoDataAvail();
                gnssNavigationMessageCb(gnssNavigationMessage);
                parcel2.writeNoException();
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IGnssNavigationMessageCallback {
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

            @Override // android.hardware.gnss.IGnssNavigationMessageCallback
            public void gnssNavigationMessageCb(GnssNavigationMessage gnssNavigationMessage) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(gnssNavigationMessage, 0);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new RemoteException("Method gnssNavigationMessageCb is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.gnss.IGnssNavigationMessageCallback
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

            @Override // android.hardware.gnss.IGnssNavigationMessageCallback
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

    public static class GnssNavigationMessage implements Parcelable {
        public static final Parcelable.Creator<GnssNavigationMessage> CREATOR = new Parcelable.Creator<GnssNavigationMessage>() { // from class: android.hardware.gnss.IGnssNavigationMessageCallback.GnssNavigationMessage.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public GnssNavigationMessage createFromParcel(Parcel parcel) {
                GnssNavigationMessage gnssNavigationMessage = new GnssNavigationMessage();
                gnssNavigationMessage.readFromParcel(parcel);
                return gnssNavigationMessage;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public GnssNavigationMessage[] newArray(int i) {
                return new GnssNavigationMessage[i];
            }
        };
        public static final int STATUS_PARITY_PASSED = 1;
        public static final int STATUS_PARITY_REBUILT = 2;
        public static final int STATUS_UNKNOWN = 0;
        public byte[] data;
        public int type;
        public int svid = 0;
        public int status = 0;
        public int messageId = 0;
        public int submessageId = 0;

        public @interface GnssNavigationMessageType {
            public static final int BDS_CNAV1 = 1283;
            public static final int BDS_CNAV2 = 1284;
            public static final int BDS_D1 = 1281;
            public static final int BDS_D2 = 1282;
            public static final int GAL_F = 1538;
            public static final int GAL_I = 1537;
            public static final int GLO_L1CA = 769;
            public static final int GPS_CNAV2 = 260;
            public static final int GPS_L1CA = 257;
            public static final int GPS_L2CNAV = 258;
            public static final int GPS_L5CNAV = 259;
            public static final int IRN_L5CA = 1793;
            public static final int QZS_L1CA = 1025;
            public static final int SBS = 513;
            public static final int UNKNOWN = 0;
        }

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
            parcel.writeInt(this.svid);
            parcel.writeInt(this.type);
            parcel.writeInt(this.status);
            parcel.writeInt(this.messageId);
            parcel.writeInt(this.submessageId);
            parcel.writeByteArray(this.data);
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
                    this.svid = parcel.readInt();
                    if (parcel.dataPosition() - dataPosition < readInt) {
                        this.type = parcel.readInt();
                        if (parcel.dataPosition() - dataPosition < readInt) {
                            this.status = parcel.readInt();
                            if (parcel.dataPosition() - dataPosition < readInt) {
                                this.messageId = parcel.readInt();
                                if (parcel.dataPosition() - dataPosition < readInt) {
                                    this.submessageId = parcel.readInt();
                                    if (parcel.dataPosition() - dataPosition < readInt) {
                                        this.data = parcel.createByteArray();
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
