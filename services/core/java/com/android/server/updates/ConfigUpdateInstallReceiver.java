package com.android.server.updates;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.util.EventLog;
import android.util.Slog;

import com.android.internal.util.HexDump;

import libcore.io.IoUtils;
import libcore.io.Streams;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class ConfigUpdateInstallReceiver extends BroadcastReceiver {
    public final File updateContent;
    public final File updateDir;
    public final File updateVersion;

    public ConfigUpdateInstallReceiver(String str, String str2, String str3, String str4) {
        this.updateDir = new File(str);
        this.updateContent = new File(str, str2);
        this.updateVersion = new File(new File(str, str3), str4);
    }

    public void install(InputStream inputStream, int i) {
        writeUpdate(this.updateDir, this.updateContent, inputStream);
        writeUpdate(
                this.updateDir,
                this.updateVersion,
                new ByteArrayInputStream(Long.toString(i).getBytes()));
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(final Context context, final Intent intent) {
        new Thread() { // from class: com.android.server.updates.ConfigUpdateInstallReceiver.1
            @Override // java.lang.Thread, java.lang.Runnable
            public final void run() {
                int i;
                byte[] bArr;
                String hexString;
                try {
                    ConfigUpdateInstallReceiver configUpdateInstallReceiver =
                            ConfigUpdateInstallReceiver.this;
                    Intent intent2 = intent;
                    configUpdateInstallReceiver.getClass();
                    String stringExtra = intent2.getStringExtra("VERSION");
                    if (stringExtra == null) {
                        throw new IllegalStateException(
                                "Missing required version number, ignoring.");
                    }
                    int parseInt = Integer.parseInt(stringExtra.trim());
                    ConfigUpdateInstallReceiver configUpdateInstallReceiver2 =
                            ConfigUpdateInstallReceiver.this;
                    Intent intent3 = intent;
                    configUpdateInstallReceiver2.getClass();
                    String stringExtra2 = intent3.getStringExtra("REQUIRED_HASH");
                    if (stringExtra2 == null) {
                        throw new IllegalStateException(
                                "Missing required previous hash, ignoring.");
                    }
                    String trim = stringExtra2.trim();
                    ConfigUpdateInstallReceiver configUpdateInstallReceiver3 =
                            ConfigUpdateInstallReceiver.this;
                    configUpdateInstallReceiver3.getClass();
                    try {
                        i =
                                Integer.parseInt(
                                        IoUtils.readFileAsString(
                                                        configUpdateInstallReceiver3.updateVersion
                                                                .getCanonicalPath())
                                                .trim());
                    } catch (IOException unused) {
                        Slog.i(
                                "ConfigUpdateInstallReceiver",
                                "Couldn't find current metadata, assuming first update");
                        i = 0;
                    }
                    ConfigUpdateInstallReceiver configUpdateInstallReceiver4 =
                            ConfigUpdateInstallReceiver.this;
                    configUpdateInstallReceiver4.getClass();
                    try {
                        bArr =
                                IoUtils.readFileAsByteArray(
                                        configUpdateInstallReceiver4.updateContent
                                                .getCanonicalPath());
                    } catch (IOException unused2) {
                        Slog.i(
                                "ConfigUpdateInstallReceiver",
                                "Failed to read current content, assuming first update!");
                        bArr = null;
                    }
                    if (bArr == null) {
                        hexString = "0";
                    } else {
                        try {
                            hexString =
                                    HexDump.toHexString(
                                            MessageDigest.getInstance("SHA512").digest(bArr),
                                            false);
                        } catch (NoSuchAlgorithmException e) {
                            throw new AssertionError(e);
                        }
                    }
                    if (!ConfigUpdateInstallReceiver.this.verifyVersion(i, parseInt)) {
                        Slog.i(
                                "ConfigUpdateInstallReceiver",
                                "Not installing, new version is <= current version");
                        return;
                    }
                    ConfigUpdateInstallReceiver.this.getClass();
                    if (!(trim.equals("NONE") ? true : hexString.equals(trim))) {
                        EventLog.writeEvent(51300, "Current hash did not match required value");
                        return;
                    }
                    Slog.i("ConfigUpdateInstallReceiver", "Found new update, installing...");
                    ConfigUpdateInstallReceiver configUpdateInstallReceiver5 =
                            ConfigUpdateInstallReceiver.this;
                    Context context2 = context;
                    Intent intent4 = intent;
                    configUpdateInstallReceiver5.getClass();
                    Uri data = intent4.getData();
                    if (data == null) {
                        throw new IllegalStateException("Missing required content path, ignoring.");
                    }
                    Binder.allowBlockingForCurrentThread();
                    try {
                        BufferedInputStream bufferedInputStream =
                                new BufferedInputStream(
                                        context2.getContentResolver().openInputStream(data));
                        try {
                            ConfigUpdateInstallReceiver.this.install(bufferedInputStream, parseInt);
                            bufferedInputStream.close();
                            Slog.i("ConfigUpdateInstallReceiver", "Installation successful");
                            ConfigUpdateInstallReceiver.this.postInstall(context);
                        } finally {
                        }
                    } finally {
                        Binder.defaultBlockingForCurrentThread();
                    }
                } catch (Exception e2) {
                    Slog.e("ConfigUpdateInstallReceiver", "Could not update content!", e2);
                    String exc = e2.toString();
                    if (exc.length() > 100) {
                        exc = exc.substring(0, 99);
                    }
                    EventLog.writeEvent(51300, exc);
                }
            }
        }.start();
    }

    public void postInstall(Context context) {}

    public boolean verifyVersion(int i, int i2) {
        return i < i2;
    }

    public final void writeUpdate(File file, File file2, InputStream inputStream) {
        FileOutputStream fileOutputStream;
        File file3 = null;
        try {
            File parentFile = file2.getParentFile();
            parentFile.mkdirs();
            if (!parentFile.exists()) {
                throw new IOException(
                        "Failed to create directory " + parentFile.getCanonicalPath());
            }
            while (!parentFile.equals(this.updateDir)) {
                parentFile.setExecutable(true, false);
                parentFile = parentFile.getParentFile();
            }
            File createTempFile = File.createTempFile("journal", "", file);
            try {
                createTempFile.setReadable(true, false);
                fileOutputStream = new FileOutputStream(createTempFile);
            } catch (Throwable th) {
                file3 = createTempFile;
                th = th;
                fileOutputStream = null;
            }
            try {
                Streams.copy(inputStream, fileOutputStream);
                fileOutputStream.getFD().sync();
                if (createTempFile.renameTo(file2)) {
                    createTempFile.delete();
                    IoUtils.closeQuietly(fileOutputStream);
                } else {
                    throw new IOException(
                            "Failed to atomically rename " + file2.getCanonicalPath());
                }
            } catch (Throwable th2) {
                file3 = createTempFile;
                th = th2;
                if (file3 != null) {
                    file3.delete();
                }
                IoUtils.closeQuietly(fileOutputStream);
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            fileOutputStream = null;
        }
    }
}
