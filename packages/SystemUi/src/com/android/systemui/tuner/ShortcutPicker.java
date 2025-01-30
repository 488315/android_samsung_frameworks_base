package com.android.systemui.tuner;

import android.content.Context;
import android.content.pm.LauncherActivityInfo;
import android.content.pm.LauncherApps;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragment;
import androidx.preference.PreferenceGroupAdapter;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceScreen;
import androidx.preference.PreferenceViewHolder;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.tuner.ShortcutParser;
import com.android.systemui.tuner.TunerService;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class ShortcutPicker extends PreferenceFragment implements TunerService.Tunable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public String mKey;
    public SelectablePreference mNonePreference;
    public final ArrayList mSelectablePreferences = new ArrayList();
    public TunerService mTunerService;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class AppPreference extends SelectablePreference {
        public boolean mBinding;
        public final LauncherActivityInfo mInfo;

        public AppPreference(Context context, LauncherActivityInfo launcherActivityInfo) {
            super(context);
            this.mInfo = launcherActivityInfo;
            setTitle(context.getString(R.string.tuner_launch_app, launcherActivityInfo.getLabel()));
            setSummary(context.getString(R.string.tuner_app, launcherActivityInfo.getLabel()));
        }

        @Override // androidx.preference.Preference
        public final void notifyChanged() {
            if (this.mBinding) {
                return;
            }
            super.notifyChanged();
        }

        @Override // androidx.preference.CheckBoxPreference, androidx.preference.Preference
        public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
            this.mBinding = true;
            if (getIcon() == null) {
                setIcon(this.mInfo.getBadgedIcon(this.mContext.getResources().getConfiguration().densityDpi));
            }
            this.mBinding = false;
            super.onBindViewHolder(preferenceViewHolder);
        }

        @Override // com.android.systemui.tuner.SelectablePreference, androidx.preference.Preference
        public final String toString() {
            return this.mInfo.getComponentName().flattenToString();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class ShortcutPreference extends SelectablePreference {
        public boolean mBinding;
        public final ShortcutParser.Shortcut mShortcut;

        public ShortcutPreference(Context context, ShortcutParser.Shortcut shortcut, CharSequence charSequence) {
            super(context);
            this.mShortcut = shortcut;
            setTitle(shortcut.label);
            setSummary(context.getString(R.string.tuner_app, charSequence));
        }

        @Override // androidx.preference.Preference
        public final void notifyChanged() {
            if (this.mBinding) {
                return;
            }
            super.notifyChanged();
        }

        @Override // androidx.preference.CheckBoxPreference, androidx.preference.Preference
        public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
            this.mBinding = true;
            if (getIcon() == null) {
                setIcon(this.mShortcut.icon.loadDrawable(this.mContext));
            }
            this.mBinding = false;
            super.onBindViewHolder(preferenceViewHolder);
        }

        @Override // com.android.systemui.tuner.SelectablePreference, androidx.preference.Preference
        public final String toString() {
            return this.mShortcut.toString();
        }
    }

    @Override // android.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        if ("sysui_keyguard_left".equals(this.mKey)) {
            getActivity().setTitle(R.string.lockscreen_shortcut_left);
        } else {
            getActivity().setTitle(R.string.lockscreen_shortcut_right);
        }
    }

    @Override // androidx.preference.PreferenceFragment
    public final void onCreatePreferences(String str) {
        PreferenceManager preferenceManager = this.mPreferenceManager;
        Context context = preferenceManager.mContext;
        PreferenceScreen preferenceScreen = new PreferenceScreen(context, null);
        preferenceScreen.onAttachedToHierarchy(preferenceManager);
        preferenceScreen.mOrderingAsAdded = true;
        PreferenceCategory preferenceCategory = new PreferenceCategory(context);
        preferenceCategory.setTitle(R.string.tuner_other_apps);
        SelectablePreference selectablePreference = new SelectablePreference(context);
        this.mNonePreference = selectablePreference;
        this.mSelectablePreferences.add(selectablePreference);
        this.mNonePreference.setTitle(R.string.lockscreen_none);
        SelectablePreference selectablePreference2 = this.mNonePreference;
        selectablePreference2.setIcon(AppCompatResources.getDrawable(R.drawable.ic_remove_circle, selectablePreference2.mContext));
        selectablePreference2.mIconResId = R.drawable.ic_remove_circle;
        preferenceScreen.addPreference(this.mNonePreference);
        List<LauncherActivityInfo> activityList = ((LauncherApps) getContext().getSystemService(LauncherApps.class)).getActivityList(null, Process.myUserHandle());
        preferenceScreen.addPreference(preferenceCategory);
        activityList.forEach(new ShortcutPicker$$ExternalSyntheticLambda0(this, context, preferenceScreen, preferenceCategory));
        preferenceScreen.removePreference(preferenceCategory);
        for (int i = 0; i < preferenceCategory.getPreferenceCount(); i++) {
            Preference preference = preferenceCategory.getPreference(0);
            preferenceCategory.removePreference(preference);
            if (Integer.MAX_VALUE != preference.mOrder) {
                preference.mOrder = Integer.MAX_VALUE;
                PreferenceGroupAdapter preferenceGroupAdapter = preference.mListener;
                if (preferenceGroupAdapter != null) {
                    Handler handler = preferenceGroupAdapter.mHandler;
                    PreferenceGroupAdapter.RunnableC04171 runnableC04171 = preferenceGroupAdapter.mSyncRunnable;
                    handler.removeCallbacks(runnableC04171);
                    handler.post(runnableC04171);
                }
            }
            preferenceScreen.addPreference(preference);
        }
        setPreferenceScreen(preferenceScreen);
        this.mKey = getArguments().getString("androidx.preference.PreferenceFragmentCompat.PREFERENCE_ROOT");
        TunerService tunerService = (TunerService) Dependency.get(TunerService.class);
        this.mTunerService = tunerService;
        tunerService.addTunable(this, this.mKey);
    }

    @Override // android.app.Fragment
    public final void onDestroy() {
        super.onDestroy();
        this.mTunerService.removeTunable(this);
    }

    @Override // androidx.preference.PreferenceFragment, androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
    public final boolean onPreferenceTreeClick(Preference preference) {
        this.mTunerService.setValue(this.mKey, preference.toString());
        getActivity().onBackPressed();
        return true;
    }

    @Override // com.android.systemui.tuner.TunerService.Tunable
    public final void onTuningChanged(String str, final String str2) {
        if (str2 == null) {
            str2 = "";
        }
        this.mSelectablePreferences.forEach(new Consumer() { // from class: com.android.systemui.tuner.ShortcutPicker$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                String str3 = str2;
                SelectablePreference selectablePreference = (SelectablePreference) obj;
                int i = ShortcutPicker.$r8$clinit;
                selectablePreference.setChecked(str3.equals(selectablePreference.toString()));
            }
        });
    }
}
