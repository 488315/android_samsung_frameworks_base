package com.samsung.android.knox.dar;

import android.p009os.Binder;
import android.p009os.ServiceManager;
import android.util.Log;
import com.samsung.android.knox.dar.IDarManagerService;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/* loaded from: classes5.dex */
public class VirtualLockUtils {
    private static final String BASE_DIR = "/data/system/users";
    public static final int DEFAULT_TRY_RANGE = 10;
    public static final int HALF_USER_ID_RANGE = 500;
    public static final int MIN_VIRTUAL_USER_ID = 1000;
    private static final String TAG = "VirtualLockUtils";
    public static final String VL_RESERVED_USERID_KEY = "vl.reserved.userid";
    public static final String VL_RST_TOKEN_HANDLE_KEY = "vl.rst.token.handle";
    private IDarManagerService mDarManagerService;

    private Optional<IDarManagerService> getDarManagerService() {
        if (this.mDarManagerService == null) {
            IDarManagerService service = IDarManagerService.Stub.asInterface(ServiceManager.getService("dar"));
            this.mDarManagerService = service;
        }
        IDarManagerService service2 = this.mDarManagerService;
        return Optional.ofNullable(service2);
    }

    public static boolean isVirtualUserId(int userId) {
        return userId >= 1000;
    }

    public int reserveUserIdForSystem() {
        return ((Integer) getDarManagerService().map(new Function() { // from class: com.samsung.android.knox.dar.VirtualLockUtils$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return VirtualLockUtils.lambda$reserveUserIdForSystem$0((IDarManagerService) obj);
            }
        }).orElse(-10000)).intValue();
    }

    static /* synthetic */ Integer lambda$reserveUserIdForSystem$0(IDarManagerService s) {
        try {
            return Integer.valueOf(s.reserveUserIdForSystem());
        } catch (Exception e) {
            Log.m97e(TAG, "failed to reserve user id for system", e);
            e.printStackTrace();
            return -10000;
        }
    }

    public int getReservedUserIdForSystem() {
        return ((Integer) getDarManagerService().map(new Function() { // from class: com.samsung.android.knox.dar.VirtualLockUtils$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return VirtualLockUtils.lambda$getReservedUserIdForSystem$1((IDarManagerService) obj);
            }
        }).orElse(-10000)).intValue();
    }

    static /* synthetic */ Integer lambda$getReservedUserIdForSystem$1(IDarManagerService s) {
        try {
            return Integer.valueOf(s.getReservedUserIdForSystem());
        } catch (Exception e) {
            Log.m97e(TAG, "failed to get reserved user id for system", e);
            e.printStackTrace();
            return -10000;
        }
    }

    public int getAvailableUserId() {
        return ((Integer) getDarManagerService().map(new Function() { // from class: com.samsung.android.knox.dar.VirtualLockUtils$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return VirtualLockUtils.lambda$getAvailableUserId$2((IDarManagerService) obj);
            }
        }).orElse(-10000)).intValue();
    }

    static /* synthetic */ Integer lambda$getAvailableUserId$2(IDarManagerService s) {
        try {
            return Integer.valueOf(s.getAvailableUserId());
        } catch (Exception e) {
            Log.m97e(TAG, "failed to get reserved user id for system", e);
            e.printStackTrace();
            return -10000;
        }
    }

    private List<Integer> getVirtualUserList() {
        List<Integer> ret = new ArrayList<>();
        long identity = Binder.clearCallingIdentity();
        try {
            File[] files = new File(BASE_DIR).listFiles();
            if (files == null) {
                return ret;
            }
            for (File f : files) {
                if (f.isDirectory()) {
                    int userId = -1;
                    try {
                        userId = Integer.parseInt(f.getName());
                    } catch (NumberFormatException e) {
                    }
                    if (userId >= 1000) {
                        ret.add(Integer.valueOf(userId));
                    }
                }
            }
            return ret;
        } finally {
            Binder.restoreCallingIdentity(identity);
        }
    }

    public int[] getVirtualUsers() {
        try {
            List<Integer> userList = getVirtualUserList();
            int[] ret = new int[userList.size()];
            for (int i = 0; i < ret.length; i++) {
                ret[i] = userList.get(i).intValue();
            }
            return ret;
        } catch (Exception e) {
            Log.m97e(TAG, "failed to get virtual users", e);
            e.printStackTrace();
            return new int[0];
        }
    }

    public boolean setResetPasswordToken(final byte[] token, final int userId) {
        if (isVirtualUserId(userId)) {
            return ((Boolean) getDarManagerService().map(new Function() { // from class: com.samsung.android.knox.dar.VirtualLockUtils$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return VirtualLockUtils.lambda$setResetPasswordToken$3(token, userId, (IDarManagerService) obj);
                }
            }).orElse(false)).booleanValue();
        }
        return false;
    }

    static /* synthetic */ Boolean lambda$setResetPasswordToken$3(byte[] token, int userId, IDarManagerService s) {
        try {
            return Boolean.valueOf(s.setResetPasswordToken(token, userId));
        } catch (Exception e) {
            Log.m97e(TAG, "failed to set reset token", e);
            e.printStackTrace();
            return false;
        }
    }

    public boolean clearResetPasswordToken(final int userId) {
        if (isVirtualUserId(userId)) {
            return ((Boolean) getDarManagerService().map(new Function() { // from class: com.samsung.android.knox.dar.VirtualLockUtils$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return VirtualLockUtils.lambda$clearResetPasswordToken$4(userId, (IDarManagerService) obj);
                }
            }).orElse(false)).booleanValue();
        }
        return false;
    }

    static /* synthetic */ Boolean lambda$clearResetPasswordToken$4(int userId, IDarManagerService s) {
        try {
            return Boolean.valueOf(s.clearResetPasswordToken(userId));
        } catch (Exception e) {
            Log.m97e(TAG, "failed to clear reset token", e);
            e.printStackTrace();
            return false;
        }
    }

    public boolean isResetPasswordTokenActive(final int userId) {
        if (isVirtualUserId(userId)) {
            return ((Boolean) getDarManagerService().map(new Function() { // from class: com.samsung.android.knox.dar.VirtualLockUtils$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return VirtualLockUtils.lambda$isResetPasswordTokenActive$5(userId, (IDarManagerService) obj);
                }
            }).orElse(false)).booleanValue();
        }
        return false;
    }

    static /* synthetic */ Boolean lambda$isResetPasswordTokenActive$5(int userId, IDarManagerService s) {
        try {
            return Boolean.valueOf(s.isResetPasswordTokenActive(userId));
        } catch (Exception e) {
            Log.m97e(TAG, "failed to check reset token active", e);
            e.printStackTrace();
            return false;
        }
    }

    public boolean resetPasswordWithToken(final String password, final byte[] token, final int userId) {
        if (isVirtualUserId(userId)) {
            return ((Boolean) getDarManagerService().map(new Function() { // from class: com.samsung.android.knox.dar.VirtualLockUtils$$ExternalSyntheticLambda3
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return VirtualLockUtils.lambda$resetPasswordWithToken$6(password, token, userId, (IDarManagerService) obj);
                }
            }).orElse(false)).booleanValue();
        }
        return false;
    }

    static /* synthetic */ Boolean lambda$resetPasswordWithToken$6(String password, byte[] token, int userId, IDarManagerService s) {
        try {
            return Boolean.valueOf(s.resetPasswordWithToken(password, token, userId));
        } catch (Exception e) {
            Log.m97e(TAG, "failed to reset passwrod with token", e);
            e.printStackTrace();
            return false;
        }
    }
}
