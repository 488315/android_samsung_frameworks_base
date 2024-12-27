package android.content.pm.parsing.result;

/* loaded from: classes.dex */
public interface ParseInput {

    public interface Callback {
        boolean isChangeEnabled(long j, String str, int i);
    }

    ParseResult<?> deferError(String str, long j);

    ParseResult<?> enableDeferredError(String str, int i);

    <ResultType> ParseResult<ResultType> error(int i);

    <ResultType> ParseResult<ResultType> error(int i, String str);

    <ResultType> ParseResult<ResultType> error(int i, String str, Exception exc);

    <ResultType> ParseResult<ResultType> error(ParseResult<?> parseResult);

    <ResultType> ParseResult<ResultType> error(String str);

    void setPackageNameForAudit(String str);

    <ResultType> ParseResult<ResultType> skip(String str);

    <ResultType> ParseResult<ResultType> success(ResultType resulttype);

    public static final class DeferredError {
        public static final long EMPTY_INTENT_ACTION_CATEGORY = 151163173;
        public static final long MISSING_APP_TAG = 150776642;
        public static final long MISSING_EXPORTED_FLAG = 150232615;
        public static final long RESOURCES_ARSC_COMPRESSED = 132742131;

        public static int getTargetSdkForChange(long changeId) {
            if (changeId == MISSING_APP_TAG || changeId == EMPTY_INTENT_ACTION_CATEGORY || changeId == RESOURCES_ARSC_COMPRESSED) {
                return 29;
            }
            if (changeId == MISSING_EXPORTED_FLAG) {
                return 30;
            }
            return -1;
        }
    }
}
