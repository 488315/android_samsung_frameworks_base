package com.android.compose.animation.scene;

import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.ui.input.pointer.PointerType;
import com.android.compose.animation.scene.Swipe;
import com.android.compose.animation.scene.SwipeSource;
import com.android.compose.animation.scene.UserAction;
import com.android.compose.animation.scene.content.Content;
import java.util.Map;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class Swipes {
    public final Swipe.Resolved downOrRight;
    public UserActionResult downOrRightResult;
    public final Swipe.Resolved upOrLeft;
    public UserActionResult upOrLeftResult;

    public Swipes(Swipe.Resolved resolved, Swipe.Resolved resolved2) {
        this.upOrLeft = resolved;
        this.downOrRight = resolved2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v4, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r4v5 */
    /* JADX WARN: Type inference failed for: r4v6, types: [int] */
    public static UserActionResult findActionResultBestMatch(Content content, Swipe.Resolved resolved) {
        char c = 0;
        UserActionResult userActionResult = null;
        for (Map.Entry entry : ((Map) ((SnapshotMutableStateImpl) content.userActions$delegate).getValue()).entrySet()) {
            UserAction.Resolved resolved2 = (UserAction.Resolved) entry.getKey();
            UserActionResult userActionResult2 = (UserActionResult) entry.getValue();
            if (resolved2 instanceof Swipe.Resolved) {
                Swipe.Resolved resolved3 = (Swipe.Resolved) resolved2;
                if (resolved3.direction == resolved.direction) {
                    if (resolved3.pointerCount == resolved.pointerCount) {
                        SwipeSource.Resolved resolved4 = resolved3.fromSource;
                        SwipeSource.Resolved resolved5 = resolved.fromSource;
                        if (resolved4 == null || resolved4.equals(resolved5)) {
                            PointerType pointerType = resolved3.pointerType;
                            PointerType pointerType2 = resolved.pointerType;
                            if (pointerType == null || pointerType.equals(pointerType2)) {
                                ?? AreEqual = Intrinsics.areEqual(resolved4, resolved5);
                                boolean zAreEqual = Intrinsics.areEqual(pointerType, pointerType2);
                                if (AreEqual != 0 && zAreEqual) {
                                    return userActionResult2;
                                }
                                if (zAreEqual) {
                                    AreEqual++;
                                }
                                if (AreEqual > c) {
                                    userActionResult = userActionResult2;
                                    c = AreEqual;
                                }
                            }
                        }
                    } else {
                        continue;
                    }
                } else {
                    continue;
                }
            }
        }
        return userActionResult;
    }
}
