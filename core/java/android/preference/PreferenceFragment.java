package android.preference;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.p002pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.p009os.Bundle;
import android.p009os.Handler;
import android.p009os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.TextView;
import com.android.internal.C4337R;

@Deprecated
/* loaded from: classes3.dex */
public abstract class PreferenceFragment extends Fragment
    implements PreferenceManager.OnPreferenceTreeClickListener {
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
  private int mLayoutResId = C4337R.layout.preference_list_fragment;
  private Handler mHandler = new Handler() { // from class: android.preference.PreferenceFragment.1
        @Override // android.p009os.Handler
        public void handleMessage(Message msg) {
          switch (msg.what) {
            case 1:
              PreferenceFragment.this.bindPreferences();
              break;
          }
        }
      };
  private final Runnable mRequestFocus =
      new Runnable() { // from class: android.preference.PreferenceFragment.2
        @Override // java.lang.Runnable
        public void run() {
          PreferenceFragment.this.mList.focusableViewAvailable(PreferenceFragment.this.mList);
        }
      };
  private View.OnKeyListener mListOnKeyListener =
      new View.OnKeyListener() { // from class: android.preference.PreferenceFragment.3
        @Override // android.view.View.OnKeyListener
        public boolean onKey(View v, int keyCode, KeyEvent event) {
          Object selectedItem = PreferenceFragment.this.mList.getSelectedItem();
          if (selectedItem instanceof Preference) {
            View selectedView = PreferenceFragment.this.mList.getSelectedView();
            return ((Preference) selectedItem).onKey(selectedView, keyCode, event);
          }
          return false;
        }
      };

  @Deprecated
  public interface OnPreferenceStartFragmentCallback {
    boolean onPreferenceStartFragment(PreferenceFragment preferenceFragment, Preference preference);
  }

  @Override // android.app.Fragment, android.content.ComponentCallbacks
  public void onConfigurationChanged(Configuration newConfig) {
    ListView listView = this.mList;
    if (listView != null) {
      Adapter adapter = listView.getAdapter();
      int isLargeLayout =
          ((newConfig.screenWidthDp > 320 || newConfig.fontScale < FONT_SCALE_MEDIUM)
                  && (newConfig.screenWidthDp >= 411 || newConfig.fontScale < FONT_SCALE_LARGE))
              ? 2
              : 1;
      if (this.mIsMetaDataInActivity
          && (adapter instanceof PreferenceGroupAdapter)
          && isLargeLayout != this.mIsLargeLayout) {
        this.mIsLargeLayout = isLargeLayout;
        boolean hasSwitchPreference = false;
        for (int i = 0; i < ((PreferenceGroupAdapter) adapter).getCount(); i++) {
          Preference preference = ((PreferenceGroupAdapter) adapter).getItem(i);
          int currentLayoutId = preference.getLayoutResource();
          if (preference instanceof SwitchPreference) {
            hasSwitchPreference = true;
            if (currentLayoutId == 17367496) {
              preference.setLayoutResource(C4337R.layout.tw_switch_preference_screen_material);
            } else if (currentLayoutId == 17367497) {
              preference.setLayoutResource(C4337R.layout.tw_switch_preference_screen_large);
            } else if (currentLayoutId == 17367486) {
              preference.setLayoutResource(C4337R.layout.tw_preference_material);
            } else if (currentLayoutId == 17367485) {
              preference.setLayoutResource(C4337R.layout.tw_preference_switch_large);
            }
          }
        }
        if (hasSwitchPreference) {
          ((PreferenceGroupAdapter) adapter).notifyDataSetChanged();
        }
      }
    }
    super.onConfigurationChanged(newConfig);
  }

  @Override // android.app.Fragment
  public void onCreate(Bundle savedInstanceState) {
    ActivityInfo ai;
    super.onCreate(savedInstanceState);
    PreferenceManager preferenceManager = new PreferenceManager(getActivity(), 100);
    this.mPreferenceManager = preferenceManager;
    preferenceManager.setFragment(this);
    Activity activity = getActivityContext(getContext());
    Configuration configuration = getResources().getConfiguration();
    boolean z = true;
    this.mIsLargeLayout =
        ((configuration.screenWidthDp > 320 || configuration.fontScale < FONT_SCALE_MEDIUM)
                && (configuration.screenWidthDp >= 411
                    || configuration.fontScale < FONT_SCALE_LARGE))
            ? 2
            : 1;
    if (activity != null && (ai = activity.getActivityInfo()) != null && ai.metaData != null) {
      String data = ai.metaData.getString(SAMSUNG_BASIC_INTERACTION_METADATA_NAME);
      if (!SAMSUNG_BASIC_INTERACTION_METADATA_VALUE_SEP10.equals(data)
          && !SAMSUNG_BASIC_INTERACTION_METADATA_VALUE_SEP11.equals(data)) {
        z = false;
      }
      this.mIsMetaDataInActivity = z;
    }
  }

  @Override // android.app.Fragment
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    TypedArray a =
        getActivity()
            .obtainStyledAttributes(null, C4337R.styleable.PreferenceFragment, 16844038, 0);
    this.mLayoutResId = a.getResourceId(0, this.mLayoutResId);
    a.recycle();
    return inflater.inflate(this.mLayoutResId, container, false);
  }

  @Override // android.app.Fragment
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    TypedArray a =
        getActivity()
            .obtainStyledAttributes(null, C4337R.styleable.PreferenceFragment, 16844038, 0);
    ListView lv = (ListView) view.findViewById(16908298);
    if (lv != null && a.hasValueOrEmpty(1)) {
      lv.setDivider(a.getDrawable(1));
    }
    a.recycle();
  }

  @Override // android.app.Fragment
  public void onActivityCreated(Bundle savedInstanceState) {
    Bundle container;
    PreferenceScreen preferenceScreen;
    super.onActivityCreated(savedInstanceState);
    if (this.mHavePrefs) {
      bindPreferences();
    }
    this.mInitDone = true;
    if (savedInstanceState != null
        && (container = savedInstanceState.getBundle(PREFERENCES_TAG)) != null
        && (preferenceScreen = getPreferenceScreen()) != null) {
      preferenceScreen.restoreHierarchyState(container);
    }
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
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    PreferenceScreen preferenceScreen = getPreferenceScreen();
    if (preferenceScreen != null) {
      Bundle container = new Bundle();
      preferenceScreen.saveHierarchyState(container);
      outState.putBundle(PREFERENCES_TAG, container);
    }
  }

  @Override // android.app.Fragment
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    this.mPreferenceManager.dispatchActivityResult(requestCode, resultCode, data);
  }

  public PreferenceManager getPreferenceManager() {
    return this.mPreferenceManager;
  }

  public void setPreferenceScreen(PreferenceScreen preferenceScreen) {
    if (this.mPreferenceManager.setPreferences(preferenceScreen) && preferenceScreen != null) {
      onUnbindPreferences();
      this.mHavePrefs = true;
      if (this.mInitDone) {
        postBindPreferences();
      }
    }
  }

  public PreferenceScreen getPreferenceScreen() {
    return this.mPreferenceManager.getPreferenceScreen();
  }

  public void addPreferencesFromIntent(Intent intent) {
    requirePreferenceManager();
    setPreferenceScreen(this.mPreferenceManager.inflateFromIntent(intent, getPreferenceScreen()));
  }

  public void addPreferencesFromResource(int preferencesResId) {
    requirePreferenceManager();
    setPreferenceScreen(
        this.mPreferenceManager.inflateFromResource(
            getActivity(), preferencesResId, getPreferenceScreen()));
  }

  @Override // android.preference.PreferenceManager.OnPreferenceTreeClickListener
  public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
    if (preference.getFragment() != null
        && (getActivity() instanceof OnPreferenceStartFragmentCallback)) {
      return ((OnPreferenceStartFragmentCallback) getActivity())
          .onPreferenceStartFragment(this, preference);
    }
    return false;
  }

  public Preference findPreference(CharSequence key) {
    PreferenceManager preferenceManager = this.mPreferenceManager;
    if (preferenceManager == null) {
      return null;
    }
    return preferenceManager.findPreference(key);
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
      View root = getView();
      if (root != null) {
        View titleView = root.findViewById(16908310);
        if (titleView instanceof TextView) {
          CharSequence title = preferenceScreen.getTitle();
          if (TextUtils.isEmpty(title)) {
            titleView.setVisibility(8);
          } else {
            ((TextView) titleView).setText(title);
            titleView.setVisibility(0);
          }
        }
      }
      preferenceScreen.bind(getListView());
    }
    onBindPreferences();
  }

  protected void onBindPreferences() {}

  protected void onUnbindPreferences() {}

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
    View root = getView();
    if (root == null) {
      return false;
    }
    View rawListView = root.findViewById(16908298);
    if (!(rawListView instanceof ListView)) {
      return false;
    }
    ListView listView = (ListView) rawListView;
    this.mList = listView;
    return listView != null;
  }

  private void ensureList() {
    if (this.mList != null) {
      return;
    }
    View root = getView();
    if (root == null) {
      throw new IllegalStateException("Content view not yet created");
    }
    View rawListView = root.findViewById(16908298);
    if (!(rawListView instanceof ListView)) {
      throw new RuntimeException(
          "Content has view with id attribute 'android.R.id.list' that is not a ListView class");
    }
    ListView listView = (ListView) rawListView;
    this.mList = listView;
    if (listView == null) {
      throw new RuntimeException(
          "Your content must have a ListView whose id attribute is 'android.R.id.list'");
    }
    listView.setOnKeyListener(this.mListOnKeyListener);
    this.mHandler.post(this.mRequestFocus);
  }

  private Activity getActivityContext(Context context) {
    Activity activity = null;
    Context tempContext = context;
    for (int count = 0; activity == null && tempContext != null && count < 100; count++) {
      if (tempContext instanceof Activity) {
        activity = (Activity) tempContext;
      } else {
        tempContext =
            tempContext instanceof ContextWrapper
                ? ((ContextWrapper) tempContext).getBaseContext()
                : null;
      }
    }
    return activity;
  }
}
