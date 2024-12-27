package com.android.server.appbinding.finders;

import android.app.role.OnRoleHoldersChangedListener;
import android.app.role.RoleManager;
import android.content.Context;
import android.os.UserHandle;
import android.util.SparseArray;

import com.android.server.appbinding.AppBindingService$$ExternalSyntheticLambda0;

import java.util.function.BiConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class CarrierMessagingClientServiceFinder {
    public final Context mContext;
    public final BiConsumer mListener;
    public final RoleManager mRoleManager;
    public final Object mLock = new Object();
    public final SparseArray mTargetPackages = new SparseArray(4);
    public final SparseArray mTargetServices = new SparseArray(4);
    public final SparseArray mLastMessages = new SparseArray(4);
    public final CarrierMessagingClientServiceFinder$$ExternalSyntheticLambda0
            mRoleHolderChangedListener =
                    new OnRoleHoldersChangedListener() { // from class:
                                                         // com.android.server.appbinding.finders.CarrierMessagingClientServiceFinder$$ExternalSyntheticLambda0
                        public final void onRoleHoldersChanged(String str, UserHandle userHandle) {
                            CarrierMessagingClientServiceFinder
                                    carrierMessagingClientServiceFinder =
                                            CarrierMessagingClientServiceFinder.this;
                            carrierMessagingClientServiceFinder.getClass();
                            if ("android.app.role.SMS".equals(str)) {
                                carrierMessagingClientServiceFinder.mListener.accept(
                                        carrierMessagingClientServiceFinder,
                                        Integer.valueOf(userHandle.getIdentifier()));
                            }
                        }
                    };

    /* JADX WARN: Type inference failed for: r4v1, types: [com.android.server.appbinding.finders.CarrierMessagingClientServiceFinder$$ExternalSyntheticLambda0] */
    public CarrierMessagingClientServiceFinder(
            Context context,
            AppBindingService$$ExternalSyntheticLambda0
                    appBindingService$$ExternalSyntheticLambda0) {
        this.mContext = context;
        this.mListener = appBindingService$$ExternalSyntheticLambda0;
        this.mRoleManager = (RoleManager) context.getSystemService(RoleManager.class);
    }
}
