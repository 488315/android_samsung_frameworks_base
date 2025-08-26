package com.android.systemui.statusbar.notification.collection.coordinator;

import android.app.Notification;
import android.app.NotificationChannel;
import android.util.Log;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.compose.animation.scene.ObservableTransitionState;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.NotiRune;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.scene.domain.interactor.SceneInteractor;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.statusbar.KeyguardBatteryStatus;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.notification.DynamicPrivacyController;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.PipelineEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.Invalidator;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityData;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityDataHelper;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityDataHelper$mOnHideRawValueChangedListener$1;
import com.android.systemui.statusbar.policy.AppLockNotificationController;
import com.android.systemui.statusbar.policy.AppLockNotificationControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.policy.SensitiveNotificationProtectionController;
import com.android.systemui.statusbar.policy.SensitiveNotificationProtectionControllerImpl;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.cover.CoverState;
import java.util.Iterator;
import java.util.List;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.sequences.FilteringSequence;
import kotlin.sequences.FilteringSequence.AnonymousClass1;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlow;

@CoordinatorScope
/* loaded from: classes3.dex */
public final class SensitiveContentCoordinatorImpl extends Invalidator implements SensitiveContentCoordinator, DynamicPrivacyController.Listener, OnBeforeRenderListListener {
    public static final int $stable = 8;
    private final AppLockNotificationController appLockNotificationController;
    private boolean canSwipeToEnter;
    private final DeviceEntryInteractor deviceEntryInteractor;
    private final DynamicPrivacyController dynamicPrivacyController;
    private boolean inTransitionFromLockedToGone;
    private final KeyguardStateController keyguardStateController;
    private final KeyguardUpdateMonitor keyguardUpdateMonitor;
    private SensitiveContentCoordinatorImpl$keyguardUpdateMonitorCallback$1 keyguardUpdateMonitorCallback;
    private final NotificationLockscreenUserManager lockscreenUserManager;
    private boolean needUpdateNext;
    private final Runnable onSensitiveStateChanged;
    private final SceneInteractor sceneInteractor;
    private final CoroutineScope scope;
    private final SensitiveContentCoordinatorImpl$screenshareSecretFilter$1 screenshareSecretFilter;
    private final SelectedUserInteractor selectedUserInteractor;
    private final SensitiveNotificationProtectionController sensitiveNotificationProtectionController;
    private final SettingsHelper settingsHelper;
    private final StatusBarStateController statusBarStateController;

    /* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.SensitiveContentCoordinatorImpl$attach$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return SensitiveContentCoordinatorImpl.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final ReadonlyStateFlow readonlyStateFlow = SensitiveContentCoordinatorImpl.this.sceneInteractor.transitionState;
                final SensitiveContentCoordinatorImpl sensitiveContentCoordinatorImpl = SensitiveContentCoordinatorImpl.this;
                Flow flowDistinctUntilChanged = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.SensitiveContentCoordinatorImpl$attach$1$invokeSuspend$$inlined$mapNotNull$1

                    /* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.SensitiveContentCoordinatorImpl$attach$1$invokeSuspend$$inlined$mapNotNull$1$2, reason: invalid class name */
                    public final class AnonymousClass2<T> implements FlowCollector {
                        final /* synthetic */ FlowCollector $this_unsafeFlow;
                        final /* synthetic */ SensitiveContentCoordinatorImpl this$0;

                        /* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.SensitiveContentCoordinatorImpl$attach$1$invokeSuspend$$inlined$mapNotNull$1$2$1, reason: invalid class name */
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

                        public AnonymousClass2(FlowCollector flowCollector, SensitiveContentCoordinatorImpl sensitiveContentCoordinatorImpl) {
                            this.$this_unsafeFlow = flowCollector;
                            this.this$0 = sensitiveContentCoordinatorImpl;
                        }

                        /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object emit(Object obj, Continuation continuation) {
                            AnonymousClass1 anonymousClass1;
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
                                FlowCollector flowCollector = this.$this_unsafeFlow;
                                Boolean bool = null;
                                boolean zIsTransitioning$default = ObservableTransitionState.isTransitioning$default((ObservableTransitionState) obj, null, Scenes.Gone, 1);
                                boolean zBooleanValue = ((Boolean) this.this$0.deviceEntryInteractor.isDeviceEntered.$$delegate_0.getValue()).booleanValue();
                                if (zIsTransitioning$default && !zBooleanValue) {
                                    bool = Boolean.TRUE;
                                } else if (!zIsTransitioning$default) {
                                    bool = Boolean.FALSE;
                                }
                                if (bool != null) {
                                    anonymousClass1.label = 1;
                                    if (flowCollector.emit(bool, anonymousClass1) == coroutineSingletons) {
                                        return coroutineSingletons;
                                    }
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
                    public Object collect(FlowCollector flowCollector, Continuation continuation) {
                        Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector, sensitiveContentCoordinatorImpl), continuation);
                        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                    }
                });
                final SensitiveContentCoordinatorImpl sensitiveContentCoordinatorImpl2 = SensitiveContentCoordinatorImpl.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.SensitiveContentCoordinatorImpl.attach.1.2
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                        return emit(((Boolean) obj2).booleanValue(), continuation);
                    }

                    public final Object emit(boolean z, Continuation continuation) {
                        sensitiveContentCoordinatorImpl2.inTransitionFromLockedToGone = z;
                        sensitiveContentCoordinatorImpl2.invalidateList("inTransitionFromLockedToGoneChanged");
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (flowDistinctUntilChanged.collect(flowCollector, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.SensitiveContentCoordinatorImpl$attach$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return SensitiveContentCoordinatorImpl.this.new AnonymousClass2(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                StateFlow stateFlow = (StateFlow) SensitiveContentCoordinatorImpl.this.deviceEntryInteractor.canSwipeToEnter$delegate.getValue();
                final SensitiveContentCoordinatorImpl sensitiveContentCoordinatorImpl = SensitiveContentCoordinatorImpl.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.SensitiveContentCoordinatorImpl.attach.2.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Boolean bool, Continuation continuation) {
                        boolean zBooleanValue = bool != null ? bool.booleanValue() : false;
                        if (sensitiveContentCoordinatorImpl.canSwipeToEnter != zBooleanValue) {
                            sensitiveContentCoordinatorImpl.canSwipeToEnter = zBooleanValue;
                            sensitiveContentCoordinatorImpl.invalidateList("canSwipeToEnterChanged");
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (stateFlow.collect(flowCollector, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass2) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.systemui.statusbar.notification.collection.coordinator.SensitiveContentCoordinatorImpl$keyguardUpdateMonitorCallback$1] */
    /* JADX WARN: Type inference failed for: r2v3, types: [com.android.systemui.statusbar.notification.collection.coordinator.SensitiveContentCoordinatorImpl$screenshareSecretFilter$1] */
    public SensitiveContentCoordinatorImpl(DynamicPrivacyController dynamicPrivacyController, NotificationLockscreenUserManager notificationLockscreenUserManager, KeyguardUpdateMonitor keyguardUpdateMonitor, StatusBarStateController statusBarStateController, KeyguardStateController keyguardStateController, SelectedUserInteractor selectedUserInteractor, SensitiveNotificationProtectionController sensitiveNotificationProtectionController, DeviceEntryInteractor deviceEntryInteractor, SceneInteractor sceneInteractor, CoroutineScope coroutineScope, SettingsHelper settingsHelper, AppLockNotificationController appLockNotificationController) {
        super("SensitiveContentInvalidator");
        this.dynamicPrivacyController = dynamicPrivacyController;
        this.lockscreenUserManager = notificationLockscreenUserManager;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.statusBarStateController = statusBarStateController;
        this.keyguardStateController = keyguardStateController;
        this.selectedUserInteractor = selectedUserInteractor;
        this.sensitiveNotificationProtectionController = sensitiveNotificationProtectionController;
        this.deviceEntryInteractor = deviceEntryInteractor;
        this.sceneInteractor = sceneInteractor;
        this.scope = coroutineScope;
        this.settingsHelper = settingsHelper;
        this.appLockNotificationController = appLockNotificationController;
        this.onSensitiveStateChanged = new Runnable() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.SensitiveContentCoordinatorImpl$onSensitiveStateChanged$1
            @Override // java.lang.Runnable
            public final void run() {
                this.this$0.invalidateList("onSensitiveStateChanged");
            }
        };
        this.keyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.SensitiveContentCoordinatorImpl$keyguardUpdateMonitorCallback$1
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onKeyguardVisibilityChanged(boolean z) {
                if (z || !((KeyguardStateControllerImpl) this.this$0.keyguardStateController).mKeyguardGoingAway) {
                    return;
                }
                this.this$0.needUpdateNext = true;
                this.this$0.invalidateList("onKeyguardVisibilityChanged");
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onBiometricLockoutChanged(boolean z) {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onDlsViewModeChanged(int i) {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onDualDARInnerLockscreenRequirementChanged(int i) {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onEmergencyStateChanged(int i) {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onFaceWidgetFullscreenModeChanged(boolean z) {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onLockDisabledChanged(boolean z) {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onPackageAdded(String str) {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onPackageChanged(String str) {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onPackageDataCleared(String str) {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onPrimaryBouncerVisibilityChanged(boolean z) {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onRefreshBatteryInfo(KeyguardBatteryStatus keyguardBatteryStatus) {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onSecurityViewChanged(KeyguardSecurityModel.SecurityMode securityMode) {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onSimulationFailToUnlock(int i) {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onTableModeChanged(boolean z) {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onUSBRestrictionChanged(boolean z) {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onUpdateCoverState(CoverState coverState) {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onFailedUnlockAttemptChanged() {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onLocaleChanged() {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onLockModeChanged() {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onOfflineStateChanged() {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onOwnerInfoChanged() {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onRemoteLockInfoChanged() {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onSystemDialogsShowing() {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onUdfpsFingerDown() {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onUdfpsFingerUp() {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onUnlocking() {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onDualDarInnerLockScreenStateChanged(int i, boolean z) {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onPackageRemoved(String str, boolean z) {
            }
        };
        this.screenshareSecretFilter = new NotifFilter() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.SensitiveContentCoordinatorImpl$screenshareSecretFilter$1
            {
                super("ScreenshareSecretFilter");
            }

            public final boolean isSecret(NotificationEntry notificationEntry) {
                NotificationChannel channel = notificationEntry.mRanking.getChannel();
                if (channel != null && channel.getLockscreenVisibility() == -1) {
                    return true;
                }
                Notification notification2 = notificationEntry.mSbn.getNotification();
                return notification2 != null && notification2.visibility == -1;
            }

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter
            public boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
                return ((SensitiveNotificationProtectionControllerImpl) this.this$0.sensitiveNotificationProtectionController).isSensitiveStateActive() && isSecret(notificationEntry);
            }
        };
    }

    private final boolean isKeyguardGoingAway() {
        return ((KeyguardStateControllerImpl) this.keyguardStateController).mKeyguardGoingAway;
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        this.dynamicPrivacyController.mListeners.add(this);
        SensitiveNotificationProtectionController sensitiveNotificationProtectionController = this.sensitiveNotificationProtectionController;
        ((SensitiveNotificationProtectionControllerImpl) sensitiveNotificationProtectionController).mListeners.addIfAbsent(this.onSensitiveStateChanged);
        notifPipeline.addOnBeforeRenderListListener(this);
        notifPipeline.addPreRenderInvalidator(this);
        notifPipeline.addFinalizeFilter(this.screenshareSecretFilter);
        this.keyguardUpdateMonitor.registerCallback(this.keyguardUpdateMonitorCallback);
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x00a5  */
    @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeRenderListListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onBeforeRenderList(List<? extends PipelineEntry> list) {
        boolean zShouldHideNotiForAppLock;
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4 = NotiRune.NOTI_LOCKSCREEN_ALWAYS_HIDE_SENSITIVE;
        boolean z5 = false;
        if (z4) {
            this.keyguardUpdateMonitor.setHasRedactedNotifications(false);
        }
        if (!isKeyguardGoingAway() || this.needUpdateNext) {
            boolean z6 = true;
            if (this.statusBarStateController.getState() == 1 && this.keyguardUpdateMonitor.getUserUnlockedWithBiometricAndIsBypassing(this.selectedUserInteractor.getSelectedUserId())) {
                return;
            }
            this.needUpdateNext = false;
            boolean zIsSensitiveStateActive = ((SensitiveNotificationProtectionControllerImpl) this.sensitiveNotificationProtectionController).isSensitiveStateActive();
            NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl = (NotificationLockscreenUserManagerImpl) this.lockscreenUserManager;
            int i = notificationLockscreenUserManagerImpl.mCurrentUserId;
            boolean zIsLockscreenPublicMode = notificationLockscreenUserManagerImpl.isLockscreenPublicMode(i);
            boolean z7 = (zIsLockscreenPublicMode && !((NotificationLockscreenUserManagerImpl) this.lockscreenUserManager).userAllowsPrivateNotificationsInPublic(i)) || zIsSensitiveStateActive;
            boolean zIsDynamicallyUnlocked = this.dynamicPrivacyController.isDynamicallyUnlocked();
            boolean zIsAllowPrivateNotificationsWhenUnsecure = z4 ? this.settingsHelper.isAllowPrivateNotificationsWhenUnsecure(i) : false;
            FilteringSequence.AnonymousClass1 anonymousClass1 = SequencesKt___SequencesKt.filter(SensitiveContentCoordinatorKt.extractAllRepresentativeEntries(list), new SensitiveContentCoordinatorImpl$$ExternalSyntheticLambda0()).new AnonymousClass1();
            while (anonymousClass1.hasNext()) {
                NotificationEntry notificationEntry = (NotificationEntry) anonymousClass1.next();
                int identifier = notificationEntry.mSbn.getUser().getIdentifier();
                boolean z8 = (zIsLockscreenPublicMode || ((NotificationLockscreenUserManagerImpl) this.lockscreenUserManager).isLockscreenPublicMode(identifier)) ? z6 : z5;
                if (NotiRune.NOTI_LOCKSCREEN_ALWAYS_HIDE_SENSITIVE && zIsAllowPrivateNotificationsWhenUnsecure) {
                    if (z8) {
                        z8 = !zIsDynamicallyUnlocked ? z6 : (identifier == i || identifier == -1) ? z5 : ((NotificationLockscreenUserManagerImpl) this.lockscreenUserManager).mUsersWithSeparateWorkChallenge.get(identifier, z5);
                    }
                }
                boolean zShouldProtectNotification = ((SensitiveNotificationProtectionControllerImpl) this.sensitiveNotificationProtectionController).shouldProtectNotification(notificationEntry);
                boolean z9 = ((NotificationLockscreenUserManagerImpl) this.lockscreenUserManager).getRedactionType(notificationEntry) != 0 ? z6 : z5;
                boolean z10 = (z8 && z9) ? z6 : z5;
                ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
                if (expandableNotificationRow == null || expandableNotificationRow.isInsignificantSummary() != z6) {
                    if (NotiRune.NOTI_SUBSCREEN_NOTIFICATION_FIFTH) {
                        notificationEntry.mUserPublic = z8;
                    }
                    boolean z11 = NotiRune.NOTI_STYLE_APP_LOCK;
                    if (z11) {
                        zShouldHideNotiForAppLock = ((AppLockNotificationControllerImpl) this.appLockNotificationController).shouldHideNotiForAppLock(notificationEntry);
                        ExpandableNotificationRow expandableNotificationRow2 = notificationEntry.row;
                        if (expandableNotificationRow2 != null && expandableNotificationRow2.mShowPublicExpander != (!zShouldHideNotiForAppLock)) {
                            expandableNotificationRow2.mShowPublicExpander = z3;
                            expandableNotificationRow2.mPublicLayout.updateExpandButtonsDuringLayout(z3, false);
                        }
                    } else {
                        zShouldHideNotiForAppLock = false;
                    }
                    notificationEntry.setSensitive(z10 || zShouldProtectNotification || (z11 && zShouldHideNotiForAppLock), z7);
                    boolean z12 = z9 || zShouldProtectNotification || (z11 && zShouldHideNotiForAppLock);
                    if (notificationEntry.mRawValueHide != z12) {
                        notificationEntry.mRawValueHide = z12;
                        Iterator it = notificationEntry.mOnHideRawValueChangedListeners.iterator();
                        while (it.hasNext()) {
                            ((OngoingActivityDataHelper$mOnHideRawValueChangedListener$1) ((NotificationEntry.OnHideRawValueChangedListener) it.next())).getClass();
                            String str = OngoingActivityDataHelper.TAG;
                            StringBuilder sbM = RowView$$ExternalSyntheticOutline0.m("hide change : ", " | ", notificationEntry.mRawValueHide);
                            String str2 = notificationEntry.mKey;
                            sbM.append(str2);
                            Log.i(str, sbM.toString());
                            OngoingActivityDataHelper.INSTANCE.getClass();
                            OngoingActivityData ongoingActivityDataByKey = OngoingActivityDataHelper.getOngoingActivityDataByKey(str2);
                            if (ongoingActivityDataByKey != null) {
                                OngoingActivityDataHelper.notifyUpdateItemNowbarObservers(OngoingActivityDataHelper.convertOngoingActivityData(ongoingActivityDataByKey));
                            } else {
                                Log.i(str, "updateNowbarItemWhenSensitivityChanged() : ongoingActivityData is null");
                            }
                        }
                    }
                    ExpandableNotificationRow expandableNotificationRow3 = notificationEntry.row;
                    if (expandableNotificationRow3 == null || expandableNotificationRow3.mShowPublicExpander == (!zShouldProtectNotification)) {
                        z = false;
                    } else {
                        expandableNotificationRow3.mShowPublicExpander = z2;
                        z = false;
                        expandableNotificationRow3.mPublicLayout.updateExpandButtonsDuringLayout(z2, false);
                    }
                    if (NotiRune.NOTI_LOCKSCREEN_ALWAYS_HIDE_SENSITIVE && !this.keyguardUpdateMonitor.hasRedactedNotifications() && z9) {
                        this.keyguardUpdateMonitor.setHasRedactedNotifications(z9);
                    }
                    z5 = z;
                    z6 = true;
                } else {
                    notificationEntry.setSensitive(z5, z7);
                }
            }
        }
    }

    @Override // com.android.systemui.statusbar.notification.DynamicPrivacyController.Listener
    public void onDynamicPrivacyChanged() {
        invalidateList("onDynamicPrivacyChanged");
    }

    private static /* synthetic */ void getKeyguardUpdateMonitorCallback$annotations() {
    }

    private static /* synthetic */ void getNeedUpdateNext$annotations() {
    }
}
