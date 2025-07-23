package android.sax;

/* loaded from: classes3.dex */
class Children {
    Child[] children = new Child[16];

    Children() {
    }

    Element getOrCreate(Element element, String str, String str2) {
        int hashCode = (str.hashCode() * 31) + str2.hashCode();
        int i = hashCode & 15;
        Child child = this.children[i];
        if (child == null) {
            Child child2 = new Child(element, str, str2, element.depth + 1, hashCode);
            this.children[i] = child2;
            return child2;
        }
        while (true) {
            if (child.hash == hashCode && child.uri.compareTo(str) == 0 && child.localName.compareTo(str2) == 0) {
                return child;
            }
            Child child3 = child.next;
            if (child3 == null) {
                Child child4 = new Child(element, str, str2, element.depth + 1, hashCode);
                child.next = child4;
                return child4;
            }
            child = child3;
        }
    }

    Element get(String str, String str2) {
        int hashCode = (str.hashCode() * 31) + str2.hashCode();
        Child child = this.children[hashCode & 15];
        if (child == null) {
            return null;
        }
        do {
            if (child.hash == hashCode && child.uri.compareTo(str) == 0 && child.localName.compareTo(str2) == 0) {
                return child;
            }
            child = child.next;
        } while (child != null);
        return null;
    }

    static class Child extends Element {
        final int hash;
        Child next;

        Child(Element element, String str, String str2, int i, int i2) {
            super(element, str, str2, i);
            this.hash = i2;
        }
    }
}
