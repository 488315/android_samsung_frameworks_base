package com.samsung.vekit.Manager;

import android.util.Log;
import com.samsung.vekit.Common.Type.ContentType;
import com.samsung.vekit.Common.Type.ManagerType;
import com.samsung.vekit.Common.VEContext;
import com.samsung.vekit.Content.AnimatedImage;
import com.samsung.vekit.Content.Audio;
import com.samsung.vekit.Content.Caption;
import com.samsung.vekit.Content.Content;
import com.samsung.vekit.Content.Doodle;
import com.samsung.vekit.Content.FragmentAudio;
import com.samsung.vekit.Content.Image;
import com.samsung.vekit.Content.PortraitVideo;
import com.samsung.vekit.Content.Video;

/* loaded from: classes6.dex */
public class ContentManager extends Manager<Content> {
    public ContentManager(VEContext context) {
        super(context, ManagerType.CONTENT);
        this.TAG = getClass().getSimpleName();
    }

    public Content create(ContentType type, String name) {
        Content content;
        try {
            int uniqueId = generateUniqueId();
            switch (type) {
                case AUDIO:
                    content = new Audio(this.context, uniqueId, name);
                    break;
                case DOODLE:
                    content = new Doodle(this.context, uniqueId, name);
                    break;
                case IMAGE:
                    content = new Image(this.context, uniqueId, name);
                    break;
                case ANIMATED_IMAGE:
                    content = new AnimatedImage(this.context, uniqueId, name);
                    break;
                case VIDEO:
                    content = new Video(this.context, uniqueId, name);
                    break;
                case CAPTION:
                    content = new Caption(this.context, uniqueId, name);
                    break;
                case FRAGMENT_AUDIO:
                    content = new FragmentAudio(this.context, uniqueId, name);
                    break;
                case PORTRAIT_VIDEO:
                    content = new PortraitVideo(this.context, uniqueId, name);
                    break;
                default:
                    return null;
            }
            add(content);
            return content;
        } catch (Exception e) {
            Log.e(this.TAG, "create: ", e);
            return null;
        }
    }
}
