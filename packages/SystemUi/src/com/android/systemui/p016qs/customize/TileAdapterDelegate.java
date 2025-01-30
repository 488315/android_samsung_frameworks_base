package com.android.systemui.p016qs.customize;

import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R;
import com.android.systemui.p016qs.customize.TileAdapter;
import com.android.systemui.p016qs.customize.TileQueryHelper;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TileAdapterDelegate extends AccessibilityDelegateCompat {
    /* JADX WARN: Removed duplicated region for block: B:16:0x00a2  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x00a7  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00c5  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00ca  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00e9  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:31:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00c7  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00a4  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0077  */
    @Override // androidx.core.view.AccessibilityDelegateCompat
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        boolean z;
        String string;
        View.AccessibilityDelegate accessibilityDelegate = this.mOriginalDelegate;
        AccessibilityNodeInfo accessibilityNodeInfo = accessibilityNodeInfoCompat.mInfo;
        accessibilityDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
        TileAdapter.Holder holder = (TileAdapter.Holder) view.getTag();
        accessibilityNodeInfoCompat.setCollectionItemInfo(null);
        accessibilityNodeInfo.setStateDescription("");
        if (holder == null) {
            return;
        }
        TileAdapter tileAdapter = TileAdapter.this;
        if (!(tileAdapter.mAccessibilityAction == 0)) {
            return;
        }
        if (holder.getLayoutPosition() > tileAdapter.mEditIndex) {
            string = view.getContext().getString(R.string.accessibility_qs_edit_tile_add_action);
        } else {
            int layoutPosition = holder.getLayoutPosition();
            if (tileAdapter.mCurrentSpecs.size() > tileAdapter.mMinNumTiles) {
                if (layoutPosition < tileAdapter.mEditIndex) {
                    z = true;
                    if (z) {
                        List actionList = accessibilityNodeInfoCompat.getActionList();
                        int size = actionList.size();
                        for (int i = 0; i < size; i++) {
                            if (((AccessibilityNodeInfoCompat.AccessibilityActionCompat) actionList.get(i)).getId() == 16) {
                                accessibilityNodeInfoCompat.removeAction((AccessibilityNodeInfoCompat.AccessibilityActionCompat) actionList.get(i));
                            }
                        }
                        if (holder.getLayoutPosition() <= tileAdapter.mEditIndex) {
                            accessibilityNodeInfoCompat.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(R.id.accessibility_action_qs_add_to_position, view.getContext().getString(R.string.accessibility_qs_edit_tile_start_add)));
                        }
                        if (holder.getLayoutPosition() >= tileAdapter.mEditIndex) {
                            accessibilityNodeInfoCompat.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(R.id.accessibility_action_qs_move_to_position, view.getContext().getString(R.string.accessibility_qs_edit_tile_start_move)));
                        }
                        if (holder.getLayoutPosition() < tileAdapter.mEditIndex) {
                            return;
                        }
                        accessibilityNodeInfo.setStateDescription(view.getContext().getString(R.string.accessibility_qs_edit_position, Integer.valueOf(holder.getLayoutPosition())));
                        return;
                    }
                    string = view.getContext().getString(R.string.accessibility_qs_edit_remove_tile_action);
                }
            }
            z = false;
            if (z) {
            }
        }
        accessibilityNodeInfoCompat.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(16, string));
        if (holder.getLayoutPosition() <= tileAdapter.mEditIndex) {
        }
        if (holder.getLayoutPosition() >= tileAdapter.mEditIndex) {
        }
        if (holder.getLayoutPosition() < tileAdapter.mEditIndex) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x006f  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0088  */
    @Override // androidx.core.view.AccessibilityDelegateCompat
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean performAccessibilityAction(View view, int i, Bundle bundle) {
        boolean z;
        TileAdapter.Holder holder = (TileAdapter.Holder) view.getTag();
        if (holder != null) {
            final TileAdapter tileAdapter = TileAdapter.this;
            boolean z2 = false;
            if (tileAdapter.mAccessibilityAction == 0) {
                if (i == 16) {
                    boolean z3 = holder.getLayoutPosition() > tileAdapter.mEditIndex;
                    View view2 = holder.itemView;
                    if (z3) {
                        int layoutPosition = holder.getLayoutPosition();
                        int i2 = tileAdapter.mEditIndex;
                        if (layoutPosition > i2) {
                            tileAdapter.move(layoutPosition, i2, true);
                            z2 = true;
                        }
                        if (z2) {
                            view2.announceForAccessibility(view2.getContext().getText(R.string.accessibility_qs_edit_tile_added));
                        }
                    } else {
                        int layoutPosition2 = holder.getLayoutPosition();
                        if (tileAdapter.mCurrentSpecs.size() > tileAdapter.mMinNumTiles) {
                            if (layoutPosition2 < tileAdapter.mEditIndex) {
                                z = true;
                                if (z) {
                                    tileAdapter.move(layoutPosition2, ((TileQueryHelper.TileInfo) ((ArrayList) tileAdapter.mTiles).get(layoutPosition2)).isSystem ? tileAdapter.mEditIndex : tileAdapter.mTileDividerIndex, true);
                                    z2 = true;
                                }
                                if (z2) {
                                    view2.announceForAccessibility(view2.getContext().getText(R.string.accessibility_qs_edit_tile_removed));
                                }
                            }
                        }
                        z = false;
                        if (z) {
                        }
                        if (z2) {
                        }
                    }
                    return true;
                }
                if (i == R.id.accessibility_action_qs_move_to_position) {
                    int layoutPosition3 = holder.getLayoutPosition();
                    tileAdapter.mAccessibilityFromIndex = layoutPosition3;
                    tileAdapter.mAccessibilityAction = 2;
                    tileAdapter.mFocusIndex = layoutPosition3;
                    tileAdapter.mNeedsFocus = true;
                    tileAdapter.notifyDataSetChanged();
                    return true;
                }
                if (i != R.id.accessibility_action_qs_add_to_position) {
                    return super.performAccessibilityAction(view, i, bundle);
                }
                tileAdapter.mAccessibilityFromIndex = holder.getLayoutPosition();
                tileAdapter.mAccessibilityAction = 1;
                List list = tileAdapter.mTiles;
                int i3 = tileAdapter.mEditIndex;
                tileAdapter.mEditIndex = i3 + 1;
                ((ArrayList) list).add(i3, null);
                tileAdapter.mTileDividerIndex++;
                final int i4 = tileAdapter.mEditIndex - 1;
                tileAdapter.mFocusIndex = i4;
                tileAdapter.mNeedsFocus = true;
                RecyclerView recyclerView = tileAdapter.mRecyclerView;
                if (recyclerView != null) {
                    recyclerView.post(new Runnable() { // from class: com.android.systemui.qs.customize.TileAdapter$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            TileAdapter tileAdapter2 = TileAdapter.this;
                            int i5 = i4;
                            RecyclerView recyclerView2 = tileAdapter2.mRecyclerView;
                            if (recyclerView2 != null) {
                                recyclerView2.smoothScrollToPosition(i5);
                            }
                        }
                    });
                }
                tileAdapter.notifyDataSetChanged();
                return true;
            }
        }
        return super.performAccessibilityAction(view, i, bundle);
    }
}
