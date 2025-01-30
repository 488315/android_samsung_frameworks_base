package com.android.server;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.FastImmutableArraySet;
import android.util.LogPrinter;
import android.util.MutableInt;
import android.util.PrintWriterPrinter;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import com.android.internal.util.FastPrintWriter;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.pm.Computer;
import com.android.server.pm.snapshot.PackageDataSnapshot;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* loaded from: classes.dex */
public abstract class IntentResolver {
    public static final Comparator mResolvePrioritySorter = new Comparator() { // from class: com.android.server.IntentResolver.1
        @Override // java.util.Comparator
        public int compare(Object obj, Object obj2) {
            int priority = ((IntentFilter) obj).getPriority();
            int priority2 = ((IntentFilter) obj2).getPriority();
            if (priority > priority2) {
                return -1;
            }
            return priority < priority2 ? 1 : 0;
        }
    };
    public final ArraySet mFilters = new ArraySet();
    public final ArrayMap mTypeToFilter = new ArrayMap();
    public final ArrayMap mBaseTypeToFilter = new ArrayMap();
    public final ArrayMap mWildTypeToFilter = new ArrayMap();
    public final ArrayMap mSchemeToFilter = new ArrayMap();
    public final ArrayMap mActionToFilter = new ArrayMap();
    public final ArrayMap mTypedActionToFilter = new ArrayMap();

    public boolean allowFilterResult(Object obj, List list) {
        return true;
    }

    public void filterResults(List list) {
    }

    public Object filterToLabel(Object obj) {
        return "IntentFilter";
    }

    public abstract IntentFilter getIntentFilter(Object obj);

    public boolean isFilterStopped(Computer computer, Object obj, int i) {
        return false;
    }

    public abstract boolean isPackageForFilter(String str, Object obj);

    public abstract Object[] newArray(int i);

    public Object newResult(Computer computer, Object obj, int i, int i2, long j) {
        return obj;
    }

    public Object snapshot(Object obj) {
        return obj;
    }

    public void addFilter(PackageDataSnapshot packageDataSnapshot, Object obj) {
        IntentFilter intentFilter = getIntentFilter(obj);
        this.mFilters.add(obj);
        int register_intent_filter = register_intent_filter(obj, intentFilter.schemesIterator(), this.mSchemeToFilter, "      Scheme: ");
        int register_mime_types = register_mime_types(obj, "      Type: ");
        if (register_intent_filter == 0 && register_mime_types == 0) {
            register_intent_filter(obj, intentFilter.actionsIterator(), this.mActionToFilter, "      Action: ");
        }
        if (register_mime_types != 0) {
            register_intent_filter(obj, intentFilter.actionsIterator(), this.mTypedActionToFilter, "      TypedAction: ");
        }
    }

    public static boolean intentMatchesFilter(IntentFilter intentFilter, Intent intent, String str) {
        boolean z = (intent.getFlags() & 8) != 0;
        LogPrinter logPrinter = z ? new LogPrinter(2, "IntentResolver", 3) : null;
        if (z) {
            Slog.v("IntentResolver", "Intent: " + intent);
            Slog.v("IntentResolver", "Matching against filter: " + intentFilter);
            intentFilter.dump(logPrinter, "  ");
        }
        int match = intentFilter.match(intent.getAction(), str, intent.getScheme(), intent.getData(), intent.getCategories(), "IntentResolver");
        if (match >= 0) {
            if (z) {
                Slog.v("IntentResolver", "Filter matched!  match=0x" + Integer.toHexString(match));
            }
            return true;
        }
        if (z) {
            Slog.v("IntentResolver", "Filter did not match: " + (match != -4 ? match != -3 ? match != -2 ? match != -1 ? "unknown reason" : "type" : "data" : "action" : "category"));
        }
        return false;
    }

    public final ArrayList collectFilters(Object[] objArr, IntentFilter intentFilter) {
        Object obj;
        ArrayList arrayList = null;
        if (objArr != null) {
            for (int i = 0; i < objArr.length && (obj = objArr[i]) != null; i++) {
                if (IntentFilter.filterEquals(getIntentFilter(obj), intentFilter)) {
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                    }
                    arrayList.add(obj);
                }
            }
        }
        return arrayList;
    }

    public ArrayList findFilters(IntentFilter intentFilter) {
        if (intentFilter.countDataSchemes() == 1) {
            return collectFilters((Object[]) this.mSchemeToFilter.get(intentFilter.getDataScheme(0)), intentFilter);
        }
        if (intentFilter.countDataTypes() != 0 && intentFilter.countActions() == 1) {
            return collectFilters((Object[]) this.mTypedActionToFilter.get(intentFilter.getAction(0)), intentFilter);
        }
        if (intentFilter.countDataTypes() == 0 && intentFilter.countDataSchemes() == 0 && intentFilter.countActions() == 1) {
            return collectFilters((Object[]) this.mActionToFilter.get(intentFilter.getAction(0)), intentFilter);
        }
        Iterator it = this.mFilters.iterator();
        ArrayList arrayList = null;
        while (it.hasNext()) {
            Object next = it.next();
            if (IntentFilter.filterEquals(getIntentFilter(next), intentFilter)) {
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                arrayList.add(next);
            }
        }
        return arrayList;
    }

    public void removeFilter(Object obj) {
        removeFilterInternal(obj);
        this.mFilters.remove(obj);
    }

    public void removeFilterInternal(Object obj) {
        IntentFilter intentFilter = getIntentFilter(obj);
        int unregister_intent_filter = unregister_intent_filter(obj, intentFilter.schemesIterator(), this.mSchemeToFilter, "      Scheme: ");
        int unregister_mime_types = unregister_mime_types(obj, "      Type: ");
        if (unregister_intent_filter == 0 && unregister_mime_types == 0) {
            unregister_intent_filter(obj, intentFilter.actionsIterator(), this.mActionToFilter, "      Action: ");
        }
        if (unregister_mime_types != 0) {
            unregister_intent_filter(obj, intentFilter.actionsIterator(), this.mTypedActionToFilter, "      TypedAction: ");
        }
    }

    public boolean dumpMap(PrintWriter printWriter, String str, String str2, String str3, ArrayMap arrayMap, String str4, boolean z, boolean z2) {
        boolean z3;
        String str5;
        Object obj;
        PrintWriterPrinter printWriterPrinter;
        boolean z4;
        ArrayMap arrayMap2 = arrayMap;
        String str6 = str3 + "  ";
        String str7 = str3 + "    ";
        ArrayMap arrayMap3 = new ArrayMap();
        String str8 = str2;
        int i = 0;
        boolean z5 = false;
        PrintWriterPrinter printWriterPrinter2 = null;
        while (i < arrayMap.size()) {
            Object[] objArr = (Object[]) arrayMap2.valueAt(i);
            int length = objArr.length;
            if (!z2 || z) {
                z3 = z5;
                str8 = str8;
                printWriterPrinter2 = printWriterPrinter2;
                int i2 = 0;
                boolean z6 = false;
                while (i2 < length) {
                    Object obj2 = objArr[i2];
                    if (obj2 != null) {
                        if (str4 == null || isPackageForFilter(str4, obj2)) {
                            if (str8 != null) {
                                printWriter.print(str);
                                printWriter.println(str8);
                                str8 = null;
                            }
                            if (!z6) {
                                printWriter.print(str6);
                                printWriter.print((String) arrayMap2.keyAt(i));
                                printWriter.println(XmlUtils.STRING_ARRAY_SEPARATOR);
                                z6 = true;
                            }
                            dumpFilter(printWriter, str7, obj2);
                            if (z) {
                                if (printWriterPrinter2 == null) {
                                    printWriterPrinter2 = new PrintWriterPrinter(printWriter);
                                }
                                getIntentFilter(obj2).dump(printWriterPrinter2, str7 + "  ");
                            }
                            z3 = true;
                        }
                        i2++;
                        arrayMap2 = arrayMap;
                    }
                }
            } else {
                arrayMap3.clear();
                int i3 = 0;
                while (true) {
                    str5 = str8;
                    if (i3 >= length || (obj = objArr[i3]) == null) {
                        break;
                    }
                    if (str4 == null || isPackageForFilter(str4, obj)) {
                        Object filterToLabel = filterToLabel(obj);
                        printWriterPrinter = printWriterPrinter2;
                        int indexOfKey = arrayMap3.indexOfKey(filterToLabel);
                        if (indexOfKey < 0) {
                            z4 = z5;
                            arrayMap3.put(filterToLabel, new MutableInt(1));
                        } else {
                            z4 = z5;
                            ((MutableInt) arrayMap3.valueAt(indexOfKey)).value++;
                        }
                    } else {
                        z4 = z5;
                        printWriterPrinter = printWriterPrinter2;
                    }
                    i3++;
                    str8 = str5;
                    printWriterPrinter2 = printWriterPrinter;
                    z5 = z4;
                }
                z3 = z5;
                PrintWriterPrinter printWriterPrinter3 = printWriterPrinter2;
                str8 = str5;
                int i4 = 0;
                boolean z7 = false;
                while (i4 < arrayMap3.size()) {
                    if (str8 != null) {
                        printWriter.print(str);
                        printWriter.println(str8);
                        str8 = null;
                    }
                    if (!z7) {
                        printWriter.print(str6);
                        printWriter.print((String) arrayMap2.keyAt(i));
                        printWriter.println(XmlUtils.STRING_ARRAY_SEPARATOR);
                        z7 = true;
                    }
                    dumpFilterLabel(printWriter, str7, arrayMap3.keyAt(i4), ((MutableInt) arrayMap3.valueAt(i4)).value);
                    i4++;
                    z3 = true;
                }
                printWriterPrinter2 = printWriterPrinter3;
            }
            z5 = z3;
            i++;
            arrayMap2 = arrayMap;
        }
        return z5;
    }

    public void writeProtoMap(ProtoOutputStream protoOutputStream, long j, ArrayMap arrayMap) {
        int size = arrayMap.size();
        for (int i = 0; i < size; i++) {
            long start = protoOutputStream.start(j);
            protoOutputStream.write(1138166333441L, (String) arrayMap.keyAt(i));
            for (Object obj : (Object[]) arrayMap.valueAt(i)) {
                if (obj != null) {
                    protoOutputStream.write(2237677961218L, obj.toString());
                }
            }
            protoOutputStream.end(start);
        }
    }

    public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        writeProtoMap(protoOutputStream, 2246267895809L, this.mTypeToFilter);
        writeProtoMap(protoOutputStream, 2246267895810L, this.mBaseTypeToFilter);
        writeProtoMap(protoOutputStream, 2246267895811L, this.mWildTypeToFilter);
        writeProtoMap(protoOutputStream, 2246267895812L, this.mSchemeToFilter);
        writeProtoMap(protoOutputStream, 2246267895813L, this.mActionToFilter);
        writeProtoMap(protoOutputStream, 2246267895814L, this.mTypedActionToFilter);
        protoOutputStream.end(start);
    }

    public boolean dump(PrintWriter printWriter, String str, String str2, String str3, boolean z, boolean z2) {
        String str4 = str2 + "  ";
        String str5 = KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE + str2;
        String str6 = str + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE + str2;
        if (dumpMap(printWriter, str6, "Full MIME Types:", str4, this.mTypeToFilter, str3, z, z2)) {
            str6 = str5;
        }
        if (dumpMap(printWriter, str6, "Base MIME Types:", str4, this.mBaseTypeToFilter, str3, z, z2)) {
            str6 = str5;
        }
        if (dumpMap(printWriter, str6, "Wild MIME Types:", str4, this.mWildTypeToFilter, str3, z, z2)) {
            str6 = str5;
        }
        if (dumpMap(printWriter, str6, "Schemes:", str4, this.mSchemeToFilter, str3, z, z2)) {
            str6 = str5;
        }
        if (dumpMap(printWriter, str6, "Non-Data Actions:", str4, this.mActionToFilter, str3, z, z2)) {
            str6 = str5;
        }
        if (dumpMap(printWriter, str6, "MIME Typed Actions:", str4, this.mTypedActionToFilter, str3, z, z2)) {
            str6 = str5;
        }
        return str6 == str5;
    }

    public class IteratorWrapper implements Iterator {
        public Object mCur;

        /* renamed from: mI */
        public final Iterator f1618mI;

        public IteratorWrapper(Iterator it) {
            this.f1618mI = it;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.f1618mI.hasNext();
        }

        @Override // java.util.Iterator
        public Object next() {
            Object next = this.f1618mI.next();
            this.mCur = next;
            return next;
        }

        @Override // java.util.Iterator
        public void remove() {
            Object obj = this.mCur;
            if (obj != null) {
                IntentResolver.this.removeFilterInternal(obj);
            }
            this.f1618mI.remove();
        }
    }

    public Iterator filterIterator() {
        return new IteratorWrapper(this.mFilters.iterator());
    }

    public Set filterSet() {
        return Collections.unmodifiableSet(this.mFilters);
    }

    public List queryIntentFromList(Computer computer, Intent intent, String str, boolean z, ArrayList arrayList, int i, long j) {
        ArrayList arrayList2 = new ArrayList();
        boolean z2 = (intent.getFlags() & 8) != 0;
        FastImmutableArraySet fastIntentCategories = getFastIntentCategories(intent);
        String scheme = intent.getScheme();
        int i2 = 0;
        for (int size = arrayList.size(); i2 < size; size = size) {
            buildResolveList(computer, intent, fastIntentCategories, z2, z, str, scheme, (Object[]) arrayList.get(i2), arrayList2, i, j);
            i2++;
        }
        filterResults(arrayList2);
        sortResults(arrayList2);
        return arrayList2;
    }

    public List queryIntent(PackageDataSnapshot packageDataSnapshot, Intent intent, String str, boolean z, int i) {
        return queryIntent(packageDataSnapshot, intent, str, z, i, 0L);
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x016a  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0193 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x01ce  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x01f4  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0210  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x022c  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0250  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x01ed  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x018f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final List queryIntent(PackageDataSnapshot packageDataSnapshot, Intent intent, String str, boolean z, int i, long j) {
        Object[] objArr;
        Object[] objArr2;
        Object[] objArr3;
        Object[] objArr4;
        Object[] objArr5;
        String str2;
        ArrayList arrayList;
        int i2;
        int indexOf;
        Object[] objArr6;
        String scheme = intent.getScheme();
        ArrayList arrayList2 = new ArrayList();
        boolean z2 = (intent.getFlags() & 8) != 0;
        if (z2) {
            Slog.v("IntentResolver", "Resolving type=" + str + " scheme=" + scheme + " defaultOnly=" + z + " userId=" + i + " of " + intent);
        }
        if (str != null && (indexOf = str.indexOf(47)) > 0) {
            String substring = str.substring(0, indexOf);
            if (!substring.equals("*")) {
                if (str.length() != indexOf + 2 || str.charAt(indexOf + 1) != '*') {
                    objArr = (Object[]) this.mTypeToFilter.get(str);
                    if (z2) {
                        Slog.v("IntentResolver", "First type cut: " + Arrays.toString(objArr));
                    }
                    objArr6 = (Object[]) this.mWildTypeToFilter.get(substring);
                    if (z2) {
                        Slog.v("IntentResolver", "Second type cut: " + Arrays.toString(objArr6));
                    }
                } else {
                    objArr = (Object[]) this.mBaseTypeToFilter.get(substring);
                    if (z2) {
                        Slog.v("IntentResolver", "First type cut: " + Arrays.toString(objArr));
                    }
                    objArr6 = (Object[]) this.mWildTypeToFilter.get(substring);
                    if (z2) {
                        Slog.v("IntentResolver", "Second type cut: " + Arrays.toString(objArr6));
                    }
                }
                Object[] objArr7 = (Object[]) this.mWildTypeToFilter.get("*");
                if (z2) {
                    Slog.v("IntentResolver", "Third type cut: " + Arrays.toString(objArr7));
                }
                objArr3 = objArr7;
                objArr2 = objArr6;
                if (scheme != null) {
                    Object[] objArr8 = (Object[]) this.mSchemeToFilter.get(scheme);
                    if (z2) {
                        Slog.v("IntentResolver", "Scheme list: " + Arrays.toString(objArr8));
                    }
                    objArr4 = objArr8;
                } else {
                    objArr4 = null;
                }
                if (str == null || scheme != null || intent.getAction() == null) {
                    objArr5 = objArr;
                } else {
                    Object[] objArr9 = (Object[]) this.mActionToFilter.get(intent.getAction());
                    if (z2) {
                        Slog.v("IntentResolver", "Action list: " + Arrays.toString(objArr9));
                    }
                    objArr5 = objArr9;
                }
                FastImmutableArraySet fastIntentCategories = getFastIntentCategories(intent);
                Computer computer = (Computer) packageDataSnapshot;
                if (objArr5 != null) {
                    arrayList = arrayList2;
                    str2 = "IntentResolver";
                    i2 = 0;
                    buildResolveList(computer, intent, fastIntentCategories, z2, z, str, scheme, objArr5, arrayList2, i, j);
                } else {
                    str2 = "IntentResolver";
                    arrayList = arrayList2;
                    i2 = 0;
                }
                if (objArr2 != null) {
                    buildResolveList(computer, intent, fastIntentCategories, z2, z, str, scheme, objArr2, arrayList, i, j);
                }
                if (objArr3 != null) {
                    buildResolveList(computer, intent, fastIntentCategories, z2, z, str, scheme, objArr3, arrayList, i, j);
                }
                if (objArr4 != null) {
                    buildResolveList(computer, intent, fastIntentCategories, z2, z, str, scheme, objArr4, arrayList, i, j);
                }
                ArrayList arrayList3 = arrayList;
                filterResults(arrayList3);
                sortResults(arrayList3);
                if (z2) {
                    Slog.v(str2, "Final result list:");
                    for (int i3 = i2; i3 < arrayList3.size(); i3++) {
                        Slog.v(str2, "  " + arrayList3.get(i3));
                    }
                }
                return arrayList3;
            }
            if (intent.getAction() != null) {
                objArr = (Object[]) this.mTypedActionToFilter.get(intent.getAction());
                if (z2) {
                    Slog.v("IntentResolver", "Typed Action list: " + Arrays.toString(objArr));
                }
                objArr2 = null;
                objArr3 = null;
                if (scheme != null) {
                }
                if (str == null) {
                }
                objArr5 = objArr;
                FastImmutableArraySet fastIntentCategories2 = getFastIntentCategories(intent);
                Computer computer2 = (Computer) packageDataSnapshot;
                if (objArr5 != null) {
                }
                if (objArr2 != null) {
                }
                if (objArr3 != null) {
                }
                if (objArr4 != null) {
                }
                ArrayList arrayList32 = arrayList;
                filterResults(arrayList32);
                sortResults(arrayList32);
                if (z2) {
                }
                return arrayList32;
            }
        }
        objArr = null;
        objArr2 = null;
        objArr3 = null;
        if (scheme != null) {
        }
        if (str == null) {
        }
        objArr5 = objArr;
        FastImmutableArraySet fastIntentCategories22 = getFastIntentCategories(intent);
        Computer computer22 = (Computer) packageDataSnapshot;
        if (objArr5 != null) {
        }
        if (objArr2 != null) {
        }
        if (objArr3 != null) {
        }
        if (objArr4 != null) {
        }
        ArrayList arrayList322 = arrayList;
        filterResults(arrayList322);
        sortResults(arrayList322);
        if (z2) {
        }
        return arrayList322;
    }

    public boolean isFilterVerified(Object obj) {
        return getIntentFilter(obj).isVerified();
    }

    public void sortResults(List list) {
        Collections.sort(list, mResolvePrioritySorter);
    }

    public void dumpFilter(PrintWriter printWriter, String str, Object obj) {
        printWriter.print(str);
        printWriter.println(obj);
    }

    public void dumpFilterLabel(PrintWriter printWriter, String str, Object obj, int i) {
        printWriter.print(str);
        printWriter.print(obj);
        printWriter.print(": ");
        printWriter.println(i);
    }

    public final void addFilter(ArrayMap arrayMap, String str, Object obj) {
        Object[] objArr = (Object[]) arrayMap.get(str);
        if (objArr == null) {
            Object[] newArray = newArray(2);
            arrayMap.put(str, newArray);
            newArray[0] = obj;
            return;
        }
        int length = objArr.length;
        int i = length;
        while (i > 0 && objArr[i - 1] == null) {
            i--;
        }
        if (i < length) {
            objArr[i] = obj;
            return;
        }
        Object[] newArray2 = newArray((length * 3) / 2);
        System.arraycopy(objArr, 0, newArray2, 0, length);
        newArray2[length] = obj;
        arrayMap.put(str, newArray2);
    }

    public final int register_mime_types(Object obj, String str) {
        String str2;
        Iterator<String> typesIterator = getIntentFilter(obj).typesIterator();
        if (typesIterator == null) {
            return 0;
        }
        int i = 0;
        while (typesIterator.hasNext()) {
            String next = typesIterator.next();
            i++;
            int indexOf = next.indexOf(47);
            if (indexOf > 0) {
                str2 = next.substring(0, indexOf).intern();
            } else {
                str2 = next;
                next = next + "/*";
            }
            addFilter(this.mTypeToFilter, next, obj);
            if (indexOf > 0) {
                addFilter(this.mBaseTypeToFilter, str2, obj);
            } else {
                addFilter(this.mWildTypeToFilter, str2, obj);
            }
        }
        return i;
    }

    public final int unregister_mime_types(Object obj, String str) {
        String str2;
        Iterator<String> typesIterator = getIntentFilter(obj).typesIterator();
        if (typesIterator == null) {
            return 0;
        }
        int i = 0;
        while (typesIterator.hasNext()) {
            String next = typesIterator.next();
            i++;
            int indexOf = next.indexOf(47);
            if (indexOf > 0) {
                str2 = next.substring(0, indexOf).intern();
            } else {
                str2 = next;
                next = next + "/*";
            }
            remove_all_objects(this.mTypeToFilter, next, obj);
            if (indexOf > 0) {
                remove_all_objects(this.mBaseTypeToFilter, str2, obj);
            } else {
                remove_all_objects(this.mWildTypeToFilter, str2, obj);
            }
        }
        return i;
    }

    public final int register_intent_filter(Object obj, Iterator it, ArrayMap arrayMap, String str) {
        int i = 0;
        if (it == null) {
            return 0;
        }
        while (it.hasNext()) {
            i++;
            addFilter(arrayMap, (String) it.next(), obj);
        }
        return i;
    }

    public final int unregister_intent_filter(Object obj, Iterator it, ArrayMap arrayMap, String str) {
        int i = 0;
        if (it == null) {
            return 0;
        }
        while (it.hasNext()) {
            i++;
            remove_all_objects(arrayMap, (String) it.next(), obj);
        }
        return i;
    }

    public final void remove_all_objects(ArrayMap arrayMap, String str, Object obj) {
        Object[] objArr = (Object[]) arrayMap.get(str);
        if (objArr != null) {
            int length = objArr.length - 1;
            while (length >= 0 && objArr[length] == null) {
                length--;
            }
            int i = length;
            while (length >= 0) {
                Object obj2 = objArr[length];
                if (obj2 != null && getIntentFilter(obj2) == getIntentFilter(obj)) {
                    int i2 = i - length;
                    if (i2 > 0) {
                        System.arraycopy(objArr, length + 1, objArr, length, i2);
                    }
                    objArr[i] = null;
                    i--;
                }
                length--;
            }
            if (i < 0) {
                arrayMap.remove(str);
            } else if (i < objArr.length / 2) {
                Object[] newArray = newArray(i + 2);
                System.arraycopy(objArr, 0, newArray, 0, i + 1);
                arrayMap.put(str, newArray);
            }
        }
    }

    public static FastImmutableArraySet getFastIntentCategories(Intent intent) {
        Set<String> categories = intent.getCategories();
        if (categories == null) {
            return null;
        }
        return new FastImmutableArraySet((String[]) categories.toArray(new String[categories.size()]));
    }

    public final void buildResolveList(Computer computer, Intent intent, FastImmutableArraySet fastImmutableArraySet, boolean z, boolean z2, String str, String str2, Object[] objArr, List list, int i, long j) {
        FastPrintWriter fastPrintWriter;
        LogPrinter logPrinter;
        String str3;
        int i2;
        int i3;
        FastPrintWriter fastPrintWriter2;
        Object[] objArr2 = objArr;
        String action = intent.getAction();
        Uri data = intent.getData();
        String str4 = intent.getPackage();
        boolean isExcludingStopped = intent.isExcludingStopped();
        if (z) {
            LogPrinter logPrinter2 = new LogPrinter(2, "IntentResolver", 3);
            logPrinter = logPrinter2;
            fastPrintWriter = new FastPrintWriter(logPrinter2);
        } else {
            fastPrintWriter = null;
            logPrinter = null;
        }
        int length = objArr2 != null ? objArr2.length : 0;
        int i4 = 0;
        boolean z3 = false;
        while (i4 < length) {
            Object obj = objArr2[i4];
            if (obj == null) {
                break;
            }
            if (z) {
                Slog.v("IntentResolver", "Matching against filter " + obj);
            }
            if (isExcludingStopped && isFilterStopped(computer, obj, i)) {
                if (z) {
                    Slog.v("IntentResolver", "  Filter's target is stopped; skipping");
                }
            } else if (str4 == null || isPackageForFilter(str4, obj)) {
                IntentFilter intentFilter = getIntentFilter(obj);
                if (intentFilter.getAutoVerify() && z) {
                    Slog.v("IntentResolver", "  Filter verified: " + isFilterVerified(obj));
                    int i5 = 0;
                    for (int countDataAuthorities = intentFilter.countDataAuthorities(); i5 < countDataAuthorities; countDataAuthorities = countDataAuthorities) {
                        Slog.v("IntentResolver", "   " + intentFilter.getDataAuthority(i5).getHost());
                        i5++;
                    }
                }
                if (allowFilterResult(obj, list)) {
                    String str5 = action;
                    str3 = action;
                    i2 = i4;
                    i3 = length;
                    fastPrintWriter2 = fastPrintWriter;
                    int match = intentFilter.match(str5, str, str2, data, fastImmutableArraySet, "IntentResolver");
                    if (match >= 0) {
                        if (z) {
                            Slog.v("IntentResolver", "  Filter matched!  match=0x" + Integer.toHexString(match) + " hasDefault=" + intentFilter.hasCategory("android.intent.category.DEFAULT"));
                        }
                        if (!z2 || intentFilter.hasCategory("android.intent.category.DEFAULT")) {
                            Object newResult = newResult(computer, obj, match, i, j);
                            if (z) {
                                Slog.v("IntentResolver", "    Created result: " + newResult);
                            }
                            if (newResult != null) {
                                list.add(newResult);
                                if (z) {
                                    dumpFilter(fastPrintWriter2, "    ", obj);
                                    fastPrintWriter2.flush();
                                    intentFilter.dump(logPrinter, "    ");
                                }
                            }
                        } else {
                            z3 = true;
                        }
                    } else if (z) {
                        Slog.v("IntentResolver", "  Filter did not match: " + (match != -4 ? match != -3 ? match != -2 ? match != -1 ? "unknown reason" : "type" : "data" : "action" : "category"));
                    }
                    i4 = i2 + 1;
                    objArr2 = objArr;
                    fastPrintWriter = fastPrintWriter2;
                    action = str3;
                    length = i3;
                } else if (z) {
                    Slog.v("IntentResolver", "  Filter's target already added");
                }
            } else if (z) {
                Slog.v("IntentResolver", "  Filter is not from package " + str4 + "; skipping");
            }
            i2 = i4;
            i3 = length;
            str3 = action;
            fastPrintWriter2 = fastPrintWriter;
            i4 = i2 + 1;
            objArr2 = objArr;
            fastPrintWriter = fastPrintWriter2;
            action = str3;
            length = i3;
        }
        if (z && z3) {
            if (list.size() == 0) {
                Slog.v("IntentResolver", "resolveIntent failed: found match, but none with CATEGORY_DEFAULT");
            } else if (list.size() > 1) {
                Slog.v("IntentResolver", "resolveIntent: multiple matches, only some with CATEGORY_DEFAULT");
            }
        }
    }

    public void copyInto(ArrayMap arrayMap, ArrayMap arrayMap2) {
        int size = arrayMap2.size();
        arrayMap.clear();
        arrayMap.ensureCapacity(size);
        for (int i = 0; i < size; i++) {
            Object[] objArr = (Object[]) arrayMap2.valueAt(i);
            String str = (String) arrayMap2.keyAt(i);
            Object[] copyOf = Arrays.copyOf(objArr, objArr.length);
            for (int i2 = 0; i2 < copyOf.length; i2++) {
                copyOf[i2] = snapshot(copyOf[i2]);
            }
            arrayMap.put(str, copyOf);
        }
    }

    public void copyInto(ArraySet arraySet, ArraySet arraySet2) {
        arraySet.clear();
        int size = arraySet2.size();
        arraySet.ensureCapacity(size);
        for (int i = 0; i < size; i++) {
            arraySet.append(snapshot(arraySet2.valueAt(i)));
        }
    }

    public void copyFrom(IntentResolver intentResolver) {
        copyInto(this.mFilters, intentResolver.mFilters);
        copyInto(this.mTypeToFilter, intentResolver.mTypeToFilter);
        copyInto(this.mBaseTypeToFilter, intentResolver.mBaseTypeToFilter);
        copyInto(this.mWildTypeToFilter, intentResolver.mWildTypeToFilter);
        copyInto(this.mSchemeToFilter, intentResolver.mSchemeToFilter);
        copyInto(this.mActionToFilter, intentResolver.mActionToFilter);
        copyInto(this.mTypedActionToFilter, intentResolver.mTypedActionToFilter);
    }
}
