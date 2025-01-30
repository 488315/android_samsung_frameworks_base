package com.android.systemui.media;

import com.android.systemui.media.controls.models.player.MediaData;
import java.util.Map;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class SecMediaHost$$ExternalSyntheticLambda6 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SecMediaHost f$0;
    public final /* synthetic */ MediaType f$1;

    public /* synthetic */ SecMediaHost$$ExternalSyntheticLambda6(SecMediaHost secMediaHost, MediaType mediaType, int i) {
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
                secMediaHost.removePlayer(mediaType, (String) ((Map.Entry) obj).getKey());
                break;
            default:
                SecMediaHost secMediaHost2 = this.f$0;
                MediaType mediaType2 = this.f$1;
                Map.Entry entry = (Map.Entry) obj;
                secMediaHost2.getClass();
                secMediaHost2.removePlayer(mediaType2, (String) entry.getKey());
                secMediaHost2.addOrUpdatePlayer((String) entry.getKey(), null, (MediaData) entry.getValue(), mediaType2);
                break;
        }
    }
}
