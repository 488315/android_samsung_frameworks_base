package android.net;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface INetdUnsolicitedEventListener extends IInterface {
  public static final String DESCRIPTOR =
      "android$net$INetdUnsolicitedEventListener".replace('$', '.');
  public static final String HASH = "38614f80a23b92603d4851177e57c460aec1b606";
  public static final int VERSION = 13;

  public class Default implements INetdUnsolicitedEventListener {
    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }

    @Override // android.net.INetdUnsolicitedEventListener
    public String getInterfaceHash() {
      return "";
    }

    @Override // android.net.INetdUnsolicitedEventListener
    public int getInterfaceVersion() {
      return 0;
    }

    @Override // android.net.INetdUnsolicitedEventListener
    public void onInterfaceAdded(String str) {}

    @Override // android.net.INetdUnsolicitedEventListener
    public void onInterfaceAddressRemoved(String str, String str2, int i, int i2) {}

    @Override // android.net.INetdUnsolicitedEventListener
    public void onInterfaceAddressUpdated(String str, String str2, int i, int i2) {}

    @Override // android.net.INetdUnsolicitedEventListener
    public void onInterfaceChanged(String str, boolean z) {}

    @Override // android.net.INetdUnsolicitedEventListener
    public void onInterfaceClassActivityChanged(boolean z, int i, long j, int i2) {}

    @Override // android.net.INetdUnsolicitedEventListener
    public void onInterfaceDnsServerInfo(String str, long j, String[] strArr) {}

    @Override // android.net.INetdUnsolicitedEventListener
    public void onInterfaceLinkStateChanged(String str, boolean z) {}

    @Override // android.net.INetdUnsolicitedEventListener
    public void onInterfaceRemoved(String str) {}

    @Override // android.net.INetdUnsolicitedEventListener
    public void onQuotaLimitReached(String str, String str2) {}

    @Override // android.net.INetdUnsolicitedEventListener
    public void onRouteChanged(boolean z, String str, String str2, String str3) {}

    @Override // android.net.INetdUnsolicitedEventListener
    public void onStrictCleartextDetected(int i, String str) {}
  }

  String getInterfaceHash();

  int getInterfaceVersion();

  void onInterfaceAdded(String str);

  void onInterfaceAddressRemoved(String str, String str2, int i, int i2);

  void onInterfaceAddressUpdated(String str, String str2, int i, int i2);

  void onInterfaceChanged(String str, boolean z);

  void onInterfaceClassActivityChanged(boolean z, int i, long j, int i2);

  void onInterfaceDnsServerInfo(String str, long j, String[] strArr);

  void onInterfaceLinkStateChanged(String str, boolean z);

  void onInterfaceRemoved(String str);

  void onQuotaLimitReached(String str, String str2);

  void onRouteChanged(boolean z, String str, String str2, String str3);

  void onStrictCleartextDetected(int i, String str);

  public abstract class Stub extends Binder implements INetdUnsolicitedEventListener {
    static final int TRANSACTION_getInterfaceHash = 16777214;
    static final int TRANSACTION_getInterfaceVersion = 16777215;
    static final int TRANSACTION_onInterfaceAdded = 6;
    static final int TRANSACTION_onInterfaceAddressRemoved = 5;
    static final int TRANSACTION_onInterfaceAddressUpdated = 4;
    static final int TRANSACTION_onInterfaceChanged = 8;
    static final int TRANSACTION_onInterfaceClassActivityChanged = 1;
    static final int TRANSACTION_onInterfaceDnsServerInfo = 3;
    static final int TRANSACTION_onInterfaceLinkStateChanged = 9;
    static final int TRANSACTION_onInterfaceRemoved = 7;
    static final int TRANSACTION_onQuotaLimitReached = 2;
    static final int TRANSACTION_onRouteChanged = 10;
    static final int TRANSACTION_onStrictCleartextDetected = 11;

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return this;
    }

    public Stub() {
      attachInterface(this, INetdUnsolicitedEventListener.DESCRIPTOR);
    }

    public static INetdUnsolicitedEventListener asInterface(IBinder iBinder) {
      if (iBinder == null) {
        return null;
      }
      IInterface queryLocalInterface =
          iBinder.queryLocalInterface(INetdUnsolicitedEventListener.DESCRIPTOR);
      if (queryLocalInterface != null
          && (queryLocalInterface instanceof INetdUnsolicitedEventListener)) {
        return (INetdUnsolicitedEventListener) queryLocalInterface;
      }
      return new Proxy(iBinder);
    }

    @Override // android.os.Binder
    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
      String str = INetdUnsolicitedEventListener.DESCRIPTOR;
      if (i >= 1 && i <= TRANSACTION_getInterfaceVersion) {
        parcel.enforceInterface(str);
      }
      switch (i) {
        case TRANSACTION_getInterfaceHash /* 16777214 */:
          parcel2.writeNoException();
          parcel2.writeString(getInterfaceHash());
          return true;
        case TRANSACTION_getInterfaceVersion /* 16777215 */:
          parcel2.writeNoException();
          parcel2.writeInt(getInterfaceVersion());
          return true;
        case 1598968902:
          parcel2.writeString(str);
          return true;
        default:
          switch (i) {
            case 1:
              onInterfaceClassActivityChanged(
                  parcel.readBoolean(), parcel.readInt(), parcel.readLong(), parcel.readInt());
              return true;
            case 2:
              onQuotaLimitReached(parcel.readString(), parcel.readString());
              return true;
            case 3:
              onInterfaceDnsServerInfo(
                  parcel.readString(), parcel.readLong(), parcel.createStringArray());
              return true;
            case 4:
              onInterfaceAddressUpdated(
                  parcel.readString(), parcel.readString(), parcel.readInt(), parcel.readInt());
              return true;
            case 5:
              onInterfaceAddressRemoved(
                  parcel.readString(), parcel.readString(), parcel.readInt(), parcel.readInt());
              return true;
            case 6:
              onInterfaceAdded(parcel.readString());
              return true;
            case 7:
              onInterfaceRemoved(parcel.readString());
              return true;
            case 8:
              onInterfaceChanged(parcel.readString(), parcel.readBoolean());
              return true;
            case 9:
              onInterfaceLinkStateChanged(parcel.readString(), parcel.readBoolean());
              return true;
            case 10:
              onRouteChanged(
                  parcel.readBoolean(),
                  parcel.readString(),
                  parcel.readString(),
                  parcel.readString());
              return true;
            case 11:
              onStrictCleartextDetected(parcel.readInt(), parcel.readString());
              return true;
            default:
              return super.onTransact(i, parcel, parcel2, i2);
          }
      }
    }

    public class Proxy implements INetdUnsolicitedEventListener {
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

      @Override // android.net.INetdUnsolicitedEventListener
      public void onInterfaceClassActivityChanged(boolean z, int i, long j, int i2) {
        Parcel obtain = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(INetdUnsolicitedEventListener.DESCRIPTOR);
          obtain.writeBoolean(z);
          obtain.writeInt(i);
          obtain.writeLong(j);
          obtain.writeInt(i2);
          if (this.mRemote.transact(1, obtain, null, 1)) {
          } else {
            throw new RemoteException("Method onInterfaceClassActivityChanged is unimplemented.");
          }
        } finally {
          obtain.recycle();
        }
      }

      @Override // android.net.INetdUnsolicitedEventListener
      public void onQuotaLimitReached(String str, String str2) {
        Parcel obtain = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(INetdUnsolicitedEventListener.DESCRIPTOR);
          obtain.writeString(str);
          obtain.writeString(str2);
          if (this.mRemote.transact(2, obtain, null, 1)) {
          } else {
            throw new RemoteException("Method onQuotaLimitReached is unimplemented.");
          }
        } finally {
          obtain.recycle();
        }
      }

      @Override // android.net.INetdUnsolicitedEventListener
      public void onInterfaceDnsServerInfo(String str, long j, String[] strArr) {
        Parcel obtain = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(INetdUnsolicitedEventListener.DESCRIPTOR);
          obtain.writeString(str);
          obtain.writeLong(j);
          obtain.writeStringArray(strArr);
          if (this.mRemote.transact(3, obtain, null, 1)) {
          } else {
            throw new RemoteException("Method onInterfaceDnsServerInfo is unimplemented.");
          }
        } finally {
          obtain.recycle();
        }
      }

      @Override // android.net.INetdUnsolicitedEventListener
      public void onInterfaceAddressUpdated(String str, String str2, int i, int i2) {
        Parcel obtain = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(INetdUnsolicitedEventListener.DESCRIPTOR);
          obtain.writeString(str);
          obtain.writeString(str2);
          obtain.writeInt(i);
          obtain.writeInt(i2);
          if (this.mRemote.transact(4, obtain, null, 1)) {
          } else {
            throw new RemoteException("Method onInterfaceAddressUpdated is unimplemented.");
          }
        } finally {
          obtain.recycle();
        }
      }

      @Override // android.net.INetdUnsolicitedEventListener
      public void onInterfaceAddressRemoved(String str, String str2, int i, int i2) {
        Parcel obtain = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(INetdUnsolicitedEventListener.DESCRIPTOR);
          obtain.writeString(str);
          obtain.writeString(str2);
          obtain.writeInt(i);
          obtain.writeInt(i2);
          if (this.mRemote.transact(5, obtain, null, 1)) {
          } else {
            throw new RemoteException("Method onInterfaceAddressRemoved is unimplemented.");
          }
        } finally {
          obtain.recycle();
        }
      }

      @Override // android.net.INetdUnsolicitedEventListener
      public void onInterfaceAdded(String str) {
        Parcel obtain = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(INetdUnsolicitedEventListener.DESCRIPTOR);
          obtain.writeString(str);
          if (this.mRemote.transact(6, obtain, null, 1)) {
          } else {
            throw new RemoteException("Method onInterfaceAdded is unimplemented.");
          }
        } finally {
          obtain.recycle();
        }
      }

      @Override // android.net.INetdUnsolicitedEventListener
      public void onInterfaceRemoved(String str) {
        Parcel obtain = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(INetdUnsolicitedEventListener.DESCRIPTOR);
          obtain.writeString(str);
          if (this.mRemote.transact(7, obtain, null, 1)) {
          } else {
            throw new RemoteException("Method onInterfaceRemoved is unimplemented.");
          }
        } finally {
          obtain.recycle();
        }
      }

      @Override // android.net.INetdUnsolicitedEventListener
      public void onInterfaceChanged(String str, boolean z) {
        Parcel obtain = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(INetdUnsolicitedEventListener.DESCRIPTOR);
          obtain.writeString(str);
          obtain.writeBoolean(z);
          if (this.mRemote.transact(8, obtain, null, 1)) {
          } else {
            throw new RemoteException("Method onInterfaceChanged is unimplemented.");
          }
        } finally {
          obtain.recycle();
        }
      }

      @Override // android.net.INetdUnsolicitedEventListener
      public void onInterfaceLinkStateChanged(String str, boolean z) {
        Parcel obtain = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(INetdUnsolicitedEventListener.DESCRIPTOR);
          obtain.writeString(str);
          obtain.writeBoolean(z);
          if (this.mRemote.transact(9, obtain, null, 1)) {
          } else {
            throw new RemoteException("Method onInterfaceLinkStateChanged is unimplemented.");
          }
        } finally {
          obtain.recycle();
        }
      }

      @Override // android.net.INetdUnsolicitedEventListener
      public void onRouteChanged(boolean z, String str, String str2, String str3) {
        Parcel obtain = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(INetdUnsolicitedEventListener.DESCRIPTOR);
          obtain.writeBoolean(z);
          obtain.writeString(str);
          obtain.writeString(str2);
          obtain.writeString(str3);
          if (this.mRemote.transact(10, obtain, null, 1)) {
          } else {
            throw new RemoteException("Method onRouteChanged is unimplemented.");
          }
        } finally {
          obtain.recycle();
        }
      }

      @Override // android.net.INetdUnsolicitedEventListener
      public void onStrictCleartextDetected(int i, String str) {
        Parcel obtain = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(INetdUnsolicitedEventListener.DESCRIPTOR);
          obtain.writeInt(i);
          obtain.writeString(str);
          if (this.mRemote.transact(11, obtain, null, 1)) {
          } else {
            throw new RemoteException("Method onStrictCleartextDetected is unimplemented.");
          }
        } finally {
          obtain.recycle();
        }
      }

      @Override // android.net.INetdUnsolicitedEventListener
      public int getInterfaceVersion() {
        if (this.mCachedVersion == -1) {
          Parcel obtain = Parcel.obtain();
          Parcel obtain2 = Parcel.obtain();
          try {
            obtain.writeInterfaceToken(INetdUnsolicitedEventListener.DESCRIPTOR);
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

      @Override // android.net.INetdUnsolicitedEventListener
      public synchronized String getInterfaceHash() {
        if ("-1".equals(this.mCachedHash)) {
          Parcel obtain = Parcel.obtain();
          Parcel obtain2 = Parcel.obtain();
          try {
            obtain.writeInterfaceToken(INetdUnsolicitedEventListener.DESCRIPTOR);
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
