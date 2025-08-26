package com.android.internal.app;

import android.content.res.Resources;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.internal.R;
import com.android.internal.app.LocaleStore;
import java.util.Locale;
import java.util.Set;

/* loaded from: classes5.dex */
public class BilingualSuggestedLocaleAdapter extends SuggestedLocaleAdapter {
    private final Locale mSecondaryLocale;
    private final int mSecondaryLocaleTextDir;
    private LocaleStore.LocaleInfo mSelectedLocaleInfo;
    private final boolean mShowSelection;

    public BilingualSuggestedLocaleAdapter(Set<LocaleStore.LocaleInfo> set, boolean z, Locale locale) {
        this(set, z, locale, false);
    }

    public BilingualSuggestedLocaleAdapter(Set<LocaleStore.LocaleInfo> set, boolean z, Locale locale, boolean z2) {
        super(set, z);
        this.mSecondaryLocale = locale;
        if (TextUtils.getLayoutDirectionFromLocale(locale) == 1) {
            this.mSecondaryLocaleTextDir = 4;
        } else {
            this.mSecondaryLocaleTextDir = 3;
        }
        this.mShowSelection = z2;
    }

    @Override // com.android.internal.app.SuggestedLocaleAdapter, android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) throws Resources.NotFoundException {
        if (view == null && this.mInflater == null) {
            this.mInflater = LayoutInflater.from(viewGroup.getContext());
        }
        int itemViewType = getItemViewType(i);
        if (itemViewType == 0 || itemViewType == 1) {
            if (!(view instanceof TextView)) {
                view = this.mInflater.inflate(R.layout.language_picker_bilingual_section_header, viewGroup, false);
            }
            TextView textView = (TextView) view;
            if (itemViewType == 0) {
                setHeaderText(textView, R.string.language_picker_section_suggested_bilingual, R.string.region_picker_section_suggested_bilingual);
                return view;
            }
            setHeaderText(textView, R.string.language_picker_section_all, R.string.region_picker_section_all);
            return view;
        }
        if (!(view instanceof ViewGroup)) {
            view = this.mInflater.inflate(R.layout.language_picker_bilingual_item, viewGroup, false);
        }
        LocaleStore.LocaleInfo localeInfo = (LocaleStore.LocaleInfo) getItem(i);
        if (this.mShowSelection) {
            setItemState(isSelectedLocaleInfo(localeInfo), view);
        }
        setLocaleToListItem(view, localeInfo);
        return view;
    }

    public void setSelectedLocaleInfo(LocaleStore.LocaleInfo localeInfo) {
        this.mSelectedLocaleInfo = localeInfo;
        notifyDataSetChanged();
    }

    public LocaleStore.LocaleInfo getSelectedLocaleInfo() {
        return this.mSelectedLocaleInfo;
    }

    private boolean isSelectedLocaleInfo(LocaleStore.LocaleInfo localeInfo) {
        return (localeInfo == null || this.mSelectedLocaleInfo == null || !localeInfo.getId().equals(this.mSelectedLocaleInfo.getId())) ? false : true;
    }

    private void setItemState(boolean z, View view) {
        RelativeLayout relativeLayout = (RelativeLayout) view;
        ImageView imageView = (ImageView) view.findViewById(R.id.indicator);
        TextView textView = (TextView) view.findViewById(R.id.locale_native);
        TextView textView2 = (TextView) view.findViewById(R.id.locale_secondary);
        if (imageView == null || textView == null || textView2 == null) {
            return;
        }
        textView.setSelected(z);
        textView2.setSelected(z);
        if (z) {
            relativeLayout.setBackgroundResource(R.drawable.language_picker_item_bg_selected);
            imageView.setVisibility(0);
        } else {
            relativeLayout.setBackgroundResource(0);
            imageView.setVisibility(8);
        }
    }

    private void setHeaderText(TextView textView, int i, int i2) {
        if (this.mCountryMode) {
            setTextTo(textView, i2);
        } else {
            setTextTo(textView, i);
        }
    }

    private void setLocaleToListItem(View view, LocaleStore.LocaleInfo localeInfo) {
        if (localeInfo == null) {
            throw new NullPointerException("Cannot set locale, locale info is null.");
        }
        TextView textView = (TextView) view.findViewById(R.id.locale_native);
        textView.lambda$setTextAsync$0(localeInfo.getLabel(this.mCountryMode));
        textView.setTextLocale(localeInfo.getLocale());
        textView.setContentDescription(localeInfo.getContentDescription(this.mCountryMode));
        TextView textView2 = (TextView) view.findViewById(R.id.locale_secondary);
        textView2.lambda$setTextAsync$0(localeInfo.getLocale().getDisplayLanguage(this.mSecondaryLocale));
        textView2.setTextDirection(this.mSecondaryLocaleTextDir);
        if (this.mCountryMode) {
            int layoutDirectionFromLocale = TextUtils.getLayoutDirectionFromLocale(localeInfo.getParent());
            view.setLayoutDirection(layoutDirectionFromLocale);
            textView.setTextDirection(layoutDirectionFromLocale == 1 ? 4 : 3);
        }
    }
}
