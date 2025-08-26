package com.android.systemui.navigationbar.gestural.domain;

import android.content.ComponentName;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public interface TaskMatcher {

    public final class TopActivityComponent implements TaskMatcher {
        public final ComponentName component;

        public TopActivityComponent(ComponentName componentName) {
            this.component = componentName;
        }

        @Override // com.android.systemui.navigationbar.gestural.domain.TaskMatcher
        public final boolean matches(TaskInfo taskInfo) {
            return Intrinsics.areEqual(this.component, taskInfo.topActivity);
        }
    }

    public final class TopActivityType implements TaskMatcher {
        public final int type;

        public TopActivityType(int i) {
            this.type = i;
        }

        @Override // com.android.systemui.navigationbar.gestural.domain.TaskMatcher
        public final boolean matches(TaskInfo taskInfo) {
            if (taskInfo.topActivity != null) {
                return taskInfo.topActivityType == this.type;
            }
            return false;
        }
    }

    boolean matches(TaskInfo taskInfo);
}
