package androidx.preference;

import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.view.ViewCompat;
import androidx.preference.Preference;
import androidx.recyclerview.widget.RecyclerView;
import androidx.reflect.view.SeslHapticFeedbackConstantsReflector;
import androidx.reflect.view.SeslViewReflector;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class PreferenceGroupAdapter extends RecyclerView.Adapter {
    public List mAccessibilityPositionTable;
    public ViewGroup mParent;
    public final PreferenceGroup mPreferenceGroup;
    public final List mPreferenceResourceDescriptors;
    public List mPreferences;
    public List mVisiblePreferences;
    public final RunnableC04171 mSyncRunnable = new Runnable() { // from class: androidx.preference.PreferenceGroupAdapter.1
        @Override // java.lang.Runnable
        public final void run() {
            PreferenceGroupAdapter.this.updatePreferences();
        }
    };
    public final int mCategoryLayoutId = R.layout.sesl_preference_category;
    public Preference mNextPreference = null;
    public Preference mNextGroupPreference = null;
    public int mParentWidth = 0;
    public final Handler mHandler = new Handler(Looper.getMainLooper());

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PreferenceResourceDescriptor {
        public final String mClassName;
        public final boolean mIsDotVisibled;
        public final int mLayoutResId;
        public final int mWidgetLayoutResId;

        public PreferenceResourceDescriptor(Preference preference) {
            this.mClassName = preference.getClass().getName();
            this.mLayoutResId = preference.mLayoutResId;
            this.mWidgetLayoutResId = preference.mWidgetLayoutResId;
            this.mIsDotVisibled = preference.mIsDotVisible;
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof PreferenceResourceDescriptor)) {
                return false;
            }
            PreferenceResourceDescriptor preferenceResourceDescriptor = (PreferenceResourceDescriptor) obj;
            return this.mLayoutResId == preferenceResourceDescriptor.mLayoutResId && this.mWidgetLayoutResId == preferenceResourceDescriptor.mWidgetLayoutResId && TextUtils.equals(this.mClassName, preferenceResourceDescriptor.mClassName) && this.mIsDotVisibled == preferenceResourceDescriptor.mIsDotVisibled && TextUtils.equals(null, null);
        }

        public final int hashCode() {
            return this.mClassName.hashCode() + ((((527 + this.mLayoutResId) * 31) + this.mWidgetLayoutResId) * 31);
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [androidx.preference.PreferenceGroupAdapter$1] */
    public PreferenceGroupAdapter(PreferenceGroup preferenceGroup) {
        this.mPreferenceGroup = preferenceGroup;
        preferenceGroup.mListener = this;
        this.mPreferences = new ArrayList();
        this.mVisiblePreferences = new ArrayList();
        this.mPreferenceResourceDescriptors = new ArrayList();
        this.mAccessibilityPositionTable = new ArrayList();
        if (preferenceGroup instanceof PreferenceScreen) {
            setHasStableIds(((PreferenceScreen) preferenceGroup).mShouldUseGeneratedIds);
        } else {
            setHasStableIds(true);
        }
        updatePreferences();
    }

    public static boolean isGroupExpandable(PreferenceGroup preferenceGroup) {
        return preferenceGroup.mInitialExpandedChildrenCount != Integer.MAX_VALUE;
    }

    public static boolean isSwitchLayout(Preference preference) {
        int i = preference.mLayoutResId;
        if (i == R.layout.sesl_preference_switch && preference.mWidgetLayoutResId == R.layout.sesl_preference_widget_switch) {
            return true;
        }
        return i == R.layout.sesl_preference_switch_screen && preference.mWidgetLayoutResId == R.layout.sesl_switch_preference_screen_widget_divider;
    }

    public final List createVisiblePreferencesList(final PreferenceGroup preferenceGroup) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        int preferenceCount = preferenceGroup.getPreferenceCount();
        int i = 0;
        for (int i2 = 0; i2 < preferenceCount; i2++) {
            Preference preference = preferenceGroup.getPreference(i2);
            if (preference.mVisible) {
                if (!isGroupExpandable(preferenceGroup) || i < preferenceGroup.mInitialExpandedChildrenCount) {
                    arrayList.add(preference);
                } else {
                    arrayList2.add(preference);
                }
                if (preference instanceof PreferenceGroup) {
                    PreferenceGroup preferenceGroup2 = (PreferenceGroup) preference;
                    if (!(!(preferenceGroup2 instanceof PreferenceScreen))) {
                        continue;
                    } else {
                        if (isGroupExpandable(preferenceGroup) && isGroupExpandable(preferenceGroup2)) {
                            throw new IllegalStateException("Nesting an expandable group inside of another expandable group is not supported!");
                        }
                        Iterator it = ((ArrayList) createVisiblePreferencesList(preferenceGroup2)).iterator();
                        while (it.hasNext()) {
                            Preference preference2 = (Preference) it.next();
                            if (!isGroupExpandable(preferenceGroup) || i < preferenceGroup.mInitialExpandedChildrenCount) {
                                arrayList.add(preference2);
                            } else {
                                arrayList2.add(preference2);
                            }
                            i++;
                        }
                    }
                } else {
                    i++;
                }
            }
        }
        if (isGroupExpandable(preferenceGroup) && i > preferenceGroup.mInitialExpandedChildrenCount) {
            ExpandButton expandButton = new ExpandButton(preferenceGroup.mContext, arrayList2, preferenceGroup.mId);
            expandButton.mOnClickListener = new Preference.OnPreferenceClickListener() { // from class: androidx.preference.PreferenceGroupAdapter.3
                @Override // androidx.preference.Preference.OnPreferenceClickListener
                public final void onPreferenceClick(Preference preference3) {
                    preferenceGroup.setInitialExpandedChildrenCount(Integer.MAX_VALUE);
                    PreferenceGroupAdapter preferenceGroupAdapter = PreferenceGroupAdapter.this;
                    Handler handler = preferenceGroupAdapter.mHandler;
                    RunnableC04171 runnableC04171 = preferenceGroupAdapter.mSyncRunnable;
                    handler.removeCallbacks(runnableC04171);
                    handler.post(runnableC04171);
                }
            };
            arrayList.add(expandButton);
        }
        return arrayList;
    }

    public final void flattenPreferenceGroup(PreferenceGroup preferenceGroup, List list) {
        synchronized (preferenceGroup) {
            Collections.sort(preferenceGroup.mPreferences);
        }
        int preferenceCount = preferenceGroup.getPreferenceCount();
        for (int i = 0; i < preferenceCount; i++) {
            Preference preference = preferenceGroup.getPreference(i);
            if (i == preferenceCount - 1) {
                this.mNextPreference = null;
            } else {
                this.mNextPreference = preferenceGroup.getPreference(i + 1);
                if (preference == this.mNextGroupPreference) {
                    this.mNextGroupPreference = null;
                }
            }
            boolean z = preference instanceof PreferenceCategory;
            if (z && !preference.mIsRoundChanged) {
                preference.mIsPreferenceRoundedBg = true;
                preference.mWhere = 15;
                preference.mSubheaderRound = true;
                preference.mIsRoundChanged = true;
            }
            ((ArrayList) list).add(preference);
            if (z && TextUtils.isEmpty(preference.getTitle()) && this.mCategoryLayoutId == preference.mLayoutResId) {
                preference.mLayoutResId = R.layout.sesl_preference_category_empty;
            }
            PreferenceResourceDescriptor preferenceResourceDescriptor = new PreferenceResourceDescriptor(preference);
            if (!((ArrayList) this.mPreferenceResourceDescriptors).contains(preferenceResourceDescriptor)) {
                ((ArrayList) this.mPreferenceResourceDescriptors).add(preferenceResourceDescriptor);
            }
            if (preference instanceof PreferenceGroup) {
                PreferenceGroup preferenceGroup2 = (PreferenceGroup) preference;
                preferenceGroup2.getClass();
                if (true ^ (preferenceGroup2 instanceof PreferenceScreen)) {
                    this.mNextGroupPreference = this.mNextPreference;
                    flattenPreferenceGroup(preferenceGroup2, list);
                }
            }
            preference.mListener = this;
        }
    }

    public final Preference getItem(int i) {
        if (i < 0 || i >= getItemCount()) {
            return null;
        }
        return (Preference) this.mVisiblePreferences.get(i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return this.mVisiblePreferences.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final long getItemId(int i) {
        if (!this.mHasStableIds || getItem(i) == null) {
            return -1L;
        }
        return getItem(i).getId();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemViewType(int i) {
        PreferenceResourceDescriptor preferenceResourceDescriptor = new PreferenceResourceDescriptor(getItem(i));
        List list = this.mPreferenceResourceDescriptors;
        int indexOf = list.indexOf(preferenceResourceDescriptor);
        if (indexOf != -1) {
            return indexOf;
        }
        int size = list.size();
        list.add(preferenceResourceDescriptor);
        return size;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        int dimensionPixelSize;
        int paddingEnd;
        ColorStateList colorStateList;
        PreferenceViewHolder preferenceViewHolder = (PreferenceViewHolder) viewHolder;
        Preference item = getItem(i);
        View view = preferenceViewHolder.itemView;
        Drawable background = view.getBackground();
        Drawable drawable = preferenceViewHolder.mBackground;
        if (background != drawable) {
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api16Impl.setBackground(view, drawable);
        }
        TextView textView = (TextView) preferenceViewHolder.findViewById(android.R.id.title);
        if (textView != null && (colorStateList = preferenceViewHolder.mTitleTextColors) != null && !textView.getTextColors().equals(colorStateList)) {
            textView.setTextColor(colorStateList);
        }
        if (!isSwitchLayout(item)) {
            item.onBindViewHolder(preferenceViewHolder);
            return;
        }
        int width = this.mParent.getWidth();
        this.mParentWidth = width;
        if (item instanceof SwitchPreference) {
            SwitchPreference switchPreference = (SwitchPreference) item;
            switchPreference.mWidth = width;
            switchPreference.onBindViewHolder(preferenceViewHolder);
            View findViewById = view.findViewById(R.id.widget_frame);
            View findViewById2 = view.findViewById(android.R.id.widget_frame);
            View findViewById3 = view.findViewById(R.id.switch_widget);
            View findViewById4 = view.findViewById(android.R.id.switch_widget);
            Configuration configuration = switchPreference.mContext.getResources().getConfiguration();
            int i2 = configuration.screenWidthDp;
            int i3 = ((i2 > 320 || configuration.fontScale < 1.1f) && (i2 >= 411 || configuration.fontScale < 1.3f)) ? 2 : 1;
            if (i3 != 1) {
                if (switchPreference.mIsLargeLayout != i3) {
                    switchPreference.mIsLargeLayout = i3;
                    TextView textView2 = (TextView) view.findViewById(android.R.id.title);
                    findViewById2.setVisibility(0);
                    findViewById.setVisibility(8);
                    textView2.requestLayout();
                }
                switchPreference.syncSwitchView(findViewById4);
                return;
            }
            switchPreference.mIsLargeLayout = i3;
            TextView textView3 = (TextView) view.findViewById(android.R.id.title);
            float measureText = textView3.getPaint().measureText(textView3.getText().toString());
            TextView textView4 = (TextView) view.findViewById(android.R.id.summary);
            float measureText2 = textView4.getPaint().measureText(textView4.getText().toString());
            if (textView4.getVisibility() == 8) {
                measureText2 = 0.0f;
            }
            float paddingEnd2 = ((switchPreference.mWidth - view.getPaddingEnd()) - view.getPaddingStart()) - switchPreference.mContext.getResources().getDimensionPixelSize(R.dimen.sesl_preference_item_switch_size);
            if (measureText >= paddingEnd2 || measureText2 >= paddingEnd2) {
                findViewById.setVisibility(0);
                findViewById2.setVisibility(8);
                textView3.requestLayout();
                SwitchCompat switchCompat = (SwitchCompat) findViewById3;
                if (!switchCompat.canHapticFeedback(switchPreference.mChecked)) {
                    if (switchPreference.mChecked != switchCompat.isChecked() && view.hasWindowFocus() && SeslViewReflector.isVisibleToUser(view) && !view.isTemporarilyDetached()) {
                        switchCompat.performHapticFeedback(SeslHapticFeedbackConstantsReflector.semGetVibrationIndex(27));
                    }
                }
                switchPreference.syncSwitchView(findViewById3);
                SwitchCompat switchCompat2 = (SwitchCompat) findViewById4;
                switchCompat2.setOnCheckedChangeListener(null);
                switchCompat2.setCheckedWithoutAnimation(switchPreference.mChecked);
                return;
            }
            findViewById2.setVisibility(0);
            findViewById.setVisibility(8);
            textView3.requestLayout();
            SwitchCompat switchCompat3 = (SwitchCompat) findViewById4;
            if (!switchCompat3.canHapticFeedback(switchPreference.mChecked)) {
                if (switchPreference.mChecked != switchCompat3.isChecked() && view.hasWindowFocus() && SeslViewReflector.isVisibleToUser(view) && !view.isTemporarilyDetached()) {
                    switchCompat3.performHapticFeedback(SeslHapticFeedbackConstantsReflector.semGetVibrationIndex(27));
                }
            }
            switchPreference.syncSwitchView(findViewById4);
            SwitchCompat switchCompat4 = (SwitchCompat) findViewById3;
            switchCompat4.setOnCheckedChangeListener(null);
            switchCompat4.setCheckedWithoutAnimation(switchPreference.mChecked);
            return;
        }
        if (!(item instanceof SwitchPreferenceCompat)) {
            item.onBindViewHolder(preferenceViewHolder);
            return;
        }
        SwitchPreferenceCompat switchPreferenceCompat = (SwitchPreferenceCompat) item;
        switchPreferenceCompat.mWidth = width;
        switchPreferenceCompat.onBindViewHolder(preferenceViewHolder);
        View findViewById5 = view.findViewById(R.id.widget_frame);
        View findViewById6 = view.findViewById(android.R.id.widget_frame);
        View findViewById7 = view.findViewById(R.id.switch_widget);
        View findViewById8 = view.findViewById(android.R.id.switch_widget);
        Configuration configuration2 = switchPreferenceCompat.mContext.getResources().getConfiguration();
        int i4 = configuration2.screenWidthDp;
        int i5 = ((i4 > 320 || configuration2.fontScale < 1.1f) && (i4 >= 411 || configuration2.fontScale < 1.3f)) ? 2 : 1;
        if (i5 != 1) {
            if (switchPreferenceCompat.mIsLargeLayout != i5) {
                switchPreferenceCompat.mIsLargeLayout = i5;
                TextView textView5 = (TextView) view.findViewById(android.R.id.title);
                findViewById6.setVisibility(0);
                findViewById5.setVisibility(8);
                textView5.requestLayout();
            }
            switchPreferenceCompat.syncSwitchView(findViewById8);
            return;
        }
        switchPreferenceCompat.mIsLargeLayout = i5;
        TextView textView6 = (TextView) view.findViewById(android.R.id.title);
        float measureText3 = textView6.getPaint().measureText(textView6.getText().toString());
        TextView textView7 = (TextView) view.findViewById(android.R.id.summary);
        float measureText4 = textView7.getPaint().measureText(textView7.getText().toString());
        if (textView7.getVisibility() == 8) {
            measureText4 = 0.0f;
        }
        if (switchPreferenceCompat instanceof SeslSwitchPreferenceScreen) {
            dimensionPixelSize = switchPreferenceCompat.mContext.getResources().getDimensionPixelSize(R.dimen.sesl_preference_screen_item_switch_size);
            paddingEnd = findViewById6.getPaddingEnd();
        } else {
            dimensionPixelSize = switchPreferenceCompat.mContext.getResources().getDimensionPixelSize(R.dimen.sesl_preference_item_switch_size);
            paddingEnd = findViewById6.getPaddingEnd();
        }
        float paddingEnd3 = ((switchPreferenceCompat.mWidth - view.getPaddingEnd()) - view.getPaddingStart()) - (paddingEnd + dimensionPixelSize);
        if (measureText3 >= paddingEnd3 || measureText4 >= paddingEnd3) {
            findViewById5.setVisibility(0);
            findViewById6.setVisibility(8);
            textView6.requestLayout();
            SwitchCompat switchCompat5 = (SwitchCompat) findViewById7;
            if (!switchCompat5.canHapticFeedback(switchPreferenceCompat.mChecked)) {
                if (switchPreferenceCompat.mChecked != switchCompat5.isChecked() && view.hasWindowFocus() && SeslViewReflector.isVisibleToUser(view) && !view.isTemporarilyDetached()) {
                    switchCompat5.performHapticFeedback(SeslHapticFeedbackConstantsReflector.semGetVibrationIndex(27));
                }
            }
            switchPreferenceCompat.syncSwitchView(findViewById7);
            SwitchCompat switchCompat6 = (SwitchCompat) findViewById8;
            switchCompat6.setOnCheckedChangeListener(null);
            switchCompat6.setCheckedWithoutAnimation(switchPreferenceCompat.mChecked);
            return;
        }
        findViewById6.setVisibility(0);
        findViewById5.setVisibility(8);
        textView6.requestLayout();
        SwitchCompat switchCompat7 = (SwitchCompat) findViewById8;
        if (!switchCompat7.canHapticFeedback(switchPreferenceCompat.mChecked)) {
            if (switchPreferenceCompat.mChecked != switchCompat7.isChecked() && view.hasWindowFocus() && SeslViewReflector.isVisibleToUser(view) && !view.isTemporarilyDetached()) {
                switchCompat7.performHapticFeedback(SeslHapticFeedbackConstantsReflector.semGetVibrationIndex(27));
            }
        }
        switchPreferenceCompat.syncSwitchView(findViewById8);
        SwitchCompat switchCompat8 = (SwitchCompat) findViewById7;
        switchCompat8.setOnCheckedChangeListener(null);
        switchCompat8.setCheckedWithoutAnimation(switchPreferenceCompat.mChecked);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(RecyclerView recyclerView, int i) {
        PreferenceResourceDescriptor preferenceResourceDescriptor = (PreferenceResourceDescriptor) ((ArrayList) this.mPreferenceResourceDescriptors).get(i);
        LayoutInflater from = LayoutInflater.from(recyclerView.getContext());
        this.mParent = recyclerView;
        View inflate = from.inflate(preferenceResourceDescriptor.mLayoutResId, (ViewGroup) recyclerView, false);
        ViewGroup viewGroup = (ViewGroup) inflate.findViewById(android.R.id.widget_frame);
        if (viewGroup != null) {
            int i2 = preferenceResourceDescriptor.mWidgetLayoutResId;
            if (i2 != 0) {
                from.inflate(i2, viewGroup);
            } else {
                viewGroup.setVisibility(8);
            }
        }
        View findViewById = inflate.findViewById(R.id.badge_frame);
        if (findViewById != null) {
            if (preferenceResourceDescriptor.mIsDotVisibled) {
                findViewById.setVisibility(0);
            } else {
                findViewById.setVisibility(8);
            }
        }
        return new PreferenceViewHolder(inflate);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int seslGetAccessibilityItemCount() {
        List list = this.mAccessibilityPositionTable;
        if (list != null && ((ArrayList) list).size() > 0) {
            return ((Integer) ((ArrayList) this.mAccessibilityPositionTable).get(r4.size() - 1)).intValue() + 1;
        }
        Iterator it = ((ArrayList) this.mVisiblePreferences).iterator();
        int i = 0;
        while (it.hasNext()) {
            if (((Preference) it.next()).mLayoutResId == R.layout.sesl_preference_category_empty) {
                i++;
            }
        }
        return getItemCount() - i;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int seslGetAccessibilityItemPosition(int i) {
        List list = this.mAccessibilityPositionTable;
        if (list == null || i >= ((ArrayList) list).size()) {
            return -1;
        }
        return ((Integer) ((ArrayList) this.mAccessibilityPositionTable).get(i)).intValue();
    }

    public final void updatePreferences() {
        int i;
        Iterator it = this.mPreferences.iterator();
        while (it.hasNext()) {
            ((Preference) it.next()).mListener = null;
        }
        ArrayList arrayList = new ArrayList(this.mPreferences.size());
        this.mPreferences = arrayList;
        PreferenceGroup preferenceGroup = this.mPreferenceGroup;
        flattenPreferenceGroup(preferenceGroup, arrayList);
        this.mVisiblePreferences = createVisiblePreferencesList(preferenceGroup);
        ArrayList arrayList2 = new ArrayList();
        Iterator it2 = this.mVisiblePreferences.iterator();
        int i2 = -1;
        while (true) {
            if (!it2.hasNext()) {
                break;
            }
            if (((Preference) it2.next()).mLayoutResId != R.layout.sesl_preference_category_empty) {
                i2++;
            }
            arrayList2.add(Integer.valueOf(Math.max(i2, 0)));
        }
        if (arrayList2.size() > 0 && ((Integer) arrayList2.get(arrayList2.size() - 1)).intValue() >= this.mVisiblePreferences.size()) {
            Log.w("PreferenceGroupAdapter", "accessibilityPosition over visible size | last " + arrayList2.get(arrayList2.size() - 1) + " vsize " + this.mVisiblePreferences.size());
            for (i = 0; i < arrayList2.size(); i++) {
                arrayList2.set(i, Integer.valueOf(i));
            }
        }
        this.mAccessibilityPositionTable = arrayList2;
        PreferenceManager preferenceManager = preferenceGroup.mPreferenceManager;
        notifyDataSetChanged();
        Iterator it3 = this.mPreferences.iterator();
        while (it3.hasNext()) {
            ((Preference) it3.next()).getClass();
        }
    }
}
