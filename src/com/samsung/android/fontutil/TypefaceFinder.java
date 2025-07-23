package com.samsung.android.fontutil;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.Uri;
import android.sec.enterprise.content.SecContentProviderURI;
import android.util.Log;
import com.android.internal.R;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
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

    private void findTypefacesWithCR(Context context, String str) {
        try {
            String type = context.getContentResolver().getType(Uri.parse(SecContentProviderURI.CONTENT + str + "/fonts"));
            String[] split = (type == null || type.isEmpty()) ? null : type.split(ShaderAssembler.NEWLINE);
            if (split == null) {
                return;
            }
            for (String str2 : split) {
                try {
                    InputStream openInputStream = context.getContentResolver().openInputStream(Uri.parse(SecContentProviderURI.CONTENT + str + "/xml/" + str2));
                    try {
                        parseTypefaceXml(str2, openInputStream, str);
                        if (openInputStream != null) {
                            openInputStream.close();
                        }
                    } catch (Throwable th) {
                        if (openInputStream != null) {
                            try {
                                openInputStream.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                        }
                        throw th;
                    }
                } catch (Exception unused) {
                    continue;
                }
            }
        } catch (Exception unused2) {
        }
    }

    public void findTypefaces(Context context, AssetManager assetManager, String str) {
        try {
            String[] list = assetManager.list("xml");
            if (list == null || list.length == 0) {
                findTypefacesWithCR(context, str);
                return;
            }
            for (String str2 : list) {
                try {
                    InputStream open = assetManager.open("xml/" + str2);
                    try {
                        parseTypefaceXml(str2, open, str);
                        if (open != null) {
                            open.close();
                        }
                    } catch (Throwable th) {
                        if (open != null) {
                            try {
                                open.close();
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
        } catch (Exception unused) {
        }
    }

    private void parseTypefaceXml(String str, InputStream inputStream, String str2) {
        try {
            XMLReader xMLReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
            TypefaceParser typefaceParser = new TypefaceParser();
            xMLReader.setContentHandler(typefaceParser);
            xMLReader.parse(new InputSource(inputStream));
            SemTypeface parsedData = typefaceParser.getParsedData();
            if (str2.equals(FONT_SAMSUNGONE_PRELOAD)) {
                parsedData.setTypefaceFilename("SamsungOneUI-Regular.xml");
            } else {
                parsedData.setTypefaceFilename(str);
            }
            parsedData.setFontPackageName(str2);
            this.mTypefaces.add(parsedData);
        } catch (Exception e) {
            Log.v(TAG, "File parsing is not possible, omit this typeface, " + e.getMessage());
        }
    }

    public void getSansEntries(Context context, PackageManager packageManager, ArrayList arrayList, ArrayList arrayList2, ArrayList arrayList3) {
        Iterator<SemTypeface> it;
        boolean z;
        boolean z2;
        arrayList.add((String) context.getResources().getText(R.string.sec_monotype_default));
        arrayList2.add("default");
        arrayList3.add("");
        Collections.sort(this.mTypefaces, new TypefaceSortByName());
        Iterator<SemTypeface> it2 = this.mTypefaces.iterator();
        boolean z3 = false;
        boolean z4 = false;
        boolean z5 = false;
        String str = null;
        String str2 = null;
        String str3 = null;
        boolean z6 = false;
        while (it2.hasNext()) {
            SemTypeface next = it2.next();
            String sansName = next.getSansName();
            if (sansName != null) {
                String typefaceFilename = next.getTypefaceFilename();
                int lastIndexOf = typefaceFilename.lastIndexOf(47);
                int lastIndexOf2 = typefaceFilename.lastIndexOf(46);
                if (lastIndexOf2 < 0) {
                    lastIndexOf2 = typefaceFilename.length();
                }
                it = it2;
                String replaceAll = typefaceFilename.substring(lastIndexOf + 1, lastIndexOf2).replaceAll(" ", "");
                String fontPackageName = next.getFontPackageName();
                z = z6;
                try {
                    ApplicationInfo applicationInfo = packageManager.getApplicationInfo(fontPackageName, 128);
                    applicationInfo.publicSourceDir = applicationInfo.sourceDir;
                } catch (Exception e) {
                    e = e;
                }
                if (fontPackageName != null) {
                    if (fontPackageName.equals(FONT_SAMSUNGONE_DOWNLOAD)) {
                        try {
                            str = sansName.replaceAll(" ", "");
                            str2 = next.getTypefaceFilename();
                            it2 = it;
                            str3 = fontPackageName;
                            z6 = true;
                        } catch (Exception e2) {
                            e = e2;
                            z6 = true;
                            Log.d(TAG, "getSansEntries - Typeface.createFromAsset caused an exception for - fonts/" + replaceAll + FONT_EXTENSION);
                            e.printStackTrace();
                            it2 = it;
                        }
                    } else {
                        if (fontPackageName.equals(FONT_FOUNDATION_PRELOAD)) {
                            if (!z4) {
                                try {
                                    arrayList.add(1, sansName.replaceAll(" ", ""));
                                    arrayList2.add(1, next.getTypefaceFilename());
                                    arrayList3.add(1, fontPackageName);
                                    z4 = true;
                                } catch (Exception e3) {
                                    e = e3;
                                    z6 = z;
                                    z4 = true;
                                    Log.d(TAG, "getSansEntries - Typeface.createFromAsset caused an exception for - fonts/" + replaceAll + FONT_EXTENSION);
                                    e.printStackTrace();
                                    it2 = it;
                                }
                            }
                        } else if (fontPackageName.equals(FONT_SAMSUNGONE_PRELOAD)) {
                            if (!z3) {
                                try {
                                    z2 = true;
                                    try {
                                        arrayList.add(1, sansName.replaceAll(" ", ""));
                                        arrayList2.add(1, next.getTypefaceFilename());
                                        arrayList3.add(1, fontPackageName);
                                        z3 = true;
                                    } catch (Exception e4) {
                                        e = e4;
                                        z3 = z2;
                                        z6 = z;
                                        Log.d(TAG, "getSansEntries - Typeface.createFromAsset caused an exception for - fonts/" + replaceAll + FONT_EXTENSION);
                                        e.printStackTrace();
                                        it2 = it;
                                    }
                                } catch (Exception e5) {
                                    e = e5;
                                    z2 = true;
                                }
                            }
                        } else if (fontPackageName.equals(FONT_ROBOTO_PRELOAD)) {
                            if (!z5) {
                                try {
                                    arrayList.add(2, sansName.replaceAll(" ", ""));
                                    arrayList2.add(2, next.getTypefaceFilename());
                                    arrayList3.add(2, fontPackageName);
                                    z5 = true;
                                } catch (Exception e6) {
                                    e = e6;
                                    z5 = true;
                                    z6 = z;
                                    Log.d(TAG, "getSansEntries - Typeface.createFromAsset caused an exception for - fonts/" + replaceAll + FONT_EXTENSION);
                                    e.printStackTrace();
                                    it2 = it;
                                }
                            }
                        }
                        it2 = it;
                        z6 = z;
                    }
                }
                arrayList.add(sansName.replaceAll(" ", ""));
                arrayList2.add(next.getTypefaceFilename());
                arrayList3.add(fontPackageName);
            } else {
                it = it2;
                z = z6;
            }
            z6 = z;
            it2 = it;
        }
        if (!z6 || z3) {
            return;
        }
        arrayList.add(str);
        arrayList2.add(str2);
        arrayList3.add(str3);
    }

    public SemTypeface findMatchingTypeface(String str) {
        for (SemTypeface semTypeface : this.mTypefaces) {
            if (semTypeface.getTypefaceFilename().equals(str)) {
                return semTypeface;
            }
        }
        return null;
    }

    public static class TypefaceSortByName implements Comparator<SemTypeface>, Serializable {
        @Override // java.util.Comparator
        public int compare(SemTypeface semTypeface, SemTypeface semTypeface2) {
            return semTypeface.getName().compareTo(semTypeface2.getName());
        }
    }
}
