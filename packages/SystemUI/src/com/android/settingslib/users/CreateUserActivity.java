package com.android.settingslib.users;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class CreateUserActivity extends Activity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public CreateUserDialogController mCreateUserDialogController;
    Dialog mSetupUserDialog;

    public void cancel() {
        setResult(0);
        finish();
    }

    @Override // android.app.Activity
    public final void onActivityResult(int i, int i2, Intent intent) {
        this.mCreateUserDialogController.onActivityResult(i, i2, intent);
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = getIntent();
        this.mCreateUserDialogController = new CreateUserDialogController(intent.getStringExtra("file_authority"));
        setContentView(R.layout.activity_create_new_user);
        if (bundle != null) {
            this.mCreateUserDialogController.onRestoreInstanceState(bundle);
        }
        Dialog createDialog = this.mCreateUserDialogController.createDialog(this, new CreateUserActivity$$ExternalSyntheticLambda0(this), intent.getBooleanExtra("can_create_admin", false), new CreateUserActivity$$ExternalSyntheticLambda0(this), new Runnable() { // from class: com.android.settingslib.users.CreateUserActivity$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                CreateUserActivity.this.cancel();
            }
        });
        this.mSetupUserDialog = createDialog;
        createDialog.show();
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

    @Override // android.app.Activity
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        cancel();
        return super.onTouchEvent(motionEvent);
    }

    public void setSuccessResult(String str, Drawable drawable, String str2, Boolean bool) {
        Intent intent = new Intent(this, (Class<?>) CreateUserActivity.class);
        intent.putExtra("new_user_name", str);
        intent.putExtra("is_admin", bool);
        intent.putExtra("user_icon_path", str2);
        setResult(-1, intent);
        finish();
    }
}
