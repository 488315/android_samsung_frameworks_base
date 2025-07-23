package com.android.internal.app;

import android.content.Context;
import android.content.res.Configuration;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.internal.R;
import com.android.internal.app.LocaleHelper;
import com.android.internal.app.LocaleStore;
import com.samsung.android.feature.SemCscFeature;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

/* loaded from: classes5.dex */
public class SuggestedLocaleAdapter extends BaseAdapter implements Filterable {
    protected static final int APP_LANGUAGE_PICKER_TYPE_COUNT = 6;
    protected static final int MIN_REGIONS_FOR_SUGGESTIONS = 6;
    protected static final int SYSTEM_LANGUAGE_TYPE_COUNT = 3;
    protected static final int SYSTEM_LANGUAGE_WITHOUT_HEADER_TYPE_COUNT = 1;
    protected static final int TYPE_CURRENT_LOCALE = 4;
    protected static final int TYPE_HEADER_ALL_OTHERS = 1;
    protected static final int TYPE_HEADER_SUGGESTED = 0;
    protected static final int TYPE_HEADER_SUGGESTED_SEC = 3;
    protected static final int TYPE_LOCALE = 2;
    protected static final int TYPE_SYSTEM_LANGUAGE_FOR_APP_LANGUAGE_PICKER = 5;
    private String mAppPackageName;
    protected int mChangeDisplayName;
    protected Context mContextOverride;
    protected final boolean mCountryMode;
    protected Locale mDisplayLocale;
    private boolean mHasSpecificAppPackageName;
    protected LayoutInflater mInflater;
    protected boolean mIsNumberingMode;
    private boolean mIsShowAll;
    protected ArrayList<LocaleStore.LocaleInfo> mLocaleOptions;
    private ArrayList<LocaleStore.LocaleInfo> mLocaleOptionsforSecSuggested;
    private ArrayList<LocaleStore.LocaleInfo> mLocaleOptionsforShowAll;
    protected ArrayList<LocaleStore.LocaleInfo> mOriginalLocaleOptions;
    private int mSecSuggestionCount;
    private int mSubheaderColor;
    protected int mSuggestionCount;

    @Override // android.widget.BaseAdapter, android.widget.ListAdapter
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public SuggestedLocaleAdapter(Set<LocaleStore.LocaleInfo> set, boolean z) {
        this(set, z, false);
    }

    public SuggestedLocaleAdapter(Set<LocaleStore.LocaleInfo> set, boolean z, boolean z2) {
        this(set, z, z2, 0);
    }

    public SuggestedLocaleAdapter(Set<LocaleStore.LocaleInfo> set, boolean z, boolean z2, int i) {
        this.mDisplayLocale = null;
        this.mContextOverride = null;
        this.mCountryMode = z;
        this.mLocaleOptions = new ArrayList<>(set.size());
        this.mHasSpecificAppPackageName = z2;
        this.mChangeDisplayName = i;
        this.mIsShowAll = false;
        this.mLocaleOptionsforSecSuggested = new ArrayList<>(set.size());
        this.mLocaleOptionsforShowAll = new ArrayList<>(set.size());
        for (LocaleStore.LocaleInfo localeInfo : set) {
            if (localeInfo.isSuggested()) {
                this.mSuggestionCount++;
            } else if (!this.mCountryMode && localeInfo.isSecSuggested()) {
                this.mSecSuggestionCount++;
            }
            if (this.mCountryMode) {
                this.mLocaleOptions.add(localeInfo);
            } else if (localeInfo.isSuggested() || localeInfo.isSecSuggested()) {
                this.mLocaleOptionsforSecSuggested.add(localeInfo);
            }
            if (this.mHasSpecificAppPackageName) {
                this.mLocaleOptionsforShowAll.add(localeInfo);
            } else if (!localeInfo.isSecSuggested() && !localeInfo.isSuggested()) {
                this.mLocaleOptionsforShowAll.add(localeInfo);
            }
        }
        if (this.mCountryMode) {
            return;
        }
        this.mLocaleOptions.addAll(this.mLocaleOptionsforSecSuggested);
    }

    public void setNumberingSystemMode(boolean z) {
        this.mIsNumberingMode = z;
    }

    public boolean getIsForNumberingSystem() {
        return this.mIsNumberingMode;
    }

    @Override // android.widget.BaseAdapter, android.widget.ListAdapter
    public boolean isEnabled(int i) {
        return getItemViewType(i) == 2 || getItemViewType(i) == 5 || getItemViewType(i) == 4;
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getItemViewType(int i) {
        if (!showHeaders() && !showSecHeaders()) {
            if (i == 0) {
                return 1;
            }
            LocaleStore.LocaleInfo localeInfo = (LocaleStore.LocaleInfo) getItem(i);
            if (localeInfo.isSystemLocale()) {
                return 5;
            }
            return localeInfo.isAppCurrentLocale() ? 4 : 2;
        }
        if (!showHeaders()) {
            if (showSecHeaders()) {
                if (i == 0) {
                    return 3;
                }
                if (i == this.mSecSuggestionCount + 1) {
                    return 1;
                }
            }
            LocaleStore.LocaleInfo localeInfo2 = (LocaleStore.LocaleInfo) getItem(i);
            if (localeInfo2.isSystemLocale()) {
                return 5;
            }
            return localeInfo2.isAppCurrentLocale() ? 4 : 2;
        }
        if (i == 0) {
            return 0;
        }
        int i2 = this.mSecSuggestionCount;
        if (i2 > 0) {
            int i3 = this.mSuggestionCount;
            if (i == i3 + 1) {
                return 3;
            }
            if (i == i3 + 1 + i2 + 1) {
                return 1;
            }
        } else if (i == this.mSuggestionCount + 1) {
            return 1;
        }
        LocaleStore.LocaleInfo localeInfo3 = (LocaleStore.LocaleInfo) getItem(i);
        if (localeInfo3.isSystemLocale()) {
            return 5;
        }
        return localeInfo3.isAppCurrentLocale() ? 4 : 2;
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getViewTypeCount() {
        return (this.mHasSpecificAppPackageName && showHeaders()) ? 6 : 4;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.mLocaleOptions.size() + getHeadersOffsetCount();
    }

    private int getHeadersOffsetCount() {
        if (!showHeaders()) {
            return (!showSecHeaders() || this.mSecSuggestionCount == this.mLocaleOptions.size()) ? 1 : 2;
        }
        if (!showSecHeaders() || this.mSuggestionCount + this.mSecSuggestionCount >= this.mLocaleOptions.size()) {
            return (showSecHeaders() || this.mSuggestionCount != this.mLocaleOptions.size()) ? 2 : 1;
        }
        return 3;
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0029, code lost:
    
        if (r6 > r5.mSuggestionCount) goto L26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0034, code lost:
    
        if (r6 > r5.mSecSuggestionCount) goto L26;
     */
    @Override // android.widget.Adapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Object getItem(int r6) {
        /*
            r5 = this;
            boolean r0 = r5.showHeaders()
            r1 = -2
            r2 = -1
            if (r0 == 0) goto L2c
            int r0 = r5.mSuggestionCount
            java.util.ArrayList<com.android.internal.app.LocaleStore$LocaleInfo> r3 = r5.mLocaleOptions
            int r3 = r3.size()
            if (r0 != r3) goto L13
            goto L37
        L13:
            int r0 = r5.mSecSuggestionCount
            if (r0 <= 0) goto L27
            int r3 = r5.mSuggestionCount
            if (r6 <= r3) goto L22
            int r4 = r3 + r0
            int r4 = r4 + 1
            if (r6 > r4) goto L22
            goto L38
        L22:
            int r3 = r3 + r0
            if (r6 <= r3) goto L37
            r1 = -3
            goto L38
        L27:
            int r0 = r5.mSuggestionCount
            if (r6 <= r0) goto L37
            goto L38
        L2c:
            boolean r0 = r5.showSecHeaders()
            if (r0 == 0) goto L37
            int r0 = r5.mSecSuggestionCount
            if (r6 <= r0) goto L37
            goto L38
        L37:
            r1 = r2
        L38:
            java.util.ArrayList<com.android.internal.app.LocaleStore$LocaleInfo> r5 = r5.mLocaleOptions
            int r6 = r6 + r1
            java.lang.Object r5 = r5.get(r6)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.internal.app.SuggestedLocaleAdapter.getItem(int):java.lang.Object");
    }

    private boolean isHeaderPosition(int i) {
        if (showHeaders()) {
            return i == 0 || i == this.mSuggestionCount + 1;
        }
        return false;
    }

    public void setDisplayLocale(Context context, Locale locale) {
        if (locale == null) {
            this.mDisplayLocale = null;
            this.mContextOverride = null;
        } else {
            if (locale.equals(this.mDisplayLocale)) {
                return;
            }
            this.mDisplayLocale = locale;
            Configuration configuration = new Configuration();
            configuration.setLocale(locale);
            this.mContextOverride = context.createConfigurationContext(configuration);
        }
    }

    protected void setTextTo(TextView textView, int i) {
        Context context = this.mContextOverride;
        if (context == null) {
            textView.setText(i);
        } else {
            textView.lambda$setTextAsync$0(context.getText(i));
        }
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView textView;
        if (view == null && this.mInflater == null) {
            this.mInflater = LayoutInflater.from(viewGroup.getContext());
        }
        int itemViewType = getItemViewType(i);
        View newViewIfNeeded = getNewViewIfNeeded(view, viewGroup, itemViewType, i);
        if (itemViewType == 0 || itemViewType == 1 || itemViewType == 3) {
            TextView textView2 = (TextView) newViewIfNeeded.findViewById(R.id.section_header);
            if (itemViewType == 0) {
                setTextTo(textView2, R.string.language_picker_section_suggested);
            } else if (itemViewType == 3) {
                if (SemCscFeature.getInstance().getBoolean("CscFeature_Common_ReplaceSecBrandAsGalaxy", false)) {
                    setTextTo(textView2, R.string.language_picker_section_suggested_sec_jpn);
                } else {
                    setTextTo(textView2, R.string.language_picker_section_suggested_sec);
                }
            } else if (this.mCountryMode) {
                setTextTo(textView2, R.string.region_picker_section_all);
            } else if (!this.mHasSpecificAppPackageName) {
                if (getCount() > 1) {
                    setTextTo(textView2, R.string.language_picker_section_show_other);
                }
            } else {
                setTextTo(textView2, R.string.language_picker_section_show_all);
            }
            Locale locale = this.mDisplayLocale;
            if (locale == null) {
                locale = Locale.getDefault();
            }
            textView2.setTextLocale(locale);
            return newViewIfNeeded;
        }
        if (itemViewType == 4) {
            newViewIfNeeded.findViewById(R.id.language_picker_item).findViewById(R.id.divider).setVisibility(8);
            newViewIfNeeded.findViewById(R.id.external_divider).setVisibility(0);
            updateTextView(newViewIfNeeded, (TextView) newViewIfNeeded.findViewById(R.id.language_picker_item).findViewById(R.id.locale), i);
            return newViewIfNeeded;
        }
        if (itemViewType == 5) {
            View findViewById = newViewIfNeeded.findViewById(R.id.external_divider);
            if (findViewById != null) {
                newViewIfNeeded.findViewById(R.id.language_picker_item).findViewById(R.id.divider).setVisibility(8);
                findViewById.setVisibility(0);
            }
            if (((LocaleStore.LocaleInfo) getItem(i)).isAppCurrentLocale()) {
                textView = (TextView) newViewIfNeeded.findViewById(R.id.language_picker_item).findViewById(R.id.locale);
            } else {
                textView = (TextView) newViewIfNeeded.findViewById(R.id.locale);
            }
            textView.setText(R.string.system_locale_title);
            textView.setContentDescription(newViewIfNeeded.getContext().getResources().getString(R.string.system_locale_title));
            return newViewIfNeeded;
        }
        if (((LocaleStore.LocaleInfo) getItem(i)) == null) {
            throw new NullPointerException("Non header locale cannot be null.");
        }
        updateTextView(newViewIfNeeded, (TextView) newViewIfNeeded.findViewById(R.id.locale), i);
        return newViewIfNeeded;
    }

    private View getNewViewIfNeeded(View view, ViewGroup viewGroup, int i, int i2) {
        boolean z;
        int i3;
        int i4;
        int i5 = 0;
        if (i == 0 || i == 1 || i == 3) {
            return (!(view instanceof TextView) || view.findViewById(R.id.section_header) == null) ? new SemLocalePickerItemView(viewGroup.getContext(), 0, this.mInflater) : view;
        }
        if (i == 4) {
            if ((view instanceof LinearLayout) && view.findViewById(R.id.external_divider) != null) {
                return view;
            }
            View inflate = this.mInflater.inflate(R.layout.sec_app_language_picker_current_locale_item, viewGroup, false);
            inflate.findViewById(R.id.language_picker_item).findViewById(R.id.divider).setVisibility(8);
            inflate.findViewById(R.id.external_divider).setVisibility(0);
            semApplyRoundedCorner(inflate, i2);
            return inflate;
        }
        if (i == 5) {
            if (((LocaleStore.LocaleInfo) getItem(i2)).isAppCurrentLocale()) {
                if ((view instanceof LinearLayout) && view.findViewById(R.id.language_picker_item) != null) {
                    return view;
                }
                View inflate2 = this.mInflater.inflate(R.layout.sec_app_language_picker_current_locale_item, viewGroup, false);
                inflate2.findViewById(R.id.language_picker_item).findViewById(R.id.divider).setVisibility(8);
                inflate2.findViewById(R.id.external_divider).setVisibility(0);
                semApplyRoundedCorner(inflate2, i2);
                return inflate2;
            }
            if ((view instanceof TextView) && view.findViewById(R.id.locale) != null) {
                return view;
            }
            View inflate3 = this.mInflater.inflate(R.layout.language_picker_item, viewGroup, false);
            semApplyRoundedCorner(inflate3, i2);
            return inflate3;
        }
        if (!(view instanceof ViewGroup) || view.findViewById(R.id.locale) == null || view.findViewById(R.id.divider) == null) {
            view = new SemLocalePickerItemView(viewGroup.getContext(), 1, this.mInflater);
        }
        TextView textView = (TextView) view.findViewById(R.id.locale);
        LocaleStore.LocaleInfo localeInfo = (LocaleStore.LocaleInfo) getItem(i2);
        textView.lambda$setTextAsync$0(localeInfo.getLabel(this.mCountryMode, this.mChangeDisplayName));
        textView.setTextLocale(localeInfo.getLocale());
        textView.setContentDescription(localeInfo.getContentDescription(this.mCountryMode));
        View findViewById = view.findViewById(R.id.divider);
        findViewById.setVisibility(0);
        int layoutDirectionFromLocale = TextUtils.getLayoutDirectionFromLocale(localeInfo.getLocale());
        view.setLayoutDirection(layoutDirectionFromLocale);
        textView.setTextDirection(layoutDirectionFromLocale != 1 ? 3 : 4);
        int headersOffsetCount = getHeadersOffsetCount();
        if (i2 == 1 || (((i4 = this.mSuggestionCount) > 0 && i2 == i4 + 2) || i2 == i4 + this.mSecSuggestionCount + headersOffsetCount)) {
            z = true;
            i5 = 3;
        } else {
            z = false;
        }
        if (i2 == getCount() - 1 || (((i3 = this.mSuggestionCount) > 0 && i2 == i3) || (headersOffsetCount > 1 && i2 == ((i3 + this.mSecSuggestionCount) + headersOffsetCount) - 2))) {
            i5 = z ? 15 : 12;
            findViewById.setVisibility(8);
        }
        view.semSetRoundedCorners(i5);
        if (i5 != 0) {
            view.semSetRoundedCornerColor(i5, this.mSubheaderColor);
        }
        return view;
    }

    protected boolean showSecHeaders() {
        return (this.mCountryMode || this.mSecSuggestionCount == 0) ? false : true;
    }

    private void semApplyRoundedCorner(View view, int i) {
        int layoutDirectionFromLocale;
        boolean z;
        int i2;
        int i3;
        TextView textView = (TextView) view.findViewById(R.id.locale);
        LocaleStore.LocaleInfo localeInfo = (LocaleStore.LocaleInfo) getItem(i);
        textView.lambda$setTextAsync$0(localeInfo.getLabel(this.mCountryMode, this.mChangeDisplayName));
        textView.setTextLocale(localeInfo.getLocale());
        textView.setContentDescription(localeInfo.getContentDescription(this.mCountryMode));
        View findViewById = view.findViewById(R.id.divider);
        int i4 = 0;
        findViewById.setVisibility(0);
        if ("und".equalsIgnoreCase(localeInfo.getFullNameNative())) {
            layoutDirectionFromLocale = TextUtils.getLayoutDirectionFromLocale(Locale.getDefault());
        } else {
            layoutDirectionFromLocale = TextUtils.getLayoutDirectionFromLocale(localeInfo.getLocale());
        }
        view.setLayoutDirection(layoutDirectionFromLocale);
        textView.setTextDirection(layoutDirectionFromLocale == 1 ? 4 : 3);
        if (i == 1 || ((i3 = this.mSuggestionCount) > 0 && i == i3 + 2)) {
            i4 = 3;
            z = true;
        } else {
            z = false;
        }
        if (i == getCount() - 1 || ((i2 = this.mSuggestionCount) > 0 && i == i2)) {
            i4 = z ? 15 : 12;
            findViewById.setVisibility(8);
        }
        view.semSetRoundedCorners(i4);
        if (i4 != 0) {
            view.semSetRoundedCornerColor(i4, this.mSubheaderColor);
        }
    }

    private boolean showHeaders() {
        return this.mSuggestionCount != 0;
    }

    public void showAllItems() {
        if (this.mHasSpecificAppPackageName) {
            this.mLocaleOptions.clear();
        }
        this.mLocaleOptions.addAll(this.mLocaleOptionsforShowAll);
        if (this.mHasSpecificAppPackageName) {
            this.mIsShowAll = true;
        }
        notifyDataSetChanged();
    }

    public void showSamsungSuggestedItems() {
        this.mLocaleOptions.clear();
        this.mLocaleOptions.addAll(this.mLocaleOptionsforSecSuggested);
        this.mIsShowAll = false;
        notifyDataSetChanged();
    }

    public void setShowAll(boolean z) {
        this.mIsShowAll = z;
    }

    public boolean getShowAll() {
        return this.mIsShowAll;
    }

    public int getSecSuggestionCount() {
        return this.mSecSuggestionCount;
    }

    public void setSecSuggestionCount(int i) {
        this.mSecSuggestionCount = i;
    }

    public void sort(LocaleHelper.LocaleInfoComparator localeInfoComparator) {
        Collections.sort(this.mLocaleOptionsforShowAll, localeInfoComparator);
    }

    public void sortForSecSuggested(LocaleHelper.LocaleInfoComparator localeInfoComparator) {
        Collections.sort(this.mLocaleOptions, localeInfoComparator);
        Collections.sort(this.mLocaleOptionsforSecSuggested, localeInfoComparator);
    }

    public void updateTheme(LayoutInflater layoutInflater, int i) {
        this.mInflater = layoutInflater;
        this.mSubheaderColor = i;
    }

    class FilterByNativeAndUiNames extends Filter {
        FilterByNativeAndUiNames() {
        }

        @Override // android.widget.Filter
        protected Filter.FilterResults performFiltering(CharSequence charSequence) {
            Filter.FilterResults filterResults = new Filter.FilterResults();
            if (SuggestedLocaleAdapter.this.mOriginalLocaleOptions == null) {
                SuggestedLocaleAdapter.this.mOriginalLocaleOptions = new ArrayList<>(SuggestedLocaleAdapter.this.mLocaleOptions);
            }
            ArrayList arrayList = new ArrayList(SuggestedLocaleAdapter.this.mOriginalLocaleOptions);
            if (charSequence == null || charSequence.length() == 0) {
                filterResults.values = arrayList;
                filterResults.count = arrayList.size();
                return filterResults;
            }
            Locale locale = Locale.getDefault();
            String normalizeForSearch = LocaleHelper.normalizeForSearch(charSequence.toString(), locale);
            int size = arrayList.size();
            ArrayList arrayList2 = new ArrayList();
            for (int i = 0; i < size; i++) {
                LocaleStore.LocaleInfo localeInfo = (LocaleStore.LocaleInfo) arrayList.get(i);
                String normalizeForSearch2 = LocaleHelper.normalizeForSearch(localeInfo.getFullNameInUiLanguage(), locale);
                if (wordMatches(LocaleHelper.normalizeForSearch(localeInfo.getFullNameNative(), locale), normalizeForSearch) || wordMatches(normalizeForSearch2, normalizeForSearch)) {
                    arrayList2.add(localeInfo);
                }
            }
            filterResults.values = arrayList2;
            filterResults.count = arrayList2.size();
            return filterResults;
        }

        boolean wordMatches(String str, String str2) {
            if (str.startsWith(str2)) {
                return true;
            }
            for (String str3 : str.split(" ")) {
                if (str3.startsWith(str2)) {
                    return true;
                }
            }
            return false;
        }

        @Override // android.widget.Filter
        protected void publishResults(CharSequence charSequence, Filter.FilterResults filterResults) {
            SuggestedLocaleAdapter.this.mLocaleOptions = (ArrayList) filterResults.values;
            SuggestedLocaleAdapter.this.mSuggestionCount = 0;
            SuggestedLocaleAdapter.this.mSecSuggestionCount = 0;
            Iterator<LocaleStore.LocaleInfo> it = SuggestedLocaleAdapter.this.mLocaleOptions.iterator();
            while (it.hasNext()) {
                LocaleStore.LocaleInfo next = it.next();
                if (next.isSuggested()) {
                    SuggestedLocaleAdapter.this.mSuggestionCount++;
                } else if (!SuggestedLocaleAdapter.this.mCountryMode && next.isSecSuggested() && !SuggestedLocaleAdapter.this.mIsShowAll) {
                    SuggestedLocaleAdapter.this.mSecSuggestionCount++;
                }
            }
            if (filterResults.count > 0) {
                SuggestedLocaleAdapter.this.notifyDataSetChanged();
            } else {
                SuggestedLocaleAdapter.this.notifyDataSetInvalidated();
            }
        }
    }

    @Override // android.widget.Filterable
    public Filter getFilter() {
        return new FilterByNativeAndUiNames();
    }

    private void updateTextView(View view, TextView textView, int i) {
        LocaleStore.LocaleInfo localeInfo = (LocaleStore.LocaleInfo) getItem(i);
        textView.lambda$setTextAsync$0(this.mIsNumberingMode ? localeInfo.getNumberingSystem() : localeInfo.getLabel(this.mCountryMode, this.mChangeDisplayName));
        textView.setTextLocale(localeInfo.getLocale());
        textView.setContentDescription(this.mIsNumberingMode ? localeInfo.getNumberingSystem() : localeInfo.getContentDescription(this.mCountryMode));
        if (this.mCountryMode) {
            int layoutDirectionFromLocale = TextUtils.getLayoutDirectionFromLocale(localeInfo.getParent());
            view.setLayoutDirection(layoutDirectionFromLocale);
            textView.setTextDirection(layoutDirectionFromLocale == 1 ? 4 : 3);
        }
    }
}
