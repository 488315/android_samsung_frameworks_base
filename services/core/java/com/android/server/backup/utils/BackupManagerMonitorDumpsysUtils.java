package com.android.server.backup.utils;

import android.app.backup.BackupRestoreEventLogger;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.os.Bundle;
import android.os.Environment;
import android.util.Slog;

import com.android.internal.util.FastPrintWriter;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.MagnificationConnectionManager$$ExternalSyntheticOutline0;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class BackupManagerMonitorDumpsysUtils {
    public static final long LOGS_RETENTION_PERIOD_MILLISEC = TimeUnit.DAYS.toMillis(1) * 60;
    public boolean mIsAfterRetentionPeriod;
    public boolean mIsAfterRetentionPeriodCached = false;
    public boolean mIsFileLargerThanSizeLimit = false;

    public static void addAgentLogsIfAvailable(Bundle bundle, PrintWriter printWriter) {
        if (bundle.containsKey("android.app.backup.extra.LOG_AGENT_LOGGING_RESULTS")
                ? !bundle.getParcelableArrayList(
                                "android.app.backup.extra.LOG_AGENT_LOGGING_RESULTS")
                        .isEmpty()
                : false) {
            printWriter.println("\tAgent Logs:");
            Iterator it =
                    bundle.getParcelableArrayList(
                                    "android.app.backup.extra.LOG_AGENT_LOGGING_RESULTS")
                            .iterator();
            while (it.hasNext()) {
                BackupRestoreEventLogger.DataTypeResult dataTypeResult =
                        (BackupRestoreEventLogger.DataTypeResult) it.next();
                int successCount = dataTypeResult.getSuccessCount() + dataTypeResult.getFailCount();
                printWriter.println("\t\tData Type: " + dataTypeResult.getDataType());
                printWriter.println(
                        "\t\t\tItem restored: "
                                + dataTypeResult.getSuccessCount()
                                + "/"
                                + successCount);
                for (Map.Entry entry : dataTypeResult.getErrors().entrySet()) {
                    printWriter.println(
                            "\t\t\tAgent Error - Category: "
                                    + ((String) entry.getKey())
                                    + ", Count: "
                                    + entry.getValue());
                }
            }
        }
    }

    public static void addExtrasIfAvailable(Bundle bundle, PrintWriter printWriter) {
        if (bundle.getInt("android.app.backup.extra.LOG_EVENT_ID") == 27) {
            if (bundle.containsKey("android.app.backup.extra.LOG_RESTORE_ANYWAY")) {
                printWriter.println(
                        "\t\tPackage supports RestoreAnyVersion: "
                                + bundle.getBoolean("android.app.backup.extra.LOG_RESTORE_ANYWAY"));
            }
            if (bundle.containsKey("android.app.backup.extra.LOG_RESTORE_VERSION")) {
                printWriter.println(
                        "\t\tPackage version on source: "
                                + bundle.getLong("android.app.backup.extra.LOG_RESTORE_VERSION"));
            }
            if (bundle.containsKey("android.app.backup.extra.LOG_EVENT_PACKAGE_FULL_VERSION")) {
                printWriter.println(
                        "\t\tPackage version on target: "
                                + bundle.getLong(
                                        "android.app.backup.extra.LOG_EVENT_PACKAGE_FULL_VERSION"));
            }
        }
        if (bundle.getInt("android.app.backup.extra.LOG_EVENT_ID") == 72) {
            if (bundle.containsKey("android.app.backup.extra.V_TO_U_DENYLIST")) {
                printWriter.println(
                        "\t\tV to U Denylist : "
                                + bundle.getString("android.app.backup.extra.V_TO_U_DENYLIST"));
            }
            if (bundle.containsKey("android.app.backup.extra.V_TO_U_ALLOWLIST")) {
                printWriter.println(
                        "\t\tV to U Allowllist : "
                                + bundle.getString("android.app.backup.extra.V_TO_U_ALLOWLIST"));
            }
        }
    }

    public static File getBMMEventsFile() {
        return new File(new File(Environment.getDataDirectory(), "backup"), "bmmevents.txt");
    }

    public static String getId(int i) {
        switch (i) {
            case 4:
                return "Full backup cancel";
            case 5:
                return "Illegal key";
            case 6:
            case 8:
            case 17:
            case 20:
            case 32:
            case 33:
            default:
                return VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Unknown log event ID: ");
            case 7:
                return "No data to send";
            case 9:
                return "Package ineligible";
            case 10:
                return "Package key-value participant";
            case 11:
                return "Package stopped";
            case 12:
                return "Package not found";
            case 13:
                return "Backup disabled";
            case 14:
                return "Device not provisioned";
            case 15:
                return "Package transport not present";
            case 16:
                return "Error preflight";
            case 18:
                return "Quota hit preflight";
            case 19:
                return "Exception full backup";
            case 21:
                return "Key-value backup cancel";
            case 22:
                return "No restore metadata available";
            case 23:
                return "No PM metadata received";
            case 24:
                return "PM agent has no metadata";
            case 25:
                return "Lost transport";
            case 26:
                return "Package not present";
            case 27:
                return "Restore version higher";
            case 28:
                return "App has no agent";
            case 29:
                return "Signature mismatch";
            case 30:
                return "Can't find agent";
            case 31:
                return "Key-value restore timeout";
            case 34:
                return "Restore any version";
            case 35:
                return "Versions match";
            case 36:
                return "Version of backup older";
            case 37:
                return "Full restore signature mismatch";
            case 38:
                return "System app no agent";
            case 39:
                return "Full restore allow backup false";
            case 40:
                return "APK not installed";
            case 41:
                return "Cannot restore without APK";
            case 42:
                return "Missing signature";
            case 43:
                return "Expected different package";
            case 44:
                return "Unknown version";
            case 45:
                return "Full restore timeout";
            case 46:
                return "Corrupt manifest";
            case 47:
                return "Widget metadata mismatch";
            case 48:
                return "Widget unknown version";
            case 49:
                return "No packages";
            case 50:
                return "Transport is null";
            case 51:
                return "Transport non-incremental backup required";
            case 52:
                return "Agent logging results";
            case 53:
                return "Start system restore";
            case 54:
                return "Start restore at install";
            case 55:
                return "Transport error during start restore";
            case 56:
                return "Cannot get next package name";
            case 57:
                return "Unknown restore type";
            case 58:
                return "KV restore";
            case 59:
                return "Full restore";
            case 60:
                return "No next restore target";
            case 61:
                return "KV agent error";
            case 62:
                return "Package restore finished";
            case 63:
                return "Transport error KV restore";
            case 64:
                return "No feeder thread";
            case 65:
                return "Full agent error";
            case 66:
                return "Transport error full restore";
            case 67:
                return "Start package restore";
            case 68:
                return "Restore complete";
            case 69:
                return "Agent failure";
            case 70:
                return "V to U restore pkg eligible";
            case 71:
                return "V to U restore pkg not eligible";
            case 72:
                return "V to U restore lists";
            case 73:
                return "Invoked restore at install";
            case 74:
                return "Skip restore at install";
            case 75:
                return "Pkg accepted for restore";
            case 76:
                return "Restore data does not belong to package";
            case 77:
                return "Unable to create Agent";
            case 78:
                return "Agent crashed before restore data is streamed";
            case 79:
                return "Failed to send data to agent";
            case 80:
                return "Agent failure during restore";
            case 81:
                return "Failed to read data from Transport";
        }
    }

    public static boolean isDateAfterNMillisec(long j, long j2, long j3) {
        return j > j2 || j2 - j >= j3;
    }

    public final boolean deleteExpiredBMMEvents() {
        try {
            if (!isAfterRetentionPeriod()) {
                return false;
            }
            File bMMEventsFile = getBMMEventsFile();
            if (bMMEventsFile.exists()) {
                if (bMMEventsFile.delete()) {
                    Slog.i("BackupManagerMonitorDumpsysUtils", "Deleted expired BMM Events");
                } else {
                    Slog.e(
                            "BackupManagerMonitorDumpsysUtils",
                            "Unable to delete expired BMM Events");
                }
            }
            return true;
        } catch (Exception unused) {
            return true;
        }
    }

    public long getBMMEventsFileSizeLimit() {
        return 25600000L;
    }

    public long getRetentionPeriodInMillisec() {
        return LOGS_RETENTION_PERIOD_MILLISEC;
    }

    public String getSetUpDate() {
        try {
            FileInputStream fileInputStream = new FileInputStream(getSetUpDateFile());
            try {
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                try {
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    try {
                        String readLine = bufferedReader.readLine();
                        bufferedReader.close();
                        inputStreamReader.close();
                        fileInputStream.close();
                        return readLine;
                    } finally {
                    }
                } finally {
                }
            } finally {
            }
        } catch (Exception e) {
            MagnificationConnectionManager$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder("An error occurred while reading the date: "),
                    "BackupManagerMonitorDumpsysUtils");
            return "Could not retrieve setup date";
        }
    }

    public File getSetUpDateFile() {
        return new File(
                new File(Environment.getDataDirectory(), "backup"), "initialSetupTimestamp.txt");
    }

    public boolean isAfterRetentionPeriod() {
        if (this.mIsAfterRetentionPeriodCached) {
            return this.mIsAfterRetentionPeriod;
        }
        if (getSetUpDateFile().length() == 0) {
            this.mIsAfterRetentionPeriod = false;
            this.mIsAfterRetentionPeriodCached = true;
            return false;
        }
        try {
            boolean isDateAfterNMillisec =
                    isDateAfterNMillisec(
                            Long.parseLong(getSetUpDate()),
                            System.currentTimeMillis(),
                            getRetentionPeriodInMillisec());
            this.mIsAfterRetentionPeriod = isDateAfterNMillisec;
            this.mIsAfterRetentionPeriodCached = true;
            return isDateAfterNMillisec;
        } catch (NumberFormatException unused) {
            this.mIsAfterRetentionPeriod = true;
            this.mIsAfterRetentionPeriodCached = true;
            return true;
        }
    }

    public final void parseBackupManagerMonitorRestoreEventForDumpsys(Bundle bundle) {
        if (!isAfterRetentionPeriod()
                && bundle.getInt("android.app.backup.extra.OPERATION_TYPE", -1) == 1) {
            if (!bundle.containsKey("android.app.backup.extra.LOG_EVENT_ID")
                    || !bundle.containsKey("android.app.backup.extra.LOG_EVENT_CATEGORY")) {
                Slog.w(
                        "BackupManagerMonitorDumpsysUtils",
                        "Event id and category are not optional fields.");
                return;
            }
            File bMMEventsFile = getBMMEventsFile();
            if (bMMEventsFile.length() == 0) {
                recordSetUpTimestamp();
            }
            if (!this.mIsFileLargerThanSizeLimit) {
                this.mIsFileLargerThanSizeLimit =
                        bMMEventsFile.length() > getBMMEventsFileSizeLimit();
            }
            if (this.mIsFileLargerThanSizeLimit) {
                return;
            }
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(bMMEventsFile, true);
                try {
                    FastPrintWriter fastPrintWriter = new FastPrintWriter(fileOutputStream);
                    try {
                        bundle.getInt("android.app.backup.extra.LOG_EVENT_CATEGORY");
                        int i = bundle.getInt("android.app.backup.extra.LOG_EVENT_ID");
                        if (i == 52) {
                            if (!(bundle.containsKey(
                                            "android.app.backup.extra.LOG_AGENT_LOGGING_RESULTS")
                                    ? !bundle.getParcelableArrayList(
                                                    "android.app.backup.extra.LOG_AGENT_LOGGING_RESULTS")
                                            .isEmpty()
                                    : false)) {
                                fastPrintWriter.close();
                                fileOutputStream.close();
                                return;
                            }
                        }
                        fastPrintWriter.println(
                                "["
                                        + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
                                                .format(new Date(System.currentTimeMillis()))
                                        + "] - "
                                        + getId(i));
                        if (bundle.containsKey("android.app.backup.extra.LOG_EVENT_PACKAGE_NAME")) {
                            fastPrintWriter.println(
                                    "\tPackage: "
                                            + bundle.getString(
                                                    "android.app.backup.extra.LOG_EVENT_PACKAGE_NAME"));
                        }
                        addAgentLogsIfAvailable(bundle, fastPrintWriter);
                        addExtrasIfAvailable(bundle, fastPrintWriter);
                        fastPrintWriter.close();
                        fileOutputStream.close();
                    } finally {
                    }
                } finally {
                }
            } catch (IOException e) {
                BootReceiver$$ExternalSyntheticOutline0.m(
                        "IO Exception when writing BMM events to file: ",
                        e,
                        "BackupManagerMonitorDumpsysUtils");
            }
        }
    }

    public void recordSetUpTimestamp() {
        File setUpDateFile = getSetUpDateFile();
        if (setUpDateFile.length() == 0) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(setUpDateFile, true);
                try {
                    FastPrintWriter fastPrintWriter = new FastPrintWriter(fileOutputStream);
                    try {
                        fastPrintWriter.println(System.currentTimeMillis());
                        fastPrintWriter.close();
                        fileOutputStream.close();
                    } finally {
                    }
                } finally {
                }
            } catch (IOException e) {
                Slog.w(
                        "BackupManagerMonitorDumpsysUtils",
                        "An error occurred while recording the setup date: " + e.getMessage());
            }
        }
    }
}
