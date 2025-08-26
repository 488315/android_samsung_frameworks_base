package android.content.om.wallpapertheme;

import android.content.om.WallpaperThemeConstants;
import android.os.FileUtils;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class ThemePalette {
    private String TAG = "SWT_ThemePalette";
    public boolean mIsGray = false;
    List<Integer> mMonetPaletteGG;
    List<Integer> mMonetPaletteSS;

    public int getMonetColorSS(int i, int i2) {
        return getMonetColorSS((i * 13) + i2);
    }

    public int getMonetColorSS(int i) {
        List<Integer> list = this.mMonetPaletteSS;
        if (list == null) {
            return 0;
        }
        return list.get(i).intValue();
    }

    public int getMonetColorGG(int i, int i2) {
        return getMonetColorGG((i * 13) + i2);
    }

    public int getMonetColorGG(int i) {
        List<Integer> list = this.mMonetPaletteGG;
        if (list == null) {
            return 0;
        }
        return list.get(i).intValue();
    }

    public void setPalette(List<Integer> list, List<Integer> list2, boolean z) {
        this.mMonetPaletteSS = list;
        this.mMonetPaletteGG = list2;
        this.mIsGray = z;
        Log.i(this.TAG, "palette updated");
    }

    public List<Integer> getPaletteSS() {
        return this.mMonetPaletteSS;
    }

    public List<Integer> getPaletteGG() {
        return this.mMonetPaletteGG;
    }

    public void writeLastPalette() throws IOException {
        File file = new File(WallpaperThemeConstants.RESID_TABLE_PATH);
        if (!file.exists()) {
            try {
                file.mkdir();
                FileUtils.setPermissions(file, 511, -1, -1);
            } catch (Exception e) {
                Log.w(this.TAG, "Failed to create wallpapertheme/ directory", e);
                return;
            }
        }
        File file2 = new File(file, WallpaperThemeConstants.LAST_PALETTE_FILE);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            try {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                try {
                    Iterator<Integer> it = this.mMonetPaletteSS.iterator();
                    while (it.hasNext()) {
                        outputStreamWriter.write(it.next().intValue() + System.lineSeparator());
                    }
                    Iterator<Integer> it2 = this.mMonetPaletteGG.iterator();
                    while (it2.hasNext()) {
                        outputStreamWriter.write(it2.next().intValue() + System.lineSeparator());
                    }
                    outputStreamWriter.write(this.mIsGray ? "1" : "0");
                    file2.setReadable(true, false);
                    outputStreamWriter.close();
                    fileOutputStream.close();
                } finally {
                }
            } finally {
            }
        } catch (Exception e2) {
            Log.w(this.TAG, "Failed to write or set permissions for last palette", e2);
        }
    }
}
