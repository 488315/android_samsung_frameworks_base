package android.accounts;

import android.app.Activity;
import android.companion.CompanionDeviceManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Insets;
import android.os.Bundle;
import android.os.UserHandle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.internal.R;
import java.io.IOException;

/* loaded from: classes.dex */
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
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setDecorFitsSystemWindows(false);
        getWindow().getDecorView().setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: android.accounts.GrantCredentialsPermissionActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnApplyWindowInsetsListener
            public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                return GrantCredentialsPermissionActivity.lambda$onCreate$0(view, windowInsets);
            }
        });
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
        PackageManager packageManager = getPackageManager();
        String[] packagesForUid = packageManager.getPackagesForUid(this.mUid);
        if (this.mAccount == null || this.mAuthTokenType == null || packagesForUid == null) {
            setResult(0);
            finish();
            return;
        }
        int launchedFromUid = getLaunchedFromUid();
        this.mCallingUid = launchedFromUid;
        if (!UserHandle.isSameApp(launchedFromUid, 1000) && this.mCallingUid != this.mUid) {
            setResult(0);
            finish();
            return;
        }
        try {
            String accountLabel = getAccountLabel(this.mAccount);
            final TextView textView = (TextView) findViewById(R.id.authtoken_type);
            textView.setVisibility(8);
            AccountManagerCallback<String> accountManagerCallback = new AccountManagerCallback<String>() { // from class: android.accounts.GrantCredentialsPermissionActivity.1
                @Override // android.accounts.AccountManagerCallback
                public void run(AccountManagerFuture<String> accountManagerFuture) {
                    try {
                        final String result = accountManagerFuture.getResult();
                        if (TextUtils.isEmpty(result)) {
                            return;
                        }
                        GrantCredentialsPermissionActivity.this.runOnUiThread(new Runnable() { // from class: android.accounts.GrantCredentialsPermissionActivity.1.1
                            @Override // java.lang.Runnable
                            public void run() {
                                if (GrantCredentialsPermissionActivity.this.isFinishing()) {
                                    return;
                                }
                                textView.lambda$setTextAsync$0(result);
                                textView.setVisibility(0);
                            }
                        });
                    } catch (AuthenticatorException | OperationCanceledException | IOException unused) {
                    }
                }
            };
            if (!AccountManager.ACCOUNT_ACCESS_TOKEN_TYPE.equals(this.mAuthTokenType)) {
                AccountManager.get(this).getAuthTokenLabel(this.mAccount.type, this.mAuthTokenType, accountManagerCallback, null);
            }
            findViewById(R.id.allow_button).setOnClickListener(this);
            findViewById(R.id.deny_button).setOnClickListener(this);
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.packages_list);
            for (String str : packagesForUid) {
                try {
                    str = packageManager.getApplicationLabel(packageManager.getApplicationInfo(str, 0)).toString();
                } catch (PackageManager.NameNotFoundException unused) {
                }
                linearLayout.addView(newPackageView(str));
            }
            ((TextView) findViewById(R.id.account_name)).lambda$setTextAsync$0(this.mAccount.name);
            ((TextView) findViewById(R.id.account_type)).lambda$setTextAsync$0(accountLabel);
        } catch (IllegalArgumentException unused2) {
            setResult(0);
            finish();
        }
    }

    static /* synthetic */ WindowInsets lambda$onCreate$0(View view, WindowInsets windowInsets) {
        Insets insets = windowInsets.getInsets(WindowInsets.Type.systemBars());
        view.setPadding(insets.left, insets.top, insets.right, insets.bottom);
        return WindowInsets.CONSUMED;
    }

    private String getAccountLabel(Account account) {
        for (AuthenticatorDescription authenticatorDescription : AccountManager.get(this).getAuthenticatorTypes()) {
            if (authenticatorDescription.type.equals(account.type)) {
                try {
                    return createPackageContext(authenticatorDescription.packageName, 0).getString(authenticatorDescription.labelId);
                } catch (PackageManager.NameNotFoundException unused) {
                    return account.type;
                } catch (Resources.NotFoundException unused2) {
                    return account.type;
                }
            }
        }
        return account.type;
    }

    private View newPackageView(String str) {
        View inflate = this.mInflater.inflate(R.layout.permissions_package_list_item, (ViewGroup) null);
        ((TextView) inflate.findViewById(R.id.package_label)).lambda$setTextAsync$0(str);
        return inflate;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == 16908812) {
            AccountManager.get(this).updateAppPermission(this.mAccount, this.mAuthTokenType, this.mUid, true);
            Intent intent = new Intent();
            intent.putExtra("retry", true);
            setResult(-1, intent);
            setAccountAuthenticatorResult(intent.getExtras());
        } else if (id == 16909031) {
            AccountManager.get(this).updateAppPermission(this.mAccount, this.mAuthTokenType, this.mUid, false);
            setResult(0);
        }
        finish();
    }

    public final void setAccountAuthenticatorResult(Bundle bundle) {
        this.mResultBundle = bundle;
    }

    @Override // android.app.Activity
    public void finish() {
        AccountAuthenticatorResponse accountAuthenticatorResponse = (AccountAuthenticatorResponse) getIntent().getParcelableExtra("response", AccountAuthenticatorResponse.class);
        if (accountAuthenticatorResponse != null) {
            Bundle bundle = this.mResultBundle;
            if (bundle != null) {
                accountAuthenticatorResponse.onResult(bundle);
            } else {
                accountAuthenticatorResponse.onError(4, CompanionDeviceManager.REASON_CANCELED);
            }
        }
        super.finish();
    }
}
