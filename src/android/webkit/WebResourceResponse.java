package android.webkit;

import android.annotation.SystemApi;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.Map;

/* loaded from: classes4.dex */
public class WebResourceResponse {
    private String mEncoding;
    private boolean mImmutable;
    private InputStream mInputStream;
    private String mMimeType;
    private String mReasonPhrase;
    private Map<String, String> mResponseHeaders;
    private int mStatusCode;

    public WebResourceResponse(String str, String str2, InputStream inputStream) {
        this.mMimeType = str;
        this.mEncoding = str2;
        setData(inputStream);
    }

    public WebResourceResponse(String str, String str2, int i, String str3, Map<String, String> map, InputStream inputStream) {
        this(str, str2, inputStream);
        setStatusCodeAndReasonPhrase(i, str3);
        setResponseHeaders(map);
    }

    public void setMimeType(String str) {
        checkImmutable();
        this.mMimeType = str;
    }

    public String getMimeType() {
        return this.mMimeType;
    }

    public void setEncoding(String str) {
        checkImmutable();
        this.mEncoding = str;
    }

    public String getEncoding() {
        return this.mEncoding;
    }

    public void setStatusCodeAndReasonPhrase(int i, String str) {
        checkImmutable();
        if (i < 100) {
            throw new IllegalArgumentException("statusCode can't be less than 100.");
        }
        if (i > 599) {
            throw new IllegalArgumentException("statusCode can't be greater than 599.");
        }
        if (i > 299 && i < 400) {
            throw new IllegalArgumentException("statusCode can't be in the [300, 399] range.");
        }
        if (str == null) {
            throw new IllegalArgumentException("reasonPhrase can't be null.");
        }
        if (str.trim().isEmpty()) {
            throw new IllegalArgumentException("reasonPhrase can't be empty.");
        }
        for (int i2 = 0; i2 < str.length(); i2++) {
            if (str.charAt(i2) > 127) {
                throw new IllegalArgumentException("reasonPhrase can't contain non-ASCII characters.");
            }
        }
        this.mStatusCode = i;
        this.mReasonPhrase = str;
    }

    public int getStatusCode() {
        return this.mStatusCode;
    }

    public String getReasonPhrase() {
        return this.mReasonPhrase;
    }

    public void setResponseHeaders(Map<String, String> map) {
        checkImmutable();
        this.mResponseHeaders = map;
    }

    public Map<String, String> getResponseHeaders() {
        return this.mResponseHeaders;
    }

    public void setData(InputStream inputStream) {
        checkImmutable();
        if (inputStream != null && StringBufferInputStream.class.isAssignableFrom(inputStream.getClass())) {
            throw new IllegalArgumentException("StringBufferInputStream is deprecated and must not be passed to a WebResourceResponse");
        }
        this.mInputStream = inputStream;
    }

    public InputStream getData() {
        return this.mInputStream;
    }

    @SystemApi
    public WebResourceResponse(boolean z, String str, String str2, int i, String str3, Map<String, String> map, InputStream inputStream) {
        this.mImmutable = z;
        this.mMimeType = str;
        this.mEncoding = str2;
        this.mStatusCode = i;
        this.mReasonPhrase = str3;
        this.mResponseHeaders = map;
        this.mInputStream = inputStream;
    }

    private void checkImmutable() {
        if (this.mImmutable) {
            throw new IllegalStateException("This WebResourceResponse instance is immutable");
        }
    }
}
