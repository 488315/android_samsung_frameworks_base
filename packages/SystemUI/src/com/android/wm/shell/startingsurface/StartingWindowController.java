package com.android.wm.shell.startingsurface;

import android.app.ActivityThread;
import android.app.TaskInfo;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Slog;
import android.util.SparseIntArray;
import androidx.activity.result.ActivityResultRegistry$register$3$$ExternalSyntheticOutline0;
import com.android.internal.util.function.TriConsumer;
import com.android.launcher3.icons.IconProvider;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.ExternalInterfaceBinder;
import com.android.wm.shell.common.RemoteCallable;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SingleInstanceRemoteListener;
import com.android.wm.shell.shared.TransactionPool;
import com.android.wm.shell.startingsurface.SplashscreenContentDrawer;
import com.android.wm.shell.sysui.ShellController;
import com.android.wm.shell.sysui.ShellInit;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class StartingWindowController implements RemoteCallable {
    public final Context mContext;
    public final ShellController mShellController;
    public final ShellTaskOrganizer mShellTaskOrganizer;
    public final ShellExecutor mSplashScreenExecutor;
    public final StartingSurfaceDrawer mStartingSurfaceDrawer;
    public final StartingWindowTypeAlgorithm mStartingWindowTypeAlgorithm;
    public TriConsumer mTaskLaunchingCallback;
    public final StartingSurfaceImpl mImpl = new StartingSurfaceImpl(this, 0);
    public final SparseIntArray mTaskBackgroundColors = new SparseIntArray();

    public class IStartingWindowImpl extends IStartingWindow$Stub implements ExternalInterfaceBinder {
        public static final /* synthetic */ int $r8$clinit = 0;
        public StartingWindowController mController;
        public final SingleInstanceRemoteListener mListener;
        public final StartingWindowController$IStartingWindowImpl$$ExternalSyntheticLambda1 mStartingWindowListener = new TriConsumer() { // from class: com.android.wm.shell.startingsurface.StartingWindowController$IStartingWindowImpl$$ExternalSyntheticLambda1
            public final void accept(Object obj, Object obj2, Object obj3) {
                Integer num = (Integer) obj;
                Integer num2 = (Integer) obj2;
                Integer num3 = (Integer) obj3;
                IInterface iInterface = this.f$0.mListener.mListener;
                if (iInterface == null) {
                    Slog.e("SingleInstanceRemoteListener", "Failed remote call on null listener");
                    return;
                }
                try {
                    IStartingWindowListener$Stub$Proxy iStartingWindowListener$Stub$Proxy = (IStartingWindowListener$Stub$Proxy) iInterface;
                    int iIntValue = num.intValue();
                    int iIntValue2 = num2.intValue();
                    int iIntValue3 = num3.intValue();
                    Parcel parcelObtain = Parcel.obtain(iStartingWindowListener$Stub$Proxy.mRemote);
                    try {
                        parcelObtain.writeInterfaceToken("com.android.wm.shell.startingsurface.IStartingWindowListener");
                        parcelObtain.writeInt(iIntValue);
                        parcelObtain.writeInt(iIntValue2);
                        parcelObtain.writeInt(iIntValue3);
                        iStartingWindowListener$Stub$Proxy.mRemote.transact(1, parcelObtain, null, 1);
                    } finally {
                        parcelObtain.recycle();
                    }
                } catch (RemoteException e) {
                    Slog.e("SingleInstanceRemoteListener", "Failed remote call", e);
                }
            }
        };

        /* JADX WARN: Type inference failed for: r0v0, types: [com.android.wm.shell.startingsurface.StartingWindowController$IStartingWindowImpl$$ExternalSyntheticLambda1] */
        public IStartingWindowImpl(StartingWindowController startingWindowController) {
            this.mController = startingWindowController;
            this.mListener = new SingleInstanceRemoteListener(startingWindowController, new Consumer() { // from class: com.android.wm.shell.startingsurface.StartingWindowController$IStartingWindowImpl$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((StartingWindowController) obj).setStartingWindowListener(this.f$0.mStartingWindowListener);
                }
            }, new StartingWindowController$IStartingWindowImpl$$ExternalSyntheticLambda3());
        }

        @Override // com.android.wm.shell.common.ExternalInterfaceBinder
        public final void invalidate() {
            this.mController = null;
            this.mListener.unregister();
        }
    }

    public class StartingSurfaceImpl {
        public /* synthetic */ StartingSurfaceImpl(StartingWindowController startingWindowController, int i) {
            this();
        }

        public final int getBackgroundColor(TaskInfo taskInfo) {
            synchronized (StartingWindowController.this.mTaskBackgroundColors) {
                try {
                    int iIndexOfKey = StartingWindowController.this.mTaskBackgroundColors.indexOfKey(taskInfo.taskId);
                    if (iIndexOfKey >= 0) {
                        return StartingWindowController.this.mTaskBackgroundColors.valueAt(iIndexOfKey);
                    }
                    SplashscreenWindowCreator splashscreenWindowCreator = StartingWindowController.this.mStartingSurfaceDrawer.mSplashscreenWindowCreator;
                    splashscreenWindowCreator.getClass();
                    ActivityInfo activityInfo = taskInfo.topActivityInfo;
                    int iPeekWindowBGColor = 0;
                    if (activityInfo != null) {
                        String str = activityInfo.packageName;
                        int i = taskInfo.userId;
                        try {
                            Context contextCreatePackageContextAsUser = splashscreenWindowCreator.mContext.createPackageContextAsUser(str, 4, UserHandle.of(i));
                            try {
                                String splashScreenTheme = ActivityThread.getPackageManager().getSplashScreenTheme(str, i);
                                int splashScreenTheme2 = AbsSplashWindowCreator.getSplashScreenTheme(splashScreenTheme != null ? contextCreatePackageContextAsUser.getResources().getIdentifier(splashScreenTheme, null, null) : 0, activityInfo);
                                if (splashScreenTheme2 != contextCreatePackageContextAsUser.getThemeResId()) {
                                    contextCreatePackageContextAsUser.setTheme(splashScreenTheme2);
                                }
                                splashscreenWindowCreator.mSplashscreenContentDrawer.getClass();
                                SplashscreenContentDrawer.SplashScreenWindowAttrs splashScreenWindowAttrs = new SplashscreenContentDrawer.SplashScreenWindowAttrs();
                                SplashscreenContentDrawer.getWindowAttrs(contextCreatePackageContextAsUser, splashScreenWindowAttrs);
                                iPeekWindowBGColor = SplashscreenContentDrawer.peekWindowBGColor(contextCreatePackageContextAsUser, splashScreenWindowAttrs);
                            } catch (RemoteException | RuntimeException e) {
                                Slog.w("ShellStartingWindow", "failed get starting window background color at taskId: " + taskInfo.taskId, e);
                            }
                        } catch (PackageManager.NameNotFoundException e2) {
                            StringBuilder sbM = ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("Failed creating package context with package name ", str, " for user ");
                            sbM.append(taskInfo.userId);
                            Slog.w("ShellStartingWindow", sbM.toString(), e2);
                        }
                    }
                    return iPeekWindowBGColor != 0 ? iPeekWindowBGColor : SplashscreenContentDrawer.getSystemBGColor();
                } finally {
                }
            }
        }

        private StartingSurfaceImpl() {
        }
    }

    public StartingWindowController(Context context, ShellInit shellInit, ShellController shellController, ShellTaskOrganizer shellTaskOrganizer, ShellExecutor shellExecutor, StartingWindowTypeAlgorithm startingWindowTypeAlgorithm, IconProvider iconProvider, TransactionPool transactionPool) {
        this.mContext = context;
        this.mShellController = shellController;
        this.mShellTaskOrganizer = shellTaskOrganizer;
        this.mStartingSurfaceDrawer = new StartingSurfaceDrawer(context, shellExecutor, iconProvider, transactionPool);
        this.mStartingWindowTypeAlgorithm = startingWindowTypeAlgorithm;
        this.mSplashScreenExecutor = shellExecutor;
        shellInit.addInitCallback(new StartingWindowController$$ExternalSyntheticLambda5(this, 1), this);
    }

    @Override // com.android.wm.shell.common.RemoteCallable
    public final Context getContext() {
        return this.mContext;
    }

    @Override // com.android.wm.shell.common.RemoteCallable
    public final ShellExecutor getRemoteCallExecutor() {
        return this.mSplashScreenExecutor;
    }

    public boolean hasStartingWindowListener() {
        return this.mTaskLaunchingCallback != null;
    }

    public void setStartingWindowListener(TriConsumer<Integer, Integer, Integer> triConsumer) {
        this.mTaskLaunchingCallback = triConsumer;
    }
}
