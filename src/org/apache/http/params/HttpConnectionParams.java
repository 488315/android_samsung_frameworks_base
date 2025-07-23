package org.apache.http.params;

@Deprecated
/* loaded from: classes6.dex */
public final class HttpConnectionParams implements CoreConnectionPNames {
    private HttpConnectionParams() {
    }

    public static int getSoTimeout(HttpParams httpParams) {
        if (httpParams == null) {
            throw new IllegalArgumentException("HTTP parameters may not be null");
        }
        return httpParams.getIntParameter(CoreConnectionPNames.SO_TIMEOUT, 0);
    }

    public static void setSoTimeout(HttpParams httpParams, int i) {
        if (httpParams == null) {
            throw new IllegalArgumentException("HTTP parameters may not be null");
        }
        httpParams.setIntParameter(CoreConnectionPNames.SO_TIMEOUT, i);
    }

    public static boolean getTcpNoDelay(HttpParams httpParams) {
        if (httpParams == null) {
            throw new IllegalArgumentException("HTTP parameters may not be null");
        }
        return httpParams.getBooleanParameter(CoreConnectionPNames.TCP_NODELAY, true);
    }

    public static void setTcpNoDelay(HttpParams httpParams, boolean z) {
        if (httpParams == null) {
            throw new IllegalArgumentException("HTTP parameters may not be null");
        }
        httpParams.setBooleanParameter(CoreConnectionPNames.TCP_NODELAY, z);
    }

    public static int getSocketBufferSize(HttpParams httpParams) {
        if (httpParams == null) {
            throw new IllegalArgumentException("HTTP parameters may not be null");
        }
        return httpParams.getIntParameter(CoreConnectionPNames.SOCKET_BUFFER_SIZE, -1);
    }

    public static void setSocketBufferSize(HttpParams httpParams, int i) {
        if (httpParams == null) {
            throw new IllegalArgumentException("HTTP parameters may not be null");
        }
        httpParams.setIntParameter(CoreConnectionPNames.SOCKET_BUFFER_SIZE, i);
    }

    public static int getLinger(HttpParams httpParams) {
        if (httpParams == null) {
            throw new IllegalArgumentException("HTTP parameters may not be null");
        }
        return httpParams.getIntParameter(CoreConnectionPNames.SO_LINGER, -1);
    }

    public static void setLinger(HttpParams httpParams, int i) {
        if (httpParams == null) {
            throw new IllegalArgumentException("HTTP parameters may not be null");
        }
        httpParams.setIntParameter(CoreConnectionPNames.SO_LINGER, i);
    }

    public static int getConnectionTimeout(HttpParams httpParams) {
        if (httpParams == null) {
            throw new IllegalArgumentException("HTTP parameters may not be null");
        }
        return httpParams.getIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 0);
    }

    public static void setConnectionTimeout(HttpParams httpParams, int i) {
        if (httpParams == null) {
            throw new IllegalArgumentException("HTTP parameters may not be null");
        }
        httpParams.setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, i);
    }

    public static boolean isStaleCheckingEnabled(HttpParams httpParams) {
        if (httpParams == null) {
            throw new IllegalArgumentException("HTTP parameters may not be null");
        }
        return httpParams.getBooleanParameter(CoreConnectionPNames.STALE_CONNECTION_CHECK, true);
    }

    public static void setStaleCheckingEnabled(HttpParams httpParams, boolean z) {
        if (httpParams == null) {
            throw new IllegalArgumentException("HTTP parameters may not be null");
        }
        httpParams.setBooleanParameter(CoreConnectionPNames.STALE_CONNECTION_CHECK, z);
    }
}
