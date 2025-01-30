package android.p009os;

/* loaded from: classes3.dex */
public interface IVibratorStateListener extends IInterface {
  public static final String DESCRIPTOR = "android.os.IVibratorStateListener";

  void onVibrating(boolean z) throws RemoteException;

  public static class Default implements IVibratorStateListener {
    @Override // android.p009os.IVibratorStateListener
    public void onVibrating(boolean vibrating) throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IVibratorStateListener {
    static final int TRANSACTION_onVibrating = 1;

    public Stub() {
      attachInterface(this, IVibratorStateListener.DESCRIPTOR);
    }

    public static IVibratorStateListener asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IVibratorStateListener.DESCRIPTOR);
      if (iin != null && (iin instanceof IVibratorStateListener)) {
        return (IVibratorStateListener) iin;
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
          return "onVibrating";
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
        data.enforceInterface(IVibratorStateListener.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IVibratorStateListener.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              boolean _arg0 = data.readBoolean();
              data.enforceNoDataAvail();
              onVibrating(_arg0);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IVibratorStateListener {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IVibratorStateListener.DESCRIPTOR;
      }

      @Override // android.p009os.IVibratorStateListener
      public void onVibrating(boolean vibrating) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IVibratorStateListener.DESCRIPTOR);
          _data.writeBoolean(vibrating);
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
