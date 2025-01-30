package com.android.server.asks;

import android.util.Slog;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes.dex */
public class PolicyConvert {
    public Map pkgInfos = new HashMap();
    public String TAG = "AASA_PolicyConvert ";

    public boolean convert(String str) {
        Slog.i(this.TAG, "working..");
        AASA();
        m3EM();
        RestrictPackages();
        boolean makeASKSxml = makeASKSxml(this.pkgInfos, str);
        if (makeASKSxml) {
            deleteFiles();
        }
        Slog.i(this.TAG, "end");
        return makeASKSxml;
    }

    /* JADX WARN: Code restructure failed: missing block: B:33:0x010f, code lost:
    
        if (r2 == null) goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00f2, code lost:
    
        r2.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00f0, code lost:
    
        if (r2 != null) goto L47;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean makeASKSxml(Map map, String str) {
        boolean z;
        Iterator it = map.keySet().iterator();
        while (true) {
            z = false;
            z = false;
            z = false;
            if (!it.hasNext()) {
                break;
            }
            String str2 = (String) it.next();
            Slog.e(this.TAG, "" + str2);
            ArrayList arrayList = (ArrayList) map.get(str2);
            for (int i = 0; i < arrayList.size(); i++) {
                Slog.e(this.TAG, "" + ((String) arrayList.get(i)));
            }
        }
        File file = new File("/data/system/.aasa/asks.xml");
        BufferedWriter bufferedWriter = null;
        try {
            try {
                if (map.size() > 0) {
                    BufferedWriter bufferedWriter2 = new BufferedWriter(new FileWriter(file));
                    try {
                        bufferedWriter2.write("<asks version=\"" + str + "\">\n");
                        for (String str3 : map.keySet()) {
                            bufferedWriter2.write("    <package name=\"" + str3 + "\">\n");
                            ArrayList arrayList2 = (ArrayList) this.pkgInfos.get(str3);
                            for (int i2 = 0; i2 < arrayList2.size(); i2++) {
                                bufferedWriter2.write(((String) arrayList2.get(i2)) + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
                            }
                            bufferedWriter2.write("    </package>\n");
                        }
                        bufferedWriter2.write("</asks>\n");
                        z = true;
                        bufferedWriter = bufferedWriter2;
                    } catch (IOException e) {
                        e = e;
                        bufferedWriter = bufferedWriter2;
                        Slog.e(this.TAG, "ERROR::" + e);
                    } catch (Throwable th) {
                        th = th;
                        bufferedWriter = bufferedWriter2;
                        if (bufferedWriter != null) {
                            try {
                                bufferedWriter.close();
                            } catch (IOException unused) {
                            }
                        }
                        throw th;
                    }
                }
            } catch (IOException e2) {
                e = e2;
            }
            return z;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public final String parseToken(String str, String str2) {
        String[] split;
        String[] split2 = str.split(str2);
        if (split2 == null || split2.length != 2 || (split = split2[1].split("\"/>")) == null) {
            return null;
        }
        return split[0];
    }

    public final void MakeString(ArrayList arrayList, String str, String str2, Map map) {
        ArrayList arrayList2 = map.containsKey(str) ? (ArrayList) map.get(str) : new ArrayList();
        arrayList2.add("        <restrict version=\"" + str2 + "\" type=\"REVOKE\" datelimit=\"00000000\" from=\"Token\">");
        for (int i = 0; i < arrayList.size(); i++) {
            arrayList2.add("            <permission value=\"" + ((String) arrayList.get(i)) + "\"/>");
        }
        arrayList2.add("        </restrict>");
        map.put(str, arrayList2);
    }

    public final void MakeString(String str, String str2, Map map) {
        ArrayList arrayList = map.containsKey(str) ? (ArrayList) map.get(str) : new ArrayList();
        arrayList.add("        <emmode value=\"" + str2 + "\"/>");
        map.put(str, arrayList);
    }

    public final void MakeString(ArrayList arrayList, String str, String str2, String str3, Map map) {
        for (int i = 0; i < arrayList.size(); i++) {
            ArrayList arrayList2 = map.containsKey(arrayList.get(i)) ? (ArrayList) map.get(arrayList.get(i)) : new ArrayList();
            if ("DELETE".equals(str)) {
                arrayList2.add("        <delete version=\"" + str3 + "\" datelimit=\"" + str2 + "\"/>");
            } else {
                arrayList2.add("        <restrict version=\"" + str3 + "\" type=\"" + str + "\" datelimit=\"" + str2 + "\" from=\"Token\"/>");
            }
            map.put((String) arrayList.get(i), arrayList2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:66:0x00f6  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0116 A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void AASA() {
        FileReader fileReader;
        String str = "0";
        HashMap hashMap = new HashMap();
        boolean z = false;
        BufferedReader bufferedReader = null;
        try {
            try {
                File file = new File("/data/system/.aasa/AASApackages.xml");
                if (file.exists()) {
                    fileReader = new FileReader(file);
                    try {
                        try {
                            BufferedReader bufferedReader2 = new BufferedReader(fileReader);
                            String str2 = "";
                            boolean z2 = false;
                            while (true) {
                                try {
                                    String readLine = bufferedReader2.readLine();
                                    if (readLine == null) {
                                        break;
                                    }
                                    if (readLine.contains("<AASA VERSION=\"")) {
                                        String parseToken = parseToken(readLine, "<AASA VERSION=\"");
                                        int indexOf = parseToken.indexOf("\"");
                                        if (indexOf == -1) {
                                            try {
                                                bufferedReader2.close();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                            try {
                                                fileReader.close();
                                                return;
                                            } catch (IOException e2) {
                                                e2.printStackTrace();
                                                return;
                                            }
                                        }
                                        str = parseToken.substring(0, indexOf);
                                    }
                                    if (readLine.contains("<BlockBySPD>")) {
                                        z2 = true;
                                    } else if (readLine.contains("</BlockBySPD>")) {
                                        break;
                                    }
                                    if (z2) {
                                        if (readLine.contains("<PKG>")) {
                                            String parseToken2 = parseToken(readLine, "<PKG>");
                                            str2 = parseToken2.substring(0, parseToken2.indexOf("<"));
                                        }
                                        if (readLine.contains("<PM>")) {
                                            String parseToken3 = parseToken(readLine, "<PM>");
                                            int indexOf2 = parseToken3.indexOf("<");
                                            if (hashMap.containsKey(str2)) {
                                                ArrayList arrayList = (ArrayList) hashMap.get(str2);
                                                arrayList.add(parseToken3.substring(0, indexOf2));
                                                hashMap.put(str2, arrayList);
                                            } else {
                                                ArrayList arrayList2 = new ArrayList();
                                                arrayList2.add(parseToken3.substring(0, indexOf2));
                                                hashMap.put(str2, arrayList2);
                                            }
                                        }
                                    }
                                } catch (IOException e3) {
                                    e = e3;
                                    bufferedReader = bufferedReader2;
                                    z = z2;
                                    System.out.println(e);
                                    if (bufferedReader != null) {
                                        try {
                                            bufferedReader.close();
                                        } catch (IOException e4) {
                                            e4.printStackTrace();
                                        }
                                    }
                                    if (fileReader != null) {
                                        fileReader.close();
                                    }
                                    if (z) {
                                    }
                                } catch (Throwable th) {
                                    th = th;
                                    bufferedReader = bufferedReader2;
                                    if (bufferedReader != null) {
                                        try {
                                            bufferedReader.close();
                                        } catch (IOException e5) {
                                            e5.printStackTrace();
                                        }
                                    }
                                    if (fileReader == null) {
                                        throw th;
                                    }
                                    try {
                                        fileReader.close();
                                        throw th;
                                    } catch (IOException e6) {
                                        e6.printStackTrace();
                                        throw th;
                                    }
                                }
                            }
                            bufferedReader = bufferedReader2;
                            z = z2;
                        } catch (IOException e7) {
                            e = e7;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                    }
                } else {
                    fileReader = null;
                }
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e8) {
                        e8.printStackTrace();
                    }
                }
            } catch (IOException e9) {
                e9.printStackTrace();
            }
        } catch (IOException e10) {
            e = e10;
            fileReader = null;
        } catch (Throwable th3) {
            th = th3;
            fileReader = null;
        }
        if (fileReader != null) {
            fileReader.close();
        }
        if (z) {
            return;
        }
        for (String str3 : hashMap.keySet()) {
            MakeString((ArrayList) hashMap.get(str3), str3, str, this.pkgInfos);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:107:0x016b A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:133:0x017a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:140:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:141:0x0170 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0111  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void RestrictPackages() {
        FileReader fileReader;
        IOException e;
        BufferedReader bufferedReader;
        HashMap hashMap = new HashMap();
        HashMap hashMap2 = new HashMap();
        BufferedReader bufferedReader2 = null;
        boolean z = false;
        try {
            try {
                File file = new File("/data/system/.aasa/RestrictPackages.xml");
                if (file.exists()) {
                    fileReader = new FileReader(file);
                    try {
                        bufferedReader = new BufferedReader(fileReader);
                        ArrayList arrayList = null;
                        ArrayList arrayList2 = null;
                        boolean z2 = false;
                        loop0: while (true) {
                            String str = "";
                            while (true) {
                                try {
                                    try {
                                        String readLine = bufferedReader.readLine();
                                        if (readLine == null) {
                                            bufferedReader2 = bufferedReader;
                                            z = z2;
                                            break loop0;
                                        }
                                        if (readLine.contains("<DATELIMIT value=")) {
                                            String parseToken = parseToken(readLine, "<DATELIMIT value=\"");
                                            int indexOf = parseToken.indexOf("\"");
                                            if (indexOf == -1 || str.length() != 0) {
                                                break loop0;
                                            } else {
                                                str = parseToken.substring(0, indexOf);
                                            }
                                        } else if (readLine.contains("<DELETE>")) {
                                            arrayList = new ArrayList();
                                        } else if (readLine.contains("<DENY>")) {
                                            arrayList2 = new ArrayList();
                                        } else if (readLine.contains("<PKG value=")) {
                                            String parseToken2 = parseToken(readLine, "<PKG value=\"");
                                            if (arrayList != null && arrayList2 == null) {
                                                arrayList.add(parseToken2);
                                            }
                                            if (arrayList2 != null && arrayList == null) {
                                                arrayList2.add(parseToken2);
                                            }
                                        } else if (readLine.contains("</DELETE>")) {
                                            if (str.length() > 0 && arrayList != null && arrayList.size() > 0) {
                                                hashMap.put(str, arrayList);
                                            }
                                            arrayList = null;
                                        } else if (readLine.contains("</DENY>")) {
                                            if (str.length() > 0 && arrayList2 != null && arrayList2.size() > 0) {
                                                hashMap2.put(str, arrayList2);
                                            }
                                            arrayList2 = null;
                                        } else if (readLine.contains("</DATELIMIT>")) {
                                            break;
                                        }
                                    } catch (IOException e2) {
                                        e = e2;
                                        z = z2;
                                        System.out.println(e);
                                        if (bufferedReader != null) {
                                            try {
                                                bufferedReader.close();
                                            } catch (IOException e3) {
                                                e3.printStackTrace();
                                            }
                                        }
                                        if (fileReader != null) {
                                            fileReader.close();
                                        }
                                        if (z) {
                                        }
                                    }
                                } catch (Throwable th) {
                                    th = th;
                                    bufferedReader2 = bufferedReader;
                                    if (bufferedReader2 != null) {
                                        try {
                                            bufferedReader2.close();
                                        } catch (IOException e4) {
                                            e4.printStackTrace();
                                        }
                                    }
                                    if (fileReader == null) {
                                        try {
                                            fileReader.close();
                                            throw th;
                                        } catch (IOException e5) {
                                            e5.printStackTrace();
                                            throw th;
                                        }
                                    }
                                    throw th;
                                }
                            }
                            z2 = true;
                        }
                        bufferedReader2 = bufferedReader;
                    } catch (IOException e6) {
                        bufferedReader = null;
                        e = e6;
                    } catch (Throwable th2) {
                        th = th2;
                        if (bufferedReader2 != null) {
                        }
                        if (fileReader == null) {
                        }
                    }
                } else {
                    fileReader = null;
                }
                if (bufferedReader2 != null) {
                    try {
                        bufferedReader2.close();
                    } catch (IOException e7) {
                        e7.printStackTrace();
                    }
                }
            } catch (IOException e8) {
                e8.printStackTrace();
            }
        } catch (IOException e9) {
            fileReader = null;
            e = e9;
            bufferedReader = null;
        } catch (Throwable th3) {
            th = th3;
            fileReader = null;
        }
        if (fileReader != null) {
            fileReader.close();
        }
        if (z) {
            return;
        }
        if (hashMap.size() > 0) {
            for (String str2 : hashMap.keySet()) {
                MakeString((ArrayList) hashMap.get(str2), "DELETE", str2, "00000000", this.pkgInfos);
            }
        }
        if (hashMap2.size() > 0) {
            for (String str3 : hashMap2.keySet()) {
                MakeString((ArrayList) hashMap2.get(str3), "DENY", str3, "00000000", this.pkgInfos);
            }
        }
    }

    /* JADX WARN: Not initialized variable reg: 3, insn: 0x00d4: MOVE (r1 I:??[OBJECT, ARRAY]) = (r3 I:??[OBJECT, ARRAY]), block:B:97:0x00d4 */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00b2  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00d2 A[ORIG_RETURN, RETURN] */
    /* renamed from: EM */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m3EM() {
        FileReader fileReader;
        IOException e;
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2;
        HashMap hashMap = new HashMap();
        BufferedReader bufferedReader3 = null;
        boolean z = false;
        try {
            try {
                try {
                    File file = new File("/data/system/.aasa/EMSupportPackages.xml");
                    if (file.exists()) {
                        fileReader = new FileReader(file);
                        try {
                            bufferedReader = new BufferedReader(fileReader);
                            String str = "";
                            String str2 = str;
                            boolean z2 = false;
                            while (true) {
                                try {
                                    String readLine = bufferedReader.readLine();
                                    if (readLine == null) {
                                        bufferedReader3 = bufferedReader;
                                        z = z2;
                                        break;
                                    }
                                    if (readLine.contains("<package value=")) {
                                        String parseToken = parseToken(readLine, "<package value=\"");
                                        int indexOf = parseToken.indexOf("\"");
                                        if (indexOf == -1) {
                                            bufferedReader3 = bufferedReader;
                                            break;
                                        }
                                        str = parseToken.substring(0, indexOf);
                                    } else if (readLine.contains("<mode value")) {
                                        str2 = parseToken(readLine, "<mode value=\"");
                                    } else if (readLine.contains("</package>")) {
                                        if (str.length() > 0 && str2.length() > 0) {
                                            hashMap.put(str, str2);
                                            z2 = true;
                                        }
                                        str = "";
                                        str2 = str;
                                    }
                                } catch (IOException e2) {
                                    e = e2;
                                    z = z2;
                                    System.out.println(e);
                                    if (bufferedReader != null) {
                                        try {
                                            bufferedReader.close();
                                        } catch (IOException e3) {
                                            e3.printStackTrace();
                                        }
                                    }
                                    if (fileReader != null) {
                                        fileReader.close();
                                    }
                                    if (z) {
                                    }
                                }
                            }
                        } catch (IOException e4) {
                            bufferedReader = null;
                            e = e4;
                        } catch (Throwable th) {
                            th = th;
                            if (bufferedReader3 != null) {
                                try {
                                    bufferedReader3.close();
                                } catch (IOException e5) {
                                    e5.printStackTrace();
                                }
                            }
                            if (fileReader != null) {
                                try {
                                    fileReader.close();
                                    throw th;
                                } catch (IOException e6) {
                                    e6.printStackTrace();
                                    throw th;
                                }
                            }
                            throw th;
                        }
                    } else {
                        fileReader = null;
                    }
                    if (bufferedReader3 != null) {
                        try {
                            bufferedReader3.close();
                        } catch (IOException e7) {
                            e7.printStackTrace();
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    bufferedReader3 = bufferedReader2;
                }
            } catch (IOException e8) {
                fileReader = null;
                e = e8;
                bufferedReader = null;
            } catch (Throwable th3) {
                th = th3;
                fileReader = null;
            }
            if (fileReader != null) {
                fileReader.close();
            }
        } catch (IOException e9) {
            e9.printStackTrace();
        }
        if (z) {
            return;
        }
        for (String str3 : hashMap.keySet()) {
            MakeString(str3, (String) hashMap.get(str3), this.pkgInfos);
        }
    }

    public final void deleteFiles() {
        File file = new File("/data/system/.aasa/AASApolicy/AASA.xml");
        if (file.exists()) {
            file.delete();
        }
        File file2 = new File("/data/system/.aasa/AASApolicy/AASA-TEMP.xml");
        if (file2.exists()) {
            file2.delete();
        }
        File file3 = new File("/data/system/.aasa/EMSupportPackages.xml");
        if (file3.exists()) {
            file3.delete();
        }
        File file4 = new File("/data/system/.aasa/RestrictPackages.xml");
        if (file4.exists()) {
            file4.delete();
        }
        File file5 = new File("/data/system/.aasa/AASApackages.xml");
        if (file5.exists()) {
            file5.delete();
        }
    }
}
