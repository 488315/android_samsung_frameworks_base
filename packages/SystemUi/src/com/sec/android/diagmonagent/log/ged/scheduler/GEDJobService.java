package com.sec.android.diagmonagent.log.ged.scheduler;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import com.samsung.android.knox.restriction.PhoneRestrictionPolicy;
import com.sec.android.diagmonagent.common.logger.AppLog;
import com.sec.android.diagmonagent.common.logger.AppLogData;
import com.sec.android.diagmonagent.log.ged.p050db.GEDDatabase;
import com.sec.android.diagmonagent.log.ged.p050db.dao.EventDao;
import com.sec.android.diagmonagent.log.ged.p050db.dao.ResultDao;
import com.sec.android.diagmonagent.log.ged.p050db.dao.ServiceDao;
import com.sec.android.diagmonagent.log.ged.p050db.model.Event;
import com.sec.android.diagmonagent.log.ged.p050db.model.Result;
import com.sec.android.diagmonagent.log.ged.p050db.model.ServiceInfo;
import com.sec.android.diagmonagent.log.ged.servreinterface.controller.DiagmonApiManager;
import com.sec.android.diagmonagent.log.ged.servreinterface.model.client.DiagmonClient;
import com.sec.android.diagmonagent.log.ged.servreinterface.model.response.PolicyVersionResponse;
import com.sec.android.diagmonagent.log.ged.servreinterface.model.response.Response;
import com.sec.android.diagmonagent.log.ged.util.DeviceUtils;
import com.sec.android.diagmonagent.log.ged.util.PreferenceUtils;
import com.sec.android.diagmonagent.log.ged.util.RestUtils;
import com.sec.ims.IMSParameter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class GEDJobService extends JobService {
    public AsyncTaskC47991 serverTask = null;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class ServerTask extends AsyncTask {
        public ServerTask() {
        }

        @Override // android.os.AsyncTask
        public /* bridge */ /* synthetic */ Object doInBackground(Object[] objArr) {
            return doInBackground();
        }

        public void onPostExecute() {
        }

        /* JADX WARN: Removed duplicated region for block: B:30:0x0140  */
        /* JADX WARN: Removed duplicated region for block: B:81:0x0392  */
        /* JADX WARN: Removed duplicated region for block: B:82:0x039a  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public Boolean doInBackground() {
            boolean z;
            Cursor query;
            NetworkCapabilities networkCapabilities;
            boolean z2;
            ServiceDao serviceDao = new ServiceDao(GEDDatabase.get(GEDJobService.this.getApplicationContext()).context);
            ServiceInfo serviceInfo = serviceDao.getServiceInfo();
            if (serviceInfo == null) {
                Log.w(DeviceUtils.TAG, "Start jobService but serviceInfo is null");
                return Boolean.TRUE;
            }
            Context applicationContext = GEDJobService.this.getApplicationContext();
            String str = serviceInfo.serviceId;
            try {
                AppLog.mContext = applicationContext;
                AppLog.mServiceId = str;
                if (AppLog.sInstance == null) {
                    AppLog.sInstance = new AppLogData(applicationContext);
                    if (!TextUtils.isEmpty(AppLog.mServiceId)) {
                        AppLog.SERVICE_ID_TAG = AppLog.mServiceId;
                    }
                }
            } catch (Exception e) {
                Log.e("DIAGMON_SDK", e.getMessage());
            }
            if (TextUtils.isEmpty(PreferenceUtils.getDiagmonPreference(GEDJobService.this.getApplicationContext(), "JWT_TOKEN", ""))) {
                DiagmonApiManager diagmonApiManager = DiagmonApiManager.getInstance();
                Context applicationContext2 = GEDJobService.this.getApplicationContext();
                diagmonApiManager.getClass();
                DiagmonApiManager.refreshToken(applicationContext2);
            }
            String str2 = serviceInfo.serviceId;
            Context applicationContext3 = GEDJobService.this.getApplicationContext();
            int i = GEDScheduler.$r8$clinit;
            boolean z3 = true;
            if (System.currentTimeMillis() >= TimeUnit.DAYS.toMillis((long) Integer.parseInt(PreferenceUtils.getDiagmonPreference(applicationContext3, "pollingInterval", "1"))) + applicationContext3.getSharedPreferences("DIAGMON_PREFERENCE", 0).getLong("lastPDUpdatedTime", 0L)) {
                DiagmonApiManager diagmonApiManager2 = DiagmonApiManager.getInstance();
                Context applicationContext4 = GEDJobService.this.getApplicationContext();
                diagmonApiManager2.getClass();
                StringBuilder sb = new StringBuilder("https://diagmon-policy.samsungdm.com");
                String str3 = RestUtils.DEVICE_KEY;
                sb.append(PreferenceUtils.getDiagmonPreference(applicationContext4, "version_info_url", "/policy/version.json"));
                Response execute = new DiagmonClient(sb.toString(), "GET").execute();
                if (execute == null) {
                    AppLog.m272w("Policy version response is null");
                } else if (execute.code == 200) {
                    AppLog.m271i("succeed to connect to get policy version");
                    AppLog.m269d(execute.body);
                    String str4 = execute.body;
                    PolicyVersionResponse policyVersionResponse = new PolicyVersionResponse();
                    try {
                        JSONObject jSONObject = new JSONObject(str4);
                        policyVersionResponse.url = jSONObject.getString("url");
                        policyVersionResponse.latestDefault = jSONObject.getString("latestDefault");
                    } catch (JSONException unused) {
                        Log.e(DeviceUtils.TAG, "JSONException occurred while parsing policy version info");
                    }
                    PreferenceUtils.setDiagmonPreference(applicationContext4, "version_info_url", policyVersionResponse.url);
                    PreferenceUtils.setDiagmonPreference(applicationContext4, "needed_version", policyVersionResponse.latestDefault);
                    z2 = true;
                    if (z2) {
                        DiagmonApiManager diagmonApiManager3 = DiagmonApiManager.getInstance();
                        Context applicationContext5 = GEDJobService.this.getApplicationContext();
                        diagmonApiManager3.getClass();
                        DiagmonApiManager.refreshPolicy(applicationContext5, str2, 0);
                    }
                    Context applicationContext6 = GEDJobService.this.getApplicationContext();
                    long currentTimeMillis = System.currentTimeMillis();
                    SharedPreferences.Editor edit = applicationContext6.getSharedPreferences("DIAGMON_PREFERENCE", 0).edit();
                    edit.putLong("lastPDUpdatedTime", currentTimeMillis);
                    edit.apply();
                } else {
                    AppLog.m272w("Failed to connect to get policy version : " + execute.code);
                }
                z2 = false;
                if (z2) {
                }
                Context applicationContext62 = GEDJobService.this.getApplicationContext();
                long currentTimeMillis2 = System.currentTimeMillis();
                SharedPreferences.Editor edit2 = applicationContext62.getSharedPreferences("DIAGMON_PREFERENCE", 0).edit();
                edit2.putLong("lastPDUpdatedTime", currentTimeMillis2);
                edit2.apply();
            } else {
                Log.i(DeviceUtils.TAG, "Policy download interval is not yet passed");
            }
            if (serviceInfo.status != 0) {
                Log.i(DeviceUtils.TAG, "There isn't unregistered service");
                z = false;
            } else {
                DiagmonApiManager diagmonApiManager4 = DiagmonApiManager.getInstance();
                Context applicationContext7 = GEDJobService.this.getApplicationContext();
                diagmonApiManager4.getClass();
                DiagmonApiManager.serviceRegister(applicationContext7, serviceInfo, 0);
                z = true;
            }
            if (z) {
                long currentTimeMillis3 = System.currentTimeMillis() - serviceDao.MAX_KEEP_TIME;
                SharedPreferences sharedPreferences = serviceDao.preferences;
                long j = sharedPreferences.getLong(PhoneRestrictionPolicy.TIMESTAMP, 0L);
                if (sharedPreferences.getInt(IMSParameter.CALL.STATUS, 0) == 0 && currentTimeMillis3 > 0 && j <= currentTimeMillis3) {
                    Log.w(DeviceUtils.TAG, "delete service by time");
                    SharedPreferences.Editor edit3 = sharedPreferences.edit();
                    edit3.clear();
                    edit3.apply();
                }
                serviceInfo = serviceDao.getServiceInfo();
                if (serviceInfo == null) {
                    Log.w(DeviceUtils.TAG, "ServiceInfo is deleted by time");
                    return Boolean.TRUE;
                }
            }
            if (serviceInfo.status != 1) {
                Log.w(DeviceUtils.TAG, "Service is not registered, reports don't send");
            } else {
                EventDao eventDao = GEDDatabase.get(GEDJobService.this.getApplicationContext()).getEventDao();
                String[] strArr = {String.valueOf(System.currentTimeMillis() - eventDao.MAX_KEEP_TIME)};
                SQLiteDatabase sQLiteDatabase = eventDao.f635db;
                sQLiteDatabase.delete("Event", "timestamp<=?", strArr);
                Iterator it = ((ArrayList) eventDao.getEvents("expirationTime>? AND expirationTime<=? AND status=?", new String[]{String.valueOf(0), String.valueOf(System.currentTimeMillis()), String.valueOf(100)})).iterator();
                while (it.hasNext()) {
                    Event event = (Event) it.next();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("serviceId", event.serviceId);
                    contentValues.put("eventId", event.eventId);
                    contentValues.put("clientStatusCode", (Integer) 304);
                    contentValues.put(PhoneRestrictionPolicy.TIMESTAMP, Long.valueOf(event.timestamp));
                    sQLiteDatabase.insert("Result", null, contentValues);
                    event.eventId = "";
                    event.s3Path = "";
                    event.expirationTime = 0L;
                    eventDao.update(event);
                }
                ConnectivityManager connectivityManager = (ConnectivityManager) GEDJobService.this.getSystemService("connectivity");
                if (connectivityManager == null || (networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork())) == null || !networkCapabilities.hasTransport(1)) {
                    z3 = false;
                } else {
                    Log.i(DeviceUtils.TAG, "Wi-Fi is connected");
                }
                List<Event> events = z3 ? eventDao.getEvents("status=?", new String[]{String.valueOf(100)}) : eventDao.getEvents("status=? AND networkMode=?", new String[]{String.valueOf(100), String.valueOf(0)});
                if (events.size() <= 0) {
                    Log.i(DeviceUtils.TAG, "There isn't unreported event");
                } else {
                    for (Event event2 : events) {
                        DiagmonApiManager diagmonApiManager5 = DiagmonApiManager.getInstance();
                        Context applicationContext8 = GEDJobService.this.getApplicationContext();
                        diagmonApiManager5.getClass();
                        DiagmonApiManager.eventReport(applicationContext8, event2, 0);
                    }
                }
                ResultDao resultDao = GEDDatabase.get(GEDJobService.this.getApplicationContext()).getResultDao();
                resultDao.f636db.delete("Result", "timestamp<=?", new String[]{String.valueOf(System.currentTimeMillis() - resultDao.MAX_KEEP_TIME)});
                ArrayList arrayList = new ArrayList();
                try {
                    query = resultDao.f636db.query("Result", null, null, null, null, null, null);
                    try {
                    } finally {
                    }
                } catch (Exception unused2) {
                    AppLog.m270e("Fail to get unreported results");
                }
                if (query == null) {
                    AppLog.m269d("cursor is null");
                    if (query != null) {
                    }
                    if (arrayList.size() > 0) {
                        Log.i(DeviceUtils.TAG, "There isn't unreported result");
                    } else {
                        Iterator it2 = arrayList.iterator();
                        while (it2.hasNext()) {
                            Result result = (Result) it2.next();
                            DiagmonApiManager diagmonApiManager6 = DiagmonApiManager.getInstance();
                            Context applicationContext9 = GEDJobService.this.getApplicationContext();
                            diagmonApiManager6.getClass();
                            DiagmonApiManager.resultReport(applicationContext9, result, 0);
                        }
                    }
                } else {
                    while (query.moveToNext()) {
                        Result result2 = new Result();
                        result2.f638id = query.getInt(query.getColumnIndexOrThrow("id"));
                        result2.eventId = query.getString(query.getColumnIndexOrThrow("eventId"));
                        result2.serviceId = query.getString(query.getColumnIndexOrThrow("serviceId"));
                        result2.clientStatusCode = query.getInt(query.getColumnIndexOrThrow("clientStatusCode"));
                        result2.timestamp = query.getLong(query.getColumnIndexOrThrow(PhoneRestrictionPolicy.TIMESTAMP));
                        arrayList.add(result2);
                    }
                }
                query.close();
                if (arrayList.size() > 0) {
                }
            }
            return Boolean.TRUE;
        }

        @Override // android.os.AsyncTask
        public /* bridge */ /* synthetic */ void onPostExecute(Object obj) {
            onPostExecute();
        }
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [android.os.AsyncTask, com.sec.android.diagmonagent.log.ged.scheduler.GEDJobService$1] */
    @Override // android.app.job.JobService
    public final boolean onStartJob(final JobParameters jobParameters) {
        int jobId = jobParameters.getJobId();
        Log.i(DeviceUtils.TAG, "Job Started : " + jobId);
        ?? r0 = new ServerTask() { // from class: com.sec.android.diagmonagent.log.ged.scheduler.GEDJobService.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // com.sec.android.diagmonagent.log.ged.scheduler.GEDJobService.ServerTask, android.os.AsyncTask
            public final /* bridge */ /* synthetic */ Object doInBackground(Object[] objArr) {
                return doInBackground();
            }

            @Override // com.sec.android.diagmonagent.log.ged.scheduler.GEDJobService.ServerTask, android.os.AsyncTask
            public final /* bridge */ /* synthetic */ void onPostExecute(Object obj) {
                onPostExecute();
            }

            @Override // com.sec.android.diagmonagent.log.ged.scheduler.GEDJobService.ServerTask
            public final Boolean doInBackground() {
                new JobParameters[]{jobParameters};
                return super.doInBackground();
            }

            @Override // com.sec.android.diagmonagent.log.ged.scheduler.GEDJobService.ServerTask
            public final void onPostExecute() {
                GEDJobService.this.jobFinished(jobParameters, false);
            }
        };
        this.serverTask = r0;
        r0.execute(new JobParameters[0]);
        return true;
    }

    @Override // android.app.job.JobService
    public final boolean onStopJob(JobParameters jobParameters) {
        AsyncTaskC47991 asyncTaskC47991 = this.serverTask;
        if (asyncTaskC47991 != null) {
            asyncTaskC47991.cancel(true);
        }
        return true;
    }
}
