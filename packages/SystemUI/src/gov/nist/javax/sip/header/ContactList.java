package gov.nist.javax.sip.header;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class ContactList extends SIPHeaderList<Contact> {
    private static final long serialVersionUID = 1224806837758986814L;

    public ContactList() {
        super(Contact.class, "Contact");
    }

    @Override // gov.nist.javax.sip.header.SIPHeaderList, gov.nist.core.GenericObject
    public final Object clone() {
        ContactList contactList = new ContactList();
        contactList.clonehlist(this.hlist);
        return contactList;
    }
}
