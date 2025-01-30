package com.android.systemui.statusbar.notification.row.ui.viewbinder;

import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.notification.row.ActivatableNotificationView;
import com.android.systemui.statusbar.notification.row.ui.viewmodel.ActivatableNotificationViewModel;
import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlinx.coroutines.DelayKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ActivatableNotificationViewBinder {
    public static final ActivatableNotificationViewBinder INSTANCE = new ActivatableNotificationViewBinder();

    private ActivatableNotificationViewBinder() {
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final CoroutineSingletons access$registerListenersWhileAttached(ActivatableNotificationViewBinder activatableNotificationViewBinder, ActivatableNotificationView activatableNotificationView, TouchHandler touchHandler, Continuation continuation) {
        C2897x1b9578a4 c2897x1b9578a4;
        int i;
        activatableNotificationViewBinder.getClass();
        try {
            if (continuation instanceof C2897x1b9578a4) {
                c2897x1b9578a4 = (C2897x1b9578a4) continuation;
                int i2 = c2897x1b9578a4.label;
                if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                    c2897x1b9578a4.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                    Object obj = c2897x1b9578a4.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = c2897x1b9578a4.label;
                    if (i != 0) {
                        ResultKt.throwOnFailure(obj);
                        activatableNotificationView.setOnTouchListener(touchHandler);
                        activatableNotificationView.mTouchHandler = touchHandler;
                        c2897x1b9578a4.L$0 = activatableNotificationView;
                        c2897x1b9578a4.label = 1;
                        if (DelayKt.awaitCancellation(c2897x1b9578a4) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        activatableNotificationView = (ActivatableNotificationView) c2897x1b9578a4.L$0;
                        ResultKt.throwOnFailure(obj);
                    }
                    throw new KotlinNothingValueException();
                }
            }
            if (i != 0) {
            }
            throw new KotlinNothingValueException();
        } catch (Throwable th) {
            activatableNotificationView.mTouchHandler = null;
            activatableNotificationView.setOnTouchListener(null);
            throw th;
        }
        c2897x1b9578a4 = new C2897x1b9578a4(activatableNotificationViewBinder, continuation);
        Object obj2 = c2897x1b9578a4.result;
        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        i = c2897x1b9578a4.label;
    }

    public static void bind(ActivatableNotificationViewModel activatableNotificationViewModel, ActivatableNotificationView activatableNotificationView, FalsingManager falsingManager) {
        ExpandableOutlineViewBinder.INSTANCE.getClass();
        ExpandableViewBinder.INSTANCE.getClass();
        RepeatWhenAttachedKt.repeatWhenAttached(activatableNotificationView, EmptyCoroutineContext.INSTANCE, new ActivatableNotificationViewBinder$bind$1(activatableNotificationView, new TouchHandler(activatableNotificationView, falsingManager), activatableNotificationViewModel, null));
    }
}
