package com.android.p038wm.shell.draganddrop;

import android.app.PendingIntent;
import android.app.RemoteAction;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.service.textclassifier.TextClassifierService;
import android.text.TextUtils;
import android.util.Slog;
import android.view.textclassifier.TextClassification;
import android.view.textclassifier.TextClassificationManager;
import com.android.p038wm.shell.draganddrop.AppResultFactory;
import com.android.p038wm.shell.draganddrop.ExecutableAppHolder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TextClassifierResolver extends BaseResolver {
    public int mCallingUserId;

    public TextClassifierResolver(Context context, ExecutableAppHolder.MultiInstanceBlockList multiInstanceBlockList) {
        super(context, multiInstanceBlockList);
    }

    public static Object runOnBlocking(TextClassifierResolver$$ExternalSyntheticLambda0 textClassifierResolver$$ExternalSyntheticLambda0) {
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
        Object obj = newCachedThreadPool.submit(textClassifierResolver$$ExternalSyntheticLambda0).get(3L, TimeUnit.SECONDS);
        newCachedThreadPool.shutdown();
        return obj;
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0074  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final SingleIntentAppResult getResultFromTextClassification(TextClassification textClassification, AppResultFactory.ResultExtra resultExtra, boolean z) {
        RemoteAction remoteAction;
        PendingIntent actionIntent;
        Intent intent;
        String str;
        List<RemoteAction> actions = textClassification.getActions();
        if (actions == null || actions.isEmpty() || (actionIntent = (remoteAction = actions.get(0)).getActionIntent()) == null || (intent = actionIntent.getIntent()) == null || "android.intent.action.TRANSLATE".equals(intent.getAction())) {
            return null;
        }
        if (BaseResolver.DEBUG) {
            Slog.d(this.TAG, "updateByTextClassifying: intent in TextClassification=" + intent);
        }
        int i = this.mCallingUserId;
        ArrayList arrayList = this.mTempList;
        resolveActivities(intent, i, arrayList, resultExtra);
        if (arrayList.isEmpty()) {
            return null;
        }
        String calculateContentType = BaseResolver.calculateContentType(intent);
        if (calculateContentType == null) {
            CharSequence contentDescription = remoteAction.getContentDescription();
            if (contentDescription == null) {
                str = null;
                return new SingleIntentAppResult(intent, arrayList, this.mMultiInstanceBlockList, this.mMultiInstanceAllowList, str, true, z ? remoteAction.getIcon() : null);
            }
            calculateContentType = contentDescription.toString();
        }
        str = calculateContentType;
        return new SingleIntentAppResult(intent, arrayList, this.mMultiInstanceBlockList, this.mMultiInstanceAllowList, str, true, z ? remoteAction.getIcon() : null);
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.wm.shell.draganddrop.TextClassifierResolver$$ExternalSyntheticLambda0] */
    @Override // com.android.p038wm.shell.draganddrop.Resolver
    public final Optional makeFrom(final ClipData clipData, int i, final AppResultFactory.ResultExtra resultExtra) {
        String str = this.TAG;
        try {
            return (Optional) runOnBlocking(new Callable() { // from class: com.android.wm.shell.draganddrop.TextClassifierResolver$$ExternalSyntheticLambda0
                @Override // java.util.concurrent.Callable
                public final Object call() {
                    TextClassifierResolver textClassifierResolver = TextClassifierResolver.this;
                    textClassifierResolver.getClass();
                    ClipData clipData2 = clipData;
                    if (clipData2.getItemCount() == 0) {
                        return Optional.empty();
                    }
                    CharSequence text = clipData2.getItemAt(0).getText();
                    boolean z = BaseResolver.DEBUG;
                    String str2 = textClassifierResolver.TAG;
                    if (text == null) {
                        if (z) {
                            Slog.d(str2, "updateByTextClassifying: There is Null text.");
                        }
                        return Optional.empty();
                    }
                    String replaceAll = text.toString().replaceAll("\u0000", "");
                    if (TextUtils.isEmpty(replaceAll)) {
                        if (z) {
                            Slog.d(str2, "updateByTextClassifying: There is no text.");
                        }
                        return Optional.empty();
                    }
                    textClassifierResolver.mCallingUserId = clipData2.getCallingUserId();
                    TextClassification.Request build = new TextClassification.Request.Builder(replaceAll, 0, replaceAll.length()).build();
                    Context context = textClassifierResolver.mContext;
                    TextClassification classifyText = TextClassifierService.getDefaultTextClassifierImplementation(context).classifyText(build);
                    AppResultFactory.ResultExtra resultExtra2 = resultExtra;
                    SingleIntentAppResult resultFromTextClassification = textClassifierResolver.getResultFromTextClassification(classifyText, resultExtra2, false);
                    if (resultFromTextClassification == null) {
                        resultFromTextClassification = textClassifierResolver.getResultFromTextClassification(((TextClassificationManager) context.getSystemService("textclassification")).getTextClassifier(1).classifyText(build), resultExtra2, true);
                        Slog.d(str2, "updateByTextClassifying: Use System type");
                    } else {
                        Slog.d(str2, "updateByTextClassifying: Use Default System type");
                    }
                    return resultFromTextClassification != null ? Optional.of(resultFromTextClassification) : Optional.empty();
                }
            });
        } catch (InterruptedException | ExecutionException e) {
            Slog.d(str, "failed to update from text classifier." + e.getMessage());
            return Optional.empty();
        } catch (TimeoutException unused) {
            Slog.d(str, "failed to update from text classifier due to timeout.");
            return Optional.empty();
        }
    }
}
