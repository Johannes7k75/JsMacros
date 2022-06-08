package xyz.wagyourtail.jsmacros.client.api.event.impl;

import net.minecraft.client.MinecraftClient;
import xyz.wagyourtail.jsmacros.client.api.library.impl.FKeyBind;
import xyz.wagyourtail.jsmacros.client.config.ClientConfigV2;
import xyz.wagyourtail.jsmacros.core.Core;
import xyz.wagyourtail.jsmacros.core.event.BaseEvent;
import xyz.wagyourtail.jsmacros.core.event.Event;
import xyz.wagyourtail.wagyourgui.BaseScreen;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Wagyourtail
 * @since 1.2.7
 */
 @Event(value = "Key", oldName = "KEY")
public class EventKey implements BaseEvent {
    static final MinecraftClient mc = MinecraftClient.getInstance();
    public final int action;
    public final int key;
    public final int mods;

    private static final Set<Integer> wasNullOnDown = new HashSet<>();
    
    public EventKey(int key, int scancode, int action, int mods) {
        this.action = action;
        this.key = key;
        this.mods = mods;

        synchronized (FKeyBind.pressedKeys) {
            if (action == 1) FKeyBind.pressedKeys.add(key);
            else FKeyBind.pressedKeys.remove(key);
        }

        if (mc.currentScreen != null) {
            if (action != 0 || !wasNullOnDown.contains(key)) {
                if (Core.getInstance().config.getOptions(ClientConfigV2.class).disableKeyWhenScreenOpen) return;
                if (mc.currentScreen instanceof BaseScreen) return;
            }
        } else if (action == 1) {
            wasNullOnDown.add(key);
        }

        if (action == 0) {
            wasNullOnDown.remove(key);
        }

        // fix mods if it was a mod key
        if (action == 1) {
            if (key == 340 || key == 344) mods -= 1;
            else if (key == 341 || key == 345) mods -= 2;
            else if (key == 342 || key == 346) mods -= 4;
        }

        profile.triggerEvent(this);
        
    }

    public String toString() {
        return String.format("%s:{\"key\": \"%s\"}", this.getEventName(), key);
    }
}
