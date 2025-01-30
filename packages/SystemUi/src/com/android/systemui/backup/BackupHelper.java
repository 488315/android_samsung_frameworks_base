package com.android.systemui.backup;

import android.app.backup.BackupAgentHelper;
import android.app.backup.BackupDataInputStream;
import android.app.backup.BackupDataOutput;
import android.app.backup.FileBackupHelper;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.os.PersistableBundle;
import android.os.UserHandle;
import android.util.Log;
import com.android.systemui.controls.controller.AuxiliaryPersistenceWrapper;
import com.android.systemui.keyguard.domain.backup.KeyguardQuickAffordanceBackupHelper;
import com.android.systemui.people.widget.PeopleBackupHelper;
import com.android.systemui.settings.UserFileManagerImpl;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.p055io.CloseableKt;
import kotlin.p055io.FileAlreadyExistsException;
import kotlin.p055io.FileSystemException;
import kotlin.p055io.NoSuchFileException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class BackupHelper extends BackupAgentHelper {
    public static final Companion Companion = new Companion(null);
    public static final Object controlsDataLock = new Object();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class NoOverwriteFileBackupHelper extends FileBackupHelper {
        public final Context context;
        public final Map fileNamesAndPostProcess;
        public final Object lock;

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public NoOverwriteFileBackupHelper(Object obj, Context context, Map<String, ? extends Function0> map) {
            super(context, (String[]) Arrays.copyOf(r0, r0.length));
            String[] strArr = (String[]) map.keySet().toArray(new String[0]);
            this.lock = obj;
            this.context = context;
            this.fileNamesAndPostProcess = map;
        }

        @Override // android.app.backup.FileBackupHelper, android.app.backup.BackupHelper
        public final void performBackup(ParcelFileDescriptor parcelFileDescriptor, BackupDataOutput backupDataOutput, ParcelFileDescriptor parcelFileDescriptor2) {
            synchronized (this.lock) {
                super.performBackup(parcelFileDescriptor, backupDataOutput, parcelFileDescriptor2);
                Unit unit = Unit.INSTANCE;
            }
        }

        @Override // android.app.backup.FileBackupHelper, android.app.backup.BackupHelper
        public final void restoreEntity(BackupDataInputStream backupDataInputStream) {
            if (Environment.buildPath(this.context.getFilesDir(), new String[]{backupDataInputStream.getKey()}).exists()) {
                Log.w("BackupHelper", "File " + backupDataInputStream.getKey() + " already exists. Skipping restore.");
                return;
            }
            synchronized (this.lock) {
                super.restoreEntity(backupDataInputStream);
                Function0 function0 = (Function0) this.fileNamesAndPostProcess.get(backupDataInputStream.getKey());
                if (function0 != null) {
                    function0.invoke();
                    Unit unit = Unit.INSTANCE;
                }
            }
        }
    }

    public final void onCreate(UserHandle userHandle, int i) {
        super.onCreate();
        final int identifier = userHandle.getIdentifier();
        UserFileManagerImpl.Companion.getClass();
        addHelper("systemui.files_no_overwrite", new NoOverwriteFileBackupHelper(controlsDataLock, this, MapsKt__MapsJVMKt.mapOf(new Pair(UserFileManagerImpl.Companion.createFile(identifier, "controls_favorites.xml").getPath(), new Function0() { // from class: com.android.systemui.backup.BackupHelperKt$getPPControlsFile$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                UserFileManagerImpl.Companion companion = UserFileManagerImpl.Companion;
                int i2 = identifier;
                companion.getClass();
                File createFile = UserFileManagerImpl.Companion.createFile(i2, "controls_favorites.xml");
                if (createFile.exists()) {
                    File createFile2 = UserFileManagerImpl.Companion.createFile(identifier, "aux_controls_favorites.xml");
                    if (!createFile.exists()) {
                        throw new NoSuchFileException(createFile, null, "The source file doesn't exist.", 2, null);
                    }
                    if (createFile2.exists()) {
                        throw new FileAlreadyExistsException(createFile, createFile2, "The destination file already exists.");
                    }
                    if (!createFile.isDirectory()) {
                        File parentFile = createFile2.getParentFile();
                        if (parentFile != null) {
                            parentFile.mkdirs();
                        }
                        FileInputStream fileInputStream = new FileInputStream(createFile);
                        try {
                            FileOutputStream fileOutputStream = new FileOutputStream(createFile2);
                            try {
                                byte[] bArr = new byte[8192];
                                for (int read = fileInputStream.read(bArr); read >= 0; read = fileInputStream.read(bArr)) {
                                    fileOutputStream.write(bArr, 0, read);
                                }
                                CloseableKt.closeFinally(fileOutputStream, null);
                                CloseableKt.closeFinally(fileInputStream, null);
                            } finally {
                            }
                        } finally {
                        }
                    } else if (!createFile2.mkdirs()) {
                        throw new FileSystemException(createFile, createFile2, "Failed to create target directory.");
                    }
                    JobScheduler jobScheduler = (JobScheduler) this.getSystemService(JobScheduler.class);
                    if (jobScheduler != null) {
                        AuxiliaryPersistenceWrapper.DeletionJobService.Companion companion2 = AuxiliaryPersistenceWrapper.DeletionJobService.Companion;
                        Context context = this;
                        int i3 = identifier;
                        companion2.getClass();
                        int userId = context.getUserId() + AuxiliaryPersistenceWrapper.DeletionJobService.DELETE_FILE_JOB_ID;
                        ComponentName componentName = new ComponentName(context, (Class<?>) AuxiliaryPersistenceWrapper.DeletionJobService.class);
                        PersistableBundle persistableBundle = new PersistableBundle();
                        persistableBundle.putInt(AuxiliaryPersistenceWrapper.DeletionJobService.USER, i3);
                        jobScheduler.schedule(new JobInfo.Builder(userId, componentName).setMinimumLatency(AuxiliaryPersistenceWrapper.DeletionJobService.WEEK_IN_MILLIS).setPersisted(true).setExtras(persistableBundle).build());
                    }
                }
                return Unit.INSTANCE;
            }
        }))));
        int i2 = PeopleBackupHelper.$r8$clinit;
        addHelper("systemui.people.shared_preferences", new PeopleBackupHelper(this, userHandle, (String[]) Collections.singletonList("shared_backup").toArray(new String[0])));
        addHelper("systemui.keyguard.quickaffordance.shared_preferences", new KeyguardQuickAffordanceBackupHelper(this, userHandle.getIdentifier()));
    }

    @Override // android.app.backup.BackupAgent
    public final void onRestoreFinished() {
        super.onRestoreFinished();
        Intent intent = new Intent("com.android.systemui.backup.RESTORE_FINISHED");
        intent.setPackage(getPackageName());
        intent.putExtra("android.intent.extra.USER_ID", getUserId());
        intent.setFlags(VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
        sendBroadcastAsUser(intent, UserHandle.SYSTEM, "com.android.systemui.permission.SELF");
    }
}
