package com.android.systemui.controls.management;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.android.systemui.R;
import com.android.systemui.controls.management.ControlsModel;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ControlHolderAccessibilityDelegate extends AccessibilityDelegateCompat {
    public static final int MOVE_AFTER_ID;
    public static final int MOVE_BEFORE_ID;
    public boolean isFavorite;
    public final ControlsModel.MoveHelper moveHelper;
    public final Function0 positionRetriever;
    public final Function1 stateRetriever;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        MOVE_BEFORE_ID = R.id.accessibility_action_controls_move_before;
        MOVE_AFTER_ID = R.id.accessibility_action_controls_move_after;
    }

    public ControlHolderAccessibilityDelegate(Function1 function1, Function0 function0, ControlsModel.MoveHelper moveHelper) {
        this.stateRetriever = function1;
        this.positionRetriever = function0;
        this.moveHelper = moveHelper;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0054  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0097  */
    @Override // androidx.core.view.AccessibilityDelegateCompat
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        boolean z;
        View.AccessibilityDelegate accessibilityDelegate = this.mOriginalDelegate;
        AccessibilityNodeInfo accessibilityNodeInfo = accessibilityNodeInfoCompat.mInfo;
        accessibilityDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
        boolean z2 = false;
        accessibilityNodeInfo.setContextClickable(false);
        accessibilityNodeInfoCompat.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(16, this.isFavorite ? view.getContext().getString(R.string.accessibility_control_change_unfavorite) : view.getContext().getString(R.string.accessibility_control_change_favorite)));
        ControlsModel.MoveHelper moveHelper = this.moveHelper;
        Function0 function0 = this.positionRetriever;
        if (moveHelper != null) {
            int intValue = ((Number) function0.invoke()).intValue();
            FavoritesModel$moveHelper$1 favoritesModel$moveHelper$1 = (FavoritesModel$moveHelper$1) moveHelper;
            if (intValue > 0 && intValue < favoritesModel$moveHelper$1.this$0.dividerPosition) {
                z = true;
                if (z) {
                    accessibilityNodeInfoCompat.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(MOVE_BEFORE_ID, view.getContext().getString(R.string.accessibility_control_move, Integer.valueOf((((Number) function0.invoke()).intValue() + 1) - 1))));
                    accessibilityNodeInfo.setContextClickable(true);
                }
                if (moveHelper != null) {
                    int intValue2 = ((Number) function0.invoke()).intValue();
                    FavoritesModel$moveHelper$1 favoritesModel$moveHelper$12 = (FavoritesModel$moveHelper$1) moveHelper;
                    if (intValue2 >= 0 && intValue2 < favoritesModel$moveHelper$12.this$0.dividerPosition - 1) {
                        z2 = true;
                    }
                }
                if (z2) {
                    accessibilityNodeInfoCompat.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(MOVE_AFTER_ID, view.getContext().getString(R.string.accessibility_control_move, Integer.valueOf(((Number) function0.invoke()).intValue() + 1 + 1))));
                    accessibilityNodeInfo.setContextClickable(true);
                }
                accessibilityNodeInfo.setStateDescription((CharSequence) this.stateRetriever.invoke(Boolean.valueOf(this.isFavorite)));
                accessibilityNodeInfoCompat.setCollectionItemInfo(null);
                accessibilityNodeInfoCompat.setClassName("android.widget.Switch");
            }
        }
        z = false;
        if (z) {
        }
        if (moveHelper != null) {
        }
        if (z2) {
        }
        accessibilityNodeInfo.setStateDescription((CharSequence) this.stateRetriever.invoke(Boolean.valueOf(this.isFavorite)));
        accessibilityNodeInfoCompat.setCollectionItemInfo(null);
        accessibilityNodeInfoCompat.setClassName("android.widget.Switch");
    }

    @Override // androidx.core.view.AccessibilityDelegateCompat
    public final boolean performAccessibilityAction(View view, int i, Bundle bundle) {
        if (super.performAccessibilityAction(view, i, bundle)) {
            return true;
        }
        boolean z = false;
        int i2 = MOVE_BEFORE_ID;
        Function0 function0 = this.positionRetriever;
        ControlsModel.MoveHelper moveHelper = this.moveHelper;
        if (i == i2) {
            if (moveHelper == null) {
                return true;
            }
            int intValue = ((Number) function0.invoke()).intValue();
            FavoritesModel$moveHelper$1 favoritesModel$moveHelper$1 = (FavoritesModel$moveHelper$1) moveHelper;
            if (intValue > 0 && intValue < favoritesModel$moveHelper$1.this$0.dividerPosition) {
                z = true;
            }
            if (z) {
                favoritesModel$moveHelper$1.this$0.onMoveItemInternal(intValue, intValue - 1);
                return true;
            }
            Log.w("FavoritesModel", "Cannot move position " + intValue + " before");
            return true;
        }
        if (i != MOVE_AFTER_ID) {
            return false;
        }
        if (moveHelper == null) {
            return true;
        }
        int intValue2 = ((Number) function0.invoke()).intValue();
        FavoritesModel$moveHelper$1 favoritesModel$moveHelper$12 = (FavoritesModel$moveHelper$1) moveHelper;
        if (intValue2 >= 0 && intValue2 < favoritesModel$moveHelper$12.this$0.dividerPosition - 1) {
            z = true;
        }
        if (z) {
            favoritesModel$moveHelper$12.this$0.onMoveItemInternal(intValue2, intValue2 + 1);
            return true;
        }
        Log.w("FavoritesModel", "Cannot move position " + intValue2 + " after");
        return true;
    }
}
