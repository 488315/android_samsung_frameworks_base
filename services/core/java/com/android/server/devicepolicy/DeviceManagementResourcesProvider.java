package com.android.server.devicepolicy;

import android.app.admin.ParcelableResource;
import android.os.Environment;
import android.util.AtomicFile;
import android.util.Log;
import android.util.Xml;

import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;

import libcore.io.IoUtils;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class DeviceManagementResourcesProvider {
    public final Injector mInjector;
    public final Object mLock;
    public final Map mUpdatedDrawablesForSource;
    public final Map mUpdatedDrawablesForStyle;
    public final Map mUpdatedStrings;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Injector {}

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ResourcesReaderWriter {
        public final File mFile;

        public ResourcesReaderWriter() {
            DeviceManagementResourcesProvider.this.getClass();
            DeviceManagementResourcesProvider.this.mInjector.getClass();
            this.mFile = new File(Environment.getDataSystemDirectory(), "updated_resources.xml");
        }

        public final void readFromFileLocked() {
            if (!this.mFile.exists()) {
                Log.d("DevicePolicyManagerService", "" + this.mFile + " doesn't exist");
                return;
            }
            Log.d("DevicePolicyManagerService", "Reading from " + this.mFile);
            FileInputStream fileInputStream = null;
            try {
                try {
                    fileInputStream = new AtomicFile(this.mFile).openRead();
                    TypedXmlPullParser resolvePullParser = Xml.resolvePullParser(fileInputStream);
                    int i = 0;
                    while (true) {
                        int next = resolvePullParser.next();
                        if (next == 1) {
                            break;
                        }
                        if (next == 2) {
                            i++;
                            String name = resolvePullParser.getName();
                            if (i == 1) {
                                if (!"root".equals(name)) {
                                    Log.e(
                                            "DevicePolicyManagerService",
                                            "Invalid root tag: " + name);
                                    return;
                                }
                            } else if (!readInner(resolvePullParser, i, name)) {
                                return;
                            }
                        } else if (next == 3) {
                            i--;
                        }
                    }
                } catch (IOException | XmlPullParserException e) {
                    Log.e("DevicePolicyManagerService", "Error parsing resources file", e);
                }
            } finally {
                IoUtils.closeQuietly(fileInputStream);
            }
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Code restructure failed: missing block: B:25:0x0023, code lost:

           if (r10.equals("drawable-style-entry") == false) goto L7;
        */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean readInner(
                com.android.modules.utils.TypedXmlPullParser r8, int r9, java.lang.String r10) {
            /*
                r7 = this;
                r0 = 0
                r1 = 2
                r2 = 1
                if (r9 <= r1) goto L6
                return r2
            L6:
                r10.getClass()
                java.lang.String r9 = "drawable-style"
                java.lang.String r3 = "drawable-id"
                r4 = 0
                com.android.server.devicepolicy.DeviceManagementResourcesProvider r7 = com.android.server.devicepolicy.DeviceManagementResourcesProvider.this
                r5 = -1
                int r6 = r10.hashCode()
                switch(r6) {
                    case -1021023306: goto L32;
                    case 1224071439: goto L26;
                    case 1406273191: goto L1c;
                    default: goto L1a;
                }
            L1a:
                r1 = r5
                goto L3d
            L1c:
                java.lang.String r6 = "drawable-style-entry"
                boolean r6 = r10.equals(r6)
                if (r6 != 0) goto L3d
                goto L1a
            L26:
                java.lang.String r1 = "drawable-source-entry"
                boolean r1 = r10.equals(r1)
                if (r1 != 0) goto L30
                goto L1a
            L30:
                r1 = r2
                goto L3d
            L32:
                java.lang.String r1 = "string-entry"
                boolean r1 = r10.equals(r1)
                if (r1 != 0) goto L3c
                goto L1a
            L3c:
                r1 = r0
            L3d:
                switch(r1) {
                    case 0: goto Ldb;
                    case 1: goto L7c;
                    case 2: goto L4c;
                    default: goto L40;
                }
            L40:
                java.lang.String r7 = "Unexpected tag: "
                java.lang.String r7 = r7.concat(r10)
                java.lang.String r8 = "DevicePolicyManagerService"
                android.util.Log.e(r8, r7)
                return r0
            L4c:
                java.lang.String r10 = r8.getAttributeValue(r4, r3)
                java.lang.String r9 = r8.getAttributeValue(r4, r9)
                android.app.admin.ParcelableResource r8 = android.app.admin.ParcelableResource.createFromXml(r8)
                java.util.Map r0 = r7.mUpdatedDrawablesForStyle
                java.util.HashMap r0 = (java.util.HashMap) r0
                boolean r0 = r0.containsKey(r10)
                if (r0 != 0) goto L6e
                java.util.Map r0 = r7.mUpdatedDrawablesForStyle
                java.util.HashMap r1 = new java.util.HashMap
                r1.<init>()
                java.util.HashMap r0 = (java.util.HashMap) r0
                r0.put(r10, r1)
            L6e:
                java.util.Map r7 = r7.mUpdatedDrawablesForStyle
                java.util.HashMap r7 = (java.util.HashMap) r7
                java.lang.Object r7 = r7.get(r10)
                java.util.Map r7 = (java.util.Map) r7
                r7.put(r9, r8)
                goto Led
            L7c:
                java.lang.String r10 = r8.getAttributeValue(r4, r3)
                java.lang.String r0 = "drawable-source"
                java.lang.String r0 = r8.getAttributeValue(r4, r0)
                java.lang.String r9 = r8.getAttributeValue(r4, r9)
                android.app.admin.ParcelableResource r8 = android.app.admin.ParcelableResource.createFromXml(r8)
                java.util.Map r1 = r7.mUpdatedDrawablesForSource
                java.util.HashMap r1 = (java.util.HashMap) r1
                boolean r1 = r1.containsKey(r10)
                if (r1 != 0) goto La5
                java.util.Map r1 = r7.mUpdatedDrawablesForSource
                java.util.HashMap r3 = new java.util.HashMap
                r3.<init>()
                java.util.HashMap r1 = (java.util.HashMap) r1
                r1.put(r10, r3)
            La5:
                java.util.Map r1 = r7.mUpdatedDrawablesForSource
                java.util.HashMap r1 = (java.util.HashMap) r1
                java.lang.Object r1 = r1.get(r10)
                java.util.Map r1 = (java.util.Map) r1
                boolean r1 = r1.containsKey(r0)
                if (r1 != 0) goto Lc7
                java.util.Map r1 = r7.mUpdatedDrawablesForSource
                java.util.HashMap r1 = (java.util.HashMap) r1
                java.lang.Object r1 = r1.get(r10)
                java.util.Map r1 = (java.util.Map) r1
                java.util.HashMap r3 = new java.util.HashMap
                r3.<init>()
                r1.put(r0, r3)
            Lc7:
                java.util.Map r7 = r7.mUpdatedDrawablesForSource
                java.util.HashMap r7 = (java.util.HashMap) r7
                java.lang.Object r7 = r7.get(r10)
                java.util.Map r7 = (java.util.Map) r7
                java.lang.Object r7 = r7.get(r0)
                java.util.Map r7 = (java.util.Map) r7
                r7.put(r9, r8)
                goto Led
            Ldb:
                java.lang.String r9 = "source-id"
                java.lang.String r9 = r8.getAttributeValue(r4, r9)
                java.util.Map r7 = r7.mUpdatedStrings
                android.app.admin.ParcelableResource r8 = android.app.admin.ParcelableResource.createFromXml(r8)
                java.util.HashMap r7 = (java.util.HashMap) r7
                r7.put(r9, r8)
            Led:
                return r2
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.devicepolicy.DeviceManagementResourcesProvider.ResourcesReaderWriter.readInner(com.android.modules.utils.TypedXmlPullParser,"
                        + " int, java.lang.String):boolean");
        }

        public final void writeInner(TypedXmlSerializer typedXmlSerializer) {
            DeviceManagementResourcesProvider deviceManagementResourcesProvider =
                    DeviceManagementResourcesProvider.this;
            Map map = deviceManagementResourcesProvider.mUpdatedDrawablesForStyle;
            if (map != null && !((HashMap) map).isEmpty()) {
                for (Map.Entry entry :
                        ((HashMap) deviceManagementResourcesProvider.mUpdatedDrawablesForStyle)
                                .entrySet()) {
                    for (Map.Entry entry2 : ((Map) entry.getValue()).entrySet()) {
                        typedXmlSerializer.startTag((String) null, "drawable-style-entry");
                        typedXmlSerializer.attribute(
                                (String) null, "drawable-id", (String) entry.getKey());
                        typedXmlSerializer.attribute(
                                (String) null, "drawable-style", (String) entry2.getKey());
                        ((ParcelableResource) entry2.getValue()).writeToXmlFile(typedXmlSerializer);
                        typedXmlSerializer.endTag((String) null, "drawable-style-entry");
                    }
                }
            }
            Map map2 = deviceManagementResourcesProvider.mUpdatedDrawablesForSource;
            if (map2 != null && !((HashMap) map2).isEmpty()) {
                for (Map.Entry entry3 :
                        ((HashMap) deviceManagementResourcesProvider.mUpdatedDrawablesForSource)
                                .entrySet()) {
                    for (Map.Entry entry4 : ((Map) entry3.getValue()).entrySet()) {
                        for (Map.Entry entry5 : ((Map) entry4.getValue()).entrySet()) {
                            typedXmlSerializer.startTag((String) null, "drawable-source-entry");
                            typedXmlSerializer.attribute(
                                    (String) null, "drawable-id", (String) entry3.getKey());
                            typedXmlSerializer.attribute(
                                    (String) null, "drawable-source", (String) entry4.getKey());
                            typedXmlSerializer.attribute(
                                    (String) null, "drawable-style", (String) entry5.getKey());
                            ((ParcelableResource) entry5.getValue())
                                    .writeToXmlFile(typedXmlSerializer);
                            typedXmlSerializer.endTag((String) null, "drawable-source-entry");
                        }
                    }
                }
            }
            Map map3 = deviceManagementResourcesProvider.mUpdatedStrings;
            if (map3 == null || ((HashMap) map3).isEmpty()) {
                return;
            }
            for (Map.Entry entry6 :
                    ((HashMap) deviceManagementResourcesProvider.mUpdatedStrings).entrySet()) {
                typedXmlSerializer.startTag((String) null, "string-entry");
                typedXmlSerializer.attribute((String) null, "source-id", (String) entry6.getKey());
                ((ParcelableResource) entry6.getValue()).writeToXmlFile(typedXmlSerializer);
                typedXmlSerializer.endTag((String) null, "string-entry");
            }
        }
    }

    public DeviceManagementResourcesProvider() {
        Injector injector = new Injector();
        this.mUpdatedDrawablesForStyle = new HashMap();
        this.mUpdatedDrawablesForSource = new HashMap();
        this.mUpdatedStrings = new HashMap();
        this.mLock = new Object();
        this.mInjector = injector;
    }

    public final ParcelableResource getDrawableForSourceLocked(
            String str, String str2, String str3) {
        if (((HashMap) this.mUpdatedDrawablesForSource).containsKey(str)
                && ((Map) ((HashMap) this.mUpdatedDrawablesForSource).get(str)).containsKey(str3)) {
            return (ParcelableResource)
                    ((Map) ((Map) ((HashMap) this.mUpdatedDrawablesForSource).get(str)).get(str3))
                            .get(str2);
        }
        return null;
    }

    public final ParcelableResource getString(String str) {
        ParcelableResource parcelableResource;
        synchronized (this.mLock) {
            parcelableResource = (ParcelableResource) ((HashMap) this.mUpdatedStrings).get(str);
        }
        return parcelableResource;
    }

    public final void write() {
        FileOutputStream startWrite;
        Log.d("DevicePolicyManagerService", "Writing updated resources to file.");
        ResourcesReaderWriter resourcesReaderWriter = new ResourcesReaderWriter();
        Log.d("DevicePolicyManagerService", "Writing to " + resourcesReaderWriter.mFile);
        AtomicFile atomicFile = new AtomicFile(resourcesReaderWriter.mFile);
        FileOutputStream fileOutputStream = null;
        try {
            startWrite = atomicFile.startWrite();
        } catch (IOException e) {
            e = e;
        }
        try {
            TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(startWrite);
            resolveSerializer.startDocument((String) null, Boolean.TRUE);
            resolveSerializer.startTag((String) null, "root");
            resourcesReaderWriter.writeInner(resolveSerializer);
            resolveSerializer.endTag((String) null, "root");
            resolveSerializer.endDocument();
            resolveSerializer.flush();
            atomicFile.finishWrite(startWrite);
        } catch (IOException e2) {
            e = e2;
            fileOutputStream = startWrite;
            Log.e("DevicePolicyManagerService", "Exception when writing", e);
            if (fileOutputStream != null) {
                atomicFile.failWrite(fileOutputStream);
            }
        }
    }
}
