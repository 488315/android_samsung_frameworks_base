package com.android.systemui.theme;

import android.content.om.FabricatedOverlay;
import android.content.om.OverlayIdentifier;
import android.content.om.OverlayInfo;
import android.content.om.OverlayManager;
import android.content.om.OverlayManagerTransaction;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.google.android.collect.Lists;
import com.google.android.collect.Sets;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ThemeOverlayApplier implements Dumpable {
    static final String ANDROID_PACKAGE = "android";
    static final String SETTINGS_PACKAGE = "com.android.settings";
    static final String SYSUI_PACKAGE = "com.android.systemui";
    public final Executor mBgExecutor;
    public final Map mCategoryToTargetPackage;
    public final String mLauncherPackage;
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

    public ThemeOverlayApplier(OverlayManager overlayManager, Executor executor, String str, String str2, DumpManager dumpManager) {
        ArrayMap arrayMap = new ArrayMap();
        this.mTargetPackageToCategories = arrayMap;
        ArrayMap arrayMap2 = new ArrayMap();
        this.mCategoryToTargetPackage = arrayMap2;
        this.mOverlayManager = overlayManager;
        this.mBgExecutor = executor;
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

    public final void applyCurrentUserOverlays(final Map map, final FabricatedOverlay[] fabricatedOverlayArr, final int i, final Set set) {
        this.mBgExecutor.execute(new Runnable() { // from class: com.android.systemui.theme.ThemeOverlayApplier$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                final ThemeOverlayApplier themeOverlayApplier = ThemeOverlayApplier.this;
                final Map map2 = map;
                FabricatedOverlay[] fabricatedOverlayArr2 = fabricatedOverlayArr;
                int i2 = i;
                Set set2 = set;
                themeOverlayApplier.getClass();
                List<String> list = ThemeOverlayApplier.THEME_CATEGORIES;
                final HashSet hashSet = new HashSet(list);
                Set set3 = (Set) hashSet.stream().map(new Function() { // from class: com.android.systemui.theme.ThemeOverlayApplier$$ExternalSyntheticLambda1
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return (String) ((ArrayMap) ThemeOverlayApplier.this.mCategoryToTargetPackage).get((String) obj);
                    }
                }).collect(Collectors.toSet());
                final ArrayList arrayList = new ArrayList();
                set3.forEach(new Consumer() { // from class: com.android.systemui.theme.ThemeOverlayApplier$$ExternalSyntheticLambda2
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ThemeOverlayApplier themeOverlayApplier2 = ThemeOverlayApplier.this;
                        arrayList.addAll(themeOverlayApplier2.mOverlayManager.getOverlayInfosForTarget((String) obj, UserHandle.SYSTEM));
                    }
                });
                final int i3 = 0;
                final int i4 = 1;
                Stream filter = arrayList.stream().filter(new Predicate() { // from class: com.android.systemui.theme.ThemeOverlayApplier$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        switch (i3) {
                            case 0:
                                OverlayInfo overlayInfo = (OverlayInfo) obj;
                                return ((Set) ((ArrayMap) ((ThemeOverlayApplier) themeOverlayApplier).mTargetPackageToCategories).get(overlayInfo.targetPackageName)).contains(overlayInfo.category);
                            case 1:
                                return ((Set) themeOverlayApplier).contains(((OverlayInfo) obj).category);
                            default:
                                return !((Map) themeOverlayApplier).containsValue(new OverlayIdentifier(((OverlayInfo) obj).packageName));
                        }
                    }
                }).filter(new Predicate() { // from class: com.android.systemui.theme.ThemeOverlayApplier$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        switch (i4) {
                            case 0:
                                OverlayInfo overlayInfo = (OverlayInfo) obj;
                                return ((Set) ((ArrayMap) ((ThemeOverlayApplier) hashSet).mTargetPackageToCategories).get(overlayInfo.targetPackageName)).contains(overlayInfo.category);
                            case 1:
                                return ((Set) hashSet).contains(((OverlayInfo) obj).category);
                            default:
                                return !((Map) hashSet).containsValue(new OverlayIdentifier(((OverlayInfo) obj).packageName));
                        }
                    }
                });
                final int i5 = 2;
                List<Pair> list2 = (List) filter.filter(new Predicate() { // from class: com.android.systemui.theme.ThemeOverlayApplier$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        switch (i5) {
                            case 0:
                                OverlayInfo overlayInfo = (OverlayInfo) obj;
                                return ((Set) ((ArrayMap) ((ThemeOverlayApplier) map2).mTargetPackageToCategories).get(overlayInfo.targetPackageName)).contains(overlayInfo.category);
                            case 1:
                                return ((Set) map2).contains(((OverlayInfo) obj).category);
                            default:
                                return !((Map) map2).containsValue(new OverlayIdentifier(((OverlayInfo) obj).packageName));
                        }
                    }
                }).filter(new ThemeOverlayApplier$$ExternalSyntheticLambda4()).map(new ThemeOverlayApplier$$ExternalSyntheticLambda5()).collect(Collectors.toList());
                OverlayManagerTransaction.Builder transactionBuilder = themeOverlayApplier.getTransactionBuilder();
                HashSet hashSet2 = new HashSet();
                if (fabricatedOverlayArr2 != null) {
                    int length = fabricatedOverlayArr2.length;
                    while (i3 < length) {
                        FabricatedOverlay fabricatedOverlay = fabricatedOverlayArr2[i3];
                        hashSet2.add(fabricatedOverlay.getIdentifier());
                        transactionBuilder.registerFabricatedOverlay(fabricatedOverlay);
                        i3++;
                    }
                }
                for (Pair pair : list2) {
                    OverlayIdentifier overlayIdentifier = new OverlayIdentifier((String) pair.second);
                    themeOverlayApplier.setEnabled(transactionBuilder, overlayIdentifier, (String) pair.first, i2, set2, false, hashSet2.contains(overlayIdentifier));
                }
                for (String str : list) {
                    if (map2.containsKey(str)) {
                        OverlayIdentifier overlayIdentifier2 = (OverlayIdentifier) map2.get(str);
                        themeOverlayApplier.setEnabled(transactionBuilder, overlayIdentifier2, str, i2, set2, true, hashSet2.contains(overlayIdentifier2));
                    }
                }
                try {
                    themeOverlayApplier.mOverlayManager.commit(transactionBuilder.build());
                } catch (IllegalStateException | SecurityException e) {
                    Log.e("ThemeOverlayApplier", "setEnabled failed", e);
                }
            }
        });
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
            ActionBarContextView$$ExternalSyntheticOutline0.m9m(sb, z, "ThemeOverlayApplier");
        }
        UserHandle of = UserHandle.of(i);
        OverlayManager overlayManager = this.mOverlayManager;
        if (overlayManager.getOverlayInfo(overlayIdentifier, of) == null && !z2) {
            Log.i("ThemeOverlayApplier", "Won't enable " + overlayIdentifier + ", it doesn't exist for user" + i);
            return;
        }
        builder.setEnabled(overlayIdentifier, z, i);
        if (i != UserHandle.SYSTEM.getIdentifier() && SYSTEM_USER_CATEGORIES.contains(str)) {
            builder.setEnabled(overlayIdentifier, z, UserHandle.SYSTEM.getIdentifier());
        }
        OverlayInfo overlayInfo = overlayManager.getOverlayInfo(overlayIdentifier, UserHandle.SYSTEM);
        if (overlayInfo == null || overlayInfo.targetPackageName.equals(this.mLauncherPackage) || overlayInfo.targetPackageName.equals(this.mThemePickerPackage)) {
            return;
        }
        Iterator it = set.iterator();
        while (it.hasNext()) {
            builder.setEnabled(overlayIdentifier, z, ((UserHandle) it.next()).getIdentifier());
        }
    }
}
