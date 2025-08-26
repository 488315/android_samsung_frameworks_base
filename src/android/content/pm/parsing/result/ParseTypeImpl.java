package android.content.pm.parsing.result;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.parsing.result.ParseInput;
import android.os.ServiceManager;
import android.util.ArrayMap;
import android.util.Slog;
import com.android.internal.compat.IPlatformCompat;
import com.android.internal.util.CollectionUtils;

/* loaded from: classes.dex */
public class ParseTypeImpl implements ParseInput, ParseResult<Object> {
    public static final boolean DEBUG_FILL_STACK_TRACE = false;
    public static final boolean DEBUG_LOG_ON_ERROR = false;
    public static final boolean DEBUG_THROW_ALL_ERRORS = false;
    private static final String TAG = "ParseTypeImpl";
    private final ParseInput.Callback mCallback;
    private String mErrorMessage;
    private Exception mException;
    private String mPackageName;
    private String mPackageNameForAudit;
    private Object mResult;
    private int mErrorCode = 1;
    private ArrayMap<Long, String> mDeferredErrors = null;
    private int mTargetSdkVersion = -1;

    public static ParseTypeImpl forParsingWithoutPlatformCompat() {
        return new ParseTypeImpl(new ParseInput.Callback() { // from class: android.content.pm.parsing.result.ParseTypeImpl$$ExternalSyntheticLambda1
            @Override // android.content.pm.parsing.result.ParseInput.Callback
            public final boolean isChangeEnabled(long j, String str, int i) {
                return ParseTypeImpl.lambda$forParsingWithoutPlatformCompat$0(j, str, i);
            }
        });
    }

    static /* synthetic */ boolean lambda$forParsingWithoutPlatformCompat$0(long j, String str, int i) {
        int targetSdkForChange = ParseInput.DeferredError.getTargetSdkForChange(j);
        return targetSdkForChange != -1 && i > targetSdkForChange;
    }

    public static ParseTypeImpl forDefaultParsing() {
        final IPlatformCompat iPlatformCompatAsInterface = IPlatformCompat.Stub.asInterface(ServiceManager.getService(Context.PLATFORM_COMPAT_SERVICE));
        return new ParseTypeImpl(new ParseInput.Callback() { // from class: android.content.pm.parsing.result.ParseTypeImpl$$ExternalSyntheticLambda0
            @Override // android.content.pm.parsing.result.ParseInput.Callback
            public final boolean isChangeEnabled(long j, String str, int i) {
                return ParseTypeImpl.lambda$forDefaultParsing$1(iPlatformCompatAsInterface, j, str, i);
            }
        });
    }

    static /* synthetic */ boolean lambda$forDefaultParsing$1(IPlatformCompat iPlatformCompat, long j, String str, int i) {
        ApplicationInfo applicationInfo = new ApplicationInfo();
        applicationInfo.packageName = str;
        applicationInfo.targetSdkVersion = i;
        try {
            return iPlatformCompat.isChangeEnabled(j, applicationInfo);
        } catch (Exception e) {
            Slog.wtf(TAG, "IPlatformCompat query failed", e);
            return true;
        }
    }

    public ParseTypeImpl(ParseInput.Callback callback) {
        this.mCallback = callback;
    }

    public ParseInput reset() {
        this.mResult = null;
        this.mErrorCode = 1;
        this.mErrorMessage = null;
        this.mException = null;
        this.mPackageNameForAudit = null;
        ArrayMap<Long, String> arrayMap = this.mDeferredErrors;
        if (arrayMap != null) {
            arrayMap.erase();
        }
        this.mTargetSdkVersion = -1;
        return this;
    }

    @Override // android.content.pm.parsing.result.ParseInput
    public <ResultType> ParseResult<ResultType> success(ResultType resulttype) {
        if (this.mErrorCode != 1) {
            Slog.wtf(TAG, "Cannot set to success after set to error, was " + this.mErrorMessage, this.mException);
        }
        this.mResult = resulttype;
        return this;
    }

    @Override // android.content.pm.parsing.result.ParseInput
    public ParseResult<?> deferError(String str, long j) {
        if (this.mTargetSdkVersion != -1) {
            ArrayMap<Long, String> arrayMap = this.mDeferredErrors;
            if (arrayMap != null && arrayMap.containsKey(Long.valueOf(j))) {
                return success(null);
            }
            if (this.mCallback.isChangeEnabled(j, this.mPackageName, this.mTargetSdkVersion)) {
                return error(str);
            }
            if (this.mDeferredErrors == null) {
                this.mDeferredErrors = new ArrayMap<>();
            }
            this.mDeferredErrors.put(Long.valueOf(j), null);
            return success(null);
        }
        if (this.mDeferredErrors == null) {
            this.mDeferredErrors = new ArrayMap<>();
        }
        this.mDeferredErrors.putIfAbsent(Long.valueOf(j), str);
        return success(null);
    }

    @Override // android.content.pm.parsing.result.ParseInput
    public ParseResult<?> enableDeferredError(String str, int i) {
        this.mPackageName = str;
        this.mTargetSdkVersion = i;
        int size = CollectionUtils.size(this.mDeferredErrors);
        while (true) {
            size--;
            if (size >= 0) {
                long jLongValue = this.mDeferredErrors.keyAt(size).longValue();
                String strValueAt = this.mDeferredErrors.valueAt(size);
                if (this.mCallback.isChangeEnabled(jLongValue, this.mPackageName, this.mTargetSdkVersion)) {
                    return error(strValueAt);
                }
                this.mDeferredErrors.setValueAt(size, null);
            } else {
                return success(null);
            }
        }
    }

    @Override // android.content.pm.parsing.result.ParseInput
    public <ResultType> ParseResult<ResultType> skip(String str) {
        return error(PackageManager.INSTALL_PARSE_FAILED_SKIPPED, str);
    }

    @Override // android.content.pm.parsing.result.ParseInput
    public <ResultType> ParseResult<ResultType> error(int i) {
        return error(i, null);
    }

    @Override // android.content.pm.parsing.result.ParseInput
    public <ResultType> ParseResult<ResultType> error(String str) {
        return error(-108, str);
    }

    @Override // android.content.pm.parsing.result.ParseInput
    public <ResultType> ParseResult<ResultType> error(int i, String str) {
        return error(i, str, null);
    }

    @Override // android.content.pm.parsing.result.ParseInput
    public <ResultType> ParseResult<ResultType> error(ParseResult<?> parseResult) {
        return error(parseResult.getErrorCode(), parseResult.getErrorMessage(), parseResult.getException());
    }

    @Override // android.content.pm.parsing.result.ParseInput
    public <ResultType> ParseResult<ResultType> error(int i, String str, Exception exc) {
        this.mErrorCode = i;
        this.mErrorMessage = str;
        this.mException = exc;
        return this;
    }

    @Override // android.content.pm.parsing.result.ParseResult
    public Object getResult() {
        return this.mResult;
    }

    @Override // android.content.pm.parsing.result.ParseResult
    public boolean isSuccess() {
        return this.mErrorCode == 1;
    }

    @Override // android.content.pm.parsing.result.ParseResult
    public boolean isError() {
        return !isSuccess();
    }

    @Override // android.content.pm.parsing.result.ParseResult
    public int getErrorCode() {
        return this.mErrorCode;
    }

    @Override // android.content.pm.parsing.result.ParseResult
    public String getErrorMessage() {
        return this.mErrorMessage;
    }

    @Override // android.content.pm.parsing.result.ParseResult
    public Exception getException() {
        return this.mException;
    }

    @Override // android.content.pm.parsing.result.ParseResult
    public String getPackageNameForAudit() {
        return this.mPackageNameForAudit;
    }

    @Override // android.content.pm.parsing.result.ParseInput, android.content.pm.parsing.result.ParseResult
    public void setPackageNameForAudit(String str) {
        this.mPackageNameForAudit = str;
    }
}
