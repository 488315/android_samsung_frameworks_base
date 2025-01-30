package android.media.musicrecognition;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IMusicRecognitionAttributionTagCallback extends IInterface {
  public static final String DESCRIPTOR =
      "android.media.musicrecognition.IMusicRecognitionAttributionTagCallback";

  void onAttributionTag(String str) throws RemoteException;

  public static class Default implements IMusicRecognitionAttributionTagCallback {
    @Override // android.media.musicrecognition.IMusicRecognitionAttributionTagCallback
    public void onAttributionTag(String attributionTag) throws RemoteException {}

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder
      implements IMusicRecognitionAttributionTagCallback {
    static final int TRANSACTION_onAttributionTag = 1;

    public Stub() {
      attachInterface(this, IMusicRecognitionAttributionTagCallback.DESCRIPTOR);
    }

    public static IMusicRecognitionAttributionTagCallback asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IMusicRecognitionAttributionTagCallback.DESCRIPTOR);
      if (iin != null && (iin instanceof IMusicRecognitionAttributionTagCallback)) {
        return (IMusicRecognitionAttributionTagCallback) iin;
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
          return "onAttributionTag";
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
        data.enforceInterface(IMusicRecognitionAttributionTagCallback.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IMusicRecognitionAttributionTagCallback.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              String _arg0 = data.readString();
              data.enforceNoDataAvail();
              onAttributionTag(_arg0);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IMusicRecognitionAttributionTagCallback {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IMusicRecognitionAttributionTagCallback.DESCRIPTOR;
      }

      @Override // android.media.musicrecognition.IMusicRecognitionAttributionTagCallback
      public void onAttributionTag(String attributionTag) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IMusicRecognitionAttributionTagCallback.DESCRIPTOR);
          _data.writeString(attributionTag);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }
    }

    @Override // android.os.Binder
    public int getMaxTransactionId() {
      return 0;
    }
  }
}
