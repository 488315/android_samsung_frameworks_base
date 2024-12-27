package com.android.server.pm;

import android.content.pm.PackageInfo;
import android.content.pm.SigningInfo;

import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.backup.BackupUtils;

import libcore.util.HexEncoding;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Base64;

public final class ShortcutPackageInfo {
    public boolean mBackupAllowedInitialized;
    public long mLastUpdateTime;
    public ArrayList mSigHashes;
    public long mVersionCode;
    public long mBackupSourceVersionCode = -1;
    public boolean mIsShadow = false;
    public boolean mBackupAllowed = false;
    public boolean mBackupSourceBackupAllowed = false;

    public ShortcutPackageInfo(long j, long j2, ArrayList arrayList) {
        this.mVersionCode = j;
        this.mLastUpdateTime = j2;
        this.mSigHashes = arrayList;
    }

    public static ShortcutPackageInfo generateForInstalledPackageForTest(
            ShortcutService shortcutService, String str, int i) {
        PackageInfo packageInfo = shortcutService.getPackageInfo(str, i, true);
        SigningInfo signingInfo = packageInfo.signingInfo;
        if (signingInfo == null) {
            BootReceiver$$ExternalSyntheticOutline0.m(
                    "Can't get signatures: package=", str, "ShortcutService");
            return null;
        }
        ShortcutPackageInfo shortcutPackageInfo =
                new ShortcutPackageInfo(
                        packageInfo.getLongVersionCode(),
                        packageInfo.lastUpdateTime,
                        BackupUtils.hashSignatureArray(signingInfo.getApkContentsSigners()));
        shortcutPackageInfo.mBackupSourceBackupAllowed =
                (packageInfo.applicationInfo.flags & 32768) != 0;
        shortcutPackageInfo.mBackupSourceVersionCode = packageInfo.getLongVersionCode();
        return shortcutPackageInfo;
    }

    public final void dump(PrintWriter printWriter, String str) {
        printWriter.println();
        printWriter.print(str);
        printWriter.println("PackageInfo:");
        printWriter.print(str);
        printWriter.print("  IsShadow: ");
        printWriter.print(this.mIsShadow);
        printWriter.print(this.mIsShadow ? " (not installed)" : " (installed)");
        printWriter.println();
        printWriter.print(str);
        printWriter.print("  Version: ");
        printWriter.print(this.mVersionCode);
        printWriter.println();
        if (this.mBackupAllowedInitialized) {
            printWriter.print(str);
            printWriter.print("  Backup Allowed: ");
            printWriter.print(this.mBackupAllowed);
            printWriter.println();
        }
        if (this.mBackupSourceVersionCode != -1) {
            printWriter.print(str);
            printWriter.print("  Backup source version: ");
            printWriter.print(this.mBackupSourceVersionCode);
            printWriter.println();
            printWriter.print(str);
            printWriter.print("  Backup source backup allowed: ");
            printWriter.print(this.mBackupSourceBackupAllowed);
            printWriter.println();
        }
        printWriter.print(str);
        printWriter.print("  Last package update time: ");
        printWriter.print(this.mLastUpdateTime);
        printWriter.println();
        for (int i = 0; i < this.mSigHashes.size(); i++) {
            printWriter.print(str);
            printWriter.print("    ");
            printWriter.print("SigHash: ");
            printWriter.println(HexEncoding.encode((byte[]) this.mSigHashes.get(i)));
        }
    }

    public boolean isBackupSourceBackupAllowed() {
        return this.mBackupSourceBackupAllowed;
    }

    public final void loadFromXml(TypedXmlPullParser typedXmlPullParser, boolean z) {
        long parseLongAttribute =
                ShortcutService.parseLongAttribute(typedXmlPullParser, "version", -1L);
        long parseLongAttribute2 =
                ShortcutService.parseLongAttribute(typedXmlPullParser, "last_udpate_time", 0L);
        boolean z2 = z || ShortcutService.parseLongAttribute(typedXmlPullParser, "shadow", 0L) == 1;
        long parseLongAttribute3 =
                ShortcutService.parseLongAttribute(typedXmlPullParser, "bk_src_version", -1L);
        long j = 1;
        boolean z3 = ShortcutService.parseLongAttribute(typedXmlPullParser, "allow-backup", j) == 1;
        boolean z4 =
                ShortcutService.parseLongAttribute(typedXmlPullParser, "bk_src_backup-allowed", j)
                        == 1;
        ArrayList arrayList = new ArrayList();
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1 || (next == 3 && typedXmlPullParser.getDepth() <= depth)) {
                break;
            }
            if (next == 2) {
                int depth2 = typedXmlPullParser.getDepth();
                String name = typedXmlPullParser.getName();
                boolean z5 = z2;
                if (depth2 == depth + 1) {
                    name.getClass();
                    if (name.equals("signature")) {
                        arrayList.add(
                                Base64.getDecoder()
                                        .decode(
                                                typedXmlPullParser.getAttributeValue(
                                                        (String) null, "hash")));
                        z2 = z5;
                    }
                }
                ShortcutService.warnForInvalidTag(depth2, name);
                z2 = z5;
            }
        }
        boolean z6 = z2;
        if (z) {
            this.mVersionCode = -1L;
            this.mBackupSourceVersionCode = parseLongAttribute;
            this.mBackupSourceBackupAllowed = z3;
        } else {
            this.mVersionCode = parseLongAttribute;
            this.mBackupSourceVersionCode = parseLongAttribute3;
            this.mBackupSourceBackupAllowed = z4;
        }
        this.mLastUpdateTime = parseLongAttribute2;
        this.mIsShadow = z6;
        this.mSigHashes = arrayList;
        this.mBackupAllowed = false;
        this.mBackupAllowedInitialized = false;
    }

    public final void saveToXml(
            ShortcutService shortcutService, TypedXmlSerializer typedXmlSerializer, boolean z) {
        if (z && !this.mBackupAllowedInitialized) {
            shortcutService.wtf("Backup happened before mBackupAllowed is initialized.", null);
        }
        typedXmlSerializer.startTag((String) null, "package-info");
        ShortcutService.writeAttr(typedXmlSerializer, "version", this.mVersionCode);
        ShortcutService.writeAttr(typedXmlSerializer, "last_udpate_time", this.mLastUpdateTime);
        ShortcutService.writeAttr(typedXmlSerializer, "shadow", this.mIsShadow);
        ShortcutService.writeAttr(typedXmlSerializer, "allow-backup", this.mBackupAllowed);
        ShortcutService.writeAttr(
                typedXmlSerializer, "allow-backup-initialized", this.mBackupAllowedInitialized);
        ShortcutService.writeAttr(
                typedXmlSerializer, "bk_src_version", this.mBackupSourceVersionCode);
        ShortcutService.writeAttr(
                typedXmlSerializer, "bk_src_backup-allowed", this.mBackupSourceBackupAllowed);
        for (int i = 0; i < this.mSigHashes.size(); i++) {
            typedXmlSerializer.startTag((String) null, "signature");
            ShortcutService.writeAttr(
                    typedXmlSerializer,
                    "hash",
                    Base64.getEncoder().encodeToString((byte[]) this.mSigHashes.get(i)));
            typedXmlSerializer.endTag((String) null, "signature");
        }
        typedXmlSerializer.endTag((String) null, "package-info");
    }
}
