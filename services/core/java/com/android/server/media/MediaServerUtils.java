package com.android.server.media;

import android.content.Context;
import android.content.pm.PackageManagerInternal;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.os.UserHandle;
import android.text.TextUtils;

import com.android.server.LocalServices;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.pm.PackageManagerService;

import java.util.Arrays;

public abstract class MediaServerUtils {
    public static void enforcePackageName(Context context, String str, int i) {
        if (i == 0 || i == 2000) {
            return;
        }
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("packageName may not be empty");
        }
        if (((PackageManagerService.PackageManagerInternalImpl)
                        ((PackageManagerInternal)
                                LocalServices.getService(PackageManagerInternal.class)))
                .isSameApp(i, UserHandle.getUserId(i), 0L, str)) {
            return;
        }
        throw new IllegalArgumentException(
                AudioOffloadInfo$$ExternalSyntheticOutline0.m(
                        StorageManagerService$$ExternalSyntheticOutline0.m(
                                i,
                                "packageName does not belong to the calling uid; pkg=",
                                str,
                                ", uid=",
                                " ("),
                        Arrays.toString(context.getPackageManager().getPackagesForUid(i)),
                        ")"));
    }
}
