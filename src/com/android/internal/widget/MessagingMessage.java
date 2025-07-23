package com.android.internal.widget;

import android.app.ActivityManager;
import android.app.Notification;
import android.view.View;
import com.android.internal.widget.MessagingLinearLayout;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes6.dex */
public interface MessagingMessage extends MessagingLinearLayout.MessagingChild {
    public static final String IMAGE_MIME_TYPE_PREFIX = "image/";

    void finalizeInflate();

    MessagingMessageState getState();

    int getVisibility();

    default boolean hasOverlappingRendering() {
        return false;
    }

    default void setColor(int i) {
    }

    void setVisibility(int i);

    static MessagingMessage createMessage(IMessagingLayout iMessagingLayout, Notification.MessagingStyle.Message message, ImageResolver imageResolver, boolean z) {
        if (hasImage(message) && !ActivityManager.isLowRamDeviceStatic()) {
            return MessagingImageMessage.createMessage(iMessagingLayout, message, imageResolver, z);
        }
        return MessagingTextMessage.createMessage(iMessagingLayout, message, z);
    }

    static void dropCache() {
        MessagingTextMessage.dropCache();
        MessagingImageMessage.dropCache();
    }

    static boolean hasImage(Notification.MessagingStyle.Message message) {
        return (message.getDataUri() == null || message.getDataMimeType() == null || !message.getDataMimeType().startsWith(IMAGE_MIME_TYPE_PREFIX)) ? false : true;
    }

    default boolean setMessage(Notification.MessagingStyle.Message message, boolean z) {
        getState().setMessage(message);
        return true;
    }

    default Notification.MessagingStyle.Message getMessage() {
        return getState().getMessage();
    }

    default boolean sameAs(Notification.MessagingStyle.Message message) {
        Notification.MessagingStyle.Message message2 = getMessage();
        if (message == null || message2 == null) {
            return message == message2;
        }
        if (Objects.equals(message.getText(), message2.getText()) && Objects.equals(message.getSender(), message2.getSender())) {
            return (message.isRemoteInputHistory() != message2.isRemoteInputHistory() || Objects.equals(Long.valueOf(message.getTimestamp()), Long.valueOf(message2.getTimestamp()))) && Objects.equals(message.getDataMimeType(), message2.getDataMimeType()) && Objects.equals(message.getDataUri(), message2.getDataUri());
        }
        return false;
    }

    default boolean sameAs(MessagingMessage messagingMessage) {
        return sameAs(messagingMessage.getMessage());
    }

    default void removeMessage(ArrayList<MessagingLinearLayout.MessagingChild> arrayList) {
        MessagingGroup group = getGroup();
        if (group != null) {
            group.removeMessage(this, arrayList);
        }
    }

    default void setMessagingGroup(MessagingGroup messagingGroup) {
        getState().setGroup(messagingGroup);
    }

    default void setIsHistoric(boolean z) {
        getState().setIsHistoric(z);
    }

    default MessagingGroup getGroup() {
        return getState().getGroup();
    }

    default void setIsHidingAnimated(boolean z) {
        getState().setIsHidingAnimated(z);
    }

    @Override // com.android.internal.widget.MessagingLinearLayout.MessagingChild
    default boolean isHidingAnimated() {
        return getState().isHidingAnimated();
    }

    @Override // com.android.internal.widget.MessagingLinearLayout.MessagingChild
    default void hideAnimated() {
        setIsHidingAnimated(true);
        MessagingGroup group = getGroup();
        if (group != null) {
            group.performRemoveAnimation(getView(), new Runnable() { // from class: com.android.internal.widget.MessagingMessage$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    MessagingMessage.this.lambda$hideAnimated$0();
                }
            });
        } else {
            setIsHidingAnimated(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* synthetic */ default void lambda$hideAnimated$0() {
        setIsHidingAnimated(false);
    }

    @Override // com.android.internal.widget.MessagingLinearLayout.MessagingChild
    default void recycle() {
        getState().recycle();
    }

    /* JADX WARN: Multi-variable type inference failed */
    default View getView() {
        return (View) this;
    }
}
