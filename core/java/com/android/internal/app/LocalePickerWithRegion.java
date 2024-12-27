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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

/* loaded from: classes5.dex */
public class LocalePickerWithRegion extends ListFragment implements SearchView.OnQueryTextListener {
    private static final String PARENT_FRAGMENT_NAME = "localeListEditor";
    private static final String TAG = LocalePickerWithRegion.class.getSimpleName();
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

    interface LocaleCollectorBase {
        HashSet<String> getIgnoredLocaleList(boolean z);

        Set<LocaleStore.LocaleInfo> getSupportedLocaleList(
                LocaleStore.LocaleInfo localeInfo, boolean z, boolean z2);

        boolean hasSpecificPackageName();
    }

    public interface LocaleSelectedListener {
        void onLocaleSelected(LocaleStore.LocaleInfo localeInfo);
    }

    private static LocalePickerWithRegion createNumberingSystemPicker(
            LocaleSelectedListener listener,
            LocaleStore.LocaleInfo parent,
            boolean translatedOnly,
            MenuItem.OnActionExpandListener onActionExpandListener,
            LocaleCollectorBase localePickerCollector) {
        LocalePickerWithRegion localePicker = new LocalePickerWithRegion();
        localePicker.setOnActionExpandListener(onActionExpandListener);
        localePicker.setIsNumberingSystem(true);
        boolean shouldShowTheList =
                localePicker.setListener(listener, parent, translatedOnly, localePickerCollector);
        if (shouldShowTheList) {
            return localePicker;
        }
        return null;
    }

    private static LocalePickerWithRegion createCountryPicker(
            LocaleSelectedListener listener,
            LocaleStore.LocaleInfo parent,
            boolean translatedOnly,
            MenuItem.OnActionExpandListener onActionExpandListener,
            LocaleCollectorBase localePickerCollector) {
        return createCountryPicker(
                listener, parent, translatedOnly, onActionExpandListener, localePickerCollector, 0);
    }

    private static LocalePickerWithRegion createCountryPicker(
            LocaleSelectedListener listener,
            LocaleStore.LocaleInfo parent,
            boolean translatedOnly,
            MenuItem.OnActionExpandListener onActionExpandListener,
            LocaleCollectorBase localePickerCollector,
            int changeDisplayName) {
        LocalePickerWithRegion localePicker = new LocalePickerWithRegion();
        localePicker.mChangeDisplayName |= changeDisplayName;
        localePicker.setOnActionExpandListener(onActionExpandListener);
        boolean shouldShowTheList =
                localePicker.setListener(listener, parent, translatedOnly, localePickerCollector);
        if (shouldShowTheList) {
            return localePicker;
        }
        return null;
    }

    public static LocalePickerWithRegion createLanguagePicker(
            Context context, LocaleSelectedListener listener, boolean translatedOnly) {
        return createLanguagePicker(context, listener, translatedOnly, null, null, null);
    }

    public static LocalePickerWithRegion createLanguagePicker(
            Context context,
            LocaleSelectedListener listener,
            boolean translatedOnly,
            LocaleList explicitLocales) {
        return createLanguagePicker(context, listener, translatedOnly, explicitLocales, null, null);
    }

    public static LocalePickerWithRegion createLanguagePicker(
            Context context,
            LocaleSelectedListener listener,
            boolean translatedOnly,
            LocaleList explicitLocales,
            String appPackageName,
            MenuItem.OnActionExpandListener onActionExpandListener) {
        return createLanguagePicker(
                context,
                listener,
                translatedOnly,
                explicitLocales,
                appPackageName,
                onActionExpandListener,
                0);
    }

    public static LocalePickerWithRegion createLanguagePicker(
            Context context,
            LocaleSelectedListener listener,
            boolean translatedOnly,
            LocaleList explicitLocales,
            String appPackageName,
            MenuItem.OnActionExpandListener onActionExpandListener,
            int changeDisplayName) {
        LocaleCollectorBase localePickerController;
        if (TextUtils.isEmpty(appPackageName)) {
            localePickerController = new SystemLocaleCollector(context, explicitLocales);
        } else {
            localePickerController = new AppLocaleCollector(context, appPackageName);
        }
        LocalePickerWithRegion localePicker = new LocalePickerWithRegion();
        localePicker.mChangeDisplayName |= changeDisplayName;
        localePicker.setOnActionExpandListener(onActionExpandListener);
        localePicker.setListener(listener, null, translatedOnly, localePickerController);
        return localePicker;
    }

    private void setIsNumberingSystem(boolean isNumberingSystem) {
        this.mIsNumberingSystem = isNumberingSystem;
    }

    private boolean setListener(
            LocaleSelectedListener listener,
            LocaleStore.LocaleInfo parent,
            boolean translatedOnly,
            LocaleCollectorBase localePickerController) {
        boolean z;
        this.mParentLocale = parent;
        this.mListener = listener;
        this.mTranslatedOnly = translatedOnly;
        this.mLocalePickerCollector = localePickerController;
        setRetainInstance(true);
        if (parent != null) {
            z = true;
        } else {
            z = false;
        }
        this.mLocaleList = localePickerController.getSupportedLocaleList(parent, translatedOnly, z);
        Log.d(TAG, "mLocaleList size:  " + this.mLocaleList.size());
        if (parent == null || listener == null || this.mLocaleList.size() != 1) {
            return true;
        }
        listener.onLocaleSelected(this.mLocaleList.iterator().next());
        return false;
    }

    private Set<LocaleStore.LocaleInfo> filterTheLanguagesNotSupportedInApp(
            boolean shouldShowList, HashSet<Locale> supportedLocales) {
        Set<LocaleStore.LocaleInfo> filteredList = new HashSet<>();
        if (!shouldShowList) {
            return filteredList;
        }
        for (LocaleStore.LocaleInfo li : this.mLocaleList) {
            if (supportedLocales.contains(li.getLocale())) {
                filteredList.add(li);
            } else {
                Iterator<Locale> it = supportedLocales.iterator();
                while (true) {
                    if (it.hasNext()) {
                        Locale l = it.next();
                        if (LocaleList.matchesLanguageAndScript(li.getLocale(), l)) {
                            filteredList.add(li);
                            break;
                        }
                    }
                }
            }
        }
        return filteredList;
    }

    private void returnToParentFrame() {
        getFragmentManager().popBackStack(PARENT_FRAGMENT_NAME, 1);
    }

    @Override // android.app.Fragment
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (this.mLocaleList == null) {
            returnToParentFrame();
            return;
        }
        this.mTitle = getActivity().getTitle();
        boolean countryMode = this.mParentLocale != null;
        Locale sortingLocale = countryMode ? this.mParentLocale.getLocale() : Locale.getDefault();
        boolean hasSpecificPackageName =
                this.mLocalePickerCollector != null
                        && this.mLocalePickerCollector.hasSpecificPackageName();
        this.mAdapter =
                new SuggestedLocaleAdapter(
                        this.mLocaleList,
                        countryMode,
                        hasSpecificPackageName,
                        this.mChangeDisplayName);
        this.mAdapter.setNumberingSystemMode(this.mIsNumberingSystem);
        LocaleHelper.LocaleInfoComparator comp =
                new LocaleHelper.LocaleInfoComparator(sortingLocale, countryMode);
        this.mAdapter.sort(comp);
        LocaleHelper.LocaleInfoComparator compForSecSuggested =
                new LocaleHelper.LocaleInfoComparator(sortingLocale, countryMode, true);
        this.mAdapter.sortForSecSuggested(compForSecSuggested);
        this.mPreviousSecSuggestionCount = this.mAdapter.getSecSuggestionCount();
        if (!hasSpecificPackageName && !countryMode) {
            this.mAdapter.showAllItems();
        }
        if (hasSpecificPackageName && this.mOnActionExpandListener != null) {
            this.mAdapter.setSecSuggestionCount(0);
            this.mAdapter.showAllItems();
        }
        setListAdapter(this.mAdapter);
    }

    @Override // android.app.ListFragment, android.app.Fragment
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getListView().setNestedScrollingEnabled(true);
        getListView().setDivider(null);
    }

    @Override // android.app.Fragment
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TypedValue outValue = new TypedValue();
        getContext().getTheme().resolveAttribute(16844176, outValue, true);
        if (outValue.data != 0) {
            this.mSubheaderColor = getResources().getColor(R.color.sem_round_and_bgcolor_light);
            this.mIsLight = true;
        } else {
            this.mSubheaderColor = getResources().getColor(R.color.sem_round_and_bgcolor_dark);
            this.mIsLight = false;
        }
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        if (this.mAdapter != null) {
            this.mAdapter.updateTheme(layoutInflater, this.mSubheaderColor);
            this.mAdapter.notifyDataSetChanged();
        }
        ListView list = getListView();
        if (list != null) {
            list.setDividerHeight(0);
            list.setDivider(null);
            list.semSetBottomColor(this.mSubheaderColor);
            list.semSetFastScrollCustomEffectEnabled(true);
            list.setFastScrollEnabled(true);
            list.semSetRoundedCorners(3);
            list.semSetRoundedCornerColor(3, this.mSubheaderColor);
            if (this.mIsLight) {
                list.semSetGoToTopEnabled(true, 0);
            } else {
                list.semSetGoToTopEnabled(true);
            }
            View footer =
                    layoutInflater.inflate(
                            R.layout.sem_language_picker_footer, (ViewGroup) null, false);
            footer.setBackgroundColor(this.mSubheaderColor);
            list.addFooterView(footer, null, false);
        }
    }

    @Override // android.app.Fragment
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id) {
            case 16908332:
                getFragmentManager().popBackStack();
                return true;
            case R.id.locale_search_menu /* 16909256 */:
                return false;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
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
        if (this.mSearchView != null) {
            this.mPreviousSearchHadFocus = this.mSearchView.hasFocus();
            this.mPreviousSearch = this.mSearchView.getQuery();
        } else {
            this.mPreviousSearchHadFocus = false;
            this.mPreviousSearch = null;
        }
        ListView list = getListView();
        View firstChild = list.getChildAt(0);
        this.mFirstVisiblePosition = list.getFirstVisiblePosition();
        this.mTopDistance = firstChild != null ? firstChild.getTop() - list.getPaddingTop() : 0;
    }

    @Override // android.app.ListFragment
    public void onListItemClick(ListView parent, View v, int position, long id) {
        LocalePickerWithRegion selector;
        LocaleStore.LocaleInfo locale =
                (LocaleStore.LocaleInfo) parent.getAdapter().getItem(position);
        boolean isSystemLocale = locale.isSystemLocale();
        boolean isRegionLocale = locale.getParent() != null;
        boolean mayHaveDifferentNumberingSystem = locale.hasNumberingSystems();
        if (isSystemLocale
                || ((isRegionLocale && !mayHaveDifferentNumberingSystem)
                        || this.mIsNumberingSystem)) {
            if (this.mListener != null) {
                this.mListener.onLocaleSelected(locale);
            }
            returnToParentFrame();
            return;
        }
        if (mayHaveDifferentNumberingSystem) {
            selector =
                    createNumberingSystemPicker(
                            this.mListener,
                            locale,
                            this.mTranslatedOnly,
                            this.mOnActionExpandListener,
                            this.mLocalePickerCollector);
        } else {
            selector =
                    createCountryPicker(
                            this.mListener,
                            locale,
                            this.mTranslatedOnly,
                            this.mOnActionExpandListener,
                            this.mLocalePickerCollector,
                            this.mChangeDisplayName);
            if (this.mSearchView != null) {
                this.mSearchView.clearFocus();
            }
        }
        if (selector != null) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(getId(), selector)
                    .addToBackStack(null)
                    .commit();
        } else {
            returnToParentFrame();
        }
    }

    @Override // android.app.Fragment
    public void onPrepareOptionsMenu(Menu menu) {
        if (this.mLocalePickerCollector.hasSpecificPackageName()
                && this.mOnActionExpandListener != null) {
            return;
        }
        super.onPrepareOptionsMenu(menu);
    }

    @Override // android.app.Fragment
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if ((!this.mLocalePickerCollector.hasSpecificPackageName()
                        || this.mOnActionExpandListener == null)
                && this.mParentLocale == null) {
            inflater.inflate(R.menu.language_selection_list, menu);
            MenuItem searchMenuItem = menu.findItem(R.id.locale_search_menu);
            this.mSearchView = new SearchView(getContext());
            searchMenuItem.setActionView(this.mSearchView);
            int id = getResources().getIdentifier("android:id/search_plate", null, null);
            LinearLayout searchviewView = (LinearLayout) this.mSearchView.findViewById(id);
            if (searchviewView != null) {
                searchviewView.setPadding(
                        0,
                        searchviewView.getPaddingTop(),
                        searchviewView.getPaddingRight(),
                        searchviewView.getPaddingBottom());
            }
            searchMenuItem.setShowAsAction(2);
            if (this.mOnActionExpandListener != null) {
                searchMenuItem.setOnActionExpandListener(this.mOnActionExpandListener);
            }
            if (this.mSearchView != null) {
                this.mSearchView.setQueryHint(getText(R.string.search_language_hint));
                this.mSearchView.setOnQueryTextListener(this);
                if (TextUtils.isEmpty(this.mPreviousSearch)) {
                    this.mSearchView.setQuery(null, false);
                } else {
                    searchMenuItem.expandActionView();
                    this.mSearchView.setIconified(false);
                    this.mSearchView.setActivated(true);
                    if (this.mPreviousSearchHadFocus) {
                        this.mSearchView.requestFocus();
                    }
                    this.mSearchView.setQuery(this.mPreviousSearch, true);
                }
            }
            getListView().setSelectionFromTop(this.mFirstVisiblePosition, this.mTopDistance);
        }
    }

    @Override // android.widget.SearchView.OnQueryTextListener
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override // android.widget.SearchView.OnQueryTextListener
    public boolean onQueryTextChange(String newText) {
        if (this.mAdapter != null) {
            this.mAdapter.getFilter().filter(newText);
            return false;
        }
        return false;
    }

    public void setOnActionExpandListener(MenuItem.OnActionExpandListener onActionExpandListener) {
        this.mOnActionExpandListener = onActionExpandListener;
    }

    @Override // android.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int marginTop =
                getResources()
                        .getDimensionPixelSize(R.dimen.sem_locale_picker_action_bar_margin_top);
        int idSearchBar = getResources().getIdentifier("android:id/search_bar", null, null);
        LinearLayout searchViewBar = (LinearLayout) this.mSearchView.findViewById(idSearchBar);
        if (searchViewBar != null) {
            LinearLayout.LayoutParams lp =
                    (LinearLayout.LayoutParams) searchViewBar.getLayoutParams();
            lp.setMargins(0, marginTop, 0, 0);
            searchViewBar.setLayoutParams(lp);
        }
    }
}
