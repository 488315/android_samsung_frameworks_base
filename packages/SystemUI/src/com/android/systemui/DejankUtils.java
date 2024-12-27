package com.android.systemui;

import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.StrictMode;
import android.os.Trace;
import android.view.Choreographer;
import android.view.View;
import android.view.ViewRootImpl;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import com.android.settingslib.utils.ThreadUtils;
import com.android.systemui.uithreadmonitor.BinderCallMonitorConstants;
import com.android.systemui.util.Assert;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Stack;
import java.util.function.Supplier;

public final class DejankUtils {
    public static final boolean STRICT_MODE_ENABLED;
    public static final DejankUtils$$ExternalSyntheticLambda0 sAnimationCallbackRunnable;
    public static final Stack sBlockingIpcs;
    public static final Choreographer sChoreographer;
    public static final Handler sHandler;
    public static boolean sImmediate;
    public static final Object sLock;
    public static final ArrayList sPendingRunnables;
    public static final AnonymousClass1 sProxy = null;
    public static final Random sRandom;
    public static boolean sTemporarilyIgnoreStrictMode;
    public static final HashSet sWhitelistedFrameworkClasses;

    static {
        boolean z = BinderCallMonitorConstants.STRICT_MODE_ENABLED;
        STRICT_MODE_ENABLED = z;
        sChoreographer = Choreographer.getInstance();
        sHandler = new Handler();
        sPendingRunnables = new ArrayList();
        sRandom = new Random();
        sBlockingIpcs = new Stack();
        HashSet hashSet = new HashSet();
        sWhitelistedFrameworkClasses = hashSet;
        sLock = new Object();
        Binder.ProxyTransactListener proxyTransactListener = new Binder.ProxyTransactListener() { // from class: com.android.systemui.DejankUtils.1
            public final Object onTransactStarted(IBinder iBinder, int i) {
                return null;
            }

            public final Object onTransactStarted(IBinder iBinder, int i, int i2) {
                String interfaceDescriptor;
                Object obj = DejankUtils.sLock;
                synchronized (obj) {
                    if ((i2 & 1) != 1) {
                        if (!DejankUtils.sBlockingIpcs.empty() && ThreadUtils.isMainThread() && !DejankUtils.sTemporarilyIgnoreStrictMode) {
                            try {
                                interfaceDescriptor = iBinder.getInterfaceDescriptor();
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                            synchronized (obj) {
                                try {
                                    if (DejankUtils.sWhitelistedFrameworkClasses.contains(interfaceDescriptor)) {
                                        return null;
                                    }
                                    StrictMode.noteSlowCall("IPC detected on critical path: " + ((String) DejankUtils.sBlockingIpcs.peek()));
                                    return null;
                                } finally {
                                }
                            }
                        }
                    }
                    return null;
                }
            }

            public final void onTransactEnded(Object obj) {
            }
        };
        if (z) {
            hashSet.add("android.view.IWindowSession");
            hashSet.add("com.android.internal.policy.IKeyguardStateCallback");
            hashSet.add("android.os.IPowerManager");
            hashSet.add("com.android.internal.statusbar.IStatusBarService");
            Binder.setProxyTransactListener(proxyTransactListener);
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectCustomSlowCalls().penaltyFlashScreen().penaltyLog().build());
        }
        sAnimationCallbackRunnable = new DejankUtils$$ExternalSyntheticLambda0();
    }

    public static void notifyRendererOfExpensiveFrame(View view, String str) {
        ViewRootImpl viewRootImpl;
        if (view == null || (viewRootImpl = view.getViewRootImpl()) == null) {
            return;
        }
        if (Trace.isTagEnabled(4096L)) {
            final int nextInt = sRandom.nextInt();
            Trace.asyncTraceForTrackBegin(4096L, "DejankUtils", ContentInViewNode$Request$$ExternalSyntheticOutline0.m("notifyRendererOfExpensiveFrame (", str, ")"), nextInt);
            postAfterTraversal(new Runnable() { // from class: com.android.systemui.DejankUtils$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    Trace.asyncTraceForTrackEnd(4096L, "DejankUtils", nextInt);
                }
            });
        }
        viewRootImpl.notifyRendererOfExpensiveFrame();
    }

    public static void postAfterTraversal(Runnable runnable) {
        if (sImmediate) {
            runnable.run();
            return;
        }
        Assert.isMainThread();
        sPendingRunnables.add(runnable);
        sChoreographer.postCallback(1, sAnimationCallbackRunnable, null);
    }

    public static void setImmediate(boolean z) {
        sImmediate = z;
    }

    public static void startDetectingBlockingIpcs(String str) {
        if (STRICT_MODE_ENABLED) {
            synchronized (sLock) {
                sBlockingIpcs.push(str);
            }
        }
    }

    public static void stopDetectingBlockingIpcs(String str) {
        if (STRICT_MODE_ENABLED) {
            synchronized (sLock) {
                sBlockingIpcs.remove(str);
            }
        }
    }

    public static void whitelistIpcs(Runnable runnable) {
        if (!STRICT_MODE_ENABLED || sTemporarilyIgnoreStrictMode) {
            runnable.run();
            return;
        }
        Object obj = sLock;
        synchronized (obj) {
            sTemporarilyIgnoreStrictMode = true;
        }
        try {
            runnable.run();
            synchronized (obj) {
                sTemporarilyIgnoreStrictMode = false;
            }
        } catch (Throwable th) {
            synchronized (sLock) {
                sTemporarilyIgnoreStrictMode = false;
                throw th;
            }
        }
    }

    public static Object whitelistIpcs(Supplier supplier) {
        if (STRICT_MODE_ENABLED && !sTemporarilyIgnoreStrictMode) {
            Object obj = sLock;
            synchronized (obj) {
                sTemporarilyIgnoreStrictMode = true;
            }
            try {
                Object obj2 = supplier.get();
                synchronized (obj) {
                    sTemporarilyIgnoreStrictMode = false;
                }
                return obj2;
            } catch (Throwable th) {
                synchronized (sLock) {
                    sTemporarilyIgnoreStrictMode = false;
                    throw th;
                }
            }
        }
        return supplier.get();
    }
}
