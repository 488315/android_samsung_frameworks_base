package com.samsung.android.camera.scpm;

import android.os.Environment;
import android.os.IInstalld;
import android.util.Slog;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes2.dex */
public class ScpmListManager {
    public String mVersion;
    public final CopyOnWriteArrayList mSCPMPolicyList = new CopyOnWriteArrayList();
    public final CopyOnWriteArrayList mDefaultPolicyList = new CopyOnWriteArrayList();
    public final CopyOnWriteArrayList mCoverFlexRotatePackageList = new CopyOnWriteArrayList();
    public final Path mFilePath = Environment.getDataDirectory().toPath().resolve("system").resolve("Scpmlist");

    public ScpmListManager() {
        for (String[] strArr : ScpmList.DEFAULT_SCPM_LIST) {
            this.mDefaultPolicyList.add(new PolicyListVO(strArr[0], strArr[1], strArr[2]));
        }
        loadDefaultScpmList();
    }

    public synchronized String getVersion() {
        return this.mVersion;
    }

    public synchronized List getScpmList() {
        return Collections.unmodifiableList(this.mSCPMPolicyList);
    }

    public synchronized List getCoverFlexRotatePkgList() {
        return Collections.unmodifiableList(this.mCoverFlexRotatePackageList);
    }

    public final synchronized void setCoverFlexRotatePkgList() {
        Iterator it = this.mSCPMPolicyList.iterator();
        while (it.hasNext()) {
            PolicyListVO policyListVO = (PolicyListVO) it.next();
            if ((Integer.parseInt(policyListVO.value) & IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES) != 0) {
                this.mCoverFlexRotatePackageList.add(policyListVO.decodedName);
            }
        }
    }

    public synchronized String getDefaultVersion() {
        return "2023111000";
    }

    public synchronized List getDefaultScpmList() {
        return Collections.unmodifiableList(this.mDefaultPolicyList);
    }

    public synchronized void loadDefaultScpmList() {
        this.mSCPMPolicyList.clear();
        this.mVersion = "2023111000";
        for (String[] strArr : ScpmList.DEFAULT_SCPM_LIST) {
            this.mSCPMPolicyList.add(new PolicyListVO(strArr[0], strArr[1], strArr[2]));
        }
        setCoverFlexRotatePkgList();
    }

    public synchronized void loadListFromFile() {
        Scanner scanner;
        Slog.v("CameraService/ScpmListManager", "loadListFromFile");
        if (!isDataFileExist()) {
            Slog.i("CameraService/ScpmListManager", "loadListFromFile : list file is not stored. current version : " + this.mVersion);
            return;
        }
        try {
            scanner = new Scanner(this.mFilePath.toFile(), "UTF-8");
            try {
            } catch (Throwable th) {
                try {
                    scanner.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (Exception e) {
            Slog.e("CameraService/ScpmListManager", "loadListFromFile " + e);
            deleteDataFile();
            loadDefaultScpmList();
        }
        if (!scanner.hasNext()) {
            Slog.e("CameraService/ScpmListManager", "Can not get list from files");
            scanner.close();
            return;
        }
        String next = scanner.next();
        if (next.startsWith("20") && next.length() == 10) {
            this.mVersion = next;
            Slog.i("CameraService/ScpmListManager", "loadListFromFile " + this.mVersion);
            if (!scanner.hasNext()) {
                Slog.e("CameraService/ScpmListManager", "Can not get count from files");
                deleteDataFile();
                loadDefaultScpmList();
                scanner.close();
                return;
            }
            int intValue = Integer.valueOf(scanner.next()).intValue();
            if (scanner.hasNext()) {
                this.mSCPMPolicyList.clear();
                do {
                    this.mSCPMPolicyList.add(new PolicyListVO(scanner.next(), scanner.next(), scanner.next()));
                } while (scanner.hasNext());
                if (this.mSCPMPolicyList.size() != intValue) {
                    Slog.e("CameraService/ScpmListManager", "loadListFromFile : size does not matched");
                    deleteDataFile();
                    loadDefaultScpmList();
                    scanner.close();
                    return;
                }
                setCoverFlexRotatePkgList();
            } else {
                Slog.e("CameraService/ScpmListManager", "loadListFromFile : file has not data");
                deleteDataFile();
                loadDefaultScpmList();
            }
            scanner.close();
            return;
        }
        Slog.e("CameraService/ScpmListManager", "loadListFromFile : invaild form. " + next);
        scanner.close();
    }

    public synchronized void saveDataToFile(int i, List list) {
        Slog.v("CameraService/ScpmListManager", "saveDataToFile");
        this.mVersion = String.valueOf(i);
        this.mSCPMPolicyList.clear();
        this.mSCPMPolicyList.addAll(list);
        setCoverFlexRotatePkgList();
        try {
            PrintWriter printWriter = new PrintWriter(this.mFilePath.toFile(), StandardCharsets.UTF_8);
            try {
                printWriter.println(this.mVersion);
                printWriter.println(this.mSCPMPolicyList.size());
                Iterator it = this.mSCPMPolicyList.iterator();
                while (it.hasNext()) {
                    PolicyListVO policyListVO = (PolicyListVO) it.next();
                    printWriter.println(policyListVO.packageName + " " + policyListVO.value + " " + policyListVO.disallowUnihalVersion);
                }
                printWriter.close();
            } catch (Throwable th) {
                try {
                    printWriter.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (Exception e) {
            Slog.e("CameraService/ScpmListManager", "saveDataFiles " + e);
            deleteDataFile();
        }
    }

    public final boolean isDataFileExist() {
        try {
            return Files.exists(this.mFilePath, new LinkOption[0]);
        } catch (Exception e) {
            Slog.e("CameraService/ScpmListManager", "isDataFileExist " + e);
            this.deleteDataFile();
            return false;
        }
    }

    public final void deleteDataFile() {
        try {
            Files.deleteIfExists(this.mFilePath);
        } catch (Exception e) {
            Slog.e("CameraService/ScpmListManager", "deleteDataFile " + e);
        }
    }
}
