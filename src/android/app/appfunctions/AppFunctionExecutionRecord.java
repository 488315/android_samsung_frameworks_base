package android.app.appfunctions;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.appsearch.GenericDocument;
import android.content.Context;
import android.content.pm.PackageManager;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.NtpTrustedTime;
import android.util.Slog;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import com.samsung.android.share.SemShareConstants;
import java.lang.reflect.Array;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class AppFunctionExecutionRecord {
    private static final Integer JSON_INDENTATION = 4;
    private static final Integer MAX_STRING_MASKING_NUMBER = 30;
    private static final String TAG = "AppFunctionExecutionRecord";
    String callingPackageVersion;
    AppFunctionException error;
    String foregroundApps;
    ExecuteAppFunctionAidlRequest requestInternal;
    ExecuteAppFunctionResponse result;
    LocalDateTime returnTime;
    String targetPackageVersion;
    private final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    LocalDateTime invocationTime = LocalDateTime.now();

    public AppFunctionExecutionRecord(ExecuteAppFunctionAidlRequest executeAppFunctionAidlRequest) {
        this.requestInternal = executeAppFunctionAidlRequest;
    }

    public void setResult(ExecuteAppFunctionResponse executeAppFunctionResponse) {
        this.returnTime = LocalDateTime.now();
        this.result = executeAppFunctionResponse;
    }

    public void setError(AppFunctionException appFunctionException) {
        this.returnTime = LocalDateTime.now();
        this.error = appFunctionException;
    }

    public String getCallingPackage() {
        return this.requestInternal.getCallingPackage();
    }

    public String getCallingPackageVersion() {
        return this.callingPackageVersion;
    }

    public String getTargetPackageName() {
        return this.requestInternal.getClientRequest().getTargetPackageName();
    }

    public String getTargetPackageVersion() {
        return this.targetPackageVersion;
    }

    public String getFunctionIdentifier() {
        return this.requestInternal.getClientRequest().getFunctionIdentifier();
    }

    public String getResultCode() {
        AppFunctionException appFunctionException = this.error;
        if (appFunctionException == null) {
            return "0";
        }
        return String.valueOf(appFunctionException.getErrorCode());
    }

    public void setExecutionRecord(Context context) {
        this.callingPackageVersion = getVersionName(context, getCallingPackage());
        this.targetPackageVersion = getVersionName(context, getTargetPackageName());
        setForegroundApps(context);
    }

    private void setForegroundApps(Context context) {
        ArrayList arrayList = new ArrayList(getVisibleApps(context));
        if (isKeyguardLocked(context)) {
            arrayList.add(getSystemUiPackageName(context));
        }
        this.foregroundApps = String.join(",", arrayList);
    }

    public String getForegroundApps() {
        return this.foregroundApps;
    }

    private boolean isKeyguardLocked(Context context) {
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(KeyguardManager.class);
        return keyguardManager != null && keyguardManager.isKeyguardLocked();
    }

    private String getSystemUiPackageName(Context context) {
        return context.getResources().getString(17039418);
    }

    private List<String> getVisibleApps(Context context) throws SecurityException {
        List<ActivityManager.RunningTaskInfo> runningTasks = ((ActivityManager) context.getSystemService(ActivityManager.class)).getRunningTasks(Integer.MAX_VALUE);
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < runningTasks.size(); i++) {
            ActivityManager.RunningTaskInfo runningTaskInfo = runningTasks.get(i);
            if (runningTaskInfo != null && runningTaskInfo.topActivity != null && runningTaskInfo.isVisible) {
                arrayList.add(runningTaskInfo.topActivity.getPackageName());
            }
        }
        return arrayList;
    }

    private String getVersionName(Context context, String str) {
        try {
            return context.getPackageManager().getPackageInfoAsUser(str, 0, UserHandle.getCallingUserId()).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Slog.d(TAG, "Package not found: " + str, e);
            return "";
        }
    }

    public LocalDateTime getInvocationTime() {
        return this.invocationTime;
    }

    public long getDuration() {
        return Duration.between(this.invocationTime, this.returnTime).toMillis();
    }

    String getResult(boolean z) {
        ExecuteAppFunctionResponse executeAppFunctionResponse = this.result;
        if (executeAppFunctionResponse == null) {
            return "{\n}";
        }
        try {
            return toJson(executeAppFunctionResponse.getResultDocument(), z);
        } catch (JSONException unused) {
            return "{\n}";
        }
    }

    void appendPropertyToJson(JSONObject jSONObject, String str, Object obj, boolean z) throws JSONException, ArrayIndexOutOfBoundsException, IllegalArgumentException {
        int i = 0;
        if (obj instanceof GenericDocument[]) {
            JSONArray jSONArray = new JSONArray();
            GenericDocument[] genericDocumentArr = (GenericDocument[]) obj;
            int length = genericDocumentArr.length;
            while (i < length) {
                GenericDocument genericDocument = genericDocumentArr[i];
                JSONObject jSONObject2 = new JSONObject();
                appendGenericDocumentToJson(jSONObject2, genericDocument, z);
                jSONArray.put(jSONObject2);
                i++;
            }
            jSONObject.put(str, jSONArray);
            return;
        }
        int length2 = Array.getLength(obj);
        JSONArray jSONArray2 = new JSONArray();
        while (i < length2) {
            Object obj2 = Array.get(obj, i);
            if (z) {
                jSONArray2.put(maskData(obj2));
            } else {
                jSONArray2.put(obj2);
            }
            i++;
        }
        jSONObject.put(str, jSONArray2);
    }

    void appendGenericDocumentToJson(JSONObject jSONObject, GenericDocument genericDocument, boolean z) throws JSONException, ArrayIndexOutOfBoundsException, IllegalArgumentException {
        jSONObject.put(Settings.EXTRA_NAMESPACE, genericDocument.getNamespace());
        jSONObject.put("id", genericDocument.getId());
        jSONObject.put(SemShareConstants.SHARE_STAR_KEY_SCORE, genericDocument.getScore());
        jSONObject.put("schemaType", genericDocument.getSchemaType());
        jSONObject.put("creationTimestampMillis", genericDocument.getCreationTimestampMillis());
        jSONObject.put("timeToLiveMillis", genericDocument.getTtlMillis());
        String[] strArr = (String[]) genericDocument.getPropertyNames().toArray(new String[0]);
        Arrays.sort(strArr);
        for (String str : strArr) {
            appendPropertyToJson(jSONObject, str, Objects.requireNonNull(genericDocument.getProperty(str)), z);
        }
    }

    String toJson(GenericDocument genericDocument, boolean z) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        try {
            appendGenericDocumentToJson(jSONObject, genericDocument, z);
        } catch (JSONException e) {
            Slog.e(TAG, "toJson JSONException : ", e);
        } catch (Exception e2) {
            Slog.e(TAG, "toJson : ", e2);
        }
        return jSONObject.toString(JSON_INDENTATION.intValue());
    }

    public String maskData(Object obj) {
        int length;
        if (obj == null) {
            return PerfettoProtoLogImpl.NULL_STRING;
        }
        if (obj instanceof String) {
            length = ((String) obj).length();
        } else if (!(obj instanceof Number) && (obj instanceof byte[])) {
            length = ((byte[]) obj).length;
        } else {
            length = obj.toString().length();
        }
        if (length > MAX_STRING_MASKING_NUMBER.intValue()) {
            return "*" + length + "~";
        }
        return "*".repeat(length);
    }

    public String toFullString(boolean z) {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append(NavigationBarInflaterView.SIZE_MOD_START);
            sb.append(this.TIME_FORMATTER.format(this.invocationTime));
            sb.append(" - ");
            sb.append(this.TIME_FORMATTER.format(this.returnTime));
            sb.append("][duration :");
            sb.append(getDuration());
            sb.append("ms]\n");
            sb.append("  callingPackage : ");
            sb.append(getCallingPackage());
            sb.append(NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
            sb.append(this.callingPackageVersion);
            sb.append(ShaderAssembler.NEWLINE);
            sb.append("  targetPackage : ");
            sb.append(getTargetPackageName());
            sb.append(NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
            sb.append(this.targetPackageVersion);
            sb.append(ShaderAssembler.NEWLINE);
            sb.append("  functionIdentifier : ");
            sb.append(this.requestInternal.getClientRequest().getFunctionIdentifier());
            sb.append(ShaderAssembler.NEWLINE);
            sb.append("  params :  ");
            sb.append(toJson(this.requestInternal.getClientRequest().getParameters(), z));
            sb.append(ShaderAssembler.NEWLINE);
            sb.append("  result :  ");
            sb.append(getResult(z));
            sb.append(ShaderAssembler.NEWLINE);
            sb.append("  resultCode : ");
            sb.append(getResultCode());
            sb.append(ShaderAssembler.NEWLINE);
        } catch (Exception e) {
            Slog.e(TAG, "toFullString : ", e);
            sb.append("exception");
        }
        return sb.toString();
    }

    public String toSummaryString() {
        return NavigationBarInflaterView.SIZE_MOD_START + this.TIME_FORMATTER.format(this.invocationTime) + " - " + this.TIME_FORMATTER.format(this.returnTime) + "][duration :" + getDuration() + "ms]\n  callingPackage : " + getCallingPackage() + NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER + this.callingPackageVersion + "\n  targetPackage : " + getTargetPackageName() + NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER + this.targetPackageVersion + "\n  functionIdentifier : " + this.requestInternal.getClientRequest().getFunctionIdentifier() + "\n  resultCode : " + getResultCode() + ShaderAssembler.NEWLINE;
    }
}
