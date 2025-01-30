package javax.sip;

import java.util.HashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class SipFactory {
    public static SipFactory sSipFactory;

    private SipFactory() {
        new HashMap();
    }

    public static synchronized SipFactory getInstance() {
        SipFactory sipFactory;
        synchronized (SipFactory.class) {
            if (sSipFactory == null) {
                sSipFactory = new SipFactory();
            }
            sipFactory = sSipFactory;
        }
        return sipFactory;
    }
}
