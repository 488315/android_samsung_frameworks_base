package android.content.pm;

import android.content.Intent;
import android.os.Bundle;

/* loaded from: classes.dex */
public final class InstantAppRequest {
    public final String callingFeatureId;
    public final String callingPackage;
    public final int[] hostDigestPrefixSecure;
    public final boolean isRequesterInstantApp;
    public final Intent origIntent;
    public final boolean resolveForStart;
    public final String resolvedType;
    public final AuxiliaryResolveInfo responseObj;
    public final String token;
    public final int userId;
    public final Bundle verificationBundle;

    public InstantAppRequest(AuxiliaryResolveInfo auxiliaryResolveInfo, Intent intent, String str, String str2, String str3, boolean z, int i, Bundle bundle, boolean z2, int[] iArr, String str4) {
        this.responseObj = auxiliaryResolveInfo;
        this.origIntent = intent;
        this.resolvedType = str;
        this.callingPackage = str2;
        this.callingFeatureId = str3;
        this.isRequesterInstantApp = z;
        this.userId = i;
        this.verificationBundle = bundle;
        this.resolveForStart = z2;
        this.hostDigestPrefixSecure = iArr;
        this.token = str4;
    }
}
