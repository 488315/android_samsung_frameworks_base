package com.android.server.om.wallpapertheme;

import android.content.om.FabricatedOverlay;
import com.android.server.om.wallpapertheme.MetaDataManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class OverlayGenerator {
    public MetaDataManager mMetaDataManager;
    public TemplateManager mTemplateManager;
    public ThemePalette mThemePalette;

    public OverlayGenerator(MetaDataManager metaDataManager, TemplateManager templateManager, ThemePalette themePalette) {
        this.mMetaDataManager = metaDataManager;
        this.mTemplateManager = templateManager;
        this.mThemePalette = themePalette;
    }

    public ArrayList createThemeOverlays(String str) {
        ArrayList arrayList = new ArrayList();
        Iterator it = this.mMetaDataManager.getPackageList().iterator();
        while (it.hasNext()) {
            MetaDataManager.Package r2 = (MetaDataManager.Package) it.next();
            if (!"SESL".equals(r2.getPackageName()) && !"Multi window".equals(r2.getPackageName())) {
                arrayList.add(createOverlayForPkg(str, r2));
            }
        }
        return arrayList;
    }

    public FabricatedOverlay createThemeOverlay(String str, String str2) {
        Iterator it = this.mMetaDataManager.getPackageList().iterator();
        while (it.hasNext()) {
            MetaDataManager.Package r1 = (MetaDataManager.Package) it.next();
            if (str2.equals(r1.getPackageName())) {
                return createOverlayForPkg(str, r1);
            }
        }
        return null;
    }

    public final FabricatedOverlay createOverlayForPkg(String str, MetaDataManager.Package r5) {
        List uidList = r5.getUidList();
        FabricatedOverlay build = new FabricatedOverlay.Builder("android", str + "_" + r5.getPackageName(), r5.getPackageName()).build();
        writeResources(uidList, build);
        return build;
    }

    public final void writeResources(List list, FabricatedOverlay fabricatedOverlay) {
        List booleans;
        Iterator it = list.iterator();
        while (it.hasNext()) {
            MetaDataManager.Uid uid = (MetaDataManager.Uid) it.next();
            String uidValue = uid.getUidValue();
            String destAttribName = uid.getDestAttribName();
            if (destAttribName != null && !destAttribName.isEmpty()) {
                String[] split = destAttribName.split(",");
                int type = uid.getType();
                if (type == 1) {
                    int parseInt = uid.getOpacity() != null ? Integer.parseInt(uid.getOpacity()) : 100;
                    List colors = this.mTemplateManager.getColors(uid.getUidValue());
                    if (colors != null) {
                        if (parseInt != 100) {
                            float f = parseInt / 100.0f;
                            colors.set(0, ThemeUtil.adjustAlpha(f, ((Integer) colors.get(0)).intValue()));
                            colors.set(1, ThemeUtil.adjustAlpha(f, ((Integer) colors.get(1)).intValue()));
                        }
                        for (String str : split) {
                            String str2 = "color/" + str.trim();
                            fabricatedOverlay.setResourceValue(str2, 28, ((Integer) colors.get(0)).intValue(), (String) null);
                            fabricatedOverlay.setResourceValue(str2, 28, ((Integer) colors.get(1)).intValue(), "night");
                        }
                    }
                } else if (type == 2) {
                    Integer integer = this.mTemplateManager.getInteger(uidValue);
                    if (integer != null) {
                        for (String str3 : split) {
                            fabricatedOverlay.setResourceValue("integer/" + str3.trim(), 16, integer.intValue(), (String) null);
                        }
                    }
                } else if (type == 3 && (booleans = this.mTemplateManager.getBooleans(uidValue)) != null) {
                    for (String str4 : split) {
                        String str5 = "bool/" + str4.trim();
                        fabricatedOverlay.setResourceValue(str5, 18, ((Boolean) booleans.get(0)).booleanValue() ? 1 : 0, (String) null);
                        fabricatedOverlay.setResourceValue(str5, 18, ((Boolean) booleans.get(1)).booleanValue() ? 1 : 0, "night");
                    }
                }
            }
        }
    }

    public ArrayList createMonetOverlay() {
        String valueOf;
        String str;
        FabricatedOverlay build = new FabricatedOverlay.Builder("android", "SemWT_MonetPalette", "android").build();
        FabricatedOverlay build2 = new FabricatedOverlay.Builder("android", "SemWT_G_MonetPalette", "android").build();
        for (int i = 0; i < 5; i++) {
            if (i < 3) {
                valueOf = String.valueOf(i + 1);
                str = "accent";
            } else {
                valueOf = String.valueOf(i - 2);
                str = "neutral";
            }
            for (int i2 = 0; i2 < 13; i2++) {
                StringBuilder sb = new StringBuilder();
                sb.append("android:color/system_");
                sb.append(str);
                sb.append(valueOf);
                sb.append('_');
                if (i2 == 0) {
                    sb.append("0");
                } else if (i2 == 1) {
                    sb.append("10");
                } else if (i2 == 2) {
                    sb.append("50");
                } else {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(i2 - 2);
                    sb2.append("00");
                    sb.append(sb2.toString());
                }
                String sb3 = sb.toString();
                build.setResourceValue(sb3, 28, this.mThemePalette.getMonetColorSS(i, i2), (String) null);
                build2.setResourceValue(sb3, 28, this.mThemePalette.getMonetColorGG(i, i2), (String) null);
            }
        }
        Iterator it = this.mMetaDataManager.getPackageList().iterator();
        while (it.hasNext()) {
            MetaDataManager.Package r3 = (MetaDataManager.Package) it.next();
            if ("Multi window".equals(r3.getPackageName())) {
                List uidList = r3.getUidList();
                writeResources(uidList, build);
                writeResources(uidList, build2);
            }
        }
        return new ArrayList(Arrays.asList(build, build2));
    }
}
