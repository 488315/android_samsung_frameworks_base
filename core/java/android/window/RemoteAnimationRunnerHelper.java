package android.window;

import android.p009os.IBinder;
import android.p009os.RemoteException;
import android.util.ArrayMap;
import android.util.Log;
import android.view.SurfaceControl;
import android.window.TransitionInfo;
import com.samsung.android.rune.CoreRune;
import java.util.HashMap;

/* loaded from: classes4.dex */
public final class RemoteAnimationRunnerHelper {
    private static final int MERGE_ANIM_CALLBACK = 1;
    private static final String TAG = "RemoteAnimationRunnerHelper";
    private static final int TRANSFER_ANIM_CALLBACK = 2;

    public static boolean mergeAnimation(IBinder token, TransitionInfo info, SurfaceControl.Transaction t, IBinder mergeTarget, IRemoteTransitionFinishedCallback finishCallback, HashMap<Integer, Runnable> animCallbacks, ArrayMap<SurfaceControl, SurfaceControl> leashMap) throws RemoteException {
        if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_MERGE && info.canMergeAnimation() && animCallbacks.get(1) != null) {
            for (int i = 0; i < info.getChanges().size(); i++) {
                TransitionInfo.Change change = info.getChanges().get(i);
                if (change.getParent() == null || (change.getFlags() & 2) == 0) {
                    t.show(change.getLeash());
                    t.setAlpha(change.getLeash(), 1.0f);
                }
            }
            t.apply();
            info.releaseAnimSurfaces();
            animCallbacks.get(1).run();
            finishCallback.onTransitionFinished(null, null);
            return true;
        }
        if (!CoreRune.FW_CUSTOM_SHELL_TRANSITION_MERGE_TRANSFER || !info.canTransferAnimation() || animCallbacks.get(2) == null) {
            return false;
        }
        WindowContainerTransaction wct = new WindowContainerTransaction();
        for (SurfaceControl leash : leashMap.keySet()) {
            SurfaceControl transitionLeash = leashMap.get(leash);
            if (transitionLeash == null || !transitionLeash.isValid()) {
                Log.m102w(TAG, "Failed to transfer animation due to invalid transition leash");
                return false;
            }
            wct.mTransferLeashMap.put(leash, transitionLeash);
        }
        animCallbacks.get(2).run();
        t.close();
        info.releaseAllSurfaces();
        finishCallback.onTransitionFinished(wct, null);
        return true;
    }
}
