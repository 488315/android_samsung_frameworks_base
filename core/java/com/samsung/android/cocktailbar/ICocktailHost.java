package com.samsung.android.cocktailbar;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.widget.RemoteViews;

/* loaded from: classes5.dex */
public interface ICocktailHost extends IInterface {
  public static final String DESCRIPTOR = "com.samsung.android.cocktailbar.ICocktailHost";

  void changeVisibleEdgeService(boolean z, int i) throws RemoteException;

  void closeCocktail(int i, int i2, int i3) throws RemoteException;

  void notePauseComponent(ComponentName componentName) throws RemoteException;

  void noteResumeComponent(ComponentName componentName) throws RemoteException;

  void notifyKeyguardState(boolean z, int i) throws RemoteException;

  void notifyWakeUpState(boolean z, int i, int i2) throws RemoteException;

  void packageSuspendChanged(Cocktail cocktail) throws RemoteException;

  void partiallyUpdateCocktail(int i, RemoteViews remoteViews, int i2) throws RemoteException;

  void partiallyUpdateHelpView(int i, RemoteViews remoteViews, int i2) throws RemoteException;

  void removeCocktail(int i, int i2) throws RemoteException;

  void sendExtraData(int i, Bundle bundle) throws RemoteException;

  void setDisableTickerView(int i, int i2) throws RemoteException;

  void setPullToRefresh(int i, int i2, PendingIntent pendingIntent, int i3) throws RemoteException;

  void showCocktail(int i, int i2) throws RemoteException;

  void switchDefaultCocktail(int i) throws RemoteException;

  void updateCocktail(int i, Cocktail cocktail, int i2) throws RemoteException;

  void updateToolLauncher(int i) throws RemoteException;

  void viewDataChanged(int i, int i2, int i3) throws RemoteException;

  public static class Default implements ICocktailHost {
    @Override // com.samsung.android.cocktailbar.ICocktailHost
    public void updateCocktail(int cocktailId, Cocktail cocktail, int userId)
        throws RemoteException {}

    @Override // com.samsung.android.cocktailbar.ICocktailHost
    public void partiallyUpdateCocktail(int cocktailId, RemoteViews contentView, int userId)
        throws RemoteException {}

    @Override // com.samsung.android.cocktailbar.ICocktailHost
    public void partiallyUpdateHelpView(int cocktailId, RemoteViews helpView, int userId)
        throws RemoteException {}

    @Override // com.samsung.android.cocktailbar.ICocktailHost
    public void removeCocktail(int cocktailId, int userId) throws RemoteException {}

    @Override // com.samsung.android.cocktailbar.ICocktailHost
    public void showCocktail(int cocktailId, int userId) throws RemoteException {}

    @Override // com.samsung.android.cocktailbar.ICocktailHost
    public void closeCocktail(int cocktailId, int category, int userId) throws RemoteException {}

    @Override // com.samsung.android.cocktailbar.ICocktailHost
    public void viewDataChanged(int cocktailId, int viewId, int userId) throws RemoteException {}

    @Override // com.samsung.android.cocktailbar.ICocktailHost
    public void setPullToRefresh(
        int cocktailId, int viewId, PendingIntent pendingIntent, int userId)
        throws RemoteException {}

    @Override // com.samsung.android.cocktailbar.ICocktailHost
    public void updateToolLauncher(int userId) throws RemoteException {}

    @Override // com.samsung.android.cocktailbar.ICocktailHost
    public void notifyKeyguardState(boolean enable, int userId) throws RemoteException {}

    @Override // com.samsung.android.cocktailbar.ICocktailHost
    public void notifyWakeUpState(boolean bEnable, int keyCode, int userId)
        throws RemoteException {}

    @Override // com.samsung.android.cocktailbar.ICocktailHost
    public void switchDefaultCocktail(int userId) throws RemoteException {}

    @Override // com.samsung.android.cocktailbar.ICocktailHost
    public void sendExtraData(int userId, Bundle extraData) throws RemoteException {}

    @Override // com.samsung.android.cocktailbar.ICocktailHost
    public void setDisableTickerView(int state, int userId) throws RemoteException {}

    @Override // com.samsung.android.cocktailbar.ICocktailHost
    public void changeVisibleEdgeService(boolean visible, int userId) throws RemoteException {}

    @Override // com.samsung.android.cocktailbar.ICocktailHost
    public void noteResumeComponent(ComponentName resumeComponentName) throws RemoteException {}

    @Override // com.samsung.android.cocktailbar.ICocktailHost
    public void notePauseComponent(ComponentName pauseComponentName) throws RemoteException {}

    @Override // com.samsung.android.cocktailbar.ICocktailHost
    public void packageSuspendChanged(Cocktail cocktail) throws RemoteException {}

    @Override // android.os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements ICocktailHost {
    static final int TRANSACTION_changeVisibleEdgeService = 15;
    static final int TRANSACTION_closeCocktail = 6;
    static final int TRANSACTION_notePauseComponent = 17;
    static final int TRANSACTION_noteResumeComponent = 16;
    static final int TRANSACTION_notifyKeyguardState = 10;
    static final int TRANSACTION_notifyWakeUpState = 11;
    static final int TRANSACTION_packageSuspendChanged = 18;
    static final int TRANSACTION_partiallyUpdateCocktail = 2;
    static final int TRANSACTION_partiallyUpdateHelpView = 3;
    static final int TRANSACTION_removeCocktail = 4;
    static final int TRANSACTION_sendExtraData = 13;
    static final int TRANSACTION_setDisableTickerView = 14;
    static final int TRANSACTION_setPullToRefresh = 8;
    static final int TRANSACTION_showCocktail = 5;
    static final int TRANSACTION_switchDefaultCocktail = 12;
    static final int TRANSACTION_updateCocktail = 1;
    static final int TRANSACTION_updateToolLauncher = 9;
    static final int TRANSACTION_viewDataChanged = 7;

    public Stub() {
      attachInterface(this, ICocktailHost.DESCRIPTOR);
    }

    public static ICocktailHost asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(ICocktailHost.DESCRIPTOR);
      if (iin != null && (iin instanceof ICocktailHost)) {
        return (ICocktailHost) iin;
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
          return "updateCocktail";
        case 2:
          return "partiallyUpdateCocktail";
        case 3:
          return "partiallyUpdateHelpView";
        case 4:
          return "removeCocktail";
        case 5:
          return "showCocktail";
        case 6:
          return "closeCocktail";
        case 7:
          return "viewDataChanged";
        case 8:
          return "setPullToRefresh";
        case 9:
          return "updateToolLauncher";
        case 10:
          return "notifyKeyguardState";
        case 11:
          return "notifyWakeUpState";
        case 12:
          return "switchDefaultCocktail";
        case 13:
          return "sendExtraData";
        case 14:
          return "setDisableTickerView";
        case 15:
          return "changeVisibleEdgeService";
        case 16:
          return "noteResumeComponent";
        case 17:
          return "notePauseComponent";
        case 18:
          return "packageSuspendChanged";
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
        data.enforceInterface(ICocktailHost.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(ICocktailHost.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              int _arg0 = data.readInt();
              Cocktail _arg1 = (Cocktail) data.readTypedObject(Cocktail.CREATOR);
              int _arg2 = data.readInt();
              data.enforceNoDataAvail();
              updateCocktail(_arg0, _arg1, _arg2);
              reply.writeNoException();
              return true;
            case 2:
              int _arg02 = data.readInt();
              RemoteViews _arg12 = (RemoteViews) data.readTypedObject(RemoteViews.CREATOR);
              int _arg22 = data.readInt();
              data.enforceNoDataAvail();
              partiallyUpdateCocktail(_arg02, _arg12, _arg22);
              reply.writeNoException();
              return true;
            case 3:
              int _arg03 = data.readInt();
              RemoteViews _arg13 = (RemoteViews) data.readTypedObject(RemoteViews.CREATOR);
              int _arg23 = data.readInt();
              data.enforceNoDataAvail();
              partiallyUpdateHelpView(_arg03, _arg13, _arg23);
              reply.writeNoException();
              return true;
            case 4:
              int _arg04 = data.readInt();
              int _arg14 = data.readInt();
              data.enforceNoDataAvail();
              removeCocktail(_arg04, _arg14);
              reply.writeNoException();
              return true;
            case 5:
              int _arg05 = data.readInt();
              int _arg15 = data.readInt();
              data.enforceNoDataAvail();
              showCocktail(_arg05, _arg15);
              reply.writeNoException();
              return true;
            case 6:
              int _arg06 = data.readInt();
              int _arg16 = data.readInt();
              int _arg24 = data.readInt();
              data.enforceNoDataAvail();
              closeCocktail(_arg06, _arg16, _arg24);
              reply.writeNoException();
              return true;
            case 7:
              int _arg07 = data.readInt();
              int _arg17 = data.readInt();
              int _arg25 = data.readInt();
              data.enforceNoDataAvail();
              viewDataChanged(_arg07, _arg17, _arg25);
              reply.writeNoException();
              return true;
            case 8:
              int _arg08 = data.readInt();
              int _arg18 = data.readInt();
              PendingIntent _arg26 = (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
              int _arg3 = data.readInt();
              data.enforceNoDataAvail();
              setPullToRefresh(_arg08, _arg18, _arg26, _arg3);
              reply.writeNoException();
              return true;
            case 9:
              int _arg09 = data.readInt();
              data.enforceNoDataAvail();
              updateToolLauncher(_arg09);
              reply.writeNoException();
              return true;
            case 10:
              boolean _arg010 = data.readBoolean();
              int _arg19 = data.readInt();
              data.enforceNoDataAvail();
              notifyKeyguardState(_arg010, _arg19);
              reply.writeNoException();
              return true;
            case 11:
              boolean _arg011 = data.readBoolean();
              int _arg110 = data.readInt();
              int _arg27 = data.readInt();
              data.enforceNoDataAvail();
              notifyWakeUpState(_arg011, _arg110, _arg27);
              reply.writeNoException();
              return true;
            case 12:
              int _arg012 = data.readInt();
              data.enforceNoDataAvail();
              switchDefaultCocktail(_arg012);
              reply.writeNoException();
              return true;
            case 13:
              int _arg013 = data.readInt();
              Bundle _arg111 = (Bundle) data.readTypedObject(Bundle.CREATOR);
              data.enforceNoDataAvail();
              sendExtraData(_arg013, _arg111);
              reply.writeNoException();
              return true;
            case 14:
              int _arg014 = data.readInt();
              int _arg112 = data.readInt();
              data.enforceNoDataAvail();
              setDisableTickerView(_arg014, _arg112);
              reply.writeNoException();
              return true;
            case 15:
              boolean _arg015 = data.readBoolean();
              int _arg113 = data.readInt();
              data.enforceNoDataAvail();
              changeVisibleEdgeService(_arg015, _arg113);
              reply.writeNoException();
              return true;
            case 16:
              ComponentName _arg016 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
              data.enforceNoDataAvail();
              noteResumeComponent(_arg016);
              reply.writeNoException();
              return true;
            case 17:
              ComponentName _arg017 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
              data.enforceNoDataAvail();
              notePauseComponent(_arg017);
              reply.writeNoException();
              return true;
            case 18:
              Cocktail _arg018 = (Cocktail) data.readTypedObject(Cocktail.CREATOR);
              data.enforceNoDataAvail();
              packageSuspendChanged(_arg018);
              reply.writeNoException();
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements ICocktailHost {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return ICocktailHost.DESCRIPTOR;
      }

      @Override // com.samsung.android.cocktailbar.ICocktailHost
      public void updateCocktail(int cocktailId, Cocktail cocktail, int userId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ICocktailHost.DESCRIPTOR);
          _data.writeInt(cocktailId);
          _data.writeTypedObject(cocktail, 0);
          _data.writeInt(userId);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.cocktailbar.ICocktailHost
      public void partiallyUpdateCocktail(int cocktailId, RemoteViews contentView, int userId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ICocktailHost.DESCRIPTOR);
          _data.writeInt(cocktailId);
          _data.writeTypedObject(contentView, 0);
          _data.writeInt(userId);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.cocktailbar.ICocktailHost
      public void partiallyUpdateHelpView(int cocktailId, RemoteViews helpView, int userId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ICocktailHost.DESCRIPTOR);
          _data.writeInt(cocktailId);
          _data.writeTypedObject(helpView, 0);
          _data.writeInt(userId);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.cocktailbar.ICocktailHost
      public void removeCocktail(int cocktailId, int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ICocktailHost.DESCRIPTOR);
          _data.writeInt(cocktailId);
          _data.writeInt(userId);
          this.mRemote.transact(4, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.cocktailbar.ICocktailHost
      public void showCocktail(int cocktailId, int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ICocktailHost.DESCRIPTOR);
          _data.writeInt(cocktailId);
          _data.writeInt(userId);
          this.mRemote.transact(5, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.cocktailbar.ICocktailHost
      public void closeCocktail(int cocktailId, int category, int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ICocktailHost.DESCRIPTOR);
          _data.writeInt(cocktailId);
          _data.writeInt(category);
          _data.writeInt(userId);
          this.mRemote.transact(6, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.cocktailbar.ICocktailHost
      public void viewDataChanged(int cocktailId, int viewId, int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ICocktailHost.DESCRIPTOR);
          _data.writeInt(cocktailId);
          _data.writeInt(viewId);
          _data.writeInt(userId);
          this.mRemote.transact(7, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.cocktailbar.ICocktailHost
      public void setPullToRefresh(
          int cocktailId, int viewId, PendingIntent pendingIntent, int userId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ICocktailHost.DESCRIPTOR);
          _data.writeInt(cocktailId);
          _data.writeInt(viewId);
          _data.writeTypedObject(pendingIntent, 0);
          _data.writeInt(userId);
          this.mRemote.transact(8, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.cocktailbar.ICocktailHost
      public void updateToolLauncher(int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ICocktailHost.DESCRIPTOR);
          _data.writeInt(userId);
          this.mRemote.transact(9, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.cocktailbar.ICocktailHost
      public void notifyKeyguardState(boolean enable, int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ICocktailHost.DESCRIPTOR);
          _data.writeBoolean(enable);
          _data.writeInt(userId);
          this.mRemote.transact(10, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.cocktailbar.ICocktailHost
      public void notifyWakeUpState(boolean bEnable, int keyCode, int userId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ICocktailHost.DESCRIPTOR);
          _data.writeBoolean(bEnable);
          _data.writeInt(keyCode);
          _data.writeInt(userId);
          this.mRemote.transact(11, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.cocktailbar.ICocktailHost
      public void switchDefaultCocktail(int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ICocktailHost.DESCRIPTOR);
          _data.writeInt(userId);
          this.mRemote.transact(12, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.cocktailbar.ICocktailHost
      public void sendExtraData(int userId, Bundle extraData) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ICocktailHost.DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeTypedObject(extraData, 0);
          this.mRemote.transact(13, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.cocktailbar.ICocktailHost
      public void setDisableTickerView(int state, int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ICocktailHost.DESCRIPTOR);
          _data.writeInt(state);
          _data.writeInt(userId);
          this.mRemote.transact(14, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.cocktailbar.ICocktailHost
      public void changeVisibleEdgeService(boolean visible, int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ICocktailHost.DESCRIPTOR);
          _data.writeBoolean(visible);
          _data.writeInt(userId);
          this.mRemote.transact(15, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.cocktailbar.ICocktailHost
      public void noteResumeComponent(ComponentName resumeComponentName) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ICocktailHost.DESCRIPTOR);
          _data.writeTypedObject(resumeComponentName, 0);
          this.mRemote.transact(16, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.cocktailbar.ICocktailHost
      public void notePauseComponent(ComponentName pauseComponentName) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ICocktailHost.DESCRIPTOR);
          _data.writeTypedObject(pauseComponentName, 0);
          this.mRemote.transact(17, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // com.samsung.android.cocktailbar.ICocktailHost
      public void packageSuspendChanged(Cocktail cocktail) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ICocktailHost.DESCRIPTOR);
          _data.writeTypedObject(cocktail, 0);
          this.mRemote.transact(18, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    @Override // android.os.Binder
    public int getMaxTransactionId() {
      return 17;
    }
  }
}
