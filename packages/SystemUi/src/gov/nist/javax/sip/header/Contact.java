package gov.nist.javax.sip.header;

import gov.nist.core.NameValue;
import gov.nist.javax.sip.address.AddressImpl;
import javax.sip.header.ContactHeader;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class Contact extends AddressParametersHeader implements ContactHeader {
    private static final long serialVersionUID = 1677294871695706288L;
    private ContactList contactList;
    protected boolean wildCardFlag;

    public Contact() {
        super("Contact");
    }

    @Override // gov.nist.javax.sip.header.AddressParametersHeader, gov.nist.javax.sip.header.ParametersHeader, gov.nist.core.GenericObject
    public final Object clone() {
        Contact contact = (Contact) super.clone();
        ContactList contactList = this.contactList;
        if (contactList != null) {
            contact.contactList = (ContactList) contactList.clone();
        }
        return contact;
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        StringBuffer stringBuffer = new StringBuffer();
        encodeBody(stringBuffer);
        return stringBuffer.toString();
    }

    @Override // gov.nist.javax.sip.header.AddressParametersHeader, gov.nist.javax.sip.header.SIPObject, gov.nist.core.GenericObject
    public final boolean equals(Object obj) {
        return (obj instanceof ContactHeader) && super.equals(obj);
    }

    @Override // gov.nist.javax.sip.header.AddressParametersHeader, javax.sip.header.HeaderAddress
    public final AddressImpl getAddress() {
        return this.address;
    }

    @Override // gov.nist.javax.sip.header.AddressParametersHeader
    public final void setAddress(AddressImpl addressImpl) {
        this.address = addressImpl;
        this.wildCardFlag = false;
    }

    @Override // gov.nist.javax.sip.header.ParametersHeader
    public final void setParameter(String str, String str2) {
        NameValue nameValue = this.parameters.getNameValue(str);
        if (nameValue != null) {
            nameValue.setValueAsObject(str2);
            return;
        }
        NameValue nameValue2 = new NameValue(str, str2);
        if (str.equalsIgnoreCase("methods")) {
            nameValue2.setQuotedValue();
        }
        this.parameters.set(nameValue2);
    }

    public final void setWildCardFlag$1() {
        this.wildCardFlag = true;
        AddressImpl addressImpl = new AddressImpl();
        this.address = addressImpl;
        addressImpl.setWildCardFlag();
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final void encodeBody(StringBuffer stringBuffer) {
        if (this.wildCardFlag) {
            stringBuffer.append('*');
            return;
        }
        if (this.address.getAddressType() == 1) {
            this.address.encode(stringBuffer);
        } else {
            stringBuffer.append('<');
            this.address.encode(stringBuffer);
            stringBuffer.append('>');
        }
        if (this.parameters.isEmpty()) {
            return;
        }
        stringBuffer.append(";");
        this.parameters.encode(stringBuffer);
    }
}
