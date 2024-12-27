package com.android.server.pm;

import android.content.pm.PackageInfo;
import android.content.pm.ShortcutInfo;
import android.content.pm.UserPackage;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Slog;
import android.util.Xml;

import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.HeimdAllFsService$$ExternalSyntheticOutline0;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class ShortcutLauncher extends ShortcutPackageItem {
    public final int mOwnerUserId;
    public final ArrayMap mPinnedShortcuts;

    public ShortcutLauncher(ShortcutUser shortcutUser, int i, String str, int i2) {
        super(shortcutUser, i2, str, new ShortcutPackageInfo(-1L, 0L, new ArrayList(0)));
        this.mPinnedShortcuts = new ArrayMap();
        this.mOwnerUserId = i;
    }

    public static ShortcutLauncher loadFromFile(
            File file, ShortcutUser shortcutUser, int i, boolean z) {
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
                    ShortcutLauncher shortcutLauncher = null;
                    while (true) {
                        int next = resolvePullParser.next();
                        if (next == 1) {
                            resilientFile.close();
                            return shortcutLauncher;
                        }
                        if (next == 2) {
                            depth = resolvePullParser.getDepth();
                            name = resolvePullParser.getName();
                            if (depth != 1 || !"launcher-pins".equals(name)) {
                                break;
                            }
                            shortcutLauncher = loadFromXml(resolvePullParser, shortcutUser, i, z);
                        }
                    }
                    ShortcutService.throwForInvalidTag(depth, name);
                    throw null;
                } catch (Exception e) {
                    e = e;
                    fileInputStream = openRead;
                    Slog.e("ShortcutService", "Failed to read file " + resilientFile.mFile, e);
                    resilientFile.failRead(fileInputStream, e);
                    ShortcutLauncher loadFromFile = loadFromFile(file, shortcutUser, i, z);
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

    public static ShortcutLauncher loadFromXml(
            TypedXmlPullParser typedXmlPullParser, ShortcutUser shortcutUser, int i, boolean z) {
        AtomicBoolean atomicBoolean = ShortcutService.sIsEmergencyMode;
        ShortcutLauncher shortcutLauncher =
                new ShortcutLauncher(
                        shortcutUser,
                        i,
                        typedXmlPullParser.getAttributeValue((String) null, "package-name"),
                        z
                                ? i
                                : (int)
                                        ShortcutService.parseLongAttribute(
                                                typedXmlPullParser, "launcher-user", i));
        int depth = typedXmlPullParser.getDepth();
        ArraySet arraySet = null;
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1 || (next == 3 && typedXmlPullParser.getDepth() <= depth)) {
                break;
            }
            if (next == 2) {
                int depth2 = typedXmlPullParser.getDepth();
                String name = typedXmlPullParser.getName();
                if (depth2 == depth + 1) {
                    name.getClass();
                    if (name.equals("package-info")) {
                        shortcutLauncher.mPackageInfo.loadFromXml(typedXmlPullParser, z);
                    } else if (name.equals("package")) {
                        String attributeValue =
                                typedXmlPullParser.getAttributeValue((String) null, "package-name");
                        int parseLongAttribute =
                                z
                                        ? i
                                        : (int)
                                                ShortcutService.parseLongAttribute(
                                                        typedXmlPullParser, "package-user", i);
                        ArraySet arraySet2 = new ArraySet();
                        synchronized (shortcutLauncher.mPackageItemLock) {
                            shortcutLauncher.mPinnedShortcuts.put(
                                    UserPackage.of(parseLongAttribute, attributeValue), arraySet2);
                        }
                        arraySet = arraySet2;
                    }
                }
                if (depth2 == depth + 2) {
                    name.getClass();
                    if (name.equals("pin")) {
                        if (arraySet == null) {
                            Slog.w("ShortcutService", "pin in invalid place");
                        } else {
                            arraySet.add(
                                    typedXmlPullParser.getAttributeValue((String) null, "value"));
                        }
                    }
                }
                ShortcutService.warnForInvalidTag(depth2, name);
            }
        }
        return shortcutLauncher;
    }

    public final void addPinnedShortcut(int i, String str, String str2) {
        ArrayList arrayList;
        synchronized (this.mPackageItemLock) {
            try {
                ArraySet arraySet = (ArraySet) this.mPinnedShortcuts.get(UserPackage.of(i, str));
                if (arraySet != null) {
                    arrayList = new ArrayList(arraySet.size() + 1);
                    arrayList.addAll(arraySet);
                } else {
                    arrayList = new ArrayList(1);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        arrayList.add(str2);
        pinShortcuts(i, str, true, arrayList);
    }

    public final void ensurePackageInfo() {
        ShortcutService shortcutService = this.mShortcutUser.mService;
        int i = this.mPackageUserId;
        String str = this.mPackageName;
        PackageInfo packageInfo = shortcutService.getPackageInfo(str, i, true);
        if (packageInfo == null) {
            HeimdAllFsService$$ExternalSyntheticOutline0.m(
                    "Package not found: ", str, "ShortcutService");
            return;
        }
        ShortcutPackageInfo shortcutPackageInfo = this.mPackageInfo;
        shortcutPackageInfo.getClass();
        shortcutPackageInfo.mVersionCode = packageInfo.getLongVersionCode();
        shortcutPackageInfo.mLastUpdateTime = packageInfo.lastUpdateTime;
        AtomicBoolean atomicBoolean = ShortcutService.sIsEmergencyMode;
        shortcutPackageInfo.mBackupAllowed = (packageInfo.applicationInfo.flags & 32768) != 0;
        shortcutPackageInfo.mBackupAllowedInitialized = true;
    }

    @Override // com.android.server.pm.ShortcutPackageItem
    public final int getOwnerUserId() {
        return this.mOwnerUserId;
    }

    public final ArraySet getPinnedShortcutIds(int i, String str) {
        ArraySet arraySet;
        synchronized (this.mPackageItemLock) {
            ArraySet arraySet2 = (ArraySet) this.mPinnedShortcuts.get(UserPackage.of(i, str));
            arraySet = arraySet2 == null ? null : new ArraySet(arraySet2);
        }
        return arraySet;
    }

    @Override // com.android.server.pm.ShortcutPackageItem
    public final File getShortcutPackageItemFile() {
        ShortcutUser shortcutUser = this.mShortcutUser;
        File file =
                new File(
                        shortcutUser.mService.injectUserDataPath(shortcutUser.mUserId),
                        "launchers");
        StringBuilder sb = new StringBuilder();
        sb.append(this.mPackageName);
        return new File(
                file, AmFmBandRange$$ExternalSyntheticOutline0.m(this.mPackageUserId, sb, ".xml"));
    }

    public final boolean hasPinned(ShortcutInfo shortcutInfo) {
        boolean z;
        synchronized (this.mPackageItemLock) {
            try {
                ArraySet arraySet =
                        (ArraySet)
                                this.mPinnedShortcuts.get(
                                        UserPackage.of(
                                                shortcutInfo.getUserId(),
                                                shortcutInfo.getPackage()));
                z = arraySet != null && arraySet.contains(shortcutInfo.getId());
            } finally {
            }
        }
        return z;
    }

    @Override // com.android.server.pm.ShortcutPackageItem
    public final void onRestored(int i) {
        ArrayList arrayList;
        if (i != 0) {
            synchronized (this.mPackageItemLock) {
                arrayList = new ArrayList(this.mPinnedShortcuts.keySet());
                this.mPinnedShortcuts.clear();
            }
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                ShortcutPackage packageShortcutsIfExists =
                        this.mShortcutUser.getPackageShortcutsIfExists(
                                ((UserPackage) arrayList.get(size)).packageName);
                if (packageShortcutsIfExists != null) {
                    packageShortcutsIfExists.refreshPinnedFlags();
                }
            }
        }
    }

    public final void pinShortcuts(int i, String str, boolean z, List list) {
        ShortcutPackage packageShortcutsIfExists =
                this.mShortcutUser.getPackageShortcutsIfExists(str);
        if (packageShortcutsIfExists == null) {
            return;
        }
        UserPackage of = UserPackage.of(i, str);
        int size = list.size();
        if (size == 0) {
            synchronized (this.mPackageItemLock) {
                this.mPinnedShortcuts.remove(of);
            }
        } else {
            ArraySet arraySet = new ArraySet();
            ArraySet arraySet2 = new ArraySet();
            for (int i2 = 0; i2 < size; i2++) {
                String str2 = (String) list.get(i2);
                ShortcutInfo findShortcutById = packageShortcutsIfExists.findShortcutById(str2);
                if (findShortcutById != null) {
                    if (findShortcutById.isDynamic()
                            || findShortcutById.isLongLived()
                            || findShortcutById.isManifestShortcut()
                            || z) {
                        arraySet2.add(str2);
                    } else {
                        arraySet.add(str2);
                    }
                }
            }
            synchronized (this.mPackageItemLock) {
                try {
                    ArraySet arraySet3 = (ArraySet) this.mPinnedShortcuts.get(of);
                    if (arraySet3 != null) {
                        Iterator it = arraySet.iterator();
                        while (it.hasNext()) {
                            String str3 = (String) it.next();
                            if (arraySet3.contains(str3)) {
                                arraySet2.add(str3);
                            }
                        }
                    }
                    this.mPinnedShortcuts.put(of, arraySet2);
                } finally {
                }
            }
        }
        packageShortcutsIfExists.refreshPinnedFlags();
    }

    @Override // com.android.server.pm.ShortcutPackageItem
    public final void saveToXml(TypedXmlSerializer typedXmlSerializer, boolean z) {
        ArrayMap arrayMap;
        if (this.mShortcutUser.mService.mSmartSwitchBackupAllowed.get()
                || !z
                || this.mPackageInfo.mBackupAllowed) {
            synchronized (this.mPackageItemLock) {
                arrayMap = new ArrayMap(this.mPinnedShortcuts);
            }
            int size = arrayMap.size();
            if (size == 0) {
                return;
            }
            typedXmlSerializer.startTag((String) null, "launcher-pins");
            ShortcutService.writeAttr(typedXmlSerializer, "package-name", this.mPackageName);
            ShortcutService.writeAttr(typedXmlSerializer, "launcher-user", this.mPackageUserId);
            this.mPackageInfo.saveToXml(this.mShortcutUser.mService, typedXmlSerializer, z);
            for (int i = 0; i < size; i++) {
                UserPackage userPackage = (UserPackage) arrayMap.keyAt(i);
                if (!z || userPackage.userId == this.mOwnerUserId) {
                    typedXmlSerializer.startTag((String) null, "package");
                    ShortcutService.writeAttr(
                            typedXmlSerializer, "package-name", userPackage.packageName);
                    ShortcutService.writeAttr(
                            typedXmlSerializer, "package-user", userPackage.userId);
                    ArraySet arraySet = (ArraySet) arrayMap.valueAt(i);
                    int size2 = arraySet.size();
                    for (int i2 = 0; i2 < size2; i2++) {
                        String str = (String) arraySet.valueAt(i2);
                        if (!TextUtils.isEmpty(str)) {
                            typedXmlSerializer.startTag((String) null, "pin");
                            typedXmlSerializer.attribute((String) null, "value", str);
                            typedXmlSerializer.endTag((String) null, "pin");
                        }
                    }
                    typedXmlSerializer.endTag((String) null, "package");
                }
            }
            typedXmlSerializer.endTag((String) null, "launcher-pins");
        }
    }
}
