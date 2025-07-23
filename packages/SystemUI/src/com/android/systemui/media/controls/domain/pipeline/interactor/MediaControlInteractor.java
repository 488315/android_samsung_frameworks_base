package com.android.systemui.media.controls.domain.pipeline.interactor;

import android.app.BroadcastOptions;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;
import com.android.internal.logging.InstanceId;
import com.android.systemui.ActivityIntentHelper;
import com.android.systemui.animation.Expandable;
import com.android.systemui.bluetooth.BroadcastDialogController;
import com.android.systemui.media.controls.data.repository.MediaFilterRepository;
import com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor;
import com.android.systemui.media.controls.shared.MediaLogger;
import com.android.systemui.media.dialog.MediaOutputDialogManager;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class MediaControlInteractor {
    public static final Intent SETTINGS_INTENT;
    public final ActivityIntentHelper activityIntentHelper;
    public final ActivityStarter activityStarter;
    public final InstanceId instanceId;
    public final KeyguardStateController keyguardStateController;
    public final NotificationLockscreenUserManager lockscreenUserManager;
    public final Flow mediaControl;
    public final MediaDataProcessor mediaDataProcessor;
    public final MediaLogger mediaLogger;
    public final MediaOutputDialogManager mediaOutputDialogManager;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        SETTINGS_INTENT = new Intent("android.settings.ACTION_MEDIA_CONTROLS_SETTINGS");
    }

    public MediaControlInteractor(InstanceId instanceId, MediaFilterRepository mediaFilterRepository, MediaDataProcessor mediaDataProcessor, KeyguardStateController keyguardStateController, ActivityStarter activityStarter, ActivityIntentHelper activityIntentHelper, NotificationLockscreenUserManager notificationLockscreenUserManager, MediaOutputDialogManager mediaOutputDialogManager, BroadcastDialogController broadcastDialogController, MediaLogger mediaLogger) {
        this.instanceId = instanceId;
        this.mediaDataProcessor = mediaDataProcessor;
        this.keyguardStateController = keyguardStateController;
        this.activityStarter = activityStarter;
        this.activityIntentHelper = activityIntentHelper;
        this.lockscreenUserManager = notificationLockscreenUserManager;
        this.mediaOutputDialogManager = mediaOutputDialogManager;
        this.mediaLogger = mediaLogger;
        final ReadonlyStateFlow readonlyStateFlow = mediaFilterRepository.selectedUserEntries;
        this.mediaControl = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.media.controls.domain.pipeline.interactor.MediaControlInteractor$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.media.controls.domain.pipeline.interactor.MediaControlInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ MediaControlInteractor this$0;

                /* renamed from: com.android.systemui.media.controls.domain.pipeline.interactor.MediaControlInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector, MediaControlInteractor mediaControlInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = mediaControlInteractor;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x0033  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r26, kotlin.coroutines.Continuation r27) {
                    /*
                        r25 = this;
                        r0 = r25
                        r1 = r27
                        boolean r2 = r1 instanceof com.android.systemui.media.controls.domain.pipeline.interactor.MediaControlInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r2 == 0) goto L17
                        r2 = r1
                        com.android.systemui.media.controls.domain.pipeline.interactor.MediaControlInteractor$special$$inlined$map$1$2$1 r2 = (com.android.systemui.media.controls.domain.pipeline.interactor.MediaControlInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r2
                        int r3 = r2.label
                        r4 = -2147483648(0xffffffff80000000, float:-0.0)
                        r5 = r3 & r4
                        if (r5 == 0) goto L17
                        int r3 = r3 - r4
                        r2.label = r3
                        goto L1c
                    L17:
                        com.android.systemui.media.controls.domain.pipeline.interactor.MediaControlInteractor$special$$inlined$map$1$2$1 r2 = new com.android.systemui.media.controls.domain.pipeline.interactor.MediaControlInteractor$special$$inlined$map$1$2$1
                        r2.<init>(r1)
                    L1c:
                        java.lang.Object r1 = r2.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r3 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r4 = r2.label
                        r5 = 1
                        if (r4 == 0) goto L33
                        if (r4 != r5) goto L2b
                        kotlin.ResultKt.throwOnFailure(r1)
                        goto L94
                    L2b:
                        java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                        java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
                        r0.<init>(r1)
                        throw r0
                    L33:
                        kotlin.ResultKt.throwOnFailure(r1)
                        r1 = r26
                        java.util.Map r1 = (java.util.Map) r1
                        com.android.systemui.media.controls.domain.pipeline.interactor.MediaControlInteractor r4 = r0.this$0
                        com.android.internal.logging.InstanceId r6 = r4.instanceId
                        java.lang.Object r1 = r1.get(r6)
                        com.android.systemui.media.controls.shared.model.MediaData r1 = (com.android.systemui.media.controls.shared.model.MediaData) r1
                        if (r1 == 0) goto L87
                        com.android.systemui.media.controls.shared.model.MediaControlModel r6 = new com.android.systemui.media.controls.shared.model.MediaControlModel
                        com.android.internal.logging.InstanceId r9 = r1.instanceId
                        android.media.session.MediaSession$Token r10 = r1.token
                        android.graphics.drawable.Icon r11 = r1.appIcon
                        android.app.PendingIntent r12 = r1.clickIntent
                        java.lang.CharSequence r14 = r1.song
                        java.lang.CharSequence r15 = r1.artist
                        android.graphics.drawable.Icon r7 = r1.artwork
                        java.util.List r8 = r1.actions
                        com.android.systemui.plugins.ActivityStarter r4 = r4.activityStarter
                        java.util.List r20 = com.android.systemui.media.controls.domain.pipeline.MediaActionsKt.getNotificationActions(r8, r4)
                        java.util.List r4 = r1.actionsToShowInCompact
                        boolean r8 = r1.resumption
                        java.lang.Double r13 = r1.resumeProgress
                        r17 = r7
                        int r7 = r1.appUid
                        r23 = r8
                        java.lang.String r8 = r1.packageName
                        r24 = r13
                        java.lang.String r13 = r1.app
                        boolean r5 = r1.isExplicit
                        r21 = r4
                        com.android.systemui.media.controls.shared.model.MediaDeviceData r4 = r1.device
                        r18 = r4
                        com.android.systemui.media.controls.shared.model.MediaButton r4 = r1.semanticActions
                        boolean r1 = r1.isClearable
                        r22 = r1
                        r19 = r4
                        r16 = r5
                        r6.<init>(r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24)
                    L85:
                        r1 = 1
                        goto L89
                    L87:
                        r6 = 0
                        goto L85
                    L89:
                        r2.label = r1
                        kotlinx.coroutines.flow.FlowCollector r0 = r0.$this_unsafeFlow
                        java.lang.Object r0 = r0.emit(r6, r2)
                        if (r0 != r3) goto L94
                        return r3
                    L94:
                        kotlin.Unit r0 = kotlin.Unit.INSTANCE
                        return r0
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.controls.domain.pipeline.interactor.MediaControlInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
    }

    public final boolean launchOverLockscreen(Expandable expandable, PendingIntent pendingIntent) {
        if (!((KeyguardStateControllerImpl) this.keyguardStateController).mShowing) {
            return false;
        }
        if (!this.activityIntentHelper.wouldPendingShowOverLockscreen(((NotificationLockscreenUserManagerImpl) this.lockscreenUserManager).mCurrentUserId, pendingIntent)) {
            return false;
        }
        try {
            if (expandable != null) {
                this.activityStarter.startPendingIntentMaybeDismissingKeyguard(pendingIntent, null, expandable.activityTransitionController(31));
                return true;
            }
            BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
            makeBasic.setInteractive(true);
            makeBasic.setPendingIntentBackgroundActivityStartMode(1);
            pendingIntent.send(makeBasic.toBundle());
            return true;
        } catch (PendingIntent.CanceledException unused) {
            Log.e("MediaControlInteractor", "pending intent of " + this.instanceId + " was canceled");
            return true;
        }
    }
}
