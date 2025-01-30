package com.android.server.backup.params;

import android.os.ParcelFileDescriptor;
import com.android.server.backup.utils.BackupEligibilityRules;

/* loaded from: classes.dex */
public class AdbBackupParams extends AdbParams {
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

    public AdbBackupParams(ParcelFileDescriptor parcelFileDescriptor, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, String[] strArr, BackupEligibilityRules backupEligibilityRules, int i, String[] strArr2) {
        this.f1647fd = parcelFileDescriptor;
        this.includeApks = z;
        this.includeObbs = z2;
        this.includeShared = z3;
        this.doWidgets = z4;
        this.allApps = z5;
        this.includeSystem = z6;
        this.doCompress = z7;
        this.includeKeyValue = z8;
        this.packages = strArr;
        this.backupEligibilityRules = backupEligibilityRules;
        this.extraFlag = i;
        this.smartswitchBackupPath = strArr2;
    }
}
