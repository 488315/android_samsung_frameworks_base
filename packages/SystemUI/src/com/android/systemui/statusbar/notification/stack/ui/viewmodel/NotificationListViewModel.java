package com.android.systemui.statusbar.notification.stack.ui.viewmodel;

import com.android.systemui.Flags;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.statusbar.domain.interactor.RemoteInputInteractor;
import com.android.systemui.statusbar.notification.domain.interactor.ActiveNotificationsInteractor;
import com.android.systemui.statusbar.notification.domain.interactor.HeadsUpNotificationInteractor;
import com.android.systemui.statusbar.notification.domain.interactor.SeenNotificationsInteractor;
import com.android.systemui.statusbar.notification.footer.shared.FooterViewRefactor;
import com.android.systemui.statusbar.notification.footer.ui.viewmodel.FooterViewModel;
import com.android.systemui.statusbar.notification.shared.NotificationsHeadsUpRefactor;
import com.android.systemui.statusbar.notification.shelf.ui.viewmodel.NotificationShelfViewModel;
import com.android.systemui.statusbar.notification.stack.domain.interactor.NotificationStackInteractor;
import com.android.systemui.statusbar.policy.domain.interactor.UserSetupInteractor;
import com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor;
import com.android.systemui.util.kotlin.FlowDumperImpl;
import com.android.systemui.util.ui.AnimatedValue;
import java.util.Optional;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.collections.EmptySet;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

public final class NotificationListViewModel extends FlowDumperImpl {
    public final Lazy areNotificationsHiddenInShade$delegate;
    public final Optional footer;
    public final Lazy hasClearableAlertingNotifications$delegate;
    public final Lazy hasFilteredOutSeenNotifications$delegate;
    public final Lazy hasPinnedHeadsUpRow$delegate;
    public final Lazy headsUpAnimationsEnabled$delegate;
    public final HeadsUpNotificationInteractor headsUpNotificationInteractor;
    public final HideListViewModel hideListViewModel;
    public final Lazy isImportantForAccessibility$delegate;
    public final Lazy pinnedHeadsUpRows$delegate;
    public final NotificationShelfViewModel shelf;
    public final Lazy shouldHideFooterView$delegate;
    public final Lazy shouldIncludeFooterView$delegate;
    public final Lazy shouldShowEmptyShadeView$delegate;
    public final Lazy topHeadsUpRow$delegate;

    public NotificationListViewModel(NotificationShelfViewModel notificationShelfViewModel, HideListViewModel hideListViewModel, Optional<FooterViewModel> optional, Optional<NotificationLoggerViewModel> optional2, final ActiveNotificationsInteractor activeNotificationsInteractor, final NotificationStackInteractor notificationStackInteractor, HeadsUpNotificationInteractor headsUpNotificationInteractor, final RemoteInputInteractor remoteInputInteractor, final SeenNotificationsInteractor seenNotificationsInteractor, final ShadeInteractor shadeInteractor, final UserSetupInteractor userSetupInteractor, final ZenModeInteractor zenModeInteractor, final CoroutineDispatcher coroutineDispatcher, DumpManager dumpManager) {
        super(dumpManager, null, 2, 0 == true ? 1 : 0);
        this.shelf = notificationShelfViewModel;
        this.hideListViewModel = hideListViewModel;
        this.footer = optional;
        this.isImportantForAccessibility$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel$isImportantForAccessibility$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                int i = FooterViewRefactor.$r8$clinit;
                Flags.notificationsFooterViewRefactor();
                refactorFlagUtils.getClass();
                RefactorFlagUtils.assertOnEngBuild("New code path expects com.android.systemui.notifications_footer_view_refactor to be enabled.");
                return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.TRUE);
            }
        });
        this.shouldShowEmptyShadeView$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel$shouldShowEmptyShadeView$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                int i = FooterViewRefactor.$r8$clinit;
                Flags.notificationsFooterViewRefactor();
                refactorFlagUtils.getClass();
                RefactorFlagUtils.assertOnEngBuild("New code path expects com.android.systemui.notifications_footer_view_refactor to be enabled.");
                return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
            }
        });
        this.shouldHideFooterView$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel$shouldHideFooterView$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                int i = FooterViewRefactor.$r8$clinit;
                Flags.notificationsFooterViewRefactor();
                refactorFlagUtils.getClass();
                RefactorFlagUtils.assertOnEngBuild("New code path expects com.android.systemui.notifications_footer_view_refactor to be enabled.");
                return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
            }
        });
        this.shouldIncludeFooterView$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel$shouldIncludeFooterView$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                int i = FooterViewRefactor.$r8$clinit;
                Flags.notificationsFooterViewRefactor();
                refactorFlagUtils.getClass();
                RefactorFlagUtils.assertOnEngBuild("New code path expects com.android.systemui.notifications_footer_view_refactor to be enabled.");
                return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(new AnimatedValue.NotAnimating(Boolean.FALSE));
            }
        });
        this.areNotificationsHiddenInShade$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel$areNotificationsHiddenInShade$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                int i = FooterViewRefactor.$r8$clinit;
                Flags.notificationsFooterViewRefactor();
                refactorFlagUtils.getClass();
                RefactorFlagUtils.assertOnEngBuild("New code path expects com.android.systemui.notifications_footer_view_refactor to be enabled.");
                return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
            }
        });
        this.hasFilteredOutSeenNotifications$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel$hasFilteredOutSeenNotifications$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                int i = FooterViewRefactor.$r8$clinit;
                Flags.notificationsFooterViewRefactor();
                refactorFlagUtils.getClass();
                RefactorFlagUtils.assertOnEngBuild("New code path expects com.android.systemui.notifications_footer_view_refactor to be enabled.");
                return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
            }
        });
        this.hasClearableAlertingNotifications$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel$hasClearableAlertingNotifications$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                int i = FooterViewRefactor.$r8$clinit;
                Flags.notificationsFooterViewRefactor();
                refactorFlagUtils.getClass();
                RefactorFlagUtils.assertOnEngBuild("New code path expects com.android.systemui.notifications_footer_view_refactor to be enabled.");
                return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
            }
        });
        LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel$hasNonClearableSilentNotifications$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                int i = FooterViewRefactor.$r8$clinit;
                Flags.notificationsFooterViewRefactor();
                refactorFlagUtils.getClass();
                RefactorFlagUtils.assertOnEngBuild("New code path expects com.android.systemui.notifications_footer_view_refactor to be enabled.");
                return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
            }
        });
        LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel$topHeadsUpRow$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                NotificationsHeadsUpRefactor notificationsHeadsUpRefactor = NotificationsHeadsUpRefactor.INSTANCE;
                Flags.notificationsHeadsUpRefactor();
                refactorFlagUtils.getClass();
                RefactorFlagUtils.assertOnEngBuild("New code path expects com.android.systemui.notifications_heads_up_refactor to be enabled.");
                return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(null);
            }
        });
        LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel$pinnedHeadsUpRows$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                NotificationsHeadsUpRefactor notificationsHeadsUpRefactor = NotificationsHeadsUpRefactor.INSTANCE;
                Flags.notificationsHeadsUpRefactor();
                refactorFlagUtils.getClass();
                RefactorFlagUtils.assertOnEngBuild("New code path expects com.android.systemui.notifications_heads_up_refactor to be enabled.");
                return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(EmptySet.INSTANCE);
            }
        });
        LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel$headsUpAnimationsEnabled$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                NotificationsHeadsUpRefactor notificationsHeadsUpRefactor = NotificationsHeadsUpRefactor.INSTANCE;
                Flags.notificationsHeadsUpRefactor();
                refactorFlagUtils.getClass();
                RefactorFlagUtils.assertOnEngBuild("New code path expects com.android.systemui.notifications_heads_up_refactor to be enabled.");
                return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
            }
        });
        LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.stack.ui.viewmodel.NotificationListViewModel$hasPinnedHeadsUpRow$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                NotificationsHeadsUpRefactor notificationsHeadsUpRefactor = NotificationsHeadsUpRefactor.INSTANCE;
                Flags.notificationsHeadsUpRefactor();
                refactorFlagUtils.getClass();
                RefactorFlagUtils.assertOnEngBuild("New code path expects com.android.systemui.notifications_heads_up_refactor to be enabled.");
                return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.FALSE);
            }
        });
    }
}
