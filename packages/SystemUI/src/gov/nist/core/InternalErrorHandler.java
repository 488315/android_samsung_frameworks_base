package gov.nist.core;

/* loaded from: classes4.dex */
public class InternalErrorHandler {
    public static void handleException(Exception exc) {
        System.err.println("Unexpected internal error FIXME!! " + exc.getMessage());
        exc.printStackTrace();
        throw new RuntimeException("Unexpected internal error FIXME!! " + exc.getMessage(), exc);
    }
}
