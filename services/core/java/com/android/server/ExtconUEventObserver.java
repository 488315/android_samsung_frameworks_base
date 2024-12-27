package com.android.server;

import android.os.FileUtils;
import android.os.UEventObserver;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Slog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class ExtconUEventObserver extends UEventObserver {
    public final Map mExtconInfos = new ArrayMap();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ExtconInfo {
        public static ExtconInfo[] sExtconInfos;
        public static final Object sLock = new Object();
        public final HashSet mDeviceTypes = new HashSet();
        public final String mName;

        public ExtconInfo(String str) {
            this.mName = str;
            File[] listFilesOrEmpty =
                    FileUtils.listFilesOrEmpty(
                            new File("/sys/class/extcon", str),
                            new ExtconUEventObserver$ExtconInfo$$ExternalSyntheticLambda0());
            if (listFilesOrEmpty.length == 0) {
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        "Unable to list cables in /sys/class/extcon/",
                        str,
                        ". This probably means the selinux policies need to be changed.",
                        "ExtconUEventObserver");
            }
            for (File file : listFilesOrEmpty) {
                String str2 = null;
                try {
                    String canonicalPath = file.getCanonicalPath();
                    try {
                        this.mDeviceTypes.add(
                                FileUtils.readTextFile(new File(file, "name"), 0, null)
                                        .replace("\n", "")
                                        .replace("\r", ""));
                    } catch (IOException e) {
                        e = e;
                        str2 = canonicalPath;
                        Slog.w(
                                "ExtconUEventObserver",
                                "Unable to read "
                                        + str2
                                        + "/name. This probably means the selinux policies need to"
                                        + " be changed.",
                                e);
                    }
                } catch (IOException e2) {
                    e = e2;
                }
            }
        }

        public static List getExtconInfoForTypes(String[] strArr) {
            synchronized (sLock) {
                initExtconInfos();
            }
            ArrayList arrayList = new ArrayList();
            for (ExtconInfo extconInfo : sExtconInfos) {
                int length = strArr.length;
                int i = 0;
                while (true) {
                    if (i < length) {
                        if (extconInfo.mDeviceTypes.contains(strArr[i])) {
                            arrayList.add(extconInfo);
                            break;
                        }
                        i++;
                    }
                }
            }
            return arrayList;
        }

        public static void initExtconInfos() {
            if (sExtconInfos != null) {
                return;
            }
            File file = new File("/sys/class/extcon");
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                ArrayList arrayList = new ArrayList(listFiles.length);
                for (File file2 : listFiles) {
                    arrayList.add(new ExtconInfo(file2.getName()));
                }
                sExtconInfos = (ExtconInfo[]) arrayList.toArray(new ExtconInfo[0]);
                return;
            }
            Slog.w(
                    "ExtconUEventObserver",
                    file
                            + " exists "
                            + file.exists()
                            + " isDir "
                            + file.isDirectory()
                            + " but listFiles returns null.This probably means the selinux policies"
                            + " need to be changed.");
            sExtconInfos = new ExtconInfo[0];
        }

        public final String getDevicePath() {
            String str = this.mName;
            try {
                File file =
                        new File(
                                TextUtils.formatSimple("/sys/class/extcon/%s", new Object[] {str}));
                if (!file.exists()) {
                    return null;
                }
                String canonicalPath = file.getCanonicalPath();
                return canonicalPath.substring(canonicalPath.indexOf("/devices"));
            } catch (IOException e) {
                Slog.e(
                        "ExtconUEventObserver",
                        "Could not get the extcon device path for " + str,
                        e);
                return null;
            }
        }
    }

    public final void onUEvent(UEventObserver.UEvent uEvent) {
        ExtconInfo extconInfo =
                (ExtconInfo) ((ArrayMap) this.mExtconInfos).get(uEvent.get("DEVPATH"));
        if (extconInfo != null) {
            onUEvent(extconInfo, uEvent);
            return;
        }
        Slog.w(
                "ExtconUEventObserver",
                "No match found for DEVPATH of " + uEvent + " in " + this.mExtconInfos);
    }

    public abstract void onUEvent(ExtconInfo extconInfo, UEventObserver.UEvent uEvent);
}
