package com.android.server.locales;

/* loaded from: classes2.dex */
public abstract class LocaleManagerInternal {
  public abstract byte[] getBackupPayload(int i);

  public abstract void stageAndApplyRestoredPayload(byte[] bArr, int i);
}
