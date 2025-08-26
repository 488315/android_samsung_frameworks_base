package com.samsung.android.knox.dar;

import android.os.Binder;
import android.os.ServiceManager;
import android.util.Log;
import com.samsung.android.knox.dar.IDarManagerService;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/* loaded from: classes6.dex */
public class VirtualLockUtils {
    private static final String BASE_DIR = "/data/system/users";
    public static final int DEFAULT_TRY_RANGE = 10;
    public static final int HALF_USER_ID_RANGE = 500;
    public static final int MIN_VIRTUAL_USER_ID = 1000;
    private static final String TAG = "VirtualLockUtils";
    public static final String VL_RESERVED_USERID_KEY = "vl.reserved.userid";
    public static final String VL_RST_TOKEN_HANDLE_KEY = "vl.rst.token.handle";
    private IDarManagerService mDarManagerService;

    public static boolean isVirtualUserId(int i) {
        return i >= 1000;
    }

    private Optional<IDarManagerService> getDarManagerService() {
        if (this.mDarManagerService == null) {
            this.mDarManagerService = IDarManagerService.Stub.asInterface(ServiceManager.getService("dar"));
        }
        return Optional.ofNullable(this.mDarManagerService);
    }

    public int reserveUserIdForSystem() {
        return ((Integer) getDarManagerService().map(new Function() { // from class: com.samsung.android.knox.dar.VirtualLockUtils$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return VirtualLockUtils.lambda$reserveUserIdForSystem$0((IDarManagerService) obj);
            }
        }).orElse(-10000)).intValue();
    }

    static /* synthetic */ Integer lambda$reserveUserIdForSystem$0(IDarManagerService iDarManagerService) {
        try {
            return Integer.valueOf(iDarManagerService.reserveUserIdForSystem());
        } catch (Exception e) {
            Log.e(TAG, "failed to reserve user id for system", e);
            e.printStackTrace();
            return -10000;
        }
    }

    public int getReservedUserIdForSystem() {
        return ((Integer) getDarManagerService().map(new Function() { // from class: com.samsung.android.knox.dar.VirtualLockUtils$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return VirtualLockUtils.lambda$getReservedUserIdForSystem$1((IDarManagerService) obj);
            }
        }).orElse(-10000)).intValue();
    }

    static /* synthetic */ Integer lambda$getReservedUserIdForSystem$1(IDarManagerService iDarManagerService) {
        try {
            return Integer.valueOf(iDarManagerService.getReservedUserIdForSystem());
        } catch (Exception e) {
            Log.e(TAG, "failed to get reserved user id for system", e);
            e.printStackTrace();
            return -10000;
        }
    }

    public int getAvailableUserId() {
        return ((Integer) getDarManagerService().map(new Function() { // from class: com.samsung.android.knox.dar.VirtualLockUtils$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return VirtualLockUtils.lambda$getAvailableUserId$2((IDarManagerService) obj);
            }
        }).orElse(-10000)).intValue();
    }

    static /* synthetic */ Integer lambda$getAvailableUserId$2(IDarManagerService iDarManagerService) {
        try {
            return Integer.valueOf(iDarManagerService.getAvailableUserId());
        } catch (Exception e) {
            Log.e(TAG, "failed to get reserved user id for system", e);
            e.printStackTrace();
            return -10000;
        }
    }

    private List<Integer> getVirtualUserList() {
        int i;
        ArrayList arrayList = new ArrayList();
        long jClearCallingIdentity = Binder.clearCallingIdentity();
        try {
            File[] fileArrListFiles = new File(BASE_DIR).listFiles();
            if (fileArrListFiles == null) {
                return arrayList;
            }
            for (File file : fileArrListFiles) {
                if (file.isDirectory()) {
                    try {
                        i = Integer.parseInt(file.getName());
                    } catch (NumberFormatException unused) {
                        i = -1;
                    }
                    if (i >= 1000) {
                        arrayList.add(Integer.valueOf(i));
                    }
                }
            }
            return arrayList;
        } finally {
            Binder.restoreCallingIdentity(jClearCallingIdentity);
        }
    }

    public int[] getVirtualUsers() {
        try {
            List<Integer> virtualUserList = getVirtualUserList();
            int size = virtualUserList.size();
            int[] iArr = new int[size];
            for (int i = 0; i < size; i++) {
                iArr[i] = virtualUserList.get(i).intValue();
            }
            return iArr;
        } catch (Exception e) {
            Log.e(TAG, "failed to get virtual users", e);
            e.printStackTrace();
            return new int[0];
        }
    }

    public boolean setResetPasswordToken(final byte[] bArr, final int i) {
        if (isVirtualUserId(i)) {
            return ((Boolean) getDarManagerService().map(new Function() { // from class: com.samsung.android.knox.dar.VirtualLockUtils$$ExternalSyntheticLambda5
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return VirtualLockUtils.lambda$setResetPasswordToken$3(bArr, i, (IDarManagerService) obj);
                }
            }).orElse(false)).booleanValue();
        }
        return false;
    }

    static /* synthetic */ Boolean lambda$setResetPasswordToken$3(byte[] bArr, int i, IDarManagerService iDarManagerService) {
        try {
            return Boolean.valueOf(iDarManagerService.setResetPasswordToken(bArr, i));
        } catch (Exception e) {
            Log.e(TAG, "failed to set reset token", e);
            e.printStackTrace();
            return false;
        }
    }

    public boolean clearResetPasswordToken(final int i) {
        if (isVirtualUserId(i)) {
            return ((Boolean) getDarManagerService().map(new Function() { // from class: com.samsung.android.knox.dar.VirtualLockUtils$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return VirtualLockUtils.lambda$clearResetPasswordToken$4(i, (IDarManagerService) obj);
                }
            }).orElse(false)).booleanValue();
        }
        return false;
    }

    static /* synthetic */ Boolean lambda$clearResetPasswordToken$4(int i, IDarManagerService iDarManagerService) {
        try {
            return Boolean.valueOf(iDarManagerService.clearResetPasswordToken(i));
        } catch (Exception e) {
            Log.e(TAG, "failed to clear reset token", e);
            e.printStackTrace();
            return false;
        }
    }

    public boolean isResetPasswordTokenActive(final int i) {
        if (isVirtualUserId(i)) {
            return ((Boolean) getDarManagerService().map(new Function() { // from class: com.samsung.android.knox.dar.VirtualLockUtils$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return VirtualLockUtils.lambda$isResetPasswordTokenActive$5(i, (IDarManagerService) obj);
                }
            }).orElse(false)).booleanValue();
        }
        return false;
    }

    static /* synthetic */ Boolean lambda$isResetPasswordTokenActive$5(int i, IDarManagerService iDarManagerService) {
        try {
            return Boolean.valueOf(iDarManagerService.isResetPasswordTokenActive(i));
        } catch (Exception e) {
            Log.e(TAG, "failed to check reset token active", e);
            e.printStackTrace();
            return false;
        }
    }

    public boolean resetPasswordWithToken(final String str, final byte[] bArr, final int i) {
        if (isVirtualUserId(i)) {
            return ((Boolean) getDarManagerService().map(new Function() { // from class: com.samsung.android.knox.dar.VirtualLockUtils$$ExternalSyntheticLambda4
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return VirtualLockUtils.lambda$resetPasswordWithToken$6(str, bArr, i, (IDarManagerService) obj);
                }
            }).orElse(false)).booleanValue();
        }
        return false;
    }

    static /* synthetic */ Boolean lambda$resetPasswordWithToken$6(String str, byte[] bArr, int i, IDarManagerService iDarManagerService) {
        try {
            return Boolean.valueOf(iDarManagerService.resetPasswordWithToken(str, bArr, i));
        } catch (Exception e) {
            Log.e(TAG, "failed to reset passwrod with token", e);
            e.printStackTrace();
            return false;
        }
    }
}
