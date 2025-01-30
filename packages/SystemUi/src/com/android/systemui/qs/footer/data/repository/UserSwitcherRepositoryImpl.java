package com.android.systemui.qs.footer.data.repository;

import android.content.Context;
import android.os.Handler;
import android.os.UserManager;
import com.android.systemui.R;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.policy.UserInfoController;
import com.android.systemui.statusbar.policy.UserSwitcherController;
import com.android.systemui.util.settings.GlobalSettings;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class UserSwitcherRepositoryImpl implements UserSwitcherRepository {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final CoroutineDispatcher bgDispatcher;
    public final Handler bgHandler;
    public final Flow currentUserInfo;
    public final Flow currentUserName;
    public final GlobalSettings globalSetting;
    public final boolean showUserSwitcherForSingleUser;
    public final UserInfoController userInfoController;
    public final UserManager userManager;
    public final UserSwitcherController userSwitcherController;
    public final UserTracker userTracker;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public UserSwitcherRepositoryImpl(Context context, Handler handler, CoroutineDispatcher coroutineDispatcher, UserManager userManager, UserTracker userTracker, UserSwitcherController userSwitcherController, UserInfoController userInfoController, GlobalSettings globalSettings) {
        this.bgHandler = handler;
        this.bgDispatcher = coroutineDispatcher;
        this.userManager = userManager;
        this.userTracker = userTracker;
        this.userSwitcherController = userSwitcherController;
        this.userInfoController = userInfoController;
        this.globalSetting = globalSettings;
        this.showUserSwitcherForSingleUser = context.getResources().getBoolean(R.bool.qs_show_user_switcher_for_single_user);
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        UserSwitcherRepositoryImpl$isEnabled$1 userSwitcherRepositoryImpl$isEnabled$1 = new UserSwitcherRepositoryImpl$isEnabled$1(this, null);
        conflatedCallbackFlow.getClass();
        Flow conflatedCallbackFlow2 = ConflatedCallbackFlow.conflatedCallbackFlow(userSwitcherRepositoryImpl$isEnabled$1);
        this.currentUserName = ConflatedCallbackFlow.conflatedCallbackFlow(new UserSwitcherRepositoryImpl$currentUserName$1(this, null));
        this.currentUserInfo = ConflatedCallbackFlow.conflatedCallbackFlow(new UserSwitcherRepositoryImpl$currentUserInfo$1(this, null));
        FlowKt.distinctUntilChanged(FlowKt.transformLatest(conflatedCallbackFlow2, new UserSwitcherRepositoryImpl$special$$inlined$flatMapLatest$1(null, this)));
    }
}
