package android.p010se.omapi;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes3.dex */
public interface ISecureElementListener extends IInterface {
  public static final String HASH = "894069bcfe4f35ceb2088278ddf87c83adee8014";
  public static final int VERSION = 1;

  String getInterfaceHash() throws RemoteException;

  int getInterfaceVersion() throws RemoteException;

  public static class Default implements ISecureElementListener {
    @Override // android.p010se.omapi.ISecureElementListener
    public int getInterfaceVersion() {
      return 0;
    }

    @Override // android.p010se.omapi.ISecureElementListener
    public String getInterfaceHash() {
      return "";
    }

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements ISecureElementListener {
    public static final String DESCRIPTOR =
        "android$se$omapi$ISecureElementListener".replace('$', '.');
    static final int TRANSACTION_getInterfaceHash = 16777214;
    static final int TRANSACTION_getInterfaceVersion = 16777215;

    public Stub() {
      markVintfStability();
      attachInterface(this, DESCRIPTOR);
    }

    public static ISecureElementListener asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (iin != null && (iin instanceof ISecureElementListener)) {
        return (ISecureElementListener) iin;
      }
      return new Proxy(obj);
    }

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return this;
    }

    @Override // android.p009os.Binder
    public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
        throws RemoteException {
      String descriptor = DESCRIPTOR;
      switch (code) {
        case 16777214:
          reply.writeNoException();
          reply.writeString(getInterfaceHash());
          break;
        case 16777215:
          reply.writeNoException();
          reply.writeInt(getInterfaceVersion());
          break;
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(descriptor);
          break;
      }
      return true;
    }

    private static class Proxy implements ISecureElementListener {
      private IBinder mRemote;
      private int mCachedVersion = -1;
      private String mCachedHash = "-1";

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return Stub.DESCRIPTOR;
      }

      @Override // android.p010se.omapi.ISecureElementListener
      public int getInterfaceVersion() throws RemoteException {
        if (this.mCachedVersion == -1) {
          Parcel data = Parcel.obtain(asBinder());
          Parcel reply = Parcel.obtain();
          try {
            data.writeInterfaceToken(Stub.DESCRIPTOR);
            this.mRemote.transact(16777215, data, reply, 0);
            reply.readException();
            this.mCachedVersion = reply.readInt();
          } finally {
            reply.recycle();
            data.recycle();
          }
        }
        return this.mCachedVersion;
      }

      @Override // android.p010se.omapi.ISecureElementListener
      public synchronized String getInterfaceHash() throws RemoteException {
        if ("-1".equals(this.mCachedHash)) {
          Parcel data = Parcel.obtain(asBinder());
          Parcel reply = Parcel.obtain();
          try {
            data.writeInterfaceToken(Stub.DESCRIPTOR);
            this.mRemote.transact(16777214, data, reply, 0);
            reply.readException();
            this.mCachedHash = reply.readString();
            reply.recycle();
            data.recycle();
          } catch (Throwable th) {
            reply.recycle();
            data.recycle();
            throw th;
          }
        }
        return this.mCachedHash;
      }
    }
  }
}
