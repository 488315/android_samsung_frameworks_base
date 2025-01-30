package android.media.soundtrigger_middleware;

import android.media.soundtrigger.Phrase;
import android.media.soundtrigger.RecognitionConfig;
import android.media.soundtrigger.SoundModel;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface ISoundTriggerInjection extends IInterface {
  public static final String DESCRIPTOR =
      "android$media$soundtrigger_middleware$ISoundTriggerInjection".replace('$', '.');
  public static final String FAKE_HAL_ARCH = "injection";

  void onClientAttached(IBinder iBinder, IInjectGlobalEvent iInjectGlobalEvent)
      throws RemoteException;

  void onClientDetached(IBinder iBinder) throws RemoteException;

  void onFrameworkDetached(IInjectGlobalEvent iInjectGlobalEvent) throws RemoteException;

  void onParamSet(int i, int i2, IInjectModelEvent iInjectModelEvent) throws RemoteException;

  void onPreempted() throws RemoteException;

  void onRecognitionStarted(
      int i,
      RecognitionConfig recognitionConfig,
      IInjectRecognitionEvent iInjectRecognitionEvent,
      IInjectModelEvent iInjectModelEvent)
      throws RemoteException;

  void onRecognitionStopped(IInjectRecognitionEvent iInjectRecognitionEvent) throws RemoteException;

  void onRestarted(IInjectGlobalEvent iInjectGlobalEvent) throws RemoteException;

  void onSoundModelLoaded(
      SoundModel soundModel,
      Phrase[] phraseArr,
      IInjectModelEvent iInjectModelEvent,
      IInjectGlobalEvent iInjectGlobalEvent)
      throws RemoteException;

  void onSoundModelUnloaded(IInjectModelEvent iInjectModelEvent) throws RemoteException;

  void registerGlobalEventInjection(IInjectGlobalEvent iInjectGlobalEvent) throws RemoteException;

  public static class Default implements ISoundTriggerInjection {
    @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
    public void registerGlobalEventInjection(IInjectGlobalEvent globalInjection)
        throws RemoteException {}

    @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
    public void onRestarted(IInjectGlobalEvent globalSession) throws RemoteException {}

    @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
    public void onFrameworkDetached(IInjectGlobalEvent globalSession) throws RemoteException {}

    @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
    public void onClientAttached(IBinder token, IInjectGlobalEvent globalSession)
        throws RemoteException {}

    @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
    public void onClientDetached(IBinder token) throws RemoteException {}

    @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
    public void onSoundModelLoaded(
        SoundModel model,
        Phrase[] phrases,
        IInjectModelEvent modelInjection,
        IInjectGlobalEvent globalSession)
        throws RemoteException {}

    @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
    public void onParamSet(int modelParam, int value, IInjectModelEvent modelSession)
        throws RemoteException {}

    @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
    public void onRecognitionStarted(
        int audioSessionToken,
        RecognitionConfig config,
        IInjectRecognitionEvent recognitionInjection,
        IInjectModelEvent modelSession)
        throws RemoteException {}

    @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
    public void onRecognitionStopped(IInjectRecognitionEvent recognitionSession)
        throws RemoteException {}

    @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
    public void onSoundModelUnloaded(IInjectModelEvent modelSession) throws RemoteException {}

    @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
    public void onPreempted() throws RemoteException {}

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements ISoundTriggerInjection {
    static final int TRANSACTION_onClientAttached = 4;
    static final int TRANSACTION_onClientDetached = 5;
    static final int TRANSACTION_onFrameworkDetached = 3;
    static final int TRANSACTION_onParamSet = 7;
    static final int TRANSACTION_onPreempted = 11;
    static final int TRANSACTION_onRecognitionStarted = 8;
    static final int TRANSACTION_onRecognitionStopped = 9;
    static final int TRANSACTION_onRestarted = 2;
    static final int TRANSACTION_onSoundModelLoaded = 6;
    static final int TRANSACTION_onSoundModelUnloaded = 10;
    static final int TRANSACTION_registerGlobalEventInjection = 1;

    public Stub() {
      attachInterface(this, DESCRIPTOR);
    }

    public static ISoundTriggerInjection asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (iin != null && (iin instanceof ISoundTriggerInjection)) {
        return (ISoundTriggerInjection) iin;
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
              IInjectGlobalEvent _arg0 =
                  IInjectGlobalEvent.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              registerGlobalEventInjection(_arg0);
              return true;
            case 2:
              IInjectGlobalEvent _arg02 =
                  IInjectGlobalEvent.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              onRestarted(_arg02);
              return true;
            case 3:
              IBinder _arg03 = data.readStrongBinder();
              IInjectGlobalEvent _arg04 = IInjectGlobalEvent.Stub.asInterface(_arg03);
              data.enforceNoDataAvail();
              onFrameworkDetached(_arg04);
              return true;
            case 4:
              IBinder _arg05 = data.readStrongBinder();
              IInjectGlobalEvent _arg1 =
                  IInjectGlobalEvent.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              onClientAttached(_arg05, _arg1);
              return true;
            case 5:
              IBinder _arg06 = data.readStrongBinder();
              data.enforceNoDataAvail();
              onClientDetached(_arg06);
              return true;
            case 6:
              SoundModel _arg07 = (SoundModel) data.readTypedObject(SoundModel.CREATOR);
              Phrase[] _arg12 = (Phrase[]) data.createTypedArray(Phrase.CREATOR);
              IInjectModelEvent _arg2 = IInjectModelEvent.Stub.asInterface(data.readStrongBinder());
              IInjectGlobalEvent _arg3 =
                  IInjectGlobalEvent.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              onSoundModelLoaded(_arg07, _arg12, _arg2, _arg3);
              return true;
            case 7:
              int _arg08 = data.readInt();
              int _arg13 = data.readInt();
              IInjectModelEvent _arg22 =
                  IInjectModelEvent.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              onParamSet(_arg08, _arg13, _arg22);
              return true;
            case 8:
              int _arg09 = data.readInt();
              RecognitionConfig _arg14 =
                  (RecognitionConfig) data.readTypedObject(RecognitionConfig.CREATOR);
              IInjectRecognitionEvent _arg23 =
                  IInjectRecognitionEvent.Stub.asInterface(data.readStrongBinder());
              IInjectModelEvent _arg32 =
                  IInjectModelEvent.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              onRecognitionStarted(_arg09, _arg14, _arg23, _arg32);
              return true;
            case 9:
              IInjectRecognitionEvent _arg010 =
                  IInjectRecognitionEvent.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              onRecognitionStopped(_arg010);
              return true;
            case 10:
              IInjectModelEvent _arg011 =
                  IInjectModelEvent.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              onSoundModelUnloaded(_arg011);
              return true;
            case 11:
              onPreempted();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements ISoundTriggerInjection {
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

      @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
      public void registerGlobalEventInjection(IInjectGlobalEvent globalInjection)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeStrongInterface(globalInjection);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
      public void onRestarted(IInjectGlobalEvent globalSession) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeStrongInterface(globalSession);
          this.mRemote.transact(2, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
      public void onFrameworkDetached(IInjectGlobalEvent globalSession) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeStrongInterface(globalSession);
          this.mRemote.transact(3, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
      public void onClientAttached(IBinder token, IInjectGlobalEvent globalSession)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeStrongBinder(token);
          _data.writeStrongInterface(globalSession);
          this.mRemote.transact(4, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
      public void onClientDetached(IBinder token) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeStrongBinder(token);
          this.mRemote.transact(5, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
      public void onSoundModelLoaded(
          SoundModel model,
          Phrase[] phrases,
          IInjectModelEvent modelInjection,
          IInjectGlobalEvent globalSession)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeTypedObject(model, 0);
          _data.writeTypedArray(phrases, 0);
          _data.writeStrongInterface(modelInjection);
          _data.writeStrongInterface(globalSession);
          this.mRemote.transact(6, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
      public void onParamSet(int modelParam, int value, IInjectModelEvent modelSession)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(modelParam);
          _data.writeInt(value);
          _data.writeStrongInterface(modelSession);
          this.mRemote.transact(7, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
      public void onRecognitionStarted(
          int audioSessionToken,
          RecognitionConfig config,
          IInjectRecognitionEvent recognitionInjection,
          IInjectModelEvent modelSession)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeInt(audioSessionToken);
          _data.writeTypedObject(config, 0);
          _data.writeStrongInterface(recognitionInjection);
          _data.writeStrongInterface(modelSession);
          this.mRemote.transact(8, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
      public void onRecognitionStopped(IInjectRecognitionEvent recognitionSession)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeStrongInterface(recognitionSession);
          this.mRemote.transact(9, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
      public void onSoundModelUnloaded(IInjectModelEvent modelSession) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeStrongInterface(modelSession);
          this.mRemote.transact(10, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.media.soundtrigger_middleware.ISoundTriggerInjection
      public void onPreempted() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          this.mRemote.transact(11, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }
    }
  }
}
