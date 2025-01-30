package com.sec.android.diagmonagent.log.ged.servreinterface.model.client;

import android.content.Context;
import android.util.Log;
import com.sec.android.diagmonagent.common.logger.AppLog;
import com.sec.android.diagmonagent.log.ged.servreinterface.model.response.Response;
import com.sec.android.diagmonagent.log.ged.util.DeviceUtils;
import com.sec.android.diagmonagent.log.ged.util.PreferenceUtils;
import com.sec.android.diagmonagent.log.ged.util.RestUtils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class DiagmonClient {
    public final JSONObject mBody;
    public final String mMethod;
    public final HttpURLConnection mURLConnection;
    public final Response response;

    public DiagmonClient(String str, String str2) {
        this.mURLConnection = null;
        try {
            AppLog.m269d("URL : " + str);
            URL url = new URL(str);
            this.response = new Response();
            this.mMethod = str2;
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            this.mURLConnection = httpURLConnection;
            httpURLConnection.setRequestMethod(str2);
            httpURLConnection.setConnectTimeout(2000);
            httpURLConnection.setReadTimeout(2000);
            httpURLConnection.setDoInput(true);
        } catch (IOException e) {
            Log.e(DeviceUtils.TAG, e + "constructor?");
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:123:0x01f3, code lost:
    
        if (r1 != null) goto L126;
     */
    /* JADX WARN: Code restructure failed: missing block: B:124:0x021b, code lost:
    
        r1.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:134:0x0219, code lost:
    
        if (r1 == null) goto L129;
     */
    /* JADX WARN: Removed duplicated region for block: B:133:0x0216 A[Catch: IOException -> 0x021f, TRY_ENTER, TryCatch #20 {IOException -> 0x021f, blocks: (B:122:0x01f0, B:124:0x021b, B:133:0x0216), top: B:2:0x0029 }] */
    /* JADX WARN: Removed duplicated region for block: B:140:0x0232 A[Catch: IOException -> 0x0236, TRY_LEAVE, TryCatch #5 {IOException -> 0x0236, blocks: (B:145:0x022d, B:140:0x0232), top: B:144:0x022d }] */
    /* JADX WARN: Removed duplicated region for block: B:144:0x022d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0224  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x012a A[Catch: IOException -> 0x0126, TryCatch #15 {IOException -> 0x0126, blocks: (B:62:0x0122, B:52:0x012a, B:54:0x012f, B:56:0x0134), top: B:61:0x0122 }] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x012f A[Catch: IOException -> 0x0126, TryCatch #15 {IOException -> 0x0126, blocks: (B:62:0x0122, B:52:0x012a, B:54:0x012f, B:56:0x0134), top: B:61:0x0122 }] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0134 A[Catch: IOException -> 0x0126, TRY_LEAVE, TryCatch #15 {IOException -> 0x0126, blocks: (B:62:0x0122, B:52:0x012a, B:54:0x012f, B:56:0x0134), top: B:61:0x0122 }] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0122 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:70:0x015e A[Catch: IOException -> 0x015a, TryCatch #0 {IOException -> 0x015a, blocks: (B:83:0x0156, B:70:0x015e, B:72:0x0163, B:74:0x0168), top: B:82:0x0156 }] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0163 A[Catch: IOException -> 0x015a, TryCatch #0 {IOException -> 0x015a, blocks: (B:83:0x0156, B:70:0x015e, B:72:0x0163, B:74:0x0168), top: B:82:0x0156 }] */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0168 A[Catch: IOException -> 0x015a, TRY_LEAVE, TryCatch #0 {IOException -> 0x015a, blocks: (B:83:0x0156, B:70:0x015e, B:72:0x0163, B:74:0x0168), top: B:82:0x0156 }] */
    /* JADX WARN: Removed duplicated region for block: B:81:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:82:0x0156 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Response execute() {
        InputStream inputStream;
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2;
        BufferedReader bufferedReader3;
        OutputStream outputStream;
        InputStream inputStream2;
        BufferedWriter bufferedWriter;
        BufferedReader bufferedReader4;
        BufferedWriter bufferedWriter2;
        BufferedReader bufferedReader5;
        OutputStream outputStream2;
        BufferedReader bufferedReader6;
        StringBuilder sb;
        BufferedReader bufferedReader7;
        AppLog.m269d("Client - execute()");
        String str = this.mMethod;
        str.getClass();
        boolean equals = str.equals("GET");
        Response response = this.response;
        HttpURLConnection httpURLConnection = this.mURLConnection;
        try {
            if (equals) {
                try {
                    httpURLConnection.getResponseMessage();
                    response.getClass();
                    response.code = httpURLConnection.getResponseCode();
                    inputStream = httpURLConnection.getResponseCode() == 200 ? httpURLConnection.getInputStream() : httpURLConnection.getErrorStream();
                    try {
                        if (inputStream != null) {
                            AppLog.m269d("bufferedReader start");
                            char[] cArr = new char[128];
                            StringBuffer stringBuffer = new StringBuffer();
                            BufferedReader bufferedReader8 = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                            while (true) {
                                try {
                                    int read = bufferedReader8.read(cArr, 0, 128);
                                    if (read == -1) {
                                        break;
                                    }
                                    stringBuffer.append(cArr, 0, read);
                                } catch (IOException e) {
                                    e = e;
                                    bufferedReader2 = bufferedReader8;
                                    try {
                                        AppLog.m270e(e + " failed to getInputStream()");
                                        if (bufferedReader2 != null) {
                                            bufferedReader2.close();
                                        }
                                    } catch (Throwable th) {
                                        th = th;
                                        bufferedReader = bufferedReader2;
                                        if (bufferedReader != null) {
                                            try {
                                                bufferedReader.close();
                                            } catch (IOException unused) {
                                                AppLog.m270e("failed to close()");
                                                throw th;
                                            }
                                        }
                                        if (inputStream != null) {
                                            inputStream.close();
                                        }
                                        throw th;
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                    bufferedReader = bufferedReader8;
                                    if (bufferedReader != null) {
                                    }
                                    if (inputStream != null) {
                                    }
                                    throw th;
                                }
                            }
                            AppLog.m269d("bufferedReader end");
                            response.body = stringBuffer.toString();
                            AppLog.m269d("JSON = " + response.body);
                            bufferedReader3 = bufferedReader8;
                        } else {
                            AppLog.m269d("in is null");
                            bufferedReader3 = null;
                        }
                        if (bufferedReader3 != null) {
                            bufferedReader3.close();
                        }
                    } catch (IOException e2) {
                        e = e2;
                        bufferedReader2 = null;
                        AppLog.m270e(e + " failed to getInputStream()");
                        if (bufferedReader2 != null) {
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        bufferedReader = null;
                        if (bufferedReader != null) {
                        }
                        if (inputStream != null) {
                        }
                        throw th;
                    }
                } catch (IOException e3) {
                    e = e3;
                    inputStream = null;
                } catch (Throwable th4) {
                    th = th4;
                    inputStream = null;
                }
            } else {
                if (!str.equals("POST")) {
                    throw new UnsupportedOperationException();
                }
                try {
                    AppLog.m269d(httpURLConnection + " bufferedWriter start");
                    outputStream = httpURLConnection.getOutputStream();
                    try {
                        bufferedWriter2 = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    } catch (IOException e4) {
                        e = e4;
                        bufferedWriter2 = null;
                        inputStream2 = null;
                        bufferedReader5 = null;
                        try {
                            AppLog.m270e(e + " failed to getInputStream()");
                            AppLog.m270e(" failed to getInputStream()");
                            if (bufferedWriter2 != null) {
                                try {
                                    bufferedWriter2.close();
                                } catch (IOException e5) {
                                    e = e5;
                                    sb = new StringBuilder();
                                    sb.append(e);
                                    sb.append("failed to close()");
                                    AppLog.m270e(sb.toString());
                                    if (httpURLConnection != null) {
                                    }
                                    return response;
                                }
                            }
                            if (outputStream != null) {
                                outputStream.close();
                            }
                            if (bufferedReader5 != null) {
                                bufferedReader5.close();
                            }
                            if (inputStream2 != null) {
                                inputStream2.close();
                            }
                            if (httpURLConnection != null) {
                            }
                            return response;
                        } catch (Throwable th5) {
                            th = th5;
                            bufferedReader6 = bufferedReader5;
                            bufferedReader4 = bufferedReader6;
                            bufferedWriter = bufferedWriter2;
                            outputStream2 = outputStream;
                            Throwable th6 = th;
                            if (bufferedWriter != null) {
                                try {
                                    bufferedWriter.close();
                                } catch (IOException e6) {
                                    AppLog.m270e(e6 + "failed to close()");
                                    throw th6;
                                }
                            }
                            if (outputStream2 != null) {
                                outputStream2.close();
                            }
                            if (bufferedReader4 != null) {
                                bufferedReader4.close();
                            }
                            if (inputStream2 != null) {
                                throw th6;
                            }
                            inputStream2.close();
                            throw th6;
                        }
                    } catch (Throwable th7) {
                        th = th7;
                        inputStream2 = null;
                        bufferedWriter = null;
                        bufferedReader4 = null;
                        outputStream2 = outputStream;
                        Throwable th62 = th;
                        if (bufferedWriter != null) {
                        }
                        if (outputStream2 != null) {
                        }
                        if (bufferedReader4 != null) {
                        }
                        if (inputStream2 != null) {
                        }
                    }
                    try {
                        bufferedWriter2.write(this.mBody.toString());
                        bufferedWriter2.flush();
                        AppLog.m269d("bufferedWriter end");
                        httpURLConnection.getResponseMessage();
                        response.getClass();
                        response.code = httpURLConnection.getResponseCode();
                        inputStream2 = httpURLConnection.getResponseCode() == 200 ? httpURLConnection.getInputStream() : httpURLConnection.getErrorStream();
                    } catch (IOException e7) {
                        e = e7;
                        inputStream2 = null;
                        bufferedReader5 = null;
                        AppLog.m270e(e + " failed to getInputStream()");
                        AppLog.m270e(" failed to getInputStream()");
                        if (bufferedWriter2 != null) {
                        }
                        if (outputStream != null) {
                        }
                        if (bufferedReader5 != null) {
                        }
                        if (inputStream2 != null) {
                        }
                        if (httpURLConnection != null) {
                        }
                        return response;
                    } catch (Throwable th8) {
                        th = th8;
                        inputStream2 = null;
                    }
                    try {
                        if (inputStream2 != null) {
                            AppLog.m269d("bufferedReader start");
                            char[] cArr2 = new char[128];
                            StringBuffer stringBuffer2 = new StringBuffer();
                            BufferedReader bufferedReader9 = new BufferedReader(new InputStreamReader(inputStream2, "UTF-8"));
                            while (true) {
                                try {
                                    int read2 = bufferedReader9.read(cArr2, 0, 128);
                                    if (read2 == -1) {
                                        break;
                                    }
                                    stringBuffer2.append(cArr2, 0, read2);
                                } catch (IOException e8) {
                                    e = e8;
                                    bufferedReader5 = bufferedReader9;
                                    AppLog.m270e(e + " failed to getInputStream()");
                                    AppLog.m270e(" failed to getInputStream()");
                                    if (bufferedWriter2 != null) {
                                    }
                                    if (outputStream != null) {
                                    }
                                    if (bufferedReader5 != null) {
                                    }
                                    if (inputStream2 != null) {
                                    }
                                    if (httpURLConnection != null) {
                                    }
                                    return response;
                                } catch (Throwable th9) {
                                    th = th9;
                                    bufferedReader6 = bufferedReader9;
                                    bufferedReader4 = bufferedReader6;
                                    bufferedWriter = bufferedWriter2;
                                    outputStream2 = outputStream;
                                    Throwable th622 = th;
                                    if (bufferedWriter != null) {
                                    }
                                    if (outputStream2 != null) {
                                    }
                                    if (bufferedReader4 != null) {
                                    }
                                    if (inputStream2 != null) {
                                    }
                                }
                            }
                            AppLog.m269d("bufferedReader end");
                            response.body = stringBuffer2.toString();
                            AppLog.m269d("JSON = " + response.body);
                            bufferedReader7 = bufferedReader9;
                        } else {
                            AppLog.m269d("in is null");
                            bufferedReader7 = null;
                        }
                        try {
                            bufferedWriter2.close();
                            if (outputStream != null) {
                                outputStream.close();
                            }
                            if (bufferedReader7 != null) {
                                bufferedReader7.close();
                            }
                            if (inputStream2 != null) {
                                inputStream2.close();
                            }
                        } catch (IOException e9) {
                            e = e9;
                            sb = new StringBuilder();
                            sb.append(e);
                            sb.append("failed to close()");
                            AppLog.m270e(sb.toString());
                            if (httpURLConnection != null) {
                            }
                            return response;
                        }
                    } catch (IOException e10) {
                        e = e10;
                        bufferedReader5 = null;
                        AppLog.m270e(e + " failed to getInputStream()");
                        AppLog.m270e(" failed to getInputStream()");
                        if (bufferedWriter2 != null) {
                        }
                        if (outputStream != null) {
                        }
                        if (bufferedReader5 != null) {
                        }
                        if (inputStream2 != null) {
                        }
                        if (httpURLConnection != null) {
                        }
                        return response;
                    } catch (Throwable th10) {
                        th = th10;
                        bufferedReader6 = null;
                        bufferedReader4 = bufferedReader6;
                        bufferedWriter = bufferedWriter2;
                        outputStream2 = outputStream;
                        Throwable th6222 = th;
                        if (bufferedWriter != null) {
                        }
                        if (outputStream2 != null) {
                        }
                        if (bufferedReader4 != null) {
                        }
                        if (inputStream2 != null) {
                        }
                    }
                } catch (IOException e11) {
                    e = e11;
                    outputStream = null;
                } catch (Throwable th11) {
                    th = th11;
                    outputStream = null;
                }
            }
        } catch (IOException unused2) {
            AppLog.m270e("failed to close()");
        }
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
        }
        return response;
    }

    public DiagmonClient(Context context, String str, String str2, String str3, String str4) {
        this.mURLConnection = null;
        try {
            String str5 = RestUtils.DEVICE_KEY;
            URL url = new URL("https://diagmon-serviceapi.samsungdm.com".concat(str).concat(str2));
            this.response = new Response();
            this.mMethod = str3;
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            this.mURLConnection = httpURLConnection;
            httpURLConnection.setRequestMethod(str3);
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setRequestProperty("Accept", "application/json");
            String diagmonPreference = PreferenceUtils.getDiagmonPreference(context, "JWT_TOKEN", "");
            AppLog.m269d("getAuth(): " + RestUtils.makeAuth(context, str, str4, "", diagmonPreference));
            httpURLConnection.setRequestProperty("Authorization", RestUtils.makeAuth(context, str, str4, "", diagmonPreference));
            httpURLConnection.setConnectTimeout(2000);
            httpURLConnection.setReadTimeout(2000);
            if (str3.equals("GET")) {
                httpURLConnection.setDoInput(true);
            } else {
                httpURLConnection.setDoOutput(true);
            }
        } catch (IOException unused) {
            AppLog.m270e(" constructor?");
        }
    }

    public DiagmonClient(Context context, String str, String str2, String str3, JSONObject jSONObject) {
        this.mURLConnection = null;
        try {
            String str4 = RestUtils.DEVICE_KEY;
            URL url = new URL("https://diagmon-serviceapi.samsungdm.com".concat(str));
            this.response = new Response();
            this.mBody = jSONObject;
            this.mMethod = str2;
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            this.mURLConnection = httpURLConnection;
            httpURLConnection.setRequestMethod(str2);
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setRequestProperty("Accept", "application/json");
            String jSONObject2 = jSONObject.toString();
            String diagmonPreference = PreferenceUtils.getDiagmonPreference(context, "JWT_TOKEN", "");
            AppLog.m269d("getAuth(): " + RestUtils.makeAuth(context, str, str3, jSONObject2, diagmonPreference));
            httpURLConnection.setRequestProperty("Authorization", RestUtils.makeAuth(context, str, str3, jSONObject2, diagmonPreference));
            httpURLConnection.setConnectTimeout(2000);
            httpURLConnection.setReadTimeout(2000);
            if (str2.equals("GET")) {
                httpURLConnection.setDoInput(true);
            } else {
                httpURLConnection.setDoOutput(true);
            }
        } catch (IOException e) {
            AppLog.m270e(e + " constructor?");
        }
    }
}
