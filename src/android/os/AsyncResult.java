package android.os;

/* loaded from: classes3.dex */
public class AsyncResult {
    public Throwable exception;
    public Object result;
    public Object userObj;

    public static AsyncResult forMessage(Message message, Object obj, Throwable th) {
        AsyncResult asyncResult = new AsyncResult(message.obj, obj, th);
        message.obj = asyncResult;
        return asyncResult;
    }

    public static AsyncResult forMessage(Message message) {
        AsyncResult asyncResult = new AsyncResult(message.obj, null, null);
        message.obj = asyncResult;
        return asyncResult;
    }

    public AsyncResult(Object obj, Object obj2, Throwable th) {
        this.userObj = obj;
        this.result = obj2;
        this.exception = th;
    }
}
