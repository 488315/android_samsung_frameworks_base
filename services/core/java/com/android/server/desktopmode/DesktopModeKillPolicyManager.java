package com.android.server.desktopmode;

import android.os.Bundle;
import android.util.IndentingPrintWriter;
import com.samsung.android.desktopmode.DesktopModeFeature;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import java.io.Serializable;
import java.util.Set;

/* loaded from: classes2.dex */
public abstract class DesktopModeKillPolicyManager {
    public static final String TAG = "[DMS_POLICY]" + DesktopModeKillPolicyManager.class.getSimpleName();
    public static final Set KEEP_POLICY_PACKAGES = Set.of("com.google.android.apps.tachyon", "com.google.android.music", "com.google.android.videos", "com.google.android.apps.photos", "com.google.android.apps.docs", "com.google.android.apps.docs.editors.docs", "com.google.android.apps.docs.editors.sheets", "com.google.android.apps.docs.editors.slides");
    public static final Set KILL_POLICY_PACKAGES = Set.of(KnoxCustomManagerService.SBROWSER_CHAMELEON_PACKAGE_NAME, "com.sec.android.inputmethod", "com.sec.android.inputmethod.beta", "com.sec.android.inputmethod.iwnnime.japan", "com.microsoft.office.word", "com.microsoft.office.excel", "com.microsoft.office.powerpoint", "com.facebook.katana");

    public static Bundle getDesktopModeKillPolicy() {
        Bundle bundle = new Bundle();
        bundle.putString("name", "DeX");
        bundle.putSerializable("kill_packages", (Serializable) KILL_POLICY_PACKAGES);
        bundle.putSerializable("keep_packages", (Serializable) KEEP_POLICY_PACKAGES);
        bundle.putBoolean("skip_sdk_version_check", true);
        if (DesktopModeFeature.DEBUG) {
            Log.m7d(TAG, "getDesktopModeKillPolicy(), args=" + bundle);
        }
        return bundle;
    }

    public static void dump(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("Current " + DesktopModeKillPolicyManager.class.getSimpleName() + " state:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("KEEP_POLICY_PACKAGES (" + KEEP_POLICY_PACKAGES.size() + ")");
        indentingPrintWriter.println("KILL_POLICY_PACKAGES (" + KILL_POLICY_PACKAGES.size() + ")");
        indentingPrintWriter.decreaseIndent();
    }
}
