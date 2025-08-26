package androidx.preference;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.AlertController$$ExternalSyntheticOutline0;
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

/* loaded from: classes.dex */
public class PreferenceGroupAdapter extends RecyclerView.Adapter {
    public List mAccessibilityPositionTable;
    public ViewGroup mParent;
    public final PreferenceGroup mPreferenceGroup;
    public final List mPreferenceResourceDescriptors;
    public List mPreferences;
    public List mVisiblePreferences;
    public final AnonymousClass1 mSyncRunnable = new Runnable() { // from class: androidx.preference.PreferenceGroupAdapter.1
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

    public class PreferenceResourceDescriptor {
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
                int i3 = preferenceGroup.mInitialExpandedChildrenCount;
                if (i3 == Integer.MAX_VALUE || i < i3) {
                    arrayList.add(preference);
                } else {
                    arrayList2.add(preference);
                }
                if (preference instanceof PreferenceGroup) {
                    PreferenceGroup preferenceGroup2 = (PreferenceGroup) preference;
                    if (preferenceGroup2 instanceof PreferenceScreen) {
                        continue;
                    } else {
                        if (preferenceGroup.mInitialExpandedChildrenCount != Integer.MAX_VALUE && preferenceGroup2.mInitialExpandedChildrenCount != Integer.MAX_VALUE) {
                            throw new IllegalStateException("Nesting an expandable group inside of another expandable group is not supported!");
                        }
                        for (Preference preference2 : createVisiblePreferencesList(preferenceGroup2)) {
                            int i4 = preferenceGroup.mInitialExpandedChildrenCount;
                            if (i4 == Integer.MAX_VALUE || i < i4) {
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
        int i5 = preferenceGroup.mInitialExpandedChildrenCount;
        if ((i5 != Integer.MAX_VALUE) && i > i5) {
            ExpandButton expandButton = new ExpandButton(preferenceGroup.mContext, arrayList2, preferenceGroup.mId);
            expandButton.mOnClickListener = new Preference.OnPreferenceClickListener() { // from class: androidx.preference.PreferenceGroupAdapter.3
                @Override // androidx.preference.Preference.OnPreferenceClickListener
                public final void onPreferenceClick(Preference preference3) {
                    preferenceGroup.mInitialExpandedChildrenCount = Integer.MAX_VALUE;
                    PreferenceGroupAdapter preferenceGroupAdapter = PreferenceGroupAdapter.this;
                    AnonymousClass1 anonymousClass1 = preferenceGroupAdapter.mSyncRunnable;
                    Handler handler = preferenceGroupAdapter.mHandler;
                    handler.removeCallbacks(anonymousClass1);
                    handler.post(anonymousClass1);
                }
            };
            arrayList.add(expandButton);
        }
        return arrayList;
    }

    public final void flattenPreferenceGroup(List list, PreferenceGroup preferenceGroup) {
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
            if (z && TextUtils.isEmpty(preference.mTitle) && this.mCategoryLayoutId == preference.mLayoutResId) {
                preference.mLayoutResId = R.layout.sesl_preference_category_empty;
            }
            PreferenceResourceDescriptor preferenceResourceDescriptor = new PreferenceResourceDescriptor(preference);
            if (!((ArrayList) this.mPreferenceResourceDescriptors).contains(preferenceResourceDescriptor)) {
                ((ArrayList) this.mPreferenceResourceDescriptors).add(preferenceResourceDescriptor);
            }
            if (preference instanceof PreferenceGroup) {
                PreferenceGroup preferenceGroup2 = (PreferenceGroup) preference;
                preferenceGroup2.getClass();
                if (!(preferenceGroup2 instanceof PreferenceScreen)) {
                    this.mNextGroupPreference = this.mNextPreference;
                    flattenPreferenceGroup(list, preferenceGroup2);
                }
            }
            preference.mListener = this;
        }
    }

    public final Preference getItem(int i) {
        if (i < 0 || i >= this.mVisiblePreferences.size()) {
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
        int iIndexOf = this.mPreferenceResourceDescriptors.indexOf(preferenceResourceDescriptor);
        if (iIndexOf != -1) {
            return iIndexOf;
        }
        int size = this.mPreferenceResourceDescriptors.size();
        this.mPreferenceResourceDescriptors.add(preferenceResourceDescriptor);
        return size;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) throws Resources.NotFoundException {
        int dimensionPixelSize;
        int paddingEnd;
        PreferenceViewHolder preferenceViewHolder = (PreferenceViewHolder) viewHolder;
        Preference item = getItem(i);
        Drawable background = preferenceViewHolder.itemView.getBackground();
        Drawable drawable = preferenceViewHolder.mBackground;
        if (background != drawable) {
            View view = preferenceViewHolder.itemView;
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            view.setBackground(drawable);
        }
        TextView textView = (TextView) preferenceViewHolder.findViewById(android.R.id.title);
        if (textView != null && preferenceViewHolder.mTitleTextColors != null && !textView.getTextColors().equals(preferenceViewHolder.mTitleTextColors)) {
            textView.setTextColor(preferenceViewHolder.mTitleTextColors);
        }
        if (!isSwitchLayout(item)) {
            if ((item instanceof SeekBarPreference) && preferenceViewHolder.mIsViewHolderRecoilEffectEnabled) {
                preferenceViewHolder.mIsViewHolderRecoilEffectEnabled = false;
            }
            item.onBindViewHolder(preferenceViewHolder);
            return;
        }
        int width = this.mParent.getWidth();
        this.mParentWidth = width;
        if (item instanceof SwitchPreference) {
            SwitchPreference switchPreference = (SwitchPreference) item;
            switchPreference.mWidth = width;
            switchPreference.onBindViewHolder(preferenceViewHolder);
            View view2 = preferenceViewHolder.itemView;
            View viewFindViewById = view2.findViewById(R.id.widget_frame);
            View viewFindViewById2 = view2.findViewById(android.R.id.widget_frame);
            View viewFindViewById3 = view2.findViewById(R.id.switch_widget);
            View viewFindViewById4 = view2.findViewById(android.R.id.switch_widget);
            Configuration configuration = switchPreference.mContext.getResources().getConfiguration();
            int i2 = configuration.screenWidthDp;
            int i3 = ((i2 > 320 || configuration.fontScale < 1.1f) && (i2 >= 411 || configuration.fontScale < 1.3f)) ? 2 : 1;
            if (i3 != 1) {
                if (switchPreference.mIsLargeLayout != i3) {
                    switchPreference.mIsLargeLayout = i3;
                    TextView textView2 = (TextView) view2.findViewById(android.R.id.title);
                    viewFindViewById2.setVisibility(0);
                    viewFindViewById.setVisibility(8);
                    textView2.requestLayout();
                }
                switchPreference.syncSwitchView(viewFindViewById4);
                return;
            }
            switchPreference.mIsLargeLayout = i3;
            TextView textView3 = (TextView) view2.findViewById(android.R.id.title);
            float fMeasureText = textView3.getPaint().measureText(textView3.getText().toString());
            TextView textView4 = (TextView) view2.findViewById(android.R.id.summary);
            float fMeasureText2 = textView4.getVisibility() == 8 ? 0.0f : textView4.getPaint().measureText(textView4.getText().toString());
            float paddingEnd2 = ((switchPreference.mWidth - view2.getPaddingEnd()) - view2.getPaddingStart()) - switchPreference.mContext.getResources().getDimensionPixelSize(R.dimen.sesl_preference_item_switch_size);
            if (fMeasureText >= paddingEnd2 || fMeasureText2 >= paddingEnd2) {
                viewFindViewById.setVisibility(0);
                viewFindViewById2.setVisibility(8);
                textView3.requestLayout();
                SwitchCompat switchCompat = (SwitchCompat) viewFindViewById3;
                if (!switchCompat.canHapticFeedback(switchPreference.mChecked) && switchPreference.mChecked != switchCompat.isChecked() && view2.hasWindowFocus() && SeslViewReflector.isVisibleToUser(view2) && !view2.isTemporarilyDetached()) {
                    switchCompat.performHapticFeedback(SeslHapticFeedbackConstantsReflector.semGetVibrationIndex(27));
                }
                switchPreference.syncSwitchView(viewFindViewById3);
                SwitchCompat switchCompat2 = (SwitchCompat) viewFindViewById4;
                switchCompat2.setOnCheckedChangeListener(null);
                switchCompat2.setCheckedWithoutAnimation(switchPreference.mChecked);
                return;
            }
            viewFindViewById2.setVisibility(0);
            viewFindViewById.setVisibility(8);
            textView3.requestLayout();
            SwitchCompat switchCompat3 = (SwitchCompat) viewFindViewById4;
            if (!switchCompat3.canHapticFeedback(switchPreference.mChecked) && switchPreference.mChecked != switchCompat3.isChecked() && view2.hasWindowFocus() && SeslViewReflector.isVisibleToUser(view2) && !view2.isTemporarilyDetached()) {
                switchCompat3.performHapticFeedback(SeslHapticFeedbackConstantsReflector.semGetVibrationIndex(27));
            }
            switchPreference.syncSwitchView(viewFindViewById4);
            SwitchCompat switchCompat4 = (SwitchCompat) viewFindViewById3;
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
        View view3 = preferenceViewHolder.itemView;
        View viewFindViewById5 = view3.findViewById(R.id.widget_frame);
        View viewFindViewById6 = view3.findViewById(android.R.id.widget_frame);
        View viewFindViewById7 = view3.findViewById(R.id.switch_widget);
        View viewFindViewById8 = view3.findViewById(android.R.id.switch_widget);
        Configuration configuration2 = switchPreferenceCompat.mContext.getResources().getConfiguration();
        int i4 = configuration2.screenWidthDp;
        int i5 = ((i4 > 320 || configuration2.fontScale < 1.1f) && (i4 >= 411 || configuration2.fontScale < 1.3f)) ? 2 : 1;
        if (i5 != 1) {
            if (switchPreferenceCompat.mIsLargeLayout != i5) {
                switchPreferenceCompat.mIsLargeLayout = i5;
                TextView textView5 = (TextView) view3.findViewById(android.R.id.title);
                viewFindViewById6.setVisibility(0);
                viewFindViewById5.setVisibility(8);
                textView5.requestLayout();
            }
            switchPreferenceCompat.syncSwitchView$1(viewFindViewById8);
            return;
        }
        switchPreferenceCompat.mIsLargeLayout = i5;
        TextView textView6 = (TextView) view3.findViewById(android.R.id.title);
        float fMeasureText3 = textView6.getPaint().measureText(textView6.getText().toString());
        TextView textView7 = (TextView) view3.findViewById(android.R.id.summary);
        float fMeasureText4 = textView7.getVisibility() == 8 ? 0.0f : textView7.getPaint().measureText(textView7.getText().toString());
        if (switchPreferenceCompat instanceof SeslSwitchPreferenceScreen) {
            dimensionPixelSize = switchPreferenceCompat.mContext.getResources().getDimensionPixelSize(R.dimen.sesl_preference_screen_item_switch_size);
            paddingEnd = viewFindViewById6.getPaddingEnd();
        } else {
            dimensionPixelSize = switchPreferenceCompat.mContext.getResources().getDimensionPixelSize(R.dimen.sesl_preference_item_switch_size);
            paddingEnd = viewFindViewById6.getPaddingEnd();
        }
        float paddingEnd3 = ((switchPreferenceCompat.mWidth - view3.getPaddingEnd()) - view3.getPaddingStart()) - (paddingEnd + dimensionPixelSize);
        if (fMeasureText3 >= paddingEnd3 || fMeasureText4 >= paddingEnd3) {
            viewFindViewById5.setVisibility(0);
            viewFindViewById6.setVisibility(8);
            textView6.requestLayout();
            SwitchCompat switchCompat5 = (SwitchCompat) viewFindViewById7;
            if (!switchCompat5.canHapticFeedback(switchPreferenceCompat.mChecked) && switchPreferenceCompat.mChecked != switchCompat5.isChecked() && view3.hasWindowFocus() && SeslViewReflector.isVisibleToUser(view3) && !view3.isTemporarilyDetached()) {
                switchCompat5.performHapticFeedback(SeslHapticFeedbackConstantsReflector.semGetVibrationIndex(27));
            }
            switchPreferenceCompat.syncSwitchView$1(viewFindViewById7);
            SwitchCompat switchCompat6 = (SwitchCompat) viewFindViewById8;
            switchCompat6.setOnCheckedChangeListener(null);
            switchCompat6.setCheckedWithoutAnimation(switchPreferenceCompat.mChecked);
            return;
        }
        viewFindViewById6.setVisibility(0);
        viewFindViewById5.setVisibility(8);
        textView6.requestLayout();
        SwitchCompat switchCompat7 = (SwitchCompat) viewFindViewById8;
        if (!switchCompat7.canHapticFeedback(switchPreferenceCompat.mChecked) && switchPreferenceCompat.mChecked != switchCompat7.isChecked() && view3.hasWindowFocus() && SeslViewReflector.isVisibleToUser(view3) && !view3.isTemporarilyDetached()) {
            switchCompat7.performHapticFeedback(SeslHapticFeedbackConstantsReflector.semGetVibrationIndex(27));
        }
        switchPreferenceCompat.syncSwitchView$1(viewFindViewById8);
        SwitchCompat switchCompat8 = (SwitchCompat) viewFindViewById7;
        switchCompat8.setOnCheckedChangeListener(null);
        switchCompat8.setCheckedWithoutAnimation(switchPreferenceCompat.mChecked);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        PreferenceResourceDescriptor preferenceResourceDescriptor = (PreferenceResourceDescriptor) this.mPreferenceResourceDescriptors.get(i);
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(viewGroup.getContext());
        this.mParent = viewGroup;
        View viewInflate = layoutInflaterFrom.inflate(preferenceResourceDescriptor.mLayoutResId, viewGroup, false);
        ViewGroup viewGroup2 = (ViewGroup) viewInflate.findViewById(android.R.id.widget_frame);
        if (viewGroup2 != null) {
            int i2 = preferenceResourceDescriptor.mWidgetLayoutResId;
            if (i2 != 0) {
                layoutInflaterFrom.inflate(i2, viewGroup2);
            } else {
                viewGroup2.setVisibility(8);
            }
        }
        View viewFindViewById = viewInflate.findViewById(R.id.badge_frame);
        if (viewFindViewById != null) {
            if (preferenceResourceDescriptor.mIsDotVisibled) {
                viewFindViewById.setVisibility(0);
            } else {
                viewFindViewById.setVisibility(8);
            }
        }
        return new PreferenceViewHolder(viewInflate);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int seslGetAccessibilityItemCount() {
        List list = this.mAccessibilityPositionTable;
        if (list != null && list.size() > 0) {
            return ((Integer) PreferenceGroupAdapter$$ExternalSyntheticOutline0.m(1, this.mAccessibilityPositionTable)).intValue() + 1;
        }
        Iterator it = this.mVisiblePreferences.iterator();
        int i = 0;
        while (it.hasNext()) {
            if (((Preference) it.next()).mLayoutResId == R.layout.sesl_preference_category_empty) {
                i++;
            }
        }
        return this.mVisiblePreferences.size() - i;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int seslGetAccessibilityItemPosition(int i) {
        List list = this.mAccessibilityPositionTable;
        if (list == null || i >= list.size()) {
            return -1;
        }
        return ((Integer) this.mAccessibilityPositionTable.get(i)).intValue();
    }

    public final void updatePreferences() {
        ArrayList arrayList = (ArrayList) this.mPreferences;
        int size = arrayList.size();
        int i = 0;
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            ((Preference) obj).mListener = null;
        }
        ArrayList arrayList2 = new ArrayList(((ArrayList) this.mPreferences).size());
        this.mPreferences = arrayList2;
        PreferenceGroup preferenceGroup = this.mPreferenceGroup;
        flattenPreferenceGroup(arrayList2, preferenceGroup);
        this.mVisiblePreferences = createVisiblePreferencesList(preferenceGroup);
        ArrayList arrayList3 = new ArrayList();
        ArrayList arrayList4 = (ArrayList) this.mVisiblePreferences;
        int size2 = arrayList4.size();
        int i3 = -1;
        int i4 = 0;
        while (i4 < size2) {
            Object obj2 = arrayList4.get(i4);
            i4++;
            if (((Preference) obj2).mLayoutResId != R.layout.sesl_preference_category_empty) {
                i3++;
            }
            arrayList3.add(Integer.valueOf(Math.max(i3, 0)));
        }
        if (arrayList3.size() > 0 && ((Integer) AlertController$$ExternalSyntheticOutline0.m(1, arrayList3)).intValue() >= ((ArrayList) this.mVisiblePreferences).size()) {
            Log.w("PreferenceGroupAdapter", "accessibilityPosition over visible size | last " + arrayList3.get(arrayList3.size() - 1) + " vsize " + ((ArrayList) this.mVisiblePreferences).size());
            for (int i5 = 0; i5 < arrayList3.size(); i5++) {
                arrayList3.set(i5, Integer.valueOf(i5));
            }
        }
        this.mAccessibilityPositionTable = arrayList3;
        notifyDataSetChanged();
        ArrayList arrayList5 = (ArrayList) this.mPreferences;
        int size3 = arrayList5.size();
        while (i < size3) {
            Object obj3 = arrayList5.get(i);
            i++;
            ((Preference) obj3).getClass();
        }
    }
}
