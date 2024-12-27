package android.content.pm.parsing.result;

public interface ParseResult<ResultType> {
    int getErrorCode();

    String getErrorMessage();

    Exception getException();

    String getPackageNameForAudit();

    ResultType getResult();

    boolean isError();

    boolean isSuccess();

    void setPackageNameForAudit(String str);
}
