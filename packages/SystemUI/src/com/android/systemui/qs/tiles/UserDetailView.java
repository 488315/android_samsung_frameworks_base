package com.android.systemui.qs.tiles;

import android.content.Context;
import android.content.Intent;
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
import com.android.systemui.qs.user.UserSwitchDialogController;
import com.android.systemui.statusbar.phone.UserAvatarView;
import com.android.systemui.statusbar.policy.BaseUserSwitcherAdapter;
import com.android.systemui.statusbar.policy.UserSwitcherController;
import com.android.systemui.user.data.source.UserRecord;
import java.util.List;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public class UserDetailView extends PseudoGridView {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Adapter extends BaseUserSwitcherAdapter implements View.OnClickListener {
        public final Context mContext;
        public final UserSwitcherController mController;
        public UserDetailItemView mCurrentUserView;
        public UserSwitchDialogController.DialogShower mDialogShower;
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
            UserRecord item = getItem(i);
            Context context = viewGroup.getContext();
            int i2 = UserDetailItemView.$r8$clinit;
            if (!(view instanceof UserDetailItemView)) {
                view = LayoutInflater.from(context).inflate(R.layout.sec_qs_user_detail_item, viewGroup, false);
            }
            UserDetailItemView userDetailItemView = (UserDetailItemView) view;
            ColorFilter colorFilter = null;
            if (!item.isCurrent || item.isGuest) {
                userDetailItemView.setOnClickListener(this);
            } else {
                userDetailItemView.setOnClickListener(null);
                userDetailItemView.setClickable(false);
            }
            userDetailItemView.setClickable(true);
            String name = getName(this.mContext, item);
            boolean supportsMultipleUsers = UserManager.supportsMultipleUsers();
            boolean z = item.isSwitchToEnabled;
            boolean z2 = item.isCurrent;
            if (supportsMultipleUsers) {
                Bitmap bitmap = item.picture;
                if (bitmap == null) {
                    Bitmap convertToBitmap = UserIcons.convertToBitmap(BaseUserSwitcherAdapter.getIconDrawable(this.mContext, item));
                    int resolveId = item.resolveId();
                    userDetailItemView.mName.setText(name);
                    UserAvatarView userAvatarView = userDetailItemView.mAvatar;
                    userAvatarView.mDrawable.setIcon(convertToBitmap);
                    userAvatarView.mDrawable.setBadgeIfManagedUser(resolveId, userAvatarView.getContext());
                } else {
                    int i3 = item.info.id;
                    userDetailItemView.mName.setText(name);
                    UserAvatarView userAvatarView2 = userDetailItemView.mAvatar;
                    userAvatarView2.mDrawable.setIcon(bitmap);
                    userAvatarView2.mDrawable.setBadgeIfManagedUser(i3, userAvatarView2.getContext());
                }
            } else if (item.picture == null) {
                Context context2 = this.mContext;
                Drawable iconDrawable = BaseUserSwitcherAdapter.getIconDrawable(context2, item);
                iconDrawable.setTint(context2.getResources().getColor(z2 ? R.color.qs_user_switcher_selected_avatar_icon_color : !z ? R.color.GM2_grey_600 : R.color.qs_user_switcher_avatar_icon_color, context2.getTheme()));
                userDetailItemView.bind(name, new LayerDrawable(new Drawable[]{context2.getDrawable(z2 ? R.drawable.bg_avatar_selected : R.drawable.qs_bg_avatar), iconDrawable}).mutate(), item.resolveId());
            } else {
                CircleFramedDrawable circleFramedDrawable = new CircleFramedDrawable(item.picture, (int) this.mContext.getResources().getDimension(R.dimen.qs_framed_avatar_size));
                if (!z) {
                    BaseUserSwitcherAdapter.Companion.getClass();
                    colorFilter = (ColorFilter) BaseUserSwitcherAdapter.disabledUserAvatarColorFilter$delegate.getValue();
                }
                circleFramedDrawable.setColorFilter(colorFilter);
                userDetailItemView.bind(name, circleFramedDrawable, item.info.id);
            }
            userDetailItemView.setActivated(z2);
            boolean z3 = item.enforcedAdmin != null;
            View view2 = userDetailItemView.mRestrictedPadlock;
            if (view2 != null) {
                view2.setVisibility(z3 ? 0 : 8);
            }
            userDetailItemView.setEnabled(true ^ z3);
            userDetailItemView.setEnabled(z);
            UserSwitcherController.setSelectableAlpha(userDetailItemView);
            if (z2) {
                this.mCurrentUserView = userDetailItemView;
            }
            userDetailItemView.setTag(item);
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
