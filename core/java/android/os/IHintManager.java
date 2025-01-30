package android.os;


/* loaded from: classes3.dex */
public interface IHintManager extends IInterface {
  public static final String DESCRIPTOR = "android.os.IHintManager";

  IHintSession createHintSession(IBinder iBinder, int[] iArr, long j) throws RemoteException;

  long getHintSessionPreferredRate() throws RemoteException;

  int[] getHintSessionThreadIds(IHintSession iHintSession) throws RemoteException;

  void setHintSessionThreads(IHintSession iHintSession, int[] iArr) throws RemoteException;

  public static class Default implements IHintManager {
    @Override // android.os.IHintManager
    public IHintSession createHintSession(IBinder token, int[] tids, long durationNanos)
        throws RemoteException {
      return null;
    }

    @Override // android.os.IHintManager
    public long getHintSessionPreferredRate() throws RemoteException {
      return 0L;
    }

    @Override // android.os.IHintManager
    public void setHintSessionThreads(IHintSession hintSession, int[] tids)
        throws RemoteException {}

    @Override // android.os.IHintManager
    public int[] getHintSessionThreadIds(IHintSession hintSession) throws RemoteException {
      return null;
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IHintManager {
    static final int TRANSACTION_createHintSession = 1;
    static final int TRANSACTION_getHintSessionPreferredRate = 2;
    static final int TRANSACTION_getHintSessionThreadIds = 4;
    static final int TRANSACTION_setHintSessionThreads = 3;

    public Stub() {
      attachInterface(this, IHintManager.DESCRIPTOR);
    }

    public static IHintManager asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IHintManager.DESCRIPTOR);
      if (iin != null && (iin instanceof IHintManager)) {
        return (IHintManager) iin;
      }
      return new Proxy(obj);
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return this;
    }

    public static String getDefaultTransactionName(int transactionCode) {
      switch (transactionCode) {
        case 1:
          return "createHintSession";
        case 2:
          return "getHintSessionPreferredRate";
        case 3:
          return "setHintSessionThreads";
        case 4:
          return "getHintSessionThreadIds";
        default:
          return null;
      }
    }

    @Override // android.os.Binder
    public String getTransactionName(int transactionCode) {
      return getDefaultTransactionName(transactionCode);
    }

    @Override // android.os.Binder
    public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
        throws RemoteException {
      if (code >= 1 && code <= 16777215) {
        data.enforceInterface(IHintManager.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IHintManager.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              IBinder _arg0 = data.readStrongBinder();
              int[] _arg1 = data.createIntArray();
              long _arg2 = data.readLong();
              data.enforceNoDataAvail();
              IHintSession _result = createHintSession(_arg0, _arg1, _arg2);
              reply.writeNoException();
              reply.writeStrongInterface(_result);
              return true;
            case 2:
              long _result2 = getHintSessionPreferredRate();
              reply.writeNoException();
              reply.writeLong(_result2);
              return true;
            case 3:
              IHintSession _arg02 = IHintSession.Stub.asInterface(data.readStrongBinder());
              int[] _arg12 = data.createIntArray();
              data.enforceNoDataAvail();
              setHintSessionThreads(_arg02, _arg12);
              reply.writeNoException();
              return true;
            case 4:
              IHintSession _arg03 = IHintSession.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              int[] _result3 = getHintSessionThreadIds(_arg03);
              reply.writeNoException();
              reply.writeIntArray(_result3);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IHintManager {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IHintManager.DESCRIPTOR;
      }

      @Override // android.os.IHintManager
      public IHintSession createHintSession(IBinder token, int[] tids, long durationNanos)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IHintManager.DESCRIPTOR);
          _data.writeStrongBinder(token);
          _data.writeIntArray(tids);
          _data.writeLong(durationNanos);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
          IHintSession _result = IHintSession.Stub.asInterface(_reply.readStrongBinder());
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.os.IHintManager
      public long getHintSessionPreferredRate() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IHintManager.DESCRIPTOR);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
          long _result = _reply.readLong();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.os.IHintManager
      public void setHintSessionThreads(IHintSession hintSession, int[] tids)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IHintManager.DESCRIPTOR);
          _data.writeStrongInterface(hintSession);
          _data.writeIntArray(tids);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.os.IHintManager
      public int[] getHintSessionThreadIds(IHintSession hintSession) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IHintManager.DESCRIPTOR);
          _data.writeStrongInterface(hintSession);
          this.mRemote.transact(4, _data, _reply, 0);
          _reply.readException();
          int[] _result = _reply.createIntArray();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    @Override // android.os.Binder
    public int getMaxTransactionId() {
      return 3;
    }
  }
}
