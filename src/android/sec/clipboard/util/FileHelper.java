package android.sec.clipboard.util;

import android.net.Uri;
import android.os.FileUtils;
import android.os.ParcelFileDescriptor;
import android.sec.clipboard.data.ClipboardConstants;
import android.text.Html;
import com.samsung.android.content.clipboard.data.SemHtmlClipData;
import com.samsung.android.content.clipboard.data.SemUriClipData;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.channels.FileChannel;

/* loaded from: classes3.dex */
public class FileHelper {
    private static final String BASE_64_ENCODING = ";base64";
    private static final String PREFIX_CONTENT_URI = "content://";
    private static final String PREFIX_DATA = "data:";
    private static final String PREFIX_FILE = "file://";
    private static final String PREFIX_HTTPS_URL = "https://";
    private static final String PREFIX_HTTP_URL = "http://";
    private static final String PREFIX_STORAGE = "storage/emulated/";
    private static final String TAG = "FileHelper";
    private File NullFile = new File("_TEMP_FILE");
    private String loadMessage = "load success";
    private static FileHelper instance = new FileHelper();
    private static final int LENGTH_HTTP_URL = 7;
    private static final int LENGTH_HTTPS_URL = 8;
    private static final int LENGTH_CONTENT_URI = 10;

    public static FileHelper getInstance() {
        return instance;
    }

    public boolean fileCopy(File file, File file2) {
        FileInputStream fileInputStream;
        FileOutputStream fileOutputStream;
        try {
            file2.createNewFile();
            FileUtils.setPermissions(file2.getAbsolutePath(), 509, -1, -1);
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e2) {
            e2.printStackTrace();
            fileInputStream = null;
        }
        FileInputStream fileInputStream2 = fileInputStream;
        try {
            fileOutputStream = new FileOutputStream(file2);
        } catch (FileNotFoundException e3) {
            e3.printStackTrace();
            if (fileInputStream2 != null) {
                try {
                    fileInputStream2.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
        }
        if (fileInputStream2 == null) {
            if (ClipboardConstants.DEBUG) {
                Log.e(TAG, "break fileCopy()...because of inputStream :" + fileInputStream2 + ", or outputStream :" + fileOutputStream);
            }
            try {
                fileOutputStream.close();
                if (fileInputStream2 != null) {
                    fileInputStream2.close();
                }
            } catch (IOException e5) {
                e5.printStackTrace();
            }
            return false;
        }
        FileChannel channel = fileInputStream2.getChannel();
        FileChannel channel2 = fileOutputStream.getChannel();
        try {
            if (channel != null && channel2 != null) {
                try {
                    channel.transferTo(0L, channel.size(), channel2);
                    channel.close();
                } catch (IOException e6) {
                    e6.printStackTrace();
                    if (channel != null) {
                        try {
                            channel.close();
                        } catch (IOException e7) {
                            e7.printStackTrace();
                            return false;
                        }
                    }
                    if (channel2 != null) {
                        channel2.close();
                    }
                    if (fileInputStream2 != null) {
                        fileInputStream2.close();
                    }
                    fileOutputStream.close();
                    return false;
                }
            }
            if (channel2 != null) {
                channel2.close();
            }
            fileOutputStream.close();
            if (fileInputStream2 != null) {
                fileInputStream2.close();
            }
            if (channel != null) {
                try {
                    channel.close();
                } catch (IOException e8) {
                    e8.printStackTrace();
                }
            }
            if (channel2 != null) {
                channel2.close();
            }
            if (fileInputStream2 != null) {
                fileInputStream2.close();
            }
            fileOutputStream.close();
            return true;
        } finally {
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v11 */
    /* JADX WARN: Type inference failed for: r10v19 */
    /* JADX WARN: Type inference failed for: r10v2 */
    /* JADX WARN: Type inference failed for: r10v20 */
    /* JADX WARN: Type inference failed for: r10v21 */
    /* JADX WARN: Type inference failed for: r10v5, types: [java.io.FileOutputStream] */
    public boolean fileCopy(ParcelFileDescriptor parcelFileDescriptor, File file) {
        Throwable th;
        ?? r10;
        FileInputStream fileInputStream;
        FileChannel fileChannel;
        IOException iOException;
        FileOutputStream fileOutputStream;
        FileNotFoundException fileNotFoundException;
        FileOutputStream fileOutputStream2;
        FileOutputStream fileOutputStream3;
        FileChannel channel;
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        FileChannel fileChannel2 = null;
        try {
            try {
                file.createNewFile();
                FileUtils.setPermissions(file.getAbsolutePath(), 509, -1, -1);
                fileInputStream = new FileInputStream(fileDescriptor);
                try {
                    fileOutputStream3 = new FileOutputStream(file);
                    try {
                        channel = fileInputStream.getChannel();
                        try {
                            fileChannel = fileOutputStream3.getChannel();
                        } catch (FileNotFoundException e) {
                            fileNotFoundException = e;
                            fileChannel = null;
                        } catch (IOException e2) {
                            iOException = e2;
                            fileChannel = null;
                        } catch (Throwable th2) {
                            th = th2;
                            fileChannel = null;
                        }
                    } catch (FileNotFoundException e3) {
                        fileNotFoundException = e3;
                        fileChannel = null;
                        fileOutputStream2 = fileOutputStream3;
                    } catch (IOException e4) {
                        iOException = e4;
                        fileChannel = null;
                        fileOutputStream = fileOutputStream3;
                    } catch (Throwable th3) {
                        th = th3;
                        fileChannel = null;
                        r10 = fileOutputStream3;
                    }
                } catch (FileNotFoundException e5) {
                    fileNotFoundException = e5;
                    fileOutputStream2 = null;
                    fileChannel = null;
                } catch (IOException e6) {
                    iOException = e6;
                    fileOutputStream = null;
                    fileChannel = null;
                } catch (Throwable th4) {
                    th = th4;
                    r10 = 0;
                    fileChannel = null;
                }
            } catch (FileNotFoundException e7) {
                fileNotFoundException = e7;
                fileOutputStream2 = null;
                fileInputStream = null;
                fileChannel = null;
            } catch (IOException e8) {
                iOException = e8;
                fileOutputStream = null;
                fileInputStream = null;
                fileChannel = null;
            } catch (Throwable th5) {
                th = th5;
                r10 = 0;
                fileInputStream = null;
                fileChannel = null;
            }
            try {
                channel.transferTo(0L, channel.size(), fileChannel);
                if (channel != null) {
                    try {
                        channel.close();
                    } catch (IOException e9) {
                        e9.printStackTrace();
                    }
                }
                if (fileChannel != null) {
                    fileChannel.close();
                }
                fileInputStream.close();
                fileOutputStream3.close();
                parcelFileDescriptor.close();
                return true;
            } catch (FileNotFoundException e10) {
                fileNotFoundException = e10;
                fileChannel2 = channel;
                fileOutputStream2 = fileOutputStream3;
                fileNotFoundException.printStackTrace();
                if (fileChannel2 != null) {
                    try {
                        fileChannel2.close();
                    } catch (IOException e11) {
                        e11.printStackTrace();
                        return false;
                    }
                }
                if (fileChannel != null) {
                    fileChannel.close();
                }
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                if (fileOutputStream2 != null) {
                    fileOutputStream2.close();
                }
                parcelFileDescriptor.close();
                return false;
            } catch (IOException e12) {
                iOException = e12;
                fileChannel2 = channel;
                fileOutputStream = fileOutputStream3;
                iOException.printStackTrace();
                if (fileChannel2 != null) {
                    try {
                        fileChannel2.close();
                    } catch (IOException e13) {
                        e13.printStackTrace();
                        return false;
                    }
                }
                if (fileChannel != null) {
                    fileChannel.close();
                }
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                parcelFileDescriptor.close();
                return false;
            } catch (Throwable th6) {
                th = th6;
                fileChannel2 = channel;
                r10 = fileOutputStream3;
                if (fileChannel2 != null) {
                    try {
                        fileChannel2.close();
                    } catch (IOException e14) {
                        e14.printStackTrace();
                        throw th;
                    }
                }
                if (fileChannel != null) {
                    fileChannel.close();
                }
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                if (r10 != 0) {
                    r10.close();
                }
                parcelFileDescriptor.close();
                throw th;
            }
        } catch (Throwable th7) {
            th = th7;
            r10 = fileDescriptor;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x006b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:31:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x008a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean saveObjectFile(java.io.File r7, java.lang.Object r8) {
        /*
            r6 = this;
            java.lang.String r6 = "close : "
            java.lang.String r0 = "saveObjectFile~IOException :"
            r1 = 0
            java.lang.String r2 = "FileHelper"
            if (r8 != 0) goto L11
            java.lang.String r6 = "obj == null"
            android.sec.clipboard.util.Log.secI(r2, r6)
            return r1
        L11:
            android.util.AtomicFile r3 = new android.util.AtomicFile
            r3.<init>(r7)
            r7 = 0
            java.io.FileOutputStream r4 = r3.startWrite()     // Catch: java.lang.Throwable -> L48 java.io.IOException -> L4c
            java.io.ObjectOutputStream r5 = new java.io.ObjectOutputStream     // Catch: java.io.IOException -> L45 java.lang.Throwable -> L48
            r5.<init>(r4)     // Catch: java.io.IOException -> L45 java.lang.Throwable -> L48
            r5.writeObject(r8)     // Catch: java.io.IOException -> L43 java.lang.Throwable -> L87
            r3.finishWrite(r4)     // Catch: java.io.IOException -> L43 java.lang.Throwable -> L87
            r5.close()     // Catch: java.io.IOException -> L2a
            goto L41
        L2a:
            r7 = move-exception
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>(r6)
            java.lang.String r6 = r7.getMessage()
            r8.append(r6)
            java.lang.String r6 = r8.toString()
            android.sec.clipboard.util.Log.secD(r2, r6)
            r7.printStackTrace()
        L41:
            r1 = 1
            goto L86
        L43:
            r7 = move-exception
            goto L50
        L45:
            r8 = move-exception
            r5 = r7
            goto L4f
        L48:
            r8 = move-exception
            r5 = r7
            r7 = r8
            goto L88
        L4c:
            r8 = move-exception
            r4 = r7
            r5 = r4
        L4f:
            r7 = r8
        L50:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L87
            r8.<init>(r0)     // Catch: java.lang.Throwable -> L87
            java.lang.String r0 = r7.getMessage()     // Catch: java.lang.Throwable -> L87
            r8.append(r0)     // Catch: java.lang.Throwable -> L87
            java.lang.String r8 = r8.toString()     // Catch: java.lang.Throwable -> L87
            android.sec.clipboard.util.Log.secD(r2, r8)     // Catch: java.lang.Throwable -> L87
            r7.printStackTrace()     // Catch: java.lang.Throwable -> L87
            r3.failWrite(r4)     // Catch: java.lang.Throwable -> L87
            if (r5 == 0) goto L86
            r5.close()     // Catch: java.io.IOException -> L6f
            goto L86
        L6f:
            r7 = move-exception
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>(r6)
            java.lang.String r6 = r7.getMessage()
            r8.append(r6)
            java.lang.String r6 = r8.toString()
            android.sec.clipboard.util.Log.secD(r2, r6)
            r7.printStackTrace()
        L86:
            return r1
        L87:
            r7 = move-exception
        L88:
            if (r5 == 0) goto La5
            r5.close()     // Catch: java.io.IOException -> L8e
            goto La5
        L8e:
            r8 = move-exception
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>(r6)
            java.lang.String r6 = r8.getMessage()
            r0.append(r6)
            java.lang.String r6 = r0.toString()
            android.sec.clipboard.util.Log.secD(r2, r6)
            r8.printStackTrace()
        La5:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: android.sec.clipboard.util.FileHelper.saveObjectFile(java.io.File, java.lang.Object):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0021  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x016b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String createThumnailFromData(android.content.Context r10, com.samsung.android.content.clipboard.data.SemClipData r11) {
        /*
            Method dump skipped, instructions count: 381
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.sec.clipboard.util.FileHelper.createThumnailFromData(android.content.Context, com.samsung.android.content.clipboard.data.SemClipData):java.lang.String");
    }

    public boolean setFirstImagePathFromHtmlData(SemHtmlClipData semHtmlClipData) {
        int i;
        if (semHtmlClipData == null) {
            return false;
        }
        String str = "";
        try {
            str = Html.fromHtml(Uri.decode(ClipboardProcText.getImgFileNameFromHtml(semHtmlClipData.getHtml().toString()))).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (str == null || str.length() < 1) {
            Log.secW(TAG, "getFirstImage : FileName is empty.");
            return true;
        }
        Log.secD(TAG, "name = " + str);
        int length = str.length();
        if (str.startsWith(PREFIX_DATA)) {
            int indexOf = str.indexOf(44);
            if (indexOf <= 0 || indexOf >= length || !str.substring(5, indexOf).contains(BASE_64_ENCODING)) {
                return false;
            }
            semHtmlClipData.setThumbnailImagePath(str);
            return true;
        }
        if (str.length() > 7 && str.substring(0, 7).compareTo(PREFIX_FILE) == 0) {
            String substring = str.substring(7, str.length());
            semHtmlClipData.setThumbnailImagePath(substring);
            Log.secI(TAG, "setFirstImagePathFromData: Substring Filepath  - " + substring);
            return true;
        }
        if (str.contains(PREFIX_STORAGE)) {
            semHtmlClipData.setThumbnailImagePath(str);
            Log.secI(TAG, "directly use firstImagePath...getFilePathBitmap : Substring Filepath  - " + str);
            return true;
        }
        int i2 = LENGTH_HTTP_URL;
        if ((length > i2 && str.substring(0, i2).compareTo(PREFIX_HTTP_URL) == 0) || (length > (i = LENGTH_HTTPS_URL) && str.substring(0, i).compareTo(PREFIX_HTTPS_URL) == 0)) {
            semHtmlClipData.setThumbnailImagePath(null);
            return true;
        }
        int i3 = LENGTH_CONTENT_URI;
        if (length <= i3 || str.substring(0, i3).compareTo("content://") != 0) {
            return false;
        }
        semHtmlClipData.setThumbnailImagePath(null);
        return true;
    }

    public boolean setThumbnailImagePathFromUriData(SemUriClipData semUriClipData) {
        if (semUriClipData == null) {
            return false;
        }
        String uri = semUriClipData.getUri().toString();
        if (uri == null || uri.length() < 1) {
            Log.secW(TAG, "getThumbnailImage : FileName is empty.");
            return true;
        }
        Log.secD(TAG, "name = " + uri);
        int length = uri.length();
        if (uri.startsWith(PREFIX_DATA)) {
            int indexOf = uri.indexOf(44);
            if (indexOf <= 0 || indexOf >= length || !uri.substring(5, indexOf).contains(BASE_64_ENCODING)) {
                return false;
            }
            semUriClipData.setThumbnailPath(uri);
            return true;
        }
        if (uri.length() > 7 && uri.substring(0, 7).compareTo(PREFIX_FILE) == 0) {
            String substring = uri.substring(7, uri.length());
            semUriClipData.setThumbnailPath(substring);
            Log.secI(TAG, "setThumbnailPathFromData: Substring Filepath  - " + substring);
            return true;
        }
        if (uri.contains(PREFIX_STORAGE)) {
            semUriClipData.setThumbnailPath(uri);
            Log.secI(TAG, "directly use ThumbnailPath...getFilePathBitmap : Substring Filepath  - " + uri);
            return true;
        }
        int i = LENGTH_CONTENT_URI;
        if (length <= i || uri.substring(0, i).compareTo("content://") != 0) {
            return false;
        }
        semUriClipData.setThumbnailPath(null);
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x001f  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x012a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.String createThumnailFromUriData(android.content.Context r8, com.samsung.android.content.clipboard.data.SemUriClipData r9) {
        /*
            Method dump skipped, instructions count: 316
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.sec.clipboard.util.FileHelper.createThumnailFromUriData(android.content.Context, com.samsung.android.content.clipboard.data.SemUriClipData):java.lang.String");
    }

    public Object loadObjectFile(File file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                try {
                    Object readObject = objectInputStream.readObject();
                    objectInputStream.close();
                    fileInputStream.close();
                    return readObject;
                } finally {
                }
            } catch (Throwable th) {
                try {
                    fileInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (IOException | ClassCastException | ClassNotFoundException e) {
            e.printStackTrace();
            String str = "" + e.getMessage();
            if (this.loadMessage.equals("load success")) {
                this.loadMessage = "load failed : " + str;
            } else if (!this.loadMessage.contains(str)) {
                this.loadMessage += "\n " + str;
            }
            return null;
        }
    }

    public void makeDir(File file) {
        if (file.exists()) {
            return;
        }
        file.mkdirs();
        FileUtils.setPermissions(file.getAbsolutePath(), 509, -1, -1);
    }

    public boolean checkFile(File file) {
        return file.isFile();
    }

    public File[] getList(File file) {
        return file.listFiles();
    }

    public void delete(File file) {
        File[] listFiles;
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (!file.isDirectory() || (listFiles = file.listFiles()) == null) {
            return;
        }
        for (File file2 : listFiles) {
            delete(file2);
        }
        file.delete();
    }

    public File getNullFile() {
        return this.NullFile;
    }
}
