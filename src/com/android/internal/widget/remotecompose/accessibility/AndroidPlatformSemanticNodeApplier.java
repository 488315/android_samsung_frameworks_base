package com.android.internal.widget.remotecompose.accessibility;

import android.graphics.Rect;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import com.android.internal.widget.remotecompose.core.semantics.ScrollableComponent;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes6.dex */
public class AndroidPlatformSemanticNodeApplier extends BaseSemanticNodeApplier<AccessibilityNodeInfo> {
    private static final String ROLE_DESCRIPTION_KEY = "AccessibilityNodeInfo.roleDescription";
    private final View mPlayer;

    @Override // com.android.internal.widget.remotecompose.accessibility.SemanticNodeApplier
    public /* bridge */ /* synthetic */ void addChildren(Object obj, List list) {
        addChildren((AccessibilityNodeInfo) obj, (List<Integer>) list);
    }

    public AndroidPlatformSemanticNodeApplier(View view) {
        this.mPlayer = view;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.internal.widget.remotecompose.accessibility.BaseSemanticNodeApplier
    public void setClickable(AccessibilityNodeInfo accessibilityNodeInfo, boolean z) {
        accessibilityNodeInfo.setClickable(z);
        if (z) {
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK);
        } else {
            accessibilityNodeInfo.removeAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.internal.widget.remotecompose.accessibility.BaseSemanticNodeApplier
    public void setEnabled(AccessibilityNodeInfo accessibilityNodeInfo, boolean z) {
        accessibilityNodeInfo.setEnabled(z);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.internal.widget.remotecompose.accessibility.BaseSemanticNodeApplier
    public CharSequence getStateDescription(AccessibilityNodeInfo accessibilityNodeInfo) {
        return accessibilityNodeInfo.getStateDescription();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.internal.widget.remotecompose.accessibility.BaseSemanticNodeApplier
    public void setStateDescription(AccessibilityNodeInfo accessibilityNodeInfo, CharSequence charSequence) {
        accessibilityNodeInfo.setStateDescription(charSequence);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.internal.widget.remotecompose.accessibility.BaseSemanticNodeApplier
    public void setRoleDescription(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
        accessibilityNodeInfo.getExtras().putCharSequence(ROLE_DESCRIPTION_KEY, str);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.internal.widget.remotecompose.accessibility.BaseSemanticNodeApplier
    public CharSequence getText(AccessibilityNodeInfo accessibilityNodeInfo) {
        return accessibilityNodeInfo.getText();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.internal.widget.remotecompose.accessibility.BaseSemanticNodeApplier
    public void setText(AccessibilityNodeInfo accessibilityNodeInfo, CharSequence charSequence) {
        accessibilityNodeInfo.setText(charSequence);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.internal.widget.remotecompose.accessibility.BaseSemanticNodeApplier
    public CharSequence getContentDescription(AccessibilityNodeInfo accessibilityNodeInfo) {
        return accessibilityNodeInfo.getContentDescription();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.internal.widget.remotecompose.accessibility.BaseSemanticNodeApplier
    public void setContentDescription(AccessibilityNodeInfo accessibilityNodeInfo, CharSequence charSequence) {
        accessibilityNodeInfo.setContentDescription(charSequence);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.internal.widget.remotecompose.accessibility.BaseSemanticNodeApplier
    public void setBoundsInScreen(AccessibilityNodeInfo accessibilityNodeInfo, Rect rect) {
        accessibilityNodeInfo.setBoundsInParent(rect);
        accessibilityNodeInfo.setBoundsInScreen(rect);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.internal.widget.remotecompose.accessibility.BaseSemanticNodeApplier
    public void setUniqueId(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
        accessibilityNodeInfo.setUniqueId(str);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.internal.widget.remotecompose.accessibility.BaseSemanticNodeApplier
    public void applyScrollable(AccessibilityNodeInfo accessibilityNodeInfo, ScrollableComponent.ScrollAxisRange scrollAxisRange, int i) {
        accessibilityNodeInfo.setScrollable(true);
        accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_TO_POSITION);
        accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SET_PROGRESS);
        accessibilityNodeInfo.setGranularScrollingSupported(true);
        if (scrollAxisRange.canScrollForward()) {
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_FORWARD);
            if (i == 2) {
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_DOWN);
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_PAGE_DOWN);
            } else if (i == 1) {
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_RIGHT);
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_PAGE_RIGHT);
            }
        }
        if (scrollAxisRange.canScrollBackwards()) {
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_BACKWARD);
            if (i == 2) {
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_UP);
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_PAGE_UP);
            } else if (i == 1) {
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_LEFT);
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_PAGE_LEFT);
            }
        }
        if (i == 1) {
            accessibilityNodeInfo.setCollectionInfo(AccessibilityNodeInfo.CollectionInfo.obtain(1, -1, false));
            accessibilityNodeInfo.setClassName("android.widget.HorizontalScrollView");
        } else {
            accessibilityNodeInfo.setCollectionInfo(AccessibilityNodeInfo.CollectionInfo.obtain(-1, 1, false));
            accessibilityNodeInfo.setClassName("android.widget.ScrollView");
        }
        if (i == 1) {
            accessibilityNodeInfo.setClassName("android.widget.HorizontalScrollView");
        } else {
            accessibilityNodeInfo.setClassName("android.widget.ScrollView");
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.android.internal.widget.remotecompose.accessibility.BaseSemanticNodeApplier
    public void applyListItem(AccessibilityNodeInfo accessibilityNodeInfo, int i) {
        accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SHOW_ON_SCREEN);
        accessibilityNodeInfo.setScreenReaderFocusable(true);
        accessibilityNodeInfo.setFocusable(true);
        accessibilityNodeInfo.setParent(this.mPlayer, i);
        accessibilityNodeInfo.setCollectionItemInfo(AccessibilityNodeInfo.CollectionItemInfo.obtain(1, 1, 0, 1, false));
    }

    public void addChildren(AccessibilityNodeInfo accessibilityNodeInfo, List<Integer> list) {
        Iterator<Integer> it = list.iterator();
        while (it.hasNext()) {
            accessibilityNodeInfo.addChild(this.mPlayer, it.next().intValue());
        }
    }
}
