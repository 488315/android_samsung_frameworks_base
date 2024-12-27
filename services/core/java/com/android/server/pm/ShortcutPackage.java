package com.android.server.pm;

import android.app.Person;
import android.app.appsearch.AppSearchBatchResult;
import android.app.appsearch.AppSearchManager;
import android.app.appsearch.BatchResultCallback;
import android.app.appsearch.SearchSpec;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManagerInternal;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.PersistableBundle;
import android.os.StrictMode;
import android.os.SystemClock;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.Slog;
import android.util.Xml;

import com.android.internal.infra.AndroidFuture;
import com.android.internal.os.BackgroundThread;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.Preconditions;
import com.android.internal.util.XmlUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.usage.UsageStatsService;

import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knoxguard.service.SystemIntentProcessor;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class ShortcutPackage extends ShortcutPackageItem {
    public int mApiCallCount;
    public final Executor mExecutor;
    public boolean mIsAppSearchSchemaUpToDate;
    public long mLastKnownForegroundElapsedTime;
    public long mLastReportedTime;
    public long mLastResetTime;
    public final int mPackageUid;
    public final ArrayList mShareTargets;
    public final ShortcutPackage$$ExternalSyntheticLambda7 mShortcutRankComparator;
    public final ShortcutPackage$$ExternalSyntheticLambda7 mShortcutTypeAndRankComparator;
    public final ShortcutPackage$$ExternalSyntheticLambda7 mShortcutTypeRankAndTimeComparator;
    public final ArrayMap mShortcuts;
    public final ArrayMap mTransientShortcuts;

    /* renamed from: com.android.server.pm.ShortcutPackage$1, reason: invalid class name */
    public final class AnonymousClass1 implements BatchResultCallback {
        public final /* synthetic */ Consumer val$cb;

        public AnonymousClass1(Consumer consumer) {
            this.val$cb = consumer;
        }

        @Override // android.app.appsearch.BatchResultCallback
        public final void onResult(AppSearchBatchResult appSearchBatchResult) {
            this.val$cb.accept(
                    (List)
                            appSearchBatchResult.getSuccesses().values().stream()
                                    .map(new ShortcutPackage$$ExternalSyntheticLambda38(2, this))
                                    .collect(Collectors.toList()));
        }

        @Override // android.app.appsearch.BatchResultCallback
        public final void onSystemError(Throwable th) {
            Slog.d("ShortcutService", "Error retrieving shortcuts", th);
        }
    }

    public ShortcutPackage(ShortcutUser shortcutUser, int i, String str) {
        super(shortcutUser, i, str, new ShortcutPackageInfo(-1L, 0L, new ArrayList(0)));
        this.mShortcuts = new ArrayMap();
        this.mTransientShortcuts = new ArrayMap(0);
        this.mShareTargets = new ArrayList(0);
        this.mShortcutTypeAndRankComparator = new ShortcutPackage$$ExternalSyntheticLambda7(0);
        this.mShortcutTypeRankAndTimeComparator = new ShortcutPackage$$ExternalSyntheticLambda7(2);
        this.mShortcutRankComparator = new ShortcutPackage$$ExternalSyntheticLambda7(3);
        this.mPackageUid = shortcutUser.mService.injectGetPackageUid(i, str);
        this.mExecutor = BackgroundThread.getExecutor();
    }

    public static void ensureAllShortcutsVisibleToLauncher(List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ShortcutInfo shortcutInfo = (ShortcutInfo) it.next();
            if (shortcutInfo.isExcludedFromSurfaces(1)) {
                throw new IllegalArgumentException(
                        "Shortcut ID="
                                + shortcutInfo.getId()
                                + " is hidden from launcher and may not be manipulated via APIs");
            }
        }
    }

    public static void ensureNoBitmapIconIfShortcutIsLongLived(List list) {
        Icon icon;
        for (int size = list.size() - 1; size >= 0; size--) {
            ShortcutInfo shortcutInfo = (ShortcutInfo) list.get(size);
            if (shortcutInfo.isLongLived()
                    && (((icon = shortcutInfo.getIcon()) == null
                                    || icon.getType() == 1
                                    || icon.getType() == 5)
                            && (icon != null || shortcutInfo.hasIconFile()))) {
                Slog.e(
                        "ShortcutService",
                        "Invalid icon type in shortcut "
                                + shortcutInfo.getId()
                                + ". Bitmaps are not allowed in long-lived shortcuts. Use Resource"
                                + " icons, or Uri-based icons instead.");
                return;
            }
        }
    }

    public static void ensureNotImmutable(ShortcutInfo shortcutInfo) {
        if (shortcutInfo != null
                && shortcutInfo.isImmutable()
                && shortcutInfo.isVisibleToPublisher()) {
            throw new IllegalArgumentException(
                    "Manifest shortcut ID="
                            + shortcutInfo.getId()
                            + " may not be manipulated via APIs");
        }
    }

    public static void incrementCountForActivity(
            ArrayMap arrayMap, ComponentName componentName, int i) {
        Integer num = (Integer) arrayMap.get(componentName);
        if (num == null) {
            num = 0;
        }
        arrayMap.put(componentName, Integer.valueOf(num.intValue() + i));
    }

    public static ShortcutPackage loadFromFile(ShortcutUser shortcutUser, File file, boolean z) {
        int depth;
        String name;
        ResilientAtomicFile resilientFile = ShortcutPackageItem.getResilientFile(file);
        FileInputStream fileInputStream = null;
        try {
            try {
                FileInputStream openRead = resilientFile.openRead();
                try {
                    if (openRead == null) {
                        Slog.d("ShortcutService", "Not found " + file);
                        resilientFile.close();
                        return null;
                    }
                    TypedXmlPullParser resolvePullParser = Xml.resolvePullParser(openRead);
                    ShortcutPackage shortcutPackage = null;
                    while (true) {
                        int next = resolvePullParser.next();
                        if (next == 1) {
                            resilientFile.close();
                            return shortcutPackage;
                        }
                        if (next == 2) {
                            depth = resolvePullParser.getDepth();
                            name = resolvePullParser.getName();
                            Slog.d(
                                    "ShortcutService",
                                    String.format(
                                            "depth=%d type=%d name=%s",
                                            Integer.valueOf(depth), Integer.valueOf(next), name));
                            if (depth != 1 || !"package".equals(name)) {
                                break;
                            }
                            shortcutPackage = loadFromXml(shortcutUser, resolvePullParser, z);
                        }
                    }
                    ShortcutService.throwForInvalidTag(depth, name);
                    throw null;
                } catch (Exception e) {
                    e = e;
                    fileInputStream = openRead;
                    Slog.e("ShortcutService", "Failed to read file " + resilientFile.mFile, e);
                    resilientFile.failRead(fileInputStream, e);
                    ShortcutPackage loadFromFile = loadFromFile(shortcutUser, file, z);
                    resilientFile.close();
                    return loadFromFile;
                }
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Throwable th) {
            try {
                resilientFile.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.android.server.pm.ShortcutPackage loadFromXml(
            com.android.server.pm.ShortcutUser r12,
            com.android.modules.utils.TypedXmlPullParser r13,
            boolean r14) {
        /*
            Method dump skipped, instructions count: 213
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.ShortcutPackage.loadFromXml(com.android.server.pm.ShortcutUser,"
                    + " com.android.modules.utils.TypedXmlPullParser,"
                    + " boolean):com.android.server.pm.ShortcutPackage");
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x00fe, code lost:

       r49 = r5;
       r47 = r7;
       r2 = r20;
       r5 = r26;
    */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x033d, code lost:

       if (r31 == null) goto L119;
    */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x033f, code lost:

       android.content.pm.ShortcutInfo.setIntentExtras(r31, r14);
       r2.clear();
       r2.add(r31);
    */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x034a, code lost:

       if (r11 != 0) goto L123;
    */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x034e, code lost:

       if ((r9 & 64) == 0) goto L123;
    */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0350, code lost:

       r39 = 1;
    */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0355, code lost:

       if (r57 == false) goto L126;
    */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0357, code lost:

       r0 = r9 | 4096;
    */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x035f, code lost:

       if (r13 != null) goto L129;
    */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0361, code lost:

       r12 = null;
    */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x03a4, code lost:

       return new android.content.pm.ShortcutInfo(r56, r15, r55, r17, null, r19, r49, r21, r22, r9, r24, r25, r47, r27, r41, (android.content.Intent[]) r2.toArray(new android.content.Intent[r2.size()]), r9, r43, r32, r0, r45, r36, r37, r38, r39, (android.app.Person[]) r5.toArray(new android.app.Person[r5.size()]), r12, r42, r44);
    */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0363, code lost:

       r12 = new android.content.LocusId(r13);
    */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x035c, code lost:

       r0 = r9;
    */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0353, code lost:

       r39 = r11;
    */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x0314, code lost:

       r2.add(r0);
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.content.pm.ShortcutInfo parseShortcut(
            com.android.modules.utils.TypedXmlPullParser r54,
            java.lang.String r55,
            int r56,
            boolean r57) {
        /*
            Method dump skipped, instructions count: 982
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.ShortcutPackage.parseShortcut(com.android.modules.utils.TypedXmlPullParser,"
                    + " java.lang.String, int, boolean):android.content.pm.ShortcutInfo");
    }

    public static void runAsSystem(Runnable runnable) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            runnable.run();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void addOrReplaceDynamicShortcut(ShortcutInfo shortcutInfo) {
        Preconditions.checkArgument(
                shortcutInfo.isEnabled(),
                "add/setDynamicShortcuts() cannot publish disabled shortcuts");
        shortcutInfo.addFlags(1);
        ShortcutInfo findShortcutById = findShortcutById(shortcutInfo.getId());
        if (findShortcutById != null) {
            findShortcutById.ensureUpdatableWith(shortcutInfo, false);
            shortcutInfo.addFlags(findShortcutById.getFlags() & 1610629122);
        }
        if (!shortcutInfo.isExcludedFromSurfaces(1)) {
            forceReplaceShortcutInner(shortcutInfo);
        } else if (isAppSearchEnabled()) {
            synchronized (this.mPackageItemLock) {
                this.mTransientShortcuts.put(shortcutInfo.getId(), shortcutInfo);
            }
        }
    }

    public final void adjustRanks() {
        ShortcutService shortcutService = this.mShortcutUser.mService;
        final long injectCurrentTimeMillis = shortcutService.injectCurrentTimeMillis();
        forEachShortcutMutate(
                new Consumer() { // from class:
                                 // com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda17
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        long j = injectCurrentTimeMillis;
                        ShortcutInfo shortcutInfo = (ShortcutInfo) obj;
                        if (!shortcutInfo.isFloating() || shortcutInfo.getRank() == 0) {
                            return;
                        }
                        shortcutInfo.setTimestamp(j);
                        shortcutInfo.setRank(0);
                    }
                });
        ArrayMap sortShortcutsToActivities = sortShortcutsToActivities();
        for (int size = sortShortcutsToActivities.size() - 1; size >= 0; size--) {
            ArrayList arrayList = (ArrayList) sortShortcutsToActivities.valueAt(size);
            Collections.sort(arrayList, this.mShortcutRankComparator);
            int size2 = arrayList.size();
            final int i = 0;
            for (int i2 = 0; i2 < size2; i2++) {
                ShortcutInfo shortcutInfo = (ShortcutInfo) arrayList.get(i2);
                if (!shortcutInfo.isManifestShortcut()) {
                    if (shortcutInfo.isDynamic()) {
                        int i3 = i + 1;
                        if (shortcutInfo.getRank() != i) {
                            mutateShortcut(
                                    shortcutInfo.getId(),
                                    shortcutInfo,
                                    new Consumer() { // from class:
                                                     // com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda18
                                        @Override // java.util.function.Consumer
                                        public final void accept(Object obj) {
                                            long j = injectCurrentTimeMillis;
                                            int i4 = i;
                                            ShortcutInfo shortcutInfo2 = (ShortcutInfo) obj;
                                            shortcutInfo2.setTimestamp(j);
                                            shortcutInfo2.setRank(i4);
                                        }
                                    });
                        }
                        i = i3;
                    } else {
                        shortcutService.wtf(
                                "Non-dynamic shortcut found. " + shortcutInfo.toInsecureString(),
                                null);
                    }
                }
            }
        }
    }

    public final List deleteAllDynamicShortcuts() {
        boolean z;
        long injectCurrentTimeMillis = this.mShortcutUser.mService.injectCurrentTimeMillis();
        synchronized (this.mPackageItemLock) {
            try {
                z = false;
                for (int size = this.mShortcuts.size() - 1; size >= 0; size--) {
                    ShortcutInfo shortcutInfo = (ShortcutInfo) this.mShortcuts.valueAt(size);
                    if (shortcutInfo.isDynamic() && shortcutInfo.isVisibleToPublisher()) {
                        shortcutInfo.setTimestamp(injectCurrentTimeMillis);
                        shortcutInfo.clearFlags(1);
                        shortcutInfo.setRank(0);
                        z = true;
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (isAppSearchEnabled()) {
            runAsSystem(new ShortcutPackage$$ExternalSyntheticLambda26(this));
        }
        if (z) {
            return removeOrphans();
        }
        return null;
    }

    public final ShortcutInfo deleteOrDisableWithId(
            String str, final boolean z, boolean z2, boolean z3, final int i, boolean z4) {
        Preconditions.checkState(
                z == (i != 0), "disable and disabledReason disagree: " + z + " vs " + i);
        ShortcutInfo findShortcutById = findShortcutById(str);
        if (findShortcutById != null
                && (findShortcutById.isEnabled()
                        || !z3
                        || findShortcutById.isVisibleToPublisher())) {
            if (!z2) {
                ensureNotImmutable(findShortcutById);
            }
            if (!z4) {
                List asList = Arrays.asList(str);
                if (isAppSearchEnabled()) {
                    runAsSystem(new ShortcutPackage$$ExternalSyntheticLambda37(this, asList, 0));
                }
            }
            if (!findShortcutById.isPinned() && !findShortcutById.isCached()) {
                forceDeleteShortcutInner(str);
                return findShortcutById;
            }
            mutateShortcut(
                    findShortcutById.getId(),
                    findShortcutById,
                    new Consumer() { // from class:
                                     // com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda20
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            ShortcutPackage shortcutPackage = ShortcutPackage.this;
                            boolean z5 = z;
                            int i2 = i;
                            ShortcutInfo shortcutInfo = (ShortcutInfo) obj;
                            shortcutPackage.getClass();
                            shortcutInfo.setRank(0);
                            shortcutInfo.clearFlags(33);
                            if (z5) {
                                shortcutInfo.addFlags(64);
                                if (shortcutInfo.getDisabledReason() == 0) {
                                    shortcutInfo.setDisabledReason(i2);
                                }
                            }
                            shortcutInfo.setTimestamp(
                                    shortcutPackage.mShortcutUser.mService
                                            .injectCurrentTimeMillis());
                            ShortcutService shortcutService =
                                    shortcutPackage.mShortcutUser.mService;
                            ComponentName activity = shortcutInfo.getActivity();
                            shortcutService.getClass();
                            if (activity == null
                                    || !"android.__dummy__".equals(activity.getClassName())) {
                                return;
                            }
                            shortcutInfo.setActivity(null);
                        }
                    });
        }
        return null;
    }

    public final void enforceShortcutCountsBeforeOperation(int i, List list) {
        ShortcutService shortcutService = this.mShortcutUser.mService;
        ArrayMap arrayMap = new ArrayMap(4);
        forEachShortcut(new ShortcutPackage$$ExternalSyntheticLambda5(this, arrayMap, i, 1));
        for (int size = list.size() - 1; size >= 0; size--) {
            ShortcutInfo shortcutInfo = (ShortcutInfo) list.get(size);
            ComponentName activity = shortcutInfo.getActivity();
            if (activity != null) {
                ShortcutInfo findShortcutById = findShortcutById(shortcutInfo.getId());
                if (findShortcutById == null) {
                    if (i != 2) {
                        incrementCountForActivity(arrayMap, activity, 1);
                    }
                } else if (!findShortcutById.isFloating() || i != 2) {
                    if (i != 0) {
                        ComponentName activity2 = findShortcutById.getActivity();
                        if (!findShortcutById.isFloating()) {
                            incrementCountForActivity(arrayMap, activity2, -1);
                        }
                    }
                    incrementCountForActivity(arrayMap, activity, 1);
                }
            } else if (i != 2) {
                shortcutService.wtf("Activity must not be null at this point", null);
            }
        }
        for (int size2 = arrayMap.size() - 1; size2 >= 0; size2--) {
            if (((Integer) arrayMap.valueAt(size2)).intValue() > shortcutService.mMaxShortcuts) {
                throw new IllegalArgumentException("Max number of dynamic shortcuts exceeded");
            }
        }
    }

    public final void ensureImmutableShortcutsNotIncluded(List list) {
        for (int size = list.size() - 1; size >= 0; size--) {
            ensureNotImmutable(findShortcutById(((ShortcutInfo) list.get(size)).getId()));
        }
    }

    public final void ensureImmutableShortcutsNotIncludedWithIds(List list) {
        for (int size = list.size() - 1; size >= 0; size--) {
            ensureNotImmutable(findShortcutById((String) list.get(size)));
        }
    }

    public final void findAll(
            final List list,
            final Predicate predicate,
            final int i,
            final String str,
            int i2,
            final boolean z) {
        ArraySet pinnedShortcutIds;
        boolean z2 = this.mPackageInfo.mIsShadow;
        String str2 = this.mPackageName;
        if (z2) {
            DualAppManagerService$$ExternalSyntheticOutline0.m(
                    "findAll() returned empty results because ",
                    str2,
                    " isn't installed yet",
                    "ShortcutService");
            return;
        }
        ShortcutService shortcutService = this.mShortcutUser.mService;
        if (str == null) {
            pinnedShortcutIds = null;
        } else {
            int i3 = this.mPackageUserId;
            pinnedShortcutIds =
                    shortcutService
                            .getLauncherShortcutsLocked(i3, i2, str)
                            .getPinnedShortcutIds(i3, str2);
        }
        final ArraySet arraySet = pinnedShortcutIds;
        forEachShortcut(
                new Consumer() { // from class:
                                 // com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda31
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ShortcutPackage shortcutPackage = ShortcutPackage.this;
                        List list2 = list;
                        Predicate predicate2 = predicate;
                        int i4 = i;
                        String str3 = str;
                        ArraySet arraySet2 = arraySet;
                        boolean z3 = z;
                        ShortcutInfo shortcutInfo = (ShortcutInfo) obj;
                        shortcutPackage.getClass();
                        boolean z4 =
                                str3 == null
                                        || (arraySet2 != null
                                                && arraySet2.contains(shortcutInfo.getId()));
                        if (!z3 && shortcutInfo.isFloating() && !shortcutInfo.isCached() && !z4) {
                            Log.d(
                                    "ShortcutService",
                                    shortcutInfo.getId()
                                            + " ignored because it isn't pinned by "
                                            + str3);
                            return;
                        }
                        ShortcutInfo clone = shortcutInfo.clone(i4);
                        if (!z3 && !z4) {
                            clone.clearFlags(2);
                        }
                        if (predicate2 == null || predicate2.test(clone)) {
                            if (!z4) {
                                clone.clearFlags(2);
                            }
                            list2.add(clone);
                        }
                    }
                });
    }

    public final ShortcutInfo findShortcutById(String str) {
        ShortcutInfo shortcutInfo;
        if (str == null) {
            return null;
        }
        synchronized (this.mPackageItemLock) {
            shortcutInfo = (ShortcutInfo) this.mShortcuts.get(str);
        }
        return shortcutInfo;
    }

    public final void forEachShortcut(Consumer consumer) {
        forEachShortcutStopWhen(new ShortcutPackage$$ExternalSyntheticLambda38(0, consumer));
    }

    public final void forEachShortcutMutate(Consumer consumer) {
        for (int size = this.mShortcuts.size() - 1; size >= 0; size--) {
            consumer.accept((ShortcutInfo) this.mShortcuts.valueAt(size));
        }
    }

    public final void forEachShortcutStopWhen(Function function) {
        synchronized (this.mPackageItemLock) {
            try {
                for (int size = this.mShortcuts.size() - 1; size >= 0; size--) {
                    if (((Boolean) function.apply((ShortcutInfo) this.mShortcuts.valueAt(size)))
                            .booleanValue()) {
                        return;
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void forceDeleteShortcutInner(String str) {
        synchronized (this.mPackageItemLock) {
            try {
                ShortcutInfo shortcutInfo = (ShortcutInfo) this.mShortcuts.remove(str);
                if (shortcutInfo != null) {
                    removeIcon(shortcutInfo);
                    shortcutInfo.clearFlags(1610629155);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void forceReplaceShortcutInner(ShortcutInfo shortcutInfo) {
        ShortcutService shortcutService = this.mShortcutUser.mService;
        forceDeleteShortcutInner(shortcutInfo.getId());
        shortcutService.saveIconAndFixUpShortcutLocked(this, shortcutInfo);
        shortcutService.fixUpShortcutResourceNamesAndValues(shortcutInfo);
        int i = this.mShortcutUser.mService.mMaxShortcutsPerApp;
        synchronized (this.mPackageItemLock) {
            try {
                List list =
                        (List)
                                this.mShortcuts.values().stream()
                                        .filter(new ShortcutPackage$$ExternalSyntheticLambda4(1))
                                        .collect(Collectors.toList());
                if (list.size() >= i) {
                    Collections.sort(list, this.mShortcutTypeRankAndTimeComparator);
                    while (list.size() >= i) {
                        ShortcutInfo shortcutInfo2 = (ShortcutInfo) list.remove(list.size() - 1);
                        if (shortcutInfo2.isDeclaredInManifest()) {
                            throw new IllegalArgumentException(
                                    this.mPackageName
                                            + " has published "
                                            + list.size()
                                            + " manifest shortcuts across different activities.");
                        }
                        forceDeleteShortcutInner(shortcutInfo2.getId());
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        saveShortcut(shortcutInfo);
    }

    public final AndroidFuture fromAppSearch() {
        StrictMode.ThreadPolicy threadPolicy = StrictMode.getThreadPolicy();
        AppSearchManager.SearchContext build =
                new AppSearchManager.SearchContext.Builder(this.mPackageName).build();
        AndroidFuture androidFuture = null;
        try {
            try {
                StrictMode.setThreadPolicy(
                        new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
                androidFuture = this.mShortcutUser.getAppSearch(build);
                synchronized (this.mPackageItemLock) {
                    try {
                        if (!this.mIsAppSearchSchemaUpToDate) {
                            androidFuture =
                                    androidFuture.thenCompose(
                                            new ShortcutPackage$$ExternalSyntheticLambda43(
                                                    this, 0));
                        }
                        this.mIsAppSearchSchemaUpToDate = true;
                    } finally {
                    }
                }
            } catch (Exception e) {
                Slog.e(
                        "ShortcutService",
                        "Failed to create app search session. pkg="
                                + this.mPackageName
                                + " user="
                                + this.mShortcutUser.mUserId,
                        e);
                Objects.requireNonNull(androidFuture);
                AndroidFuture androidFuture2 = androidFuture;
                androidFuture.completeExceptionally(e);
            }
            Objects.requireNonNull(androidFuture);
            return androidFuture;
        } finally {
            StrictMode.setThreadPolicy(threadPolicy);
        }
    }

    public List getAllShareTargetsForTest() {
        ArrayList arrayList;
        synchronized (this.mPackageItemLock) {
            arrayList = new ArrayList(this.mShareTargets);
        }
        return arrayList;
    }

    public List getAllShortcutsForTest() {
        ArrayList arrayList = new ArrayList(1);
        forEachShortcut(new ShortcutPackage$$ExternalSyntheticLambda13(0, arrayList));
        return arrayList;
    }

    public final int getApiCallCount(boolean z) {
        ShortcutService shortcutService = this.mShortcutUser.mService;
        int i = this.mPackageUid;
        if (shortcutService.isUidForegroundLocked(i)
                || this.mLastKnownForegroundElapsedTime
                        < shortcutService.mUidLastForegroundElapsedTime.get(i)
                || z) {
            this.mLastKnownForegroundElapsedTime = shortcutService.injectElapsedRealtime();
            if (this.mApiCallCount > 0) {
                this.mApiCallCount = 0;
                scheduleSave();
            }
        }
        shortcutService.updateTimesLocked();
        long j = shortcutService.mRawLastResetTime.get();
        long injectCurrentTimeMillis = shortcutService.injectCurrentTimeMillis();
        if ((injectCurrentTimeMillis >= 1420070400)
                && this.mLastResetTime > injectCurrentTimeMillis) {
            Slog.w("ShortcutService", "Clock rewound");
            this.mLastResetTime = injectCurrentTimeMillis;
            this.mApiCallCount = 0;
            return 0;
        }
        long j2 = this.mLastResetTime;
        if (j2 < j) {
            Slog.d(
                    "ShortcutService",
                    String.format(
                            "%s: last reset=%d, now=%d, last=%d: resetting",
                            this.mPackageName,
                            Long.valueOf(j2),
                            Long.valueOf(injectCurrentTimeMillis),
                            Long.valueOf(j)));
            this.mApiCallCount = 0;
            this.mLastResetTime = j;
        }
        return this.mApiCallCount;
    }

    public final List getMatchingShareTargets(IntentFilter intentFilter, String str) {
        synchronized (this.mPackageItemLock) {
            try {
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < this.mShareTargets.size(); i++) {
                    ShareTargetInfo shareTargetInfo = (ShareTargetInfo) this.mShareTargets.get(i);
                    ShareTargetInfo.TargetData[] targetDataArr = shareTargetInfo.mTargetData;
                    int length = targetDataArr.length;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= length) {
                            break;
                        }
                        if (intentFilter.hasDataType(targetDataArr[i2].mMimeType)) {
                            arrayList.add(shareTargetInfo);
                            break;
                        }
                        i2++;
                    }
                }
                if (arrayList.isEmpty()) {
                    return new ArrayList();
                }
                ArrayList arrayList2 = new ArrayList();
                findAll(
                        arrayList2,
                        new ShortcutPackage$$ExternalSyntheticLambda4(0),
                        9,
                        str,
                        0,
                        false);
                ArrayList arrayList3 = new ArrayList();
                for (int i3 = 0; i3 < arrayList2.size(); i3++) {
                    Set<String> categories = ((ShortcutInfo) arrayList2.get(i3)).getCategories();
                    if (categories != null && !categories.isEmpty()) {
                        int i4 = 0;
                        while (true) {
                            if (i4 < arrayList.size()) {
                                ShareTargetInfo shareTargetInfo2 =
                                        (ShareTargetInfo) arrayList.get(i4);
                                int i5 = 0;
                                while (true) {
                                    String[] strArr = shareTargetInfo2.mCategories;
                                    if (i5 >= strArr.length) {
                                        arrayList3.add(
                                                new ShortcutManager.ShareShortcutInfo(
                                                        (ShortcutInfo) arrayList2.get(i3),
                                                        new ComponentName(
                                                                this.mPackageName,
                                                                shareTargetInfo2.mTargetClass)));
                                        break;
                                    }
                                    if (!categories.contains(strArr[i5])) {
                                        break;
                                    }
                                    i5++;
                                }
                            }
                            i4++;
                        }
                    }
                }
                return arrayList3;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.pm.ShortcutPackageItem
    public final int getOwnerUserId() {
        return this.mPackageUserId;
    }

    public final SearchSpec getSearchSpec() {
        return new SearchSpec.Builder()
                .addFilterSchemas("Shortcut")
                .addFilterNamespaces(this.mPackageName)
                .setTermMatch(1)
                .setResultCountPerPage(this.mShortcutUser.mService.mMaxShortcuts)
                .build();
    }

    @Override // com.android.server.pm.ShortcutPackageItem
    public final File getShortcutPackageItemFile() {
        ShortcutUser shortcutUser = this.mShortcutUser;
        return new File(
                new File(
                        shortcutUser.mService.injectUserDataPath(shortcutUser.mUserId), "packages"),
                AudioOffloadInfo$$ExternalSyntheticOutline0.m(
                        new StringBuilder(), this.mPackageName, ".xml"));
    }

    public void getTopShortcutsFromPersistence(AndroidFuture androidFuture) {
        if (!isAppSearchEnabled()) {
            androidFuture.complete((Object) null);
        }
        runAsSystem(new ShortcutPackage$$ExternalSyntheticLambda0(this, androidFuture));
    }

    public final boolean isAppSearchEnabled() {
        return this.mShortcutUser.mService.mIsAppSearchEnabled;
    }

    public final boolean isShortcutExistsAndVisibleToPublisher(String str) {
        ShortcutInfo findShortcutById = findShortcutById(str);
        return findShortcutById != null && findShortcutById.isVisibleToPublisher();
    }

    public final void mutateShortcut(String str, ShortcutInfo shortcutInfo, Consumer consumer) {
        Objects.requireNonNull(str);
        synchronized (this.mPackageItemLock) {
            if (shortcutInfo != null) {
                try {
                    consumer.accept(shortcutInfo);
                } catch (Throwable th) {
                    throw th;
                }
            }
            ShortcutInfo findShortcutById = findShortcutById(str);
            if (findShortcutById == null) {
                return;
            }
            consumer.accept(findShortcutById);
            saveShortcut(findShortcutById);
        }
    }

    @Override // com.android.server.pm.ShortcutPackageItem
    public final void onRestored(final int i) {
        forEachShortcutMutate(
                new Consumer() { // from class:
                                 // com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda11
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        int i2 = i;
                        ShortcutInfo shortcutInfo = (ShortcutInfo) obj;
                        if (i2 == 0
                                && !shortcutInfo.hasFlags(4096)
                                && shortcutInfo.getDisabledReason() == i2) {
                            return;
                        }
                        shortcutInfo.clearFlags(4096);
                        shortcutInfo.setDisabledReason(i2);
                        if (i2 != 0) {
                            shortcutInfo.addFlags(64);
                        }
                    }
                });
        refreshPinnedFlags();
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean pushDynamicShortcut(
            android.content.pm.ShortcutInfo r11, java.util.List r12) {
        /*
            r10 = this;
            boolean r0 = r11.isEnabled()
            java.lang.String r1 = "pushDynamicShortcuts() cannot publish disabled shortcuts"
            com.android.internal.util.Preconditions.checkArgument(r0, r1)
            r0 = 1
            r11.addFlags(r0)
            java.util.ArrayList r12 = (java.util.ArrayList) r12
            r12.clear()
            java.lang.String r1 = r11.getId()
            android.content.pm.ShortcutInfo r1 = r10.findShortcutById(r1)
            r9 = 0
            if (r1 == 0) goto L24
            boolean r2 = r1.isDynamic()
            if (r2 != 0) goto La2
        L24:
            com.android.server.pm.ShortcutUser r2 = r10.mShortcutUser
            com.android.server.pm.ShortcutService r2 = r2.mService
            int r3 = r2.mMaxShortcuts
            android.util.ArrayMap r4 = r10.sortShortcutsToActivities()
            android.content.ComponentName r5 = r11.getActivity()
            java.lang.Object r4 = r4.get(r5)
            java.util.ArrayList r4 = (java.util.ArrayList) r4
            if (r4 == 0) goto L5b
            int r5 = r4.size()
            if (r5 <= r3) goto L5b
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "Error pushing shortcut. There are already "
            r5.<init>(r6)
            int r6 = r4.size()
            r5.append(r6)
            java.lang.String r6 = " shortcuts."
            r5.append(r6)
            java.lang.String r5 = r5.toString()
            r6 = 0
            r2.wtf(r5, r6)
        L5b:
            if (r4 == 0) goto La2
            int r2 = r4.size()
            if (r2 != r3) goto La2
            com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda7 r2 = r10.mShortcutTypeAndRankComparator
            java.util.Collections.sort(r4, r2)
            int r3 = r3 - r0
            java.lang.Object r2 = r4.get(r3)
            android.content.pm.ShortcutInfo r2 = (android.content.pm.ShortcutInfo) r2
            boolean r3 = r2.isManifestShortcut()
            if (r3 == 0) goto L8d
            java.lang.String r10 = "ShortcutService"
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            java.lang.String r1 = "Failed to remove manifest shortcut while pushing dynamic shortcut "
            r12.<init>(r1)
            java.lang.String r11 = r11.getId()
            r12.append(r11)
            java.lang.String r11 = r12.toString()
            android.util.Slog.e(r10, r11)
            return r0
        L8d:
            r12.add(r2)
            java.lang.String r3 = r2.getId()
            r4 = 0
            r5 = 0
            r7 = 0
            r2 = r10
            r6 = r0
            r8 = r0
            android.content.pm.ShortcutInfo r12 = r2.deleteOrDisableWithId(r3, r4, r5, r6, r7, r8)
            if (r12 == 0) goto La2
            r12 = r0
            goto La3
        La2:
            r12 = r9
        La3:
            if (r1 == 0) goto Lb3
            r1.ensureUpdatableWith(r11, r9)
            int r1 = r1.getFlags()
            r2 = 1610629122(0x60004002, float:3.6965555E19)
            r1 = r1 & r2
            r11.addFlags(r1)
        Lb3:
            boolean r0 = r11.isExcludedFromSurfaces(r0)
            if (r0 == 0) goto Ld0
            boolean r0 = r10.isAppSearchEnabled()
            if (r0 == 0) goto Ld3
            java.lang.Object r0 = r10.mPackageItemLock
            monitor-enter(r0)
            android.util.ArrayMap r1 = r10.mTransientShortcuts     // Catch: java.lang.Throwable -> Lcd
            java.lang.String r2 = r11.getId()     // Catch: java.lang.Throwable -> Lcd
            r1.put(r2, r11)     // Catch: java.lang.Throwable -> Lcd
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Lcd
            goto Ld3
        Lcd:
            r10 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Lcd
            throw r10
        Ld0:
            r10.forceReplaceShortcutInner(r11)
        Ld3:
            boolean r0 = r10.isAppSearchEnabled()
            if (r0 == 0) goto Le1
            com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda0 r0 = new com.android.server.pm.ShortcutPackage$$ExternalSyntheticLambda0
            r0.<init>(r10, r11)
            runAsSystem(r0)
        Le1:
            return r12
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.ShortcutPackage.pushDynamicShortcut(android.content.pm.ShortcutInfo,"
                    + " java.util.List):boolean");
    }

    public final void refreshPinnedFlags() {
        List list;
        ArraySet arraySet = new ArraySet();
        this.mShortcutUser.forAllLaunchers(
                new ShortcutPackage$$ExternalSyntheticLambda23(2, this, arraySet));
        synchronized (this.mPackageItemLock) {
            Stream stream = arraySet.stream();
            ArrayMap arrayMap = this.mShortcuts;
            Objects.requireNonNull(arrayMap);
            list =
                    (List)
                            stream.map(new ShortcutPackage$$ExternalSyntheticLambda38(1, arrayMap))
                                    .filter(new ShortcutPackage$$ExternalSyntheticLambda4(3))
                                    .collect(Collectors.toList());
        }
        if (list != null) {
            list.forEach(new ShortcutPackage$$ExternalSyntheticLambda14(2));
        }
        forEachShortcutMutate(new ShortcutPackage$$ExternalSyntheticLambda10(2, arraySet));
        this.mShortcutUser.forAllLaunchers(new ShortcutPackage$$ExternalSyntheticLambda14(3));
        removeOrphans();
    }

    public final List removeOrphans() {
        ArrayList arrayList = new ArrayList(1);
        forEachShortcut(new ShortcutPackage$$ExternalSyntheticLambda13(1, arrayList));
        if (!arrayList.isEmpty()) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                forceDeleteShortcutInner(((ShortcutInfo) arrayList.get(size)).getId());
            }
        }
        return arrayList;
    }

    public final void reportShortcutUsed(
            UsageStatsManagerInternal usageStatsManagerInternal, String str) {
        synchronized (this.mPackageItemLock) {
            try {
                long elapsedRealtime = SystemClock.elapsedRealtime();
                if (elapsedRealtime - this.mLastReportedTime
                        > this.mShortcutUser.mService.mSaveDelayMillis) {
                    this.mLastReportedTime = elapsedRealtime;
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        String str2 = this.mPackageName;
                        int i = this.mPackageUserId;
                        UsageStatsService.LocalService localService =
                                (UsageStatsService.LocalService) usageStatsManagerInternal;
                        localService.getClass();
                        if (str2 != null && str != null) {
                            UsageEvents.Event event =
                                    new UsageEvents.Event(8, SystemClock.elapsedRealtime());
                            event.mPackage = str2.intern();
                            event.mShortcutId = str.intern();
                            UsageStatsService.this.reportEventOrAddToQueue(i, event);
                        }
                        Slog.w(
                                "UsageStatsService",
                                "Event reported without a package name or a shortcut ID");
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean rescanPackageIfNeeded(boolean r18, boolean r19) {
        /*
            Method dump skipped, instructions count: 640
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.pm.ShortcutPackage.rescanPackageIfNeeded(boolean,"
                    + " boolean):boolean");
    }

    public final void saveShortcut(
            TypedXmlSerializer typedXmlSerializer,
            ShortcutInfo shortcutInfo,
            boolean z,
            boolean z2) {
        ShortcutService shortcutService = this.mShortcutUser.mService;
        if (!z || (shortcutInfo.isPinned() && shortcutInfo.isEnabled())) {
            boolean z3 = !z || this.mShortcutUser.mService.mSmartSwitchBackupAllowed.get() || z2;
            if (shortcutInfo.isIconPendingSave()) {
                removeIcon(shortcutInfo);
            }
            typedXmlSerializer.startTag((String) null, "shortcut");
            ShortcutService.writeAttr(typedXmlSerializer, "id", shortcutInfo.getId());
            ComponentName activity = shortcutInfo.getActivity();
            if (activity != null) {
                ShortcutService.writeAttr(
                        typedXmlSerializer, "activity", activity.flattenToString());
            }
            ShortcutService.writeAttr(
                    typedXmlSerializer,
                    KnoxCustomManagerService.SHORTCUT_TITLE,
                    shortcutInfo.getTitle());
            ShortcutService.writeAttr(typedXmlSerializer, "titleid", shortcutInfo.getTitleResId());
            ShortcutService.writeAttr(
                    typedXmlSerializer, "titlename", shortcutInfo.getTitleResName());
            ShortcutService.writeAttr(
                    typedXmlSerializer,
                    "splash-screen-theme-name",
                    shortcutInfo.getStartingThemeResName());
            ShortcutService.writeAttr(typedXmlSerializer, "text", shortcutInfo.getText());
            ShortcutService.writeAttr(typedXmlSerializer, "textid", shortcutInfo.getTextResId());
            ShortcutService.writeAttr(
                    typedXmlSerializer, "textname", shortcutInfo.getTextResName());
            if (z3) {
                ShortcutService.writeAttr(
                        typedXmlSerializer, "dmessage", shortcutInfo.getDisabledMessage());
                ShortcutService.writeAttr(
                        typedXmlSerializer,
                        "dmessageid",
                        shortcutInfo.getDisabledMessageResourceId());
                ShortcutService.writeAttr(
                        typedXmlSerializer,
                        "dmessagename",
                        shortcutInfo.getDisabledMessageResName());
            }
            ShortcutService.writeAttr(
                    typedXmlSerializer, "disabled-reason", shortcutInfo.getDisabledReason());
            ShortcutService.writeAttr(
                    typedXmlSerializer, "timestamp", shortcutInfo.getLastChangedTimestamp());
            if (shortcutInfo.getLocusId() != null) {
                ShortcutService.writeAttr(
                        typedXmlSerializer, "locus-id", shortcutInfo.getLocusId().getId());
            }
            if (!z || this.mShortcutUser.mService.mSmartSwitchBackupAllowed.get()) {
                ShortcutService.writeAttr(typedXmlSerializer, "rank", shortcutInfo.getRank());
                ShortcutService.writeAttr(typedXmlSerializer, "flags", shortcutInfo.getFlags());
                ShortcutService.writeAttr(
                        typedXmlSerializer, "icon-res", shortcutInfo.getIconResourceId());
                ShortcutService.writeAttr(
                        typedXmlSerializer, "icon-resname", shortcutInfo.getIconResName());
                ShortcutService.writeAttr(
                        typedXmlSerializer, "bitmap-path", shortcutInfo.getBitmapPath());
                ShortcutService.writeAttr(
                        typedXmlSerializer, "icon-uri", shortcutInfo.getIconUri());
            } else {
                ShortcutService.writeAttr(
                        typedXmlSerializer, "flags", shortcutInfo.getFlags() & (-35342));
                if (this.mPackageInfo.mVersionCode == 0) {
                    shortcutService.wtf(
                            "Package version code should be available at this point.", null);
                }
            }
            if (z3) {
                Set<String> categories = shortcutInfo.getCategories();
                if (categories != null && categories.size() > 0) {
                    typedXmlSerializer.startTag((String) null, "categories");
                    XmlUtils.writeStringArrayXml(
                            (String[]) categories.toArray(new String[categories.size()]),
                            "categories",
                            XmlUtils.makeTyped(typedXmlSerializer));
                    typedXmlSerializer.endTag((String) null, "categories");
                }
                if (!z) {
                    Person[] persons = shortcutInfo.getPersons();
                    if (!ArrayUtils.isEmpty(persons)) {
                        for (Person person : persons) {
                            typedXmlSerializer.startTag((String) null, "person");
                            ShortcutService.writeAttr(typedXmlSerializer, "name", person.getName());
                            ShortcutService.writeAttr(
                                    typedXmlSerializer,
                                    SystemIntentProcessor.KEY_URI,
                                    person.getUri());
                            ShortcutService.writeAttr(typedXmlSerializer, "key", person.getKey());
                            ShortcutService.writeAttr(typedXmlSerializer, "is-bot", person.isBot());
                            ShortcutService.writeAttr(
                                    typedXmlSerializer, "is-important", person.isImportant());
                            typedXmlSerializer.endTag((String) null, "person");
                        }
                    }
                }
                Intent[] intentsNoExtras = shortcutInfo.getIntentsNoExtras();
                PersistableBundle[] intentPersistableExtrases =
                        shortcutInfo.getIntentPersistableExtrases();
                if (intentsNoExtras != null && intentPersistableExtrases != null) {
                    int length = intentsNoExtras.length;
                    for (int i = 0; i < length; i++) {
                        typedXmlSerializer.startTag((String) null, KnoxCustomManagerService.INTENT);
                        Intent intent = intentsNoExtras[i];
                        if (intent != null) {
                            ShortcutService.writeAttr(
                                    typedXmlSerializer, "intent-base", intent.toUri(0));
                        }
                        PersistableBundle persistableBundle = intentPersistableExtrases[i];
                        if (persistableBundle != null) {
                            typedXmlSerializer.startTag((String) null, "extras");
                            persistableBundle.saveToXml(typedXmlSerializer);
                            typedXmlSerializer.endTag((String) null, "extras");
                        }
                        typedXmlSerializer.endTag((String) null, KnoxCustomManagerService.INTENT);
                    }
                }
                PersistableBundle extras = shortcutInfo.getExtras();
                if (extras != null) {
                    typedXmlSerializer.startTag((String) null, "extras");
                    extras.saveToXml(typedXmlSerializer);
                    typedXmlSerializer.endTag((String) null, "extras");
                }
                Map capabilityBindingsInternal = shortcutInfo.getCapabilityBindingsInternal();
                if (capabilityBindingsInternal != null && !capabilityBindingsInternal.isEmpty()) {
                    XmlUtils.writeMapXml(
                            capabilityBindingsInternal, "capability", typedXmlSerializer);
                }
            }
            typedXmlSerializer.endTag((String) null, "shortcut");
        }
    }

    public final void saveShortcut(ShortcutInfo... shortcutInfoArr) {
        List<ShortcutInfo> asList = Arrays.asList(shortcutInfoArr);
        Objects.requireNonNull(asList);
        synchronized (this.mPackageItemLock) {
            try {
                for (ShortcutInfo shortcutInfo : asList) {
                    this.mShortcuts.put(shortcutInfo.getId(), shortcutInfo);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.pm.ShortcutPackageItem
    public final void saveToXml(TypedXmlSerializer typedXmlSerializer, boolean z) {
        int size;
        synchronized (this.mPackageItemLock) {
            try {
                int size2 = this.mShortcuts.size();
                int size3 = this.mShareTargets.size();
                boolean z2 = true;
                if (isAppSearchEnabled()) {
                    boolean[] zArr = new boolean[1];
                    forEachShortcutStopWhen(new ShortcutPackage$$ExternalSyntheticLambda3(0, zArr));
                    z2 = true ^ zArr[0];
                } else {
                    synchronized (this.mPackageItemLock) {
                        size = this.mShortcuts.size();
                    }
                    if (size != 0) {
                        z2 = false;
                    }
                }
                if (z2 && size3 == 0 && this.mApiCallCount == 0) {
                    return;
                }
                typedXmlSerializer.startTag((String) null, "package");
                ShortcutService.writeAttr(typedXmlSerializer, "name", this.mPackageName);
                ShortcutService.writeAttr(typedXmlSerializer, "call-count", this.mApiCallCount);
                ShortcutService.writeAttr(typedXmlSerializer, "last-reset", this.mLastResetTime);
                if (!z) {
                    ShortcutService.writeAttr(
                            typedXmlSerializer,
                            "schema-version",
                            this.mIsAppSearchSchemaUpToDate ? 3L : 0L);
                }
                this.mPackageInfo.saveToXml(this.mShortcutUser.mService, typedXmlSerializer, z);
                for (int i = 0; i < size2; i++) {
                    saveShortcut(
                            typedXmlSerializer,
                            (ShortcutInfo) this.mShortcuts.valueAt(i),
                            z,
                            this.mPackageInfo.mBackupAllowed);
                }
                if (!z) {
                    for (int i2 = 0; i2 < size3; i2++) {
                        ((ShareTargetInfo) this.mShareTargets.get(i2))
                                .saveToXml(typedXmlSerializer);
                    }
                }
                typedXmlSerializer.endTag((String) null, "package");
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.pm.ShortcutPackageItem
    public final void scheduleSaveToAppSearchLocked() {
        ArrayMap arrayMap = new ArrayMap(this.mShortcuts);
        if (!this.mTransientShortcuts.isEmpty()) {
            arrayMap.putAll((Map) this.mTransientShortcuts);
            this.mTransientShortcuts.clear();
        }
        Collection collection =
                (Collection)
                        arrayMap.values().stream()
                                .filter(new ShortcutPackage$$ExternalSyntheticLambda4(2))
                                .collect(Collectors.toList());
        Objects.requireNonNull(collection);
        if (!isAppSearchEnabled() || collection.isEmpty()) {
            return;
        }
        Slog.d(
                "ShortcutService",
                "Saving shortcuts async for user="
                        + this.mShortcutUser.mUserId
                        + " pkg="
                        + this.mPackageName
                        + " ids="
                        + ((String)
                                collection.stream()
                                        .map(new ShortcutPackage$$ExternalSyntheticLambda51(2))
                                        .collect(Collectors.joining(",", "[", "]"))));
        runAsSystem(new ShortcutPackage$$ExternalSyntheticLambda37(this, collection, 1));
    }

    public final ArrayMap sortShortcutsToActivities() {
        ArrayMap arrayMap = new ArrayMap();
        forEachShortcut(new ShortcutPackage$$ExternalSyntheticLambda23(0, this, arrayMap));
        return arrayMap;
    }

    public final boolean tryApiCall(boolean z) {
        if (getApiCallCount(z) >= this.mShortcutUser.mService.mMaxUpdatesPerInterval) {
            return false;
        }
        this.mApiCallCount++;
        scheduleSave();
        return true;
    }

    public final void verifyRanksSequential(List list) {
        int i = 0;
        while (true) {
            ArrayList arrayList = (ArrayList) list;
            if (i >= arrayList.size()) {
                return;
            }
            ShortcutInfo shortcutInfo = (ShortcutInfo) arrayList.get(i);
            if (shortcutInfo.getRank() != i) {
                Log.e(
                        "ShortcutService.verify",
                        "Package "
                                + this.mPackageName
                                + ": shortcut "
                                + shortcutInfo.getId()
                                + " rank="
                                + shortcutInfo.getRank()
                                + " but expected to be "
                                + i);
            }
            i++;
        }
    }

    @Override // com.android.server.pm.ShortcutPackageItem
    public final void verifyStates() {
        boolean[] zArr = new boolean[1];
        ShortcutService shortcutService = this.mShortcutUser.mService;
        ArrayMap sortShortcutsToActivities = sortShortcutsToActivities();
        for (int size = sortShortcutsToActivities.size() - 1; size >= 0; size--) {
            ArrayList arrayList = (ArrayList) sortShortcutsToActivities.valueAt(size);
            if (arrayList.size() > this.mShortcutUser.mService.mMaxShortcuts) {
                zArr[0] = true;
                Log.e(
                        "ShortcutService.verify",
                        "Package "
                                + this.mPackageName
                                + ": activity "
                                + sortShortcutsToActivities.keyAt(size)
                                + " has "
                                + ((ArrayList) sortShortcutsToActivities.valueAt(size)).size()
                                + " shortcuts.");
            }
            Collections.sort(arrayList, new ShortcutPackage$$ExternalSyntheticLambda7(1));
            ArrayList arrayList2 = new ArrayList(arrayList);
            arrayList2.removeIf(new ShortcutPackage$$ExternalSyntheticLambda4(4));
            ArrayList arrayList3 = new ArrayList(arrayList);
            arrayList3.removeIf(new ShortcutPackage$$ExternalSyntheticLambda4(5));
            verifyRanksSequential(arrayList2);
            verifyRanksSequential(arrayList3);
        }
        forEachShortcut(new ShortcutPackage$$ExternalSyntheticLambda2(this, zArr, shortcutService));
        if (zArr[0]) {
            throw new IllegalStateException("See logcat for errors");
        }
    }
}
