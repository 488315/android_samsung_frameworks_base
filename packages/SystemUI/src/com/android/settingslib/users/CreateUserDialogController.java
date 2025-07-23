package com.android.settingslib.users;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.UserHandle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.widget.DialogTitle;
import com.android.internal.util.UserIcons;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.drawable.CircleFramedDrawable;
import com.android.settingslib.users.EditUserPhotoController;
import com.android.settingslib.utils.CustomDialogHelper;
import com.android.settingslib.utils.ThreadUtils;
import com.android.systemui.R;
import com.google.common.util.concurrent.AbstractListeningExecutorService;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import java.io.File;
import java.util.concurrent.Callable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class CreateUserDialogController {
    public Activity mActivity;
    public ActivityStarter mActivityStarter;
    public String mCachedDrawablePath;
    public Runnable mCancelCallback;
    public int mCurrentState;
    public CustomDialogHelper mCustomDialogHelper;
    public View mEditUserInfoView;
    public EditUserPhotoController mEditUserPhotoController;
    public final String mFileAuthority;
    public View mGrantAdminView;
    public Boolean mIsAdmin;
    public Toast mMaxToast = null;
    public Drawable mNewUserIcon;
    public CircleFramedDrawable mSavedDrawable;
    public String mSavedName;
    public Bitmap mSavedPhoto;
    public NewUserData mSuccessCallback;
    public Dialog mUserCreationDialog;
    public String mUserName;
    public EditText mUserNameView;
    public boolean mWaitingForActivityResult;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class CustomLengthFilter extends InputFilter.LengthFilter {
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

    public final Dialog createDialog(Activity activity, ActivityStarter activityStarter, final boolean z, NewUserData newUserData, Runnable runnable) {
        this.mActivity = activity;
        this.mCustomDialogHelper = new CustomDialogHelper(activity);
        this.mSuccessCallback = newUserData;
        this.mCancelCallback = runnable;
        this.mActivityStarter = activityStarter;
        View inflate = View.inflate(this.mActivity, R.layout.grant_admin_dialog_content, null);
        this.mGrantAdminView = inflate;
        this.mCustomDialogHelper.mCustomLayout.addView(inflate);
        RadioGroup radioGroup = (RadioGroup) this.mGrantAdminView.findViewById(R.id.choose_admin);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { // from class: com.android.settingslib.users.CreateUserDialogController$$ExternalSyntheticLambda5
            @Override // android.widget.RadioGroup.OnCheckedChangeListener
            public final void onCheckedChanged(RadioGroup radioGroup2, int i) {
                CreateUserDialogController createUserDialogController = CreateUserDialogController.this;
                createUserDialogController.mCustomDialogHelper.setButtonEnabled(true);
                createUserDialogController.mIsAdmin = Boolean.valueOf(i == R.id.grant_admin_yes);
            }
        });
        if (Boolean.TRUE.equals(this.mIsAdmin)) {
            ((RadioButton) radioGroup.findViewById(R.id.grant_admin_yes)).setChecked(true);
        } else if (Boolean.FALSE.equals(this.mIsAdmin)) {
            ((RadioButton) radioGroup.findViewById(R.id.grant_admin_no)).setChecked(true);
        }
        View inflate2 = View.inflate(this.mActivity, R.layout.edit_user_info_dialog_content, null);
        this.mEditUserInfoView = inflate2;
        this.mCustomDialogHelper.mCustomLayout.addView(inflate2);
        EditText editText = (EditText) this.mEditUserInfoView.findViewById(R.id.user_name);
        this.mUserNameView = editText;
        String str = this.mSavedName;
        if (str == null) {
            editText.setText(R.string.user_new_user_name);
        } else {
            editText.setText(str);
        }
        final EditText editText2 = (EditText) this.mEditUserInfoView.findViewById(R.id.user_name);
        if (editText2.getText().toString().length() > 32) {
            editText2.setText(editText2.getText().toString().substring(0, 32));
        }
        editText2.setFilters(new InputFilter[]{new CustomLengthFilter(this.mActivity, 32)});
        editText2.setPrivateImeOptions("disableImage=true");
        editText2.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: com.android.settingslib.users.CreateUserDialogController$$ExternalSyntheticLambda3
            @Override // android.view.View.OnFocusChangeListener
            public final void onFocusChange(View view, boolean z2) {
                CreateUserDialogController createUserDialogController = CreateUserDialogController.this;
                EditText editText3 = editText2;
                if (z2) {
                    createUserDialogController.getClass();
                    return;
                }
                InputMethodManager inputMethodManager = (InputMethodManager) createUserDialogController.mActivity.getSystemService("input_method");
                if (inputMethodManager == null || !inputMethodManager.isActive()) {
                    return;
                }
                inputMethodManager.hideSoftInputFromWindow(editText3.getWindowToken(), 0);
            }
        });
        editText2.addTextChangedListener(new TextWatcher() { // from class: com.android.settingslib.users.CreateUserDialogController.2
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
        final ImageView imageView = (ImageView) this.mEditUserInfoView.findViewById(R.id.user_photo);
        Drawable defaultUserIcon = UserIcons.getDefaultUserIcon(this.mActivity.getResources(), -10000, false);
        if (this.mCachedDrawablePath != null) {
            ListenableFuture submit = ((AbstractListeningExecutorService) ThreadUtils.getBackgroundExecutor()).submit(new Callable() { // from class: com.android.settingslib.users.CreateUserDialogController$$ExternalSyntheticLambda6
                @Override // java.util.concurrent.Callable
                public final Object call() {
                    CreateUserDialogController createUserDialogController = CreateUserDialogController.this;
                    createUserDialogController.getClass();
                    Bitmap decodeFile = BitmapFactory.decodeFile(new File(createUserDialogController.mCachedDrawablePath).getAbsolutePath());
                    createUserDialogController.mSavedPhoto = decodeFile;
                    CircleFramedDrawable circleFramedDrawable = new CircleFramedDrawable(decodeFile, createUserDialogController.mActivity.getResources().getDimensionPixelSize(R.dimen.user_photo_size_in_user_info_dialog));
                    createUserDialogController.mSavedDrawable = circleFramedDrawable;
                    return circleFramedDrawable;
                }
            });
            submit.addListener(new Futures.CallbackListener(submit, new FutureCallback(this) { // from class: com.android.settingslib.users.CreateUserDialogController.1
                @Override // com.google.common.util.concurrent.FutureCallback
                public final void onSuccess(Object obj) {
                    imageView.setImageDrawable((Drawable) obj);
                }

                @Override // com.google.common.util.concurrent.FutureCallback
                public final void onFailure(Throwable th) {
                }
            }), this.mActivity.getMainExecutor());
        } else {
            imageView.setImageDrawable(defaultUserIcon);
        }
        if (isChangePhotoRestrictedByBase(this.mActivity)) {
            this.mEditUserInfoView.findViewById(R.id.add_a_photo_icon).setVisibility(8);
        } else {
            final RestrictedLockUtils.EnforcedAdmin changePhotoAdminRestriction = getChangePhotoAdminRestriction(this.mActivity);
            if (changePhotoAdminRestriction != null) {
                imageView.setOnClickListener(new View.OnClickListener() { // from class: com.android.settingslib.users.CreateUserDialogController$$ExternalSyntheticLambda4
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        CreateUserDialogController createUserDialogController = CreateUserDialogController.this;
                        RestrictedLockUtils.sendShowAdminSupportDetailsIntent(createUserDialogController.mActivity, changePhotoAdminRestriction);
                    }
                });
            } else {
                this.mEditUserPhotoController = createEditUserPhotoController(imageView);
            }
        }
        final int i = 0;
        this.mCustomDialogHelper.setButton(6, R.string.next, new View.OnClickListener(this) { // from class: com.android.settingslib.users.CreateUserDialogController$$ExternalSyntheticLambda1
            public final /* synthetic */ CreateUserDialogController f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i) {
                    case 0:
                        CreateUserDialogController createUserDialogController = this.f$0;
                        boolean z2 = z;
                        int i2 = createUserDialogController.mCurrentState;
                        int i3 = i2 + 1;
                        createUserDialogController.mCurrentState = i3;
                        if (i3 == 1 && !z2) {
                            createUserDialogController.mCurrentState = i2 + 2;
                        }
                        createUserDialogController.updateLayout();
                        break;
                    default:
                        CreateUserDialogController createUserDialogController2 = this.f$0;
                        boolean z3 = z;
                        int i4 = createUserDialogController2.mCurrentState;
                        int i5 = i4 - 1;
                        createUserDialogController2.mCurrentState = i5;
                        if (i5 == 1 && !z3) {
                            createUserDialogController2.mCurrentState = i4 - 2;
                        }
                        createUserDialogController2.updateLayout();
                        break;
                }
            }
        });
        final int i2 = 1;
        this.mCustomDialogHelper.setButton(5, R.string.back, new View.OnClickListener(this) { // from class: com.android.settingslib.users.CreateUserDialogController$$ExternalSyntheticLambda1
            public final /* synthetic */ CreateUserDialogController f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i2) {
                    case 0:
                        CreateUserDialogController createUserDialogController = this.f$0;
                        boolean z2 = z;
                        int i22 = createUserDialogController.mCurrentState;
                        int i3 = i22 + 1;
                        createUserDialogController.mCurrentState = i3;
                        if (i3 == 1 && !z2) {
                            createUserDialogController.mCurrentState = i22 + 2;
                        }
                        createUserDialogController.updateLayout();
                        break;
                    default:
                        CreateUserDialogController createUserDialogController2 = this.f$0;
                        boolean z3 = z;
                        int i4 = createUserDialogController2.mCurrentState;
                        int i5 = i4 - 1;
                        createUserDialogController2.mCurrentState = i5;
                        if (i5 == 1 && !z3) {
                            createUserDialogController2.mCurrentState = i4 - 2;
                        }
                        createUserDialogController2.updateLayout();
                        break;
                }
            }
        });
        this.mUserCreationDialog = this.mCustomDialogHelper.mDialog;
        updateLayout();
        this.mUserCreationDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.settingslib.users.CreateUserDialogController$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                CreateUserDialogController createUserDialogController = CreateUserDialogController.this;
                if (createUserDialogController.mCurrentState == 3) {
                    NewUserData newUserData2 = createUserDialogController.mSuccessCallback;
                    if (newUserData2 != null) {
                        EditUserPhotoController editUserPhotoController = createUserDialogController.mEditUserPhotoController;
                        if (editUserPhotoController != null && createUserDialogController.mCachedDrawablePath == null) {
                            createUserDialogController.mCachedDrawablePath = editUserPhotoController.mCachedDrawablePath;
                        }
                        newUserData2.onSuccess(createUserDialogController.mUserName, createUserDialogController.mNewUserIcon, createUserDialogController.mCachedDrawablePath, Boolean.valueOf(Boolean.TRUE.equals(createUserDialogController.mIsAdmin)));
                    }
                } else {
                    Runnable runnable2 = createUserDialogController.mCancelCallback;
                    if (runnable2 != null) {
                        runnable2.run();
                    }
                }
                createUserDialogController.mUserCreationDialog = null;
                createUserDialogController.mCustomDialogHelper = null;
                createUserDialogController.mEditUserPhotoController = null;
                createUserDialogController.mSavedPhoto = null;
                createUserDialogController.mSavedName = null;
                createUserDialogController.mSavedDrawable = null;
                createUserDialogController.mIsAdmin = null;
                createUserDialogController.mActivity = null;
                createUserDialogController.mActivityStarter = null;
                createUserDialogController.mGrantAdminView = null;
                createUserDialogController.mEditUserInfoView = null;
                createUserDialogController.mUserNameView = null;
                createUserDialogController.mSuccessCallback = null;
                createUserDialogController.mCancelCallback = null;
                createUserDialogController.mCachedDrawablePath = null;
                createUserDialogController.mCurrentState = 0;
            }
        });
        this.mCustomDialogHelper.mDialogMessage.setPadding(10, 10, 10, 10);
        this.mUserCreationDialog.setCanceledOnTouchOutside(true);
        return this.mUserCreationDialog;
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

    public final void onActivityResult(int i, int i2, Intent intent) {
        this.mWaitingForActivityResult = false;
        final EditUserPhotoController editUserPhotoController = this.mEditUserPhotoController;
        if (editUserPhotoController != null && i2 == -1 && i == 1004) {
            boolean hasExtra = intent.hasExtra("default_icon_tint_color");
            ListeningExecutorService listeningExecutorService = editUserPhotoController.mExecutorService;
            if (hasExtra) {
                final int intExtra = intent.getIntExtra("default_icon_tint_color", -1);
                ListenableFuture submit = ((AbstractListeningExecutorService) listeningExecutorService).submit(new Callable() { // from class: com.android.settingslib.users.EditUserPhotoController$$ExternalSyntheticLambda2
                    @Override // java.util.concurrent.Callable
                    public final Object call() {
                        Resources resources = EditUserPhotoController.this.mActivity.getResources();
                        return UserIcons.convertToBitmapAtUserIconSize(resources, UserIcons.getDefaultUserIconInColor(resources, intExtra));
                    }
                });
                EditUserPhotoController.AnonymousClass1 anonymousClass1 = new FutureCallback() { // from class: com.android.settingslib.users.EditUserPhotoController.1
                    public AnonymousClass1() {
                    }

                    @Override // com.google.common.util.concurrent.FutureCallback
                    public final void onFailure(Throwable th) {
                        Log.e("EditUserPhotoController", "Error processing default icon", th);
                    }

                    @Override // com.google.common.util.concurrent.FutureCallback
                    public final void onSuccess(Object obj) {
                        EditUserPhotoController.m976$$Nest$monPhotoProcessed(EditUserPhotoController.this, (Bitmap) obj);
                    }
                };
                submit.addListener(new Futures.CallbackListener(submit, anonymousClass1), editUserPhotoController.mImageView.getContext().getMainExecutor());
                return;
            }
            if (intent.getData() != null) {
                final Uri data = intent.getData();
                ListenableFuture submit2 = ((AbstractListeningExecutorService) listeningExecutorService).submit(new Callable() { // from class: com.android.settingslib.users.EditUserPhotoController$$ExternalSyntheticLambda1
                    /* JADX WARN: Multi-variable type inference failed */
                    /* JADX WARN: Removed duplicated region for block: B:25:0x003b A[EXC_TOP_SPLITTER, SYNTHETIC] */
                    /* JADX WARN: Type inference failed for: r3v2, types: [java.io.InputStream] */
                    /* JADX WARN: Type inference failed for: r3v3 */
                    /* JADX WARN: Type inference failed for: r3v8 */
                    /* JADX WARN: Type inference failed for: r5v10, types: [java.io.InputStream] */
                    /* JADX WARN: Type inference failed for: r5v2, types: [java.io.IOException, java.lang.Throwable] */
                    /* JADX WARN: Type inference failed for: r5v6 */
                    /* JADX WARN: Type inference failed for: r5v7, types: [java.io.InputStream] */
                    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:34:0x0021 -> B:9:0x0038). Please report as a decompilation issue!!! */
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
                });
                EditUserPhotoController.AnonymousClass2 anonymousClass2 = new FutureCallback() { // from class: com.android.settingslib.users.EditUserPhotoController.2
                    public AnonymousClass2() {
                    }

                    @Override // com.google.common.util.concurrent.FutureCallback
                    public final void onSuccess(Object obj) {
                        EditUserPhotoController.m976$$Nest$monPhotoProcessed(EditUserPhotoController.this, (Bitmap) obj);
                    }

                    @Override // com.google.common.util.concurrent.FutureCallback
                    public final void onFailure(Throwable th) {
                    }
                };
                submit2.addListener(new Futures.CallbackListener(submit2, anonymousClass2), editUserPhotoController.mImageView.getContext().getMainExecutor());
            }
        }
    }

    public final void onRestoreInstanceState(Bundle bundle) {
        this.mCachedDrawablePath = bundle.getString("pending_photo");
        this.mCurrentState = bundle.getInt("current_state");
        if (bundle.containsKey("admin_status")) {
            this.mIsAdmin = Boolean.valueOf(bundle.getBoolean("admin_status"));
        }
        this.mSavedName = bundle.getString("saved_name");
        this.mWaitingForActivityResult = bundle.getBoolean("awaiting_result", false);
    }

    public final void onSaveInstanceState(Bundle bundle) {
        EditUserPhotoController editUserPhotoController;
        if (this.mUserCreationDialog != null && (editUserPhotoController = this.mEditUserPhotoController) != null && this.mCachedDrawablePath == null) {
            this.mCachedDrawablePath = editUserPhotoController.mCachedDrawablePath;
        }
        String str = this.mCachedDrawablePath;
        if (str != null) {
            bundle.putString("pending_photo", str);
        }
        Boolean bool = this.mIsAdmin;
        if (bool != null) {
            bundle.putBoolean("admin_status", Boolean.TRUE.equals(bool));
        }
        bundle.putString("saved_name", this.mUserNameView.getText().toString().trim());
        bundle.putInt("current_state", this.mCurrentState);
        bundle.putBoolean("awaiting_result", this.mWaitingForActivityResult);
    }

    public final void updateLayout() {
        Drawable drawable;
        int i = this.mCurrentState;
        if (i == -1) {
            this.mUserCreationDialog.dismiss();
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
            Drawable drawable2 = this.mActivity.getDrawable(R.drawable.ic_person_add);
            CustomDialogHelper customDialogHelper = this.mCustomDialogHelper;
            customDialogHelper.setVisibility(0, false);
            customDialogHelper.setVisibility(1, true);
            customDialogHelper.setVisibility(2, true);
            customDialogHelper.setVisibility(9, true);
            customDialogHelper.setVisibility(8, true);
            customDialogHelper.setVisibility(7, false);
            customDialogHelper.setupDialogPaddings();
            customDialogHelper.mDialogIcon.setImageDrawable(drawable2);
            customDialogHelper.setButtonEnabled(true);
            customDialogHelper.setTitle(R.string.user_add_user_title);
            customDialogHelper.mDialogMessage.setText(i2);
            customDialogHelper.checkMaxFontScale(customDialogHelper.mDialogMessage, customDialogHelper.mContext.getResources().getDimensionPixelSize(R.dimen.sec_dialog_body_text_size));
            customDialogHelper.mNegativeButton.setText(R.string.cancel);
            customDialogHelper.mPositiveButton.setText(R.string.next);
            DialogTitle dialogTitle = this.mCustomDialogHelper.mDialogTitle;
            dialogTitle.requestFocus();
            dialogTitle.sendAccessibilityEvent(8);
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
            customDialogHelper2.mDialogMessage.setText(R.string.user_grant_admin_message);
            customDialogHelper2.checkMaxFontScale(customDialogHelper2.mDialogMessage, customDialogHelper2.mContext.getResources().getDimensionPixelSize(R.dimen.sec_dialog_body_text_size));
            customDialogHelper2.mNegativeButton.setText(R.string.back);
            customDialogHelper2.mPositiveButton.setText(R.string.next);
            DialogTitle dialogTitle2 = this.mCustomDialogHelper.mDialogTitle;
            dialogTitle2.requestFocus();
            dialogTitle2.sendAccessibilityEvent(8);
            if (this.mIsAdmin == null) {
                this.mCustomDialogHelper.setButtonEnabled(false);
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
            DialogTitle dialogTitle3 = this.mCustomDialogHelper.mDialogTitle;
            dialogTitle3.requestFocus();
            dialogTitle3.sendAccessibilityEvent(8);
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
        if (editUserPhotoController == null || (drawable = editUserPhotoController.mNewUserPhotoDrawable) == null) {
            drawable = this.mSavedDrawable;
        }
        this.mNewUserIcon = drawable;
        String trim = this.mUserNameView.getText().toString().trim();
        String string = this.mActivity.getString(R.string.user_new_user_name);
        if (trim.isEmpty()) {
            trim = string;
        }
        this.mUserName = trim;
        this.mCustomDialogHelper.mDialog.dismiss();
    }
}
