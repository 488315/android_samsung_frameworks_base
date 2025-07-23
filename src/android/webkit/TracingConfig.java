package android.webkit;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* loaded from: classes4.dex */
public class TracingConfig {
    public static final int CATEGORIES_ALL = 1;
    public static final int CATEGORIES_ANDROID_WEBVIEW = 2;
    public static final int CATEGORIES_FRAME_VIEWER = 64;
    public static final int CATEGORIES_INPUT_LATENCY = 8;
    public static final int CATEGORIES_JAVASCRIPT_AND_RENDERING = 32;
    public static final int CATEGORIES_NONE = 0;
    public static final int CATEGORIES_RENDERING = 16;
    public static final int CATEGORIES_WEB_DEVELOPER = 4;
    public static final int RECORD_CONTINUOUSLY = 1;
    public static final int RECORD_UNTIL_FULL = 0;
    private final List<String> mCustomIncludedCategories;
    private int mPredefinedCategories;
    private int mTracingMode;

    @Retention(RetentionPolicy.SOURCE)
    public @interface PredefinedCategories {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TracingMode {
    }

    public TracingConfig(int i, List<String> list, int i2) {
        ArrayList arrayList = new ArrayList();
        this.mCustomIncludedCategories = arrayList;
        this.mPredefinedCategories = i;
        arrayList.addAll(list);
        this.mTracingMode = i2;
    }

    public int getPredefinedCategories() {
        return this.mPredefinedCategories;
    }

    public List<String> getCustomIncludedCategories() {
        return this.mCustomIncludedCategories;
    }

    public int getTracingMode() {
        return this.mTracingMode;
    }

    public static class Builder {
        private int mPredefinedCategories = 0;
        private final List<String> mCustomIncludedCategories = new ArrayList();
        private int mTracingMode = 1;

        public TracingConfig build() {
            return new TracingConfig(this.mPredefinedCategories, this.mCustomIncludedCategories, this.mTracingMode);
        }

        public Builder addCategories(int... iArr) {
            for (int i : iArr) {
                this.mPredefinedCategories = i | this.mPredefinedCategories;
            }
            return this;
        }

        public Builder addCategories(String... strArr) {
            for (String str : strArr) {
                this.mCustomIncludedCategories.add(str);
            }
            return this;
        }

        public Builder addCategories(Collection<String> collection) {
            this.mCustomIncludedCategories.addAll(collection);
            return this;
        }

        public Builder setTracingMode(int i) {
            this.mTracingMode = i;
            return this;
        }
    }
}
