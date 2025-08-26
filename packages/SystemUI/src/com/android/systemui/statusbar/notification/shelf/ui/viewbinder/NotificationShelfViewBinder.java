package com.android.systemui.statusbar.notification.shelf.ui.viewbinder;

import android.os.Trace;
import android.util.Log;
import android.view.View;
import com.android.app.tracing.TraceUtilsKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.Dependency;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.shade.domain.interactor.SecPanelSAStatusLogInteractor;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.NotificationShelf;
import com.android.systemui.statusbar.NotificationShelfManager;
import com.android.systemui.statusbar.notification.NotificationActivityStarter;
import com.android.systemui.statusbar.notification.icon.ui.viewbinder.NotificationIconContainerShelfViewBinder;
import com.android.systemui.statusbar.notification.row.ui.viewbinder.ActivatableNotificationViewBinder;
import com.android.systemui.statusbar.notification.shelf.domain.interactor.NotificationShelfInteractor;
import com.android.systemui.statusbar.notification.shelf.ui.viewmodel.NotificationShelfViewModel;
import com.android.systemui.statusbar.phone.NotificationIconAreaController;
import com.android.systemui.util.SystemUIAnalytics;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.DelayKt;

/* loaded from: classes3.dex */
public final class NotificationShelfViewBinder {
    public static final NotificationShelfViewBinder INSTANCE = new NotificationShelfViewBinder();

    /* renamed from: com.android.systemui.statusbar.notification.shelf.ui.viewbinder.NotificationShelfViewBinder$bind$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ FalsingManager $falsingManager;
        final /* synthetic */ NotificationIconContainerShelfViewBinder $nicBinder;
        final /* synthetic */ NotificationActivityStarter $notificationActivityStarter;
        final /* synthetic */ NotificationIconAreaController $notificationIconAreaController;
        final /* synthetic */ NotificationShelf $shelf;
        final /* synthetic */ NotificationShelfManager $shelfManager;
        final /* synthetic */ NotificationShelfViewModel $viewModel;
        private /* synthetic */ Object L$0;
        int label;

        /* renamed from: com.android.systemui.statusbar.notification.shelf.ui.viewbinder.NotificationShelfViewBinder$bind$2$1, reason: invalid class name */
        final class AnonymousClass1 extends SuspendLambda implements Function2 {
            final /* synthetic */ NotificationShelfManager $shelfManager;
            final /* synthetic */ NotificationShelfViewModel $viewModel;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public AnonymousClass1(NotificationShelfManager notificationShelfManager, NotificationShelfViewModel notificationShelfViewModel, Continuation continuation) {
                super(2, continuation);
                this.$shelfManager = notificationShelfManager;
                this.$viewModel = notificationShelfViewModel;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new AnonymousClass1(this.$shelfManager, this.$viewModel, continuation);
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
                    NotificationShelfViewBinder notificationShelfViewBinder = NotificationShelfViewBinder.INSTANCE;
                    NotificationShelfManager notificationShelfManager = this.$shelfManager;
                    NotificationShelfViewModel notificationShelfViewModel = this.$viewModel;
                    this.label = 1;
                    notificationShelfViewBinder.getClass();
                    if (CoroutineScopeKt.coroutineScope(new NotificationShelfViewBinder$bindClearAllButton$2(notificationShelfViewModel, notificationShelfManager, null), this) == coroutineSingletons) {
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
        }

        /* renamed from: com.android.systemui.statusbar.notification.shelf.ui.viewbinder.NotificationShelfViewBinder$bind$2$2, reason: invalid class name and collision with other inner class name */
        final class C05052 extends SuspendLambda implements Function2 {
            final /* synthetic */ NotificationActivityStarter $notificationActivityStarter;
            final /* synthetic */ NotificationShelfManager $shelfManager;
            int label;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C05052(NotificationShelfManager notificationShelfManager, NotificationActivityStarter notificationActivityStarter, Continuation continuation) {
                super(2, continuation);
                this.$shelfManager = notificationShelfManager;
                this.$notificationActivityStarter = notificationActivityStarter;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                return new C05052(this.$shelfManager, this.$notificationActivityStarter, continuation);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C05052) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    NotificationShelfViewBinder notificationShelfViewBinder = NotificationShelfViewBinder.INSTANCE;
                    NotificationShelfManager notificationShelfManager = this.$shelfManager;
                    NotificationActivityStarter notificationActivityStarter = this.$notificationActivityStarter;
                    this.label = 1;
                    notificationShelfViewBinder.getClass();
                    Object objCoroutineScope = CoroutineScopeKt.coroutineScope(new NotificationShelfViewBinder$bindSettingsButton$2(notificationShelfManager, notificationActivityStarter, null), this);
                    if (objCoroutineScope != obj2) {
                        objCoroutineScope = Unit.INSTANCE;
                    }
                    if (objCoroutineScope == obj2) {
                        return obj2;
                    }
                } else {
                    if (i != 1) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                }
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(NotificationShelfViewModel notificationShelfViewModel, NotificationShelf notificationShelf, FalsingManager falsingManager, NotificationShelfManager notificationShelfManager, NotificationActivityStarter notificationActivityStarter, NotificationIconAreaController notificationIconAreaController, NotificationIconContainerShelfViewBinder notificationIconContainerShelfViewBinder, Continuation continuation) {
            super(2, continuation);
            this.$viewModel = notificationShelfViewModel;
            this.$shelf = notificationShelf;
            this.$falsingManager = falsingManager;
            this.$shelfManager = notificationShelfManager;
            this.$notificationActivityStarter = notificationActivityStarter;
            this.$notificationIconAreaController = notificationIconAreaController;
            this.$nicBinder = notificationIconContainerShelfViewBinder;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$viewModel, this.$shelf, this.$falsingManager, this.$shelfManager, this.$notificationActivityStarter, this.$notificationIconAreaController, this.$nicBinder, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                ActivatableNotificationViewBinder activatableNotificationViewBinder = ActivatableNotificationViewBinder.INSTANCE;
                NotificationShelfViewModel notificationShelfViewModel = this.$viewModel;
                NotificationShelf notificationShelf = this.$shelf;
                FalsingManager falsingManager = this.$falsingManager;
                activatableNotificationViewBinder.getClass();
                ActivatableNotificationViewBinder.bind(notificationShelfViewModel, notificationShelf, falsingManager);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass1(this.$shelfManager, this.$viewModel, null), 7);
                CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new C05052(this.$shelfManager, this.$notificationActivityStarter, null), 7);
                NotificationShelf notificationShelf2 = this.$shelf;
                NotificationShelfViewModel notificationShelfViewModel2 = this.$viewModel;
                NotificationIconAreaController notificationIconAreaController = this.$notificationIconAreaController;
                boolean zIsEnabled = Trace.isEnabled();
                if (zIsEnabled) {
                    TraceUtilsKt.beginSlice("NotifShelf#bindShelfIcons");
                }
                try {
                    notificationIconAreaController.setShelfIcons(notificationShelf2.getShelfIcons());
                    Unit unit = Unit.INSTANCE;
                    CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new NotificationShelfViewBinder$bind$2$3$2(notificationShelfViewModel2, notificationShelf2, null), 7);
                    CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new NotificationShelfViewBinder$bind$2$3$3(notificationShelfViewModel2, notificationShelf2, null), 7);
                    NotificationShelfViewBinder notificationShelfViewBinder = NotificationShelfViewBinder.INSTANCE;
                    this.L$0 = notificationShelf2;
                    this.label = 1;
                    if (NotificationShelfViewBinder.access$registerViewListenersWhileAttached(notificationShelfViewBinder, notificationShelf2, notificationShelfViewModel2, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                } finally {
                    if (zIsEnabled) {
                        TraceUtilsKt.endSlice();
                    }
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    private NotificationShelfViewBinder() {
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final CoroutineSingletons access$registerViewListenersWhileAttached(NotificationShelfViewBinder notificationShelfViewBinder, NotificationShelf notificationShelf, final NotificationShelfViewModel notificationShelfViewModel, ContinuationImpl continuationImpl) {
        NotificationShelfViewBinder$registerViewListenersWhileAttached$1 notificationShelfViewBinder$registerViewListenersWhileAttached$1;
        notificationShelfViewBinder.getClass();
        if (continuationImpl instanceof NotificationShelfViewBinder$registerViewListenersWhileAttached$1) {
            notificationShelfViewBinder$registerViewListenersWhileAttached$1 = (NotificationShelfViewBinder$registerViewListenersWhileAttached$1) continuationImpl;
            int i = notificationShelfViewBinder$registerViewListenersWhileAttached$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                notificationShelfViewBinder$registerViewListenersWhileAttached$1.label = i - Integer.MIN_VALUE;
            } else {
                notificationShelfViewBinder$registerViewListenersWhileAttached$1 = new NotificationShelfViewBinder$registerViewListenersWhileAttached$1(notificationShelfViewBinder, continuationImpl);
            }
        }
        Object obj = notificationShelfViewBinder$registerViewListenersWhileAttached$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = notificationShelfViewBinder$registerViewListenersWhileAttached$1.label;
        try {
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                notificationShelf.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.statusbar.notification.shelf.ui.viewbinder.NotificationShelfViewBinder$registerViewListenersWhileAttached$2
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        NotificationShelfInteractor notificationShelfInteractor = notificationShelfViewModel.interactor;
                        notificationShelfInteractor.powerInteractor.wakeUpIfDozing(4, "SHADE_CLICK");
                        ((SecPanelSAStatusLogInteractor) Dependency.sDependency.getDependencyInner(SecPanelSAStatusLogInteractor.class)).countOpenNotificationPanelFromLockscreen();
                        if (notificationShelfInteractor.statusBarStateControllerImpl.mState == 1) {
                            LockscreenShadeTransitionController.Companion companion = LockscreenShadeTransitionController.Companion;
                            notificationShelfInteractor.keyguardTransitionController.goToLockedShade(null, true);
                        } else {
                            Log.d("NotificationShelfInteractor", "goToLockedShadeFromShelf - collapse shade");
                            notificationShelfInteractor.shadeControllerImpl.animateCollapseShade(0);
                        }
                        SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_OPEN_NOTIFICATION_LIST, SystemUIAnalytics.DID_TAP_MORE_STRIP);
                    }
                });
                notificationShelfViewBinder$registerViewListenersWhileAttached$1.L$0 = notificationShelf;
                notificationShelfViewBinder$registerViewListenersWhileAttached$1.label = 1;
                if (DelayKt.awaitCancellation(notificationShelfViewBinder$registerViewListenersWhileAttached$1) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i2 != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                notificationShelf = (NotificationShelf) notificationShelfViewBinder$registerViewListenersWhileAttached$1.L$0;
                ResultKt.throwOnFailure(obj);
            }
            throw new KotlinNothingValueException();
        } catch (Throwable th) {
            notificationShelf.setOnClickListener(null);
            throw th;
        }
    }

    public static Object bind(NotificationShelfViewModel notificationShelfViewModel, NotificationShelf notificationShelf, FalsingManager falsingManager, NotificationShelfManager notificationShelfManager, NotificationActivityStarter notificationActivityStarter, NotificationIconAreaController notificationIconAreaController, NotificationIconContainerShelfViewBinder notificationIconContainerShelfViewBinder, Continuation continuation) {
        Object objCoroutineScope = CoroutineScopeKt.coroutineScope(new AnonymousClass2(notificationShelfViewModel, notificationShelf, falsingManager, notificationShelfManager, notificationActivityStarter, notificationIconAreaController, notificationIconContainerShelfViewBinder, null), continuation);
        return objCoroutineScope == CoroutineSingletons.COROUTINE_SUSPENDED ? objCoroutineScope : Unit.INSTANCE;
    }
}
