package com.android.systemui.kairos;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.datastore.preferences.core.MutablePreferences$$ExternalSyntheticOutline0;
import com.android.systemui.kairos.internal.CompletableLazy;
import com.android.systemui.kairos.internal.IncrementalImpl;
import com.android.systemui.kairos.internal.Init;
import com.android.systemui.kairos.internal.NetworkScope;
import com.android.systemui.kairos.internal.util.UtilKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class IncrementalLoop extends Incremental {
    public final CompletableLazy deferred;
    public final Init init;
    public final String name;

    public IncrementalLoop() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    @Override // com.android.systemui.kairos.State
    public final Init getInit$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos() {
        return this.init;
    }

    public final String toString() {
        String simpleName = Reflection.getOrCreateKotlinClass(IncrementalLoop.class).getSimpleName();
        return MutablePreferences$$ExternalSyntheticOutline0.m(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(simpleName, "("), this.name, ")@", UtilKt.getHashString(this));
    }

    public IncrementalLoop(String str) {
        super(null);
        this.name = str;
        this.deferred = new CompletableLazy(null, str, 1, null);
        this.init = new Init(str, new Function1() { // from class: com.android.systemui.kairos.IncrementalLoop$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                return (IncrementalImpl) ((Incremental) IncrementalLoop.this.deferred.getValue()).getInit$frameworks__base__packages__SystemUI__utils__kairos__android_common__kairos().connect((NetworkScope) obj);
            }
        });
    }

    public /* synthetic */ IncrementalLoop(String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : str);
    }
}
