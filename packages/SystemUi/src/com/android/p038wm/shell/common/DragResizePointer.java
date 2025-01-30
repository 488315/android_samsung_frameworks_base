package com.android.p038wm.shell.common;

import com.samsung.android.knox.EnterpriseContainerCallback;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DragResizePointer {
    public static int convertDexPointerIconType(int i) {
        if (i == 1000) {
            return 10121;
        }
        switch (i) {
            case EnterpriseContainerCallback.CONTAINER_VERIFY_PWD_SUCCESSFUL /* 1014 */:
                return 10122;
            case EnterpriseContainerCallback.CONTAINER_VERIFY_PWD_FAILED /* 1015 */:
                return 10123;
            case EnterpriseContainerCallback.CONTAINER_CANCELLED /* 1016 */:
                return 10125;
            case 1017:
                return 10124;
            default:
                return i;
        }
    }

    public static int convertStylusIconType(int i) {
        if (i == 20001) {
            return 10121;
        }
        switch (i) {
            case EnterpriseContainerCallback.CONTAINER_VERIFY_PWD_SUCCESSFUL /* 1014 */:
                return 20006;
            case EnterpriseContainerCallback.CONTAINER_VERIFY_PWD_FAILED /* 1015 */:
                return 20007;
            case EnterpriseContainerCallback.CONTAINER_CANCELLED /* 1016 */:
                return 20009;
            case 1017:
                return 20008;
            default:
                return i;
        }
    }
}
