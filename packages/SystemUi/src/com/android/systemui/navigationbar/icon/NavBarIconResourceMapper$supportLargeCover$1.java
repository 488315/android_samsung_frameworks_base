package com.android.systemui.navigationbar.icon;

import com.android.systemui.BasicRune;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class NavBarIconResourceMapper$supportLargeCover$1 extends Lambda implements Function0 {
    final /* synthetic */ NavBarIconResourceMapper this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NavBarIconResourceMapper$supportLargeCover$1(NavBarIconResourceMapper navBarIconResourceMapper) {
        super(0);
        this.this$0 = navBarIconResourceMapper;
    }

    /* JADX WARN: Code restructure failed: missing block: B:4:0x000d, code lost:
    
        if (r1.this$0.context.getDisplayId() == 1) goto L8;
     */
    @Override // kotlin.jvm.functions.Function0
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invoke() {
        boolean z = BasicRune.NAVBAR_SUPPORT_LARGE_COVER_SCREEN;
        return Boolean.valueOf(z);
    }
}
