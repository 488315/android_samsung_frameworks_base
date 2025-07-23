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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class TileAdapterDelegate extends AccessibilityDelegateCompat {
    /* JADX WARN: Removed duplicated region for block: B:14:0x009e  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x00bc  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00da  */
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
            if (r8 == 0) goto Lf6
            com.android.systemui.qs.customize.TileAdapter r0 = com.android.systemui.qs.customize.TileAdapter.this
            int r1 = r0.mAccessibilityAction
            if (r1 != 0) goto Lf6
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
            r3 = 2131951893(0x7f130115, float:1.9540213E38)
            java.lang.String r1 = r1.getString(r3)
            goto L5c
        L3d:
            int r1 = r8.getLayoutPosition()
            java.util.List r5 = r0.mCurrentSpecs
            java.util.ArrayList r5 = (java.util.ArrayList) r5
            int r5 = r5.size()
            int r6 = r0.mMinNumTiles
            if (r5 <= r6) goto L68
            int r5 = r0.mEditIndex
            if (r1 >= r5) goto L68
            android.content.Context r1 = r9.getContext()
            r3 = 2131951892(0x7f130114, float:1.9540211E38)
            java.lang.String r1 = r1.getString(r3)
        L5c:
            androidx.core.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityActionCompat r3 = new androidx.core.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityActionCompat
            r3.<init>(r2, r1)
            r10.addAction(r3)
            r10.setClickable(r4)
            goto L96
        L68:
            java.util.List r1 = r10.getActionList()
            java.util.ArrayList r1 = (java.util.ArrayList) r1
            int r4 = r1.size()
            r5 = r3
        L73:
            if (r5 >= r4) goto L93
            java.lang.Object r6 = r1.get(r5)
            androidx.core.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityActionCompat r6 = (androidx.core.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityActionCompat) r6
            int r6 = r6.getId()
            if (r6 != r2) goto L90
            java.lang.Object r6 = r1.get(r5)
            androidx.core.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityActionCompat r6 = (androidx.core.view.accessibility.AccessibilityNodeInfoCompat.AccessibilityActionCompat) r6
            android.view.accessibility.AccessibilityNodeInfo r7 = r10.mInfo
            java.lang.Object r6 = r6.mAction
            android.view.accessibility.AccessibilityNodeInfo$AccessibilityAction r6 = (android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction) r6
            r7.removeAction(r6)
        L90:
            int r5 = r5 + 1
            goto L73
        L93:
            r10.setClickable(r3)
        L96:
            int r1 = r8.getLayoutPosition()
            int r2 = r0.mEditIndex
            if (r1 <= r2) goto Lb4
            androidx.core.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityActionCompat r1 = new androidx.core.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityActionCompat
            android.content.Context r2 = r9.getContext()
            r3 = 2131951899(0x7f13011b, float:1.9540226E38)
            java.lang.String r2 = r2.getString(r3)
            r3 = 2131361851(0x7f0a003b, float:1.8343466E38)
            r1.<init>(r3, r2)
            r10.addAction(r1)
        Lb4:
            int r1 = r8.getLayoutPosition()
            int r2 = r0.mEditIndex
            if (r1 >= r2) goto Ld2
            androidx.core.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityActionCompat r1 = new androidx.core.view.accessibility.AccessibilityNodeInfoCompat$AccessibilityActionCompat
            android.content.Context r2 = r9.getContext()
            r3 = 2131951900(0x7f13011c, float:1.9540228E38)
            java.lang.String r2 = r2.getString(r3)
            r3 = 2131361852(0x7f0a003c, float:1.8343468E38)
            r1.<init>(r3, r2)
            r10.addAction(r1)
        Ld2:
            int r1 = r8.getLayoutPosition()
            int r0 = r0.mEditIndex
            if (r1 >= r0) goto Lf6
            android.content.Context r9 = r9.getContext()
            int r8 = r8.getLayoutPosition()
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)
            java.lang.Object[] r8 = new java.lang.Object[]{r8}
            r0 = 2131951891(0x7f130113, float:1.954021E38)
            java.lang.String r8 = r9.getString(r0, r8)
            android.view.accessibility.AccessibilityNodeInfo r9 = r10.mInfo
            r9.setStateDescription(r8)
        Lf6:
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
                        }
                        return true;
                    }
                    int layoutPosition2 = holder.getLayoutPosition();
                    if (((ArrayList) tileAdapter.mCurrentSpecs).size() > tileAdapter.mMinNumTiles && layoutPosition2 < tileAdapter.mEditIndex) {
                        tileAdapter.move(layoutPosition2, ((TileQueryHelper.TileInfo) ((ArrayList) tileAdapter.mTiles).get(layoutPosition2)).isSystem ? tileAdapter.mEditIndex : tileAdapter.mTileDividerIndex, true);
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
