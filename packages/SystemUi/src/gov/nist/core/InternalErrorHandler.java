package gov.nist.core;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class InternalErrorHandler {
    public static void handleException(Exception exc) {
        System.err.println("Unexpected internal error FIXME!! " + exc.getMessage());
        exc.printStackTrace();
        throw new RuntimeException("Unexpected internal error FIXME!! " + exc.getMessage(), exc);
    }
}
