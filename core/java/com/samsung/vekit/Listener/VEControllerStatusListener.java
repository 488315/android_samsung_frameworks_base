package com.samsung.vekit.Listener;

import com.samsung.vekit.Common.Type.EventType;

public interface VEControllerStatusListener extends NativeInterfaceListener {
    void onEvent(EventType eventType);
}
