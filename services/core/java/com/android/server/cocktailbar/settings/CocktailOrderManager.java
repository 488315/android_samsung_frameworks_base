package com.android.server.cocktailbar.settings;

import android.content.ComponentName;
import android.content.Context;
import android.os.Environment;
import android.util.AtomicFile;
import android.util.Slog;
import android.util.SparseArray;
import android.util.Xml;
import com.android.internal.util.FastXmlSerializer;
import com.android.server.cocktailbar.utils.CocktailBarConfig;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.samsung.android.cocktailbar.Cocktail;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import libcore.io.IoUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class CocktailOrderManager {
  public static final String TAG = "CocktailOrderManager";
  public ArrayList mCocktailOrderedList;
  public Context mContext;
  public final String TAG_COCKTAIL_ORDER = "cocktail_order";
  public final String TAG_PROVIDER = "provider";
  public final String TAG_ORDER = "order";
  public final String TAG_USER_ID = "user_id";
  public final String TAG_COCKTAIL_ID = "cocktail_id";
  public final String TAG_PACKAGE = "package";
  public final String TAG_CLASS_NAME = "class_name";
  public Object mLock = new Object();

  public CocktailOrderManager(Context context) {
    this.mContext = context;
  }

  public static File getSettingsFile(int i) {
    return new File(Environment.getUserSystemDirectory(i), "cocktail_order.xml");
  }

  public final AtomicFile savedStateFile() {
    File userSystemDirectory = Environment.getUserSystemDirectory(0);
    File settingsFile = getSettingsFile(0);
    if (!settingsFile.exists()) {
      if (!userSystemDirectory.exists() && !userSystemDirectory.mkdirs()) {
        Slog.w(TAG, "savedStateFile Failed mkdirs");
      }
      if (!new File("/data/system/cocktail_order.xml").renameTo(settingsFile)) {
        Slog.w(TAG, "Failed rename to setting file.");
      }
    }
    return new AtomicFile(settingsFile);
  }

  public final void saveStateLocked() {
    AtomicFile savedStateFile = savedStateFile();
    try {
      FileOutputStream startWrite = savedStateFile.startWrite();
      if (writeStateToFileLocked(startWrite)) {
        savedStateFile.finishWrite(startWrite);
      } else {
        savedStateFile.failWrite(startWrite);
        Slog.w(TAG, "Failed to save state, restoring backup.");
      }
    } catch (IOException e) {
      Slog.w(TAG, "Failed open state file for write: " + e);
    }
  }

  public final void loadStateLocked() {
    FileInputStream fileInputStream = null;
    try {
      try {
        fileInputStream = savedStateFile().openRead();
        readStateFromFileLocked(fileInputStream);
      } catch (FileNotFoundException e) {
        Slog.w(TAG, "Failed to read state: " + e);
      }
    } finally {
      IoUtils.closeQuietly(fileInputStream);
    }
  }

  public final boolean writeStateToFileLocked(FileOutputStream fileOutputStream) {
    try {
      if (this.mCocktailOrderedList == null) {
        Slog.w(TAG, "Failed to write state: no order info");
        return false;
      }
      FastXmlSerializer fastXmlSerializer = new FastXmlSerializer();
      fastXmlSerializer.setOutput(fileOutputStream, "utf-8");
      fastXmlSerializer.startDocument(null, Boolean.TRUE);
      fastXmlSerializer.startTag(null, "cocktail_order");
      int size = this.mCocktailOrderedList.size();
      for (int i = 0; i < size; i++) {
        CocktailOrderInfo cocktailOrderInfo = (CocktailOrderInfo) this.mCocktailOrderedList.get(i);
        ComponentName componentName = cocktailOrderInfo.mComponentName;
        if (componentName != null) {
          fastXmlSerializer.startTag(null, "provider");
          fastXmlSerializer.attribute(null, "order", String.valueOf(cocktailOrderInfo.mOrder));
          fastXmlSerializer.attribute(null, "user_id", String.valueOf(cocktailOrderInfo.mUserId));
          fastXmlSerializer.attribute(
              null, "cocktail_id", String.valueOf(cocktailOrderInfo.mCocktailId));
          fastXmlSerializer.attribute(null, "package", componentName.getPackageName());
          fastXmlSerializer.attribute(null, "class_name", componentName.getClassName());
          fastXmlSerializer.endTag(null, "provider");
        }
      }
      fastXmlSerializer.endTag(null, "cocktail_order");
      fastXmlSerializer.endDocument();
      return true;
    } catch (IOException e) {
      Slog.w(TAG, "Failed to write state: " + e);
      return false;
    }
  }

  /* JADX WARN: Code restructure failed: missing block: B:100:0x019c, code lost:

     r3 = r14;
  */
  /* JADX WARN: Code restructure failed: missing block: B:101:0x01c9, code lost:

     if (r3 != null) goto L323;
  */
  /* JADX WARN: Code restructure failed: missing block: B:102:0x01e4, code lost:

     throw r13;
  */
  /* JADX WARN: Code restructure failed: missing block: B:104:0x01cb, code lost:

     r3.close();
  */
  /* JADX WARN: Code restructure failed: missing block: B:106:0x01cf, code lost:

     r14 = move-exception;
  */
  /* JADX WARN: Code restructure failed: missing block: B:107:0x01d0, code lost:

     android.util.Slog.w(com.android.server.cocktailbar.settings.CocktailOrderManager.TAG, "failed to close stream " + r14);
  */
  /* JADX WARN: Code restructure failed: missing block: B:108:0x01a3, code lost:

     r13 = e;
  */
  /* JADX WARN: Code restructure failed: missing block: B:109:?, code lost:

     return;
  */
  /* JADX WARN: Code restructure failed: missing block: B:110:0x01a1, code lost:

     r13 = th;
  */
  /* JADX WARN: Code restructure failed: missing block: B:111:0x00b0, code lost:

     r14 = e;
  */
  /* JADX WARN: Code restructure failed: missing block: B:112:0x00b1, code lost:

     r4 = true;
  */
  /* JADX WARN: Code restructure failed: missing block: B:113:0x0399, code lost:

     android.util.Slog.w(com.android.server.cocktailbar.settings.CocktailOrderManager.TAG, "failed parsing " + r14);
  */
  /* JADX WARN: Code restructure failed: missing block: B:115:0x03ad, code lost:

     if (r4 == false) goto L332;
  */
  /* JADX WARN: Code restructure failed: missing block: B:117:0x03af, code lost:

     r14 = new java.io.BufferedReader(new java.io.InputStreamReader(savedStateFile().openRead(), "UTF-8"));
  */
  /* JADX WARN: Code restructure failed: missing block: B:120:0x03c1, code lost:

     r13 = new java.lang.StringBuffer();
  */
  /* JADX WARN: Code restructure failed: missing block: B:121:0x03c6, code lost:

     r0 = r14.readLine();
  */
  /* JADX WARN: Code restructure failed: missing block: B:122:0x03ca, code lost:

     if (r0 != null) goto L202;
  */
  /* JADX WARN: Code restructure failed: missing block: B:123:0x03cc, code lost:

     r13.append(r0);
  */
  /* JADX WARN: Code restructure failed: missing block: B:125:0x03d0, code lost:

     r14.close();
  */
  /* JADX WARN: Code restructure failed: missing block: B:127:0x0427, code lost:

     return;
  */
  /* JADX WARN: Code restructure failed: missing block: B:130:0x03d4, code lost:

     r13 = e;
  */
  /* JADX WARN: Code restructure failed: missing block: B:131:0x03d5, code lost:

     r14 = com.android.server.cocktailbar.settings.CocktailOrderManager.TAG;
     r0 = new java.lang.StringBuilder();
  */
  /* JADX WARN: Code restructure failed: missing block: B:133:0x03e1, code lost:

     r13 = e;
  */
  /* JADX WARN: Code restructure failed: missing block: B:134:0x03e2, code lost:

     r3 = r14;
  */
  /* JADX WARN: Code restructure failed: missing block: B:135:0x03e7, code lost:

     android.util.Slog.w(com.android.server.cocktailbar.settings.CocktailOrderManager.TAG, "failed to close stream " + r13);
  */
  /* JADX WARN: Code restructure failed: missing block: B:136:0x03fb, code lost:

     if (r3 != null) goto L278;
  */
  /* JADX WARN: Code restructure failed: missing block: B:138:0x03fd, code lost:

     r3.close();
  */
  /* JADX WARN: Code restructure failed: missing block: B:140:0x0401, code lost:

     r13 = e;
  */
  /* JADX WARN: Code restructure failed: missing block: B:141:0x0402, code lost:

     r14 = com.android.server.cocktailbar.settings.CocktailOrderManager.TAG;
     r0 = new java.lang.StringBuilder();
  */
  /* JADX WARN: Code restructure failed: missing block: B:142:0x03de, code lost:

     r13 = th;
  */
  /* JADX WARN: Code restructure failed: missing block: B:143:0x03df, code lost:

     r3 = r14;
  */
  /* JADX WARN: Code restructure failed: missing block: B:144:0x040b, code lost:

     if (r3 != null) goto L289;
  */
  /* JADX WARN: Code restructure failed: missing block: B:145:0x0426, code lost:

     throw r13;
  */
  /* JADX WARN: Code restructure failed: missing block: B:147:0x040d, code lost:

     r3.close();
  */
  /* JADX WARN: Code restructure failed: missing block: B:149:0x0411, code lost:

     r14 = move-exception;
  */
  /* JADX WARN: Code restructure failed: missing block: B:150:0x0412, code lost:

     android.util.Slog.w(com.android.server.cocktailbar.settings.CocktailOrderManager.TAG, "failed to close stream " + r14);
  */
  /* JADX WARN: Code restructure failed: missing block: B:151:0x03e6, code lost:

     r13 = e;
  */
  /* JADX WARN: Code restructure failed: missing block: B:152:?, code lost:

     return;
  */
  /* JADX WARN: Code restructure failed: missing block: B:153:0x03e4, code lost:

     r13 = th;
  */
  /* JADX WARN: Code restructure failed: missing block: B:154:0x00ac, code lost:

     r14 = e;
  */
  /* JADX WARN: Code restructure failed: missing block: B:155:0x00ad, code lost:

     r4 = true;
  */
  /* JADX WARN: Code restructure failed: missing block: B:156:0x0308, code lost:

     android.util.Slog.w(com.android.server.cocktailbar.settings.CocktailOrderManager.TAG, "failed parsing " + r14);
  */
  /* JADX WARN: Code restructure failed: missing block: B:158:0x031c, code lost:

     if (r4 == false) goto L319;
  */
  /* JADX WARN: Code restructure failed: missing block: B:160:0x031e, code lost:

     r14 = new java.io.BufferedReader(new java.io.InputStreamReader(savedStateFile().openRead(), "UTF-8"));
  */
  /* JADX WARN: Code restructure failed: missing block: B:163:0x0330, code lost:

     r13 = new java.lang.StringBuffer();
  */
  /* JADX WARN: Code restructure failed: missing block: B:164:0x0335, code lost:

     r0 = r14.readLine();
  */
  /* JADX WARN: Code restructure failed: missing block: B:165:0x0339, code lost:

     if (r0 != null) goto L170;
  */
  /* JADX WARN: Code restructure failed: missing block: B:166:0x033b, code lost:

     r13.append(r0);
  */
  /* JADX WARN: Code restructure failed: missing block: B:168:0x033f, code lost:

     r14.close();
  */
  /* JADX WARN: Code restructure failed: missing block: B:172:0x0344, code lost:

     r13 = e;
  */
  /* JADX WARN: Code restructure failed: missing block: B:173:0x0345, code lost:

     r14 = com.android.server.cocktailbar.settings.CocktailOrderManager.TAG;
     r0 = new java.lang.StringBuilder();
  */
  /* JADX WARN: Code restructure failed: missing block: B:175:0x0351, code lost:

     r13 = e;
  */
  /* JADX WARN: Code restructure failed: missing block: B:176:0x0352, code lost:

     r3 = r14;
  */
  /* JADX WARN: Code restructure failed: missing block: B:177:0x0357, code lost:

     android.util.Slog.w(com.android.server.cocktailbar.settings.CocktailOrderManager.TAG, "failed to close stream " + r13);
  */
  /* JADX WARN: Code restructure failed: missing block: B:178:0x036b, code lost:

     if (r3 != null) goto L268;
  */
  /* JADX WARN: Code restructure failed: missing block: B:180:0x036d, code lost:

     r3.close();
  */
  /* JADX WARN: Code restructure failed: missing block: B:182:0x0372, code lost:

     r13 = e;
  */
  /* JADX WARN: Code restructure failed: missing block: B:183:0x0373, code lost:

     r14 = com.android.server.cocktailbar.settings.CocktailOrderManager.TAG;
     r0 = new java.lang.StringBuilder();
  */
  /* JADX WARN: Code restructure failed: missing block: B:184:0x034e, code lost:

     r13 = th;
  */
  /* JADX WARN: Code restructure failed: missing block: B:185:0x034f, code lost:

     r3 = r14;
  */
  /* JADX WARN: Code restructure failed: missing block: B:186:0x037c, code lost:

     if (r3 != null) goto L280;
  */
  /* JADX WARN: Code restructure failed: missing block: B:187:0x0397, code lost:

     throw r13;
  */
  /* JADX WARN: Code restructure failed: missing block: B:189:0x037e, code lost:

     r3.close();
  */
  /* JADX WARN: Code restructure failed: missing block: B:191:0x0382, code lost:

     r14 = move-exception;
  */
  /* JADX WARN: Code restructure failed: missing block: B:192:0x0383, code lost:

     android.util.Slog.w(com.android.server.cocktailbar.settings.CocktailOrderManager.TAG, "failed to close stream " + r14);
  */
  /* JADX WARN: Code restructure failed: missing block: B:193:0x0356, code lost:

     r13 = e;
  */
  /* JADX WARN: Code restructure failed: missing block: B:194:?, code lost:

     return;
  */
  /* JADX WARN: Code restructure failed: missing block: B:195:0x0354, code lost:

     r13 = th;
  */
  /* JADX WARN: Code restructure failed: missing block: B:196:0x00a8, code lost:

     r14 = e;
  */
  /* JADX WARN: Code restructure failed: missing block: B:197:0x00a9, code lost:

     r4 = true;
  */
  /* JADX WARN: Code restructure failed: missing block: B:198:0x0277, code lost:

     android.util.Slog.w(com.android.server.cocktailbar.settings.CocktailOrderManager.TAG, "failed parsing " + r14);
  */
  /* JADX WARN: Code restructure failed: missing block: B:200:0x028b, code lost:

     if (r4 == false) goto L311;
  */
  /* JADX WARN: Code restructure failed: missing block: B:202:0x028d, code lost:

     r14 = new java.io.BufferedReader(new java.io.InputStreamReader(savedStateFile().openRead(), "UTF-8"));
  */
  /* JADX WARN: Code restructure failed: missing block: B:205:0x029f, code lost:

     r13 = new java.lang.StringBuffer();
  */
  /* JADX WARN: Code restructure failed: missing block: B:206:0x02a4, code lost:

     r0 = r14.readLine();
  */
  /* JADX WARN: Code restructure failed: missing block: B:207:0x02a8, code lost:

     if (r0 != null) goto L138;
  */
  /* JADX WARN: Code restructure failed: missing block: B:208:0x02aa, code lost:

     r13.append(r0);
  */
  /* JADX WARN: Code restructure failed: missing block: B:210:0x02ae, code lost:

     r14.close();
  */
  /* JADX WARN: Code restructure failed: missing block: B:214:0x02b3, code lost:

     r13 = e;
  */
  /* JADX WARN: Code restructure failed: missing block: B:215:0x02b4, code lost:

     r14 = com.android.server.cocktailbar.settings.CocktailOrderManager.TAG;
     r0 = new java.lang.StringBuilder();
  */
  /* JADX WARN: Code restructure failed: missing block: B:217:0x02c0, code lost:

     r13 = e;
  */
  /* JADX WARN: Code restructure failed: missing block: B:218:0x02c1, code lost:

     r3 = r14;
  */
  /* JADX WARN: Code restructure failed: missing block: B:219:0x02c6, code lost:

     android.util.Slog.w(com.android.server.cocktailbar.settings.CocktailOrderManager.TAG, "failed to close stream " + r13);
  */
  /* JADX WARN: Code restructure failed: missing block: B:220:0x02da, code lost:

     if (r3 != null) goto L334;
  */
  /* JADX WARN: Code restructure failed: missing block: B:222:0x02dc, code lost:

     r3.close();
  */
  /* JADX WARN: Code restructure failed: missing block: B:224:0x02e1, code lost:

     r13 = e;
  */
  /* JADX WARN: Code restructure failed: missing block: B:225:0x02e2, code lost:

     r14 = com.android.server.cocktailbar.settings.CocktailOrderManager.TAG;
     r0 = new java.lang.StringBuilder();
  */
  /* JADX WARN: Code restructure failed: missing block: B:226:0x02bd, code lost:

     r13 = th;
  */
  /* JADX WARN: Code restructure failed: missing block: B:227:0x02be, code lost:

     r3 = r14;
  */
  /* JADX WARN: Code restructure failed: missing block: B:228:0x02eb, code lost:

     if (r3 != null) goto L266;
  */
  /* JADX WARN: Code restructure failed: missing block: B:229:0x0306, code lost:

     throw r13;
  */
  /* JADX WARN: Code restructure failed: missing block: B:231:0x02ed, code lost:

     r3.close();
  */
  /* JADX WARN: Code restructure failed: missing block: B:233:0x02f1, code lost:

     r14 = move-exception;
  */
  /* JADX WARN: Code restructure failed: missing block: B:234:0x02f2, code lost:

     android.util.Slog.w(com.android.server.cocktailbar.settings.CocktailOrderManager.TAG, "failed to close stream " + r14);
  */
  /* JADX WARN: Code restructure failed: missing block: B:235:0x02c5, code lost:

     r13 = e;
  */
  /* JADX WARN: Code restructure failed: missing block: B:236:?, code lost:

     return;
  */
  /* JADX WARN: Code restructure failed: missing block: B:237:0x02c3, code lost:

     r13 = th;
  */
  /* JADX WARN: Code restructure failed: missing block: B:238:0x009d, code lost:

     r14 = e;
  */
  /* JADX WARN: Code restructure failed: missing block: B:239:0x009e, code lost:

     r4 = true;
  */
  /* JADX WARN: Code restructure failed: missing block: B:23:0x00a4, code lost:

     r14 = e;
  */
  /* JADX WARN: Code restructure failed: missing block: B:240:0x00b8, code lost:

     android.util.Slog.w(com.android.server.cocktailbar.settings.CocktailOrderManager.TAG, "failed parsing " + r14);
  */
  /* JADX WARN: Code restructure failed: missing block: B:241:0x00cc, code lost:

     if (r4 == false) goto L287;
  */
  /* JADX WARN: Code restructure failed: missing block: B:243:0x00ce, code lost:

     r14 = new java.io.BufferedReader(new java.io.InputStreamReader(savedStateFile().openRead(), "UTF-8"));
  */
  /* JADX WARN: Code restructure failed: missing block: B:246:0x00e0, code lost:

     r13 = new java.lang.StringBuffer();
  */
  /* JADX WARN: Code restructure failed: missing block: B:247:0x00e5, code lost:

     r0 = r14.readLine();
  */
  /* JADX WARN: Code restructure failed: missing block: B:248:0x00e9, code lost:

     if (r0 != null) goto L41;
  */
  /* JADX WARN: Code restructure failed: missing block: B:249:0x00eb, code lost:

     r13.append(r0);
  */
  /* JADX WARN: Code restructure failed: missing block: B:24:0x00a5, code lost:

     r4 = true;
  */
  /* JADX WARN: Code restructure failed: missing block: B:251:0x00ef, code lost:

     r14.close();
  */
  /* JADX WARN: Code restructure failed: missing block: B:253:?, code lost:

     return;
  */
  /* JADX WARN: Code restructure failed: missing block: B:254:0x00f4, code lost:

     r13 = e;
  */
  /* JADX WARN: Code restructure failed: missing block: B:255:0x00f5, code lost:

     r14 = com.android.server.cocktailbar.settings.CocktailOrderManager.TAG;
     r0 = new java.lang.StringBuilder();
  */
  /* JADX WARN: Code restructure failed: missing block: B:257:0x010e, code lost:

     r13 = e;
  */
  /* JADX WARN: Code restructure failed: missing block: B:258:0x010f, code lost:

     r3 = r14;
  */
  /* JADX WARN: Code restructure failed: missing block: B:259:0x0114, code lost:

     android.util.Slog.w(com.android.server.cocktailbar.settings.CocktailOrderManager.TAG, "failed to close stream " + r13);
  */
  /* JADX WARN: Code restructure failed: missing block: B:25:0x01e6, code lost:

     android.util.Slog.w(com.android.server.cocktailbar.settings.CocktailOrderManager.TAG, "failed parsing " + r14);
  */
  /* JADX WARN: Code restructure failed: missing block: B:260:0x0128, code lost:

     if (r3 != null) goto L308;
  */
  /* JADX WARN: Code restructure failed: missing block: B:262:0x012a, code lost:

     r3.close();
  */
  /* JADX WARN: Code restructure failed: missing block: B:264:?, code lost:

     return;
  */
  /* JADX WARN: Code restructure failed: missing block: B:265:0x012f, code lost:

     r13 = e;
  */
  /* JADX WARN: Code restructure failed: missing block: B:266:0x0130, code lost:

     r14 = com.android.server.cocktailbar.settings.CocktailOrderManager.TAG;
     r0 = new java.lang.StringBuilder();
  */
  /* JADX WARN: Code restructure failed: missing block: B:267:?, code lost:

     return;
  */
  /* JADX WARN: Code restructure failed: missing block: B:269:0x010b, code lost:

     r13 = th;
  */
  /* JADX WARN: Code restructure failed: missing block: B:270:0x010c, code lost:

     r3 = r14;
  */
  /* JADX WARN: Code restructure failed: missing block: B:271:0x0138, code lost:

     if (r3 != null) goto L317;
  */
  /* JADX WARN: Code restructure failed: missing block: B:272:0x0153, code lost:

     throw r13;
  */
  /* JADX WARN: Code restructure failed: missing block: B:274:0x013a, code lost:

     r3.close();
  */
  /* JADX WARN: Code restructure failed: missing block: B:276:0x013e, code lost:

     r14 = move-exception;
  */
  /* JADX WARN: Code restructure failed: missing block: B:277:0x013f, code lost:

     android.util.Slog.w(com.android.server.cocktailbar.settings.CocktailOrderManager.TAG, "failed to close stream " + r14);
  */
  /* JADX WARN: Code restructure failed: missing block: B:278:0x0113, code lost:

     r13 = e;
  */
  /* JADX WARN: Code restructure failed: missing block: B:279:0x0111, code lost:

     r13 = th;
  */
  /* JADX WARN: Code restructure failed: missing block: B:27:0x01fa, code lost:

     if (r4 == false) goto L306;
  */
  /* JADX WARN: Code restructure failed: missing block: B:280:?, code lost:

     return;
  */
  /* JADX WARN: Code restructure failed: missing block: B:281:0x0099, code lost:

     r14 = th;
  */
  /* JADX WARN: Code restructure failed: missing block: B:282:0x009a, code lost:

     r4 = true;
  */
  /* JADX WARN: Code restructure failed: missing block: B:283:0x0428, code lost:

     if (r4 == false) goto L270;
  */
  /* JADX WARN: Code restructure failed: missing block: B:284:0x04ad, code lost:

     throw r14;
  */
  /* JADX WARN: Code restructure failed: missing block: B:286:0x042a, code lost:

     r0 = new java.io.BufferedReader(new java.io.InputStreamReader(savedStateFile().openRead(), "UTF-8"));
  */
  /* JADX WARN: Code restructure failed: missing block: B:289:0x043c, code lost:

     r13 = new java.lang.StringBuffer();
  */
  /* JADX WARN: Code restructure failed: missing block: B:290:0x0441, code lost:

     r1 = r0.readLine();
  */
  /* JADX WARN: Code restructure failed: missing block: B:291:0x0445, code lost:

     if (r1 != null) goto L233;
  */
  /* JADX WARN: Code restructure failed: missing block: B:292:0x0447, code lost:

     r13.append(r1);
  */
  /* JADX WARN: Code restructure failed: missing block: B:294:0x044b, code lost:

     r0.close();
  */
  /* JADX WARN: Code restructure failed: missing block: B:296:0x044f, code lost:

     r13 = e;
  */
  /* JADX WARN: Code restructure failed: missing block: B:297:0x0450, code lost:

     r0 = com.android.server.cocktailbar.settings.CocktailOrderManager.TAG;
     r1 = new java.lang.StringBuilder();
  */
  /* JADX WARN: Code restructure failed: missing block: B:298:0x0457, code lost:

     r1.append("failed to close stream ");
     r1.append(r13);
     android.util.Slog.w(r0, r1.toString());
  */
  /* JADX WARN: Code restructure failed: missing block: B:29:0x01fc, code lost:

     r14 = new java.io.BufferedReader(new java.io.InputStreamReader(savedStateFile().openRead(), "UTF-8"));
  */
  /* JADX WARN: Code restructure failed: missing block: B:300:0x0468, code lost:

     r13 = e;
  */
  /* JADX WARN: Code restructure failed: missing block: B:301:0x0469, code lost:

     r3 = r0;
  */
  /* JADX WARN: Code restructure failed: missing block: B:302:0x046e, code lost:

     android.util.Slog.w(com.android.server.cocktailbar.settings.CocktailOrderManager.TAG, "failed to close stream " + r13);
  */
  /* JADX WARN: Code restructure failed: missing block: B:303:0x0482, code lost:

     if (r3 != null) goto L275;
  */
  /* JADX WARN: Code restructure failed: missing block: B:305:0x0484, code lost:

     r3.close();
  */
  /* JADX WARN: Code restructure failed: missing block: B:307:0x0488, code lost:

     r13 = e;
  */
  /* JADX WARN: Code restructure failed: missing block: B:308:0x0489, code lost:

     r0 = com.android.server.cocktailbar.settings.CocktailOrderManager.TAG;
     r1 = new java.lang.StringBuilder();
  */
  /* JADX WARN: Code restructure failed: missing block: B:310:0x0465, code lost:

     r13 = th;
  */
  /* JADX WARN: Code restructure failed: missing block: B:311:0x0466, code lost:

     r3 = r0;
  */
  /* JADX WARN: Code restructure failed: missing block: B:312:0x0491, code lost:

     if (r3 != null) goto L282;
  */
  /* JADX WARN: Code restructure failed: missing block: B:313:0x04ac, code lost:

     throw r13;
  */
  /* JADX WARN: Code restructure failed: missing block: B:315:0x0493, code lost:

     r3.close();
  */
  /* JADX WARN: Code restructure failed: missing block: B:317:0x0497, code lost:

     r14 = move-exception;
  */
  /* JADX WARN: Code restructure failed: missing block: B:318:0x0498, code lost:

     android.util.Slog.w(com.android.server.cocktailbar.settings.CocktailOrderManager.TAG, "failed to close stream " + r14);
  */
  /* JADX WARN: Code restructure failed: missing block: B:319:0x046d, code lost:

     r13 = e;
  */
  /* JADX WARN: Code restructure failed: missing block: B:320:0x046b, code lost:

     r13 = th;
  */
  /* JADX WARN: Code restructure failed: missing block: B:32:0x020e, code lost:

     r13 = new java.lang.StringBuffer();
  */
  /* JADX WARN: Code restructure failed: missing block: B:33:0x0213, code lost:

     r0 = r14.readLine();
  */
  /* JADX WARN: Code restructure failed: missing block: B:34:0x0217, code lost:

     if (r0 != null) goto L106;
  */
  /* JADX WARN: Code restructure failed: missing block: B:35:0x0219, code lost:

     r13.append(r0);
  */
  /* JADX WARN: Code restructure failed: missing block: B:37:0x021d, code lost:

     r14.close();
  */
  /* JADX WARN: Code restructure failed: missing block: B:40:?, code lost:

     return;
  */
  /* JADX WARN: Code restructure failed: missing block: B:42:0x0222, code lost:

     r13 = e;
  */
  /* JADX WARN: Code restructure failed: missing block: B:43:0x0223, code lost:

     r14 = com.android.server.cocktailbar.settings.CocktailOrderManager.TAG;
     r0 = new java.lang.StringBuilder();
  */
  /* JADX WARN: Code restructure failed: missing block: B:44:0x00fc, code lost:

     r0.append("failed to close stream ");
     r0.append(r13);
     android.util.Slog.w(r14, r0.toString());
  */
  /* JADX WARN: Code restructure failed: missing block: B:45:?, code lost:

     return;
  */
  /* JADX WARN: Code restructure failed: missing block: B:47:0x022f, code lost:

     r13 = e;
  */
  /* JADX WARN: Code restructure failed: missing block: B:48:0x0230, code lost:

     r3 = r14;
  */
  /* JADX WARN: Code restructure failed: missing block: B:49:0x0235, code lost:

     android.util.Slog.w(com.android.server.cocktailbar.settings.CocktailOrderManager.TAG, "failed to close stream " + r13);
  */
  /* JADX WARN: Code restructure failed: missing block: B:50:0x0249, code lost:

     if (r3 != null) goto L327;
  */
  /* JADX WARN: Code restructure failed: missing block: B:52:0x024b, code lost:

     r3.close();
  */
  /* JADX WARN: Code restructure failed: missing block: B:54:0x0250, code lost:

     r13 = e;
  */
  /* JADX WARN: Code restructure failed: missing block: B:55:0x0251, code lost:

     r14 = com.android.server.cocktailbar.settings.CocktailOrderManager.TAG;
     r0 = new java.lang.StringBuilder();
  */
  /* JADX WARN: Code restructure failed: missing block: B:56:0x022c, code lost:

     r13 = th;
  */
  /* JADX WARN: Code restructure failed: missing block: B:57:0x022d, code lost:

     r3 = r14;
  */
  /* JADX WARN: Code restructure failed: missing block: B:58:0x025a, code lost:

     if (r3 != null) goto L336;
  */
  /* JADX WARN: Code restructure failed: missing block: B:59:0x0275, code lost:

     throw r13;
  */
  /* JADX WARN: Code restructure failed: missing block: B:61:0x025c, code lost:

     r3.close();
  */
  /* JADX WARN: Code restructure failed: missing block: B:63:0x0260, code lost:

     r14 = move-exception;
  */
  /* JADX WARN: Code restructure failed: missing block: B:64:0x0261, code lost:

     android.util.Slog.w(com.android.server.cocktailbar.settings.CocktailOrderManager.TAG, "failed to close stream " + r14);
  */
  /* JADX WARN: Code restructure failed: missing block: B:65:0x0234, code lost:

     r13 = e;
  */
  /* JADX WARN: Code restructure failed: missing block: B:66:?, code lost:

     return;
  */
  /* JADX WARN: Code restructure failed: missing block: B:67:0x0232, code lost:

     r13 = th;
  */
  /* JADX WARN: Code restructure failed: missing block: B:68:0x00a0, code lost:

     r14 = e;
  */
  /* JADX WARN: Code restructure failed: missing block: B:69:0x00a1, code lost:

     r4 = true;
  */
  /* JADX WARN: Code restructure failed: missing block: B:70:0x0155, code lost:

     android.util.Slog.w(com.android.server.cocktailbar.settings.CocktailOrderManager.TAG, "failed parsing " + r14);
  */
  /* JADX WARN: Code restructure failed: missing block: B:72:0x0169, code lost:

     if (r4 == false) goto L296;
  */
  /* JADX WARN: Code restructure failed: missing block: B:74:0x016b, code lost:

     r14 = new java.io.BufferedReader(new java.io.InputStreamReader(savedStateFile().openRead(), "UTF-8"));
  */
  /* JADX WARN: Code restructure failed: missing block: B:77:0x017d, code lost:

     r13 = new java.lang.StringBuffer();
  */
  /* JADX WARN: Code restructure failed: missing block: B:78:0x0182, code lost:

     r0 = r14.readLine();
  */
  /* JADX WARN: Code restructure failed: missing block: B:79:0x0186, code lost:

     if (r0 != null) goto L74;
  */
  /* JADX WARN: Code restructure failed: missing block: B:80:0x0188, code lost:

     r13.append(r0);
  */
  /* JADX WARN: Code restructure failed: missing block: B:82:0x018c, code lost:

     r14.close();
  */
  /* JADX WARN: Code restructure failed: missing block: B:85:?, code lost:

     return;
  */
  /* JADX WARN: Code restructure failed: missing block: B:87:0x0191, code lost:

     r13 = e;
  */
  /* JADX WARN: Code restructure failed: missing block: B:88:0x0192, code lost:

     r14 = com.android.server.cocktailbar.settings.CocktailOrderManager.TAG;
     r0 = new java.lang.StringBuilder();
  */
  /* JADX WARN: Code restructure failed: missing block: B:90:0x019e, code lost:

     r13 = e;
  */
  /* JADX WARN: Code restructure failed: missing block: B:91:0x019f, code lost:

     r3 = r14;
  */
  /* JADX WARN: Code restructure failed: missing block: B:92:0x01a4, code lost:

     android.util.Slog.w(com.android.server.cocktailbar.settings.CocktailOrderManager.TAG, "failed to close stream " + r13);
  */
  /* JADX WARN: Code restructure failed: missing block: B:93:0x01b8, code lost:

     if (r3 != null) goto L315;
  */
  /* JADX WARN: Code restructure failed: missing block: B:95:0x01ba, code lost:

     r3.close();
  */
  /* JADX WARN: Code restructure failed: missing block: B:97:0x01bf, code lost:

     r13 = e;
  */
  /* JADX WARN: Code restructure failed: missing block: B:98:0x01c0, code lost:

     r14 = com.android.server.cocktailbar.settings.CocktailOrderManager.TAG;
     r0 = new java.lang.StringBuilder();
  */
  /* JADX WARN: Code restructure failed: missing block: B:99:0x019b, code lost:

     r13 = th;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final void readStateFromFileLocked(FileInputStream fileInputStream) {
    int next;
    BufferedReader bufferedReader = null;
    boolean z = false;
    try {
      try {
        try {
          XmlPullParser newPullParser = Xml.newPullParser();
          newPullParser.setInput(fileInputStream, null);
          ArrayList arrayList = new ArrayList();
          do {
            next = newPullParser.next();
            if (next == 2) {
              String name = newPullParser.getName();
              if ("cocktail_order".equals(name)) {
                try {
                  Integer.parseInt(newPullParser.getAttributeValue(null, "version"));
                } catch (NumberFormatException unused) {
                }
              } else if ("provider".equals(name)) {
                arrayList.add(
                    new CocktailOrderInfo(
                        Integer.parseInt(newPullParser.getAttributeValue(null, "cocktail_id")),
                        new ComponentName(
                            newPullParser.getAttributeValue(null, "package"),
                            newPullParser.getAttributeValue(null, "class_name")),
                        Integer.parseInt(newPullParser.getAttributeValue(null, "user_id")),
                        Integer.parseInt(newPullParser.getAttributeValue(null, "order"))));
              }
            }
          } while (next != 1);
          this.mCocktailOrderedList = arrayList;
          Slog.d(TAG, "readStateFromFileLocked: orderList=" + this.mCocktailOrderedList.toString());
        } catch (Throwable th) {
          th = th;
        }
      } catch (NumberFormatException e) {
        e = e;
      }
    } catch (IOException e2) {
      e = e2;
    } catch (IndexOutOfBoundsException e3) {
      e = e3;
    } catch (NullPointerException e4) {
      e = e4;
    } catch (XmlPullParserException e5) {
      e = e5;
    } catch (Exception e6) {
      e = e6;
    }
  }

  public void setOrderedList(ArrayList arrayList) {
    synchronized (this.mLock) {
      this.mCocktailOrderedList = arrayList;
      saveStateLocked();
    }
  }

  public class CocktailOrderInfo {
    public int mCocktailId;
    public ComponentName mComponentName;
    public int mOrder;
    public int mUserId;

    public CocktailOrderInfo(Cocktail cocktail, int i, int i2) {
      if (cocktail != null) {
        this.mCocktailId = cocktail.getCocktailId();
        if (cocktail.getProvider() != null) {
          this.mComponentName = cocktail.getProvider();
        }
      }
      this.mOrder = i2;
      this.mUserId = i;
    }

    public CocktailOrderInfo(int i, ComponentName componentName, int i2, int i3) {
      this.mCocktailId = i;
      this.mComponentName = componentName;
      this.mOrder = i3;
      this.mUserId = i2;
    }

    public String toString() {
      StringBuffer stringBuffer = new StringBuffer("CocktailOrderInfo");
      stringBuffer.append(super.toString());
      stringBuffer.append(" uid=");
      stringBuffer.append(this.mUserId);
      stringBuffer.append(" cid=");
      stringBuffer.append(this.mCocktailId);
      stringBuffer.append(" order=");
      stringBuffer.append(this.mOrder);
      stringBuffer.append(this.mComponentName);
      return stringBuffer.toString();
    }
  }

  public int[] getMatchedSortIds(ArrayList arrayList, SparseArray sparseArray) {
    ArrayList arrayList2 = this.mCocktailOrderedList;
    if (arrayList2 == null || arrayList2.isEmpty()) {
      synchronized (this.mLock) {
        loadStateLocked();
        String str = TAG;
        StringBuilder sb = new StringBuilder();
        sb.append("getMatchedSortIds: loadedorder=");
        ArrayList arrayList3 = this.mCocktailOrderedList;
        String str2 = arrayList3;
        if (arrayList3 != null) {
          str2 = arrayList3.toString();
        }
        sb.append((Object) str2);
        Slog.d(str, sb.toString());
      }
    }
    ArrayList arrayList4 = this.mCocktailOrderedList;
    if (arrayList4 != null && !arrayList4.isEmpty()) {
      Collections.sort(
          arrayList, new CocktailOrderComparator(this.mCocktailOrderedList, sparseArray));
    }
    int size = arrayList.size();
    int[] iArr = new int[size];
    for (int i = 0; i < size; i++) {
      iArr[i] = ((Integer) arrayList.get(i)).intValue();
    }
    return iArr;
  }

  public class CocktailOrderComparator implements Comparator {
    public SparseArray mEnabledPanelInfoArray;
    public HashMap mOrderInfoMap;

    public CocktailOrderComparator(ArrayList arrayList, SparseArray sparseArray) {
      synchronized (CocktailOrderManager.this.mLock) {
        this.mOrderInfoMap = new HashMap();
        this.mEnabledPanelInfoArray = sparseArray;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
          CocktailOrderInfo cocktailOrderInfo = (CocktailOrderInfo) arrayList.get(i);
          if (cocktailOrderInfo != null && cocktailOrderInfo.mComponentName != null) {
            String convertedComponent =
                CocktailBarConfig.getInstance(CocktailOrderManager.this.mContext)
                    .getConvertedComponent(cocktailOrderInfo.mComponentName.getClassName());
            this.mOrderInfoMap.put(
                convertedComponent != null
                    ? convertedComponent + "_" + cocktailOrderInfo.mUserId
                    : cocktailOrderInfo.mComponentName.getClassName()
                        + "_"
                        + cocktailOrderInfo.mUserId,
                Integer.valueOf(cocktailOrderInfo.mOrder));
          }
        }
      }
    }

    @Override // java.util.Comparator
    public int compare(Integer num, Integer num2) {
      try {
        String str = (String) this.mEnabledPanelInfoArray.get(num.intValue());
        String str2 = (String) this.mEnabledPanelInfoArray.get(num2.intValue());
        Integer valueOf =
            Integer.valueOf(
                this.mOrderInfoMap.get(str) != null
                    ? ((Integer) this.mOrderInfoMap.get(str)).intValue()
                    : this.mOrderInfoMap.size());
        Integer valueOf2 =
            Integer.valueOf(
                this.mOrderInfoMap.get(str2) != null
                    ? ((Integer) this.mOrderInfoMap.get(str2)).intValue()
                    : this.mOrderInfoMap.size());
        if (valueOf == null || valueOf2 == null) {
          return 0;
        }
        return valueOf.intValue() - valueOf2.intValue();
      } catch (Exception unused) {
        Slog.d(
            CocktailOrderManager.TAG,
            "CocktailOrderComparator: no order info for " + num + " and " + num2);
        return 0;
      }
    }
  }

  public String dump() {
    StringBuffer stringBuffer = new StringBuffer("[CocktailOrderManager]: ");
    synchronized (this.mLock) {
      ArrayList arrayList = this.mCocktailOrderedList;
      if (arrayList != null) {
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
          stringBuffer.append(((CocktailOrderInfo) this.mCocktailOrderedList.get(i)).mCocktailId);
          stringBuffer.append(" ");
        }
        stringBuffer.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
      } else {
        stringBuffer.append("null\n");
      }
    }
    return stringBuffer.toString();
  }
}
