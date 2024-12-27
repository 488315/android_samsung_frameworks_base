package com.samsung.vekit.Listener;

import com.samsung.vekit.Common.Object.PcmInfo;

import java.util.HashMap;

public interface PcmInfoListener {
    void onUpdate(HashMap<String, PcmInfo> hashMap);
}
