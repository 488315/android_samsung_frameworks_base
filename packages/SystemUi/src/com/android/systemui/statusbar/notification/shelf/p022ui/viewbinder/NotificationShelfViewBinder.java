package com.android.systemui.statusbar.notification.shelf.p022ui.viewbinder;

import android.os.SystemClock;
import android.view.View;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.NotificationShelf;
import com.android.systemui.statusbar.NotificationShelfController;
import com.android.systemui.statusbar.notification.row.p021ui.viewbinder.ActivatableNotificationViewBinder;
import com.android.systemui.statusbar.notification.shelf.domain.interactor.NotificationShelfInteractor;
import com.android.systemui.statusbar.notification.shelf.p022ui.viewmodel.NotificationShelfViewModel;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.NotificationIconAreaController;
import com.android.systemui.util.time.SystemClockImpl;
import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlinx.coroutines.DelayKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotificationShelfViewBinder {
    public static final NotificationShelfViewBinder INSTANCE = new NotificationShelfViewBinder();

    private NotificationShelfViewBinder() {
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final CoroutineSingletons access$registerViewListenersWhileAttached(NotificationShelfViewBinder notificationShelfViewBinder, NotificationShelf notificationShelf, final NotificationShelfViewModel notificationShelfViewModel, Continuation continuation) {
        NotificationShelfViewBinder$registerViewListenersWhileAttached$1 notificationShelfViewBinder$registerViewListenersWhileAttached$1;
        int i;
        notificationShelfViewBinder.getClass();
        try {
            if (continuation instanceof NotificationShelfViewBinder$registerViewListenersWhileAttached$1) {
                notificationShelfViewBinder$registerViewListenersWhileAttached$1 = (NotificationShelfViewBinder$registerViewListenersWhileAttached$1) continuation;
                int i2 = notificationShelfViewBinder$registerViewListenersWhileAttached$1.label;
                if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                    notificationShelfViewBinder$registerViewListenersWhileAttached$1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                    Object obj = notificationShelfViewBinder$registerViewListenersWhileAttached$1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    i = notificationShelfViewBinder$registerViewListenersWhileAttached$1.label;
                    if (i != 0) {
                        ResultKt.throwOnFailure(obj);
                        notificationShelf.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.shelf.ui.viewbinder.NotificationShelfViewBinder$registerViewListenersWhileAttached$2
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                NotificationShelfInteractor notificationShelfInteractor = NotificationShelfViewModel.this.interactor;
                                ((SystemClockImpl) notificationShelfInteractor.systemClock).getClass();
                                ((CentralSurfacesImpl) notificationShelfInteractor.centralSurfaces).wakeUpIfDozing(SystemClock.uptimeMillis(), "SHADE_CLICK", 4);
                                notificationShelfInteractor.keyguardTransitionController.goToLockedShade(null, true);
                            }
                        });
                        notificationShelfViewBinder$registerViewListenersWhileAttached$1.L$0 = notificationShelf;
                        notificationShelfViewBinder$registerViewListenersWhileAttached$1.label = 1;
                        if (DelayKt.awaitCancellation(notificationShelfViewBinder$registerViewListenersWhileAttached$1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        notificationShelf = (NotificationShelf) notificationShelfViewBinder$registerViewListenersWhileAttached$1.L$0;
                        ResultKt.throwOnFailure(obj);
                    }
                    throw new KotlinNothingValueException();
                }
            }
            if (i != 0) {
            }
            throw new KotlinNothingValueException();
        } catch (Throwable th) {
            notificationShelf.setOnClickListener(null);
            throw th;
        }
        notificationShelfViewBinder$registerViewListenersWhileAttached$1 = new NotificationShelfViewBinder$registerViewListenersWhileAttached$1(notificationShelfViewBinder, continuation);
        Object obj2 = notificationShelfViewBinder$registerViewListenersWhileAttached$1.result;
        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        i = notificationShelfViewBinder$registerViewListenersWhileAttached$1.label;
    }

    public static void bind(NotificationShelf notificationShelf, NotificationShelfViewModel notificationShelfViewModel, FalsingManager falsingManager, NotificationIconAreaController notificationIconAreaController) {
        ActivatableNotificationViewBinder.INSTANCE.getClass();
        ActivatableNotificationViewBinder.bind(notificationShelfViewModel, notificationShelf, falsingManager);
        notificationShelf.mShelfRefactorFlagEnabled = true;
        Flags flags = Flags.INSTANCE;
        notificationShelf.mSensitiveRevealAnimEndabled = false;
        FeatureFlags featureFlags = notificationIconAreaController.mFeatureFlags;
        NotificationShelfController.checkRefactorFlagEnabled();
        RepeatWhenAttachedKt.repeatWhenAttached(notificationShelf, EmptyCoroutineContext.INSTANCE, new NotificationShelfViewBinder$bind$1$1(notificationShelf, notificationShelfViewModel, notificationShelf, null));
    }
}
