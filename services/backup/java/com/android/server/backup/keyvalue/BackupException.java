package com.android.server.backup.keyvalue;

import android.util.AndroidException;

/* loaded from: classes.dex */
abstract class BackupException extends AndroidException {
  public BackupException() {}

  public BackupException(Exception exc) {
    super(exc);
  }
}
