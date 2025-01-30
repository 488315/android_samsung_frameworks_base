package com.android.server.security;

import android.annotation.SystemApi;
import android.os.ParcelFileDescriptor;
import com.android.internal.security.VerityUtils;
import java.io.File;

@SystemApi(client = SystemApi.Client.SYSTEM_SERVER)
/* loaded from: classes3.dex */
public final class FileIntegrity {
  @SystemApi(client = SystemApi.Client.SYSTEM_SERVER)
  public static void setUpFsVerity(File file) {
    VerityUtils.setUpFsverity(file.getAbsolutePath());
  }

  @SystemApi(client = SystemApi.Client.SYSTEM_SERVER)
  public static void setUpFsVerity(ParcelFileDescriptor parcelFileDescriptor) {
    VerityUtils.setUpFsverity(parcelFileDescriptor.getFd());
  }
}
