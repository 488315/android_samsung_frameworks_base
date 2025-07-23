package android.preference;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.preference.Preference;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
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
            int compareTo = this.name.compareTo(preferenceLayout.name);
            if (compareTo != 0) {
                return compareTo;
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

    /* JADX WARN: Code restructure failed: missing block: B:27:0x0051, code lost:
    
        if (r6 != null) goto L31;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void flattenPreferenceGroup(java.util.List<android.preference.Preference> r9, android.preference.PreferenceGroup r10) {
        /*
            Method dump skipped, instructions count: 220
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.preference.PreferenceGroupAdapter.flattenPreferenceGroup(java.util.List, android.preference.PreferenceGroup):void");
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
        PreferenceLayout createPreferenceLayout = createPreferenceLayout(preference, null);
        if (Collections.binarySearch(this.mPreferenceLayouts, createPreferenceLayout) < 0) {
            this.mPreferenceLayouts.add((r0 * (-1)) - 1, createPreferenceLayout);
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        Preference item = getItem(i);
        PreferenceLayout createPreferenceLayout = createPreferenceLayout(item, this.mTempPreferenceLayout);
        this.mTempPreferenceLayout = createPreferenceLayout;
        if (Collections.binarySearch(this.mPreferenceLayouts, createPreferenceLayout) < 0 || getItemViewType(i) == getHighlightItemViewType()) {
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
        PreferenceLayout createPreferenceLayout = createPreferenceLayout(item, this.mTempPreferenceLayout);
        this.mTempPreferenceLayout = createPreferenceLayout;
        int binarySearch = Collections.binarySearch(this.mPreferenceLayouts, createPreferenceLayout);
        if (binarySearch < 0) {
            return -1;
        }
        return binarySearch;
    }

    @Override // android.widget.BaseAdapter, android.widget.Adapter
    public int getViewTypeCount() {
        if (!this.mHasReturnedViewTypeCount) {
            this.mHasReturnedViewTypeCount = true;
        }
        return Math.max(1, this.mPreferenceLayouts.size()) + 1;
    }
}
