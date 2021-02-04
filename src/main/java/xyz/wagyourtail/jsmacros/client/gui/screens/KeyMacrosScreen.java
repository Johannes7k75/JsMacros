package xyz.wagyourtail.jsmacros.client.gui.screens;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.InputUtil;
import xyz.wagyourtail.jsmacros.client.JsMacros;
import xyz.wagyourtail.jsmacros.client.api.event.impl.EventKey;
import xyz.wagyourtail.jsmacros.client.gui.containers.MacroContainer;
import xyz.wagyourtail.jsmacros.core.Core;
import xyz.wagyourtail.jsmacros.core.config.ScriptTrigger;
import xyz.wagyourtail.jsmacros.core.event.BaseListener;
import xyz.wagyourtail.jsmacros.core.event.Event;
import xyz.wagyourtail.jsmacros.core.event.IEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class KeyMacrosScreen extends MacroScreen {

    public KeyMacrosScreen(Screen parent) {
        super(parent);
    }

    public void init() {
        super.init();
        keyScreen.setColor(0x4FFFFFFF);

        eventScreen.onPress = (btn) -> {
            assert client != null;
            client.openScreen(new EventMacrosScreen(this));
        };

        profileScreen.onPress = (btn) -> {
            assert client != null;
            client.openScreen(new ProfileScreen(this));
        };

        Set<IEventListener> listeners = Core.instance.eventRegistry.getListeners().get(EventKey.class.getAnnotation(Event.class).value());
        List<ScriptTrigger> macros = new ArrayList<>();

        if (listeners != null) for (IEventListener event : ImmutableList.copyOf(listeners)) {
            if (event instanceof BaseListener && ((BaseListener) event).getRawTrigger().triggerType != ScriptTrigger.TriggerType.EVENT) macros.add(((BaseListener) event).getRawTrigger());
        }

        macros.sort(JsMacros.core.config.options.getSortComparator());

        for (ScriptTrigger macro : macros) {
            addMacro(macro);
        }
    }

    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        String translationKey = EventKey.getKeyModifiers(modifiers);
        if (!translationKey.equals("")) translationKey += "+";
        translationKey += InputUtil.fromKeyCode(keyCode, scanCode).getTranslationKey();
        for (MacroContainer macro : macros) {
            if (!macro.onKey(translationKey)) return false;
        }
        return super.keyReleased(keyCode, scanCode, modifiers);
    }

    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        int mods = 0;
        if (hasShiftDown()) mods += 1;
        if (hasControlDown()) mods += 2;
        if (hasAltDown()) mods += 4;
        String translationKey = EventKey.getKeyModifiers(mods);
        if (!translationKey.equals("")) translationKey += "+";
        translationKey += InputUtil.Type.MOUSE.createFromCode(button).getTranslationKey();
        for (MacroContainer macro : macros) {
            if (!macro.onKey(translationKey)) return false;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }
}