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

public class ChooseAccountActivity extends Activity {
    private static final String TAG = "AccountManager";
    private String mCallingPackage;
    private int mCallingUid;
    private Bundle mResult;
    private Parcelable[] mAccounts = null;
    private AccountManagerResponse mAccountManagerResponse = null;
    private HashMap<String, AuthenticatorDescription> mTypeToAuthDescription = new HashMap<>();

    @Override // android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addSystemFlags(524288);
        this.mAccounts = getIntent().getParcelableArrayExtra(AccountManager.KEY_ACCOUNTS);
        this.mAccountManagerResponse =
                (AccountManagerResponse)
                        getIntent()
                                .getParcelableExtra(
                                        AccountManager.KEY_ACCOUNT_MANAGER_RESPONSE,
                                        AccountManagerResponse.class);
        if (this.mAccounts == null) {
            setResult(0);
            finish();
            return;
        }
        this.mCallingUid = getLaunchedFromUid();
        this.mCallingPackage = getLaunchedFromPackage();
        if (UserHandle.isSameApp(this.mCallingUid, 1000)
                && getIntent().getStringExtra(AccountManager.KEY_ANDROID_PACKAGE_NAME) != null) {
            this.mCallingPackage =
                    getIntent().getStringExtra(AccountManager.KEY_ANDROID_PACKAGE_NAME);
        }
        if (!UserHandle.isSameApp(this.mCallingUid, 1000)
                && getIntent().getStringExtra(AccountManager.KEY_ANDROID_PACKAGE_NAME) != null) {
            Log.w(
                    getClass().getSimpleName(),
                    "Non-system Uid: " + this.mCallingUid + " tried to override packageName \n");
        }
        getAuthDescriptions();
        AccountInfo[] mAccountInfos = new AccountInfo[this.mAccounts.length];
        for (int i = 0; i < this.mAccounts.length; i++) {
            mAccountInfos[i] =
                    new AccountInfo(
                            ((Account) this.mAccounts[i]).name,
                            getDrawableForType(((Account) this.mAccounts[i]).type));
        }
        setContentView(R.layout.choose_account);
        ListView list = (ListView) findViewById(16908298);
        list.setAdapter((ListAdapter) new AccountArrayAdapter(this, 17367043, mAccountInfos));
        list.setChoiceMode(1);
        list.setTextFilterEnabled(true);
        list.setOnItemClickListener(
                new AdapterView.OnItemClickListener() { // from class:
                    // android.accounts.ChooseAccountActivity.1
                    @Override // android.widget.AdapterView.OnItemClickListener
                    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                        ChooseAccountActivity.this.onListItemClick(
                                (ListView) parent, v, position, id);
                    }
                });
    }

    private void getAuthDescriptions() {
        for (AuthenticatorDescription desc : AccountManager.get(this).getAuthenticatorTypes()) {
            this.mTypeToAuthDescription.put(desc.type, desc);
        }
    }

    private Drawable getDrawableForType(String accountType) {
        if (!this.mTypeToAuthDescription.containsKey(accountType)) {
            return null;
        }
        try {
            AuthenticatorDescription desc = this.mTypeToAuthDescription.get(accountType);
            Context authContext = createPackageContext(desc.packageName, 0);
            Drawable icon = authContext.getDrawable(desc.iconId);
            return icon;
        } catch (PackageManager.NameNotFoundException e) {
            if (!Log.isLoggable(TAG, 5)) {
                return null;
            }
            Log.w(TAG, "No icon name for account type " + accountType);
            return null;
        } catch (Resources.NotFoundException e2) {
            if (!Log.isLoggable(TAG, 5)) {
                return null;
            }
            Log.w(TAG, "No icon resource for account type " + accountType);
            return null;
        }
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        Account account = (Account) this.mAccounts[position];
        AccountManager am = AccountManager.get(this);
        Integer oldVisibility =
                Integer.valueOf(am.getAccountVisibility(account, this.mCallingPackage));
        if (oldVisibility != null && oldVisibility.intValue() == 4) {
            am.setAccountVisibility(account, this.mCallingPackage, 2);
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
        if (this.mAccountManagerResponse != null) {
            if (this.mResult != null) {
                this.mAccountManagerResponse.onResult(this.mResult);
            } else {
                this.mAccountManagerResponse.onError(4, CompanionDeviceManager.REASON_CANCELED);
            }
        }
        super.finish();
    }

    private static class AccountInfo {
        final Drawable drawable;
        final String name;

        AccountInfo(String name, Drawable drawable) {
            this.name = name;
            this.drawable = drawable;
        }
    }

    private static class ViewHolder {
        ImageView icon;
        TextView text;

        private ViewHolder() {}
    }

    private static class AccountArrayAdapter extends ArrayAdapter<AccountInfo> {
        private AccountInfo[] mInfos;
        private LayoutInflater mLayoutInflater;

        public AccountArrayAdapter(Context context, int textViewResourceId, AccountInfo[] infos) {
            super(context, textViewResourceId, infos);
            this.mInfos = infos;
            this.mLayoutInflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView =
                        this.mLayoutInflater.inflate(R.layout.choose_account_row, (ViewGroup) null);
                holder = new ViewHolder();
                holder.text = (TextView) convertView.findViewById(R.id.account_row_text);
                holder.icon = (ImageView) convertView.findViewById(R.id.account_row_icon);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.text.lambda$setTextAsync$0(this.mInfos[position].name);
            holder.icon.lambda$setImageURIAsync$0(this.mInfos[position].drawable);
            return convertView;
        }
    }
}
