package android.net;

import android.util.Log;
import java.io.IOException;
import java.net.InetSocketAddress;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/* loaded from: classes3.dex */
public class PrivateDnsConnectivityChecker {
    private static final int CONNECTION_TIMEOUT_MS = 5000;
    private static final int PRIVATE_DNS_PORT = 853;
    private static final String TAG = "NetworkUtils";

    private PrivateDnsConnectivityChecker() {
    }

    public static boolean canConnectToPrivateDnsServer(String str) {
        SocketFactory socketFactory = SSLSocketFactory.getDefault();
        TrafficStats.setThreadStatsTagApp();
        try {
            SSLSocket sSLSocket = (SSLSocket) socketFactory.createSocket();
            try {
                sSLSocket.setSoTimeout(5000);
                sSLSocket.connect(new InetSocketAddress(str, 853));
                if (!sSLSocket.isConnected()) {
                    Log.w(TAG, String.format("Connection to %s failed.", str));
                    if (sSLSocket != null) {
                        sSLSocket.close();
                    }
                    return false;
                }
                sSLSocket.startHandshake();
                Log.w(TAG, String.format("TLS handshake to %s succeeded.", str));
                if (sSLSocket != null) {
                    sSLSocket.close();
                }
                return true;
            } finally {
            }
        } catch (IOException e) {
            Log.w(TAG, String.format("TLS handshake to %s failed.", str), e);
            return false;
        }
    }
}
