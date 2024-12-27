package com.android.server;

import android.util.ArraySet;
import android.util.SparseArray;
import android.view.inputmethod.EditorInfo;

import com.android.internal.inputmethod.IRemoteAccessibilityInputConnection;

public abstract class AccessibilityManagerInternal {
    public static final AnonymousClass1 NOP = new AnonymousClass1();

    /* renamed from: com.android.server.AccessibilityManagerInternal$1, reason: invalid class name */
    public final class AnonymousClass1 extends AccessibilityManagerInternal {
        @Override // com.android.server.AccessibilityManagerInternal
        public final void bindInput() {}

        @Override // com.android.server.AccessibilityManagerInternal
        public final void createImeSession(ArraySet arraySet) {}

        @Override // com.android.server.AccessibilityManagerInternal
        public final boolean isTouchExplorationEnabled(int i) {
            return false;
        }

        @Override // com.android.server.AccessibilityManagerInternal
        public final void performSystemAction() {}

        @Override // com.android.server.AccessibilityManagerInternal
        public final void setImeSessionEnabled(SparseArray sparseArray, boolean z) {}

        @Override // com.android.server.AccessibilityManagerInternal
        public final void startInput(
                IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection,
                EditorInfo editorInfo,
                boolean z) {}

        @Override // com.android.server.AccessibilityManagerInternal
        public final void unbindInput() {}
    }

    public static AccessibilityManagerInternal get() {
        AccessibilityManagerInternal accessibilityManagerInternal =
                (AccessibilityManagerInternal)
                        LocalServices.getService(AccessibilityManagerInternal.class);
        return accessibilityManagerInternal != null ? accessibilityManagerInternal : NOP;
    }

    public abstract void bindInput();

    public abstract void createImeSession(ArraySet arraySet);

    public abstract boolean isTouchExplorationEnabled(int i);

    public abstract void performSystemAction();

    public abstract void setImeSessionEnabled(SparseArray sparseArray, boolean z);

    public abstract void startInput(
            IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection,
            EditorInfo editorInfo,
            boolean z);

    public abstract void unbindInput();
}
