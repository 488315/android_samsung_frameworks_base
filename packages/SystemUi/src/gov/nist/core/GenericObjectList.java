package gov.nist.core;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class GenericObjectList extends LinkedList<GenericObject> implements Serializable, Cloneable {
    public static final /* synthetic */ int $r8$clinit = 0;
    protected int indentation;
    protected String listName;
    protected Class<?> myClass;
    private ListIterator<? extends GenericObject> myListIterator;
    protected String separator;
    private String stringRep;

    public GenericObjectList() {
        this.listName = null;
        this.stringRep = "";
        this.separator = ";";
    }

    @Override // java.util.LinkedList, java.util.Deque
    public final void addFirst(Object obj) {
        GenericObject genericObject = (GenericObject) obj;
        if (this.myClass == null) {
            this.myClass = genericObject.getClass();
        } else {
            super.addFirst(genericObject);
        }
    }

    @Override // java.util.LinkedList
    public final Object clone() {
        GenericObjectList genericObjectList = (GenericObjectList) super.clone();
        ListIterator<GenericObject> listIterator = genericObjectList.listIterator();
        while (listIterator.hasNext()) {
            listIterator.set((GenericObject) listIterator.next().clone());
        }
        return genericObjectList;
    }

    public final String encode() {
        if (isEmpty()) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        ListIterator<GenericObject> listIterator = listIterator();
        if (listIterator.hasNext()) {
            while (true) {
                GenericObject next = listIterator.next();
                if (next instanceof GenericObject) {
                    stringBuffer.append(next.encode());
                } else {
                    stringBuffer.append(next.toString());
                }
                if (!listIterator.hasNext()) {
                    break;
                }
                stringBuffer.append(this.separator);
            }
        }
        return stringBuffer.toString();
    }

    @Override // java.util.AbstractList, java.util.Collection, java.util.List
    public final boolean equals(Object obj) {
        if (obj == null || !getClass().equals(obj.getClass())) {
            return false;
        }
        GenericObjectList genericObjectList = (GenericObjectList) obj;
        if (size() != genericObjectList.size()) {
            return false;
        }
        ListIterator<GenericObject> listIterator = listIterator();
        while (listIterator.hasNext()) {
            do {
                try {
                } catch (NoSuchElementException unused) {
                    return false;
                }
            } while (!listIterator.next().equals(genericObjectList.listIterator().next()));
        }
        ListIterator<GenericObject> listIterator2 = genericObjectList.listIterator();
        while (listIterator2.hasNext()) {
            do {
                try {
                } catch (NoSuchElementException unused2) {
                    return false;
                }
            } while (!listIterator2.next().equals(listIterator().next()));
        }
        return true;
    }

    @Override // java.util.AbstractList, java.util.Collection, java.util.List
    public final int hashCode() {
        return 42;
    }

    @Override // java.util.AbstractCollection
    public final String toString() {
        return encode();
    }

    public GenericObjectList(String str) {
        this();
        this.listName = str;
    }

    public GenericObjectList(String str, String str2) {
        this(str);
        try {
            this.myClass = Class.forName(str2);
        } catch (ClassNotFoundException e) {
            InternalErrorHandler.handleException(e);
            throw null;
        }
    }

    public GenericObjectList(String str, Class cls) {
        this(str);
        this.myClass = cls;
    }
}
