package android.sec.clipboard.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.FileUtils;
import android.os.ParcelFileDescriptor;
import android.sec.clipboard.data.ClipboardConstants;
import android.system.ErrnoException;
import android.text.Html;
import android.util.AtomicFile;
import android.util.Base64;
import com.samsung.android.content.clipboard.data.SemClipData;
import com.samsung.android.content.clipboard.data.SemHtmlClipData;
import com.samsung.android.content.clipboard.data.SemUriClipData;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

    public boolean fileCopy(File file, File file2) throws IOException, ErrnoException {
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
    public boolean fileCopy(ParcelFileDescriptor parcelFileDescriptor, File file) throws Throwable {
        Throwable th;
        ?? r10;
        FileInputStream fileInputStream;
        FileChannel channel;
        IOException iOException;
        FileOutputStream fileOutputStream;
        FileNotFoundException fileNotFoundException;
        FileOutputStream fileOutputStream2;
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        FileChannel fileChannel = null;
        try {
            try {
                file.createNewFile();
                FileUtils.setPermissions(file.getAbsolutePath(), 509, -1, -1);
                fileInputStream = new FileInputStream(fileDescriptor);
                try {
                    FileOutputStream fileOutputStream3 = new FileOutputStream(file);
                    try {
                        FileChannel channel2 = fileInputStream.getChannel();
                        try {
                            channel = fileOutputStream3.getChannel();
                            try {
                                channel2.transferTo(0L, channel2.size(), channel);
                                if (channel2 != null) {
                                    try {
                                        channel2.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                if (channel != null) {
                                    channel.close();
                                }
                                fileInputStream.close();
                                fileOutputStream3.close();
                                parcelFileDescriptor.close();
                                return true;
                            } catch (FileNotFoundException e2) {
                                fileNotFoundException = e2;
                                fileChannel = channel2;
                                fileOutputStream2 = fileOutputStream3;
                                fileNotFoundException.printStackTrace();
                                if (fileChannel != null) {
                                    try {
                                        fileChannel.close();
                                    } catch (IOException e3) {
                                        e3.printStackTrace();
                                        return false;
                                    }
                                }
                                if (channel != null) {
                                    channel.close();
                                }
                                if (fileInputStream != null) {
                                    fileInputStream.close();
                                }
                                if (fileOutputStream2 != null) {
                                    fileOutputStream2.close();
                                }
                                parcelFileDescriptor.close();
                                return false;
                            } catch (IOException e4) {
                                iOException = e4;
                                fileChannel = channel2;
                                fileOutputStream = fileOutputStream3;
                                iOException.printStackTrace();
                                if (fileChannel != null) {
                                    try {
                                        fileChannel.close();
                                    } catch (IOException e5) {
                                        e5.printStackTrace();
                                        return false;
                                    }
                                }
                                if (channel != null) {
                                    channel.close();
                                }
                                if (fileInputStream != null) {
                                    fileInputStream.close();
                                }
                                if (fileOutputStream != null) {
                                    fileOutputStream.close();
                                }
                                parcelFileDescriptor.close();
                                return false;
                            } catch (Throwable th2) {
                                th = th2;
                                fileChannel = channel2;
                                r10 = fileOutputStream3;
                                if (fileChannel != null) {
                                    try {
                                        fileChannel.close();
                                    } catch (IOException e6) {
                                        e6.printStackTrace();
                                        throw th;
                                    }
                                }
                                if (channel != null) {
                                    channel.close();
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
                        } catch (FileNotFoundException e7) {
                            fileNotFoundException = e7;
                            channel = null;
                        } catch (IOException e8) {
                            iOException = e8;
                            channel = null;
                        } catch (Throwable th3) {
                            th = th3;
                            channel = null;
                        }
                    } catch (FileNotFoundException e9) {
                        fileNotFoundException = e9;
                        channel = null;
                        fileOutputStream2 = fileOutputStream3;
                    } catch (IOException e10) {
                        iOException = e10;
                        channel = null;
                        fileOutputStream = fileOutputStream3;
                    } catch (Throwable th4) {
                        th = th4;
                        channel = null;
                        r10 = fileOutputStream3;
                    }
                } catch (FileNotFoundException e11) {
                    fileNotFoundException = e11;
                    fileOutputStream2 = null;
                    channel = null;
                } catch (IOException e12) {
                    iOException = e12;
                    fileOutputStream = null;
                    channel = null;
                } catch (Throwable th5) {
                    th = th5;
                    r10 = 0;
                    channel = null;
                }
            } catch (Throwable th6) {
                th = th6;
                r10 = fileDescriptor;
            }
        } catch (FileNotFoundException e13) {
            fileNotFoundException = e13;
            fileOutputStream2 = null;
            fileInputStream = null;
            channel = null;
        } catch (IOException e14) {
            iOException = e14;
            fileOutputStream = null;
            fileInputStream = null;
            channel = null;
        } catch (Throwable th7) {
            th = th7;
            r10 = 0;
            fileInputStream = null;
            channel = null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:46:0x006b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x008a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:52:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean saveObjectFile(File file, Object obj) throws Throwable {
        FileOutputStream fileOutputStreamStartWrite;
        ObjectOutputStream objectOutputStream;
        Throwable th;
        IOException e;
        if (obj == null) {
            Log.secI(TAG, "obj == null");
            return false;
        }
        AtomicFile atomicFile = new AtomicFile(file);
        try {
            try {
                fileOutputStreamStartWrite = atomicFile.startWrite();
                try {
                    objectOutputStream = new ObjectOutputStream(fileOutputStreamStartWrite);
                    try {
                        try {
                            objectOutputStream.writeObject(obj);
                            atomicFile.finishWrite(fileOutputStreamStartWrite);
                            try {
                                objectOutputStream.close();
                            } catch (IOException e2) {
                                Log.secD(TAG, "close : " + e2.getMessage());
                                e2.printStackTrace();
                            }
                            return true;
                        } catch (IOException e3) {
                            e = e3;
                            Log.secD(TAG, "saveObjectFile~IOException :" + e.getMessage());
                            e.printStackTrace();
                            atomicFile.failWrite(fileOutputStreamStartWrite);
                            if (objectOutputStream != null) {
                            }
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        if (objectOutputStream != null) {
                            try {
                                objectOutputStream.close();
                            } catch (IOException e4) {
                                Log.secD(TAG, "close : " + e4.getMessage());
                                e4.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } catch (IOException e5) {
                    e = e5;
                    objectOutputStream = null;
                    e = e;
                    Log.secD(TAG, "saveObjectFile~IOException :" + e.getMessage());
                    e.printStackTrace();
                    atomicFile.failWrite(fileOutputStreamStartWrite);
                    if (objectOutputStream != null) {
                        return false;
                    }
                    try {
                        objectOutputStream.close();
                        return false;
                    } catch (IOException e6) {
                        Log.secD(TAG, "close : " + e6.getMessage());
                        e6.printStackTrace();
                        return false;
                    }
                }
            } catch (Throwable th3) {
                objectOutputStream = null;
                th = th3;
                if (objectOutputStream != null) {
                }
                throw th;
            }
        } catch (IOException e7) {
            e = e7;
            fileOutputStreamStartWrite = null;
            objectOutputStream = null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0021  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x016b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public String createThumnailFromData(Context context, SemClipData semClipData) throws IOException, ErrnoException {
        int thumbReqWidth;
        int i;
        Bitmap uriPathBitmap;
        FileOutputStream fileOutputStream;
        if (context == null) {
            Log.secD(TAG, "createThumnailFromData(): context is null!");
            return null;
        }
        int thumbReqHeigth = 384;
        try {
            thumbReqWidth = ClipboardDataBitmapUtil.getThumbReqWidth(context);
        } catch (Exception e) {
            e = e;
            thumbReqWidth = 384;
        }
        try {
            thumbReqHeigth = ClipboardDataBitmapUtil.getThumbReqHeigth(context);
        } catch (Exception e2) {
            e = e2;
            e.printStackTrace();
            if (semClipData == null) {
            }
        }
        if (semClipData == null) {
            if (semClipData.getClipType() == 4) {
                SemHtmlClipData semHtmlClipData = (SemHtmlClipData) semClipData;
                Log.secI(TAG, "Create preview image for html data in createThumnailFromData()");
                String string = "";
                try {
                    string = Html.fromHtml(Uri.decode(ClipboardProcText.getImgFileNameFromHtml(semHtmlClipData.getHtml()))).toString();
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
                if (string == null || string.length() < 1) {
                    Log.secW(TAG, "getFirstImage : FileName is empty.");
                    return null;
                }
                Log.secD(TAG, "name = " + string);
                int length = string.length();
                if (string.startsWith(PREFIX_DATA)) {
                    int iIndexOf = string.indexOf(44);
                    uriPathBitmap = (iIndexOf <= 0 || iIndexOf >= length || !string.substring(5, iIndexOf).contains(BASE_64_ENCODING)) ? null : ClipboardDataBitmapUtil.getResizeBitmap(Base64.decode(string.substring(iIndexOf + 1).getBytes(), 4), thumbReqWidth, thumbReqHeigth);
                } else {
                    int i2 = LENGTH_HTTP_URL;
                    if ((length > i2 && string.substring(0, i2).compareTo(PREFIX_HTTP_URL) == 0) || (length > (i = LENGTH_HTTPS_URL) && string.substring(0, i).compareTo(PREFIX_HTTPS_URL) == 0)) {
                        Log.secI(TAG, "downloadSimpleBitmap");
                        try {
                            Log.secD(TAG, "html : " + semHtmlClipData.getHtml());
                            uriPathBitmap = ClipboardDataBitmapUtil.downloadSimpleBitmap(string, thumbReqWidth, thumbReqHeigth);
                        } catch (Exception e4) {
                            e4.printStackTrace();
                            return null;
                        }
                    } else {
                        int i3 = LENGTH_CONTENT_URI;
                        if (length <= i3 || string.substring(0, i3).compareTo("content://") != 0) {
                            Log.secD(TAG, "invalid data");
                        } else {
                            Log.secI(TAG, "getUriPathBitmap...");
                            uriPathBitmap = ClipboardDataBitmapUtil.getUriPathBitmap(context, Uri.parse(string), thumbReqWidth, thumbReqHeigth);
                        }
                    }
                }
                if (uriPathBitmap == null) {
                    return null;
                }
                getInstance().makeDir(new File(ClipboardConstants.CLIPBOARD_ROOT_PATH_TEMP));
                String str = new File(ClipboardConstants.CLIPBOARD_ROOT_PATH_TEMP, ClipboardConstants.HTML_PREVIEW_IMAGE_NAME) + ClipboardConstants.THUMBNAIL_SUFFIX;
                try {
                    fileOutputStream = new FileOutputStream(str);
                } catch (Exception e5) {
                    e5.printStackTrace();
                }
                try {
                    uriPathBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                    fileOutputStream.close();
                    uriPathBitmap.recycle();
                    return str;
                } finally {
                }
            }
            Log.secI(TAG, "createThumnailFromData() is false because clip is not html type. clip.GetFomat() :" + semClipData.getClipType());
            return null;
        }
        Log.secI(TAG, "createThumnailFromData() is false because clip is invalid data. clip :" + semClipData);
        return null;
    }

    public boolean setFirstImagePathFromHtmlData(SemHtmlClipData semHtmlClipData) {
        int i;
        if (semHtmlClipData == null) {
            return false;
        }
        String string = "";
        try {
            string = Html.fromHtml(Uri.decode(ClipboardProcText.getImgFileNameFromHtml(semHtmlClipData.getHtml().toString()))).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (string == null || string.length() < 1) {
            Log.secW(TAG, "getFirstImage : FileName is empty.");
            return true;
        }
        Log.secD(TAG, "name = " + string);
        int length = string.length();
        if (string.startsWith(PREFIX_DATA)) {
            int iIndexOf = string.indexOf(44);
            if (iIndexOf <= 0 || iIndexOf >= length || !string.substring(5, iIndexOf).contains(BASE_64_ENCODING)) {
                return false;
            }
            semHtmlClipData.setThumbnailImagePath(string);
            return true;
        }
        if (string.length() > 7 && string.substring(0, 7).compareTo(PREFIX_FILE) == 0) {
            String strSubstring = string.substring(7, string.length());
            semHtmlClipData.setThumbnailImagePath(strSubstring);
            Log.secI(TAG, "setFirstImagePathFromData: Substring Filepath  - " + strSubstring);
            return true;
        }
        if (string.contains(PREFIX_STORAGE)) {
            semHtmlClipData.setThumbnailImagePath(string);
            Log.secI(TAG, "directly use firstImagePath...getFilePathBitmap : Substring Filepath  - " + string);
            return true;
        }
        int i2 = LENGTH_HTTP_URL;
        if ((length > i2 && string.substring(0, i2).compareTo(PREFIX_HTTP_URL) == 0) || (length > (i = LENGTH_HTTPS_URL) && string.substring(0, i).compareTo(PREFIX_HTTPS_URL) == 0)) {
            semHtmlClipData.setThumbnailImagePath(null);
            return true;
        }
        int i3 = LENGTH_CONTENT_URI;
        if (length <= i3 || string.substring(0, i3).compareTo("content://") != 0) {
            return false;
        }
        semHtmlClipData.setThumbnailImagePath(null);
        return true;
    }

    public boolean setThumbnailImagePathFromUriData(SemUriClipData semUriClipData) {
        if (semUriClipData == null) {
            return false;
        }
        String string = semUriClipData.getUri().toString();
        if (string == null || string.length() < 1) {
            Log.secW(TAG, "getThumbnailImage : FileName is empty.");
            return true;
        }
        Log.secD(TAG, "name = " + string);
        int length = string.length();
        if (string.startsWith(PREFIX_DATA)) {
            int iIndexOf = string.indexOf(44);
            if (iIndexOf <= 0 || iIndexOf >= length || !string.substring(5, iIndexOf).contains(BASE_64_ENCODING)) {
                return false;
            }
            semUriClipData.setThumbnailPath(string);
            return true;
        }
        if (string.length() > 7 && string.substring(0, 7).compareTo(PREFIX_FILE) == 0) {
            String strSubstring = string.substring(7, string.length());
            semUriClipData.setThumbnailPath(strSubstring);
            Log.secI(TAG, "setThumbnailPathFromData: Substring Filepath  - " + strSubstring);
            return true;
        }
        if (string.contains(PREFIX_STORAGE)) {
            semUriClipData.setThumbnailPath(string);
            Log.secI(TAG, "directly use ThumbnailPath...getFilePathBitmap : Substring Filepath  - " + string);
            return true;
        }
        int i = LENGTH_CONTENT_URI;
        if (length <= i || string.substring(0, i).compareTo("content://") != 0) {
            return false;
        }
        semUriClipData.setThumbnailPath(null);
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x001f  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x012a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public String createThumnailFromUriData(Context context, SemUriClipData semUriClipData) throws IOException, ErrnoException {
        int thumbReqWidth;
        Bitmap filePathBitmap;
        FileOutputStream fileOutputStream;
        String str = null;
        if (context == null) {
            Log.secD(TAG, "createThumnailFromUriData(): context is null!");
            return null;
        }
        int thumbReqHeigth = 384;
        try {
            thumbReqWidth = ClipboardDataBitmapUtil.getThumbReqWidth(context);
        } catch (Exception e) {
            e = e;
            thumbReqWidth = 384;
        }
        try {
            thumbReqHeigth = ClipboardDataBitmapUtil.getThumbReqHeigth(context);
        } catch (Exception e2) {
            e = e2;
            e.printStackTrace();
            if (semUriClipData == null) {
            }
        }
        if (semUriClipData == null) {
            if (semUriClipData.getClipType() == 16) {
                Log.secI(TAG, "Create preview image for uri data in createThumnailFromData()");
                String string = semUriClipData.getUri().toString();
                if (string == null || string.length() < 1) {
                    Log.secW(TAG, "getFirstImage : FileName is empty.");
                    return null;
                }
                int length = string.length();
                Log.secD(TAG, "name = " + string);
                if (string.startsWith(PREFIX_DATA)) {
                    int iIndexOf = string.indexOf(44);
                    filePathBitmap = (iIndexOf <= 0 || iIndexOf >= length || !string.substring(5, iIndexOf).contains(BASE_64_ENCODING)) ? null : ClipboardDataBitmapUtil.getResizeBitmap(Base64.decode(string.substring(iIndexOf + 1).getBytes(), 4), thumbReqWidth, thumbReqHeigth);
                } else if (string.startsWith(PREFIX_STORAGE) || string.startsWith(PREFIX_FILE)) {
                    filePathBitmap = ClipboardDataBitmapUtil.getFilePathBitmap(string, thumbReqWidth, thumbReqHeigth);
                } else {
                    int i = LENGTH_CONTENT_URI;
                    if (length <= i || string.substring(0, i).compareTo("content://") != 0) {
                        Log.secD(TAG, "invalid data");
                    } else {
                        Log.secI(TAG, "getUriPathBitmap...");
                        filePathBitmap = ClipboardDataBitmapUtil.getUriPathBitmap(context, Uri.parse(string), thumbReqWidth, thumbReqHeigth);
                    }
                }
                if (filePathBitmap != null) {
                    getInstance().makeDir(new File(ClipboardConstants.CLIPBOARD_ROOT_PATH_TEMP));
                    str = new File(ClipboardConstants.CLIPBOARD_ROOT_PATH_TEMP, ClipboardConstants.HTML_PREVIEW_IMAGE_NAME) + ClipboardConstants.THUMBNAIL_SUFFIX;
                    try {
                        fileOutputStream = new FileOutputStream(str);
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                    try {
                        filePathBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                        fileOutputStream.close();
                        filePathBitmap.recycle();
                    } finally {
                    }
                }
                return str;
            }
            Log.secI(TAG, "createThumnailFromData() is false because clip is not uri type. clip.GetFomat() :" + semUriClipData.getClipType());
            return null;
        }
        Log.secI(TAG, "createThumnailFromData() is false because clip is invalid data. clip :" + semUriClipData);
        return null;
    }

    public Object loadObjectFile(File file) throws IOException {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                try {
                    Object object = objectInputStream.readObject();
                    objectInputStream.close();
                    fileInputStream.close();
                    return object;
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

    public void makeDir(File file) throws ErrnoException {
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
        File[] fileArrListFiles;
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (!file.isDirectory() || (fileArrListFiles = file.listFiles()) == null) {
            return;
        }
        for (File file2 : fileArrListFiles) {
            delete(file2);
        }
        file.delete();
    }

    public File getNullFile() {
        return this.NullFile;
    }
}
