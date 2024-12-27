package com.android.server.permission.access.appop;

import com.android.modules.utils.BinaryXmlSerializer;
import com.android.server.permission.access.AccessState;
import com.android.server.permission.access.MutableUserState;
import com.android.server.permission.access.immutable.Immutable;
import com.android.server.permission.access.immutable.IndexedMap;
import com.android.server.permission.access.immutable.MutableIndexedReferenceMap;
import com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class PackageAppOpPersistence extends BaseAppOpPersistence {
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00b4, code lost:

       r0 = r10.next();
    */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00b8, code lost:

       if (r0 == 1) goto L70;
    */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00ba, code lost:

       if (r0 == 2) goto L71;
    */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00bc, code lost:

       if (r0 == 3) goto L72;
    */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x00e2, code lost:

       r10 = r12.map.size() - 1;
    */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x00ea, code lost:

       if ((-1) >= r10) goto L87;
    */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x00ec, code lost:

       r0 = r12.map.keyAt(r10);
       r1 = (com.android.server.permission.access.immutable.IndexedMap) r12.valueAt(r10);
       r0 = (java.lang.String) r0;
    */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x0104, code lost:

       if (r11.getExternalState().packageStates.containsKey(r0) != false) goto L89;
    */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x0106, code lost:

       com.android.server.PinnerService$$ExternalSyntheticOutline0.m("Dropping unknown package ", r0, " when parsing app-op state", "PackageAppOpPersistence");
       r0 = ((com.android.server.permission.access.immutable.MutableReference) r12.map.removeAt(r10)).immutable;
       r9.requestWriteMode(1);
    */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x0118, code lost:

       r10 = r10 - 1;
    */
    /* JADX WARN: Code restructure failed: missing block: B:79:?, code lost:

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
            Method dump skipped, instructions count: 284
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.permission.access.appop.PackageAppOpPersistence.parseUserState(com.android.modules.utils.BinaryXmlPullParser,"
                    + " com.android.server.permission.access.MutableAccessState, int):void");
    }

    @Override // com.android.server.permission.access.appop.BaseAppOpPersistence
    public final void serializeUserState(
            BinaryXmlSerializer binaryXmlSerializer, AccessState accessState, int i) {
        Immutable immutable = accessState.getUserStates().get(i);
        Intrinsics.checkNotNull(immutable);
        MutableIndexedReferenceMap packageAppOpModes =
                ((MutableUserState) immutable).getPackageAppOpModes();
        binaryXmlSerializer.startTag((String) null, "package-app-ops");
        int size = packageAppOpModes.map.size();
        for (int i2 = 0; i2 < size; i2++) {
            Object keyAt = packageAppOpModes.map.keyAt(i2);
            IndexedMap indexedMap = (IndexedMap) packageAppOpModes.valueAt(i2);
            binaryXmlSerializer.startTag((String) null, "package");
            binaryXmlSerializer.attributeInterned((String) null, "name", (String) keyAt);
            BaseAppOpPersistence.serializeAppOps(binaryXmlSerializer, indexedMap);
            binaryXmlSerializer.endTag((String) null, "package");
        }
        binaryXmlSerializer.endTag((String) null, "package-app-ops");
    }
}
