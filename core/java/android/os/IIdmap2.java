package android.os;

import java.util.List;

/* loaded from: classes3.dex */
public interface IIdmap2 extends IInterface {
  public static final String DESCRIPTOR = "android.os.IIdmap2";

  int acquireFabricatedOverlayIterator() throws RemoteException;

  FabricatedOverlayInfo createFabricatedOverlay(FabricatedOverlayInternal fabricatedOverlayInternal)
      throws RemoteException;

  String createIdmap(String str, String str2, String str3, int i, boolean z, int i2)
      throws RemoteException;

  boolean deleteFabricatedOverlay(String str) throws RemoteException;

  String dumpIdmap(String str) throws RemoteException;

  String getIdmapPath(String str, int i) throws RemoteException;

  String getTargetPath(String str) throws RemoteException;

  List<FabricatedOverlayInfo> nextFabricatedOverlayInfos(int i) throws RemoteException;

  void releaseFabricatedOverlayIterator(int i) throws RemoteException;

  boolean removeIdmap(String str, int i) throws RemoteException;

  boolean verifyIdmap(String str, String str2, String str3, int i, boolean z, int i2)
      throws RemoteException;

  public static class Default implements IIdmap2 {
    @Override // android.os.IIdmap2
    public String getIdmapPath(String overlayApkPath, int userId) throws RemoteException {
      return null;
    }

    @Override // android.os.IIdmap2
    public boolean removeIdmap(String overlayApkPath, int userId) throws RemoteException {
      return false;
    }

    @Override // android.os.IIdmap2
    public boolean verifyIdmap(
        String targetApkPath,
        String overlayApkPath,
        String overlayName,
        int fulfilledPolicies,
        boolean enforceOverlayable,
        int userId)
        throws RemoteException {
      return false;
    }

    @Override // android.os.IIdmap2
    public String createIdmap(
        String targetApkPath,
        String overlayApkPath,
        String overlayName,
        int fulfilledPolicies,
        boolean enforceOverlayable,
        int userId)
        throws RemoteException {
      return null;
    }

    @Override // android.os.IIdmap2
    public String getTargetPath(String overlayApkPath) throws RemoteException {
      return null;
    }

    @Override // android.os.IIdmap2
    public FabricatedOverlayInfo createFabricatedOverlay(FabricatedOverlayInternal overlay)
        throws RemoteException {
      return null;
    }

    @Override // android.os.IIdmap2
    public boolean deleteFabricatedOverlay(String path) throws RemoteException {
      return false;
    }

    @Override // android.os.IIdmap2
    public int acquireFabricatedOverlayIterator() throws RemoteException {
      return 0;
    }

    @Override // android.os.IIdmap2
    public void releaseFabricatedOverlayIterator(int iteratorId) throws RemoteException {}

    @Override // android.os.IIdmap2
    public List<FabricatedOverlayInfo> nextFabricatedOverlayInfos(int iteratorId)
        throws RemoteException {
      return null;
    }

    @Override // android.os.IIdmap2
    public String dumpIdmap(String overlayApkPath) throws RemoteException {
      return null;
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IIdmap2 {
    static final int TRANSACTION_acquireFabricatedOverlayIterator = 8;
    static final int TRANSACTION_createFabricatedOverlay = 6;
    static final int TRANSACTION_createIdmap = 4;
    static final int TRANSACTION_deleteFabricatedOverlay = 7;
    static final int TRANSACTION_dumpIdmap = 11;
    static final int TRANSACTION_getIdmapPath = 1;
    static final int TRANSACTION_getTargetPath = 5;
    static final int TRANSACTION_nextFabricatedOverlayInfos = 10;
    static final int TRANSACTION_releaseFabricatedOverlayIterator = 9;
    static final int TRANSACTION_removeIdmap = 2;
    static final int TRANSACTION_verifyIdmap = 3;

    public Stub() {
      attachInterface(this, IIdmap2.DESCRIPTOR);
    }

    public static IIdmap2 asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IIdmap2.DESCRIPTOR);
      if (iin != null && (iin instanceof IIdmap2)) {
        return (IIdmap2) iin;
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
          return "getIdmapPath";
        case 2:
          return "removeIdmap";
        case 3:
          return "verifyIdmap";
        case 4:
          return "createIdmap";
        case 5:
          return "getTargetPath";
        case 6:
          return "createFabricatedOverlay";
        case 7:
          return "deleteFabricatedOverlay";
        case 8:
          return "acquireFabricatedOverlayIterator";
        case 9:
          return "releaseFabricatedOverlayIterator";
        case 10:
          return "nextFabricatedOverlayInfos";
        case 11:
          return "dumpIdmap";
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
        data.enforceInterface(IIdmap2.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IIdmap2.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              String _arg0 = data.readString();
              int _arg1 = data.readInt();
              data.enforceNoDataAvail();
              String _result = getIdmapPath(_arg0, _arg1);
              reply.writeNoException();
              reply.writeString(_result);
              return true;
            case 2:
              String _arg02 = data.readString();
              int _arg12 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result2 = removeIdmap(_arg02, _arg12);
              reply.writeNoException();
              reply.writeBoolean(_result2);
              return true;
            case 3:
              String _arg03 = data.readString();
              String _arg13 = data.readString();
              String _arg2 = data.readString();
              int _arg3 = data.readInt();
              boolean _arg4 = data.readBoolean();
              int _arg5 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result3 = verifyIdmap(_arg03, _arg13, _arg2, _arg3, _arg4, _arg5);
              reply.writeNoException();
              reply.writeBoolean(_result3);
              return true;
            case 4:
              String _arg04 = data.readString();
              String _arg14 = data.readString();
              String _arg22 = data.readString();
              int _arg32 = data.readInt();
              boolean _arg42 = data.readBoolean();
              int _arg52 = data.readInt();
              data.enforceNoDataAvail();
              String _result4 = createIdmap(_arg04, _arg14, _arg22, _arg32, _arg42, _arg52);
              reply.writeNoException();
              reply.writeString(_result4);
              return true;
            case 5:
              String _arg05 = data.readString();
              data.enforceNoDataAvail();
              String _result5 = getTargetPath(_arg05);
              reply.writeNoException();
              reply.writeString(_result5);
              return true;
            case 6:
              FabricatedOverlayInternal _arg06 =
                  (FabricatedOverlayInternal)
                      data.readTypedObject(FabricatedOverlayInternal.CREATOR);
              data.enforceNoDataAvail();
              FabricatedOverlayInfo _result6 = createFabricatedOverlay(_arg06);
              reply.writeNoException();
              reply.writeTypedObject(_result6, 1);
              return true;
            case 7:
              String _arg07 = data.readString();
              data.enforceNoDataAvail();
              boolean _result7 = deleteFabricatedOverlay(_arg07);
              reply.writeNoException();
              reply.writeBoolean(_result7);
              return true;
            case 8:
              int _result8 = acquireFabricatedOverlayIterator();
              reply.writeNoException();
              reply.writeInt(_result8);
              return true;
            case 9:
              int _arg08 = data.readInt();
              data.enforceNoDataAvail();
              releaseFabricatedOverlayIterator(_arg08);
              reply.writeNoException();
              return true;
            case 10:
              int _arg09 = data.readInt();
              data.enforceNoDataAvail();
              List<FabricatedOverlayInfo> _result9 = nextFabricatedOverlayInfos(_arg09);
              reply.writeNoException();
              reply.writeTypedList(_result9, 1);
              return true;
            case 11:
              String _arg010 = data.readString();
              data.enforceNoDataAvail();
              String _result10 = dumpIdmap(_arg010);
              reply.writeNoException();
              reply.writeString(_result10);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IIdmap2 {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IIdmap2.DESCRIPTOR;
      }

      @Override // android.os.IIdmap2
      public String getIdmapPath(String overlayApkPath, int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IIdmap2.DESCRIPTOR);
          _data.writeString(overlayApkPath);
          _data.writeInt(userId);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.os.IIdmap2
      public boolean removeIdmap(String overlayApkPath, int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IIdmap2.DESCRIPTOR);
          _data.writeString(overlayApkPath);
          _data.writeInt(userId);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.os.IIdmap2
      public boolean verifyIdmap(
          String targetApkPath,
          String overlayApkPath,
          String overlayName,
          int fulfilledPolicies,
          boolean enforceOverlayable,
          int userId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IIdmap2.DESCRIPTOR);
          _data.writeString(targetApkPath);
          _data.writeString(overlayApkPath);
          _data.writeString(overlayName);
          _data.writeInt(fulfilledPolicies);
          _data.writeBoolean(enforceOverlayable);
          _data.writeInt(userId);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.os.IIdmap2
      public String createIdmap(
          String targetApkPath,
          String overlayApkPath,
          String overlayName,
          int fulfilledPolicies,
          boolean enforceOverlayable,
          int userId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IIdmap2.DESCRIPTOR);
          _data.writeString(targetApkPath);
          _data.writeString(overlayApkPath);
          _data.writeString(overlayName);
          _data.writeInt(fulfilledPolicies);
          _data.writeBoolean(enforceOverlayable);
          _data.writeInt(userId);
          this.mRemote.transact(4, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.os.IIdmap2
      public String getTargetPath(String overlayApkPath) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IIdmap2.DESCRIPTOR);
          _data.writeString(overlayApkPath);
          this.mRemote.transact(5, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.os.IIdmap2
      public FabricatedOverlayInfo createFabricatedOverlay(FabricatedOverlayInternal overlay)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IIdmap2.DESCRIPTOR);
          _data.writeTypedObject(overlay, 0);
          this.mRemote.transact(6, _data, _reply, 0);
          _reply.readException();
          FabricatedOverlayInfo _result =
              (FabricatedOverlayInfo) _reply.readTypedObject(FabricatedOverlayInfo.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.os.IIdmap2
      public boolean deleteFabricatedOverlay(String path) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IIdmap2.DESCRIPTOR);
          _data.writeString(path);
          this.mRemote.transact(7, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.os.IIdmap2
      public int acquireFabricatedOverlayIterator() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IIdmap2.DESCRIPTOR);
          this.mRemote.transact(8, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.os.IIdmap2
      public void releaseFabricatedOverlayIterator(int iteratorId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IIdmap2.DESCRIPTOR);
          _data.writeInt(iteratorId);
          this.mRemote.transact(9, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.os.IIdmap2
      public List<FabricatedOverlayInfo> nextFabricatedOverlayInfos(int iteratorId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IIdmap2.DESCRIPTOR);
          _data.writeInt(iteratorId);
          this.mRemote.transact(10, _data, _reply, 0);
          _reply.readException();
          List<FabricatedOverlayInfo> _result =
              _reply.createTypedArrayList(FabricatedOverlayInfo.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.os.IIdmap2
      public String dumpIdmap(String overlayApkPath) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IIdmap2.DESCRIPTOR);
          _data.writeString(overlayApkPath);
          this.mRemote.transact(11, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    @Override // android.os.Binder
    public int getMaxTransactionId() {
      return 10;
    }
  }
}
