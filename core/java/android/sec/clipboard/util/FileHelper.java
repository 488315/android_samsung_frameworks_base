package android.sec.clipboard.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.p009os.FileUtils;
import android.p009os.ParcelFileDescriptor;
import android.sec.clipboard.data.ClipboardConstants;
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
    private static final String PREFIX_STORAGE = "storage/emulated/";
    private static final String TAG = "FileHelper";
    private File NullFile = new File("_TEMP_FILE");
    private String loadMessage = "load success";
    private static FileHelper instance = new FileHelper();
    private static final String PREFIX_HTTP_URL = "http://";
    private static final int LENGTH_HTTP_URL = PREFIX_HTTP_URL.length();
    private static final String PREFIX_HTTPS_URL = "https://";
    private static final int LENGTH_HTTPS_URL = PREFIX_HTTPS_URL.length();
    private static final int LENGTH_CONTENT_URI = "content://".length();

    public static FileHelper getInstance() {
        return instance;
    }

    public boolean fileCopy(File src, File dest) {
        boolean result = false;
        FileInputStream inputStream = null;
        try {
            dest.createNewFile();
            FileUtils.setPermissions(dest.getAbsolutePath(), 509, -1, -1);
            inputStream = new FileInputStream(src);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        try {
            FileOutputStream outputStream = new FileOutputStream(dest);
            if (inputStream == null) {
                if (ClipboardConstants.DEBUG) {
                    Log.m60e(TAG, "break fileCopy()...because of inputStream :" + inputStream + ", or outputStream :" + outputStream);
                }
                try {
                    outputStream.close();
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
                return false;
            }
            FileChannel fcin = inputStream.getChannel();
            FileChannel fcout = outputStream.getChannel();
            try {
                try {
                    if (fcin != null) {
                        try {
                            long lSize = fcin.size();
                            fcin.transferTo(0L, lSize, fcout);
                            fcin.close();
                        } catch (IOException e4) {
                            e4.printStackTrace();
                            result = false;
                            if (fcin != null) {
                                fcin.close();
                            }
                            if (fcout != null) {
                                fcout.close();
                            }
                            if (inputStream != null) {
                                inputStream.close();
                            }
                            outputStream.close();
                        }
                    }
                    if (fcout != null) {
                        fcout.close();
                    }
                    outputStream.close();
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    result = true;
                    if (fcin != null) {
                        fcin.close();
                    }
                    if (fcout != null) {
                        fcout.close();
                    }
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    outputStream.close();
                } catch (IOException e5) {
                    e5.printStackTrace();
                }
                return result;
            } catch (Throwable th) {
                if (fcin != null) {
                    try {
                        fcin.close();
                    } catch (IOException e6) {
                        e6.printStackTrace();
                        throw th;
                    }
                }
                if (fcout != null) {
                    fcout.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                outputStream.close();
                throw th;
            }
        } catch (FileNotFoundException e7) {
            e7.printStackTrace();
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
            return false;
        }
    }

    public boolean fileCopy(ParcelFileDescriptor pfd, File dest) {
        boolean result = false;
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        FileChannel fcin = null;
        FileChannel fcout = null;
        FileDescriptor fd = pfd.getFileDescriptor();
        try {
            try {
                try {
                    dest.createNewFile();
                    FileUtils.setPermissions(dest.getAbsolutePath(), 509, -1, -1);
                    inputStream = new FileInputStream(fd);
                    outputStream = new FileOutputStream(dest);
                    fcin = inputStream.getChannel();
                    fcout = outputStream.getChannel();
                    long lSize = fcin.size();
                    fcin.transferTo(0L, lSize, fcout);
                    result = true;
                    if (fcin != null) {
                        fcin.close();
                    }
                    if (fcout != null) {
                        fcout.close();
                    }
                    inputStream.close();
                    outputStream.close();
                    pfd.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e2) {
                e2.printStackTrace();
                if (fcin != null) {
                    try {
                        fcin.close();
                    } catch (IOException e3) {
                        e3.printStackTrace();
                        return false;
                    }
                }
                if (fcout != null) {
                    fcout.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                pfd.close();
                return false;
            } catch (IOException e4) {
                e4.printStackTrace();
                if (fcin != null) {
                    fcin.close();
                }
                if (fcout != null) {
                    fcout.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                pfd.close();
            }
            return result;
        } catch (Throwable th) {
            if (0 != 0) {
                try {
                    fcin.close();
                } catch (IOException e5) {
                    e5.printStackTrace();
                    throw th;
                }
            }
            if (0 != 0) {
                fcout.close();
            }
            if (0 != 0) {
                inputStream.close();
            }
            if (0 != 0) {
                outputStream.close();
            }
            pfd.close();
            throw th;
        }
    }

    public boolean saveObjectFile(File file, Object obj) {
        StringBuilder sb;
        if (obj == null) {
            Log.secI(TAG, "obj == null");
            return false;
        }
        boolean result = true;
        AtomicFile atomicFile = new AtomicFile(file);
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            try {
                fos = atomicFile.startWrite();
                oos = new ObjectOutputStream(fos);
                oos.writeObject(obj);
                atomicFile.finishWrite(fos);
            } catch (IOException e) {
                Log.secD(TAG, "saveObjectFile~IOException :" + e.getMessage());
                e.printStackTrace();
                atomicFile.failWrite(fos);
                result = false;
                if (oos != null) {
                    try {
                        oos.close();
                    } catch (IOException e2) {
                        e = e2;
                        sb = new StringBuilder();
                        Log.secD(TAG, sb.append("close : ").append(e.getMessage()).toString());
                        e.printStackTrace();
                        return result;
                    }
                }
            }
            try {
                oos.close();
            } catch (IOException e3) {
                e = e3;
                sb = new StringBuilder();
                Log.secD(TAG, sb.append("close : ").append(e.getMessage()).toString());
                e.printStackTrace();
                return result;
            }
            return result;
        } catch (Throwable th) {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e4) {
                    Log.secD(TAG, "close : " + e4.getMessage());
                    e4.printStackTrace();
                }
            }
            throw th;
        }
    }

    public String createThumnailFromData(Context context, SemClipData clip) {
        int i;
        if (context == null) {
            Log.secD(TAG, "createThumnailFromData(): context is null!");
            return null;
        }
        int thumbImageWidth = 384;
        int thumbImageHeight = 384;
        try {
            thumbImageWidth = ClipboardDataBitmapUtil.getThumbReqWidth(context);
            thumbImageHeight = ClipboardDataBitmapUtil.getThumbReqHeigth(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (clip != null) {
            if (clip.getClipType() == 4) {
                SemHtmlClipData htmlClip = (SemHtmlClipData) clip;
                Log.secI(TAG, "Create preview image for html data in createThumnailFromData()");
                Bitmap bm = null;
                String sFileName = "";
                try {
                    String sFileName2 = ClipboardProcText.getImgFileNameFromHtml(htmlClip.getHtml());
                    sFileName = Uri.decode(sFileName2);
                    sFileName = Html.fromHtml(sFileName).toString();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                if (sFileName == null || sFileName.length() < 1) {
                    Log.secW(TAG, "getFirstImage : FileName is empty.");
                    return null;
                }
                Log.secD(TAG, "name = " + sFileName);
                int length = sFileName.length();
                if (sFileName.startsWith(PREFIX_DATA)) {
                    int index = sFileName.indexOf(44);
                    if (index > 0 && index < length && sFileName.substring(PREFIX_DATA.length(), index).contains(BASE_64_ENCODING)) {
                        byte[] decodedString = sFileName.substring(index + 1).getBytes();
                        bm = ClipboardDataBitmapUtil.getResizeBitmap(Base64.decode(decodedString, 4), thumbImageWidth, thumbImageHeight);
                    }
                } else {
                    int i2 = LENGTH_HTTP_URL;
                    if ((length > i2 && sFileName.substring(0, i2).compareTo(PREFIX_HTTP_URL) == 0) || (length > (i = LENGTH_HTTPS_URL) && sFileName.substring(0, i).compareTo(PREFIX_HTTPS_URL) == 0)) {
                        Log.secI(TAG, "downloadSimpleBitmap");
                        try {
                            Log.secD(TAG, "html : " + htmlClip.getHtml());
                            bm = ClipboardDataBitmapUtil.downloadSimpleBitmap(sFileName, thumbImageWidth, thumbImageHeight);
                        } catch (Exception e3) {
                            e3.printStackTrace();
                            return null;
                        }
                    } else {
                        int i3 = LENGTH_CONTENT_URI;
                        if (length > i3 && sFileName.substring(0, i3).compareTo("content://") == 0) {
                            Log.secI(TAG, "getUriPathBitmap...");
                            Uri uri = Uri.parse(sFileName);
                            bm = ClipboardDataBitmapUtil.getUriPathBitmap(context, uri, thumbImageWidth, thumbImageHeight);
                        } else {
                            Log.secD(TAG, "invalid data");
                        }
                    }
                }
                if (bm == null) {
                    return null;
                }
                File tempFolder = new File(ClipboardConstants.CLIPBOARD_ROOT_PATH_TEMP);
                getInstance().makeDir(tempFolder);
                File createFile = new File(ClipboardConstants.CLIPBOARD_ROOT_PATH_TEMP, ClipboardConstants.HTML_PREVIEW_IMAGE_NAME);
                String thumbFullPath = createFile + ClipboardConstants.THUMBNAIL_SUFFIX;
                try {
                    FileOutputStream fos = new FileOutputStream(thumbFullPath);
                    try {
                        bm.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                        fos.close();
                    } finally {
                    }
                } catch (Exception e4) {
                    e4.printStackTrace();
                }
                bm.recycle();
                return thumbFullPath;
            }
            Log.secI(TAG, "createThumnailFromData() is false because clip is not html type. clip.GetFomat() :" + clip.getClipType());
            return null;
        }
        Log.secI(TAG, "createThumnailFromData() is false because clip is invalid data. clip :" + clip);
        return null;
    }

    public boolean setFirstImagePathFromHtmlData(SemHtmlClipData clip) {
        int i;
        if (clip == null) {
            return false;
        }
        String sFileName = "";
        try {
            String sFileName2 = ClipboardProcText.getImgFileNameFromHtml(clip.getHtml().toString());
            sFileName = Uri.decode(sFileName2);
            sFileName = Html.fromHtml(sFileName).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (sFileName == null || sFileName.length() < 1) {
            Log.secW(TAG, "getFirstImage : FileName is empty.");
            return true;
        }
        Log.secD(TAG, "name = " + sFileName);
        int length = sFileName.length();
        if (sFileName.startsWith(PREFIX_DATA)) {
            int index = sFileName.indexOf(44);
            if (index <= 0 || index >= length || !sFileName.substring(PREFIX_DATA.length(), index).contains(BASE_64_ENCODING)) {
                return false;
            }
            clip.setThumbnailImagePath(sFileName);
            return true;
        }
        if (sFileName.length() > 7 && sFileName.substring(0, 7).compareTo(PREFIX_FILE) == 0) {
            String sFileName3 = sFileName.substring(7, sFileName.length());
            clip.setThumbnailImagePath(sFileName3);
            Log.secI(TAG, "setFirstImagePathFromData: Substring Filepath  - " + sFileName3);
            return true;
        }
        if (sFileName.contains(PREFIX_STORAGE)) {
            clip.setThumbnailImagePath(sFileName);
            Log.secI(TAG, "directly use firstImagePath...getFilePathBitmap : Substring Filepath  - " + sFileName);
            return true;
        }
        int i2 = LENGTH_HTTP_URL;
        if ((length > i2 && sFileName.substring(0, i2).compareTo(PREFIX_HTTP_URL) == 0) || (length > (i = LENGTH_HTTPS_URL) && sFileName.substring(0, i).compareTo(PREFIX_HTTPS_URL) == 0)) {
            clip.setThumbnailImagePath(null);
            return true;
        }
        int i3 = LENGTH_CONTENT_URI;
        if (length <= i3 || sFileName.substring(0, i3).compareTo("content://") != 0) {
            return false;
        }
        clip.setThumbnailImagePath(null);
        return true;
    }

    public boolean setThumbnailImagePathFromUriData(SemUriClipData clip) {
        if (clip == null) {
            return false;
        }
        String sFileName = clip.getUri().toString();
        if (sFileName == null || sFileName.length() < 1) {
            Log.secW(TAG, "getThumbnailImage : FileName is empty.");
            return true;
        }
        Log.secD(TAG, "name = " + sFileName);
        int length = sFileName.length();
        if (sFileName.startsWith(PREFIX_DATA)) {
            int index = sFileName.indexOf(44);
            if (index <= 0 || index >= length || !sFileName.substring(PREFIX_DATA.length(), index).contains(BASE_64_ENCODING)) {
                return false;
            }
            clip.setThumbnailPath(sFileName);
            return true;
        }
        if (sFileName.length() > 7 && sFileName.substring(0, 7).compareTo(PREFIX_FILE) == 0) {
            String sFileName2 = sFileName.substring(7, sFileName.length());
            clip.setThumbnailPath(sFileName2);
            Log.secI(TAG, "setThumbnailPathFromData: Substring Filepath  - " + sFileName2);
            return true;
        }
        if (sFileName.contains(PREFIX_STORAGE)) {
            clip.setThumbnailPath(sFileName);
            Log.secI(TAG, "directly use ThumbnailPath...getFilePathBitmap : Substring Filepath  - " + sFileName);
            return true;
        }
        int i = LENGTH_CONTENT_URI;
        if (length <= i || sFileName.substring(0, i).compareTo("content://") != 0) {
            return false;
        }
        clip.setThumbnailPath(null);
        return true;
    }

    public String createThumnailFromUriData(Context context, SemUriClipData clip) {
        if (context == null) {
            Log.secD(TAG, "createThumnailFromUriData(): context is null!");
            return null;
        }
        int thumbImageWidth = 384;
        int thumbImageHeight = 384;
        try {
            thumbImageWidth = ClipboardDataBitmapUtil.getThumbReqWidth(context);
            thumbImageHeight = ClipboardDataBitmapUtil.getThumbReqHeigth(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (clip != null) {
            if (clip.getClipType() != 16) {
                Log.secI(TAG, "createThumnailFromData() is false because clip is not uri type. clip.GetFomat() :" + clip.getClipType());
                return null;
            }
            Log.secI(TAG, "Create preview image for uri data in createThumnailFromData()");
            Bitmap bm = null;
            String sFileName = clip.getUri().toString();
            if (sFileName == null || sFileName.length() < 1) {
                Log.secW(TAG, "getFirstImage : FileName is empty.");
                return null;
            }
            int length = sFileName.length();
            Log.secD(TAG, "name = " + sFileName);
            if (sFileName.startsWith(PREFIX_DATA)) {
                int index = sFileName.indexOf(44);
                if (index > 0 && index < length && sFileName.substring(PREFIX_DATA.length(), index).contains(BASE_64_ENCODING)) {
                    byte[] decodedString = sFileName.substring(index + 1).getBytes();
                    bm = ClipboardDataBitmapUtil.getResizeBitmap(Base64.decode(decodedString, 4), thumbImageWidth, thumbImageHeight);
                }
            } else if (sFileName.startsWith(PREFIX_STORAGE) || sFileName.startsWith(PREFIX_FILE)) {
                bm = ClipboardDataBitmapUtil.getFilePathBitmap(sFileName, thumbImageWidth, thumbImageHeight);
            } else {
                int i = LENGTH_CONTENT_URI;
                if (length > i && sFileName.substring(0, i).compareTo("content://") == 0) {
                    Log.secI(TAG, "getUriPathBitmap...");
                    Uri uri = Uri.parse(sFileName);
                    bm = ClipboardDataBitmapUtil.getUriPathBitmap(context, uri, thumbImageWidth, thumbImageHeight);
                } else {
                    Log.secD(TAG, "invalid data");
                }
            }
            if (bm == null) {
                return null;
            }
            File tempFolder = new File(ClipboardConstants.CLIPBOARD_ROOT_PATH_TEMP);
            getInstance().makeDir(tempFolder);
            File createFile = new File(ClipboardConstants.CLIPBOARD_ROOT_PATH_TEMP, ClipboardConstants.HTML_PREVIEW_IMAGE_NAME);
            String thumbFullPath = createFile + ClipboardConstants.THUMBNAIL_SUFFIX;
            try {
                FileOutputStream fos = new FileOutputStream(thumbFullPath);
                try {
                    bm.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.close();
                } finally {
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            bm.recycle();
            return thumbFullPath;
        }
        Log.secI(TAG, "createThumnailFromData() is false because clip is invalid data. clip :" + clip);
        return null;
    }

    public Object loadObjectFile(File file) {
        FileInputStream fis;
        ObjectInputStream ois;
        Object result = null;
        try {
            fis = new FileInputStream(file);
            try {
                ois = new ObjectInputStream(fis);
            } catch (Throwable th) {
                try {
                    fis.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (IOException | ClassCastException | ClassNotFoundException e) {
            e.printStackTrace();
            String errMsg = "" + e.getMessage();
            if (this.loadMessage.equals("load success")) {
                this.loadMessage = "load failed : " + errMsg;
            } else if (!this.loadMessage.contains(errMsg)) {
                this.loadMessage += "\n " + errMsg;
            }
        }
        try {
            result = ois.readObject();
            ois.close();
            fis.close();
            return result;
        } finally {
        }
    }

    public void makeDir(File file) {
        if (!file.exists()) {
            file.mkdirs();
            FileUtils.setPermissions(file.getAbsolutePath(), 509, -1, -1);
        }
    }

    public boolean checkFile(File file) {
        return file.isFile();
    }

    public File[] getList(File file) {
        return file.listFiles();
    }

    public void delete(File file) {
        File[] list;
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory() && (list = file.listFiles()) != null) {
            for (File f : list) {
                delete(f);
            }
            file.delete();
        }
    }

    public File getNullFile() {
        return this.NullFile;
    }
}
