package com.android.server.devicepolicy;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.admin.flags.Flags;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.os.ServiceManager;
import android.provider.Telephony;
import android.text.TextUtils;
import android.util.ArraySet;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.IAccessibilityManager;
import android.view.inputmethod.InputMethodInfo;

import com.android.internal.telephony.SmsApplication;
import com.android.server.inputmethod.InputMethodManagerInternal;
import com.android.server.utils.Slogf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public final class PersonalAppsSuspensionHelper {
    public final Context mContext;
    public final PackageManager mPackageManager;

    public PersonalAppsSuspensionHelper(Context context) {
        this.mContext = context;
        this.mPackageManager = context.getPackageManager();
    }

    public final List getAccessibilityServices() {
        IBinder service = ServiceManager.getService("accessibility");
        IAccessibilityManager asInterface =
                service == null ? null : IAccessibilityManager.Stub.asInterface(service);
        Context context = this.mContext;
        AccessibilityManager accessibilityManager =
                new AccessibilityManager(context, asInterface, context.getUserId());
        try {
            List<AccessibilityServiceInfo> enabledAccessibilityServiceList =
                    accessibilityManager.getEnabledAccessibilityServiceList(-1);
            accessibilityManager.removeClient();
            ArrayList arrayList = new ArrayList();
            Iterator<AccessibilityServiceInfo> it = enabledAccessibilityServiceList.iterator();
            while (it.hasNext()) {
                ComponentName unflattenFromString =
                        ComponentName.unflattenFromString(it.next().getId());
                if (unflattenFromString != null) {
                    arrayList.add(unflattenFromString.getPackageName());
                }
            }
            return arrayList;
        } catch (Throwable th) {
            accessibilityManager.removeClient();
            throw th;
        }
    }

    public final List getInputMethodPackages() {
        List enabledInputMethodListAsUser =
                InputMethodManagerInternal.get()
                        .getEnabledInputMethodListAsUser(this.mContext.getUserId());
        ArrayList arrayList = new ArrayList();
        Iterator it = enabledInputMethodListAsUser.iterator();
        while (it.hasNext()) {
            arrayList.add(((InputMethodInfo) it.next()).getPackageName());
        }
        return arrayList;
    }

    public final String[] getPersonalAppsForSuspension() {
        String defaultSmsPackage;
        List<PackageInfo> installedPackages = this.mPackageManager.getInstalledPackages(786432);
        ArraySet arraySet = new ArraySet();
        for (PackageInfo packageInfo : installedPackages) {
            ApplicationInfo applicationInfo = packageInfo.applicationInfo;
            if (applicationInfo.isSystemApp() || applicationInfo.isUpdatedSystemApp()) {
                String str = packageInfo.packageName;
                Intent intent = new Intent("android.intent.action.MAIN");
                intent.addCategory("android.intent.category.LAUNCHER");
                intent.setPackage(str);
                List<ResolveInfo> queryIntentActivities =
                        this.mPackageManager.queryIntentActivities(intent, 786432);
                if (queryIntentActivities != null && !queryIntentActivities.isEmpty()) {}
            }
            arraySet.add(packageInfo.packageName);
        }
        arraySet.removeAll(Arrays.asList(this.mContext.getResources().getStringArray(17236281)));
        arraySet.removeAll(getSystemLauncherPackages());
        arraySet.removeAll(getAccessibilityServices());
        arraySet.removeAll(getInputMethodPackages());
        if (Flags.defaultSmsPersonalAppSuspensionFixEnabled()) {
            Context context = this.mContext;
            ComponentName defaultSmsApplicationAsUser =
                    SmsApplication.getDefaultSmsApplicationAsUser(
                            context, false, context.getUser());
            defaultSmsPackage =
                    defaultSmsApplicationAsUser != null
                            ? defaultSmsApplicationAsUser.getPackageName()
                            : null;
        } else {
            defaultSmsPackage = Telephony.Sms.getDefaultSmsPackage(this.mContext);
        }
        arraySet.remove(defaultSmsPackage);
        Intent intent2 = new Intent("android.settings.SETTINGS");
        intent2.addCategory("android.intent.category.DEFAULT");
        ResolveInfo resolveActivity = this.mPackageManager.resolveActivity(intent2, 786432);
        arraySet.remove(resolveActivity != null ? resolveActivity.activityInfo.packageName : null);
        for (String str2 :
                this.mPackageManager.getUnsuspendablePackages(
                        (String[]) arraySet.toArray(new String[0]))) {
            arraySet.remove(str2);
        }
        return (String[]) arraySet.toArray(new String[0]);
    }

    public final List getSystemLauncherPackages() {
        ArrayList arrayList = new ArrayList();
        for (ResolveInfo resolveInfo :
                this.mPackageManager.queryIntentActivities(
                        PersonalAppsSuspensionHelper$$ExternalSyntheticOutline0.m(
                                "android.intent.action.MAIN", "android.intent.category.HOME"),
                        786432)) {
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            if (activityInfo == null || TextUtils.isEmpty(activityInfo.packageName)) {
                Slogf.wtf(
                        "DevicePolicyManager",
                        "Could not find package name for launcher app %s",
                        resolveInfo);
            } else {
                String str = resolveInfo.activityInfo.packageName;
                try {
                    ApplicationInfo applicationInfo =
                            this.mPackageManager.getApplicationInfo(str, 786432);
                    if (applicationInfo.isSystemApp() || applicationInfo.isUpdatedSystemApp()) {
                        arrayList.add(str);
                    }
                } catch (PackageManager.NameNotFoundException unused) {
                    Slogf.e(
                            "DevicePolicyManager",
                            "Could not find application info for launcher app: %s",
                            str);
                }
            }
        }
        return arrayList;
    }
}
