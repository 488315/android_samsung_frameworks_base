package com.android.systemui.people;

import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.app.people.IPeopleManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.ServiceManager;
import android.util.Log;
import com.android.keyguard.EmergencyButton$$ExternalSyntheticOutline0;
import com.android.systemui.people.widget.PeopleBackupHelper;
import com.android.systemui.people.widget.PeopleTileKey;
import java.time.Duration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class PeopleBackupFollowUpJob extends JobService {
    public static final /* synthetic */ int $r8$clinit = 0;
    public Context mContext;
    public IPeopleManager mIPeopleManager;
    public JobScheduler mJobScheduler;
    public final Object mLock = new Object();
    public PackageManager mPackageManager;
    public static final long JOB_PERIODIC_DURATION = Duration.ofHours(6).toMillis();
    public static final long CLEAN_UP_STORAGE_AFTER_DURATION = Duration.ofHours(48).toMillis();

    public final void cancelJobAndClearRemainingWidgets(Map map, SharedPreferences.Editor editor, SharedPreferences sharedPreferences) {
        for (Map.Entry entry : ((HashMap) map).entrySet()) {
            PeopleTileKey fromString = PeopleTileKey.fromString((String) entry.getKey());
            if (PeopleTileKey.isValid(fromString)) {
                try {
                    Iterator it = ((Set) entry.getValue()).iterator();
                    while (it.hasNext()) {
                        try {
                            int parseInt = Integer.parseInt((String) it.next());
                            PeopleSpaceUtils.removeSharedPreferencesStorageForTile(this.mContext, fromString, parseInt, sharedPreferences.getString(String.valueOf(parseInt), ""));
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

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0039, code lost:
    
        if ((r8 - r6) > com.android.systemui.people.PeopleBackupFollowUpJob.CLEAN_UP_STORAGE_AFTER_DURATION) goto L9;
     */
    @Override // android.app.job.JobService
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean onStartJob(android.app.job.JobParameters r11) {
        /*
            r10 = this;
            java.lang.Object r0 = r10.mLock
            monitor-enter(r0)
            android.content.SharedPreferences r1 = android.preference.PreferenceManager.getDefaultSharedPreferences(r10)     // Catch: java.lang.Throwable -> L3f
            android.content.SharedPreferences$Editor r2 = r1.edit()     // Catch: java.lang.Throwable -> L3f
            java.lang.String r3 = "shared_follow_up"
            r4 = 0
            android.content.SharedPreferences r3 = r10.getSharedPreferences(r3, r4)     // Catch: java.lang.Throwable -> L3f
            android.content.SharedPreferences$Editor r5 = r3.edit()     // Catch: java.lang.Throwable -> L3f
            java.util.Map r3 = r10.processFollowUpFile(r3, r5)     // Catch: java.lang.Throwable -> L3f
            android.os.PersistableBundle r11 = r11.getExtras()     // Catch: java.lang.Throwable -> L3f
            java.lang.String r6 = "start_date"
            long r6 = r11.getLong(r6)     // Catch: java.lang.Throwable -> L3f
            long r8 = java.lang.System.currentTimeMillis()     // Catch: java.lang.Throwable -> L3f
            r11 = r3
            java.util.HashMap r11 = (java.util.HashMap) r11     // Catch: java.lang.Throwable -> L3f
            boolean r11 = r11.isEmpty()     // Catch: java.lang.Throwable -> L3f
            if (r11 == 0) goto L34
            goto L3b
        L34:
            long r8 = r8 - r6
            long r6 = com.android.systemui.people.PeopleBackupFollowUpJob.CLEAN_UP_STORAGE_AFTER_DURATION     // Catch: java.lang.Throwable -> L3f
            int r11 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1))
            if (r11 <= 0) goto L41
        L3b:
            r10.cancelJobAndClearRemainingWidgets(r3, r5, r1)     // Catch: java.lang.Throwable -> L3f
            goto L41
        L3f:
            r10 = move-exception
            goto L4e
        L41:
            r2.apply()     // Catch: java.lang.Throwable -> L3f
            r5.apply()     // Catch: java.lang.Throwable -> L3f
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L3f
            android.content.Context r10 = r10.mContext
            com.android.systemui.people.widget.PeopleBackupHelper.updateWidgets(r10)
            return r4
        L4e:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L3f
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.people.PeopleBackupFollowUpJob.onStartJob(android.app.job.JobParameters):boolean");
    }

    @Override // android.app.job.JobService
    public final boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

    public final Map processFollowUpFile(SharedPreferences sharedPreferences, SharedPreferences.Editor editor) {
        HashMap hashMap = new HashMap();
        for (Map.Entry<String, ?> entry : sharedPreferences.getAll().entrySet()) {
            String key = entry.getKey();
            if (PeopleBackupHelper.isReadyForRestore(this.mIPeopleManager, this.mPackageManager, PeopleTileKey.fromString(key))) {
                editor.remove(key);
            } else {
                try {
                    hashMap.put(entry.getKey(), (Set) entry.getValue());
                } catch (Exception unused) {
                    Log.e("PeopleBackupFollowUpJob", "Malformed entry value: " + entry.getValue());
                }
            }
        }
        return hashMap;
    }

    public void setManagers(Context context, PackageManager packageManager, IPeopleManager iPeopleManager, JobScheduler jobScheduler) {
        this.mContext = context;
        this.mPackageManager = packageManager;
        this.mIPeopleManager = iPeopleManager;
        this.mJobScheduler = jobScheduler;
    }
}
