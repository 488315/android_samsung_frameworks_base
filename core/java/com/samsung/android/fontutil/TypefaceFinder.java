package com.samsung.android.fontutil;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.Uri;
import android.sec.enterprise.content.SecContentProviderURI;
import android.util.Log;
import com.android.internal.C4337R;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

/* loaded from: classes5.dex */
public class TypefaceFinder {
  public static final String DEFAULT_FONT_VALUE = "default";
  private static final String FONT_ASSET_DIR = "xml";
  private static final String FONT_DIRECTORY = "fonts/";
  private static final String FONT_EXTENSION = ".ttf";
  private static final String FONT_FOUNDATION_PRELOAD = "com.monotype.android.font.foundation";
  private static final String FONT_ROBOTO_PRELOAD = "com.monotype.android.font.roboto";
  private static final String FONT_SAMSUNGONE_DOWNLOAD =
      "com.monotype.android.font.samsungoneuiregular";
  public static final String FONT_SAMSUNGONE_PRELOAD = "com.monotype.android.font.samsungone";
  private static final String TAG = "TypefaceFinder";
  private final List<SemTypeface> mTypefaces = new ArrayList();

  public SemTypeface findMatchingTypefaceByName(String fontName) {
    for (SemTypeface typeface : this.mTypefaces) {
      if (typeface.getName().equalsIgnoreCase(fontName)) {
        return typeface;
      }
    }
    return null;
  }

  private void findTypefacesWithCR(Context context, String fontPackageName) {
    String[] xmlFiles = null;
    try {
      Uri uriFonts = Uri.parse(SecContentProviderURI.CONTENT + fontPackageName + "/fonts");
      String xmlFilesString = context.getContentResolver().getType(uriFonts);
      if (xmlFilesString != null && !xmlFilesString.isEmpty()) {
        xmlFiles = xmlFilesString.split("\n");
      }
      if (xmlFiles == null || xmlFiles.length == 0) {
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
          Log.m100v(TAG, "Not possible to open, continue to next file, " + e.getMessage());
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
      Log.m100v(TAG, "File parsing is not possible, omit this typeface, " + e.getMessage());
    }
  }

  /* JADX WARN: Incorrect condition in loop: B:3:0x0044 */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public void getSansEntries(
      Context context,
      PackageManager packageManager,
      ArrayList entries,
      ArrayList entryValues,
      ArrayList fontPackageName) {
    String samsungOneTypefaceFilename;
    Iterator<SemTypeface> it;
    String samsungOnePackageName;
    int end_pos;
    entries.add((String) context.getResources().getText(C4337R.string.sec_monotype_default));
    entryValues.add("default");
    fontPackageName.add("");
    Collections.sort(this.mTypefaces, new TypefaceSortByName());
    Iterator<SemTypeface> it2 = this.mTypefaces.iterator();
    String packageName = null;
    String samsungOnePackageName2 = null;
    String samsungOneSansName = null;
    boolean isExistRobotoFont = false;
    boolean isExistSamsungOneDownloadFont = false;
    boolean isExistFoundationFont = false;
    boolean isExistSamsungOneSystemFont = false;
    while (isExistSamsungOneSystemFont) {
      SemTypeface typeface = it2.next();
      String s = typeface.getSansName();
      if (s == null) {
        samsungOneTypefaceFilename = samsungOnePackageName2;
        it = it2;
        samsungOnePackageName = packageName;
      } else {
        String fontName = typeface.getTypefaceFilename();
        int start_pos = fontName.lastIndexOf(47);
        int end_pos2 = fontName.lastIndexOf(46);
        if (end_pos2 >= 0) {
          it = it2;
          end_pos = end_pos2;
        } else {
          it = it2;
          end_pos = fontName.length();
        }
        String loadTypeface = fontName.substring(start_pos + 1, end_pos);
        String loadTypeface2 = loadTypeface.replaceAll(" ", "");
        samsungOnePackageName = packageName;
        packageName = typeface.getFontPackageName();
        samsungOneTypefaceFilename = samsungOnePackageName2;
        try {
          ApplicationInfo appInfo = packageManager.getApplicationInfo(packageName, 128);
          appInfo.publicSourceDir = appInfo.sourceDir;
        } catch (Exception e) {
          Log.m94d(
              TAG,
              "getSansEntries - Typeface.createFromAsset caused an exception for - fonts/"
                  + loadTypeface2
                  + FONT_EXTENSION);
          e.printStackTrace();
        }
        if (packageName != null) {
          if (packageName.equals(FONT_SAMSUNGONE_DOWNLOAD)) {
            isExistSamsungOneDownloadFont = true;
            samsungOneSansName = s.replaceAll(" ", "");
            samsungOnePackageName2 = typeface.getTypefaceFilename();
            it2 = it;
          } else if (packageName.equals(FONT_FOUNDATION_PRELOAD)) {
            if (!isExistFoundationFont) {
              isExistFoundationFont = true;
              entries.add(1, s.replaceAll(" ", ""));
              entryValues.add(1, typeface.getTypefaceFilename());
              fontPackageName.add(1, packageName);
            }
            it2 = it;
            packageName = samsungOnePackageName;
            samsungOnePackageName2 = samsungOneTypefaceFilename;
          } else if (packageName.equals(FONT_SAMSUNGONE_PRELOAD)) {
            if (!isExistSamsungOneSystemFont) {
              isExistSamsungOneSystemFont = true;
              entries.add(1, s.replaceAll(" ", ""));
              entryValues.add(1, typeface.getTypefaceFilename());
              fontPackageName.add(1, packageName);
            }
            it2 = it;
            packageName = samsungOnePackageName;
            samsungOnePackageName2 = samsungOneTypefaceFilename;
          } else if (packageName.equals(FONT_ROBOTO_PRELOAD)) {
            if (!isExistRobotoFont) {
              isExistRobotoFont = true;
              entries.add(2, s.replaceAll(" ", ""));
              entryValues.add(2, typeface.getTypefaceFilename());
              fontPackageName.add(2, packageName);
            }
            it2 = it;
            packageName = samsungOnePackageName;
            samsungOnePackageName2 = samsungOneTypefaceFilename;
          }
        }
        entries.add(s.replaceAll(" ", ""));
        entryValues.add(typeface.getTypefaceFilename());
        fontPackageName.add(packageName);
      }
      it2 = it;
      packageName = samsungOnePackageName;
      samsungOnePackageName2 = samsungOneTypefaceFilename;
    }
    String samsungOneTypefaceFilename2 = samsungOnePackageName2;
    String samsungOnePackageName3 = packageName;
    if (isExistSamsungOneDownloadFont && !isExistSamsungOneSystemFont) {
      entries.add(samsungOneSansName);
      entryValues.add(samsungOneTypefaceFilename2);
      fontPackageName.add(samsungOnePackageName3);
    }
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
