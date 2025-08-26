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
import com.android.settingslib.volume.MediaSessions$H$$ExternalSyntheticOutline0;
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

/* loaded from: classes3.dex */
public final class KeyguardShortcutDialerUpdateManager {
    public final Context context;
    private final SettingsHelper settingsHelper;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
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
        MediaSessions$H$$ExternalSyntheticOutline0.m("getContactsPackageName() packageName : ", string, ", packageNameCSC : ", string2, "KeyguardShortcutDialerUpdateManager");
        if (z) {
            string = "com.samsung.android.dialer";
        }
        return "com.andorid.contacts".equals(string) ? !"".equals(string2) ? string2 : "com.andorid.contacts" : string;
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x0107 A[LOOP:1: B:34:0x0105->B:35:0x0107, LOOP_END] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateLockShortcutDialerApp(Intent intent) {
        boolean z;
        String str;
        Collection collectionTake;
        String shortcutAppList = this.settingsHelper.getShortcutAppList();
        String action = intent.getAction();
        boolean z2 = true;
        if ("android.telecom.action.DEFAULT_DIALER_CHANGED".equals(action)) {
            Bundle extras = intent.getExtras();
            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("default dialer : ", extras != null ? extras.getString("android.telecom.extra.CHANGE_DEFAULT_DIALER_PACKAGE_NAME") : null, "KeyguardShortcutDialerUpdateManager");
            if (shortcutAppList == null) {
                Context context = this.context;
                boolean zEquals = "com.skt.prod.dialer".equals(((TelecomManager) context.getSystemService("telecom")).getDefaultDialerPackage());
                boolean zEquals2 = "com.lguplus.aicallagent".equals(((TelecomManager) this.context.getSystemService("telecom")).getDefaultDialerPackage());
                String strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(getContactsPackageName(true), "/com.samsung.android.dialer.DialtactsActivity");
                boolean zIsVoiceCapable = ((TelephonyManager) context.getSystemService("phone")).isVoiceCapable();
                if (zEquals2) {
                    strM = "com.lguplus.aicallagent/com.lguplus.aicallagent.MainActivity";
                } else if (zEquals) {
                    strM = "com.skt.prod.dialer/com.skt.prod.dialer.activities.main.MainActivity";
                } else if (!zIsVoiceCapable) {
                    try {
                        context.getPackageManager().getApplicationInfo("com.sec.android.app.sbrowser", 128).getClass();
                        str = "com.sec.android.app.sbrowser/com.sec.android.app.sbrowser.SBrowserMainActivity";
                    } catch (PackageManager.NameNotFoundException unused) {
                        Log.d("Utils", "Package not found : com.sec.android.app.sbrowser");
                        str = "com.android.chrome/com.google.android.apps.chrome.Main";
                    }
                    strM = str;
                }
                shortcutAppList = ContentInViewNode$Request$$ExternalSyntheticOutline0.m("1;", strM, ";1;com.sec.android.app.camera/com.sec.android.app.camera.Camera");
                if (SemCscFeature.getInstance().getBoolean("CscFeature_Common_SupportDualApps")) {
                    List listSplit = new Regex(";").split(shortcutAppList);
                    if (listSplit.isEmpty()) {
                        collectionTake = EmptyList.INSTANCE;
                        String[] strArr = (String[]) collectionTake.toArray(new String[0]);
                        strArr[1] = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(strArr[1], "/0");
                        strArr[3] = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(strArr[3], "/0");
                        String str2 = "";
                        while (i < r11) {
                        }
                        Log.d("KeyguardShortcutDialerUpdateManager", str2);
                        shortcutAppList = str2;
                    } else {
                        ListIterator listIterator = listSplit.listIterator(listSplit.size());
                        while (listIterator.hasPrevious()) {
                            if (((String) listIterator.previous()).length() != 0) {
                                collectionTake = CollectionsKt___CollectionsKt.take(listSplit, listIterator.nextIndex() + 1);
                                break;
                            }
                        }
                        collectionTake = EmptyList.INSTANCE;
                        String[] strArr2 = (String[]) collectionTake.toArray(new String[0]);
                        strArr2[1] = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(strArr2[1], "/0");
                        strArr2[3] = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(strArr2[3], "/0");
                        String str22 = "";
                        for (String str3 : strArr2) {
                            str3.getClass();
                            str22 = str22 + str3 + ";";
                        }
                        Log.d("KeyguardShortcutDialerUpdateManager", str22);
                        shortcutAppList = str22;
                    }
                } else {
                    Log.d("KeyguardShortcutDialerUpdateManager", shortcutAppList);
                }
            }
            String contactsPackageName = getContactsPackageName(true);
            String strM2 = StringsKt__StringsKt.contains(shortcutAppList, "com.skt.prod.dialer", false) ? "com.skt.prod.dialer/com.skt.prod.dialer.activities.main.MainActivity" : StringsKt__StringsKt.contains(shortcutAppList, "com.lguplus.aicallagent", false) ? "com.lguplus.aicallagent/com.lguplus.aicallagent.MainActivity" : AbstractResolvableFuture$$ExternalSyntheticOutline0.m(getContactsPackageName(true), "/com.samsung.android.dialer.DialtactsActivity");
            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("current dialer pkg:  ", strM2, "KeyguardShortcutDialerUpdateManager");
            if ("com.skt.prod.dialer".equals(((TelecomManager) this.context.getSystemService("telecom")).getDefaultDialerPackage())) {
                shortcutAppList = StringsKt__StringsJVMKt.replace$default(shortcutAppList, strM2, "com.skt.prod.dialer/com.skt.prod.dialer.activities.main.MainActivity");
            } else if ("com.lguplus.aicallagent".equals(((TelecomManager) this.context.getSystemService("telecom")).getDefaultDialerPackage())) {
                shortcutAppList = StringsKt__StringsJVMKt.replace$default(shortcutAppList, strM2, "com.lguplus.aicallagent/com.lguplus.aicallagent.MainActivity");
            } else {
                shortcutAppList = StringsKt__StringsJVMKt.replace$default(shortcutAppList, strM2, contactsPackageName + "/com.samsung.android.dialer.DialtactsActivity");
            }
        } else if (!PopupUIUtil.ACTION_BOOT_COMPLETED.equals(action) || shortcutAppList == null) {
            z2 = false;
        } else {
            String contactsPackageName2 = getContactsPackageName(false);
            if (StringsKt__StringsKt.contains(shortcutAppList, "com.samsung.android.contacts/com.samsung.android.contacts.contactslist.PeopleActivity", false) && Intrinsics.areEqual(contactsPackageName2, "com.samsung.android.app.contacts")) {
                shortcutAppList = StringsKt__StringsJVMKt.replace$default(shortcutAppList, "com.samsung.android.contacts/com.samsung.android.contacts.contactslist.PeopleActivity", "com.samsung.android.app.contacts/com.samsung.android.contacts.contactslist.PeopleActivity");
                z = true;
            } else {
                z = false;
            }
            if (StringsKt__StringsKt.contains(shortcutAppList, "com.samsung.android.contacts/com.android.contacts.activities.PeopleActivity", false) && Intrinsics.areEqual(contactsPackageName2, "com.samsung.android.app.contacts")) {
                shortcutAppList = StringsKt__StringsJVMKt.replace$default(shortcutAppList, "com.samsung.android.contacts/com.android.contacts.activities.PeopleActivity", "com.samsung.android.app.contacts/com.samsung.android.contacts.contactslist.PeopleActivity");
                z = true;
            }
            if (StringsKt__StringsKt.contains(shortcutAppList, "com.android.contacts/com.android.contacts.activities.PeopleActivity", false) && Intrinsics.areEqual(contactsPackageName2, "com.samsung.android.app.contacts")) {
                shortcutAppList = StringsKt__StringsJVMKt.replace$default(shortcutAppList, "com.android.contacts/com.android.contacts.activities.PeopleActivity", "com.samsung.android.app.contacts/com.samsung.android.contacts.contactslist.PeopleActivity");
                z = true;
            }
            if (StringsKt__StringsKt.contains(shortcutAppList, "com.samsung.contacts/com.android.contacts.activities.PeopleActivity", false) && Intrinsics.areEqual(contactsPackageName2, "com.samsung.android.app.contacts")) {
                shortcutAppList = StringsKt__StringsJVMKt.replace$default(shortcutAppList, "com.samsung.contacts/com.android.contacts.activities.PeopleActivity", "com.samsung.android.app.contacts/com.samsung.android.contacts.contactslist.PeopleActivity");
            } else {
                z2 = z;
            }
        }
        if (z2) {
            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("Updated Dialer App Info: ", shortcutAppList, "KeyguardShortcutDialerUpdateManager");
            this.settingsHelper.setShortcutAppList(shortcutAppList);
        }
    }
}
