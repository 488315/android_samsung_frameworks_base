package com.android.systemui.user;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.IActivityManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.UserInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
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
import com.android.settingslib.users.CreateUserDialogController.CustomLengthFilter;
import com.android.settingslib.users.EditUserPhotoController;
import com.android.settingslib.utils.CustomDialogHelper;
import com.android.settingslib.utils.ThreadUtils;
import com.android.systemui.R;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.user.CreateUserActivity;
import com.android.systemui.user.CreateUserActivity$$ExternalSyntheticLambda0;
import com.android.systemui.user.CreateUserActivity$$ExternalSyntheticLambda2;
import com.android.systemui.user.UserCreator;
import com.google.common.util.concurrent.AbstractListeningExecutorService;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

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
            boolean hasExtra = intent.hasExtra("default_icon_tint_color");
            ListeningExecutorService listeningExecutorService = editUserPhotoController.mExecutorService;
            if (hasExtra) {
                final int intExtra = intent.getIntExtra("default_icon_tint_color", -1);
                Futures.addCallback(((AbstractListeningExecutorService) listeningExecutorService).submit(new Callable() { // from class: com.android.settingslib.users.EditUserPhotoController$$ExternalSyntheticLambda2
                    @Override // java.util.concurrent.Callable
                    public final Object call() {
                        Resources resources = EditUserPhotoController.this.mActivity.getResources();
                        return UserIcons.convertToBitmapAtUserIconSize(resources, UserIcons.getDefaultUserIconInColor(resources, intExtra));
                    }
                }), new FutureCallback() { // from class: com.android.settingslib.users.EditUserPhotoController.1
                    public AnonymousClass1() {
                    }

                    @Override // com.google.common.util.concurrent.FutureCallback
                    public final void onFailure(Throwable th) {
                        Log.e("EditUserPhotoController", "Error processing default icon", th);
                    }

                    @Override // com.google.common.util.concurrent.FutureCallback
                    public final void onSuccess(Object obj) {
                        EditUserPhotoController.m858$$Nest$monPhotoProcessed(EditUserPhotoController.this, (Bitmap) obj);
                    }
                }, editUserPhotoController.mImageView.getContext().getMainExecutor());
            } else if (intent.getData() != null) {
                final Uri data = intent.getData();
                Futures.addCallback(((AbstractListeningExecutorService) listeningExecutorService).submit(new Callable() { // from class: com.android.settingslib.users.EditUserPhotoController$$ExternalSyntheticLambda1
                    @Override // java.util.concurrent.Callable
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object call() {
                        /*
                            r5 = this;
                            android.net.Uri r0 = r2
                            com.android.settingslib.users.EditUserPhotoController r5 = com.android.settingslib.users.EditUserPhotoController.this
                            r5.getClass()
                            java.lang.String r1 = "Cannot close image stream"
                            java.lang.String r2 = "EditUserPhotoController"
                            r3 = 0
                            android.app.Activity r5 = r5.mActivity     // Catch: java.lang.Throwable -> L2a java.io.FileNotFoundException -> L2c
                            android.content.ContentResolver r5 = r5.getContentResolver()     // Catch: java.lang.Throwable -> L2a java.io.FileNotFoundException -> L2c
                            java.io.InputStream r5 = r5.openInputStream(r0)     // Catch: java.lang.Throwable -> L2a java.io.FileNotFoundException -> L2c
                            android.graphics.Bitmap r3 = android.graphics.BitmapFactory.decodeStream(r5)     // Catch: java.lang.Throwable -> L25 java.io.FileNotFoundException -> L28
                            if (r5 == 0) goto L38
                            r5.close()     // Catch: java.io.IOException -> L20
                            goto L38
                        L20:
                            r5 = move-exception
                            android.util.Log.w(r2, r1, r5)
                            goto L38
                        L25:
                            r0 = move-exception
                            r3 = r5
                            goto L39
                        L28:
                            r0 = move-exception
                            goto L2e
                        L2a:
                            r0 = move-exception
                            goto L39
                        L2c:
                            r0 = move-exception
                            r5 = r3
                        L2e:
                            java.lang.String r4 = "Cannot find image file"
                            android.util.Log.w(r2, r4, r0)     // Catch: java.lang.Throwable -> L25
                            if (r5 == 0) goto L38
                            r5.close()     // Catch: java.io.IOException -> L20
                        L38:
                            return r3
                        L39:
                            if (r3 == 0) goto L43
                            r3.close()     // Catch: java.io.IOException -> L3f
                            goto L43
                        L3f:
                            r5 = move-exception
                            android.util.Log.w(r2, r1, r5)
                        L43:
                            throw r0
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.users.EditUserPhotoController$$ExternalSyntheticLambda1.call():java.lang.Object");
                    }
                }), new FutureCallback() { // from class: com.android.settingslib.users.EditUserPhotoController.2
                    public AnonymousClass2() {
                    }

                    @Override // com.google.common.util.concurrent.FutureCallback
                    public final void onSuccess(Object obj) {
                        EditUserPhotoController.m858$$Nest$monPhotoProcessed(EditUserPhotoController.this, (Bitmap) obj);
                    }

                    @Override // com.google.common.util.concurrent.FutureCallback
                    public final void onFailure(Throwable th) {
                    }
                }, editUserPhotoController.mImageView.getContext().getMainExecutor());
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

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setShowWhenLocked(true);
        setContentView(R.layout.activity_create_new_user);
        final CreateUserDialogController createUserDialogController = this.mCreateUserDialogController;
        if (bundle != null) {
            createUserDialogController.getClass();
            createUserDialogController.mCachedDrawablePath = bundle.getString("pending_photo");
            createUserDialogController.mCurrentState = bundle.getInt("current_state");
            if (bundle.containsKey("admin_status")) {
                createUserDialogController.mIsAdmin = Boolean.valueOf(bundle.getBoolean("admin_status"));
            }
            createUserDialogController.mSavedName = bundle.getString("saved_name");
            createUserDialogController.mWaitingForActivityResult = bundle.getBoolean("awaiting_result", false);
        }
        setTheme(R.style.Theme_SystemUI_Dialog_Alert);
        getString(R.string.user_new_user_name);
        boolean booleanExtra = getIntent().getBooleanExtra("extra_is_keyguard_showing", true);
        CreateUserActivity$$ExternalSyntheticLambda0 createUserActivity$$ExternalSyntheticLambda0 = new CreateUserActivity$$ExternalSyntheticLambda0(this);
        UserCreator userCreator = this.mUserCreator;
        userCreator.getClass();
        final boolean z = UserManager.isMultipleAdminEnabled() && userCreator.userManager.isAdminUser() && !booleanExtra;
        CreateUserActivity$$ExternalSyntheticLambda0 createUserActivity$$ExternalSyntheticLambda02 = new CreateUserActivity$$ExternalSyntheticLambda0(this);
        CreateUserActivity$$ExternalSyntheticLambda2 createUserActivity$$ExternalSyntheticLambda2 = new CreateUserActivity$$ExternalSyntheticLambda2(this, 0);
        createUserDialogController.mActivity = this;
        createUserDialogController.mCustomDialogHelper = new CustomDialogHelper(this);
        createUserDialogController.mSuccessCallback = createUserActivity$$ExternalSyntheticLambda02;
        createUserDialogController.mCancelCallback = createUserActivity$$ExternalSyntheticLambda2;
        createUserDialogController.mActivityStarter = createUserActivity$$ExternalSyntheticLambda0;
        View inflate = View.inflate(createUserDialogController.mActivity, R.layout.grant_admin_dialog_content, null);
        createUserDialogController.mGrantAdminView = inflate;
        createUserDialogController.mCustomDialogHelper.mCustomLayout.addView(inflate);
        RadioGroup radioGroup = (RadioGroup) createUserDialogController.mGrantAdminView.findViewById(R.id.choose_admin);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { // from class: com.android.settingslib.users.CreateUserDialogController$$ExternalSyntheticLambda5
            @Override // android.widget.RadioGroup.OnCheckedChangeListener
            public final void onCheckedChanged(RadioGroup radioGroup2, int i) {
                CreateUserDialogController createUserDialogController2 = CreateUserDialogController.this;
                createUserDialogController2.mCustomDialogHelper.mPositiveButton.setEnabled(true);
                createUserDialogController2.mIsAdmin = Boolean.valueOf(i == R.id.grant_admin_yes);
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
        editText2.addTextChangedListener(new TextWatcher() { // from class: com.android.settingslib.users.CreateUserDialogController.2
            public AnonymousClass2() {
            }

            @Override // android.text.TextWatcher
            public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
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
            public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
        });
        ImageView imageView = (ImageView) createUserDialogController.mEditUserInfoView.findViewById(R.id.user_photo);
        Drawable defaultUserIcon = UserIcons.getDefaultUserIcon(createUserDialogController.mActivity.getResources(), -10000, false);
        if (createUserDialogController.mCachedDrawablePath != null) {
            ListenableFuture submit = ((AbstractListeningExecutorService) ThreadUtils.getBackgroundExecutor()).submit(new Callable() { // from class: com.android.settingslib.users.CreateUserDialogController$$ExternalSyntheticLambda6
                @Override // java.util.concurrent.Callable
                public final Object call() {
                    CreateUserDialogController createUserDialogController2 = CreateUserDialogController.this;
                    createUserDialogController2.getClass();
                    Bitmap decodeFile = BitmapFactory.decodeFile(new File(createUserDialogController2.mCachedDrawablePath).getAbsolutePath());
                    createUserDialogController2.mSavedPhoto = decodeFile;
                    CircleFramedDrawable circleFramedDrawable = new CircleFramedDrawable(decodeFile, createUserDialogController2.mActivity.getResources().getDimensionPixelSize(R.dimen.user_photo_size_in_user_info_dialog));
                    createUserDialogController2.mSavedDrawable = circleFramedDrawable;
                    return circleFramedDrawable;
                }
            });
            submit.addListener(new Futures.CallbackListener(submit, new FutureCallback(createUserDialogController, imageView) { // from class: com.android.settingslib.users.CreateUserDialogController.1
                public final /* synthetic */ ImageView val$userPhotoView;

                public AnonymousClass1(final CreateUserDialogController createUserDialogController2, ImageView imageView2) {
                    this.val$userPhotoView = imageView2;
                }

                @Override // com.google.common.util.concurrent.FutureCallback
                public final void onSuccess(Object obj) {
                    this.val$userPhotoView.setImageDrawable((Drawable) obj);
                }

                @Override // com.google.common.util.concurrent.FutureCallback
                public final void onFailure(Throwable th) {
                }
            }), createUserDialogController2.mActivity.getMainExecutor());
        } else {
            imageView2.setImageDrawable(defaultUserIcon);
        }
        if (createUserDialogController2.isChangePhotoRestrictedByBase(createUserDialogController2.mActivity)) {
            createUserDialogController2.mEditUserInfoView.findViewById(R.id.add_a_photo_icon).setVisibility(8);
        } else {
            final RestrictedLockUtils.EnforcedAdmin changePhotoAdminRestriction = createUserDialogController2.getChangePhotoAdminRestriction(createUserDialogController2.mActivity);
            if (changePhotoAdminRestriction != null) {
                imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.android.settingslib.users.CreateUserDialogController$$ExternalSyntheticLambda4
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        CreateUserDialogController createUserDialogController2 = CreateUserDialogController.this;
                        RestrictedLockUtils.sendShowAdminSupportDetailsIntent(createUserDialogController2.mActivity, changePhotoAdminRestriction);
                    }
                });
            } else {
                createUserDialogController2.mEditUserPhotoController = createUserDialogController2.createEditUserPhotoController(imageView2);
            }
        }
        final int i = 0;
        createUserDialogController2.mCustomDialogHelper.setButton(6, R.string.next, new View.OnClickListener() { // from class: com.android.settingslib.users.CreateUserDialogController$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i) {
                    case 0:
                        CreateUserDialogController createUserDialogController2 = createUserDialogController2;
                        boolean z2 = z;
                        int i2 = createUserDialogController2.mCurrentState;
                        int i3 = i2 + 1;
                        createUserDialogController2.mCurrentState = i3;
                        if (i3 == 1 && !z2) {
                            createUserDialogController2.mCurrentState = i2 + 2;
                        }
                        createUserDialogController2.updateLayout();
                        break;
                    default:
                        CreateUserDialogController createUserDialogController3 = createUserDialogController2;
                        boolean z3 = z;
                        int i4 = createUserDialogController3.mCurrentState;
                        int i5 = i4 - 1;
                        createUserDialogController3.mCurrentState = i5;
                        if (i5 == 1 && !z3) {
                            createUserDialogController3.mCurrentState = i4 - 2;
                        }
                        createUserDialogController3.updateLayout();
                        break;
                }
            }
        });
        final int i2 = 1;
        createUserDialogController2.mCustomDialogHelper.setButton(5, R.string.back, new View.OnClickListener() { // from class: com.android.settingslib.users.CreateUserDialogController$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i2) {
                    case 0:
                        CreateUserDialogController createUserDialogController2 = createUserDialogController2;
                        boolean z2 = z;
                        int i22 = createUserDialogController2.mCurrentState;
                        int i3 = i22 + 1;
                        createUserDialogController2.mCurrentState = i3;
                        if (i3 == 1 && !z2) {
                            createUserDialogController2.mCurrentState = i22 + 2;
                        }
                        createUserDialogController2.updateLayout();
                        break;
                    default:
                        CreateUserDialogController createUserDialogController3 = createUserDialogController2;
                        boolean z3 = z;
                        int i4 = createUserDialogController3.mCurrentState;
                        int i5 = i4 - 1;
                        createUserDialogController3.mCurrentState = i5;
                        if (i5 == 1 && !z3) {
                            createUserDialogController3.mCurrentState = i4 - 2;
                        }
                        createUserDialogController3.updateLayout();
                        break;
                }
            }
        });
        createUserDialogController2.mUserCreationDialog = createUserDialogController2.mCustomDialogHelper.mDialog;
        createUserDialogController2.updateLayout();
        createUserDialogController2.mUserCreationDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.settingslib.users.CreateUserDialogController$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                CreateUserDialogController createUserDialogController2 = CreateUserDialogController.this;
                if (createUserDialogController2.mCurrentState == 3) {
                    CreateUserActivity$$ExternalSyntheticLambda0 createUserActivity$$ExternalSyntheticLambda03 = createUserDialogController2.mSuccessCallback;
                    if (createUserActivity$$ExternalSyntheticLambda03 != null) {
                        String str2 = createUserDialogController2.mUserName;
                        final Drawable drawable = createUserDialogController2.mNewUserIcon;
                        final Boolean valueOf = Boolean.valueOf(Boolean.TRUE.equals(createUserDialogController2.mIsAdmin));
                        final CreateUserActivity createUserActivity = createUserActivity$$ExternalSyntheticLambda03.f$0;
                        createUserActivity.mSetupUserDialog.dismiss();
                        if (str2 == null || str2.trim().isEmpty()) {
                            str2 = createUserActivity.getString(R.string.user_new_user_name);
                        }
                        final String str3 = str2;
                        final Consumer consumer = new Consumer() { // from class: com.android.systemui.user.CreateUserActivity$$ExternalSyntheticLambda5
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
                        final CreateUserActivity$$ExternalSyntheticLambda2 createUserActivity$$ExternalSyntheticLambda22 = new CreateUserActivity$$ExternalSyntheticLambda2(createUserActivity, 1);
                        final UserCreator userCreator2 = createUserActivity.mUserCreator;
                        userCreator2.getClass();
                        final UserCreatingDialog userCreatingDialog = new UserCreatingDialog(userCreator2.context);
                        userCreatingDialog.show();
                        userCreator2.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.user.UserCreator$createUser$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                final UserInfo createUser = UserCreator.this.userManager.createUser(str3, "android.os.usertype.full.SECONDARY", 0);
                                final UserCreator userCreator3 = UserCreator.this;
                                Executor executor = userCreator3.mainExecutor;
                                final Dialog dialog = userCreatingDialog;
                                final Runnable runnable = createUserActivity$$ExternalSyntheticLambda22;
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
                                            Resources resources = userCreator3.context.getResources();
                                            Drawable drawable3 = drawable2;
                                            if (drawable3 == null) {
                                                drawable3 = UserIcons.getDefaultUserIcon(resources, createUser.id, false);
                                            }
                                            userCreator3.userManager.setUserIcon(createUser.id, drawable2 == null ? UserIcons.convertToBitmapAtUserIconSize(resources, drawable3) : UserIcons.convertToBitmap(drawable3));
                                        } else {
                                            final UserCreator userCreator4 = userCreator3;
                                            Executor executor2 = userCreator4.bgExecutor;
                                            final Drawable drawable4 = drawable2;
                                            final UserInfo userInfo = createUser;
                                            executor2.execute(new Runnable() { // from class: com.android.systemui.user.UserCreator.createUser.1.1.1
                                                @Override // java.lang.Runnable
                                                public final void run() {
                                                    Drawable drawable5 = drawable4;
                                                    Resources resources2 = userCreator4.context.getResources();
                                                    if (drawable5 == null) {
                                                        drawable5 = UserIcons.getDefaultUserIcon(resources2, userInfo.id, false);
                                                    }
                                                    userCreator4.userManager.setUserIcon(userInfo.id, UserIcons.convertToBitmapAtUserIconSize(resources2, drawable5));
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
                } else {
                    Runnable runnable = createUserDialogController2.mCancelCallback;
                    if (runnable != null) {
                        runnable.run();
                    }
                }
                createUserDialogController2.mUserCreationDialog = null;
                createUserDialogController2.mCustomDialogHelper = null;
                createUserDialogController2.mEditUserPhotoController = null;
                createUserDialogController2.mSavedPhoto = null;
                createUserDialogController2.mSavedName = null;
                createUserDialogController2.mSavedDrawable = null;
                createUserDialogController2.mIsAdmin = null;
                createUserDialogController2.mActivity = null;
                createUserDialogController2.mActivityStarter = null;
                createUserDialogController2.mGrantAdminView = null;
                createUserDialogController2.mEditUserInfoView = null;
                createUserDialogController2.mUserNameView = null;
                createUserDialogController2.mSuccessCallback = null;
                createUserDialogController2.mCancelCallback = null;
                createUserDialogController2.mCachedDrawablePath = null;
                createUserDialogController2.mCurrentState = 0;
            }
        });
        createUserDialogController2.mCustomDialogHelper.mDialogMessage.setPadding(10, 10, 10, 10);
        createUserDialogController2.mUserCreationDialog.setCanceledOnTouchOutside(true);
        Dialog dialog = createUserDialogController2.mUserCreationDialog;
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
        EditUserPhotoController editUserPhotoController;
        Dialog dialog = this.mSetupUserDialog;
        if (dialog != null && dialog.isShowing()) {
            bundle.putBundle("create_user_dialog_state", this.mSetupUserDialog.onSaveInstanceState());
        }
        CreateUserDialogController createUserDialogController = this.mCreateUserDialogController;
        if (createUserDialogController.mUserCreationDialog != null && (editUserPhotoController = createUserDialogController.mEditUserPhotoController) != null && createUserDialogController.mCachedDrawablePath == null) {
            createUserDialogController.mCachedDrawablePath = editUserPhotoController.mCachedDrawablePath;
        }
        String str = createUserDialogController.mCachedDrawablePath;
        if (str != null) {
            bundle.putString("pending_photo", str);
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
