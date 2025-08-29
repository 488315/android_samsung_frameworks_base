package android.accounts;

import android.app.Activity;
import android.companion.CompanionDeviceManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.UserHandle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.android.internal.R;
import java.util.HashMap;

/* loaded from: classes.dex */
public class ChooseAccountActivity extends Activity {
    private static final String TAG = "AccountManager";
    private String mCallingPackage;
    private int mCallingUid;
    private Bundle mResult;
    private Parcelable[] mAccounts = null;
    private AccountManagerResponse mAccountManagerResponse = null;
    private HashMap<String, AuthenticatorDescription> mTypeToAuthDescription = new HashMap<>();

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) throws Resources.NotFoundException {
        super.onCreate(bundle);
        getWindow().addSystemFlags(524288);
        this.mAccounts = getIntent().getParcelableArrayExtra(AccountManager.KEY_ACCOUNTS);
        this.mAccountManagerResponse = (AccountManagerResponse) getIntent().getParcelableExtra(AccountManager.KEY_ACCOUNT_MANAGER_RESPONSE, AccountManagerResponse.class);
        if (this.mAccounts == null) {
            setResult(0);
            finish();
            return;
        }
        this.mCallingUid = getLaunchedFromUid();
        this.mCallingPackage = getLaunchedFromPackage();
        if (UserHandle.isSameApp(this.mCallingUid, 1000) && getIntent().getStringExtra(AccountManager.KEY_ANDROID_PACKAGE_NAME) != null) {
            this.mCallingPackage = getIntent().getStringExtra(AccountManager.KEY_ANDROID_PACKAGE_NAME);
        }
        if (!UserHandle.isSameApp(this.mCallingUid, 1000) && getIntent().getStringExtra(AccountManager.KEY_ANDROID_PACKAGE_NAME) != null) {
            Log.w(getClass().getSimpleName(), "Non-system Uid: " + this.mCallingUid + " tried to override packageName \n");
        }
        getAuthDescriptions();
        AccountInfo[] accountInfoArr = new AccountInfo[this.mAccounts.length];
        for (int i = 0; i < this.mAccounts.length; i++) {
            accountInfoArr[i] = new AccountInfo(((Account) this.mAccounts[i]).name, getDrawableForType(((Account) this.mAccounts[i]).type));
        }
        setContentView(R.layout.choose_account);
        ListView listView = (ListView) findViewById(16908298);
        listView.setAdapter((ListAdapter) new AccountArrayAdapter(this, 17367043, accountInfoArr));
        listView.setChoiceMode(1);
        listView.setTextFilterEnabled(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: android.accounts.ChooseAccountActivity.1
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i2, long j) {
                ChooseAccountActivity.this.onListItemClick((ListView) adapterView, view, i2, j);
            }
        });
    }

    private void getAuthDescriptions() {
        for (AuthenticatorDescription authenticatorDescription : AccountManager.get(this).getAuthenticatorTypes()) {
            this.mTypeToAuthDescription.put(authenticatorDescription.type, authenticatorDescription);
        }
    }

    private Drawable getDrawableForType(String str) {
        if (!this.mTypeToAuthDescription.containsKey(str)) {
            return null;
        }
        try {
            AuthenticatorDescription authenticatorDescription = this.mTypeToAuthDescription.get(str);
            return createPackageContext(authenticatorDescription.packageName, 0).getDrawable(authenticatorDescription.iconId);
        } catch (PackageManager.NameNotFoundException unused) {
            if (!Log.isLoggable(TAG, 5)) {
                return null;
            }
            Log.w(TAG, "No icon name for account type " + str);
            return null;
        } catch (Resources.NotFoundException unused2) {
            if (!Log.isLoggable(TAG, 5)) {
                return null;
            }
            Log.w(TAG, "No icon resource for account type " + str);
            return null;
        }
    }

    protected void onListItemClick(ListView listView, View view, int i, long j) {
        Account account = (Account) this.mAccounts[i];
        AccountManager accountManager = AccountManager.get(this);
        int accountVisibility = accountManager.getAccountVisibility(account, this.mCallingPackage);
        Integer numValueOf = Integer.valueOf(accountVisibility);
        if (numValueOf != null) {
            numValueOf.getClass();
            if (accountVisibility == 4) {
                accountManager.setAccountVisibility(account, this.mCallingPackage, 2);
            }
        }
        Log.d(TAG, "selected account " + account.toSafeString());
        Bundle bundle = new Bundle();
        bundle.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
        bundle.putString("accountType", account.type);
        this.mResult = bundle;
        finish();
    }

    @Override // android.app.Activity
    public void finish() {
        AccountManagerResponse accountManagerResponse = this.mAccountManagerResponse;
        if (accountManagerResponse != null) {
            Bundle bundle = this.mResult;
            if (bundle != null) {
                accountManagerResponse.onResult(bundle);
            } else {
                accountManagerResponse.onError(4, CompanionDeviceManager.REASON_CANCELED);
            }
        }
        super.finish();
    }

    private static class AccountInfo {
        final Drawable drawable;
        final String name;

        AccountInfo(String str, Drawable drawable) {
            this.name = str;
            this.drawable = drawable;
        }
    }

    private static class ViewHolder {
        ImageView icon;
        TextView text;

        private ViewHolder() {
        }
    }

    private static class AccountArrayAdapter extends ArrayAdapter<AccountInfo> {
        private AccountInfo[] mInfos;
        private LayoutInflater mLayoutInflater;

        public AccountArrayAdapter(Context context, int i, AccountInfo[] accountInfoArr) {
            super(context, i, accountInfoArr);
            this.mInfos = accountInfoArr;
            this.mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (view == null) {
                view = this.mLayoutInflater.inflate(R.layout.choose_account_row, (ViewGroup) null);
                viewHolder = new ViewHolder();
                viewHolder.text = (TextView) view.findViewById(R.id.account_row_text);
                viewHolder.icon = (ImageView) view.findViewById(R.id.account_row_icon);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.text.lambda$setTextAsync$0(this.mInfos[i].name);
            viewHolder.icon.lambda$setImageURIAsync$0(this.mInfos[i].drawable);
            return view;
        }
    }
}
