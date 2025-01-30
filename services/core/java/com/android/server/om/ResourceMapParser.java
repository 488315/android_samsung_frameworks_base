package com.android.server.om;

import android.content.APKContents;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Build;
import android.os.FileUtils;
import android.util.ArrayMap;
import android.util.Log;
import com.android.internal.util.XmlUtils;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.AndroidPackageSplit;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import libcore.io.IoUtils;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes2.dex */
public abstract class ResourceMapParser {
    public static final boolean DEBUG = "eng".equals(Build.TYPE);

    enum ResourceType {
        DRAWABLE,
        COLOR
    }

    public static void parseResourceMap(AndroidPackage androidPackage) {
        AssetManager assetManager;
        if (DEBUG) {
            Log.d("ResourceMapParser", "parseResourceMap = " + androidPackage.getPackageName());
        }
        XmlResourceParser xmlResourceParser = null;
        try {
            try {
                APKContents aPKContents = new APKContents(((AndroidPackageSplit) androidPackage.getSplits().get(0)).getPath());
                assetManager = aPKContents.getAssets();
                try {
                    Resources resources = aPKContents.getResources();
                    int identifier = resources.getIdentifier("resource_map", "xml", androidPackage.getPackageName());
                    if (identifier != 0) {
                        xmlResourceParser = resources.getXml(identifier);
                        parseResourceMapToFile(androidPackage.getPackageName(), androidPackage.getBaseApkPath(), resources, xmlResourceParser);
                    } else {
                        Log.e("ResourceMapParser", "resource_map file not found in res/xml/.. folder");
                    }
                } catch (IOException | RuntimeException | XmlPullParserException e) {
                    e = e;
                    Log.e("ResourceMapParser", "Failed to parse resource_map");
                    e.printStackTrace();
                    IoUtils.closeQuietly(xmlResourceParser);
                    IoUtils.closeQuietly(assetManager);
                }
            } catch (Throwable th) {
                th = th;
                IoUtils.closeQuietly(xmlResourceParser);
                IoUtils.closeQuietly(assetManager);
                throw th;
            }
        } catch (IOException | RuntimeException | XmlPullParserException e2) {
            e = e2;
            assetManager = null;
        } catch (Throwable th2) {
            th = th2;
            assetManager = null;
            IoUtils.closeQuietly(xmlResourceParser);
            IoUtils.closeQuietly(assetManager);
            throw th;
        }
        IoUtils.closeQuietly(xmlResourceParser);
        IoUtils.closeQuietly(assetManager);
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0080, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x007d, code lost:
    
        writeMapFile(r7, r8, r0);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void parseResourceMapToFile(String str, String str2, Resources resources, XmlResourceParser xmlResourceParser) {
        String name;
        ArrayMap arrayMap = new ArrayMap();
        xmlResourceParser.getEventType();
        int depth = xmlResourceParser.getDepth();
        boolean z = false;
        while (true) {
            int next = xmlResourceParser.next();
            if (next == 1 || (next == 3 && xmlResourceParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4 && (name = xmlResourceParser.getName()) != null) {
                if (z) {
                    if ("drawable".equals(name)) {
                        if (arrayMap.get("drawable") == null) {
                            arrayMap.put("drawable", new ArrayMap());
                        }
                        if (!parseResourceEntries(str, resources, xmlResourceParser, arrayMap, ResourceType.DRAWABLE)) {
                            return;
                        }
                    } else if ("color".equals(name)) {
                        if (arrayMap.get("color") == null) {
                            arrayMap.put("color", new ArrayMap());
                        }
                        if (!parseResourceEntries(str, resources, xmlResourceParser, arrayMap, ResourceType.COLOR)) {
                            return;
                        }
                    } else {
                        continue;
                    }
                } else {
                    if (!"resource-map".equals(name)) {
                        throw new XmlPullParserException("Invalid resource_map XML");
                    }
                    z = true;
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x00bf, code lost:
    
        return true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean parseResourceEntries(String str, Resources resources, XmlResourceParser xmlResourceParser, ArrayMap arrayMap, ResourceType resourceType) {
        int depth = xmlResourceParser.getDepth();
        while (true) {
            int next = xmlResourceParser.next();
            if (next == 1 || (next == 3 && xmlResourceParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4) {
                String name = xmlResourceParser.getName();
                String attributeValue = xmlResourceParser.getAttributeValue(null, "overlay");
                if ("match".equals(name) && attributeValue != null) {
                    ArrayList arrayList = new ArrayList();
                    if (!parseEntry(str, resources, xmlResourceParser, arrayList, resourceType)) {
                        return false;
                    }
                    if (!arrayList.isEmpty()) {
                        Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            String str2 = (String) it.next();
                            int i = AbstractC20531.$SwitchMap$com$android$server$om$ResourceMapParser$ResourceType[resourceType.ordinal()];
                            if (i == 1) {
                                ((ArrayMap) arrayMap.get("color")).put(str2, attributeValue);
                            } else if (i == 2) {
                                ((ArrayMap) arrayMap.get("drawable")).put(str2, attributeValue);
                            }
                        }
                    } else if (DEBUG) {
                        Log.w("ResourceMapParser", "Empty mapping for " + attributeValue);
                    }
                } else {
                    Log.w("ResourceMapParser", "Unknown element under <resource-map>: " + xmlResourceParser.getName() + " at  " + xmlResourceParser.getPositionDescription());
                    XmlUtils.skipCurrentTag(xmlResourceParser);
                }
            }
        }
    }

    /* renamed from: com.android.server.om.ResourceMapParser$1 */
    public abstract /* synthetic */ class AbstractC20531 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$server$om$ResourceMapParser$ResourceType;

        static {
            int[] iArr = new int[ResourceType.values().length];
            $SwitchMap$com$android$server$om$ResourceMapParser$ResourceType = iArr;
            try {
                iArr[ResourceType.COLOR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$server$om$ResourceMapParser$ResourceType[ResourceType.DRAWABLE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public static boolean parseEntry(String str, Resources resources, XmlResourceParser xmlResourceParser, ArrayList arrayList, ResourceType resourceType) {
        int identifier;
        int depth = xmlResourceParser.getDepth();
        while (true) {
            int next = xmlResourceParser.next();
            if (next == 1 || (next == 3 && xmlResourceParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4 && "original".equals(xmlResourceParser.getName())) {
                if (xmlResourceParser.next() == 4) {
                    String text = xmlResourceParser.getText();
                    if (text != null && !text.isEmpty()) {
                        int i = AbstractC20531.$SwitchMap$com$android$server$om$ResourceMapParser$ResourceType[resourceType.ordinal()];
                        if (i == 1) {
                            identifier = resources.getIdentifier(text, "color", str);
                        } else {
                            identifier = i != 2 ? 0 : resources.getIdentifier(text, "drawable", str);
                        }
                        if (identifier != 0) {
                            arrayList.add(text);
                        }
                    }
                    if (xmlResourceParser.next() != 3 || !"original".equals(xmlResourceParser.getName())) {
                        Log.w("ResourceMapParser", "Unknown element under <match>: " + xmlResourceParser.getName() + " at  " + xmlResourceParser.getPositionDescription());
                        XmlUtils.skipCurrentTag(xmlResourceParser);
                    }
                } else {
                    Log.w("ResourceMapParser", "Unknown element under <match>: " + xmlResourceParser.getName() + " at  " + xmlResourceParser.getPositionDescription());
                    XmlUtils.skipCurrentTag(xmlResourceParser);
                }
            }
        }
        return true;
    }

    public static void writeMapFile(String str, String str2, ArrayMap arrayMap) {
        File file = new File("/data/overlays/remaps/");
        if (!file.exists()) {
            file.mkdir();
            FileUtils.setPermissions(file, 485, -1, -1);
        }
        File file2 = new File("/data/overlays/remaps/" + str2.replace("/", ".") + ".map");
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file2, false));
        try {
            if (DEBUG) {
                Log.d("ResourceMapParser", "create resource map for " + str);
            }
            ArrayMap arrayMap2 = (ArrayMap) arrayMap.get("color");
            if (arrayMap2 != null && arrayMap2.size() > 0) {
                bufferedWriter.write("# C\n");
                for (Map.Entry entry : arrayMap2.entrySet()) {
                    bufferedWriter.write(((String) entry.getKey()) + " " + ((String) entry.getValue()));
                    bufferedWriter.newLine();
                }
            }
            ArrayMap arrayMap3 = (ArrayMap) arrayMap.get("drawable");
            if (arrayMap3 != null && arrayMap3.size() > 0) {
                bufferedWriter.write("# D\n");
                for (Map.Entry entry2 : arrayMap3.entrySet()) {
                    bufferedWriter.write(((String) entry2.getKey()) + " " + ((String) entry2.getValue()));
                    bufferedWriter.newLine();
                }
            }
            FileUtils.setPermissions(file2, 485, -1, -1);
            bufferedWriter.close();
        } catch (Throwable th) {
            try {
                bufferedWriter.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }
}
