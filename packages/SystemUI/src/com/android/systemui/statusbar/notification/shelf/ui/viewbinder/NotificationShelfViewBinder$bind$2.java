package com.android.systemui.statusbar.notification.shelf.ui.viewbinder;

import android.os.Trace;
import com.android.app.tracing.TraceUtilsKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.NotificationShelf;
import com.android.systemui.statusbar.NotificationShelfManager;
import com.android.systemui.statusbar.notification.NotificationActivityStarter;
import com.android.systemui.statusbar.notification.icon.ui.viewbinder.NotificationIconContainerShelfViewBinder;
import com.android.systemui.statusbar.notification.row.ui.viewbinder.ActivatableNotificationViewBinder;
import com.android.systemui.statusbar.notification.shelf.ui.viewmodel.NotificationShelfViewModel;
import com.android.systemui.statusbar.phone.NotificationIconAreaController;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class NotificationShelfViewBinder$bind$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ FalsingManager $falsingManager;
    final /* synthetic */ NotificationIconContainerShelfViewBinder $nicBinder;
    final /* synthetic */ NotificationActivityStarter $notificationActivityStarter;
    final /* synthetic */ NotificationIconAreaController $notificationIconAreaController;
    final /* synthetic */ NotificationShelf $shelf;
    final /* synthetic */ NotificationShelfManager $shelfManager;
    final /* synthetic */ NotificationShelfViewModel $viewModel;
    private /* synthetic */ Object L$0;
    int label;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.notification.shelf.ui.viewbinder.NotificationShelfViewBinder$bind$2$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ NotificationActivityStarter $notificationActivityStarter;
        final /* synthetic */ NotificationShelfManager $shelfManager;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(NotificationShelfManager notificationShelfManager, NotificationActivityStarter notificationActivityStarter, Continuation continuation) {
            super(2, continuation);
            this.$shelfManager = notificationShelfManager;
            this.$notificationActivityStarter = notificationActivityStarter;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass2(this.$shelfManager, this.$notificationActivityStarter, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
                Object coroutineScope = CoroutineScopeKt.coroutineScope(new NotificationShelfViewBinder$bindSettingsButton$2(notificationShelfManager, notificationActivityStarter, null), this);
                if (coroutineScope != obj2) {
                    coroutineScope = Unit.INSTANCE;
                }
                if (coroutineScope == obj2) {
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
    public NotificationShelfViewBinder$bind$2(NotificationShelfViewModel notificationShelfViewModel, NotificationShelf notificationShelf, FalsingManager falsingManager, NotificationShelfManager notificationShelfManager, NotificationActivityStarter notificationActivityStarter, NotificationIconAreaController notificationIconAreaController, NotificationIconContainerShelfViewBinder notificationIconContainerShelfViewBinder, Continuation continuation) {
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
        NotificationShelfViewBinder$bind$2 notificationShelfViewBinder$bind$2 = new NotificationShelfViewBinder$bind$2(this.$viewModel, this.$shelf, this.$falsingManager, this.$shelfManager, this.$notificationActivityStarter, this.$notificationIconAreaController, this.$nicBinder, continuation);
        notificationShelfViewBinder$bind$2.L$0 = obj;
        return notificationShelfViewBinder$bind$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((NotificationShelfViewBinder$bind$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
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
            CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass2(this.$shelfManager, this.$notificationActivityStarter, null), 7);
            NotificationShelf notificationShelf2 = this.$shelf;
            NotificationShelfViewModel notificationShelfViewModel2 = this.$viewModel;
            NotificationIconAreaController notificationIconAreaController = this.$notificationIconAreaController;
            boolean isEnabled = Trace.isEnabled();
            if (isEnabled) {
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
                if (isEnabled) {
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
