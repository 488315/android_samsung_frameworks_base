package com.android.systemui.qs.customize;

import android.os.Bundle;
import android.view.View;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R;
import com.android.systemui.qs.customize.TileAdapter;
import com.android.systemui.qs.customize.TileQueryHelper;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class TileAdapterDelegate extends AccessibilityDelegateCompat {
    /* JADX WARN: Removed duplicated region for block: B:14:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x00b8  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00d6  */
    /* JADX WARN: Removed duplicated region for block: B:23:? A[RETURN, SYNTHETIC] */
    @Override // androidx.core.view.AccessibilityDelegateCompat
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onInitializeAccessibilityNodeInfo(android.view.View r9, androidx.core.view.accessibility.AccessibilityNodeInfoCompat r10) {
        /*
            r8 = this;
            android.view.View$AccessibilityDelegate r8 = r8.mOriginalDelegate
            android.view.accessibility.AccessibilityNodeInfo r0 = r10.mInfo
            r8.onInitializeAccessibilityNodeInfo(r9, r0)
            java.lang.Object r8 = r9.getTag()
            com.android.systemui.qs.customize.TileAdapter$Holder r8 = (com.android.systemui.qs.customize.TileAdapter.Holder) r8
            r0 = 0
            r10.setCollectionItemInfo(r0)
            android.view.accessibility.AccessibilityNodeInfo r0 = r10.mInfo
            java.lang.String r1 = ""
            r0.setStateDescription(r1)
            if (r8 == 0) goto Lf2
            com.android.systemui.qs.customize.TileAdapter r0 = com.android.systemui.qs.customize.TileAdapter.this
            int r1 = r0.mAccessibilityAction
            if (r1 != 0) goto Lf2
            int r1 = r8.getLayoutPosition()
            int r2 = r0.mEditIndex
            r3 = 0
            r4 = 1
            if (r1 <= r2) goto L2c
            r1 = r4
            goto L2d
        L2c:
            r1 = r3
        L2d:
            r2 = 16
            if (r1 == 0) goto L3d
            android.content.Context r1 = r9.getContext()
            r3 = 2131951870(0x7f1300fe, float:1.9540167E38)
            java.lang.String r1 = r1.getString(r3)
            goto L5a
        L3d:
            int r1 = r8.getLayoutPosition()
            java.util.List r5 = r0.mCurrentSpecs
            int r5 = r5.size()
            int r6 = r0.mMinNumTiles
            if (r5 <= r6) goto L66
            int r5 = r0.mEditIndex
            if (r1 >= r5) goto L66
            android.content.Context r1 = r9.getContext()
            r3 = 2131951869(0x7f1300fd, float:1.9540165E38)
            java.lang.String r1 = r1.getString(r3)
        L5a:
            androidx.core.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityActionCompat r3 = new androidx.core.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityActionCompat
            r3.<init>(r2, r1)
            r10.addAction(r3)
            r10.setClickable(r4)
            goto L92
        L66:
            java.util.List r1 = r10.getActionList()
            int r4 = r1.size()
            r5 = r3
        L6f:
            if (r5 >= r4) goto L8f
            java.lang.Object r6 = r1.get(r5)
            androidx.core.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityActionCompat r6 = (androidx.core.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityActionCompat) r6
            int r6 = r6.getId()
            if (r6 != r2) goto L8c
            java.lang.Object r6 = r1.get(r5)
            androidx.core.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityActionCompat r6 = (androidx.core.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityActionCompat) r6
            android.view.accessibility.AccessibilityNodeInfo r7 = r10.mInfo
            java.lang.Object r6 = r6.mAction
            android.view.accessibility.AccessibilityNodeInfo$AccessibilityAction r6 = (android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction) r6
            r7.removeAction(r6)
        L8c:
            int r5 = r5 + 1
            goto L6f
        L8f:
            r10.setClickable(r3)
        L92:
            int r1 = r8.getLayoutPosition()
            int r2 = r0.mEditIndex
            if (r1 <= r2) goto Lb0
            androidx.core.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityActionCompat r1 = new androidx.core.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityActionCompat
            android.content.Context r2 = r9.getContext()
            r3 = 2131951875(0x7f130103, float:1.9540177E38)
            java.lang.String r2 = r2.getString(r3)
            r3 = 2131361843(0x7f0a0033, float:1.834345E38)
            r1.<init>(r3, r2)
            r10.addAction(r1)
        Lb0:
            int r1 = r8.getLayoutPosition()
            int r2 = r0.mEditIndex
            if (r1 >= r2) goto Lce
            androidx.core.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityActionCompat r1 = new androidx.core.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityActionCompat
            android.content.Context r2 = r9.getContext()
            r3 = 2131951876(0x7f130104, float:1.9540179E38)
            java.lang.String r2 = r2.getString(r3)
            r3 = 2131361844(0x7f0a0034, float:1.8343452E38)
            r1.<init>(r3, r2)
            r10.addAction(r1)
        Lce:
            int r1 = r8.getLayoutPosition()
            int r0 = r0.mEditIndex
            if (r1 >= r0) goto Lf2
            android.content.Context r9 = r9.getContext()
            int r8 = r8.getLayoutPosition()
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)
            java.lang.Object[] r8 = new java.lang.Object[]{r8}
            r0 = 2131951868(0x7f1300fc, float:1.9540163E38)
            java.lang.String r8 = r9.getString(r0, r8)
            android.view.accessibility.AccessibilityNodeInfo r9 = r10.mInfo
            r9.setStateDescription(r8)
        Lf2:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.customize.TileAdapterDelegate.onInitializeAccessibilityNodeInfo(android.view.View, androidx.core.view.accessibility.AccessibilityNodeInfoCompat):void");
    }

    @Override // androidx.core.view.AccessibilityDelegateCompat
    public final boolean performAccessibilityAction(View view, int i, Bundle bundle) {
        TileAdapter.Holder holder = (TileAdapter.Holder) view.getTag();
        if (holder != null) {
            final TileAdapter tileAdapter = TileAdapter.this;
            if (tileAdapter.mAccessibilityAction == 0) {
                if (i == 16) {
                    if (holder.getLayoutPosition() > tileAdapter.mEditIndex) {
                        int layoutPosition = holder.getLayoutPosition();
                        int i2 = tileAdapter.mEditIndex;
                        if (layoutPosition > i2) {
                            tileAdapter.move(layoutPosition, i2, true);
                            View view2 = holder.itemView;
                            view2.announceForAccessibility(view2.getContext().getText(R.string.accessibility_qs_edit_tile_added));
                        }
                    } else {
                        int layoutPosition2 = holder.getLayoutPosition();
                        if (tileAdapter.mCurrentSpecs.size() > tileAdapter.mMinNumTiles && layoutPosition2 < tileAdapter.mEditIndex) {
                            tileAdapter.move(layoutPosition2, ((TileQueryHelper.TileInfo) ((ArrayList) tileAdapter.mTiles).get(layoutPosition2)).isSystem ? tileAdapter.mEditIndex : tileAdapter.mTileDividerIndex, true);
                            View view3 = holder.itemView;
                            view3.announceForAccessibility(view3.getContext().getText(R.string.accessibility_qs_edit_tile_removed));
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
