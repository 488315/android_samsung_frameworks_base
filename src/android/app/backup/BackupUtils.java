package android.app.backup;

import android.app.backup.FullBackup;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

/* loaded from: classes.dex */
public class BackupUtils {
    private BackupUtils() {
    }

    public static boolean isFileSpecifiedInPathList(File file, Collection<FullBackup.BackupScheme.PathWithRequiredFlags> collection) throws IOException {
        Iterator<FullBackup.BackupScheme.PathWithRequiredFlags> it = collection.iterator();
        while (it.hasNext()) {
            String path = it.next().getPath();
            File file2 = new File(path);
            if (file2.isDirectory()) {
                if (file.isDirectory()) {
                    if (file.equals(file2)) {
                        return true;
                    }
                } else if (file.toPath().startsWith(path)) {
                    return true;
                }
            } else if (file.equals(file2)) {
                return true;
            }
        }
        return false;
    }
}
