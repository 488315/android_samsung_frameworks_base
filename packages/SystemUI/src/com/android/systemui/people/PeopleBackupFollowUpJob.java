package com.android.systemui.people;

import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.app.people.IPeopleManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.ServiceManager;
import android.preference.PreferenceManager;
import android.util.Log;
import com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0;
import com.android.systemui.people.widget.PeopleBackupHelper;
import com.android.systemui.people.widget.PeopleTileKey;
import java.time.Duration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* loaded from: classes2.dex */
public class PeopleBackupFollowUpJob extends JobService {
    public static final /* synthetic */ int $r8$clinit = 0;
    public Context mContext;
    public IPeopleManager mIPeopleManager;
    public JobScheduler mJobScheduler;
    public final Object mLock = new Object();
    public PackageManager mPackageManager;
    public static final long JOB_PERIODIC_DURATION = Duration.ofHours(6).toMillis();
    public static final long CLEAN_UP_STORAGE_AFTER_DURATION = Duration.ofHours(48).toMillis();

    public final void cancelJobAndClearRemainingWidgets(Map map, SharedPreferences.Editor editor, SharedPreferences sharedPreferences) throws NumberFormatException {
        for (Map.Entry entry : ((HashMap) map).entrySet()) {
            PeopleTileKey peopleTileKeyFromString = PeopleTileKey.fromString((String) entry.getKey());
            if (PeopleTileKey.isValid(peopleTileKeyFromString)) {
                try {
                    Iterator it = ((Set) entry.getValue()).iterator();
                    while (it.hasNext()) {
                        try {
                            int i = Integer.parseInt((String) it.next());
                            PeopleSpaceUtils.removeSharedPreferencesStorageForTile(this.mContext, peopleTileKeyFromString, i, sharedPreferences.getString(String.valueOf(i), ""));
                        } catch (NumberFormatException e) {
                            Log.e("PeopleBackupFollowUpJob", "Malformed widget id in follow-up file: " + e);
                        }
                    }
                } catch (Exception e2) {
                    EmergencyButton$$ExternalSyntheticOutline0.m("Malformed widget ids in follow-up file: ", e2, "PeopleBackupFollowUpJob");
                }
            } else {
                Log.e("PeopleBackupFollowUpJob", "Malformed peopleTileKey in follow-up file: " + ((String) entry.getKey()));
            }
        }
        editor.clear();
        this.mJobScheduler.cancel(74823873);
    }

    @Override // android.app.Service
    public final void onCreate() {
        super.onCreate();
        this.mContext = getApplicationContext();
        this.mPackageManager = getApplicationContext().getPackageManager();
        this.mIPeopleManager = IPeopleManager.Stub.asInterface(ServiceManager.getService("people"));
        this.mJobScheduler = (JobScheduler) this.mContext.getSystemService(JobScheduler.class);
    }

    @Override // android.app.job.JobService
    public final boolean onStartJob(JobParameters jobParameters) {
        synchronized (this.mLock) {
            try {
                SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editorEdit = defaultSharedPreferences.edit();
                SharedPreferences sharedPreferences = getSharedPreferences("shared_follow_up", 0);
                SharedPreferences.Editor editorEdit2 = sharedPreferences.edit();
                Map mapProcessFollowUpFile = processFollowUpFile(sharedPreferences, editorEdit2);
                long j = jobParameters.getExtras().getLong("start_date");
                long jCurrentTimeMillis = System.currentTimeMillis();
                if (((HashMap) mapProcessFollowUpFile).isEmpty() || jCurrentTimeMillis - j > CLEAN_UP_STORAGE_AFTER_DURATION) {
                    cancelJobAndClearRemainingWidgets(mapProcessFollowUpFile, editorEdit2, defaultSharedPreferences);
                }
                editorEdit.apply();
                editorEdit2.apply();
            } catch (Throwable th) {
                throw th;
            }
        }
        PeopleBackupHelper.updateWidgets(this.mContext);
        return false;
    }

    @Override // android.app.job.JobService
    public final boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

    public final Map processFollowUpFile(SharedPreferences sharedPreferences, SharedPreferences.Editor editor) throws NumberFormatException {
        HashMap map = new HashMap();
        for (Map.Entry<String, ?> entry : sharedPreferences.getAll().entrySet()) {
            String key = entry.getKey();
            if (PeopleBackupHelper.isReadyForRestore(this.mIPeopleManager, this.mPackageManager, PeopleTileKey.fromString(key))) {
                editor.remove(key);
            } else {
                try {
                    map.put(entry.getKey(), (Set) entry.getValue());
                } catch (Exception unused) {
                    Log.e("PeopleBackupFollowUpJob", "Malformed entry value: " + entry.getValue());
                }
            }
        }
        return map;
    }

    public void setManagers(Context context, PackageManager packageManager, IPeopleManager iPeopleManager, JobScheduler jobScheduler) {
        this.mContext = context;
        this.mPackageManager = packageManager;
        this.mIPeopleManager = iPeopleManager;
        this.mJobScheduler = jobScheduler;
    }
}
