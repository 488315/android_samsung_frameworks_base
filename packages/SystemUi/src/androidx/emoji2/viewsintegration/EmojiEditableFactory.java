package androidx.emoji2.viewsintegration;

import android.text.Editable;
import androidx.emoji2.text.SpannableBuilder;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class EmojiEditableFactory extends Editable.Factory {
    public static final Object INSTANCE_LOCK = new Object();
    public static volatile EmojiEditableFactory sInstance;
    public static Class sWatcherClass;

    private EmojiEditableFactory() {
        try {
            sWatcherClass = Class.forName("android.text.DynamicLayout$ChangeWatcher", false, EmojiEditableFactory.class.getClassLoader());
        } catch (Throwable unused) {
        }
    }

    public static Editable.Factory getInstance() {
        if (sInstance == null) {
            synchronized (INSTANCE_LOCK) {
                if (sInstance == null) {
                    sInstance = new EmojiEditableFactory();
                }
            }
        }
        return sInstance;
    }

    @Override // android.text.Editable.Factory
    public final Editable newEditable(CharSequence charSequence) {
        Class cls = sWatcherClass;
        return cls != null ? new SpannableBuilder(cls, charSequence) : super.newEditable(charSequence);
    }
}
