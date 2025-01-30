package android.preference;

import android.graphics.drawable.Drawable;
import android.p009os.Handler;
import android.preference.Preference;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import com.android.internal.C4337R;
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

    private static class PreferenceLayout implements Comparable<PreferenceLayout> {
        private String name;
        private int resId;
        private int widgetResId;

        private PreferenceLayout() {
        }

        @Override // java.lang.Comparable
        public int compareTo(PreferenceLayout other) {
            int compareNames = this.name.compareTo(other.name);
            if (compareNames == 0) {
                int i = this.resId;
                int i2 = other.resId;
                if (i == i2) {
                    int i3 = this.widgetResId;
                    int i4 = other.widgetResId;
                    if (i3 == i4) {
                        return 0;
                    }
                    return i3 - i4;
                }
                return i - i2;
            }
            return compareNames;
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
            List<Preference> newPreferenceList = new ArrayList<>(this.mPreferenceList.size());
            flattenPreferenceGroup(newPreferenceList, this.mPreferenceGroup);
            this.mPreferenceList = newPreferenceList;
            notifyDataSetChanged();
            synchronized (this) {
                this.mIsSyncing = false;
                notifyAll();
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x0051, code lost:
    
        if (r6 != null) goto L31;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void flattenPreferenceGroup(List<Preference> preferences, PreferenceGroup group) {
        Preference preference;
        group.sortPreferences();
        int groupSize = group.getPreferenceCount();
        for (int i = 0; i < groupSize; i++) {
            Preference preference2 = group.getPreference(i);
            if (preference2 != null) {
                if (View.sIsSamsungBasicInteraction) {
                    if (i == groupSize - 1) {
                        this.mNextPreference = null;
                        if (this.mIsCategoryAfter && preference2 == this.mNextGroupPreference) {
                            this.mNextGroupPreference = null;
                        }
                    } else {
                        this.mNextPreference = group.getPreference(i + 1);
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
                                    if (!(preference4 instanceof PreferenceCategory)) {
                                    }
                                }
                            }
                            preference2.setRoundCorner(15);
                            this.mIsCategoryAfter = false;
                        }
                        if (z) {
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
                    if (group.mIsChangedCategoryBG) {
                        preference2.setCategoryBGColor(group.mCategoryBGColor);
                    }
                }
                preferences.add(preference2);
                if (View.sIsSamsungBasicInteraction && (preference2 instanceof PreferenceCategory)) {
                    if (TextUtils.isEmpty(preference2.getTitle())) {
                        preference2.setLayoutResource(C4337R.layout.tw_preference_category_material_empty);
                    } else {
                        preference2.setLayoutResource(C4337R.layout.tw_preference_category_material);
                    }
                }
                if (!this.mHasReturnedViewTypeCount && preference2 != null && preference2.isRecycleEnabled()) {
                    addPreferenceClassName(preference2);
                }
                if (preference2 instanceof PreferenceGroup) {
                    PreferenceGroup preferenceAsGroup = (PreferenceGroup) preference2;
                    if (preferenceAsGroup.isOnSameScreenAsChildren()) {
                        this.mNextGroupPreference = this.mNextPreference;
                        flattenPreferenceGroup(preferences, preferenceAsGroup);
                    }
                }
                preference2.setOnPreferenceChangeInternalListener(this);
            }
        }
    }

    private PreferenceLayout createPreferenceLayout(Preference preference, PreferenceLayout in) {
        PreferenceLayout pl = in != null ? in : new PreferenceLayout();
        pl.name = preference.getClass().getName();
        pl.resId = preference.getLayoutResource();
        pl.widgetResId = preference.getWidgetLayoutResource();
        return pl;
    }

    private void addPreferenceClassName(Preference preference) {
        PreferenceLayout pl = createPreferenceLayout(preference, null);
        int insertPos = Collections.binarySearch(this.mPreferenceLayouts, pl);
        if (insertPos < 0) {
            this.mPreferenceLayouts.add((insertPos * (-1)) - 1, pl);
        }
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.mPreferenceList.size();
    }

    @Override // android.widget.Adapter
    public Preference getItem(int position) {
        if (position < 0 || position >= getCount()) {
            return null;
        }
        return this.mPreferenceList.get(position);
    }

    @Override // android.widget.Adapter
    public long getItemId(int position) {
        if (position < 0 || position >= getCount()) {
            return Long.MIN_VALUE;
        }
        return getItem(position).getId();
    }

    public void setHighlighted(int position) {
        this.mHighlightedPosition = position;
    }

    public void setHighlightedDrawable(Drawable drawable) {
        this.mHighlightedDrawable = drawable;
    }

    @Override // android.widget.Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        Preference preference = getItem(position);
        PreferenceLayout createPreferenceLayout = createPreferenceLayout(preference, this.mTempPreferenceLayout);
        this.mTempPreferenceLayout = createPreferenceLayout;
        if (Collections.binarySearch(this.mPreferenceLayouts, createPreferenceLayout) < 0 || getItemViewType(position) == getHighlightItemViewType()) {
            convertView = null;
        }
        View result = preference.getView(convertView, parent);
        if (position == this.mHighlightedPosition && this.mHighlightedDrawable != null) {
            ViewGroup wrapper = new FrameLayout(parent.getContext());
            wrapper.setLayoutParams(sWrapperLayoutParams);
            wrapper.setBackgroundDrawable(this.mHighlightedDrawable);
            wrapper.addView(result);
            return wrapper;
        }
        return result;
    }

    @Override // android.widget.BaseAdapter, android.widget.ListAdapter
    public boolean isEnabled(int position) {
        if (position < 0 || position >= getCount()) {
            return true;
        }
        return getItem(position).isSelectable();
    }

    @Override // android.widget.BaseAdapter, android.widget.ListAdapter
    public boolean areAllItemsEnabled() {
        return false;
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

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public boolean hasStableIds() {
        return true;
    }

    private int getHighlightItemViewType() {
        return getViewTypeCount() - 1;
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getItemViewType(int position) {
        if (position == this.mHighlightedPosition) {
            return getHighlightItemViewType();
        }
        if (!this.mHasReturnedViewTypeCount) {
            this.mHasReturnedViewTypeCount = true;
        }
        Preference preference = getItem(position);
        if (!preference.isRecycleEnabled()) {
            return -1;
        }
        PreferenceLayout createPreferenceLayout = createPreferenceLayout(preference, this.mTempPreferenceLayout);
        this.mTempPreferenceLayout = createPreferenceLayout;
        int viewType = Collections.binarySearch(this.mPreferenceLayouts, createPreferenceLayout);
        if (viewType < 0) {
            return -1;
        }
        return viewType;
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getViewTypeCount() {
        if (!this.mHasReturnedViewTypeCount) {
            this.mHasReturnedViewTypeCount = true;
        }
        return Math.max(1, this.mPreferenceLayouts.size()) + 1;
    }
}
