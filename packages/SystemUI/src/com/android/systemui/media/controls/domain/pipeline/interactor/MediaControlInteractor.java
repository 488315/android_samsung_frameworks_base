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
import com.android.systemui.media.controls.domain.pipeline.MediaActionsKt;
import com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor;
import com.android.systemui.media.controls.shared.MediaLogger;
import com.android.systemui.media.controls.shared.model.MediaControlModel;
import com.android.systemui.media.controls.shared.model.MediaData;
import com.android.systemui.media.dialog.MediaOutputDialogManager;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    MediaControlModel mediaControlModel;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        MediaControlInteractor mediaControlInteractor = this.this$0;
                        MediaData mediaData = (MediaData) ((Map) obj).get(mediaControlInteractor.instanceId);
                        if (mediaData != null) {
                            mediaControlModel = new MediaControlModel(mediaData.appUid, mediaData.packageName, mediaData.instanceId, mediaData.token, mediaData.appIcon, mediaData.clickIntent, mediaData.app, mediaData.song, mediaData.artist, mediaData.isExplicit, mediaData.artwork, mediaData.device, mediaData.semanticActions, MediaActionsKt.getNotificationActions(mediaData.actions, mediaControlInteractor.activityStarter), mediaData.actionsToShowInCompact, mediaData.isClearable, mediaData.resumption, mediaData.resumeProgress);
                        } else {
                            mediaControlModel = null;
                        }
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(mediaControlModel, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
    }

    public final boolean launchOverLockscreen(Expandable expandable, PendingIntent pendingIntent) throws PendingIntent.CanceledException {
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
            BroadcastOptions broadcastOptionsMakeBasic = BroadcastOptions.makeBasic();
            broadcastOptionsMakeBasic.setInteractive(true);
            broadcastOptionsMakeBasic.setPendingIntentBackgroundActivityStartMode(1);
            pendingIntent.send(broadcastOptionsMakeBasic.toBundle());
            return true;
        } catch (PendingIntent.CanceledException unused) {
            Log.e("MediaControlInteractor", "pending intent of " + this.instanceId + " was canceled");
            return true;
        }
    }
}
