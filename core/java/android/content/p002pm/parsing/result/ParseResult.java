package android.content.p002pm.parsing.result;

/* loaded from: classes.dex */
public interface ParseResult<ResultType> {
    int getErrorCode();

    String getErrorMessage();

    Exception getException();

    ResultType getResult();

    boolean isError();

    boolean isSuccess();
}
