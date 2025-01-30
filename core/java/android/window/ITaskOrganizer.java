package android.window;

import android.app.ActivityManager;
import android.content.p002pm.ActivityInfo;
import android.content.res.Configuration;
import android.p009os.Binder;
import android.p009os.Bundle;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;
import android.view.SurfaceControl;

/* loaded from: classes4.dex */
public interface ITaskOrganizer extends IInterface {
  public static final String DESCRIPTOR = "android.window.ITaskOrganizer";

  void addStartingWindow(StartingWindowInfo startingWindowInfo) throws RemoteException;

  void copySplashScreenView(int i) throws RemoteException;

  void onAppSplashScreenViewRemoved(int i) throws RemoteException;

  void onBackPressedOnTaskRoot(ActivityManager.RunningTaskInfo runningTaskInfo)
      throws RemoteException;

  void onImeDrawnOnTask(int i) throws RemoteException;

  void onImmersiveModeChanged(int i, boolean z) throws RemoteException;

  void onKeepScreenOnChanged(int i, boolean z) throws RemoteException;

  void onNewDexImmersiveModeChanged(int i, boolean z) throws RemoteException;

  void onSplitLayoutChangeRequested(ActivityManager.RunningTaskInfo runningTaskInfo, Bundle bundle)
      throws RemoteException;

  void onTaskAppeared(
      ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl)
      throws RemoteException;

  void onTaskInfoChanged(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException;

  void onTaskVanished(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException;

  void preloadSplashScreenAppIcon(ActivityInfo activityInfo, int i, Configuration configuration)
      throws RemoteException;

  void removeStartingWindow(StartingWindowRemovalInfo startingWindowRemovalInfo)
      throws RemoteException;

  void requestAffordanceAnim(ActivityManager.RunningTaskInfo runningTaskInfo, int i)
      throws RemoteException;

  void resetStashedFreeform(int i, boolean z) throws RemoteException;

  public static class Default implements ITaskOrganizer {
    @Override // android.window.ITaskOrganizer
    public void addStartingWindow(StartingWindowInfo info) throws RemoteException {}

    @Override // android.window.ITaskOrganizer
    public void removeStartingWindow(StartingWindowRemovalInfo removalInfo)
        throws RemoteException {}

    @Override // android.window.ITaskOrganizer
    public void copySplashScreenView(int taskId) throws RemoteException {}

    @Override // android.window.ITaskOrganizer
    public void onAppSplashScreenViewRemoved(int taskId) throws RemoteException {}

    @Override // android.window.ITaskOrganizer
    public void onTaskAppeared(ActivityManager.RunningTaskInfo taskInfo, SurfaceControl leash)
        throws RemoteException {}

    @Override // android.window.ITaskOrganizer
    public void onTaskVanished(ActivityManager.RunningTaskInfo taskInfo) throws RemoteException {}

    @Override // android.window.ITaskOrganizer
    public void onTaskInfoChanged(ActivityManager.RunningTaskInfo taskInfo)
        throws RemoteException {}

    @Override // android.window.ITaskOrganizer
    public void onBackPressedOnTaskRoot(ActivityManager.RunningTaskInfo taskInfo)
        throws RemoteException {}

    @Override // android.window.ITaskOrganizer
    public void onImeDrawnOnTask(int taskId) throws RemoteException {}

    @Override // android.window.ITaskOrganizer
    public void onSplitLayoutChangeRequested(
        ActivityManager.RunningTaskInfo taskInfo, Bundle infoBundle) throws RemoteException {}

    @Override // android.window.ITaskOrganizer
    public void preloadSplashScreenAppIcon(ActivityInfo info, int userId, Configuration config)
        throws RemoteException {}

    @Override // android.window.ITaskOrganizer
    public void resetStashedFreeform(int taskId, boolean anim) throws RemoteException {}

    @Override // android.window.ITaskOrganizer
    public void onKeepScreenOnChanged(int taskId, boolean keepScreenOn) throws RemoteException {}

    @Override // android.window.ITaskOrganizer
    public void onImmersiveModeChanged(int taskId, boolean immersive) throws RemoteException {}

    @Override // android.window.ITaskOrganizer
    public void onNewDexImmersiveModeChanged(int taskId, boolean immersive)
        throws RemoteException {}

    @Override // android.window.ITaskOrganizer
    public void requestAffordanceAnim(ActivityManager.RunningTaskInfo taskInfo, int gestureFrom)
        throws RemoteException {}

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements ITaskOrganizer {
    static final int TRANSACTION_addStartingWindow = 1;
    static final int TRANSACTION_copySplashScreenView = 3;
    static final int TRANSACTION_onAppSplashScreenViewRemoved = 4;
    static final int TRANSACTION_onBackPressedOnTaskRoot = 8;
    static final int TRANSACTION_onImeDrawnOnTask = 9;
    static final int TRANSACTION_onImmersiveModeChanged = 14;
    static final int TRANSACTION_onKeepScreenOnChanged = 13;
    static final int TRANSACTION_onNewDexImmersiveModeChanged = 15;
    static final int TRANSACTION_onSplitLayoutChangeRequested = 10;
    static final int TRANSACTION_onTaskAppeared = 5;
    static final int TRANSACTION_onTaskInfoChanged = 7;
    static final int TRANSACTION_onTaskVanished = 6;
    static final int TRANSACTION_preloadSplashScreenAppIcon = 11;
    static final int TRANSACTION_removeStartingWindow = 2;
    static final int TRANSACTION_requestAffordanceAnim = 16;
    static final int TRANSACTION_resetStashedFreeform = 12;

    public Stub() {
      attachInterface(this, ITaskOrganizer.DESCRIPTOR);
    }

    public static ITaskOrganizer asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(ITaskOrganizer.DESCRIPTOR);
      if (iin != null && (iin instanceof ITaskOrganizer)) {
        return (ITaskOrganizer) iin;
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
          return "addStartingWindow";
        case 2:
          return "removeStartingWindow";
        case 3:
          return "copySplashScreenView";
        case 4:
          return "onAppSplashScreenViewRemoved";
        case 5:
          return "onTaskAppeared";
        case 6:
          return "onTaskVanished";
        case 7:
          return "onTaskInfoChanged";
        case 8:
          return "onBackPressedOnTaskRoot";
        case 9:
          return "onImeDrawnOnTask";
        case 10:
          return "onSplitLayoutChangeRequested";
        case 11:
          return "preloadSplashScreenAppIcon";
        case 12:
          return "resetStashedFreeform";
        case 13:
          return "onKeepScreenOnChanged";
        case 14:
          return "onImmersiveModeChanged";
        case 15:
          return "onNewDexImmersiveModeChanged";
        case 16:
          return "requestAffordanceAnim";
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
        data.enforceInterface(ITaskOrganizer.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(ITaskOrganizer.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              StartingWindowInfo _arg0 =
                  (StartingWindowInfo) data.readTypedObject(StartingWindowInfo.CREATOR);
              data.enforceNoDataAvail();
              addStartingWindow(_arg0);
              return true;
            case 2:
              StartingWindowRemovalInfo _arg02 =
                  (StartingWindowRemovalInfo)
                      data.readTypedObject(StartingWindowRemovalInfo.CREATOR);
              data.enforceNoDataAvail();
              removeStartingWindow(_arg02);
              return true;
            case 3:
              int _arg03 = data.readInt();
              data.enforceNoDataAvail();
              copySplashScreenView(_arg03);
              return true;
            case 4:
              int _arg04 = data.readInt();
              data.enforceNoDataAvail();
              onAppSplashScreenViewRemoved(_arg04);
              return true;
            case 5:
              ActivityManager.RunningTaskInfo _arg05 =
                  (ActivityManager.RunningTaskInfo)
                      data.readTypedObject(ActivityManager.RunningTaskInfo.CREATOR);
              SurfaceControl _arg1 = (SurfaceControl) data.readTypedObject(SurfaceControl.CREATOR);
              data.enforceNoDataAvail();
              onTaskAppeared(_arg05, _arg1);
              return true;
            case 6:
              ActivityManager.RunningTaskInfo _arg06 =
                  (ActivityManager.RunningTaskInfo)
                      data.readTypedObject(ActivityManager.RunningTaskInfo.CREATOR);
              data.enforceNoDataAvail();
              onTaskVanished(_arg06);
              return true;
            case 7:
              ActivityManager.RunningTaskInfo _arg07 =
                  (ActivityManager.RunningTaskInfo)
                      data.readTypedObject(ActivityManager.RunningTaskInfo.CREATOR);
              data.enforceNoDataAvail();
              onTaskInfoChanged(_arg07);
              return true;
            case 8:
              ActivityManager.RunningTaskInfo _arg08 =
                  (ActivityManager.RunningTaskInfo)
                      data.readTypedObject(ActivityManager.RunningTaskInfo.CREATOR);
              data.enforceNoDataAvail();
              onBackPressedOnTaskRoot(_arg08);
              return true;
            case 9:
              int _arg09 = data.readInt();
              data.enforceNoDataAvail();
              onImeDrawnOnTask(_arg09);
              return true;
            case 10:
              ActivityManager.RunningTaskInfo _arg010 =
                  (ActivityManager.RunningTaskInfo)
                      data.readTypedObject(ActivityManager.RunningTaskInfo.CREATOR);
              Bundle _arg12 = (Bundle) data.readTypedObject(Bundle.CREATOR);
              data.enforceNoDataAvail();
              onSplitLayoutChangeRequested(_arg010, _arg12);
              return true;
            case 11:
              ActivityInfo _arg011 = (ActivityInfo) data.readTypedObject(ActivityInfo.CREATOR);
              int _arg13 = data.readInt();
              Configuration _arg2 = (Configuration) data.readTypedObject(Configuration.CREATOR);
              data.enforceNoDataAvail();
              preloadSplashScreenAppIcon(_arg011, _arg13, _arg2);
              return true;
            case 12:
              int _arg012 = data.readInt();
              boolean _arg14 = data.readBoolean();
              data.enforceNoDataAvail();
              resetStashedFreeform(_arg012, _arg14);
              return true;
            case 13:
              int _arg013 = data.readInt();
              boolean _arg15 = data.readBoolean();
              data.enforceNoDataAvail();
              onKeepScreenOnChanged(_arg013, _arg15);
              return true;
            case 14:
              int _arg014 = data.readInt();
              boolean _arg16 = data.readBoolean();
              data.enforceNoDataAvail();
              onImmersiveModeChanged(_arg014, _arg16);
              return true;
            case 15:
              int _arg015 = data.readInt();
              boolean _arg17 = data.readBoolean();
              data.enforceNoDataAvail();
              onNewDexImmersiveModeChanged(_arg015, _arg17);
              return true;
            case 16:
              ActivityManager.RunningTaskInfo _arg016 =
                  (ActivityManager.RunningTaskInfo)
                      data.readTypedObject(ActivityManager.RunningTaskInfo.CREATOR);
              int _arg18 = data.readInt();
              data.enforceNoDataAvail();
              requestAffordanceAnim(_arg016, _arg18);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements ITaskOrganizer {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return ITaskOrganizer.DESCRIPTOR;
      }

      @Override // android.window.ITaskOrganizer
      public void addStartingWindow(StartingWindowInfo info) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ITaskOrganizer.DESCRIPTOR);
          _data.writeTypedObject(info, 0);
          this.mRemote.transact(1, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.window.ITaskOrganizer
      public void removeStartingWindow(StartingWindowRemovalInfo removalInfo)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ITaskOrganizer.DESCRIPTOR);
          _data.writeTypedObject(removalInfo, 0);
          this.mRemote.transact(2, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.window.ITaskOrganizer
      public void copySplashScreenView(int taskId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ITaskOrganizer.DESCRIPTOR);
          _data.writeInt(taskId);
          this.mRemote.transact(3, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.window.ITaskOrganizer
      public void onAppSplashScreenViewRemoved(int taskId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ITaskOrganizer.DESCRIPTOR);
          _data.writeInt(taskId);
          this.mRemote.transact(4, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.window.ITaskOrganizer
      public void onTaskAppeared(ActivityManager.RunningTaskInfo taskInfo, SurfaceControl leash)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ITaskOrganizer.DESCRIPTOR);
          _data.writeTypedObject(taskInfo, 0);
          _data.writeTypedObject(leash, 0);
          this.mRemote.transact(5, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.window.ITaskOrganizer
      public void onTaskVanished(ActivityManager.RunningTaskInfo taskInfo) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ITaskOrganizer.DESCRIPTOR);
          _data.writeTypedObject(taskInfo, 0);
          this.mRemote.transact(6, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.window.ITaskOrganizer
      public void onTaskInfoChanged(ActivityManager.RunningTaskInfo taskInfo)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ITaskOrganizer.DESCRIPTOR);
          _data.writeTypedObject(taskInfo, 0);
          this.mRemote.transact(7, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.window.ITaskOrganizer
      public void onBackPressedOnTaskRoot(ActivityManager.RunningTaskInfo taskInfo)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ITaskOrganizer.DESCRIPTOR);
          _data.writeTypedObject(taskInfo, 0);
          this.mRemote.transact(8, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.window.ITaskOrganizer
      public void onImeDrawnOnTask(int taskId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ITaskOrganizer.DESCRIPTOR);
          _data.writeInt(taskId);
          this.mRemote.transact(9, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.window.ITaskOrganizer
      public void onSplitLayoutChangeRequested(
          ActivityManager.RunningTaskInfo taskInfo, Bundle infoBundle) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ITaskOrganizer.DESCRIPTOR);
          _data.writeTypedObject(taskInfo, 0);
          _data.writeTypedObject(infoBundle, 0);
          this.mRemote.transact(10, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.window.ITaskOrganizer
      public void preloadSplashScreenAppIcon(ActivityInfo info, int userId, Configuration config)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ITaskOrganizer.DESCRIPTOR);
          _data.writeTypedObject(info, 0);
          _data.writeInt(userId);
          _data.writeTypedObject(config, 0);
          this.mRemote.transact(11, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.window.ITaskOrganizer
      public void resetStashedFreeform(int taskId, boolean anim) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ITaskOrganizer.DESCRIPTOR);
          _data.writeInt(taskId);
          _data.writeBoolean(anim);
          this.mRemote.transact(12, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.window.ITaskOrganizer
      public void onKeepScreenOnChanged(int taskId, boolean keepScreenOn) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ITaskOrganizer.DESCRIPTOR);
          _data.writeInt(taskId);
          _data.writeBoolean(keepScreenOn);
          this.mRemote.transact(13, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.window.ITaskOrganizer
      public void onImmersiveModeChanged(int taskId, boolean immersive) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ITaskOrganizer.DESCRIPTOR);
          _data.writeInt(taskId);
          _data.writeBoolean(immersive);
          this.mRemote.transact(14, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.window.ITaskOrganizer
      public void onNewDexImmersiveModeChanged(int taskId, boolean immersive)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ITaskOrganizer.DESCRIPTOR);
          _data.writeInt(taskId);
          _data.writeBoolean(immersive);
          this.mRemote.transact(15, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.window.ITaskOrganizer
      public void requestAffordanceAnim(ActivityManager.RunningTaskInfo taskInfo, int gestureFrom)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(ITaskOrganizer.DESCRIPTOR);
          _data.writeTypedObject(taskInfo, 0);
          _data.writeInt(gestureFrom);
          this.mRemote.transact(16, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }
    }

    @Override // android.p009os.Binder
    public int getMaxTransactionId() {
      return 15;
    }
  }
}
