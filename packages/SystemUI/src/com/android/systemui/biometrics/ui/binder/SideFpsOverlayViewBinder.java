package com.android.systemui.biometrics.ui.binder;

import android.content.Context;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.android.systemui.CoreStartable;
import com.android.systemui.R;
import dagger.Lazy;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes.dex */
public final class SideFpsOverlayViewBinder implements CoreStartable {

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public SideFpsOverlayViewBinder(CoroutineScope coroutineScope, Context context, Lazy lazy, Lazy lazy2, Lazy lazy3, Lazy lazy4, Lazy lazy5, Lazy lazy6, Lazy lazy7) {
        new AccessibilityDelegateCompat() { // from class: com.android.systemui.biometrics.ui.binder.SideFpsOverlayViewBinder$pauseDelegate$1
            @Override // androidx.core.view.AccessibilityDelegateCompat
            public final boolean dispatchPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
                if (accessibilityEvent.getEventType() == 32) {
                    return true;
                }
                return this.mOriginalDelegate.dispatchPopulateAccessibilityEvent(view, accessibilityEvent);
            }

            @Override // androidx.core.view.AccessibilityDelegateCompat
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                this.mOriginalDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat.mInfo);
                accessibilityNodeInfoCompat.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(16, view.getContext().getString(R.string.pause_animation)));
            }
        };
        new AccessibilityDelegateCompat() { // from class: com.android.systemui.biometrics.ui.binder.SideFpsOverlayViewBinder$resumeDelegate$1
            @Override // androidx.core.view.AccessibilityDelegateCompat
            public final boolean dispatchPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
                if (accessibilityEvent.getEventType() == 32) {
                    return true;
                }
                return this.mOriginalDelegate.dispatchPopulateAccessibilityEvent(view, accessibilityEvent);
            }

            @Override // androidx.core.view.AccessibilityDelegateCompat
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                this.mOriginalDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat.mInfo);
                accessibilityNodeInfoCompat.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(16, view.getContext().getString(R.string.resume_animation)));
            }
        };
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
    }
}
