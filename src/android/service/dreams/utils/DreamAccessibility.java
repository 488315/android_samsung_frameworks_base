package android.service.dreams.utils;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import com.android.internal.R;

/* loaded from: classes3.dex */
public class DreamAccessibility {
    private final View.AccessibilityDelegate mAccessibilityDelegate;
    private final Context mContext;
    private final Runnable mDismissCallback;
    private final View mView;

    public DreamAccessibility(Context context, View view, Runnable runnable) {
        this.mContext = context;
        this.mView = view;
        this.mAccessibilityDelegate = createNewAccessibilityDelegate(context);
        this.mDismissCallback = runnable;
    }

    public void updateAccessibilityConfiguration() {
        if (this.mView.getAccessibilityDelegate() == null) {
            addAccessibilityConfiguration();
        }
    }

    private void addAccessibilityConfiguration() {
        this.mView.setAccessibilityDelegate(this.mAccessibilityDelegate);
    }

    private View.AccessibilityDelegate createNewAccessibilityDelegate(final Context context) {
        return new View.AccessibilityDelegate() { // from class: android.service.dreams.utils.DreamAccessibility.1
            @Override // android.view.View.AccessibilityDelegate
            public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(1048576, context.getResources().getString(R.string.dream_accessibility_action_click)));
            }

            @Override // android.view.View.AccessibilityDelegate
            public boolean performAccessibilityAction(View view, int i, Bundle bundle) {
                if (i != 1048576 || DreamAccessibility.this.mDismissCallback == null) {
                    return true;
                }
                DreamAccessibility.this.mDismissCallback.run();
                return true;
            }
        };
    }
}
