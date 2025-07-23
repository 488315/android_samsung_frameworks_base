package android.app.backup;

import android.annotation.SystemApi;
import android.app.AppOpsManager$$ExternalSyntheticLambda4;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArrayMap;
import android.util.Slog;
import com.android.server.backup.Flags;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class BackupRestoreEventLogger {
    public static final int DATA_TYPES_ALLOWED = 150;
    private static final String TAG = "BackupRestoreEventLogger";
    private final MessageDigest mHashDigest;
    private final int mOperationType;
    private final Map<String, DataTypeResult> mResults = new HashMap();

    @Retention(RetentionPolicy.SOURCE)
    public @interface BackupRestoreDataType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface BackupRestoreError {
    }

    public BackupRestoreEventLogger(int i) {
        MessageDigest messageDigest;
        this.mOperationType = i;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            Slog.w("Couldn't create MessageDigest for hash computation", e);
            messageDigest = null;
        }
        this.mHashDigest = messageDigest;
    }

    public void logItemsBackedUp(String str, int i) {
        logSuccess(0, str, i);
    }

    public void logItemsBackupFailed(String str, int i, String str2) {
        logFailure(0, str, i, str2);
    }

    public void logBackupMetadata(String str, String str2) {
        logMetaData(0, str, str2);
    }

    public void logItemsRestored(String str, int i) {
        logSuccess(1, str, i);
    }

    public void logItemsRestoreFailed(String str, int i, String str2) {
        logFailure(1, str, i, str2);
    }

    public void logRestoreMetadata(String str, String str2) {
        logMetaData(1, str, str2);
    }

    public List<DataTypeResult> getLoggingResults() {
        return new ArrayList(this.mResults.values());
    }

    public int getOperationType() {
        return this.mOperationType;
    }

    public void clearData() {
        this.mResults.clear();
    }

    private void logSuccess(int i, String str, int i2) {
        DataTypeResult dataTypeResult = getDataTypeResult(i, str);
        if (dataTypeResult == null) {
            return;
        }
        dataTypeResult.mSuccessCount += i2;
        this.mResults.put(str, dataTypeResult);
    }

    private void logFailure(int i, String str, int i2, String str2) {
        DataTypeResult dataTypeResult = getDataTypeResult(i, str);
        if (dataTypeResult == null) {
            return;
        }
        dataTypeResult.mFailCount += i2;
        if (str2 != null) {
            dataTypeResult.mErrors.merge(str2, Integer.valueOf(i2), new AppOpsManager$$ExternalSyntheticLambda4());
        }
    }

    private void logMetaData(int i, String str, String str2) {
        DataTypeResult dataTypeResult;
        if (this.mHashDigest == null || (dataTypeResult = getDataTypeResult(i, str)) == null) {
            return;
        }
        dataTypeResult.mMetadataHash = getMetaDataHash(str2);
    }

    private DataTypeResult getDataTypeResult(int i, String str) {
        if (i != this.mOperationType) {
            Slog.d(TAG, "Operation type mismatch: logger created for " + this.mOperationType + ", trying to log for " + i);
            return null;
        }
        if (!this.mResults.containsKey(str)) {
            if (this.mResults.keySet().size() == getDataTypesAllowed()) {
                Slog.d(TAG, "Logger is full, ignoring new data type");
                return null;
            }
            this.mResults.put(str, new DataTypeResult(str));
        }
        return this.mResults.get(str);
    }

    private byte[] getMetaDataHash(String str) {
        return this.mHashDigest.digest(str.getBytes(StandardCharsets.UTF_8));
    }

    private int getDataTypesAllowed() {
        return Flags.enableIncreaseDatatypesForAgentLogging() ? 150 : 15;
    }

    public static String toString(DataTypeResult dataTypeResult) {
        Objects.requireNonNull(dataTypeResult, "result cannot be null");
        StringBuilder sb = new StringBuilder("type=");
        sb.append(dataTypeResult.mDataType);
        sb.append(", successCount=");
        sb.append(dataTypeResult.mSuccessCount);
        sb.append(", failCount=");
        sb.append(dataTypeResult.mFailCount);
        if (!dataTypeResult.mErrors.isEmpty()) {
            sb.append(", errors=");
            sb.append(dataTypeResult.mErrors);
        }
        if (dataTypeResult.mMetadataHash != null) {
            sb.append(", metadataHash=");
            sb.append(Arrays.toString(dataTypeResult.mMetadataHash));
        }
        return sb.toString();
    }

    public static final class DataTypeResult implements Parcelable {
        public static final Parcelable.Creator<DataTypeResult> CREATOR = new Parcelable.Creator<DataTypeResult>() { // from class: android.app.backup.BackupRestoreEventLogger.DataTypeResult.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public DataTypeResult createFromParcel(Parcel parcel) {
                String readString = parcel.readString();
                int readInt = parcel.readInt();
                int readInt2 = parcel.readInt();
                ArrayMap arrayMap = new ArrayMap();
                Bundle readBundle = parcel.readBundle(getClass().getClassLoader());
                for (String str : readBundle.keySet()) {
                    arrayMap.put(str, Integer.valueOf(readBundle.getInt(str)));
                }
                byte[] createByteArray = parcel.createByteArray();
                DataTypeResult dataTypeResult = new DataTypeResult(readString);
                dataTypeResult.mSuccessCount = readInt;
                dataTypeResult.mFailCount = readInt2;
                dataTypeResult.mErrors.putAll(arrayMap);
                dataTypeResult.mMetadataHash = createByteArray;
                return dataTypeResult;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public DataTypeResult[] newArray(int i) {
                return new DataTypeResult[i];
            }
        };
        private final String mDataType;
        private final Map<String, Integer> mErrors = new HashMap();
        private int mFailCount;
        private byte[] mMetadataHash;
        private int mSuccessCount;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public DataTypeResult(String str) {
            this.mDataType = str;
        }

        public String getDataType() {
            return this.mDataType;
        }

        public int getSuccessCount() {
            return this.mSuccessCount;
        }

        public int getFailCount() {
            return this.mFailCount;
        }

        public Map<String, Integer> getErrors() {
            return this.mErrors;
        }

        public byte[] getMetadataHash() {
            return this.mMetadataHash;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.mDataType);
            parcel.writeInt(this.mSuccessCount);
            parcel.writeInt(this.mFailCount);
            Bundle bundle = new Bundle();
            for (Map.Entry<String, Integer> entry : this.mErrors.entrySet()) {
                bundle.putInt(entry.getKey(), entry.getValue().intValue());
            }
            parcel.writeBundle(bundle);
            parcel.writeByteArray(this.mMetadataHash);
        }
    }
}
