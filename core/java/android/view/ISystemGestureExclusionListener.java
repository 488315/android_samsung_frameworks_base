package android.view;

import android.graphics.Region;
import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes4.dex */
public interface ISystemGestureExclusionListener extends IInterface {
  public static final String DESCRIPTOR = "android.view.ISystemGestureExclusionListener";

  void onSystemGestureExclusionChanged(int i, Region region, Region region2) throws RemoteException;

  public static class Default implements ISystemGestureExclusionListener {
    @Override // android.view.ISystemGestureExclusionListener
    public void onSystemGestureExclusionChanged(
        int displayId, Region systemGestureExclusion, Region systemGestureExclusionUnrestricted)
        throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements ISystemGestureExclusionListener {
    static final int TRANSACTION_onSystemGestureExclusionChanged = 1;

    public Stub() {
      attachInterface(this, ISystemGestureExclusionListener.DESCRIPTOR);
    }

    public static ISystemGestureExclusionListener asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(ISystemGestureExclusionListener.DESCRIPTOR);
      if (iin != null && (iin instanceof ISystemGestureExclusionListener)) {
        return (ISystemGestureExclusionListener) iin;
      }
      return new Proxy(obj);
    }

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return this;
    }

    public static String getDefaultTransactionName(int transactionCode) {
      switch (transactionCode) {
        case 1:
          return "onSystemGestureExclusionChanged";
        default:
          return null;
      }
    }

    @Override // android.p009os.Binder
    public String getTransactionName(int transactionCode) {
      return getDefaultTransactionName(transactionCode);
    }

    @Override // android.p009os.Binder
    public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
        throws RemoteException {
      if (code >= 1 && code <= 16777215) {
        data.enforceInterface(ISystemGestureExclusionListener.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(ISystemGestureExclusionListener.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              int _arg0 = data.readInt();
              Region _arg1 = (Region) data.readTypedObject(Region.CREATOR);
              Region _arg2 = (Region) data.readTypedObject(Region.CREATOR);
              data.enforceNoDataAvail();
              onSystemGestureExclusionChanged(_arg0, _arg1, _arg2);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements ISystemGestureExclusionListener {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return ISystemGestureExclusionListener.DESCRIPTOR;
      }

      @Override // android.view.ISystemGestureExclusionListener
      public void onSystemGestureExclusionChanged(
          int displayId, Region systemGestureExclusion, Region systemGestureExclusionUnrestricted)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ISystemGestureExclusionListener.DESCRIPTOR);
          _data.writeInt(displayId);
          _data.writeTypedObject(systemGestureExclusion, 0);
          _data.writeTypedObject(systemGestureExclusionUnrestricted, 0);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }
    }

    @Override // android.p009os.Binder
    public int getMaxTransactionId() {
      return 0;
    }
  }
}
