package android.hardware.radio;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;
import java.util.List;

/* loaded from: classes2.dex */
public interface IAnnouncementListener extends IInterface {
  void onListUpdated(List<Announcement> list) throws RemoteException;

  public static class Default implements IAnnouncementListener {
    @Override // android.hardware.radio.IAnnouncementListener
    public void onListUpdated(List<Announcement> activeAnnouncements) throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IAnnouncementListener {
    public static final String DESCRIPTOR = "android.hardware.radio.IAnnouncementListener";
    static final int TRANSACTION_onListUpdated = 1;

    public Stub() {
      attachInterface(this, DESCRIPTOR);
    }

    public static IAnnouncementListener asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (iin != null && (iin instanceof IAnnouncementListener)) {
        return (IAnnouncementListener) iin;
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
          return "onListUpdated";
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
        data.enforceInterface(DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              List<Announcement> _arg0 = data.createTypedArrayList(Announcement.CREATOR);
              data.enforceNoDataAvail();
              onListUpdated(_arg0);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IAnnouncementListener {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return Stub.DESCRIPTOR;
      }

      @Override // android.hardware.radio.IAnnouncementListener
      public void onListUpdated(List<Announcement> activeAnnouncements) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedList(activeAnnouncements, 0);
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
