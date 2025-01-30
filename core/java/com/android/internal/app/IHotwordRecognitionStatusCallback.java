package com.android.internal.app;

import android.hardware.soundtrigger.SoundTrigger;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.service.voice.HotwordDetectedResult;
import android.service.voice.HotwordDetectionServiceFailure;
import android.service.voice.HotwordRejectedResult;
import android.service.voice.SoundTriggerFailure;
import android.service.voice.VisualQueryDetectionServiceFailure;

/* loaded from: classes4.dex */
public interface IHotwordRecognitionStatusCallback extends IInterface {
  public static final String DESCRIPTOR =
      "com.android.internal.app.IHotwordRecognitionStatusCallback";

  void onGenericSoundTriggerDetected(SoundTrigger.GenericRecognitionEvent genericRecognitionEvent)
      throws RemoteException;

  void onHotwordDetectionServiceFailure(
      HotwordDetectionServiceFailure hotwordDetectionServiceFailure) throws RemoteException;

  void onKeyphraseDetected(
      SoundTrigger.KeyphraseRecognitionEvent keyphraseRecognitionEvent,
      HotwordDetectedResult hotwordDetectedResult)
      throws RemoteException;

  void onProcessRestarted() throws RemoteException;

  void onRecognitionPaused() throws RemoteException;

  void onRecognitionResumed() throws RemoteException;

  void onRejected(HotwordRejectedResult hotwordRejectedResult) throws RemoteException;

  void onSoundTriggerFailure(SoundTriggerFailure soundTriggerFailure) throws RemoteException;

  void onStatusReported(int i) throws RemoteException;

  void onUnknownFailure(String str) throws RemoteException;

  void onVisualQueryDetectionServiceFailure(
      VisualQueryDetectionServiceFailure visualQueryDetectionServiceFailure) throws RemoteException;

  public static class Default implements IHotwordRecognitionStatusCallback {
    @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
    public void onKeyphraseDetected(
        SoundTrigger.KeyphraseRecognitionEvent recognitionEvent, HotwordDetectedResult result)
        throws RemoteException {}

    @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
    public void onGenericSoundTriggerDetected(SoundTrigger.GenericRecognitionEvent recognitionEvent)
        throws RemoteException {}

    @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
    public void onRejected(HotwordRejectedResult result) throws RemoteException {}

    @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
    public void onHotwordDetectionServiceFailure(
        HotwordDetectionServiceFailure hotwordDetectionServiceFailure) throws RemoteException {}

    @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
    public void onVisualQueryDetectionServiceFailure(
        VisualQueryDetectionServiceFailure visualQueryDetectionServiceFailure)
        throws RemoteException {}

    @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
    public void onSoundTriggerFailure(SoundTriggerFailure soundTriggerFailure)
        throws RemoteException {}

    @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
    public void onUnknownFailure(String errorMessage) throws RemoteException {}

    @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
    public void onRecognitionPaused() throws RemoteException {}

    @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
    public void onRecognitionResumed() throws RemoteException {}

    @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
    public void onStatusReported(int status) throws RemoteException {}

    @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
    public void onProcessRestarted() throws RemoteException {}

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IHotwordRecognitionStatusCallback {
    static final int TRANSACTION_onGenericSoundTriggerDetected = 2;
    static final int TRANSACTION_onHotwordDetectionServiceFailure = 4;
    static final int TRANSACTION_onKeyphraseDetected = 1;
    static final int TRANSACTION_onProcessRestarted = 11;
    static final int TRANSACTION_onRecognitionPaused = 8;
    static final int TRANSACTION_onRecognitionResumed = 9;
    static final int TRANSACTION_onRejected = 3;
    static final int TRANSACTION_onSoundTriggerFailure = 6;
    static final int TRANSACTION_onStatusReported = 10;
    static final int TRANSACTION_onUnknownFailure = 7;
    static final int TRANSACTION_onVisualQueryDetectionServiceFailure = 5;

    public Stub() {
      attachInterface(this, IHotwordRecognitionStatusCallback.DESCRIPTOR);
    }

    public static IHotwordRecognitionStatusCallback asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(IHotwordRecognitionStatusCallback.DESCRIPTOR);
      if (iin != null && (iin instanceof IHotwordRecognitionStatusCallback)) {
        return (IHotwordRecognitionStatusCallback) iin;
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
          return "onKeyphraseDetected";
        case 2:
          return "onGenericSoundTriggerDetected";
        case 3:
          return "onRejected";
        case 4:
          return "onHotwordDetectionServiceFailure";
        case 5:
          return "onVisualQueryDetectionServiceFailure";
        case 6:
          return "onSoundTriggerFailure";
        case 7:
          return "onUnknownFailure";
        case 8:
          return "onRecognitionPaused";
        case 9:
          return "onRecognitionResumed";
        case 10:
          return "onStatusReported";
        case 11:
          return "onProcessRestarted";
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
        data.enforceInterface(IHotwordRecognitionStatusCallback.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(IHotwordRecognitionStatusCallback.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              SoundTrigger.KeyphraseRecognitionEvent _arg0 =
                  (SoundTrigger.KeyphraseRecognitionEvent)
                      data.readTypedObject(SoundTrigger.KeyphraseRecognitionEvent.CREATOR);
              HotwordDetectedResult _arg1 =
                  (HotwordDetectedResult) data.readTypedObject(HotwordDetectedResult.CREATOR);
              data.enforceNoDataAvail();
              onKeyphraseDetected(_arg0, _arg1);
              return true;
            case 2:
              SoundTrigger.GenericRecognitionEvent _arg02 =
                  (SoundTrigger.GenericRecognitionEvent)
                      data.readTypedObject(SoundTrigger.GenericRecognitionEvent.CREATOR);
              data.enforceNoDataAvail();
              onGenericSoundTriggerDetected(_arg02);
              return true;
            case 3:
              HotwordRejectedResult _arg03 =
                  (HotwordRejectedResult) data.readTypedObject(HotwordRejectedResult.CREATOR);
              data.enforceNoDataAvail();
              onRejected(_arg03);
              return true;
            case 4:
              HotwordDetectionServiceFailure _arg04 =
                  (HotwordDetectionServiceFailure)
                      data.readTypedObject(HotwordDetectionServiceFailure.CREATOR);
              data.enforceNoDataAvail();
              onHotwordDetectionServiceFailure(_arg04);
              return true;
            case 5:
              VisualQueryDetectionServiceFailure _arg05 =
                  (VisualQueryDetectionServiceFailure)
                      data.readTypedObject(VisualQueryDetectionServiceFailure.CREATOR);
              data.enforceNoDataAvail();
              onVisualQueryDetectionServiceFailure(_arg05);
              return true;
            case 6:
              SoundTriggerFailure _arg06 =
                  (SoundTriggerFailure) data.readTypedObject(SoundTriggerFailure.CREATOR);
              data.enforceNoDataAvail();
              onSoundTriggerFailure(_arg06);
              return true;
            case 7:
              String _arg07 = data.readString();
              data.enforceNoDataAvail();
              onUnknownFailure(_arg07);
              return true;
            case 8:
              onRecognitionPaused();
              return true;
            case 9:
              onRecognitionResumed();
              return true;
            case 10:
              int _arg08 = data.readInt();
              data.enforceNoDataAvail();
              onStatusReported(_arg08);
              return true;
            case 11:
              onProcessRestarted();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IHotwordRecognitionStatusCallback {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return IHotwordRecognitionStatusCallback.DESCRIPTOR;
      }

      @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
      public void onKeyphraseDetected(
          SoundTrigger.KeyphraseRecognitionEvent recognitionEvent, HotwordDetectedResult result)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IHotwordRecognitionStatusCallback.DESCRIPTOR);
          _data.writeTypedObject(recognitionEvent, 0);
          _data.writeTypedObject(result, 0);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
      public void onGenericSoundTriggerDetected(
          SoundTrigger.GenericRecognitionEvent recognitionEvent) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IHotwordRecognitionStatusCallback.DESCRIPTOR);
          _data.writeTypedObject(recognitionEvent, 0);
          this.mRemote.transact(2, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
      public void onRejected(HotwordRejectedResult result) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IHotwordRecognitionStatusCallback.DESCRIPTOR);
          _data.writeTypedObject(result, 0);
          this.mRemote.transact(3, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
      public void onHotwordDetectionServiceFailure(
          HotwordDetectionServiceFailure hotwordDetectionServiceFailure) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IHotwordRecognitionStatusCallback.DESCRIPTOR);
          _data.writeTypedObject(hotwordDetectionServiceFailure, 0);
          this.mRemote.transact(4, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
      public void onVisualQueryDetectionServiceFailure(
          VisualQueryDetectionServiceFailure visualQueryDetectionServiceFailure)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IHotwordRecognitionStatusCallback.DESCRIPTOR);
          _data.writeTypedObject(visualQueryDetectionServiceFailure, 0);
          this.mRemote.transact(5, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
      public void onSoundTriggerFailure(SoundTriggerFailure soundTriggerFailure)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IHotwordRecognitionStatusCallback.DESCRIPTOR);
          _data.writeTypedObject(soundTriggerFailure, 0);
          this.mRemote.transact(6, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
      public void onUnknownFailure(String errorMessage) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IHotwordRecognitionStatusCallback.DESCRIPTOR);
          _data.writeString(errorMessage);
          this.mRemote.transact(7, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
      public void onRecognitionPaused() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IHotwordRecognitionStatusCallback.DESCRIPTOR);
          this.mRemote.transact(8, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
      public void onRecognitionResumed() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IHotwordRecognitionStatusCallback.DESCRIPTOR);
          this.mRemote.transact(9, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
      public void onStatusReported(int status) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IHotwordRecognitionStatusCallback.DESCRIPTOR);
          _data.writeInt(status);
          this.mRemote.transact(10, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // com.android.internal.app.IHotwordRecognitionStatusCallback
      public void onProcessRestarted() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(IHotwordRecognitionStatusCallback.DESCRIPTOR);
          this.mRemote.transact(11, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }
    }

    @Override // android.os.Binder
    public int getMaxTransactionId() {
      return 10;
    }
  }
}
