package com.samsung.android.gamesdk;

import android.hardware.scontext.SContextConstants;
import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes5.dex */
public interface IGameSDKService extends IInterface {
  public static final String DESCRIPTOR = "com.samsung.android.gamesdk.IGameSDKService";

  void finalGameSDK(String str) throws RemoteException;

  int getCPULevelMax() throws RemoteException;

  int getClusterInfo() throws RemoteException;

  int getCpuJTLevel() throws RemoteException;

  int getCpuLevelCurrent() throws RemoteException;

  double getCpuLoad() throws RemoteException;

  int getCurrentRefreshRate() throws RemoteException;

  double getFrameworkFPS() throws RemoteException;

  int getGPULevelMax() throws RemoteException;

  double getGpuFrameTime() throws RemoteException;

  int getGpuJTLevel() throws RemoteException;

  int getGpuLevelCurrent() throws RemoteException;

  double getGpuLoad() throws RemoteException;

  double getHighPrecisionSkinTempLevel() throws RemoteException;

  double getPrevFrameworkFPS() throws RemoteException;

  int getSkinTempLevel() throws RemoteException;

  int[] getSupportedRefreshRates() throws RemoteException;

  int getTempLevel() throws RemoteException;

  String getVersion() throws RemoteException;

  boolean initGameSDK() throws RemoteException;

  boolean initGameSDKWithJsonData(String str, String str2) throws RemoteException;

  boolean initGameSDKWithVersion(String str) throws RemoteException;

  boolean isGameSDKVrrSupported() throws RemoteException;

  int isPMModeEnable() throws RemoteException;

  boolean isPackageInWhiteList(String str) throws RemoteException;

  boolean registerDebugBridgePid(int i) throws RemoteException;

  void resetRefreshRate() throws RemoteException;

  boolean setCpuBoostMode(int i) throws RemoteException;

  boolean setDisableTMLevel(int i) throws RemoteException;

  int setFreqLevels(int i, int i2) throws RemoteException;

  boolean setGameSDKListener(IGameSDKListener iGameSDKListener) throws RemoteException;

  boolean setGameSDKStateListener(IGameSDKStateListener iGameSDKStateListener)
      throws RemoteException;

  boolean setGpuBoostMode(int i) throws RemoteException;

  boolean setLevelWithScene(String str, int i, int i2) throws RemoteException;

  void setRefreshRate(int i) throws RemoteException;

  public static class Default implements IGameSDKService {
    @Override // com.samsung.android.gamesdk.IGameSDKService
    public boolean initGameSDK() throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.gamesdk.IGameSDKService
    public boolean initGameSDKWithVersion(String version) throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.gamesdk.IGameSDKService
    public boolean initGameSDKWithJsonData(String inVersion, String jsonData)
        throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.gamesdk.IGameSDKService
    public String getVersion() throws RemoteException {
      return null;
    }

    @Override // com.samsung.android.gamesdk.IGameSDKService
    public int getTempLevel() throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.gamesdk.IGameSDKService
    public int getSkinTempLevel() throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.gamesdk.IGameSDKService
    public int getCPULevelMax() throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.gamesdk.IGameSDKService
    public int getGPULevelMax() throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.gamesdk.IGameSDKService
    public double getHighPrecisionSkinTempLevel() throws RemoteException {
      return SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    }

    @Override // com.samsung.android.gamesdk.IGameSDKService
    public boolean setLevelWithScene(String scene, int cpuLevel, int gpuLevel)
        throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.gamesdk.IGameSDKService
    public boolean setGameSDKListener(IGameSDKListener listener) throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.gamesdk.IGameSDKService
    public int getCpuJTLevel() throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.gamesdk.IGameSDKService
    public int getGpuJTLevel() throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.gamesdk.IGameSDKService
    public boolean isGameSDKVrrSupported() throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.gamesdk.IGameSDKService
    public int[] getSupportedRefreshRates() throws RemoteException {
      return null;
    }

    @Override // com.samsung.android.gamesdk.IGameSDKService
    public void setRefreshRate(int targetRefreshRate) throws RemoteException {}

    @Override // com.samsung.android.gamesdk.IGameSDKService
    public void resetRefreshRate() throws RemoteException {}

    @Override // com.samsung.android.gamesdk.IGameSDKService
    public int getCurrentRefreshRate() throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.gamesdk.IGameSDKService
    public double getFrameworkFPS() throws RemoteException {
      return SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    }

    @Override // com.samsung.android.gamesdk.IGameSDKService
    public double getPrevFrameworkFPS() throws RemoteException {
      return SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    }

    @Override // com.samsung.android.gamesdk.IGameSDKService
    public double getCpuLoad() throws RemoteException {
      return SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    }

    @Override // com.samsung.android.gamesdk.IGameSDKService
    public double getGpuLoad() throws RemoteException {
      return SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    }

    @Override // com.samsung.android.gamesdk.IGameSDKService
    public int getCpuLevelCurrent() throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.gamesdk.IGameSDKService
    public int getGpuLevelCurrent() throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.gamesdk.IGameSDKService
    public int isPMModeEnable() throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.gamesdk.IGameSDKService
    public double getGpuFrameTime() throws RemoteException {
      return SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
    }

    @Override // com.samsung.android.gamesdk.IGameSDKService
    public boolean setDisableTMLevel(int stepLevel) throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.gamesdk.IGameSDKService
    public int setFreqLevels(int cpuLevel, int gpuLevel) throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.gamesdk.IGameSDKService
    public void finalGameSDK(String packagename) throws RemoteException {}

    @Override // com.samsung.android.gamesdk.IGameSDKService
    public boolean isPackageInWhiteList(String packageName) throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.gamesdk.IGameSDKService
    public boolean setCpuBoostMode(int mode) throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.gamesdk.IGameSDKService
    public boolean setGpuBoostMode(int mode) throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.gamesdk.IGameSDKService
    public int getClusterInfo() throws RemoteException {
      return 0;
    }

    @Override // com.samsung.android.gamesdk.IGameSDKService
    public boolean registerDebugBridgePid(int pid) throws RemoteException {
      return false;
    }

    @Override // com.samsung.android.gamesdk.IGameSDKService
    public boolean setGameSDKStateListener(IGameSDKStateListener listener) throws RemoteException {
      return false;
    }

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IGameSDKService {
    static final int TRANSACTION_finalGameSDK = 29;
    static final int TRANSACTION_getCPULevelMax = 7;
    static final int TRANSACTION_getClusterInfo = 33;
    static final int TRANSACTION_getCpuJTLevel = 12;
    static final int TRANSACTION_getCpuLevelCurrent = 23;
    static final int TRANSACTION_getCpuLoad = 21;
    static final int TRANSACTION_getCurrentRefreshRate = 18;
    static final int TRANSACTION_getFrameworkFPS = 19;
    static final int TRANSACTION_getGPULevelMax = 8;
    static final int TRANSACTION_getGpuFrameTime = 26;
    static final int TRANSACTION_getGpuJTLevel = 13;
    static final int TRANSACTION_getGpuLevelCurrent = 24;
    static final int TRANSACTION_getGpuLoad = 22;
    static final int TRANSACTION_getHighPrecisionSkinTempLevel = 9;
    static final int TRANSACTION_getPrevFrameworkFPS = 20;
    static final int TRANSACTION_getSkinTempLevel = 6;
    static final int TRANSACTION_getSupportedRefreshRates = 15;
    static final int TRANSACTION_getTempLevel = 5;
    static final int TRANSACTION_getVersion = 4;
    static final int TRANSACTION_initGameSDK = 1;
    static final int TRANSACTION_initGameSDKWithJsonData = 3;
    static final int TRANSACTION_initGameSDKWithVersion = 2;
    static final int TRANSACTION_isGameSDKVrrSupported = 14;
    static final int TRANSACTION_isPMModeEnable = 25;
    static final int TRANSACTION_isPackageInWhiteList = 30;
    static final int TRANSACTION_registerDebugBridgePid = 34;
    static final int TRANSACTION_resetRefreshRate = 17;
    static final int TRANSACTION_setCpuBoostMode = 31;
    static final int TRANSACTION_setDisableTMLevel = 27;
    static final int TRANSACTION_setFreqLevels = 28;
    static final int TRANSACTION_setGameSDKListener = 11;
    static final int TRANSACTION_setGameSDKStateListener = 35;
    static final int TRANSACTION_setGpuBoostMode = 32;
    static final int TRANSACTION_setLevelWithScene = 10;
    static final int TRANSACTION_setRefreshRate = 16;

    public Stub() {
      attachInterface(this, IGameSDKService.DESCRIPTOR);
    }

    public static IGameSDKService asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IGameSDKService.DESCRIPTOR);
      if (iin != null && (iin instanceof IGameSDKService)) {
        return (IGameSDKService) iin;
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
          return "initGameSDK";
        case 2:
          return "initGameSDKWithVersion";
        case 3:
          return "initGameSDKWithJsonData";
        case 4:
          return "getVersion";
        case 5:
          return "getTempLevel";
        case 6:
          return "getSkinTempLevel";
        case 7:
          return "getCPULevelMax";
        case 8:
          return "getGPULevelMax";
        case 9:
          return "getHighPrecisionSkinTempLevel";
        case 10:
          return "setLevelWithScene";
        case 11:
          return "setGameSDKListener";
        case 12:
          return "getCpuJTLevel";
        case 13:
          return "getGpuJTLevel";
        case 14:
          return "isGameSDKVrrSupported";
        case 15:
          return "getSupportedRefreshRates";
        case 16:
          return "setRefreshRate";
        case 17:
          return "resetRefreshRate";
        case 18:
          return "getCurrentRefreshRate";
        case 19:
          return "getFrameworkFPS";
        case 20:
          return "getPrevFrameworkFPS";
        case 21:
          return "getCpuLoad";
        case 22:
          return "getGpuLoad";
        case 23:
          return "getCpuLevelCurrent";
        case 24:
          return "getGpuLevelCurrent";
        case 25:
          return "isPMModeEnable";
        case 26:
          return "getGpuFrameTime";
        case 27:
          return "setDisableTMLevel";
        case 28:
          return "setFreqLevels";
        case 29:
          return "finalGameSDK";
        case 30:
          return "isPackageInWhiteList";
        case 31:
          return "setCpuBoostMode";
        case 32:
          return "setGpuBoostMode";
        case 33:
          return "getClusterInfo";
        case 34:
          return "registerDebugBridgePid";
        case 35:
          return "setGameSDKStateListener";
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
        data.enforceInterface(IGameSDKService.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IGameSDKService.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              boolean _result = initGameSDK();
              reply.writeNoException();
              reply.writeBoolean(_result);
              return true;
            case 2:
              String _arg0 = data.readString();
              data.enforceNoDataAvail();
              boolean _result2 = initGameSDKWithVersion(_arg0);
              reply.writeNoException();
              reply.writeBoolean(_result2);
              return true;
            case 3:
              String _arg02 = data.readString();
              String _arg1 = data.readString();
              data.enforceNoDataAvail();
              boolean _result3 = initGameSDKWithJsonData(_arg02, _arg1);
              reply.writeNoException();
              reply.writeBoolean(_result3);
              return true;
            case 4:
              String _result4 = getVersion();
              reply.writeNoException();
              reply.writeString(_result4);
              return true;
            case 5:
              int _result5 = getTempLevel();
              reply.writeNoException();
              reply.writeInt(_result5);
              return true;
            case 6:
              int _result6 = getSkinTempLevel();
              reply.writeNoException();
              reply.writeInt(_result6);
              return true;
            case 7:
              int _result7 = getCPULevelMax();
              reply.writeNoException();
              reply.writeInt(_result7);
              return true;
            case 8:
              int _result8 = getGPULevelMax();
              reply.writeNoException();
              reply.writeInt(_result8);
              return true;
            case 9:
              double _result9 = getHighPrecisionSkinTempLevel();
              reply.writeNoException();
              reply.writeDouble(_result9);
              return true;
            case 10:
              String _arg03 = data.readString();
              int _arg12 = data.readInt();
              int _arg2 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result10 = setLevelWithScene(_arg03, _arg12, _arg2);
              reply.writeNoException();
              reply.writeBoolean(_result10);
              return true;
            case 11:
              IGameSDKListener _arg04 = IGameSDKListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              boolean _result11 = setGameSDKListener(_arg04);
              reply.writeNoException();
              reply.writeBoolean(_result11);
              return true;
            case 12:
              int _result12 = getCpuJTLevel();
              reply.writeNoException();
              reply.writeInt(_result12);
              return true;
            case 13:
              int _result13 = getGpuJTLevel();
              reply.writeNoException();
              reply.writeInt(_result13);
              return true;
            case 14:
              boolean _result14 = isGameSDKVrrSupported();
              reply.writeNoException();
              reply.writeBoolean(_result14);
              return true;
            case 15:
              int[] _result15 = getSupportedRefreshRates();
              reply.writeNoException();
              reply.writeIntArray(_result15);
              return true;
            case 16:
              int _arg05 = data.readInt();
              data.enforceNoDataAvail();
              setRefreshRate(_arg05);
              reply.writeNoException();
              return true;
            case 17:
              resetRefreshRate();
              reply.writeNoException();
              return true;
            case 18:
              int _result16 = getCurrentRefreshRate();
              reply.writeNoException();
              reply.writeInt(_result16);
              return true;
            case 19:
              double _result17 = getFrameworkFPS();
              reply.writeNoException();
              reply.writeDouble(_result17);
              return true;
            case 20:
              double _result18 = getPrevFrameworkFPS();
              reply.writeNoException();
              reply.writeDouble(_result18);
              return true;
            case 21:
              double _result19 = getCpuLoad();
              reply.writeNoException();
              reply.writeDouble(_result19);
              return true;
            case 22:
              double _result20 = getGpuLoad();
              reply.writeNoException();
              reply.writeDouble(_result20);
              return true;
            case 23:
              int _result21 = getCpuLevelCurrent();
              reply.writeNoException();
              reply.writeInt(_result21);
              return true;
            case 24:
              int _result22 = getGpuLevelCurrent();
              reply.writeNoException();
              reply.writeInt(_result22);
              return true;
            case 25:
              int _result23 = isPMModeEnable();
              reply.writeNoException();
              reply.writeInt(_result23);
              return true;
            case 26:
              double _result24 = getGpuFrameTime();
              reply.writeNoException();
              reply.writeDouble(_result24);
              return true;
            case 27:
              int _arg06 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result25 = setDisableTMLevel(_arg06);
              reply.writeNoException();
              reply.writeBoolean(_result25);
              return true;
            case 28:
              int _arg07 = data.readInt();
              int _arg13 = data.readInt();
              data.enforceNoDataAvail();
              int _result26 = setFreqLevels(_arg07, _arg13);
              reply.writeNoException();
              reply.writeInt(_result26);
              return true;
            case 29:
              String _arg08 = data.readString();
              data.enforceNoDataAvail();
              finalGameSDK(_arg08);
              reply.writeNoException();
              return true;
            case 30:
              String _arg09 = data.readString();
              data.enforceNoDataAvail();
              boolean _result27 = isPackageInWhiteList(_arg09);
              reply.writeNoException();
              reply.writeBoolean(_result27);
              return true;
            case 31:
              int _arg010 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result28 = setCpuBoostMode(_arg010);
              reply.writeNoException();
              reply.writeBoolean(_result28);
              return true;
            case 32:
              int _arg011 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result29 = setGpuBoostMode(_arg011);
              reply.writeNoException();
              reply.writeBoolean(_result29);
              return true;
            case 33:
              int _result30 = getClusterInfo();
              reply.writeNoException();
              reply.writeInt(_result30);
              return true;
            case 34:
              int _arg012 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result31 = registerDebugBridgePid(_arg012);
              reply.writeNoException();
              reply.writeBoolean(_result31);
              return true;
            case 35:
              IGameSDKStateListener _arg013 =
                  IGameSDKStateListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              boolean _result32 = setGameSDKStateListener(_arg013);
              reply.writeNoException();
              reply.writeBoolean(_result32);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IGameSDKService {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IGameSDKService.DESCRIPTOR;
      }

      @Override // com.samsung.android.gamesdk.IGameSDKService
      public boolean initGameSDK() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IGameSDKService.DESCRIPTOR);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.gamesdk.IGameSDKService
      public boolean initGameSDKWithVersion(String version) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IGameSDKService.DESCRIPTOR);
          _data.writeString(version);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.gamesdk.IGameSDKService
      public boolean initGameSDKWithJsonData(String inVersion, String jsonData)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IGameSDKService.DESCRIPTOR);
          _data.writeString(inVersion);
          _data.writeString(jsonData);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.gamesdk.IGameSDKService
      public String getVersion() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IGameSDKService.DESCRIPTOR);
          this.mRemote.transact(4, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.gamesdk.IGameSDKService
      public int getTempLevel() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IGameSDKService.DESCRIPTOR);
          this.mRemote.transact(5, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.gamesdk.IGameSDKService
      public int getSkinTempLevel() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IGameSDKService.DESCRIPTOR);
          this.mRemote.transact(6, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.gamesdk.IGameSDKService
      public int getCPULevelMax() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IGameSDKService.DESCRIPTOR);
          this.mRemote.transact(7, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.gamesdk.IGameSDKService
      public int getGPULevelMax() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IGameSDKService.DESCRIPTOR);
          this.mRemote.transact(8, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.gamesdk.IGameSDKService
      public double getHighPrecisionSkinTempLevel() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IGameSDKService.DESCRIPTOR);
          this.mRemote.transact(9, _data, _reply, 0);
          _reply.readException();
          double _result = _reply.readDouble();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.gamesdk.IGameSDKService
      public boolean setLevelWithScene(String scene, int cpuLevel, int gpuLevel)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IGameSDKService.DESCRIPTOR);
          _data.writeString(scene);
          _data.writeInt(cpuLevel);
          _data.writeInt(gpuLevel);
          this.mRemote.transact(10, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.gamesdk.IGameSDKService
      public boolean setGameSDKListener(IGameSDKListener listener) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IGameSDKService.DESCRIPTOR);
          _data.writeStrongInterface(listener);
          this.mRemote.transact(11, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.gamesdk.IGameSDKService
      public int getCpuJTLevel() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IGameSDKService.DESCRIPTOR);
          this.mRemote.transact(12, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.gamesdk.IGameSDKService
      public int getGpuJTLevel() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IGameSDKService.DESCRIPTOR);
          this.mRemote.transact(13, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.gamesdk.IGameSDKService
      public boolean isGameSDKVrrSupported() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IGameSDKService.DESCRIPTOR);
          this.mRemote.transact(14, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.gamesdk.IGameSDKService
      public int[] getSupportedRefreshRates() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IGameSDKService.DESCRIPTOR);
          this.mRemote.transact(15, _data, _reply, 0);
          _reply.readException();
          int[] _result = _reply.createIntArray();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.gamesdk.IGameSDKService
      public void setRefreshRate(int targetRefreshRate) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IGameSDKService.DESCRIPTOR);
          _data.writeInt(targetRefreshRate);
          this.mRemote.transact(16, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.gamesdk.IGameSDKService
      public void resetRefreshRate() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IGameSDKService.DESCRIPTOR);
          this.mRemote.transact(17, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.gamesdk.IGameSDKService
      public int getCurrentRefreshRate() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IGameSDKService.DESCRIPTOR);
          this.mRemote.transact(18, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.gamesdk.IGameSDKService
      public double getFrameworkFPS() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IGameSDKService.DESCRIPTOR);
          this.mRemote.transact(19, _data, _reply, 0);
          _reply.readException();
          double _result = _reply.readDouble();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.gamesdk.IGameSDKService
      public double getPrevFrameworkFPS() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IGameSDKService.DESCRIPTOR);
          this.mRemote.transact(20, _data, _reply, 0);
          _reply.readException();
          double _result = _reply.readDouble();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.gamesdk.IGameSDKService
      public double getCpuLoad() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IGameSDKService.DESCRIPTOR);
          this.mRemote.transact(21, _data, _reply, 0);
          _reply.readException();
          double _result = _reply.readDouble();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.gamesdk.IGameSDKService
      public double getGpuLoad() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IGameSDKService.DESCRIPTOR);
          this.mRemote.transact(22, _data, _reply, 0);
          _reply.readException();
          double _result = _reply.readDouble();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.gamesdk.IGameSDKService
      public int getCpuLevelCurrent() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IGameSDKService.DESCRIPTOR);
          this.mRemote.transact(23, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.gamesdk.IGameSDKService
      public int getGpuLevelCurrent() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IGameSDKService.DESCRIPTOR);
          this.mRemote.transact(24, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.gamesdk.IGameSDKService
      public int isPMModeEnable() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IGameSDKService.DESCRIPTOR);
          this.mRemote.transact(25, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.gamesdk.IGameSDKService
      public double getGpuFrameTime() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IGameSDKService.DESCRIPTOR);
          this.mRemote.transact(26, _data, _reply, 0);
          _reply.readException();
          double _result = _reply.readDouble();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.gamesdk.IGameSDKService
      public boolean setDisableTMLevel(int stepLevel) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IGameSDKService.DESCRIPTOR);
          _data.writeInt(stepLevel);
          this.mRemote.transact(27, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.gamesdk.IGameSDKService
      public int setFreqLevels(int cpuLevel, int gpuLevel) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IGameSDKService.DESCRIPTOR);
          _data.writeInt(cpuLevel);
          _data.writeInt(gpuLevel);
          this.mRemote.transact(28, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.gamesdk.IGameSDKService
      public void finalGameSDK(String packagename) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IGameSDKService.DESCRIPTOR);
          _data.writeString(packagename);
          this.mRemote.transact(29, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.gamesdk.IGameSDKService
      public boolean isPackageInWhiteList(String packageName) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IGameSDKService.DESCRIPTOR);
          _data.writeString(packageName);
          this.mRemote.transact(30, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.gamesdk.IGameSDKService
      public boolean setCpuBoostMode(int mode) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IGameSDKService.DESCRIPTOR);
          _data.writeInt(mode);
          this.mRemote.transact(31, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.gamesdk.IGameSDKService
      public boolean setGpuBoostMode(int mode) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IGameSDKService.DESCRIPTOR);
          _data.writeInt(mode);
          this.mRemote.transact(32, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.gamesdk.IGameSDKService
      public int getClusterInfo() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IGameSDKService.DESCRIPTOR);
          this.mRemote.transact(33, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.gamesdk.IGameSDKService
      public boolean registerDebugBridgePid(int pid) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IGameSDKService.DESCRIPTOR);
          _data.writeInt(pid);
          this.mRemote.transact(34, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.gamesdk.IGameSDKService
      public boolean setGameSDKStateListener(IGameSDKStateListener listener)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(IGameSDKService.DESCRIPTOR);
          _data.writeStrongInterface(listener);
          this.mRemote.transact(35, _data, _reply, 0);
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
      return 34;
    }
  }
}
