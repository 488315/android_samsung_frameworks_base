package androidx.preference;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.AbsSavedState;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.res.TypedArrayUtils;
import androidx.preference.PreferenceGroupAdapter;
import androidx.preference.PreferenceManager;
import com.android.systemui.R;
import com.android.systemui.util.SettingsHelper;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class Preference implements Comparable<Preference> {
    public final boolean mAllowDividerAbove;
    public final boolean mAllowDividerBelow;
    public boolean mBaseMethodCalled;
    public final AnonymousClass1 mClickListener;
    public final Context mContext;
    public final boolean mCopyingEnabled;
    public final Object mDefaultValue;
    public final String mDependencyKey;
    public boolean mDependencyMet;
    public List mDependents;
    public boolean mEnabled;
    public Bundle mExtras;
    public final String mFragment;
    public boolean mHasId;
    public final boolean mHasSingleLineTitleAttr;
    public Drawable mIcon;
    public int mIconResId;
    public boolean mIconSpaceReserved;
    public long mId;
    public Intent mIntent;
    public final boolean mIsDotVisible;
    public boolean mIsPreferenceRoundedBg;
    public boolean mIsRoundChanged;
    public View mItemView;
    public String mKey;
    public int mLayoutResId;
    public PreferenceGroupAdapter mListener;
    public OnPreferenceChangeListener mOnChangeListener;
    public OnPreferenceClickListener mOnClickListener;
    public OnPreferenceCopyListener mOnCopyListener;
    public int mOrder;
    public boolean mParentDependencyMet;
    public PreferenceGroup mParentGroup;
    public boolean mPersistent;
    public PreferenceManager mPreferenceManager;
    public boolean mRequiresKey;
    public boolean mSelectable;
    public boolean mShouldDisableView;
    public final boolean mSingleLineTitle;
    public boolean mSubheaderRound;
    public CharSequence mSummary;
    public SummaryProvider mSummaryProvider;
    public final ColorStateList mTextColorSecondary;
    public CharSequence mTitle;
    public boolean mVisible;
    public int mWhere;
    public int mWidgetLayoutResId;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class BaseSavedState extends AbsSavedState {
        public static final Parcelable.Creator<BaseSavedState> CREATOR = new Parcelable.Creator() { // from class: androidx.preference.Preference.BaseSavedState.1
            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                return new BaseSavedState(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new BaseSavedState[i];
            }
        };

        public BaseSavedState(Parcel parcel) {
            super(parcel);
        }

        public BaseSavedState(Parcelable parcelable) {
            super(parcelable);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface OnPreferenceChangeListener {
        boolean onPreferenceChange(Preference preference, Object obj);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface OnPreferenceClickListener {
        void onPreferenceClick(Preference preference);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class OnPreferenceCopyListener implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        public final Preference mPreference;

        public OnPreferenceCopyListener(Preference preference) {
            this.mPreference = preference;
        }

        @Override // android.view.View.OnCreateContextMenuListener
        public final void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            CharSequence summary = this.mPreference.getSummary();
            if (!this.mPreference.mCopyingEnabled || TextUtils.isEmpty(summary)) {
                return;
            }
            contextMenu.setHeaderTitle(summary);
            contextMenu.add(0, 0, 0, R.string.copy).setOnMenuItemClickListener(this);
        }

        @Override // android.view.MenuItem.OnMenuItemClickListener
        public final boolean onMenuItemClick(MenuItem menuItem) {
            ClipboardManager clipboardManager = (ClipboardManager) this.mPreference.mContext.getSystemService("clipboard");
            CharSequence summary = this.mPreference.getSummary();
            clipboardManager.setPrimaryClip(ClipData.newPlainText("Preference", summary));
            Context context = this.mPreference.mContext;
            Toast.makeText(context, context.getString(R.string.preference_copied, summary), 0).show();
            return true;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface SummaryProvider {
        CharSequence provideSummary(Preference preference);
    }

    /* JADX WARN: Type inference failed for: r2v1, types: [androidx.preference.Preference$1] */
    public Preference(Context context, AttributeSet attributeSet, int i, int i2) {
        this.mOrder = Integer.MAX_VALUE;
        this.mEnabled = true;
        this.mSelectable = true;
        this.mPersistent = true;
        this.mDependencyMet = true;
        this.mParentDependencyMet = true;
        this.mVisible = true;
        this.mAllowDividerAbove = true;
        this.mAllowDividerBelow = true;
        this.mSingleLineTitle = true;
        this.mShouldDisableView = true;
        this.mLayoutResId = R.layout.sesl_preference;
        this.mClickListener = new View.OnClickListener() { // from class: androidx.preference.Preference.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Preference.this.performClick(view);
            }
        };
        this.mIsPreferenceRoundedBg = false;
        this.mSubheaderRound = false;
        this.mWhere = 0;
        this.mIsRoundChanged = false;
        this.mContext = context;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.Preference, i, i2);
        this.mIconResId = obtainStyledAttributes.getResourceId(23, obtainStyledAttributes.getResourceId(0, 0));
        String string = obtainStyledAttributes.getString(27);
        this.mKey = string == null ? obtainStyledAttributes.getString(6) : string;
        CharSequence text = obtainStyledAttributes.getText(35);
        this.mTitle = text == null ? obtainStyledAttributes.getText(4) : text;
        CharSequence text2 = obtainStyledAttributes.getText(34);
        this.mSummary = text2 == null ? obtainStyledAttributes.getText(7) : text2;
        this.mOrder = obtainStyledAttributes.getInt(29, obtainStyledAttributes.getInt(8, Integer.MAX_VALUE));
        String string2 = obtainStyledAttributes.getString(22);
        this.mFragment = string2 == null ? obtainStyledAttributes.getString(13) : string2;
        this.mLayoutResId = obtainStyledAttributes.getResourceId(28, obtainStyledAttributes.getResourceId(3, R.layout.preference));
        this.mWidgetLayoutResId = obtainStyledAttributes.getResourceId(36, obtainStyledAttributes.getResourceId(9, 0));
        this.mIsDotVisible = obtainStyledAttributes.getBoolean(25, obtainStyledAttributes.getBoolean(25, false));
        this.mEnabled = obtainStyledAttributes.getBoolean(21, obtainStyledAttributes.getBoolean(2, true));
        this.mSelectable = obtainStyledAttributes.getBoolean(31, obtainStyledAttributes.getBoolean(5, true));
        this.mPersistent = obtainStyledAttributes.getBoolean(30, obtainStyledAttributes.getBoolean(1, true));
        String string3 = obtainStyledAttributes.getString(19);
        this.mDependencyKey = string3 == null ? obtainStyledAttributes.getString(10) : string3;
        this.mAllowDividerAbove = obtainStyledAttributes.getBoolean(16, obtainStyledAttributes.getBoolean(16, this.mSelectable));
        this.mAllowDividerBelow = obtainStyledAttributes.getBoolean(17, obtainStyledAttributes.getBoolean(17, this.mSelectable));
        if (obtainStyledAttributes.hasValue(18)) {
            this.mDefaultValue = onGetDefaultValue(obtainStyledAttributes, 18);
        } else if (obtainStyledAttributes.hasValue(11)) {
            this.mDefaultValue = onGetDefaultValue(obtainStyledAttributes, 11);
        }
        this.mShouldDisableView = obtainStyledAttributes.getBoolean(32, obtainStyledAttributes.getBoolean(12, true));
        boolean hasValue = obtainStyledAttributes.hasValue(33);
        this.mHasSingleLineTitleAttr = hasValue;
        if (hasValue) {
            this.mSingleLineTitle = obtainStyledAttributes.getBoolean(33, obtainStyledAttributes.getBoolean(14, true));
        }
        this.mIconSpaceReserved = obtainStyledAttributes.getBoolean(24, obtainStyledAttributes.getBoolean(15, false));
        this.mVisible = obtainStyledAttributes.getBoolean(26, obtainStyledAttributes.getBoolean(26, true));
        this.mCopyingEnabled = obtainStyledAttributes.getBoolean(20, obtainStyledAttributes.getBoolean(20, false));
        obtainStyledAttributes.recycle();
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.textColorSecondary, typedValue, true);
        if (typedValue.resourceId > 0) {
            this.mTextColorSecondary = context.getResources().getColorStateList(typedValue.resourceId);
        }
    }

    public static void setEnabledStateOnViews(View view, boolean z) {
        view.setEnabled(z);
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                setEnabledStateOnViews(viewGroup.getChildAt(childCount), z);
            }
        }
    }

    private void setLineBreakWordStyle(TextView textView) {
        textView.setLineBreakWordStyle(1);
    }

    public final boolean callChangeListener(Object obj) {
        OnPreferenceChangeListener onPreferenceChangeListener = this.mOnChangeListener;
        return onPreferenceChangeListener == null || onPreferenceChangeListener.onPreferenceChange(this, obj);
    }

    public void callClickListener() {
        OnPreferenceClickListener onPreferenceClickListener = this.mOnClickListener;
        if (onPreferenceClickListener != null) {
            onPreferenceClickListener.onPreferenceClick(this);
        }
    }

    @Override // java.lang.Comparable
    public final int compareTo(Preference preference) {
        Preference preference2 = preference;
        int i = this.mOrder;
        int i2 = preference2.mOrder;
        if (i != i2) {
            return i - i2;
        }
        CharSequence charSequence = this.mTitle;
        CharSequence charSequence2 = preference2.mTitle;
        if (charSequence == charSequence2) {
            return 0;
        }
        if (charSequence == null) {
            return 1;
        }
        if (charSequence2 == null) {
            return -1;
        }
        return charSequence.toString().compareToIgnoreCase(preference2.mTitle.toString());
    }

    public void dispatchRestoreInstanceState(Bundle bundle) {
        Parcelable parcelable;
        if (TextUtils.isEmpty(this.mKey) || (parcelable = bundle.getParcelable(this.mKey)) == null) {
            return;
        }
        this.mBaseMethodCalled = false;
        onRestoreInstanceState(parcelable);
        if (!this.mBaseMethodCalled) {
            throw new IllegalStateException("Derived class did not call super.onRestoreInstanceState()");
        }
    }

    public void dispatchSaveInstanceState(Bundle bundle) {
        if (TextUtils.isEmpty(this.mKey)) {
            return;
        }
        this.mBaseMethodCalled = false;
        Parcelable onSaveInstanceState = onSaveInstanceState();
        if (!this.mBaseMethodCalled) {
            throw new IllegalStateException("Derived class did not call super.onSaveInstanceState()");
        }
        if (onSaveInstanceState != null) {
            bundle.putParcelable(this.mKey, onSaveInstanceState);
        }
    }

    public final Drawable getIcon() {
        int i;
        if (this.mIcon == null && (i = this.mIconResId) != 0) {
            this.mIcon = AppCompatResources.getDrawable(i, this.mContext);
        }
        return this.mIcon;
    }

    public long getId() {
        return this.mId;
    }

    public final int getPersistedInt(int i) {
        return !shouldPersist() ? i : this.mPreferenceManager.getSharedPreferences().getInt(this.mKey, i);
    }

    public final String getPersistedString(String str) {
        return !shouldPersist() ? str : this.mPreferenceManager.getSharedPreferences().getString(this.mKey, str);
    }

    public CharSequence getSummary() {
        SummaryProvider summaryProvider = this.mSummaryProvider;
        return summaryProvider != null ? summaryProvider.provideSummary(this) : this.mSummary;
    }

    public CharSequence getTitle() {
        return this.mTitle;
    }

    public boolean isEnabled() {
        return this.mEnabled && this.mDependencyMet && this.mParentDependencyMet;
    }

    public final boolean isTalkBackIsRunning$1() {
        String string;
        AccessibilityManager accessibilityManager = (AccessibilityManager) this.mContext.getSystemService("accessibility");
        if (accessibilityManager == null || !accessibilityManager.isEnabled() || (string = Settings.Secure.getString(this.mContext.getContentResolver(), SettingsHelper.INDEX_ENABLED_ACCESSIBILITY_SERVICES)) == null) {
            return false;
        }
        return string.matches("(?i).*com.samsung.accessibility/com.samsung.android.app.talkback.TalkBackService.*") || string.matches("(?i).*com.samsung.android.accessibility.talkback/com.samsung.android.marvin.talkback.TalkBackService.*") || string.matches("(?i).*com.google.android.marvin.talkback.TalkBackService.*") || string.matches("(?i).*com.samsung.accessibility/com.samsung.accessibility.universalswitch.UniversalSwitchService.*");
    }

    public void notifyChanged() {
        int indexOf;
        PreferenceGroupAdapter preferenceGroupAdapter = this.mListener;
        if (preferenceGroupAdapter == null || (indexOf = preferenceGroupAdapter.mVisiblePreferences.indexOf(this)) == -1) {
            return;
        }
        preferenceGroupAdapter.notifyItemChanged(indexOf, this);
    }

    public void notifyDependencyChange(boolean z) {
        List list = this.mDependents;
        if (list == null) {
            return;
        }
        ArrayList arrayList = (ArrayList) list;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            Preference preference = (Preference) arrayList.get(i);
            if (preference.mDependencyMet == z) {
                preference.mDependencyMet = !z;
                preference.notifyDependencyChange(preference.shouldDisableDependents());
                preference.notifyChanged();
            }
        }
    }

    public void onAttached() {
        PreferenceScreen preferenceScreen;
        if (TextUtils.isEmpty(this.mDependencyKey)) {
            return;
        }
        String str = this.mDependencyKey;
        PreferenceManager preferenceManager = this.mPreferenceManager;
        Preference preference = null;
        if (preferenceManager != null && (preferenceScreen = preferenceManager.mPreferenceScreen) != null) {
            preference = preferenceScreen.findPreference(str);
        }
        if (preference == null) {
            throw new IllegalStateException("Dependency \"" + this.mDependencyKey + "\" not found for preference \"" + this.mKey + "\" (title: \"" + ((Object) this.mTitle) + "\"");
        }
        if (preference.mDependents == null) {
            preference.mDependents = new ArrayList();
        }
        ((ArrayList) preference.mDependents).add(this);
        boolean shouldDisableDependents = preference.shouldDisableDependents();
        if (this.mDependencyMet == shouldDisableDependents) {
            this.mDependencyMet = !shouldDisableDependents;
            notifyDependencyChange(shouldDisableDependents());
            notifyChanged();
        }
    }

    public void onAttachedToHierarchy(PreferenceManager preferenceManager) {
        long j;
        this.mPreferenceManager = preferenceManager;
        if (!this.mHasId) {
            synchronized (preferenceManager) {
                j = preferenceManager.mNextId;
                preferenceManager.mNextId = 1 + j;
            }
            this.mId = j;
        }
        if (shouldPersist()) {
            PreferenceManager preferenceManager2 = this.mPreferenceManager;
            if ((preferenceManager2 != null ? preferenceManager2.getSharedPreferences() : null).contains(this.mKey)) {
                onSetInitialValue(null, true);
                return;
            }
        }
        Object obj = this.mDefaultValue;
        if (obj != null) {
            onSetInitialValue(obj, false);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00aa  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00e0  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00e9  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x00fd  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x012a  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x012d  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0105  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onBindViewHolder(androidx.preference.PreferenceViewHolder r9) {
        /*
            Method dump skipped, instructions count: 320
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.preference.Preference.onBindViewHolder(androidx.preference.PreferenceViewHolder):void");
    }

    public void onDetached() {
        unregisterDependency();
    }

    public Object onGetDefaultValue(TypedArray typedArray, int i) {
        return null;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        this.mBaseMethodCalled = true;
        if (parcelable != AbsSavedState.EMPTY_STATE && parcelable != null) {
            throw new IllegalArgumentException("Wrong state class -- expecting Preference State");
        }
    }

    public Parcelable onSaveInstanceState() {
        this.mBaseMethodCalled = true;
        return AbsSavedState.EMPTY_STATE;
    }

    public void onSetInitialValue(Object obj) {
    }

    public void performClick(View view) {
        performClick();
    }

    public void persistBoolean(boolean z) {
        if (shouldPersist()) {
            boolean z2 = !z;
            if (shouldPersist()) {
                z2 = this.mPreferenceManager.getSharedPreferences().getBoolean(this.mKey, z2);
            }
            if (z == z2) {
                return;
            }
            SharedPreferences.Editor editor = this.mPreferenceManager.getEditor();
            editor.putBoolean(this.mKey, z);
            if (this.mPreferenceManager.mNoCommit) {
                return;
            }
            editor.apply();
        }
    }

    public final void persistInt(int i) {
        if (shouldPersist() && i != getPersistedInt(~i)) {
            SharedPreferences.Editor editor = this.mPreferenceManager.getEditor();
            editor.putInt(this.mKey, i);
            if (this.mPreferenceManager.mNoCommit) {
                return;
            }
            editor.apply();
        }
    }

    public void persistString(String str) {
        if (shouldPersist() && !TextUtils.equals(str, getPersistedString(null))) {
            SharedPreferences.Editor editor = this.mPreferenceManager.getEditor();
            editor.putString(this.mKey, str);
            if (this.mPreferenceManager.mNoCommit) {
                return;
            }
            editor.apply();
        }
    }

    public void setEnabled(boolean z) {
        if (this.mEnabled != z) {
            this.mEnabled = z;
            notifyDependencyChange(shouldDisableDependents());
            notifyChanged();
        }
    }

    public void setIcon(Drawable drawable) {
        if (this.mIcon != drawable) {
            this.mIcon = drawable;
            this.mIconResId = 0;
            notifyChanged();
        }
    }

    public final void setKey(String str) {
        this.mKey = str;
        if (this.mRequiresKey && TextUtils.isEmpty(str)) {
            if (TextUtils.isEmpty(this.mKey)) {
                throw new IllegalStateException("Preference does not have a key assigned.");
            }
            this.mRequiresKey = true;
        }
    }

    public final void setOrder(int i) {
        if (i != this.mOrder) {
            this.mOrder = i;
            PreferenceGroupAdapter preferenceGroupAdapter = this.mListener;
            if (preferenceGroupAdapter != null) {
                PreferenceGroupAdapter.AnonymousClass1 anonymousClass1 = preferenceGroupAdapter.mSyncRunnable;
                Handler handler = preferenceGroupAdapter.mHandler;
                handler.removeCallbacks(anonymousClass1);
                handler.post(anonymousClass1);
            }
        }
    }

    public final void setSelectable(boolean z) {
        if (this.mSelectable != z) {
            this.mSelectable = z;
            notifyChanged();
        }
    }

    public void setSummary(int i) {
        setSummary(this.mContext.getString(i));
    }

    public void setTitle(CharSequence charSequence) {
        if (TextUtils.equals(charSequence, this.mTitle)) {
            return;
        }
        this.mTitle = charSequence;
        notifyChanged();
    }

    public final void setVisible(boolean z) {
        if (this.mVisible != z) {
            this.mVisible = z;
            PreferenceGroupAdapter preferenceGroupAdapter = this.mListener;
            if (preferenceGroupAdapter != null) {
                PreferenceGroupAdapter.AnonymousClass1 anonymousClass1 = preferenceGroupAdapter.mSyncRunnable;
                Handler handler = preferenceGroupAdapter.mHandler;
                handler.removeCallbacks(anonymousClass1);
                handler.post(anonymousClass1);
            }
        }
    }

    public boolean shouldDisableDependents() {
        return !isEnabled();
    }

    public final boolean shouldPersist() {
        return (this.mPreferenceManager == null || !this.mPersistent || TextUtils.isEmpty(this.mKey)) ? false : true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        CharSequence title = getTitle();
        if (!TextUtils.isEmpty(title)) {
            sb.append(title);
            sb.append(' ');
        }
        CharSequence summary = getSummary();
        if (!TextUtils.isEmpty(summary)) {
            sb.append(summary);
            sb.append(' ');
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }

    public final void unregisterDependency() {
        List list;
        PreferenceScreen preferenceScreen;
        String str = this.mDependencyKey;
        if (str != null) {
            PreferenceManager preferenceManager = this.mPreferenceManager;
            Preference preference = null;
            if (preferenceManager != null && (preferenceScreen = preferenceManager.mPreferenceScreen) != null) {
                preference = preferenceScreen.findPreference(str);
            }
            if (preference == null || (list = preference.mDependents) == null) {
                return;
            }
            ((ArrayList) list).remove(this);
        }
    }

    public void onSetInitialValue(Object obj, boolean z) {
        onSetInitialValue(obj);
    }

    public void performClick() {
        Intent intent;
        PreferenceManager.OnPreferenceTreeClickListener onPreferenceTreeClickListener;
        if (isEnabled() && this.mSelectable) {
            onClick();
            OnPreferenceClickListener onPreferenceClickListener = this.mOnClickListener;
            if (onPreferenceClickListener != null) {
                onPreferenceClickListener.onPreferenceClick(this);
                return;
            }
            PreferenceManager preferenceManager = this.mPreferenceManager;
            if ((preferenceManager == null || (onPreferenceTreeClickListener = preferenceManager.mOnPreferenceTreeClickListener) == null || !onPreferenceTreeClickListener.onPreferenceTreeClick(this)) && (intent = this.mIntent) != null) {
                this.mContext.startActivity(intent);
            }
        }
    }

    public void setSummary(CharSequence charSequence) {
        if (this.mSummaryProvider != null) {
            throw new IllegalStateException("Preference already has a SummaryProvider set.");
        }
        if (TextUtils.equals(this.mSummary, charSequence)) {
            return;
        }
        this.mSummary = charSequence;
        notifyChanged();
    }

    public final void setTitle(int i) {
        setTitle(this.mContext.getString(i));
    }

    public void onClick() {
    }

    public Preference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public Preference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, TypedArrayUtils.getAttr(context, R.attr.preferenceStyle, android.R.attr.preferenceStyle));
    }

    public Preference(Context context) {
        this(context, null);
    }
}
