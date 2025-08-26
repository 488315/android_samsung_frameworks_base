package com.android.systemui.statusbar.pipeline.shared.ui.model;

import com.android.systemui.log.table.Diffable;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.statusbar.events.shared.model.SystemEventAnimationState;
import com.android.systemui.util.DumpUtilsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class SystemInfoCombinedVisibilityModel implements Diffable {
    public final SystemEventAnimationState animationState;
    public final VisibilityModel baseVisibility;

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

    public SystemInfoCombinedVisibilityModel(VisibilityModel visibilityModel, SystemEventAnimationState systemEventAnimationState) {
        this.baseVisibility = visibilityModel;
        this.animationState = systemEventAnimationState;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SystemInfoCombinedVisibilityModel)) {
            return false;
        }
        SystemInfoCombinedVisibilityModel systemInfoCombinedVisibilityModel = (SystemInfoCombinedVisibilityModel) obj;
        return Intrinsics.areEqual(this.baseVisibility, systemInfoCombinedVisibilityModel.baseVisibility) && this.animationState == systemInfoCombinedVisibilityModel.animationState;
    }

    public final int hashCode() {
        return this.animationState.hashCode() + (this.baseVisibility.hashCode() * 31);
    }

    @Override // com.android.systemui.log.table.Diffable
    public final void logDiffs(Diffable diffable, TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
        SystemInfoCombinedVisibilityModel systemInfoCombinedVisibilityModel = (SystemInfoCombinedVisibilityModel) diffable;
        SystemEventAnimationState systemEventAnimationState = systemInfoCombinedVisibilityModel.animationState;
        SystemEventAnimationState systemEventAnimationState2 = this.animationState;
        if (systemEventAnimationState2 != systemEventAnimationState) {
            tableRowLoggerImpl.logChange("animState", systemEventAnimationState2.name());
        }
        VisibilityModel visibilityModel = this.baseVisibility;
        visibilityModel.getClass();
        VisibilityModel visibilityModel2 = systemInfoCombinedVisibilityModel.baseVisibility;
        int i = visibilityModel2.visibility;
        int i2 = visibilityModel.visibility;
        if (i2 != i) {
            tableRowLoggerImpl.logChange("vis", DumpUtilsKt.visibilityString(i2));
        }
        boolean z = visibilityModel2.shouldAnimateChange;
        boolean z2 = visibilityModel.shouldAnimateChange;
        if (z2 != z) {
            tableRowLoggerImpl.logChange("animate", z2);
        }
    }

    @Override // com.android.systemui.log.table.Diffable
    public final void logFull(TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
        tableRowLoggerImpl.logChange("animState", this.animationState.name());
        this.baseVisibility.logFull(tableRowLoggerImpl);
    }

    public final String toString() {
        return "SystemInfoCombinedVisibilityModel(baseVisibility=" + this.baseVisibility + ", animationState=" + this.animationState + ")";
    }
}
