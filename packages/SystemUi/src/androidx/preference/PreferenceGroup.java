package androidx.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.AbsSavedState;
import androidx.collection.SimpleArrayMap;
import androidx.preference.Preference;
import androidx.preference.PreferenceGroupAdapter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class PreferenceGroup extends Preference {
    public boolean mAttachedToHierarchy;
    public final RunnableC04151 mClearRecycleCacheRunnable;
    public int mCurrentPreferenceOrder;
    public final Handler mHandler;
    public final SimpleArrayMap mIdRecycleCache;
    public int mInitialExpandedChildrenCount;
    public boolean mOrderingAsAdded;
    public final List mPreferences;

    /* JADX WARN: Type inference failed for: r2v0, types: [androidx.preference.PreferenceGroup$1] */
    public PreferenceGroup(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mIdRecycleCache = new SimpleArrayMap();
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mOrderingAsAdded = true;
        this.mCurrentPreferenceOrder = 0;
        this.mAttachedToHierarchy = false;
        this.mInitialExpandedChildrenCount = Integer.MAX_VALUE;
        this.mClearRecycleCacheRunnable = new Runnable() { // from class: androidx.preference.PreferenceGroup.1
            @Override // java.lang.Runnable
            public final void run() {
                synchronized (this) {
                    PreferenceGroup.this.mIdRecycleCache.clear();
                }
            }
        };
        this.mPreferences = new ArrayList();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.PreferenceGroup, i, i2);
        this.mOrderingAsAdded = obtainStyledAttributes.getBoolean(2, obtainStyledAttributes.getBoolean(2, true));
        if (obtainStyledAttributes.hasValue(1)) {
            setInitialExpandedChildrenCount(obtainStyledAttributes.getInt(1, obtainStyledAttributes.getInt(1, Integer.MAX_VALUE)));
        }
        obtainStyledAttributes.recycle();
    }

    public final void addPreference(Preference preference) {
        long j;
        if (((ArrayList) this.mPreferences).contains(preference)) {
            return;
        }
        if (preference.mKey != null) {
            PreferenceGroup preferenceGroup = this;
            while (true) {
                PreferenceGroup preferenceGroup2 = preferenceGroup.mParentGroup;
                if (preferenceGroup2 == null) {
                    break;
                } else {
                    preferenceGroup = preferenceGroup2;
                }
            }
            String str = preference.mKey;
            if (preferenceGroup.findPreference(str) != null) {
                Log.e("PreferenceGroup", "Found duplicated key: \"" + str + "\". This can cause unintended behaviour, please use unique keys for every preference.");
            }
        }
        int i = preference.mOrder;
        if (i == Integer.MAX_VALUE) {
            if (this.mOrderingAsAdded) {
                int i2 = this.mCurrentPreferenceOrder;
                this.mCurrentPreferenceOrder = i2 + 1;
                if (i2 != i) {
                    preference.mOrder = i2;
                    PreferenceGroupAdapter preferenceGroupAdapter = preference.mListener;
                    if (preferenceGroupAdapter != null) {
                        Handler handler = preferenceGroupAdapter.mHandler;
                        PreferenceGroupAdapter.RunnableC04171 runnableC04171 = preferenceGroupAdapter.mSyncRunnable;
                        handler.removeCallbacks(runnableC04171);
                        handler.post(runnableC04171);
                    }
                }
            }
            if (preference instanceof PreferenceGroup) {
                ((PreferenceGroup) preference).mOrderingAsAdded = this.mOrderingAsAdded;
            }
        }
        int binarySearch = Collections.binarySearch(this.mPreferences, preference);
        if (binarySearch < 0) {
            binarySearch = (binarySearch * (-1)) - 1;
        }
        boolean shouldDisableDependents = shouldDisableDependents();
        if (preference.mParentDependencyMet == shouldDisableDependents) {
            preference.mParentDependencyMet = !shouldDisableDependents;
            preference.notifyDependencyChange(preference.shouldDisableDependents());
            preference.notifyChanged();
        }
        synchronized (this) {
            ((ArrayList) this.mPreferences).add(binarySearch, preference);
        }
        PreferenceManager preferenceManager = this.mPreferenceManager;
        String str2 = preference.mKey;
        if (str2 == null || !this.mIdRecycleCache.containsKey(str2)) {
            synchronized (preferenceManager) {
                j = preferenceManager.mNextId;
                preferenceManager.mNextId = 1 + j;
            }
        } else {
            j = ((Long) this.mIdRecycleCache.get(str2)).longValue();
            this.mIdRecycleCache.remove(str2);
        }
        preference.mId = j;
        preference.mHasId = true;
        try {
            preference.onAttachedToHierarchy(preferenceManager);
            preference.mHasId = false;
            if (preference.mParentGroup != null) {
                throw new IllegalStateException("This preference already has a parent. You must remove the existing parent before assigning a new one.");
            }
            preference.mParentGroup = this;
            if (this.mAttachedToHierarchy) {
                preference.onAttached();
            }
            PreferenceGroupAdapter preferenceGroupAdapter2 = this.mListener;
            if (preferenceGroupAdapter2 != null) {
                Handler handler2 = preferenceGroupAdapter2.mHandler;
                PreferenceGroupAdapter.RunnableC04171 runnableC041712 = preferenceGroupAdapter2.mSyncRunnable;
                handler2.removeCallbacks(runnableC041712);
                handler2.post(runnableC041712);
            }
        } catch (Throwable th) {
            preference.mHasId = false;
            throw th;
        }
    }

    @Override // androidx.preference.Preference
    public final void dispatchRestoreInstanceState(Bundle bundle) {
        super.dispatchRestoreInstanceState(bundle);
        int preferenceCount = getPreferenceCount();
        for (int i = 0; i < preferenceCount; i++) {
            getPreference(i).dispatchRestoreInstanceState(bundle);
        }
    }

    @Override // androidx.preference.Preference
    public final void dispatchSaveInstanceState(Bundle bundle) {
        super.dispatchSaveInstanceState(bundle);
        int preferenceCount = getPreferenceCount();
        for (int i = 0; i < preferenceCount; i++) {
            getPreference(i).dispatchSaveInstanceState(bundle);
        }
    }

    public final Preference findPreference(CharSequence charSequence) {
        Preference findPreference;
        if (charSequence == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        if (TextUtils.equals(this.mKey, charSequence)) {
            return this;
        }
        int preferenceCount = getPreferenceCount();
        for (int i = 0; i < preferenceCount; i++) {
            Preference preference = getPreference(i);
            if (TextUtils.equals(preference.mKey, charSequence)) {
                return preference;
            }
            if ((preference instanceof PreferenceGroup) && (findPreference = ((PreferenceGroup) preference).findPreference(charSequence)) != null) {
                return findPreference;
            }
        }
        return null;
    }

    public final Preference getPreference(int i) {
        return (Preference) ((ArrayList) this.mPreferences).get(i);
    }

    public final int getPreferenceCount() {
        return ((ArrayList) this.mPreferences).size();
    }

    @Override // androidx.preference.Preference
    public final void notifyDependencyChange(boolean z) {
        super.notifyDependencyChange(z);
        int preferenceCount = getPreferenceCount();
        for (int i = 0; i < preferenceCount; i++) {
            Preference preference = getPreference(i);
            if (preference.mParentDependencyMet == z) {
                preference.mParentDependencyMet = !z;
                preference.notifyDependencyChange(preference.shouldDisableDependents());
                preference.notifyChanged();
            }
        }
    }

    @Override // androidx.preference.Preference
    public final void onAttached() {
        super.onAttached();
        this.mAttachedToHierarchy = true;
        int preferenceCount = getPreferenceCount();
        for (int i = 0; i < preferenceCount; i++) {
            getPreference(i).onAttached();
        }
    }

    @Override // androidx.preference.Preference
    public final void onDetached() {
        unregisterDependency();
        this.mAttachedToHierarchy = false;
        int preferenceCount = getPreferenceCount();
        for (int i = 0; i < preferenceCount; i++) {
            getPreference(i).onDetached();
        }
    }

    @Override // androidx.preference.Preference
    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (!parcelable.getClass().equals(SavedState.class)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        this.mInitialExpandedChildrenCount = savedState.mInitialExpandedChildrenCount;
        super.onRestoreInstanceState(savedState.getSuperState());
    }

    @Override // androidx.preference.Preference
    public final Parcelable onSaveInstanceState() {
        this.mBaseMethodCalled = true;
        return new SavedState(AbsSavedState.EMPTY_STATE, this.mInitialExpandedChildrenCount);
    }

    public final void removePreference(Preference preference) {
        synchronized (this) {
            try {
                preference.unregisterDependency();
                if (preference.mParentGroup == this) {
                    preference.mParentGroup = null;
                }
                if (((ArrayList) this.mPreferences).remove(preference)) {
                    String str = preference.mKey;
                    if (str != null) {
                        this.mIdRecycleCache.put(str, Long.valueOf(preference.getId()));
                        this.mHandler.removeCallbacks(this.mClearRecycleCacheRunnable);
                        this.mHandler.post(this.mClearRecycleCacheRunnable);
                    }
                    if (this.mAttachedToHierarchy) {
                        preference.onDetached();
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        PreferenceGroupAdapter preferenceGroupAdapter = this.mListener;
        if (preferenceGroupAdapter != null) {
            Handler handler = preferenceGroupAdapter.mHandler;
            PreferenceGroupAdapter.RunnableC04171 runnableC04171 = preferenceGroupAdapter.mSyncRunnable;
            handler.removeCallbacks(runnableC04171);
            handler.post(runnableC04171);
        }
    }

    public final void setInitialExpandedChildrenCount(int i) {
        if (i != Integer.MAX_VALUE && !(!TextUtils.isEmpty(this.mKey))) {
            Log.e("PreferenceGroup", getClass().getSimpleName().concat(" should have a key defined if it contains an expandable preference"));
        }
        this.mInitialExpandedChildrenCount = i;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SavedState extends Preference.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator() { // from class: androidx.preference.PreferenceGroup.SavedState.1
            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new SavedState[i];
            }
        };
        public final int mInitialExpandedChildrenCount;

        public SavedState(Parcel parcel) {
            super(parcel);
            this.mInitialExpandedChildrenCount = parcel.readInt();
        }

        @Override // android.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.mInitialExpandedChildrenCount);
        }

        public SavedState(Parcelable parcelable, int i) {
            super(parcelable);
            this.mInitialExpandedChildrenCount = i;
        }
    }

    public PreferenceGroup(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public PreferenceGroup(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }
}
