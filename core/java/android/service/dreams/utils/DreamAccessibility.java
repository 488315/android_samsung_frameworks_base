package android.service.dreams.utils;

import android.content.Context;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;

import com.android.internal.R;

import java.util.Iterator;

public class DreamAccessibility {
    private final View.AccessibilityDelegate mAccessibilityDelegate;
    private final Context mContext;
    private final View mView;

    public DreamAccessibility(Context context, View view) {
        this.mContext = context;
        this.mView = view;
        this.mAccessibilityDelegate = createNewAccessibilityDelegate(this.mContext);
    }

    public void updateAccessibilityConfiguration(Boolean interactive) {
        if (!interactive.booleanValue()) {
            addAccessibilityConfiguration();
        } else {
            removeCustomAccessibilityAction();
        }
    }

    private void addAccessibilityConfiguration() {
        this.mView.setAccessibilityDelegate(this.mAccessibilityDelegate);
    }

    private void removeCustomAccessibilityAction() {
        if (this.mView.getAccessibilityDelegate() == this.mAccessibilityDelegate) {
            this.mView.setAccessibilityDelegate(null);
        }
    }

    private View.AccessibilityDelegate createNewAccessibilityDelegate(final Context context) {
        return new View
                .AccessibilityDelegate() { // from class:
                                           // android.service.dreams.utils.DreamAccessibility.1
            @Override // android.view.View.AccessibilityDelegate
            public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfo info) {
                super.onInitializeAccessibilityNodeInfo(host, info);
                Iterator<AccessibilityNodeInfo.AccessibilityAction> it =
                        info.getActionList().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    AccessibilityNodeInfo.AccessibilityAction action = it.next();
                    if (action.getId() == 16) {
                        info.removeAction(action);
                        break;
                    }
                }
                info.addAction(
                        new AccessibilityNodeInfo.AccessibilityAction(
                                16,
                                context.getResources()
                                        .getString(R.string.dream_accessibility_action_click)));
            }
        };
    }
}
