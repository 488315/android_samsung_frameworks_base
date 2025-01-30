package androidx.emoji2.text;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.util.Log;
import androidx.core.provider.FontRequest;
import androidx.core.util.Preconditions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DefaultEmojiCompatConfig {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DefaultEmojiCompatConfigFactory {
        public final DefaultEmojiCompatConfigHelper mHelper;

        public DefaultEmojiCompatConfigFactory(DefaultEmojiCompatConfigHelper defaultEmojiCompatConfigHelper) {
            this.mHelper = defaultEmojiCompatConfigHelper == null ? new DefaultEmojiCompatConfigHelper_API28() : defaultEmojiCompatConfigHelper;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class DefaultEmojiCompatConfigHelper {
        public ProviderInfo getProviderInfo(ResolveInfo resolveInfo) {
            throw new IllegalStateException("Unable to get provider info prior to API 19");
        }

        public Signature[] getSigningSignatures(PackageManager packageManager, String str) {
            return packageManager.getPackageInfo(str, 64).signatures;
        }

        public List queryIntentContentProviders(PackageManager packageManager, Intent intent) {
            return Collections.emptyList();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DefaultEmojiCompatConfigHelper_API28 extends DefaultEmojiCompatConfigHelper_API19 {
        @Override // androidx.emoji2.text.DefaultEmojiCompatConfig.DefaultEmojiCompatConfigHelper
        public final Signature[] getSigningSignatures(PackageManager packageManager, String str) {
            return packageManager.getPackageInfo(str, 64).signatures;
        }
    }

    private DefaultEmojiCompatConfig() {
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0043 A[EDGE_INSN: B:12:0x0043->B:13:0x0043 BREAK  A[LOOP:0: B:2:0x0020->B:29:?], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0078  */
    /* JADX WARN: Removed duplicated region for block: B:19:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:29:? A[LOOP:0: B:2:0x0020->B:29:?, LOOP_END, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static FontRequestEmojiCompatConfig create(Context context) {
        int i;
        ProviderInfo providerInfo;
        FontRequest fontRequest;
        boolean z;
        ApplicationInfo applicationInfo;
        DefaultEmojiCompatConfigFactory defaultEmojiCompatConfigFactory = new DefaultEmojiCompatConfigFactory(null);
        PackageManager packageManager = context.getPackageManager();
        Preconditions.checkNotNull(packageManager, "Package manager required to locate emoji font provider");
        Intent intent = new Intent("androidx.content.action.LOAD_EMOJI_FONT");
        DefaultEmojiCompatConfigHelper defaultEmojiCompatConfigHelper = defaultEmojiCompatConfigFactory.mHelper;
        Iterator it = defaultEmojiCompatConfigHelper.queryIntentContentProviders(packageManager, intent).iterator();
        while (true) {
            if (!it.hasNext()) {
                providerInfo = null;
                break;
            }
            providerInfo = defaultEmojiCompatConfigHelper.getProviderInfo((ResolveInfo) it.next());
            if (providerInfo != null && (applicationInfo = providerInfo.applicationInfo) != null) {
                z = true;
                if ((applicationInfo.flags & 1) == 1) {
                    if (!z) {
                        break;
                    }
                }
            }
            z = false;
            if (!z) {
            }
        }
        if (providerInfo != null) {
            try {
                String str = providerInfo.authority;
                String str2 = providerInfo.packageName;
                Signature[] signingSignatures = defaultEmojiCompatConfigHelper.getSigningSignatures(packageManager, str2);
                ArrayList arrayList = new ArrayList();
                for (Signature signature : signingSignatures) {
                    arrayList.add(signature.toByteArray());
                }
                fontRequest = new FontRequest(str, str2, "emojicompat-emoji-font", (List<List<byte[]>>) Collections.singletonList(arrayList));
            } catch (PackageManager.NameNotFoundException e) {
                Log.wtf("emoji2.text.DefaultEmojiConfig", e);
            }
            if (fontRequest != null) {
                return null;
            }
            return new FontRequestEmojiCompatConfig(context, fontRequest);
        }
        fontRequest = null;
        if (fontRequest != null) {
        }
    }
}
