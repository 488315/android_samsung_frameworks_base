package com.android.systemui.lockstar;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.ViewStub;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.samsung.systemui.splugins.SPlugin;
import com.samsung.systemui.splugins.SPluginListener;
import com.samsung.systemui.splugins.SPluginManager;
import com.samsung.systemui.splugins.lockstar.PluginLockStar;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class PluginLockStarManager implements SPluginListener, PluginLockStar.PluginLockStarCallback, Dumpable, DisplayLifecycle.Observer, WakefulnessLifecycle.Observer, StatusBarStateController.StateListener {
    public final Context mContext;
    public final DisplayLifecycle mDisplayLifecycle;
    public final DumpManager mDumpManager;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public LockStarViewContainer mLockStarContainer;
    public ViewStub mLockStarViewStub;
    public PluginLockStar mPluginLockStar;
    public ViewGroup mRootView;
    public final SPluginManager mSPluginManager;
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
    public final LockStarPresenter mLockStarPresenter = new LockStarPresenter();

    public PluginLockStarManager(Context context, SPluginManager sPluginManager, DumpManager dumpManager, KeyguardUpdateMonitor keyguardUpdateMonitor, DisplayLifecycle displayLifecycle, WakefulnessLifecycle wakefulnessLifecycle, StatusBarStateController statusBarStateController) {
        this.mContext = context;
        this.mSPluginManager = sPluginManager;
        this.mDumpManager = dumpManager;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mDisplayLifecycle = displayLifecycle;
        this.mWakefulnessLifecycle = wakefulnessLifecycle;
        this.mStatusBarStateController = statusBarStateController;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        if (this.mPluginLockStar == null) {
            return;
        }
        printWriter.println("  mPluginLockStar = " + this.mPluginLockStar);
        printWriter.println("  mRootView = " + this.mRootView);
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

    public final PluginLockStar.Modifier getModifier(String str) {
        PluginLockStar pluginLockStar = this.mPluginLockStar;
        if (pluginLockStar == null) {
            return null;
        }
        return pluginLockStar.getModifier(str);
    }

    @Override // com.samsung.systemui.splugins.lockstar.PluginLockStar.PluginLockStarCallback
    public final int getResourceId(String str, String str2, String str3) {
        Context context = this.mContext;
        if (context == null || context.getResources() == null) {
            return -1;
        }
        return context.getResources().getIdentifier(str, str2, str3);
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
    public final void onChangedLockStarEnabled(boolean z) {
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("onChangedLockStarEnabled :: ", z, "LStar|PluginLockStarManager");
        Iterator it = ((HashMap) this.mLockStarPresenter.mCallbackMap).entrySet().iterator();
        while (it.hasNext()) {
            ((LockStarCallback) ((Map.Entry) it.next()).getValue()).onChangedLockStarEnabled(z);
        }
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
        Log.d("LStar|PluginLockStarManager", "onPluginConnected");
        this.mPluginLockStar = (PluginLockStar) sPlugin;
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
    }

    @Override // com.samsung.systemui.splugins.SPluginListener
    public final void onPluginDisconnected(SPlugin sPlugin, int i) {
        ListPopupWindow$$ExternalSyntheticOutline0.m10m("onPluginDisconnected ", i, "LStar|PluginLockStarManager");
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
        Iterator it = ((HashMap) this.mLockStarPresenter.mCallbackMap).entrySet().iterator();
        while (it.hasNext()) {
            ((LockStarCallback) ((Map.Entry) it.next()).getValue()).onUpdateModifiers();
        }
    }

    public final void registerCallback(String str, LockStarCallback lockStarCallback) {
        HashMap hashMap = (HashMap) this.mLockStarPresenter.mCallbackMap;
        hashMap.remove(str);
        hashMap.put(str, lockStarCallback);
    }

    public final void unregisterCallback(String str) {
        HashMap hashMap = (HashMap) this.mLockStarPresenter.mCallbackMap;
        if (hashMap.containsKey(str)) {
            hashMap.remove(str);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface LockStarCallback {
        void onChangedLockStarEnabled(boolean z);

        default void onUpdateModifiers() {
        }
    }
}
