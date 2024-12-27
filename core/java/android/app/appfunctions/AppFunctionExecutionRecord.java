package android.app.appfunctions;

import android.app.appsearch.GenericDocument;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.provider.Settings;
import android.util.Slog;

import com.samsung.android.share.SemShareConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Objects;

public class AppFunctionExecutionRecord {
    AppFunctionException error;
    ExecuteAppFunctionAidlRequest requestInternal;
    ExecuteAppFunctionResponse result;
    LocalDateTime returnTime;
    private static final String TAG = AppFunctionExecutionRecord.class.getSimpleName();
    private static final Integer JSON_INDENTATION = 4;
    private static final Integer MAX_STRING_MASKING_NUMBER = 30;
    private final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    LocalDateTime invocationTime = LocalDateTime.now();

    public AppFunctionExecutionRecord(ExecuteAppFunctionAidlRequest requestInternal) {
        this.requestInternal = requestInternal;
    }

    public void setResult(ExecuteAppFunctionResponse result) {
        this.returnTime = LocalDateTime.now();
        this.result = result;
    }

    public void setError(AppFunctionException error) {
        this.returnTime = LocalDateTime.now();
        this.error = error;
    }

    public String getCallingPackage() {
        return this.requestInternal.getCallingPackage();
    }

    public String getTargetPackageName() {
        return this.requestInternal.getClientRequest().getTargetPackageName();
    }

    public String getFunctionIdentifier() {
        return this.requestInternal.getClientRequest().getFunctionIdentifier();
    }

    public String getResultCode() {
        if (this.error == null) {
            return "0";
        }
        return String.valueOf(this.error.getErrorCode());
    }

    public LocalDateTime getInvocationTime() {
        return this.invocationTime;
    }

    public long getDuration() {
        Duration duration = Duration.between(this.invocationTime, this.returnTime);
        return duration.toMillis();
    }

    String getResult(boolean needToMask) {
        if (this.result == null) {
            return "{\n}";
        }
        try {
            return toJson(this.result.getResultDocument(), needToMask);
        } catch (JSONException e) {
            return "{\n}";
        }
    }

    void appendPropertyToJson(
            JSONObject jsonObject, String propertyName, Object propertyValue, boolean needToMask)
            throws JSONException {
        if (propertyValue instanceof GenericDocument[]) {
            JSONArray jsonArray = new JSONArray();
            GenericDocument[] documentValues = (GenericDocument[]) propertyValue;
            for (GenericDocument documentValue : documentValues) {
                JSONObject nestedDocumentJson = new JSONObject();
                appendGenericDocumentToJson(nestedDocumentJson, documentValue, needToMask);
                jsonArray.put(nestedDocumentJson);
            }
            jsonObject.put(propertyName, jsonArray);
            return;
        }
        int propertyArrLength = Array.getLength(propertyValue);
        JSONArray jsonArray2 = new JSONArray();
        for (int i = 0; i < propertyArrLength; i++) {
            Object propertyElement = Array.get(propertyValue, i);
            if (needToMask) {
                String maskedPropertyElement = maskData(propertyElement);
                jsonArray2.put(maskedPropertyElement);
            } else {
                jsonArray2.put(propertyElement);
            }
        }
        jsonObject.put(propertyName, jsonArray2);
    }

    void appendGenericDocumentToJson(
            JSONObject jsonObject, GenericDocument document, boolean needToMask)
            throws JSONException {
        jsonObject.put(Settings.EXTRA_NAMESPACE, document.getNamespace());
        jsonObject.put("id", document.getId());
        jsonObject.put(SemShareConstants.SHARE_STAR_KEY_SCORE, document.getScore());
        jsonObject.put("schemaType", document.getSchemaType());
        jsonObject.put("creationTimestampMillis", document.getCreationTimestampMillis());
        jsonObject.put("timeToLiveMillis", document.getTtlMillis());
        String[] sortedProperties = (String[]) document.getPropertyNames().toArray(new String[0]);
        Arrays.sort(sortedProperties);
        for (String sortedProperty : sortedProperties) {
            Object propertyValue = Objects.requireNonNull(document.getProperty(sortedProperty));
            appendPropertyToJson(jsonObject, sortedProperty, propertyValue, needToMask);
        }
    }

    String toJson(GenericDocument document, boolean needToMask) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        try {
            appendGenericDocumentToJson(jsonObject, document, needToMask);
        } catch (JSONException e) {
            Slog.e(TAG, "toJson JSONException : ", e);
        } catch (Exception e2) {
            Slog.e(TAG, "toJson : ", e2);
        }
        return jsonObject.toString(JSON_INDENTATION.intValue());
    }

    public String maskData(Object data) {
        int length;
        if (data == null) {
            return "null";
        }
        if (data instanceof String) {
            length = ((String) data).length();
        } else if (data instanceof Number) {
            length = data.toString().length();
        } else if (data instanceof byte[]) {
            length = ((byte[]) data).length;
        } else {
            length = data.toString().length();
        }
        if (length > MAX_STRING_MASKING_NUMBER.intValue()) {
            return "*" + length + "~";
        }
        return "*".repeat(length);
    }

    public String toFullString(boolean needToMask) {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append(NavigationBarInflaterView.SIZE_MOD_START)
                    .append(this.TIME_FORMATTER.format(this.invocationTime))
                    .append(" - ")
                    .append(this.TIME_FORMATTER.format(this.returnTime))
                    .append("][duration :")
                    .append(getDuration())
                    .append("ms]\n")
                    .append("  callingPackage : ")
                    .append(this.requestInternal.getCallingPackage())
                    .append("\n")
                    .append("  targetPackage : ")
                    .append(this.requestInternal.getClientRequest().getTargetPackageName())
                    .append("\n")
                    .append("  functionIdentifier : ")
                    .append(this.requestInternal.getClientRequest().getFunctionIdentifier())
                    .append("\n")
                    .append("  params :  ")
                    .append(
                            toJson(
                                    this.requestInternal.getClientRequest().getParameters(),
                                    needToMask))
                    .append("\n")
                    .append("  result :  ")
                    .append(getResult(needToMask))
                    .append("\n")
                    .append("  resultCode : ")
                    .append(getResultCode())
                    .append("\n");
        } catch (Exception e) {
            Slog.e(TAG, "toFullString : ", e);
            sb.append("exception");
        }
        return sb.toString();
    }

    public String toSummaryString() {
        StringBuilder sb = new StringBuilder();
        sb.append(NavigationBarInflaterView.SIZE_MOD_START)
                .append(this.TIME_FORMATTER.format(this.invocationTime))
                .append(" - ")
                .append(this.TIME_FORMATTER.format(this.returnTime))
                .append("][duration :")
                .append(getDuration())
                .append("ms]\n")
                .append("  callingPackage : ")
                .append(this.requestInternal.getCallingPackage())
                .append("\n")
                .append("  targetPackage : ")
                .append(this.requestInternal.getClientRequest().getTargetPackageName())
                .append("\n")
                .append("  functionIdentifier : ")
                .append(this.requestInternal.getClientRequest().getFunctionIdentifier())
                .append("\n")
                .append("  resultCode : ")
                .append(getResultCode())
                .append("\n");
        return sb.toString();
    }
}
