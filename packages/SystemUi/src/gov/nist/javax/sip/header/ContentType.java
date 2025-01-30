package gov.nist.javax.sip.header;

import gov.nist.core.NameValueList;
import javax.sip.header.ContentTypeHeader;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class ContentType extends ParametersHeader implements ContentTypeHeader {
    private static final long serialVersionUID = 8475682204373446610L;
    protected MediaRange mediaRange;

    public ContentType() {
        super("Content-Type");
    }

    @Override // gov.nist.javax.sip.header.ParametersHeader, gov.nist.core.GenericObject
    public final Object clone() {
        ContentType contentType = (ContentType) super.clone();
        MediaRange mediaRange = this.mediaRange;
        if (mediaRange != null) {
            contentType.mediaRange = (MediaRange) mediaRange.clone();
        }
        return contentType;
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        StringBuffer stringBuffer = new StringBuffer();
        encodeBody(stringBuffer);
        return stringBuffer.toString();
    }

    @Override // gov.nist.javax.sip.header.SIPObject, gov.nist.core.GenericObject
    public final boolean equals(Object obj) {
        if (obj instanceof ContentTypeHeader) {
            ContentTypeHeader contentTypeHeader = (ContentTypeHeader) obj;
            MediaRange mediaRange = this.mediaRange;
            String str = mediaRange == null ? null : mediaRange.type;
            ContentType contentType = (ContentType) contentTypeHeader;
            MediaRange mediaRange2 = contentType.mediaRange;
            if (str.equalsIgnoreCase(mediaRange2 == null ? null : mediaRange2.type)) {
                MediaRange mediaRange3 = this.mediaRange;
                String str2 = mediaRange3 == null ? null : mediaRange3.subtype;
                MediaRange mediaRange4 = contentType.mediaRange;
                if (str2.equalsIgnoreCase(mediaRange4 != null ? mediaRange4.subtype : null) && equalParameters(contentType)) {
                    return true;
                }
            }
        }
        return false;
    }

    public final void setContentSubType(String str) {
        if (str == null) {
            throw new NullPointerException("null arg");
        }
        if (this.mediaRange == null) {
            this.mediaRange = new MediaRange();
        }
        this.mediaRange.subtype = str;
    }

    public final void setContentType(String str) {
        if (str == null) {
            throw new NullPointerException("null arg");
        }
        if (this.mediaRange == null) {
            this.mediaRange = new MediaRange();
        }
        this.mediaRange.type = str;
    }

    public ContentType(String str, String str2) {
        this();
        if (this.mediaRange == null) {
            this.mediaRange = new MediaRange();
        }
        MediaRange mediaRange = this.mediaRange;
        mediaRange.type = str;
        mediaRange.subtype = str2;
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final void encodeBody(StringBuffer stringBuffer) {
        this.mediaRange.encode(stringBuffer);
        NameValueList nameValueList = this.parameters;
        if ((nameValueList == null || nameValueList.isEmpty()) ? false : true) {
            stringBuffer.append(";");
            this.parameters.encode(stringBuffer);
        }
    }
}
