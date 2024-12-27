package android.view.textclassifier;

import android.content.Context;
import android.os.ServiceManager;
import com.android.internal.util.IndentingPrintWriter;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class TextClassificationManager {
    private static final String LOG_TAG = "androidtc";
    private static final TextClassificationConstants sDefaultSettings = new TextClassificationConstants();
    private final Context mContext;
    private TextClassifier mCustomTextClassifier;
    private TextClassificationConstants mSettings;
    private final Object mLock = new Object();
    private final TextClassificationSessionFactory mDefaultSessionFactory = new TextClassificationSessionFactory() { // from class: android.view.textclassifier.TextClassificationManager$$ExternalSyntheticLambda0
        @Override // android.view.textclassifier.TextClassificationSessionFactory
        public final TextClassifier createTextClassificationSession(TextClassificationContext textClassificationContext) {
            TextClassifier lambda$new$0;
            lambda$new$0 = TextClassificationManager.this.lambda$new$0(textClassificationContext);
            return lambda$new$0;
        }
    };
    private TextClassificationSessionFactory mSessionFactory = this.mDefaultSessionFactory;

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ TextClassifier lambda$new$0(TextClassificationContext classificationContext) {
        return new TextClassificationSession(classificationContext, getTextClassifier());
    }

    public TextClassificationManager(Context context) {
        this.mContext = (Context) Objects.requireNonNull(context);
    }

    public TextClassifier getTextClassifier() {
        synchronized (this.mLock) {
            if (this.mCustomTextClassifier != null) {
                return this.mCustomTextClassifier;
            }
            if (getSettings().isSystemTextClassifierEnabled()) {
                return getSystemTextClassifier(1);
            }
            return getLocalTextClassifier();
        }
    }

    public void setTextClassifier(TextClassifier textClassifier) {
        synchronized (this.mLock) {
            this.mCustomTextClassifier = textClassifier;
        }
    }

    public TextClassifier getTextClassifier(int type) {
        switch (type) {
            case 0:
                return getLocalTextClassifier();
            default:
                return getSystemTextClassifier(type);
        }
    }

    private TextClassificationConstants getSettings() {
        TextClassificationConstants textClassificationConstants;
        synchronized (this.mLock) {
            if (this.mSettings == null) {
                this.mSettings = new TextClassificationConstants();
            }
            textClassificationConstants = this.mSettings;
        }
        return textClassificationConstants;
    }

    public TextClassifier createTextClassificationSession(TextClassificationContext classificationContext) {
        Objects.requireNonNull(classificationContext);
        TextClassifier textClassifier = this.mSessionFactory.createTextClassificationSession(classificationContext);
        Objects.requireNonNull(textClassifier, "Session Factory should never return null");
        return textClassifier;
    }

    public TextClassifier createTextClassificationSession(TextClassificationContext classificationContext, TextClassifier textClassifier) {
        Objects.requireNonNull(classificationContext);
        Objects.requireNonNull(textClassifier);
        return new TextClassificationSession(classificationContext, textClassifier);
    }

    public void setTextClassificationSessionFactory(TextClassificationSessionFactory factory) {
        synchronized (this.mLock) {
            if (factory != null) {
                this.mSessionFactory = factory;
            } else {
                this.mSessionFactory = this.mDefaultSessionFactory;
            }
        }
    }

    private TextClassifier getSystemTextClassifier(int type) {
        synchronized (this.mLock) {
            if (getSettings().isSystemTextClassifierEnabled()) {
                try {
                    Log.d("androidtc", "Initializing SystemTextClassifier, type = " + TextClassifier.typeToString(type));
                    return new SystemTextClassifier(this.mContext, getSettings(), type == 2);
                } catch (ServiceManager.ServiceNotFoundException e) {
                    Log.e("androidtc", "Could not initialize SystemTextClassifier", e);
                }
            }
            return TextClassifier.NO_OP;
        }
    }

    private TextClassifier getLocalTextClassifier() {
        Log.d("androidtc", "Local text-classifier not supported. Returning a no-op text-classifier.");
        return TextClassifier.NO_OP;
    }

    public void dump(IndentingPrintWriter pw) {
        getSystemTextClassifier(2).dump(pw);
        getSystemTextClassifier(1).dump(pw);
        getSettings().dump(pw);
    }

    public static TextClassificationConstants getSettings(Context context) {
        Objects.requireNonNull(context);
        TextClassificationManager tcm = (TextClassificationManager) context.getSystemService(TextClassificationManager.class);
        if (tcm != null) {
            return tcm.getSettings();
        }
        return sDefaultSettings;
    }
}
