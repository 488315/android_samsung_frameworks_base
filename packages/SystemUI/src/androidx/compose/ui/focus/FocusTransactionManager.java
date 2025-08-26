package androidx.compose.ui.focus;

import androidx.collection.MutableScatterMap;
import androidx.collection.ScatterMapKt;
import androidx.compose.runtime.collection.MutableVector;
import androidx.compose.ui.ComposeUiFlags;
import androidx.compose.ui.autofill.AndroidAutofill$$ExternalSyntheticOutline0;
import kotlin.jvm.functions.Function0;

/* loaded from: classes.dex */
public final class FocusTransactionManager {
    public int generation;
    public boolean ongoingTransaction;
    public final MutableScatterMap states = ScatterMapKt.mutableScatterMapOf();
    public final MutableVector cancellationListener = new MutableVector(new Function0[16], 0);

    public static final void access$cancelTransaction(FocusTransactionManager focusTransactionManager) {
        focusTransactionManager.states.clear();
        focusTransactionManager.ongoingTransaction = false;
        MutableVector mutableVector = focusTransactionManager.cancellationListener;
        Object[] objArr = mutableVector.content;
        int i = mutableVector.size;
        for (int i2 = 0; i2 < i; i2++) {
            ((Function0) objArr[i2]).invoke();
        }
        mutableVector.clear();
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x006a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void access$commitTransaction(FocusTransactionManager focusTransactionManager) {
        MutableScatterMap mutableScatterMap = focusTransactionManager.states;
        Object[] objArr = mutableScatterMap.keys;
        long[] jArr = mutableScatterMap.metadata;
        int length = jArr.length - 2;
        if (length >= 0) {
            int i = 0;
            while (true) {
                long j = jArr[i];
                if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                    int i2 = 8 - ((~(i - length)) >>> 31);
                    for (int i3 = 0; i3 < i2; i3++) {
                        if ((255 & j) < 128) {
                            FocusTargetNode focusTargetNode = (FocusTargetNode) objArr[(i << 3) + i3];
                            focusTargetNode.getClass();
                            FocusTransactionManager focusTransactionManagerRequireTransactionManager = FocusTargetNodeKt.requireTransactionManager(focusTargetNode);
                            focusTransactionManagerRequireTransactionManager.getClass();
                            if (ComposeUiFlags.isTrackFocusEnabled) {
                                throw new IllegalStateException("uncommittedFocusState must not be accessed when isTrackFocusEnabled is on");
                            }
                            FocusStateImpl focusStateImpl = (FocusStateImpl) focusTransactionManagerRequireTransactionManager.states.get(focusTargetNode);
                            if (focusStateImpl == null) {
                                throw AndroidAutofill$$ExternalSyntheticOutline0.m("committing a node that was not updated in the current transaction");
                            }
                            focusTargetNode.committedFocusState = focusStateImpl;
                        }
                        j >>= 8;
                    }
                    if (i2 != 8) {
                        break;
                    } else if (i == length) {
                        break;
                    } else {
                        i++;
                    }
                }
            }
        }
        mutableScatterMap.clear();
        focusTransactionManager.ongoingTransaction = false;
        focusTransactionManager.cancellationListener.clear();
    }
}
