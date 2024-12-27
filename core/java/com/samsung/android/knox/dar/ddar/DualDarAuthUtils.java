package com.samsung.android.knox.dar.ddar;

import android.content.Context;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;

import com.samsung.android.knox.dar.IDarManagerService;
import com.samsung.android.knox.dar.VirtualLockUtils;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class DualDarAuthUtils {
    public static final String DDAR_INNER_AUTH_USERID_KEY = "ddar.inner.auth.userid";
    public static final String DDAR_INNER_MAIN_USERID_KEY = "ddar.inner.main.userid";
    private static final String TAG = "DualDarAuthUtils";
    private final Context mContext;
    private final VirtualLockUtils mVirtualLockUtils = new VirtualLockUtils();
    private IDarManagerService mDarManagerService = null;

    public DualDarAuthUtils(Context context) {
        this.mContext = context;
    }

    private Optional<IDarManagerService> getDarManagerService() {
        if (this.mDarManagerService == null) {
            this.mDarManagerService =
                    IDarManagerService.Stub.asInterface(ServiceManager.getService("dar"));
        }
        return Optional.ofNullable(this.mDarManagerService);
    }

    public void setInnerAuthUserId(final int innerAuthUserId, final int userId) {
        getDarManagerService()
                .ifPresent(
                        new Consumer() { // from class:
                                         // com.samsung.android.knox.dar.ddar.DualDarAuthUtils$$ExternalSyntheticLambda1
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                DualDarAuthUtils.lambda$setInnerAuthUserId$0(
                                        innerAuthUserId, userId, (IDarManagerService) obj);
                            }
                        });
    }

    static /* synthetic */ void lambda$setInnerAuthUserId$0(
            int innerAuthUserId, int userId, IDarManagerService service) {
        try {
            service.setInnerAuthUserId(innerAuthUserId, userId);
        } catch (RemoteException e) {
            Log.e(TAG, "failed due to remote error");
        }
    }

    public int getInnerAuthUserId(final int userId) {
        if (DualDarManager.isOnDeviceOwnerEnabled()) {
            return ((Integer)
                            getDarManagerService()
                                    .map(
                                            new Function() { // from class:
                                                             // com.samsung.android.knox.dar.ddar.DualDarAuthUtils$$ExternalSyntheticLambda0
                                                @Override // java.util.function.Function
                                                public final Object apply(Object obj) {
                                                    return DualDarAuthUtils
                                                            .lambda$getInnerAuthUserId$1(
                                                                    userId,
                                                                    (IDarManagerService) obj);
                                                }
                                            })
                                    .orElse(-10000))
                    .intValue();
        }
        return -10000;
    }

    static /* synthetic */ Integer lambda$getInnerAuthUserId$1(
            int userId, IDarManagerService service) {
        try {
            return Integer.valueOf(service.getInnerAuthUserId(userId));
        } catch (RemoteException e) {
            Log.e(TAG, "failed due to remote error");
            return -10000;
        }
    }

    public void setMainUserId(final int mainUserId, final int innerAuthUserId) {
        getDarManagerService()
                .ifPresent(
                        new Consumer() { // from class:
                                         // com.samsung.android.knox.dar.ddar.DualDarAuthUtils$$ExternalSyntheticLambda5
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                DualDarAuthUtils.lambda$setMainUserId$2(
                                        mainUserId, innerAuthUserId, (IDarManagerService) obj);
                            }
                        });
    }

    static /* synthetic */ void lambda$setMainUserId$2(
            int mainUserId, int innerAuthUserId, IDarManagerService service) {
        try {
            service.setMainUserId(mainUserId, innerAuthUserId);
        } catch (RemoteException e) {
            Log.e(TAG, "failed due to remote error");
        }
    }

    public int getMainUserId(final int innerAuthUserId) {
        if (!VirtualLockUtils.isVirtualUserId(innerAuthUserId)) {
            return innerAuthUserId;
        }
        return ((Integer)
                        getDarManagerService()
                                .map(
                                        new Function() { // from class:
                                                         // com.samsung.android.knox.dar.ddar.DualDarAuthUtils$$ExternalSyntheticLambda3
                                            @Override // java.util.function.Function
                                            public final Object apply(Object obj) {
                                                return DualDarAuthUtils.lambda$getMainUserId$3(
                                                        innerAuthUserId, (IDarManagerService) obj);
                                            }
                                        })
                                .orElse(-10000))
                .intValue();
    }

    static /* synthetic */ Integer lambda$getMainUserId$3(
            int innerAuthUserId, IDarManagerService service) {
        try {
            return Integer.valueOf(service.getMainUserId(innerAuthUserId));
        } catch (RemoteException e) {
            Log.e(TAG, "failed due to remote error");
            return -10000;
        }
    }

    public int getInnerAuthUserForDo() {
        if (DualDarManager.isOnDeviceOwnerEnabled()) {
            return ((Integer)
                            getDarManagerService()
                                    .map(
                                            new Function() { // from class:
                                                             // com.samsung.android.knox.dar.ddar.DualDarAuthUtils$$ExternalSyntheticLambda2
                                                @Override // java.util.function.Function
                                                public final Object apply(Object obj) {
                                                    return DualDarAuthUtils
                                                            .lambda$getInnerAuthUserForDo$4(
                                                                    (IDarManagerService) obj);
                                                }
                                            })
                                    .orElse(-10000))
                    .intValue();
        }
        return -10000;
    }

    static /* synthetic */ Integer lambda$getInnerAuthUserForDo$4(IDarManagerService service) {
        try {
            return Integer.valueOf(service.getInnerAuthUserId(0));
        } catch (RemoteException e) {
            Log.e(TAG, "failed due to remote error");
            return -10000;
        }
    }

    public boolean isInnerAuthUserForDo(int userId) {
        return getInnerAuthUserForDo() == userId;
    }

    public int getPasswordMinimumLengthForInner() {
        if (DualDarManager.isOnDeviceOwnerEnabled()) {
            return ((Integer)
                            getDarManagerService()
                                    .map(
                                            new Function() { // from class:
                                                             // com.samsung.android.knox.dar.ddar.DualDarAuthUtils$$ExternalSyntheticLambda4
                                                @Override // java.util.function.Function
                                                public final Object apply(Object obj) {
                                                    return DualDarAuthUtils
                                                            .lambda$getPasswordMinimumLengthForInner$5(
                                                                    (IDarManagerService) obj);
                                                }
                                            })
                                    .orElse(0))
                    .intValue();
        }
        return 0;
    }

    static /* synthetic */ Integer lambda$getPasswordMinimumLengthForInner$5(
            IDarManagerService service) {
        try {
            return Integer.valueOf(service.getPasswordMinimumLengthForInner());
        } catch (RemoteException e) {
            Log.e(TAG, "failed due to remote error");
            return 0;
        }
    }
}
