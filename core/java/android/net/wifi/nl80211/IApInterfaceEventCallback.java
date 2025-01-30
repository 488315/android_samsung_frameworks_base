package android.net.wifi.nl80211;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IApInterfaceEventCallback extends IInterface {
  public static final int BANDWIDTH_160 = 6;
  public static final int BANDWIDTH_20 = 2;
  public static final int BANDWIDTH_20_NOHT = 1;
  public static final int BANDWIDTH_320 = 7;
  public static final int BANDWIDTH_40 = 3;
  public static final int BANDWIDTH_80 = 4;
  public static final int BANDWIDTH_80P80 = 5;
  public static final int BANDWIDTH_INVALID = 0;
  public static final String DESCRIPTOR = "android.net.wifi.nl80211.IApInterfaceEventCallback";

  void onConnectedClientsChanged(NativeWifiClient nativeWifiClient, boolean z)
      throws RemoteException;

  void onSoftApChannelSwitched(int i, int i2) throws RemoteException;

  public static class Default implements IApInterfaceEventCallback {
    @Override // android.net.wifi.nl80211.IApInterfaceEventCallback
    public void onConnectedClientsChanged(NativeWifiClient client, boolean isConnected)
        throws RemoteException {}

    @Override // android.net.wifi.nl80211.IApInterfaceEventCallback
    public void onSoftApChannelSwitched(int frequency, int bandwidth) throws RemoteException {}

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IApInterfaceEventCallback {
    static final int TRANSACTION_onConnectedClientsChanged = 1;
    static final int TRANSACTION_onSoftApChannelSwitched = 2;

    public Stub() {
      attachInterface(this, IApInterfaceEventCallback.DESCRIPTOR);
    }

    public static IApInterfaceEventCallback asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IApInterfaceEventCallback.DESCRIPTOR);
      if (iin != null && (iin instanceof IApInterfaceEventCallback)) {
        return (IApInterfaceEventCallback) iin;
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
          return "onConnectedClientsChanged";
        case 2:
          return "onSoftApChannelSwitched";
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
        data.enforceInterface(IApInterfaceEventCallback.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IApInterfaceEventCallback.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              NativeWifiClient _arg0 =
                  (NativeWifiClient) data.readTypedObject(NativeWifiClient.CREATOR);
              boolean _arg1 = data.readBoolean();
              data.enforceNoDataAvail();
              onConnectedClientsChanged(_arg0, _arg1);
              return true;
            case 2:
              int _arg02 = data.readInt();
              int _arg12 = data.readInt();
              data.enforceNoDataAvail();
              onSoftApChannelSwitched(_arg02, _arg12);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IApInterfaceEventCallback {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IApInterfaceEventCallback.DESCRIPTOR;
      }

      @Override // android.net.wifi.nl80211.IApInterfaceEventCallback
      public void onConnectedClientsChanged(NativeWifiClient client, boolean isConnected)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IApInterfaceEventCallback.DESCRIPTOR);
          _data.writeTypedObject(client, 0);
          _data.writeBoolean(isConnected);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.net.wifi.nl80211.IApInterfaceEventCallback
      public void onSoftApChannelSwitched(int frequency, int bandwidth) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IApInterfaceEventCallback.DESCRIPTOR);
          _data.writeInt(frequency);
          _data.writeInt(bandwidth);
          this.mRemote.transact(2, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }
    }

    @Override // android.os.Binder
    public int getMaxTransactionId() {
      return 1;
    }
  }
}
