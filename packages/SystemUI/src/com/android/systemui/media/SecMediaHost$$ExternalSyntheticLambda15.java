package com.android.systemui.media;

import com.android.systemui.media.controls.shared.model.MediaData;
import java.util.Map;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class SecMediaHost$$ExternalSyntheticLambda15 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SecMediaHost f$0;
    public final /* synthetic */ MediaType f$1;

    public /* synthetic */ SecMediaHost$$ExternalSyntheticLambda15(SecMediaHost secMediaHost, MediaType mediaType, int i) {
        this.$r8$classId = i;
        this.f$0 = secMediaHost;
        this.f$1 = mediaType;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                SecMediaHost secMediaHost = this.f$0;
                MediaType mediaType = this.f$1;
                secMediaHost.getClass();
                secMediaHost.removePlayer((String) ((Map.Entry) obj).getKey(), mediaType);
                break;
            default:
                SecMediaHost secMediaHost2 = this.f$0;
                MediaType mediaType2 = this.f$1;
                Map.Entry entry = (Map.Entry) obj;
                secMediaHost2.getClass();
                secMediaHost2.removePlayer((String) entry.getKey(), mediaType2);
                secMediaHost2.addOrUpdatePlayer((String) entry.getKey(), null, (MediaData) entry.getValue(), mediaType2);
                break;
        }
    }
}
