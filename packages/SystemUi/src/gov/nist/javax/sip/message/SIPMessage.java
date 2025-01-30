package gov.nist.javax.sip.message;

import gov.nist.core.GenericObject;
import gov.nist.core.InternalErrorHandler;
import gov.nist.javax.sip.header.C4830To;
import gov.nist.javax.sip.header.CSeq;
import gov.nist.javax.sip.header.CallID;
import gov.nist.javax.sip.header.ContentLength;
import gov.nist.javax.sip.header.ContentType;
import gov.nist.javax.sip.header.From;
import gov.nist.javax.sip.header.MaxForwards;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.SIPHeaderList;
import gov.nist.javax.sip.header.SIPHeaderNamesCache;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.sip.InvalidArgumentException;
import javax.sip.header.Header;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class SIPMessage extends MessageObject {
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
    protected C4830To toHeader;
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

    /* JADX WARN: Removed duplicated region for block: B:17:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0088  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00ac  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00db  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00e0  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00b7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void attachHeader(SIPHeader sIPHeader) {
        boolean z;
        SIPHeader sIPHeader2;
        String lowerCase;
        SIPHeader sIPHeader3;
        SIPHeaderList sIPHeaderList;
        Hashtable hashtable = ListMap.headerListTable;
        if (!(sIPHeader instanceof SIPHeaderList)) {
            if (ListMap.headerListTable.get(sIPHeader.getClass()) != null) {
                z = true;
                if (z || SIPHeaderList.class.isAssignableFrom(sIPHeader.getClass())) {
                    sIPHeader2 = sIPHeader;
                } else {
                    if (!ListMap.initialized) {
                        ListMap.initializeListMap();
                    }
                    try {
                        SIPHeaderList sIPHeaderList2 = (SIPHeaderList) ((Class) ListMap.headerListTable.get(sIPHeader.getClass())).newInstance();
                        sIPHeaderList2.setHeaderName(sIPHeader.getName());
                        sIPHeaderList = sIPHeaderList2;
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        sIPHeaderList = null;
                        sIPHeaderList.add(sIPHeader);
                        sIPHeader2 = sIPHeaderList;
                        lowerCase = SIPHeaderNamesCache.toLowerCase(sIPHeader2.getName());
                        if (!this.nameTable.containsKey(lowerCase)) {
                        }
                        sIPHeader3 = (SIPHeader) getHeaderLowerCase(SIPHeaderNamesCache.toLowerCase(sIPHeader.getName()));
                        if (sIPHeader3 != null) {
                        }
                        if (this.nameTable.containsKey(lowerCase)) {
                        }
                        if (!(sIPHeader2 instanceof From)) {
                        }
                    } catch (InstantiationException e2) {
                        e2.printStackTrace();
                        sIPHeaderList = null;
                        sIPHeaderList.add(sIPHeader);
                        sIPHeader2 = sIPHeaderList;
                        lowerCase = SIPHeaderNamesCache.toLowerCase(sIPHeader2.getName());
                        if (!this.nameTable.containsKey(lowerCase)) {
                        }
                        sIPHeader3 = (SIPHeader) getHeaderLowerCase(SIPHeaderNamesCache.toLowerCase(sIPHeader.getName()));
                        if (sIPHeader3 != null) {
                        }
                        if (this.nameTable.containsKey(lowerCase)) {
                        }
                        if (!(sIPHeader2 instanceof From)) {
                        }
                    }
                    sIPHeaderList.add(sIPHeader);
                    sIPHeader2 = sIPHeaderList;
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
                sIPHeader3 = (SIPHeader) getHeaderLowerCase(SIPHeaderNamesCache.toLowerCase(sIPHeader.getName()));
                if (sIPHeader3 != null) {
                    Iterator<SIPHeader> it = this.headers.iterator();
                    while (it.hasNext()) {
                        if (it.next().equals(sIPHeader3)) {
                            it.remove();
                        }
                    }
                }
                if (this.nameTable.containsKey(lowerCase)) {
                    this.nameTable.put(lowerCase, sIPHeader2);
                    this.headers.add(sIPHeader2);
                } else if (sIPHeader2 instanceof SIPHeaderList) {
                    SIPHeaderList sIPHeaderList3 = (SIPHeaderList) this.nameTable.get(lowerCase);
                    if (sIPHeaderList3 != null) {
                        sIPHeaderList3.addAll((SIPHeaderList) sIPHeader2);
                    } else {
                        this.nameTable.put(lowerCase, sIPHeader2);
                    }
                } else {
                    this.nameTable.put(lowerCase, sIPHeader2);
                }
                if (!(sIPHeader2 instanceof From)) {
                    this.fromHeader = (From) sIPHeader2;
                    return;
                }
                if (sIPHeader2 instanceof ContentLength) {
                    this.contentLengthHeader = (ContentLength) sIPHeader2;
                    return;
                }
                if (sIPHeader2 instanceof C4830To) {
                    this.toHeader = (C4830To) sIPHeader2;
                    return;
                }
                if (sIPHeader2 instanceof CSeq) {
                    this.cSeqHeader = (CSeq) sIPHeader2;
                    return;
                } else if (sIPHeader2 instanceof CallID) {
                    this.callIdHeader = (CallID) sIPHeader2;
                    return;
                } else {
                    if (sIPHeader2 instanceof MaxForwards) {
                        this.maxForwardsHeader = (MaxForwards) sIPHeader2;
                        return;
                    }
                    return;
                }
            }
        }
        z = false;
        if (z) {
        }
        sIPHeader2 = sIPHeader;
        lowerCase = SIPHeaderNamesCache.toLowerCase(sIPHeader2.getName());
        if (!this.nameTable.containsKey(lowerCase)) {
        }
        sIPHeader3 = (SIPHeader) getHeaderLowerCase(SIPHeaderNamesCache.toLowerCase(sIPHeader.getName()));
        if (sIPHeader3 != null) {
        }
        if (this.nameTable.containsKey(lowerCase)) {
        }
        if (!(sIPHeader2 instanceof From)) {
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
            if (obj == null && (obj = this.messageContent) == null && (obj = this.messageContentBytes) == null) {
                obj = null;
            }
            stringBuffer.append(obj.toString());
        } else {
            String str2 = this.messageContent;
            if (str2 != null || this.messageContentBytes != null) {
                if (str2 == null) {
                    try {
                        byte[] bArr = this.messageContentBytes;
                        ContentType contentType = (ContentType) getHeaderLowerCase(CONTENT_TYPE_LOWERCASE);
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

    public final Header getHeaderLowerCase(String str) {
        if (str == null) {
            throw new NullPointerException("bad name");
        }
        SIPHeader sIPHeader = this.nameTable.get(str);
        return sIPHeader instanceof SIPHeaderList ? ((SIPHeaderList) sIPHeader).getFirst() : sIPHeader;
    }

    public final int hashCode() {
        CallID callID = this.callIdHeader;
        if (callID != null) {
            return callID.encodeBody().hashCode();
        }
        throw new RuntimeException("Invalid message! Cannot compute hashcode! call-id header is missing !");
    }
}
