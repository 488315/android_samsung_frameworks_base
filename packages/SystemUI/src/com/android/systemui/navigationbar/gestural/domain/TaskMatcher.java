package com.android.systemui.navigationbar.gestural.domain;

import android.content.ComponentName;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface TaskMatcher {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
