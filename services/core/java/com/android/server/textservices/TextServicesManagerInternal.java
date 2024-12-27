package com.android.server.textservices;

import android.view.textservice.SpellCheckerInfo;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class TextServicesManagerInternal {
    public static final AnonymousClass1 NOP = new AnonymousClass1();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.textservices.TextServicesManagerInternal$1, reason: invalid class name */
    public final class AnonymousClass1 extends TextServicesManagerInternal {
        @Override // com.android.server.textservices.TextServicesManagerInternal
        public final SpellCheckerInfo getCurrentSpellCheckerForUser(int i) {
            return null;
        }
    }

    public abstract SpellCheckerInfo getCurrentSpellCheckerForUser(int i);
}
