package com.android.server.pm;

import android.content.Context;
import android.content.pm.IPackageManager;
import android.content.pm.ModuleInfo;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.util.ArrayMap;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public class ModuleInfoProvider {
    public final ApexManager mApexManager;
    public final Context mContext;
    public volatile boolean mMetadataLoaded;
    public final Map mModuleInfo;
    public IPackageManager mPackageManager;
    public volatile String mPackageName;

    public ModuleInfoProvider(Context context) {
        this.mContext = context;
        this.mApexManager = ApexManager.getInstance();
        this.mModuleInfo = new ArrayMap();
    }

    public ModuleInfoProvider(XmlResourceParser xmlResourceParser, Resources resources, ApexManager apexManager) {
        this.mContext = null;
        this.mApexManager = apexManager;
        this.mModuleInfo = new ArrayMap();
        loadModuleMetadata(xmlResourceParser, resources);
    }

    public final ModuleInfo getModuleInfo(String str, int i) {
        if (!this.mMetadataLoaded) {
            throw new IllegalStateException("Call to getModuleInfo before metadata loaded");
        }
        if ((i & 1) == 0) {
            return (ModuleInfo) this.mModuleInfo.get(str);
        }
        for (ModuleInfo moduleInfo : this.mModuleInfo.values()) {
            if (str.equals(moduleInfo.getApexModuleName())) {
                return moduleInfo;
            }
        }
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0020, code lost:
    
        android.util.Slog.w("PackageManager.ModuleInfoProvider", "Unexpected metadata element: " + r7.getName());
        r6.mModuleInfo.clear();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void loadModuleMetadata(android.content.res.XmlResourceParser r7, android.content.res.Resources r8) {
        /*
            r6 = this;
            java.lang.String r0 = "PackageManager.ModuleInfoProvider"
            r1 = 1
            java.lang.String r2 = "module-metadata"
            com.android.internal.util.XmlUtils.beginDocument(r7, r2)     // Catch: java.lang.Throwable -> L43 java.lang.Throwable -> L45
        L9:
            com.android.internal.util.XmlUtils.nextElement(r7)     // Catch: java.lang.Throwable -> L43 java.lang.Throwable -> L45
            int r2 = r7.getEventType()     // Catch: java.lang.Throwable -> L43 java.lang.Throwable -> L45
            if (r2 != r1) goto L13
            goto L3d
        L13:
            java.lang.String r2 = "module"
            java.lang.String r3 = r7.getName()     // Catch: java.lang.Throwable -> L43 java.lang.Throwable -> L45
            boolean r2 = r2.equals(r3)     // Catch: java.lang.Throwable -> L43 java.lang.Throwable -> L45
            if (r2 != 0) goto L47
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L43 java.lang.Throwable -> L45
            r8.<init>()     // Catch: java.lang.Throwable -> L43 java.lang.Throwable -> L45
            java.lang.String r2 = "Unexpected metadata element: "
            r8.append(r2)     // Catch: java.lang.Throwable -> L43 java.lang.Throwable -> L45
            java.lang.String r2 = r7.getName()     // Catch: java.lang.Throwable -> L43 java.lang.Throwable -> L45
            r8.append(r2)     // Catch: java.lang.Throwable -> L43 java.lang.Throwable -> L45
            java.lang.String r8 = r8.toString()     // Catch: java.lang.Throwable -> L43 java.lang.Throwable -> L45
            android.util.Slog.w(r0, r8)     // Catch: java.lang.Throwable -> L43 java.lang.Throwable -> L45
            java.util.Map r8 = r6.mModuleInfo     // Catch: java.lang.Throwable -> L43 java.lang.Throwable -> L45
            r8.clear()     // Catch: java.lang.Throwable -> L43 java.lang.Throwable -> L45
        L3d:
            r7.close()
            r6.mMetadataLoaded = r1
            goto La0
        L43:
            r8 = move-exception
            goto La1
        L45:
            r8 = move-exception
            goto L95
        L47:
            java.lang.String r2 = "name"
            r3 = 0
            java.lang.String r2 = r7.getAttributeValue(r3, r2)     // Catch: java.lang.Throwable -> L43 java.lang.Throwable -> L45 java.lang.Throwable -> L45
            java.lang.String r2 = r2.substring(r1)     // Catch: java.lang.Throwable -> L43 java.lang.Throwable -> L45 java.lang.Throwable -> L45
            int r2 = java.lang.Integer.parseInt(r2)     // Catch: java.lang.Throwable -> L43 java.lang.Throwable -> L45 java.lang.Throwable -> L45
            java.lang.CharSequence r2 = r8.getText(r2)     // Catch: java.lang.Throwable -> L43 java.lang.Throwable -> L45 java.lang.Throwable -> L45
            java.lang.String r3 = "packageName"
            java.lang.String r3 = com.android.internal.util.XmlUtils.readStringAttribute(r7, r3)     // Catch: java.lang.Throwable -> L43 java.lang.Throwable -> L45 java.lang.Throwable -> L45
            java.lang.String r4 = "isHidden"
            boolean r4 = com.android.internal.util.XmlUtils.readBooleanAttribute(r7, r4)     // Catch: java.lang.Throwable -> L43 java.lang.Throwable -> L45 java.lang.Throwable -> L45
            android.content.pm.ModuleInfo r5 = new android.content.pm.ModuleInfo     // Catch: java.lang.Throwable -> L43 java.lang.Throwable -> L45 java.lang.Throwable -> L45
            r5.<init>()     // Catch: java.lang.Throwable -> L43 java.lang.Throwable -> L45 java.lang.Throwable -> L45
            r5.setHidden(r4)     // Catch: java.lang.Throwable -> L43 java.lang.Throwable -> L45 java.lang.Throwable -> L45
            r5.setPackageName(r3)     // Catch: java.lang.Throwable -> L43 java.lang.Throwable -> L45 java.lang.Throwable -> L45
            r5.setName(r2)     // Catch: java.lang.Throwable -> L43 java.lang.Throwable -> L45 java.lang.Throwable -> L45
            com.android.server.pm.ApexManager r2 = r6.mApexManager     // Catch: java.lang.Throwable -> L43 java.lang.Throwable -> L45 java.lang.Throwable -> L45
            java.lang.String r2 = r2.getApexModuleNameForPackageName(r3)     // Catch: java.lang.Throwable -> L43 java.lang.Throwable -> L45 java.lang.Throwable -> L45
            r5.setApexModuleName(r2)     // Catch: java.lang.Throwable -> L43 java.lang.Throwable -> L45 java.lang.Throwable -> L45
            boolean r2 = com.android.internal.hidden_from_bootclasspath.android.content.pm.Flags.provideInfoOfApkInApex()     // Catch: java.lang.Throwable -> L43 java.lang.Throwable -> L45 java.lang.Throwable -> L45
            if (r2 == 0) goto L8e
            com.android.server.pm.ApexManager r2 = r6.mApexManager     // Catch: java.lang.Throwable -> L43 java.lang.Throwable -> L45 java.lang.Throwable -> L45
            java.util.List r2 = r2.getApksInApex(r3)     // Catch: java.lang.Throwable -> L43 java.lang.Throwable -> L45 java.lang.Throwable -> L45
            r5.setApkInApexPackageNames(r2)     // Catch: java.lang.Throwable -> L43 java.lang.Throwable -> L45 java.lang.Throwable -> L45
        L8e:
            java.util.Map r2 = r6.mModuleInfo     // Catch: java.lang.Throwable -> L43 java.lang.Throwable -> L45 java.lang.Throwable -> L45
            r2.put(r3, r5)     // Catch: java.lang.Throwable -> L43 java.lang.Throwable -> L45 java.lang.Throwable -> L45
            goto L9
        L95:
            java.lang.String r2 = "Error parsing module metadata"
            android.util.Slog.w(r0, r2, r8)     // Catch: java.lang.Throwable -> L43
            java.util.Map r8 = r6.mModuleInfo     // Catch: java.lang.Throwable -> L43
            r8.clear()     // Catch: java.lang.Throwable -> L43
            goto L3d
        La0:
            return
        La1:
            r7.close()
            r6.mMetadataLoaded = r1
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.ModuleInfoProvider.loadModuleMetadata(android.content.res.XmlResourceParser, android.content.res.Resources):void");
    }
}
