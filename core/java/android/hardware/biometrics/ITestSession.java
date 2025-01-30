package android.hardware.biometrics;

import android.Manifest;
import android.app.ActivityThread;
import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.PermissionEnforcer;
import android.p009os.RemoteException;

/* loaded from: classes.dex */
public interface ITestSession extends IInterface {
  public static final String DESCRIPTOR = "android.hardware.biometrics.ITestSession";

  void acceptAuthentication(int i) throws RemoteException;

  void cleanupInternalState(int i) throws RemoteException;

  void finishEnroll(int i) throws RemoteException;

  void notifyAcquired(int i, int i2) throws RemoteException;

  void notifyError(int i, int i2) throws RemoteException;

  void notifyVendorAcquired(int i, int i2) throws RemoteException;

  void notifyVendorError(int i, int i2) throws RemoteException;

  void rejectAuthentication(int i) throws RemoteException;

  void setTestHalEnabled(boolean z) throws RemoteException;

  void startEnroll(int i) throws RemoteException;

  public static class Default implements ITestSession {
    @Override // android.hardware.biometrics.ITestSession
    public void setTestHalEnabled(boolean enableTestHal) throws RemoteException {}

    @Override // android.hardware.biometrics.ITestSession
    public void startEnroll(int userId) throws RemoteException {}

    @Override // android.hardware.biometrics.ITestSession
    public void finishEnroll(int userId) throws RemoteException {}

    @Override // android.hardware.biometrics.ITestSession
    public void acceptAuthentication(int userId) throws RemoteException {}

    @Override // android.hardware.biometrics.ITestSession
    public void rejectAuthentication(int userId) throws RemoteException {}

    @Override // android.hardware.biometrics.ITestSession
    public void notifyAcquired(int userId, int acquireInfo) throws RemoteException {}

    @Override // android.hardware.biometrics.ITestSession
    public void notifyError(int userId, int errorCode) throws RemoteException {}

    @Override // android.hardware.biometrics.ITestSession
    public void cleanupInternalState(int userId) throws RemoteException {}

    @Override // android.hardware.biometrics.ITestSession
    public void notifyVendorAcquired(int userId, int vendorAcquired) throws RemoteException {}

    @Override // android.hardware.biometrics.ITestSession
    public void notifyVendorError(int userId, int vendorErrorCode) throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements ITestSession {
    static final int TRANSACTION_acceptAuthentication = 4;
    static final int TRANSACTION_cleanupInternalState = 8;
    static final int TRANSACTION_finishEnroll = 3;
    static final int TRANSACTION_notifyAcquired = 6;
    static final int TRANSACTION_notifyError = 7;
    static final int TRANSACTION_notifyVendorAcquired = 9;
    static final int TRANSACTION_notifyVendorError = 10;
    static final int TRANSACTION_rejectAuthentication = 5;
    static final int TRANSACTION_setTestHalEnabled = 1;
    static final int TRANSACTION_startEnroll = 2;
    private final PermissionEnforcer mEnforcer;

    public Stub(PermissionEnforcer enforcer) {
      attachInterface(this, ITestSession.DESCRIPTOR);
      if (enforcer == null) {
        throw new IllegalArgumentException("enforcer cannot be null");
      }
      this.mEnforcer = enforcer;
    }

    @Deprecated
    public Stub() {
      this(
          PermissionEnforcer.fromContext(
              ActivityThread.currentActivityThread().getSystemContext()));
    }

    public static ITestSession asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(ITestSession.DESCRIPTOR);
      if (iin != null && (iin instanceof ITestSession)) {
        return (ITestSession) iin;
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
          return "setTestHalEnabled";
        case 2:
          return "startEnroll";
        case 3:
          return "finishEnroll";
        case 4:
          return "acceptAuthentication";
        case 5:
          return "rejectAuthentication";
        case 6:
          return "notifyAcquired";
        case 7:
          return "notifyError";
        case 8:
          return "cleanupInternalState";
        case 9:
          return "notifyVendorAcquired";
        case 10:
          return "notifyVendorError";
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
        data.enforceInterface(ITestSession.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(ITestSession.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              boolean _arg0 = data.readBoolean();
              data.enforceNoDataAvail();
              setTestHalEnabled(_arg0);
              reply.writeNoException();
              return true;
            case 2:
              int _arg02 = data.readInt();
              data.enforceNoDataAvail();
              startEnroll(_arg02);
              reply.writeNoException();
              return true;
            case 3:
              int _arg03 = data.readInt();
              data.enforceNoDataAvail();
              finishEnroll(_arg03);
              reply.writeNoException();
              return true;
            case 4:
              int _arg04 = data.readInt();
              data.enforceNoDataAvail();
              acceptAuthentication(_arg04);
              reply.writeNoException();
              return true;
            case 5:
              int _arg05 = data.readInt();
              data.enforceNoDataAvail();
              rejectAuthentication(_arg05);
              reply.writeNoException();
              return true;
            case 6:
              int _arg06 = data.readInt();
              int _arg1 = data.readInt();
              data.enforceNoDataAvail();
              notifyAcquired(_arg06, _arg1);
              reply.writeNoException();
              return true;
            case 7:
              int _arg07 = data.readInt();
              int _arg12 = data.readInt();
              data.enforceNoDataAvail();
              notifyError(_arg07, _arg12);
              reply.writeNoException();
              return true;
            case 8:
              int _arg08 = data.readInt();
              data.enforceNoDataAvail();
              cleanupInternalState(_arg08);
              reply.writeNoException();
              return true;
            case 9:
              int _arg09 = data.readInt();
              int _arg13 = data.readInt();
              data.enforceNoDataAvail();
              notifyVendorAcquired(_arg09, _arg13);
              reply.writeNoException();
              return true;
            case 10:
              int _arg010 = data.readInt();
              int _arg14 = data.readInt();
              data.enforceNoDataAvail();
              notifyVendorError(_arg010, _arg14);
              reply.writeNoException();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements ITestSession {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return ITestSession.DESCRIPTOR;
      }

      @Override // android.hardware.biometrics.ITestSession
      public void setTestHalEnabled(boolean enableTestHal) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ITestSession.DESCRIPTOR);
          _data.writeBoolean(enableTestHal);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.biometrics.ITestSession
      public void startEnroll(int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ITestSession.DESCRIPTOR);
          _data.writeInt(userId);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.biometrics.ITestSession
      public void finishEnroll(int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ITestSession.DESCRIPTOR);
          _data.writeInt(userId);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.biometrics.ITestSession
      public void acceptAuthentication(int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ITestSession.DESCRIPTOR);
          _data.writeInt(userId);
          this.mRemote.transact(4, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.biometrics.ITestSession
      public void rejectAuthentication(int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ITestSession.DESCRIPTOR);
          _data.writeInt(userId);
          this.mRemote.transact(5, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.biometrics.ITestSession
      public void notifyAcquired(int userId, int acquireInfo) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ITestSession.DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeInt(acquireInfo);
          this.mRemote.transact(6, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.biometrics.ITestSession
      public void notifyError(int userId, int errorCode) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ITestSession.DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeInt(errorCode);
          this.mRemote.transact(7, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.biometrics.ITestSession
      public void cleanupInternalState(int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ITestSession.DESCRIPTOR);
          _data.writeInt(userId);
          this.mRemote.transact(8, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.biometrics.ITestSession
      public void notifyVendorAcquired(int userId, int vendorAcquired) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ITestSession.DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeInt(vendorAcquired);
          this.mRemote.transact(9, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.hardware.biometrics.ITestSession
      public void notifyVendorError(int userId, int vendorErrorCode) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ITestSession.DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeInt(vendorErrorCode);
          this.mRemote.transact(10, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    protected void setTestHalEnabled_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.TEST_BIOMETRIC, getCallingPid(), getCallingUid());
    }

    protected void startEnroll_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.TEST_BIOMETRIC, getCallingPid(), getCallingUid());
    }

    protected void finishEnroll_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.TEST_BIOMETRIC, getCallingPid(), getCallingUid());
    }

    protected void acceptAuthentication_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.TEST_BIOMETRIC, getCallingPid(), getCallingUid());
    }

    protected void rejectAuthentication_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.TEST_BIOMETRIC, getCallingPid(), getCallingUid());
    }

    protected void notifyAcquired_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.TEST_BIOMETRIC, getCallingPid(), getCallingUid());
    }

    protected void notifyError_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.TEST_BIOMETRIC, getCallingPid(), getCallingUid());
    }

    protected void cleanupInternalState_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.TEST_BIOMETRIC, getCallingPid(), getCallingUid());
    }

    protected void notifyVendorAcquired_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.TEST_BIOMETRIC, getCallingPid(), getCallingUid());
    }

    protected void notifyVendorError_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.TEST_BIOMETRIC, getCallingPid(), getCallingUid());
    }

    @Override // android.p009os.Binder
    public int getMaxTransactionId() {
      return 9;
    }
  }
}
