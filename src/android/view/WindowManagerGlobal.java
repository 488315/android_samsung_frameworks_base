package android.view;

import android.animation.ValueAnimator;
import android.app.ActivityThread;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
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
import android.view.IRotationWatcher;
import android.view.IWindowManager;
import android.view.IWindowSessionCallback;
import android.view.ViewGroup;
import android.view.ViewRootImpl;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import android.view.inputmethod.InputMethodManager;
import android.window.ITrustedPresentationListener;
import android.window.InputTransferToken;
import android.window.TrustedPresentationThresholds;
import com.android.internal.R;
import com.android.internal.policy.DecorView;
import com.android.internal.policy.PhoneWindow;
import com.android.internal.util.FastPrintWriter;
import com.samsung.android.rune.CoreRune;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

/* loaded from: classes4.dex */
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
    public static final int ADD_STARTING_NOT_NEEDED = -6;
    private static final int LOG_WINDOW_COUNT = 50;
    private static final int MAX_ADD_REPEAT_COUNT = 4000;
    private static final int MAX_WINDOW_COUNT = 300;
    public static final int RELAYOUT_INSETS_PENDING = 1;
    public static final int RELAYOUT_RES_CANCEL_AND_REDRAW = 16;
    public static final int RELAYOUT_RES_CONSUME_ALWAYS_SYSTEM_BARS = 8;
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
    private final ListenerGroup<List<View>> mWindowViewsListenerGroup = new ListenerGroup<>(new ArrayList());
    private final ArrayList<ViewRootImpl> mRoots = new ArrayList<>();
    private final ArrayList<WindowManager.LayoutParams> mParams = new ArrayList<>();
    private final ArraySet<View> mDyingViews = new ArraySet<>();
    private final ArrayList<ViewRootImpl> mWindowlessRoots = new ArrayList<>();
    private final TrustedPresentationListener mTrustedPresentationListener = new TrustedPresentationListener();
    private final SparseArray<SurfaceControlInputReceiverInfo> mSurfaceControlInputReceivers = new SparseArray<>();
    private long mLastAddViewTime = 0;
    private int mAddRepeatCount = 0;

    private WindowManagerGlobal() {
    }

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

    public static void setWindowManagerServiceForSystemProcess(IWindowManager iWindowManager) {
        sWindowManagerService = iWindowManager;
    }

    public static IWindowManager getWindowManagerService() {
        IWindowManager iWindowManager;
        IWindowManager iWindowManager2 = sWindowManagerService;
        if (iWindowManager2 != null) {
            return iWindowManager2;
        }
        synchronized (WindowManagerGlobal.class) {
            if (sWindowManagerService == null) {
                IWindowManager iWindowManagerAsInterface = IWindowManager.Stub.asInterface(ServiceManager.getService(Context.WINDOW_SERVICE));
                sWindowManagerService = iWindowManagerAsInterface;
                if (iWindowManagerAsInterface != null) {
                    try {
                        ValueAnimator.setDurationScale(iWindowManagerAsInterface.getCurrentAnimatorScale());
                    } catch (RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
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
                    sWindowSession = getWindowManagerService().openSession(new IWindowSessionCallback.Stub() { // from class: android.view.WindowManagerGlobal.1
                        @Override // android.view.IWindowSessionCallback
                        public void onAnimatorScaleChanged(float f) {
                            ValueAnimator.setDurationScale(f);
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
        String[] strArr;
        synchronized (this.mLock) {
            int size = this.mRoots.size();
            int size2 = this.mWindowlessRoots.size();
            strArr = new String[size + size2];
            for (int i = 0; i < size; i++) {
                strArr[i] = getWindowName(this.mRoots.get(i));
            }
            for (int i2 = 0; i2 < size2; i2++) {
                strArr[i2 + size] = getWindowName(this.mWindowlessRoots.get(i2));
            }
        }
        return strArr;
    }

    public ArrayList<ViewRootImpl> getRootViews(IBinder iBinder) {
        ArrayList<ViewRootImpl> arrayList = new ArrayList<>();
        synchronized (this.mLock) {
            int size = this.mRoots.size();
            for (int i = 0; i < size; i++) {
                WindowManager.LayoutParams layoutParams = this.mParams.get(i);
                if (layoutParams.token != null) {
                    if (layoutParams.token != iBinder) {
                        if (layoutParams.type >= 1000 && layoutParams.type <= 1999) {
                            for (int i2 = 0; i2 < size; i2++) {
                                View view = this.mViews.get(i2);
                                WindowManager.LayoutParams layoutParams2 = this.mParams.get(i2);
                                if (layoutParams.token == view.getWindowToken() && layoutParams2.token == iBinder) {
                                    arrayList.add(this.mRoots.get(i));
                                    break;
                                    break;
                                }
                            }
                        }
                    } else {
                        arrayList.add(this.mRoots.get(i));
                        break;
                    }
                }
            }
        }
        return arrayList;
    }

    public ArrayList<View> getWindowViews() {
        ArrayList<View> arrayList;
        synchronized (this.mLock) {
            arrayList = new ArrayList<>(this.mViews);
        }
        return arrayList;
    }

    public void addWindowViewsListener(Executor executor, Consumer<List<View>> consumer) {
        synchronized (this.mLock) {
            if (this.mWindowViewsListenerGroup.isConsumerPresent(consumer)) {
                return;
            }
            this.mWindowViewsListenerGroup.addListener(executor, consumer);
        }
    }

    public void removeWindowViewsListener(Consumer<List<View>> consumer) {
        synchronized (this.mLock) {
            this.mWindowViewsListenerGroup.removeListener(consumer);
        }
    }

    public View getWindowView(IBinder iBinder) {
        synchronized (this.mLock) {
            int size = this.mViews.size();
            for (int i = 0; i < size; i++) {
                View view = this.mViews.get(i);
                if (view.getWindowToken() == iBinder) {
                    return view;
                }
            }
            return null;
        }
    }

    public View getRootView(String str) {
        synchronized (this.mLock) {
            for (int size = this.mRoots.size() - 1; size >= 0; size--) {
                ViewRootImpl viewRootImpl = this.mRoots.get(size);
                if (str.equals(getWindowName(viewRootImpl))) {
                    return viewRootImpl.getView();
                }
            }
            for (int size2 = this.mWindowlessRoots.size() - 1; size2 >= 0; size2--) {
                ViewRootImpl viewRootImpl2 = this.mWindowlessRoots.get(size2);
                if (str.equals(getWindowName(viewRootImpl2))) {
                    return viewRootImpl2.getView();
                }
            }
            return null;
        }
    }

    public void addView(View view, ViewGroup.LayoutParams layoutParams, Display display, Window window, int i) {
        View view2;
        ViewRootImpl viewRootImpl;
        if (view == null) {
            throw new IllegalArgumentException("view must not be null");
        }
        if (display == null) {
            throw new IllegalArgumentException("display must not be null");
        }
        if (!(layoutParams instanceof WindowManager.LayoutParams)) {
            throw new IllegalArgumentException("Params must be WindowManager.LayoutParams");
        }
        WindowManager.LayoutParams layoutParams2 = (WindowManager.LayoutParams) layoutParams;
        int i2 = 0;
        if (layoutParams2.type != 3) {
            long jUptimeMillis = SystemClock.uptimeMillis();
            long j = this.mLastAddViewTime + 50;
            this.mLastAddViewTime = jUptimeMillis;
            if (jUptimeMillis < j) {
                int i3 = this.mAddRepeatCount;
                if (i3 > 4000) {
                    throw new IllegalStateException("Add view repeat count is over!!");
                }
                this.mAddRepeatCount = i3 + 1;
            } else {
                this.mAddRepeatCount = 0;
            }
        }
        if (this.mViews.size() >= 300) {
            synchronized (this.mLock) {
                int size = this.mViews.size() - 1;
                while (size >= 0 && i2 < 50) {
                    Log.d(TAG, "addedView(" + size + NavigationBarInflaterView.KEY_CODE_END + this.mViews.get(size));
                    if (this.mParams.get(size) != null) {
                        Log.d(TAG, "addedParams(" + size + NavigationBarInflaterView.KEY_CODE_END + this.mParams.get(size) + " / Title: " + ((Object) this.mParams.get(size).getTitle()));
                    }
                    size--;
                    i2++;
                }
            }
            throw new IllegalStateException("window count is over max!!");
        }
        Context context = view.getContext();
        if (window != null) {
            window.adjustLayoutParamsForSubWindow(layoutParams2);
        } else if (context != null && (context.getApplicationInfo().flags & 536870912) != 0) {
            layoutParams2.flags |= 16777216;
        }
        if (context != null && layoutParams2.type > 99) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(R.styleable.Window);
            if (PhoneWindow.isOptingOutEdgeToEdgeEnforcement(context.getApplicationInfo(), true, typedArrayObtainStyledAttributes)) {
                layoutParams2.privateFlags |= 67108864;
            }
            typedArrayObtainStyledAttributes.recycle();
        }
        if (layoutParams2.type != 1 && layoutParams2.type != 3) {
            Log.i(TAG, "WindowManagerGlobal#addView, ty=" + layoutParams2.type + ", view=" + view + ", caller=" + Debug.getCallers(3));
        }
        synchronized (this.mLock) {
            if (this.mSystemPropertyUpdater == null) {
                Runnable runnable = new Runnable() { // from class: android.view.WindowManagerGlobal.2
                    @Override // java.lang.Runnable
                    public void run() {
                        synchronized (WindowManagerGlobal.this.mLock) {
                            for (int size2 = WindowManagerGlobal.this.mRoots.size() - 1; size2 >= 0; size2--) {
                                ((ViewRootImpl) WindowManagerGlobal.this.mRoots.get(size2)).loadSystemProperties();
                            }
                        }
                    }
                };
                this.mSystemPropertyUpdater = runnable;
                SystemProperties.addChangeCallback(runnable);
            }
            int iFindViewLocked = findViewLocked(view, false);
            if (iFindViewLocked >= 0) {
                if (this.mDyingViews.contains(view)) {
                    this.mRoots.get(iFindViewLocked).doDie();
                } else {
                    throw new IllegalStateException("View " + view + " has already been added to the window manager.");
                }
            }
            IWindowSession windowSession = null;
            if (layoutParams2.type < 1000 || layoutParams2.type > 1999) {
                view2 = null;
            } else {
                int size2 = this.mViews.size();
                view2 = null;
                for (int i4 = 0; i4 < size2; i4++) {
                    if (this.mRoots.get(i4).mWindow.asBinder() == layoutParams2.token) {
                        view2 = this.mViews.get(i4);
                    }
                }
            }
            if (layoutParams2.token != null && view2 == null) {
                while (true) {
                    if (i2 >= this.mWindowlessRoots.size()) {
                        break;
                    }
                    ViewRootImpl viewRootImpl2 = this.mWindowlessRoots.get(i2);
                    if (viewRootImpl2.getWindowToken() == layoutParams2.token) {
                        windowSession = viewRootImpl2.getWindowSession();
                        break;
                    }
                    i2++;
                }
            }
            if (windowSession == null) {
                viewRootImpl = new ViewRootImpl(view.getContext(), display);
            } else {
                viewRootImpl = new ViewRootImpl(view.getContext(), display, windowSession, new WindowlessWindowLayout());
            }
            if (ActivityThread.isFixedAppContextDisplay() && display.getDisplayId() != 0) {
                if (viewRootImpl.mContext.equals(ActivityThread.currentActivityThread().getApplication())) {
                    throw new IllegalArgumentException("bad display id : " + display.getDisplayId());
                }
            }
            view.setLayoutParams(layoutParams2);
            this.mViews.add(view);
            this.mRoots.add(viewRootImpl);
            this.mParams.add(layoutParams2);
            try {
                viewRootImpl.setView(view, layoutParams2, view2, i);
                this.mWindowViewsListenerGroup.accept(getWindowViews());
            } catch (RuntimeException e) {
                Log.e(TAG, "Couldn't add view: " + view, e);
                if (iFindViewLocked < 0) {
                    iFindViewLocked = this.mViews.size() - 1;
                }
                if (iFindViewLocked >= 0) {
                    removeViewLocked(iFindViewLocked, true);
                }
                throw e;
            }
        }
    }

    public void updateViewLayout(View view, ViewGroup.LayoutParams layoutParams) {
        if (view == null) {
            throw new IllegalArgumentException("view must not be null");
        }
        if (!(layoutParams instanceof WindowManager.LayoutParams)) {
            throw new IllegalArgumentException("Params must be WindowManager.LayoutParams");
        }
        WindowManager.LayoutParams layoutParams2 = (WindowManager.LayoutParams) layoutParams;
        view.setLayoutParams(layoutParams2);
        if (view instanceof DecorView) {
            ((DecorView) view).updateElevationIfNeeded();
        }
        synchronized (this.mLock) {
            int iFindViewLocked = findViewLocked(view, true);
            ViewRootImpl viewRootImpl = this.mRoots.get(iFindViewLocked);
            this.mParams.remove(iFindViewLocked);
            this.mParams.add(iFindViewLocked, layoutParams2);
            viewRootImpl.setLayoutParams(layoutParams2, false);
        }
    }

    public void removeView(View view, boolean z) {
        if (view == null) {
            throw new IllegalArgumentException("view must not be null");
        }
        synchronized (this.mLock) {
            int iFindViewLocked = findViewLocked(view, true);
            View view2 = this.mRoots.get(iFindViewLocked).getView();
            removeViewLocked(iFindViewLocked, z);
            if (view2 != view) {
                throw new IllegalStateException("Calling with view " + view + " but the ViewAncestor is attached to " + view2);
            }
        }
    }

    public void closeAll(IBinder iBinder, String str, String str2) {
        closeAllExceptView(iBinder, null, str, str2);
    }

    public void closeAllExceptView(IBinder iBinder, View view, String str, String str2) {
        synchronized (this.mLock) {
            int size = this.mViews.size();
            for (int i = 0; i < size; i++) {
                if ((view == null || this.mViews.get(i) != view) && (iBinder == null || this.mParams.get(i).token == iBinder)) {
                    ViewRootImpl viewRootImpl = this.mRoots.get(i);
                    if (str != null) {
                        WindowLeaked windowLeaked = new WindowLeaked(str2 + " " + str + " has leaked window " + viewRootImpl.getView() + " that was originally added here");
                        windowLeaked.setStackTrace(viewRootImpl.getLocation().getStackTrace());
                        Log.e(TAG, "", windowLeaked);
                    }
                    removeViewLocked(i, false);
                }
            }
        }
    }

    private void removeViewLocked(int i, boolean z) {
        int i2;
        ViewRootImpl viewRootImpl = this.mRoots.get(i);
        View view = viewRootImpl.getView();
        if (viewRootImpl != null && (i2 = viewRootImpl.mWindowAttributes.type) != 1 && i2 != 3) {
            Log.i(TAG, "WindowManagerGlobal#removeView, ty=" + i2 + ", view=" + view + ", caller=" + Debug.getCallers(3));
        }
        if (viewRootImpl != null) {
            viewRootImpl.getImeFocusController().onWindowDismissed();
        }
        boolean zDie = viewRootImpl.die(z);
        if (view != null) {
            view.assignParent(null);
            if (zDie) {
                this.mDyingViews.add(view);
            }
        }
    }

    void doRemoveView(ViewRootImpl viewRootImpl) {
        boolean zIsEmpty;
        synchronized (this.mLock) {
            int iIndexOf = this.mRoots.indexOf(viewRootImpl);
            if (iIndexOf >= 0) {
                this.mRoots.remove(iIndexOf);
                this.mParams.remove(iIndexOf);
                this.mDyingViews.remove(this.mViews.remove(iIndexOf));
            }
            zIsEmpty = this.mRoots.isEmpty();
            this.mWindowViewsListenerGroup.accept(getWindowViews());
        }
        if (zIsEmpty) {
            InsetsAnimationThread.release();
        }
    }

    private int findViewLocked(View view, boolean z) {
        int iIndexOf = this.mViews.indexOf(view);
        if (!z || iIndexOf >= 0) {
            return iIndexOf;
        }
        throw new IllegalArgumentException("View=" + view + " not attached to window manager");
    }

    public void trimMemory(int i) {
        if (CoreRune.GFW_DEBUG_DISABLE_HWRENDERING) {
            return;
        }
        ThreadedRenderer.trimMemory(i);
    }

    public void trimCaches(int i) {
        ThreadedRenderer.trimCaches(i);
    }

    public void dumpGfxInfo(FileDescriptor fileDescriptor, String[] strArr) {
        FastPrintWriter fastPrintWriter = new FastPrintWriter(new FileOutputStream(fileDescriptor));
        try {
            synchronized (this.mLock) {
                int size = this.mViews.size();
                fastPrintWriter.println("Profile data in ms:");
                for (int i = 0; i < size; i++) {
                    ViewRootImpl viewRootImpl = this.mRoots.get(i);
                    fastPrintWriter.printf("\n\t%s (visibility=%d)", getWindowName(viewRootImpl), Integer.valueOf(viewRootImpl.getHostVisibility()));
                    ThreadedRenderer threadedRenderer = viewRootImpl.getView().mAttachInfo.mThreadedRenderer;
                    if (threadedRenderer != null) {
                        threadedRenderer.dumpGfxInfo(fastPrintWriter, fileDescriptor, strArr);
                    }
                }
                fastPrintWriter.println("\nView hierarchy:\n");
                ViewRootImpl.GfxInfo gfxInfo = new ViewRootImpl.GfxInfo();
                for (int i2 = 0; i2 < size; i2++) {
                    ViewRootImpl viewRootImpl2 = this.mRoots.get(i2);
                    ViewRootImpl.GfxInfo gfxInfo2 = viewRootImpl2.getGfxInfo();
                    gfxInfo.add(gfxInfo2);
                    fastPrintWriter.printf("  %s\n  %d views, %.2f kB of render nodes", getWindowName(viewRootImpl2), Integer.valueOf(gfxInfo2.viewCount), Float.valueOf(gfxInfo2.renderNodeMemoryUsage / 1024.0f));
                    fastPrintWriter.printf("\n\n", new Object[0]);
                }
                fastPrintWriter.printf("\nTotal %-15s: %d\n", "ViewRootImpl", Integer.valueOf(size));
                fastPrintWriter.printf("Total %-15s: %d\n", "attached Views", Integer.valueOf(gfxInfo.viewCount));
                fastPrintWriter.printf("Total %-15s: %.2f kB (used) / %.2f kB (capacity)\n\n", "RenderNode", Float.valueOf(gfxInfo.renderNodeMemoryUsage / 1024.0f), Float.valueOf(gfxInfo.renderNodeMemoryAllocated / 1024.0f));
                ActivityThread activityThreadCurrentActivityThread = ActivityThread.currentActivityThread();
                if (activityThreadCurrentActivityThread != null) {
                    activityThreadCurrentActivityThread.dumpProcessAdjustmentInfo(fastPrintWriter);
                }
            }
        } finally {
            fastPrintWriter.flush();
        }
    }

    private static String getWindowName(ViewRootImpl viewRootImpl) {
        return ((Object) viewRootImpl.mWindowAttributes.getTitle()) + "/" + viewRootImpl.getClass().getName() + '@' + Integer.toHexString(viewRootImpl.hashCode());
    }

    public void setStoppedState(IBinder iBinder, final boolean z) {
        ArrayList arrayList;
        synchronized (this.mLock) {
            arrayList = null;
            for (int size = this.mViews.size() - 1; size >= 0; size--) {
                if (iBinder == null || this.mParams.get(size).token == iBinder) {
                    ViewRootImpl viewRootImpl = this.mRoots.get(size);
                    if (viewRootImpl.mThread == Thread.currentThread()) {
                        viewRootImpl.setWindowStopped(z);
                    } else {
                        if (arrayList == null) {
                            arrayList = new ArrayList();
                        }
                        arrayList.add(viewRootImpl);
                    }
                    setStoppedState(viewRootImpl.mAttachInfo.mWindowToken, z);
                }
            }
        }
        if (arrayList != null) {
            for (int size2 = arrayList.size() - 1; size2 >= 0; size2--) {
                final ViewRootImpl viewRootImpl2 = (ViewRootImpl) arrayList.get(size2);
                viewRootImpl2.mHandler.runWithScissors(new Runnable() { // from class: android.view.WindowManagerGlobal$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        viewRootImpl2.setWindowStopped(z);
                    }
                }, 0L);
            }
        }
    }

    public void reportNewConfiguration(Configuration configuration) {
        synchronized (this.mLock) {
            int size = this.mViews.size();
            Configuration configuration2 = new Configuration(configuration);
            for (int i = 0; i < size; i++) {
                this.mRoots.get(i).requestUpdateConfiguration(configuration2);
            }
        }
    }

    public void changeCanvasOpacity(IBinder iBinder, boolean z) {
        if (iBinder == null) {
            return;
        }
        synchronized (this.mLock) {
            for (int size = this.mParams.size() - 1; size >= 0; size--) {
                if (this.mParams.get(size).token == iBinder) {
                    this.mRoots.get(size).changeCanvasOpacity(z);
                    return;
                }
            }
        }
    }

    public SurfaceControl mirrorWallpaperSurface(int i) {
        try {
            return getWindowManagerService().mirrorWallpaperSurface(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerProposedRotationListener(IBinder iBinder, Executor executor, final IntConsumer intConsumer) {
        ProposedRotationListenerDelegate proposedRotationListenerDelegate;
        synchronized (this.mLock) {
            if (this.mProposedRotationListenerMap == null) {
                this.mProposedRotationListenerMap = new WeakHashMap<>(1);
            }
            final ProposedRotationListenerDelegate proposedRotationListenerDelegate2 = this.mProposedRotationListenerMap.get(iBinder);
            if (proposedRotationListenerDelegate2 == null) {
                WeakHashMap<IBinder, ProposedRotationListenerDelegate> weakHashMap = this.mProposedRotationListenerMap;
                proposedRotationListenerDelegate = new ProposedRotationListenerDelegate();
                weakHashMap.put(iBinder, proposedRotationListenerDelegate);
            } else {
                proposedRotationListenerDelegate = proposedRotationListenerDelegate2;
            }
            if (proposedRotationListenerDelegate.add(executor, intConsumer)) {
                if (proposedRotationListenerDelegate2 != null) {
                    executor.execute(new Runnable() { // from class: android.view.WindowManagerGlobal$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            intConsumer.accept(proposedRotationListenerDelegate2.mLastRotation);
                        }
                    });
                    return;
                }
                try {
                    proposedRotationListenerDelegate.onRotationChanged(getWindowManagerService().registerProposedRotationListener(iBinder, proposedRotationListenerDelegate));
                } catch (RemoteException e) {
                    throw e.rethrowFromSystemServer();
                }
            }
        }
    }

    public void unregisterProposedRotationListener(IBinder iBinder, IntConsumer intConsumer) {
        synchronized (this.mLock) {
            WeakHashMap<IBinder, ProposedRotationListenerDelegate> weakHashMap = this.mProposedRotationListenerMap;
            if (weakHashMap == null) {
                return;
            }
            ProposedRotationListenerDelegate proposedRotationListenerDelegate = weakHashMap.get(iBinder);
            if (proposedRotationListenerDelegate == null) {
                return;
            }
            if (proposedRotationListenerDelegate.remove(intConsumer)) {
                this.mProposedRotationListenerMap.remove(iBinder);
                try {
                    getWindowManagerService().removeRotationWatcher(proposedRotationListenerDelegate);
                } catch (RemoteException e) {
                    e.rethrowFromSystemServer();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
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

            ListenerWrapper(Executor executor, IntConsumer intConsumer) {
                this.mExecutor = executor;
                this.mListener = new WeakReference<>(intConsumer);
            }
        }

        boolean add(Executor executor, IntConsumer intConsumer) {
            for (int size = this.mListeners.size() - 1; size >= 0; size--) {
                if (this.mListeners.get(size).mListener.get() == intConsumer) {
                    return false;
                }
            }
            this.mListeners.add(new ListenerWrapper(executor, intConsumer));
            this.mListenerArray = (ListenerWrapper[]) this.mListeners.toArray(new ListenerWrapper[0]);
            return true;
        }

        boolean remove(IntConsumer intConsumer) {
            int size = this.mListeners.size();
            do {
                size--;
                if (size < 0) {
                    return false;
                }
            } while (this.mListeners.get(size).mListener.get() != intConsumer);
            this.mListeners.remove(size);
            this.mListenerArray = (ListenerWrapper[]) this.mListeners.toArray(new ListenerWrapper[0]);
            return this.mListeners.isEmpty();
        }

        @Override // android.view.IRotationWatcher
        public void onRotationChanged(final int i) {
            this.mLastRotation = i;
            boolean z = false;
            for (ListenerWrapper listenerWrapper : this.mListenerArray) {
                final IntConsumer intConsumer = listenerWrapper.mListener.get();
                if (intConsumer != null) {
                    listenerWrapper.mExecutor.execute(new Runnable() { // from class: android.view.WindowManagerGlobal$ProposedRotationListenerDelegate$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            intConsumer.accept(i);
                        }
                    });
                    z = true;
                }
            }
            if (z) {
                return;
            }
            try {
                WindowManagerGlobal.getWindowManagerService().removeRotationWatcher(this);
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }
    }

    public void registerTrustedPresentationListener(IBinder iBinder, TrustedPresentationThresholds trustedPresentationThresholds, Executor executor, Consumer<Boolean> consumer) {
        this.mTrustedPresentationListener.addListener(iBinder, trustedPresentationThresholds, consumer, executor);
    }

    public void unregisterTrustedPresentationListener(Consumer<Boolean> consumer) {
        this.mTrustedPresentationListener.removeListener(consumer);
    }

    private static InputChannel createInputChannel(IBinder iBinder, InputTransferToken inputTransferToken, SurfaceControl surfaceControl, InputTransferToken inputTransferToken2) {
        InputChannel inputChannel = new InputChannel();
        try {
            getWindowSession().grantInputChannel(-1, surfaceControl, iBinder, inputTransferToken, 0, 0, 2, 0, null, inputTransferToken2, surfaceControl.getName(), inputChannel);
            return inputChannel;
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to create input channel", e);
            e.rethrowAsRuntimeException();
            return inputChannel;
        }
    }

    private static void removeInputChannel(IBinder iBinder) {
        try {
            getWindowSession().remove(iBinder);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to remove input channel", e);
            e.rethrowAsRuntimeException();
        }
    }

    InputTransferToken registerBatchedSurfaceControlInputReceiver(InputTransferToken inputTransferToken, SurfaceControl surfaceControl, Choreographer choreographer, final SurfaceControlInputReceiver surfaceControlInputReceiver) {
        Binder binder = new Binder();
        InputTransferToken inputTransferToken2 = new InputTransferToken();
        InputChannel inputChannelCreateInputChannel = createInputChannel(binder, inputTransferToken, surfaceControl, inputTransferToken2);
        synchronized (this.mSurfaceControlInputReceivers) {
            this.mSurfaceControlInputReceivers.put(surfaceControl.getLayerId(), new SurfaceControlInputReceiverInfo(binder, new BatchedInputEventReceiver(this, inputChannelCreateInputChannel, choreographer.getLooper(), choreographer) { // from class: android.view.WindowManagerGlobal.3
                @Override // android.view.InputEventReceiver
                public void onInputEvent(InputEvent inputEvent) {
                    finishInputEvent(inputEvent, surfaceControlInputReceiver.onInputEvent(inputEvent));
                }
            }));
        }
        return inputTransferToken2;
    }

    InputTransferToken registerUnbatchedSurfaceControlInputReceiver(InputTransferToken inputTransferToken, SurfaceControl surfaceControl, Looper looper, final SurfaceControlInputReceiver surfaceControlInputReceiver) {
        Binder binder = new Binder();
        InputTransferToken inputTransferToken2 = new InputTransferToken();
        InputChannel inputChannelCreateInputChannel = createInputChannel(binder, inputTransferToken, surfaceControl, inputTransferToken2);
        synchronized (this.mSurfaceControlInputReceivers) {
            this.mSurfaceControlInputReceivers.put(surfaceControl.getLayerId(), new SurfaceControlInputReceiverInfo(binder, new InputEventReceiver(this, inputChannelCreateInputChannel, looper) { // from class: android.view.WindowManagerGlobal.4
                @Override // android.view.InputEventReceiver
                public void onInputEvent(InputEvent inputEvent) {
                    finishInputEvent(inputEvent, surfaceControlInputReceiver.onInputEvent(inputEvent));
                }
            }));
        }
        return inputTransferToken2;
    }

    void unregisterSurfaceControlInputReceiver(SurfaceControl surfaceControl) {
        SurfaceControlInputReceiverInfo surfaceControlInputReceiverInfoRemoveReturnOld;
        synchronized (this.mSurfaceControlInputReceivers) {
            surfaceControlInputReceiverInfoRemoveReturnOld = this.mSurfaceControlInputReceivers.removeReturnOld(surfaceControl.getLayerId());
        }
        if (surfaceControlInputReceiverInfoRemoveReturnOld == null) {
            Log.w(TAG, "No registered input event receiver with sc: " + surfaceControl);
        } else {
            removeInputChannel(surfaceControlInputReceiverInfoRemoveReturnOld.mClientToken);
            surfaceControlInputReceiverInfoRemoveReturnOld.mInputEventReceiver.dispose();
        }
    }

    IBinder getSurfaceControlInputClientToken(SurfaceControl surfaceControl) {
        SurfaceControlInputReceiverInfo surfaceControlInputReceiverInfo;
        synchronized (this.mSurfaceControlInputReceivers) {
            surfaceControlInputReceiverInfo = this.mSurfaceControlInputReceivers.get(surfaceControl.getLayerId());
        }
        if (surfaceControlInputReceiverInfo == null) {
            Log.w(TAG, "No registered input event receiver with sc: " + surfaceControl);
            return null;
        }
        return surfaceControlInputReceiverInfo.mClientToken;
    }

    boolean transferTouchGesture(InputTransferToken inputTransferToken, InputTransferToken inputTransferToken2) {
        try {
            return getWindowManagerService().transferTouchGesture(inputTransferToken, inputTransferToken2);
        } catch (RemoteException e) {
            e.rethrowAsRuntimeException();
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    final class TrustedPresentationListener extends ITrustedPresentationListener.Stub {
        private static int sId;
        private final ArrayMap<Consumer<Boolean>, Pair<Integer, Executor>> mListeners;
        private final Object mTplLock;

        private TrustedPresentationListener(WindowManagerGlobal windowManagerGlobal) {
            this.mListeners = new ArrayMap<>();
            this.mTplLock = new Object();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addListener(IBinder iBinder, TrustedPresentationThresholds trustedPresentationThresholds, Consumer<Boolean> consumer, Executor executor) {
            synchronized (this.mTplLock) {
                if (this.mListeners.containsKey(consumer)) {
                    Log.i(WindowManagerGlobal.TAG, "Updating listener " + consumer + " thresholds to " + trustedPresentationThresholds);
                    removeListener(consumer);
                }
                int i = sId;
                sId = i + 1;
                this.mListeners.put(consumer, new Pair<>(Integer.valueOf(i), executor));
                try {
                    WindowManagerGlobal.getWindowManagerService().registerTrustedPresentationListener(iBinder, this, trustedPresentationThresholds, i);
                } catch (RemoteException e) {
                    e.rethrowFromSystemServer();
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void removeListener(Consumer<Boolean> consumer) {
            synchronized (this.mTplLock) {
                Pair<Integer, Executor> pairRemove = this.mListeners.remove(consumer);
                if (pairRemove == null) {
                    Log.i(WindowManagerGlobal.TAG, "listener " + consumer + " does not exist.");
                    return;
                }
                try {
                    WindowManagerGlobal.getWindowManagerService().unregisterTrustedPresentationListener(this, pairRemove.first.intValue());
                } catch (RemoteException e) {
                    e.rethrowFromSystemServer();
                }
            }
        }

        @Override // android.window.ITrustedPresentationListener
        public void onTrustedPresentationChanged(final int[] iArr, final int[] iArr2) {
            final ArrayList arrayList = new ArrayList();
            synchronized (this.mTplLock) {
                this.mListeners.forEach(new BiConsumer() { // from class: android.view.WindowManagerGlobal$TrustedPresentationListener$$ExternalSyntheticLambda0
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        WindowManagerGlobal.TrustedPresentationListener.lambda$onTrustedPresentationChanged$4(iArr, arrayList, iArr2, (Consumer) obj, (Pair) obj2);
                    }
                });
            }
            for (int i = 0; i < arrayList.size(); i++) {
                ((Runnable) arrayList.get(i)).run();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        static /* synthetic */ void lambda$onTrustedPresentationChanged$4(int[] iArr, ArrayList arrayList, int[] iArr2, final Consumer consumer, Pair pair) {
            Integer num = (Integer) pair.first;
            final Executor executor = (Executor) pair.second;
            for (int i : iArr) {
                if (num.intValue() == i) {
                    arrayList.add(new Runnable() { // from class: android.view.WindowManagerGlobal$TrustedPresentationListener$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            executor.execute(new Runnable() { // from class: android.view.WindowManagerGlobal$TrustedPresentationListener$$ExternalSyntheticLambda3
                                @Override // java.lang.Runnable
                                public final void run() {
                                    consumer.accept(true);
                                }
                            });
                        }
                    });
                }
            }
            for (int i2 : iArr2) {
                if (num.intValue() == i2) {
                    arrayList.add(new Runnable() { // from class: android.view.WindowManagerGlobal$TrustedPresentationListener$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            executor.execute(new Runnable() { // from class: android.view.WindowManagerGlobal$TrustedPresentationListener$$ExternalSyntheticLambda4
                                @Override // java.lang.Runnable
                                public final void run() {
                                    consumer.accept(false);
                                }
                            });
                        }
                    });
                }
            }
        }
    }

    public void addWindowlessRoot(ViewRootImpl viewRootImpl) {
        synchronized (this.mLock) {
            this.mWindowlessRoots.add(viewRootImpl);
        }
    }

    public void removeWindowlessRoot(ViewRootImpl viewRootImpl) {
        synchronized (this.mLock) {
            this.mWindowlessRoots.remove(viewRootImpl);
        }
    }

    public void setRecentsAppBehindSystemBars(boolean z) {
        try {
            getWindowManagerService().setRecentsAppBehindSystemBars(z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private static class SurfaceControlInputReceiverInfo {
        final IBinder mClientToken;
        final InputEventReceiver mInputEventReceiver;

        private SurfaceControlInputReceiverInfo(IBinder iBinder, InputEventReceiver inputEventReceiver) {
            this.mClientToken = iBinder;
            this.mInputEventReceiver = inputEventReceiver;
        }
    }
}
