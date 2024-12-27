package com.android.server.backup.params;

import com.android.server.backup.utils.BackupEligibilityRules;

public final class AdbBackupParams extends AdbParams {
    public boolean allApps;
    public BackupEligibilityRules backupEligibilityRules;
    public boolean doCompress;
    public boolean doWidgets;
    public int extraFlag;
    public boolean includeApks;
    public boolean includeKeyValue;
    public boolean includeObbs;
    public boolean includeShared;
    public boolean includeSystem;
    public String[] packages;
    public String[] smartswitchBackupPath;
    public int transportFlags;
}
