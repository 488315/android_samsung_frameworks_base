package com.android.server.textservices;

import android.view.textservice.SpellCheckerInfo;

public abstract class TextServicesManagerInternal {
    public static final AnonymousClass1 NOP = new AnonymousClass1();

    /* renamed from: com.android.server.textservices.TextServicesManagerInternal$1, reason: invalid class name */
    public final class AnonymousClass1 extends TextServicesManagerInternal {
        @Override // com.android.server.textservices.TextServicesManagerInternal
        public final SpellCheckerInfo getCurrentSpellCheckerForUser(int i) {
            return null;
        }
    }

    public abstract SpellCheckerInfo getCurrentSpellCheckerForUser(int i);
}
