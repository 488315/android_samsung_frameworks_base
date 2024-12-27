package com.android.server.security;

import android.annotation.SystemApi;
import android.os.ParcelFileDescriptor;

import com.android.internal.security.VerityUtils;

import java.io.File;
import java.io.IOException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
@SystemApi(client = SystemApi.Client.SYSTEM_SERVER)
/* loaded from: classes2.dex */
public final class FileIntegrity {
    @SystemApi(client = SystemApi.Client.SYSTEM_SERVER)
    public static void setUpFsVerity(ParcelFileDescriptor parcelFileDescriptor) throws IOException {
        VerityUtils.setUpFsverity(parcelFileDescriptor.getFd());
    }

    @SystemApi(client = SystemApi.Client.SYSTEM_SERVER)
    public static void setUpFsVerity(File file) throws IOException {
        ParcelFileDescriptor open = ParcelFileDescriptor.open(file, 268435456);
        try {
            setUpFsVerity(open);
            if (open != null) {
                open.close();
            }
        } catch (Throwable th) {
            if (open != null) {
                try {
                    open.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }
}
