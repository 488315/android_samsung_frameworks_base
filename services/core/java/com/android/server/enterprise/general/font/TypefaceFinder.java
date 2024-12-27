package com.android.server.enterprise.general.font;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.util.Log;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.xml.parsers.SAXParserFactory;

public final class TypefaceFinder {
    public final List mTypefaces = new ArrayList();

    public final class TypefaceSortByName implements Comparator {
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return ((Typeface) obj).mName.compareTo(((Typeface) obj2).mName);
        }
    }

    public static String deleteWhiteSpace(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != ' ') {
                stringBuffer.append(str.charAt(i));
            }
        }
        return stringBuffer.toString();
    }

    public final void findTypefaces(AssetManager assetManager, String str) {
        try {
            String[] list = assetManager.list("xml");
            for (int i = 0; i < list.length; i++) {
                try {
                    parseTypefaceXml(list[i], assetManager.open("xml/" + list[i]), str);
                } catch (Exception unused) {
                }
            }
        } catch (Exception unused2) {
        }
    }

    public final void getSansEntries(
            PackageManager packageManager, Vector vector, Vector vector2, Vector vector3) {
        vector.add("default");
        vector2.add("default");
        vector3.add("");
        Collections.sort(this.mTypefaces, new TypefaceSortByName());
        for (int i = 0; i < ((ArrayList) this.mTypefaces).size(); i++) {
            Typeface typeface = (Typeface) ((ArrayList) this.mTypefaces).get(i);
            String str = ((ArrayList) typeface.mSansFonts).isEmpty() ? null : typeface.mName;
            if (str != null) {
                String str2 = ((Typeface) ((ArrayList) this.mTypefaces).get(i)).mTypefaceFilename;
                int lastIndexOf = str2.lastIndexOf(47);
                int lastIndexOf2 = str2.lastIndexOf(46);
                if (lastIndexOf2 < 0) {
                    lastIndexOf2 = str2.length();
                }
                String replaceAll =
                        str2.substring(lastIndexOf + 1, lastIndexOf2).replaceAll(" ", "");
                try {
                    ApplicationInfo applicationInfo =
                            packageManager.getApplicationInfo(
                                    ((Typeface) ((ArrayList) this.mTypefaces).get(i))
                                            .mFontPackageName,
                                    128);
                    applicationInfo.publicSourceDir = applicationInfo.sourceDir;
                    android.graphics.Typeface.createFromAsset(
                            packageManager.getResourcesForApplication(applicationInfo).getAssets(),
                            "fonts/" + replaceAll + ".ttf");
                    if (((Typeface) ((ArrayList) this.mTypefaces).get(i)).mFontPackageName
                            != null) {
                        if (!((Typeface) ((ArrayList) this.mTypefaces).get(i))
                                .mFontPackageName.contains(
                                        "com.monotype.android.font.droidserifitalic")) {
                            if (((Typeface) ((ArrayList) this.mTypefaces).get(i))
                                    .mFontPackageName.equals(
                                            "com.monotype.android.font.samsungoneuiregular")) {
                                Iterator it = ((ArrayList) this.mTypefaces).iterator();
                                while (it.hasNext()) {
                                    if (((Typeface) it.next())
                                            .mFontPackageName.equalsIgnoreCase(
                                                    "com.monotype.android.font.samsungone")) {
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    if (((Typeface) ((ArrayList) this.mTypefaces).get(i))
                            .mFontPackageName.equals("com.monotype.android.font.foundation")) {
                        vector.add(1, deleteWhiteSpace(str));
                        vector2.add(
                                1,
                                ((Typeface) ((ArrayList) this.mTypefaces).get(i))
                                        .mTypefaceFilename);
                        vector3.add(
                                1,
                                ((Typeface) ((ArrayList) this.mTypefaces).get(i)).mFontPackageName);
                    } else if (((Typeface) ((ArrayList) this.mTypefaces).get(i))
                            .mFontPackageName.equals("com.monotype.android.font.samsungone")) {
                        vector.add(1, deleteWhiteSpace(str));
                        vector2.add(
                                1,
                                ((Typeface) ((ArrayList) this.mTypefaces).get(i))
                                        .mTypefaceFilename);
                        vector3.add(
                                1,
                                ((Typeface) ((ArrayList) this.mTypefaces).get(i)).mFontPackageName);
                    } else {
                        vector.add(str);
                        vector2.add(
                                ((Typeface) ((ArrayList) this.mTypefaces).get(i))
                                        .mTypefaceFilename);
                        vector3.add(
                                ((Typeface) ((ArrayList) this.mTypefaces).get(i)).mFontPackageName);
                    }
                } catch (Exception e) {
                    Log.d(
                            "TypefaceFinder",
                            "getSansEntries - Typeface.createFromAsset caused an exception for -"
                                + " fonts/"
                                    + replaceAll
                                    + ".ttf");
                    e.printStackTrace();
                }
            }
        }
    }

    public final void parseTypefaceXml(String str, InputStream inputStream, String str2) {
        try {
            XMLReader xMLReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
            TypefaceParser typefaceParser = new TypefaceParser();
            typefaceParser.in_sans = false;
            typefaceParser.in_serif = false;
            typefaceParser.in_monospace = false;
            typefaceParser.in_filename = false;
            typefaceParser.in_droidname = false;
            typefaceParser.mFont = null;
            typefaceParser.mFontFile = null;
            xMLReader.setContentHandler(typefaceParser);
            xMLReader.parse(new InputSource(inputStream));
            Typeface typeface = typefaceParser.mFont;
            if (str2.equals("com.monotype.android.font.samsungone")) {
                typeface.mTypefaceFilename = "SamsungOneUI-Regular.xml";
            } else {
                typeface.mTypefaceFilename = str;
            }
            typeface.mFontPackageName = str2;
            ((ArrayList) this.mTypefaces).add(typeface);
        } catch (Exception unused) {
        }
    }
}
