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

    public SuggestedLocaleAdapter(Set<LocaleStore.LocaleInfo> localeOptions, boolean countryMode) {
        this(localeOptions, countryMode, false);
    }

    public SuggestedLocaleAdapter(Set<LocaleStore.LocaleInfo> localeOptions, boolean countryMode, boolean hasSpecificAppPackageName) {
        this(localeOptions, countryMode, hasSpecificAppPackageName, 0);
    }

    public SuggestedLocaleAdapter(Set<LocaleStore.LocaleInfo> localeOptions, boolean countryMode, boolean hasSpecificAppPackageName, int changeDisplayName) {
        this.mDisplayLocale = null;
        this.mContextOverride = null;
        this.mCountryMode = countryMode;
        this.mLocaleOptions = new ArrayList<>(localeOptions.size());
        this.mHasSpecificAppPackageName = hasSpecificAppPackageName;
        this.mChangeDisplayName = changeDisplayName;
        this.mIsShowAll = false;
        this.mLocaleOptionsforSecSuggested = new ArrayList<>(localeOptions.size());
        this.mLocaleOptionsforShowAll = new ArrayList<>(localeOptions.size());
        for (LocaleStore.LocaleInfo li : localeOptions) {
            if (li.isSuggested()) {
                this.mSuggestionCount++;
            } else if (!this.mCountryMode && li.isSecSuggested()) {
                this.mSecSuggestionCount++;
            }
            if (this.mCountryMode) {
                this.mLocaleOptions.add(li);
            } else if (li.isSuggested() || li.isSecSuggested()) {
                this.mLocaleOptionsforSecSuggested.add(li);
            }
            if (this.mHasSpecificAppPackageName) {
                this.mLocaleOptionsforShowAll.add(li);
            } else if (!li.isSecSuggested() && !li.isSuggested()) {
                this.mLocaleOptionsforShowAll.add(li);
            }
        }
        if (!this.mCountryMode) {
            this.mLocaleOptions.addAll(this.mLocaleOptionsforSecSuggested);
        }
    }

    public void setNumberingSystemMode(boolean isNumberSystemMode) {
        this.mIsNumberingMode = isNumberSystemMode;
    }

    public boolean getIsForNumberingSystem() {
        return this.mIsNumberingMode;
    }

    @Override // android.widget.BaseAdapter, android.widget.ListAdapter
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override // android.widget.BaseAdapter, android.widget.ListAdapter
    public boolean isEnabled(int position) {
        return getItemViewType(position) == 2 || getItemViewType(position) == 5 || getItemViewType(position) == 4;
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getItemViewType(int position) {
        if (!showHeaders() && !showSecHeaders()) {
            if (position == 0) {
                return 1;
            }
            LocaleStore.LocaleInfo item = (LocaleStore.LocaleInfo) getItem(position);
            if (item.isSystemLocale()) {
                return 5;
            }
            return item.isAppCurrentLocale() ? 4 : 2;
        }
        if (showHeaders()) {
            if (position == 0) {
                return 0;
            }
            if (this.mSecSuggestionCount > 0) {
                if (position == this.mSuggestionCount + 1) {
                    return 3;
                }
                if (position == this.mSuggestionCount + 1 + this.mSecSuggestionCount + 1) {
                    return 1;
                }
            } else if (position == this.mSuggestionCount + 1) {
                return 1;
            }
            LocaleStore.LocaleInfo item2 = (LocaleStore.LocaleInfo) getItem(position);
            if (item2.isSystemLocale()) {
                return 5;
            }
            return item2.isAppCurrentLocale() ? 4 : 2;
        }
        if (showSecHeaders()) {
            if (position == 0) {
                return 3;
            }
            if (position == this.mSecSuggestionCount + 1) {
                return 1;
            }
        }
        LocaleStore.LocaleInfo item3 = (LocaleStore.LocaleInfo) getItem(position);
        if (item3.isSystemLocale()) {
            return 5;
        }
        return item3.isAppCurrentLocale() ? 4 : 2;
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getViewTypeCount() {
        if (this.mHasSpecificAppPackageName && showHeaders()) {
            return 6;
        }
        return 4;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        int offset = getHeadersOffsetCount();
        return this.mLocaleOptions.size() + offset;
    }

    private int getHeadersOffsetCount() {
        if (showHeaders()) {
            if (showSecHeaders() && this.mSuggestionCount + this.mSecSuggestionCount < this.mLocaleOptions.size()) {
                int offset = 0 + 3;
                return offset;
            }
            if (showSecHeaders() || this.mSuggestionCount != this.mLocaleOptions.size()) {
                int offset2 = 0 + 2;
                return offset2;
            }
            int offset3 = 0 + 1;
            return offset3;
        }
        if (showSecHeaders()) {
            if (this.mSecSuggestionCount != this.mLocaleOptions.size()) {
                int offset4 = 0 + 2;
                return offset4;
            }
            int offset5 = 0 + 1;
            return offset5;
        }
        int offset6 = 0 + 1;
        return offset6;
    }

    @Override // android.widget.Adapter
    public Object getItem(int position) {
        int offset;
        if (showHeaders()) {
            if (this.mSuggestionCount == this.mLocaleOptions.size()) {
                offset = -1;
            } else if (this.mSecSuggestionCount > 0) {
                if (position > this.mSuggestionCount && position <= this.mSuggestionCount + this.mSecSuggestionCount + 1) {
                    offset = -2;
                } else if (position > this.mSuggestionCount + this.mSecSuggestionCount) {
                    offset = -3;
                } else {
                    offset = -1;
                }
            } else {
                offset = position <= this.mSuggestionCount ? -1 : -2;
            }
        } else if (showSecHeaders()) {
            offset = position <= this.mSecSuggestionCount ? -1 : -2;
        } else {
            offset = -1;
        }
        return this.mLocaleOptions.get(position + offset);
    }

    private boolean isHeaderPosition(int position) {
        return showHeaders() && (position == 0 || position == this.mSuggestionCount + 1);
    }

    @Override // android.widget.Adapter
    public long getItemId(int position) {
        return position;
    }

    public void setDisplayLocale(Context context, Locale locale) {
        if (locale == null) {
            this.mDisplayLocale = null;
            this.mContextOverride = null;
        } else if (!locale.equals(this.mDisplayLocale)) {
            this.mDisplayLocale = locale;
            Configuration configOverride = new Configuration();
            configOverride.setLocale(locale);
            this.mContextOverride = context.createConfigurationContext(configOverride);
        }
    }

    protected void setTextTo(TextView textView, int resId) {
        if (this.mContextOverride == null) {
            textView.setText(resId);
        } else {
            textView.lambda$setTextAsync$0(this.mContextOverride.getText(resId));
        }
    }

    @Override // android.widget.Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView title;
        if (convertView == null && this.mInflater == null) {
            this.mInflater = LayoutInflater.from(parent.getContext());
        }
        int itemType = getItemViewType(position);
        View itemView = getNewViewIfNeeded(convertView, parent, itemType, position);
        switch (itemType) {
            case 0:
            case 1:
            case 3:
                TextView textView = (TextView) itemView.findViewById(R.id.section_header);
                if (itemType == 0) {
                    setTextTo(textView, R.string.language_picker_section_suggested);
                } else if (itemType == 3) {
                    if (SemCscFeature.getInstance().getBoolean("CscFeature_Common_ReplaceSecBrandAsGalaxy", false)) {
                        setTextTo(textView, R.string.language_picker_section_suggested_sec_jpn);
                    } else {
                        setTextTo(textView, R.string.language_picker_section_suggested_sec);
                    }
                } else if (this.mCountryMode) {
                    setTextTo(textView, R.string.region_picker_section_all);
                } else if (!this.mHasSpecificAppPackageName) {
                    if (getCount() > 1) {
                        setTextTo(textView, R.string.language_picker_section_show_other);
                    }
                } else {
                    setTextTo(textView, R.string.language_picker_section_show_all);
                }
                textView.setTextLocale(this.mDisplayLocale != null ? this.mDisplayLocale : Locale.getDefault());
                return itemView;
            case 2:
            default:
                updateTextView(itemView, (TextView) itemView.findViewById(R.id.locale), position);
                return itemView;
            case 4:
                itemView.findViewById(R.id.language_picker_item).findViewById(R.id.divider).setVisibility(8);
                itemView.findViewById(R.id.external_divider).setVisibility(0);
                updateTextView(itemView, (TextView) itemView.findViewById(R.id.language_picker_item).findViewById(R.id.locale), position);
                return itemView;
            case 5:
                View externalDivider = itemView.findViewById(R.id.external_divider);
                if (externalDivider != null) {
                    itemView.findViewById(R.id.language_picker_item).findViewById(R.id.divider).setVisibility(8);
                    externalDivider.setVisibility(0);
                }
                if (((LocaleStore.LocaleInfo) getItem(position)).isAppCurrentLocale()) {
                    title = (TextView) itemView.findViewById(R.id.language_picker_item).findViewById(R.id.locale);
                } else {
                    title = (TextView) itemView.findViewById(R.id.locale);
                }
                title.setText(R.string.system_locale_title);
                title.setContentDescription(itemView.getContext().getResources().getString(R.string.system_locale_title));
                return itemView;
        }
    }

    private View getNewViewIfNeeded(View convertView, ViewGroup parent, int itemType, int position) {
        int i;
        View updatedView = convertView;
        switch (itemType) {
            case 0:
            case 1:
            case 3:
                boolean shouldReuseView = (convertView instanceof TextView) && convertView.findViewById(R.id.section_header) != null;
                if (!shouldReuseView) {
                    break;
                }
                break;
            case 2:
            default:
                boolean shouldReuseView2 = (!(convertView instanceof ViewGroup) || convertView.findViewById(R.id.locale) == null || convertView.findViewById(R.id.divider) == null) ? false : true;
                if (!shouldReuseView2) {
                    updatedView = new SemLocalePickerItemView(parent.getContext(), 1, this.mInflater);
                }
                TextView text = (TextView) updatedView.findViewById(R.id.locale);
                LocaleStore.LocaleInfo item = (LocaleStore.LocaleInfo) getItem(position);
                text.lambda$setTextAsync$0(item.getLabel(this.mCountryMode, this.mChangeDisplayName));
                text.setTextLocale(item.getLocale());
                text.setContentDescription(item.getContentDescription(this.mCountryMode));
                View divder = updatedView.findViewById(R.id.divider);
                divder.setVisibility(0);
                int layoutDir = TextUtils.getLayoutDirectionFromLocale(item.getLocale());
                updatedView.setLayoutDirection(layoutDir);
                if (layoutDir == 1) {
                    i = 4;
                } else {
                    i = 3;
                }
                text.setTextDirection(i);
                boolean isTopCorner = false;
                int corners = 0;
                int headersOffsetCount = getHeadersOffsetCount();
                if (position == 1 || ((this.mSuggestionCount > 0 && position == this.mSuggestionCount + 2) || position == this.mSuggestionCount + this.mSecSuggestionCount + headersOffsetCount)) {
                    corners = 3;
                    isTopCorner = true;
                }
                if (position == getCount() - 1 || ((this.mSuggestionCount > 0 && position == this.mSuggestionCount) || (headersOffsetCount > 1 && position == ((this.mSuggestionCount + this.mSecSuggestionCount) + headersOffsetCount) - 2))) {
                    corners = isTopCorner ? 15 : 12;
                    divder.setVisibility(8);
                }
                updatedView.semSetRoundedCorners(corners);
                if (corners != 0) {
                    updatedView.semSetRoundedCornerColor(corners, this.mSubheaderColor);
                }
                break;
            case 4:
                boolean shouldReuseView3 = convertView instanceof LinearLayout;
                boolean shouldReuseView4 = shouldReuseView3 && convertView.findViewById(R.id.external_divider) != null;
                if (!shouldReuseView4) {
                    View updatedView2 = this.mInflater.inflate(R.layout.sec_app_language_picker_current_locale_item, parent, false);
                    updatedView2.findViewById(R.id.language_picker_item).findViewById(R.id.divider).setVisibility(8);
                    updatedView2.findViewById(R.id.external_divider).setVisibility(0);
                    semApplyRoundedCorner(updatedView2, position);
                    break;
                }
                break;
            case 5:
                if (((LocaleStore.LocaleInfo) getItem(position)).isAppCurrentLocale()) {
                    boolean shouldReuseView5 = (convertView instanceof LinearLayout) && convertView.findViewById(R.id.language_picker_item) != null;
                    if (!shouldReuseView5) {
                        View updatedView3 = this.mInflater.inflate(R.layout.sec_app_language_picker_current_locale_item, parent, false);
                        updatedView3.findViewById(R.id.language_picker_item).findViewById(R.id.divider).setVisibility(8);
                        updatedView3.findViewById(R.id.external_divider).setVisibility(0);
                        semApplyRoundedCorner(updatedView3, position);
                        break;
                    }
                } else {
                    boolean shouldReuseView6 = (convertView instanceof TextView) && convertView.findViewById(R.id.locale) != null;
                    if (!shouldReuseView6) {
                        View updatedView4 = this.mInflater.inflate(R.layout.language_picker_item, parent, false);
                        semApplyRoundedCorner(updatedView4, position);
                        break;
                    }
                }
                break;
        }
        return updatedView;
    }

    protected boolean showSecHeaders() {
        return (this.mCountryMode || this.mSecSuggestionCount == 0) ? false : true;
    }

    private void semApplyRoundedCorner(View updatedView, int position) {
        int layoutDir;
        int i;
        TextView text = (TextView) updatedView.findViewById(R.id.locale);
        LocaleStore.LocaleInfo item = (LocaleStore.LocaleInfo) getItem(position);
        text.lambda$setTextAsync$0(item.getLabel(this.mCountryMode, this.mChangeDisplayName));
        text.setTextLocale(item.getLocale());
        text.setContentDescription(item.getContentDescription(this.mCountryMode));
        View divder = updatedView.findViewById(R.id.divider);
        divder.setVisibility(0);
        if ("und".equalsIgnoreCase(item.getFullNameNative())) {
            layoutDir = TextUtils.getLayoutDirectionFromLocale(Locale.getDefault());
        } else {
            layoutDir = TextUtils.getLayoutDirectionFromLocale(item.getLocale());
        }
        updatedView.setLayoutDirection(layoutDir);
        if (layoutDir == 1) {
            i = 4;
        } else {
            i = 3;
        }
        text.setTextDirection(i);
        boolean isTopCorner = false;
        int corners = 0;
        if (position == 1 || (this.mSuggestionCount > 0 && position == this.mSuggestionCount + 2)) {
            corners = 3;
            isTopCorner = true;
        }
        if (position == getCount() - 1 || (this.mSuggestionCount > 0 && position == this.mSuggestionCount)) {
            corners = isTopCorner ? 15 : 12;
            divder.setVisibility(8);
        }
        updatedView.semSetRoundedCorners(corners);
        if (corners != 0) {
            updatedView.semSetRoundedCornerColor(corners, this.mSubheaderColor);
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

    public void setShowAll(boolean flag) {
        this.mIsShowAll = flag;
    }

    public boolean getShowAll() {
        return this.mIsShowAll;
    }

    public int getSecSuggestionCount() {
        return this.mSecSuggestionCount;
    }

    public void setSecSuggestionCount(int count) {
        this.mSecSuggestionCount = count;
    }

    public void sort(LocaleHelper.LocaleInfoComparator comp) {
        Collections.sort(this.mLocaleOptionsforShowAll, comp);
    }

    public void sortForSecSuggested(LocaleHelper.LocaleInfoComparator comp) {
        Collections.sort(this.mLocaleOptions, comp);
        Collections.sort(this.mLocaleOptionsforSecSuggested, comp);
    }

    public void updateTheme(LayoutInflater layoutInflater, int color) {
        this.mInflater = layoutInflater;
        this.mSubheaderColor = color;
    }

    class FilterByNativeAndUiNames extends Filter {
        FilterByNativeAndUiNames() {
        }

        @Override // android.widget.Filter
        protected Filter.FilterResults performFiltering(CharSequence prefix) {
            Filter.FilterResults results = new Filter.FilterResults();
            if (SuggestedLocaleAdapter.this.mOriginalLocaleOptions == null) {
                SuggestedLocaleAdapter.this.mOriginalLocaleOptions = new ArrayList<>(SuggestedLocaleAdapter.this.mLocaleOptions);
            }
            ArrayList<LocaleStore.LocaleInfo> values = new ArrayList<>(SuggestedLocaleAdapter.this.mOriginalLocaleOptions);
            if (prefix == null || prefix.length() == 0) {
                results.values = values;
                results.count = values.size();
            } else {
                Locale locale = Locale.getDefault();
                String prefixString = LocaleHelper.normalizeForSearch(prefix.toString(), locale);
                int count = values.size();
                ArrayList<LocaleStore.LocaleInfo> newValues = new ArrayList<>();
                for (int i = 0; i < count; i++) {
                    LocaleStore.LocaleInfo value = values.get(i);
                    String nameToCheck = LocaleHelper.normalizeForSearch(value.getFullNameInUiLanguage(), locale);
                    String nativeNameToCheck = LocaleHelper.normalizeForSearch(value.getFullNameNative(), locale);
                    if (wordMatches(nativeNameToCheck, prefixString) || wordMatches(nameToCheck, prefixString)) {
                        newValues.add(value);
                    }
                }
                results.values = newValues;
                results.count = newValues.size();
            }
            return results;
        }

        boolean wordMatches(String valueText, String prefixString) {
            if (valueText.startsWith(prefixString)) {
                return true;
            }
            String[] words = valueText.split(" ");
            for (String word : words) {
                if (word.startsWith(prefixString)) {
                    return true;
                }
            }
            return false;
        }

        @Override // android.widget.Filter
        protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
            SuggestedLocaleAdapter.this.mLocaleOptions = (ArrayList) results.values;
            SuggestedLocaleAdapter.this.mSuggestionCount = 0;
            SuggestedLocaleAdapter.this.mSecSuggestionCount = 0;
            Iterator<LocaleStore.LocaleInfo> it = SuggestedLocaleAdapter.this.mLocaleOptions.iterator();
            while (it.hasNext()) {
                LocaleStore.LocaleInfo li = it.next();
                if (li.isSuggested()) {
                    SuggestedLocaleAdapter.this.mSuggestionCount++;
                } else if (!SuggestedLocaleAdapter.this.mCountryMode && li.isSecSuggested() && !SuggestedLocaleAdapter.this.mIsShowAll) {
                    SuggestedLocaleAdapter.this.mSecSuggestionCount++;
                }
            }
            if (results.count > 0) {
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

    private void updateTextView(View convertView, TextView text, int position) {
        int i;
        LocaleStore.LocaleInfo item = (LocaleStore.LocaleInfo) getItem(position);
        text.lambda$setTextAsync$0(this.mIsNumberingMode ? item.getNumberingSystem() : item.getLabel(this.mCountryMode, this.mChangeDisplayName));
        text.setTextLocale(item.getLocale());
        text.setContentDescription(this.mIsNumberingMode ? item.getNumberingSystem() : item.getContentDescription(this.mCountryMode));
        if (this.mCountryMode) {
            int layoutDir = TextUtils.getLayoutDirectionFromLocale(item.getParent());
            convertView.setLayoutDirection(layoutDir);
            if (layoutDir == 1) {
                i = 4;
            } else {
                i = 3;
            }
            text.setTextDirection(i);
        }
    }
}
