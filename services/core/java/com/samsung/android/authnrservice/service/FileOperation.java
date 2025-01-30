package com.samsung.android.authnrservice.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* loaded from: classes2.dex */
public final class FileOperation {
    public static FileOperation mFileOperation;

    public static synchronized FileOperation getInstance() {
        FileOperation fileOperation;
        synchronized (FileOperation.class) {
            if (mFileOperation == null) {
                mFileOperation = new FileOperation();
            }
            fileOperation = mFileOperation;
        }
        return fileOperation;
    }

    public synchronized boolean writeFile(byte[] bArr, String str) {
        FileOutputStream fileOutputStream;
        AuthnrLog.m123v("FO", "writeFile");
        String str2 = "/data/.fido/" + str;
        String substring = str2.substring(0, str2.lastIndexOf("/"));
        if (new File(str2).isDirectory()) {
            AuthnrLog.m121e("FO", "invalid path");
            return false;
        }
        File file = new File(substring);
        if (!file.exists() && !file.mkdirs()) {
            AuthnrLog.m121e("FO", "mkdirs failed");
            return false;
        }
        FileOutputStream fileOutputStream2 = null;
        try {
            try {
                fileOutputStream = new FileOutputStream(str2);
            } catch (IOException e) {
                e = e;
            }
        } catch (Throwable th) {
            th = th;
        }
        try {
            fileOutputStream.write(bArr);
            fileOutputStream.flush();
            try {
                fileOutputStream.close();
            } catch (IOException e2) {
                AuthnrLog.m121e("FO", "IOE 2" + e2.getMessage());
            }
            return true;
        } catch (IOException e3) {
            e = e3;
            fileOutputStream2 = fileOutputStream;
            AuthnrLog.m121e("FO", "IOE 1" + e.getMessage());
            if (fileOutputStream2 != null) {
                try {
                    fileOutputStream2.close();
                } catch (IOException e4) {
                    AuthnrLog.m121e("FO", "IOE 2" + e4.getMessage());
                }
            }
            return false;
        } catch (Throwable th2) {
            th = th2;
            fileOutputStream2 = fileOutputStream;
            if (fileOutputStream2 != null) {
                try {
                    fileOutputStream2.close();
                } catch (IOException e5) {
                    AuthnrLog.m121e("FO", "IOE 2" + e5.getMessage());
                }
            }
            throw th;
        }
    }

    public synchronized boolean deleteFile(String str) {
        AuthnrLog.m123v("FO", "deleteFile");
        return deleteFileRec("/data/.fido/" + str);
    }

    public final synchronized boolean deleteFileRec(String str) {
        File file = new File(str);
        if (!file.exists()) {
            AuthnrLog.m124w("FO", "file not exist");
            return false;
        }
        if (file.isDirectory()) {
            str = str + "/";
        }
        String[] list = file.list();
        if (list != null && list.length > 0) {
            for (String str2 : list) {
                File file2 = new File(str + str2);
                if (file2.isDirectory()) {
                    if (!deleteFileRec(file2.getPath())) {
                        return false;
                    }
                } else {
                    if (!file2.exists()) {
                        AuthnrLog.m121e("FO", "deleting file failed : " + file2.getPath());
                        return false;
                    }
                    if (!file2.delete()) {
                        AuthnrLog.m124w("FO", "deleting file failed : " + file2.getPath());
                    }
                }
            }
        }
        return file.delete();
    }

    public List getFiles(String str, String str2) {
        AuthnrLog.m123v("FO", "getFiles");
        return getFilesRec("/data/.fido/" + str, str2);
    }

    public final List getFilesRec(String str, String str2) {
        String[] strArr;
        FileInputStream fileInputStream;
        ArrayList arrayList = new ArrayList();
        File file = new File(str);
        if (!file.exists()) {
            AuthnrLog.m124w("FO", "file not exist");
            return arrayList;
        }
        if (file.isDirectory()) {
            str = str + "/";
            strArr = file.list();
            if (strArr == null || strArr.length == 0) {
                AuthnrLog.m124w("FO", "children paths not exist");
                return arrayList;
            }
            Arrays.sort(strArr);
        } else {
            strArr = new String[]{str};
        }
        String trim = str2.trim();
        for (String str3 : strArr) {
            File file2 = new File(str + str3);
            if (file2.isDirectory()) {
                List filesRec = getFilesRec(file2.getPath(), trim);
                if (filesRec != null && filesRec.size() > 0) {
                    arrayList.addAll(filesRec);
                }
            } else if (trim != null && trim.length() != 0 && !str3.contains(trim)) {
                AuthnrLog.m120d("FO", "continue");
            } else {
                FileInputStream fileInputStream2 = null;
                try {
                    try {
                        fileInputStream = new FileInputStream(str + str3);
                    } catch (Throwable th) {
                        th = th;
                    }
                } catch (IOException e) {
                    e = e;
                }
                try {
                    File file3 = new File(str + str3);
                    int length = (int) file3.length();
                    byte[] bArr = new byte[length];
                    int read = fileInputStream.read(bArr, 0, length);
                    AuthnrLog.m120d("FO", "file name:" + file3.getPath());
                    if (read != -1) {
                        arrayList.add(Encoding$Base64.encode(bArr));
                    }
                    try {
                        fileInputStream.close();
                    } catch (IOException e2) {
                        AuthnrLog.m121e("FO", "IOE 2" + e2.getMessage());
                    }
                } catch (IOException e3) {
                    e = e3;
                    fileInputStream2 = fileInputStream;
                    AuthnrLog.m121e("FO", "IOE 1" + e.getMessage());
                    if (fileInputStream2 != null) {
                        try {
                            fileInputStream2.close();
                        } catch (IOException e4) {
                            AuthnrLog.m121e("FO", "IOE 2" + e4.getMessage());
                        }
                    }
                    return arrayList;
                } catch (Throwable th2) {
                    th = th2;
                    fileInputStream2 = fileInputStream;
                    if (fileInputStream2 != null) {
                        try {
                            fileInputStream2.close();
                        } catch (IOException e5) {
                            AuthnrLog.m121e("FO", "IOE 2" + e5.getMessage());
                        }
                    }
                    throw th;
                }
            }
        }
        return arrayList;
    }

    public String readFile(String str) {
        String str2;
        StringBuilder sb;
        FileInputStream fileInputStream;
        AuthnrLog.m123v("FO", "readFile");
        String str3 = "/data/.fido/" + str;
        File file = new File(str3);
        str2 = "";
        if (!file.exists()) {
            AuthnrLog.m124w("FO", "file not exist");
            return "";
        }
        if (file.isDirectory()) {
            AuthnrLog.m124w("FO", "file is a directory");
            return "";
        }
        FileInputStream fileInputStream2 = null;
        try {
            try {
                fileInputStream = new FileInputStream(str3);
            } catch (IOException e) {
                e = e;
            }
        } catch (Throwable th) {
            th = th;
        }
        try {
            int length = (int) file.length();
            byte[] bArr = new byte[length];
            int read = fileInputStream.read(bArr, 0, length);
            AuthnrLog.m120d("FO", "file name:" + file.getPath());
            str2 = read != -1 ? Encoding$Base64.encode(bArr) : "";
            try {
                fileInputStream.close();
            } catch (IOException e2) {
                e = e2;
                sb = new StringBuilder();
                sb.append("IOE 2");
                sb.append(e.getMessage());
                AuthnrLog.m121e("FO", sb.toString());
                return str2;
            }
        } catch (IOException e3) {
            e = e3;
            fileInputStream2 = fileInputStream;
            AuthnrLog.m121e("FO", "IOE 1" + e.getMessage());
            if (fileInputStream2 != null) {
                try {
                    fileInputStream2.close();
                } catch (IOException e4) {
                    e = e4;
                    sb = new StringBuilder();
                    sb.append("IOE 2");
                    sb.append(e.getMessage());
                    AuthnrLog.m121e("FO", sb.toString());
                    return str2;
                }
            }
            return str2;
        } catch (Throwable th2) {
            th = th2;
            fileInputStream2 = fileInputStream;
            if (fileInputStream2 != null) {
                try {
                    fileInputStream2.close();
                } catch (IOException e5) {
                    AuthnrLog.m121e("FO", "IOE 2" + e5.getMessage());
                }
            }
            throw th;
        }
        return str2;
    }

    public List getMatchedFilePaths(String str, String str2) {
        AuthnrLog.m123v("FO", "getMatchedFilePaths");
        String trim = str == null ? "" : str.trim();
        if (trim.endsWith("/")) {
            trim = trim.substring(0, trim.length() - 1);
        }
        return getFilesPaths(("/data/.fido/" + trim).trim(), (str2 != null ? str2.trim() : "").trim());
    }

    public final List getFilesPaths(String str, String str2) {
        ArrayList arrayList = new ArrayList();
        File file = new File(str);
        if (!file.exists()) {
            AuthnrLog.m124w("FO", "file not exist");
            return Collections.emptyList();
        }
        if (!file.isDirectory()) {
            if (str2.length() != 0 && !str.contains(str2)) {
                return Collections.emptyList();
            }
            String substring = str.substring(12);
            if (substring.startsWith("/")) {
                substring = substring.substring(1);
            }
            arrayList.add(substring);
            return arrayList;
        }
        String str3 = str + "/";
        String[] list = file.list();
        if (list == null || list.length == 0) {
            AuthnrLog.m124w("FO", "children paths not exist");
            return arrayList;
        }
        Arrays.sort(list);
        for (String str4 : list) {
            arrayList.addAll(getFilesPaths((str3 + str4).trim(), str2.trim()));
        }
        return arrayList;
    }
}
