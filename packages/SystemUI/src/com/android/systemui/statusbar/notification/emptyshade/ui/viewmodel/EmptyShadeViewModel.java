package com.android.systemui.statusbar.notification.emptyshade.ui.viewmodel;

import android.content.Context;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractor;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.shared.notifications.domain.interactor.NotificationSettingsInteractor;
import com.android.systemui.statusbar.notification.domain.interactor.SeenNotificationsInteractor;
import com.android.systemui.statusbar.notification.emptyshade.shared.ModesEmptyShadeFix;
import com.android.systemui.statusbar.policy.domain.interactor.ZenModeInteractor;
import com.android.systemui.util.kotlin.FlowDumperImpl;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.StateFlow;

/* loaded from: classes3.dex */
public final class EmptyShadeViewModel extends FlowDumperImpl {
    public final Lazy areNotificationsHiddenInShade$delegate;
    public final Context context;
    public final StateFlow hasFilteredOutSeenNotifications;

    public interface Factory {
        EmptyShadeViewModel create();
    }

    public EmptyShadeViewModel(Context context, final ZenModeInteractor zenModeInteractor, SeenNotificationsInteractor seenNotificationsInteractor, NotificationSettingsInteractor notificationSettingsInteractor, ConfigurationInteractor configurationInteractor, final CoroutineDispatcher coroutineDispatcher, DumpManager dumpManager) {
        super(dumpManager, null, 2, null);
        this.context = context;
        this.areNotificationsHiddenInShade$delegate = LazyKt__LazyJVMKt.lazy(new Function0(zenModeInteractor, coroutineDispatcher) { // from class: com.android.systemui.statusbar.notification.emptyshade.ui.viewmodel.EmptyShadeViewModel$$ExternalSyntheticLambda0
            public final /* synthetic */ ZenModeInteractor f$1;

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return this.f$0.dumpWhileCollecting(this.f$1.areNotificationsHiddenInShade, "areNotificationsHiddenInShade");
            }
        });
        this.hasFilteredOutSeenNotifications = dumpValue(seenNotificationsInteractor.hasFilteredOutSeenNotifications, "hasFilteredOutSeenNotifications");
        LazyKt__LazyJVMKt.lazy(new EmptyShadeViewModel$$ExternalSyntheticLambda1(configurationInteractor));
        LazyKt__LazyJVMKt.lazy(new EmptyShadeViewModel$$ExternalSyntheticLambda1(this, zenModeInteractor, coroutineDispatcher));
        final int i = 0;
        LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.emptyshade.ui.viewmodel.EmptyShadeViewModel$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                        int i2 = ModesEmptyShadeFix.$r8$clinit;
                        throw new IllegalStateException("New code path not supported when android.app.modes_ui_empty_shade is disabled.");
                    default:
                        RefactorFlagUtils refactorFlagUtils2 = RefactorFlagUtils.INSTANCE;
                        int i3 = ModesEmptyShadeFix.$r8$clinit;
                        throw new IllegalStateException("New code path not supported when android.app.modes_ui_empty_shade is disabled.");
                }
            }
        });
        final int i2 = 1;
        LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.emptyshade.ui.viewmodel.EmptyShadeViewModel$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                        int i22 = ModesEmptyShadeFix.$r8$clinit;
                        throw new IllegalStateException("New code path not supported when android.app.modes_ui_empty_shade is disabled.");
                    default:
                        RefactorFlagUtils refactorFlagUtils2 = RefactorFlagUtils.INSTANCE;
                        int i3 = ModesEmptyShadeFix.$r8$clinit;
                        throw new IllegalStateException("New code path not supported when android.app.modes_ui_empty_shade is disabled.");
                }
            }
        });
    }
}
