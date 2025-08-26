package android.hardware.gnss.visibility_control;

import android.os.BadParcelableException;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IGnssVisibilityControlCallback extends IInterface {
    public static final String DESCRIPTOR = "android$hardware$gnss$visibility_control$IGnssVisibilityControlCallback".replace('$', '.');
    public static final String HASH = "fc957f1d3d261d065ff5e5415f2d21caa79c310f";
    public static final int VERSION = 2;

    public @interface NfwProtocolStack {
        public static final int CTRL_PLANE = 0;
        public static final int IMS = 10;
        public static final int OTHER_PROTOCOL_STACK = 100;
        public static final int SIM = 11;
        public static final int SUPL = 1;
    }

    public @interface NfwRequestor {
        public static final int AUTOMOBILE_CLIENT = 20;
        public static final int CARRIER = 0;
        public static final int GNSS_CHIPSET_VENDOR = 12;
        public static final int MODEM_CHIPSET_VENDOR = 11;
        public static final int OEM = 10;
        public static final int OTHER_CHIPSET_VENDOR = 13;
        public static final int OTHER_REQUESTOR = 100;
    }

    public @interface NfwResponseType {
        public static final int ACCEPTED_LOCATION_PROVIDED = 2;
        public static final int ACCEPTED_NO_LOCATION_PROVIDED = 1;
        public static final int REJECTED = 0;
    }

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    boolean isInEmergencySession() throws RemoteException;

    void nfwNotifyCb(NfwNotification nfwNotification) throws RemoteException;

    public static class Default implements IGnssVisibilityControlCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.gnss.visibility_control.IGnssVisibilityControlCallback
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.gnss.visibility_control.IGnssVisibilityControlCallback
        public boolean isInEmergencySession() throws RemoteException {
            return false;
        }

        @Override // android.hardware.gnss.visibility_control.IGnssVisibilityControlCallback
        public void nfwNotifyCb(NfwNotification nfwNotification) throws RemoteException {
        }

        @Override // android.hardware.gnss.visibility_control.IGnssVisibilityControlCallback
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IGnssVisibilityControlCallback {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_isInEmergencySession = 2;
        static final int TRANSACTION_nfwNotifyCb = 1;

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

        public static IGnssVisibilityControlCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IGnssVisibilityControlCallback)) {
                return (IGnssVisibilityControlCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "nfwNotifyCb";
            }
            if (i == 2) {
                return "isInEmergencySession";
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
                NfwNotification nfwNotification = (NfwNotification) parcel.readTypedObject(NfwNotification.CREATOR);
                parcel.enforceNoDataAvail();
                nfwNotifyCb(nfwNotification);
                parcel2.writeNoException();
            } else if (i == 2) {
                boolean zIsInEmergencySession = isInEmergencySession();
                parcel2.writeNoException();
                parcel2.writeBoolean(zIsInEmergencySession);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IGnssVisibilityControlCallback {
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

            @Override // android.hardware.gnss.visibility_control.IGnssVisibilityControlCallback
            public void nfwNotifyCb(NfwNotification nfwNotification) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    parcelObtain.writeTypedObject(nfwNotification, 0);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method nfwNotifyCb is unimplemented.");
                    }
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.gnss.visibility_control.IGnssVisibilityControlCallback
            public boolean isInEmergencySession() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcelObtain, parcelObtain2, 0)) {
                        throw new RemoteException("Method isInEmergencySession is unimplemented.");
                    }
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.hardware.gnss.visibility_control.IGnssVisibilityControlCallback
            public int getInterfaceVersion() throws RemoteException {
                if (this.mCachedVersion == -1) {
                    Parcel parcelObtain = Parcel.obtain(asBinder());
                    Parcel parcelObtain2 = Parcel.obtain();
                    try {
                        parcelObtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777215, parcelObtain, parcelObtain2, 0);
                        parcelObtain2.readException();
                        this.mCachedVersion = parcelObtain2.readInt();
                    } finally {
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.hardware.gnss.visibility_control.IGnssVisibilityControlCallback
            public synchronized String getInterfaceHash() throws RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    Parcel parcelObtain = Parcel.obtain(asBinder());
                    Parcel parcelObtain2 = Parcel.obtain();
                    try {
                        parcelObtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777214, parcelObtain, parcelObtain2, 0);
                        parcelObtain2.readException();
                        this.mCachedHash = parcelObtain2.readString();
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                    } catch (Throwable th) {
                        parcelObtain2.recycle();
                        parcelObtain.recycle();
                        throw th;
                    }
                }
                return this.mCachedHash;
            }
        }
    }

    public static class NfwNotification implements Parcelable {
        public static final Parcelable.Creator<NfwNotification> CREATOR = new Parcelable.Creator<NfwNotification>() { // from class: android.hardware.gnss.visibility_control.IGnssVisibilityControlCallback.NfwNotification.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public NfwNotification createFromParcel(Parcel parcel) {
                NfwNotification nfwNotification = new NfwNotification();
                nfwNotification.readFromParcel(parcel);
                return nfwNotification;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public NfwNotification[] newArray(int i) {
                return new NfwNotification[i];
            }
        };
        public boolean inEmergencyMode = false;
        public boolean isCachedLocation = false;
        public String otherProtocolStackName;
        public int protocolStack;
        public String proxyAppPackageName;
        public int requestor;
        public String requestorId;
        public int responseType;

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
            int iDataPosition = parcel.dataPosition();
            parcel.writeInt(0);
            parcel.writeString(this.proxyAppPackageName);
            parcel.writeInt(this.protocolStack);
            parcel.writeString(this.otherProtocolStackName);
            parcel.writeInt(this.requestor);
            parcel.writeString(this.requestorId);
            parcel.writeInt(this.responseType);
            parcel.writeBoolean(this.inEmergencyMode);
            parcel.writeBoolean(this.isCachedLocation);
            int iDataPosition2 = parcel.dataPosition();
            parcel.setDataPosition(iDataPosition);
            parcel.writeInt(iDataPosition2 - iDataPosition);
            parcel.setDataPosition(iDataPosition2);
        }

        public final void readFromParcel(Parcel parcel) {
            int iDataPosition = parcel.dataPosition();
            int i = parcel.readInt();
            try {
                if (i < 4) {
                    throw new BadParcelableException("Parcelable too small");
                }
                if (parcel.dataPosition() - iDataPosition < i) {
                    this.proxyAppPackageName = parcel.readString();
                    if (parcel.dataPosition() - iDataPosition < i) {
                        this.protocolStack = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i) {
                            this.otherProtocolStackName = parcel.readString();
                            if (parcel.dataPosition() - iDataPosition < i) {
                                this.requestor = parcel.readInt();
                                if (parcel.dataPosition() - iDataPosition < i) {
                                    this.requestorId = parcel.readString();
                                    if (parcel.dataPosition() - iDataPosition < i) {
                                        this.responseType = parcel.readInt();
                                        if (parcel.dataPosition() - iDataPosition < i) {
                                            this.inEmergencyMode = parcel.readBoolean();
                                            if (parcel.dataPosition() - iDataPosition < i) {
                                                this.isCachedLocation = parcel.readBoolean();
                                                if (iDataPosition > Integer.MAX_VALUE - i) {
                                                    throw new BadParcelableException("Overflow in the size of parcelable");
                                                }
                                            } else if (iDataPosition > Integer.MAX_VALUE - i) {
                                                throw new BadParcelableException("Overflow in the size of parcelable");
                                            }
                                        } else if (iDataPosition > Integer.MAX_VALUE - i) {
                                            throw new BadParcelableException("Overflow in the size of parcelable");
                                        }
                                    } else if (iDataPosition > Integer.MAX_VALUE - i) {
                                        throw new BadParcelableException("Overflow in the size of parcelable");
                                    }
                                } else if (iDataPosition > Integer.MAX_VALUE - i) {
                                    throw new BadParcelableException("Overflow in the size of parcelable");
                                }
                            } else if (iDataPosition > Integer.MAX_VALUE - i) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (iDataPosition > Integer.MAX_VALUE - i) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (iDataPosition > Integer.MAX_VALUE - i) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                } else if (iDataPosition > Integer.MAX_VALUE - i) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(iDataPosition + i);
            } catch (Throwable th) {
                if (iDataPosition > Integer.MAX_VALUE - i) {
                    throw new BadParcelableException("Overflow in the size of parcelable");
                }
                parcel.setDataPosition(iDataPosition + i);
                throw th;
            }
        }
    }
}
