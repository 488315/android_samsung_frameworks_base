package gov.nist.javax.sip.message;

import gov.nist.core.GenericObject;
import gov.nist.core.InternalErrorHandler;
import gov.nist.javax.sip.header.CSeq;
import gov.nist.javax.sip.header.CallID;
import gov.nist.javax.sip.header.ContentLength;
import gov.nist.javax.sip.header.ContentType;
import gov.nist.javax.sip.header.From;
import gov.nist.javax.sip.header.MaxForwards;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.SIPHeaderList;
import gov.nist.javax.sip.header.SIPHeaderNamesCache;
import gov.nist.javax.sip.header.To;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class SIPMessage extends MessageObject implements Cloneable, Serializable {
    public static final String CONTENT_TYPE_LOWERCASE = SIPHeaderNamesCache.toLowerCase("Content-Type");
    protected Object applicationData;
    protected CSeq cSeqHeader;
    protected CallID callIdHeader;
    protected ContentLength contentLengthHeader;
    protected From fromHeader;
    protected MaxForwards maxForwardsHeader;
    private String messageContent;
    private byte[] messageContentBytes;
    private Object messageContentObject;
    protected boolean nullRequest;
    protected int size;
    protected To toHeader;
    private String contentEncodingCharset = "UTF-8";
    protected LinkedList<String> unrecognizedHeaders = new LinkedList<>();
    protected ConcurrentLinkedQueue<SIPHeader> headers = new ConcurrentLinkedQueue<>();
    private Hashtable<String, SIPHeader> nameTable = new Hashtable<>();

    static {
        SIPHeaderNamesCache.toLowerCase("Error-Info");
        SIPHeaderNamesCache.toLowerCase("Contact");
        SIPHeaderNamesCache.toLowerCase("Via");
        SIPHeaderNamesCache.toLowerCase("Authorization");
        SIPHeaderNamesCache.toLowerCase("Route");
        SIPHeaderNamesCache.toLowerCase("Record-Route");
        SIPHeaderNamesCache.toLowerCase("Content-Disposition");
        SIPHeaderNamesCache.toLowerCase("Content-Encoding");
        SIPHeaderNamesCache.toLowerCase("Content-Language");
        SIPHeaderNamesCache.toLowerCase("Expires");
    }

    public SIPMessage() {
        try {
            attachHeader(new ContentLength(0));
        } catch (Exception unused) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0119  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void attachHeader(gov.nist.javax.sip.header.SIPHeader r5) {
        /*
            Method dump skipped, instructions count: 289
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: gov.nist.javax.sip.message.SIPMessage.attachHeader(gov.nist.javax.sip.header.SIPHeader):void");
    }

    @Override // gov.nist.core.GenericObject
    public Object clone() {
        SIPMessage sIPMessage = (SIPMessage) super.clone();
        sIPMessage.nameTable = new Hashtable<>();
        sIPMessage.fromHeader = null;
        sIPMessage.toHeader = null;
        sIPMessage.cSeqHeader = null;
        sIPMessage.callIdHeader = null;
        sIPMessage.contentLengthHeader = null;
        sIPMessage.maxForwardsHeader = null;
        if (this.headers != null) {
            sIPMessage.headers = new ConcurrentLinkedQueue<>();
            Iterator<SIPHeader> it = this.headers.iterator();
            while (it.hasNext()) {
                SIPHeader sIPHeader = (SIPHeader) it.next().clone();
                if (sIPHeader == null) {
                    throw new IllegalArgumentException("null header!");
                }
                try {
                    if (!(sIPHeader instanceof SIPHeaderList) || !((SIPHeaderList) sIPHeader).isEmpty()) {
                        sIPMessage.attachHeader(sIPHeader);
                    }
                } catch (SIPDuplicateHeaderException unused) {
                }
            }
        }
        byte[] bArr = this.messageContentBytes;
        if (bArr != null) {
            sIPMessage.messageContentBytes = (byte[]) bArr.clone();
        }
        Object obj = this.messageContentObject;
        if (obj != null) {
            sIPMessage.messageContentObject = GenericObject.makeClone(obj);
        }
        sIPMessage.unrecognizedHeaders = this.unrecognizedHeaders;
        return sIPMessage;
    }

    @Override // gov.nist.core.GenericObject
    public String encode() {
        String str;
        StringBuffer stringBuffer = new StringBuffer();
        Iterator<SIPHeader> it = this.headers.iterator();
        while (it.hasNext()) {
            SIPHeader next = it.next();
            if (!(next instanceof ContentLength)) {
                stringBuffer.append(next.encode());
            }
        }
        Iterator<String> it2 = this.unrecognizedHeaders.iterator();
        while (it2.hasNext()) {
            stringBuffer.append(it2.next());
            stringBuffer.append("\r\n");
        }
        stringBuffer.append(this.contentLengthHeader.encode());
        stringBuffer.append("\r\n");
        Object obj = this.messageContentObject;
        if (obj != null) {
            stringBuffer.append(obj.toString());
        } else {
            String str2 = this.messageContent;
            if (str2 != null || this.messageContentBytes != null) {
                if (str2 == null) {
                    try {
                        byte[] bArr = this.messageContentBytes;
                        String str3 = CONTENT_TYPE_LOWERCASE;
                        if (str3 == null) {
                            throw new NullPointerException("bad name");
                        }
                        SIPHeader sIPHeader = this.nameTable.get(str3);
                        if (sIPHeader instanceof SIPHeaderList) {
                            sIPHeader = ((SIPHeaderList) sIPHeader).mo3419getFirst();
                        }
                        ContentType contentType = (ContentType) sIPHeader;
                        if (contentType != null) {
                            str = contentType.getParameter("charset");
                            if (str == null) {
                                str = this.contentEncodingCharset;
                            }
                        } else {
                            str = this.contentEncodingCharset;
                        }
                        str2 = new String(bArr, str);
                    } catch (UnsupportedEncodingException e) {
                        InternalErrorHandler.handleException(e);
                        throw null;
                    }
                }
                stringBuffer.append(str2);
            }
        }
        return stringBuffer.toString();
    }

    @Override // gov.nist.core.GenericObject
    public boolean equals(Object obj) {
        if (!obj.getClass().equals(getClass())) {
            return false;
        }
        SIPMessage sIPMessage = (SIPMessage) obj;
        if (this.nameTable.size() != sIPMessage.nameTable.size()) {
            return false;
        }
        for (SIPHeader sIPHeader : this.nameTable.values()) {
            SIPHeader sIPHeader2 = sIPMessage.nameTable.get(SIPHeaderNamesCache.toLowerCase(sIPHeader.getName()));
            if (sIPHeader2 == null || !sIPHeader2.equals(sIPHeader)) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        CallID callID = this.callIdHeader;
        if (callID != null) {
            return callID.encodeBody().hashCode();
        }
        throw new RuntimeException("Invalid message! Cannot compute hashcode! call-id header is missing !");
    }
}
