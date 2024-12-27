package com.android.server.pm;

import android.content.pm.Signature;
import android.content.pm.SigningDetails;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.pm.pkg.AndroidPackage;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class Policy {
    public final Set mCerts;
    public final Map mPkgMap;
    public final String mSeinfo;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PolicyBuilder {
        public final Set mCerts = new HashSet(2);
        public final Map mPkgMap = new HashMap(2);
        public String mSeinfo;

        public final void addSignature(String str) {
            if (str == null) {
                throw new IllegalArgumentException(ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("Invalid signature value ", str));
            }
            ((HashSet) this.mCerts).add(new Signature(str));
        }
    }

    public Policy(PolicyBuilder policyBuilder) {
        this.mSeinfo = policyBuilder.mSeinfo;
        this.mCerts = Collections.unmodifiableSet(policyBuilder.mCerts);
        this.mPkgMap = Collections.unmodifiableMap(policyBuilder.mPkgMap);
    }

    public final String getMatchedSeInfo(AndroidPackage androidPackage) {
        Signature[] signatureArr = (Signature[]) this.mCerts.toArray(new Signature[0]);
        if (androidPackage.getSigningDetails() != SigningDetails.UNKNOWN && !Signature.areExactMatch(androidPackage.getSigningDetails(), signatureArr) && (signatureArr.length > 1 || !androidPackage.getSigningDetails().hasCertificate(signatureArr[0]))) {
            return null;
        }
        String str = (String) this.mPkgMap.get(androidPackage.getPackageName());
        return str != null ? str : this.mSeinfo;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        Iterator it = this.mCerts.iterator();
        while (it.hasNext()) {
            sb.append("cert=" + ((Signature) it.next()).toCharsString().substring(0, 11) + "... ");
        }
        String str = this.mSeinfo;
        if (str != null) {
            sb.append("seinfo=" + str);
        }
        for (String str2 : this.mPkgMap.keySet()) {
            StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m(" ", str2, "=");
            m.append((String) this.mPkgMap.get(str2));
            sb.append(m.toString());
        }
        return sb.toString();
    }
}
