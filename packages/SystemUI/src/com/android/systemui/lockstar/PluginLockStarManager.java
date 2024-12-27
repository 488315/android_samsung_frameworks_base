package com.android.systemui.lockstar;

import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.ViewStub;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardClockSwitchController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.Dumpable;
import com.android.systemui.LsRune;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.lockstar.shortcut.LockStarShortcutController;
import com.android.systemui.lockstar.shortcut.LockStarShortcutDnd;
import com.android.systemui.lockstar.shortcut.LockStarShortcutFlashLight;
import com.android.systemui.lockstar.shortcut.LockStarShortcutTask;
import com.android.systemui.pluginlock.PluginLockBottomAreaCallback;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.util.kotlin.MapUtilsKt;
import com.samsung.systemui.splugins.SPlugin;
import com.samsung.systemui.splugins.SPluginListener;
import com.samsung.systemui.splugins.SPluginManager;
import com.samsung.systemui.splugins.lockstar.LockStarValues;
import com.samsung.systemui.splugins.lockstar.PluginLockStar;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import kotlin.jvm.internal.Intrinsics;

public final class PluginLockStarManager implements SPluginListener, PluginLockStar.PluginLockStarCallback, Dumpable, DisplayLifecycle.Observer, WakefulnessLifecycle.Observer, StatusBarStateController.StateListener {
    public final Context mContext;
    public final DisplayLifecycle mDisplayLifecycle;
    public final DumpManager mDumpManager;
    public final GoodLockLifecycle mGoodLockLifecycle;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public LockStarViewContainer mLockStarContainer;
    public ViewStub mLockStarViewStub;
    public PluginLockStar mPluginLockStar;
    public ViewGroup mRootView;
    public final SPluginManager mSPluginManager;
    public final LockStarShortcutController mShortcutController;
    public final StatusBarStateController mStatusBarStateController;
    public final WakefulnessLifecycle mWakefulnessLifecycle;
    public final KeyguardUpdateMonitorCallback mCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.lockstar.PluginLockStarManager.1
        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onPackageAdded(String str) {
            PluginLockStar pluginLockStar = PluginLockStarManager.this.mPluginLockStar;
            if (pluginLockStar != null) {
                try {
                    pluginLockStar.onPackageAdded(str);
                } catch (AbstractMethodError | Exception e) {
                    Log.w("LStar|PluginLockStarManager", "onPackageAdded " + e);
                }
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onPackageChanged(String str) {
            PluginLockStar pluginLockStar = PluginLockStarManager.this.mPluginLockStar;
            if (pluginLockStar != null) {
                try {
                    pluginLockStar.onPackageChanged(str);
                } catch (AbstractMethodError | Exception e) {
                    Log.w("LStar|PluginLockStarManager", "onPackageChanged " + e);
                }
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onPackageRemoved(String str, boolean z) {
            PluginLockStar pluginLockStar = PluginLockStarManager.this.mPluginLockStar;
            if (pluginLockStar != null) {
                try {
                    pluginLockStar.onPackageRemoved(str, z);
                } catch (AbstractMethodError | Exception e) {
                    Log.w("LStar|PluginLockStarManager", "onPackageRemoved " + e);
                }
            }
        }
    };
    public boolean mRequested = false;
    public boolean mIsGoodLockInstalled = false;
    public final AnonymousClass2 mObserver = new AnonymousClass2();
    public final LockStarPresenter mLockStarPresenter = new LockStarPresenter();

    /* renamed from: com.android.systemui.lockstar.PluginLockStarManager$2, reason: invalid class name */
    public final class AnonymousClass2 {
        public AnonymousClass2() {
        }
    }

    public interface LockStarCallback {
        void onChangedLockStarData(boolean z);

        default Bundle request(Bundle bundle) {
            return new Bundle();
        }
    }

    public PluginLockStarManager(Context context, SPluginManager sPluginManager, DumpManager dumpManager, KeyguardUpdateMonitor keyguardUpdateMonitor, DisplayLifecycle displayLifecycle, WakefulnessLifecycle wakefulnessLifecycle, StatusBarStateController statusBarStateController, ZenModeController zenModeController, GoodLockLifecycle goodLockLifecycle) {
        this.mContext = context;
        this.mSPluginManager = sPluginManager;
        this.mDumpManager = dumpManager;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mDisplayLifecycle = displayLifecycle;
        this.mWakefulnessLifecycle = wakefulnessLifecycle;
        this.mStatusBarStateController = statusBarStateController;
        this.mGoodLockLifecycle = goodLockLifecycle;
        this.mShortcutController = new LockStarShortcutController(context, zenModeController);
    }

    public final void checkGoodLockInstalledState() {
        this.mIsGoodLockInstalled = this.mGoodLockLifecycle.isInstalled;
        KeyguardSecPasswordViewController$$ExternalSyntheticOutline0.m(new StringBuilder("checkGoodLockInstalledState: "), this.mIsGoodLockInstalled, "LStar|PluginLockStarManager");
        if (this.mIsGoodLockInstalled) {
            if (this.mRequested) {
                return;
            }
            Log.i("LStar|PluginLockStarManager", "checkGoodLockInstalledState: addPluginListener");
            this.mSPluginManager.addPluginListener(PluginLockStar.ACTION, this, PluginLockStar.class, false, true);
            this.mRequested = true;
            return;
        }
        if (this.mRequested) {
            Log.i("LStar|PluginLockStarManager", "checkGoodLockInstalledState: removePluginListener");
            this.mSPluginManager.removePluginListener(this);
            this.mRequested = false;
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        StringBuilder m = KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(CarrierTextController$$ExternalSyntheticOutline0.m(printWriter, "   SYSUI VERSION = 4001", "  mIsGoodLockInstalled = "), this.mIsGoodLockInstalled, printWriter, "  mRootView = ");
        m.append(this.mRootView);
        printWriter.println(m.toString());
        if (this.mPluginLockStar == null) {
            return;
        }
        printWriter.println("  mPluginLockStar = " + this.mPluginLockStar);
        printWriter.println("   APP VERSION = " + this.mPluginLockStar.getVersion());
        printWriter.println("  mLockStarContainer = " + this.mLockStarContainer);
        try {
            this.mPluginLockStar.dump(printWriter, strArr);
        } catch (Error e) {
            Log.w("LStar|PluginLockStarManager", "dump " + e.toString());
        }
        printWriter.println(" ----------------------------------------------- ");
    }

    @Override // com.samsung.systemui.splugins.lockstar.PluginLockStar.PluginLockStarCallback
    public final Object get(String str) {
        if (str == null) {
            Log.d("LStar|PluginLockStarManager", "name is invalid");
            return null;
        }
        if (str.equalsIgnoreCase("lockstarContainer")) {
            return this.mLockStarContainer;
        }
        if (str.equalsIgnoreCase("briefWidgetContainer")) {
            return this.mRootView.findViewWithTag("keyguard_complication_widget");
        }
        return null;
    }

    public final LockStarValues getLockStarValues() {
        PluginLockStar pluginLockStar = this.mPluginLockStar;
        if (pluginLockStar == null) {
            return null;
        }
        try {
            return pluginLockStar.getLockStarValues();
        } catch (Throwable th) {
            Log.w("LStar|PluginLockStarManager", "getLockStarValues() failed " + th.getMessage());
            return null;
        }
    }

    @Override // com.samsung.systemui.splugins.lockstar.PluginLockStar.PluginLockStarCallback
    public final int getResourceId(String str, String str2, String str3) {
        Context context = this.mContext;
        if (context == null || context.getResources() == null) {
            return -1;
        }
        return this.mContext.getResources().getIdentifier(str, str2, str3);
    }

    public final boolean isLockStarEnabled() {
        PluginLockStar pluginLockStar = this.mPluginLockStar;
        if (pluginLockStar == null) {
            return false;
        }
        return pluginLockStar.isLockStarEnabled();
    }

    @Override // com.samsung.systemui.splugins.lockstar.PluginLockStar.PluginLockStarCallback
    public final boolean isRunningNoNeedUnlockService(String str) {
        PluginLockBottomAreaCallback pluginLockBottomAreaCallback;
        ComponentName unflattenFromString;
        if (TextUtils.isEmpty(str) || (pluginLockBottomAreaCallback = this.mShortcutController.bottomAreaCallback) == null || (unflattenFromString = ComponentName.unflattenFromString(str)) == null) {
            return false;
        }
        boolean isNoUnlockNeed = pluginLockBottomAreaCallback.isNoUnlockNeed(unflattenFromString.getPackageName());
        Log.i("LockStarShortcutController", "isRunningNoNeedUnlockService: ret=" + isNoUnlockNeed + ", " + str);
        return isNoUnlockNeed;
    }

    public final boolean isTouchable(MotionEvent motionEvent) {
        PluginLockStar pluginLockStar = this.mPluginLockStar;
        if (pluginLockStar == null) {
            return false;
        }
        try {
            return pluginLockStar.isTouchable(motionEvent);
        } catch (AbstractMethodError | Exception e) {
            Log.w("LStar|PluginLockStarManager", "isTouchable " + e);
            return false;
        }
    }

    @Override // com.samsung.systemui.splugins.lockstar.PluginLockStar.PluginLockStarCallback
    public final void launchShortcutApp(String str) {
        Object obj;
        if (TextUtils.isEmpty(str)) {
            return;
        }
        LockStarShortcutController lockStarShortcutController = this.mShortcutController;
        lockStarShortcutController.getClass();
        ComponentName unflattenFromString = ComponentName.unflattenFromString(str);
        if (unflattenFromString == null) {
            return;
        }
        Iterator it = lockStarShortcutController.taskList.iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            LockStarShortcutTask lockStarShortcutTask = (LockStarShortcutTask) obj;
            if ("NoUnlockNeeded".equals(unflattenFromString.getPackageName()) && Intrinsics.areEqual(lockStarShortcutTask.getName(), unflattenFromString.getClassName())) {
                break;
            }
        }
        LockStarShortcutTask lockStarShortcutTask2 = (LockStarShortcutTask) obj;
        if (lockStarShortcutTask2 != null) {
            Log.i("LockStarShortcutController", "launchShortcutApp: execute task ".concat(str));
            lockStarShortcutTask2.execute();
            return;
        }
        Log.i("LockStarShortcutController", "launchShortcutApp: ".concat(str));
        PluginLockBottomAreaCallback pluginLockBottomAreaCallback = lockStarShortcutController.bottomAreaCallback;
        if (pluginLockBottomAreaCallback != null) {
            pluginLockBottomAreaCallback.launchApp(unflattenFromString);
        }
    }

    @Override // com.samsung.systemui.splugins.lockstar.PluginLockStar.PluginLockStarCallback
    public final void onChangedLockStarData(boolean z, Bundle bundle) {
        Log.d("LStar|PluginLockStarManager", "onChangedLockStarData :: " + z + ", extras=" + bundle);
        this.mLockStarPresenter.onChangedLockStarData(z, bundle);
    }

    @Override // com.samsung.systemui.splugins.lockstar.PluginLockStar.PluginLockStarCallback
    public final void onChangedLockStarEnabled(boolean z) {
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("onChangedLockStarEnabled :: ", "LStar|PluginLockStarManager", z);
        this.mLockStarPresenter.onChangedLockStarData(z, null);
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public final void onDozeAmountChanged(float f, float f2) {
        Float valueOf = Float.valueOf(f2);
        PluginLockStar pluginLockStar = this.mPluginLockStar;
        if (pluginLockStar == null) {
            return;
        }
        try {
            pluginLockStar.setDarkAmount(valueOf);
        } catch (Throwable unused) {
        }
    }

    @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
    public final void onFinishedGoingToSleep() {
        PluginLockStar pluginLockStar = this.mPluginLockStar;
        if (pluginLockStar == null) {
            return;
        }
        try {
            pluginLockStar.onFinishedGoingToSleep();
        } catch (Throwable unused) {
        }
    }

    @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
    public final void onFinishedWakingUp() {
        PluginLockStar pluginLockStar = this.mPluginLockStar;
        if (pluginLockStar == null) {
            return;
        }
        try {
            pluginLockStar.onFinishedWakingUp();
        } catch (Throwable unused) {
        }
    }

    @Override // com.android.systemui.keyguard.DisplayLifecycle.Observer
    public final void onFolderStateChanged(boolean z) {
        PluginLockStar pluginLockStar = this.mPluginLockStar;
        if (pluginLockStar == null) {
            return;
        }
        try {
            pluginLockStar.onChangedDisplay(!z ? 1 : 0);
        } catch (Throwable unused) {
        }
    }

    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        PluginLockStar pluginLockStar = this.mPluginLockStar;
        if (pluginLockStar == null) {
            return false;
        }
        try {
            return pluginLockStar.onInterceptTouchEvent(motionEvent);
        } catch (AbstractMethodError | Exception e) {
            Log.w("LStar|PluginLockStarManager", "onInterceptTouchEvent " + e);
            return false;
        }
    }

    @Override // com.samsung.systemui.splugins.SPluginListener
    public final void onPluginConnected(SPlugin sPlugin, Context context) {
        PluginLockStar pluginLockStar = (PluginLockStar) sPlugin;
        if (LsRune.PLUGIN_LOCK_STAR_SAFEMODE) {
            Log.i("LStar|PluginLockStarManager", "Do not plug in connection in safe mode");
            return;
        }
        Log.d("LStar|PluginLockStarManager", "onPluginConnected");
        this.mPluginLockStar = pluginLockStar;
        ViewStub viewStub = this.mLockStarViewStub;
        if (viewStub != null) {
            this.mLockStarContainer = (LockStarViewContainer) viewStub.inflate();
            this.mLockStarViewStub = null;
        }
        LockStarViewContainer lockStarViewContainer = this.mLockStarContainer;
        if (lockStarViewContainer == null) {
            Log.e("LStar|PluginLockStarManager", "Failed to get lock star container");
        } else {
            lockStarViewContainer.setVisibility(8);
        }
        this.mPluginLockStar.init(this);
        this.mKeyguardUpdateMonitor.registerCallback(this.mCallback);
        PluginLockStar pluginLockStar2 = this.mPluginLockStar;
        LockStarShortcutController lockStarShortcutController = this.mShortcutController;
        lockStarShortcutController.getClass();
        Log.d("LockStarShortcutController", "init: ");
        if (lockStarShortcutController.taskList.isEmpty()) {
            ArrayList arrayList = lockStarShortcutController.taskList;
            arrayList.add(new LockStarShortcutFlashLight(lockStarShortcutController.context, pluginLockStar2));
            arrayList.add(new LockStarShortcutDnd(lockStarShortcutController.context, pluginLockStar2, lockStarShortcutController.zenModeController));
        }
        Iterator it = lockStarShortcutController.taskList.iterator();
        while (it.hasNext()) {
            ((LockStarShortcutTask) it.next()).init();
        }
    }

    @Override // com.samsung.systemui.splugins.SPluginListener
    public final void onPluginDisconnected(SPlugin sPlugin, int i) {
        ListPopupWindow$$ExternalSyntheticOutline0.m(i, "onPluginDisconnected ", "LStar|PluginLockStarManager");
        this.mPluginLockStar.onDestroy();
        this.mPluginLockStar = null;
        LockStarViewContainer lockStarViewContainer = this.mLockStarContainer;
        if (lockStarViewContainer != null) {
            lockStarViewContainer.setVisibility(8);
        }
        Log.d("LStar|PluginLockStarManager", "removeAllAddedViews");
        LockStarViewContainer lockStarViewContainer2 = this.mLockStarContainer;
        if (lockStarViewContainer2 != null) {
            lockStarViewContainer2.removeAllViews();
            this.mLockStarContainer.setVisibility(8);
        }
        onChangedLockStarEnabled(false);
        this.mKeyguardUpdateMonitor.removeCallback(this.mCallback);
        LockStarShortcutController lockStarShortcutController = this.mShortcutController;
        lockStarShortcutController.getClass();
        Log.d("LockStarShortcutController", "terminate: ");
        Iterator it = lockStarShortcutController.taskList.iterator();
        while (it.hasNext()) {
            ((LockStarShortcutTask) it.next()).terminate();
        }
        lockStarShortcutController.taskList.clear();
    }

    @Override // com.samsung.systemui.splugins.SPluginListener
    public final void onPluginLoadFailed(int i) {
        Log.e("LStar|PluginLockStarManager", "onPluginLoadFailed");
        Log.d("LStar|PluginLockStarManager", "removeAllAddedViews");
        LockStarViewContainer lockStarViewContainer = this.mLockStarContainer;
        if (lockStarViewContainer != null) {
            lockStarViewContainer.removeAllViews();
            this.mLockStarContainer.setVisibility(8);
        }
    }

    @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
    public final void onStartedGoingToSleep() {
        PluginLockStar pluginLockStar = this.mPluginLockStar;
        if (pluginLockStar == null) {
            return;
        }
        try {
            pluginLockStar.onStartedGoingToSleep();
        } catch (Throwable unused) {
        }
    }

    @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
    public final void onStartedWakingUp() {
        PluginLockStar pluginLockStar = this.mPluginLockStar;
        if (pluginLockStar == null) {
            return;
        }
        try {
            pluginLockStar.onStartedWakingUp();
        } catch (Throwable unused) {
        }
    }

    @Override // com.samsung.systemui.splugins.lockstar.PluginLockStar.PluginLockStarCallback
    public final void onUpdateModifiers(Map map) {
        Log.d("LStar|PluginLockStarManager", "onUpdateModifiers :: " + map.toString());
        Iterator it = MapUtilsKt.filterValuesNotNull(this.mLockStarPresenter.callbackMap).entrySet().iterator();
        while (it.hasNext()) {
            ((LockStarCallback) ((Map.Entry) it.next()).getValue()).getClass();
        }
    }

    public final void registerCallback(String str, LockStarCallback lockStarCallback) {
        LockStarPresenter lockStarPresenter = this.mLockStarPresenter;
        ((HashMap) lockStarPresenter.callbackMap).remove(str);
        ((HashMap) lockStarPresenter.callbackMap).put(str, lockStarCallback);
    }

    @Override // com.samsung.systemui.splugins.lockstar.PluginLockStar.PluginLockStarCallback
    public final Bundle request(Bundle bundle) {
        Log.d("LStar|PluginLockStarManager", "request :: " + bundle);
        LockStarPresenter lockStarPresenter = this.mLockStarPresenter;
        lockStarPresenter.getClass();
        String string = bundle.getString("type", "");
        for (Map.Entry entry : MapUtilsKt.filterValuesNotNull(lockStarPresenter.callbackMap).entrySet()) {
            String str = (String) entry.getKey();
            Bundle request = ((LockStarCallback) entry.getValue()).request(bundle);
            if (Intrinsics.areEqual(str, string)) {
                return request;
            }
        }
        return new Bundle();
    }

    public final void unregisterCallback(String str) {
        LockStarPresenter lockStarPresenter = this.mLockStarPresenter;
        if (((HashMap) lockStarPresenter.callbackMap).containsKey(str)) {
            ((HashMap) lockStarPresenter.callbackMap).remove(str);
        }
    }
}
