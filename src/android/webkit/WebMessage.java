package android.webkit;

/* loaded from: classes4.dex */
public class WebMessage {
    private String mData;
    private WebMessagePort[] mPorts;

    public WebMessage(String str) {
        this.mData = str;
    }

    public WebMessage(String str, WebMessagePort[] webMessagePortArr) {
        this.mData = str;
        this.mPorts = webMessagePortArr;
    }

    public String getData() {
        return this.mData;
    }

    public WebMessagePort[] getPorts() {
        return this.mPorts;
    }
}
