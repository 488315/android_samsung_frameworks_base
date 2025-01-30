package android.service.carrier;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes3.dex */
public interface ICarrierMessagingClientService extends IInterface {
  public static final String DESCRIPTOR = "android.service.carrier.ICarrierMessagingClientService";

  public static class Default implements ICarrierMessagingClientService {
    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements ICarrierMessagingClientService {
    public Stub() {
      attachInterface(this, ICarrierMessagingClientService.DESCRIPTOR);
    }

    public static ICarrierMessagingClientService asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(ICarrierMessagingClientService.DESCRIPTOR);
      if (iin != null && (iin instanceof ICarrierMessagingClientService)) {
        return (ICarrierMessagingClientService) iin;
      }
      return new Proxy(obj);
    }

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return this;
    }

    public static String getDefaultTransactionName(int transactionCode) {
      return null;
    }

    @Override // android.p009os.Binder
    public String getTransactionName(int transactionCode) {
      return getDefaultTransactionName(transactionCode);
    }

    @Override // android.p009os.Binder
    public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
        throws RemoteException {
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(ICarrierMessagingClientService.DESCRIPTOR);
          return true;
        default:
          return super.onTransact(code, data, reply, flags);
      }
    }

    private static class Proxy implements ICarrierMessagingClientService {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return ICarrierMessagingClientService.DESCRIPTOR;
      }
    }

    @Override // android.p009os.Binder
    public int getMaxTransactionId() {
      return 0;
    }
  }
}
