package com.android.server.wm;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemProperties;
import android.util.AtomicFile;
import android.util.DisplayMetrics;
import android.util.Slog;
import android.util.Xml;
import com.android.internal.util.ArrayUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.samsung.android.rune.CoreRune;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Predicate;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes3.dex */
public class AppWarnings {
    public final ActivityTaskManagerService mAtm;
    public final AtomicFile mConfigFile;
    public DeprecatedAbiDialog mDeprecatedAbiDialog;
    public DeprecatedTargetSdkVersionDialog mDeprecatedTargetSdkVersionDialog;
    public final ConfigHandler mHandler;
    public BaseDialog mPackageNightModeDialog;
    public final Context mUiContext;
    public final UiHandler mUiHandler;
    public UnsupportedCompileSdkDialog mUnsupportedCompileSdkDialog;
    public UnsupportedDisplaySizeDialog mUnsupportedDisplaySizeDialog;
    public final HashMap mPackageFlags = new HashMap();
    public HashSet mAlwaysShowUnsupportedCompileSdkWarningActivities = new HashSet();

    public void alwaysShowUnsupportedCompileSdkWarning(ComponentName componentName) {
        this.mAlwaysShowUnsupportedCompileSdkWarningActivities.add(componentName);
    }

    public AppWarnings(ActivityTaskManagerService activityTaskManagerService, Context context, Handler handler, Handler handler2, File file) {
        this.mAtm = activityTaskManagerService;
        this.mUiContext = context;
        this.mHandler = new ConfigHandler(handler.getLooper());
        this.mUiHandler = new UiHandler(handler2.getLooper());
        this.mConfigFile = new AtomicFile(new File(file, "packages-warnings.xml"), "warnings-config");
        readConfigFromFileAmsThread();
    }

    public void showUnsupportedDisplaySizeDialogIfNeeded(ActivityRecord activityRecord) {
        Configuration globalConfiguration = this.mAtm.getGlobalConfiguration();
        if (globalConfiguration.densityDpi == DisplayMetrics.DENSITY_DEVICE_STABLE || activityRecord.info.applicationInfo.requiresSmallestWidthDp <= globalConfiguration.smallestScreenWidthDp) {
            return;
        }
        this.mUiHandler.showUnsupportedDisplaySizeDialog(activityRecord);
    }

    public void showUnsupportedCompileSdkDialogIfNeeded(ActivityRecord activityRecord) {
        ApplicationInfo applicationInfo = activityRecord.info.applicationInfo;
        if (applicationInfo.compileSdkVersion == 0 || applicationInfo.compileSdkVersionCodename == null || !this.mAlwaysShowUnsupportedCompileSdkWarningActivities.contains(activityRecord.mActivityComponent)) {
            return;
        }
        ApplicationInfo applicationInfo2 = activityRecord.info.applicationInfo;
        int i = applicationInfo2.compileSdkVersion;
        int i2 = Build.VERSION.SDK_INT;
        boolean z = !"REL".equals(applicationInfo2.compileSdkVersionCodename);
        String str = Build.VERSION.CODENAME;
        boolean z2 = !"REL".equals(str);
        if ((!z || i >= i2) && ((!z2 || i2 >= i) && !(z && z2 && i2 == i && !str.equals(activityRecord.info.applicationInfo.compileSdkVersionCodename)))) {
            return;
        }
        this.mUiHandler.showUnsupportedCompileSdkDialog(activityRecord);
    }

    public void showDeprecatedTargetDialogIfNeeded(ActivityRecord activityRecord) {
        if (activityRecord.info.applicationInfo.targetSdkVersion < Build.VERSION.MIN_SUPPORTED_TARGET_SDK_INT) {
            this.mUiHandler.showDeprecatedTargetDialog(activityRecord);
        }
    }

    public void showDeprecatedAbiDialogIfNeeded(ActivityRecord activityRecord) {
        if (((activityRecord.info.applicationInfo.privateFlagsExt & 32) != 0) || SystemProperties.getBoolean("debug.wm.disable_deprecated_abi_dialog", false)) {
            return;
        }
        ApplicationInfo applicationInfo = activityRecord.info.applicationInfo;
        String str = applicationInfo.primaryCpuAbi;
        boolean z = (str == null || applicationInfo.secondaryCpuAbi != null || str.contains("64")) ? false : true;
        if ((ArrayUtils.find(Build.SUPPORTED_ABIS, new Predicate() { // from class: com.android.server.wm.AppWarnings$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$showDeprecatedAbiDialogIfNeeded$0;
                lambda$showDeprecatedAbiDialogIfNeeded$0 = AppWarnings.lambda$showDeprecatedAbiDialogIfNeeded$0((String) obj);
                return lambda$showDeprecatedAbiDialogIfNeeded$0;
            }
        }) != null) && z) {
            this.mUiHandler.showDeprecatedAbiDialog(activityRecord);
        }
    }

    public static /* synthetic */ boolean lambda$showDeprecatedAbiDialogIfNeeded$0(String str) {
        return str.contains("64");
    }

    public void onStartActivity(ActivityRecord activityRecord) {
        showUnsupportedCompileSdkDialogIfNeeded(activityRecord);
        showUnsupportedDisplaySizeDialogIfNeeded(activityRecord);
        showDeprecatedTargetDialogIfNeeded(activityRecord);
        showDeprecatedAbiDialogIfNeeded(activityRecord);
        if (activityRecord.mShouldShowPackageNightModeDialog) {
            this.mUiHandler.showPackageNightModeDialog(activityRecord);
        }
    }

    public void onResumeActivity(ActivityRecord activityRecord) {
        showUnsupportedDisplaySizeDialogIfNeeded(activityRecord);
    }

    public void onPackageDataCleared(String str) {
        removePackageAndHideDialogs(str);
    }

    public void onPackageUninstalled(String str) {
        removePackageAndHideDialogs(str);
    }

    public void onDensityChanged() {
        this.mUiHandler.hideUnsupportedDisplaySizeDialog();
    }

    public final void removePackageAndHideDialogs(String str) {
        this.mUiHandler.hideDialogsForPackage(str);
        synchronized (this.mPackageFlags) {
            this.mPackageFlags.remove(str);
            this.mHandler.scheduleWrite();
        }
    }

    public final void hideUnsupportedDisplaySizeDialogUiThread() {
        UnsupportedDisplaySizeDialog unsupportedDisplaySizeDialog = this.mUnsupportedDisplaySizeDialog;
        if (unsupportedDisplaySizeDialog != null) {
            unsupportedDisplaySizeDialog.dismiss();
            this.mUnsupportedDisplaySizeDialog = null;
        }
    }

    public final boolean isSpeg(String str, int i) {
        PackageManager packageManager;
        if (!CoreRune.SYSFW_APP_SPEG || (packageManager = this.mUiContext.getPackageManager()) == null || !packageManager.isSpeg(str, i)) {
            return false;
        }
        Slog.i("SPEG", "Skipping warning dialog of " + str);
        return true;
    }

    public final void showUnsupportedDisplaySizeDialogUiThread(ActivityRecord activityRecord) {
        if (CoreRune.SYSFW_APP_SPEG && isSpeg(activityRecord.packageName, activityRecord.mUserId)) {
            return;
        }
        UnsupportedDisplaySizeDialog unsupportedDisplaySizeDialog = this.mUnsupportedDisplaySizeDialog;
        if (unsupportedDisplaySizeDialog != null) {
            unsupportedDisplaySizeDialog.dismiss();
            this.mUnsupportedDisplaySizeDialog = null;
        }
        if (activityRecord == null || hasPackageFlag(activityRecord.packageName, 1)) {
            return;
        }
        UnsupportedDisplaySizeDialog unsupportedDisplaySizeDialog2 = new UnsupportedDisplaySizeDialog(this, this.mUiContext, activityRecord.info.applicationInfo);
        this.mUnsupportedDisplaySizeDialog = unsupportedDisplaySizeDialog2;
        unsupportedDisplaySizeDialog2.show();
    }

    public final void showUnsupportedCompileSdkDialogUiThread(ActivityRecord activityRecord) {
        if (CoreRune.SYSFW_APP_SPEG && isSpeg(activityRecord.packageName, activityRecord.mUserId)) {
            return;
        }
        UnsupportedCompileSdkDialog unsupportedCompileSdkDialog = this.mUnsupportedCompileSdkDialog;
        if (unsupportedCompileSdkDialog != null) {
            unsupportedCompileSdkDialog.dismiss();
            this.mUnsupportedCompileSdkDialog = null;
        }
        if (activityRecord == null || hasPackageFlag(activityRecord.packageName, 2)) {
            return;
        }
        UnsupportedCompileSdkDialog unsupportedCompileSdkDialog2 = new UnsupportedCompileSdkDialog(this, this.mUiContext, activityRecord.info.applicationInfo);
        this.mUnsupportedCompileSdkDialog = unsupportedCompileSdkDialog2;
        unsupportedCompileSdkDialog2.show();
    }

    public final void showDeprecatedTargetSdkDialogUiThread(ActivityRecord activityRecord) {
        if (CoreRune.SYSFW_APP_SPEG && isSpeg(activityRecord.packageName, activityRecord.mUserId)) {
            return;
        }
        DeprecatedTargetSdkVersionDialog deprecatedTargetSdkVersionDialog = this.mDeprecatedTargetSdkVersionDialog;
        if (deprecatedTargetSdkVersionDialog != null) {
            deprecatedTargetSdkVersionDialog.dismiss();
            this.mDeprecatedTargetSdkVersionDialog = null;
        }
        if (activityRecord == null || hasPackageFlag(activityRecord.packageName, 4)) {
            return;
        }
        DeprecatedTargetSdkVersionDialog deprecatedTargetSdkVersionDialog2 = new DeprecatedTargetSdkVersionDialog(this, this.mUiContext, activityRecord.info.applicationInfo);
        this.mDeprecatedTargetSdkVersionDialog = deprecatedTargetSdkVersionDialog2;
        deprecatedTargetSdkVersionDialog2.show();
    }

    public final void showDeprecatedAbiDialogUiThread(ActivityRecord activityRecord) {
        if (CoreRune.SYSFW_APP_SPEG && isSpeg(activityRecord.packageName, activityRecord.mUserId)) {
            return;
        }
        DeprecatedAbiDialog deprecatedAbiDialog = this.mDeprecatedAbiDialog;
        if (deprecatedAbiDialog != null) {
            deprecatedAbiDialog.dismiss();
            this.mDeprecatedAbiDialog = null;
        }
        if (activityRecord == null || hasPackageFlag(activityRecord.packageName, 8)) {
            return;
        }
        DeprecatedAbiDialog deprecatedAbiDialog2 = new DeprecatedAbiDialog(this, this.mUiContext, activityRecord.info.applicationInfo);
        this.mDeprecatedAbiDialog = deprecatedAbiDialog2;
        deprecatedAbiDialog2.show();
    }

    public final void showPackageNightModeDialogUiThread(ActivityRecord activityRecord) {
        BaseDialog baseDialog = this.mPackageNightModeDialog;
        if (baseDialog != null) {
            baseDialog.dismiss();
            this.mPackageNightModeDialog = null;
        }
        if (activityRecord == null || !activityRecord.mShouldShowPackageNightModeDialog) {
            return;
        }
        PackageNightModeDialog packageNightModeDialog = new PackageNightModeDialog(this, this.mUiContext, activityRecord.info.applicationInfo);
        this.mPackageNightModeDialog = packageNightModeDialog;
        packageNightModeDialog.show();
        activityRecord.mShouldShowPackageNightModeDialog = false;
    }

    public final void hideDialogsForPackageUiThread(String str) {
        UnsupportedDisplaySizeDialog unsupportedDisplaySizeDialog = this.mUnsupportedDisplaySizeDialog;
        if (unsupportedDisplaySizeDialog != null && (str == null || str.equals(unsupportedDisplaySizeDialog.mPackageName))) {
            this.mUnsupportedDisplaySizeDialog.dismiss();
            this.mUnsupportedDisplaySizeDialog = null;
        }
        UnsupportedCompileSdkDialog unsupportedCompileSdkDialog = this.mUnsupportedCompileSdkDialog;
        if (unsupportedCompileSdkDialog != null && (str == null || str.equals(unsupportedCompileSdkDialog.mPackageName))) {
            this.mUnsupportedCompileSdkDialog.dismiss();
            this.mUnsupportedCompileSdkDialog = null;
        }
        DeprecatedTargetSdkVersionDialog deprecatedTargetSdkVersionDialog = this.mDeprecatedTargetSdkVersionDialog;
        if (deprecatedTargetSdkVersionDialog != null && (str == null || str.equals(deprecatedTargetSdkVersionDialog.mPackageName))) {
            this.mDeprecatedTargetSdkVersionDialog.dismiss();
            this.mDeprecatedTargetSdkVersionDialog = null;
        }
        DeprecatedAbiDialog deprecatedAbiDialog = this.mDeprecatedAbiDialog;
        if (deprecatedAbiDialog != null && (str == null || str.equals(deprecatedAbiDialog.mPackageName))) {
            this.mDeprecatedAbiDialog.dismiss();
            this.mDeprecatedAbiDialog = null;
        }
        BaseDialog baseDialog = this.mPackageNightModeDialog;
        if (baseDialog != null) {
            if (str == null || str.equals(baseDialog.mPackageName)) {
                this.mPackageNightModeDialog.dismiss();
                this.mPackageNightModeDialog = null;
            }
        }
    }

    public boolean hasPackageFlag(String str, int i) {
        return (getPackageFlags(str) & i) == i;
    }

    public void setPackageFlag(String str, int i, boolean z) {
        synchronized (this.mPackageFlags) {
            int packageFlags = getPackageFlags(str);
            int i2 = z ? i | packageFlags : (~i) & packageFlags;
            if (packageFlags != i2) {
                if (i2 != 0) {
                    this.mPackageFlags.put(str, Integer.valueOf(i2));
                } else {
                    this.mPackageFlags.remove(str);
                }
                this.mHandler.scheduleWrite();
            }
        }
    }

    public final int getPackageFlags(String str) {
        int intValue;
        synchronized (this.mPackageFlags) {
            intValue = ((Integer) this.mPackageFlags.getOrDefault(str, 0)).intValue();
        }
        return intValue;
    }

    public final class UiHandler extends Handler {
        public UiHandler(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    AppWarnings.this.showUnsupportedDisplaySizeDialogUiThread((ActivityRecord) message.obj);
                    break;
                case 2:
                    AppWarnings.this.hideUnsupportedDisplaySizeDialogUiThread();
                    break;
                case 3:
                    AppWarnings.this.showUnsupportedCompileSdkDialogUiThread((ActivityRecord) message.obj);
                    break;
                case 4:
                    AppWarnings.this.hideDialogsForPackageUiThread((String) message.obj);
                    break;
                case 5:
                    AppWarnings.this.showDeprecatedTargetSdkDialogUiThread((ActivityRecord) message.obj);
                    break;
                case 6:
                    AppWarnings.this.showDeprecatedAbiDialogUiThread((ActivityRecord) message.obj);
                    break;
                case 7:
                    AppWarnings.this.showPackageNightModeDialogUiThread((ActivityRecord) message.obj);
                    break;
            }
        }

        public void showUnsupportedDisplaySizeDialog(ActivityRecord activityRecord) {
            removeMessages(1);
            obtainMessage(1, activityRecord).sendToTarget();
        }

        public void hideUnsupportedDisplaySizeDialog() {
            removeMessages(2);
            sendEmptyMessage(2);
        }

        public void showUnsupportedCompileSdkDialog(ActivityRecord activityRecord) {
            removeMessages(3);
            obtainMessage(3, activityRecord).sendToTarget();
        }

        public void showDeprecatedTargetDialog(ActivityRecord activityRecord) {
            removeMessages(5);
            obtainMessage(5, activityRecord).sendToTarget();
        }

        public void showDeprecatedAbiDialog(ActivityRecord activityRecord) {
            removeMessages(6);
            obtainMessage(6, activityRecord).sendToTarget();
        }

        public void showPackageNightModeDialog(ActivityRecord activityRecord) {
            removeMessages(7);
            obtainMessage(7, activityRecord).sendToTarget();
        }

        public void hideDialogsForPackage(String str) {
            obtainMessage(4, str).sendToTarget();
        }
    }

    public abstract class BaseDialog {
        public BroadcastReceiver mCloseReceiver;
        public AlertDialog mDialog;
        public final AppWarnings mManager;
        public final String mPackageName;

        public BaseDialog(AppWarnings appWarnings, String str) {
            this.mManager = appWarnings;
            this.mPackageName = str;
        }

        public void show() {
            if (this.mDialog == null) {
                return;
            }
            if (this.mCloseReceiver == null) {
                this.mCloseReceiver = new BroadcastReceiver() { // from class: com.android.server.wm.AppWarnings.BaseDialog.1
                    @Override // android.content.BroadcastReceiver
                    public void onReceive(Context context, Intent intent) {
                        if ("android.intent.action.CLOSE_SYSTEM_DIALOGS".equals(intent.getAction())) {
                            BaseDialog.this.mManager.mUiHandler.hideDialogsForPackage(BaseDialog.this.mPackageName);
                        }
                    }
                };
                this.mManager.mUiContext.registerReceiver(this.mCloseReceiver, new IntentFilter("android.intent.action.CLOSE_SYSTEM_DIALOGS"), 2);
            }
            Slog.w("AppWarnings", "Showing " + getClass().getSimpleName() + " for package " + this.mPackageName);
            this.mDialog.show();
        }

        public void dismiss() {
            if (this.mDialog == null) {
                return;
            }
            if (this.mCloseReceiver != null) {
                this.mManager.mUiContext.unregisterReceiver(this.mCloseReceiver);
                this.mCloseReceiver = null;
            }
            this.mDialog.dismiss();
            this.mDialog = null;
        }
    }

    public final class ConfigHandler extends Handler {
        public ConfigHandler(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != 1) {
                return;
            }
            AppWarnings.this.writeConfigToFileAmsThread();
        }

        public void scheduleWrite() {
            removeMessages(1);
            sendEmptyMessageDelayed(1, 10000L);
        }
    }

    public final void writeConfigToFileAmsThread() {
        HashMap hashMap;
        FileOutputStream fileOutputStream;
        IOException e;
        synchronized (this.mPackageFlags) {
            hashMap = new HashMap(this.mPackageFlags);
        }
        try {
            fileOutputStream = this.mConfigFile.startWrite();
        } catch (IOException e2) {
            fileOutputStream = null;
            e = e2;
        }
        try {
            TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(fileOutputStream);
            resolveSerializer.startDocument((String) null, Boolean.TRUE);
            resolveSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
            resolveSerializer.startTag((String) null, "packages");
            for (Map.Entry entry : hashMap.entrySet()) {
                String str = (String) entry.getKey();
                int intValue = ((Integer) entry.getValue()).intValue();
                if (intValue != 0) {
                    resolveSerializer.startTag((String) null, "package");
                    resolveSerializer.attribute((String) null, "name", str);
                    resolveSerializer.attributeInt((String) null, "flags", intValue);
                    resolveSerializer.endTag((String) null, "package");
                }
            }
            resolveSerializer.endTag((String) null, "packages");
            resolveSerializer.endDocument();
            this.mConfigFile.finishWrite(fileOutputStream);
        } catch (IOException e3) {
            e = e3;
            Slog.w("AppWarnings", "Error writing package metadata", e);
            if (fileOutputStream != null) {
                this.mConfigFile.failWrite(fileOutputStream);
            }
        }
    }

    public final void readConfigFromFileAmsThread() {
        FileInputStream openRead;
        TypedXmlPullParser resolvePullParser;
        int eventType;
        String attributeValue;
        FileInputStream fileInputStream = null;
        try {
            try {
                try {
                    openRead = this.mConfigFile.openRead();
                } catch (IOException unused) {
                    return;
                }
            } catch (IOException e) {
                e = e;
            } catch (XmlPullParserException e2) {
                e = e2;
            }
        } catch (Throwable th) {
            th = th;
        }
        try {
            resolvePullParser = Xml.resolvePullParser(openRead);
            eventType = resolvePullParser.getEventType();
            while (eventType != 2 && eventType != 1) {
                eventType = resolvePullParser.next();
            }
        } catch (IOException e3) {
            e = e3;
            fileInputStream = openRead;
            if (fileInputStream != null) {
                Slog.w("AppWarnings", "Error reading package metadata", e);
            }
            if (fileInputStream == null) {
                return;
            }
            fileInputStream.close();
        } catch (XmlPullParserException e4) {
            e = e4;
            fileInputStream = openRead;
            Slog.w("AppWarnings", "Error reading package metadata", e);
            if (fileInputStream == null) {
                return;
            }
            fileInputStream.close();
        } catch (Throwable th2) {
            th = th2;
            fileInputStream = openRead;
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException unused2) {
                }
            }
            throw th;
        }
        if (eventType == 1) {
            if (openRead != null) {
                try {
                    openRead.close();
                    return;
                } catch (IOException unused3) {
                    return;
                }
            }
            return;
        }
        if ("packages".equals(resolvePullParser.getName())) {
            int next = resolvePullParser.next();
            do {
                if (next == 2) {
                    String name = resolvePullParser.getName();
                    if (resolvePullParser.getDepth() == 2 && "package".equals(name) && (attributeValue = resolvePullParser.getAttributeValue((String) null, "name")) != null) {
                        this.mPackageFlags.put(attributeValue, Integer.valueOf(resolvePullParser.getAttributeInt((String) null, "flags", 0)));
                    }
                }
                next = resolvePullParser.next();
            } while (next != 1);
        }
        if (openRead != null) {
            openRead.close();
        }
    }
}
