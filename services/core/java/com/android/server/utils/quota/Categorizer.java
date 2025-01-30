package com.android.server.utils.quota;

/* loaded from: classes3.dex */
public interface Categorizer {
    public static final Categorizer SINGLE_CATEGORIZER = new Categorizer() { // from class: com.android.server.utils.quota.Categorizer$$ExternalSyntheticLambda0
        @Override // com.android.server.utils.quota.Categorizer
        public final Category getCategory(int i, String str, String str2) {
            Category lambda$static$0;
            lambda$static$0 = Categorizer.lambda$static$0(i, str, str2);
            return lambda$static$0;
        }
    };

    Category getCategory(int i, String str, String str2);

    static /* synthetic */ Category lambda$static$0(int i, String str, String str2) {
        return Category.SINGLE_CATEGORY;
    }
}
