package android.net.resolv.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
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
    public void onDnsHealthEvent(DnsHealthEventParcel dnsHealthEventParcel) {}

    @Override // android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener
    public void onNat64PrefixEvent(Nat64PrefixEventParcel nat64PrefixEventParcel) {}

    @Override // android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener
    public void onPrivateDnsValidationEvent(
        PrivateDnsValidationEventParcel privateDnsValidationEventParcel) {}
  }

  String getInterfaceHash();

  int getInterfaceVersion();

  void onDnsHealthEvent(DnsHealthEventParcel dnsHealthEventParcel);

  void onNat64PrefixEvent(Nat64PrefixEventParcel nat64PrefixEventParcel);

  void onPrivateDnsValidationEvent(PrivateDnsValidationEventParcel privateDnsValidationEventParcel);

  public abstract class Stub extends Binder implements IDnsResolverUnsolicitedEventListener {
    static final int TRANSACTION_getInterfaceHash = 16777214;
    static final int TRANSACTION_getInterfaceVersion = 16777215;
    static final int TRANSACTION_onDnsHealthEvent = 1;
    static final int TRANSACTION_onNat64PrefixEvent = 2;
    static final int TRANSACTION_onPrivateDnsValidationEvent = 3;

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return this;
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
      return new Proxy(iBinder);
    }

    @Override // android.os.Binder
    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
      String str = IDnsResolverUnsolicitedEventListener.DESCRIPTOR;
      if (i >= 1 && i <= TRANSACTION_getInterfaceVersion) {
        parcel.enforceInterface(str);
      }
      switch (i) {
        case TRANSACTION_getInterfaceHash /* 16777214 */:
          parcel2.writeNoException();
          parcel2.writeString(getInterfaceHash());
          break;
        case TRANSACTION_getInterfaceVersion /* 16777215 */:
          parcel2.writeNoException();
          parcel2.writeInt(getInterfaceVersion());
          break;
        case 1598968902:
          parcel2.writeString(str);
          break;
        default:
          if (i == 1) {
            onDnsHealthEvent(
                (DnsHealthEventParcel) parcel.readTypedObject(DnsHealthEventParcel.CREATOR));
            break;
          } else if (i == 2) {
            onNat64PrefixEvent(
                (Nat64PrefixEventParcel) parcel.readTypedObject(Nat64PrefixEventParcel.CREATOR));
            break;
          } else if (i == 3) {
            onPrivateDnsValidationEvent(
                (PrivateDnsValidationEventParcel)
                    parcel.readTypedObject(PrivateDnsValidationEventParcel.CREATOR));
            break;
          } else {
            break;
          }
      }
      return true;
    }

    public class Proxy implements IDnsResolverUnsolicitedEventListener {
      public IBinder mRemote;
      public int mCachedVersion = -1;
      public String mCachedHash = "-1";

      public Proxy(IBinder iBinder) {
        this.mRemote = iBinder;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      @Override // android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener
      public void onDnsHealthEvent(DnsHealthEventParcel dnsHealthEventParcel) {
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
      public void onNat64PrefixEvent(Nat64PrefixEventParcel nat64PrefixEventParcel) {
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
      public void onPrivateDnsValidationEvent(
          PrivateDnsValidationEventParcel privateDnsValidationEventParcel) {
        Parcel obtain = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(IDnsResolverUnsolicitedEventListener.DESCRIPTOR);
          obtain.writeTypedObject(privateDnsValidationEventParcel, 0);
          if (this.mRemote.transact(3, obtain, null, 1)) {
          } else {
            throw new RemoteException("Method onPrivateDnsValidationEvent is unimplemented.");
          }
        } finally {
          obtain.recycle();
        }
      }

      @Override // android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener
      public int getInterfaceVersion() {
        if (this.mCachedVersion == -1) {
          Parcel obtain = Parcel.obtain();
          Parcel obtain2 = Parcel.obtain();
          try {
            obtain.writeInterfaceToken(IDnsResolverUnsolicitedEventListener.DESCRIPTOR);
            this.mRemote.transact(Stub.TRANSACTION_getInterfaceVersion, obtain, obtain2, 0);
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
      public synchronized String getInterfaceHash() {
        if ("-1".equals(this.mCachedHash)) {
          Parcel obtain = Parcel.obtain();
          Parcel obtain2 = Parcel.obtain();
          try {
            obtain.writeInterfaceToken(IDnsResolverUnsolicitedEventListener.DESCRIPTOR);
            this.mRemote.transact(Stub.TRANSACTION_getInterfaceHash, obtain, obtain2, 0);
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
}
