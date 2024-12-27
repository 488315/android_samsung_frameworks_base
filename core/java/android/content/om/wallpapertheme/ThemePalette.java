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

public class ThemePalette {
    private String TAG = "SWT_ThemePalette";
    public boolean mIsGray = false;
    List<Integer> mMonetPaletteGG;
    List<Integer> mMonetPaletteSS;

    public int getMonetColorSS(int typeIndex, int luminence) {
        int index = (typeIndex * 13) + luminence;
        return getMonetColorSS(index);
    }

    public int getMonetColorSS(int index) {
        if (this.mMonetPaletteSS == null) {
            return 0;
        }
        return this.mMonetPaletteSS.get(index).intValue();
    }

    public int getMonetColorGG(int typeIndex, int luminence) {
        int index = (typeIndex * 13) + luminence;
        return getMonetColorGG(index);
    }

    public int getMonetColorGG(int index) {
        if (this.mMonetPaletteGG == null) {
            return 0;
        }
        return this.mMonetPaletteGG.get(index).intValue();
    }

    public void setPalette(
            List<Integer> wallpaperColorSS, List<Integer> wallpaperColorGG, boolean isGray) {
        this.mMonetPaletteSS = wallpaperColorSS;
        this.mMonetPaletteGG = wallpaperColorGG;
        this.mIsGray = isGray;
        Log.i(this.TAG, "palette updated");
    }

    public List<Integer> getPaletteSS() {
        return this.mMonetPaletteSS;
    }

    public List<Integer> getPaletteGG() {
        return this.mMonetPaletteGG;
    }

    public void writeLastPalette() {
        OutputStreamWriter fos = null;
        try {
            try {
                try {
                    File dir = new File(WallpaperThemeConstants.RESID_TABLE_PATH);
                    if (!dir.exists()) {
                        dir.mkdir();
                        FileUtils.setPermissions(dir, 511, -1, -1);
                    }
                    File file = new File("/data/overlays/wallpapertheme/last_palette.txt");
                    fos = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
                    Iterator<Integer> it = this.mMonetPaletteSS.iterator();
                    while (it.hasNext()) {
                        int color = it.next().intValue();
                        fos.write(color + System.lineSeparator());
                    }
                    Iterator<Integer> it2 = this.mMonetPaletteGG.iterator();
                    while (it2.hasNext()) {
                        int color2 = it2.next().intValue();
                        fos.write(color2 + System.lineSeparator());
                    }
                    fos.write(this.mIsGray ? "1" : "0");
                    file.setReadable(true, false);
                    fos.close();
                } catch (Exception e) {
                    Log.w(this.TAG, e);
                    if (fos == null) {
                    } else {
                        fos.close();
                    }
                }
            } catch (Throwable th) {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e2) {
                        Log.w(this.TAG, e2);
                    }
                }
                throw th;
            }
        } catch (IOException e3) {
            Log.w(this.TAG, e3);
        }
    }
}
