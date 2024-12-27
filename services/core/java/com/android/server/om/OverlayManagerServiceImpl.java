package com.android.server.om;

import android.content.om.OverlayIdentifier;
import android.content.om.OverlayInfo;
import android.content.om.OverlayInfoExt;
import android.content.pm.UserPackage;
import android.content.pm.overlay.OverlayPaths;
import android.content.pm.parsing.FrameworkParsingPackageUtils;
import android.os.Environment;
import android.os.FabricatedOverlayInfo;
import android.os.FabricatedOverlayInternal;
import android.os.FileUtils;
import android.os.IIdmap2;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import com.android.internal.content.om.OverlayConfig;
import com.android.internal.util.CollectionUtils;
import com.android.internal.util.IndentingPrintWriter;
import com.android.server.AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.CustomizedBinderCallsStatsInternal$$ExternalSyntheticOutline0;
import com.android.server.SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.am.ActiveServices$$ExternalSyntheticOutline0;
import com.android.server.attention.AttentionManagerService$AttentionCheck$$ExternalSyntheticOutline0;
import com.android.server.om.IdmapDaemon;
import com.android.server.om.OverlayManagerService;
import com.android.server.om.OverlayManagerSettings;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.AndroidPackageSplit;
import com.android.server.pm.pkg.PackageState;
import com.android.server.pm.pkg.PackageStateInternal;
import com.samsung.android.server.pm.mm.MaintenanceModeManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class OverlayManagerServiceImpl {
    public final String[] mDefaultOverlays;
    public final IdmapManager mIdmapManager;
    public final OverlayConfig mOverlayConfig;
    public OverlayManagerServiceExt mOverlayManagerExt;
    public final OverlayManagerService.PackageManagerHelperImpl mPackageManager;
    public final OverlayManagerSettings mSettings;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    final class OperationFailedException extends Exception {
        public OperationFailedException(String str) {
            super(str);
        }
    }

    public OverlayManagerServiceImpl(OverlayManagerService.PackageManagerHelperImpl packageManagerHelperImpl, IdmapManager idmapManager, OverlayManagerSettings overlayManagerSettings, OverlayConfig overlayConfig, String[] strArr) {
        this.mPackageManager = packageManagerHelperImpl;
        this.mIdmapManager = idmapManager;
        this.mSettings = overlayManagerSettings;
        this.mOverlayConfig = overlayConfig;
        this.mDefaultOverlays = strArr;
    }

    public static void createLocaleOverlayPreferenceDir(int i) {
        File dataFile = getDataFile("/overlays/current_locale_apks/locale_preferences_" + i + "/", "/");
        List list = SemSamsungThemeUtils.disableOverlayList;
        if (dataFile.exists()) {
            return;
        }
        if (dataFile.mkdirs()) {
            FileUtils.setPermissions(dataFile.toString(), 508, -1, -1);
            return;
        }
        Log.e("OverlayManager", "createLocaleOverlayPreferenceDir: Unable to create dir for new user - " + dataFile);
    }

    public static void dumpFile(PrintWriter printWriter, File file, String str, int i) {
        printWriter.println("uid= " + Integer.toString(i) + " " + str + " Dump");
        long length = file.length() - 1048576;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8));
            if (length > 0) {
                try {
                    if (bufferedReader.skip(length) != length) {
                        printWriter.println("Error in skipping file contents.");
                    }
                } finally {
                }
            }
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    bufferedReader.close();
                    return;
                }
                printWriter.println(readLine);
            }
        } catch (IOException unused) {
        }
    }

    public static File getDataFile(String str, String str2) {
        return new File(new File(Environment.getDataDirectory(), str), str2);
    }

    public final void dump(PrintWriter printWriter, final DumpState dumpState) {
        String str;
        OverlayIdentifier overlayIdentifier;
        OverlayInfo nullableOverlayInfo;
        Pair pair = (dumpState.mPackageName == null || (nullableOverlayInfo = this.mSettings.getNullableOverlayInfo((overlayIdentifier = new OverlayIdentifier(dumpState.mPackageName, dumpState.mOverlayName)), 0)) == null) ? null : new Pair(overlayIdentifier, nullableOverlayInfo.baseCodePath);
        final OverlayManagerSettings overlayManagerSettings = this.mSettings;
        synchronized (overlayManagerSettings.mItemsLock) {
            try {
                Stream stream = overlayManagerSettings.mItems.stream();
                if (dumpState.mUserId != -1) {
                    stream = stream.filter(new OverlayManagerSettings$$ExternalSyntheticLambda4(0, dumpState));
                }
                if (dumpState.mPackageName != null) {
                    stream = stream.filter(new OverlayManagerSettings$$ExternalSyntheticLambda4(1, dumpState));
                }
                if (dumpState.mOverlayName != null) {
                    stream = stream.filter(new OverlayManagerSettings$$ExternalSyntheticLambda4(2, dumpState));
                }
                final IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
                if (dumpState.mField != null) {
                    stream.forEach(new Consumer() { // from class: com.android.server.om.OverlayManagerSettings$$ExternalSyntheticLambda7
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            IndentingPrintWriter indentingPrintWriter2;
                            OverlayManagerSettings.SettingsItem settingsItem;
                            OverlayManagerSettings overlayManagerSettings2 = OverlayManagerSettings.this;
                            indentingPrintWriter2 = indentingPrintWriter;
                            DumpState dumpState2 = dumpState;
                            settingsItem = (OverlayManagerSettings.SettingsItem) obj;
                            overlayManagerSettings2.getClass();
                            String str2 = dumpState2.mField;
                            str2.getClass();
                            switch (str2) {
                                case "targetoverlayablename":
                                    indentingPrintWriter2.println(settingsItem.mTargetOverlayableName);
                                    break;
                                case "targetpackagename":
                                    indentingPrintWriter2.println(settingsItem.mTargetPackageName);
                                    break;
                                case "priority":
                                    indentingPrintWriter2.println(settingsItem.mPriority);
                                    break;
                                case "userid":
                                    indentingPrintWriter2.println(settingsItem.mUserId);
                                    break;
                                case "ismutable":
                                    indentingPrintWriter2.println(settingsItem.mIsMutable);
                                    break;
                                case "overlayname":
                                    indentingPrintWriter2.println(settingsItem.mOverlay.getOverlayName());
                                    break;
                                case "category":
                                    indentingPrintWriter2.println(settingsItem.mCategory);
                                    break;
                                case "state":
                                    indentingPrintWriter2.println(OverlayInfo.stateToString(settingsItem.mState));
                                    break;
                                case "isenabled":
                                    indentingPrintWriter2.println(settingsItem.mIsEnabled);
                                    break;
                                case "packagename":
                                    indentingPrintWriter2.println(settingsItem.mOverlay.getPackageName());
                                    break;
                                case "basecodepath":
                                    indentingPrintWriter2.println(settingsItem.mBaseCodePath);
                                    break;
                            }
                        }
                    });
                } else {
                    stream.forEach(new Consumer() { // from class: com.android.server.om.OverlayManagerSettings$$ExternalSyntheticLambda8
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            OverlayManagerSettings overlayManagerSettings2 = OverlayManagerSettings.this;
                            IndentingPrintWriter indentingPrintWriter2 = indentingPrintWriter;
                            OverlayManagerSettings.SettingsItem settingsItem = (OverlayManagerSettings.SettingsItem) obj;
                            overlayManagerSettings2.getClass();
                            StringBuilder sb = new StringBuilder();
                            sb.append(settingsItem.mOverlay);
                            sb.append(":");
                            int i = settingsItem.mUserId;
                            sb.append(i);
                            sb.append(" {");
                            indentingPrintWriter2.println(sb.toString());
                            indentingPrintWriter2.increaseIndent();
                            indentingPrintWriter2.println("mPackageName...........: " + settingsItem.mOverlay.getPackageName());
                            indentingPrintWriter2.println("mOverlayName...........: " + settingsItem.mOverlay.getOverlayName());
                            indentingPrintWriter2.println("mUserId................: " + i);
                            indentingPrintWriter2.println("mTargetPackageName.....: " + settingsItem.mTargetPackageName);
                            indentingPrintWriter2.println("mTargetOverlayableName.: " + settingsItem.mTargetOverlayableName);
                            indentingPrintWriter2.println("mBaseCodePath..........: " + settingsItem.mBaseCodePath);
                            indentingPrintWriter2.println("mState.................: " + OverlayInfo.stateToString(settingsItem.mState));
                            StringBuilder m = AttentionManagerService$AttentionCheck$$ExternalSyntheticOutline0.m(AttentionManagerService$AttentionCheck$$ExternalSyntheticOutline0.m(new StringBuilder("mIsEnabled.............: "), settingsItem.mIsEnabled, indentingPrintWriter2, "mIsMutable.............: "), settingsItem.mIsMutable, indentingPrintWriter2, "mPriority..............: ");
                            m.append(settingsItem.mPriority);
                            indentingPrintWriter2.println(m.toString());
                            indentingPrintWriter2.println("mCategory..............: " + settingsItem.mCategory);
                            indentingPrintWriter2.println("mIsFabricated..........: " + settingsItem.mIsFabricated);
                            indentingPrintWriter2.decreaseIndent();
                            indentingPrintWriter2.println("}");
                        }
                    });
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (dumpState.mField == null) {
            for (Pair pair2 : pair != null ? Set.of(pair) : this.mSettings.getAllIdentifiersAndBaseCodePaths()) {
                printWriter.println("IDMAP OF " + pair2.first);
                IdmapManager idmapManager = this.mIdmapManager;
                String str2 = (String) pair2.second;
                IdmapDaemon idmapDaemon = idmapManager.mIdmapDaemon;
                idmapDaemon.getClass();
                String str3 = "idmap2d service is not ready for dumpIdmap()";
                try {
                    IdmapDaemon.Connection connect = idmapDaemon.connect();
                    try {
                        IIdmap2 iIdmap2 = connect.mIdmap2;
                        if (iIdmap2 == null) {
                            Slog.w("OverlayManager", "idmap2d service is not ready for dumpIdmap()");
                        } else {
                            str3 = TextUtils.nullIfEmpty(iIdmap2.dumpIdmap(str2));
                        }
                        connect.close();
                    } catch (Throwable th2) {
                        try {
                            connect.close();
                        } catch (Throwable th3) {
                            th2.addSuppressed(th3);
                        }
                        throw th2;
                    }
                } catch (Exception e) {
                    Slog.wtf("OverlayManager", "failed to dump idmap", e);
                    str3 = null;
                }
                if (str3 != null) {
                    printWriter.println(str3);
                } else {
                    OverlayInfo nullableOverlayInfo2 = this.mSettings.getNullableOverlayInfo((OverlayIdentifier) pair2.first, 0);
                    if (nullableOverlayInfo2 != null) {
                        IdmapManager idmapManager2 = this.mIdmapManager;
                        idmapManager2.getClass();
                        if (!idmapManager2.mIdmapDaemon.idmapExists(nullableOverlayInfo2.userId, nullableOverlayInfo2.baseCodePath)) {
                            str = "<missing idmap>";
                            printWriter.println(str);
                        }
                    }
                    str = "<internal error>";
                    printWriter.println(str);
                }
            }
        }
        if (pair == null) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(new StringBuilder("Default overlays: "), TextUtils.join(";", this.mDefaultOverlays), printWriter);
        }
        if (dumpState.mPackageName == null) {
            this.mOverlayConfig.dump(printWriter);
        }
        dumpFile(printWriter, getDataFile("log", "lom_log.txt"), "lom_log.txt", 0);
        for (int i : this.mSettings.getUsers()) {
            dumpFile(printWriter, getDataFile("/overlays/current_locale_apks/locale_preferences_" + i + "/", "current_locale_overlays"), "current_locale_overlays", i);
        }
        dumpFile(printWriter, getDataFile("/overlays/current_locale_apks/locale_preferences_0/", "locale_overlay_preferences.xml"), "locale_overlay_preferences.xml", 0);
    }

    public final OverlayPaths getEnabledOverlayPaths(int i, String str, boolean z) {
        OverlayPaths.Builder builder = new OverlayPaths.Builder();
        ArrayList arrayList = new ArrayList();
        OverlayManagerSettings overlayManagerSettings = this.mSettings;
        int size = overlayManagerSettings.mItems.size();
        for (int i2 = 0; i2 < size; i2++) {
            OverlayManagerSettings.SettingsItem settingsItem = (OverlayManagerSettings.SettingsItem) overlayManagerSettings.mItems.get(i2);
            if (settingsItem.mUserId == i && (str == null || settingsItem.mTargetPackageName.equals(str))) {
                OverlayInfo m739$$Nest$mgetOverlayInfo = OverlayManagerSettings.SettingsItem.m739$$Nest$mgetOverlayInfo(settingsItem);
                if (m739$$Nest$mgetOverlayInfo.isEnabled() && (z || m739$$Nest$mgetOverlayInfo.isMutable)) {
                    if (m739$$Nest$mgetOverlayInfo.isFabricated()) {
                        if (m739$$Nest$mgetOverlayInfo.overlayName.startsWith("ThemePark_")) {
                            arrayList.add(m739$$Nest$mgetOverlayInfo);
                        } else {
                            builder.addNonApkPath(m739$$Nest$mgetOverlayInfo.baseCodePath);
                        }
                    } else if (OverlayInfoExt.isOverlayInfoExtOfCategory(m739$$Nest$mgetOverlayInfo, 2)) {
                        arrayList.add(m739$$Nest$mgetOverlayInfo);
                    } else {
                        builder.addApkPath(m739$$Nest$mgetOverlayInfo.baseCodePath);
                    }
                }
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            OverlayInfo overlayInfo = (OverlayInfo) it.next();
            if (overlayInfo.isFabricated) {
                builder.addNonApkPath(overlayInfo.baseCodePath);
            } else {
                builder.addApkPath(overlayInfo.baseCodePath);
            }
        }
        return builder.build();
    }

    public final boolean mustReinitializeOverlay(AndroidPackage androidPackage, OverlayInfo overlayInfo) {
        boolean isMutable;
        if (overlayInfo != null && Objects.equals(androidPackage.getOverlayTarget(), overlayInfo.targetPackageName) && Objects.equals(androidPackage.getOverlayTargetOverlayableName(), overlayInfo.targetOverlayableName) && !overlayInfo.isFabricated && (isMutable = this.mOverlayConfig.isMutable(androidPackage.getPackageName())) == overlayInfo.isMutable) {
            return (isMutable || this.mOverlayConfig.isEnabled(androidPackage.getPackageName()) == overlayInfo.isEnabled()) ? false : true;
        }
        return true;
    }

    public final Set onPackageRemoved(int i, String str, boolean z) {
        StringBuilder m = StorageManagerService$$ExternalSyntheticOutline0.m(i, "onPackageRemoved: pkgName = [", str, "], userId = [", "], packageHidden = [");
        m.append(z);
        m.append("]");
        Slog.d("OverlayManager", m.toString());
        return CollectionUtils.addAll(updateOverlaysForTarget(i, z ? 16 : 0, str), removeOverlaysForUser(i, new OverlayManagerServiceImpl$$ExternalSyntheticLambda0(1, str)));
    }

    public final Set reconcileSettingsForPackage(int i, int i2, String str) {
        Slog.d("OverlayManager", "reconcileSettingsForPackage pkgName=" + str + " userId=" + i);
        Set addAll = CollectionUtils.addAll(Collections.emptySet(), updateOverlaysForTarget(i, i2, str));
        PackageState packageStateForUser = this.mPackageManager.getPackageStateForUser(i, str);
        AndroidPackage androidPackage = packageStateForUser == null ? null : packageStateForUser.getAndroidPackage();
        return androidPackage == null ? onPackageRemoved(i, str, false) : CollectionUtils.addAll(addAll, updatePackageOverlays(androidPackage, i, i2));
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x005d, code lost:
    
        r13.add(android.content.pm.UserPackage.of(r26, r2.targetPackageName));
     */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00cf A[Catch: BadKeyException -> 0x0067, TryCatch #0 {BadKeyException -> 0x0067, blocks: (B:49:0x0046, B:52:0x0051, B:54:0x00c1, B:23:0x00c9, B:25:0x00cf, B:27:0x00d5, B:29:0x00dd, B:31:0x00ed, B:33:0x00f3, B:34:0x00f9, B:36:0x00ff, B:11:0x005d, B:12:0x006a, B:14:0x006e, B:16:0x0076, B:18:0x007c, B:22:0x0088), top: B:48:0x0046 }] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00ff A[Catch: BadKeyException -> 0x0067, TRY_LEAVE, TryCatch #0 {BadKeyException -> 0x0067, blocks: (B:49:0x0046, B:52:0x0051, B:54:0x00c1, B:23:0x00c9, B:25:0x00cf, B:27:0x00d5, B:29:0x00dd, B:31:0x00ed, B:33:0x00f3, B:34:0x00f9, B:36:0x00ff, B:11:0x005d, B:12:0x006a, B:14:0x006e, B:16:0x0076, B:18:0x007c, B:22:0x0088), top: B:48:0x0046 }] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x010b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.Set registerFabricatedOverlay(android.os.FabricatedOverlayInfo r25, int r26) {
        /*
            Method dump skipped, instructions count: 278
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.om.OverlayManagerServiceImpl.registerFabricatedOverlay(android.os.FabricatedOverlayInfo, int):java.util.Set");
    }

    public final Set registerFabricatedOverlay(FabricatedOverlayInternal fabricatedOverlayInternal) {
        if (FrameworkParsingPackageUtils.validateName(fabricatedOverlayInternal.overlayName, false, true) != null) {
            throw new OperationFailedException("overlay name can only consist of alphanumeric characters, '_', and '.'");
        }
        IdmapDaemon idmapDaemon = this.mIdmapManager.mIdmapDaemon;
        idmapDaemon.getClass();
        FabricatedOverlayInfo fabricatedOverlayInfo = null;
        try {
            IdmapDaemon.Connection connect = idmapDaemon.connect();
            try {
                IIdmap2 iIdmap2 = connect.mIdmap2;
                if (iIdmap2 == null) {
                    Slog.w("OverlayManager", "idmap2d service is not ready for createFabricatedOverlay()");
                    connect.close();
                } else {
                    FabricatedOverlayInfo createFabricatedOverlay = iIdmap2.createFabricatedOverlay(fabricatedOverlayInternal);
                    connect.close();
                    fabricatedOverlayInfo = createFabricatedOverlay;
                }
            } finally {
            }
        } catch (Exception e) {
            Slog.wtf("OverlayManager", "failed to fabricate overlay " + fabricatedOverlayInternal, e);
        }
        if (fabricatedOverlayInfo == null) {
            throw new OperationFailedException("failed to create fabricated overlay");
        }
        ArraySet arraySet = new ArraySet();
        for (int i : this.mSettings.getUsers()) {
            arraySet.addAll(registerFabricatedOverlay(fabricatedOverlayInfo, i));
        }
        return arraySet;
    }

    public final void removeIdmapForOverlay(OverlayIdentifier overlayIdentifier, int i) {
        try {
            removeIdmapIfPossible(this.mSettings.getOverlayInfo(overlayIdentifier, i));
        } catch (OverlayManagerSettings.BadKeyException e) {
            throw new OperationFailedException("failed to update settings", e);
        }
    }

    public final void removeIdmapIfPossible(OverlayInfo overlayInfo) {
        IdmapManager idmapManager = this.mIdmapManager;
        idmapManager.getClass();
        if (idmapManager.mIdmapDaemon.idmapExists(overlayInfo.userId, overlayInfo.baseCodePath)) {
            OverlayManagerSettings overlayManagerSettings = this.mSettings;
            for (int i : overlayManagerSettings.getUsers()) {
                try {
                    OverlayInfo overlayInfo2 = overlayManagerSettings.getOverlayInfo(overlayInfo.getOverlayIdentifier(), i);
                    if (overlayInfo2 != null && overlayInfo2.isEnabled()) {
                        return;
                    }
                } catch (OverlayManagerSettings.BadKeyException unused) {
                }
            }
            idmapManager.removeIdmap(overlayInfo, overlayInfo.userId);
        }
    }

    public final Set removeOverlaysForUser(final int i, final Predicate predicate) {
        List removeIf = this.mSettings.removeIf(new Predicate() { // from class: com.android.server.om.OverlayManagerServiceImpl$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                OverlayInfo overlayInfo = (OverlayInfo) obj;
                return i == overlayInfo.userId && predicate.test(overlayInfo);
            }
        });
        Set emptySet = Collections.emptySet();
        int size = removeIf.size();
        for (int i2 = 0; i2 < size; i2++) {
            OverlayInfo overlayInfo = (OverlayInfo) removeIf.get(i2);
            emptySet = CollectionUtils.add(emptySet, UserPackage.of(i, overlayInfo.targetPackageName));
            removeIdmapIfPossible(overlayInfo);
        }
        return emptySet;
    }

    public final Set setEnabled(int i, OverlayIdentifier overlayIdentifier, boolean z) {
        OverlayManagerSettings overlayManagerSettings = this.mSettings;
        Slog.d("OverlayManager", String.format("setEnabled overlay=%s enable=%s userId=%d", overlayIdentifier, Boolean.valueOf(z), Integer.valueOf(i)));
        try {
            OverlayInfo overlayInfo = overlayManagerSettings.getOverlayInfo(overlayIdentifier, i);
            if (overlayInfo.isMutable) {
                return updateState(overlayInfo, i, 0) | overlayManagerSettings.setEnabled(i, overlayIdentifier, z) ? Set.of(UserPackage.of(i, overlayInfo.targetPackageName)) : Set.of();
            }
            throw new OperationFailedException("cannot enable immutable overlay packages in runtime");
        } catch (OverlayManagerSettings.BadKeyException e) {
            throw new OperationFailedException("failed to update settings", e);
        }
    }

    public final Optional setEnabledExclusive(int i, OverlayIdentifier overlayIdentifier, boolean z) {
        OverlayManagerSettings overlayManagerSettings = this.mSettings;
        Slog.d("OverlayManager", String.format("setEnabledExclusive overlay=%s withinCategory=%s userId=%d", overlayIdentifier, Boolean.valueOf(z), Integer.valueOf(i)));
        try {
            OverlayInfo overlayInfo = overlayManagerSettings.getOverlayInfo(overlayIdentifier, i);
            if (!overlayInfo.isMutable) {
                throw new OperationFailedException("cannot enable immutable overlay packages in runtime");
            }
            List overlaysForTarget = overlayManagerSettings.getOverlaysForTarget(i, overlayInfo.targetPackageName);
            overlaysForTarget.remove(overlayInfo);
            boolean z2 = false;
            for (int i2 = 0; i2 < overlaysForTarget.size(); i2++) {
                OverlayInfo overlayInfo2 = (OverlayInfo) overlaysForTarget.get(i2);
                OverlayIdentifier overlayIdentifier2 = overlayInfo2.getOverlayIdentifier();
                if (overlayInfo2.isMutable && (!z || Objects.equals(overlayInfo2.category, overlayInfo.category))) {
                    z2 = z2 | overlayManagerSettings.setEnabled(i, overlayIdentifier2, false) | updateState(overlayInfo2, i, 0);
                }
            }
            return updateState(overlayInfo, i, 0) | (overlayManagerSettings.setEnabled(i, overlayIdentifier, true) | z2) ? Optional.of(UserPackage.of(i, overlayInfo.targetPackageName)) : Optional.empty();
        } catch (OverlayManagerSettings.BadKeyException e) {
            throw new OperationFailedException("failed to update settings", e);
        }
    }

    public final Set setHighestPriority(OverlayIdentifier overlayIdentifier, int i) {
        OverlayManagerSettings overlayManagerSettings = this.mSettings;
        try {
            Slog.d("OverlayManager", "setHighestPriority overlay=" + overlayIdentifier + " userId=" + i);
            OverlayInfo overlayInfo = overlayManagerSettings.getOverlayInfo(overlayIdentifier, i);
            if (overlayInfo.isMutable) {
                return overlayManagerSettings.setHighestPriority(overlayIdentifier, i) ? Set.of(UserPackage.of(i, overlayInfo.targetPackageName)) : Set.of();
            }
            throw new OperationFailedException("cannot change priority of an immutable overlay package at runtime");
        } catch (OverlayManagerSettings.BadKeyException e) {
            throw new OperationFailedException("failed to update settings", e);
        }
    }

    public final Optional setLowestPriority(OverlayIdentifier overlayIdentifier, int i) {
        try {
            Slog.d("OverlayManager", "setLowestPriority packageName=" + overlayIdentifier + " userId=" + i);
            OverlayInfo overlayInfo = this.mSettings.getOverlayInfo(overlayIdentifier, i);
            if (!overlayInfo.isMutable) {
                throw new OperationFailedException("cannot change priority of an immutable overlay package at runtime");
            }
            OverlayManagerSettings overlayManagerSettings = this.mSettings;
            synchronized (overlayManagerSettings.mItemsLock) {
                int select = overlayManagerSettings.select(overlayIdentifier, i);
                if (select <= 0) {
                    return Optional.empty();
                }
                OverlayManagerSettings.SettingsItem settingsItem = (OverlayManagerSettings.SettingsItem) overlayManagerSettings.mItems.get(select);
                overlayManagerSettings.mItems.remove(settingsItem);
                overlayManagerSettings.mItems.add(0, settingsItem);
                return Optional.of(UserPackage.of(i, overlayInfo.targetPackageName));
            }
        } catch (OverlayManagerSettings.BadKeyException e) {
            throw new OperationFailedException("failed to update settings", e);
        }
    }

    public final Optional setPriority(OverlayIdentifier overlayIdentifier, OverlayIdentifier overlayIdentifier2, int i) {
        OverlayManagerSettings overlayManagerSettings = this.mSettings;
        try {
            Slog.d("OverlayManager", "setPriority overlay=" + overlayIdentifier + " newParentOverlay=" + overlayIdentifier2 + " userId=" + i);
            OverlayInfo overlayInfo = overlayManagerSettings.getOverlayInfo(overlayIdentifier, i);
            if (overlayInfo.isMutable) {
                return overlayManagerSettings.setPriority(overlayIdentifier, overlayIdentifier2, i) ? Optional.of(UserPackage.of(i, overlayInfo.targetPackageName)) : Optional.empty();
            }
            throw new OperationFailedException("cannot change priority of an immutable overlay package at runtime");
        } catch (OverlayManagerSettings.BadKeyException e) {
            throw new OperationFailedException("failed to update settings", e);
        }
    }

    public final Set unregisterFabricatedOverlay(OverlayIdentifier overlayIdentifier) {
        Set of;
        ArraySet arraySet = new ArraySet();
        OverlayManagerSettings overlayManagerSettings = this.mSettings;
        for (int i : overlayManagerSettings.getUsers()) {
            OverlayInfo nullableOverlayInfo = overlayManagerSettings.getNullableOverlayInfo(overlayIdentifier, i);
            if (nullableOverlayInfo != null) {
                overlayManagerSettings.remove(overlayIdentifier, i);
                if (nullableOverlayInfo.isEnabled()) {
                    of = Set.of(UserPackage.of(i, nullableOverlayInfo.targetPackageName));
                    arraySet.addAll(of);
                }
            }
            of = Set.of();
            arraySet.addAll(of);
        }
        return arraySet;
    }

    public final Set updateOverlaysForTarget(int i, int i2, String str) {
        boolean remove;
        CustomizedBinderCallsStatsInternal$$ExternalSyntheticOutline0.m(StorageManagerService$$ExternalSyntheticOutline0.m(i, "updateOverlaysForTarget() called with: targetPackage = [", str, "], userId = [", "], flags = ["), i2, "]", "OverlayManager");
        OverlayManagerSettings overlayManagerSettings = this.mSettings;
        List overlaysForTarget = overlayManagerSettings.getOverlaysForTarget(i, str);
        int size = overlaysForTarget.size();
        boolean z = false;
        for (int i3 = 0; i3 < size; i3++) {
            OverlayInfo overlayInfo = (OverlayInfo) overlaysForTarget.get(i3);
            try {
                remove = updateState(overlayInfo, i, i2);
            } catch (OverlayManagerSettings.BadKeyException e) {
                Slog.e("OverlayManager", "failed to update settings", e);
                remove = overlayManagerSettings.remove(overlayInfo.getOverlayIdentifier(), i);
            }
            z |= remove;
        }
        return !z ? Collections.emptySet() : Set.of(UserPackage.of(i, str));
    }

    public final ArraySet updateOverlaysForUser(final int i) {
        ArrayMap arrayMap;
        ArraySet arraySet = new ArraySet();
        final OverlayManagerService.PackageManagerHelperImpl packageManagerHelperImpl = this.mPackageManager;
        synchronized (packageManagerHelperImpl.mCache) {
            try {
                if (packageManagerHelperImpl.mInitializedUsers.add(Integer.valueOf(i))) {
                    packageManagerHelperImpl.mPackageManagerInternal.forEachPackageState(new Consumer() { // from class: com.android.server.om.OverlayManagerService$PackageManagerHelperImpl$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            OverlayManagerService.PackageManagerHelperImpl packageManagerHelperImpl2 = OverlayManagerService.PackageManagerHelperImpl.this;
                            int i2 = i;
                            PackageStateInternal packageStateInternal = (PackageStateInternal) obj;
                            packageManagerHelperImpl2.getClass();
                            if (packageStateInternal.getPkg() == null || !packageStateInternal.getUserStateOrDefault(i2).isInstalled()) {
                                return;
                            }
                            packageManagerHelperImpl2.addPackageUser(packageStateInternal, i2);
                        }
                    });
                }
                arrayMap = new ArrayMap();
                int size = packageManagerHelperImpl.mCache.size();
                for (int i2 = 0; i2 < size; i2++) {
                    OverlayManagerService.PackageManagerHelperImpl.PackageStateUsers packageStateUsers = (OverlayManagerService.PackageManagerHelperImpl.PackageStateUsers) packageManagerHelperImpl.mCache.valueAt(i2);
                    if (((ArraySet) packageStateUsers.mInstalledUsers).contains(Integer.valueOf(i))) {
                        arrayMap.put((String) packageManagerHelperImpl.mCache.keyAt(i2), packageStateUsers.mPackageState);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        CollectionUtils.addAll(arraySet, removeOverlaysForUser(i, new OverlayManagerServiceImpl$$ExternalSyntheticLambda0(0, arrayMap)));
        ArraySet arraySet2 = new ArraySet();
        Iterator it = arrayMap.values().iterator();
        while (it.hasNext()) {
            AndroidPackage androidPackage = ((PackageState) it.next()).getAndroidPackage();
            String overlayTarget = androidPackage == null ? null : androidPackage.getOverlayTarget();
            if (!TextUtils.isEmpty(overlayTarget)) {
                arraySet2.add(overlayTarget);
            }
        }
        int size2 = arrayMap.size();
        for (int i3 = 0; i3 < size2; i3++) {
            PackageState packageState = (PackageState) arrayMap.valueAt(i3);
            AndroidPackage androidPackage2 = packageState.getAndroidPackage();
            if (androidPackage2 != null) {
                String packageName = packageState.getPackageName();
                try {
                    CollectionUtils.addAll(arraySet, updatePackageOverlays(androidPackage2, i, 0));
                    if (arraySet2.contains(packageName)) {
                        arraySet.add(UserPackage.of(i, packageName));
                    }
                } catch (OperationFailedException e) {
                    Slog.e("OverlayManager", AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0.m(i, "failed to initialize overlays of '", packageName, "' for user ", ""), e);
                }
            }
        }
        Set allBaseCodePaths = this.mSettings.getAllBaseCodePaths();
        ArrayList arrayList = new ArrayList(this.mIdmapManager.getFabricatedOverlayInfos());
        arrayList.removeIf(new OverlayManagerServiceImpl$$ExternalSyntheticLambda0(2, allBaseCodePaths));
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            FabricatedOverlayInfo fabricatedOverlayInfo = (FabricatedOverlayInfo) it2.next();
            try {
            } catch (OperationFailedException e2) {
                Slog.e("OverlayManager", ActiveServices$$ExternalSyntheticOutline0.m(i, fabricatedOverlayInfo.path, "' for user ", "", new StringBuilder("failed to initialize fabricated overlay of '")), e2);
            }
            if (MaintenanceModeManager.isInMaintenanceModeFromProperty()) {
                List list = SemSamsungThemeUtils.disableOverlayList;
                String str = fabricatedOverlayInfo.overlayName;
                if (str != null && str.startsWith("SemWT_")) {
                    String str2 = fabricatedOverlayInfo.overlayName;
                    if (str2 != null && SemSamsungThemeUtils.disableOverlayList.contains(str2)) {
                        OverlayInfo nullableOverlayInfo = this.mSettings.getNullableOverlayInfo(new OverlayIdentifier("android", "SemWT_G_MonetPalette"), 0);
                        if (nullableOverlayInfo != null && nullableOverlayInfo.isEnabled()) {
                            try {
                                setEnabled(0, new OverlayIdentifier(fabricatedOverlayInfo.packageName, fabricatedOverlayInfo.overlayName), false);
                            } catch (OperationFailedException e3) {
                                throw new RuntimeException(e3);
                            }
                        }
                    }
                }
            }
            CollectionUtils.addAll(arraySet, registerFabricatedOverlay(fabricatedOverlayInfo, i));
        }
        ArraySet arraySet3 = new ArraySet();
        ArrayMap overlaysForUser = this.mSettings.getOverlaysForUser(i);
        int size3 = overlaysForUser.size();
        for (int i4 = 0; i4 < size3; i4++) {
            List list2 = (List) overlaysForUser.valueAt(i4);
            int size4 = list2 != null ? list2.size() : 0;
            for (int i5 = 0; i5 < size4; i5++) {
                OverlayInfo overlayInfo = (OverlayInfo) list2.get(i5);
                if (overlayInfo.isEnabled()) {
                    arraySet3.add(overlayInfo.category);
                }
            }
        }
        for (String str3 : this.mDefaultOverlays) {
            try {
                OverlayIdentifier overlayIdentifier = new OverlayIdentifier(str3);
                OverlayInfo overlayInfo2 = this.mSettings.getOverlayInfo(overlayIdentifier, i);
                if (!arraySet3.contains(overlayInfo2.category)) {
                    Slog.w("OverlayManager", "Enabling default overlay '" + str3 + "' for target '" + overlayInfo2.targetPackageName + "' in category '" + overlayInfo2.category + "' for user " + i);
                    this.mSettings.setEnabled(i, overlayIdentifier, true);
                    if (updateState(overlayInfo2, i, 0)) {
                        CollectionUtils.add(arraySet, UserPackage.of(overlayInfo2.userId, overlayInfo2.targetPackageName));
                    }
                }
            } catch (OverlayManagerSettings.BadKeyException e4) {
                Slog.e("OverlayManager", SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0.m(i, "Failed to set default overlay '", str3, "' for user "), e4);
            }
        }
        Set allBaseCodePaths2 = this.mSettings.getAllBaseCodePaths();
        IdmapManager idmapManager = this.mIdmapManager;
        for (FabricatedOverlayInfo fabricatedOverlayInfo2 : idmapManager.getFabricatedOverlayInfos()) {
            if (!((ArraySet) allBaseCodePaths2).contains(fabricatedOverlayInfo2.path)) {
                String str4 = fabricatedOverlayInfo2.path;
                IdmapDaemon idmapDaemon = idmapManager.mIdmapDaemon;
                idmapDaemon.getClass();
                try {
                    IdmapDaemon.Connection connect = idmapDaemon.connect();
                    try {
                        IIdmap2 iIdmap2 = connect.mIdmap2;
                        if (iIdmap2 == null) {
                            Slog.w("OverlayManager", "idmap2d service is not ready for deleteFabricatedOverlay(\"" + str4 + "\")");
                        } else {
                            iIdmap2.deleteFabricatedOverlay(str4);
                        }
                        connect.close();
                    } catch (Throwable th2) {
                        try {
                            connect.close();
                        } catch (Throwable th3) {
                            th2.addSuppressed(th3);
                        }
                        throw th2;
                    }
                } catch (Exception e5) {
                    Slog.wtf("OverlayManager", "failed to delete fabricated overlay '" + str4 + "'", e5);
                }
            }
        }
        return arraySet;
    }

    public final Set updatePackageOverlays(AndroidPackage androidPackage, int i, int i2) {
        int i3;
        OverlayManagerSettings overlayManagerSettings = this.mSettings;
        if (androidPackage.getOverlayTarget() == null) {
            return Collections.emptySet();
        }
        Set emptySet = Collections.emptySet();
        OverlayIdentifier overlayIdentifier = new OverlayIdentifier(androidPackage.getPackageName());
        int priority = this.mOverlayConfig.getPriority(androidPackage.getPackageName());
        try {
            OverlayInfo nullableOverlayInfo = overlayManagerSettings.getNullableOverlayInfo(overlayIdentifier, i);
            if (mustReinitializeOverlay(androidPackage, nullableOverlayInfo)) {
                if (nullableOverlayInfo != null) {
                    emptySet = CollectionUtils.add(emptySet, UserPackage.of(i, nullableOverlayInfo.targetPackageName));
                }
                Set set = emptySet;
                String overlayTarget = androidPackage.getOverlayTarget();
                String overlayTargetOverlayableName = androidPackage.getOverlayTargetOverlayableName();
                String path = ((AndroidPackageSplit) androidPackage.getSplits().get(0)).getPath();
                boolean isMutable = this.mOverlayConfig.isMutable(androidPackage.getPackageName());
                boolean isEnabled = this.mOverlayConfig.isEnabled(androidPackage.getPackageName());
                int priority2 = this.mOverlayConfig.getPriority(androidPackage.getPackageName());
                String overlayCategory = androidPackage.getOverlayCategory();
                overlayManagerSettings.remove(overlayIdentifier, i);
                OverlayManagerSettings.SettingsItem settingsItem = new OverlayManagerSettings.SettingsItem(overlayIdentifier, i, overlayTarget, overlayTargetOverlayableName, path, -1, isEnabled, isMutable, priority2, overlayCategory, false);
                overlayManagerSettings.insert(settingsItem);
                nullableOverlayInfo = OverlayManagerSettings.SettingsItem.m739$$Nest$mgetOverlayInfo(settingsItem);
                i3 = i2;
                emptySet = set;
            } else {
                if (priority != nullableOverlayInfo.priority) {
                    overlayManagerSettings.setPriority(overlayIdentifier, i, priority);
                    emptySet = CollectionUtils.add(emptySet, UserPackage.of(i, nullableOverlayInfo.targetPackageName));
                }
                i3 = i2;
            }
            return updateState(nullableOverlayInfo, i, i3) ? CollectionUtils.add(emptySet, UserPackage.of(i, nullableOverlayInfo.targetPackageName)) : emptySet;
        } catch (OverlayManagerSettings.BadKeyException e) {
            throw new OperationFailedException("failed to update settings", e);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:108:0x0345, code lost:
    
        if (r5.mIdmapDaemon.idmapExists(r6.userId, r6.baseCodePath) == false) goto L128;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:114:0x0304  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x00f7  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x014b  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0302  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x035c  */
    /* JADX WARN: Removed duplicated region for block: B:96:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:97:0x031e  */
    /* JADX WARN: Type inference failed for: r0v23 */
    /* JADX WARN: Type inference failed for: r0v24 */
    /* JADX WARN: Type inference failed for: r0v28 */
    /* JADX WARN: Type inference failed for: r0v29 */
    /* JADX WARN: Type inference failed for: r11v1 */
    /* JADX WARN: Type inference failed for: r11v2 */
    /* JADX WARN: Type inference failed for: r11v3 */
    /* JADX WARN: Type inference failed for: r11v4 */
    /* JADX WARN: Type inference failed for: r11v5 */
    /* JADX WARN: Type inference failed for: r11v6, types: [int] */
    /* JADX WARN: Type inference failed for: r11v7 */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.server.om.OverlayManagerSettings] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean updateState(android.content.om.CriticalOverlayInfo r25, int r26, int r27) {
        /*
            Method dump skipped, instructions count: 905
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.om.OverlayManagerServiceImpl.updateState(android.content.om.CriticalOverlayInfo, int, int):boolean");
    }
}
