package com.android.server.smartclip;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Environment;
import android.os.FileUtils;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import android.view.PointerIcon;
import com.android.internal.content.PackageMonitor;
import com.android.internal.util.FastXmlSerializer;
import com.android.internal.util.JournaledFile;
import com.android.server.display.DisplayPowerController2;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import libcore.io.IoUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* loaded from: classes3.dex */
public class SpenThemeManager {
    public static final String TAG = "SpenThemeManager";
    public Context mContext;
    public ThemeData mThemeData;
    public boolean mRegistered = false;
    public PackageMonitor mMonitor = new PackageMonitor() { // from class: com.android.server.smartclip.SpenThemeManager.1
        public void onPackageRemoved(String str, int i) {
            super.onPackageRemoved(str, i);
            Log.i(SpenThemeManager.TAG, "package removed = " + str);
            SpenThemeManager.this.mPackageRemovedHandler.sendMessage(SpenThemeManager.this.mPackageRemovedHandler.obtainMessage(0, str));
        }
    };
    public final Handler mPackageRemovedHandler = new Handler() { // from class: com.android.server.smartclip.SpenThemeManager.2
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            String str = (String) message.obj;
            if (TextUtils.isEmpty(str)) {
                return;
            }
            SpenThemeManager.this.clearCustomDoubleTapAction(str);
            SpenThemeManager.this.clearPenHoverIcon(str);
            SpenThemeManager.this.clearPenAttachSound(str);
            SpenThemeManager.this.clearPenDetachSound(str);
        }
    };

    public SpenThemeManager(Context context) {
        this.mContext = context;
        init();
    }

    public final void init() {
        ThemeData themeData = new ThemeData(this.mContext);
        this.mThemeData = themeData;
        themeData.loadData();
        loadAndSetThemeFiles();
        registerPackageMonitorIfNeeded();
    }

    public final File getRootDir() {
        File file = new File((Environment.getDataDirectory().getAbsolutePath() + "/overlays") + "/spen_theme");
        if (!file.exists()) {
            file.mkdir();
            FileUtils.setPermissions(file.getPath(), 505, -1, -1);
        }
        return file;
    }

    public final File getThemeFile(int i, String str) {
        String str2 = "spen_theme_pen_hover_icon" + str;
        if (i == 1) {
            str2 = "spen_theme_pen_attach_sound" + str;
        } else if (i == 2) {
            str2 = "spen_theme_pen_detach_sound" + str;
        }
        return new File(getRootDir(), str2);
    }

    public final FileDescriptor getThemeFileInputDescriptor(int i, String str) {
        try {
            return new FileInputStream(getThemeFile(i, str)).getFD();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0030 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void saveFile(FileDescriptor fileDescriptor, File file) {
        FileOutputStream fileOutputStream;
        Throwable th;
        IOException e;
        if (fileDescriptor == null || file == null) {
            return;
        }
        try {
            try {
                fileOutputStream = new FileOutputStream(file);
                try {
                    try {
                        FileUtils.copy(fileDescriptor, fileOutputStream.getFD());
                        fileOutputStream.close();
                    } catch (IOException e2) {
                        e = e2;
                        e.printStackTrace();
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (IOException e4) {
                e4.printStackTrace();
            }
        } catch (IOException e5) {
            fileOutputStream = null;
            e = e5;
        } catch (Throwable th3) {
            fileOutputStream = null;
            th = th3;
            if (fileOutputStream != null) {
            }
            throw th;
        }
    }

    public final boolean deleteFile(int i) {
        File file = null;
        if (i == 0) {
            file = getThemeFile(i, null);
        } else if (i == 1) {
            file = new File(this.mThemeData.getAttachSoundPath());
        } else if (i == 2) {
            file = new File(this.mThemeData.getDetachSoundPath());
        }
        return deleteFile(file);
    }

    public final boolean deleteFile(File file) {
        if (file == null || !file.exists()) {
            return false;
        }
        return file.delete();
    }

    public final void loadAndSetThemeFiles() {
        FileDescriptor themeFileInputDescriptor = getThemeFileInputDescriptor(0, null);
        ThemeData themeData = this.mThemeData;
        setPenHoverIcon(themeFileInputDescriptor, themeData.hotspotX, themeData.hotspotY);
    }

    public final void registerPackageMonitorIfNeeded() {
        if (this.mRegistered) {
            return;
        }
        this.mMonitor.register(this.mContext, (Looper) null, true);
        this.mRegistered = true;
        Log.i(TAG, "package monitor registered.");
    }

    public final boolean applyChanges(String str, FileDescriptor fileDescriptor, int i, String str2) {
        boolean z = false;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        if (fileDescriptor == null) {
            if (str.equals(this.mThemeData.packageNameList[i])) {
                this.mThemeData.packageNameList[i] = null;
                deleteFile(i);
            }
        } else {
            saveFile(fileDescriptor, getThemeFile(i, str2));
            this.mThemeData.packageNameList[i] = str;
            if (i != 0) {
                deleteFile(i);
            }
            z = true;
        }
        this.mThemeData.saveData();
        return z;
    }

    public void setPenHoverIcon(String str, FileDescriptor fileDescriptor, float f, float f2) {
        Log.i(TAG, "set icon package = " + str + ", file = " + fileDescriptor);
        if (TextUtils.isEmpty(str)) {
            return;
        }
        ThemeData themeData = this.mThemeData;
        themeData.hotspotX = f;
        themeData.hotspotY = f2;
        if (applyChanges(str, fileDescriptor, 0, null)) {
            setPenHoverIcon(getThemeFileInputDescriptor(0, null), f, f2);
        } else {
            setPenHoverIcon(null, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
        }
    }

    public final void setPenHoverIcon(FileDescriptor fileDescriptor, float f, float f2) {
        PointerIcon pointerIcon = null;
        if (fileDescriptor != null) {
            Bitmap decodeFileDescriptor = BitmapFactory.decodeFileDescriptor(fileDescriptor, null, null);
            Log.i(TAG, "set icon image = " + decodeFileDescriptor);
            if (decodeFileDescriptor != null) {
                pointerIcon = PointerIcon.create(decodeFileDescriptor, f, f2);
            }
        }
        Log.i(TAG, "set icon pointer icon = " + pointerIcon);
        PointerIcon.setDefaultPointerIconInternal(2, pointerIcon, true);
    }

    public boolean canLaunchCustomDoubleTapAction() {
        int i = Settings.Global.getInt(this.mContext.getContentResolver(), "pen_custom_double_tap_action_enabled", 0);
        String string = Settings.Global.getString(this.mContext.getContentResolver(), "pen_custom_double_tap_action_shortcut");
        if (i == 0) {
            Log.i(TAG, "Cannot launch custom double tab action. It is disabled.");
            return false;
        }
        if (!TextUtils.isEmpty(string)) {
            return true;
        }
        Log.i(TAG, "Cannot launch custom double tab action. Shortcut info is empty.");
        return false;
    }

    public void clearCustomDoubleTapAction(String str) {
        if ("com.samsung.android.pentastic".equals(str)) {
            Settings.Global.putInt(this.mContext.getContentResolver(), "pen_custom_double_tap_action_enabled", 0);
            Settings.Global.putString(this.mContext.getContentResolver(), "pen_custom_double_tap_action_shortcut", "");
        }
    }

    public void clearPenHoverIcon(String str) {
        if (!TextUtils.isEmpty(str) && str.equals(this.mThemeData.packageNameList[0])) {
            resetPenHoverIcon(str);
        }
    }

    public void resetPenHoverIcon(String str) {
        setPenHoverIcon(str, null, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
    }

    public void setPenAttachSound(String str, FileDescriptor fileDescriptor) {
        setPenAttachSound(str, fileDescriptor, String.valueOf(System.nanoTime()));
    }

    public void setPenAttachSound(String str, FileDescriptor fileDescriptor, String str2) {
        Log.i(TAG, "set attach sound package = " + str + ", file = " + fileDescriptor + ", file name = " + str2);
        if (TextUtils.isEmpty(str)) {
            return;
        }
        if (applyChanges(str, fileDescriptor, 1, str2)) {
            setPenAttachSound(getThemeFile(1, str2));
        } else {
            setPenAttachSound(null);
        }
    }

    public final void setPenAttachSound(File file) {
        if (file == null || !file.exists()) {
            this.mThemeData.setAttachSoundPath(null);
        } else {
            this.mThemeData.setAttachSoundPath(file.getAbsolutePath());
        }
    }

    public void resetPenAttachSound(String str) {
        setPenAttachSound(str, null, null);
    }

    public void clearPenAttachSound(String str) {
        if (!TextUtils.isEmpty(str) && str.equals(this.mThemeData.packageNameList[1])) {
            resetPenAttachSound(str);
        }
    }

    public void setPenDetachSound(String str, FileDescriptor fileDescriptor) {
        setPenDetachSound(str, fileDescriptor, String.valueOf(System.nanoTime()));
    }

    public void setPenDetachSound(String str, FileDescriptor fileDescriptor, String str2) {
        Log.i(TAG, "set detach sound package = " + str + ", file = " + fileDescriptor + ", file name = " + str2);
        if (TextUtils.isEmpty(str)) {
            return;
        }
        if (applyChanges(str, fileDescriptor, 2, str2)) {
            setPenDetachSound(getThemeFile(2, str2));
        } else {
            setPenDetachSound(null);
        }
    }

    public final void setPenDetachSound(File file) {
        if (file == null) {
            this.mThemeData.setDetachSoundPath(null);
        } else {
            this.mThemeData.setDetachSoundPath(file.getAbsolutePath());
        }
    }

    public void resetPenDetachSound(String str) {
        setPenDetachSound(str, null, null);
    }

    public void clearPenDetachSound(String str) {
        if (!TextUtils.isEmpty(str) && str.equals(this.mThemeData.packageNameList[2])) {
            resetPenDetachSound(str);
        }
    }

    public void launchCustomDoubleTapAction(UserHandle userHandle, Point point) {
        Log.i(TAG, "Launch custom double tab action.");
        Intent intent = new Intent("com.samsung.android.service.aircommand.ACTION_CUSTOM_DOUBLE_TAP");
        intent.setPackage("com.samsung.android.service.aircommand");
        intent.putExtra("tabX", point.x);
        intent.putExtra("tabY", point.y);
        this.mContext.startServiceAsUser(intent, userHandle);
    }

    public class SoundPathInfo {
        public String attachSoundPath;
        public String detachSoundPath;
        public final Handler mWriteSettingHandler;

        public SoundPathInfo() {
            this.mWriteSettingHandler = new Handler() { // from class: com.android.server.smartclip.SpenThemeManager.SoundPathInfo.1
                @Override // android.os.Handler
                public void handleMessage(Message message) {
                    Object obj = message.obj;
                    if (obj instanceof Context) {
                        SoundPathInfo.this.handleWriteSetting((Context) obj);
                    }
                }
            };
        }

        public void setPaths(String str, String str2) {
            this.attachSoundPath = str;
            this.detachSoundPath = str2;
        }

        public void setPaths(String str) {
            String[] split;
            Log.i(SpenThemeManager.TAG, "set paths = " + str);
            if (TextUtils.isEmpty(str) || (split = str.split(",")) == null || split.length < 2) {
                return;
            }
            this.attachSoundPath = split[0];
            this.detachSoundPath = split[1];
        }

        public String getPaths() {
            String str = (("" + this.attachSoundPath) + ",") + this.detachSoundPath;
            Log.i(SpenThemeManager.TAG, "get paths = " + str);
            return str;
        }

        public void readSetting(Context context) {
            if (context == null) {
                return;
            }
            String stringForUser = Settings.System.getStringForUser(context.getContentResolver(), "pen_detachment_notification", -2);
            Log.i(SpenThemeManager.TAG, "read setting paths = " + stringForUser);
            setPaths(stringForUser);
        }

        public void writeSetting(Context context) {
            Handler handler = this.mWriteSettingHandler;
            handler.sendMessage(handler.obtainMessage(0, context));
        }

        public void handleWriteSetting(Context context) {
            if (context == null) {
                return;
            }
            String paths = getPaths();
            Log.i(SpenThemeManager.TAG, "write setting paths = " + paths);
            Settings.System.putStringForUser(context.getContentResolver(), "pen_detachment_notification", paths, -2);
        }

        public String toString() {
            return "attach sound = " + this.attachSoundPath + ", detach sound = " + this.detachSoundPath;
        }
    }

    public class ThemeData {
        public Context mContext;
        public SoundPathInfo mCurSoundPathInfo;
        public SoundPathInfo mDefaultSoundPathInfo;
        public String[] packageNameList = new String[3];
        public float hotspotX = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
        public float hotspotY = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;

        public ThemeData(Context context) {
            this.mCurSoundPathInfo = new SoundPathInfo();
            this.mDefaultSoundPathInfo = new SoundPathInfo();
            this.mContext = context;
            this.mCurSoundPathInfo.readSetting(context);
            this.mDefaultSoundPathInfo.setPaths("/system/media/audio/ui/Pen_att_noti1.ogg", "/system/media/audio/ui/Pen_det_noti1.ogg");
        }

        public void setAttachSoundPath(String str) {
            Log.i(SpenThemeManager.TAG, "attach sound = " + str);
            if (TextUtils.isEmpty(str)) {
                this.mCurSoundPathInfo.attachSoundPath = this.mDefaultSoundPathInfo.attachSoundPath;
            } else {
                this.mCurSoundPathInfo.attachSoundPath = str;
            }
            this.mCurSoundPathInfo.writeSetting(this.mContext);
        }

        public String getAttachSoundPath() {
            return this.mCurSoundPathInfo.attachSoundPath;
        }

        public void setDetachSoundPath(String str) {
            Log.i(SpenThemeManager.TAG, "detach sound = " + str);
            if (TextUtils.isEmpty(str)) {
                this.mCurSoundPathInfo.detachSoundPath = this.mDefaultSoundPathInfo.detachSoundPath;
            } else {
                this.mCurSoundPathInfo.detachSoundPath = str;
            }
            this.mCurSoundPathInfo.writeSetting(this.mContext);
        }

        public String getDetachSoundPath() {
            return this.mCurSoundPathInfo.detachSoundPath;
        }

        public void saveData() {
            JournaledFile makeJournaledFile = makeJournaledFile();
            BufferedOutputStream bufferedOutputStream = null;
            try {
                XmlSerializer fastXmlSerializer = new FastXmlSerializer();
                FileOutputStream fileOutputStream = new FileOutputStream(makeJournaledFile.chooseForWrite(), false);
                BufferedOutputStream bufferedOutputStream2 = new BufferedOutputStream(fileOutputStream);
                try {
                    fastXmlSerializer.setOutput(bufferedOutputStream2, StandardCharsets.UTF_8.name());
                    fastXmlSerializer.startDocument(null, Boolean.TRUE);
                    saveElements(fastXmlSerializer);
                    fastXmlSerializer.endDocument();
                    bufferedOutputStream2.flush();
                    FileUtils.sync(fileOutputStream);
                    bufferedOutputStream2.close();
                    makeJournaledFile.commit();
                } catch (IOException unused) {
                    bufferedOutputStream = bufferedOutputStream2;
                    IoUtils.closeQuietly(bufferedOutputStream);
                    makeJournaledFile.rollback();
                }
            } catch (IOException unused2) {
            }
        }

        public final void saveElements(XmlSerializer xmlSerializer) {
            try {
                xmlSerializer.startTag(null, "hover-icon");
                xmlSerializer.startTag(null, "package");
                String str = this.packageNameList[0];
                if (str != null) {
                    xmlSerializer.text(str);
                }
                xmlSerializer.endTag(null, "package");
                xmlSerializer.startTag(null, "hotspotX");
                xmlSerializer.text(String.valueOf(this.hotspotX));
                xmlSerializer.endTag(null, "hotspotX");
                xmlSerializer.startTag(null, "hotspotY");
                xmlSerializer.text(String.valueOf(this.hotspotY));
                xmlSerializer.endTag(null, "hotspotY");
                xmlSerializer.endTag(null, "hover-icon");
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i(SpenThemeManager.TAG, "save = " + SpenThemeManager.this.mThemeData.toString());
        }

        public void loadData() {
            FileInputStream fileInputStream;
            int next;
            File chooseForRead = makeJournaledFile().chooseForRead();
            if (!chooseForRead.exists()) {
                Log.i(SpenThemeManager.TAG, "There's no data file to load");
                return;
            }
            FileInputStream fileInputStream2 = null;
            try {
                fileInputStream = new FileInputStream(chooseForRead);
                try {
                    XmlPullParser newPullParser = Xml.newPullParser();
                    newPullParser.setInput(fileInputStream, StandardCharsets.UTF_8.name());
                    do {
                        next = newPullParser.next();
                        if (next == 2 && "hover-icon".equals(newPullParser.getName())) {
                            parseHoverIconData(newPullParser);
                        }
                    } while (next != 1);
                } catch (FileNotFoundException unused) {
                    fileInputStream2 = fileInputStream;
                    Log.w(SpenThemeManager.TAG, "no current wallpaper -- first boot?");
                    fileInputStream = fileInputStream2;
                    IoUtils.closeQuietly(fileInputStream);
                    Log.i(SpenThemeManager.TAG, "load = " + SpenThemeManager.this.mThemeData.toString());
                } catch (IOException | XmlPullParserException e) {
                    e = e;
                    fileInputStream2 = fileInputStream;
                    Log.w(SpenThemeManager.TAG, "failed parsing " + e);
                    fileInputStream = fileInputStream2;
                    IoUtils.closeQuietly(fileInputStream);
                    Log.i(SpenThemeManager.TAG, "load = " + SpenThemeManager.this.mThemeData.toString());
                }
            } catch (FileNotFoundException unused2) {
            } catch (IOException | XmlPullParserException e2) {
                e = e2;
            }
            IoUtils.closeQuietly(fileInputStream);
            Log.i(SpenThemeManager.TAG, "load = " + SpenThemeManager.this.mThemeData.toString());
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

        public final JournaledFile makeJournaledFile() {
            String absolutePath = new File(SpenThemeManager.this.getRootDir(), "spen_theme_data_file").getAbsolutePath();
            return new JournaledFile(new File(absolutePath), new File(absolutePath + ".tmp"));
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            sb.append("icon package name = " + this.packageNameList[0] + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            sb.append("icon hotspotX = " + this.hotspotX + ", hotspotY = " + this.hotspotY + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            return sb.toString();
        }
    }
}
