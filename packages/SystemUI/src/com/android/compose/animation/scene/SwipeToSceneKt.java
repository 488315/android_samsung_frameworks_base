package com.android.compose.animation.scene;

import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.ui.Modifier;
import com.android.compose.animation.scene.Swipe;
import com.android.compose.animation.scene.UserAction;
import com.android.compose.animation.scene.content.Content;
import com.android.compose.gesture.NestedDraggableKt;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public abstract class SwipeToSceneKt {
    public static final boolean enabled(DraggableHandler draggableHandler, Content content) {
        DragControllerImpl dragControllerImpl = draggableHandler.dragController;
        if (dragControllerImpl == null || !dragControllerImpl.isDrivingTransition()) {
            MutableState mutableState = content.userActions$delegate;
            if (((Map) ((SnapshotMutableStateImpl) mutableState).getValue()).isEmpty()) {
                return false;
            }
            Set<UserAction.Resolved> setKeySet = ((Map) ((SnapshotMutableStateImpl) mutableState).getValue()).keySet();
            if ((setKeySet instanceof Collection) && setKeySet.isEmpty()) {
                return false;
            }
            for (UserAction.Resolved resolved : setKeySet) {
                if (!(resolved instanceof Swipe.Resolved) || ((Swipe.Resolved) resolved).direction.getOrientation() != draggableHandler.orientation) {
                }
            }
            return false;
        }
        return true;
    }

    public static final Modifier swipeToScene(Modifier modifier, DraggableHandler draggableHandler) {
        Content contentContentForUserActions$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout = draggableHandler.layoutImpl.contentForUserActions$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout();
        boolean zEnabled = enabled(draggableHandler, contentContentForUserActions$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout);
        return NestedDraggableKt.nestedDraggable(modifier, draggableHandler, draggableHandler.orientation, draggableHandler.overscrollEffect, zEnabled, zEnabled && ((Boolean) ((SnapshotMutableStateImpl) contentContentForUserActions$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout.nestedScrollControlState.isOuterScrollAllowed$delegate).getValue()).booleanValue());
    }
}
