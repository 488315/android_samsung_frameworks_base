package com.android.systemui.plank.command;

import android.os.Bundle;
import com.android.systemui.Dependency;
import com.android.systemui.globalactions.GlobalActionsComponent;
import com.android.systemui.globalactions.presentation.features.FakeFeatures;
import com.android.systemui.globalactions.util.FakeConditionChecker;
import com.samsung.android.knox.ucm.configurator.UniversalCredentialManager;
import kotlin.NoWhenBranchMatchedException;
import kotlin.enums.EnumEntriesKt;

/* loaded from: classes2.dex */
public final class GlobalActionCommandDispatcher implements PlankCommandDispatcher {

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class Action {
        public static final /* synthetic */ Action[] $VALUES;
        public static final Action add_condition;
        public static final Action add_feature;
        public static final Action hide;
        public static final Action remove_condition;
        public static final Action remove_feature;
        public static final Action reset;
        public static final Action show;
        public static final Action unknown;

        static {
            Action action = new Action("show", 0);
            show = action;
            Action action2 = new Action("hide", 1);
            hide = action2;
            Action action3 = new Action("add_feature", 2);
            add_feature = action3;
            Action action4 = new Action("remove_feature", 3);
            remove_feature = action4;
            Action action5 = new Action("add_condition", 4);
            add_condition = action5;
            Action action6 = new Action("remove_condition", 5);
            remove_condition = action6;
            Action action7 = new Action(UniversalCredentialManager.RESET_APPLET_FORM_FACTOR, 6);
            reset = action7;
            Action action8 = new Action("unknown", 7);
            unknown = action8;
            Action[] actionArr = {action, action2, action3, action4, action5, action6, action7, action8};
            $VALUES = actionArr;
            EnumEntriesKt.enumEntries(actionArr);
        }

        private Action(String str, int i) {
        }

        public static Action valueOf(String str) {
            return (Action) Enum.valueOf(Action.class, str);
        }

        public static Action[] values() {
            return (Action[]) $VALUES.clone();
        }
    }

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[Action.values().length];
            try {
                iArr[Action.show.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[Action.hide.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[Action.add_feature.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[Action.remove_feature.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[Action.add_condition.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[Action.remove_condition.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[Action.reset.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[Action.unknown.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @Override // com.android.systemui.plank.command.PlankCommandDispatcher
    public final Bundle dispatch(Bundle bundle, String str) {
        Action actionValueOf;
        Bundle bundle2 = new Bundle();
        bundle2.putBoolean("key_monitor_result", true);
        try {
            actionValueOf = Action.valueOf(str);
        } catch (Exception unused) {
            actionValueOf = Action.unknown;
        }
        switch (WhenMappings.$EnumSwitchMapping$0[actionValueOf.ordinal()]) {
            case 1:
            case 2:
                ((GlobalActionsComponent) Dependency.sDependency.getDependencyInner(GlobalActionsComponent.class)).handleShowGlobalActionsMenu(-1);
                return bundle2;
            case 3:
                FakeFeatures.sInstance.updateFeature(bundle != null ? bundle.getString("key_string_type") : null, bundle != null ? Boolean.valueOf(bundle.getBoolean("key_boolean_type")) : null);
                return bundle2;
            case 4:
                String string = bundle != null ? bundle.getString("key_string_type") : null;
                FakeFeatures fakeFeatures = FakeFeatures.sInstance;
                fakeFeatures.getClass();
                FakeFeatures.sConditionMap.remove(string);
                fakeFeatures.mLogWrapper.v("FakeFeatures", "removed");
                return bundle2;
            case 5:
                FakeConditionChecker.sInstance.updateCondition(bundle != null ? bundle.getString("key_string_type") : null, bundle != null ? Boolean.valueOf(bundle.getBoolean("key_boolean_type")) : null);
                return bundle2;
            case 6:
                String string2 = bundle != null ? bundle.getString("key_string_type") : null;
                FakeConditionChecker fakeConditionChecker = FakeConditionChecker.sInstance;
                fakeConditionChecker.getClass();
                FakeConditionChecker.sConditionMap.remove(string2);
                fakeConditionChecker.mLogWrapper.v("FakeConditionChecker", "removed");
                return bundle2;
            case 7:
                FakeFeatures.sInstance.getClass();
                FakeFeatures.sConditionMap.clear();
                FakeConditionChecker.sInstance.getClass();
                FakeConditionChecker.sConditionMap.clear();
                return bundle2;
            case 8:
                bundle2.putBoolean("key_monitor_result", false);
                return bundle2;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }
}
