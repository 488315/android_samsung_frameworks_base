package com.google.dexmaker.dx.rop.type;

import java.util.HashMap;

/* loaded from: classes4.dex */
public final class Prototype implements Comparable {
    public static final HashMap internTable = new HashMap(500);
    public final String descriptor;
    public final StdTypeList parameterTypes;
    public final Type returnType;

    private Prototype(String str, Type type, StdTypeList stdTypeList) {
        if (str == null) {
            throw new NullPointerException("descriptor == null");
        }
        if (type == null) {
            throw new NullPointerException("returnType == null");
        }
        if (stdTypeList == null) {
            throw new NullPointerException("parameterTypes == null");
        }
        this.descriptor = str;
        this.returnType = type;
        this.parameterTypes = stdTypeList;
    }

    public static Prototype intern(String str) {
        Prototype prototype;
        int i;
        if (str == null) {
            throw new NullPointerException("descriptor == null");
        }
        HashMap map = internTable;
        synchronized (map) {
            prototype = (Prototype) map.get(str);
        }
        if (prototype != null) {
            return prototype;
        }
        int length = str.length();
        if (str.charAt(0) != '(') {
            throw new IllegalArgumentException("bad descriptor");
        }
        int i2 = 1;
        int i3 = 0;
        while (true) {
            if (i2 >= length) {
                i2 = 0;
                break;
            }
            char cCharAt = str.charAt(i2);
            if (cCharAt == ')') {
                break;
            }
            if (cCharAt >= 'A' && cCharAt <= 'Z') {
                i3++;
            }
            i2++;
        }
        if (i2 == 0 || i2 == length - 1) {
            throw new IllegalArgumentException("bad descriptor");
        }
        if (str.indexOf(41, i2 + 1) != -1) {
            throw new IllegalArgumentException("bad descriptor");
        }
        Type[] typeArr = new Type[i3];
        int i4 = 1;
        int i5 = 0;
        while (true) {
            char cCharAt2 = str.charAt(i4);
            if (cCharAt2 == ')') {
                String strSubstring = str.substring(i4 + 1);
                HashMap map2 = Type.internTable;
                try {
                    Type typeIntern = strSubstring.equals("V") ? Type.VOID : Type.intern(strSubstring);
                    StdTypeList stdTypeList = new StdTypeList(i5);
                    for (int i6 = 0; i6 < i5; i6++) {
                        stdTypeList.set(i6, typeArr[i6]);
                    }
                    Prototype prototype2 = new Prototype(str, typeIntern, stdTypeList);
                    HashMap map3 = internTable;
                    synchronized (map3) {
                        try {
                            String str2 = prototype2.descriptor;
                            Prototype prototype3 = (Prototype) map3.get(str2);
                            if (prototype3 != null) {
                                return prototype3;
                            }
                            map3.put(str2, prototype2);
                            return prototype2;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                } catch (NullPointerException unused) {
                    throw new NullPointerException("descriptor == null");
                }
            }
            int i7 = i4;
            while (cCharAt2 == '[') {
                i7++;
                cCharAt2 = str.charAt(i7);
            }
            if (cCharAt2 == 'L') {
                int iIndexOf = str.indexOf(59, i7);
                if (iIndexOf == -1) {
                    throw new IllegalArgumentException("bad descriptor");
                }
                i = iIndexOf + 1;
            } else {
                i = i7 + 1;
            }
            typeArr[i5] = Type.intern(str.substring(i4, i));
            i5++;
            i4 = i;
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Prototype) {
            return this.descriptor.equals(((Prototype) obj).descriptor);
        }
        return false;
    }

    public final int hashCode() {
        return this.descriptor.hashCode();
    }

    public final String toString() {
        return this.descriptor;
    }

    @Override // java.lang.Comparable
    public final int compareTo(Prototype prototype) {
        if (this != prototype) {
            int iCompareTo = this.returnType.descriptor.compareTo(prototype.returnType.descriptor);
            if (iCompareTo != 0) {
                return iCompareTo;
            }
            int length = this.parameterTypes.arr.length;
            int length2 = prototype.parameterTypes.arr.length;
            int iMin = Math.min(length, length2);
            for (int i = 0; i < iMin; i++) {
                int iCompareTo2 = this.parameterTypes.get(i).descriptor.compareTo(prototype.parameterTypes.get(i).descriptor);
                if (iCompareTo2 != 0) {
                    return iCompareTo2;
                }
            }
            if (length < length2) {
                return -1;
            }
            if (length > length2) {
                return 1;
            }
        }
        return 0;
    }
}
