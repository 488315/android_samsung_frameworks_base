package com.android.server.knox.dar;

import android.os.Environment;
import java.io.File;

/* loaded from: classes2.dex */
public final class FileUtil {
  public static File getUserSystemDir(int i) {
    return new File(new File(Environment.getDataSystemDirectory(), "users"), Integer.toString(i));
  }

  public static File getEncUserDir(int i) {
    return new File(new File(Environment.getDataDirectory(), "enc_user"), Integer.toString(i));
  }
}
