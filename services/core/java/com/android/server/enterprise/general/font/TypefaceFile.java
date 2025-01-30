package com.android.server.enterprise.general.font;

/* loaded from: classes2.dex */
public class TypefaceFile {
  public String fileName = null;
  public String droidName = null;

  public String getFileName() {
    return this.fileName;
  }

  public void setFileName(String str) {
    this.fileName = str;
  }

  public String getDroidName() {
    return this.droidName;
  }

  public void setDroidName(String str) {
    this.droidName = str;
  }

  public String toString() {
    return "Filename = " + this.fileName + "\nDroidname = " + this.droidName;
  }
}
