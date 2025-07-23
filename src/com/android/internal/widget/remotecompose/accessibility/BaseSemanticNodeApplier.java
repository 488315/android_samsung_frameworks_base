package com.android.internal.widget.remotecompose.accessibility;

import android.graphics.Rect;
import android.util.Log;
import com.android.internal.widget.remotecompose.core.operations.layout.Component;
import com.android.internal.widget.remotecompose.core.operations.layout.LayoutComponent;
import com.android.internal.widget.remotecompose.core.semantics.AccessibilitySemantics;
import com.android.internal.widget.remotecompose.core.semantics.AccessibleComponent;
import com.android.internal.widget.remotecompose.core.semantics.CoreSemantics;
import com.android.internal.widget.remotecompose.core.semantics.ScrollableComponent;
import java.util.List;

/* loaded from: classes6.dex */
public abstract class BaseSemanticNodeApplier<N> implements SemanticNodeApplier<N> {
    private static final String LOG_TAG = "RemoteCompose";

    protected abstract void applyListItem(N n, int i);

    protected abstract void applyScrollable(N n, ScrollableComponent.ScrollAxisRange scrollAxisRange, int i);

    protected abstract CharSequence getContentDescription(N n);

    protected abstract CharSequence getStateDescription(N n);

    protected abstract CharSequence getText(N n);

    protected abstract void setBoundsInScreen(N n, Rect rect);

    protected abstract void setClickable(N n, boolean z);

    protected abstract void setContentDescription(N n, CharSequence charSequence);

    protected abstract void setEnabled(N n, boolean z);

    protected abstract void setRoleDescription(N n, String str);

    protected abstract void setStateDescription(N n, CharSequence charSequence);

    protected abstract void setText(N n, CharSequence charSequence);

    protected abstract void setUniqueId(N n, String str);

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.internal.widget.remotecompose.accessibility.SemanticNodeApplier
    public void applyComponent(RemoteComposeDocumentAccessibility remoteComposeDocumentAccessibility, N n, Component component, List<AccessibilitySemantics> list) {
        float[] fArr = new float[2];
        component.getLocationInWindow(fArr);
        float f = fArr[0];
        setBoundsInScreen(n, new Rect((int) f, (int) fArr[1], (int) (f + component.getWidth()), (int) (fArr[1] + component.getHeight())));
        setUniqueId(n, String.valueOf(component.getComponentId()));
        if (component instanceof AccessibleComponent) {
            AccessibleComponent accessibleComponent = (AccessibleComponent) component;
            applyContentDescription(accessibleComponent.getContentDescriptionId(), n, remoteComposeDocumentAccessibility);
            applyText(accessibleComponent.getTextId(), n, remoteComposeDocumentAccessibility);
            applyRole(accessibleComponent.getRole(), n);
        }
        applySemantics(remoteComposeDocumentAccessibility, n, list);
        if (getText(n) == null && getContentDescription(n) == null) {
            setContentDescription(n, "");
        }
        if (component.getParent() instanceof LayoutComponent) {
            LayoutComponent layoutComponent = (LayoutComponent) component.getParent();
            if (((ScrollableComponent) layoutComponent.selfOrModifier(ScrollableComponent.class)) != null) {
                applyListItem(n, layoutComponent.getComponentId());
            }
        }
    }

    protected void applySemantics(RemoteComposeDocumentAccessibility remoteComposeDocumentAccessibility, N n, List<AccessibilitySemantics> list) {
        for (AccessibilitySemantics accessibilitySemantics : list) {
            if (accessibilitySemantics.isInterestingForSemantics()) {
                if (accessibilitySemantics instanceof CoreSemantics) {
                    applyCoreSemantics(remoteComposeDocumentAccessibility, n, (CoreSemantics) accessibilitySemantics);
                } else if (accessibilitySemantics instanceof AccessibleComponent) {
                    AccessibleComponent accessibleComponent = (AccessibleComponent) accessibilitySemantics;
                    if (accessibleComponent.isClickable()) {
                        setClickable(n, true);
                    }
                    if (accessibleComponent.getContentDescriptionId() != null) {
                        applyContentDescription(accessibleComponent.getContentDescriptionId(), n, remoteComposeDocumentAccessibility);
                    }
                    if (accessibleComponent.getTextId() != null) {
                        applyText(accessibleComponent.getTextId(), n, remoteComposeDocumentAccessibility);
                    }
                    applyRole(accessibleComponent.getRole(), n);
                } else if (accessibilitySemantics instanceof ScrollableComponent) {
                    ScrollableComponent scrollableComponent = (ScrollableComponent) accessibilitySemantics;
                    if (scrollableComponent.supportsScrollByOffset()) {
                        applyScrollable(n, scrollableComponent.getScrollAxisRange(), scrollableComponent.scrollDirection());
                    }
                } else {
                    Log.w(LOG_TAG, "Unknown semantic: " + accessibilitySemantics);
                }
            }
        }
    }

    protected void applyCoreSemantics(RemoteComposeDocumentAccessibility remoteComposeDocumentAccessibility, N n, CoreSemantics coreSemantics) {
        applyContentDescription(coreSemantics.getContentDescriptionId(), n, remoteComposeDocumentAccessibility);
        applyRole(coreSemantics.getRole(), n);
        applyText(coreSemantics.getTextId(), n, remoteComposeDocumentAccessibility);
        applyStateDescription(coreSemantics.getStateDescriptionId(), n, remoteComposeDocumentAccessibility);
        if (coreSemantics.mEnabled) {
            return;
        }
        setEnabled(n, false);
    }

    protected void applyStateDescription(Integer num, N n, RemoteComposeDocumentAccessibility remoteComposeDocumentAccessibility) {
        if (num != null) {
            setStateDescription(n, appendNullable(getStateDescription(n), remoteComposeDocumentAccessibility.stringValue(num.intValue())));
        }
    }

    protected void applyRole(AccessibleComponent.Role role, N n) {
        if (role != null) {
            setRoleDescription(n, role.getDescription());
        }
    }

    protected void applyText(Integer num, N n, RemoteComposeDocumentAccessibility remoteComposeDocumentAccessibility) {
        if (num != null) {
            setText(n, appendNullable(getText(n), remoteComposeDocumentAccessibility.stringValue(num.intValue())));
        }
    }

    protected void applyContentDescription(Integer num, N n, RemoteComposeDocumentAccessibility remoteComposeDocumentAccessibility) {
        if (num != null) {
            setContentDescription(n, appendNullable(getContentDescription(n), remoteComposeDocumentAccessibility.stringValue(num.intValue())));
        }
    }

    private CharSequence appendNullable(CharSequence charSequence, String str) {
        if (charSequence == null) {
            return str;
        }
        if (str == null) {
            return charSequence;
        }
        return ((Object) charSequence) + " " + str;
    }
}
