package android.window;

import android.os.IBinder;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.Log;
import android.view.SurfaceControl;
import android.window.TransitionInfo;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
public final class RemoteAnimationRunnerHelper {
    private static final String TAG = "RemoteAnimationRunnerHelper";
    public static final int TYPE_MERGE_ANIM_CALLBACK = 1;
    public static final int TYPE_TRANSFER_ANIM_CALLBACK = 2;
    private static RemoteAnimationRunnerHelper sRemoteAnimationRunnerHelper;
    private final HashMap<Integer, Runnable> mAnimCallbacks = new HashMap<>();
    Predicate<TransitionInfo> mMergeAnimFilter = null;
    ArrayList<IBinder> mMergedTransitionTokens = new ArrayList<>();
    ArrayList<IBinder> mTransferTransitionTokens = new ArrayList<>();

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

    public void setMergeAnimFilter(Predicate<TransitionInfo> predicate) {
        this.mMergeAnimFilter = predicate;
    }

    public void registerAnimCallback(int i, Runnable runnable) {
        this.mAnimCallbacks.put(Integer.valueOf(i), runnable);
    }

    public boolean mergeOrTransferAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, IBinder iBinder2, IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback, ArrayMap<SurfaceControl, SurfaceControl> arrayMap) throws RemoteException {
        if (CoreRune.FW_SHELL_TRANSITION_MERGE && mergeAnimation(iBinder, transitionInfo, transaction, iRemoteTransitionFinishedCallback)) {
            return true;
        }
        return CoreRune.FW_SHELL_TRANSITION_MERGE_TRANSFER && transferAnimation(iBinder2, transitionInfo, transaction, iRemoteTransitionFinishedCallback, arrayMap);
    }

    private void runCallback(int i) {
        Runnable runnable = this.mAnimCallbacks.get(Integer.valueOf(i));
        if (runnable != null) {
            runnable.run();
            return;
        }
        Log.i(TAG, "Remote callback is null. type=" + i);
    }

    private boolean mergeAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback) throws RemoteException {
        String str = TAG;
        Log.i(str, "Tried to merge animation, canMergeAnimation=" + transitionInfo.canMergeAnimation());
        Predicate<TransitionInfo> predicate = this.mMergeAnimFilter;
        if (predicate != null && predicate.test(transitionInfo)) {
            if (transitionInfo.canMergeAnimation()) {
                for (int i = 0; i < transitionInfo.getChanges().size(); i++) {
                    TransitionInfo.Change change = transitionInfo.getChanges().get(i);
                    if (change.getParent() == null || (change.getFlags() & 2) == 0) {
                        transaction.show(change.getLeash());
                        transaction.setAlpha(change.getLeash(), 1.0f);
                    }
                }
            }
            this.mMergedTransitionTokens.add(iBinder);
            transaction.apply();
            transitionInfo.releaseAnimSurfaces();
            runCallback(1);
            iRemoteTransitionFinishedCallback.onTransitionFinished(null, null);
            return true;
        }
        Log.i(str, "Not merge animation, filter=" + this.mMergeAnimFilter + ", info=" + transitionInfo);
        return false;
    }

    private boolean transferAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback, ArrayMap<SurfaceControl, SurfaceControl> arrayMap) throws RemoteException {
        String str = TAG;
        Log.i(str, "Tried to transfer animation, canMergeAnimation=" + transitionInfo.canTransferAnimation());
        if (transitionInfo.canTransferAnimation() && this.mAnimCallbacks.get(2) != null) {
            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
            for (Map.Entry<SurfaceControl, SurfaceControl> entry : arrayMap.entrySet()) {
                SurfaceControl key = entry.getKey();
                SurfaceControl value = entry.getValue();
                if (value == null || !value.isValid()) {
                    Log.w(TAG, "Failed to transfer animation due to invalid transition leash");
                    return false;
                }
                windowContainerTransaction.addTransferLeash(key, value);
            }
            this.mTransferTransitionTokens.add(iBinder);
            runCallback(2);
            transaction.close();
            transitionInfo.releaseAllSurfaces();
            iRemoteTransitionFinishedCallback.onTransitionFinished(windowContainerTransaction, null);
            return true;
        }
        Log.i(str, "Not transfer animation, transferCallback=" + this.mAnimCallbacks.get(2) + ", info=" + transitionInfo);
        return false;
    }

    public boolean interceptTransitionConsumed(IBinder iBinder) {
        if (CoreRune.FW_SHELL_TRANSITION_MERGE && this.mMergedTransitionTokens.remove(iBinder)) {
            return true;
        }
        return CoreRune.FW_SHELL_TRANSITION_MERGE_TRANSFER && this.mTransferTransitionTokens.remove(iBinder);
    }

    public void clear() {
        if (CoreRune.FW_SHELL_TRANSITION_MERGE) {
            this.mMergedTransitionTokens.clear();
        }
        if (CoreRune.FW_SHELL_TRANSITION_MERGE_TRANSFER) {
            this.mTransferTransitionTokens.clear();
        }
    }
}
