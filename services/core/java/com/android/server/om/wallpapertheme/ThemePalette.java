package com.android.server.om.wallpapertheme;

import android.os.FileUtils;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class ThemePalette {
  public String TAG = "SWT_ThemePalette";
  public boolean mIsGray = false;
  public List mMonetPaletteGG;
  public List mMonetPaletteSS;

  public int getMonetColorSS(int i, int i2) {
    return getMonetColorSS((i * 13) + i2);
  }

  public int getMonetColorSS(int i) {
    List list = this.mMonetPaletteSS;
    if (list == null) {
      return 0;
    }
    return ((Integer) list.get(i)).intValue();
  }

  public int getMonetColorGG(int i, int i2) {
    return getMonetColorGG((i * 13) + i2);
  }

  public int getMonetColorGG(int i) {
    List list = this.mMonetPaletteGG;
    if (list == null) {
      return 0;
    }
    return ((Integer) list.get(i)).intValue();
  }

  public void setPalette(List list, List list2, boolean z) {
    this.mMonetPaletteSS = list;
    this.mMonetPaletteGG = list2;
    this.mIsGray = z;
    Log.i(this.TAG, "palette updated");
  }

  public List getPaletteSS() {
    return this.mMonetPaletteSS;
  }

  public List getPaletteGG() {
    return this.mMonetPaletteGG;
  }

  public void writeLastPalette() {
    File file;
    OutputStreamWriter outputStreamWriter;
    OutputStreamWriter outputStreamWriter2 = null;
    try {
      try {
        try {
          File file2 = new File("/data/overlays/wallpapertheme/");
          if (!file2.exists()) {
            file2.mkdir();
            FileUtils.setPermissions(file2, 511, -1, -1);
          }
          file = new File("/data/overlays/wallpapertheme/last_palette.txt");
          outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
        } catch (IOException e) {
          Log.w(this.TAG, e);
          return;
        }
      } catch (Exception e2) {
        e = e2;
      }
    } catch (Throwable th) {
      th = th;
    }
    try {
      Iterator it = this.mMonetPaletteSS.iterator();
      while (it.hasNext()) {
        outputStreamWriter.write(((Integer) it.next()).intValue() + System.lineSeparator());
      }
      Iterator it2 = this.mMonetPaletteGG.iterator();
      while (it2.hasNext()) {
        outputStreamWriter.write(((Integer) it2.next()).intValue() + System.lineSeparator());
      }
      outputStreamWriter.write(this.mIsGray ? "1" : "0");
      file.setReadable(true, false);
      outputStreamWriter.close();
    } catch (Exception e3) {
      e = e3;
      outputStreamWriter2 = outputStreamWriter;
      Log.w(this.TAG, e);
      if (outputStreamWriter2 != null) {
        outputStreamWriter2.close();
      }
    } catch (Throwable th2) {
      th = th2;
      outputStreamWriter2 = outputStreamWriter;
      if (outputStreamWriter2 != null) {
        try {
          outputStreamWriter2.close();
        } catch (IOException e4) {
          Log.w(this.TAG, e4);
        }
      }
      throw th;
    }
  }
}
