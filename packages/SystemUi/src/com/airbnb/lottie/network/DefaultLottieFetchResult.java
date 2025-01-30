package com.airbnb.lottie.network;

import com.airbnb.lottie.utils.Logger;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DefaultLottieFetchResult implements Closeable {
    public final HttpURLConnection connection;

    public DefaultLottieFetchResult(HttpURLConnection httpURLConnection) {
        this.connection = httpURLConnection;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        this.connection.disconnect();
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0010 A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0012 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final String error() {
        boolean z;
        if (this.connection.getResponseCode() / 100 == 2) {
            z = true;
            if (!z) {
                return null;
            }
            try {
                StringBuilder sb = new StringBuilder();
                sb.append("Unable to fetch ");
                sb.append(this.connection.getURL());
                sb.append(". Failed with ");
                sb.append(this.connection.getResponseCode());
                sb.append("\n");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.connection.getErrorStream()));
                StringBuilder sb2 = new StringBuilder();
                while (true) {
                    try {
                        String readLine = bufferedReader.readLine();
                        if (readLine != null) {
                            sb2.append(readLine);
                            sb2.append('\n');
                        } else {
                            try {
                                break;
                            } catch (Exception unused) {
                            }
                        }
                    } finally {
                        try {
                            bufferedReader.close();
                        } catch (Exception unused2) {
                        }
                    }
                }
                sb.append(sb2.toString());
                return sb.toString();
            } catch (IOException e) {
                Logger.warning("get error failed ", e);
                return e.getMessage();
            }
        }
        z = false;
        if (!z) {
        }
    }
}
