package com.android.server.ibs;

import com.android.server.art.ArtManagerLocal;
import com.android.server.pm.PackageManagerLocal;
import com.android.server.pm.dex.DexoptOptions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class IntelligentBatterySaverDexoptManager {
    public ArtManagerLocal mArtManagerLocal;
    public PackageManagerLocal mPackageManagerLocal;

    public final List dexoptPackages(List list) {
        ArrayList arrayList = new ArrayList();
        try {
            PackageManagerLocal.FilteredSnapshot withFilteredSnapshot =
                    this.mPackageManagerLocal.withFilteredSnapshot();
            try {
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    String str = (String) it.next();
                    int finalStatus =
                            this.mArtManagerLocal
                                    .dexoptPackage(
                                            withFilteredSnapshot,
                                            str,
                                            new DexoptOptions(25, 1541, str, "speed-profile", null)
                                                    .convertToDexoptParams(0))
                                    .getFinalStatus();
                    if (finalStatus != 20 && finalStatus != 10) {
                        arrayList.add(0);
                    }
                    arrayList.add(1);
                }
                if (withFilteredSnapshot != null) {
                    withFilteredSnapshot.close();
                }
            } finally {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }
}
