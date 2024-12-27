package com.samsung.android.ims;

public interface SemEpdgListener {
    void onEpdgAvailable(int i, boolean z, int i2);

    void onEpdgShowPopup(int i, int i2);

    void onHandoverResult(int i, int i2, int i3, String str);

    void onIpsecConnection(int i, String str, int i2, int i3);

    void onIpsecDisconnection(int i, String str);
}
