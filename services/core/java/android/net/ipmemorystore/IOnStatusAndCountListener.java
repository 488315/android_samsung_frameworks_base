package android.net.ipmemorystore;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IOnStatusAndCountListener extends IInterface {
  public static final String DESCRIPTOR =
      "android$net$ipmemorystore$IOnStatusAndCountListener".replace('$', '.');
  public static final String HASH = "d5ea5eb3ddbdaa9a986ce6ba70b0804ca3e39b0c";
  public static final int VERSION = 10;

  public class Default implements IOnStatusAndCountListener {
    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }

    @Override // android.net.ipmemorystore.IOnStatusAndCountListener
    public String getInterfaceHash() {
      return "";
    }

    @Override // android.net.ipmemorystore.IOnStatusAndCountListener
    public int getInterfaceVersion() {
      return 0;
    }

    @Override // android.net.ipmemorystore.IOnStatusAndCountListener
    public void onComplete(StatusParcelable statusParcelable, int i) {}
  }

  String getInterfaceHash();

  int getInterfaceVersion();

  void onComplete(StatusParcelable statusParcelable, int i);

  public abstract class Stub extends Binder implements IOnStatusAndCountListener {
    static final int TRANSACTION_getInterfaceHash = 16777214;
    static final int TRANSACTION_getInterfaceVersion = 16777215;
    static final int TRANSACTION_onComplete = 1;

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return this;
    }

    public Stub() {
      attachInterface(this, IOnStatusAndCountListener.DESCRIPTOR);
    }

    public static IOnStatusAndCountListener asInterface(IBinder iBinder) {
      if (iBinder == null) {
        return null;
      }
      IInterface queryLocalInterface =
          iBinder.queryLocalInterface(IOnStatusAndCountListener.DESCRIPTOR);
      if (queryLocalInterface != null
          && (queryLocalInterface instanceof IOnStatusAndCountListener)) {
        return (IOnStatusAndCountListener) queryLocalInterface;
      }
      return new Proxy(iBinder);
    }

    @Override // android.os.Binder
    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
      String str = IOnStatusAndCountListener.DESCRIPTOR;
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
            onComplete(
                (StatusParcelable) parcel.readTypedObject(StatusParcelable.CREATOR),
                parcel.readInt());
            return true;
          }
          return super.onTransact(i, parcel, parcel2, i2);
      }
    }

    public class Proxy implements IOnStatusAndCountListener {
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

      @Override // android.net.ipmemorystore.IOnStatusAndCountListener
      public void onComplete(StatusParcelable statusParcelable, int i) {
        Parcel obtain = Parcel.obtain();
        try {
          obtain.writeInterfaceToken(IOnStatusAndCountListener.DESCRIPTOR);
          obtain.writeTypedObject(statusParcelable, 0);
          obtain.writeInt(i);
          if (this.mRemote.transact(1, obtain, null, 1)) {
          } else {
            throw new RemoteException("Method onComplete is unimplemented.");
          }
        } finally {
          obtain.recycle();
        }
      }

      @Override // android.net.ipmemorystore.IOnStatusAndCountListener
      public int getInterfaceVersion() {
        if (this.mCachedVersion == -1) {
          Parcel obtain = Parcel.obtain();
          Parcel obtain2 = Parcel.obtain();
          try {
            obtain.writeInterfaceToken(IOnStatusAndCountListener.DESCRIPTOR);
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

      @Override // android.net.ipmemorystore.IOnStatusAndCountListener
      public synchronized String getInterfaceHash() {
        if ("-1".equals(this.mCachedHash)) {
          Parcel obtain = Parcel.obtain();
          Parcel obtain2 = Parcel.obtain();
          try {
            obtain.writeInterfaceToken(IOnStatusAndCountListener.DESCRIPTOR);
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
