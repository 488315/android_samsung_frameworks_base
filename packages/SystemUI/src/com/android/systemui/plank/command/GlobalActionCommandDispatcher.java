package com.android.systemui.plank.command;

import com.samsung.android.knox.ucm.configurator.UniversalCredentialManager;
import kotlin.enums.EnumEntriesKt;

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

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x00b1, code lost:
    
        return r5;
     */
    @Override // com.android.systemui.plank.command.PlankCommandDispatcher
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.os.Bundle dispatch(android.os.Bundle r6, java.lang.String r7) {
        /*
            r5 = this;
            android.os.Bundle r5 = new android.os.Bundle
            r5.<init>()
            java.lang.String r0 = "key_monitor_result"
            r1 = 1
            r5.putBoolean(r0, r1)
            com.android.systemui.plank.command.GlobalActionCommandDispatcher$Action r7 = com.android.systemui.plank.command.GlobalActionCommandDispatcher.Action.valueOf(r7)     // Catch: java.lang.Exception -> L10
            goto L12
        L10:
            com.android.systemui.plank.command.GlobalActionCommandDispatcher$Action r7 = com.android.systemui.plank.command.GlobalActionCommandDispatcher.Action.unknown
        L12:
            int[] r1 = com.android.systemui.plank.command.GlobalActionCommandDispatcher.WhenMappings.$EnumSwitchMapping$0
            int r7 = r7.ordinal()
            r7 = r1[r7]
            java.lang.String r1 = "removed"
            java.lang.String r2 = "key_boolean_type"
            java.lang.String r3 = "key_string_type"
            r4 = 0
            switch(r7) {
                case 1: goto La3;
                case 2: goto La3;
                case 3: goto L8b;
                case 4: goto L73;
                case 5: goto L5b;
                case 6: goto L43;
                case 7: goto L2d;
                case 8: goto L27;
                default: goto L25;
            }
        L25:
            goto Lb1
        L27:
            r6 = 0
            r5.putBoolean(r0, r6)
            goto Lb1
        L2d:
            com.android.systemui.globalactions.presentation.features.FakeFeatures r6 = com.android.systemui.globalactions.presentation.features.FakeFeatures.sInstance
            r6.getClass()
            java.util.HashMap r6 = com.android.systemui.globalactions.presentation.features.FakeFeatures.sConditionMap
            r6.clear()
            com.android.systemui.globalactions.util.FakeConditionChecker r6 = com.android.systemui.globalactions.util.FakeConditionChecker.sInstance
            r6.getClass()
            java.util.HashMap r6 = com.android.systemui.globalactions.util.FakeConditionChecker.sConditionMap
            r6.clear()
            goto Lb1
        L43:
            if (r6 == 0) goto L49
            java.lang.String r4 = r6.getString(r3)
        L49:
            com.android.systemui.globalactions.util.FakeConditionChecker r6 = com.android.systemui.globalactions.util.FakeConditionChecker.sInstance
            r6.getClass()
            java.util.HashMap r7 = com.android.systemui.globalactions.util.FakeConditionChecker.sConditionMap
            r7.remove(r4)
            com.samsung.android.globalactions.util.LogWrapper r6 = r6.mLogWrapper
            java.lang.String r7 = "FakeConditionChecker"
            r6.v(r7, r1)
            goto Lb1
        L5b:
            if (r6 == 0) goto L62
            java.lang.String r7 = r6.getString(r3)
            goto L63
        L62:
            r7 = r4
        L63:
            if (r6 == 0) goto L6d
            boolean r6 = r6.getBoolean(r2)
            java.lang.Boolean r4 = java.lang.Boolean.valueOf(r6)
        L6d:
            com.android.systemui.globalactions.util.FakeConditionChecker r6 = com.android.systemui.globalactions.util.FakeConditionChecker.sInstance
            r6.updateCondition(r7, r4)
            goto Lb1
        L73:
            if (r6 == 0) goto L79
            java.lang.String r4 = r6.getString(r3)
        L79:
            com.android.systemui.globalactions.presentation.features.FakeFeatures r6 = com.android.systemui.globalactions.presentation.features.FakeFeatures.sInstance
            r6.getClass()
            java.util.HashMap r7 = com.android.systemui.globalactions.presentation.features.FakeFeatures.sConditionMap
            r7.remove(r4)
            com.samsung.android.globalactions.util.LogWrapper r6 = r6.mLogWrapper
            java.lang.String r7 = "FakeFeatures"
            r6.v(r7, r1)
            goto Lb1
        L8b:
            if (r6 == 0) goto L92
            java.lang.String r7 = r6.getString(r3)
            goto L93
        L92:
            r7 = r4
        L93:
            if (r6 == 0) goto L9d
            boolean r6 = r6.getBoolean(r2)
            java.lang.Boolean r4 = java.lang.Boolean.valueOf(r6)
        L9d:
            com.android.systemui.globalactions.presentation.features.FakeFeatures r6 = com.android.systemui.globalactions.presentation.features.FakeFeatures.sInstance
            r6.updateFeature(r7, r4)
            goto Lb1
        La3:
            com.android.systemui.Dependency r6 = com.android.systemui.Dependency.sDependency
            java.lang.Class<com.android.systemui.globalactions.GlobalActionsComponent> r7 = com.android.systemui.globalactions.GlobalActionsComponent.class
            java.lang.Object r6 = r6.getDependencyInner(r7)
            com.android.systemui.globalactions.GlobalActionsComponent r6 = (com.android.systemui.globalactions.GlobalActionsComponent) r6
            r7 = -1
            r6.handleShowGlobalActionsMenu(r7)
        Lb1:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.plank.command.GlobalActionCommandDispatcher.dispatch(android.os.Bundle, java.lang.String):android.os.Bundle");
    }
}
