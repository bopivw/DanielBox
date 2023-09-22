package vdatta.us.danielbox.util.messaging;/*
package studio.soarinng.com.danielstaff.util.messaging;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class TextComponentFactory {
    private final TextComponent textComponent;

    public TextComponentFactory(final MessageComponent messageComponent) {
        textComponent = new TextComponent(messageComponent.getString());
    }

    public TextComponentFactory addShowText(final MessageComponent messageComponent) {
        textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(messageComponent.getString()).create()));
        return this;
    }

    public TextComponentFactory addRunCommand(final MessageComponent messageComponent) {
        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, messageComponent.getString()));
        return this;
    }

    public TextComponentFactory addSuggestedCommand(final MessageComponent messageComponent) {
        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, messageComponent.getString()));
        return this;
    }

    public TextComponent build() {
        return textComponent;
    }
}*/
