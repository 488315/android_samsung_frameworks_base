package com.android.systemui.tuner;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.text.TextUtils;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragment;
import androidx.preference.SwitchPreference;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.tuner.ShortcutParser;
import com.android.systemui.tuner.TunerService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;

public class LockscreenFragment extends PreferenceFragment {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ArrayList mTunables = new ArrayList();
    public TunerService mTunerService;

    @Override // androidx.preference.PreferenceFragment
    public final void onCreatePreferences(String str) {
        this.mTunerService = (TunerService) Dependency.sDependency.getDependencyInner(TunerService.class);
        new Handler();
        addPreferencesFromResource(R.xml.lockscreen_settings);
        final Preference findPreference = findPreference("sysui_keyguard_left");
        final SwitchPreference switchPreference = (SwitchPreference) findPreference("sysui_keyguard_left_unlock");
        TunerService.Tunable tunable = new TunerService.Tunable() { // from class: com.android.systemui.tuner.LockscreenFragment$$ExternalSyntheticLambda0
            @Override // com.android.systemui.tuner.TunerService.Tunable
            public final void onTuningChanged(String str2, String str3) {
                ActivityInfo activityInfo;
                ShortcutParser.Shortcut shortcut;
                int i = LockscreenFragment.$r8$clinit;
                LockscreenFragment lockscreenFragment = LockscreenFragment.this;
                lockscreenFragment.getClass();
                switchPreference.setVisible(!TextUtils.isEmpty(str3));
                Preference preference = findPreference;
                if (str3 == null) {
                    preference.setSummary(R.string.lockscreen_none);
                    return;
                }
                if (!str3.contains("::")) {
                    if (!str3.contains("/")) {
                        preference.setSummary(R.string.lockscreen_none);
                        return;
                    }
                    try {
                        activityInfo = lockscreenFragment.getContext().getPackageManager().getActivityInfo(ComponentName.unflattenFromString(str3), 0);
                    } catch (PackageManager.NameNotFoundException unused) {
                        activityInfo = null;
                    }
                    preference.setSummary(activityInfo != null ? activityInfo.loadLabel(lockscreenFragment.getContext().getPackageManager()) : null);
                    return;
                }
                Context context = lockscreenFragment.getContext();
                String[] split = str3.split("::");
                try {
                    Iterator it = ((ArrayList) new ShortcutParser(context, new ComponentName(split[0], split[1])).getShortcuts()).iterator();
                    while (it.hasNext()) {
                        shortcut = (ShortcutParser.Shortcut) it.next();
                        if (shortcut.id.equals(split[2])) {
                            break;
                        }
                    }
                } catch (PackageManager.NameNotFoundException unused2) {
                }
                shortcut = null;
                preference.setSummary(shortcut != null ? shortcut.label : null);
            }
        };
        this.mTunables.add(tunable);
        this.mTunerService.addTunable(tunable, "sysui_keyguard_left");
        final Preference findPreference2 = findPreference("sysui_keyguard_right");
        final SwitchPreference switchPreference2 = (SwitchPreference) findPreference("sysui_keyguard_right_unlock");
        TunerService.Tunable tunable2 = new TunerService.Tunable() { // from class: com.android.systemui.tuner.LockscreenFragment$$ExternalSyntheticLambda0
            @Override // com.android.systemui.tuner.TunerService.Tunable
            public final void onTuningChanged(String str2, String str3) {
                ActivityInfo activityInfo;
                ShortcutParser.Shortcut shortcut;
                int i = LockscreenFragment.$r8$clinit;
                LockscreenFragment lockscreenFragment = LockscreenFragment.this;
                lockscreenFragment.getClass();
                switchPreference2.setVisible(!TextUtils.isEmpty(str3));
                Preference preference = findPreference2;
                if (str3 == null) {
                    preference.setSummary(R.string.lockscreen_none);
                    return;
                }
                if (!str3.contains("::")) {
                    if (!str3.contains("/")) {
                        preference.setSummary(R.string.lockscreen_none);
                        return;
                    }
                    try {
                        activityInfo = lockscreenFragment.getContext().getPackageManager().getActivityInfo(ComponentName.unflattenFromString(str3), 0);
                    } catch (PackageManager.NameNotFoundException unused) {
                        activityInfo = null;
                    }
                    preference.setSummary(activityInfo != null ? activityInfo.loadLabel(lockscreenFragment.getContext().getPackageManager()) : null);
                    return;
                }
                Context context = lockscreenFragment.getContext();
                String[] split = str3.split("::");
                try {
                    Iterator it = ((ArrayList) new ShortcutParser(context, new ComponentName(split[0], split[1])).getShortcuts()).iterator();
                    while (it.hasNext()) {
                        shortcut = (ShortcutParser.Shortcut) it.next();
                        if (shortcut.id.equals(split[2])) {
                            break;
                        }
                    }
                } catch (PackageManager.NameNotFoundException unused2) {
                }
                shortcut = null;
                preference.setSummary(shortcut != null ? shortcut.label : null);
            }
        };
        this.mTunables.add(tunable2);
        this.mTunerService.addTunable(tunable2, "sysui_keyguard_right");
    }

    @Override // android.app.Fragment
    public final void onDestroy() {
        super.onDestroy();
        this.mTunables.forEach(new Consumer() { // from class: com.android.systemui.tuner.LockscreenFragment$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                LockscreenFragment.this.mTunerService.removeTunable((TunerService.Tunable) obj);
            }
        });
    }
}
