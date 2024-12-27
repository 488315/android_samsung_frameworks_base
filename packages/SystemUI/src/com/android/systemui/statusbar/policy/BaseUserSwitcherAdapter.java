package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.content.pm.UserInfo;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.os.UserManager;
import android.widget.BaseAdapter;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.util.UserIcons;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.qs.user.UserSwitchDialogController;
import com.android.systemui.user.data.source.UserRecord;
import com.android.systemui.user.domain.interactor.UserSwitcherInteractor;
import com.android.systemui.user.legacyhelper.data.LegacyUserDataHelper;
import com.android.systemui.user.legacyhelper.ui.LegacyUserUiHelper;
import com.android.systemui.user.shared.model.UserActionModel;
import com.android.systemui.user.utils.MultiUserActionsEvent;
import com.android.systemui.user.utils.MultiUserActionsEventHelper;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public abstract class BaseUserSwitcherAdapter extends BaseAdapter {
    public static final Companion Companion = new Companion(null);
    public static final Lazy disabledUserAvatarColorFilter$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.policy.BaseUserSwitcherAdapter$Companion$disabledUserAvatarColorFilter$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            ColorMatrix colorMatrix = new ColorMatrix();
            colorMatrix.setSaturation(0.0f);
            return new ColorMatrixColorFilter(colorMatrix);
        }
    });
    public final UserSwitcherController controller;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public BaseUserSwitcherAdapter(UserSwitcherController userSwitcherController) {
        this.controller = userSwitcherController;
        final WeakReference weakReference = new WeakReference(this);
        userSwitcherController.getMUserSwitcherInteractor().addCallback(new UserSwitcherInteractor.UserCallback() { // from class: com.android.systemui.statusbar.policy.UserSwitcherController$addAdapter$1
            @Override // com.android.systemui.user.domain.interactor.UserSwitcherInteractor.UserCallback
            public final boolean isEvictable() {
                return weakReference.get() == null;
            }

            @Override // com.android.systemui.user.domain.interactor.UserSwitcherInteractor.UserCallback
            public final void onUserStateChanged() {
                BaseUserSwitcherAdapter baseUserSwitcherAdapter = (BaseUserSwitcherAdapter) weakReference.get();
                if (baseUserSwitcherAdapter != null) {
                    baseUserSwitcherAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    public static final Drawable getIconDrawable(Context context, UserRecord userRecord) {
        Companion.getClass();
        boolean supportsMultipleUsers = UserManager.supportsMultipleUsers();
        boolean z = userRecord.isAddUser;
        if (!supportsMultipleUsers) {
            Drawable drawable = context.getDrawable(LegacyUserUiHelper.getUserSwitcherActionIconResourceId(z, userRecord.isGuest, userRecord.isAddSupervisedUser, false, userRecord.isManageUsers));
            if (drawable != null) {
                return drawable;
            }
            throw new IllegalStateException("Required value was null.".toString());
        }
        if (!z) {
            return UserIcons.getDefaultUserIcon(context.getResources(), userRecord.resolveId(), false);
        }
        Drawable drawable2 = context.getDrawable(R.drawable.ic_sec_add_circle_qs);
        if (drawable2 != null) {
            return drawable2.mutate();
        }
        throw new IllegalStateException("Required value was null.".toString());
    }

    @Override // android.widget.Adapter
    public final int getCount() {
        return getUsers().size();
    }

    @Override // android.widget.Adapter
    public final long getItemId(int i) {
        return i;
    }

    public final String getName(Context context, UserRecord userRecord) {
        return LegacyUserUiHelper.getUserRecordName(context, userRecord, this.controller.getMUserSwitcherInteractor().isGuestUserAutoCreated, this.controller.getMUserSwitcherInteractor().isGuestUserResetting, false);
    }

    public List getUsers() {
        ArrayList arrayList = (ArrayList) this.controller.getMUserSwitcherInteractor().userRecords.$$delegate_0.getValue();
        ArrayList arrayList2 = new ArrayList();
        for (Object obj : arrayList) {
            UserRecord userRecord = (UserRecord) obj;
            if (LsRune.LOCKUI_MULTI_USER) {
                if (!((KeyguardInteractor) this.controller.keyguardInteractor$delegate.getValue()).isKeyguardShowing()) {
                    if (!this.controller.isUserSwitcherEnabled() && !userRecord.isCurrent) {
                    }
                    arrayList2.add(obj);
                } else if (!userRecord.isRestricted && (this.controller.isUserSwitcherEnabled() || userRecord.isCurrent)) {
                    if (userRecord.info != null) {
                        arrayList2.add(obj);
                    }
                }
            } else if (!((KeyguardInteractor) this.controller.keyguardInteractor$delegate.getValue()).isKeyguardShowing() || !userRecord.isRestricted) {
                if (!this.controller.isUserSwitcherEnabled() && !userRecord.isCurrent) {
                }
                arrayList2.add(obj);
            }
        }
        return arrayList2;
    }

    public final void onUserListItemClicked(UserRecord userRecord, UserSwitchDialogController.DialogShower dialogShower) {
        UserActionModel userActionModel;
        UserSwitcherInteractor mUserSwitcherInteractor = this.controller.getMUserSwitcherInteractor();
        LegacyUserDataHelper.INSTANCE.getClass();
        UserInfo userInfo = userRecord.info;
        if (userInfo != null) {
            UiEventLogger uiEventLogger = mUserSwitcherInteractor.uiEventLogger;
            MultiUserActionsEventHelper.Companion companion = MultiUserActionsEventHelper.Companion;
            if (userInfo == null) {
                throw new IllegalStateException("Required value was null.".toString());
            }
            companion.getClass();
            uiEventLogger.log(userInfo.isGuest() ? MultiUserActionsEvent.SWITCH_TO_GUEST_FROM_USER_SWITCHER : userInfo.isRestricted() ? MultiUserActionsEvent.SWITCH_TO_RESTRICTED_USER_FROM_USER_SWITCHER : MultiUserActionsEvent.SWITCH_TO_USER_FROM_USER_SWITCHER);
            UserInfo userInfo2 = userRecord.info;
            if (userInfo2 == null) {
                throw new IllegalStateException("Required value was null.".toString());
            }
            mUserSwitcherInteractor.selectUser(userInfo2.id, dialogShower);
            return;
        }
        if (!(!(userInfo != null))) {
            throw new IllegalStateException("Check failed.".toString());
        }
        if (userRecord.isAddUser) {
            userActionModel = UserActionModel.ADD_USER;
        } else if (userRecord.isAddSupervisedUser) {
            userActionModel = UserActionModel.ADD_SUPERVISED_USER;
        } else if (userRecord.isGuest) {
            userActionModel = UserActionModel.ENTER_GUEST_MODE;
        } else {
            if (!userRecord.isManageUsers) {
                throw new IllegalStateException(("Not a known action: " + userRecord).toString());
            }
            userActionModel = UserActionModel.NAVIGATE_TO_USER_MANAGEMENT;
        }
        mUserSwitcherInteractor.executeAction(userActionModel, dialogShower);
    }

    @Override // android.widget.Adapter
    public final UserRecord getItem(int i) {
        return (UserRecord) getUsers().get(i);
    }
}
