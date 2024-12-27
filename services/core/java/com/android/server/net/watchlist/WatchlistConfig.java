package com.android.server.net.watchlist;

import android.util.Log;
import android.util.Slog;
import android.util.Xml;
import com.android.internal.util.HexDump;
import com.android.internal.util.XmlUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class WatchlistConfig {
    public static final WatchlistConfig sInstance = new WatchlistConfig(new File("/data/misc/network_watchlist/network_watchlist.xml"));
    public volatile CrcShaDigests mDomainDigests;
    public volatile CrcShaDigests mIpDigests;
    public boolean mIsSecureConfig = true;
    public File mXmlFile;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CrcShaDigests {
        public final HarmfulCrcs crc32s;
        public final HarmfulDigests sha256Digests;

        public CrcShaDigests(HarmfulCrcs harmfulCrcs, HarmfulDigests harmfulDigests) {
            this.crc32s = harmfulCrcs;
            this.sha256Digests = harmfulDigests;
        }
    }

    public WatchlistConfig(File file) {
        this.mXmlFile = file;
        reloadConfig();
    }

    public static void parseHashes(XmlPullParser xmlPullParser, String str, List list) {
        xmlPullParser.require(2, null, str);
        while (xmlPullParser.nextTag() == 2) {
            xmlPullParser.require(2, null, "hash");
            byte[] hexStringToByteArray = HexDump.hexStringToByteArray(xmlPullParser.nextText());
            xmlPullParser.require(3, null, "hash");
            ((ArrayList) list).add(hexStringToByteArray);
        }
        xmlPullParser.require(3, null, str);
    }

    public final byte[] getWatchlistConfigHash() {
        if (!this.mXmlFile.exists()) {
            return null;
        }
        try {
            return DigestUtils.getSha256Hash(this.mXmlFile);
        } catch (IOException | NoSuchAlgorithmException e) {
            Log.e("WatchlistConfig", "Unable to get watchlist config hash", e);
            return null;
        }
    }

    public final void reloadConfig() {
        char c;
        if (this.mXmlFile.exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(this.mXmlFile);
                try {
                    ArrayList arrayList = new ArrayList();
                    ArrayList arrayList2 = new ArrayList();
                    ArrayList arrayList3 = new ArrayList();
                    ArrayList arrayList4 = new ArrayList();
                    XmlPullParser newPullParser = Xml.newPullParser();
                    newPullParser.setInput(fileInputStream, StandardCharsets.UTF_8.name());
                    newPullParser.nextTag();
                    newPullParser.require(2, null, "watchlist-config");
                    while (newPullParser.nextTag() == 2) {
                        String name = newPullParser.getName();
                        switch (name.hashCode()) {
                            case -1862636386:
                                if (name.equals("crc32-domain")) {
                                    c = 0;
                                    break;
                                }
                                c = 65535;
                                break;
                            case -14835926:
                                if (name.equals("sha256-domain")) {
                                    c = 2;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 835385997:
                                if (name.equals("sha256-ip")) {
                                    c = 3;
                                    break;
                                }
                                c = 65535;
                                break;
                            case 1718657537:
                                if (name.equals("crc32-ip")) {
                                    c = 1;
                                    break;
                                }
                                c = 65535;
                                break;
                            default:
                                c = 65535;
                                break;
                        }
                        if (c == 0) {
                            parseHashes(newPullParser, name, arrayList);
                        } else if (c == 1) {
                            parseHashes(newPullParser, name, arrayList3);
                        } else if (c == 2) {
                            parseHashes(newPullParser, name, arrayList2);
                        } else if (c != 3) {
                            Log.w("WatchlistConfig", "Unknown element: " + newPullParser.getName());
                            XmlUtils.skipCurrentTag(newPullParser);
                        } else {
                            parseHashes(newPullParser, name, arrayList4);
                        }
                    }
                    newPullParser.require(3, null, "watchlist-config");
                    this.mDomainDigests = new CrcShaDigests(new HarmfulCrcs(arrayList), new HarmfulDigests(arrayList2));
                    this.mIpDigests = new CrcShaDigests(new HarmfulCrcs(arrayList3), new HarmfulDigests(arrayList4));
                    Log.i("WatchlistConfig", "Reload watchlist done");
                    fileInputStream.close();
                } catch (Throwable th) {
                    try {
                        fileInputStream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } catch (IOException | IllegalStateException | IndexOutOfBoundsException | NullPointerException | NumberFormatException | XmlPullParserException e) {
                Slog.e("WatchlistConfig", "Failed parsing xml", e);
            }
        }
    }
}
