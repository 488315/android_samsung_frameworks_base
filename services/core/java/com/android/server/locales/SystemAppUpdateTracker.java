package com.android.server.locales;

import android.content.Context;
import android.text.TextUtils;
import android.util.AtomicFile;
import android.util.Slog;
import android.util.Xml;

import com.android.internal.util.XmlUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SystemAppUpdateTracker {
    public final Context mContext;
    public final LocaleManagerService mLocaleManagerService;
    public final AtomicFile mUpdatedAppsFile;
    public final Object mFileLock = new Object();
    public final Set mUpdatedApps = new HashSet();

    public SystemAppUpdateTracker(
            Context context, LocaleManagerService localeManagerService, AtomicFile atomicFile) {
        this.mContext = context;
        this.mLocaleManagerService = localeManagerService;
        this.mUpdatedAppsFile = atomicFile;
    }

    public Set getUpdatedApps() {
        return this.mUpdatedApps;
    }

    public final void readFromXml(InputStream inputStream) {
        TypedXmlPullParser newFastPullParser = Xml.newFastPullParser();
        newFastPullParser.setInput(inputStream, StandardCharsets.UTF_8.name());
        XmlUtils.beginDocument(newFastPullParser, "system_apps");
        int depth = newFastPullParser.getDepth();
        while (XmlUtils.nextElementWithin(newFastPullParser, depth)) {
            if (newFastPullParser.getName().equals("package")) {
                String attributeValue = newFastPullParser.getAttributeValue((String) null, "name");
                if (!TextUtils.isEmpty(attributeValue)) {
                    ((HashSet) this.mUpdatedApps).add(attributeValue);
                }
            }
        }
    }

    public final void updateBroadcastedAppsList(String str) {
        synchronized (this.mFileLock) {
            ((HashSet) this.mUpdatedApps).add(str);
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = this.mUpdatedAppsFile.startWrite();
                writeToXmlLocked(fileOutputStream);
                this.mUpdatedAppsFile.finishWrite(fileOutputStream);
            } catch (IOException e) {
                this.mUpdatedAppsFile.failWrite(fileOutputStream);
                Slog.e("SystemAppUpdateTracker", "Failed to persist the updated apps list", e);
            }
        }
    }

    public final void writeToXmlLocked(OutputStream outputStream) {
        TypedXmlSerializer newFastSerializer = Xml.newFastSerializer();
        newFastSerializer.setOutput(outputStream, StandardCharsets.UTF_8.name());
        newFastSerializer.startDocument((String) null, Boolean.TRUE);
        newFastSerializer.startTag((String) null, "system_apps");
        Iterator it = ((HashSet) this.mUpdatedApps).iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            newFastSerializer.startTag((String) null, "package");
            newFastSerializer.attribute((String) null, "name", str);
            newFastSerializer.endTag((String) null, "package");
        }
        newFastSerializer.endTag((String) null, "system_apps");
        newFastSerializer.endDocument();
    }
}
