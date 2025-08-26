package com.android.wm.shell.compatui.components;

import android.content.Context;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.R;
import com.android.wm.shell.compatui.api.CompatUIInfo;
import com.android.wm.shell.compatui.api.CompatUILayout;
import com.android.wm.shell.compatui.api.CompatUILifecyclePredicates;
import com.android.wm.shell.compatui.api.CompatUISpec;
import kotlin.Unit;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;

/* loaded from: classes3.dex */
public abstract class RestartButtonSpecKt {
    public static final CompatUISpec RestartButtonSpec;

    static {
        final int i = 0;
        CompatUILifecyclePredicates compatUILifecyclePredicates = new CompatUILifecyclePredicates(new RestartButtonSpecKt$$ExternalSyntheticLambda0(), new Function3() { // from class: com.android.wm.shell.compatui.components.RestartButtonSpecKt$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                switch (i) {
                    case 0:
                        CompatUIInfo compatUIInfo = (CompatUIInfo) obj;
                        if (obj3 == null) {
                            return Boolean.valueOf(!compatUIInfo.taskInfo.appCompatTaskInfo.isTopActivityInSizeCompat());
                        }
                        throw new ClassCastException();
                    default:
                        Context context = (Context) obj;
                        if (obj3 == null) {
                            return LayoutInflater.from(context).inflate(R.layout.compat_ui_restart_button_layout, (ViewGroup) null);
                        }
                        throw new ClassCastException();
                }
            }
        }, null, 4, null);
        final int i2 = 1;
        Function3 function3 = new Function3() { // from class: com.android.wm.shell.compatui.components.RestartButtonSpecKt$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                switch (i2) {
                    case 0:
                        CompatUIInfo compatUIInfo = (CompatUIInfo) obj;
                        if (obj3 == null) {
                            return Boolean.valueOf(!compatUIInfo.taskInfo.appCompatTaskInfo.isTopActivityInSizeCompat());
                        }
                        throw new ClassCastException();
                    default:
                        Context context = (Context) obj;
                        if (obj3 == null) {
                            return LayoutInflater.from(context).inflate(R.layout.compat_ui_restart_button_layout, (ViewGroup) null);
                        }
                        throw new ClassCastException();
                }
            }
        };
        final int i3 = 0;
        Function4 function4 = new Function4() { // from class: com.android.wm.shell.compatui.components.RestartButtonSpecKt$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function4
            public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
                View view = (View) obj;
                switch (i3) {
                    case 0:
                        if (obj4 != null) {
                            throw new ClassCastException();
                        }
                        view.setVisibility(0);
                        View viewFindViewById = view.findViewById(R.id.size_compat_restart_button);
                        if (viewFindViewById != null) {
                            viewFindViewById.setVisibility(0);
                        }
                        return Unit.INSTANCE;
                    default:
                        if (obj4 == null) {
                            return new Point(500, 500);
                        }
                        throw new ClassCastException();
                }
            }
        };
        final int i4 = 1;
        RestartButtonSpec = new CompatUISpec(null, "restartButton", compatUILifecyclePredicates, new CompatUILayout(10010, 0, function3, function4, new Function4() { // from class: com.android.wm.shell.compatui.components.RestartButtonSpecKt$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function4
            public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
                View view = (View) obj;
                switch (i4) {
                    case 0:
                        if (obj4 != null) {
                            throw new ClassCastException();
                        }
                        view.setVisibility(0);
                        View viewFindViewById = view.findViewById(R.id.size_compat_restart_button);
                        if (viewFindViewById != null) {
                            viewFindViewById.setVisibility(0);
                        }
                        return Unit.INSTANCE;
                    default:
                        if (obj4 == null) {
                            return new Point(500, 500);
                        }
                        throw new ClassCastException();
                }
            }
        }, null, 34, null), 1, null);
    }
}
