package com.android.internal.app;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;
import android.p009os.SemSimpleNetworkStats;
import java.util.List;

/* loaded from: classes4.dex */
public interface IBatteryStatsCallback extends IInterface {
  public static final String DESCRIPTOR = "com.android.internal.app.IBatteryStatsCallback";

  void notifyNetworkStatsUpdated(List<SemSimpleNetworkStats> list) throws RemoteException;

  public static class Default implements IBatteryStatsCallback {
    @Override // com.android.internal.app.IBatteryStatsCallback
    public void notifyNetworkStatsUpdated(List<SemSimpleNetworkStats> stats)
        throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IBatteryStatsCallback {
    static final int TRANSACTION_notifyNetworkStatsUpdated = 1;

    public Stub() {
      attachInterface(this, IBatteryStatsCallback.DESCRIPTOR);
    }

    public static IBatteryStatsCallback asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IBatteryStatsCallback.DESCRIPTOR);
      if (iin != null && (iin instanceof IBatteryStatsCallback)) {
        return (IBatteryStatsCallback) iin;
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
          return "notifyNetworkStatsUpdated";
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
        data.enforceInterface(IBatteryStatsCallback.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IBatteryStatsCallback.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              List<SemSimpleNetworkStats> _arg0 =
                  data.createTypedArrayList(SemSimpleNetworkStats.CREATOR);
              data.enforceNoDataAvail();
              notifyNetworkStatsUpdated(_arg0);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IBatteryStatsCallback {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IBatteryStatsCallback.DESCRIPTOR;
      }

      @Override // com.android.internal.app.IBatteryStatsCallback
      public void notifyNetworkStatsUpdated(List<SemSimpleNetworkStats> stats)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IBatteryStatsCallback.DESCRIPTOR);
          _data.writeTypedList(stats, 0);
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
