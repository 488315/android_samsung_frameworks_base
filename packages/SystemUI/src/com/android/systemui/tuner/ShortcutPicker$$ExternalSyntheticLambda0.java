package com.android.systemui.tuner;

import android.content.Context;
import android.content.pm.LauncherActivityInfo;
import android.content.pm.PackageManager;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;
import com.android.systemui.tuner.ShortcutParser;
import com.android.systemui.tuner.ShortcutPicker;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class ShortcutPicker$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ ShortcutPicker f$0;
    public final /* synthetic */ Context f$1;
    public final /* synthetic */ PreferenceScreen f$2;
    public final /* synthetic */ Object f$3;

    public /* synthetic */ ShortcutPicker$$ExternalSyntheticLambda0(ShortcutPicker shortcutPicker, Context context, LauncherActivityInfo launcherActivityInfo, PreferenceScreen preferenceScreen) {
        this.f$0 = shortcutPicker;
        this.f$1 = context;
        this.f$3 = launcherActivityInfo;
        this.f$2 = preferenceScreen;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ShortcutPicker shortcutPicker = this.f$0;
                Context context = this.f$1;
                PreferenceScreen preferenceScreen = this.f$2;
                PreferenceCategory preferenceCategory = (PreferenceCategory) this.f$3;
                LauncherActivityInfo launcherActivityInfo = (LauncherActivityInfo) obj;
                int i = ShortcutPicker.$r8$clinit;
                shortcutPicker.getClass();
                try {
                    List shortcuts = new ShortcutParser(shortcutPicker.getContext(), launcherActivityInfo.getComponentName()).getShortcuts();
                    ShortcutPicker.AppPreference appPreference = new ShortcutPicker.AppPreference(context, launcherActivityInfo);
                    shortcutPicker.mSelectablePreferences.add(appPreference);
                    ArrayList arrayList = (ArrayList) shortcuts;
                    if (arrayList.size() != 0) {
                        preferenceScreen.addPreference(appPreference);
                        arrayList.forEach(new ShortcutPicker$$ExternalSyntheticLambda0(shortcutPicker, context, launcherActivityInfo, preferenceScreen));
                    } else {
                        preferenceCategory.addPreference(appPreference);
                    }
                    break;
                } catch (PackageManager.NameNotFoundException unused) {
                    return;
                }
            default:
                ShortcutPicker shortcutPicker2 = this.f$0;
                Context context2 = this.f$1;
                LauncherActivityInfo launcherActivityInfo2 = (LauncherActivityInfo) this.f$3;
                PreferenceScreen preferenceScreen2 = this.f$2;
                int i2 = ShortcutPicker.$r8$clinit;
                shortcutPicker2.getClass();
                ShortcutPicker.ShortcutPreference shortcutPreference = new ShortcutPicker.ShortcutPreference(context2, (ShortcutParser.Shortcut) obj, launcherActivityInfo2.getLabel());
                shortcutPicker2.mSelectablePreferences.add(shortcutPreference);
                preferenceScreen2.addPreference(shortcutPreference);
                break;
        }
    }

    public /* synthetic */ ShortcutPicker$$ExternalSyntheticLambda0(ShortcutPicker shortcutPicker, Context context, PreferenceScreen preferenceScreen, PreferenceCategory preferenceCategory) {
        this.f$0 = shortcutPicker;
        this.f$1 = context;
        this.f$2 = preferenceScreen;
        this.f$3 = preferenceCategory;
    }
}
