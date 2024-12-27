package com.samsung.android.fontutil;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;
import android.sec.enterprise.content.SecContentProviderURI;
import android.util.Log;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

/* loaded from: classes6.dex */
public class TypefaceFinder {
    public static final String DEFAULT_FONT_VALUE = "default";
    private static final String FONT_ASSET_DIR = "xml";
    private static final String FONT_DIRECTORY = "fonts/";
    private static final String FONT_EXTENSION = ".ttf";
    private static final String FONT_FOUNDATION_PRELOAD = "com.monotype.android.font.foundation";
    private static final String FONT_ROBOTO_PRELOAD = "com.monotype.android.font.roboto";
    private static final String FONT_SAMSUNGONE_DOWNLOAD = "com.monotype.android.font.samsungoneuiregular";
    public static final String FONT_SAMSUNGONE_PRELOAD = "com.monotype.android.font.samsungone";
    private static final String TAG = "TypefaceFinder";
    private final List<SemTypeface> mTypefaces = new ArrayList();

    private void findTypefacesWithCR(Context context, String fontPackageName) {
        String[] xmlFiles = null;
        try {
            Uri uriFonts = Uri.parse(SecContentProviderURI.CONTENT + fontPackageName + "/fonts");
            String xmlFilesString = context.getContentResolver().getType(uriFonts);
            if (xmlFilesString != null && !xmlFilesString.isEmpty()) {
                xmlFiles = xmlFilesString.split("\n");
            }
            if (xmlFiles == null) {
                return;
            }
            for (String xmlFile : xmlFiles) {
                Uri uriXML = Uri.parse(SecContentProviderURI.CONTENT + fontPackageName + "/xml/" + xmlFile);
                try {
                    InputStream in = context.getContentResolver().openInputStream(uriXML);
                    try {
                        parseTypefaceXml(xmlFile, in, fontPackageName);
                        if (in != null) {
                            in.close();
                        }
                    } catch (Throwable th) {
                        if (in != null) {
                            try {
                                in.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        }
                        throw th;
                    }
                } catch (Exception e) {
                }
            }
        } catch (Exception e2) {
        }
    }

    public void findTypefaces(Context context, AssetManager assetManager, String fontPackageName) {
        try {
            String[] xmlFiles = assetManager.list("xml");
            if (xmlFiles == null || xmlFiles.length == 0) {
                findTypefacesWithCR(context, fontPackageName);
                return;
            }
            for (String xmlFile : xmlFiles) {
                try {
                    InputStream in = assetManager.open("xml/" + xmlFile);
                    try {
                        parseTypefaceXml(xmlFile, in, fontPackageName);
                        if (in != null) {
                            in.close();
                        }
                    } catch (Throwable th) {
                        if (in != null) {
                            try {
                                in.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        }
                        throw th;
                    }
                } catch (Exception e) {
                    Log.v(TAG, "Not possible to open, continue to next file, " + e.getMessage());
                }
            }
        } catch (Exception e2) {
        }
    }

    private void parseTypefaceXml(String xmlFilename, InputStream inStream, String fontPackageName) {
        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();
            TypefaceParser fontParser = new TypefaceParser();
            xr.setContentHandler(fontParser);
            xr.parse(new InputSource(inStream));
            SemTypeface newTypeface = fontParser.getParsedData();
            if (fontPackageName.equals(FONT_SAMSUNGONE_PRELOAD)) {
                newTypeface.setTypefaceFilename("SamsungOneUI-Regular.xml");
            } else {
                newTypeface.setTypefaceFilename(xmlFilename);
            }
            newTypeface.setFontPackageName(fontPackageName);
            this.mTypefaces.add(newTypeface);
        } catch (Exception e) {
            Log.v(TAG, "File parsing is not possible, omit this typeface, " + e.getMessage());
        }
    }

    /* JADX WARN: Incorrect condition in loop: B:3:0x0044 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void getSansEntries(android.content.Context r24, android.content.pm.PackageManager r25, java.util.ArrayList r26, java.util.ArrayList r27, java.util.ArrayList r28) {
        /*
            Method dump skipped, instructions count: 392
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.fontutil.TypefaceFinder.getSansEntries(android.content.Context, android.content.pm.PackageManager, java.util.ArrayList, java.util.ArrayList, java.util.ArrayList):void");
    }

    public SemTypeface findMatchingTypeface(String typefaceFilename) {
        for (SemTypeface typeface : this.mTypefaces) {
            if (typeface.getTypefaceFilename().equals(typefaceFilename)) {
                return typeface;
            }
        }
        return null;
    }

    public static class TypefaceSortByName implements Comparator<SemTypeface>, Serializable {
        @Override // java.util.Comparator
        public int compare(SemTypeface o1, SemTypeface o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }
}
