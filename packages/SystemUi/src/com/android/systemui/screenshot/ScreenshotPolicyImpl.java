package com.android.systemui.screenshot;

import android.app.ActivityTaskManager;
import android.app.IActivityTaskManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Process;
import android.os.UserManager;
import android.util.Log;
import com.android.internal.infra.ServiceConnector;
import com.android.systemui.SystemUIService;
import com.android.systemui.screenshot.IScreenshotProxy;
import com.android.systemui.screenshot.ScreenshotPolicy;
import com.android.systemui.settings.DisplayTracker;
import com.samsung.android.nexus.video.VideoPlayer;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.SafeContinuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ScreenshotPolicyImpl implements ScreenshotPolicy {
    public final IActivityTaskManager atmService;
    public final CoroutineDispatcher bgDispatcher;
    public final ServiceConnector proxyConnector;
    public final ScreenshotPolicy.DisplayContentInfo systemUiContent;
    public final UserManager userMgr;

    public ScreenshotPolicyImpl(Context context, UserManager userManager, IActivityTaskManager iActivityTaskManager, CoroutineDispatcher coroutineDispatcher, DisplayTracker displayTracker) {
        this.userMgr = userManager;
        this.atmService = iActivityTaskManager;
        this.bgDispatcher = coroutineDispatcher;
        this.proxyConnector = new ServiceConnector.Impl(context, new Intent(context, (Class<?>) ScreenshotProxyService.class), 1073741857, 0, new Function() { // from class: com.android.systemui.screenshot.ScreenshotPolicyImpl$proxyConnector$1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                IBinder iBinder = (IBinder) obj;
                int i = IScreenshotProxy.Stub.$r8$clinit;
                if (iBinder == null) {
                    return null;
                }
                IInterface queryLocalInterface = iBinder.queryLocalInterface("com.android.systemui.screenshot.IScreenshotProxy");
                return (queryLocalInterface == null || !(queryLocalInterface instanceof IScreenshotProxy)) ? new IScreenshotProxy.Stub.Proxy(iBinder) : (IScreenshotProxy) queryLocalInterface;
            }
        });
        this.systemUiContent = new ScreenshotPolicy.DisplayContentInfo(new ComponentName(context, (Class<?>) SystemUIService.class), new Rect(), Process.myUserHandle(), -1);
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00a7  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00aa  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00a2 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x005b  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0040  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Object findPrimaryContent$suspendImpl(ScreenshotPolicyImpl screenshotPolicyImpl, int i, Continuation continuation) {
        ScreenshotPolicyImpl$findPrimaryContent$1 screenshotPolicyImpl$findPrimaryContent$1;
        Object obj;
        int i2;
        Iterator it;
        Object obj2;
        if (continuation instanceof ScreenshotPolicyImpl$findPrimaryContent$1) {
            screenshotPolicyImpl$findPrimaryContent$1 = (ScreenshotPolicyImpl$findPrimaryContent$1) continuation;
            int i3 = screenshotPolicyImpl$findPrimaryContent$1.label;
            if ((i3 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                screenshotPolicyImpl$findPrimaryContent$1.label = i3 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                obj = screenshotPolicyImpl$findPrimaryContent$1.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                i2 = screenshotPolicyImpl$findPrimaryContent$1.label;
                if (i2 != 0) {
                    ResultKt.throwOnFailure(obj);
                    screenshotPolicyImpl$findPrimaryContent$1.L$0 = screenshotPolicyImpl;
                    screenshotPolicyImpl$findPrimaryContent$1.I$0 = i;
                    screenshotPolicyImpl$findPrimaryContent$1.label = 1;
                    obj = screenshotPolicyImpl.isNotificationShadeExpanded(screenshotPolicyImpl$findPrimaryContent$1);
                    if (obj == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i2 != 1) {
                        if (i2 != 2) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        screenshotPolicyImpl = (ScreenshotPolicyImpl) screenshotPolicyImpl$findPrimaryContent$1.L$0;
                        ResultKt.throwOnFailure(obj);
                        it = ((List) obj).iterator();
                        while (true) {
                            if (it.hasNext()) {
                                obj2 = null;
                                break;
                            }
                            obj2 = it.next();
                            ActivityTaskManager.RootTaskInfo rootTaskInfo = (ActivityTaskManager.RootTaskInfo) obj2;
                            screenshotPolicyImpl.getClass();
                            boolean z = false;
                            if (rootTaskInfo.getWindowingMode() != 2 && rootTaskInfo.isVisible && rootTaskInfo.isRunning && rootTaskInfo.numActivities > 0 && rootTaskInfo.topActivity != null) {
                                if (!(rootTaskInfo.childTaskIds.length == 0)) {
                                    z = true;
                                }
                            }
                        }
                        ActivityTaskManager.RootTaskInfo rootTaskInfo2 = (ActivityTaskManager.RootTaskInfo) obj2;
                        return rootTaskInfo2 != null ? screenshotPolicyImpl.systemUiContent : ScreenshotPolicyImplKt.toDisplayContentInfo(rootTaskInfo2);
                    }
                    i = screenshotPolicyImpl$findPrimaryContent$1.I$0;
                    screenshotPolicyImpl = (ScreenshotPolicyImpl) screenshotPolicyImpl$findPrimaryContent$1.L$0;
                    ResultKt.throwOnFailure(obj);
                }
                if (!((Boolean) obj).booleanValue()) {
                    return screenshotPolicyImpl.systemUiContent;
                }
                screenshotPolicyImpl$findPrimaryContent$1.L$0 = screenshotPolicyImpl;
                screenshotPolicyImpl$findPrimaryContent$1.label = 2;
                obj = screenshotPolicyImpl.getAllRootTaskInfosOnDisplay(i, screenshotPolicyImpl$findPrimaryContent$1);
                if (obj == coroutineSingletons) {
                    return coroutineSingletons;
                }
                it = ((List) obj).iterator();
                while (true) {
                    if (it.hasNext()) {
                    }
                }
                ActivityTaskManager.RootTaskInfo rootTaskInfo22 = (ActivityTaskManager.RootTaskInfo) obj2;
                if (rootTaskInfo22 != null) {
                }
            }
        }
        screenshotPolicyImpl$findPrimaryContent$1 = new ScreenshotPolicyImpl$findPrimaryContent$1(screenshotPolicyImpl, continuation);
        obj = screenshotPolicyImpl$findPrimaryContent$1.result;
        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        i2 = screenshotPolicyImpl$findPrimaryContent$1.label;
        if (i2 != 0) {
        }
        if (!((Boolean) obj).booleanValue()) {
        }
    }

    public static /* synthetic */ Object getAllRootTaskInfosOnDisplay$suspendImpl(ScreenshotPolicyImpl screenshotPolicyImpl, int i, Continuation<? super List<? extends ActivityTaskManager.RootTaskInfo>> continuation) {
        return BuildersKt.withContext(screenshotPolicyImpl.bgDispatcher, new ScreenshotPolicyImpl$getAllRootTaskInfosOnDisplay$2(screenshotPolicyImpl, i, null), continuation);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Object isManagedProfile$suspendImpl(ScreenshotPolicyImpl screenshotPolicyImpl, int i, Continuation continuation) {
        ScreenshotPolicyImpl$isManagedProfile$1 screenshotPolicyImpl$isManagedProfile$1;
        int i2;
        if (continuation instanceof ScreenshotPolicyImpl$isManagedProfile$1) {
            screenshotPolicyImpl$isManagedProfile$1 = (ScreenshotPolicyImpl$isManagedProfile$1) continuation;
            int i3 = screenshotPolicyImpl$isManagedProfile$1.label;
            if ((i3 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                screenshotPolicyImpl$isManagedProfile$1.label = i3 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                Object obj = screenshotPolicyImpl$isManagedProfile$1.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                i2 = screenshotPolicyImpl$isManagedProfile$1.label;
                if (i2 != 0) {
                    ResultKt.throwOnFailure(obj);
                    CoroutineDispatcher coroutineDispatcher = screenshotPolicyImpl.bgDispatcher;
                    ScreenshotPolicyImpl$isManagedProfile$managed$1 screenshotPolicyImpl$isManagedProfile$managed$1 = new ScreenshotPolicyImpl$isManagedProfile$managed$1(screenshotPolicyImpl, i, null);
                    screenshotPolicyImpl$isManagedProfile$1.label = 1;
                    obj = BuildersKt.withContext(coroutineDispatcher, screenshotPolicyImpl$isManagedProfile$managed$1, screenshotPolicyImpl$isManagedProfile$1);
                    if (obj == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } else {
                    if (i2 != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                boolean booleanValue = ((Boolean) obj).booleanValue();
                Log.d("ScreenshotPolicyImpl", "isManagedProfile: " + booleanValue);
                return Boolean.valueOf(booleanValue);
            }
        }
        screenshotPolicyImpl$isManagedProfile$1 = new ScreenshotPolicyImpl$isManagedProfile$1(screenshotPolicyImpl, continuation);
        Object obj2 = screenshotPolicyImpl$isManagedProfile$1.result;
        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        i2 = screenshotPolicyImpl$isManagedProfile$1.label;
        if (i2 != 0) {
        }
        boolean booleanValue2 = ((Boolean) obj2).booleanValue();
        Log.d("ScreenshotPolicyImpl", "isManagedProfile: " + booleanValue2);
        return Boolean.valueOf(booleanValue2);
    }

    public static Object isNotificationShadeExpanded$suspendImpl(ScreenshotPolicyImpl screenshotPolicyImpl, Continuation<? super Boolean> continuation) {
        final SafeContinuation safeContinuation = new SafeContinuation(IntrinsicsKt__IntrinsicsJvmKt.intercepted(continuation));
        screenshotPolicyImpl.proxyConnector.postForResult(new ServiceConnector.Job() { // from class: com.android.systemui.screenshot.ScreenshotPolicyImpl$isNotificationShadeExpanded$2$1
            public final Object run(Object obj) {
                return Boolean.valueOf(((IScreenshotProxy) obj).isNotificationShadeExpanded());
            }
        }).whenComplete(new BiConsumer() { // from class: com.android.systemui.screenshot.ScreenshotPolicyImpl$isNotificationShadeExpanded$2$2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                Boolean bool = (Boolean) obj;
                Throwable th = (Throwable) obj2;
                if (th != null) {
                    Log.e("ScreenshotPolicyImpl", "isNotificationShadeExpanded", th);
                }
                Continuation continuation2 = safeContinuation;
                int i = Result.$r8$clinit;
                if (bool == null) {
                    bool = Boolean.FALSE;
                }
                continuation2.resumeWith(bool);
            }
        });
        Object orThrow = safeContinuation.getOrThrow();
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        return orThrow;
    }

    public Object getAllRootTaskInfosOnDisplay(int i, Continuation<? super List<? extends ActivityTaskManager.RootTaskInfo>> continuation) {
        return getAllRootTaskInfosOnDisplay$suspendImpl(this, i, continuation);
    }

    public Object isNotificationShadeExpanded(Continuation<? super Boolean> continuation) {
        return isNotificationShadeExpanded$suspendImpl(this, continuation);
    }

    /* renamed from: getSystemUiContent$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations */
    public static /* synthetic */ void m179xae0bd866() {
    }
}
