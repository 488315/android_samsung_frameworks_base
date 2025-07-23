package javax.sip;

import java.util.HashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class SipFactory {
    public static SipFactory sSipFactory;

    private SipFactory() {
        new HashMap();
    }

    public static synchronized SipFactory getInstance() {
        SipFactory sipFactory;
        synchronized (SipFactory.class) {
            try {
                if (sSipFactory == null) {
                    sSipFactory = new SipFactory();
                }
                sipFactory = sSipFactory;
            } catch (Throwable th) {
                throw th;
            }
        }
        return sipFactory;
    }
}
