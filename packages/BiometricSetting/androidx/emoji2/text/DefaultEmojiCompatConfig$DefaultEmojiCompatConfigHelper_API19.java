package androidx.emoji2.text;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import java.util.List;

/* loaded from: classes.dex */
public class DefaultEmojiCompatConfig$DefaultEmojiCompatConfigHelper_API19 extends DefaultEmojiCompatConfig$DefaultEmojiCompatConfigHelper {
    @Override // androidx.emoji2.text.DefaultEmojiCompatConfig$DefaultEmojiCompatConfigHelper
    public final ProviderInfo getProviderInfo(ResolveInfo resolveInfo) {
        return resolveInfo.providerInfo;
    }

    @Override // androidx.emoji2.text.DefaultEmojiCompatConfig$DefaultEmojiCompatConfigHelper
    public final List queryIntentContentProviders(PackageManager packageManager, Intent intent) {
        return packageManager.queryIntentContentProviders(intent, 0);
    }
}
