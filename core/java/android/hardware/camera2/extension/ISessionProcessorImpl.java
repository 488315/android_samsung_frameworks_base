package android.hardware.camera2.extension;

import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.impl.CameraMetadataNative;
import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

/* loaded from: classes.dex */
public interface ISessionProcessorImpl extends IInterface {
  public static final String DESCRIPTOR =
      "android.hardware.camera2.extension.ISessionProcessorImpl";

  void deInitSession(IBinder iBinder) throws RemoteException;

  LatencyPair getRealtimeCaptureLatency() throws RemoteException;

  CameraSessionConfig initSession(
      IBinder iBinder,
      String str,
      Map<String, CameraMetadataNative> map,
      OutputSurface outputSurface,
      OutputSurface outputSurface2,
      OutputSurface outputSurface3)
      throws RemoteException;

  void onCaptureSessionEnd() throws RemoteException;

  void onCaptureSessionStart(IRequestProcessorImpl iRequestProcessorImpl) throws RemoteException;

  void setParameters(CaptureRequest captureRequest) throws RemoteException;

  int startCapture(ICaptureCallback iCaptureCallback, boolean z) throws RemoteException;

  int startRepeating(ICaptureCallback iCaptureCallback) throws RemoteException;

  int startTrigger(CaptureRequest captureRequest, ICaptureCallback iCaptureCallback)
      throws RemoteException;

  void stopRepeating() throws RemoteException;

  public static class Default implements ISessionProcessorImpl {
    @Override // android.hardware.camera2.extension.ISessionProcessorImpl
    public CameraSessionConfig initSession(
        IBinder token,
        String cameraId,
        Map<String, CameraMetadataNative> charsMap,
        OutputSurface previewSurface,
        OutputSurface imageCaptureSurface,
        OutputSurface postviewSurface)
        throws RemoteException {
      return null;
    }

    @Override // android.hardware.camera2.extension.ISessionProcessorImpl
    public void deInitSession(IBinder token) throws RemoteException {}

    @Override // android.hardware.camera2.extension.ISessionProcessorImpl
    public void onCaptureSessionStart(IRequestProcessorImpl requestProcessor)
        throws RemoteException {}

    @Override // android.hardware.camera2.extension.ISessionProcessorImpl
    public void onCaptureSessionEnd() throws RemoteException {}

    @Override // android.hardware.camera2.extension.ISessionProcessorImpl
    public int startRepeating(ICaptureCallback callback) throws RemoteException {
      return 0;
    }

    @Override // android.hardware.camera2.extension.ISessionProcessorImpl
    public void stopRepeating() throws RemoteException {}

    @Override // android.hardware.camera2.extension.ISessionProcessorImpl
    public int startCapture(ICaptureCallback callback, boolean isPostviewRequested)
        throws RemoteException {
      return 0;
    }

    @Override // android.hardware.camera2.extension.ISessionProcessorImpl
    public void setParameters(CaptureRequest captureRequest) throws RemoteException {}

    @Override // android.hardware.camera2.extension.ISessionProcessorImpl
    public int startTrigger(CaptureRequest captureRequest, ICaptureCallback callback)
        throws RemoteException {
      return 0;
    }

    @Override // android.hardware.camera2.extension.ISessionProcessorImpl
    public LatencyPair getRealtimeCaptureLatency() throws RemoteException {
      return null;
    }

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements ISessionProcessorImpl {
    static final int TRANSACTION_deInitSession = 2;
    static final int TRANSACTION_getRealtimeCaptureLatency = 10;
    static final int TRANSACTION_initSession = 1;
    static final int TRANSACTION_onCaptureSessionEnd = 4;
    static final int TRANSACTION_onCaptureSessionStart = 3;
    static final int TRANSACTION_setParameters = 8;
    static final int TRANSACTION_startCapture = 7;
    static final int TRANSACTION_startRepeating = 5;
    static final int TRANSACTION_startTrigger = 9;
    static final int TRANSACTION_stopRepeating = 6;

    public Stub() {
      attachInterface(this, ISessionProcessorImpl.DESCRIPTOR);
    }

    public static ISessionProcessorImpl asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(ISessionProcessorImpl.DESCRIPTOR);
      if (iin != null && (iin instanceof ISessionProcessorImpl)) {
        return (ISessionProcessorImpl) iin;
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
          return "initSession";
        case 2:
          return "deInitSession";
        case 3:
          return "onCaptureSessionStart";
        case 4:
          return "onCaptureSessionEnd";
        case 5:
          return "startRepeating";
        case 6:
          return "stopRepeating";
        case 7:
          return "startCapture";
        case 8:
          return "setParameters";
        case 9:
          return "startTrigger";
        case 10:
          return "getRealtimeCaptureLatency";
        default:
          return null;
      }
    }

    @Override // android.p009os.Binder
    public String getTransactionName(int transactionCode) {
      return getDefaultTransactionName(transactionCode);
    }

    @Override // android.p009os.Binder
    public boolean onTransact(int code, final Parcel data, Parcel reply, int flags)
        throws RemoteException {
      if (code >= 1 && code <= 16777215) {
        data.enforceInterface(ISessionProcessorImpl.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(ISessionProcessorImpl.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              IBinder _arg0 = data.readStrongBinder();
              String _arg1 = data.readString();
              int N = data.readInt();
              final Map<String, CameraMetadataNative> _arg2 = N < 0 ? null : new HashMap<>();
              IntStream.range(0, N)
                  .forEach(
                      new IntConsumer() { // from class:
                                          // android.hardware.camera2.extension.ISessionProcessorImpl$Stub$$ExternalSyntheticLambda0
                        @Override // java.util.function.IntConsumer
                        public final void accept(int i) {
                          ISessionProcessorImpl.Stub.lambda$onTransact$0(Parcel.this, _arg2, i);
                        }
                      });
              OutputSurface _arg3 = (OutputSurface) data.readTypedObject(OutputSurface.CREATOR);
              OutputSurface _arg4 = (OutputSurface) data.readTypedObject(OutputSurface.CREATOR);
              OutputSurface _arg5 = (OutputSurface) data.readTypedObject(OutputSurface.CREATOR);
              data.enforceNoDataAvail();
              CameraSessionConfig _result = initSession(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5);
              reply.writeNoException();
              reply.writeTypedObject(_result, 1);
              return true;
            case 2:
              IBinder _arg02 = data.readStrongBinder();
              data.enforceNoDataAvail();
              deInitSession(_arg02);
              reply.writeNoException();
              return true;
            case 3:
              IRequestProcessorImpl _arg03 =
                  IRequestProcessorImpl.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              onCaptureSessionStart(_arg03);
              reply.writeNoException();
              return true;
            case 4:
              onCaptureSessionEnd();
              reply.writeNoException();
              return true;
            case 5:
              ICaptureCallback _arg04 = ICaptureCallback.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              int _result2 = startRepeating(_arg04);
              reply.writeNoException();
              reply.writeInt(_result2);
              return true;
            case 6:
              stopRepeating();
              reply.writeNoException();
              return true;
            case 7:
              ICaptureCallback _arg05 = ICaptureCallback.Stub.asInterface(data.readStrongBinder());
              boolean _arg12 = data.readBoolean();
              data.enforceNoDataAvail();
              int _result3 = startCapture(_arg05, _arg12);
              reply.writeNoException();
              reply.writeInt(_result3);
              return true;
            case 8:
              CaptureRequest _arg06 = (CaptureRequest) data.readTypedObject(CaptureRequest.CREATOR);
              data.enforceNoDataAvail();
              setParameters(_arg06);
              reply.writeNoException();
              return true;
            case 9:
              CaptureRequest _arg07 = (CaptureRequest) data.readTypedObject(CaptureRequest.CREATOR);
              ICaptureCallback _arg13 = ICaptureCallback.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              int _result4 = startTrigger(_arg07, _arg13);
              reply.writeNoException();
              reply.writeInt(_result4);
              return true;
            case 10:
              LatencyPair _result5 = getRealtimeCaptureLatency();
              reply.writeNoException();
              reply.writeTypedObject(_result5, 1);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    static /* synthetic */ void lambda$onTransact$0(Parcel data, Map _arg2, int i) {
      String k = data.readString();
      CameraMetadataNative v =
          (CameraMetadataNative) data.readTypedObject(CameraMetadataNative.CREATOR);
      _arg2.put(k, v);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class Proxy implements ISessionProcessorImpl {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return ISessionProcessorImpl.DESCRIPTOR;
      }

      @Override // android.hardware.camera2.extension.ISessionProcessorImpl
      public CameraSessionConfig initSession(
          IBinder token,
          String cameraId,
          Map<String, CameraMetadataNative> charsMap,
          OutputSurface previewSurface,
          OutputSurface imageCaptureSurface,
          OutputSurface postviewSurface)
          throws RemoteException {
        final Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISessionProcessorImpl.DESCRIPTOR);
          _data.writeStrongBinder(token);
          _data.writeString(cameraId);
          if (charsMap == null) {
            _data.writeInt(-1);
          } else {
            _data.writeInt(charsMap.size());
            charsMap.forEach(
                new BiConsumer() { // from class:
                                   // android.hardware.camera2.extension.ISessionProcessorImpl$Stub$Proxy$$ExternalSyntheticLambda0
                  @Override // java.util.function.BiConsumer
                  public final void accept(Object obj, Object obj2) {
                    ISessionProcessorImpl.Stub.Proxy.lambda$initSession$0(
                        Parcel.this, (String) obj, (CameraMetadataNative) obj2);
                  }
                });
          }
          _data.writeTypedObject(previewSurface, 0);
          _data.writeTypedObject(imageCaptureSurface, 0);
          _data.writeTypedObject(postviewSurface, 0);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
          CameraSessionConfig _result =
              (CameraSessionConfig) _reply.readTypedObject(CameraSessionConfig.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      static /* synthetic */ void lambda$initSession$0(
          Parcel _data, String k, CameraMetadataNative v) {
        _data.writeString(k);
        _data.writeTypedObject(v, 0);
      }

      @Override // android.hardware.camera2.extension.ISessionProcessorImpl
      public void deInitSession(IBinder token) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISessionProcessorImpl.DESCRIPTOR);
          _data.writeStrongBinder(token);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.camera2.extension.ISessionProcessorImpl
      public void onCaptureSessionStart(IRequestProcessorImpl requestProcessor)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISessionProcessorImpl.DESCRIPTOR);
          _data.writeStrongInterface(requestProcessor);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.camera2.extension.ISessionProcessorImpl
      public void onCaptureSessionEnd() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISessionProcessorImpl.DESCRIPTOR);
          this.mRemote.transact(4, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.camera2.extension.ISessionProcessorImpl
      public int startRepeating(ICaptureCallback callback) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISessionProcessorImpl.DESCRIPTOR);
          _data.writeStrongInterface(callback);
          this.mRemote.transact(5, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.camera2.extension.ISessionProcessorImpl
      public void stopRepeating() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISessionProcessorImpl.DESCRIPTOR);
          this.mRemote.transact(6, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.camera2.extension.ISessionProcessorImpl
      public int startCapture(ICaptureCallback callback, boolean isPostviewRequested)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISessionProcessorImpl.DESCRIPTOR);
          _data.writeStrongInterface(callback);
          _data.writeBoolean(isPostviewRequested);
          this.mRemote.transact(7, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.camera2.extension.ISessionProcessorImpl
      public void setParameters(CaptureRequest captureRequest) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISessionProcessorImpl.DESCRIPTOR);
          _data.writeTypedObject(captureRequest, 0);
          this.mRemote.transact(8, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.camera2.extension.ISessionProcessorImpl
      public int startTrigger(CaptureRequest captureRequest, ICaptureCallback callback)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISessionProcessorImpl.DESCRIPTOR);
          _data.writeTypedObject(captureRequest, 0);
          _data.writeStrongInterface(callback);
          this.mRemote.transact(9, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.camera2.extension.ISessionProcessorImpl
      public LatencyPair getRealtimeCaptureLatency() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ISessionProcessorImpl.DESCRIPTOR);
          this.mRemote.transact(10, _data, _reply, 0);
          _reply.readException();
          LatencyPair _result = (LatencyPair) _reply.readTypedObject(LatencyPair.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    @Override // android.p009os.Binder
    public int getMaxTransactionId() {
      return 9;
    }
  }
}
