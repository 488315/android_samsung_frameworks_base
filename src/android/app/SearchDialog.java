package android.app;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import com.android.internal.R;

/* loaded from: classes.dex */
public class SearchDialog extends Dialog {
    private static final boolean DBG = false;
    private static final String IME_OPTION_NO_MICROPHONE = "nm";
    private static final String INSTANCE_KEY_APPDATA = "data";
    private static final String INSTANCE_KEY_COMPONENT = "comp";
    private static final String INSTANCE_KEY_USER_QUERY = "uQry";
    private static final String LOG_TAG = "SearchDialog";
    private static final int SEARCH_PLATE_LEFT_PADDING_NON_GLOBAL = 7;
    private Context mActivityContext;
    private ImageView mAppIcon;
    private Bundle mAppSearchData;
    private TextView mBadgeLabel;
    private View mCloseSearch;
    private BroadcastReceiver mConfChangeListener;
    private ComponentName mLaunchComponent;
    private final SearchView.OnCloseListener mOnCloseListener;
    private final SearchView.OnQueryTextListener mOnQueryChangeListener;
    private final SearchView.OnSuggestionListener mOnSuggestionSelectionListener;
    private AutoCompleteTextView mSearchAutoComplete;
    private int mSearchAutoCompleteImeOptions;
    private View mSearchPlate;
    private SearchView mSearchView;
    private SearchableInfo mSearchable;
    private String mUserQuery;
    private final Intent mVoiceAppSearchIntent;
    private final Intent mVoiceWebSearchIntent;
    private Drawable mWorkingSpinner;

    static int resolveDialogTheme(Context context) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.searchDialogTheme, typedValue, true);
        return typedValue.resourceId;
    }

    public SearchDialog(Context context, SearchManager searchManager) {
        super(context, resolveDialogTheme(context));
        this.mConfChangeListener = new BroadcastReceiver() { // from class: android.app.SearchDialog.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if (intent.getAction().equals(Intent.ACTION_CONFIGURATION_CHANGED)) {
                    SearchDialog.this.onConfigurationChanged();
                }
            }
        };
        this.mOnCloseListener = new SearchView.OnCloseListener() { // from class: android.app.SearchDialog.3
            @Override // android.widget.SearchView.OnCloseListener
            public boolean onClose() {
                return SearchDialog.this.onClosePressed();
            }
        };
        this.mOnQueryChangeListener = new SearchView.OnQueryTextListener() { // from class: android.app.SearchDialog.4
            @Override // android.widget.SearchView.OnQueryTextListener
            public boolean onQueryTextChange(String str) {
                return false;
            }

            @Override // android.widget.SearchView.OnQueryTextListener
            public boolean onQueryTextSubmit(String str) {
                SearchDialog.this.dismiss();
                return false;
            }
        };
        this.mOnSuggestionSelectionListener = new SearchView.OnSuggestionListener() { // from class: android.app.SearchDialog.5
            @Override // android.widget.SearchView.OnSuggestionListener
            public boolean onSuggestionSelect(int i) {
                return false;
            }

            @Override // android.widget.SearchView.OnSuggestionListener
            public boolean onSuggestionClick(int i) {
                SearchDialog.this.dismiss();
                return false;
            }
        };
        Intent intent = new Intent(RecognizerIntent.ACTION_WEB_SEARCH);
        this.mVoiceWebSearchIntent = intent;
        intent.addFlags(268435456);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        Intent intent2 = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        this.mVoiceAppSearchIntent = intent2;
        intent2.addFlags(268435456);
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = -1;
        attributes.height = -1;
        attributes.gravity = 55;
        attributes.softInputMode = 16;
        window.setAttributes(attributes);
        setCanceledOnTouchOutside(true);
    }

    private void createContentView() {
        setContentView(R.layout.search_bar);
        SearchView searchView = (SearchView) findViewById(R.id.search_view);
        this.mSearchView = searchView;
        searchView.setIconified(false);
        this.mSearchView.setOnCloseListener(this.mOnCloseListener);
        this.mSearchView.setOnQueryTextListener(this.mOnQueryChangeListener);
        this.mSearchView.setOnSuggestionListener(this.mOnSuggestionSelectionListener);
        this.mSearchView.onActionViewExpanded();
        View viewFindViewById = findViewById(16908327);
        this.mCloseSearch = viewFindViewById;
        viewFindViewById.setOnClickListener(new View.OnClickListener() { // from class: android.app.SearchDialog.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                SearchDialog.this.dismiss();
            }
        });
        this.mBadgeLabel = (TextView) this.mSearchView.findViewById(R.id.search_badge);
        this.mSearchAutoComplete = (AutoCompleteTextView) this.mSearchView.findViewById(R.id.search_src_text);
        this.mAppIcon = (ImageView) findViewById(R.id.search_app_icon);
        this.mSearchPlate = this.mSearchView.findViewById(R.id.search_plate);
        this.mWorkingSpinner = getContext().getDrawable(R.drawable.search_spinner);
        setWorking(false);
        this.mBadgeLabel.setVisibility(8);
        this.mSearchAutoCompleteImeOptions = this.mSearchAutoComplete.getImeOptions();
    }

    public boolean show(String str, boolean z, ComponentName componentName, Bundle bundle) {
        boolean zDoShow = doShow(str, z, componentName, bundle);
        if (zDoShow) {
            this.mSearchAutoComplete.showDropDownAfterLayout();
        }
        return zDoShow;
    }

    private boolean doShow(String str, boolean z, ComponentName componentName, Bundle bundle) {
        if (!show(componentName, bundle)) {
            return false;
        }
        setUserQuery(str);
        if (!z) {
            return true;
        }
        this.mSearchAutoComplete.selectAll();
        return true;
    }

    private boolean show(ComponentName componentName, Bundle bundle) {
        SearchableInfo searchableInfo = ((SearchManager) this.mContext.getSystemService("search")).getSearchableInfo(componentName);
        this.mSearchable = searchableInfo;
        if (searchableInfo == null) {
            return false;
        }
        this.mLaunchComponent = componentName;
        this.mAppSearchData = bundle;
        this.mActivityContext = searchableInfo.getActivityContext(getContext());
        if (!isShowing()) {
            createContentView();
            this.mSearchView.setSearchableInfo(this.mSearchable);
            this.mSearchView.setAppSearchData(this.mAppSearchData);
            show();
        }
        updateUI();
        return true;
    }

    @Override // android.app.Dialog
    public void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_CONFIGURATION_CHANGED);
        getContext().registerReceiver(this.mConfChangeListener, intentFilter);
    }

    @Override // android.app.Dialog
    public void onStop() {
        super.onStop();
        getContext().unregisterReceiver(this.mConfChangeListener);
        this.mLaunchComponent = null;
        this.mAppSearchData = null;
        this.mSearchable = null;
        this.mUserQuery = null;
    }

    public void setWorking(boolean z) {
        this.mWorkingSpinner.setAlpha(z ? 255 : 0);
        this.mWorkingSpinner.setVisible(z, false);
        this.mWorkingSpinner.invalidateSelf();
    }

    @Override // android.app.Dialog
    public Bundle onSaveInstanceState() {
        if (!isShowing()) {
            return null;
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_KEY_COMPONENT, this.mLaunchComponent);
        bundle.putBundle("data", this.mAppSearchData);
        bundle.putString(INSTANCE_KEY_USER_QUERY, this.mUserQuery);
        return bundle;
    }

    @Override // android.app.Dialog
    public void onRestoreInstanceState(Bundle bundle) {
        if (bundle == null) {
            return;
        }
        doShow(bundle.getString(INSTANCE_KEY_USER_QUERY), false, (ComponentName) bundle.getParcelable(INSTANCE_KEY_COMPONENT, ComponentName.class), bundle.getBundle("data"));
    }

    public void onConfigurationChanged() {
        if (this.mSearchable == null || !isShowing()) {
            return;
        }
        updateSearchAppIcon();
        updateSearchBadge();
        if (isLandscapeMode(getContext())) {
            this.mSearchAutoComplete.setInputMethodMode(1);
            if (this.mSearchAutoComplete.isDropDownAlwaysVisible() || enoughToFilter()) {
                this.mSearchAutoComplete.showDropDown();
            }
        }
    }

    static boolean isLandscapeMode(Context context) {
        return context.getResources().getConfiguration().orientation == 2;
    }

    private boolean enoughToFilter() {
        Filterable filterable = (Filterable) this.mSearchAutoComplete.getAdapter();
        if (filterable == null || filterable.getFilter() == null) {
            return false;
        }
        return this.mSearchAutoComplete.enoughToFilter();
    }

    private void updateUI() {
        if (this.mSearchable != null) {
            this.mDecor.setVisibility(0);
            updateSearchAutoComplete();
            updateSearchAppIcon();
            updateSearchBadge();
            int inputType = this.mSearchable.getInputType();
            if ((inputType & 15) == 1) {
                inputType &= -65537;
                if (this.mSearchable.getSuggestAuthority() != null) {
                    inputType |= 65536;
                }
            }
            this.mSearchAutoComplete.setInputType(inputType);
            int imeOptions = this.mSearchable.getImeOptions();
            this.mSearchAutoCompleteImeOptions = imeOptions;
            this.mSearchAutoComplete.setImeOptions(imeOptions);
            if (this.mSearchable.getVoiceSearchEnabled()) {
                this.mSearchAutoComplete.setPrivateImeOptions(IME_OPTION_NO_MICROPHONE);
            } else {
                this.mSearchAutoComplete.setPrivateImeOptions(null);
            }
        }
    }

    private void updateSearchAutoComplete() {
        this.mSearchAutoComplete.setDropDownDismissedOnCompletion(false);
        this.mSearchAutoComplete.setForceIgnoreOutsideTouch(false);
    }

    private void updateSearchAppIcon() {
        Drawable defaultActivityIcon;
        PackageManager packageManager = getContext().getPackageManager();
        try {
            defaultActivityIcon = packageManager.getApplicationIcon(packageManager.getActivityInfo(this.mLaunchComponent, 0).applicationInfo);
        } catch (PackageManager.NameNotFoundException unused) {
            defaultActivityIcon = packageManager.getDefaultActivityIcon();
            Log.w(LOG_TAG, this.mLaunchComponent + " not found, using generic app icon");
        }
        this.mAppIcon.lambda$setImageURIAsync$2(defaultActivityIcon);
        this.mAppIcon.setVisibility(0);
        View view = this.mSearchPlate;
        view.setPadding(7, view.getPaddingTop(), this.mSearchPlate.getPaddingRight(), this.mSearchPlate.getPaddingBottom());
    }

    private void updateSearchBadge() {
        int i;
        Drawable drawable;
        String string;
        if (this.mSearchable.useBadgeIcon()) {
            drawable = this.mActivityContext.getDrawable(this.mSearchable.getIconId());
            i = 0;
            string = null;
        } else if (this.mSearchable.useBadgeLabel()) {
            i = 0;
            string = this.mActivityContext.getResources().getText(this.mSearchable.getLabelId()).toString();
            drawable = null;
        } else {
            i = 8;
            drawable = null;
            string = null;
        }
        this.mBadgeLabel.setCompoundDrawablesWithIntrinsicBounds(drawable, (Drawable) null, (Drawable) null, (Drawable) null);
        this.mBadgeLabel.lambda$setTextAsync$0(string);
        this.mBadgeLabel.setVisibility(i);
    }

    @Override // android.app.Dialog
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.mSearchAutoComplete.isPopupShowing() && isOutOfBounds(this.mSearchPlate, motionEvent)) {
            cancel();
            return true;
        }
        return super.onTouchEvent(motionEvent);
    }

    private boolean isOutOfBounds(View view, MotionEvent motionEvent) {
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        int scaledWindowTouchSlop = ViewConfiguration.get(this.mContext).getScaledWindowTouchSlop();
        int i = -scaledWindowTouchSlop;
        return x < i || y < i || x > view.getWidth() + scaledWindowTouchSlop || y > view.getHeight() + scaledWindowTouchSlop;
    }

    @Override // android.app.Dialog
    public void hide() {
        if (isShowing()) {
            InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(InputMethodManager.class);
            if (inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
            }
            super.hide();
        }
    }

    public void launchQuerySearch() {
        launchQuerySearch(0, null);
    }

    protected void launchQuerySearch(int i, String str) {
        launchIntent(createIntent(Intent.ACTION_SEARCH, null, null, this.mSearchAutoComplete.getText().toString(), i, str));
    }

    private void launchIntent(Intent intent) {
        if (intent == null) {
            return;
        }
        Log.d(LOG_TAG, "launching " + intent);
        try {
            getContext().startActivity(intent);
            dismiss();
        } catch (RuntimeException e) {
            Log.e(LOG_TAG, "Failed launch activity: " + intent, e);
        }
    }

    public void setListSelection(int i) {
        this.mSearchAutoComplete.setListSelection(i);
    }

    private Intent createIntent(String str, Uri uri, String str2, String str3, int i, String str4) {
        Intent intent = new Intent(str);
        intent.addFlags(268435456);
        if (uri != null) {
            intent.setData(uri);
        }
        intent.putExtra(SearchManager.USER_QUERY, this.mUserQuery);
        if (str3 != null) {
            intent.putExtra("query", str3);
        }
        if (str2 != null) {
            intent.putExtra(SearchManager.EXTRA_DATA_KEY, str2);
        }
        Bundle bundle = this.mAppSearchData;
        if (bundle != null) {
            intent.putExtra(SearchManager.APP_DATA, bundle);
        }
        if (i != 0) {
            intent.putExtra(SearchManager.ACTION_KEY, i);
            intent.putExtra(SearchManager.ACTION_MSG, str4);
        }
        intent.setComponent(this.mSearchable.getSearchActivity());
        return intent;
    }

    public static class SearchBar extends LinearLayout {
        public SearchBar(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public SearchBar(Context context) {
            super(context);
        }

        @Override // android.view.ViewGroup, android.view.ViewParent
        public ActionMode startActionModeForChild(View view, ActionMode.Callback callback, int i) {
            if (i != 0) {
                return super.startActionModeForChild(view, callback, i);
            }
            return null;
        }
    }

    private boolean isEmpty(AutoCompleteTextView autoCompleteTextView) {
        return TextUtils.getTrimmedLength(autoCompleteTextView.getText()) == 0;
    }

    @Override // android.app.Dialog
    public void onBackPressed() {
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(InputMethodManager.class);
        if (inputMethodManager != null && inputMethodManager.isFullscreenMode() && inputMethodManager.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0)) {
            return;
        }
        cancel();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean onClosePressed() {
        if (!isEmpty(this.mSearchAutoComplete)) {
            return false;
        }
        dismiss();
        return true;
    }

    private void setUserQuery(String str) {
        if (str == null) {
            str = "";
        }
        this.mUserQuery = str;
        this.mSearchAutoComplete.lambda$setTextAsync$0(str);
        this.mSearchAutoComplete.setSelection(str.length());
    }
}
