package com.sec.android.diagmonagent.log.ged.servreinterface.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import com.samsung.android.knox.ex.peripheral.PeripheralManager;
import com.samsung.android.knox.restriction.PhoneRestrictionPolicy;
import com.sec.android.diagmonagent.common.logger.AppLog;
import com.sec.android.diagmonagent.log.ged.db.GEDDatabase;
import com.sec.android.diagmonagent.log.ged.db.dao.ResultDao;
import com.sec.android.diagmonagent.log.ged.db.dao.ServiceDao;
import com.sec.android.diagmonagent.log.ged.db.model.Event;
import com.sec.android.diagmonagent.log.ged.db.model.Result;
import com.sec.android.diagmonagent.log.ged.db.model.ServiceInfo;
import com.sec.android.diagmonagent.log.ged.servreinterface.model.client.DiagmonClient;
import com.sec.android.diagmonagent.log.ged.servreinterface.model.client.FileUploadClient;
import com.sec.android.diagmonagent.log.ged.servreinterface.model.client.TokenClient;
import com.sec.android.diagmonagent.log.ged.servreinterface.model.response.EventReportResponse;
import com.sec.android.diagmonagent.log.ged.servreinterface.model.response.PolicyResponse;
import com.sec.android.diagmonagent.log.ged.servreinterface.model.response.Response;
import com.sec.android.diagmonagent.log.ged.servreinterface.model.response.ServiceRegistrationResponse;
import com.sec.android.diagmonagent.log.ged.util.DeviceUtils;
import com.sec.android.diagmonagent.log.ged.util.ParsingUtils;
import com.sec.android.diagmonagent.log.ged.util.PreferenceUtils;
import com.sec.android.diagmonagent.log.ged.util.RestUtils;
import com.sec.ims.IMSParameter;
import com.sec.ims.configuration.DATA;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class DiagmonApiManager {
    public static volatile DiagmonApiManager sInstance;

    /* JADX WARN: Removed duplicated region for block: B:160:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x01ff  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0223  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x01d6 A[Catch: IOException -> 0x01d2, TryCatch #5 {IOException -> 0x01d2, blocks: (B:82:0x01ce, B:71:0x01d6, B:73:0x01db, B:75:0x01e0, B:76:0x01e3), top: B:81:0x01ce }] */
    /* JADX WARN: Removed duplicated region for block: B:73:0x01db A[Catch: IOException -> 0x01d2, TryCatch #5 {IOException -> 0x01d2, blocks: (B:82:0x01ce, B:71:0x01d6, B:73:0x01db, B:75:0x01e0, B:76:0x01e3), top: B:81:0x01ce }] */
    /* JADX WARN: Removed duplicated region for block: B:75:0x01e0 A[Catch: IOException -> 0x01d2, TryCatch #5 {IOException -> 0x01d2, blocks: (B:82:0x01ce, B:71:0x01d6, B:73:0x01db, B:75:0x01e0, B:76:0x01e3), top: B:81:0x01ce }] */
    /* JADX WARN: Removed duplicated region for block: B:81:0x01ce A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0289 A[Catch: IOException -> 0x029f, TryCatch #14 {IOException -> 0x029f, blocks: (B:91:0x0284, B:93:0x0289, B:95:0x028e, B:97:0x0293, B:99:0x0298, B:100:0x029b), top: B:90:0x0284 }] */
    /* JADX WARN: Removed duplicated region for block: B:95:0x028e A[Catch: IOException -> 0x029f, TryCatch #14 {IOException -> 0x029f, blocks: (B:91:0x0284, B:93:0x0289, B:95:0x028e, B:97:0x0293, B:99:0x0298, B:100:0x029b), top: B:90:0x0284 }] */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0293 A[Catch: IOException -> 0x029f, TryCatch #14 {IOException -> 0x029f, blocks: (B:91:0x0284, B:93:0x0289, B:95:0x028e, B:97:0x0293, B:99:0x0298, B:100:0x029b), top: B:90:0x0284 }] */
    /* JADX WARN: Removed duplicated region for block: B:99:0x0298 A[Catch: IOException -> 0x029f, TryCatch #14 {IOException -> 0x029f, blocks: (B:91:0x0284, B:93:0x0289, B:95:0x028e, B:97:0x0293, B:99:0x0298, B:100:0x029b), top: B:90:0x0284 }] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x00ca  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void eventReport(Context context, Event event, int i) {
        JSONObject jSONObject;
        JSONObject put;
        Response execute;
        String str;
        OutputStream outputStream;
        Throwable th;
        BufferedOutputStream bufferedOutputStream;
        FileInputStream fileInputStream;
        BufferedInputStream bufferedInputStream;
        BufferedOutputStream bufferedOutputStream2;
        BufferedInputStream bufferedInputStream2;
        Throwable th2;
        int i2;
        String str2;
        StringBuilder sb;
        String str3 = RestUtils.DEVICE_KEY;
        try {
            jSONObject = DeviceUtils.getSimpleDeviceInfo(context);
            jSONObject.put("diagnosticsAgree", "N");
        } catch (JSONException unused) {
            jSONObject = null;
        }
        if (jSONObject != null) {
            try {
                jSONObject.put("deviceId", event.deviceId);
                jSONObject.put("eventId", event.eventId);
                jSONObject.put("networkMode", event.networkMode);
                jSONObject.put(IMSParameter.CALL.STATUS, event.status);
                jSONObject.put("retryCount", event.retryCount);
                jSONObject.put("serviceId", event.serviceId);
                jSONObject.put("serviceVersion", event.serviceVersion);
                jSONObject.put("serviceAgreeType", event.serviceAgreeType);
                jSONObject.put("logPath", event.logPath);
                jSONObject.put("s3Path", event.s3Path);
                jSONObject.put("sdkType", event.sdkType);
                jSONObject.put(PeripheralManager.Temp.EXTRA_SDK_VERSION, event.sdkVersion);
                jSONObject.put("serviceDefinedKey", event.serviceDefinedKey);
                jSONObject.put("errorCode", event.errorCode);
                jSONObject.put("description", event.description);
                jSONObject.put("relayClientType", event.relayClientType);
                jSONObject.put("relayClientVersion", event.relayClientVersion);
                jSONObject.put("extension", event.extension);
                jSONObject.put(PhoneRestrictionPolicy.TIMESTAMP, event.timestamp);
                jSONObject.put("expirationTime", event.expirationTime);
                put = new JSONObject().put("eventInfo", jSONObject);
            } catch (JSONException unused2) {
                AppLog.m270e("JSONException occurred making event object");
            }
            execute = new DiagmonClient(context, "/v2/eventreport", "POST", event.serviceId, put).execute();
            if (execute == null) {
                if (execute.code != 200) {
                    if (RestUtils.isTokenNeedToBeUpdated(context, execute)) {
                        refreshToken(context);
                        AppLog.m271i("Retry event report");
                        if (i < 3) {
                            eventReport(context, event, i + 1);
                            return;
                        }
                        int i3 = event.retryCount + 1;
                        event.retryCount = i3;
                        if (i3 >= 3) {
                            event.status = 303;
                            GEDDatabase.get(context).getEventDao().update(event);
                            GEDDatabase.get(context).getResultDao().insert(ResultDao.makeResult(event));
                            return;
                        }
                        return;
                    }
                    if (401 == execute.code && (str = execute.body) != null && str.contains("4403")) {
                        AppLog.m272w("Unauthorized error code : " + event.errorCode);
                        event.status = 402;
                        GEDDatabase.get(context).getEventDao().update(event);
                        GEDDatabase.get(context).getResultDao().insert(ResultDao.makeResult(event));
                        return;
                    }
                    AppLog.m272w("failed to connect to report event : " + execute.code);
                    int i4 = execute.code;
                    int i5 = event.retryCount + 1;
                    event.retryCount = i5;
                    if (i5 < 3) {
                        GEDDatabase.get(context).getEventDao().update(event);
                        return;
                    }
                    if (i4 == 400) {
                        event.status = 400;
                    } else if (i4 == 401) {
                        event.status = 401;
                    } else if (i4 != 500) {
                        event.status = 305;
                    } else {
                        event.status = 500;
                    }
                    AppLog.m271i("upload retry count over - delete LogFile");
                    DeviceUtils.deleteFiles(context.getApplicationContext().getFilesDir() + "/" + event.logPath);
                    GEDDatabase.get(context).getEventDao().update(event);
                    GEDDatabase.get(context).getResultDao().insert(ResultDao.makeResult(event));
                    return;
                }
                AppLog.m271i("succeed to connect to report event");
                AppLog.m269d(execute.body);
                String str4 = execute.body;
                EventReportResponse eventReportResponse = new EventReportResponse();
                try {
                    JSONObject jSONObject2 = new JSONObject(str4);
                    if (jSONObject2.has("eventId")) {
                        eventReportResponse.eventId = jSONObject2.getString("eventId");
                    }
                    if (jSONObject2.has("preSignedURL")) {
                        eventReportResponse.preSignedURL = jSONObject2.getString("preSignedURL");
                    }
                } catch (JSONException unused3) {
                    Log.e(DeviceUtils.TAG, "JSONException occurred while parsing event response");
                }
                event.eventId = eventReportResponse.eventId;
                event.s3Path = eventReportResponse.preSignedURL;
                event.expirationTime = System.currentTimeMillis() + 86400000;
                GEDDatabase.get(context).getEventDao().update(event);
                FileUploadClient fileUploadClient = new FileUploadClient(event.s3Path);
                String str5 = context.getFilesDir() + "/" + event.logPath;
                HttpURLConnection httpURLConnection = fileUploadClient.mURLConnection;
                if (httpURLConnection == null) {
                    i2 = -1;
                } else {
                    try {
                        outputStream = httpURLConnection.getOutputStream();
                        try {
                            bufferedOutputStream2 = new BufferedOutputStream(outputStream);
                        } catch (IOException e) {
                            e = e;
                            bufferedOutputStream2 = null;
                            bufferedInputStream2 = null;
                            fileInputStream = null;
                            e.printStackTrace();
                            try {
                                i2 = httpURLConnection.getResponseCode();
                                if (bufferedOutputStream2 != null) {
                                }
                                if (outputStream != null) {
                                }
                                if (bufferedInputStream2 != null) {
                                }
                                if (fileInputStream != null) {
                                }
                                httpURLConnection.disconnect();
                            } catch (IOException e2) {
                                e = e2;
                                i2 = -1;
                            }
                            if (i2 != 200) {
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            th = th;
                            bufferedOutputStream = null;
                            fileInputStream = null;
                            bufferedInputStream = null;
                            try {
                                httpURLConnection.getResponseCode();
                                if (bufferedOutputStream != null) {
                                }
                                if (outputStream != null) {
                                }
                                if (bufferedInputStream != null) {
                                }
                                if (fileInputStream != null) {
                                }
                                httpURLConnection.disconnect();
                                throw th;
                            } catch (IOException e3) {
                                Log.d(DeviceUtils.TAG, e3 + "close?");
                                throw th;
                            }
                        }
                        try {
                            fileInputStream = new FileInputStream(new File(str5));
                            try {
                                bufferedInputStream2 = new BufferedInputStream(fileInputStream);
                                try {
                                    try {
                                        byte[] bArr = new byte[1024];
                                        while (true) {
                                            int read = bufferedInputStream2.read(bArr, 0, 1024);
                                            if (read <= 0) {
                                                break;
                                            } else {
                                                bufferedOutputStream2.write(bArr, 0, read);
                                            }
                                        }
                                        bufferedOutputStream2.flush();
                                        try {
                                            i2 = httpURLConnection.getResponseCode();
                                        } catch (IOException e4) {
                                            e = e4;
                                            i2 = -1;
                                        }
                                        try {
                                            bufferedOutputStream2.close();
                                            if (outputStream != null) {
                                                outputStream.close();
                                            }
                                            bufferedInputStream2.close();
                                            fileInputStream.close();
                                            httpURLConnection.disconnect();
                                        } catch (IOException e5) {
                                            e = e5;
                                            str2 = DeviceUtils.TAG;
                                            sb = new StringBuilder();
                                            sb.append(e);
                                            sb.append("close?");
                                            Log.d(str2, sb.toString());
                                            if (i2 != 200) {
                                            }
                                        }
                                    } catch (IOException e6) {
                                        e = e6;
                                        e.printStackTrace();
                                        i2 = httpURLConnection.getResponseCode();
                                        if (bufferedOutputStream2 != null) {
                                            try {
                                                bufferedOutputStream2.close();
                                            } catch (IOException e7) {
                                                e = e7;
                                                str2 = DeviceUtils.TAG;
                                                sb = new StringBuilder();
                                                sb.append(e);
                                                sb.append("close?");
                                                Log.d(str2, sb.toString());
                                                if (i2 != 200) {
                                                }
                                            }
                                        }
                                        if (outputStream != null) {
                                            outputStream.close();
                                        }
                                        if (bufferedInputStream2 != null) {
                                            bufferedInputStream2.close();
                                        }
                                        if (fileInputStream != null) {
                                            fileInputStream.close();
                                        }
                                        httpURLConnection.disconnect();
                                        if (i2 != 200) {
                                        }
                                    }
                                } catch (Throwable th4) {
                                    th2 = th4;
                                    bufferedInputStream = bufferedInputStream2;
                                    bufferedOutputStream = bufferedOutputStream2;
                                    th = th2;
                                    httpURLConnection.getResponseCode();
                                    if (bufferedOutputStream != null) {
                                        bufferedOutputStream.close();
                                    }
                                    if (outputStream != null) {
                                        outputStream.close();
                                    }
                                    if (bufferedInputStream != null) {
                                        bufferedInputStream.close();
                                    }
                                    if (fileInputStream != null) {
                                        fileInputStream.close();
                                    }
                                    httpURLConnection.disconnect();
                                    throw th;
                                }
                            } catch (IOException e8) {
                                e = e8;
                                bufferedInputStream2 = null;
                            } catch (Throwable th5) {
                                th2 = th5;
                                bufferedInputStream2 = null;
                                bufferedInputStream = bufferedInputStream2;
                                bufferedOutputStream = bufferedOutputStream2;
                                th = th2;
                                httpURLConnection.getResponseCode();
                                if (bufferedOutputStream != null) {
                                }
                                if (outputStream != null) {
                                }
                                if (bufferedInputStream != null) {
                                }
                                if (fileInputStream != null) {
                                }
                                httpURLConnection.disconnect();
                                throw th;
                            }
                        } catch (IOException e9) {
                            e = e9;
                            bufferedInputStream2 = null;
                            fileInputStream = null;
                            e.printStackTrace();
                            i2 = httpURLConnection.getResponseCode();
                            if (bufferedOutputStream2 != null) {
                            }
                            if (outputStream != null) {
                            }
                            if (bufferedInputStream2 != null) {
                            }
                            if (fileInputStream != null) {
                            }
                            httpURLConnection.disconnect();
                            if (i2 != 200) {
                            }
                        } catch (Throwable th6) {
                            th2 = th6;
                            bufferedOutputStream = bufferedOutputStream2;
                            fileInputStream = null;
                            bufferedInputStream = null;
                            th = th2;
                            httpURLConnection.getResponseCode();
                            if (bufferedOutputStream != null) {
                            }
                            if (outputStream != null) {
                            }
                            if (bufferedInputStream != null) {
                            }
                            if (fileInputStream != null) {
                            }
                            httpURLConnection.disconnect();
                            throw th;
                        }
                    } catch (IOException e10) {
                        e = e10;
                        outputStream = null;
                    } catch (Throwable th7) {
                        th = th7;
                        outputStream = null;
                    }
                }
                if (i2 != 200) {
                    AppLog.m269d(event.eventId);
                    AppLog.m271i("succeed to connect to upload file");
                    new File(str5).delete();
                    event.status = 200;
                    GEDDatabase.get(context).getEventDao().update(event);
                    resultReportAfterLogUpload(context, event, 0);
                    return;
                }
                AppLog.m272w("Failed to connect to upload file");
                int i6 = event.retryCount + 1;
                event.retryCount = i6;
                if (i6 < 3) {
                    GEDDatabase.get(context).getEventDao().update(event);
                    return;
                }
                AppLog.m271i("upload retry count over - delete LogFile");
                DeviceUtils.deleteFiles(context.getApplicationContext().getFilesDir() + "/" + event.logPath);
                event.status = 302;
                GEDDatabase.get(context).getEventDao().update(event);
                GEDDatabase.get(context).getResultDao().insert(ResultDao.makeResult(event));
                return;
            }
            return;
        }
        put = null;
        execute = new DiagmonClient(context, "/v2/eventreport", "POST", event.serviceId, put).execute();
        if (execute == null) {
        }
    }

    public static DiagmonApiManager getInstance() {
        if (sInstance == null) {
            synchronized (DiagmonApiManager.class) {
                if (sInstance == null) {
                    sInstance = new DiagmonApiManager();
                }
            }
        }
        return sInstance;
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x00e0  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00f5 A[Catch: JSONException -> 0x01fb, TryCatch #0 {JSONException -> 0x01fb, blocks: (B:12:0x0087, B:13:0x009f, B:16:0x00ac, B:31:0x00fb, B:32:0x00e7, B:34:0x00ee, B:36:0x00f5, B:38:0x00c5, B:41:0x00cd, B:44:0x00d5, B:48:0x00fe, B:49:0x0105, B:51:0x010b, B:53:0x011b, B:54:0x0122, B:56:0x0128, B:58:0x0136, B:72:0x017c, B:74:0x0182, B:79:0x0189, B:81:0x0190, B:82:0x0196, B:84:0x019c, B:85:0x01a2, B:87:0x01a8, B:90:0x01af, B:92:0x01b8, B:93:0x01be, B:95:0x01c4, B:96:0x01ca, B:98:0x01d0, B:101:0x014e, B:104:0x0156, B:107:0x0161), top: B:11:0x0087 }] */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0173  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x01af A[Catch: JSONException -> 0x01fb, TryCatch #0 {JSONException -> 0x01fb, blocks: (B:12:0x0087, B:13:0x009f, B:16:0x00ac, B:31:0x00fb, B:32:0x00e7, B:34:0x00ee, B:36:0x00f5, B:38:0x00c5, B:41:0x00cd, B:44:0x00d5, B:48:0x00fe, B:49:0x0105, B:51:0x010b, B:53:0x011b, B:54:0x0122, B:56:0x0128, B:58:0x0136, B:72:0x017c, B:74:0x0182, B:79:0x0189, B:81:0x0190, B:82:0x0196, B:84:0x019c, B:85:0x01a2, B:87:0x01a8, B:90:0x01af, B:92:0x01b8, B:93:0x01be, B:95:0x01c4, B:96:0x01ca, B:98:0x01d0, B:101:0x014e, B:104:0x0156, B:107:0x0161), top: B:11:0x0087 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void refreshPolicy(Context context, String str, int i) {
        String str2;
        JSONArray jSONArray;
        String str3;
        JSONArray jSONArray2;
        char c;
        char c2;
        if (DATA.DM_FIELD_INDEX.PCSCF_DOMAIN.equals(PreferenceUtils.getDiagmonPreference(context, "needed_version", DATA.DM_FIELD_INDEX.PCSCF_DOMAIN))) {
            AppLog.m271i("Needed policy version is invalid");
            return;
        }
        String str4 = "?policyVersion=" + PreferenceUtils.getDiagmonPreference(context, "needed_version", DATA.DM_FIELD_INDEX.PCSCF_DOMAIN) + "&currentPolicyVersion=" + PreferenceUtils.getDiagmonPreference(context, "version", DATA.DM_FIELD_INDEX.PCSCF_DOMAIN) + "&dmaVersion=ged&tmcc=" + DeviceUtils.getTmcc(context) + "&smcc=" + DeviceUtils.getSmcc(context);
        String str5 = RestUtils.DEVICE_KEY;
        Response execute = new DiagmonClient(context, "/v2/policy", str4, "GET", "x6g1q14r77").execute();
        if (execute != null) {
            if (execute.code != 200) {
                if (!RestUtils.isTokenNeedToBeUpdated(context, execute)) {
                    AppLog.m272w("Failed to connect to refresh policy : " + execute.code);
                    return;
                } else {
                    refreshToken(context);
                    AppLog.m271i("Retry refresh policy");
                    if (i < 3) {
                        refreshPolicy(context, str, 1 + i);
                        return;
                    }
                    return;
                }
            }
            AppLog.m271i("succeed to connect to refresh policy");
            AppLog.m269d(execute.body);
            String str6 = execute.body;
            PolicyResponse policyResponse = new PolicyResponse();
            try {
                JSONObject jSONObject = new JSONObject(str6);
                policyResponse.version = jSONObject.getString("version");
                policyResponse.pollingInterval = jSONObject.getString("pollingInterval");
                JSONArray jSONArray3 = jSONObject.getJSONArray("defaultPolicySet");
                int i2 = 0;
                while (true) {
                    str2 = "policyId";
                    if (i2 >= jSONArray3.length()) {
                        break;
                    }
                    JSONObject jSONObject2 = jSONArray3.getJSONObject(i2);
                    String string = jSONObject2.getString("policyId");
                    int hashCode = string.hashCode();
                    if (hashCode == -243495139) {
                        if (string.equals("uploadFile")) {
                            c2 = 0;
                            if (c2 != 0) {
                            }
                            i2++;
                        }
                        c2 = 65535;
                        if (c2 != 0) {
                        }
                        i2++;
                    } else if (hashCode != 1296732449) {
                        if (hashCode == 1529398255 && string.equals("maxFileCount")) {
                            c2 = 2;
                            if (c2 != 0) {
                                policyResponse.defaultUploadFile = jSONObject2.getString("value");
                            } else if (c2 == 1) {
                                policyResponse.defaultMaxFileSize = jSONObject2.getString("value");
                            } else if (c2 == 2) {
                                policyResponse.defaultMaxFileCount = jSONObject2.getString("value");
                            }
                            i2++;
                        }
                        c2 = 65535;
                        if (c2 != 0) {
                        }
                        i2++;
                    } else {
                        if (string.equals("maxFileSize")) {
                            c2 = 1;
                            if (c2 != 0) {
                            }
                            i2++;
                        }
                        c2 = 65535;
                        if (c2 != 0) {
                        }
                        i2++;
                    }
                }
                JSONArray jSONArray4 = jSONObject.getJSONArray("services");
                int i3 = 0;
                while (i3 < jSONArray4.length()) {
                    JSONObject jSONObject3 = jSONArray4.getJSONObject(i3);
                    if (str.equals(jSONObject3.getString("serviceId"))) {
                        JSONArray jSONArray5 = jSONObject3.getJSONArray("policySet");
                        int i4 = 0;
                        while (i4 < jSONArray5.length()) {
                            JSONObject jSONObject4 = jSONArray5.getJSONObject(i4);
                            String string2 = jSONObject4.getString(str2);
                            if (TextUtils.isEmpty(string2)) {
                                jSONArray = jSONArray4;
                                str3 = str2;
                                jSONArray2 = jSONArray5;
                            } else {
                                jSONArray = jSONArray4;
                                int hashCode2 = string2.hashCode();
                                str3 = str2;
                                if (hashCode2 == -243495139) {
                                    if (string2.equals("uploadFile")) {
                                        c = 0;
                                        if (c == 0) {
                                        }
                                    }
                                    c = 65535;
                                    if (c == 0) {
                                    }
                                } else if (hashCode2 != 1296732449) {
                                    if (hashCode2 == 1529398255 && string2.equals("maxFileCount")) {
                                        c = 2;
                                        if (c == 0) {
                                            jSONArray2 = jSONArray5;
                                            if (c == 1) {
                                                if (jSONObject4.has("value")) {
                                                    policyResponse.maxFileSizeValue = jSONObject4.getString("value");
                                                }
                                                if (jSONObject4.has("serviceVersion")) {
                                                    policyResponse.maxFileSizeServiceVersion = jSONObject4.getString("serviceVersion");
                                                }
                                                if (jSONObject4.has("errorCode")) {
                                                    policyResponse.maxFileSizeErrorCode = jSONObject4.getString("errorCode");
                                                }
                                            } else if (c == 2 && jSONObject4.has("value")) {
                                                policyResponse.maxFileCountValue = jSONObject4.getString("value");
                                            }
                                        } else {
                                            jSONArray2 = jSONArray5;
                                            if (jSONObject4.has("value")) {
                                                policyResponse.uploadFileValue = jSONObject4.getString("value");
                                            }
                                            if (jSONObject4.has("serviceVersion")) {
                                                policyResponse.uploadFileServiceVersion = jSONObject4.getString("serviceVersion");
                                            }
                                            if (jSONObject4.has("errorCode")) {
                                                policyResponse.uploadFileErrorCode = jSONObject4.getString("errorCode");
                                            }
                                        }
                                    }
                                    c = 65535;
                                    if (c == 0) {
                                    }
                                } else {
                                    if (string2.equals("maxFileSize")) {
                                        c = 1;
                                        if (c == 0) {
                                        }
                                    }
                                    c = 65535;
                                    if (c == 0) {
                                    }
                                }
                            }
                            i4++;
                            jSONArray4 = jSONArray;
                            str2 = str3;
                            jSONArray5 = jSONArray2;
                        }
                    }
                    i3++;
                    jSONArray4 = jSONArray4;
                    str2 = str2;
                }
            } catch (JSONException unused) {
                Log.e(DeviceUtils.TAG, "JSONException occurred while parsing policy");
            }
            PreferenceUtils.setDiagmonPreference(context, "uploadFileValue", "");
            PreferenceUtils.setDiagmonPreference(context, "uploadFileServiceVersion", "");
            PreferenceUtils.setDiagmonPreference(context, "uploadFileErrorCode", "");
            PreferenceUtils.setDiagmonPreference(context, "maxFileSizeValue", "");
            PreferenceUtils.setDiagmonPreference(context, "maxFileSizeServiceVersion", "");
            PreferenceUtils.setDiagmonPreference(context, "maxFileSizeErrorCode", "");
            PreferenceUtils.setDiagmonPreference(context, "maxFileCountValue", "");
            PreferenceUtils.setDiagmonPreference(context, "version", policyResponse.version);
            PreferenceUtils.setDiagmonPreference(context, "pollingInterval", policyResponse.pollingInterval);
            PreferenceUtils.setDiagmonPreference(context, "uploadFile", policyResponse.defaultUploadFile);
            PreferenceUtils.setDiagmonPreference(context, "maxFileSize", policyResponse.defaultMaxFileSize);
            PreferenceUtils.setDiagmonPreference(context, "maxFileCount", policyResponse.defaultMaxFileCount);
            PreferenceUtils.setDiagmonPreference(context, "uploadFileValue", policyResponse.uploadFileValue);
            PreferenceUtils.setDiagmonPreference(context, "uploadFileServiceVersion", policyResponse.uploadFileServiceVersion);
            PreferenceUtils.setDiagmonPreference(context, "uploadFileErrorCode", policyResponse.uploadFileErrorCode);
            PreferenceUtils.setDiagmonPreference(context, "maxFileSizeValue", policyResponse.maxFileSizeValue);
            PreferenceUtils.setDiagmonPreference(context, "maxFileSizeServiceVersion", policyResponse.maxFileSizeServiceVersion);
            PreferenceUtils.setDiagmonPreference(context, "maxFileSizeErrorCode", policyResponse.maxFileSizeErrorCode);
            PreferenceUtils.setDiagmonPreference(context, "maxFileCountValue", policyResponse.maxFileCountValue);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0082, code lost:
    
        if (r2 != 0) goto L34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00ae, code lost:
    
        r2.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00ac, code lost:
    
        if (r2 != 0) goto L34;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Not initialized variable reg: 8, insn: 0x00f4: MOVE (r4 I:??[OBJECT, ARRAY]) = (r8 I:??[OBJECT, ARRAY]) (LINE:245), block:B:58:0x00f4 */
    /* JADX WARN: Type inference failed for: r2v0, types: [java.net.HttpURLConnection] */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v10 */
    /* JADX WARN: Type inference failed for: r2v11 */
    /* JADX WARN: Type inference failed for: r2v3 */
    /* JADX WARN: Type inference failed for: r2v4, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r2v5 */
    /* JADX WARN: Type inference failed for: r2v6, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r2v8, types: [java.io.InputStream] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void refreshToken(Context context) {
        BufferedReader bufferedReader;
        IOException e;
        BufferedReader bufferedReader2;
        TokenClient tokenClient = new TokenClient(context, "/v2/common/authtoken");
        ?? r2 = tokenClient.mURLConnection;
        Response response = tokenClient.response;
        BufferedReader bufferedReader3 = null;
        try {
            try {
                try {
                    r2.getResponseMessage();
                    response.getClass();
                    response.code = r2.getResponseCode();
                    r2 = r2.getResponseCode() == 200 ? r2.getInputStream() : r2.getErrorStream();
                } catch (Throwable th) {
                    th = th;
                    bufferedReader3 = bufferedReader2;
                }
                try {
                    if (r2 != 0) {
                        AppLog.m269d("bufferedReader start");
                        char[] cArr = new char[128];
                        StringBuffer stringBuffer = new StringBuffer();
                        bufferedReader = new BufferedReader(new InputStreamReader((InputStream) r2, "UTF-8"));
                        while (true) {
                            try {
                                int read = bufferedReader.read(cArr, 0, 128);
                                if (read == -1) {
                                    break;
                                } else {
                                    stringBuffer.append(cArr, 0, read);
                                }
                            } catch (IOException e2) {
                                e = e2;
                                AppLog.m270e(e + " failed to getInputStream()");
                                if (bufferedReader != null) {
                                    bufferedReader.close();
                                }
                            }
                        }
                        AppLog.m269d("bufferedReader end");
                        response.body = stringBuffer.toString();
                        AppLog.m271i("JSON = " + response.body);
                        bufferedReader3 = bufferedReader;
                    } else {
                        AppLog.m272w("in is null");
                    }
                    if (bufferedReader3 != null) {
                        bufferedReader3.close();
                    }
                } catch (IOException e3) {
                    bufferedReader = bufferedReader3;
                    e = e3;
                } catch (Throwable th2) {
                    th = th2;
                    if (bufferedReader3 != null) {
                        try {
                            bufferedReader3.close();
                        } catch (IOException unused) {
                            AppLog.m270e("Failed to close()");
                            throw th;
                        }
                    }
                    if (r2 != 0) {
                        r2.close();
                    }
                    throw th;
                }
            } catch (IOException e4) {
                bufferedReader = null;
                e = e4;
                r2 = 0;
            } catch (Throwable th3) {
                th = th3;
                r2 = 0;
            }
        } catch (IOException unused2) {
            AppLog.m270e("Failed to close()");
        }
        if (response != null) {
            try {
                if (response.code == 200) {
                    AppLog.m271i("succeed to connect to get JWT");
                    AppLog.m269d(response.body);
                    PreferenceUtils.setDiagmonPreference(context, "JWT_TOKEN", ParsingUtils.parseTokenResponse(response.body).token);
                } else {
                    AppLog.m272w("failed to connect to get JWT : " + response.code);
                }
            } catch (IllegalStateException | NullPointerException e5) {
                AppLog.m270e(e5.getMessage());
            }
        }
    }

    public static void resultReport(Context context, Result result, int i) {
        JSONObject jSONObject;
        String str = RestUtils.DEVICE_KEY;
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put("eventId", result.eventId);
            jSONObject2.put("clientStatusCode", result.clientStatusCode);
            jSONObject = new JSONObject().put("resultInfo", jSONObject2);
        } catch (JSONException unused) {
            AppLog.m270e("JSONException occurred making result object");
            jSONObject = null;
        }
        Response execute = new DiagmonClient(context, "/v2/eventreport/result", "POST", result.serviceId, jSONObject).execute();
        if (execute != null) {
            if (execute.code == 200) {
                AppLog.m271i("succeed to connect to report result");
                AppLog.m269d(execute.body);
                GEDDatabase.get(context).getResultDao().f636db.delete("Result", "id=?", new String[]{String.valueOf(result.f638id)});
                return;
            }
            if (!RestUtils.isTokenNeedToBeUpdated(context, execute)) {
                AppLog.m272w("Failed to connect to report result : " + execute.code);
            } else {
                refreshToken(context);
                AppLog.m271i("Retry result report");
                if (i < 3) {
                    resultReport(context, result, i + 1);
                }
            }
        }
    }

    public static void resultReportAfterLogUpload(Context context, Event event, int i) {
        JSONObject jSONObject;
        String str = RestUtils.DEVICE_KEY;
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put("eventId", event.eventId);
            jSONObject2.put("clientStatusCode", event.status);
            jSONObject = new JSONObject().put("resultInfo", jSONObject2);
        } catch (JSONException unused) {
            AppLog.m270e("JSONException occurred making result object");
            jSONObject = null;
        }
        Response execute = new DiagmonClient(context, "/v2/eventreport/result", "POST", event.serviceId, jSONObject).execute();
        if (execute != null) {
            if (execute.code == 200) {
                AppLog.m271i("succeed to connect to report result after log upload");
                AppLog.m269d(execute.body);
                return;
            }
            if (!RestUtils.isTokenNeedToBeUpdated(context, execute)) {
                GEDDatabase.get(context).getResultDao().insert(ResultDao.makeResult(event));
                AppLog.m272w("failed to connect to report result after log upload: " + execute.code);
                return;
            }
            refreshToken(context);
            AppLog.m271i("Retry result report after log upload");
            if (i < 3) {
                resultReportAfterLogUpload(context, event, i + 1);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x00c6, code lost:
    
        r12 = r13.getJSONObject(r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x00ce, code lost:
    
        if (r12.has("documentId") == false) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x00d0, code lost:
    
        r9.documentId = r12.getString("documentId");
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x00da, code lost:
    
        if (r12.has("id") == false) goto L27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x00dc, code lost:
    
        r12.getString("id");
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x00e3, code lost:
    
        if (r12.has("statusCode") == false) goto L30;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x00e5, code lost:
    
        r9.statusCode = r12.getString("statusCode");
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x00ef, code lost:
    
        if (r12.has("errorCode") == false) goto L33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x00f1, code lost:
    
        r9.errorCode = r12.getString("errorCode");
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00fb, code lost:
    
        if (r12.has("errorMessage") == false) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x00fd, code lost:
    
        r9.errorMessage = r12.getString("errorMessage");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void serviceRegister(Context context, ServiceInfo serviceInfo, int i) {
        JSONObject jSONObject;
        String str = RestUtils.DEVICE_KEY;
        JSONObject jSONObject2 = null;
        try {
            jSONObject = DeviceUtils.getSimpleDeviceInfo(context);
            jSONObject.put("diagnosticsAgree", "N");
        } catch (JSONException unused) {
            jSONObject = null;
        }
        if (jSONObject != null) {
            try {
                JSONObject jSONObject3 = new JSONObject();
                JSONArray jSONArray = new JSONArray();
                jSONObject3.put("serviceId", serviceInfo.serviceId);
                jSONObject3.put("deviceId", serviceInfo.deviceId);
                jSONObject3.put("serviceVersion", serviceInfo.serviceVersion);
                jSONObject3.put("serviceAgreeType", serviceInfo.serviceAgreeType);
                jSONObject3.put("sdkType", serviceInfo.sdkType);
                jSONObject3.put(PeripheralManager.Temp.EXTRA_SDK_VERSION, serviceInfo.sdkVersion);
                jSONObject3.put(IMSParameter.CALL.STATUS, serviceInfo.status);
                jSONObject3.put("trackingId", serviceInfo.trackingId);
                jSONArray.put(jSONObject3);
                jSONObject.put("service", jSONArray);
                jSONObject2 = new JSONObject().put("deviceInfo", jSONObject);
            } catch (JSONException unused2) {
                AppLog.m270e("JSONException occurred making service object");
            }
        }
        String str2 = RestUtils.DEVICE_KEY;
        Response execute = new DiagmonClient(context, "/v2/common/serviceregistration", "POST", "x6g1q14r77", jSONObject2).execute();
        if (execute != null) {
            if (execute.code != 200) {
                if (!RestUtils.isTokenNeedToBeUpdated(context, execute)) {
                    AppLog.m272w("failed to connect to register service : " + execute.code);
                    return;
                } else {
                    refreshToken(context);
                    AppLog.m271i("Retry service registration");
                    if (i < 3) {
                        serviceRegister(context, serviceInfo, i + 1);
                        return;
                    }
                    return;
                }
            }
            AppLog.m271i("succeed to connect to register service");
            AppLog.m269d(execute.body);
            String str3 = execute.body;
            String str4 = serviceInfo.serviceId;
            ServiceRegistrationResponse serviceRegistrationResponse = new ServiceRegistrationResponse();
            try {
                JSONArray jSONArray2 = new JSONObject(str3).getJSONArray("service");
                int i2 = 0;
                while (true) {
                    if (i2 >= jSONArray2.length()) {
                        break;
                    } else if (str4.equals(jSONArray2.getJSONObject(i2).getString("id"))) {
                        break;
                    } else {
                        i2++;
                    }
                }
            } catch (JSONException unused3) {
                Log.e(DeviceUtils.TAG, "JSONException occurred while parsing service response");
            }
            ServiceDao serviceDao = new ServiceDao(GEDDatabase.get(context).context);
            boolean equalsIgnoreCase = "Y".equalsIgnoreCase(serviceRegistrationResponse.statusCode);
            SharedPreferences sharedPreferences = serviceDao.preferences;
            if (equalsIgnoreCase) {
                sharedPreferences.edit().putString("documentId", serviceRegistrationResponse.documentId).apply();
                sharedPreferences.edit().putInt(IMSParameter.CALL.STATUS, 1).apply();
            } else {
                if ("1100".equals(serviceRegistrationResponse.errorCode)) {
                    sharedPreferences.edit().putInt(IMSParameter.CALL.STATUS, 2).apply();
                    return;
                }
                if ("1101".equals(serviceRegistrationResponse.errorCode)) {
                    sharedPreferences.edit().putInt(IMSParameter.CALL.STATUS, 3).apply();
                    return;
                }
                AppLog.m272w("ErrorCode = " + serviceRegistrationResponse.errorCode);
                AppLog.m272w("ErrorMessage = " + serviceRegistrationResponse.errorMessage);
            }
        }
    }
}
