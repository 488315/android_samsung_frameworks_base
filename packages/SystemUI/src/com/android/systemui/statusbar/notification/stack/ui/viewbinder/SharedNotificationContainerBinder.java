package com.android.systemui.statusbar.notification.stack.ui.viewbinder;

import android.view.View;
import com.android.systemui.common.ui.view.ViewExtKt$onLayoutChanged$2;
import com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.ui.viewmodel.ViewStateAccessor;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.recents.LauncherProxyService$1$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.NotificationShelf;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.notification.stack.NotificationStackSizeCalculator;
import com.android.systemui.statusbar.notification.stack.NotificationStackSizeCalculator$$ExternalSyntheticLambda2;
import com.android.systemui.statusbar.notification.stack.NotificationStackSizeCalculator$$ExternalSyntheticLambda3;
import com.android.systemui.statusbar.notification.stack.ui.view.SharedNotificationContainer;
import com.android.systemui.statusbar.notification.stack.ui.viewmodel.SharedNotificationContainerViewModel;
import com.android.systemui.statusbar.policy.SplitShadeStateControllerImpl;
import com.android.systemui.util.ConvenienceExtensionsKt;
import com.android.systemui.util.kotlin.DisposableHandles;
import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.reflect.KProperty;
import kotlin.sequences.SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlin.sequences.TakeWhileSequence;
import kotlin.sequences.TransformingSequence;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SharedNotificationContainerBinder {
    public final SharedNotificationContainerBinder$$ExternalSyntheticLambda0 calculateMaxNotifications = new Function2() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewbinder.SharedNotificationContainerBinder$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            boolean z;
            final float floatValue = ((Float) obj).floatValue();
            boolean booleanValue = ((Boolean) obj2).booleanValue();
            SharedNotificationContainerBinder sharedNotificationContainerBinder = SharedNotificationContainerBinder.this;
            NotificationStackScrollLayoutController notificationStackScrollLayoutController = sharedNotificationContainerBinder.controller;
            NotificationShelf notificationShelf = notificationStackScrollLayoutController.mView.mShelf;
            int i = 0;
            float height = notificationShelf == null ? 0 : notificationShelf.getHeight();
            NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
            final float f = booleanValue ? height : 0.0f;
            float f2 = floatValue + f;
            final NotificationStackSizeCalculator notificationStackSizeCalculator = sharedNotificationContainerBinder.notificationStackSizeCalculator;
            if (f2 <= 0.0f) {
                notificationStackSizeCalculator.getClass();
            } else {
                SequencesKt__SequenceBuilderKt$sequence$$inlined$Sequence$1 computeHeightPerNotificationLimit = notificationStackSizeCalculator.computeHeightPerNotificationLimit(notificationStackScrollLayout, height);
                if (notificationStackSizeCalculator.mediaDataManager.hasActiveMediaOrRecommendation()) {
                    ((SplitShadeStateControllerImpl) notificationStackSizeCalculator.splitShadeStateController).shouldUseSplitNotificationShade();
                    z = true;
                } else {
                    z = false;
                }
                final int i2 = 0;
                int count = SequencesKt___SequencesKt.count(new TakeWhileSequence(computeHeightPerNotificationLimit, new Function1() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackSizeCalculator$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj3) {
                        boolean z2 = false;
                        float f3 = f;
                        float f4 = floatValue;
                        NotificationStackSizeCalculator notificationStackSizeCalculator2 = notificationStackSizeCalculator;
                        NotificationStackSizeCalculator.StackHeight stackHeight = (NotificationStackSizeCalculator.StackHeight) obj3;
                        switch (i2) {
                            case 0:
                                KProperty[] kPropertyArr = NotificationStackSizeCalculator.$$delegatedProperties;
                                notificationStackSizeCalculator2.getClass();
                                if (!stackHeight.shouldForceIntoShelf && NotificationStackSizeCalculator.canStackFitInSpace(stackHeight, f4, f3) == NotificationStackSizeCalculator.FitResult.FIT) {
                                    z2 = true;
                                }
                                return Boolean.valueOf(z2);
                            default:
                                KProperty[] kPropertyArr2 = NotificationStackSizeCalculator.$$delegatedProperties;
                                notificationStackSizeCalculator2.getClass();
                                if (!stackHeight.shouldForceIntoShelf && NotificationStackSizeCalculator.canStackFitInSpace(stackHeight, f4, f3) != NotificationStackSizeCalculator.FitResult.NO_FIT) {
                                    z2 = true;
                                }
                                return Boolean.valueOf(z2);
                        }
                    }
                })) - 1;
                if (count >= (z ? 2 : 1)) {
                    notificationStackSizeCalculator.saveSpaceOnLockscreen = false;
                } else {
                    notificationStackSizeCalculator.saveSpaceOnLockscreen = true;
                    final int i3 = 1;
                    count = SequencesKt___SequencesKt.count(new TakeWhileSequence(computeHeightPerNotificationLimit, new Function1() { // from class: com.android.systemui.statusbar.notification.stack.NotificationStackSizeCalculator$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo779invoke(Object obj3) {
                            boolean z2 = false;
                            float f3 = f;
                            float f4 = floatValue;
                            NotificationStackSizeCalculator notificationStackSizeCalculator2 = notificationStackSizeCalculator;
                            NotificationStackSizeCalculator.StackHeight stackHeight = (NotificationStackSizeCalculator.StackHeight) obj3;
                            switch (i3) {
                                case 0:
                                    KProperty[] kPropertyArr = NotificationStackSizeCalculator.$$delegatedProperties;
                                    notificationStackSizeCalculator2.getClass();
                                    if (!stackHeight.shouldForceIntoShelf && NotificationStackSizeCalculator.canStackFitInSpace(stackHeight, f4, f3) == NotificationStackSizeCalculator.FitResult.FIT) {
                                        z2 = true;
                                    }
                                    return Boolean.valueOf(z2);
                                default:
                                    KProperty[] kPropertyArr2 = NotificationStackSizeCalculator.$$delegatedProperties;
                                    notificationStackSizeCalculator2.getClass();
                                    if (!stackHeight.shouldForceIntoShelf && NotificationStackSizeCalculator.canStackFitInSpace(stackHeight, f4, f3) != NotificationStackSizeCalculator.FitResult.NO_FIT) {
                                        z2 = true;
                                    }
                                    return Boolean.valueOf(z2);
                            }
                        }
                    })) - 1;
                }
                for (ExpandableView expandableView : SequencesKt___SequencesKt.toList(SequencesKt___SequencesKt.filter(new TransformingSequence(ConvenienceExtensionsKt.getChildren(notificationStackScrollLayout), new NotificationStackSizeCalculator$$ExternalSyntheticLambda3()), new NotificationStackSizeCalculator$$ExternalSyntheticLambda2(notificationStackSizeCalculator)))) {
                    if (expandableView instanceof ExpandableNotificationRow) {
                        ((ExpandableNotificationRow) expandableView).mSaveSpaceOnLockscreen = notificationStackSizeCalculator.saveSpaceOnLockscreen;
                    }
                }
                if (notificationStackSizeCalculator.onLockscreen()) {
                    count = Math.min(((Number) notificationStackSizeCalculator.maxKeyguardNotifications$delegate.getValue(notificationStackSizeCalculator, NotificationStackSizeCalculator.$$delegatedProperties[0])).intValue(), count);
                }
                i = Math.max(0, count);
            }
            return Integer.valueOf(i);
        }
    };
    public final CommunalSettingsInteractor communalSettingsInteractor;
    public final NotificationStackScrollLayoutController controller;
    public final KeyguardInteractor keyguardInteractor;
    public final CoroutineDispatcher mainImmediateDispatcher;
    public final NotificationStackSizeCalculator notificationStackSizeCalculator;

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.statusbar.notification.stack.ui.viewbinder.SharedNotificationContainerBinder$$ExternalSyntheticLambda0] */
    public SharedNotificationContainerBinder(NotificationStackScrollLayoutController notificationStackScrollLayoutController, NotificationStackSizeCalculator notificationStackSizeCalculator, NotificationScrollViewBinder notificationScrollViewBinder, CommunalSettingsInteractor communalSettingsInteractor, CoroutineDispatcher coroutineDispatcher, KeyguardInteractor keyguardInteractor) {
        this.controller = notificationStackScrollLayoutController;
        this.notificationStackSizeCalculator = notificationStackSizeCalculator;
        this.communalSettingsInteractor = communalSettingsInteractor;
        this.mainImmediateDispatcher = coroutineDispatcher;
        this.keyguardInteractor = keyguardInteractor;
    }

    public final DisposableHandles bind(SharedNotificationContainer sharedNotificationContainer, final SharedNotificationContainerViewModel sharedNotificationContainerViewModel) {
        DisposableHandles disposableHandles = new DisposableHandles();
        SharedNotificationContainerBinder$bind$1 sharedNotificationContainerBinder$bind$1 = new SharedNotificationContainerBinder$bind$1(sharedNotificationContainerViewModel, sharedNotificationContainer, this, null);
        CoroutineContext coroutineContext = RepeatWhenAttachedKt.MAIN_DISPATCHER_SINGLETON;
        disposableHandles.plusAssign(RepeatWhenAttachedKt.repeatWhenAttached(sharedNotificationContainer, EmptyCoroutineContext.INSTANCE, sharedNotificationContainerBinder$bind$1));
        disposableHandles.plusAssign(RepeatWhenAttachedKt.repeatWhenAttached(sharedNotificationContainer, this.mainImmediateDispatcher, new SharedNotificationContainerBinder$bind$2(this, sharedNotificationContainerViewModel, sharedNotificationContainer, new ViewStateAccessor(new Function0() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewbinder.SharedNotificationContainerBinder$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Float.valueOf(SharedNotificationContainerBinder.this.controller.mView.getAlpha());
            }
        }, null, null, 6, null), null)));
        this.controller.mView.mOnHeightChangedRunnable = new SharedNotificationContainerBinder$bind$3(sharedNotificationContainerViewModel);
        disposableHandles.plusAssign(new DisposableHandle() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewbinder.SharedNotificationContainerBinder$bind$4
            @Override // kotlinx.coroutines.DisposableHandle
            public final void dispose() {
                SharedNotificationContainerBinder.this.controller.mView.mOnHeightChangedRunnable = null;
            }
        });
        final Function1 function1 = new Function1() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewbinder.SharedNotificationContainerBinder$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                StateFlowImpl stateFlowImpl = SharedNotificationContainerViewModel.this.interactor._notificationStackChanged;
                LauncherProxyService$1$$ExternalSyntheticOutline0.m((Number) stateFlowImpl.getValue(), 1L, stateFlowImpl, null);
                return Unit.INSTANCE;
            }
        };
        View.OnLayoutChangeListener onLayoutChangeListener = new View.OnLayoutChangeListener() { // from class: com.android.systemui.common.ui.view.ViewExtKt$onLayoutChanged$1
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                Function1 function12 = Function1.this;
                view.getClass();
                function12.mo779invoke(view);
            }
        };
        sharedNotificationContainer.addOnLayoutChangeListener(onLayoutChangeListener);
        disposableHandles.plusAssign(new ViewExtKt$onLayoutChanged$2(sharedNotificationContainer, onLayoutChangeListener));
        return disposableHandles;
    }
}
