package com.android.server.asks;

import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.os.SystemProperties;
import android.util.Slog;
import android.util.jar.StrictJarFile;

import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class RuleUpdateForSecurity {
    public final RUFSContainer mContainer;
    public String mVersionFromDevice = "";
    public String device_policyCopyPath = "";
    public String device_policyUnzipPath = "";
    public String mVersionFromToken = "";

    public RuleUpdateForSecurity(RUFSContainer rUFSContainer) {
        this.mContainer = rUFSContainer;
    }

    public static void checkTargetAndRemoveIfNot(
            String str, String str2, String str3, String str4) {
        if (str3 == null || str4 == null) {
            return;
        }
        String[] split = str3.split(",");
        String[] split2 = str4.split(",");
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        boolean z = false;
        if (split != null) {
            for (String str5 : split) {
                arrayList.add(str5);
            }
        }
        if (split2 != null) {
            for (String str6 : split2) {
                arrayList2.add(str6);
            }
        }
        String str7 = SystemProperties.get("ro.product.model");
        String str8 = SystemProperties.get("ro.csc.sales_code");
        if (!arrayList.contains("ALL") || arrayList.size() != 1
                ? !(!arrayList.contains(str7)
                        || ((!arrayList2.contains("ALL") || arrayList2.size() != 1)
                                && !arrayList2.contains(str8)))
                : !((!arrayList2.contains("ALL") || arrayList2.size() != 1)
                        && !arrayList2.contains(str8))) {
            z = true;
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "checkTargetModelAndCarrier() : result = ", "AASA_RuleUpdateForSecurity_RUFS", z);
        if (z) {
            return;
        }
        File file =
                new File(
                        AudioOffloadInfo$$ExternalSyntheticOutline0.m(
                                BootReceiver$$ExternalSyntheticOutline0.m(str),
                                File.separator,
                                str2));
        if (file.exists()) {
            Slog.v("AASA_RuleUpdateForSecurity_RUFS", str2.concat(" is not target here"));
            file.delete();
        }
    }

    public static void copyFile(File file, File file2) {
        if (!file2.exists() && !file2.createNewFile()) {
            Slog.e(
                    "AASA_RuleUpdateForSecurity_RUFS",
                    "Failed to create new file: " + file2.getAbsolutePath());
            throw new IOException("Failed to create new file");
        }
        FileChannel channel = new FileInputStream(file).getChannel();
        try {
            FileChannel channel2 = new FileOutputStream(file2).getChannel();
            try {
                channel2.transferFrom(channel, 0L, channel.size());
                channel2.close();
                channel.close();
            } finally {
            }
        } catch (Throwable th) {
            if (channel != null) {
                try {
                    channel.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public static boolean deleteDirectoryWithContents(File file) {
        File[] listFiles;
        if (file == null) {
            Slog.e("AASA_RuleUpdateForSecurity_RUFS", "file is null");
            return false;
        }
        if (file.isDirectory() && (listFiles = file.listFiles()) != null) {
            for (File file2 : listFiles) {
                if (!deleteDirectoryWithContents(file2)) {
                    Slog.e(
                            "AASA_RuleUpdateForSecurity_RUFS",
                            "Filed to delete: " + file2.getAbsolutePath());
                    return false;
                }
            }
        }
        return file.delete();
    }

    public static byte[] descramble(byte[] bArr) {
        byte[] bArr2 = new byte[bArr.length];
        for (int i = 0; i < bArr.length; i++) {
            int charAt = ("ASKSRUFS!!".charAt(i % 10) % 2) + 1;
            byte b = bArr[i];
            byte b2 = (byte) ((b >> charAt) & 1);
            int i2 = 1 << charAt;
            bArr2[i] = (byte) (b2 == 0 ? i2 | b : (~i2) & b);
        }
        return bArr2;
    }

    public static String digest(StrictJarFile strictJarFile, ZipEntry zipEntry) {
        MessageDigest messageDigest;
        InputStream inputStream = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            messageDigest = null;
        }
        try {
            try {
                inputStream = strictJarFile.getInputStream(zipEntry);
                byte[] bArr = new byte[4096];
                if (inputStream != null) {
                    while (true) {
                        int read = inputStream.read(bArr, 0, 4096);
                        if (read == -1) {
                            break;
                        }
                        messageDigest.update(bArr, 0, read);
                    }
                    inputStream.close();
                }
            } catch (IOException e2) {
                System.err.println(" + No IO " + e2.toString());
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (RuntimeException e3) {
                System.err.println(" + No RUN " + e3.toString());
                if (inputStream != null) {
                    inputStream.close();
                }
            }
        } catch (IOException unused) {
        }
        byte[] digest = messageDigest.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            int i = (b >>> 4) & 15;
            int i2 = 0;
            while (true) {
                sb.append((char) ((i < 0 || i > 9) ? i + 87 : i + 48));
                i = b & 15;
                int i3 = i2 + 1;
                if (i2 >= 1) {
                    break;
                }
                i2 = i3;
            }
        }
        return sb.toString();
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x0020, code lost:

       r3 = r1.group(1);
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String extractVersionFromFile(java.io.File r3) {
        /*
            java.io.BufferedReader r0 = new java.io.BufferedReader
            java.io.FileReader r1 = new java.io.FileReader
            r1.<init>(r3)
            r0.<init>(r1)
            java.lang.String r3 = "<VERSION\\s+value=\"(\\d{8})\""
            java.util.regex.Pattern r3 = java.util.regex.Pattern.compile(r3)     // Catch: java.lang.Throwable -> L26
        L10:
            java.lang.String r1 = r0.readLine()     // Catch: java.lang.Throwable -> L26
            if (r1 == 0) goto L28
            java.util.regex.Matcher r1 = r3.matcher(r1)     // Catch: java.lang.Throwable -> L26
            boolean r2 = r1.find()     // Catch: java.lang.Throwable -> L26
            if (r2 == 0) goto L10
            r3 = 1
            java.lang.String r3 = r1.group(r3)     // Catch: java.lang.Throwable -> L26
            goto L29
        L26:
            r3 = move-exception
            goto L2d
        L28:
            r3 = 0
        L29:
            r0.close()
            return r3
        L2d:
            r0.close()     // Catch: java.lang.Throwable -> L31
            goto L35
        L31:
            r0 = move-exception
            r3.addSuppressed(r0)
        L35:
            throw r3
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.asks.RuleUpdateForSecurity.extractVersionFromFile(java.io.File):java.lang.String");
    }

    public static String getScpmPolicyVersion() {
        String str = "00000000";
        try {
            FileInputStream fileInputStream = new FileInputStream("/data/system/.aasa/ASKS.zip");
            try {
                ZipInputStream zipInputStream = new ZipInputStream(fileInputStream);
                while (true) {
                    try {
                        ZipEntry nextEntry = zipInputStream.getNextEntry();
                        if (nextEntry == null) {
                            break;
                        }
                        if ("version.txt".equals(nextEntry.getName())) {
                            byte[] bArr = new byte[8];
                            byte[] bArr2 = new byte[2];
                            zipInputStream.read(bArr, 0, 8);
                            String str2 = new String(bArr);
                            try {
                                Slog.i("PackageInformation", "version : " + str2);
                                zipInputStream.read(bArr2, 0, 2);
                                String str3 = new String(bArr2);
                                if ("0x1".equals(SystemProperties.get("ro.boot.em.status"))) {
                                    Slog.d("PackageInformation", "tag : " + str3);
                                }
                                if ("_D".equals(str3)) {
                                    str = str2 + "_D";
                                } else {
                                    str = str2 + "_B";
                                }
                                Slog.i("PackageInformation", "scpm policy version : " + str);
                            } catch (Throwable th) {
                                th = th;
                                str = str2;
                                try {
                                    zipInputStream.close();
                                } catch (Throwable th2) {
                                    th.addSuppressed(th2);
                                }
                                throw th;
                            }
                        }
                        zipInputStream.closeEntry();
                    } catch (Throwable th3) {
                        th = th3;
                    }
                }
                zipInputStream.close();
                fileInputStream.close();
            } finally {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static ZipEntry getTargetInfoEntry(int i, StrictJarFile strictJarFile) {
        if (i == 1) {
            ZipEntry findEntry = strictJarFile.findEntry("SEC-INF/targetinfo");
            return findEntry == null
                    ? strictJarFile.findEntry("META-INF/SEC-INF/targetinfo")
                    : findEntry;
        }
        if (i == 2) {
            return strictJarFile.findEntry("targetinfo");
        }
        throw new IllegalStateException(
                VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Unexpected value: "));
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:58:0x0032 -> B:15:0x0062). Please report as a decompilation issue!!! */
    public static byte[] readFile(StrictJarFile strictJarFile, ZipEntry zipEntry) {
        BufferedInputStream bufferedInputStream;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[1048576];
        InputStream inputStream = null;
        try {
            try {
                try {
                    InputStream inputStream2 = strictJarFile.getInputStream(zipEntry);
                    try {
                        bufferedInputStream = new BufferedInputStream(inputStream2);
                        while (true) {
                            try {
                                int read = bufferedInputStream.read(bArr);
                                if (read < 0) {
                                    break;
                                }
                                byteArrayOutputStream.write(bArr, 0, read);
                            } catch (Exception unused) {
                                inputStream = inputStream2;
                                if (inputStream != null) {
                                    try {
                                        inputStream.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                if (bufferedInputStream != null) {
                                    bufferedInputStream.close();
                                }
                                return byteArrayOutputStream.toByteArray();
                            } catch (Throwable th) {
                                th = th;
                                inputStream = inputStream2;
                                try {
                                    if (inputStream != null) {
                                        try {
                                            inputStream.close();
                                        } catch (IOException e2) {
                                            e2.printStackTrace();
                                        }
                                    }
                                    if (bufferedInputStream == null) {
                                        throw th;
                                    }
                                    try {
                                        bufferedInputStream.close();
                                        throw th;
                                    } catch (IOException e3) {
                                        e3.printStackTrace();
                                        throw th;
                                    }
                                } catch (Exception unused2) {
                                    throw th;
                                }
                            }
                        }
                        if (inputStream2 != null) {
                            try {
                                inputStream2.close();
                            } catch (IOException e4) {
                                e4.printStackTrace();
                            }
                        }
                        bufferedInputStream.close();
                    } catch (Exception unused3) {
                        bufferedInputStream = null;
                    } catch (Throwable th2) {
                        th = th2;
                        bufferedInputStream = null;
                    }
                } catch (Exception unused4) {
                    bufferedInputStream = null;
                } catch (Throwable th3) {
                    th = th3;
                    bufferedInputStream = null;
                }
            } catch (IOException e5) {
                e5.printStackTrace();
            }
        } catch (Exception unused5) {
        }
        return byteArrayOutputStream.toByteArray();
    }

    public static void writeFile(String str, byte[] bArr) {
        File file = new File(str);
        if (file.getParentFile() != null && !file.getParentFile().exists()) {
            file.getParentFile().mkdir();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(str);
        fileOutputStream.write(bArr);
        fileOutputStream.close();
    }

    public final boolean isUpdatePolicy(String str) {
        boolean z = false;
        RUFSContainer rUFSContainer = this.mContainer;
        if (rUFSContainer != null) {
            String str2 =
                    rUFSContainer.mIsDelta
                            ? rUFSContainer.mRUFSpolicyDeltaVersion
                            : rUFSContainer.mRUFSpolicyVersion;
            if (str2 != null && str2.length() > 0) {
                try {
                    Slog.i("AASA_RuleUpdateForSecurity_RUFS", "token:" + str2 + " device:" + str);
                    if (Integer.parseInt(str2) > Integer.parseInt(str)) {
                        z = true;
                        this.mVersionFromDevice = str;
                        this.mVersionFromToken = str2;
                        Slog.i("AASA_RuleUpdateForSecurity_RUFS", " Now try to update");
                    } else {
                        Slog.i(
                                "AASA_RuleUpdateForSecurity_RUFS",
                                "Current policy is latest version.");
                    }
                } catch (NumberFormatException unused) {
                    Slog.e("AASA_RuleUpdateForSecurity_RUFS", "The version format is wrong !!");
                }
            }
        }
        return z;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(13:17|18|19|20|21|(1:22)|(8:24|25|(2:27|28)(1:(3:228|229|(2:231|(1:233))(1:235))(1:236))|29|30|(28:32|(1:34)|35|36|37|38|39|40|41|(3:43|(3:44|45|(4:47|48|49|51))|194)|201|202|203|204|161|162|59|(2:61|(9:63|64|65|66|67|68|(4:72|(3:75|76|73)|77|78)|80|(1:82)(2:84|85)))|92|(3:94|(3:97|(3:99|(2:101|(2:103|104)(2:106|107))(3:108|109|111)|105)(3:134|(4:136|137|138|(4:141|142|143|(2:145|146)(1:147)))(1:158)|105)|95)|159)|160|114|(2:116|(3:118|(1:120)(1:122)|121)(1:123))|124|(1:(3:127|128|(2:130|131)))(1:133)|132|128|(0))(1:223)|90|91)(1:237)|234|29|30|(0)(0)|90|91) */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x0223, code lost:

       if (r7 == null) goto L85;
    */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x028f, code lost:

       if (r3 == false) goto L191;
    */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:116:0x036d  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x03d8  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x0412  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x03dd  */
    /* JADX WARN: Removed duplicated region for block: B:165:0x021d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:169:0x0218 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:178:0x0430 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:184:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:185:0x0428 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:189:0x0423 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:223:0x0434  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0158  */
    /* JADX WARN: Type inference failed for: r0v18 */
    /* JADX WARN: Type inference failed for: r0v30 */
    /* JADX WARN: Type inference failed for: r0v87 */
    /* JADX WARN: Type inference failed for: r0v88 */
    /* JADX WARN: Type inference failed for: r7v14 */
    /* JADX WARN: Type inference failed for: r7v15, types: [int] */
    /* JADX WARN: Type inference failed for: r7v24 */
    /* JADX WARN: Type inference failed for: r9v26 */
    /* JADX WARN: Type inference failed for: r9v27, types: [int] */
    /* JADX WARN: Type inference failed for: r9v33 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean updatePolicy(int r20, java.lang.String r21) {
        /*
            Method dump skipped, instructions count: 1079
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.asks.RuleUpdateForSecurity.updatePolicy(int,"
                    + " java.lang.String):boolean");
    }
}
