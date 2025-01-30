package com.android.server.enterprise.general.font;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.util.Log;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

/* loaded from: classes2.dex */
public class TypefaceFinder {
  public final List mTypefaces = new ArrayList();

  public boolean findTypefaces(AssetManager assetManager, String str) {
    try {
      String[] list = assetManager.list("xml");
      for (int i = 0; i < list.length; i++) {
        try {
          parseTypefaceXml(list[i], assetManager.open("xml/" + list[i]), str);
        } catch (Exception unused) {
        }
      }
      return true;
    } catch (Exception unused2) {
      return false;
    }
  }

  public void parseTypefaceXml(String str, InputStream inputStream, String str2) {
    try {
      XMLReader xMLReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
      TypefaceParser typefaceParser = new TypefaceParser();
      xMLReader.setContentHandler(typefaceParser);
      xMLReader.parse(new InputSource(inputStream));
      Typeface parsedData = typefaceParser.getParsedData();
      if (str2.equals("com.monotype.android.font.samsungone")) {
        parsedData.setTypefaceFilename("SamsungOneUI-Regular.xml");
      } else {
        parsedData.setTypefaceFilename(str);
      }
      parsedData.setFontPackageName(str2);
      this.mTypefaces.add(parsedData);
    } catch (Exception unused) {
    }
  }

  public class TypefaceSortByName implements Comparator, Serializable {
    @Override // java.util.Comparator
    public int compare(Typeface typeface, Typeface typeface2) {
      return typeface.getName().compareTo(typeface2.getName());
    }
  }

  public final String deleteWhiteSpace(String str) {
    StringBuffer stringBuffer = new StringBuffer();
    for (int i = 0; i < str.length(); i++) {
      if (str.charAt(i) != ' ') {
        stringBuffer.append(str.charAt(i));
      }
    }
    return stringBuffer.toString();
  }

  public void getSansEntries(
      PackageManager packageManager, Vector vector, Vector vector2, Vector vector3) {
    boolean z;
    vector.add("default");
    vector2.add("default");
    vector3.add("");
    Collections.sort(this.mTypefaces, new TypefaceSortByName());
    for (int i = 0; i < this.mTypefaces.size(); i++) {
      String sansName = ((Typeface) this.mTypefaces.get(i)).getSansName();
      if (sansName != null) {
        String typefaceFilename = ((Typeface) this.mTypefaces.get(i)).getTypefaceFilename();
        int lastIndexOf = typefaceFilename.lastIndexOf(47);
        int lastIndexOf2 = typefaceFilename.lastIndexOf(46);
        if (lastIndexOf2 < 0) {
          lastIndexOf2 = typefaceFilename.length();
        }
        String replaceAll =
            typefaceFilename.substring(lastIndexOf + 1, lastIndexOf2).replaceAll(" ", "");
        try {
          ApplicationInfo applicationInfo =
              packageManager.getApplicationInfo(
                  ((Typeface) this.mTypefaces.get(i)).getFontPackageName(), 128);
          applicationInfo.publicSourceDir = applicationInfo.sourceDir;
          android.graphics.Typeface.createFromAsset(
              packageManager.getResourcesForApplication(applicationInfo).getAssets(),
              "fonts/" + replaceAll + ".ttf");
          if (((Typeface) this.mTypefaces.get(i)).getFontPackageName() != null) {
            if (!((Typeface) this.mTypefaces.get(i))
                .getFontPackageName()
                .contains("com.monotype.android.font.droidserifitalic")) {
              if (((Typeface) this.mTypefaces.get(i))
                  .getFontPackageName()
                  .equals("com.monotype.android.font.samsungoneuiregular")) {
                Iterator it = this.mTypefaces.iterator();
                while (true) {
                  if (it.hasNext()) {
                    if (((Typeface) it.next())
                        .getFontPackageName()
                        .equalsIgnoreCase("com.monotype.android.font.samsungone")) {
                      z = true;
                      break;
                    }
                  } else {
                    z = false;
                    break;
                  }
                }
                if (z) {}
              }
            }
          }
          if (((Typeface) this.mTypefaces.get(i))
              .getFontPackageName()
              .equals("com.monotype.android.font.foundation")) {
            vector.add(1, deleteWhiteSpace(sansName));
            vector2.add(1, ((Typeface) this.mTypefaces.get(i)).getTypefaceFilename());
            vector3.add(1, ((Typeface) this.mTypefaces.get(i)).getFontPackageName());
          } else if (((Typeface) this.mTypefaces.get(i))
              .getFontPackageName()
              .equals("com.monotype.android.font.samsungone")) {
            vector.add(1, deleteWhiteSpace(sansName));
            vector2.add(1, ((Typeface) this.mTypefaces.get(i)).getTypefaceFilename());
            vector3.add(1, ((Typeface) this.mTypefaces.get(i)).getFontPackageName());
          } else {
            vector.add(sansName);
            vector2.add(((Typeface) this.mTypefaces.get(i)).getTypefaceFilename());
            vector3.add(((Typeface) this.mTypefaces.get(i)).getFontPackageName());
          }
        } catch (Exception e) {
          Log.d(
              "TypefaceFinder",
              "getSansEntries - Typeface.createFromAsset caused an exception for - fonts/"
                  + replaceAll
                  + ".ttf");
          e.printStackTrace();
        }
      }
    }
  }

  public Typeface findMatchingTypefaceByName(String str) {
    Log.i("TypefaceFinder", "findMatchingTypefaceByName:" + str);
    for (int i = 0; i < this.mTypefaces.size(); i++) {
      Typeface typeface = (Typeface) this.mTypefaces.get(i);
      Log.i("TypefaceFinder", "findMatchingTypeface:" + typeface.getName());
      if (typeface.getName().equalsIgnoreCase(str)) {
        return typeface;
      }
    }
    return null;
  }
}
