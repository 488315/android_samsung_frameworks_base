package android.service.quicksettings;

import android.content.Intent;
import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;
import android.text.TextUtils;
import android.widget.RemoteViews;

/* loaded from: classes3.dex */
public interface IQSTileService extends IInterface {
  void onClick(IBinder iBinder) throws RemoteException;

  void onStartListening() throws RemoteException;

  void onStopListening() throws RemoteException;

  void onTileAdded() throws RemoteException;

  void onTileRemoved() throws RemoteException;

  void onUnlockComplete() throws RemoteException;

  RemoteViews semGetDetailView() throws RemoteException;

  CharSequence semGetDetailViewSettingButtonName() throws RemoteException;

  CharSequence semGetDetailViewTitle() throws RemoteException;

  Intent semGetSettingsIntent() throws RemoteException;

  boolean semIsToggleButtonChecked() throws RemoteException;

  boolean semIsToggleButtonExists() throws RemoteException;

  void semSetToggleButtonChecked(boolean z) throws RemoteException;

  public static class Default implements IQSTileService {
    @Override // android.service.quicksettings.IQSTileService
    public void onTileAdded() throws RemoteException {}

    @Override // android.service.quicksettings.IQSTileService
    public void onTileRemoved() throws RemoteException {}

    @Override // android.service.quicksettings.IQSTileService
    public void onStartListening() throws RemoteException {}

    @Override // android.service.quicksettings.IQSTileService
    public void onStopListening() throws RemoteException {}

    @Override // android.service.quicksettings.IQSTileService
    public void onClick(IBinder wtoken) throws RemoteException {}

    @Override // android.service.quicksettings.IQSTileService
    public void onUnlockComplete() throws RemoteException {}

    @Override // android.service.quicksettings.IQSTileService
    public CharSequence semGetDetailViewTitle() throws RemoteException {
      return null;
    }

    @Override // android.service.quicksettings.IQSTileService
    public CharSequence semGetDetailViewSettingButtonName() throws RemoteException {
      return null;
    }

    @Override // android.service.quicksettings.IQSTileService
    public boolean semIsToggleButtonExists() throws RemoteException {
      return false;
    }

    @Override // android.service.quicksettings.IQSTileService
    public boolean semIsToggleButtonChecked() throws RemoteException {
      return false;
    }

    @Override // android.service.quicksettings.IQSTileService
    public RemoteViews semGetDetailView() throws RemoteException {
      return null;
    }

    @Override // android.service.quicksettings.IQSTileService
    public Intent semGetSettingsIntent() throws RemoteException {
      return null;
    }

    @Override // android.service.quicksettings.IQSTileService
    public void semSetToggleButtonChecked(boolean checked) throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements IQSTileService {
    public static final String DESCRIPTOR = "android.service.quicksettings.IQSTileService";
    static final int TRANSACTION_onClick = 5;
    static final int TRANSACTION_onStartListening = 3;
    static final int TRANSACTION_onStopListening = 4;
    static final int TRANSACTION_onTileAdded = 1;
    static final int TRANSACTION_onTileRemoved = 2;
    static final int TRANSACTION_onUnlockComplete = 6;
    static final int TRANSACTION_semGetDetailView = 11;
    static final int TRANSACTION_semGetDetailViewSettingButtonName = 8;
    static final int TRANSACTION_semGetDetailViewTitle = 7;
    static final int TRANSACTION_semGetSettingsIntent = 12;
    static final int TRANSACTION_semIsToggleButtonChecked = 10;
    static final int TRANSACTION_semIsToggleButtonExists = 9;
    static final int TRANSACTION_semSetToggleButtonChecked = 13;

    public Stub() {
      attachInterface(this, DESCRIPTOR);
    }

    public static IQSTileService asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (iin != null && (iin instanceof IQSTileService)) {
        return (IQSTileService) iin;
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
          return "onTileAdded";
        case 2:
          return "onTileRemoved";
        case 3:
          return "onStartListening";
        case 4:
          return "onStopListening";
        case 5:
          return "onClick";
        case 6:
          return "onUnlockComplete";
        case 7:
          return "semGetDetailViewTitle";
        case 8:
          return "semGetDetailViewSettingButtonName";
        case 9:
          return "semIsToggleButtonExists";
        case 10:
          return "semIsToggleButtonChecked";
        case 11:
          return "semGetDetailView";
        case 12:
          return "semGetSettingsIntent";
        case 13:
          return "semSetToggleButtonChecked";
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
              onTileAdded();
              reply.writeNoException();
              return true;
            case 2:
              onTileRemoved();
              reply.writeNoException();
              return true;
            case 3:
              onStartListening();
              reply.writeNoException();
              return true;
            case 4:
              onStopListening();
              reply.writeNoException();
              return true;
            case 5:
              IBinder _arg0 = data.readStrongBinder();
              data.enforceNoDataAvail();
              onClick(_arg0);
              reply.writeNoException();
              return true;
            case 6:
              onUnlockComplete();
              reply.writeNoException();
              return true;
            case 7:
              CharSequence _result = semGetDetailViewTitle();
              reply.writeNoException();
              if (_result != null) {
                reply.writeInt(1);
                TextUtils.writeToParcel(_result, reply, 1);
              } else {
                reply.writeInt(0);
              }
              return true;
            case 8:
              CharSequence _result2 = semGetDetailViewSettingButtonName();
              reply.writeNoException();
              if (_result2 != null) {
                reply.writeInt(1);
                TextUtils.writeToParcel(_result2, reply, 1);
              } else {
                reply.writeInt(0);
              }
              return true;
            case 9:
              boolean _result3 = semIsToggleButtonExists();
              reply.writeNoException();
              reply.writeBoolean(_result3);
              return true;
            case 10:
              boolean _result4 = semIsToggleButtonChecked();
              reply.writeNoException();
              reply.writeBoolean(_result4);
              return true;
            case 11:
              RemoteViews _result5 = semGetDetailView();
              reply.writeNoException();
              reply.writeTypedObject(_result5, 1);
              return true;
            case 12:
              Intent _result6 = semGetSettingsIntent();
              reply.writeNoException();
              reply.writeTypedObject(_result6, 1);
              return true;
            case 13:
              boolean _arg02 = data.readBoolean();
              data.enforceNoDataAvail();
              semSetToggleButtonChecked(_arg02);
              reply.writeNoException();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements IQSTileService {
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

      @Override // android.service.quicksettings.IQSTileService
      public void onTileAdded() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.service.quicksettings.IQSTileService
      public void onTileRemoved() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.service.quicksettings.IQSTileService
      public void onStartListening() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.service.quicksettings.IQSTileService
      public void onStopListening() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(4, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.service.quicksettings.IQSTileService
      public void onClick(IBinder wtoken) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongBinder(wtoken);
          this.mRemote.transact(5, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.service.quicksettings.IQSTileService
      public void onUnlockComplete() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(6, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.service.quicksettings.IQSTileService
      public CharSequence semGetDetailViewTitle() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(7, _data, _reply, 0);
          _reply.readException();
          CharSequence _result =
              (CharSequence) _reply.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.service.quicksettings.IQSTileService
      public CharSequence semGetDetailViewSettingButtonName() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(8, _data, _reply, 0);
          _reply.readException();
          CharSequence _result =
              (CharSequence) _reply.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.service.quicksettings.IQSTileService
      public boolean semIsToggleButtonExists() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(9, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.service.quicksettings.IQSTileService
      public boolean semIsToggleButtonChecked() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(10, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.service.quicksettings.IQSTileService
      public RemoteViews semGetDetailView() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(11, _data, _reply, 0);
          _reply.readException();
          RemoteViews _result = (RemoteViews) _reply.readTypedObject(RemoteViews.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.service.quicksettings.IQSTileService
      public Intent semGetSettingsIntent() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(12, _data, _reply, 0);
          _reply.readException();
          Intent _result = (Intent) _reply.readTypedObject(Intent.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.service.quicksettings.IQSTileService
      public void semSetToggleButtonChecked(boolean checked) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeBoolean(checked);
          this.mRemote.transact(13, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    @Override // android.p009os.Binder
    public int getMaxTransactionId() {
      return 12;
    }
  }
}
