package android.hardware.input;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes2.dex */
public interface ISwitchEventChangedListener extends IInterface {
  public static final String DESCRIPTOR = "android.hardware.input.ISwitchEventChangedListener";

  void onSwitchEventChanged(int i, int i2, int i3, int i4) throws RemoteException;

  public static class Default implements ISwitchEventChangedListener {
    @Override // android.hardware.input.ISwitchEventChangedListener
    public void onSwitchEventChanged(
        int switchValues, int switchMask, int extraValues, int extraMask) throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements ISwitchEventChangedListener {
    static final int TRANSACTION_onSwitchEventChanged = 1;

    public Stub() {
      attachInterface(this, ISwitchEventChangedListener.DESCRIPTOR);
    }

    public static ISwitchEventChangedListener asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(ISwitchEventChangedListener.DESCRIPTOR);
      if (iin != null && (iin instanceof ISwitchEventChangedListener)) {
        return (ISwitchEventChangedListener) iin;
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
          return "onSwitchEventChanged";
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
        data.enforceInterface(ISwitchEventChangedListener.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(ISwitchEventChangedListener.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              int _arg0 = data.readInt();
              int _arg1 = data.readInt();
              int _arg2 = data.readInt();
              int _arg3 = data.readInt();
              data.enforceNoDataAvail();
              onSwitchEventChanged(_arg0, _arg1, _arg2, _arg3);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements ISwitchEventChangedListener {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return ISwitchEventChangedListener.DESCRIPTOR;
      }

      @Override // android.hardware.input.ISwitchEventChangedListener
      public void onSwitchEventChanged(
          int switchValues, int switchMask, int extraValues, int extraMask) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ISwitchEventChangedListener.DESCRIPTOR);
          _data.writeInt(switchValues);
          _data.writeInt(switchMask);
          _data.writeInt(extraValues);
          _data.writeInt(extraMask);
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
