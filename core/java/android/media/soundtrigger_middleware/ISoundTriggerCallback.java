package android.media.soundtrigger_middleware;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface ISoundTriggerCallback extends IInterface {
  public static final String DESCRIPTOR =
      "android$media$soundtrigger_middleware$ISoundTriggerCallback".replace('$', '.');

  void onModelUnloaded(int i) throws RemoteException;

  void onModuleDied() throws RemoteException;

  void onPhraseRecognition(int i, PhraseRecognitionEventSys phraseRecognitionEventSys, int i2)
      throws RemoteException;

  void onRecognition(int i, RecognitionEventSys recognitionEventSys, int i2) throws RemoteException;

  void onResourcesAvailable() throws RemoteException;

  public static class Default implements ISoundTriggerCallback {
    @Override // android.media.soundtrigger_middleware.ISoundTriggerCallback
    public void onRecognition(int modelHandle, RecognitionEventSys event, int captureSession)
        throws RemoteException {}

    @Override // android.media.soundtrigger_middleware.ISoundTriggerCallback
    public void onPhraseRecognition(
        int modelHandle, PhraseRecognitionEventSys event, int captureSession)
        throws RemoteException {}

    @Override // android.media.soundtrigger_middleware.ISoundTriggerCallback
    public void onResourcesAvailable() throws RemoteException {}

    @Override // android.media.soundtrigger_middleware.ISoundTriggerCallback
    public void onModelUnloaded(int modelHandle) throws RemoteException {}

    @Override // android.media.soundtrigger_middleware.ISoundTriggerCallback
    public void onModuleDied() throws RemoteException {}

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements ISoundTriggerCallback {
    static final int TRANSACTION_onModelUnloaded = 4;
    static final int TRANSACTION_onModuleDied = 5;
    static final int TRANSACTION_onPhraseRecognition = 2;
    static final int TRANSACTION_onRecognition = 1;
    static final int TRANSACTION_onResourcesAvailable = 3;

    public Stub() {
      attachInterface(this, DESCRIPTOR);
    }

    public static ISoundTriggerCallback asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (iin != null && (iin instanceof ISoundTriggerCallback)) {
        return (ISoundTriggerCallback) iin;
      }
      return new Proxy(obj);
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return this;
    }

    @Override // android.os.Binder
    public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
        throws RemoteException {
      String descriptor = DESCRIPTOR;
      if (code >= 1 && code <= 16777215) {
        data.enforceInterface(descriptor);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(descriptor);
          return true;
        default:
          switch (code) {
            case 1:
              int _arg0 = data.readInt();
              RecognitionEventSys _arg1 =
                  (RecognitionEventSys) data.readTypedObject(RecognitionEventSys.CREATOR);
              int _arg2 = data.readInt();
              data.enforceNoDataAvail();
              onRecognition(_arg0, _arg1, _arg2);
              return true;
            case 2:
              int _arg02 = data.readInt();
              PhraseRecognitionEventSys _arg12 =
                  (PhraseRecognitionEventSys)
                      data.readTypedObject(PhraseRecognitionEventSys.CREATOR);
              int _arg22 = data.readInt();
              data.enforceNoDataAvail();
              onPhraseRecognition(_arg02, _arg12, _arg22);
              return true;
            case 3:
              onResourcesAvailable();
              return true;
            case 4:
              int _arg03 = data.readInt();
              data.enforceNoDataAvail();
              onModelUnloaded(_arg03);
              return true;
            case 5:
              onModuleDied();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements ISoundTriggerCallback {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return DESCRIPTOR;
      }

      @Override // android.media.soundtrigger_middleware.ISoundTriggerCallback
      public void onRecognition(int modelHandle, RecognitionEventSys event, int captureSession)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(modelHandle);
          _data.writeTypedObject(event, 0);
          _data.writeInt(captureSession);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.media.soundtrigger_middleware.ISoundTriggerCallback
      public void onPhraseRecognition(
          int modelHandle, PhraseRecognitionEventSys event, int captureSession)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(modelHandle);
          _data.writeTypedObject(event, 0);
          _data.writeInt(captureSession);
          this.mRemote.transact(2, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.media.soundtrigger_middleware.ISoundTriggerCallback
      public void onResourcesAvailable() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          this.mRemote.transact(3, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.media.soundtrigger_middleware.ISoundTriggerCallback
      public void onModelUnloaded(int modelHandle) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(modelHandle);
          this.mRemote.transact(4, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.media.soundtrigger_middleware.ISoundTriggerCallback
      public void onModuleDied() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          this.mRemote.transact(5, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }
    }
  }
}
