package com.android.systemui.user;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.IActivityManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.UserManager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.window.OnBackInvokedCallback;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.util.UserIcons;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.drawable.CircleFramedDrawable;
import com.android.settingslib.users.CreateUserDialogController;
import com.android.settingslib.users.CreateUserDialogController$$ExternalSyntheticLambda0;
import com.android.settingslib.users.CreateUserDialogController.CustomLengthFilter;
import com.android.settingslib.users.EditUserPhotoController;
import com.android.settingslib.users.EditUserPhotoController$$ExternalSyntheticLambda2;
import com.android.settingslib.utils.CustomDialogHelper;
import com.android.settingslib.utils.ThreadUtils;
import com.android.systemui.R;
import com.android.systemui.plugins.ActivityStarter;
import java.util.concurrent.ExecutionException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class CreateUserActivity extends Activity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final IActivityManager mActivityManager;
    public final ActivityStarter mActivityStarter;
    public final CreateUserActivity$$ExternalSyntheticLambda2 mBackCallback = new OnBackInvokedCallback() { // from class: com.android.systemui.user.CreateUserActivity$$ExternalSyntheticLambda2
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

    /* JADX WARN: Type inference failed for: r5v1, types: [com.android.systemui.user.CreateUserActivity$$ExternalSyntheticLambda2] */
    public CreateUserActivity(UserCreator userCreator, CreateUserDialogController createUserDialogController, IActivityManager iActivityManager, ActivityStarter activityStarter, UiEventLogger uiEventLogger) {
        this.mUserCreator = userCreator;
        this.mCreateUserDialogController = createUserDialogController;
        this.mActivityManager = iActivityManager;
        this.mActivityStarter = activityStarter;
    }

    @Override // android.app.Activity
    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        CreateUserDialogController createUserDialogController = this.mCreateUserDialogController;
        createUserDialogController.mWaitingForActivityResult = false;
        final EditUserPhotoController editUserPhotoController = createUserDialogController.mEditUserPhotoController;
        if (editUserPhotoController != null && i2 == -1 && i == 1004) {
            if (!intent.hasExtra("default_icon_tint_color")) {
                if (intent.getData() != null) {
                    ThreadUtils.postOnBackgroundThread(new EditUserPhotoController$$ExternalSyntheticLambda2(editUserPhotoController, intent.getData(), 2));
                }
            } else {
                final int intExtra = intent.getIntExtra("default_icon_tint_color", -1);
                try {
                    ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.android.settingslib.users.EditUserPhotoController$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            EditUserPhotoController editUserPhotoController2 = EditUserPhotoController.this;
                            int i3 = intExtra;
                            Resources resources = editUserPhotoController2.mActivity.getResources();
                            ThreadUtils.postOnMainThread(new EditUserPhotoController$$ExternalSyntheticLambda2(editUserPhotoController2, UserIcons.convertToBitmapAtUserIconSize(resources, UserIcons.getDefaultUserIconInColor(resources, i3)), 0));
                        }
                    }).get();
                } catch (InterruptedException | ExecutionException e) {
                    Log.e("EditUserPhotoController", "Error processing default icon", e);
                }
            }
        }
    }

    @Override // android.app.Activity
    public final void onBackPressed() {
        Dialog dialog = this.mSetupUserDialog;
        if (dialog != null) {
            dialog.dismiss();
        }
        finish();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v31, types: [com.android.settingslib.users.CreateUserDialogController$$ExternalSyntheticLambda2] */
    /* JADX WARN: Type inference failed for: r4v32, types: [com.android.settingslib.users.CreateUserDialogController$$ExternalSyntheticLambda2] */
    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        final int i = 1;
        setShowWhenLocked(true);
        setContentView(R.layout.activity_create_new_user);
        final CreateUserDialogController createUserDialogController = this.mCreateUserDialogController;
        final int i2 = 0;
        if (bundle != null) {
            createUserDialogController.getClass();
            String string = bundle.getString("pending_photo");
            if (string != null) {
                ThreadUtils.postOnBackgroundThread(new CreateUserDialogController$$ExternalSyntheticLambda0(createUserDialogController, string, i2));
            }
            createUserDialogController.mCurrentState = bundle.getInt("current_state");
            if (bundle.containsKey("admin_status")) {
                createUserDialogController.mIsAdmin = Boolean.valueOf(bundle.getBoolean("admin_status"));
            }
            createUserDialogController.mSavedName = bundle.getString("saved_name");
            createUserDialogController.mWaitingForActivityResult = bundle.getBoolean("awaiting_result", false);
        }
        setTheme(2132018528);
        getString(R.string.user_new_user_name);
        boolean booleanExtra = getIntent().getBooleanExtra("extra_is_keyguard_showing", true);
        CreateUserActivity$$ExternalSyntheticLambda0 createUserActivity$$ExternalSyntheticLambda0 = new CreateUserActivity$$ExternalSyntheticLambda0(this);
        UserCreator userCreator = this.mUserCreator;
        userCreator.getClass();
        final boolean z = UserManager.isMultipleAdminEnabled() && userCreator.userManager.isAdminUser() && !booleanExtra;
        CreateUserActivity$$ExternalSyntheticLambda0 createUserActivity$$ExternalSyntheticLambda02 = new CreateUserActivity$$ExternalSyntheticLambda0(this);
        final CreateUserActivity$$ExternalSyntheticLambda1 createUserActivity$$ExternalSyntheticLambda1 = new CreateUserActivity$$ExternalSyntheticLambda1(this, 0);
        createUserDialogController.mActivity = this;
        createUserDialogController.mCustomDialogHelper = new CustomDialogHelper(this);
        createUserDialogController.mSuccessCallback = createUserActivity$$ExternalSyntheticLambda02;
        createUserDialogController.mActivityStarter = createUserActivity$$ExternalSyntheticLambda0;
        View inflate = View.inflate(createUserDialogController.mActivity, R.layout.grant_admin_dialog_content, null);
        createUserDialogController.mGrantAdminView = inflate;
        createUserDialogController.mCustomDialogHelper.mCustomLayout.addView(inflate);
        RadioGroup radioGroup = (RadioGroup) createUserDialogController.mGrantAdminView.findViewById(R.id.choose_admin);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { // from class: com.android.settingslib.users.CreateUserDialogController$$ExternalSyntheticLambda5
            @Override // android.widget.RadioGroup.OnCheckedChangeListener
            public final void onCheckedChanged(RadioGroup radioGroup2, int i3) {
                CreateUserDialogController createUserDialogController2 = CreateUserDialogController.this;
                createUserDialogController2.mCustomDialogHelper.mPositiveButton.setEnabled(true);
                createUserDialogController2.mIsAdmin = Boolean.valueOf(i3 == R.id.grant_admin_yes);
            }
        });
        if (Boolean.TRUE.equals(createUserDialogController.mIsAdmin)) {
            ((RadioButton) radioGroup.findViewById(R.id.grant_admin_yes)).setChecked(true);
        } else if (Boolean.FALSE.equals(createUserDialogController.mIsAdmin)) {
            ((RadioButton) radioGroup.findViewById(R.id.grant_admin_no)).setChecked(true);
        }
        View inflate2 = View.inflate(createUserDialogController.mActivity, R.layout.edit_user_info_dialog_content, null);
        createUserDialogController.mEditUserInfoView = inflate2;
        createUserDialogController.mCustomDialogHelper.mCustomLayout.addView(inflate2);
        EditText editText = (EditText) createUserDialogController.mEditUserInfoView.findViewById(R.id.user_name);
        createUserDialogController.mUserNameView = editText;
        String str = createUserDialogController.mSavedName;
        if (str == null) {
            editText.setText(R.string.user_new_user_name);
        } else {
            editText.setText(str);
        }
        final EditText editText2 = (EditText) createUserDialogController.mEditUserInfoView.findViewById(R.id.user_name);
        if (editText2.getText().toString().length() > 32) {
            editText2.setText(editText2.getText().toString().substring(0, 32));
        }
        editText2.setFilters(new InputFilter[]{createUserDialogController.new CustomLengthFilter(createUserDialogController.mActivity, 32)});
        editText2.setPrivateImeOptions("disableImage=true");
        editText2.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: com.android.settingslib.users.CreateUserDialogController$$ExternalSyntheticLambda3
            @Override // android.view.View.OnFocusChangeListener
            public final void onFocusChange(View view, boolean z2) {
                CreateUserDialogController createUserDialogController2 = CreateUserDialogController.this;
                EditText editText3 = editText2;
                if (z2) {
                    createUserDialogController2.getClass();
                    return;
                }
                InputMethodManager inputMethodManager = (InputMethodManager) createUserDialogController2.mActivity.getSystemService("input_method");
                if (inputMethodManager == null || !inputMethodManager.isActive()) {
                    return;
                }
                inputMethodManager.hideSoftInputFromWindow(editText3.getWindowToken(), 0);
            }
        });
        editText2.addTextChangedListener(new TextWatcher() { // from class: com.android.settingslib.users.CreateUserDialogController.1
            @Override // android.text.TextWatcher
            public final void onTextChanged(CharSequence charSequence, int i3, int i4, int i5) {
                if ("".equals(charSequence.toString().trim())) {
                    ((AlertDialog) CreateUserDialogController.this.mUserCreationDialog).getButton(-1).setEnabled(false);
                } else {
                    ((AlertDialog) CreateUserDialogController.this.mUserCreationDialog).getButton(-1).setEnabled(true);
                }
            }

            @Override // android.text.TextWatcher
            public final void afterTextChanged(Editable editable) {
            }

            @Override // android.text.TextWatcher
            public final void beforeTextChanged(CharSequence charSequence, int i3, int i4, int i5) {
            }
        });
        ImageView imageView = (ImageView) createUserDialogController.mEditUserInfoView.findViewById(R.id.user_photo);
        Drawable defaultUserIcon = UserIcons.getDefaultUserIcon(createUserDialogController.mActivity.getResources(), -10000, false);
        Bitmap bitmap = createUserDialogController.mSavedPhoto;
        if (bitmap != null) {
            CircleFramedDrawable circleFramedDrawable = new CircleFramedDrawable(bitmap, createUserDialogController.mActivity.getResources().getDimensionPixelSize(R.dimen.user_photo_size_in_user_info_dialog));
            createUserDialogController.mSavedDrawable = circleFramedDrawable;
            defaultUserIcon = circleFramedDrawable;
        }
        imageView.setImageDrawable(defaultUserIcon);
        if (createUserDialogController.isChangePhotoRestrictedByBase(createUserDialogController.mActivity)) {
            createUserDialogController.mEditUserInfoView.findViewById(R.id.add_a_photo_icon).setVisibility(8);
        } else {
            final RestrictedLockUtils.EnforcedAdmin changePhotoAdminRestriction = createUserDialogController.getChangePhotoAdminRestriction(createUserDialogController.mActivity);
            if (changePhotoAdminRestriction != null) {
                imageView.setOnClickListener(new View.OnClickListener() { // from class: com.android.settingslib.users.CreateUserDialogController$$ExternalSyntheticLambda4
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        CreateUserDialogController createUserDialogController2 = CreateUserDialogController.this;
                        RestrictedLockUtils.sendShowAdminSupportDetailsIntent(createUserDialogController2.mActivity, changePhotoAdminRestriction);
                    }
                });
            } else {
                createUserDialogController.mEditUserPhotoController = createUserDialogController.createEditUserPhotoController(imageView);
            }
        }
        createUserDialogController.mCustomDialogHelper.setButton(6, R.string.next, new View.OnClickListener() { // from class: com.android.settingslib.users.CreateUserDialogController$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i2) {
                    case 0:
                        CreateUserDialogController createUserDialogController2 = createUserDialogController;
                        boolean z2 = z;
                        int i3 = createUserDialogController2.mCurrentState + 1;
                        createUserDialogController2.mCurrentState = i3;
                        if (i3 == 1 && !z2) {
                            createUserDialogController2.mCurrentState = i3 + 1;
                        }
                        createUserDialogController2.updateLayout();
                        break;
                    default:
                        CreateUserDialogController createUserDialogController3 = createUserDialogController;
                        boolean z3 = z;
                        int i4 = createUserDialogController3.mCurrentState - 1;
                        createUserDialogController3.mCurrentState = i4;
                        if (i4 == 1 && !z3) {
                            createUserDialogController3.mCurrentState = i4 - 1;
                        }
                        createUserDialogController3.updateLayout();
                        break;
                }
            }
        });
        createUserDialogController.mCustomDialogHelper.setButton(5, R.string.back, new View.OnClickListener() { // from class: com.android.settingslib.users.CreateUserDialogController$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i) {
                    case 0:
                        CreateUserDialogController createUserDialogController2 = createUserDialogController;
                        boolean z2 = z;
                        int i3 = createUserDialogController2.mCurrentState + 1;
                        createUserDialogController2.mCurrentState = i3;
                        if (i3 == 1 && !z2) {
                            createUserDialogController2.mCurrentState = i3 + 1;
                        }
                        createUserDialogController2.updateLayout();
                        break;
                    default:
                        CreateUserDialogController createUserDialogController3 = createUserDialogController;
                        boolean z3 = z;
                        int i4 = createUserDialogController3.mCurrentState - 1;
                        createUserDialogController3.mCurrentState = i4;
                        if (i4 == 1 && !z3) {
                            createUserDialogController3.mCurrentState = i4 - 1;
                        }
                        createUserDialogController3.updateLayout();
                        break;
                }
            }
        });
        createUserDialogController.mUserCreationDialog = createUserDialogController.mCustomDialogHelper.mDialog;
        createUserDialogController.updateLayout();
        createUserDialogController.mUserCreationDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.settingslib.users.CreateUserDialogController$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                CreateUserDialogController createUserDialogController2 = CreateUserDialogController.this;
                Runnable runnable = createUserActivity$$ExternalSyntheticLambda1;
                createUserDialogController2.getClass();
                runnable.run();
                createUserDialogController2.clear();
            }
        });
        createUserDialogController.mUserCreationDialog.setCanceledOnTouchOutside(true);
        Dialog dialog = createUserDialogController.mUserCreationDialog;
        this.mSetupUserDialog = dialog;
        dialog.show();
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
        CreateUserDialogController createUserDialogController = this.mCreateUserDialogController;
        if (createUserDialogController.mUserCreationDialog != null && createUserDialogController.mEditUserPhotoController != null) {
            ThreadUtils.postOnBackgroundThread(new CreateUserDialogController$$ExternalSyntheticLambda0(createUserDialogController, bundle, 1));
        }
        Boolean bool = createUserDialogController.mIsAdmin;
        if (bool != null) {
            bundle.putBoolean("admin_status", Boolean.TRUE.equals(bool));
        }
        bundle.putString("saved_name", createUserDialogController.mUserNameView.getText().toString().trim());
        bundle.putInt("current_state", createUserDialogController.mCurrentState);
        bundle.putBoolean("awaiting_result", createUserDialogController.mWaitingForActivityResult);
        super.onSaveInstanceState(bundle);
    }
}
