package gov.nist.javax.sip.header;

import java.util.ListIterator;

/* loaded from: classes4.dex */
public class ExtensionHeaderList extends SIPHeaderList<ExtensionHeaderImpl> {
    private static final long serialVersionUID = 4681326807149890197L;

    public ExtensionHeaderList(String str) {
        super(ExtensionHeaderImpl.class, str);
    }

    @Override // gov.nist.javax.sip.header.SIPHeaderList, gov.nist.core.GenericObject
    public final Object clone() {
        ExtensionHeaderList extensionHeaderList = new ExtensionHeaderList(this.headerName);
        extensionHeaderList.clonehlist(this.hlist);
        return extensionHeaderList;
    }

    @Override // gov.nist.javax.sip.header.SIPHeaderList, gov.nist.javax.sip.header.SIPHeader, gov.nist.core.GenericObject
    public final String encode() {
        StringBuffer stringBuffer = new StringBuffer();
        ListIterator listIterator = listIterator();
        while (listIterator.hasNext()) {
            stringBuffer.append(((ExtensionHeaderImpl) listIterator.next()).encode());
        }
        return stringBuffer.toString();
    }

    public ExtensionHeaderList() {
        super(ExtensionHeaderImpl.class, null);
    }
}
