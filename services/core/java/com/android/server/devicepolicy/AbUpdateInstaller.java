package com.android.server.devicepolicy;

import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.os.PowerManager;
import android.os.UpdateEngine;
import android.os.UpdateEngineCallback;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class AbUpdateInstaller extends UpdateInstaller {
    public static final Map errorCodesMap;
    public static final Map errorStringsMap;
    public Enumeration mEntries;
    public long mOffsetForUpdate;
    public ZipFile mPackedUpdateFile;
    public List mProperties;
    public long mSizeForUpdate;
    public boolean mUpdateInstalled;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DelegatingUpdateEngineCallback extends UpdateEngineCallback {
        public UpdateEngine mUpdateEngine;
        public UpdateInstaller mUpdateInstaller;

        public final void onPayloadApplicationComplete(int i) {
            this.mUpdateEngine.unbind();
            if (i == 0) {
                UpdateInstaller updateInstaller = this.mUpdateInstaller;
                File file = updateInstaller.mCopiedUpdateFile;
                if (file != null && file.exists()) {
                    updateInstaller.mCopiedUpdateFile.delete();
                }
                ((PowerManager)
                                updateInstaller.mInjector.mContext.getSystemService(
                                        PowerManager.class))
                        .reboot("deviceowner");
                return;
            }
            this.mUpdateInstaller.notifyCallbackOnError(
                    ((Integer)
                                    ((HashMap) AbUpdateInstaller.errorCodesMap)
                                            .getOrDefault(Integer.valueOf(i), 1))
                            .intValue(),
                    (String)
                            ((HashMap) AbUpdateInstaller.errorStringsMap)
                                    .getOrDefault(
                                            Integer.valueOf(i),
                                            VibrationParam$1$$ExternalSyntheticOutline0.m(
                                                    i, "Unknown error with error code = ")));
        }

        public final void onStatusUpdate(int i, float f) {}
    }

    static {
        HashMap hashMap = new HashMap();
        hashMap.put(1, 1);
        hashMap.put(20, 2);
        hashMap.put(51, 2);
        hashMap.put(12, 3);
        hashMap.put(11, 3);
        hashMap.put(6, 3);
        hashMap.put(10, 3);
        hashMap.put(26, 3);
        hashMap.put(5, 1);
        hashMap.put(7, 1);
        hashMap.put(9, 1);
        hashMap.put(52, 1);
        errorCodesMap = hashMap;
        HashMap hashMap2 = new HashMap();
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                1,
                hashMap2,
                "Unknown error with error code = ",
                20,
                "The delta update payload was targeted for another version or the source"
                    + " partitionwas modified after it was installed");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                5,
                hashMap2,
                "Failed to finish the configured postinstall works.",
                7,
                "Failed to open one of the partitions it tried to write to or read data from.");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                6,
                hashMap2,
                "Payload mismatch error.",
                9,
                "Failed to read the payload data from the given URL.");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                10, hashMap2, "Payload hash error.", 11, "Payload size mismatch error.");
        AbUpdateInstaller$$ExternalSyntheticOutline0.m(
                12,
                hashMap2,
                "Failed to verify the signature of the payload.",
                52,
                "The payload has been successfully installed,but the active slot was not flipped.");
        errorStringsMap = hashMap2;
    }

    public final void applyPayload(String str) {
        long j = 0;
        while (this.mEntries.hasMoreElements()) {
            ZipEntry zipEntry = (ZipEntry) this.mEntries.nextElement();
            String name = zipEntry.getName();
            j +=
                    zipEntry.getCompressedSize()
                            + name.length()
                            + 30
                            + (zipEntry.getExtra() == null ? 0 : zipEntry.getExtra().length);
            if (zipEntry.isDirectory()) {
                j -= zipEntry.getCompressedSize();
            } else if ("payload.bin".equals(name)) {
                if (zipEntry.getMethod() != 0) {
                    Log.w("UpdateInstaller", "Invalid compression method.");
                    notifyCallbackOnError(3, "Invalid compression method.");
                    return;
                } else {
                    this.mSizeForUpdate = zipEntry.getCompressedSize();
                    this.mOffsetForUpdate = j - zipEntry.getCompressedSize();
                }
            } else if ("payload_properties.txt".equals(name)) {
                BufferedReader bufferedReader =
                        new BufferedReader(
                                new InputStreamReader(
                                        this.mPackedUpdateFile.getInputStream(zipEntry)));
                while (true) {
                    try {
                        String readLine = bufferedReader.readLine();
                        if (readLine == null) {
                            break;
                        } else {
                            ((ArrayList) this.mProperties).add(readLine);
                        }
                    } catch (Throwable th) {
                        try {
                            bufferedReader.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                }
                bufferedReader.close();
            } else {
                continue;
            }
        }
        String[] strArr =
                (String[])
                        this.mProperties.stream()
                                .toArray(new AbUpdateInstaller$$ExternalSyntheticLambda1());
        if (this.mSizeForUpdate == -1) {
            Log.w("UpdateInstaller", "Failed to find payload entry in the given package.");
            notifyCallbackOnError(3, "Failed to find payload entry in the given package.");
            return;
        }
        UpdateEngine updateEngine = new UpdateEngine();
        DelegatingUpdateEngineCallback delegatingUpdateEngineCallback =
                new DelegatingUpdateEngineCallback();
        delegatingUpdateEngineCallback.mUpdateInstaller = this;
        delegatingUpdateEngineCallback.mUpdateEngine = updateEngine;
        updateEngine.bind(delegatingUpdateEngineCallback);
        try {
            updateEngine.applyPayload(str, this.mOffsetForUpdate, this.mSizeForUpdate, strArr);
        } catch (Exception e) {
            Log.w("UpdateInstaller", "Failed to install update from file.", e);
            notifyCallbackOnError(1, "Failed to install update from file.");
        }
    }

    @Override // com.android.server.devicepolicy.UpdateInstaller
    public final void installUpdateInThread() {
        if (this.mUpdateInstalled) {
            throw new IllegalStateException("installUpdateInThread can be called only once.");
        }
        try {
            setState();
            applyPayload(
                    Paths.get(this.mCopiedUpdateFile.getAbsolutePath(), new String[0])
                            .toUri()
                            .toString());
        } catch (ZipException e) {
            Log.w("UpdateInstaller", e);
            notifyCallbackOnError(3, Log.getStackTraceString(e));
        } catch (IOException e2) {
            Log.w("UpdateInstaller", e2);
            notifyCallbackOnError(1, Log.getStackTraceString(e2));
        }
    }

    public final void setState() {
        this.mUpdateInstalled = true;
        this.mPackedUpdateFile = new ZipFile(this.mCopiedUpdateFile);
        this.mProperties = new ArrayList();
        this.mSizeForUpdate = -1L;
        this.mOffsetForUpdate = 0L;
        this.mEntries = this.mPackedUpdateFile.entries();
    }
}
