package com.android.systemui.controls.util;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.picker.model.AppInfo$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ControlsBackUpRestore$BNRRequest {
    public final ControlsBackUpRestore$BNRAction action;
    public final String exportSessionTime;
    public final ArrayList extraBackupItem;
    public final String intentAction;
    public final String savePath;
    public final ControlsBackUpRestore$BNRSecurityLevel securityLevel;
    public final String sessionKey;
    public final String source;

    public /* synthetic */ ControlsBackUpRestore$BNRRequest(String str, String str2, ControlsBackUpRestore$BNRAction controlsBackUpRestore$BNRAction, String str3, String str4, ControlsBackUpRestore$BNRSecurityLevel controlsBackUpRestore$BNRSecurityLevel, ArrayList arrayList, String str5, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, controlsBackUpRestore$BNRAction, str3, str4, controlsBackUpRestore$BNRSecurityLevel, arrayList, str5);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ControlsBackUpRestore$BNRRequest)) {
            return false;
        }
        ControlsBackUpRestore$BNRRequest controlsBackUpRestore$BNRRequest = (ControlsBackUpRestore$BNRRequest) obj;
        return Intrinsics.areEqual(this.intentAction, controlsBackUpRestore$BNRRequest.intentAction) && Intrinsics.areEqual(this.savePath, controlsBackUpRestore$BNRRequest.savePath) && this.action == controlsBackUpRestore$BNRRequest.action && Intrinsics.areEqual(this.sessionKey, controlsBackUpRestore$BNRRequest.sessionKey) && Intrinsics.areEqual(this.source, controlsBackUpRestore$BNRRequest.source) && this.securityLevel == controlsBackUpRestore$BNRRequest.securityLevel && Intrinsics.areEqual(this.extraBackupItem, controlsBackUpRestore$BNRRequest.extraBackupItem) && Intrinsics.areEqual(this.exportSessionTime, controlsBackUpRestore$BNRRequest.exportSessionTime);
    }

    public final int hashCode() {
        String str = this.intentAction;
        int hashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.savePath;
        int hashCode2 = (this.extraBackupItem.hashCode() + ((this.securityLevel.hashCode() + AppInfo$$ExternalSyntheticOutline0.m41m(this.source, AppInfo$$ExternalSyntheticOutline0.m41m(this.sessionKey, (this.action.hashCode() + ((hashCode + (str2 == null ? 0 : str2.hashCode())) * 31)) * 31, 31), 31)) * 31)) * 31;
        String str3 = this.exportSessionTime;
        return hashCode2 + (str3 != null ? str3.hashCode() : 0);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("BNRRequest(intentAction=");
        sb.append(this.intentAction);
        sb.append(", savePath=");
        sb.append(this.savePath);
        sb.append(", action=");
        sb.append(this.action);
        sb.append(", sessionKey=, source=");
        sb.append(this.source);
        sb.append(", securityLevel=");
        sb.append(this.securityLevel);
        sb.append(", extraBackupItem=");
        sb.append(this.extraBackupItem);
        sb.append(", exportSessionTime=");
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(sb, this.exportSessionTime, ")");
    }

    private ControlsBackUpRestore$BNRRequest(String str, String str2, ControlsBackUpRestore$BNRAction controlsBackUpRestore$BNRAction, String str3, String str4, ControlsBackUpRestore$BNRSecurityLevel controlsBackUpRestore$BNRSecurityLevel, ArrayList<String> arrayList, String str5) {
        this.intentAction = str;
        this.savePath = str2;
        this.action = controlsBackUpRestore$BNRAction;
        this.sessionKey = str3;
        this.source = str4;
        this.securityLevel = controlsBackUpRestore$BNRSecurityLevel;
        this.extraBackupItem = arrayList;
        this.exportSessionTime = str5;
    }
}
