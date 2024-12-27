package com.android.server.permission.access.appop;

import com.android.modules.utils.BinaryXmlSerializer;
import com.android.server.permission.access.AccessState;
import com.android.server.permission.access.MutableUserState;
import com.android.server.permission.access.immutable.Immutable;
import com.android.server.permission.access.immutable.IndexedMap;
import com.android.server.permission.access.immutable.MutableIntReferenceMap;
import com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class AppIdAppOpPersistence extends BaseAppOpPersistence {
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00a4, code lost:

       r0 = r10.next();
    */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00a8, code lost:

       if (r0 == 1) goto L72;
    */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00aa, code lost:

       if (r0 == 2) goto L73;
    */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00ac, code lost:

       if (r0 == 3) goto L74;
    */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x00d2, code lost:

       r10 = r9.getAppIdAppOpModes();
       r0 = r10.array.size() - 1;
    */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x00de, code lost:

       if ((-1) >= r0) goto L89;
    */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x00e0, code lost:

       r1 = r10.array.keyAt(r0);
       r2 = (com.android.server.permission.access.immutable.IndexedMap) r10.valueAt(r0);
    */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x00fa, code lost:

       if (r11.getExternalState().getAppIdPackageNames().array.contains(r1) != false) goto L91;
    */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x00fe, code lost:

       if (r1 < 10000) goto L92;
    */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x0100, code lost:

       com.android.server.accessibility.BrailleDisplayConnection$$ExternalSyntheticOutline0.m(r1, "Dropping unknown app ID ", " when parsing app-op state", "AppIdAppOpPersistence");
       r12.removeAt$1(r0);
       r9.requestWriteMode(1);
    */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x010b, code lost:

       r0 = r0 - 1;
    */
    /* JADX WARN: Code restructure failed: missing block: B:82:?, code lost:

       return;
    */
    @Override // com.android.server.permission.access.appop.BaseAppOpPersistence
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void parseUserState(
            com.android.modules.utils.BinaryXmlPullParser r10,
            com.android.server.permission.access.MutableAccessState r11,
            int r12) {
        /*
            Method dump skipped, instructions count: 271
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.permission.access.appop.AppIdAppOpPersistence.parseUserState(com.android.modules.utils.BinaryXmlPullParser,"
                    + " com.android.server.permission.access.MutableAccessState, int):void");
    }

    @Override // com.android.server.permission.access.appop.BaseAppOpPersistence
    public final void serializeUserState(
            BinaryXmlSerializer binaryXmlSerializer, AccessState accessState, int i) {
        Immutable immutable = accessState.getUserStates().get(i);
        Intrinsics.checkNotNull(immutable);
        MutableIntReferenceMap appIdAppOpModes =
                ((MutableUserState) immutable).getAppIdAppOpModes();
        binaryXmlSerializer.startTag((String) null, "app-id-app-ops");
        int size = appIdAppOpModes.array.size();
        for (int i2 = 0; i2 < size; i2++) {
            int keyAt = appIdAppOpModes.array.keyAt(i2);
            IndexedMap indexedMap = (IndexedMap) appIdAppOpModes.valueAt(i2);
            binaryXmlSerializer.startTag((String) null, "app-id");
            binaryXmlSerializer.attributeInt((String) null, "id", keyAt);
            BaseAppOpPersistence.serializeAppOps(binaryXmlSerializer, indexedMap);
            binaryXmlSerializer.endTag((String) null, "app-id");
        }
        binaryXmlSerializer.endTag((String) null, "app-id-app-ops");
    }
}
