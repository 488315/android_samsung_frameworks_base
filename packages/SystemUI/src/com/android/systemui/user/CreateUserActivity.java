package com.android.systemui.user;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.IActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserManager;
import android.window.OnBackInvokedCallback;
import com.android.internal.logging.UiEventLogger;
import com.android.settingslib.users.CreateUserDialogController;
import com.android.systemui.R;
import com.android.systemui.plugins.ActivityStarter;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class CreateUserActivity extends Activity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final IActivityManager mActivityManager;
    public final ActivityStarter mActivityStarter;
    public final CreateUserActivity$$ExternalSyntheticLambda3 mBackCallback = new OnBackInvokedCallback() { // from class: com.android.systemui.user.CreateUserActivity$$ExternalSyntheticLambda3
        @Override // android.window.OnBackInvokedCallback
        public final void onBackInvoked() {
            CreateUserActivity createUserActivity = CreateUserActivity.this;
            int i = CreateUserActivity.$r8$clinit;
            Dialog dialog = createUserActivity.mSetupUserDialog;
            if (dialog != null) {
                dialog.dismiss();
            }
            createUserActivity.finish();
        }
    };
    public final CreateUserDialogController mCreateUserDialogController;
    public Dialog mSetupUserDialog;
    public final UserCreator mUserCreator;

    /* JADX WARN: Type inference failed for: r5v1, types: [com.android.systemui.user.CreateUserActivity$$ExternalSyntheticLambda3] */
    public CreateUserActivity(UserCreator userCreator, CreateUserDialogController createUserDialogController, IActivityManager iActivityManager, ActivityStarter activityStarter, UiEventLogger uiEventLogger) {
        this.mUserCreator = userCreator;
        this.mCreateUserDialogController = createUserDialogController;
        this.mActivityManager = iActivityManager;
        this.mActivityStarter = activityStarter;
    }

    @Override // android.app.Activity
    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        this.mCreateUserDialogController.onActivityResult(i, i2, intent);
    }

    @Override // android.app.Activity
    public final void onBackPressed() {
        Dialog dialog = this.mSetupUserDialog;
        if (dialog != null) {
            dialog.dismiss();
        }
        finish();
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setShowWhenLocked(true);
        setContentView(R.layout.activity_create_new_user);
        if (bundle != null) {
            this.mCreateUserDialogController.onRestoreInstanceState(bundle);
        }
        setTheme(R.style.Theme_SystemUI_Dialog_Alert);
        getString(R.string.user_new_user_name);
        boolean booleanExtra = getIntent().getBooleanExtra("extra_is_keyguard_showing", true);
        CreateUserActivity$$ExternalSyntheticLambda0 createUserActivity$$ExternalSyntheticLambda0 = new CreateUserActivity$$ExternalSyntheticLambda0(this);
        UserCreator userCreator = this.mUserCreator;
        userCreator.getClass();
        Dialog createDialog = this.mCreateUserDialogController.createDialog(this, createUserActivity$$ExternalSyntheticLambda0, UserManager.isMultipleAdminEnabled() && !userCreator.userManager.hasUserRestriction("no_grant_admin") && userCreator.userManager.isAdminUser() && !booleanExtra, new CreateUserActivity$$ExternalSyntheticLambda0(this), new CreateUserActivity$$ExternalSyntheticLambda2(this, 0));
        this.mSetupUserDialog = createDialog;
        if (createDialog instanceof AlertDialog) {
            ((AlertDialog) createDialog).semSetBackgroundBlurEnabled(true);
        }
        this.mSetupUserDialog.show();
        getOnBackInvokedDispatcher().registerOnBackInvokedCallback(0, this.mBackCallback);
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        getOnBackInvokedDispatcher().unregisterOnBackInvokedCallback(this.mBackCallback);
        super.onDestroy();
    }

    @Override // android.app.Activity
    public final void onRestoreInstanceState(Bundle bundle) {
        Dialog dialog;
        super.onRestoreInstanceState(bundle);
        Bundle bundle2 = bundle.getBundle("create_user_dialog_state");
        if (bundle2 == null || (dialog = this.mSetupUserDialog) == null) {
            return;
        }
        dialog.onRestoreInstanceState(bundle2);
    }

    @Override // android.app.Activity
    public final void onSaveInstanceState(Bundle bundle) {
        Dialog dialog = this.mSetupUserDialog;
        if (dialog != null && dialog.isShowing()) {
            bundle.putBundle("create_user_dialog_state", this.mSetupUserDialog.onSaveInstanceState());
        }
        this.mCreateUserDialogController.onSaveInstanceState(bundle);
        super.onSaveInstanceState(bundle);
    }
}
