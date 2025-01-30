package gov.nist.javax.sip.header;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class CallIdentifier extends SIPObject {
    private static final long serialVersionUID = 7314773655675451377L;
    protected String host;
    protected String localId;

    public CallIdentifier() {
    }

    @Override // gov.nist.core.GenericObject
    public final String encode() {
        StringBuffer stringBuffer = new StringBuffer();
        encode(stringBuffer);
        return stringBuffer.toString();
    }

    @Override // gov.nist.javax.sip.header.SIPObject, gov.nist.core.GenericObject
    public final boolean equals(Object obj) {
        if (obj == null || !obj.getClass().equals(CallIdentifier.class)) {
            return false;
        }
        CallIdentifier callIdentifier = (CallIdentifier) obj;
        if (this.localId.compareTo(callIdentifier.localId) != 0) {
            return false;
        }
        String str = this.host;
        String str2 = callIdentifier.host;
        if (str == str2) {
            return true;
        }
        return (str != null || str2 == null) && (str == null || str2 != null) && str.compareToIgnoreCase(str2) == 0;
    }

    public final int hashCode() {
        String str = this.localId;
        if (str != null) {
            return str.hashCode();
        }
        throw new UnsupportedOperationException("Hash code called before id is set");
    }

    public CallIdentifier(String str, String str2) {
        this.localId = str;
        this.host = str2;
    }

    @Override // gov.nist.javax.sip.header.SIPObject, gov.nist.core.GenericObject
    public final StringBuffer encode(StringBuffer stringBuffer) {
        stringBuffer.append(this.localId);
        if (this.host != null) {
            stringBuffer.append("@");
            stringBuffer.append(this.host);
        }
        return stringBuffer;
    }

    public CallIdentifier(String str) {
        if (str != null) {
            int indexOf = str.indexOf(64);
            if (indexOf == -1) {
                this.localId = str;
                this.host = null;
                return;
            }
            this.localId = str.substring(0, indexOf);
            String substring = str.substring(indexOf + 1, str.length());
            this.host = substring;
            if (this.localId == null || substring == null) {
                throw new IllegalArgumentException("CallID  must be token@token or token");
            }
            return;
        }
        throw new IllegalArgumentException("NULL!");
    }
}
