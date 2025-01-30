package gov.nist.javax.sip.header;

import gov.nist.core.NameValueList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class Accept extends ParametersHeader {
    private static final long serialVersionUID = -7866187924308658151L;
    protected MediaRange mediaRange;

    public Accept() {
        super("Accept");
    }

    @Override // gov.nist.javax.sip.header.ParametersHeader, gov.nist.core.GenericObject
    public final Object clone() {
        Accept accept = (Accept) super.clone();
        MediaRange mediaRange = this.mediaRange;
        if (mediaRange != null) {
            accept.mediaRange = (MediaRange) mediaRange.clone();
        }
        return accept;
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        StringBuffer stringBuffer = new StringBuffer();
        encodeBody(stringBuffer);
        return stringBuffer.toString();
    }

    public final void setContentSubType(String str) {
        if (this.mediaRange == null) {
            this.mediaRange = new MediaRange();
        }
        this.mediaRange.subtype = str;
    }

    public final void setContentType(String str) {
        if (this.mediaRange == null) {
            this.mediaRange = new MediaRange();
        }
        this.mediaRange.type = str;
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final void encodeBody(StringBuffer stringBuffer) {
        MediaRange mediaRange = this.mediaRange;
        if (mediaRange != null) {
            mediaRange.encode(stringBuffer);
        }
        NameValueList nameValueList = this.parameters;
        if (nameValueList == null || nameValueList.isEmpty()) {
            return;
        }
        stringBuffer.append(';');
        this.parameters.encode(stringBuffer);
    }
}
