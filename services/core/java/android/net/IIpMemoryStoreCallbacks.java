package android.net;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IIpMemoryStoreCallbacks extends IInterface {
  public static final String DESCRIPTOR = "android$net$IIpMemoryStoreCallbacks".replace('$', '.');
  public static final String HASH = "d5ea5eb3ddbdaa9a986ce6ba70b0804ca3e39b0c";
  public static final int VERSION = 10;

  public class Default implements IIpMemoryStoreCallbacks {
    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }

    @Override // android.net.IIpMemoryStoreCallbacks
    public String getInterfaceHash() {
      return "";
    }

    @Override // android.net.IIpMemoryStoreCallbacks
    public int getInterfaceVersion() {
      return 0;
    }

    @Override // android.net.IIpMemoryStoreCallbacks
    public void onIpMemoryStoreFetched(IIpMemoryStore iIpMemoryStore) {}
  }

  String getInterfaceHash();

  int getInterfaceVersion();

  void onIpMemoryStoreFetched(IIpMemoryStore iIpMemoryStore);

  public abstract class Stub extends Binder implements IIpMemoryStoreCallbacks {
    static final int TRANSACTION_getInterfaceHash = 16777214;
    static final int TRANSACTION_getInterfaceVersion = 16777215;
    static final int TRANSACTION_onIpMemoryStoreFetched = 1;

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return this;
    }

    public Stub() {
      attachInterface(this, IIpMemoryStoreCallbacks.DESCRIPTOR);
    }

    public static IIpMemoryStoreCallbacks asInterface(IBinder iBinder) {
      if (iBinder == null) {
        return null;
      }
      IInterface queryLocalInterface =
          iBinder.queryLocalInterface(IIpMemoryStoreCallbacks.DESCRIPTOR);
      if (queryLocalInterface != null && (queryLocalInterface instanceof IIpMemoryStoreCallbacks)) {
        return (IIpMemoryStoreCallbacks) queryLocalInterface;
      }
      return new Proxy(iBinder);
    }

    @Override // android.os.Binder
    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
      String str = IIpMemoryStoreCallbacks.DESCRIPTOR;
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
          if (i == 1) {
            onIpMemoryStoreFetched(IIpMemoryStore.Stub.asInterface(parcel.readStrongBinder()));
            return true;
          }
          return super.onTransact(i, parcel, parcel2, i2);
      }
    }

    public class Proxy implements IIpMemoryStoreCallbacks {
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

      @Override // android.net.IIpMemoryStoreCallbacks
      public void onIpMemoryStoreFetched(IIpMemoryStore iIpMemoryStore) {
        Parcel obtain = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(IIpMemoryStoreCallbacks.DESCRIPTOR);
          obtain.writeStrongInterface(iIpMemoryStore);
          if (this.mRemote.transact(1, obtain, null, 1)) {
          } else {
            throw new RemoteException("Method onIpMemoryStoreFetched is unimplemented.");
          }
        } finally {
          obtain.recycle();
        }
      }

      @Override // android.net.IIpMemoryStoreCallbacks
      public int getInterfaceVersion() {
        if (this.mCachedVersion == -1) {
          Parcel obtain = Parcel.obtain();
          Parcel obtain2 = Parcel.obtain();
          try {
            obtain.writeInterfaceToken(IIpMemoryStoreCallbacks.DESCRIPTOR);
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

      @Override // android.net.IIpMemoryStoreCallbacks
      public synchronized String getInterfaceHash() {
        if ("-1".equals(this.mCachedHash)) {
          Parcel obtain = Parcel.obtain();
          Parcel obtain2 = Parcel.obtain();
          try {
            obtain.writeInterfaceToken(IIpMemoryStoreCallbacks.DESCRIPTOR);
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
