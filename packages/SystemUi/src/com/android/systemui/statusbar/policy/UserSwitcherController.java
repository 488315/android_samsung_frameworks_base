package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.view.View;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.policy.UserSwitcherController;
import com.android.systemui.user.data.model.UserSwitcherSettingsModel;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.android.systemui.user.domain.interactor.GuestUserInteractor;
import com.android.systemui.user.domain.interactor.UserInteractor;
import dagger.Lazy;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class UserSwitcherController {
    public static final Companion Companion = new Companion(null);
    public final ActivityStarter activityStarter;
    public final Context applicationContext;
    public final Lazy guestUserInteractorLazy;
    public final Lazy keyguardInteractorLazy;
    public final Lazy userInteractorLazy;
    public final UserTracker userTracker;
    public final kotlin.Lazy userInteractor$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.policy.UserSwitcherController$userInteractor$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return (UserInteractor) UserSwitcherController.this.userInteractorLazy.get();
        }
    });
    public final kotlin.Lazy guestUserInteractor$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.policy.UserSwitcherController$guestUserInteractor$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return (GuestUserInteractor) UserSwitcherController.this.guestUserInteractorLazy.get();
        }
    });
    public final kotlin.Lazy keyguardInteractor$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.policy.UserSwitcherController$keyguardInteractor$2
        {
            super(0);
        }

        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return (KeyguardInteractor) UserSwitcherController.this.keyguardInteractorLazy.get();
        }
    });
    public final Map callbackCompatMap = new LinkedHashMap();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface UserSwitchCallback {
        void onUserSwitched();
    }

    public UserSwitcherController(Context context, Lazy lazy, Lazy lazy2, Lazy lazy3, ActivityStarter activityStarter, UserTracker userTracker) {
        this.applicationContext = context;
        this.userInteractorLazy = lazy;
        this.guestUserInteractorLazy = lazy2;
        this.keyguardInteractorLazy = lazy3;
        this.activityStarter = activityStarter;
        this.userTracker = userTracker;
    }

    public static final void setSelectableAlpha(View view) {
        Companion.getClass();
        view.setAlpha(view.isEnabled() ? 1.0f : 0.38f);
    }

    public final void addUserSwitchCallback(final UserSwitchCallback userSwitchCallback) {
        UserInteractor.UserCallback userCallback = new UserInteractor.UserCallback() { // from class: com.android.systemui.statusbar.policy.UserSwitcherController$addUserSwitchCallback$interactorCallback$1
            @Override // com.android.systemui.user.domain.interactor.UserInteractor.UserCallback
            public final boolean isEvictable() {
                return false;
            }

            @Override // com.android.systemui.user.domain.interactor.UserInteractor.UserCallback
            public final void onUserStateChanged() {
                UserSwitcherController.UserSwitchCallback.this.onUserSwitched();
            }
        };
        this.callbackCompatMap.put(userSwitchCallback, userCallback);
        getUserInteractor().addCallback(userCallback);
    }

    public final UserInteractor getUserInteractor() {
        return (UserInteractor) this.userInteractor$delegate.getValue();
    }

    public final boolean isUserSwitcherEnabled() {
        return ((UserSwitcherSettingsModel) ((UserRepositoryImpl) getUserInteractor().repository)._userSwitcherSettings.getValue()).isUserSwitcherEnabled;
    }

    public final void removeUserSwitchCallback(UserSwitchCallback userSwitchCallback) {
        UserInteractor.UserCallback userCallback = (UserInteractor.UserCallback) this.callbackCompatMap.remove(userSwitchCallback);
        if (userCallback != null) {
            getUserInteractor().removeCallback(userCallback);
        }
    }
}
