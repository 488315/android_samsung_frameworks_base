package com.android.server.backup;

import android.app.backup.BackupDataOutput;
import android.app.backup.BlobBackupHelper;
import android.os.ParcelFileDescriptor;

import com.android.server.LocalServices;
import com.android.server.grammaticalinflection.GrammaticalInflectionBackupHelper;
import com.android.server.grammaticalinflection.GrammaticalInflectionService;

import java.nio.ByteBuffer;

public final class SystemGrammaticalGenderBackupHelper extends BlobBackupHelper {
    public final GrammaticalInflectionService.GrammaticalInflectionManagerInternalImpl
            mGrammarInflectionManagerInternal;
    public final int mUserId;

    public SystemGrammaticalGenderBackupHelper(int i) {
        super(1, new String[] {"system_gender"});
        this.mUserId = i;
        this.mGrammarInflectionManagerInternal =
                (GrammaticalInflectionService.GrammaticalInflectionManagerInternalImpl)
                        LocalServices.getService(
                                GrammaticalInflectionService
                                        .GrammaticalInflectionManagerInternalImpl.class);
    }

    public final void applyRestoredPayload(String str, byte[] bArr) {
        GrammaticalInflectionService.GrammaticalInflectionManagerInternalImpl
                grammaticalInflectionManagerInternalImpl;
        if (!"system_gender".equals(str)
                || (grammaticalInflectionManagerInternalImpl =
                                this.mGrammarInflectionManagerInternal)
                        == null) {
            return;
        }
        int i = this.mUserId;
        GrammaticalInflectionBackupHelper grammaticalInflectionBackupHelper =
                GrammaticalInflectionService.this.mBackupHelper;
        grammaticalInflectionBackupHelper.getClass();
        grammaticalInflectionBackupHelper.mGrammaticalGenderService.setSystemWideGrammaticalGender(
                ByteBuffer.wrap(bArr).getInt(), i);
    }

    public final byte[] getBackupPayload(String str) {
        GrammaticalInflectionService.GrammaticalInflectionManagerInternalImpl
                grammaticalInflectionManagerInternalImpl;
        if (!"system_gender".equals(str)
                || (grammaticalInflectionManagerInternalImpl =
                                this.mGrammarInflectionManagerInternal)
                        == null) {
            return null;
        }
        int i = this.mUserId;
        GrammaticalInflectionService grammaticalInflectionService =
                GrammaticalInflectionService.this;
        GrammaticalInflectionService.m569$$Nest$menforceCallerPermissions(
                grammaticalInflectionService);
        int systemGrammaticalGender =
                grammaticalInflectionService.mBackupHelper.mGrammaticalGenderService
                        .getSystemGrammaticalGender(i);
        ByteBuffer allocate = ByteBuffer.allocate(4);
        allocate.putInt(systemGrammaticalGender);
        return allocate.array();
    }

    public final void performBackup(
            ParcelFileDescriptor parcelFileDescriptor,
            BackupDataOutput backupDataOutput,
            ParcelFileDescriptor parcelFileDescriptor2) {
        if ((backupDataOutput.getTransportFlags() & 1) == 0) {
            return;
        }
        super.performBackup(parcelFileDescriptor, backupDataOutput, parcelFileDescriptor2);
    }
}
