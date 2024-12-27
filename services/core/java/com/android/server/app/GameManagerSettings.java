package com.android.server.app;

import android.os.FileUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.AtomicFile;
import android.util.Slog;
import android.util.Xml;

import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.XmlUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class GameManagerSettings {
    final AtomicFile mSettingsFile;
    public final ArrayMap mGameModes = new ArrayMap();
    public final ArrayMap mConfigOverrides = new ArrayMap();

    public GameManagerSettings(File file) {
        File file2 = new File(file, "system");
        file2.mkdirs();
        FileUtils.setPermissions(file2.toString(), 509, -1, -1);
        this.mSettingsFile = new AtomicFile(new File(file2, "game-manager-service.xml"));
    }

    public static void writeGameModeConfigTags(
            TypedXmlSerializer typedXmlSerializer,
            GameManagerService.GamePackageConfiguration gamePackageConfiguration) {
        boolean z;
        String str;
        int i;
        if (gamePackageConfiguration == null) {
            return;
        }
        for (int i2 : gamePackageConfiguration.getAvailableGameModes()) {
            GameManagerService.GamePackageConfiguration.GameModeConfiguration
                    gameModeConfiguration = gamePackageConfiguration.getGameModeConfiguration(i2);
            if (gameModeConfiguration != null) {
                typedXmlSerializer.startTag((String) null, "gameModeConfig");
                typedXmlSerializer.attributeInt((String) null, "gameMode", i2);
                synchronized (gameModeConfiguration) {
                    z = gameModeConfiguration.mUseAngle;
                }
                typedXmlSerializer.attributeBoolean((String) null, "useAngle", z);
                synchronized (gameModeConfiguration) {
                    str = gameModeConfiguration.mFps;
                }
                typedXmlSerializer.attribute((String) null, "fps", str);
                typedXmlSerializer.attributeFloat(
                        (String) null, "scaling", gameModeConfiguration.getScaling());
                synchronized (gameModeConfiguration) {
                    i = gameModeConfiguration.mLoadingBoostDuration;
                }
                typedXmlSerializer.attributeInt((String) null, "loadingBoost", i);
                typedXmlSerializer.endTag((String) null, "gameModeConfig");
            }
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(10:38|39|41|42|(2:43|44)|72|56|57|58|a6) */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x012a, code lost:

       r10 = r3.mModeConfigLock;
    */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x012c, code lost:

       monitor-enter(r10);
    */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x012d, code lost:

       r1 = !r3.mModeConfigs.isEmpty();
    */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0134, code lost:

       monitor-exit(r10);
    */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0135, code lost:

       if (r1 == false) goto L125;
    */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0137, code lost:

       r9.mConfigOverrides.put(r0, r3);
    */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x013c, code lost:

       return;
    */
    /* JADX WARN: Code restructure failed: missing block: B:24:?, code lost:

       return;
    */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x00ae, code lost:

       r5 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x00af, code lost:

       r6 = r10.getAttributeValue((java.lang.String) null, "useAngle");
    */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x00b6, code lost:

       if (r6 != null) goto L56;
    */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x00b8, code lost:

       android.util.Slog.wtf("GameManagerService_GameManagerSettings", "Invalid useAngle value in config tag: ".concat(r6), r5);
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void readPackage(com.android.modules.utils.TypedXmlPullParser r10) {
        /*
            Method dump skipped, instructions count: 320
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.app.GameManagerSettings.readPackage(com.android.modules.utils.TypedXmlPullParser):void");
    }

    public final void readPersistentDataLocked() {
        int next;
        this.mGameModes.clear();
        if (!this.mSettingsFile.exists()) {
            Slog.v(
                    "GameManagerService_GameManagerSettings",
                    "Settings file doesn't exist, skip reading");
            return;
        }
        try {
            FileInputStream openRead = this.mSettingsFile.openRead();
            TypedXmlPullParser resolvePullParser = Xml.resolvePullParser(openRead);
            do {
                next = resolvePullParser.next();
                if (next == 2) {
                    break;
                }
            } while (next != 1);
            if (next != 2) {
                Slog.wtf(
                        "GameManagerService_GameManagerSettings",
                        "No start tag found in game manager settings");
                return;
            }
            int depth = resolvePullParser.getDepth();
            while (true) {
                int next2 = resolvePullParser.next();
                if (next2 == 1 || (next2 == 3 && resolvePullParser.getDepth() <= depth)) {
                    break;
                }
                if (next2 != 3 && next2 != 4) {
                    String name = resolvePullParser.getName();
                    if (next2 == 2 && "package".equals(name)) {
                        readPackage(resolvePullParser);
                    } else {
                        XmlUtils.skipCurrentTag(resolvePullParser);
                        Slog.w(
                                "GameManagerService_GameManagerSettings",
                                "Unknown element under packages tag: "
                                        + name
                                        + " with type: "
                                        + next2);
                    }
                }
            }
            openRead.close();
        } catch (IOException | XmlPullParserException e) {
            Slog.wtf(
                    "GameManagerService_GameManagerSettings",
                    "Error reading game manager settings",
                    e);
        }
    }

    public final void writePersistentDataLocked() {
        FileOutputStream startWrite;
        FileOutputStream fileOutputStream = null;
        try {
            startWrite = this.mSettingsFile.startWrite();
        } catch (IOException e) {
            e = e;
        }
        try {
            TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(startWrite);
            resolveSerializer.startDocument((String) null, Boolean.TRUE);
            resolveSerializer.setFeature(
                    "http://xmlpull.org/v1/doc/features.html#indent-output", true);
            resolveSerializer.startTag((String) null, "packages");
            ArraySet arraySet = new ArraySet(this.mGameModes.keySet());
            arraySet.addAll(this.mConfigOverrides.keySet());
            Iterator it = arraySet.iterator();
            while (it.hasNext()) {
                String str = (String) it.next();
                resolveSerializer.startTag((String) null, "package");
                resolveSerializer.attribute((String) null, "name", str);
                if (this.mGameModes.containsKey(str)) {
                    resolveSerializer.attributeInt(
                            (String) null,
                            "gameMode",
                            ((Integer) this.mGameModes.get(str)).intValue());
                }
                writeGameModeConfigTags(
                        resolveSerializer,
                        (GameManagerService.GamePackageConfiguration)
                                this.mConfigOverrides.get(str));
                resolveSerializer.endTag((String) null, "package");
            }
            resolveSerializer.endTag((String) null, "packages");
            resolveSerializer.endDocument();
            this.mSettingsFile.finishWrite(startWrite);
            FileUtils.setPermissions(
                    this.mSettingsFile.toString(),
                    FrameworkStatsLog.HOTWORD_DETECTION_SERVICE_RESTARTED,
                    -1,
                    -1);
        } catch (IOException e2) {
            e = e2;
            fileOutputStream = startWrite;
            this.mSettingsFile.failWrite(fileOutputStream);
            Slog.wtf(
                    "GameManagerService_GameManagerSettings",
                    "Unable to write game manager service settings, current changes will be lost at"
                        + " reboot",
                    e);
        }
    }
}
