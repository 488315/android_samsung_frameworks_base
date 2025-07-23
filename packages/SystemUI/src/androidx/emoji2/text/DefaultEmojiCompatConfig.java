package androidx.emoji2.text;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import java.util.Collections;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class DefaultEmojiCompatConfig {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class DefaultEmojiCompatConfigFactory {
        public final DefaultEmojiCompatConfigHelper mHelper;

        public DefaultEmojiCompatConfigFactory(DefaultEmojiCompatConfigHelper defaultEmojiCompatConfigHelper) {
            this.mHelper = defaultEmojiCompatConfigHelper == null ? new DefaultEmojiCompatConfigHelper_API28() : defaultEmojiCompatConfigHelper;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class DefaultEmojiCompatConfigHelper {
        public ProviderInfo getProviderInfo(ResolveInfo resolveInfo) {
            throw new IllegalStateException("Unable to get provider info prior to API 19");
        }

        public Signature[] getSigningSignatures(PackageManager packageManager, String str) {
            return packageManager.getPackageInfo(str, 64).signatures;
        }

        public List queryIntentContentProviders(PackageManager packageManager, Intent intent) {
            return Collections.EMPTY_LIST;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class DefaultEmojiCompatConfigHelper_API19 extends DefaultEmojiCompatConfigHelper {
        @Override // androidx.emoji2.text.DefaultEmojiCompatConfig.DefaultEmojiCompatConfigHelper
        public final ProviderInfo getProviderInfo(ResolveInfo resolveInfo) {
            return resolveInfo.providerInfo;
        }

        @Override // androidx.emoji2.text.DefaultEmojiCompatConfig.DefaultEmojiCompatConfigHelper
        public final List queryIntentContentProviders(PackageManager packageManager, Intent intent) {
            return packageManager.queryIntentContentProviders(intent, 0);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class DefaultEmojiCompatConfigHelper_API28 extends DefaultEmojiCompatConfigHelper_API19 {
        @Override // androidx.emoji2.text.DefaultEmojiCompatConfig.DefaultEmojiCompatConfigHelper
        public final Signature[] getSigningSignatures(PackageManager packageManager, String str) {
            return packageManager.getPackageInfo(str, 64).signatures;
        }
    }

    private DefaultEmojiCompatConfig() {
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0075  */
    /* JADX WARN: Removed duplicated region for block: B:19:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static androidx.emoji2.text.FontRequestEmojiCompatConfig create(android.content.Context r8) {
        /*
            androidx.emoji2.text.DefaultEmojiCompatConfig$DefaultEmojiCompatConfigFactory r0 = new androidx.emoji2.text.DefaultEmojiCompatConfig$DefaultEmojiCompatConfigFactory
            r1 = 0
            r0.<init>(r1)
            android.content.pm.PackageManager r2 = r8.getPackageManager()
            java.lang.String r3 = "Package manager required to locate emoji font provider"
            androidx.core.util.Preconditions.checkNotNull(r2, r3)
            android.content.Intent r3 = new android.content.Intent
            java.lang.String r4 = "androidx.content.action.LOAD_EMOJI_FONT"
            r3.<init>(r4)
            androidx.emoji2.text.DefaultEmojiCompatConfig$DefaultEmojiCompatConfigHelper r0 = r0.mHelper
            java.util.List r3 = r0.queryIntentContentProviders(r2, r3)
            java.util.Iterator r3 = r3.iterator()
        L20:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L3d
            java.lang.Object r4 = r3.next()
            android.content.pm.ResolveInfo r4 = (android.content.pm.ResolveInfo) r4
            android.content.pm.ProviderInfo r4 = r0.getProviderInfo(r4)
            if (r4 == 0) goto L20
            android.content.pm.ApplicationInfo r5 = r4.applicationInfo
            if (r5 == 0) goto L20
            int r5 = r5.flags
            r6 = 1
            r5 = r5 & r6
            if (r5 != r6) goto L20
            goto L3e
        L3d:
            r4 = r1
        L3e:
            if (r4 != 0) goto L42
        L40:
            r2 = r1
            goto L72
        L42:
            java.lang.String r3 = r4.authority     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L6b
            java.lang.String r4 = r4.packageName     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L6b
            android.content.pm.Signature[] r0 = r0.getSigningSignatures(r2, r4)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L6b
            java.util.ArrayList r2 = new java.util.ArrayList     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L6b
            r2.<init>()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L6b
            int r5 = r0.length     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L6b
            r6 = 0
        L51:
            if (r6 >= r5) goto L5f
            r7 = r0[r6]     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L6b
            byte[] r7 = r7.toByteArray()     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L6b
            r2.add(r7)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L6b
            int r6 = r6 + 1
            goto L51
        L5f:
            java.util.List r0 = java.util.Collections.singletonList(r2)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L6b
            androidx.core.provider.FontRequest r2 = new androidx.core.provider.FontRequest     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L6b
            java.lang.String r5 = "emojicompat-emoji-font"
            r2.<init>(r3, r4, r5, r0)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L6b
            goto L72
        L6b:
            r0 = move-exception
            java.lang.String r2 = "emoji2.text.DefaultEmojiConfig"
            android.util.Log.wtf(r2, r0)
            goto L40
        L72:
            if (r2 != 0) goto L75
            goto L7a
        L75:
            androidx.emoji2.text.FontRequestEmojiCompatConfig r1 = new androidx.emoji2.text.FontRequestEmojiCompatConfig
            r1.<init>(r8, r2)
        L7a:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.emoji2.text.DefaultEmojiCompatConfig.create(android.content.Context):androidx.emoji2.text.FontRequestEmojiCompatConfig");
    }
}
