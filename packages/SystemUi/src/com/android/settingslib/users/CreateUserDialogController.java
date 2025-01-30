package com.android.settingslib.users;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.UserInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.RemoteException;
import android.os.UserHandle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.internal.util.UserIcons;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.drawable.CircleFramedDrawable;
import com.android.settingslib.utils.CustomDialogHelper;
import com.android.systemui.R;
import com.android.systemui.user.CreateUserActivity;
import com.android.systemui.user.CreateUserActivity$$ExternalSyntheticLambda0;
import com.android.systemui.user.CreateUserActivity$$ExternalSyntheticLambda1;
import com.android.systemui.user.UserCreator;
import com.android.systemui.util.DeviceState;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CreateUserDialogController {
    public Activity mActivity;
    public ActivityStarter mActivityStarter;
    public int mCurrentState;
    public CustomDialogHelper mCustomDialogHelper;
    public View mEditUserInfoView;
    public EditUserPhotoController mEditUserPhotoController;
    public final String mFileAuthority;
    public View mGrantAdminView;
    public Boolean mIsAdmin;
    public Toast mMaxToast = null;
    public CircleFramedDrawable mSavedDrawable;
    public String mSavedName;
    public Bitmap mSavedPhoto;
    public CreateUserActivity$$ExternalSyntheticLambda0 mSuccessCallback;
    public Dialog mUserCreationDialog;
    public EditText mUserNameView;
    public boolean mWaitingForActivityResult;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class CustomLengthFilter extends InputFilter.LengthFilter {
        public final Activity mActivity;

        public CustomLengthFilter(Activity activity, int i) {
            super(i);
            this.mActivity = activity;
        }

        @Override // android.text.InputFilter.LengthFilter, android.text.InputFilter
        public final CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
            char charAt;
            CharSequence filter = super.filter(charSequence, i, i2, spanned, i3, i4);
            if (filter != null) {
                Toast toast = CreateUserDialogController.this.mMaxToast;
                if (toast != null && (toast.getView() == null || CreateUserDialogController.this.mMaxToast.getView().isShown())) {
                    CreateUserDialogController.this.mMaxToast.cancel();
                }
                CreateUserDialogController createUserDialogController = CreateUserDialogController.this;
                Activity activity = this.mActivity;
                createUserDialogController.mMaxToast = Toast.makeText(activity, activity.getResources().getString(R.string.max_byte_error), 0);
                CreateUserDialogController.this.mMaxToast.show();
                if (filter.length() > 0 && ((charAt = charSequence.charAt(filter.length() - 1)) == 9770 || charAt == 10013)) {
                    return "";
                }
            }
            return filter;
        }
    }

    public CreateUserDialogController(String str) {
        this.mFileAuthority = str;
    }

    public final void clear() {
        this.mUserCreationDialog = null;
        this.mCustomDialogHelper = null;
        this.mEditUserPhotoController = null;
        this.mSavedPhoto = null;
        this.mSavedName = null;
        this.mSavedDrawable = null;
        this.mIsAdmin = null;
        this.mActivity = null;
        this.mActivityStarter = null;
        this.mGrantAdminView = null;
        this.mEditUserInfoView = null;
        this.mUserNameView = null;
        this.mSuccessCallback = null;
        this.mCurrentState = 0;
    }

    public EditUserPhotoController createEditUserPhotoController(ImageView imageView) {
        return new EditUserPhotoController(this.mActivity, this.mActivityStarter, imageView, this.mSavedPhoto, this.mSavedDrawable, this.mFileAuthority);
    }

    public RestrictedLockUtils.EnforcedAdmin getChangePhotoAdminRestriction(Context context) {
        return RestrictedLockUtilsInternal.checkIfRestrictionEnforced(context, "no_set_user_icon", UserHandle.myUserId());
    }

    public boolean isChangePhotoRestrictedByBase(Context context) {
        return RestrictedLockUtilsInternal.hasBaseUserRestriction(context, "no_set_user_icon", UserHandle.myUserId());
    }

    public final void updateLayout() {
        int i = this.mCurrentState;
        if (i == -1) {
            this.mCustomDialogHelper.mDialog.dismiss();
            return;
        }
        if (i == 0) {
            this.mEditUserInfoView.setVisibility(8);
            this.mGrantAdminView.setVisibility(8);
            SharedPreferences preferences = this.mActivity.getPreferences(0);
            boolean z = preferences.getBoolean("key_add_user_long_message_displayed", false);
            int i2 = z ? R.string.user_add_user_message_short : R.string.user_add_user_message_long;
            if (!z) {
                preferences.edit().putBoolean("key_add_user_long_message_displayed", true).apply();
            }
            Drawable drawable = this.mActivity.getDrawable(R.drawable.ic_person_add);
            CustomDialogHelper customDialogHelper = this.mCustomDialogHelper;
            customDialogHelper.setVisibility(0, false);
            customDialogHelper.setVisibility(1, true);
            customDialogHelper.setVisibility(2, true);
            customDialogHelper.setVisibility(9, true);
            customDialogHelper.setVisibility(8, true);
            customDialogHelper.setVisibility(7, false);
            customDialogHelper.setupDialogPaddings();
            customDialogHelper.mDialogIcon.setImageDrawable(drawable);
            Button button = customDialogHelper.mPositiveButton;
            button.setEnabled(true);
            customDialogHelper.setTitle(R.string.user_add_user_title);
            TextView textView = customDialogHelper.mDialogMessage;
            textView.setText(i2);
            customDialogHelper.checkMaxFontScale(customDialogHelper.mContext.getResources().getDimensionPixelSize(R.dimen.sec_dialog_body_text_size), textView);
            customDialogHelper.mNegativeButton.setText(R.string.cancel);
            button.setText(R.string.next);
            return;
        }
        if (i == 1) {
            this.mEditUserInfoView.setVisibility(8);
            this.mGrantAdminView.setVisibility(0);
            CustomDialogHelper customDialogHelper2 = this.mCustomDialogHelper;
            customDialogHelper2.setVisibility(0, false);
            customDialogHelper2.setVisibility(1, true);
            customDialogHelper2.setVisibility(2, true);
            customDialogHelper2.setVisibility(9, true);
            customDialogHelper2.setVisibility(8, true);
            customDialogHelper2.setVisibility(7, true);
            customDialogHelper2.setupDialogPaddings();
            customDialogHelper2.mDialogIcon.setImageDrawable(this.mActivity.getDrawable(R.drawable.ic_admin_panel_settings));
            customDialogHelper2.setTitle(R.string.user_grant_admin_title);
            TextView textView2 = customDialogHelper2.mDialogMessage;
            textView2.setText(R.string.user_grant_admin_message);
            customDialogHelper2.checkMaxFontScale(customDialogHelper2.mContext.getResources().getDimensionPixelSize(R.dimen.sec_dialog_body_text_size), textView2);
            customDialogHelper2.mNegativeButton.setText(R.string.back);
            customDialogHelper2.mPositiveButton.setText(R.string.next);
            if (this.mIsAdmin == null) {
                this.mCustomDialogHelper.mPositiveButton.setEnabled(false);
                return;
            }
            return;
        }
        if (i == 2) {
            CustomDialogHelper customDialogHelper3 = this.mCustomDialogHelper;
            customDialogHelper3.setVisibility(0, false);
            customDialogHelper3.setVisibility(1, true);
            customDialogHelper3.setVisibility(2, false);
            customDialogHelper3.setVisibility(9, true);
            customDialogHelper3.setVisibility(8, false);
            customDialogHelper3.setVisibility(7, true);
            customDialogHelper3.setupDialogPaddings();
            customDialogHelper3.setTitle(R.string.user_info_settings_title);
            customDialogHelper3.mNegativeButton.setText(R.string.back);
            customDialogHelper3.mPositiveButton.setText(R.string.done);
            this.mEditUserInfoView.setVisibility(0);
            this.mGrantAdminView.setVisibility(8);
            return;
        }
        if (i != 3) {
            if (i < -1) {
                this.mCurrentState = -1;
                updateLayout();
                return;
            } else {
                this.mCurrentState = 3;
                updateLayout();
                return;
            }
        }
        EditUserPhotoController editUserPhotoController = this.mEditUserPhotoController;
        final Drawable drawable2 = editUserPhotoController != null ? editUserPhotoController.mNewUserPhotoDrawable : null;
        String trim = this.mUserNameView.getText().toString().trim();
        String string = this.mActivity.getString(R.string.user_new_user_name);
        if (trim.isEmpty()) {
            trim = string;
        }
        CreateUserActivity$$ExternalSyntheticLambda0 createUserActivity$$ExternalSyntheticLambda0 = this.mSuccessCallback;
        if (createUserActivity$$ExternalSyntheticLambda0 != null) {
            final Boolean valueOf = Boolean.valueOf(Boolean.TRUE.equals(this.mIsAdmin));
            final CreateUserActivity createUserActivity = createUserActivity$$ExternalSyntheticLambda0.f$0;
            createUserActivity.mSetupUserDialog.dismiss();
            if (trim == null || trim.trim().isEmpty()) {
                trim = createUserActivity.getString(R.string.user_new_user_name);
            }
            final Consumer consumer = new Consumer() { // from class: com.android.systemui.user.CreateUserActivity$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    CreateUserActivity createUserActivity2 = CreateUserActivity.this;
                    Boolean bool = valueOf;
                    UserInfo userInfo = (UserInfo) obj;
                    int i3 = CreateUserActivity.$r8$clinit;
                    createUserActivity2.getClass();
                    if (bool.booleanValue()) {
                        createUserActivity2.mUserCreator.userManager.setUserAdmin(userInfo.id);
                    }
                    try {
                        createUserActivity2.mActivityManager.switchUser(userInfo.id);
                    } catch (RemoteException e) {
                        Log.e("CreateUserActivity", "Couldn't switch user.", e);
                    }
                    if (createUserActivity2.isFinishing() || createUserActivity2.isDestroyed()) {
                        return;
                    }
                    createUserActivity2.finish();
                }
            };
            final CreateUserActivity$$ExternalSyntheticLambda1 createUserActivity$$ExternalSyntheticLambda1 = new CreateUserActivity$$ExternalSyntheticLambda1(createUserActivity, 1);
            final UserCreator userCreator = createUserActivity.mUserCreator;
            userCreator.getClass();
            final UserCreatingDialog userCreatingDialog = new UserCreatingDialog(userCreator.context);
            userCreatingDialog.show();
            final String str = trim;
            userCreator.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.user.UserCreator$createUser$1
                @Override // java.lang.Runnable
                public final void run() {
                    final UserInfo createUser = UserCreator.this.userManager.createUser(str, "android.os.usertype.full.SECONDARY", 0);
                    final UserCreator userCreator2 = UserCreator.this;
                    Executor executor = userCreator2.mainExecutor;
                    final Dialog dialog = userCreatingDialog;
                    final Runnable runnable = createUserActivity$$ExternalSyntheticLambda1;
                    final Drawable drawable3 = drawable2;
                    final Consumer consumer2 = consumer;
                    executor.execute(new Runnable() { // from class: com.android.systemui.user.UserCreator$createUser$1.1
                        @Override // java.lang.Runnable
                        public final void run() {
                            if (createUser == null) {
                                dialog.dismiss();
                                runnable.run();
                                return;
                            }
                            if (DeviceState.supportsMultipleUsers()) {
                                Drawable drawable4 = drawable3;
                                Resources resources = userCreator2.context.getResources();
                                userCreator2.userManager.setUserIcon(createUser.id, drawable4 == null ? UserIcons.convertToBitmapAtUserIconSize(resources, UserIcons.getDefaultUserIcon(resources, createUser.id, false)) : UserIcons.convertToBitmap(drawable4));
                            } else {
                                final UserCreator userCreator3 = userCreator2;
                                Executor executor2 = userCreator3.bgExecutor;
                                final Drawable drawable5 = drawable3;
                                final UserInfo userInfo = createUser;
                                executor2.execute(new Runnable() { // from class: com.android.systemui.user.UserCreator.createUser.1.1.1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        Drawable drawable6 = drawable5;
                                        Resources resources2 = userCreator3.context.getResources();
                                        if (drawable6 == null) {
                                            drawable6 = UserIcons.getDefaultUserIcon(resources2, userInfo.id, false);
                                        }
                                        userCreator3.userManager.setUserIcon(userInfo.id, UserIcons.convertToBitmapAtUserIconSize(resources2, drawable6));
                                    }
                                });
                            }
                            dialog.dismiss();
                            consumer2.accept(createUser);
                        }
                    });
                }
            });
        }
        this.mCustomDialogHelper.mDialog.dismiss();
        clear();
    }
}
