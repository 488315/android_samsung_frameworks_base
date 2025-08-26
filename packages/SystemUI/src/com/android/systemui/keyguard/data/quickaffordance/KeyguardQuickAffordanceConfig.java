package com.android.systemui.keyguard.data.quickaffordance;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import com.android.systemui.animation.Expandable;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.keyguard.shared.quickaffordance.ActivationState;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.Flow;

/* loaded from: classes2.dex */
public interface KeyguardQuickAffordanceConfig {

    public final class Companion {
        public static final /* synthetic */ int $r8$clinit = 0;

        static {
            new Companion();
        }

        private Companion() {
        }
    }

    public final class LaunchingFromTriggeredResult {
        public final String configKey;
        public final boolean launched;

        public LaunchingFromTriggeredResult(boolean z, String str) {
            this.launched = z;
            this.configKey = str;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof LaunchingFromTriggeredResult)) {
                return false;
            }
            LaunchingFromTriggeredResult launchingFromTriggeredResult = (LaunchingFromTriggeredResult) obj;
            return this.launched == launchingFromTriggeredResult.launched && Intrinsics.areEqual(this.configKey, launchingFromTriggeredResult.configKey);
        }

        public final int hashCode() {
            return this.configKey.hashCode() + (Boolean.hashCode(this.launched) * 31);
        }

        public final String toString() {
            return "LaunchingFromTriggeredResult(launched=" + this.launched + ", configKey=" + this.configKey + ")";
        }
    }

    public abstract class LockScreenState {

        public final class Hidden extends LockScreenState {
            public static final Hidden INSTANCE = new Hidden();

            private Hidden() {
                super(null);
            }
        }

        public /* synthetic */ LockScreenState(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

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

        private LockScreenState() {
        }
    }

    public abstract class OnTriggeredResult {

        public final class Handled extends OnTriggeredResult {
            public final boolean actionLaunched;

            public Handled(boolean z) {
                super(null);
                this.actionLaunched = z;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                return (obj instanceof Handled) && this.actionLaunched == ((Handled) obj).actionLaunched;
            }

            public final int hashCode() {
                return Boolean.hashCode(this.actionLaunched);
            }

            public final String toString() {
                return MoveResult$$ExternalSyntheticOutline0.m(new StringBuilder("Handled(actionLaunched="), this.actionLaunched, ")");
            }
        }

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
                int iHashCode = this.dialog.hashCode() * 31;
                Expandable expandable = this.expandable;
                return iHashCode + (expandable == null ? 0 : expandable.hashCode());
            }

            public final String toString() {
                return "ShowDialog(dialog=" + this.dialog + ", expandable=" + this.expandable + ")";
            }
        }

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

            public final int hashCode() {
                return Boolean.hashCode(this.canShowWhileLocked) + (this.intent.hashCode() * 31);
            }

            public final String toString() {
                return "StartActivity(intent=" + this.intent + ", canShowWhileLocked=" + this.canShowWhileLocked + ")";
            }
        }

        public /* synthetic */ OnTriggeredResult(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private OnTriggeredResult() {
        }
    }

    public abstract class PickerScreenState {

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
                int iHashCode = this.explanation.hashCode() * 31;
                String str = this.actionText;
                int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
                Intent intent = this.actionIntent;
                return iHashCode2 + (intent != null ? intent.hashCode() : 0);
            }

            public final String toString() {
                return "Disabled(explanation=" + this.explanation + ", actionText=" + this.actionText + ", actionIntent=" + this.actionIntent + ")";
            }

            public Disabled(String str, String str2, Intent intent) {
                super(null);
                this.explanation = str;
                this.actionText = str2;
                this.actionIntent = intent;
                if (str.length() <= 0) {
                    throw new IllegalStateException("Explanation must not be empty!");
                }
                if ((str2 == null || str2.length() == 0) && intent == null) {
                    return;
                }
                if (str2 == null || str2.length() == 0 || intent == null) {
                    throw new IllegalStateException("actionText and actionIntent must either both be null/empty or both be\nnon-null and non-empty!");
                }
            }
        }

        public final class UnavailableOnDevice extends PickerScreenState {
            public static final UnavailableOnDevice INSTANCE = new UnavailableOnDevice();

            private UnavailableOnDevice() {
                super(null);
            }
        }

        public /* synthetic */ PickerScreenState(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private PickerScreenState() {
        }
    }

    static {
        int i = Companion.$r8$clinit;
    }

    default Drawable getDrawable() {
        return null;
    }

    String getKey();

    Flow getLockScreenState();

    default Drawable getPanelIconTransitionDrawable() {
        return null;
    }

    int getPickerIconResourceId();

    /* JADX WARN: Multi-variable type inference failed */
    default Object getPickerScreenState(Continuation continuation) {
        return new PickerScreenState.Default(null, 1, 0 == true ? 1 : 0);
    }

    default boolean isAvailable() {
        return true;
    }

    default boolean isIconPaddingRequired() {
        return false;
    }

    default boolean isTaskEnabled() {
        return false;
    }

    default boolean isUnlockWaitRequired() {
        return false;
    }

    OnTriggeredResult onTriggered(Expandable expandable);

    String pickerName();

    default void setSpecName(String str) {
    }

    default void addListener() {
    }

    default void removeListener() {
    }
}
