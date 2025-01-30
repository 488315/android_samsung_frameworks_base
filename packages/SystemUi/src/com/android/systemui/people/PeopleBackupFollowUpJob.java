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

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
                    EmergencyButton$$ExternalSyntheticOutline0.m58m("Malformed widget ids in follow-up file: ", e2, "PeopleBackupFollowUpJob");
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
            SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor edit = defaultSharedPreferences.edit();
            SharedPreferences sharedPreferences = getSharedPreferences("shared_follow_up", 0);
            SharedPreferences.Editor edit2 = sharedPreferences.edit();
            Map processFollowUpFile = processFollowUpFile(sharedPreferences, edit2);
            long j = jobParameters.getExtras().getLong("start_date");
            long currentTimeMillis = System.currentTimeMillis();
            boolean z = true;
            if (!((HashMap) processFollowUpFile).isEmpty()) {
                if (!(currentTimeMillis - j > CLEAN_UP_STORAGE_AFTER_DURATION)) {
                    z = false;
                }
            }
            if (z) {
                cancelJobAndClearRemainingWidgets(processFollowUpFile, edit2, defaultSharedPreferences);
            }
            edit.apply();
            edit2.apply();
        }
        PeopleBackupHelper.updateWidgets(this.mContext);
        return false;
    }

    @Override // android.app.job.JobService
    public final boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

    public final Map processFollowUpFile(SharedPreferences sharedPreferences, SharedPreferences.Editor editor) {
        boolean z;
        HashMap hashMap = new HashMap();
        for (Map.Entry<String, ?> entry : sharedPreferences.getAll().entrySet()) {
            String key = entry.getKey();
            PeopleTileKey fromString = PeopleTileKey.fromString(key);
            IPeopleManager iPeopleManager = this.mIPeopleManager;
            PackageManager packageManager = this.mPackageManager;
            int i = PeopleBackupHelper.$r8$clinit;
            if (PeopleTileKey.isValid(fromString)) {
                try {
                    packageManager.getPackageInfoAsUser(fromString.mPackageName, 0, fromString.mUserId);
                    z = iPeopleManager.isConversation(fromString.mPackageName, fromString.mUserId, fromString.mShortcutId);
                } catch (PackageManager.NameNotFoundException | Exception unused) {
                    z = false;
                }
            } else {
                z = true;
            }
            if (z) {
                editor.remove(key);
            } else {
                try {
                    hashMap.put(entry.getKey(), (Set) entry.getValue());
                } catch (Exception unused2) {
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
