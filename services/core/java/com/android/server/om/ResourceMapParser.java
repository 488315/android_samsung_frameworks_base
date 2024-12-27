package com.android.server.om;

import android.content.APKContents;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Build;
import android.util.ArrayMap;
import android.util.Log;
import com.android.internal.util.XmlUtils;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.AndroidPackageSplit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import libcore.io.IoUtils;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class ResourceMapParser {
    public static final boolean DEBUG = "eng".equals(Build.TYPE);

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    final class ResourceType {
        public static final /* synthetic */ ResourceType[] $VALUES;
        public static final ResourceType COLOR;
        public static final ResourceType DRAWABLE;

        static {
            ResourceType resourceType = new ResourceType("DRAWABLE", 0);
            DRAWABLE = resourceType;
            ResourceType resourceType2 = new ResourceType("COLOR", 1);
            COLOR = resourceType2;
            $VALUES = new ResourceType[]{resourceType, resourceType2};
        }

        public static ResourceType valueOf(String str) {
            return (ResourceType) Enum.valueOf(ResourceType.class, str);
        }

        public static ResourceType[] values() {
            return (ResourceType[]) $VALUES.clone();
        }
    }

    public static void parseResourceEntries(String str, Resources resources, XmlResourceParser xmlResourceParser, ArrayMap arrayMap, ResourceType resourceType) {
        int i;
        int i2;
        int i3;
        int identifier;
        int depth = xmlResourceParser.getDepth();
        while (true) {
            int next = xmlResourceParser.next();
            int i4 = 1;
            if (next == 1) {
                return;
            }
            int i5 = 3;
            if (next == 3 && xmlResourceParser.getDepth() <= depth) {
                return;
            }
            if (next != 3) {
                int i6 = 4;
                if (next != 4) {
                    String name = xmlResourceParser.getName();
                    String attributeValue = xmlResourceParser.getAttributeValue(null, "overlay");
                    if (!"match".equals(name) || attributeValue == null) {
                        i = depth;
                        Log.w("ResourceMapParser", "Unknown element under <resource-map>: " + xmlResourceParser.getName() + " at  " + xmlResourceParser.getPositionDescription());
                        XmlUtils.skipCurrentTag(xmlResourceParser);
                    } else {
                        ArrayList arrayList = new ArrayList();
                        int depth2 = xmlResourceParser.getDepth();
                        while (true) {
                            int next2 = xmlResourceParser.next();
                            if (next2 == i4 || (next2 == i5 && xmlResourceParser.getDepth() <= depth2)) {
                                break;
                            }
                            if (next2 == i5 || next2 == i6 || !"original".equals(xmlResourceParser.getName())) {
                                i4 = 1;
                            } else {
                                if (xmlResourceParser.next() == i6) {
                                    String text = xmlResourceParser.getText();
                                    if (text == null || text.isEmpty()) {
                                        i2 = depth;
                                    } else {
                                        int ordinal = resourceType.ordinal();
                                        if (ordinal != 0) {
                                            i2 = depth;
                                            identifier = ordinal != 1 ? 0 : resources.getIdentifier(text, "color", str);
                                        } else {
                                            i2 = depth;
                                            identifier = resources.getIdentifier(text, "drawable", str);
                                        }
                                        if (identifier != 0) {
                                            arrayList.add(text);
                                        }
                                    }
                                    i3 = 3;
                                    if (xmlResourceParser.next() != 3 || !"original".equals(xmlResourceParser.getName())) {
                                        Log.w("ResourceMapParser", "Unknown element under <match>: " + xmlResourceParser.getName() + " at  " + xmlResourceParser.getPositionDescription());
                                        XmlUtils.skipCurrentTag(xmlResourceParser);
                                    }
                                } else {
                                    i2 = depth;
                                    i3 = 3;
                                    Log.w("ResourceMapParser", "Unknown element under <match>: " + xmlResourceParser.getName() + " at  " + xmlResourceParser.getPositionDescription());
                                    XmlUtils.skipCurrentTag(xmlResourceParser);
                                }
                                i5 = i3;
                                depth = i2;
                                i4 = 1;
                                i6 = 4;
                            }
                        }
                        i = depth;
                        if (!arrayList.isEmpty()) {
                            Iterator it = arrayList.iterator();
                            while (it.hasNext()) {
                                String str2 = (String) it.next();
                                int ordinal2 = resourceType.ordinal();
                                if (ordinal2 == 0) {
                                    ((ArrayMap) arrayMap.get("drawable")).put(str2, attributeValue);
                                } else if (ordinal2 == 1) {
                                    ((ArrayMap) arrayMap.get("color")).put(str2, attributeValue);
                                }
                            }
                        } else if (DEBUG) {
                            Log.w("ResourceMapParser", "Empty mapping for ".concat(attributeValue));
                        }
                    }
                    depth = i;
                }
            }
        }
    }

    public static void parseResourceMap(AndroidPackage androidPackage) {
        AssetManager assetManager;
        APKContents aPKContents;
        if (DEBUG) {
            Log.d("ResourceMapParser", "parseResourceMap = " + androidPackage.getPackageName());
        }
        XmlResourceParser xmlResourceParser = null;
        try {
            aPKContents = new APKContents(((AndroidPackageSplit) androidPackage.getSplits().get(0)).getPath());
            assetManager = aPKContents.getAssets();
        } catch (IOException | RuntimeException | XmlPullParserException e) {
            e = e;
            assetManager = null;
        } catch (Throwable th) {
            th = th;
            assetManager = null;
            IoUtils.closeQuietly(xmlResourceParser);
            IoUtils.closeQuietly(assetManager);
            throw th;
        }
        try {
            try {
                Resources resources = aPKContents.getResources();
                int identifier = resources.getIdentifier("resource_map", "xml", androidPackage.getPackageName());
                if (identifier != 0) {
                    xmlResourceParser = resources.getXml(identifier);
                    parseResourceMapToFile(androidPackage.getPackageName(), androidPackage.getBaseApkPath(), resources, xmlResourceParser);
                } else {
                    Log.e("ResourceMapParser", "resource_map file not found in res/xml/.. folder");
                }
            } catch (Throwable th2) {
                th = th2;
                IoUtils.closeQuietly(xmlResourceParser);
                IoUtils.closeQuietly(assetManager);
                throw th;
            }
        } catch (IOException | RuntimeException | XmlPullParserException e2) {
            e = e2;
            Log.e("ResourceMapParser", "Failed to parse resource_map");
            e.printStackTrace();
            IoUtils.closeQuietly(xmlResourceParser);
            IoUtils.closeQuietly(assetManager);
        }
        IoUtils.closeQuietly(xmlResourceParser);
        IoUtils.closeQuietly(assetManager);
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0088, code lost:
    
        if (r13.exists() != false) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x008a, code lost:
    
        r13.mkdir();
        android.os.FileUtils.setPermissions(r13, 485, -1, -1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0090, code lost:
    
        r13 = new java.io.File("/data/overlays/remaps/" + r11.replace("/", ".") + ".map");
        r11 = new java.io.BufferedWriter(new java.io.FileWriter(r13, false));
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x00ba, code lost:
    
        if (com.android.server.om.ResourceMapParser.DEBUG == false) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x00bc, code lost:
    
        android.util.Log.d("ResourceMapParser", "create resource map for " + r10);
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x00d1, code lost:
    
        r10 = (android.util.ArrayMap) r0.get("color");
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x00d9, code lost:
    
        if (r10 == null) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x00df, code lost:
    
        if (r10.size() <= 0) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x00e1, code lost:
    
        r11.write("# C\n");
        r10 = r10.entrySet().iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x00f2, code lost:
    
        if (r10.hasNext() == false) goto L91;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x00f4, code lost:
    
        r1 = (java.util.Map.Entry) r10.next();
        r11.write(((java.lang.String) r1.getKey()) + " " + ((java.lang.String) r1.getValue()));
        r11.newLine();
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x011f, code lost:
    
        r10 = (android.util.ArrayMap) r0.get("drawable");
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0125, code lost:
    
        if (r10 == null) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x012b, code lost:
    
        if (r10.size() <= 0) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x012d, code lost:
    
        r11.write("# D\n");
        r10 = r10.entrySet().iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x013e, code lost:
    
        if (r10.hasNext() == false) goto L92;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0140, code lost:
    
        r0 = (java.util.Map.Entry) r10.next();
        r11.write(((java.lang.String) r0.getKey()) + " " + ((java.lang.String) r0.getValue()));
        r11.newLine();
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x016b, code lost:
    
        android.os.FileUtils.setPermissions(r13, 485, -1, -1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x016e, code lost:
    
        r11.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0171, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00ce, code lost:
    
        r10 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0172, code lost:
    
        r11.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x017a, code lost:
    
        throw r10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x0176, code lost:
    
        r11 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0177, code lost:
    
        r10.addSuppressed(r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0078, code lost:
    
        r13 = new java.io.File("/data/overlays/remaps/");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void parseResourceMapToFile(java.lang.String r10, java.lang.String r11, android.content.res.Resources r12, android.content.res.XmlResourceParser r13) {
        /*
            Method dump skipped, instructions count: 379
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.om.ResourceMapParser.parseResourceMapToFile(java.lang.String, java.lang.String, android.content.res.Resources, android.content.res.XmlResourceParser):void");
    }
}
