package android.app.backup;

import android.app.QueuedWork;
import android.content.Context;
import android.os.ParcelFileDescriptor;

/* loaded from: classes.dex */
public class SharedPreferencesBackupHelper extends FileBackupHelperBase implements BackupHelper {
    private static final boolean DEBUG = false;
    private static final String TAG = "SharedPreferencesBackupHelper";
    private Context mContext;
    private String[] mPrefGroups;

    @Override // android.app.backup.FileBackupHelperBase, android.app.backup.BackupHelper
    public /* bridge */ /* synthetic */ void writeNewStateDescription(ParcelFileDescriptor parcelFileDescriptor) {
        super.writeNewStateDescription(parcelFileDescriptor);
    }

    public SharedPreferencesBackupHelper(Context context, String... strArr) {
        super(context);
        this.mContext = context;
        this.mPrefGroups = strArr;
    }

    @Override // android.app.backup.BackupHelper
    public void performBackup(ParcelFileDescriptor parcelFileDescriptor, BackupDataOutput backupDataOutput, ParcelFileDescriptor parcelFileDescriptor2) {
        Context context = this.mContext;
        QueuedWork.waitToFinish();
        String[] strArr = this.mPrefGroups;
        int length = strArr.length;
        String[] strArr2 = new String[length];
        for (int i = 0; i < length; i++) {
            strArr2[i] = context.getSharedPrefsFile(strArr[i]).getAbsolutePath();
        }
        performBackup_checked(parcelFileDescriptor, backupDataOutput, parcelFileDescriptor2, strArr2, strArr);
    }

    @Override // android.app.backup.BackupHelper
    public void restoreEntity(BackupDataInputStream backupDataInputStream) {
        Context context = this.mContext;
        String key = backupDataInputStream.getKey();
        if (isKeyInList(key, this.mPrefGroups)) {
            writeFile(context.getSharedPrefsFile(key).getAbsoluteFile(), backupDataInputStream);
        }
    }
}
