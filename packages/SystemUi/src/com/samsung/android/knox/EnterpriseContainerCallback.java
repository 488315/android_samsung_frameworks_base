package com.samsung.android.knox;

import android.os.Bundle;
import com.samsung.android.knox.IEnterpriseContainerCallback;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract class EnterpriseContainerCallback extends IEnterpriseContainerCallback.Stub {
    public static final String APKS_COUNT = "apksCount";
    public static final int CONTAINER_CANCELLED = 1016;
    public static final int CONTAINER_CHANGE_PWD_FAILED = 1013;
    public static final int CONTAINER_CHANGE_PWD_SUCCESSFUL = 1012;
    public static final String CONTAINER_ID = "containerid";
    public static final int CONTAINER_MOUNT_STATUS = 1010;
    public static final int CONTAINER_PACKAGE_INFORMATION = 1011;
    public static final int CONTAINER_PACKAGE_INSTALL_FAILURE = 1007;
    public static final int CONTAINER_PACKAGE_INSTALL_SUCCESS = 1006;
    public static final int CONTAINER_PACKAGE_UNINSTALL_FAILURE = 1009;
    public static final int CONTAINER_PACKAGE_UNINSTALL_SUCCESS = 1008;
    public static final int CONTAINER_REMOVED = 1003;
    public static final int CONTAINER_REMOVE_UNMOUNT_FAILURE = 1005;
    public static final int CONTAINER_SETUP_BASE = 1000;
    public static final int CONTAINER_SETUP_FAILURE = 1002;
    public static final int CONTAINER_SETUP_SUCCESS = 1001;
    public static final int CONTAINER_VERIFY_PWD_FAILED = 1015;
    public static final int CONTAINER_VERIFY_PWD_SUCCESSFUL = 1014;
    public static final String PACKAGE_INSTALLATION_STATUS = "packageInstallStatus";
    public static final String PACKAGE_MANAGER_ERROR_CODE = "pmerrorcode";
    public static final String PACKAGE_NAME = "packageName";
    public static final String PRIMARY_MOUNT_STATUS = "primaryMountStatus";
    public static final String REQUEST_ID = "requestid";
    public static final String SECONDARY_MOUNT_STATUS = "secondaryMountStatus";

    @Override // com.samsung.android.knox.IEnterpriseContainerCallback
    public abstract void updateStatus(int i, Bundle bundle);
}
