package com.android.server;

import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import android.system.Os;
import android.util.Base64OutputStream;
import android.util.Slog;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.GZIPOutputStream;

/* loaded from: classes.dex */
public class FileDescriptorWatcher {
    public static Context mContext;
    public int mFdCount = 0;

    public void initInstance(Context context) {
        mContext = context;
    }

    public int getFileDescriptorCount() {
        return this.mFdCount;
    }

    public void setFileDescriptorCount() {
        File[] listFiles = new File("/proc/self/fd").listFiles();
        this.mFdCount = listFiles != null ? listFiles.length : -1;
    }

    public final class FileDescriptorLeakWatcher implements Runnable {
        public static final SimpleDateFormat mTraceDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        public FileDescriptorLeakWatcher() {
        }

        public final void getFdInfo(StringBuilder sb, File[] fileArr) {
            for (File file : fileArr) {
                try {
                    sb.append(file.getName() + " -> " + new File(Os.readlink(file.getAbsolutePath())).getAbsolutePath() + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
                } catch (Exception unused) {
                    sb.append(file.getName() + " -> readlink error\n");
                }
            }
        }

        public final void getMapInfo(StringBuilder sb) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader("/proc/self/maps"));
                try {
                    sb.append("\n=====system_server MAPS info=====\n");
                    for (String readLine = bufferedReader.readLine(); readLine != null; readLine = bufferedReader.readLine()) {
                        sb.append(readLine);
                        sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
                    }
                    bufferedReader.close();
                } finally {
                }
            } catch (IOException unused) {
                Slog.e("Watchdog:FileDescriptorWatcher", "Failed to write system_server MAPS info");
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r3v1 */
        /* JADX WARN: Type inference failed for: r3v14 */
        /* JADX WARN: Type inference failed for: r3v15 */
        /* JADX WARN: Type inference failed for: r3v16 */
        /* JADX WARN: Type inference failed for: r3v2 */
        /* JADX WARN: Type inference failed for: r3v5, types: [java.io.BufferedReader] */
        /* JADX WARN: Type inference failed for: r3v6 */
        /* JADX WARN: Type inference failed for: r3v7 */
        /* JADX WARN: Type inference failed for: r3v9 */
        public final void getProcessInfo(StringBuilder sb) {
            BufferedReader bufferedReader;
            Throwable th;
            ?? r3 = 0;
            r3 = 0;
            try {
                try {
                    try {
                        Process exec = Runtime.getRuntime().exec("ps -A -o PID -o NAME");
                        exec.waitFor();
                        bufferedReader = new BufferedReader(new InputStreamReader(exec.getInputStream()));
                    } catch (Exception unused) {
                        return;
                    }
                } catch (IOException | InterruptedException unused2) {
                }
            } catch (Throwable th2) {
                bufferedReader = r3;
                th = th2;
            }
            try {
                String readLine = bufferedReader.readLine();
                sb.append("\n=====all Process Status info ('ps -A -o PID -o NAME')=====\n");
                while (readLine != null) {
                    sb.append(readLine);
                    sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
                    readLine = bufferedReader.readLine();
                }
                bufferedReader.close();
                r3 = readLine;
            } catch (IOException | InterruptedException unused3) {
                r3 = bufferedReader;
                Slog.e("Watchdog:FileDescriptorWatcher", "Failed to write all Process Status info");
                if (r3 != 0) {
                    r3.close();
                    r3 = r3;
                }
            } catch (Throwable th3) {
                th = th3;
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (Exception unused4) {
                    }
                }
                throw th;
            }
        }

        public final void recordResult(StringBuilder sb) {
            String str;
            BufferedWriter bufferedWriter;
            BufferedWriter bufferedWriter2 = null;
            try {
                try {
                    try {
                        File file = new File("/data/log/fd_list.txt");
                        if (!file.exists() && !file.createNewFile()) {
                            Slog.e("Watchdog:FileDescriptorWatcher", "Failed to create fd_list.txt");
                        }
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        Base64OutputStream base64OutputStream = new Base64OutputStream(byteArrayOutputStream, 0);
                        GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(base64OutputStream);
                        gZIPOutputStream.write(sb.toString().getBytes("UTF-8"));
                        gZIPOutputStream.close();
                        base64OutputStream.close();
                        byteArrayOutputStream.close();
                        str = "FD_LIST_INFO **********\n" + byteArrayOutputStream.toString() + "\nFD_LIST_INFO **********\n";
                        bufferedWriter = new BufferedWriter(new FileWriter(file, false));
                    } catch (Exception unused) {
                        return;
                    }
                } catch (IOException | SecurityException unused2) {
                }
            } catch (Throwable th) {
                th = th;
            }
            try {
                bufferedWriter.write(str, 0, str.length());
                bufferedWriter.flush();
                bufferedWriter.close();
                Runtime.getRuntime().exec("chown system:log /data/log/fd_list.txt");
            } catch (IOException | SecurityException unused3) {
                bufferedWriter2 = bufferedWriter;
                Slog.e("Watchdog:FileDescriptorWatcher", "Failed to write contens in fd_list.txt");
                if (bufferedWriter2 != null) {
                    bufferedWriter2.close();
                    Runtime.getRuntime().exec("chown system:log /data/log/fd_list.txt");
                    Runtime.getRuntime().exec("chmod 640 /data/log/fd_list.txt");
                }
                return;
            } catch (Throwable th2) {
                th = th2;
                bufferedWriter2 = bufferedWriter;
                if (bufferedWriter2 != null) {
                    try {
                        bufferedWriter2.close();
                        Runtime.getRuntime().exec("chown system:log /data/log/fd_list.txt");
                        Runtime.getRuntime().exec("chmod 640 /data/log/fd_list.txt");
                    } catch (Exception unused4) {
                    }
                }
                throw th;
            }
            Runtime.getRuntime().exec("chmod 640 /data/log/fd_list.txt");
        }

        public final void sendIssueTrackerIntent(Context context) {
            Intent intent = new Intent();
            intent.setPackage("com.salab.issuetracker");
            intent.setAction("com.sec.android.ISSUE_TRACKER_ACTION");
            intent.putExtra("ERRCODE", -136);
            intent.putExtra("ERRPKG", "android");
            intent.putExtra("ERRNAME", "SystemServerFD");
            intent.putExtra("ERRMSG", "SystemServerFD_leak");
            context.sendBroadcastAsUser(intent, UserHandle.SYSTEM);
            Slog.i("Watchdog:FileDescriptorWatcher", "sent intent to issuetracker for FD_LEAK");
        }

        @Override // java.lang.Runnable
        public void run() {
            File[] listFiles = new File("/proc/self/fd").listFiles();
            int length = listFiles != null ? listFiles.length : -1;
            StringBuilder sb = new StringBuilder("");
            sb.append("===== fd_list: " + mTraceDateFormat.format(new Date(System.currentTimeMillis())) + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            sb.append("Length: " + length + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            if (listFiles != null) {
                getFdInfo(sb, listFiles);
            }
            getMapInfo(sb);
            getProcessInfo(sb);
            Slog.e("Watchdog:FileDescriptorWatcher", "!@ The number of fd in system_server is " + length + ", so we make fd_list.txt file for debugging.");
            recordResult(sb);
            sendIssueTrackerIntent(FileDescriptorWatcher.mContext);
        }
    }

    public void startFileDescriptorWatch() {
        new Thread(new FileDescriptorLeakWatcher()).start();
    }
}
