package com.android.p038wm.shell.draganddrop;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Slog;
import com.android.p038wm.shell.draganddrop.AppResultFactory;
import com.android.p038wm.shell.draganddrop.ExecutableAppHolder;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.net.URI;
import java.util.ArrayList;
import java.util.Optional;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class UriResolver extends BaseResolver {
    public boolean mIsClipDataFromSBrowser;
    public final ExecutableAppHolder.MimeTypeBlockList mMimeTypeBlockList;

    public UriResolver(Context context, ExecutableAppHolder.MultiInstanceBlockList multiInstanceBlockList) {
        super(context, multiInstanceBlockList);
        this.mMimeTypeBlockList = new ExecutableAppHolder.MimeTypeBlockList(context);
    }

    @Override // com.android.p038wm.shell.draganddrop.Resolver
    public final Optional makeFrom(ClipData clipData, int i, AppResultFactory.ResultExtra resultExtra) {
        CharSequence label;
        CharSequence text;
        int itemCount = clipData.getItemCount();
        boolean z = false;
        boolean z2 = BaseResolver.DEBUG;
        String str = this.TAG;
        Uri uri = null;
        if (itemCount > 0) {
            ClipData.Item itemAt = clipData.getItemAt(0);
            ArrayList arrayList = new ArrayList();
            if (itemAt.getUri() != null) {
                arrayList.add(itemAt.getUri());
            }
            Intent intent = itemAt.getIntent();
            if (intent != null) {
                if (intent.getData() != null) {
                    arrayList.add(intent.getData());
                }
                if (intent.getClipData() != null) {
                    intent.getClipData().collectUris(arrayList);
                }
            }
            Uri uri2 = arrayList.isEmpty() ? null : (Uri) arrayList.get(0);
            if (uri2 == null && (text = itemAt.getText()) != null) {
                try {
                    uri2 = Uri.parse(URI.create(text.toString()).toString());
                    if (uri2.getScheme() == null) {
                        uri2 = null;
                    }
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
            if (uri2 != null) {
                if (z2) {
                    Slog.d(str, "extractUriFromClipData: found uri=" + uri2);
                }
                uri = uri2;
            }
        }
        if (uri == null) {
            if (z2) {
                Slog.d(str, "There is no uri.");
            }
            return Optional.empty();
        }
        ClipDescription description = clipData.getDescription();
        if (description != null && (label = description.getLabel()) != null && "terrace-image-or-link-drag-label".equals(label.toString())) {
            z = true;
        }
        this.mIsClipDataFromSBrowser = z;
        Intent intent2 = new Intent();
        intent2.setAction("android.intent.action.VIEW");
        if ("content".equals(uri.getScheme())) {
            intent2.setDataAndType(uri, this.mContext.getContentResolver().getType(uri));
            intent2.addFlags(i);
            intent2.addFlags(QuickStepContract.SYSUI_STATE_BUBBLES_MANAGE_MENU_EXPANDED);
        } else {
            intent2.setData(uri);
        }
        if (intent2.hasWebURI()) {
            intent2.addCategory("android.intent.category.BROWSABLE");
        }
        if (this.mIsClipDataFromSBrowser) {
            intent2.putExtra("terrace-image-or-link-drag-label", true);
        }
        String calculateContentType = BaseResolver.calculateContentType(intent2);
        if (z2) {
            Slog.d(str, "resolveType=" + calculateContentType);
        }
        if (this.mMimeTypeBlockList.mBlockList.contains(calculateContentType)) {
            if (z2) {
                Slog.d(str, "type blocked");
            }
            return Optional.empty();
        }
        int callingUserId = clipData.getCallingUserId();
        boolean z3 = this.mIsClipDataFromSBrowser;
        ArrayList arrayList2 = this.mTempList;
        if (z3) {
            resolveActivitiesForSBrowser(intent2, callingUserId, arrayList2, resultExtra);
        } else {
            resolveActivities(intent2, callingUserId, arrayList2, resultExtra);
        }
        if (arrayList2.isEmpty()) {
            return Optional.empty();
        }
        if (z2) {
            Slog.d(str, "resolveList=" + arrayList2);
        }
        return Optional.of(new SingleIntentAppResult(intent2, arrayList2, this.mMultiInstanceBlockList, this.mMultiInstanceAllowList, calculateContentType, true));
    }
}
