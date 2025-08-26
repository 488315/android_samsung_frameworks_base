package com.android.internal.app;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.UserManager;
import android.util.Log;
import com.android.internal.R;
import com.android.internal.app.AlertController;

/* loaded from: classes5.dex */
public class ConfirmUserCreationActivity extends AlertActivity implements DialogInterface.OnClickListener {
    private static final String TAG = "CreateUser";
    private static final String USER_TYPE = "android.os.usertype.full.SECONDARY";
    private String mAccountName;
    private PersistableBundle mAccountOptions;
    private String mAccountType;
    private boolean mCanProceed;
    private boolean mIsFirstClick;
    private UserManager mUserManager;
    private String mUserName;

    @Override // com.android.internal.app.AlertActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addSystemFlags(524288);
        Intent intent = getIntent();
        this.mUserName = intent.getStringExtra(UserManager.EXTRA_USER_NAME);
        this.mAccountName = intent.getStringExtra(UserManager.EXTRA_USER_ACCOUNT_NAME);
        this.mAccountType = intent.getStringExtra(UserManager.EXTRA_USER_ACCOUNT_TYPE);
        this.mAccountOptions = (PersistableBundle) intent.getParcelableExtra(UserManager.EXTRA_USER_ACCOUNT_OPTIONS, PersistableBundle.class);
        this.mUserManager = (UserManager) getSystemService(UserManager.class);
        String strCheckUserCreationRequirements = checkUserCreationRequirements();
        if (strCheckUserCreationRequirements == null) {
            finish();
            return;
        }
        AlertController.AlertParams alertParams = this.mAlertParams;
        alertParams.mMessage = strCheckUserCreationRequirements;
        alertParams.mPositiveButtonText = getString(17039370);
        alertParams.mPositiveButtonListener = this;
        if (this.mCanProceed) {
            alertParams.mNegativeButtonText = getString(17039360);
            alertParams.mNegativeButtonListener = this;
        }
        this.mIsFirstClick = true;
        setupAlert();
    }

    private String checkUserCreationRequirements() {
        PersistableBundle persistableBundle;
        String callingPackage = getCallingPackage();
        if (callingPackage == null) {
            throw new SecurityException("User Creation intent must be launched with startActivityForResult");
        }
        try {
            boolean z = false;
            ApplicationInfo applicationInfo = getPackageManager().getApplicationInfo(callingPackage, 0);
            boolean z2 = this.mUserManager.hasUserRestriction(UserManager.DISALLOW_ADD_USER) || !this.mUserManager.isAdminUser();
            boolean zCanAddMoreUsers = this.mUserManager.canAddMoreUsers("android.os.usertype.full.SECONDARY");
            Account account = new Account(this.mAccountName, this.mAccountType);
            if (this.mAccountName != null && this.mAccountType != null && (AccountManager.get(this).someUserHasAccount(account) | this.mUserManager.someUserHasSeedAccount(this.mAccountName, this.mAccountType))) {
                z = true;
            }
            this.mCanProceed = true;
            String string = applicationInfo.loadLabel(getPackageManager()).toString();
            if (z2) {
                setResult(1);
                return null;
            }
            if (!isUserPropertyWithinLimit(this.mUserName, 100) || !isUserPropertyWithinLimit(this.mAccountName, 500) || !isUserPropertyWithinLimit(this.mAccountType, 500) || ((persistableBundle = this.mAccountOptions) != null && !persistableBundle.isBundleContentsWithinLengthLimit(1000))) {
                setResult(1);
                Log.i(TAG, "User properties must not exceed their character limits");
                return null;
            }
            if (!zCanAddMoreUsers) {
                setResult(2);
                return null;
            }
            if (z) {
                return getString(R.string.user_creation_account_exists, string, this.mAccountName);
            }
            return getString(R.string.user_creation_adding, string, this.mAccountName);
        } catch (PackageManager.NameNotFoundException unused) {
            throw new SecurityException("Cannot find the calling package");
        }
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialogInterface, int i) {
        setResult(0);
        if (i == -1 && this.mCanProceed && this.mIsFirstClick) {
            this.mIsFirstClick = false;
            Log.i(TAG, "Ok, creating user");
            UserInfo userInfoCreateUser = this.mUserManager.createUser(this.mUserName, "android.os.usertype.full.SECONDARY", 0);
            if (userInfoCreateUser == null) {
                Log.e(TAG, "Couldn't create user");
                finish();
                return;
            } else {
                this.mUserManager.setSeedAccountData(userInfoCreateUser.id, this.mAccountName, this.mAccountType, this.mAccountOptions);
                setResult(-1);
            }
        }
        finish();
    }

    private boolean isUserPropertyWithinLimit(String str, int i) {
        return str == null || str.length() <= i;
    }
}
