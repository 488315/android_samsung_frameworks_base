package com.samsung.android.wifi.p046ai;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes6.dex */
public interface ISemWifiAiService extends IInterface {
  public static final String DESCRIPTOR = "com.samsung.android.wifi.ai.ISemWifiAiService";

  void serviceTypeQuery(float[][] fArr, String[] strArr, int[] iArr, int i) throws RemoteException;

  void toggleDebugMode(boolean z) throws RemoteException;

  public static class Default implements ISemWifiAiService {
    @Override // com.samsung.android.wifi.p046ai.ISemWifiAiService
    public void serviceTypeQuery(
        float[][] trfDataArr, String[] convArr, int[] timeStepArr, int convCnt)
        throws RemoteException {}

    @Override // com.samsung.android.wifi.p046ai.ISemWifiAiService
    public void toggleDebugMode(boolean debug) throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements ISemWifiAiService {
    static final int TRANSACTION_serviceTypeQuery = 1;
    static final int TRANSACTION_toggleDebugMode = 2;

    public Stub() {
      attachInterface(this, ISemWifiAiService.DESCRIPTOR);
    }

    public static ISemWifiAiService asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(ISemWifiAiService.DESCRIPTOR);
      if (iin != null && (iin instanceof ISemWifiAiService)) {
        return (ISemWifiAiService) iin;
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
          return "serviceTypeQuery";
        case 2:
          return "toggleDebugMode";
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
        data.enforceInterface(ISemWifiAiService.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(ISemWifiAiService.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              float[][] _arg0 = (float[][]) data.createFixedArray(float[][].class, 7, 60);
              String[] _arg1 = data.createStringArray();
              int[] _arg2 = data.createIntArray();
              int _arg3 = data.readInt();
              data.enforceNoDataAvail();
              serviceTypeQuery(_arg0, _arg1, _arg2, _arg3);
              return true;
            case 2:
              boolean _arg02 = data.readBoolean();
              data.enforceNoDataAvail();
              toggleDebugMode(_arg02);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements ISemWifiAiService {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return ISemWifiAiService.DESCRIPTOR;
      }

      @Override // com.samsung.android.wifi.p046ai.ISemWifiAiService
      public void serviceTypeQuery(
          float[][] trfDataArr, String[] convArr, int[] timeStepArr, int convCnt)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ISemWifiAiService.DESCRIPTOR);
          _data.writeFixedArray(trfDataArr, 0, 7, 60);
          _data.writeStringArray(convArr);
          _data.writeIntArray(timeStepArr);
          _data.writeInt(convCnt);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // com.samsung.android.wifi.p046ai.ISemWifiAiService
      public void toggleDebugMode(boolean debug) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ISemWifiAiService.DESCRIPTOR);
          _data.writeBoolean(debug);
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
