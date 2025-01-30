package com.android.server.permission.access;

import android.util.ArrayMap;
import android.util.Log;
import android.util.SparseArray;
import com.android.modules.utils.BinaryXmlPullParser;
import com.android.modules.utils.BinaryXmlSerializer;
import com.android.server.pm.permission.PermissionAllowlist;
import com.android.server.pm.pkg.PackageState;
import com.android.server.permission.access.appop.PackageAppOpPolicy;
import com.android.server.permission.access.appop.UidAppOpPolicy;
import com.android.server.permission.access.collection.IndexedListSet;
import com.android.server.permission.access.collection.IntSet;
import com.android.server.permission.access.collection.IntSetKt;
import com.android.server.permission.access.permission.UidPermissionPolicy;
import com.android.server.permission.jarjar.kotlin.jvm.internal.DefaultConstructorMarker;
import com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics;
import java.util.Iterator;
import java.util.Map;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: AccessPolicy.kt */
/* loaded from: classes2.dex */
public final class AccessPolicy {
    public static final Companion Companion = new Companion(null);
    public static final String LOG_TAG = AccessPolicy.class.getSimpleName();
    public final ArrayMap schemePolicies;

    /* compiled from: AccessPolicy.kt */
    public final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public AccessPolicy(ArrayMap arrayMap) {
        this.schemePolicies = arrayMap;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public AccessPolicy() {
        this(r0);
        ArrayMap arrayMap = new ArrayMap();
        _init_$lambda$1$addPolicy(arrayMap, new UidPermissionPolicy());
        _init_$lambda$1$addPolicy(arrayMap, new UidAppOpPolicy());
        _init_$lambda$1$addPolicy(arrayMap, new PackageAppOpPolicy());
    }

    public static final SchemePolicy _init_$lambda$1$addPolicy(ArrayMap arrayMap, SchemePolicy schemePolicy) {
        String subjectScheme = schemePolicy.getSubjectScheme();
        Object obj = arrayMap.get(subjectScheme);
        if (obj == null) {
            obj = new ArrayMap();
            arrayMap.put(subjectScheme, obj);
        }
        return (SchemePolicy) ((ArrayMap) obj).put(schemePolicy.getObjectScheme(), schemePolicy);
    }

    public final SchemePolicy getSchemePolicy(String str, String str2) {
        ArrayMap arrayMap = (ArrayMap) this.schemePolicies.get(str);
        SchemePolicy schemePolicy = arrayMap != null ? (SchemePolicy) arrayMap.get(str2) : null;
        if (schemePolicy != null) {
            return schemePolicy;
        }
        throw new IllegalStateException(("Scheme policy for " + str + " and " + str2 + " does not exist").toString());
    }

    /* JADX WARN: Code restructure failed: missing block: B:122:0x01a5, code lost:
    
        r0 = r18.next();
     */
    /* JADX WARN: Code restructure failed: missing block: B:123:0x01aa, code lost:
    
        if (r0 == 1) goto L122;
     */
    /* JADX WARN: Code restructure failed: missing block: B:124:0x01ac, code lost:
    
        if (r0 == 2) goto L123;
     */
    /* JADX WARN: Code restructure failed: missing block: B:126:0x01af, code lost:
    
        if (r0 == 3) goto L124;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x0111, code lost:
    
        r5 = r18.next();
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x0116, code lost:
    
        if (r5 == 1) goto L133;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x0118, code lost:
    
        if (r5 == 2) goto L134;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x011b, code lost:
    
        if (r5 == 3) goto L135;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void parseSystemState(BinaryXmlPullParser binaryXmlPullParser, AccessState accessState) {
        int next;
        int i;
        int i2;
        int next2;
        int next3;
        int next4;
        int next5;
        int next6;
        int eventType = binaryXmlPullParser.getEventType();
        if (eventType != 0 && eventType != 2) {
            throw new XmlPullParserException("Unexpected event type " + eventType);
        }
        do {
            next = binaryXmlPullParser.next();
            i = 3;
            i2 = 1;
            if (next == 1 || next == 2) {
                break;
            }
        } while (next != 3);
        while (true) {
            int eventType2 = binaryXmlPullParser.getEventType();
            if (eventType2 == i2) {
                return;
            }
            if (eventType2 != 2) {
                if (eventType2 == i) {
                    return;
                }
                throw new XmlPullParserException("Unexpected event type " + eventType2);
            }
            int depth = binaryXmlPullParser.getDepth();
            if (Intrinsics.areEqual(binaryXmlPullParser.getName(), "access")) {
                int eventType3 = binaryXmlPullParser.getEventType();
                if (eventType3 != 0 && eventType3 != 2) {
                    throw new XmlPullParserException("Unexpected event type " + eventType3);
                }
                do {
                    next4 = binaryXmlPullParser.next();
                    if (next4 == i2 || next4 == 2) {
                        break;
                    }
                } while (next4 != i);
                while (true) {
                    int eventType4 = binaryXmlPullParser.getEventType();
                    if (eventType4 == i2) {
                        break;
                    }
                    if (eventType4 == 2) {
                        int depth2 = binaryXmlPullParser.getDepth();
                        ArrayMap arrayMap = this.schemePolicies;
                        int size = arrayMap.size();
                        for (int i3 = 0; i3 < size; i3++) {
                            ArrayMap arrayMap2 = (ArrayMap) arrayMap.valueAt(i3);
                            int size2 = arrayMap2.size();
                            for (int i4 = 0; i4 < size2; i4++) {
                                ((SchemePolicy) arrayMap2.valueAt(i4)).parseSystemState(binaryXmlPullParser, accessState);
                            }
                        }
                        int depth3 = binaryXmlPullParser.getDepth();
                        if (depth3 != depth2) {
                            throw new XmlPullParserException("Unexpected post-block depth " + depth3 + ", expected " + depth2);
                        }
                        while (true) {
                            int eventType5 = binaryXmlPullParser.getEventType();
                            if (eventType5 == 2) {
                                do {
                                    next6 = binaryXmlPullParser.next();
                                    if (next6 != 1 && next6 != 2) {
                                    }
                                } while (next6 != 3);
                            } else {
                                if (eventType5 != 3) {
                                    throw new XmlPullParserException("Unexpected event type " + eventType5);
                                }
                                if (binaryXmlPullParser.getDepth() <= depth2) {
                                    break;
                                }
                                do {
                                    next5 = binaryXmlPullParser.next();
                                    if (next5 != 1 && next5 != 2) {
                                    }
                                } while (next5 != 3);
                            }
                        }
                    } else if (eventType4 != i) {
                        throw new XmlPullParserException("Unexpected event type " + eventType4);
                    }
                    i = 3;
                    i2 = 1;
                }
            } else {
                Log.w(LOG_TAG, "Ignoring unknown tag " + binaryXmlPullParser.getName() + " when parsing system state");
            }
            int depth4 = binaryXmlPullParser.getDepth();
            if (depth4 != depth) {
                throw new XmlPullParserException("Unexpected post-block depth " + depth4 + ", expected " + depth);
            }
            while (true) {
                int eventType6 = binaryXmlPullParser.getEventType();
                if (eventType6 == 2) {
                    do {
                        next3 = binaryXmlPullParser.next();
                        if (next3 == 1 || next3 == 2) {
                            break;
                        }
                    } while (next3 != 3);
                } else {
                    if (eventType6 != 3) {
                        throw new XmlPullParserException("Unexpected event type " + eventType6);
                    }
                    if (binaryXmlPullParser.getDepth() <= depth) {
                        break;
                    }
                    do {
                        next2 = binaryXmlPullParser.next();
                        if (next2 != 1 && next2 != 2) {
                        }
                    } while (next2 != 3);
                }
            }
            i = 3;
            i2 = 1;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:121:0x01b3, code lost:
    
        r1 = r18.next();
     */
    /* JADX WARN: Code restructure failed: missing block: B:122:0x01b8, code lost:
    
        if (r1 == 1) goto L123;
     */
    /* JADX WARN: Code restructure failed: missing block: B:124:0x01bb, code lost:
    
        if (r1 == 2) goto L124;
     */
    /* JADX WARN: Code restructure failed: missing block: B:126:0x01be, code lost:
    
        if (r1 == 3) goto L125;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x0117, code lost:
    
        r5 = r18.next();
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x011c, code lost:
    
        if (r5 == 1) goto L134;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x011f, code lost:
    
        if (r5 == 2) goto L135;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x0122, code lost:
    
        if (r5 == 3) goto L136;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void parseUserState(BinaryXmlPullParser binaryXmlPullParser, AccessState accessState, int i) {
        int next;
        int i2;
        int i3;
        int next2;
        int next3;
        int next4;
        int eventType = binaryXmlPullParser.getEventType();
        int i4 = 2;
        if (eventType != 0 && eventType != 2) {
            throw new XmlPullParserException("Unexpected event type " + eventType);
        }
        do {
            next = binaryXmlPullParser.next();
            i2 = 3;
            i3 = 1;
            if (next == 1 || next == 2) {
                break;
            }
        } while (next != 3);
        while (true) {
            int eventType2 = binaryXmlPullParser.getEventType();
            if (eventType2 == i3) {
                return;
            }
            if (eventType2 != i4) {
                if (eventType2 == i2) {
                    return;
                }
                throw new XmlPullParserException("Unexpected event type " + eventType2);
            }
            int depth = binaryXmlPullParser.getDepth();
            if (Intrinsics.areEqual(binaryXmlPullParser.getName(), "access")) {
                int eventType3 = binaryXmlPullParser.getEventType();
                if (eventType3 != 0 && eventType3 != i4) {
                    throw new XmlPullParserException("Unexpected event type " + eventType3);
                }
                do {
                    next3 = binaryXmlPullParser.next();
                    if (next3 == i3 || next3 == i4) {
                        break;
                    }
                } while (next3 != i2);
                while (true) {
                    int eventType4 = binaryXmlPullParser.getEventType();
                    if (eventType4 == i3) {
                        break;
                    }
                    if (eventType4 == i4) {
                        int depth2 = binaryXmlPullParser.getDepth();
                        ArrayMap arrayMap = this.schemePolicies;
                        int size = arrayMap.size();
                        for (int i5 = 0; i5 < size; i5++) {
                            ArrayMap arrayMap2 = (ArrayMap) arrayMap.valueAt(i5);
                            int size2 = arrayMap2.size();
                            for (int i6 = 0; i6 < size2; i6++) {
                                ((SchemePolicy) arrayMap2.valueAt(i6)).parseUserState(binaryXmlPullParser, accessState, i);
                            }
                        }
                        int depth3 = binaryXmlPullParser.getDepth();
                        if (depth3 != depth2) {
                            throw new XmlPullParserException("Unexpected post-block depth " + depth3 + ", expected " + depth2);
                        }
                        while (true) {
                            int eventType5 = binaryXmlPullParser.getEventType();
                            if (eventType5 == 2) {
                                do {
                                    next4 = binaryXmlPullParser.next();
                                    if (next4 != 1 && next4 != 2) {
                                    }
                                } while (next4 != 3);
                            } else {
                                if (eventType5 != 3) {
                                    throw new XmlPullParserException("Unexpected event type " + eventType5);
                                }
                                if (binaryXmlPullParser.getDepth() <= depth2) {
                                    break;
                                }
                                while (true) {
                                    int next5 = binaryXmlPullParser.next();
                                    int i7 = (next5 == 1 || next5 == i7 || next5 == 3) ? 2 : 2;
                                }
                            }
                        }
                    } else if (eventType4 != i2) {
                        throw new XmlPullParserException("Unexpected event type " + eventType4);
                    }
                    i4 = 2;
                    i2 = 3;
                    i3 = 1;
                }
            } else {
                Log.w(LOG_TAG, "Ignoring unknown tag " + binaryXmlPullParser.getName() + " when parsing user state for user " + i);
            }
            int depth4 = binaryXmlPullParser.getDepth();
            if (depth4 != depth) {
                throw new XmlPullParserException("Unexpected post-block depth " + depth4 + ", expected " + depth);
            }
            while (true) {
                int eventType6 = binaryXmlPullParser.getEventType();
                if (eventType6 == 2) {
                    do {
                        next2 = binaryXmlPullParser.next();
                        if (next2 != 1 && next2 != 2) {
                        }
                    } while (next2 != 3);
                } else {
                    if (eventType6 != 3) {
                        throw new XmlPullParserException("Unexpected event type " + eventType6);
                    }
                    if (binaryXmlPullParser.getDepth() <= depth) {
                        break;
                    }
                    while (true) {
                        int next6 = binaryXmlPullParser.next();
                        int i8 = (next6 == 1 || next6 == i8 || next6 == 3) ? 2 : 2;
                    }
                }
            }
            i4 = 2;
            i2 = 3;
            i3 = 1;
        }
    }

    public final int getDecision(GetStateScope getStateScope, AccessUri accessUri, AccessUri accessUri2) {
        return getSchemePolicy(accessUri, accessUri2).getDecision(getStateScope, accessUri, accessUri2);
    }

    public final void serializeSystemState(BinaryXmlSerializer binaryXmlSerializer, AccessState accessState) {
        binaryXmlSerializer.startTag((String) null, "access");
        ArrayMap arrayMap = this.schemePolicies;
        int size = arrayMap.size();
        for (int i = 0; i < size; i++) {
            ArrayMap arrayMap2 = (ArrayMap) arrayMap.valueAt(i);
            int size2 = arrayMap2.size();
            for (int i2 = 0; i2 < size2; i2++) {
                ((SchemePolicy) arrayMap2.valueAt(i2)).serializeSystemState(binaryXmlSerializer, accessState);
            }
        }
        binaryXmlSerializer.endTag((String) null, "access");
    }

    public final void serializeUserState(BinaryXmlSerializer binaryXmlSerializer, AccessState accessState, int i) {
        binaryXmlSerializer.startTag((String) null, "access");
        ArrayMap arrayMap = this.schemePolicies;
        int size = arrayMap.size();
        for (int i2 = 0; i2 < size; i2++) {
            ArrayMap arrayMap2 = (ArrayMap) arrayMap.valueAt(i2);
            int size2 = arrayMap2.size();
            for (int i3 = 0; i3 < size2; i3++) {
                ((SchemePolicy) arrayMap2.valueAt(i3)).serializeUserState(binaryXmlSerializer, accessState, i);
            }
        }
        binaryXmlSerializer.endTag((String) null, "access");
    }

    public final void setDecision(MutateStateScope mutateStateScope, AccessUri accessUri, AccessUri accessUri2, int i) {
        getSchemePolicy(accessUri, accessUri2).setDecision(mutateStateScope, accessUri, accessUri2, i);
    }

    public final void initialize(AccessState accessState, IntSet intSet, Map map, Map map2, SparseArray sparseArray, boolean z, Map map3, IndexedListSet indexedListSet, PermissionAllowlist permissionAllowlist, ArrayMap arrayMap) {
        SystemState systemState = accessState.getSystemState();
        IntSetKt.plusAssign(systemState.getUserIds(), intSet);
        systemState.setPackageStates(map);
        systemState.setDisabledSystemPackageStates(map2);
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            PackageState packageState = (PackageState) ((Map.Entry) it.next()).getValue();
            SparseArray appIds = systemState.getAppIds();
            int appId = packageState.getAppId();
            Object obj = appIds.get(appId);
            if (obj == null) {
                obj = new IndexedListSet();
                appIds.put(appId, obj);
            }
            ((IndexedListSet) obj).add(packageState.getPackageName());
        }
        systemState.setKnownPackages(sparseArray);
        systemState.setLeanback(z);
        systemState.setConfigPermissions(map3);
        systemState.setPrivilegedPermissionAllowlistPackages(indexedListSet);
        systemState.setPermissionAllowlist(permissionAllowlist);
        systemState.setImplicitToSourcePermissions(arrayMap);
        SparseArray userStates = accessState.getUserStates();
        int size = intSet.getSize();
        for (int i = 0; i < size; i++) {
            userStates.set(intSet.elementAt(i), new UserState());
        }
    }

    public final void onUserAdded(MutateStateScope mutateStateScope, int i) {
        mutateStateScope.getNewState().getSystemState().getUserIds().add(i);
        mutateStateScope.getNewState().getUserStates().set(i, new UserState());
        ArrayMap arrayMap = this.schemePolicies;
        int size = arrayMap.size();
        for (int i2 = 0; i2 < size; i2++) {
            ArrayMap arrayMap2 = (ArrayMap) arrayMap.valueAt(i2);
            int size2 = arrayMap2.size();
            for (int i3 = 0; i3 < size2; i3++) {
                ((SchemePolicy) arrayMap2.valueAt(i3)).onUserAdded(mutateStateScope, i);
            }
        }
    }

    public final void onUserRemoved(MutateStateScope mutateStateScope, int i) {
        mutateStateScope.getNewState().getSystemState().getUserIds().remove(i);
        mutateStateScope.getNewState().getUserStates().remove(i);
        ArrayMap arrayMap = this.schemePolicies;
        int size = arrayMap.size();
        for (int i2 = 0; i2 < size; i2++) {
            ArrayMap arrayMap2 = (ArrayMap) arrayMap.valueAt(i2);
            int size2 = arrayMap2.size();
            for (int i3 = 0; i3 < size2; i3++) {
                ((SchemePolicy) arrayMap2.valueAt(i3)).onUserRemoved(mutateStateScope, i);
            }
        }
    }

    public final void onStorageVolumeMounted(MutateStateScope mutateStateScope, Map map, Map map2, SparseArray sparseArray, String str, boolean z) {
        IntSet intSet = new IntSet();
        SystemState systemState = mutateStateScope.getNewState().getSystemState();
        systemState.setPackageStates(map);
        systemState.setDisabledSystemPackageStates(map2);
        for (Map.Entry entry : map.entrySet()) {
            String str2 = (String) entry.getKey();
            PackageState packageState = (PackageState) entry.getValue();
            if (Intrinsics.areEqual(packageState.getVolumeUuid(), str)) {
                int appId = packageState.getAppId();
                SparseArray appIds = systemState.getAppIds();
                Object obj = appIds.get(appId);
                if (obj == null) {
                    intSet.add(appId);
                    obj = new IndexedListSet();
                    appIds.put(appId, obj);
                }
                ((IndexedListSet) obj).add(str2);
            }
        }
        systemState.setKnownPackages(sparseArray);
        int size = intSet.getSize();
        for (int i = 0; i < size; i++) {
            int elementAt = intSet.elementAt(i);
            ArrayMap arrayMap = this.schemePolicies;
            int size2 = arrayMap.size();
            for (int i2 = 0; i2 < size2; i2++) {
                ArrayMap arrayMap2 = (ArrayMap) arrayMap.valueAt(i2);
                int size3 = arrayMap2.size();
                for (int i3 = 0; i3 < size3; i3++) {
                    ((SchemePolicy) arrayMap2.valueAt(i3)).onAppIdAdded(mutateStateScope, elementAt);
                }
            }
        }
        ArrayMap arrayMap3 = this.schemePolicies;
        int size4 = arrayMap3.size();
        for (int i4 = 0; i4 < size4; i4++) {
            ArrayMap arrayMap4 = (ArrayMap) arrayMap3.valueAt(i4);
            int size5 = arrayMap4.size();
            for (int i5 = 0; i5 < size5; i5++) {
                ((SchemePolicy) arrayMap4.valueAt(i5)).onStorageVolumeMounted(mutateStateScope, str, z);
            }
        }
    }

    public final void onPackageAdded(MutateStateScope mutateStateScope, Map map, Map map2, SparseArray sparseArray, String str) {
        boolean z;
        PackageState packageState = (PackageState) map.get(str);
        if (packageState == null) {
            throw new IllegalStateException(("Added package " + str + " isn't found in packageStates in onPackageAdded()").toString());
        }
        int appId = packageState.getAppId();
        SystemState systemState = mutateStateScope.getNewState().getSystemState();
        systemState.setPackageStates(map);
        systemState.setDisabledSystemPackageStates(map2);
        SparseArray appIds = systemState.getAppIds();
        Object obj = appIds.get(appId);
        if (obj != null) {
            z = false;
        } else {
            obj = new IndexedListSet();
            appIds.put(appId, obj);
            z = true;
        }
        ((IndexedListSet) obj).add(str);
        systemState.setKnownPackages(sparseArray);
        if (z) {
            ArrayMap arrayMap = this.schemePolicies;
            int size = arrayMap.size();
            for (int i = 0; i < size; i++) {
                ArrayMap arrayMap2 = (ArrayMap) arrayMap.valueAt(i);
                int size2 = arrayMap2.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    ((SchemePolicy) arrayMap2.valueAt(i2)).onAppIdAdded(mutateStateScope, appId);
                }
            }
        }
        ArrayMap arrayMap3 = this.schemePolicies;
        int size3 = arrayMap3.size();
        for (int i3 = 0; i3 < size3; i3++) {
            ArrayMap arrayMap4 = (ArrayMap) arrayMap3.valueAt(i3);
            int size4 = arrayMap4.size();
            for (int i4 = 0; i4 < size4; i4++) {
                ((SchemePolicy) arrayMap4.valueAt(i4)).onPackageAdded(mutateStateScope, packageState);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0041  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x005f  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0084 A[ORIG_RETURN, RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onPackageRemoved(MutateStateScope mutateStateScope, Map map, Map map2, SparseArray sparseArray, String str, int i) {
        int size;
        int i2;
        boolean z = true;
        if (!(!map.containsKey(str))) {
            throw new IllegalStateException(("Removed package " + str + " is still in packageStates in onPackageRemoved()").toString());
        }
        SystemState systemState = mutateStateScope.getNewState().getSystemState();
        systemState.setPackageStates(map);
        systemState.setDisabledSystemPackageStates(map2);
        IndexedListSet indexedListSet = (IndexedListSet) systemState.getAppIds().get(i);
        if (indexedListSet != null) {
            indexedListSet.remove(str);
            if (indexedListSet.isEmpty()) {
                systemState.getAppIds().remove(i);
                systemState.setKnownPackages(sparseArray);
                ArrayMap arrayMap = this.schemePolicies;
                size = arrayMap.size();
                for (i2 = 0; i2 < size; i2++) {
                    ArrayMap arrayMap2 = (ArrayMap) arrayMap.valueAt(i2);
                    int size2 = arrayMap2.size();
                    for (int i3 = 0; i3 < size2; i3++) {
                        ((SchemePolicy) arrayMap2.valueAt(i3)).onPackageRemoved(mutateStateScope, str, i);
                    }
                }
                if (z) {
                    return;
                }
                ArrayMap arrayMap3 = this.schemePolicies;
                int size3 = arrayMap3.size();
                for (int i4 = 0; i4 < size3; i4++) {
                    ArrayMap arrayMap4 = (ArrayMap) arrayMap3.valueAt(i4);
                    int size4 = arrayMap4.size();
                    for (int i5 = 0; i5 < size4; i5++) {
                        ((SchemePolicy) arrayMap4.valueAt(i5)).onAppIdRemoved(mutateStateScope, i);
                    }
                }
                return;
            }
        }
        z = false;
        systemState.setKnownPackages(sparseArray);
        ArrayMap arrayMap5 = this.schemePolicies;
        size = arrayMap5.size();
        while (i2 < size) {
        }
        if (z) {
        }
    }

    public final void onPackageInstalled(MutateStateScope mutateStateScope, Map map, Map map2, SparseArray sparseArray, String str, int i) {
        SystemState systemState = mutateStateScope.getNewState().getSystemState();
        systemState.setPackageStates(map);
        systemState.setDisabledSystemPackageStates(map2);
        systemState.setKnownPackages(sparseArray);
        PackageState packageState = (PackageState) map.get(str);
        if (packageState == null) {
            throw new IllegalStateException(("Installed package " + str + " isn't found in packageStates in onPackageInstalled()").toString());
        }
        ArrayMap arrayMap = this.schemePolicies;
        int size = arrayMap.size();
        for (int i2 = 0; i2 < size; i2++) {
            ArrayMap arrayMap2 = (ArrayMap) arrayMap.valueAt(i2);
            int size2 = arrayMap2.size();
            for (int i3 = 0; i3 < size2; i3++) {
                ((SchemePolicy) arrayMap2.valueAt(i3)).onPackageInstalled(mutateStateScope, packageState, i);
            }
        }
    }

    public final void onPackageUninstalled(MutateStateScope mutateStateScope, Map map, Map map2, SparseArray sparseArray, String str, int i, int i2) {
        SystemState systemState = mutateStateScope.getNewState().getSystemState();
        systemState.setPackageStates(map);
        systemState.setDisabledSystemPackageStates(map2);
        systemState.setKnownPackages(sparseArray);
        ArrayMap arrayMap = this.schemePolicies;
        int size = arrayMap.size();
        for (int i3 = 0; i3 < size; i3++) {
            ArrayMap arrayMap2 = (ArrayMap) arrayMap.valueAt(i3);
            int size2 = arrayMap2.size();
            for (int i4 = 0; i4 < size2; i4++) {
                ((SchemePolicy) arrayMap2.valueAt(i4)).onPackageUninstalled(mutateStateScope, str, i, i2);
            }
        }
    }

    public final void onSystemReady(MutateStateScope mutateStateScope) {
        mutateStateScope.getNewState().getSystemState().setSystemReady(true);
        ArrayMap arrayMap = this.schemePolicies;
        int size = arrayMap.size();
        for (int i = 0; i < size; i++) {
            ArrayMap arrayMap2 = (ArrayMap) arrayMap.valueAt(i);
            int size2 = arrayMap2.size();
            for (int i2 = 0; i2 < size2; i2++) {
                ((SchemePolicy) arrayMap2.valueAt(i2)).onSystemReady(mutateStateScope);
            }
        }
    }

    public final SchemePolicy getSchemePolicy(AccessUri accessUri, AccessUri accessUri2) {
        throw null;
    }

    public final void onInitialized(MutateStateScope mutateStateScope) {
        ArrayMap arrayMap = this.schemePolicies;
        int size = arrayMap.size();
        for (int i = 0; i < size; i++) {
            ArrayMap arrayMap2 = (ArrayMap) arrayMap.valueAt(i);
            int size2 = arrayMap2.size();
            for (int i2 = 0; i2 < size2; i2++) {
                ((SchemePolicy) arrayMap2.valueAt(i2)).onInitialized(mutateStateScope);
            }
        }
    }

    public final void onStateMutated(GetStateScope getStateScope) {
        ArrayMap arrayMap = this.schemePolicies;
        int size = arrayMap.size();
        for (int i = 0; i < size; i++) {
            ArrayMap arrayMap2 = (ArrayMap) arrayMap.valueAt(i);
            int size2 = arrayMap2.size();
            for (int i2 = 0; i2 < size2; i2++) {
                ((SchemePolicy) arrayMap2.valueAt(i2)).onStateMutated(getStateScope);
            }
        }
    }
}
