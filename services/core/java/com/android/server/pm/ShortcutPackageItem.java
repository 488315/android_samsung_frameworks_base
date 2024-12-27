package com.android.server.pm;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManagerInternal;
import android.content.pm.ShortcutInfo;
import android.content.pm.SigningInfo;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.util.Slog;
import android.util.Xml;

import com.android.internal.util.Preconditions;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0;
import com.android.server.HeimdAllFsService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.PinnerService$$ExternalSyntheticOutline0;
import com.android.server.backup.BackupUtils;

import com.samsung.android.server.pm.PmLog;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public abstract class ShortcutPackageItem {
    public final ShortcutPackageInfo mPackageInfo;
    public final String mPackageName;
    public final int mPackageUserId;
    public final ShortcutBitmapSaver mShortcutBitmapSaver;
    public ShortcutUser mShortcutUser;
    public final Object mPackageItemLock = new Object();
    public final ShortcutPackageItem$$ExternalSyntheticLambda0 mSaveShortcutPackageRunner =
            new Runnable() { // from class:
                             // com.android.server.pm.ShortcutPackageItem$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ShortcutPackageItem shortcutPackageItem = ShortcutPackageItem.this;
                    shortcutPackageItem.waitForBitmapSaves();
                    File shortcutPackageItemFile = shortcutPackageItem.getShortcutPackageItemFile();
                    Slog.d(
                            "ShortcutService",
                            "Saving package item "
                                    + shortcutPackageItem.mPackageName
                                    + " to "
                                    + shortcutPackageItemFile);
                    synchronized (shortcutPackageItem.mPackageItemLock) {
                        shortcutPackageItemFile.getParentFile().mkdirs();
                        shortcutPackageItem.saveToFileLocked(shortcutPackageItemFile);
                        shortcutPackageItem.scheduleSaveToAppSearchLocked();
                    }
                }
            };

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.server.pm.ShortcutPackageItem$$ExternalSyntheticLambda0] */
    public ShortcutPackageItem(
            ShortcutUser shortcutUser, int i, String str, ShortcutPackageInfo shortcutPackageInfo) {
        this.mShortcutUser = shortcutUser;
        this.mPackageUserId = i;
        this.mPackageName = (String) Preconditions.checkStringNotEmpty(str);
        this.mPackageInfo = shortcutPackageInfo;
        this.mShortcutBitmapSaver = new ShortcutBitmapSaver(shortcutUser.mService);
    }

    public static ResilientAtomicFile getResilientFile(File file) {
        String path = file.getPath();
        return new ResilientAtomicFile(
                file,
                new File(
                        ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(
                                path, ".backup")),
                new File(
                        ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(
                                path, ".reservecopy")),
                505,
                "shortcut package item",
                null);
    }

    public final void attemptToRestoreIfNeededAndSave() {
        ShortcutPackageInfo shortcutPackageInfo = this.mPackageInfo;
        if (shortcutPackageInfo.mIsShadow) {
            ShortcutService shortcutService = this.mShortcutUser.mService;
            String str = this.mPackageName;
            int i = this.mPackageUserId;
            if (shortcutService.isPackageInstalled(i, str)) {
                int i2 = 102;
                if (shortcutPackageInfo.mSigHashes.size() > 0) {
                    PackageInfo packageInfo = shortcutService.getPackageInfo(str, i, true);
                    packageInfo.getLongVersionCode();
                    if (!BackupUtils.signaturesMatch(
                            shortcutPackageInfo.mSigHashes,
                            packageInfo,
                            (PackageManagerInternal)
                                    LocalServices.getService(PackageManagerInternal.class))) {
                        Slog.w("ShortcutService", "Can't restore: Package signature mismatch");
                    } else if (shortcutService.mSmartSwitchBackupAllowed.get()
                            || ((packageInfo.applicationInfo.flags & 32768) != 0
                                    && shortcutPackageInfo.mBackupSourceBackupAllowed)) {
                        i2 = 0;
                    } else {
                        Slog.w(
                                "ShortcutService",
                                "Can't restore: package didn't or doesn't allow backup");
                        i2 = 101;
                    }
                } else {
                    shortcutService.wtf(
                            AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0.m(
                                    i,
                                    "Attempted to restore package ",
                                    str,
                                    "/u",
                                    " but signatures not found in the restore data."),
                            null);
                }
                onRestored(i2);
                shortcutPackageInfo.mIsShadow = false;
                scheduleSave();
            }
        }
    }

    public abstract int getOwnerUserId();

    public abstract File getShortcutPackageItemFile();

    public abstract void onRestored(int i);

    public final void refreshPackageSignatureAndSave() {
        ShortcutPackageInfo shortcutPackageInfo = this.mPackageInfo;
        boolean z = shortcutPackageInfo.mIsShadow;
        if (z) {
            return;
        }
        ShortcutService shortcutService = this.mShortcutUser.mService;
        String str = this.mPackageName;
        if (z) {
            StringBuilder m =
                    DumpUtils$$ExternalSyntheticOutline0.m(
                            "Attempted to refresh package info for shadow package ",
                            str,
                            ", user=");
            m.append(getOwnerUserId());
            shortcutService.wtf(m.toString(), null);
        } else {
            PackageInfo packageInfo =
                    shortcutService.getPackageInfo(str, this.mPackageUserId, true);
            if (packageInfo == null) {
                HeimdAllFsService$$ExternalSyntheticOutline0.m(
                        "Package not found: ", str, "ShortcutService");
            } else {
                SigningInfo signingInfo = packageInfo.signingInfo;
                if (signingInfo == null) {
                    PinnerService$$ExternalSyntheticOutline0.m(
                            "Not refreshing signature for ",
                            str,
                            " since it appears to have no signing info.",
                            "ShortcutService");
                } else {
                    shortcutPackageInfo.mSigHashes =
                            BackupUtils.hashSignatureArray(signingInfo.getApkContentsSigners());
                }
            }
        }
        scheduleSave();
    }

    public final void removeIcon(ShortcutInfo shortcutInfo) {
        synchronized (this.mPackageItemLock) {
            this.mShortcutBitmapSaver.getClass();
            ShortcutBitmapSaver.removeIcon(shortcutInfo);
        }
    }

    public final void removeShortcutPackageItem() {
        synchronized (this.mPackageItemLock) {
            ResilientAtomicFile resilientFile = getResilientFile(getShortcutPackageItemFile());
            resilientFile.mFile.delete();
            resilientFile.mTemporaryBackup.delete();
            resilientFile.mReserveCopy.delete();
        }
    }

    public final void saveToFileLocked(File file) {
        FileOutputStream startWrite;
        ResilientAtomicFile resilientFile = getResilientFile(file);
        FileOutputStream fileOutputStream = null;
        try {
            try {
                startWrite = resilientFile.startWrite();
            } catch (IOException | XmlPullParserException e) {
                e = e;
            }
            try {
                TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(startWrite);
                resolveSerializer.startDocument((String) null, Boolean.TRUE);
                saveToXml(resolveSerializer, false);
                resolveSerializer.endDocument();
                startWrite.flush();
                resilientFile.finishWrite(startWrite);
            } catch (IOException | XmlPullParserException e2) {
                e = e2;
                fileOutputStream = startWrite;
                Slog.e("ShortcutService", "Failed to write to file " + resilientFile.mFile, e);
                PmLog.logDebugInfo(
                        "Failed to write to shortcut file "
                                + resilientFile.mFile
                                + ", Error : "
                                + e);
                resilientFile.failWrite(fileOutputStream);
                resilientFile.close();
            }
            resilientFile.close();
        } catch (Throwable th) {
            try {
                resilientFile.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public abstract void saveToXml(TypedXmlSerializer typedXmlSerializer, boolean z);

    public final void scheduleSave() {
        ShortcutService shortcutService = this.mShortcutUser.mService;
        ShortcutPackageItem$$ExternalSyntheticLambda0
                shortcutPackageItem$$ExternalSyntheticLambda0 = this.mSaveShortcutPackageRunner;
        shortcutService.injectPostToHandlerDebounced(
                shortcutPackageItem$$ExternalSyntheticLambda0,
                shortcutPackageItem$$ExternalSyntheticLambda0);
    }

    public void scheduleSaveToAppSearchLocked() {}

    public void verifyStates() {}

    public final void waitForBitmapSaves() {
        synchronized (this.mPackageItemLock) {
            this.mShortcutBitmapSaver.waitForAllSavesLocked();
        }
    }
}
