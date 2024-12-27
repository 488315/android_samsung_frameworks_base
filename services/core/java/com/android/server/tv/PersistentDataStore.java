package com.android.server.tv;

import android.content.Context;
import android.content.Intent;
import android.media.tv.TvContentRating;
import android.os.Environment;
import android.os.Handler;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.AtomicFile;
import android.util.Slog;
import android.util.Xml;
import com.android.internal.util.XmlUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import libcore.io.IoUtils;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PersistentDataStore {
    public final AtomicFile mAtomicFile;
    public boolean mBlockedRatingsChanged;
    public final Context mContext;
    public boolean mLoaded;
    public boolean mParentalControlsEnabled;
    public boolean mParentalControlsEnabledChanged;
    public final Handler mHandler = new Handler();
    public final List mBlockedRatings = Collections.synchronizedList(new ArrayList());
    public final AnonymousClass1 mSaveRunnable = new Runnable() { // from class: com.android.server.tv.PersistentDataStore.1
        @Override // java.lang.Runnable
        public final void run() {
            PersistentDataStore persistentDataStore = PersistentDataStore.this;
            persistentDataStore.getClass();
            try {
                FileOutputStream startWrite = persistentDataStore.mAtomicFile.startWrite();
                try {
                    TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(startWrite);
                    persistentDataStore.saveToXml(resolveSerializer);
                    resolveSerializer.flush();
                    persistentDataStore.mAtomicFile.finishWrite(startWrite);
                    if (persistentDataStore.mParentalControlsEnabledChanged) {
                        persistentDataStore.mParentalControlsEnabledChanged = false;
                        persistentDataStore.mContext.sendBroadcastAsUser(new Intent("android.media.tv.action.PARENTAL_CONTROLS_ENABLED_CHANGED"), UserHandle.ALL);
                    }
                    if (persistentDataStore.mBlockedRatingsChanged) {
                        persistentDataStore.mBlockedRatingsChanged = false;
                        persistentDataStore.mContext.sendBroadcastAsUser(new Intent("android.media.tv.action.BLOCKED_RATINGS_CHANGED"), UserHandle.ALL);
                    }
                } catch (Throwable th) {
                    persistentDataStore.mAtomicFile.failWrite(startWrite);
                    throw th;
                }
            } catch (IOException e) {
                Slog.w("TvInputManagerService", "Failed to save tv input manager persistent store data.", e);
            }
        }
    };

    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.server.tv.PersistentDataStore$1] */
    public PersistentDataStore(Context context, int i) {
        this.mContext = context;
        File userSystemDirectory = Environment.getUserSystemDirectory(i);
        if (!userSystemDirectory.exists() && !userSystemDirectory.mkdirs()) {
            throw new IllegalStateException(AccountManagerService$$ExternalSyntheticOutline0.m(userSystemDirectory, "User dir cannot be created: "));
        }
        this.mAtomicFile = new AtomicFile(new File(userSystemDirectory, "tv-input-manager-state.xml"), "tv-input-state");
    }

    public final void loadFromXml(TypedXmlPullParser typedXmlPullParser) {
        XmlUtils.beginDocument(typedXmlPullParser, "tv-input-manager-state");
        int depth = typedXmlPullParser.getDepth();
        while (XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
            if (typedXmlPullParser.getName().equals("blocked-ratings")) {
                int depth2 = typedXmlPullParser.getDepth();
                while (XmlUtils.nextElementWithin(typedXmlPullParser, depth2)) {
                    if (typedXmlPullParser.getName().equals("rating")) {
                        String attributeValue = typedXmlPullParser.getAttributeValue((String) null, "string");
                        if (TextUtils.isEmpty(attributeValue)) {
                            throw new XmlPullParserException("Missing string attribute on rating");
                        }
                        this.mBlockedRatings.add(TvContentRating.unflattenFromString(attributeValue));
                    }
                }
            } else if (typedXmlPullParser.getName().equals("parental-controls")) {
                this.mParentalControlsEnabled = typedXmlPullParser.getAttributeBoolean((String) null, "enabled");
            }
        }
    }

    public final void loadIfNeeded() {
        if (this.mLoaded) {
            return;
        }
        this.mBlockedRatings.clear();
        this.mParentalControlsEnabled = false;
        try {
            FileInputStream openRead = this.mAtomicFile.openRead();
            try {
                try {
                    loadFromXml(Xml.resolvePullParser(openRead));
                } finally {
                    IoUtils.closeQuietly(openRead);
                }
            } catch (IOException | XmlPullParserException e) {
                Slog.w("TvInputManagerService", "Failed to load tv input manager persistent store data.", e);
                this.mBlockedRatings.clear();
                this.mParentalControlsEnabled = false;
            }
        } catch (FileNotFoundException unused) {
        }
        this.mLoaded = true;
    }

    public final void saveToXml(TypedXmlSerializer typedXmlSerializer) {
        typedXmlSerializer.startDocument((String) null, Boolean.TRUE);
        typedXmlSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        typedXmlSerializer.startTag((String) null, "tv-input-manager-state");
        typedXmlSerializer.startTag((String) null, "blocked-ratings");
        synchronized (this.mBlockedRatings) {
            try {
                for (TvContentRating tvContentRating : this.mBlockedRatings) {
                    typedXmlSerializer.startTag((String) null, "rating");
                    typedXmlSerializer.attribute((String) null, "string", tvContentRating.flattenToString());
                    typedXmlSerializer.endTag((String) null, "rating");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        typedXmlSerializer.endTag((String) null, "blocked-ratings");
        typedXmlSerializer.startTag((String) null, "parental-controls");
        typedXmlSerializer.attributeBoolean((String) null, "enabled", this.mParentalControlsEnabled);
        typedXmlSerializer.endTag((String) null, "parental-controls");
        typedXmlSerializer.endTag((String) null, "tv-input-manager-state");
        typedXmlSerializer.endDocument();
    }
}
