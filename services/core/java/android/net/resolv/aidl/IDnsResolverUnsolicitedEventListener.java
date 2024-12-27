package android.net.resolv.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IDnsResolverUnsolicitedEventListener extends IInterface {
    public static final String DESCRIPTOR =
            "android$net$resolv$aidl$IDnsResolverUnsolicitedEventListener".replace('$', '.');
    public static final int DNS_HEALTH_RESULT_OK = 0;
    public static final int DNS_HEALTH_RESULT_TIMEOUT = 255;
    public static final String HASH = "882638dc86e8afd0924ecf7c28db6cce572f7e7d";
    public static final int PREFIX_OPERATION_ADDED = 1;
    public static final int PREFIX_OPERATION_REMOVED = 2;
    public static final int VALIDATION_RESULT_FAILURE = 2;
    public static final int VALIDATION_RESULT_SUCCESS = 1;
    public static final int VERSION = 9;

    public class Default implements IDnsResolverUnsolicitedEventListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener
        public String getInterfaceHash() {
            return "";
        }

        @Override // android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener
        public void onDnsHealthEvent(DnsHealthEventParcel dnsHealthEventParcel)
                throws RemoteException {}

        @Override // android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener
        public void onNat64PrefixEvent(Nat64PrefixEventParcel nat64PrefixEventParcel)
                throws RemoteException {}

        @Override // android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener
        public void onPrivateDnsValidationEvent(
                PrivateDnsValidationEventParcel privateDnsValidationEventParcel)
                throws RemoteException {}
    }

    public abstract class Stub extends Binder implements IDnsResolverUnsolicitedEventListener {
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_onDnsHealthEvent = 1;
        static final int TRANSACTION_onNat64PrefixEvent = 2;
        static final int TRANSACTION_onPrivateDnsValidationEvent = 3;

        public final class Proxy implements IDnsResolverUnsolicitedEventListener {
            public String mCachedHash;
            public int mCachedVersion;
            public IBinder mRemote;

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener
            public final synchronized String getInterfaceHash() {
                try {
                    if ("-1".equals(this.mCachedHash)) {
                        Parcel obtain = Parcel.obtain();
                        Parcel obtain2 = Parcel.obtain();
                        try {
                            obtain.writeInterfaceToken(
                                    IDnsResolverUnsolicitedEventListener.DESCRIPTOR);
                            this.mRemote.transact(
                                    Stub.TRANSACTION_getInterfaceHash, obtain, obtain2, 0);
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
                } catch (Throwable th2) {
                    throw th2;
                }
                return this.mCachedHash;
            }

            @Override // android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener
            public final int getInterfaceVersion() {
                if (this.mCachedVersion == -1) {
                    Parcel obtain = Parcel.obtain();
                    Parcel obtain2 = Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(IDnsResolverUnsolicitedEventListener.DESCRIPTOR);
                        this.mRemote.transact(
                                Stub.TRANSACTION_getInterfaceVersion, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedVersion = obtain2.readInt();
                    } finally {
                        obtain2.recycle();
                        obtain.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener
            public final void onDnsHealthEvent(DnsHealthEventParcel dnsHealthEventParcel) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDnsResolverUnsolicitedEventListener.DESCRIPTOR);
                    obtain.writeTypedObject(dnsHealthEventParcel, 0);
                    if (this.mRemote.transact(1, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method onDnsHealthEvent is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener
            public final void onNat64PrefixEvent(Nat64PrefixEventParcel nat64PrefixEventParcel) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDnsResolverUnsolicitedEventListener.DESCRIPTOR);
                    obtain.writeTypedObject(nat64PrefixEventParcel, 0);
                    if (this.mRemote.transact(2, obtain, null, 1)) {
                    } else {
                        throw new RemoteException("Method onNat64PrefixEvent is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener
            public final void onPrivateDnsValidationEvent(
                    PrivateDnsValidationEventParcel privateDnsValidationEventParcel) {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDnsResolverUnsolicitedEventListener.DESCRIPTOR);
                    obtain.writeTypedObject(privateDnsValidationEventParcel, 0);
                    if (this.mRemote.transact(3, obtain, null, 1)) {
                    } else {
                        throw new RemoteException(
                                "Method onPrivateDnsValidationEvent is unimplemented.");
                    }
                } finally {
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IDnsResolverUnsolicitedEventListener.DESCRIPTOR);
        }

        public static IDnsResolverUnsolicitedEventListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface =
                    iBinder.queryLocalInterface(IDnsResolverUnsolicitedEventListener.DESCRIPTOR);
            if (queryLocalInterface != null
                    && (queryLocalInterface instanceof IDnsResolverUnsolicitedEventListener)) {
                return (IDnsResolverUnsolicitedEventListener) queryLocalInterface;
            }
            Proxy proxy = new Proxy();
            proxy.mCachedVersion = -1;
            proxy.mCachedHash = "-1";
            proxy.mRemote = iBinder;
            return proxy;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2)
                throws RemoteException {
            String str = IDnsResolverUnsolicitedEventListener.DESCRIPTOR;
            if (i >= 1 && i <= TRANSACTION_getInterfaceVersion) {
                parcel.enforceInterface(str);
            }
            if (i == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            if (i == TRANSACTION_getInterfaceVersion) {
                parcel2.writeNoException();
                parcel2.writeInt(getInterfaceVersion());
                return true;
            }
            if (i == TRANSACTION_getInterfaceHash) {
                parcel2.writeNoException();
                parcel2.writeString(getInterfaceHash());
                return true;
            }
            if (i == 1) {
                onDnsHealthEvent(
                        (DnsHealthEventParcel)
                                parcel.readTypedObject(DnsHealthEventParcel.CREATOR));
            } else if (i == 2) {
                onNat64PrefixEvent(
                        (Nat64PrefixEventParcel)
                                parcel.readTypedObject(Nat64PrefixEventParcel.CREATOR));
            } else {
                if (i != 3) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                onPrivateDnsValidationEvent(
                        (PrivateDnsValidationEventParcel)
                                parcel.readTypedObject(PrivateDnsValidationEventParcel.CREATOR));
            }
            return true;
        }
    }

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    void onDnsHealthEvent(DnsHealthEventParcel dnsHealthEventParcel) throws RemoteException;

    void onNat64PrefixEvent(Nat64PrefixEventParcel nat64PrefixEventParcel) throws RemoteException;

    void onPrivateDnsValidationEvent(
            PrivateDnsValidationEventParcel privateDnsValidationEventParcel) throws RemoteException;
}
