package com.android.server;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.Uri;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.FastImmutableArraySet;
import android.util.LogPrinter;
import android.util.MutableInt;
import android.util.PrintWriterPrinter;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;

import com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags;
import com.android.internal.util.FastPrintWriter;
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

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class IntentResolver {
    public static final AnonymousClass1 mResolvePrioritySorter = new AnonymousClass1();
    public final ArraySet mFilters = new ArraySet();
    public final ArrayMap mTypeToFilter = new ArrayMap();
    public final ArrayMap mBaseTypeToFilter = new ArrayMap();
    public final ArrayMap mWildTypeToFilter = new ArrayMap();
    public final ArrayMap mSchemeToFilter = new ArrayMap();
    public final ArrayMap mActionToFilter = new ArrayMap();
    public final ArrayMap mTypedActionToFilter = new ArrayMap();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.IntentResolver$1, reason: invalid class name */
    public final class AnonymousClass1 implements Comparator {
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            int priority = ((IntentFilter) obj).getPriority();
            int priority2 = ((IntentFilter) obj2).getPriority();
            if (priority > priority2) {
                return -1;
            }
            return priority < priority2 ? 1 : 0;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class IteratorWrapper implements Iterator {
        public Object mCur;
        public final Iterator mI;

        public IteratorWrapper(Iterator it) {
            this.mI = it;
        }

        @Override // java.util.Iterator
        public final boolean hasNext() {
            return this.mI.hasNext();
        }

        @Override // java.util.Iterator
        public final Object next() {
            Object next = this.mI.next();
            this.mCur = next;
            return next;
        }

        @Override // java.util.Iterator
        public final void remove() {
            Object obj = this.mCur;
            if (obj != null) {
                IntentResolver.this.removeFilterInternal(obj);
            }
            this.mI.remove();
        }
    }

    public static void writeProtoMap(
            ProtoOutputStream protoOutputStream, long j, ArrayMap arrayMap) {
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

    public void addFilter(PackageDataSnapshot packageDataSnapshot, Object obj) {
        String str;
        IntentFilter intentFilter = getIntentFilter(obj);
        this.mFilters.add(obj);
        int register_intent_filter =
                register_intent_filter(obj, intentFilter.schemesIterator(), this.mSchemeToFilter);
        Iterator<String> typesIterator = getIntentFilter(obj).typesIterator();
        int i = 0;
        if (typesIterator != null) {
            int i2 = 0;
            while (typesIterator.hasNext()) {
                String next = typesIterator.next();
                i2++;
                int indexOf = next.indexOf(47);
                if (indexOf > 0) {
                    str = next.substring(0, indexOf).intern();
                } else {
                    str = next;
                    next = next.concat("/*");
                }
                addFilter(this.mTypeToFilter, next, obj);
                if (indexOf > 0) {
                    addFilter(this.mBaseTypeToFilter, str, obj);
                } else {
                    addFilter(this.mWildTypeToFilter, str, obj);
                }
            }
            i = i2;
        }
        if (register_intent_filter == 0 && i == 0) {
            register_intent_filter(obj, intentFilter.actionsIterator(), this.mActionToFilter);
        }
        if (i != 0) {
            register_intent_filter(obj, intentFilter.actionsIterator(), this.mTypedActionToFilter);
        }
    }

    public boolean allowFilterResult(List list, Object obj) {
        return true;
    }

    public final void buildResolveList(
            Computer computer,
            Intent intent,
            FastImmutableArraySet fastImmutableArraySet,
            boolean z,
            boolean z2,
            String str,
            String str2,
            Object[] objArr,
            List list,
            int i,
            long j) {
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
                    Slog.v(
                            "IntentResolver",
                            "  Filter verified: " + getIntentFilter(obj).isVerified());
                    int i5 = 0;
                    for (int countDataAuthorities = intentFilter.countDataAuthorities();
                            i5 < countDataAuthorities;
                            countDataAuthorities = countDataAuthorities) {
                        Slog.v(
                                "IntentResolver",
                                "   " + intentFilter.getDataAuthority(i5).getHost());
                        i5++;
                    }
                }
                if (allowFilterResult(list, obj)) {
                    String str5 = action;
                    str3 = action;
                    i2 = i4;
                    i3 = length;
                    fastPrintWriter2 = fastPrintWriter;
                    int match =
                            intentFilter.match(
                                    str5, str, str2, data, fastImmutableArraySet, "IntentResolver");
                    if (match >= 0) {
                        if (z) {
                            Slog.v(
                                    "IntentResolver",
                                    "  Filter matched!  match=0x"
                                            + Integer.toHexString(match)
                                            + " hasDefault="
                                            + intentFilter.hasCategory(
                                                    "android.intent.category.DEFAULT"));
                        }
                        if (!z2 || intentFilter.hasCategory("android.intent.category.DEFAULT")) {
                            Object newResult = newResult(computer, obj, match, i, j);
                            if (z) {
                                Slog.v("IntentResolver", "    Created result: " + newResult);
                            }
                            if (newResult != null) {
                                ((ArrayList) list).add(newResult);
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
                        Slog.v(
                                "IntentResolver",
                                "  Filter did not match: "
                                        .concat(
                                                match != -4
                                                        ? match != -3
                                                                ? match != -2
                                                                        ? match != -1
                                                                                ? "unknown reason"
                                                                                : "type"
                                                                        : "data"
                                                                : "action"
                                                        : "category"));
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
            ArrayList arrayList = (ArrayList) list;
            if (arrayList.size() == 0) {
                Slog.v(
                        "IntentResolver",
                        "resolveIntent failed: found match, but none with CATEGORY_DEFAULT");
            } else if (arrayList.size() > 1) {
                Slog.v(
                        "IntentResolver",
                        "resolveIntent: multiple matches, only some with CATEGORY_DEFAULT");
            }
        }
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

    public final void copyFrom(IntentResolver intentResolver) {
        ArraySet arraySet = this.mFilters;
        ArraySet arraySet2 = intentResolver.mFilters;
        arraySet.clear();
        int size = arraySet2.size();
        arraySet.ensureCapacity(size);
        for (int i = 0; i < size; i++) {
            arraySet.append(snapshot(arraySet2.valueAt(i)));
        }
        copyInto(this.mTypeToFilter, intentResolver.mTypeToFilter);
        copyInto(this.mBaseTypeToFilter, intentResolver.mBaseTypeToFilter);
        copyInto(this.mWildTypeToFilter, intentResolver.mWildTypeToFilter);
        copyInto(this.mSchemeToFilter, intentResolver.mSchemeToFilter);
        copyInto(this.mActionToFilter, intentResolver.mActionToFilter);
        copyInto(this.mTypedActionToFilter, intentResolver.mTypedActionToFilter);
    }

    public final void copyInto(ArrayMap arrayMap, ArrayMap arrayMap2) {
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

    public final boolean dump(
            PrintWriter printWriter, String str, String str2, String str3, boolean z, boolean z2) {
        String concat = str2.concat("  ");
        String concat2 = "\n".concat(str2);
        String m = AnyMotionDetector$$ExternalSyntheticOutline0.m(str, "\n", str2);
        if (dumpMap(printWriter, m, "Full MIME Types:", concat, this.mTypeToFilter, str3, z, z2)) {
            m = concat2;
        }
        if (dumpMap(
                printWriter, m, "Base MIME Types:", concat, this.mBaseTypeToFilter, str3, z, z2)) {
            m = concat2;
        }
        if (dumpMap(
                printWriter, m, "Wild MIME Types:", concat, this.mWildTypeToFilter, str3, z, z2)) {
            m = concat2;
        }
        if (dumpMap(printWriter, m, "Schemes:", concat, this.mSchemeToFilter, str3, z, z2)) {
            m = concat2;
        }
        if (dumpMap(
                printWriter, m, "Non-Data Actions:", concat, this.mActionToFilter, str3, z, z2)) {
            m = concat2;
        }
        if (dumpMap(
                printWriter,
                m,
                "MIME Typed Actions:",
                concat,
                this.mTypedActionToFilter,
                str3,
                z,
                z2)) {
            m = concat2;
        }
        return m == concat2;
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

    public final boolean dumpMap(
            PrintWriter printWriter,
            String str,
            String str2,
            String str3,
            ArrayMap arrayMap,
            String str4,
            boolean z,
            boolean z2) {
        boolean z3;
        String str5;
        Object obj;
        PrintWriterPrinter printWriterPrinter;
        boolean z4;
        ArrayMap arrayMap2 = arrayMap;
        String m$1 = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str3, "  ");
        String m$12 = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str3, "    ");
        ArrayMap arrayMap3 = new ArrayMap();
        String str6 = str2;
        int i = 0;
        boolean z5 = false;
        PrintWriterPrinter printWriterPrinter2 = null;
        while (i < arrayMap.size()) {
            Object[] objArr = (Object[]) arrayMap2.valueAt(i);
            int length = objArr.length;
            if (!z2 || z) {
                z3 = z5;
                str6 = str6;
                printWriterPrinter2 = printWriterPrinter2;
                int i2 = 0;
                boolean z6 = false;
                while (i2 < length) {
                    Object obj2 = objArr[i2];
                    if (obj2 == null) {
                        break;
                    }
                    if (str4 == null || isPackageForFilter(str4, obj2)) {
                        if (str6 != null) {
                            printWriter.print(str);
                            printWriter.println(str6);
                            str6 = null;
                        }
                        if (!z6) {
                            printWriter.print(m$1);
                            printWriter.print((String) arrayMap2.keyAt(i));
                            printWriter.println(":");
                            z6 = true;
                        }
                        dumpFilter(printWriter, m$12, obj2);
                        if (z) {
                            if (printWriterPrinter2 == null) {
                                printWriterPrinter2 = new PrintWriterPrinter(printWriter);
                            }
                            getIntentFilter(obj2).dump(printWriterPrinter2, m$12 + "  ");
                        }
                        z3 = true;
                    }
                    i2++;
                    arrayMap2 = arrayMap;
                }
            } else {
                arrayMap3.clear();
                int i3 = 0;
                while (true) {
                    str5 = str6;
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
                    str6 = str5;
                    printWriterPrinter2 = printWriterPrinter;
                    z5 = z4;
                }
                z3 = z5;
                PrintWriterPrinter printWriterPrinter3 = printWriterPrinter2;
                str6 = str5;
                int i4 = 0;
                boolean z7 = false;
                while (i4 < arrayMap3.size()) {
                    if (str6 != null) {
                        printWriter.print(str);
                        printWriter.println(str6);
                        str6 = null;
                    }
                    if (!z7) {
                        printWriter.print(m$1);
                        printWriter.print((String) arrayMap2.keyAt(i));
                        printWriter.println(":");
                        z7 = true;
                    }
                    dumpFilterLabel(
                            printWriter,
                            m$12,
                            arrayMap3.keyAt(i4),
                            ((MutableInt) arrayMap3.valueAt(i4)).value);
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

    public final IteratorWrapper filterIterator() {
        return new IteratorWrapper(this.mFilters.iterator());
    }

    public void filterResults(List list) {}

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

    /* JADX WARN: Removed duplicated region for block: B:34:0x0160  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0186 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x01ba  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x01d6  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x01fc  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0218  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0234  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0258  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x01f5  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x01bd  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0182  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.List queryIntent(
            com.android.server.pm.snapshot.PackageDataSnapshot r24,
            android.content.Intent r25,
            java.lang.String r26,
            boolean r27,
            int r28,
            long r29) {
        /*
            Method dump skipped, instructions count: 638
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.IntentResolver.queryIntent(com.android.server.pm.snapshot.PackageDataSnapshot,"
                    + " android.content.Intent, java.lang.String, boolean, int,"
                    + " long):java.util.List");
    }

    public final List queryIntentFromList(
            Computer computer,
            Intent intent,
            String str,
            boolean z,
            ArrayList arrayList,
            int i,
            long j) {
        if ("android.intent.action.PROCESS_TEXT".equals(intent.getAction())
                && Flags.ignoreProcessText()) {
            return Collections.emptyList();
        }
        ArrayList arrayList2 = new ArrayList();
        boolean z2 = (intent.getFlags() & 8) != 0;
        Set<String> categories = intent.getCategories();
        FastImmutableArraySet fastImmutableArraySet =
                categories == null
                        ? null
                        : new FastImmutableArraySet(
                                (String[]) categories.toArray(new String[categories.size()]));
        String scheme = intent.getScheme();
        int i2 = 0;
        for (int size = arrayList.size(); i2 < size; size = size) {
            buildResolveList(
                    computer,
                    intent,
                    fastImmutableArraySet,
                    z2,
                    z,
                    str,
                    scheme,
                    (Object[]) arrayList.get(i2),
                    arrayList2,
                    i,
                    j);
            i2++;
        }
        sortResults(arrayList2);
        return arrayList2;
    }

    public final int register_intent_filter(Object obj, Iterator it, ArrayMap arrayMap) {
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

    public void removeFilter(Object obj) {
        removeFilterInternal(obj);
        this.mFilters.remove(obj);
    }

    public void removeFilterInternal(Object obj) {
        String str;
        IntentFilter intentFilter = getIntentFilter(obj);
        int unregister_intent_filter =
                unregister_intent_filter(obj, intentFilter.schemesIterator(), this.mSchemeToFilter);
        Iterator<String> typesIterator = getIntentFilter(obj).typesIterator();
        int i = 0;
        if (typesIterator != null) {
            int i2 = 0;
            while (typesIterator.hasNext()) {
                String next = typesIterator.next();
                i2++;
                int indexOf = next.indexOf(47);
                if (indexOf > 0) {
                    str = next.substring(0, indexOf).intern();
                } else {
                    str = next;
                    next = next.concat("/*");
                }
                remove_all_objects(this.mTypeToFilter, next, obj);
                if (indexOf > 0) {
                    remove_all_objects(this.mBaseTypeToFilter, str, obj);
                } else {
                    remove_all_objects(this.mWildTypeToFilter, str, obj);
                }
            }
            i = i2;
        }
        if (unregister_intent_filter == 0 && i == 0) {
            unregister_intent_filter(obj, intentFilter.actionsIterator(), this.mActionToFilter);
        }
        if (i != 0) {
            unregister_intent_filter(
                    obj, intentFilter.actionsIterator(), this.mTypedActionToFilter);
        }
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

    public Object snapshot(Object obj) {
        return obj;
    }

    public void sortResults(List list) {
        Collections.sort(list, mResolvePrioritySorter);
    }

    public final int unregister_intent_filter(Object obj, Iterator it, ArrayMap arrayMap) {
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
}
