package android.app.backup;

import android.app.WallpaperManager;
import android.content.Context;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.util.Slog;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/* loaded from: classes.dex */
public class WallpaperBackupHelper extends FileBackupHelperBase implements BackupHelper {
    private static final boolean DEBUG = false;
    private static final String STAGE_FILE = new File(Environment.getUserSystemDirectory(0), "wallpaper-tmp").getAbsolutePath();
    private static final String TAG = "WallpaperBackupHelper";
    public static final String WALLPAPER_IMAGE_KEY = "/data/data/com.android.settings/files/wallpaper";
    public static final String WALLPAPER_INFO_KEY = "/data/system/wallpaper_info.xml";
    private final String[] mKeys;
    private final WallpaperManager mWpm;

    @Override // android.app.backup.BackupHelper
    public void performBackup(ParcelFileDescriptor parcelFileDescriptor, BackupDataOutput backupDataOutput, ParcelFileDescriptor parcelFileDescriptor2) {
    }

    @Override // android.app.backup.FileBackupHelperBase, android.app.backup.BackupHelper
    public /* bridge */ /* synthetic */ void writeNewStateDescription(ParcelFileDescriptor parcelFileDescriptor) {
        super.writeNewStateDescription(parcelFileDescriptor);
    }

    public WallpaperBackupHelper(Context context, String[] strArr) {
        super(context);
        this.mContext = context;
        this.mKeys = strArr;
        this.mWpm = (WallpaperManager) context.getSystemService("wallpaper");
    }

    @Override // android.app.backup.BackupHelper
    public void restoreEntity(BackupDataInputStream backupDataInputStream) {
        if (this.mWpm == null) {
            Slog.w(TAG, "restoreEntity(): no wallpaper service");
            return;
        }
        String key = backupDataInputStream.getKey();
        if (isKeyInList(key, this.mKeys) && key.equals(WALLPAPER_IMAGE_KEY)) {
            File file = new File(STAGE_FILE);
            try {
                if (writeFile(file, backupDataInputStream)) {
                    try {
                        FileInputStream fileInputStream = new FileInputStream(file);
                        try {
                            this.mWpm.setStream(fileInputStream);
                            fileInputStream.close();
                        } catch (Throwable th) {
                            try {
                                fileInputStream.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                            throw th;
                        }
                    } catch (IOException e) {
                        Slog.e(TAG, "Unable to set restored wallpaper: " + e.getMessage());
                    }
                } else {
                    Slog.e(TAG, "Unable to save restored wallpaper");
                }
            } finally {
                file.delete();
            }
        }
    }
}
