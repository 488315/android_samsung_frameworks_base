package android.view;

import android.animation.ValueAnimator;
import android.app.ActivityThread;
import android.content.Context;
import android.content.res.Configuration;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Binder;
import android.os.Debug;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import android.view.inputmethod.InputMethodManager;
import android.window.ITrustedPresentationListener;
import android.window.InputTransferToken;
import android.window.TrustedPresentationThresholds;

import com.android.internal.policy.DecorView;
import com.android.internal.util.FastPrintWriter;

import com.samsung.android.rune.CoreRune;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.WeakHashMap;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

public final class WindowManagerGlobal {
    public static final int ADD_APP_EXITING = -4;
    public static final int ADD_BAD_APP_TOKEN = -1;
    public static final int ADD_BAD_SUBWINDOW_TOKEN = -2;
    public static final int ADD_DUPLICATE_ADD = -5;
    public static final int ADD_FLAG_ALWAYS_CONSUME_SYSTEM_BARS = 4;
    public static final int ADD_FLAG_APP_VISIBLE = 2;
    public static final int ADD_FLAG_HIGH_REFRESHRATE_RESTRICT = 16777216;
    public static final int ADD_FLAG_IN_TOUCH_MODE = 1;
    public static final int ADD_FLAG_REMOVE_CUTOUT = 2097152;
    public static final int ADD_FLAG_REMOVE_CUTOUT_FOR_DISPATCH = 4194304;
    public static final int ADD_INVALID_DISPLAY = -9;
    public static final int ADD_INVALID_TYPE = -10;
    public static final int ADD_INVALID_USER = -11;
    public static final int ADD_MULTIPLE_SINGLETON = -7;
    public static final int ADD_NOT_APP_TOKEN = -3;
    public static final int ADD_OKAY = 0;
    public static final int ADD_PERMISSION_DENIED = -8;
    private static final int ADD_REPEAT_TIMEOUT = 50;
    public static final int ADD_RES_DEX_COMPAT_MODE = 131072;
    public static final int ADD_STARTING_NOT_NEEDED = -6;
    private static final int LOG_WINDOW_COUNT = 50;
    private static final int MAX_ADD_REPEAT_COUNT = 4000;
    private static final int MAX_WINDOW_COUNT = 300;
    public static final int RELAYOUT_INSETS_PENDING = 1;
    public static final int RELAYOUT_RES_CANCEL_AND_REDRAW = 16;
    public static final int RELAYOUT_RES_CONSUME_ALWAYS_SYSTEM_BARS = 8;
    public static final int RELAYOUT_RES_DEX_COMPAT_MODE = 131072;
    public static final int RELAYOUT_RES_FIRST_TIME = 1;
    public static final int RELAYOUT_RES_REMOVE_CUTOUT = 2097152;
    public static final int RELAYOUT_RES_REMOVE_CUTOUT_FOR_DISPATCH = 4194304;
    public static final int RELAYOUT_RES_SURFACE_CHANGED = 2;
    public static final int RELAYOUT_RES_SURFACE_RESIZED = 4;
    private static final String TAG = "WindowManager";
    private static WindowManagerGlobal sDefaultWindowManager;
    private static IWindowManager sWindowManagerService;
    private static IWindowSession sWindowSession;
    private WeakHashMap<IBinder, ProposedRotationListenerDelegate> mProposedRotationListenerMap;
    private Runnable mSystemPropertyUpdater;
    private final Object mLock = new Object();
    private final ArrayList<View> mViews = new ArrayList<>();
    private final ArrayList<ViewRootImpl> mRoots = new ArrayList<>();
    private final ArrayList<WindowManager.LayoutParams> mParams = new ArrayList<>();
    private final ArraySet<View> mDyingViews = new ArraySet<>();
    private final ArrayList<ViewRootImpl> mWindowlessRoots = new ArrayList<>();
    private final TrustedPresentationListener mTrustedPresentationListener =
            new TrustedPresentationListener();
    private final SparseArray<SurfaceControlInputReceiverInfo> mSurfaceControlInputReceivers =
            new SparseArray<>();
    private long mLastAddViewTime = 0;
    private int mAddRepeatCount = 0;

    private WindowManagerGlobal() {}

    public static void initialize() {
        getWindowManagerService();
    }

    public static WindowManagerGlobal getInstance() {
        WindowManagerGlobal windowManagerGlobal;
        synchronized (WindowManagerGlobal.class) {
            if (sDefaultWindowManager == null) {
                sDefaultWindowManager = new WindowManagerGlobal();
            }
            windowManagerGlobal = sDefaultWindowManager;
        }
        return windowManagerGlobal;
    }

    public static void setWindowManagerServiceForSystemProcess(IWindowManager wms) {
        sWindowManagerService = wms;
    }

    public static IWindowManager getWindowManagerService() {
        IWindowManager iWindowManager;
        if (sWindowManagerService != null) {
            return sWindowManagerService;
        }
        synchronized (WindowManagerGlobal.class) {
            if (sWindowManagerService == null) {
                sWindowManagerService =
                        IWindowManager.Stub.asInterface(
                                ServiceManager.getService(Context.WINDOW_SERVICE));
                try {
                    if (sWindowManagerService != null) {
                        ValueAnimator.setDurationScale(
                                sWindowManagerService.getCurrentAnimatorScale());
                    }
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
            iWindowManager = sWindowManagerService;
        }
        return iWindowManager;
    }

    public static IWindowSession getWindowSession() {
        IWindowSession iWindowSession;
        synchronized (WindowManagerGlobal.class) {
            if (sWindowSession == null) {
                try {
                    InputMethodManager.ensureDefaultInstanceForDefaultDisplayIfNecessary();
                    IWindowManager windowManager = getWindowManagerService();
                    sWindowSession =
                            windowManager.openSession(
                                    new IWindowSessionCallback
                                            .Stub() { // from class:
                                                      // android.view.WindowManagerGlobal.1
                                        @Override // android.view.IWindowSessionCallback
                                        public void onAnimatorScaleChanged(float scale) {
                                            ValueAnimator.setDurationScale(scale);
                                        }
                                    });
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
            iWindowSession = sWindowSession;
        }
        return iWindowSession;
    }

    public static IWindowSession peekWindowSession() {
        IWindowSession iWindowSession;
        synchronized (WindowManagerGlobal.class) {
            iWindowSession = sWindowSession;
        }
        return iWindowSession;
    }

    public String[] getViewRootNames() {
        String[] mViewRoots;
        synchronized (this.mLock) {
            int numRoots = this.mRoots.size();
            int windowlessRoots = this.mWindowlessRoots.size();
            mViewRoots = new String[numRoots + windowlessRoots];
            for (int i = 0; i < numRoots; i++) {
                mViewRoots[i] = getWindowName(this.mRoots.get(i));
            }
            for (int i2 = 0; i2 < windowlessRoots; i2++) {
                mViewRoots[i2 + numRoots] = getWindowName(this.mWindowlessRoots.get(i2));
            }
        }
        return mViewRoots;
    }

    public ArrayList<ViewRootImpl> getRootViews(IBinder token) {
        ArrayList<ViewRootImpl> views = new ArrayList<>();
        synchronized (this.mLock) {
            int numRoots = this.mRoots.size();
            for (int i = 0; i < numRoots; i++) {
                WindowManager.LayoutParams params = this.mParams.get(i);
                if (params.token != null) {
                    if (params.token != token) {
                        boolean isChild = false;
                        if (params.type >= 1000 && params.type <= 1999) {
                            int j = 0;
                            while (true) {
                                if (j >= numRoots) {
                                    break;
                                }
                                View viewj = this.mViews.get(j);
                                WindowManager.LayoutParams paramsj = this.mParams.get(j);
                                if (params.token != viewj.getWindowToken()
                                        || paramsj.token != token) {
                                    j++;
                                } else {
                                    isChild = true;
                                    break;
                                }
                            }
                        }
                        if (!isChild) {}
                    }
                    views.add(this.mRoots.get(i));
                }
            }
        }
        return views;
    }

    public ArrayList<View> getWindowViews() {
        ArrayList<View> arrayList;
        synchronized (this.mLock) {
            arrayList = new ArrayList<>(this.mViews);
        }
        return arrayList;
    }

    public View getWindowView(IBinder windowToken) {
        synchronized (this.mLock) {
            int numViews = this.mViews.size();
            for (int i = 0; i < numViews; i++) {
                View view = this.mViews.get(i);
                if (view.getWindowToken() == windowToken) {
                    return view;
                }
            }
            return null;
        }
    }

    public View getRootView(String name) {
        synchronized (this.mLock) {
            for (int i = this.mRoots.size() - 1; i >= 0; i--) {
                ViewRootImpl root = this.mRoots.get(i);
                if (name.equals(getWindowName(root))) {
                    return root.getView();
                }
            }
            for (int i2 = this.mWindowlessRoots.size() - 1; i2 >= 0; i2--) {
                ViewRootImpl root2 = this.mWindowlessRoots.get(i2);
                if (name.equals(getWindowName(root2))) {
                    return root2.getView();
                }
            }
            return null;
        }
    }

    public void addView(
            View view,
            ViewGroup.LayoutParams params,
            Display display,
            Window parentWindow,
            int userId) {
        ViewRootImpl root;
        View decorView;
        if (view == null) {
            throw new IllegalArgumentException("view must not be null");
        }
        if (display == null) {
            throw new IllegalArgumentException("display must not be null");
        }
        if (!(params instanceof WindowManager.LayoutParams)) {
            throw new IllegalArgumentException("Params must be WindowManager.LayoutParams");
        }
        if (((WindowManager.LayoutParams) params).type != 3) {
            long curTime = SystemClock.uptimeMillis();
            long timeout = this.mLastAddViewTime + 50;
            this.mLastAddViewTime = curTime;
            if (curTime < timeout) {
                if (this.mAddRepeatCount > 4000) {
                    throw new IllegalStateException("Add view repeat count is over!!");
                }
                this.mAddRepeatCount++;
            } else {
                this.mAddRepeatCount = 0;
            }
        }
        if (this.mViews.size() >= 300) {
            synchronized (this.mLock) {
                int i = this.mViews.size() - 1;
                for (int j = 0; i >= 0 && j < 50; j++) {
                    Log.d(
                            TAG,
                            "addedView("
                                    + i
                                    + NavigationBarInflaterView.KEY_CODE_END
                                    + this.mViews.get(i));
                    if (this.mParams.get(i) != null) {
                        Log.d(
                                TAG,
                                "addedParams("
                                        + i
                                        + NavigationBarInflaterView.KEY_CODE_END
                                        + this.mParams.get(i)
                                        + " / Title: "
                                        + ((Object) this.mParams.get(i).getTitle()));
                    }
                    i--;
                }
            }
            throw new IllegalStateException("window count is over max!!");
        }
        WindowManager.LayoutParams wparams = (WindowManager.LayoutParams) params;
        if (parentWindow != null) {
            parentWindow.adjustLayoutParamsForSubWindow(wparams);
        } else {
            Context context = view.getContext();
            if (context != null && (context.getApplicationInfo().flags & 536870912) != 0) {
                wparams.flags |= 16777216;
            }
        }
        if (wparams.type != 1 && wparams.type != 3) {
            Log.i(
                    TAG,
                    "WindowManagerGlobal#addView, ty="
                            + wparams.type
                            + ", view="
                            + view
                            + ", caller="
                            + Debug.getCallers(3));
        }
        View panelParentView = null;
        synchronized (this.mLock) {
            if (this.mSystemPropertyUpdater == null) {
                this.mSystemPropertyUpdater =
                        new Runnable() { // from class: android.view.WindowManagerGlobal.2
                            @Override // java.lang.Runnable
                            public void run() {
                                synchronized (WindowManagerGlobal.this.mLock) {
                                    for (int i2 = WindowManagerGlobal.this.mRoots.size() - 1;
                                            i2 >= 0;
                                            i2--) {
                                        ((ViewRootImpl) WindowManagerGlobal.this.mRoots.get(i2))
                                                .loadSystemProperties();
                                    }
                                }
                            }
                        };
                SystemProperties.addChangeCallback(this.mSystemPropertyUpdater);
            }
            int index = findViewLocked(view, false);
            if (index >= 0) {
                if (this.mDyingViews.contains(view)) {
                    this.mRoots.get(index).doDie();
                } else {
                    throw new IllegalStateException(
                            "View " + view + " has already been added to the window manager.");
                }
            }
            if (wparams.type >= 1000 && wparams.type <= 1999) {
                int count = this.mViews.size();
                for (int i2 = 0; i2 < count; i2++) {
                    if (this.mRoots.get(i2).mWindow.asBinder() == wparams.token) {
                        panelParentView = this.mViews.get(i2);
                    }
                }
            }
            IWindowSession windowlessSession = null;
            if (wparams.token != null && panelParentView == null) {
                int i3 = 0;
                while (true) {
                    if (i3 >= this.mWindowlessRoots.size()) {
                        break;
                    }
                    ViewRootImpl maybeParent = this.mWindowlessRoots.get(i3);
                    if (maybeParent.getWindowToken() != wparams.token) {
                        i3++;
                    } else {
                        windowlessSession = maybeParent.getWindowSession();
                        break;
                    }
                }
            }
            if (windowlessSession == null) {
                root = new ViewRootImpl(view.getContext(), display);
            } else {
                root =
                        new ViewRootImpl(
                                view.getContext(),
                                display,
                                windowlessSession,
                                new WindowlessWindowLayout());
            }
            if (ActivityThread.isFixedAppContextDisplay() && display.getDisplayId() != 0) {
                Context appCtx = ActivityThread.currentActivityThread().getApplication();
                if (root.mContext.equals(appCtx)) {
                    throw new IllegalArgumentException(
                            "bad display id : " + display.getDisplayId());
                }
            }
            view.setLayoutParams(wparams);
            this.mViews.add(view);
            this.mRoots.add(root);
            this.mParams.add(wparams);
            if (parentWindow != null) {
                if (panelParentView != null) {
                    decorView = panelParentView;
                } else {
                    try {
                        decorView = parentWindow.getDecorView();
                    } catch (RuntimeException e) {
                        int viewIndex = index >= 0 ? index : this.mViews.size() - 1;
                        if (viewIndex >= 0) {
                            removeViewLocked(viewIndex, true);
                        }
                        throw e;
                    }
                }
                root.mParentDecorView = decorView;
            }
            root.setView(view, wparams, panelParentView, userId);
        }
    }

    public void updateViewLayout(View view, ViewGroup.LayoutParams params) {
        if (view == null) {
            throw new IllegalArgumentException("view must not be null");
        }
        if (!(params instanceof WindowManager.LayoutParams)) {
            throw new IllegalArgumentException("Params must be WindowManager.LayoutParams");
        }
        WindowManager.LayoutParams wparams = (WindowManager.LayoutParams) params;
        view.setLayoutParams(wparams);
        if (view instanceof DecorView) {
            ((DecorView) view).updateElevationIfNeeded();
        }
        synchronized (this.mLock) {
            int index = findViewLocked(view, true);
            ViewRootImpl root = this.mRoots.get(index);
            this.mParams.remove(index);
            this.mParams.add(index, wparams);
            root.setLayoutParams(wparams, false);
        }
    }

    public void removeView(View view, boolean immediate) {
        if (view == null) {
            throw new IllegalArgumentException("view must not be null");
        }
        synchronized (this.mLock) {
            int index = findViewLocked(view, true);
            View curView = this.mRoots.get(index).getView();
            removeViewLocked(index, immediate);
            if (curView != view) {
                throw new IllegalStateException(
                        "Calling with view "
                                + view
                                + " but the ViewAncestor is attached to "
                                + curView);
            }
        }
    }

    public void closeAll(IBinder token, String who, String what) {
        closeAllExceptView(token, null, who, what);
    }

    public void closeAllExceptView(IBinder token, View view, String who, String what) {
        synchronized (this.mLock) {
            int count = this.mViews.size();
            for (int i = 0; i < count; i++) {
                if ((view == null || this.mViews.get(i) != view)
                        && (token == null || this.mParams.get(i).token == token)) {
                    ViewRootImpl root = this.mRoots.get(i);
                    if (who != null) {
                        WindowLeaked leak =
                                new WindowLeaked(
                                        what
                                                + " "
                                                + who
                                                + " has leaked window "
                                                + root.getView()
                                                + " that was originally added here");
                        leak.setStackTrace(root.getLocation().getStackTrace());
                        Log.e(TAG, "", leak);
                    }
                    removeViewLocked(i, false);
                }
            }
        }
    }

    private void removeViewLocked(int index, boolean immediate) {
        int type;
        ViewRootImpl root = this.mRoots.get(index);
        View view = root.getView();
        if (root != null && (type = root.mWindowAttributes.type) != 1 && type != 3) {
            Log.i(
                    TAG,
                    "WindowManagerGlobal#removeView, ty="
                            + type
                            + ", view="
                            + view
                            + ", caller="
                            + Debug.getCallers(3));
        }
        if (root != null) {
            root.getImeFocusController().onWindowDismissed();
        }
        boolean deferred = root.die(immediate);
        if (view != null) {
            view.assignParent(null);
            if (deferred) {
                this.mDyingViews.add(view);
            }
        }
    }

    void doRemoveView(ViewRootImpl root) {
        boolean allViewsRemoved;
        synchronized (this.mLock) {
            int index = this.mRoots.indexOf(root);
            if (index >= 0) {
                this.mRoots.remove(index);
                this.mParams.remove(index);
                View view = this.mViews.remove(index);
                this.mDyingViews.remove(view);
            }
            allViewsRemoved = this.mRoots.isEmpty();
        }
        if (allViewsRemoved) {
            InsetsAnimationThread.release();
        }
    }

    private int findViewLocked(View view, boolean required) {
        int index = this.mViews.indexOf(view);
        if (required && index < 0) {
            throw new IllegalArgumentException("View=" + view + " not attached to window manager");
        }
        return index;
    }

    public void trimMemory(int level) {
        if (CoreRune.GFW_DEBUG_DISABLE_HWRENDERING) {
            return;
        }
        ThreadedRenderer.trimMemory(level);
    }

    public void trimCaches(int level) {
        ThreadedRenderer.trimCaches(level);
    }

    public void dumpGfxInfo(FileDescriptor fd, String[] args) {
        FileOutputStream fout = new FileOutputStream(fd);
        PrintWriter pw = new FastPrintWriter(fout);
        try {
            synchronized (this.mLock) {
                int count = this.mViews.size();
                pw.println("Profile data in ms:");
                for (int i = 0; i < count; i++) {
                    ViewRootImpl root = this.mRoots.get(i);
                    String name = getWindowName(root);
                    pw.printf(
                            "\n\t%s (visibility=%d)",
                            name, Integer.valueOf(root.getHostVisibility()));
                    ThreadedRenderer renderer = root.getView().mAttachInfo.mThreadedRenderer;
                    if (renderer != null) {
                        renderer.dumpGfxInfo(pw, fd, args);
                    }
                }
                pw.println("\nView hierarchy:\n");
                ViewRootImpl.GfxInfo totals = new ViewRootImpl.GfxInfo();
                for (int i2 = 0; i2 < count; i2++) {
                    ViewRootImpl root2 = this.mRoots.get(i2);
                    ViewRootImpl.GfxInfo info = root2.getGfxInfo();
                    totals.add(info);
                    String name2 = getWindowName(root2);
                    pw.printf(
                            "  %s\n  %d views, %.2f kB of render nodes",
                            name2,
                            Integer.valueOf(info.viewCount),
                            Float.valueOf(info.renderNodeMemoryUsage / 1024.0f));
                    pw.printf("\n\n", new Object[0]);
                }
                pw.printf("\nTotal %-15s: %d\n", "ViewRootImpl", Integer.valueOf(count));
                pw.printf("Total %-15s: %d\n", "attached Views", Integer.valueOf(totals.viewCount));
                pw.printf(
                        "Total %-15s: %.2f kB (used) / %.2f kB (capacity)\n\n",
                        "RenderNode",
                        Float.valueOf(totals.renderNodeMemoryUsage / 1024.0f),
                        Float.valueOf(totals.renderNodeMemoryAllocated / 1024.0f));
                Choreographer choreographer =
                        Looper.myLooper() != null ? Choreographer.getInstance() : null;
                if (choreographer != null) {
                    pw.printf(
                            "Total STB frames rendered : %d\n",
                            Long.valueOf(choreographer.getSTBCount()));
                    if (args.length != 0 && args[args.length - 1].equals("reset")) {
                        choreographer.resetSTBCount();
                    }
                }
                ActivityThread thread = ActivityThread.currentActivityThread();
                if (thread != null) {
                    thread.dumpProcessAdjustmentInfo(pw);
                }
            }
        } finally {
            pw.flush();
        }
    }

    private static String getWindowName(ViewRootImpl root) {
        return ((Object) root.mWindowAttributes.getTitle())
                + "/"
                + root.getClass().getName()
                + '@'
                + Integer.toHexString(root.hashCode());
    }

    public void setStoppedState(IBinder token, final boolean stopped) {
        ArrayList<ViewRootImpl> nonCurrentThreadRoots = null;
        synchronized (this.mLock) {
            int count = this.mViews.size();
            for (int i = count - 1; i >= 0; i--) {
                if (token == null || this.mParams.get(i).token == token) {
                    ViewRootImpl root = this.mRoots.get(i);
                    if (root.mThread == Thread.currentThread()) {
                        root.setWindowStopped(stopped);
                    } else {
                        if (nonCurrentThreadRoots == null) {
                            nonCurrentThreadRoots = new ArrayList<>();
                        }
                        nonCurrentThreadRoots.add(root);
                    }
                    setStoppedState(root.mAttachInfo.mWindowToken, stopped);
                }
            }
        }
        if (nonCurrentThreadRoots != null) {
            for (int i2 = nonCurrentThreadRoots.size() - 1; i2 >= 0; i2--) {
                final ViewRootImpl root2 = nonCurrentThreadRoots.get(i2);
                root2.mHandler.runWithScissors(
                        new Runnable() { // from class:
                                         // android.view.WindowManagerGlobal$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                ViewRootImpl.this.setWindowStopped(stopped);
                            }
                        },
                        0L);
            }
        }
    }

    public void reportNewConfiguration(Configuration config) {
        synchronized (this.mLock) {
            int count = this.mViews.size();
            Configuration config2 = new Configuration(config);
            for (int i = 0; i < count; i++) {
                ViewRootImpl root = this.mRoots.get(i);
                root.requestUpdateConfiguration(config2);
            }
        }
    }

    public void changeCanvasOpacity(IBinder token, boolean opaque) {
        if (token == null) {
            return;
        }
        synchronized (this.mLock) {
            for (int i = this.mParams.size() - 1; i >= 0; i--) {
                if (this.mParams.get(i).token == token) {
                    this.mRoots.get(i).changeCanvasOpacity(opaque);
                    return;
                }
            }
        }
    }

    public SurfaceControl mirrorWallpaperSurface(int displayId) {
        try {
            return getWindowManagerService().mirrorWallpaperSurface(displayId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerProposedRotationListener(
            IBinder contextToken, Executor executor, final IntConsumer listener) {
        synchronized (this.mLock) {
            if (this.mProposedRotationListenerMap == null) {
                this.mProposedRotationListenerMap = new WeakHashMap<>(1);
            }
            final ProposedRotationListenerDelegate delegate =
                    this.mProposedRotationListenerMap.get(contextToken);
            if (delegate == null) {
                WeakHashMap<IBinder, ProposedRotationListenerDelegate> weakHashMap =
                        this.mProposedRotationListenerMap;
                ProposedRotationListenerDelegate proposedRotationListenerDelegate =
                        new ProposedRotationListenerDelegate();
                delegate = proposedRotationListenerDelegate;
                weakHashMap.put(contextToken, proposedRotationListenerDelegate);
            }
            if (delegate.add(executor, listener)) {
                if (delegate != null) {
                    executor.execute(
                            new Runnable() { // from class:
                                             // android.view.WindowManagerGlobal$$ExternalSyntheticLambda1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    listener.accept(delegate.mLastRotation);
                                }
                            });
                    return;
                }
                try {
                    int currentRotation =
                            getWindowManagerService()
                                    .registerProposedRotationListener(contextToken, delegate);
                    delegate.onRotationChanged(currentRotation);
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    public void unregisterProposedRotationListener(IBinder contextToken, IntConsumer listener) {
        synchronized (this.mLock) {
            if (this.mProposedRotationListenerMap == null) {
                return;
            }
            ProposedRotationListenerDelegate delegate =
                    this.mProposedRotationListenerMap.get(contextToken);
            if (delegate == null) {
                return;
            }
            if (delegate.remove(listener)) {
                this.mProposedRotationListenerMap.remove(contextToken);
                try {
                    getWindowManagerService().removeRotationWatcher(delegate);
                } catch (RemoteException e) {
                    e.rethrowFromSystemServer();
                }
            }
        }
    }

    static class ProposedRotationListenerDelegate extends IRotationWatcher.Stub {
        int mLastRotation;
        private volatile ListenerWrapper[] mListenerArray;
        private final ArrayList<ListenerWrapper> mListeners;

        private ProposedRotationListenerDelegate() {
            this.mListeners = new ArrayList<>(1);
        }

        static class ListenerWrapper {
            final Executor mExecutor;
            final WeakReference<IntConsumer> mListener;

            ListenerWrapper(Executor executor, IntConsumer listener) {
                this.mExecutor = executor;
                this.mListener = new WeakReference<>(listener);
            }
        }

        boolean add(Executor executor, IntConsumer listener) {
            for (int i = this.mListeners.size() - 1; i >= 0; i--) {
                if (this.mListeners.get(i).mListener.get() == listener) {
                    return false;
                }
            }
            this.mListeners.add(new ListenerWrapper(executor, listener));
            this.mListenerArray =
                    (ListenerWrapper[]) this.mListeners.toArray(new ListenerWrapper[0]);
            return true;
        }

        boolean remove(IntConsumer listener) {
            int i = this.mListeners.size();
            do {
                i--;
                if (i < 0) {
                    return false;
                }
            } while (this.mListeners.get(i).mListener.get() != listener);
            this.mListeners.remove(i);
            this.mListenerArray =
                    (ListenerWrapper[]) this.mListeners.toArray(new ListenerWrapper[0]);
            return this.mListeners.isEmpty();
        }

        @Override // android.view.IRotationWatcher
        public void onRotationChanged(final int rotation) {
            this.mLastRotation = rotation;
            boolean alive = false;
            for (ListenerWrapper listenerWrapper : this.mListenerArray) {
                final IntConsumer listener = listenerWrapper.mListener.get();
                if (listener != null) {
                    listenerWrapper.mExecutor.execute(
                            new Runnable() { // from class:
                                             // android.view.WindowManagerGlobal$ProposedRotationListenerDelegate$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    listener.accept(rotation);
                                }
                            });
                    alive = true;
                }
            }
            if (!alive) {
                try {
                    WindowManagerGlobal.getWindowManagerService().removeRotationWatcher(this);
                } catch (RemoteException e) {
                    e.rethrowFromSystemServer();
                }
            }
        }
    }

    public void registerTrustedPresentationListener(
            IBinder window,
            TrustedPresentationThresholds thresholds,
            Executor executor,
            Consumer<Boolean> listener) {
        this.mTrustedPresentationListener.addListener(window, thresholds, listener, executor);
    }

    public void unregisterTrustedPresentationListener(Consumer<Boolean> listener) {
        this.mTrustedPresentationListener.removeListener(listener);
    }

    private static InputChannel createInputChannel(
            IBinder clientToken,
            InputTransferToken hostToken,
            SurfaceControl surfaceControl,
            InputTransferToken inputTransferToken) {
        InputChannel inputChannel = new InputChannel();
        try {
            getWindowSession()
                    .grantInputChannel(
                            -1,
                            surfaceControl,
                            clientToken,
                            hostToken,
                            0,
                            0,
                            2,
                            0,
                            null,
                            inputTransferToken,
                            surfaceControl.getName(),
                            inputChannel);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to create input channel", e);
            e.rethrowAsRuntimeException();
        }
        return inputChannel;
    }

    private static void removeInputChannel(IBinder clientToken) {
        try {
            getWindowSession().remove(clientToken);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to remove input channel", e);
            e.rethrowAsRuntimeException();
        }
    }

    InputTransferToken registerBatchedSurfaceControlInputReceiver(
            InputTransferToken hostToken,
            SurfaceControl surfaceControl,
            Choreographer choreographer,
            final SurfaceControlInputReceiver receiver) {
        IBinder clientToken = new Binder();
        InputTransferToken inputTransferToken = new InputTransferToken();
        InputChannel inputChannel =
                createInputChannel(clientToken, hostToken, surfaceControl, inputTransferToken);
        synchronized (this.mSurfaceControlInputReceivers) {
            this.mSurfaceControlInputReceivers.put(
                    surfaceControl.getLayerId(),
                    new SurfaceControlInputReceiverInfo(
                            clientToken,
                            new BatchedInputEventReceiver(
                                    inputChannel,
                                    choreographer.getLooper(),
                                    choreographer) { // from class:
                                                     // android.view.WindowManagerGlobal.3
                                @Override // android.view.InputEventReceiver
                                public void onInputEvent(InputEvent event) {
                                    boolean handled = receiver.onInputEvent(event);
                                    finishInputEvent(event, handled);
                                }
                            }));
        }
        return inputTransferToken;
    }

    InputTransferToken registerUnbatchedSurfaceControlInputReceiver(
            InputTransferToken hostToken,
            SurfaceControl surfaceControl,
            Looper looper,
            final SurfaceControlInputReceiver receiver) {
        IBinder clientToken = new Binder();
        InputTransferToken inputTransferToken = new InputTransferToken();
        InputChannel inputChannel =
                createInputChannel(clientToken, hostToken, surfaceControl, inputTransferToken);
        synchronized (this.mSurfaceControlInputReceivers) {
            this.mSurfaceControlInputReceivers.put(
                    surfaceControl.getLayerId(),
                    new SurfaceControlInputReceiverInfo(
                            clientToken,
                            new InputEventReceiver(
                                    inputChannel,
                                    looper) { // from class: android.view.WindowManagerGlobal.4
                                @Override // android.view.InputEventReceiver
                                public void onInputEvent(InputEvent event) {
                                    boolean handled = receiver.onInputEvent(event);
                                    finishInputEvent(event, handled);
                                }
                            }));
        }
        return inputTransferToken;
    }

    void unregisterSurfaceControlInputReceiver(SurfaceControl surfaceControl) {
        SurfaceControlInputReceiverInfo surfaceControlInputReceiverInfo;
        synchronized (this.mSurfaceControlInputReceivers) {
            surfaceControlInputReceiverInfo =
                    this.mSurfaceControlInputReceivers.removeReturnOld(surfaceControl.getLayerId());
        }
        if (surfaceControlInputReceiverInfo == null) {
            Log.w(TAG, "No registered input event receiver with sc: " + surfaceControl);
        } else {
            removeInputChannel(surfaceControlInputReceiverInfo.mClientToken);
            surfaceControlInputReceiverInfo.mInputEventReceiver.dispose();
        }
    }

    IBinder getSurfaceControlInputClientToken(SurfaceControl surfaceControl) {
        SurfaceControlInputReceiverInfo surfaceControlInputReceiverInfo;
        synchronized (this.mSurfaceControlInputReceivers) {
            surfaceControlInputReceiverInfo =
                    this.mSurfaceControlInputReceivers.get(surfaceControl.getLayerId());
        }
        if (surfaceControlInputReceiverInfo == null) {
            Log.w(TAG, "No registered input event receiver with sc: " + surfaceControl);
            return null;
        }
        return surfaceControlInputReceiverInfo.mClientToken;
    }

    boolean transferTouchGesture(
            InputTransferToken transferFromToken, InputTransferToken transferToToken) {
        try {
            return getWindowManagerService()
                    .transferTouchGesture(transferFromToken, transferToToken);
        } catch (RemoteException e) {
            e.rethrowAsRuntimeException();
            return false;
        }
    }

    final class TrustedPresentationListener extends ITrustedPresentationListener.Stub {
        private static int sId = 0;
        private final ArrayMap<Consumer<Boolean>, Pair<Integer, Executor>> mListeners;
        private final Object mTplLock;

        private TrustedPresentationListener() {
            this.mListeners = new ArrayMap<>();
            this.mTplLock = new Object();
        }

        public void addListener(
                IBinder window,
                TrustedPresentationThresholds thresholds,
                Consumer<Boolean> listener,
                Executor executor) {
            synchronized (this.mTplLock) {
                if (this.mListeners.containsKey(listener)) {
                    Log.i(
                            WindowManagerGlobal.TAG,
                            "Updating listener " + listener + " thresholds to " + thresholds);
                    removeListener(listener);
                }
                int id = sId;
                sId = id + 1;
                this.mListeners.put(listener, new Pair<>(Integer.valueOf(id), executor));
                try {
                    WindowManagerGlobal.getWindowManagerService()
                            .registerTrustedPresentationListener(window, this, thresholds, id);
                } catch (RemoteException e) {
                    e.rethrowFromSystemServer();
                }
            }
        }

        public void removeListener(Consumer<Boolean> listener) {
            synchronized (this.mTplLock) {
                Pair<Integer, Executor> removedListener = this.mListeners.remove(listener);
                if (removedListener == null) {
                    Log.i(WindowManagerGlobal.TAG, "listener " + listener + " does not exist.");
                    return;
                }
                try {
                    WindowManagerGlobal.getWindowManagerService()
                            .unregisterTrustedPresentationListener(
                                    this, removedListener.first.intValue());
                } catch (RemoteException e) {
                    e.rethrowFromSystemServer();
                }
            }
        }

        @Override // android.window.ITrustedPresentationListener
        public void onTrustedPresentationChanged(
                final int[] inTrustedStateListenerIds, final int[] outOfTrustedStateListenerIds) {
            final ArrayList<Runnable> firedListeners = new ArrayList<>();
            synchronized (this.mTplLock) {
                this.mListeners.forEach(
                        new BiConsumer() { // from class:
                                           // android.view.WindowManagerGlobal$TrustedPresentationListener$$ExternalSyntheticLambda0
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                WindowManagerGlobal.TrustedPresentationListener
                                        .lambda$onTrustedPresentationChanged$4(
                                                inTrustedStateListenerIds,
                                                firedListeners,
                                                outOfTrustedStateListenerIds,
                                                (Consumer) obj,
                                                (Pair) obj2);
                            }
                        });
            }
            for (int i = 0; i < firedListeners.size(); i++) {
                firedListeners.get(i).run();
            }
        }

        static /* synthetic */ void lambda$onTrustedPresentationChanged$4(
                int[] inTrustedStateListenerIds,
                ArrayList firedListeners,
                int[] outOfTrustedStateListenerIds,
                final Consumer listener,
                Pair idExecutorPair) {
            Integer listenerId = (Integer) idExecutorPair.first;
            final Executor executor = (Executor) idExecutorPair.second;
            for (int id : inTrustedStateListenerIds) {
                if (listenerId.intValue() == id) {
                    firedListeners.add(
                            new Runnable() { // from class:
                                             // android.view.WindowManagerGlobal$TrustedPresentationListener$$ExternalSyntheticLambda1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    executor.execute(
                                            new Runnable() { // from class:
                                                             // android.view.WindowManagerGlobal$TrustedPresentationListener$$ExternalSyntheticLambda3
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    r1.accept(true);
                                                }
                                            });
                                }
                            });
                }
            }
            for (int id2 : outOfTrustedStateListenerIds) {
                if (listenerId.intValue() == id2) {
                    firedListeners.add(
                            new Runnable() { // from class:
                                             // android.view.WindowManagerGlobal$TrustedPresentationListener$$ExternalSyntheticLambda2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    executor.execute(
                                            new Runnable() { // from class:
                                                             // android.view.WindowManagerGlobal$TrustedPresentationListener$$ExternalSyntheticLambda4
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    r1.accept(false);
                                                }
                                            });
                                }
                            });
                }
            }
        }
    }

    public void addWindowlessRoot(ViewRootImpl impl) {
        synchronized (this.mLock) {
            this.mWindowlessRoots.add(impl);
        }
    }

    public void removeWindowlessRoot(ViewRootImpl impl) {
        synchronized (this.mLock) {
            this.mWindowlessRoots.remove(impl);
        }
    }

    public void setRecentsAppBehindSystemBars(boolean behindSystemBars) {
        try {
            getWindowManagerService().setRecentsAppBehindSystemBars(behindSystemBars);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private static class SurfaceControlInputReceiverInfo {
        final IBinder mClientToken;
        final InputEventReceiver mInputEventReceiver;

        private SurfaceControlInputReceiverInfo(
                IBinder clientToken, InputEventReceiver inputEventReceiver) {
            this.mClientToken = clientToken;
            this.mInputEventReceiver = inputEventReceiver;
        }
    }
}
