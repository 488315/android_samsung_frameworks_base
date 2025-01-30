package com.android.systemui.tuner;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.view.View;
import androidx.preference.PreferenceFragment;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceScreen;
import androidx.preference.PreferenceViewHolder;
import androidx.preference.SwitchPreference;
import com.android.internal.util.ArrayUtils;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.plugins.PluginEnablerImpl;
import com.android.systemui.plugins.PluginManager;
import com.android.systemui.shared.plugins.PluginEnabler;
import com.android.systemui.shared.plugins.PluginPrefs;
import com.android.systemui.tuner.PluginFragment;
import java.util.Iterator;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class PluginFragment extends PreferenceFragment {
    public static final /* synthetic */ int $r8$clinit = 0;
    public PluginEnablerImpl mPluginEnabler;
    public PluginPrefs mPluginPrefs;
    public final BroadcastReceiver mReceiver = new BroadcastReceiver() { // from class: com.android.systemui.tuner.PluginFragment.1
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            PluginFragment pluginFragment = PluginFragment.this;
            int i = PluginFragment.$r8$clinit;
            pluginFragment.loadPrefs();
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class PluginPreference extends SwitchPreference {
        public final boolean mHasSettings;
        public final PackageInfo mInfo;
        public final PluginEnabler mPluginEnabler;

        public PluginPreference(Context context, PackageInfo packageInfo, PluginEnabler pluginEnabler) {
            super(context);
            PackageManager packageManager = context.getPackageManager();
            boolean z = false;
            this.mHasSettings = packageManager.resolveActivity(new Intent("com.android.systemui.action.PLUGIN_SETTINGS").setPackage(packageInfo.packageName), 0) != null;
            this.mInfo = packageInfo;
            this.mPluginEnabler = pluginEnabler;
            setTitle(packageInfo.applicationInfo.loadLabel(packageManager));
            int i = 0;
            while (true) {
                if (i >= this.mInfo.services.length) {
                    z = true;
                    break;
                }
                PackageInfo packageInfo2 = this.mInfo;
                if (!this.mPluginEnabler.isEnabled(new ComponentName(packageInfo2.packageName, packageInfo2.services[i].name))) {
                    break;
                } else {
                    i++;
                }
            }
            setChecked(z);
            this.mWidgetLayoutResId = R.layout.tuner_widget_settings_switch;
        }

        @Override // androidx.preference.SwitchPreference, androidx.preference.Preference
        public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
            super.onBindViewHolder(preferenceViewHolder);
            preferenceViewHolder.findViewById(R.id.settings).setVisibility(this.mHasSettings ? 0 : 8);
            preferenceViewHolder.findViewById(R.id.divider).setVisibility(this.mHasSettings ? 0 : 8);
            preferenceViewHolder.findViewById(R.id.settings).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.tuner.PluginFragment$PluginPreference$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PluginFragment.PluginPreference pluginPreference = PluginFragment.PluginPreference.this;
                    pluginPreference.getClass();
                    ResolveInfo resolveActivity = view.getContext().getPackageManager().resolveActivity(new Intent("com.android.systemui.action.PLUGIN_SETTINGS").setPackage(pluginPreference.mInfo.packageName), 0);
                    if (resolveActivity != null) {
                        Context context = view.getContext();
                        Intent intent = new Intent();
                        ActivityInfo activityInfo = resolveActivity.activityInfo;
                        context.startActivity(intent.setComponent(new ComponentName(activityInfo.packageName, activityInfo.name)));
                    }
                }
            });
            preferenceViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.systemui.tuner.PluginFragment$PluginPreference$$ExternalSyntheticLambda1
                @Override // android.view.View.OnLongClickListener
                public final boolean onLongClick(View view) {
                    PluginFragment.PluginPreference pluginPreference = PluginFragment.PluginPreference.this;
                    pluginPreference.getClass();
                    Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                    intent.setData(Uri.fromParts("package", pluginPreference.mInfo.packageName, null));
                    pluginPreference.mContext.startActivity(intent);
                    return true;
                }
            });
        }

        @Override // androidx.preference.Preference
        public final void persistBoolean(boolean z) {
            PackageInfo packageInfo;
            int i = 0;
            boolean z2 = false;
            while (true) {
                packageInfo = this.mInfo;
                if (i >= packageInfo.services.length) {
                    break;
                }
                PackageInfo packageInfo2 = this.mInfo;
                ComponentName componentName = new ComponentName(packageInfo2.packageName, packageInfo2.services[i].name);
                if (this.mPluginEnabler.isEnabled(componentName) != z) {
                    z2 = true;
                    if (z) {
                        this.mPluginEnabler.setEnabled(componentName);
                    } else {
                        this.mPluginEnabler.setDisabled(componentName, 1);
                    }
                }
                i++;
            }
            if (z2) {
                String str = packageInfo.packageName;
                this.mContext.sendBroadcast(new Intent("com.android.systemui.action.PLUGIN_CHANGED", str != null ? Uri.fromParts("package", str, null) : null));
            }
        }
    }

    public final void loadPrefs() {
        final PluginManager pluginManager = (PluginManager) Dependency.get(PluginManager.class);
        PreferenceManager preferenceManager = this.mPreferenceManager;
        Context context = getContext();
        preferenceManager.getClass();
        final PreferenceScreen preferenceScreen = new PreferenceScreen(context, null);
        preferenceScreen.onAttachedToHierarchy(preferenceManager);
        preferenceScreen.mOrderingAsAdded = false;
        final Context context2 = this.mPreferenceManager.mContext;
        this.mPluginPrefs = new PluginPrefs(getContext());
        PackageManager packageManager = getContext().getPackageManager();
        PluginPrefs pluginPrefs = this.mPluginPrefs;
        pluginPrefs.getClass();
        ArraySet arraySet = new ArraySet(pluginPrefs.mPluginActions);
        final ArrayMap arrayMap = new ArrayMap();
        Iterator it = arraySet.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            String replace = str.replace("com.android.systemui.action.PLUGIN_", "");
            StringBuilder sb = new StringBuilder();
            for (String str2 : replace.split("_")) {
                if (sb.length() != 0) {
                    sb.append(' ');
                }
                sb.append(str2.substring(0, 1));
                sb.append(str2.substring(1).toLowerCase());
            }
            String sb2 = sb.toString();
            Iterator<ResolveInfo> it2 = packageManager.queryIntentServices(new Intent(str), 512).iterator();
            while (it2.hasNext()) {
                String str3 = it2.next().serviceInfo.packageName;
                if (!arrayMap.containsKey(str3)) {
                    arrayMap.put(str3, new ArraySet());
                }
                ((ArraySet) arrayMap.get(str3)).add(sb2);
            }
        }
        packageManager.getPackagesHoldingPermissions(new String[]{"com.android.systemui.permission.PLUGIN"}, 516).forEach(new Consumer() { // from class: com.android.systemui.tuner.PluginFragment$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                PluginFragment pluginFragment = PluginFragment.this;
                ArrayMap arrayMap2 = arrayMap;
                PluginManager pluginManager2 = pluginManager;
                Context context3 = context2;
                PreferenceScreen preferenceScreen2 = preferenceScreen;
                PackageInfo packageInfo = (PackageInfo) obj;
                int i = PluginFragment.$r8$clinit;
                pluginFragment.getClass();
                if (arrayMap2.containsKey(packageInfo.packageName) && !ArrayUtils.contains(pluginManager2.getPrivilegedPlugins(), packageInfo.packageName)) {
                    PluginFragment.PluginPreference pluginPreference = new PluginFragment.PluginPreference(context3, packageInfo, pluginFragment.mPluginEnabler);
                    StringBuilder sb3 = new StringBuilder("Plugins: ");
                    ArraySet arraySet2 = (ArraySet) arrayMap2.get(packageInfo.packageName);
                    StringBuilder sb4 = new StringBuilder();
                    Iterator it3 = arraySet2.iterator();
                    while (it3.hasNext()) {
                        String str4 = (String) it3.next();
                        if (sb4.length() != 0) {
                            sb4.append(", ");
                        }
                        sb4.append(str4);
                    }
                    sb3.append(sb4.toString());
                    pluginPreference.setSummary(sb3.toString());
                    preferenceScreen2.addPreference(pluginPreference);
                }
            }
        });
        setPreferenceScreen(preferenceScreen);
    }

    @Override // androidx.preference.PreferenceFragment, android.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addDataScheme("package");
        getContext().registerReceiver(this.mReceiver, intentFilter);
        getContext().registerReceiver(this.mReceiver, new IntentFilter("android.intent.action.USER_UNLOCKED"));
    }

    @Override // androidx.preference.PreferenceFragment
    public final void onCreatePreferences(String str) {
        this.mPluginEnabler = new PluginEnablerImpl(getContext());
        loadPrefs();
    }

    @Override // android.app.Fragment
    public final void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(this.mReceiver);
    }
}
