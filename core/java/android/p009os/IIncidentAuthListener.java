package android.p009os;

/* loaded from: classes3.dex */
public interface IIncidentAuthListener extends IInterface {
  public static final String DESCRIPTOR = "android.os.IIncidentAuthListener";

  void onReportApproved() throws RemoteException;

  void onReportDenied() throws RemoteException;

  public static class Default implements IIncidentAuthListener {
    @Override // android.p009os.IIncidentAuthListener
    public void onReportApproved() throws RemoteException {}

    @Override // android.p009os.IIncidentAuthListener
    public void onReportDenied() throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IIncidentAuthListener {
    static final int TRANSACTION_onReportApproved = 1;
    static final int TRANSACTION_onReportDenied = 2;

    public Stub() {
      attachInterface(this, IIncidentAuthListener.DESCRIPTOR);
    }

    public static IIncidentAuthListener asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IIncidentAuthListener.DESCRIPTOR);
      if (iin != null && (iin instanceof IIncidentAuthListener)) {
        return (IIncidentAuthListener) iin;
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
          return "onReportApproved";
        case 2:
          return "onReportDenied";
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
        data.enforceInterface(IIncidentAuthListener.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IIncidentAuthListener.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              onReportApproved();
              return true;
            case 2:
              onReportDenied();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IIncidentAuthListener {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IIncidentAuthListener.DESCRIPTOR;
      }

      @Override // android.p009os.IIncidentAuthListener
      public void onReportApproved() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IIncidentAuthListener.DESCRIPTOR);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.p009os.IIncidentAuthListener
      public void onReportDenied() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IIncidentAuthListener.DESCRIPTOR);
          this.mRemote.transact(2, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }
    }

    @Override // android.p009os.Binder
    public int getMaxTransactionId() {
      return 1;
    }
  }
}
