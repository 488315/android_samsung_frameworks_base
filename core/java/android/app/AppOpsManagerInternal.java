package android.app;

import android.content.AttributionSource;
import android.os.IBinder;
import android.os.UserHandle;
import android.util.SparseArray;
import android.util.SparseIntArray;
import com.android.internal.app.IAppOpsCallback;
import com.android.internal.util.function.DodecFunction;
import com.android.internal.util.function.HexConsumer;
import com.android.internal.util.function.HexFunction;
import com.android.internal.util.function.OctFunction;
import com.android.internal.util.function.QuadFunction;
import com.android.internal.util.function.UndecFunction;

/* loaded from: classes.dex */
public abstract class AppOpsManagerInternal {

    public interface CheckOpsDelegate {
        int checkAudioOperation(int i, int i2, int i3, String str, QuadFunction<Integer, Integer, Integer, String, Integer> quadFunction);

        int checkOperation(int i, int i2, String str, String str2, int i3, boolean z, HexFunction<Integer, Integer, String, String, Integer, Boolean, Integer> hexFunction);

        void finishOperation(IBinder iBinder, int i, int i2, String str, String str2, int i3, HexConsumer<IBinder, Integer, Integer, String, String, Integer> hexConsumer);

        void finishProxyOperation(IBinder iBinder, int i, AttributionSource attributionSource, boolean z, QuadFunction<IBinder, Integer, AttributionSource, Boolean, Void> quadFunction);

        SyncNotedAppOp noteOperation(int i, int i2, String str, String str2, int i3, boolean z, String str3, boolean z2, OctFunction<Integer, Integer, String, String, Integer, Boolean, String, Boolean, SyncNotedAppOp> octFunction);

        SyncNotedAppOp noteProxyOperation(int i, AttributionSource attributionSource, boolean z, String str, boolean z2, boolean z3, HexFunction<Integer, AttributionSource, Boolean, String, Boolean, Boolean, SyncNotedAppOp> hexFunction);

        SyncNotedAppOp startOperation(IBinder iBinder, int i, int i2, String str, String str2, int i3, boolean z, boolean z2, String str3, boolean z3, int i4, int i5, DodecFunction<IBinder, Integer, Integer, String, String, Integer, Boolean, Boolean, String, Boolean, Integer, Integer, SyncNotedAppOp> dodecFunction);

        SyncNotedAppOp startProxyOperation(IBinder iBinder, int i, AttributionSource attributionSource, boolean z, boolean z2, String str, boolean z3, boolean z4, int i2, int i3, int i4, UndecFunction<IBinder, Integer, AttributionSource, Boolean, Boolean, String, Boolean, Boolean, Integer, Integer, Integer, SyncNotedAppOp> undecFunction);
    }

    public abstract int getOpRestrictionCount(int i, UserHandle userHandle, String str, String str2);

    public abstract void setDeviceAndProfileOwners(SparseIntArray sparseIntArray);

    public abstract void setGlobalRestriction(int i, boolean z, IBinder iBinder);

    public abstract void setModeFromPermissionPolicy(int i, int i2, String str, int i3, IAppOpsCallback iAppOpsCallback);

    public abstract void setUidModeFromPermissionPolicy(int i, int i2, int i3, IAppOpsCallback iAppOpsCallback);

    public abstract void updateAppWidgetVisibility(SparseArray<String> sparseArray, boolean z);
}
