package com.android.server.devicepolicy;

import android.app.admin.DevicePolicyDrawableResource;
import android.app.admin.DevicePolicyStringResource;
import android.app.admin.ParcelableResource;
import android.os.Environment;
import android.util.AtomicFile;
import android.util.Log;
import android.util.Xml;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import libcore.io.IoUtils;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes2.dex */
public class DeviceManagementResourcesProvider {
    public final Injector mInjector;
    public final Object mLock;
    public final Map mUpdatedDrawablesForSource;
    public final Map mUpdatedDrawablesForStyle;
    public final Map mUpdatedStrings;

    public DeviceManagementResourcesProvider() {
        this(new Injector());
    }

    public DeviceManagementResourcesProvider(Injector injector) {
        this.mUpdatedDrawablesForStyle = new HashMap();
        this.mUpdatedDrawablesForSource = new HashMap();
        this.mUpdatedStrings = new HashMap();
        this.mLock = new Object();
        Objects.requireNonNull(injector);
        this.mInjector = injector;
    }

    public boolean updateDrawables(List list) {
        boolean updateDrawableForSource;
        boolean z = false;
        for (int i = 0; i < list.size(); i++) {
            String drawableId = ((DevicePolicyDrawableResource) list.get(i)).getDrawableId();
            String drawableStyle = ((DevicePolicyDrawableResource) list.get(i)).getDrawableStyle();
            String drawableSource = ((DevicePolicyDrawableResource) list.get(i)).getDrawableSource();
            ParcelableResource resource = ((DevicePolicyDrawableResource) list.get(i)).getResource();
            Objects.requireNonNull(drawableId, "drawableId must be provided.");
            Objects.requireNonNull(drawableStyle, "drawableStyle must be provided.");
            Objects.requireNonNull(drawableSource, "drawableSource must be provided.");
            Objects.requireNonNull(resource, "ParcelableResource must be provided.");
            if ("UNDEFINED".equals(drawableSource)) {
                updateDrawableForSource = updateDrawable(drawableId, drawableStyle, resource);
            } else {
                updateDrawableForSource = updateDrawableForSource(drawableId, drawableSource, drawableStyle, resource);
            }
            z |= updateDrawableForSource;
        }
        if (!z) {
            return false;
        }
        synchronized (this.mLock) {
            write();
        }
        return true;
    }

    public final boolean updateDrawable(String str, String str2, ParcelableResource parcelableResource) {
        synchronized (this.mLock) {
            if (!this.mUpdatedDrawablesForStyle.containsKey(str)) {
                this.mUpdatedDrawablesForStyle.put(str, new HashMap());
            }
            if (parcelableResource.equals((ParcelableResource) ((Map) this.mUpdatedDrawablesForStyle.get(str)).get(str2))) {
                return false;
            }
            ((Map) this.mUpdatedDrawablesForStyle.get(str)).put(str2, parcelableResource);
            return true;
        }
    }

    public final boolean updateDrawableForSource(String str, String str2, String str3, ParcelableResource parcelableResource) {
        synchronized (this.mLock) {
            if (!this.mUpdatedDrawablesForSource.containsKey(str)) {
                this.mUpdatedDrawablesForSource.put(str, new HashMap());
            }
            Map map = (Map) this.mUpdatedDrawablesForSource.get(str);
            if (!map.containsKey(str2)) {
                ((Map) this.mUpdatedDrawablesForSource.get(str)).put(str2, new HashMap());
            }
            if (parcelableResource.equals((ParcelableResource) ((Map) map.get(str2)).get(str3))) {
                return false;
            }
            ((Map) map.get(str2)).put(str3, parcelableResource);
            return true;
        }
    }

    public boolean removeDrawables(List list) {
        synchronized (this.mLock) {
            int i = 0;
            boolean z = false;
            while (true) {
                boolean z2 = true;
                if (i >= list.size()) {
                    break;
                }
                String str = (String) list.get(i);
                if (this.mUpdatedDrawablesForStyle.remove(str) == null && this.mUpdatedDrawablesForSource.remove(str) == null) {
                    z2 = false;
                }
                z |= z2;
                i++;
            }
            if (!z) {
                return false;
            }
            write();
            return true;
        }
    }

    public ParcelableResource getDrawable(String str, String str2, String str3) {
        synchronized (this.mLock) {
            ParcelableResource drawableForSourceLocked = getDrawableForSourceLocked(str, str2, str3);
            if (drawableForSourceLocked != null) {
                return drawableForSourceLocked;
            }
            if (!this.mUpdatedDrawablesForStyle.containsKey(str)) {
                return null;
            }
            return (ParcelableResource) ((Map) this.mUpdatedDrawablesForStyle.get(str)).get(str2);
        }
    }

    public ParcelableResource getDrawableForSourceLocked(String str, String str2, String str3) {
        if (this.mUpdatedDrawablesForSource.containsKey(str) && ((Map) this.mUpdatedDrawablesForSource.get(str)).containsKey(str3)) {
            return (ParcelableResource) ((Map) ((Map) this.mUpdatedDrawablesForSource.get(str)).get(str3)).get(str2);
        }
        return null;
    }

    public boolean updateStrings(List list) {
        boolean z = false;
        for (int i = 0; i < list.size(); i++) {
            String stringId = ((DevicePolicyStringResource) list.get(i)).getStringId();
            ParcelableResource resource = ((DevicePolicyStringResource) list.get(i)).getResource();
            Objects.requireNonNull(stringId, "stringId must be provided.");
            Objects.requireNonNull(resource, "ParcelableResource must be provided.");
            z |= updateString(stringId, resource);
        }
        if (!z) {
            return false;
        }
        synchronized (this.mLock) {
            write();
        }
        return true;
    }

    public final boolean updateString(String str, ParcelableResource parcelableResource) {
        synchronized (this.mLock) {
            if (parcelableResource.equals((ParcelableResource) this.mUpdatedStrings.get(str))) {
                return false;
            }
            this.mUpdatedStrings.put(str, parcelableResource);
            return true;
        }
    }

    public boolean removeStrings(List list) {
        synchronized (this.mLock) {
            int i = 0;
            boolean z = false;
            while (true) {
                boolean z2 = true;
                if (i >= list.size()) {
                    break;
                }
                if (this.mUpdatedStrings.remove((String) list.get(i)) == null) {
                    z2 = false;
                }
                z |= z2;
                i++;
            }
            if (!z) {
                return false;
            }
            write();
            return true;
        }
    }

    public ParcelableResource getString(String str) {
        ParcelableResource parcelableResource;
        synchronized (this.mLock) {
            parcelableResource = (ParcelableResource) this.mUpdatedStrings.get(str);
        }
        return parcelableResource;
    }

    public final void write() {
        Log.d("DevicePolicyManagerService", "Writing updated resources to file.");
        new ResourcesReaderWriter().writeToFileLocked();
    }

    public void load() {
        synchronized (this.mLock) {
            new ResourcesReaderWriter().readFromFileLocked();
        }
    }

    public final File getResourcesFile() {
        return new File(this.mInjector.environmentGetDataSystemDirectory(), "updated_resources.xml");
    }

    public class ResourcesReaderWriter {
        public final File mFile;

        public ResourcesReaderWriter() {
            this.mFile = DeviceManagementResourcesProvider.this.getResourcesFile();
        }

        public void writeToFileLocked() {
            FileOutputStream startWrite;
            Log.d("DevicePolicyManagerService", "Writing to " + this.mFile);
            AtomicFile atomicFile = new AtomicFile(this.mFile);
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
                writeInner(resolveSerializer);
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

        public void readFromFileLocked() {
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
                                    Log.e("DevicePolicyManagerService", "Invalid root tag: " + name);
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

        public void writeInner(TypedXmlSerializer typedXmlSerializer) {
            writeDrawablesForStylesInner(typedXmlSerializer);
            writeDrawablesForSourcesInner(typedXmlSerializer);
            writeStringsInner(typedXmlSerializer);
        }

        public final void writeDrawablesForStylesInner(TypedXmlSerializer typedXmlSerializer) {
            if (DeviceManagementResourcesProvider.this.mUpdatedDrawablesForStyle == null || DeviceManagementResourcesProvider.this.mUpdatedDrawablesForStyle.isEmpty()) {
                return;
            }
            for (Map.Entry entry : DeviceManagementResourcesProvider.this.mUpdatedDrawablesForStyle.entrySet()) {
                for (Map.Entry entry2 : ((Map) entry.getValue()).entrySet()) {
                    typedXmlSerializer.startTag((String) null, "drawable-style-entry");
                    typedXmlSerializer.attribute((String) null, "drawable-id", (String) entry.getKey());
                    typedXmlSerializer.attribute((String) null, "drawable-style", (String) entry2.getKey());
                    ((ParcelableResource) entry2.getValue()).writeToXmlFile(typedXmlSerializer);
                    typedXmlSerializer.endTag((String) null, "drawable-style-entry");
                }
            }
        }

        public final void writeDrawablesForSourcesInner(TypedXmlSerializer typedXmlSerializer) {
            if (DeviceManagementResourcesProvider.this.mUpdatedDrawablesForSource == null || DeviceManagementResourcesProvider.this.mUpdatedDrawablesForSource.isEmpty()) {
                return;
            }
            for (Map.Entry entry : DeviceManagementResourcesProvider.this.mUpdatedDrawablesForSource.entrySet()) {
                for (Map.Entry entry2 : ((Map) entry.getValue()).entrySet()) {
                    for (Map.Entry entry3 : ((Map) entry2.getValue()).entrySet()) {
                        typedXmlSerializer.startTag((String) null, "drawable-source-entry");
                        typedXmlSerializer.attribute((String) null, "drawable-id", (String) entry.getKey());
                        typedXmlSerializer.attribute((String) null, "drawable-source", (String) entry2.getKey());
                        typedXmlSerializer.attribute((String) null, "drawable-style", (String) entry3.getKey());
                        ((ParcelableResource) entry3.getValue()).writeToXmlFile(typedXmlSerializer);
                        typedXmlSerializer.endTag((String) null, "drawable-source-entry");
                    }
                }
            }
        }

        public final void writeStringsInner(TypedXmlSerializer typedXmlSerializer) {
            if (DeviceManagementResourcesProvider.this.mUpdatedStrings == null || DeviceManagementResourcesProvider.this.mUpdatedStrings.isEmpty()) {
                return;
            }
            for (Map.Entry entry : DeviceManagementResourcesProvider.this.mUpdatedStrings.entrySet()) {
                typedXmlSerializer.startTag((String) null, "string-entry");
                typedXmlSerializer.attribute((String) null, "source-id", (String) entry.getKey());
                ((ParcelableResource) entry.getValue()).writeToXmlFile(typedXmlSerializer);
                typedXmlSerializer.endTag((String) null, "string-entry");
            }
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Code restructure failed: missing block: B:26:0x0019, code lost:
        
            if (r8.equals("drawable-style-entry") == false) goto L7;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final boolean readInner(TypedXmlPullParser typedXmlPullParser, int i, String str) {
            char c = 2;
            if (i > 2) {
                return true;
            }
            str.hashCode();
            switch (str.hashCode()) {
                case -1021023306:
                    if (str.equals("string-entry")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 1224071439:
                    if (str.equals("drawable-source-entry")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 1406273191:
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    DeviceManagementResourcesProvider.this.mUpdatedStrings.put(typedXmlPullParser.getAttributeValue((String) null, "source-id"), ParcelableResource.createFromXml(typedXmlPullParser));
                    return true;
                case 1:
                    String attributeValue = typedXmlPullParser.getAttributeValue((String) null, "drawable-id");
                    String attributeValue2 = typedXmlPullParser.getAttributeValue((String) null, "drawable-source");
                    String attributeValue3 = typedXmlPullParser.getAttributeValue((String) null, "drawable-style");
                    ParcelableResource createFromXml = ParcelableResource.createFromXml(typedXmlPullParser);
                    if (!DeviceManagementResourcesProvider.this.mUpdatedDrawablesForSource.containsKey(attributeValue)) {
                        DeviceManagementResourcesProvider.this.mUpdatedDrawablesForSource.put(attributeValue, new HashMap());
                    }
                    if (!((Map) DeviceManagementResourcesProvider.this.mUpdatedDrawablesForSource.get(attributeValue)).containsKey(attributeValue2)) {
                        ((Map) DeviceManagementResourcesProvider.this.mUpdatedDrawablesForSource.get(attributeValue)).put(attributeValue2, new HashMap());
                    }
                    ((Map) ((Map) DeviceManagementResourcesProvider.this.mUpdatedDrawablesForSource.get(attributeValue)).get(attributeValue2)).put(attributeValue3, createFromXml);
                    return true;
                case 2:
                    String attributeValue4 = typedXmlPullParser.getAttributeValue((String) null, "drawable-id");
                    String attributeValue5 = typedXmlPullParser.getAttributeValue((String) null, "drawable-style");
                    ParcelableResource createFromXml2 = ParcelableResource.createFromXml(typedXmlPullParser);
                    if (!DeviceManagementResourcesProvider.this.mUpdatedDrawablesForStyle.containsKey(attributeValue4)) {
                        DeviceManagementResourcesProvider.this.mUpdatedDrawablesForStyle.put(attributeValue4, new HashMap());
                    }
                    ((Map) DeviceManagementResourcesProvider.this.mUpdatedDrawablesForStyle.get(attributeValue4)).put(attributeValue5, createFromXml2);
                    return true;
                default:
                    Log.e("DevicePolicyManagerService", "Unexpected tag: " + str);
                    return false;
            }
        }
    }

    public class Injector {
        public File environmentGetDataSystemDirectory() {
            return Environment.getDataSystemDirectory();
        }
    }
}
