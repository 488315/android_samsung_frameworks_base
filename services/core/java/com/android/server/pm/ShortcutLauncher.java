package com.android.server.pm;

import android.content.pm.PackageInfo;
import android.content.pm.ShortcutInfo;
import android.content.pm.UserPackage;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Slog;
import android.util.Xml;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.pm.ShortcutService;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class ShortcutLauncher extends ShortcutPackageItem {
    public final int mOwnerUserId;
    public final ArrayMap mPinnedShortcuts;

    @Override // com.android.server.pm.ShortcutPackageItem
    public boolean canRestoreAnyVersion() {
        return true;
    }

    public ShortcutLauncher(ShortcutUser shortcutUser, int i, String str, int i2, ShortcutPackageInfo shortcutPackageInfo) {
        super(shortcutUser, i2, str, shortcutPackageInfo == null ? ShortcutPackageInfo.newEmpty() : shortcutPackageInfo);
        this.mPinnedShortcuts = new ArrayMap();
        this.mOwnerUserId = i;
    }

    public ShortcutLauncher(ShortcutUser shortcutUser, int i, String str, int i2) {
        this(shortcutUser, i, str, i2, null);
    }

    @Override // com.android.server.pm.ShortcutPackageItem
    public int getOwnerUserId() {
        return this.mOwnerUserId;
    }

    public final void onRestoreBlocked() {
        ArrayList arrayList = new ArrayList(this.mPinnedShortcuts.keySet());
        this.mPinnedShortcuts.clear();
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            ShortcutPackage packageShortcutsIfExists = this.mShortcutUser.getPackageShortcutsIfExists(((UserPackage) arrayList.get(size)).packageName);
            if (packageShortcutsIfExists != null) {
                packageShortcutsIfExists.refreshPinnedFlags();
            }
        }
    }

    @Override // com.android.server.pm.ShortcutPackageItem
    public void onRestored(int i) {
        if (i != 0) {
            onRestoreBlocked();
        }
    }

    public void pinShortcuts(int i, String str, List list, boolean z) {
        ShortcutPackage packageShortcutsIfExists = this.mShortcutUser.getPackageShortcutsIfExists(str);
        if (packageShortcutsIfExists == null) {
            return;
        }
        UserPackage of = UserPackage.of(i, str);
        int size = list.size();
        if (size == 0) {
            this.mPinnedShortcuts.remove(of);
        } else {
            ArraySet arraySet = (ArraySet) this.mPinnedShortcuts.get(of);
            ArraySet arraySet2 = new ArraySet();
            for (int i2 = 0; i2 < size; i2++) {
                String str2 = (String) list.get(i2);
                ShortcutInfo findShortcutById = packageShortcutsIfExists.findShortcutById(str2);
                if (findShortcutById != null && (findShortcutById.isDynamic() || findShortcutById.isLongLived() || findShortcutById.isManifestShortcut() || ((arraySet != null && arraySet.contains(str2)) || z))) {
                    arraySet2.add(str2);
                }
            }
            this.mPinnedShortcuts.put(of, arraySet2);
        }
        packageShortcutsIfExists.refreshPinnedFlags();
    }

    public ArraySet getPinnedShortcutIds(String str, int i) {
        return (ArraySet) this.mPinnedShortcuts.get(UserPackage.of(i, str));
    }

    public boolean hasPinned(ShortcutInfo shortcutInfo) {
        ArraySet pinnedShortcutIds = getPinnedShortcutIds(shortcutInfo.getPackage(), shortcutInfo.getUserId());
        return pinnedShortcutIds != null && pinnedShortcutIds.contains(shortcutInfo.getId());
    }

    public void addPinnedShortcut(String str, int i, String str2, boolean z) {
        ArrayList arrayList;
        ArraySet pinnedShortcutIds = getPinnedShortcutIds(str, i);
        if (pinnedShortcutIds != null) {
            arrayList = new ArrayList(pinnedShortcutIds.size() + 1);
            arrayList.addAll(pinnedShortcutIds);
        } else {
            arrayList = new ArrayList(1);
        }
        arrayList.add(str2);
        pinShortcuts(i, str, arrayList, z);
    }

    public boolean cleanUpPackage(String str, int i) {
        return this.mPinnedShortcuts.remove(UserPackage.of(i, str)) != null;
    }

    public void ensurePackageInfo() {
        PackageInfo packageInfoWithSignatures = this.mShortcutUser.mService.getPackageInfoWithSignatures(getPackageName(), getPackageUserId());
        if (packageInfoWithSignatures == null) {
            Slog.w("ShortcutService", "Package not found: " + getPackageName());
            return;
        }
        getPackageInfo().updateFromPackageInfo(packageInfoWithSignatures);
    }

    @Override // com.android.server.pm.ShortcutPackageItem
    public void saveToXml(TypedXmlSerializer typedXmlSerializer, boolean z) {
        int size;
        if ((this.mShortcutUser.mService.isSmartSwitchBackupAllowed() || !z || getPackageInfo().isBackupAllowed()) && (size = this.mPinnedShortcuts.size()) != 0) {
            typedXmlSerializer.startTag((String) null, "launcher-pins");
            ShortcutService.writeAttr(typedXmlSerializer, "package-name", getPackageName());
            ShortcutService.writeAttr(typedXmlSerializer, "launcher-user", getPackageUserId());
            getPackageInfo().saveToXml(this.mShortcutUser.mService, typedXmlSerializer, z);
            for (int i = 0; i < size; i++) {
                UserPackage userPackage = (UserPackage) this.mPinnedShortcuts.keyAt(i);
                if (!z || userPackage.userId == getOwnerUserId()) {
                    typedXmlSerializer.startTag((String) null, "package");
                    ShortcutService.writeAttr(typedXmlSerializer, "package-name", userPackage.packageName);
                    ShortcutService.writeAttr(typedXmlSerializer, "package-user", userPackage.userId);
                    ArraySet arraySet = (ArraySet) this.mPinnedShortcuts.valueAt(i);
                    int size2 = arraySet.size();
                    for (int i2 = 0; i2 < size2; i2++) {
                        ShortcutService.writeTagValue(typedXmlSerializer, "pin", (String) arraySet.valueAt(i2));
                    }
                    typedXmlSerializer.endTag((String) null, "package");
                }
            }
            typedXmlSerializer.endTag((String) null, "launcher-pins");
        }
    }

    public static ShortcutLauncher loadFromFile(File file, ShortcutUser shortcutUser, int i, boolean z) {
        FileInputStream fileInputStream;
        Exception e;
        ResilientAtomicFile resilientFile = ShortcutPackageItem.getResilientFile(file);
        ShortcutLauncher shortcutLauncher = null;
        try {
            try {
                fileInputStream = resilientFile.openRead();
            } catch (Throwable th) {
                if (resilientFile != null) {
                    try {
                        resilientFile.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        } catch (Exception e2) {
            fileInputStream = null;
            e = e2;
        }
        try {
            if (fileInputStream == null) {
                Slog.d("ShortcutService", "Not found " + file);
                resilientFile.close();
                return null;
            }
            TypedXmlPullParser resolvePullParser = Xml.resolvePullParser(fileInputStream);
            while (true) {
                int next = resolvePullParser.next();
                if (next == 1) {
                    resilientFile.close();
                    return shortcutLauncher;
                }
                if (next == 2) {
                    int depth = resolvePullParser.getDepth();
                    String name = resolvePullParser.getName();
                    if (depth == 1 && "launcher-pins".equals(name)) {
                        shortcutLauncher = loadFromXml(resolvePullParser, shortcutUser, i, z);
                    } else {
                        ShortcutService.throwForInvalidTag(depth, name);
                    }
                }
            }
        } catch (Exception e3) {
            e = e3;
            Slog.e("ShortcutService", "Failed to read file " + resilientFile.getBaseFile(), e);
            resilientFile.failRead(fileInputStream, e);
            ShortcutLauncher loadFromFile = loadFromFile(file, shortcutUser, i, z);
            resilientFile.close();
            return loadFromFile;
        }
    }

    public static ShortcutLauncher loadFromXml(TypedXmlPullParser typedXmlPullParser, ShortcutUser shortcutUser, int i, boolean z) {
        ShortcutLauncher shortcutLauncher = new ShortcutLauncher(shortcutUser, i, ShortcutService.parseStringAttribute(typedXmlPullParser, "package-name"), z ? i : ShortcutService.parseIntAttribute(typedXmlPullParser, "launcher-user", i));
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
                    name.hashCode();
                    if (name.equals("package-info")) {
                        shortcutLauncher.getPackageInfo().loadFromXml(typedXmlPullParser, z);
                    } else if (name.equals("package")) {
                        String parseStringAttribute = ShortcutService.parseStringAttribute(typedXmlPullParser, "package-name");
                        int parseIntAttribute = z ? i : ShortcutService.parseIntAttribute(typedXmlPullParser, "package-user", i);
                        ArraySet arraySet2 = new ArraySet();
                        shortcutLauncher.mPinnedShortcuts.put(UserPackage.of(parseIntAttribute, parseStringAttribute), arraySet2);
                        arraySet = arraySet2;
                    }
                }
                if (depth2 == depth + 2) {
                    name.hashCode();
                    if (name.equals("pin")) {
                        if (arraySet == null) {
                            Slog.w("ShortcutService", "pin in invalid place");
                        } else {
                            arraySet.add(ShortcutService.parseStringAttribute(typedXmlPullParser, "value"));
                        }
                    }
                }
                ShortcutService.warnForInvalidTag(depth2, name);
            }
        }
        return shortcutLauncher;
    }

    public void dump(PrintWriter printWriter, String str, ShortcutService.DumpFilter dumpFilter) {
        printWriter.println();
        printWriter.print(str);
        printWriter.print("Launcher: ");
        printWriter.print(getPackageName());
        printWriter.print("  Package user: ");
        printWriter.print(getPackageUserId());
        printWriter.print("  Owner user: ");
        printWriter.print(getOwnerUserId());
        printWriter.println();
        getPackageInfo().dump(printWriter, str + "  ");
        printWriter.println();
        int size = this.mPinnedShortcuts.size();
        for (int i = 0; i < size; i++) {
            printWriter.println();
            UserPackage userPackage = (UserPackage) this.mPinnedShortcuts.keyAt(i);
            printWriter.print(str);
            printWriter.print("  ");
            printWriter.print("Package: ");
            printWriter.print(userPackage.packageName);
            printWriter.print("  User: ");
            printWriter.println(userPackage.userId);
            ArraySet arraySet = (ArraySet) this.mPinnedShortcuts.valueAt(i);
            int size2 = arraySet.size();
            for (int i2 = 0; i2 < size2; i2++) {
                printWriter.print(str);
                printWriter.print("    Pinned: ");
                printWriter.print((String) arraySet.valueAt(i2));
                printWriter.println();
            }
        }
    }

    @Override // com.android.server.pm.ShortcutPackageItem
    public JSONObject dumpCheckin(boolean z) {
        return super.dumpCheckin(z);
    }

    public ArraySet getAllPinnedShortcutsForTest(String str, int i) {
        return new ArraySet((ArraySet) this.mPinnedShortcuts.get(UserPackage.of(i, str)));
    }

    @Override // com.android.server.pm.ShortcutPackageItem
    public File getShortcutPackageItemFile() {
        ShortcutUser shortcutUser = this.mShortcutUser;
        return new File(new File(shortcutUser.mService.injectUserDataPath(shortcutUser.getUserId()), "launchers"), getPackageName() + getPackageUserId() + ".xml");
    }
}
