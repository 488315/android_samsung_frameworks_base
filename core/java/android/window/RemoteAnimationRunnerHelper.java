package android.window;

import android.os.IBinder;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.Log;
import android.view.SurfaceControl;
import android.window.TransitionInfo;
import com.samsung.android.rune.CoreRune;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

/* loaded from: classes4.dex */
public final class RemoteAnimationRunnerHelper {
    private static final String TAG = RemoteAnimationRunnerHelper.class.getSimpleName();
    public static final int TYPE_MERGE_ANIM_CALLBACK = 1;
    public static final int TYPE_TRANSFER_ANIM_CALLBACK = 2;
    private static RemoteAnimationRunnerHelper sRemoteAnimationRunnerHelper;
    private final HashMap<Integer, Runnable> mAnimCallbacks = new HashMap<>();
    Predicate<TransitionInfo> mMergeAnimFilter = null;

    private RemoteAnimationRunnerHelper() {
    }

    public static RemoteAnimationRunnerHelper getInstance() {
        RemoteAnimationRunnerHelper remoteAnimationRunnerHelper;
        synchronized (RemoteAnimationRunnerHelper.class) {
            if (sRemoteAnimationRunnerHelper == null) {
                sRemoteAnimationRunnerHelper = new RemoteAnimationRunnerHelper();
            }
            remoteAnimationRunnerHelper = sRemoteAnimationRunnerHelper;
        }
        return remoteAnimationRunnerHelper;
    }

    public void setMergeAnimFilter(Predicate<TransitionInfo> mergeAnimFilter) {
        this.mMergeAnimFilter = mergeAnimFilter;
    }

    public void registerAnimCallback(int callbackType, Runnable callback) {
        this.mAnimCallbacks.put(Integer.valueOf(callbackType), callback);
    }

    public boolean mergeOrTransferAnimation(IBinder token, TransitionInfo info, SurfaceControl.Transaction t, IBinder mergeTarget, IRemoteTransitionFinishedCallback finishCallback, ArrayMap<SurfaceControl, SurfaceControl> leashMap) throws RemoteException {
        if (CoreRune.FW_SHELL_TRANSITION_MERGE && mergeAnimation(info, t, finishCallback)) {
            return true;
        }
        return CoreRune.FW_SHELL_TRANSITION_MERGE_TRANSFER && transferAnimation(info, t, finishCallback, leashMap);
    }

    private void runCallback(int type) {
        Runnable runnable = this.mAnimCallbacks.get(Integer.valueOf(type));
        if (runnable != null) {
            runnable.run();
        } else {
            Log.i(TAG, "Remote callback is null. type=" + type);
        }
    }

    private boolean mergeAnimation(TransitionInfo info, SurfaceControl.Transaction t, IRemoteTransitionFinishedCallback finishCallback) throws RemoteException {
        if (this.mMergeAnimFilter != null && this.mMergeAnimFilter.test(info)) {
            if (info.canMergeAnimation()) {
                for (int i = 0; i < info.getChanges().size(); i++) {
                    TransitionInfo.Change change = info.getChanges().get(i);
                    if (change.getParent() == null || (change.getFlags() & 2) == 0) {
                        t.show(change.getLeash());
                        t.setAlpha(change.getLeash(), 1.0f);
                    }
                }
            }
            t.apply();
            info.releaseAnimSurfaces();
            runCallback(1);
            finishCallback.onTransitionFinished(null, null);
            return true;
        }
        return false;
    }

    private boolean transferAnimation(TransitionInfo info, SurfaceControl.Transaction t, IRemoteTransitionFinishedCallback finishCallback, ArrayMap<SurfaceControl, SurfaceControl> leashMap) throws RemoteException {
        if (!info.canTransferAnimation() || this.mAnimCallbacks.get(2) == null) {
            return false;
        }
        WindowContainerTransaction wct = new WindowContainerTransaction();
        for (Map.Entry<SurfaceControl, SurfaceControl> entry : leashMap.entrySet()) {
            SurfaceControl leash = entry.getKey();
            SurfaceControl transitionLeash = entry.getValue();
            if (transitionLeash == null || !transitionLeash.isValid()) {
                Log.w(TAG, "Failed to transfer animation due to invalid transition leash");
                return false;
            }
            wct.addTransferLeash(leash, transitionLeash);
        }
        runCallback(2);
        t.close();
        info.releaseAllSurfaces();
        finishCallback.onTransitionFinished(wct, null);
        return true;
    }
}
