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
import android.os.Trace;
import android.os.UserHandle;
import android.util.Log;
import com.android.app.tracing.TraceUtilsKt;
import com.android.settingslib.SecNotificationBlockManager$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.communal.data.backup.CommunalBackupHelper;
import com.android.systemui.communal.data.backup.CommunalBackupUtils;
import com.android.systemui.communal.domain.backup.CommunalPrefsBackupHelper;
import com.android.systemui.controls.controller.AuxiliaryPersistenceWrapper;
import com.android.systemui.keyguard.domain.backup.KeyguardQuickAffordanceBackupHelper;
import com.android.systemui.people.widget.PeopleBackupHelper;
import com.android.systemui.settings.UserFileManagerImpl;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.io.CloseableKt;
import kotlin.io.FileAlreadyExistsException;
import kotlin.io.FileSystemException;
import kotlin.io.NoSuchFileException;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public class BackupHelper extends BackupAgentHelper {
    public static final Companion Companion = new Companion(null);
    public static final Object controlsDataLock = new Object();

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class NoOverwriteFileBackupHelper extends FileBackupHelper {
        public final Context context;
        public final Map fileNamesAndPostProcess;
        public final Object lock;

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public NoOverwriteFileBackupHelper(java.lang.Object r3, android.content.Context r4, java.util.Map<java.lang.String, ? extends kotlin.jvm.functions.Function0> r5) {
            /*
                r2 = this;
                java.util.Set r0 = r5.keySet()
                java.util.Collection r0 = (java.util.Collection) r0
                r1 = 0
                java.lang.String[] r1 = new java.lang.String[r1]
                java.lang.Object[] r0 = r0.toArray(r1)
                java.lang.String[] r0 = (java.lang.String[]) r0
                int r1 = r0.length
                java.lang.Object[] r0 = java.util.Arrays.copyOf(r0, r1)
                java.lang.String[] r0 = (java.lang.String[]) r0
                r2.<init>(r4, r0)
                r2.lock = r3
                r2.context = r4
                r2.fileNamesAndPostProcess = r5
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.backup.BackupHelper.NoOverwriteFileBackupHelper.<init>(java.lang.Object, android.content.Context, java.util.Map):void");
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
            SecNotificationBlockManager$$ExternalSyntheticOutline0.m(this.context.getUserId(), "Starting restore for ", backupDataInputStream.getKey(), " for user ", "BackupHelper");
            if (Environment.buildPath(this.context.getFilesDir(), new String[]{backupDataInputStream.getKey()}).exists()) {
                Log.w("BackupHelper", "File " + backupDataInputStream.getKey() + " already exists. Skipping restore.");
                return;
            }
            synchronized (this.lock) {
                String str = "File restore: " + backupDataInputStream.getKey();
                boolean isEnabled = Trace.isEnabled();
                if (isEnabled) {
                    TraceUtilsKt.beginSlice(str);
                }
                try {
                    super.restoreEntity(backupDataInputStream);
                    Unit unit = Unit.INSTANCE;
                    if (isEnabled) {
                        TraceUtilsKt.endSlice();
                    }
                    Log.d("BackupHelper", "Finishing restore for " + backupDataInputStream.getKey() + " for user " + this.context.getUserId() + ". Starting postProcess.");
                    String key = backupDataInputStream.getKey();
                    StringBuilder sb = new StringBuilder("Postprocess: ");
                    sb.append(key);
                    String sb2 = sb.toString();
                    isEnabled = Trace.isEnabled();
                    if (isEnabled) {
                        TraceUtilsKt.beginSlice(sb2);
                    }
                    try {
                        Function0 function0 = (Function0) this.fileNamesAndPostProcess.get(backupDataInputStream.getKey());
                        if (function0 != null) {
                            function0.invoke();
                        }
                        Log.d("BackupHelper", "Finishing postprocess for " + backupDataInputStream.getKey() + " for user " + this.context.getUserId() + ".");
                    } finally {
                        if (isEnabled) {
                            TraceUtilsKt.endSlice();
                        }
                    }
                } finally {
                    if (isEnabled) {
                        TraceUtilsKt.endSlice();
                    }
                }
            }
        }
    }

    public final void onCreate(UserHandle userHandle) {
        super.onCreate(userHandle);
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
                int i = identifier;
                companion.getClass();
                File createFile = UserFileManagerImpl.Companion.createFile(i, "controls_favorites.xml");
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
                        int i2 = identifier;
                        companion2.getClass();
                        int userId = context.getUserId() + AuxiliaryPersistenceWrapper.DeletionJobService.DELETE_FILE_JOB_ID;
                        ComponentName componentName = new ComponentName(context, (Class<?>) AuxiliaryPersistenceWrapper.DeletionJobService.class);
                        PersistableBundle persistableBundle = new PersistableBundle();
                        persistableBundle.putInt(AuxiliaryPersistenceWrapper.DeletionJobService.USER, i2);
                        jobScheduler.schedule(new JobInfo.Builder(userId, componentName).setMinimumLatency(AuxiliaryPersistenceWrapper.DeletionJobService.WEEK_IN_MILLIS).setPersisted(true).setExtras(persistableBundle).build());
                    }
                }
                return Unit.INSTANCE;
            }
        }))));
        int i = PeopleBackupHelper.$r8$clinit;
        List singletonList = Collections.singletonList("shared_backup");
        Intrinsics.checkNotNull(singletonList);
        addHelper("systemui.people.shared_preferences", new PeopleBackupHelper(this, userHandle, (String[]) singletonList.toArray(new String[0])));
        addHelper("systemui.keyguard.quickaffordance.shared_preferences", new KeyguardQuickAffordanceBackupHelper(this, userHandle.getIdentifier()));
        if (getResources().getBoolean(R.bool.config_communalServiceEnabled)) {
            addHelper("systemui.communal.shared_preferences", new CommunalPrefsBackupHelper(this, userHandle.getIdentifier()));
            addHelper("systemui.communal_state", new CommunalBackupHelper(userHandle, new CommunalBackupUtils(this)));
        }
    }

    @Override // android.app.backup.BackupAgent
    public final void onRestoreFinished() {
        super.onRestoreFinished();
        Intent intent = new Intent("com.android.systemui.backup.RESTORE_FINISHED");
        intent.setPackage(getPackageName());
        intent.putExtra("android.intent.extra.USER_ID", getUserId());
        intent.setFlags(1073741824);
        sendBroadcastAsUser(intent, UserHandle.SYSTEM, "com.android.systemui.permission.SELF");
    }
}
