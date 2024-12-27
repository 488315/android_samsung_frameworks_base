package com.android.server.backup;

import android.app.backup.BackupAgentHelper;
import android.app.backup.BackupDataInput;
import android.app.backup.BackupHelperWithLogger;
import android.app.backup.BackupRestoreEventLogger;
import android.app.backup.FullBackupDataOutput;
import android.app.backup.WallpaperBackupHelper;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.ArraySet;

import com.google.android.collect.Sets;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class SystemBackupAgent extends BackupAgentHelper {
    public static final String WALLPAPER_IMAGE;
    public static final String WALLPAPER_INFO;
    public static final Set sEligibleHelpersForNonSystemUser;
    public static final Set sEligibleHelpersForProfileUser;
    public BackupRestoreEventLogger mLogger;
    public int mUserId = 0;
    public boolean mIsProfileUser = false;

    static {
        Environment.getUserSystemDirectory(0).getAbsolutePath();
        WALLPAPER_IMAGE =
                new File(Environment.getUserSystemDirectory(0), "wallpaper").getAbsolutePath();
        Environment.getUserSystemDirectory(0).getAbsolutePath();
        WALLPAPER_INFO =
                new File(Environment.getUserSystemDirectory(0), "wallpaper_info.xml")
                        .getAbsolutePath();
        ArraySet newArraySet =
                Sets.newArraySet(
                        new String[] {
                            "permissions",
                            "notifications",
                            "account_sync_settings",
                            "app_locales",
                            "companion",
                            "app_gender",
                            "system_gender"
                        });
        sEligibleHelpersForProfileUser = newArraySet;
        ArraySet newArraySet2 =
                Sets.newArraySet(
                        new String[] {
                            "account_manager",
                            "usage_stats",
                            "preferred_activities",
                            "shortcut_manager"
                        });
        HashSet hashSet = new HashSet(newArraySet);
        hashSet.addAll(newArraySet2);
        sEligibleHelpersForNonSystemUser = hashSet;
    }

    public final void addHelperIfEligibleForUser(
            String str, BackupHelperWithLogger backupHelperWithLogger) {
        if (this.mUserId == 0
                ? true
                : this.mIsProfileUser
                        ? ((ArraySet) sEligibleHelpersForProfileUser).contains(str)
                        : ((HashSet) sEligibleHelpersForNonSystemUser).contains(str)) {
            addHelper(str, backupHelperWithLogger);
            if (Flags.enableMetricsSystemBackupAgents()) {
                backupHelperWithLogger.setLogger(this.mLogger);
            }
        }
    }

    public final void onCreate(UserHandle userHandle, int i) {
        super.onCreate(userHandle, i);
        this.mLogger = getBackupRestoreEventLogger();
        int identifier = userHandle.getIdentifier();
        this.mUserId = identifier;
        if (identifier != 0) {
            this.mIsProfileUser =
                    ((UserManager)
                                    createContextAsUser(userHandle, 0)
                                            .getSystemService(UserManager.class))
                            .isProfile();
        }
        addHelperIfEligibleForUser(
                "account_sync_settings", new AccountSyncSettingsBackupHelper(this, this.mUserId));
        addHelperIfEligibleForUser(
                "preferred_activities", new PreferredActivityBackupHelper(this.mUserId));
        addHelperIfEligibleForUser("notifications", new NotificationBackupHelper(this.mUserId));
        addHelperIfEligibleForUser("permissions", new PermissionBackupHelper(this.mUserId));
        addHelperIfEligibleForUser("usage_stats", new UsageStatsBackupHelper(this.mUserId));
        addHelperIfEligibleForUser("shortcut_manager", new ShortcutBackupHelper(this.mUserId));
        addHelperIfEligibleForUser("account_manager", new AccountManagerBackupHelper(this.mUserId));
        if (!getPackageManager().hasSystemFeature("android.software.slices_disabled")) {
            addHelperIfEligibleForUser("slices", new SliceBackupHelper(this));
        }
        addHelperIfEligibleForUser("people", new PeopleBackupHelper(this.mUserId));
        addHelperIfEligibleForUser("app_locales", new AppSpecificLocalesBackupHelper(this.mUserId));
        addHelperIfEligibleForUser(
                "app_gender", new AppGrammaticalGenderBackupHelper(this.mUserId));
        addHelperIfEligibleForUser("companion", new CompanionBackupHelper(this.mUserId));
        addHelperIfEligibleForUser(
                "system_gender", new SystemGrammaticalGenderBackupHelper(this.mUserId));
    }

    @Override // android.app.backup.BackupAgent
    public final void onFullBackup(FullBackupDataOutput fullBackupDataOutput) {}

    @Override // android.app.backup.BackupAgentHelper, android.app.backup.BackupAgent
    public final void onRestore(
            BackupDataInput backupDataInput, int i, ParcelFileDescriptor parcelFileDescriptor) {
        addHelper(
                "wallpaper",
                new WallpaperBackupHelper(
                        this, new String[] {"/data/data/com.android.settings/files/wallpaper"}));
        addHelper(
                "system_files",
                new WallpaperBackupHelper(
                        this, new String[] {"/data/data/com.android.settings/files/wallpaper"}));
        super.onRestore(backupDataInput, i, parcelFileDescriptor);
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onRestoreFile(
            android.os.ParcelFileDescriptor r18,
            long r19,
            int r21,
            java.lang.String r22,
            java.lang.String r23,
            long r24,
            long r26) {
        /*
            r17 = this;
            r0 = r22
            r1 = r23
            java.lang.String r2 = "Couldn't restore settings\n"
            java.lang.String r3 = "Skipping unrecognized system file: [ "
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "Restoring file domain="
            r4.<init>(r5)
            r4.append(r0)
            java.lang.String r5 = " path="
            r4.append(r5)
            r4.append(r1)
            java.lang.String r4 = r4.toString()
            java.lang.String r5 = "SystemBackupAgent"
            android.util.Slog.i(r5, r4)
            java.lang.String r4 = "r"
            boolean r4 = r0.equals(r4)
            java.lang.String r6 = "wallpaper"
            if (r4 == 0) goto L51
            java.lang.String r4 = "wallpaper_info.xml"
            boolean r4 = r1.equals(r4)
            r7 = 1
            if (r4 == 0) goto L43
            java.io.File r4 = new java.io.File
            java.lang.String r8 = com.android.server.backup.SystemBackupAgent.WALLPAPER_INFO
            r4.<init>(r8)
        L40:
            r16 = r4
            goto L54
        L43:
            boolean r4 = r1.equals(r6)
            if (r4 == 0) goto L51
            java.io.File r4 = new java.io.File
            java.lang.String r8 = com.android.server.backup.SystemBackupAgent.WALLPAPER_IMAGE
            r4.<init>(r8)
            goto L40
        L51:
            r7 = 0
            r4 = 0
            goto L40
        L54:
            if (r16 != 0) goto L72
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.io.IOException -> L9f
            r4.<init>(r3)     // Catch: java.io.IOException -> L9f
            r4.append(r0)     // Catch: java.io.IOException -> L9f
            java.lang.String r0 = " : "
            r4.append(r0)     // Catch: java.io.IOException -> L9f
            r4.append(r1)     // Catch: java.io.IOException -> L9f
            java.lang.String r0 = " ]"
            r4.append(r0)     // Catch: java.io.IOException -> L9f
            java.lang.String r0 = r4.toString()     // Catch: java.io.IOException -> L9f
            android.util.Slog.w(r5, r0)     // Catch: java.io.IOException -> L9f
        L72:
            r8 = r18
            r9 = r19
            r11 = r21
            r12 = r24
            r14 = r26
            android.app.backup.FullBackup.restoreFile(r8, r9, r11, r12, r14, r16)     // Catch: java.io.IOException -> L9f
            if (r7 == 0) goto Lb5
            android.os.IBinder r0 = android.os.ServiceManager.getService(r6)     // Catch: java.io.IOException -> L9f
            android.app.IWallpaperManager r0 = (android.app.IWallpaperManager) r0     // Catch: java.io.IOException -> L9f
            if (r0 == 0) goto Lb5
            r0.settingsRestored()     // Catch: android.os.RemoteException -> L8d java.io.IOException -> L9f
            goto Lb5
        L8d:
            r0 = move-exception
            r1 = r0
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: java.io.IOException -> L9f
            r0.<init>(r2)     // Catch: java.io.IOException -> L9f
            r0.append(r1)     // Catch: java.io.IOException -> L9f
            java.lang.String r0 = r0.toString()     // Catch: java.io.IOException -> L9f
            android.util.Slog.e(r5, r0)     // Catch: java.io.IOException -> L9f
            goto Lb5
        L9f:
            if (r7 == 0) goto Lb5
            java.io.File r0 = new java.io.File
            java.lang.String r1 = com.android.server.backup.SystemBackupAgent.WALLPAPER_IMAGE
            r0.<init>(r1)
            r0.delete()
            java.io.File r0 = new java.io.File
            java.lang.String r1 = com.android.server.backup.SystemBackupAgent.WALLPAPER_INFO
            r0.<init>(r1)
            r0.delete()
        Lb5:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.backup.SystemBackupAgent.onRestoreFile(android.os.ParcelFileDescriptor,"
                    + " long, int, java.lang.String, java.lang.String, long, long):void");
    }
}
