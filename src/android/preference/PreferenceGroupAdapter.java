package android.preference;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.preference.Preference;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import com.android.internal.R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Deprecated
/* loaded from: classes3.dex */
public class PreferenceGroupAdapter extends BaseAdapter implements Preference.OnPreferenceChangeInternalListener {
    private static final String TAG = "PreferenceGroupAdapter";
    private static ViewGroup.LayoutParams sWrapperLayoutParams = new ViewGroup.LayoutParams(-1, -2);
    private Drawable mHighlightedDrawable;
    private PreferenceGroup mPreferenceGroup;
    private ArrayList<PreferenceLayout> mPreferenceLayouts;
    private List<Preference> mPreferenceList;
    private PreferenceLayout mTempPreferenceLayout = new PreferenceLayout();
    private boolean mHasReturnedViewTypeCount = false;
    boolean mIsCategoryAfter = false;
    Preference mNextPreference = null;
    Preference mNextGroupPreference = null;
    private volatile boolean mIsSyncing = false;
    private Handler mHandler = new Handler();
    private Runnable mSyncRunnable = new Runnable() { // from class: android.preference.PreferenceGroupAdapter.1
        @Override // java.lang.Runnable
        public void run() {
            PreferenceGroupAdapter.this.syncMyPreferences();
        }
    };
    private int mHighlightedPosition = -1;

    @Override // android.widget.BaseAdapter, android.widget.ListAdapter
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public boolean hasStableIds() {
        return true;
    }

    private static class PreferenceLayout implements Comparable<PreferenceLayout> {
        private String name;
        private int resId;
        private int widgetResId;

        private PreferenceLayout() {
        }

        @Override // java.lang.Comparable
        public int compareTo(PreferenceLayout preferenceLayout) {
            int iCompareTo = this.name.compareTo(preferenceLayout.name);
            if (iCompareTo != 0) {
                return iCompareTo;
            }
            int i = this.resId;
            int i2 = preferenceLayout.resId;
            if (i != i2) {
                return i - i2;
            }
            int i3 = this.widgetResId;
            int i4 = preferenceLayout.widgetResId;
            if (i3 == i4) {
                return 0;
            }
            return i3 - i4;
        }
    }

    public PreferenceGroupAdapter(PreferenceGroup preferenceGroup) {
        this.mPreferenceGroup = preferenceGroup;
        preferenceGroup.setOnPreferenceChangeInternalListener(this);
        this.mPreferenceList = new ArrayList();
        this.mPreferenceLayouts = new ArrayList<>();
        syncMyPreferences();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void syncMyPreferences() {
        synchronized (this) {
            if (this.mIsSyncing) {
                return;
            }
            this.mIsSyncing = true;
            this.mIsCategoryAfter = false;
            this.mNextPreference = null;
            this.mNextGroupPreference = null;
            ArrayList arrayList = new ArrayList(this.mPreferenceList.size());
            flattenPreferenceGroup(arrayList, this.mPreferenceGroup);
            this.mPreferenceList = arrayList;
            notifyDataSetChanged();
            synchronized (this) {
                this.mIsSyncing = false;
                notifyAll();
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x005d  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0064  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void flattenPreferenceGroup(List<Preference> list, PreferenceGroup preferenceGroup) {
        Preference preference;
        preferenceGroup.sortPreferences();
        int preferenceCount = preferenceGroup.getPreferenceCount();
        for (int i = 0; i < preferenceCount; i++) {
            Preference preference2 = preferenceGroup.getPreference(i);
            if (preference2 != null) {
                if (View.sIsSamsungBasicInteraction) {
                    if (i == preferenceCount - 1) {
                        this.mNextPreference = null;
                        if (this.mIsCategoryAfter && preference2 == this.mNextGroupPreference) {
                            this.mNextGroupPreference = null;
                        }
                    } else {
                        this.mNextPreference = preferenceGroup.getPreference(i + 1);
                        if (preference2 == this.mNextGroupPreference) {
                            this.mNextGroupPreference = null;
                        }
                    }
                    if (preference2 instanceof PreferenceCategory) {
                        this.mIsCategoryAfter = true;
                    } else {
                        boolean z = this.mIsCategoryAfter;
                        if (z) {
                            Preference preference3 = this.mNextPreference;
                            if (!(preference3 instanceof PreferenceCategory)) {
                                if (preference3 == null) {
                                    Preference preference4 = this.mNextGroupPreference;
                                    if ((preference4 instanceof PreferenceCategory) || preference4 == null) {
                                    }
                                }
                                if (!z) {
                                    preference2.setRoundCorner(3);
                                    this.mIsCategoryAfter = false;
                                } else {
                                    Preference preference5 = this.mNextPreference;
                                    if ((preference5 instanceof PreferenceCategory) || ((preference5 == null && (this.mNextGroupPreference instanceof PreferenceCategory)) || preference2 == (preference = this.mNextGroupPreference) || (preference5 == null && preference == null))) {
                                        preference2.setRoundCorner(12);
                                        this.mIsCategoryAfter = true;
                                    } else {
                                        preference2.setRoundCorner(0);
                                    }
                                }
                            }
                            preference2.setRoundCorner(15);
                            this.mIsCategoryAfter = false;
                        } else if (!z) {
                        }
                    }
                    if (preferenceGroup.mIsChangedCategoryBG) {
                        preference2.setCategoryBGColor(preferenceGroup.mCategoryBGColor);
                    }
                }
                list.add(preference2);
                if (View.sIsSamsungBasicInteraction && (preference2 instanceof PreferenceCategory)) {
                    if (TextUtils.isEmpty(preference2.getTitle())) {
                        preference2.setLayoutResource(R.layout.tw_preference_category_material_empty);
                    } else {
                        preference2.setLayoutResource(R.layout.tw_preference_category_material);
                    }
                }
                if (!this.mHasReturnedViewTypeCount && preference2 != null && preference2.isRecycleEnabled()) {
                    addPreferenceClassName(preference2);
                }
                if (preference2 instanceof PreferenceGroup) {
                    PreferenceGroup preferenceGroup2 = (PreferenceGroup) preference2;
                    if (preferenceGroup2.isOnSameScreenAsChildren()) {
                        this.mNextGroupPreference = this.mNextPreference;
                        flattenPreferenceGroup(list, preferenceGroup2);
                    }
                }
                preference2.setOnPreferenceChangeInternalListener(this);
            }
        }
    }

    private PreferenceLayout createPreferenceLayout(Preference preference, PreferenceLayout preferenceLayout) {
        if (preferenceLayout == null) {
            preferenceLayout = new PreferenceLayout();
        }
        preferenceLayout.name = preference.getClass().getName();
        preferenceLayout.resId = preference.getLayoutResource();
        preferenceLayout.widgetResId = preference.getWidgetLayoutResource();
        return preferenceLayout;
    }

    private void addPreferenceClassName(Preference preference) {
        PreferenceLayout preferenceLayoutCreatePreferenceLayout = createPreferenceLayout(preference, null);
        if (Collections.binarySearch(this.mPreferenceLayouts, preferenceLayoutCreatePreferenceLayout) < 0) {
            this.mPreferenceLayouts.add((r0 * (-1)) - 1, preferenceLayoutCreatePreferenceLayout);
        }
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.mPreferenceList.size();
    }

    @Override // android.widget.Adapter
    public Preference getItem(int i) {
        if (i < 0 || i >= getCount()) {
            return null;
        }
        return this.mPreferenceList.get(i);
    }

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        if (i < 0 || i >= getCount()) {
            return Long.MIN_VALUE;
        }
        return getItem(i).getId();
    }

    public void setHighlighted(int i) {
        this.mHighlightedPosition = i;
    }

    public void setHighlightedDrawable(Drawable drawable) {
        this.mHighlightedDrawable = drawable;
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) throws Resources.NotFoundException {
        Preference item = getItem(i);
        PreferenceLayout preferenceLayoutCreatePreferenceLayout = createPreferenceLayout(item, this.mTempPreferenceLayout);
        this.mTempPreferenceLayout = preferenceLayoutCreatePreferenceLayout;
        if (Collections.binarySearch(this.mPreferenceLayouts, preferenceLayoutCreatePreferenceLayout) < 0 || getItemViewType(i) == getHighlightItemViewType()) {
            view = null;
        }
        View view2 = item.getView(view, viewGroup);
        if (i != this.mHighlightedPosition || this.mHighlightedDrawable == null) {
            return view2;
        }
        FrameLayout frameLayout = new FrameLayout(viewGroup.getContext());
        frameLayout.setLayoutParams(sWrapperLayoutParams);
        frameLayout.setBackgroundDrawable(this.mHighlightedDrawable);
        frameLayout.addView(view2);
        return frameLayout;
    }

    @Override // android.widget.BaseAdapter, android.widget.ListAdapter
    public boolean isEnabled(int i) {
        if (i < 0 || i >= getCount()) {
            return true;
        }
        return getItem(i).isSelectable();
    }

    @Override // android.preference.Preference.OnPreferenceChangeInternalListener
    public void onPreferenceChange(Preference preference) {
        notifyDataSetChanged();
    }

    @Override // android.preference.Preference.OnPreferenceChangeInternalListener
    public void onPreferenceHierarchyChange(Preference preference) {
        this.mHandler.removeCallbacks(this.mSyncRunnable);
        this.mHandler.post(this.mSyncRunnable);
    }

    private int getHighlightItemViewType() {
        return getViewTypeCount() - 1;
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getItemViewType(int i) {
        if (i == this.mHighlightedPosition) {
            return getHighlightItemViewType();
        }
        if (!this.mHasReturnedViewTypeCount) {
            this.mHasReturnedViewTypeCount = true;
        }
        Preference item = getItem(i);
        if (!item.isRecycleEnabled()) {
            return -1;
        }
        PreferenceLayout preferenceLayoutCreatePreferenceLayout = createPreferenceLayout(item, this.mTempPreferenceLayout);
        this.mTempPreferenceLayout = preferenceLayoutCreatePreferenceLayout;
        int iBinarySearch = Collections.binarySearch(this.mPreferenceLayouts, preferenceLayoutCreatePreferenceLayout);
        if (iBinarySearch < 0) {
            return -1;
        }
        return iBinarySearch;
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getViewTypeCount() {
        if (!this.mHasReturnedViewTypeCount) {
            this.mHasReturnedViewTypeCount = true;
        }
        return Math.max(1, this.mPreferenceLayouts.size()) + 1;
    }
}
