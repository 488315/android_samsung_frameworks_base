package com.android.server.asks;

import android.os.Binder;
import android.os.SystemProperties;
import android.p005os.IInstalld;
import android.util.Slog;
import android.util.jar.StrictJarFile;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/* loaded from: classes.dex */
public class RuleUpdateForSecurity {
    public RUFSContainer mContainer;
    public String TAG = "AASA_RuleUpdateForSecurity_RUFS";
    public String mVersionFromDevice = "";
    public String device_basePath = "/data/system/.aasa";
    public String device_policyCopyPath = "";
    public String device_policyUnzipPath = "";
    public String mVersionFromToken = "";

    public final byte inverseEachBit(byte b, int i) {
        return (byte) (((byte) ((b >> i) & 1)) == 0 ? (1 << i) | b : (~(1 << i)) & b);
    }

    public RuleUpdateForSecurity(RUFSContainer rUFSContainer) {
        this.mContainer = rUFSContainer;
    }

    public boolean isUpdatePolicy(String str) {
        String policyVersion;
        RUFSContainer rUFSContainer = this.mContainer;
        boolean z = false;
        if (rUFSContainer == null) {
            return false;
        }
        if (rUFSContainer.getIsDelta()) {
            policyVersion = this.mContainer.getDeltaPolicyVersion();
        } else {
            policyVersion = this.mContainer.getPolicyVersion();
        }
        if (policyVersion == null || policyVersion.length() <= 0) {
            return false;
        }
        try {
            Slog.i(this.TAG, "token:" + policyVersion + " device:" + str);
            if (Integer.parseInt(policyVersion) <= Integer.parseInt(str)) {
                return false;
            }
            z = true;
            this.mVersionFromDevice = str;
            this.mVersionFromToken = policyVersion;
            Slog.i(this.TAG, " Now try to update");
            return true;
        } catch (NumberFormatException unused) {
            return z;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:47:0x01a2, code lost:
    
        if (r0 == null) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x019e, code lost:
    
        r0.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x019c, code lost:
    
        if (r0 == null) goto L55;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean updatePolicy(String str, boolean z) {
        StrictJarFile strictJarFile;
        ZipEntry findEntry;
        byte[] readFile;
        boolean z2 = false;
        if (!enforcePermissionCheckForASKS()) {
            Slog.e(this.TAG, "updatePolicy: enforced fail");
            return false;
        }
        if (this.mContainer != null) {
            Slog.i(this.TAG, "TRY::::::::::::::" + str);
            StrictJarFile strictJarFile2 = null;
            try {
                try {
                    strictJarFile = new StrictJarFile(str, false, true);
                } catch (IOException unused) {
                }
            } catch (IOException unused2) {
            } catch (SecurityException unused3) {
            } catch (Throwable th) {
                th = th;
            }
            try {
                if (z) {
                    findEntry = strictJarFile.findEntry("SEC-INF/targetinfo");
                    if (findEntry == null) {
                        findEntry = strictJarFile.findEntry("META-INF/SEC-INF/targetinfo");
                    }
                } else {
                    findEntry = strictJarFile.findEntry("targetinfo");
                }
                if (findEntry != null) {
                    Slog.i(this.TAG, "Target Info exists");
                    if (z && (!z || Long.compare(this.mContainer.getSizeofzip(), findEntry.getSize()) != 0)) {
                        Slog.i(this.TAG, "Fail");
                        Slog.i(this.TAG, "Ticke size " + this.mContainer.getSizeofzip());
                        Slog.i(this.TAG, "Token size " + findEntry.getSize());
                    }
                    String digest = digest(strictJarFile, findEntry);
                    Slog.i(this.TAG, "digestFromFile  :" + digest);
                    Slog.i(this.TAG, "digestFromtoken :" + this.mContainer.getDigest());
                    if ((!z || (z && digest.equals(this.mContainer.getDigest()))) && (readFile = readFile(strictJarFile, findEntry)) != null) {
                        byte[] descramble = descramble(readFile, "ASKSRUFS!!");
                        if (descramble != null) {
                            StringBuilder sb = new StringBuilder();
                            sb.append(this.device_basePath);
                            String str2 = File.separator;
                            sb.append(str2);
                            sb.append(digest);
                            sb.append(".zip");
                            this.device_policyCopyPath = sb.toString();
                            this.device_policyUnzipPath = this.device_basePath + str2 + this.mVersionFromToken;
                            writeFile(descramble, this.device_policyCopyPath);
                            unzip(this.device_policyCopyPath, this.device_policyUnzipPath);
                        }
                        if (!z || compSizeofUncompressFiles(this.device_policyUnzipPath)) {
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(this.device_basePath);
                            String str3 = File.separator;
                            sb2.append(str3);
                            sb2.append(this.mVersionFromDevice);
                            if (applyPolicies(sb2.toString(), this.device_basePath + str3 + this.mVersionFromToken)) {
                                Slog.i(this.TAG, "policy applied!");
                                z2 = true;
                            }
                        }
                    }
                } else {
                    Slog.i(this.TAG, " mTokenEntry is null plz check ");
                }
                strictJarFile.close();
            } catch (IOException unused4) {
                strictJarFile2 = strictJarFile;
            } catch (SecurityException unused5) {
                strictJarFile2 = strictJarFile;
            } catch (Throwable th2) {
                th = th2;
                strictJarFile2 = strictJarFile;
                if (strictJarFile2 != null) {
                    try {
                        strictJarFile2.close();
                    } catch (IOException unused6) {
                    }
                }
                throw th;
            }
        }
        return z2;
    }

    public final boolean copyFileUsingStream(File file, File file2) {
        FileOutputStream fileOutputStream;
        FileInputStream fileInputStream;
        FileInputStream fileInputStream2 = null;
        try {
            fileInputStream = new FileInputStream(file);
            try {
                fileOutputStream = new FileOutputStream(file2);
            } catch (IOException unused) {
                fileOutputStream = null;
            } catch (Throwable th) {
                th = th;
                fileOutputStream = null;
            }
        } catch (IOException unused2) {
            fileOutputStream = null;
        } catch (Throwable th2) {
            th = th2;
            fileOutputStream = null;
        }
        try {
            byte[] bArr = new byte[IInstalld.FLAG_USE_QUOTA];
            while (true) {
                int read = fileInputStream.read(bArr);
                if (read > 0) {
                    fileOutputStream.write(bArr, 0, read);
                } else {
                    try {
                        break;
                    } catch (IOException e) {
                        System.out.println("" + e);
                        e.printStackTrace();
                    }
                }
            }
            fileInputStream.close();
            fileOutputStream.close();
            return true;
        } catch (IOException unused3) {
            fileInputStream2 = fileInputStream;
            if (fileInputStream2 != null) {
                try {
                    fileInputStream2.close();
                } catch (IOException e2) {
                    System.out.println("" + e2);
                    e2.printStackTrace();
                    return false;
                }
            }
            if (fileOutputStream == null) {
                return false;
            }
            fileOutputStream.close();
            return false;
        } catch (Throwable th3) {
            th = th3;
            fileInputStream2 = fileInputStream;
            if (fileInputStream2 != null) {
                try {
                    fileInputStream2.close();
                } catch (IOException e3) {
                    System.out.println("" + e3);
                    e3.printStackTrace();
                    throw th;
                }
            }
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            throw th;
        }
    }

    public final boolean checkTargetModelAndCarrier(ArrayList arrayList, ArrayList arrayList2) {
        String str = SystemProperties.get("ro.product.model");
        String str2 = SystemProperties.get("ro.csc.sales_code");
        boolean z = true;
        if (!arrayList.contains("ALL") || arrayList.size() != 1 ? !arrayList.contains(str) || ((!arrayList2.contains("ALL") || arrayList2.size() != 1) && !arrayList2.contains(str2)) : (!arrayList2.contains("ALL") || arrayList2.size() != 1) && !arrayList2.contains(str2)) {
            z = false;
        }
        Slog.d(this.TAG, "checkTargetModelAndCarrier() : result = " + z);
        return z;
    }

    public final void checkTargetAndRemoveIfNot(String str, String str2, String str3, String str4) {
        ArrayList arrayList;
        if (str3 == null || str4 == null) {
            return;
        }
        String[] split = str3.split(",");
        String[] split2 = str4.split(",");
        ArrayList arrayList2 = null;
        if (split != null) {
            arrayList = new ArrayList();
            for (String str5 : split) {
                arrayList.add(str5);
            }
        } else {
            arrayList = null;
        }
        if (split2 != null) {
            arrayList2 = new ArrayList();
            for (String str6 : split2) {
                arrayList2.add(str6);
            }
        }
        if (checkTargetModelAndCarrier(arrayList, arrayList2)) {
            return;
        }
        File file = new File(str + File.separator + str2);
        if (file.exists()) {
            Slog.v(this.TAG, str2 + " is not target here");
            file.delete();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x010f, code lost:
    
        if (r1 != 3) goto L30;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean applyPolicies(String str, String str2) {
        char c;
        RUFSContainer rUFSContainer = this.mContainer;
        if (rUFSContainer != null) {
            checkTargetAndRemoveIfNot(str2, "ADP.xml", rUFSContainer.getADPModels(), this.mContainer.getADPCarriers());
            checkTargetAndRemoveIfNot(str2, "ASKSRNEW.xml", this.mContainer.getASKSRNEWModels(), this.mContainer.getASKSRNEWCarriers());
        }
        File file = new File(this.device_basePath + File.separator + "AASApolicy");
        File[] listFiles = file.listFiles();
        boolean z = true;
        if (listFiles != null) {
            for (int i = 0; i < listFiles.length; i++) {
                File file2 = new File(str2 + File.separator + listFiles[i].getName());
                if (!file2.exists() && !copyFileUsingStream(listFiles[i], file2)) {
                    c = 1;
                    break;
                }
            }
        }
        c = 0;
        if (c != 1) {
            if (file.renameTo(new File(str))) {
                Slog.i(this.TAG, " AASApolicy folder is changed into old version: " + this.mVersionFromDevice);
                File file3 = new File(str2);
                System.out.println("" + str2);
                if (file3.renameTo(new File(this.device_basePath + File.separator + "AASApolicy"))) {
                    Slog.i(this.TAG, " new policy folder is changed into AASApolicy");
                    c = 3;
                } else {
                    System.out.println("Fail changeD");
                    c = 2;
                }
            } else {
                Slog.i(this.TAG, " Fail changed into " + this.mVersionFromDevice);
            }
        }
        if (c == 2) {
            new File(str).renameTo(new File(this.device_basePath + File.separator + "AASApolicy"));
        }
        z = false;
        deleteDir(new File(str));
        deleteDir(new File(str2));
        deleteDir(new File(this.device_policyCopyPath));
        return z;
    }

    public final void deleteDir(File file) {
        if (file != null) {
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                for (File file2 : listFiles) {
                    deleteDir(file2);
                }
            }
            if (file.exists()) {
                file.delete();
            }
        }
    }

    public final byte[] descramble(byte[] bArr, String str) {
        byte[] bArr2 = new byte[bArr.length];
        for (int i = 0; i < bArr.length; i++) {
            bArr2[i] = inverseEachBit(bArr[i], (str.charAt(i % str.length()) % 2) + 1);
        }
        return bArr2;
    }

    public static void writeFile(byte[] bArr, String str) {
        File file = new File(str);
        if (file.getParentFile() != null && !file.getParentFile().exists()) {
            file.getParentFile().mkdir();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(str);
        fileOutputStream.write(bArr);
        fileOutputStream.close();
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:57:0x002d -> B:13:0x0061). Please report as a decompilation issue!!! */
    public final byte[] readFile(StrictJarFile strictJarFile, ZipEntry zipEntry) {
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
                                    if (bufferedInputStream != null) {
                                        try {
                                            bufferedInputStream.close();
                                            throw th;
                                        } catch (IOException e3) {
                                            e3.printStackTrace();
                                            throw th;
                                        }
                                    }
                                    throw th;
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
                } catch (IOException e5) {
                    e5.printStackTrace();
                }
            } catch (Exception unused4) {
                bufferedInputStream = null;
            } catch (Throwable th3) {
                th = th3;
                bufferedInputStream = null;
            }
        } catch (Exception unused5) {
        }
        return byteArrayOutputStream.toByteArray();
    }

    public final String digest(StrictJarFile strictJarFile, ZipEntry zipEntry) {
        MessageDigest messageDigest;
        InputStream inputStream = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            messageDigest = null;
        }
        if (zipEntry == null) {
            return null;
        }
        try {
            inputStream = strictJarFile.getInputStream(zipEntry);
            byte[] bArr = new byte[IInstalld.FLAG_USE_QUOTA];
            if (inputStream != null) {
                while (true) {
                    int read = inputStream.read(bArr, 0, IInstalld.FLAG_USE_QUOTA);
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
        return convertToHex(messageDigest.digest());
    }

    public final boolean unzip(String str, String str2) {
        ZipInputStream zipInputStream;
        FileInputStream fileInputStream;
        File file = new File(str2);
        if (!file.exists()) {
            file.mkdirs();
        }
        byte[] bArr = new byte[IInstalld.FLAG_USE_QUOTA];
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream(str);
            try {
                zipInputStream = new ZipInputStream(fileInputStream);
                try {
                    try {
                        ZipEntry nextEntry = zipInputStream.getNextEntry();
                        while (nextEntry != null) {
                            File file2 = new File(str2 + File.separator + nextEntry.getName());
                            new File(file2.getParent()).mkdirs();
                            FileOutputStream fileOutputStream2 = new FileOutputStream(file2);
                            while (true) {
                                try {
                                    int read = zipInputStream.read(bArr);
                                    if (read <= 0) {
                                        break;
                                    }
                                    fileOutputStream2.write(bArr, 0, read);
                                } catch (IOException e) {
                                    e = e;
                                    fileOutputStream = fileOutputStream2;
                                    e.printStackTrace();
                                    if (fileOutputStream != null) {
                                        try {
                                            fileOutputStream.close();
                                        } catch (IOException unused) {
                                        }
                                    }
                                    if (zipInputStream != null) {
                                        try {
                                            zipInputStream.closeEntry();
                                            zipInputStream.close();
                                        } catch (IOException unused2) {
                                        }
                                    }
                                    if (fileInputStream == null) {
                                        return false;
                                    }
                                    try {
                                        fileInputStream.close();
                                        return false;
                                    } catch (IOException unused3) {
                                        return false;
                                    }
                                } catch (Throwable th) {
                                    th = th;
                                    fileOutputStream = fileOutputStream2;
                                    if (fileOutputStream != null) {
                                        try {
                                            fileOutputStream.close();
                                        } catch (IOException unused4) {
                                        }
                                    }
                                    if (zipInputStream != null) {
                                        try {
                                            zipInputStream.closeEntry();
                                            zipInputStream.close();
                                        } catch (IOException unused5) {
                                        }
                                    }
                                    if (fileInputStream != null) {
                                        try {
                                            fileInputStream.close();
                                            throw th;
                                        } catch (IOException unused6) {
                                            throw th;
                                        }
                                    }
                                    throw th;
                                }
                            }
                            zipInputStream.closeEntry();
                            ZipEntry nextEntry2 = zipInputStream.getNextEntry();
                            fileOutputStream2.close();
                            nextEntry = nextEntry2;
                        }
                        try {
                            zipInputStream.closeEntry();
                            zipInputStream.close();
                        } catch (IOException unused7) {
                        }
                        try {
                            fileInputStream.close();
                        } catch (IOException unused8) {
                        }
                        return true;
                    } catch (IOException e2) {
                        e = e2;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (IOException e3) {
                e = e3;
                zipInputStream = null;
            } catch (Throwable th3) {
                th = th3;
                zipInputStream = null;
            }
        } catch (IOException e4) {
            e = e4;
            zipInputStream = null;
            fileInputStream = null;
        } catch (Throwable th4) {
            th = th4;
            zipInputStream = null;
            fileInputStream = null;
        }
    }

    public final String convertToHex(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bArr) {
            int i = (b >>> 4) & 15;
            int i2 = 0;
            while (true) {
                sb.append((char) ((i < 0 || i > 9) ? (i - 10) + 97 : i + 48));
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

    public final boolean compSizeofUncompressFiles(String str) {
        RUFSContainer rUFSContainer = this.mContainer;
        if (rUFSContainer == null) {
            return false;
        }
        try {
            long sizeofFiles = rUFSContainer.getSizeofFiles();
            long sizeofFiles2 = getSizeofFiles(str);
            if (Long.compare(sizeofFiles, sizeofFiles2) == 0) {
                return true;
            }
            Slog.i(this.TAG, "size of all files   token:" + sizeofFiles + " device:" + sizeofFiles2);
            return false;
        } catch (NumberFormatException unused) {
            return false;
        }
    }

    public final long getSizeofFiles(String str) {
        try {
            File file = new File(str);
            if (!file.isDirectory()) {
                return 0L;
            }
            long j = 0;
            for (File file2 : file.listFiles()) {
                j += file2.length();
            }
            return j;
        } catch (Exception e) {
            System.out.println("Error:" + e);
            return 0L;
        }
    }

    public final boolean enforcePermissionCheckForASKS() {
        return Binder.getCallingUid() == 1000;
    }
}
