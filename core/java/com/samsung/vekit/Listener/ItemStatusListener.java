package com.samsung.vekit.Listener;

import com.samsung.vekit.Common.Type.ItemErrorType;

public interface ItemStatusListener extends NativeInterfaceListener {
    void onError(ItemErrorType itemErrorType);
}
