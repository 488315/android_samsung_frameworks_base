package android.accounts;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.app.admin.DevicePolicyResources;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.p009os.Bundle;
import android.p009os.Parcelable;
import android.p009os.UserHandle;
import android.p009os.UserManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.android.internal.C4337R;
import com.google.android.collect.Sets;
import com.samsung.android.util.SemViewUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

/* loaded from: classes.dex */
public class ChooseTypeAndAccountActivity extends Activity
    implements AccountManagerCallback<Bundle> {
  public static final String EXTRA_ADD_ACCOUNT_AUTH_TOKEN_TYPE_STRING = "authTokenType";
  public static final String EXTRA_ADD_ACCOUNT_OPTIONS_BUNDLE = "addAccountOptions";
  public static final String EXTRA_ADD_ACCOUNT_REQUIRED_FEATURES_STRING_ARRAY =
      "addAccountRequiredFeatures";
  public static final String EXTRA_ALLOWABLE_ACCOUNTS_ARRAYLIST = "allowableAccounts";
  public static final String EXTRA_ALLOWABLE_ACCOUNT_TYPES_STRING_ARRAY = "allowableAccountTypes";

  @Deprecated public static final String EXTRA_ALWAYS_PROMPT_FOR_ACCOUNT = "alwaysPromptForAccount";
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
  private Button mCancelButton;
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
  public void onCreate(Bundle savedInstanceState) {
    int i;
    int i2;
    int buttonTextColor;
    if (Log.isLoggable(TAG, 2)) {
      Log.m100v(
          TAG,
          "ChooseTypeAndAccountActivity.onCreate(savedInstanceState="
              + savedInstanceState
              + NavigationBarInflaterView.KEY_CODE_END);
    }
    getWindow().addSystemFlags(524288);
    this.mCallingUid = getLaunchedFromUid();
    String launchedFromPackage = getLaunchedFromPackage();
    this.mCallingPackage = launchedFromPackage;
    if (this.mCallingUid != 0 && launchedFromPackage != null) {
      Bundle restrictions =
          UserManager.get(this)
              .getUserRestrictions(new UserHandle(UserHandle.getUserId(this.mCallingUid)));
      this.mDisallowAddAccounts =
          restrictions.getBoolean(UserManager.DISALLOW_MODIFY_ACCOUNTS, false);
    }
    Intent intent = getIntent();
    this.mSetOfAllowableAccounts = getAllowableAccountSet(intent);
    this.mSetOfRelevantAccountTypes = getReleventAccountTypes(intent);
    this.mDescriptionOverride = intent.getStringExtra(EXTRA_DESCRIPTION_TEXT_OVERRIDE);
    if (savedInstanceState != null) {
      this.mPendingRequest = savedInstanceState.getInt(KEY_INSTANCE_STATE_PENDING_REQUEST);
      this.mExistingAccounts =
          savedInstanceState.getParcelableArray(KEY_INSTANCE_STATE_EXISTING_ACCOUNTS);
      this.mSelectedAccountName =
          savedInstanceState.getString(KEY_INSTANCE_STATE_SELECTED_ACCOUNT_NAME);
      this.mSelectedAddNewAccount =
          savedInstanceState.getBoolean(KEY_INSTANCE_STATE_SELECTED_ADD_ACCOUNT, false);
      Parcelable[] accounts =
          savedInstanceState.getParcelableArray(KEY_INSTANCE_STATE_ACCOUNTS_LIST);
      ArrayList<Integer> visibility =
          savedInstanceState.getIntegerArrayList(KEY_INSTANCE_STATE_VISIBILITY_LIST);
      this.mAccounts = new LinkedHashMap<>();
      for (int i3 = 0; i3 < accounts.length; i3++) {
        this.mAccounts.put((Account) accounts[i3], visibility.get(i3));
      }
    } else {
      this.mPendingRequest = 0;
      this.mExistingAccounts = null;
      Account selectedAccount =
          (Account) intent.getParcelableExtra(EXTRA_SELECTED_ACCOUNT, Account.class);
      if (selectedAccount != null) {
        this.mSelectedAccountName = selectedAccount.name;
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
      setContentView(C4337R.layout.app_not_authorized);
      TextView view = (TextView) findViewById(C4337R.id.description);
      String text =
          ((DevicePolicyManager) getSystemService(DevicePolicyManager.class))
              .getResources()
              .getString(
                  DevicePolicyResources.Strings.Core.CANT_ADD_ACCOUNT_MESSAGE,
                  new Supplier() { // from class:
                                   // android.accounts.ChooseTypeAndAccountActivity$$ExternalSyntheticLambda0
                    @Override // java.util.function.Supplier
                    public final Object get() {
                      String lambda$onCreate$0;
                      lambda$onCreate$0 = ChooseTypeAndAccountActivity.this.lambda$onCreate$0();
                      return lambda$onCreate$0;
                    }
                  });
      view.setText(text);
      this.mDontShowPicker = true;
    }
    if (this.mDontShowPicker) {
      super.onCreate(savedInstanceState);
      return;
    }
    if (this.mPendingRequest == 0 && this.mPossiblyVisibleAccounts.isEmpty()) {
      setNonLabelThemeAndCallSuperCreate(savedInstanceState);
      if (this.mSetOfRelevantAccountTypes.size() == 1) {
        runAddAccountForAuthenticator(this.mSetOfRelevantAccountTypes.iterator().next());
      } else {
        startChooseAccountTypeActivity();
      }
    }
    String[] listItems = getListOfDisplayableOptions(this.mPossiblyVisibleAccounts);
    this.mSelectedItemIndex =
        getItemIndexToSelect(
            this.mPossiblyVisibleAccounts, this.mSelectedAccountName, this.mSelectedAddNewAccount);
    super.onCreate(savedInstanceState);
    setContentView(C4337R.layout.choose_type_and_account);
    overrideDescriptionIfSupplied(this.mDescriptionOverride);
    populateUIAccountList(listItems);
    Button button = (Button) findViewById(16908314);
    this.mOkButton = button;
    button.setEnabled(this.mSelectedItemIndex != -1);
    this.mCancelButton = (Button) findViewById(16908313);
    if (SemViewUtils.isOpenThemeApplied(this)) {
      TypedValue typedValue = new TypedValue();
      Context wrapper = new ContextThemeWrapper(this, 16974120);
      wrapper.getTheme().resolveAttribute(16843828, typedValue, true);
      if (typedValue.resourceId != 0) {
        buttonTextColor = wrapper.getResources().getColor(typedValue.resourceId);
        Log.m98i(TAG, "onCreate: #1 buttonTextColor=0x" + Integer.toHexString(buttonTextColor));
      } else {
        buttonTextColor = typedValue.data;
        Log.m98i(TAG, "onCreate: #2 buttonTextColor=0x" + Integer.toHexString(buttonTextColor));
      }
      this.mOkButton.setTextColor(buttonTextColor);
      this.mCancelButton.setTextColor(buttonTextColor);
    } else if (getResources().getConfiguration().semDesktopModeEnabled != 1
        && Settings.System.getInt(getContentResolver(), "wallpapertheme_state", 0) != 0) {
      Resources res = getResources();
      boolean isNightMode = (res.getConfiguration().uiMode & 48) == 32;
      if (isNightMode) {
        i = C4337R.color.tw_dialog_title_text_color_material_dark;
      } else {
        i = C4337R.color.tw_dialog_title_text_color_material_light;
      }
      int titleColor = res.getColor(i, null);
      View title = getWindow().getDecorView().findViewById(16908310);
      if (title instanceof TextView) {
        ((TextView) title).setTextColor(titleColor);
      }
      if (isNightMode) {
        i2 = C4337R.color.tw_dialog_button_text_color_material_dark;
      } else {
        i2 = C4337R.color.tw_dialog_button_text_color_material_light;
      }
      int buttonTextColor2 = res.getColor(i2, null);
      this.mOkButton.setTextColor(buttonTextColor2);
      this.mCancelButton.setTextColor(buttonTextColor2);
      Log.m98i(
          TAG,
          "onCreate: colorPalette=true isNightMode="
              + isNightMode
              + " titleColor=0x"
              + Integer.toHexString(titleColor)
              + " buttonTextColor=0x"
              + Integer.toHexString(buttonTextColor2));
    }
    String[] themes = getTheme().getTheme();
    StringBuilder sb = new StringBuilder();
    for (String str : themes) {
      sb.append(str);
      sb.append(" ");
    }
    Log.m98i(TAG, "Theme=" + ((Object) sb));
    ColorStateList okTextColors = this.mOkButton.getTextColors();
    Log.m98i(
        TAG,
        "Ok textColors="
            + okTextColors
            + " defaultColor=0x"
            + Integer.toHexString(okTextColors.getDefaultColor()));
    ColorStateList cancelTextColors = this.mCancelButton.getTextColors();
    Log.m98i(
        TAG,
        "Cancel textColors="
            + cancelTextColors
            + " defaultColor=0x"
            + Integer.toHexString(cancelTextColors.getDefaultColor()));
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ String lambda$onCreate$0() {
    return getString(C4337R.string.error_message_change_not_allowed);
  }

  @Override // android.app.Activity
  protected void onDestroy() {
    if (Log.isLoggable(TAG, 2)) {
      Log.m100v(TAG, "ChooseTypeAndAccountActivity.onDestroy()");
    }
    super.onDestroy();
  }

  @Override // android.app.Activity
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putInt(KEY_INSTANCE_STATE_PENDING_REQUEST, this.mPendingRequest);
    if (this.mPendingRequest == 2) {
      outState.putParcelableArray(KEY_INSTANCE_STATE_EXISTING_ACCOUNTS, this.mExistingAccounts);
    }
    int i = this.mSelectedItemIndex;
    if (i != -1) {
      if (i == this.mPossiblyVisibleAccounts.size()) {
        outState.putBoolean(KEY_INSTANCE_STATE_SELECTED_ADD_ACCOUNT, true);
      } else {
        outState.putBoolean(KEY_INSTANCE_STATE_SELECTED_ADD_ACCOUNT, false);
        outState.putString(
            KEY_INSTANCE_STATE_SELECTED_ACCOUNT_NAME,
            this.mPossiblyVisibleAccounts.get(this.mSelectedItemIndex).name);
      }
    }
    Parcelable[] accounts = new Parcelable[this.mAccounts.size()];
    ArrayList<Integer> visibility = new ArrayList<>(this.mAccounts.size());
    int i2 = 0;
    for (Map.Entry<Account, Integer> e : this.mAccounts.entrySet()) {
      accounts[i2] = e.getKey();
      visibility.add(e.getValue());
      i2++;
    }
    outState.putParcelableArray(KEY_INSTANCE_STATE_ACCOUNTS_LIST, accounts);
    outState.putIntegerArrayList(KEY_INSTANCE_STATE_VISIBILITY_LIST, visibility);
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
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    String accountType;
    if (Log.isLoggable(TAG, 2)) {
      if (data != null && data.getExtras() != null) {
        data.getExtras().keySet();
      }
      if (data != null) {
        data.getExtras();
      }
      Log.m100v(
          TAG,
          "ChooseTypeAndAccountActivity.onActivityResult(reqCode="
              + requestCode
              + ", resCode="
              + resultCode
              + NavigationBarInflaterView.KEY_CODE_END);
    }
    this.mPendingRequest = 0;
    if (resultCode == 0) {
      if (this.mPossiblyVisibleAccounts.isEmpty()) {
        setResult(0);
        finish();
        return;
      }
      return;
    }
    if (resultCode == -1) {
      if (requestCode == 1) {
        if (data == null || (accountType = data.getStringExtra("accountType")) == null) {
          Log.m94d(
              TAG,
              "ChooseTypeAndAccountActivity.onActivityResult: unable to find account type,"
                  + " pretending the request was canceled");
        } else {
          runAddAccountForAuthenticator(accountType);
          return;
        }
      } else if (requestCode == 2) {
        String accountName = null;
        String accountType2 = null;
        if (data != null) {
          accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
          accountType2 = data.getStringExtra("accountType");
        }
        if (accountName == null || accountType2 == null) {
          Account[] currentAccounts =
              AccountManager.get(this)
                  .getAccountsForPackage(this.mCallingPackage, this.mCallingUid);
          Set<Account> preExistingAccounts = new HashSet<>();
          for (Parcelable accountParcel : this.mExistingAccounts) {
            preExistingAccounts.add((Account) accountParcel);
          }
          int length = currentAccounts.length;
          int i = 0;
          while (true) {
            if (i >= length) {
              break;
            }
            Account account = currentAccounts[i];
            if (preExistingAccounts.contains(account)) {
              i++;
            } else {
              accountName = account.name;
              accountType2 = account.type;
              break;
            }
          }
        }
        if (accountName != null || accountType2 != null) {
          setResultAndFinish(accountName, accountType2);
          return;
        }
      }
      Log.m94d(
          TAG,
          "ChooseTypeAndAccountActivity.onActivityResult: unable to find added account, pretending"
              + " the request was canceled");
    }
    if (Log.isLoggable(TAG, 2)) {
      Log.m100v(TAG, "ChooseTypeAndAccountActivity.onActivityResult: canceled");
    }
    setResult(0);
    finish();
  }

  protected void runAddAccountForAuthenticator(String type) {
    if (Log.isLoggable(TAG, 2)) {
      Log.m100v(TAG, "runAddAccountForAuthenticator: " + type);
    }
    Bundle options = getIntent().getBundleExtra(EXTRA_ADD_ACCOUNT_OPTIONS_BUNDLE);
    String[] requiredFeatures =
        getIntent().getStringArrayExtra(EXTRA_ADD_ACCOUNT_REQUIRED_FEATURES_STRING_ARRAY);
    String authTokenType = getIntent().getStringExtra("authTokenType");
    AccountManager.get(this)
        .addAccount(type, authTokenType, requiredFeatures, options, null, this, null);
  }

  @Override // android.accounts.AccountManagerCallback
  public void run(AccountManagerFuture<Bundle> accountManagerFuture) {
    try {
      Bundle accountManagerResult = accountManagerFuture.getResult();
      Intent intent = (Intent) accountManagerResult.getParcelable("intent", Intent.class);
      if (intent != null) {
        this.mPendingRequest = 2;
        this.mExistingAccounts =
            AccountManager.get(this).getAccountsForPackage(this.mCallingPackage, this.mCallingUid);
        intent.setFlags(intent.getFlags() & (-268435457));
        startActivityForResult(new Intent(intent), 2);
        return;
      }
    } catch (AuthenticatorException e) {
    } catch (OperationCanceledException e2) {
      setResult(0);
      finish();
      return;
    } catch (IOException e3) {
    }
    Bundle bundle = new Bundle();
    bundle.putString(AccountManager.KEY_ERROR_MESSAGE, "error communicating with server");
    setResult(-1, new Intent().putExtras(bundle));
    finish();
  }

  private void setNonLabelThemeAndCallSuperCreate(Bundle savedInstanceState) {
    setTheme(16974132);
    super.onCreate(savedInstanceState);
  }

  private void onAccountSelected(Account account) {
    Log.m94d(TAG, "selected account " + account.toSafeString());
    setResultAndFinish(account.name, account.type);
  }

  private void setResultAndFinish(String accountName, String accountType) {
    Account account = new Account(accountName, accountType);
    Integer oldVisibility =
        Integer.valueOf(
            AccountManager.get(this).getAccountVisibility(account, this.mCallingPackage));
    if (oldVisibility != null && oldVisibility.intValue() == 4) {
      AccountManager.get(this).setAccountVisibility(account, this.mCallingPackage, 2);
    }
    if (oldVisibility != null && oldVisibility.intValue() == 3) {
      setResult(0);
      finish();
      return;
    }
    Bundle bundle = new Bundle();
    bundle.putString(AccountManager.KEY_ACCOUNT_NAME, accountName);
    bundle.putString("accountType", accountType);
    setResult(-1, new Intent().putExtras(bundle));
    if (Log.isLoggable(TAG, 2)) {
      Log.m100v(
          TAG,
          "ChooseTypeAndAccountActivity.setResultAndFinish: selected account "
              + account.toSafeString());
    }
    finish();
  }

  private void startChooseAccountTypeActivity() {
    if (Log.isLoggable(TAG, 2)) {
      Log.m100v(TAG, "ChooseAccountTypeActivity.startChooseAccountTypeActivity()");
    }
    Intent intent = new Intent(this, (Class<?>) ChooseAccountTypeActivity.class);
    intent.setFlags(524288);
    intent.putExtra(
        EXTRA_ALLOWABLE_ACCOUNT_TYPES_STRING_ARRAY,
        getIntent().getStringArrayExtra(EXTRA_ALLOWABLE_ACCOUNT_TYPES_STRING_ARRAY));
    intent.putExtra(
        EXTRA_ADD_ACCOUNT_OPTIONS_BUNDLE,
        getIntent().getBundleExtra(EXTRA_ADD_ACCOUNT_OPTIONS_BUNDLE));
    intent.putExtra(
        EXTRA_ADD_ACCOUNT_REQUIRED_FEATURES_STRING_ARRAY,
        getIntent().getStringArrayExtra(EXTRA_ADD_ACCOUNT_REQUIRED_FEATURES_STRING_ARRAY));
    intent.putExtra("authTokenType", getIntent().getStringExtra("authTokenType"));
    startActivityForResult(intent, 1);
    this.mPendingRequest = 1;
  }

  private int getItemIndexToSelect(
      ArrayList<Account> accounts, String selectedAccountName, boolean selectedAddNewAccount) {
    if (selectedAddNewAccount) {
      return accounts.size();
    }
    for (int i = 0; i < accounts.size(); i++) {
      if (accounts.get(i).name.equals(selectedAccountName)) {
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
      strArr[arrayList.size()] = getResources().getString(C4337R.string.add_account_button_label);
    }
    return strArr;
  }

  private LinkedHashMap<Account, Integer> getAcceptableAccountChoices(
      AccountManager accountManager) {
    Set<String> set;
    Map<Account, Integer> accountsAndVisibilityForCaller =
        accountManager.getAccountsAndVisibilityForPackage(this.mCallingPackage, null);
    Account[] allAccounts = accountManager.getAccounts();
    LinkedHashMap<Account, Integer> accountsToPopulate =
        new LinkedHashMap<>(accountsAndVisibilityForCaller.size());
    for (Account account : allAccounts) {
      Set<Account> set2 = this.mSetOfAllowableAccounts;
      if ((set2 == null || set2.contains(account))
          && (((set = this.mSetOfRelevantAccountTypes) == null || set.contains(account.type))
              && accountsAndVisibilityForCaller.get(account) != null)) {
        accountsToPopulate.put(account, accountsAndVisibilityForCaller.get(account));
      }
    }
    return accountsToPopulate;
  }

  private Set<String> getReleventAccountTypes(Intent intent) {
    String[] allowedAccountTypes =
        intent.getStringArrayExtra(EXTRA_ALLOWABLE_ACCOUNT_TYPES_STRING_ARRAY);
    AuthenticatorDescription[] descs = AccountManager.get(this).getAuthenticatorTypes();
    Set<String> supportedAccountTypes = new HashSet<>(descs.length);
    for (AuthenticatorDescription desc : descs) {
      supportedAccountTypes.add(desc.type);
    }
    if (allowedAccountTypes != null) {
      Set<String> setOfRelevantAccountTypes = Sets.newHashSet(allowedAccountTypes);
      setOfRelevantAccountTypes.retainAll(supportedAccountTypes);
      return setOfRelevantAccountTypes;
    }
    return supportedAccountTypes;
  }

  private Set<Account> getAllowableAccountSet(Intent intent) {
    Set<Account> setOfAllowableAccounts = null;
    ArrayList<Parcelable> validAccounts =
        intent.getParcelableArrayListExtra(EXTRA_ALLOWABLE_ACCOUNTS_ARRAYLIST);
    if (validAccounts != null) {
      setOfAllowableAccounts = new HashSet<>(validAccounts.size());
      Iterator<Parcelable> it = validAccounts.iterator();
      while (it.hasNext()) {
        Parcelable parcelable = it.next();
        setOfAllowableAccounts.add((Account) parcelable);
      }
    }
    return setOfAllowableAccounts;
  }

  private void overrideDescriptionIfSupplied(String descriptionOverride) {
    TextView descriptionView = (TextView) findViewById(C4337R.id.description);
    if (!TextUtils.isEmpty(descriptionOverride)) {
      descriptionView.setText(descriptionOverride);
    } else {
      descriptionView.setVisibility(8);
    }
  }

  private final void populateUIAccountList(String[] listItems) {
    ListView list = (ListView) findViewById(16908298);
    list.setAdapter(
        (ListAdapter)
            new ArrayAdapter(
                this, C4337R.layout.sem_choose_type_and_account_dialog_item_layout, listItems));
    list.setChoiceMode(1);
    list.setItemsCanFocus(false);
    list.setOnItemClickListener(
        new AdapterView
            .OnItemClickListener() { // from class: android.accounts.ChooseTypeAndAccountActivity.1
          @Override // android.widget.AdapterView.OnItemClickListener
          public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            ChooseTypeAndAccountActivity.this.mSelectedItemIndex = position;
            ChooseTypeAndAccountActivity.this.mOkButton.setEnabled(true);
          }
        });
    int i = this.mSelectedItemIndex;
    if (i != -1) {
      list.setItemChecked(i, true);
      if (Log.isLoggable(TAG, 2)) {
        Log.m100v(TAG, "List item " + this.mSelectedItemIndex + " should be selected");
      }
    }
  }

  private static class CheckedTextView extends android.widget.CheckedTextView {
    public CheckedTextView(Context context, AttributeSet attrs) {
      super(context, attrs);
      int i;
      if (getResources().getConfiguration().semDesktopModeEnabled != 1
          && Settings.System.getInt(context.getContentResolver(), "wallpapertheme_state", 0) != 0) {
        boolean isNightMode = (getResources().getConfiguration().uiMode & 48) == 32;
        Resources resources = getResources();
        if (isNightMode) {
          i = C4337R.color.sem_dialog_list_color_material_dark;
        } else {
          i = C4337R.color.sem_dialog_list_color_material_light;
        }
        int textColor = resources.getColor(i, null);
        setTextColor(textColor);
      }
    }
  }
}
