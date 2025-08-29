package android.accounts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Bundle;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/* loaded from: classes.dex */
public class ChooseAccountTypeActivity extends Activity {
    private static final String TAG = "AccountChooser";
    private ArrayList<AuthInfo> mAuthenticatorInfosToDisplay;
    private HashMap<String, AuthInfo> mTypeToAuthenticatorInfo = new HashMap<>();

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) throws Resources.NotFoundException {
        HashSet hashSet;
        super.onCreate(bundle);
        getWindow().addSystemFlags(524288);
        if (Log.isLoggable(TAG, 2)) {
            Log.v(TAG, "ChooseAccountTypeActivity.onCreate(savedInstanceState=" + bundle + NavigationBarInflaterView.KEY_CODE_END);
        }
        String[] stringArrayExtra = getIntent().getStringArrayExtra(ChooseTypeAndAccountActivity.EXTRA_ALLOWABLE_ACCOUNT_TYPES_STRING_ARRAY);
        if (stringArrayExtra != null) {
            hashSet = new HashSet(stringArrayExtra.length);
            for (String str : stringArrayExtra) {
                hashSet.add(str);
            }
        } else {
            hashSet = null;
        }
        buildTypeToAuthDescriptionMap();
        this.mAuthenticatorInfosToDisplay = new ArrayList<>(this.mTypeToAuthenticatorInfo.size());
        for (Map.Entry<String, AuthInfo> entry : this.mTypeToAuthenticatorInfo.entrySet()) {
            String key = entry.getKey();
            AuthInfo value = entry.getValue();
            if (hashSet == null || hashSet.contains(key)) {
                this.mAuthenticatorInfosToDisplay.add(value);
            }
        }
        if (this.mAuthenticatorInfosToDisplay.isEmpty()) {
            Bundle bundle2 = new Bundle();
            bundle2.putString(AccountManager.KEY_ERROR_MESSAGE, "no allowable account types");
            setResult(-1, new Intent().putExtras(bundle2));
            finish();
            return;
        }
        if (this.mAuthenticatorInfosToDisplay.size() == 1) {
            setResultAndFinish(this.mAuthenticatorInfosToDisplay.get(0).desc.type);
            return;
        }
        setContentView(R.layout.choose_account_type);
        ListView listView = (ListView) findViewById(16908298);
        listView.setAdapter((ListAdapter) new AccountArrayAdapter(this, 17367043, this.mAuthenticatorInfosToDisplay));
        listView.setChoiceMode(0);
        listView.setTextFilterEnabled(false);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: android.accounts.ChooseAccountTypeActivity.1
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                ChooseAccountTypeActivity chooseAccountTypeActivity = ChooseAccountTypeActivity.this;
                chooseAccountTypeActivity.setResultAndFinish(((AuthInfo) chooseAccountTypeActivity.mAuthenticatorInfosToDisplay.get(i)).desc.type);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setResultAndFinish(String str) {
        Bundle bundle = new Bundle();
        bundle.putString("accountType", str);
        setResult(-1, new Intent().putExtras(bundle));
        if (Log.isLoggable(TAG, 2)) {
            Log.v(TAG, "ChooseAccountTypeActivity.setResultAndFinish: selected account type " + str);
        }
        finish();
    }

    private void buildTypeToAuthDescriptionMap() {
        Drawable drawable;
        for (AuthenticatorDescription authenticatorDescription : AccountManager.get(this).getAuthenticatorTypes()) {
            String string = null;
            try {
                Context contextCreatePackageContext = createPackageContext(authenticatorDescription.packageName, 0);
                drawable = contextCreatePackageContext.getDrawable(authenticatorDescription.iconId);
                try {
                    CharSequence text = contextCreatePackageContext.getResources().getText(authenticatorDescription.labelId);
                    if (text != null) {
                        string = text.toString();
                    }
                } catch (PackageManager.NameNotFoundException unused) {
                    if (Log.isLoggable(TAG, 5)) {
                        Log.w(TAG, "No icon name for account type " + authenticatorDescription.type);
                    }
                    this.mTypeToAuthenticatorInfo.put(authenticatorDescription.type, new AuthInfo(authenticatorDescription, string, drawable));
                } catch (Resources.NotFoundException unused2) {
                    if (Log.isLoggable(TAG, 5)) {
                        Log.w(TAG, "No icon resource for account type " + authenticatorDescription.type);
                    }
                    this.mTypeToAuthenticatorInfo.put(authenticatorDescription.type, new AuthInfo(authenticatorDescription, string, drawable));
                }
            } catch (PackageManager.NameNotFoundException unused3) {
                drawable = null;
            } catch (Resources.NotFoundException unused4) {
                drawable = null;
            }
            this.mTypeToAuthenticatorInfo.put(authenticatorDescription.type, new AuthInfo(authenticatorDescription, string, drawable));
        }
    }

    private static class AuthInfo {
        final AuthenticatorDescription desc;
        final Drawable drawable;
        final String name;

        AuthInfo(AuthenticatorDescription authenticatorDescription, String str, Drawable drawable) {
            this.desc = authenticatorDescription;
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

    private static class AccountArrayAdapter extends ArrayAdapter<AuthInfo> {
        private ArrayList<AuthInfo> mInfos;
        private LayoutInflater mLayoutInflater;

        public AccountArrayAdapter(Context context, int i, ArrayList<AuthInfo> arrayList) {
            super(context, i, arrayList);
            this.mInfos = arrayList;
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
            viewHolder.text.lambda$setTextAsync$0(this.mInfos.get(i).name);
            viewHolder.icon.lambda$setImageURIAsync$0(this.mInfos.get(i).drawable);
            return view;
        }
    }
}
