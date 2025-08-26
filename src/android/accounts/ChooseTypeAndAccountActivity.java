package android.accounts;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.app.admin.DevicePolicyResources;
import android.content.Intent;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.android.internal.R;
import com.google.android.collect.Sets;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public class ChooseTypeAndAccountActivity extends Activity implements AccountManagerCallback<Bundle> {
    public static final String EXTRA_ADD_ACCOUNT_AUTH_TOKEN_TYPE_STRING = "authTokenType";
    public static final String EXTRA_ADD_ACCOUNT_OPTIONS_BUNDLE = "addAccountOptions";
    public static final String EXTRA_ADD_ACCOUNT_REQUIRED_FEATURES_STRING_ARRAY = "addAccountRequiredFeatures";
    public static final String EXTRA_ALLOWABLE_ACCOUNTS_ARRAYLIST = "allowableAccounts";
    public static final String EXTRA_ALLOWABLE_ACCOUNT_TYPES_STRING_ARRAY = "allowableAccountTypes";

    @Deprecated
    public static final String EXTRA_ALWAYS_PROMPT_FOR_ACCOUNT = "alwaysPromptForAccount";
    public static final String EXTRA_DESCRIPTION_TEXT_OVERRIDE = "descriptionTextOverride";
    public static final String EXTRA_SELECTED_ACCOUNT = "selectedAccount";
    private static final String KEY_INSTANCE_STATE_ACCOUNTS_LIST = "accountsList";
    private static final String KEY_INSTANCE_STATE_EXISTING_ACCOUNTS = "existingAccounts";
    private static final String KEY_INSTANCE_STATE_PENDING_REQUEST = "pendingRequest";
    private static final String KEY_INSTANCE_STATE_SELECTED_ACCOUNT_NAME = "selectedAccountName";
    private static final String KEY_INSTANCE_STATE_SELECTED_ADD_ACCOUNT = "selectedAddAccount";
    private static final String KEY_INSTANCE_STATE_VISIBILITY_LIST = "visibilityList";
    public static final int REQUEST_ADD_ACCOUNT = 2;
    public static final int REQUEST_CHOOSE_TYPE = 1;
    public static final int REQUEST_NULL = 0;
    private static final int SELECTED_ITEM_NONE = -1;
    private static final String TAG = "AccountChooser";
    private LinkedHashMap<Account, Integer> mAccounts;
    private String mCallingPackage;
    private int mCallingUid;
    private String mDescriptionOverride;
    private boolean mDisallowAddAccounts;
    private boolean mDontShowPicker;
    private Button mOkButton;
    private ArrayList<Account> mPossiblyVisibleAccounts;
    private int mSelectedItemIndex;
    private Set<Account> mSetOfAllowableAccounts;
    private Set<String> mSetOfRelevantAccountTypes;
    private String mSelectedAccountName = null;
    private boolean mSelectedAddNewAccount = false;
    private int mPendingRequest = 0;
    private Parcelable[] mExistingAccounts = null;

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        if (Log.isLoggable(TAG, 2)) {
            Log.v(TAG, "ChooseTypeAndAccountActivity.onCreate(savedInstanceState=" + bundle + NavigationBarInflaterView.KEY_CODE_END);
        }
        getWindow().addSystemFlags(524288);
        this.mCallingUid = getLaunchedFromUid();
        String launchedFromPackage = getLaunchedFromPackage();
        this.mCallingPackage = launchedFromPackage;
        if (this.mCallingUid != 0 && launchedFromPackage != null) {
            this.mDisallowAddAccounts = UserManager.get(this).getUserRestrictions(new UserHandle(UserHandle.getUserId(this.mCallingUid))).getBoolean(UserManager.DISALLOW_MODIFY_ACCOUNTS, false);
        }
        Intent intent = getIntent();
        this.mSetOfAllowableAccounts = getAllowableAccountSet(intent);
        this.mSetOfRelevantAccountTypes = getReleventAccountTypes(intent);
        this.mDescriptionOverride = intent.getStringExtra(EXTRA_DESCRIPTION_TEXT_OVERRIDE);
        if (bundle != null) {
            this.mPendingRequest = bundle.getInt(KEY_INSTANCE_STATE_PENDING_REQUEST);
            this.mExistingAccounts = bundle.getParcelableArray(KEY_INSTANCE_STATE_EXISTING_ACCOUNTS);
            this.mSelectedAccountName = bundle.getString(KEY_INSTANCE_STATE_SELECTED_ACCOUNT_NAME);
            this.mSelectedAddNewAccount = bundle.getBoolean(KEY_INSTANCE_STATE_SELECTED_ADD_ACCOUNT, false);
            Parcelable[] parcelableArray = bundle.getParcelableArray(KEY_INSTANCE_STATE_ACCOUNTS_LIST);
            ArrayList<Integer> integerArrayList = bundle.getIntegerArrayList(KEY_INSTANCE_STATE_VISIBILITY_LIST);
            this.mAccounts = new LinkedHashMap<>();
            for (int i = 0; i < parcelableArray.length; i++) {
                this.mAccounts.put((Account) parcelableArray[i], integerArrayList.get(i));
            }
        } else {
            this.mPendingRequest = 0;
            this.mExistingAccounts = null;
            Account account = (Account) intent.getParcelableExtra(EXTRA_SELECTED_ACCOUNT, Account.class);
            if (account != null) {
                this.mSelectedAccountName = account.name;
            }
            this.mAccounts = getAcceptableAccountChoices(AccountManager.get(this));
        }
        this.mPossiblyVisibleAccounts = new ArrayList<>(this.mAccounts.size());
        for (Map.Entry<Account, Integer> entry : this.mAccounts.entrySet()) {
            if (3 != entry.getValue().intValue()) {
                this.mPossiblyVisibleAccounts.add(entry.getKey());
            }
        }
        if (this.mPossiblyVisibleAccounts.isEmpty() && this.mDisallowAddAccounts) {
            requestWindowFeature(1);
            setContentView(R.layout.app_not_authorized);
            ((TextView) findViewById(R.id.description)).lambda$setTextAsync$0(((DevicePolicyManager) getSystemService(DevicePolicyManager.class)).getResources().getString(DevicePolicyResources.Strings.Core.CANT_ADD_ACCOUNT_MESSAGE, new Supplier() { // from class: android.accounts.ChooseTypeAndAccountActivity$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.lambda$onCreate$0();
                }
            }));
            this.mDontShowPicker = true;
        }
        if (this.mDontShowPicker) {
            super.onCreate(bundle);
            return;
        }
        if (this.mPendingRequest == 0 && this.mPossiblyVisibleAccounts.isEmpty()) {
            setNonLabelThemeAndCallSuperCreate(bundle);
            if (this.mSetOfRelevantAccountTypes.size() == 1) {
                runAddAccountForAuthenticator(this.mSetOfRelevantAccountTypes.iterator().next());
            } else {
                startChooseAccountTypeActivity();
            }
        }
        String[] listOfDisplayableOptions = getListOfDisplayableOptions(this.mPossiblyVisibleAccounts);
        this.mSelectedItemIndex = getItemIndexToSelect(this.mPossiblyVisibleAccounts, this.mSelectedAccountName, this.mSelectedAddNewAccount);
        super.onCreate(bundle);
        setContentView(R.layout.choose_type_and_account);
        overrideDescriptionIfSupplied(this.mDescriptionOverride);
        populateUIAccountList(listOfDisplayableOptions);
        Button button = (Button) findViewById(16908314);
        this.mOkButton = button;
        button.setEnabled(this.mSelectedItemIndex != -1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String lambda$onCreate$0() {
        return getString(R.string.error_message_change_not_allowed);
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        if (Log.isLoggable(TAG, 2)) {
            Log.v(TAG, "ChooseTypeAndAccountActivity.onDestroy()");
        }
        super.onDestroy();
    }

    @Override // android.app.Activity
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(KEY_INSTANCE_STATE_PENDING_REQUEST, this.mPendingRequest);
        if (this.mPendingRequest == 2) {
            bundle.putParcelableArray(KEY_INSTANCE_STATE_EXISTING_ACCOUNTS, this.mExistingAccounts);
        }
        int i = this.mSelectedItemIndex;
        int i2 = 0;
        if (i != -1) {
            if (i == this.mPossiblyVisibleAccounts.size()) {
                bundle.putBoolean(KEY_INSTANCE_STATE_SELECTED_ADD_ACCOUNT, true);
            } else {
                bundle.putBoolean(KEY_INSTANCE_STATE_SELECTED_ADD_ACCOUNT, false);
                bundle.putString(KEY_INSTANCE_STATE_SELECTED_ACCOUNT_NAME, this.mPossiblyVisibleAccounts.get(this.mSelectedItemIndex).name);
            }
        }
        Parcelable[] parcelableArr = new Parcelable[this.mAccounts.size()];
        ArrayList<Integer> arrayList = new ArrayList<>(this.mAccounts.size());
        for (Map.Entry<Account, Integer> entry : this.mAccounts.entrySet()) {
            parcelableArr[i2] = entry.getKey();
            arrayList.add(entry.getValue());
            i2++;
        }
        bundle.putParcelableArray(KEY_INSTANCE_STATE_ACCOUNTS_LIST, parcelableArr);
        bundle.putIntegerArrayList(KEY_INSTANCE_STATE_VISIBILITY_LIST, arrayList);
    }

    public void onCancelButtonClicked(View view) {
        onBackPressed();
    }

    public void onOkButtonClicked(View view) {
        if (this.mSelectedItemIndex == this.mPossiblyVisibleAccounts.size()) {
            startChooseAccountTypeActivity();
            return;
        }
        int i = this.mSelectedItemIndex;
        if (i != -1) {
            onAccountSelected(this.mPossiblyVisibleAccounts.get(i));
        }
    }

    @Override // android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        String stringExtra;
        String stringExtra2;
        String stringExtra3;
        if (Log.isLoggable(TAG, 2)) {
            if (intent != null && intent.getExtras() != null) {
                intent.getExtras().keySet();
            }
            if (intent != null) {
                intent.getExtras();
            }
            Log.v(TAG, "ChooseTypeAndAccountActivity.onActivityResult(reqCode=" + i + ", resCode=" + i2 + NavigationBarInflaterView.KEY_CODE_END);
        }
        this.mPendingRequest = 0;
        if (i2 == 0) {
            if (this.mPossiblyVisibleAccounts.isEmpty()) {
                setResult(0);
                finish();
                return;
            }
            return;
        }
        if (i2 == -1) {
            if (i == 1) {
                if (intent == null || (stringExtra3 = intent.getStringExtra("accountType")) == null) {
                    Log.d(TAG, "ChooseTypeAndAccountActivity.onActivityResult: unable to find account type, pretending the request was canceled");
                } else {
                    runAddAccountForAuthenticator(stringExtra3);
                    return;
                }
            } else if (i == 2) {
                if (intent != null) {
                    stringExtra = intent.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    stringExtra2 = intent.getStringExtra("accountType");
                } else {
                    stringExtra = null;
                    stringExtra2 = null;
                }
                if (stringExtra == null || stringExtra2 == null) {
                    Account[] accountsForPackage = AccountManager.get(this).getAccountsForPackage(this.mCallingPackage, this.mCallingUid);
                    HashSet hashSet = new HashSet();
                    for (Parcelable parcelable : this.mExistingAccounts) {
                        hashSet.add((Account) parcelable);
                    }
                    int length = accountsForPackage.length;
                    int i3 = 0;
                    while (true) {
                        if (i3 >= length) {
                            break;
                        }
                        Account account = accountsForPackage[i3];
                        if (!hashSet.contains(account)) {
                            stringExtra = account.name;
                            stringExtra2 = account.type;
                            break;
                        }
                        i3++;
                    }
                }
                if (stringExtra != null || stringExtra2 != null) {
                    setResultAndFinish(stringExtra, stringExtra2);
                    return;
                }
            }
            Log.d(TAG, "ChooseTypeAndAccountActivity.onActivityResult: unable to find added account, pretending the request was canceled");
        }
        if (Log.isLoggable(TAG, 2)) {
            Log.v(TAG, "ChooseTypeAndAccountActivity.onActivityResult: canceled");
        }
        setResult(0);
        finish();
    }

    protected void runAddAccountForAuthenticator(String str) {
        if (Log.isLoggable(TAG, 2)) {
            Log.v(TAG, "runAddAccountForAuthenticator: " + str);
        }
        Bundle bundleExtra = getIntent().getBundleExtra(EXTRA_ADD_ACCOUNT_OPTIONS_BUNDLE);
        AccountManager.get(this).addAccount(str, getIntent().getStringExtra("authTokenType"), getIntent().getStringArrayExtra(EXTRA_ADD_ACCOUNT_REQUIRED_FEATURES_STRING_ARRAY), bundleExtra, null, this, null);
    }

    @Override // android.accounts.AccountManagerCallback
    public void run(AccountManagerFuture<Bundle> accountManagerFuture) {
        try {
            Intent intent = (Intent) accountManagerFuture.getResult().getParcelable("intent", Intent.class);
            if (intent != null) {
                this.mPendingRequest = 2;
                this.mExistingAccounts = AccountManager.get(this).getAccountsForPackage(this.mCallingPackage, this.mCallingUid);
                intent.setFlags(intent.getFlags() & (-268435457));
                startActivityForResult(new Intent(intent), 2);
                return;
            }
        } catch (AuthenticatorException | IOException unused) {
        } catch (OperationCanceledException unused2) {
            setResult(0);
            finish();
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString(AccountManager.KEY_ERROR_MESSAGE, "error communicating with server");
        setResult(-1, new Intent().putExtras(bundle));
        finish();
    }

    private void setNonLabelThemeAndCallSuperCreate(Bundle bundle) {
        setTheme(16974132);
        super.onCreate(bundle);
    }

    private void onAccountSelected(Account account) {
        Log.d(TAG, "selected account " + account.toSafeString());
        setResultAndFinish(account.name, account.type);
    }

    private void setResultAndFinish(String str, String str2) {
        Account account = new Account(str, str2);
        int accountVisibility = AccountManager.get(this).getAccountVisibility(account, this.mCallingPackage);
        Integer numValueOf = Integer.valueOf(accountVisibility);
        if (numValueOf != null) {
            numValueOf.getClass();
            if (accountVisibility == 4) {
                AccountManager.get(this).setAccountVisibility(account, this.mCallingPackage, 2);
            }
        }
        if (numValueOf != null) {
            numValueOf.getClass();
            if (accountVisibility == 3) {
                setResult(0);
                finish();
                return;
            }
        }
        Bundle bundle = new Bundle();
        bundle.putString(AccountManager.KEY_ACCOUNT_NAME, str);
        bundle.putString("accountType", str2);
        setResult(-1, new Intent().putExtras(bundle));
        if (Log.isLoggable(TAG, 2)) {
            Log.v(TAG, "ChooseTypeAndAccountActivity.setResultAndFinish: selected account " + account.toSafeString());
        }
        finish();
    }

    private void startChooseAccountTypeActivity() {
        if (Log.isLoggable(TAG, 2)) {
            Log.v(TAG, "ChooseAccountTypeActivity.startChooseAccountTypeActivity()");
        }
        Intent intent = new Intent(this, (Class<?>) ChooseAccountTypeActivity.class);
        intent.setFlags(524288);
        intent.putExtra(EXTRA_ALLOWABLE_ACCOUNT_TYPES_STRING_ARRAY, getIntent().getStringArrayExtra(EXTRA_ALLOWABLE_ACCOUNT_TYPES_STRING_ARRAY));
        intent.putExtra(EXTRA_ADD_ACCOUNT_OPTIONS_BUNDLE, getIntent().getBundleExtra(EXTRA_ADD_ACCOUNT_OPTIONS_BUNDLE));
        intent.putExtra(EXTRA_ADD_ACCOUNT_REQUIRED_FEATURES_STRING_ARRAY, getIntent().getStringArrayExtra(EXTRA_ADD_ACCOUNT_REQUIRED_FEATURES_STRING_ARRAY));
        intent.putExtra("authTokenType", getIntent().getStringExtra("authTokenType"));
        startActivityForResult(intent, 1);
        this.mPendingRequest = 1;
    }

    private int getItemIndexToSelect(ArrayList<Account> arrayList, String str, boolean z) {
        if (z) {
            return arrayList.size();
        }
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).name.equals(str)) {
                return i;
            }
        }
        return -1;
    }

    private String[] getListOfDisplayableOptions(ArrayList<Account> arrayList) {
        String[] strArr = new String[arrayList.size() + (!this.mDisallowAddAccounts ? 1 : 0)];
        for (int i = 0; i < arrayList.size(); i++) {
            strArr[i] = arrayList.get(i).name;
        }
        if (!this.mDisallowAddAccounts) {
            strArr[arrayList.size()] = getResources().getString(R.string.add_account_button_label);
        }
        return strArr;
    }

    private LinkedHashMap<Account, Integer> getAcceptableAccountChoices(AccountManager accountManager) {
        Set<String> set;
        Map<Account, Integer> accountsAndVisibilityForPackage = accountManager.getAccountsAndVisibilityForPackage(this.mCallingPackage, null);
        Account[] accounts = accountManager.getAccounts();
        LinkedHashMap<Account, Integer> linkedHashMap = new LinkedHashMap<>(accountsAndVisibilityForPackage.size());
        for (Account account : accounts) {
            Set<Account> set2 = this.mSetOfAllowableAccounts;
            if ((set2 == null || set2.contains(account)) && (((set = this.mSetOfRelevantAccountTypes) == null || set.contains(account.type)) && accountsAndVisibilityForPackage.get(account) != null)) {
                linkedHashMap.put(account, accountsAndVisibilityForPackage.get(account));
            }
        }
        return linkedHashMap;
    }

    private Set<String> getReleventAccountTypes(Intent intent) {
        String[] stringArrayExtra = intent.getStringArrayExtra(EXTRA_ALLOWABLE_ACCOUNT_TYPES_STRING_ARRAY);
        AuthenticatorDescription[] authenticatorTypes = AccountManager.get(this).getAuthenticatorTypes();
        HashSet hashSet = new HashSet(authenticatorTypes.length);
        for (AuthenticatorDescription authenticatorDescription : authenticatorTypes) {
            hashSet.add(authenticatorDescription.type);
        }
        if (stringArrayExtra == null) {
            return hashSet;
        }
        HashSet hashSetNewHashSet = Sets.newHashSet(stringArrayExtra);
        hashSetNewHashSet.retainAll(hashSet);
        return hashSetNewHashSet;
    }

    private Set<Account> getAllowableAccountSet(Intent intent) {
        ArrayList parcelableArrayListExtra = intent.getParcelableArrayListExtra(EXTRA_ALLOWABLE_ACCOUNTS_ARRAYLIST);
        if (parcelableArrayListExtra == null) {
            return null;
        }
        HashSet hashSet = new HashSet(parcelableArrayListExtra.size());
        Iterator it = parcelableArrayListExtra.iterator();
        while (it.hasNext()) {
            hashSet.add((Account) ((Parcelable) it.next()));
        }
        return hashSet;
    }

    private void overrideDescriptionIfSupplied(String str) {
        TextView textView = (TextView) findViewById(R.id.description);
        if (!TextUtils.isEmpty(str)) {
            textView.lambda$setTextAsync$0(str);
        } else {
            textView.setVisibility(8);
        }
    }

    private final void populateUIAccountList(String[] strArr) {
        ListView listView = (ListView) findViewById(16908298);
        listView.setAdapter((ListAdapter) new ArrayAdapter(this, 17367055, strArr));
        listView.setChoiceMode(1);
        listView.setItemsCanFocus(false);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: android.accounts.ChooseTypeAndAccountActivity.1
            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                ChooseTypeAndAccountActivity.this.mSelectedItemIndex = i;
                ChooseTypeAndAccountActivity.this.mOkButton.setEnabled(true);
            }
        });
        int i = this.mSelectedItemIndex;
        if (i != -1) {
            listView.setItemChecked(i, true);
            if (Log.isLoggable(TAG, 2)) {
                Log.v(TAG, "List item " + this.mSelectedItemIndex + " should be selected");
            }
        }
    }
}
