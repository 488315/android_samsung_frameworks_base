package com.android.systemui.scene.domain.interactor;

import android.os.RemoteException;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.CoreStartable;
import com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor;
import com.android.systemui.keyguard.data.repository.KeyguardRepository;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.keyguard.shared.model.StatusBarState;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.scene.data.repository.WindowRootViewVisibilityRepository;
import com.android.systemui.statusbar.NotificationPresenter;
import com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManager;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl;
import com.android.systemui.statusbar.phone.StatusBarNotificationPresenter;
import javax.inject.Provider;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* loaded from: classes2.dex */
public final class WindowRootViewVisibilityInteractor implements CoreStartable {
    public final ActiveNotificationsInteractor activeNotificationsInteractor;
    public final HeadsUpManager headsUpManager;
    public final ReadonlyStateFlow isLockscreenOrShadeVisible;
    public final ReadonlyStateFlow isLockscreenOrShadeVisibleAndInteractive;
    public final KeyguardRepository keyguardRepository;
    public NotificationPresenter notificationPresenter;
    public final CoroutineScope scope;
    public final WindowRootViewVisibilityRepository windowRootViewVisibilityRepository;

    /* renamed from: com.android.systemui.scene.domain.interactor.WindowRootViewVisibilityInteractor$start$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return WindowRootViewVisibilityInteractor.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final WindowRootViewVisibilityInteractor windowRootViewVisibilityInteractor = WindowRootViewVisibilityInteractor.this;
                ReadonlyStateFlow readonlyStateFlow = windowRootViewVisibilityInteractor.isLockscreenOrShadeVisibleAndInteractive;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.scene.domain.interactor.WindowRootViewVisibilityInteractor.start.1.1
                    /* JADX WARN: Removed duplicated region for block: B:23:0x004f  */
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object emit(Object obj2, Continuation continuation) {
                        boolean zBooleanValue = ((Boolean) obj2).booleanValue();
                        WindowRootViewVisibilityInteractor windowRootViewVisibilityInteractor2 = windowRootViewVisibilityInteractor;
                        if (zBooleanValue) {
                            final WindowRootViewVisibilityRepository windowRootViewVisibilityRepository = windowRootViewVisibilityInteractor2.windowRootViewVisibilityRepository;
                            StatusBarState statusBarState = (StatusBarState) ((KeyguardRepositoryImpl) windowRootViewVisibilityInteractor2.keyguardRepository).statusBarState.$$delegate_0.getValue();
                            NotificationPresenter notificationPresenter = windowRootViewVisibilityInteractor2.notificationPresenter;
                            final int allNotificationsCountValue = 1;
                            final boolean z = !(notificationPresenter != null ? ((StatusBarNotificationPresenter) notificationPresenter).mPanelExpansionInteractor.isFullyCollapsed() : true) && (statusBarState == StatusBarState.SHADE || statusBarState == StatusBarState.SHADE_LOCKED);
                            if (((HeadsUpManagerImpl) windowRootViewVisibilityInteractor2.headsUpManager).mHasPinnedNotification) {
                                NotificationPresenter notificationPresenter2 = windowRootViewVisibilityInteractor2.notificationPresenter;
                                if (!(notificationPresenter2 != null ? ((StatusBarNotificationPresenter) notificationPresenter2).mPanelExpansionInteractor.isFullyCollapsed() : true)) {
                                }
                                windowRootViewVisibilityRepository.getClass();
                                final Function0 function0 = new Function0() { // from class: com.android.systemui.scene.data.repository.WindowRootViewVisibilityRepository$$ExternalSyntheticLambda0
                                    @Override // kotlin.jvm.functions.Function0
                                    public final Object invoke() {
                                        windowRootViewVisibilityRepository.statusBarService.onPanelRevealed(z, allNotificationsCountValue);
                                        return Unit.INSTANCE;
                                    }
                                };
                                windowRootViewVisibilityRepository.uiBgExecutor.execute(new Runnable() { // from class: com.android.systemui.scene.data.repository.WindowRootViewVisibilityRepository$executeServiceCallOnUiBg$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        try {
                                            function0.invoke();
                                        } catch (RemoteException unused) {
                                        }
                                    }
                                });
                            } else {
                                allNotificationsCountValue = windowRootViewVisibilityInteractor2.activeNotificationsInteractor.getAllNotificationsCountValue();
                                windowRootViewVisibilityRepository.getClass();
                                final Function0 function02 = new Function0() { // from class: com.android.systemui.scene.data.repository.WindowRootViewVisibilityRepository$$ExternalSyntheticLambda0
                                    @Override // kotlin.jvm.functions.Function0
                                    public final Object invoke() {
                                        windowRootViewVisibilityRepository.statusBarService.onPanelRevealed(z, allNotificationsCountValue);
                                        return Unit.INSTANCE;
                                    }
                                };
                                windowRootViewVisibilityRepository.uiBgExecutor.execute(new Runnable() { // from class: com.android.systemui.scene.data.repository.WindowRootViewVisibilityRepository$executeServiceCallOnUiBg$1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        try {
                                            function02.invoke();
                                        } catch (RemoteException unused) {
                                        }
                                    }
                                });
                            }
                        } else {
                            final WindowRootViewVisibilityRepository windowRootViewVisibilityRepository2 = windowRootViewVisibilityInteractor2.windowRootViewVisibilityRepository;
                            windowRootViewVisibilityRepository2.getClass();
                            final Function0 function03 = new Function0() { // from class: com.android.systemui.scene.data.repository.WindowRootViewVisibilityRepository$$ExternalSyntheticLambda1
                                @Override // kotlin.jvm.functions.Function0
                                public final Object invoke() {
                                    windowRootViewVisibilityRepository2.statusBarService.onPanelHidden();
                                    return Unit.INSTANCE;
                                }
                            };
                            windowRootViewVisibilityRepository2.uiBgExecutor.execute(new Runnable() { // from class: com.android.systemui.scene.data.repository.WindowRootViewVisibilityRepository$executeServiceCallOnUiBg$1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    try {
                                        function03.invoke();
                                    } catch (RemoteException unused) {
                                    }
                                }
                            });
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (readonlyStateFlow.$$delegate_0.collect(flowCollector, this) == coroutineSingletons) {
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
    }

    public WindowRootViewVisibilityInteractor(CoroutineScope coroutineScope, WindowRootViewVisibilityRepository windowRootViewVisibilityRepository, KeyguardRepository keyguardRepository, HeadsUpManager headsUpManager, PowerInteractor powerInteractor, ActiveNotificationsInteractor activeNotificationsInteractor, Provider provider, PrimaryBouncerInteractor primaryBouncerInteractor) {
        this.scope = coroutineScope;
        this.windowRootViewVisibilityRepository = windowRootViewVisibilityRepository;
        this.keyguardRepository = keyguardRepository;
        this.headsUpManager = headsUpManager;
        this.activeNotificationsInteractor = activeNotificationsInteractor;
        ReadonlyStateFlow readonlyStateFlow = windowRootViewVisibilityRepository.isLockscreenOrShadeVisible;
        this.isLockscreenOrShadeVisible = readonlyStateFlow;
        FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1Combine = FlowKt.combine(primaryBouncerInteractor.isShowing, readonlyStateFlow, powerInteractor.isAwake, new WindowRootViewVisibilityInteractor$isLockscreenOrShadeVisibleAndInteractive$1(null));
        SharingStarted.Companion.getClass();
        this.isLockscreenOrShadeVisibleAndInteractive = FlowKt.stateIn(flowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1Combine, coroutineScope, SharingStarted.Companion.Eagerly, Boolean.FALSE);
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        CoroutineTracingKt.launchTraced$default(this.scope, null, null, new AnonymousClass1(null), 7);
    }
}
