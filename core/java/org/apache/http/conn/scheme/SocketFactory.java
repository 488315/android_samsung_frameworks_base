package org.apache.http.conn.scheme;

import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.params.HttpParams;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

@Deprecated
public interface SocketFactory {
    Socket connectSocket(
            Socket socket,
            String str,
            int i,
            InetAddress inetAddress,
            int i2,
            HttpParams httpParams)
            throws IOException, UnknownHostException, ConnectTimeoutException;

    Socket createSocket() throws IOException;

    boolean isSecure(Socket socket) throws IllegalArgumentException;
}
