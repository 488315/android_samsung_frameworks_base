package android.preference;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.android.internal.R;

@Deprecated
/* loaded from: classes3.dex */
public abstract class PreferenceFragment extends Fragment implements PreferenceManager.OnPreferenceTreeClickListener {
    private static final int FIRST_REQUEST_CODE = 100;
    private static final float FONT_SCALE_LARGE = 1.3f;
    private static final float FONT_SCALE_MEDIUM = 1.1f;
    private static final int MAX_LOOP_COUNT = 100;
    private static final int MSG_BIND_PREFERENCES = 1;
    private static final String PREFERENCES_TAG = "android:preferences";
    private static final String SAMSUNG_BASIC_INTERACTION_METADATA_NAME = "SamsungBasicInteraction";
    private static final String SAMSUNG_BASIC_INTERACTION_METADATA_VALUE_SEP10 = "SEP10";
    private static final String SAMSUNG_BASIC_INTERACTION_METADATA_VALUE_SEP11 = "SEP11";
    public static final int TW_SWITCH_PREFERENCE_LAYOUT = 2;
    public static final int TW_SWITCH_PREFERENCE_LAYOUT_LARGE = 1;
    private boolean mHavePrefs;
    private boolean mInitDone;
    private int mIsLargeLayout;
    private ListView mList;
    private PreferenceManager mPreferenceManager;
    private boolean mIsMetaDataInActivity = View.sIsSamsungBasicInteraction;
    private int mLayoutResId = R.layout.preference_list_fragment;
    private Handler mHandler = new Handler() { // from class: android.preference.PreferenceFragment.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != 1) {
                return;
            }
            PreferenceFragment.this.bindPreferences();
        }
    };
    private final Runnable mRequestFocus = new Runnable() { // from class: android.preference.PreferenceFragment.2
        @Override // java.lang.Runnable
        public void run() {
            PreferenceFragment.this.mList.focusableViewAvailable(PreferenceFragment.this.mList);
        }
    };
    private View.OnKeyListener mListOnKeyListener = new View.OnKeyListener() { // from class: android.preference.PreferenceFragment.3
        @Override // android.view.View.OnKeyListener
        public boolean onKey(View view, int i, KeyEvent keyEvent) {
            Object selectedItem = PreferenceFragment.this.mList.getSelectedItem();
            if (!(selectedItem instanceof Preference)) {
                return false;
            }
            return ((Preference) selectedItem).onKey(PreferenceFragment.this.mList.getSelectedView(), i, keyEvent);
        }
    };

    @Deprecated
    public interface OnPreferenceStartFragmentCallback {
        boolean onPreferenceStartFragment(PreferenceFragment preferenceFragment, Preference preference);
    }

    protected void onBindPreferences() {
    }

    protected void onUnbindPreferences() {
    }

    @Override // android.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        PreferenceGroupAdapter preferenceGroupAdapter;
        ListView listView = this.mList;
        if (listView != null) {
            ListAdapter adapter = listView.getAdapter();
            int i = ((configuration.screenWidthDp > 320 || configuration.fontScale < FONT_SCALE_MEDIUM) && (configuration.screenWidthDp >= 411 || configuration.fontScale < FONT_SCALE_LARGE)) ? 2 : 1;
            if (this.mIsMetaDataInActivity && (adapter instanceof PreferenceGroupAdapter) && i != this.mIsLargeLayout) {
                this.mIsLargeLayout = i;
                int i2 = 0;
                boolean z = false;
                while (true) {
                    preferenceGroupAdapter = (PreferenceGroupAdapter) adapter;
                    if (i2 >= preferenceGroupAdapter.getCount()) {
                        break;
                    }
                    Preference item = preferenceGroupAdapter.getItem(i2);
                    int layoutResource = item.getLayoutResource();
                    if (item instanceof SwitchPreference) {
                        if (layoutResource == 17367547) {
                            item.setLayoutResource(R.layout.tw_switch_preference_screen_material);
                        } else if (layoutResource == 17367548) {
                            item.setLayoutResource(R.layout.tw_switch_preference_screen_large);
                        } else if (layoutResource == 17367537) {
                            item.setLayoutResource(R.layout.tw_preference_material);
                        } else if (layoutResource == 17367536) {
                            item.setLayoutResource(R.layout.tw_preference_switch_large);
                        }
                        z = true;
                    }
                    i2++;
                }
                if (z) {
                    preferenceGroupAdapter.notifyDataSetChanged();
                }
            }
        }
        super.onConfigurationChanged(configuration);
    }

    @Override // android.app.Fragment
    public void onCreate(Bundle bundle) {
        ActivityInfo activityInfo;
        super.onCreate(bundle);
        PreferenceManager preferenceManager = new PreferenceManager(getActivity(), 100);
        this.mPreferenceManager = preferenceManager;
        preferenceManager.setFragment(this);
        Activity activityContext = getActivityContext(getContext());
        Configuration configuration = getResources().getConfiguration();
        boolean z = true;
        this.mIsLargeLayout = ((configuration.screenWidthDp > 320 || configuration.fontScale < FONT_SCALE_MEDIUM) && (configuration.screenWidthDp >= 411 || configuration.fontScale < FONT_SCALE_LARGE)) ? 2 : 1;
        if (activityContext == null || (activityInfo = activityContext.getActivityInfo()) == null || activityInfo.metaData == null) {
            return;
        }
        String string = activityInfo.metaData.getString(SAMSUNG_BASIC_INTERACTION_METADATA_NAME);
        if (!SAMSUNG_BASIC_INTERACTION_METADATA_VALUE_SEP10.equals(string) && !SAMSUNG_BASIC_INTERACTION_METADATA_VALUE_SEP11.equals(string)) {
            z = false;
        }
        this.mIsMetaDataInActivity = z;
    }

    @Override // android.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        TypedArray obtainStyledAttributes = getActivity().obtainStyledAttributes(null, R.styleable.PreferenceFragment, 16844038, 0);
        this.mLayoutResId = obtainStyledAttributes.getResourceId(0, this.mLayoutResId);
        obtainStyledAttributes.recycle();
        return layoutInflater.inflate(this.mLayoutResId, viewGroup, false);
    }

    @Override // android.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        TypedArray obtainStyledAttributes = getActivity().obtainStyledAttributes(null, R.styleable.PreferenceFragment, 16844038, 0);
        ListView listView = (ListView) view.findViewById(16908298);
        if (listView != null && obtainStyledAttributes.hasValueOrEmpty(1)) {
            listView.setDivider(obtainStyledAttributes.getDrawable(1));
        }
        obtainStyledAttributes.recycle();
    }

    @Override // android.app.Fragment
    public void onActivityCreated(Bundle bundle) {
        Bundle bundle2;
        PreferenceScreen preferenceScreen;
        super.onActivityCreated(bundle);
        if (this.mHavePrefs) {
            bindPreferences();
        }
        this.mInitDone = true;
        if (bundle == null || (bundle2 = bundle.getBundle(PREFERENCES_TAG)) == null || (preferenceScreen = getPreferenceScreen()) == null) {
            return;
        }
        preferenceScreen.restoreHierarchyState(bundle2);
    }

    @Override // android.app.Fragment
    public void onStart() {
        super.onStart();
        this.mPreferenceManager.setOnPreferenceTreeClickListener(this);
    }

    @Override // android.app.Fragment
    public void onStop() {
        super.onStop();
        this.mPreferenceManager.dispatchActivityStop();
        this.mPreferenceManager.setOnPreferenceTreeClickListener(null);
    }

    @Override // android.app.Fragment
    public void onDestroyView() {
        ListView listView = this.mList;
        if (listView != null) {
            listView.setOnKeyListener(null);
        }
        this.mList = null;
        this.mHandler.removeCallbacks(this.mRequestFocus);
        this.mHandler.removeMessages(1);
        super.onDestroyView();
    }

    @Override // android.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        this.mPreferenceManager.dispatchActivityDestroy();
    }

    @Override // android.app.Fragment
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        if (preferenceScreen != null) {
            Bundle bundle2 = new Bundle();
            preferenceScreen.saveHierarchyState(bundle2);
            bundle.putBundle(PREFERENCES_TAG, bundle2);
        }
    }

    @Override // android.app.Fragment
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        this.mPreferenceManager.dispatchActivityResult(i, i2, intent);
    }

    public PreferenceManager getPreferenceManager() {
        return this.mPreferenceManager;
    }

    public void setPreferenceScreen(PreferenceScreen preferenceScreen) {
        if (!this.mPreferenceManager.setPreferences(preferenceScreen) || preferenceScreen == null) {
            return;
        }
        onUnbindPreferences();
        this.mHavePrefs = true;
        if (this.mInitDone) {
            postBindPreferences();
        }
    }

    public PreferenceScreen getPreferenceScreen() {
        return this.mPreferenceManager.getPreferenceScreen();
    }

    public void addPreferencesFromIntent(Intent intent) {
        requirePreferenceManager();
        setPreferenceScreen(this.mPreferenceManager.inflateFromIntent(intent, getPreferenceScreen()));
    }

    public void addPreferencesFromResource(int i) {
        requirePreferenceManager();
        setPreferenceScreen(this.mPreferenceManager.inflateFromResource(getActivity(), i, getPreferenceScreen()));
    }

    @Override // android.preference.PreferenceManager.OnPreferenceTreeClickListener
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        if (preference.getFragment() == null || !(getActivity() instanceof OnPreferenceStartFragmentCallback)) {
            return false;
        }
        return ((OnPreferenceStartFragmentCallback) getActivity()).onPreferenceStartFragment(this, preference);
    }

    public Preference findPreference(CharSequence charSequence) {
        PreferenceManager preferenceManager = this.mPreferenceManager;
        if (preferenceManager == null) {
            return null;
        }
        return preferenceManager.findPreference(charSequence);
    }

    private void requirePreferenceManager() {
        if (this.mPreferenceManager == null) {
            throw new RuntimeException("This should be called after super.onCreate.");
        }
    }

    private void postBindPreferences() {
        if (this.mHandler.hasMessages(1)) {
            return;
        }
        this.mHandler.obtainMessage(1).sendToTarget();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bindPreferences() {
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        if (preferenceScreen != null) {
            View view = getView();
            if (view != null) {
                View findViewById = view.findViewById(16908310);
                if (findViewById instanceof TextView) {
                    CharSequence title = preferenceScreen.getTitle();
                    if (TextUtils.isEmpty(title)) {
                        findViewById.setVisibility(8);
                    } else {
                        ((TextView) findViewById).lambda$setTextAsync$0(title);
                        findViewById.setVisibility(0);
                    }
                }
            }
            preferenceScreen.bind(getListView());
        }
        onBindPreferences();
    }

    public ListView getListView() {
        ensureList();
        return this.mList;
    }

    public ListView semGetListView() {
        return getListView();
    }

    public boolean hasListView() {
        if (this.mList != null) {
            return true;
        }
        View view = getView();
        if (view == null) {
            return false;
        }
        View findViewById = view.findViewById(16908298);
        if (!(findViewById instanceof ListView)) {
            return false;
        }
        ListView listView = (ListView) findViewById;
        this.mList = listView;
        return listView != null;
    }

    private void ensureList() {
        if (this.mList != null) {
            return;
        }
        View view = getView();
        if (view == null) {
            throw new IllegalStateException("Content view not yet created");
        }
        View findViewById = view.findViewById(16908298);
        if (!(findViewById instanceof ListView)) {
            throw new RuntimeException("Content has view with id attribute 'android.R.id.list' that is not a ListView class");
        }
        ListView listView = (ListView) findViewById;
        this.mList = listView;
        if (listView == null) {
            throw new RuntimeException("Your content must have a ListView whose id attribute is 'android.R.id.list'");
        }
        listView.setOnKeyListener(this.mListOnKeyListener);
        this.mHandler.post(this.mRequestFocus);
    }

    private Activity getActivityContext(Context context) {
        Activity activity = null;
        for (int i = 0; activity == null && context != null && i < 100; i++) {
            if (context instanceof Activity) {
                activity = (Activity) context;
            } else {
                context = context instanceof ContextWrapper ? ((ContextWrapper) context).getBaseContext() : null;
            }
        }
        return activity;
    }
}
