package com.android.systemui.theme;

import android.content.om.OverlayIdentifier;
import android.content.om.OverlayInfo;
import android.content.om.OverlayManager;
import android.content.om.OverlayManagerTransaction;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.Log;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.google.android.collect.Lists;
import com.google.android.collect.Sets;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ThemeOverlayApplier implements Dumpable {
    static final String ANDROID_PACKAGE = "android";
    static final String SETTINGS_PACKAGE = "com.android.settings";
    static final String SYSUI_PACKAGE = "com.android.systemui";
    public final Executor mBgExecutor;
    public final Map mCategoryToTargetPackage;
    public final String mLauncherPackage;
    public final Executor mMainExecutor;
    public final OverlayManager mOverlayManager;
    public final Map mTargetPackageToCategories;
    public final String mThemePickerPackage;
    public static final boolean DEBUG = Log.isLoggable("ThemeOverlayApplier", 3);
    static final String OVERLAY_CATEGORY_ICON_LAUNCHER = "android.theme.customization.icon_pack.launcher";
    static final String OVERLAY_CATEGORY_SHAPE = "android.theme.customization.adaptive_icon_shape";
    static final String OVERLAY_CATEGORY_FONT = "android.theme.customization.font";
    static final String OVERLAY_CATEGORY_ICON_ANDROID = "android.theme.customization.icon_pack.android";
    static final String OVERLAY_CATEGORY_ICON_SYSUI = "android.theme.customization.icon_pack.systemui";
    static final String OVERLAY_CATEGORY_ICON_SETTINGS = "android.theme.customization.icon_pack.settings";
    static final String OVERLAY_CATEGORY_ICON_THEME_PICKER = "android.theme.customization.icon_pack.themepicker";
    public static final List THEME_CATEGORIES = Lists.newArrayList(new String[]{"android.theme.customization.system_palette", OVERLAY_CATEGORY_ICON_LAUNCHER, OVERLAY_CATEGORY_SHAPE, OVERLAY_CATEGORY_FONT, "android.theme.customization.accent_color", "android.theme.customization.dynamic_color", OVERLAY_CATEGORY_ICON_ANDROID, OVERLAY_CATEGORY_ICON_SYSUI, OVERLAY_CATEGORY_ICON_SETTINGS, OVERLAY_CATEGORY_ICON_THEME_PICKER});
    static final Set<String> SYSTEM_USER_CATEGORIES = Sets.newHashSet(new String[]{"android.theme.customization.system_palette", "android.theme.customization.accent_color", "android.theme.customization.dynamic_color", OVERLAY_CATEGORY_FONT, OVERLAY_CATEGORY_SHAPE, OVERLAY_CATEGORY_ICON_ANDROID, OVERLAY_CATEGORY_ICON_SYSUI});

    public ThemeOverlayApplier(OverlayManager overlayManager, Executor executor, String str, String str2, DumpManager dumpManager, Executor executor2) {
        ArrayMap arrayMap = new ArrayMap();
        this.mTargetPackageToCategories = arrayMap;
        ArrayMap arrayMap2 = new ArrayMap();
        this.mCategoryToTargetPackage = arrayMap2;
        this.mOverlayManager = overlayManager;
        this.mBgExecutor = executor;
        this.mMainExecutor = executor2;
        this.mLauncherPackage = str;
        this.mThemePickerPackage = str2;
        arrayMap.put(ANDROID_PACKAGE, Sets.newHashSet(new String[]{"android.theme.customization.system_palette", "android.theme.customization.accent_color", "android.theme.customization.dynamic_color", OVERLAY_CATEGORY_FONT, OVERLAY_CATEGORY_SHAPE, OVERLAY_CATEGORY_ICON_ANDROID}));
        arrayMap.put(SYSUI_PACKAGE, Sets.newHashSet(new String[]{OVERLAY_CATEGORY_ICON_SYSUI}));
        arrayMap.put("com.android.settings", Sets.newHashSet(new String[]{OVERLAY_CATEGORY_ICON_SETTINGS}));
        arrayMap.put(str, Sets.newHashSet(new String[]{OVERLAY_CATEGORY_ICON_LAUNCHER}));
        arrayMap.put(str2, Sets.newHashSet(new String[]{OVERLAY_CATEGORY_ICON_THEME_PICKER}));
        arrayMap2.put("android.theme.customization.accent_color", ANDROID_PACKAGE);
        arrayMap2.put("android.theme.customization.dynamic_color", ANDROID_PACKAGE);
        arrayMap2.put(OVERLAY_CATEGORY_FONT, ANDROID_PACKAGE);
        arrayMap2.put(OVERLAY_CATEGORY_SHAPE, ANDROID_PACKAGE);
        arrayMap2.put(OVERLAY_CATEGORY_ICON_ANDROID, ANDROID_PACKAGE);
        arrayMap2.put(OVERLAY_CATEGORY_ICON_SYSUI, SYSUI_PACKAGE);
        arrayMap2.put(OVERLAY_CATEGORY_ICON_SETTINGS, "com.android.settings");
        arrayMap2.put(OVERLAY_CATEGORY_ICON_LAUNCHER, str);
        arrayMap2.put(OVERLAY_CATEGORY_ICON_THEME_PICKER, str2);
        dumpManager.registerDumpable("ThemeOverlayApplier", this);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("mTargetPackageToCategories=" + this.mTargetPackageToCategories);
        printWriter.println("mCategoryToTargetPackage=" + this.mCategoryToTargetPackage);
    }

    public OverlayManagerTransaction.Builder getTransactionBuilder() {
        return new OverlayManagerTransaction.Builder();
    }

    public final void setEnabled(OverlayManagerTransaction.Builder builder, OverlayIdentifier overlayIdentifier, String str, int i, Set set, boolean z, boolean z2) {
        if (DEBUG) {
            StringBuilder sb = new StringBuilder("setEnabled: ");
            sb.append(overlayIdentifier.getPackageName());
            sb.append(" category: ");
            sb.append(str);
            sb.append(": ");
            ActionBarContextView$$ExternalSyntheticOutline0.m(sb, z, "ThemeOverlayApplier");
        }
        if (this.mOverlayManager.getOverlayInfo(overlayIdentifier, UserHandle.of(i)) == null && !z2) {
            Log.i("ThemeOverlayApplier", "Won't enable " + overlayIdentifier + ", it doesn't exist for user" + i);
            return;
        }
        builder.setEnabled(overlayIdentifier, z, i);
        UserHandle userHandle = UserHandle.SYSTEM;
        if (i != userHandle.getIdentifier() && SYSTEM_USER_CATEGORIES.contains(str)) {
            builder.setEnabled(overlayIdentifier, z, userHandle.getIdentifier());
        }
        OverlayInfo overlayInfo = this.mOverlayManager.getOverlayInfo(overlayIdentifier, userHandle);
        if (overlayInfo == null || overlayInfo.targetPackageName.equals(this.mLauncherPackage) || overlayInfo.targetPackageName.equals(this.mThemePickerPackage)) {
            return;
        }
        Iterator it = ((HashSet) set).iterator();
        while (it.hasNext()) {
            builder.setEnabled(overlayIdentifier, z, ((UserHandle) it.next()).getIdentifier());
        }
    }
}
