package com.android.internal.widget;

import android.app.Notification;
import android.app.Person;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.RemotableViewMethod;
import android.widget.FrameLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.flags.Flags;

import com.android.internal.R;

import java.util.function.Consumer;

@RemoteViews.RemoteView
public class CallLayout extends FrameLayout {
    private CachingIconView mConversationIconBadgeBg;
    private CachingIconView mConversationIconView;
    private TextView mConversationText;
    private CachingIconView mIcon;
    private Icon mLargeIcon;
    private int mLayoutColor;
    private final PeopleHelper mPeopleHelper;
    private Person mUser;

    public CallLayout(Context context) {
        super(context);
        this.mPeopleHelper = new PeopleHelper();
    }

    public CallLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mPeopleHelper = new PeopleHelper();
    }

    public CallLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mPeopleHelper = new PeopleHelper();
    }

    public CallLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mPeopleHelper = new PeopleHelper();
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mPeopleHelper.init(getContext());
        this.mConversationText = (TextView) findViewById(R.id.conversation_text);
        this.mConversationIconView = (CachingIconView) findViewById(R.id.conversation_icon);
        this.mIcon = (CachingIconView) findViewById(16908294);
        this.mConversationIconBadgeBg =
                (CachingIconView) findViewById(R.id.conversation_icon_badge_bg);
        this.mIcon.setOnForceHiddenChangedListener(
                new Consumer() { // from class:
                                 // com.android.internal.widget.CallLayout$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        CallLayout.this.lambda$onFinishInflate$0((Boolean) obj);
                    }
                });
    }

    public /* synthetic */ void lambda$onFinishInflate$0(Boolean forceHidden) {
        this.mPeopleHelper.animateViewForceHidden(
                this.mConversationIconBadgeBg, forceHidden.booleanValue());
    }

    private Icon getConversationIcon() {
        CharSequence callerName = "";
        String symbol = "";
        Icon icon = null;
        if (this.mUser != null) {
            icon = this.mUser.getIcon();
            callerName = this.mUser.getName();
            symbol = this.mPeopleHelper.findNamePrefix(callerName, "");
        }
        if (icon == null) {
            icon = this.mLargeIcon;
        }
        if (icon == null) {
            Icon icon2 =
                    this.mPeopleHelper.createAvatarSymbol(callerName, symbol, this.mLayoutColor);
            return icon2;
        }
        return icon;
    }

    public Runnable setLayoutColorAsync(final int color) {
        if (!Flags.callStyleSetDataAsync()) {
            return new Runnable() { // from class:
                                    // com.android.internal.widget.CallLayout$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    CallLayout.this.lambda$setLayoutColorAsync$1(color);
                }
            };
        }
        this.mLayoutColor = color;
        return new Runnable() { // from class:
                                // com.android.internal.widget.CallLayout$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                CallLayout.lambda$setLayoutColorAsync$2();
            }
        };
    }

    static /* synthetic */ void lambda$setLayoutColorAsync$2() {}

    @RemotableViewMethod(asyncImpl = "setLayoutColorAsync")
    /* renamed from: setLayoutColor, reason: merged with bridge method [inline-methods] */
    public void lambda$setLayoutColorAsync$1(int color) {
        this.mLayoutColor = color;
    }

    @RemotableViewMethod
    public void setNotificationBackgroundColor(int color) {
        this.mConversationIconBadgeBg.setImageTintList(ColorStateList.valueOf(color));
    }

    public Runnable setLargeIconAsync(final Icon largeIcon) {
        if (!Flags.callStyleSetDataAsync()) {
            return new Runnable() { // from class:
                                    // com.android.internal.widget.CallLayout$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    CallLayout.this.lambda$setLargeIconAsync$3(largeIcon);
                }
            };
        }
        this.mLargeIcon = largeIcon;
        return new Runnable() { // from class:
                                // com.android.internal.widget.CallLayout$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                CallLayout.lambda$setLargeIconAsync$4();
            }
        };
    }

    static /* synthetic */ void lambda$setLargeIconAsync$4() {}

    @RemotableViewMethod(asyncImpl = "setLargeIconAsync")
    /* renamed from: setLargeIcon, reason: merged with bridge method [inline-methods] */
    public void lambda$setLargeIconAsync$3(Icon largeIcon) {
        this.mLargeIcon = largeIcon;
    }

    @RemotableViewMethod(asyncImpl = "setDataAsync")
    /* renamed from: setData, reason: merged with bridge method [inline-methods] */
    public void lambda$setDataAsync$5(Bundle extras) {
        Person person = getPerson(extras);
        setUser(person);
        Icon icon = getConversationIcon();
        this.mConversationIconView.setImageIcon(icon);
    }

    public Runnable setDataAsync(final Bundle extras) {
        if (!Flags.callStyleSetDataAsync()) {
            return new Runnable() { // from class:
                                    // com.android.internal.widget.CallLayout$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    CallLayout.this.lambda$setDataAsync$5(extras);
                }
            };
        }
        Person person = getPerson(extras);
        setUser(person);
        Icon conversationIcon = getConversationIcon();
        return this.mConversationIconView.setImageIconAsync(conversationIcon);
    }

    private Person getPerson(Bundle extras) {
        return (Person) extras.getParcelable(Notification.EXTRA_CALL_PERSON, Person.class);
    }

    private void setUser(Person user) {
        this.mUser = user;
    }
}
