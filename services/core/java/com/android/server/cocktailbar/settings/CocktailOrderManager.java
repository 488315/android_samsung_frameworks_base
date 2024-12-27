package com.android.server.cocktailbar.settings;

import android.content.ComponentName;
import android.content.Context;
import android.os.Environment;
import android.util.AtomicFile;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.util.FastXmlSerializer;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class CocktailOrderManager {
    public ArrayList mCocktailOrderedList;
    public Context mContext;
    public Object mLock;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CocktailOrderComparator implements Comparator {
        public SparseArray mEnabledPanelInfoArray;
        public HashMap mOrderInfoMap;

        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            Integer num = (Integer) obj;
            Integer num2 = (Integer) obj2;
            try {
                String str = (String) this.mEnabledPanelInfoArray.get(num.intValue());
                String str2 = (String) this.mEnabledPanelInfoArray.get(num2.intValue());
                return (this.mOrderInfoMap.get(str) != null ? ((Integer) this.mOrderInfoMap.get(str)).intValue() : this.mOrderInfoMap.size()) - (this.mOrderInfoMap.get(str2) != null ? ((Integer) this.mOrderInfoMap.get(str2)).intValue() : this.mOrderInfoMap.size());
            } catch (Exception unused) {
                Slog.d("CocktailOrderManager", "CocktailOrderComparator: no order info for " + num + " and " + num2);
                return 0;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CocktailOrderInfo {
        public int mCocktailId;
        public ComponentName mComponentName;
        public int mOrder;
        public int mUserId;

        public CocktailOrderInfo(int i, int i2, int i3, ComponentName componentName) {
            this.mCocktailId = i;
            this.mComponentName = componentName;
            this.mOrder = i3;
            this.mUserId = i2;
        }

        public final String toString() {
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

    public static AtomicFile savedStateFile() {
        File userSystemDirectory = Environment.getUserSystemDirectory(0);
        File file = new File(Environment.getUserSystemDirectory(0), "cocktail_order.xml");
        if (!file.exists()) {
            if (!userSystemDirectory.exists() && !userSystemDirectory.mkdirs()) {
                Slog.w("CocktailOrderManager", "savedStateFile Failed mkdirs");
            }
            if (!new File("/data/system/cocktail_order.xml").renameTo(file)) {
                Slog.w("CocktailOrderManager", "Failed rename to setting file.");
            }
        }
        return new AtomicFile(file);
    }

    public final String dump() {
        StringBuffer stringBuffer = new StringBuffer("[CocktailOrderManager]: ");
        synchronized (this.mLock) {
            try {
                ArrayList arrayList = this.mCocktailOrderedList;
                if (arrayList != null) {
                    int size = arrayList.size();
                    for (int i = 0; i < size; i++) {
                        stringBuffer.append(((CocktailOrderInfo) this.mCocktailOrderedList.get(i)).mCocktailId);
                        stringBuffer.append(" ");
                    }
                    stringBuffer.append("\n");
                } else {
                    stringBuffer.append("null\n");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return stringBuffer.toString();
    }

    /* JADX WARN: Code restructure failed: missing block: B:101:0x02eb, code lost:
    
        r15 = new java.io.BufferedReader(new java.io.InputStreamReader(savedStateFile().openRead(), "UTF-8"));
     */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x02fd, code lost:
    
        r14 = new java.lang.StringBuffer();
     */
    /* JADX WARN: Code restructure failed: missing block: B:105:0x0302, code lost:
    
        r0 = r15.readLine();
     */
    /* JADX WARN: Code restructure failed: missing block: B:106:0x0306, code lost:
    
        if (r0 != null) goto L189;
     */
    /* JADX WARN: Code restructure failed: missing block: B:107:0x0308, code lost:
    
        r14.append(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:109:0x0312, code lost:
    
        r15.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:111:0x030f, code lost:
    
        r14 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:112:0x0310, code lost:
    
        r4 = r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:113:0x0319, code lost:
    
        android.util.Slog.w("CocktailOrderManager", "failed to close stream " + r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:114:0x032b, code lost:
    
        if (r4 != null) goto L202;
     */
    /* JADX WARN: Code restructure failed: missing block: B:115:0x032d, code lost:
    
        r4.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:116:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:118:0x030c, code lost:
    
        r14 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:119:0x030d, code lost:
    
        r4 = r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:120:0x0331, code lost:
    
        if (r4 != null) goto L268;
     */
    /* JADX WARN: Code restructure failed: missing block: B:121:0x033b, code lost:
    
        throw r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:123:0x0333, code lost:
    
        r4.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:125:0x0337, code lost:
    
        r15 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:126:0x0338, code lost:
    
        com.android.server.DeviceIdleController$$ExternalSyntheticOutline0.m("failed to close stream ", r15, "CocktailOrderManager");
     */
    /* JADX WARN: Code restructure failed: missing block: B:127:0x0318, code lost:
    
        r14 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:128:0x0316, code lost:
    
        r14 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:129:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:130:0x00c6, code lost:
    
        r14 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:131:0x00c7, code lost:
    
        r5 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:132:0x0270, code lost:
    
        android.util.Slog.w("CocktailOrderManager", "failed parsing " + r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:134:0x0282, code lost:
    
        if (r5 == false) goto L275;
     */
    /* JADX WARN: Code restructure failed: missing block: B:136:0x0284, code lost:
    
        r15 = new java.io.BufferedReader(new java.io.InputStreamReader(savedStateFile().openRead(), "UTF-8"));
     */
    /* JADX WARN: Code restructure failed: missing block: B:139:0x0296, code lost:
    
        r14 = new java.lang.StringBuffer();
     */
    /* JADX WARN: Code restructure failed: missing block: B:140:0x029b, code lost:
    
        r0 = r15.readLine();
     */
    /* JADX WARN: Code restructure failed: missing block: B:141:0x029f, code lost:
    
        if (r0 != null) goto L162;
     */
    /* JADX WARN: Code restructure failed: missing block: B:142:0x02a1, code lost:
    
        r14.append(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:144:0x02ab, code lost:
    
        r15.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:148:0x02a8, code lost:
    
        r14 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:149:0x02a9, code lost:
    
        r4 = r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:150:0x02b3, code lost:
    
        android.util.Slog.w("CocktailOrderManager", "failed to close stream " + r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:151:0x02c5, code lost:
    
        if (r4 != null) goto L175;
     */
    /* JADX WARN: Code restructure failed: missing block: B:152:0x02c7, code lost:
    
        r4.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:153:0x02a5, code lost:
    
        r14 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:154:0x02a6, code lost:
    
        r4 = r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:155:0x02cc, code lost:
    
        if (r4 != null) goto L281;
     */
    /* JADX WARN: Code restructure failed: missing block: B:156:0x02d6, code lost:
    
        throw r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:158:0x02ce, code lost:
    
        r4.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:160:0x02d2, code lost:
    
        r15 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:161:0x02d3, code lost:
    
        com.android.server.DeviceIdleController$$ExternalSyntheticOutline0.m("failed to close stream ", r15, "CocktailOrderManager");
     */
    /* JADX WARN: Code restructure failed: missing block: B:162:0x02b2, code lost:
    
        r14 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:163:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:164:0x02b0, code lost:
    
        r14 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:165:0x00c2, code lost:
    
        r14 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:166:0x00c3, code lost:
    
        r5 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:167:0x0209, code lost:
    
        android.util.Slog.w("CocktailOrderManager", "failed parsing " + r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:169:0x021b, code lost:
    
        if (r5 == false) goto L283;
     */
    /* JADX WARN: Code restructure failed: missing block: B:171:0x021d, code lost:
    
        r15 = new java.io.BufferedReader(new java.io.InputStreamReader(savedStateFile().openRead(), "UTF-8"));
     */
    /* JADX WARN: Code restructure failed: missing block: B:174:0x022f, code lost:
    
        r14 = new java.lang.StringBuffer();
     */
    /* JADX WARN: Code restructure failed: missing block: B:175:0x0234, code lost:
    
        r0 = r15.readLine();
     */
    /* JADX WARN: Code restructure failed: missing block: B:176:0x0238, code lost:
    
        if (r0 != null) goto L135;
     */
    /* JADX WARN: Code restructure failed: missing block: B:177:0x023a, code lost:
    
        r14.append(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:179:0x0244, code lost:
    
        r15.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:183:0x0241, code lost:
    
        r14 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:184:0x0242, code lost:
    
        r4 = r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:185:0x024c, code lost:
    
        android.util.Slog.w("CocktailOrderManager", "failed to close stream " + r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:186:0x025e, code lost:
    
        if (r4 != null) goto L148;
     */
    /* JADX WARN: Code restructure failed: missing block: B:187:0x0260, code lost:
    
        r4.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:188:0x023e, code lost:
    
        r14 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:189:0x023f, code lost:
    
        r4 = r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:190:0x0265, code lost:
    
        if (r4 != null) goto L243;
     */
    /* JADX WARN: Code restructure failed: missing block: B:191:0x026f, code lost:
    
        throw r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:193:0x0267, code lost:
    
        r4.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:195:0x026b, code lost:
    
        r15 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:196:0x026c, code lost:
    
        com.android.server.DeviceIdleController$$ExternalSyntheticOutline0.m("failed to close stream ", r15, "CocktailOrderManager");
     */
    /* JADX WARN: Code restructure failed: missing block: B:197:0x024b, code lost:
    
        r14 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:198:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:199:0x0249, code lost:
    
        r14 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:200:0x00b7, code lost:
    
        r14 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:201:0x00b8, code lost:
    
        r5 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:202:0x00ce, code lost:
    
        android.util.Slog.w("CocktailOrderManager", "failed parsing " + r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:203:0x00e0, code lost:
    
        if (r5 == false) goto L256;
     */
    /* JADX WARN: Code restructure failed: missing block: B:205:0x00e2, code lost:
    
        r15 = new java.io.BufferedReader(new java.io.InputStreamReader(savedStateFile().openRead(), "UTF-8"));
     */
    /* JADX WARN: Code restructure failed: missing block: B:208:0x00f4, code lost:
    
        r14 = new java.lang.StringBuffer();
     */
    /* JADX WARN: Code restructure failed: missing block: B:209:0x00f9, code lost:
    
        r0 = r15.readLine();
     */
    /* JADX WARN: Code restructure failed: missing block: B:210:0x00fd, code lost:
    
        if (r0 != null) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:211:0x00ff, code lost:
    
        r14.append(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:213:0x0109, code lost:
    
        r15.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:215:0x0106, code lost:
    
        r14 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:216:0x0107, code lost:
    
        r4 = r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:217:0x0117, code lost:
    
        android.util.Slog.w("CocktailOrderManager", "failed to close stream " + r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:218:0x0129, code lost:
    
        if (r4 != null) goto L67;
     */
    /* JADX WARN: Code restructure failed: missing block: B:219:0x012b, code lost:
    
        r4.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:220:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:222:0x0103, code lost:
    
        r14 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:223:0x0104, code lost:
    
        r4 = r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:224:0x0130, code lost:
    
        if (r4 != null) goto L273;
     */
    /* JADX WARN: Code restructure failed: missing block: B:225:0x013a, code lost:
    
        throw r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:227:0x0132, code lost:
    
        r4.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:229:0x0136, code lost:
    
        r15 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:230:0x0137, code lost:
    
        com.android.server.DeviceIdleController$$ExternalSyntheticOutline0.m("failed to close stream ", r15, "CocktailOrderManager");
     */
    /* JADX WARN: Code restructure failed: missing block: B:231:0x0116, code lost:
    
        r14 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:232:0x0114, code lost:
    
        r14 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:233:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:234:0x00b3, code lost:
    
        r14 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:235:0x00b4, code lost:
    
        r5 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:237:0x033d, code lost:
    
        if (r5 == false) goto L241;
     */
    /* JADX WARN: Code restructure failed: missing block: B:239:0x033f, code lost:
    
        r0 = new java.io.BufferedReader(new java.io.InputStreamReader(savedStateFile().openRead(), "UTF-8"));
     */
    /* JADX WARN: Code restructure failed: missing block: B:242:0x0351, code lost:
    
        r15 = new java.lang.StringBuffer();
     */
    /* JADX WARN: Code restructure failed: missing block: B:243:0x0356, code lost:
    
        r1 = r0.readLine();
     */
    /* JADX WARN: Code restructure failed: missing block: B:244:0x035a, code lost:
    
        if (r1 != null) goto L216;
     */
    /* JADX WARN: Code restructure failed: missing block: B:245:0x035c, code lost:
    
        r15.append(r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:247:0x0366, code lost:
    
        r0.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x00be, code lost:
    
        r14 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:251:0x0363, code lost:
    
        r15 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:252:0x0364, code lost:
    
        r4 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:253:0x0372, code lost:
    
        android.util.Slog.w("CocktailOrderManager", "failed to close stream " + r15);
     */
    /* JADX WARN: Code restructure failed: missing block: B:254:0x0384, code lost:
    
        if (r4 != null) goto L231;
     */
    /* JADX WARN: Code restructure failed: missing block: B:255:0x0386, code lost:
    
        r4.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:257:0x0360, code lost:
    
        r14 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:258:0x0361, code lost:
    
        r4 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:259:0x038a, code lost:
    
        if (r4 != null) goto L261;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x00bf, code lost:
    
        r5 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:260:0x0394, code lost:
    
        throw r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:262:0x038c, code lost:
    
        r4.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:264:0x0390, code lost:
    
        r15 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:265:0x0391, code lost:
    
        com.android.server.DeviceIdleController$$ExternalSyntheticOutline0.m("failed to close stream ", r15, "CocktailOrderManager");
     */
    /* JADX WARN: Code restructure failed: missing block: B:266:0x0371, code lost:
    
        r15 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:267:0x036f, code lost:
    
        r14 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:268:0x0395, code lost:
    
        throw r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:269:0x036a, code lost:
    
        r15 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x01a2, code lost:
    
        android.util.Slog.w("CocktailOrderManager", "failed parsing " + r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:270:0x036b, code lost:
    
        com.android.server.DeviceIdleController$$ExternalSyntheticOutline0.m("failed to close stream ", r15, "CocktailOrderManager");
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x01b4, code lost:
    
        if (r5 == false) goto L290;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x01b6, code lost:
    
        r15 = new java.io.BufferedReader(new java.io.InputStreamReader(savedStateFile().openRead(), "UTF-8"));
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x01c8, code lost:
    
        r14 = new java.lang.StringBuffer();
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x01cd, code lost:
    
        r0 = r15.readLine();
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x01d1, code lost:
    
        if (r0 != null) goto L108;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x01d3, code lost:
    
        r14.append(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x01dd, code lost:
    
        r15.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x033c, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x01da, code lost:
    
        r14 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x01db, code lost:
    
        r4 = r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x01e5, code lost:
    
        android.util.Slog.w("CocktailOrderManager", "failed to close stream " + r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x01f7, code lost:
    
        if (r4 != null) goto L121;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x01f9, code lost:
    
        r4.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x01d7, code lost:
    
        r14 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x01d8, code lost:
    
        r4 = r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x01fe, code lost:
    
        if (r4 != null) goto L248;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0208, code lost:
    
        throw r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0200, code lost:
    
        r4.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x0204, code lost:
    
        r15 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x0205, code lost:
    
        com.android.server.DeviceIdleController$$ExternalSyntheticOutline0.m("failed to close stream ", r15, "CocktailOrderManager");
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x01e4, code lost:
    
        r14 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x01e2, code lost:
    
        r14 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x00ba, code lost:
    
        r14 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x00bb, code lost:
    
        r5 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x013b, code lost:
    
        android.util.Slog.w("CocktailOrderManager", "failed parsing " + r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x014d, code lost:
    
        if (r5 == false) goto L251;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x014f, code lost:
    
        r15 = new java.io.BufferedReader(new java.io.InputStreamReader(savedStateFile().openRead(), "UTF-8"));
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x0161, code lost:
    
        r14 = new java.lang.StringBuffer();
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x0166, code lost:
    
        r0 = r15.readLine();
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x016a, code lost:
    
        if (r0 != null) goto L81;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x016c, code lost:
    
        r14.append(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x0176, code lost:
    
        r15.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x0173, code lost:
    
        r14 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x0174, code lost:
    
        r4 = r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x017e, code lost:
    
        android.util.Slog.w("CocktailOrderManager", "failed to close stream " + r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x0190, code lost:
    
        if (r4 != null) goto L94;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x0192, code lost:
    
        r4.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x0170, code lost:
    
        r14 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x0171, code lost:
    
        r4 = r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x0197, code lost:
    
        if (r4 != null) goto L266;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x01a1, code lost:
    
        throw r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x0199, code lost:
    
        r4.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x019d, code lost:
    
        r15 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x019e, code lost:
    
        com.android.server.DeviceIdleController$$ExternalSyntheticOutline0.m("failed to close stream ", r15, "CocktailOrderManager");
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x017d, code lost:
    
        r14 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x017b, code lost:
    
        r14 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x00ca, code lost:
    
        r14 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x00cb, code lost:
    
        r5 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x02d7, code lost:
    
        android.util.Slog.w("CocktailOrderManager", "failed parsing " + r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x02e9, code lost:
    
        if (r5 == false) goto L254;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void readStateFromFileLocked(java.io.FileInputStream r15) {
        /*
            Method dump skipped, instructions count: 918
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.cocktailbar.settings.CocktailOrderManager.readStateFromFileLocked(java.io.FileInputStream):void");
    }

    public final boolean writeStateToFileLocked(FileOutputStream fileOutputStream) {
        try {
            if (this.mCocktailOrderedList == null) {
                Slog.w("CocktailOrderManager", "Failed to write state: no order info");
                return false;
            }
            FastXmlSerializer fastXmlSerializer = new FastXmlSerializer();
            fastXmlSerializer.setOutput(fileOutputStream, "utf-8");
            fastXmlSerializer.startDocument((String) null, Boolean.TRUE);
            fastXmlSerializer.startTag((String) null, "cocktail_order");
            int size = this.mCocktailOrderedList.size();
            for (int i = 0; i < size; i++) {
                CocktailOrderInfo cocktailOrderInfo = (CocktailOrderInfo) this.mCocktailOrderedList.get(i);
                ComponentName componentName = cocktailOrderInfo.mComponentName;
                if (componentName != null) {
                    fastXmlSerializer.startTag((String) null, "provider");
                    fastXmlSerializer.attribute((String) null, "order", String.valueOf(cocktailOrderInfo.mOrder));
                    fastXmlSerializer.attribute((String) null, "user_id", String.valueOf(cocktailOrderInfo.mUserId));
                    fastXmlSerializer.attribute((String) null, "cocktail_id", String.valueOf(cocktailOrderInfo.mCocktailId));
                    fastXmlSerializer.attribute((String) null, "package", componentName.getPackageName());
                    fastXmlSerializer.attribute((String) null, "class_name", componentName.getClassName());
                    fastXmlSerializer.endTag((String) null, "provider");
                }
            }
            fastXmlSerializer.endTag((String) null, "cocktail_order");
            fastXmlSerializer.endDocument();
            return true;
        } catch (IOException e) {
            DeviceIdleController$$ExternalSyntheticOutline0.m("Failed to write state: ", e, "CocktailOrderManager");
            return false;
        }
    }
}
