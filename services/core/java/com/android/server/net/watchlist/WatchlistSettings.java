package com.android.server.net.watchlist;

import android.os.Environment;
import android.util.AtomicFile;
import android.util.Log;
import android.util.Slog;
import android.util.Xml;

import com.android.internal.util.HexDump;
import com.android.internal.util.XmlUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.SecureRandom;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class WatchlistSettings {
    public static final WatchlistSettings sInstance =
            new WatchlistSettings(
                    new File(Environment.getDataSystemDirectory(), "watchlist_settings.xml"));
    public final byte[] mPrivacySecretKey;
    public final AtomicFile mXmlFile;

    public WatchlistSettings(File file) {
        this.mPrivacySecretKey = null;
        AtomicFile atomicFile = new AtomicFile(file, "net-watchlist");
        this.mXmlFile = atomicFile;
        if (atomicFile.exists()) {
            try {
                try {
                    FileInputStream openRead = atomicFile.openRead();
                    try {
                        TypedXmlPullParser resolvePullParser = Xml.resolvePullParser(openRead);
                        XmlUtils.beginDocument(resolvePullParser, "network-watchlist-settings");
                        int depth = resolvePullParser.getDepth();
                        while (XmlUtils.nextElementWithin(resolvePullParser, depth)) {
                            if (resolvePullParser.getName().equals("secret-key")) {
                                resolvePullParser.require(2, null, "secret-key");
                                byte[] hexStringToByteArray =
                                        HexDump.hexStringToByteArray(resolvePullParser.nextText());
                                resolvePullParser.require(3, null, "secret-key");
                                if (hexStringToByteArray == null
                                        || hexStringToByteArray.length != 48) {
                                    Log.e("WatchlistSettings", "Unable to parse secret key");
                                    hexStringToByteArray = null;
                                }
                                this.mPrivacySecretKey = hexStringToByteArray;
                            }
                        }
                        Slog.i("WatchlistSettings", "Reload watchlist settings done");
                        if (openRead != null) {
                            openRead.close();
                        }
                    } catch (Throwable th) {
                        if (openRead != null) {
                            try {
                                openRead.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        }
                        throw th;
                    }
                } catch (RuntimeException e) {
                    Slog.e("WatchlistSettings", "Failed parsing xml", e);
                }
            } catch (IOException
                    | IllegalStateException
                    | IndexOutOfBoundsException
                    | NullPointerException
                    | NumberFormatException
                    | XmlPullParserException e2) {
                Slog.e("WatchlistSettings", "Failed parsing xml", e2);
            }
        }
        if (this.mPrivacySecretKey == null) {
            byte[] bArr = new byte[48];
            new SecureRandom().nextBytes(bArr);
            this.mPrivacySecretKey = bArr;
            try {
                FileOutputStream startWrite = this.mXmlFile.startWrite();
                try {
                    TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(startWrite);
                    resolveSerializer.startDocument((String) null, Boolean.TRUE);
                    resolveSerializer.startTag((String) null, "network-watchlist-settings");
                    resolveSerializer.startTag((String) null, "secret-key");
                    resolveSerializer.text(HexDump.toHexString(this.mPrivacySecretKey));
                    resolveSerializer.endTag((String) null, "secret-key");
                    resolveSerializer.endTag((String) null, "network-watchlist-settings");
                    resolveSerializer.endDocument();
                    this.mXmlFile.finishWrite(startWrite);
                } catch (IOException e3) {
                    Log.w(
                            "WatchlistSettings",
                            "Failed to write display settings, restoring backup.",
                            e3);
                    this.mXmlFile.failWrite(startWrite);
                }
            } catch (IOException e4) {
                Log.w("WatchlistSettings", "Failed to write display settings: " + e4);
            }
        }
    }
}
