package com.android.systemui.statusbar.policy;

import android.content.Context;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.user.data.model.UserSwitcherSettingsModel;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.android.systemui.user.domain.interactor.GuestUserInteractor;
import com.android.systemui.user.domain.interactor.UserSwitcherInteractor;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class UserSwitcherController {
    public static final Companion Companion = new Companion(null);
    public final ActivityStarter activityStarter;
    public final Context applicationContext;
    public final Map callbackCompatMap = new LinkedHashMap();
    public final Lazy guestUserInteractor$delegate;
    public final dagger.Lazy guestUserInteractorLazy;
    public final Lazy keyguardInteractor$delegate;
    public final dagger.Lazy keyguardInteractorLazy;
    public final Lazy mUserSwitcherInteractor$delegate;
    public final dagger.Lazy userSwitcherInteractorLazy;
    public final UserTracker userTracker;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public interface UserSwitchCallback {
        void onUserSwitched();
    }

    public UserSwitcherController(Context context, dagger.Lazy lazy, dagger.Lazy lazy2, dagger.Lazy lazy3, ActivityStarter activityStarter, UserTracker userTracker) {
        this.applicationContext = context;
        this.userSwitcherInteractorLazy = lazy;
        this.guestUserInteractorLazy = lazy2;
        this.keyguardInteractorLazy = lazy3;
        this.activityStarter = activityStarter;
        this.userTracker = userTracker;
        final int i = 0;
        this.mUserSwitcherInteractor$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.statusbar.policy.UserSwitcherController$$ExternalSyntheticLambda0
            public final /* synthetic */ UserSwitcherController f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        return (UserSwitcherInteractor) this.f$0.userSwitcherInteractorLazy.get();
                    case 1:
                        return (GuestUserInteractor) this.f$0.guestUserInteractorLazy.get();
                    default:
                        return (KeyguardInteractor) this.f$0.keyguardInteractorLazy.get();
                }
            }
        });
        final int i2 = 1;
        this.guestUserInteractor$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.statusbar.policy.UserSwitcherController$$ExternalSyntheticLambda0
            public final /* synthetic */ UserSwitcherController f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        return (UserSwitcherInteractor) this.f$0.userSwitcherInteractorLazy.get();
                    case 1:
                        return (GuestUserInteractor) this.f$0.guestUserInteractorLazy.get();
                    default:
                        return (KeyguardInteractor) this.f$0.keyguardInteractorLazy.get();
                }
            }
        });
        final int i3 = 2;
        this.keyguardInteractor$delegate = LazyKt__LazyJVMKt.lazy(new Function0(this) { // from class: com.android.systemui.statusbar.policy.UserSwitcherController$$ExternalSyntheticLambda0
            public final /* synthetic */ UserSwitcherController f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i3) {
                    case 0:
                        return (UserSwitcherInteractor) this.f$0.userSwitcherInteractorLazy.get();
                    case 1:
                        return (GuestUserInteractor) this.f$0.guestUserInteractorLazy.get();
                    default:
                        return (KeyguardInteractor) this.f$0.keyguardInteractorLazy.get();
                }
            }
        });
    }

    public final void addUserSwitchCallback(final UserSwitchCallback userSwitchCallback) {
        UserSwitcherInteractor.UserCallback userCallback = new UserSwitcherInteractor.UserCallback() { // from class: com.android.systemui.statusbar.policy.UserSwitcherController$addUserSwitchCallback$interactorCallback$1
            @Override // com.android.systemui.user.domain.interactor.UserSwitcherInteractor.UserCallback
            public final void onUserStateChanged() {
                userSwitchCallback.onUserSwitched();
            }
        };
        this.callbackCompatMap.put(userSwitchCallback, userCallback);
        getMUserSwitcherInteractor().addCallback(userCallback);
    }

    public final UserSwitcherInteractor getMUserSwitcherInteractor() {
        return (UserSwitcherInteractor) this.mUserSwitcherInteractor$delegate.getValue();
    }

    public final boolean isUserSwitcherEnabled() {
        return ((UserSwitcherSettingsModel) ((UserRepositoryImpl) getMUserSwitcherInteractor().repository)._userSwitcherSettings.$$delegate_0.getValue()).isUserSwitcherEnabled;
    }
}
