package javax.sip;

import java.util.HashMap;

/* loaded from: classes4.dex */
public class SipFactory {
    public static SipFactory sSipFactory;

    private SipFactory() {
        new HashMap();
    }

    public static synchronized SipFactory getInstance() {
        try {
            if (sSipFactory == null) {
                sSipFactory = new SipFactory();
            }
        } catch (Throwable th) {
            throw th;
        }
        return sSipFactory;
    }
}
