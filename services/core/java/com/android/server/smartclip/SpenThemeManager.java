package com.android.server.smartclip;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Environment;
import android.os.FileUtils;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import android.view.PointerIcon;

import com.android.internal.content.PackageMonitor;
import com.android.internal.util.FastXmlSerializer;
import com.android.internal.util.JournaledFile;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SpenThemeManager {
    public final Context mContext;
    public final AnonymousClass1 mMonitor =
            new PackageMonitor() { // from class: com.android.server.smartclip.SpenThemeManager.1
                public final void onPackageRemoved(String str, int i) {
                    super.onPackageRemoved(str, i);
                    ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                            "package removed = ", str, "SpenThemeManager");
                    AnonymousClass2 anonymousClass2 = SpenThemeManager.this.mPackageRemovedHandler;
                    anonymousClass2.sendMessage(anonymousClass2.obtainMessage(0, str));
                }
            };
    public final AnonymousClass2 mPackageRemovedHandler =
            new Handler() { // from class: com.android.server.smartclip.SpenThemeManager.2
                @Override // android.os.Handler
                public final void handleMessage(Message message) {
                    String str = (String) message.obj;
                    if (TextUtils.isEmpty(str)) {
                        return;
                    }
                    SpenThemeManager spenThemeManager = SpenThemeManager.this;
                    spenThemeManager.getClass();
                    if ("com.samsung.android.pentastic".equals(str)) {
                        Settings.Global.putInt(
                                spenThemeManager.mContext.getContentResolver(),
                                "pen_custom_double_tap_action_enabled",
                                0);
                        Settings.Global.putString(
                                spenThemeManager.mContext.getContentResolver(),
                                "pen_custom_double_tap_action_shortcut",
                                "");
                    }
                    if (!TextUtils.isEmpty(str)
                            && str.equals(spenThemeManager.mThemeData.packageNameList[0])) {
                        spenThemeManager.setPenHoverIcon(
                                str,
                                null,
                                FullScreenMagnificationGestureHandler.MAX_SCALE,
                                FullScreenMagnificationGestureHandler.MAX_SCALE);
                    }
                    if (!TextUtils.isEmpty(str)
                            && str.equals(spenThemeManager.mThemeData.packageNameList[1])) {
                        spenThemeManager.setPenAttachSound(null, str, null);
                    }
                    if (!TextUtils.isEmpty(str)
                            && str.equals(spenThemeManager.mThemeData.packageNameList[2])) {
                        spenThemeManager.setPenDetachSound(null, str, null);
                    }
                }
            };
    public final boolean mRegistered;
    public final ThemeData mThemeData;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SoundPathInfo {
        public String attachSoundPath;
        public String detachSoundPath;
        public final AnonymousClass1 mWriteSettingHandler =
                new Handler() { // from class:
                                // com.android.server.smartclip.SpenThemeManager.SoundPathInfo.1
                    @Override // android.os.Handler
                    public final void handleMessage(Message message) {
                        Object obj = message.obj;
                        if (obj instanceof Context) {
                            Context context = (Context) obj;
                            SoundPathInfo soundPathInfo = SoundPathInfo.this;
                            if (context == null) {
                                soundPathInfo.getClass();
                                return;
                            }
                            StringBuilder m =
                                    BootReceiver$$ExternalSyntheticOutline0.m(
                                            ConnectivityModuleConnector$$ExternalSyntheticOutline0
                                                    .m$1("" + soundPathInfo.attachSoundPath, ","));
                            m.append(soundPathInfo.detachSoundPath);
                            String sb = m.toString();
                            Log.i("SpenThemeManager", "get paths = " + sb);
                            Log.i("SpenThemeManager", "write setting paths = " + sb);
                            Settings.System.putStringForUser(
                                    context.getContentResolver(),
                                    "pen_detachment_notification",
                                    sb,
                                    -2);
                        }
                    }
                };

        public final String toString() {
            return "attach sound = "
                    + this.attachSoundPath
                    + ", detach sound = "
                    + this.detachSoundPath;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ThemeData {
        public float hotspotX;
        public float hotspotY;
        public final Context mContext;
        public final SoundPathInfo mCurSoundPathInfo;
        public final SoundPathInfo mDefaultSoundPathInfo;
        public final String[] packageNameList;

        public ThemeData(Context context) {
            String[] split;
            SoundPathInfo soundPathInfo = new SoundPathInfo();
            this.mCurSoundPathInfo = soundPathInfo;
            SoundPathInfo soundPathInfo2 = new SoundPathInfo();
            this.mDefaultSoundPathInfo = soundPathInfo2;
            this.packageNameList = new String[3];
            this.hotspotX = FullScreenMagnificationGestureHandler.MAX_SCALE;
            this.hotspotY = FullScreenMagnificationGestureHandler.MAX_SCALE;
            this.mContext = context;
            if (context != null) {
                String stringForUser =
                        Settings.System.getStringForUser(
                                context.getContentResolver(), "pen_detachment_notification", -2);
                Log.i("SpenThemeManager", "read setting paths = " + stringForUser);
                Log.i("SpenThemeManager", "set paths = " + stringForUser);
                if (!TextUtils.isEmpty(stringForUser)
                        && (split = stringForUser.split(",")) != null
                        && split.length >= 2) {
                    soundPathInfo.attachSoundPath = split[0];
                    soundPathInfo.detachSoundPath = split[1];
                }
            }
            soundPathInfo2.attachSoundPath = "/system/media/audio/ui/Pen_att_noti1.ogg";
            soundPathInfo2.detachSoundPath = "/system/media/audio/ui/Pen_det_noti1.ogg";
        }

        public final JournaledFile makeJournaledFile() {
            SpenThemeManager.this.getClass();
            String absolutePath =
                    new File(SpenThemeManager.getRootDir(), "spen_theme_data_file")
                            .getAbsolutePath();
            return new JournaledFile(
                    new File(absolutePath),
                    new File(
                            ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(
                                    absolutePath, ".tmp")));
        }

        public final void parseHoverIconData(XmlPullParser xmlPullParser) {
            int next;
            do {
                try {
                    next = xmlPullParser.next();
                    if (next == 2) {
                        String name = xmlPullParser.getName();
                        xmlPullParser.next();
                        String text = xmlPullParser.getText();
                        if ("package".equals(name)) {
                            this.packageNameList[0] = text;
                        } else if ("hotspotX".equals(name)) {
                            if (text != null) {
                                this.hotspotX = Float.parseFloat(text);
                            }
                        } else if ("hotspotY".equals(name) && text != null) {
                            this.hotspotY = Float.parseFloat(text);
                        }
                    } else if (next == 3 && "hover-icon".equals(xmlPullParser.getName())) {
                        return;
                    }
                } catch (IOException | NumberFormatException | XmlPullParserException e) {
                    e.printStackTrace();
                    return;
                }
            } while (next != 1);
        }

        public final void saveElements(XmlSerializer xmlSerializer) {
            try {
                FastXmlSerializer fastXmlSerializer = (FastXmlSerializer) xmlSerializer;
                fastXmlSerializer.startTag((String) null, "hover-icon");
                fastXmlSerializer.startTag((String) null, "package");
                String str = this.packageNameList[0];
                if (str != null) {
                    fastXmlSerializer.text(str);
                }
                fastXmlSerializer.endTag((String) null, "package");
                fastXmlSerializer.startTag((String) null, "hotspotX");
                fastXmlSerializer.text(String.valueOf(this.hotspotX));
                fastXmlSerializer.endTag((String) null, "hotspotX");
                fastXmlSerializer.startTag((String) null, "hotspotY");
                fastXmlSerializer.text(String.valueOf(this.hotspotY));
                fastXmlSerializer.endTag((String) null, "hotspotY");
                fastXmlSerializer.endTag((String) null, "hover-icon");
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i("SpenThemeManager", "save = " + SpenThemeManager.this.mThemeData.toString());
        }

        public final void setAttachSoundPath(String str) {
            Log.i("SpenThemeManager", "attach sound = " + str);
            boolean isEmpty = TextUtils.isEmpty(str);
            SoundPathInfo soundPathInfo = this.mCurSoundPathInfo;
            if (isEmpty) {
                soundPathInfo.attachSoundPath = this.mDefaultSoundPathInfo.attachSoundPath;
            } else {
                soundPathInfo.attachSoundPath = str;
            }
            Context context = this.mContext;
            SoundPathInfo.AnonymousClass1 anonymousClass1 = soundPathInfo.mWriteSettingHandler;
            anonymousClass1.sendMessage(anonymousClass1.obtainMessage(0, context));
        }

        public final void setDetachSoundPath(String str) {
            Log.i("SpenThemeManager", "detach sound = " + str);
            boolean isEmpty = TextUtils.isEmpty(str);
            SoundPathInfo soundPathInfo = this.mCurSoundPathInfo;
            if (isEmpty) {
                soundPathInfo.detachSoundPath = this.mDefaultSoundPathInfo.detachSoundPath;
            } else {
                soundPathInfo.detachSoundPath = str;
            }
            Context context = this.mContext;
            SoundPathInfo.AnonymousClass1 anonymousClass1 = soundPathInfo.mWriteSettingHandler;
            anonymousClass1.sendMessage(anonymousClass1.obtainMessage(0, context));
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("\n");
            sb.append("icon package name = " + this.packageNameList[0] + "\n");
            sb.append("icon hotspotX = " + this.hotspotX + ", hotspotY = " + this.hotspotY + "\n");
            return sb.toString();
        }
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.server.smartclip.SpenThemeManager$1] */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.server.smartclip.SpenThemeManager$2] */
    public SpenThemeManager(Context context) {
        int next;
        FileDescriptor fileDescriptor;
        this.mRegistered = false;
        this.mContext = context;
        ThemeData themeData = new ThemeData(context);
        this.mThemeData = themeData;
        File chooseForRead = themeData.makeJournaledFile().chooseForRead();
        if (chooseForRead.exists()) {
            try {
                try {
                    FileInputStream fileInputStream = new FileInputStream(chooseForRead);
                    try {
                        XmlPullParser newPullParser = Xml.newPullParser();
                        newPullParser.setInput(fileInputStream, StandardCharsets.UTF_8.name());
                        do {
                            next = newPullParser.next();
                            if (next == 2 && "hover-icon".equals(newPullParser.getName())) {
                                themeData.parseHoverIconData(newPullParser);
                            }
                        } while (next != 1);
                        fileInputStream.close();
                    } catch (Throwable th) {
                        try {
                            fileInputStream.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                } catch (FileNotFoundException unused) {
                    Log.w("SpenThemeManager", "no current wallpaper -- first boot?");
                }
            } catch (IOException | XmlPullParserException e) {
                Log.w("SpenThemeManager", "failed parsing " + e);
            }
            Log.i("SpenThemeManager", "load = " + SpenThemeManager.this.mThemeData.toString());
        } else {
            Log.i("SpenThemeManager", "There's no data file to load");
        }
        try {
            fileDescriptor = new FileInputStream(getThemeFile(0, null)).getFD();
        } catch (IOException e2) {
            e2.printStackTrace();
            fileDescriptor = null;
        }
        ThemeData themeData2 = this.mThemeData;
        setPenHoverIcon(fileDescriptor, themeData2.hotspotX, themeData2.hotspotY);
        if (this.mRegistered) {
            return;
        }
        register(this.mContext, (Looper) null, true);
        this.mRegistered = true;
        Log.i("SpenThemeManager", "package monitor registered.");
    }

    public static File getRootDir() {
        File file =
                new File(
                        ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(
                                Environment.getDataDirectory().getAbsolutePath() + "/overlays",
                                "/spen_theme"));
        if (!file.exists()) {
            file.mkdir();
            FileUtils.setPermissions(file.getPath(), 505, -1, -1);
        }
        return file;
    }

    public static File getThemeFile(int i, String str) {
        String m =
                ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                        "spen_theme_pen_hover_icon", str);
        if (i == 1) {
            m =
                    ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                            "spen_theme_pen_attach_sound", str);
        } else if (i == 2) {
            m =
                    ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                            "spen_theme_pen_detach_sound", str);
        }
        return new File(getRootDir(), m);
    }

    public static void setPenHoverIcon(FileDescriptor fileDescriptor, float f, float f2) {
        PointerIcon pointerIcon = null;
        if (fileDescriptor != null) {
            Bitmap decodeFileDescriptor =
                    BitmapFactory.decodeFileDescriptor(fileDescriptor, null, null);
            Log.i("SpenThemeManager", "set icon image = " + decodeFileDescriptor);
            if (decodeFileDescriptor != null) {
                pointerIcon = PointerIcon.create(decodeFileDescriptor, f, f2);
            }
        }
        Log.i("SpenThemeManager", "set icon pointer icon = " + pointerIcon);
        PointerIcon.setDefaultPointerIconInternal(2, pointerIcon, true);
    }

    public final boolean applyChanges(
            String str, FileDescriptor fileDescriptor, int i, String str2) {
        boolean z;
        FileOutputStream fileOutputStream;
        BufferedOutputStream bufferedOutputStream;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        if (fileDescriptor == null) {
            if (str.equals(this.mThemeData.packageNameList[i])) {
                this.mThemeData.packageNameList[i] = null;
                deleteFile(i);
            }
            z = false;
        } else {
            try {
                FileOutputStream fileOutputStream2 = new FileOutputStream(getThemeFile(i, str2));
                try {
                    FileUtils.copy(fileDescriptor, fileOutputStream2.getFD());
                    fileOutputStream2.close();
                } finally {
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.mThemeData.packageNameList[i] = str;
            if (i != 0) {
                deleteFile(i);
            }
            z = true;
        }
        ThemeData themeData = this.mThemeData;
        JournaledFile makeJournaledFile = themeData.makeJournaledFile();
        try {
            fileOutputStream = new FileOutputStream(makeJournaledFile.chooseForWrite(), false);
            try {
                bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            } finally {
            }
        } catch (IOException unused) {
            makeJournaledFile.rollback();
        }
        try {
            FastXmlSerializer fastXmlSerializer = new FastXmlSerializer();
            fastXmlSerializer.setOutput(bufferedOutputStream, StandardCharsets.UTF_8.name());
            fastXmlSerializer.startDocument((String) null, Boolean.TRUE);
            themeData.saveElements(fastXmlSerializer);
            fastXmlSerializer.endDocument();
            bufferedOutputStream.flush();
            FileUtils.sync(fileOutputStream);
            makeJournaledFile.commit();
            bufferedOutputStream.close();
            fileOutputStream.close();
            return z;
        } finally {
        }
    }

    public final void deleteFile(int i) {
        File file = null;
        if (i == 0) {
            file = getThemeFile(i, null);
        } else if (i == 1) {
            file = new File(this.mThemeData.mCurSoundPathInfo.attachSoundPath);
        } else if (i == 2) {
            file = new File(this.mThemeData.mCurSoundPathInfo.detachSoundPath);
        }
        if (file == null || !file.exists()) {
            return;
        }
        file.delete();
    }

    public final void setPenAttachSound(FileDescriptor fileDescriptor, String str, String str2) {
        Log.i(
                "SpenThemeManager",
                "set attach sound package = "
                        + str
                        + ", file = "
                        + fileDescriptor
                        + ", file name = "
                        + str2);
        if (TextUtils.isEmpty(str)) {
            return;
        }
        if (!applyChanges(str, fileDescriptor, 1, str2)) {
            this.mThemeData.setAttachSoundPath(null);
            return;
        }
        File themeFile = getThemeFile(1, str2);
        if (themeFile.exists()) {
            this.mThemeData.setAttachSoundPath(themeFile.getAbsolutePath());
        } else {
            this.mThemeData.setAttachSoundPath(null);
        }
    }

    public final void setPenDetachSound(FileDescriptor fileDescriptor, String str, String str2) {
        Log.i(
                "SpenThemeManager",
                "set detach sound package = "
                        + str
                        + ", file = "
                        + fileDescriptor
                        + ", file name = "
                        + str2);
        if (TextUtils.isEmpty(str)) {
            return;
        }
        if (!applyChanges(str, fileDescriptor, 2, str2)) {
            this.mThemeData.setDetachSoundPath(null);
        } else {
            this.mThemeData.setDetachSoundPath(getThemeFile(2, str2).getAbsolutePath());
        }
    }

    public final void setPenHoverIcon(
            String str, FileDescriptor fileDescriptor, float f, float f2) {
        Log.i("SpenThemeManager", "set icon package = " + str + ", file = " + fileDescriptor);
        if (TextUtils.isEmpty(str)) {
            return;
        }
        ThemeData themeData = this.mThemeData;
        themeData.hotspotX = f;
        themeData.hotspotY = f2;
        FileDescriptor fileDescriptor2 = null;
        if (!applyChanges(str, fileDescriptor, 0, null)) {
            setPenHoverIcon(
                    null,
                    FullScreenMagnificationGestureHandler.MAX_SCALE,
                    FullScreenMagnificationGestureHandler.MAX_SCALE);
            return;
        }
        try {
            fileDescriptor2 = new FileInputStream(getThemeFile(0, null)).getFD();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setPenHoverIcon(fileDescriptor2, f, f2);
    }
}
