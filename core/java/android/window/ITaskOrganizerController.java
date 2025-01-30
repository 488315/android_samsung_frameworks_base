package android.window;

import android.app.ActivityManager;
import android.content.p002pm.ParceledListSlice;
import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;
import java.util.List;

/* loaded from: classes4.dex */
public interface ITaskOrganizerController extends IInterface {
  public static final String DESCRIPTOR = "android.window.ITaskOrganizerController";

  void createRootTask(int i, int i2, IBinder iBinder, boolean z) throws RemoteException;

  void createStageRootTask(int i, int i2, int i3, IBinder iBinder) throws RemoteException;

  boolean deleteRootTask(WindowContainerToken windowContainerToken) throws RemoteException;

  List<ActivityManager.RunningTaskInfo> getChildTasks(
      WindowContainerToken windowContainerToken, int[] iArr) throws RemoteException;

  float getFreeformTaskOpacity(int i) throws RemoteException;

  WindowContainerToken getImeTarget(int i) throws RemoteException;

  List<ActivityManager.RunningTaskInfo> getRootTasks(int i, int[] iArr) throws RemoteException;

  boolean isKeepScreenOn(int i) throws RemoteException;

  boolean isPinStateChangeable(int i) throws RemoteException;

  ParceledListSlice<TaskAppearedInfo> registerTaskOrganizer(ITaskOrganizer iTaskOrganizer)
      throws RemoteException;

  void restartTaskTopActivityProcessIfVisible(WindowContainerToken windowContainerToken)
      throws RemoteException;

  void setFreeformTaskOpacity(int i, float f) throws RemoteException;

  void setFreeformTaskSurfaceOverlappedWithNavi(
      WindowContainerToken windowContainerToken, boolean z) throws RemoteException;

  void setInterceptBackPressedOnTaskRoot(WindowContainerToken windowContainerToken, boolean z)
      throws RemoteException;

  void setOrientationRequestPolicy(boolean z, int[] iArr, int[] iArr2) throws RemoteException;

  boolean togglePinTaskState(int i) throws RemoteException;

  void unregisterTaskOrganizer(ITaskOrganizer iTaskOrganizer) throws RemoteException;

  void updateCameraCompatControlState(WindowContainerToken windowContainerToken, int i)
      throws RemoteException;

  public static class Default implements ITaskOrganizerController {
    @Override // android.window.ITaskOrganizerController
    public ParceledListSlice<TaskAppearedInfo> registerTaskOrganizer(ITaskOrganizer organizer)
        throws RemoteException {
      return null;
    }

    @Override // android.window.ITaskOrganizerController
    public void unregisterTaskOrganizer(ITaskOrganizer organizer) throws RemoteException {}

    @Override // android.window.ITaskOrganizerController
    public void createRootTask(
        int displayId, int windowingMode, IBinder launchCookie, boolean removeWithTaskOrganizer)
        throws RemoteException {}

    @Override // android.window.ITaskOrganizerController
    public void createStageRootTask(
        int displayId, int windowingMode, int stageType, IBinder launchCookie)
        throws RemoteException {}

    @Override // android.window.ITaskOrganizerController
    public boolean deleteRootTask(WindowContainerToken task) throws RemoteException {
      return false;
    }

    @Override // android.window.ITaskOrganizerController
    public List<ActivityManager.RunningTaskInfo> getChildTasks(
        WindowContainerToken parent, int[] activityTypes) throws RemoteException {
      return null;
    }

    @Override // android.window.ITaskOrganizerController
    public List<ActivityManager.RunningTaskInfo> getRootTasks(int displayId, int[] activityTypes)
        throws RemoteException {
      return null;
    }

    @Override // android.window.ITaskOrganizerController
    public WindowContainerToken getImeTarget(int display) throws RemoteException {
      return null;
    }

    @Override // android.window.ITaskOrganizerController
    public void setInterceptBackPressedOnTaskRoot(
        WindowContainerToken task, boolean interceptBackPressed) throws RemoteException {}

    @Override // android.window.ITaskOrganizerController
    public void restartTaskTopActivityProcessIfVisible(WindowContainerToken task)
        throws RemoteException {}

    @Override // android.window.ITaskOrganizerController
    public void updateCameraCompatControlState(WindowContainerToken task, int state)
        throws RemoteException {}

    @Override // android.window.ITaskOrganizerController
    public void setOrientationRequestPolicy(
        boolean isIgnoreOrientationRequestDisabled, int[] fromOrientations, int[] toOrientations)
        throws RemoteException {}

    @Override // android.window.ITaskOrganizerController
    public void setFreeformTaskSurfaceOverlappedWithNavi(WindowContainerToken task, boolean overlap)
        throws RemoteException {}

    @Override // android.window.ITaskOrganizerController
    public float getFreeformTaskOpacity(int taskId) throws RemoteException {
      return 0.0f;
    }

    @Override // android.window.ITaskOrganizerController
    public void setFreeformTaskOpacity(int taskId, float alpha) throws RemoteException {}

    @Override // android.window.ITaskOrganizerController
    public boolean togglePinTaskState(int taskId) throws RemoteException {
      return false;
    }

    @Override // android.window.ITaskOrganizerController
    public boolean isPinStateChangeable(int taskId) throws RemoteException {
      return false;
    }

    @Override // android.window.ITaskOrganizerController
    public boolean isKeepScreenOn(int taskId) throws RemoteException {
      return false;
    }

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements ITaskOrganizerController {
    static final int TRANSACTION_createRootTask = 3;
    static final int TRANSACTION_createStageRootTask = 4;
    static final int TRANSACTION_deleteRootTask = 5;
    static final int TRANSACTION_getChildTasks = 6;
    static final int TRANSACTION_getFreeformTaskOpacity = 14;
    static final int TRANSACTION_getImeTarget = 8;
    static final int TRANSACTION_getRootTasks = 7;
    static final int TRANSACTION_isKeepScreenOn = 18;
    static final int TRANSACTION_isPinStateChangeable = 17;
    static final int TRANSACTION_registerTaskOrganizer = 1;
    static final int TRANSACTION_restartTaskTopActivityProcessIfVisible = 10;
    static final int TRANSACTION_setFreeformTaskOpacity = 15;
    static final int TRANSACTION_setFreeformTaskSurfaceOverlappedWithNavi = 13;
    static final int TRANSACTION_setInterceptBackPressedOnTaskRoot = 9;
    static final int TRANSACTION_setOrientationRequestPolicy = 12;
    static final int TRANSACTION_togglePinTaskState = 16;
    static final int TRANSACTION_unregisterTaskOrganizer = 2;
    static final int TRANSACTION_updateCameraCompatControlState = 11;

    public Stub() {
      attachInterface(this, ITaskOrganizerController.DESCRIPTOR);
    }

    public static ITaskOrganizerController asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(ITaskOrganizerController.DESCRIPTOR);
      if (iin != null && (iin instanceof ITaskOrganizerController)) {
        return (ITaskOrganizerController) iin;
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
          return "registerTaskOrganizer";
        case 2:
          return "unregisterTaskOrganizer";
        case 3:
          return "createRootTask";
        case 4:
          return "createStageRootTask";
        case 5:
          return "deleteRootTask";
        case 6:
          return "getChildTasks";
        case 7:
          return "getRootTasks";
        case 8:
          return "getImeTarget";
        case 9:
          return "setInterceptBackPressedOnTaskRoot";
        case 10:
          return "restartTaskTopActivityProcessIfVisible";
        case 11:
          return "updateCameraCompatControlState";
        case 12:
          return "setOrientationRequestPolicy";
        case 13:
          return "setFreeformTaskSurfaceOverlappedWithNavi";
        case 14:
          return "getFreeformTaskOpacity";
        case 15:
          return "setFreeformTaskOpacity";
        case 16:
          return "togglePinTaskState";
        case 17:
          return "isPinStateChangeable";
        case 18:
          return "isKeepScreenOn";
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
        data.enforceInterface(ITaskOrganizerController.DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(ITaskOrganizerController.DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              ITaskOrganizer _arg0 = ITaskOrganizer.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              ParceledListSlice<TaskAppearedInfo> _result = registerTaskOrganizer(_arg0);
              reply.writeNoException();
              reply.writeTypedObject(_result, 1);
              return true;
            case 2:
              ITaskOrganizer _arg02 = ITaskOrganizer.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              unregisterTaskOrganizer(_arg02);
              reply.writeNoException();
              return true;
            case 3:
              int _arg03 = data.readInt();
              int _arg1 = data.readInt();
              IBinder _arg2 = data.readStrongBinder();
              boolean _arg3 = data.readBoolean();
              data.enforceNoDataAvail();
              createRootTask(_arg03, _arg1, _arg2, _arg3);
              reply.writeNoException();
              return true;
            case 4:
              int _arg04 = data.readInt();
              int _arg12 = data.readInt();
              int _arg22 = data.readInt();
              IBinder _arg32 = data.readStrongBinder();
              data.enforceNoDataAvail();
              createStageRootTask(_arg04, _arg12, _arg22, _arg32);
              reply.writeNoException();
              return true;
            case 5:
              WindowContainerToken _arg05 =
                  (WindowContainerToken) data.readTypedObject(WindowContainerToken.CREATOR);
              data.enforceNoDataAvail();
              boolean _result2 = deleteRootTask(_arg05);
              reply.writeNoException();
              reply.writeBoolean(_result2);
              return true;
            case 6:
              WindowContainerToken _arg06 =
                  (WindowContainerToken) data.readTypedObject(WindowContainerToken.CREATOR);
              int[] _arg13 = data.createIntArray();
              data.enforceNoDataAvail();
              List<ActivityManager.RunningTaskInfo> _result3 = getChildTasks(_arg06, _arg13);
              reply.writeNoException();
              reply.writeTypedList(_result3, 1);
              return true;
            case 7:
              int _arg07 = data.readInt();
              int[] _arg14 = data.createIntArray();
              data.enforceNoDataAvail();
              List<ActivityManager.RunningTaskInfo> _result4 = getRootTasks(_arg07, _arg14);
              reply.writeNoException();
              reply.writeTypedList(_result4, 1);
              return true;
            case 8:
              int _arg08 = data.readInt();
              data.enforceNoDataAvail();
              WindowContainerToken _result5 = getImeTarget(_arg08);
              reply.writeNoException();
              reply.writeTypedObject(_result5, 1);
              return true;
            case 9:
              WindowContainerToken _arg09 =
                  (WindowContainerToken) data.readTypedObject(WindowContainerToken.CREATOR);
              boolean _arg15 = data.readBoolean();
              data.enforceNoDataAvail();
              setInterceptBackPressedOnTaskRoot(_arg09, _arg15);
              reply.writeNoException();
              return true;
            case 10:
              WindowContainerToken _arg010 =
                  (WindowContainerToken) data.readTypedObject(WindowContainerToken.CREATOR);
              data.enforceNoDataAvail();
              restartTaskTopActivityProcessIfVisible(_arg010);
              reply.writeNoException();
              return true;
            case 11:
              WindowContainerToken _arg011 =
                  (WindowContainerToken) data.readTypedObject(WindowContainerToken.CREATOR);
              int _arg16 = data.readInt();
              data.enforceNoDataAvail();
              updateCameraCompatControlState(_arg011, _arg16);
              reply.writeNoException();
              return true;
            case 12:
              boolean _arg012 = data.readBoolean();
              int[] _arg17 = data.createIntArray();
              int[] _arg23 = data.createIntArray();
              data.enforceNoDataAvail();
              setOrientationRequestPolicy(_arg012, _arg17, _arg23);
              reply.writeNoException();
              return true;
            case 13:
              WindowContainerToken _arg013 =
                  (WindowContainerToken) data.readTypedObject(WindowContainerToken.CREATOR);
              boolean _arg18 = data.readBoolean();
              data.enforceNoDataAvail();
              setFreeformTaskSurfaceOverlappedWithNavi(_arg013, _arg18);
              reply.writeNoException();
              return true;
            case 14:
              int _arg014 = data.readInt();
              data.enforceNoDataAvail();
              float _result6 = getFreeformTaskOpacity(_arg014);
              reply.writeNoException();
              reply.writeFloat(_result6);
              return true;
            case 15:
              int _arg015 = data.readInt();
              float _arg19 = data.readFloat();
              data.enforceNoDataAvail();
              setFreeformTaskOpacity(_arg015, _arg19);
              reply.writeNoException();
              return true;
            case 16:
              int _arg016 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result7 = togglePinTaskState(_arg016);
              reply.writeNoException();
              reply.writeBoolean(_result7);
              return true;
            case 17:
              int _arg017 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result8 = isPinStateChangeable(_arg017);
              reply.writeNoException();
              reply.writeBoolean(_result8);
              return true;
            case 18:
              int _arg018 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result9 = isKeepScreenOn(_arg018);
              reply.writeNoException();
              reply.writeBoolean(_result9);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements ITaskOrganizerController {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return ITaskOrganizerController.DESCRIPTOR;
      }

      @Override // android.window.ITaskOrganizerController
      public ParceledListSlice<TaskAppearedInfo> registerTaskOrganizer(ITaskOrganizer organizer)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ITaskOrganizerController.DESCRIPTOR);
          _data.writeStrongInterface(organizer);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
          ParceledListSlice<TaskAppearedInfo> _result =
              (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.window.ITaskOrganizerController
      public void unregisterTaskOrganizer(ITaskOrganizer organizer) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ITaskOrganizerController.DESCRIPTOR);
          _data.writeStrongInterface(organizer);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.window.ITaskOrganizerController
      public void createRootTask(
          int displayId, int windowingMode, IBinder launchCookie, boolean removeWithTaskOrganizer)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ITaskOrganizerController.DESCRIPTOR);
          _data.writeInt(displayId);
          _data.writeInt(windowingMode);
          _data.writeStrongBinder(launchCookie);
          _data.writeBoolean(removeWithTaskOrganizer);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.window.ITaskOrganizerController
      public void createStageRootTask(
          int displayId, int windowingMode, int stageType, IBinder launchCookie)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ITaskOrganizerController.DESCRIPTOR);
          _data.writeInt(displayId);
          _data.writeInt(windowingMode);
          _data.writeInt(stageType);
          _data.writeStrongBinder(launchCookie);
          this.mRemote.transact(4, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.window.ITaskOrganizerController
      public boolean deleteRootTask(WindowContainerToken task) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ITaskOrganizerController.DESCRIPTOR);
          _data.writeTypedObject(task, 0);
          this.mRemote.transact(5, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.window.ITaskOrganizerController
      public List<ActivityManager.RunningTaskInfo> getChildTasks(
          WindowContainerToken parent, int[] activityTypes) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ITaskOrganizerController.DESCRIPTOR);
          _data.writeTypedObject(parent, 0);
          _data.writeIntArray(activityTypes);
          this.mRemote.transact(6, _data, _reply, 0);
          _reply.readException();
          List<ActivityManager.RunningTaskInfo> _result =
              _reply.createTypedArrayList(ActivityManager.RunningTaskInfo.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.window.ITaskOrganizerController
      public List<ActivityManager.RunningTaskInfo> getRootTasks(int displayId, int[] activityTypes)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ITaskOrganizerController.DESCRIPTOR);
          _data.writeInt(displayId);
          _data.writeIntArray(activityTypes);
          this.mRemote.transact(7, _data, _reply, 0);
          _reply.readException();
          List<ActivityManager.RunningTaskInfo> _result =
              _reply.createTypedArrayList(ActivityManager.RunningTaskInfo.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.window.ITaskOrganizerController
      public WindowContainerToken getImeTarget(int display) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ITaskOrganizerController.DESCRIPTOR);
          _data.writeInt(display);
          this.mRemote.transact(8, _data, _reply, 0);
          _reply.readException();
          WindowContainerToken _result =
              (WindowContainerToken) _reply.readTypedObject(WindowContainerToken.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.window.ITaskOrganizerController
      public void setInterceptBackPressedOnTaskRoot(
          WindowContainerToken task, boolean interceptBackPressed) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ITaskOrganizerController.DESCRIPTOR);
          _data.writeTypedObject(task, 0);
          _data.writeBoolean(interceptBackPressed);
          this.mRemote.transact(9, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.window.ITaskOrganizerController
      public void restartTaskTopActivityProcessIfVisible(WindowContainerToken task)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ITaskOrganizerController.DESCRIPTOR);
          _data.writeTypedObject(task, 0);
          this.mRemote.transact(10, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.window.ITaskOrganizerController
      public void updateCameraCompatControlState(WindowContainerToken task, int state)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ITaskOrganizerController.DESCRIPTOR);
          _data.writeTypedObject(task, 0);
          _data.writeInt(state);
          this.mRemote.transact(11, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.window.ITaskOrganizerController
      public void setOrientationRequestPolicy(
          boolean isIgnoreOrientationRequestDisabled, int[] fromOrientations, int[] toOrientations)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ITaskOrganizerController.DESCRIPTOR);
          _data.writeBoolean(isIgnoreOrientationRequestDisabled);
          _data.writeIntArray(fromOrientations);
          _data.writeIntArray(toOrientations);
          this.mRemote.transact(12, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.window.ITaskOrganizerController
      public void setFreeformTaskSurfaceOverlappedWithNavi(
          WindowContainerToken task, boolean overlap) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ITaskOrganizerController.DESCRIPTOR);
          _data.writeTypedObject(task, 0);
          _data.writeBoolean(overlap);
          this.mRemote.transact(13, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.window.ITaskOrganizerController
      public float getFreeformTaskOpacity(int taskId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ITaskOrganizerController.DESCRIPTOR);
          _data.writeInt(taskId);
          this.mRemote.transact(14, _data, _reply, 0);
          _reply.readException();
          float _result = _reply.readFloat();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.window.ITaskOrganizerController
      public void setFreeformTaskOpacity(int taskId, float alpha) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ITaskOrganizerController.DESCRIPTOR);
          _data.writeInt(taskId);
          _data.writeFloat(alpha);
          this.mRemote.transact(15, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.window.ITaskOrganizerController
      public boolean togglePinTaskState(int taskId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ITaskOrganizerController.DESCRIPTOR);
          _data.writeInt(taskId);
          this.mRemote.transact(16, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.window.ITaskOrganizerController
      public boolean isPinStateChangeable(int taskId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ITaskOrganizerController.DESCRIPTOR);
          _data.writeInt(taskId);
          this.mRemote.transact(17, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.window.ITaskOrganizerController
      public boolean isKeepScreenOn(int taskId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(ITaskOrganizerController.DESCRIPTOR);
          _data.writeInt(taskId);
          this.mRemote.transact(18, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    @Override // android.p009os.Binder
    public int getMaxTransactionId() {
      return 17;
    }
  }
}
