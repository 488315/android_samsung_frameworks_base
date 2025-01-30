package com.android.systemui.controls.management.model;

import android.app.PendingIntent;
import android.content.ComponentName;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import com.android.systemui.controls.management.model.MainModel;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MainPanelModel extends MainModel {
    public final ComponentName panelActivity;
    public final PendingIntent pendingIntent;
    public final boolean settings;
    public final MainModel.Type type = MainModel.Type.PANEL;

    public MainPanelModel(PendingIntent pendingIntent, ComponentName componentName, boolean z) {
        this.pendingIntent = pendingIntent;
        this.panelActivity = componentName;
        this.settings = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MainPanelModel)) {
            return false;
        }
        MainPanelModel mainPanelModel = (MainPanelModel) obj;
        return Intrinsics.areEqual(this.pendingIntent, mainPanelModel.pendingIntent) && Intrinsics.areEqual(this.panelActivity, mainPanelModel.panelActivity) && this.settings == mainPanelModel.settings;
    }

    @Override // com.android.systemui.controls.management.model.MainModel
    public final MainModel.Type getType() {
        return this.type;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        int hashCode = this.pendingIntent.hashCode() * 31;
        ComponentName componentName = this.panelActivity;
        int hashCode2 = (hashCode + (componentName == null ? 0 : componentName.hashCode())) * 31;
        boolean z = this.settings;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        return hashCode2 + i;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("MainPanelModel(pendingIntent=");
        sb.append(this.pendingIntent);
        sb.append(", panelActivity=");
        sb.append(this.panelActivity);
        sb.append(", settings=");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m6m(sb, this.settings, ")");
    }
}
