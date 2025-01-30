package android.location;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;
import java.util.List;

/* loaded from: classes2.dex */
public interface IGnssAntennaInfoListener extends IInterface {
  public static final String DESCRIPTOR = "android.location.IGnssAntennaInfoListener";

  void onGnssAntennaInfoChanged(List<GnssAntennaInfo> list) throws RemoteException;

  public static class Default implements IGnssAntennaInfoListener {
    @Override // android.location.IGnssAntennaInfoListener
    public void onGnssAntennaInfoChanged(List<GnssAntennaInfo> antennaInfos)
        throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IGnssAntennaInfoListener {
    static final int TRANSACTION_onGnssAntennaInfoChanged = 1;

    public Stub() {
      attachInterface(this, IGnssAntennaInfoListener.DESCRIPTOR);
    }

    public static IGnssAntennaInfoListener asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IGnssAntennaInfoListener.DESCRIPTOR);
      if (iin != null && (iin instanceof IGnssAntennaInfoListener)) {
        return (IGnssAntennaInfoListener) iin;
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
          return "onGnssAntennaInfoChanged";
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
        data.enforceInterface(IGnssAntennaInfoListener.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IGnssAntennaInfoListener.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              List<GnssAntennaInfo> _arg0 = data.createTypedArrayList(GnssAntennaInfo.CREATOR);
              data.enforceNoDataAvail();
              onGnssAntennaInfoChanged(_arg0);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IGnssAntennaInfoListener {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IGnssAntennaInfoListener.DESCRIPTOR;
      }

      @Override // android.location.IGnssAntennaInfoListener
      public void onGnssAntennaInfoChanged(List<GnssAntennaInfo> antennaInfos)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IGnssAntennaInfoListener.DESCRIPTOR);
          _data.writeTypedList(antennaInfos, 0);
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
