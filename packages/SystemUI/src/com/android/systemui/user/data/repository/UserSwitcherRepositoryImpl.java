package com.android.systemui.user.data.repository;

import android.content.Context;
import android.os.Handler;
import android.os.UserManager;
import com.android.systemui.R;
import com.android.systemui.statusbar.policy.UserInfoController;
import com.android.systemui.statusbar.policy.UserSwitcherController;
import com.android.systemui.util.settings.GlobalSettings;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: classes3.dex */
public final class UserSwitcherRepositoryImpl implements UserSwitcherRepository {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CoroutineDispatcher bgDispatcher;
    public final Handler bgHandler;
    public final Flow currentUserInfo;
    public final Flow currentUserName;
    public final GlobalSettings globalSetting;
    public final Flow isEnabled;
    public final boolean showUserSwitcherForSingleUser;
    public final UserInfoController userInfoController;
    public final UserManager userManager;
    public final UserRepository userRepository;
    public final UserSwitcherController userSwitcherController;
    public final Flow userSwitcherStatus;
    public final boolean userSwitchingMustGoThroughLoginScreen;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public UserSwitcherRepositoryImpl(Context context, Handler handler, CoroutineDispatcher coroutineDispatcher, UserManager userManager, UserSwitcherController userSwitcherController, UserInfoController userInfoController, GlobalSettings globalSettings, UserRepository userRepository) {
        this.bgHandler = handler;
        this.bgDispatcher = coroutineDispatcher;
        this.userManager = userManager;
        this.userSwitcherController = userSwitcherController;
        this.userInfoController = userInfoController;
        this.globalSetting = globalSettings;
        this.userRepository = userRepository;
        this.showUserSwitcherForSingleUser = context.getResources().getBoolean(R.bool.qs_show_user_switcher_for_single_user);
        this.userSwitchingMustGoThroughLoginScreen = context.getResources().getBoolean(R.bool.config_userSwitchingMustGoThroughLoginScreen);
        Flow flowConflatedCallbackFlow = FlowConflatedKt.conflatedCallbackFlow(new UserSwitcherRepositoryImpl$isEnabled$1(this, null));
        this.isEnabled = flowConflatedCallbackFlow;
        this.currentUserName = FlowConflatedKt.conflatedCallbackFlow(new UserSwitcherRepositoryImpl$currentUserName$1(this, null));
        this.currentUserInfo = FlowConflatedKt.conflatedCallbackFlow(new UserSwitcherRepositoryImpl$currentUserInfo$1(this, null));
        this.userSwitcherStatus = FlowKt.distinctUntilChanged(FlowKt.transformLatest(flowConflatedCallbackFlow, new UserSwitcherRepositoryImpl$special$$inlined$flatMapLatest$1(null, this)));
    }
}
