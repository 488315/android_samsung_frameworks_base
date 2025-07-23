package com.airbnb.lottie.network;

import com.airbnb.lottie.utils.Logger;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class DefaultLottieFetchResult implements Closeable {
    public final HttpURLConnection connection;

    public DefaultLottieFetchResult(HttpURLConnection httpURLConnection) {
        this.connection = httpURLConnection;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        this.connection.disconnect();
    }

    public final String error() {
        boolean z = false;
        try {
            if (this.connection.getResponseCode() / 100 == 2) {
                z = true;
            }
        } catch (IOException unused) {
        }
        if (z) {
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
                        } catch (Exception unused2) {
                        }
                    }
                } finally {
                    try {
                        bufferedReader.close();
                    } catch (Exception unused3) {
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
}
