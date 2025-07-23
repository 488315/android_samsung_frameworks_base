package com.android.systemui.qs.tiles;

import android.content.Context;
import android.content.Intent;
import android.content.pm.UserInfo;
import android.graphics.Bitmap;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Trace;
import android.os.UserManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.util.UserIcons;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.drawable.CircleFramedDrawable;
import com.android.systemui.R;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.qs.PseudoGridView;
import com.android.systemui.qs.QSUserSwitcherEvent;
import com.android.systemui.statusbar.phone.UserAvatarView;
import com.android.systemui.statusbar.policy.BaseUserSwitcherAdapter;
import com.android.systemui.statusbar.policy.UserSwitcherController;
import com.android.systemui.user.data.source.UserRecord;
import com.android.systemui.user.legacyhelper.ui.LegacyUserUiHelper;
import com.android.systemui.user.ui.dialog.DialogShowerImpl;
import java.util.List;
import java.util.stream.Collectors;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class UserDetailView extends PseudoGridView {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Adapter extends BaseUserSwitcherAdapter implements View.OnClickListener {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final Context mContext;
        public final UserSwitcherController mController;
        public UserDetailItemView mCurrentUserView;
        public DialogShowerImpl mDialogShower;
        public final FalsingManager mFalsingManager;
        public final UiEventLogger mUiEventLogger;

        public Adapter(Context context, UserSwitcherController userSwitcherController, UiEventLogger uiEventLogger, FalsingManager falsingManager) {
            super(userSwitcherController);
            this.mContext = context;
            this.mController = userSwitcherController;
            this.mUiEventLogger = uiEventLogger;
            this.mFalsingManager = falsingManager;
            userSwitcherController.getMUserSwitcherInteractor().refreshUsersScheduler.refreshIfNotPaused();
        }

        @Override // com.android.systemui.statusbar.policy.BaseUserSwitcherAdapter
        public final List getUsers() {
            return (List) super.getUsers().stream().filter(new UserDetailView$Adapter$$ExternalSyntheticLambda0()).collect(Collectors.toList());
        }

        @Override // android.widget.Adapter
        public final View getView(int i, View view, ViewGroup viewGroup) {
            UserInfo userInfo;
            UserInfo userInfo2;
            UserRecord userRecord = (UserRecord) getUsers().get(i);
            Context context = viewGroup.getContext();
            int i2 = UserDetailItemView.$r8$clinit;
            if (!(view instanceof UserDetailItemView)) {
                view = LayoutInflater.from(context).inflate(R.layout.sec_qs_user_detail_item, viewGroup, false);
            }
            UserDetailItemView userDetailItemView = (UserDetailItemView) view;
            boolean z = userRecord.isCurrent;
            boolean z2 = userRecord.isGuest;
            ColorFilter colorFilter = null;
            if (!z || z2) {
                userDetailItemView.setOnClickListener(this);
            } else {
                userDetailItemView.setOnClickListener(null);
                userDetailItemView.setClickable(false);
            }
            userDetailItemView.setClickable(true);
            String userRecordName = LegacyUserUiHelper.getUserRecordName(this.mContext, userRecord, this.controller.getMUserSwitcherInteractor().isGuestUserAutoCreated, this.controller.getMUserSwitcherInteractor().isGuestUserResetting);
            boolean supportsMultipleUsers = UserManager.supportsMultipleUsers();
            int i3 = -10000;
            boolean z3 = userRecord.isSwitchToEnabled;
            boolean z4 = userRecord.isCurrent;
            if (supportsMultipleUsers) {
                Bitmap bitmap = userRecord.picture;
                if (bitmap == null) {
                    Bitmap convertToBitmap = UserIcons.convertToBitmap(BaseUserSwitcherAdapter.getIconDrawable(this.mContext, userRecord));
                    if (!z2 && (userInfo2 = userRecord.info) != null) {
                        i3 = userInfo2.id;
                    }
                    userDetailItemView.mName.setText(userRecordName);
                    UserAvatarView userAvatarView = userDetailItemView.mAvatar;
                    userAvatarView.mDrawable.setIcon(convertToBitmap);
                    userAvatarView.mDrawable.setBadgeIfManagedUser(i3, userAvatarView.getContext());
                } else {
                    int i4 = userRecord.info.id;
                    userDetailItemView.mName.setText(userRecordName);
                    UserAvatarView userAvatarView2 = userDetailItemView.mAvatar;
                    userAvatarView2.mDrawable.setIcon(bitmap);
                    userAvatarView2.mDrawable.setBadgeIfManagedUser(i4, userAvatarView2.getContext());
                }
            } else if (userRecord.picture == null) {
                Context context2 = this.mContext;
                Drawable iconDrawable = BaseUserSwitcherAdapter.getIconDrawable(context2, userRecord);
                iconDrawable.setTint(context2.getResources().getColor(z4 ? R.color.qs_user_switcher_selected_avatar_icon_color : !z3 ? R.color.GM2_grey_600 : R.color.qs_user_switcher_avatar_icon_color, context2.getTheme()));
                Drawable mutate = new LayerDrawable(new Drawable[]{context2.getDrawable(z4 ? R.drawable.bg_avatar_selected : R.drawable.qs_bg_avatar), iconDrawable}).mutate();
                if (!z2 && (userInfo = userRecord.info) != null) {
                    i3 = userInfo.id;
                }
                userDetailItemView.bind(userRecordName, mutate, i3);
            } else {
                CircleFramedDrawable circleFramedDrawable = new CircleFramedDrawable(userRecord.picture, (int) this.mContext.getResources().getDimension(R.dimen.qs_framed_avatar_size));
                if (!z3) {
                    BaseUserSwitcherAdapter.Companion.getClass();
                    colorFilter = (ColorFilter) BaseUserSwitcherAdapter.disabledUserAvatarColorFilter$delegate.getValue();
                }
                circleFramedDrawable.setColorFilter(colorFilter);
                userDetailItemView.bind(userRecordName, circleFramedDrawable, userRecord.info.id);
            }
            userDetailItemView.setActivated(z4);
            userDetailItemView.setSelected(z4);
            boolean z5 = userRecord.enforcedAdmin != null;
            View view2 = userDetailItemView.mRestrictedPadlock;
            if (view2 != null) {
                view2.setVisibility(z5 ? 0 : 8);
            }
            userDetailItemView.setEnabled(true ^ z5);
            userDetailItemView.setEnabled(z3);
            UserSwitcherController.Companion.getClass();
            userDetailItemView.setAlpha(userDetailItemView.isEnabled() ? 1.0f : 0.38f);
            if (z4) {
                this.mCurrentUserView = userDetailItemView;
            }
            userDetailItemView.setTag(userRecord);
            return userDetailItemView;
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            if (this.mFalsingManager.isFalseTap(1)) {
                return;
            }
            Trace.beginSection("UserDetailView.Adapter#onClick");
            UserRecord userRecord = (UserRecord) view.getTag();
            RestrictedLockUtils.EnforcedAdmin enforcedAdmin = userRecord.enforcedAdmin;
            if (enforcedAdmin != null) {
                Intent showAdminSupportDetailsIntent = RestrictedLockUtils.getShowAdminSupportDetailsIntent(enforcedAdmin);
                this.mController.getMUserSwitcherInteractor().dismissDialog();
                this.mController.activityStarter.startActivity(showAdminSupportDetailsIntent, true);
            } else if (userRecord.isSwitchToEnabled) {
                MetricsLogger.action(this.mContext, 156);
                this.mUiEventLogger.log(QSUserSwitcherEvent.QS_USER_SWITCH);
                if (!userRecord.isAddUser && !userRecord.isRestricted && userRecord.enforcedAdmin == null) {
                    UserDetailItemView userDetailItemView = this.mCurrentUserView;
                    if (userDetailItemView != null) {
                        userDetailItemView.setActivated(false);
                    }
                    view.setActivated(true);
                }
                onUserListItemClicked(userRecord, this.mDialogShower);
            }
            Trace.endSection();
        }
    }

    public UserDetailView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
