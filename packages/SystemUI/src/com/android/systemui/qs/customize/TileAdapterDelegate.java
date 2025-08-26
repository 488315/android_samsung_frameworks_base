package com.android.systemui.qs.customize;

import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R;
import com.android.systemui.qs.customize.TileAdapter;
import com.android.systemui.qs.customize.TileQueryHelper;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class TileAdapterDelegate extends AccessibilityDelegateCompat {
    /* JADX WARN: Removed duplicated region for block: B:28:0x009e  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00bc  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00da  */
    /* JADX WARN: Removed duplicated region for block: B:41:? A[RETURN, SYNTHETIC] */
    @Override // androidx.core.view.AccessibilityDelegateCompat
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        String string;
        this.mOriginalDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat.mInfo);
        TileAdapter.Holder holder = (TileAdapter.Holder) view.getTag();
        accessibilityNodeInfoCompat.setCollectionItemInfo(null);
        accessibilityNodeInfoCompat.mInfo.setStateDescription("");
        if (holder == null) {
            return;
        }
        TileAdapter tileAdapter = TileAdapter.this;
        if (tileAdapter.mAccessibilityAction != 0) {
            return;
        }
        if (holder.getLayoutPosition() > tileAdapter.mEditIndex) {
            string = view.getContext().getString(R.string.accessibility_qs_edit_tile_add_action);
        } else {
            int layoutPosition = holder.getLayoutPosition();
            if (((ArrayList) tileAdapter.mCurrentSpecs).size() <= tileAdapter.mMinNumTiles || layoutPosition >= tileAdapter.mEditIndex) {
                ArrayList arrayList = (ArrayList) accessibilityNodeInfoCompat.getActionList();
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    if (((AccessibilityNodeInfoCompat.AccessibilityActionCompat) arrayList.get(i)).getId() == 16) {
                        accessibilityNodeInfoCompat.mInfo.removeAction((AccessibilityNodeInfo.AccessibilityAction) ((AccessibilityNodeInfoCompat.AccessibilityActionCompat) arrayList.get(i)).mAction);
                    }
                }
                accessibilityNodeInfoCompat.setClickable(false);
                if (holder.getLayoutPosition() > tileAdapter.mEditIndex) {
                    accessibilityNodeInfoCompat.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(R.id.accessibility_action_qs_add_to_position, view.getContext().getString(R.string.accessibility_qs_edit_tile_start_add)));
                }
                if (holder.getLayoutPosition() < tileAdapter.mEditIndex) {
                    accessibilityNodeInfoCompat.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(R.id.accessibility_action_qs_move_to_position, view.getContext().getString(R.string.accessibility_qs_edit_tile_start_move)));
                }
                if (holder.getLayoutPosition() >= tileAdapter.mEditIndex) {
                    accessibilityNodeInfoCompat.mInfo.setStateDescription(view.getContext().getString(R.string.accessibility_qs_edit_position, Integer.valueOf(holder.getLayoutPosition())));
                    return;
                }
                return;
            }
            string = view.getContext().getString(R.string.accessibility_qs_edit_remove_tile_action);
        }
        accessibilityNodeInfoCompat.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(16, string));
        accessibilityNodeInfoCompat.setClickable(true);
        if (holder.getLayoutPosition() > tileAdapter.mEditIndex) {
        }
        if (holder.getLayoutPosition() < tileAdapter.mEditIndex) {
        }
        if (holder.getLayoutPosition() >= tileAdapter.mEditIndex) {
        }
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
                            TileAdapter tileAdapter2 = tileAdapter;
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
