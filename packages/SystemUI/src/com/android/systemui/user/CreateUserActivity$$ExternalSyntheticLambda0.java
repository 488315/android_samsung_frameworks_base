package com.android.systemui.user;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.UserInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.RemoteException;
import android.os.UserManager;
import android.util.Log;
import com.android.internal.util.UserIcons;
import com.android.settingslib.users.ActivityStarter;
import com.android.settingslib.users.NewUserData;
import com.android.settingslib.users.UserCreatingDialog;
import com.android.systemui.R;
import com.android.systemui.plugins.ActivityStarter;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class CreateUserActivity$$ExternalSyntheticLambda0 implements ActivityStarter, NewUserData {
    public final /* synthetic */ CreateUserActivity f$0;

    @Override // com.android.settingslib.users.NewUserData
    public void onSuccess(String str, final Drawable drawable, String str2, final Boolean bool) {
        final CreateUserActivity createUserActivity = this.f$0;
        createUserActivity.mSetupUserDialog.dismiss();
        if (str == null || str.trim().isEmpty()) {
            str = createUserActivity.getString(R.string.user_new_user_name);
        }
        final String str3 = str;
        final Consumer consumer = new Consumer() { // from class: com.android.systemui.user.CreateUserActivity$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                CreateUserActivity createUserActivity2 = CreateUserActivity.this;
                Boolean bool2 = bool;
                UserInfo userInfo = (UserInfo) obj;
                int i = CreateUserActivity.$r8$clinit;
                if (bool2.booleanValue()) {
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
        final CreateUserActivity$$ExternalSyntheticLambda2 createUserActivity$$ExternalSyntheticLambda2 = new CreateUserActivity$$ExternalSyntheticLambda2(createUserActivity, 1);
        final UserCreator userCreator = createUserActivity.mUserCreator;
        userCreator.getClass();
        final UserCreatingDialog userCreatingDialog = new UserCreatingDialog(userCreator.context);
        userCreatingDialog.show();
        userCreator.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.user.UserCreator$createUser$1
            @Override // java.lang.Runnable
            public final void run() {
                final UserInfo createUser = UserCreator.this.userManager.createUser(str3, "android.os.usertype.full.SECONDARY", 0);
                final UserCreator userCreator2 = UserCreator.this;
                Executor executor = userCreator2.mainExecutor;
                final Dialog dialog = userCreatingDialog;
                final Runnable runnable = createUserActivity$$ExternalSyntheticLambda2;
                final Drawable drawable2 = drawable;
                final Consumer consumer2 = consumer;
                executor.execute(new Runnable() { // from class: com.android.systemui.user.UserCreator$createUser$1.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        if (createUser == null) {
                            dialog.dismiss();
                            runnable.run();
                            return;
                        }
                        if (UserManager.supportsMultipleUsers()) {
                            Resources resources = userCreator2.context.getResources();
                            Drawable drawable3 = drawable2;
                            if (drawable3 == null) {
                                drawable3 = UserIcons.getDefaultUserIcon(resources, createUser.id, false);
                            }
                            userCreator2.userManager.setUserIcon(createUser.id, drawable2 == null ? UserIcons.convertToBitmapAtUserIconSize(resources, drawable3) : UserIcons.convertToBitmap(drawable3));
                        } else {
                            final UserCreator userCreator3 = userCreator2;
                            Executor executor2 = userCreator3.bgExecutor;
                            final Drawable drawable4 = drawable2;
                            final UserInfo userInfo = createUser;
                            executor2.execute(new Runnable() { // from class: com.android.systemui.user.UserCreator.createUser.1.1.1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    Drawable drawable5 = drawable4;
                                    Resources resources2 = userCreator3.context.getResources();
                                    if (drawable5 == null) {
                                        drawable5 = UserIcons.getDefaultUserIcon(resources2, userInfo.id, false);
                                    }
                                    userCreator3.userManager.setUserIcon(userInfo.id, UserIcons.convertToBitmapAtUserIconSize(resources2, drawable5));
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

    @Override // com.android.settingslib.users.ActivityStarter
    public void startActivityForResult(final Intent intent) {
        int i = CreateUserActivity.$r8$clinit;
        final CreateUserActivity createUserActivity = this.f$0;
        createUserActivity.mActivityStarter.dismissKeyguardThenExecute(new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.user.CreateUserActivity$$ExternalSyntheticLambda4
            @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
            public final boolean onDismiss() {
                Intent intent2 = intent;
                CreateUserActivity createUserActivity2 = CreateUserActivity.this;
                createUserActivity2.mCreateUserDialogController.mWaitingForActivityResult = true;
                createUserActivity2.startActivityForResult(intent2, VolteConstants.ErrorCode.CLIENT_ERROR_NOT_ALLOWED_URI);
                return true;
            }
        }, null, true);
    }
}
