package xyz.wagyourtail.jsmacros.client.api.library.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import xyz.wagyourtail.jsmacros.core.library.BaseLibrary;
import xyz.wagyourtail.jsmacros.core.library.Library;

import java.util.*;

/**
 *
 * Functions for getting and modifying key pressed states.
 * 
 * An instance of this class is passed to scripts as the {@code KeyBind} variable.
 * 
 * @author Wagyourtail
 */
 @Library("KeyBind")
 @SuppressWarnings("unused")
public class FKeyBind extends BaseLibrary {
    private static final MinecraftClient mc = MinecraftClient.getInstance();
    /**
     * Don't modify
     */
    public static final Set<String> pressedKeys = new HashSet<>();
    
    /**
     * Dont use this one... get the raw minecraft keycode class.
     *
     * @param keyName
     * @return the raw minecraft keycode class
     */
    public InputUtil.KeyCode getKeyCode(String keyName) {
        try {
            return InputUtil.fromName(keyName);
        } catch (Exception e) {
            return InputUtil.UNKNOWN_KEYCODE;
        }
    }
    
    /**
     * @since 1.2.2
     *
     * @return A {@link java.util.Map Map} of all the minecraft keybinds.
     */
    public Map<String, String> getKeyBindings() {
        Map<String, String> keys = new HashMap<>();
        for (KeyBinding key : ImmutableList.copyOf(mc.options.keysAll)) {
            keys.put(key.getId(), key.getName());
        }
        return keys;
    }
    
    /**
     * Sets a minecraft keybind to the specified key.
     *
     * @since 1.2.2
     *
     * @param bind
     * @param key
     */
    public void setKeyBind(String bind, String key) {
        for (KeyBinding keybind : mc.options.keysAll) {
            if (keybind.getId().equals(bind)) {
                keybind.setKeyCode(InputUtil.fromName(key));
                return;
            }
        }
    }
    
    /**
     * Set a key-state for a key.
     *
     * @param keyName
     * @param keyState
     */
    public void key(String keyName, boolean keyState) {
        key(getKeyCode(keyName), keyState);
    }
    
    /**
     * Don't use this one... set the key-state using the raw minecraft keycode class.
     *
     * @param keyBind
     * @param keyState
     */
    protected void key(InputUtil.KeyCode keyBind, boolean keyState) {
        if (keyState) KeyBinding.onKeyPressed(keyBind);
        KeyBinding.setKeyPressed(keyBind, keyState);

        // add to pressed keys list
        if (keyState) pressedKeys.add(keyBind.getName());
        else pressedKeys.remove(keyBind.getName());
    }
    
    /**
     * Set a key-state using the name of the keybind rather than the name of the key.
     *
     * This is probably the one you should use.
     *
     * @since 1.2.2
     *
     * @param keyBind
     * @param keyState
     */
    public void keyBind(String keyBind, boolean keyState) {
        for (KeyBinding key : mc.options.keysAll) {
            if (key.getName().equals(keyBind)) {
                if (keyState) KeyBinding.onKeyPressed(InputUtil.fromName(key.getName()));
                key.setPressed(keyState);

                // add to pressed keys list
                if (keyState) pressedKeys.add(key.getName());
                else pressedKeys.remove(key.getName());
                return;
            }
        }
    }
    
    /**
     * Don't use this one... set the key-state using the raw minecraft keybind class.
     *
     * @param keyBind
     * @param keyState
     */
    protected void key(KeyBinding keyBind, boolean keyState) {
        if (keyState) KeyBinding.onKeyPressed(InputUtil.fromName(keyBind.getName()));
        keyBind.setPressed(keyState);

        // add to pressed keys list
        if (keyState) pressedKeys.add(keyBind.getName());
        else pressedKeys.remove(keyBind.getName());
    }
    
    /**
     * @since 1.2.6 (turned into set instead of list in 1.6.5)
     * 
     * @return a set of currently pressed keys.
     */
    public Set<String> getPressedKeys() {
        synchronized (pressedKeys) {
            return ImmutableSet.copyOf(pressedKeys);
        }
    }
}
