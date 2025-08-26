package android.window;

import android.app.ActivityManager;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.SurfaceControl;

/* loaded from: classes5.dex */
public interface ITaskOrganizer extends IInterface {
    public static final String DESCRIPTOR = "android.window.ITaskOrganizer";

    public static class Default implements ITaskOrganizer {
        @Override // android.window.ITaskOrganizer
        public void addStartingWindow(StartingWindowInfo startingWindowInfo) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.window.ITaskOrganizer
        public void copySplashScreenView(int i) throws RemoteException {
        }

        @Override // android.window.ITaskOrganizer
        public void onAppSplashScreenViewRemoved(int i) throws RemoteException {
        }

        @Override // android.window.ITaskOrganizer
        public void onBackPressedOnTaskRoot(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException {
        }

        @Override // android.window.ITaskOrganizer
        public void onImeDrawnOnTask(int i) throws RemoteException {
        }

        @Override // android.window.ITaskOrganizer
        public void onSplitLayoutChangeRequested(ActivityManager.RunningTaskInfo runningTaskInfo, Bundle bundle) throws RemoteException {
        }

        @Override // android.window.ITaskOrganizer
        public void onTaskAppeared(ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl) throws RemoteException {
        }

        @Override // android.window.ITaskOrganizer
        public void onTaskInfoChanged(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException {
        }

        @Override // android.window.ITaskOrganizer
        public void onTaskVanished(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException {
        }

        @Override // android.window.ITaskOrganizer
        public void preloadSplashScreenAppIcon(ActivityInfo activityInfo, int i, Configuration configuration) throws RemoteException {
        }

        @Override // android.window.ITaskOrganizer
        public void removeStartingWindow(StartingWindowRemovalInfo startingWindowRemovalInfo) throws RemoteException {
        }

        @Override // android.window.ITaskOrganizer
        public void requestAffordanceAnim(ActivityManager.RunningTaskInfo runningTaskInfo, int i) throws RemoteException {
        }

        @Override // android.window.ITaskOrganizer
        public void resetStashedFreeform(int i, boolean z) throws RemoteException {
        }
    }

    void addStartingWindow(StartingWindowInfo startingWindowInfo) throws RemoteException;

    void copySplashScreenView(int i) throws RemoteException;

    void onAppSplashScreenViewRemoved(int i) throws RemoteException;

    void onBackPressedOnTaskRoot(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException;

    void onImeDrawnOnTask(int i) throws RemoteException;

    void onSplitLayoutChangeRequested(ActivityManager.RunningTaskInfo runningTaskInfo, Bundle bundle) throws RemoteException;

    void onTaskAppeared(ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl) throws RemoteException;

    void onTaskInfoChanged(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException;

    void onTaskVanished(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException;

    void preloadSplashScreenAppIcon(ActivityInfo activityInfo, int i, Configuration configuration) throws RemoteException;

    void removeStartingWindow(StartingWindowRemovalInfo startingWindowRemovalInfo) throws RemoteException;

    void requestAffordanceAnim(ActivityManager.RunningTaskInfo runningTaskInfo, int i) throws RemoteException;

    void resetStashedFreeform(int i, boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements ITaskOrganizer {
        static final int TRANSACTION_addStartingWindow = 1;
        static final int TRANSACTION_copySplashScreenView = 3;
        static final int TRANSACTION_onAppSplashScreenViewRemoved = 4;
        static final int TRANSACTION_onBackPressedOnTaskRoot = 8;
        static final int TRANSACTION_onImeDrawnOnTask = 9;
        static final int TRANSACTION_onSplitLayoutChangeRequested = 10;
        static final int TRANSACTION_onTaskAppeared = 5;
        static final int TRANSACTION_onTaskInfoChanged = 7;
        static final int TRANSACTION_onTaskVanished = 6;
        static final int TRANSACTION_preloadSplashScreenAppIcon = 13;
        static final int TRANSACTION_removeStartingWindow = 2;
        static final int TRANSACTION_requestAffordanceAnim = 12;
        static final int TRANSACTION_resetStashedFreeform = 11;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 12;
        }

        public Stub() {
            attachInterface(this, ITaskOrganizer.DESCRIPTOR);
        }

        public static ITaskOrganizer asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ITaskOrganizer.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ITaskOrganizer)) {
                return (ITaskOrganizer) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
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
                    return "resetStashedFreeform";
                case 12:
                    return "requestAffordanceAnim";
                case 13:
                    return "preloadSplashScreenAppIcon";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ITaskOrganizer.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ITaskOrganizer.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    StartingWindowInfo startingWindowInfo = (StartingWindowInfo) parcel.readTypedObject(StartingWindowInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    addStartingWindow(startingWindowInfo);
                    return true;
                case 2:
                    StartingWindowRemovalInfo startingWindowRemovalInfo = (StartingWindowRemovalInfo) parcel.readTypedObject(StartingWindowRemovalInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    removeStartingWindow(startingWindowRemovalInfo);
                    return true;
                case 3:
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    copySplashScreenView(i3);
                    return true;
                case 4:
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onAppSplashScreenViewRemoved(i4);
                    return true;
                case 5:
                    ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) parcel.readTypedObject(ActivityManager.RunningTaskInfo.CREATOR);
                    SurfaceControl surfaceControl = (SurfaceControl) parcel.readTypedObject(SurfaceControl.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTaskAppeared(runningTaskInfo, surfaceControl);
                    return true;
                case 6:
                    ActivityManager.RunningTaskInfo runningTaskInfo2 = (ActivityManager.RunningTaskInfo) parcel.readTypedObject(ActivityManager.RunningTaskInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTaskVanished(runningTaskInfo2);
                    return true;
                case 7:
                    ActivityManager.RunningTaskInfo runningTaskInfo3 = (ActivityManager.RunningTaskInfo) parcel.readTypedObject(ActivityManager.RunningTaskInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onTaskInfoChanged(runningTaskInfo3);
                    return true;
                case 8:
                    ActivityManager.RunningTaskInfo runningTaskInfo4 = (ActivityManager.RunningTaskInfo) parcel.readTypedObject(ActivityManager.RunningTaskInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    onBackPressedOnTaskRoot(runningTaskInfo4);
                    return true;
                case 9:
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onImeDrawnOnTask(i5);
                    return true;
                case 10:
                    ActivityManager.RunningTaskInfo runningTaskInfo5 = (ActivityManager.RunningTaskInfo) parcel.readTypedObject(ActivityManager.RunningTaskInfo.CREATOR);
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSplitLayoutChangeRequested(runningTaskInfo5, bundle);
                    return true;
                case 11:
                    int i6 = parcel.readInt();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    resetStashedFreeform(i6, z);
                    return true;
                case 12:
                    ActivityManager.RunningTaskInfo runningTaskInfo6 = (ActivityManager.RunningTaskInfo) parcel.readTypedObject(ActivityManager.RunningTaskInfo.CREATOR);
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestAffordanceAnim(runningTaskInfo6, i7);
                    return true;
                case 13:
                    ActivityInfo activityInfo = (ActivityInfo) parcel.readTypedObject(ActivityInfo.CREATOR);
                    int i8 = parcel.readInt();
                    Configuration configuration = (Configuration) parcel.readTypedObject(Configuration.CREATOR);
                    parcel.enforceNoDataAvail();
                    preloadSplashScreenAppIcon(activityInfo, i8, configuration);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ITaskOrganizer {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITaskOrganizer.DESCRIPTOR;
            }

            @Override // android.window.ITaskOrganizer
            public void addStartingWindow(StartingWindowInfo startingWindowInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITaskOrganizer.DESCRIPTOR);
                    parcelObtain.writeTypedObject(startingWindowInfo, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.window.ITaskOrganizer
            public void removeStartingWindow(StartingWindowRemovalInfo startingWindowRemovalInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITaskOrganizer.DESCRIPTOR);
                    parcelObtain.writeTypedObject(startingWindowRemovalInfo, 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.window.ITaskOrganizer
            public void copySplashScreenView(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITaskOrganizer.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.window.ITaskOrganizer
            public void onAppSplashScreenViewRemoved(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITaskOrganizer.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.window.ITaskOrganizer
            public void onTaskAppeared(ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITaskOrganizer.DESCRIPTOR);
                    parcelObtain.writeTypedObject(runningTaskInfo, 0);
                    parcelObtain.writeTypedObject(surfaceControl, 0);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.window.ITaskOrganizer
            public void onTaskVanished(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITaskOrganizer.DESCRIPTOR);
                    parcelObtain.writeTypedObject(runningTaskInfo, 0);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.window.ITaskOrganizer
            public void onTaskInfoChanged(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITaskOrganizer.DESCRIPTOR);
                    parcelObtain.writeTypedObject(runningTaskInfo, 0);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.window.ITaskOrganizer
            public void onBackPressedOnTaskRoot(ActivityManager.RunningTaskInfo runningTaskInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITaskOrganizer.DESCRIPTOR);
                    parcelObtain.writeTypedObject(runningTaskInfo, 0);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.window.ITaskOrganizer
            public void onImeDrawnOnTask(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITaskOrganizer.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.window.ITaskOrganizer
            public void onSplitLayoutChangeRequested(ActivityManager.RunningTaskInfo runningTaskInfo, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITaskOrganizer.DESCRIPTOR);
                    parcelObtain.writeTypedObject(runningTaskInfo, 0);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(10, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.window.ITaskOrganizer
            public void resetStashedFreeform(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITaskOrganizer.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(11, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.window.ITaskOrganizer
            public void requestAffordanceAnim(ActivityManager.RunningTaskInfo runningTaskInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITaskOrganizer.DESCRIPTOR);
                    parcelObtain.writeTypedObject(runningTaskInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(12, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.window.ITaskOrganizer
            public void preloadSplashScreenAppIcon(ActivityInfo activityInfo, int i, Configuration configuration) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ITaskOrganizer.DESCRIPTOR);
                    parcelObtain.writeTypedObject(activityInfo, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(configuration, 0);
                    this.mRemote.transact(13, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
