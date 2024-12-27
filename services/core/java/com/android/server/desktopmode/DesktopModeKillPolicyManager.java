package com.android.server.desktopmode;

import com.samsung.android.knox.custom.KnoxCustomManagerService;

import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class DesktopModeKillPolicyManager {
    public static final Set KEEP_POLICY_PACKAGES =
            Set.of(
                    "com.google.android.apps.tachyon",
                    "com.google.android.music",
                    "com.google.android.videos",
                    "com.google.android.apps.photos",
                    "com.google.android.apps.docs",
                    "com.google.android.apps.docs.editors.docs",
                    "com.google.android.apps.docs.editors.sheets",
                    "com.google.android.apps.docs.editors.slides");
    public static final Set KILL_POLICY_PACKAGES =
            Set.of(
                    KnoxCustomManagerService.SBROWSER_CHAMELEON_PACKAGE_NAME,
                    "com.sec.android.inputmethod",
                    "com.sec.android.inputmethod.beta",
                    "com.sec.android.inputmethod.iwnnime.japan",
                    "com.microsoft.office.word",
                    "com.microsoft.office.excel",
                    "com.microsoft.office.powerpoint",
                    "com.facebook.katana");
}
