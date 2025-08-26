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
import com.android.systemui.qs.panels.domain.backup.QSPreferencesBackupHelper;
import com.android.systemui.settings.UserFileManagerImpl;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.io.FileAlreadyExistsException;
import kotlin.io.FileSystemException;
import kotlin.io.NoSuchFileException;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public class BackupHelper extends BackupAgentHelper {
    public static final Companion Companion = new Companion(null);
    public static final Object controlsDataLock = new Object();

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class NoOverwriteFileBackupHelper extends FileBackupHelper {
        public final Context context;
        public final Map fileNamesAndPostProcess;
        public final Object lock;

        /* JADX WARN: Illegal instructions before constructor call */
        public NoOverwriteFileBackupHelper(Object obj, Context context, Map<String, ? extends Function0> map) {
            String[] strArr = (String[]) map.keySet().toArray(new String[0]);
            super(context, (String[]) Arrays.copyOf(strArr, strArr.length));
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
            SecNotificationBlockManager$$ExternalSyntheticOutline0.m(this.context.getUserId(), "Starting restore for ", backupDataInputStream.getKey(), " for user ", "BackupHelper");
            if (Environment.buildPath(this.context.getFilesDir(), new String[]{backupDataInputStream.getKey()}).exists()) {
                Log.w("BackupHelper", "File " + backupDataInputStream.getKey() + " already exists. Skipping restore.");
                return;
            }
            synchronized (this.lock) {
                String str = "File restore: " + backupDataInputStream.getKey();
                boolean zIsEnabled = Trace.isEnabled();
                if (zIsEnabled) {
                    TraceUtilsKt.beginSlice(str);
                }
                try {
                    super.restoreEntity(backupDataInputStream);
                    Unit unit = Unit.INSTANCE;
                    if (zIsEnabled) {
                        TraceUtilsKt.endSlice();
                    }
                    Log.d("BackupHelper", "Finishing restore for " + backupDataInputStream.getKey() + " for user " + this.context.getUserId() + ". Starting postProcess.");
                    String key = backupDataInputStream.getKey();
                    StringBuilder sb = new StringBuilder("Postprocess: ");
                    sb.append(key);
                    String string = sb.toString();
                    zIsEnabled = Trace.isEnabled();
                    if (zIsEnabled) {
                        TraceUtilsKt.beginSlice(string);
                    }
                    try {
                        Function0 function0 = (Function0) this.fileNamesAndPostProcess.get(backupDataInputStream.getKey());
                        if (function0 != null) {
                            function0.invoke();
                        }
                        Log.d("BackupHelper", "Finishing postprocess for " + backupDataInputStream.getKey() + " for user " + this.context.getUserId() + ".");
                    } finally {
                        if (zIsEnabled) {
                            TraceUtilsKt.endSlice();
                        }
                    }
                } finally {
                    if (zIsEnabled) {
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
        Pair pair = new Pair(UserFileManagerImpl.Companion.createFile(identifier, "controls_favorites.xml").getPath(), new Function0() { // from class: com.android.systemui.backup.BackupHelperKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() throws IOException {
                BackupHelper backupHelper = this;
                UserFileManagerImpl.Companion.getClass();
                int i = identifier;
                File fileCreateFile = UserFileManagerImpl.Companion.createFile(i, "controls_favorites.xml");
                if (fileCreateFile.exists()) {
                    File fileCreateFile2 = UserFileManagerImpl.Companion.createFile(i, "aux_controls_favorites.xml");
                    if (!fileCreateFile.exists()) {
                        throw new NoSuchFileException(fileCreateFile, null, "The source file doesn't exist.", 2, null);
                    }
                    if (fileCreateFile2.exists()) {
                        throw new FileAlreadyExistsException(fileCreateFile, fileCreateFile2, "The destination file already exists.");
                    }
                    if (!fileCreateFile.isDirectory()) {
                        File parentFile = fileCreateFile2.getParentFile();
                        if (parentFile != null) {
                            parentFile.mkdirs();
                        }
                        FileInputStream fileInputStream = new FileInputStream(fileCreateFile);
                        try {
                            FileOutputStream fileOutputStream = new FileOutputStream(fileCreateFile2);
                            try {
                                byte[] bArr = new byte[8192];
                                for (int i2 = fileInputStream.read(bArr); i2 >= 0; i2 = fileInputStream.read(bArr)) {
                                    fileOutputStream.write(bArr, 0, i2);
                                }
                                fileOutputStream.close();
                                fileInputStream.close();
                            } finally {
                            }
                        } finally {
                        }
                    } else if (!fileCreateFile2.mkdirs()) {
                        throw new FileSystemException(fileCreateFile, fileCreateFile2, "Failed to create target directory.");
                    }
                    JobScheduler jobScheduler = (JobScheduler) backupHelper.getSystemService(JobScheduler.class);
                    if (jobScheduler != null) {
                        AuxiliaryPersistenceWrapper.DeletionJobService.Companion.getClass();
                        int userId = backupHelper.getUserId() + AuxiliaryPersistenceWrapper.DeletionJobService.DELETE_FILE_JOB_ID;
                        ComponentName componentName = new ComponentName(backupHelper, (Class<?>) AuxiliaryPersistenceWrapper.DeletionJobService.class);
                        PersistableBundle persistableBundle = new PersistableBundle();
                        persistableBundle.putInt(AuxiliaryPersistenceWrapper.DeletionJobService.USER, i);
                        jobScheduler.schedule(new JobInfo.Builder(userId, componentName).setMinimumLatency(AuxiliaryPersistenceWrapper.DeletionJobService.WEEK_IN_MILLIS).setPersisted(true).setExtras(persistableBundle).build());
                    }
                }
                return Unit.INSTANCE;
            }
        });
        addHelper("systemui.files_no_overwrite", new NoOverwriteFileBackupHelper(controlsDataLock, this, Collections.singletonMap(pair.getFirst(), pair.getSecond())));
        int i = PeopleBackupHelper.$r8$clinit;
        List listSingletonList = Collections.singletonList("shared_backup");
        listSingletonList.getClass();
        addHelper("systemui.people.shared_preferences", new PeopleBackupHelper(this, userHandle, (String[]) listSingletonList.toArray(new String[0])));
        addHelper("systemui.keyguard.quickaffordance.shared_preferences", new KeyguardQuickAffordanceBackupHelper(this, userHandle.getIdentifier()));
        addHelper("systemui.qs.shared_preferences", new QSPreferencesBackupHelper(this, userHandle.getIdentifier()));
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
