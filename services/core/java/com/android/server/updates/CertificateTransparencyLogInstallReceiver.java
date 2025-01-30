package com.android.server.updates;

import android.os.FileUtils;
import android.system.ErrnoException;
import android.system.Os;
import android.util.Base64;
import android.util.Slog;
import com.android.internal.util.HexDump;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
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
import libcore.io.Streams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class CertificateTransparencyLogInstallReceiver extends ConfigUpdateInstallReceiver {
    public CertificateTransparencyLogInstallReceiver() {
        super("/data/misc/keychain/trusted_ct_logs/", "ct_logs", "metadata/", "version");
    }

    @Override // com.android.server.updates.ConfigUpdateInstallReceiver
    public void install(InputStream inputStream, int i) {
        this.updateDir.mkdir();
        if (!this.updateDir.isDirectory()) {
            throw new IOException("Unable to make directory " + this.updateDir.getCanonicalPath());
        }
        if (!this.updateDir.setReadable(true, false)) {
            throw new IOException("Unable to set permissions on " + this.updateDir.getCanonicalPath());
        }
        File file = new File(this.updateDir, "current");
        File file2 = new File(this.updateDir, "logs-" + String.valueOf(i));
        if (file2.exists()) {
            if (file2.getCanonicalPath().equals(file.getCanonicalPath())) {
                writeUpdate(this.updateDir, this.updateVersion, new ByteArrayInputStream(Long.toString(i).getBytes()));
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
                JSONArray jSONArray = new JSONObject(new String(Streams.readFullyNoClose(inputStream), StandardCharsets.UTF_8)).getJSONArray("logs");
                for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                    installLog(file2, jSONArray.getJSONObject(i2));
                }
                File file3 = new File(this.updateDir, "new_symlink");
                try {
                    Os.symlink(file2.getCanonicalPath(), file3.getCanonicalPath());
                    file3.renameTo(file.getAbsoluteFile());
                    Slog.i("CTLogInstallReceiver", "CT log directory updated to " + file2.getAbsolutePath());
                    writeUpdate(this.updateDir, this.updateVersion, new ByteArrayInputStream(Long.toString((long) i).getBytes()));
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

    public final void installLog(File file, JSONObject jSONObject) {
        try {
            File file2 = new File(file, getLogFileName(jSONObject.getString("key")));
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file2), StandardCharsets.UTF_8);
            try {
                writeLogEntry(outputStreamWriter, "key", jSONObject.getString("key"));
                writeLogEntry(outputStreamWriter, "url", jSONObject.getString("url"));
                writeLogEntry(outputStreamWriter, "description", jSONObject.getString("description"));
                outputStreamWriter.close();
                if (file2.setReadable(true, false)) {
                    return;
                }
                throw new IOException("Failed to set permissions on " + file2.getCanonicalPath());
            } finally {
            }
        } catch (JSONException e) {
            throw new IOException("Failed to parse log", e);
        }
    }

    public final String getLogFileName(String str) {
        try {
            return HexDump.toHexString(MessageDigest.getInstance("SHA-256").digest(Base64.decode(str, 0)), false);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public final void writeLogEntry(OutputStreamWriter outputStreamWriter, String str, String str2) {
        outputStreamWriter.write(str + XmlUtils.STRING_ARRAY_SEPARATOR + str2 + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
    }

    public final void deleteOldLogDirectories() {
        if (this.updateDir.exists()) {
            final File canonicalFile = new File(this.updateDir, "current").getCanonicalFile();
            for (File file : this.updateDir.listFiles(new FileFilter() { // from class: com.android.server.updates.CertificateTransparencyLogInstallReceiver.1
                @Override // java.io.FileFilter
                public boolean accept(File file2) {
                    return !canonicalFile.equals(file2) && file2.getName().startsWith("logs-");
                }
            })) {
                FileUtils.deleteContentsAndDir(file);
            }
        }
    }
}
