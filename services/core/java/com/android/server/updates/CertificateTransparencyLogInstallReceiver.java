package com.android.server.updates;

import android.os.FileUtils;
import android.system.ErrnoException;
import android.system.Os;
import android.util.Base64;
import android.util.Slog;

import com.android.internal.util.HexDump;

import libcore.io.Streams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public class CertificateTransparencyLogInstallReceiver extends ConfigUpdateInstallReceiver {
    public CertificateTransparencyLogInstallReceiver() {
        super("/data/misc/keychain/trusted_ct_logs/", "ct_logs", "metadata/", "version");
    }

    public static void installLog(JSONObject jSONObject, File file) {
        try {
            try {
                File file2 =
                        new File(
                                file,
                                HexDump.toHexString(
                                        MessageDigest.getInstance("SHA-256")
                                                .digest(
                                                        Base64.decode(
                                                                jSONObject.getString("key"), 0)),
                                        false));
                OutputStreamWriter outputStreamWriter =
                        new OutputStreamWriter(new FileOutputStream(file2), StandardCharsets.UTF_8);
                try {
                    writeLogEntry(outputStreamWriter, "key", jSONObject.getString("key"));
                    writeLogEntry(outputStreamWriter, "url", jSONObject.getString("url"));
                    writeLogEntry(
                            outputStreamWriter, "description", jSONObject.getString("description"));
                    outputStreamWriter.close();
                    if (file2.setReadable(true, false)) {
                        return;
                    }
                    throw new IOException(
                            "Failed to set permissions on " + file2.getCanonicalPath());
                } finally {
                }
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        } catch (JSONException e2) {
            throw new IOException("Failed to parse log", e2);
        }
    }

    public static void writeLogEntry(
            OutputStreamWriter outputStreamWriter, String str, String str2) {
        outputStreamWriter.write(str + ":" + str2 + "\n");
    }

    public final void deleteOldLogDirectories() {
        if (this.updateDir.exists()) {
            final File canonicalFile = new File(this.updateDir, "current").getCanonicalFile();
            for (File file :
                    this.updateDir.listFiles(
                            new FileFilter() { // from class:
                                               // com.android.server.updates.CertificateTransparencyLogInstallReceiver.1
                                @Override // java.io.FileFilter
                                public final boolean accept(File file2) {
                                    return !canonicalFile.equals(file2)
                                            && file2.getName().startsWith("logs-");
                                }
                            })) {
                FileUtils.deleteContentsAndDir(file);
            }
        }
    }

    @Override // com.android.server.updates.ConfigUpdateInstallReceiver
    public final void install(InputStream inputStream, int i) {
        this.updateDir.mkdir();
        if (!this.updateDir.isDirectory()) {
            throw new IOException("Unable to make directory " + this.updateDir.getCanonicalPath());
        }
        if (!this.updateDir.setReadable(true, false)) {
            throw new IOException(
                    "Unable to set permissions on " + this.updateDir.getCanonicalPath());
        }
        File file = new File(this.updateDir, "current");
        File file2 = new File(this.updateDir, "logs-" + String.valueOf(i));
        if (file2.exists()) {
            if (file2.getCanonicalPath().equals(file.getCanonicalPath())) {
                writeUpdate(
                        this.updateDir,
                        this.updateVersion,
                        new ByteArrayInputStream(Long.toString(i).getBytes()));
                deleteOldLogDirectories();
                return;
            }
            FileUtils.deleteContentsAndDir(file2);
        }
        try {
            file2.mkdir();
            if (!file2.isDirectory()) {
                throw new IOException("Unable to make directory " + file2.getCanonicalPath());
            }
            if (!file2.setReadable(true, false)) {
                throw new IOException("Failed to set " + file2.getCanonicalPath() + " readable");
            }
            try {
                JSONArray jSONArray =
                        new JSONObject(
                                        new String(
                                                Streams.readFullyNoClose(inputStream),
                                                StandardCharsets.UTF_8))
                                .getJSONArray("logs");
                for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                    installLog(jSONArray.getJSONObject(i2), file2);
                }
                File file3 = new File(this.updateDir, "new_symlink");
                try {
                    Os.symlink(file2.getCanonicalPath(), file3.getCanonicalPath());
                    file3.renameTo(file.getAbsoluteFile());
                    Slog.i(
                            "CTLogInstallReceiver",
                            "CT log directory updated to " + file2.getAbsolutePath());
                    writeUpdate(
                            this.updateDir,
                            this.updateVersion,
                            new ByteArrayInputStream(Long.toString((long) i).getBytes()));
                    deleteOldLogDirectories();
                } catch (ErrnoException e) {
                    throw new IOException("Failed to create symlink", e);
                }
            } catch (JSONException e2) {
                throw new IOException("Failed to parse logs", e2);
            }
        } catch (IOException | RuntimeException e3) {
            FileUtils.deleteContentsAndDir(file2);
            throw e3;
        }
    }
}
