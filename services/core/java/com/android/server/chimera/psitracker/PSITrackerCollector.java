package com.android.server.chimera.psitracker;

import java.util.ArrayList;
import java.util.List;

public final class PSITrackerCollector {
    public static boolean DEBUG = true;
    public static int availableMemSaveCount;
    public static boolean availableMemUpdateFlag;
    public final List mAvailableMemRecords;

    public PSITrackerCollector() {
        DEBUG = true;
        this.mAvailableMemRecords = new ArrayList();
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void saveAvailableMemRecord2db(
            com.android.server.chimera.psitracker.PSIAvailableMemRecord r8) {
        /*
            Method dump skipped, instructions count: 338
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.chimera.psitracker.PSITrackerCollector.saveAvailableMemRecord2db(com.android.server.chimera.psitracker.PSIAvailableMemRecord):void");
    }
}
