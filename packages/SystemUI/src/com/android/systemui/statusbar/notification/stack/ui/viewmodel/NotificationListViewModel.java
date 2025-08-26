package com.android.systemui.statusbar.notification.stack.ui.viewmodel;

import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel;
import com.android.systemui.statusbar.chips.ui.viewmodel.OngoingActivityChipsViewModel$special$$inlined$map$9;
import com.android.systemui.statusbar.domain.interactor.RemoteInputInteractor;
import com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor;
import com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor;
import com.android.systemui.statusbar.notification.emptyshade.shared.ModesEmptyShadeFix;
import com.android.systemui.statusbar.notification.emptyshade.ui.viewmodel.EmptyShadeViewModel;
import com.android.systemui.statusbar.notification.footer.ui.viewmodel.FooterViewModel;
import com.android.systemui.statusbar.notification.shelf.ui.viewmodel.NotificationShelfViewModel;
import com.android.systemui.statusbar.notification.stack.domain.interactor.NotificationStackInteractor;
import com.android.systemui.statusbar.policy.domain.interactor.UserSetupInteractor;
import com.android.systemui.util.kotlin.FlowDumperImpl;
import com.android.systemui.util.ui.AnimatedValueKt;
import java.util.Optional;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.StateFlow;

/* loaded from: classes3.dex */
public final class NotificationListViewModel extends FlowDumperImpl {
    public final Lazy activeHeadsUpRowKeys$delegate;
    public final EmptyShadeViewModel.Factory emptyShadeViewModelFactory;
    public final FooterViewModel.Factory footerViewModelFactory;
    public final Flow hasClearableAlertingNotifications;
    public final Flow hasNonClearableSilentNotifications;
    public final Lazy hasPinnedHeadsUpRow$delegate;
    public final Lazy headsUpAnimationsEnabled$delegate;
    public final HeadsUpNotificationInteractor headsUpNotificationInteractor;
    public final HideListViewModel hideListViewModel;
    public final Flow isImportantForAccessibility;
    public final Optional logger;
    public final Lazy pinnedHeadsUpRowKeys$delegate;
    public final NotificationShelfViewModel shelf;
    public final Lazy shouldShowEmptyShadeView$delegate;
    public final Lazy topHeadsUpRow$delegate;
    public final OngoingActivityChipsViewModel$special$$inlined$map$9 visibleStatusBarChipKeys;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class VisibilityChange {
        public static final /* synthetic */ VisibilityChange[] $VALUES;
        public static final VisibilityChange APPEAR_WITH_ANIMATION;
        public static final VisibilityChange DISAPPEAR_WITHOUT_ANIMATION;
        public static final VisibilityChange DISAPPEAR_WITH_ANIMATION;
        private final boolean canAnimate;
        private final boolean visible;

        static {
            VisibilityChange visibilityChange = new VisibilityChange("DISAPPEAR_WITHOUT_ANIMATION", 0, false, false);
            DISAPPEAR_WITHOUT_ANIMATION = visibilityChange;
            VisibilityChange visibilityChange2 = new VisibilityChange("DISAPPEAR_WITH_ANIMATION", 1, false, true);
            DISAPPEAR_WITH_ANIMATION = visibilityChange2;
            VisibilityChange visibilityChange3 = new VisibilityChange("APPEAR_WITH_ANIMATION", 2, true, true);
            APPEAR_WITH_ANIMATION = visibilityChange3;
            VisibilityChange[] visibilityChangeArr = {visibilityChange, visibilityChange2, visibilityChange3};
            $VALUES = visibilityChangeArr;
            EnumEntriesKt.enumEntries(visibilityChangeArr);
        }

        private VisibilityChange(String str, int i, boolean z, boolean z2) {
            this.visible = z;
            this.canAnimate = z2;
        }

        public static VisibilityChange valueOf(String str) {
            return (VisibilityChange) Enum.valueOf(VisibilityChange.class, str);
        }

        public static VisibilityChange[] values() {
            return (VisibilityChange[]) $VALUES.clone();
        }

        public final boolean getCanAnimate() {
            return this.canAnimate;
        }

        public final boolean getVisible() {
            return this.visible;
        }
    }

    public NotificationListViewModel(NotificationShelfViewModel notificationShelfViewModel, HideListViewModel hideListViewModel, OngoingActivityChipsViewModel ongoingActivityChipsViewModel, FooterViewModel.Factory factory, EmptyShadeViewModel.Factory factory2, Optional<NotificationLoggerViewModel> optional, final ActiveNotificationsInteractor activeNotificationsInteractor, final NotificationStackInteractor notificationStackInteractor, HeadsUpNotificationInteractor headsUpNotificationInteractor, final RemoteInputInteractor remoteInputInteractor, final ShadeInteractor shadeInteractor, final UserSetupInteractor userSetupInteractor, final CoroutineDispatcher coroutineDispatcher, DumpManager dumpManager) {
        super(dumpManager, null, 2, null);
        this.shelf = notificationShelfViewModel;
        this.hideListViewModel = hideListViewModel;
        this.footerViewModelFactory = factory;
        this.emptyShadeViewModelFactory = factory2;
        this.logger = optional;
        this.headsUpNotificationInteractor = headsUpNotificationInteractor;
        this.isImportantForAccessibility = FlowKt.flowOn(dumpWhileCollecting(FlowKt.distinctUntilChanged(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(activeNotificationsInteractor.areAnyNotificationsPresent, notificationStackInteractor.isShowingOnLockscreen, new NotificationListViewModel$isImportantForAccessibility$1(null))), "isImportantForAccessibility"), coroutineDispatcher);
        this.shouldShowEmptyShadeView$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                int i = ModesEmptyShadeFix.$r8$clinit;
                ActiveNotificationsInteractor activeNotificationsInteractor2 = activeNotificationsInteractor;
                Flow flowIsQsFullscreen = ((ShadeInteractorImpl) shadeInteractor).baseShadeInteractor.isQsFullscreen();
                NotificationStackInteractor notificationStackInteractor2 = notificationStackInteractor;
                return FlowKt.flowOn(this.f$0.dumpWhileCollecting(FlowKt.distinctUntilChanged(FlowKt.combine(activeNotificationsInteractor2.areAnyNotificationsPresent, flowIsQsFullscreen, notificationStackInteractor2.isShowingOnLockscreen, new NotificationListViewModel$shouldShowEmptyShadeView$2$1(null))), "shouldShowEmptyShadeView"), coroutineDispatcher);
            }
        });
        final int i = 0;
        LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                        int i2 = ModesEmptyShadeFix.$r8$clinit;
                        refactorFlagUtils.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects android.app.modes_ui_empty_shade to be enabled.");
                        break;
                    case 1:
                        RefactorFlagUtils refactorFlagUtils2 = RefactorFlagUtils.INSTANCE;
                        int i3 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils2.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        break;
                    case 2:
                        RefactorFlagUtils refactorFlagUtils3 = RefactorFlagUtils.INSTANCE;
                        int i4 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils3.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        break;
                    case 3:
                        RefactorFlagUtils refactorFlagUtils4 = RefactorFlagUtils.INSTANCE;
                        int i5 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils4.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        break;
                    case 4:
                        RefactorFlagUtils refactorFlagUtils5 = RefactorFlagUtils.INSTANCE;
                        int i6 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils5.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        break;
                    case 5:
                        RefactorFlagUtils refactorFlagUtils6 = RefactorFlagUtils.INSTANCE;
                        int i7 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils6.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        break;
                    default:
                        RefactorFlagUtils refactorFlagUtils7 = RefactorFlagUtils.INSTANCE;
                        int i8 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils7.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        break;
                }
                return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
            }
        });
        LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                int i2 = SceneContainerFlag.$r8$clinit;
                final StateFlow shadeExpansion = ((ShadeInteractorImpl) shadeInteractor).baseShadeInteractor.getShadeExpansion();
                return FlowKt.flowOn(this.f$0.dumpWhileCollecting(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel$shouldHideFooterView_delegate$lambda$4$$inlined$map$1

                    /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel$shouldHideFooterView_delegate$lambda$4$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* renamed from: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel$shouldHideFooterView_delegate$lambda$4$$inlined$map$1$2$1, reason: invalid class name */
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

                        public AnonymousClass2(FlowCollector flowCollector) {
                            this.$this_unsafeFlow = flowCollector;
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
                                Boolean boolValueOf = Boolean.valueOf(((Number) obj).floatValue() == 0.0f);
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
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
                        Object objCollect = shadeExpansion.collect(new AnonymousClass2(flowCollector), continuation);
                        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                    }
                }), "shouldHideFooterView"), coroutineDispatcher);
            }
        });
        LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                int i2 = SceneContainerFlag.$r8$clinit;
                ActiveNotificationsInteractor activeNotificationsInteractor2 = activeNotificationsInteractor;
                StateFlow stateFlow = userSetupInteractor.isUserSetUp;
                NotificationStackInteractor notificationStackInteractor2 = notificationStackInteractor;
                ShadeInteractorImpl shadeInteractorImpl = (ShadeInteractorImpl) shadeInteractor;
                return FlowKt.flowOn(this.f$0.dumpWhileCollecting(AnimatedValueKt.toAnimatedValueFlow(com.android.systemui.util.kotlin.FlowKt.sample(FlowKt.distinctUntilChanged(FlowKt.combine(activeNotificationsInteractor2.areAnyNotificationsPresent, stateFlow, notificationStackInteractor2.isShowingOnLockscreen, shadeInteractorImpl.baseShadeInteractor.isQsFullscreen(), remoteInputInteractor.isRemoteInputActive, new NotificationListViewModel$shouldIncludeFooterView$2$1(null)), new NotificationListViewModel$$ExternalSyntheticLambda10()), new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new NotificationListViewModel$shouldIncludeFooterView$2$6(null), new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(shadeInteractorImpl.isShadeFullyExpanded, shadeInteractorImpl.isShadeTouchable, NotificationListViewModel$shouldIncludeFooterView$2$5.INSTANCE)), new NotificationListViewModel$shouldIncludeFooterView$2$7(null))), "shouldIncludeFooterView"), coroutineDispatcher);
            }
        });
        final int i2 = 1;
        LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                        int i22 = ModesEmptyShadeFix.$r8$clinit;
                        refactorFlagUtils.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects android.app.modes_ui_empty_shade to be enabled.");
                        break;
                    case 1:
                        RefactorFlagUtils refactorFlagUtils2 = RefactorFlagUtils.INSTANCE;
                        int i3 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils2.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        break;
                    case 2:
                        RefactorFlagUtils refactorFlagUtils3 = RefactorFlagUtils.INSTANCE;
                        int i4 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils3.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        break;
                    case 3:
                        RefactorFlagUtils refactorFlagUtils4 = RefactorFlagUtils.INSTANCE;
                        int i5 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils4.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        break;
                    case 4:
                        RefactorFlagUtils refactorFlagUtils5 = RefactorFlagUtils.INSTANCE;
                        int i6 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils5.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        break;
                    case 5:
                        RefactorFlagUtils refactorFlagUtils6 = RefactorFlagUtils.INSTANCE;
                        int i7 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils6.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        break;
                    default:
                        RefactorFlagUtils refactorFlagUtils7 = RefactorFlagUtils.INSTANCE;
                        int i8 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils7.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        break;
                }
                return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
            }
        });
        this.hasClearableAlertingNotifications = dumpWhileCollecting(activeNotificationsInteractor.hasClearableAlertingNotifications, "hasClearableAlertingNotifications");
        this.hasNonClearableSilentNotifications = dumpWhileCollecting(activeNotificationsInteractor.hasNonClearableSilentNotifications, "hasNonClearableSilentNotifications");
        final int i3 = 2;
        this.topHeadsUpRow$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i3) {
                    case 0:
                        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                        int i22 = ModesEmptyShadeFix.$r8$clinit;
                        refactorFlagUtils.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects android.app.modes_ui_empty_shade to be enabled.");
                        break;
                    case 1:
                        RefactorFlagUtils refactorFlagUtils2 = RefactorFlagUtils.INSTANCE;
                        int i32 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils2.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        break;
                    case 2:
                        RefactorFlagUtils refactorFlagUtils3 = RefactorFlagUtils.INSTANCE;
                        int i4 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils3.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        break;
                    case 3:
                        RefactorFlagUtils refactorFlagUtils4 = RefactorFlagUtils.INSTANCE;
                        int i5 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils4.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        break;
                    case 4:
                        RefactorFlagUtils refactorFlagUtils5 = RefactorFlagUtils.INSTANCE;
                        int i6 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils5.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        break;
                    case 5:
                        RefactorFlagUtils refactorFlagUtils6 = RefactorFlagUtils.INSTANCE;
                        int i7 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils6.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        break;
                    default:
                        RefactorFlagUtils refactorFlagUtils7 = RefactorFlagUtils.INSTANCE;
                        int i8 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils7.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        break;
                }
                return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
            }
        });
        final int i4 = 3;
        this.activeHeadsUpRowKeys$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i4) {
                    case 0:
                        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                        int i22 = ModesEmptyShadeFix.$r8$clinit;
                        refactorFlagUtils.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects android.app.modes_ui_empty_shade to be enabled.");
                        break;
                    case 1:
                        RefactorFlagUtils refactorFlagUtils2 = RefactorFlagUtils.INSTANCE;
                        int i32 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils2.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        break;
                    case 2:
                        RefactorFlagUtils refactorFlagUtils3 = RefactorFlagUtils.INSTANCE;
                        int i42 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils3.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        break;
                    case 3:
                        RefactorFlagUtils refactorFlagUtils4 = RefactorFlagUtils.INSTANCE;
                        int i5 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils4.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        break;
                    case 4:
                        RefactorFlagUtils refactorFlagUtils5 = RefactorFlagUtils.INSTANCE;
                        int i6 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils5.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        break;
                    case 5:
                        RefactorFlagUtils refactorFlagUtils6 = RefactorFlagUtils.INSTANCE;
                        int i7 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils6.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        break;
                    default:
                        RefactorFlagUtils refactorFlagUtils7 = RefactorFlagUtils.INSTANCE;
                        int i8 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils7.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        break;
                }
                return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
            }
        });
        final int i5 = 4;
        this.pinnedHeadsUpRowKeys$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i5) {
                    case 0:
                        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                        int i22 = ModesEmptyShadeFix.$r8$clinit;
                        refactorFlagUtils.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects android.app.modes_ui_empty_shade to be enabled.");
                        break;
                    case 1:
                        RefactorFlagUtils refactorFlagUtils2 = RefactorFlagUtils.INSTANCE;
                        int i32 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils2.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        break;
                    case 2:
                        RefactorFlagUtils refactorFlagUtils3 = RefactorFlagUtils.INSTANCE;
                        int i42 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils3.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        break;
                    case 3:
                        RefactorFlagUtils refactorFlagUtils4 = RefactorFlagUtils.INSTANCE;
                        int i52 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils4.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        break;
                    case 4:
                        RefactorFlagUtils refactorFlagUtils5 = RefactorFlagUtils.INSTANCE;
                        int i6 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils5.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        break;
                    case 5:
                        RefactorFlagUtils refactorFlagUtils6 = RefactorFlagUtils.INSTANCE;
                        int i7 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils6.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        break;
                    default:
                        RefactorFlagUtils refactorFlagUtils7 = RefactorFlagUtils.INSTANCE;
                        int i8 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils7.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        break;
                }
                return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
            }
        });
        final int i6 = 5;
        this.headsUpAnimationsEnabled$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i6) {
                    case 0:
                        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                        int i22 = ModesEmptyShadeFix.$r8$clinit;
                        refactorFlagUtils.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects android.app.modes_ui_empty_shade to be enabled.");
                        break;
                    case 1:
                        RefactorFlagUtils refactorFlagUtils2 = RefactorFlagUtils.INSTANCE;
                        int i32 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils2.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        break;
                    case 2:
                        RefactorFlagUtils refactorFlagUtils3 = RefactorFlagUtils.INSTANCE;
                        int i42 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils3.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        break;
                    case 3:
                        RefactorFlagUtils refactorFlagUtils4 = RefactorFlagUtils.INSTANCE;
                        int i52 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils4.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        break;
                    case 4:
                        RefactorFlagUtils refactorFlagUtils5 = RefactorFlagUtils.INSTANCE;
                        int i62 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils5.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        break;
                    case 5:
                        RefactorFlagUtils refactorFlagUtils6 = RefactorFlagUtils.INSTANCE;
                        int i7 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils6.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        break;
                    default:
                        RefactorFlagUtils refactorFlagUtils7 = RefactorFlagUtils.INSTANCE;
                        int i8 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils7.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        break;
                }
                return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
            }
        });
        final int i7 = 6;
        this.hasPinnedHeadsUpRow$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i7) {
                    case 0:
                        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                        int i22 = ModesEmptyShadeFix.$r8$clinit;
                        refactorFlagUtils.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects android.app.modes_ui_empty_shade to be enabled.");
                        break;
                    case 1:
                        RefactorFlagUtils refactorFlagUtils2 = RefactorFlagUtils.INSTANCE;
                        int i32 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils2.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        break;
                    case 2:
                        RefactorFlagUtils refactorFlagUtils3 = RefactorFlagUtils.INSTANCE;
                        int i42 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils3.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        break;
                    case 3:
                        RefactorFlagUtils refactorFlagUtils4 = RefactorFlagUtils.INSTANCE;
                        int i52 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils4.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        break;
                    case 4:
                        RefactorFlagUtils refactorFlagUtils5 = RefactorFlagUtils.INSTANCE;
                        int i62 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils5.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        break;
                    case 5:
                        RefactorFlagUtils refactorFlagUtils6 = RefactorFlagUtils.INSTANCE;
                        int i72 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils6.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        break;
                    default:
                        RefactorFlagUtils refactorFlagUtils7 = RefactorFlagUtils.INSTANCE;
                        int i8 = SceneContainerFlag.$r8$clinit;
                        refactorFlagUtils7.getClass();
                        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
                        break;
                }
                return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
            }
        });
        this.visibleStatusBarChipKeys = ongoingActivityChipsViewModel.visibleChipKeys;
    }
}
