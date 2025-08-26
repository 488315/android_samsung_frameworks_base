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
import javax.sip.InvalidArgumentException;

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

    /* JADX WARN: Removed duplicated region for block: B:22:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0119  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void attachHeader(SIPHeader sIPHeader) {
        SIPHeader sIPHeader2;
        String lowerCase;
        String lowerCase2;
        SIPHeaderList sIPHeaderList;
        SIPHeaderList sIPHeaderList2;
        Hashtable hashtable = ListMap.headerListTable;
        if (sIPHeader instanceof SIPHeaderList) {
            sIPHeader2 = sIPHeader;
        } else if (ListMap.headerListTable.get(sIPHeader.getClass()) != null && !SIPHeaderList.class.isAssignableFrom(sIPHeader.getClass())) {
            if (!ListMap.initialized) {
                ListMap.initializeListMap();
            }
            try {
                SIPHeaderList sIPHeaderList3 = (SIPHeaderList) ((Class) ListMap.headerListTable.get(sIPHeader.getClass())).newInstance();
                sIPHeaderList3.setHeaderName(sIPHeader.getName());
                sIPHeaderList2 = sIPHeaderList3;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                sIPHeaderList2 = null;
                sIPHeaderList2.add(sIPHeader);
                sIPHeader2 = sIPHeaderList2;
                lowerCase = SIPHeaderNamesCache.toLowerCase(sIPHeader2.getName());
                if (!this.nameTable.containsKey(lowerCase)) {
                }
                lowerCase2 = SIPHeaderNamesCache.toLowerCase(sIPHeader.getName());
                if (lowerCase2 == null) {
                }
            } catch (InstantiationException e2) {
                e2.printStackTrace();
                sIPHeaderList2 = null;
                sIPHeaderList2.add(sIPHeader);
                sIPHeader2 = sIPHeaderList2;
                lowerCase = SIPHeaderNamesCache.toLowerCase(sIPHeader2.getName());
                if (!this.nameTable.containsKey(lowerCase)) {
                }
                lowerCase2 = SIPHeaderNamesCache.toLowerCase(sIPHeader.getName());
                if (lowerCase2 == null) {
                }
            }
            sIPHeaderList2.add(sIPHeader);
            sIPHeader2 = sIPHeaderList2;
        }
        lowerCase = SIPHeaderNamesCache.toLowerCase(sIPHeader2.getName());
        if (!this.nameTable.containsKey(lowerCase) && !(sIPHeader2 instanceof SIPHeaderList)) {
            if (sIPHeader2 instanceof ContentLength) {
                try {
                    this.contentLengthHeader.setContentLength(((ContentLength) sIPHeader2).getContentLength());
                    return;
                } catch (InvalidArgumentException unused) {
                    return;
                }
            }
            return;
        }
        lowerCase2 = SIPHeaderNamesCache.toLowerCase(sIPHeader.getName());
        if (lowerCase2 == null) {
            throw new NullPointerException("bad name");
        }
        SIPHeader sIPHeaderMo3439getFirst = this.nameTable.get(lowerCase2);
        if (sIPHeaderMo3439getFirst instanceof SIPHeaderList) {
            sIPHeaderMo3439getFirst = ((SIPHeaderList) sIPHeaderMo3439getFirst).mo3439getFirst();
        }
        if (sIPHeaderMo3439getFirst != null) {
            Iterator<SIPHeader> it = this.headers.iterator();
            while (it.hasNext()) {
                if (it.next().equals(sIPHeaderMo3439getFirst)) {
                    it.remove();
                }
            }
        }
        if (!this.nameTable.containsKey(lowerCase)) {
            this.nameTable.put(lowerCase, sIPHeader2);
            this.headers.add(sIPHeader2);
        } else if (!(sIPHeader2 instanceof SIPHeaderList) || (sIPHeaderList = (SIPHeaderList) this.nameTable.get(lowerCase)) == null) {
            this.nameTable.put(lowerCase, sIPHeader2);
        } else {
            sIPHeaderList.addAll((SIPHeaderList) sIPHeader2);
        }
        if (sIPHeader2 instanceof From) {
            this.fromHeader = (From) sIPHeader2;
            return;
        }
        if (sIPHeader2 instanceof ContentLength) {
            this.contentLengthHeader = (ContentLength) sIPHeader2;
            return;
        }
        if (sIPHeader2 instanceof To) {
            this.toHeader = (To) sIPHeader2;
            return;
        }
        if (sIPHeader2 instanceof CSeq) {
            this.cSeqHeader = (CSeq) sIPHeader2;
        } else if (sIPHeader2 instanceof CallID) {
            this.callIdHeader = (CallID) sIPHeader2;
        } else if (sIPHeader2 instanceof MaxForwards) {
            this.maxForwardsHeader = (MaxForwards) sIPHeader2;
        }
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
                if (!(sIPHeader instanceof SIPHeaderList) || !((SIPHeaderList) sIPHeader).isEmpty()) {
                    sIPMessage.attachHeader(sIPHeader);
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
        String parameter;
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
            String str = this.messageContent;
            if (str != null || this.messageContentBytes != null) {
                if (str == null) {
                    try {
                        byte[] bArr = this.messageContentBytes;
                        String str2 = CONTENT_TYPE_LOWERCASE;
                        if (str2 == null) {
                            throw new NullPointerException("bad name");
                        }
                        SIPHeader sIPHeaderMo3439getFirst = this.nameTable.get(str2);
                        if (sIPHeaderMo3439getFirst instanceof SIPHeaderList) {
                            sIPHeaderMo3439getFirst = ((SIPHeaderList) sIPHeaderMo3439getFirst).mo3439getFirst();
                        }
                        ContentType contentType = (ContentType) sIPHeaderMo3439getFirst;
                        if (contentType == null || (parameter = contentType.getParameter("charset")) == null) {
                            parameter = this.contentEncodingCharset;
                        }
                        str = new String(bArr, parameter);
                    } catch (UnsupportedEncodingException e) {
                        InternalErrorHandler.handleException(e);
                        throw null;
                    }
                }
                stringBuffer.append(str);
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
