package android.p009os;

/* loaded from: classes3.dex */
public interface ISemHqmManager extends IInterface {
  public static final String DESCRIPTOR = "android.os.ISemHqmManager";

  boolean getCFServerEnable() throws RemoteException;

  boolean getDVServerEnable() throws RemoteException;

  boolean getHqmEnable() throws RemoteException;

  boolean sendHWParamServer(
      int i,
      String str,
      String str2,
      String str3,
      String str4,
      String str5,
      String str6,
      String str7)
      throws RemoteException;

  boolean sendHWParamToHQM(
      int i,
      String str,
      String str2,
      String str3,
      String str4,
      String str5,
      String str6,
      String str7,
      String str8)
      throws RemoteException;

  boolean sendHWParamToHQMwithAppId(
      int i,
      String str,
      String str2,
      String str3,
      String str4,
      String str5,
      String str6,
      String str7,
      String str8,
      String str9)
      throws RemoteException;

  boolean sendHWParamToHQMwithFile(
      int i,
      String str,
      String str2,
      String str3,
      String str4,
      String str5,
      String str6,
      String str7,
      String str8,
      String str9,
      String str10)
      throws RemoteException;

  void sendSystemInfoToHQM(int i, String str, String str2) throws RemoteException;

  public static class Default implements ISemHqmManager {
    @Override // android.p009os.ISemHqmManager
    public boolean sendHWParamServer(
        int type,
        String id,
        String ver,
        String manufacture,
        String hitType,
        String feature,
        String logMaps,
        String envlogMaps)
        throws RemoteException {
      return false;
    }

    @Override // android.p009os.ISemHqmManager
    public boolean sendHWParamToHQM(
        int type,
        String id,
        String feature,
        String hitType,
        String ver,
        String manufacture,
        String dev_custom_dataset,
        String custom_dataset,
        String pri_custom_dataset)
        throws RemoteException {
      return false;
    }

    @Override // android.p009os.ISemHqmManager
    public boolean sendHWParamToHQMwithAppId(
        int type,
        String id,
        String feature,
        String hitType,
        String ver,
        String manufacture,
        String dev_custom_dataset,
        String custom_dataset,
        String pri_custom_dataset,
        String appID)
        throws RemoteException {
      return false;
    }

    @Override // android.p009os.ISemHqmManager
    public boolean sendHWParamToHQMwithFile(
        int type,
        String id,
        String feature,
        String hitType,
        String ver,
        String manufacture,
        String dev_custom_dataset,
        String custom_dataset,
        String pri_custom_dataset,
        String appID,
        String filePath)
        throws RemoteException {
      return false;
    }

    @Override // android.p009os.ISemHqmManager
    public void sendSystemInfoToHQM(int type, String dataset, String sub_dataset)
        throws RemoteException {}

    @Override // android.p009os.ISemHqmManager
    public boolean getHqmEnable() throws RemoteException {
      return false;
    }

    @Override // android.p009os.ISemHqmManager
    public boolean getDVServerEnable() throws RemoteException {
      return false;
    }

    @Override // android.p009os.ISemHqmManager
    public boolean getCFServerEnable() throws RemoteException {
      return false;
    }

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements ISemHqmManager {
    static final int TRANSACTION_getCFServerEnable = 8;
    static final int TRANSACTION_getDVServerEnable = 7;
    static final int TRANSACTION_getHqmEnable = 6;
    static final int TRANSACTION_sendHWParamServer = 1;
    static final int TRANSACTION_sendHWParamToHQM = 2;
    static final int TRANSACTION_sendHWParamToHQMwithAppId = 3;
    static final int TRANSACTION_sendHWParamToHQMwithFile = 4;
    static final int TRANSACTION_sendSystemInfoToHQM = 5;

    public Stub() {
      attachInterface(this, ISemHqmManager.DESCRIPTOR);
    }

    public static ISemHqmManager asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(ISemHqmManager.DESCRIPTOR);
      if (iin != null && (iin instanceof ISemHqmManager)) {
        return (ISemHqmManager) iin;
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
          return "sendHWParamServer";
        case 2:
          return "sendHWParamToHQM";
        case 3:
          return "sendHWParamToHQMwithAppId";
        case 4:
          return "sendHWParamToHQMwithFile";
        case 5:
          return "sendSystemInfoToHQM";
        case 6:
          return "getHqmEnable";
        case 7:
          return "getDVServerEnable";
        case 8:
          return "getCFServerEnable";
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
        data.enforceInterface(ISemHqmManager.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(ISemHqmManager.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              int _arg0 = data.readInt();
              String _arg1 = data.readString();
              String _arg2 = data.readString();
              String _arg3 = data.readString();
              String _arg4 = data.readString();
              String _arg5 = data.readString();
              String _arg6 = data.readString();
              String _arg7 = data.readString();
              data.enforceNoDataAvail();
              boolean _result =
                  sendHWParamServer(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5, _arg6, _arg7);
              reply.writeNoException();
              reply.writeBoolean(_result);
              return true;
            case 2:
              int _arg02 = data.readInt();
              String _arg12 = data.readString();
              String _arg22 = data.readString();
              String _arg32 = data.readString();
              String _arg42 = data.readString();
              String _arg52 = data.readString();
              String _arg62 = data.readString();
              String _arg72 = data.readString();
              String _arg8 = data.readString();
              data.enforceNoDataAvail();
              boolean _result2 =
                  sendHWParamToHQM(
                      _arg02, _arg12, _arg22, _arg32, _arg42, _arg52, _arg62, _arg72, _arg8);
              reply.writeNoException();
              reply.writeBoolean(_result2);
              return true;
            case 3:
              int _arg03 = data.readInt();
              String _arg13 = data.readString();
              String _arg23 = data.readString();
              String _arg33 = data.readString();
              String _arg43 = data.readString();
              String _arg53 = data.readString();
              String _arg63 = data.readString();
              String _arg73 = data.readString();
              String _arg82 = data.readString();
              String _arg9 = data.readString();
              data.enforceNoDataAvail();
              boolean _result3 =
                  sendHWParamToHQMwithAppId(
                      _arg03, _arg13, _arg23, _arg33, _arg43, _arg53, _arg63, _arg73, _arg82,
                      _arg9);
              reply.writeNoException();
              reply.writeBoolean(_result3);
              return true;
            case 4:
              int _arg04 = data.readInt();
              String _arg14 = data.readString();
              String _arg24 = data.readString();
              String _arg34 = data.readString();
              String _arg44 = data.readString();
              String _arg54 = data.readString();
              String _arg64 = data.readString();
              String _arg74 = data.readString();
              String _arg83 = data.readString();
              String _arg92 = data.readString();
              String _arg10 = data.readString();
              data.enforceNoDataAvail();
              boolean _result4 =
                  sendHWParamToHQMwithFile(
                      _arg04, _arg14, _arg24, _arg34, _arg44, _arg54, _arg64, _arg74, _arg83,
                      _arg92, _arg10);
              reply.writeNoException();
              reply.writeBoolean(_result4);
              return true;
            case 5:
              int _arg05 = data.readInt();
              String _arg15 = data.readString();
              String _arg25 = data.readString();
              data.enforceNoDataAvail();
              sendSystemInfoToHQM(_arg05, _arg15, _arg25);
              reply.writeNoException();
              return true;
            case 6:
              boolean _result5 = getHqmEnable();
              reply.writeNoException();
              reply.writeBoolean(_result5);
              return true;
            case 7:
              boolean _result6 = getDVServerEnable();
              reply.writeNoException();
              reply.writeBoolean(_result6);
              return true;
            case 8:
              boolean _result7 = getCFServerEnable();
              reply.writeNoException();
              reply.writeBoolean(_result7);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements ISemHqmManager {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return ISemHqmManager.DESCRIPTOR;
      }

      @Override // android.p009os.ISemHqmManager
      public boolean sendHWParamServer(
          int type,
          String id,
          String ver,
          String manufacture,
          String hitType,
          String feature,
          String logMaps,
          String envlogMaps)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISemHqmManager.DESCRIPTOR);
          _data.writeInt(type);
          _data.writeString(id);
          _data.writeString(ver);
          _data.writeString(manufacture);
          _data.writeString(hitType);
          _data.writeString(feature);
          _data.writeString(logMaps);
          _data.writeString(envlogMaps);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.ISemHqmManager
      public boolean sendHWParamToHQM(
          int type,
          String id,
          String feature,
          String hitType,
          String ver,
          String manufacture,
          String dev_custom_dataset,
          String custom_dataset,
          String pri_custom_dataset)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISemHqmManager.DESCRIPTOR);
          _data.writeInt(type);
          _data.writeString(id);
          _data.writeString(feature);
          _data.writeString(hitType);
          _data.writeString(ver);
          _data.writeString(manufacture);
          _data.writeString(dev_custom_dataset);
          _data.writeString(custom_dataset);
          _data.writeString(pri_custom_dataset);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.ISemHqmManager
      public boolean sendHWParamToHQMwithAppId(
          int type,
          String id,
          String feature,
          String hitType,
          String ver,
          String manufacture,
          String dev_custom_dataset,
          String custom_dataset,
          String pri_custom_dataset,
          String appID)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISemHqmManager.DESCRIPTOR);
          _data.writeInt(type);
          _data.writeString(id);
          _data.writeString(feature);
          _data.writeString(hitType);
          _data.writeString(ver);
          _data.writeString(manufacture);
          _data.writeString(dev_custom_dataset);
          _data.writeString(custom_dataset);
          _data.writeString(pri_custom_dataset);
          _data.writeString(appID);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.ISemHqmManager
      public boolean sendHWParamToHQMwithFile(
          int type,
          String id,
          String feature,
          String hitType,
          String ver,
          String manufacture,
          String dev_custom_dataset,
          String custom_dataset,
          String pri_custom_dataset,
          String appID,
          String filePath)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISemHqmManager.DESCRIPTOR);
          _data.writeInt(type);
          try {
            _data.writeString(id);
            try {
              _data.writeString(feature);
              try {
                _data.writeString(hitType);
              } catch (Throwable th) {
                th = th;
                _reply.recycle();
                _data.recycle();
                throw th;
              }
            } catch (Throwable th2) {
              th = th2;
              _reply.recycle();
              _data.recycle();
              throw th;
            }
          } catch (Throwable th3) {
            th = th3;
            _reply.recycle();
            _data.recycle();
            throw th;
          }
          try {
            _data.writeString(ver);
            try {
              _data.writeString(manufacture);
              try {
                _data.writeString(dev_custom_dataset);
                try {
                  _data.writeString(custom_dataset);
                } catch (Throwable th4) {
                  th = th4;
                  _reply.recycle();
                  _data.recycle();
                  throw th;
                }
              } catch (Throwable th5) {
                th = th5;
                _reply.recycle();
                _data.recycle();
                throw th;
              }
            } catch (Throwable th6) {
              th = th6;
              _reply.recycle();
              _data.recycle();
              throw th;
            }
            try {
              _data.writeString(pri_custom_dataset);
              try {
                _data.writeString(appID);
                try {
                  _data.writeString(filePath);
                  try {
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                  } catch (Throwable th7) {
                    th = th7;
                    _reply.recycle();
                    _data.recycle();
                    throw th;
                  }
                } catch (Throwable th8) {
                  th = th8;
                }
              } catch (Throwable th9) {
                th = th9;
                _reply.recycle();
                _data.recycle();
                throw th;
              }
            } catch (Throwable th10) {
              th = th10;
              _reply.recycle();
              _data.recycle();
              throw th;
            }
          } catch (Throwable th11) {
            th = th11;
            _reply.recycle();
            _data.recycle();
            throw th;
          }
        } catch (Throwable th12) {
          th = th12;
        }
      }

      @Override // android.p009os.ISemHqmManager
      public void sendSystemInfoToHQM(int type, String dataset, String sub_dataset)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISemHqmManager.DESCRIPTOR);
          _data.writeInt(type);
          _data.writeString(dataset);
          _data.writeString(sub_dataset);
          this.mRemote.transact(5, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.ISemHqmManager
      public boolean getHqmEnable() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISemHqmManager.DESCRIPTOR);
          this.mRemote.transact(6, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.ISemHqmManager
      public boolean getDVServerEnable() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISemHqmManager.DESCRIPTOR);
          this.mRemote.transact(7, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.p009os.ISemHqmManager
      public boolean getCFServerEnable() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISemHqmManager.DESCRIPTOR);
          this.mRemote.transact(8, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    @Override // android.p009os.Binder
    public int getMaxTransactionId() {
      return 7;
    }
  }
}
