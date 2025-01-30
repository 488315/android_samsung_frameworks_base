package android.media;

import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes2.dex */
public interface ISoundDose extends IInterface {
  public static final String DESCRIPTOR = "android$media$ISoundDose".replace('$', '.');

  void forceComputeCsdOnAllDevices(boolean z) throws RemoteException;

  void forceUseFrameworkMel(boolean z) throws RemoteException;

  float getCsd() throws RemoteException;

  float getOutputRs2UpperBound() throws RemoteException;

  boolean isSoundDoseHalSupported() throws RemoteException;

  void resetCsd(float f, SoundDoseRecord[] soundDoseRecordArr) throws RemoteException;

  void setCsdEnabled(boolean z) throws RemoteException;

  void setOutputRs2UpperBound(float f) throws RemoteException;

  void updateAttenuation(float f, int i) throws RemoteException;

  public static class Default implements ISoundDose {
    @Override // android.media.ISoundDose
    public void setOutputRs2UpperBound(float rs2Value) throws RemoteException {}

    @Override // android.media.ISoundDose
    public void resetCsd(float currentCsd, SoundDoseRecord[] records) throws RemoteException {}

    @Override // android.media.ISoundDose
    public void updateAttenuation(float attenuationDB, int device) throws RemoteException {}

    @Override // android.media.ISoundDose
    public void setCsdEnabled(boolean enabled) throws RemoteException {}

    @Override // android.media.ISoundDose
    public float getOutputRs2UpperBound() throws RemoteException {
      return 0.0f;
    }

    @Override // android.media.ISoundDose
    public float getCsd() throws RemoteException {
      return 0.0f;
    }

    @Override // android.media.ISoundDose
    public boolean isSoundDoseHalSupported() throws RemoteException {
      return false;
    }

    @Override // android.media.ISoundDose
    public void forceUseFrameworkMel(boolean useFrameworkMel) throws RemoteException {}

    @Override // android.media.ISoundDose
    public void forceComputeCsdOnAllDevices(boolean computeCsdOnAllDevices)
        throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements ISoundDose {
    static final int TRANSACTION_forceComputeCsdOnAllDevices = 9;
    static final int TRANSACTION_forceUseFrameworkMel = 8;
    static final int TRANSACTION_getCsd = 6;
    static final int TRANSACTION_getOutputRs2UpperBound = 5;
    static final int TRANSACTION_isSoundDoseHalSupported = 7;
    static final int TRANSACTION_resetCsd = 2;
    static final int TRANSACTION_setCsdEnabled = 4;
    static final int TRANSACTION_setOutputRs2UpperBound = 1;
    static final int TRANSACTION_updateAttenuation = 3;

    public Stub() {
      attachInterface(this, DESCRIPTOR);
    }

    public static ISoundDose asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (iin != null && (iin instanceof ISoundDose)) {
        return (ISoundDose) iin;
      }
      return new Proxy(obj);
    }

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return this;
    }

    @Override // android.p009os.Binder
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
              float _arg0 = data.readFloat();
              data.enforceNoDataAvail();
              setOutputRs2UpperBound(_arg0);
              return true;
            case 2:
              float _arg02 = data.readFloat();
              SoundDoseRecord[] _arg1 =
                  (SoundDoseRecord[]) data.createTypedArray(SoundDoseRecord.CREATOR);
              data.enforceNoDataAvail();
              resetCsd(_arg02, _arg1);
              return true;
            case 3:
              float _arg03 = data.readFloat();
              int _arg12 = data.readInt();
              data.enforceNoDataAvail();
              updateAttenuation(_arg03, _arg12);
              return true;
            case 4:
              boolean _arg04 = data.readBoolean();
              data.enforceNoDataAvail();
              setCsdEnabled(_arg04);
              return true;
            case 5:
              float _result = getOutputRs2UpperBound();
              reply.writeNoException();
              reply.writeFloat(_result);
              return true;
            case 6:
              float _result2 = getCsd();
              reply.writeNoException();
              reply.writeFloat(_result2);
              return true;
            case 7:
              boolean _result3 = isSoundDoseHalSupported();
              reply.writeNoException();
              reply.writeBoolean(_result3);
              return true;
            case 8:
              boolean _arg05 = data.readBoolean();
              data.enforceNoDataAvail();
              forceUseFrameworkMel(_arg05);
              return true;
            case 9:
              boolean _arg06 = data.readBoolean();
              data.enforceNoDataAvail();
              forceComputeCsdOnAllDevices(_arg06);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements ISoundDose {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return DESCRIPTOR;
      }

      @Override // android.media.ISoundDose
      public void setOutputRs2UpperBound(float rs2Value) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeFloat(rs2Value);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.media.ISoundDose
      public void resetCsd(float currentCsd, SoundDoseRecord[] records) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeFloat(currentCsd);
          _data.writeTypedArray(records, 0);
          this.mRemote.transact(2, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.media.ISoundDose
      public void updateAttenuation(float attenuationDB, int device) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeFloat(attenuationDB);
          _data.writeInt(device);
          this.mRemote.transact(3, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.media.ISoundDose
      public void setCsdEnabled(boolean enabled) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeBoolean(enabled);
          this.mRemote.transact(4, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.media.ISoundDose
      public float getOutputRs2UpperBound() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          this.mRemote.transact(5, _data, _reply, 0);
          _reply.readException();
          float _result = _reply.readFloat();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.ISoundDose
      public float getCsd() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          this.mRemote.transact(6, _data, _reply, 0);
          _reply.readException();
          float _result = _reply.readFloat();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.ISoundDose
      public boolean isSoundDoseHalSupported() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          this.mRemote.transact(7, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.media.ISoundDose
      public void forceUseFrameworkMel(boolean useFrameworkMel) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeBoolean(useFrameworkMel);
          this.mRemote.transact(8, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.media.ISoundDose
      public void forceComputeCsdOnAllDevices(boolean computeCsdOnAllDevices)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(DESCRIPTOR);
          _data.writeBoolean(computeCsdOnAllDevices);
          this.mRemote.transact(9, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }
    }
  }
}
