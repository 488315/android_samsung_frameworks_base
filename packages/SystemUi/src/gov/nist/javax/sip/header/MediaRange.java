package gov.nist.javax.sip.header;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class MediaRange extends SIPObject {
    private static final long serialVersionUID = -6297125815438079210L;
    protected String subtype;
    protected String type;

    @Override // gov.nist.core.GenericObject
    public final String encode() {
        StringBuffer stringBuffer = new StringBuffer();
        encode(stringBuffer);
        return stringBuffer.toString();
    }

    @Override // gov.nist.javax.sip.header.SIPObject, gov.nist.core.GenericObject
    public final StringBuffer encode(StringBuffer stringBuffer) {
        stringBuffer.append(this.type);
        stringBuffer.append("/");
        stringBuffer.append(this.subtype);
        return stringBuffer;
    }
}
