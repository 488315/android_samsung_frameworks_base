package android.accounts;

import android.app.Activity;
import android.companion.CompanionDeviceManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.UserHandle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.internal.R;

import java.io.IOException;

public class GrantCredentialsPermissionActivity extends Activity implements View.OnClickListener {
    public static final String EXTRAS_ACCOUNT = "account";
    public static final String EXTRAS_AUTH_TOKEN_TYPE = "authTokenType";
    public static final String EXTRAS_REQUESTING_UID = "uid";
    public static final String EXTRAS_RESPONSE = "response";
    private Account mAccount;
    private String mAuthTokenType;
    private int mCallingUid;
    protected LayoutInflater mInflater;
    private Bundle mResultBundle = null;
    private int mUid;

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        String packageLabel;
        super.onCreate(savedInstanceState);
        getWindow().addSystemFlags(524288);
        setContentView(R.layout.grant_credentials_permission);
        setTitle(R.string.grant_permissions_header_text);
        this.mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            setResult(0);
            finish();
            return;
        }
        this.mAccount = (Account) extras.getParcelable("account", Account.class);
        this.mAuthTokenType = extras.getString("authTokenType");
        this.mUid = extras.getInt("uid");
        PackageManager pm = getPackageManager();
        String[] packages = pm.getPackagesForUid(this.mUid);
        if (this.mAccount == null || this.mAuthTokenType == null || packages == null) {
            setResult(0);
            finish();
            return;
        }
        this.mCallingUid = getLaunchedFromUid();
        if (!UserHandle.isSameApp(this.mCallingUid, 1000) && this.mCallingUid != this.mUid) {
            setResult(0);
            finish();
            return;
        }
        try {
            String accountTypeLabel = getAccountLabel(this.mAccount);
            final TextView authTokenTypeView = (TextView) findViewById(R.id.authtoken_type);
            authTokenTypeView.setVisibility(8);
            AccountManagerCallback<String> callback =
                    new AccountManagerCallback<String>() { // from class:
                        // android.accounts.GrantCredentialsPermissionActivity.1
                        @Override // android.accounts.AccountManagerCallback
                        public void run(AccountManagerFuture<String> future) {
                            try {
                                final String authTokenLabel = future.getResult();
                                if (!TextUtils.isEmpty(authTokenLabel)) {
                                    GrantCredentialsPermissionActivity.this.runOnUiThread(
                                            new Runnable() { // from class:
                                                // android.accounts.GrantCredentialsPermissionActivity.1.1
                                                @Override // java.lang.Runnable
                                                public void run() {
                                                    if (!GrantCredentialsPermissionActivity.this
                                                            .isFinishing()) {
                                                        authTokenTypeView.lambda$setTextAsync$0(
                                                                authTokenLabel);
                                                        authTokenTypeView.setVisibility(0);
                                                    }
                                                }
                                            });
                                }
                            } catch (AuthenticatorException e) {
                            } catch (OperationCanceledException e2) {
                            } catch (IOException e3) {
                            }
                        }
                    };
            if (!AccountManager.ACCOUNT_ACCESS_TOKEN_TYPE.equals(this.mAuthTokenType)) {
                AccountManager.get(this)
                        .getAuthTokenLabel(this.mAccount.type, this.mAuthTokenType, callback, null);
            }
            findViewById(R.id.allow_button).setOnClickListener(this);
            findViewById(R.id.deny_button).setOnClickListener(this);
            LinearLayout packagesListView = (LinearLayout) findViewById(R.id.packages_list);
            for (String pkg : packages) {
                try {
                    packageLabel = pm.getApplicationLabel(pm.getApplicationInfo(pkg, 0)).toString();
                } catch (PackageManager.NameNotFoundException e) {
                    packageLabel = pkg;
                }
                packagesListView.addView(newPackageView(packageLabel));
            }
            ((TextView) findViewById(R.id.account_name)).lambda$setTextAsync$0(this.mAccount.name);
            ((TextView) findViewById(R.id.account_type)).lambda$setTextAsync$0(accountTypeLabel);
        } catch (IllegalArgumentException e2) {
            setResult(0);
            finish();
        }
    }

    private String getAccountLabel(Account account) {
        AuthenticatorDescription[] authenticatorTypes =
                AccountManager.get(this).getAuthenticatorTypes();
        for (AuthenticatorDescription desc : authenticatorTypes) {
            if (desc.type.equals(account.type)) {
                try {
                    return createPackageContext(desc.packageName, 0).getString(desc.labelId);
                } catch (PackageManager.NameNotFoundException e) {
                    return account.type;
                } catch (Resources.NotFoundException e2) {
                    return account.type;
                }
            }
        }
        return account.type;
    }

    private View newPackageView(String packageLabel) {
        View view =
                this.mInflater.inflate(R.layout.permissions_package_list_item, (ViewGroup) null);
        ((TextView) view.findViewById(R.id.package_label)).lambda$setTextAsync$0(packageLabel);
        return view;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.allow_button /* 16908777 */:
                AccountManager.get(this)
                        .updateAppPermission(this.mAccount, this.mAuthTokenType, this.mUid, true);
                Intent result = new Intent();
                result.putExtra("retry", true);
                setResult(-1, result);
                setAccountAuthenticatorResult(result.getExtras());
                break;
            case R.id.deny_button /* 16908995 */:
                AccountManager.get(this)
                        .updateAppPermission(this.mAccount, this.mAuthTokenType, this.mUid, false);
                setResult(0);
                break;
        }
        finish();
    }

    public final void setAccountAuthenticatorResult(Bundle result) {
        this.mResultBundle = result;
    }

    @Override // android.app.Activity
    public void finish() {
        Intent intent = getIntent();
        AccountAuthenticatorResponse response =
                (AccountAuthenticatorResponse)
                        intent.getParcelableExtra("response", AccountAuthenticatorResponse.class);
        if (response != null) {
            if (this.mResultBundle != null) {
                response.onResult(this.mResultBundle);
            } else {
                response.onError(4, CompanionDeviceManager.REASON_CANCELED);
            }
        }
        super.finish();
    }
}
