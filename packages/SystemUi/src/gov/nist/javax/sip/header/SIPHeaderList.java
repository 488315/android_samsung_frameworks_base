package gov.nist.javax.sip.header;

import gov.nist.javax.sip.header.SIPHeader;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import javax.sip.header.Header;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class SIPHeaderList<HDR extends SIPHeader> extends SIPHeader implements List<HDR> {
    protected List<HDR> hlist;
    private Class<HDR> myClass;

    private SIPHeaderList() {
        this.hlist = new LinkedList();
    }

    @Override // java.util.List
    public final void add(int i, Object obj) {
        this.hlist.add(i, (SIPHeader) obj);
    }

    @Override // java.util.List, java.util.Collection
    public final boolean addAll(Collection collection) {
        return this.hlist.addAll(collection);
    }

    @Override // java.util.List, java.util.Collection
    public final void clear() {
        this.hlist.clear();
    }

    @Override // gov.nist.core.GenericObject
    public Object clone() {
        try {
            SIPHeaderList sIPHeaderList = (SIPHeaderList) getClass().getConstructor(null).newInstance(null);
            sIPHeaderList.headerName = this.headerName;
            sIPHeaderList.myClass = this.myClass;
            sIPHeaderList.clonehlist(this.hlist);
            return sIPHeaderList;
        } catch (Exception e) {
            throw new RuntimeException("Could not clone!", e);
        }
    }

    public final void clonehlist(List list) {
        if (list != null) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                this.hlist.add((SIPHeader) ((Header) it.next()).clone());
            }
        }
    }

    @Override // java.util.List, java.util.Collection
    public final boolean contains(Object obj) {
        return this.hlist.contains(obj);
    }

    @Override // java.util.List, java.util.Collection
    public final boolean containsAll(Collection collection) {
        return this.hlist.containsAll(collection);
    }

    @Override // gov.nist.javax.sip.header.SIPHeader, gov.nist.core.GenericObject
    public String encode() {
        StringBuffer stringBuffer = new StringBuffer();
        encode(stringBuffer);
        return stringBuffer.toString();
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String encodeBody() {
        StringBuffer stringBuffer = new StringBuffer();
        encodeBody(stringBuffer);
        return stringBuffer.toString();
    }

    @Override // gov.nist.javax.sip.header.SIPObject, gov.nist.core.GenericObject
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SIPHeaderList)) {
            return false;
        }
        List<HDR> list = this.hlist;
        List<HDR> list2 = ((SIPHeaderList) obj).hlist;
        if (list == list2) {
            return true;
        }
        return list == null ? list2 == null || list2.size() == 0 : list.equals(list2);
    }

    @Override // java.util.List
    public final Object get(int i) {
        return this.hlist.get(i);
    }

    public Header getFirst() {
        List<HDR> list = this.hlist;
        if (list == null || list.isEmpty()) {
            return null;
        }
        return this.hlist.get(0);
    }

    public final Class getMyClass() {
        return this.myClass;
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final String getName() {
        return this.headerName;
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final int hashCode() {
        return this.headerName.hashCode();
    }

    @Override // java.util.List
    public final int indexOf(Object obj) {
        return this.hlist.indexOf(obj);
    }

    @Override // java.util.List, java.util.Collection
    public final boolean isEmpty() {
        return this.hlist.isEmpty();
    }

    @Override // java.util.List, java.util.Collection, java.lang.Iterable
    public final Iterator iterator() {
        return this.hlist.listIterator();
    }

    @Override // java.util.List
    public final int lastIndexOf(Object obj) {
        return this.hlist.lastIndexOf(obj);
    }

    @Override // java.util.List
    public final ListIterator listIterator() {
        return this.hlist.listIterator(0);
    }

    @Override // java.util.List, java.util.Collection
    public final boolean remove(Object obj) {
        return this.hlist.remove(obj);
    }

    @Override // java.util.List, java.util.Collection
    public final boolean removeAll(Collection collection) {
        return this.hlist.removeAll(collection);
    }

    @Override // java.util.List, java.util.Collection
    public final boolean retainAll(Collection collection) {
        return this.hlist.retainAll(collection);
    }

    @Override // java.util.List
    public final Object set(int i, Object obj) {
        return (SIPHeader) this.hlist.set(i, (SIPHeader) obj);
    }

    @Override // java.util.List, java.util.Collection
    public final int size() {
        return this.hlist.size();
    }

    @Override // java.util.List
    public final List subList(int i, int i2) {
        return this.hlist.subList(i, i2);
    }

    @Override // java.util.List, java.util.Collection
    public final Object[] toArray() {
        return this.hlist.toArray();
    }

    @Override // java.util.List
    public final boolean addAll(int i, Collection collection) {
        return this.hlist.addAll(i, collection);
    }

    @Override // gov.nist.javax.sip.header.SIPHeader, gov.nist.javax.sip.header.SIPObject, gov.nist.core.GenericObject
    public final StringBuffer encode(StringBuffer stringBuffer) {
        if (this.hlist.isEmpty()) {
            stringBuffer.append(this.headerName);
            stringBuffer.append(':');
            stringBuffer.append("\r\n");
        } else if (this.headerName.equals("WWW-Authenticate") || this.headerName.equals("Proxy-Authenticate") || this.headerName.equals("Authorization") || this.headerName.equals("Proxy-Authorization") || getClass().equals(ExtensionHeaderList.class)) {
            ListIterator<HDR> listIterator = this.hlist.listIterator();
            while (listIterator.hasNext()) {
                listIterator.next().encode(stringBuffer);
            }
        } else {
            stringBuffer.append(this.headerName);
            stringBuffer.append(":");
            stringBuffer.append(" ");
            encodeBody(stringBuffer);
            stringBuffer.append("\r\n");
        }
        return stringBuffer;
    }

    @Override // gov.nist.javax.sip.header.SIPHeader
    public final void encodeBody(StringBuffer stringBuffer) {
        ListIterator listIterator = listIterator();
        while (true) {
            SIPHeader sIPHeader = (SIPHeader) listIterator.next();
            if (sIPHeader == this) {
                throw new RuntimeException("Unexpected circularity in SipHeaderList");
            }
            sIPHeader.encodeBody(stringBuffer);
            if (!listIterator.hasNext()) {
                return;
            }
            if (this.headerName.equals("Privacy")) {
                stringBuffer.append(";");
            } else {
                stringBuffer.append(",");
            }
        }
    }

    @Override // java.util.List
    public final ListIterator listIterator(int i) {
        return this.hlist.listIterator(i);
    }

    @Override // java.util.List
    public final Object remove(int i) {
        return this.hlist.remove(i);
    }

    @Override // java.util.List, java.util.Collection
    public final Object[] toArray(Object[] objArr) {
        return this.hlist.toArray(objArr);
    }

    public SIPHeaderList(Class<HDR> cls, String str) {
        this();
        this.headerName = str;
        this.myClass = cls;
    }

    @Override // java.util.List, java.util.Collection
    public final /* bridge */ /* synthetic */ boolean add(Object obj) {
        add((SIPHeader) obj);
        return true;
    }

    public final void add(SIPHeader sIPHeader) {
        this.hlist.add(sIPHeader);
    }
}
