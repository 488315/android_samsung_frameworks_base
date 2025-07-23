package com.android.internal.app;

import android.app.ListFragment;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.LocaleList;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import com.android.internal.R;
import com.android.internal.app.LocaleHelper;
import com.android.internal.app.LocaleStore;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

@Deprecated
/* loaded from: classes5.dex */
public class LocalePickerWithRegion extends ListFragment implements SearchView.OnQueryTextListener {
    private static final String PARENT_FRAGMENT_NAME = "localeListEditor";
    private static final String TAG = "LocalePickerWithRegion";
    private SuggestedLocaleAdapter mAdapter;
    private String mAppPackageName;
    private LocaleSelectedListener mListener;
    private Set<LocaleStore.LocaleInfo> mLocaleList;
    private LocaleCollectorBase mLocalePickerCollector;
    private MenuItem.OnActionExpandListener mOnActionExpandListener;
    private LocaleStore.LocaleInfo mParentLocale;
    private int mSubheaderColor;
    private boolean mTranslatedOnly = false;
    private SearchView mSearchView = null;
    private CharSequence mPreviousSearch = null;
    private boolean mPreviousSearchHadFocus = false;
    private int mFirstVisiblePosition = 0;
    private int mTopDistance = 0;
    private CharSequence mTitle = null;
    private boolean mIsNumberingSystem = false;
    private boolean mIsLight = true;
    private int mPreviousSecSuggestionCount = 0;
    private int mChangeDisplayName = 0;

    public interface LocaleSelectedListener {
        void onLocaleSelected(LocaleStore.LocaleInfo localeInfo);

        default void onParentLocaleSelected(LocaleStore.LocaleInfo localeInfo) {
        }
    }

    @Override // android.widget.SearchView.OnQueryTextListener
    public boolean onQueryTextSubmit(String str) {
        return false;
    }

    private static LocalePickerWithRegion createNumberingSystemPicker(LocaleSelectedListener localeSelectedListener, LocaleStore.LocaleInfo localeInfo, boolean z, MenuItem.OnActionExpandListener onActionExpandListener, LocaleCollectorBase localeCollectorBase) {
        LocalePickerWithRegion localePickerWithRegion = new LocalePickerWithRegion();
        localePickerWithRegion.setOnActionExpandListener(onActionExpandListener);
        localePickerWithRegion.setIsNumberingSystem(true);
        if (localePickerWithRegion.setListener(localeSelectedListener, localeInfo, z, localeCollectorBase)) {
            return localePickerWithRegion;
        }
        return null;
    }

    private static LocalePickerWithRegion createCountryPicker(LocaleSelectedListener localeSelectedListener, LocaleStore.LocaleInfo localeInfo, boolean z, MenuItem.OnActionExpandListener onActionExpandListener, LocaleCollectorBase localeCollectorBase) {
        return createCountryPicker(localeSelectedListener, localeInfo, z, onActionExpandListener, localeCollectorBase, 0);
    }

    private static LocalePickerWithRegion createCountryPicker(LocaleSelectedListener localeSelectedListener, LocaleStore.LocaleInfo localeInfo, boolean z, MenuItem.OnActionExpandListener onActionExpandListener, LocaleCollectorBase localeCollectorBase, int i) {
        LocalePickerWithRegion localePickerWithRegion = new LocalePickerWithRegion();
        localePickerWithRegion.mChangeDisplayName = i | localePickerWithRegion.mChangeDisplayName;
        localePickerWithRegion.setOnActionExpandListener(onActionExpandListener);
        if (localePickerWithRegion.setListener(localeSelectedListener, localeInfo, z, localeCollectorBase)) {
            return localePickerWithRegion;
        }
        return null;
    }

    public static LocalePickerWithRegion createLanguagePicker(Context context, LocaleSelectedListener localeSelectedListener, boolean z) {
        return createLanguagePicker(context, localeSelectedListener, z, null, null, null);
    }

    public static LocalePickerWithRegion createLanguagePicker(Context context, LocaleSelectedListener localeSelectedListener, boolean z, LocaleList localeList) {
        return createLanguagePicker(context, localeSelectedListener, z, localeList, null, null);
    }

    public static LocalePickerWithRegion createLanguagePicker(Context context, LocaleSelectedListener localeSelectedListener, boolean z, LocaleList localeList, String str, MenuItem.OnActionExpandListener onActionExpandListener) {
        return createLanguagePicker(context, localeSelectedListener, z, localeList, str, onActionExpandListener, 0);
    }

    public static LocalePickerWithRegion createLanguagePicker(Context context, LocaleSelectedListener localeSelectedListener, boolean z, LocaleList localeList, String str, MenuItem.OnActionExpandListener onActionExpandListener, int i) {
        LocaleCollectorBase appLocaleCollector;
        if (TextUtils.isEmpty(str)) {
            appLocaleCollector = new SystemLocaleCollector(context, localeList);
        } else {
            appLocaleCollector = new AppLocaleCollector(context, str);
        }
        LocalePickerWithRegion localePickerWithRegion = new LocalePickerWithRegion();
        localePickerWithRegion.mChangeDisplayName |= i;
        localePickerWithRegion.setOnActionExpandListener(onActionExpandListener);
        localePickerWithRegion.setListener(localeSelectedListener, null, z, appLocaleCollector);
        return localePickerWithRegion;
    }

    private void setIsNumberingSystem(boolean z) {
        this.mIsNumberingSystem = z;
    }

    private boolean setListener(LocaleSelectedListener localeSelectedListener, LocaleStore.LocaleInfo localeInfo, boolean z, LocaleCollectorBase localeCollectorBase) {
        this.mParentLocale = localeInfo;
        this.mListener = localeSelectedListener;
        this.mTranslatedOnly = z;
        this.mLocalePickerCollector = localeCollectorBase;
        setRetainInstance(true);
        this.mLocaleList = localeCollectorBase.getSupportedLocaleList(localeInfo, z, localeInfo != null);
        Log.d(TAG, "mLocaleList size:  " + this.mLocaleList.size());
        if (localeInfo == null || localeSelectedListener == null || this.mLocaleList.size() != 1) {
            return true;
        }
        localeSelectedListener.onLocaleSelected(this.mLocaleList.iterator().next());
        return false;
    }

    private Set<LocaleStore.LocaleInfo> filterTheLanguagesNotSupportedInApp(boolean z, HashSet<Locale> hashSet) {
        HashSet hashSet2 = new HashSet();
        if (z) {
            for (LocaleStore.LocaleInfo localeInfo : this.mLocaleList) {
                if (hashSet.contains(localeInfo.getLocale())) {
                    hashSet2.add(localeInfo);
                } else {
                    Iterator<Locale> it = hashSet.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            if (LocaleList.matchesLanguageAndScript(localeInfo.getLocale(), it.next())) {
                                hashSet2.add(localeInfo);
                                break;
                            }
                        }
                    }
                }
            }
        }
        return hashSet2;
    }

    private void returnToParentFrame() {
        getFragmentManager().popBackStack(PARENT_FRAGMENT_NAME, 1);
    }

    @Override // android.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setHasOptionsMenu(true);
        if (this.mLocaleList == null) {
            returnToParentFrame();
            return;
        }
        this.mTitle = getActivity().getTitle();
        LocaleStore.LocaleInfo localeInfo = this.mParentLocale;
        boolean z = localeInfo != null;
        Locale locale = z ? localeInfo.getLocale() : Locale.getDefault();
        LocaleCollectorBase localeCollectorBase = this.mLocalePickerCollector;
        boolean z2 = localeCollectorBase != null && localeCollectorBase.hasSpecificPackageName();
        SuggestedLocaleAdapter suggestedLocaleAdapter = new SuggestedLocaleAdapter(this.mLocaleList, z, z2, this.mChangeDisplayName);
        this.mAdapter = suggestedLocaleAdapter;
        suggestedLocaleAdapter.setNumberingSystemMode(this.mIsNumberingSystem);
        this.mAdapter.sort(new LocaleHelper.LocaleInfoComparator(locale, z));
        this.mAdapter.sortForSecSuggested(new LocaleHelper.LocaleInfoComparator(locale, z, true));
        this.mPreviousSecSuggestionCount = this.mAdapter.getSecSuggestionCount();
        if (!z2 && !z) {
            this.mAdapter.showAllItems();
        }
        if (z2 && this.mOnActionExpandListener != null) {
            this.mAdapter.setSecSuggestionCount(0);
            this.mAdapter.showAllItems();
        }
        setListAdapter(this.mAdapter);
    }

    @Override // android.app.ListFragment, android.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        getListView().setNestedScrollingEnabled(true);
        getListView().setDivider(null);
    }

    @Override // android.app.Fragment
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        TypedValue typedValue = new TypedValue();
        getContext().getTheme().resolveAttribute(16844176, typedValue, true);
        if (typedValue.data != 0) {
            this.mSubheaderColor = getResources().getColor(R.color.sem_round_and_bgcolor_light);
            this.mIsLight = true;
        } else {
            this.mSubheaderColor = getResources().getColor(R.color.sem_round_and_bgcolor_dark);
            this.mIsLight = false;
        }
        LayoutInflater from = LayoutInflater.from(getContext());
        SuggestedLocaleAdapter suggestedLocaleAdapter = this.mAdapter;
        if (suggestedLocaleAdapter != null) {
            suggestedLocaleAdapter.updateTheme(from, this.mSubheaderColor);
            this.mAdapter.notifyDataSetChanged();
        }
        ListView listView = getListView();
        if (listView != null) {
            listView.setDividerHeight(0);
            listView.setDivider(null);
            listView.semSetBottomColor(this.mSubheaderColor);
            listView.semSetFastScrollCustomEffectEnabled(true);
            listView.setFastScrollEnabled(true);
            listView.semSetRoundedCorners(3);
            listView.semSetRoundedCornerColor(3, this.mSubheaderColor);
            if (this.mIsLight) {
                listView.semSetGoToTopEnabled(true, 0);
            } else {
                listView.semSetGoToTopEnabled(true);
            }
            View inflate = from.inflate(R.layout.sem_language_picker_footer, (ViewGroup) null, false);
            inflate.setBackgroundColor(this.mSubheaderColor);
            listView.addFooterView(inflate, null, false);
        }
    }

    @Override // android.app.Fragment
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == 16908332) {
            getFragmentManager().popBackStack();
            return true;
        }
        if (itemId != 16909296) {
            return super.onOptionsItemSelected(menuItem);
        }
        return false;
    }

    @Override // android.app.Fragment
    public void onResume() {
        super.onResume();
        if (this.mParentLocale != null) {
            getActivity().setTitle(this.mParentLocale.getFullNameNative());
        } else {
            getActivity().setTitle(this.mTitle);
        }
        getListView().requestFocus();
    }

    @Override // android.app.Fragment
    public void onPause() {
        super.onPause();
        SearchView searchView = this.mSearchView;
        if (searchView != null) {
            this.mPreviousSearchHadFocus = searchView.hasFocus();
            this.mPreviousSearch = this.mSearchView.getQuery();
        } else {
            this.mPreviousSearchHadFocus = false;
            this.mPreviousSearch = null;
        }
        ListView listView = getListView();
        View childAt = listView.getChildAt(0);
        this.mFirstVisiblePosition = listView.getFirstVisiblePosition();
        this.mTopDistance = childAt != null ? childAt.getTop() - listView.getPaddingTop() : 0;
    }

    @Override // android.app.ListFragment
    public void onListItemClick(ListView listView, View view, int i, long j) {
        LocalePickerWithRegion createCountryPicker;
        LocaleStore.LocaleInfo localeInfo = (LocaleStore.LocaleInfo) listView.getAdapter().getItem(i);
        if (localeInfo == null) {
            Log.d(TAG, "Can not get the locale.");
            return;
        }
        boolean isSystemLocale = localeInfo.isSystemLocale();
        boolean z = localeInfo.getParent() != null;
        boolean hasNumberingSystems = localeInfo.hasNumberingSystems();
        if (isSystemLocale || ((z && !hasNumberingSystems) || this.mIsNumberingSystem)) {
            LocaleSelectedListener localeSelectedListener = this.mListener;
            if (localeSelectedListener != null) {
                localeSelectedListener.onLocaleSelected(localeInfo);
                return;
            } else {
                returnToParentFrame();
                return;
            }
        }
        if (hasNumberingSystems) {
            createCountryPicker = createNumberingSystemPicker(this.mListener, localeInfo, this.mTranslatedOnly, this.mOnActionExpandListener, this.mLocalePickerCollector);
        } else {
            createCountryPicker = createCountryPicker(this.mListener, localeInfo, this.mTranslatedOnly, this.mOnActionExpandListener, this.mLocalePickerCollector, this.mChangeDisplayName);
            SearchView searchView = this.mSearchView;
            if (searchView != null) {
                searchView.clearFocus();
            }
        }
        this.mListener.onParentLocaleSelected(localeInfo);
        if (createCountryPicker != null) {
            getFragmentManager().beginTransaction().replace(getId(), createCountryPicker).addToBackStack(null).commit();
        } else {
            returnToParentFrame();
        }
    }

    @Override // android.app.Fragment
    public void onPrepareOptionsMenu(Menu menu) {
        if (!this.mLocalePickerCollector.hasSpecificPackageName() || this.mOnActionExpandListener == null) {
            super.onPrepareOptionsMenu(menu);
        }
    }

    @Override // android.app.Fragment
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        if ((!this.mLocalePickerCollector.hasSpecificPackageName() || this.mOnActionExpandListener == null) && this.mParentLocale == null) {
            menuInflater.inflate(R.menu.language_selection_list, menu);
            MenuItem findItem = menu.findItem(R.id.locale_search_menu);
            SearchView searchView = new SearchView(getContext());
            this.mSearchView = searchView;
            findItem.setActionView(searchView);
            LinearLayout linearLayout = (LinearLayout) this.mSearchView.findViewById(getResources().getIdentifier("android:id/search_plate", null, null));
            if (linearLayout != null) {
                linearLayout.setPadding(0, linearLayout.getPaddingTop(), linearLayout.getPaddingRight(), linearLayout.getPaddingBottom());
            }
            findItem.setShowAsAction(2);
            MenuItem.OnActionExpandListener onActionExpandListener = this.mOnActionExpandListener;
            if (onActionExpandListener != null) {
                findItem.setOnActionExpandListener(onActionExpandListener);
            }
            SearchView searchView2 = this.mSearchView;
            if (searchView2 != null) {
                searchView2.setQueryHint(getText(R.string.search_language_hint));
                this.mSearchView.setOnQueryTextListener(this);
                if (!TextUtils.isEmpty(this.mPreviousSearch)) {
                    findItem.expandActionView();
                    this.mSearchView.setIconified(false);
                    this.mSearchView.setActivated(true);
                    if (this.mPreviousSearchHadFocus) {
                        this.mSearchView.requestFocus();
                    }
                    this.mSearchView.setQuery(this.mPreviousSearch, true);
                } else {
                    this.mSearchView.setQuery(null, false);
                }
            }
            getListView().setSelectionFromTop(this.mFirstVisiblePosition, this.mTopDistance);
        }
    }

    @Override // android.widget.SearchView.OnQueryTextListener
    public boolean onQueryTextChange(String str) {
        SuggestedLocaleAdapter suggestedLocaleAdapter = this.mAdapter;
        if (suggestedLocaleAdapter == null) {
            return false;
        }
        suggestedLocaleAdapter.getFilter().filter(str);
        return false;
    }

    public void setOnActionExpandListener(MenuItem.OnActionExpandListener onActionExpandListener) {
        this.mOnActionExpandListener = onActionExpandListener;
    }

    @Override // android.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        LinearLayout linearLayout;
        super.onConfigurationChanged(configuration);
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.sem_locale_picker_action_bar_margin_top);
        int identifier = getResources().getIdentifier("android:id/search_bar", null, null);
        SearchView searchView = this.mSearchView;
        if (searchView == null || (linearLayout = (LinearLayout) searchView.findViewById(identifier)) == null) {
            return;
        }
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
        layoutParams.setMargins(0, dimensionPixelSize, 0, 0);
        linearLayout.setLayoutParams(layoutParams);
    }
}
