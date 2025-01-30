package com.android.systemui.plank.command;

import android.os.Bundle;
import com.android.systemui.Dependency;
import com.android.systemui.globalactions.GlobalActionsComponent;
import com.android.systemui.globalactions.presentation.features.FakeFeatures;
import com.android.systemui.globalactions.util.FakeConditionChecker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class GlobalActionCommandDispatcher implements PlankCommandDispatcher {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum Action {
        show,
        hide,
        add_feature,
        remove_feature,
        add_condition,
        remove_condition,
        reset,
        unknown
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
    /* JADX WARN: Code restructure failed: missing block: B:27:0x00af, code lost:
    
        return r5;
     */
    @Override // com.android.systemui.plank.command.PlankCommandDispatcher
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Bundle dispatch(String str, Bundle bundle) {
        Action action;
        Bundle bundle2 = new Bundle();
        bundle2.putBoolean("key_monitor_result", true);
        try {
            action = Action.valueOf(str);
        } catch (Exception unused) {
            action = Action.unknown;
        }
        switch (WhenMappings.$EnumSwitchMapping$0[action.ordinal()]) {
            case 1:
            case 2:
                ((GlobalActionsComponent) Dependency.get(GlobalActionsComponent.class)).handleShowGlobalActionsMenu(-1);
                break;
            case 3:
                FakeFeatures.sInstance.updateFeature(bundle != null ? bundle.getString("key_string_type") : null, bundle != null ? Boolean.valueOf(bundle.getBoolean("key_boolean_type")) : null);
                break;
            case 4:
                String string = bundle != null ? bundle.getString("key_string_type") : null;
                FakeFeatures fakeFeatures = FakeFeatures.sInstance;
                fakeFeatures.getClass();
                FakeFeatures.sConditionMap.remove(string);
                fakeFeatures.mLogWrapper.v("FakeFeatures", "removed");
                break;
            case 5:
                FakeConditionChecker.sInstance.updateCondition(bundle != null ? bundle.getString("key_string_type") : null, bundle != null ? Boolean.valueOf(bundle.getBoolean("key_boolean_type")) : null);
                break;
            case 6:
                String string2 = bundle != null ? bundle.getString("key_string_type") : null;
                FakeConditionChecker fakeConditionChecker = FakeConditionChecker.sInstance;
                fakeConditionChecker.getClass();
                FakeConditionChecker.sConditionMap.remove(string2);
                fakeConditionChecker.mLogWrapper.v("FakeConditionChecker", "removed");
                break;
            case 7:
                FakeFeatures.sInstance.getClass();
                FakeFeatures.sConditionMap.clear();
                FakeConditionChecker.sInstance.getClass();
                FakeConditionChecker.sConditionMap.clear();
                break;
            case 8:
                bundle2.putBoolean("key_monitor_result", false);
                break;
        }
    }
}
