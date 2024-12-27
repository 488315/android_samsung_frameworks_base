package com.android.systemui.statusbar;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.core.provider.FontProvider$$ExternalSyntheticOutline0;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.feature.SemFloatingFeature;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class KeyguardShortcutDialerUpdateManager {
    public final Context context;
    private final SettingsHelper settingsHelper;
    public final String tag = "KeyguardShortcutDialerUpdateManager";

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public KeyguardShortcutDialerUpdateManager(Context context, SettingsHelper settingsHelper) {
        this.context = context;
        this.settingsHelper = settingsHelper;
    }

    public final String getContactsPackageName(boolean z) {
        String string = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_CONTACTS_CONFIG_PACKAGE_NAME", "com.android.contacts");
        String string2 = SemCscFeature.getInstance().getString("CscFeature_Contact_ReplacePackageAs");
        Log.d(this.tag, FontProvider$$ExternalSyntheticOutline0.m("getContactsPackageName() packageName : ", string, ", packageNameCSC : ", string2));
        if (z) {
            string = "com.samsung.android.dialer";
        }
        return "com.andorid.contacts".equals(string) ? !"".equals(string2) ? string2 : "com.andorid.contacts" : string;
    }

    public final void updateLockShortcutDialerApp(Intent intent) {
        boolean z;
        String replace$default;
        String str;
        Collection collection;
        String shortcutAppList = this.settingsHelper.getShortcutAppList();
        String action = intent.getAction();
        boolean equals = "android.telecom.action.DEFAULT_DIALER_CHANGED".equals(action);
        boolean z2 = true;
        String str2 = this.tag;
        if (equals) {
            Bundle extras = intent.getExtras();
            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("default dialer : ", extras != null ? extras.getString("android.telecom.extra.CHANGE_DEFAULT_DIALER_PACKAGE_NAME") : null, str2);
            if (shortcutAppList == null) {
                Context context = this.context;
                boolean equals2 = "com.skt.prod.dialer".equals(((TelecomManager) context.getSystemService("telecom")).getDefaultDialerPackage());
                String concat = getContactsPackageName(true).concat("/com.samsung.android.dialer.DialtactsActivity");
                boolean isVoiceCapable = ((TelephonyManager) context.getSystemService("phone")).isVoiceCapable();
                if (equals2) {
                    concat = "com.skt.prod.dialer/com.skt.prod.dialer.activities.main.MainActivity";
                } else if (!isVoiceCapable) {
                    try {
                        context.getPackageManager().getApplicationInfo("com.sec.android.app.sbrowser", 128);
                        str = "com.sec.android.app.sbrowser/com.sec.android.app.sbrowser.SBrowserMainActivity";
                    } catch (PackageManager.NameNotFoundException unused) {
                        Log.d("Utils", "Package not found : com.sec.android.app.sbrowser");
                        str = "com.android.chrome/com.google.android.apps.chrome.Main";
                    }
                    concat = str;
                }
                shortcutAppList = ContentInViewNode$Request$$ExternalSyntheticOutline0.m("1;", concat, ";1;com.sec.android.app.camera/com.sec.android.app.camera.Camera");
                if (SemCscFeature.getInstance().getBoolean("CscFeature_Common_SupportDualApps")) {
                    List split = new Regex(";").split(shortcutAppList);
                    if (!split.isEmpty()) {
                        ListIterator listIterator = split.listIterator(split.size());
                        while (listIterator.hasPrevious()) {
                            if (((String) listIterator.previous()).length() != 0) {
                                collection = CollectionsKt___CollectionsKt.take(split, listIterator.nextIndex() + 1);
                                break;
                            }
                        }
                    }
                    collection = EmptyList.INSTANCE;
                    String[] strArr = (String[]) collection.toArray(new String[0]);
                    strArr[1] = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(strArr[1], "/0");
                    strArr[3] = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(strArr[3], "/0");
                    String str3 = "";
                    for (String str4 : strArr) {
                        str3 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str3, str4, ";");
                    }
                    Log.d(str2, str3);
                    shortcutAppList = str3;
                } else {
                    Log.d(str2, shortcutAppList);
                }
            }
            String contactsPackageName = getContactsPackageName(true);
            if ("com.skt.prod.dialer".equals(((TelecomManager) this.context.getSystemService("telecom")).getDefaultDialerPackage())) {
                Intrinsics.checkNotNull(shortcutAppList);
                replace$default = StringsKt__StringsJVMKt.replace$default(shortcutAppList, contactsPackageName.concat("/com.samsung.android.dialer.DialtactsActivity"), "com.skt.prod.dialer/com.skt.prod.dialer.activities.main.MainActivity");
            } else {
                Intrinsics.checkNotNull(shortcutAppList);
                replace$default = StringsKt__StringsJVMKt.replace$default(shortcutAppList, "com.skt.prod.dialer/com.skt.prod.dialer.activities.main.MainActivity", contactsPackageName.concat("/com.samsung.android.dialer.DialtactsActivity"));
            }
            shortcutAppList = replace$default;
        } else if (!PopupUIUtil.ACTION_BOOT_COMPLETED.equals(action) || shortcutAppList == null) {
            z2 = false;
        } else {
            String contactsPackageName2 = getContactsPackageName(false);
            if (StringsKt__StringsKt.contains(shortcutAppList, "com.samsung.android.contacts/com.samsung.android.contacts.contactslist.PeopleActivity", false) && contactsPackageName2.equals("com.samsung.android.app.contacts")) {
                shortcutAppList = StringsKt__StringsJVMKt.replace$default(shortcutAppList, "com.samsung.android.contacts/com.samsung.android.contacts.contactslist.PeopleActivity", "com.samsung.android.app.contacts/com.samsung.android.contacts.contactslist.PeopleActivity");
                z = true;
            } else {
                z = false;
            }
            if (StringsKt__StringsKt.contains(shortcutAppList, "com.samsung.android.contacts/com.android.contacts.activities.PeopleActivity", false) && contactsPackageName2.equals("com.samsung.android.app.contacts")) {
                shortcutAppList = StringsKt__StringsJVMKt.replace$default(shortcutAppList, "com.samsung.android.contacts/com.android.contacts.activities.PeopleActivity", "com.samsung.android.app.contacts/com.samsung.android.contacts.contactslist.PeopleActivity");
                z = true;
            }
            if (StringsKt__StringsKt.contains(shortcutAppList, "com.android.contacts/com.android.contacts.activities.PeopleActivity", false) && contactsPackageName2.equals("com.samsung.android.app.contacts")) {
                shortcutAppList = StringsKt__StringsJVMKt.replace$default(shortcutAppList, "com.android.contacts/com.android.contacts.activities.PeopleActivity", "com.samsung.android.app.contacts/com.samsung.android.contacts.contactslist.PeopleActivity");
                z = true;
            }
            if (StringsKt__StringsKt.contains(shortcutAppList, "com.samsung.contacts/com.android.contacts.activities.PeopleActivity", false) && contactsPackageName2.equals("com.samsung.android.app.contacts")) {
                shortcutAppList = StringsKt__StringsJVMKt.replace$default(shortcutAppList, "com.samsung.contacts/com.android.contacts.activities.PeopleActivity", "com.samsung.android.app.contacts/com.samsung.android.contacts.contactslist.PeopleActivity");
            } else {
                z2 = z;
            }
        }
        if (z2) {
            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("Updated Dialer App Info: ", shortcutAppList, str2);
            this.settingsHelper.setShortcutAppList(shortcutAppList);
        }
    }
}
