package android.view.contentcapture;

import android.content.ContentCaptureOptions;
import android.content.p002pm.ParceledListSlice;
import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes4.dex */
public interface IContentCaptureDirectManager extends IInterface {
  public static final String DESCRIPTOR =
      "android.view.contentcapture.IContentCaptureDirectManager";

  void sendEvents(
      ParceledListSlice parceledListSlice, int i, ContentCaptureOptions contentCaptureOptions)
      throws RemoteException;

  public static class Default implements IContentCaptureDirectManager {
    @Override // android.view.contentcapture.IContentCaptureDirectManager
    public void sendEvents(ParceledListSlice events, int reason, ContentCaptureOptions options)
        throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IContentCaptureDirectManager {
    static final int TRANSACTION_sendEvents = 1;

    public Stub() {
      attachInterface(this, IContentCaptureDirectManager.DESCRIPTOR);
    }

    public static IContentCaptureDirectManager asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IContentCaptureDirectManager.DESCRIPTOR);
      if (iin != null && (iin instanceof IContentCaptureDirectManager)) {
        return (IContentCaptureDirectManager) iin;
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
          return "sendEvents";
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
        data.enforceInterface(IContentCaptureDirectManager.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IContentCaptureDirectManager.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              ParceledListSlice _arg0 =
                  (ParceledListSlice) data.readTypedObject(ParceledListSlice.CREATOR);
              int _arg1 = data.readInt();
              ContentCaptureOptions _arg2 =
                  (ContentCaptureOptions) data.readTypedObject(ContentCaptureOptions.CREATOR);
              data.enforceNoDataAvail();
              sendEvents(_arg0, _arg1, _arg2);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IContentCaptureDirectManager {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IContentCaptureDirectManager.DESCRIPTOR;
      }

      @Override // android.view.contentcapture.IContentCaptureDirectManager
      public void sendEvents(ParceledListSlice events, int reason, ContentCaptureOptions options)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IContentCaptureDirectManager.DESCRIPTOR);
          _data.writeTypedObject(events, 0);
          _data.writeInt(reason);
          _data.writeTypedObject(options, 0);
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
