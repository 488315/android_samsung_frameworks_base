package com.android.systemui.keyguard.data.quickaffordance;

import android.app.AlertDialog;
import android.content.Intent;
import com.android.systemui.animation.Expandable;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.keyguard.shared.quickaffordance.ActivationState;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface KeyguardQuickAffordanceConfig {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class LockScreenState {

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class Hidden extends LockScreenState {
            public static final Hidden INSTANCE = new Hidden();

            private Hidden() {
                super(null);
            }
        }

        private LockScreenState() {
        }

        public /* synthetic */ LockScreenState(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class Visible extends LockScreenState {
            public final ActivationState activationState;
            public final Icon icon;

            public /* synthetic */ Visible(Icon icon, ActivationState activationState, int i, DefaultConstructorMarker defaultConstructorMarker) {
                this(icon, (i & 2) != 0 ? ActivationState.NotSupported.INSTANCE : activationState);
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof Visible)) {
                    return false;
                }
                Visible visible = (Visible) obj;
                return Intrinsics.areEqual(this.icon, visible.icon) && Intrinsics.areEqual(this.activationState, visible.activationState);
            }

            public final int hashCode() {
                return this.activationState.hashCode() + (this.icon.hashCode() * 31);
            }

            public final String toString() {
                return "Visible(icon=" + this.icon + ", activationState=" + this.activationState + ")";
            }

            public Visible(Icon icon, ActivationState activationState) {
                super(null);
                this.icon = icon;
                this.activationState = activationState;
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class OnTriggeredResult {

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class Handled extends OnTriggeredResult {
            public static final Handled INSTANCE = new Handled();

            private Handled() {
                super(null);
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class ShowDialog extends OnTriggeredResult {
            public final AlertDialog dialog;
            public final Expandable expandable;

            public ShowDialog(AlertDialog alertDialog, Expandable expandable) {
                super(null);
                this.dialog = alertDialog;
                this.expandable = expandable;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof ShowDialog)) {
                    return false;
                }
                ShowDialog showDialog = (ShowDialog) obj;
                return Intrinsics.areEqual(this.dialog, showDialog.dialog) && Intrinsics.areEqual(this.expandable, showDialog.expandable);
            }

            public final int hashCode() {
                int hashCode = this.dialog.hashCode() * 31;
                Expandable expandable = this.expandable;
                return hashCode + (expandable == null ? 0 : expandable.hashCode());
            }

            public final String toString() {
                return "ShowDialog(dialog=" + this.dialog + ", expandable=" + this.expandable + ")";
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class StartActivity extends OnTriggeredResult {
            public final boolean canShowWhileLocked;
            public final Intent intent;

            public StartActivity(Intent intent, boolean z) {
                super(null);
                this.intent = intent;
                this.canShowWhileLocked = z;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof StartActivity)) {
                    return false;
                }
                StartActivity startActivity = (StartActivity) obj;
                return Intrinsics.areEqual(this.intent, startActivity.intent) && this.canShowWhileLocked == startActivity.canShowWhileLocked;
            }

            /* JADX WARN: Multi-variable type inference failed */
            public final int hashCode() {
                int hashCode = this.intent.hashCode() * 31;
                boolean z = this.canShowWhileLocked;
                int i = z;
                if (z != 0) {
                    i = 1;
                }
                return hashCode + i;
            }

            public final String toString() {
                return "StartActivity(intent=" + this.intent + ", canShowWhileLocked=" + this.canShowWhileLocked + ")";
            }
        }

        private OnTriggeredResult() {
        }

        public /* synthetic */ OnTriggeredResult(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class PickerScreenState {

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class Default extends PickerScreenState {
            public final Intent configureIntent;

            /* JADX WARN: Multi-variable type inference failed */
            public Default() {
                this(null, 1, 0 == true ? 1 : 0);
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                return (obj instanceof Default) && Intrinsics.areEqual(this.configureIntent, ((Default) obj).configureIntent);
            }

            public final int hashCode() {
                Intent intent = this.configureIntent;
                if (intent == null) {
                    return 0;
                }
                return intent.hashCode();
            }

            public final String toString() {
                return "Default(configureIntent=" + this.configureIntent + ")";
            }

            public /* synthetic */ Default(Intent intent, int i, DefaultConstructorMarker defaultConstructorMarker) {
                this((i & 1) != 0 ? null : intent);
            }

            public Default(Intent intent) {
                super(null);
                this.configureIntent = intent;
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class Disabled extends PickerScreenState {
            public final Intent actionIntent;
            public final String actionText;
            public final String explanation;

            public /* synthetic */ Disabled(String str, String str2, Intent intent, int i, DefaultConstructorMarker defaultConstructorMarker) {
                this(str, (i & 2) != 0 ? null : str2, (i & 4) != 0 ? null : intent);
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof Disabled)) {
                    return false;
                }
                Disabled disabled = (Disabled) obj;
                return Intrinsics.areEqual(this.explanation, disabled.explanation) && Intrinsics.areEqual(this.actionText, disabled.actionText) && Intrinsics.areEqual(this.actionIntent, disabled.actionIntent);
            }

            public final int hashCode() {
                int hashCode = this.explanation.hashCode() * 31;
                String str = this.actionText;
                int hashCode2 = (hashCode + (str == null ? 0 : str.hashCode())) * 31;
                Intent intent = this.actionIntent;
                return hashCode2 + (intent != null ? intent.hashCode() : 0);
            }

            public final String toString() {
                return "Disabled(explanation=" + this.explanation + ", actionText=" + this.actionText + ", actionIntent=" + this.actionIntent + ")";
            }

            public Disabled(String str, String str2, Intent intent) {
                super(null);
                this.explanation = str;
                this.actionText = str2;
                this.actionIntent = intent;
                int length = str.length();
                boolean z = true;
                if (!(length > 0)) {
                    throw new IllegalStateException("Explanation must not be empty!".toString());
                }
                if (!(str2 == null || str2.length() == 0) || intent != null) {
                    if ((str2 == null || str2.length() == 0) || intent == null) {
                        z = false;
                    }
                }
                if (!z) {
                    throw new IllegalStateException("actionText and actionIntent must either both be null/empty or both be\nnon-null and non-empty!".toString());
                }
            }
        }

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class UnavailableOnDevice extends PickerScreenState {
            public static final UnavailableOnDevice INSTANCE = new UnavailableOnDevice();

            private UnavailableOnDevice() {
                super(null);
            }
        }

        private PickerScreenState() {
        }

        public /* synthetic */ PickerScreenState(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    String getKey();

    Flow getLockScreenState();

    int getPickerIconResourceId();

    Object getPickerScreenState(Continuation continuation);

    OnTriggeredResult onTriggered(Expandable expandable);

    String pickerName();
}
