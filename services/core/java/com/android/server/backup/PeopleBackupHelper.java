package com.android.server.backup;

import android.app.backup.BlobBackupHelper;

import com.android.server.HeimdAllFsService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.people.PeopleServiceInternal;

public final class PeopleBackupHelper extends BlobBackupHelper {
    public final int mUserId;

    public PeopleBackupHelper(int i) {
        super(1, new String[] {"people_conversation_infos"});
        this.mUserId = i;
    }

    public final void applyRestoredPayload(String str, byte[] bArr) {
        if ("people_conversation_infos".equals(str)) {
            ((PeopleServiceInternal) LocalServices.getService(PeopleServiceInternal.class))
                    .restore(this.mUserId, bArr);
        } else {
            HeimdAllFsService$$ExternalSyntheticOutline0.m(
                    "Unexpected restore key ", str, "PeopleBackupHelper");
        }
    }

    public final byte[] getBackupPayload(String str) {
        if ("people_conversation_infos".equals(str)) {
            return ((PeopleServiceInternal) LocalServices.getService(PeopleServiceInternal.class))
                    .getBackupPayload(this.mUserId);
        }
        HeimdAllFsService$$ExternalSyntheticOutline0.m(
                "Unexpected backup key ", str, "PeopleBackupHelper");
        return new byte[0];
    }
}
